package com.kingdee.eas.fdc.finance.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryFactory;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.aimcost.CostSplitCollection;
import com.kingdee.eas.fdc.aimcost.CostSplitFactory;
import com.kingdee.eas.fdc.aimcost.CostSplitInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettlementCostSplitCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;
import com.kingdee.eas.fdc.finance.FIProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.ISettledMonthly;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.ProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusInfo;
import com.kingdee.eas.fdc.finance.SettledMonthlyFactory;
import com.kingdee.eas.fdc.finance.SettledMonthlyHelper;
import com.kingdee.eas.fdc.finance.SettledMonthlyInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.db.SQLUtils;

public class CostClosePeriodFacadeControllerBean extends AbstractCostClosePeriodFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.finance.app.CostClosePeriodFacadeControllerBean");

	protected void _tracePayment(Context ctx, List idList) throws BOSException, EASBizException {
		// ��ͬ���
		Set set = new HashSet();
		Set idSet = FDCHelper.list2Set(idList);
		EntityViewInfo viewContractSplit = new EntityViewInfo();
		FilterInfo filterContractSplit = new FilterInfo();
		filterContractSplit.getFilterItems().add(new FilterItemInfo("contractBill.curProject.id", idSet, CompareType.INCLUDE));
		filterContractSplit.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));

		viewContractSplit.setFilter(filterContractSplit);
		viewContractSplit.getSelector().add("id");
		CoreBillBaseCollection contract = ContractCostSplitFactory.getLocalInstance(ctx).getCoreBillBaseCollection(viewContractSplit);
		for (Iterator it = contract.iterator(); it.hasNext();) {
			set.add(((CoreBillBaseInfo) (it.next())).getId().toString());
		}

		// ������
		EntityViewInfo viewChangeSplit = new EntityViewInfo();
		FilterInfo filterChangeSplit = new FilterInfo();
		filterChangeSplit.getFilterItems().add(new FilterItemInfo("contractChange.curProject.id", idSet, CompareType.INCLUDE));
		filterChangeSplit.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));

		viewChangeSplit.setFilter(filterChangeSplit);
		viewChangeSplit.getSelector().add("id");
		CoreBillBaseCollection change = ConChangeSplitFactory.getLocalInstance(ctx).getCoreBillBaseCollection(viewChangeSplit);
		for (Iterator it = change.iterator(); it.hasNext();) {
			set.add(((CoreBillBaseInfo) (it.next())).getId().toString());
		}

		EntityViewInfo viewPaymentSplit = new EntityViewInfo();
		FilterInfo filterPaymentSplit = new FilterInfo();
		filterPaymentSplit.getFilterItems().add(new FilterItemInfo("parent.paymentBill.curProject.id", idSet, CompareType.INCLUDE));
		filterPaymentSplit.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		filterPaymentSplit.getFilterItems().add(new FilterItemInfo("splitBillId", set, CompareType.NOTINCLUDE));

		viewPaymentSplit.setFilter(filterPaymentSplit);
		viewPaymentSplit.getSelector().add("parent.*");
		viewPaymentSplit.getSelector().add("id");

		Map values = new HashMap();

		PaymentSplitEntryCollection payment = PaymentSplitEntryFactory.getLocalInstance(ctx).getPaymentSplitEntryCollection(viewPaymentSplit);
		for (Iterator it = payment.iterator(); it.hasNext();) {
			String key = ((PaymentSplitEntryInfo) (it.next())).getParent().getId().toString();
			if (values.containsKey(key)) {
				continue;
			} else {
				values.put(key, ((PaymentSplitEntryInfo) (it.next())).getParent());
			}
		}

		// ���ⲿ�ָ��������ϣ�����������ͬ

		SysUtil.abort();
	}

	protected void _checkCostSplit(Context ctx, List idList) throws BOSException, EASBizException {
		// ����ڱ��ڼ��ڵĳɱ������Ƿ�����ɣ���ɺ�ͬ��֣������֣������֣���Ʒ���㵥��

	}

	private ProjectPeriodStatusInfo getPrjPeriodStatus(Context ctx, String prjId) throws BOSException, EASBizException {
		ProjectPeriodStatusInfo prjInfo = new ProjectPeriodStatusInfo();
		EntityViewInfo viewPrj = new EntityViewInfo();
		FilterInfo filterPrj = new FilterInfo();
		filterPrj.appendFilterItem("project.id", prjId);
		viewPrj.setFilter(filterPrj);
		viewPrj.getSelector().add("*");
		viewPrj.getSelector().add("costPeriod.*");
		viewPrj.getSelector().add("finacialPeriod.*");
		if (ctx == null) {
			prjInfo = ProjectPeriodStatusFactory.getRemoteInstance().getProjectPeriodStatusCollection(viewPrj).get(0);
		} else {
			prjInfo = ProjectPeriodStatusFactory.getLocalInstance(ctx).getProjectPeriodStatusCollection(viewPrj).get(0);
		}
		return prjInfo;
	}

	/**
	 * �ɱ��½�
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.finance.app.AbstractCostClosePeriodFacadeControllerBean#_traceCostClose(com.kingdee.bos.Context,
	 *      java.util.List)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.finance.app.AbstractCostClosePeriodFacadeControllerBean#_traceCostClose(com.kingdee.bos.Context,
	 *      java.util.List)
	 */
	protected String _traceCostClose(Context ctx, List idList) throws BOSException, EASBizException {
		String strSuccess = null;
		String strNoUse = null;
		String strNoPeirod = null;
		String strFaild = null;
		String strHasClose = null;
		String strHasProduct = null;
		String strNotCon = null;
		String strNotLoad = null;
		String strNotAuditCon = null;
		String strNotSettle = null;
		Connection cn = null;
		try {
			cn = getConnection(ctx);
			// ������������
			IORMappingDAO iSettleMonth = ORMappingDAO.getInstance(new SettledMonthlyInfo().getBOSType(), ctx, cn);
			// �½�֮ǰ�������̯�������½��м��
			// saveDyCostSplitData(ctx,idList);
			for (int i = 0, size = idList.size(); i < size; i++) {
				String prjId = idList.get(i).toString();

				// �ȼ�飬���������ϣ������½ᣬ�˷�ʱ��(�����ļ���ظ�������ʱ����Ҫ�׳��쳣
				// ProjectPeriodStatusUtil.checkNext(ctx, prjId, true);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("longNumber");
				selector.add("displayName");
				selector.add("fullOrgUnit.id");
				// ������Ŀ
				CurProjectInfo curprjInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(BOSUuid.read(prjId)));
				String prjStr = curprjInfo.getLongNumber().replace('!', '.') + " " + curprjInfo.getDisplayName() + " ";
				// ������Ŀ�ĵ�ǰ�ڼ�,�������ݿ��Ա��Ⲣ����Ӱ��
				FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
				sql.appendSql("update t_fnc_projectperiodstatus set fstartperiodid=fstartperiodid where fprojectid=");
				sql.appendParam(prjId);
				sql.executeUpdate();
				PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);

				boolean isFinacial = FDCUtils.IsFinacial(ctx, curprjInfo.getFullOrgUnit().getId().toString());

				// �����������
				ProjectPeriodStatusUtil.doImportData(ctx, prjId, true, currentPeriod);

				// ��һ���ڼ��Ƿ����
				PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(ctx, currentPeriod);
				if (nextPeriod == null || nextPeriod.getId() == null) {
					if (strNoPeirod == null)
						strNoPeirod = "";
					strNoPeirod = strNoPeirod + prjStr;
					continue;
				}
//				String nextPeriodStr = Integer.toString(nextPeriod.getNumber());
				String currentPeriodStr = Integer.toString(currentPeriod.getNumber());
				String currentPeriodId = currentPeriod.getId().toString();
				ProjectPeriodStatusInfo prjInfo = getPrjPeriodStatus(ctx, prjId);
				if (!prjInfo.isIsClosed()) {
					if (strNoUse == null)
						strNoUse = "";
					strNoUse = strNoUse + prjStr;
					continue;
				} else if (prjInfo.isIsCostEnd()) {
					if (strHasClose == null)
						strHasClose = "";
					strHasClose = strHasClose + prjStr;
					continue;
				}

				// �Ƿ����δ�����ĺ�ͬ
				FilterInfo filterCon = new FilterInfo();
				filterCon.getFilterItems().add(new FilterItemInfo("curproject.id", prjId));
				filterCon.getFilterItems().add(new FilterItemInfo("period.id", currentPeriodId));
				filterCon.getFilterItems().add(new FilterItemInfo("isCoseSplit", Boolean.TRUE));//ֻ�Գɱ����������
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTEQUALS));
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
				//�ݻ��Ĳ�����
				filterCon.getFilterItems().add(new FilterItemInfo("isRespite", Boolean.FALSE));
				if (ContractBillFactory.getLocalInstance(ctx).exists(filterCon)) {
					if (strNotCon == null)
						strNotCon = "";
					strNotCon = strNotCon + prjStr;
					continue;
				}

				// �Ƿ����δ�����Ĺ�����ȷ�ϵ�
				FilterInfo filterLoad = new FilterInfo();
				filterLoad.getFilterItems().add(new FilterItemInfo("curproject.id", prjId));
				filterLoad.getFilterItems().add(new FilterItemInfo("period.id", currentPeriodId));
				filterLoad.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTEQUALS));
				//�ݻ��Ĳ�����
				filterLoad.getFilterItems().add(new FilterItemInfo("isRespite", Boolean.FALSE));
				if (WorkLoadConfirmBillFactory.getLocalInstance(ctx).exists(filterLoad)) {
					if (strNotLoad == null)
						strNotLoad = "";
					strNotLoad = strNotLoad + prjStr;
					continue;
				}
				
				// �Ƿ����δ�����Ľ���
				filterCon = new FilterInfo();
				filterCon.getFilterItems().add(new FilterItemInfo("curproject.id", prjId));
				filterCon.getFilterItems().add(new FilterItemInfo("isCostSplit", Boolean.TRUE));//ֻ�Գɱ����������
				filterCon.getFilterItems().add(new FilterItemInfo("period.id", currentPeriodId));
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTEQUALS));
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
				//�ݻ��Ĳ�����
				filterCon.getFilterItems().add(new FilterItemInfo("isRespite", Boolean.FALSE));
				if (ContractSettlementBillFactory.getLocalInstance(ctx).exists(filterCon)) {
					if (strNotSettle == null)
						strNotSettle = "";
					strNotSettle = strNotSettle + prjStr;
					continue;
				}

				// �Ƿ����δ�����ı��������
				filterCon = new FilterInfo();
				filterCon.getFilterItems().add(new FilterItemInfo("curproject.id", prjId));
				filterCon.getFilterItems().add(new FilterItemInfo("period.id", currentPeriodId));
				//TODO ������
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE));
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED_VALUE));
//				filterCon.getFilterItems().add(new FilterItemInfo("isCostSplit", Boolean.TRUE));//ֻ�Գɱ����������
				//�ݻ��Ĳ�����
				filterCon.getFilterItems().add(new FilterItemInfo("isRespite", Boolean.FALSE));
				filterCon.setMaskString(" #0 and #1 and (#2 or #3) and #4 ");
				if (ChangeAuditBillFactory.getLocalInstance(ctx).exists(filterCon)) {
					if (strNotAuditCon == null)
						strNotAuditCon = "";
					strNotAuditCon = strNotAuditCon + prjStr;
					continue;
				}

				// �Ƿ����δ�����ı��ָ��
				filterCon = new FilterInfo();
				filterCon.getFilterItems().add(new FilterItemInfo("curproject.id", prjId));
				filterCon.getFilterItems().add(new FilterItemInfo("period.id", currentPeriodId));
				//TODO ������
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE));
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED_VALUE));
				filterCon.getFilterItems().add(new FilterItemInfo("isCostSplit", Boolean.TRUE));//ֻ�Գɱ����������
				//�ݻ��Ĳ�����
				filterCon.getFilterItems().add(new FilterItemInfo("changeAudit.isRespite", Boolean.FALSE));
				filterCon.setMaskString(" #0 and #1 and (#2 or #3) and #4 and #5");
				if (ContractChangeBillFactory.getLocalInstance(ctx).exists(filterCon)) {
					if (strNotAuditCon == null)
						strNotAuditCon = "";
					strNotAuditCon = strNotAuditCon + prjStr;
					continue;
				}
				
				// ������Ƿ���δ��ɵĲ��

				// �ж�ʱ ����֮���ڲ�ֵ�����
				if (hasNoSplitBill(ctx, prjId, currentPeriod)) {
					if (strFaild == null)
						strFaild = "";
					strFaild = strFaild + prjStr;
					continue;
				}

				// �Ƿ������һ�ڼ���ǰ��δ����Ĳ�Ʒ���㵥
				if (isFinacial) {
					FilterInfo filterProduct = new FilterInfo();
					filterProduct.getFilterItems().add(new FilterItemInfo("curProjProductEntries.curProject.id", prjId));
					filterProduct.getFilterItems().add(new FilterItemInfo("fiVouchered", Boolean.FALSE));
					filterProduct.getFilterItems().add(new FilterItemInfo("period.number", currentPeriodStr, CompareType.LESS));
					if (ProductSettleBillFactory.getLocalInstance(ctx).exists(filterProduct)) {
						if (strHasProduct == null)
							strHasProduct = "";
						strHasProduct = strHasProduct + prjStr;
						continue;
					}
				}

				// ����Ϊ���� by sxhong 2009-06-01 16:35:19

				saveToSettledMonth(ctx, iSettleMonth, prjId, currentPeriod, nextPeriod);

				FDCCostRptFacadeFactory.getLocalInstance(ctx).saveSnapShot(prjId, CostMonthlySaveTypeEnum.COSTMONTHLY);

				ProjectPeriodStatusUtil.next(ctx, prjId, true);
				if (strSuccess == null)
					strSuccess = "";
				strSuccess = strSuccess + prjStr;
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new BOSException(e);
		} finally {
			SQLUtils.cleanup(cn);
		}

		String end = null;
		if (strSuccess != null) {
			strSuccess = "�½�ɹ���Ŀ��" + strSuccess + "\n";
		} else {
			strSuccess = "";
		}
		if (strHasClose != null) {
			strHasClose = "�Ѿ��½ᣬ���ܳɱ��½����Ŀ��" + strHasClose + "\n";
		} else {
			strHasClose = "";
		}
		if (strNotCon != null) {
			strNotCon = "����δ�����ĺ�ͬ�����ܳɱ��½����Ŀ��" + strNotCon + "\n";
		} else {
			strNotCon = "";
		}
		if (strNotLoad != null) {
			strNotLoad = "����δ�����Ĺ�����ȷ�ϵ������ܳɱ��½����Ŀ��" + strNotLoad + "\n";
		} else {
			strNotLoad = "";
		}
		if (strNotAuditCon != null) {
			strNotAuditCon = "����δ�����ı�������������ܳɱ��½����Ŀ��" + strNotAuditCon + "\n";
		} else {
			strNotAuditCon = "";
		}
		if (strNotSettle != null) {
			strNotSettle = "����δ�����Ľ��㵥�����ܳɱ��½����Ŀ��" + strNotSettle + "\n";
		} else {
			strNotSettle = "";
		}
		if (strFaild != null) {
			strFaild = "����δ������ݣ����ܳɱ��½����Ŀ��" + strFaild + "\n";
		} else {
			strFaild = "";
		}
		if (strHasProduct != null) {
			strHasProduct = "��һ�ڴ���δ��������������Ĳ�Ʒ���㵥�����ܳɱ��½����Ŀ��" + strHasProduct + "\n";
		} else {
			strHasProduct = "";
		}
		if (strNoUse != null) {
			strNoUse = "δ����(��δ������ʼ��)�����ܳɱ��½����Ŀ: " + strNoUse + "\n";
		} else {
			strNoUse = "";
		}

		if (strNoPeirod != null) {
			strNoPeirod = "��һ���ڼ䲻����,��Ҫ��ά����������[�ڼ�].���ܳɱ��½����Ŀ: " + strNoPeirod + "\n";
		} else {
			strNoPeirod = "";
		}
		end = strSuccess + strNoPeirod + strNoUse + strHasClose + strNotCon + strNotLoad + strNotAuditCon + strNotSettle + strFaild + strHasProduct;
		return end;
	}

	/**
	 * �Ƿ���û�в�ֵĵ��� by sxhong ����δ�������,���ڷ���true,�����ڷ���false
	 * 
	 * �������ע�� Added by Owen_wen 2011-07-21 <br>
	 * ֻ��Ҫͳ�ƽ�̬�ɱ������ݣ���fisCostSplit=1��<br>
	 * �ǳɱ��ĺ�ͬ�۷�T_CON_ConNoCostSplit��<br>
	 * �ǳɱ��ı�����T_CON_ConChangeNoCostSplit��<br>
	 * �ǳɱ��Ľ�����T_CON_SettNoCostSplit <br>
	 * 
	 * @param ctx
	 * @param prjId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private boolean hasNoSplitBill(Context ctx, String prjId, PeriodInfo currentPeriod) throws BOSException, SQLException {
		Integer currentPeriodNumber = new Integer(currentPeriod.getNumber());
		// ȡ������֮ǰ�ڼ�ĺ�ͬ,���,����
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select count(*) as count from (");
		builder.appendSql("select distinct fid from T_Con_Contractbill where fcurProjectId=? and fisRespite=0 and fisCostSplit=1 and fisAmtWithoutCost=0 and fperiodid in \n");
		builder.appendSql("(select fid from t_Bd_Period where fnumber<=?) \n");
		builder.appendSql("union all \n");
		builder.appendSql("select distinct bill.fid from t_Con_Contractchangebill bill inner join T_con_contractbill con on con.fid=bill.fcontractbillid  inner join t_Con_Changeauditbill changeaudit on changeaudit.fid=bill.fchangeauditid where con.fisAmtWithoutCost=0 and bill.fcurProjectId=? and changeaudit.fisRespite=0 and bill.fisCostSplit=1 and  bill.fperiodid in \n");
		builder.appendSql("(select fid from t_Bd_Period where fnumber<=?) \n");
		builder.appendSql("union all \n");
		builder.appendSql("select distinct bill.fid from t_Con_Contractsettlementbill  bill inner join T_con_contractbill con on con.fid=bill.fcontractbillid where con.fisAmtWithoutCost=0 and bill.fcurProjectId=? and bill.fisRespite=0 and  bill.fisCostSplit=1 and bill.fperiodid in \n");
		builder.appendSql("(select fid from t_Bd_Period where fnumber<=?) \n");

		builder.appendSql(")t ");
		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);
		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);
		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);

		IRowSet rowSet = builder.executeQuery();
		int billCount = 0;
		if (rowSet.next()) {
			billCount = rowSet.getInt("count");
		}

		// //ȡ������֮ǰ�ڼ�ĺ�ͬ,���,����Ĳ��
		builder.clear();
		builder.appendSql("select count(*) as count from ( \n");
		builder.appendSql("select distinct fcontractBillId from t_Con_Contractcostsplit split inner join T_con_contractbill bill on split.fcontractbillid=bill.fid where split.fcurProjectId=? and split.fislastVerThisPeriod=1  and bill.fisRespite=0 \n");
		builder.appendSql("and  split.fperiodid in (select fid from t_Bd_Period where fnumber<=?)\n");
		builder.appendSql("union all  \n");
		builder.appendSql("select distinct fcontractchangeid from t_Con_Conchangesplit split inner join T_con_contractchangebill bill on split.fcontractchangeid=bill.fid inner join T_con_changeauditbill changeaudit on changeaudit.fid=bill.fchangeauditid where split.fcurProjectId=? and split.fislastVerThisPeriod=1 and changeaudit.fisRespite=0 \n");
		builder.appendSql("and  split.fperiodid in (select fid from t_Bd_Period where fnumber<=?) \n");
		builder.appendSql("union all \n");
		builder.appendSql("select distinct fsettlementBillid from t_con_settlementcostsplit split inner join T_con_ContractSettlementBill bill on split.fsettlementBillid=bill.fid where split.fcurProjectId=? and fislastVerThisPeriod=1  and bill.fisRespite=0 \n");
		builder.appendSql("and  split.fperiodid in (select fid from t_Bd_Period where fnumber<=?) \n");
		builder.appendSql(") t  \n");

		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);
		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);
		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);

		int splitCount = 0;
		rowSet = builder.executeQuery();
		if (rowSet.next()) {
			splitCount = rowSet.getInt("count");
		}

		return billCount != splitCount;
	}

	/**
	 * ����
	 */
	protected void _frozenProject(Context ctx, List idList) throws BOSException, EASBizException {
		for (int i = 0, size = idList.size(); i < size; i++) {
			ProjectPeriodStatusUtil.freeze(ctx, idList.get(i).toString(), true);
			// ProjectPeriodStatusInfo prjInfo = getPrjPeriodStatus(ctx, idList
			// .get(i).toString());
			// prjInfo.setIsCostFreeze(true);
			// SelectorItemCollection selector = new SelectorItemCollection();
			// selector.add("isCostFreeze");
			// ProjectPeriodStatusFactory.getLocalInstance(ctx).updatePartial(
			// prjInfo, selector);
		}
	}

	/**
	 * ���½�
	 */
	protected String _antiCostClose(Context ctx, List idList) throws BOSException, EASBizException {
		String end = null;
		String strSuccess = null;

		String strEnd = null;
		String strEqualStart = null;
		String strNotEqual = null;
		String strNotCompany = null;
		String strHasFIProduct = null;
		String strHasDynamicCost = null;
		String strHasProjectIndex = null;

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("longNumber");
		sic.add("displayName");
		sic.add("fullOrgUnit.id");
		// �ɹ����½�Ĺ�����Ŀ
		Set prjIds = new HashSet();

		for (int i = 0, size = idList.size(); i < size; i++) {
			String prjId = idList.get(i).toString();

			// �ȼ��
			// ProjectPeriodStatusUtil.checkLast(ctx, prjId, true);

			// ������Ŀ
			CurProjectInfo curprjInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(BOSUuid.read(prjId)), sic);

			// ������Ŀ�ĵ�ǰ�ڼ�
			PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
			PeriodInfo shouldPeriod = PeriodUtils.getPrePeriodInfo(ctx, currentPeriod);

			BOSUuid companyID = curprjInfo.getFullOrgUnit().getId();
			// ������Ŀ���ڲ�����֯��ϵͳ�ڼ�
			PeriodInfo companyPeriod = SystemStatusCtrolUtils.getCurrentPeriod(ctx, SystemEnum.FDC, new ObjectUuidPK(companyID));

			// ������Ŀ״̬
			ProjectPeriodStatusInfo prjInfo = getPrjPeriodStatus(ctx, prjId);
			PeriodInfo startPeriod = prjInfo.getStartPeriod();

			String numberName = curprjInfo.getLongNumber().replace('!', '.') + " " + curprjInfo.getDisplayName() + " ";

			if (prjInfo.isIsEnd()) {
				if (strEnd == null)
					strEnd = "";
				// ������Ŀ�Ѿ��ر�
				strEnd = strEnd + numberName;
				continue;
			} else if (currentPeriod.getId().toString().equals(startPeriod.getId().toString())) {
				if (strEqualStart == null)
					strEqualStart = "";
				// �������ڼ䲻�ܷ��½�
				strEqualStart = strEqualStart + numberName;
				continue;
			} else if (!prjInfo.isIsCostEnd() && prjInfo.isIsFinacialEnd()) {
				if (strNotEqual == null)
					strNotEqual = "";
				// �����Ѿ��½�
				strNotEqual = strNotEqual + numberName;
				continue;
			} else if (companyPeriod.getNumber() >= currentPeriod.getNumber()) {
				if (strNotCompany == null)
					strNotCompany = "";
				// ������֯�ĵ�ǰ�ڼ���ڵ�ǰ������Ŀ���ڼ�
				strNotCompany = strNotCompany + numberName;
				continue;
			}

			// �����ڼ��ڴ��ڼ����֮��Ŀ�������������
			FilterInfo filterProduct = new FilterInfo();
			filterProduct.getFilterItems().add(new FilterItemInfo("curProjProductEntries.curProject.id", prjId));
			filterProduct.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(shouldPeriod.getNumber()), CompareType.GREATER));
			if (FIProductSettleBillFactory.getLocalInstance(ctx).exists(filterProduct)) {
				if (strHasFIProduct == null)
					strHasFIProduct = "";
				strHasFIProduct = strHasFIProduct + numberName;
				continue;
			}

			// ��鶯̬�ɱ��Ƿ��Ѿ�����
			// FilterInfo filter = new FilterInfo();
			// filter.getFilterItems().add(
			// new FilterItemInfo("account.curProject.id", prjId));
			// filter.getFilterItems().add(
			// new FilterItemInfo("period.number", Integer
			// .toString(shouldPeriod.getNumber()),
			// CompareType.GREATER));
			// if (DynamicCostFactory.getLocalInstance(ctx).exists(filter)) {
			// if (strHasDynamicCost == null)
			// strHasDynamicCost = "";
			// strHasDynamicCost = strHasDynamicCost + numberName;
			// continue;
			// }

			// �Ƿ���ڵ�ǰ�ڼ估֮���ָ������
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("projOrOrgID", prjId));
			filter.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(shouldPeriod.getNumber()), CompareType.GREATER));
			if (ProjectIndexDataFactory.getLocalInstance(ctx).exists(filter)) {
				if (strHasProjectIndex == null)
					strHasProjectIndex = "";
				strHasProjectIndex = strHasProjectIndex + numberName;
				continue;
			}

			// ɾ������
			FDCCostRptFacadeFactory.getLocalInstance(ctx).reverseCostMonthSettled(prjId, shouldPeriod);
			// ���ͨ��֮��ɾ����ǰ�ڼ���½���е�����
			deleteSettleMonth(ctx, prjId, shouldPeriod.getId().toString());
			// �ɹ����½�Ĺ�����Ŀ
			prjIds.add(prjId);
			// ���½�
			ProjectPeriodStatusUtil.last(ctx, prjId, true);

			if (strSuccess == null)
				strSuccess = "";
			strSuccess = strSuccess + numberName;

		}
		if (strSuccess != null) {
			strSuccess = "�ɱ����½�ɹ���Ŀ��" + strSuccess + "\n";
		} else {
			strSuccess = "";
		}
		if (strEnd != null) {
			strEnd = "�Ѿ��رգ����ܷ��½����Ŀ��" + strEnd + "\n";
		} else {
			strEnd = "";
		}
		if (strEqualStart != null) {
			strEqualStart = "��ǰ�ɱ��ڼ���������ڼ䣬���ܷ��½����Ŀ��" + strEqualStart + "\n";
		} else {
			strEqualStart = "";
		}
		if (strNotEqual != null) {
			strNotEqual = "����ɱ��ڼ���ɱ��ڼ���ͬ�����ܳɱ����½����Ŀ��" + strNotEqual + "\n";
		} else {
			strNotEqual = "";
		}
		if (strNotCompany != null) {
			strNotCompany = "������֯�ڼ���ɱ��ڼ䲻ƥ�䣬���ܳɱ����½����Ŀ��" + strNotCompany + "\n";
		} else {
			strNotCompany = "";
		}
		if (strHasFIProduct != null) {
			strHasFIProduct = "�е�ǰ�ڼ�Ŀ����������������ܳɱ����½����Ŀ�� " + strHasFIProduct + "\n";
		} else {
			strHasFIProduct = "";
		}

		if (strHasDynamicCost != null) {
			strHasDynamicCost = "��ǰ�ڼ��Ѿ����¶�̬�ɱ���̯���ã����ܳɱ����½����Ŀ�� " + strHasDynamicCost + "\n";
		} else {
			strHasDynamicCost = "";
		}

		if (strHasProjectIndex != null) {
			strHasProjectIndex = "��ǰ�ڼ��Ѿ�����ָ�꣬���ܳɱ����½����Ŀ�� " + strHasProjectIndex + "\n";
		} else {
			strHasProjectIndex = "";
		}

		end = strSuccess + strEnd + strEqualStart + strNotEqual + strNotCompany + strHasFIProduct + strHasDynamicCost + strHasProjectIndex;
		return end;
	}

	/**
	 * ɾ���½����ݱ��е�����: ��ͬ�޶�
	 * 
	 */
	protected void _delete(Context ctx, String contractId, String periodId) throws BOSException, EASBizException {
		ISettledMonthly settleMonth = SettledMonthlyFactory.getLocalInstance(ctx);
		FilterInfo filterPrj = new FilterInfo();
		if (contractId != null) {
			filterPrj.getFilterItems().add(new FilterItemInfo("contractID", contractId));
		}
		filterPrj.getFilterItems().add(new FilterItemInfo("settlePeriod.id", periodId));

		settleMonth.delete(filterPrj);
	}

	// ɾ����������
	protected void deleteSettleMonth(Context ctx, String projectId, String periodId) throws BOSException, EASBizException {
		ISettledMonthly settleMonth = SettledMonthlyFactory.getLocalInstance(ctx);
		FilterInfo filterPrj = new FilterInfo();
		filterPrj.getFilterItems().add(new FilterItemInfo("curProjectID", projectId));

		filterPrj.getFilterItems().add(new FilterItemInfo("settlePeriod.id", periodId));
		filterPrj.getFilterItems().add(new FilterItemInfo("isCost", Boolean.TRUE));

		settleMonth.delete(filterPrj);
	}

	// �����̯�������½��м��
	protected void saveDyCostSplitData(Context ctx, List list) throws BOSException, EASBizException {
		if (list == null || list.size() < 0) {
			return;
		}
		Connection cn = null;
		try {
			cn = getConnection(ctx);
			// ������������
			IORMappingDAO iSettleMonth = new ORMappingDAO(new SettledMonthlyInfo().getBOSType(), ctx, cn);
			for (int i = 0, size = list.size(); i < size; i++) {
				String prjId = list.get(i).toString();
				// ����Ŀ��dynamicId
				Set dynamicIds = new HashSet();
				// ��ø���Ŀ��ǰ�ڼ�
				PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.getSelector().add("*");
				filter.getFilterItems().add(new FilterItemInfo("account.curProject.id", prjId));
				view.setFilter(filter);
				DynamicCostCollection coll = DynamicCostFactory.getLocalInstance(ctx).getDynamicCostCollection(view);
				for (Iterator it = coll.iterator(); it.hasNext();) {
					DynamicCostInfo dynamicInfo = (DynamicCostInfo) it.next();
					SettledMonthlyInfo settle = new SettledMonthlyInfo();
					settle.setCurProjectID(prjId);
					settle.setEntityID(dynamicInfo.getBOSType().toString());
					settle.setObjectID(dynamicInfo.getId().toString());
					settle.setSettlePeriod(currentPeriod);
					settle.setIsCost(true);
					iSettleMonth.addNewBatch(settle);
					dynamicIds.add(dynamicInfo.getId().toString());
				}
				// ��Ʒ��̯����
				view = new EntityViewInfo();
				filter = new FilterInfo();
				view.getSelector().add("*");
				view.setFilter(filter);
				view.getFilter().getFilterItems().add(new FilterItemInfo("dynamicCostId", dynamicIds, CompareType.INCLUDE));
				// ȡ���°汾�ķ�̯����
				view.getFilter().getFilterItems().add(new FilterItemInfo("isLatestVer", new Integer(1)));
				DynamicCostProductSplitEntryCollection splits = DynamicCostProductSplitEntryFactory.getLocalInstance(ctx).getDynamicCostProductSplitEntryCollection(view);
				for (Iterator it = splits.iterator(); it.hasNext();) {
					DynamicCostProductSplitEntryInfo entry = (DynamicCostProductSplitEntryInfo) it.next();
					SettledMonthlyInfo settle = new SettledMonthlyInfo();
					settle.setCurProjectID(prjId);
					settle.setEntityID(entry.getBOSType().toString());
					settle.setObjectID(entry.getId().toString());
					settle.setSettlePeriod(currentPeriod);
					settle.setIsCost(true);
					iSettleMonth.addNewBatch(settle);
				}
			}
			iSettleMonth.executeBatch();

		} finally {
			SQLUtils.cleanup(cn);
		}
	}

	// ���½�֮�󣬸��·�̯����isLatestVerΪfalse�����±���isLaterstVerΪtrue
	protected void updateSplitData(Context ctx, Set prjIds) throws BOSException, EASBizException {
		Map map = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("account.curProject.id", prjIds, CompareType.INCLUDE));
		view.setFilter(filter);
		DynamicCostCollection dynamicCostCollection = DynamicCostFactory.getLocalInstance(ctx).getDynamicCostCollection(view);
		Set dynamicIds = new HashSet();
		for (int i = 0; i < dynamicCostCollection.size(); i++) {
			DynamicCostInfo dynamic = dynamicCostCollection.get(i);
			dynamicIds.add(dynamic.getId().toString());
		}

	}

	/**
	 * �ȳ��һ�������Ժ����Ż� by sxhong 2009-06-01 17:30:24
	 * 
	 * @param ctx
	 * @param iSettleMonth
	 * @param prjId
	 * @param currentPeriod
	 * @param nextPeriod
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	private void saveToSettledMonth(Context ctx, IORMappingDAO iSettleMonth, String prjId, PeriodInfo currentPeriod, PeriodInfo nextPeriod) throws BOSException, EASBizException, SQLException {
		// ������Ŀ��Ӧ�ĳɱ�����
		/**
		 * R091119-224����ɱ��½�ͨ����: ��ʾ��ͬ���δ��,����Ϊʲô�ɱ��½��ܹ�ͨ��
		 * �������һ۷��������ڿ���Ŀ��ֿ�Ŀ���𣬿�Ŀ���ڹ�����Ŀ�޳ɱ��½�����
		 * ������ͬ�����(���㲻�ô���)��ֵ���֯ȡ��Ŀ��Ӧ�Ĺ�����Ŀ�ĳɱ�����(��Ϊ������Ŀ������ã����ж���ɱ�����)
		 * by hpw 2009-12-11
		 */
