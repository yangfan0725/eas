package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractThirdPartyExpenseBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractThirdPartyExpenseBillEntryInfo()
    {
        this("id");
    }
    protected AbstractThirdPartyExpenseBillEntryInfo(String pkField)
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
     * Object:�������������뵥��¼'s ǩԼIdproperty 
     */
    public String getSignManageId()
    {
        return getString("signManageId");
    }
    public void setSignManageId(String item)
    {
        setString("signManageId", item);
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
     * Object:�������������뵥��¼'s ���᷽ʽproperty 
     */
    public com.kingdee.eas.fdc.contract.JTTypeEnum getJtType()
    {
        return com.kingdee.eas.fdc.contract.JTTypeEnum.getEnum(getString("jtType"));
    }
    public void setJtType(com.kingdee.eas.fdc.contract.JTTypeEnum item)
    {
		if (item != null) {
        setString("jtType", item.getValue());
		}
    }
    /**
     * Object:�������������뵥��¼'s ����������ϵ��property 
     */
    public java.math.BigDecimal getDsxs()
    {
        return getBigDecimal("dsxs");
    }
    public void setDsxs(java.math.BigDecimal item)
    {
        setBigDecimal("dsxs", item);
    }
    /**
     * Object:�������������뵥��¼'s ������������property 
     */
    public java.math.BigDecimal getTsxs()
    {
        return getBigDecimal("tsxs");
    }
    public void setTsxs(java.math.BigDecimal item)
    {
        setBigDecimal("tsxs", item);
    }
    /**
     * Object:�������������뵥��¼'s ������Ԫ��property 
     */
    public java.math.BigDecimal getJl()
    {
        return getBigDecimal("jl");
    }
    public void setJl(java.math.BigDecimal item)
    {
        setBigDecimal("jl", item);
    }
    /**
     * Object:�������������뵥��¼'s �����������property 
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
     * Object:�������������뵥��¼'s ���뽱�����property 
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
     * Object:�������������뵥��¼'s ҵ��״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.TransactionStateEnum getBizType()
    {
        return com.kingdee.eas.fdc.sellhouse.TransactionStateEnum.getEnum(getString("bizType"));
    }
    public void setBizType(com.kingdee.eas.fdc.sellhouse.TransactionStateEnum item)
    {
		if (item != null) {
        setString("bizType", item.getValue());
		}
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
        return new BOSObjectType("5E8B4D8D");
    }
}