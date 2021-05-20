package com.kingdee.eas.fdc.basecrm.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.framework.ICoreBase;

/**
 * ���������տ������ӿڣ�����ҵ��ϵͳ��չ
 * */
public interface IRevHandle {
	/**
	 * ɾ���տʱ��ҵ��ϵͳ�ķ�д
	 * */
	void doOfDelRev(Context ctx, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException;
	
	/**
	 * �տ�֮ǰ��У�鼰����
	 * @param oldFdcRev 
	 * */
	void doBeforeRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException;
	
	/**
	 * �տ�֮��Ĳ�������Ҫ�ǶԸ�ҵ��ϵͳ�ķ�д
	 * @param oldFdcRev 
	 * */
	void doAfterRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException;
	
	/**
	 * �����տ���ϸ��ID������,��ѯ���տ���ϸ����
	 * */
	IRevListInfo getRevListInfoObj(Context ctx, String id, RevListTypeEnum revListType)throws EASBizException, BOSException;

	/**
	 * �����տ���ϸ������,���ز����տ���ϸ��ҵ��ӿ�
	 * @throws BOSException 
	 * */
	ICoreBase getRevListBizInterface(Context ctx, RevListTypeEnum revListType) throws BOSException;

	/**
	 * ��������,���ʵ�ս���ֶ���
	 * */
	String getActRevFieldName(RevListTypeEnum revListType);

	/**
	 * ��������,������˽���ֶ���
	 * */
	String getHasRefundmentFieldName(RevListTypeEnum revListType);

	/**
	 * ��������,���������ý���ֶ���
	 * */
	String getHasToFeeFieldName(RevListTypeEnum revListType);

	/**
	 * ��������,�����ת����ֶ���
	 * */
	String getHasTransferredFieldName(RevListTypeEnum revListType);
	
	/**
	 * ��������,���Ӧ�ս���ֶ���
	 * */
	String getAppRevFieldName(RevListTypeEnum revListType);
	
	/**
	 * ��������,���ʵ�������ֶ���
	 * */
	String getActRevDateFieldName(RevListTypeEnum revListType);
	
}
