package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonthCommissionEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMonthCommissionEntryInfo()
    {
        this("id");
    }
    protected AbstractMonthCommissionEntryInfo(String pkField)
    {
        super(pkField);
        put("ptEntry", new com.kingdee.eas.fdc.sellhouse.MonthCommissionPTEntryCollection());
    }
    /**
     * Object: 月度营销提成测算明细表分录 's 父亲 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MonthCommissionInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.MonthCommissionInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.MonthCommissionInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 月度营销提成测算明细表分录 's 产品类型分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MonthCommissionPTEntryCollection getPtEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.MonthCommissionPTEntryCollection)get("ptEntry");
    }
    /**
     * Object: 月度营销提成测算明细表分录 's 姓名 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    /**
     * Object:月度营销提成测算明细表分录's 岗位property 
     */
    public String getPosition()
    {
        return getString("position");
    }
    public void setPosition(String item)
    {
        setString("position", item);
    }
    /**
     * Object:月度营销提成测算明细表分录's 月度认购指标property 
     */
    public java.math.BigDecimal getPurTarget()
    {
        return getBigDecimal("purTarget");
    }
    public void setPurTarget(java.math.BigDecimal item)
    {
        setBigDecimal("purTarget", item);
    }
    /**
     * Object:月度营销提成测算明细表分录's 月度合同指标property 
     */
    public java.math.BigDecimal getSignTarget()
    {
        return getBigDecimal("signTarget");
    }
    public void setSignTarget(java.math.BigDecimal item)
    {
        setBigDecimal("signTarget", item);
    }
    /**
     * Object:月度营销提成测算明细表分录's 月度回款指标property 
     */
    public java.math.BigDecimal getBackTarget()
    {
        return getBigDecimal("backTarget");
    }
    public void setBackTarget(java.math.BigDecimal item)
    {
        setBigDecimal("backTarget", item);
    }
    /**
     * Object:月度营销提成测算明细表分录's 完成认购额property 
     */
    public java.math.BigDecimal getPur()
    {
        return getBigDecimal("pur");
    }
    public void setPur(java.math.BigDecimal item)
    {
        setBigDecimal("pur", item);
    }
    /**
     * Object:月度营销提成测算明细表分录's 完成合同额property 
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
     * Object:月度营销提成测算明细表分录's 完成回款额property 
     */
    public java.math.BigDecimal getBack()
    {
        return getBigDecimal("back");
    }
    public void setBack(java.math.BigDecimal item)
    {
        setBigDecimal("back", item);
    }
    /**
     * Object:月度营销提成测算明细表分录's 认购指标完成率property 
     */
    public java.math.BigDecimal getPurRate()
    {
        return getBigDecimal("purRate");
    }
    public void setPurRate(java.math.BigDecimal item)
    {
        setBigDecimal("purRate", item);
    }
    /**
     * Object:月度营销提成测算明细表分录's 合同指标完成率property 
     */
    public java.math.BigDecimal getSignRate()
    {
        return getBigDecimal("signRate");
    }
    public void setSignRate(java.math.BigDecimal item)
    {
        setBigDecimal("signRate", item);
    }
    /**
     * Object:月度营销提成测算明细表分录's 回款指标完成率property 
     */
    public java.math.BigDecimal getBackRate()
    {
        return getBigDecimal("backRate");
    }
    public void setBackRate(java.math.BigDecimal item)
    {
        setBigDecimal("backRate", item);
    }
    /**
     * Object:月度营销提成测算明细表分录's 指标综合完成率property 
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
     * Object:月度营销提成测算明细表分录's 个人提成合计（按金额）property 
     */
    public java.math.BigDecimal getSumTotal()
    {
        return getBigDecimal("sumTotal");
    }
    public void setSumTotal(java.math.BigDecimal item)
    {
        setBigDecimal("sumTotal", item);
    }
    /**
     * Object:月度营销提成测算明细表分录's 个人提成合计（按数量）property 
     */
    public java.math.BigDecimal getAmountTotal()
    {
        return getBigDecimal("amountTotal");
    }
    public void setAmountTotal(java.math.BigDecimal item)
    {
        setBigDecimal("amountTotal", item);
    }
    /**
     * Object:月度营销提成测算明细表分录's 合计佣金property 
     */
    public java.math.BigDecimal getTotal()
    {
        return getBigDecimal("total");
    }
    public void setTotal(java.math.BigDecimal item)
    {
        setBigDecimal("total", item);
    }
    /**
     * Object:月度营销提成测算明细表分录's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("356CA702");
    }
}