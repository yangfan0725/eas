package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWorkAmountBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractWorkAmountBillInfo()
    {
        this("id");
    }
    protected AbstractWorkAmountBillInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.schedule.WorkAmountEntryCollection());
    }
    /**
     * Object: ���񹤳���� 's ����� property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getBizDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("bizDept");
    }
    public void setBizDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("bizDept", item);
    }
    /**
     * Object: ���񹤳���� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.schedule.WorkAmountEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.schedule.WorkAmountEntryCollection)get("entry");
    }
    /**
     * Object: ���񹤳���� 's ������Ŀ property 
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
     * Object: ���񹤳���� 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getAdminPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("adminPerson");
    }
    public void setAdminPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("adminPerson", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("75D27BC6");
    }
}