package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketingCommissionEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketingCommissionEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketingCommissionEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 员工 property 
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
     * Object: 分录 's 佣金结算单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 合同金额property 
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
     * Object:分录's 合同目标金额property 
     */
    public java.math.BigDecimal getContractAmtTarget()
    {
        return getBigDecimal("contractAmtTarget");
    }
    public void setContractAmtTarget(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmtTarget", item);
    }
    /**
     * Object:分录's 回款金额property 
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
     * Object:分录's 回款目标金额property 
     */
    public java.math.BigDecimal getBackAmtTarget()
    {
        return getBigDecimal("backAmtTarget");
    }
    public void setBackAmtTarget(java.math.BigDecimal item)
    {
        setBigDecimal("backAmtTarget", item);
    }
    /**
     * Object:分录's 合同完成比率property 
     */
    public java.math.BigDecimal getContractCompleteRate()
    {
        return getBigDecimal("contractCompleteRate");
    }
    public void setContractCompleteRate(java.math.BigDecimal item)
    {
        setBigDecimal("contractCompleteRate", item);
    }
    /**
     * Object:分录's 回款完成比例property 
     */
    public java.math.BigDecimal getBackCompleteRate()
    {
        return getBigDecimal("backCompleteRate");
    }
    public void setBackCompleteRate(java.math.BigDecimal item)
    {
        setBigDecimal("backCompleteRate", item);
    }
    /**
     * Object:分录's 产品类型property 
     */
    public String getProductType()
    {
        return getString("productType");
    }
    public void setProductType(String item)
    {
        setString("productType", item);
    }
    /**
     * Object:分录's 提奖基数property 
     */
    public java.math.BigDecimal getCalcBonusAmt()
    {
        return getBigDecimal("calcBonusAmt");
    }
    public void setCalcBonusAmt(java.math.BigDecimal item)
    {
        setBigDecimal("calcBonusAmt", item);
    }
    /**
     * Object:分录's 提成比例property 
     */
    public java.math.BigDecimal getCalcBonusRate()
    {
        return getBigDecimal("calcBonusRate");
    }
    public void setCalcBonusRate(java.math.BigDecimal item)
    {
        setBigDecimal("calcBonusRate", item);
    }
    /**
     * Object:分录's 奖金property 
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
     * Object:分录's 预留金额property 
     */
    public java.math.BigDecimal getKeepAmt()
    {
        return getBigDecimal("keepAmt");
    }
    public void setKeepAmt(java.math.BigDecimal item)
    {
        setBigDecimal("keepAmt", item);
    }
    /**
     * Object:分录's 调整金额property 
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
     * Object:分录's 实际发放奖金数property 
     */
    public java.math.BigDecimal getRealBonusAmt()
    {
        return getBigDecimal("realBonusAmt");
    }
    public void setRealBonusAmt(java.math.BigDecimal item)
    {
        setBigDecimal("realBonusAmt", item);
    }
    /**
     * Object:分录's 描述property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object:分录's 岗位property 
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
     * Object:分录's bonusTypeproperty 
     */
    public int getBonusType()
    {
        return getInt("bonusType");
    }
    public void setBonusType(int item)
    {
        setInt("bonusType", item);
    }
    /**
     * Object:分录's 月度认购招标（元）property 
     */
    public java.math.BigDecimal getMpur()
    {
        return getBigDecimal("mpur");
    }
    public void setMpur(java.math.BigDecimal item)
    {
        setBigDecimal("mpur", item);
    }
    /**
     * Object:分录's 月度合同招标（元）property 
     */
    public java.math.BigDecimal getMcontract()
    {
        return getBigDecimal("mcontract");
    }
    public void setMcontract(java.math.BigDecimal item)
    {
        setBigDecimal("mcontract", item);
    }
    /**
     * Object:分录's 完成认购额（元）property 
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
     * Object:分录's 完成合同额（元）property 
     */
    public java.math.BigDecimal getContract()
    {
        return getBigDecimal("contract");
    }
    public void setContract(java.math.BigDecimal item)
    {
        setBigDecimal("contract", item);
    }
    /**
     * Object:分录's 认购招标完成率（%）property 
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
     * Object:分录's 合同招标完成率（%）property 
     */
    public java.math.BigDecimal getContractRate()
    {
        return getBigDecimal("contractRate");
    }
    public void setContractRate(java.math.BigDecimal item)
    {
        setBigDecimal("contractRate", item);
    }
    /**
     * Object:分录's 提奖基数property 
     */
    public java.math.BigDecimal getCalcTBonus()
    {
        return getBigDecimal("calcTBonus");
    }
    public void setCalcTBonus(java.math.BigDecimal item)
    {
        setBigDecimal("calcTBonus", item);
    }
    /**
     * Object:分录's 提成比例property 
     */
    public java.math.BigDecimal getCalcTBonusRate()
    {
        return getBigDecimal("calcTBonusRate");
    }
    public void setCalcTBonusRate(java.math.BigDecimal item)
    {
        setBigDecimal("calcTBonusRate", item);
    }
    /**
     * Object:分录's 个人提成修正property 
     */
    public java.math.BigDecimal getXz()
    {
        return getBigDecimal("xz");
    }
    public void setXz(java.math.BigDecimal item)
    {
        setBigDecimal("xz", item);
    }
    /**
     * Object:分录's 预留比例property 
     */
    public java.math.BigDecimal getKeepRate()
    {
        return getBigDecimal("keepRate");
    }
    public void setKeepRate(java.math.BigDecimal item)
    {
        setBigDecimal("keepRate", item);
    }
    /**
     * Object:分录's 指标综合完成率property 
     */
    public java.math.BigDecimal getZbRate()
    {
        return getBigDecimal("zbRate");
    }
    public void setZbRate(java.math.BigDecimal item)
    {
        setBigDecimal("zbRate", item);
    }
    /**
     * Object:分录's 渠道人员property 
     */
    public String getQdPerson()
    {
        return getString("qdPerson");
    }
    public void setQdPerson(String item)
    {
        setString("qdPerson", item);
    }
    /**
     * Object:分录's 推荐人property 
     */
    public String getRecommended()
    {
        return getString("recommended");
    }
    public void setRecommended(String item)
    {
        setString("recommended", item);
    }
    /**
     * Object:分录's 完成认购额property 
     */
    public java.math.BigDecimal getPurAmt()
    {
        return getBigDecimal("purAmt");
    }
    public void setPurAmt(java.math.BigDecimal item)
    {
        setBigDecimal("purAmt", item);
    }
    /**
     * Object:分录's 月度认购指标property 
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
     * Object:分录's 认购指标完成率property 
     */
    public java.math.BigDecimal getPurComplateRate()
    {
        return getBigDecimal("purComplateRate");
    }
    public void setPurComplateRate(java.math.BigDecimal item)
    {
        setBigDecimal("purComplateRate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FA10D91C");
    }
}