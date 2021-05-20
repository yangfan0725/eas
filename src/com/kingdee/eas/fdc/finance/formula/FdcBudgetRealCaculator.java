package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
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
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
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
 * @work ��ͬʵ�ʸ����ڿ�ʼ�ڼ䵽�����ڼ�֮����·ݵ������Ѹ���ĸ������ۼơ�
 * �����������¼�룬��ֻȡ��Ӧ�����������ݣ�δ¼�룬��ȡ���и����������ݣ�����ʼ�ڼ�������ڼ���ͬ����ȡ���ڼ�Ķ�Ӧ�·ݵ������Ѹ���ĸ������ۼơ�
 * �����������¼�룬��ֻȡ��Ӧ�����������ݣ�δ¼�룬��ȡ���и����������ݣ�
 * @method fdc_budget_real Ԥ��ϵͳ��ͬʵ�ʸ���ȡ������
 */
public class FdcBudgetRealCaculator implements ICalculator, IMethodBatchQuery {

	private ICalculateContextProvider context;

	private Context serverCtx = null;

	/** ���ز���ͬ�ӿ� */
	private IContractBill iContractBill;

	/** ���ز�����ӿ� */
	private IPaymentBill iPaymentBill;

	/** �ɱ����ı��� */
	private String costCenterNumber = null;

	/** ���������� */
	private String paymentTypeNumber;

	/** ��ʼ�ڼ� */
	private String periodBeginNumber;

	/** �����ڼ� */
	private String periodEndNumber;

	public FdcBudgetRealCaculator() {

	}

	public void initCalculateContext(ICalculateContextProvider context) {
		this.context = context;
		this.serverCtx = this.context.getServerContext();
	}
	
	protected ICalculateContextProvider getProvider() {
		return this.context;
	}
	
