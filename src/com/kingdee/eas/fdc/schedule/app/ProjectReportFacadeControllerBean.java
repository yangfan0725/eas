package com.kingdee.eas.fdc.schedule.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

public class ProjectReportFacadeControllerBean extends AbstractProjectReportFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ProjectReportFacadeControllerBean");

	protected Map _getPeriodTask(Context ctx, Map param) throws BOSException, EASBizException {
		/*
		 * TODO根据传入的参数，从相应的专项，或者主项查询所需要的结果，返回客户端
		 */
		Map entryMap = new HashMap();
		boolean isMonthReport = false;
		if(param.get("isMonthReport")!= null ){
			isMonthReport = Boolean.valueOf(param.get("isMonthReport")+"");
		}
		
		// 用于存储本周任务
		List thisWeek = new ArrayList();
		// 用于存储下周任务
		List nextWeek = new ArrayList();
		
		List<String> idList = new ArrayList<String>();

		String stringPk = (String) param.get("stringPk");
		Date starDate = (Date) param.get("starDate");
		Date endDate = (Date) param.get("endDate");

		Date nextFist = (Date) param.get("nextFist");
		Date nextLast = (Date) param.get("nextLast");
		UserInfo user=(UserInfo) param.get("creator");
		ProjectSpecialInfo projectSpecialInfo = (ProjectSpecialInfo) param.get("projectSpecial");

		// 用于存储本月任务
		List thisMonth = new ArrayList();
		// 用于存储下月任务
		List nextMonth = new ArrayList();
		// if (stringPk != null && !"".equals(stringPk)) {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);

			builder
					.appendSql("select a.fsimpleName,a.ftaskType,a.fid,a.fname_l2,a.fnumber,a.factualstartdate,a.fsrcid,a.fstart,a.fend,a.fintendEndDate,a.fcomplete,a.FActualEndDate,a.fdescription_l2,a.FAdminPersonID,"
						+ "person.fname_l2 pfname,person.fnumber pfnumber,a.FAdminDeptID,"
						+ "org.fname_l2 orgfname,org.fnumber orgfnumber,quanlity.fid quanlityId,quanlity.fname_l2 quanlityName,quanlity.fnumber quanlityNumber,planPerson.fid planPersonId,planPerson.fname_l2 planPersonName,planPerson.fnumber planPersonNumber "
						+ " from T_SCH_FDCScheduleTask a "
						+ " right outer join T_SCH_FDCSchedule d  on a.fscheduleid = d.fid "
						+ " left outer join t_bd_person person on a.FAdminPersonID = person.fid  "
						+ " left outer join t_pm_user u on u.FpersonID = person.fid  "
						+ " left outer join t_org_baseunit org on a.fadmindeptid = org.fid "
						+ " left outer join t_bd_person quanlity  on a.fqualityevaluatepersonid = quanlity.fid "
						+ " left outer join t_bd_person planPerson on a.fplanevaluatepersonid = planPerson.fid ");

			builder.appendSql(" where ((a.fend<= ? and isnull(a.fcomplete,0)<100  )");//结束日期小于指定开始日期但完成率小于100%

			if(isMonthReport){
				builder.appendSql(" or  ((a.fend>=? and a.fend<=?) ))");//1、开始日期小于指定开始日期并且开始日期小于指定结束日期 2、结束日期日期小于指定开始日期并且结束日期小于指定结束日期
			}else{
				builder.appendSql(" or  ((a.fstart>=? and a.fstart<=?) or (a.fend>=? and a.fend<=?) )");//1、开始日期小于指定开始日期并且开始日期小于指定结束日期 2、结束日期日期小于指定开始日期并且结束日期小于指定结束日期
				builder.appendSql(" or (a.fstart<=? and a.fend>?) ");//开始日期和结束日期都在指定时间段内
				builder.appendSql(" or (a.FActualEndDate>=? and a.FActualEndDate<=?)");//实际完成日期在指定时间段内
				builder.appendSql(" or (a.fstart>=? and (a.FActualStartDate>=? and a.FActualStartDate<=?)))");//提前完成
			}
			builder.appendSql(" and a.fcomplete<100");

			if (!StringUtils.isEmpty(stringPk))
				builder.appendSql(" and a.FScheduleID = '" + stringPk + "'"); 
			if (projectSpecialInfo == null) {
				builder.appendSql("and (d.fprojectSpecialid is null or d.fprojectSpecialid='') ");
			} else {
				builder.appendSql("and d.fprojectSpecialid='" + projectSpecialInfo.getId().toString().trim() + "'");
			}
			builder.appendSql(" and u.fid='" + user.getId().toString().trim() + "'");
			// 只查询出叶子节点任务 modify by duhongming
		builder.appendSql(" and a.fisleaf=1 and d.fislatestver =1 ");
			builder.appendSql(" order by a.fseq ");
			// 参数设置
			builder.addParam(starDate);
			
			if(isMonthReport){
				builder.addParam(starDate);
				builder.addParam(endDate);
				
			}else{
				builder.addParam(starDate);
				builder.addParam(endDate);
				builder.addParam(starDate);
				builder.addParam(endDate);
				builder.addParam(starDate);
				builder.addParam(endDate);
				
				builder.addParam(starDate);
				builder.addParam(endDate);
				
				builder.addParam(endDate);
				builder.addParam(starDate);
				builder.addParam(endDate);
			}


			
			// builder.addParam(projectSpecialID);
			IRowSet rowSet = builder.executeQuery();
			try {
				while (rowSet.next()) {
					ProjectMonthReportEntryInfo monthEntryInfo = new ProjectMonthReportEntryInfo();
					ProjectWeekReportEntryInfo entryInfo = new ProjectWeekReportEntryInfo();
					String taskId = rowSet.getString("fid");
					idList.add(taskId);
					Date actualStartDate = rowSet.getDate("factualstartdate");
				String srcID = rowSet.getString("fsrcid");
				String taskNumber = rowSet.getString("fnumber");
				String taskName = rowSet.getString("fname_l2");
				Date planEndDate = rowSet.getDate("fend");
				Date realendDate = rowSet.getDate("FActualEndDate");
				Object completePrecent = rowSet.getObject("fcomplete");
				Date intendEndDate = rowSet.getDate("fintendEndDate");
				String description = rowSet.getString("fdescription_l2");
				String personId = rowSet.getString("FAdminPersonID");
				String deptId = rowSet.getString("FAdminDeptID");
				String taskType=rowSet.getString("ftaskType");
					FDCScheduleTaskInfo task = new FDCScheduleTaskInfo();
					task.setId(BOSUuid.read(taskId));
					task.setName(taskName);
					task.setNumber(taskNumber);
					task.setSrcID(srcID);
					task.setActualStartDate(actualStartDate);
					task.setStart(rowSet.getDate("fstart"));
					task.setIntendEndDate(intendEndDate);
					task.setEnd(planEndDate);
					task.setComplete(rowSet.getBigDecimal("fcomplete"));
					task.setTaskType(RESchTaskTypeEnum.getEnum(taskType));
					task.setSimpleName(rowSet.getString("fsimpleName"));
					entryInfo.setRelateTask(task);
					monthEntryInfo.setRelateTask(task);

					PersonInfo personInfo = new PersonInfo();
					if (!StringUtils.isEmpty(personId)) {
						personInfo.setId(BOSUuid.read(personId));
						String personName = rowSet.getString("pfname");
						personInfo.setName(personName);
						String personNumber = rowSet.getString("pfnumber");
						personInfo.setNumber(personNumber);
						entryInfo.setRespPerson(personInfo);
						monthEntryInfo.setRespPerson(personInfo);
						
					}
					
					personId = rowSet.getString("quanlityId");
					if(!StringUtils.isEmpty(personId)){
						personInfo = new PersonInfo();
						String personName = rowSet.getString("quanlityName");
						personInfo.setName(personName);
						String personNumber = rowSet.getString("quanlityNumber");
						personInfo.setNumber(personNumber);
						task.setQualityEvaluatePerson(personInfo);
						entryInfo.setQuanlityPerson(personName);
						monthEntryInfo.setQuanlityPerson(personName);
					}
					
					personId = rowSet.getString("planPersonId");
					if(!StringUtils.isEmpty(personId)){
						personInfo = new PersonInfo();
						String personName = rowSet.getString("planPersonName");
						personInfo.setName(personName);
						String personNumber = rowSet.getString("planPersonNumber");
						personInfo.setNumber(personNumber);
						task.setPlanEvaluatePerson(personInfo);
						entryInfo.setPlanPerson(personName);
						monthEntryInfo.setPlanPerson(personName);
					}
					
					
					
					// 本周分录
					entryInfo.setTaskName(taskName);
					entryInfo.setPlanEndDate(planEndDate);
					
					/* modified by zhaoqin for R140812-0088 on 2014/09/24 */
					//entryInfo.setIsNext(true);
					entryInfo.setIsNext(false);
					
					entryInfo.setRealEndDate(realendDate);
					entryInfo.setIntendEndDate(intendEndDate);
					if (completePrecent != null && !"".equals(completePrecent)) {
						if (completePrecent instanceof BigDecimal) {
							entryInfo.setCompletePrecent(((BigDecimal) completePrecent).intValue());
						}

					}
					if (deptId != null) {
						FullOrgUnitInfo fullOrgUnitInfo = new FullOrgUnitInfo();
						fullOrgUnitInfo.setId(BOSUuid.read(deptId));
						String deptName = rowSet.getString("orgfname");
						fullOrgUnitInfo.setName(deptName);
						String deptNumber = rowSet.getString("orgfnumber");
						fullOrgUnitInfo.setNumber(deptNumber);
						entryInfo.setRespDept(fullOrgUnitInfo);
						monthEntryInfo.setRespDept(fullOrgUnitInfo);
					}

					
					entryInfo.setDescription(description);

					// 本月分录
					monthEntryInfo.setTaskName(taskName);
					monthEntryInfo.setPlanEndDate(planEndDate);
					
					/* modified by zhaoqin for R140812-0088 on 2014/09/24 */
					//monthEntryInfo.setIsNext(true);
					monthEntryInfo.setIsNext(false);
					
					monthEntryInfo.setRealEndDate(realendDate);
					monthEntryInfo.setIntendEndDate(intendEndDate);
					if (completePrecent != null && !"".equals(completePrecent)) {
						if (completePrecent instanceof BigDecimal) {
							monthEntryInfo.setCompletePrecent(((BigDecimal) completePrecent).intValue());
						}

					}

					//monthEntryInfo.setRespPerson(personInfo);
					monthEntryInfo.setDescription(description);

					thisWeek.add(entryInfo);
					thisMonth.add(monthEntryInfo);
					// coreBaseCollection.add(entryInfo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 查找下周任务
			FDCSQLBuilder nextBuilder = new FDCSQLBuilder(ctx);

			nextBuilder
				.appendSql(" select  a.fsrcid, a.fid,a.fnumber,a.FActualEndDate,a.FActualStartDate,a.fname_l2,a.fstart,a.fend ,a.FAdminPersonID,person.fname_l2 pfname,person.fnumber pfnumber,a.FAdminDeptID,org.fname_l2 orgfname,org.fnumber orgfnumber,quanlity.fid quanlityId,quanlity.fname_l2 quanlityName,quanlity.fnumber quanlityNumber,planPerson.fid planPersonId,planPerson.fname_l2 planPersonName,planPerson.fnumber planPersonNumber  "
						+ "from T_SCH_FDCScheduleTask a right outer join T_SCH_FDCSchedule d on a.fscheduleid = d.fid left outer join t_bd_person person on person.fid = a.fadminpersonid left outer join t_org_baseunit org on org.fid = a.fadmindeptid "
						+ " left outer join t_bd_person quanlity  on a.fqualityevaluatepersonid = quanlity.fid "
						+ " left outer join t_bd_person planPerson on a.fplanevaluatepersonid = planPerson.fid ");
			nextBuilder
				.appendSql(" where ((((a.fstart>=? and a.fstart<=?) or(a.fend>=? and a.fend<=?)) and (a.fcomplete<100 or a.fcomplete is null  ))");
		nextBuilder.appendSql(" or  (a.fstart<= ? and (a.fend>=? and (a.fcomplete<100 or a.fcomplete is null ))))");
			nextBuilder.appendSql(" and a.FScheduleID = '" + stringPk + "'");
			// nextBuilder.appendSql(
			// " and a.FAdminPersonID=b.fid and a.FAdminDeptID=c.fid ");
			if (projectSpecialInfo == null) {
				nextBuilder.appendSql(" and (d.fprojectSpecialid is null or d.fprojectSpecialid='') ");
			} else {
				nextBuilder.appendSql(" and d.fprojectSpecialid='" + projectSpecialInfo.getId().toString().trim() + "'");
			}
			// 只查询出叶子节点任务 modify by duhongming
		nextBuilder.appendSql(" and a.fisleaf=1 and d.fislatestver =1 ");
			nextBuilder.appendSql(" order by a.fend ");

			// 参数设置
			nextBuilder.addParam(nextFist);
			nextBuilder.addParam(nextLast);
			nextBuilder.addParam(nextFist);
			nextBuilder.addParam(nextLast);
			nextBuilder.addParam(nextFist);
			nextBuilder.addParam(nextLast);
			// builder.addParam(stringPk);

			IRowSet nextSet = nextBuilder.executeQuery();
			try {
				while (nextSet.next()) {
					ProjectWeekReportEntryInfo entryInfo = new ProjectWeekReportEntryInfo();
					ProjectMonthReportEntryInfo monthEntryInfo = new ProjectMonthReportEntryInfo();
					String id = nextSet.getString("fid");
					String srcId = nextSet.getString("fsrcid");
					String taskName = nextSet.getString("fname_l2");
					String taskNumber = nextSet.getString("fnumber");
					Date planEndDate = nextSet.getDate("fend");
					Date planstartDate = nextSet.getDate("fstart");
					String personId = nextSet.getString("FAdminPersonID");
					String deptId = nextSet.getString("FAdminDeptID");
					Date actualStartDate = nextSet.getDate("FActualStartDate");
					Date actualEndDate = nextSet.getDate("FActualEndDate");
				
					FDCScheduleTaskInfo task = new FDCScheduleTaskInfo();
					task.setId(BOSUuid.read(id));
					task.setSrcID(srcId);
					task.setName(taskName);
					task.setNumber(taskNumber);
					task.setActualStartDate(actualStartDate);
					task.setActualEndDate(actualEndDate);
					task.setStart(nextSet.getDate("fstart"));
					task.setEnd(planstartDate);
					
					entryInfo.setRelateTask(task);
					monthEntryInfo.setRelateTask(task);
					PersonInfo personInfo = null;
					if (!StringUtils.isEmpty(personId)) {
						personInfo = new PersonInfo();
						personInfo.setId(BOSUuid.read(personId));
						String personNumber = nextSet.getString("pfnumber");
						personInfo.setNumber(personNumber);
						String personName = nextSet.getString("pfname");
						personInfo.setName(personName);
						entryInfo.setRespPerson(personInfo);
						monthEntryInfo.setRespPerson(personInfo);
						
					}
					
					
					personId = nextSet.getString("quanlityId");
					if(!StringUtils.isEmpty(personId)){
						personInfo = new PersonInfo();
						String personName = nextSet.getString("quanlityName");
						personInfo.setName(personName);
						String personNumber = nextSet.getString("quanlityNumber");
						personInfo.setNumber(personNumber);
						task.setQualityEvaluatePerson(personInfo);
						entryInfo.setQuanlityPerson(personName);
						monthEntryInfo.setQuanlityPerson(personName);
					}
					
					personId = nextSet.getString("planPersonId");
					if(!StringUtils.isEmpty(personId)){
						personInfo = new PersonInfo();
						String personName = nextSet.getString("planPersonName");
						personInfo.setName(personName);
						String personNumber = nextSet.getString("planPersonNumber");
						personInfo.setNumber(personNumber);
						task.setPlanEvaluatePerson(personInfo);
						entryInfo.setPlanPerson(personName);
						monthEntryInfo.setPlanPerson(personName);
					}
					
					
					
					if (deptId != null) {
						FullOrgUnitInfo fullOrgUnitInfo = new FullOrgUnitInfo();
						fullOrgUnitInfo.setId(BOSUuid.read(deptId));
						String deptName = nextSet.getString("orgfname");
						fullOrgUnitInfo.setName(deptName);
						String deptNumber = nextSet.getString("orgfnumber");
						fullOrgUnitInfo.setNumber(deptNumber);
						entryInfo.setRespDept(fullOrgUnitInfo);
						monthEntryInfo.setRespDept(fullOrgUnitInfo);
					}
					// 下周
					entryInfo.setRespPerson(personInfo);
					entryInfo.setTaskName(taskName);
					entryInfo.setPlanEndDate(planEndDate);
					
					/* modified by zhaoqin for R140812-0088 on 2014/09/24 */
					//entryInfo.setIsNext(false);
					entryInfo.setIsNext(true);
					
					entryInfo.setPlanStartDate(planstartDate);

					// 下月任务分录
					monthEntryInfo.setRespPerson(personInfo);
					monthEntryInfo.setTaskName(taskName);
					monthEntryInfo.setPlanEndDate(planEndDate);
					
					/* modified by zhaoqin for R140812-0088 on 2014/09/24 */
					//monthEntryInfo.setIsNext(false);
					monthEntryInfo.setIsNext(true);
					
					monthEntryInfo.setPlanStartDate(planstartDate);

					nextWeek.add(entryInfo);
					nextMonth.add(monthEntryInfo);
					// nextCollection.add(entryInfo);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			Map<String, ScheduleTaskProgressReportInfo> reportMap = new HashMap<String, ScheduleTaskProgressReportInfo>();
		if (idList != null && !idList.isEmpty()) {
			builder.clear();
			builder.appendSql("select FRelateTaskID,FDescription from t_sch_schtaskprogressreport where ");
			builder.appendParam("FRelateTaskID", idList.toArray(), "VARCHAR");
			builder.appendSql("order by FReportDate Desc");
			IRowSet rs = builder.executeQuery();
			try {
				ScheduleTaskProgressReportInfo reportInfo = null;
				String relateTaskid = null;
				while (rs.next()) {
					relateTaskid = rs.getString("FRelateTaskID");
					if (!reportMap.containsKey(relateTaskid)) {
						reportInfo = new ScheduleTaskProgressReportInfo();
						if (rs.getString("FDescription") != null && !rs.getString("FDescription").equals("null"))
							reportInfo.setDescription(rs.getString("FDescription"));
						reportMap.put(relateTaskid, reportInfo);
					}

				}
			} catch (SQLException e) {
				throw new BOSException(e.getMessage(), e.getCause());
			}

		}
		
		/***
		 * 获取所有评价信息
		 */
		
		
		Map<String,Object[]> evaluationMap = new HashMap<String, Object[]>();
		
		String sql = " /*dialect*/ select fcreatetime, fsrcrelatetask, fevaluationtype, fevaluationresult_l2 "+
		  " from (select eb.fcreatetime, "+
		  "             eb.fsrcrelatetask,"+
		  "             eb.fevaluationtype,"+
		  "             ev.fevaluationresult_l2,"+
		  "             row_number() over(partition by eb.fevaluationtype, eb.fsrcrelatetask order by eb.flastupdatetime desc) rn "+
		  "        from t_sch_taskevaluationbill eb "+
		  "        inner join t_sch_fdcscheduletask task "+
		  "          on task.fsrcid = eb.fsrcrelatetask "+
		  "        inner join t_sch_taskevaluation ev "+
		  "          on ev.fevaluationtype = eb.fevaluationtype "+
		  "         and ev.fid = eb.fevaluationresult "+
		  "         and task.fscheduleid ='"+stringPk+"') "+
		  "  where rn = 1 ";

		
		 builder.clear();
		 builder.appendSql(sql);
		 rowSet = builder.executeQuery();
		 String key = null;
		 
		 try {
			while(rowSet.next()){
				key = rowSet.getString("fsrcrelatetask")+"_"+rowSet.getString("fevaluationtype");
				evaluationMap.put(key, new Object[]{rowSet.getDate("fcreatetime"),rowSet.getString("fevaluationresult_l2")});
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("查询评价信息失败，sql:"+builder.getTestSql());
			e.printStackTrace();
			
		}
		
		
		

		if (!reportMap.isEmpty()) {
			ScheduleTaskProgressReportInfo reportInfo = null;
			 key = null;
			for (int i = 0; i < thisWeek.size(); i++) {
				ProjectWeekReportEntryInfo entryInfo = (ProjectWeekReportEntryInfo) thisWeek.get(i);
				key = entryInfo.getRelateTask().getId().toString();
				if (reportMap.containsKey(key)) {
					reportInfo = reportMap.get(key);
					entryInfo.setDescription(reportInfo.getDescription());
				}
				key = entryInfo.getRelateTask().getSrcID()+"_"+"1SCHEDULE";
				if(evaluationMap.containsKey(key)){
					entryInfo.setPEvaluationDate((Date) evaluationMap.get(key)[0]);
					entryInfo.setPEvaluationResult(evaluationMap.get(key)[1]+"");
				}
				
				key = entryInfo.getRelateTask().getSrcID()+"_"+"2QUALITY";
				if(evaluationMap.containsKey(key)){
					entryInfo.setQEvaluationDate((Date) evaluationMap.get(key)[0]);
					entryInfo.setQEvaluationResult(evaluationMap.get(key)[1]+"");
				}
			}
			for (int i = 0; i < thisMonth.size(); i++) {
				ProjectMonthReportEntryInfo entryInfo = (ProjectMonthReportEntryInfo) thisMonth.get(i);
				key = entryInfo.getRelateTask().getId().toString();
				if (reportMap.containsKey(key)) {
					reportInfo = reportMap.get(key);
					entryInfo.setDescription(reportInfo.getDescription());
				}
				
				key = entryInfo.getRelateTask().getSrcID()+"_"+"1SCHEDULE";
				if(evaluationMap.containsKey(key)){
					entryInfo.setPEvaluationDate((Date) evaluationMap.get(key)[0]);
					entryInfo.setPEvaluationResult(evaluationMap.get(key)[1]+"");
				}
				
				key = entryInfo.getRelateTask().getSrcID()+"_"+"2QUALITY";
				if(evaluationMap.containsKey(key)){
					entryInfo.setQEvaluationDate((Date) evaluationMap.get(key)[0]);
					entryInfo.setQEvaluationResult(evaluationMap.get(key)[1]+"");
				}
			}

			reportMap.clear();
		}
			// 将任务放入到Map集合中
			entryMap.put("thisWeek", thisWeek);
			entryMap.put("nextWeek", nextWeek);
			entryMap.put("thisMonth", thisMonth);
			entryMap.put("nextMonth", nextMonth);
		// } else {
		// throw new EASBizException(new NumericExceptionSubItem("1001",
		// "该工程项目下本周或下周无任务"));
		// }

		return entryMap;
	}
}