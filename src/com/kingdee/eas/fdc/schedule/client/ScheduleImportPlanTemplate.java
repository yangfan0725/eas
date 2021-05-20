/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.DependHardnessEnum;
import com.kingdee.eas.framework.CoreBaseCollection;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������ ����ƻ�ģ��
 * 
 * @author �ź���
 * @version EAS7.0
 * @createDate 2011-9-23
 * @see
 */

public class ScheduleImportPlanTemplate {
	private FDCScheduleBaseEditUI editUI;
	private FDCScheduleInfo editData;
	private CoreBaseCollection wbsCoreBaseCollection;

	public ScheduleImportPlanTemplate(FDCScheduleInfo editData, FDCScheduleBaseEditUI editUI) {
		this.editData = editData;
		this.editUI = editUI;
	}
	
	public void importPlanTemplate(RESchTemplateInfo schTemplateInfo) {
		if (null == schTemplateInfo || null == schTemplateInfo.getId() || "".equals(schTemplateInfo.getId().toString().trim())) {
			return;
		}

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

		/* ��ձ������ */
		int entryCount = this.editData.getTaskEntrys().size();
		for (int k = 0; k < entryCount; k++) {
			this.editData.getTaskEntrys().remove(this.editData.getTaskEntrys().get(0));
		}

		/* ����ģ��id������е�ģ��������Ϣ����ͬʱ����ģ������ */
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("template.id", schTemplateInfo.getId());
		view.setFilter(filter);
		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("longNumber");
		sii.setSortType(SortType.ASCEND);
		view.getSorter().add(sii);
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("template.*"));
		view.getSelector().add(new SelectorItemInfo("parent.*"));
		view.getSelector().add(new SelectorItemInfo("standardTask.*"));
		view.getSelector().add(new SelectorItemInfo("businessType.*"));
		view.getSelector().add(new SelectorItemInfo("predecessors.*"));
		view.getSelector().add(new SelectorItemInfo("achievementType.*"));
		try {
			RESchTemplateTaskCollection rESchTemplateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(
					view);

			/* ȡֵ */
			Map sMap = new HashMap();
			Map pMap = new HashMap();
			Map handlerProcessorMap = new HashMap();
			FDCScheduleTaskCollection fDCScheduleTaskCollection = new FDCScheduleTaskCollection();
			for (int k = 0; k < rESchTemplateTaskCollection.size(); k++) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = new FDCScheduleTaskInfo();
				RESchTemplateTaskInfo rESchTemplateTaskInfo = rESchTemplateTaskCollection.get(k);
				/* ���������� */
				fDCScheduleTaskInfo.setId(BOSUuid.create((new FDCScheduleTaskInfo()).getBOSType()));
				/* ������������ */
				fDCScheduleTaskInfo.setName(rESchTemplateTaskInfo.getName());

				/* ���� ������� */
				fDCScheduleTaskInfo.setTaskType(rESchTemplateTaskInfo.getTaskType());
				/* ����ҵ������ */
				for (int s = 0; s < rESchTemplateTaskInfo.getBusinessType().size(); s++) {
					RESchTemplateTaskBizTypeInfo rESchTemplateTaskBizTypeInfo = rESchTemplateTaskInfo.getBusinessType().get(s);
					ScheduleTaskBizTypeInfo scheduleTaskBizTypeInfo = new ScheduleTaskBizTypeInfo();
					scheduleTaskBizTypeInfo.setId(BOSUuid.create((new ScheduleTaskBizTypeInfo()).getBOSType()));
					scheduleTaskBizTypeInfo.setBizType(rESchTemplateTaskBizTypeInfo.getBizType());
					fDCScheduleTaskInfo.getBizType().add(scheduleTaskBizTypeInfo);
				}

				/* ���ù��� */
				fDCScheduleTaskInfo.setEffectTimes(BigDecimal.valueOf(rESchTemplateTaskInfo.getReferenceDay()));
				/* ���óɹ���� */
				fDCScheduleTaskInfo.setAchievementType(rESchTemplateTaskInfo.getAchievementType());
				/* ���ñ�׼���� */
				fDCScheduleTaskInfo.setStandardTask(rESchTemplateTaskInfo.getStandardTask());
				/* ���ñ�ע */
				fDCScheduleTaskInfo.setDescription(rESchTemplateTaskInfo.getDescription());
				/* ���ý������� */
				fDCScheduleTaskInfo.setSchedule(this.editData);
				/* ���ñ��� */
				fDCScheduleTaskInfo.setNumber(rESchTemplateTaskInfo.getNumber());
				/* ���ó����� */
				fDCScheduleTaskInfo.setLongNumber(rESchTemplateTaskInfo.getLongNumber());
				/* ���ü��� */
				fDCScheduleTaskInfo.setLevel(rESchTemplateTaskInfo.getLevel());
				/* �����Ƿ����ӽڵ� */
				fDCScheduleTaskInfo.setIsLeaf(rESchTemplateTaskInfo.isIsLeaf());
				fDCScheduleTaskCollection.add(fDCScheduleTaskInfo);

				sMap.put(rESchTemplateTaskInfo.getId().toString(), fDCScheduleTaskInfo);
				if (null != rESchTemplateTaskInfo.getParent() && null != rESchTemplateTaskInfo.getParent().getId()
						&& !"".equals(rESchTemplateTaskInfo.getParent().getId().toString().trim())) {
					pMap.put(fDCScheduleTaskInfo.getId().toString(), rESchTemplateTaskInfo.getParent().getId());
				}
				handlerProcessorMap.put(rESchTemplateTaskInfo.getId().toString(), fDCScheduleTaskInfo);
			}

