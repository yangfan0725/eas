package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCDepConPayPlanItemInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCDepConPayPlanItemInfo()
    {
        this("id");
    }
    protected AbstractFDCDepConPayPlanItemInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���ź�ͬ����ƻ���Ŀ's �걨����property 
     */
    public java.math.BigDecimal getRequestProp()
    {
        return getBigDecimal("requestProp");
    }
    public void setRequestProp(java.math.BigDecimal item)
    {
        setBigDecimal("requestProp", item);
    }
    /**
     * Object:���ź�ͬ����ƻ���Ŀ's �걨���property 
     */
    public java.math.BigDecimal getRequestAmt()
    {
        return getBigDecimal("requestAmt");
    }
    public void setRequestAmt(java.math.BigDecimal item)
    {
        setBigDecimal("requestAmt", item);
    }
    /**
     * Object:���ź�ͬ����ƻ���Ŀ's ��������property 
     */
    public java.math.BigDecimal getAuditProp()
    {
        return getBigDecimal("auditProp");
    }
    public void setAuditProp(java.math.BigDecimal item)
    {
        setBigDecimal("auditProp", item);
    }
    /**
     * Object:���ź�ͬ����ƻ���Ŀ's �������property 
     */
    public java.math.BigDecimal getAuditAmt()
    {
        return getBigDecimal("auditAmt");
    }
    public void setAuditAmt(java.math.BigDecimal item)
    {
        setBigDecimal("auditAmt", item);
    }
    /**
     * Object: ���ź�ͬ����ƻ���Ŀ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryInfo getEntry()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryInfo)get("entry");
    }
    public void setEntry(com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryInfo item)
    {
        put("entry", item);
    }
    /**
     * Object:���ź�ͬ����ƻ���Ŀ's ���property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:���ź�ͬ����ƻ���Ŀ's �·�property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F28BEC5B");
    }
}