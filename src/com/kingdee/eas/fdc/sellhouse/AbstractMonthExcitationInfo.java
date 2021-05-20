package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonthExcitationInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMonthExcitationInfo()
    {
        this("id");
    }
    protected AbstractMonthExcitationInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.sellhouse.MonthExcitationEntryCollection());
    }
    /**
     * Object: �¶����ۼ���������ϸ�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.MonthExcitationEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.MonthExcitationEntryCollection)get("entry");
    }
    /**
     * Object:�¶����ۼ���������ϸ��'s ���˰��ָͬ��property 
     */
    public java.math.BigDecimal getTarget()
    {
        return getBigDecimal("target");
    }
    public void setTarget(java.math.BigDecimal item)
    {
        setBigDecimal("target", item);
    }
    /**
     * Object:�¶����ۼ���������ϸ��'s ���ۼ������ֵproperty 
     */
    public java.math.BigDecimal getMax()
    {
        return getBigDecimal("max");
    }
    public void setMax(java.math.BigDecimal item)
    {
        setBigDecimal("max", item);
    }
    /**
     * Object:�¶����ۼ���������ϸ��'s ���ۼ���ռ��property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object: �¶����ۼ���������ϸ�� 's ��Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object:�¶����ۼ���������ϸ��'s ��property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object:�¶����ۼ���������ϸ��'s ���property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("849F3AFF");
    }
}