			CoreBaseCollection newDependTaskCollection = new CoreBaseCollection();
			CoreBaseCollection newTaskCollection = new CoreBaseCollection();
			/* ��ֵ */
			for (int j = 0; j < fDCScheduleTaskCollection.size(); j++) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = fDCScheduleTaskCollection.get(j);

				Object pObject = pMap.get(fDCScheduleTaskInfo.getId().toString());
				/* �ж��Ƿ���ڸ��ڵ� */
				if (null != pObject && !"".equals(pObject.toString().trim())) {
					Object sObject = sMap.get(pObject.toString().trim());
					if (null != sObject && sObject instanceof FDCScheduleTaskInfo) {
						FDCScheduleTaskInfo dCScheduleTaskInfo = (FDCScheduleTaskInfo) sObject;
						fDCScheduleTaskInfo.setParent(dCScheduleTaskInfo);
					}
				}
				fDCScheduleTaskInfo.setWbs(createWBS(fDCScheduleTaskInfo, fDCScheduleTaskInfo.getParent()));
				fDCScheduleTaskInfo.setExpand(true);
				fDCScheduleTaskInfo.setCalendar(this.editData.getCalendar());
				fDCScheduleTaskInfo.setEffectTimes(BigDecimal.valueOf(0));
				fDCScheduleTaskInfo.setDuration(1);
				fDCScheduleTaskInfo.setNatureTimes(BigDecimal.valueOf(0));
				fDCScheduleTaskInfo.setIsScheduleTask(true);
				fDCScheduleTaskInfo.setPriority(1);
				fDCScheduleTaskInfo.setIsAdd(true);
				if (null != fDCScheduleTaskInfo.getWbs()) {
					fDCScheduleTaskInfo.setLocked(fDCScheduleTaskInfo.getWbs().isIsLocked());
					wbsCoreBaseCollection.add(fDCScheduleTaskInfo.getWbs());
				}

				/* ����ǰ������ */
				processDepends(fDCScheduleTaskInfo, rESchTemplateTaskCollection.get(j), handlerProcessorMap, newDependTaskCollection);

				newTaskCollection.add(fDCScheduleTaskInfo);
				this.editData.getTaskEntrys().add(fDCScheduleTaskInfo);
			}

			// FDCScheduleTaskFactory.getRemoteInstance().importTasks(
			// currentAllTaskCollection, newTaskCollection,
			// currentWbsCollection,
			// wbsCoreBaseCollection, currentDependTaskCollection,
			// newDependTaskCollection);

			/* ˢ�±� */
			/* ���¼������� */
			// setDataObject(this.editData);
			// load2Gantt(this.editData);
			// this.setInitEnd(false);
			// setMessageText("��" + newTaskCollection.size() + "�����ݱ��ɹ����룡");
			// showMessage();
			// weakOnShow();
		} catch (Exception e) {
			// logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private void processDepends(FDCScheduleTaskInfo fDCScheduleTaskInfo, RESchTemplateTaskInfo rESchTemplateTaskInfo, Map handlerProcessorMap,
			CoreBaseCollection newDependTaskCollection) {
		FDCScheduleTaskDependCollection depends = fDCScheduleTaskInfo.getDependEntrys();

		RESchTemplateTaskPredecessorCollection rESchTemplateTaskPredecessorCollection = rESchTemplateTaskInfo.getPredecessors();
		for (int k = 0; k < rESchTemplateTaskPredecessorCollection.size(); k++) {
			RESchTemplateTaskPredecessorInfo rESchTemplateTaskPredecessorInfo = rESchTemplateTaskPredecessorCollection.get(k);

			RESchTemplateTaskInfo schTemplateTaskInfo = rESchTemplateTaskPredecessorInfo.getPredecessorTask();
			if (null == schTemplateTaskInfo) {
				continue;
			}
			Object proceObj = handlerProcessorMap.get(schTemplateTaskInfo.getId().toString());
			if (null == proceObj) {
				continue;
			}

			FDCScheduleTaskDependInfo depend = new FDCScheduleTaskDependInfo();
			depends.add(depend);
			// ���õ�ǰ����
			depend.setTask(fDCScheduleTaskInfo);
			// ����ǰ������
			depend.setDependTask((FDCScheduleTaskInfo) handlerProcessorMap.get(schTemplateTaskInfo.getId().toString()));
			// ���ô�ӹ�ϵ
			depend.setType(rESchTemplateTaskPredecessorInfo.getPredecessorType());
			// ���ô������
			depend.setDifference(rESchTemplateTaskPredecessorInfo.getDifferenceDay());
			depend.setHardness(DependHardnessEnum.Rubber);
			newDependTaskCollection.add(depend);
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
