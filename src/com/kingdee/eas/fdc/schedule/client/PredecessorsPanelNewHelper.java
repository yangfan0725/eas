/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sourceforge.ganttproject.cache.ActivityCache;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyCollectionMutator;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyConstraint;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyException;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishStartConstraintImpl;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleHelper;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;
import com.kingdee.eas.util.SysUtil;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������
 * 
 * @author �ź���
 * @version EAS7.0
 * @createDate 2011-8-20
 * @see
 */

public class PredecessorsPanelNewHelper implements ITaskPanelHelper {

	private FDCScheduleTaskPropertiesNewUI taskProperties;

	private final KDTask selectTask;

	private TaskDependencyCollectionMutator myMutator;
	
	private Set alreadyDependTask = new HashSet();

	public PredecessorsPanelNewHelper(FDCScheduleTaskPropertiesNewUI taskProperties) {
		this.taskProperties = taskProperties;
		selectTask = taskProperties.getSelectedTask();
		myMutator = selectTask.getManager().getDependencyCollection().createMutator();
	}

	/**
	 * @description
	 * @author �ź���
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void commit() {
		verifyRequired();
		this.myMutator.commit();
	}
	
	/**
	 * У���
	 */
	private void verifyRequired() {
		int rowNums = taskProperties.kDTablePredecessor.getRowCount();
		for (int i = 0; i < rowNums; i++) {
			IRow row = taskProperties.kDTablePredecessor.getRow(i);
			Object number = row.getCell("number").getValue();
			Object name = row.getCell("name").getValue();
			Object linkType = row.getCell("linkType").getValue();
			Object diff = row.getCell("diff").getValue();
			if (FDCHelper.isEmpty(number)) {
				FDCMsgBox.showWarning(taskProperties, "ǰ������ҳǩ����" + (i + 1)
						+ "�С�ǰ��������롯����Ϊ�ա�");
				SysUtil.abort();
			} else if (FDCHelper.isEmpty(name)) {
				FDCMsgBox.showWarning(taskProperties, "ǰ������ҳǩ����" + (i + 1)
						+ "�С�ǰ���������ơ�����Ϊ�ա�");
				SysUtil.abort();
			} else if (FDCHelper.isEmpty(linkType)) {
				FDCMsgBox.showWarning(taskProperties, "ǰ������ҳǩ����" + (i + 1)
						+ "�С���ӹ�ϵ������Ϊ�ա�");
				SysUtil.abort();
			} else if (FDCHelper.isEmpty(diff)) {
				FDCMsgBox.showWarning(taskProperties, "ǰ������ҳǩ����" + (i + 1)
						+ "�С����ʱ�䡯����Ϊ�ա�");
				SysUtil.abort();
			}
		}
	}

	/**
	 * @description
	 * @author �ź���
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void load() throws EASBizException, BOSException {
		taskProperties.kDTablePredecessor.checkParsed();
		initPredecessorsTable();
		loadPreTask();
	}

	/**
	 * @description ��ʼ��ǰ�������
	 * @author �ź���
	 * @createDate 2011-8-18 void
	 * @version EAS7.0
	 * @see
	 */
	private void initPredecessorsTable() {
		KDBizPromptBox f7 = getPreTaskNumberF7();
		Set filterIdSet = new HashSet();
		// ���Լ����¼��ų�
		ScheduleBaseInfo info = eliminateOneselfAndSubordinate(filterIdSet);
		ScheduleHelper.getAllRootIDs((FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo(), filterIdSet);
		// �����������
		final F7ScheduleTaskPromptBox selectorBox = buildpreTaskNumber(f7, filterIdSet, info);
		addSelectorListenerForTaskNumberF7(f7, selectorBox);
		f7.addDataChangeListener(new DataChangeListener() {

			public void dataChanged(DataChangeEvent eventObj) {
				if (eventObj.getNewValue() != null) {
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) eventObj.getNewValue();
					if (!task.isIsLeaf()) {
						FDCMsgBox.showError(taskProperties, "����ѡ�����ϸ������Ϊǰ������");
						((KDBizPromptBox) eventObj.getSource()).setValue(null);
					}
				}

			}
		});
		// ��ӹ�ϵ
		initLinkType();
		// ���ʱ��
		initLinkTimes();
		addKDTEditListenerForPreTaskTab();
		initAddBtn();
		initDelBtn();
		if (!selectTask.getScheduleTaskInfo().isIsLeaf()) {
			taskProperties.btnAdd.setVisible(false);
			taskProperties.btnDel.setVisible(false);
		}
		
		taskProperties.kDContainer1.addButton(taskProperties.btnAdd);
		taskProperties.kDContainer1.addButton(taskProperties.btnDel);
	}

