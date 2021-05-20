package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierChangeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupplierChangeEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierChangeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ӧ�̱����¼'s ����ֵ property 
     */
    public String getPropertyValue()
    {
        return getString("propertyValue");
    }
    public void setPropertyValue(String item)
    {
        setString("propertyValue", item);
    }
    /**
     * Object:��Ӧ�̱����¼'s ��������property 
     */
    public String getPropertyName()
    {
        return getString("propertyName");
    }
    public void setPropertyName(String item)
    {
        setString("propertyName", item);
    }
    /**
     * Object: ��Ӧ�̱����¼ 's ��������� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4A2233FE");
    }
}