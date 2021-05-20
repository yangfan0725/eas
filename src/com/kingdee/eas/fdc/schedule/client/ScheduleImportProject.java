/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;

import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCSCHUtils;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchCalendar;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchTaskCreator;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchTaskPredecessorCreator;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTask;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTaskPredecessor;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������ ����project
 * 
 * @author �ź���
 * @version EAS7.0
 * @createDate 2011-9-23
 * @see
 */

public class ScheduleImportProject {
	
	private FDCScheduleBaseEditUI editUI;
	private FDCScheduleInfo editData;
	private CoreBaseCollection wbsCoreBaseCollection;
	
	public ScheduleImportProject(FDCScheduleInfo editData, FDCScheduleBaseEditUI editUI) {
		this.editData = editData;
		this.editUI = editUI;
	}

	public void execude() throws Exception {
		/* �洢��ǰ���� */
		CoreBaseCollection currentAllTaskCollection = new CoreBaseCollection();
		CoreBaseCollection currentWbsCollection = new CoreBaseCollection();
		CoreBaseCollection currentDependTaskCollection = new CoreBaseCollection();
		for (int k = 0; k < this.editData.getTaskEntrys().size(); k++) {
			FDCScheduleTaskInfo fDCScheduleTaskInfo = this.editData.getTaskEntrys().get(k);
			currentAllTaskCollection.add(fDCScheduleTaskInfo);
			if (null != fDCScheduleTaskInfo.getWbs()) {
				currentWbsCollection.add(fDCScheduleTaskInfo.getWbs());
			}
			FDCScheduleTaskDependCollection fDCScheduleTaskDependCollection = fDCScheduleTaskInfo.getDependEntrys();
			for (int m = 0; m < fDCScheduleTaskDependCollection.size(); m++) {
				currentDependTaskCollection.add(fDCScheduleTaskDependCollection.get(m));
			}
		}

		if (this.editData.getTaskEntrys().size() > 0) {
			int result = FDCMsgBox.showConfirm2("�˲�����ɾ�����������Ƿ����");
			/* result==0 ɾ������������ result==2 ��ִ�е������ */
			if (result == 2) {
				return;
			}
		}

		/*
		 * ִ�е���project����
		 */
		JFileChooser chooser = new JFileChooser(new File("c:\\"));
		/* ���ù����� */
		chooser.setFileFilter(new FilterType());
		/* �����Ի��� */
		chooser.showDialog(null, "����Project");
		/* �ж��Ƿ�ѡ���ļ� */
		if (chooser.getSelectedFile() == null) {
			return;
		}
		/* ���ѡȡ�ļ�·�� */
		String filePath = chooser.getSelectedFile().getPath();
		File file = new File(filePath);

		/* ��ձ������ */
		int entryCount = this.editData.getTaskEntrys().size();
		for (int k = 0; k < entryCount; k++) {
			this.editData.getTaskEntrys().remove(this.editData.getTaskEntrys().get(0));
		}

		/* ȡ���� */
		List projectList = RESchMSProjectReader.pasreMSProject(file,
				new IRESchTaskCreator() {
					public IRESchTask createSchTask() {
						return (IRESchTask) new FDCScheduleTaskInfo();
					}
				}, new IRESchTaskPredecessorCreator() {
					public IRESchTaskPredecessor createSchTaskPredecessor() {
						return new FDCScheduleTaskDependInfo();
					}
				}, new IRESchCalendar() {
					public ScheduleCalendarInfo getSchedule() {
						return editData.getCalendar();
					}
				});

		CoreBaseCollection newDependTaskCollection = new CoreBaseCollection();
		/* �����Ա�������idΪ������������Ϊֵ���Ա���湹����νṹ */
		Map projectMap = new HashMap();
		for (int k = 0; k < projectList.size(); k++) {
			if (null != projectList.get(k)) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) projectList.get(k);
				if (null != fDCScheduleTaskInfo.getMsProjectId()) {
					projectMap.put(fDCScheduleTaskInfo.getMsProjectId().toString(), fDCScheduleTaskInfo);
				}
				/* ����������� */
				FDCScheduleTaskDependCollection fDCScheduleTaskDependCollection = fDCScheduleTaskInfo.getDependEntrys();
				for (int j = 0; j < fDCScheduleTaskDependCollection.size(); j++) {
					FDCScheduleTaskDependInfo fDCScheduleTaskDependInfo = fDCScheduleTaskDependCollection.get(j);
					fDCScheduleTaskDependInfo.setId(BOSUuid.create((new FDCScheduleTaskDependInfo()).getBOSType()));
					newDependTaskCollection.add(fDCScheduleTaskDependInfo);
				}
			}
		}

		wbsCoreBaseCollection = new CoreBaseCollection();
		/* ����ģ���������ݣ������ò�νṹ */
		CoreBaseCollection newTaskCollection = new CoreBaseCollection();
		for (int k = 0; k < projectList.size(); k++) {
			if (null != projectList.get(k)) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) projectList.get(k);
				Object projectObj = projectMap.get(String.valueOf(null == fDCScheduleTaskInfo.getMsParentPrjId() ? "" : fDCScheduleTaskInfo
						.getMsParentPrjId()));
				if (null != projectObj && !"".equals(projectObj.toString().trim())) {
					if (projectObj instanceof FDCScheduleTaskInfo) {
						fDCScheduleTaskInfo.setParent((FDCScheduleTaskInfo) projectObj);
					}
				}
				/* ����WBS */
				fDCScheduleTaskInfo.setWbs(createWBS(fDCScheduleTaskInfo, fDCScheduleTaskInfo.getParent()));
				/* ������Ч���� */
				BigDecimal defaultDuration = FDCSCHUtils.getDefaultDuration();
				BigDecimal effDay = BigDecimal.valueOf(com.kingdee.eas.fdc.schedule.framework.DateUtils.diffdates(fDCScheduleTaskInfo.getPlanStart(),
						fDCScheduleTaskInfo.getPlanEnd()));
				fDCScheduleTaskInfo.setDuration(effDay.intValue());
				fDCScheduleTaskInfo.setEffectTimes(effDay);
				fDCScheduleTaskInfo.setSchedule(this.editData);
				fDCScheduleTaskInfo.setCalendar(this.editData.getCalendar());
				fDCScheduleTaskInfo.setNatureTimes(BigDecimal.valueOf(0));
				fDCScheduleTaskInfo.setIsScheduleTask(true);
				fDCScheduleTaskInfo.setPriority(1);
				fDCScheduleTaskInfo.setIsAdd(true);
				fDCScheduleTaskInfo.setStart(fDCScheduleTaskInfo.getPlanStart());
				fDCScheduleTaskInfo.setEnd(fDCScheduleTaskInfo.getPlanEnd());
				if (fDCScheduleTaskInfo.getParent() != null) {
					fDCScheduleTaskInfo.setLongNumber(fDCScheduleTaskInfo.getParent().getLongNumber() + "!" + fDCScheduleTaskInfo.getNumber());
					fDCScheduleTaskInfo.setLevel(fDCScheduleTaskInfo.getLevel() + 1);
				} else {
					fDCScheduleTaskInfo.setLongNumber(fDCScheduleTaskInfo.getNumber());
					fDCScheduleTaskInfo.setLevel(0);
				}
				if (null != fDCScheduleTaskInfo.getWbs()) {
					fDCScheduleTaskInfo.setLocked(fDCScheduleTaskInfo.getWbs().isIsLocked());
				}
				newTaskCollection.add((CoreBaseInfo) fDCScheduleTaskInfo);
				this.editData.getTaskEntrys().add(fDCScheduleTaskInfo);
				// RESchTemplateTaskFactory.getRemoteInstance().addnew(
				// schTemplateTaskInfo);
			}
		}

		// runSave();

		// FDCScheduleTaskFactory.getRemoteInstance().importTasks(
		// currentAllTaskCollection, newTaskCollection, currentWbsCollection,
		// wbsCoreBaseCollection, currentDependTaskCollection,
		// newDependTaskCollection);
		/* ˢ�±� */
		/* ���¼������� */
		try {
			// editUI.setDataObject(this.editData);
			// editUI.load2GanttAfter(info)
			// this.setInitEnd(false);
			// editUI.setMessageText("��" + newTaskCollection.size() +
			// "�����ݱ��ɹ����룡");
			// editUI.showMessage();
			// editUI.weakOnShow();
		} catch (Exception ex) {
		}
	}

	// ����WBS
	private FDCWBSInfo createWBS(FDCScheduleTaskInfo task, FDCScheduleTaskInfo parentTask) {
		if (null == parentTask) {
			parentTask = new FDCScheduleTaskInfo();
		}
		FDCWBSInfo wbs = new FDCWBSInfo();
		wbs.setId(BOSUuid.create(wbs.getBOSType()));
		wbs.setParent(parentTask.getWbs());
		wbs.setIsEnabled(true);
		wbs.setBoolean("isNew", true);
		wbs.setIsFromTemplate(false);
		wbs.setCurProject(this.editData.getProject());
		wbs.setName(task.getName());
		wbs.setTaskType(this.editData.getScheduleType());
		if (editUI.isSpecialSchedule()) {
			// ר����̳���ѡ�ڵ�������˺����β���
			if (null != parentTask.getWbs()) {
				wbs.setAdminPerson(parentTask.getWbs().getAdminPerson());
				wbs.setRespDept(parentTask.getWbs().getRespDept());
			}
		} else {
			// ���������üƻ�����Ϊ��½�û��ĳɱ�����
			wbs.setAdminDept(SysContext.getSysContext().getCurrentCostUnit().castToFullOrgUnitInfo());
		}
		wbs.setIsUnVisible(false);
		BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(task.getStart(), task.getEnd(), this.editData.getCalendar());
		wbs.setEstimateDays(effectTimes.intValue());
		wbsCoreBaseCollection.add(wbs);
		return wbs;
	}
}
