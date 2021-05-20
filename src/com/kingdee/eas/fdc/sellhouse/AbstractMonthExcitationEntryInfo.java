package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonthExcitationEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMonthExcitationEntryInfo()
    {
        this("id");
    }
    protected AbstractMonthExcitationEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 月度销售激励测算明细表分录 's 父亲 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MonthExcitationInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.MonthExcitationInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.MonthExcitationInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 岗位property 
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
     * Object: 月度销售激励测算明细表分录 's 姓名 property 
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
     * Object:月度销售激励测算明细表分录's 销售激励汇总property 
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
     * Object:月度销售激励测算明细表分录's 备注property 
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
     * Object:月度销售激励测算明细表分录's 客户拓展奖罚property 
     */
    public java.math.BigDecimal getAmount1()
    {
        return getBigDecimal("amount1");
    }
    public void setAmount1(java.math.BigDecimal item)
    {
        setBigDecimal("amount1", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 奖罚说明property 
     */
    public String getExplain1()
    {
        return getString("explain1");
    }
    public void setExplain1(String item)
    {
        setString("explain1", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 成交奖罚property 
     */
    public java.math.BigDecimal getAmount2()
    {
        return getBigDecimal("amount2");
    }
    public void setAmount2(java.math.BigDecimal item)
    {
        setBigDecimal("amount2", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 奖罚说明property 
     */
    public String getExplain2()
    {
        return getString("explain2");
    }
    public void setExplain2(String item)
    {
        setString("explain2", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 销售排名奖罚property 
     */
    public java.math.BigDecimal getAmount3()
    {
        return getBigDecimal("amount3");
    }
    public void setAmount3(java.math.BigDecimal item)
    {
        setBigDecimal("amount3", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 奖罚说明property 
     */
    public String getExplain3()
    {
        return getString("explain3");
    }
    public void setExplain3(String item)
    {
        setString("explain3", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 签约奖罚property 
     */
    public java.math.BigDecimal getAmount4()
    {
        return getBigDecimal("amount4");
    }
    public void setAmount4(java.math.BigDecimal item)
    {
        setBigDecimal("amount4", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 奖罚说明property 
     */
    public String getExplain4()
    {
        return getString("explain4");
    }
    public void setExplain4(String item)
    {
        setString("explain4", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 回款奖罚property 
     */
    public java.math.BigDecimal getAmount5()
    {
        return getBigDecimal("amount5");
    }
    public void setAmount5(java.math.BigDecimal item)
    {
        setBigDecimal("amount5", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 奖罚说明property 
     */
    public String getExplain5()
    {
        return getString("explain5");
    }
    public void setExplain5(String item)
    {
        setString("explain5", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 规范性奖罚property 
     */
    public java.math.BigDecimal getAmount6()
    {
        return getBigDecimal("amount6");
    }
    public void setAmount6(java.math.BigDecimal item)
    {
        setBigDecimal("amount6", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 奖罚说明property 
     */
    public String getExplain6()
    {
        return getString("explain6");
    }
    public void setExplain6(String item)
    {
        setString("explain6", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 指标奖罚property 
     */
    public java.math.BigDecimal getAmount7()
    {
        return getBigDecimal("amount7");
    }
    public void setAmount7(java.math.BigDecimal item)
    {
        setBigDecimal("amount7", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 奖罚说明property 
     */
    public String getExplain7()
    {
        return getString("explain7");
    }
    public void setExplain7(String item)
    {
        setString("explain7", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 单项奖罚property 
     */
    public java.math.BigDecimal getAmount8()
    {
        return getBigDecimal("amount8");
    }
    public void setAmount8(java.math.BigDecimal item)
    {
        setBigDecimal("amount8", item);
    }
    /**
     * Object:月度销售激励测算明细表分录's 奖罚说明property 
     */
    public String getExplain8()
    {
        return getString("explain8");
    }
    public void setExplain8(String item)
    {
        setString("explain8", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("36F8E4B3");
    }
}