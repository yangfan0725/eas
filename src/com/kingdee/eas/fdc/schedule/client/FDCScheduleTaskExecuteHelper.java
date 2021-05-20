/*
 * @(#)FDCScheduleTaskExecuteHelper.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleExecuteEnum;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.ScheduleHelper;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.DateTimeUtils;

/***
 * 
 * ����:��Ҫ������ִ���е�һЩУ�鹦�ܽ��г�ȡ
 * @author yuanjun_lan  date:2011-10-21
 * @version EAS6.1
 */
public class FDCScheduleTaskExecuteHelper {
	
	private static Logger logger = Logger.getLogger(FDCScheduleTaskExecuteHelper.class);
	public final static String hasCompletedCantModify = "��ɽ����ѻ㱨Ϊ100%���������޸Ļ㱨��"; 
	
	public static void beforeOperator(FDCScheduleTaskInfo task,String oprtName){
	   	checkLeafTask(task,oprtName);
	   	checkTaskSuccessState(task,oprtName);
	}
	
	public static void checkLeafTask(FDCScheduleTaskInfo task,String oprtName){
		if(!task.isIsLeaf()) {
			FDCMsgBox.showError("���ܶԷ���ϸ�������"+oprtName);
			SysUtil.abort();
		} 
	}
	
	private static void checkTaskSuccessState(FDCScheduleTaskInfo task, String oprtName) {
		if(task.getComplete()==null || !(task.getComplete().compareTo(FDCHelper.ONE_HUNDRED)==0)){
			FDCMsgBox.showError("���ܶ�δ�깤���������"+oprtName);
			SysUtil.abort();
		}
		
		
	}
	/**
	 * 
	 * �����������ǰ��û�а�ְԱ�ͷ��ص�ǰ�û����û���������󶨾ͷ��ص�ǰ�û���ְԱ����
	 * @return
	 * �����ˣ�yuanjun_lan
	 * ����ʱ�䣺2011-11-11
	 */
	
	public static String getDisplayPersonInfo(UserInfo user){
		if(user.getPerson() != null && user.getPerson().getName() != null){
			return user.getPerson().getName();
		}else{
			return user.getNumber();
		}
		
	}
	
