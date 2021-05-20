package com.kingdee.eas.fdc.schedule.framework.ext;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;

public class ScheduleTaskChangeHelper {
	static private Map innerMap = new HashMap();

	public static void addChangeTask(KDTask task) {
		ScheduleTaskBaseInfo oldTaskInfo = task.getScheduleTaskInfo();
		ScheduleTaskBaseInfo taskInfo = new ScheduleTaskBaseInfo();
		/*
		 * taskInfo.setStart(oldTaskInfo.getStart());
		 * taskInfo.setEnd(oldTaskInfo.getEnd());
		 * taskInfo.setDuration(oldTaskInfo.getDuration());
		 * taskInfo.setIsScheduleTask(oldTaskInfo.isScheduleTask());
		 */taskInfo.putAll(oldTaskInfo);
		getChangeMap(task).put(task, taskInfo);
	}

	/**
	 * �ڸ��²�����Ҫ��ʱ�������������û���
	 * 
	 * @param task
	 */
	public static void resetChangeTask(KDTask task) {
		Map changeMap = getChangeMap(task);
		for (Iterator iter = changeMap.keySet().iterator(); iter.hasNext();) {
			KDTask changeTask = (KDTask) iter.next();
			ScheduleTaskBaseInfo changeTaskInfo = (ScheduleTaskBaseInfo) changeMap.get(changeTask);
			changeTask.setStart(ScheduleParserHelper.parseDateToGanttCalendar(changeTaskInfo.getStart()));
			changeTask.setLength(changeTaskInfo.getDuration());
		}
	}

	/**
	 * ���������ϼ��ƻ�������
	 * 
	 * @param task
	 */
	public static void resetNoScheduleTask(KDTask task) {
		Map changeMap = getChangeMap(task);
		for (Iterator iter = changeMap.keySet().iterator(); iter.hasNext();) {
			KDTask changeTask = (KDTask) iter.next();
			ScheduleTaskBaseInfo changeTaskInfo = (ScheduleTaskBaseInfo) changeMap.get(changeTask);
			if (changeTaskInfo.isScheduleTask()) {
				continue;
			}
			changeTask.setStart(ScheduleParserHelper.parseDateToGanttCalendar(changeTaskInfo.getStart()));
			changeTask.setLength(changeTaskInfo.getDuration());
		}
	}

	public static void clearChange(KDTask task) {
		Map changeMap = getChangeMap(task);
		changeMap.clear();
	}

	public static Map getChangeMap(KDTask task) {
		BOSUuid scheduleId = task.getScheduleTaskInfo().getScheduleBase().getId();
		Map changeMap = (HashMap) innerMap.get(scheduleId);
		if (changeMap == null) {
			changeMap = new HashMap();
			innerMap.put(scheduleId, changeMap);
		}
		return changeMap;
	}

	public static void handleAfterTaskChange(KDTask task) {
		Map changeMap = ScheduleTaskChangeHelper.getChangeMap(task);
		Collection values = changeMap.values();
		boolean cantUpdate = false;
		for (Iterator iter = values.iterator(); iter.hasNext();) {
			ScheduleTaskBaseInfo taskInfo = (ScheduleTaskBaseInfo) iter.next();
			if (!taskInfo.isScheduleTask()) {
				continue;
			}
			if (taskInfo.getStart() != null && taskInfo.getBoundStart() != null) {
				if (taskInfo.getStart().compareTo(taskInfo.getBoundStart()) < 0) {
					FDCMsgBox.showWarning(FDCClientHelper.getCurrentActiveWindow(), "������Ŀ�ʼʱ�������ϼ���ʱ��");
					cantUpdate = true;
					break;
				}
			}

			if (taskInfo.getEnd() != null && taskInfo.getBoundEnd() != null) {
				if (taskInfo.getEnd().compareTo(taskInfo.getBoundEnd()) > 0) {
					FDCMsgBox.showWarning(FDCClientHelper.getCurrentActiveWindow(), "��������������ʱ�䳬�����ϼ�����Ľ���ʱ��");
					cantUpdate = true;
					break;
				}
			}
		}

		if (cantUpdate) {
			// ��������µ�����
			ScheduleTaskChangeHelper.resetChangeTask(task);
		} else {
			// ���ϼ��������޸ģ�����ϼ��ƻ��ı䣬�����Ļ��� //TODO N Ӧ���������޸�ǰ����TaskImp .commit
			ScheduleTaskChangeHelper.resetNoScheduleTask(task);
		}

		ScheduleTaskChangeHelper.clearChange(task);
	}

