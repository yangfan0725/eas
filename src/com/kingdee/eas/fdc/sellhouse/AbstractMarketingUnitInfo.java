package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketingUnitInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractMarketingUnitInfo()
    {
        this("id");
    }
    protected AbstractMarketingUnitInfo(String pkField)
    {
        super(pkField);
        put("member", new com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection());
        put("sellProject", new com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectCollection());
    }
    /**
     * Object: Ӫ����Ԫ 's ����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: Ӫ����Ԫ 's ��֯��Ԫ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object: Ӫ����Ԫ 's ��Ŀ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectCollection getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectCollection)get("sellProject");
    }
    /**
     * Object: Ӫ����Ԫ 's ��Ա��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection getMember()
    {
        return (com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection)get("member");
    }
    /**
     * Object:Ӫ����Ԫ's �Ƿ�����ְ��property 
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
     * Object:Ӫ����Ԫ's �Ƿ����ְ��property 
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
     * Object:Ӫ����Ԫ's �Ƿ���ҵְ��property 
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
     * Object:Ӫ����Ԫ's �Ƿ���Ӫ��ְ��property 
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
     * Object:Ӫ����Ԫ's �Ƿ��й���ְ��property 
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
     * Object:Ӫ����Ԫ's �Ƿ��л�Աְ��property 
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
     * Object:Ӫ����Ԫ's ����property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B6B669CF");
    }
}