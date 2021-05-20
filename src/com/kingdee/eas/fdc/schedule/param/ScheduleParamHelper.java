package com.kingdee.eas.fdc.schedule.param;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.contract.FDCUtils;

public class ScheduleParamHelper {
	/**
	 * �����������������޸Ĳ�����������WBS�ڵ�����
	 */
	public static final String FDCSCH_PARAM_CANSAVEADDNEWTASK = "FDCSCH001";
	/**
	 * ֧���¶ȼƻ���������ƻ�
	 */
	public static final String FDCSCH_PARAM_MONTHPLANIMPORTMAINTASK = "FDCSCH002";
	//���ȹ���  �Ƿ������ڼƻ������������
	

	
	/**
     * ��÷��ز�ϵͳ���ȹ���Ĳ���
     * 
     * @param ctx
     * @param companyID
     * @return
     */
    public static HashMap getAllSchParam(Context ctx, String companyID) throws BOSException, EASBizException {
		// IObjectPK comPK = null;
		// if(companyID!=null){
		// comPK = new ObjectUuidPK(companyID);
		// }
        
        HashMap hmParamIn = new HashMap();
        hmParamIn.put(FDCSCH_PARAM_CANSAVEADDNEWTASK, null);
        hmParamIn.put(FDCSCH_PARAM_MONTHPLANIMPORTMAINTASK, null);
        
        IParamControl pc;
        if(ctx!=null)
        	pc = ParamControlFactory.getLocalInstance(ctx);
        else
        	pc= ParamControlFactory.getRemoteInstance();
        HashMap hmAllParam = pc.getParamHashMap(hmParamIn);
        
        return hmAllParam;
    }
    
    /**
     * ��÷��ز�ϵͳ����,������ֵΪ��/�����͵Ĳ���
     * 
     * @param ctx
     * @param companyID
     * @param fdcParamKey
     * @return
     */
    public static boolean getSchParamByKey(Context ctx, String companyID,String fdcParamKey) throws BOSException, EASBizException {
        HashMap hmAllParam = getAllSchParam(ctx,companyID);
        Object theValue = hmAllParam.get(fdcParamKey);
        if(theValue != null){
			return Boolean.valueOf(theValue.toString()).booleanValue();
		}
        return false;
    }
    
    /**
     * 
     * �����Է���,ͨ���������ֵMap,����������ȡ����,��������ȡ������ʵ��
     * ��:
     * Map paramMap=FDCUtils.getDefaultFDCParam(...);
     * boolean flag=FDCUtils.getParamValue(paramMap,FDCConstants.FDC_PARAM_BUDGET_DISPLAYCOST);
     *  by sxhong 2008-09-23 17:03:05
     * @param paramMap
     * @param fdcParamKey
     * @return
     */
    public static boolean getParamValue(Map paramMap,String fdcParamKey){
        return FDCUtils.getParamValue(paramMap, fdcParamKey);
    }
}
