package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;

public class TaskAdjustManager implements Serializable{
	private List taskItems=new ArrayList();
	private final ScheduleCalendarInfo calendarInfo;
	private Map childMap=new HashMap();
	public TaskAdjustManager(FDCScheduleInfo info){
		childMap.clear();
		for(Iterator iter=info.getTaskEntrys().iterator();iter.hasNext();){
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo)iter.next();
			addItem(task);
			addChildTask(task);
		}
		calendarInfo=info.getCalendar();
	}
	
	/**
	 * ������ڵ�
	 * @param task
	 * @return
	 */
	private FDCScheduleTaskCollection getChildTasks(FDCScheduleTaskInfo task){
		if(task == null) return new FDCScheduleTaskCollection();
		FDCScheduleTaskCollection childs=(FDCScheduleTaskCollection)childMap.get(task.getLongNumber());
		if(childs==null){
			return new FDCScheduleTaskCollection();
		}
		return childs;
	}
	
	/**
	 * ��ʼ��������ڵ�
	 * @param task
	 */
	private void addChildTask(FDCScheduleTaskInfo task){
		if(task==null||task.getLongNumber()==null){
			return;
		}
//		System.out.println("longNumber:"+task.getLongNumber());
		if(task.getLongNumber().indexOf('!')>-1){
			String parentNumber=task.getLongNumber().substring(0, task.getLongNumber().lastIndexOf('!'));
			FDCScheduleTaskCollection childTasks=(FDCScheduleTaskCollection)childMap.get(parentNumber);
			if(childTasks==null){
				childTasks=new FDCScheduleTaskCollection();
				childMap.put(parentNumber, childTasks);
			}
			childTasks.add(task);
		}
	}
	
	public int size(){
		return taskItems.size();
	}
	public TaskAdjustManagerItem get(int i){
		return (TaskAdjustManagerItem)taskItems.get(i);
	}
	private TaskAdjustManagerItem addItem(FDCScheduleTaskInfo oldTask){
		TaskAdjustManagerItem item=new TaskAdjustManagerItem(oldTask);
		taskItems.add(item);
		oldTask.put("taskListItem", item);
		return item;
	}
	/**
	 * update all item's new task 
	 * @return a source List (adjust and new )
	 * sourceItems��ű�����������
	 */
	public List updateAll(){
		int size=size();
		List sourceItems=new ArrayList();
		for(int i=size-1;i>=0;i--){
			TaskAdjustManagerItem item=get(i);
			if(item.isDelete){
//				taskItems.remove(i);
				//Ҫʵ��ɾ���Ŀ�����
				item.type=TaskAdjustManagerItem.TYPE_NOCHANGE;
				item.newTask=null;
				item.isDelete=false;
				continue;
			}
			
			if(item.type.equals(TaskAdjustManagerItem.TYPE_ADJUSTTASK)||
					item.type.equals(TaskAdjustManagerItem.TYPE_NEWTASK) || 
					item.type.equals(TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK)){
				item.updateNewTask();
				sourceItems.add(item);
			}else{
				//û�е����ļ������������ȫ�����³�null,�Ա�����
				item.type=TaskAdjustManagerItem.TYPE_NOCHANGE;
				item.newTask=null;
			}
			item.isParentManAdjust=false;
		}
		return sourceItems;
	}
	public void updateItem(String taskId,ScheduleAdjustTaskEntryInfo adjustTaskEntry){
		TaskAdjustManagerItem item=findTaskListItem(taskId);
		if(item!=null){
			item.update(adjustTaskEntry);
//			FDCScheduleTaskDependCollection taskDepCol = item.getOldTask().getDependEntrys();
//			for(Iterator ite = taskDepCol.iterator();ite.hasNext();){
//				FDCScheduleTaskInfo taskInfo = ((FDCScheduleTaskDependInfo)ite.next()).getDependTask();
//				TaskAdjustManagerItem depItem = findTaskListItem(taskInfo.getId().toString());
//				depItem.setType(TaskAdjustManagerItem.TYPE_AFFECTTASK);
//			}
		}
	}
	
	public void addNewItem(String parentTaskId,ScheduleNewTaskEntryInfo newTaskEntry){
		TaskAdjustManagerItem item=new TaskAdjustManagerItem(newTaskEntry);
		int index=getNewItemIndex(newTaskEntry);
		if(index < 0) index = 0;
		taskItems.add(index,item);
		
	}
	public void deleteItem(ScheduleNewTaskEntryInfo newTaskEntry){
		TaskAdjustManagerItem item = findTaskListItem(newTaskEntry.getId().toString());
		if(item!=null){
			item.isDelete=true;
			getChildTasks(item.getNewTask().getParent()).remove(item.getNewTask());
		}
	}
	private TaskAdjustManagerItem findTaskListItem(String taskId){
		for(Iterator iter=taskItems.iterator();iter.hasNext();){
			TaskAdjustManagerItem item=(TaskAdjustManagerItem)iter.next();
			if(item.taskId!=null&&item.taskId.equals(taskId)){
				return item;
			}
		}
		
		return null;
	}
	
	private int getNewItemIndex(ScheduleNewTaskEntryInfo newTaskEntry){
		if(newTaskEntry.getParentTask()==null){
			return taskItems.size();
		}
		String parentTaskId=newTaskEntry.getParentTask().getId().toString();
		int index=-1;
		boolean findParent=false;
		for (int i = 0; i < taskItems.size(); i++) {
			TaskAdjustManagerItem item=(TaskAdjustManagerItem)taskItems.get(i);
		
			if(findParent&&item.getOldTask()!=null&&item.getOldTask().getLevel()<=newTaskEntry.getParentTask().getLevel()){
				//����Ѿ��ҵ����Ͻ����񣬵�����һ�����ϼ�����������ʱ��˵���Ѿ������һ����
				break;
			}
			if(item.taskId!=null&&item.taskId.equals(parentTaskId)){
				findParent=true;
				//�嵽���ĺ���
				index=i+1;
			}
			if(item.parentTaskId!=null&&item.parentTaskId.equals(parentTaskId)){
				//�嵽���ĺ���
				index=i+1;
			}
			
		}
		return index;
	}
	
	/**
	 * ���мƻ�����
	 */
	public void adjustTasks(){
		List sourceList = updateAll();
		//����
		setAdjustPrefixTask(true);
		for(Iterator iter=sourceList.iterator();iter.hasNext();){
			TaskAdjustManagerItem item=(TaskAdjustManagerItem)iter.next();
			if(item.type==TaskAdjustManagerItem.TYPE_ADJUSTTASK
					|| item.type == TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK){
				//update parent task and depend
				_adjustTask(item,item.getNewTask());
			}else{
				//update parent task
				FDCScheduleTaskInfo newTask = item.getNewTask();
				FDCScheduleTaskInfo parentTask = newTask.getParent();
				if(parentTask==null){
					continue;
				}
				//new task out of parent task 's bound (start<parent.start and end>parent.end)
				TaskAdjustManagerItem newItem = adjustParentAffectTask(newTask, parentTask);
				if(newItem == null) {
					continue;
				}
				_adjustTask(newItem,newItem.getNewTask());
			}
		}
		
	}

	private boolean isAdjustPrefixTask=false;
	public void setAdjustPrefixTask(boolean isAdjustPrefixTask){
		this.isAdjustPrefixTask=isAdjustPrefixTask;
	}
	
	private void _adjustTask(TaskAdjustManagerItem item,FDCScheduleTaskInfo sourceTask){
		if(item==null){
			return;
		}
		if(item.type==TaskAdjustManagerItem.TYPE_ADJUSTTASK || 
				item.type==TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK){
			item.isParentManAdjust=true;
		}
		
		FDCScheduleTaskInfo oldTask = item.getOldTask();

		//handle parent task
		if(oldTask.getParent()!=null){
			TaskAdjustManagerItem parentItem = adjustParentAffectTask(item.getNewTask(),oldTask.getParent());
			_adjustTask(parentItem,sourceTask);
		}
		
		//handle child task
		FDCScheduleTaskCollection childTasks = getChildTasks(oldTask);
		for(Iterator iter=childTasks.iterator();iter.hasNext();){
			FDCScheduleTaskInfo childTask=(FDCScheduleTaskInfo)iter.next();
			TaskAdjustManagerItem childItem = adjustChildAffectTask(item,childTask);
			_adjustTask(childItem,sourceTask);
		}
		//handle depend task
		for(Iterator iter=oldTask.getDependEntrys().iterator();iter.hasNext();){
			FDCScheduleTaskDependInfo depend=(FDCScheduleTaskDependInfo)iter.next();
			TaskAdjustManagerItem dependItem = adjustDependTaskAffectTask(sourceTask,item.getNewTask(),depend);
			_adjustTask(dependItem,sourceTask);
		}
		
		
		
		//handle prefix task ���´��ʱ��,����������������ɰ汾���ݺ����,������һ����������
		if(isAdjustPrefixTask){
			FDCScheduleTaskDependCollection depends=getDepends();
			for(int i=0,size=depends.size();i<size;i++){
				FDCScheduleTaskDependInfo depend=depends.get(i);
				if(depend.getDependTask().getId().equals(oldTask.getId())&&item.getNewTask()!=null){
					//update prefix
					if(depend.getType()==TaskLinkTypeEnum.FINISH_START){
						//difference=��������ʼʱ��-��ǰ����Ľ���ʱ��(ע�ⶼ�Ǹ��º������)
//						depend.setDifference(ScheduleCalendarHelper.getEffectTimes(item.getNewTask().getStart(), depend.getTask().getEnd(), calendarInfo).intValue()-1);
						if(getTaskListItem(depend.getTask())!=null&&getTaskListItem(depend.getTask()).getNewTask()!=null)
							depend.setDifference(ScheduleCalendarHelper.getEffectTimes(
								getTaskListItem(depend.getTask()).getNewTask().getEnd(),item.getNewTask().getStart(),  calendarInfo).intValue()-1);
						else
							depend.setDifference(ScheduleCalendarHelper.getEffectTimes(
									depend.getTask().getEnd(),item.getNewTask().getStart(),  calendarInfo).intValue()-1);
					}
					if(depend.getType()==TaskLinkTypeEnum.FINISH_FINISH){
						//difference=newTask.end-end
//						depend.setDifference(ScheduleCalendarHelper.getEffectTimes(item.getNewTask().getEnd(), depend.getTask().getEnd(), calendarInfo).intValue()-1);
						if(getTaskListItem(depend.getTask())!=null&&getTaskListItem(depend.getTask()).getNewTask()!=null)
							depend.setDifference(ScheduleCalendarHelper.getEffectTimes(
								getTaskListItem(depend.getTask()).getNewTask().getEnd(), item.getNewTask().getEnd(), calendarInfo).intValue()-1);
						else
							depend.setDifference(ScheduleCalendarHelper.getEffectTimes(
									depend.getTask().getEnd(), item.getNewTask().getEnd(), calendarInfo).intValue()-1);
					}
//					if(depend.getType()==TaskLinkTypeEnum.START_FINISH){
//						//difference=newTask.end-start
//						depend.setDifference(ScheduleCalendarHelper.getEffectTimes(item.getNewTask().getEnd(), depend.getTask().getStart(), calendarInfo).intValue()-1);
//					}
					if(depend.getType()==TaskLinkTypeEnum.START_START){
						//difference=newTask.start-start
//						depend.setDifference(ScheduleCalendarHelper.getEffectTimes(item.getNewTask().getStart(), depend.getTask().getStart(), calendarInfo).intValue()-1);
						if(getTaskListItem(depend.getTask())!=null&&getTaskListItem(depend.getTask()).getNewTask()!=null)
							depend.setDifference(ScheduleCalendarHelper.getEffectTimes(
								getTaskListItem(depend.getTask()).getNewTask().getStart(),item.getNewTask().getStart(),calendarInfo).intValue()-1);
						else
							depend.setDifference(ScheduleCalendarHelper.getEffectTimes(
									depend.getTask().getStart(),item.getNewTask().getStart(),calendarInfo).intValue()-1);
					}
				}
			}
		}
	}
	
	private FDCScheduleTaskDependCollection depends=null;
	/**
	 * ȡ�����к��ýڵ�
	 * @return
	 */
	private FDCScheduleTaskDependCollection getDepends(){
		if(depends==null){
			depends=new FDCScheduleTaskDependCollection();
			for(int i=0,size=size();i<size;i++){
				TaskAdjustManagerItem item = get(i);
				if(item.oldTask!=null){
					depends.addCollection(item.oldTask.getDependEntrys());
				}
			}
		}
		return depends;
	}
	
	/**
	 * ������Ӱ����ϼ��ڵ�
	 * @param newTask
	 * @param parentTask
	 * @return
	 */
	private TaskAdjustManagerItem adjustParentAffectTask(FDCScheduleTaskInfo newTask, FDCScheduleTaskInfo parentTask) {
		if(newTask==null){
			return null;
		}
		TaskAdjustManagerItem parentItem=getTaskListItem(parentTask);
		if(parentItem==null||!parentItem.type.equals(TaskAdjustManagerItem.TYPE_NOCHANGE)){
			//�Ѿ�������,�����ظ�,��Ȼ����ѭ��
			return null;
		}
		parentItem.newTask=cloneTask(parentTask);
		if(newTask.getStart().compareTo(parentTask.getStart())<0){
			parentItem.newTask.setStart(newTask.getStart());
		}else{
			/**
			 * ����¼��Ŀ�ʼʱ��������ϼ�����Ŀ�ʼʱ��֮�����ϼ�����Ŀ�ʼʱ��Ϊ�¼�����������Ŀ�ʼʱ��
			 * ������������и�����,���������ר��,�������¼���ר����ϼ������������Ͳ���Ҫ��������
			 * @author xiaohong_shi 2009/12/17 
			 */
			if(parentTask.isMainTask()&&!newTask.isMainTask()){
					//�������ר���������������
			}else{
				//Ϊ�������¼��п�ʼʱ�������һ��
				Date childEarliestStartDate = getChildEarliestStartDate(parentTask);
				if(childEarliestStartDate.compareTo(parentTask.getStart())!=0){
					parentItem.newTask.setStart(childEarliestStartDate);
				}
			}
		}
		if(newTask.getEnd().compareTo(parentTask.getEnd())>0){
			parentItem.newTask.setEnd(newTask.getEnd());
		}else{
			/**
			 * ����¼��Ŀ�ʼʱ��������ϼ�����Ŀ�ʼʱ��֮�����ϼ�����Ŀ�ʼʱ��Ϊ�¼�����������Ŀ�ʼʱ��
			 * ������������и�����,���������ר��,�������¼���ר����ϼ������������Ͳ���Ҫ��������
			 * @author xiaohong_shi 2009/12/17 
			 */
			if(parentTask.isMainTask()&&!newTask.isMainTask()){
					//�������ר���������������
			}else{
				//Ϊ�������¼��п�ʼʱ�������һ��
//				Date childEarliestEndDate = getChildEarliestEndDate(parentTask);
//				if(childEarliestEndDate.compareTo(parentTask.getEnd())!=0){
//					parentItem.newTask.setEnd(childEarliestEndDate);
//				}
				//Ӧ���ǵ���Ϊ�¼��п�ʼʱ����ٵ�һ���Ŷ�		-by neo
				Date childLatestEndDate = getChildLatestEndDate(parentTask);
					if(childLatestEndDate.compareTo(parentTask.getEnd())!=0){
					parentItem.newTask.setEnd(childLatestEndDate);
				}
			}
		}
		
		parentItem.newTask.setEffectTimes(ScheduleCalendarHelper.getEffectTimes(parentItem.newTask.getStart(), parentItem.newTask.getEnd(), calendarInfo));
		if(parentItem!=null){
			if(TaskAdjustManagerItem.TYPE_ADJUSTTASK.equals(parentItem.type)
					|| TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK.equals(parentItem.type)){
				parentItem.type=TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK;
			}else{
				parentItem.type=TaskAdjustManagerItem.TYPE_AFFECTTASK;
			}
		}
		return parentItem;
	}

	/**
	 * �¼�����Ŀ�ʼʱ��
	 * ��������жϣ�1.�¼�Ϊ�������ϼ�Ϊ�������¼�ʱ��ͻ���ϼ����򷵻��¼���ʼʱ��
	 * 				2.�¼�Ϊ�������ϼ�Ϊ�������¼�ʱ��δͻ���ϼ����򷵻��ϼ���ʼʱ��
	 * 				3.�¼�Ϊר�����ϼ�Ϊר����ϼ�ʱ������¼�����
	 * 				4.�¼�Ϊר�����ϼ�Ϊ������¼�������ͻ������
	 * 				5.���û���¼�����ֱ�ӷ��ص�ǰ��endDate
	 * @param parentTask
	 */
	private Date getChildEarliestStartDate(FDCScheduleTaskInfo parentTask) {
		Date earliestDate=null;
		FDCScheduleTaskInfo childInfo = null;
		FDCScheduleTaskCollection childTasks = getChildTasks(parentTask);
		for(Iterator iter=childTasks.iterator();iter.hasNext();){
			FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
			TaskAdjustManagerItem taskItem = getTaskListItem(task);
			if(taskItem!=null&&taskItem.newTask!=null){
				task=taskItem.newTask;
			}
			if(earliestDate==null){
				earliestDate=task.getStart();
				childInfo = task;
			}else if(earliestDate.after(task.getStart())){
				earliestDate=task.getStart();
				childInfo = task;
			}
		}
		if(childInfo == null) return parentTask.getEnd();
		if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(parentTask.getWbs().getTaskType().getId().toString())){			
			if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(childInfo.getWbs().getTaskType().getId().toString())){			
				if(childInfo.getStart().before(parentTask.getStart())){							
					earliestDate = childInfo.getStart();			//�ϼ�Ϊ����&&�¼�Ϊ����&&�¼�ͻ���ϼ�
				}else{
					earliestDate = parentTask.getStart();			//�ϼ�Ϊ����&&�¼�Ϊ����&&�¼�δͻ���ϼ�
				}
			}else if(TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(childInfo.getWbs().getTaskType().getId().toString())){
				if(childInfo.getStart().before(parentTask.getStart())){							
								//�ϼ�Ϊ����&&�¼�Ϊר��&&�¼�ͻ���ϼ�
				}else{
					earliestDate = parentTask.getStart();			//�ϼ�Ϊ����&&�¼�Ϊר��&&�¼�δͻ���ϼ�
				}
			}
		}else if(TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(parentTask.getWbs().getTaskType().getId().toString())){
			if(childInfo.getStart().before(parentTask.getStart())){							
				earliestDate = childInfo.getStart();			//�ϼ�Ϊר��&&�¼�Ϊר��&&�¼�ͻ���ϼ�
			}else{
				earliestDate = parentTask.getStart();			//�ϼ�Ϊר��&&�¼�Ϊר��&&�¼�δͻ���ϼ�
			}
		}
		return earliestDate;
	}
	
	/**
	 * �¼�����Ľ���ʱ��
	 * @param parentTask
	 */
