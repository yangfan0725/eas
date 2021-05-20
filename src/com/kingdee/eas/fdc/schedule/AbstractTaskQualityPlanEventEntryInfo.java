package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskQualityPlanEventEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTaskQualityPlanEventEntryInfo()
    {
        this("id");
    }
    protected AbstractTaskQualityPlanEventEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�����ƻ��ش��¼���¼'s �¼�����property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:�����ƻ��ش��¼���¼'s �¼�����property 
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
     * Object:�����ƻ��ش��¼���¼'s ����ʱ��property 
     */
    public java.util.Date getHappenTime()
    {
        return getDate("happenTime");
    }
    public void setHappenTime(java.util.Date item)
    {
        setDate("happenTime", item);
    }
    /**
     * Object:�����ƻ��ش��¼���¼'s �������property 
     */
    public String getSuggestion()
    {
        return getString("suggestion");
    }
    public void setSuggestion(String item)
    {
        setString("suggestion", item);
    }
    /**
     * Object: �����ƻ��ش��¼���¼ 's ֱ�������� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getDirectAdminPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("directAdminPerson");
    }
    public void setDirectAdminPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("directAdminPerson", item);
    }
    /**
     * Object: �����ƻ��ش��¼���¼ 's ��������� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getIndirectAdminPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("indirectAdminPerson");
    }
    public void setIndirectAdminPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("indirectAdminPerson", item);
    }
    /**
     * Object: �����ƻ��ش��¼���¼ 's ��ͷ property 
     */
    public com.kingdee.eas.fdc.schedule.TaskQualityPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.TaskQualityPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.TaskQualityPlanInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3658C145");
    }
}