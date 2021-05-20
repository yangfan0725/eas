package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.app.AbstractRevHandle;
import com.kingdee.eas.fdc.tenancy.TENRevHelper;
import com.kingdee.eas.framework.ICoreBase;

public class TenRevHandle extends AbstractRevHandle{

	public void doAfterRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException {
		// ����Ŀǰû���տ�����Ҫ��дҵ�񵥾ݵ�

	}

	public void doBeforeRev(Context ctx, FDCReceivingBillInfo rev, FDCReceivingBillInfo oldFdcRev) throws BOSException, EASBizException {
		//�����ж��Ƿ�����տ�
	}

	protected String[] getNeededFieldNames(RevListTypeEnum revListType){
		//�������ͳһ�˸��ֶε�����
		return new String[]{"actRevAmount", "hasRefundmentAmount", "hasToFeeAmount", "hasTransferredAmount", "appAmount","actRevDate"};
		
		/*
		if(RevListTypeEnum.tenRoomRev.equals(revListType)){
			return new String[]{"", "", "", ""};
		}else if(RevListTypeEnum.tenOtherRev.equals(revListType)){
			//TODO ���������տ���ϸ
			
		}
		return null;
		*/
	}
	
	public String getActRevDateFieldName(RevListTypeEnum revListType) {
		return super.getActRevDateFieldName(revListType);
	}
	
	public ICoreBase getRevListBizInterface(Context ctx, RevListTypeEnum revListType) throws BOSException {
		return TENRevHelper.getRevListBizInterface(ctx, revListType);
	}

}
