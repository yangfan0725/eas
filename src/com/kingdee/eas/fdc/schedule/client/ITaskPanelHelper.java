package com.kingdee.eas.fdc.schedule.client;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;

public interface ITaskPanelHelper {
	public void load() throws EASBizException, BOSException;
	public void commit();
    /**
     * ����ִ��״̬�ĸ�UI״̬
     */
	public void setExecutingUIStatus();
    
    /**
     * ���ò鿴״̬�ĸ�UI״̬
     */
	public void setViewUIStatus();
    
    /**
     * ���ñ༭״̬��UI״̬--ֻ�мƻ����Ƶ�ʱ��Ż��б༭״̬����������
     */
    public void setEditUIStatus();
}
