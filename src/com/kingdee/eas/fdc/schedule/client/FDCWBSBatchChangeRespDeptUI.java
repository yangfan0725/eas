/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class FDCWBSBatchChangeRespDeptUI extends AbstractFDCWBSBatchChangeRespDeptUI {
	private static final Logger logger = CoreUIObject.getLogger(FDCWBSBatchChangeProUI.class);

	/**
	 * output class constructor
	 */
	public FDCWBSBatchChangeRespDeptUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		Object o = getUIContext().get("isMainOrSpecial");
		if(o != null&&!((Boolean)o).booleanValue()){
			//���׼���޸ĵ���������ר���ô�ƻ����Ʋ��Ż��Բ��ܱ༭  by cassiel_peng 2010-05-07
			this.prmtAdminDept.setEnabled(false);
		}
		String cuId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
//		prmtAdminDept.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7FullOrgUnitQuery");
		// ���β���
		//�ж��Ƿ����ü��ſ���   modify by warship at 2010/07/08
		if(ScheduleClientHelper.chooseAllOrg()){
			ScheduleClientHelper.setRespDept(prmtAdminDept, this, null);
			ScheduleClientHelper.setRespDept(prmtRespDept, this, null);
		}else{
			ScheduleClientHelper.setRespDept(prmtAdminDept, this, cuId);
			ScheduleClientHelper.setRespDept(prmtRespDept, this, cuId);
		}
		prmtAdminPerson.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");
		ScheduleClientHelper.setPersonF7(prmtAdminPerson, this, SysContext.getSysContext().getCurrentCtrlUnit().getId() != null ? SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()
				: null);
	}
	protected void prmtRespDept_dataChanged(DataChangeEvent e) throws Exception {
		Object oldValue = e.getOldValue();
		Object newValue = e.getNewValue();
		if(newValue!=null&&newValue!=oldValue&&!newValue.equals(oldValue)){
			prmtAdminPerson.setValue(null);
		}
	}
	protected void prmtAdminPerson_willShow(SelectorEvent e) throws Exception {
		//�������Ƿ񰴵�ǰ�û�������֯���й��� by cassiel_peng 2010-06-07
		CtrlUnitInfo currentCtrlUnit = SysContext.getSysContext().getCurrentCtrlUnit();
		if(!FDCSCHClientHelper.filterRespPerson()){//����ͬ�������˱���һ��
			if(FDCSCHClientHelper.canSelectOtherOrgPerson()){
				FDCClientUtils.setPersonF7(prmtAdminPerson, this,null );
			}else{
				if(currentCtrlUnit==null){
					FDCMsgBox.showWarning("����ԪΪ�գ�����");
					SysUtil.abort();
				}
				FDCClientUtils.setPersonF7(prmtAdminPerson, this,currentCtrlUnit.getId().toString());
			}
		}else{
			//���û��ѡ�����β���
			if(prmtRespDept.getValue()==null){
				if(currentCtrlUnit==null){
					FDCMsgBox.showWarning("����ԪΪ�գ�����");
					SysUtil.abort();
				}
				FDCClientUtils.setPersonF7(prmtAdminPerson, this,currentCtrlUnit.getId().toString());
			}else{
				if(prmtRespDept.getValue()!=null&&prmtRespDept.getValue() instanceof CostCenterOrgUnitInfo){
					if(((CostCenterOrgUnitInfo)prmtRespDept.getValue()).getCU()==null){
						FDCMsgBox.showWarning("����ԪΪ�գ�����");
						SysUtil.abort();
					}
					String cuID = ((CostCenterOrgUnitInfo)prmtRespDept.getValue()).getCU().getId().toString();
					FDCClientUtils.setPersonF7(prmtAdminPerson, this,cuID);
				}
			}
		}
	}
	protected void initWorkButton() {
		super.initWorkButton();
		btnOK.setEnabled(true);
		btnCancel.setEnabled(true);
	}

	/***
	 * �����޸����β���
	 * 
	 * isWbsOrSchedule=True:Wbs�����޸����β��� False:�ƻ����ƣ������ר������޸����β���  
	 * 
	 * add by warship at 2010/08/05
	 */
	public void actionOK_actionPerformed(ActionEvent e) throws Exception {
		Object o = getUIContext().get("isWbsOrSchedule");//True:Wbs�����޸����β��� False:�ƻ����ƣ������ר������޸����β���  add by warship at 2010/08/05
		Set wbsIDs = new HashSet();
		if(o != null&&((Boolean)o).booleanValue()){//Wbs�����޸����β���
			if (getUIContext().get("wbsIDs") != null) {
				wbsIDs = (Set) getUIContext().get("wbsIDs");
			} else {
				FDCMsgBox.showError("��ѡ��ĿWBS���ʺϴ˲���!");
				SysUtil.abort();
			}
			if (wbsIDs.size() < 1) {
				FDCMsgBox.showError("��ѡ��ĿWBS���ʺϴ˲���!");
				SysUtil.abort();
			}
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("wbs.id",wbsIDs,CompareType.INCLUDE));
			if(FDCScheduleTaskFactory.getRemoteInstance().exists(filter)){
				int result = FDCMsgBox.showConfirm2("����ͬ���޸Ľ��������е������Ϣ��");
				if(result == FDCMsgBox.CANCEL){
					return;
				}
			}
		}else{//�ƻ����ƣ������ר������޸����β���
			if (getUIContext().get("taskSets") != null) {
				wbsIDs = (Set) getUIContext().get("taskSets");
			}
		}
		String adminDeptID;
		if (prmtAdminDept.getValue() != null) {
			FullOrgUnitInfo fullOrg = null;
			if(prmtAdminDept.getValue()!=null&&prmtAdminDept.getValue() instanceof CostCenterOrgUnitInfo){
				fullOrg=((CostCenterOrgUnitInfo) prmtAdminDept.getValue()).castToFullOrgUnitInfo();
			}else if(prmtAdminDept.getValue()!=null&&prmtAdminDept.getValue() instanceof FullOrgUnitInfo){
				fullOrg=(FullOrgUnitInfo)prmtAdminDept.getValue();
			}
			adminDeptID = fullOrg.getId().toString();
			if(o != null&&((Boolean)o).booleanValue()){
				FDCWBSFactory.getRemoteInstance().batChangeAdminDept(wbsIDs, adminDeptID);
			}else{//�ƻ����ƣ������ר������޸ļƻ����Ʋ���
				if(wbsIDs.size()>0){
					for(Iterator it =wbsIDs.iterator();it.hasNext();){
						FDCScheduleTaskInfo task =(FDCScheduleTaskInfo)it.next();
						task.setPlanDept(fullOrg);
					}
				}
				
			}
		}
		String respDeptId;
		if (prmtRespDept.getValue() != null) {
			FullOrgUnitInfo fullOrg = null;
			if(prmtRespDept.getValue()!=null&&prmtRespDept.getValue() instanceof CostCenterOrgUnitInfo){
				fullOrg=((CostCenterOrgUnitInfo) prmtRespDept.getValue()).castToFullOrgUnitInfo();
			}else if(prmtRespDept.getValue()!=null&&prmtRespDept.getValue() instanceof FullOrgUnitInfo){
				fullOrg=(FullOrgUnitInfo)prmtRespDept.getValue();
			}
			respDeptId = fullOrg.getId().toString();
			if(o != null&&((Boolean)o).booleanValue()){
				FDCWBSFactory.getRemoteInstance().batChangeRespDept(wbsIDs, respDeptId);
			}else{//�ƻ����ƣ������ר������޸����β���
				if(wbsIDs.size()>0){
					for(Iterator it =wbsIDs.iterator();it.hasNext();){
						FDCScheduleTaskInfo task =(FDCScheduleTaskInfo)it.next();
						task.setAdminDept(fullOrg);
					}
				}
			}
		}
		String adminPersonID;
		if (prmtAdminPerson.getValue() != null && prmtAdminPerson.getValue() instanceof PersonInfo) {
			PersonInfo adminPerson =(PersonInfo) prmtAdminPerson.getValue();
			adminPersonID = adminPerson.getId().toString();
			if(o != null&&((Boolean)o).booleanValue()){
				FDCWBSFactory.getRemoteInstance().batChangeAdminPerson(wbsIDs, adminPersonID);
			}else{//�ƻ����ƣ������ר������޸�������
				if(wbsIDs.size()>0){
					for(Iterator it =wbsIDs.iterator();it.hasNext();){
						FDCScheduleTaskInfo task =(FDCScheduleTaskInfo)it.next();
						task.setAdminPerson(adminPerson);
					}
				}
			}
		}
		this.disposeUIWindow();
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		this.disposeUIWindow();
	}
}