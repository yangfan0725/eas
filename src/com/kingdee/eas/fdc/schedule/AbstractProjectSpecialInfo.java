package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectSpecialInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractProjectSpecialInfo()
    {
        this("id");
    }
    protected AbstractProjectSpecialInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ŀר������ 's ������ property 
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
     * Object: ��Ŀר������ 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getAdminDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("adminDept");
    }
    public void setAdminDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("adminDept", item);
    }
    /**
     * Object: ��Ŀר������ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FB704CB6");
    }
}