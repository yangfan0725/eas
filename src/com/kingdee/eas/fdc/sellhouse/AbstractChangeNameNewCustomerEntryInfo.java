package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeNameNewCustomerEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractChangeNameNewCustomerEntryInfo()
    {
        this("id");
    }
    protected AbstractChangeNameNewCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���������¿ͻ���¼ 's �����¼��¼ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ���������¿ͻ���¼ 's ���ز��ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("customer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7920110A");
    }
}