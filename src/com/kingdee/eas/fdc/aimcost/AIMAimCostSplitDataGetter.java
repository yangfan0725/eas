package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.XTable;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class AIMAimCostSplitDataGetter extends CostDataGetter implements Serializable {
	private XTable aimCostXTable;

	private AimProductTypeGetter productTypeGetter;

	private HashMap aimEntryProductMap = new HashMap();

	private HashMap acctMap = new HashMap();
	private HashMap costEntryMap = new HashMap();

	public void updateProductMap(String entryId, AIMAimCostProductSplitEntryCollection col) throws BOSException {
		this.aimEntryProductMap.put(entryId, col);
		initProductSplitData();
	}

	Context ctx = null;

	public AIMAimCostSplitDataGetter(String objectId) {
		try {

			init(objectId, null);
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
	}

	public AIMAimCostSplitDataGetter(Context ctx, String objectId) {
		try {
			this.ctx = ctx;
			init(objectId, null);
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
	}

	/*
	 * �����ӿ���Ŀ��ɱ���̯���� @param objectId @param productTypeGetter
	 */
	public AIMAimCostSplitDataGetter(String objectId, AimProductTypeGetter productTypeGetter) {
		try {

			init(objectId, productTypeGetter);
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
	}

	public AIMAimCostSplitDataGetter(Context ctx, String objectId, AimProductTypeGetter productTypeGetter) {
		try {
			this.ctx = ctx;
			init(objectId, productTypeGetter);
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
	}

	private void init(String objectId, AimProductTypeGetter productTypeGetter) throws BOSException, SQLException {
		this.initAimCostData(objectId);
		if (productTypeGetter != null) {
			this.productTypeGetter = productTypeGetter;
		} else {
			this.productTypeGetter = new AimProductTypeGetter(ctx, objectId, ProjectStageEnum.AIMCOST);
		}
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		FilterInfo acctFilter = new FilterInfo();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", objectId));
		} else {
			acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", objectId));
		}
		acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		this.initAcct(acctFilter);
	}

	public BigDecimal getAimCost(String acctId) {
		Map aimRow = this.aimCostXTable.getRow(acctId);
		BigDecimal aimAmount = FDCHelper.ZERO;
		if (aimRow != null) {
			aimAmount = (BigDecimal) aimRow.get("aimCostSum");
		}
		return aimAmount;
	}

	public BigDecimal getSumAimCost(String acctId) throws BOSException, SQLException {
		BigDecimal aimCost = FDCHelper.ZERO;
		CostAccountInfo acct = (CostAccountInfo) this.acctMap.get(acctId);
		String fullNumber = "";
		if (acct == null) {
			return FDCHelper.ZERO;
		}
		if (acct.getCurProject() != null) {
			fullNumber = acct.getCurProject().getFullOrgUnit().getLongNumber() + "!" + acct.getCurProject().getLongNumber();
		} else {
			fullNumber = acct.getFullOrgUnit().getLongNumber();
		}

		String longNumber = acct.getLongNumber();
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(FCostAmount) sumCostAmount from T_Aim_CostEntry entry inner join T_Aim_AimCost head " + "on entry.FHeadId=head.FId and FISLastVersion=1 inner join ");
		sql.append(FDCHelper.getFullAcctSql()).append(" on entry.FCostAccountId=costAcct.fid ");
		sql.append(" where (costAcct.FLongNumber = '").append(longNumber).append("'").append(" or costAcct.FLongNumber like '").append(longNumber).append("!%' ").append(
				" or costAcct.FLongNumber like '%!").append(longNumber).append("!%') ");
		sql.append("and (costAcct.FullNumber like '").append(fullNumber).append("!%' ").append(" or costAcct.FullNumber like '%!").append(fullNumber).append(
				"!%') And costAcct.FIsLeaf=1 And costAcct.isleafProject=1");
		IRowSet rs = null;
		if (ctx == null) {
			rs = SQLExecutorFactory.getRemoteInstance(sql.toString()).executeSQL();
		} else {
			rs = SQLExecutorFactory.getLocalInstance(ctx, sql.toString()).executeSQL();
		}

		while (rs.next()) {
			if (rs.getBigDecimal("sumCostAmount") != null) {
				aimCost = rs.getBigDecimal("sumCostAmount");
			}
		}
		return aimCost;
	}

	private void initAcct(FilterInfo acctFilter) throws BOSException {
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		sel.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sel.add(new SelectorItemInfo("curProject.longNumber"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit.longNumber"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(acctFilter);
		CostAccountCollection accts = null;
		if (ctx == null) {
			accts = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		} else {
			accts = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		}

		for (int i = 0; i < accts.size(); i++) {
			CostAccountInfo info = accts.get(i);
			this.acctMap.put(info.getId().toString(), info);
		}
	}
 
	public CostEntryCollection getCostEntrys(String acctId) {
		Map aimRow = this.aimCostXTable.getRow(acctId);
		CostEntryCollection aimEntrys = new CostEntryCollection();
		if (aimRow != null) {
			aimEntrys = (CostEntryCollection) aimRow.get("entryColl");
		}
		return aimEntrys;
	}

	public AIMAimCostProductSplitEntryCollection getProductSplitEntry(String entryId) {
		return (AIMAimCostProductSplitEntryCollection) this.aimEntryProductMap.get(entryId);
	}

	public Map getProductMap(String acctId) throws BOSException {
		Map productAim = new HashMap();
		if (this.productTypeGetter.getProductTypeSize() == 0) {
			return productAim;
		}
		CostEntryCollection aimEntrys = this.getCostEntrys(acctId);
		for (int i = 0; i < aimEntrys.size(); i++) {
			CostEntryInfo info = aimEntrys.get(i);
			// AIMAimCostProductSplitEntryCollection splits =
			// createSetting(info);
			Map entryData = this.getAimProductData(info);
			Set entrySet = entryData.keySet();
			for (Iterator iter = entrySet.iterator(); iter.hasNext();) {
				String proId = (String) iter.next();
				BigDecimal amount = (BigDecimal) entryData.get(proId);
				if (productAim.containsKey(proId)) {
					BigDecimal value = (BigDecimal) productAim.get(proId);
					productAim.put(proId, value.add(amount));
				} else {
					productAim.put(proId, amount);
				}

			}
		}

		return productAim;
	}

	public AIMAimCostProductSplitEntryCollection createSetting(CostEntryInfo info) {
		AIMAimCostProductSplitEntryCollection splits = new AIMAimCostProductSplitEntryCollection();
		AIMAimCostProductSplitEntryCollection cashe = this.getProductSplitEntry(info.getId().toString());
		if (cashe != null) {
			splits.addCollection(cashe);
		}
		if (info.getProduct() != null) {
			AIMAimCostProductSplitEntryInfo split = new AIMAimCostProductSplitEntryInfo();
			split.setProduct(info.getProduct());
			split.setDirectAmount(info.getCostAmount());
			splits.clear();
			splits.add(split);
		}
		return splits;
	}

	public boolean isFullApportion(String acctId) throws BOSException {
		BigDecimal aimCost = this.getAimCost(acctId);
		Map productMap = this.getProductMap(acctId);
		Set set = productMap.keySet();
		BigDecimal sum = FDCHelper.ZERO;
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String proId = (String) iter.next();
			BigDecimal amount = (BigDecimal) productMap.get(proId);
			aimCost = aimCost.subtract(amount);
			sum = sum.add(amount);
		}
		if (sum.compareTo(FDCHelper.ZERO) != 0 && aimCost.compareTo(FDCHelper.ZERO) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * �õ�Ŀ��ɱ���Ʒ��̯����
	 * 
	 * @param entry
	 * @param splits
	 * @return
	 * @throws BOSException
	 */
	private Map getAimProductData(CostEntryInfo entry, AIMAimCostProductSplitEntryCollection splits) throws BOSException {
		if (splits.size() <= 0) {
			return new HashMap();
		}
		System.out.println("entryId" + entry.getId() + "\t " + entry.getCostAmount());
		Map calculateData = new HashMap();
		AIMAimCostProductSplitEntryInfo firstSplit = splits.get(0);
		ApportionTypeInfo apportionType = firstSplit.getApportionType();
		BigDecimal amount = entry.getCostAmount();
		// �ȿ۵�ֱ�ӷ���
		for (int j = 0; j < splits.size(); j++) {
			firstSplit = splits.get(j);
			String productSplitId = firstSplit.getProduct().getId().toString();
			calculateData.put(productSplitId, firstSplit.getDirectAmount());
			amount = amount.subtract(firstSplit.getDirectAmount());
		}
		// ���ݷ�̯���Ͳ��
		if (splits.size() > 1) {
			Map areaData = new HashMap();
			BigDecimal areaSum = FDCHelper.ZERO;
			for (int j = 0; j < splits.size(); j++) {
				String productSplitId = splits.get(j).getProduct().getId().toString();
				if (splits.get(j).getDescription() == null) {
					areaData.put(productSplitId, FDCHelper.ZERO);
					continue;
				}
				BigDecimal appValue = this.productTypeGetter.getProductApprotion(productSplitId, apportionType.getId().toString());
				areaData.put(productSplitId, appValue);
				areaSum = areaSum.add(appValue);
			}
			String lastProductId = null;
			BigDecimal restAmount = amount.add(FDCHelper.ZERO);
			Set keySet = new TreeSet(calculateData.keySet());
			for (Iterator iter = keySet.iterator(); iter.hasNext();) {
				String prodId = (String) iter.next();
				BigDecimal splitAmount = FDCHelper.ZERO;
				if (areaSum.compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal area = (BigDecimal) areaData.get(prodId);
					if (area == null) {
						area = FDCHelper.ZERO;
					}
					splitAmount = amount.multiply(area).divide(areaSum, 2, BigDecimal.ROUND_HALF_UP);
					if (splitAmount.compareTo(FDCHelper.ZERO) != 0) {
						lastProductId = prodId;
						restAmount = restAmount.subtract(splitAmount);
					}
				} else {
					lastProductId = prodId;
				}
				BigDecimal directAmount = (BigDecimal) calculateData.get(prodId);
				BigDecimal finalAmount = directAmount.add(splitAmount);
				calculateData.put(prodId, finalAmount);
			}
			if (lastProductId != null && restAmount.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal calAmount = (BigDecimal) calculateData.get(lastProductId);
				BigDecimal finalAmount = calAmount.add(restAmount);
				calculateData.put(lastProductId, finalAmount);
			}
		} else {
			// ��һ��Ʒֱ�ӷ�̯
			String productSplitId = splits.get(0).getProduct().getId().toString();
			calculateData.put(productSplitId, entry.getCostAmount());

		}
		return calculateData;
	}

	private void initAimCostData(String objectId) throws BOSException, SQLException {
		aimCostXTable = new XTable();
		aimCostXTable.addColumn("aimCostSum");
		aimCostXTable.addColumn("entryColl");
		EntityViewInfo aimView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		aimView.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId", objectId));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion", new Integer(1)));
		/********��ӹ��ˣ��������FDC_PARAM_AIMCOSTAUDIT���������������������	-by neo********/
		try {
			if(ctx!=null){
				if(FDCUtils.getDefaultFDCParamByKey(ctx, 
						ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_AIMCOSTAUDIT)){
					filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
				}
			}else{
				if(FDCUtils.getDefaultFDCParamByKey(null, 
						SysContext.getSysContext().getCurrentFIUnit().getId().toString(), FDCConstants.FDC_PARAM_AIMCOSTAUDIT))
					filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		aimView.getSelector().add("costEntry.*");
		// aimView.getSelector().add("costEntry.costAmount");
		aimView.getSelector().add("costEntry.costAccount.id");
		aimView.getSelector().add(new SelectorItemInfo("costEntry.product.id"));
		aimView.getSelector().add(new SelectorItemInfo("costEntry.product.name"));
		aimView.getSelector().add(new SelectorItemInfo("costEntry.product.number"));

		AimCostCollection aimCostCollection = null;
		if (ctx == null) {
			aimCostCollection = AimCostFactory.getRemoteInstance().getAimCostCollection(aimView);
		} else {
			aimCostCollection = AimCostFactory.getLocalInstance(ctx).getAimCostCollection(aimView);
		}

		if (aimCostCollection.size() >= 1) {
			AimCostInfo aimInfo = aimCostCollection.get(0);
			CostEntryCollection costEntrys = aimInfo.getCostEntry();
			/*
			 * EntityViewInfo entryView = new EntityViewInfo(); filter = new
			 * FilterInfo(); entryView.setFilter(filter);
			 * filter.getFilterItems().add( new FilterItemInfo("head",
			 * aimInfo.getId().toString())); entryView.getSelector().add(new
			 * SelectorItemInfo("*")); entryView.getSelector().add(new
			 * SelectorItemInfo("product.*")); entryView.getSorter().add(new
			 * SorterItemInfo("costAccount")); entryView.getSorter().add(new
			 * SorterItemInfo("entryName")); if(ctx == null) { costEntrys =
			 * CostEntryFactory
			 * .getRemoteInstance().getCostEntryCollection(entryView); } else {
			 * costEntrys = CostEntryFactory
			 * .getLocalInstance(ctx).getCostEntryCollection(entryView); }
			 */
			Set costEntryIdSet = new HashSet();
			for (int i = 0; i < costEntrys.size(); i++) {
				CostEntryInfo entry = costEntrys.get(i);
				costEntryIdSet.add(entry.getId().toString());
				costEntryMap.put(entry.getId().toString(), entry);
				CostAccountInfo costAccount = entry.getCostAccount();
				if (costAccount == null || costAccount.getId() == null) {
					continue;
				}
				BigDecimal value = entry.getCostAmount();
				if (value == null) {
					value = FDCHelper.ZERO;
				}
				Map row = aimCostXTable.getRow(costAccount.getId().toString());
				if (row != null) {
					BigDecimal sum = (BigDecimal) row.get("aimCostSum");
					row.put("aimCostSum", sum.add(value));
					CostEntryCollection coll = (CostEntryCollection) row.get("entryColl");
					coll.add(entry);

				} else {
					Map newRow = aimCostXTable.addRow(costAccount.getId().toString());
					CostEntryCollection newColl = new CostEntryCollection();
					newColl.add(entry);
					newRow.put("aimCostSum", value);
					newRow.put("entryColl", newColl);
				}
			}
			// ��ʼ��Ŀ��ɱ��������
			if (costEntryMap.size() > 0) {
				EntityViewInfo view = new EntityViewInfo();
				view.getSelector().clear();
				view.getSelector().add("*");
				view.getSelector().add("product.id");
				// view.getSelector().add("apportionType.*");
				view.getSelector().add("apportionType.id");
				view.getSelector().add("apportionType.number");
				view.getSelector().add("apportionType.name");
				filter = new FilterInfo();
				view.setFilter(filter);
				filter.getFilterItems().add(new FilterItemInfo("costEntryId", costEntryIdSet, CompareType.INCLUDE));
				// String innerSql="(select fid from T_AIM_CostEntry where
				// FHeadID='"+aimInfo.getId().toString()+"')";
				// filter.getFilterItems().add(new FilterItemInfo("costEntryId",
				// innerSql, CompareType.INNER));
				AIMAimCostProductSplitEntryCollection splits = null;
				if (ctx == null) {
					splits = AIMAimCostProductSplitEntryFactory.getRemoteInstance().getAIMAimCostProductSplitEntryCollection(view);
				} else {
					splits = AIMAimCostProductSplitEntryFactory.getLocalInstance(ctx).getAIMAimCostProductSplitEntryCollection(view);
				}
				aimEntryProductMap.clear();
				for (int i = 0; i < splits.size(); i++) {
					AIMAimCostProductSplitEntryInfo info = splits.get(i);
					String costEntryId = info.getCostEntryId();
					if (aimEntryProductMap.containsKey(costEntryId)) {
						AIMAimCostProductSplitEntryCollection coll = (AIMAimCostProductSplitEntryCollection) aimEntryProductMap.get(costEntryId);
						coll.add(info);
					} else {
						AIMAimCostProductSplitEntryCollection newColl = new AIMAimCostProductSplitEntryCollection();
						newColl.add(info);
						aimEntryProductMap.put(costEntryId, newColl);
					}
				}
			}
		}
	}

	private CostEntryInfo getCostEntry(String costEntryid) {
		return (CostEntryInfo) this.costEntryMap.get(costEntryid);
	}

	public void initProductSplitData() throws BOSException {
		if (aimProductDataMap == null) {
			aimProductDataMap = new HashMap();
		} else {
			aimProductDataMap.clear();
		}
		for (Iterator iter = costEntryMap.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			AIMAimCostProductSplitEntryCollection splits = getProductSplitEntry(key);
			final CostEntryInfo costEntry = getCostEntry(key);
			Map calculateData = new HashMap();
			if (costEntry.getProduct() != null) {
				// ֱ��ָ��
				calculateData.put(costEntry.getProduct().getId().toString(), costEntry.getCostAmount());
				aimProductDataMap.put(key, calculateData);
			} else {
				if (splits == null) {
					aimProductDataMap.put(key, calculateData);
				} else {
					aimProductDataMap.put(key, getAimProductData(costEntry, splits));
				}
			}
		}
	}

	private HashMap aimProductDataMap = null;

	public Map getAimProductData(CostEntryInfo entry) throws BOSException {
		if (aimProductDataMap == null) {
			initProductSplitData();
		}
		Map map = (Map) aimProductDataMap.get(entry.getId().toString());
		return map != null ? map : new HashMap();
	}
}
