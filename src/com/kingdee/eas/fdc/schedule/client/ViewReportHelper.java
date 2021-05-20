/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.ParamItemCollection;
import com.kingdee.eas.base.param.ParamItemFactory;
import com.kingdee.eas.base.param.ParamItemInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTaskProgressReportInfo;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������ ��������
 * 
 * @author �ź���
 * @version EAS7.0
 * @createDate 2011-11-4
 * @see
 */

public class ViewReportHelper {

	public static Map<String, String> getParams(String currFiCost) throws BOSException {
		Map<String, String> ret = new HashMap<String, String>();
		String sql = "select fcostcenterid from t_fdc_curproject where ffullorgunit = '" + currFiCost + "'";
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("keyID.number", "FDCSH011"));
		filter.getFilterItems().add(new FilterItemInfo("orgUnitID.id", sql, CompareType.INNER));
		view.setFilter(filter);
		ParamItemCollection cols = ParamItemFactory.getRemoteInstance().getParamItemCollection(view);
		if (cols.size() > 0) {
			ParamItemInfo pi = null;
			for (int i = 0; i < cols.size(); i++) {
				pi = cols.get(i);
				ret.put(pi.getOrgUnitID().getId().toString(), pi.getValue());
			}
		}
		return ret;
	}

	public static Map<String, String> getAllParams() throws BOSException {
		Map<String, String> ret = new HashMap<String, String>();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("keyID.number", "FDCSH011"));
		view.setFilter(filter);
		ParamItemCollection cols = ParamItemFactory.getRemoteInstance().getParamItemCollection(view);
		if (cols.size() > 0) {
			ParamItemInfo pi = null;
			for (int i = 0; i < cols.size(); i++) {
				pi = cols.get(i);
				ret.put(pi.getOrgUnitID().getId().toString(), pi.getValue());
			}
		}
		return ret;
	}

	/**
	 * 
	 * @description �Ƿ���в�������
	 * @author �ź���
	 * @createDate 2011-11-4
	 * @return boolean
	 * @version EAS7.0
	 * @see
	 */
	public static boolean isStartingParamControl(String costCenterId, Object obj) {
		ObjectUuidPK id = new ObjectUuidPK(BOSUuid.read(costCenterId));
		// ������������
		// ר������ļƻ�������ڴ��������������ļƻ��������
		String fdcParamValue = "";
		try {
			fdcParamValue = ParamControlFactory.getRemoteInstance().getParamValue(id, "FDCSH011");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if (obj instanceof CurProjectInfo) {
			if (fdcParamValue.equals("1") || fdcParamValue.equals("3")) {
				return true;
			}
		} else if (obj instanceof ProjectSpecialInfo) {
			if (fdcParamValue.equals("2") || fdcParamValue.equals("3")) {
				return true;
			}
		}
		return !"0".equals(fdcParamValue);
	}

	public static int getState(boolean isParamControl, FDCScheduleTaskInfo fDCScheduleTaskInfo, Date realEndDate, Date planEndDate, int complete) {
		String scheduleAppraise = isScheduleAppraise(fDCScheduleTaskInfo);
		if (isParamControl) {// ������������
			if (complete == 100 && scheduleAppraise == null) {
				return 3;// �������Ϊ100 û�н��н������� ��ȷ��
			}
			// �������Ϊ100 ���н������� ����Ϊ�ϸ�
			// ���ݼƻ�ʱ���ʵ��ʱ�����ж��ǰ�ʱ��ɻ���ʱ���(����Ϊ�ϸ���Ȳ�ΪΪ100,�������ﲻ�ٶ��Ƿ�ϸ�����ж�)
			if (complete == 100 && scheduleAppraise != null) {
				if (DateUtils.diffdates(planEndDate, realEndDate) <= 0) {
					return 0;
				}
				return 1;
			}

			if (complete < 100) {
				if (DateUtils.diffdates(new Date(), planEndDate) >= 0) {
					return 4; // �������С��100 �ƻ�������ڡ�=��ǰʱ�� ״̬Ϊ�գ�ִ���У�
				}
				if (DateUtils.diffdates(planEndDate, new Date()) > 0) {
					return 2;// �������С��100 �ƻ�������ڡ� ��ǰ���� ��ʱδ���
				}
			}
		} else {// ��������������
			// �������Ϊ100 ���ݼƻ�ʱ���ʵ��ʱ�����ж��ǰ�ʱ��ɻ���ʱ���
			if (realEndDate != null && complete == 100) {
				if (DateUtils.diffdates(planEndDate, realEndDate) <= 0) {
					return 0;
				}
				return 1;
			}
			if (complete < 100) {
				if (DateUtils.diffdates(new Date(), planEndDate) >= 0) {
					return 4; // �������С��100 �ƻ��������>=��ǰʱ�� ״̬Ϊ�գ�ִ���У�
				}
				return 2;
			}
		}
		return -1;
	}

	/**
	 * 
	 * ��������ԭ�еķ���������д���޸�����
	 * 
	 * @param isEvalationed
	 *            �Ƿ��Ѿ�����
	 * @param isStartingParamControl
	 *            �Ƿ����ò�������
	 * @param fDCScheduleTaskInfo
	 *            ������Ϣ
	 * @return �����ˣ�yuanjun_lan ����ʱ�䣺2012-12-6
	 */
	public static int getState(boolean isEvalationed, boolean isStartingParamControl, FDCScheduleTaskInfo fDCScheduleTaskInfo) {
		if (fDCScheduleTaskInfo == null) {
			return -1;
		}
		int complete = fDCScheduleTaskInfo.getComplete().intValue();
		Date planEndDate = fDCScheduleTaskInfo.getEnd();
		Date realEndDate = fDCScheduleTaskInfo.getActualEndDate();
		
		if(planEndDate == null || realEndDate == null){
			return -1;
		}

		if (complete == 100) {
			int diffDay = DateUtils.diffdates(planEndDate, realEndDate);
			int result = FDCScheduleConstant.UNDEF;
			if (diffDay <= 0) {
				result = isStartingParamControl ? (isEvalationed ? FDCScheduleConstant.ONTIME : FDCScheduleConstant.CONFIRMED) : FDCScheduleConstant.ONTIME;
				return result;
			} else {
				result = isStartingParamControl ? (isEvalationed ? FDCScheduleConstant.DELAY : FDCScheduleConstant.CONFIRMED) : FDCScheduleConstant.DELAY;
				return result;
			}

		} else {
			if (DateUtils.diffdates(new Date(), planEndDate) >= 0) {
				return FDCScheduleConstant.UNDEF; // �������С��100 �ƻ�������ڡ�=��ǰʱ��
				// ״̬Ϊ�գ�ִ���У�
			}
			if (DateUtils.diffdates(planEndDate, new Date()) > 0) {
				return FDCScheduleConstant.DELAYANDNOTCOMPLETE;// �������С��100
				// �ƻ�������ڡ� ��ǰ����
				// ��ʱδ���
			}
		}

		return -1;
	}

	/**
	 * 
	 * @description �Ƿ���н�������
	 * @author �ź���
	 * @createDate 2011-11-3
	 * @param fDCScheduleTaskInfo
	 * @return String
	 * @version EAS7.0
	 * @throws BOSException
	 * @throws SQLException
	 * @see
	 */
	private static String isScheduleAppraise(FDCScheduleTaskInfo fDCScheduleTaskInfo) {
		if (fDCScheduleTaskInfo != null && fDCScheduleTaskInfo.getId() != null) {
			FDCSQLBuilder t_sql = new FDCSQLBuilder();
			t_sql.appendSql(" select eval.fevaluationtype from T_SCH_FDCScheduleTask as sche ");
			t_sql.appendSql(" LEFT OUTER JOIN T_SCH_TaskEvaluationbill as eval ");
			t_sql.appendSql(" on sche.fsrcid =eval.fsrcrelatetask where sche.fsrcid = '" + fDCScheduleTaskInfo.getId().toString() + "' ");
			try {
				IRowSet rs = t_sql.executeQuery();
				while (rs.next()) {
					return rs.getString("fevaluationtype") == "" ? null : rs.getString("fevaluationtype");
				}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void initStateCell(boolean isEvaluation, boolean isParamControl, IRow row, Object obj) throws BOSException {

		Object entryInfo = null;
		// ʵ���������
		Date realEndDate = null;
		// �ƻ��������
		Date planEndDate = null;
		FDCScheduleTaskInfo relateTask = null;
		int completePrecent = 0;
		if (obj instanceof ProjectWeekReportEntryInfo) {
			entryInfo = (ProjectWeekReportEntryInfo) obj;
			realEndDate = ((ProjectWeekReportEntryInfo) entryInfo).getRealEndDate();
			relateTask = ((ProjectWeekReportEntryInfo) entryInfo).getRelateTask();
			planEndDate = relateTask.getEnd();
			completePrecent = ((ProjectWeekReportEntryInfo) entryInfo).getCompletePrecent();
		}
		if (obj instanceof ProjectMonthReportEntryInfo) {
			entryInfo = (ProjectMonthReportEntryInfo) obj;
			entryInfo = (ProjectMonthReportEntryInfo) obj;
			realEndDate = ((ProjectMonthReportEntryInfo) entryInfo).getRealEndDate();
			relateTask = ((ProjectMonthReportEntryInfo) entryInfo).getRelateTask();
			planEndDate = relateTask.getEnd();
			completePrecent = ((ProjectMonthReportEntryInfo) entryInfo).getCompletePrecent();
		}

		int state = getState(isEvaluation, isParamControl, relateTask);
		setStateValue(row, state);
	}

	public static Set sortDateByYearMonth(LinkedHashSet set) {
		if (set == null || set.size() == 0) {
			return new HashSet();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Object[] array = set.toArray();
		for (int i = 0; i < set.size(); i++) {
			for (int j = 0; j < set.size(); j++) {
				if (DateUtils.diffdates((Date) array[j], (Date) array[i]) > 0) {
					Date temp = (Date) array[i];
					array[i] = (Date) array[j];
					array[j] = temp;
				}
			}
		}
		set.clear();
		for (int i = 0; i < array.length; i++) {
			set.add(sdf.format(array[i]));
		}
		return set;
	}
	
	public static Map<String,Object[]> getEvaluationInformation(String scheduleid){
		IRowSet rowSet = null;
		Map<String, Object[]> evaluationMap = new HashMap<String, Object[]>();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		String sql = " /*dialect*/ select fcreatetime, fsrcrelatetask, fevaluationtype, fevaluationresult_l2 "
				+ " from (select eb.fcreatetime, "
				+ "             eb.fsrcrelatetask,"
				+ "             eb.fevaluationtype,"
				+ "             ev.fevaluationresult_l2,"
				+ "             row_number() over(partition by eb.fevaluationtype, eb.fsrcrelatetask order by eb.flastupdatetime desc) rn "
				+ "        from t_sch_taskevaluationbill eb "
				+ "        inner join t_sch_fdcscheduletask task "
				+ "          on task.fsrcid = eb.fsrcrelatetask "
				+ "        inner join t_sch_taskevaluation ev "
				+ "          on ev.fevaluationtype = eb.fevaluationtype "
				+ "         and ev.fid = eb.fevaluationresult "
				+ "         and task.fscheduleid ='" +scheduleid +"') "
				+ "  where rn = 1 ";

		builder.clear();
		builder.appendSql(sql);
		try {
			rowSet = builder.executeQuery();
			String key = null;
			while (rowSet.next()) {
				key = rowSet.getString("fsrcrelatetask") + "_"+ rowSet.getString("fevaluationtype");
				evaluationMap.put(key, new Object[] {rowSet.getDate("fcreatetime"),	rowSet.getString("fevaluationresult_l2") });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return evaluationMap;
	}
	
	public static Map<String,Object[]> getEvaluationInformationByProject(String projectId,boolean isSpecial){
		IRowSet rowSet = null;
		Map<String, Object[]> evaluationMap = new HashMap<String, Object[]>();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		String sql = " /*dialect*/ select fcreatetime, fsrcrelatetask, fevaluationtype, fevaluationresult_l2 "
				+ " from (select eb.fcreatetime, "
				+ "             eb.fsrcrelatetask,"
				+ "             eb.fevaluationtype,"
				+ "             ev.fevaluationresult_l2,"
				+ "             row_number() over(partition by eb.fevaluationtype, eb.fsrcrelatetask order by eb.flastupdatetime desc) rn "
				+ "        from t_sch_taskevaluationbill eb "
				+ "        inner join t_sch_fdcscheduletask task "
				+ "          on task.fsrcid = eb.fsrcrelatetask "
				+ "        inner join t_sch_fdcschedule s "
				+ "          on task.fscheduleid = task.fid "
				+ "        inner join t_sch_taskevaluation ev "
				+ "          on ev.fevaluationtype = eb.fevaluationtype "
				+ "         and ev.fid = eb.fevaluationresult "
				+ "         and s.fislatestver = 1 "
				+ "         and s.fprojectId ='"+projectId+"'";
				
		if(isSpecial){
			sql +="  and s.fprojectspecialid is not null )   where rn = 1 ";
		}else{
			sql +="  and s.fprojectspecialid is  null )   where rn = 1 ";
		}
				

		builder.clear();
		builder.appendSql(sql);
		try {
			rowSet = builder.executeQuery();
			String key = null;
			while (rowSet.next()) {
				key = rowSet.getString("fsrcrelatetask") + "_"+ rowSet.getString("fevaluationtype");
				evaluationMap.put(key, new Object[] {rowSet.getDate("fcreatetime"),	rowSet.getString("fevaluationresult_l2") });
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return evaluationMap;
	}
	
	
	

	public static FDCScheduleTaskCollection getNextMonthTasks(FDCScheduleTaskCollection col, String date) throws ParseException {
		Date fromtDate = DateUtils.parse(date, "yyyy-MM");
		Date startOfMonth = DateUtils.startOfMonth(fromtDate);
		Date endOfMonth = DateUtils.endOfMonth(fromtDate);
		startOfMonth = getNextMonthOfYear(startOfMonth);
		endOfMonth = DateUtils.endOfMonth(startOfMonth);
		FDCScheduleTaskCollection currMonthTasks = new FDCScheduleTaskCollection();
		Map<String,Object[]> evaluationMap = null;
		if(!col.isEmpty()){
			String scheduleId = col.get(0).getSchedule().getId()+"";
			evaluationMap = getEvaluationInformation(scheduleId);
		}
		String key = null;
		String evKey = null;
		for (int i = 0; i < col.size(); i++) {
			FDCScheduleTaskInfo task = col.get(i);
		    key = task.getSrcID();
		    evKey = key +"_"+"1SCHEDULE";
		    if(evaluationMap!=null && evaluationMap.containsKey(evKey)){
		    	task.put("pEvaluationDate",evaluationMap.get(evKey)[0]);
		    	task.put("pEvaluationResult",evaluationMap.get(evKey)[1]);
		    }
		    evKey = key +"_"+"2QUALITY";
		    if(evaluationMap!=null && evaluationMap.containsKey(evKey)){
		    	task.put("qEvaluationDate",evaluationMap.get(evKey)[0]);
		    	task.put("qEvaluationResult",evaluationMap.get(evKey)[1]);
		    }
		    
			addNextMonthTask(startOfMonth, endOfMonth, currMonthTasks, task);
		}
		return currMonthTasks;
	}

	public static FDCScheduleTaskCollection getCurrMonthTasks(FDCScheduleTaskCollection col, String date) throws ParseException {
		Date fromtDate = DateUtils.parse(date, "yyyy-MM");
		Date startOfMonth = DateUtils.startOfMonth(fromtDate);
		Date endOfMonth = DateUtils.endOfMonth(fromtDate);
		Calendar c = Calendar.getInstance();
		c.setTime(fromtDate);
		FDCScheduleTaskCollection currMonthTasks = new FDCScheduleTaskCollection();
		
		Map<String,Object[]> evaluationMap = null;
		if(!col.isEmpty()){
			String scheduleId = col.get(0).getSchedule().getId()+"";
			evaluationMap = getEvaluationInformation(scheduleId);
		}
		
		String key = null;
		String evKey = null;
		for (int i = 0; i < col.size(); i++) {
			FDCScheduleTaskInfo task = col.get(i);
			
			key = task.getSrcID();
		    evKey = key +"_"+"1SCHEDULE";
		    if(evaluationMap!=null && evaluationMap.containsKey(evKey)){
		    	task.put("pEvaluationDate",evaluationMap.get(evKey)[0]);
		    	task.put("pEvaluationResult",evaluationMap.get(evKey)[1]);
		    }
		    evKey = key +"_"+"2QUALITY";
		    if(evaluationMap!=null && evaluationMap.containsKey(evKey)){
		    	task.put("qEvaluationDate",evaluationMap.get(evKey)[0]);
		    	task.put("qEvaluationResult",evaluationMap.get(evKey)[1]);
		    }
			addCurrMonthTask(startOfMonth, endOfMonth, currMonthTasks, task);
		}
		return currMonthTasks;
	}
	public static FDCScheduleTaskCollection getNextMonthTasks(FDCScheduleTaskCollection col, String date, CurProjectInfo project) throws ParseException {
		Date fromtDate = DateUtils.parse(date, "yyyy-MM");
		Date startOfMonth = DateUtils.startOfMonth(fromtDate);
		Date endOfMonth = DateUtils.endOfMonth(fromtDate);
		startOfMonth = getNextMonthOfYear(startOfMonth);
		endOfMonth = DateUtils.endOfMonth(startOfMonth);
		FDCScheduleTaskCollection currMonthTasks = new FDCScheduleTaskCollection();
		
		ProjectSpecialInfo  psi = null;
		if(col.get(0)!= null 
				&& col.get(0).getSchedule() != null 
				&& col.get(0).getSchedule().getProjectSpecial() != null){
			psi = col.get(0).getSchedule().getProjectSpecial();
		}
		Map<String,Object[]> evaluationMap = new HashMap<String, Object[]>();
		if(project != null){
			evaluationMap=getEvaluationInformationByProject(project.getId().toString(),psi==null || psi.getId() == null);
		}
		String key = null;
		String evKey = null;
		
		for (int i = 0; i < col.size(); i++) {
			FDCScheduleTaskInfo task = col.get(i);
			
			key = task.getSrcID();
		    evKey = key +"_"+"1SCHEDULE";
		    if(evaluationMap!=null && evaluationMap.containsKey(evKey)){
		    	task.put("pEvaluationDate",evaluationMap.get(evKey)[0]);
		    	task.put("pEvaluationResult",evaluationMap.get(evKey)[1]);
		    }
		    evKey = key +"_"+"2QUALITY";
		    if(evaluationMap!=null && evaluationMap.containsKey(evKey)){
		    	task.put("qEvaluationDate",evaluationMap.get(evKey)[0]);
		    	task.put("qEvaluationResult",evaluationMap.get(evKey)[1]);
		    }
			
			if (project != null) {
				if (!task.getSchedule().getProject().getId().equals(project.getId())) {
					continue;
				}
			}
			addNextMonthTask(startOfMonth, endOfMonth, currMonthTasks, task);
		}
		return currMonthTasks;
	}

	public static FDCScheduleTaskCollection getCurrMonthTasks(FDCScheduleTaskCollection col, String date, CurProjectInfo project) throws ParseException {
		Date fromtDate = DateUtils.parse(date, "yyyy-MM");
		Date startOfMonth = DateUtils.startOfMonth(fromtDate);
		Date endOfMonth = DateUtils.endOfMonth(fromtDate);
		Calendar c = Calendar.getInstance();
		c.setTime(fromtDate);
		FDCScheduleTaskCollection currMonthTasks = new FDCScheduleTaskCollection();
		
		ProjectSpecialInfo  psi = null;
		if(col.get(0)!= null 
				&& col.get(0).getSchedule() != null 
				&& col.get(0).getSchedule().getProjectSpecial() != null){
			psi = col.get(0).getSchedule().getProjectSpecial();
		}
		
		Map<String,Object[]> evaluationMap = new HashMap<String, Object[]>();
		if(project != null){
			evaluationMap= getEvaluationInformationByProject(project.getId().toString(),psi==null || psi.getId() == null);
		}
		String key = null;
		String evKey = null;
		
		for (int i = 0; i < col.size(); i++) {
			FDCScheduleTaskInfo task = col.get(i);
			
			key = task.getSrcID();
		    evKey = key +"_"+"1SCHEDULE";
		    if(evaluationMap!=null && evaluationMap.containsKey(evKey)){
		    	task.put("pEvaluationDate",evaluationMap.get(evKey)[0]);
		    	task.put("pEvaluationResult",evaluationMap.get(evKey)[1]);
		    }
		    evKey = key +"_"+"2QUALITY";
		    if(evaluationMap!=null && evaluationMap.containsKey(evKey)){
		    	task.put("qEvaluationDate",evaluationMap.get(evKey)[0]);
		    	task.put("qEvaluationResult",evaluationMap.get(evKey)[1]);
		    }
			
			if (project != null) {
				if (!task.getSchedule().getProject().getId().equals(project.getId())) {
					continue;
				}
			}
			addCurrMonthTask(startOfMonth, endOfMonth, currMonthTasks, task);
		}
		return currMonthTasks;
	}

	public static Date getNextMonthOfYear(Date currMonthDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(currMonthDate);
		c.add(Calendar.MONTH, 1);// ��һ����
		c.set(Calendar.DATE, 1);// ����������Ϊ���µ�һ��
		return c.getTime();
	}

	private static void addCurrMonthTask(Date startOfMonth, Date endOfMonth, FDCScheduleTaskCollection currMonthTasks, FDCScheduleTaskInfo task) {
		try {
			// �Խ������ڵ����⴦�����磺2011-12-1 00:00:00 �� 2011-11-30 23:59:59 ��������Ϊ0��
			endOfMonth = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(endOfMonth));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// ������ʱ��,��Ϊ��ʵ���ȡ��ʱ������Ϊjava.sql.date ���Ƚϵ�����Ϊ java.util.date
		// System.err.println(task.getName());
		Date planStartDate = task.getStart();
		if (planStartDate != null) {
			Calendar temp_planStartDate = Calendar.getInstance();
			temp_planStartDate.setTime(planStartDate);
			planStartDate = temp_planStartDate.getTime();// �ƻ���ʼ
		}
		Date planEndDate = task.getEnd();
		if (planEndDate != null) {
			Calendar temp_planEndDate = Calendar.getInstance();
			temp_planEndDate.setTime(planEndDate);
			planEndDate = temp_planEndDate.getTime();// �ƻ�����
		}
		Date actualStartDate = task.getActualStartDate();
		if (actualStartDate != null) {
			Calendar temp_actualStartDate = Calendar.getInstance();
			temp_actualStartDate.setTime(actualStartDate);
			actualStartDate = temp_actualStartDate.getTime();// ʵ�ʿ�ʼ
		}
		Date actualEndDate = task.getActualEndDate();
		if (actualEndDate != null) {
			Calendar temp_actualEndDate = Calendar.getInstance();
			temp_actualEndDate.setTime(actualEndDate);
			actualEndDate = temp_actualEndDate.getTime();// ʵ�ʽ���
		}

		int complete = task.getComplete() == null ? 0 : task.getComplete().intValue();
		// 1���ƻ��������С�ڱ��£����깤�ٷֱ�С��100
		// if (planEndDate != null && DateTimeUtils.dateDiff(planEndDate,
		// startOfMonth) > 0 && complete < 100) {
		// currMonthTasks.add(task);
		// return;
		// }
		// // 2.1 �ƻ���ʼ�����ڱ���
		// if (planStartDate != null && DateTimeUtils.dateDiff(startOfMonth,
		// planStartDate) >= 0
		// && DateTimeUtils.dateDiff(endOfMonth, planStartDate) <= 0) {
		// currMonthTasks.add(task);
		// return;
		// }
		// // 2.2 �ƻ����������ڱ���
		// if (planEndDate != null && DateTimeUtils.dateDiff(startOfMonth,
		// planEndDate) >= 0
		// && DateTimeUtils.dateDiff(endOfMonth, planEndDate) <= 0) {
		// currMonthTasks.add(task);
		// return;
		// }
		// // 3�ƻ���ʼ�����ڱ��£����ƻ�������ڴ��ڱ���
		// if (planStartDate != null && planEndDate != null &&
		// DateTimeUtils.dateDiff(startOfMonth, planStartDate) < 0
		// && DateTimeUtils.dateDiff(endOfMonth, planEndDate) > 0) {
		// currMonthTasks.add(task);
		// return;
		// }
		// // 4 ʵ����������ڱ���
		// if (actualEndDate != null && DateTimeUtils.dateDiff(startOfMonth,
		// actualEndDate) >= 0
		// && DateTimeUtils.dateDiff(endOfMonth, actualEndDate) <= 0) {
		// currMonthTasks.add(task);
		// return;
		// }
		// // 5ʵ����������ڱ���
		// if (planStartDate != null && actualStartDate != null &&
		// DateUtils.diffdates(endOfMonth, planStartDate) > 0
		// && DateUtils.diffdates(startOfMonth, actualStartDate) >= 0 &&
		// DateUtils.diffdates(endOfMonth, actualStartDate) <= 0) {
		// currMonthTasks.add(task);
		// return;
		// }
		//		
		// if (planStartDate != null && planEndDate != null &&
		// DateTimeUtils.dateDiff(startOfMonth, planStartDate) < 0
		// && DateTimeUtils.dateDiff(endOfMonth, planEndDate) > 0) {
		// currMonthTasks.add(task);
		// }

		if (planEndDate != null && DateTimeUtils.dateDiff(startOfMonth, planEndDate) < 0 && complete < 100) {
			currMonthTasks.add(task);
		} else if (planStartDate != null && planEndDate != null && DateTimeUtils.dateDiff(startOfMonth, planStartDate) >= 0 && DateTimeUtils.dateDiff(endOfMonth, planEndDate) <= 0) {
			currMonthTasks.add(task);
		} else if (planStartDate != null && planEndDate != null && DateTimeUtils.dateDiff(startOfMonth, planStartDate) < 0 && DateTimeUtils.dateDiff(endOfMonth, planEndDate) > 0) {
			currMonthTasks.add(task);
		} else if (actualStartDate != null && DateTimeUtils.dateDiff(actualStartDate, startOfMonth) <= 0 && DateTimeUtils.dateDiff(actualStartDate, endOfMonth) >= 0) {
			currMonthTasks.add(task);
		} else if (actualEndDate != null && DateTimeUtils.dateDiff(actualEndDate, startOfMonth) <= 0 && DateTimeUtils.dateDiff(actualEndDate, endOfMonth) >= 0) {
			currMonthTasks.add(task);
		} else if (planEndDate != null && DateTimeUtils.dateDiff(startOfMonth, planEndDate) >= 0 && DateTimeUtils.dateDiff(planEndDate, endOfMonth) >= 0) {
			currMonthTasks.add(task);
		} else if (planEndDate != null && DateTimeUtils.dateDiff(startOfMonth, planStartDate) >= 0 && DateTimeUtils.dateDiff(planStartDate, endOfMonth) >= 0) {
			currMonthTasks.add(task);
		}

		//

		// if (isBetweenDateRange(startOfMonth, endOfMonth,
		// task.getActualEndDate())) {
		// currMonthTasks.add(task);
		// return;
		// }
	}

	private static void addNextMonthTask(Date startOfMonth, Date endOfMonth, FDCScheduleTaskCollection currMonthTasks, FDCScheduleTaskInfo task) {
		try {
			// �Խ������ڵ����⴦�����磺2011-12-1 00:00:00 �� 2011-11-30 23:59:59 ��������Ϊ0��
			endOfMonth = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(endOfMonth));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// ������ʱ��,��Ϊ��ʵ���ȡ��ʱ������Ϊjava.sql.date ���Ƚϵ�����Ϊ java.util.date
		Date planStartDate = task.getStart();
		if (planStartDate != null) {
			Calendar temp_planStartDate = Calendar.getInstance();
			temp_planStartDate.setTime(planStartDate);
			planStartDate = temp_planStartDate.getTime();// �ƻ���ʼ
		}
		Date planEndDate = task.getEnd();
		if (planEndDate != null) {
			Calendar temp_planEndDate = Calendar.getInstance();
			temp_planEndDate.setTime(planEndDate);
			planEndDate = temp_planEndDate.getTime();// �ƻ�����
		}
		int complete = task.getComplete() == null ? 0 : task.getComplete().intValue();
		if (planEndDate != null && DateUtils.diffdates(startOfMonth, planEndDate) >= 0 && DateUtils.diffdates(endOfMonth, planEndDate) <= 0 && complete < 100) {
			currMonthTasks.add(task);
			return;
		}
		if (planStartDate != null && DateUtils.diffdates(startOfMonth, planStartDate) >= 0 && DateUtils.diffdates(endOfMonth, planStartDate) <= 0 && complete < 100) {
			currMonthTasks.add(task);
			return;
		}
		if (planStartDate != null && planEndDate != null && DateUtils.diffdates(planStartDate, startOfMonth) > 0 && DateUtils.diffdates(endOfMonth, planEndDate) > 0 && complete < 100) {
			currMonthTasks.add(task);
			return;
		}

	}

	private static boolean isBetweenDateRange(final Date startOfMonth, final Date endOfMonth, final Date compareDate) {
		if (compareDate == null) {
			return false;
		}

		if (DateUtils.diffdates(compareDate, startOfMonth) > 0 && DateUtils.diffdates(endOfMonth, compareDate) > 0) {
			return true;
		} else {
			return false;
		}

	}

	public static Date[] getStartAndEndDate(FullOrgUnitInfo info, Map alreadyMap) throws BOSException, SQLException {

		Date[] startAndEndDate = null;
		if (alreadyMap.get(info.getId().toString()) != null) {
			return (Date[]) alreadyMap.get(info.getId().toString());

		} else {

			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select min(task.fstart),max(task.fend) from t_sch_fdcscheduletask task right outer join t_sch_fdcschedule sch on task.fscheduleid = sch.fid");
			builder
					.appendSql(" left outer join t_fdc_curproject prj on prj.fid = sch.fprojectid  where prj.ffullorgunit= ? and sch.fislatestver =1 and (sch.fprojectspecialid is null or fprojectspecialid = '')");
			builder.addParam(info.getId().toString());

			IRowSet rs = builder.executeQuery();
			while (rs.next()) {
				Date startDate = rs.getDate(1);
				Date endDate = rs.getDate(2);
				startAndEndDate = new Date[] { startDate, endDate };
			}
			alreadyMap.put(info.getId().toString(), startAndEndDate);

		}

		return startAndEndDate;

	}

	private static void fillCtrl(KDComboBox yearCtrl, Date startDate, Date endDate) throws BOSException {
		if (startDate != null && endDate != null) {
			Calendar startCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();

			startCal.setTime(startDate);
			endCal.setTime(endDate);

			int startYear = startCal.get(Calendar.YEAR);
			int endYear = endCal.get(Calendar.YEAR);

			int startMonth = startCal.get(Calendar.MONTH);
			int endMonth = endCal.get(Calendar.MONTH);

			for (int i = startYear; i <= endYear; i++) {
				yearCtrl.addItem(Integer.valueOf(i));
			}

		}
	}

	private static void fillCtrl(Date startDate, Date endDate) {
		if (startDate != null && endDate != null) {
			Calendar startCal = new GregorianCalendar();
			Calendar endCal = new GregorianCalendar();

			startCal.setTime(startDate);
			endCal.setTime(endDate);

			int startYear = startCal.get(Calendar.YEAR);
			int endYear = endCal.get(Calendar.YEAR);

			int startMonth = startCal.get(Calendar.MONTH);
			int endMonth = endCal.get(Calendar.MONTH);

			int s = 0;
			int e = 12;

			for (int i = startYear; i <= endYear; i++) {
				if (i == startYear) {
					s = startMonth;
				} else if (i == endYear) {
					e = endMonth;
				}

				for (int j = s; j < e; j++) {
					System.out.println(i + ":" + (j + 1));
				}
				s = 0;
				e = 12;
			}
		}
	}

	public static void initStateCell(boolean isEvaluation, boolean isParamControl, IRow row, FDCScheduleTaskInfo entryInfo) throws BOSException {
		int state = -1;
		state = ViewReportHelper.getState(isEvaluation, isParamControl, entryInfo);
		setStateValue(row, state);
	}

	private static void setStateValue(IRow row, int state) {
		if (row != null) {
			String rsPath = "com.kingdee.eas.fdc.schedule.ScheduleResource";
			// ��
			String achieve = EASResource.getString(rsPath, "achieve");
			// Ȧ
			String pending = EASResource.getString(rsPath, "pending");
			// ��
			Color green = new Color(10, 220, 10);
			// ��
			Color red = new Color(245, 0, 0);
			// ��
			Color orange = new Color(220, 180, 0);
			switch (state) {
			case FDCScheduleConstant.ONTIME:
				row.getCell("state").setValue(achieve);
				row.getCell("state").setUserObject("0");
				row.getCell("state").getStyleAttributes().setBold(true);
				row.getCell("state").getStyleAttributes().setFontColor(green);
				break;
			case FDCScheduleConstant.DELAY:
				row.getCell("state").setValue(achieve);
				row.getCell("state").setUserObject("1");
				row.getCell("state").getStyleAttributes().setBold(true);
				row.getCell("state").getStyleAttributes().setFontColor(red);
				break;
			case FDCScheduleConstant.CONFIRMED:
				row.getCell("state").setValue(pending);
				row.getCell("state").setUserObject("2");
				row.getCell("state").getStyleAttributes().setFontColor(orange);
				break;
			case FDCScheduleConstant.DELAYANDNOTCOMPLETE:
				row.getCell("state").setValue(pending);
				row.getCell("state").setUserObject("3");
				row.getCell("state").getStyleAttributes().setFontColor(red);
				break;
			}
		}
	}

	public static Map<String, ScheduleTaskProgressReportInfo> getScheudleReportData(FDCScheduleTaskCollection list) throws BOSException {
		if (list == null || list.isEmpty()) {
			return null;
		}
		Set<String> srcIds = new HashSet<String>();
		for (int i = 0; i < list.size(); i++) {
			srcIds.add(list.get(i).getSrcID());
		}

		Map<String, ScheduleTaskProgressReportInfo> reportMap = new HashMap<String, ScheduleTaskProgressReportInfo>();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("description");
		view.getSelector().add("intendEndDate");
		view.getSelector().add("completePrecent");
		view.getSelector().add("reportDate");
		view.getSelector().add("srcRelateTask");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcRelateTask", srcIds, CompareType.INCLUDE));
		view.setFilter(filter);

		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo item = new SorterItemInfo("reportDate");
		item.setSortType(SortType.DESCEND);
		sorter.add(item);
		view.setSorter(sorter);

		ScheduleTaskProgressReportCollection reportCol = ScheduleTaskProgressReportFactory.getRemoteInstance().getScheduleTaskProgressReportCollection(view);
		ScheduleTaskProgressReportInfo reportInfo = null;
		ScheduleTaskProgressReportInfo otherInfo = null;
		for (Iterator it = reportCol.iterator(); it.hasNext();) {
			reportInfo = (ScheduleTaskProgressReportInfo) it.next();
			if (reportMap.get(reportInfo.getSrcRelateTask()) == null) {
				reportMap.put(reportInfo.getSrcRelateTask(), reportInfo);
			} else {
				if (reportMap.get(reportInfo.getSrcRelateTask()) != null) {
					otherInfo = reportMap.get(reportInfo.getSrcRelateTask());
					if (DateTimeUtils.dateDiff(reportInfo.getReportDate(), otherInfo.getReportDate()) < 0) {
						reportMap.remove(reportInfo.getSrcRelateTask());
					}
				}

			}
		}
		return reportMap;
	}
}
