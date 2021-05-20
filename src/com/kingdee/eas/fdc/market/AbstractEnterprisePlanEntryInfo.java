package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEnterprisePlanEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEnterprisePlanEntryInfo()
    {
        this("id");
    }
    protected AbstractEnterprisePlanEntryInfo(String pkField)
    {
        super(pkField);
        put("sellPlanEntry", new com.kingdee.eas.fdc.market.EnterpriseSellPlanCollection());
    }
    /**
     * Object: �󻮼ƻ���¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.market.EnterprisePlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.EnterprisePlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.EnterprisePlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �󻮼ƻ���¼ 's ������ property 
     */
    public com.kingdee.eas.fdc.market.EnterpriseSellPlanCollection getSellPlanEntry()
    {
        return (com.kingdee.eas.fdc.market.EnterpriseSellPlanCollection)get("sellPlanEntry");
    }
    /**
     * Object:�󻮼ƻ���¼'s ����״̬property 
     */
    public com.kingdee.eas.fdc.market.ThemeEnum getState()
    {
        return com.kingdee.eas.fdc.market.ThemeEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.market.ThemeEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object:�󻮼ƻ���¼'s ������property 
     */
    public String getDescribe()
    {
        return getString("describe");
    }
    public void setDescribe(String item)
    {
        setString("describe", item);
    }
    /**
     * Object:�󻮼ƻ���¼'s ������property 
     */
    public String getTheme()
    {
        return getString("theme");
    }
    public void setTheme(String item)
    {
        setString("theme", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3F0BC6F7");
    }
}