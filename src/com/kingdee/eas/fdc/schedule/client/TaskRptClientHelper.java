package com.kingdee.eas.fdc.schedule.client;

import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;

public class TaskRptClientHelper {
	private TaskRptClientHelper(){};
	
	public static String getScheduleDigest(FDCScheduleInfo info){
		StringBuffer digest=new StringBuffer();
		digest.append("�ƻ�����: ");
		digest.append(info.getNumber());
		digest.append(";�ƻ�����: ");
		digest.append(info.getName());
		digest.append(";����������Ŀ: ");
		digest.append(info.getProject()!=null?info.getProject().getName():"");
		digest.append(";�ƻ�������: ");
		digest.append(info.getAdminPerson()!=null?info.getAdminPerson().getName():"");
		return digest.toString();
	}
	
	public static String getProjectFilterSql(){
		//ȡ��ǰ������֯�ڵĹ�����Ŀ
		OrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
		if(currentFIUnit==null){
			//ȡ������ǰ������֯�Ļ�ֻ�ܿ���ǰ��֯���Ƿ��в�����֯��
			currentFIUnit=SysContext.getSysContext().getCurrentOrgUnit();
		}
		StringBuffer sql=new StringBuffer();
		sql.append("select fid from T_fdc_curProject where ffullorgunit in ( " );
		sql.append("   select company.fid from t_org_baseunit parent ,T_org_company company ");
		sql.append("	where parent.fid='"+currentFIUnit.getId().toString()+"'");
		sql.append("      and charindex(parent.flongnumber||'!',company.flongnumber||'!')=1  and company.fisbizunit=1 ");
		sql.append(") 	");
		return sql.toString();
	}
	
	public static String getProjectByTaskAdminDempFilterSql(){
//		//ȡ��ǰ������֯�ڵĹ�����Ŀ
//		OrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
//		if(currentFIUnit==null){
			//ȡ������ǰ������֯�Ļ�ֻ�ܿ���ǰ��֯���Ƿ��в�����֯��
		OrgUnitInfo currentFIUnit=SysContext.getSysContext().getCurrentOrgUnit();
//		}
		StringBuffer sql=new StringBuffer();
		sql.append("select project.fid from T_fdc_curProject project,T_SCH_FDCSchedule schedule,T_SCH_FDCScheduleTask task ");
		sql.append(" where project.fid=schedule.FProjectID and schedule.fid=task.fscheduleid ");
		sql.append(" and schedule.FIsLatestVer=1 and task.FAdminDeptID='"+currentFIUnit.getId().toString()+"'");
		return sql.toString();
	}
	public static String getProjectByTaskAdminPersonFilterSql(){
		UserInfo user=SysContext.getSysContext().getCurrentUserInfo();
		PersonInfo person = user.getPerson();
		StringBuffer sql=new StringBuffer();
		sql.append("select project.fid from T_fdc_curProject project,T_SCH_FDCSchedule schedule,T_SCH_FDCScheduleTask task ");
		sql.append(" where project.fid=schedule.FProjectID and schedule.fid=task.fscheduleid ");
		sql.append(" and schedule.FIsLatestVer=1 and task.FAdminPersonID='"+person.getId().toString()+"'");
		return sql.toString();
	}
}
