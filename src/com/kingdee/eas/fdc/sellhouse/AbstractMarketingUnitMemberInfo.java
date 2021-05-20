package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketingUnitMemberInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMarketingUnitMemberInfo()
    {
        this("id");
    }
    protected AbstractMarketingUnitMemberInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ӫ����Ԫ��Ա 's Ӫ����Ԫͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo item)
    {
        put("head", item);
    }
    /**
     * Object: Ӫ����Ԫ��Ա 's ��Ա property 
     */
    public com.kingdee.eas.base.permission.UserInfo getMember()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("member");
    }
    public void setMember(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("member", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's �Ƿ�����property 
     */
    public boolean isIsDuty()
    {
        return getBoolean("isDuty");
    }
    public void setIsDuty(boolean item)
    {
        setBoolean("isDuty", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's ������ְ��property 
     */
    public String getDutyPersonTitle()
    {
        return getString("dutyPersonTitle");
    }
    public void setDutyPersonTitle(String item)
    {
        setString("dutyPersonTitle", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's �Ƿ����޸�ְԱ�ͻ���Ȩ��property 
     */
    public boolean isIsUpdateMember()
    {
        return getBoolean("isUpdateMember");
    }
    public void setIsUpdateMember(boolean item)
    {
        setBoolean("isUpdateMember", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's �Ƿ���ҵ�����Ȩ��property 
     */
    public boolean isIsOperation()
    {
        return getBoolean("isOperation");
    }
    public void setIsOperation(boolean item)
    {
        setBoolean("isOperation", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's �Ƿ�����Ӷproperty 
     */
    public boolean isIsSharePercent()
    {
        return getBoolean("isSharePercent");
    }
    public void setIsSharePercent(boolean item)
    {
        setBoolean("isSharePercent", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's ��ӷֵ�����property 
     */
    public java.math.BigDecimal getTakePercentage()
    {
        return getBigDecimal("takePercentage");
    }
    public void setTakePercentage(java.math.BigDecimal item)
    {
        setBigDecimal("takePercentage", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's �Ƿ�������ְ��property 
     */
    public boolean isIsSellFunction()
    {
        return getBoolean("isSellFunction");
    }
    public void setIsSellFunction(boolean item)
    {
        setBoolean("isSellFunction", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's �Ƿ������ְ��property 
     */
    public boolean isIsTenancyFunction()
    {
        return getBoolean("isTenancyFunction");
    }
    public void setIsTenancyFunction(boolean item)
    {
        setBoolean("isTenancyFunction", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's �Ƿ�����ҵְ��property 
     */
    public boolean isIsWuYeFunction()
    {
        return getBoolean("isWuYeFunction");
    }
    public void setIsWuYeFunction(boolean item)
    {
        setBoolean("isWuYeFunction", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's �Ƿ���Ӫ��ְ��property 
     */
    public boolean isIsMarketFunction()
    {
        return getBoolean("isMarketFunction");
    }
    public void setIsMarketFunction(boolean item)
    {
        setBoolean("isMarketFunction", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's �Ƿ��й���ְ��property 
     */
    public boolean isIsProjectFunction()
    {
        return getBoolean("isProjectFunction");
    }
    public void setIsProjectFunction(boolean item)
    {
        setBoolean("isProjectFunction", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's �Ƿ��л�Աְ��property 
     */
    public boolean isIsInsideFunction()
    {
        return getBoolean("isInsideFunction");
    }
    public void setIsInsideFunction(boolean item)
    {
        setBoolean("isInsideFunction", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's �ϸ�����property 
     */
    public java.util.Date getAccessionDate()
    {
        return getDate("accessionDate");
    }
    public void setAccessionDate(java.util.Date item)
    {
        setDate("accessionDate", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's �������property 
     */
    public java.util.Date getDimissionDate()
    {
        return getDate("dimissionDate");
    }
    public void setDimissionDate(java.util.Date item)
    {
        setDate("dimissionDate", item);
    }
    /**
     * Object:Ӫ����Ԫ��Ա's Ӷ�����property 
     */
    public String getCommisionCal()
    {
        return getString("commisionCal");
    }
    public void setCommisionCal(String item)
    {
        setString("commisionCal", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("13D50949");
    }
}