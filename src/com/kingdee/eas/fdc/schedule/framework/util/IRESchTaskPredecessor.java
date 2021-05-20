package com.kingdee.eas.fdc.schedule.framework.util;

import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;

public interface IRESchTaskPredecessor {
	
	/**
	 * ǰ������
	 * 
	 * @return
	 */
	public IRESchTask getPredecessor();

	public void setPredecessor(IRESchTask task);
	/**
	 * ��ǰ����
	 * 
	 * @return
	 */
	public IRESchTask getCurrentTask();

	public void setCurrentTask(IRESchTask task);
	/**
	 * �ʱ��
	 * 
	 * @return
	 */
	public int getDifferenceDay();

	public void setDifferenceDay(int item);
	/**
	 * ǰ������
	 * 
	 * @return
	 */
	public TaskLinkTypeEnum getPredecessorType();

	public void setPredecessorType(TaskLinkTypeEnum item);	

}
