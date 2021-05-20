package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.eas.fi.newrpt.formula.IReportPropertyAdapter;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.BgRptReportPropertyAdapter;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;

/**
 * 
 * @author pengwei_hou
 * @date 2009-06-11
 * @work �ڼ�ƫ����δ¼�����Ϊ0����Ŀ�¶�Ԥ���ĸ�Ԥ���ڼ��Ӧ�·ݼƻ����������ڼ�ƫ����ΪN����Ϊ��Ŀ�¶�Ԥ����Ԥ���ڼ�+N�ڼ��Ӧ�·ݼƻ���������
 * @method fdc_budget_acct_monthlyBudgetPay ��Ŀ�¶�Ԥ���ĸ�Ԥ���ڼ��Ӧ�·ݼƻ���������
 */
public class FdcBudgetAcctMonthlyBudgetPayCaculator implements IMethodBatchQuery,
		ICalculator {
	
	private Context ServerCtx = null;

	private ICalculateContextProvider context;

	/** ʵ��ɱ����ı��� */
	private String costCenterNumber = null;

	/** �ɱ���Ŀ������ */
	private String acctLongNumber = null;

	/** Ԥ���ڼ���� */
	private String budgetPeriod = null;
	
	/**�ڼ�ƫ����*/
	private String periodStep = null;
	
	public Context getServerCtx()
	{
		return ServerCtx;
	}

	public void setServerCtx(Context serverCtx)
	{
		ServerCtx = serverCtx;
	}
	
	public void initCalculateContext(ICalculateContextProvider context) {
		this.context = context;
		this.ServerCtx = this.context.getServerContext();
	}
	
	protected ICalculateContextProvider getProvider() {
		return this.context;
	}
	
	public boolean batchQuery(Map methods) {
		//��ôӽ��洫�ݹ�������ز���
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_budget_acct_monthlyBudgetPay");
		
		IReportPropertyAdapter adapter = null;
		
		// ��ȡ��ʼ����
		adapter = getProvider().getReportAdapter();
		
		String _currOrgUnitNumber = (String) adapter.getReportProperty(BgRptReportPropertyAdapter.BG_ORG_NUMBER);
		String _currPeriodNumber = (String) adapter.getReportProperty(BgRptReportPropertyAdapter.BG_PERIOD_NUMBER);
		
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();
				
				costCenterNumber = (String) ((Variant) obj[0]).getValue();
				acctLongNumber = (String) ((Variant) obj[1]).getValue();
				budgetPeriod = (String) ((Variant) obj[2]).getValue();
				Object value = ((Variant) obj[3]).getValue();
				if(value != null){
					periodStep = ((Variant) obj[3]).getValue().toString();
				} else {
					periodStep = "0";
				}
//				periodStep = (String) ((Variant) obj[3]).getValue();
				//���ȡ����ʽ��ʵ��ɱ�����Ϊ��Ĭ�ϵ�����Ϊ��ǰ�ɱ�����
				if (StringUtils.isEmpty(costCenterNumber)) {
					if(_currOrgUnitNumber==null){
						costCenterNumber="";
					}else{
						costCenterNumber = _currOrgUnitNumber;
					}
				}
				//���Ԥ���ڼ�Ϊ��Ĭ�ϵ�����ΪԤ��ϵͳ��ǰ�ڼ�
				if (StringUtils.isEmpty(budgetPeriod)) {
					budgetPeriod = _currPeriodNumber;
				}
//				if (StringUtils.isEmpty(periodStep)) {
//					periodStep = "0";
//				}
				try {
					// ͨ�����㹫ʽ���㣬���ؽ��
					BigDecimal amount = this.fdc_budget_acct_monthlyBudgetPay(
							costCenterNumber, acctLongNumber, budgetPeriod, periodStep);
					params.getParameter(i).setValue(amount);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Ԥ��ϵͳ�¶�Ԥ��ƻ�����ȡ������
	 * �ڼ�ƫ����δ¼�����Ϊ0����Ŀ�¶�Ԥ���ĸ�Ԥ���ڼ��Ӧ�·ݼƻ����������ڼ�ƫ����ΪN����Ϊ��Ŀ�¶�Ԥ����Ԥ���ڼ�+N�ڼ��Ӧ�·ݼƻ���������
	 * @param costCenterNumber �ɱ����ĳ�����
	 * @param acctLongNumber �ɱ���Ŀ������
	 * @param budgetPeriod Ԥ���ڼ�(�꣬��)
	 * @param periodStep �ڼ�ƫ����
	 * @return
	 * @throws ParseException
	 * @throws BOSException
	 * @throws SQLException
	 * @throws EASBizException 
	 */
	public BigDecimal fdc_budget_acct_monthlyBudgetPay(String costCenterNumber,
			String acctLongNumber, String budgetPeriod,String periodStep) throws ParseException,
			BOSException, SQLException, EASBizException {
		BgPeriodInfo period = FDCBudgetAcctCaculatorHelper.getBgPeriod(ServerCtx, budgetPeriod);
//		Date transformDate = null ;
//		transformDate = FDCDateHelper.stringToDate(budgetPeriod);
		int step = Integer.parseInt(periodStep);
		if(step!=0){
			int month = period.getMonth()+step;
			period.setMonth(month);
//			transformDate = DateTimeUtils.addMonth(transformDate, step);
		}
		
		//�������¶ȣ�����Ӧ�ö�����ת��������ûÿ���µ����һ���ʱ��
//		transformDate = FDCDateHelper.getLastDayOfMonth2(transformDate);
		
		List curProjectID = FDCBudgetAcctCaculatorHelper.initProjectIDList(ServerCtx, costCenterNumber);
		String projectID = "" ;
		BigDecimal result = FDCHelper.ZERO;
		if(curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
			List costAccountID = FDCBudgetAcctCaculatorHelper.initCostAccount(ServerCtx, projectID, acctLongNumber);
			result = FDCBudgetAcctCaculatorHelper.getMonthlyBudgetPay(ServerCtx,projectID,costAccountID, period);	
			if(result==null){
				result = FDCHelper.ZERO;
			}
		}
		return result;
	}

}
