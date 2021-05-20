package com.kingdee.eas.fdc.schedule.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.OpReportBaseHelper;
import com.kingdee.eas.fdc.schedule.OpReportEntryBaseInfo;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportInfo;
import com.kingdee.eas.util.app.ContextUtil;

public class OpReportBaseControllerBean extends AbstractOpReportBaseControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.OpReportBaseControllerBean");
    

	public static final String WEEK_DESC = "�ܱ���㱨��¼:";
	public static final String MONTH_DESC = "�±���㱨��¼:";
	
	/**
	 * ���������/�±������ݷ�д
	 */
	protected void processReport(Context ctx, boolean isWeek, OpReportEntryBaseInfo reportEntryInfo) throws EASBizException, BOSException {
		Date realEndDate = reportEntryInfo.getRealEndDate();
		Date intendEndDate = reportEntryInfo.getIntendEndDate();
		boolean isComplete = reportEntryInfo.isIsComplete();
		BigDecimal completePrecent = FDCNumberHelper.toBigDecimal(reportEntryInfo.getCompletePrecent());
		BigDecimal completeAmount = reportEntryInfo.getCompleteAmount();

		FDCScheduleTaskInfo task = reportEntryInfo.getRelateTask();
		task.setActualStartDate(reportEntryInfo.getPlanStartDate());

		Date realEndDate_ = task.getActualEndDate();
		Date intendEndDate_ = task.getIntendEndDate();
		BigDecimal completePrecent_ = task.getComplete();

		boolean taskChanged = false; // �����Ƿ��иı�
		boolean reportChanged = false; //�����Ƿ��иı�
		// �Ƿ�100%���
		boolean isComplete_ = FDCNumberHelper.ONE_HUNDRED.compareTo(task.getComplete()) == 0;

		// ���ݱȶ�
		if (!OpReportBaseHelper.dateCompare(realEndDate_, realEndDate)) {
			task.setActualEndDate(realEndDate);
			taskChanged = true;
			reportChanged = true;
		}

		if (!OpReportBaseHelper.dateCompare(intendEndDate_, intendEndDate)) {
			task.setIntendEndDate(intendEndDate);
			taskChanged = true;
			reportChanged = true;
		}
		if (!OpReportBaseHelper.bigDecimalCompare(completePrecent_, completePrecent)) {
			taskChanged = true;
			reportChanged = true;
		}
		if (completeAmount != null && completeAmount.compareTo(FDCNumberHelper.ZERO) > 0) {
			reportChanged = true;
			taskChanged = true;
			task.setWorkLoad(FDCNumberHelper.add(completeAmount, task.getWorkLoad()));
		}
		if (isComplete_ != isComplete) {
			reportChanged = true;
		}

		updateTaskReport(ctx, reportEntryInfo, task, taskChanged, reportChanged, isWeek);
	}

	/**
	 * @description ���ݱ仯���,������������
	 * @author �ź���
	 * @param reportChanged �����/�±����б仯
	 * @createDate 2011-12-29
	 */
	private void updateTaskReport(Context ctx, OpReportEntryBaseInfo baseInfo, FDCScheduleTaskInfo task, boolean taskChanged,
			boolean reportChanged, boolean isWeek) throws EASBizException, BOSException {
		if (reportChanged) {// ���������һ���ֶα仯,������һ���㱨��¼
			boolean isMainSchedule = false;
			if (baseInfo instanceof ProjectWeekReportEntryInfo) {
				ProjectWeekReportEntryInfo pwre = (ProjectWeekReportEntryInfo) baseInfo;
				isMainSchedule = (pwre.getHead().getProjectSpecial() == null);//ר��Ϊnullʱ����������
			} else if (baseInfo instanceof ProjectMonthReportEntryInfo) {
				ProjectMonthReportEntryInfo pmre = (ProjectMonthReportEntryInfo) baseInfo;
				isMainSchedule = (pmre.getHead().getProjectSpecial() == null);
			}
			
			addNewScheduleTaskReport(ctx, baseInfo, isWeek, isMainSchedule);
			// if (taskChanged) {// �����ȡ�������˵����ʵ��������ڱ仯,�͸�������Ľ���
			// FDCScheduleTaskFactory.getRemoteInstance().update(new ObjectStringPK(task.getId().toString()), task);
			// }
		}
	}

	/**
	 * @description ����һ�����Ȼ㱨
	 * @param isWeek true ���ܱ���fasleΪ�±�
	 * @author �ź���
	 */
	private void addNewScheduleTaskReport(Context ctx, OpReportEntryBaseInfo reportEntryInfo, boolean isWeek, boolean isMainSchedule)
			throws EASBizException, BOSException {

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule.islatestver", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("srcid", "select fsrcid from t_sch_fdcscheduletask where fid='"
						+ reportEntryInfo.getRelateTask().getId().toString() + "'", CompareType.INNER));
		if (isMainSchedule) {
			filter.getFilterItems().add(new FilterItemInfo("schedule.projectspecial is null"));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("schedule.projectspecial is not null"));
		}
		view.setFilter(filter);
		FDCScheduleTaskInfo relateTask = (FDCScheduleTaskInfo) FDCScheduleTaskFactory.getLocalInstance(ctx).getCollection(view).get(0);
		relateTask.setActualStartDate(reportEntryInfo.getPlanStartDate());

		// ��Ҫ�½��Ľ��Ȼ㱨
		ScheduleTaskProgressReportInfo stprInfo = new ScheduleTaskProgressReportInfo();
		stprInfo.setIsComplete(reportEntryInfo.isIsComplete());
		stprInfo.setIntendEndDate(reportEntryInfo.getIntendEndDate());
		stprInfo.setRealEndDate(reportEntryInfo.getRealEndDate());
		relateTask.setActualEndDate(reportEntryInfo.getRealEndDate());
		stprInfo.setTaskName(relateTask.getName());
		BigDecimal complete = new BigDecimal(reportEntryInfo.getCompletePrecent());
		BigDecimal workAmount = FDCHelper.toBigDecimal(reportEntryInfo.getCompleteAmount());
		if (complete != null) {
			relateTask.setComplete(complete);
		}
		if (workAmount != null) {
			relateTask.setWorkLoad(workAmount);
		}

		stprInfo.setCompleteAmount(workAmount);
		stprInfo.setCompletePrecent(complete);
		stprInfo.setReportor(reportEntryInfo.getRespPerson());
		stprInfo.setReportDate(new Date());
		stprInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		stprInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
		stprInfo.setTaskNumber(relateTask.getNumber());
		stprInfo.setPlanEndDate(relateTask.getPlanEnd());
		stprInfo.setRelateTask(relateTask);
		stprInfo.setSrcRelateTask(relateTask.getSrcID());
		stprInfo.setId(BOSUuid.create(stprInfo.getBOSType()));
		stprInfo.setRealStartDate(reportEntryInfo.getPlanStartDate());
		stprInfo.setReportor(ContextUtil.getCurrentUserInfo(ctx).getPerson());
		
		BigDecimal diffDays = ScheduleCalendarHelper.getEffectTimes(relateTask.getEnd(),relateTask.getActualEndDate() , relateTask.getCalendar());
		if(diffDays.compareTo(FDCHelper.ZERO)>0){
			relateTask.setDiffDays(diffDays.subtract(new BigDecimal(1)));
		}else if(diffDays.compareTo(FDCHelper.ZERO)<0){
			relateTask.setDiffDays(diffDays.add(new BigDecimal(1)));
		}else{
			relateTask.setDiffDays(diffDays);
		}
		String desc = reportEntryInfo.getDescription();
		if (desc == null) {
			desc = "";
		}
		if (isWeek) {
			desc = WEEK_DESC + desc;
		} else {
			desc = MONTH_DESC + desc;
		}
		if (desc.length() > 80) {
			desc = desc.substring(0, 79);
		}
		stprInfo.setDescription(desc);
		String id=ScheduleTaskProgressReportFactory.getLocalInstance(ctx).save(stprInfo).toString();
		
		FilterInfo f=new FilterInfo();
		f.getFilterItems().add(new FilterItemInfo("boid",relateTask.getId().toString()));
		EntityViewInfo v=new EntityViewInfo();
		v.setFilter(f);
		BoAttchAssoCollection attach=BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(v);
		if(attach.size()>0){
			BoAttchAssoInfo newAttach=(BoAttchAssoInfo) attach.get(0).clone();
			newAttach.setId(BOSUuid.create(new BoAttchAssoInfo().getBOSType()));
			newAttach.setBoID(id);
			BoAttchAssoFactory.getLocalInstance(ctx).save(newAttach);
		}
	}	

}