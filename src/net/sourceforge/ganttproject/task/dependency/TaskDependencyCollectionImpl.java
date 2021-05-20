package net.sourceforge.ganttproject.task.dependency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import net.sourceforge.ganttproject.GPLogger;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskContainmentHierarchyFacade;
import net.sourceforge.ganttproject.task.dependency.TaskDependency.Hardness;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishFinishConstraintImpl;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishStartConstraintImpl;

/**
 * Created by IntelliJ IDEA. User: bard Date: 14.02.2004 Time: 16:02:48 To
 * change this template use File | Settings | File Templates.
 */
public class TaskDependencyCollectionImpl implements TaskDependencyCollection {
    private Set myDependencies = new HashSet();

    private SortedMap mySearchKey2dependency = new TreeMap();

    private final EventDispatcher myEventDispatcher;

	private final TaskContainmentHierarchyFacade.Factory myTaskHierarchyFactory;
	
	/**
	 * �յ�TaskDependency����
	 */
	private final static TaskDependency[] TASK_DEPENDENCY_EMPTY_ARRAY = new TaskDependency[0]; 

    public TaskDependencyCollectionImpl(TaskContainmentHierarchyFacade.Factory taskHierarchyFactory, EventDispatcher myEventDispatcher) {
        this.myEventDispatcher = myEventDispatcher;
        myTaskHierarchyFactory = taskHierarchyFactory;
    }

    public TaskDependency[] getDependencies() {
		return (TaskDependency[]) myDependencies.toArray(TASK_DEPENDENCY_EMPTY_ARRAY);
	}

	public TaskDependency[] getDependencies(Task task) {
		if (mySearchKey2dependency.isEmpty()) {
			return TASK_DEPENDENCY_EMPTY_ARRAY;
		}

		SearchKey fromKey = new RangeSearchFromKey(task);
		SearchKey toKey = new RangeSearchToKey(task);
		SortedMap subMap = mySearchKey2dependency.subMap(fromKey, toKey);
		if (subMap.isEmpty()) {
			return TASK_DEPENDENCY_EMPTY_ARRAY;
		}

		return (TaskDependency[]) subMap.values().toArray(TASK_DEPENDENCY_EMPTY_ARRAY);
	}

	public TaskDependency[] getDependenciesAsDependant(Task dependant) {
		if (mySearchKey2dependency.isEmpty()) {
			return TASK_DEPENDENCY_EMPTY_ARRAY;
		}

		int taskID = dependant.getTaskID();
		SearchKey fromKey = new SearchKey(SearchKey.DEPENDANT, taskID, -1);
		SearchKey toKey = new SearchKey(SearchKey.DEPENDEE, taskID, -1);
		SortedMap subMap = mySearchKey2dependency.subMap(fromKey, toKey);
		if (subMap.isEmpty()) {
			return TASK_DEPENDENCY_EMPTY_ARRAY;
		}

		return (TaskDependency[]) subMap.values().toArray(TASK_DEPENDENCY_EMPTY_ARRAY);
	}

	public TaskDependency[] getDependenciesAsDependee(Task dependee) {
		if (mySearchKey2dependency.isEmpty()) {
			return TASK_DEPENDENCY_EMPTY_ARRAY;
		}

		int taskID = dependee.getTaskID();
		SearchKey fromKey = new SearchKey(SearchKey.DEPENDEE, taskID, -1);
		SearchKey toKey = new SearchKey(Integer.MAX_VALUE, taskID, -1);
		SortedMap subMap = mySearchKey2dependency.subMap(fromKey, toKey);
		if (subMap.isEmpty()) {
			return TASK_DEPENDENCY_EMPTY_ARRAY;
		}

		return (TaskDependency[]) subMap.values().toArray(TASK_DEPENDENCY_EMPTY_ARRAY);
	}

    public TaskDependency createDependency(Task dependant, Task dependee)
            throws TaskDependencyException{
        return createDependency(dependant, dependee,
                new FinishStartConstraintImpl());
    }

    public TaskDependency createDependency(Task dependant, Task dependee,
            TaskDependencyConstraint constraint) throws TaskDependencyException {
        TaskDependency result = auxCreateDependency(dependant, dependee,
                constraint);
        addDependency(result);
        return result;
    }

    
    /**
     * ����Ч�����������ķ���
     * @param dependant
     * @param dependee
     * @param constraint
     * @param hardness
     * @param difference
     * @return
     * @throws TaskDependencyException
     */
    public TaskDependency createDependency(Task dependant, Task dependee,
            TaskDependencyConstraint constraint,Hardness hardness,int difference) throws TaskDependencyException {
        TaskDependency result = auxCreateDependency(dependant, dependee,
                constraint);
        result.setDifference(difference);
        result.setHardness(hardness);
        addDependency(result);
        return result;
    }
    
