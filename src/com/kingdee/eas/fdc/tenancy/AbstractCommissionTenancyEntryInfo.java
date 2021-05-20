package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCommissionTenancyEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCommissionTenancyEntryInfo()
    {
        this("id");
    }
    protected AbstractCommissionTenancyEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 佣金结算审批单租赁分录 's 租赁合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyBill");
    }
    public void setTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyBill", item);
    }
    /**
     * Object:佣金结算审批单租赁分录's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:佣金结算审批单租赁分录's 提成基数property 
     */
    public java.math.BigDecimal getBaseAmount()
    {
        return getBigDecimal("baseAmount");
    }
    public void setBaseAmount(java.math.BigDecimal item)
    {
        setBigDecimal("baseAmount", item);
    }
    /**
     * Object:佣金结算审批单租赁分录's 类型property 
     */
    public int getType()
    {
        return getInt("type");
    }
    public void setType(int item)
    {
        setInt("type", item);
    }
    /**
     * Object:佣金结算审批单租赁分录's 已收租金property 
     */
    public java.math.BigDecimal getRevRent()
    {
        return getBigDecimal("revRent");
    }
    public void setRevRent(java.math.BigDecimal item)
    {
        setBigDecimal("revRent", item);
    }
    /**
     * Object: 佣金结算审批单租赁分录 's 收款明细 property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo getRevEntry()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo)get("revEntry");
    }
    public void setRevEntry(com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo item)
    {
        put("revEntry", item);
    }
    /**
     * Object:佣金结算审批单租赁分录's 已收取保证金property 
     */
    public java.math.BigDecimal getRevBZRent()
    {
        return getBigDecimal("revBZRent");
    }
    public void setRevBZRent(java.math.BigDecimal item)
    {
        setBigDecimal("revBZRent", item);
    }
    /**
     * Object: 佣金结算审批单租赁分录 's 头 property 
     */
    public com.kingdee.eas.fdc.tenancy.CommissionSettlementBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.CommissionSettlementBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.CommissionSettlementBillInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C046B2C6");
    }
}