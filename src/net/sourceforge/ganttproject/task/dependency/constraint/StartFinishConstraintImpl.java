package net.sourceforge.ganttproject.task.dependency.constraint;

import java.util.Date;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttTaskRelationship;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskActivity;
import net.sourceforge.ganttproject.task.TaskMutator;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyConstraint;
import net.sourceforge.ganttproject.task.dependency.TaskDependency.ActivityBinding;

/**
 * Dependant task finishes not earlier than dependee starts Created by IntelliJ
 * IDEA. User: bard
 */
public class StartFinishConstraintImpl extends ConstraintImpl implements
        TaskDependencyConstraint {
    public StartFinishConstraintImpl() {
        super(GanttTaskRelationship.SF, GanttLanguage.getInstance().getText(
                "startfinish"));
	}

	public String getSimpleName() {
		return "SF";
	}

    public TaskDependencyConstraint.Collision getCollision() {
        TaskDependencyConstraint.Collision result = null;
        Task dependee = getDependency().getDependee();
        Task dependant = getDependency().getDependant();
        GanttCalendar dependeeStart = dependee.getStart();
        GanttCalendar dependantEnd = dependant.getEnd();
        //
        int difference = getDependency().getDifference();
        GanttCalendar comparisonDate = dependeeStart.Clone();
        comparisonDate.add(difference);

        boolean isActive = getDependency().getHardness()==TaskDependency.Hardness.RUBBER ? dependantEnd
                .compareTo(comparisonDate) < 0 : dependantEnd
                .compareTo(comparisonDate) != 0;

        // GanttCalendar acceptableStart = dependant.getStart();
        GanttCalendar acceptableStart = dependee.getStart().Clone();
        if (isActive) {
            Task clone = dependee.unpluggedClone();
            TaskMutator mutator = clone.createMutator();
            mutator.shift(-dependant.getDuration().getLength());
            acceptableStart = clone.getStart();
        }
        addDelay(acceptableStart);
        result = new TaskDependencyConstraint.DefaultCollision(acceptableStart,
                TaskDependencyConstraint.Collision.START_LATER_VARIATION,
                isActive);
        return result;
    }

    public ActivityBinding getActivityBinding() {
        TaskActivity[] dependantActivities = getDependency().getDependant()
                .getActivities();
        TaskActivity[] dependeeActivities = getDependency().getDependee()
                .getActivities();
        TaskActivity theDependant = dependantActivities[dependantActivities.length - 1];
        TaskActivity theDependee = dependeeActivities[0];
        return new DependencyActivityBindingImpl(theDependant, theDependee,
                new Date[] { theDependant.getEnd(), theDependee.getStart() });
    }

}