	/**
	 * @description ����ǰ������������
	 * @author �ź���
	 * @createDate 2011-8-18 void
	 * @version EAS7.0
	 * @see
	 */

	private void loadPreTask() {
		taskProperties.kDTablePredecessor.removeRows();
		TaskDependency[] dependencies = selectTask.getDependenciesAsDependant().toArray();
		for (int i = 0; i < dependencies.length; i++) {
			TaskDependency dependency = dependencies[i];
			IRow row = taskProperties.kDTablePredecessor.addRow();
			row.setUserObject(dependency);
			KDTask dependee = (KDTask) dependency.getDependee();// ǰ������
			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) dependee.getScheduleTaskInfo();
			row.getCell("number").setValue(taskInfo);
			row.getCell("name").setValue(taskInfo.getName());
			row.getCell("linkType").setValue(ScheduleParserHelper.getTaskLinkTypeByConstraint(dependency.getConstraint()));
			row.getCell("diff").setValue(new Integer(dependency.getDifference()));
		}
	}

	/**
	 * 
	 * @description ����ǰ���������
	 * @author �ź���
	 * @createDate 2011-8-18
	 * @param f7
	 * @param filterIdSet
	 * @param info
	 * @return F7ScheduleTaskPromptBox
	 * @version EAS7.0
	 * @see
	 */
	private F7ScheduleTaskPromptBox buildpreTaskNumber(KDBizPromptBox f7, Set filterIdSet, ScheduleBaseInfo info) {
		final F7ScheduleTaskPromptBox selectorBox = new F7ScheduleTaskPromptBox(taskProperties, info, filterIdSet, (FDCScheduleTaskInfo) selectTask
				.getScheduleTaskInfo());
		f7.setSelector(selectorBox);
		ObjectValueRender renderer = new ObjectValueRender();
		renderer.setFormat(new BizDataFormat("$number$"));
		taskProperties.kDTablePredecessor.getColumn("number").setEditor(new KDTDefaultCellEditor(f7));
		taskProperties.kDTablePredecessor.getColumn("number").setRenderer(renderer);
		taskProperties.kDTablePredecessor.getColumn("number").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		taskProperties.kDTablePredecessor.getColumn("name").getStyleAttributes().setLocked(true);
		return selectorBox;
	}

	/**
	 * 
	 * @description ��ǰ������������ѡ�������
	 * @author �ź���
	 * @createDate 2011-8-18
	 * @param f7
	 * @param selectorBox
	 * @return void
	 * @version EAS7.0
	 * @see
	 */
	private void addSelectorListenerForTaskNumberF7(KDBizPromptBox f7, final F7ScheduleTaskPromptBox selectorBox) {
		f7.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				Set filterIdSet = new HashSet();
				// ���Լ��ų�
				filterIdSet.add(selectTask.getScheduleTaskInfo().getId().toString());
				// ����ѡ���ǰ�������ų�
				eliminateSelectPreTask(selectorBox, filterIdSet);
			}
		});
	}

	/**
	 * 
	 * @description ǰ���������F7
	 * @author �ź���
	 * @createDate 2011-8-18
	 * @return KDBizPromptBox
	 * @version EAS7.0
	 * @see
	 */
	private KDBizPromptBox getPreTaskNumberF7() {
		KDBizPromptBox f7 = new KDBizPromptBox();
		f7.setDisplayFormat("$number$");
		f7.setEditFormat("$number$");
		f7.setCommitFormat("$number$");
		return f7;
	}

	/**
	 * 
	 * @description �ų��Լ����¼�
	 * @author �ź���
	 * @createDate 2011-8-18
	 * @param filterIdSet
	 * @return ScheduleBaseInfo
	 * @version EAS7.0
	 * @see
	 */
	private ScheduleBaseInfo eliminateOneselfAndSubordinate(Set filterIdSet) {
		filterIdSet.add(selectTask.getScheduleTaskInfo().getId().toString());
		ScheduleBaseInfo info = selectTask.getScheduleTaskInfo().getScheduleBase();
		if (!selectTask.getScheduleTaskInfo().isIsLeaf()) {
			String mySelfLongNumber = selectTask.getScheduleTaskInfo().getLongNumber();
			for (Iterator iter = info.getScheduleTasks().iterator(); iter.hasNext();) {
				ScheduleTaskBaseInfo task = (ScheduleTaskBaseInfo) iter.next();
				if (task.getLongNumber() != null && task.getLongNumber().indexOf(mySelfLongNumber + "!") > -1) {
					filterIdSet.add(task.getId().toString());
				}
			}
		}
		return info;
	}

	/**
	 * 
	 * @description ����ѡ���ǰ�������ų�
	 * @author �ź���
	 * @createDate 2011-8-18
	 * @param selectorBox
	 * @param filterIdSet
	 * @return void
	 * @version EAS7.0
	 * @see
	 */
	private void eliminateSelectPreTask(final F7ScheduleTaskPromptBox selectorBox, Set filterIdSet) {
		for (int i = 0; i < taskProperties.kDTablePredecessor.getRowCount(); i++) {
			if (taskProperties.kDTablePredecessor.getCell(i, "number").getValue() != null) {
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) taskProperties.kDTablePredecessor.getCell(i, "number").getValue();
				filterIdSet.add(taskInfo.getId().toString());
			}
		}
		ScheduleBaseInfo info = selectTask.getScheduleTaskInfo().getScheduleBase();
		if (!selectTask.getScheduleTaskInfo().isIsLeaf()) {
			String mySelfLongNumber = selectTask.getScheduleTaskInfo().getLongNumber();
			for (Iterator iter = info.getScheduleTasks().iterator(); iter.hasNext();) {
				ScheduleTaskBaseInfo task = (ScheduleTaskBaseInfo) iter.next();
				if (task.getLongNumber() != null && task.getLongNumber().indexOf(mySelfLongNumber + "!") > -1) {
					filterIdSet.add(task.getId().toString());
				}
			}
		}

		for (int i = 0; i < taskProperties.kDTablePredecessor.getRowCount(); i++) {
			IRow row = taskProperties.kDTablePredecessor.getRow(i);
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) row.getCell("number").getUserObject();
			if (task != null) {
				filterIdSet.add(task.getId().toString());
			}
		}
		F7ScheduleTaskUI f7ScheduleTaskUI = selectorBox.getF7ScheduleTaskUI();
		if (f7ScheduleTaskUI != null) {
			f7ScheduleTaskUI.setFilterTask(filterIdSet);
			f7ScheduleTaskUI.setCurTask((FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo());
		}
	}

	/**
	 * 
	 * @description ��ʼ����ӹ�ϵ
	 * @author �ź���
	 * @createDate 2011-8-18 void
	 * @version EAS7.0
	 * @see
	 */
	private void initLinkType() {
		KDComboBox linkBox = new KDComboBox();
		linkBox.addItems(TaskLinkTypeEnum.getEnumList().toArray());
		taskProperties.kDTablePredecessor.getColumn("linkType").setEditor(new KDTDefaultCellEditor(linkBox));
		taskProperties.kDTablePredecessor.getColumn("linkType").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
	}

	/**
	 * 
	 * @description ��ʼ���ʱ��
	 * @author �ź���
	 * @createDate 2011-8-18 void
	 * @version EAS7.0
	 * @see
	 */
	private void initLinkTimes() {
		KDFormattedTextField linkTimes = new KDFormattedTextField();
		linkTimes.setDataType(KDFormattedTextField.INTEGER_TYPE);
		linkTimes.setPrecision(0);
		linkTimes.setRemoveingZeroInDispaly(true);
		linkTimes.setRemoveingZeroInEdit(true);
		taskProperties.kDTablePredecessor.getColumn("diff").setEditor(new KDTDefaultCellEditor(linkTimes));
		taskProperties.kDTablePredecessor.getColumn("diff").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
	}

	private void addKDTEditListenerForPreTaskTab() {
		taskProperties.kDTablePredecessor.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}
		});
	}

	private void table_editStopped(KDTEditEvent e) {
		IRow row = taskProperties.kDTablePredecessor.getRow(e.getRowIndex());
		TaskDependency dependency = (TaskDependency) row.getUserObject();
		Object value = e.getValue();
		
		if (taskProperties.kDTablePredecessor.getColumnIndex("number") == e
				.getColIndex()) {
			// ɾ���ɵģ��½��µ�
			// ��ϵ����
			try {
				if (dependency != null) {
					dependency.delete();
				}
				FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) value;
				if (task != null) {
					KDTask newTask = taskProperties.scheduleGanttProject
							.getKDTaskById(task.getId().toString());
					dependency = createDependency(dependency, newTask);
					row.setUserObject(dependency);
					row.getCell("name").setValue(task.getName());
					
					/* modified by zhaoqin for R140403-0180 on 2014/05/26 */
					//row.getCell("diff").setValue("1");
					row.getCell("diff").setValue(FDCHelper.ZERO);
				} else {
					taskProperties.kDTablePredecessor.removeRow(e.getRowIndex());
				}
			} catch (TaskDependencyException e1) {
				taskProperties.handUIException(e1);
			}
			this.myMutator.commit();
		}
		if (taskProperties.kDTablePredecessor.getColumnIndex("linkType") == e
				.getColIndex()) {
			try {
				if (dependency != null) {
					dependency.setConstraint(ScheduleParserHelper.getTaskDependencyConstraintByLinkType((TaskLinkTypeEnum) value));
				}
				FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) taskProperties.kDTablePredecessor
						.getCell(e.getRowIndex(), "number").getValue();
				if (task != null) {
					KDTask newTask = taskProperties.scheduleGanttProject
							.getKDTaskById(task.getId().toString());
					if (dependency == null) {
						dependency = createDependency(dependency, newTask);
						row.setUserObject(dependency);
					}
					int diff = row.getCell("diff").getValue() == null ? 0 : Integer.parseInt(row.getCell("diff").getValue() + "");
					if (value != null) {
						if (value instanceof BigDecimal) {
							diff = ((BigDecimal) value).intValue();
						} else if (value instanceof Integer) {
							diff = ((Integer) value).intValue();
						}
					}
					dependency.setDifference(diff);
					TaskLinkTypeEnum type = (TaskLinkTypeEnum) row.getCell(
							"linkType").getValue();
					if (type != null) {
//						computeTask(task, type, diff);
						// computeTask(task, type, diff);
						// �˴�ȡRecalculateTaskScheduleAlgorithm�������ֵ�����ڻ��Զ���
						Date changedStart = this.taskProperties.selectTask.getStart().getTime();
						Date changedEnd = this.taskProperties.selectTask.getEnd().getTime();
						taskProperties.pkPlanStart.setValue(changedStart, false);
						taskProperties.pkPlanEnd.setValue(changedEnd, false);
					}
				}
			} catch (TaskDependencyException e1) {
				taskProperties.handUIException(e1);
			}
			
			
		}
		//��ӷ�ʽ����ʱһ����Ҫ���¼�����ʼʱ��
		if (taskProperties.kDTablePredecessor.getColumnIndex("diff") == e.getColIndex() ||
				/* modified by zhaoqin for R140403-0180 on 2014/05/26 */
				taskProperties.kDTablePredecessor.getColumnIndex("number") == e.getColIndex()) {
			
			// if (taskProperties.kDTablePredecessor.getRowCount() > 1) {
			// FDCMsgBox.showWarning(taskProperties,
			// "���ڶ����ӹ�ϵ���ƣ���ֱ�ӵ��ڼƻ���ʼ���������ڡ�");
			// row.getCell("diff").setValue(e.getOldValue());
			// return;
			// }
			try {
				FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) taskProperties.kDTablePredecessor.getCell(e.getRowIndex(), "number")
						.getValue();
				if (task != null) {
					KDTask newTask = taskProperties.scheduleGanttProject.getKDTaskById(task.getId().toString());
					if (dependency == null) {
						dependency = createDependency(dependency, newTask);
						row.setUserObject(dependency);
					}
					int diff = 0;
					if (value != null) {
						if (value instanceof BigDecimal) {
							diff = ((BigDecimal) value).intValue();
						} else if (value instanceof Integer) {
							diff = ((Integer) value).intValue();
						}
					}
					dependency.setDifference(diff);
					TaskLinkTypeEnum type = (TaskLinkTypeEnum) row.getCell("linkType").getValue();
					if (type != null) {
						// computeTask(task, type, diff);
						// �˴�ȡRecalculateTaskScheduleAlgorithm�������ֵ�����ڻ��Զ���
						Date changedStart = this.taskProperties.selectTask.getStart().getTime();
						Date changedEnd = this.taskProperties.selectTask.getEnd().getTime();
						taskProperties.pkPlanStart.setValue(changedStart, false);
						taskProperties.pkPlanEnd.setValue(changedEnd, false);
					}
				}
			} catch (TaskDependencyException e1) {
				taskProperties.handUIException(e1);
			}
		}
	}
	// TODO������ȡ
	protected List getTaskDependencyTask() {
		List preTaskList = new ArrayList();
		for (int i = 0; i < taskProperties.kDTablePredecessor.getRowCount(); i++) {
			IRow row = taskProperties.kDTablePredecessor.getRow(i);
			TaskDependency dependency = (TaskDependency) row.getUserObject();
			if (dependency == null) {
				continue;
			}
			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) row.getCell("number").getValue();
			if (taskInfo == null) {
				continue;
			}

			TaskLinkTypeEnum type = (TaskLinkTypeEnum) row.getCell("linkType").getValue();
			int diff = Integer.parseInt(row.getCell("diff").getValue().toString());
			preTaskList.add(new Object[] { dependency, taskInfo, type, diff });

		}
		return preTaskList;
	}

	protected TaskLinkTypeEnum getCurrentTaskLinkType() {
		TaskLinkTypeEnum linkType = null;
		Object[] tasksInfo = null;
		List preTaskList = getTaskDependencyTask();
		int size = preTaskList.size();
		if (size == 1) {
			return (TaskLinkTypeEnum) ((Object[]) preTaskList.get(0))[2];
		}

		Date date = null;
		FDCScheduleTaskInfo baseTask = null;
		FDCScheduleTaskInfo pretask = null;
		BigDecimal diff = FDCHelper.ZERO;
		Date nextDate = null;
		int baseIndex = -1;
		for (int i = 0; i < size; i++) {
			tasksInfo = (Object[]) preTaskList.get(i);
			pretask = (FDCScheduleTaskInfo) tasksInfo[1];
			linkType = (TaskLinkTypeEnum) tasksInfo[2];
			if (tasksInfo[3] != null) {
				diff = FDCHelper.toBigDecimal(tasksInfo[3]);
			}

			if (linkType.equals(TaskLinkTypeEnum.FINISH_FINISH) || linkType.equals(TaskLinkTypeEnum.FINISH_START)) {
				Date preTaskEndDate = pretask.getEnd();
				nextDate = ScheduleCalendarHelper.getEndDate(preTaskEndDate, diff, pretask.getCalendar());
			} else if (linkType.equals(TaskLinkTypeEnum.START_START)) {
				Date preTaskStartDate = pretask.getStart();
				nextDate = ScheduleCalendarHelper.getEndDate(preTaskStartDate, diff, pretask.getCalendar());
			}
			if (date == null) {
				date = nextDate;
			}
			if (date.compareTo(nextDate) < 0) {
				date = nextDate;
				baseIndex = i;
			}
		}
		if (baseIndex > 0) {
			return (TaskLinkTypeEnum) ((Object[]) preTaskList.get(baseIndex))[2];
		}

		return linkType;
	}
	
	
	/**
	 * ���ʱ��ı䣬���㵱������Ŀ�ʼ����
	 * 
	 * @param task
	 * @param type
	 * @param diff
	 */
	protected void computeTask(FDCScheduleTaskInfo task, TaskLinkTypeEnum type,
			int diff) {
		ScheduleCalendarInfo calendarInfo = (ScheduleCalendarInfo) taskProperties.prmtTaskCalendar
				.getValue();
		if (calendarInfo == null) {
			return;
		}
		getCurrentTaskLinkType();
	
		Date oldEndDate = (Date) taskProperties.pkPlanEnd.getValue();
		calendarInfo = ActivityCache.getInstance().getCalendar(
				calendarInfo.getId().toString());
		if (TaskLinkTypeEnum.FINISH_START.equals(type)) {
			Date preF = task.getPlanEnd();
//			Date nxtS = ScheduleCalendarHelper.getEndDate(preF, new BigDecimal(
//					diff + 2), calendarInfo);
			// Date nxtS = ScheduleCalendarHelper.getEndDate(preF, new
			// BigDecimal(
			// diff>=0?diff+2:diff ), calendarInfo);
			Date nxtS = ScheduleCalendarHelper.getEndDate(preF, new BigDecimal(diff + 1), calendarInfo);
			taskProperties.pkPlanStart.setValue(nxtS, false);
			BigDecimal dur = taskProperties.txtWorkDay.getBigDecimalValue();
			Date nxtF = ScheduleCalendarHelper.getEndDate(nxtS, dur.subtract(FDCHelper.ONE),
					calendarInfo);
			taskProperties.pkPlanEnd.setValue(nxtF, false);
			if (taskProperties.txtYes.isSelected()) {
				Date checkDate = (Date) taskProperties.pkAccessDate.getValue();
				if (checkDate == null || checkDate.compareTo(oldEndDate) == 0) {
					taskProperties.pkAccessDate.setValue(nxtF);
				}
			}
		} else if (TaskLinkTypeEnum.START_START.equals(type)) {
			Date preS = task.getPlanStart();
			Date nxtS = ScheduleCalendarHelper.getEndDate(preS, new BigDecimal(
					diff), calendarInfo);
			taskProperties.pkPlanStart.setValue(nxtS, false);
			BigDecimal dur = taskProperties.txtWorkDay.getBigDecimalValue();
			Date nxtF = ScheduleCalendarHelper.getEndDate(nxtS, dur.subtract(FDCHelper.ONE),
					calendarInfo);
			taskProperties.pkPlanEnd.setValue(nxtF, false);
			if (taskProperties.txtYes.isSelected()) {
				Date checkDate = (Date) taskProperties.pkAccessDate.getValue();
				if (checkDate == null || checkDate.compareTo(oldEndDate) == 0) {
					taskProperties.pkAccessDate.setValue(nxtF);
				}
			}
		} else if (TaskLinkTypeEnum.FINISH_FINISH.equals(type)) {
			Date preF = task.getPlanEnd();
			Date nxtF = ScheduleCalendarHelper.getEndDate(preF, FDCHelper.toBigDecimal(diff), calendarInfo);
			taskProperties.pkPlanEnd.setValue(nxtF, false);
			BigDecimal dur = taskProperties.txtWorkDay.getBigDecimalValue();
			Date nxtS = ScheduleCalendarHelper.getStartDate(nxtF, dur.subtract(FDCHelper.ONE),
					calendarInfo);
			taskProperties.pkPlanStart.setValue(nxtS, false);
			if (taskProperties.txtYes.isSelected()) {
				Date checkDate = (Date) taskProperties.pkAccessDate.getValue();
				if (checkDate == null || checkDate.compareTo(oldEndDate) == 0) {
					taskProperties.pkAccessDate.setValue(nxtF);
				}
			}
		}
	}

	/**
	 * 
	 * @description
	 * @author �ź���
	 * @createDate 2011-8-18
	 * @param dependency
	 * @param newTask
	 * @return TaskDependency
	 * @throws TaskDependencyException
	 * @version EAS7.0
	 * @see
	 */
	private TaskDependency createDependency(TaskDependency dependency, KDTask newTask) throws TaskDependencyException {
		TaskDependencyConstraint constraint = null;
		if (dependency != null) {
			constraint = dependency.getConstraint();
		} else {
			constraint = new FinishStartConstraintImpl();
		}
			
		TaskDependency dep = myMutator.createDependency(selectTask, newTask, constraint);
		if (dependency != null) {
			dep.setDifference(dependency.getDifference());
		}
		return dep;
	}

	private void initDelBtn() {
		taskProperties.btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionDelete_Performed(e);
			}
		});
	}

	private void initAddBtn() {
		taskProperties.btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionAdd_Performed(e);
			}
		});
	}

	/**
	 * 
	 * @description ǰ����������¼�
	 * @author �ź���
	 * @createDate 2011-8-18
	 * @param e
	 * @version EAS7.0
	 * @see
	 */
	private void actionAdd_Performed(ActionEvent e) {
		IRow row = taskProperties.kDTablePredecessor.addRow();
		row.getCell("diff").setValue(FDCHelper.ZERO);
		row.getCell("linkType").setValue(TaskLinkTypeEnum.FINISH_START);
	}

	/**
	 * 
	 * @description ǰ������ɾ���¼�
	 * @author �ź���
	 * @createDate 2011-8-18
	 * @param e
	 * @version EAS7.0
	 * @see
	 */
	private void actionDelete_Performed(ActionEvent e) {
		IRow row = FDCTableHelper.getSelectedRow(taskProperties.kDTablePredecessor);
		if (row == null) {
			FDCMsgBox.showInfo("����ѡ����");
			SysUtil.abort();
		}
		if (row.getUserObject() != null) {
			TaskDependency dependency = (TaskDependency) row.getUserObject();
			myMutator.deleteDependency(dependency);
			dependency.delete();
		}
		taskProperties.kDTablePredecessor.removeRow(row.getRowIndex());
	}

	/**
	 * @description
	 * @author �ź���
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void setEditUIStatus() {
		// TODO Auto-generated method stub

	}

	/**
	 * @description
	 * @author �ź���
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void setExecutingUIStatus() {
		// TODO Auto-generated method stub

	}

	/**
	 * @description
	 * @author �ź���
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void setViewUIStatus() {
		// TODO Auto-generated method stub

	}

}
