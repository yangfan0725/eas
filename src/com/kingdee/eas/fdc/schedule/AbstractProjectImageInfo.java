package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectImageInfo extends com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo implements Serializable 
{
    public AbstractProjectImageInfo()
    {
        this("id");
    }
    protected AbstractProjectImageInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.fdc.schedule.ProjectImageEntryCollection());
    }
    /**
     * Object: ��Ŀ������� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:��Ŀ�������'s �ļ�property 
     */
    public byte[] getFile()
    {
        return (byte[])get("file");
    }
    public void setFile(byte[] item)
    {
        put("file", item);
    }
    /**
     * Object:��Ŀ�������'s ��Сproperty 
     */
    public String getSize()
    {
        return getString("size");
    }
    public void setSize(String item)
    {
        setString("size", item);
    }
    /**
     * Object:��Ŀ�������'s �ֽڴ�Сproperty 
     */
    public int getSizeInByte()
    {
        return getInt("sizeInByte");
    }
    public void setSizeInByte(int item)
    {
        setInt("sizeInByte", item);
    }
    /**
     * Object:��Ŀ�������'s �깤�ٷֱ�property 
     */
    public java.math.BigDecimal getPercent()
    {
        return getBigDecimal("percent");
    }
    public void setPercent(java.math.BigDecimal item)
    {
        setBigDecimal("percent", item);
    }
    /**
     * Object:��Ŀ�������'s �ļ�����·��property 
     */
    public String getFileName()
    {
        return getString("fileName");
    }
    public void setFileName(String item)
    {
        setString("fileName", item);
    }
    /**
     * Object: ��Ŀ������� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.schedule.ProjectImageEntryCollection getEntries()
    {
        return (com.kingdee.eas.fdc.schedule.ProjectImageEntryCollection)get("entries");
    }
    /**
     * Object:��Ŀ�������'s ��Ŀ�������˵��property 
     */
    public String getImgDescription()
    {
        return getString("imgDescription");
    }
    public void setImgDescription(String item)
    {
        setString("imgDescription", item);
    }
    /**
     * Object:��Ŀ�������'s �Ƿ�ؼ�֤��property 
     */
    public boolean isIsKeyCert()
    {
        return getBoolean("isKeyCert");
    }
    public void setIsKeyCert(boolean item)
    {
        setBoolean("isKeyCert", item);
    }
    /**
     * Object: ��Ŀ������� 's ������� property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getRelateTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("relateTask");
    }
    public void setRelateTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("relateTask", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8A7E9098");
    }
}