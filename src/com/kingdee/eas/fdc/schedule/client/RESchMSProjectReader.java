package com.kingdee.eas.fdc.schedule.client;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.mpxj.Duration;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Relation;
import net.sf.mpxj.RelationType;
import net.sf.mpxj.Task;
import net.sf.mpxj.TimeUnit;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mpx.MPXReader;
import net.sf.mpxj.mspdi.MSPDIReader;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.AchievementTypeCollection;
import com.kingdee.eas.fdc.schedule.AchievementTypeFactory;
import com.kingdee.eas.fdc.schedule.AchievementTypeInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeFactory;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideCollection;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideFactory;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTask;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTaskPredecessor;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.StringUtils;

/**
 * ��ȡMS Project�� �������parse�������ٵ���getTasks�������ɻ�������Ѿ�����������
 * 
 * @author zhiqiao_yang
 * 
 */
public class RESchMSProjectReader {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.framework.util.REMSProjectReader");
	private static NumberFormat NUMBER_FORMAT = new DecimalFormat("00");
	private ProjectFile mpx = null;
	private IRESchTaskCreator taskCreator;
	private IRESchTaskPredecessorCreator predecessorCreator;
	private IRESchCalendar calendar;
	private List schTasks;
	private ScheduleCalendarInfo calendarInfo;
	private Map msTaskId2SchTask;
	private Map adminDept2MSTaskIds;
	private Map adminPerson2MSTaskIds;
	
	/* �ɹ���� */
	private Map achievementTypes;
	
	/* ��׼���� */
	private Map standardTaskGuides;
	
	/* ҵ������ */
	private Map businessTypes;
	
	/* ������� */
	private Map taskTypes;
	
	public static List pasreMSProject(File projectFile,
			IRESchTaskCreator taskCreator,
			IRESchTaskPredecessorCreator predecessorCreator,
			IRESchCalendar calendar) throws Exception {
		RESchMSProjectReader reader = new RESchMSProjectReader(projectFile,
				taskCreator, predecessorCreator, calendar);
		reader.parse();
		return reader.getTasks();
	}
	
	public RESchMSProjectReader(File projectFile,
			IRESchTaskCreator taskCreator,
			IRESchTaskPredecessorCreator predecessorCreator,
			IRESchCalendar calendar)
			throws Exception {
		try {
			this.taskCreator = taskCreator;
			this.predecessorCreator = predecessorCreator;
			this.calendar = calendar;
			if (calendar != null) {
				this.calendarInfo = calendar.getSchedule();
			}
			mpx = new MPPReader().read(projectFile);
		} catch (Exception e) {
			try {
				mpx = new MPXReader().read(projectFile);
			} catch (Exception ex) {
				mpx = new MSPDIReader().read(projectFile);
			}
		}
	}
	
	public void parse() throws BOSException {		
		init();
		// �ش��ڵ�һ������������������ΪProject�ļ���, ����Դ�����
		Task rootTask = (Task) mpx.getChildTasks().get(0);
		// ������Ϣ
		processTasks(rootTask.getChildTasks(), null);
		// ǰ������
		processPredecessors();
		// ���β���������
		processTaskExtProperties();
	}

	private void processTaskExtProperties() throws BOSException {
		fillAdminDept();
		fillAdminPerson();
	}

