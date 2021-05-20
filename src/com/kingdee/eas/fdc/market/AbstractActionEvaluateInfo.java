package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractActionEvaluateInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractActionEvaluateInfo()
    {
        this("id");
    }
    protected AbstractActionEvaluateInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����� 's ����� property 
     */
    public com.kingdee.eas.fdc.market.AppraiseInfo getAppraise()
    {
        return (com.kingdee.eas.fdc.market.AppraiseInfo)get("appraise");
    }
    public void setAppraise(com.kingdee.eas.fdc.market.AppraiseInfo item)
    {
        put("appraise", item);
    }
    /**
     * Object: ����� 's ������� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    /**
     * Object:�����'s ��������property 
     */
    public java.util.Date getEvaluatedate()
    {
        return getDate("evaluatedate");
    }
    public void setEvaluatedate(java.util.Date item)
    {
        setDate("evaluatedate", item);
    }
    /**
     * Object:�����'s �����property 
     */
    public String getMeno()
    {
        return getString("meno");
    }
    public void setMeno(String item)
    {
        setString("meno", item);
    }
    /**
     * Object:�����'s �IDproperty 
     */
    public String getActionid()
    {
        return getString("actionid");
    }
    public void setActionid(String item)
    {
        setString("actionid", item);
    }
    /**
     * Object:�����'s �����property 
     */
    public String getTheme()
    {
        return getString("theme");
    }
    public void setTheme(String item)
    {
        setString("theme", item);
    }
    /**
     * Object: ����� 's ����� property 
     */
    public com.kingdee.eas.fdc.market.MarketTypeInfo getMovementName()
    {
        return (com.kingdee.eas.fdc.market.MarketTypeInfo)get("movementName");
    }
    public void setMovementName(com.kingdee.eas.fdc.market.MarketTypeInfo item)
    {
        put("movementName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("887E5D00");
    }
}