	public boolean batchQuery(Map methods) {

		SortedParameterArray params = (SortedParameterArray)methods.get("fdc_budget_real");
		
		IReportPropertyAdapter adapter = null;
		
		// ��ȡ��ʼ����
		adapter = getProvider().getReportAdapter();
		
		String _currOrgUnitNumber = (String) adapter.getReportProperty(BgRptReportPropertyAdapter.BG_ORG_NUMBER);
		String _currPeriodNumber = (String) adapter.getReportProperty(BgRptReportPropertyAdapter.BG_PERIOD_NUMBER);
		if(params!=null){
			for(int i=0;i<params.size();i++)
			{
			Parameter param = params.getParameter(i);
			Object[] obj = param.getArgs();
			
			costCenterNumber = (String)((Variant)obj[0]).getValue();
			paymentTypeNumber = (String)((Variant)obj[1]).getValue();
			periodBeginNumber = (String)((Variant)obj[2]).getValue();
			periodEndNumber = (String)((Variant)obj[3]).getValue();
			
			if(StringUtils.isEmpty(costCenterNumber)){
				if(_currOrgUnitNumber==null){
					costCenterNumber="";
				}else{
					costCenterNumber = _currOrgUnitNumber;
				}
			}
			if (StringUtils.isEmpty(periodBeginNumber)) {
				if(_currPeriodNumber==null){
					periodBeginNumber="";
				}else{
					periodBeginNumber = _currPeriodNumber;
				}
			}
			if (StringUtils.isEmpty(periodEndNumber)) {
				periodEndNumber = _currPeriodNumber;
			}
		try {
			BigDecimal amount = fdc_budget_real(costCenterNumber,paymentTypeNumber, periodBeginNumber,periodEndNumber);
			params.getParameter(i).setValue(amount);
		} catch (EASBizException e) {
			e.printStackTrace();
			return false ;
		} catch (BOSException e) {
			e.printStackTrace();
			return false ;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		}
	}
		return true;
	}
	

	public Context getServerCtx() {
		return serverCtx;
	}

	public void setServerCtx(Context serverCtx) {
		this.serverCtx = serverCtx;
	}
	

	private IContractBill getIContractBill() throws BOSException {
		return iContractBill != null ? iContractBill : (iContractBill = (serverCtx != null ? ContractBillFactory.getLocalInstance(serverCtx)
				: ContractBillFactory.getRemoteInstance()));
	}

	private IPaymentBill getIPaymentBill() throws BOSException {
		return iPaymentBill != null ? iPaymentBill : (iPaymentBill = (serverCtx != null ? PaymentBillFactory.getLocalInstance(serverCtx) : PaymentBillFactory
				.getRemoteInstance()));
	}

	private IBgPeriod getBgPeriod() throws BOSException {
		return serverCtx == null ? BgPeriodFactory.getRemoteInstance() : BgPeriodFactory.getLocalInstance(serverCtx);
	}

	/**
	 * Ԥ��ϵͳ��ͬʵ�ʸ���ȡ������
	 * 
	 * @param costCenterNumber
	 * @param paymentType
	 * @param periodBegin
	 * @param periodEnd
	 * @return BigDecimal
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException 
	 */
	public BigDecimal fdc_budget_real(String costCenterNumber, String paymentType, String periodBegin, String periodEnd) throws BOSException, EASBizException, SQLException {
		List curProjectID = FDCBudgetAcctCaculatorHelper.initProjectIDList(serverCtx, costCenterNumber);
		String projectID = "";
		if(curProjectID.size()==1){
			projectID = curProjectID.get(0).toString() ;
		}
		if(projectID==null||"".equals(projectID)){
			return FDCHelper.ZERO;
		}
		// ��������
		IBgPeriod iBgPeriod = getBgPeriod();

		BgPeriodInfo info;
		Date endDate, beginDate;
		EntityViewInfo evi;
		FilterInfo filter;
		BigDecimal sum = FDCHelper.ZERO;
		//
		// ȡ�ÿ�ʼ�ڼ�
		String oql = "SELECT * WHERE number = '" + periodBegin + "'";
		try {
			info = iBgPeriod.getBgPeriodInfo(oql);
		} catch (ObjectNotFoundException e) {
			info=new BgPeriodInfo();
		}
		beginDate = info.getBeginDate();

		// ȡ�ý����ڼ�
		oql = "SELECT * WHERE number = '" + periodEnd + "'";
		info = iBgPeriod.getBgPeriodInfo(oql);
		if(periodBegin.equals(periodEnd)){
			endDate = info.getEndDate();
		} else {
			endDate = info.getBeginDate();
		}
		

		evi = new EntityViewInfo();
		filter = new FilterInfo();
//		String ln = fdcProject.replace('.', '!');
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectID));

		evi.getSelector().add("*");
		evi.setFilter(filter);
		ContractBillCollection contractBillCollection = getIContractBill().getContractBillCollection(evi);
		Set set = new HashSet();

		for (int j = 0; j < contractBillCollection.size(); j++) {
			set.add(contractBillCollection.get(j).getId().toString());
		}
		if (set.size() < 1)
			return sum;

		// ��ȡ���
		evi = new EntityViewInfo();
		filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED));
		filter.getFilterItems().add(new FilterItemInfo("payDate", beginDate, CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("payDate", endDate, CompareType.LESS_EQUALS));
		if(!StringUtils.isEmpty(paymentType)){
			filter.getFilterItems().add(new FilterItemInfo("fdcPayType.number", paymentType));
		}
		filter.getFilterItems().add(new FilterItemInfo("contractBillId", set, CompareType.INCLUDE));
		evi.setFilter(filter);
		evi.getSelector().add(new SelectorItemInfo("actPayLocAmt"));
		PaymentBillCollection pbc = getIPaymentBill().getPaymentBillCollection(evi);
		if (pbc != null) {
			for (int j = 0; j < pbc.size(); j++) {
				sum = FDCHelper.add(sum,pbc.get(j).getActPayLocAmt());
			}
		}
		return sum;
	}

}
