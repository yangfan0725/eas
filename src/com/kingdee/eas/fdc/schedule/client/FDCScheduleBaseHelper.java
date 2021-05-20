/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.ganttproject.task.dependency.TaskDependency;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeCollection;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		
 *		
 * @author		�ź���
 * @version		EAS7.0		
 * @createDate	2011-9-23 
 * @see						
 */

public class FDCScheduleBaseHelper {
	private static final Logger logger = CoreUIObject.getLogger(FDCScheduleBaseEditUI.class);
	private FDCScheduleInfo editData;

	private FDCScheduleBaseEditUI editUI;
	
	public FDCScheduleBaseHelper(FDCScheduleInfo editData, FDCScheduleBaseEditUI editUI) {
		this.editData = editData;
		this.editUI = editUI;
	}
	public FDCScheduleInfo getSaveSchedule() {
		logger.info("getSaveSchedule begin...");
		// ��¡�Ա�֤����ҵ��������Ӱ��
		FDCScheduleInfo info = (FDCScheduleInfo) editData.clone();
		/** ���뵱ǰ�Ĺ�����Ŀ **/
		info.setProject((CurProjectInfo) editUI.prmtCurproject.getValue());
		/** ���������ǰ������,��������������������Ҫ���� **/
		FDCScheduleTaskDependCollection prefixDepends = new FDCScheduleTaskDependCollection();
		/** ������е����� **/
		info.getTaskEntrys().clear();
		info.put("dependBaseKeyMap", null);// dependBaseKeyMap���ж�FDCScheduleTaskInfo������
		/** �Ӹ���ͼ�л��һ������������б� **/
		List tasks = editUI.getScheduleGanttProject().getSortKDTask();
		/** �������ø���ͼ�����б�Ľڵ���Ϣ **/
		setIsLeaf(tasks);
		/** ѭ������õĸ���ͼ�����б� **/
		copyTaskPerperties(info, prefixDepends, tasks);
		/*** �üƻ��Ŀ�ʼ����ʱ�� **/
		Date prjStartDate = info.getStartDate();
		Date prjEndDate = info.getEndDate();
		
		if (prefixDepends.size() > 0) {
			info.put("prefixDepends", prefixDepends);
		}
		if (prjStartDate != null && prjEndDate != null) {
			BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(info.getStartDate(), info.getEndDate(), this.editData.getCalendar());
			BigDecimal natureTimes = ScheduleCalendarHelper.getNatureTimes(info.getStartDate(), info.getEndDate());
			info.setEffectTimes(effectTimes);
//			info.setNatureTimes(natureTimes);
			info.setStartDate(info.getStartDate());
			info.setEndDate(info.getEndDate());
		}
		// TODO version
		if (info.getVersion() > 1f) {
			info.setVersion(editData.getVersion());
		} else {
			info.setVersion(1.0f);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			if (editData.getProject() != null
					&& editData.getProjectSpecial() != null) {
				info.setVersionName(info.getProject().getName()
						+ editData.getProjectSpecial()+ "��" + new Float(info.getVersion()) + "_"  + sdf.format(new Date()) );
			}
			if (editData.getProject() != null
					&& editData.getProjectSpecial() == null) {
				String version = "ִ�а�";
				String stage = info.getScheduleStage()!= null?info.getScheduleStage().getName():"";
				info.setVersionName(info.getProject().getName()+stage
						+version+ new Float(info.getVersion())
						+"_"+sdf.format(new Date()));
			}
		}
		// �ٴο�¡���������ھ������ݶ�����ʾ���ݲ���Ӱ��
		FDCScheduleInfo ret = (FDCScheduleInfo) info.clone();
		ret.reduceData4Transfer2Remote();
		logger.info("getSaveSchedule end... ");
		try {
			ret.put("wbsTree", editUI.getScheduleGanttProject().getWbsTree(false));
		} catch (Exception e) {
			 logger.warn(e);
		}
		// ͬ����editData��
		editData.setVersionName(ret.getVersionName());
		return ret;
	}

