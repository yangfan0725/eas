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
     * Object: 月度销售激励测算明细表 's 分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MonthExcitationEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.MonthExcitationEntryCollection)get("entry");
    }
    /**
     * Object:月度销售激励测算明细表's 考核版合同指标property 
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
     * Object:月度销售激励测算明细表's 销售激励最高值property 
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
     * Object:月度销售激励测算明细表's 销售激励占比property 
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
     * Object: 月度销售激励测算明细表 's 项目 property 
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
     * Object:月度销售激励测算明细表's 月property 
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
     * Object:月度销售激励测算明细表's 年度property 
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