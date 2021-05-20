package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetCtrlStrategy.FDCBudgetParam;
import com.kingdee.eas.util.app.ContextUtil;

public class FDCBudgetCtrlStrategyFactory {
/*	private static FDCBudgetCtrlStrategy getInstance(FDCBudgetCtrlStrategyType type){
		if(type==FDCBudgetCtrlStrategyType.CONTRACTPLANCTRL){
			return new FDCBudgetConCtrlStrategy();
		}
		if(type==FDCBudgetCtrlStrategyType.ACCTPLANCTRL){
			return new FDCBudgetAcctCtrlStrategy();
		}
		if(type==FDCBudgetCtrlStrategyType.BGSYSCTRL){
			return new FDCBudgetBgSysCtrlStrategy();
		}
		if(type==FDCBudgetCtrlStrategyType.CONTRACTPLANCTRL){
			return new FDCBudgetConCtrlStrategy();
		}
		return new FDCBudgetNotCtrlStrategy();
	}*/
	/***
	 * ��ʹ��getInstanceByOrgId����
	 * @deprecated
	 * @see getInstanceByOrgId(Context ctx, String companyId)
	 */
	public static FDCBudgetCtrlStrategy getInstance(Context ctx) throws EASBizException, BOSException{
		return getInstanceByOrgId(ctx, null);
	}
	
	/***
	 * ����: ���ݴ���Ĳ�����֯ID��companyId������ȡ������ �����ݲ�������ʼ��FDCBudgetCtrlStrategy�� <br>
	 *       ��companyIdΪNULLʱ��ʹ�õ�ǰ������֯ID
	 * @param ctx
	 * @param companyId ������֯ID
	 */
	public static FDCBudgetCtrlStrategy getInstanceByOrgId(Context ctx, String companyId) throws EASBizException, BOSException{
		//get param 
		if (companyId == null) {
			companyId=ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		}
		FDCBudgetParam param=FDCBudgetParam.getInstance(ctx,companyId);
		FDCBudgetCtrlStrategy ctrlStrategy =null;
		if(param.isContractPlanCtrl()){
			ctrlStrategy=new  FDCBudgetConCtrlStrategy(ctx,param); 
		}
		if(param.isAcctPlanCtrl()){
			ctrlStrategy=new FDCBudgetAcctCtrlStrategy(ctx,param);
		}
		if(param.isBgSysCtrl()){
			ctrlStrategy=new FDCBudgetBgSysCtrlStrategy(ctx,param);
		}
		if(ctrlStrategy==null){
			ctrlStrategy=new FDCBudgetNotCtrlStrategy(ctx,param);
		}
		return ctrlStrategy;
	}
	
	/**
	 * R101227-067:����ʱȡ�����������ڵ���֯���²���ֵ���ԣ�ֱ��ȡ���ݹ�����Ŀ�Ĳ�����֯
	 * @param ctx
	 * @param info
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static FDCBudgetCtrlStrategy getInstance(Context ctx,IObjectValue info) throws EASBizException, BOSException{
//		String companyId=ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		String companyId=((PayRequestBillInfo)info).getCurProject().getFullOrgUnit().getId().toString();
		FDCBudgetParam param=FDCBudgetParam.getInstance(ctx,companyId);
		FDCBudgetCtrlStrategy ctrlStrategy =null;
		if(param.isContractPlanCtrl()){
			ctrlStrategy=new  FDCBudgetConCtrlStrategy(ctx,param); 
		}
		if(param.isAcctPlanCtrl()){
			ctrlStrategy=new FDCBudgetAcctCtrlStrategy(ctx,param);
		}
		if(param.isBgSysCtrl()){
			ctrlStrategy=new FDCBudgetBgSysCtrlStrategy(ctx,param);
		}
		if(ctrlStrategy==null){
			ctrlStrategy=new FDCBudgetNotCtrlStrategy(ctx,param);
		}
		return ctrlStrategy;
	}

}