//		String orgUnitId = FDCUtils.findCostCenterOrgId(ctx, prjId);
		String currentPeriodStr = Integer.toString(currentPeriod.getNumber());
		String nextPeriodStr = Integer.toString(nextPeriod.getNumber());
		String currentPeriodId = currentPeriod.getId().toString();
		// ��¼�½�����
		Set set = new HashSet();
		Set splitSet = new HashSet();

		// ��ͬ���
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder.appendSql("select distinct splitentry.fparentid from t_con_contractcostsplitentry splitentry ");
		builder.appendSql("where splitentry.fcostaccountid in (select account.fid from t_fdc_costaccount account where account.FCurProject=?) ");
		builder.appendSql("and splitentry.fparentid in (																	");
		builder
				.appendSql("	select split.fid from t_con_contractcostsplit split where (split.fstate<>'9INVALID' or (split.FIslastVerThisPeriod=1 and split.fstate='9INVALID' and split.fperiodid=?)) ");
		builder.appendSql("	and split.fperiodid in (select period.fid from t_bd_period period where period.fnumber<=?) 		");
		builder.appendSql("	and split.forgunitid in (	select fcostcenterid from t_fdc_curproject where fid in ( ");
		builder.appendSql(" select fcurprojectid from t_con_contractcostsplit where fid in( select distinct splitentry.fparentid from t_con_contractcostsplitentry splitentry ");

		builder.appendSql(" where splitentry.fcostaccountid in (select account.fid from t_fdc_costaccount account where account.FCurProject=? )))) ");
		builder.appendSql("	)");
		builder.addParam(prjId);
		builder.addParam(currentPeriodId);
		builder.addParam(currentPeriodStr);
		builder.addParam(prjId);
