package com.kingdee.eas.fdc.schedule.framework.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Relation;
import net.sf.mpxj.RelationType;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mpx.MPXReader;
import net.sf.mpxj.mspdi.MSPDIReader;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSTree;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.DependHardnessEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.util.KDEntityTree.KDEntityTreeNode;

public class KDProjectFileReader {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.framework.util.KDProjectFileReader");
	private ProjectFile mpx = null;
	private FDCScheduleInfo schedule;
	private FDCScheduleTaskInfo parentTask;
	private FDCWBSTree wbsTree;
	
	/* �����ˡ����β����Ƿ�Ϊ�� */
	public Map isPersonNulls = null;
	public Map isDeptNulls = null;
	
	/* �����ˡ����β����Ƿ�Ϊ�ظ� */
	public Map isPersonRepeats = null;
	public Map isDeptRepeats = null;
	
	/* ���������� */
	private List allPersonList;
	private Map allPersonsMap;
	
	/* �������β��� */
	private List allCostCenterOrgUnitList;
	private Map allCostCenterOrgUnitMap;

	public KDProjectFileReader(FDCScheduleInfo schedule, File file,FDCScheduleTaskInfo parentTask, FDCWBSTree wbsTree) throws MPXJException {
		try {
			this.wbsTree = wbsTree;
			this.schedule = schedule;
			this.parentTask = parentTask;
			isPersonNulls = new HashMap();
			isDeptNulls = new HashMap();
			isPersonRepeats = new HashMap();
			isDeptRepeats = new HashMap();
			allPersonList = new ArrayList();
			allCostCenterOrgUnitList = new ArrayList();
			allPersonsMap = new HashMap();
			allCostCenterOrgUnitMap = new HashMap();
			setDepartmentAndPersonValue();
			mpx = new MPPReader().read(file);
		} catch (Exception e) {
			try {
				mpx = new MPXReader().read(file);
			} catch (Exception ex) {
				mpx = new MSPDIReader().read(file);
			}
		}
	}
	
