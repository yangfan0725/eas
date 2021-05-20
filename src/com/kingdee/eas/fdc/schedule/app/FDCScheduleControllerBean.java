package com.kingdee.eas.fdc.schedule.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.DataAccessException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.REAutoRememberFactory;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;
import com.kingdee.eas.fdc.basedata.util.FdcMethodUtil;
import com.kingdee.eas.fdc.schedule.FDCSchTaskWithContractCollection;
import com.kingdee.eas.fdc.schedule.FDCSchTaskWithContractFactory;
import com.kingdee.eas.fdc.schedule.FDCSchTaskWithContractInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleCompleteHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskStateHelper;
import com.kingdee.eas.fdc.schedule.FDCWBSCollection;
import com.kingdee.eas.fdc.schedule.HelpDeptEntryCollection;
import com.kingdee.eas.fdc.schedule.HelpPersonEntryCollection;
import com.kingdee.eas.fdc.schedule.ProjectSpecialFactory;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqFactory;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqInfo;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqWBSEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleCodingTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCreateReasonEnum;
import com.kingdee.eas.fdc.schedule.ScheduleException;
import com.kingdee.eas.fdc.schedule.ScheduleHelper;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerCollection;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryCollection;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryFactory;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerFactory;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo;
import com.kingdee.eas.fdc.schedule.SpecialScheduleUtils;
import com.kingdee.eas.fdc.schedule.TaskTypeFactory;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.framework.util.StopWatch;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

public class FDCScheduleControllerBean extends AbstractFDCScheduleControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.app.FDCScheduleControllerBean");

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		if (model instanceof FDCScheduleInfo) {
			FDCScheduleInfo schedule = (FDCScheduleInfo) model;
			if (schedule != null && schedule.getDispColumns() != null) {
				schedule.getDispColumns().clear();
			}
		}
		super._update(ctx, pk, model);
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		recycleNumber(ctx, pk);
		_delete(ctx, new IObjectPK[] { pk });
	}

	protected IObjectPK[] _delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException {
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo("id"));
		IObjectPK[] pks = getPKList(ctx, filter, sorter);
		for (int i = 0; i < pks.length; i++) {
			recycleNumber(ctx, pks[i]);
		}
		_delete(ctx, pks);
		return pks;
	}

	/**
	 * 
	 * ��������鵱ǰ�����Ƿ���ִ�У����������˹����������Դӹ���������������������Ȼ���ύ״̬��������������飬���ܻ�������ݲ�һ��
	 * 
	 * @param ctx
	 * @param info
	 * @return 0:�ܽ��б���c �����ˣ�yuanjun_lan ����ʱ�䣺2011-12-22
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkCanOPerator(Context ctx,BOSUuid id) throws EASBizException, BOSException{
		FDCScheduleInfo newInfo = (FDCScheduleInfo) getValue(ctx, new ObjectUuidPK(id));
		if(newInfo.getState().equals(ScheduleStateEnum.AUDITTED)||newInfo.getState().equals(ScheduleStateEnum.AUDITTING))
			throw new ScheduleException(ScheduleException.WITHMSG,new Object[]{"��ǰ����Ϊ"+newInfo.getState()+",���ܽ��д˲�����"});
		
	}

	protected IObjectPK[] _delete(Context ctx, String oql) throws BOSException, EASBizException {
		IObjectPK[] pks = this.getPKList(ctx, oql);
		for (int i = 0; i < pks.length; i++) {
			recycleNumber(ctx, pks[i]);
		}
		_delete(ctx, pks);
		return pks;
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		// ɾ����չ����
		// ֻ��ɾ������,�ύ�ĵ���

		// Set idSet=new HashSet();
		String[] arrays = new String[arrayPK.length];
		for (int i = 0; i < arrayPK.length; i++) {
			// idSet.add(arrayPK[i].toString());
			arrays[i] = arrayPK[i].toString();
		}

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule where fstate<>? and fstate<> ? and ");
		builder.addParam(ScheduleStateEnum.SAVED_VALUE);
		builder.addParam(ScheduleStateEnum.SUBMITTED_VALUE);
		builder.appendParam("fid", arrays);
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("111", "���漰�ύ״̬�ĵ��ݲſ���ɾ��!"));
		}
		FilterInfo filter = new FilterInfo();
		String sql = "select c.fid from T_SCH_FDCSchedule a,T_SCH_FDCScheduleTask b,T_Sch_FDCWBS c where a.fid=b.fscheduleid and b.fwbsid=c.fid and  "
				+ FDCSQLBuilder.getInSql("a.fid", arrays) + "";
		filter.getFilterItems().add(new FilterItemInfo("wbs.id", sql, CompareType.INNER));
		FDCScheduleTaskExtFactory.getLocalInstance(ctx).delete(filter);

		// delete ver information
		// / delete only on ver entry

		sql = "select fscheduleid from T_sch_schedulevermanagerentry  where " + FDCSQLBuilder.getInSql("fscheduleid", arrays)
				+ " group by fscheduleid having count(*)=1 ";
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", sql, CompareType.INNER));
		ScheduleVerManagerFactory.getLocalInstance(ctx).delete(filter);

		// delete other
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule.id", new HashSet(Arrays.asList(arrays)), CompareType.INCLUDE));
		ScheduleVerManagerEntryFactory.getLocalInstance(ctx).delete(filter);

		for (int i = 0; i < arrayPK.length; i++) {
			builder.clear();
			builder.appendSql("delete from t_sch_fdcscheduletaskdepend where ftaskid in (select fid from t_sch_fdcscheduletask where fscheduleid=?)");
			builder.addParam(arrayPK[i].toString());
			builder.execute();
			builder.clear();
			builder.appendSql("delete from t_sch_fdcscheduletask where fscheduleid=?");
			builder.addParam(arrayPK[i].toString());
			builder.execute();
		}
		for (int i = 0; i < arrayPK.length; i++) {
			recycleNumber(ctx, arrayPK[i]);
		}
		super._delete(ctx, arrayPK);
	}

	/**
	 * 
	 * �����������޸����ݣ� ������ɾ����÷�ʽ���ܹ���Ч��������
	 * 
	 * @param col
	 *            ���޸ĵ�ʵ������
	 * @param dao
	 * @throws DataAccessException
	 *             ����ʱ�䣺2010-8-16 �����ˣ�zhiqiao_yang
	 */
	private void batchUpdate(AbstractObjectCollection col, IORMappingDAO dao) throws DataAccessException {
		int size = col.size();
		if (size > 0) {
			for (int i = 0; i < size; ++i) {
				dao.deleteBatch(new ObjectUuidPK(((CoreBaseInfo) col.getObject(i)).getId()));
			}
			dao.executeBatch();
			for (int i = 0; i < size; ++i) {
				dao.addNewBatch(col.getObject(i));
			}
			dao.executeBatch();
		}
	}

	/**
	 * 
	 * ���������¼ƻ��е���������ϵ
	 * 
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 *             ����ʱ�䣺2010-8-16 �����ˣ�zhiqiao_yang
	 */
	private void addTaskAndTaskDepend(Context ctx, FDCScheduleInfo info) throws BOSException {
		FDCScheduleTaskCollection tasks = info.getTaskEntrys();
		if (tasks != null && tasks.size() > 0) {
			FDCScheduleTaskDependCollection depends = new FDCScheduleTaskDependCollection();
			
			HelpPersonEntryCollection pcols=new HelpPersonEntryCollection();
			HelpDeptEntryCollection dcols=new HelpDeptEntryCollection();
			int taskSize = tasks.size();
			for (int i = 0; i < taskSize; ++i) {
				tasks.get(i).setSchedule(info);
				FDCScheduleTaskDependCollection tempDepends = tasks.get(i).getDependEntrys();
				if (tempDepends != null && tempDepends.size() > 0) {
					depends.addCollection(tempDepends);
				}
				
				HelpPersonEntryCollection pcol=tasks.get(i).getHelpPersonEntry();
				if(pcol!=null&&pcol.size()>0){
					pcols.addCollection(pcol);
				}
				
				HelpDeptEntryCollection dcol=tasks.get(i).getHelpDeptEntry();
				if(dcol!=null&&dcol.size()>0){
					dcols.addCollection(dcol);
				}
			}
			IORMappingDAO taskDao = ORMappingDAO.getInstance(tasks.get(0).getBOSType(), ctx, getConnection(ctx));
			batchUpdate(tasks, taskDao);
			if (depends.size() > 0) {
				IORMappingDAO dependDao = ORMappingDAO.getInstance(depends.get(0).getBOSType(), ctx, getConnection(ctx));
				batchUpdate(depends, dependDao);
			}
			if(pcols.size()>0){
				IORMappingDAO dependDao = ORMappingDAO.getInstance(pcols.get(0).getBOSType(), ctx, getConnection(ctx));
				batchUpdate(pcols, dependDao);
			}
			if(dcols.size()>0){
				IORMappingDAO dependDao = ORMappingDAO.getInstance(dcols.get(0).getBOSType(), ctx, getConnection(ctx));
				batchUpdate(dcols, dependDao);
			}
		}
	}

	private IObjectPK _saveOrSubmit(Context ctx, IObjectValue model, ScheduleStateEnum state) throws BOSException, EASBizException {
		// StopWatch sw = new StopWatch();
		// sw.start();
		FDCScheduleInfo info = (FDCScheduleInfo) model;
        if(info.getVersionName()!=null){
        	info.setName(info.getVersionName());
        	info.setNumber(info.getVersionName());
        }
		info.setState(state);
		if (info.containsKey("AdjustParam")) {
			handleAdjustGanttReq(ctx, info);
			return new ObjectUuidPK(info.get("returnPK").toString());
		}
		handBeforeSaveAndSubmit(ctx, info);
		// logger.info("SaveOrSumit handBeforeSaveAndSubmit cost time: " +
		// sw.getLastTime());
		if ((info.getId() != null && !this._exists(ctx, new ObjectUuidPK(info.getId()))) || info.getId() == null) {
			// handleIntermitNumber(ctx, info);
		}
		IObjectPK pk = null;
		if (ScheduleStateEnum.SUBMITTED.equals(state)) {
			pk = super._submit(ctx, model);
		} else {
			pk = super._save(ctx, model);
		}
		
		// ���������ͬ
		saveRelationContract(ctx, info);
		
		// ɾ��ǰ�ù�ϵ
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("delete from T_SCH_FDCScheduleTaskDepend where ftaskid in ");
		builder.appendSql("(select fid from T_SCH_FDCScheduleTask where fscheduleid=?)");
		builder.addParam(info.getId().toString());
		logger.info(builder.getTestSql());
		builder.execute();
		
		//ɾ������ҵ�����ͷ�¼
//		builder.clear();
//		builder.appendSql("delete from T_SCH_ScheduleTaskBizType where fparentid in  (select fid from t_sch_fdcscheduletask where fscheduleid =(select fid from t_sch_fdcschedule where fversion = ? and fprojectid = ?");
//		builder.addParam(Float.valueOf(info.getVersion()-1));
//		builder.addParam(info.getProject().getId().toString());
//		if(info.getProjectSpecial() == null){
//			builder.appendSql(" and FProjectSpecialID is null");
//		}else{
//			builder.appendSql(" and FProjectSpecialID is not null and FProjectSpecialID = ?");
//			builder.addParam(info.getProjectSpecial().getId().toString());
//		}
//	    builder.appendSql("))");
//        logger.info(builder.getTestSql());
//		builder.execute();
		
		// ɾ������
		builder.clear();
		builder.appendSql("delete from T_SCH_FDCScheduleTask where fscheduleid=?");
		builder.addParam(info.getId().toString());
		 logger.info(builder.getTestSql());
		builder.execute();
		
		addTaskAndTaskDepend(ctx, info);
		if (info.getId() == null) {
			info.setId(BOSUuid.read(pk.toString()));
		}

		// logger.info("SaveOrSumit save cost time: " + sw.getLastTime());
		handAfterSaveAndSubmit(ctx, info);
		// logger.info("SaveOrSumit handAfterSaveAndSubmit cost time: " +
		// sw.getLastTime());
		
		return pk;
	}

	/**
	 * ���������ͬ
	 * 
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void saveRelationContract(Context ctx, FDCScheduleInfo info)
			throws BOSException, EASBizException {
		// ��վɹ�ϵ
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("task.schedule.srcID", info.getSrcID()));
		FDCSchTaskWithContractFactory.getLocalInstance(ctx).delete(filter);
		// �����¹�ϵ
		IORMappingDAO dao = ORMappingDAO.getInstance(
				new FDCSchTaskWithContractInfo().getBOSType(), ctx,
				getConnection(ctx));
		for (int i = 0; i < info.getTaskEntrys().size(); i++) {
			List relCon = (List) info.getTaskEntrys().get(i).get("relCon");
			if (relCon != null) {
				for (int j = 0; j < relCon.size(); j++) {
					FDCSchTaskWithContractInfo relConInfo = (FDCSchTaskWithContractInfo) relCon
							.get(j);
					dao.addnew(relConInfo);
				}
			}
		}
		dao.executeBatch();
	}

	private void updateHistoryVersionState(IObjectPK pk, Context ctx, FDCScheduleInfo info) {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set FIsLatestVer='0' ");
		builder.appendSql(" where FProjectID=? and ");
		builder.addParam(info.getProject().getId().toString());
		if (info.getProjectSpecial() == null) {
			builder.appendSql("(FProjectSpecialID is null or FProjectSpecialID = '') and ");
		} else {
			builder.appendParam("FProjectSpecialID", info.getProjectSpecial().getId().toString());
			builder.appendSql(" and ");
		}
		builder.appendSql(" FID<>?");
		builder.addParam(pk.toString());
		try {
			builder.executeUpdate();
		} catch (BOSException e) {
			e.printStackTrace();
		}

	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		FDCScheduleInfo info = (FDCScheduleInfo) model;
		if(info.getId() != null && exists(ctx, new ObjectUuidPK(info.getId())))
		  checkCanOPerator(ctx, info.getId());
		IObjectPK pk = _saveOrSubmit(ctx, model, ScheduleStateEnum.SUBMITTED);
		/** ������ʷ�汾��״̬ **/
