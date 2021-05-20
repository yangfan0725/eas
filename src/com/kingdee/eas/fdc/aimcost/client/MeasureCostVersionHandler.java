package com.kingdee.eas.fdc.aimcost.client;

import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class MeasureCostVersionHandler {

	private int lastVerisonNumber = 0;

	//֧��һ������˽����������ɾ��
	public MeasureCostVersionHandler(String orgId, String prjId, boolean isAimMeasure) throws BOSException, SQLException {
		/*
		 * String sql = "select FVersionNumber from T_Aim_MeasureCost " +
		 * "where FProjectId='" + projectId + "'";
		 */
		// String sql =
		// "select FVersionNumber from T_Aim_MeasureCost where FOrgUnitId='" +
		// orgId + "' and FProjectID='"+prjId+"'  ";
		String sql = "select fversionnumber from t_aim_measurecost where  fprojectid='" + prjId + "'  ";
		if (isAimMeasure) {
			sql += " and FisAimMeasure=1";
		} else {
			sql += " and FisAimMeasure=0";
		}
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		while (rs.next()) {
			String versionNumber = rs.getString("FVersionNumber");
			// versionNumber.replaceAll("V", "");
			int k = versionNumber.indexOf("!");
			String number = versionNumber.substring(0, k);
			if (Integer.parseInt(number) > this.lastVerisonNumber) {
				this.lastVerisonNumber = Integer.parseInt(number);
			}
		}
	}
	
	/**
	 * ���Ӳ���׶Σ��ڸ��׶��д�汾��С�汾 
	 * modified by hpw
	 * @param orgId
	 * @param prjId
	 * @param isAimMeasure
	 * @throws BOSException
	 * @throws SQLException
	 */
	public MeasureCostVersionHandler(String orgId, String prjId, boolean isAimMeasure,MeasureStageInfo stageInfo) throws BOSException, SQLException {
		String sql = "select FVersionNumber from T_Aim_MeasureCost where FOrgUnitId='" +
		 orgId +"' ";
		if(prjId!=null){
			//��֯����Ŀ��������Ŀ��ɱ����� 
			sql+= " and FProjectID='"+prjId+"'  ";
		}
//		String sql = "select fversionnumber from t_aim_measurecost where  fprojectid='" + prjId + "'  ";
		if (isAimMeasure) {
			sql += " and FisAimMeasure=1";
		} else {
			sql += " and FisAimMeasure=0";
		}
		if(stageInfo!=null){
			sql+=" and fmeasurestageid= '"+stageInfo.getId().toString()+"'";
		}
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		while (rs.next()) {
			String versionNumber = rs.getString("FVersionNumber");
			int k = versionNumber.indexOf("!");
			String number = versionNumber.substring(0, k);
			if (Integer.parseInt(number) > this.lastVerisonNumber) {
				this.lastVerisonNumber = Integer.parseInt(number);
			}
		}
	}

	public String getFirstVersion() {
		return "V1!0";
	}

	public static String getPreVersion(String versionNumber) {
//		versionNumber.replaceAll("V", "");
		int k = versionNumber.indexOf("!");
		String number = versionNumber.substring(0, k);
		int num = Integer.parseInt(number);
		if (num <= 1) {
			return null;
		}
		return (num - 1) + "!0";
	}

	public static String getNextVersion(String versionNumber) {
//		versionNumber.replaceAll("V", "");
		int k = versionNumber.indexOf("!");
		String number = versionNumber.substring(0, k);
		int num = Integer.parseInt(number);
		return (num + 1) + "!0";
	}

	public String getLastVersion() {
		return this.lastVerisonNumber + "!0";
	}

	public void verifyVersion(String versionNumber) {
		if (versionNumber == null) {
			return;
		}
//		versionNumber.replaceAll("V", "");
		int k = versionNumber.indexOf("!");
		String number = versionNumber.substring(0, k);
		int num = Integer.parseInt(number);
		if (num > this.lastVerisonNumber) {
			SysUtil.abort();
		}
	}

}
