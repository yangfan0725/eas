package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanProInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProjectMonthPlanProInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanProInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryCollection());
    }
    /**
     * Object: ��ǩ��ͬ&&�޺�ͬ���ø���ƻ� 's ������Ŀ property 
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
     * Object:��ǩ��ͬ&&�޺�ͬ���ø���ƻ�'s �汾��property 
     */
    public int getVersion()
    {
        return getInt("version");
    }
    public void setVersion(int item)
    {
        setInt("version", item);
    }
    /**
     * Object: ��ǩ��ͬ&&�޺�ͬ���ø���ƻ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryCollection)get("entry");
    }
    /**
     * Object:��ǩ��ͬ&&�޺�ͬ���ø���ƻ�'s �Ƿ�����property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    /**
     * Object: ��ǩ��ͬ&&�޺�ͬ���ø���ƻ� 's �ƻ����� property 
     */
    public com.kingdee.eas.fdc.basedata.PayPlanCycleInfo getCycle()
    {
        return (com.kingdee.eas.fdc.basedata.PayPlanCycleInfo)get("cycle");
    }
    public void setCycle(com.kingdee.eas.fdc.basedata.PayPlanCycleInfo item)
    {
        put("cycle", item);
    }
    /**
     * Object: ��ǩ��ͬ&&�޺�ͬ���ø���ƻ� 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getRespDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("respDept");
    }
    public void setRespDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("respDept", item);
    }
    /**
     * Object: ��ǩ��ͬ&&�޺�ͬ���ø���ƻ� 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRespPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("respPerson");
    }
    public void setRespPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("respPerson", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A900C46E");
    }
}