package net.sourceforge.ganttproject.task.dependency.constraint;

import java.util.Date;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttTaskRelationship;
import net.sourceforge.ganttproject.calendar.ScheduleCalendarImpl;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskActivity;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyConstraint;
import net.sourceforge.ganttproject.task.dependency.TaskDependency.ActivityBinding;

import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;

/**
 * Dependant task starts not earlier than dependee finishes Created by IntelliJ
 * IDEA. User: bard
 */
public class FinishStartConstraintImpl extends ConstraintImpl implements
		TaskDependencyConstraint {

	public FinishStartConstraintImpl() {
		super(GanttTaskRelationship.FS, GanttLanguage.getInstance().getText(
				"finishstart"));
	}

	public String getSimpleName() {
		return "FS";
	}

	public TaskDependencyConstraint.Collision getCollision() {
		TaskDependencyConstraint.Collision result = null;
		Task dependee = getDependency().getDependee();
		Task dependant = getDependency().getDependant();
		GanttCalendar dependeeEnd = dependee.getEnd().Clone();
		// GanttCalendar dependeeEnd = dependee.getEnd();
		GanttCalendar dependantStart = dependant.getStart();

		int difference = getDependency().getDifference();
		GanttCalendar comparisonDate = dependantStart.Clone();
		comparisonDate.add(difference);

		// �����֮ǰ���߼�������Ŀǰ�������ֻ�й���Ϊһ���ʱ��᷵��false(bug)�����Դ˴�ֱ����Ϊtrue��
		// boolean isActive =
		// getDependency().getHardness()==TaskDependency.Hardness.RUBBER ?
		// dependeeEnd
		// .compareTo(comparisonDate) > 0 : dependeeEnd
		// .compareTo(comparisonDate) != 0;
		boolean isActive = true;
		// new Exception("[FinishStartConstraint] isActive="+isActive+"
		// dependdee="+dependee+" end="+dependeeEnd+"
		// start="+dependantStart).printStackTrace();
		addDelay(dependeeEnd);
		result = new TaskDependencyConstraint.DefaultCollision(dependeeEnd,
				TaskDependencyConstraint.Collision.START_LATER_VARIATION,
				isActive);
		return result;
	}

	public ActivityBinding getActivityBinding() {
		TaskActivity[] dependantActivities = getDependency().getDependant()
				.getActivities();
		TaskActivity[] dependeeActivities = getDependency().getDependee()
				.getActivities();
		if (dependantActivities.length > 0 && dependeeActivities.length > 0) {
			TaskActivity theDependant = dependantActivities[0];
			TaskActivity theDependee = dependeeActivities[dependeeActivities.length - 1];
			return new DependencyActivityBindingImpl(
					theDependant,
					theDependee,
					new Date[] { theDependant.getStart(), theDependee.getEnd() });
		} else {
			return null;
		}

	}

	public void addDelay(GanttCalendar calendar) {
		TaskDependency myDependency = getDependency();
		ScheduleCalendarInfo cal = ((FDCScheduleTaskInfo) ((KDTask) getDependency().getDependee()).getScheduleTaskInfo()).getCalendar();
		ScheduleCalendarImpl myCal = new ScheduleCalendarImpl(cal);
		GanttCalendar solutionStart = calendar.Clone();
		// 0���ʾ��ǰ������������ڵ���һ�졣��˶��1 by yangzhiqiao
		// ������ǰ��û�п��ǹ�������������Ǽ���Ҫ�����淨��
		int difference = myDependency.getDifference();
		if (difference >= 0) {
			difference = myDependency.getDifference() + 1;
			calendar.add(difference);
			for (int i = 0; i <= difference; i++) {
				if (myCal.isNonWorkingDay(solutionStart.getTime())) {
					calendar.add(1);
					difference++;
				}
				solutionStart.add(1);
			}
		} else {
			/* modified by zhaoqin for R140401-0040 on 2014/05/26 */
			//difference = myDependency.getDifference() - 1;
			difference = myDependency.getDifference() + 1;
			
			calendar.add(difference);
			for (int i = difference; i <= 0; i++) {
				if (myCal.isNonWorkingDay(solutionStart.getTime())) {
					calendar.add(-1);
					i--;
				}
				solutionStart.add(-1);
			}
		}
	}
}