	/**
	 * @discription  <Ϊ�����ˡ����β��Ÿ�ֵ>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/08/24> <p>
	 * @param        <��>
	 * @return       <����ֵ����>
	 * 
	 * modifier      <��> <p>
	 * modifyDate    <��> <p>
	 * modifyInfo	 <��> <p>
	 * @see          <��ص���>
	 */
	public void setDepartmentAndPersonValue(){
		try {
			PersonCollection personCollection = PersonFactory.getRemoteInstance().getPersonCollection();
			for(int k = 0; k < personCollection.size(); k ++){
				PersonInfo personInfo = personCollection.get(k);
				allPersonList.add(personInfo.getName());
				allPersonsMap.put(personInfo.getName().toString(), personInfo);
			}
			
			CostCenterOrgUnitCollection costCenterOrgUnitCollection = CostCenterOrgUnitFactory.getRemoteInstance().getCostCenterOrgUnitCollection();
			for(int k = 0; k < costCenterOrgUnitCollection.size(); k ++){
				CostCenterOrgUnitInfo costCenterOrgUnitInfo = costCenterOrgUnitCollection.get(k);
				allCostCenterOrgUnitList.add(costCenterOrgUnitInfo.getName());
				allCostCenterOrgUnitMap.put(costCenterOrgUnitInfo.getName().toString(), costCenterOrgUnitInfo);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @discription  <�������ƺ����ͻ�������˻����β��ŵĸ���>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/08/24> <p>
	 * @param        <��>
	 * @return       <����ֵ����>
	 * 
	 * modifier      <��> <p>
	 * modifyDate    <��> <p>
	 * modifyInfo	 <��> <p>
	 * @see          <��ص���>
	 */
	public int getCountByName(String name,String type){
		int count = 0;
		if("person".equals(type)){
			for(int k = 0; k < allPersonList.size(); k ++){
				if(null != allPersonList.get(k) && name.equals(allPersonList.get(k).toString().trim())){
					++ count; 
				}
			}
		}else{
			for(int k = 0; k < allCostCenterOrgUnitList.size(); k ++){
				if(null != allCostCenterOrgUnitList.get(k) && name.equals(allCostCenterOrgUnitList.get(k).toString().trim())){
					++ count; 
				}
			}
		}
		return count;
	}
	
	/**
	 * ���������Ƿ���ָ����ʱ�䷶Χ��
	 * @param begin
	 * @param end
	 * @return
	 */
	public boolean verifyDateRange(Date begin, Date end){
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Task rootTask = (Task) mpx.getChildTasks().get(0);
		return df.format(rootTask.getStart()).compareTo(df.format(begin)) >= 0 && df.format(rootTask.getFinish()).compareTo(df.format(end)) <= 0;
		
	}
	public String getOnlyRootName(){
		Task first = (Task)mpx.getChildTasks().get(0);
		if(first.getChildTasks().size() == 1){
			return ((Task)first.getChildTasks().get(0)).getName();
		}
		return null;
	}
	public boolean isEmptyTask(){
		return mpx.getAllTasks().size() <= 1;
	}
	public boolean existEmptyName(){
		List projectTasks = mpx.getAllTasks();
		for (int i = 1; i < projectTasks.size(); ++i) {
			Task task = (Task) projectTasks.get(i);
			if(task.getName() == null || task.getName().trim().length() == 0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ������ У��Project��������Ŀ���������Ƿ�һ��
	 * @return
	 * ����ʱ�䣺2010-7-30
	 * �����ˣ�zhiqiao_yang
	 */
//	public boolean verifyCalendar(){
//		ScheduleCalendarInfo scheduleCalendar = schedule.getCalendar();
//		ProjectCalendar projectCalendar = mpx.getBaseCalendar(mpx.getProjectHeader().getCalendarName());
//		System.out.println(mpx.getProjectHeader().getCalendarName());
//		if(scheduleCalendar != null  ){
//			if(projectCalendar != null){
//				if(!equalCalendar(scheduleCalendar,projectCalendar)){
//					return false;
//				}
//			}
//			List projectTasks = mpx.getAllTasks();
//			for (int i = 1; i < projectTasks.size(); ++i) {//���˵���һ�������Ǹ������ñȽ�
//				Task task = (Task) projectTasks.get(i);
//				ProjectCalendar taskCalendar = task.getCalendar();
//				if(taskCalendar != null){
//					if(!equalCalendar(scheduleCalendar,taskCalendar)){
//						return false;
//					}
//				}				
//			}
//			return true;
//		}		
//		return false;
//	}
//	private boolean equalCalendar(ScheduleCalendarInfo scheduleCalendar, ProjectCalendar projectCalendar){
//		//��schedule��project��ʱ��鲻��
//		//scheduleCalendar.isWeekTime(date)
//		//String.valueOf((date.getYear()+1900)*10000+(date.getMonth()+1)*100+date.getDate());
//		//��ScheduleCalendarΪ��׼���Ƚ���ĩ�Ƿ���ͬ
//		DefaultWeekendEntryCollection weekendEntry =(DefaultWeekendEntryCollection)scheduleCalendar.getWeekendEntrys();
//		for(Iterator it =weekendEntry.iterator();it.hasNext();){
//			DefaultWeekendEntryInfo weekend = (DefaultWeekendEntryInfo)it.next();
//			ScheduleWeekendEnum schWeekend = (ScheduleWeekendEnum)weekend.getWeekend();
//			//ScheduleCalendar������һΪ��Ϣ�գ���ProjectCalendar������һ�ǹ�����
//			if(schWeekend.equals(ScheduleWeekendEnum.Monday) && projectCalendar.isWorkingDay(Day.MONDAY)){
//				FDCMsgBox.showError("����һ���������ò�һ�£�");
//				return false;
//			}
//			//ScheduleCalendar�����ڶ�Ϊ��Ϣ�գ���ProjectCalendar�����ڶ��ǹ�����
//			if(schWeekend.equals(ScheduleWeekendEnum.Tuesday) && projectCalendar.isWorkingDay(Day.TUESDAY)){
//				FDCMsgBox.showError("���ڶ����������ò�һ�£�");
//				return false;
//			}
//			//ScheduleCalendar��������Ϊ��Ϣ�գ���ProjectCalendar���������ǹ�����
//			if(schWeekend.equals(ScheduleWeekendEnum.Wednesday) && projectCalendar.isWorkingDay(Day.WEDNESDAY)){
//				FDCMsgBox.showError("���������������ò�һ�£�");
//				return false;
//			}
//			//ScheduleCalendar��������Ϊ��Ϣ�գ���ProjectCalendar���������ǹ�����
//			if(schWeekend.equals(ScheduleWeekendEnum.Thursday) && projectCalendar.isWorkingDay(Day.THURSDAY)){
//				FDCMsgBox.showError("�����ĵ��������ò�һ�£�");
//				return false;
//			}
//			//ScheduleCalendar��������Ϊ��Ϣ�գ���ProjectCalendar���������ǹ�����
//			if(schWeekend.equals(ScheduleWeekendEnum.Friday) && projectCalendar.isWorkingDay(Day.FRIDAY)){
//				FDCMsgBox.showError("��������������ò�һ�£�");
//				return false;
//			}
//			//ScheduleCalendar��������Ϊ��Ϣ�գ���ProjectCalendar���������ǹ�����
//			if(schWeekend.equals(ScheduleWeekendEnum.Saturday) && projectCalendar.isWorkingDay(Day.SATURDAY)){
//				FDCMsgBox.showError("���������������ò�һ�£�");
//				return false;
//			}
//			//ScheduleCalendar��������Ϊ��Ϣ�գ���ProjectCalendar���������ǹ�����
//			if(schWeekend.equals(ScheduleWeekendEnum.Sunday) && projectCalendar.isWorkingDay(Day.SUNDAY)){
//				FDCMsgBox.showError("�����յ��������ò�һ�£�");
//				return false;
//			}
//		}
//		
//		//��ProjectCalendarΪ��׼���Ƚ���Ϣ���Ƿ���ͬ
//		List exceptions = projectCalendar.getCalendarExceptions();//��ĩ
//		for(int i = 0; i < exceptions.size(); ++i){
//			ProjectCalendarException exception = (ProjectCalendarException) exceptions.get(i);
//			//ÿ��������Ϣ�գ���ʱ��Σ�
//			if(!exception.getWorking()){
//				Date fromDate = exception.getFromDate();
//				Date toDate =exception.getToDate();
//				for(Date date=fromDate;!toDate.before(date);){
//					//��Ӧ��ʱ����ScheduleCalendar���ǹ�����
//					if(!scheduleCalendar.isWeekTime(date)){
//						FDCMsgBox.showError(date.toString().substring(date.toString().length()-4)+"/"+(date.getMonth()+1)+"/"+date.getDate()+"�������ò�һ�£�");
//						return false;
//					}
//					date.setDate(date.getDate()+1); 
//
//				}
//			}			
//		}
//			
//		//��ScheduleCalendarΪ��׼���ȽϽڼ����Ƿ���ͬ
//		HolidayEntryCollection holidayEntry =(HolidayEntryCollection)scheduleCalendar.getHolidayEntrys();
//		for(Iterator it =holidayEntry.iterator();it.hasNext();){
//			HolidayEntryInfo holiday = (HolidayEntryInfo)it.next();
//			Date holidayDate = holiday.getDate();
//			//ScheduleCalendar�е�ÿ��ڼ�����ProjectCalendar��Ϊ������
//			if(!projectCalendar.isWorkingDate(holidayDate)){
//				FDCMsgBox.showError(holidayDate+"�ڼ������ò�һ�£�");
//				return false;
//			}
//		}
//			
//
//		
//		return true;
//	}
	private void removeScheduleWbs() {
		if (!isSpecialSchedule()) {
			Map deleteWbs = null;
			if (schedule.containsKey("delete_wbs")) {
				deleteWbs = (HashMap) schedule.get("delete_wbs");
			} else {
				deleteWbs = new HashMap();
				schedule.put("delete_wbs", deleteWbs);
			}
			if (wbsTree != null) {
				deleteWbs.putAll(wbsTree.getId2WBSMap());
			} else {
				FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
				for (int i = 0; i < taskEntrys.size(); ++i) {
					FDCScheduleTaskInfo taskInfo = taskEntrys.get(i);
					taskInfo.getWbs().put("isDelete", Boolean.TRUE);

					deleteWbs.put(taskInfo.getWbs().getId().toString(), taskInfo.getWbs());
				}
			}
			if (deleteWbs.isEmpty()) {
				schedule.remove("delete_wbs");
			}
			wbsTree.clear();
			schedule.getTaskEntrys().clear();

		}
	}

	//����WBS
	private FDCWBSInfo createWBS(Integer rootId,Task task, Map projectTaskId2FdcTask){
		FDCWBSInfo wbs = new FDCWBSInfo();
		wbs.setId(BOSUuid.create(wbs.getBOSType()));
		Integer parentId = task.getParentTask().getUniqueID();
		if (!rootId.equals(parentId)) {
			FDCScheduleTaskInfo parent = (FDCScheduleTaskInfo) projectTaskId2FdcTask.get(parentId); 
			wbs.setParent(parent.getWbs());
		}else if (isSpecialSchedule()) {
			wbs.setParent(parentTask.getWbs());
		}		
		wbs.setIsEnabled(true);
		wbs.setBoolean("isNew", true);
		wbs.setIsFromTemplate(false);
		wbs.setCurProject(schedule.getProject());
		wbs.setName(task.getName());		
		wbs.setTaskType(schedule.getScheduleType());
		if (isSpecialSchedule()) {
			//ר����̳���ѡ�ڵ�������˺����β���
			wbs.setAdminPerson(parentTask.getWbs().getAdminPerson());
			wbs.setRespDept(parentTask.getWbs().getRespDept());
		}else{			
			//���������üƻ�����Ϊ��½�û��ĳɱ�����
			wbs.setAdminDept(SysContext.getSysContext().getCurrentCostUnit().castToFullOrgUnitInfo());
		}
		wbs.setIsUnVisible(false);
		BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(task.getStart(), task.getFinish(), schedule.getCalendar());
		wbs.setEstimateDays(effectTimes.intValue());
		
		return wbs;
	}
	private Date clearTime(Date date){
		Calendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MILLISECOND, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		return gc.getTime();
	}
	/**
	 * ��������Project�ļ��е����񼰹�ϵת��ΪFDC���ȼƻ�������ģ��
	 * @param isRemoveRoot ��Project�ļ��д��ڵ����ĸ������Ƿ�ɾ���˸�����
	 * @return 
	 * ����ʱ�䣺2010-7-22
	 * �����ˣ�zhiqiao_yang
	 */
	public FDCScheduleTaskCollection getFDCScheduleTask(boolean isRemoveRoot) {
		//ɾ���ƻ������е�WBS
		removeScheduleWbs();
		List projectTasks = mpx.getAllTasks();
		FDCScheduleTaskCollection fdcTasks = new FDCScheduleTaskCollection();
		//Project����idӳ��FDC���񣬱����ڴ���FDC���ϵʱʹ��
		Map projectTaskId2FdcTask = new HashMap();
		//��������
		processTasks(isRemoveRoot, projectTasks, projectTaskId2FdcTask, fdcTasks);
		//�����ϵ
		processDepends(projectTasks, projectTaskId2FdcTask);
		schedule.setInt("initIndex", schedule.getTaskEntrys().size());
		return fdcTasks;
	}
	private void processTasks(boolean isRemoveRoot, List projectTasks, Map projectTaskId2FdcTask, FDCScheduleTaskCollection fdcTasks) {
		ScheduleCalendarInfo calendarInfo = schedule.getCalendar();
		//�ش��ڵ�һ������������������ΪProject�ļ���, ����Դ�����
		final int begin = isRemoveRoot? 2 : 1;
		Integer rootId = ((Task) projectTasks.get(begin - 1)).getUniqueID();
		for (int i = begin; i < projectTasks.size(); ++i) {
			Task task = (Task) projectTasks.get(i);
			Integer id = task.getUniqueID();					
			FDCScheduleTaskInfo fdcTask = new FDCScheduleTaskInfo();
			fdcTask.setId(BOSUuid.create(fdcTask.getBOSType()));
			projectTaskId2FdcTask.put(id, fdcTask);		
			FDCWBSInfo wbs = createWBS(rootId, task, projectTaskId2FdcTask);
			fillTaskBaseInfo(wbs, fdcTask, task, projectTaskId2FdcTask);
			fdcTasks.add(fdcTask);		
			fillTaskTimeInfo(fdcTask, task, calendarInfo);
			fdcTask.setExpand(true);
			Integer parentId = task.getParentTask().getUniqueID();
			setParent(parentId, rootId, fdcTask, projectTaskId2FdcTask);
		}
		sortFDCTask(fdcTasks);
		reCalculateCode(fdcTasks);		
	}
	//����FDC�����parent���߽���Ϣ
	private void setParent(Integer parentId, Integer rootId,FDCScheduleTaskInfo fdcTask, Map projectTaskId2FdcTask){
		boolean isSpecialSchedule = isSpecialSchedule();
		if (rootId.equals(parentId)) {
			fdcTask.setParent(isSpecialSchedule? this.parentTask : null);
			if(isSpecialSchedule){
				fdcTask.setBoundStart(parentTask.getStart());
				fdcTask.setBoundEnd(parentTask.getEnd());	
			}
		} else {
			FDCScheduleTaskInfo parentTask = (FDCScheduleTaskInfo) projectTaskId2FdcTask.get(parentId);
			fdcTask.setParent(parentTask);
			fdcTask.setBoundStart(parentTask.getStart());
			fdcTask.setBoundEnd(parentTask.getEnd());		
		}
	}
	//����WBS���������
	private void reCalculateCode(FDCScheduleTaskCollection fdcTasks){
		FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
		for(int i = 0; i < fdcTasks.size(); ++i){		
			wbsTree.addChild(fdcTasks.get(i).getWbs());
		}
		wbsTree.testPrint();
		wbsTree.reCalculateCode();
		for(int i = 0; i < taskEntrys.size(); ++i){			
			FDCScheduleTaskInfo fdcTask =taskEntrys.get(i);
			FDCWBSInfo wbs = fdcTask.getWbs();
			fdcTask.setIsLeaf(wbs.isIsLeaf());
			fdcTask.setLongNumber(wbs.getLongNumber());
			fdcTask.setNumber(wbs.getLongNumber().replace('!', '.'));
			fdcTask.setLevel(wbs.getLevel());
			fdcTask.setSeq(wbs.getIndex());
		}
	}
	//�������򣬱��ڼ��ص��ƻ���
	private void sortFDCTask(FDCScheduleTaskCollection fdcTasks){
		boolean isSpecialSchedule = isSpecialSchedule();
		if(isSpecialSchedule){
			FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
			String parentTaskId = parentTask.getId().toString();
			KDEntityTree entityTree = new KDEntityTree(schedule.getTaskEntrys(), null);
			KDEntityTreeNode node = entityTree.getNodeById(parentTaskId);
			List brothers = null;
			String nextInsertTaskId = null;
			while(true){
				if(node.getParent() != null){
					brothers = node.getParent().getChildren();
				}else{
					brothers = entityTree.getRoots();
				}
				for(int i = 0; i < brothers.size(); ++i){
					if(node.equals(brothers.get(i))){
						if(i + 1 < brothers.size()){
							nextInsertTaskId = ((KDEntityTreeNode)brothers.get(i+1)).getId();
						}	
					}
				}
				if(node.getParent() == null || nextInsertTaskId != null){
					break;
				}
				node = node.getParent();
			}
			if(nextInsertTaskId == null){
				schedule.getTaskEntrys().addCollection(fdcTasks);
			}else{
				int insertIndex = 0;
				for(int i = 0; i < schedule.getTaskEntrys().size(); ++i){
					if(nextInsertTaskId.equals(schedule.getTaskEntrys().get(i).getId().toString())){
						insertIndex = i;
						break;
					}
				}
				for(int i = 0; i < fdcTasks.size(); ++i){
					taskEntrys.addObject(insertIndex + i, fdcTasks.get(i));
				}
			}
		}else{
			schedule.getTaskEntrys().addCollection(fdcTasks);
		}
	}

	//����FDC�����������
	private void fillTaskBaseInfo(FDCWBSInfo wbs, FDCScheduleTaskInfo fdcTask, Task task, Map projectTaskId2FdcTask){
		fdcTask.setWbs(wbs);
		fdcTask.setAdminDept(wbs.getRespDept());
		fdcTask.setPlanDept(wbs.getAdminDept());
		fdcTask.setAdminPerson(wbs.getAdminPerson());
		fdcTask.setName(wbs.getName());
		fdcTask.setSchedule(schedule);
		fdcTask.setLocked(wbs.isIsLocked());
		fdcTask.setIsAdd(true);
		fdcTask.setIsScheduleTask(true);
		fdcTask.setPriority(1);
		validatePersonAndDept(fdcTask ,task,task.getText2(),task.getText3());
	}
	
	/**
	 * @discription  <��֤�����˻����β����Ƿ��ظ�>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/08/24> <p>
	 * @param        <��>
	 * @return       <����ֵ����>
	 * 
	 * modifier      <��> <p>
	 * modifyDate    <��> <p>
	 * modifyInfo	 <��> <p>
	 * @see          <��ص���>
	 */
	public void validatePersonAndDept(FDCScheduleTaskInfo fdcTask, Task task, String person, String dept){
		if(null == person || "".equals(person.trim())){
			isPersonNulls.put(fdcTask.getId().toString(), "������___" + task.getUniqueID());
		}else{
			/* �ж��������Ƿ��ظ� */
			if(getCountByName(person, "person") > 1){
				isPersonRepeats.put(fdcTask.getId().toString(), "������___" + task.getUniqueID());
			}else{
				 if(null != allPersonsMap.get(person)){	
					 fdcTask.setAdminPerson((PersonInfo)allPersonsMap.get(person)); 
				 }
			}
		}
		if(null == dept || "".equals(dept.trim())){
			isDeptNulls.put(fdcTask.getId().toString(), "���β���___" + task.getUniqueID());
		}else{
			/* �ж����β����Ƿ��ظ� */
			if(getCountByName(dept, "dept") > 1){
				isDeptRepeats.put(fdcTask.getId().toString(), "���β���___" + task.getUniqueID());
			}else{
				if(null != allCostCenterOrgUnitMap.get(dept)){	
					 fdcTask.setAdminDept((FullOrgUnitInfo)allCostCenterOrgUnitMap.get(dept)); 
				 }
			}
		}
	}
	
	//����ʱ�乤�ڵ�����		
	private void fillTaskTimeInfo(FDCScheduleTaskInfo fdcTask, Task task, ScheduleCalendarInfo calendarInfo){
		fdcTask.setName(task.getName());
		Date start = task.getStart();
		start = clearTime(start);
		Date end = task.getFinish();
		end = clearTime(end);
		fdcTask.setStart(start);
		fdcTask.setEnd(end);
		BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(fdcTask.getStart(), fdcTask.getEnd(), calendarInfo);
		fdcTask.setEffectTimes(effectTimes);
		fdcTask.setBoolean("isImport", true);
		if(effectTimes.intValue() == 0){
			logger.error("error: duration=0; name="+fdcTask.getName()+"; startDate="+fdcTask.getStart()+"; endDate="+fdcTask.getEnd());
			fdcTask.setDuration(1);
		}else{
			fdcTask.setDuration(effectTimes.intValue());
		}
		fdcTask.setNatureTimes(ScheduleCalendarHelper.getNatureTimes(fdcTask.getStart(), fdcTask.getEnd()));
	}
	private void processDepends(List projectTasks, Map projectTaskId2FdcTask) {
		ScheduleCalendarInfo calendarInfo = schedule.getCalendar();
		for (int i = 1; i < projectTasks.size(); ++i) {
			Task task = (Task) projectTasks.get(i);
			Integer id = task.getUniqueID();
			List pres = task.getPredecessors();
			if (pres != null) {
				for (Iterator iter = pres.iterator(); iter.hasNext();) {
					Relation rel = (Relation) iter.next();
					if (!RelationType.START_FINISH.equals(rel.getType())) {
						Integer preId = rel.getSourceTask().getUniqueID();
						FDCScheduleTaskInfo preFDCTask = (FDCScheduleTaskInfo) projectTaskId2FdcTask.get(preId);
						FDCScheduleTaskInfo fdcTask = (FDCScheduleTaskInfo) projectTaskId2FdcTask.get(id);
						FDCScheduleTaskDependCollection depends = preFDCTask.getDependEntrys();
						FDCScheduleTaskDependInfo depend = new FDCScheduleTaskDependInfo();
						depends.add(depend);
						depend.setTask(preFDCTask);
						depend.setDependTask(fdcTask);
						depend.setType(getFDCLinkType(rel.getType()));
						
						depend.setDifference(getFDCLinkDays(depend, calendarInfo));
						depend.setHardness(DependHardnessEnum.Rubber);
					}
				}
			}
		}
	}

	private int getFDCLinkDays(FDCScheduleTaskDependInfo depend, ScheduleCalendarInfo calendarInfo) {
		FDCScheduleTaskInfo preFDCTask = depend.getTask();
		FDCScheduleTaskInfo fdcTask = depend.getDependTask();
		TaskLinkTypeEnum type = depend.getType();
		int ret = 0;
		if(TaskLinkTypeEnum.FINISH_FINISH.equals(type)){
			ret = ScheduleCalendarHelper.getEffectTimes(preFDCTask.getEnd(), fdcTask.getEnd(), calendarInfo).intValue();
			if(ret > 0){
				ret = ret - 1;
			}
		}else if(TaskLinkTypeEnum.FINISH_START.equals(type)){
			ret =  ScheduleCalendarHelper.getEffectTimes(preFDCTask.getEnd(), fdcTask.getStart(), calendarInfo).intValue();
			if(ret > 0){
				ret = ret - 2;
			}
		}else if(TaskLinkTypeEnum.START_START.equals(type)){
			ret =  ScheduleCalendarHelper.getEffectTimes(preFDCTask.getStart(), fdcTask.getStart(), calendarInfo).intValue();
			if(ret > 0){
				ret = ret - 1;
			}
		}	
		return ret;
	}

	private TaskLinkTypeEnum getFDCLinkType(RelationType relationType) {
		if (RelationType.FINISH_FINISH.equals(relationType)) {
			return TaskLinkTypeEnum.FINISH_FINISH;
		} else if (RelationType.START_START.equals(relationType)) {
			return TaskLinkTypeEnum.START_START;
		} else if (RelationType.FINISH_START.equals(relationType)) {
			return TaskLinkTypeEnum.FINISH_START;
		}
		return null;
	}
	private boolean isSpecialSchedule(){
		if(null != schedule.getScheduleType()){
			return TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(schedule.getScheduleType().getId().toString());
		}else{
			return false;
		}
	}
	
	
}
