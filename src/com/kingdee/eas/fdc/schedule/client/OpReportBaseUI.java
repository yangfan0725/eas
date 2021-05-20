/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.schedule.ProjectReportFacadeFactory;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ���ȱ���������
 */
public abstract class OpReportBaseUI extends AbstractOpReportBaseUI {
	private static final Logger logger = CoreUIObject.getLogger(OpReportBaseUI.class);
	
	private boolean isNeedRefresh = false;
	
	protected boolean isDisableChEndDate = false;
	
	public boolean isNeedRefresh() {
		return isNeedRefresh;
	}
	
	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		isDisableChEndDate = FDCUtils.getBooleanValue4FDCParamByKey(null, SysContext.getSysContext().getCurrentCostUnit().getId()+"", "FDCSCH_170618");
	}

	public void setNeedRefresh(boolean isNeedRefresh) {
		this.isNeedRefresh = isNeedRefresh;
	}

	public OpReportBaseUI() throws Exception {
		super();
	}

	/*
	 * ��ȡ���ֵ ��������
	 */
	protected void fetchInitData() throws Exception {

	}

	/*
	 * ��ȡ���ֵ ���÷������˷��� 1����ȡ���ܵ����񣬼������������ 2����ȡ���ܵ����� �������¶ȱ��棬�����ܱ��棬���ɵ��ô˷���
	 * �뽫��ȡ��ֵ������ProjectWeekReportEntryInfo�����뵽editData��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#fetchInitData()
	 */
	protected Map fetchData() throws Exception {
		return ProjectReportFacadeFactory.getRemoteInstance().getPeriodTask(getMainFilterParam());
	}

	/*
	 * ������Ӧ�Ĺ�������
	 */
	protected abstract Map getMainFilterParam() throws Exception;

	public void storeFields() {
		super.storeFields();
		storeLineFields();
	}

	public void loadFields() {
		super.loadFields();
		loadLineFields();
	}

	public void loadLineFields() {
		loadThisTableFields();
		loadNextTableFields();
	}

	/***
	 * �������е����ݣ���ʾ�����ڱ����
	 */
	public abstract void loadThisTableFields();

	/***
	 * �������е����ݣ���ʾ�����ڱ����
	 */
	public abstract void loadNextTableFields();

	public void storeLineFields() {
		storeThisTableFields();
		storeNextTableFields();
	}

	/***
	 * �����ڱ���е����ݣ�ͬ���洢��������
	 */
	public abstract void storeThisTableFields();

	/***
	 * �����ڱ���е����ݣ�ͬ���洢��������
	 */
	public abstract void storeNextTableFields();

	public boolean destroyWindow() {
		 try {
			if ((getUIContext().get("Owner")) instanceof FDCScheduleBaseEditUI && isNeedRefresh) {
				FDCScheduleBaseEditUI ui = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
				ui.refresh();

			}
				
		} catch (Exception e) {
			// �˴��쳣����Ե��������շ��쳣��һ�����������ǰ������Ѿ��رգ����˴�ȥ��ˢ�£��ᱨ�쳣��
			// �������������Ŀ���±���ģʽ�Ի���
			logger.error("��ר��ƻ������ѹرա��޷�ˢ�½���");
			// handUIException(e);
		}
		return super.destroyWindow();
	}

	/**
	 * �õ���Ŀ����
	 * 
	 * @return
	 */
	protected CurProjectInfo getCurProject() {
		CurProjectInfo curProjectInfo = null;
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof MainScheduleExecuteUI) {
			MainScheduleExecuteUI scheduleExecuteUI = (MainScheduleExecuteUI) getUIContext().get("Owner");
			curProjectInfo = scheduleExecuteUI.curprojectInfo;
			return curProjectInfo;
		} else if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof SpecialScheduleExecuteUI) {
			SpecialScheduleExecuteUI specialScheduleExecuteUI = (SpecialScheduleExecuteUI) getUIContext().get("Owner");
			curProjectInfo = specialScheduleExecuteUI.curprojectInfo;
		}
		
		if (curProjectInfo == null) {
			curProjectInfo = editData.getProject();
		}
	
		return curProjectInfo;
	}
	
	/**
	 * �����ܱ���ID��ѯ������Ŀ��ID�������ڽ���δ���빤����ĿID�����
	 */
	public String getCurProjectIDByEditDataID() throws BOSException, EASBizException, SQLException {
		String editDataId = getUIContext().get("ID").toString();
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select fprojectid from " + getReportTableName() + " where fid=?  ");
		if (editDataId != null) {
			sql.addParam(editDataId);
		} else {
			sql.addParam(" ");
		}

		IRowSet rs = sql.executeQuery();
		String curProjectID = "";
		while (rs.next()) {
			curProjectID = rs.getString(1);
		}
		return curProjectID;
	}
	
	protected abstract String getReportTableName();

	/**
	 * �����ý��洫�Ρ���editDataID�ķ�ʽ��ȡ�ù�����Ŀ��ID
	 * �����ø����������
	 * @Author��owen_wen
	 * @CreateTime��2013-6-27
	 */
	protected String getCurProjectIDForAll() throws EASBizException, BOSException, SQLException {
		if (getCurProject() != null) // ���洫����ֵ
			return getCurProject().getId().toString();
		else
			return getCurProjectIDByEditDataID();
	}
}