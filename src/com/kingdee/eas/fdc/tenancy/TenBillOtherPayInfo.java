package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;

public class TenBillOtherPayInfo extends AbstractTenBillOtherPayInfo implements Serializable 
{
    public TenBillOtherPayInfo()
    {
        super();
        //TODO ��ͳһ����Ϊʣ�������
        this.setIsRemainCanRefundment(true);
    }
    
    public RevListTypeEnum getRevListTypeEnum()
    {
    	return RevListTypeEnum.tenOtherRev;
    }
}