//	private Date getChildEarliestEndDate(FDCScheduleTaskInfo parentTask) {
//		Date earliestDate=null;
//		FDCScheduleTaskInfo childInfo = null;
//		FDCScheduleTaskCollection childTasks = getChildTasks(parentTask);
//		for(Iterator iter=childTasks.iterator();iter.hasNext();){
//			FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
//			TaskAdjustManagerItem taskItem = getTaskListItem(task);
//			if(taskItem!=null&&taskItem.newTask!=null){
//				task=taskItem.newTask;
//			}
//			if(earliestDate==null){
//				earliestDate=task.getEnd();
//			}else if(earliestDate.after(task.getEnd())){
//				earliestDate=task.getEnd();
//			}
//		}
//		if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(parentTask.getWbs().getTaskType().getId().toString())){			
//			if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(childInfo.getWbs().getTaskType().getId().toString())){			
//				if(parentTask.getEnd().before(childInfo.getEnd())){							
//					earliestDate = childInfo.getEnd();			//�ϼ�Ϊ����&&�¼�Ϊ����&&�¼�ͻ���ϼ�
//				}else{
//					earliestDate = parentTask.getEnd();			//�ϼ�Ϊ����&&�¼�Ϊ����&&�¼�δͻ���ϼ�
//				}
//			}else if(TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(childInfo.getWbs().getTaskType().getId().toString())){
//				if(parentTask.getEnd().before(childInfo.getEnd())){							
//								//�ϼ�Ϊ����&&�¼�Ϊר��&&�¼�ͻ���ϼ�
//				}else{
//					earliestDate = parentTask.getEnd();			//�ϼ�Ϊ����&&�¼�Ϊר��&&�¼�δͻ���ϼ�
//				}
//			}
//		}else if(TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(parentTask.getWbs().getTaskType().getId().toString())){
//			if(parentTask.getEnd().before(childInfo.getEnd())){							
//				earliestDate = childInfo.getEnd();			//�ϼ�Ϊר��&&�¼�Ϊר��&&�¼�ͻ���ϼ�
//			}else{
//				earliestDate = parentTask.getEnd();			//�ϼ�Ϊר��&&�¼�Ϊר��&&�¼�δͻ���ϼ�
//			}
//		}
//		return earliestDate;
//	}
	/**
	 * �¼���ٵĽ���ʱ��
	 * ��������жϣ�1.�¼�Ϊ�������ϼ�Ϊ�������¼�ʱ��ͻ���ϼ����򷵻��¼�����ʱ��
	 * 				2.�¼�Ϊ�������ϼ�Ϊ�������¼�ʱ��δͻ���ϼ����򷵻��ϼ�����ʱ��
	 * 				3.�¼�Ϊר�����ϼ�Ϊר����ϼ�ʱ������¼�����
	 * 				4.�¼�Ϊר�����ϼ�Ϊ������¼�������ͻ������
	 * 				5.���û���¼�����ֱ�ӷ��ص�ǰ��endDate
	 * @param parentTask
	 */
	private Date getChildLatestEndDate(FDCScheduleTaskInfo parentTask) {
		Date latestDate=null;
		FDCScheduleTaskCollection childTasks = getChildTasks(parentTask);
		FDCScheduleTaskInfo childInfo = null;
		for(Iterator iter=childTasks.iterator();iter.hasNext();){
			FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
			TaskAdjustManagerItem taskItem = getTaskListItem(task);
			if(taskItem!=null&&taskItem.newTask!=null){
				task=taskItem.newTask;
			}
			if(latestDate==null){
				latestDate=task.getEnd();
				childInfo = task;
			}else if(latestDate.before(task.getEnd())){
				latestDate=task.getEnd();
				childInfo = task;
			}
		}
		if(childInfo == null) return parentTask.getEnd();
		if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(parentTask.getWbs().getTaskType().getId().toString())){			
			if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(childInfo.getWbs().getTaskType().getId().toString())){			
				if(parentTask.getEnd().before(childInfo.getEnd())){							
					latestDate = childInfo.getEnd();			//�ϼ�Ϊ����&&�¼�Ϊ����&&�¼�ͻ���ϼ�
				}else{
					latestDate = parentTask.getEnd();			//�ϼ�Ϊ����&&�¼�Ϊ����&&�¼�δͻ���ϼ�
				}
			}else if(TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(childInfo.getWbs().getTaskType().getId().toString())){
				if(parentTask.getEnd().before(childInfo.getEnd())){							
								//�ϼ�Ϊ����&&�¼�Ϊר��&&�¼�ͻ���ϼ�
				}else{
					latestDate = parentTask.getEnd();			//�ϼ�Ϊ����&&�¼�Ϊר��&&�¼�δͻ���ϼ�
				}
			}
		}else if(TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(parentTask.getWbs().getTaskType().getId().toString())){
			if(parentTask.getEnd().before(childInfo.getEnd())){							
				latestDate = childInfo.getEnd();			//�ϼ�Ϊר��&&�¼�Ϊר��&&�¼�ͻ���ϼ�
			}else{
				latestDate = parentTask.getEnd();			//�ϼ�Ϊר��&&�¼�Ϊר��&&�¼�δͻ���ϼ�
			}
		}
		return latestDate;
	}
	
	/**
	 * ������Ӱ����¼��ڵ�
	 * 
	 * �������㷨��GanttProjectһ��:�ϼ��������¼��ڲ����ϼ��Ŀ�ʼʱ��/����ʱ������ĵ������������Χ��
	 * @param parentAdjustType ��Ҫ�������ֵ��������ڵ�(�ֹ�����),�������ڵ�(ǰ��������ϼ�������)
	 * @param newTask
	 * @param childTask
	 * @return
	 */
	private TaskAdjustManagerItem adjustChildAffectTask(TaskAdjustManagerItem parentItem, FDCScheduleTaskInfo childTask) {
		TaskAdjustManagerItem childItem=getTaskListItem(childTask);
		if(childItem==null||!childItem.type.equals(TaskAdjustManagerItem.TYPE_NOCHANGE)){
			//�Ѿ�������,�����ظ�,��Ȼ����ѭ��
			return null;
		}
		boolean isParentManAdjust=parentItem.isParentManAdjust;
		FDCScheduleTaskInfo newTask=parentItem.newTask;
		FDCScheduleTaskInfo oldTask=parentItem.oldTask;
		if(newTask==null){
			return null;
		}
		if(isParentManAdjust){			
			//���ڸı�
			if(newTask.getStart().compareTo(childTask.getStart())>0 
					||newTask.getEnd().compareTo(childTask.getEnd())<0){
				childItem.newTask=cloneTask(childTask);
				if(newTask.getStart().compareTo(childTask.getStart())>0){
					childItem.newTask.setStart(newTask.getStart());
				}
				if(newTask.getEnd().compareTo(childTask.getEnd())<0){
					childItem.newTask.setEnd(newTask.getEnd());
				}
				childItem.newTask.setEffectTimes(ScheduleCalendarHelper.getEffectTimes(childItem.newTask.getStart(), childItem.newTask.getEnd(), calendarInfo));
			}else{
				//����ǰ�����ϼ���ʼʱ��,����ʱ����¼������ڵ�����Ҳ�����ϼ��Ŀ�ʼʱ��,�����Ա�֤�ϼ������¼�����
				if(oldTask!=null){
					if(oldTask.getStart().compareTo(childTask.getStart())==0){
						if(childItem.newTask==null){
							childItem.newTask=cloneTask(childTask);
						}
						childItem.newTask.setStart(newTask.getStart());
						childItem.newTask.setEnd(ScheduleCalendarHelper.getEndDate(newTask.getStart(), childItem.newTask.getEffectTimes(), calendarInfo));
					}
					if(oldTask.getEnd().compareTo(childTask.getEnd())==0){
						if(childItem.newTask==null){
							childItem.newTask=cloneTask(childTask);
						}
						childItem.newTask.setEnd(newTask.getEnd());
					}
					if(childItem.newTask != null){
						childItem.newTask.setEffectTimes(ScheduleCalendarHelper.getEffectTimes(childItem.newTask.getStart(), childItem.newTask.getEnd(), calendarInfo));
					}
				}
				
			}
			
		}else{
			//���ڲ���
			if(newTask.getStart().compareTo(childTask.getStart())>0 
					||newTask.getEnd().compareTo(childTask.getEnd())<0){
				childItem.newTask=cloneTask(childTask);
				if(newTask.getStart().compareTo(childTask.getStart())>0){
					childItem.newTask.setStart(newTask.getStart());
					childItem.newTask.setEnd(ScheduleCalendarHelper.getEndDate(newTask.getStart(), newTask.getEffectTimes(), calendarInfo));
				}
				if(newTask.getEnd().compareTo(childTask.getEnd())<0){
					childItem.newTask.setEnd(newTask.getEnd());
					childItem.newTask.setStart(ScheduleCalendarHelper.getStartDate(newTask.getEnd(), newTask.getEffectTimes(), calendarInfo));
				}
			}
		}
		if(childItem!=null){
			childItem.isParentManAdjust=isParentManAdjust;
			if(TaskAdjustManagerItem.TYPE_ADJUSTTASK.equals(childItem.type)
					||TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK.equals(childItem.type)){
				childItem.type=TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK;
			}else{
				childItem.type=TaskAdjustManagerItem.TYPE_AFFECTTASK;
			}
		}
		return childItem;
	}
	
	/**
	 * ��������Ҫ���е�����ʼ�����ĵ���
	 * @param newTask �仯�������ڵ�
	 * @param depend
	 */
	private TaskAdjustManagerItem adjustDependTaskAffectTask(FDCScheduleTaskInfo sourceTask,FDCScheduleTaskInfo newTask, FDCScheduleTaskDependInfo depend) {
		if(newTask==null){
			return null;
		}
		//only adjust diffecttimes
		FDCScheduleTaskInfo dependTask = depend.getDependTask();
		TaskAdjustManagerItem sourceItem = null;
		for(int i=0;i<taskItems.size();i++){
			TaskAdjustManagerItem tempItem = get(i);
			if(tempItem.getOldTask()!= null && sourceTask.getId().toString().equals(tempItem.getOldTask().getId().toString())){
				sourceItem = tempItem;
			}
		}
		TaskAdjustManagerItem dependItem=getTaskListItem(dependTask);
		if(dependItem==null||dependItem.type.equals(TaskAdjustManagerItem.TYPE_NOCHANGE)){
			//�Ѿ�������,�����ظ�,��Ȼ����ѭ��
			return null;
		}
		if(dependItem.newTask == null){
			dependItem.newTask=cloneTask(dependTask);
		}else{
//			Object object = dependTask.remove("taskListItem");
//			dependItem.newTask.put("taskListItem", object);
		}
		BigDecimal diff=new BigDecimal(String.valueOf(depend.getDifference()+1)); //���ڵ�����Ч����Ϊ1
		BigDecimal effectedStartDiff = FDCHelper.subtract(ScheduleCalendarHelper.getNatureTimes(
				dependItem.getOldTask().getStart(), dependItem.getNewTask().getStart()),FDCHelper.ONE);
		BigDecimal effectedEndDiff = FDCHelper.subtract(ScheduleCalendarHelper.getNatureTimes(
				dependItem.getOldTask().getEnd(), dependItem.getNewTask().getEnd()),FDCHelper.ONE);
		if(depend.getType()==TaskLinkTypeEnum.FINISH_START){
			//start=newTask.end+difference
			//���+1=��Ч����
			if(sourceTask.getId().toString().equals(newTask.getId().toString())){
				dependItem.newTask.setStart(ScheduleCalendarHelper.getEndDate(newTask.getEnd(), FDCHelper.add(diff, effectedStartDiff), calendarInfo));
				dependItem.newTask.setEnd(ScheduleCalendarHelper.getEndDate(dependItem.newTask.getStart(), FDCHelper.add(effectedEndDiff,dependTask.getEffectTimes()), calendarInfo));
			}else{
				dependItem.newTask.setStart(ScheduleCalendarHelper.getEndDate(
						newTask.getEnd(),FDCHelper.add(diff, FDCHelper.subtract(sourceItem.getOldTask().getStart(), 
								sourceItem.getNewTask().getStart())), calendarInfo));
				dependItem.newTask.setEnd(ScheduleCalendarHelper.getEndDate(
						dependItem.newTask.getStart(), dependItem.newTask.getEffectTimes(), calendarInfo));
			}
		}
		if(depend.getType()==TaskLinkTypeEnum.FINISH_FINISH){
			//end=newTask.end+difference
			dependItem.newTask.setEnd(ScheduleCalendarHelper.getEndDate(newTask.getEnd(), diff, calendarInfo));
			dependItem.newTask.setStart(ScheduleCalendarHelper.getStartDate(dependItem.newTask.getEnd(), dependTask.getEffectTimes(), calendarInfo));
		}
//		if(depend.getType()==TaskLinkTypeEnum.START_FINISH){
//			//end=newTask.start+difference
//			dependItem.newTask.setEnd(ScheduleCalendarHelper.getEndDate(newTask.getStart(), diff, calendarInfo));
//			dependItem.newTask.setStart(ScheduleCalendarHelper.getStartDate(dependTask.getEnd(), dependTask.getEffectTimes(), calendarInfo));
//
//		}
		if(depend.getType()==TaskLinkTypeEnum.START_START){
			//start=newTask.start+difference
			dependItem.newTask.setStart(ScheduleCalendarHelper.getEndDate(newTask.getStart(), diff, calendarInfo));
			dependItem.newTask.setEnd(ScheduleCalendarHelper.getEndDate(dependItem.newTask.getStart(), dependTask.getEffectTimes(), calendarInfo));
		}
//		dependItem.newTask.setEffectTimes(ScheduleCalendarHelper.getEffectTimes(dependItem.newTask.getStart(), dependItem.newTask.getEnd(), calendarInfo));
		if(dependItem!=null){
			if(TaskAdjustManagerItem.TYPE_ADJUSTTASK.equals(dependItem.type)
					||TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK.equals(dependItem.type)){
				dependItem.type=TaskAdjustManagerItem.TYPE_ADJUSTANDTAFFECTTASK;
			}else{
				dependItem.type=TaskAdjustManagerItem.TYPE_AFFECTTASK;
			}
		}
		return dependItem;
	
	}

	private TaskAdjustManagerItem getTaskListItem(FDCScheduleTaskInfo task){
		return (TaskAdjustManagerItem)task.get("taskListItem");
	}
	
	private FDCScheduleTaskInfo cloneTask(FDCScheduleTaskInfo task){
		Object object = task.remove("taskListItem");
		FDCScheduleTaskInfo newTask=new FDCScheduleTaskInfo();
		newTask.putAll(task);
		task.put("taskListItem", object);
		return newTask;
	}
	public class TaskAdjustManagerItem implements Serializable{
		/**
		 * �ϼ��ڵ㼰���ϼ��ڵ��Ƿ����ֹ�����
		 * �������Ӱ���ϼ������¼���ʱ���Ƿ������ڱ仯
		 */
		private boolean isParentManAdjust=false;
		private String type=TYPE_NOCHANGE;
		/**
		 * δ�ı������
		 */
		public final static String TYPE_NOCHANGE="nochange";
		/**
		 * �ֹ������˵�����
		 */
		public final static String TYPE_ADJUSTTASK="adjustTask";
		/**
		 * ����������
		 */
		public final static String TYPE_NEWTASK="newTask";
		/**
		 * ��Ϊ�����������������������ĵ���
		 */
		public final static String TYPE_AFFECTTASK="affectTask";
		
		/**
		 * ����һ�����ͣ��ֹ��������ұ�������������Ӱ��ĵ���
		 */
		public final static String TYPE_ADJUSTANDTAFFECTTASK = "adjustAndAffectTask";
		
		private boolean isDelete=false;
		
		private final FDCScheduleTaskInfo oldTask;
		private FDCScheduleTaskInfo newTask;
		private Object source=null;
		private String taskId=null;
		private String parentTaskId=null;
		private TaskAdjustManagerItem(FDCScheduleTaskInfo task){
			this.oldTask=task;
			taskId=task.getId().toString();
			parentTaskId=task.getParent()!=null?task.getParent().getId().toString():null;
		}
		
		public String getType(){
			return type;
		}
		
		public void setType(String str){
			this.type = str;
		}
		
		private void update(ScheduleAdjustTaskEntryInfo entry){
			if(entry!=null){
				newTask=cloneTask(this.oldTask);
				setNewTaskValue(entry);
				source=entry;
				isDelete=false;
				type=TYPE_ADJUSTTASK;
			}else{
				newTask=null;
				source=null;
				isDelete=true;
				type=TYPE_NOCHANGE;
			}
		}
		
		private TaskAdjustManagerItem(ScheduleNewTaskEntryInfo entry){
			this.oldTask=null;
			newTask=new FDCScheduleTaskInfo();
			setNewTaskValue(entry);
			newTask.setId(BOSUuid.create(newTask.getBOSType()));
			taskId=entry.getId().toString();
			//TODO H ��һЩԤ����
			parentTaskId=entry.getParentTask()!=null?entry.getParentTask().getId().toString():null;
			source=entry;
			type=TYPE_NEWTASK;
			
			addChildTask(newTask);
		}
		
		public FDCScheduleTaskInfo getOldTask(){
			return this.oldTask;
		}
		
		public FDCScheduleTaskInfo getNewTask(){
			return this.newTask;
		}
		
		private void setNewTaskValue(ScheduleAdjustTaskEntryInfo entry){
			newTask.setStart(entry.getStartDate());
			newTask.setEnd(entry.getEndDate());
			newTask.setEffectTimes(entry.getEffectTimes());
		}
		
		private void setNewTaskValue(ScheduleNewTaskEntryInfo entry){
			newTask.putAll(entry);
			newTask.setLongNumber(entry.getLongNumber());
			if(entry.getParentTask()!=null){
				String taskId=entry.getParentTask().getId().toString();
				for(int i=0,size=size();i<size;i++){
					TaskAdjustManagerItem item = get(i);
					if(item.taskId.equals(taskId)){
						newTask.setParent(item.getOldTask());
						newTask.setSchedule(item.getOldTask().getSchedule());
						break;
					}
				}
			}
			
		}
		private void updateNewTask(){
			if(newTask==null&&oldTask!=null){
				newTask=cloneTask(oldTask);
			}
			if(newTask==null){
				return;
			}
			Object obj=source;
			if(obj instanceof ScheduleNewTaskEntryInfo){
				setNewTaskValue((ScheduleNewTaskEntryInfo)obj);
			}
			if(obj instanceof ScheduleAdjustTaskEntryInfo){
				setNewTaskValue((ScheduleAdjustTaskEntryInfo)obj);
			}
		}
		
	}
}