	/**
	 * �ж�����ڵ��Ƿ���Ե���
	 * 
	 * @param task
	 * @param event
	 * @return
	 */
	public static boolean isTaskAdjustable(FDCScheduleTaskInfo task, KDTaskAdjustEvent event) {
		if (event == null || task == null) {
			return false;
		}
		ScheduleCalendarInfo calendar = task.getCalendar();
		if (calendar == null) {
			calendar = new ScheduleCalendarInfo();
			task.getSchedule().setCalendar(calendar);
		}
		switch (event.getChangeType()) {
		case KDTaskAdjustEvent.CHANGETYPE_STARTDATE:
			task.setDate("tempStartDate", event.getStartDate());
			task.setDate("tempEndDate", ScheduleCalendarHelper.getEndDate(event.getStartDate(), task.getEffectTimes(), calendar));
			break;
		case KDTaskAdjustEvent.CHANGETYPE_ENDDATE:
			task.setDate("tempStartDate", task.getStart());
			task.setDate("tempEndDate", event.getEndDate());
			break;
		case KDTaskAdjustEvent.CHANGETYPE_DURATION:
			if (task.getStart() != null) {
				task.setDate("tempStartDate", task.getStart());
//				TaskDependency dependency = GanttTreeTableModel.getCurrentTaskDependency((Task) event.getSource());
//				 if (dependency == null) {
					task.setDate("tempEndDate", ScheduleCalendarHelper.getEndDate(task.getStart(), new BigDecimal(String.valueOf(event
							.getDuration())), calendar));
//				 } else {
//					if (dependency.getConstraint() instanceof FinishFinishConstraintImpl) {
//						task.setDate("tempEndDate", ((Task) event.getSource()).getEnd().getTime());
//					} else {
//						task.setDate("tempEndDate", ScheduleCalendarHelper.getEndDate(task.getStart(), new BigDecimal(String.valueOf(event
//								.getDuration())), calendar));
//					}
				// }
				
			}

			break;
		default:
			break;
		}

		if (!_isTaskAdjustable(task)) {
			return false;
		}
		return true;
	}

