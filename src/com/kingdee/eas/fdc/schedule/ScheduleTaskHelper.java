/**
 * 
 */
package com.kingdee.eas.fdc.schedule;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.message.BMCMessageFactory;
import com.kingdee.eas.base.message.BMCMessageInfo;
import com.kingdee.eas.base.message.IBMCMessage;
import com.kingdee.eas.base.message.MsgBizType;
import com.kingdee.eas.base.message.MsgPriority;
import com.kingdee.eas.base.message.MsgStatus;
import com.kingdee.eas.base.message.MsgType;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.receiver.BasReceiverCollection;
import com.kingdee.eas.base.receiver.BasReceiverInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.IContextHelper;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������
 * 
 * @author �ź���
 * @version EAS7.0
 * @createDate 2011-11-14
 * @see
 */ 

public class ScheduleTaskHelper {

	/**
	 * 
	 * @description �Ƿ���в�������
	 * @author �ź���
	 * @createDate 2011-11-4
	 * @return boolean
	 * @version EAS7.0
	 * @see
	 */
	public static boolean isStartingParamControl(Context ctx) {
		String companyId = "";
		
		if (null != ContextUtil.getCurrentFIUnit(ctx).getId()) {
			companyId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		}
		ObjectUuidPK id = new ObjectUuidPK(BOSUuid.read(companyId));
		// ������������
		// ר������ļƻ�������ڴ��������������ļƻ��������
		String fdcParamValue = "";
		try {
			fdcParamValue = ParamControlFactory.getLocalInstance(ctx).getParamValue(id, "FDCSH011");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return !"0".equals(fdcParamValue);
	}
	
	public static boolean isLatestVer(Context ctx, BOSUuid id) throws SQLException, BOSException {
		FDCSQLBuilder t_sql = new FDCSQLBuilder(ctx);
		t_sql.appendSql(" select sch.fisLatestVer from T_SCH_FDCScheduleTask as sche ");
		t_sql.appendSql(" LEFT OUTER JOIN T_SCH_FDCSchedule as sch ");
		t_sql.appendSql(" on sche.FScheduleID =sch.fid  ");
		t_sql.appendSql(" where sche.fsrcid = '" + id.toString() + "' ");
		t_sql.appendSql(" and sch.fisLatestVer=1 ");
		IRowSet rs = t_sql.executeQuery();
		int isLatestVer = 0;
		if (rs.next()) {
			isLatestVer = rs.getInt(1);
		}
		if (isLatestVer == 1) {
			return true;
		}
		return false;
	}
	
	public static boolean isScheduleAppraise(Context ctx, BOSUuid id) throws SQLException, BOSException {
		if (isStartingParamControl(ctx)) {
			FDCSQLBuilder t_sql = new FDCSQLBuilder(ctx);
			t_sql.appendSql(" select eval.fevaluationtype from T_SCH_FDCScheduleTask as sche ");
			t_sql.appendSql(" LEFT OUTER JOIN T_SCH_TaskEvaluationbill as eval ");
			t_sql.appendSql(" on sche.fsrcid =eval.fsrcrelatetask where sche.fsrcid = '" + id.toString() + "' ");
			IRowSet rs = t_sql.executeQuery();
			String type = "";
			while (rs.next()) {
				type = rs.getString("fevaluationtype");
			}
			if ("".equals(type) || type == null) {
				return false;
			}
		}
		return true;
	}

	/***
	 * 1�����ô����۲�����2������=100%��3��δ���н������ۻ��������� �����������۷���true�����򷵻�false;4�Ӽ�����
	 */
	public static boolean isNeedEvaluation(Context ctx, BOSUuid id) throws SQLException, BOSException {
		// ���ô����۲���
		if (isStartingParamControl(ctx)) {
			FDCSQLBuilder t_sql = new FDCSQLBuilder(ctx);
			t_sql.appendSql(" select sch.fVersionname from T_SCH_FDCScheduleTask as sche ");
			t_sql.appendSql(" LEFT OUTER JOIN T_SCH_TaskEvaluationbill as eval ");
			t_sql.appendSql(" on sche.fsrcid =eval.fsrcrelatetask  ");
			t_sql.appendSql(" LEFT OUTER JOIN T_SCH_FDCSchedule as sch ");
			t_sql.appendSql(" on sche.FScheduleID =sch.fid  ");
			t_sql.appendSql(" where sche.fsrcid = '" + id.toString() + "' ");
			t_sql.appendSql(" and sch.fisLatestVer=1 ");
			t_sql.appendSql(" and sche.fcomplete=100 ");
			t_sql.appendSql(" and eval.FEvaluationResult is null ");
			t_sql.appendSql(" and sche.FIsLeaf = 1 ");

			IRowSet rs = t_sql.executeQuery();
			String type = "";
			if (rs.next()) {
				type = rs.getString(1);
			}
			if (type != null && !("".equals(type))) {
				return true;
			}			
		}
		return false;
	}

	/***
	 * 
	 * ��������ǰ���ڴ��ڼƻ�������ڷ���true�����򷵻�false
	 */
	public static boolean isDelay(Context ctx, BOSUuid id) throws SQLException, BOSException {
		FDCSQLBuilder t_sql = new FDCSQLBuilder(ctx);
		t_sql.appendSql(" select sche.fend from T_SCH_FDCScheduleTask as sche ");
		t_sql.appendSql(" LEFT OUTER JOIN T_SCH_FDCSchedule as sch ");
		t_sql.appendSql(" on sche.FScheduleID =sch.fid  ");
		t_sql.appendSql(" where sche.fsrcid = '" + id.toString() + "' ");
		t_sql.appendSql(" and sch.fisLatestVer=1 ");
		
		IRowSet rs = t_sql.executeQuery();
		Date end = null;
		if (rs.next()) {
			end = rs.getDate(1);
		}
		if (end != null) {
			Date now = new Date();
			if (days(end, now) > 0) {
				return true;
			}
		}
		return false;
	}
	
	public static int days(Date starDate, Date endDate) {
		long a = truncateDate(endDate).getTime();
		long b = truncateDate(starDate).getTime();
		long c = a - b;
		long y = c / (1000 * 60 * 60 * 24);
		return Integer.parseInt(y + "");
	}
	
	public static Date truncateDate(Date dt) {
		if (dt == null) {
			return null;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			cal.set(cal.get(1), cal.get(2), cal.get(5), 0, 0, 0);
			return new Date((cal.getTimeInMillis() / 1000L) * 1000L);
		}
	}
	/***
	 * 
	 * ���������ڷ���true�����򷵻�false
	 */
	public static boolean isNotCompleted(Context ctx, BOSUuid id) throws SQLException, BOSException {
		if (isNotComplete(ctx, id) && isDelay(ctx, id)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotComplete(Context ctx, BOSUuid id) throws SQLException, BOSException {
		FDCSQLBuilder t_sql = new FDCSQLBuilder(ctx);
		t_sql.appendSql(" select sche.fcomplete from T_SCH_FDCScheduleTask as sche ");
		t_sql.appendSql(" LEFT OUTER JOIN T_SCH_FDCSchedule as sch ");
		t_sql.appendSql(" on sche.FScheduleID =sch.fid  ");
		t_sql.appendSql(" where sche.fsrcid = '" + id.toString() + "' ");
		t_sql.appendSql(" and sch.fisLatestVer=1 ");
		IRowSet rs = t_sql.executeQuery();
		float complete = 0.0f;
		if (rs.next()) {
			complete = rs.getFloat(1);
		}
		if (complete >= 100) {
			return false;
		}
		return true;
	}
	/***
	 * 
	 * ������ǰ��������ת
	 * 
	 * @param newTasks
	 * @param taskEntrys
	 * @return �����ˣ�yuanjun_lan ����ʱ�䣺2012-2-14
	 */
	// public static FDCScheduleTaskCollection
	// getRelationReverseTasks(FDCScheduleTaskCollection newTasks,
	// FDCScheduleTaskCollection taskEntrys) {
	// /** �����������е�ǰ��������������� **/
	// for (int i = 0; i < taskEntrys.size(); i++) {
	// FDCScheduleTaskInfo task = taskEntrys.get(i);
	// FDCScheduleTaskInfo clone = (FDCScheduleTaskInfo) task.clone();
	// clone.getDepends().clear();
	// newTasks.add(clone);
	// }
	//
	// for (int i = 0; i < taskEntrys.size(); i++) {
	// FDCScheduleTaskInfo task = taskEntrys.get(i);
	// ScheduleTaskDependCollection depends = task.getDepends();
	// /** ѭ�������е�ǰ������ **/
	// for (int j = 0; j < depends.size(); j++) {
	// ScheduleTaskDependInfo depend = depends.get(j);
	// ScheduleTaskDependInfo clone = (ScheduleTaskDependInfo) depend.clone();
	// /** �ڿ�¡��ǰ�������з������ģ������ǰ������(��ʱǰ�������зŵ���ǰ������,ǰ�������зŵ�����) **/
	// clone.put("task", depend.getDependTaskBase());
	// clone.put("dependTask", depend.getTaskBase());
	// newTasks.get(i).getDepends().add(clone);
	// }
	// }
	// return newTasks;
	// }
	// public static void sendBMCMsgInfo(Context ctx, String title, String body,
	// UserInfo user, String billId) throws BOSException {
	// if (user == null) {
	// return;
	// }
	// IBMCMessage iBMCMessage;
	// IContextHelper localInstance;
	// if (ctx == null) {
	// iBMCMessage = BMCMessageFactory.getRemoteInstance();
	// localInstance = ContextHelperFactory.getRemoteInstance();
	// } else {
	// iBMCMessage = BMCMessageFactory.getLocalInstance(ctx);
	// localInstance = ContextHelperFactory.getLocalInstance(ctx);
	//
	// }
	// BMCMessageInfo msgInfo = new BMCMessageInfo();
	// msgInfo.setTitle(title);
	// msgInfo.setBody(body);
	// msgInfo.setPriority(MsgPriority.HIGH);
	// msgInfo.setReceivers(user.getName());
	// msgInfo.setNreceivers(user.getName());
	// msgInfo.setType(MsgType.NOTICE);
	// msgInfo.setBizType(MsgBizType.XITONGOFFICE);
	// msgInfo.setStatus(MsgStatus.UNREADED);
	// msgInfo.setId(BOSUuid.create(msgInfo.getBOSType()));
	// msgInfo.setOrgType(OrgType.ControlUnit);
	// msgInfo.setOrgID(localInstance.getCurrentCtrlUnit().getId().toString());
	// msgInfo.setSender(localInstance.getCurrentUser().getName());
	// // msgInfo.setSourceIDs("|" + billId + "|" + billId + "|" + billId);
	//
	// BasReceiverInfo receiverInfo = new BasReceiverInfo();
	// receiverInfo.setType(ReceiverObjType.STATIC_USER);
	// receiverInfo.setValue(user.getId().toString());
	// receiverInfo.setDesc(user.getName());
	// // Ĭ���ڷ�����porlet��ʾ
	// receiverInfo.setIsShow(false);
	// BasReceiverCollection receivercoll = new BasReceiverCollection();
	// receivercoll.add(receiverInfo);
	// iBMCMessage.addHandMsg(msgInfo, receivercoll);
	//
	// }
	public static void sendBMCMsgInfo(Context ctx, String title, String body, UserInfo user, String billId) throws BOSException {
		if (user == null)
			return;
		IBMCMessage iBMCMessage;
		IContextHelper localInstance;
		if (ctx == null) {
			iBMCMessage = BMCMessageFactory.getRemoteInstance();
			localInstance = ContextHelperFactory.getRemoteInstance();
		} else {
			iBMCMessage = BMCMessageFactory.getLocalInstance(ctx);
			localInstance = ContextHelperFactory.getLocalInstance(ctx);
		}

		BMCMessageInfo msgInfo = new BMCMessageInfo();
		msgInfo.setTitle(title);
		msgInfo.setBody(body);
		msgInfo.setPriority(MsgPriority.HIGH);
		msgInfo.setReceivers(user.getName());
		msgInfo.setNreceivers(user.getName());
		msgInfo.setType(MsgType.NOTICE);
		msgInfo.setBizType(MsgBizType.XITONGOFFICE);
		msgInfo.setStatus(MsgStatus.UNREADED);
		msgInfo.setId(BOSUuid.create(msgInfo.getBOSType()));
		// ҵ����Ϣ����'s Ԥ����ϢID�ַ���,Ŀ�ķų����ݰ�ť
		msgInfo.setSourceIDs("|" + billId + "|" + billId + "|" + billId);

		msgInfo.setOrgType(OrgType.ControlUnit);
		msgInfo.setOrgID(localInstance.getCurrentCtrlUnit().getId().toString());
		msgInfo.setSender(localInstance.getCurrentUser().getName());

		BasReceiverInfo receiverInfo = new BasReceiverInfo();
		receiverInfo.setType("1");
		receiverInfo.setValue(user.getId().toString());
		receiverInfo.setDesc(user.getName());

		receiverInfo.setIsShow(false);
		BasReceiverCollection receivercoll = new BasReceiverCollection();
		receivercoll.add(receiverInfo);
		iBMCMessage.addHandMsg(msgInfo, receivercoll);
	}
}
