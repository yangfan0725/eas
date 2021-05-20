package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
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
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.eas.fi.newrpt.formula.IReportPropertyAdapter;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.BgRptReportPropertyAdapter;
import com.kingdee.eas.ma.budget.IBgPeriod;
import com.kingdee.util.StringUtils;
/**
 * Ԥ��ϵͳʵ�ʸ���ȡ������
 * @author pengwei_hou
 * @date 2009-06-10
 * @work
 */
public class FdcBudgetAcctActualPayCaculator implements ICalculator, IMethodBatchQuery 
{

	private Context ServerCtx = null;

	private ICalculateContextProvider context;

	/** �ɱ����ı��� */
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
	
	public boolean batchQuery(Map methods)
	{
//		��ôӽ��洫�ݹ�������ز���
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_budget_acct_actualPay");
		
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
				if (StringUtils.isEmpty(costCenterNumber)) {
					if(_currOrgUnitNumber==null){
						costCenterNumber="";
					}else{
						costCenterNumber = _currOrgUnitNumber;
					}
				}
				if (StringUtils.isEmpty(budgetPeriod)) {
					budgetPeriod = _currPeriodNumber;
				}
				try {
					// ͨ�����㹫ʽ���㣬���ؽ��
					BigDecimal amount = this.fdc_budget_acct_actualPay(
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
	 * Ԥ��ϵͳʵ�ʳɱ�ȡ������
	 * 
	 * @param costCenterNumber
	 *            ʵ��ɱ����ĳ�����
	 * @param acctLongNumber
	 *            �ɱ���Ŀ������
	 * @param budgetPeriod
	 *            Ԥ���ڼ�
	 * @param periodStep 
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	public BigDecimal fdc_budget_acct_actualPay(String costCenterNumber,
			String acctLongNumber, String budgetPeriod, String periodStep) throws ParseException,
			BOSException, SQLException, EASBizException {
//		Date transformDate = null;
//		transformDate = FDCDateHelper.stringToDate(budgetPeriod);
//		// �������¶ȣ�����Ӧ�ö�����ת��������ûÿ���µ����һ���ʱ��
//		transformDate = FDCDateHelper.getLastDayOfMonth2(transformDate);
		BgPeriodInfo period = FDCBudgetAcctCaculatorHelper.getBgPeriod(ServerCtx, budgetPeriod);
		int step = Integer.parseInt(periodStep);
		if(step!=0){
			int month = period.getMonth()+step;
			period.setMonth(month);
		}
		List curProjectID = FDCBudgetAcctCaculatorHelper.initProjectIDList(
				ServerCtx, costCenterNumber);
		String projectID = "" ;
		BigDecimal result = FDCHelper.ZERO;
		if(curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
			List costAccountID = FDCBudgetAcctCaculatorHelper.initCostAccount(ServerCtx, projectID, acctLongNumber);
			if(costAccountID.size() > 0)
			{
				result = FDCBudgetAcctCaculatorHelper.getActualPay(ServerCtx, costAccountID, period);
			}
			if(result == null)
			{
				result = FDCHelper.ZERO ;
			}
		}
		return result;
	}
}