	/**
	 * �ݹ��жϸýڵ���������ϼ��ڵ��Լ����ýڵ��Ƿ񳬹����Ե����ķ�Χ
	 * 
	 * @param task
	 * @return
	 */
	private static boolean _isTaskAdjustable(FDCScheduleTaskInfo task) {
		if (isOutOfBound(task)) {
			return false;
		}

		if (null == task.getSchedule()) {
			return false;
		}
		ScheduleCalendarInfo calendar = task.getCalendar();

		// parent task
		Date tempEndDate = task.getDate("tempEndDate");
		Date tempStartDate = task.getDate("tempStartDate");
		if (task.getParent() != null) {
			FDCScheduleTaskInfo parentTask = task.getParent();
			if (parentTask.getSchedule() == null) {
				parentTask.setSchedule(task.getSchedule());
			}
			// �¼��Ŀ�ʼ����ʱ����������ϼ����ϼ����п��ܳ��ֲ��ܵ���,���߿϶��ǿ��Ե�����
			if (isOutOfBound(tempStartDate, tempEndDate, parentTask.getStart(), parentTask.getEnd())) {
				if (tempStartDate.compareTo(parentTask.getStart()) < 0) {
					parentTask.setDate("tempStartDate", tempStartDate);
				} else {
					parentTask.setDate("tempStartDate", parentTask.getStart());
				}
				if (tempEndDate.compareTo(parentTask.getEnd()) < 0) {
					parentTask.setDate("tempEndDate", tempEndDate);
				} else {
					parentTask.setDate("tempEndDate", parentTask.getEnd());
				}
				boolean taskAdjustable = _isTaskAdjustable(parentTask);
				if (!taskAdjustable) {
					return false;
				}
			}
		}

		// dependTask
		for (Iterator iter = task.getDependEntrys().iterator(); iter.hasNext();) {
			FDCScheduleTaskDependInfo depend = (FDCScheduleTaskDependInfo) iter.next();
			FDCScheduleTaskInfo dependTask = depend.getDependTask();
			dependTask.setSchedule(task.getSchedule());
			if (task.getId().equals(dependTask.getId())) {
				continue;
			}
			if (depend.getType() == TaskLinkTypeEnum.FINISH_START) {
				// ���+1=��Ч����
				Date myStartDate = ScheduleCalendarHelper.getEndDate(tempEndDate, new BigDecimal(String.valueOf(depend.getDifference() + 1)),
						calendar);
				if (dependTask.getSchedule() == null) {
					dependTask.setSchedule(task.getSchedule());
				}
				dependTask.setDate("tempStartDate", myStartDate);
				dependTask.setDate("tempEndDate", ScheduleCalendarHelper.getEndDate(myStartDate, dependTask.getEffectTimes(), calendar));
				boolean taskAdjustable = _isTaskAdjustable(dependTask);
				if (!taskAdjustable) {
					return false;
				}
			}

			if (depend.getType() == TaskLinkTypeEnum.FINISH_FINISH) {
				// ���+1=��Ч����
				Date myEndDate = ScheduleCalendarHelper.getEndDate(tempEndDate, new BigDecimal(String.valueOf(depend.getDifference() + 1)), calendar);
				dependTask.setDate("tempEndDate", myEndDate);
				dependTask.setDate("tempStartDate", ScheduleCalendarHelper.getStartDate(myEndDate, dependTask.getEffectTimes(), calendar));
				boolean taskAdjustable = _isTaskAdjustable(dependTask);
				if (!taskAdjustable) {
					return false;
				}
			}

			if (depend.getType() == TaskLinkTypeEnum.START_START) {
				Date myStartDate = ScheduleCalendarHelper.getEndDate(tempStartDate, new BigDecimal(String.valueOf(depend.getDifference() + 1)),
						calendar);
				dependTask.setDate("tempStartDate", myStartDate);
				dependTask.setDate("tempEndDate", ScheduleCalendarHelper.getEndDate(myStartDate, dependTask.getEffectTimes(), calendar));
				boolean taskAdjustable = _isTaskAdjustable(dependTask);
				if (!taskAdjustable) {
					return false;
				}
			}

			// if(depend.getType()==TaskLinkTypeEnum.START_FINISH){
			// Date myEndDate = ScheduleCalendarHelper.getEndDate(tempStartDate,
			// new BigDecimal(String.valueOf(depend.getDifference()+1)),
			// calendar);
			// dependTask.setDate("tempEndDate", myEndDate);
			// dependTask.setDate("tempStartDate",
			// ScheduleCalendarHelper.getStartDate(myEndDate,
			// dependTask.getEffectTimes(), calendar));
			// boolean taskAdjustable=_isTaskAdjustable(dependTask);
			// if(!taskAdjustable){
			// return false;
			// }
			// }

		}

		return true;
	}

	private static boolean isOutOfBound(FDCScheduleTaskInfo task) {
		return isOutOfBound(task.getDate("tempStartDate"), task.getDate("tempEndDate"), task.getBoundStart(), task.getBoundEnd());
	}

	private static boolean isOutOfBound(Date startDate, Date endDate, Date startDateBound, Date endDateBound) {
		if (startDate == null || endDate == null) {
			return false;
		}
		if (startDateBound == null || endDateBound == null) {
			return false;
		}
		if (startDate.compareTo(startDateBound) < 0) {
			return true;
		}

		if (endDate.compareTo(endDateBound) > 0) {
			return true;
		}

		return false;
	}

}
