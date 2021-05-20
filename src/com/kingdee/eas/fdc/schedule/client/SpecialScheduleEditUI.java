/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.ScheduleFacadeFactory;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class SpecialScheduleEditUI extends AbstractSpecialScheduleEditUI {
	private static final Logger logger = CoreUIObject.getLogger(SpecialScheduleEditUI.class);

	/**
	 * output class constructor
	 */
	public SpecialScheduleEditUI() throws Exception {
		super();
	}
	
	protected IObjectValue createNewData() {
		/* TODO �Զ����ɷ������ */
		return super.createNewData();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		prmtPrjSpecial.setRequired(true);
		/** ��ʼ��ר�� **/
		if (editData != null && editData.getId() != null && editData.getProjectSpecial() != null) {
			prmtPrjSpecial.setDataNoNotify(editData.getProjectSpecial());
		}
		
		prmtPrjSpecial.addSelectorListener(new SelectorListener() {
			
			public void willShow(SelectorEvent e) {
				CurProjectInfo project = null;
				if (prmtCurproject.getValue() != null) {
					project = (CurProjectInfo) prmtCurproject.getValue();
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString()));
					view.setFilter(filter);
					prmtPrjSpecial.setEntityViewInfo(view);
					prmtPrjSpecial.getQueryAgent().resetRuntimeEntityView();
				}else{
					FDCMsgBox.showError("����ѡ�񹤳���Ŀ��");
					abort();
				}
				
			}
		});
		
	}
	
	/**
	 * @description
	 * @author �ź���
	 * @createDate 2011-9-6
	 * @version EAS7.0
	 * @see
	 */

	public void onShow() throws Exception {
		if(getScheduleGanttProject() == null){
			abort();
		}
		super.onShow();
		if (isFromWf()) {
			this.contSpecialProject.setEnabled(false);
		}
		
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.actionHideOther.setVisible(true);
		this.actionDisplayAll.setVisible(true);
		prmtPrjSpecial.setRequired(true);
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (this.editData != null && this.editData.getId() != null && oprtState != null && oprtState.equals(OprtState.EDIT)) {
			// �鿴״̬�ſ���ʹ����ʾ���������������
			this.actionDisplayAll.setEnabled(true);
			this.actionHideOther.setEnabled(false);
		} else {
			this.actionDisplayAll.setEnabled(false);
			this.actionHideOther.setEnabled(false);
		}
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
		param.put("taskTypeId", TaskTypeInfo.TASKTYPE_SPECIALTASK);// ����רҵ����
		FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance().getNewData(param);
		return info;
	}

	public void actionDisplayAll_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData == null || this.editData.getId() == null) {
			FDCMsgBox.showWarning(this, "���ȱ���!");
			SysUtil.abort();
		}
		super.actionDisplayAll_actionPerformed(e);
		this.editData = ScheduleFacadeFactory.getRemoteInstance().getOtherDeptSchedule(this.editData.getId().toString());
		this.editData.setEditable(true);
		setDataObject(this.editData);

		load2Gantt(this.editData);
		this.actionDisplayAll.setEnabled(false);
		this.actionHideOther.setEnabled(true);
	}

	public void actionHideOther_actionPerformed(ActionEvent e) throws Exception {
		super.actionHideOther_actionPerformed(e);
		this.editData = FDCScheduleFactory.getRemoteInstance().getScheduleInfo(new ObjectUuidPK(this.editData.getId()));
		this.editData.setEditable(true);
		setDataObject(this.editData);
		load2Gantt(this.editData);
		this.actionDisplayAll.setEnabled(true);
		this.actionHideOther.setEnabled(false);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// ��ǰ��֯��������һ�²����޸�
		// checkAdminDept();
		super.actionEdit_actionPerformed(e);
		prmtPrjSpecial.setEditable(false);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// checkAdminDept();
		// ר�����ʱ��Ӧ�ò���ɾ��������������������ŵļƻ� add by warship at 2010/07/01
		List list = this.getScheduleGanttProject().getTaskSelectionContext().getSelectedTasks();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null && list.get(i) instanceof KDTask) {
				KDTask ttask = (KDTask) (list.get(i));
				if (ttask.getScheduleTaskInfo() != null) {
					if (!ttask.getScheduleTaskInfo().isScheduleTask()) {
						FDCMsgBox.showInfo("����ɾ��ѡ�е�����!");
						return;
					}
				}
			}
		}
		super.actionRemove_actionPerformed(e);
	}
	
	/**
	 * @description �Ƿ�����
	 * @author �ź���
	 * @createDate 2011-9-5
	 * @version EAS7.0
	 * @see
	 */

	protected boolean isMainSchedule() {
		return false;
	}

	/**
	 * @description ����֮ǰ,����ר��
	 * @author �ź���
	 * @createDate 2011-9-5
	 * @version EAS7.0
	 * @see
	 * ����ʹ��editData�еģ���֪��ǰ�������ֵ�ΪɶҪ�������á�
	 */

	public FDCScheduleInfo getSaveSchedule() {
		FDCScheduleInfo info = super.getSaveSchedule();
//		if (prmtPrjSpecial.getValue() != null) {
//			info.setProjectSpecial((ProjectSpecialInfo) prmtPrjSpecial.getValue());
//		} else {
//			FDCMsgBox.showWarning(this, "��Ŀר���Ϊ�գ�");
//			SysUtil.abort();
//		}
		
		if(editData.getProjectSpecial()==null){
			FDCMsgBox.showWarning(this, "��Ŀר���Ϊ�գ�");
			SysUtil.abort();
		}
		return info;
	}
	
	/**
	 * @description
	 * @author �ź���
	 * @createDate 2011-9-6
	 * @version EAS7.0
	 * @see
	 */

	public boolean destroyWindow() {
		if (prmtPrjSpecial.getValue() != null && editData != null) {
			this.editData.setProjectSpecial((ProjectSpecialInfo) prmtPrjSpecial.getValue());
		}
		return super.destroyWindow();
	}
	/**
	 * ר��ƻ�����ʱ,ר��f7�����ı�ʱ����ȡ������Ϣ
	 * @description
	 * @author �ź���
	 * @createDate 2011-9-6
	 * @version EAS7.0
	 * @see
	 */
	protected void prmtPrjSpecial_dataChanged(DataChangeEvent e) throws Exception {
		CurProjectInfo project = (CurProjectInfo) prmtCurproject.getValue();
		ProjectSpecialInfo projectSpecial = (ProjectSpecialInfo) e.getNewValue();
		if (projectSpecial == null) {
			setDataObject(null);
			editData = null;
			getScheduleGanttProject().close();
			setUIEnabled(false);
		} else {
			super.prmtCurproject_dataChanged(new DataChangeEvent(this.prmtCurproject,project,editData==null||editData.getProject()==null?null:editData.getProject()));
			setUIEnabled(true);
		}
	}
	/**
	 * @description
	 * @author �ź���
	 * @createDate 2011-9-6
	 * @version EAS7.0
	 * @see
	 */
	protected void prmtCurproject_dataChanged(DataChangeEvent e) throws Exception {
		CurProjectInfo project = null;
		//������Ŀһ��ʱ����Ҫ�����ѯ�����������Ŀ��һ��ʱ�����ר������
		if(e.getNewValue() != null && e.getOldValue() != null){
			project = (CurProjectInfo) prmtCurproject.getValue();
			if(project.getId().equals(((CurProjectInfo)e.getOldValue()).getId())){
				return ;
			}else{
				editData = null;
				prmtPrjSpecial.setData(null);
			}
		}
		
		ProjectSpecialInfo projectSpecial = null;
		if(editData !=null 
				&& project != null
				&& editData.getProjectSpecial() != null
				&& editData.getProject()!=null
				&& editData.getProject().getId().equals(project.getId())){
			projectSpecial = editData.getProjectSpecial();
		    prmtPrjSpecial.setDataNoNotify(editData.getProjectSpecial());
		}
			
		if (project == null && projectSpecial != null && projectSpecial.getId() == null) {
			editData = null;
			getScheduleGanttProject().close();
			setUIEnabled(false);
		} else if(prmtPrjSpecial.getValue()!=null) {
			super.prmtCurproject_dataChanged(e);
			setUIEnabled(true);
		}

	}
	
	protected String getUIName() {
		return SpecialScheduleEditUI.class.getName();
	}
	
	/**
	 * ���ý��湦���Ƿ���ã���������Ŀ����Ŀר��Ϊ��ʱ�����������ȣ�
	 * ִ���е�����ť�ǲ�����ʾ��
	 * @param isEnable
	 */
	protected void setUIEnabled(boolean isEnable) {
		actionEdit.setEnabled(isEnable);
		actionAdjust.setEnabled(isEnable);
		actionRestore.setEnabled(isEnable);
		actionAttachment.setEnabled(isEnable);
		actionImportPlanTemplate.setEnabled(isEnable);
		actionImportProject.setEnabled(isEnable);
		actionExportPlanTemplate.setEnabled(isEnable);
		actionExportProject.setEnabled(isEnable);
		btnHisVerion.setEnabled(isEnable);
		actionCompareVer.setEnabled(isEnable);
		actionSave.setEnabled(isEnable);
		actionSubmit.setEnabled(isEnable);
	}
	protected String getPropertityUIName() {
		String propertyUIName = FDCSpecialScheduleTaskPropertiesUI.class.getName();
		return propertyUIName;
	}
}