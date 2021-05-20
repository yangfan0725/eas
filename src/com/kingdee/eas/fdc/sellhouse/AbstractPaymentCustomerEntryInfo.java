package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPaymentCustomerEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPaymentCustomerEntryInfo()
    {
        this("id");
    }
    protected AbstractPaymentCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ſ���ϸ�ͻ���¼ 's �ſ���ϸͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BankPaymentEntryInfo getPaymentEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.BankPaymentEntryInfo)get("paymentEntry");
    }
    public void setPaymentEntry(com.kingdee.eas.fdc.sellhouse.BankPaymentEntryInfo item)
    {
        put("paymentEntry", item);
    }
    /**
     * Object: �ſ���ϸ�ͻ���¼ 's �ͻ� property 
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
        return new BOSObjectType("ECA93629");
    }
}