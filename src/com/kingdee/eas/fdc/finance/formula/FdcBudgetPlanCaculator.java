package com.kingdee.eas.fdc.finance.formula;
/*
 * @(#)FdcPlanCaculator.java
 *
 * �����������������޹�˾��Ȩ���� 
 */


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.bos.dao.ObjectNotFoundException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.IContractPayPlan;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.eas.fi.newrpt.formula.IReportPropertyAdapter;
import com.kingdee.eas.ma.budget.BgPeriodFactory;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.BgRptReportPropertyAdapter;
import com.kingdee.eas.ma.budget.IBgPeriod;
import com.kingdee.util.StringUtils;

/**
 * 
 * @author pengwei_hou
 * @date 2009-06-11
 * @work ��ͬ����ƻ��üƻ��ڼ��Ӧ�·ݵļƻ�����
 * @method fdc_budget_plan Ԥ��ϵͳ��ͬ����ƻ�ȡ������
 */
public class FdcBudgetPlanCaculator implements ICalculator, IMethodBatchQuery {

	private ICalculateContextProvider context;

	private Context ServerCtx = null;

	/** ���ز���ͬ����ƻ��ӿ� */
	private IContractPayPlan iContractPayPlan;

	/** ���ز���ͬ�ӿ� */
	private IContractBill iContractBill;

	/** ʵ��ɱ����ı��� */
	private String costCenterNumber = null;

	/** �ڼ���� */
	private String planPeriodNumber;

	public FdcBudgetPlanCaculator() {

	}

	public void initCalculateContext(ICalculateContextProvider context) {
		this.context = context;
		this.ServerCtx = this.context.getServerContext();
	}

	protected ICalculateContextProvider getProvider() {
		return this.context;
	}
	
	public boolean batchQuery(Map methods) {

		SortedParameterArray params = (SortedParameterArray) methods
				.get("fdc_budget_plan");
		IReportPropertyAdapter adapter = null;
		//��ȡ��ʼ����
		adapter = getProvider().getReportAdapter();
		String _currOrgUnitNumber = (String) adapter.getReportProperty(BgRptReportPropertyAdapter.BG_ORG_NUMBER);
		String _currPeriodNumber = (String) adapter.getReportProperty(BgRptReportPropertyAdapter.BG_PERIOD_NUMBER);
		
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();

				costCenterNumber = (String) ((Variant) obj[0]).getValue();
				planPeriodNumber = (String) ((Variant) obj[1]).getValue();
				if (StringUtils.isEmpty(costCenterNumber)) {
					if(_currOrgUnitNumber==null){
						costCenterNumber="";
					}else{
						costCenterNumber = _currOrgUnitNumber;
					}
				}
				if (StringUtils.isEmpty(planPeriodNumber)) {
					if(_currPeriodNumber==null){
						planPeriodNumber="";
					}else{
						planPeriodNumber = _currPeriodNumber;
					}
				}
				try {
					BigDecimal amount = fdc_budget_plan(costCenterNumber,
							planPeriodNumber);
					params.getParameter(i).setValue(amount);

				} catch (EASBizException e) {
					e.printStackTrace();
					return false;
				} catch (BOSException e) {
					e.printStackTrace();
					return false;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		}

		return true;
	}
	
	public Context getServerCtx() {
		return ServerCtx;
	}

	public void setServerCtx(Context serverCtx) {
		ServerCtx = serverCtx;
	}
	
	private IBgPeriod getBgPeriod() throws BOSException {
		return ServerCtx == null ? BgPeriodFactory.getRemoteInstance() : BgPeriodFactory.getLocalInstance(ServerCtx);
	}
	
	private IContractPayPlan getIContractPayPlan() throws BOSException {
		return iContractPayPlan != null ? iContractPayPlan : (iContractPayPlan = (ServerCtx != null ? ContractPayPlanFactory.getLocalInstance(ServerCtx)
				: ContractPayPlanFactory.getRemoteInstance()));
	}

	private IContractBill getIContractBill() throws BOSException {
		return iContractBill != null ? iContractBill : (iContractBill = (ServerCtx != null ? ContractBillFactory.getLocalInstance(ServerCtx)
				: ContractBillFactory.getRemoteInstance()));
	}
	
	/**
	 * ���ز��ʽ�ƻ�ȡ����ʽ_�ƻ���
	 * �������������ڣ����Ҳ���������ļ������еĲ�������һ�£����򱨱��޷��ж�ȡ�������Ƿ����
	 * @param fdcProject
	 * @param planPeriod
	 * @return BigDecimal
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException 
	 */
	public  BigDecimal fdc_budget_plan(String costCenterNumber, String planPeriod) throws BOSException, EASBizException, SQLException {
		List curProjectID = FDCBudgetAcctCaculatorHelper.initProjectIDList(ServerCtx, costCenterNumber);
		String projectID = "";
		if(curProjectID.size()==1){
			projectID = curProjectID.get(0).toString() ;
		}
		if(projectID==null||"".equals(projectID)){
			return FDCHelper.ZERO;
		}
		IBgPeriod iBgPeriod = getBgPeriod();

		BgPeriodInfo info = null;

		BigDecimal sum = FDCHelper.ZERO;

		String oql = "SELECT * WHERE number = '" + planPeriod + "'";

		try {
			info = iBgPeriod.getBgPeriodInfo(oql);
		} catch (ObjectNotFoundException e) {
			info= new BgPeriodInfo();
		}

		// ��ȡ��ͬ
		EntityViewInfo evi = new EntityViewInfo();
		
		evi.getSelector().add("*");
		
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectID));

		
		evi.setFilter(filter);
		
		ContractBillCollection contractBillCollection = getIContractBill().getContractBillCollection(evi);

		Set set = new HashSet();

		for (int j = 0; j < contractBillCollection.size(); j++) {
			set.add(contractBillCollection.get(j).getId().toString());
		}

		if (set.size() < 1)
			return sum;

		// ��ȡ��ͬ�ƻ�
		evi = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("payDate", info.getBeginDate(), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("payDate", info.getEndDate(), CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("contractId", set, CompareType.INCLUDE));
		evi.setFilter(filter);
		evi.getSelector().add(new SelectorItemInfo("*"));
		ContractPayPlanCollection cppc = getIContractPayPlan().getContractPayPlanCollection(evi);

		if (cppc != null) {
			for (int j = 0; j < cppc.size(); j++) {
				sum = sum.add(cppc.get(j).getPayAmount());
			}
			return sum;
		}

		return sum;
	}
}
