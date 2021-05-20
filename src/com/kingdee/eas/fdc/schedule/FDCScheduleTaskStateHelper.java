/*
 * @(#)FDCScheduleTaskStateHelper.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.schedule;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.util.FdcCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcEntityViewUtil;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.basedata.util.FdcObjectValueUtil;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;
/***
 * 
 * ����:�����ڶദ��Ҫ��������״̬���£����Գ�ȡ�˷�����
 * @author yuanjun_lan  date:2011-10-21
 * @version EAS6.1
 * 
 */
public class FDCScheduleTaskStateHelper {
	
	private static Logger logger = Logger.getLogger(FDCScheduleTaskStateHelper.class);
	/**
	 * 
	 * ��������ȡ��Ӧ����״̬
	 * @param ctx
	 * @param schedule
	 * @throws BOSException
	 * @throws EASBizException
	 * �����ˣ�yuanjun_lan
	 * ����ʱ�䣺2011-10-21
	 * @throws SQLException 
	 * 
	 */
	public static void putState(Context ctx, FDCScheduleInfo schedule) throws BOSException, EASBizException, SQLException {
		ICurProject curProjectInterface = null;
		if(ctx == null){
			curProjectInterface = CurProjectFactory.getRemoteInstance();
		}else{
			curProjectInterface = CurProjectFactory.getLocalInstance(ctx);
		}
		CurProjectInfo project = curProjectInterface.getCurProjectInfo(new ObjectUuidPK(schedule.getProject().getId()));

		if (schedule.getTaskEntrys() != null) {
			FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
			putState(ctx, taskEntrys, project);
			putEvaluationState(ctx,taskEntrys);

		}
	}
	
	
	public static void putEvaluationState(Context ctx,FDCScheduleTaskCollection cols) throws BOSException{
		
		//��ѯ����ǰ����������������״̬
		//�ռ������е�������srcID
		
		Set<String> srcIDs = new HashSet<String>();
		FDCScheduleTaskInfo task = null;
		for(Iterator<FDCScheduleTaskInfo> it = cols.iterator();it.hasNext();){
			task = it.next();
			srcIDs.add(task.getSrcID());
		}
		
		//��ȡ���е�������Ϣ
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("evaluationResult.*");
		view.setSelector(sic);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcRelateTask",srcIDs,CompareType.INCLUDE));
		
		SorterItemCollection sorter  = new SorterItemCollection();
		SorterItemInfo sorters  = new SorterItemInfo("lastUpdateTime");
		sorters.setSortType(SortType.ASCEND);
		sorter.add(sorters);
		view.setSorter(sorter);
		view.setFilter(filter);
		ITaskEvaluationBill  taskEvaluationBill = null;
		if(ctx == null){
			taskEvaluationBill = TaskEvaluationBillFactory.getRemoteInstance();
		}else{
			taskEvaluationBill = TaskEvaluationBillFactory.getLocalInstance(ctx);
		}
		
		TaskEvaluationBillCollection evaluationBills = taskEvaluationBill.getTaskEvaluationBillCollection(view);
		TaskEvaluationBillInfo  evaluationBill = null;
		Map<String,Boolean> evaluatedMap = new HashMap<String, Boolean>();
		Map<String,String> evaluatedResultMap = new HashMap<String, String>();
		String key = null;
		for(Iterator<TaskEvaluationBillInfo> it = evaluationBills.iterator();it.hasNext();){
			evaluationBill = it.next();
			key = evaluationBill.getSrcRelateTask()+"_"+evaluationBill.getEvaluationType().getValue();
			evaluatedMap.put(key, true);
			evaluatedResultMap.put(key, evaluationBill.getEvaluationResult().getEvaluationResult());
		}
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcRelateTask",srcIDs,CompareType.INCLUDE));
		
		sorter  = new SorterItemCollection();
		sorters  = new SorterItemInfo("lastUpdateTime");
		sorters.setSortType(SortType.ASCEND);
		sorter.add(sorters);
		
		view.setSorter(sorter);
		view.setFilter(filter);
		IScheduleTaskProgressReport reportFactory = null;
		if(ctx== null){
			reportFactory = ScheduleTaskProgressReportFactory.getRemoteInstance();
		}else{
			reportFactory = ScheduleTaskProgressReportFactory.getLocalInstance(ctx);
		}
		
		ScheduleTaskProgressReportCollection reportCols = reportFactory.getScheduleTaskProgressReportCollection(view);
		ScheduleTaskProgressReportInfo report = null;
		Map<String,String> reportMap = new HashMap<String, String>();
		for(Iterator<ScheduleTaskProgressReportInfo> it = reportCols.iterator();it.hasNext();){
			report = it.next();
			key = report.getSrcRelateTask();
			if(!StringUtils.isEmpty(report.getDescription())){
				reportMap.put(key, report.getDescription());
			}
			
		}
		
		
		
		
		
		
		
		//��������״̬��ȥ
		
		if(!evaluationBills.isEmpty()){
			
			for(Iterator<FDCScheduleTaskInfo> it = cols.iterator();it.hasNext();){
				task = it.next();
				key = task.getSrcID();
				if(reportMap.containsKey(key)){
					task.put("reportDesc", reportMap.get(key));
				}
				
				
				key=key+"_"+TaskEvaluationTypeEnum.QUALITY_VALUE;
				if(evaluatedMap.containsKey(key)){
					task.put("qualityEvaluation","������");
				}
				
				if(evaluatedResultMap.containsKey(key)){
					task.put("qualityResult",evaluatedResultMap.get(key));
				}
				
				key= task.getSrcID()+"_"+TaskEvaluationTypeEnum.SCHEDULE_VALUE;
				if(evaluatedMap.containsKey(key)){
					task.put("scheduleEvaluation","������");
				}
				
				if(evaluatedResultMap.containsKey(key)){
					task.put("scheduleResult",evaluatedResultMap.get(key));
				}
			}
		}
		
		
	}
	/**
	 * ��������״̬ 
	 *    1 ��������Ҫ������������۲���ͨ���������ʱ��ֻ��д��ϸ������񣬷�����Ჿ����
	 * @param ctx
	 * @param cols
	 * @param projectInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException 
	 */
	public static void putState(Context ctx, FDCScheduleTaskCollection cols,CurProjectInfo projectInfo) throws BOSException, EASBizException, SQLException {
		// ////////////////////////////////////////////////////////////////////////
		// ���������Ż� by skyiter_wang 2014-06-11
		Map putStateMap = FdcManagementUtil.recodeExeTimeBefore(FDCScheduleTaskStateHelper.class, "putState");
		// ////////////////////////////////////////////////////////////////////////
		
		if(cols.isEmpty()){
			return ;
		}
		ICurProject curProjectInterface = null;
		IFDCSchedule fdcScheduleInterface = null;
		if(ctx == null){
			curProjectInterface = CurProjectFactory.getRemoteInstance();
			fdcScheduleInterface = FDCScheduleFactory.getRemoteInstance();
		}else{
			curProjectInterface = CurProjectFactory.getLocalInstance(ctx);
			fdcScheduleInterface = FDCScheduleFactory.getLocalInstance(ctx);
		}
		CurProjectInfo project = curProjectInterface.getCurProjectInfo(new ObjectUuidPK(projectInfo.getId()));
		
		String passMustEvaluation = ParamManager.getParamValue(ctx, new ObjectUuidPK(project.getCostCenter().getId()), "FDCSH011");
	
	    boolean isNeedEvalutaion = false;
	    FDCScheduleInfo sch = null;
	    if(cols.size()>0 && cols.get(0)!=null){
	    	sch = fdcScheduleInterface.getFDCScheduleInfo(new ObjectUuidPK(cols.get(0).getSchedule().getId()));
	    }
	    isNeedEvalutaion = isMustEvaluation(passMustEvaluation,sch);
	    
	    //1��Ҫ���۲���ͨ��ʱ��ֻ����ϸ�����������
	    FDCScheduleTaskCollection taskEntrys = cols;
		FDCScheduleTaskInfo scheduleTaskInfo = null;
		
	    if(isNeedEvalutaion){
	    	Set idSet = isEvaluationed(ctx, taskEntrys);
	    	for (int i = 0; i < taskEntrys.size(); i++) {
				scheduleTaskInfo = taskEntrys.get(i);
				if(!scheduleTaskInfo.isIsLeaf()){
					continue;
				}
				if (scheduleTaskInfo.getComplete() != null
						&& (scheduleTaskInfo.getComplete().compareTo(FDCHelper.ONE_HUNDRED) == 0)) {
					// ��ǰ���
					if (scheduleTaskInfo.getActualEndDate() != null 
							&& scheduleTaskInfo.getEnd() != null 
							&& DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getActualEndDate()), scheduleTaskInfo.getEnd()) >=0) {
						if (idSet.contains(scheduleTaskInfo.getSrcID().toString())) {
							scheduleTaskInfo.put("state", "0");
						} else {
							scheduleTaskInfo.put("state", "2");
						}
					}else if (scheduleTaskInfo.getActualEndDate() != null && 
							scheduleTaskInfo.getEnd() != null
							&& DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getActualEndDate()), scheduleTaskInfo.getEnd()) < 0) {// �Ӻ����
						if (idSet.contains(scheduleTaskInfo.getSrcID().toString())) {
							scheduleTaskInfo.put("state", "1");
						} else {
							scheduleTaskInfo.put("state", "2");
						}
					}
				} else if (scheduleTaskInfo.getEnd() != null 
						    &&DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getEnd()), new Date()) > 0) {
					// ��ʱδ��ɵ�����
					scheduleTaskInfo.put("state", "3");
				} else{
					scheduleTaskInfo.put("state","-1");
				}
				
	    	}
	    	
	    	
	    	
	    }else{
	    	
	    	putStateByNotNeedEvaluation(taskEntrys);
	    }
	    
	        
	        /**
	         * ֻҪ��ǰ��������Ҫ���۵ģ���ô���Ǿ�ֻ��Ҫȥ��д���и��������״̬
	         * ���������ǲ��ܱ����۵�
	         * */
	        if(isNeedEvalutaion){
	        	Map resultMap = getMaxChildrenState(cols,ctx);
	        	for (int i = 0; i < taskEntrys.size(); i++) {
	        		scheduleTaskInfo = taskEntrys.get(i);
	        		if(!scheduleTaskInfo.isIsLeaf()){
	        			if (scheduleTaskInfo.getComplete() != null 
	    						&& (scheduleTaskInfo.getComplete().compareTo(FDCHelper.ONE_HUNDRED) == 0)) {
	    					// ��ǰ���
	    					if (scheduleTaskInfo.getActualEndDate() != null 
	    							&& scheduleTaskInfo.getEnd() != null 
	    							&& DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getActualEndDate()), scheduleTaskInfo.getEnd()) >=0 ) {
	    						
	    						if(resultMap.get(scheduleTaskInfo.getId().toString()) != null && Integer.parseInt(resultMap.get(scheduleTaskInfo.getId().toString())+"") == 2 
	    	        					&& scheduleTaskInfo.getComplete().compareTo(FDCHelper.ONE_HUNDRED) == 0){
	    	        				scheduleTaskInfo.put("state", "2");
	    	        			}else{
	    	        				scheduleTaskInfo.put("state", "0");
	    	        			}
	    						
	    					}else if (scheduleTaskInfo.getActualEndDate() != null 
	    							&& scheduleTaskInfo.getEnd() != null 
	    							&&DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getActualEndDate()),scheduleTaskInfo.getEnd()) <0
	    							) {// �Ӻ����
	    						if(resultMap.get(scheduleTaskInfo.getId().toString()) != null && Integer.parseInt(resultMap.get(scheduleTaskInfo.getId().toString())+"") == 2 
	    	        					&& scheduleTaskInfo.getComplete().compareTo(FDCHelper.ONE_HUNDRED) == 0){
	    	        				scheduleTaskInfo.put("state", "2");
	    	        			}else{
	    	        				scheduleTaskInfo.put("state", "1");
	    	        			}
	    					}
	    				} else if (scheduleTaskInfo.getEnd() != null 
	    						&& DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getEnd()), DateTimeUtils.truncateDate(new Date())) > 0) {
	    					// ��ʱδ��ɵ�����
	    					scheduleTaskInfo.put("state", "3");
	    				} 
	        		}
	        		
	        		
	        	}
	        	
	        }

		// ////////////////////////////////////////////////////////////////////////
		// ���������Ż� by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(FDCScheduleTaskStateHelper.class, "putState", putStateMap);
		// ////////////////////////////////////////////////////////////////////////
	}
	
	/**
	 * ϵͳ����FDCSCH011������ʱ��ֱ�Ӹ����깤�ٷֱȺ�ʵ���깤�������ж�
	 * ������
	 * @param taskEntrys
	 * �����ˣ�yuanjun_lan
	 * ����ʱ�䣺2011-11-2
	 */
	private static void putStateByNotNeedEvaluation(FDCScheduleTaskCollection taskEntrys) {
		FDCScheduleTaskInfo scheduleTaskInfo;
		for (int i = 0; i < taskEntrys.size(); i++) {
			scheduleTaskInfo = taskEntrys.get(i);
			if (scheduleTaskInfo.getComplete() != null 
					&& (scheduleTaskInfo.getComplete().compareTo(FDCHelper.ONE_HUNDRED) == 0)) {
				// ��ǰ���
				if (scheduleTaskInfo.getActualEndDate() != null 
						&& scheduleTaskInfo.getEnd() != null 
						&& DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getActualEndDate()), scheduleTaskInfo.getEnd()) >=0 ) {
					scheduleTaskInfo.put("state", "0");
				}else if (scheduleTaskInfo.getActualEndDate() != null 
						&& scheduleTaskInfo.getEnd() != null 
						&& DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getActualEndDate()), scheduleTaskInfo.getEnd()) < 0
						) {// �Ӻ����
					scheduleTaskInfo.put("state", "1");
				}
			} else if (scheduleTaskInfo.getEnd() != null
					    &&DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getStart()), DateTimeUtils.truncateDate(new Date())) > 0
					    &&DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(new Date()), DateTimeUtils.truncateDate(scheduleTaskInfo.getEnd())) >=0) {
				// ��ʱδ��ɵ�����
				scheduleTaskInfo.put("state", "3");
			} else if(scheduleTaskInfo.getEnd() != null
				    &&DateTimeUtils.dateDiff(DateTimeUtils.truncateDate(scheduleTaskInfo.getEnd()), DateTimeUtils.truncateDate(new Date())) > 0){
				scheduleTaskInfo.put("state", "1");
			}
		}
	}
	
	
	
	


	
	
	/**
	 * 
	 * ������ȡ���Ѿ��Ե�ǰ������й����۵ļ���
	 * @param ctx
	 * @param entrys
	 * @return
	 * @throws BOSException
	 * �����ˣ�yuanjun_lan
	 * ����ʱ�䣺2011-10-21
	 * ֻ�н������۲�Ӱ������״̬
	 * select bill.fsrcrelatetask
     *   from t_sch_taskevaluationbill bill left outer join T_SCH_TaskEvaluation eva on  bill.fevaluationresult = eva.fid
     *   where bill.fsrcrelatetask ='PeiCVC3HTH6QEhTEBWoxtpDM8is=' and isnull(eva.FEVALUATIONPASS,0) = 1 and eva.fevaluationtype = '1SCHEDULE'
	 * @throws SQLException 
	 */
	public static Set isEvaluationed(Context ctx, FDCScheduleTaskCollection entrys) throws BOSException, SQLException {
		ITaskEvaluationBill taskEvaluationBill = null;
		if(ctx == null){
			taskEvaluationBill = TaskEvaluationBillFactory.getRemoteInstance();
		}else{
			taskEvaluationBill = TaskEvaluationBillFactory.getLocalInstance(ctx);
		}
		
		String [] param = new String[entrys.size()];
		Set idSet = new HashSet();
		
		Map evaMap = new HashMap();
		//��ע�ͣ������Ժ󻹻�����δ���
//		for(int i=0;i<entrys.size();i++){
//			param[i] = entrys.get(i).getSrcID().toString();
//		}
		for(int i=0;i<entrys.size();i++){
			idSet.add(entrys.get(i).getSrcID().toString());
		}
//		
//		
//		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		builder.appendSql("select bill.fsrcrelatetask ");
//		builder.appendSql("from t_sch_taskevaluationbill bill left outer join T_SCH_TaskEvaluation eva on  bill.fevaluationresult = eva.fid  ");
//		builder.appendSql("where bill.fsrcrelatetask in (");
//		builder.appendParam(param);
//		builder.appendSql(" )and isnull(eva.FEVALUATIONPASS,0) = 1 and eva.fevaluationtype = '1SCHEDULE' order by bill.flastupdatetime desc");
////		builder.appendSql(" )and eva.fevaluationtype = '1SCHEDULE' order by bill.flastupdatetime desc");
//		
//		IRowSet rs = builder.executeQuery();
//		logger.info(builder.getTestSql());
//		while(rs.next()){
//			idSet.add(rs.getString(1));
//		}
		
		
		//�п����û��ڵ�һ������ĳ������ͨ������������һ����������Ϊ��ͨ������ʱ����ô����
		//����������ȡ��������ص����ۣ��Ƚ����ǵ�����ʱ�䣬
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("evaluationResult.id");
		view.getSelector().add("evaluationResult.name");
		view.getSelector().add("evaluationResult.number");
		view.getSelector().add("evaluationResult.evaluationPass");
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo sorterInfo = new SorterItemInfo("lastupdateTime");
		sorterInfo.setSortType(SortType.ASCEND);
		sorter.add(sorterInfo);
		view.setSorter(sorter);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcRelateTask",idSet,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("evaluationType",TaskEvaluationTypeEnum.SCHEDULE));
		view.setFilter(filter);
		TaskEvaluationBillCollection cols = taskEvaluationBill.getTaskEvaluationBillCollection(view);
		TaskEvaluationBillInfo evaInfo = null;
		TaskEvaluationBillInfo otherInfo = null;
		for(Iterator it = cols.iterator();it.hasNext();){
			evaInfo = (TaskEvaluationBillInfo) it.next();
			if(evaInfo.getEvaluationResult().isEvaluationPass()){
				if(evaMap.get(evaInfo.getSrcRelateTask())== null){
					evaMap.put(evaInfo.getSrcRelateTask(), evaInfo);
				}
			}else{
				if(evaMap.get(evaInfo.getSrcRelateTask())!= null){
					otherInfo = (TaskEvaluationBillInfo) evaMap.get(evaInfo.getSrcRelateTask());
					if(DateTimeUtils.dateDiff(evaInfo.getLastUpdateTime(), otherInfo.getLastUpdateTime())<0){
						evaMap.remove(evaInfo.getSrcRelateTask());
//						evaMap.put(evaInfo.getSrcRelateTask(),evaInfo);
					}
					
				}
			}
			
		}
		
		evaInfo = null;
		otherInfo = null;
//	    return idSet;
		return evaMap.keySet();
	}
	
	
	public static Map<String, FDCScheduleTaskInfo> getNewstTasks(Context ctx, FDCScheduleTaskCollection entrys) throws BOSException, SQLException {
		Set idSet = new HashSet();
		String scheduleId = null;
		for (int i = 0; i < entrys.size(); i++) {
			if (StringUtils.isEmpty(scheduleId))
			scheduleId = entrys.get(i).getSchedule().getId().toString();
			idSet.add(entrys.get(i).getSrcID().toString());
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcId", idSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("schedule.islatestver", Boolean.TRUE));
		// if (!StringUtils.isEmpty(scheduleId))
			// filter.getFilterItems().add(new FilterItemInfo("schedule.id",
			// scheduleId));
		view.setFilter(filter);
		if (ctx != null) {
			entrys = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		} else {
			entrys = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		}
		
		Map<String, FDCScheduleTaskInfo> ret = new HashMap<String, FDCScheduleTaskInfo>();
		FDCScheduleTaskInfo task = null;
		for (int i = 0; i < entrys.size(); i++) {
			task = entrys.get(i);
			ret.put(task.getSrcID(), task);
		}
		
// ITaskEvaluationBill taskEvaluationBill = null;
		// if (ctx == null) {
		// taskEvaluationBill = TaskEvaluationBillFactory.getRemoteInstance();
		// } else {
		// taskEvaluationBill = TaskEvaluationBillFactory.getLocalInstance(ctx);
		// }
		//
		// String[] param = new String[entrys.size()];
		//
		// Map evaMap = new HashMap();
		// for (int i = 0; i < entrys.size(); i++) {
		// idSet.add(entrys.get(i).getSrcID().toString());
		// }
		//
		// // �п����û��ڵ�һ������ĳ������ͨ������������һ����������Ϊ��ͨ������ʱ����ô����
		// // ����������ȡ��������ص����ۣ��Ƚ����ǵ�����ʱ�䣬
		// view = new EntityViewInfo();
		// view.getSelector().add("*");
		// view.getSelector().add("evaluationResult.id");
		// view.getSelector().add("evaluationResult.name");
		// view.getSelector().add("evaluationResult.number");
		// view.getSelector().add("evaluationResult.evaluationPass");
		// SorterItemCollection sorter = new SorterItemCollection();
		// SorterItemInfo sorterInfo = new SorterItemInfo("lastupdateTime");
		// sorterInfo.setSortType(SortType.ASCEND);
		// sorter.add(sorterInfo);
		// view.setSorter(sorter);
		// filter = new FilterInfo();
		// filter.getFilterItems().add(new FilterItemInfo("srcRelateTask",
		// idSet, CompareType.INCLUDE));
		// filter.getFilterItems().add(new FilterItemInfo("evaluationType",
		// TaskEvaluationTypeEnum.SCHEDULE));
		// view.setFilter(filter);
		// TaskEvaluationBillCollection cols =
		// taskEvaluationBill.getTaskEvaluationBillCollection(view);
		// TaskEvaluationBillInfo evaInfo = null;
		// TaskEvaluationBillInfo otherInfo = null;
		// for (Iterator it = cols.iterator(); it.hasNext();) {
		// evaInfo = (TaskEvaluationBillInfo) it.next();
		// if (evaInfo.getEvaluationResult().isEvaluationPass()) {
		// if (evaMap.get(evaInfo.getSrcRelateTask()) == null) {
		// evaMap.put(evaInfo.getSrcRelateTask(), evaInfo);
		// }
		// } else {
		// if (evaMap.get(evaInfo.getSrcRelateTask()) != null) {
		// otherInfo = (TaskEvaluationBillInfo)
		// evaMap.get(evaInfo.getSrcRelateTask());
		// if (DateTimeUtils.dateDiff(evaInfo.getLastUpdateTime(),
		// otherInfo.getLastUpdateTime()) < 0) {
		// evaMap.remove(evaInfo.getSrcRelateTask());
		// }
		//
		// }
		// }
		//
		// }
		//
		// evaInfo = null;
		// otherInfo = null;
		return ret;
	}


	/**
	 * 
	 * ����������ϵͳ�����жϵ�ǰ�����ǲ�����Ҫ��������ȷ��
	 * @param passStatage
	 * @param schedule
	 * @return
	 * �����ˣ�yuanjun_lan
	 * ����ʱ�䣺2011-10-21
	 */
	public static boolean isMustEvaluation(String passStatage,FDCScheduleInfo schedule){
		//������#������������#����ר������#���������ר������
		if("3".equals(passStatage)){
		   return true;	
		}else if("1".equals(passStatage)){
			if(schedule.getProjectSpecial() != null){
				return false;
			}else {
				return true;
			}
			
		}else if("2".equals(passStatage)){
			if(schedule.getProjectSpecial() != null){
				return true;
			}else {
				return false;
			}
			
		}
		return false;
	}
	
	/**
	 * ��ȡ��ǰ�ڵ�������ֵܽڵ㣬������й�������״����
	 * @param resultMap
	 * @param taskEntrys
	 * @param task
	 * @param fdcScheduleTaskInterface
	 * @throws BOSException
	 * @deprecated
	 * @See setParentState(FDCScheduleTaskCollection taskEntrys,FDCScheduleTaskInfo taskInfo)
	 */
	private static void setParentTaskState(Map resultMap,FDCScheduleTaskCollection taskEntrys,FDCScheduleTaskInfo task,IFDCScheduleTask fdcScheduleTaskInterface) throws BOSException{
		if(task.getParent() == null){
			return;
		}
		task = task.getParent();
		int stateNumber =-1;
		if(task.get("state") != null){
			stateNumber =Integer.parseInt(task.get("state")+"");
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent", task.getId().toString()));
		view.setFilter(filter);
		FDCScheduleTaskCollection cols = fdcScheduleTaskInterface.getFDCScheduleTaskCollection(view);
		FDCScheduleTaskInfo cTaskInfo = null;
		FDCScheduleTaskInfo tempTask = null;
		//��¼�µ�ǰ���������������
		Set idSet = new HashSet();
		for (Iterator it = cols.iterator(); it.hasNext();) {
			tempTask = (FDCScheduleTaskInfo) it.next();
            idSet.add(tempTask.getId().toString());
		}
		
		for(Iterator it = taskEntrys.iterator(); it.hasNext();){
			cTaskInfo = (FDCScheduleTaskInfo) it.next();
			if(idSet.contains(cTaskInfo.getId().toString()) && cTaskInfo.get("state") != null){
				if(Integer.parseInt(cTaskInfo.get("state")+"")>stateNumber){
				     task.put("state", cTaskInfo.get("state"));
				     stateNumber = Integer.parseInt(cTaskInfo.get("state")+"");
				     resultMap.put(task.getId().toString(), task.get("state"));
				}
			}
		}
		task.put("state", stateNumber+"");

		if (task.getParent() != null) {
			setParentTaskState(resultMap,taskEntrys,task.getParent(), fdcScheduleTaskInterface);
		}
	}
	
	public static void setParentState(FDCScheduleTaskCollection taskEntrys,FDCScheduleTaskInfo taskInfo){
		if(taskInfo.getParent() == null){
			return ;
		}
		int stateNumber = -1;
		String parentId = taskInfo.getParent().getId().toString();
		FDCScheduleTaskInfo parentTask = null;
		/**��ȡ�����ֵܽڵ�*/
		Set sbilingSet = getAllSblibing(taskInfo, taskEntrys);
		FDCScheduleTaskInfo tempTask = null;
		for(Iterator it = taskEntrys.iterator();it.hasNext();){
			tempTask = (FDCScheduleTaskInfo) it.next();
			if(tempTask.getId().toString().equals(parentId)){
				parentTask = tempTask;
			}
			if(sbilingSet.contains(tempTask.getId().toString())){
				if(tempTask.get("state")!= null && Integer.parseInt(tempTask.get("state")+"")>stateNumber){
					stateNumber = Integer.parseInt(tempTask.get("state")+"");
				}
			}
			
		}
		
		if(parentTask != null){
			if(parentTask.getComplete()== null){
				parentTask.setComplete(FDCHelper.ZERO);
			}
			if(parentTask.getComplete().compareTo(FDCHelper.ONE_HUNDRED)==0 ){
				parentTask.put("state", stateNumber+"");
			}else if(parentTask.getComplete().compareTo(FDCHelper.ONE_HUNDRED)<0 && stateNumber > 2){
				parentTask.put("state", stateNumber+"");
			}
		}
		    
		if(taskInfo.getParent() != null){
			setParentState(taskEntrys,taskInfo.getParent());
		}
	}
	
	
	
	public static Set getAllSblibing(FDCScheduleTaskInfo taskInfo,FDCScheduleTaskCollection cols){
		Set slibingSet = new HashSet();
		int level = taskInfo.getLevel();
		BOSUuid parentId = taskInfo.getParent().getId();
		FDCScheduleTaskInfo tempTask = null;
		for(Iterator it = cols.iterator();it.hasNext();){
			tempTask = (FDCScheduleTaskInfo) it.next();
			if(tempTask.getLevel() == level && tempTask.getParent().getId().equals(parentId)){
				slibingSet.add(tempTask.getId().toString());
			}
		}		
		return slibingSet;
		
	}
	
	/**
	 * ȡ����ǰ�����б��и��������е��������״̬���ֵ
	 * @param list
	 * @return
	 */
	public static Map getMaxChildrenState(FDCScheduleTaskCollection list,Context ctx){
		// ////////////////////////////////////////////////////////////////////////
		// ���������Ż� by skyiter_wang 2014-06-11
		Map getMaxChildrenStateMap = FdcManagementUtil.recodeExeTimeBefore(FDCScheduleTaskStateHelper.class,
				"getMaxChildrenState");
		// ////////////////////////////////////////////////////////////////////////
		
		Map resultMap = new HashMap();
		Object [] colsArray =  list.toArray();
		Arrays.sort(colsArray,new Comparator(){

			public int compare(Object o1, Object o2) {
				if(!(o2 instanceof FDCScheduleTaskInfo)){
					return -1;
				}
				if(o1 == o2){
					return 0;
				}
				FDCScheduleTaskInfo info ,otherInfo;
				if(o1 != null && o2 != null){
					info = (FDCScheduleTaskInfo) o1;
					otherInfo = (FDCScheduleTaskInfo) o2;
					return (info.getLongNumber().compareTo(otherInfo.getLongNumber()))*-1;
				}
				return 0;
			}});

		// ////////////////////////////////////////////////////////////////////////
		
		// ���������Ż��� ����ƿ�� exeTime: 3.363 S by skyiter_wang 2014-06-11
		// ���������Ż�: �˴��ǿ����Ż��� by skyiter_wang 2014-06-11

		// ////////////////////////////////////////////////////////////////////////

		Map allScheduleTaskIdMap = new LinkedHashMap();

		// �Ż�������ѭ��RPC--->����ȡ��
		FDCScheduleTaskInfo firstScheduleTaskInfo = (FDCScheduleTaskInfo) colsArray[0];
		FDCScheduleInfo scheduleInfo = firstScheduleTaskInfo.getSchedule();
		String scheduleId = (null != scheduleInfo) ? scheduleInfo.getId() + "" : null;

		EntityViewInfo viewInfo = new EntityViewInfo();
		SelectorItemCollection selectors = FdcEntityViewUtil.getSelector(new String[] { "id", "name", "state",
				"parent.id", "parent.name", "parent.state" });
		viewInfo.setSelector(selectors);
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("schedule.id", scheduleId);
		viewInfo.setFilter(filterInfo);

		FDCScheduleTaskCollection scheduleTaskCollection = null;
		IFDCScheduleTask fDCScheduleTask = null;
		try {
			if (null != ctx) {
				fDCScheduleTask = FDCScheduleTaskFactory.getLocalInstance(ctx);
			} else {
				fDCScheduleTask = FDCScheduleTaskFactory.getRemoteInstance();
			}

			// �����ݿ���ȡ�����µ�����
			scheduleTaskCollection = fDCScheduleTask.getFDCScheduleTaskCollection(viewInfo);
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			logger.error(e1);
		}

		// �Ż�����������Map
		Map scheduleTaskIdMap = FdcObjectCollectionUtil.parseUniqueIdMap(scheduleTaskCollection);
		allScheduleTaskIdMap.putAll(scheduleTaskIdMap);
		
		// ////////////////////////////////////////////////////////////////////////

		Set tempIdSet = new HashSet();
		FDCScheduleTaskInfo tempTask = null;
		for (int i = 0; i < colsArray.length; i++) {
			tempTask = (FDCScheduleTaskInfo) colsArray[i];
			tempIdSet.add(tempTask.getId());
			tempIdSet.add(FdcObjectValueUtil.get(tempTask, "parent.id"));
		}
		tempIdSet.removeAll(scheduleTaskIdMap.keySet());
		tempIdSet.remove(null);

		if (FdcCollectionUtil.isNotEmpty(tempIdSet)) {
			filterInfo.getFilterItems().clear();
			filterInfo.getFilterItems().add(new FilterItemInfo("id", tempIdSet, CompareType.INCLUDE));

			FDCScheduleTaskCollection tempScheduleTaskCollection = null;
			try {
				// �����ݿ���ȡ�����µ�����
				tempScheduleTaskCollection = fDCScheduleTask.getFDCScheduleTaskCollection(viewInfo);
			} catch (BOSException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				logger.error(e1);
			}
			// �Ż�����������Map
			Map tempScheduleTaskIdMap = FdcObjectCollectionUtil.parseUniqueIdMap(tempScheduleTaskCollection);
			allScheduleTaskIdMap.putAll(tempScheduleTaskIdMap);
		}

		// ////////////////////////////////////////////////////////////////////////
		
		BOSUuid parentId = null;
		BOSUuid parentParentId = null;
		
		for (int i = 0; i < colsArray.length; i++) {
			tempTask = (FDCScheduleTaskInfo) colsArray[i];
			if (tempTask.getParent() != null && tempTask.get("state") != null) {
				if (resultMap.get(tempTask.getParent().getId().toString()) != null) {
					if (Integer.parseInt(resultMap.get(tempTask.getParent().getId().toString()) + "") < Integer
							.parseInt(tempTask.get("state") + "")) {
						resultMap.remove(tempTask.getParent().getId().toString());
						resultMap.put(tempTask.getParent().getId().toString(), tempTask.get("state"));
					}
				} else {
					if (resultMap.get(tempTask.getId().toString()) != null) {
						resultMap.put(tempTask.getParent().getId().toString(), resultMap.get(tempTask.getId()
								.toString()));
					} else {
						parentId = tempTask.getParent().getId();
						FDCScheduleTaskInfo tmpParent = (FDCScheduleTaskInfo) allScheduleTaskIdMap.get(parentId);
						
						if (null != tmpParent && tmpParent.getParent() != null) {
							resultMap.put(parentId.toString(), tempTask.get("state"));

							parentParentId = tmpParent.getParent().getId();
							FDCScheduleTaskInfo innerTmpParent = (FDCScheduleTaskInfo) allScheduleTaskIdMap
									.get(parentParentId);

							if (null != innerTmpParent && innerTmpParent.getParent() != null) {
								resultMap.put(innerTmpParent.getParent().getId().toString(), tempTask.get("state"));
							}
						}
					}
				}
			}

		}
		
		// ////////////////////////////////////////////////////////////////////////
		// ���������Ż� by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(FDCScheduleTaskStateHelper.class, "getMaxChildrenState",
				getMaxChildrenStateMap);
		// ////////////////////////////////////////////////////////////////////////
        
		return resultMap;
	}
	
	
	
}