	/**
	 * ������������ļ��Ρ��Ƿ���ϸ emanon
	 * <p>
	 * һ�����ڱ���ǰ����
	 * 
	 * @param tasks
	 */
	// TODO FIXME ���ü���ʱ�ᱨ�ж�
	private void setIsLeaf(List tasks) {
		if (tasks != null) {
			Map key = new HashMap();
			for (int i = 0; i < tasks.size(); i++) {
				KDTask task = (KDTask) tasks.get(i);
				// if (!task.isScheduleTask()) {
				// continue;
				// }
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) task
						.getScheduleTaskInfo();
				key.put(taskInfo.getSrcID(), new Integer(i));
				taskInfo.setIsLeaf(true);
				if (taskInfo.getParent() == null) {
					taskInfo.setLevel(0);
				} else {
					Integer index = (Integer) key.get(taskInfo.getParent()
							.getSrcID());
					 if (index != null) {
						KDTask parentTask = (KDTask) tasks.get(index.intValue());
						FDCScheduleTaskInfo parentInfo = (FDCScheduleTaskInfo) parentTask.getScheduleTaskInfo();
						parentInfo.setIsLeaf(false);
						taskInfo.setLevel(parentInfo.getLevel() + 1);
					 }
				}
			}
		}
	}

	private void copyTaskPerperties(FDCScheduleInfo info, FDCScheduleTaskDependCollection prefixDepends, List tasks) {
		for (int i = 0; i < tasks.size(); i++) {
			KDTask task = (KDTask) tasks.get(i);
			/** �Ӹ���ͼ�����л�ý�������ǿתΪ���ز��������� **/
			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) task.getScheduleTaskInfo();
			/** �ӵ�ǰ���ȼƻ��л�ð汾��Ϣ,�������Ϊ1˵��Ϊ�����Ľ�������,��ô�ý������񱣴浱ǰԴID **/
			if (editData.getVersion() == 1f) {
				taskInfo.setSrcID(taskInfo.getId().toString());
			}
			/** �ڽ��������з����������� **/
			taskInfo.put("name_l2", taskInfo.getName());
			/** ����������ΪNULL�Ĵ��� **/
			if (FDCHelper.isEmpty(taskInfo.getCalendar())) {
				taskInfo.setCalendar(info.getCalendar());
			}
			/** ������õ������б��������� **/
			info.getTaskEntrys().add(taskInfo);

			/**
			 * ������ȼƻ��Ŀ�ʼʱ��ΪNULL,�������Ŀ�ʼ������ȼƻ���ʼʱ��,�����Ϊnull�Ҵ�������ʼ����ʱ��,
			 * ��ô�������ʱ�丳��ƻ���ʼʱ��
			 **/
			info.setStartDate(getScheduleStartDate(info.getStartDate(), taskInfo));
			info.setEndDate(getScheduleEndDate(info.getEndDate(), taskInfo));
			// ����ǰ��������Ϣ
			/** ��������ǰ�������б� **/
			// taskInfo.getDependEntrys().clear();
			/** �Ӹ�������ID�Ӹ���ͼ��ȡ����ͼ���� **/
			KDTask myTask = editUI.getScheduleGanttProject().getKDTaskById(taskInfo.getId().toString());
			/*** �Ӹ���ͼ�����л�ȡǰ�������б� **/
			if(myTask == null)
				continue;
			 TaskDependency[] depend = myTask.getDependencies().toArray();
			// // TaskDependency[] depend =
			// // myTask.getDependenciesAsDependee().toArray(); // TODO
			// // System.out.println(depend.length);
			// // H
			// // ȡ���ýڵ�
			// // ������ܻ��в���
			// /*** ѭ������ͼ�����ǰ�������б� **/
			setDepndTasks(taskInfo, depend, myTask);
			// // depend = myTask.getDependenciesAsDependant().toArray();
			setDependTasks(prefixDepends, taskInfo, depend, myTask);
			
			for(int j=0;j<taskInfo.getHelpPersonEntry().size();j++){
				taskInfo.getHelpPersonEntry().get(j).setId(BOSUuid.create(taskInfo.getHelpPersonEntry().get(j).getBOSType()));
			}
			for(int j=0;j<taskInfo.getHelpDeptEntry().size();j++){
				taskInfo.getHelpDeptEntry().get(j).setId(BOSUuid.create(taskInfo.getHelpDeptEntry().get(j).getBOSType()));
			}
		}
	}

	private Date getScheduleEndDate(Date prjEndDate, FDCScheduleTaskInfo taskInfo) {
		if (prjEndDate == null) {
			prjEndDate = taskInfo.getEnd();
		} else {
			if (null != taskInfo.getEnd() && prjEndDate.compareTo(taskInfo.getEnd()) < 0) {
				prjEndDate = taskInfo.getEnd();
			}
		}
		return prjEndDate;
	}

	private Date getScheduleStartDate(Date prjStartDate, FDCScheduleTaskInfo taskInfo) {
		if (prjStartDate == null) {
			prjStartDate = taskInfo.getStart();
		} else {
			if (null != taskInfo.getStart() && prjStartDate.compareTo(taskInfo.getStart()) > 0) {
				prjStartDate = taskInfo.getStart();
			}
		}
		return prjStartDate;
	}

	private void setDepndTasks(FDCScheduleTaskInfo taskInfo, TaskDependency[] depend, KDTask task) {
		taskInfo.getDependEntrys().clear();
		for (int j = 0; j < depend.length; j++) {
			/** ��������ǰ�������������ڸ���ͼǰ������ **/
			FDCScheduleTaskDependInfo item = new FDCScheduleTaskDependInfo();
			TaskDependency taskDependency = depend[j];
			if (taskDependency.getDependee().getTaskID() != task.getTaskID()) {
				continue;
			}
			/** ����ǰ������������Ϣ **/
			item.setId(BOSUuid.create(item.getBOSType()));
			item.setTask(taskInfo);
			item.setDependTask((FDCScheduleTaskInfo) ((KDTask) taskDependency.getDependant()).getScheduleTaskInfo());
			item.setType(ScheduleParserHelper.getTaskLinkTypeByConstraint(taskDependency.getConstraint()));
			int difference = taskDependency.getDifference();
			// ǰ��һ���������,������ʼ,ʱ�������Ǵʱ��
			// if (taskDependency.getConstraint() instanceof
			// FinishStartConstraintImpl) {
			// // ǰ��ʱ��Ľ���
			// Date preEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(taskDependency
			// .getDependee().getEnd());
			// // ����ʱ��Ŀ�ʼ
			// Date nextStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate
			// (taskDependency.getDependant().getStart());
			// // ǰ��ʱ��Ľ���-��������Ŀ�ʼ
			// // ��1-1 ��1-2����Ч������2�죬���Ǵ��ʱ����ʵ��0�����������-2
			// difference =
			// Integer.parseInt(ScheduleCalendarHelper.getEffectTimes
			// (preEndDate, nextStartDate, taskInfo.getSchedule().getCalendar())
			// .toString()) - 2;
			// }
			// // ǰ���������������ͬʱ��ʼ
			// else if (taskDependency.getConstraint() instanceof
			// StartStartConstraintImpl) {
			// Date preStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(
			// taskDependency.getDependee().getStart());
			// Date nextStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate
			// (taskDependency.getDependant().getStart());
			// difference = Integer.parseInt(ScheduleCalendarHelper
			// .getEffectTimes(preStartDate, nextStartDate,
			// taskInfo.getSchedule().getCalendar()).toString()) - 1;
			// }
			// // ǰ���������������ͬʱ���
			// else if (taskDependency.getConstraint() instanceof
			// FinishFinishConstraintImpl) {
			// Date preEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(taskDependency
			// .getDependee().getEnd());
			// Date nextEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(taskDependency
			// .getDependant().getEnd());
			// difference = Integer.parseInt(ScheduleCalendarHelper
			// .getEffectTimes(preEndDate, nextEndDate,
			// taskInfo.getSchedule().getCalendar())
			// .toString());
			// if (difference < 0) {
			// difference += 1;
			// } else {
			// difference -= 1;
			// }
			// }
			item.setDifference(difference);
			item.setHardness(null);
			taskInfo.getDependEntrys().add(item);
		}
	}

	private void setDependTasks(FDCScheduleTaskDependCollection prefixDepends, FDCScheduleTaskInfo taskInfo, TaskDependency[] depend,
			KDTask task) {
		for (int j = 0; j < depend.length; j++) {
			FDCScheduleTaskDependInfo item = new FDCScheduleTaskDependInfo();
			TaskDependency taskDependency = depend[j];
			if (taskDependency.getDependant().getTaskID() != task.getTaskID()) {
				continue;
			}
			item.setId(BOSUuid.create(item.getBOSType()));
			item.setTask((FDCScheduleTaskInfo) ((KDTask) taskDependency.getDependee()).getScheduleTaskInfo());
			item.setDependTask(taskInfo);
			item.setType(ScheduleParserHelper.getTaskLinkTypeByConstraint(taskDependency.getConstraint()));
			int difference = taskDependency.getDifference();
			// if (taskDependency.getConstraint() instanceof
			// FinishStartConstraintImpl) {
			// Date preEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(taskDependency
			// .getDependee().getEnd());
			// Date nextStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate
			// (taskDependency.getDependant().getStart());
			// difference =
			// Integer.parseInt(ScheduleCalendarHelper.getNatureTimes
			// (preEndDate, nextStartDate).toString()) - 1;
			// } else if (taskDependency.getConstraint() instanceof
			// StartStartConstraintImpl) {
			// Date preStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(
			// taskDependency.getDependee().getStart());
			// Date nextStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate
			// (taskDependency.getDependant().getStart());
			// difference =
			// Integer.parseInt(ScheduleCalendarHelper.getNatureTimes
			// (preStartDate, nextStartDate).toString()) - 1;
			// } else if (taskDependency.getConstraint() instanceof
			// FinishFinishConstraintImpl) {
			// Date preEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(taskDependency
			// .getDependee().getEnd());
			// Date nextEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(taskDependency
			// .getDependant().getEnd());
			// difference =
			// Integer.parseInt(ScheduleCalendarHelper.getNatureTimes
			// (preEndDate, nextEndDate).toString()) - 1;
			// }
			item.setDifference(difference);
			// item.setDifference(taskDependency.getDifference());
			// item.setHardness(item);
			prefixDepends.add(item);
		}
	}

	/**
	 * ���뼯�Źܿؽڵ�
	 * 
	 * @return
	 */
	public static GlobalTaskNodeCollection getGlobalTaskNode() {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("isBuidingPlan"));
		sic.add(new SelectorItemInfo("isOperationPlan"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("taskType"));
		sic.add(new SelectorItemInfo("achType.id"));
		sic.add(new SelectorItemInfo("achType.name"));
		sic.add(new SelectorItemInfo("achType.number"));
		sic.add(new SelectorItemInfo("bizType.bizType.id"));
		sic.add(new SelectorItemInfo("bizType.bizType.name"));
		sic.add(new SelectorItemInfo("bizType.bizType.number"));

		view.setSelector(sic);

		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("number");
		si.setSortType(SortType.ASCEND);
		sorter.add(si);

		view.setSorter(sorter);    

		GlobalTaskNodeCollection cols = null;
		try {
			cols = GlobalTaskNodeFactory.getRemoteInstance().getGlobalTaskNodeCollection(view);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cols;
	}
	/**
	 * ���뼯�Źܿؽڵ�
	 * 
	 * @return
	 */
	public static RESchTemplateTaskCollection getRESchTemplateTask() {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("longNumber");
		sic.add("orgUnit.id");
		sic.add("orgUnit.name");
		sic.add("orgUnit.number");
		sic.add("state");
		sic.add("templateType");
		sic.add("template.id");
		sic.add("template.name");
		sic.add("template.number");
		sic.add("entry.id");
		sic.add("entry.name");
		sic.add("entry.tasktype");
		sic.add("entry.number");
		sic.add("entry.longNumber");
		sic.add("entry.referenceday");
		sic.add("entry.isLeaf");
		sic.add("entry.level");
		sic.add("entry.srcGroupNode");
		sic.add("entry.parent.id");
		sic.add("entry.parent.name");
		sic.add("entry.parent.number");
		sic.add("entry.parent.level");
		sic.add("entry.parent.isLeaf");
		sic.add("entry.standardTask.id");
		sic.add("entry.standardTask.name");
		sic.add("entry.standardTask.number");

		sic.add("entry.achievementType.id");
		sic.add("entry.achievementType.name");
		sic.add("entry.achievementType.number");

		sic.add("entry.businessType.id");
		sic.add("entry.businessType.bizType.id");
		sic.add("entry.businessType.bizType.number");
		sic.add("entry.businessType.bizType.name");

		sic.add("entry.predecessors.id");
		sic.add("entry.predecessors.predecessorTask.id");
		sic.add("entry.predecessors.predecessorTask.name");
		sic.add("entry.predecessors.predecessorTask.number");
		sic.add("entry.predecessors.differenceDay");
		sic.add("entry.predecessors.predecessorType");
		view.setSelector(sic);

		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("number");
		si.setSortType(SortType.ASCEND);
		sorter.add(si);

		view.setSorter(sorter);    

		RESchTemplateTaskCollection cols = null;
		try {
			cols = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cols;
	}
	
	/**
	 * ���뿼�˽ڵ�
	 * 
	 * @return
	 */
	public static FDCScheduleTaskCollection getFDCSchTask() {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("isCheckNode"); //�Ƿ񿼺˽ڵ�, modified by zhaoqin on 2014/06/20
		sic.add("schedule.id");	
		sic.add("schedule.versionName");	
		sic.add("schedule.isCheckVersion");
		sic.add("schedule.isLatestVer");
		sic.add("schedule.projectSpecial");
		sic.add("schedule.srcID");
		view.setSelector(sic);

		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("number");
		si.setSortType(SortType.ASCEND);
		sorter.add(si);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule.isCheckVersion",1));
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",1));
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer",1));
		filter.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial", null));
		filter.getFilterItems().add(new FilterItemInfo("schedule.srcID", null, CompareType.NOTEQUALS));
		
		/* modified by zhaoqin on 2014/06/20 */
		filter.getFilterItems().add(new FilterItemInfo("isCheckNode", 1));
		//filter.setMaskString("(#0 and #1) or #2 and #3 and #4");
		filter.setMaskString("(#0 and #1) or #2 and #3 and #4 and #5");
		
		view.setFilter(filter);	
		view.setSorter(sorter);    

		FDCScheduleTaskCollection cols = null;
		try {
			cols = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cols;
	}
}
