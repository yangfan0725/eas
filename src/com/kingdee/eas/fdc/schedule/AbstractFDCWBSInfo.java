package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCWBSInfo extends com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo implements Serializable 
{
    public AbstractFDCWBSInfo()
    {
        this("id");
    }
    protected AbstractFDCWBSInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.schedule.PrefixWBSEntryCollection());
        put("ctrlItemEntrys", new com.kingdee.eas.fdc.schedule.CtrlItemWBSEntryCollection());
    }
    /**
     * Object: ��ĿWBS 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCWBSInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.FDCWBSInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.FDCWBSInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��ĿWBS 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: ��ĿWBS 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getAdminPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("adminPerson");
    }
    public void setAdminPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("adminPerson", item);
    }
    /**
     * Object:��ĿWBS's Ԥ�ƹ���property 
     */
    public int getEstimateDays()
    {
        return getInt("estimateDays");
    }
    public void setEstimateDays(int item)
    {
        setInt("estimateDays", item);
    }
    /**
     * Object:��ĿWBS's �Ƿ�����property 
     */
    public boolean isIsLocked()
    {
        return getBoolean("isLocked");
    }
    public void setIsLocked(boolean item)
    {
        setBoolean("isLocked", item);
    }
    /**
     * Object: ��ĿWBS 's �������� property 
     */
    public com.kingdee.eas.fdc.schedule.TaskTypeInfo getTaskType()
    {
        return (com.kingdee.eas.fdc.schedule.TaskTypeInfo)get("taskType");
    }
    public void setTaskType(com.kingdee.eas.fdc.schedule.TaskTypeInfo item)
    {
        put("taskType", item);
    }
    /**
     * Object: ��ĿWBS 's ��¼ property 
     */
    public com.kingdee.eas.fdc.schedule.PrefixWBSEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.PrefixWBSEntryCollection)get("entrys");
    }
    /**
     * Object: ��ĿWBS 's ���������¼ property 
     */
    public com.kingdee.eas.fdc.schedule.CtrlItemWBSEntryCollection getCtrlItemEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.CtrlItemWBSEntryCollection)get("ctrlItemEntrys");
    }
    /**
     * Object: ��ĿWBS 's ����λ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getAdminDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("adminDept");
    }
    public void setAdminDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("adminDept", item);
    }
    /**
     * Object:��ĿWBS's ���property 
     */
    public int getIndex()
    {
        return getInt("index");
    }
    public void setIndex(int item)
    {
        setInt("index", item);
    }
    /**
     * Object:��ĿWBS's �Ƿ���ģ�嵼��property 
     */
    public boolean isIsFromTemplate()
    {
        return getBoolean("isFromTemplate");
    }
    public void setIsFromTemplate(boolean item)
    {
        setBoolean("isFromTemplate", item);
    }
    /**
     * Object: ��ĿWBS 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getRespDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("respDept");
    }
    public void setRespDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("respDept", item);
    }
    /**
     * Object:��ĿWBS's �Ƿ񲻿ɼ�property 
     */
    public boolean isIsUnVisible()
    {
        return getBoolean("isUnVisible");
    }
    public void setIsUnVisible(boolean item)
    {
        setBoolean("isUnVisible", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("786DC4B9");
    }
}