    public boolean canCreateDependency(Task dependant, Task dependee) {
        if (dependant == dependee) {
            return false;
        }
        if (false==getTaskHierarchy().areUnrelated(dependant, dependee)) {
            return false;
        }
        SearchKey key = new SearchKey(SearchKey.DEPENDANT, dependant.getTaskID(), dependee.getTaskID());
        if (mySearchKey2dependency.containsKey(key)) {
            return false;
        }
        TaskDependency testDep = new TaskDependencyImpl(dependant, dependee, this);
        if (isLooping(testDep)) {
        	return false;
        }
        return true;
    }

    public void deleteDependency(TaskDependency dependency) {
        delete(dependency);
    }

    void fireChanged(TaskDependency dependency) {
    	myEventDispatcher.fireDependencyRemoved(dependency);
    	myEventDispatcher.fireDependencyAdded(dependency);
    }
    public void clear() {
        doClear();
    }

    public TaskDependencyCollectionMutator createMutator() {
        return new MutatorImpl();
    }

    private class MutatorImpl implements TaskDependencyCollectionMutator {
        private Map myQueue = new LinkedHashMap();

        private MutationInfo myCleanupMutation;

        public void commit() {
            List mutations = new ArrayList(myQueue.values());
            if (myCleanupMutation != null) {
                mutations.add(myCleanupMutation);
            }
            Collections.sort(mutations);
            for (int i = 0; i < mutations.size(); i++) {
                MutationInfo next = (MutationInfo) mutations.get(i);
                switch (next.myOperation) {
                case MutationInfo.ADD:
                {
                    try
                    {
                        addDependency(next.myDependency);
                    }
                    catch (TaskDependencyException e)
                    {
                    	if (!GPLogger.log(e)) {
                    		e.printStackTrace(System.err);
                    	}
                    }
                    break;
                }
                case MutationInfo.DELETE: {
                    delete(next.myDependency);
                    break;
                }
                case MutationInfo.CLEAR: {
                    doClear();
                    break;
                }
                }
            }
        }

        public void clear() {
            myQueue.clear();
            myCleanupMutation = new MutationInfo(null, MutationInfo.CLEAR);
        }

        public TaskDependency createDependency(Task dependant, Task dependee)
                throws TaskDependencyException {
            return createDependency(dependant, dependee,
                    new FinishFinishConstraintImpl());
        }

        public TaskDependency createDependency(Task dependant, Task dependee,
                TaskDependencyConstraint constraint)
                throws TaskDependencyException {
            TaskDependency result = auxCreateDependency(dependant, dependee,
                    constraint);
            myQueue.put(result, new MutationInfo(result, MutationInfo.ADD));
            return result;
        }

        public void deleteDependency(TaskDependency dependency) {
            MutationInfo info = (MutationInfo) myQueue.get(dependency);
            if (info == null) {
                myQueue.put(dependency, new MutationInfo(dependency,
                        MutationInfo.DELETE));
            } else if (info.myOperation == MutationInfo.ADD) {
                myQueue.remove(dependency);
            }
        }

    }

    private static class MutationInfo implements Comparable {
        static final int ADD = 0;

        static final int DELETE = 1;

        static final int CLEAR = 2;

        final TaskDependency myDependency;

        final int myOperation;

        final int myOrder = ourOrder++;

        static int ourOrder;

        public MutationInfo(TaskDependency myDependency, int myOperation) {
            this.myDependency = myDependency;
            this.myOperation = myOperation;
        }

        public int compareTo(Object o) {
            MutationInfo rvalue = (MutationInfo) o;
            return myOrder - rvalue.myOrder;
        }
    }

    private TaskDependency auxCreateDependency(Task dependant, Task dependee,
            TaskDependencyConstraint constraint) {
        TaskDependency result = new TaskDependencyImpl(dependant, dependee,
                this);
        result.setConstraint(constraint);
        //TODO H ֧�����ӵ�ʱ���ƶ�����һ��
        result.setDifference(1);
        return result;
    }

