package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractReceiveDistillCommisionEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractReceiveDistillCommisionEntryInfo()
    {
        this("id");
    }
    protected AbstractReceiveDistillCommisionEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �տ�ֵ���¼ 's ���ز��տ property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object:�տ�ֵ���¼'s �������property 
     */
    public java.math.BigDecimal getSharePercent()
    {
        return getBigDecimal("sharePercent");
    }
    public void setSharePercent(java.math.BigDecimal item)
    {
        setBigDecimal("sharePercent", item);
    }
    /**
     * Object:�տ�ֵ���¼'s �ֵ�����property 
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
     * Object:�տ�ֵ���¼'s ��Ӷ����property 
     */
    public int getDistillCommisionLevel()
    {
        return getInt("distillCommisionLevel");
    }
    public void setDistillCommisionLevel(int item)
    {
        setInt("distillCommisionLevel", item);
    }
    /**
     * Object:�տ�ֵ���¼'s �Ƿ������Ӷproperty 
     */
    public boolean isIsAchieveCommision()
    {
        return getBoolean("isAchieveCommision");
    }
    public void setIsAchieveCommision(boolean item)
    {
        setBoolean("isAchieveCommision", item);
    }
    /**
     * Object: �տ�ֵ���¼ 's Ӫ����Ա property 
     */
    public com.kingdee.eas.base.permission.UserInfo getUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("user");
    }
    public void setUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("user", item);
    }
    /**
     * Object: �տ�ֵ���¼ 's �����Ӷ���� property 
     */
    public com.kingdee.eas.fdc.tenancy.MarketingUnitInfo getMarketUnit()
    {
        return (com.kingdee.eas.fdc.tenancy.MarketingUnitInfo)get("marketUnit");
    }
    public void setMarketUnit(com.kingdee.eas.fdc.tenancy.MarketingUnitInfo item)
    {
        put("marketUnit", item);
    }
    /**
     * Object:�տ�ֵ���¼'s �Ѽ�����property 
     */
    public java.math.BigDecimal getAlreadyShareAmount()
    {
        return getBigDecimal("alreadyShareAmount");
    }
    public void setAlreadyShareAmount(java.math.BigDecimal item)
    {
        setBigDecimal("alreadyShareAmount", item);
    }
    /**
     * Object:�տ�ֵ���¼'s ����ʱ��property 
     */
    public java.util.Date getHandleTime()
    {
        return getDate("handleTime");
    }
    public void setHandleTime(java.util.Date item)
    {
        setDate("handleTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E97BCCDB");
    }
}