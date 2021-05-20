package com.kingdee.eas.fdc.schedule.framework.ext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttTreeTableModel;
import net.sourceforge.ganttproject.Mediator;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskLength;
import net.sourceforge.ganttproject.task.TaskNode;

import com.kingdee.eas.fdc.basedata.FDCHelper;

public class GanttTreeTableModelExt {
	public static final String strColAchievementType = "�׶��Գɹ����";
	public static String strColWBSNumber = "�������";
	public static String strColTaskType = "�������";
	public static String strColTaskName = "��������";
	public static String strColSimpleName = "��ɱ�־";
	public static String strColEffectTimes = "�ƻ�����(��)";
	public static String strColNatureTimes = "ʵ�ʹ���(��)";
	public static String strColCompletePercent = "��ɽ���(%)";
	public static String strColPrefixNode = "ǰ������";
	public static String strColActualEndDate = "ʵ���������";
	public static String strColPlanDept = "�ƻ����Ʋ���";
	public static String stradminPerson = "������";
	public static String strColAdminDept = "���β���";
	public static String strColBizType = "ҵ������";
	public static String strColIsKey = "�Ƿ�ؼ�����";
	public static String strColMeeting = "�Ƿ���̱�����";
	public static String strColDescription = "��ע";
	public static String strColState = "״̬";
	public static String strColCheckDate = "��������";
	public static String strColHelpDep = "Э������";
	public static String strColHelpPerson = "Э����";
	public static String strColPlanEvaPerson = "����������";
	public static String strColQualityEvaPerson = "����������";
	public static String strColTaskGuilde = "����ָ��";
	public static String strColPlanEvaluation = "��������״̬";
	public static String strColQualityEvaluation = "��������״̬";
	public static String strColDiffDays = "��ɲ���(��)";
	public static String strColFromOpenDays = "���õ�ʱ��(��)";
	private final GanttTreeTableModel model;
	// �ɱ༭��
	private Set canLeafEdit = new HashSet();
	private Set canEdit = new HashSet();

	public GanttTreeTableModelExt(GanttTreeTableModel model) {
		this.model = model;
		
		// ��ʱ���ţ��������ƣ����ڡ��ƻ���ʼ���ƻ���ɡ������ˡ����β��ţ�
		// �������ӿɱ༭�У���Ҫ��ʼ����Ӧeditor
		canLeafEdit = new HashSet();
		canLeafEdit.add(strColTaskName);
		canLeafEdit.add(strColEffectTimes);
		canLeafEdit.add("�ƻ���ʼ����");
		canLeafEdit.add("�ƻ��������");
		canLeafEdit.add(stradminPerson);
		canLeafEdit.add(strColAdminDept);
		canLeafEdit.add(strColSimpleName);
		/* modified by zhaoqin for R140424-0128 on 2014/05/30 start */
		//canLeafEdit.add(strColQualityEvaPerson);
		//canLeafEdit.add(strColPlanEvaPerson);
		canEdit = new HashSet();
		canEdit.add(strColTaskName);
		canEdit.add(stradminPerson);
		canEdit.add(strColAdminDept);
		canEdit.add(strColSimpleName);
		//canEdit.add(strColQualityEvaPerson);
		//canEdit.add(strColPlanEvaPerson);
		/* modified by zhaoqin for R140424-0128 on 2014/05/30 end */
	}

	public void afterChangeLanguage(GanttLanguage ganttLanguage) {
		// �޸ı�ͷ���ƽ� ���Ƹĳ� ��WBS���롱 ԭ����Ŀǰֻ�������ǿ�������չʾ�� by sxhong 2009-09-17 14:45:30
		GanttTreeTableModel.strColName = strColWBSNumber;
		GanttTreeTableModel.strColBegDate = "�ƻ���ʼ����";
		GanttTreeTableModel.strColEndDate = "�ƻ��������";
		// GanttTreeTableModel.strColDuration="��Ч����2";
	}

