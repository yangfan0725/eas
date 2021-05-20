package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOldRentFreeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractOldRentFreeEntryInfo()
    {
        this("id");
    }
    protected AbstractOldRentFreeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��ͬ�����¼'s ���⿪ʼ����property 
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
     * Object:��ͬ�����¼'s �����������property 
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
     * Object:��ͬ�����¼'s ��������property 
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
     * Object:��ͬ�����¼'s ��עproperty 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: ��ͬ�����¼ 's ��ͬ���ͷ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyModificationInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyModificationInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.TenancyModificationInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7708B2EF");
    }
}