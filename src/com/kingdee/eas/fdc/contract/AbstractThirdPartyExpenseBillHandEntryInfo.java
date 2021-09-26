package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractThirdPartyExpenseBillHandEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractThirdPartyExpenseBillHandEntryInfo()
    {
        this("id");
    }
    protected AbstractThirdPartyExpenseBillHandEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第三方费用申请单分录 's 头 property 
     */
    public com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 第三方费用申请单分录 's 推荐类型 property 
     */
    public com.kingdee.eas.fdc.contract.RecommendTypeInfo getRecommendType()
    {
        return (com.kingdee.eas.fdc.contract.RecommendTypeInfo)get("recommendType");
    }
    public void setRecommendType(com.kingdee.eas.fdc.contract.RecommendTypeInfo item)
    {
        put("recommendType", item);
    }
    /**
     * Object: 第三方费用申请单分录 's 费用科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:第三方费用申请单分录's 情况说明property 
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
     * Object:第三方费用申请单分录's 奖励金额补差property 
     */
    public java.math.BigDecimal getSqjl()
    {
        return getBigDecimal("sqjl");
    }
    public void setSqjl(java.math.BigDecimal item)
    {
        setBigDecimal("sqjl", item);
    }
    /**
     * Object:第三方费用申请单分录's 房间property 
     */
    public String getRoom()
    {
        return getString("room");
    }
    public void setRoom(String item)
    {
        setString("room", item);
    }
    /**
     * Object:第三方费用申请单分录's 客户property 
     */
    public String getCustomer()
    {
        return getString("customer");
    }
    public void setCustomer(String item)
    {
        setString("customer", item);
    }
    /**
     * Object:第三方费用申请单分录's 认购日期property 
     */
    public java.util.Date getPurDate()
    {
        return getDate("purDate");
    }
    public void setPurDate(java.util.Date item)
    {
        setDate("purDate", item);
    }
    /**
     * Object:第三方费用申请单分录's 签约日期property 
     */
    public java.util.Date getSignDate()
    {
        return getDate("signDate");
    }
    public void setSignDate(java.util.Date item)
    {
        setDate("signDate", item);
    }
    /**
     * Object:第三方费用申请单分录's 建筑面积property 
     */
    public java.math.BigDecimal getBuildingArea()
    {
        return getBigDecimal("buildingArea");
    }
    public void setBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildingArea", item);
    }
    /**
     * Object:第三方费用申请单分录's 成交总价property 
     */
    public java.math.BigDecimal getDealTotalAmount()
    {
        return getBigDecimal("dealTotalAmount");
    }
    public void setDealTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("dealTotalAmount", item);
    }
    /**
     * Object:第三方费用申请单分录's 置业顾问property 
     */
    public String getSaleMan()
    {
        return getString("saleMan");
    }
    public void setSaleMan(String item)
    {
        setString("saleMan", item);
    }
    /**
     * Object:第三方费用申请单分录's 推荐人property 
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
     * Object:第三方费用申请单分录's 渠道人员property 
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
     * Object:第三方费用申请单分录's 现置业顾问property 
     */
    public String getNowSaleMan()
    {
        return getString("nowSaleMan");
    }
    public void setNowSaleMan(String item)
    {
        setString("nowSaleMan", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("844B84DE");
    }
}