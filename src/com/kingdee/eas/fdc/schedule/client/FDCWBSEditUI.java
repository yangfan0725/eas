/**
output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.WBSCodeRuleFactory;
import com.kingdee.eas.fi.gl.common.toolkit.sqlbuilder.SqlBuilder;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class FDCWBSEditUI extends AbstractFDCWBSEditUI {
	private static final Logger logger = CoreUIObject.getLogger(FDCWBSEditUI.class);

	public static final String COL_PREFIX_NAME = "prefixName";
	public static final String COL_PREFIX_NUMBER = "prefixNum";
	public static final String COL_PREFIX_LINKDAY = "linkDay";
	public static final String COL_PREFIX_LINKTYPE = "linkType";
	public static final String COL_PREFIX_ID = "id";
	public static final String COL_PREFIX_WBSID = "prefixID";

	/**
	 * output class constructor
	 */
	public FDCWBSEditUI() throws Exception {
		super();
	}

	public void storeFields() {
		// super.storeFields();
		editData.setName(txtName.getSelectedItemData().toString());
		editData.setAdminPerson((PersonInfo) prmtAdminPerson.getValue());
		if(txtEstimateDays.getIntegerValue()==null){
			editData.setEstimateDays(1);
		}else{
			editData.setEstimateDays(txtEstimateDays.getIntegerValue().intValue());
		}
		editData.setDescription(txtDescription.getText());
		editData.setTaskType((TaskTypeInfo) prmtTaskType.getValue());
		FullOrgUnitInfo fullOrg = null;
		if (prmtAdminDept.getValue() != null && prmtAdminDept.getValue() instanceof CostCenterOrgUnitInfo) {
			fullOrg = ((CostCenterOrgUnitInfo) prmtAdminDept.getValue()).castToFullOrgUnitInfo();
		} else if (prmtAdminDept.getValue() != null && prmtAdminDept.getValue() instanceof FullOrgUnitInfo) {
			fullOrg = (FullOrgUnitInfo) prmtAdminDept.getValue();
		}
		editData.setAdminDept(fullOrg);
		FullOrgUnitInfo fullOrg1 = null;
		if (prmtRespDept.getValue() != null && prmtRespDept.getValue() instanceof CostCenterOrgUnitInfo) {
			fullOrg1 = ((CostCenterOrgUnitInfo) prmtRespDept.getValue()).castToFullOrgUnitInfo();
		} else if (prmtRespDept.getValue() != null && prmtRespDept.getValue() instanceof FullOrgUnitInfo) {
			fullOrg1 = (FullOrgUnitInfo) prmtRespDept.getValue();
		}
		editData.setRespDept(fullOrg1);
	}

	protected FDCTreeBaseDataInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		FDCWBSInfo wbsInfo = new FDCWBSInfo();
		wbsInfo.setIsUnVisible(false);
		wbsInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		int level = 1;
		try {
			wbsInfo.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			this.handleException(e);
		}
		String projectID = null;
		String parentID = null;
		if (getUIContext().get("projectID") != null) {
			projectID = getUIContext().get("projectID").toString();
		}
		if (getUIContext().get("ParentNode") != null && getUIContext().get("ParentNode") instanceof FDCWBSInfo) {
			parentID = ((FDCWBSInfo) getUIContext().get("ParentNode")).getId().toString();
		}
		if (parentID != null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("curProject.id"));
			sic.add(new SelectorItemInfo("curProject.number"));
			sic.add(new SelectorItemInfo("curProject.name"));
			sic.add(new SelectorItemInfo("taskType.id"));
			sic.add(new SelectorItemInfo("taskType.number"));
			sic.add(new SelectorItemInfo("taskType.name"));
			sic.add(new SelectorItemInfo("taskType.longNumber"));
			sic.add(new SelectorItemInfo("taskType.level"));
			sic.add(new SelectorItemInfo("adminDept.id"));
			sic.add(new SelectorItemInfo("adminDept.number"));
			sic.add(new SelectorItemInfo("adminDept.name"));
			sic.add(new SelectorItemInfo("adminDept.longNumber"));
			// sic.add(new SelectorItemInfo("adminDept.level"));
			sic.add(new SelectorItemInfo("adminPerson.id"));
			sic.add(new SelectorItemInfo("adminPerson.number"));
			sic.add(new SelectorItemInfo("adminPerson.name"));
			sic.add(new SelectorItemInfo("respDept.id"));
			sic.add(new SelectorItemInfo("respDept.number"));
			sic.add(new SelectorItemInfo("respDept.name"));
			sic.add(new SelectorItemInfo("respDept.longNumber"));
			sic.add(new SelectorItemInfo("*"));
			FDCWBSInfo parentInfo = null;
			try {
				parentInfo = FDCWBSFactory.getRemoteInstance().getFDCWBSInfo(new ObjectUuidPK(BOSUuid.read(parentID)), sic);
				wbsInfo.setParent(parentInfo);

				// this.txtEstimateDays.setRemoveingZeroInDispaly(true);
				getUIContext().put("ParentNode", parentInfo);
				level = parentInfo.getLevel() + 1;
			} catch (Exception e) {
				this.handleException(e);
			}
		}
		try {
			wbsInfo.setCurProject(CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(BOSUuid.read(projectID))));
			wbsInfo.setNumber(WBSCodeRuleFactory.getRemoteInstance().getNewNumber(level, parentID, projectID));
			wbsInfo.setIsFromTemplate(true);
			wbsInfo.setEstimateDays(1);
			this.txtEstimateDays.setValue(new Integer("1"));
		} catch (Exception e) {
			this.handleException(e);
		}
		wbsInfo.setLevel(level);
		return wbsInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCWBSFactory.getRemoteInstance();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		actionPre.setVisible(false);
		actionNext.setVisible(false);
		actionFirst.setVisible(false);
		actionLast.setVisible(false);
		actionCopy.setVisible(false);
		actionSave.setVisible(false);
		actionRemove.setVisible(false);
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
				if(prmtRespDept.getValue()!=null&&prmtRespDept.getValue() instanceof FullOrgUnitInfo){
					if(((FullOrgUnitInfo)prmtRespDept.getValue()).getCU()==null){
						FDCMsgBox.showWarning("����ԪΪ�գ�����");
						SysUtil.abort();
					}
					String cuID = ((FullOrgUnitInfo)prmtRespDept.getValue()).getCU().getId().toString();
					FDCClientUtils.setPersonF7(prmtAdminPerson, this,cuID);
				}
			}
		}
	}
	public void onShow() throws Exception {
		super.onShow();
		setTaskTypeEnabled();
	}
	public void onLoad() throws Exception {
		if (!SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()) {
			FDCMsgBox.showInfo("��ǰ��֯���ǳɱ����ģ����ܽ��룡");
			SysUtil.abort();
		}
		super.onLoad();
		setTaskTypeEnabled();
//		prmtAdminPerson.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");
//		ScheduleClientHelper.setPersonF7(prmtAdminPerson, this, SysContext.getSysContext().getCurrentCtrlUnit().getId() != null ? SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()
//				: null);
		// ���β���
		String cuId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		//�ж��Ƿ����ü��ſ���   modify by warship at 2010/07/08
		if(ScheduleClientHelper.chooseAllOrg()){
			ScheduleClientHelper.setRespDept(prmtAdminDept, this, null);
			ScheduleClientHelper.setRespDept(prmtRespDept, this, null);
		}else{
			ScheduleClientHelper.setRespDept(prmtRespDept, this, cuId);
			
			// �ƻ����� :���е�F7������֯�Ƚϻ��ң��еĹ���������֯���еĹ��˳ɱ����ģ���ҪͳһΪ��ǰ�û�������֯�Ĳ�����֯�µ����гɱ����� by
			// cassiel_peng 2010-04-27
			ScheduleClientHelper.setRespDept(prmtAdminDept, this, cuId);
		}

		// EntityViewInfo view = prmtAdminDept.getEntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// if (prmtParentAdminDept.getValue() != null) {
		// filter.getFilterItems().add(new FilterItemInfo("longNumber",
		// ((FullOrgUnitInfo) prmtParentAdminDept.getValue()).getLongNumber() +
		// "%", CompareType.LIKE));
		// if (view == null)
		// view = new EntityViewInfo();
		// view.setFilter(filter);
		// // prmtAdminDept.setSelectAllOnFocus(selectAllOnFocus)
		// prmtAdminDept.setEntityViewInfo(view);
		//
		// } else if (editData.getParent() != null &&
		// !OprtState.VIEW.equals(getOprtState())) {
		// // FDCMsgBox.showWarning("���������ϼ�WBS�����񼶱�ͼƻ����ţ�");
		// //�����Լ:ȡ�����мƻ����ŵ��ϼ�������¼�����ļƻ����ŵĿ����߼� by cassiel_peng 2010-04-21
		// FDCMsgBox.showWarning("���������ϼ�wbs�����񼶱�");
		// SysUtil.abort();
		// }

		// view = prmtTaskType.getEntityViewInfo();
		// if (view == null)
		// view = new EntityViewInfo();
		// filter = view.getFilter();
		// if (filter == null)
		// filter = new FilterInfo();
		// if (prmtParentTaskType.getValue() != null) {
		// TaskTypeInfo parTaskType = (TaskTypeInfo)
		// prmtParentTaskType.getValue();
		// filter.getFilterItems().add(new FilterItemInfo("longNumber",
		// parTaskType.getLongNumber().replace('!', '.') + "%",
		// CompareType.LIKE));
		// filter.getFilterItems().add(new FilterItemInfo("level",
		// Integer.valueOf(parTaskType.getLevel() + 1)));
		// filter.getFilterItems().add(new FilterItemInfo("level",
		// Integer.valueOf(parTaskType.getLevel())));
		// filter.setMaskString("#0 and (#1 or #2)"); //
		// filter.getFilterItems().add(new FilterItemInfo("isEnabled",
		// Boolean.TRUE));
		// view.setFilter(filter);
		// prmtTaskType.setEntityViewInfo(view);
		// } else if (editData.getParent() != null &&
		// !OprtState.VIEW.equals(getOprtState())) {
		// // FDCMsgBox.showWarning("���������ϼ�WBS�����񼶱�ͼƻ����ţ�");
		// //�����Լ:ȡ�����мƻ����ŵ��ϼ�������¼�����ļƻ����ŵĿ����߼� by cassiel_peng 2010-04-21
		// FDCMsgBox.showWarning("���������ϼ�wbs�����񼶱�");
		// SysUtil.abort();
		// } else {
		// filter.getFilterItems().add(new FilterItemInfo("id",
		// TaskTypeInfo.TASKTYPE_MAINTASK, CompareType.EQUALS));
		// view.setFilter(filter);
		// prmtTaskType.setEntityViewInfo(view);
		// }
		actionCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		btnEdit.setEnabled(true);
		contCreateTime.setVisible(false);
		contCreator.setVisible(false);
		contLastUpdateTime.setVisible(false);
		contLastUpdateUser.setVisible(false);
		txtDescription.setMaxLength(500);
		txtName.setMaxLength(80);
		txtEstimateDays.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		txtEstimateDays.setMaximumValue(new BigDecimal("10000"));
		txtEstimateDays.setMinimumValue(new BigDecimal("1"));
		txtLevel.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		txtParentLevel.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		if (OprtState.VIEW.equals(getOprtState())) {
			btnAddNew.setEnabled(true);
		}
//		prmtAdminDept.setEditable(false);
//		prmtAdminPerson.setEditable(false);
//		prmtTaskType.setEditable(false);
	}
	protected void prmtRespDept_dataChanged(DataChangeEvent e) throws Exception {
		Object oldValue = e.getOldValue();
		Object newValue = e.getNewValue();
		if(newValue!=null&&newValue!=oldValue&&!newValue.equals(oldValue)&&!isFirstOnload()){
			prmtAdminPerson.setValue(null);
		}
	}
	protected void prmtTaskType_dataChanged(DataChangeEvent e) throws Exception {
		setTaskTypeEnabled();
	}

	private void setTaskTypeEnabled() {
		if (prmtTaskType.getValue() != null && prmtTaskType.getValue() instanceof TaskTypeInfo && ((TaskTypeInfo) prmtTaskType.getValue()).getId().toString().equals(TaskTypeInfo.TASKTYPE_MAINTASK)) {
			this.prmtAdminDept.setEnabled(true);
		} else {
			this.prmtAdminDept.setValue(null);
			this.prmtAdminDept.setEnabled(false);
			this.prmtAdminDept.setEditable(false);
		}
	}

	protected boolean isSystemDefaultData() {
		return false;
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyCtrlItem();
		//by cassiel_peng 2010-05-14
		BOSUuid id = editData.getId();
			//���ڵ㵱�Ҳ���¼����������ʱ���ܱ���
		 if(this.editData.getParent()==null||this.editData.getParent().getId()==null){
				if( prmtTaskType.getValue() != null&& prmtTaskType.getValue() instanceof TaskTypeInfo && ((TaskTypeInfo) prmtTaskType.getValue()).getId().toString().equals(TaskTypeInfo.TASKTYPE_SPECIALTASK)){
					MsgBox.showWarning("��WBSΪ���ϼ����񣬶�Ӧ�����񼶱����Ϊ��������");
					SysUtil.abort();
				}
			}else if(this.editData.getParent()!=null&&this.editData.getParent().getId()!=null){
				//�ϼ�δ¼��ƻ����𣬱������ܱ���
				//�Ǹ��ڵ㣬����ϼ��������������¼���������ר����ԣ��ϼ���ר�����ֻ��¼��ר��
				if(prmtParentTaskType.getValue() == null ){
					MsgBox.showWarning("��WBS���ϼ�WBSδ�������񼶱�Ľ��������棡");
					SysUtil.abort();
				}else{
					if(( prmtParentTaskType.getValue() instanceof TaskTypeInfo && ((TaskTypeInfo) prmtParentTaskType.getValue()).getId().toString().equals(TaskTypeInfo.TASKTYPE_SPECIALTASK))){
						if( prmtTaskType!=null&&prmtTaskType.getValue() instanceof TaskTypeInfo && ((TaskTypeInfo) prmtTaskType.getValue()).getId().toString().equals(TaskTypeInfo.TASKTYPE_MAINTASK)){
							MsgBox.showWarning("��WBS��Ӧ�����񼶱�Ϊ���������ϼ�����Ϊר��Ļ�δ�������񼶱�Ľ��������棡");
							SysUtil.abort();
						}
					}
				}
		}
		if(id != null){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("wbs.id",id.toString()));
			if(FDCScheduleTaskFactory.getRemoteInstance().exists(filter)){
				int result = FDCMsgBox.showConfirm2("����ͬ���޸Ľ��������е������Ϣ��");
				if(result == FDCMsgBox.CANCEL){
					return;
				}
			}
		}
		super.actionSubmit_actionPerformed(e);
		btnEdit.setEnabled(true);
	}

	private void verifyCtrlItem() {
		if (txtName.getSelectedItemData() == null) {
			FDCMsgBox.showWarning("�������Ʋ���Ϊ�գ�");
			SysUtil.abort();
		}
//		if (prmtAdminPerson.getValue() == null) {
//			FDCMsgBox.showWarning("���������˲���Ϊ�գ�");
//			SysUtil.abort();
//		}
//		if (prmtTaskType.getValue() == null) {
//			FDCMsgBox.showWarning("�������񼶱���Ϊ�գ�");
//			SysUtil.abort();
//		}
//		if (prmtAdminDept.getValue() == null) {
//			FDCMsgBox.showWarning("�����ƻ����Ų���Ϊ�գ�");
//			SysUtil.abort();
//		}
//		if (prmtRespDept.getValue() == null) {
//			FDCMsgBox.showWarning("�������β��Ų���Ϊ�գ�");
//			SysUtil.abort();
//		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("parent.taskType.name");
		sic.add("parent.taskType.id");
		sic.add("parent.taskType.number");
		sic.add("parent.taskType.longNumber");
		sic.add("parent.taskType.level");
		sic.add("parent.adminDept.name");
		sic.add("parent.adminDept.id");
		sic.add("parent.adminDept.number");
		sic.add("parent.adminDept.longNumber");
		sic.add("parent.adminPerson.id");
		sic.add("parent.adminPerson.number");
		sic.add("parent.adminPerson.name");
		sic.add("parent.respDept.id");
		sic.add("parent.respDept.number");
		sic.add("parent.respDept.name");
		sic.add("parent.estimateDays");
		sic.add("longNumber");
		sic.add("isFromTemplate");
		sic.add("isEnabled");
		return sic;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (editData.isIsEnabled()) {
			FDCMsgBox.showError("��ĿWBS�����ã������޸ģ�");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
		setTaskTypeEnabled();
		// if(chkIsLocked.isSelected()){
		// txtEstimateDays.setEnabled(false);
		// }else{
		// txtEstimateDays.setEnabled(true);
		// }
	}

	public boolean isModify() {
		if (editData != null) {
			if (!FDCSCHClientHelper.isEqual(editData.getName(), txtName.getSelectedItemData().toString()))
				return true;
			if (prmtAdminPerson.getValue() != null && !prmtAdminPerson.getValue().equals(editData.getAdminPerson()))
				return true;
			if (!FDCSCHClientHelper.isEqual(editData.getDescription(), txtDescription.getText()))
				return true;
			if (txtEstimateDays.getBigDecimalValue() != null) {
				if (editData.getEstimateDays() != Integer.parseInt(txtEstimateDays.getBigDecimalValue().toString()))
					return true;
			} else {
				if (editData.getEstimateDays() != 0)
					return true;
			}
			// if(editData.isIsLocked() != chkIsLocked.isSelected())
			// return true;
			if (prmtAdminDept.getValue() != null && !(prmtAdminDept.getValue().equals(editData.getAdminDept())))
				return true;
			if (prmtTaskType.getValue() != null && !(prmtTaskType.getValue().equals(editData.getTaskType())))
				return true;
			if (prmtRespDept.getValue() != null && !(prmtRespDept.getValue().equals(editData.getRespDept())))
				return true;
			return false;
		} else
			return false;
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		setTitle();
	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, "��ĿWBS");
	}

}