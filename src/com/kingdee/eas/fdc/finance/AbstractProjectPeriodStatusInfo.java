package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectPeriodStatusInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractProjectPeriodStatusInfo()
    {
        this("id");
    }
    protected AbstractProjectPeriodStatusInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������Ŀ�ڼ����'s ������ʼ��property 
     */
    public boolean isIsClosed()
    {
        return getBoolean("isClosed");
    }
    public void setIsClosed(boolean item)
    {
        setBoolean("isClosed", item);
    }
    /**
     * Object: ������Ŀ�ڼ���� 's ��֯ property 
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
     * Object: ������Ŀ�ڼ���� 's ������Ŀ property 
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
     * Object: ������Ŀ�ڼ���� 's �����ڼ� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getStartPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("startPeriod");
    }
    public void setStartPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("startPeriod", item);
    }
    /**
     * Object: ������Ŀ�ڼ���� 's �ɱ���ǰ�ڼ� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getCostPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("costPeriod");
    }
    public void setCostPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("costPeriod", item);
    }
    /**
     * Object: ������Ŀ�ڼ���� 's ����ǰ�ڼ� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getFinacialPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("finacialPeriod");
    }
    public void setFinacialPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("finacialPeriod", item);
    }
    /**
     * Object:������Ŀ�ڼ����'s �ɱ��Ѷ���property 
     */
    public boolean isIsCostFreeze()
    {
        return getBoolean("isCostFreeze");
    }
    public void setIsCostFreeze(boolean item)
    {
        setBoolean("isCostFreeze", item);
    }
    /**
     * Object:������Ŀ�ڼ����'s �����Ѿ�����property 
     */
    public boolean isIsFinaclaFreeze()
    {
        return getBoolean("isFinaclaFreeze");
    }
    public void setIsFinaclaFreeze(boolean item)
    {
        setBoolean("isFinaclaFreeze", item);
    }
    /**
     * Object:������Ŀ�ڼ����'s �ɱ��Ѿ��½�property 
     */
    public boolean isIsCostEnd()
    {
        return getBoolean("isCostEnd");
    }
    public void setIsCostEnd(boolean item)
    {
        setBoolean("isCostEnd", item);
    }
    /**
     * Object:������Ŀ�ڼ����'s �����Ѿ��½�property 
     */
    public boolean isIsFinacialEnd()
    {
        return getBoolean("isFinacialEnd");
    }
    public void setIsFinacialEnd(boolean item)
    {
        setBoolean("isFinacialEnd", item);
    }
    /**
     * Object:������Ŀ�ڼ����'s ����������property 
     */
    public boolean isIsEnd()
    {
        return getBoolean("isEnd");
    }
    public void setIsEnd(boolean item)
    {
        setBoolean("isEnd", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("30DDC95D");
    }
}