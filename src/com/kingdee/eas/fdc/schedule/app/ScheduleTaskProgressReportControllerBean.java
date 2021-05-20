package com.kingdee.eas.fdc.schedule.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.AchievementManagerFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleCompleteHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.IAchievementManager;
import com.kingdee.eas.fdc.schedule.IProjectImage;
import com.kingdee.eas.fdc.schedule.IScheduleQualityCheckPoint;
import com.kingdee.eas.fdc.schedule.IScheduleTaskProgressReport;
import com.kingdee.eas.fdc.schedule.ProjectImageFactory;
import com.kingdee.eas.fdc.schedule.ScheduleQualityCheckPointFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportInfo;

public class ScheduleTaskProgressReportControllerBean extends AbstractScheduleTaskProgressReportControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.ScheduleTaskProgressReportControllerBean");
    @Override
    protected IObjectValue _getValue(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
    	SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("reportor.id"));
		sic.add(new SelectorItemInfo("reportor.name"));
		sic.add(new SelectorItemInfo("reportor.number"));
    	return super._getValue(ctx, pk,sic);
    }
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	IObjectPK pk = super._save(ctx, model);
    	ScheduleTaskProgressReportInfo info = (ScheduleTaskProgressReportInfo) model;
    	
    	Set projectImageSet = (Set) info.get("projectImageBillId");
		Set checkPointSet = (Set) info.get("checkPointBillId");
		Set achievementSet = (Set) info.get("achievementBillId");
		if (projectImageSet != null && !projectImageSet.isEmpty())
			updateRelateData(ctx, pk.toString(), "T_SCH_ProjectImage", projectImageSet);
		if (checkPointSet != null && !checkPointSet.isEmpty())
			updateRelateData(ctx, pk.toString(), "T_SCH_SchQualityCheckPoint", checkPointSet);
		if (achievementSet != null && !achievementSet.isEmpty())
			updateRelateData(ctx, pk.toString(), "T_SCH_AchievementManager", achievementSet);
    	
		
		FDCScheduleTaskInfo scheduleInfo = info.getRelateTask();
		scheduleInfo.setComplete(info.getCompletePrecent());
        FDCScheduleTaskInfo otherInfo = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskInfo(new ObjectUuidPK(info.getRelateTask().getId()));
        BigDecimal newWorkAmount = info.getCompleteAmount();
        if(newWorkAmount != null && otherInfo.getWorkLoad() != null){
        	newWorkAmount = FDCHelper.add(newWorkAmount, otherInfo.getWorkLoad());
        }
        scheduleInfo.setSchedule(otherInfo.getSchedule());
        if(newWorkAmount != null){
        	scheduleInfo.setWorkLoad(newWorkAmount);
        }
        if(otherInfo.getParent() != null){
        	scheduleInfo.setParent(otherInfo.getParent());
        }
        if(info.getRealStartDate()!= null){
        	scheduleInfo.setActualStartDate(info.getRealStartDate());
        }
        if(info.getRealEndDate() != null){
        	scheduleInfo.setActualEndDate(info.getRealEndDate());
        }
        
        if (info.getIntendEndDate() != null) {
			scheduleInfo.setIntendEndDate(info.getIntendEndDate());
		}
    	FDCScheduleCompleteHelper.updateRelateTaskComplete(ctx, scheduleInfo);
    	FDCScheduleCompleteHelper.updateAllTaskCompleteRate(ctx, scheduleInfo);
    	
    	
    	
		return pk;
	}

	/**
	 * 
	 * ����������ǰ��û�������Ȼ㱨��������ȡ��������㡢�׶��Գɹ��Ĺ�����������sourcebill������
	 * 
	 * @param sourcebillId
	 *            �����ˣ�yuanjun_lan ����ʱ�䣺2012-6-1
	 * @throws BOSException
	 */
	private void updateRelateData(Context ctx, String sourcebillId, String tblName, Set idSets) throws BOSException {

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String sql = "update " + tblName + " set fsourcebillid = ? where fid = ?";
	    int size = idSets.size();
		List paramList = new ArrayList();
		List innerList = null;
		for (Iterator it = idSets.iterator(); it.hasNext();) {
			innerList = new ArrayList();
			innerList.add(sourcebillId);
			innerList.add(it.next().toString());
			paramList.add(innerList);
		}
		builder.executeBatch(sql, paramList);
			

	}
    
   
    /**
     * @deprecated
     * @see com.kingdee.eas.fdc.schedule.app.AbstractScheduleTaskProgressReportControllerBean#_afterschreportwriteBack(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectValue)
     */
	protected void _afterschreportwriteBack(Context ctx, IObjectValue model) throws BOSException {
		FDCScheduleTaskInfo info = (FDCScheduleTaskInfo) model;
		// FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// builder.appendSql("update t_sch_fdcscheduletask");
		// // �����
		// builder.appendSql(" set fcomplete='" + info.getComplete() + "'");
		// // ʵ���������
		// if (!FDCHelper.isEmpty(info.getActualEndDate())) {
		// builder.appendSql(" ,factualenddate='" + info.getActualEndDate() +
		// "'");
		// }
		// // ʵ�ʿ�ʼ
		// if (!FDCHelper.isEmpty(info.getActualStartDate())) {
		// builder.appendSql(" ,factualstartdate='" + info.getActualStartDate()
		// + "'");
		// }
		// // �ƻ���ʼ
		// if (!FDCHelper.isEmpty(info.getStart())) {
		// builder.appendSql(" ,fstart='" + info.getStart() + "'");
		// }
		// // �ƻ����
		// if (!FDCHelper.isEmpty(info.getEnd())) {
		// builder.appendSql(" ,fend='" + info.getEnd() + "'");
		// }
		// builder.appendSql(" where fid='" + info.getId().toString() + "'");
		// builder.executeUpdate();
		try {
			FDCScheduleTaskFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId().toString()), info);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	protected IObjectPK[] _delete(Context ctx, String oql) throws BOSException, EASBizException {
		/* TODO �Զ����ɷ������ */
		return super._delete(ctx, oql);
	}
	/**
	 * ����ɾ��Ҳ�����һ���������Կ��Ż���ѭ��ȡ�����еĹ������Ͱٷֱȣ�Ȼ��ִ��һ�θ��£����ܻ������� 
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_delete(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectPK[])
	 */
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		IScheduleTaskProgressReport reportFactory = ScheduleTaskProgressReportFactory.getLocalInstance(ctx);
		ScheduleTaskProgressReportInfo reportInfo = null;
		FDCScheduleTaskInfo scheduleTaskInfo = null;
		BigDecimal totalAmt = FDCHelper.ZERO;
		BigDecimal totalPercent = FDCHelper.ZERO;
	
		
		for(int i=0;i<arrayPK.length;i++){
			reportInfo = reportFactory.getScheduleTaskProgressReportInfo(arrayPK[i]);
			if(scheduleTaskInfo == null){
				scheduleTaskInfo = reportInfo.getRelateTask();
			}
			if(reportInfo.getCompleteAmount() != null){
				totalAmt = FDCHelper.add(reportInfo.getCompleteAmount(), totalAmt);
			}
			if(reportInfo.getAmount() != null){
				totalPercent = FDCHelper.add(reportInfo.getAmount(), totalPercent);
			}
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("parent.id");
		sic.add("parent.name");
		sic.add("parent.number");
		sic.add("schedule.id");
		sic.add("schedule.name");
		sic.add("schedule.number");
	    FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcid", reportInfo.getSrcRelateTask()));
		filter.getFilterItems().add(new FilterItemInfo("schedule.islatestver", Boolean.TRUE));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.setSelector(sic);
		FDCScheduleTaskInfo otherInfo = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view).get(0);
		BigDecimal compPercent = otherInfo.getComplete() == null ? FDCHelper.ZERO:otherInfo.getComplete();
		BigDecimal compWorkLaod = otherInfo.getWorkLoad()== null ? FDCHelper.ZERO:otherInfo.getWorkLoad();
		scheduleTaskInfo.setSrcID(otherInfo.getSrcID());
		if(otherInfo.getParent()!=null){
			scheduleTaskInfo.setParent(otherInfo.getParent());
		}
        if(totalAmt != null){
        	totalAmt = FDCHelper.subtract(compWorkLaod, totalAmt);
        }
        
        if(totalPercent != null){
        	totalPercent = FDCHelper.subtract(compPercent, totalPercent);
        }
        scheduleTaskInfo.setSchedule(otherInfo.getSchedule());
        if(totalAmt != null){
        	scheduleTaskInfo.setWorkLoad(totalAmt);
        }
        if(totalPercent != null){
        	scheduleTaskInfo.setComplete(totalPercent);
        }
		
		super._delete(ctx, arrayPK);
		FDCScheduleCompleteHelper.updateRelateTask(ctx, scheduleTaskInfo);
		FDCScheduleCompleteHelper.updateAllTaskCompleteRate(ctx, scheduleTaskInfo);
	}
	/**
	 * @deprecated
	 * ���������ϣ�Ŀǰȷ�ϲ���Ҫɾ����Ӧ�ļ�¼
	 * @param ctx
	 * @param pk
	 * @throws EASBizException
	 * @throws BOSException
	 * �����ˣ�yuanjun_lan
	 * ����ʱ�䣺2011-11-14
	 */
	private void deleteRelateData(Context ctx,IObjectPK pk) throws EASBizException, BOSException{
		 ScheduleTaskProgressReportInfo reportInfo = ScheduleTaskProgressReportFactory.getLocalInstance(ctx).getScheduleTaskProgressReportInfo(pk);
		 FDCScheduleTaskInfo otherInfo = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskInfo(new ObjectUuidPK(reportInfo.getRelateTask().getId()));
		 FDCScheduleTaskInfo scheduleInfo = reportInfo.getRelateTask();
		 BigDecimal compPercent = otherInfo.getComplete() == null ? FDCHelper.ZERO:otherInfo.getComplete();
		 BigDecimal compWorkLaod = otherInfo.getWorkLoad()== null ? FDCHelper.ZERO:otherInfo.getWorkLoad();
		 
		 BigDecimal newWorkAmount = reportInfo.getCompleteAmount();
		 BigDecimal newPercent = reportInfo.getCompletePrecent();
        if(newWorkAmount != null){
        	newWorkAmount = FDCHelper.subtract(compWorkLaod, newWorkAmount);
        }
        
        if(newPercent != null){
        	newPercent = FDCHelper.subtract(compPercent, compPercent);
        }
        scheduleInfo.setSchedule(otherInfo.getSchedule());
        if(newWorkAmount != null){
        	scheduleInfo.setWorkLoad(newWorkAmount);
        }
        if(newPercent != null){
        	scheduleInfo.setComplete(newPercent);
        }
    	FDCScheduleCompleteHelper.updateRelateTask(ctx, scheduleInfo);
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id",otherInfo.getId().toString()));
		view.setFilter(filter);
		ScheduleTaskBizTypeCollection cols = ScheduleTaskBizTypeFactory.getLocalInstance(ctx).getScheduleTaskBizTypeCollection(view);
		ScheduleTaskBizTypeInfo  type = null;
		IProjectImage imageFactory = ProjectImageFactory.getLocalInstance(ctx);
		IAchievementManager iachievementManager = AchievementManagerFactory.getLocalInstance(ctx);
		IScheduleQualityCheckPoint iqualityChk = ScheduleQualityCheckPointFactory.getLocalInstance(ctx);
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("relateTask",scheduleInfo.getId().toString()));
		for(Iterator it = cols.iterator();it.hasNext();){
			type = (ScheduleTaskBizTypeInfo) it.next();
			if (type.getBizType().getId().toString().equals("Rz+dS7ECSfqM4kEJqGawYWLF6cA=")) {
				iqualityChk.delete(filter);
			} else if (type.getBizType().getId().toString().equals("Rz+dS7ECSfqM4kEJqGawYWLF6cA=")) {
				iachievementManager.delete(filter);
			} else if (type.getBizType().getId().toString().equals("8PR0EhHFTaiDGdtCmQ19SGLF6cA=")) {
				imageFactory.delete(filter);
			}
		}
	}
	
/**
 * Ŀǰ����Ҫ������ֶ�
 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#dealAmount(com.kingdee.bos.Context, com.kingdee.eas.fdc.basedata.FDCBillInfo)
 */
protected void dealAmount(Context ctx, FDCBillInfo billInfo) throws EASBizException, BOSException {
}
    
}