	/**
	 * KDTask��ĳ����Ԫ���Ƿ�ɱ༭
	 * <p>
	 * ĿǰĬ�ϡ��������ơ��������ڡ�������ʼ���������������������ˡ��������β��š��ɱ༭<br>
	 * ������ϸ����ʱ�����ڡ���ʼ���������ɱ༭<br>
	 * 
	 * @param task
	 * @param column
	 * @return
	 */
	public boolean isKDTaskCellEditable(KDTask task, int column) {
		KDTask kdTask = task;
		if (kdTask.isEditable()) {
			String columnName = model.getColumnName(column);
			if (kdTask.getScheduleTaskInfo().isIsLeaf()) {
				if (canLeafEdit.contains(columnName)) {
					return true;
				}
				return false;
			} else {
				if (canEdit.contains(columnName)) {
					return true;
				}
				return false;
			}
		}
		return false;
	}

	public boolean beforeSetValue(final Object value, final Object node, final int column) {
		if (((TaskNode) node).getUserObject() instanceof KDTask) {
			KDTask task = (KDTask) ((TaskNode) node).getUserObject();
			// ScheduleTaskChangeHelper.handleAfterTaskChange(task);//ʹ���µĿ��Ʋ���
			// KDTask.isadjustable
			String columnName = model.getColumnName(column);
			if (columnName != null && columnName.equals(GanttTreeTableModelExt.strColEffectTimes)) {
				// ��������ı䶯
				BigDecimal effectTimes = (BigDecimal) value;
				if (effectTimes != null) {
					// ����=��Ч����-1
					// ��������������6
					if (effectTimes.compareTo(FDCHelper.ONE) < 0) {
						return false;
					}
					int duration = effectTimes.intValue() - KDGPConstants.EFFECTTIMESBALANCE;
					task.setLength(duration);
					// ScheduleCalendarInfo calendar =
					// ((FDCScheduleInfo)task.getScheduleTaskInfo
					// ().getScheduleBase()).getCalendar();
					//task.setEnd(ScheduleParserHelper.parseDateToGanttCalendar(
					// ScheduleCalendarHelper
					// .getEndDate(task.getScheduleTaskInfo().getStart(),
					// effectTimes,calendar)));
					return false;
				}
			}
		}

		return true;
	}

	public void afterSetValue(final Object value, final Object node, final int column) {
		

		Mediator.getGanttProjectSingleton().repaint();

	}

	static class ChangeValues {
		static Map innerMap = new HashMap();

		static void addChangeValues(KDTask task) {
			Object[] values = new Object[] { task.getStart(), task.getEnd(), task.getDuration() };
			innerMap.put(getTaskKey(task), values);
		}

		static GanttCalendar getStartDate(KDTask task) {
			Object[] values = getValues(task);
			if (values != null) {
				return (GanttCalendar) values[0];
			}
			return null;
		}

		static GanttCalendar getEndDate(KDTask task) {
			Object[] values = getValues(task);
			if (values != null) {
				return (GanttCalendar) values[1];
			}
			return null;
		}

		static TaskLength getDuration(KDTask task) {
			Object[] values = getValues(task);
			if (values != null) {
				return (TaskLength) values[2];
			}
			return null;
		}

		static Object[] getValues(KDTask task) {
			return (Object[]) innerMap.get(getTaskKey(task));
		}

		static String getTaskKey(KDTask task) {
			return String.valueOf(task.getTaskID());
		}
	}

	public Object getValueAt(Object node, int column) {
		Object res = null;
		if (!(node instanceof TaskNode))
			return null;
		TaskNode tn = (TaskNode) node;
		Task t = (Task) tn.getUserObject();
		if (!(t instanceof KDTask)) {
			return null;
		}
		KDTask task = (KDTask) t;
		if (task.isScheduleTask()) {
			return null;
		}
		// if(tn.getParent()!=null){
		switch (column) {
		case 4:
			res = task.getMyOldStartDate();
			break;
		case 5:
			res = task.getMyOldEndDate();
			break;
		case 6:
			res = task.getMyOldDuration();
			break;
		default:
			String colName = model.getColumnName(column);
			if (colName == null) {
				return null;
			}
			if (colName.equals(GanttTreeTableModelExt.strColNatureTimes)) {
				res = task.get("myOldNatureTimes");
			} else if (colName.equals(GanttTreeTableModelExt.strColEffectTimes)) {
				res = task.get("myOldEffectTimes");
			}
			break;
		}
		return res;
	}
}
