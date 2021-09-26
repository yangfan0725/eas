package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayRequestBillEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractPayRequestBillEntryInfo()
    {
        this("id");
    }
    protected AbstractPayRequestBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.PayRequestBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 付款次数property 
     */
    public int getPayTimes()
    {
        return getInt("payTimes");
    }
    public void setPayTimes(int item)
    {
        setInt("payTimes", item);
    }
    /**
     * Object: 分录 's 付款单 property 
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
     * Object:分录's 付款金额property 
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
     * Object:分录's 增加工程款property 
     */
    public java.math.BigDecimal getAddProjectAmt()
    {
        return getBigDecimal("addProjectAmt");
    }
    public void setAddProjectAmt(java.math.BigDecimal item)
    {
        setBigDecimal("addProjectAmt", item);
    }
    /**
     * Object:分录's 应扣甲供材料款property 
     */
    public java.math.BigDecimal getPayPartAMatlAmt()
    {
        return getBigDecimal("payPartAMatlAmt");
    }
    public void setPayPartAMatlAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payPartAMatlAmt", item);
    }
    /**
     * Object:分录's 合同内工程款property 
     */
    public java.math.BigDecimal getProjectPriceInContract()
    {
        return getBigDecimal("projectPriceInContract");
    }
    public void setProjectPriceInContract(java.math.BigDecimal item)
    {
        setBigDecimal("projectPriceInContract", item);
    }
    /**
     * Object:分录's 原币金额property 
     */
    public java.math.BigDecimal getOriginalAmount()
    {
        return getBigDecimal("originalAmount");
    }
    public void setOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("originalAmount", item);
    }
    /**
     * Object:分录's 预付款原币property 
     */
    public java.math.BigDecimal getAdvance()
    {
        return getBigDecimal("advance");
    }
    public void setAdvance(java.math.BigDecimal item)
    {
        setBigDecimal("advance", item);
    }
    /**
     * Object:分录's 预付款本币property 
     */
    public java.math.BigDecimal getLocAdvance()
    {
        return getBigDecimal("locAdvance");
    }
    public void setLocAdvance(java.math.BigDecimal item)
    {
        setBigDecimal("locAdvance", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DAD04989");
    }
}