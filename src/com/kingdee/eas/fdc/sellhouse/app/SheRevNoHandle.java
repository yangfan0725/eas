package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;


/**
 * SheRevHandle ����չ
 * Ҳ���ǲ���Ҫ�����տ�տ��ɾ���ķ�д�����
 * 
 * ������ 1.����ת����ʱ���ɵ�ת������ÿ��Ƿ���״̬�ı仯
 * 		  2.��������ʱ תΪԤ�տ
 * 		  3.�˷�����ʱ д���˽���ת�������� 
 * 
 * 
 * @author jeegan_wang *
 */
public class SheRevNoHandle extends SheRevHandle {

	public void doBeforeRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException{
	}
	
	public void doAfterRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException {
	}
	
	
	public void doOfDelRev(Context ctx, FDCReceivingBillInfo oldFdcRev)	throws BOSException, EASBizException {
	}
	
}
