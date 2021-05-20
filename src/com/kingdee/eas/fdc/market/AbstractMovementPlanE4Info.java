package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMovementPlanE4Info extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractMovementPlanE4Info()
    {
        this("id");
    }
    protected AbstractMovementPlanE4Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ƻ���ϸ���� 's null property 
     */
    public com.kingdee.eas.fdc.market.MovementPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MovementPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MovementPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �ƻ���ϸ���� 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getResponsiblePers()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("responsiblePers");
    }
    public void setResponsiblePers(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("responsiblePers", item);
    }
    /**
     * Object:�ƻ���ϸ����'s �μ���Աproperty 
     */
    public String getPersons()
    {
        return getString("persons");
    }
    public void setPersons(String item)
    {
        setString("persons", item);
    }
    /**
     * Object:�ƻ���ϸ����'s ��ʼʱ��property 
     */
    public java.util.Date getStartTime()
    {
        return getDate("startTime");
    }
    public void setStartTime(java.util.Date item)
    {
        setDate("startTime", item);
    }
    /**
     * Object:�ƻ���ϸ����'s ����ʱ��property 
     */
    public java.util.Date getEndTime()
    {
        return getDate("endTime");
    }
    public void setEndTime(java.util.Date item)
    {
        setDate("endTime", item);
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
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("06163F18");
    }
}