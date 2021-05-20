package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketManageInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractMarketManageInfo()
    {
        this("id");
    }
    protected AbstractMarketManageInfo(String pkField)
    {
        super(pkField);
        put("Effect", new com.kingdee.eas.fdc.market.MarketManageEffectCollection());
        put("Charge", new com.kingdee.eas.fdc.market.MarketManageChargeCollection());
        put("entrys", new com.kingdee.eas.fdc.market.MarketManageEntryCollection());
        put("Customer", new com.kingdee.eas.fdc.market.MarketManageCustomerCollection());
        put("Media", new com.kingdee.eas.fdc.market.MarketManageMediaCollection());
    }
    /**
     * Object: Ӫ��� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.market.MarketManageEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.market.MarketManageEntryCollection)get("entrys");
    }
    /**
     * Object:Ӫ���'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:Ӫ���'s ���������property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object: Ӫ��� 's ��������� property 
     */
    public com.kingdee.eas.fdc.market.MovementPlanInfo getMarketPlan()
    {
        return (com.kingdee.eas.fdc.market.MovementPlanInfo)get("marketPlan");
    }
    public void setMarketPlan(com.kingdee.eas.fdc.market.MovementPlanInfo item)
    {
        put("marketPlan", item);
    }
    /**
     * Object:Ӫ���'s ��֯����property 
     */
    public String getOrgName()
    {
        return getString("orgName");
    }
    public void setOrgName(String item)
    {
        setString("orgName", item);
    }
    /**
     * Object: Ӫ��� 's Ӫ����Ŀ property 
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
     * Object:Ӫ���'s ��������property 
     */
    public com.kingdee.eas.fdc.market.SchemeTypeEnum getSchemeType()
    {
        return com.kingdee.eas.fdc.market.SchemeTypeEnum.getEnum(getString("schemeType"));
    }
    public void setSchemeType(com.kingdee.eas.fdc.market.SchemeTypeEnum item)
    {
		if (item != null) {
        setString("schemeType", item.getValue());
		}
    }
    /**
     * Object:Ӫ���'s ��ʼ����property 
     */
    public java.util.Date getBeginDate()
    {
        return getDate("beginDate");
    }
    public void setBeginDate(java.util.Date item)
    {
        setDate("beginDate", item);
    }
    /**
     * Object:Ӫ���'s ��������property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:Ӫ���'s ����ϵͳproperty 
     */
    public com.kingdee.eas.fdc.basedata.MoneySysTypeEnum getBelongSystem()
    {
        return com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.getEnum(getString("belongSystem"));
    }
    public void setBelongSystem(com.kingdee.eas.fdc.basedata.MoneySysTypeEnum item)
    {
		if (item != null) {
        setString("belongSystem", item.getValue());
		}
    }
    /**
     * Object:Ӫ���'s �����property 
     */
    public String getMovemntTheme()
    {
        return getString("movemntTheme");
    }
    public void setMovemntTheme(String item)
    {
        setString("movemntTheme", item);
    }
    /**
     * Object: Ӫ��� 's ����� property 
     */
    public com.kingdee.eas.fdc.market.MarketTypeInfo getMarkettype()
    {
        return (com.kingdee.eas.fdc.market.MarketTypeInfo)get("markettype");
    }
    public void setMarkettype(com.kingdee.eas.fdc.market.MarketTypeInfo item)
    {
        put("markettype", item);
    }
    /**
     * Object:Ӫ���'s �ƻ��ܷ���property 
     */
    public java.math.BigDecimal getPlanTotalMoney()
    {
        return getBigDecimal("planTotalMoney");
    }
    public void setPlanTotalMoney(java.math.BigDecimal item)
    {
        setBigDecimal("planTotalMoney", item);
    }
    /**
     * Object:Ӫ���'s ʵ���ܷ���property 
     */
    public java.math.BigDecimal getFactTotalMoney()
    {
        return getBigDecimal("factTotalMoney");
    }
    public void setFactTotalMoney(java.math.BigDecimal item)
    {
        setBigDecimal("factTotalMoney", item);
    }
    /**
     * Object:Ӫ���'s ʵ�ʸ����property 
     */
    public java.math.BigDecimal getFactPayment()
    {
        return getBigDecimal("factPayment");
    }
    public void setFactPayment(java.math.BigDecimal item)
    {
        setBigDecimal("factPayment", item);
    }
    /**
     * Object: Ӫ��� 's �ƻ���ϸ���� property 
     */
    public com.kingdee.eas.fdc.market.MarketManageChargeCollection getCharge()
    {
        return (com.kingdee.eas.fdc.market.MarketManageChargeCollection)get("Charge");
    }
    /**
     * Object: Ӫ��� 's �ƻ�������Ա property 
     */
    public com.kingdee.eas.fdc.market.MarketManageCustomerCollection getCustomer()
    {
        return (com.kingdee.eas.fdc.market.MarketManageCustomerCollection)get("Customer");
    }
    /**
     * Object: Ӫ��� 's ʵ��Ч�� property 
     */
    public com.kingdee.eas.fdc.market.MarketManageEffectCollection getEffect()
    {
        return (com.kingdee.eas.fdc.market.MarketManageEffectCollection)get("Effect");
    }
    /**
     * Object:Ӫ���'s ��ͬ��property 
     */
    public String getContractNumber()
    {
        return getString("contractNumber");
    }
    public void setContractNumber(String item)
    {
        setString("contractNumber", item);
    }
    /**
     * Object: Ӫ��� 's ý����� property 
     */
    public com.kingdee.eas.fdc.market.MarketManageMediaCollection getMedia()
    {
        return (com.kingdee.eas.fdc.market.MarketManageMediaCollection)get("Media");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5F2D3512");
    }
}