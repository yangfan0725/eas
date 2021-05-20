package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketManageChargeInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractMarketManageChargeInfo()
    {
        this("id");
    }
    protected AbstractMarketManageChargeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ƻ���ϸ���� 's null property 
     */
    public com.kingdee.eas.fdc.market.MarketManageInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MarketManageInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MarketManageInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �ƻ���ϸ���� 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getCustomer()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("customer", item);
    }
    /**
     * Object:�ƻ���ϸ����'s �μ���Աproperty 
     */
    public String getPerson()
    {
        return getString("person");
    }
    public void setPerson(String item)
    {
        setString("person", item);
    }
    /**
     * Object:�ƻ���ϸ����'s ��ʼʱ��property 
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
     * Object:�ƻ���ϸ����'s ����ʱ��property 
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
     * Object:�ƻ���ϸ����'s ��ϸʱ��property 
     */
    public String getActiveTime()
    {
        return getString("activeTime");
    }
    public void setActiveTime(String item)
    {
        setString("activeTime", item);
    }
    /**
     * Object:�ƻ���ϸ����'s ��������property 
     */
    public String getContent()
    {
        return getString("content");
    }
    public void setContent(String item)
    {
        setString("content", item);
    }
    /**
     * Object:�ƻ���ϸ����'s ������property 
     */
    public String getCompletion()
    {
        return getString("completion");
    }
    public void setCompletion(String item)
    {
        setString("completion", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("641DAE66");
    }
}