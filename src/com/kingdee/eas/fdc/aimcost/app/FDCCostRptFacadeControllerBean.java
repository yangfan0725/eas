package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostMap;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.AimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.AimProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.CostDataGetter;
import com.kingdee.eas.fdc.aimcost.CostMap;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DyProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotFactory;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostMap;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.FullDynamicCostMap;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.IDynamicCost;
import com.kingdee.eas.fdc.aimcost.ProductAimCostMap;
import com.kingdee.eas.fdc.aimcost.ProductDynamicCostMap;
import com.kingdee.eas.fdc.aimcost.ProjectCostChangeLogFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.CostClosePeriodFacade;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;


public class FDCCostRptFacadeControllerBean extends AbstractFDCCostRptFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.FDCCostRptFacadeControllerBean");

	protected AimCostMap _getAimCost(Context ctx, String orgOrPrjId, IObjectValue period) throws BOSException, EASBizException {
		return null;
	}

	protected ProductAimCostMap _getProductAimCost(Context ctx, String prjId, ProjectStageEnum indexProjectStage, IObjectValue period) throws BOSException, EASBizException {
		// indexProjectStage=ProjectStageEnum.DYNCOST;
		ProductAimCostMap productAimCostMap = new ProductAimCostMap();
		final TreeModel costAcctTree = CostMap.createAcctTreeMode(prjId, ctx);
		AimProductTypeGetter productTypeGetter = new AimProductTypeGetter(ctx, prjId, indexProjectStage);
		AimCostSplitDataGetter aimGetter = new AimCostSplitDataGetter(ctx, prjId);
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) costAcctTree.getRoot();
		int maxLevel = root.getDepth() - 1;
		productAimCostMap.setMaxLevel(maxLevel);
		// productAimCostMap.put("productTypeGetter", productTypeGetter);
		for (int i = 0; i < root.getChildCount(); i++) {
			productAimCostMap.buildAdapter((DefaultMutableTreeNode) root.getChildAt(i), productTypeGetter, aimGetter);
		}

		return productAimCostMap;
	}

	protected FullDynamicCostMap _getFullDynamicCost(Context ctx, String prjId, IObjectValue period) throws BOSException, EASBizException {
		FullDynamicCostMap fullDynamicCostMap = new FullDynamicCostMap();
		HappenDataGetter happenGetter = new HappenDataGetter(ctx, prjId, true, true, false);
		fullDynamicCostMap.setHappenDataGetter(happenGetter);
		Map map = ProjectHelper.getIndexValue(ctx, prjId, new String[] { ApportionTypeInfo.buildAreaType, ApportionTypeInfo.sellAreaType }, ProjectStageEnum.DYNCOST, false);
		BigDecimal buildArea = (BigDecimal) map.get(prjId + " " + ApportionTypeInfo.buildAreaType);
		BigDecimal sellArea = (BigDecimal) map.get(prjId + " " + ApportionTypeInfo.sellAreaType);

		fullDynamicCostMap.setDynBuildApp(buildArea);
		fullDynamicCostMap.setDynSellApp(sellArea);

		fullDynamicCostMap.setAimCostMap(getAcctAimCostData(ctx, prjId));
		fullDynamicCostMap.setDynamicCostMapp(getAcctAdjustCostData(ctx, prjId));
		
		
		Map noHappen=new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select cost.fcostAccountId as acctid,sum(fcontractAssign) as amount from T_CON_ProgrammingContracCost cost");
		builder.appendSql(" left join T_CON_ProgrammingContract pc on pc.fid=cost.fcontractId left join T_CON_Programming pro on pro.fid=pc.fProgrammingId");
		builder.appendSql(" where pc.fisCiting=0 and pro.FIsLatest=1 and pro.fstate='4AUDITTED' and pro.fprojectID ='"+prjId+"' group by cost.fcostAccountId");
		IRowSet rowSet = builder.executeQuery();
		try {
			while (rowSet.next()) {
				noHappen.put(rowSet.getString("acctid"), rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fullDynamicCostMap.setNoHappen(noHappen);
		return fullDynamicCostMap;
	}

	protected DynamicCostMap _getDynamicCost(Context ctx, String prjId, IObjectValue period, boolean isLeaf) throws BOSException, EASBizException {
		TimeTools.getInstance().reset();
		TimeTools.getInstance().msValuePrintln("----------start----------");
//		//Ϊ�½�����׼���ڼ�
//		ctx.put("period",period);
		DynamicCostMap map = new DynamicCostMap();
		AimProductTypeGetter aimProductTypeGetter = new AimProductTypeGetter(ctx, prjId, ProjectStageEnum.DYNCOST);
		DyProductTypeGetter dyProductTypeGetter = new DyProductTypeGetter(ctx, prjId);
		TimeTools.getInstance().msValuePrintln("before happenGetter");
		HappenDataGetter happenGetter = new HappenDataGetter(ctx, prjId, false, false, true);
		TimeTools.getInstance().msValuePrintln("after happenGetter");
		// ����get��ָ��һ�£���ʼ��һ������ָ��
		dyProductTypeGetter.setDyApportionMap(aimProductTypeGetter.getAimApportionMap());
		TimeTools.getInstance().msValuePrintln("setDyApportionMap");
		AimCostSplitDataGetter aimGetter = new AimCostSplitDataGetter(ctx, prjId, aimProductTypeGetter);
		TimeTools.getInstance().msValuePrintln("initProductSplitData");
		aimGetter.initProductSplitData();
		TimeTools.getInstance().msValuePrintln("initProductSplitData");
		DyCostSplitDataGetter dyGetter = new DyCostSplitDataGetter(ctx, prjId, aimGetter, happenGetter, dyProductTypeGetter);
		dyGetter.initProductSplitData();
		TimeTools.getInstance().msValuePrintln("DyCostSplitDataGetter");
		map.setDyCostSplitDataGetter(dyGetter);
		try {
			setDynamicCostTotalData(ctx, prjId, map);
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		TimeTools.getInstance().msValuePrintln("after setDynamicCostTotalData");
		Map acctMap = new HashMap();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		sel.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sel.add(new SelectorItemInfo("curProject.longNumber"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit.longNumber"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		FilterInfo acctFilter = new FilterInfo();

		if (CostDataGetter.isCurProject(prjId)) {
			acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", prjId));
		} else {
			acctFilter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", prjId));
		}
		acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		acctFilter.appendFilterItem("isLeaf", Boolean.TRUE);
		view.setFilter(acctFilter);
		CostAccountCollection accts = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		for (int i = 0; i < accts.size(); i++) {
			CostAccountInfo info = accts.get(i);
			acctMap.put(info.getId().toString(), info);
		}
		map.setAcctMap(acctMap);
		if (!isLeaf) {
			try {
				map.setAdjustMap(getSumAdjustCol(ctx, acctMap));
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		TimeTools.getInstance().msValuePrintln("------------ end ----------");
		return map;
	}

	/**
	 * 
	 * @param acctMap
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	// TODO ��ʱ���Ƶ�����˵�,���ܴ��Ľ� ������ȡ��by sxhong 2008-04-3 13:36:35
	private Map getSumAdjustCol(Context ctx, Map acctMap) throws BOSException, SQLException {
		Map adjustMap = new HashMap();
		for (Iterator iter = acctMap.values().iterator(); iter.hasNext();) {
			CostAccountInfo costAcct = (CostAccountInfo) iter.next();
			StringBuffer innerSql = new StringBuffer();
			innerSql.append("select fid from " + FDCHelper.getFullAcctSql() + " where ");
			String fullNumber = "";
			CostAccountInfo acct = (CostAccountInfo) acctMap.get(costAcct.getId().toString());
			if (acct.getCurProject() != null) {
				fullNumber = acct.getCurProject().getFullOrgUnit().getLongNumber() + "!" + acct.getCurProject().getLongNumber();
			} else {
				fullNumber = acct.getFullOrgUnit().getLongNumber();
			}

			String longNumber = costAcct.getLongNumber();
			innerSql.append(" (FLongNumber ='").append(longNumber).append("'").append(" or FLongNumber like '").append(longNumber).append("!%' ").append(" or FLongNumber like		 '%!").append(
					longNumber).append("!%') ");
			innerSql.append("and (FullNumber =				 '").append(fullNumber).append("'").append(" or FullNumber like '").append(fullNumber).append("!%' ").append(" or FullNumber like '%!").append(
					fullNumber).append("!%') And costAcct.FIsLeaf=1 And costAcct.isleafProject=1 ");

			AdjustRecordEntryCollection adjusts = new AdjustRecordEntryCollection();
			String sql = "select FCostAmount,FProductId from T_AIM_AdjustRecordEntry inner join T_AIM_DynamicCost "
					+ "on  T_AIM_AdjustRecordEntry.FParentID= T_AIM_DynamicCost.fid where T_AIM_DynamicCost.FAccountId in (" + innerSql.toString() + ")";
			IRowSet rs = SQLExecutorFactory.getLocalInstance(ctx, sql).executeSQL();
			while (rs.next()) {
				AdjustRecordEntryInfo info = new AdjustRecordEntryInfo();
				info.setCostAmount(rs.getBigDecimal("FCostAmount"));
				if (rs.getString("FProductId") != null) {
					ProductTypeInfo product = new ProductTypeInfo();
					product.setId(BOSUuid.read(rs.getString("FProductId")));
					info.setProduct(product);
				}
				adjusts.add(info);
			}
			adjustMap.put(costAcct.getId().toString(), adjusts);
		}
		return adjustMap;
	}

	private Map _getSumAdjustCol(Context ctx, String prjOrgId, Map acctMap) throws BOSException, SQLException {
		// ��ȡ���е���Ŀ
		Set prjSet = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if (CostDataGetter.isCurProject(prjOrgId)) {
			builder
					.appendSql("select b.fid as fid from T_FDC_CurProject a ,T_FDC_CurProject b where a.fid=? and b.fisleaf=1 and b.fisenabled=1 and (charindex(a.flongnumber||'!',b.flongnumber)=1 or b.fid=a.fid)");
			builder.addParam(prjOrgId);
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.size() == 0) {
				return new HashMap();
			}
			prjSet = new HashSet(rowSet.size());
			while (rowSet.next()) {
				prjSet.add(rowSet.getString("fid"));
			}
		} else {
			builder.clear();
		}
		Map adjustMap = new HashMap();
		for (Iterator iter = acctMap.values().iterator(); iter.hasNext();) {
			CostAccountInfo costAcct = (CostAccountInfo) iter.next();
			StringBuffer innerSql = new StringBuffer();
			innerSql.append("select fid from " + FDCHelper.getFullAcctSql() + " where ");
			String fullNumber = "";
			CostAccountInfo acct = (CostAccountInfo) acctMap.get(costAcct.getId().toString());
			if (acct.getCurProject() != null) {
				fullNumber = acct.getCurProject().getFullOrgUnit().getLongNumber() + "!" + acct.getCurProject().getLongNumber();
			} else {
				fullNumber = acct.getFullOrgUnit().getLongNumber();
			}

			String longNumber = costAcct.getLongNumber();
			innerSql.append(" (FLongNumber ='").append(longNumber).append("'").append(" or FLongNumber like '").append(longNumber).append("!%' ").append(" or FLongNumber like		 '%!").append(
					longNumber).append("!%') ");
			innerSql.append("and (FullNumber =				 '").append(fullNumber).append("'").append(" or FullNumber like '").append(fullNumber).append("!%' ").append(" or FullNumber like '%!").append(
					fullNumber).append("!%') And costAcct.FIsLeaf=1 And costAcct.isleafProject=1 ");

			AdjustRecordEntryCollection adjusts = new AdjustRecordEntryCollection();
			String sql = "select FCostAmount,FProductId from T_AIM_AdjustRecordEntry inner join T_AIM_DynamicCost "
					+ "on  T_AIM_AdjustRecordEntry.FParentID= T_AIM_DynamicCost.fid where T_AIM_DynamicCost.FAccountId in (" + innerSql.toString() + ")";
			IRowSet rs = SQLExecutorFactory.getLocalInstance(ctx, sql).executeSQL();
			while (rs.next()) {
				AdjustRecordEntryInfo info = new AdjustRecordEntryInfo();
				info.setCostAmount(rs.getBigDecimal("FCostAmount"));
				if (rs.getString("FProductId") != null) {
					ProductTypeInfo product = new ProductTypeInfo();
					product.setId(BOSUuid.read(rs.getString("FProductId")));
					info.setProduct(product);
				}
				adjusts.add(info);
			}
			adjustMap.put(costAcct.getId().toString(), adjusts);
		}
		return adjustMap;
	}

	/**
	 * ��ȡδ�������
	 * @param ctx
	 * @param prjOrgId
	 * @param map
	 * @throws BOSException
	 * @throws SQLException
	 * @throws EASBizException 
	 */
	private void setDynamicCostTotalData(Context ctx, String prjOrgId, DynamicCostMap map) throws BOSException, SQLException, EASBizException {
		String id = prjOrgId;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("select b.fid as fid from T_FDC_CurProject a ,T_FDC_CurProject b where a.fid=? and b.fisleaf=1 and b.fisenabled=1 and (charindex(a.flongnumber||'!',b.flongnumber)=1 or b.fid=a.fid)");
		builder.addParam(id);
		IRowSet rowSet = builder.executeQuery();
		if (rowSet.size() == 0) {
			return;
		}
		Set prjIds = new HashSet(rowSet.size());
		while (rowSet.next()) {
			prjIds.add(rowSet.getString("fid"));
		}
		
		//��ȡ���� ��FDC214_SPLITSUBMITBILL���Ƿ��������ύ״̬�ĵ��ݡ���ֵ
		boolean canSplitSubmit = FDCHelper.getBooleanValue4FDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_SPLITSUBMIT);

		// δ��ֺ�ͬ���(δ����)��
		builder.clear();
		builder.appendSql(" select sum(amount) as amount  \n");
		builder.appendSql(" from ( \n");
		builder.appendSql(" SELECT sum(famount) AMOUNT FROM T_CON_ContractBill where ");
		builder.appendParam("fcurprojectid", prjIds.toArray());
		builder.appendSql(" AND (fhassettled = 0 or fhassettled is null) AND FIsCostSplit = 1 AND (FSplitState IS NULL OR fsplitstate = '1NOSPLIT') ");
		builder.appendSql(" AND FIsAmtWithoutCost = 0 ");
		if (canSplitSubmit) {
			builder.appendSql(" AND fstate NOT IN ('1SAVED', '9INVALID') \n");
		} else {
			builder.appendSql(" AND fstate NOT IN ('1SAVED', '2SUBMITTED', '3AUDITTING','9INVALID') \n");
		}

		builder.appendSql(" union all  \n");
		
		// ��� 
		//union all ʱ�ֶα�����������ѯһ�� by hpw
//		builder.appendSql(" select case when fhassettled =0 then  famount else fbalanceamount end  as famount from T_Con_ContractChangeBill change  \n");
		builder.appendSql(" select case when change.fhassettled =0 then  change.famount else change.fbalanceamount end  as amount from T_Con_ContractChangeBill change  \n");
		builder.appendSql(" inner join T_CON_ContractBill contract on change.FContractBillID = contract.FID ");
		builder.appendSql(" where ");
		builder.appendParam("change.fcurprojectid", prjIds.toArray());
		builder.appendSql(" and  (change.fisconsetted=0 or change.fisconsetted is null) and change.fiscostsplit=1 and (change.fsplitstate IS NULL OR change.fsplitstate = '1NOSPLIT') ");
		builder.appendSql(" AND contract.FIsAmtWithoutCost = 0 ");
		if (canSplitSubmit) {
			builder.appendSql(" AND change.fstate NOT IN ('1SAVED', '9INVALID') \n");
		} else {
			builder.appendSql(" AND change.fstate NOT IN ('1SAVED', '2SUBMITTED', '3AUDITTING','9INVALID') \n");
		}

		builder.appendSql(" union all \n");
		// ����
		builder.appendSql(" SELECT sum(settle.fsettleprice) AMOUNT FROM T_CON_ContractSettlementBill SETTLE  \n");
		builder.appendSql(" inner join T_CON_ContractBill contract on SETTLE.FContractBillID = contract.FID ");
		builder.appendSql(" where ");
		builder.appendParam("SETTLE.fcurprojectid", prjIds.toArray());
		builder.appendSql(" and settle.fiscostsplit=1 and (settle.fsplitstate IS NULL OR settle.fsplitstate = '1NOSPLIT')  \n");
		builder.appendSql(" AND contract.FIsAmtWithoutCost = 0 ");
		//���㻹������ʵʵȡ���������ݰɡ� ��Ϊδ�����Ľ��㵥���Ѿ�ͳ���˺�ͬ�ͱ��
		builder.appendSql(" AND SETTLE.fstate = '4AUDITTED' \n");
		
		builder.appendSql(" union all \n");
		//���ı�
		builder.appendSql(" select sum(contractNoText.famount) as amount from T_CON_ContractWithoutText contractNoText ");
		builder.appendSql(" inner join T_CAS_PaymentBill payment on payment.fContractBillId = contractNoText.FID ");
		builder.appendSql(" where");
		builder.appendParam("contractNoText.fcurprojectid", prjIds.toArray());
		builder.appendSql(" and contractNoText.fiscostsplit=1 and (contractNoText.fsplitstate is null or contractNoText.fsplitstate = '1NOSPLIT')  \n");
		if (canSplitSubmit) {
			builder.appendSql(" AND payment.fbillstatus NOT IN (10) \n");
		} else {
			builder.appendSql(" AND payment.fbillstatus NOT IN (10, 6, 11) \n");
		}
		builder.appendSql(" ) t\n");

		rowSet = builder.executeQuery();
		if (rowSet != null && rowSet.size() == 1) {
			rowSet.next();
			map.setNoSplitAmt(rowSet.getBigDecimal("amount"));
		}
	}

	protected ProductDynamicCostMap _getProductDynamicCost(Context ctx, String prjId, IObjectValue period) throws BOSException, EASBizException {
		String comId  = "";
		if(CostDataGetter.isCurProject(prjId)){
			CurProjectInfo prjInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(BOSUuid.read(prjId)));
			comId = prjInfo.getFullOrgUnit().getId().toString();			
		}else{
			comId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		}
		// �Ƿ����ò���һ�廯
		boolean finacial = false;
		HashMap param = FDCUtils.getDefaultFDCParam(ctx, comId);
		// �Ƿ����ò���һ�廯
		if (param.get(FDCConstants.FDC_PARAM_FINACIAL) != null) {
			finacial = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_FINACIAL).toString())
					.booleanValue();
		}
		ctx.put("finacial", Boolean.valueOf(finacial));
		if (period == null) {
			return getProductDynamicCost(ctx, prjId);
		} else {
			return getPeriodProductDynamicCost(ctx, prjId, (PeriodInfo) period);
		}
	}

	private ProductDynamicCostMap getProductDynamicCost(Context ctx, String prjId) throws BOSException, EASBizException {
		ProductDynamicCostMap map = new ProductDynamicCostMap();
		AimProductTypeGetter aimProductTypeGetter = new AimProductTypeGetter(ctx, prjId, ProjectStageEnum.DYNCOST);
		DyProductTypeGetter dyProductTypeGetter = new DyProductTypeGetter(ctx, prjId);
		HappenDataGetter happenGetter = new HappenDataGetter(ctx, prjId, false, false, true);
		// ����get��ָ��һ�£���ʼ��һ������ָ��
		dyProductTypeGetter.setDyApportionMap(aimProductTypeGetter.getAimApportionMap());

		ctx.put("period",null);
		
		AimCostSplitDataGetter aimGetter = new AimCostSplitDataGetter(ctx, prjId, aimProductTypeGetter);
		aimGetter.initProductSplitData();
		DyCostSplitDataGetter dyGetter = new DyCostSplitDataGetter(ctx, prjId, aimGetter, happenGetter, dyProductTypeGetter);
		dyGetter.initProductSplitData();

		map.setDyCostSplitDataGetter(dyGetter);

//		Map aimSellApportionMap = ProjectHelper.getIndexValueByProjProd(ctx, prjId, ApportionTypeInfo.sellAreaType, ProjectStageEnum.AIMCOST);
//		map.setAimSellApportionMap(aimSellApportionMap);
		//Ŀ��ɱ����۵����ö�ָ̬��  by sxhong
		map.setAimSellApportionMap(aimProductTypeGetter.getAimApportionMap());
		return map;
	}

	private ProductDynamicCostMap getPeriodProductDynamicCost(Context ctx, String prjId, PeriodInfo period) throws BOSException, EASBizException {
		ProductDynamicCostMap map = new ProductDynamicCostMap();
		// ֱ���ñ��������
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("projectId", prjId);
		view.getFilter().appendFilterItem("period.id", period.getId().toString());
		// view.getFilter().appendFilterItem("period.id",
		// "Nkv1KAEXEADgAAfzwKgTBYI4jEw=");
		view.getFilter().appendFilterItem("savedType", CostMonthlySaveTypeEnum.FINANCEMONTHLY_VALUE);
		view.getSelector().add("*");
		view.getSelector().add("approtionTypeId");
		view.getSelector().add("proTypEntries.*");
		ctx.put("period",period);
		final DynCostSnapShotCollection snapColl = DynCostSnapShotFactory.getLocalInstance(ctx).getDynCostSnapShotCollection(view);
		Map dynSnapMap = new HashMap();
		Set appSet = new HashSet();
		for (int i = 0; i < snapColl.size(); i++) {
			DynCostSnapShotInfo info = snapColl.get(i);
			String key = info.getCostAccountId().toString();
			for (Iterator iter = info.getProTypEntries().iterator(); iter.hasNext();) {
				DynCostSnapShotProTypEntryInfo entry = (DynCostSnapShotProTypEntryInfo) iter.next();
				final String productTypeId = entry.getProductTypeId().toString();
				// System.out.println("entry:"+entry);
				dynSnapMap.put(key + productTypeId, entry);
			}
			info.getProTypEntries().clear();
			dynSnapMap.put(key, info);
			if (info.getApprotionTypeId() != null) {
				appSet.add(info.getApprotionTypeId().toString());
			}
		}
		if (appSet.size() > 0) {
			view = new EntityViewInfo();
			view.setFilter(new FilterInfo());
			view.getFilter().getFilterItems().add(new FilterItemInfo("id", appSet, CompareType.INCLUDE));
			view.getSelector().add("*");
			final ApportionTypeCollection apportionTypeCollection = ApportionTypeFactory.getLocalInstance(ctx).getApportionTypeCollection(view);
			HashMap appMap = new HashMap();
			for (int i = 0; i < apportionTypeCollection.size(); i++) {
				final ApportionTypeInfo apportionTypeInfo = apportionTypeCollection.get(i);
				appMap.put(apportionTypeInfo.getId().toString(), apportionTypeInfo);
			}
			map.put("appMap", appMap);
		} else {
			map.put("appMap", new HashMap());
		}

		map.put("dynSnapMap", dynSnapMap);
		DyProductTypeGetter dyProductTypeGetter = new DyProductTypeGetter(ctx, prjId);
		map.put("dyProductTypeGetter", dyProductTypeGetter);
		
		AimProductTypeGetter aimProductTypeGetter = new AimProductTypeGetter(ctx, prjId, ProjectStageEnum.DYNCOST);
		HappenDataGetter happenGetter = new HappenDataGetter(ctx, prjId, false, false, true);
		// ����get��ָ��һ�£���ʼ��һ������ָ��
		dyProductTypeGetter.setDyApportionMap(aimProductTypeGetter.getAimApportionMap());

		AimCostSplitDataGetter aimGetter = new AimCostSplitDataGetter(ctx, prjId, aimProductTypeGetter);
		aimGetter.initProductSplitData();
		DyCostSplitDataGetter dyGetter = new DyCostSplitDataGetter(ctx, prjId, aimGetter, happenGetter, dyProductTypeGetter);
		dyGetter.initProductSplitData();

		map.setDyCostSplitDataGetter(dyGetter);
		
		/**
		 * ȡ�ڼ�ָ��
		 */
		view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("forCostApportion", Boolean.TRUE));
		filter.mergeFilter(ApportionTypeInfo.getCUFilter(ContextUtil.getCurrentCtrlUnit(ctx)), "and");
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("name");
		view.getSorter().add(new SorterItemInfo("number"));
		ApportionTypeCollection apportionColl = ApportionTypeFactory.getLocalInstance(ctx)
				.getApportionTypeCollection(view);
		Set appIds = new HashSet();
		for(int i = 0 ; i < apportionColl.size();i++){
			ApportionTypeInfo appInfo = apportionColl.get(i);
			appIds.add(appInfo.getId().toString());
		}
		Map aimSellApportionMap = ProjectHelper.getIndexValueByProjProd(ctx, prjId, appIds, ProjectStageEnum.DYNCOST,period);
		map.setAimSellApportionMap(aimSellApportionMap);
		return map;
	}

	protected Map _submitAIMAimProductCost(Context ctx, Map costEntryMap) throws BOSException, EASBizException {
		// ��ɾ���������
		if (costEntryMap == null || costEntryMap.size() <= 0) {
			return null;
		}
		// ����Ҫ�����ڼ�Ĺ��˹���
		final Set keySet = costEntryMap.keySet();
		// ���
		final BOSObjectType type = new AIMAimCostProductSplitEntryInfo().getBOSType();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String deleteSql = "delete from T_AIM_AIMAimCostProdSplitEntry where fcostEntryId=?";
		String sql = "insert into T_AIM_AIMAimCostProdSplitEntry " + "(FId,FProductID,FApportionTypeID,FSplitAmount,FDirectProportion,FCostEntryId,FDescription_l2) " + "values(?,?,?,?,?,?,?)";
		List deleteParams = new ArrayList();
		List params = new ArrayList();

		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			final Object key = iter.next();
			//����ϸ���ܣ���Ʒ��̯Ŀ��ɱ��仯,���˵�by hpw 
			if(key.equals("prjId")) continue;
			
			deleteParams.add(Arrays.asList(new Object[] { key }));
			AIMAimCostProductSplitEntryCollection c = (AIMAimCostProductSplitEntryCollection) costEntryMap.get(key);
			for (int i = 0; i < c.size(); i++) {
				final AIMAimCostProductSplitEntryInfo entryInfo = c.get(i);
				if (entryInfo == null)
					continue;
				String fid = BOSUuid.create(type).toString();
				String fproductid = entryInfo.getProduct().getId().toString();
				String fapportiontypeid = entryInfo.getApportionType().getId().toString();
				BigDecimal fsplitamount = entryInfo.getSplitAmount();
				BigDecimal fdirectproportion = entryInfo.getDirectAmount();
				String fcostentryid = entryInfo.getCostEntryId();
				String fdescription = entryInfo.getDescription();
				params.add(Arrays.asList(new Object[] { fid,// FId
						fproductid, fapportiontypeid, fsplitamount, fdirectproportion, fcostentryid, fdescription }));

			}
		}
		builder.executeBatch(deleteSql, deleteParams);
		builder.executeBatch(sql, params);
		//����ϸ���ܣ���Ʒ��̯Ŀ��ɱ��仯 by hpw 
		if(costEntryMap.get("prjId")!=null){
				
			Set prjSet=new HashSet();
			prjSet.add(costEntryMap.get("prjId"));
			ProjectCostChangeLogFactory.getLocalInstance(ctx).insertLog(prjSet);
		}
		return null;
	}

	/*
	 * costEntryMap �������˿���ͨ������ AimCostSplitDataGetter.getProductSplitEntry
	 * 
	 * @see com.kingdee.eas.fdc.aimcost.app.AbstractFDCCostRptFacadeControllerBean#_submitAimProductCost(com.kingdee.bos.Context,
	 *      java.util.Map)
	 */
	protected Map _submitAimProductCost(Context ctx, Map costEntryMap) throws BOSException, EASBizException {
		// ��ɾ���������
		if (costEntryMap == null || costEntryMap.size() <= 0) {
			return null;
		}
		// ����Ҫ�����ڼ�Ĺ��˹���

		// ���
		final BOSObjectType type = new AimCostProductSplitEntryInfo().getBOSType();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String deleteSql = "delete from T_AIM_AimCostProductSplitEntry where fcostEntryId=?";
		String sql = "insert into T_AIM_AimCostProductSplitEntry " + "(FId,FProductID,FApportionTypeID,FSplitAmount,FDirectProportion,FCostEntryId,FDescription_l2) " + "values(?,?,?,?,?,?,?)";
		List deleteParams = new ArrayList();
		List params = new ArrayList();
		//ȡ����ĿID��������Map��ɾ��
		String prjId=(String)costEntryMap.remove("prjId");
		final Set keySet = costEntryMap.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			final Object key = iter.next();
			deleteParams.add(Arrays.asList(new Object[] { key }));
			AimCostProductSplitEntryCollection c = (AimCostProductSplitEntryCollection) costEntryMap.get(key);
			for (int i = 0; i < c.size(); i++) {
				final AimCostProductSplitEntryInfo entryInfo = c.get(i);
				if (entryInfo == null)
					continue;
				String fid = BOSUuid.create(type).toString();
				String fproductid = entryInfo.getProduct().getId().toString();
				String fapportiontypeid = entryInfo.getApportionType().getId().toString();
				BigDecimal fsplitamount = entryInfo.getSplitAmount();
				BigDecimal fdirectproportion = entryInfo.getDirectAmount();
				String fcostentryid = entryInfo.getCostEntryId();
				String fdescription = entryInfo.getDescription();
				params.add(Arrays.asList(new Object[] { fid,// FId
						fproductid, fapportiontypeid, fsplitamount, fdirectproportion, fcostentryid, fdescription }));

			}
		}

		builder.executeBatch(deleteSql, deleteParams);
		builder.executeBatch(sql, params);
		Set prjSet=new HashSet();
		prjSet.add(prjId);
		ProjectCostChangeLogFactory.getLocalInstance(ctx).insertLog(prjSet);
		return null;
	}

	protected FullDynamicCostMap _getFullPrjDynamicCost(Context ctx, String projOrgId, IObjectValue period) throws BOSException, EASBizException {
		return null;
	}

	protected Map _submitDynProductCost(Context ctx, Map dynCostMap) throws BOSException, EASBizException {
		//�Ƿ����ò���һ�廯
		boolean finacial= false;
		if(ctx != null && ctx.get("finacial") != null){
			finacial =  ((Boolean)ctx.get("finacial")).booleanValue();
		}
		//���ò���ɱ�һ�廯
		if(finacial){
			//������
			DynamicCostCollection addDynamicCostColl = (DynamicCostCollection) dynCostMap.get("addDynamicCostColl");
			//���е�
			DynamicCostCollection allDynamic = (DynamicCostCollection) dynCostMap.get("allDynamic");
			Map dynSplitEntryMap = (Map) dynCostMap.get("dynSplitEntryMap");
			//���ò���ɱ�һ�廯������½��ж�汾����
			String projectId = (String)dynCostMap.get("projectId");
			PeriodInfo curPeriod = FDCUtils.getCurrentPeriod(ctx,projectId,true);
			
			Set idSet = new HashSet();
			for (Iterator iter = dynSplitEntryMap.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();	
				idSet.add(key);
			}
			
			//������ò���ɱ�һ�廯�ұ������½�--����ʾ�������޸�
//			if(isCostEnd(ctx,projectId,curPeriod.getId().toString())){
//				throw new CostRptException(CostRptException.HAS_COST_END);
//			}
			//��������Ѵ��ڷ�̯����
//			else 
			if(existSplitEntry(ctx,idSet,curPeriod.getId().toString())){
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);			
				String addSql = "insert into T_AIM_DynamicCost " + "(fid,faccountID,fadjustSumAmount,fintendingCostSumAmount) " + "values (?,?,0,0)";
				// �����Ķ�̬�ɱ�
				if (addDynamicCostColl != null && addDynamicCostColl.size() > 0) {
					List addSqlList = new ArrayList();
					for (int i = 0; i < addDynamicCostColl.size(); i++) {
						addSqlList.add(Arrays.asList(new Object[] { 
							addDynamicCostColl.get(i).getId().toString()
							, addDynamicCostColl.get(i).getAccount().getId().toString() }));

					}
					builder.executeBatch(addSql, addSqlList);
		
				}
				//��ɾ�����ڷ�̯����
				String deleteSql = "delete from T_AIM_DynCostProduSplitEntry where FDynamicCostId=? and FPeriodID = ?";
//				String deleteSql = "delete from T_AIM_DynCostProduSplitEntry where FPeriodID = ? " +
//						" and FDynamicCostId in " +
//					"(select fid from T_AIM_DynamicCost aim inner join " +
//					"T_FDC_CostAccount acc on aim.FAccountID =  acc.fid " +
//					" inner join T_FDC_CurProject prj on acc.fcurproject = prj.fid where prj.fid  = ?)";
				//�������ڷ�̯���� FIsLatestVerΪtrue
				String addSplitSql = "insert into T_AIM_DynCostProduSplitEntry "
						+ "(fid,FDynamicCostId,FProductID,FApportionTypeID,FSplitAmount," +
								"FHanppenDirectAmount,FIntendingDirectAmount,FAimCostAmount," +
								"FAppointAmount,FDescription_l2 ,FPeriodID,FIsLatestVer) "
						+ "values(?,?,?,?,?,?,?,?,?,? ,?,?)";
				List dynamicList = new ArrayList();		
				
				if (dynSplitEntryMap != null && dynSplitEntryMap.size() > 0) {
					List addSplitSqlList = new ArrayList();
					List deleteSqlList = new ArrayList();
					BOSObjectType type = new DynamicCostProductSplitEntryInfo().getBOSType();
					for (Iterator iter = dynSplitEntryMap.keySet().iterator(); iter.hasNext();) {
						String key = (String) iter.next();	
						deleteSqlList.add(Arrays.asList(new Object[] {key, curPeriod.getId().toString()}));
						DynamicCostProductSplitEntryCollection entryColl = (DynamicCostProductSplitEntryCollection) dynSplitEntryMap.get(key);
						if (entryColl != null && entryColl.size() > 0) {
							for (int i = 0; i < entryColl.size(); i++) {
								DynamicCostProductSplitEntryInfo info = entryColl.get(i);
								String fid = BOSUuid.create(type).toString();
								String FDynamicCostId = info.getDynamicCostId();
								String FProductID = info.getProduct().getId().toString();
								String FApportionTypeID = info.getApportionType().getId().toString();
								BigDecimal FSplitAmount = info.getSplitAmount();
								BigDecimal FHanppenDirectAmount = info.getHanppenDirectAmount();
								BigDecimal FIntendingDirectAmount = info.getIntendingDirectAmount();
								BigDecimal FAimCostAmount = info.getAimCostAmount();
								BigDecimal FAppointAmount = info.getAppointAmount();
								String fdescription = info.getDescription();
								String FPeriodID = curPeriod.getId().toString();
								addSplitSqlList.add(Arrays.asList(new Object[] { fid, FDynamicCostId, FProductID, 
										FApportionTypeID, FSplitAmount, FHanppenDirectAmount, 
										FIntendingDirectAmount, FAimCostAmount,
										FAppointAmount, fdescription ,
										FPeriodID,new Integer(1)}));
		
								if (!dynamicList.contains(FDynamicCostId)) {
									dynamicList.add(FDynamicCostId);
								}
							}
						}			
					}
			
					builder.executeBatch(deleteSql, deleteSqlList);
					builder.executeBatch(addSplitSql, addSplitSqlList);
					// ����T_AIM_DynamicCost���ڼ�
					if (dynamicList.size() > 0) {		
						IDynamicCost iDynamicCost = DynamicCostFactory.getLocalInstance(ctx);
						SelectorItemCollection sic = new SelectorItemCollection();
						sic.add(new SelectorItemInfo("account.curProject.id"));
						
						String updateSql = "update T_AIM_DynamicCost set FPeriodId=? where fid =?";
						List updateSqlList = new ArrayList();
						for (int i = 0; i < dynamicList.size(); i++) {
							String dynamicId = (String) dynamicList.get(i);

							// ��̬����
							DynamicCostInfo info = iDynamicCost.getDynamicCostInfo(new ObjectUuidPK(dynamicId), sic);
							String prjId = info.getAccount().getCurProject().getId().toString();
							PeriodInfo period = FDCUtils.getCurrentPeriod(ctx, prjId, true);
							if (period == null) {
								continue;
							}
							String prdId = period.getId().toString();	
							updateSqlList.add(Arrays.asList(new Object[] { prdId, dynamicId }));
						}
						builder.executeBatch(updateSql, updateSqlList);
			
					}
				}				
			}
			// �����ڷ�̯���� ����Ϊtrue
			else{
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				String addSql = "insert into T_AIM_DynamicCost " + "(fid,faccountID,fadjustSumAmount,fintendingCostSumAmount) " + "values (?,?,0,0)";
				// �����Ķ�̬�ɱ�
				if (addDynamicCostColl != null && addDynamicCostColl.size() > 0) {
					List addSqlList = new ArrayList();
					for (int i = 0; i < addDynamicCostColl.size(); i++) {
						addSqlList.add(Arrays.asList(new Object[] {
							addDynamicCostColl.get(i).getId().toString()
							, addDynamicCostColl.get(i).getAccount().getId().toString() }));

					}
					builder.executeBatch(addSql, addSqlList);
		
				}
				
				//��Ӧ�ð���ǰ�ڼ��FIsLatestVer����Ϊfalse�������ᵼ�·��½ᵽ��ǰ�ڼ��ʱ�� ȡ����FIsLatestVerΪtrue�ķ�̯����
				//������ǰ�ڼ��FIsLatestVerΪfalse
				String updateVerSql = " update T_AIM_DynCostProduSplitEntry set FIsLatestVer = ? where FDynamicCostId = ?";
				List updateVerList = new ArrayList();
				//�������е�
				if (allDynamic != null && allDynamic.size() > 0) {
					for (int i = 0; i < allDynamic.size(); i++) {
						updateVerList.add(Arrays.asList(new Object[] { new Integer(0),allDynamic.get(i).getId().toString()}));
					}
				}
				builder.executeBatch(updateVerSql,updateVerList);
				//��������FIsLatestVerΪtrue�ķ�̯����
				String addSplitSql = "insert into T_AIM_DynCostProduSplitEntry "
					+ "(fid,FDynamicCostId,FProductID,FApportionTypeID,FSplitAmount," +
							"FHanppenDirectAmount,FIntendingDirectAmount,FAimCostAmount," +
							"FAppointAmount,FDescription_l2 ,FPeriodID,FIsLatestVer) "
					+ "values(?,?,?,?,?,?,?,?,?,? ,?,?)";
				List dynamicList = new ArrayList();
				if (dynSplitEntryMap != null && dynSplitEntryMap.size() > 0) {
					List addSplitSqlList = new ArrayList();
					BOSObjectType type = new DynamicCostProductSplitEntryInfo().getBOSType();
					for (Iterator iter = dynSplitEntryMap.keySet().iterator(); iter.hasNext();) {
						String key = (String) iter.next();
						DynamicCostProductSplitEntryCollection entryColl = (DynamicCostProductSplitEntryCollection) dynSplitEntryMap.get(key);
						if (entryColl != null && entryColl.size() > 0) {
							for (int i = 0; i < entryColl.size(); i++) {
								DynamicCostProductSplitEntryInfo info = entryColl.get(i);
								String fid = BOSUuid.create(type).toString();
								String FDynamicCostId = info.getDynamicCostId();
								String FProductID = info.getProduct().getId().toString();
								String FApportionTypeID = info.getApportionType().getId().toString();
								BigDecimal FSplitAmount = info.getSplitAmount();
								BigDecimal FHanppenDirectAmount = info.getHanppenDirectAmount();
								BigDecimal FIntendingDirectAmount = info.getIntendingDirectAmount();
								BigDecimal FAimCostAmount = info.getAimCostAmount();
								BigDecimal FAppointAmount = info.getAppointAmount();
								String fdescription = info.getDescription();
								String FPeriodID = curPeriod.getId().toString();
								addSplitSqlList.add(Arrays.asList(new Object[] { fid, FDynamicCostId, FProductID, 
										FApportionTypeID, FSplitAmount, FHanppenDirectAmount, 
										FIntendingDirectAmount, FAimCostAmount,
										FAppointAmount, fdescription ,
										FPeriodID,new Integer(1)}));
								if (!dynamicList.contains(FDynamicCostId)) {
									dynamicList.add(FDynamicCostId);
								}
							}
						}
		
					}
					builder.executeBatch(addSplitSql, addSplitSqlList);
				}
				// ����T_AIM_DynamicCost���ڼ�
				if (dynamicList.size() > 0) {		
					IDynamicCost iDynamicCost = DynamicCostFactory.getLocalInstance(ctx);
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add(new SelectorItemInfo("account.curProject.id"));
					
					String updateSql = "update T_AIM_DynamicCost set FPeriodId=? where fid =?";
					List updateSqlList = new ArrayList();
					for (int i = 0; i < dynamicList.size(); i++) {
						String dynamicId = (String) dynamicList.get(i);

						// ��̬����
						DynamicCostInfo info = iDynamicCost.getDynamicCostInfo(new ObjectUuidPK(dynamicId), sic);
						String prjId = info.getAccount().getCurProject().getId().toString();
						PeriodInfo period = FDCUtils.getCurrentPeriod(ctx, prjId, true);
						if (period == null) {
							continue;
						}
		
						String prdId = period.getId().toString();	
		
						updateSqlList.add(Arrays.asList(new Object[] { prdId, dynamicId }));
					}
					builder.executeBatch(updateSql, updateSqlList);
		
				}
			}
			
		}else{
			DynamicCostCollection addDynamicCostColl = (DynamicCostCollection) dynCostMap.get("addDynamicCostColl");
			Map dynSplitEntryMap = (Map) dynCostMap.get("dynSplitEntryMap");
			String addSql = "insert into T_AIM_DynamicCost " + "(fid,faccountID,fadjustSumAmount,fintendingCostSumAmount) " + "values (?,?,0,0)";
	
			String deleteSql = "delete from T_AIM_DynCostProduSplitEntry where FDynamicCostId=?";
			String addSplitSql = "insert into T_AIM_DynCostProduSplitEntry "
					+ "(fid,FDynamicCostId,FProductID,FApportionTypeID,FSplitAmount,FHanppenDirectAmount,FIntendingDirectAmount,FAimCostAmount,FAppointAmount,FDescription_l2,FIsLatestVer) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?)";
	
			// ����T_AIM_DynamicCost���ڼ�
			List dynamicList = new ArrayList();
	
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			// �����Ķ�̬�ɱ�
			if (addDynamicCostColl != null && addDynamicCostColl.size() > 0) {
				List addSqlList = new ArrayList();
				for (int i = 0; i < addDynamicCostColl.size(); i++) {
					addSqlList.add(Arrays.asList(new Object[] { addDynamicCostColl.get(i).getId().toString(), addDynamicCostColl.get(i).getAccount().getId().toString() }));
	
					// String dynamicId =
					// addDynamicCostColl.get(i).getId().toString();
					// if(!dynamicList.contains(dynamicId)){
					// dynamicList.add(dynamicId);
					// }
				}
				builder.executeBatch(addSql, addSqlList);
	
			}
	
			// ��̬�ɱ���¼�޸�
			if (dynSplitEntryMap != null && dynSplitEntryMap.size() > 0) {
				List deleteSqlList = new ArrayList();
				List addSplitSqlList = new ArrayList();
				BOSObjectType type = new DynamicCostProductSplitEntryInfo().getBOSType();
				for (Iterator iter = dynSplitEntryMap.keySet().iterator(); iter.hasNext();) {
					String key = (String) iter.next();
					deleteSqlList.add(Arrays.asList(new Object[] { key }));
					DynamicCostProductSplitEntryCollection entryColl = (DynamicCostProductSplitEntryCollection) dynSplitEntryMap.get(key);
	
					if (entryColl != null && entryColl.size() > 0) {
						for (int i = 0; i < entryColl.size(); i++) {
							DynamicCostProductSplitEntryInfo info = entryColl.get(i);
							String fid = BOSUuid.create(type).toString();
							String FDynamicCostId = info.getDynamicCostId();
							String FProductID = info.getProduct().getId().toString();
							String FApportionTypeID = info.getApportionType().getId().toString();
							BigDecimal FSplitAmount = info.getSplitAmount();
							BigDecimal FHanppenDirectAmount = info.getHanppenDirectAmount();
							BigDecimal FIntendingDirectAmount = info.getIntendingDirectAmount();
							BigDecimal FAimCostAmount = info.getAimCostAmount();
							BigDecimal FAppointAmount = info.getAppointAmount();
							String fdescription = info.getDescription();
	
							addSplitSqlList.add(Arrays.asList(new Object[] { fid, FDynamicCostId, FProductID, FApportionTypeID, FSplitAmount, FHanppenDirectAmount, FIntendingDirectAmount, FAimCostAmount,
									FAppointAmount, fdescription ,new Integer(1)}));
	
							if (!dynamicList.contains(FDynamicCostId)) {
								dynamicList.add(FDynamicCostId);
							}
						}
					}
	
				}
	
				builder.executeBatch(deleteSql, deleteSqlList);
				builder.executeBatch(addSplitSql, addSplitSqlList);
			}
	
			// ����T_AIM_DynamicCost���ڼ�
			// boolean isCost = FDCUtils.IsInCorporation(ctx,)
			if (dynamicList.size() > 0) {
	
				IDynamicCost iDynamicCost = DynamicCostFactory.getLocalInstance(ctx);
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("account.curProject.id"));
	
				String updateSql = "update T_AIM_DynamicCost set FPeriodId=? where fid =?";
				List updateSqlList = new ArrayList();
				for (int i = 0; i < dynamicList.size(); i++) {
					String dynamicId = (String) dynamicList.get(i);
	
					// ��̬����
					DynamicCostInfo info = iDynamicCost.getDynamicCostInfo(new ObjectUuidPK(dynamicId), sic);
					String prjId = info.getAccount().getCurProject().getId().toString();
					PeriodInfo period = FDCUtils.getCurrentPeriod(ctx, prjId, true);
					if (period == null) {
						continue;
					}
	
					String prdId = period.getId().toString();
	
					updateSqlList.add(Arrays.asList(new Object[] { prdId, dynamicId }));
				}
				builder.executeBatch(updateSql, updateSqlList);
	
			}
		}
		return null;
	}

	private Map getAcctAimCostData(Context ctx, String objectId) throws BOSException {
		// TODO ������SQL��ʵ��
		/*
		 * Map aimCostMap = new HashMap(); EntityViewInfo aimView = new
		 * EntityViewInfo(); FilterInfo filter = new FilterInfo();
		 * aimView.setFilter(filter); filter.getFilterItems().add(new
		 * FilterItemInfo("orgOrProId", objectId));
		 * filter.getFilterItems().add(new FilterItemInfo("isLastVersion", new
		 * Integer(1))); // aimView.getSelector().add(new
		 * SelectorItemInfo("*")); aimView.getSelector().add(new
		 * SelectorItemInfo("costEntry.id")); aimView.getSelector().add(new
		 * SelectorItemInfo("costEntry.costAccount.id"));
		 * aimView.getSelector().add(new
		 * SelectorItemInfo("costEntry.costAmount")); AimCostCollection
		 * aimCostCollection =
		 * AimCostFactory.getLocalInstance(ctx).getAimCostCollection(aimView);
		 * if (aimCostCollection.size() >= 1) { CostEntryCollection costEntrys =
		 * aimCostCollection.get(0).getCostEntry(); for (int i = 0; i <
		 * costEntrys.size(); i++) { CostEntryInfo entry = costEntrys.get(i);
		 * CostAccountInfo costAccount = entry.getCostAccount(); BigDecimal
		 * value = entry.getCostAmount(); if (value == null) { value =
		 * FDCHelper.ZERO; } if
		 * (aimCostMap.containsKey(costAccount.getId().toString())) { BigDecimal
		 * sum = (BigDecimal) aimCostMap.get(costAccount.getId().toString());
		 * aimCostMap.put(costAccount.getId().toString(), sum.add(value)); }
		 * else { aimCostMap.put(costAccount.getId().toString(), value); } } }
		 */

		Map aimCostMap = new HashMap();
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			if(FDCUtils.getDefaultFDCParamByKey(
					ctx, ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_AIMCOSTAUDIT)){
				builder.appendSql("select entry.fcostaccountid as acctid,sum(entry.fcostamount) as amount,sum(isnull(me.FRateAmount,0)) aimTaxAmount from T_AIM_AimCost head " +
						"	inner join T_AIM_CostEntry entry on entry.fheadid=head.fid " +
						"   inner join T_AIM_MeasureEntry me on entry.fmeasureentryid = me.fid " +
						"	where head.fisLastVersion=1 and  head.ForgOrProId=? " +
						"	and head.fstate=?"+
						"	group by entry.fcostaccountid ");
				builder.addParam(objectId);
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
			}else{
				builder.appendSql("select entry.fcostaccountid as acctid,sum(entry.fcostamount) as amount,sum(isnull(me.FRateAmount,0)) aimTaxAmount from T_AIM_AimCost head " +
						"	inner join T_AIM_CostEntry entry on entry.fheadid=head.fid " +
						"   inner join T_AIM_MeasureEntry me on entry.fmeasureentryid = me.fid  " +
						"	where head.fisLastVersion=1 and  head.ForgOrProId=? " +
						"	group by entry.fcostaccountid ");
				builder.addParam(objectId);
			}
		
			final IRowSet rowSet = builder.executeQuery();
			String acctId = null;
			BigDecimal aimCostAmount = FDCHelper.ZERO;
			BigDecimal aimTaxAmount = null;
			while (rowSet.next()) {
				acctId = rowSet.getString("acctid");
				aimCostAmount = rowSet.getBigDecimal("amount");
				aimTaxAmount = rowSet.getBigDecimal("aimTaxAmount");
				aimCostMap.put(acctId, rowSet.getBigDecimal("amount"));
				if(aimTaxAmount != null && aimTaxAmount.compareTo(FDCHelper.ZERO)>0){
					aimCostMap.put(acctId+"_AimTaxAmount", aimTaxAmount);
				}
				
			}

		} catch (SQLException e) {
			throw new BOSException(e);
		} catch (EASBizException e) {
			throw new BOSException(e);
		}
		return aimCostMap;

	}

	private Map getAcctAdjustCostData(Context ctx, String objectId) throws BOSException {
		Map dynamicCostMap = new HashMap();
		/*
		 * EntityViewInfo view = new EntityViewInfo(); FilterInfo filter = new
		 * FilterInfo(); view.setFilter(filter); BOSObjectType bosType =
		 * BOSUuid.read(objectId).getType(); if (new
		 * CurProjectInfo().getBOSType().equals(bosType)) {
		 * filter.getFilterItems().add( new
		 * FilterItemInfo("account.curProject.id", objectId)); } else {
		 * filter.getFilterItems().add( new
		 * FilterItemInfo("account.fullOrgUnit.id", objectId)); }
		 * view.getSelector().add(new SelectorItemInfo("*"));
		 * DynamicCostCollection DynamicCostCollection = DynamicCostFactory
		 * .getRemoteInstance().getDynamicCostCollection(view); for (int i = 0;
		 * i < DynamicCostCollection.size(); i++) { DynamicCostInfo info =
		 * DynamicCostCollection.get(i); CostAccountInfo acct =
		 * info.getAccount(); dynamicCostMap.put(acct.getId().toString(), info
		 * .getAdjustSumAmount()); }
		 */
		boolean isPrj = CostDataGetter.isCurProject(objectId);
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select FAccountID as acctid,FAdjustSumAmount as amount from T_AIM_DynamicCost dyn");
			builder.appendSql(" inner join T_FDC_CostAccount acct on dyn.faccountid=acct.fid where ");
			if (isPrj) {
				builder.appendSql(" FcurProject=?");
			} else {
				builder.appendSql("FFullOrgUnit=?");
			}
			builder.addParam(objectId);
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				dynamicCostMap.put(rowSet.getString("acctid"), rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return dynamicCostMap;
	}

	protected void _saveSnapShot(Context ctx, String prjId, CostMonthlySaveTypeEnum savedType) throws BOSException, EASBizException {
		if (savedType == null) {
			return;
		}
		// test
		// savedType=CostMonthlySaveTypeEnum.COSTMONTHLY;
		// savedType=CostMonthlySaveTypeEnum.FINANCEMONTHLY;
//		savedType = CostMonthlySaveTypeEnum.AUTOSAVE;
		try {
			// �õ��¸��µĵ�һ���0��0��
			if (savedType == CostMonthlySaveTypeEnum.AUTOSAVE) {
				java.sql.Date sqlDate = new java.sql.Date(FDCDateHelper.getLastDayOfCurMonth().getTime());
				ctx.put("autoSaveSnapShotDate", sqlDate);
			}
			DynCostSnapShoter.saveSnapShot(ctx, prjId, savedType);
		} finally {
			if (ctx.get("autoSaveSnapShotDate") != null) {
				ctx.remove("autoSaveSnapShotDate");
			}
		}
	}

	protected void _autoSaveSnapShot(Context ctx) throws BOSException {
		try {
			// �õ��¸��µĵ�һ���0��0��
			java.sql.Date sqlDate = new java.sql.Date(FDCDateHelper.getLastDayOfCurMonth().getTime());
			ctx.put("autoSaveSnapShotDate", sqlDate);
			DynCostSnapShoter.autoSaveSnapShot(ctx);
		} finally {
			if (ctx.get("autoSaveSnapShotDate") != null) {
				ctx.remove("autoSaveSnapShotDate");
			}
		}
		
		//�Զ�����ϵͳ��������
//		autoClear(ctx);
	}

	/**
	 * �Զ���������ȷ�Ե�У��,���������ݵĶ��ں�̨���
	 *  by sxhong 2008-06-24 14:24:21
	 * @param ctx
	 */
	private void autoClear(Context ctx) throws BOSException {
		FDCHelper.autoClear(ctx);
	}

	protected void _deleteDynSnapShot(Context ctx, String prjId, IObjectValue period) throws BOSException, EASBizException {
		if (prjId == null) {
			return;
		}
		DynCostSnapShoter.deleteDynSnapShot(ctx, prjId, period);
	}

	protected void _reverseCostMonthSettled(Context ctx, String prjId, IObjectValue period) throws BOSException, EASBizException {
		if (prjId == null || period == null) {
			return;
		}
		DynCostSnapShoter.reverseCostMonthSettled(ctx, prjId, period);
	}

	protected void _reverseFinanceMonthSettled(Context ctx, String prjId, IObjectValue period) throws BOSException, EASBizException {
		if (prjId == null || period == null) {
			return;
		}
		DynCostSnapShoter.reverseFinanceMonthSettled(ctx, prjId, period);
	}

	/* 
	 * ȡ��ָ����Ŀ��Ŀ��ɱ�,����Ŀ����Ϊ����ϸ��Ŀ
	 * (non-Javadoc)
	 * @see com.kingdee.eas.fdc.aimcost.app.AbstractFDCCostRptFacadeControllerBean#_getAimCost(com.kingdee.bos.Context, java.lang.String)
	 */
	protected BigDecimal _getAimCost(Context ctx, String prjId) throws BOSException, EASBizException {
		try {
			Set set=FDCHelper.getProjectLeafIdSet(ctx, prjId);
			return getAimCost(ctx, set);
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
	}

	protected BigDecimal _getDynCost(Context ctx, String prjId) throws BOSException, EASBizException {
		try {
			Set prjIdSet = FDCHelper.getProjectLeafIdSet(ctx, prjId);
			BigDecimal aimcost = getAimCost(ctx, prjIdSet);
			BigDecimal adjustCost = getAdjustCost(ctx, prjIdSet);
			return aimcost.add(adjustCost);
		} catch (SQLException e) {
			throw new BOSException(e);
		}
	}

	protected BigDecimal _getHappendCost(Context ctx, String prjId) throws BOSException, EASBizException {
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		try {
			builder.appendSql("select fcontrolUnitId from T_FDC_CurProject where fid=?");
			builder.addParam(prjId);
			IRowSet rowSet = builder.executeQuery();
			String cuId = null;
			if (rowSet.size() > 0) {
				rowSet.next();
				cuId = rowSet.getString("fcontrolUnitId");
			}
			Set prjIdSet = FDCHelper.getProjectLeafIdSet(ctx, prjId);
			if (cuId == null||prjIdSet==null||prjIdSet.size()<=0) {
				return FDCHelper.ZERO;
			}
			builder.clear();
			builder.appendSql("select fid from T_FDC_CostAccount where flevel=1 and ");
			builder.appendParam("fcurproject", prjIdSet.toArray());
			rowSet=builder.executeQuery();
			Set acctIdSet=new HashSet(rowSet.size());
			while(rowSet.next()){
				acctIdSet.add(rowSet.getString("fid"));
			}
			if (acctIdSet==null||acctIdSet.size()<=0) {
				return FDCHelper.ZERO;
			}
			BigDecimal contractAmt=FDCHelper.ZERO;
			BigDecimal changeAmt=FDCHelper.ZERO;
			BigDecimal settlementAmt=FDCHelper.ZERO;
			BigDecimal conWithoutTxtAmt=FDCHelper.ZERO;
			// ��ͬ
			builder.clear();
			builder.appendSql("select sum(entry.FAmount) amount ");
			builder.appendSql("from T_AIM_CostSplitEntry entry ");
			builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentID=head.FId ");
			builder.appendSql("inner join T_CON_ContractBill con on head.FCostBillID=con.FId ");
			builder.appendSql("where FCostBillType='CONTRACTSPLIT' and  head.FControlunitId=? And head.FIsInvalid=0 And FIsProduct=0 and  ");
			builder.addParam(cuId);
			builder.appendParam("entry.fcostaccountid",acctIdSet.toArray());
			builder.appendSql(" and (con.FHasSettled=0 or con.FHasSettled is null) ");
			rowSet=builder.executeQuery();
			if(rowSet.size()>0){
				rowSet.next();
				contractAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
			}
			
			// ���
			builder.clear();
			builder.appendSql("select sum(entry.FAmount) amount ");
			builder.appendSql("from T_AIM_CostSplitEntry entry ");
			builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentID=head.FId ");
			builder.appendSql("inner join T_CON_ContractChangeBill change on head.FCostBillID=change.FId ");
			builder.appendSql("where FCostBillType='CNTRCHANGESPLIT' and  head.FControlunitId=? And head.FIsInvalid=0 And FIsProduct=0 and  ");
			builder.addParam(cuId);
			builder.appendParam("entry.fcostaccountid",acctIdSet.toArray());
			builder.appendSql(" and (change.fisconsetted=0 or change.fisconsetted is null) ");
			rowSet=builder.executeQuery();
			if(rowSet.size()>0){
				rowSet.next();
				changeAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
			}
			
			// ����
			builder.clear();
			builder.appendSql("select sum(entry.FAmount) amount ");
			builder.appendSql("from T_AIM_CostSplitEntry entry ");
			builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentID=head.FId ");
			builder.appendSql("where FCostBillType='SETTLEMENTSPLIT' and  head.FControlunitId=? And head.FIsInvalid=0 And FIsProduct=0 and  ");
			builder.addParam(cuId);
			builder.appendParam("entry.fcostaccountid",acctIdSet.toArray());
			rowSet=builder.executeQuery();
			if(rowSet.size()>0){
				rowSet.next();
				settlementAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
			}
			//���ı�
			builder.clear();
			builder.appendSql("select sum(entry.FAmount) amount ");
			builder.appendSql("from T_AIM_CostSplitEntry entry ");
			builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentID=head.FId ");
			builder.appendSql("where FCostBillType='NOTEXTCONSPLIT' and  head.FControlunitId=? And head.FIsInvalid=0 And FIsProduct=0 and  ");
			builder.addParam(cuId);
			builder.appendParam("entry.fcostaccountid",acctIdSet.toArray());
			rowSet=builder.executeQuery();
			if(rowSet.size()>0){
				rowSet.next();
				conWithoutTxtAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
			}
			
			return contractAmt.add(changeAmt).add(settlementAmt).add(conWithoutTxtAmt);
		}catch(SQLException e){
			throw new BOSException(e);
		}
	}
	
	private BigDecimal getAimCost(Context ctx, Set prjIdSet) throws BOSException, EASBizException {
		if(prjIdSet==null||prjIdSet.size()<=0){
			return FDCHelper.ZERO;
		}
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("select sum(FCostAmount) as amount from T_AIM_CostEntry entry ");
			builder.appendSql("inner join T_AIM_AimCost head on head.fid=entry.fheadId ");
			builder.appendSql(" where head.FIsLastVersion=1 and ");
			builder.appendParam("head.FOrgOrProId", prjIdSet.toArray());
			IRowSet rowSet=builder.executeQuery();
			if(rowSet.size()==1){
				rowSet.next();
				return FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		return FDCHelper.ZERO;
	}
	
	private BigDecimal getAdjustCost(Context ctx, Set prjIdSet) throws BOSException {
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select sum(FAdjustSumAmount) as amount from T_AIM_DynamicCost dyn");
			builder.appendSql(" inner join T_FDC_CostAccount acct on dyn.faccountid=acct.fid where ");
			builder.appendParam("acct.fcurproject", prjIdSet.toArray());
			IRowSet rowSet = builder.executeQuery();
			if(rowSet.size()==1){
				rowSet.next();
				return FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return FDCHelper.ZERO;
	}

	protected BigDecimal _getAdjustCost(Context ctx, String prjId) throws BOSException, EASBizException {
		try{
			Set prjIdSet=FDCHelper.getProjectLeafIdSet(ctx, prjId);
			return getAdjustCost(ctx, prjIdSet);
		}catch(SQLException e){
			throw new BOSException(e);
		}
	}

	protected Map _getCostAccountAimCost(Context ctx, String prjId) throws BOSException, EASBizException {
		return getAcctAimCostData(ctx, prjId);
	}

	protected Map _getCostAccountDynCost(Context ctx, String prjId) throws BOSException, EASBizException {
		Map aimCostMap=getAcctAimCostData(ctx, prjId);
		Map adjustMap=getAcctAdjustCostData(ctx, prjId);
		return __getCostAccountDynCost(ctx, aimCostMap, adjustMap);
	}
	
	private Map __getCostAccountDynCost(Context ctx,Map aimCostMap,Map adjustMap){
		if(aimCostMap==null){
			aimCostMap=new  HashMap();
		}
		if(adjustMap==null){
			adjustMap=new  HashMap();
		}
		Set acctSet=new HashSet();
		acctSet.addAll(aimCostMap.keySet());
		acctSet.addAll(adjustMap.keySet());
		
		Map dynMap=new HashMap();
		for(Iterator iter=acctSet.iterator();iter.hasNext();){
			final Object key = iter.next();
			BigDecimal aimCost=(BigDecimal)aimCostMap.get(key);
			BigDecimal adjustCost=(BigDecimal)adjustMap.get(key);
			BigDecimal dynCost=FDCNumberHelper.add(aimCost, adjustCost);
			dynMap.put(key, dynCost);
		}
		return dynMap;
	}

	protected Map _getCostMap(Context ctx, Map param) throws BOSException {
		String prjId=(String)param.get("prjId");
		if(prjId==null){
			throw new NullPointerException("prjId cann't be null!");
		}
		Map aimCostMap=getAcctAimCostData(ctx, prjId);
		Map adjustMap=getAcctAdjustCostData(ctx, prjId);
		Map dynMap=__getCostAccountDynCost(ctx, aimCostMap, adjustMap);
		Map retMap=new HashMap();
		if(aimCostMap==null){
			aimCostMap=new HashMap();
		}
		if(dynMap==null){
			dynMap=new HashMap();
		}
		retMap.put("aimCost", aimCostMap);
		retMap.put("dynCost", dynMap);
		return retMap;
	}

	/**
	 * ���½ᱨ���Ʒ��̯�����Խ��п��ձ���и���
	 */
	protected void _updatePeriodCostPayedAmt(Context ctx, Map param) throws BOSException, EASBizException {
		Set costAcctIds = (Set)param.get("costAcctIds");
		DynCostSnapShotProTypEntryCollection snapShotEntrys = null;
		if(costAcctIds == null || costAcctIds.size() < 0){
			return;
		}
		//���¿��ձ��������
		String updateSnapShotSql = "update T_AIM_DynCostSnapShot set FApprotionTypeId = ? ,FSavedType = ? where " +
			"FProjectId = ? and FCostAccountId = ? and FPeriodID = ? " ;
		//���¿��շ�¼--��ʵ�ָ���Ʒ��̯���
		String updateSnapShotEntrySql = "update T_AIM_DynCostSnpShtProTypEntry set FCostPayedAmt = ? where FProductTypeId = ?" +
				" and FParentID in (select fid from T_AIM_DynCostSnapShot where FProjectId = ? and " +
				" FCostAccountId = ? and FPeriodID = ? )";
		List updateSnapShotList = new ArrayList();
		List updateSnapShotEntryList = new ArrayList();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		for(Iterator it = costAcctIds.iterator();it.hasNext();){
			String costAcctId = (String)it.next();
			DynCostSnapShotInfo info = (DynCostSnapShotInfo)param.get(costAcctId+"snapShot");
			updateSnapShotList.add(Arrays.asList(new Object[]{info.getApprotionTypeId().toString(),
					CostMonthlySaveTypeEnum.FINANCEMONTHLY_VALUE,info.getProjectId().toString(),
					costAcctId,info.getPeriod().getId().toString()}));
			snapShotEntrys = (DynCostSnapShotProTypEntryCollection)param.get(costAcctId+"entrys");
			if(snapShotEntrys == null || snapShotEntrys.size() < 0){
				continue;
			}
			for(int i = 0 ; i < snapShotEntrys.size();i++){
				DynCostSnapShotProTypEntryInfo entry = (DynCostSnapShotProTypEntryInfo)snapShotEntrys.get(i);
				String productTypeId = entry.getProductTypeId().toString();
				BigDecimal costPayedAmt = entry.getCostPayedAmt();
				updateSnapShotEntryList.add(Arrays.asList(new Object[] {costPayedAmt,productTypeId,
						info.getProjectId().toString(),costAcctId,info.getPeriod().getId().toString()}));
			}

		}
		//���¿��ձ�
		builder.executeBatch(updateSnapShotSql,updateSnapShotList);
		//���¿��շ�¼
		builder.executeBatch(updateSnapShotEntrySql,updateSnapShotEntryList);
		
	}
	//��Ŀ�Ƿ��½�
	private boolean isCostEnd(Context ctx,String projectId,String periodId) throws BOSException,EASBizException{
		boolean isCostEnd = false;
//		EntityViewInfo view = new EntityViewInfo();
//		view.getSelector().add("FIsCostEnd");
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("FProjectID",projectId));
//		view.setFilter(filter);
        //��ǰ������Ŀ�ĳɱ��ڼ�
        
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select FIsCostEnd from T_FNC_ProjectPeriodStatus where ");
		builder.appendParam("FProjectId",projectId);
		builder.appendSql(" and ");
		builder.appendParam(" fcostperiodid",periodId);
		IRowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				isCostEnd = rs.getBoolean("FIsCostEnd");
				if(isCostEnd){
					return true;
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		return isCostEnd;
	}
	//�жϱ����Ƿ���ڷ�̯����
	private boolean existSplitEntry(Context ctx,Set idSet,String periodId) throws BOSException,EASBizException{
		boolean existSplitEntry = false;
//		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		builder.appendSql(" select fid from T_AIM_DynamicCost inner join " +
//				" where ");
//		builder.appendParam("FProjectId",projectId);
//		builder.appendSql(" and ");
//		builder.appendParam(" fcostperiodid",periodId);
//		IRowSet rs = builder.executeQuery();
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("period",periodId));
		filter.getFilterItems().add(new FilterItemInfo("dynamicCostId",idSet,CompareType.INCLUDE));
		view.setFilter(filter);
		DynamicCostProductSplitEntryCollection coll = DynamicCostProductSplitEntryFactory
			.getLocalInstance(ctx).getDynamicCostProductSplitEntryCollection(view);
		if(coll != null && coll.size() >0){
			existSplitEntry = true;
		}
		return existSplitEntry;
	}
	
	/**
	 * ȡ������Ŀ�����еĳɱ���Ŀ
	 * @author ling_peng
	 */
	protected Map getCostAccountByPrj() {
		return null;
	}
	/**
	 * ��ȡ����ϸ������Ŀ�¸��������Ľ����
	 * @author ling_peng
	 */
	protected Map _getNonLeafPaymentCostStatistics(Context ctx, Map param) throws BOSException, EASBizException {
		Set leafPrjIds=(Set)param.get("leafPrjIds");
		if(leafPrjIds==null){
			throw new NullPointerException("prjId can't be null");
		}
		
		//��ȡ������Ŀ�µ����гɱ���Ŀ���ƿ�Ŀ�������Ӧ��ϵ
		List costAccountList = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
	
		 // ȡ������Ŀ�����еĳɱ���Ŀ
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		Object obj=param.get("selectedObj");
		if(obj!=null&&obj instanceof CurProjectInfo){
			try {
			String fname="fname_"+ctx.getLocale().toString();
			builder.appendSql("select fid,flongnumber,flevel,fisLeaf,fparentid,FCurProject,");
			builder.appendSql(fname);		
			builder.appendSql("  from T_FDC_CostAccount ");
			builder.appendSql("where fcurproject=? ");
			builder.appendSql("order by flongnumber asc");
			builder.addParam(((CurProjectInfo)obj).getId().toString());
			IRowSet rowSet=builder.executeQuery();
				while(rowSet.next()){
					CostAccountInfo costAccountInfo =new CostAccountInfo();
					costAccountInfo.setId(BOSUuid.read(rowSet.getString("fid")));
					costAccountInfo.setName(rowSet.getString(fname));
					costAccountInfo.setLongNumber(rowSet.getString("flongnumber"));
					costAccountInfo.setLevel(rowSet.getInt("flevel"));
					costAccountInfo.setIsLeaf(rowSet.getBoolean("fisLeaf"));
					CostAccountInfo parentCostAccount=new CostAccountInfo();
					if(rowSet.getString("fparentid")!=null){
						parentCostAccount.setId(BOSUuid.read(rowSet.getString("fparentid")));
						costAccountInfo.setParent(parentCostAccount);
					}
					costAccountList.add(costAccountInfo);
				}
			} catch (Exception e) {
				throw new BOSException(e);
			}
				
		}
		if(obj!=null&&obj instanceof OrgStructureInfo){
			try {
				String fname="fname_"+ctx.getLocale().toString();
				builder.appendSql("select fid,flongnumber,flevel,fisLeaf,fparentid,FCurProject,");
				builder.appendSql(fname);
				builder.appendSql("  from T_FDC_CostAccount ");
				builder.appendSql(" where FFullOrgUnit in ");
				builder.appendSql("(select FUnitID from T_ORG_Structure ");
				builder.appendSql("where fid=? ");
				builder.appendSql("order by flongnumber asc");
				builder.addParam(((OrgStructureInfo)obj).getId().toString());
				IRowSet rowSet=builder.executeQuery();
				while(rowSet.next()){
					CostAccountInfo costAccountInfo =new CostAccountInfo();
					costAccountInfo.setId(BOSUuid.read(rowSet.getString("fid")));
					costAccountInfo.setName(rowSet.getString(fname));
					costAccountInfo.setLongNumber(rowSet.getString("flongnumber"));
					costAccountInfo.setLevel(rowSet.getInt("flevel"));
					costAccountInfo.setIsLeaf(rowSet.getBoolean("fisLeaf"));
					CostAccountInfo parentCostAccount=new CostAccountInfo();
					if(rowSet.getString("fparentid")!=null){
						parentCostAccount.setId(BOSUuid.read(rowSet.getString("fparentid")));
						costAccountInfo.setParent(parentCostAccount);
					}
					costAccountList.add(costAccountInfo);
				}
			} catch (Exception e) {
				throw new BOSException();
			}
		}
		
		if(obj!=null && obj instanceof FullOrgUnitInfo){
			try {
				String fname="fname_"+ctx.getLocale().toString();
				builder.appendSql("select fid,flongnumber,flevel,fisLeaf,fparentid,FCurProject,");
				builder.appendSql(fname);
				builder.appendSql("  from T_FDC_CostAccount ");
				builder.appendSql("where FFullOrgUnit=? ");
				builder.appendSql("order by flongnumber asc");
				builder.addParam(((FullOrgUnitInfo)obj).getId().toString());
				IRowSet rowSet=builder.executeQuery();
				while(rowSet.next()){
					CostAccountInfo costAccountInfo =new CostAccountInfo();
					costAccountInfo.setId(BOSUuid.read(rowSet.getString("fid")));
					costAccountInfo.setName(rowSet.getString(fname));
					costAccountInfo.setLongNumber(rowSet.getString("flongnumber"));
					costAccountInfo.setLevel(rowSet.getInt("flevel"));
					costAccountInfo.setIsLeaf(rowSet.getBoolean("fisLeaf"));
					CostAccountInfo parentCostAccount=new CostAccountInfo();
					if(rowSet.getString("fparentid")!=null){
						parentCostAccount.setId(BOSUuid.read(rowSet.getString("fparentid")));
						costAccountInfo.setParent(parentCostAccount);
					}
					costAccountList.add(costAccountInfo);
				}
			} catch (Exception e) {
				throw new BOSException();
			}
		}
		
		//   �׳��쳣��û���ҵ��ɱ���Ŀ���ƿ�Ŀ�Ķ�Ӧ��ϵ������ʾΪ�հף�����ʾ�Ǹ�ɶ�ɱ���Ŀ��
		//   ʱС��˵��ʹû���ҵ��ɱ���Ŀ���ƿ�Ŀ�Ķ�Ӧ��ϵ��ҲӦ�ý���ӵ�еĳɱ���Ŀ�����Ϣ��ʾ���� 
		Map costAccountMap = FDCUtils.getCostAccountMap(ctx,leafPrjIds);
		
		Map	costAccountWithAccountMapAll =null;
		try{
			costAccountWithAccountMapAll = FDCUtils.getCostAccountWithAccountMapAll(ctx,leafPrjIds,true);
		}catch(ContractException e){
			logger.error(e.getMessage(), e);
			costAccountWithAccountMapAll=new HashMap();
		}
		
//      Map	costAccountWithAccountMapAll = FDCUtils.getCostAccountWithAccountMapAll(ctx,leafPrjIds,null);//��ƿ�Ŀ��ɱ���Ŀ�Ķ�Ӧ��ϵ
        Map accountMap = new HashMap();
		for(Iterator it = costAccountWithAccountMapAll.keySet().iterator();it.hasNext();){
			String key = (String) it.next();
			CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)costAccountWithAccountMapAll.get(key);
			
			if(info!=null&&
			   info.getAccount()!=null&&
			   info.getAccount().getId()!=null&&
			   !accountMap.containsKey(info.getAccount().getId().toString())){
				accountMap.put(info.getAccount().getId().toString(),info.getAccount());
			}
		}
		
		//��ȡ����ϸ������Ŀ�µ����л�ƿ�Ŀ()
		List accountList = new ArrayList();
		Map longNumberTempMap = new HashMap();
		if(accountMap.size()>0){
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",accountMap.keySet(),CompareType.INCLUDE));
			SorterItemInfo sorterItem = new SorterItemInfo();
			sorterItem.setPropertyName("longnumber");
			sorterItem.setSortType(SortType.ASCEND);
			view.getSorter().add(sorterItem);
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("longnumber");
			AccountViewCollection accountViewColl = AccountViewFactory.getLocalInstance(ctx).getAccountViewCollection(view);
			for(Iterator it=accountViewColl.iterator();it.hasNext();){
				AccountViewInfo accountViewInfo = (AccountViewInfo)it.next();
				if(accountMap!=null&&accountMap.containsKey(accountViewInfo.getId().toString())&&accountMap.get(accountViewInfo.getId().toString())!=null)				
				{
					//���ܰ����ظ�ֵ
					if(!longNumberTempMap.containsKey(accountViewInfo.getLongNumber()))
					{
						accountList.add(accountMap.get(accountViewInfo.getId().toString()));
						longNumberTempMap.put(accountViewInfo.getLongNumber(), Boolean.TRUE);
					}
				}
			}
		}
		
		//Ŀ��ɱ�
		Map aimCostMapSum=getAimCostForNonLeaf(ctx,obj,leafPrjIds);
		Map aimCostMapDetail=getAimCostForNonLeafDetail(ctx,leafPrjIds);
		
		//�ڵ�Ƽ�
		Map nodeMeasureMapSum=getAcctNodeMeasureDataForNonLeaf(ctx,obj,leafPrjIds);
		Map nodeMeasureMapDetail=getAcctNodeMeasureDataForNonLeafDetail(ctx,leafPrjIds);
		
		//�������:�ۼƸ�����
		Map paySplitAmtMapSum=getCostSplitDataForNonLeaf(ctx, obj, leafPrjIds);
		Map paySplitAmtMapDetail=getCostSplitDataForNonLeafDetail(ctx, leafPrjIds);
		Map accountCostMap = new HashMap();
		Map accountNodeMeasureMap = new HashMap();
		Map accountPayAmtMap = new HashMap();
		
		//ȡ��������ݲ�ӳ��ɱ���Ŀ���ƿ�Ŀ�Ķ�Ӧ��ϵ,����������չʾ   by yong_zhou
		for(Iterator pIt = paySplitAmtMapDetail.keySet().iterator();pIt.hasNext();){
			String key = (String)pIt.next();
			BigDecimal amount = FDCHelper.toBigDecimal((BigDecimal)paySplitAmtMapDetail.get(key));
			CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)costAccountWithAccountMapAll.get(key);
			if(info!=null){
				String accountLongNumber = info.getAccount().getLongNumber();
				BigDecimal tempAmt = FDCHelper.toBigDecimal(accountPayAmtMap.get(accountLongNumber));
				accountPayAmtMap.put(accountLongNumber, FDCHelper.add(tempAmt, amount));
			}
		}
		for(Iterator pIt = nodeMeasureMapDetail.keySet().iterator();pIt.hasNext();){
			String key = (String)pIt.next();
			BigDecimal amount = FDCHelper.toBigDecimal((BigDecimal)nodeMeasureMapDetail.get(key));
			CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)costAccountWithAccountMapAll.get(key);
			if(info!=null){
				String accountLongNumber = info.getAccount().getLongNumber();
				BigDecimal tempAmt = FDCHelper.toBigDecimal(accountNodeMeasureMap.get(accountLongNumber));
				accountNodeMeasureMap.put(accountLongNumber, FDCHelper.add(tempAmt, amount));
			}
		}
		for(Iterator pIt = aimCostMapDetail.keySet().iterator();pIt.hasNext();){
			String key = (String)pIt.next();
			BigDecimal amount = FDCHelper.toBigDecimal((BigDecimal)aimCostMapDetail.get(key));
			CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)costAccountWithAccountMapAll.get(key);
			if(info!=null){
				String accountLongNumber = info.getAccount().getLongNumber();
				BigDecimal tempAmt = FDCHelper.toBigDecimal(accountCostMap.get(accountLongNumber));
				accountCostMap.put(accountLongNumber, FDCHelper.add(tempAmt, amount));
			}
		}
		Map accountAmtMap = new HashMap();
		accountAmtMap.put("accountCostMap", accountCostMap);
		accountAmtMap.put("accountNodeMeasureMap", accountNodeMeasureMap);
		accountAmtMap.put("accountPayAmtMap", accountPayAmtMap);
		
        Map returnMap = new HashMap();
        returnMap.put("costAccountList",costAccountList);
        returnMap.put("costAccountMap",costAccountMap);
        returnMap.put("costAccountWithAccountMapAll",costAccountWithAccountMapAll);
        returnMap.put("aimCostMap", aimCostMapSum);
        returnMap.put("nodeMeasureMap", nodeMeasureMapSum);
        returnMap.put("paySplitAmtMap", paySplitAmtMapSum);
        returnMap.put("accountAmtMap", accountAmtMap);
		returnMap.put("accountList",accountList);
		return returnMap;
		
	}
	/**
	 *  ѡ�нڵ��Ƿ���ϸ�ڵ�(������֯�ڵ�͹�����Ŀ�ڵ�)ʱȡ�ýڵ�֮��������ϸ������Ŀ���ۼƸ�����  by Cassiel_peng
	 */
	private Map getCostSplitDataForNonLeaf(Context ctx, Object object,Set subObjectIds) throws BOSException {
		Map paySplitAmyMapSum=new HashMap();
		if(subObjectIds==null||subObjectIds.size()==0)
			return paySplitAmyMapSum;
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("select costacc.flongnumber longnumber,sum(entry.FPaidAmount) amount ");
			builder.appendSql("from T_AIM_CostSplitEntry entry ");
			builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentID=head.fid ");
			builder.appendSql("inner join t_Fdc_Costaccount costacc on entry.FCostAccountID=costacc.fid ");
			builder.appendSql("where head.FCostBillType='PAYMENTSPLIT' and head.FIsInvalid=0 ");
			builder.appendSql("and costacc.fisleaf=1 and entry.FIsProduct=0 and ");
			builder.appendParam(" entry.fobjectId ",subObjectIds.toArray());
			builder.appendSql(" group by costacc.flongnumber  ");
			builder.appendSql(" order by costacc.flongnumber ");
			final IRowSet rowSet=builder.executeQuery();
			while(rowSet.next()){
				paySplitAmyMapSum.put(rowSet.getString("longnumber"), rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException();
		}
		return paySplitAmyMapSum;
	}
	/***
	 * ��ȡ������ϸ�ڵ�ģ����ۼƸ��������ϸ������ϸ������Ŀ�֡�����     by yong_zhou
	 * ���ؽ��
	 * key = ������ĿId + '_' + �ɱ���Ŀid
	 * value = ��ϸ������Ŀ ��ֵ��˿�Ŀ�� �����ֽ��
	 * @param ctx
	 * @param subObjectIds
	 * @return
	 * @throws BOSException
	 */
	private Map getCostSplitDataForNonLeafDetail(Context ctx, Set subObjectIds) throws BOSException {
		Map paySplitAmyMapDetail=new HashMap();
		if(subObjectIds==null||subObjectIds.size()==0)
			return paySplitAmyMapDetail;
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("select costacc.fcurproject curprojectid,costacc.flongnumber longnumber,sum(entry.FPaidAmount) amount ");
			builder.appendSql(" from T_AIM_CostSplitEntry entry ");
			builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentID=head.fid ");
			builder.appendSql(" inner join t_Fdc_Costaccount costacc on entry.FCostAccountID=costacc.fid ");
			builder.appendSql(" where head.FCostBillType='PAYMENTSPLIT' and head.FIsInvalid=0 ");
			builder.appendSql(" and costacc.fisleaf=1 and entry.FIsProduct=0  and ");
			builder.appendParam("costacc.fcurproject ",subObjectIds.toArray());
			builder.appendSql(" group by costacc.fcurproject ,costacc.flongnumber  ");
			builder.appendSql(" order by costacc.fcurproject ,costacc.flongnumber ");
			final IRowSet rowSet=builder.executeQuery();
			while(rowSet.next()){
				paySplitAmyMapDetail.put(rowSet.getString("curprojectid") + "_" + rowSet.getString("longnumber"), rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException();
		}
		return paySplitAmyMapDetail;
	}
	/***
	 * ѡ�нڵ��Ƿ���ϸ�ڵ�(������֯�ڵ�͹�����Ŀ�ڵ�)ʱȡ�ýڵ�֮��������ϸ������Ŀ��Ŀ��ɱ�  by Cassiel_peng
	 */
	private Map getAimCostForNonLeaf(Context ctx, Object object,Set subObjectIds) throws BOSException {
		Map aimCostMapSum=new HashMap();
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.clear();
			builder.appendSql("select parentAcct.flongnumber longnumber,sum(entry.fcostamount) as amount ");
			builder.appendSql("from T_AIM_AimCost head ");
			builder.appendSql("inner join T_AIM_CostEntry entry on entry.fheadid=head.fid ");
			builder.appendSql("inner join t_fdc_costaccount acc on entry.fcostaccountid=acc.fid ");
			builder.appendSql("inner join t_fdc_costaccount parentAcct on charindex(parentAcct.flongnumber||'!',acc.flongnumber||'!')=1 ");
			builder.appendSql("where head.fisLastVersion=1 and parentAcct.fisleaf=1 ");
			if(object instanceof  CurProjectInfo){
				builder.appendParam("and parentAcct.fcurProject ",((CurProjectInfo)object).getId().toString());
			}else{
				builder.appendParam(" and parentAcct.ffullorgunit ",((FullOrgUnitInfo)object).getId().toString());
			}
			builder.appendParam("and  head.ForgOrProId",subObjectIds.toArray());
			builder.appendSql("group by parentAcct.flongnumber");
			final IRowSet rowSet=builder.executeQuery();
			while(rowSet.next()){
				aimCostMapSum.put(rowSet.getString("longnumber"), rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return aimCostMapSum;
	}
	/**
	 * ѡ�нڵ��Ƿ���ϸ�ڵ�(������֯�ڵ�͹�����Ŀ�ڵ�)ʱȡ�ýڵ�֮��������ϸ������Ŀ��Ŀ��ɱ�  by yong_zhou 
	 */
	private Map getAimCostForNonLeafDetail(Context ctx, Set subObjectIds) throws BOSException {
		Map aimCostMapDetail=new HashMap();
		if(subObjectIds==null||subObjectIds.size()==0)
			return aimCostMapDetail;
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.clear();
			builder.appendSql("select acc.fcurproject curprojectid,acc.flongnumber longnumber,sum(entry.fcostamount) as amount ");
			builder.appendSql("from T_AIM_AimCost head ");
			builder.appendSql("inner join T_AIM_CostEntry entry on entry.fheadid=head.fid ");
			builder.appendSql("inner join t_fdc_costaccount acc on entry.fcostaccountid=acc.fid ");
			builder.appendSql(" where head.fisLastVersion=1 and acc.fisleaf=1 and ");
			builder.appendParam("acc.fcurProject ",subObjectIds.toArray());
			builder.appendParam("and  head.ForgOrProId",subObjectIds.toArray());
			builder.appendSql(" group by acc.fcurproject,acc.flongnumber");
			
			final IRowSet rowSet=builder.executeQuery();
			while(rowSet.next()){
				aimCostMapDetail.put(rowSet.getString("curprojectid")+"_"+rowSet.getString("longnumber"), rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return aimCostMapDetail;
	}
	/**
	 * ѡ�нڵ��Ƿ���ϸ�ڵ�(������֯�ڵ�͹�����Ŀ�ڵ�)�Ľڵ�Ƽ� by Cassiel_peng
	 */
	private Map getAcctNodeMeasureDataForNonLeaf(Context ctx,Object object,Set subObjectIds) throws BOSException {
		Map nodeMeasureMapSum=new HashMap();
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.clear();
			builder.appendSql("select parentAcct.flongnumber longnumber,sum(entry.FNodeAmount) as amount ");
			builder.appendSql("from T_CON_NodeMeasureEntry entry ");
			builder.appendSql("inner join T_CON_NodeMeasure head on entry.FParentId=head.fid ");
			builder.appendSql("inner join t_fdc_costaccount acc on entry.fcostaccountid=acc.fid ");
			builder.appendSql("inner join t_fdc_costaccount parentAcct on charindex(parentAcct.flongnumber||'!',acc.flongnumber||'!')=1 ");
			builder.appendSql("where  parentAcct.fisleaf=1 ");
			if(object instanceof  CurProjectInfo){
				builder.appendParam("and parentAcct.fcurProject ",((CurProjectInfo)object).getId().toString());
			}else{
				builder.appendParam("and parentAcct.ffullorgunit ",((FullOrgUnitInfo)object).getId().toString());
			}
			builder.appendSql("group by parentAcct.flongnumber");
			final IRowSet rowSet=builder.executeQuery();
			while(rowSet.next()){
				nodeMeasureMapSum.put(rowSet.getString("longnumber"), rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return nodeMeasureMapSum;
	}
	/**
	 * ��ȡĳһ��������Ŀ(��������ϸ������Ŀ�µ�������ϸ������Ŀ)�Ľڵ�Ƽ� by yong_zhou
	 */
	private Map getAcctNodeMeasureDataForNonLeafDetail(Context ctx,Set subObjectIds) throws BOSException {
		Map nodeMeasureMapDetail=new HashMap();
		if(subObjectIds==null||subObjectIds.size()==0)
			return nodeMeasureMapDetail;
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("select acc.fcurproject curprojectid,acc.flongnumber longnumber,sum(entry.FNodeAmount) as amount ");
			builder.appendSql(" from T_CON_NodeMeasureEntry entry ");
			builder.appendSql(" inner join T_CON_NodeMeasure head on entry.FParentId=head.fid ");
			builder.appendSql(" inner join t_fdc_costaccount acc on entry.fcostaccountid=acc.fid ");
			builder.appendSql(" where  acc.fisleaf=1 and ");
			builder.appendParam("acc.fcurProject ",subObjectIds.toArray());
			builder.appendSql("group by acc.fcurproject,acc.flongnumber");
			final IRowSet rowSet=builder.executeQuery();
			while(rowSet.next()){
				nodeMeasureMapDetail.put(rowSet.getString("curprojectid")+"_"+rowSet.getString("longnumber"), rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return nodeMeasureMapDetail;
	}
	/**
	 * ��ȡ��ϸ������Ŀ�¸�����r��Ľ����
	 */
	protected Map _getPaymentCostStatistics(Context ctx, Map param) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		String prjId=(String)param.get("prjId");
		if(prjId==null){
			throw new NullPointerException("prjId cann't be null!");
		}
		
		Set curProjectIds = new HashSet();
		curProjectIds.add(prjId);
		//��ȡ������Ŀ�µ����гɱ���Ŀ���ƿ�Ŀ�������Ӧ��ϵ
		List costAccountList = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
	
		 // ȡ������Ŀ�����еĳɱ���Ŀ
		ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		view.getSelector().clear();
		view.getSelector().add("id");
		view.getSelector().add("name");
		view.getSelector().add("longnumber");
		view.getSelector().add("level");
		view.getSelector().add("isLeaf");
		view.getSelector().add("parent.id");
		view.getSelector().add("curProject.id");
		view.getFilter().getFilterItems().clear();
		view.getFilter().getFilterItems().add(new FilterItemInfo("curProject.id",curProjectIds,CompareType.INCLUDE));
		SorterItemInfo sorterItem = new SorterItemInfo();
		sorterItem.setPropertyName("longnumber");
		sorterItem.setSortType(SortType.ASCEND);
		view.getSorter().add(sorterItem);
		CostAccountCollection costAccountColl = iCostAccount.getCostAccountCollection(view);
		for(Iterator it=costAccountColl.iterator();it.hasNext();){
			CostAccountInfo costAccountInfo = (CostAccountInfo)it.next();
			costAccountList.add(costAccountInfo);
		}
		
		Map costAccountMap = FDCUtils.getCostAccountMap(ctx,curProjectIds);
		//   �׳��쳣��û���ҵ��ɱ���Ŀ���ƿ�Ŀ�Ķ�Ӧ��ϵ������ʾΪ�հף�����ʾ�Ǹ�ɶ�ɱ���Ŀ��
		//   ʱС��˵��ʹû���ҵ��ɱ���Ŀ���ƿ�Ŀ�Ķ�Ӧ��ϵ��ҲӦ�ý���ӵ�еĳɱ���Ŀ�����Ϣ��ʾ���� 
		Map	costAccountWithAccountMapAll =null;
		try{
			costAccountWithAccountMapAll = FDCUtils.getCostAccountWithAccountMapAll(ctx,curProjectIds,null);
		}catch(ContractException e){
			logger.error(e.getMessage(), e);
			costAccountWithAccountMapAll=new HashMap();
		}
		
		Map accountMap = new HashMap();
		for(Iterator it = costAccountWithAccountMapAll.keySet().iterator();it.hasNext();){
			String key = (String) it.next();
			CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)costAccountWithAccountMapAll.get(key);
			
			if(info!=null&&
			   info.getAccount()!=null&&
			   info.getAccount().getId()!=null&&
			   !accountMap.containsKey(info.getAccount().getId().toString())){
				accountMap.put(info.getAccount().getId().toString(),info.getAccount());
			}
		}
		//��ȡĳһ��������Ŀ�µ����п�Ŀ��Ŀ��ɱ�
		Map aimCostMap=getAcctAimCostData(ctx, prjId);
		//��ȡĳһ��������Ŀ�µ����п�Ŀ�Ľڵ�Ƽ�
		Map nodeMeasureMap=getAcctNodeMeasureData(ctx,prjId);
		//��ȡ�����¼�͸����ֵĹ���������
		List payList = new ArrayList();
		Map payListMap = new HashMap();
		Map paySplitAmtMap = new HashMap();
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			//������
			builder.appendSql("select entry.FCostAccountID acctid,pay.fid payid,pay.fpaydate paydate,sum(entry.FPayedAmt) amount ");
			//builder.appendSql("from T_AIM_CostSplitEntry entry ");
			builder.appendSql("from T_FNC_PaymentSplitEntry entry ");
			builder.appendSql("inner join T_FNC_PaymentSplit head on entry.FParentID=head.FId ");
			builder.appendSql("inner join t_fdc_costAccount costacc on entry.FCostAccountId=costacc.FId ");
			builder.appendSql("inner join T_CAS_PAYMENTBILL pay on head.FPaymentBillID=pay.FId ");//head.FCostBillID=pay.FId
			builder.appendSql("where   costacc.FCurProject=? "); //--FCostBillType='PAYMENTSPLIT' and
			builder.appendSql("And head.FIsInvalid=0 And entry.FisLeaf=1 ");//FIsProduct=0
			builder.appendSql("group by entry.FCostAccountID,pay.fid,pay.fpaydate ");
			builder.appendSql("order by pay.fpaydate asc ");
			
			builder.addParam(prjId);
			final IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				paySplitAmtMap.put(rowSet.getString("payid")+"_"+rowSet.getString("acctid"), rowSet.getBigDecimal("amount"));
				Map payment = new HashMap();
				payment.put("payid",rowSet.getString("payid"));
				payment.put("paydate",rowSet.getDate("paydate"));
				if(!payListMap.containsKey(rowSet.getString("payid")))
				{
					payList.add(payment);
					payListMap.put(rowSet.getString("payid"),payment);
				}
			}

		} catch (SQLException e) {
			throw new BOSException(e);
		}
		List accountList = new ArrayList();
		if(accountMap.size()>0){
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",accountMap.keySet(),CompareType.INCLUDE));
			view.getSorter().add(sorterItem);
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("longnumber");
			AccountViewCollection accountViewColl = AccountViewFactory.getLocalInstance(ctx).getAccountViewCollection(view);
			for(Iterator it=accountViewColl.iterator();it.hasNext();){
				AccountViewInfo accountViewInfo = (AccountViewInfo)it.next();
				if(accountMap!=null&&accountMap.containsKey(accountViewInfo.getId().toString())&&accountMap.get(accountViewInfo.getId().toString())!=null)
					accountList.add(accountMap.get(accountViewInfo.getId().toString()));
			}
		}
		
		Map returnMap = new HashMap();
		returnMap.put("costAccountList",costAccountList);
		returnMap.put("costAccountMap",costAccountMap);
		returnMap.put("costAccountWithAccountMapAll",costAccountWithAccountMapAll);
		returnMap.put("accountList",accountList);
		//returnMap.put("accountMap",accountMap);
		returnMap.put("aimCostMap",aimCostMap);
		returnMap.put("nodeMeasureMap",nodeMeasureMap);
		returnMap.put("payList",payList);
		returnMap.put("paySplitAmtMap",paySplitAmtMap);
		return returnMap;
	}
	
	private Map getAcctNodeMeasureData(Context ctx, String objectId) throws BOSException {
		Map nodeMeasureMap = new HashMap();
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder
					.appendSql("select entry.FcostAccountId as acctid,sum(entry.FNodeAmount) as amount from T_CON_NodeMeasureEntry entry inner join T_CON_NodeMeasure head on entry.FParentId=head.fid where head.FCurProjectId=? group by entry.fcostaccountid ");
			builder.addParam(objectId);
			final IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				nodeMeasureMap.put(rowSet.getString("acctid"), rowSet.getBigDecimal("amount"));
			}

		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return nodeMeasureMap;

	}

	/**
	 * ������������Ŀ������ȡ��
	 * @author pengwei_hou 
	 * @date date:2009-07-16
	 */
	protected Map _getProjectAnalysisCost(Context ctx, Map param)
			throws BOSException, EASBizException {
		Map retMap = new HashMap();
		String orgUnitID = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		//��ǰ��֯������Ŀ
		Map projectData = FDCUtils.getOrgUnitProjects(ctx, orgUnitID);
		//��һ������Ŀ��ɱ�
		Map firstAimCostData = this.getAcctAimCostData(ctx, new HashSet(projectData.keySet()), false);
		//�����޶�������Ŀ��ɱ�
		Map lastAimCostData = this.getAcctAimCostData(ctx, new HashSet(projectData.keySet()), true);
		//��ִͬ����Ϣ
		Map conInfosData = this.getProjectConInfos(ctx, new HashSet(projectData.keySet()));
		//�����ϣ�Ϊ�˱�������
		retMap.put("projectData", projectData.get("colls"));
		retMap.put("projectIDSet", new HashSet(projectData.keySet()));
		retMap.put("firstAimCostData", firstAimCostData);
		retMap.put("lastAimCostData", lastAimCostData);
		retMap.put("conInfosData", conInfosData);
		return retMap;
	}
	private Map getAcctAimCostData(Context ctx, Set idSet, boolean isRecense) throws BOSException {
		Map aimCostMap = new HashMap();
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			if (isRecense) {
				builder
						.appendSql(" select head.FVersionNumber,acct.FLongNumber as longNumber,sum(entry.fcostamount) as amount,project.fid as projectID from T_AIM_AimCost head ");
				builder
						.appendSql(" inner join T_AIM_CostEntry entry on entry.fheadid=head.fid ");
				builder
						.appendSql(" inner join T_FDC_CurProject project on project.fid = head.ForgOrProId ");
				builder
						.appendSql(" inner join T_FDC_CostAccount childAcct on childAcct.FID = entry.fcostaccountid ");
				builder
						.appendSql(" inner join T_FDC_CostAccount acct on charindex(acct.flongnumber||'!',childAcct.flongnumber||'!')=1  ");
				// builder
				// .appendSql(
				// " inner join T_FDC_CostAccount acct on acct.FID = entry.fcostaccountid "
				// );
				builder.appendSql(" where ");
				builder.appendParam("head.FOrgOrProId", idSet.toArray());
				builder.appendParam(" and acct.ffullorgunit ",
						OrgConstants.DEF_CU_ID);
				builder.appendSql(" and head.fisLastVersion = 1 ");
//				builder.appendSql(" and head.FVersionNumber > 1");
				builder.appendSql(" and head.fauditorid is not null ");
				builder.appendSql(" and acct.FIsEnable = 1");
				builder.appendSql(" and acct.fisleaf = 1");
				builder
						.appendSql(" group by acct.FLongNumber,project.fid,head.FVersionNumber ");
			} else {
				builder
						.appendSql(" select head.FVersionNumber as Number,acct.FLongNumber as longNumber,sum(entry.fcostamount) as amount,project.fid as projectID from T_AIM_AimCost head ");
				builder
						.appendSql(" inner join T_AIM_CostEntry entry on entry.fheadid=head.fid ");
				builder
						.appendSql(" inner join T_FDC_CurProject project on project.fid = head.ForgOrProId ");
				builder
						.appendSql(" inner join T_FDC_CostAccount childAcct on childAcct.FID = entry.fcostaccountid ");
				builder
						.appendSql(" inner join T_FDC_CostAccount acct on charindex(acct.flongnumber||'!',childAcct.flongnumber||'!')=1  ");
				// builder
				// .appendSql(
				// " inner join T_FDC_CostAccount acct on acct.FID = entry.fcostaccountid "
				// );
				builder.appendSql(" where ");
				builder.appendParam("head.FOrgOrProId", idSet.toArray());
				builder.appendParam(" and acct.ffullorgunit",
						OrgConstants.DEF_CU_ID);
				//��һ�汾����ʾ���޶�ֻ�����������ʾ
//				builder.appendSql(" and  head.fauditorid is not null ");
				builder.appendSql(" and TO_NUMBER(head.FVersionNumber) = 1");//TO_NUMBER db2 ���ͱ���һ�� by hpw 2010.10.25
				builder.appendSql(" and acct.FIsEnable = 1");
				builder.appendSql(" and acct.fisleaf = 1");
				builder
						.appendSql(" group by acct.FLongNumber,project.fid,head.FVersionNumber ");
			}
			final IRowSet rowSet = builder.executeQuery();
			String assAllPrj = "assAllPrj";
			while (rowSet.next()) {
				String projectID = rowSet.getString("projectID");
				BigDecimal amount = rowSet.getBigDecimal("amount");
				String key = rowSet.getString("longNumber") + "_" + projectID;
				aimCostMap.put(key, amount);

				if (isRecense) {
					// �����ܽ��=��Ŀ����ϸ��Ŀ�޶����Ļ��ܡ�
					String assKey = "ass_" + projectID;
					if (aimCostMap.containsKey(assKey)) {
						aimCostMap.put(assKey, FDCHelper.add(aimCostMap
								.get(assKey), amount));
					} else {
						aimCostMap.put(assKey, amount);
					}
					aimCostMap.put(assAllPrj, FDCHelper.add(aimCostMap
							.get(assAllPrj), amount));
				}
			}

		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return aimCostMap;
	}
	
	/**
	 * ̫��̬�ˣ�û�취����ʵ�֣��Ժ��� 
	 * @author pengwei_hou date 2009-07-20
	 * @param ctx
	 * @param idSet
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private Map getProjectConInfos(Context ctx, Set idSet) throws BOSException,
			EASBizException {

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("select sum(amount) as amount,sum(newAmount) as newAmount,sum(changeAmt) as changeAmt,sum(payAmt) as payAmt,type as type,project as project from ( \n ");
		builder
				.appendSql("select sum(con.FAmount) as amount,sum(con.FSettleAmt) as newAmount,sum(change.FBalanceAmount) as changeAmt,sum(pay.FActPayLocAmt) as payAmt,con.FContractTypeID as type,con.FCurProjectID as project from T_CON_ContractBill con \n ");
		builder.appendSql(" inner join T_FDC_ContractType t on t.fid = con.fcontracttypeid \n");
		builder
				.appendSql("left outer join T_CON_ContractChangeBill change on change.FContractBillID = con.FID \n ");
		builder
				.appendSql("left outer join (select sum(FActPayLocAmt) as FActPayLocAmt,FContractBillID from T_CAS_PAYMENTBILL where FBillStatus = 15 group by FContractBillID ) pay on pay.FContractBillID=con.FID  \n ");
		builder.appendSql("where con.FState='4AUDITTED' \n ");
		builder.appendSql(" and t.FIsEnabled =1 \n");
		builder
				.appendSql("and ( change.FState='4AUDITTED' or change.FState='8VISA' or change.FState='7ANNOUNCE' or change.FState IS NULL) \n ");
		builder.appendSql("and  con.FHasSettled=1  \n ");
		builder.appendSql("and \n ");
		builder.appendParam("con.FCurProjectID", idSet.toArray());
		builder
				.appendSql(" group by con.FContractTypeID,con.FCurProjectID \n ");
		builder.appendSql("union all \n  ");
		builder
				.appendSql("select sum(con.FAmount) as amount,sum(con.FAmount) as newAmount,sum(change.FAmount) as changeAmt,sum(pay.FActPayLocAmt) as payAmt,con.FContractTypeID as type,con.FCurProjectID as project from T_CON_ContractBill con \n ");
		builder.appendSql(" inner join T_FDC_ContractType t on t.fid = con.fcontracttypeid \n");
		builder
				.appendSql("left outer join T_CON_ContractChangeBill change on change.FContractBillID = con.FID  \n ");
		builder
				.appendSql("left outer join (select sum(FActPayLocAmt) as FActPayLocAmt,FContractBillID from T_CAS_PAYMENTBILL where FBillStatus = 15 group by FContractBillID ) pay on pay.FContractBillID=con.FID  \n ");
		builder.appendSql("where con.FState='4AUDITTED' \n ");
		builder.appendSql(" and t.FIsEnabled =1 \n");
		builder
				.appendSql("and ( change.FState='4AUDITTED' or change.FState='8VISA' or change.FState='7ANNOUNCE' or change.FState IS NULL) \n ");
		builder.appendSql("and  con.FHasSettled=0 \n  ");
		builder.appendSql("and ");
		builder.appendParam("con.FCurProjectID", idSet.toArray());
		builder
				.appendSql(" group by con.FContractTypeID,con.FCurProjectID \n ");

		builder.appendSql(") t GROUP BY type,project \n ");//db2 �Ӳ�ѯ����� by hpw

		
		IRowSet rowSet = builder.executeQuery();
		Map conInfosMap = new HashMap();
		String last_ = "last_";
		String pay_ = "pay_";
		String lastAllPrj = "lastAllPrj"; 
		String allPay = "allPay"; 
		Object[] info = null;
		Object[] total = null;
		try {
			while (rowSet.next()) {
				info = new Object[5];
				total = new Object[5];
				//��ͬ���
				info[0] = rowSet.getBigDecimal("amount");
				//��ͬ�������
				info[1] = FDCHelper.add(rowSet.getBigDecimal("newAmount"),rowSet.getBigDecimal("changeAmt"));
				//�䶯���=��ͬ�������-��ͬ���
				info[2] = FDCHelper.subtract(info[1], info[0]);
				//��֧��
				info[3] = rowSet.getBigDecimal("payAmt");
				//δ֧��=��ͬ�������-��֧��
				info[4] = FDCHelper.subtract(info[1], info[3]);
				String type = rowSet.getString("type");
				String projectID = rowSet.getString("project");
				//key=��ͬ����ID+������ĿID
				conInfosMap.put(type+"_"+projectID, info);
				if(conInfosMap.containsKey(type)){
					Object[] typeValue = (Object[])conInfosMap.get(type);
					total[0] = FDCHelper.add(typeValue[0],info[0]);
					total[1] = FDCHelper.add(typeValue[1],info[1]);
					total[2] = FDCHelper.add(typeValue[2],info[2]);
					total[3] = FDCHelper.add(typeValue[3],info[3]);
					total[4] = FDCHelper.add(typeValue[4],info[4]);
				}else{
					total[0] = info[0];
					total[1] = info[1];
					total[2] = info[2];
					total[3] = info[3];
					total[4] = info[4];
				}
				
				//���ݺ�ͬ���ͺϼ�
				conInfosMap.put(type, total);
				//��ǩ���ͬ�ϼ�=��ϸ��Ŀ����������ͬ��������ۻ���
				String lastKey = last_+projectID;
				if(conInfosMap.containsKey(lastKey)){
					conInfosMap.put(lastKey,FDCHelper.add(conInfosMap.get(lastKey),info[1]));
				}else{
					conInfosMap.put(lastKey, info[1]);
				}
				//���й�����Ŀ������ۺ�
				conInfosMap.put(lastAllPrj,FDCHelper.add(conInfosMap.get(lastAllPrj),info[1]));
				
				//��ϸ��Ŀ֧���ϼ� ȡ ��ϸ��Ŀ��֧�������ܽ��
				String payKey = pay_+projectID;
				if(conInfosMap.containsKey(payKey)){
					conInfosMap.put(payKey,FDCHelper.add(conInfosMap.get(payKey),info[3]));
				}else{
					conInfosMap.put(payKey, info[3]);
				}
				conInfosMap.put(allPay, FDCHelper.add(conInfosMap.get(allPay),info[3]));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return conInfosMap;

	}

	/**
	 * �������½ᱨ��ȡ��
	 * sql ���Ż� ά��ʱ����ע���������Ĳ�֣���һ�ֲ�ֽ�sqlץ�����޸ĺú��ٷŽ�������
	 * @author Pengwei_hou
	 * modify: Owen_wen 2010-08-10 �Ż��˴���
	 * @param isCost:true�ɱ��½ᣬfalse�������½�
	 * @param param 
	 * @throws  BOSException,EASBizException
	 */
	protected Map _getCostCloseData(Context ctx, boolean isCost, Map param)
			throws BOSException, EASBizException {
		String object = (String) param.get("periodNumber");
		Integer periodNumber = new Integer(object);
		
		Object[] arrayList = new HashSet((Set)param.get("prjIdSet")).toArray();
		Map retMap = new HashMap();
		Map costClose = new HashMap();
		Map costUnClose = new HashMap();
//		Map unCloseDetail = new HashMap();�������ͻ��˴����Ƶ������
		//״̬ͳ��
		Object[] statistics = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		IRowSet rs = null;
		if(isCost){
			//���½�
			builder.appendSql(" select fnumber,FCurProjectID,entity,fsplitstate,sum(count) as count from ( \n");
			//TODO ���Ż�
			addSplitTable("T_CON_ContractCostSplit",null,null,"con",periodNumber, arrayList,builder,isCost,true);
			builder.appendSql(" union all \n");
			addSplitTable("T_CON_ConChangeSplit",null,null,"change",periodNumber, arrayList,builder,isCost,true);
			builder.appendSql(" union all \n");
			addSplitTable("T_CON_SettlementCostSplit",null,null,"settle",periodNumber, arrayList,builder,isCost,true);
			builder.appendSql(" union all \n");
			addSplitTable("t_fnc_paymentsplit",null,null,"workload",periodNumber, arrayList,builder,isCost,true);
			builder.appendSql(" union all \n");
			addSplitTable("t_fnc_paymentsplit",null,null,"conWithout",periodNumber, arrayList,builder,isCost,true);
			builder.appendSql(")t group by fnumber,FCurProjectID,entity,fsplitstate \n");
		
			rs = builder.executeQuery();
			try {
				while(rs.next()){
					int count = rs.getInt("count");
					String splitState = rs.getString("fsplitState"); 
					String key = rs.getString("fnumber")+rs.getString("fcurprojectid")+rs.getString("entity");
					statistics = new Object[2];
					statistics[0] = FDCHelper.toBigDecimal(count);
					if(CostSplitStateEnum.ALLSPLIT_VALUE.equals(splitState)){
						statistics[1] = FDCHelper.toBigDecimal(count);
					}
					if(costClose.containsKey(key)){
						Object[] addStatistics =(Object[])costClose.get(key);
						addStatistics[0] = FDCHelper.add(statistics[0], FDCHelper.toBigDecimal(count));
						addStatistics[1] = FDCHelper.add(statistics[1], FDCHelper.toBigDecimal(count));
					}else{
						costClose.put(key, statistics);
					}
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
			
			//δ�½�
			builder.clear();
			builder.appendSql(" select fnumber,FCurProjectID,entity,fstate,fsplitstate,sum(count) as count from ( \n");
			addSplitTable("T_CON_ContractCostSplit","t_con_contractbill","fcontractbillid","con",periodNumber, arrayList,builder,isCost,false);
			builder.appendSql(" union all \n");
			addSplitTable("T_CON_ConChangeSplit","t_con_contractchangebill","FContractChangeID","change",periodNumber, arrayList,builder,isCost,false);
			builder.appendSql(" union all \n");
			addSplitTable("T_CON_SettlementCostSplit","T_CON_ContractSettlementBill","FSettlementBillID","settle",periodNumber, arrayList,builder,isCost,false);
			builder.appendSql(" union all \n");
			addSplitTable("t_fnc_paymentsplit","t_fnc_workloadconfirmbill","fworkloadbillid","workload",periodNumber, arrayList,builder,isCost,false);
			builder.appendSql(" union all \n");
			addSplitTable("t_fnc_paymentsplit","t_con_contractwithouttext","FConWithoutTextID","conWithout",periodNumber, arrayList,builder,isCost,false);
			builder.appendSql(")t group by fnumber,FCurProjectID,entity,fstate,fsplitstate \n");
			
			rs = builder.executeQuery();
			try {
				while(rs.next()){
					BigDecimal count = FDCHelper.toBigDecimal(rs.getInt("count"));
					String state = rs.getString("fstate");
					String splitState = rs.getString("fsplitState"); 
					String entity = rs.getString("entity");
					String key = rs.getString("fnumber")+rs.getString("fcurprojectid")+ entity;
					statistics = new Object[6];
					if(FDCBillStateEnum.SAVED_VALUE.equals(state)){
						statistics[0] = count;
					} else if (FDCBillStateEnum.SUBMITTED_VALUE.equals(state)
							|| FDCBillStateEnum.AUDITTING_VALUE.equals(state)
					) {
						statistics[1] = count;
					} else if (FDCBillStateEnum.AUDITTED_VALUE.equals(state)
							|| FDCBillStateEnum.ANNOUNCE_VALUE.equals(state)
							|| FDCBillStateEnum.VISA_VALUE.equals(state)
							|| "15".equals(state)
					) {
						statistics[2] = count;
					}
					if (CostSplitStateEnum.ALLSPLIT_VALUE
							.equals(splitState)) {
						statistics[5] = count;
					} else if (CostSplitStateEnum.PARTSPLIT_VALUE
							.equals(splitState)) {
						statistics[4] = count;
					} else if (FDCBillStateEnum.AUDITTED_VALUE.equals(state)
							&& !CostSplitStateEnum.PARTSPLIT_VALUE
									.equals(splitState)
							&& !CostSplitStateEnum.ALLSPLIT_VALUE
									.equals(splitState)) {
//						if (!"conWithout".equals(entity))
							statistics[3] = FDCHelper.toBigDecimal(count);
					}
					
					if(costUnClose.containsKey(key)){
						Object[] addStatistics =(Object[])costUnClose.get(key);
						addStatistics[0] = FDCHelper.add(addStatistics[0], statistics[0]);
						addStatistics[1] = FDCHelper.add(addStatistics[1], statistics[1]);
						addStatistics[2] = FDCHelper.add(addStatistics[2], statistics[2]);
						addStatistics[3] = FDCHelper.add(addStatistics[3], statistics[3]);
						addStatistics[4] = FDCHelper.add(addStatistics[4], statistics[4]);
						addStatistics[5] = FDCHelper.add(addStatistics[5], statistics[5]);
//						costUnClose.put(key, addStatistics);
					}else{
						costUnClose.put(key, statistics);
					}
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		// ����ɱ��½�
		}else{
			
			//����
			builder.clear();
			builder.appendSql(" select fnumber,FCurProjectID,entity,fsplitstate,sum(count) as count from ( \n");
			addSplitTable("T_FNC_PaymentSplit",null,null,"paySplit",periodNumber, arrayList,builder,isCost,true);
			builder.appendSql(" union all \n");
			addSplitTable("T_FNC_PaymentSplit",null,null,"payNoTextSplit",periodNumber, arrayList,builder,isCost,true);
			builder.appendSql(" union all \n");
			addSplitTable("t_con_contractcostsplit",null,null,"invalidCon",periodNumber, arrayList,builder,isCost,true);
			builder.appendSql(" union all \n");
			addSplitTable("t_fnc_paymentsplit",null,null,"invalidConWithout",periodNumber, arrayList,builder,isCost,true);
			builder.appendSql(")t group by fnumber,FCurProjectID,entity,fsplitstate \n");
			rs = builder.executeQuery();
			try {
				while(rs.next()){
					int count = rs.getInt("count");
					String splitState = rs.getString("fsplitState"); 
					String period = rs.getString("fnumber");
					String entity = rs.getString("entity");
					String key = period+rs.getString("fcurprojectid")+entity;
					statistics = new Object[2];
					statistics[0] = FDCHelper.toBigDecimal(count);
					if(CostSplitStateEnum.ALLSPLIT_VALUE.equals(splitState)){
						statistics[1] = FDCHelper.toBigDecimal(count);
					}
					if(costClose.containsKey(key)){
						Object[] addStatistics =(Object[])costClose.get(key);
						addStatistics[0] = FDCHelper.add(statistics[0], FDCHelper.toBigDecimal(count));
						addStatistics[1] = FDCHelper.add(statistics[1], FDCHelper.toBigDecimal(count));
					}else{
						costClose.put(key, statistics);
					}
				}
			}catch (SQLException e) {
				throw new BOSException(e);
			}
			//δ�½�--���
			//Update by zhiyuan_tang 2010-01-12 �ݻ����ݲ���ʾ
			builder.clear();
			builder.appendSql(" select period.fnumber,bill.FCurProjectID,bill.fbillstatus,split.fsplitstate,'paySplit' entity,count(*) as count from t_cas_paymentbill bill \n"); 
			builder.appendSql(" inner join t_con_contractbill con on con.fid=bill.fcontractbillid \n");
			builder.appendSql(" inner join t_con_payrequestbill req on req.fid=bill.ffdcpayreqid \n");
			builder.appendSql(" inner join t_bd_period period on period.fid=req.fperiodid \n");
			builder.appendSql(" left outer join t_fnc_paymentsplit split on split.fpaymentbillid=bill.fid \n");
			builder.appendSql(" left outer join T_FNC_FDCPaymentBill fdcPayment on fdcPayment.fpaymentbillid=bill.fid \n");
			builder.appendSql(" where (bill.fbillstatus =10 or bill.fbillstatus=11 or bill.fbillstatus =12 or bill.fbillstatus =15 or split.fsplitstate is null) \n");
			builder.appendSql("	and  period.fnumber<=? \n");
			builder.addParam(periodNumber);
			builder.appendSql("	 and (fdcPayment.fisRespite is null or fdcPayment.fisRespite = 0) \n");
			builder.appendSql("	 and \n");
			builder.appendParam("bill.FCurProjectID",arrayList);
			builder.appendSql("	 and \n");
			builder.appendParam("con.fiscostsplit",Boolean.TRUE);
			builder.appendSql(" and (split.fstate <> '9INVALID' or split.fstate ='4AUDITTED' or split.fstate='1SAVED' or split.fstate is null) \n");
			builder.appendSql("  and split.fid not in (  \n");
			builder.appendSql("  select closee.fobjectid from t_fnc_settledmonthly closee where closee.fcurprojectid=split.FCurProjectID) \n"); 
			builder.appendSql("  group by period.fnumber,bill.FCurProjectID,bill.fbillstatus,split.fsplitstate  \n");
			rs = builder.executeQuery();
			try {
				while(rs.next()){
					BigDecimal count = FDCHelper.toBigDecimal(rs.getInt("count"));
					int state = rs.getInt("fbillstatus");
					String splitState = rs.getString("fsplitState"); 
					String entity = rs.getString("entity");
					String key = rs.getString("fnumber")+rs.getString("fcurprojectid")+ entity;
					statistics = new Object[6];
					if(BillStatusEnum.SAVE_VALUE==state){
						statistics[0] = count;
					}else if(BillStatusEnum.SUBMIT_VALUE==state){
						statistics[1] = count;
					}else if(BillStatusEnum.AUDITED_VALUE==state || BillStatusEnum.AUDITING_VALUE==state || BillStatusEnum.PAYED_VALUE==state){
						statistics[2] = count;
					}
					if (CostSplitStateEnum.ALLSPLIT_VALUE
							.equals(splitState)) {
						statistics[5] = count;
					} else if (CostSplitStateEnum.PARTSPLIT_VALUE
							.equals(splitState)) {
						statistics[4] = count;
					} else if (BillStatusEnum.AUDITED_VALUE==state
							&& !CostSplitStateEnum.PARTSPLIT_VALUE
									.equals(splitState)
							&& !CostSplitStateEnum.ALLSPLIT_VALUE
									.equals(splitState)) {
							statistics[3] = FDCHelper.toBigDecimal(count);
					}
					
					if(costUnClose.containsKey(key)){
						Object[] addStatistics =(Object[])costUnClose.get(key);
						addStatistics[0] = FDCHelper.add(addStatistics[0], statistics[0]);
						addStatistics[1] = FDCHelper.add(addStatistics[1], statistics[1]);
						addStatistics[2] = FDCHelper.add(addStatistics[2], statistics[2]);
						addStatistics[3] = FDCHelper.add(addStatistics[3], statistics[3]);
						addStatistics[4] = FDCHelper.add(addStatistics[4], statistics[4]);
						addStatistics[5] = FDCHelper.add(addStatistics[5], statistics[5]);
					}else{
						costUnClose.put(key, statistics);
					}
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
			//δ�½ᣬ���ı�����
			builder.clear();
			builder.appendSql(" select period.fnumber,bill.FCurProjectID,bill.fbillstatus,split.fsplitstate,'payNoTextSplit' entity,count(*) as count from t_cas_paymentbill bill \n"); 
			builder.appendSql("  inner join t_con_contractwithouttext con on con.fid=bill.fcontractbillid \n");
			builder.appendSql("  inner join t_con_payrequestbill req on req.fid=bill.ffdcpayreqid  \n");
			builder.appendSql("  inner join t_bd_period period on period.fid=req.fperiodid \n");
			builder.appendSql("  left outer join t_fnc_paymentsplit split on split.fpaymentbillid=bill.fid  \n");
			builder.appendSql("  where (bill.fbillstatus =10 or bill.fbillstatus=11 or bill.fbillstatus =12 or bill.fbillstatus =15 or split.fsplitstate is null) \n");
			builder.appendSql("	and  period.fnumber<=? \n");
			builder.addParam(periodNumber);
			builder.appendSql("	 and \n");
			builder.appendParam("bill.FCurProjectID",arrayList);
			builder.appendSql("	 and \n");
			builder.appendParam("con.fiscostsplit",Boolean.TRUE);
			builder.appendSql(" and (split.fstate <> '9INVALID' or split.fstate ='4AUDITTED' or split.fstate='1SAVED' or split.fstate is null) \n");
			builder.appendSql("  and split.fid not in (  \n");
			builder.appendSql("     select closee.fobjectid from t_fnc_settledmonthly closee where closee.fcurprojectid=split.FCurProjectID) \n"); 
			builder.appendSql("  group by period.fnumber,bill.FCurProjectID,bill.fbillstatus,split.fsplitstate  \n");
			rs = builder.executeQuery();
			try {
				while(rs.next()){
					BigDecimal count = FDCHelper.toBigDecimal(rs.getInt("count"));
					int state = rs.getInt("fbillstatus");
					String splitState = rs.getString("fsplitState"); 
					String entity = rs.getString("entity");
					String key = rs.getString("fnumber")+rs.getString("fcurprojectid")+ entity;
					statistics = new Object[6];
					if(BillStatusEnum.SAVE_VALUE==state){
						statistics[0] = count;
					}else if(BillStatusEnum.SUBMIT_VALUE==state){
						statistics[1] = count;
					}else if(BillStatusEnum.AUDITED_VALUE==state || BillStatusEnum.AUDITING_VALUE==state || BillStatusEnum.PAYED_VALUE==state){
						statistics[2] = count;
					}
					if (CostSplitStateEnum.ALLSPLIT_VALUE
							.equals(splitState)) {
						statistics[5] = count;
					} else if (CostSplitStateEnum.PARTSPLIT_VALUE
							.equals(splitState)) {
						statistics[4] = count;
					} else if (BillStatusEnum.AUDITED_VALUE==state
							&& !CostSplitStateEnum.PARTSPLIT_VALUE
									.equals(splitState)
							&& !CostSplitStateEnum.ALLSPLIT_VALUE
									.equals(splitState)) {
							statistics[3] = FDCHelper.toBigDecimal(count);
					}
					if(costUnClose.containsKey(key)){
						Object[] addStatistics =(Object[])costUnClose.get(key);
						addStatistics[0] = FDCHelper.add(addStatistics[0], statistics[0]);
						addStatistics[1] = FDCHelper.add(addStatistics[1], statistics[1]);
						addStatistics[2] = FDCHelper.add(addStatistics[2], statistics[2]);
						addStatistics[3] = FDCHelper.add(addStatistics[3], statistics[3]);
						addStatistics[4] = FDCHelper.add(addStatistics[4], statistics[4]);
						addStatistics[5] = FDCHelper.add(addStatistics[5], statistics[5]);
					}else{
						costUnClose.put(key, statistics);
					}
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
			
			///δ�½ᣬ�������ͬ
			builder.clear();
			builder.appendSql("  select period.fnumber,bill.FCurProjectID,'invalidCon' entity,count(*) as count from t_con_contractbill bill \n");
			builder.appendSql("  inner join t_bd_period period on period.fid=bill.fperiodid \n");
			builder.appendSql("	and  period.fnumber<=? \n");
			builder.addParam(periodNumber);
			builder.appendSql("  and bill.FConSplitExecState='INVALID' \n");
			builder.appendSql("  and \n");
			builder.appendParam("bill.FCurProjectID",arrayList);
			builder.appendSql(" and \n");
			builder.appendParam("bill.fiscostsplit", Boolean.TRUE);
			builder.appendSql("  group by period.fnumber,bill.FCurProjectID \n");
			rs = builder.executeQuery();
			try {
				while(rs.next()){
					BigDecimal count = FDCHelper.toBigDecimal(rs.getInt("count"));
					String entity = rs.getString("entity");
					String key = rs.getString("fnumber")+rs.getString("fcurprojectid")+ entity;
					statistics = new Object[6];
					statistics[1] = count;
					if(costUnClose.containsKey(key)){
						Object[] addStatistics =(Object[])costUnClose.get(key);
						addStatistics[1] = FDCHelper.add(addStatistics[1], statistics[1]);
					}else{
						costUnClose.put(key, statistics);
					}
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
			
			//δ�½ᣬ���ı�������
			builder.clear();
			builder.appendSql("  select period.fnumber,bill.FCurProjectID,'invalidConWithout' entity,count(*) as count from t_con_contractwithouttext bill \n");
			builder.appendSql("  inner join t_bd_period period on period.fid=bill.fperiodid \n");
			builder.appendSql("  where period.fnumber<=? \n");
			builder.addParam(periodNumber);
			builder.appendSql("  and bill.FConSplitExecState='INVALID'\n");
			builder.appendSql("  and \n");
			builder.appendParam("bill.FCurProjectID", arrayList);
			builder.appendSql(" and \n");
			builder.appendParam("bill.fiscostsplit", Boolean.TRUE);
			builder.appendSql("  group by period.fnumber,bill.FCurProjectID \n");
			rs = builder.executeQuery();
			try {
				while(rs.next()){
					BigDecimal count = FDCHelper.toBigDecimal(rs.getInt("count"));
					String entity = rs.getString("entity");
					String key = rs.getString("fnumber")+rs.getString("fcurprojectid")+ entity;
					statistics = new Object[6];
					statistics[1] = count;
					if(costUnClose.containsKey(key)){
						Object[] addStatistics =(Object[])costUnClose.get(key);
						addStatistics[1] = FDCHelper.add(addStatistics[1], statistics[1]);
					}else{
						costUnClose.put(key, statistics);
					}
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		retMap.put("costClose", costClose);
		retMap.put("costUnClose", costUnClose);
//		retMap.put("unCloseDetail", unCloseDetail);			
		return retMap;
	}
	
	//sql ̫ɢû������
	private void addSplitTable(String splitTable,String billTable,String field,String item,Integer periodNumber, Object[] arrayList,FDCSQLBuilder builder,boolean isCost,boolean isClose) {
		
		if(isCost){//�ɱ�
			if(isClose){//���½�
				
				builder.appendSql(" select period.fnumber,split.FCurProjectID,'"+item+"' entity,split.fsplitstate,count(*) as count from "+splitTable+" split \n");
				builder.appendSql(" inner join t_fnc_settledmonthly closee on closee.fobjectid=split.fid  \n");
				if("conWithout".equals(item)){
					builder.appendSql(" inner join t_con_contractwithouttext con on con.fid = closee.fcontractid \n");
				}else{
					builder.appendSql(" inner join t_con_contractbill con on con.fid = closee.fcontractid \n");
				}
				
				
				//�����߼�������½��һ����ֻᱣ����ÿ���ڼ��ȡ��С���ڼ�
				//db2��֧���Ӳ�ѯ�����ⲿ��  �Ӳ�ѯ���ⲿ������ͬ����ͬ�����ݲ�����������ⲿ���� start by hpw
				//builder.appendSql(" inner join t_bd_period period on (period.fid=closee.fsettleperiodid  and period.fnumber=(select min(pd.fnumber) from t_fnc_settledmonthly mon inner join t_bd_period pd on pd.fid= mon.fsettleperiodid where mon.fobjectid=split.fid)) \n");
				builder.appendSql("inner join t_bd_period period on period.fid=closee.fsettleperiodid \n");
				builder.appendSql("inner join ( \n");
				builder.appendSql("  select min(iperiod.fnumber) fnumber,isplit.fid from "+splitTable+" isplit \n");
				builder.appendSql("  inner join t_fnc_settledmonthly iclosee on iclosee.fobjectid=isplit.fid   \n");
				if("conWithout".equals(item)){
					builder.appendSql(" inner join t_con_contractwithouttext icon on icon.fid = iclosee.fcontractid \n");
				}else{
					builder.appendSql(" inner join t_con_contractbill icon on icon.fid = iclosee.fcontractid \n");
				}
				builder.appendSql("  inner join t_bd_period iperiod on iperiod.fid=iclosee.fsettleperiodid  \n");
				builder.appendSql("  where iperiod.fnumber<=? \n");
				builder.addParam(periodNumber);
				if("workload".equals(item)){
					builder.appendSql("and \n");
					builder.appendParam("isplit.fisworkloadbill", Boolean.TRUE);
				}
				builder.appendSql("  and isplit.fstate <> '9INVALID' \n");
				builder.appendSql("  and \n");
				if("workload".equals(item) || "conWithout".equals(item)){
					builder.appendParam("iclosee.fiscost",Boolean.FALSE);
				}else{
					builder.appendParam("iclosee.fiscost",Boolean.TRUE);
				}
				builder.appendSql("   and \n");
				builder.appendParam("icon.fiscostsplit",Boolean.TRUE);
				builder.appendSql("  and \n");
				builder.appendParam("isplit.FCurProjectID",arrayList);
				builder.appendSql("  group by isplit.fid \n");
				builder.appendSql("  ) osplit on osplit.fid=split.fid and osplit.fnumber=period.fnumber \n");
				//db2��֧���Ӳ�ѯ�����ⲿ��  end by hpw
				
				
				
				builder.appendSql(" where period.fnumber<=? \n");
				builder.addParam(periodNumber);
				if("workload".equals(item)){
					builder.appendSql(" and \n");
					builder.appendParam("split.fisworkloadbill", Boolean.TRUE);
				}
				builder.appendSql(" and split.fstate <> '9INVALID' \n");
				builder.appendSql(" and \n");
				if("workload".equals(item) || "conWithout".equals(item)){
					builder.appendParam("closee.fiscost",Boolean.FALSE);
				}else{
					builder.appendParam("closee.fiscost",Boolean.TRUE);
				}
				builder.appendSql(" and \n");
				builder.appendParam("con.fiscostsplit",Boolean.TRUE);
				builder.appendSql(" and \n");
				builder.appendParam("split.FCurProjectID",arrayList);   //�Ƶ�where�������ұߣ���Ϊ������Ч����С��ѯ��Χ
				builder.appendSql(" \n group by period.fnumber,split.FCurProjectID,split.fsplitstate \n");
			}else{//δ�½�
//				if("conWithout".equals(item)){
//					builder.appendSql("select period.fnumber,bill.FCurProjectID,bill.fstate,split.fsplitstate,'"+item+"' entity,count(*) as count from "+billTable+" bill, "+billTable+" split \n");
//					builder.appendSql(" inner join t_bd_period period on period.fid=split.fperiodid ");
//					builder.appendSql(" where bill.fid=split.fid and period.fnumber<=? \n");
//				}else{
					builder.appendSql(" select period.fnumber,bill.FCurProjectID,bill.fstate,split.fsplitstate,'"+item+"' entity,count(*) as count from "+billTable+" bill \n");
					builder.appendSql(" inner join t_bd_period period on period.fid=bill.fperiodid \n");
					if(!"con".equals(item) && !"conWithout".equals(item)){
						builder.appendSql(" inner join t_con_contractbill con on con.fid=bill.fcontractbillid \n");
					}
					builder.appendSql(" left outer join "+splitTable+" split on split."+field+"=bill.fid \n");
					builder.appendSql(" where (bill.fstate = '1SAVED' or bill.fstate='2SUBMITTED' or bill.fstate ='4AUDITTED' or bill.fstate ='7ANNOUNCE' or bill.fstate ='8VISA' or split.fsplitstate is null) and period.fnumber<=? \n");
//				}
				builder.addParam(periodNumber);
//				if("workload".equals(item)){
//					builder.appendSql(" and \n");
//					builder.appendParam("split.fisworkloadbill", Boolean.TRUE);
//				}
				//������ȷ�ϵ�δ�½����ݣ��������ݻ�����  Add by zhiyuan_tang 2010-01-12
				if ("t_fnc_workloadconfirmbill".equalsIgnoreCase(billTable)) {					
					builder.appendSql(" and ( bill.fisRespite is null or bill.fisRespite = 0 )  \n");
				}
				builder.appendSql(" and \n");
				if(item.startsWith("con")){
					builder.appendParam("bill.fiscostsplit",Boolean.TRUE);
				}else{
					builder.appendParam("con.fiscostsplit",Boolean.TRUE);
				}
				builder.appendSql(" and (split.fstate <> '9INVALID' or split.fstate ='4AUDITTED' or split.fstate='1SAVED' or split.fstate is null) \n");
				builder.appendSql(" and split.fid not in ( \n");
				builder.appendSql("    select closee.fobjectid from t_fnc_settledmonthly closee where closee.fcurprojectid=split.FCurProjectID) \n");
				builder.appendSql(" and \n");
				builder.appendParam("bill.FCurProjectID",arrayList); //�Ƶ�where�������ұߣ���Ϊ������Ч����С��ѯ��Χ
				builder.appendSql("\n group by period.fnumber,bill.FCurProjectID,bill.fstate,split.fsplitstate \n");
			}
		}else{//����
			if(isClose){//���½�
				builder.appendSql(" select period.fnumber,split.FCurProjectID,'"+item+"' entity,split.fsplitstate,count(*) as count from "+splitTable+" split \n");
				if("paySplit".equals(item) || "invalidCon".equals(item)){
					builder.appendSql(" inner join t_con_contractbill con on con.fid=split.fcontractbillid \n");
				}
				if("payNoTextSplit".equals(item) || "invalidConWithout".equals(item)){
					builder.appendSql(" inner join t_con_contractwithouttext con on con.fid=split.FConWithoutTextID \n");
				}
				builder.appendSql(" inner join t_fnc_settledmonthly closee on closee.fobjectid=split.fid \n");
				
				
				//�����߼�������½��һ����ֻᱣ����ÿ���ڼ��ȡ��С���ڼ�
				//db2��֧���Ӳ�ѯ�����ⲿ��  �Ӳ�ѯ���ⲿ������ͬ����ͬ�����ݲ�����������ⲿ���� start by hpw
				//builder.appendSql(" inner join t_bd_period period on period.fid=closee.fsettleperiodid  and period.fnumber=(select min(pd.fnumber) from t_fnc_settledmonthly mon inner join t_bd_period pd on pd.fid= mon.fsettleperiodid where mon.fobjectid=split.fid)\n");
				builder.appendSql("inner join t_bd_period period on period.fid=closee.fsettleperiodid \n");
				builder.appendSql("inner join ( \n");
				builder.appendSql("  select min(iperiod.fnumber) fnumber,isplit.fid from "+splitTable+" isplit \n");
				builder.appendSql("  inner join t_fnc_settledmonthly iclosee on iclosee.fobjectid=isplit.fid   \n");
				if("paySplit".equals(item) || "invalidCon".equals(item)){
					builder.appendSql(" inner join t_con_contractbill icon on icon.fid=isplit.fcontractbillid \n");
				}
				if("payNoTextSplit".equals(item) || "invalidConWithout".equals(item)){
					builder.appendSql(" inner join t_con_contractwithouttext icon on icon.fid=isplit.FConWithoutTextID \n");
				}
				builder.appendSql("  inner join t_bd_period iperiod on iperiod.fid=iclosee.fsettleperiodid  \n");
				builder.appendSql(" where iperiod.fnumber<=? \n");
				builder.addParam(periodNumber);
				if("paySplit".equals(item)){
					builder.appendSql(" and \n");
					builder.appendParam("isplit.fisworkloadbill", Boolean.FALSE);
				}
				if(item.startsWith("invalid")){
					builder.appendSql(" and \n");
					builder.appendParam("icon.FConSplitExecState", ConSplitExecStateEnum.INVALIDDID_VALUE);
				}
				builder.appendSql(" and isplit.fstate <>'9INVALID' ");
				builder.appendSql(" and \n");
				if("invalidCon".equals(item)){
					builder.appendParam("iclosee.fiscost",Boolean.TRUE);
				}else{
					builder.appendParam("iclosee.fiscost",Boolean.FALSE);
				}
				builder.appendSql(" and \n");
				builder.appendParam("icon.fiscostsplit", Boolean.TRUE);
				builder.appendSql(" and \n");
				builder.appendParam("isplit.FCurProjectID",arrayList);
				builder.appendSql("  group by isplit.fid \n");
				builder.appendSql("  ) osplit on osplit.fid=split.fid and osplit.fnumber=period.fnumber \n");
				//db2��֧���Ӳ�ѯ�����ⲿ��  end by hpw
				
				
				
				
				
				builder.appendSql(" where period.fnumber<=? \n");
				builder.addParam(periodNumber);
				if("paySplit".equals(item)){
					builder.appendSql(" and \n");
					builder.appendParam("split.fisworkloadbill", Boolean.FALSE);
				}
				if(item.startsWith("invalid")){
					builder.appendSql(" and \n");
					builder.appendParam("con.FConSplitExecState", ConSplitExecStateEnum.INVALIDDID_VALUE);
				}
				builder.appendSql(" and split.fstate <>'9INVALID' ");
				builder.appendSql(" and \n");
				if("invalidCon".equals(item)){
					builder.appendParam("closee.fiscost",Boolean.TRUE);
				}else{
					builder.appendParam("closee.fiscost",Boolean.FALSE);
				}
				builder.appendSql(" and \n");
				builder.appendParam("con.fiscostsplit", Boolean.TRUE);
				builder.appendSql(" and \n");
				builder.appendParam("split.FCurProjectID",arrayList);//�Ƶ�where�������ұߣ���Ϊ������Ч����С��ѯ��Χ
				builder.appendSql(" \n group by period.fnumber,split.FCurProjectID,split.fsplitstate \n");
			}else{//δ�½�
				//��ִ���
			}
			
		}
	}

	protected void _updateProjectCostDetail(Context ctx) throws BOSException,
			EASBizException {
		
	}

	protected void _updateProjectCostDetail(Context ctx, Map param) throws BOSException, EASBizException {
		if(param==null){
			return;
		}
		if(param.containsKey("projectIds")){
			Set projectIds = (HashSet) param.get("projectIds");
			for(Iterator it = projectIds.iterator();it.hasNext();){
				String projectId = (String)it.next();
				DynCostSnapShoter.updateProjectCostDetail(ctx, projectId);
			}
		}
	}
}

