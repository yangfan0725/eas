package com.kingdee.eas.fdc.schedule.framework.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sourceforge.ganttproject.calendar.GPCalendarActivity;
import net.sourceforge.ganttproject.calendar.ScheduleCalendarImpl;

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
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.app.ScheduleAppHelper;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class ScheduleCalendarControllerBean extends
		AbstractScheduleCalendarControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.schedule.framework.app.ScheduleCalendarControllerBean");

	protected Map _getCalendar(Context ctx, String calendarId)
			throws BOSException, EASBizException {
		Map map = new HashMap();
		ScheduleCalendarInfo calendarInfo = new ScheduleCalendarInfo();
		CurProjectInfo project = new CurProjectInfo();
		calendarInfo = ScheduleCalendarFactory.getLocalInstance(ctx)
				.getScheduleCalendarInfo(
						new ObjectUuidPK(BOSUuid.read(calendarId)));
		map.put("calendarInfo", calendarInfo);
		if (calendarInfo.getObjectId() != null) {
			project = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(
							new ObjectUuidPK(BOSUuid.read(calendarInfo
									.getObjectId())));
			;
		}
		map.put("projectInfo", project);
		return map;
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		try {
			verify(model, ctx);
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		super._submit(ctx, model);
		ScheduleCalendarInfo info = (ScheduleCalendarInfo) model;
		info.isIsSys();
		BOSUuid id = info.getId();

		String strID = id.toString();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_sch_schedulecalendar set fisSys = ? where fid= ?");
		// ��Ĭ������
		if (info.isIsSys()) {
			Integer i = new Integer(1);
			builder.addParam(i);
			builder.addParam(id.toString());
			// Ĭ����Ŀ����ֻ����һ�������Ա��뽫��ǰ�ǵ��޸ĳɲ��ǡ�
			FDCSQLBuilder b1 = new FDCSQLBuilder(ctx);
			b1
					.appendSql("update t_sch_schedulecalendar set fisSys = 0 where fisSys= 1");
			b1.executeUpdate();
		} else {
			// ����Ĭ������
			Integer i = new Integer(0);
			builder.addParam(i);
			builder.addParam(id.toString());
		}
		builder.executeUpdate();
		return null;
	}

	private void verify(IObjectValue model, Context ctx)
			throws EASBizException, BOSException, SQLException {
		ScheduleCalendarInfo calendarInfo = (ScheduleCalendarInfo) model;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", calendarInfo.getName(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("number", calendarInfo.getNumber(), CompareType.EQUALS));
		if (calendarInfo.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", calendarInfo.getId().toString(), CompareType.NOTEQUALS));
			filter.setMaskString("(#0 or #1) and #2");
		} else {
			filter.setMaskString("#0 or #1");
		}
		if (FDCHelper.isEmpty(calendarInfo.getId())) {
			if (_exists(ctx, filter)) {
				throw new EASBizException(new NumericExceptionSubItem("0011", "����������ظ���"));
			}
		}
		//һ����Ŀֻ����һ����׼����
		if(calendarInfo.getObjectId() != null){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select count(*) from T_SCH_ScheduleCalendar where FObjectID = ? ");
			if(calendarInfo.getId() != null){
				builder.appendSql(" and fobjectid <> ?");
				builder.addParam(calendarInfo.getId().toString());
			}
			builder.addParam(calendarInfo.getObjectId());
			IRowSet rs = builder.executeQuery();
			int count = 0;
			while(rs.next()){
				count = rs.getInt(1);
			}
			if(count>0){
				throw new EASBizException(new NumericExceptionSubItem("0012", "һ����Ŀֻ�ܴ���һ��������"));
			}
		}
		
		//����ϵͳ������һ��Ĭ������
		if(!calendarInfo.isIsSys()){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select count(*) from t_sch_ScheduleCalendar where fisSys = 1 ");
			if (calendarInfo.getId() != null) {
				builder.appendSql("and fid <> ?");
				builder.addParam(calendarInfo.getId().toString());
			}
			IRowSet rs = builder.executeQuery();
			int count = 0;
			rs =builder.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
			
			if(count==0){
				throw new EASBizException(new NumericExceptionSubItem("0013", "ȫ���ű�����һ��Ĭ��������"));
			}
		}
		
		// ScheduleCalendarInfo calendarInfo = (ScheduleCalendarInfo) model;
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(
		// new FilterItemInfo("name", calendarInfo.getName(),
		// CompareType.EQUALS));
		//		
		// if (calendarInfo.getId() != null) {
		// filter.getFilterItems().add(new FilterItemInfo("id",
		// calendarInfo.getId().toString(), CompareType.NOTEQUALS));
		// filter.setMaskString("#0 and #1");
		// }
		// if (_exists(ctx, filter)) {
		// throw new EASBizException(new NumericExceptionSubItem("0011",
		// "���Ʋ����ظ���"));
		// }
		//		
		// FilterInfo filter2 = new FilterInfo();
		// filter2.getFilterItems().add(
		// new FilterItemInfo("number", calendarInfo.getNumber(),
		// CompareType.EQUALS));
		// if (calendarInfo.getId() != null) {
		// filter2.getFilterItems().add(
		// new FilterItemInfo("id", calendarInfo.getId().toString(),
		// CompareType.NOTEQUALS));
		// filter2.setMaskString("(#0 and #1");
		// }
		// if (_exists(ctx, filter2)) {
		// throw new EASBizException(new NumericExceptionSubItem("0011",
		// "���벻���ظ���"));
		// }

	}

	/**
	 * ȡ����ĿĬ������
	 * @throws EASBizException 
	 */
	protected IObjectValue _getDefaultCal(Context ctx, String prjID)
			throws BOSException, EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("holidayEntrys.*");
		sic.add("weekendEntrys.*");
		// ���������ĿΪ�գ���ֱ�ӷ���ϵͳĬ������
		if (FDCHelper.isEmpty(prjID)) {
			return getSysDefaultCal(ctx, sic);
		}
        // ���������Ŀ��Ϊ�գ��򷵻���Ŀ����
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("objectId", prjID));
//		filter.getFilterItems().add(new FilterItemInfo("isSys", Boolean.TRUE));
		view.setFilter(filter);
	
		ScheduleCalendarCollection col = getScheduleCalendarCollection(ctx,
				view);
		if (col != null && col.size() > 0) {
			return col.get(0);
		} else {
			
			return getSysDefaultCal(ctx, sic);
			
//			return null;
		}
	}

	private IObjectValue getSysDefaultCal(Context ctx,
			SelectorItemCollection sic) throws BOSException, EASBizException {
		// ��ĿûĬ���������򷵻ؼ���Ĭ����������������Ϣ��
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isSys",Boolean.TRUE));
		view.setFilter(filter);
		ScheduleCalendarCollection cols = ScheduleCalendarFactory.getLocalInstance(ctx).getScheduleCalendarCollection(view);
		if(cols.isEmpty()){
			throw new EASBizException(new NumericExceptionSubItem("114","��ǰϵͳû������Ĭ���������������½��ȹ���Ļ�������������������������ΪĬ��������"));
		}
		return cols.getObject(0);
//		return getScheduleCalendarInfo(ctx, new ObjectUuidPK(
//				"L5WgOM5+TDCr/oCKWmYL0IclCXs="), sic);
	}

	protected boolean _isquote(Context ctx, String[] idstr) throws BOSException {
		// �ж��Ƿ�����
		for (int i = 0; i < idstr.length; i++) {
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			// sql.appendSql(
			// " select distinct head.FID as id,prj.FName_l2 as name ");
			// sql.appendSql(" from T_SCH_FDCScheduleTask as task ");
			// sql.appendSql(" left join T_SCH_FDCSchedule as head ");
			// sql.appendSql(" on head.FID = task.FScheduleID ");
			// sql.appendSql(" left join T_FDC_CurProject as prj ");
			// sql.appendSql(" on prj.FID = head.FProjectID ");
			// sql.appendSql(" where task.FScheduleID = ? ");
			// String param = idstr[i].toString();
			// sql.addParam(param);
			sql
					.appendSql("select * from t_sch_fdcscheduletask where FCalendarID = ?");
			sql.addParam(idstr[i]);
			IRowSet set = sql.executeQuery();
			// ����з����� ��֤��������
			try {
				if (set.next()) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * �ύ�������������Ժ���Ҫͬʱ����ʹ���˸������ļƻ�<br>
	 * 
	 * ����������Ҫ���µĽ��ȼƻ�id<br>
	 * ��ѭ��ÿ���ƻ��������¼����������������ʱ�����ı�<br>
	 * �����Ǳ���������������Ϊ���ҵ�ǰ��������ĿĬ�������Ҽƻ�����Ŀ���ǵ�ǰ��Ŀ������֮<br>
	 * ���²������£�<br>
	 * 1�����Ƚ��䱾��ʼ����������Ч���ڡ���Ȼ���ڼ��㣬<br>
	 * ��ʼ����ֻ������ĩ�������ٸ��ݿ�ʼ�������㹤��<br>
	 * 2��Ȼ����������к��������ô�ӹ�ϵȷ����������ʱ��<br>
	 * ���ڱ�Ȼ����������������Ը��º������񲻱�ѭ������<br>
	 * 3��Ȼ������������ϼ�����ʹ��ʼʱ�䡢����ʱ�䲻С�����ڼ�<br>
	 * 
	 * ���еĸ������ն��Ǹ���taskEntrys������ϣ����ʹ���������ѭ��update���ݿ�
	 * 
	 * @author emanon
	 */
	protected void _reCalculateSchedule(Context ctx, Set scheduleIDSet,
			IObjectValue calendar) throws BOSException,EASBizException{
		// 1.�Ƚ��޸ĺ�������ύ
	
		_submit(ctx, calendar);
	
		// _submit(ctx, calendar);

		// 2.Ȼ����¼ƻ�
		if (scheduleIDSet == null || scheduleIDSet.isEmpty()) {
			return;
		}
		// �õ���ǰ�����Ķ���

		ScheduleCalendarInfo cal = (ScheduleCalendarInfo) calendar;
		ScheduleCalendarImpl impl = new ScheduleCalendarImpl(cal);
		// ������ID
		String calID = cal.getId().toString();
		// �����Ĺ�����Ŀ
		String prjID = cal.getObjectId();
		// �����Ƿ��ǹ�����ĿĬ������
		boolean isDefault = cal.isIsSys();

		// ȡ������¼ƻ��ļ���
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("project.id");
		sic.add("project.number");
		sic.add("project.name");
		sic.add("taskEntrys.id");
		sic.add("taskEntrys.isLeaf");
		sic.add("taskEntrys.longNumber");
		sic.add("taskEntrys.start");
		sic.add("taskEntrys.end");
		sic.add("taskEntrys.natureTimes");
		sic.add("taskEntrys.effectTimes");
		sic.add("taskEntrys.parent.id");
		sic.add("taskEntrys.dependEntrys.id");
		sic.add("taskEntrys.dependEntrys.type");
		sic.add("taskEntrys.dependEntrys.difference");
		// sic.add("taskEntrys.dependEntrys.hardness");
		sic.add("taskEntrys.dependEntrys.dependTask.id");
		// sic.add("taskEntrys.dependEntrys.dependTask.isLeaf");
		// sic.add("taskEntrys.dependEntrys.dependTask.longNumber");
		// sic.add("taskEntrys.dependEntrys.dependTask.start");
		// sic.add("taskEntrys.dependEntrys.dependTask.end");
		// sic.add("taskEntrys.dependEntrys.dependTask.natureTimes");
		// sic.add("taskEntrys.dependEntrys.dependTask.effectTimes");
		sic.add("taskEntrys.scheduleCalendar.id");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", scheduleIDSet, CompareType.INCLUDE));
		view.setFilter(filter);
		FDCScheduleCollection scheduleCol = FDCScheduleFactory.getLocalInstance(ctx).getFDCScheduleCollection(view);

		// ������¼ƻ�
		for (int i = 0; i < scheduleCol.size(); i++) {
			FDCScheduleInfo scheduleInfo = scheduleCol.get(i);
			String curPrjID = scheduleInfo.getProject().getId().toString();
			// ������·�¼
			FDCScheduleTaskCollection taskEntrys = scheduleInfo.getTaskEntrys();
			Map updateMap = new HashMap();
			logger.info("start to init");
			for (int j = 0; j < taskEntrys.size(); j++) {
				FDCScheduleTaskInfo taskInfo = taskEntrys.get(j);

				String curCalID = null;
				if (taskInfo.getCalendar() != null) {
					curCalID = taskInfo.getCalendar().getId().toString();
				}
				// ������ ���� ��ǰ������Ĭ������ &&����Ϊ��&&�ƻ�����Ŀ�ǵ�ǰ��Ŀ
				boolean isNeetUpdate = (calID.equals(curCalID)) || (isDefault && curCalID == null && curPrjID.equals(prjID));
				if (isNeetUpdate) {
					// 1�����Ƚ��䱾��ʼ����������Ч���ڡ���Ȼ���ڼ���
					// ����Ҫ�޸ĵ������¼�����ø���������
					taskInfo.setCalendar(cal);
					calcSelf(impl, taskInfo, updateMap);
					// 2��Ȼ����������к��������ô�ӹ�ϵȷ����������ʱ��
					updateDepend(impl, taskInfo, taskEntrys, updateMap);
					// 3��Ȼ������������ϼ�����ʹ��ʼʱ�䡢����ʱ�䲻С�����ڼ�
					updateSuper(taskInfo, taskEntrys, updateMap);
				}
			}
			// д�����ݿ�
			logger.info("start update DB");
			updateToDB(ctx, updateMap, scheduleInfo);
			logger.info(" update DB finish");
		}
	}

	/**
	 * ���������к��������ô�ӹ�ϵȷ����������ʱ��<br>
	 * ���ڱ�Ȼ����������������Ը��º������񲻱�ѭ������<br>
	 * �ú��������ID��taskEntrys�����ң�û��˵���Ǳ����Ŀ�ƻ���task��������
	 * 
	 * @param impl
	 * @param taskInfo
	 * @param taskEntrys
	 * @param updateMap
	 */
	private void updateDepend(ScheduleCalendarImpl impl, FDCScheduleTaskInfo taskInfo, FDCScheduleTaskCollection taskEntrys, Map updateMap) {
		FDCScheduleTaskDependCollection dependEntrys = taskInfo.getDependEntrys();
		FDCScheduleTaskInfo tmpInfo;
		for (int i = 0; i < dependEntrys.size(); i++) {
			FDCScheduleTaskDependInfo dependInfo = dependEntrys.get(i);
			TaskLinkTypeEnum linkType = dependInfo.getType();
			int difference = dependInfo.getDifference();
			for (int j = 0; j < taskEntrys.size(); j++) {
				String curID = taskEntrys.get(j).getId().toString();
				if (curID.equals(dependInfo.getId().toString())) {
					tmpInfo = taskEntrys.get(j);
					Date start = tmpInfo.getStart();
					Date end = tmpInfo.getEnd();
					if (linkType.equals(TaskLinkTypeEnum.FINISH_START)) {
						Date preEnd = taskInfo.getEnd();
						Date newStart = FDCDateHelper.addDays(preEnd, difference);
						newStart = impl.findClosestWorkingTime(newStart);
						int diff = FDCDateHelper.getDiffDays(start, newStart);
						Date newEnd = FDCDateHelper.addDays(end, diff);
						setTask(impl, tmpInfo, newStart, newEnd);
					} else if (linkType.equals(TaskLinkTypeEnum.START_START)) {
						Date preStart = taskInfo.getStart();
						Date newStart = FDCDateHelper.addDays(preStart, difference);
						newStart = impl.findClosestWorkingTime(newStart);
						int diff = FDCDateHelper.getDiffDays(start, newStart);
						Date newEnd = FDCDateHelper.addDays(end, diff);
						setTask(impl, tmpInfo, newStart, newEnd);
					} else if (linkType.equals(TaskLinkTypeEnum.FINISH_FINISH)) {
						Date preEnd = taskInfo.getEnd();
						Date newEnd = FDCDateHelper.addDays(preEnd, difference);
						newEnd = impl.findClosestWorkingTime(newEnd);
						int diff = FDCDateHelper.getDiffDays(end, newEnd);
						Date newStart = FDCDateHelper.addDays(start, diff);
						setTask(impl, tmpInfo, newStart, newEnd);
					}
					updateMap.put(tmpInfo.getId().toString(), tmpInfo);
					// // ���ºú����ûط�¼
					// taskEntrys.set(j, tmpInfo);
				}
			}
		}
	}

	/**
	 * ���Ƚ��䱾��ʼ����������Ч���ڡ���Ȼ���ڼ���<br>
	 * ��ʼ����ֻ������ĩ�������ٸ��ݿ�ʼ�������㹤��
	 * 
	 * @param impl
	 * @param taskInfo
	 * @param updateMap
	 */
	private void calcSelf(ScheduleCalendarImpl impl, FDCScheduleTaskInfo taskInfo, Map updateMap) {
		Date newStart = impl.findClosestWorkingTime(taskInfo.getStart());
		Date newEnd = impl.findClosestWorkingTime(taskInfo.getEnd());
		setTask(impl, taskInfo, newStart, newEnd);
		updateMap.put(taskInfo.getId().toString(), taskInfo);
	}

	/**
	 * ��һ��task�����µĿ�ʼ�������ڣ����ݴ˼����µ���Ч����Ȼ����
	 * 
	 * @param impl
	 * @param taskInfo
	 * @param newStart
	 * @param newEnd
	 */
	private void setTask(ScheduleCalendarImpl impl, FDCScheduleTaskInfo taskInfo, Date newStart, Date newEnd) {
		BigDecimal newNatureTimes = FDCHelper.ZERO;
		BigDecimal newEffectTimes = FDCHelper.ZERO;
		List activities = impl.getActivities(newStart, newEnd);
		for (int i = 0; i < activities.size(); i++) {
			GPCalendarActivity nextCalendarActivity = (GPCalendarActivity) activities.get(i);
			int diff = FDCDateHelper.getDiffDays(nextCalendarActivity.getStart(), nextCalendarActivity.getEnd());
			BigDecimal len = new BigDecimal(diff);
			if (nextCalendarActivity.isWorkingTime()) {
				newEffectTimes = newEffectTimes.add(len);
			}
			newNatureTimes = newNatureTimes.add(len);
		}
		taskInfo.setStart(newStart);
		taskInfo.setEnd(newEnd);
		taskInfo.setNatureTimes(newNatureTimes);
		taskInfo.setEffectTimes(newEffectTimes);
	}

	/**
	 * �����������ϼ�����ʹ��ʼʱ�䡢����ʱ�䲻С�����ڼ�<br>
	 * �����ѭ���������¸�����ֱ�����
	 * 
	 * @param impl
	 * @param taskInfo
	 * @param taskEntrys
	 * @param updateMap
	 */
	private void updateSuper(FDCScheduleTaskInfo taskInfo, FDCScheduleTaskCollection taskEntrys, Map updateMap) {
		FDCScheduleTaskInfo parent = taskInfo.getParent();
		if (parent != null) {
			Date start = taskInfo.getStart();
			Date end = taskInfo.getEnd();
			String prtID = parent.getId().toString();
			for (int i = 0; i < taskEntrys.size(); i++) {
				FDCScheduleTaskInfo tmpInfo = taskEntrys.get(i);
				if (!tmpInfo.isIsLeaf() && prtID.equals(tmpInfo.getId().toString())) {
					parent = tmpInfo;
					Date pStart = parent.getStart();
					Date pEnd = parent.getEnd();
					if (start.compareTo(pStart) < 0 || end.compareTo(pEnd) > 0) {
						// update by libing
						ScheduleCalendarImpl impl = new ScheduleCalendarImpl(parent.getCalendar());
						pStart = impl.findClosestWorkingTime(start);
						pEnd = impl.findClosestWorkingTime(end);
						setTask(impl, parent, pStart, pEnd);
						taskEntrys.set(i, parent);
						updateMap.put(parent.getId().toString(), parent);
						break;
					}
				}
			}
			// ѭ���������¸�����
			updateSuper(parent, taskEntrys, updateMap);
		}
	}

	/**
	 * ����map����������¼���������ݿ⿪ʼ����������Ч����Ȼ<br>
	 * ʹ���������£����Ч�ʣ�һƬ�ֹ�ȥ��Ƭ
	 * 
	 * @param ctx
	 * 
	 * @param updateSet
	 * @param scheduleInfo
	 * @throws BOSException
	 */
	private void updateToDB(Context ctx, Map updateSet,
			FDCScheduleInfo scheduleInfo) throws BOSException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		if (updateSet != null && !updateSet.isEmpty()) {
			// ��������
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			String head = "update T_SCH_FDCScheduleTask set FStart = ?,FEnd = ?,FEffectTimes = ?,FNatureTimes = ? where FID = ?";
			sql.setPrepareStatementSql(head);
			sql.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
			List params = new ArrayList();
			boolean hasSql = false;
			Iterator it = updateSet.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Entry) it.next();
				FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) entry.getValue();
				String id = taskInfo.getId().toString();
				Timestamp start = new Timestamp(taskInfo.getStart().getTime());
				Timestamp end = new Timestamp(taskInfo.getEnd().getTime());
				BigDecimal effectTimes = taskInfo.getEffectTimes();
				BigDecimal natureTimes = taskInfo.getNatureTimes();
				sql.addParam(start);
				sql.addParam(end);
				sql.addParam(effectTimes);
				sql.addParam(natureTimes);
				sql.addParam(id);
				sql.addBatch();
				hasSql =true;
				
			}
			if(hasSql){
				sql.executeBatch();
			}

			// ���±�ͷ���汾�ƻ��Ŀ�ʼ,������ʱ��
			sql.clear();
			sql
					.appendSql("select min(task.fstart) as fstart,max(task.fend) as fend ");
			sql.appendSql(" from T_SCH_FDCScheduleTask task ");
			sql.appendSql(" inner join T_Sch_FDCSchedule head ");
			sql.appendSql(" on head.fid=task.fscheduleid ");
			sql.appendSql(" where head.fid = ? ");

			sql.addParam(scheduleInfo.getId().toString());
			IRowSet rowSet = sql.executeQuery();
			try {
				if (rowSet.next()) {
					Date startDate = rowSet.getDate("fstart");
					Date endDate = rowSet.getDate("fend");
					ScheduleCalendarInfo calendar = scheduleInfo.getCalendar();
					if (calendar == null) {
						ScheduleAppHelper.setCalendar(ctx, scheduleInfo);
						calendar = scheduleInfo.getCalendar();
					}
					BigDecimal effectTimes = ScheduleCalendarHelper
							.getEffectTimes(startDate, endDate, calendar);
					BigDecimal natureTimes = ScheduleCalendarHelper
							.getNatureTimes(startDate, endDate);
					sql.clear();
					sql
							.appendSql(" update T_Sch_FDCSchedule set fstartDate=?,fendDate=?,feffectTimes=?,fnatureTimes=? where fId=? ");
					sql.addParam(startDate);
					sql.addParam(endDate);
					sql.addParam(effectTimes);
					sql.addParam(natureTimes);
					sql.addParam(scheduleInfo.getId().toString());
					sql.execute();
					sql.clear();
					sql
							.appendSql(" update T_SCH_ScheduleVerManager set fstartDate=?,fendDate=?,feffectTimes=?,fnatureTimes=? where fprojectId=? and fislatestver=1 ");
					sql.addParam(startDate);
					sql.addParam(endDate);
					sql.addParam(effectTimes);
					sql.addParam(natureTimes);
					sql.addParam(scheduleInfo.getProject().getId().toString());
					sql.execute();
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
	}

	protected String _verifyremote(Context ctx, IObjectValue model)
			throws BOSException {
		ScheduleCalendarInfo calendarInfo = (ScheduleCalendarInfo) model;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("name", calendarInfo.getName(),
						CompareType.EQUALS));
		if (!FDCHelper.isEmpty(calendarInfo.getId())) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", calendarInfo.getId().toString(),
							CompareType.EQUALS));
			filter.setMaskString("#0 and #1");
		}
		try {
			if (_exists(ctx, filter)) {
				return "1";
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(
				new FilterItemInfo("number", calendarInfo.getNumber(),
						CompareType.EQUALS));
		if (!FDCHelper.isEmpty(calendarInfo.getId())) {
			filter2.getFilterItems().add(
					new FilterItemInfo("id", calendarInfo.getId().toString(),
							CompareType.EQUALS));
			filter2.setMaskString("#0 and #1");
		}
		try {
			if (_exists(ctx, filter2)) {
				return "2";
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "3";
	}

	protected String _verifyremoteupdate(Context ctx, IObjectValue model)
			throws BOSException {
		ScheduleCalendarInfo calendarInfo = (ScheduleCalendarInfo) model;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("name", calendarInfo.getName(),
						CompareType.EQUALS));

		if (calendarInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", calendarInfo.getId().toString(),
							CompareType.NOTEQUALS));
			filter.setMaskString("#0 and #1");
		}
		try {
			if (_exists(ctx, filter)) {
				// throw new EASBizException(new NumericExceptionSubItem("0011",
				// "���Ʋ����ظ���"));
				return "1";
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(
				new FilterItemInfo("number", calendarInfo.getNumber(),
						CompareType.EQUALS));
		if (calendarInfo.getId() != null) {
			filter2.getFilterItems().add(
					new FilterItemInfo("id", calendarInfo.getId().toString(),
							CompareType.NOTEQUALS));
			filter2.setMaskString("#0 and #1");
		}
		try {
			if (_exists(ctx, filter2)) {
				// throw new EASBizException(new NumericExceptionSubItem("0011",
				// "���벻���ظ���"));
				return "2";
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "3";
	}

	protected String _addOnetoNewNum(Context ctx) throws BOSException {
		// TODO Auto-generated method stub
		String strNum = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// ֱ��дSQL��ѯ��������
		builder
				.appendSql("select max(fnumber) as fnumber from T_SCH_ScheduleCalendar");
		IRowSet rowSet = builder.executeQuery();
		try {
			// �Ƿ���ֵ
			while (rowSet.next()) {
				strNum = rowSet.getString("fnumber");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (strNum == "" || strNum == null) {
			strNum = "001";
		}
		return strNum;
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ScheduleCalendarInfo info = (ScheduleCalendarInfo) getValue(ctx, pk);
		if (info.isIsSys() == true) {
			throw new EASBizException(new NumericExceptionSubItem("115", "��ǰ����ΪĬ��������������ɾ���������Ҫɾ����ǰ�����������Ƚ���ǰ��������Ϊ��Ĭ��������"));
		}
		super._delete(ctx, pk);
	}
}