//		updateHistoryVersionState(pk, ctx, (FDCScheduleInfo) model);
		if(info.getProjectSpecial() != null){
			submitSpecialSchedule(ctx, info);
		}else{
			submitMainSchedule(ctx, info);
		}
		return pk;
	}

	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		FDCScheduleInfo info = (FDCScheduleInfo) model;
		if(info.getId() != null && exists(ctx, new ObjectUuidPK(info.getId())))
		  checkCanOPerator(ctx, info.getId());
		info.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		info.setCreateTime(new Timestamp(System.currentTimeMillis()));
		IObjectPK pk = _saveOrSubmit(ctx, model, ScheduleStateEnum.SAVED);
		/** ������ʷ�汾��״̬ **/
//		updateHistoryVersionState(pk, ctx, (FDCScheduleInfo) model);
		return pk;
	}

	private void handleAdjustGanttReq(Context ctx, FDCScheduleInfo info) throws EASBizException, BOSException {
		Map param = (Map) info.get("AdjustParam");
		ScheduleAdjustGattReqInfo adjustGanttReq = (ScheduleAdjustGattReqInfo) info.get("AdjustInfo");

		/***
		 * R100823-129 ��Ͽͻ��˴�����޸ģ��������˴���Ҳ����Ӧ���жϣ�
		 * Ŀ�ģ���Ϊ�˱��������ƻ����ƵĹ���������Ҫ�ܹ����������ƻ��������Ĺ�����
		 */
		if (param.containsKey("isSubmit")) {
			info.setState(ScheduleStateEnum.SUBMITTED);
		}
		String taskTypeId = (String) param.get("taskTypeId");// ����רҵ����

		ScheduleVerManagerInfo adjustVerInfo = info.getBaseVer();
		ScheduleVerManagerInfo newSchVerInfo = null;
		if (adjustVerInfo.getVersion() != 0 && adjustGanttReq.getNewVer() == null) {
			newSchVerInfo = (ScheduleVerManagerInfo) adjustVerInfo.clone();
		} else {
			newSchVerInfo = adjustVerInfo;
		}

		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selector = view.getSelector();
		selector.add("id");
		selector.add("parent.project.id");
		selector.add("parent.project.name");
		selector.add("parent.project.number");
		selector.add("parent.project.longNumber");
		selector.add("parent.project.startDate");
		selector.add("parent.project.fullOrgUnit.id");
		selector.add("parent.project.fullOrgUnit.name");
		selector.add("parent.project.fullOrgUnit.number");
		selector.add("*");
		selector.add("schedule.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("parent.id", adjustVerInfo.getId().toString()));
		ScheduleVerManagerEntryCollection verEntrys = ScheduleVerManagerEntryFactory.getLocalInstance(ctx).getScheduleVerManagerEntryCollection(view);

		newSchVerInfo.getEntrys().clear();
		newSchVerInfo.getEntrys().addCollection(verEntrys);

		if (adjustGanttReq.getNewVer() == null) {
			newSchVerInfo.setVersion(FDCHelper.ZERO.floatValue());
			newSchVerInfo.setId(BOSUuid.create(adjustVerInfo.getBOSType()));
			newSchVerInfo.setCreateReason(ScheduleCreateReasonEnum.Adjust);
		}
		newSchVerInfo.setIsLatestVer(false);

		Map schedules = new HashMap();
		for (Iterator it = newSchVerInfo.getEntrys().iterator(); it.hasNext();) {
			ScheduleVerManagerEntryInfo entry = (ScheduleVerManagerEntryInfo) it.next();
			if (adjustGanttReq.getNewVer() == null) {
				entry.setId(BOSUuid.create(entry.getBOSType()));
				entry.setParent(newSchVerInfo);
				entry.setVersion(entry.getVersion() + 1);
			}

			schedules.put(entry.getSchedule().getId().toString(), entry.getSchedule());
		}

		FDCScheduleTaskCollection schduleTaskColl = info.getTaskEntrys();
		FDCScheduleTaskCollection saveScheduleTasks = new FDCScheduleTaskCollection();

		Map oldIdNewTaskMap = new HashMap();
		for (Iterator it = schduleTaskColl.iterator(); it.hasNext();) {
			FDCScheduleTaskInfo fdcSchTask = (FDCScheduleTaskInfo) it.next();
			if (fdcSchTask.containsKey("myScheduleID") && schedules.containsKey(fdcSchTask.get("myScheduleID"))) {
				FDCScheduleInfo fdcSch2 = (FDCScheduleInfo) schedules.get(fdcSchTask.get("myScheduleID"));
				if (!fdcSch2.containsKey("HasNewID")) {
					if (adjustGanttReq.getNewVer() == null) {
						fdcSch2.setId(BOSUuid.create(fdcSch2.getBOSType()));
						fdcSch2.setVersion(FDCHelper.ZERO.floatValue());
						fdcSch2.setIsLatestVer(false);
						fdcSch2.setState(ScheduleStateEnum.SAVED);
						fdcSch2.setBaseVer(newSchVerInfo);
						fdcSch2.setCreateTime(new Timestamp(Calendar.getInstance().getTime().getTime()));
						fdcSch2.setCreateReason(ScheduleCreateReasonEnum.Adjust);
						fdcSch2.setStartDate(null);
						fdcSch2.setEndDate(null);
					}
					fdcSch2.getTaskEntrys().clear();
					fdcSch2.getDispColumns().clear();
					fdcSch2.put("HasNewID", Boolean.TRUE);
				}

				String oldId = fdcSchTask.getId().toString();
				fdcSchTask.put("oldId", oldId);
				if (adjustGanttReq.getNewVer() == null) {
					fdcSchTask.setId(BOSUuid.create(fdcSchTask.getBOSType()));
					fdcSchTask.setSchedule(fdcSch2);
				}
				if (fdcSch2.getStartDate() == null || fdcSch2.getStartDate().after(fdcSchTask.getStart())) {
					fdcSch2.setStartDate(fdcSchTask.getStart());
				}
				if (fdcSch2.getEndDate() == null || fdcSch2.getEndDate().before(fdcSchTask.getEnd())) {
					fdcSch2.setEndDate(fdcSchTask.getEnd());
				}
				fdcSch2.getTaskEntrys().add(fdcSchTask);
				oldIdNewTaskMap.put(oldId, fdcSchTask);

				saveScheduleTasks.add(fdcSchTask);
			}
		}

		/***
		 * [1].[2]ȡ���мƻ������񣬼�����֮���ǰ���ϵ
		 * 
		 */
		FDCScheduleTaskDependCollection taskDepends = new FDCScheduleTaskDependCollection();
		for (Iterator it = schduleTaskColl.iterator(); it.hasNext();) {
			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) it.next();
			if (taskInfo.getParent() != null) {
				if ((FDCScheduleTaskInfo) oldIdNewTaskMap.get(taskInfo.getParent().getId().toString()) != null)
					taskInfo.setParent((FDCScheduleTaskInfo) oldIdNewTaskMap.get(taskInfo.getParent().getId().toString()));
			}

			for (int i = 0; i < taskInfo.getDependEntrys().size(); i++) {
				FDCScheduleTaskDependInfo dependInfo = taskInfo.getDependEntrys().get(i);
				if (adjustGanttReq.getNewVer() == null) {
					dependInfo.setId(BOSUuid.create(dependInfo.getBOSType()));
				}

				// dependInfo.setTask(taskInfo);
				if ((FDCScheduleTaskInfo) oldIdNewTaskMap.get(dependInfo.getDependTask().getId().toString()) != null)
					dependInfo.setDependTask((FDCScheduleTaskInfo) oldIdNewTaskMap.get(dependInfo.getDependTask().getId().toString()));

				taskDepends.add(dependInfo);
			}
		}

		IORMappingDAO dao = ORMappingDAO.getInstance(info.getBOSType(), ctx, getConnection(ctx));
		ScheduleCalendarInfo calendar = info.getCalendar();
		if (calendar == null) {
			ScheduleAppHelper.setCalendar(ctx, info);
			calendar = info.getCalendar();
		}
		for (int i = 0; i < newSchVerInfo.getEntrys().size(); i++) {
			FDCScheduleInfo schedule = newSchVerInfo.getEntrys().get(i).getSchedule();
			BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(schedule.getStartDate(), schedule.getEndDate(), calendar);
//			BigDecimal natureTimes = ScheduleCalendarHelper.getNatureTimes(schedule.getStartDate(), schedule.getEndDate());
			schedule.setEffectTimes(effectTimes);
//			schedule.setNatureTimes(natureTimes);
			if (!FDCScheduleFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(schedule.getId()))) {
				dao.addNewBatch(schedule);
			} else {
				dao.updateBatch(new ObjectUuidPK(schedule.getId()), schedule);
			}
			addTaskAndTaskDepend(ctx, schedule);
		}
		dao.executeBatch();

		IObjectPK newVerPK = ScheduleVerManagerFactory.getLocalInstance(ctx).save(newSchVerInfo);

		// ���°汾�ƻ��Ŀ�ʼ,������ʱ��
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select min(task.fstart) as fstartDate,max(task.fend) as fendDate from T_SCH_FDCScheduleTask task ");
		builder.appendSql(" inner join T_Sch_FDCSchedule head on head.fid=task.fscheduleid ");
		builder.appendSql(" where head.fprojectid=? and head.fversion=0");

		builder.addParam(info.getProject().getId().toString());
		IRowSet rowSet = builder.executeQuery();
		try {
			if (rowSet.next()) {
				Date startDate = rowSet.getDate("fstartDate");
				Date endDate = rowSet.getDate("fendDate");
				BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(startDate, endDate, calendar);
//				BigDecimal natureTimes = ScheduleCalendarHelper.getNatureTimes(startDate, endDate);
				builder.clear();
				builder
						.appendSql("update T_SCH_ScheduleVerManager set fstartDate=?,fendDate=?,feffectTimes=? where fprojectId=? and fversion=0 ");
				builder.addParam(startDate);
				builder.addParam(endDate);
				builder.addParam(effectTimes);
//				builder.addParam(natureTimes);
				builder.addParam(info.getProject().getId().toString());
				builder.execute();
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		IObjectPK reqPk = null;
		if (adjustGanttReq.getNewVer() == null) {
			adjustGanttReq.setBaseVer(adjustVerInfo);
			adjustGanttReq.setNewVer(newSchVerInfo);
		}
		if (ScheduleStateEnum.SUBMITTED.equals(info.getState())) {
			reqPk = ScheduleAdjustGattReqFactory.getLocalInstance(ctx).submit(adjustGanttReq);
		} else {
			reqPk = ScheduleAdjustGattReqFactory.getLocalInstance(ctx).save(adjustGanttReq);
		}

		adjustGanttReq.setId(BOSUuid.read(reqPk.toString()));
		info.put("returnPK", reqPk);
	}

	private void handAfterSaveAndSubmit(Context ctx, FDCScheduleInfo info) throws EASBizException, BOSException {
		StopWatch sw = new StopWatch();
		sw.start();
		// ���°汾�ƻ��Ŀ�ʼ,������ʱ��
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select min(task.fstart) as fstartDate,max(task.fend) as fendDate from T_SCH_FDCScheduleTask task ");
		builder.appendSql(" inner join T_Sch_FDCSchedule head on head.fid=task.fscheduleid ");
		builder.appendSql(" where head.fislatestver=1 and head.fprojectid=? ");

		builder.addParam(info.getProject().getId().toString());
		IRowSet rowSet = builder.executeQuery();
		try {
			if (rowSet.next()) {
				Date startDate = rowSet.getDate("fstartDate");
				Date endDate = rowSet.getDate("fendDate");
				if(null != startDate && null != endDate) {
					ScheduleCalendarInfo calendar = info.getCalendar();
					if (calendar == null) {
						ScheduleAppHelper.setCalendar(ctx, info);
						calendar = info.getCalendar();
					}
					BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(startDate, endDate, calendar);
//					BigDecimal natureTimes = ScheduleCalendarHelper.getNatureTimes(startDate, endDate);
					builder.clear();
					builder
							.appendSql("update T_SCH_ScheduleVerManager set fstartDate=?,fendDate=?,feffectTimes=? where fprojectId=? and fislatestver=1 ");
					builder.addParam(startDate);
					builder.addParam(endDate);
					builder.addParam(effectTimes);
//					builder.addParam(natureTimes);
					builder.addParam(info.getProject().getId().toString());
					builder.execute();
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		logger.info("handAfterSaveAndSubmit 3 cost time: " + sw.getLastTime());

		/*
		 * �����ƻ���ǰ������ 1.��ԭ�еı��ƻ����ǰ������ɾ���� 2.����Ŀǰ�ı��ƻ����ǰ������
		 */
		// delete prefixdepend
		// builder.clear();
		// builder = new FDCSQLBuilder(ctx);
		// builder
		// .appendSql(
		// "delete from T_sch_fdcscheduletaskdepend where exists (select 1 from T_sch_fdcscheduletask where fid=ftaskid and fscheduleid<>?) and exists (select 1 from T_sch_fdcscheduletask where fid=fdependtaskid and fscheduleid=? )"
		// );
		// builder.addParam(info.getId().toString());
		// builder.addParam(info.getId().toString());
		// builder.execute();
		// // save prefixdepend
		// FDCScheduleTaskDependCollection prefixDepends =
		// (FDCScheduleTaskDependCollection) info.get("prefixDepends");
		//
		// // ��ʱ��֮���أ�����������κ������񣬲����׿�ƻ���ǰ������Ӧ����ô���������Ҫ�Ļ��˴�����ж�
		// if (prefixDepends != null && prefixDepends.size() > 0) {
		// CoreBaseCollection colls = (CoreBaseCollection)
		// prefixDepends.cast(CoreBaseCollection.class);
		// // FDCScheduleTaskDependFactory.getLocalInstance(ctx).save(colls);
		// }
		logger.info("handAfterSaveAndSubmit 4 cost time: " + sw.getLastTime());

	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		IObjectPK pk = super._addnew(ctx, model);
		FDCScheduleInfo info = (FDCScheduleInfo) model;
		String id = pk.toString();
		if (info.getId() == null) {
			info.setId(BOSUuid.read(id));
		}
		addToScheduleVerManager(ctx, info);
		return pk;
	}

	private void addToScheduleVerManager(Context ctx, FDCScheduleInfo info) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getSelector().add("*");
		view.getSelector().add("project.id");
		view.getSelector().add("entrys.*");
		view.getSelector().add("schedule.id");
		view.getFilter().appendFilterItem("project.id", info.getProject().getId().toString());
		view.getFilter().appendFilterItem("isLatestVer", Boolean.TRUE);
		ScheduleVerManagerCollection coll = ScheduleVerManagerFactory.getLocalInstance(ctx).getScheduleVerManagerCollection(view);
		if (coll.size() > 1) {
			throw new EASBizException(new NumericExceptionSubItem("120", "���ڶ�����µ��ܽ��Ȱ汾!"));
		}
		String baseVerId = null;
		if (coll.size() == 1) {
			ScheduleVerManagerInfo verInfo = coll.get(0);
			baseVerId = verInfo.getId().toString();
			boolean hasInsert = false;
			for (Iterator iter = verInfo.getEntrys().iterator(); iter.hasNext();) {
				ScheduleVerManagerEntryInfo entry = (ScheduleVerManagerEntryInfo) iter.next();
				if (entry.getSchedule().getId().equals(info.getId())) {
					hasInsert = true;
					break;
				}
			}

			if (!hasInsert) {
				// Ϊ��ǰ�����°汾����һ����¼
				ScheduleVerManagerEntryInfo entry = new ScheduleVerManagerEntryInfo();
				entry.setParent(verInfo);
				entry.setSchedule(info);
				entry.setVersion(info.getVersion());
				ScheduleVerManagerEntryFactory.getLocalInstance(ctx).addnew(entry);

				// update startDate,endDate,times
				if (verInfo.getStartDate() == null || info.getStartDate().compareTo(verInfo.getStartDate()) < 0) {
					verInfo.setStartDate(info.getStartDate());
				}
				// ֮ǰ�Ĵ���û���ж�info.getEndDate() �Ƿ�Ϊ
				// null.����������ר�û������ר�������ʱ�򣬻ᱨ��ָ���쳣�� --jiadong
				if (verInfo.getEndDate() == null || info.getEndDate() == null || info.getEndDate().compareTo(verInfo.getEndDate()) > 0) {
					verInfo.setEndDate(info.getEndDate());
				}
				verInfo.setEffectTimes(ScheduleCalendarHelper.getEffectTimes(verInfo.getStartDate(), verInfo.getEndDate(), info.getCalendar()));
//				verInfo.setNatureTimes(ScheduleCalendarHelper.getNatureTimes(verInfo.getStartDate(), verInfo.getEndDate()));

				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("startDate");
				selector.add("endDate");
				selector.add("effectTimes");
//				selector.add("natureTimes");
				ScheduleVerManagerFactory.getLocalInstance(ctx).updatePartial(verInfo, selector);
			}
		} else {
			// addNew
			ScheduleVerManagerInfo verInfo = new ScheduleVerManagerInfo();
			verInfo.setVersion(info.getVersion());
			verInfo.setProject(info.getProject());
			verInfo.setEffectTimes(info.getEffectTimes());
//			verInfo.setNatureTimes(info.getNatureTimes());
			verInfo.setStartDate(info.getStartDate());
			verInfo.setEndDate(info.getEndDate());
			verInfo.setCreateReason(ScheduleCreateReasonEnum.InitVer);
			verInfo.setIsLatestVer(true);
			// entrys
			ScheduleVerManagerEntryInfo entry = new ScheduleVerManagerEntryInfo();
			entry.setParent(verInfo);
			entry.setSchedule(info);
			entry.setVersion(info.getVersion());
			verInfo.getEntrys().add(entry);

			verInfo.setEffectTimes(ScheduleCalendarHelper.getEffectTimes(verInfo.getStartDate(), verInfo.getEndDate(), info.getCalendar()));
//			verInfo.setNatureTimes(ScheduleCalendarHelper.getNatureTimes(verInfo.getStartDate(), verInfo.getEndDate()));
			verInfo.setIsLatestVer(true);
			IObjectPK pk = ScheduleVerManagerFactory.getLocalInstance(ctx).addnew(verInfo);
			baseVerId = pk.toString();
		}

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_sch_fdcschedule set fbaseverid=? where fid=?");
		builder.addParam(baseVerId);
		builder.addParam(info.getId().toString());
		builder.execute();
	}

	/**
	 * �����ύ�����ݵ�Ԥ����
	 * 
	 * @param ctx
	 * @param info
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected void handBeforeSaveAndSubmit(Context ctx, FDCScheduleInfo info) throws EASBizException, BOSException {
		if (info.getVersion() < 1f) {
			info.setVersion(1.0f);
		}
		saveExtPropertys(ctx, info);
		info.getScheduleDispColumns().clear();// TODO ��ʱ������
		// ����ʱ�������հ汾��ֻ�е������󣬲ű�Ϊ���հ汾��1.0��Ҳ������
		info.setIsLatestVer(false);
		info.setCreateReason(ScheduleCreateReasonEnum.InitVer);

		// // ������������ļ��Ρ��Ƿ���ϸ
		// FDCScheduleTaskCollection taskEntrys = info.getTaskEntrys();
		// if (taskEntrys != null) {
		// Map key = new HashMap();
		// for (int i = 0; i < taskEntrys.size(); i++) {
		// FDCScheduleTaskInfo taskInfo = taskEntrys.get(i);
		// key.put(taskInfo.getId(), new Integer(i));
		// taskInfo.setIsLeaf(true);
		// if (taskInfo.getParent() == null) {
		// taskInfo.setLevel(0);
		// } else {
		// Integer index = (Integer) key.get(taskInfo.getParent()
		// .getId());
		// FDCScheduleTaskInfo parentInfo = taskEntrys.get(index
		// .intValue());
		// parentInfo.setIsLeaf(false);
		// taskInfo.setLevel(parentInfo.getLevel() + 1);
		// }
		// }
		// }
		FDCScheduleTaskCollection taskEntrys = info.getTaskEntrys();
		Date project=CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(info.getProject().getId())).getStartDate();
		for (int i = 0; i < taskEntrys.size(); i++) {
			BigDecimal fromOpenDays = ScheduleCalendarHelper.getEffectTimes(project,taskEntrys.get(i).getEnd() , taskEntrys.get(i).getCalendar());
			taskEntrys.get(i).setFromOpenDays(fromOpenDays);
		}
	}

	/**
	 * �����������չ����
	 * 
	 * @param ctx
	 * @param info
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void saveExtPropertys(Context ctx, FDCScheduleInfo info) throws EASBizException, BOSException {
		if (true) {
			return;
		}
		FDCScheduleTaskExtCollection colls = new FDCScheduleTaskExtCollection();
		for (Iterator iter = info.getTaskEntrys().iterator(); iter.hasNext();) {
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) iter.next();
			FDCScheduleTaskExtInfo taskExt = task.getTaskExt();
			colls.add(taskExt);
		}
		if (colls.size() > 0) {
			CoreBaseCollection coll = (CoreBaseCollection) colls.cast(CoreBaseCollection.class);
			FDCScheduleTaskExtFactory.getLocalInstance(ctx).save(coll);
		}

		// �������û���˵�����
		FilterInfo filter = new FilterInfo();
		String filterSql = "select fid from T_sch_fdcscheduleTaskExt ext where not exists (select 1 from T_sch_fdcscheduletask where fid=ext.ftaskid and fscheduleId='"
				+ info.getId().toString() + "')";
		filter.getFilterItems().add(new FilterItemInfo("id", filterSql, CompareType.INNER));
		FDCScheduleTaskExtFactory.getLocalInstance(ctx).delete(filter);
	}

	protected void checkBeforeAddNew(Context ctx, FDCScheduleInfo info, String adminDeptId, String prjId) throws EASBizException, BOSException {
		// ����Ƿ��ظ� adminDeptId/prjId/taskTypeidΨһ
		String scheduleTypeId = info.getScheduleType().getId().toString();
		if (scheduleTypeId != null && scheduleTypeId.equals(TaskTypeInfo.TASKTYPE_MAINTASK)) {
			// ����ڵ���
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select 1 from T_SCH_FDCSchedule where fprojectId=? and fscheduleTypeId=? ");
			builder.addParam(prjId);
			builder.addParam(TaskTypeInfo.TASKTYPE_MAINTASK);
			if (builder.isExist()) {
				throw new EASBizException(new NumericExceptionSubItem("100", "�ƻ��Ѿ����ƹ���������������"));
			}

		} else {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select 1 from T_SCH_FDCSchedule where fprojectId=? and fadminDeptId=? and fscheduleTypeId=? ");
			builder.addParam(prjId);
			builder.addParam(adminDeptId);
			builder.addParam(scheduleTypeId);
			if (builder.isExist()) {
				throw new EASBizException(new NumericExceptionSubItem("100", "�ƻ��Ѿ����ƹ���������������"));
			}
		}
	}

	/*
	 * �����������۵Ľ����Ŀǰֻ�����������룬��������ֻ�������ר��
	 * ���ѡ���������������ڲ����Ǳ����ŵ�A������WBS���ڵ㼰�������ŵ�A���ϼ�WBS�ڵ�
	 * ���ѡ�����ר��������ڲ����Ǳ����ŵ����з������WBS�ڵ㣬�������ϼ��ڵ㣬�������ƻ�֮ǰҪ��������ϼ��ڵ�ļƻ��Ƿ��ѱ��Ʋ�����������
	 * 
	 * �����ĵڶ��㣬ר��������޸�Ϊ
	 * 
	 * �������������������һ�����û�У�����ʾû�пɱ༭������
	 */
	protected IObjectValue _getNewData(Context ctx, Map param) throws BOSException, EASBizException {
		FDCScheduleInfo info = new FDCScheduleInfo();

		String adminDeptId = (String) param.get("adminDeptId");// ��ڲ���
		String prjId = (String) param.get("prjId");// ������Ŀ
		String taskTypeId = (String) param.get("taskTypeId");// ����רҵ����
		if (param.containsKey("isVersionCompare")) {
			/***
			 * �汾�Ա�ȡ��
			 */
			String baseVerID = (String) param.get("baseVerID");
			String compVerID = (String) param.get("compVerID");
			FDCScheduleInfo baseVerInfo = ScheduleVerManagerFactory.getLocalInstance(ctx).getVerData(baseVerID);
			FDCScheduleInfo compVerInfo = ScheduleVerManagerFactory.getLocalInstance(ctx).getVerData(compVerID);

			return baseVerInfo;

		}

		if (taskTypeId != null && taskTypeId.equals(TaskTypeInfo.TASKTYPE_ADJUST)) {
			return getAdjustScheduleInfo(ctx, param);
		}
		if (taskTypeId != null && taskTypeId.equals(TaskTypeInfo.TASKTYPE_SPECIALTASK)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", prjId, CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTEQUALS));

			if (ScheduleAdjustGattReqFactory.getLocalInstance(ctx).exists(filter)) {
				throw new EASBizException(new NumericExceptionSubItem("100", "���ƻ����ڵ���������(����δ�����ĵ������뵥),��������ר��ƻ���"));
			}

		}

		// info.setScheduleType(FDCScheduleInfo.getScheduleType(taskTypeId));
		info.setScheduleType(TaskTypeFactory.getLocalInstance(ctx).getTaskTypeInfo(new ObjectUuidPK(BOSUuid.read(taskTypeId))));
		// ���������������������������Ա�������
		ScheduleCodingTypeInfo scheduleCodingType = new ScheduleCodingTypeInfo();
		if (TaskTypeInfo.TASKTYPE_MAINTASK.equals(info.getScheduleType().getId().toString())) {
			scheduleCodingType.setId(BOSUuid.read(ScheduleCodingTypeInfo.MAINTASK_CODINGID));
			scheduleCodingType.setNumber(ScheduleCodingTypeInfo.MAINTASK_CODINGNUM);
		} else {
			scheduleCodingType.setId(BOSUuid.read(ScheduleCodingTypeInfo.SPECIALTASK_CODINGID));
			scheduleCodingType.setNumber(ScheduleCodingTypeInfo.SPECIALTASK_CODINGNUM);
		}
		info.setScheduleCodingType(scheduleCodingType);

		checkBeforeAddNew(ctx, info, adminDeptId, prjId);

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("name");
		selector.add("codingNumber");
		selector.add("number");
		selector.add("projectPeriod");
		selector.add("longNumber");
		selector.add("startDate");
		CurProjectInfo prj = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(prjId), selector);
		FullOrgUnitInfo adminDept = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(new ObjectUuidPK(adminDeptId));

		info.setId(BOSUuid.create(info.getBOSType()));
		info.setProject(prj);
		info.setCreateTime(new Timestamp(System.currentTimeMillis()));
		info.setAdminDept(adminDept);

		info.setOrgUnit(prj.getFullOrgUnit()); // ��ʱ���ó���Ŀ������֯
		Date date = prj.getStartDate();// �Ժ�ȡ��Ŀ�����ϵ�ʱ��
		if (date == null) {
			date = new Date();
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date = cal.getTime();
		info.setStartDate(date);
		info.setViewDate(date);
		info.setGdivlocation(369);
		info.setRdivlocation(322);
		info.setGversion(FDCHelper.toBigDecimal("1.12"));
		info.setViewIndex(0);
		info.setColor("#DCB8F2");
		info.setIsLatestVer(true);
		info.setEditable(true);
		// ��Ŀ����
		ScheduleAppHelper.setCalendar(ctx, info);

		if (info.getCalendar() != null && info.getCalendar().isWeekTime(info.getStartDate())) {
			info.setStartDate(ScheduleCalendarHelper.getNextWorkDay(info.getStartDate(), info.getCalendar()));
		}

		return info;
	}

	/****
	 * ȡ����ʱ���ܼƻ�
	 * 
	 * ʵ�ַ���Ϊ�� 1����ȡ������ƻ���ר��ƻ� 2����������ƻ���ר��ƻ��������°汾���汾��Ϊ0����ʾ������
	 * 3������һ�����ܼƻ��汾0��������Ӧ���ܼƻ�����ר��ƻ� 4�����ذ汾Ϊ0���ܼƻ����ݣ���������ƻ���ר��ƻ�
	 * 5������ʱ����������ƻ���ר��ƻ����ֱ𱣴�
	 * 
	 * @param param
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private IObjectValue getAdjustScheduleInfo(Context ctx, Map param) throws EASBizException, BOSException {
		String taskTypeId = (String) param.get("taskTypeId");// ����רҵ����
		String adjustVerID = (String) param.get("adjustVerID");
		String prjId = (String) param.get("prjId");
		if (adjustVerID == null)
			return null;
		else {

			FDCScheduleInfo fdcSchedule = null;
			if (param.get("adjustReqId") != null) {
				String reqid = param.get("adjustReqId").toString();// ����ƻ������ڹ������б�����ύ
				// add by
				// warship
				// at
				// 2010/07
				// /30

				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("*");
				sic.add("adjustReason.*");
				sic.add("adjustReason.name");
				sic.add("adjustReason.number");
				sic.add("baseVer.id");
				sic.add("baseVer.version");
				sic.add("newVer.id");
				sic.add("newVer.version");
				sic.add("entrys.*");
				sic.add("entrys.wbs.*");
				sic.add("creator.id");
				sic.add("creator.name");
				sic.add("creator.number");
				sic.add("handler.id");
				sic.add("handler.name");
				sic.add("handler.number");
				ScheduleAdjustGattReqInfo reqInfo = ScheduleAdjustGattReqFactory.getLocalInstance(ctx).getScheduleAdjustGattReqInfo(
						new ObjectUuidPK(reqid), sic);

				fdcSchedule = ScheduleVerManagerFactory.getLocalInstance(ctx).getVerData(reqInfo.getNewVer().getId().toString());
				FDCScheduleInfo oldfdcSchedule = ScheduleVerManagerFactory.getLocalInstance(ctx).getVerData(reqInfo.getBaseVer().getId().toString());

				Map oldWbsTaskMap = new HashMap();
				for (Iterator it = oldfdcSchedule.getTaskEntrys().iterator(); it.hasNext();) {
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) it.next();
					oldWbsTaskMap.put(task.getWbs().getId().toString(), task);
				}

				Map wbsAdjustEntryMap = new HashMap();
				for (Iterator it = reqInfo.getEntrys().iterator(); it.hasNext();) {
					ScheduleAdjustGattReqWBSEntryInfo entry = (ScheduleAdjustGattReqWBSEntryInfo) it.next();
					wbsAdjustEntryMap.put(entry.getWbs().getId().toString(), entry);
				}
				fdcSchedule.put("wbsAdjustEntryMap", wbsAdjustEntryMap);
				fdcSchedule.put("AdjustInfo", reqInfo);
				for (Iterator it = fdcSchedule.getTaskEntrys().iterator(); it.hasNext();) {
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) it.next();
					task.setIsAdd(wbsAdjustEntryMap.containsKey(task.getWbs().getId().toString()));
					if (oldWbsTaskMap.containsKey(task.getWbs().getId().toString())) {
						FDCScheduleTaskInfo oldTask = (FDCScheduleTaskInfo) oldWbsTaskMap.get(task.getWbs().getId().toString());
						task.put("myOldStartDate", oldTask.getStart());
						task.put("myOldEndDate", oldTask.getEnd());
						task.put("myOldDuration", Integer.valueOf(String.valueOf(oldTask.getDuration())));
					} else {
						task.remove("myOldStartDate");
						task.remove("myOldEndDate");
						task.remove("myOldDuration");
					}
				}
			} else {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("project.id", prjId, CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("scheduleType.id", TaskTypeInfo.TASKTYPE_SPECIALTASK, CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("state", ScheduleStateEnum.SAVED_VALUE, CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("state", ScheduleStateEnum.SUBMITTED_VALUE, CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("state", ScheduleStateEnum.AUDITTING_VALUE, CompareType.EQUALS));
				filter.setMaskString("#0 and #1 and ( #2 or #3 or #4)");

				if (FDCScheduleFactory.getLocalInstance(ctx).exists(filter)) {
					throw new EASBizException(new NumericExceptionSubItem("100", "ר��ƻ����ڱ���(����δ����ר��ƻ�),���ܽ��е�����"));
				}
				fdcSchedule = ScheduleVerManagerFactory.getLocalInstance(ctx).getVerData(adjustVerID.toString());
			}
			return fdcSchedule;
		}
	}

	private void createTaskEntrys(Context ctx, FDCScheduleInfo info, FDCWBSCollection wbsColl, String baseVerId) throws BOSException, EASBizException {
		StopWatch sw = new StopWatch();
		sw.start();

	}

	protected IObjectValue _getScheduleInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		// ////////////////////////////////////////////////////////////////////////
		// ���������Ż� by skyiter_wang 2014-06-11
		Map _getScheduleInfoMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "_getScheduleInfo");
		// ////////////////////////////////////////////////////////////////////////
		
		EntityViewInfo view = ScheduleHelper.getScheduleView(pk.toString());
		FDCScheduleCollection col = getFDCScheduleCollection(ctx, view);
		FDCScheduleInfo schedule = col.get(0);
		if(schedule != null){
			afterGetSchedule(ctx, schedule);
		}
		
		// ////////////////////////////////////////////////////////////////////////
		// ���������Ż� by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "_getScheduleInfo", _getScheduleInfoMap);
		// ////////////////////////////////////////////////////////////////////////
		
		return schedule;
	}

	protected Map _cancel(Context ctx, Set ids) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule where (fstate<>? or fislatestver<>1) and ");
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		Object[] arrayIds = ids.toArray();
		builder.appendParam("fid", arrayIds);
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("111", "ֻ�ܷ�����ִ��״̬�����°汾�ļƻ�"));
		}

		builder.clear();
		builder.appendSql("update T_SCH_FDCSchedule set fstate=? where ");
		builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.appendParam("fid", arrayIds);
		builder.execute();

		// �������İ汾���ִ��״̬
		builder.clear();
		builder.appendSql("update T_SCH_ScheduleVerManager set fstate=? ");
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.appendSql(" where fid in (select fparentid from T_SCH_ScheduleVerManagerEntry where  ");
		builder.appendParam(" fscheduleid", arrayIds);
		builder.appendSql(" ) and fislatestver=1 ");
		builder.execute();
		return null;
	}

	/*
	 * add by warship at 2010/07/20 ���ƻ���״̬��ֻ����������״̬�ſ���ִ��
	 */
	protected void _checkScheduleState(Context ctx, Set ids) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule where (fstate<>? or fislatestver<>1) and ");
		builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.appendParam("fid", ids.toArray());
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("111", "ֻ��ִ������״̬�����°汾�ļƻ�"));
		}
	}

	protected Map _cancelCancel(Context ctx, Set ids) throws BOSException, EASBizException {
		// set state to executing

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// builder
		// .appendSql(
		// "select top 1 fid from T_SCH_FDCSchedule where (fstate<>? or fislatestver<>1) and "
		// );
		// builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		// builder.appendParam("fid", ids.toArray());
		// if (builder.isExist()) {
		// throw new EASBizException(new NumericExceptionSubItem("111",
		// "ֻ��ִ������״̬�����°汾�ļƻ�"));
		// }
		// builder.clear();
		builder.appendSql("update T_SCH_FDCSchedule set fstate=? where ");
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.appendParam("fid", ids.toArray());
		builder.execute();

		// �������°汾Ϊִ��״̬
		builder.clear();
		builder.appendSql("update T_SCH_ScheduleVerManager set fstate=? ");
		builder.appendSql(" where fid in (select distinct entry.fparentid from T_SCH_ScheduleVerManagerEntry entry ");
		builder.appendSql(" inner join t_sch_fdcschedule schedule on schedule.fid=entry.fscheduleid ");
		builder.appendSql(" where schedule.fstate=? ");
		builder.appendSql(" ) and fislatestver=1 and (fstate<>? or fstate is null)");
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.execute();
		return null;
	}

	protected Map _close(Context ctx, Set ids) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule where fstate<>? and ");
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.appendParam("fid", ids.toArray());
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("111", "ֻ�ܹر�ִ����״̬�µļƻ�"));
		}

		builder.clear();
		builder.appendSql("update T_SCH_FDCSchedule set fstate=? where fislatestver=1 and ");
		builder.addParam(ScheduleStateEnum.CLOSED_VALUE);
		builder.appendParam("fid", ids.toArray());
		builder.execute();
		return null;
	}

	protected Map _audit(Context ctx, Set ids) throws BOSException, EASBizException {
		checkBeforeAudit(ctx, ids);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		UserInfo currentUserInfo = ContextUtil.getCurrentUserInfo(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fstate=? ,fauditorid=? ,fauditTime=? where fislatestver=1 and ");
		builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.addParam(currentUserInfo.getId().toString());
		builder.addParam(new Date());
		builder.appendParam("fid", ids.toArray());
		builder.execute();
		// ������ʾ����Ϣ�Ժ����ͨ��Map����
		return null;
	}

	protected Map _unAudit(Context ctx, Set ids) throws BOSException, EASBizException {
		checkBeforeUnAudit(ctx, ids);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fstate=?,fauditorid=null ,fauditTime=null where fislatestver=1  and ");
		builder.addParam(ScheduleStateEnum.SUBMITTED_VALUE);
		builder.appendParam("fid", ids.toArray());
		builder.execute();
		// ������ʾ����Ϣ�Ժ����ͨ��Map����
		return null;
	}

	private void checkBeforeAudit(Context ctx, Set ids) throws BOSException, EASBizException {
		// must be submit
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule where fstate<>? and ");
		builder.addParam(ScheduleStateEnum.SUBMITTED_VALUE);
		builder.appendParam("fid", ids.toArray());
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("111", "���ڲ����ύ״̬�ļƻ�,��������"));
		}
	}

	private void checkBeforeUnAudit(Context ctx, Set ids) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule t where (fstate<>?  ");
		builder.appendSql("  or exists (select 1 from T_SCH_FDCSchedule where fprojectid=t.fprojectid and fversion=0)) and ");
		builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.appendParam("fid", ids.toArray());
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("111", "�Ѿ����ڵ������뵥,�üƻ�����������!"));
		}
		builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule t where (fstate<>?  ");
		builder.appendSql("  or exists (select 1 from T_SCH_FDCSchedule where fprojectid=t.fprojectid and fversion>1)) and ");
		builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.appendParam("fid", ids.toArray());
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("111", "���ڲ�������״̬���Ѿ��������ļƻ�,���ܷ�����!"));
		}

		// ���������ڵ�,���������Ƿ���ר��ڵ�,����в��÷�����
		builder.clear();
		builder.appendSql("select top 1 fid from T_SCH_FDCSchedule t where ");
		builder.appendParam("fid", ids.toArray());
		// /��������ڵ�
		builder.appendSql(" and exists (select 1 from T_SCH_FDCSchedule where fscheduletypeid=? and fid=t.fid) ");
		// /��ר��ڵ�ƻ�
		builder.appendSql(" and exists (select 1 from T_SCH_FDCSchedule where fscheduletypeid<>? and fprojectid=t.fprojectid )");
		builder.addParam(TaskTypeInfo.TASKTYPE_MAINTASK);
		builder.addParam(TaskTypeInfo.TASKTYPE_MAINTASK);
		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("112", "����ڵ�ƻ�������ר������ƻ�,���ܷ�����!"));
		}

		// �Ƿ񱻵���������
		builder.clear();
		builder.appendSql("select top 1 schedule.fid from T_SCH_FDCSchedule schedule ");
		builder.appendSql(" inner join T_SCH_FDCScheduleTask task on schedule.fid=task.fscheduleid ");
		builder.appendSql(" where ");
		builder.appendParam("schedule.fid", ids.toArray());
		// /��������
		builder.appendSql(" and (");
		builder.appendSql(" exists (select 1 from T_SCH_ScheduleAdjustTaskEntry where ftaskid=task.fid) ");
		// /��������
		builder.appendSql(" or exists (select 1 from T_SCH_ScheduleNewTaskEntry where fparentTaskId=task.fid )  )");

		if (builder.isExist()) {
			throw new EASBizException(new NumericExceptionSubItem("113", "�ƻ��Ѿ����й��������ܷ�����"));
		}
	}

	protected void _setExecuting(Context ctx, int day) throws BOSException, EASBizException {
		Calendar calendar = Calendar.getInstance();
		// int day=FDCHelper.toBigDecimal(days).intValue();
		calendar.add(Calendar.DAY_OF_MONTH, 1); // ������ȥ�Ƚ�
		calendar.add(Calendar.DAY_OF_MONTH, day * -1);// ��ǰ��
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_Sch_FDCSchedule set fstate=? where fstartDate<=? and fstate=? and fislatestver=1 ");
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.addParam(calendar.getTime());
		builder.addParam(ScheduleStateEnum.AUDITTED_VALUE);
		builder.execute();

		// �������°汾Ϊִ��״̬
		builder.clear();
		builder.appendSql("update T_SCH_ScheduleVerManager set fstate=? ");
		builder.appendSql(" where fid in (select distinct entry.fparentid from T_SCH_ScheduleVerManagerEntry entry ");
		builder.appendSql(" inner join t_sch_fdcschedule schedule on schedule.fid=entry.fscheduleid ");
		builder.appendSql(" where schedule.fstate=? ");
		builder.appendSql(" ) and fislatestver=1 and (fstate<>? or fstate is null)");
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.addParam(ScheduleStateEnum.EXETING_VALUE);
		builder.execute();
	}

	protected void _setStart(Context ctx, int days) throws BOSException, EASBizException {
		// �����ε�˵��,���þ���ִ��
		_setExecuting(ctx, days);
	}

	protected void _saveNewTask(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		FDCScheduleInfo schedule = (FDCScheduleInfo) model;
		if (schedule == null || schedule.getTaskEntrys().size() == 0) {
			return;
		}
		// save new task
		if (schedule.getCalendar() == null) {
			ScheduleAppHelper.setCalendar(ctx, schedule);
		}
		IORMappingDAO dao = ORMappingDAO.getInstance(schedule.getTaskEntrys().get(0).getBOSType(), ctx, getConnection(ctx));
		for (Iterator iter = schedule.getTaskEntrys().iterator(); iter.hasNext();) {
			FDCScheduleTaskInfo newTask = (FDCScheduleTaskInfo) iter.next();
			if (newTask.getId() != null) {
				dao.deleteBatch(new ObjectUuidPK(newTask.getId()));
			}
			dao.addNewBatch(newTask);
		}
		dao.executeBatch();

		// save new depends...
		// update schedule start/end
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select min(fstart) fstartDate,max(fend) fenddate from T_Sch_FDCScheduleTask where fscheduleId=? ");
		builder.addParam(schedule.getId().toString());
		IRowSet rowSet = builder.executeQuery();
		try {
			if (rowSet.next()) {
				Date startDate = rowSet.getDate("fstartDate");
				Date endDate = rowSet.getDate("fendDate");
				BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(startDate, endDate, schedule.getCalendar());
//				BigDecimal natureTimes = ScheduleCalendarHelper.getNatureTimes(startDate, endDate);
				builder.clear();
				builder.appendSql("update T_SCH_FDCSchedule set fstartDate=?,fendDate=?,feffectTimes=? where fid=?");
				builder.addParam(startDate);
				builder.addParam(endDate);
				builder.addParam(effectTimes);
//				builder.addParam(natureTimes);
				builder.addParam(schedule.getId().toString());
				builder.execute();
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
	}

	protected void _audit(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		
		//������һ�汾Ϊ�����հ汾
		FDCScheduleInfo info = getFDCScheduleInfo(ctx,new ObjectUuidPK(billID));
		float currVer = info.getVersion()-1f;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fislatestver = 0 where fprojectid = ?");
		builder.addParam(info.getProject().getId().toString());
		if(info.getProjectSpecial() != null){
			builder.appendSql(" and fprojectspecialid = ?");
			builder.addParam(info.getProjectSpecial().getId().toString());
		}else{
			builder.appendSql(" and fprojectspecialid is null");
		}
		builder.appendSql(" and fversion = ?");
		builder.addParam(Float.valueOf(currVer));
		builder.execute();
		
		builder.clear();
		builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fstate=?,fislatestver=1 where ");
		builder.addParam(ScheduleStateEnum.AUDITTED.getValue());
		builder.appendParam("fid", billID.toString());
		builder.execute();
		
		syscScheduleTask(ctx, billID);
        //����ȫ������(ȡ�ô˽��Ƚ����еļ�����������Ȼ����н���������)
		EntityViewInfo view = new EntityViewInfo();
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("parent.id");
		sic.add("parent.name");
		sic.add("parent.number");
		view.setSelector(sic);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule",billID.toString()));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		view.setFilter(filter);
		
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo sort = new SorterItemInfo("level");
		sort.setSortType(SortType.DESCEND);
		sorter.add(sort);
		sort = new SorterItemInfo("longnumber");
		sort.setSortType(SortType.DESCEND);
		sorter.add(sort);
		view.setSorter(sorter);
		
		FDCScheduleTaskCollection taskEntry = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		
		if(!taskEntry.isEmpty()){
			FDCScheduleTaskInfo task = taskEntry.get(0);
			int prevLevel = -1;
			String prevLongNumber = null;
			for(Iterator it = taskEntry.iterator();it.hasNext();){
				task = (FDCScheduleTaskInfo) it.next();
				if(task.getLevel() == -1){
					prevLevel = task.getLevel();
				}
				if(prevLongNumber == null){
					prevLongNumber = task.getLongNumber();
				}
				//һ���㼶������ֻ��Ҫ����һ�μ���
				if(task.getLevel()  == prevLevel && prevLongNumber.substring(0,prevLongNumber.lastIndexOf('.')).equals(task.getLongNumber().substring(0,task.getLongNumber().lastIndexOf('.'))) )
				{
					continue;
				}
				FDCScheduleCompleteHelper.updateAllTaskCompleteRate(ctx, task);
			}
			
		}
		
		
		// ��������������ʱ��
		builder.clear();
		builder.appendSql("update t_sch_fdcschedule set fauditorid=?,faudittime = now() where fid = ? ");
		builder.addParam(ContextUtil.getCurrentUserInfo(ctx).getId().toString());
		builder.addParam(billID.toString());
		builder.execute();
		
		updatePayPlanScheduleNode(ctx, billID);
	}

	/**
	 * �ʽ�ƻ����½��Ƚڵ㡣<br>
	 * ���ж��ʽ�ƻ�ģ��(��PayPlanNewFactory)�Ƿ����ڣ������ڲŵ���onScheduleChange�ӿڸ����ʽ�ƻ�������<br>
	 * �ȼ���ֱ�ӵ��� com.kingdee.eas.fdc.finance.PayPlanNewFactory.onScheduleChange(billID.toString())
	 * @author owen_wen 2014-04-03
	 */
	private void updatePayPlanScheduleNode(Context ctx, BOSUuid billID) throws BOSException {
		try {
			Class.forName("com.kingdee.eas.fdc.finance.PayPlanNewFactory");
			Object obj = BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A633823E"));
			FdcMethodUtil.invokeMethod(obj, "onScheduleChange", new Class[] { String.class }, new String[] { billID.toString() }, true);
		} catch (ClassNotFoundException e) {
			logger.info(e.getMessage());
		} 
	}
	
	/**
	 * @deprecated
	 * ������
	 * @param ctx
	 * @param info
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * �����ˣ�yuanjun_lan
	 * ����ʱ�䣺2011-11-28
	 */
	private String getPreviewSchedule(Context ctx,FDCScheduleInfo info) throws BOSException, SQLException{
		String scheduleId = null;
		float currVer = info.getVersion()-1f;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select fid  where fprojectid = ?");
		builder.addParam(info.getProject().getId().toString());
		if(info.getProjectSpecial() != null){
			builder.appendSql(" and fprojectspecialid = ?");
			builder.addParam(info.getProjectSpecial().getId().toString());
		}else{
			builder.appendSql(" and fprojectspecialid is null");
		}
		builder.appendSql(" and fversion = ?");
		builder.addParam(Float.valueOf(currVer));
		IRowSet rs = builder.executeQuery();
		while(rs.next()){
			scheduleId = rs.getString(1);
		}
		return scheduleId;
	}
	/**
	 * @deprecated
	 * ������
	 * @param ctx
	 * @param info
	 * @param prevID
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @throws EASBizException
	 * �����ˣ�yuanjun_lan
	 * ����ʱ�䣺2011-11-28
	 */
	private Set getNewTaskComparePreVersion(Context ctx,FDCScheduleInfo info,String prevID) throws BOSException, SQLException, EASBizException{
		Set newTaskSet = new HashSet();
		int newScheduleTaskCount = 0;
		int oldScheduleTaskCount = 0;
		boolean isDelete = false;
		boolean isAdd = false;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select count(*) from t_sch_fdcscheduletask where fscheduleid = ?");
		builder.addParam(info.getId().toString());
		IRowSet rs = builder.executeQuery();
		while(rs.next()){
			newScheduleTaskCount = rs.getInt(1);
		}
		
		builder.appendSql("select count(*) from t_sch_fdcscheduletask where fscheduleid = ?");
		builder.addParam(prevID);
		rs = builder.executeQuery();
		while(rs.next()){
			oldScheduleTaskCount = rs.getInt(1);
		}
		
		if(newScheduleTaskCount>oldScheduleTaskCount){
			isAdd = true;
		}else if(oldScheduleTaskCount>newScheduleTaskCount){
			isDelete = true;
		}
		StringBuffer sql = new StringBuffer("select fid from t_sch_fdcscheduletask where fsrcid in(select fsrcid from t_sch_fdcscheduletask where ");
		if(isAdd){
			sql.append("fscheduleid = '");
			sql.append(info.getId().toString());
			sql.append("'");
			sql.append(" and fsrcid not in( select fsrc from t_sch_fdcscheduletask where ");
			sql.append("scheduleid = '");
			sql.append(prevID);
			sql.append("'))");
			
		}
		
		if(isDelete){
			sql.append("fscheduleid = '");
			sql.append(prevID);
			sql.append("'");
			sql.append(" and fsrcid not in( select fsrc from t_sch_fdcscheduletask where ");
			sql.append("scheduleid = '");
			sql.append(info.getId().toString());
			sql.append("'))");
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule",sql.toString(),CompareType.INNER));
		FDCScheduleTaskCollection cols = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		
		FDCScheduleTaskInfo task = null;
		if(isAdd){
			for(Iterator it = cols.iterator();it.hasNext();){
				task = (FDCScheduleTaskInfo) it.next();
				FDCScheduleCompleteHelper.updateAllTaskCompleteRate(ctx, task);
			}
		}
		
		if(isDelete){
			for(Iterator it = cols.iterator();it.hasNext();){
				task = (FDCScheduleTaskInfo) it.next();
				
			}
		}
		
		return newTaskSet;
	}

	
	
	private void syscScheduleTask(Context ctx, BOSUuid billID) throws EASBizException, BOSException{
		/**����һ�汾�ϵ����������ʵ�ʿ������ڣ��깤������ͬ�����µ������������Ա�ִ�����㱨������*/
		FDCScheduleInfo scheduleInfo = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleInfo(new ObjectUuidPK(billID));
		float lastVersion = scheduleInfo.getVersion() - 1f;
		String projectId = scheduleInfo.getProject().getId().toString();
		if(lastVersion < 1f){
			return ;
		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		StringBuffer sql = new StringBuffer( "update t_sch_fdcscheduletask tartask set fcomplete = isnull((");
		sql.append("select fcomplete  from t_sch_fdcscheduletask task  right outer join t_sch_fdcschedule sch on task.fscheduleid = sch.fid ");
		sql.append("left outer join t_fdc_curproject prj on prj.fid = sch.fprojectid");
		sql.append(" where fversion = ? and sch.fprojectid = ? and tartask.fsrcid = task.fsrcid ");
		builder.appendSql(sql.toString());
		builder.addParam(lastVersion+"");
		builder.addParam(projectId);
		if(scheduleInfo.getProjectSpecial() == null){
			builder.appendSql("and (sch.fprojectspecialid is null or sch.fprojectspecialid = '') ),0)");
		}else{
			builder.appendSql("and sch.fprojectspecialid = ?),0)");
			builder.addParam(scheduleInfo.getProjectSpecial().getId().toString());
		}
		builder.appendSql(" where fscheduleid = ? ");
		builder.addParam(scheduleInfo.getId().toString());
		logger.info(builder.getTestSql());
		builder.execute();
		
		sql = new StringBuffer( "update t_sch_fdcscheduletask tartask set fworkload = isnull((");
		sql.append("select fworkload  from t_sch_fdcscheduletask task  right outer join t_sch_fdcschedule sch on task.fscheduleid = sch.fid ");
		sql.append("left outer join t_fdc_curproject prj on prj.fid = sch.fprojectid");
		sql.append(" where fversion = ? and sch.fprojectid = ? and tartask.fsrcid = task.fsrcid ");
		builder.appendSql(sql.toString());
		builder.addParam(lastVersion+"");
		builder.addParam(projectId);
		if(scheduleInfo.getProjectSpecial() == null){
			builder.appendSql("and (sch.fprojectspecialid is null or sch.fprojectspecialid = '')),0)");
		}else{
			builder.appendSql("and sch.fprojectspecialid = ?),0)");
			builder.addParam(scheduleInfo.getProjectSpecial().getId().toString());
		}
		builder.appendSql(" where fscheduleid = ? ");
		builder.addParam(scheduleInfo.getId().toString());
		logger.info(builder.getTestSql());
		builder.execute();
		
		builder.clear();
		sql = new StringBuffer( "update t_sch_fdcscheduletask tartask set FACTUALENDDATE = (");
		sql.append("select FACTUALENDDATE  from t_sch_fdcscheduletask task  right outer join t_sch_fdcschedule sch on task.fscheduleid = sch.fid ");
		sql.append("left outer join t_fdc_curproject prj on prj.fid = sch.fprojectid");
		sql.append(" where fversion = ? and sch.fprojectid = ? and tartask.fsrcid = task.fsrcid ");
		builder.appendSql(sql.toString());
		builder.addParam(lastVersion+"");
		builder.addParam(projectId);
		if(scheduleInfo.getProjectSpecial() == null){
			builder.appendSql("and (sch.fprojectspecialid is null or sch.fprojectspecialid = ''))");
		}else{
			builder.appendSql("and sch.fprojectspecialid = ?)");
			builder.addParam(scheduleInfo.getProjectSpecial().getId().toString());
		}
		builder.appendSql(" where fscheduleid = ?");
		builder.addParam(scheduleInfo.getId().toString());
		logger.info(builder.getTestSql());
		builder.execute();
		
		
		
		builder.clear();
		sql = new StringBuffer( "update t_sch_fdcscheduletask tartask set FACTUALSTARTDATE = (");
		sql.append("select FACTUALSTARTDATE  from t_sch_fdcscheduletask task  right outer join t_sch_fdcschedule sch on task.fscheduleid = sch.fid ");
		sql.append("left outer join t_fdc_curproject prj on prj.fid = sch.fprojectid");
		sql.append(" where fversion = ? and sch.fprojectid = ? and tartask.fsrcid = task.fsrcid ");
		builder.appendSql(sql.toString());
		builder.addParam(lastVersion+"");
		builder.addParam(projectId);
		if(scheduleInfo.getProjectSpecial() == null){
			builder.appendSql("and (sch.fprojectspecialid is null or sch.fprojectspecialid = ''))");
		}else{
			builder.appendSql("and sch.fprojectspecialid = ?)");
			builder.addParam(scheduleInfo.getProjectSpecial().getId().toString());
		}
		builder.appendSql(" where fscheduleid = ?");
		builder.addParam(scheduleInfo.getId().toString());
		logger.info(builder.getTestSql());
		builder.execute();
		
		
		builder.clear();
		// ��֪��ΪɶҪͬ�����°汾��������޸ĵĻ�������ط��ǲ���ͬ���ģ�����
		sql = new StringBuffer( "update t_sch_fdcscheduletask tartask set FDEPENDMAINTASKID = (");
		sql.append("select FDEPENDMAINTASKID  from t_sch_fdcscheduletask task  right outer join t_sch_fdcschedule sch on task.fscheduleid = sch.fid ");
		sql.append("left outer join t_fdc_curproject prj on prj.fid = sch.fprojectid");
		sql.append(" where fversion = ? and sch.fprojectid = ? and tartask.fsrcid = task.fsrcid ");
		builder.appendSql(sql.toString());
		builder.addParam(lastVersion+"");
		builder.addParam(projectId);
		if(scheduleInfo.getProjectSpecial() == null){
			builder.appendSql("and (sch.fprojectspecialid is null or sch.fprojectspecialid = ''))");
		}else{
			builder.appendSql("and sch.fprojectspecialid = ?)");
			builder.addParam(scheduleInfo.getProjectSpecial().getId().toString());
		}
		builder.appendSql(" where fscheduleid = ?");
		builder.addParam(scheduleInfo.getId().toString());
		logger.info(builder.getTestSql());
		// builder.execute();
		
	}

	protected void _setAudittingStatus(Context ctx, BOSUuid billID) throws BOSException, EASBizException {

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fstate=? where ");
		builder.addParam(ScheduleStateEnum.AUDITTING.getValue());
		builder.appendParam("fid", billID.toString());
		builder.execute();
	}

	protected void _setSubmitStatus(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fstate=? where ");
		builder.addParam(ScheduleStateEnum.SUBMITTED.getValue());
		builder.appendParam("fid", billID.toString());
		builder.execute();

	}

	protected void _unAudit(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		FDCScheduleInfo schedule = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleInfo(new ObjectUuidPK(billID));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", schedule.getProject().getId().toString(), CompareType.EQUALS));
		if (ScheduleAdjustGattReqFactory.getLocalInstance(ctx).exists(filter)) {
			throw new EASBizException(new NumericExceptionSubItem("100", "�Ѿ����ڵ������뵥,�üƻ�������������"));
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_SCH_FDCSchedule set fstate=? where ");
		builder.addParam(ScheduleStateEnum.SUBMITTED.getValue());
		builder.appendParam("fid", billID.toString());
		builder.execute();

	}

	protected void _unClose(Context ctx, Set idSet) throws BOSException, EASBizException {

	}

	protected void _setSendMsg(Context ctx, int days) throws BOSException, EASBizException {
		// TODO ����ִ���յ���Ϣ����
	}

	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		FDCScheduleNumberHandler.recycleNumber(ctx, pk, _getValue(ctx, pk));
	}

	/**
	 * 
	 * @description ���ϺŴ���
	 * @author �ź���
	 * @createDate 2011-9-2
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException void
	 * @version EAS7.0
	 * @see
	 */
	protected void handleIntermitNumber(Context ctx, FDCScheduleInfo info) throws BOSException, CodingRuleException, EASBizException {
		FDCScheduleNumberHandler.execute(ctx, info);
	}

	/**
	 *�ƻ��������½����鿴ʱ ��ȡ�ƻ�����
	 * 
	 * @param isMainSchedule
	 *            �Ƿ�����ƻ�
	 *@param isAdjust
	 *            �Ƿ��������ԭ��
	 *@param project
	 *            ������Ŀ
	 *@param projectSpecial
	 *            ��Ŀר��
	 *@return FDCScheduleInfo
	 * @throws EASBizException
	 */
	protected IObjectValue _getScheduleInfo(Context ctx, boolean isMainSchedule, boolean isAdjust, IObjectValue project, IObjectValue projectSpecial,Map param)
			throws BOSException, EASBizException {
		// ////////////////////////////////////////////////////////////////////////
		// ���������Ż� by skyiter_wang 2014-06-11
		Map _getScheduleInfoMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "_getScheduleInfo");
		// ////////////////////////////////////////////////////////////////////////
		
		boolean isFormExecuteUI = false;
		if(param != null && param.get("isFormExecuteUI") != null){
			isFormExecuteUI = Boolean.valueOf(param.get("isFormExecuteUI").toString()).booleanValue();
		}
		boolean isTotalUI = false;
		if (param != null && param.get("isFormTotalUI") != null) {
			isTotalUI = Boolean.valueOf(param.get("isFormTotalUI").toString())
					.booleanValue();
		}
		CurProjectInfo curProject = getProject(ctx, isMainSchedule, (CurProjectInfo) project);
		ProjectSpecialInfo special = isMainSchedule ? null : getSpecial(ctx, (ProjectSpecialInfo) projectSpecial);
		if(!isMainSchedule && special == null){
			return createNewSchedule(ctx, curProject, special);
		}
		if (curProject != null) {
			FDCScheduleInfo schedule = null;
			if (isTotalUI) {
				try {
					schedule = getTotalSchedule(ctx, curProject);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				schedule = getFDCSchedule(ctx, curProject, special,
						isFormExecuteUI);
				if (schedule != null && special != null) {
					SpecialScheduleUtils.reBuilderSpecialSchedule(ctx, curProject, special, schedule);
					
				}
			}
			if (schedule != null) {
				// ����ǰ������
				// handleTasks(schedule);
				// ��ȡ��һ�汾��Ϣ���ڰ汾�Ա�
				afterGetSchedule(ctx,schedule);
				return schedule;
			}
		}
		
		IObjectValue objectValue = createNewSchedule(ctx, curProject, special);

		// ////////////////////////////////////////////////////////////////////////
		// ���������Ż� by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "_getScheduleInfo", _getScheduleInfoMap);
		// ////////////////////////////////////////////////////////////////////////

		return objectValue;
	}
	
	private void afterGetSchedule(Context ctx,FDCScheduleInfo schedule) throws BOSException, EASBizException{
		
		putOldDateOfTask(ctx, schedule);
		// ҵ�����Ͷ�ѡʱ�ö������ӳ�һ���ֶ�
		putBizType(schedule);
		// ִ��ʱ��Ҫ��������������ÿһ�������״̬
		try {
			FDCScheduleTaskStateHelper.putState(ctx, schedule);
		} catch (SQLException e) {
			throw new BOSException(e.getCause());
		}
		// ȡ�ù�����ͬ
		putReloationContract(ctx, schedule);
	}

	/**
	 * ȡ�ܽ��ȼƻ�
	 * <p>
	 * 1����ȡ���°汾������ƻ�<br>
	 * 2��ȡ���й����˵����ר����Ӽ�ID<br>
	 * 3������IDȡ������Info<br>
	 * 4������Щ������ӵ���Ӧ��������<br>
	 * 5������seq��ʹ�������
	 * 
	 * @param ctx
	 * @param curProject
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	protected FDCScheduleInfo getTotalSchedule(Context ctx,
			CurProjectInfo curProject) throws BOSException, SQLException {
		if (curProject == null) {
			return null;
		}
		// 1����ȡ���°汾������ƻ�
		EntityViewInfo view = ScheduleHelper.getScheduleView();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", curProject.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
		view.setFilter(filter);
		FDCScheduleCollection col = getFDCScheduleCollection(ctx, view);
		if (col != null && col.size() > 0) {
			FDCScheduleInfo scheduleInfo = col.get(0);
			// 2��ȡ���й����˵����ר����Ӽ�ID
			FDCScheduleTaskCollection mainScheduleTasks = scheduleInfo.getTaskEntrys();
			Set tasks = new HashSet();
			for (int i = 0; i < mainScheduleTasks.size(); i++) {
				FDCScheduleTaskInfo entryInfo = mainScheduleTasks.get(i);
				entryInfo.put("totalSchedule", "main");
				tasks.add(entryInfo.getId().toString());
			}
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			sql.appendSql(" select task.FID as taskID ");
			sql.appendSql(" from T_SCH_FDCScheduleTask as task ");
			sql.appendSql(" left join ( ");
			sql.appendSql(" select sTask.FScheduleID as schID ");
			sql.appendSql(" ,sTask.FLongNumber as taskNum ");
			sql.appendSql(" from T_SCH_FDCScheduleTask as sTask ");
			sql.appendSql(" left join T_SCH_FDCScheduleTask as mTask ");
			sql.appendSql(" on mTask.FSRCID = sTask.FDependMainTaskID ");
			sql.appendSql(" left join T_SCH_FDCSchedule ssch on ssch.fid = sTask.fscheduleid ");
			sql.appendSql(" where ssch.fislatestver = 1 and  mTask.FID in ");
			sql.appendSql(FMHelper.setTran2String(tasks));
			sql.appendSql(" ) as rel ");
			sql.appendSql(" on task.FScheduleID = rel.SchID ");
			sql.appendSql(" where rel.SchID is not null and ");
			sql.appendSql(" task.FLongNumber like concat(rel.taskNum ,'%') ");
			IRowSet rs = sql.executeQuery();
			tasks.clear();
			while (rs.next()) {
				tasks.add(rs.getString("taskID"));
			}
			// 3������IDȡ������Info
			view = new EntityViewInfo();
			view.setSelector(ScheduleHelper.getTaskSelector());
			//��Щ�ͻ����������ֶ�("schedule.id")��������ڲ�ѯ�ֶ��У����������ʧЧ Added by Owen_wen 2013-11-12
			view.getSelector().add("schedule.id"); 
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", tasks, CompareType.INCLUDE));
			view.setFilter(filter);
			SorterItemCollection sorter = new SorterItemCollection();
			SorterItemInfo sortSch = new SorterItemInfo("schedule.id");
			SorterItemInfo sortNum = new SorterItemInfo("longNumber");
			sorter.add(sortSch);
			sorter.add(sortNum);
			view.setSorter(sorter);
			FDCScheduleTaskCollection specialTasks = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
			if (specialTasks != null) {
				// 4������Щ������ӵ���Ӧ��������
				int index = 0;
				for (int i = 0; i < specialTasks.size(); i++) {
					FDCScheduleTaskInfo specialTask = specialTasks.get(i);
					FDCScheduleTaskInfo mainTask = specialTask.getDependMainTaskID();
					if (mainTask != null) {
						// ������Ϊ�գ���Ѹ�������ӵ���������֮��
						for (int j = 0; j < mainScheduleTasks.size(); j++) {
							FDCScheduleTaskInfo mainInfo = mainScheduleTasks.get(j);
							if (mainTask.getId() != null && mainTask.getId().toString().equals(mainInfo.getSrcID().toString())) {
								specialTask.setParent(mainInfo);
								specialTask.getDependEntrys().clear();
								specialTask.put("totalSchedule", "special");
								mainScheduleTasks.addObject(j + 1, specialTask);
								index = j + 1;
								break;
							}
						}
					} else {
						// �������Ϊ�գ�˵������һ��������Ϊ������������񣬼ӵ���һ��֮��
						specialTask.getDependEntrys().clear();
						specialTask.put("totalSchedule", "special");
						mainScheduleTasks.addObject(++index, specialTask);
					}
				}
				// 5������Seq��ʹ�������
				for (int i = 0; i < mainScheduleTasks.size(); i++) {
					mainScheduleTasks.get(i).setSeq(i + 1);
				}
			}
			return scheduleInfo;
		}
		return null;
	}

	public boolean getCurrentTaskStage(FDCScheduleTaskInfo info ){
		return false;
	}
	

	/**
	 * 
	 * @description ��ҵ�����ʹ���
	 * @author �ź���
	 * @createDate 2011-9-26
	 * @param schedule
	 *            void
	 * @version EAS7.0
	 * @see
	 */
	protected void putBizType(FDCScheduleInfo schedule) {
		if (schedule.getTaskEntrys() != null) {
			FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
			for (int i = 0; i < taskEntrys.size(); i++) {
				FDCScheduleTaskInfo scheduleTaskInfo = taskEntrys.get(i);
				ScheduleTaskBizTypeCollection bizTypes = scheduleTaskInfo.getBizType();
				if (bizTypes != null && bizTypes.size() > 0) {
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < bizTypes.size(); j++) {
						ScheduleTaskBizTypeInfo bizType = bizTypes.get(j);
						
						if (j > 0) {
							if(bizType.getBizType() != null && bizType.getBizType().getName()!=null){
								sb.append(","); 
								sb.append(bizType.getBizType().getName());
							}
						} else {
							sb.append(bizType.getBizType()==null?null:bizType.getBizType().getName());
						}
					}
					scheduleTaskInfo.put("taskBizType", sb.toString());
				}
			}
		}
	}

	/**
	 * 
	 * @description ��ȡ����������
	 * @author �ź���
	 * @createDate 2011-9-2
	 * @param schedule
	 *            void
	 * @version EAS7.0*
	 * @throws BOSException 
	 * @see
	 */
	private void handleTasks(FDCScheduleInfo schedule) throws BOSException {
		FDCScheduleTaskCollection newTasks = new FDCScheduleTaskCollection();
		FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
		newTasks = getRelationReverseTasks(newTasks, taskEntrys);
		schedule.getTaskEntrys().addCollection(newTasks);
	}



	/**
	 * 
	 * @description ����ϰ汾��������ڷ������ڵ����������
	 * @author �ź���
	 * @createDate 2011-9-2
	 * @param schedule
	 *            void
	 * @version EAS7.0
	 * @throws BOSException
	 * @see
	 */
	protected void putOldDateOfTask(Context ctx, FDCScheduleInfo schedule) throws BOSException {
		FDCScheduleInfo oldSchedule = null;
		if (schedule != null && schedule.getVersion() > 1f) {
			oldSchedule = (FDCScheduleInfo) _getSchedule4Compare(ctx, null, schedule.getId().toString());
			FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
			if (oldSchedule != null) {
			FDCScheduleTaskCollection oldTasks = oldSchedule.getTaskEntrys();
			for (int i = 0; i < taskEntrys.size(); i++) {
				String id = taskEntrys.get(i).getSrcID();
				FDCScheduleTaskInfo task = schedule.getTaskEntrys().get(i);
				for (int j = 0; j < oldTasks.size(); j++) {
					FDCScheduleTaskInfo oldTask = oldTasks.get(j);
					if (null != id && null != oldTask && null != oldTask.getSrcID() && oldTask.getSrcID().toString().equals(id)) {
						task.put("myOldStartDate", oldTask.getStart());
						task.put("myOldEndDate", oldTask.getEnd());
						task.put("myOldCheckDate", oldTask.getCheckDate()); 
					}
				}
			}
			}
		}
		
	}

	/**
	 * ����ǰ���û���
	 * <p>
	 * ���ڸ���ͼ���ǰ�ǰ��������ʾ���������ݿⱣ���ǰ���������棬��������Ҫ��λ
	 * 
	 * @author �ź���
	 * @createDate 2011-9-2
	 * @param newTasks
	 * @param taskEntrys
	 * @return FDCScheduleTaskCollection
	 * @version EAS7.0
	 */
	private FDCScheduleTaskCollection getRelationReverseTasks(FDCScheduleTaskCollection newTasks, FDCScheduleTaskCollection taskEntrys) {
		/** �����������е�ǰ��������������� **/
		for (int i = 0; i < taskEntrys.size(); i++) {
			FDCScheduleTaskInfo task = taskEntrys.get(i);
			FDCScheduleTaskInfo clone = (FDCScheduleTaskInfo) task.clone();
			clone.getDepends().clear();
			newTasks.add(clone);
		}

		for (int i = 0; i < taskEntrys.size(); i++) {
			FDCScheduleTaskInfo task = taskEntrys.get(i);
			ScheduleTaskDependCollection depends = task.getDepends();
			/** ѭ�������е�ǰ������ **/
			for (int j = 0; j < depends.size(); j++) {
				ScheduleTaskDependInfo depend = depends.get(j);
				ScheduleTaskDependInfo clone = (ScheduleTaskDependInfo) depend.clone();
				/** �ڿ�¡��ǰ�������з������ģ������ǰ������(��ʱǰ�������зŵ���ǰ������,ǰ�������зŵ�����) **/
				clone.put("task", depend.getDependTaskBase());
				clone.put("dependTask", depend.getTaskBase());
				newTasks.get(i).getDepends().add(clone);
			}
		}
		return newTasks;
	}

	/**
	 * ȡ���������ͬ
	 * <p>
	 * ������ͬ���󶨵�Դ�����ϣ�����ֻȡ�ƻ�ԴID�뵱ǰ�ƻ�ԴID��ͬ�ļ�¼
	 * 
	 * @param ctx
	 * @param schedule
	 * @throws BOSException
	 */
	protected void putReloationContract(Context ctx, FDCScheduleInfo schedule)
			throws BOSException {
		// ȡ�õ�ǰ�ƻ������к�ͬ��ϵ
		String schSrcID = schedule.getSrcID();
		if (FDCHelper.isEmpty(schSrcID)) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("task.id");
		sic.add("contract.id");
		sic.add("contract.number");
		sic.add("contract.name");
		sic.add("contract.originalAmount");
		sic.add("contract.respPerson.id");
		sic.add("contract.respPerson.number");
		sic.add("contract.respPerson.name");
		sic.add("contract.respDept.id");
		sic.add("contract.respDept.number");
		sic.add("contract.respDept.name");
		sic.add("contract.partB.id");
		sic.add("contract.partB.number");
		sic.add("contract.partB.name");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("task.schedule.srcID", schSrcID));
		view.setFilter(filter);
		SorterItemInfo sort = new SorterItemInfo("task.id");
		view.getSorter().add(sort);
		FDCSchTaskWithContractCollection relConCol = FDCSchTaskWithContractFactory
				.getLocalInstance(ctx)
				.getFDCSchTaskWithContractCollection(view);
		// ����ϵput����Ӧ������
		if (relConCol != null && relConCol.size() > 0) {
			Map relMap = new HashMap();
			for (int i = 0; i < relConCol.size(); i++) {
				FDCSchTaskWithContractInfo relInfo = relConCol.get(i);
				String taskID = relInfo.getTask().getId().toString();
				if (relMap.containsKey(taskID)) {
					List rel = (List) relMap.get(taskID);
					rel.add(relInfo);
				} else {
					List rel = new ArrayList();
					rel.add(relInfo);
					relMap.put(taskID, rel);
				}
			}
			for (int i = 0; i < schedule.getTaskEntrys().size(); i++) {
				FDCScheduleTaskInfo taskInfo = schedule.getTaskEntrys().get(i);
				taskInfo.put("relCon", relMap.get(taskInfo.getId().toString()));
			}
		}
	}

	protected IObjectValue createNewSchedule(Context ctx, CurProjectInfo project, ProjectSpecialInfo projectSpecial) throws BOSException,
			EASBizException {
		FDCScheduleInfo info = new FDCScheduleInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		info.setProject(project);
		info.setProjectSpecial(projectSpecial);
		// ����Ĭ������ add by emanon
		if (project != null) {
			info.setCalendar(ScheduleCalendarFactory.getLocalInstance(ctx)
					.getDefaultCal(project.getId().toString()));
		}
		/** ��һ�δ�����ʱ�����ý��ȵ�srcID **/
		info.setSrcID(info.getId().toString());
		// TODO
		/** �������� **/
		// ScheduleAppHelper.setCalendar(ctx, info);
		/** �п���û�й�����Ŀ���� modfiy by duhongming 2011-08-25 **/
		// if (project == null) {
		info.setViewDate(new Date());
		info.setStartDate(new Date());
		// }
		info.setState(ScheduleStateEnum.SAVED);
		// TODO ���û�������
		info.setVersion(1f);
		info.setEditable(true);
		info.setIsLatestVer(false);
		info.setVersionDate(new Date());
		info.setAdminDept((FullOrgUnitInfo) SysContext.getSysContext().getCurrentOrgUnit());
		info.setAdminPerson((PersonInfo) (SysContext.getSysContext().getCurrentUser()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (project != null && projectSpecial != null) {
			info.setVersionName(project.getName() + projectSpecial.getName()
					+ sdf.format(new Date()) + "_"
					+ new Float(info.getVersion()) + "��");
		}
		if (project != null && projectSpecial == null) {
			info.setVersionName(project.getName() + sdf.format(new Date())
					+ "_" + new Float(info.getVersion()) + "��");
		} else {
			info.setVersionName(sdf.format(new Date()) + "_"
					+ new Float(info.getVersion()) + "��");
		}
		if(projectSpecial != null){
			SpecialScheduleUtils.reBuilderSpecialSchedule(ctx, project, projectSpecial, info);
		}
		return info;
	}

	/**
	 * 
	 * @description ��ȡ����
	 * @author �ź���
	 * @createDate 2011-9-2
	 * @param ctx
	 * @param project
	 * @param projectSpecial
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 *             FDCScheduleInfo
	 * @version EAS7.0
	 * @see
	 */
	protected FDCScheduleInfo getFDCSchedule(Context ctx, CurProjectInfo project, ProjectSpecialInfo projectSpecial,boolean isFormExecute) throws BOSException,
			EASBizException {
		/** ������°汾�Ľ���ID **/
		String billID = getNewestVersionScheduleID(ctx, project, projectSpecial,isFormExecute);
		if (FDCHelper.isEmpty(billID)) {
			return null;
		}
		FDCScheduleCollection col = getScheduleByID(ctx, billID);
		if (col == null || col.isEmpty()) {
			return null;
		} else if (col != null && col.size() == 1) {
			return col.get(0);
		} else {
			// TODO �׳�ҵ���쳣����ʾ"ͬһ�汾���ݴ��ڶ�����������ϵͳ����Ա�޸�����"
			throw new EASBizException(EASBizException.CHECKEXIST);
		}
	}

	/**
	 * 
	 * @description ����ID��ȡ����
	 * @author �ź���
	 * @createDate 2011-9-2
	 * @param ctx
	 * @param billID
	 * @return
	 * @throws BOSException
	 *             FDCScheduleCollection
	 * @version EAS7.0
	 * @see
	 */
	private FDCScheduleCollection getScheduleByID(Context ctx, String billID) throws BOSException {
		/** �Ѳ�ѯ�����һ��������,�������ɾ����ѯ�ֶ� modify by duhongming 2011-09-02 **/
		EntityViewInfo view = ScheduleHelper.getScheduleView(billID);
		FDCScheduleCollection col = getFDCScheduleCollection(ctx, view);
		return col;
	}

	/**
	 * ������°汾
	 * 
	 * @author �ź���
	 * @createDate 2011-9-2
	 * @param ctx
	 * @param project
	 * @param projectSpecial
	 * @return String
	 * @throws BOSException
	 * @version EAS7.0
	 */
	private String getNewestVersionScheduleID(Context ctx, CurProjectInfo project, ProjectSpecialInfo projectSpecial, boolean isFormExecute)
			throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select top 1 fid as id from T_SCH_FDCSCHEDULE where ");
		if (projectSpecial == null) {
			builder.appendSql("(FProjectSpecialID is null or FProjectSpecialID = '') ");
		} else {
			builder.appendParam("FProjectSpecialID", projectSpecial.getId().toString());
		}
		if (isFormExecute) {
			builder.appendSql(" and fislatestver = 1");
			builder.appendSql(" and fstate = '4AUDITTED'");
		}
		builder.appendSql(" and FProjectID='" + project.getId().toString() + "'");
		builder.appendSql(" order by fversion desc");
		IRowSet rowSet = builder.executeQuery();
		String billID = "";
		try {
			if (rowSet.next()) {
				billID = rowSet.getString("id");
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return billID;
	}

	protected ProjectSpecialInfo getSpecial(Context ctx, ProjectSpecialInfo projectSpecial) throws EASBizException, BOSException {
		if (projectSpecial == null) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("name");
			selector.add("number");
			String historyProjectId = getAutoRememberProject(ctx, "SpecialScheduleProject");
			if(!StringUtils.isEmpty(historyProjectId) && historyProjectId.indexOf('!')>-1){
				String [] specialProject = historyProjectId.split("!");
				return ProjectSpecialFactory.getLocalInstance(ctx).getProjectSpecialInfo(new ObjectUuidPK(specialProject[1]));
			}else{
				return null;
			}
//			if (FDCHelper.isEmpty(historyProjectId)) {
//				return null;
//			}else{
//			 return (ProjectSpecialInfo) ProjectSpecialFactory.getLocalInstance(ctx).getProjectSpecialInfo(new ObjectStringPK(historyProjectId),selector);}
		}
		return projectSpecial;
	}

	/**
	 * 
	 * @description ��ѯ������Ŀ
	 * @author �ź���
	 * @createDate 2011-9-2
	 * @param ctx
	 * @param isMainProject
	 * @param isMainSchedule
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 *             Object
	 * @version EAS7.0
	 * @see
	 */
	private Object queryProject(Context ctx, boolean isMainProject, boolean isMainSchedule) throws BOSException, EASBizException {
		String function = isMainSchedule ? "MainScheduleProject" : "SpecialScheduleProject";
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("name");
		selector.add("number");
		selector.add("costcenter.id");
		selector.add("costcenter.name");
		String historyProjectId = getAutoRememberProject(ctx, function);
		
		if (FDCHelper.isEmpty(historyProjectId)) {
			return null;
		}else if(historyProjectId.indexOf('!')>-1){
			String [] tempStr = historyProjectId.split("!");
			historyProjectId = tempStr[0];
		}
		
		if (isMainProject) {
			selector.add("startDate");
			selector.add("fullOrgUnit");
		}
		return (CurProjectInfo) CurProjectFactory.getLocalInstance(ctx).getProjectInfo(new ObjectStringPK(historyProjectId), selector);
	}

	protected CurProjectInfo getProject(Context ctx, boolean isMainSchedule, CurProjectInfo project) throws EASBizException, BOSException {
		if (project == null) {
			Object obj = queryProject(ctx, true, isMainSchedule);
			if (obj != null) {
				return (CurProjectInfo) obj;
			}
		}
		return project;
	}

	/**
	 * 
	 * @description ��ü��乤����Ŀ
	 * @author �ź���
	 * @createDate 2011-9-2
	 * @param ctx
	 * @param function
	 * @return
	 * @throws BOSException
	 *             String
	 * @version EAS7.0
	 * @see
	 */
	private String getAutoRememberProject(Context ctx, String function) throws BOSException {
		String userID = ContextUtil.getCurrentUserInfo(ctx).getId().toString();
		String orgUnitID = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		return REAutoRememberFactory.getLocalInstance(ctx).getValue(userID, orgUnitID, function);
	}

	/**
	 * @description �汾�Ա�
	 * @author �ź���
	 * @createDate 2011-9-13
	 * @version EAS7.0
	 * @param baseVerID ��׼�汾ID
	 * @param compareVerID �Ƚϰ汾ID
	 * @see
	 */

	protected IObjectValue _getSchedule4Compare(Context ctx, String baseVerID, String compareVerID) throws BOSException {
		FDCScheduleInfo compareVerSchedule;
		FDCScheduleInfo baseVerSchedule;
		compareVerSchedule = getSchedule4Compare(compareVerID, ctx);// ��ȡ�Ƚϰ汾�ƻ�
		if(baseVerID == null&&compareVerSchedule!=null){
		    baseVerID = getBaseVerID(ctx, compareVerSchedule.getSrcID(), compareVerSchedule.getVersion() - 1f);
		}
		baseVerSchedule = getSchedule4Compare(baseVerID, ctx);// ��ȡ��׼�汾�ƻ�

		Map srcId2BaseSchTask = getSrcId2SchTaskMap(baseVerSchedule);//��ȡ����Դid����������䡣��ͬ
		Map srcId2CompareSchTask = getSrcId2SchTaskMap(compareVerSchedule);

		// ��׼�汾�ƻ���������
		for (int i=0; i < srcId2BaseSchTask.size(); ++i){
		// ѭ����׼�汾��map������ڻ�׼�汾�������ҵ���һ���Ƚϱ�������,˵���Ƚϰ汾����������ݱ仯�ˣ�
			// ���ǻ�ȡ�Ƚϰ汾�����ݷ��뵽��׼���������
			FDCScheduleTaskInfo baseVerScheduleTask = baseVerSchedule.getTaskEntrys().get(i);
			FDCScheduleTaskInfo compareTask = (FDCScheduleTaskInfo) srcId2CompareSchTask.get(baseVerScheduleTask.getSrcID());
			if (compareTask != null) {
				// ��׼�汾����
				baseVerScheduleTask.put("MyOldStartDate", compareTask.getStart());
				baseVerScheduleTask.put("MyOldEndDate", compareTask.getEnd());
				baseVerScheduleTask.put("MyOldDuration", new Integer(compareTask.getDuration()));
				baseVerScheduleTask.put("MyOldCheckDate", compareTask.getCheckDate());
			} else {
				baseVerScheduleTask.put("type", "delete");
			}
			
		}
		// ѭ���Ƚ�map,����ڻ�׼�汾���治�ܻ�ñȽϱ�����������,˵��������������,���Ƿ��뵽��׼�汾����
		for (int i=0; i < srcId2CompareSchTask.size(); ++i){
			FDCScheduleTaskInfo compareVerScheduleTask = compareVerSchedule.getTaskEntrys().get(i);
			if (srcId2BaseSchTask.get(compareVerScheduleTask.getSrcID()) == null) {
				/** �ڻ�׼�汾��ӱȽϱ������������� **/
				compareVerScheduleTask.put("type", "newTask");
				if (baseVerSchedule != null && baseVerSchedule.getTaskEntrys() != null) {
					baseVerSchedule.getTaskEntrys().add(compareVerScheduleTask);
				}
				/** ɾ���Ƚϰ汾�ƻ������ǰ������ **/
				if (compareVerScheduleTask != null && compareVerScheduleTask.getDependEntrys() != null) {
					compareVerScheduleTask.getDependEntrys().clear();
				}
			}
		}
		return baseVerSchedule;
	}

	/**
	 * @description��ȡ����Դid�����������
	 * @author �ź���
	 * @createDate 2011-9-13 void
	 * @version EAS7.0
	 * @see
	 */

	private Map getSrcId2SchTaskMap(FDCScheduleInfo info) {
		Map map = new HashMap();
		if (info != null && info.getTaskEntrys() != null && info.getTaskEntrys().size() > 0) {
			FDCScheduleTaskCollection taskEntrys = info.getTaskEntrys();
			for (int i = 0; i < taskEntrys.size(); i++) {
				map.put(taskEntrys.get(i).getSrcID(), taskEntrys.get(i));
			}
		}
		return map;
	}

	private FDCScheduleInfo getSchedule4Compare(String compareVerID, Context ctx) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("srcID");
		view.getSelector().add("version");
		view.getSelector().add("taskEntrys.id");
		view.getSelector().add("taskEntrys.name");
		view.getSelector().add("taskEntrys.number");
		view.getSelector().add("taskEntrys.longNumber");
		view.getSelector().add("taskEntrys.level");
		view.getSelector().add("taskEntrys.isLeaf");
		view.getSelector().add("taskEntrys.end");
		view.getSelector().add("taskEntrys.start");
		view.getSelector().add("taskEntrys.duration");
		view.getSelector().add("taskEntrys.checkDate");
		view.getSelector().add("taskEntrys.srcID");
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("id", compareVerID);
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo item = new SorterItemInfo("taskEntrys.longNumber");
		sorter.add(item);
		view.setSorter(sorter);
		FDCScheduleCollection scheduleCollection = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleCollection(view);
		if (scheduleCollection != null && scheduleCollection.size() > 0) {
			return scheduleCollection.get(0);
		}
		return null;
	}

	private String getBaseVerID(Context ctx, String srcID, float version) throws BOSException {
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("id");
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("srcID", srcID);
		filter.appendFilterItem("version", new Float(version));
		view.setFilter(filter);
		FDCScheduleCollection scheduleCollection = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleCollection(view);
		if (scheduleCollection != null && scheduleCollection.size() > 0) {
			return scheduleCollection.get(0).getId().toString();
		}
		return null;
	}

	/**
	 * @description ������°汾�Ľ���
	 * @author �ź���
	 * @createDate 2011-9-17
	 * @version EAS7.0
	 * @see
	 */

	protected IObjectValue _getNewestVerSchedule(Context ctx, IObjectValue curProject, IObjectValue projectSpecial) throws BOSException,
			EASBizException {
		  return getNewestVerSchedule(ctx,curProject,projectSpecial,false);
	}
	
	protected IObjectValue getNewestVerSchedule(Context ctx, IObjectValue curProject, IObjectValue projectSpecial,boolean isFromExecute)throws BOSException,
	EASBizException  {
		String scheduleID = getNewestVersionScheduleID(ctx, (CurProjectInfo) curProject, (ProjectSpecialInfo) projectSpecial,isFromExecute);
		return FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleInfo(new ObjectStringPK(scheduleID));
	}

	/**
	 * ����ϵͳ�кܶ������޸ĵ����󣬳�ȡ�˷���
	 * Ϊ�˱����Ժ���չ�������ز�������ΪMAP
	 * ��Ҫ������¹��ܣ�
	 * 1�����������޸�
	 * 2��ȡ���޸ĺ���������ݲ�����
	 * @see com.kingdee.eas.fdc.schedule.app.AbstractFDCScheduleControllerBean#_batchChangeTaskProperties(com.kingdee.bos.Context, java.util.Map)
	 */
	protected Map _batchChangeTaskProperties(Context ctx, Map param) throws BOSException, EASBizException {
		boolean isNeedReload = false;
		Map resultMap = new HashMap();
		Set idSet = null;
		BOSUuid scheduleId = null;
		Object [] bizTypes = null;
		RESchTaskTypeEnum taskTypeEnum = null;
		if(param.get("idSet") != null){
			idSet = (Set) param.get("idSet");
		}
		if(param.get("bizTypes") != null){
			bizTypes =  (Object[]) param.get("bizTypes");
		}
		if(param.get("taskType") != null){
			taskTypeEnum = (RESchTaskTypeEnum) param.get("taskType");
		}
		if(param.get("scheduleId") != null){
			scheduleId =  (BOSUuid) param.get("scheduleId");
		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if(taskTypeEnum != null){
			builder.appendSql("update t_sch_fdcscheduletask set ftasktype = ? where " );
			builder.addParam(taskTypeEnum.getValue());
			builder.appendParam("fid", idSet.toArray());
			builder.execute();
			isNeedReload = true;
		}
		//����ɵ�ҵ������
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id",idSet,CompareType.INCLUDE));
		ScheduleTaskBizTypeFactory.getLocalInstance(ctx).delete(filter);
		
		//�����޸ĺ��ҵ������ 
		
		builder.clear();
		if(bizTypes!= null && bizTypes.length>0){
			
			String sql = "insert into T_SCH_ScheduleTaskBizType(fid,fparentid,FBizTypeID) values(?,?,?)";
			ScheduleBizTypeInfo bizTypeInfo = null;
			List paramList = new ArrayList();
			List detailList = null;
			for(int i=0;i<bizTypes.length;i++){
				bizTypeInfo = (ScheduleBizTypeInfo) bizTypes[i];
				for(Iterator it = idSet.iterator();it.hasNext();){
					detailList = new ArrayList(3);
					detailList.add(BOSUuid.create("0B475AFB").toString());
					detailList.add(it.next().toString());
					detailList.add(bizTypeInfo.getId().toString());
					
					paramList.add(detailList);
					
					detailList = null;
				}
				
			}
			
			builder.executeBatch(sql, paramList);
			isNeedReload = true;
		}
		
		if(isNeedReload){
			resultMap.put("isNeedReload", Boolean.TRUE);
			FDCScheduleCollection  cols =getScheduleByID(ctx, scheduleId.toString());
			if(!cols.isEmpty()){
				resultMap.put("scheduleInfo", cols.get(0));
			}
		}
		
		return resultMap;
	}

	protected void _submitMainSchedule(Context ctx, IObjectValue mainSchedule) throws BOSException, EASBizException {
		/* TODO �Զ����ɷ������ */
		
	}

	protected void _submitSpecialSchedule(Context ctx, IObjectValue specialSchedule) throws BOSException, EASBizException {
		/* TODO �Զ����ɷ������ */
		
	}
	
	public CoreBaseCollection getCollection(Context ctx, String oql) throws BOSException {
		int index = oql.indexOf("'");
		String billId = oql.substring(index + 1, oql.length() - 1);	
		CoreBaseCollection cols = new CoreBaseCollection();
		IObjectPK pk = new ObjectUuidPK(BOSUuid.read(billId));
		try {
			FDCScheduleInfo info = (FDCScheduleInfo) _getScheduleInfo(ctx, pk);
			cols.add(info);
		} catch (EASBizException e) {
			throw new BOSException(e.getCause());
		}
		return cols;
	}

	/**
	 *���õ�ǰ�汾Ϊ���˰汾
	 */
	protected void _setCheckVersion(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		IObjectPK pk =  new ObjectUuidPK(billId);
		// TODO Auto-generated method stub
		if(!exists(ctx, pk)){
			throw new ScheduleException(ScheduleException.WITHMSG,new Object[]{"���ȱ���ƻ����ٽ��п��˰����ò�����"});
		}
		FDCScheduleInfo info =  getFDCScheduleInfo(ctx, pk);
		//���´˰汾�Ŀ�������Ϊ�ƻ��������
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
        builder.appendSql("update t_sch_fdcschedule set FISCHECKVERSION = 0 where fsrcid = ?");
        builder.addParam(info.getSrcID());
        builder.execute();
		builder.clear();
		String sql = "update t_sch_fdcschedule set FISCHECKVERSION = 1 ,fversionName=replace(fversionName,'ִ�а�','���˰�') where fid = ?";
		builder.appendSql(sql);
		builder.addParam(info.getId().toString());
		builder.execute();
		//���¼ƻ��������Ϊ��������
		builder.clear();
		
	    sql = "update t_sch_fdcscheduletask set fcheckdate = fend where fscheduleid = ?";
	    builder.appendSql(sql);
	    builder.addParam(billId.toString());
	    builder.execute();
		
		
	}
	
}