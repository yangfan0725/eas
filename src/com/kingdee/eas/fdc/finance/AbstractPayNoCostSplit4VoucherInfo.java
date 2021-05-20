package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayNoCostSplit4VoucherInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractPayNoCostSplit4VoucherInfo()
    {
        this("id");
    }
    protected AbstractPayNoCostSplit4VoucherInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ʷ���ȿ� 's ������ͷ property 
     */
    public com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��ʷ���ȿ� 's �����˻� property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getBankAccount()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("bankAccount");
    }
    public void setBankAccount(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("bankAccount", item);
    }
    /**
     * Object: ��ʷ���ȿ� 's �����Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountView()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountView");
    }
    public void setAccountView(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountView", item);
    }
    /**
     * Object: ��ʷ���ȿ� 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object: ��ʷ���ȿ� 's ��� property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillInfo getPaymentBill()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillInfo)get("paymentBill");
    }
    public void setPaymentBill(com.kingdee.eas.fi.cas.PaymentBillInfo item)
    {
        put("paymentBill", item);
    }
    /**
     * Object:��ʷ���ȿ�'s ������property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:��ʷ���ȿ�'s ���ڳɱ����property 
     */
    public java.math.BigDecimal getCostAmount()
    {
        return getBigDecimal("costAmount");
    }
    public void setCostAmount(java.math.BigDecimal item)
    {
        setBigDecimal("costAmount", item);
    }
    /**
     * Object:��ʷ���ȿ�'s ����property 
     */
    public java.math.BigDecimal getExchangeRate()
    {
        return getBigDecimal("exchangeRate");
    }
    public void setExchangeRate(java.math.BigDecimal item)
    {
        setBigDecimal("exchangeRate", item);
    }
    /**
     * Object: ��ʷ���ȿ� 's �������뵥 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillInfo getPayRequestBill()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillInfo)get("payRequestBill");
    }
    public void setPayRequestBill(com.kingdee.eas.fdc.contract.PayRequestBillInfo item)
    {
        put("payRequestBill", item);
    }
    /**
     * Object:��ʷ���ȿ�'s �Ƿ���Ҫ��תproperty 
     */
    public boolean isIsNeedTransit()
    {
        return getBoolean("isNeedTransit");
    }
    public void setIsNeedTransit(boolean item)
    {
        setBoolean("isNeedTransit", item);
    }
    /**
     * Object: ��ʷ���ȿ� 's ��ת��Ŀ property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getTransitAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("transitAccount");
    }
    public void setTransitAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("transitAccount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3F82CC6D");
    }
}