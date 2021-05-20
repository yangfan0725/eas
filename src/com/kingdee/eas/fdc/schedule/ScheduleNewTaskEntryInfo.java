package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;

import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;

public class ScheduleNewTaskEntryInfo extends AbstractScheduleNewTaskEntryInfo implements Serializable 
{
    public ScheduleNewTaskEntryInfo()
    {
        super();
    }
    protected ScheduleNewTaskEntryInfo(String pkField)
    {
        super(pkField);
    }
    
    /**
     * ����ǰ������Ĵ�ӹ�ϵʱ��
     * //TODO �ȴ������-��ʼ 
     */
    public void reCalcPrefixTaskLinkTimes(ScheduleCalendarInfo calendarInfo){
		//���ô��ʱ��
		for(Iterator iter=this.getPrefixEntrys().iterator();iter.hasNext();){
			ScheduleAdjustPrefixTaskInfo prefixTaskEntry=(ScheduleAdjustPrefixTaskInfo)iter.next();
			FDCScheduleTaskInfo prefixTask=prefixTaskEntry.getPrefixTask();
			int linkTimes=0;
			BigDecimal effectTimes=null;
			if(prefixTaskEntry.getType()==TaskLinkTypeEnum.FINISH_START){
				effectTimes=ScheduleCalendarHelper.getEffectTimes(prefixTask.getEnd(), this.getStart(), calendarInfo);
			}
			if(prefixTaskEntry.getType()==TaskLinkTypeEnum.FINISH_FINISH){
				effectTimes=ScheduleCalendarHelper.getEffectTimes(prefixTask.getEnd(), this.getEnd(), calendarInfo);
			}
			if(prefixTaskEntry.getType()==TaskLinkTypeEnum.START_START){
				effectTimes=ScheduleCalendarHelper.getEffectTimes(prefixTask.getStart(), this.getStart(), calendarInfo);
			}
			linkTimes=effectTimes.intValue()-1;
			prefixTaskEntry.setDifference(linkTimes);
		}
	
    }
}