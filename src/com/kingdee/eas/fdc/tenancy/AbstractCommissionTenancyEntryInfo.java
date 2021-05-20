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
     * Object: Ӷ��������������޷�¼ 's ���޺�ͬ property 
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
     * Object:Ӷ��������������޷�¼'s ��עproperty 
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
     * Object:Ӷ��������������޷�¼'s ��ɻ���property 
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
     * Object:Ӷ��������������޷�¼'s ����property 
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
     * Object:Ӷ��������������޷�¼'s �������property 
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
     * Object: Ӷ��������������޷�¼ 's �տ���ϸ property 
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
     * Object:Ӷ��������������޷�¼'s ����ȡ��֤��property 
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
     * Object: Ӷ��������������޷�¼ 's ͷ property 
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