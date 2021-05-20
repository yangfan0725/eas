package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskWorkResultEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTaskWorkResultEntryInfo()
    {
        this("id");
    }
    protected AbstractTaskWorkResultEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�����ɹ���¼'s �ɹ�����property 
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
     * Object:�����ɹ���¼'s �ɹ����property 
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
     * Object:�����ɹ���¼'s �ύʱ��property 
     */
    public java.util.Date getCreateTime()
    {
        return getDate("createTime");
    }
    public void setCreateTime(java.util.Date item)
    {
        setDate("createTime", item);
    }
    /**
     * Object: �����ɹ���¼ 's �ύ�� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getCreator()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("creator");
    }
    public void setCreator(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("creator", item);
    }
    /**
     * Object:�����ɹ���¼'s �ɹ�����property 
     */
    public String getResultType()
    {
        return getString("resultType");
    }
    public void setResultType(String item)
    {
        setString("resultType", item);
    }
    /**
     * Object: �����ɹ���¼ 's �������� property 
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
     * Object: �����ɹ���¼ 's ��ͷ property 
     */
    public com.kingdee.eas.fdc.schedule.TaskWorkResultInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.TaskWorkResultInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.TaskWorkResultInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �����ɹ���¼ 's ���� property 
     */
    public com.kingdee.eas.fdc.schedule.TaskWorkResultEntryFileInfo getFile()
    {
        return (com.kingdee.eas.fdc.schedule.TaskWorkResultEntryFileInfo)get("file");
    }
    public void setFile(com.kingdee.eas.fdc.schedule.TaskWorkResultEntryFileInfo item)
    {
        put("file", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6E3EA6A9");
    }
}