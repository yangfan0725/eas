package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonthCommissionInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMonthCommissionInfo()
    {
        this("id");
    }
    protected AbstractMonthCommissionInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.sellhouse.MonthCommissionEntryCollection());
    }
    /**
     * Object: 月度营销提成测算明细表 's 项目 property 
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
     * Object: 月度营销提成测算明细表 's 分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MonthCommissionEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.MonthCommissionEntryCollection)get("entry");
    }
    /**
     * Object:月度营销提成测算明细表's 月度property 
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
     * Object:月度营销提成测算明细表's 年度property 
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
        return new BOSObjectType("36410350");
    }
}