//		builder.addParam(orgUnitId);
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			splitSet.add(rowSet.getString("fparentid"));
		}

		EntityViewInfo viewContractSplit = new EntityViewInfo();
		FilterInfo filterContractSplit = new FilterInfo();
		// ����м��
		EntityViewInfo viewConCostSplit = new EntityViewInfo();
		FilterInfo filterConCostSplit = new FilterInfo();
		CostSplitCollection conCost = new CostSplitCollection();
		CostSplitInfo conCostInfo = new CostSplitInfo();
		if (splitSet.size() > 0) {

			filterContractSplit.getFilterItems().add(new FilterItemInfo("id", splitSet, CompareType.INCLUDE));
			viewContractSplit.setFilter(filterContractSplit);
			viewContractSplit.getSelector().add("id");
			viewContractSplit.getSelector().add("contractBill.id");
			ContractCostSplitCollection contract = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(viewContractSplit);
			ContractCostSplitInfo contractInfo = new ContractCostSplitInfo();
			for (Iterator it = contract.iterator(); it.hasNext();) {
				// SettledMonthlyInfo settle = new SettledMonthlyInfo();
				contractInfo = (ContractCostSplitInfo) (it.next());
				BOSObjectType bosType = contractInfo.getBOSType();
				String objectId = contractInfo.getId().toString();
				if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
					SettledMonthlyInfo settle = new SettledMonthlyInfo();
					settle.setCurProjectID(prjId);
					settle.setEntityID(bosType.toString());
					settle.setObjectID(objectId);
					settle.setSettlePeriod(currentPeriod);
					settle.setContractID(contractInfo.getContractBill().getId().toString());
					// settleMonth.addnew(settle);
					settle.setIsCost(true);
					iSettleMonth.addNewBatch(settle);

					set.add(contractInfo.getId().toString());
				}
			}

			// ��ͬ����м��
			viewConCostSplit = new EntityViewInfo();
			filterConCostSplit = new FilterInfo();
			conCost = new CostSplitCollection();
			conCostInfo = new CostSplitInfo();
			if (set.size() > 0) {
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("splitBillId", set, CompareType.INCLUDE));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("isInvalid", Boolean.TRUE, CompareType.NOTEQUALS));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("costBillType", CostSplitBillTypeEnum.CONTRACTSPLIT_VALUE, CompareType.EQUALS));
				// filterConCostSplit.getFilterItems().add(
				// new FilterItemInfo("period.number", Integer
				// .toString(currentPeriod.getNumber()),
				// CompareType.LESS_EQUALS));

				viewConCostSplit.setFilter(filterConCostSplit);
				viewConCostSplit.getSelector().add("id");
				viewConCostSplit.getSelector().add("costBillId");
				conCost = CostSplitFactory.getLocalInstance(ctx).getCostSplitCollection(viewConCostSplit);
				conCostInfo = new CostSplitInfo();
				for (Iterator it = conCost.iterator(); it.hasNext();) {
					// SettledMonthlyInfo settle = new
					// SettledMonthlyInfo();
					conCostInfo = (CostSplitInfo) (it.next());
					BOSObjectType bosType = conCostInfo.getBOSType();
					String objectId = conCostInfo.getId().toString();
					if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
						SettledMonthlyInfo settle = new SettledMonthlyInfo();
						settle.setCurProjectID(prjId);
						settle.setEntityID(bosType.toString());
						settle.setObjectID(objectId);
						settle.setSettlePeriod(currentPeriod);
						settle.setContractID(conCostInfo.getCostBillId().toString());
						// settleMonth.addnew(settle);
						settle.setIsCost(true);
						iSettleMonth.addNewBatch(settle);
					}
				}
			}
		}

		// ������
		set = new HashSet();
		splitSet = new HashSet();

		builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder
				.appendSql("select distinct splitentry.fparentid from t_con_conchangesplitentry splitentry where splitentry.fcostaccountid in (select account.fid from t_fdc_costaccount account where account.FCurProject=?) \n");
		builder.appendSql(" and splitentry.fparentid in (select split.fid from t_con_conchangesplit split where (split.fstate<>'9INVALID' or (split.FIslastVerThisPeriod=1 and split.fstate='9INVALID' and split.fperiodid=?)) \n");
		builder.appendSql(" and split.fperiodid in (select period.fid from t_bd_period period where period.fnumber<=? ) \n");
		builder.appendSql("	and split.forgunitid in (	select fcostcenterid from t_fdc_curproject where fid in ( \n");
		builder.appendSql(" 	select fcurprojectid from t_con_conchangesplit where fid in( select distinct splitentry.fparentid from t_con_conchangesplitentry splitentry \n");
		builder.appendSql(" 	where splitentry.fcostaccountid in (select account.fid from t_fdc_costaccount account where account.FCurProject=? )))) \n");
		builder.appendSql("	)");
		builder.addParam(prjId);
		builder.addParam(currentPeriodId);
		builder.addParam(currentPeriodStr);
		builder.addParam(prjId);
