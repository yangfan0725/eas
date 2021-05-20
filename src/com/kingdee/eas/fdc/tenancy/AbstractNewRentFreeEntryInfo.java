package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewRentFreeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractNewRentFreeEntryInfo()
    {
        this("id");
    }
    protected AbstractNewRentFreeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 新免租分录 's 免租分录的头合同变更 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyModificationInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyModificationInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.TenancyModificationInfo item)
    {
        put("head", item);
    }
    /**
     * Object:新免租分录's 免租开始日期property 
     */
    public java.util.Date getFreeStartDate()
    {
        return getDate("freeStartDate");
    }
    public void setFreeStartDate(java.util.Date item)
    {
        setDate("freeStartDate", item);
    }
    /**
     * Object:新免租分录's 免租结束日期property 
     */
    public java.util.Date getFreeEndDate()
    {
        return getDate("freeEndDate");
    }
    public void setFreeEndDate(java.util.Date item)
    {
        setDate("freeEndDate", item);
    }
    /**
     * Object:新免租分录's 免租类型property 
     */
    public com.kingdee.eas.fdc.tenancy.FreeTenancyTypeEnum getFreeTenancyType()
    {
        return com.kingdee.eas.fdc.tenancy.FreeTenancyTypeEnum.getEnum(getString("freeTenancyType"));
    }
    public void setFreeTenancyType(com.kingdee.eas.fdc.tenancy.FreeTenancyTypeEnum item)
    {
		if (item != null) {
        setString("freeTenancyType", item.getValue());
		}
    }
    /**
     * Object:新免租分录's 备注property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E3ECE416");
    }
}