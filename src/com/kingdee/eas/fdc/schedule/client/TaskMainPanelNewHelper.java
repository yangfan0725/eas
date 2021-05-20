/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.ganttproject.cache.ActivityCache;
import net.sourceforge.ganttproject.calendar.ScheduleCalendarImpl;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.shape.PaintCellRenderer;
import net.sourceforge.ganttproject.shape.ShapeConstants;
import net.sourceforge.ganttproject.shape.ShapePaint;
import net.sourceforge.ganttproject.task.CustomColumnsException;
import net.sourceforge.ganttproject.task.TaskMutator;
import net.sourceforge.ganttproject.task.dependency.TaskDependency;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.ext.IFilterInfoProducer;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractWFEntryInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractWFTypeInfo;
import com.kingdee.eas.fdc.schedule.AchievementTypeInfo;
import com.kingdee.eas.fdc.schedule.CheckNodeCollection;
import com.kingdee.eas.fdc.schedule.CheckNodeFactory;
import com.kingdee.eas.fdc.schedule.CheckNodeInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.HelpDeptEntryCollection;
import com.kingdee.eas.fdc.schedule.HelpDeptEntryInfo;
import com.kingdee.eas.fdc.schedule.HelpPersonEntryCollection;
import com.kingdee.eas.fdc.schedule.HelpPersonEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.enums.EnumUtils;

/**
 * ��Ȩ�� �����������������޹�˾��Ȩ����
 * <p>
 * ������Ҫ���ڴ����������Ի�����Ϣ����
 * <p>
 * �ص��ע����ʼ���������ڡ����ڡ��������������һ���ı䣬�����ֶ���������<br>
 * add by emanon<br>
 * 1�����ڸı�<br>
 * ��ʼ���䣬������������<br>
 * 2����ʼ���ڸı䣺<br>
 * ���ڲ��䣬������������<br>
 * 3���������ڸı䣺<br>
 * ��ʼ���䣬���㹤�ڡ����<br>
 * 4����������ı�<br>
 * ���ڲ��䣬���㿪ʼ������<br>
 * ��Ӹı����д��PredecessorsPanelNewHelper.table_editStopped()��<br>
 * 5�������ı�<br>
 * �Ҳ���ֻ�Ĺ���(��Ӧ��΢����ʼ���������ڵ���������գ�<br>
 * 
 * @author emanon
 * @version EAS7.0
 * @createDate 2011-8-20
 * @see
 */
public class TaskMainPanelNewHelper implements ITaskPanelHelper {

	private FDCScheduleTaskPropertiesNewUI taskProperties;
	private GanttLanguage language = GanttLanguage.getInstance();
	private KDTask selectTask;
	private boolean isStageAchievement = false;
	private ScheduleCalendarInfo schCalendar = null;
	private CurProjectInfo project = null;
	private String passMustEvaluation = "0";
	
	private final static int DURATION = 0;
	private final static int START = 1;
	private final static int END = 2;
	

	public TaskMainPanelNewHelper(
			FDCScheduleTaskPropertiesNewUI taskProperties,
			CurProjectInfo project) {
		this.taskProperties = taskProperties;
		selectTask = taskProperties.getSelectedTask();
		schCalendar = (ScheduleCalendarInfo) taskProperties.prmtTaskCalendar
				.getValue();
		this.project = project;
		if (schCalendar == null) {
			try {
				String prjID = project == null ? null : project.getId()
						.toString();
				schCalendar = ScheduleCalendarFactory.getRemoteInstance()
						.getDefaultCal(prjID);
			} catch (BOSException e) {
				taskProperties.handUIException(e);
			} catch (EASBizException e) {
				taskProperties.handUIException(e);
			}
		}
		taskProperties.txtTaskName.setRequired(true);
		initCtrl();
	}