	private void fillAdminPerson() throws BOSException {
		Set personSet = new HashSet();
		Object[] personObjArray = adminPerson2MSTaskIds.keySet().toArray();
		for(int k = 0; k < personObjArray.length; k ++){
			personSet.add(String.valueOf(null == personObjArray[k] ? "" : personObjArray[k].toString()));
		}
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("name", personSet, CompareType.INCLUDE));
		view.getSelector().add("id");
		view.getSelector().add("name");
		PersonCollection adminPersons = PersonFactory.getRemoteInstance().getPersonCollection(view);
		Map name2Person = new HashMap();
		for (int i = 0; i < adminPersons.size(); ++i) {
			PersonInfo person = adminPersons.get(i);
			if (name2Person.get(person.getName()) == null) {
				name2Person.put(person.getName(), new ArrayList());
			}
			List list = (List) name2Person.get(person.getName());
			list.add(person);
		}
		Set names = name2Person.keySet();
		for (Iterator iter = names.iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			List persons = (List) name2Person.get(name);
			if (persons.size() == 1) {// ��persons.size������1˵�����������ҵ������û���ҵ�ְԱ
				List msTaskIds = (List) adminPerson2MSTaskIds.get(name);
				for (int i = 0; i < msTaskIds.size(); ++i) {
					IRESchTask schTask = (IRESchTask) msTaskId2SchTask.get(msTaskIds.get(i));
					schTask.setAdminPerson((PersonInfo) persons.get(0));
				}
			}
		}
	}

	private void fillAdminDept() throws BOSException {
		Set deptSet = new HashSet();
		Object[] deptObjArray = adminDept2MSTaskIds.keySet().toArray();
		for(int k = 0; k < deptObjArray.length; k ++){
			deptSet.add(String.valueOf(null == deptObjArray[k] ? "" : deptObjArray[k].toString()));
		}
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("name", deptSet, CompareType.INCLUDE));
		view.getSelector().add("id");
		view.getSelector().add("name");
		FullOrgUnitCollection adminDepts = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
		Map name2AdminDepts = new HashMap();
		for (int i = 0; i < adminDepts.size(); ++i) {
			FullOrgUnitInfo adminDept = adminDepts.get(i);
			if (name2AdminDepts.get(adminDept.getName()) == null) {
				name2AdminDepts.put(adminDept.getName(), new ArrayList());
			}
			List list = (List) name2AdminDepts.get(adminDept.getName());
			list.add(adminDept);
		}
		Set names = name2AdminDepts.keySet();
		for (Iterator iter = names.iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			List depts = (List) name2AdminDepts.get(name);
			if (depts.size() == 1) {// ��depts.size������1˵�����������ҵ������û���ҵ���֯
				List msTaskIds = (List) adminDept2MSTaskIds.get(name);
				for (int i = 0; i < msTaskIds.size(); ++i) {
					IRESchTask schTask = (IRESchTask) msTaskId2SchTask.get(msTaskIds.get(i));
					schTask.setAdminDept((FullOrgUnitInfo) depts.get(0));
				}
			}
		}
	}
	
	
	
	private void init() {
		schTasks = new ArrayList();
		msTaskId2SchTask = new HashMap();
		adminDept2MSTaskIds = new HashMap();
		adminPerson2MSTaskIds = new HashMap();
		
		try{
			/* Ϊ�ɹ�����Map����ֵ  */
			achievementTypes = new HashMap();
			AchievementTypeCollection achievementTypeCollection = AchievementTypeFactory.getRemoteInstance().getAchievementTypeCollection();
			for(int i = 0; i < achievementTypeCollection.size(); i ++){
				AchievementTypeInfo achievementTypeInfo = achievementTypeCollection.get(i);
				achievementTypes.put(achievementTypeInfo.getName(), achievementTypeInfo);
			}
			
			/* Ϊҵ������Map����ֵ */
			businessTypes = new HashMap();
			ScheduleBizTypeCollection scheduleBizTypeCollection = ScheduleBizTypeFactory.getRemoteInstance().getScheduleBizTypeCollection();
			for(int k = 0; k < scheduleBizTypeCollection.size(); k ++){
				ScheduleBizTypeInfo scheduleBizTypeInfo = scheduleBizTypeCollection.get(k);
				businessTypes.put(scheduleBizTypeInfo.getName(), scheduleBizTypeInfo);
			}
			
			/* Ϊ��׼����Map����ֵ */
			standardTaskGuides = new HashMap();
			StandardTaskGuideCollection standardTaskGuideCollection = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideCollection();
			for(int j = 0; j < standardTaskGuideCollection.size(); j ++){
				StandardTaskGuideInfo standardTaskGuideInfo = standardTaskGuideCollection.get(j);
				standardTaskGuides.put(standardTaskGuideInfo.getName(), standardTaskGuideInfo);
			}
			
			/* Ϊ�����������ֵ */
			taskTypes = new HashMap();
			for(Iterator taskTypeEnum = RESchTaskTypeEnum.iterator();taskTypeEnum.hasNext();){
				RESchTaskTypeEnum schTaskTypeEnum = (RESchTaskTypeEnum)taskTypeEnum.next();
				taskTypes.put(schTaskTypeEnum.toString(), schTaskTypeEnum);
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}

	private void processTasks(List children, IRESchTask father) {
		if (children != null && !children.isEmpty()) {
			for (int i = 0; i < children.size(); ++i) {
				Task task = (Task) children.get(i);
				IRESchTask schTask = taskCreator.createSchTask();
				if (i < 99) {
					schTask.setNumber(NUMBER_FORMAT.format(i + 1));
				} else {
					schTask.setNumber(String.valueOf(i + 1));
				}
				fillSchTask(task, schTask, father);
				processTasks(task.getChildTasks(), schTask);			
			}
		}
	}

	private void processPredecessors() {
		List tasks = mpx.getAllTasks();
		for (int i = 1; i < tasks.size(); ++i) {
			Task msTask = (Task) tasks.get(i);
			List pres = msTask.getPredecessors();
			if (pres != null && !pres.isEmpty()) {
				IRESchTask schTask = (IRESchTask) msTaskId2SchTask.get(msTask.getUniqueID());
				String predecessorDesc = "";
				for (Iterator iter = pres.iterator(); iter.hasNext();) {
					Relation rel = (Relation) iter.next();
					IRESchTaskPredecessor predecessor = predecessorCreator.createSchTaskPredecessor();
					IRESchTask curTask = (IRESchTask) msTaskId2SchTask.get(rel.getSourceTask().getUniqueID());
					IRESchTask reschTask = (IRESchTask) msTaskId2SchTask.get(rel.getTargetTask().getUniqueID());
					// ������ר��ƻ�����ƻ�ģ����������෴�������ڴ������⴦��
					if (predecessor instanceof FDCScheduleTaskDependInfo) {
						predecessor.setCurrentTask(reschTask);
						predecessor.setPredecessor(curTask);
					} else {
						predecessor.setCurrentTask(curTask);
						predecessor.setPredecessor(reschTask);
					}
					
					TaskLinkTypeEnum preType = convertPredecessorType(rel.getType());
					predecessor.setPredecessorType(preType);
					// project���ʱ�䲻׼����Ҫ����
					int difference = 0;
					if (TaskLinkTypeEnum.FINISH_START.equals(preType)) {
						Date newFinish = msTask.getFinish();
						Date newStart = msTask.getStart();
						Date finish = reschTask.getPlanEnd();
						Date start = curTask.getPlanStart();
						// ǰ��ʱ��Ľ���-��������Ŀ�ʼ
						// ��1-1 ��1-2����Ч������2�죬���Ǵ��ʱ����ʵ��0�����������-2
						// difference =
						// ScheduleCalendarHelper.getEffectTimes(finish, start,
						// calendarInfo).intValue() - 2;
						difference = ScheduleCalendarHelper.getEffectTimes(newFinish, newStart, calendarInfo).intValue() - 2;
					} else if (TaskLinkTypeEnum.START_START.equals(preType)) {
						Date pstart = reschTask.getPlanStart();
						Date astart = curTask.getPlanStart();
						difference = ScheduleCalendarHelper.getEffectTimes(pstart, astart, calendarInfo).intValue() - 1;
					} else if (TaskLinkTypeEnum.FINISH_FINISH.equals(preType)) {
						Date newFinish = msTask.getFinish();
						Date newStart = msTask.getStart();
						Date pend = reschTask.getPlanEnd();
						Date aend = curTask.getPlanEnd();
						difference = ScheduleCalendarHelper.getEffectTimes(pend, aend, calendarInfo).intValue();
						if (difference < 0) {
							difference += 1;
						} else {
							difference -= 1;
						}
					} else {
						continue;// SF�Ȳ�����
					}
					predecessor.setDifferenceDay((int) rel.getLag().getDuration());
					schTask.addPredecessor(predecessor);
					//predecessor.setDifferenceDay(getDay4Duration(rel.getLag())
					// );
					if (!"".equals(predecessorDesc)) {
						predecessorDesc += "," + reschTask.getName();
					} else {
						predecessorDesc += reschTask.getName();
					}
				}
				// Ϊǰ����������������
				if (null != predecessorDesc && predecessorDesc.length() > 255) {
					FDCMsgBox.showInfo("��" + msTask.getID() + "��ǰ���������ַ����Ȳ��ܳ���255���ַ���");
					SysUtil.abort();
				}
				schTask.setPredecessorDesc(predecessorDesc);
			}
		}
	}

	
	private int getDay4Duration(Duration lag) {
		if (TimeUnit.DAYS.equals(lag.getUnits())) {
			return (int) lag.getDuration();
		}
		// TODO �����ʱ���ݲ�֧��
		return 0;
	}

	private TaskLinkTypeEnum convertPredecessorType(RelationType type) {
		if (RelationType.FINISH_FINISH.equals(type)) {
			return TaskLinkTypeEnum.FINISH_FINISH;
		} else if (RelationType.FINISH_START.equals(type)) {
			return TaskLinkTypeEnum.FINISH_START;
		} else if (RelationType.START_START.equals(type)) {
			return TaskLinkTypeEnum.START_START;
		} else if(RelationType.START_FINISH.equals(type)){
			return null;
		}
		
		throw new IllegalArgumentException(type + "is error.");
	}

	protected void fillSchTask(Task msTask, IRESchTask schTask, IRESchTask parentSchTask) {
		msTaskId2SchTask.put(msTask.getUniqueID(), schTask);
		schTasks.add(schTask);
		fillIds(msTask, schTask);
		schTask.setParentTask(parentSchTask);
		
		//�����������
		
		String taskType = String.valueOf(null == msTask.getText2() ? "" : msTask.getText2());
		Object taskTypeObj = taskTypes.get(taskType);
		 if (null != taskTypeObj && !"".equals(taskTypeObj.toString().trim())) {
			schTask.setTaskType((RESchTaskTypeEnum) taskTypeObj);
		} else {
			schTask.setTaskType(RESchTaskTypeEnum.COMMONLY);
		 }
		// if (msTask.getMilestone()) {
		// schTask.setTaskType(RESchTaskTypeEnum.MILESTONE);
		// }
		// if (msTask.getCritical()) {
		// schTask.setTaskType(RESchTaskTypeEnum.KEY);
		// }
		
		//����ҵ������
		String businessType = String.valueOf(null == msTask.getText1() ? "" : msTask.getText1());
		String[] businessTypeArray = businessType.split(",");
		businessType = "";
		for(int f = 0; f < businessTypeArray.length; f ++){
			Object businessTypeObj = businessTypes.get(businessTypeArray[f]);
			if(null != businessTypeObj && !"".equals(businessTypeObj.toString().trim())){
				if (schTask instanceof FDCScheduleTaskInfo) {
					ScheduleTaskBizTypeInfo scheduleTaskBizTypeInfo = new ScheduleTaskBizTypeInfo();
					scheduleTaskBizTypeInfo.setId(BOSUuid.create((new RESchTemplateTaskBizTypeInfo()).getBOSType()));
					scheduleTaskBizTypeInfo.setParent((FDCScheduleTaskInfo) schTask);
					ScheduleBizTypeInfo scheduleBizTypeInfo = (ScheduleBizTypeInfo) businessTypeObj;
					scheduleTaskBizTypeInfo.setBizType(scheduleBizTypeInfo);
					((FDCScheduleTaskInfo) schTask).getBizType().add(scheduleTaskBizTypeInfo);
					if (!"".equals(businessType)) {
						businessType += "," + scheduleBizTypeInfo.getName();
					} else {
						businessType += scheduleBizTypeInfo.getName();
					}
				}else{
					RESchTemplateTaskBizTypeInfo rchTemplateTaskBizTypeInfo = new RESchTemplateTaskBizTypeInfo();
					rchTemplateTaskBizTypeInfo.setId(BOSUuid.create((new RESchTemplateTaskBizTypeInfo()).getBOSType()));
					rchTemplateTaskBizTypeInfo.setParent((RESchTemplateTaskInfo) schTask);
					ScheduleBizTypeInfo scheduleBizTypeInfo = (ScheduleBizTypeInfo) businessTypeObj;
					rchTemplateTaskBizTypeInfo.setBizType(scheduleBizTypeInfo);
					schTask.getRESchBusinessType().add(rchTemplateTaskBizTypeInfo);
					if (!"".equals(businessType)) {
						businessType += "," + scheduleBizTypeInfo.getName();
					} else {
						businessType += scheduleBizTypeInfo.getName();
					}
				}
				
				
			}
		}
		//Ϊǰ����������������
		if(null != businessType && businessType.length() > 255){
			FDCMsgBox.showInfo("��" + msTask.getID() + "��ҵ���������ַ����Ȳ��ܳ���255���ַ���");
			SysUtil.abort();
		}
		schTask.setRESchBusinessTypeDesc(businessType);
		
		//���òο�����
		schTask.setReferenceDay(BigDecimal.valueOf(msTask.getDuration().getDuration()).intValue() == 0 ? 1 : BigDecimal.valueOf(
				msTask.getDuration().getDuration()).intValue());
		
		//���ñ�׼����ָ��
		String staObj = String.valueOf(null == msTask.getText5() ? "" : msTask.getText5());
		Object objGuide = standardTaskGuides.get(staObj.trim());
		if(null != objGuide && !"".equals(objGuide.toString().trim())){
			schTask.setStandardTask((StandardTaskGuideInfo)objGuide);
		}
		
		//���óɹ����
		Object objAchiev = achievementTypes.get(String.valueOf(null == msTask.getText4() ? "" : msTask.getText4()).trim());
		if(null != objAchiev && !"".equals(objAchiev.toString().trim())){
			schTask.setAchievementType((AchievementTypeInfo)objAchiev);
		}
		
		//���ñ�ע
		schTask.setDescription(msTask.getText8());
		if(null != msTask.getText8() && msTask.getText8().length() > 255){
			FDCMsgBox.showInfo("��" + msTask.getID() + "�б�ע���ַ����Ȳ��ܳ���255���ַ���");
			SysUtil.abort();
		}
		
		//���ÿ�������
		schTask.setCheckDate(msTask.getDate1());
		
		if(null != msTask.getGUID()){
			schTask.setMsProjectId(msTask.getGUID().toString());
		}else{
			schTask.setMsProjectId(msTask.getUniqueID().toString());
		}
		if(null != msTask.getParentTask() && null != msTask.getParentTask().getGUID()){
			schTask.setMsParentPrjId(msTask.getParentTask().getGUID().toString());
		}else{
			schTask.setMsParentPrjId(msTask.getParentTask().getUniqueID().toString());
		}
		if(null != msTask.getText8() && msTask.getText8().length() > 255){
			FDCMsgBox.showInfo("��" + msTask.getID() + "�������������ַ����Ȳ��ܳ���255���ַ���");
			SysUtil.abort();
		}
		schTask.setName(msTask.getName());
		
		// ���üƻ���ʼʱ��
		schTask.setPlanStart(clearTime(ScheduleCalendarHelper
				.getClosestWorkDay(msTask.getStart(), calendarInfo)));

		// ���üƻ����ʱ��
		schTask.setPlanEnd(clearTime(ScheduleCalendarHelper.getClosestWorkDay(
				msTask.getFinish(), calendarInfo)));
		BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(schTask
				.getPlanStart(), schTask.getPlanEnd(), calendarInfo);
		if (effectTimes.intValue() == 0) {
			logger.error("error: duration=0;name=" + schTask.getName()
					+ ";startDate=" + schTask.getPlanStart() + ";endDate="
					+ schTask.getPlanEnd());
			schTask.setDay(FDCHelper.ONE);
		} else {
			schTask.setDay(effectTimes);
		}

		schTask.setLevel(msTask.getOutlineLevel().intValue());
		List childTasks = msTask.getChildTasks();
		if (childTasks == null || childTasks.size() < 1) {
			schTask.setIsLeaf(true);
		} else {
			schTask.setIsLeaf(false);
		}
		
		fillExtendProperties(msTask, schTask);
	}

	protected void fillExtendProperties(Task msTask, IRESchTask schTask) {
		String adminDept = msTask.getText3();
		if (adminDept != null) {
			List ids = (List) this.adminDept2MSTaskIds.get(adminDept);
			if (ids == null) {
				ids = new ArrayList();
				this.adminDept2MSTaskIds.put(adminDept, ids);
			}
			ids.add(msTask.getUniqueID());
		}
		String adminPerson = msTask.getText2();
		if (adminPerson != null) {
			List ids = (List) this.adminPerson2MSTaskIds.get(adminPerson);
			if (ids == null) {
				ids = new ArrayList();
				this.adminPerson2MSTaskIds.put(adminPerson, ids);
			}
			ids.add(msTask.getUniqueID());
		}
	}

	protected void fillIds(Task msTask, IRESchTask schTask) {
		String taskId = msTask.getText17();
		schTask.setId(BOSUuid.create(schTask.getBOSType()));
		if (!StringUtils.isEmpty(taskId)) {
			try {
				schTask.setId(BOSUuid.read(taskId));
			} catch (Exception e) {
				logger.error("Id is error. " + taskId + " is error[ " + schTask.getName() + " ]", e);
			}
		}
		String srcTaskId = msTask.getText16();
		if (!StringUtils.isEmpty(srcTaskId)) {
			try {
				BOSObjectType bosType = BOSUuid.getBOSObjectType(srcTaskId, true);
				if (schTask.getBOSType().equals(bosType)) {
					schTask.setSrcID(srcTaskId);
				}
			} catch (Exception e) {
				logger.error("SrcId is error. " + srcTaskId + " is error[ " + schTask.getName() + " ]", e);
			}
		}
		if (schTask.getSrcID() == null) {
			schTask.setSrcID(schTask.getId().toString());
		}
	}
	public List getTasks() {
		return this.schTasks;
	}
	private Date clearTime(Date date) {
		Calendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MILLISECOND, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		return gc.getTime();
	}
	private RESchTaskTypeEnum convertTaskType(String text) {
		List list = RESchTaskTypeEnum.getEnumList();
		for (int i = 0; i < list.size(); ++i) {
			RESchTaskTypeEnum e = (RESchTaskTypeEnum) list.get(i);
			if (e.getAlias().equalsIgnoreCase(text)) {
				return e;
			}
		}
		return RESchTaskTypeEnum.COMMONLY;
	}
	
	public static interface IRESchTaskCreator {
		public IRESchTask createSchTask();
	}
	
	public static interface IRESchTaskPredecessorCreator {
		public IRESchTaskPredecessor createSchTaskPredecessor();
	}
	
	public static interface IRESchCalendar {
		public ScheduleCalendarInfo getSchedule();
	}
}