	public static EntityViewInfo getTaskFilterEntityView(FDCScheduleExecuteEnum enumType,String fscheduleID) throws BOSException{
		BOSUuid currUser = (SysContext.getSysContext().getCurrentUserInfo().getPerson() == null ? null : SysContext.getSysContext()
				.getCurrentUserInfo().getPerson().getId());
		Timestamp nowTime = FDCDateHelper.getServerTimeStamp();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("schedule.id", fscheduleID);
		Date[] nextWeekDate = getNextDayOfWeek(nowTime);
		if (enumType == FDCScheduleExecuteEnum.TASK_ALL) {
			// ��������
		} else if (enumType == FDCScheduleExecuteEnum.TASK_OVERDUE) {
			/*
			 * �������񣺼ƻ��������С�ڵ�ǰ����
			 */
			filter.getFilterItems().add(new FilterItemInfo("end", FDCDateHelper.addDays(nowTime, 1), CompareType.LESS));
			filter.getFilterItems().add(new FilterItemInfo("complete", FDCHelper.ONE_HUNDRED, CompareType.LESS));
		} else if (enumType == FDCScheduleExecuteEnum.TASK_MYTREADO) {
			/*
			 * �ҵĴ�������������Ϊ��ǰ�û�,�ҵ�ǰ�����ڼƻ���ʼ���ں��������֮��
			 */
			if (currUser == null) {
				FDCMsgBox.showError("��ǰ�û�û�й���ְԱ���޷����������˽��й��ˣ�");
				SysUtil.abort();
			}
			filter.getFilterItems().add(new FilterItemInfo("complete", FDCHelper.ONE_HUNDRED,CompareType.LESS));
			filter.getFilterItems().add(new FilterItemInfo("adminPerson.id", currUser, CompareType.EQUALS));
//			filter.getFilterItems().add(new FilterItemInfo("start", nowTime, CompareType.LESS));
//			filter.getFilterItems().add(new FilterItemInfo("end", nowTime, CompareType.GREATER));
//			filter.getFilterItems().add(new FilterItemInfo("end", nowTime, CompareType.LESS));
//			
//			FilterInfo orFilter = new FilterInfo();
//			orFilter.getFilterItems().add(new FilterItemInfo("complete",null,CompareType.EQUALS));
//			orFilter.getFilterItems().add(new FilterItemInfo("adminPerson.id", currUser, CompareType.EQUALS));
//			filter.mergeFilter(orFilter, "or");
			
		} else if (enumType == FDCScheduleExecuteEnum.TASK_WEEKEND) {
			/*
			 * ����Ӧ������񣺼ƻ���������ڱ�������֮ǰ��δ�������
			 */
			Date theWeekEnd  = DateTimeUtils.truncateDate(DateTimeUtils.addDay(getLastDayOfWeek(nowTime), 1L));
			filter.getFilterItems().add(new FilterItemInfo("end",theWeekEnd, CompareType.LESS));
			filter.getFilterItems().add(new FilterItemInfo("complete", FDCHelper.ONE_HUNDRED, CompareType.LESS));
		} else if (enumType == FDCScheduleExecuteEnum.TASK_WEEKBEGIN) {
			// ����Ӧ��ʼ����ʾ�ƻ���ʼ����������֮���δ�������
			filter.getFilterItems().add(new FilterItemInfo("start", nextWeekDate[0], CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("start", nextWeekDate[1], CompareType.LESS_EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("complete", FDCHelper.ONE_HUNDRED, CompareType.LESS));
		} else if (enumType == FDCScheduleExecuteEnum.TASK_MYEVALUATE) {
			// �ҵĴ��������񣺽��������˺�����������Ϊ��ǰ�û�
			if (currUser == null) {
				FDCMsgBox.showError("��ǰ�û�û�й���ְԱ���޷����������˽��й��ˣ�");
				SysUtil.abort();
			}
			filter.getFilterItems().add(new FilterItemInfo("planEvaluatePerson", currUser, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("qualityEvaluatePerson", currUser, CompareType.EQUALS));
			
			/* modified by zhaoqin for ������������ on 2014/10/14 */
			filter.getFilterItems().add(new FilterItemInfo("complete", FDCHelper.ONE_HUNDRED));
			
			/* �Ѿ��㱨�����ݲ�Ӧ��ʾ: modified by zhaoqin for R141027-0038 on 2014/10/14
			 * 1. ���"����������"��"����������"���ǵ�ǰ�û�,���ѽ�����"��������"��"��������"��������ʾ
			 * 2. ���"����������"�ǵ�ǰ�û�,���ѽ�����"��������"��������ʾ
			 * 3. ���"����������"�ǵ�ǰ�û�,���ѽ�����"��������"��������ʾ
			 */
			String curUserId = currUser.toString();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT t.fid FROM T_SCH_FDCSchedule s \n");
			sb.append("join T_SCH_FDCScheduleTask t ON t.FScheduleID = s.fid \n");
			sb.append("join (select e1.FRelateTaskID from T_SCH_TaskEvaluationBill e1 \n");
			sb.append("	join T_SCH_TaskEvaluationBill e2 on e2.FRelateTaskID = e1.FRelateTaskID \n");
			sb.append("	where e1.FEvaluationType = '1SCHEDULE' and e2.FEvaluationType = '2QUALITY' \n");
			sb.append(") e on e.FRelateTaskID = t.fid \n");
			sb.append("WHERE t.FPlanEvaluatePersonID = '").append(curUserId).append("' \n");
			sb.append("and t.FQualityEvaluatePersonID = '").append(curUserId).append("' \n");
			sb.append("and s.fid = '").append(fscheduleID).append("' \n");
			sb.append("union \n");
			sb.append("SELECT t.fid FROM T_SCH_FDCSchedule s \n");
			sb.append("join T_SCH_FDCScheduleTask t ON t.FScheduleID = s.fid \n");
			sb.append("join T_SCH_TaskEvaluationBill e on e.FRelateTaskID = t.fid \n");
			sb.append("WHERE t.FPlanEvaluatePersonID = '").append(curUserId).append("' \n");
			sb.append("and t.FQualityEvaluatePersonID <> '").append(curUserId).append("' \n");
			sb.append("and e.FEvaluationType = '1SCHEDULE' \n");
			sb.append("and s.fid = '").append(fscheduleID).append("' \n");
			sb.append("union \n");
			sb.append("SELECT t.fid FROM T_SCH_FDCSchedule s \n");
			sb.append("join T_SCH_FDCScheduleTask t ON t.FScheduleID = s.fid \n");
			sb.append("join T_SCH_TaskEvaluationBill e on e.FRelateTaskID = t.fid \n");
			sb.append("WHERE t.FPlanEvaluatePersonID <> '").append(curUserId).append("' \n");
			sb.append("and t.FQualityEvaluatePersonID = '").append(curUserId).append("' \n");
			sb.append("and e.FEvaluationType = '2QUALITY' \n");
			sb.append("and s.fid = '").append(fscheduleID).append("' \n");
			filter.getFilterItems().add(new FilterItemInfo("id", sb.toString(), CompareType.NOTINNER));

			filter.setMaskString("#0 and (#1 or #2) and #3 and #4");

		} else if (enumType == FDCScheduleExecuteEnum.TASK_MILEPOST) {
			// ��̱�����
			filter.getFilterItems().add(new FilterItemInfo("taskType", RESchTaskTypeEnum.MILESTONE, CompareType.EQUALS));

		} else if (enumType == FDCScheduleExecuteEnum.TASK_HINGE) {
			// �ؼ�����
			filter.getFilterItems().add(new FilterItemInfo("taskType", RESchTaskTypeEnum.KEY, CompareType.EQUALS));
		}

		// EntityViewInfo taskView =
		// ScheduleHelper.getScheduleView(fscheduleID);
		EntityViewInfo taskView = new EntityViewInfo();
		taskView.getSelector().add(new SelectorItemInfo("*"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.type"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.difference"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.id"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.hardness"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.task.id"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.task.name"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.task.number"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.dependTask.id"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.dependTask.name"));
		taskView.getSelector().add(new SelectorItemInfo("dependEntrys.dependTask.number"));
		taskView.getSelector().add(new SelectorItemInfo("adminPerson.name"));
		taskView.getSelector().add(new SelectorItemInfo("adminPerson.number"));
		taskView.getSelector().add(new SelectorItemInfo("adminPerson.id"));
		taskView.getSelector().add(new SelectorItemInfo("adminDept.id"));
		taskView.getSelector().add(new SelectorItemInfo("adminDept.name"));
		taskView.getSelector().add(new SelectorItemInfo("adminDept.number"));
		taskView.getSelector().add(new SelectorItemInfo("bizType.bizType.name"));
		taskView.getSelector().add(new SelectorItemInfo("bizType.bizType.id"));
		taskView.getSelector().add(new SelectorItemInfo("bizType.bizType.number"));
		taskView.getSelector().add(new SelectorItemInfo("schedule.project.id"));
		taskView.getSelector().add(new SelectorItemInfo("schedule.project.number"));
		taskView.getSelector().add(new SelectorItemInfo("schedule.project.name"));
		taskView.getSelector().add(new SelectorItemInfo("schedule.project.costCenter.id"));
		taskView.getSelector().add(new SelectorItemInfo("qualityEvaluatePerson.id"));
		taskView.getSelector().add(new SelectorItemInfo("qualityEvaluatePerson.name"));
		taskView.getSelector().add(new SelectorItemInfo("qualityEvaluatePerson.number"));
		taskView.getSelector().add(new SelectorItemInfo("qualityEvaluatePersonOther.id"));
		taskView.getSelector().add(new SelectorItemInfo("qualityEvaluatePersonOther.name"));
		taskView.getSelector().add(new SelectorItemInfo("qualityEvaluatePersonOther.number"));
		/** ׷�ӽ��������� **/
		taskView.getSelector().add(new SelectorItemInfo("planEvaluatePerson.id"));
		taskView.getSelector().add(new SelectorItemInfo("planEvaluatePerson.name"));
		taskView.getSelector().add(new SelectorItemInfo("planEvaluatePerson.number"));
		
		/* modified by zhaoqin for R140925-0073 on 2015/01/19 */
		taskView.getSelector().addObjectCollection(ScheduleHelper.getTaskSelector());
		
		SorterItemCollection sorts = new SorterItemCollection();
		SorterItemInfo sortItem  = new SorterItemInfo("longnumber");
		sorts.add(sortItem);
		taskView.setSorter(sorts);

		taskView.setFilter(filter);
		
		return taskView;
	}
	
	
	public static Date getFirstDayOfWeek(Date currTime) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(currTime);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c.getTime();
	}

	/**
	 * @description 	���ݵ�ǰ����ʱ����㵱ǰ�����һ������ʱ��
	 * @author 			�ܺ���
	 * @createDate 		2011-08-29
	 * @param 			currTime
	 * @return 			Date
	 * 
	 * @version 		EAS7.0
	 */
	public static Date getLastDayOfWeek(Date currTime) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(currTime);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
		return c.getTime();
	}

	/**
	 * @description 	���ݵ�ǰ�ܵ����һ��,�������һ�ܵĵ�һ������һ������ʱ��
	 * @author 			�ܺ���
	 * @createDate 		2011-08-29
	 * @param 			currTime
	 *            		<ϵͳ��ǰ����>
	 * @return 			Date[]
	 * 
	 * @version 		EAS7.0
	 */
	public static Date[] getNextDayOfWeek(Date currTime) {
		Date[] nextWeek = new Date[2];
		Date lastDayofWeek = getLastDayOfWeek(currTime);
		Calendar c = new GregorianCalendar();
		c.setTime(lastDayofWeek);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date nextWeekBgDay = DateUtils.addDay(c.getTime(), 1);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		Date nextWeekEdDay = DateUtils.addDay(c.getTime(), 7);
		
		nextWeek[0] = nextWeekBgDay;
		nextWeek[1] = nextWeekEdDay;

		return nextWeek;

	}

	/**
	 * ���Ȼ㱨ǰ�ļ���Ƿ���Ҫ���������ʾ���������������£�<p>
	 * 
	 * <b>���Ȼ㱨Ϊ100%��Ŀ���</b><br>
	 * 
		<p>���������ͨ���������ۿ����������״̬����ɿ��Ʋ��ԡ�Ϊ�������ơ�<br>
		������㱨Ϊ100%�󣬿��Ʋ����ٽ��л㱨��������Ӧ����ʾ��Ϣ
		���1.����������ϵġ����Ȼ㱨����ťʱ��������ʾ��Ϣ������ɽ����ѻ㱨Ϊ100%�������޸ġ�
		���2.˫������򿪵����Խ����У���ʵ�ʿ�ʼʱ�䡱����ʵ��/Ԥ�����ʱ�䡱������ɽ��ȡ��ֶλ��ԣ�
		���3.��������塰���Ȼ㱨��ҳǩ�еİ�ť�����Ȼ㱨����ɾ����ʱ��������ʾ��Ϣ������ɽ����ѻ㱨Ϊ100%�������޸ġ�</p>
	
		<p>���������ͨ���������ۿ����������״̬����ɿ��Ʋ��ԡ�Ϊ�����ơ�<br>
		������㱨Ϊ100%�󣬲��������������ۺ�Ҳ����Ganttͼ������״̬Ϊ��ʱ��ɻ���ʱ��ɣ������Ʋ����ٽ��л㱨��������Ӧ����ʾ��Ϣ
		���1.����������ϵġ����Ȼ㱨����ťʱ��������ʾ��Ϣ������ɽ����ѻ㱨Ϊ100%�������޸ġ�
		���2.˫������򿪵����Խ����У���ʵ�ʿ�ʼʱ�䡱����ʵ�����ʱ�䡱������ɽ��ȡ��ֶλ��ԣ�
		���3.�����������ǩ�Ľ��Ȼ㱨�ı��塰���Ȼ㱨��ҳǩ�еİ�ť�����Ȼ㱨����ɾ����ʱ��������ʾ��Ϣ������ɽ����ѻ㱨Ϊ100%�������޸ġ�</p>
	 * @author owen_wen 2013-7-25
	 */
	public static boolean checkShouldBeControledBeforeProgressReport(FDCScheduleTaskInfo task) throws EASBizException,
			BOSException {
		if (FDCNumberHelper.isLessThan(task.getComplete(), FDCConstants.ONE_HUNDRED)) {
			return false; // δ��ɣ����Լ����㱨�Ȳ���
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcRelateTask", task.getSrcID()));
		filter.getFilterItems().add(new FilterItemInfo("isComplete", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("completePrecent", 100));
		boolean isComplete = ScheduleTaskProgressReportFactory.getRemoteInstance().exists(filter);
		if (!isComplete) {
			return false; // δ��ɣ����Լ����㱨�Ȳ���
		} // ���ѻ㱨100%��ɣ���Ҫ����������ж�
		
		String FDCSH011_param_value = ParamManager.getParamValue(null, new ObjectUuidPK(SysContext.getSysContext().getCurrentCostUnit().getId()),
				"FDCSH011");
		
		// 0�������� 	1 ���������������	2�������ר������	3������������ר������
		
		if (isComplete && "0".equals(FDCSH011_param_value)) { // ���������� �� 100%���
			return true;
		}
		
		boolean isMainSchedule = task.getSchedule().getProjectSpecial() == null;
		if (isMainSchedule) { // ����ƻ��㱨
			if ("1".equals(FDCSH011_param_value) || "3".equals(FDCSH011_param_value)) {
				// ����û�ж����������ۣ����������ߴ����ο�MainScheduleExecuteUI�еĴ���
				return true;
			}
		} else {//ר��ƻ��㱨 
			if ("2".equals(FDCSH011_param_value) || "3".equals(FDCSH011_param_value)) {
				return true;
			}
		}
		
		return false;
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////

	/**
	 * ����Ƿ�Ҫ����������������������Ϣ�����ж��˳�
	 * 
	 * @param task
	 *            ��������
	 * @author owen_wen 2013-7-25
	 */
	public static void checkAndShowMsgInfoWhenCompleted(FDCScheduleTaskInfo task) throws EASBizException, BOSException {
		/* modified by zhaoqin for R141022-0210 on 2014/12/10 */
		//checkAndShowMsgInfoWhenCompleted(task, FDCScheduleTaskExecuteHelper.hasCompletedCantModify, false);
		checkAndShowMsgInfoWhenCompleted(task, FDCScheduleTaskExecuteHelper.hasCompletedCantModify, true);
	}

	/**
	 * ������ ����Ƿ�Ҫ����������������������Ϣ�����ж��˳�
	 * 
	 * @param task
	 *            ��������
	 * @param msg
	 *            ��ʾ��Ϣ
	 * @param isCheckTaskEvaluation
	 *            �Ƿ�����������
	 * @throws EASBizException
	 * @throws BOSException
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-30
	 */
	public static void checkAndShowMsgInfoWhenCompleted(FDCScheduleTaskInfo task, String msg,
			boolean isCheckTaskEvaluation) throws EASBizException, BOSException {
		if (isCheckTaskEvaluation) {
			boolean hasPramControl = true;
			String paramValue = ParamManager.getParamValue(null, new ObjectUuidPK(SysContext.getSysContext()
					.getCurrentCostUnit().getId()), "FDCSH011");
			// 0�������� 1 ��������������� 2�������ר������ 3������������ר������
			if ("0".equals(paramValue)) { // ����������
				hasPramControl = false;
			}
			if (!hasPramControl) {
				return;
			}
		}

		boolean flag = isProgressReportCompleted(task);
		if (flag) {
			FDCMsgBox.showWarning(msg);
			SysUtil.abort();
		}
	}

	/**
	 * ����������㱨�Ƿ��Ѿ����
	 * 
	 * @param task
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @author rd_skyiter_wang
	 * @createDate 2014-5-30
	 */
	public static boolean isProgressReportCompleted(FDCScheduleTaskInfo task) throws EASBizException, BOSException {
		boolean flag = false;

		boolean shouldBeControled = checkShouldBeControledBeforeProgressReport(task);
		if (shouldBeControled) {// ��Ҫ���ƣ��Ǿ͸�����ʾ
			String state = task.getString("state");
			// ����״̬˵���뿴GanttGraphicArea.getToolTipText(MouseEvent event)�е�˵��
			if ("0".equals(state) || "1".equals(state)) { // ��ʱ��ɡ���ʱ���
				flag = true;
			}
		}

		return flag;
	}

	// //////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////
}
