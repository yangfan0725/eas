package com.kingdee.eas.fdc.schedule.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.StatusTaskResult;
import com.kingdee.eas.fdc.schedule.TaskExecuteStatusEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

public class ScheduleRptFacadeControllerBean extends AbstractScheduleRptFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ScheduleRptFacadeControllerBean");
    /* ʱ��������ѯ
     * @see com.kingdee.eas.fdc.schedule.app.AbstractScheduleRptFacadeControllerBean#_getTimeFilerTasks(com.kingdee.bos.Context, java.util.Map)
     */
	protected RetValue _getFilterStatusAllInfos(Context ctx, ParamValue param) throws BOSException , EASBizException{
		if(param==null){
    		throw new NullPointerException("filter param can't be null!");
    	}
    	String prjId=param.getString("projectId");
    	String scheduleId=param.getString("scheduleId");
    	String adminDeptId=param.getString("adminDeptId");
    	String adminPersonId=param.getString("adminPersonId");
    	
    	EntityViewInfo view=new EntityViewInfo();
    	view.setFilter(new FilterInfo());
    	buildScheduleFilter(ctx, prjId, scheduleId, view);
    	
    	if(adminDeptId!=null){
    		view.getFilter().appendFilterItem("adminDept.id", adminDeptId);
    	}
    	
    	if(adminPersonId!=null){
    		view.getFilter().appendFilterItem("adminPerson.id", adminPersonId);
    	}
    	
    	view.getSelector().add("*");
    	view.getSelector().add("adminDept.id");
    	view.getSelector().add("adminDept.number");
    	view.getSelector().add("adminDept.name");
    	view.getSelector().add("adminPerson.id");
    	view.getSelector().add("adminPerson.number");
    	view.getSelector().add("adminPerson.name");
    	view.getSelector().add("schedule.id");
    	view.getSelector().add("schedule.number");
    	view.getSelector().add("schedule.name");
    	view.getSelector().add("schedule.project.id");
    	view.getSelector().add("schedule.project.longNumber");
    	view.getSelector().add("schedule.project.name");
    	view.getSelector().add("schedule.adminDept.id");
    	view.getSelector().add("schedule.adminDept.name");
    	view.getSelector().add("schedule.adminPerson.id");
    	view.getSelector().add("schedule.adminPerson.name");
    	view.getSelector().add("schedule.project.name");
    	view.getSorter().add(new SorterItemInfo("schedule.project.longNumber"));
    	view.getSorter().add(new SorterItemInfo("schedule.number"));

    	FDCScheduleCollection scheduleInfos = getFilterFDCScheduleCollection(ctx, view);
    	
    	//create result data
    	StatusTaskResult statusTaskResult=new StatusTaskResult(false); 
    	for(int i=0;i<scheduleInfos.size();i++){
    		FDCScheduleInfo info=scheduleInfos.get(i);
    		statusTaskResult.addItem(info);
    	}
		RetValue retValue=new RetValue();
    	retValue.put("StatusTaskResult", statusTaskResult);
    	return retValue; 
	}
	

	protected RetValue _getFilterStatusTasks(Context ctx, ParamValue param) throws BOSException, EASBizException {
		if(param==null){
    		throw new NullPointerException("filter param can't be null!");
    	}
    	String prjId=param.getString("projectId");
    	String scheduleId=param.getString("scheduleId");
    	String adminDeptId=param.getString("adminDeptId");
    	String adminPersonId=param.getString("adminPersonId");
    	TaskExecuteStatusEnum executeStatus=(TaskExecuteStatusEnum)param.get("TaskExecuteStatus");
    	
    	EntityViewInfo view=new EntityViewInfo();
    	view.setFilter(new FilterInfo());
    	buildScheduleFilter(ctx, prjId, scheduleId, view);
    	
    	if(adminDeptId!=null){
    		view.getFilter().appendFilterItem("adminDept.id", adminDeptId);
    	}
    	
    	if(adminPersonId!=null){
    		view.getFilter().appendFilterItem("adminPerson.id", adminPersonId);
    	}
    	
    	if(executeStatus!=null){
    		//filter by execute status
    		buildTaskStatusFilter(executeStatus, view);
    	}
    	
    	
    	view.getSelector().add("*");
    	view.getSelector().add("wbs.id");
    	view.getSelector().add("wbs.index");
    	view.getSelector().add("adminDept.id");
    	view.getSelector().add("adminDept.number");
    	view.getSelector().add("adminDept.name");
    	view.getSelector().add("adminPerson.id");
    	view.getSelector().add("adminPerson.number");
    	view.getSelector().add("adminPerson.name");
    	view.getSelector().add("schedule.id");
    	view.getSelector().add("schedule.number");
    	view.getSelector().add("schedule.name");
    	view.getSelector().add("schedule.project.id");
    	view.getSelector().add("schedule.project.longNumber");
    	view.getSelector().add("schedule.project.name");
    	view.getSelector().add("schedule.adminDept.id");
    	view.getSelector().add("schedule.adminDept.name");
    	view.getSelector().add("schedule.adminPerson.id");
    	view.getSelector().add("schedule.adminPerson.name");
    	view.getSelector().add("schedule.project.name");
    	view.getSorter().add(new SorterItemInfo("schedule.project.longNumber"));
    	view.getSorter().add(new SorterItemInfo("schedule.number"));
    	view.getSorter().add(new SorterItemInfo("number"));
    	view.getSorter().add(new SorterItemInfo("wbs.index"));

    	FDCScheduleCollection scheduleInfos = getFilterFDCScheduleCollection(ctx, view);
    	
    	//create result data
    	
//		retValue.put("FDCScheduleTaskCollection",scheduleInfos);
    	StatusTaskResult statusTaskResult=new StatusTaskResult(true); 
    	for(int i=0;i<scheduleInfos.size();i++){
    		FDCScheduleInfo info=scheduleInfos.get(i);
    		statusTaskResult.addItem(info);
    	}
		RetValue retValue=new RetValue();
    	retValue.put("StatusTaskResult", statusTaskResult);
    	
    	Set scheduleIds = new HashSet();
    	for(int i=0;i<scheduleInfos.size();i++){
    		scheduleIds.add(scheduleInfos.get(i).getId().toString());
    	}
    	retValue.put("workLoad", gettaskLoadPctMap(ctx, scheduleIds, null));
    	
    	return retValue; 
	
	}


	private void buildTaskStatusFilter(TaskExecuteStatusEnum executeStatus, EntityViewInfo view) throws BOSException {
		if(executeStatus==null||executeStatus==TaskExecuteStatusEnum.NONE){
			return;
		}
		/**
		 *����,�ض�ʱ��
		 */
		Date today=DateTimeUtils.truncateDate(new Date());
		/**
		 * ����,�ض�ʱ��
		 */
		Date tomorrow=DateTimeUtils.truncateDate(FDCDateHelper.getNextDay(today));
		if(executeStatus==TaskExecuteStatusEnum.NORMAL){
			/**
			*	����δ��ʼ����
			*	�������������
			*	�������������
			*/
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("start",tomorrow,CompareType.GREATER));
			filter.getFilterItems().add(new FilterItemInfo("complete",FDCHelper.ONE_HUNDRED));
			filter.setMaskString("#0 or #1");
			view.getFilter().mergeFilter(filter, "and");
		}
		if(executeStatus==TaskExecuteStatusEnum.EXECUTING){
			/**
			*	��������������
			*	
			*/
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("end",today,CompareType.GREATER));
			filter.getFilterItems().add(new FilterItemInfo("complete",FDCHelper.ZERO,CompareType.GREATER));
			filter.setMaskString("#0 and #1");
			view.getFilter().mergeFilter(filter, "and");
		}
		
		if(executeStatus==TaskExecuteStatusEnum.DELAY){
			/**
			*	����δ�������
			*	����δ��ʼ����
			*/
			
			FilterInfo filter=new FilterInfo();
			//����δ�������
			filter.getFilterItems().add(new FilterItemInfo("end",today,CompareType.LESS));
			filter.getFilterItems().add(new FilterItemInfo("complete",FDCHelper.ONE_HUNDRED,CompareType.LESS));
			//����δ��ʼ����
			filter.getFilterItems().add(new FilterItemInfo("start",today,CompareType.LESS));
			filter.getFilterItems().add(new FilterItemInfo("complete",FDCHelper.ZERO));
			filter.getFilterItems().add(new FilterItemInfo("complete",null,CompareType.EQUALS));
			
			filter.setMaskString("(#0 and #1) or (#2 and (#3 or #4))");
			view.getFilter().mergeFilter(filter, "and");
		}
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.schedule.app.AbstractScheduleRptFacadeControllerBean#_getTimeFilerTasks(com.kingdee.bos.Context, com.kingdee.eas.fdc.basedata.ParamValue)
	 */
	protected RetValue _getTimeFilerTasks(Context ctx, ParamValue param) throws BOSException, EASBizException {

		if(param==null){
    		throw new NullPointerException("filter param can't be null!");
    	}
		RetValue retValue=new RetValue();
    	String prjId=param.getString("projectId");
    	String scheduleId=param.getString("scheduleId");
    	String adminDeptId=param.getString("adminDeptId");
    	String adminPersonId=param.getString("adminPersonId");
    	Date startDate=param.getDate("startDate");
    	Date endDate=param.getDate("endDate");
    	
    	EntityViewInfo view=new EntityViewInfo();
    	view.setFilter(new FilterInfo());
    	buildScheduleFilter(ctx, prjId, scheduleId, view);
    	
    	if(adminDeptId!=null){
    		view.getFilter().appendFilterItem("adminDept.id", adminDeptId);
    	}
    	
    	if(adminPersonId!=null){
    		view.getFilter().appendFilterItem("adminPerson.id", adminPersonId);
    	}
    	
    	if(startDate==null){
    		startDate=FDCDateHelper.getFirstYearDate(1900); //����һ����С����
    	}
    	
    	if(endDate==null){
    		endDate=FDCDateHelper.getLastYearDate(2900); //����һ��������
    	}
    	//�������µ�����ֻҪ��ѯ�����ڸ�����������н����ȡ����,�������������෴��(����Ŀ�ʼʱ��>��ѯ�Ľ���ʱ�� ||����Ľ���ʱ��<��ѯ�Ŀ�ʼʱ��) ���ǲ�ѯ�Ľ��
    	// by sxhong
//    	view.getFilter().getFilterItems().add(new FilterItemInfo("start",startDate,CompareType.GREATER_EQUALS));
//    	view.getFilter().getFilterItems().add(new FilterItemInfo("end",endDate,CompareType.LESS_EQUALS));
    	String timeFilterSql="select fid from T_SCH_FDCScheduleTask where fstart>#endDate# or fend<#startDate#" ;
    	timeFilterSql=timeFilterSql.replaceAll("#startDate#", "{"+FDCDateHelper.formatDate2(startDate)+"}");
    	timeFilterSql=timeFilterSql.replaceAll("#endDate#", "{"+FDCDateHelper.formatDate2(endDate)+"}");
    	view.getFilter().getFilterItems().add(new FilterItemInfo("id",timeFilterSql,CompareType.NOTINNER));
    	
    	view.getSelector().add("*");
    	view.getSelector().add("bizType.id");
    	view.getSelector().add("bizType.name");
    	view.getSelector().add("wbs.id");
    	view.getSelector().add("wbs.index");
    	view.getSelector().add("adminDept.id");
    	view.getSelector().add("adminDept.number");
    	view.getSelector().add("adminDept.name");
    	view.getSelector().add("adminPerson.id");
    	view.getSelector().add("adminPerson.number");
    	view.getSelector().add("adminPerson.name");
    	view.getSelector().add("schedule.id");
    	view.getSelector().add("schedule.number");
    	view.getSelector().add("schedule.name");
    	view.getSelector().add("schedule.adminDept.id");
    	view.getSelector().add("schedule.adminDept.name");
    	view.getSelector().add("schedule.adminPerson.id");
    	view.getSelector().add("schedule.adminPerson.name");
    	view.getSelector().add("schedule.project.name");
    	view.getSorter().add(new SorterItemInfo("schedule.project.longNumber"));
    	view.getSorter().add(new SorterItemInfo("schedule.number"));
    	view.getSorter().add(new SorterItemInfo("number"));
    	view.getSorter().add(new SorterItemInfo("wbs.index"));
    	FDCScheduleCollection schCol = getFilterFDCScheduleCollection(ctx, view);
    	
    	retValue.put("FDCScheduleCollection",schCol);
    	
    	Set scheduleIds = new HashSet();
    	for(int i=0;i<schCol.size();i++){
    		scheduleIds.add(schCol.get(i).getId().toString());
    	}
    	retValue.put("workLoad", gettaskLoadPctMap(ctx, scheduleIds, null));
    	return retValue; 
	}
	
	/**
	 * ���ȼ��Ĺ�������
	 * @param ctx
	 * @param prjId
	 * @param scheduleId
	 * @param view
	 */
	private void buildScheduleFilter(Context ctx, String prjId, String scheduleId, EntityViewInfo view) {
		view.getFilter().appendFilterItem("schedule.isLatestVer", Boolean.TRUE);
		if(scheduleId!=null){
    		view.getFilter().appendFilterItem("schedule.id", scheduleId);
    	}else{
    		if(prjId!=null){
    			view.getFilter().appendFilterItem("schedule.project.id", prjId);
    		}else{
    			//ȡ��ǰ������֯�ڵĹ�����Ŀ
    			OrgUnitInfo currentFIUnit = ContextUtil.getCurrentFIUnit(ctx);
    			if(currentFIUnit==null){
    				//ȡ������ǰ������֯�Ļ�ֻ�ܿ���ǰ��֯���Ƿ��в�����֯��
    				currentFIUnit=ContextUtil.getCurrentOrgUnit(ctx);
    			}
    			StringBuffer sql=new StringBuffer();
    			sql.append("select fid from T_fdc_curProject where fisEnabled=1 and ffullorgunit in ( " );
    			sql.append("   select company.fid from t_org_baseunit parent ,T_org_company company ");
    			sql.append("	where parent.fid='"+currentFIUnit.getId().toString()+"'");
    			sql.append("      and charindex(parent.flongnumber||'!',company.flongnumber||'!')=1  and company.fisbizunit=1 ");
    			sql.append(") 	");
    			
    			view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.project.id",sql.toString(),CompareType.INNER));
    		}
    	}
	}
	
	/**
	 * 
	 * @param ctx
	 * @param taskView	����Ĺ�������
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private FDCScheduleCollection getFilterFDCScheduleCollection(Context ctx,EntityViewInfo taskView) throws BOSException,EASBizException{
    	FDCScheduleTaskCollection tasks = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(taskView);
    	//��������FDCScheduleCollection
    	FDCScheduleCollection scheduleInfos=new FDCScheduleCollection();
    	Map helperMap=new HashMap();
    	for(Iterator iter=tasks.iterator();iter.hasNext();){
    		FDCScheduleTaskInfo task=(FDCScheduleTaskInfo)iter.next();
    		FDCScheduleInfo info=(FDCScheduleInfo)helperMap.get(task.getSchedule().getId());
    		if(info==null){
    			info=task.getSchedule();
    			info.getTaskEntrys().clear();
    			scheduleInfos.add(info);
    			helperMap.put(info.getId(),info);
    		}
    		task.setSchedule(info);
    		info.getTaskEntrys().add(task);
    	}
    	return scheduleInfos;
	}
	/**
	 * ��������ID��WBSID��ȡ�����깤���������깤�ٷֱ�
	 * Ŀǰʹ��in�����ͻ��ֳ�������������Ƚ϶࣬Ӧ�ÿ����������Ż���SQL����
	 * ��ʱ��Ϊ��schedule��id����
	 * @param ctx
	 * @param taskId
	 * @param wbsId
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws SQLException 
	 */
	private Map gettaskLoadPctMap(Context ctx,Set scheduleIds,String wbsId) throws EASBizException, BOSException{
		Map returnMap = new HashMap();
		Map paramMap = new HashMap();
		paramMap = FDCUtils.getDefaultFDCParam(ctx,ContextUtil.getCurrentOrgUnit(ctx).getId().toString());
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// ���������������
		boolean isBasedOnTask = false;
		// ���ں�ͬ�������
		boolean isBasedOnContract = false;
		// Ĭ�Ϲ��������ʽ
		boolean isDefaultMethod = false;
		if (paramMap.get(FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT) != null) {
			isBasedOnContract = (Boolean.valueOf(paramMap.get(FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT).toString()).booleanValue());
		}
		if (paramMap.get(FDCConstants.FDCSCH_PARAM_BASEONTASK) != null) {
			isBasedOnTask = Boolean.valueOf(paramMap.get(FDCConstants.FDCSCH_PARAM_BASEONTASK).toString()).booleanValue();
		}
		if((!isBasedOnContract) && (!isBasedOnTask)){
			isDefaultMethod = true;
		}
//		ȡProjectFillBillEntry������
		if(isBasedOnContract && scheduleIds.size()>0){
			builder.clear();
			builder.appendSql("select ent.FTaskId as task,sum(ent.FQty) as qty,sum(ent.FPercent) as pct from T_FPM_ProjectFillBillEntry ent" +
					"	inner join T_FPM_ProjectFillBill head on head.fid=ent.fbillid " +
					"	inner join T_SCH_FDCScheduleTask tk on tk.fid=ent.FTaskID " +
					"	inner join T_SCH_FDCSchedule sch on tk.fscheduleid=sch.fid" +
					"	where ");
			builder.appendParam("sch.FID", scheduleIds.toArray());
			builder.appendSql("and");
			builder.appendParam("head.fstate", FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql("	group by ent.FTaskId");
			IRowSet rowSet = builder.executeQuery();
			try {
				while(rowSet.next()){
					String taskId = rowSet.getString("task");
					BigDecimal qty = rowSet.getBigDecimal("qty");
					BigDecimal pct = FDCHelper.divide(rowSet.getBigDecimal("pct"), FDCHelper.ONE_HUNDRED);
					ArrayList array = new ArrayList();
					array.add(0,qty);
					array.add(1,pct);
					returnMap.put(taskId, array);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		ȡWorkAmountEntry
		if(isBasedOnTask && scheduleIds.size() > 0){
			builder.clear();
			builder.appendSql("select ent.FTaskId as task,sum(ent.FconfirmAmount) as qty,sum(ent.FconfirmPercent) as pct from T_SCH_WorkAmountEntry ent" +
					"	inner join T_SCH_WorkAmountBill head on ent.fparentid=head.fid " +
					"	inner join T_SCH_FDCScheduleTask tk on tk.fid=ent.FTaskId" +
					"	inner join T_SCH_FDCSchedule sch on tk.fscheduleid=sch.fid" +
					"	where ");
			builder.appendParam("sch.fid", scheduleIds.toArray());
			builder.appendSql("	and");
			builder.appendParam("head.fstate", FDCBillStateEnum.AUDITTED_VALUE);
			builder.appendSql("	group by ent.FTaskId");
			IRowSet rowSet = builder.executeQuery();
			try {
				while(rowSet.next()){
					String taskId = rowSet.getString("task");
					BigDecimal qty = rowSet.getBigDecimal("qty");
					BigDecimal pct = FDCHelper.divide(rowSet.getBigDecimal("pct"), FDCHelper.ONE_HUNDRED,4,BigDecimal.ROUND_HALF_UP);
					ArrayList array = new ArrayList();
					array.add(0,qty);
					array.add(1,pct);
					returnMap.put(taskId, array);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		ȡTaskLoadEntry
		if(isDefaultMethod && scheduleIds.size()>0){
			builder.clear();
			builder.appendSql("select tk.fid as task,sum(ent.Fload) as qty,sum(ent.Fpercent) as pct from T_SCH_TaskLoadEntry ent" +
			"	inner join t_sch_fdcscheduletask tk on tk.fwbsid=ent.fwbsid" +
			"	inner join t_sch_fdcschedule sch on tk.fscheduleid=sch.fid" +
			"	where ent.FIsConfirm=1 and ");
			builder.appendParam("sch.fid", scheduleIds.toArray());
			builder.appendSql("	group by tk.fid");
			IRowSet rowSet = builder.executeQuery();
			try {
				while(rowSet.next()){
					String taskId = rowSet.getString("task");
					BigDecimal qty = rowSet.getBigDecimal("qty");
					BigDecimal pct = FDCHelper.divide(rowSet.getBigDecimal("pct"), FDCHelper.ONE_HUNDRED);
					ArrayList array = new ArrayList();
					array.add(0,qty);
					array.add(1,pct);
					returnMap.put(taskId, array);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returnMap;
	}
}