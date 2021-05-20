package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRentFreeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRentFreeEntryInfo()
    {
        this("id");
    }
    protected AbstractRentFreeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����¼ 's ���޺�ͬͷ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancy()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancy");
    }
    public void setTenancy(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancy", item);
    }
    /**
     * Object:�����¼'s ���⿪ʼ����property 
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
     * Object:�����¼'s �����������property 
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
     * Object:�����¼'s ��עproperty 
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
     * Object:�����¼'s ��������property 
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
     * Object:�����¼'s �Ƿ�����property 
     */
    public boolean isIsPresent()
    {
        return getBoolean("isPresent");
    }
    public void setIsPresent(boolean item)
    {
        setBoolean("isPresent", item);
    }
    /**
     * Object: �����¼ 's ������������ property 
     */
    public com.kingdee.eas.fdc.tenancy.RentFreeBillInfo getRentFreeBill()
    {
        return (com.kingdee.eas.fdc.tenancy.RentFreeBillInfo)get("rentFreeBill");
    }
    public void setRentFreeBill(com.kingdee.eas.fdc.tenancy.RentFreeBillInfo item)
    {
        put("rentFreeBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7D898324");
    }
}