//		builder.addParam(orgUnitId);
		rowSet = builder.executeQuery();
		while (rowSet.next()) {
			splitSet.add(rowSet.getString("fparentid"));
		}

		EntityViewInfo viewChangeSplit = new EntityViewInfo();
		FilterInfo filterChangeSplit = new FilterInfo();
		if (splitSet.size() > 0) {
			// filterChangeSplit.getFilterItems().add(
			// new FilterItemInfo("contractChange.curProject.id", idList
			// .get(i)));
			// filterChangeSplit.getFilterItems().add(
			// new FilterItemInfo("state",
			// FDCBillStateEnum.INVALID_VALUE,
			// CompareType.NOTEQUALS));
			// filterChangeSplit.getFilterItems().add(
			// new FilterItemInfo("period.number", Integer
			// .toString(currentPeriod.getNumber()),
			// CompareType.LESS_EQUALS));
			filterChangeSplit.getFilterItems().add(new FilterItemInfo("id", splitSet, CompareType.INCLUDE));
			viewChangeSplit.setFilter(filterChangeSplit);
			viewChangeSplit.getSelector().add("id");
			viewChangeSplit.getSelector().add("contractChange.contractBill.id");
			ConChangeSplitCollection conChange = ConChangeSplitFactory.getLocalInstance(ctx).getConChangeSplitCollection(viewChangeSplit);
			ConChangeSplitInfo conChangeInfo = new ConChangeSplitInfo();
			for (Iterator it = conChange.iterator(); it.hasNext();) {
				SettledMonthlyInfo settle = new SettledMonthlyInfo();
				conChangeInfo = (ConChangeSplitInfo) (it.next());
				BOSObjectType bosType = conChangeInfo.getBOSType();
				String objectId = conChangeInfo.getId().toString();
				if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
					// settle = new SettledMonthlyInfo();
					settle.setCurProjectID(prjId);
					settle.setEntityID(bosType.toString());
					settle.setObjectID(objectId);
					settle.setSettlePeriod(currentPeriod);
					settle.setContractID(conChangeInfo.getContractChange().getContractBill().getId().toString());
					// settleMonth.addnew(settle);
					settle.setIsCost(true);
					iSettleMonth.addNewBatch(settle);
					set.add(conChangeInfo.getId().toString());
				}
			}

			// �������м��
			if (set.size() > 0) {
				viewConCostSplit = new EntityViewInfo();
				filterConCostSplit = new FilterInfo();
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("splitBillId", set, CompareType.INCLUDE));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("isInvalid", Boolean.TRUE, CompareType.NOTEQUALS));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("costBillType", CostSplitBillTypeEnum.CNTRCHANGESPLIT_VALUE, CompareType.EQUALS));
				// filterConCostSplit.getFilterItems().add(
				// new FilterItemInfo("period.number", Integer
				// .toString(currentPeriod.getNumber()),
				// CompareType.LESS_EQUALS));

				viewConCostSplit.setFilter(filterConCostSplit);
				viewConCostSplit.getSelector().add("id");
				viewConCostSplit.getSelector().add("splitBillId");

				conCost = CostSplitFactory.getLocalInstance(ctx).getCostSplitCollection(viewConCostSplit);
				conCostInfo = new CostSplitInfo();
				for (Iterator it = conCost.iterator(); it.hasNext();) {
					SettledMonthlyInfo settle = new SettledMonthlyInfo();
					conCostInfo = (CostSplitInfo) (it.next());
					BOSObjectType bosType = conCostInfo.getBOSType();
					String objectId = conCostInfo.getId().toString();
					if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
						// settle = new SettledMonthlyInfo();
						settle.setCurProjectID(prjId);
						settle.setEntityID(bosType.toString());
						settle.setObjectID(objectId);
						settle.setSettlePeriod(currentPeriod);

						SelectorItemCollection selectorContractID = new SelectorItemCollection();
						selectorContractID.add("id");
						selectorContractID.add("contractChange.contractBill.id");
						ConChangeSplitInfo contractIDInfo = ConChangeSplitFactory.getLocalInstance(ctx).getConChangeSplitInfo(new ObjectUuidPK(conCostInfo.getSplitBillId()), selectorContractID);

						settle.setContractID(contractIDInfo.getContractChange().getContractBill().getId().toString());
						// settleMonth.addnew(settle);
						settle.setIsCost(true);
						iSettleMonth.addNewBatch(settle);
					}
				}
			}
		}

		// ������
		set = new HashSet();
		splitSet = new HashSet();

		builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder
				.appendSql("select distinct splitentry.fparentid from t_con_settlementcostsplitentry splitentry where splitentry.fcostaccountid in (select account.fid from t_fdc_costaccount account where account.FCurProject=?) and splitentry.fparentid in (select split.fid from t_con_settlementcostsplit split where (split.fstate<>'9INVALID' or (split.FIslastVerThisPeriod=1 and split.fstate='9INVALID' and split.fperiodid=?)) and split.fperiodid in (select period.fid from t_bd_period period where period.fnumber<=?))");
		builder.addParam(prjId);
		builder.addParam(currentPeriodId);
		builder.addParam(currentPeriodStr);
		rowSet = builder.executeQuery();
		while (rowSet.next()) {
			splitSet.add(rowSet.getString("fparentid"));
		}

		if (splitSet.size() > 0) {
			viewChangeSplit = new EntityViewInfo();
			filterChangeSplit = new FilterInfo();
			// filterChangeSplit.getFilterItems().add(
			// new FilterItemInfo(
			// "settlementBill.contractBill.curProject.id", idList
			// .get(i)));
			// filterChangeSplit.getFilterItems().add(
			// new FilterItemInfo("state",
			// FDCBillStateEnum.INVALID_VALUE,
			// CompareType.NOTEQUALS));
			// filterChangeSplit.getFilterItems().add(
			// new FilterItemInfo("period.number", Integer
			// .toString(currentPeriod.getNumber()),
			// CompareType.LESS_EQUALS));

			filterChangeSplit.getFilterItems().add(new FilterItemInfo("id", splitSet, CompareType.INCLUDE));

			viewChangeSplit.setFilter(filterChangeSplit);
			viewChangeSplit.getSelector().add("id");
			viewChangeSplit.getSelector().add("settlementBill.contractBill.id");
			SettlementCostSplitCollection settlement = SettlementCostSplitFactory.getLocalInstance(ctx).getSettlementCostSplitCollection(viewChangeSplit);
			SettlementCostSplitInfo settlementInfo = new SettlementCostSplitInfo();
			for (Iterator it = settlement.iterator(); it.hasNext();) {
				SettledMonthlyInfo settle = new SettledMonthlyInfo();

				settlementInfo = (SettlementCostSplitInfo) (it.next());
				BOSObjectType bosType = settlementInfo.getBOSType();
				String objectId = settlementInfo.getId().toString();
				if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
					// settle = new SettledMonthlyInfo();
					settle.setCurProjectID(prjId);
					settle.setEntityID(bosType.toString());
					settle.setObjectID(objectId);
					settle.setSettlePeriod(currentPeriod);
					settle.setContractID(settlementInfo.getSettlementBill().getContractBill().getId().toString());

					// settleMonth.addnew(settle);
					settle.setIsCost(true);
					iSettleMonth.addNewBatch(settle);

					set.add(settlementInfo.getId().toString());
				}
			}

			// �������м��
			if (set.size() > 0) {
				viewConCostSplit = new EntityViewInfo();
				filterConCostSplit = new FilterInfo();
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("splitBillId", set, CompareType.INCLUDE));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("isInvalid", Boolean.TRUE, CompareType.NOTEQUALS));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("costBillType", CostSplitBillTypeEnum.SETTLEMENTSPLIT_VALUE, CompareType.EQUALS));
				// filterConCostSplit.getFilterItems().add(
				// new FilterItemInfo("period.number", Integer
				// .toString(currentPeriod.getNumber()),
				// CompareType.LESS_EQUALS));

				viewConCostSplit.setFilter(filterConCostSplit);
				viewConCostSplit.getSelector().add("id");
				viewConCostSplit.getSelector().add("splitBillId");

				conCost = CostSplitFactory.getLocalInstance(ctx).getCostSplitCollection(viewConCostSplit);
				conCostInfo = new CostSplitInfo();
				for (Iterator it = conCost.iterator(); it.hasNext();) {
					SettledMonthlyInfo settle = new SettledMonthlyInfo();

					conCostInfo = (CostSplitInfo) (it.next());
					BOSObjectType bosType = conCostInfo.getBOSType();
					String objectId = conCostInfo.getId().toString();
					if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
						// settle = new SettledMonthlyInfo();
						settle.setCurProjectID(prjId);
						settle.setEntityID(conCostInfo.getBOSType().toString());
						settle.setObjectID(conCostInfo.getId().toString());
						settle.setSettlePeriod(currentPeriod);

						SelectorItemCollection selectorContractID = new SelectorItemCollection();
						selectorContractID.add("id");
						selectorContractID.add("settlementBill.contractBill.id");
						SettlementCostSplitInfo contractIDInfo = SettlementCostSplitFactory.getLocalInstance(ctx).getSettlementCostSplitInfo(new ObjectUuidPK(conCostInfo.getSplitBillId()),
								selectorContractID);

						settle.setContractID(contractIDInfo.getSettlementBill().getContractBill().getId().toString());
						// settleMonth.addnew(settle);
						settle.setIsCost(true);
						iSettleMonth.addNewBatch(settle);
					}
				}
			}
		}
		// Ŀ��ɱ�
		EntityViewInfo aimView = new EntityViewInfo();
		FilterInfo aimFilter = new FilterInfo();
		aimFilter.getFilterItems().add(new FilterItemInfo("orgOrProId", prjId));
		aimFilter.getFilterItems().add(new FilterItemInfo("period.number", nextPeriodStr, CompareType.LESS));
		// aimFilter.getFilterItems().add(
		// new FilterItemInfo("isLastVersion", Boolean.TRUE));
		aimView.setFilter(aimFilter);
		aimView.getSelector().add("id");
		aimView.getSorter().add(new SorterItemInfo("lastUpdateTime"));
		aimView.getSorter().get(0).setSortType(SortType.DESCEND);
		AimCostCollection aimColl = AimCostFactory.getLocalInstance(ctx).getAimCostCollection(aimView);
		if (aimColl.iterator().hasNext()) {
			AimCostInfo aimInfo = aimColl.get(0);
			SettledMonthlyInfo settle = new SettledMonthlyInfo();
			settle.setCurProjectID(prjId);
			settle.setEntityID(aimInfo.getBOSType().toString());
			settle.setObjectID(aimInfo.getId().toString());
			settle.setSettlePeriod(currentPeriod);
			// settleMonth.addnew(settle);
			settle.setIsCost(true);
			iSettleMonth.addNewBatch(settle);
		}

		// ������Ŀָ��
		aimView = new EntityViewInfo();
		aimFilter = new FilterInfo();
		aimFilter.getFilterItems().add(new FilterItemInfo("projOrOrgID", prjId));
		//ֻ�����°汾�����ݼ��뵽�ڼ�����,�������°汾�ֶλᱻ����,���½�����������ݴ���,ȡ����ָ������ by sxhong 2009-06-08 17:25:02
		aimFilter.appendFilterItem("isLatestVer", Boolean.TRUE);
		aimFilter.getFilterItems().add(new FilterItemInfo("period.number", nextPeriodStr, CompareType.LESS));
		// aimFilter.getFilterItems().add(
		// new FilterItemInfo("isLatestVer", Boolean.TRUE));
		aimView.setFilter(aimFilter);
		aimView.getSelector().add("id");
		aimView.getSorter().add(new SorterItemInfo("lastUpdateTime"));
		aimView.getSorter().get(0).setSortType(SortType.DESCEND);
		ProjectIndexDataCollection indexColl = ProjectIndexDataFactory.getLocalInstance(ctx).getProjectIndexDataCollection(aimView);
		for (Iterator it = indexColl.iterator(); it.hasNext();) {
			ProjectIndexDataInfo indexInfo = (ProjectIndexDataInfo) it.next();
			SettledMonthlyInfo settle = new SettledMonthlyInfo();
			settle.setCurProjectID(prjId);
			settle.setEntityID(indexInfo.getBOSType().toString());
			settle.setObjectID(indexInfo.getId().toString());
			settle.setSettlePeriod(currentPeriod);
			// settleMonth.addnew(settle);
			settle.setIsCost(true);
			iSettleMonth.addNewBatch(settle);
		}

		// ����������
		aimView = new EntityViewInfo();
		aimFilter = new FilterInfo();
		aimFilter.getFilterItems().add(new FilterItemInfo("parent.account.curProject.id", prjId));
		aimFilter.getFilterItems().add(new FilterItemInfo("period.number", nextPeriodStr, CompareType.LESS));
		aimView.setFilter(aimFilter);
		aimView.getSelector().add("id");
		AdjustRecordEntryCollection adjustColl = AdjustRecordEntryFactory.getLocalInstance(ctx).getAdjustRecordEntryCollection(aimView);
		for (Iterator it = adjustColl.iterator(); it.hasNext();) {
			AdjustRecordEntryInfo adjustInfo = (AdjustRecordEntryInfo) it.next();
			SettledMonthlyInfo settle = new SettledMonthlyInfo();
			settle.setCurProjectID(prjId);
			settle.setEntityID(adjustInfo.getBOSType().toString());
			settle.setObjectID(adjustInfo.getId().toString());
			settle.setSettlePeriod(currentPeriod);
			// settleMonth.addnew(settle);
			settle.setIsCost(true);
			iSettleMonth.addNewBatch(settle);
		}

		// ���������ύ���ݿ�
		iSettleMonth.executeBatch();
	}
}