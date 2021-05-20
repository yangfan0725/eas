package com.kingdee.eas.fdc.schedule.framework.util;

import net.sourceforge.ganttproject.task.Task;

import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;

/**
 * KD GP ��GanttProject��������ͨ�ð�����
 * @author xiaohong_shi
 *
 */
public class KDGPCommonHelper {
	private KDGPCommonHelper(){}
	/**
	 * �Ƿ��Ǳ��ƻ�������
	 * @param task
	 * @return
	 */
	public static boolean isScheduleTask(Task task){
		 if(task instanceof KDTask && ((KDTask)task).getScheduleTaskInfo()!=null 
         		&& ((KDTask)task).getScheduleTaskInfo().getScheduleBase()!=null && ((KDTask)task).getScheduleTaskInfo().getScheduleBase().containsKey("AdjustInfo")){
         	return true;
         }
		return (task instanceof KDTask) &&((KDTask)task).isScheduleTask();
	}
}