	/**
	 * @description ��ʼ��ʱ�޸Ŀؼ�״̬
	 * @author �ź���
	 * @createDate 2011-8-29 void
	 * @version EAS7.0
	 * @see
	 */
	private void initCtrl() {
		taskProperties.prmtAchievementType.setEditable(false);
		taskProperties.prmtBizType.setEditable(false);
		taskProperties.prmtBizType.setEnabledMultiSelection(true);
		taskProperties.prmtDutyDep.setEditable(false);
		taskProperties.prmtDutyPerson.setEditable(false);
		taskProperties.prmtHelpDep.setEditable(false);
		taskProperties.prmtHelpDep.setEnabledMultiSelection(true);
		taskProperties.prmtQualityAppraisePerson.setEditable(false);
		taskProperties.prmtHelpPerson.setEditable(false);
		taskProperties.prmtHelpPerson.setEnabledMultiSelection(true);
		taskProperties.prmtRiskChargePerson.setEditable(false);
		taskProperties.prmtTaskCalendar.setEditable(false);
		taskProperties.prmtTaskGuide.setEditable(false);		
		taskProperties.cbTaskType.setEditable(false);
		// taskProperties.txtWorkDay.setEditable(true);
		taskProperties.txtNumber.setEnabled(false);
		String status = taskProperties.getOwnerStatus("VIEW");
		if ("VIEW".equals(status)) {
			// taskProperties.actionSave.setVisible(false);
			taskProperties.prmtAchievementType.setEnabled(false);
			taskProperties.kDTablePredecessor.setEnabled(false);
			taskProperties.prmtScheduleAppraisePerson.setEnabled(false);
			taskProperties.cbShape.setEnabled(false);
			taskProperties.prmtAchievementType.setEnabled(false);

			if (taskProperties instanceof FDCSpecialScheduleTaskPropertiesUI) {
				((FDCSpecialScheduleTaskPropertiesUI) taskProperties).prmtRelationMainTask
						.setEnabled(false);
			}
		} else {
			// taskProperties.actionSave.setVisible(true);

			// taskProperties.txtNumber.setEditable(true);
			taskProperties.prmtAchievementType.setEnabled(true);
			taskProperties.kDTablePredecessor.setEnabled(true);
			taskProperties.cbShape.setEnabled(true);

			if (taskProperties instanceof FDCSpecialScheduleTaskPropertiesUI) {
				((FDCSpecialScheduleTaskPropertiesUI) taskProperties).prmtRelationMainTask
						.setEnabled(true);
			}
		}
	
		taskProperties.txtAppendEndQuantity.setRemoveingZeroInDispaly(false);
		
		//��׼����ÿָ������
		EntityViewInfo view = taskProperties.prmtTaskGuide.getEntityViewInfo();
		if(view == null){
			taskProperties.prmtTaskGuide.getQueryAgent().resetRuntimeEntityView();
			view = new EntityViewInfo();
			SorterItemCollection sorter = new SorterItemCollection();
		    SorterItemInfo sort = new SorterItemInfo("longnumber");
		    sorter.add(sort);
			view.setSorter(sorter);
		}
		taskProperties.prmtTaskCalendar.getQueryAgent().getQueryInfo().setAlias("������ѯ");	
		taskProperties.prmtTaskCalendar.setFilterInfoProducer(new IFilterInfoProducer(){

			public FilterInfo getFilterInfo() {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("objectId",project.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("objectId",null,CompareType.EQUALS));
				filter.setMaskString("#0 or #1");
				return filter;
			}

			public void setCurrentCtrlUnit(CtrlUnitInfo cui) {
				
			}

			public void setCurrentMainBizOrgUnit(OrgUnitInfo oui, OrgType ot) {
				
			}});
		
		
		
		// �������ã�����������˱�¼
		try {
			// ������#������������#����ר������#���������ר������
			passMustEvaluation = ParamManager.getParamValue(null,
					new ObjectUuidPK(project.getCostCenter().getId()),
					"FDCSH011");
			if ("0".equals(passMustEvaluation)) {
				taskProperties.prmtScheduleAppraisePerson.setRequired(false);
			} else if ("1".equals(passMustEvaluation)) {
				if (getOwner() instanceof MainScheduleEditUI) {
					taskProperties.prmtScheduleAppraisePerson.setRequired(true);
				} else {
					taskProperties.prmtScheduleAppraisePerson
							.setRequired(false);
				}
			} else if ("2".equals(passMustEvaluation)) {
				if (getOwner() instanceof SpecialScheduleEditUI) {
					taskProperties.prmtScheduleAppraisePerson.setRequired(true);
				} else {
					taskProperties.prmtScheduleAppraisePerson
							.setRequired(false);
				}
			} else if ("3".equals(passMustEvaluation)) {
				taskProperties.prmtScheduleAppraisePerson.setRequired(true);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		taskProperties.prmtTaskCalendar.setEnabled(false);
	}
	
	private void verifyAdjust(int adjustType, DataChangeEvent e) {
		if (!canAdjuest(adjustType)) {
			FDCMsgBox.showInfo(taskProperties, "��ǰ����������������ڴ�ӹ�ϵ���������޸ĵ�ǰ���ԣ�");
			KDDatePicker dt = (KDDatePicker) e.getSource();
			dt.setValue(e.getOldValue(), false);
			SysUtil.abort();
		}
	}

	protected void initListener() {
		DataChangeListener workDayListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				txtWorkDayChanged_propertyChange(e);
			}
		};

		DataChangeListener planStartListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				verifyAdjust(START, e);
				pkPlanStart_DataChanged(e);
			    
			}
		};
		DataChangeListener planEndListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				verifyAdjust(END, e);
				pkPlanEnd_DataChanged(e);
				
			}
		};

		DataChangeListener calendarListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				cbSchCalendar_propertyChange(e);
			}
		};

		DataChangeListener prmtBizTypeDateListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				prmtBizTypeDateListener(e);
			}
		};
		DataChangeListener prmtCheckNodeDateListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				prmtCheckNodeDateListener(e);
			}
		};

		ItemListener yesListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				txtYes_ItemChanged();
			}
		};

		taskProperties.txtWorkDay.addDataChangeListener(workDayListener);
		taskProperties.pkPlanStart.addDataChangeListener(planStartListener);
		taskProperties.pkPlanEnd.addDataChangeListener(planEndListener);
		taskProperties.prmtTaskCalendar.addDataChangeListener(calendarListener);
		taskProperties.prmtCheckNode.addDataChangeListener(prmtCheckNodeDateListener);
		taskProperties.prmtBizType.addDataChangeListener(prmtBizTypeDateListener);
		taskProperties.txtYes.addItemListener(yesListener);
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
	 * ���ڸı����
	 * <p>
	 * ��ʼ���䣬������������<br>
	 * 
	 * @param e
	 */
	protected void txtWorkDayChanged_propertyChange(DataChangeEvent e) {
		Date beginDate = getPlanStartDate();
		BigDecimal duration = taskProperties.txtWorkDay.getBigDecimalValue();
		
		// �����FS������SS���������޸Ľ������ڣ������FF�Ļ��������ʼ����
		TaskLinkTypeEnum linkType = getCurrentTaskLinkType();
		BigDecimal newDuration = duration.subtract(FDCHelper.ONE);
		if (linkType == null) {
			Date endDate = ScheduleCalendarHelper.getEndDate(beginDate, newDuration, schCalendar);
			taskProperties.pkPlanEnd.setValue(endDate);
		} else {
			if (linkType.equals(TaskLinkTypeEnum.FINISH_FINISH)) {
				// TODO �Ŀ�ʼ���ڵ���
				Date endDate = ScheduleCalendarHelper.getEndDate(getPlanEndDate(),
						FDCHelper.multiply(newDuration, FDCHelper
						.toBigDecimal(-1)), schCalendar);
				taskProperties.pkPlanStart.setValue(endDate);
			} else if (linkType.equals(TaskLinkTypeEnum.FINISH_START) || linkType.equals(TaskLinkTypeEnum.START_START)) {
				// �������
				Date endDate = ScheduleCalendarHelper.getEndDate(beginDate, newDuration, schCalendar);
				taskProperties.pkPlanEnd.setValue(endDate);
			}
		}
		
		
		
		
		

		
		// ������
		// computePreposing();
	}
	
	
	public boolean canModify(int changeSource) {
		FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
		FDCScheduleTaskDependCollection cols = taskInfo.getDependEntrys();
		// if (cols.size() == 1) {
		// FDCScheduleTaskDependInfo dependency = cols.get(0);
		// return isCanModifyEffectDependant(changeSource, dependency);
		//
		// } else {
			// ֻҪ��һ�����ʺϵ����������ж����ܵ�����
            boolean isCanOperator = true;
			for (int i = 0; i < cols.size(); i++) {
				if (!isCanOperator) {
					isCanOperator = false;
				}
			}
			return isCanOperator;
		// }
		//
		// return true;
	}

	private boolean isCanModifyEffectDependant(int changeSource, FDCScheduleTaskDependInfo dependency) {
		if (dependency.getType().equals(TaskLinkTypeEnum.FINISH_START) || dependency.getType().equals(TaskLinkTypeEnum.FINISH_START)) {
			if (changeSource == START) {
				return false;
			} else {
				return true;
			}
		} else if (dependency.getType().equals(TaskLinkTypeEnum.FINISH_FINISH)) {
			if (changeSource == END) {
				return false;
			} else {
				return true;
			}
		}
		return true;
	}

	/**
	 * 
	 * ���������û������Խ�����е���ʱ������У�����Ƿ��д�ӹ�ϵ������д�ӹ�ϵ����������¹�����е� FS:
	 * �޸Ĺ��ڣ��������ڸı䣬�������䣩���޸Ľ������ڣ����ڸı䣬�������䣩����ʼ���ڲ������޸�
	 * FF���޸Ĺ��ڣ���ʼ���ڸı䣬�������䣩���޸Ŀ�ʼ���ڣ����ڸı䣬�������䣩���������ڲ������޸�
	 * SS���޸Ĺ��ڣ��������ڸı䣬�������䣩���޸Ľ������ڣ����ڸı䣬�������䣩����ʼ���ڲ������޸�
	 * �����ڶ����ӹ�ϵʱ���Լ����������Ĵ�ӹ�ϵΪ׼����������Ӧ�Ĵ�ӹ�ϵԼ���� ��һ������t1 ����t2��FS+1�Ĵ�ӹ�ϵ
	 * ����t3��FF+2�Ĵ�ӹ�ϵ����Ƚ�t1Fs+1��t2FF+2�Ǹ�ʱ�������ȡ��Ӧ�Ĵ�ӹ�ϵ����Լ���� ��һ������t1
	 * ����t2��FF+1�Ĵ�ӹ�ϵ ����t3��SS+2�Ĵ�ӹ�ϵ����Ƚ�t1 FF+1��t2 SS+2�Ǹ�ʱ�������ȡ��Ӧ�Ĵ�ӹ�ϵ����Լ����
	 * ��һ������t1 ����t2��FS+1�Ĵ�ӹ�ϵ ����t3��SS+2�Ĵ�ӹ�ϵ����Ƚ�t1 FF+1��t2
	 * SS+2�Ǹ�ʱ�������ȡ��Ӧ�Ĵ�ӹ�ϵ����Լ����
	 * 
	 * @return �ܲ��Ե�������е���
	 * preTaskList���ݽṹ��TaskDependency,FDCScheduleTask,TaskLinkTypeEnum,Diff��
	 * @param int adjustType �����ˣ�yuanjun_lan ����ʱ�䣺2012-3-3
	 */
	protected boolean canAdjuest(int adjustType) {
		List preTaskList = getTaskDependencyTask();
		boolean isCanModify = false;
		int size = preTaskList.size();
		TaskDependency dependency = null;
		FDCScheduleTaskInfo pretask = null;
		TaskLinkTypeEnum linkType = null;
		BigDecimal diff = FDCHelper.ZERO;
		Object[] tasksInfo = null;
		/* �����ǰ����ֻ��һ��ǰ������ʱֱ�Ӹ��ݹ������󷵻� */
		if (size == 1) {
			tasksInfo = (Object[]) preTaskList.get(0);
			return isCanModify(adjustType, tasksInfo); 
		}
		/**
		 * ���µĴ�������Ե�ǰ�����ж����ӹ�ϵʱ�����Ĵ��� ���������Ӧ�Ĵ�ӹ�ϵ�����Ӧ��ʱ�䣬 FF�����ǰ����Ľ���ʱ��
		 * FS�����ǰ����Ŀ�ʼʱ�� SS�����ǰ����Ŀ�ʼʱ�� Ȼ��Ƚ������ֵ,ȡ��ʱ������ֵ��Ȼ���������ǰ������Ĵ�ӹ�ϵ��������
		 */
		Date date = null;
		FDCScheduleTaskInfo baseTask = null;
		Date nextDate = null;
		int baseIndex = -1;
		for (int i = 0; i < size; i++) {
             tasksInfo = (Object[]) preTaskList.get(i);
			pretask = (FDCScheduleTaskInfo) tasksInfo[1];
			linkType = (TaskLinkTypeEnum) tasksInfo[2];
			if (tasksInfo[3] != null) {
				diff = FDCHelper.toBigDecimal(tasksInfo[3]);
			}
			
			if(linkType.equals(TaskLinkTypeEnum.FINISH_FINISH) || linkType.equals(TaskLinkTypeEnum.FINISH_START)){
				Date preTaskEndDate = pretask.getEnd();
				nextDate = ScheduleCalendarHelper.getEndDate(preTaskEndDate, diff, pretask.getCalendar());
			} else if (linkType.equals(TaskLinkTypeEnum.START_START)) {
				Date preTaskStartDate = pretask.getStart();
				nextDate = ScheduleCalendarHelper.getEndDate(preTaskStartDate, diff, pretask.getCalendar());
			}
			if (date == null) {
				date = nextDate;
				baseIndex = i;
			}
            if (date.compareTo(nextDate) < 0) {
				date = nextDate;
				baseIndex = i;
			} 
		}
	    if (baseIndex > -1) {
			Object[] resultTask = (Object[]) preTaskList.get(baseIndex);
			return isCanModify(adjustType, resultTask);
		}
		
		return true;
	}

	private boolean isCanModify(int adjustType, Object[] tasksInfo) {
		TaskDependency dependency;
		FDCScheduleTaskInfo pretask;
		TaskLinkTypeEnum linkType;
		dependency = (TaskDependency) tasksInfo[0];
		pretask = (FDCScheduleTaskInfo) tasksInfo[1];
		linkType = (TaskLinkTypeEnum) tasksInfo[2];
		if (linkType.equals(TaskLinkTypeEnum.FINISH_START) || linkType.equals(TaskLinkTypeEnum.START_START)) {
			if (adjustType == START) {
				return false;
			} else {
				return true;
			}
		} else if (linkType.equals(TaskLinkTypeEnum.FINISH_FINISH)) {
			if (adjustType == END) {
				return false;
			} else {
				return true;
			}

		}
		return true;
	}

	/**
	 * ��ʼ���ڸı�����¼�
	 * <p>
	 * ���ڲ��䣬������������
	 * 
	 * @author emanon
	 * @createDate 2011-8-29
	 * @param e
	 * @version EAS7.0
	 * @see
	 */
	protected void pkPlanStart_DataChanged(DataChangeEvent e) {
		
		
		Date beginDate = getPlanStartDate();
		Date newDate = ScheduleCalendarHelper.getClosestWorkDay(beginDate, schCalendar);
		if (beginDate.compareTo(newDate) != 0) {
			beginDate = newDate;
			taskProperties.pkPlanStart.setValue(beginDate);
		}
		
		BigDecimal duration = taskProperties.txtWorkDay.getBigDecimalValue();
		TaskLinkTypeEnum linkType = getCurrentTaskLinkType();
		if (linkType == null) {
			Date endDate = ScheduleCalendarHelper.getEndDate(beginDate, duration.subtract(FDCHelper.ONE), schCalendar);
			taskProperties.pkPlanEnd.setValue(endDate);
		} else {
			if (linkType.equals(TaskLinkTypeEnum.FINISH_FINISH)) {
                 BigDecimal myDuration = ScheduleCalendarHelper.getEffectTimes(beginDate, getPlanEndDate(), schCalendar);
				taskProperties.txtWorkDay.setValue(myDuration);
			}
		}
		
		
		
		// ������������
		

		// computePreposing();
	}

	/**
	 * �������ڸı�����¼�
	 * <p>
	 * ��ʼ���ڲ��䣬���㹤�ڡ����
	 * 
	 * @author emanon
	 * @param e
	 */
	protected void pkPlanEnd_DataChanged(DataChangeEvent e) {
		Date beginDate = getPlanStartDate();
		Date endDate = getPlanEndDate();
		Date newDate = ScheduleCalendarHelper.getClosestWorkDay(endDate, schCalendar);
		if (endDate.compareTo(newDate) != 0) {
			endDate = newDate;
			taskProperties.pkPlanEnd.setValue(endDate, false);
		}
		TaskLinkTypeEnum linkType = getCurrentTaskLinkType();
		BigDecimal duration = ScheduleCalendarHelper.getEffectTimes(beginDate,
				endDate, schCalendar);
		taskProperties.txtWorkDay.setValue(duration, false);
		if (taskProperties.txtYes.isSelected()) {
			Date checkDate = (Date) taskProperties.pkAccessDate.getValue();
			if (checkDate == null || checkDate.compareTo((Date) e.getOldValue()) == 0) {
				taskProperties.pkAccessDate.setValue(e.getNewValue());
			}
		}else{
			taskProperties.pkAccessDate.setValue(null);
			taskProperties.prmtCheckNode.setValue(null);
		}
		// computePreposing();
	}

	/**
	 * ҵ�����͸ı�
	 * <p>
	 * �����ѡ��׶��Գɹ�����ɹ����F7��ѡ�����򲻿�ѡ
	 * 
	 * @param e
	 */
	protected void prmtBizTypeDateListener(DataChangeEvent e) {
		taskProperties.controlState();
		Object[] taskBizTypes = (Object[]) taskProperties.prmtBizType
				.getValue();
		if (taskBizTypes == null) {
			isEnableArchType(false);
			return;
		}
		for (int i = 0; i < taskBizTypes.length; i++) {
			ScheduleBizTypeInfo item = (ScheduleBizTypeInfo) taskBizTypes[i];
			if (null != item && "�׶��Գɹ�".equals(item.getName())) {
				// isStageAchievement = true;
				isEnableArchType(true);
				return;
			}
			
		}
		// isStageAchievement = false;
		isEnableArchType(false);
	}
	
	
	/**
	 * ���˽ڵ�
	 * <p>
	 * ���˽ڵ�仯����taskname��Ϊ���˽ڵ�����
	 * 
	 * @param e
	 */
	protected void prmtCheckNodeDateListener(DataChangeEvent e) {
		if(taskProperties.txtYes.isSelected()){			
			if(e.getNewValue()!=null && e.getNewValue() instanceof CheckNodeInfo){			
				taskProperties.txtTaskName.setText(((CheckNodeInfo)e.getNewValue()).getName().toString());
			}
			if(isChecknodeName(taskProperties.txtTaskName.getText())) {				
				taskProperties.pkAccessDate.setValue(taskProperties.pkPlanEnd.getValue());
			}
		}
	}

	/**
	 * �����ı䣬Ӱ����Ч����<br>
	 * δȷ��ǰ����ֱ��set��task����<br>
	 * 
	 * @author emanon
	 * @param e
	 */
	protected void cbSchCalendar_propertyChange(DataChangeEvent e) {
		KDTask clone = new KDTask(selectTask, selectTask.getScheduleTaskInfo());
		Object obj = taskProperties.prmtTaskCalendar.getValue();
		if (obj != null && obj instanceof ScheduleCalendarInfo) {
			ActivityCache cache = ActivityCache.getInstance();
			ScheduleCalendarInfo calendar = cache
					.getCalendar(((ScheduleCalendarInfo) obj).getId()
							.toString());
			this.schCalendar = calendar;
			clone.setCalendar(new ScheduleCalendarImpl(calendar));
		} else {
			clone.setCalendar(new ScheduleCalendarImpl(null));
		}

		Date beginDate = getPlanStartDate();
		Date endDate = getPlanEndDate();
		long duration = clone.getCalendar().getEffectTimes(beginDate, endDate);
		taskProperties.txtWorkDay.setValue(FDCHelper.toBigDecimal(String
				.valueOf(duration)), false);
	}

	/**
	 * ѡ���Ƿ񿼺˽ڵ�ı�
	 * <p>
	 * �ǿ��˽ڵ�ʱ�������ڱ�¼
	 */
	protected void txtYes_ItemChanged() {
		if (taskProperties.txtYes.isSelected()) {
			taskProperties.contAccessDate.setEnabled(true);
			taskProperties.contCheckNode.setEnabled(true);
			taskProperties.pkAccessDate.setRequired(true);
			taskProperties.prmtCheckNode.setRequired(true);
			
//			//�ж��ǲ��Ǽ��Źܿؽڵ㣬����ǣ�д�뿼�����ڣ���д
//			if(isGlobalTaskName(taskProperties.txtTaskName.getText())) {				
//				taskProperties.pkAccessDate.setValue(taskProperties.pkPlanEnd.getValue());
//			}
			//�ж��ǲ��ǿ��˽ڵ㣬����ǣ�д�뿼�����ڣ���д
			if(taskProperties.txtTaskName.getText()!=null){
				
				if(isChecknodeName(taskProperties.txtTaskName.getText())) {				
					taskProperties.pkAccessDate.setValue(taskProperties.pkPlanEnd.getValue());
					if(taskProperties.prmtCheckNode.getValue() == null){
						EntityViewInfo view = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();
						filter.appendFilterItem("name", taskProperties.txtTaskName.getText());
						view.setFilter(filter);		
						CheckNodeCollection cols = new CheckNodeCollection();
						try {
							cols = CheckNodeFactory.getRemoteInstance().getCheckNodeCollection(view);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
						if(cols.size()>0){
							taskProperties.prmtCheckNode.setValue(cols.get(0));
						}
					}
				}
			}
		} else {
			taskProperties.pkAccessDate.setValue(null);
			taskProperties.prmtCheckNode.setValue(null);
			taskProperties.contAccessDate.setEnabled(false);
			taskProperties.contCheckNode.setEnabled(false);
			taskProperties.pkAccessDate.setRequired(false);
			taskProperties.prmtCheckNode.setRequired(false);
		}
	}

	protected boolean isGlobalTaskName(String taskName){
		boolean result = false;
		try {
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql.appendSql(" select fid from T_SCH_GlobalTaskNode "); 
			sql.appendSql(" where FNAME_L2=? ");
			sql.addParam(taskName);
			IRowSet rs = sql.executeQuery();
			if (rs.next()) {
				if (rs.getString(1) == null)
				{				
					result = false;
				}else{
					result = true;
				}
			}else{
				result = false;
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	protected boolean isChecknodeName(String taskName){
		boolean result = false;
		try {
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql.appendSql(" select fid from T_SCH_CheckNode "); 
			sql.appendSql(" where FNAME_L2=? ");
			sql.addParam(taskName);
			IRowSet rs = sql.executeQuery();
			if (rs.next()) {
				if (rs.getString(1) == null)
				{				
					result = false;
				}else{
					result = true;
				}
			}else{
				result = false;
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * ��������ǰ�������б���ȡ�����е�������Ϣ
	 * 
	 * @return ����ǰ�������б� �����ˣ�yuanjun_lan ����ʱ�䣺2012-3-3
	 */
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

	/**
	 * ��������ǰ������Ĵ��ʱ��
	 * <p>
	 * �������޸Ŀ�ʼ������������ʱ������ǰ�������¼�еĴ��ʱ��<br>
	 * ���Ҫ�����������������ӹ�ϵ������ı�
	 * 
	 * @author emanon
	 */
	protected void computePreposing() {
		for (int i = 0; i < taskProperties.kDTablePredecessor.getRowCount(); i++) {
			IRow row = taskProperties.kDTablePredecessor.getRow(i);
			TaskDependency dependency = (TaskDependency) row.getUserObject();
			if (dependency == null) {
				continue;
			}
			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) row.getCell(
					"number").getValue();
			if (taskInfo == null) {
				continue;
			}
			int oldDiff = 0;
			int newDiff = 0;
			Number diff = (Number) row.getCell("diff").getValue();
			if (diff != null) {
				oldDiff = diff.intValue();
			}
			TaskLinkTypeEnum type = (TaskLinkTypeEnum) row.getCell("linkType")
					.getValue();
			if (TaskLinkTypeEnum.FINISH_START.equals(type)) {
				Date preF = taskInfo.getPlanEnd();
				Date nxtS = (Date) taskProperties.pkPlanStart.getValue();
				newDiff = ScheduleCalendarHelper.getEffectTimes(preF, nxtS,
						schCalendar).intValue() - 2;
			} else if (TaskLinkTypeEnum.START_START.equals(type)) {
				Date preS = taskInfo.getPlanStart();
				Date nxtS = (Date) taskProperties.pkPlanStart.getValue();
				newDiff = ScheduleCalendarHelper.getEffectTimes(preS, nxtS,
						schCalendar).intValue() - 1;
			} else if (TaskLinkTypeEnum.FINISH_FINISH.equals(type)) {
				Date preF = taskInfo.getPlanEnd();
				Date nxtF = (Date) taskProperties.pkPlanEnd.getValue();
				newDiff = ScheduleCalendarHelper.getEffectTimes(preF, nxtF,
						schCalendar).intValue();
				if (newDiff < 0) {
					newDiff += 1;
				} else {
					newDiff -= 1;
				}
			}

			if (oldDiff != newDiff) {
				row.getCell("diff").setValue(new Integer(newDiff));
				dependency.setDifference(newDiff);
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

	public void commit() {

		FDCScheduleTaskInfo fdcScheduleTask = (FDCScheduleTaskInfo) selectTask
				.getScheduleTaskInfo();
			try {
				commitBaseTask(fdcScheduleTask);
				commitBasePanel(fdcScheduleTask);
				commitAdvancedTask(fdcScheduleTask);
				commitAdvancedPanel(fdcScheduleTask);
			} catch (EASBizException e) {
				/* TODO �Զ����� catch �� */
				e.printStackTrace();
			} catch (BOSException e) {
				/* TODO �Զ����� catch �� */
				e.printStackTrace();
			} catch (CustomColumnsException e) {
				/* TODO �Զ����� catch �� */
				e.printStackTrace();
			}
		
		
	}

	/**
	 * 
	 * @description �ύ�߼������Ϣ
	 * @author �ź���
	 * @createDate 2011-8-30
	 * @param fdcScheduleTask
	 * @throws CustomColumnsException void
	 * @version EAS7.0
	 * @see
	 */
	private void commitAdvancedPanel(FDCScheduleTaskInfo fdcScheduleTask)
			throws CustomColumnsException {
		// "ʵ���깤����
		fdcScheduleTask.setActualEndDate((Date) taskProperties.pkRealityEnd
				.getValue());
		// "ʵ�ʿ�������
		fdcScheduleTask.setActualStartDate((Date) taskProperties.pkRealityStart
				.getValue());
		
		fdcScheduleTask.getHelpPersonEntry().clear();
		if(taskProperties.prmtHelpPerson.getValue() != null){
			Object value[] = (Object[])taskProperties.prmtHelpPerson.getValue();
			for(int i = 0; i < value.length; i++){
				if(value[i] != null && (value[i] instanceof PersonInfo)){
					HelpPersonEntryInfo entry = new HelpPersonEntryInfo();
					entry.setPerson((PersonInfo)value[i]);
					fdcScheduleTask.getHelpPersonEntry().add(entry);
				}
			}
		}
		fdcScheduleTask.getHelpDeptEntry().clear();
		if(taskProperties.prmtHelpDep.getValue() != null){
			Object value[] = (Object[])taskProperties.prmtHelpDep.getValue();
			for(int i = 0; i < value.length; i++){
				if(value[i] != null){
					HelpDeptEntryInfo entry = new HelpDeptEntryInfo();
					if(value[i] instanceof FullOrgUnitInfo){
						entry.setDept((FullOrgUnitInfo)value[i]);
					}
					fdcScheduleTask.getHelpDeptEntry().add(entry);
				}
			}
		}
//		// Э����
//		fdcScheduleTask
//				.setHelpPerson((PersonInfo) taskProperties.prmtHelpPerson
//						.getValue());
//		// Э������
//		// TODO ����Ϊ������֯
//		if (taskProperties.prmtHelpDep.getValue() != null
//				&& taskProperties.prmtHelpDep.getValue() instanceof AdminOrgUnitInfo) {
//			fdcScheduleTask
//					.setHelpDept(((AdminOrgUnitInfo) taskProperties.prmtHelpDep
//							.getValue()).castToFullOrgUnitInfo());
//		} else {
//			fdcScheduleTask.setHelpDept(null);
//		}
		// ���ո�����
		fdcScheduleTask
				.setRiskResPerson((PersonInfo) taskProperties.prmtRiskChargePerson
						.getValue());
		// ��������
		Object obj = taskProperties.prmtTaskCalendar.getValue();
		if (obj != null && obj instanceof ScheduleCalendarInfo) {
			ScheduleCalendarInfo cal = (ScheduleCalendarInfo) obj;
			fdcScheduleTask.setCalendar(cal);
			selectTask.setCalendar(new ScheduleCalendarImpl(cal));
		} else {
			fdcScheduleTask.setCalendar(null);
			selectTask.setCalendar(ScheduleCalendarImpl.DEFAULT_CALENDAR);
		}
		// fdcScheduleTask.setCalendar((ScheduleCalendarInfo)
		// taskProperties.prmtTaskCalendar.getValue());
		// �ۼ���ɹ�����
		fdcScheduleTask.setWorkLoad(FDCHelper
				.toBigDecimal(taskProperties.txtAppendEndQuantity
						.getNumberValue()));

	}

	/**
	 * 
	 * @description �ύ�߼������Ϣ������ͼ��
	 * @author �ź���
	 * @createDate 2011-8-31
	 * @param fdcScheduleTask
	 * @throws CustomColumnsException void
	 * @version EAS7.0
	 * @see
	 */
	private void commitAdvancedTask(FDCScheduleTaskInfo fdcScheduleTask)
			throws CustomColumnsException {
		// "ʵ���깤����
		// selectTask.getCustomValues().setValue(GanttTreeTableModelExt.
		// strColActualEndDate, taskProperties.pkRealityEnd.getValue());
		// color
		if (!taskProperties.cbColour.getColor().equals(selectTask.getColor())) {
			selectTask.setColor(taskProperties.cbColour.getColor());
		}
		// shape
		if ((selectTask.getShape() == null && taskProperties.cbShape
				.getSelectedIndex() != 0)
				|| (selectTask.getShape() != null && (selectTask.getShape()
						.equals(taskProperties.cbShape.getSelectedItem())))) {
			selectTask.setShape((ShapePaint) taskProperties.cbShape
					.getSelectedItem());
		}
		if (selectTask.getShape() != null) {
			selectTask.setShape(new ShapePaint(selectTask.getShape(),
					Color.white, selectTask.getColor()));
		}
		
		if(taskProperties.prmtHelpDep.getValue() != null){
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColHelpDep, taskProperties.prmtHelpDep.getText());
		}else{
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColHelpDep, null);
		}
		if(taskProperties.prmtHelpPerson.getValue() != null){
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColHelpPerson, taskProperties.prmtHelpPerson.getText());
		}else{
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColHelpPerson, null);
		}
		
		
		// ��ɽ���
		if (FDCHelper.toBigDecimal(
				taskProperties.txtEndSchedule.getNumberValue()).intValue() != selectTask
				.getCompletionPercentage()) {
			TaskMutator mutator = selectTask.createMutator();
			mutator.setCompletionPercentage(FDCHelper.toBigDecimal(
					taskProperties.txtEndSchedule.getNumberValue()).intValue());
			mutator.commit();
		}

	}

	/**
	 * 
	 * @description �ύ���������Ϣ
	 * @author �ź���
	 * @createDate 2011-8-30
	 * @param fdcScheduleTask
	 * @param schedule
	 * @throws CustomColumnsException void
	 * @version EAS7.0
	 * @see
	 */
	private void commitBasePanel(FDCScheduleTaskInfo fdcScheduleTask)
			throws CustomColumnsException {
		//����ר��
		if(taskProperties.prmtBelongSpecial.getValue()!= null){
			fdcScheduleTask.setBelongToSpecial((ProjectSpecialInfo) taskProperties.prmtBelongSpecial.getValue());
		}else{
			fdcScheduleTask.setBelongToSpecial(null);
		}
		
		/** �������� **/
		String taskname = (String) getVerifyInputData(
				taskProperties.txtTaskName.getText(), "��������");
		if (taskname.length() > 100) {
			FDCMsgBox.showWarning(taskProperties, "���������ַ����ܴ���100!");
			SysUtil.abort();
		}
		fdcScheduleTask.setName(taskname);
		// �Ƿ񿼺˽ڵ�
		if (taskProperties.txtYes.isSelected()) {
			if (taskProperties.pkAccessDate.isRequired()){				
				if (FDCHelper.isEmpty(taskProperties.pkAccessDate.getValue())) {
//					FDCMsgBox.showWarning(taskProperties, "�������ڲ���Ϊ�գ���ʾ����������Ϊ���Źܿؽڵ㿼�����ڻ��Զ���ֵ��");
					FDCMsgBox.showWarning(taskProperties, "�������ڲ���Ϊ�գ���ʾ����������Ϊ���˽ڵ㣬�򿼺����ڻ��Զ���ֵ��");
					SysUtil.abort();
				}
				if (FDCHelper.isEmpty(taskProperties.prmtCheckNode.getValue())) {
					FDCMsgBox.showWarning(taskProperties, "���˽ڵ㲻��Ϊ�գ���ʾ����������Ϊ���˽ڵ㣬�򿼺����ڻ��Զ���ֵ��");
					SysUtil.abort();
				}
			}
//				fdcScheduleTask.setCheckDate((Date) getVerifyInputData(taskProperties.pkAccessDate.getValue(), "��������"));
			fdcScheduleTask.setIsCheckNode(true);
			//TODO ���ñ���ô������
			fdcScheduleTask.setCheckDate((Date) taskProperties.pkAccessDate.getValue());
			fdcScheduleTask.setCheckNode((CheckNodeInfo)taskProperties.prmtCheckNode.getValue());
			// ��������
		} else {
			fdcScheduleTask.setIsCheckNode(false);
			// �������� ���
			fdcScheduleTask.setCheckDate(null);
//			fdcScheduleTask.setCheckDate((Date) taskProperties.pkAccessDate.getValue());
		}
		// �������
		fdcScheduleTask.setTaskType((RESchTaskTypeEnum) getVerifyInputData(
				taskProperties.cbTaskType.getSelectedItem(), "�������"));
		// ��������
		selectTask.getCustomValues().setValue(
				GanttTreeTableModelExt.strColTaskName,
				taskProperties.txtTaskName.getText());
		// ����(��Ч����)
		fdcScheduleTask.setEffectTimes(taskProperties.txtWorkDay
				.getBigDecimalValue());
		// ����������
		fdcScheduleTask
				.setQualityEvaluatePerson((PersonInfo) taskProperties.prmtQualityAppraisePerson
						.getValue());
		// ҵ������
		processBizType(fdcScheduleTask);

		// ����ָ��
		fdcScheduleTask
				.setStandardTask((StandardTaskGuideInfo) taskProperties.prmtTaskGuide
						.getValue());
		// ��ע
		fdcScheduleTask.setDescription(taskProperties.txtDesciption.getText());

		// �Ƿ�ؼ�����|��̱�����

		// ������
		fdcScheduleTask
				.setAdminPerson((PersonInfo) taskProperties.prmtDutyPerson
						.getValue());
		// ���β���-- ѡ����ϸ������֯
		// TODO
		// ������ʷ���ݣ����ܻᵼ���жϡ�
		AdminOrgUnitInfo dutyDep = null;
		if (taskProperties.prmtDutyDep.getValue() instanceof AdminOrgUnitInfo)
			dutyDep = (AdminOrgUnitInfo) taskProperties.prmtDutyDep.getValue();
		if (dutyDep != null)
			fdcScheduleTask.setAdminDept(dutyDep.castToFullOrgUnitInfo());
		else
			fdcScheduleTask.setAdminDept((FullOrgUnitInfo) taskProperties.prmtDutyDep.getValue());
		// ����������
		fdcScheduleTask
				.setPlanEvaluatePerson((PersonInfo) taskProperties.prmtScheduleAppraisePerson
						.getValue());
		// �ɹ����
		if (fdcScheduleTask.getSchedule().getProjectSpecial() == null) {
			if (isStageAchievement || (taskProperties.prmtAchievementType.isRequired() && taskProperties.prmtAchievementType.isEnabled())) {
				AchievementTypeInfo typeInfo = (AchievementTypeInfo) getVerifyInputData(taskProperties.prmtAchievementType.getValue(),
						"�ɹ����");
				fdcScheduleTask.setAchievementType(typeInfo);
			} else {
				fdcScheduleTask.setAchievementType(null);
			}
		}
	}

	private void processBizType(FDCScheduleTaskInfo fdcScheduleTask) {
		if (taskProperties.prmtBizType.getValue() == null) {
			if (fdcScheduleTask.getBizType() != null)
				fdcScheduleTask.getBizType().clear();
			return;
		}
		Object[] bizTypes = (Object[]) taskProperties.prmtBizType.getValue();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("bizType.id");
		view.getSelector().add("bizType.name");
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("parent.id", fdcScheduleTask.getId().toString());
		String[] arrays = new String[bizTypes.length];
		ScheduleTaskBizTypeCollection newCol = new ScheduleTaskBizTypeCollection();
		fdcScheduleTask.getBizType().clear();
		for (int i = 0; i < bizTypes.length; i++) {
			ScheduleBizTypeInfo bizType = (ScheduleBizTypeInfo) bizTypes[i];
			arrays[i] = bizType.getId().toString();
			if(arrays[i].equals(FDCScheduleConstant.ACHIEVEMANAGER)){
				if(taskProperties.prmtAchievementType.getValue() == null){
					continue;
				}
			}
			ScheduleTaskBizTypeInfo taskBizType = new ScheduleTaskBizTypeInfo();
			taskBizType.setBizType(bizType);
			taskBizType.setParent(fdcScheduleTask);
			fdcScheduleTask.getBizType().add(taskBizType);
			newCol.add(taskBizType);
		}
		filter.getFilterItems().add(
				new FilterItemInfo("bizType.id", new HashSet(Arrays
						.asList(arrays)), CompareType.INCLUDE));
		try {
			view.setFilter(filter);
			ScheduleTaskBizTypeCollection oldCol = ScheduleTaskBizTypeFactory
					.getRemoteInstance().getScheduleTaskBizTypeCollection(view);
			fdcScheduleTask.getBizType().clear();
			Map oldMap = new HashMap();
			Map newMap = new HashMap();
			for (int i = 0; i < oldCol.size(); i++) {
				ScheduleBizTypeInfo oldBizType = oldCol.get(i).getBizType();
				oldMap.put(oldBizType.getName(), oldCol.get(i));
			}
			for (int i = 0; i < newCol.size(); i++) {
				ScheduleBizTypeInfo newBizType = newCol.get(i).getBizType();
				newMap.put(newBizType.getName(), newCol.get(i));
			}

			for (Iterator it = oldMap.keySet().iterator(); it.hasNext();) {
				String name = (String) it.next();
				if (newMap.containsKey(name)) {
					fdcScheduleTask.getBizType().add(
							(ScheduleTaskBizTypeInfo) oldMap.get(name));
				}
			}

			for (Iterator it = newMap.keySet().iterator(); it.hasNext();) {
				String name = (String) it.next();
				if (!oldMap.containsKey(name)) {
					fdcScheduleTask.getBizType().add(
							(ScheduleTaskBizTypeInfo) newMap.get(name));
				}
			}

		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	private void commitBaseTask(FDCScheduleTaskInfo fdcScheduleTask)
			throws CustomColumnsException, EASBizException, BOSException {
		// ��������
		String taskname = (String) getVerifyInputData(
				taskProperties.txtTaskName.getText(), "��������");
		// String bizType = (String)
		// getVerifyInputData(taskProperties.prmtBizType.getText(), "ҵ������");
		if (FDCHelper.isEmpty(taskname)) {
			FDCMsgBox.showWarning(taskProperties, "�������Ʋ���Ϊ��!");
			taskProperties.txtTaskName.requestFocus();
			SysUtil.abort();
		} else if (taskname.length() > 100) {
			FDCMsgBox.showWarning(taskProperties, "���������ַ����Ȳ��ܴ���100!");
			SysUtil.abort();
		}
		selectTask.getCustomValues().setValue(
				GanttTreeTableModelExt.strColTaskName, taskname);
		selectTask.setName(taskname);
		// selectTask.getCustomValues().setValue(GanttTreeTableModelExt.
		// strColBizType, bizType);
		Date start = (Date) getVerifyInputData(getPlanStartDate(), "�ƻ���ʼ����");
		Date end = (Date) getVerifyInputData(getPlanEndDate(), "�ƻ��������");
		if (!FDCHelper.isEmpty(start) && !FDCHelper.isEmpty(end)) {
			if (getPlanStartDate().compareTo(getPlanEndDate()) > 0) {
				FDCMsgBox.showWarning(taskProperties, "����ʼ���ڲ��ܴ��������������");
				SysUtil.abort();
			}
		}
		// ����������
		if (taskProperties.prmtScheduleAppraisePerson.getValue() == null) {
			boolean isNeedAppr = false;
			if ("3".equals(passMustEvaluation)) {
				isNeedAppr = true;
			} else if ("1".equals(passMustEvaluation)
					&& getOwner() instanceof MainScheduleEditUI) {
				isNeedAppr = true;
			} else if ("2".equals(passMustEvaluation)
					&& getOwner() instanceof SpecialScheduleEditUI) {
				isNeedAppr = true;
			}
			if (isNeedAppr) {
				FDCMsgBox.showWarning(taskProperties,
						"������ͨ���������ۿ����������״̬������Ʋ��ԡ����ã����������˲���Ϊ�գ�");
				SysUtil.abort();
			}
		}
		
		// �ƻ��������--���ܳ����������������ʱ�䷶Χ, ��У��Ҫ��mutator.commit()ǰ����
		String FDCSH010_param_value = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(project.getCostCenter().getId()),
				"FDCSH010");
		// �ƻ��������
		if (taskProperties instanceof FDCSpecialScheduleTaskPropertiesUI) {
			// ��ǰ��ר������ҪУ���ǲ��ǽ������ڴ�������ƻ���
			FDCScheduleTaskInfo main = (FDCScheduleTaskInfo) ((FDCSpecialScheduleTaskPropertiesUI) taskProperties).prmtRelationMainTask
					.getValue();
			if (main != null) {
				if ("1".equals(FDCSH010_param_value)) {
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("srcID", main.getSrcID()));
					filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
					filter.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial is null"));
					filter.getFilterItems().add(new FilterItemInfo("end", end, CompareType.LESS));
					filter.getFilterItems().add(new FilterItemInfo("start", start, CompareType.GREATER));
					filter.setMaskString("#0 and #1 and #2 and (#3 or #4)");
					if (FDCScheduleTaskFactory.getRemoteInstance().exists(filter)) {
						FDCMsgBox.showWarning("����ר���й���ʱ��ר������ļƻ���ʼ�����������ļƻ���ʼ���ڣ��Ҽƻ����С������ƻ�������ڡ�");
						SysUtil.abort();
					}
				}
				FDCScheduleTaskInfo mTask = (FDCScheduleTaskInfo) main.clone();
				mTask.setId(BOSUuid.read(mTask.getSrcID()));
				((FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo()).setDependMainTaskID(mTask);
			}
		} else {
			// ��ǰ����������ҪУ������ר���ǽ��������ǲ��Ǵ��ڱ�����
			StringBuffer str = new StringBuffer("��ϸ��Ϣ���£�\n");
			str.append("ר����������");
			str.append("\t\t\t");
			str.append("������������\n");

			if ("1".equals(FDCSH010_param_value)) {
				int err = 0;
				for (int i = 0; i < taskProperties.tblSpecially.getRowCount(); i++) {
					IRow row = taskProperties.tblSpecially.getRow(i);
					FDCScheduleTaskInfo spTask = (FDCScheduleTaskInfo) row.getCell("name").getValue();
					if (spTask != null) {
						if (!(DateTimeUtils.dateDiff(start, spTask.getStart()) >= 0 && DateTimeUtils.dateDiff(end, spTask.getEnd()) <= 0)) {
							str.append(spTask.getSchedule().getProjectSpecial().getName() + "->" + spTask.getName() + "\t\t" + selectTask.getName()
									+ "\n");
							err++;
						}
					}
				}

				if (err > 0) {
					FDCMsgBox.showDetailAndOK(taskProperties, "����ר���й���ʱ��ר������ļƻ���ʼ�����������ļƻ���ʼ���ڣ��Ҽƻ����С������ƻ�������ڡ�������ϸ��Ϣ�鿴��", str.toString(),
							2);
					SysUtil.abort();
				}
			}
		}
		
		if (!start.equals(fdcScheduleTask.getStart())) {
			TaskMutator mutator = selectTask.createMutator();
			mutator.setStart(ScheduleParserHelper.parseDateToGanttCalendar(start));
			mutator.setDuration(selectTask.getManager().createLength(
					FDCHelper.toBigDecimal(taskProperties.txtWorkDay.getBigDecimalValue()).intValue()));
			mutator.commit();
		}
		
		if (!end.equals(fdcScheduleTask.getEnd())) {
			TaskMutator mutator = selectTask.createMutator();
			mutator.setEnd(ScheduleParserHelper.parseDateToGanttCalendar(end));
			mutator.commit();
		}
		// ���β���-- ѡ����ϸ������֯
		selectTask.getCustomValues().setValue(
				GanttTreeTableModelExt.strColAdminDept,
				taskProperties.prmtDutyDep.getValue());
		// ������
		selectTask.getCustomValues().setValue(
				GanttTreeTableModelExt.stradminPerson,
				taskProperties.prmtDutyPerson.getValue());

		// �Ƿ�ؼ�����|��̱�����
		RESchTaskTypeEnum taskTypeEnum = ((RESchTaskTypeEnum) taskProperties.cbTaskType
				.getSelectedItem());
		selectTask.getCustomValues().setValue(
				GanttTreeTableModelExt.strColTaskType, taskTypeEnum);
		// ҵ������,ר��ô���ҵ������
		if (!(taskProperties instanceof FDCSpecialScheduleTaskPropertiesUI)) {
			if (taskProperties.contBizType.isVisible() && taskProperties.prmtBizType.getValue() != null) {
				Object[] values = (Object[]) taskProperties.prmtBizType.getValue();
				ScheduleTaskBizTypeCollection cols = new ScheduleTaskBizTypeCollection();
				ScheduleTaskBizTypeInfo info = null;
				for (int i = 0; i < values.length; i++) {
					info = new ScheduleTaskBizTypeInfo();
					info.setBizType((ScheduleBizTypeInfo) values[i]);
					info.setParent(fdcScheduleTask);
					cols.add(info);
				}
				selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColBizType, cols);
			} else {
				selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColBizType, null);
			}
			
			if (taskProperties.prmtAchievementType.getValue() != null) {
				selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColAchievementType,
						taskProperties.prmtAchievementType.getValue());
			} else {
				selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColAchievementType, null);
			}
		}
		
		if(taskProperties.pkAccessDate.getValue()!=null){
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColCheckDate, taskProperties.pkAccessDate.getValue());
		}
		
		//�����ɹ���𡱡�����ָ���������������ˡ������������ˡ���Э���ˡ���Э�����š����ֶε�ֵ������ʾ��
		
		
		//�����ֶ��޸�ҲҪͬ����GanttProject
		
		if(taskProperties.prmtScheduleAppraisePerson.getValue() != null){
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColPlanEvaPerson, taskProperties.prmtScheduleAppraisePerson.getValue());
		} else {
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColPlanEvaPerson, null);
		}
		if(taskProperties.prmtQualityAppraisePerson.getValue() != null){
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColQualityEvaPerson, taskProperties.prmtQualityAppraisePerson.getValue());
		} else {
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColQualityEvaPerson, null);
		}
		
		if(taskProperties.prmtTaskGuide.getValue() != null){
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColTaskGuilde, taskProperties.prmtTaskGuide.getValue());
		} else {
			selectTask.getCustomValues().setValue(GanttTreeTableModelExt.strColTaskGuilde, null);
		}
		
		
	}

	/**
	 * @description ���������(������塢�߼����)��Ϣ
	 * @author �ź���
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void load() throws EASBizException, BOSException {
		KDTask selectTask = this.selectTask;
		FDCScheduleTaskInfo fdcScheduleTask = (FDCScheduleTaskInfo) selectTask
				.getScheduleTaskInfo();
		loadBasePanel(fdcScheduleTask);
		loadAdvancedPanel(fdcScheduleTask);
		initCBCalendar(fdcScheduleTask);
		initListener();
	}

	private void initCBCalendar(FDCScheduleTaskInfo taskInfo) {
		ScheduleCalendarInfo cal = taskInfo.getCalendar();
		taskProperties.prmtTaskCalendar.setValue(cal);
	}

	/**
	 * 
	 * @description ���ػ����汾��Ϣ
	 * @author �ź���
	 * @createDate 2011-8-30
	 * @param fdcScheudleTask
	 * @param baseInfo
	 *            void
	 * @version EAS7.0
	 * @see
	 */
	private void loadBasePanel(FDCScheduleTaskInfo fdcScheudleTask) {
		//����ר��
		taskProperties.prmtBelongSpecial.setValue(fdcScheudleTask.getBelongToSpecial());
		// ����
		taskProperties.txtTaskName.setText(fdcScheudleTask.getName());
		// ����
		taskProperties.txtNumber.setText(fdcScheudleTask.getLongNumber().replace('!', '.'));
		// ��ע
		taskProperties.txtDesciption.setText(fdcScheudleTask.getDescription());
		// �������
		taskProperties.cbTaskType.addItems(EnumUtils.getEnumList(
				"com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum").toArray());
		if (fdcScheudleTask.getSchedule().getProjectSpecial() != null) {
			taskProperties.cbTaskType.removeItem(RESchTaskTypeEnum.MILESTONE);
		}
		if (FDCHelper.isEmpty(fdcScheudleTask.getTaskType())) {
			fdcScheudleTask.setTaskType(RESchTaskTypeEnum.COMMONLY);
		} else {
			taskProperties.cbTaskType.setSelectedItem(fdcScheudleTask
					.getTaskType());
		}
		changeTaskNameByRESchTaskType();
		// ����ָ��
		taskProperties.prmtTaskGuide
				.setValue(fdcScheudleTask.getStandardTask());
		// ҵ������
		// ����ҵ�����͡�Ϊ���׶��Գɹ���ʱ����ʾ���ɹ�����ֶΣ�������ʾ���ֶΡ�
		ScheduleTaskBizTypeCollection taskBizTypes = fdcScheudleTask.getBizType();
		 Object[] objs = taskBizTypes == null ? null : new Object[taskBizTypes.size()];
		taskProperties.prmtAchievementType.setEnabled(false);
		for (int i = 0; taskBizTypes != null && i < taskBizTypes.size(); i++) {
			ScheduleBizTypeInfo bizType = taskBizTypes.get(i).getBizType();
			objs[i] = bizType;
			if (!FDCHelper.isEmpty(bizType) && bizType.getName().equals("�׶��Գɹ�")) {
				taskProperties.prmtAchievementType.setEnabled(true);
			}
			// if (null != taskBizTypes.get(i).getBizType().getId()
			// && (null == taskBizTypes.get(i).getBizType().getName() || ""
			// .equals(taskBizTypes.get(i).getBizType().getName()))) {
			// try {
			// ScheduleBizTypeInfo scheduleBizTypeInfo = ScheduleBizTypeFactory
			// .getRemoteInstance().getScheduleBizTypeInfo(
			// new ObjectUuidPK(taskBizTypes.get(i)
			// .getBizType().getId()));
			// objs[i] = scheduleBizTypeInfo;
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
		}
		// taskProperties.prmtBizType.setDisplayFormat("$name$");
		// taskProperties.prmtBizType.setValue(fdcScheudleTask.getBizType());
		taskProperties.prmtBizType.setValue(objs);
		// ��������
		taskProperties.pkPlanStart.setValue(fdcScheudleTask.getStart());
		// �깤ʱ��
		taskProperties.pkPlanEnd.setValue(fdcScheudleTask.getEnd());
		// ������(ְԱ)
		taskProperties.prmtDutyPerson
				.setValue(fdcScheudleTask.getAdminPerson());
		// ���β���
		// if (FDCHelper.isEmpty(fdcScheudleTask.getAdminDept())) {
		// taskProperties.prmtDutyDep.setValue(SysContext.getSysContext().
		// getCurrentOrgUnit());
		// } else {
		taskProperties.prmtDutyDep.setValue(fdcScheudleTask.getAdminDept());
		// }
		// ��Ч����(��Ȼ����)
		if (FDCHelper.isEmpty(fdcScheudleTask.getEffectTimes())) {
			taskProperties.txtWorkDay.setValue(new Integer(0));
		} else {
			taskProperties.txtWorkDay.setValue(new Integer(fdcScheudleTask
					.getEffectTimes().intValue()));
		}
		// �Ƿ񿼺˽ڵ�property
		if (fdcScheudleTask.isIsCheckNode()) {
			taskProperties.txtYes.setSelected(true);
			taskProperties.txtNo.setSelected(false);
			taskProperties.contAccessDate.setEnabled(true);
			taskProperties.contCheckNode.setEnabled(true);
			taskProperties.pkAccessDate.setRequired(true);
			taskProperties.prmtCheckNode.setRequired(true);
			// ��������
			taskProperties.pkAccessDate.setValue(fdcScheudleTask.getCheckDate());
			// ���˽ڵ�
			if(fdcScheudleTask.getCheckNode()!=null){
				try {
					taskProperties.prmtCheckNode.setValue(CheckNodeFactory.getRemoteInstance().getCheckNodeInfo(new ObjectUuidPK(fdcScheudleTask.getCheckNode().getId())));
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		} else {
			taskProperties.txtYes.setSelected(false);
			taskProperties.txtNo.setSelected(true);
			taskProperties.contAccessDate.setEnabled(false);
			taskProperties.contCheckNode.setEnabled(false);
			taskProperties.pkAccessDate.setRequired(false);
			taskProperties.prmtCheckNode.setRequired(false);
			taskProperties.pkAccessDate.setValue(null);
			taskProperties.prmtCheckNode.setValue(null);
		}
		// ����������
		taskProperties.prmtQualityAppraisePerson.setValue(fdcScheudleTask
				.getQualityEvaluatePerson());
		// ����������
		taskProperties.prmtScheduleAppraisePerson.setValue(fdcScheudleTask
				.getPlanEvaluatePerson());
		// �ɹ����
		taskProperties.prmtAchievementType.setValue(fdcScheudleTask
				.getAchievementType());
	}

	private void isEnableArchType(boolean isVisible) {
		isStageAchievement = isVisible;
		if (isVisible) {
			taskProperties.prmtAchievementType.setEnabled(true);
		} else {
			taskProperties.prmtAchievementType.setEnabled(false);
			taskProperties.prmtAchievementType.setValue(null);
		}
	}

	/**
	 * 
	 * @description ���ظ߼������Ϣ
	 * @author �ź���
	 * @createDate 2011-8-16
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void loadAdvancedPanel(FDCScheduleTaskInfo fdcScheudleTask) {
		// ʵ�ʿ�ʼʱ��
		taskProperties.pkRealityStart.setValue(fdcScheudleTask
				.getActualStartDate());
		// ʵ���깤����
		taskProperties.pkRealityEnd
				.setValue(fdcScheudleTask.getActualEndDate());
		// ��������
		// TODO ѡ���������á��е�����������ѡ����������Զ����¡����ڡ������ƻ���ʼʱ�䡱�����ƻ�����ʱ�䡱
		if (FDCHelper.isEmpty(fdcScheudleTask.getCalendar())) {
			taskProperties.prmtTaskCalendar.setValue(fdcScheudleTask
					.getSchedule().getCalendar());
		} else {
			taskProperties.prmtTaskCalendar.setValue(fdcScheudleTask
					.getCalendar());
		}
		// ��״
		// gantt project's shape Renderer

		taskProperties.cbShape.removeAllItems();
		taskProperties.cbShape.setRenderer(new PaintCellRenderer());
		taskProperties.cbShape.addItems(ShapeConstants.PATTERN_LIST);
		if (selectTask.shapeDefined()) {
			for (int j = 0; j < ShapeConstants.PATTERN_LIST.length; j++) {
				if (selectTask.getShape()
						.equals(ShapeConstants.PATTERN_LIST[j])) {
					taskProperties.cbShape.setSelectedIndex(j);
					break;
				}
			}
		}
		// ��ɫ
		taskProperties.cbColour.setColor(selectTask.getColor());
		// ���ȼ�
		taskProperties.cbPriorityLevel.removeAllItems();
		taskProperties.cbPriorityLevel.addItem(language.getText("low"));
		taskProperties.cbPriorityLevel.addItem(language.getText("normal"));
		taskProperties.cbPriorityLevel.addItem(language.getText("hight"));
		taskProperties.cbPriorityLevel.setSelectedIndex(fdcScheudleTask.getPriority());

		HelpPersonEntryCollection pcol= fdcScheudleTask.getHelpPersonEntry();
		Object helpPerson[] = new Object[pcol.size()];
		for(int i = 0; i < pcol.size(); i++){
			HelpPersonEntryInfo person=pcol.get(i);
			helpPerson[i]=person.getPerson();
		}
		HelpDeptEntryCollection dcol=fdcScheudleTask.getHelpDeptEntry();
		Object helpDept[] = new Object[dcol.size()];
		for(int i = 0; i < dcol.size(); i++){
			HelpDeptEntryInfo orgUnit=dcol.get(i);
			helpDept[i]=orgUnit.getDept();
		}
		
		// Э����
		taskProperties.prmtHelpPerson.setValue(helpPerson);
		// Э������
		taskProperties.prmtHelpDep.setValue(helpDept);
		// ������(��ɽ���)
		if (FDCHelper.isEmpty(fdcScheudleTask.getComplete())) {
			fdcScheudleTask.setComplete(new BigDecimal(0));
		}
		taskProperties.txtEndSchedule.setValue(fdcScheudleTask.getComplete());
		// �ۼ���ɹ�����
		if (FDCHelper.isEmpty(fdcScheudleTask.getWorkLoad())) {
			taskProperties.txtAppendEndQuantity.setValue(new BigDecimal(0));
		} else {
			taskProperties.txtAppendEndQuantity.setValue(fdcScheudleTask
					.getWorkLoad());
		}
		// ���ո�����
		taskProperties.prmtRiskChargePerson.setValue(fdcScheudleTask
				.getRiskResPerson());
	}

	/**
	 * @description ��֤����
	 * @author �ź���
	 * @createDate 2011-8-30 void
	 * @version EAS7.0
	 * @see
	 */

	protected Object getVerifyInputData(Object obj, String mesg) {
		if (FDCHelper.isEmpty(obj)) {
			FDCMsgBox.showWarning(taskProperties, mesg + "����Ϊ�գ�");
			SysUtil.abort();
		}
		return obj;
	}

	/**
	 * 
	 * @description �������������������͸ı�
	 * @author �ź���
	 * @createDate 2011-8-30
	 * @version EAS7.0
	 * @see
	 */
	private void cbTaskType_itemStateChanged(java.awt.event.ItemEvent e) {
		RESchTaskTypeEnum taskTypeEnum = null;
		if (e.getStateChange() == 1) {
			taskTypeEnum = (RESchTaskTypeEnum) e.getItem();
		} else {
			return;
		}
		if (taskTypeEnum.equals(RESchTaskTypeEnum.COMMONLY)) {
			taskProperties.txtTaskName.setCustomForegroundColor(Color.BLACK);
			return;
		}
		if (taskTypeEnum.equals(RESchTaskTypeEnum.KEY)) {
			taskProperties.txtTaskName.setCustomForegroundColor(Color.BLUE);
			return;
		}
		if (taskTypeEnum.equals(RESchTaskTypeEnum.MILESTONE)) {
			taskProperties.txtTaskName.setCustomForegroundColor(Color.RED);
			return;
		}
	}

	private void changeTaskNameByRESchTaskType() {
		taskProperties.cbTaskType
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent e) {
						try {
							cbTaskType_itemStateChanged(e);
						} catch (Exception exc) {
							taskProperties.handUIException(exc);
						} finally {
						}
					}
				});

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
		// �����ר������ִ��ʱ�������пؼ�������
		if (getOwner() instanceof MainScheduleExecuteUI
				|| getOwner() instanceof SpecialScheduleExecuteUI) {
			lockControl();
		}
	}

	private Object getOwner() {
		Object owner = taskProperties.getUIContext().get("Owner");
		// �ദ�õ������������жϿ�
		owner = owner == null ? "" : owner;
		return owner;
	}

	private void lockControl() {
		KDLabelContainer cont = null;

		for (int i = 0; i < taskProperties.pnlBaseInfo.getComponentCount(); i++) {

			if (taskProperties.pnlBaseInfo.getComponent(i) instanceof KDLabelContainer) {
				cont = (KDLabelContainer) taskProperties.pnlBaseInfo
						.getComponent(i);
				cont.setEnabled(false);
			}
		}

		for (int i = 0; i < taskProperties.pnlAdvanced.getComponentCount(); i++) {

			if (taskProperties.pnlAdvanced.getComponent(i) instanceof KDLabelContainer) {
				cont = (KDLabelContainer) taskProperties.pnlAdvanced
						.getComponent(i);
				cont.setEnabled(false);
			}
		}
		// ��ذ�ťҲ�����õ�
		taskProperties.btnAdd.setVisible(false);
		taskProperties.btnDel.setVisible(false);
		taskProperties.actionSave.setEnabled(false);
		taskProperties.cbColour.setEnabled(false);
		
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
		lockControl();
	}

	/**
	 * ȡ�üƻ���ʼ���ڣ�ʱ������
	 * 
	 * @return
	 */
	private Date getPlanStartDate() {
		Date date = (Date) taskProperties.pkPlanStart.getValue();
		if (date != null) {
			date = FDCDateHelper.getDayBegin(date);
		}
		return date;
	}

	/**
	 * ȡ�üƻ��������ڣ�ʱ������
	 * 
	 * @return
	 */
	private Date getPlanEndDate() {
		Date date = (Date) taskProperties.pkPlanEnd.getValue();
		if (date != null) {
			date = FDCDateHelper.getDayBegin(date);
		}
		return date;
	}
}
