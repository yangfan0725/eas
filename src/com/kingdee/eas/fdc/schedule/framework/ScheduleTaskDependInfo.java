package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;

public class ScheduleTaskDependInfo extends AbstractScheduleTaskDependInfo implements Serializable 
{
    public ScheduleTaskDependInfo()
    {
        super();
    }
    protected ScheduleTaskDependInfo(String pkField)
    {
        super(pkField);
    }
    
    /**
     * ����ڵ�
     * @return
     */
    public ScheduleTaskBaseInfo getTaskBase()
    {
        return (ScheduleTaskBaseInfo)get("task");
    }
    
    /**
     * ��������
     * @return
     */
    public ScheduleTaskBaseInfo getDependTaskBase()
    {
        return (ScheduleTaskBaseInfo)get("dependTask");
    }
    
}