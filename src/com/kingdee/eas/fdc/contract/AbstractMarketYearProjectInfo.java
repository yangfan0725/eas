package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketYearProjectInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMarketYearProjectInfo()
    {
        this("id");
    }
    protected AbstractMarketYearProjectInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.MarketYearProjectEntryCollection());
    }
    /**
     * Object: 营销年度预算 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.MarketYearProjectEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.MarketYearProjectEntryCollection)get("entry");
    }
    /**
     * Object:营销年度预算's 年度property 
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
     * Object:营销年度预算's 版本号property 
     */
    public int getVersion()
    {
        return getInt("version");
    }
    public void setVersion(int item)
    {
        setInt("version", item);
    }
    /**
     * Object:营销年度预算's 是否最新property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    /**
     * Object:营销年度预算's 年度总额property 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    /**
     * Object:营销年度预算's 上一版本年度总额property 
     */
    public java.math.BigDecimal getLastTotalAmount()
    {
        return getBigDecimal("lastTotalAmount");
    }
    public void setLastTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("lastTotalAmount", item);
    }
    /**
     * Object:营销年度预算's 年度签约指标（万）property 
     */
    public java.math.BigDecimal getSign()
    {
        return getBigDecimal("sign");
    }
    public void setSign(java.math.BigDecimal item)
    {
        setBigDecimal("sign", item);
    }
    /**
     * Object:营销年度预算's 年度营销费率（全科目）%property 
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
     * Object:营销年度预算's 年度营销费用预算（考核科目）万property 
     */
    public java.math.BigDecimal getYearAmount()
    {
        return getBigDecimal("yearAmount");
    }
    public void setYearAmount(java.math.BigDecimal item)
    {
        setBigDecimal("yearAmount", item);
    }
    /**
     * Object:营销年度预算's 年度营销费率（考核科目）%property 
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
     * Object: 营销年度预算 's 营销项目 property 
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
     * Object:营销年度预算's 项目名称property 
     */
    public String getSellProjecttxt()
    {
        return getString("sellProjecttxt");
    }
    public void setSellProjecttxt(String item)
    {
        setString("sellProjecttxt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("12113D65");
    }
}