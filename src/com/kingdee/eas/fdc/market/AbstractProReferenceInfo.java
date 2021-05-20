package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProReferenceInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProReferenceInfo()
    {
        this("id");
    }
    protected AbstractProReferenceInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ŀ����ο���¼'s ���property 
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
     * Object:��Ŀ����ο���¼'s ��Ȱ�����ֵproperty 
     */
    public java.math.BigDecimal getYearValue()
    {
        return getBigDecimal("yearValue");
    }
    public void setYearValue(java.math.BigDecimal item)
    {
        setBigDecimal("yearValue", item);
    }
    /**
     * Object:��Ŀ����ο���¼'s Ŀ���ܻ�ֵproperty 
     */
    public java.math.BigDecimal getTargetValue()
    {
        return getBigDecimal("targetValue");
    }
    public void setTargetValue(java.math.BigDecimal item)
    {
        setBigDecimal("targetValue", item);
    }
    /**
     * Object:��Ŀ����ο���¼'s �ƻ���property 
     */
    public java.math.BigDecimal getPlanAmount()
    {
        return getBigDecimal("planAmount");
    }
    public void setPlanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("planAmount", item);
    }
    /**
     * Object:��Ŀ����ο���¼'s ʵ�ʷ�����property 
     */
    public java.math.BigDecimal getActulAmount()
    {
        return getBigDecimal("actulAmount");
    }
    public void setActulAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actulAmount", item);
    }
    /**
     * Object: ��Ŀ����ο���¼ 's  property 
     */
    public com.kingdee.eas.fdc.market.MonthPlanInfo getHead()
    {
        return (com.kingdee.eas.fdc.market.MonthPlanInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.market.MonthPlanInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("472F936F");
    }
}