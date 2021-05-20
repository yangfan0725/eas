package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAttachRentAdjustEntrysInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractAttachRentAdjustEntrysInfo()
    {
        this("id");
    }
    protected AbstractAttachRentAdjustEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������Դ���ⵥ��¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.tenancy.AttachRentAdjustInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.AttachRentAdjustInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.AttachRentAdjustInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ������Դ���ⵥ��¼ 's ������Դ property 
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
     * Object:������Դ���ⵥ��¼'s ���������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getOldRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("oldRentType"));
    }
    public void setOldRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("oldRentType", item.getValue());
		}
    }
    /**
     * Object:������Դ���ⵥ��¼'s ԭ����״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateEnum getOldTenancyState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateEnum.getEnum(getString("oldTenancyState"));
    }
    public void setOldTenancyState(com.kingdee.eas.fdc.tenancy.TenancyStateEnum item)
    {
		if (item != null) {
        setString("oldTenancyState", item.getValue());
		}
    }
    /**
     * Object:������Դ���ⵥ��¼'s ԭ��׼���property 
     */
    public java.math.BigDecimal getOldStandardRent()
    {
        return getBigDecimal("oldStandardRent");
    }
    public void setOldStandardRent(java.math.BigDecimal item)
    {
        setBigDecimal("oldStandardRent", item);
    }
    /**
     * Object:������Դ���ⵥ��¼'s �����������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getNewRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("newRentType"));
    }
    public void setNewRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("newRentType", item.getValue());
		}
    }
    /**
     * Object:������Դ���ⵥ��¼'s ��������״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateEnum getNewTenancyState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateEnum.getEnum(getString("newTenancyState"));
    }
    public void setNewTenancyState(com.kingdee.eas.fdc.tenancy.TenancyStateEnum item)
    {
		if (item != null) {
        setString("newTenancyState", item.getValue());
		}
    }
    /**
     * Object:������Դ���ⵥ��¼'s �����׼���property 
     */
    public java.math.BigDecimal getNewStandardRent()
    {
        return getBigDecimal("newStandardRent");
    }
    public void setNewStandardRent(java.math.BigDecimal item)
    {
        setBigDecimal("newStandardRent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9C40C9D7");
    }
}