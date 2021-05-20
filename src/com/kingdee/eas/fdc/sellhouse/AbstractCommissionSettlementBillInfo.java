package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCommissionSettlementBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractCommissionSettlementBillInfo()
    {
        this("id");
    }
    protected AbstractCommissionSettlementBillInfo(String pkField)
    {
        super(pkField);
        put("empBonusEntry", new com.kingdee.eas.fdc.sellhouse.MarketingCommissionEntryCollection());
        put("agencyEntry", new com.kingdee.eas.fdc.sellhouse.AgencyCommissionEntryCollection());
    }
    /**
     * Object:佣金结算审批单's 月度property 
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
     * Object: 佣金结算审批单 's 分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MarketingCommissionEntryCollection getEmpBonusEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.MarketingCommissionEntryCollection)get("empBonusEntry");
    }
    /**
     * Object: 佣金结算审批单 's 分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.AgencyCommissionEntryCollection getAgencyEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.AgencyCommissionEntryCollection)get("agencyEntry");
    }
    /**
     * Object:佣金结算审批单's 销售团队月度佣金property 
     */
    public java.math.BigDecimal getBonus()
    {
        return getBigDecimal("bonus");
    }
    public void setBonus(java.math.BigDecimal item)
    {
        setBigDecimal("bonus", item);
    }
    /**
     * Object:佣金结算审批单's 销售团队月度销售激励property 
     */
    public java.math.BigDecimal getAdjustAmt()
    {
        return getBigDecimal("adjustAmt");
    }
    public void setAdjustAmt(java.math.BigDecimal item)
    {
        setBigDecimal("adjustAmt", item);
    }
    /**
     * Object:佣金结算审批单's 月度销售合同金额property 
     */
    public java.math.BigDecimal getContractAmt()
    {
        return getBigDecimal("contractAmt");
    }
    public void setContractAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmt", item);
    }
    /**
     * Object:佣金结算审批单's 月度销售回笼金额property 
     */
    public java.math.BigDecimal getBackAmt()
    {
        return getBigDecimal("backAmt");
    }
    public void setBackAmt(java.math.BigDecimal item)
    {
        setBigDecimal("backAmt", item);
    }
    /**
     * Object:佣金结算审批单's 月度结算薪酬总金额property 
     */
    public java.math.BigDecimal getSalary()
    {
        return getBigDecimal("salary");
    }
    public void setSalary(java.math.BigDecimal item)
    {
        setBigDecimal("salary", item);
    }
    /**
     * Object:佣金结算审批单's 月度薪酬营销费用占比property 
     */
    public java.math.BigDecimal getPercent()
    {
        return getBigDecimal("percent");
    }
    public void setPercent(java.math.BigDecimal item)
    {
        setBigDecimal("percent", item);
    }
    /**
     * Object:佣金结算审批单's 年度property 
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
     * Object:佣金结算审批单's 基础底薪property 
     */
    public java.math.BigDecimal getBaseSalary()
    {
        return getBigDecimal("baseSalary");
    }
    public void setBaseSalary(java.math.BigDecimal item)
    {
        setBigDecimal("baseSalary", item);
    }
    /**
     * Object: 佣金结算审批单 's 关联项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F3927C76");
    }
}