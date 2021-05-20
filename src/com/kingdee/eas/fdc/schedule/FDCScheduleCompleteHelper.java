/*
 * @(#)FDCScheduleCompleteHelper.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.schedule;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.util.DateTimeUtils;

public class FDCScheduleCompleteHelper {
	
	 /**
     * 
     * ���������¹���������깤������
     * @param ctx
     * @param scheduleInfo
     * @throws BOSException
     * �����ˣ�yuanjun_lan
     * ����ʱ�䣺2011-10-24
     */
    public static  void updateRelateTaskComplete(Context ctx,FDCScheduleTaskInfo scheduleInfo) throws BOSException{
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	/* modified by zhaoqin for 2014/08/19 */
    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	builder.appendSql("update t_sch_fdcscheduletask set fcomplete = ?,"); 
    	builder.addParam(scheduleInfo.getComplete());
    	if (scheduleInfo.getIntendEndDate() != null) {
			builder.appendSql("FIntendEndDate = ?,");
			builder.addParam(scheduleInfo.getIntendEndDate());
		}
    	if(scheduleInfo.getWorkLoad()!=null && scheduleInfo.getWorkLoad().compareTo(FDCHelper.ZERO)>0){
    		builder.appendSql("fworkload = ? ,");
    		builder.addParam((scheduleInfo.getWorkLoad())== null ? FDCHelper.ZERO:scheduleInfo.getWorkLoad());
    	}
    	builder.appendSql(" FActualStartDate={ts'"
				+ sdf.format(scheduleInfo.getActualStartDate() == null ? scheduleInfo.getStart() : scheduleInfo.getActualStartDate())
				+ "'}");
    	if(scheduleInfo.getComplete() != null && scheduleInfo.getComplete().compareTo(FDCHelper.ONE_HUNDRED)==0 ){
    		//FActualEndDate
    		builder.appendSql(", FActualEndDate = {ts'"+sdf.format(scheduleInfo.getActualEndDate())+"'}");
    		builder.appendSql(", fnatureTimes = "+scheduleInfo.getNatureTimes());
    		builder.appendSql(", fdiffDays = "+scheduleInfo.getDiffDays());
    	}
    	builder.appendSql(" where fsrcid = ?");
    	builder.appendSql(" and fscheduleid  = ?");
    	builder.addParam(scheduleInfo.getSrcID().toString());
    	builder.addParam(scheduleInfo.getSchedule().getId().toString());
    	builder.execute();
    }
    
    /**
     * 
     * ������ֻ���¶�Ӧ����Ĺ��������깤�ٱ�
     * @param ctx
     * @param scheduleTaskInfo
     * @throws BOSException
     * �����ˣ�yuanjun_lan
     * ����ʱ�䣺2011-11-11
     */
    public static void updateRelateTask(Context ctx,FDCScheduleTaskInfo scheduleTaskInfo) throws BOSException{
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	/**
		 * ɾ���㱨ʱ�������е��깤�ٷֱȸ��¹���ı䣺
		 * ֮ǰ�ǰ���������д������ɾ���㱨����ȡ���л㱨�������깤�ٷֱȸ��µ�������ȥ
		 * ��ɾ��ȫ���㱨������Ϊ0 Added by Owen_wen 2013-12-10
		 */ 
    	builder.appendSql("update t_sch_fdcscheduletask set fcomplete = ");
		builder
				.appendSql("(select isNull(max(FCompletePrecent), 0) from T_SCH_SchTaskProgressReport where FRelateTaskID = t_sch_fdcscheduletask.fid)");
		builder.appendSql(" where t_sch_fdcscheduletask.fid = '" + scheduleTaskInfo.getId() + "'");
		builder.executeUpdate();

		builder.clear();
    	builder.appendSql("update t_sch_fdcscheduletask set "); 
    	if(scheduleTaskInfo.getWorkLoad().compareTo(FDCHelper.ZERO)>= 0){
    		builder.appendSql(" fworkload = ? ");
    		builder.addParam((scheduleTaskInfo.getWorkLoad())== null ? FDCHelper.ZERO:scheduleTaskInfo.getWorkLoad());
    	}
    	/**���깤�ٷֱ�С��100%ʱ��ɾ����ǰ�����ϵ�ʵ��������ں�ʵ�ʿ�ʼ���� */
    	if(scheduleTaskInfo.getComplete().compareTo(FDCHelper.ONE_HUNDRED)<0){
    		builder.appendSql(" , factualEnddate = null ");
    	}
    	if(scheduleTaskInfo.getComplete().compareTo(FDCHelper.ZERO) == 0){
    		builder.appendSql(", factualstartdate = null ");
    	}
    	builder.appendSql(" where fsrcid = ?");
    	builder.appendSql(" and fscheduleid  = ?");
    	builder.addParam(scheduleTaskInfo.getSrcID().toString());
    	builder.addParam(scheduleTaskInfo.getSchedule().getId().toString());
    	builder.execute();
    }
    
    /**
     * 
     * ���������������ϼ��������ɱ���
     *       ���㹫ʽΪ��
     *       ���裺 A �� A1������T 10,��ɱ���P��100����A2������T 20�죬��ɱ���P��20��������������A���㹫ʽ Ϊ(p*10+p*20)/10+20
     * �����ˣ�yuanjun_lan
     * ����ʱ�䣺2011-10-24
     * @throws BOSException 
     * @throws EASBizException 
     */
    public static void  updateAllTaskCompleteRate(Context ctx,FDCScheduleTaskInfo task) throws BOSException, EASBizException{
    	/**��ȡ���������������*/
    	if(task.getParent() == null){
    		return ;
    	}
    	task = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskInfo(new ObjectUuidPK(task.getParent().getId()));
    	Date parentTaskCompelteDate = null;
    	Date parentTaskStartDate = null;
    	BigDecimal realCompleteRate = FDCHelper.ZERO;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("parent",task.getId().toString()));
    	view.setFilter(filter);
    	FDCScheduleTaskCollection cols = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
    	/**���㵱ǰ�ڵ���깤���� */
    	FDCScheduleTaskInfo cTaskInfo = null;
    	BigDecimal completePercent = FDCHelper.ZERO;
    	BigDecimal totalTimes = FDCHelper.ZERO;
    	BigDecimal workLoad = FDCHelper.ZERO;
    	for(Iterator it = cols.iterator();it.hasNext();){
    		cTaskInfo = (FDCScheduleTaskInfo) it.next();
            if(parentTaskCompelteDate == null && cTaskInfo.getActualEndDate() != null){
            	parentTaskCompelteDate = cTaskInfo.getActualEndDate();
    		}
    		
    		
            if(parentTaskStartDate == null && cTaskInfo.getActualStartDate() != null){
            	parentTaskStartDate = cTaskInfo.getActualStartDate();
            }
            
            //��¼������������������ʱ������д���������ʱ��
    		totalTimes = FDCHelper.add(totalTimes, cTaskInfo.getEffectTimes());
    		if(parentTaskStartDate != null 
    				&& cTaskInfo.getActualStartDate() != null 
    				&& DateTimeUtils.dateDiff(cTaskInfo.getActualStartDate(), parentTaskStartDate)>0)
    		    parentTaskStartDate = cTaskInfo.getActualStartDate();
    		if(cTaskInfo.getComplete()!=null   
    				      && cTaskInfo.getEffectTimes().compareTo(FDCHelper.ZERO)>0){
    			completePercent = FDCHelper.add(completePercent, FDCHelper.multiply(cTaskInfo.getComplete(), cTaskInfo.getEffectTimes()));
    			if(cTaskInfo.getActualEndDate() != null
    					&& cTaskInfo.getActualStartDate() != null 
    					&& DateTimeUtils.dateDiff(cTaskInfo.getActualEndDate(), parentTaskCompelteDate) < 0)
    		    	   parentTaskCompelteDate = cTaskInfo.getActualEndDate();
    		}
    		
    		if(cTaskInfo.getWorkLoad() != null){
    			workLoad = FDCHelper.add(workLoad, cTaskInfo.getWorkLoad());
    		}
    	}
    	/**���������깤�ٷֱȵ�������,���ȫ����������ɣ���Ӧ��д�����ʵ�ʿ�ʼ���ڡ�ʵ���깤����**/
    	realCompleteRate =FDCHelper.divide(completePercent, totalTimes);
    	if(realCompleteRate == null){
    		realCompleteRate = FDCHelper.ZERO;
    	}
    	if(workLoad == null){
    		workLoad = FDCHelper.ZERO;
    	}
    		
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("update t_sch_fdcscheduletask set fcomplete = ?,fworkload = ?");
    	if(parentTaskStartDate != null){
    		builder.appendSql(",Factualstartdate = {ts' "+sdf.format(parentTaskStartDate)+"'}");
    	}else{
    		builder.appendSql(",Factualstartdate = null");
    	}
    	builder.addParam(realCompleteRate);
    	builder.addParam(workLoad);
    	if(realCompleteRate.compareTo(FDCHelper.ONE_HUNDRED)==0){//����ȫ���깤
    		builder.appendSql(",factualenddate = {ts' "+sdf.format(parentTaskCompelteDate)+"'}");
    	} else {
			builder.appendSql(",factualenddate = null");
    	}
    	builder.appendSql(" where fsrcid = ?");
    	builder.appendSql(" and fscheduleid = ?");
    	builder.addParam(task.getSrcID().toString());
    	builder.addParam(task.getSchedule().getId().toString());
    	builder.execute();
    	/**ѭ���������и���ڵ���깤���� */
    	if(task.getParent() != null){
    		updateAllTaskCompleteRate(ctx, task);
    	}
    	
    }

}
