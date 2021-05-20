package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseElsePayListEntryInfo extends com.kingdee.eas.fdc.basecrm.RevListInfo implements Serializable 
{
    public AbstractPurchaseElsePayListEntryInfo()
    {
        this("id");
    }
    protected AbstractPurchaseElsePayListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �����������ϸ��¼ 's ��������ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("head", item);
    }
    /**
     * Object: �����������ϸ��¼ 's �ұ� property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:�����������ϸ��¼'s ����ʱ��property 
     */
    public java.sql.Timestamp getLastUpdateTime()
    {
        return getTimestamp("lastUpdateTime");
    }
    public void setLastUpdateTime(java.sql.Timestamp item)
    {
        setTimestamp("lastUpdateTime", item);
    }
    /**
     * Object:�����������ϸ��¼'s ����ʱ��property 
     */
    public java.sql.Timestamp getCreateTime()
    {
        return getTimestamp("createTime");
    }
    public void setCreateTime(java.sql.Timestamp item)
    {
        setTimestamp("createTime", item);
    }
    /**
     * Object:�����������ϸ��¼'s ����property 
     */
    public String getDescription()
    {
        return getDescription((Locale)null);
    }
    public void setDescription(String item)
    {
		setDescription(item,(Locale)null);
    }
    public String getDescription(Locale local)
    {
        return TypeConversionUtils.objToString(get("description", local));
    }
    public void setDescription(String item, Locale local)
    {
        put("description", item, local);
    }
    /**
     * Object:�����������ϸ��¼'s ���շ��ù�ʽproperty 
     */
    public String getSheCollection()
    {
        return getString("sheCollection");
    }
    public void setSheCollection(String item)
    {
        setString("sheCollection", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9D280781");
    }
}