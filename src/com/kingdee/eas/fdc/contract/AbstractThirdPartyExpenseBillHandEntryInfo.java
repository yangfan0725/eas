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
     * Object: �������������뵥��¼ 's ͷ property 
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
     * Object: �������������뵥��¼ 's �Ƽ����� property 
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
     * Object: �������������뵥��¼ 's ���ÿ�Ŀ property 
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
     * Object:�������������뵥��¼'s ���˵��property 
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
     * Object:�������������뵥��¼'s ��������property 
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
     * Object:�������������뵥��¼'s ����property 
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
     * Object:�������������뵥��¼'s �ͻ�property 
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
     * Object:�������������뵥��¼'s �Ϲ�����property 
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
     * Object:�������������뵥��¼'s ǩԼ����property 
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
     * Object:�������������뵥��¼'s �������property 
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
     * Object:�������������뵥��¼'s �ɽ��ܼ�property 
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
     * Object:�������������뵥��¼'s ��ҵ����property 
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
     * Object:�������������뵥��¼'s �Ƽ���property 
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
     * Object:�������������뵥��¼'s ������Աproperty 
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
     * Object:�������������뵥��¼'s ����ҵ����property 
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