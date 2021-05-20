/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������
 * 
 * @author �ź���
 * @version EAS7.0
 * @createDate 2011-8-19
 * @see
 */

public class TaskExtProPanelNewHelper implements ITaskPanelHelper {

	private FDCScheduleTaskPropertiesNewUI taskProperties;
	private boolean isFromExecUI = false;
	private final KDTask selectTask;

	public TaskExtProPanelNewHelper(FDCScheduleTaskPropertiesNewUI taskProperties) {
		this.taskProperties = taskProperties;
		selectTask = taskProperties.getSelectedTask();
	}

	public void load(boolean isExecuting) throws EASBizException, BOSException {
		this.isFromExecUI = isExecuting;
	}

	/**
	 * ����ƻ�����ʱ����չ����
	 * 
	 * @return
	 */
	private Map storeComplieProp() {
		Map map = new HashMap();
		return map;
	}

	/**
	 * ����ƻ�ִ��ʱ����չ����
	 * 
	 * @return
	 */
	private Map storeExecProp() {
		Map map = new HashMap();
		return map;
	}

	public void commit() {
		Map map = new HashMap();
		if (isFromExecUI) {
			map = storeExecProp();
		} else {
			map = storeComplieProp();
		}
		FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new FilterItemInfo("wbs.id",
		// getWBS().getId().toString()));
		try {
			map.put("wbsInfo", getWBS());
			if (FDCScheduleTaskExtFactory.getRemoteInstance().exists(filter)) {
				if (isFromExecUI)
					FDCScheduleTaskExtFactory.getRemoteInstance().updateExeProp(map);
				else
					FDCScheduleTaskExtFactory.getRemoteInstance().updateCompleProp(map);
			} else {
				FDCScheduleTaskExtFactory.getRemoteInstance().saveCompileProp(map);
			}
		} catch (Exception e) {
			taskProperties.handUIException(e);
		}
	}

	public FDCWBSInfo getWBS() {
		return ((FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo()).getWbs();
	}

	public Map getExtProperties() {
		return new HashMap();
	}

	/**
	 * ���ñ༭״̬,ִ��
	 */
	public void setEditUIStatus() {

	}

	/**
	 * ����ִ��״̬��ִ��״̬
	 */
	public void setExecutingUIStatus() {

	}

	/**
	 * ���ò鿴״̬
	 */
	public void setViewUIStatus() {

	}

	public void load() throws EASBizException, BOSException {
		load(taskProperties.isExecuting());
	}

	public void loadMaterial() throws BOSException {
	}

	public void loadTaskLog() throws BOSException {
	}
}
