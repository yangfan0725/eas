/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleHelper;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SpecialProgressReportBaseSchTaskPropertiesNewUI extends AbstractSpecialProgressReportBaseSchTaskPropertiesNewUI
{
    private static final Logger logger = CoreUIObject.getLogger(SpecialProgressReportBaseSchTaskPropertiesNewUI.class);
    
    /**
     * output class constructor
     */
    public SpecialProgressReportBaseSchTaskPropertiesNewUI() throws Exception
    {
        super();
    }
    public void storeFields()
    {
        super.storeFields();
    }
    public void onShow() throws Exception {
    	super.onShow();
    	
    	//��ʼ�������������F7
    	initRelationMainTask();
    }
    public void initRelationMainTask() throws EASBizException, BOSException{
    	this.prmtRelationMainTask.setQueryInfo("com.kingdee.eas.fdc.schedule.app.FDCScheduleTaskReaQuery");
    	this.prmtRelationMainTask.setDisplayFormat("$name$");
    	this.prmtRelationMainTask.setEditFormat("$name$");
    	this.prmtRelationMainTask.setCommitFormat("$name$");
    	prmtRelationMainTask.getQueryAgent().getQueryInfo().setAlias("�����������");
		this.prmtRelationMainTask.setEditable(false);
		prmtRelationMainTask.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				FDCScheduleTaskInfo mainTask = (FDCScheduleTaskInfo) e
						.getNewValue();
				
				if (mainTask != null) {
//					if (!mainTask.isIsLeaf()) {
//						FDCMsgBox.showWarning(
//								FDCSpecialScheduleTaskPropertiesUI.this,
//								"��ѡ��ĩ����������");
//						prmtRelationMainTask.setDataNoNotify(e.getOldValue());
//					}
					Set resultSet = new HashSet();
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
					if(task.getSchedule().getId() == null){
						FDCMsgBox.showInfo("���ȱ��浱ǰ�汾�������ٽ��й���");
						abort();
					}
					ScheduleHelper.getAllRootIDs(task, resultSet);
					try {
						FDCScheduleTaskCollection cols = ScheduleHelper.getAlreayRelateMainTask(task, resultSet);
						
						if(cols != null && cols.size()>0){
							StringBuffer strMsg = new StringBuffer("��ǰ����ĸ�����������Ӽ������Ѿ��������ӣ�\n");
							strMsg.append("ר����������\t          \t ������������ \n");
							FDCScheduleTaskInfo tempTask = null;
							for(Iterator it = cols.iterator();it.hasNext();){
								tempTask = (FDCScheduleTaskInfo) it.next();
								strMsg.append(tempTask.getName());
								strMsg.append(" \t  ����>   \t");
								strMsg.append(tempTask.getDependMainTaskID().getName());
								strMsg.append("\n");
								
							}
							strMsg.append("�����ٴν��д�ӣ�");
							FDCMsgBox.showError(strMsg.toString());
							prmtRelationMainTask.setDataNoNotify(e.getOldValue());
							abort();
						}
					} catch (BOSException e1) {
						handUIException(e1);
					}
					
					prmtRelationMainTask.setValue(mainTask);
					
				}
			}
		});
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		/* ���ݵ�ǰ��Ŀ���� */
		if (null != getUIContext().get("Owner")) {
			FDCScheduleBaseEditUI fDCScheduleBaseEditUI = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
			CurProjectInfo currentProjectInfo = (CurProjectInfo) fDCScheduleBaseEditUI.prmtCurproject.getValue();
			filter.appendFilterItem("schedule.project.id", currentProjectInfo.getId());
		}
		
		
		
		/* �������°汾���� */
		filter.appendFilterItem("schedule.isLatestVer", Boolean.TRUE);
		filter.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial.id", null,CompareType.EQUALS));
		
		
		SorterItemInfo sort = new SorterItemInfo("longNumber");
		view.getSorter().add(sort);
		view.setFilter(filter);
		this.prmtRelationMainTask.setEntityViewInfo(view);
		this.prmtRelationMainTask.setEditable(false);
		
		/*
		 * ��ʼ��ֵ
		 */
		if(selectTask.getScheduleTaskInfo() instanceof FDCScheduleTaskInfo){
			FDCScheduleTaskInfo scheduleTaskInfo = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
			if(null != scheduleTaskInfo.getDependMainTaskID() && null != scheduleTaskInfo.getDependMainTaskID().getId() && !"".equals(scheduleTaskInfo.getDependMainTaskID().getId().toString())){
				try {
					EntityViewInfo srcView = new EntityViewInfo();
					FilterInfo filterInfo = new FilterInfo();
					filterInfo.getFilterItems().add(new FilterItemInfo("srcid", scheduleTaskInfo.getDependMainTaskID().getId().toString()));
					filterInfo.getFilterItems().add(new FilterItemInfo("schedule.isLatestver", Boolean.TRUE));

					srcView.setFilter(filterInfo);
					FDCScheduleTaskCollection cols = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(srcView);
					if (!cols.isEmpty()) {
						this.prmtRelationMainTask.setDataNoNotify(cols.get(0));
					}
				} catch (Exception e) {
					logger.error("��ȡ�������ʧ�ܣ�����ֵ");
				}
			}
			
		}
    }
}