    void addDependency(TaskDependency dep) throws TaskDependencyException
    {
        if (myDependencies.contains(dep))
        {
            throw new TaskDependencyException("Dependency=" + dep
                    + " already exists");
        }
        if(this.isLooping(dep))
        {
        	throw new TaskDependencyException("Dependency=" + dep
                    + " is looping");
        }
        if (false==getTaskHierarchy().areUnrelated(dep.getDependant(), dep.getDependee())) {
        	throw new TaskDependencyException("In dependency="+dep+" one of participants is a supertask of another");
        }
        myDependencies.add(dep);
        //
        mySearchKey2dependency.put(new SearchKey(SearchKey.DEPENDANT,
                (TaskDependencyImpl) dep), dep);
        mySearchKey2dependency.put(new SearchKey(SearchKey.DEPENDEE,
                (TaskDependencyImpl) dep), dep);
        if(dep.getConstraint().getID() == 3){
//        	KDTask dependant = (KDTask)dep.getDependant();
//        	KDTask dependee = (KDTask) dep.getDependee();
//        	Date newEndDate = new Date();
//        	Date newStartDate = new Date();
//        	FDCScheduleTaskInfo dependantInfo = (FDCScheduleTaskInfo) dependant.getScheduleTaskInfo();
//        	newEndDate = ScheduleCalendarHelper.getDiffDate(dependee.getScheduleTaskInfo().getEnd(), new BigDecimal(dep.getDifference()));
//        	newStartDate =  ScheduleCalendarHelper.getEndDate(
//        			newStartDate, new BigDecimal(dependantInfo.getDuration()), dependantInfo.getScheduleBase().getCalendar());
//        	dependantInfo.setEnd(newEndDate);
//        	dependantInfo.setStart(newStartDate);
//        	System.out.print("newEndDate:"+newEndDate+";startDate:"+newStartDate);
        	
//        	dependant.setStart(ScheduleParserHelper.parseDateToGanttCalendar(dependant.getScheduleTaskInfo().getStart()));
        }
//        dep.getConstraint();
        myEventDispatcher.fireDependencyAdded(dep);
    }

    boolean isLooping(TaskDependency dep) {
    	LoopDetector detector = new LoopDetector(dep.getDependant().getManager());
    	return detector.isLooping(dep);
    }

    boolean _isLooping(TaskDependency dep)
    {
    	Set tasksInvolved = new HashSet();
    	tasksInvolved.add(dep.getDependee());
    	return _isLooping(dep, tasksInvolved);
    }

    private boolean _isLooping(TaskDependency dep, Set tasksInvolved) {
    	Task dependant = dep.getDependant();
    	if (tasksInvolved.contains(dependant)) {
    		return true;
    	}
    	for (Iterator tasks = tasksInvolved.iterator(); tasks.hasNext();) {
    		Task nextInvolved = (Task) tasks.next();
    		if (false==getTaskHierarchy().areUnrelated(nextInvolved, dependant)) {
    			return true;
    		}
    	}
    	tasksInvolved.add(dependant);
    	{
	    	TaskDependency[] nextDeps =dependant.getDependenciesAsDependee().toArray();
	    	for (int i=0; i<nextDeps.length; i++) {
	    		if (_isLooping(nextDeps[i], tasksInvolved)) {
	    			return true;
	    		}
	    	}
    	}
    	Task[] nestedTasks = getTaskHierarchy().getNestedTasks(dependant);
    	for (int i=0; i<nestedTasks.length; i++) {
    		tasksInvolved.add(nestedTasks[i]);
        	TaskDependency[] nextDeps =nestedTasks[i].getDependenciesAsDependee().toArray();
        	for (int j=0; j<nextDeps.length; j++) {
        		if (_isLooping(nextDeps[j], tasksInvolved)) {
        			return true;
        		}
        	}

    	}
        tasksInvolved.remove(dependant);
    	return false;
    }


    void delete(TaskDependency dep) {
        myDependencies.remove(dep);
        SearchKey key1 = new SearchKey(SearchKey.DEPENDANT, dep.getDependant()
                .getTaskID(), dep.getDependee().getTaskID());
        SearchKey key2 = new SearchKey(SearchKey.DEPENDEE, dep.getDependee()
                .getTaskID(), dep.getDependant().getTaskID());
        mySearchKey2dependency.remove(key1);
        mySearchKey2dependency.remove(key2);
        myEventDispatcher.fireDependencyRemoved(dep);
        // SearchKey fromKey = new RangeSearchFromKey(dep.getDependant());
        // SearchKey toKey = new RangeSearchToKey(dep.getDependant());
        // mySearchKey2dependency.subMap(fromKey, toKey).clear();
        // fromKey = new RangeSearchFromKey(dep.getDependee());
        // toKey = new RangeSearchToKey(dep.getDependee());
        // mySearchKey2dependency.subMap(fromKey, toKey).clear();
    }

    public void doClear() {
        myDependencies.clear();
        mySearchKey2dependency.clear();
    }

    protected TaskContainmentHierarchyFacade getTaskHierarchy() {
        return myTaskHierarchyFactory.createFacede();
    }

}
