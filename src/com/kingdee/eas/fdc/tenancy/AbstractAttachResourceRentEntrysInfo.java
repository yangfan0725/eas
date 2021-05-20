package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAttachResourceRentEntrysInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractAttachResourceRentEntrysInfo()
    {
        this("id");
    }
    protected AbstractAttachResourceRentEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������Դ�����¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.tenancy.AttachResourceRentBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.AttachResourceRentBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.AttachResourceRentBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ������Դ�����¼ 's ������Դ property 
     */
    public com.kingdee.eas.fdc.tenancy.AttachResourceInfo getAttach()
    {
        return (com.kingdee.eas.fdc.tenancy.AttachResourceInfo)get("attach");
    }
    public void setAttach(com.kingdee.eas.fdc.tenancy.AttachResourceInfo item)
    {
        put("attach", item);
    }
    /**
     * Object:������Դ�����¼'s ��׼���property 
     */
    public java.math.BigDecimal getStandardRent()
    {
        return getBigDecimal("standardRent");
    }
    public void setStandardRent(java.math.BigDecimal item)
    {
        setBigDecimal("standardRent", item);
    }
    /**
     * Object:������Դ�����¼'s �Ƿ����property 
     */
    public boolean isIsAdjust()
    {
        return getBoolean("isAdjust");
    }
    public void setIsAdjust(boolean item)
    {
        setBoolean("isAdjust", item);
    }
    /**
     * Object:������Դ�����¼'s �������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("rentType"));
    }
    public void setRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("rentType", item.getValue());
		}
    }
    /**
     * Object:������Դ�����¼'s ����״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateEnum getTenancyState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateEnum.getEnum(getString("tenancyState"));
    }
    public void setTenancyState(com.kingdee.eas.fdc.tenancy.TenancyStateEnum item)
    {
		if (item != null) {
        setString("tenancyState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0063AF56");
    }
}