package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleBaseInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractScheduleBaseInfo()
    {
        this("id");
    }
    protected AbstractScheduleBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:���Ȼ���'s ����property 
     */
    public String getWebLink()
    {
        return getString("webLink");
    }
    public void setWebLink(String item)
    {
        setString("webLink", item);
    }
    /**
     * Object:���Ȼ���'s viewDateproperty 
     */
    public java.util.Date getViewDate()
    {
        return getDate("viewDate");
    }
    public void setViewDate(java.util.Date item)
    {
        setDate("viewDate", item);
    }
    /**
     * Object:���Ȼ���'s viewIndexproperty 
     */
    public int getViewIndex()
    {
        return getInt("viewIndex");
    }
    public void setViewIndex(int item)
    {
        setInt("viewIndex", item);
    }
    /**
     * Object:���Ȼ���'s gantt-divider-locationproperty 
     */
    public int getGdivlocation()
    {
        return getInt("gdivlocation");
    }
    public void setGdivlocation(int item)
    {
        setInt("gdivlocation", item);
    }
    /**
     * Object:���Ȼ���'s resource-divider-locationproperty 
     */
    public int getRdivlocation()
    {
        return getInt("rdivlocation");
    }
    public void setRdivlocation(int item)
    {
        setInt("rdivlocation", item);
    }
    /**
     * Object:���Ȼ���'s �汾property 
     */
    public java.math.BigDecimal getGversion()
    {
        return getBigDecimal("gversion");
    }
    public void setGversion(java.math.BigDecimal item)
    {
        setBigDecimal("gversion", item);
    }
    /**
     * Object:���Ȼ���'s ��ɫproperty 
     */
    public String getColor()
    {
        return getString("color");
    }
    public void setColor(String item)
    {
        setString("color", item);
    }
    /**
     * Object:���Ȼ���'s ����property 
     */
    public String getName()
    {
        return getName((Locale)null);
    }
    public void setName(String item)
    {
		setName(item,(Locale)null);
    }
    public String getName(Locale local)
    {
        return TypeConversionUtils.objToString(get("name", local));
    }
    public void setName(String item, Locale local)
    {
        put("name", item, local);
    }
    /**
     * Object:���Ȼ���'s ����״̬property 
     */
    public com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum getState()
    {
        return com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object: ���Ȼ��� 's ��֯ property 
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
     * Object:���Ȼ���'s ���ʱ��property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    /**
     * Object:���Ȼ���'s ����property 
     */
    public boolean isLocked()
    {
        return getBoolean("locked");
    }
    public void setLocked(boolean item)
    {
        setBoolean("locked", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CB06BB8E");
    }
}