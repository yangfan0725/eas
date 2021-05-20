/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MainScheduleEditUI extends AbstractMainScheduleEditUI {
	private static final Logger logger = CoreUIObject.getLogger(MainScheduleEditUI.class);
	

	/**
	 * output class constructor
	 */
	public MainScheduleEditUI() throws Exception {
		super();
	}
	
	


	protected FDCScheduleInfo getNewData() throws EASBizException, BOSException {
		Map param = new HashMap();
		CurProjectInfo prj = (CurProjectInfo) getUIContext().get("CurProject");
		if (prj == null) {
			FDCMsgBox.showWarning(this, "������ĿΪ�գ�");
			SysUtil.abort();
		}
		param.put("adminDeptId", SysContext.getSysContext().getCurrentOrgUnit().getId().toString());// ��ڲ���
		param.put("prjId", prj.getId().toString());// ������Ŀ
		param.put("taskTypeId", TaskTypeInfo.TASKTYPE_MAINTASK);// ����רҵ����
		FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance().getNewData(param);
		
		return info;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.actionSubmitOption.setVisible(false);
		this.btnSetCheckVersion.setVisible(false);
//		this.menuItemSetCheckVersion.setVisible(false);
	}
	
	protected void loadData2Gantt(FDCScheduleInfo editData) throws Exception {
		super.loadData2Gantt(editData);
		setScheduleStage();
	}
	
	@Override
	public void onLoad() throws Exception {
		super.onLoad();
		setScheduleStage();
		this.btnSetCheckVersion.setIcon(EASResource.getIcon("imgTbtn_execute"));
		this.menuItemSetCheckVersion.setIcon(EASResource.getIcon("imgTbtn_execute"));
	}
	
	@Override
	protected void afterOnload() {
		super.afterOnload();
		prmtProjectStage.setEnabled(false);
		
	}
	
	@Override
	protected void changePageState() throws BOSException, SQLException {
		// TODO Auto-generated method stub
		super.changePageState();
		boolean isEdit = oprtState.equals(STATUS_EDIT);
		this.contProjectStage.setEnabled(isEdit);
		this.prmtProjectStage.setEnabled(isEdit);
	}
	
	private void setScheduleStage(){
		if(editData.getScheduleStage()!=null){
			prmtProjectStage.setDataNoNotify(editData.getScheduleStage());
		}
	}
	
	/**
	 * @description �Ƿ�����
	 * @author �ź���
	 * @createDate 2011-9-5
	 * @version EAS7.0
	 * @see
	 */

	protected boolean isMainSchedule() {
		return true;
	}
	
	protected String getUIName() {
		return MainScheduleEditUI.class.getName();
	}
	/**
	 * 
	 * ѡ��汾��У���Ƿ��Ѿ������˺���汾
	 * 
	 */
	protected void prmtProjectStage_dataChanged(DataChangeEvent e)
			throws Exception {
		if(e.getNewValue() != null && !e.getNewValue().equals(e.getOldValue())){
			MeasureStageInfo stageInfo = (MeasureStageInfo) e.getNewValue();
			editData.setScheduleStage(stageInfo);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			if (editData.getProject() != null && editData.getProjectSpecial() == null) {
				String version = "ִ�а�";
				String stage = editData.getScheduleStage()!= null?editData.getScheduleStage().getName():"";
				editData.setVersionName(editData.getProject().getName()+stage
						+version+ new Float(editData.getVersion())
						+"_"+sdf.format(new Date()));
			}
			this.txtVersion.setText(editData.getVersionName());
		}
	}


    /**
     * ����ܷ���Ƶ�ǰ�׶εļƻ�
     * @param stageInfo
     * @throws BOSException 
     * @throws SQLException 
     */
	private void checkStage(MeasureStageInfo stageInfo,CurProjectInfo projectInfo) throws BOSException, SQLException {
		String sql = "select max(convert(int,fnumber)) from t_fdc_measurestage where fid in (select isnull(fschedulestage,0) from t_sch_fdcschedule where fprojectid = ?)";
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(sql);
		builder.addParam(projectInfo.getId().toString());
		
		int maxNumber = -1;
		IRowSet rs = builder.executeQuery();
		while(rs.next()){
			maxNumber = rs.getInt(1);
		}
		int currentNumber = Integer.parseInt(stageInfo.getNumber()==null?"0":stageInfo.getNumber());
		if(currentNumber<maxNumber){
			FDCMsgBox.showError("�Ѿ����ƹ������׶εļƻ��������ٱ��Ƶ�ǰ�׶εļƻ���������ѡ��");
			abort();
		}
	}
	
	protected void prmtProjectStage_willShow(SelectorEvent e) throws Exception {
		super.prmtProjectStage_willShow(e);
		prmtProjectStage.getQueryAgent().resetRuntimeEntityView();
		CurProjectInfo projectInfo = (CurProjectInfo) prmtCurproject.getValue();
		if(projectInfo != null){
			//���ڲ���׶ε����ʧ�󣬴˴�����ֲ�����������ڲ���׶��ϼ���һ��Ϊnumeric����˳���ֶ�
			//ȡ����ǰ������Ŀ������ƻ�������׶�
			FilterInfo filter  = new FilterInfo();
			String sql = "select fid from t_fdc_measurestage where fnumber>(select isnull(max(fnumber),0) from t_fdc_measurestage where fid in(select fschedulestage from t_sch_fdcschedule where fprojectspecialid is null and fprojectid='"+projectInfo.getId().toString()+"'))";
			filter.getFilterItems().add(new FilterItemInfo("id",sql,CompareType.INNER));
			filter.appendFilterItem("isEnabled", 1);
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			prmtProjectStage.setEntityViewInfo(view);
		}
		
	}
	
	public void actionSetCheckVersion_actionPerformed(ActionEvent e) throws Exception {
		FDCScheduleFactory.getRemoteInstance().setCheckVersion(editData.getId());
		editData.setVersionName(editData.getVersionName().replace("ִ�а�", "���˰�"));
		this.txtVersion.setText(editData.getVersionName());
		FDCMsgBox.showInfo("���˰汾���óɹ���");
	}
	
	
	
}