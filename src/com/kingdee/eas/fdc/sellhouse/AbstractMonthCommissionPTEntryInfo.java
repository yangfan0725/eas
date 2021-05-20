package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonthCommissionPTEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMonthCommissionPTEntryInfo()
    {
        this("id");
    }
    protected AbstractMonthCommissionPTEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �¶�Ӫ�����������ϸ���Ʒ���ͷ�¼ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MonthCommissionEntryInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.MonthCommissionEntryInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.MonthCommissionEntryInfo item)
    {
        put("head", item);
    }
    /**
     * Object: �¶�Ӫ�����������ϸ���Ʒ���ͷ�¼ 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object:�¶�Ӫ�����������ϸ���Ʒ���ͷ�¼'s �����ɵ���property 
     */
    public java.math.BigDecimal getYearRate()
    {
        return getBigDecimal("yearRate");
    }
    public void setYearRate(java.math.BigDecimal item)
    {
        setBigDecimal("yearRate", item);
    }
    /**
     * Object:�¶�Ӫ�����������ϸ���Ʒ���ͷ�¼'s �¶���ɵ���property 
     */
    public java.math.BigDecimal getMonthRate()
    {
        return getBigDecimal("monthRate");
    }
    public void setMonthRate(java.math.BigDecimal item)
    {
        setBigDecimal("monthRate", item);
    }
    /**
     * Object:�¶�Ӫ�����������ϸ���Ʒ���ͷ�¼'s �ά����1property 
     */
    public java.math.BigDecimal getSum1()
    {
        return getBigDecimal("sum1");
    }
    public void setSum1(java.math.BigDecimal item)
    {
        setBigDecimal("sum1", item);
    }
    /**
     * Object:�¶�Ӫ�����������ϸ���Ʒ���ͷ�¼'s �ά����1property 
     */
    public java.math.BigDecimal getSumRate1()
    {
        return getBigDecimal("sumRate1");
    }
    public void setSumRate1(java.math.BigDecimal item)
    {
        setBigDecimal("sumRate1", item);
    }
    /**
     * Object:�¶�Ӫ�����������ϸ���Ʒ���ͷ�¼'s �ά����2property 
     */
    public java.math.BigDecimal getSum2()
    {
        return getBigDecimal("sum2");
    }
    public void setSum2(java.math.BigDecimal item)
    {
        setBigDecimal("sum2", item);
    }
    /**
     * Object:�¶�Ӫ�����������ϸ���Ʒ���ͷ�¼'s �ά����2property 
     */
    public java.math.BigDecimal getSumRate2()
    {
        return getBigDecimal("sumRate2");
    }
    public void setSumRate2(java.math.BigDecimal item)
    {
        setBigDecimal("sumRate2", item);
    }
    /**
     * Object:�¶�Ӫ�����������ϸ���Ʒ���ͷ�¼'s ��Ʒ1property 
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
     * Object:�¶�Ӫ�����������ϸ���Ʒ���ͷ�¼'s �ά���property 
     */
    public java.math.BigDecimal getAmountRate()
    {
        return getBigDecimal("amountRate");
    }
    public void setAmountRate(java.math.BigDecimal item)
    {
        setBigDecimal("amountRate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A3CD537E");
    }
}