package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDNumberTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.util.FDCImageAttUtil;
import com.kingdee.eas.fdc.schedule.GuideTypeEnum;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.RESchTemplateFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorInfo;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideEntryInfo;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideFactory;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;

/**
 * �ƻ�ģ������༭����
 * 
 * @author ��С��
 * @version EAS7.0
 * @createDate 2011-08-04
 * @see
 */
public class RESchTemplateTaskEditUI extends AbstractRESchTemplateTaskEditUI {
	/* ȱʡ�汾�� */
	private static final long serialVersionUID = 1L;
	
	private List alreadyExistNumber = new ArrayList();

	/* ��־ */
	private static final Logger logger = CoreUIObject.getLogger(RESchTemplateTaskEditUI.class);

	/* ������ */
	private static final String COL_NAME = "name";

	/* ������ */
	private static final String COL_NUMBER = "number";

	/* ��ӹ�ϵ�� */
	private static final String COL_DIFFERENT_RELATION = "differentRelation";

	/* ���ʱ���� */
	private static final String COL_DIFFERENT_DAY = "differenceDay";

	/* ǰ������ */
	private static final String COL_PTASK = "pTask";

	/* ������ */
	private static final String COL_ID = "id";

	/* �����а�Ť */
	private static final String BTN_ADDROW = "btnAddRow";

	/* ɾ���а�Ť */
	private static final String BTN_REMOVEROW = "btnRemoveRow";

	/* �����а�Ťͼ�� */
	private static final String IMG_BTN_ADDLINE = "imgTbtn_addline";

	/* ɾ���а�Ťͼ�� */
	private static final String IMG_BTN_REMOVELINE = "imgTbtn_deleteline";

	/* �ĵ�Ŀ¼�� */
	private static final String COL_DOCPATH = "docPath";

	/* �ĵ������� */
	private static final String COL_DOCNAME = "docName";


	/* ��׼����-->���½Ƿ�¼ͼƬ */
	private FDCImageAttUtil imageAttUtil = null;

	/* ��׼����-->�������� */
	private String attID = null;

	public RESchTemplateTaskEditUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
        if (editData.getPredecessors() != null)
			this.editData.getPredecessors().clear();
        if (getUIContext().get("parent") != null) {
			RESchTemplateTaskInfo parent = (RESchTemplateTaskInfo) getUIContext().get("parent");
			editData.setParent(parent);
			editData.setLongNumber(editData.getParent().getLongNumber() + "!" + editData.getNumber());			
			editData.setLevel(editData.getParent().getLevel() + 1);
		} else if (editData.getParent() != null) {
			editData.setLongNumber(editData.getParent().getLongNumber() + "!" + editData.getNumber());
			editData.setLevel(editData.getParent().getLevel() + 1);
		} else {
			editData.setLongNumber(editData.getNumber());
			editData.setLevel(1);
		}

		/* ��ҵ�����͵�ֵ */
		while (null != this.editData.getBusinessType().get(0)) {
			this.editData.getBusinessType().remove(this.editData.getBusinessType().get(0));
		}

		Object[] businessTypes = (Object[]) this.prmpBusinessType.getValue();
		StringBuffer businessTypeDesc = new StringBuffer();
		// Modify by duhongming
		if (businessTypes != null) {
			for (int k = 0; k < businessTypes.length; k++) {
				RESchTemplateTaskBizTypeInfo schTemplateTaskBizTypeInfo = new RESchTemplateTaskBizTypeInfo();
				if (businessTypes[k] instanceof ScheduleBizTypeInfo) {
					ScheduleBizTypeInfo scheduleBizTypeInfo = (ScheduleBizTypeInfo) businessTypes[k];
					schTemplateTaskBizTypeInfo.setBizType(scheduleBizTypeInfo);
					schTemplateTaskBizTypeInfo.setParent(this.editData);
					schTemplateTaskBizTypeInfo.setId(BOSUuid.create((new RESchTemplateTaskBizTypeInfo()).getBOSType()));
					this.editData.getBusinessType().add(schTemplateTaskBizTypeInfo);
					businessTypeDesc.append(scheduleBizTypeInfo.getName()).append(",");
				}
			}

		}

		/* ����ǰ������ */
		int count = this.tblTaskMain.getRowCount();
		StringBuffer predecessorDesc = new StringBuffer();
		this.editData.getPredecessors();
		ObjectUuidPK[] idss = new ObjectUuidPK[this.editData.getPredecessors().size()];
		for (int m = 0; m < this.editData.getPredecessors().size(); m++) {
			RESchTemplateTaskPredecessorInfo schTemplateTaskPredecessorInfo = this.editData.getPredecessors().get(m);
			idss[m] = new ObjectUuidPK(schTemplateTaskPredecessorInfo.getId());
		}

		for (int k = 0; k < count; k++) {
			RESchTemplateTaskPredecessorInfo schTemplateTaskPredecessorInfo = new RESchTemplateTaskPredecessorInfo();
			IRow row = this.tblTaskMain.getRow(k);
			row.getCell(COL_NAME).getValue();
			row.getCell(COL_NUMBER).getValue();

			// ���õ�ǰ����
			schTemplateTaskPredecessorInfo.setTask(this.editData);
			// ���ô��ϵ
			TaskLinkTypeEnum taskLinkTypeEnum = (TaskLinkTypeEnum) row.getCell(COL_DIFFERENT_RELATION).getValue();
			schTemplateTaskPredecessorInfo.setPredecessorType(taskLinkTypeEnum);
			// ���ôʱ��
			schTemplateTaskPredecessorInfo.setDifferenceDay(BigDecimal.valueOf(
					Double.parseDouble((row.getCell(COL_DIFFERENT_DAY).getValue().toString()))).intValue());
			// ����ǰ������
			if (row.getCell(COL_PTASK).getValue() instanceof RESchTemplateTaskInfo) {
				RESchTemplateTaskInfo templateTaskInfo = (RESchTemplateTaskInfo) (row.getCell(COL_PTASK).getValue());
				schTemplateTaskPredecessorInfo.setPredecessorTask(templateTaskInfo);
				predecessorDesc.append(templateTaskInfo.getName()).append(",");
			}
			schTemplateTaskPredecessorInfo.setParent(this.editData);
			if (null != row.getCell(COL_ID).getValue() && !"".equals(row.getCell(COL_ID).getValue().toString())) {
				schTemplateTaskPredecessorInfo.setId(BOSUuid.read(row.getCell(COL_ID).getValue().toString()));
			}
			this.editData.getPredecessors().add(schTemplateTaskPredecessorInfo);
		}

		/* ����ǰ���������� */
		if (predecessorDesc.indexOf(",") != -1) {
			this.editData.setPredecessorDesc(predecessorDesc.substring(0, predecessorDesc.lastIndexOf(",")));
		} else {
			this.editData.setPredecessorDesc(predecessorDesc.toString());
		}

		/* ����ҵ���������� */
		if (businessTypeDesc.indexOf(",") != -1) {
			this.editData.setBusinessTypeDesc(businessTypeDesc.substring(0, businessTypeDesc.lastIndexOf(",")));
		} else {
			this.editData.setBusinessTypeDesc(businessTypeDesc.toString());
		}

		/* ����ģ����Ϣ */
		if (this.getUIContext().get("Owner") instanceof RESchTemplateListUI) {
			RESchTemplateListUI schTemplateListUI = (RESchTemplateListUI) this.getUIContext().get("Owner");
			if (!"".equals(schTemplateListUI.templateId)) {
				try {
					RESchTemplateInfo rESchTemplateInfo = RESchTemplateFactory.getRemoteInstance().getRESchTemplateInfo(
							new ObjectUuidPK(schTemplateListUI.templateId));
					this.editData.setTemplate(rESchTemplateInfo);
				} catch (EASBizException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				} catch (BOSException e) {
					logger.error(e.getMessage());
				}
			}
			if (null == schTemplateListUI.parentId || "".equals(schTemplateListUI.parentId)) {
				this.editData.setParent(null);
			}
		}
//		// ������(ְԱ)
//		prmtDutyPerson.setValue(editData.getAdminPerson());
//		// ���β���
//		prmtDutyDep.setValue(editData.getAdminDept());
		// ������

		initOldData(editData);

	}
	
	protected void loadData() throws Exception {
		if (getUIContext().get("taskInfo") != null) {
			RESchTemplateTaskInfo obj = (RESchTemplateTaskInfo) getUIContext().get("taskInfo");
			setDataObject(obj);
			loadFields();
		} else {
			super.loadData();
		}
	}

	/**
	 * @discription <�ж�ǰ�������Ƿ���ȷ>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/11>
	 *             <p>
	 * @param <��>
	 * @return <����ֵ����>
	 * 
	 *         modifier <��>
	 *         <p>
	 *         modifyDate <��>
	 *         <p>
	 *         modifyInfo <��>
	 *         <p>
	 * @see <��ص���>
	 */
	public boolean isPredecesserTask() {
		// ���������
		int count = this.tblTaskMain.getRowCount();

		/* ѭ���жϱ�¼���Ƿ�Ϊ�� */
		for (int k = 0; k < count; k++) {
			IRow row = this.tblTaskMain.getRow(k);
			Object nameObj = row.getCell(COL_NAME).getValue();
			Object relactionObj = row.getCell(COL_DIFFERENT_RELATION).getValue();
			Object dayObj = row.getCell(COL_DIFFERENT_DAY).getValue();
			if (null == nameObj || null == relactionObj || null == dayObj) {
				return false;
			}
			if ("".equals(nameObj.toString().trim()) || "".equals(relactionObj.toString().trim()) || "".equals(dayObj.toString().trim())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		checkNumber();
	}

	/**
	 * TODO ����Ӧ�����ύ����,�������ʵ�ʵ��õ��Ǳ��� modify by duhongming
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkNumber();
		if (!this.isPredecesserTask()) {
			FDCMsgBox.showInfo("ǰ�������б�  ���б�¼��Ŀδ¼�룬���������ע����ɫ������Ϊ��¼�У�");
			return;
		}

		if (!validateAchType()) {
			FDCMsgBox.showInfo("ҵ������Ϊ�׶��Գɹ�ʱ���ɹ�����¼��");
			return;
		}
		
		if (txtReferenceDay.getIntegerValue().intValue() < 1) {
			FDCMsgBox.showInfo("�ο����ڲ���С��1��");
			return;
		}
		
		if(prmtAchievementType.isRequired() &&  prmtAchievementType.getValue() == null){
			FDCMsgBox.showInfo("�ɹ������Ϊ�գ�");
			return;
		}

		if (this.getUIContext().get("Owner") instanceof RESchTemplateListUI) {
			RESchTemplateListUI schTemplateListUI = (RESchTemplateListUI) this.getUIContext().get("Owner");
			if (!"".equals(schTemplateListUI.templateId)) {
				RESchTemplateInfo schTemplateInfo = RESchTemplateFactory.getRemoteInstance().getRESchTemplateInfo(
						new ObjectUuidPK(schTemplateListUI.templateId));
				if (!schTemplateInfo.isIsLeaf()) {
					FDCMsgBox.showInfo("����ִ�б��棬ѡ���ģ�壬�Ѿ�������ϸ�ڵ㣡");
					return;
				}
			}
		}
		storeFields();
		this.editData.setAdminPerson((PersonInfo) this.prmtDutyPerson.getValue());
		// ���β���-- ѡ����ϸ������֯
		// TODO
		// ������ʷ���ݣ����ܻᵼ���жϡ�
		AdminOrgUnitInfo dutyDep = null;
		if (this.prmtDutyDep.getValue() instanceof AdminOrgUnitInfo)
			dutyDep = (AdminOrgUnitInfo) this.prmtDutyDep.getValue();
		if (dutyDep != null)
			this.editData.setAdminDept(dutyDep.castToFullOrgUnitInfo());
		else
			this.editData.setAdminDept((FullOrgUnitInfo) this.prmtDutyDep.getValue());
		RESchTemplateEditUI ui = (RESchTemplateEditUI) getUIContext().get("fromUI");
		ui.refreshTable(this.editData);
//		disposeUIWindow();
	}

	protected boolean isGlobalTaskName(String taskName){
		boolean result = false;
		try {
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql.appendSql(" select fid from T_SCH_GlobalTaskNode "); 
			sql.appendSql(" where FNAME_L2=? ");
			sql.addParam(taskName);
			IRowSet rs = sql.executeQuery();
			if (rs.next()) {
				if (rs.getString(1) == null)
				{				
					result = false;
				}else{
					result = true;
				}
			}else{
				result = false;
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @discription <���ҵ������Ϊ�׶��Գɹ�����ɹ�����¼>
	 * @author <Xiaolong Luo>
	 * @createDate <2011//>
	 *             <p>
	 *             on
	 * @param <��>
	 * @return <����ֵ����>
	 * 
	 *         modifier <��>
	 *         <p>
	 *         modifyDate <��>
	 *         <p>
	 *         modifyInfo <��>
	 *         <p>
	 * @see <��ص���>
	 */
	public boolean validateAchType() {
		if (prmpBusinessType.getValue() instanceof Object[]) {
			Object[] typeArray = (Object[]) prmpBusinessType.getValue();
			if (null == typeArray) {
				return true;
			}
			for (int k = 0; k < typeArray.length; k++) {
				if (null != typeArray[k] && "�׶��Գɹ�".equals(typeArray[k].toString())) {
					Object acType = this.prmtAchievementType.getValue();
					if (null == acType || "".equals(acType.toString())) {
						return false;
					}
				}
			}

		}
		return true;
	}

	/**
	 * @discription <��֤�����Ƿ���ȷ>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/13>
	 *             <p>
	 * @param <��>
	 * @return <����ֵ����>
	 * 
	 *         modifier <��>
	 *         <p>
	 *         modifyDate <��>
	 *         <p>
	 *         modifyInfo <��>
	 *         <p>
	 * @throws BOSException
	 * @throws SQLException
	 * @see <��ص���>
	 */
	public boolean validateNumber() throws BOSException, SQLException {
		String number = this.txtNumber.getText().trim();
		if (StringUtils.isEmpty(number)) {
			FDCMsgBox.showInfo("���벻��Ϊ�գ�");
			return false;
		}
		Pattern tDouble = Pattern.compile("([0-9]{1,20})");
		if (!tDouble.matcher(number).matches()) {
			FDCMsgBox.showInfo("����ֻ���������֣�");
			return false;
		}
		int currNumber = Integer.parseInt(number);
		if (alreadyExistNumber.contains(currNumber)) {
			FDCMsgBox.showInfo("�����Ѿ����ڣ�");
			return false;
		}
		return true;
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		this.tblTaskMain.removeRows();
	}

	public void checkModified() throws Exception {
		if (isModify()) {
			String message = null;
			if (!isModifySave()) {
				message = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Save_Exit");
			} else {
				message = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Save_Check");
			}

			int result = FDCMsgBox.showConfirm3(this, message);
			if (result == 0) {
				if (!isModifySave()) {
					ActionEvent event = new ActionEvent(btnSubmit, 1001, btnSubmit.getActionCommand());
					actionSubmit.actionPerformed(event);
					if (actionSubmit.isInvokeFailed())
						SysUtil.abort();
				} else {
					actionSave.setDaemonRun(false);
					ActionEvent event = new ActionEvent(btnSave, 1001, btnSave.getActionCommand());
					actionSave.actionPerformed(event);
					if (actionSave.isInvokeFailed())
						SysUtil.abort();
				}
			} else if (result == 2) {
				SysUtil.abort();
			} else if (result == 1) {
				/* �������ʾ��ͼ */
				this.KDPanelImg.removeAll();

				/* ���ǰ������ */
				this.tblTaskMain.removeRows();

				/* ���ҵ������ */
				this.prmpBusinessType.setText("");
				this.updateUI();
			}
		}
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		
		super.actionEdit_actionPerformed(e);
		if (prmpBusinessType.getValue() instanceof Object[]) {
			Object[] typeArray = (Object[]) prmpBusinessType.getValue();
			if (typeArray == null || typeArray.length==0) {
				prmtAchievementType.setEnabled(false);
				prmtAchievementType.setRequired(false);
				prmtAchievementType.setValue(null);
			} else {
				boolean isRequire = false;
				ScheduleBizTypeInfo bizType = null;
				for (int i = 0; i < typeArray.length; i++) {
					bizType = (ScheduleBizTypeInfo) typeArray[i];
					if (bizType.getId().toString().equals("Rz+dS7ECSfqM4kEJqGawYWLF6cA=")) {
						isRequire = true;
					}
				}
				prmtAchievementType.setEnabled(isRequire);
				prmtAchievementType.setRequired(isRequire);
			}
		}
		
		lockComponetExceptReferenceDay();
	}

	/**
	 * �Զ����롰���Źܿؽڵ�ģ�塱���������񣬳����ο����ڡ��ֶ��ⲻ���޸�
	 * PBG099752���������ơ�ҵ�����͡��ɹ�������������޸ġ�����2013-9-25
	 * ���˲ο����ڣ�����ȫ��ס���ܱ༭
	 * @author owen_wen 2013-5-31
	 */
	private void lockComponetExceptReferenceDay() {
		if (!StringUtils.isEmpty(editData.getSrcGroupNode())) {
			contName.setEnabled(false);
			kDLabelContainer1.setEnabled(false);
			contAchievementType.setEnabled(false);
		}
	}

	protected FDCTreeBaseDataInfo getEditData() {
		return this.editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		RESchTemplateTaskInfo templateTaskInfo = new RESchTemplateTaskInfo();
		if (getUIContext().get("parent") != null) {
			templateTaskInfo.setParent((RESchTemplateTaskInfo) getUIContext().get("parent"));
		}
		templateTaskInfo.setNumber(generateNumber());
		templateTaskInfo.setReferenceDay(1);
		templateTaskInfo.setTaskType(RESchTaskTypeEnum.COMMONLY);
		templateTaskInfo.setId(BOSUuid.create(templateTaskInfo.getBOSType()));
		Object ownerObj = getUIContext().get("template");
		if(null != ownerObj && ownerObj instanceof RESchTemplateInfo){
			RESchTemplateInfo info=(RESchTemplateInfo)ownerObj;
			info.setState(FDCBillStateEnum.SAVED);
			templateTaskInfo.setTemplate((RESchTemplateInfo)ownerObj);
		}
		//����������Ĭ�Ͼ�����ϸ���� By Owen_wen 2014-4-15
		templateTaskInfo.setIsLeaf(true);
		return templateTaskInfo;
	}
    /**
	 * ���ݸ��ڵ����ɱ������� ������ڵ�Ϊ�գ���ֱ�Ӽ��㵱ǰ����Ϊ0�������� ������ڵ㲻Ϊ�գ���ֱ�Ӽ���parent��ͬ��������
	 */
	private String generateNumber() {
		RESchTemplateTaskInfo pTask = null;
		RESchTemplateTaskInfo tTask = null;
		String currNumber = null;
		RESchTemplateTaskCollection alreadyExistsTask = null;
		if (getUIContext().get("parent") != null) {
			pTask = (RESchTemplateTaskInfo) getUIContext().get("parent");
		}
		if (getUIContext().get("tasks") != null) {
			alreadyExistsTask = (RESchTemplateTaskCollection) getUIContext().get("tasks");
		}
        // �½���Ϊ��һ������
		if (alreadyExistsTask == null || alreadyExistsTask.isEmpty()) {
			return "01";
		}
		int size = alreadyExistsTask.size();
		int maxNumber = 0;
		int tempNumber = 1;
		for (int i = 0; i < size; i++) {
			tTask = alreadyExistsTask.get(i);
			tempNumber = Integer.parseInt(tTask.getNumber());
			alreadyExistNumber.add(tempNumber);
			if (pTask != null) {
				if (tTask.getId().equals(pTask.getId()) || tTask.getParent() == null || !tTask.getParent().getId().equals(pTask.getId())) {
					continue;
				}
			} else {
				if (tTask.getLevel() > 1) {
					continue;
				}
			}
			if (tempNumber > maxNumber) {
				maxNumber = tempNumber;
			}
		}
        currNumber = String.valueOf(maxNumber + 1);
        if (maxNumber < 9) {
			currNumber = "0" + currNumber;
		}
		return currNumber;
		
	}
	
	private void checkNumber() {
		// У���ʽ�ǲ�������
		String number = this.txtNumber.getText().trim();
		if (StringUtils.isEmpty(number)) {
			FDCMsgBox.showInfo("���벻��Ϊ�գ�");
			abort();
		}
		Pattern tDouble = Pattern.compile("([0-9]{1,20})");
		if (!tDouble.matcher(number).matches()) {
			FDCMsgBox.showInfo("����ֻ���������֣�");
			abort();
		}
		
		int intNum = Integer.parseInt(number);
		int err = 0;
		
		RESchTemplateTaskInfo pTask = null;
		RESchTemplateTaskInfo tTask = null;
		RESchTemplateTaskCollection alreadyExistsTask = null;
		if (getUIContext().get("parent") != null) {
			pTask = (RESchTemplateTaskInfo) getUIContext().get("parent");
		} else if (editData.getTemplate() != null) {
			pTask = editData.getParent();
		}
		if (getUIContext().get("tasks") != null) {
			alreadyExistsTask = (RESchTemplateTaskCollection) getUIContext().get("tasks");
		}
		// �½���Ϊ��һ������
		if (alreadyExistsTask == null || alreadyExistsTask.isEmpty()) {
			return;
		}
		int size = alreadyExistsTask.size();
		int tempNumber = 1;
		for (int i = 0; i < size; i++) {
			tTask = alreadyExistsTask.get(i);
			// ���ID��ͬ����˵��������
			if (tTask.getId().equals(editData.getId())) {
				continue;
			}
			tempNumber = Integer.parseInt(tTask.getNumber());
			if (pTask != null) {
				if (tTask.getId().equals(pTask.getId()) || tTask.getParent() == null || !tTask.getParent().getId().equals(pTask.getId())) {
					continue;
				}
			} else {
				if (tTask.getLevel() > 1) {
					continue;
				}
			}
			if (tempNumber == intNum && (pTask == null ? tTask.getParent() == null : tTask.getParent().getId().equals(pTask.getId()))) {
					err++;
					break;
			}
		}
		if (err > 0) {
			FDCMsgBox.showInfo("�����Ѿ����ڣ�");
			abort();
		}

	}

	protected ICoreBase getBizInterface() throws Exception {
		return RESchTemplateTaskFactory.getRemoteInstance();
	}

	public void setDataObject(IObjectValue dataObject) {
		Object aaa = this.getUIContext().get(COL_ID);
		if (null != aaa && !"".equals(aaa.toString().trim())) {
			RESchTemplateTaskInfo bbb = null;
			try {
				bbb = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskInfo(new ObjectUuidPK(aaa.toString()));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			getUIContext().put("ParentNode", bbb);
		} else {
			getUIContext().put("ParentNode", null);
		}

		super.setDataObject(dataObject);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("parent.*"));
		sic.add(new SelectorItemInfo("parent.id"));
		sic.add(new SelectorItemInfo("parent.number"));
		sic.add(new SelectorItemInfo("parent.longNumber"));
		sic.add(new SelectorItemInfo("parent.level"));
		sic.add(new SelectorItemInfo("parent.isLeaf"));
		sic.add(new SelectorItemInfo("template.id"));
		sic.add(new SelectorItemInfo("template.name"));
		sic.add(new SelectorItemInfo("template.number"));
		sic.add(new SelectorItemInfo("level"));
		sic.add(new SelectorItemInfo("isLeaf"));
		
		sic.add(new SelectorItemInfo("standardTask.id"));
		sic.add(new SelectorItemInfo("standardTask.name"));
		sic.add(new SelectorItemInfo("standardTask.number"));
		
		sic.add(new SelectorItemInfo("achievementType.id"));
		sic.add(new SelectorItemInfo("achievementType.name"));
		sic.add(new SelectorItemInfo("achievementType.number"));
	
		sic.add(new SelectorItemInfo("businessType.id"));
		sic.add(new SelectorItemInfo("businessType.bizType.id"));
		sic.add(new SelectorItemInfo("businessType.bizType.number"));
		sic.add(new SelectorItemInfo("businessType.bizType.name"));

		sic.add(new SelectorItemInfo("predecessors.id"));
		sic.add(new SelectorItemInfo("predecessors.predecessorTask.id"));
		sic.add(new SelectorItemInfo("predecessors.predecessorTask.name"));
		sic.add(new SelectorItemInfo("predecessors.predecessorTask.number"));
		sic.add(new SelectorItemInfo("predecessors.differenceDay"));
		sic.add(new SelectorItemInfo("predecessors.predecessorType"));
		
		sic.add(new SelectorItemInfo("adminPerson.id"));
		sic.add(new SelectorItemInfo("adminPerson.name"));
		sic.add(new SelectorItemInfo("adminPerson.number"));
		sic.add(new SelectorItemInfo("adminDept.id"));
		sic.add(new SelectorItemInfo("adminDept.name"));
		sic.add(new SelectorItemInfo("adminDept.number"));
		return sic;
	}

	/**
	 * ǰ������������
	 */
	public void actionAddRow_actionPerformed(ActionEvent e) throws Exception {
		if (getOprtState().equals(OprtState.VIEW)) {
			FDCMsgBox.showInfo("�鿴״̬����������");
			return;
		}
		// �����µ�һ��
		this.tblTaskMain.addRow();
		// initTaskTableDisplay();
	}

	/**
	 * ǰ������ɾ����
	 */
	public void actionRemoveRow_actionPerformed(ActionEvent e) throws Exception {
		if (getOprtState().equals(OprtState.VIEW)) {
			FDCMsgBox.showInfo("�鿴״̬����ɾ����");
			return;
		}
		int seletedRowIndex = this.tblTaskMain.getSelectManager().getActiveRowIndex();
		if (seletedRowIndex < 0) {
			FDCMsgBox.showInfo("��ѡ����Ҫɾ�������ݣ�");
			return;
		}
		int result = FDCMsgBox.showConfirm2("�Ƿ�ɾ����");
		if (result == 2) {
			return;
		}
		this.tblTaskMain.removeRow(seletedRowIndex);
	}

	public void onLoad() throws Exception {
		 if (this.getUIContext().get("fromUI") instanceof RESchTemplateEditUI) {
			RESchTemplateEditUI schTemplateListUI = (RESchTemplateEditUI) this.getUIContext().get("fromUI");
			if (schTemplateListUI.getOprtState().equals(OprtState.VIEW))
				this.setOprtState(schTemplateListUI.getOprtState());
		}
		super.onLoad();

		FDCClientUtils.setRespDeptF7(prmtDutyDep, this);
		FDCClientUtils.setPersonF7(prmtDutyPerson, this, null);
		this.tblTaskMain.addKDTEditListener(new KDTEditListener() {

			public void editCanceled(KDTEditEvent arg0) {
				// setNumberValue();
			}

			public void editStarted(KDTEditEvent arg0) {
				// setNumberValue();
			}

			public void editStarting(KDTEditEvent arg0) {
				// setNumberValue();
			}

			public void editStopped(KDTEditEvent arg0) {
				setNumberValue();
			}

			public void editStopping(KDTEditEvent arg0) {
				// setNumberValue();
			}

			public void editValueChanged(KDTEditEvent arg0) {
				// setNumberValue();
			}

		});

		//���ո�ȥ�Ŀ��˸�ү�ļ���������
		this.txtName.addMouseListener(new MouseAdapter(){
			public void mouseExited(MouseEvent e) {
				if(e.getSource()==txtName){
					if(isGlobalTaskName(txtName.getSelectedItem().toString())) {				
						comboTaskType.setSelectedItem(RESchTaskTypeEnum.MILESTONE);
					}
				}			
			}
		});
		
		
		this.prmtStandardTask.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent arg0) {
				if (arg0.getNewValue() != null && arg0.getNewValue() instanceof StandardTaskGuideInfo) {
					StandardTaskGuideInfo info = (StandardTaskGuideInfo) arg0.getNewValue();
					comboTaskType.setSelectedItem(info.getTaskType());
				}
				loadFieldOfImage();
				loadFieldOfEntry();
			}
		});

		if (this.getUIContext().get("Owner") instanceof RESchTemplateListUI) {
			RESchTemplateListUI schTemplateListUI = (RESchTemplateListUI) this.getUIContext().get("Owner");
			schTemplateListUI.setButtonState();
		}
		
		// ҵ��������Ӽ��� modify by duhongming
		DataChangeListener prmtBizTypeDateListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				prmtBizTypeDateListener(e);
			}
		};
		prmpBusinessType.addDataChangeListener(prmtBizTypeDateListener);
		initTaskTableDisplay();
		if (editData != null && !editData.isIsLeaf()) {
			this.actionRemoveRow.setVisible(false);
			this.actionAddRow.setVisible(false);
		}
//		if (editData != null && editData.getTemplate() != null && editData.getTemplate().getState() != null
//				&& !editData.getTemplate().getState().equals(FDCBillStateEnum.AUDITTED)
//				&& !editData.getTemplate().getState().equals(FDCBillStateEnum.AUDITTING))

		if (editData != null && editData.getTemplate() != null && editData.getTemplate().getState() != null
				&& !editData.getTemplate().getState().equals(FDCBillStateEnum.AUDITTED)
				&& !editData.getTemplate().getState().equals(FDCBillStateEnum.AUDITTING))
		  this.btnEdit.setEnabled(true);	
		//����δ�浼��ļ��Źܿؽڵ�ҲҪ�༭��
		if(editData != null && editData.getTemplate()!=null && editData.getTemplate().getState() == null){
			  this.btnEdit.setEnabled(true);		
		}
	}
	
	private void prmtBizTypeDateListener(DataChangeEvent e) {
		Object[] taskBizTypes = (Object[]) prmpBusinessType.getValue();
		if (taskBizTypes != null) {
			for (int i = 0; i < taskBizTypes.length; i++) {
				ScheduleBizTypeInfo item = (ScheduleBizTypeInfo) taskBizTypes[i];
				if (null != item && "�׶��Գɹ�".equals(item.getName())) {
					prmtAchievementType.setEnabled(true);
					prmtAchievementType.setRequired(true);
					return;
				}
			}
		}
		prmtAchievementType.setEnabled(false);
		prmtAchievementType.setRequired(false);
		prmtAchievementType.setValue(null);
	}

	/**
	 * @discription <���ñ����е�ֵ>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/11>
	 *             <p>
	 * @param <��>
	 * @return <����ֵ����>
	 * 
	 *         modifier <��>
	 *         <p>
	 *         modifyDate <��> 
	 *         <p>
	 *         modifyInfo <��>
	 *         <p>
	 * @see <��ص���>
	 */
	public void setNumberValue() {
		int rowIndex = this.tblTaskMain.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0) {
			return;
		}
		IRow row = this.tblTaskMain.getRow(rowIndex);
		if (null == row.getCell(COL_NAME).getValue() || "".equals(row.getCell(COL_NAME).getValue().toString().trim())) {
			return;
		}
		if (row.getCell(COL_NAME).getValue() instanceof RESchTemplateTaskInfo) {
			RESchTemplateTaskInfo schTemplateTaskInfo = (RESchTemplateTaskInfo) row.getCell(COL_NAME).getValue();
			if (!schTemplateTaskInfo.isIsLeaf()) {
				FDCMsgBox.showError("����ѡ�����ϸ������Ϊǰ������");
				this.tblTaskMain.removeRow(rowIndex);
			} else {
				row.getCell(COL_PTASK).setValue(schTemplateTaskInfo);
				row.getCell(COL_NUMBER).setValue(schTemplateTaskInfo.getNumber());
				row.getCell(COL_NAME).setUserObject(schTemplateTaskInfo);
			}
		}
	}

	public void onShow() throws Exception {
		super.onShow();

		/* ���ء����á������á���Ť */
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);

		/* ����ɾ����Ť����ʾ */
		this.btnRemove.setVisible(false);
		this.txtName.setRequired(true);
		this.txtNumber.setRequired(true);
		if (prmtAchievementType.getValue() != null) {
			prmtAchievementType.setRequired(true);
			prmtAchievementType.setEnabled(true);
		}
		/* ��ǰ���������ñ༭��Ť */
		actionAddRow.putValue(Action.SHORT_DESCRIPTION, "������");
		actionAddRow.putValue(Action.SMALL_ICON, EASResource.getIcon(IMG_BTN_ADDLINE));
		actionRemoveRow.putValue(Action.SHORT_DESCRIPTION, "ɾ����");
		actionRemoveRow.putValue(Action.SMALL_ICON, EASResource.getIcon(IMG_BTN_REMOVELINE));

		/* ���������а�Ť */
		KDWorkButton btnAdd = new KDWorkButton(actionAddRow);
		btnAdd.setEnabled(true);
		btnAdd.setName(BTN_ADDROW);
		btnAdd.setText("������");
		btnAdd.setVisible(true);
		btnAdd.setSize(21, 8);

		/* ����ɾ���а�Ť */
		KDWorkButton btnRemove = new KDWorkButton(actionRemoveRow);
		btnRemove.setEnabled(true);
		btnRemove.setName(BTN_REMOVEROW);
		btnRemove.setText("ɾ����");
		btnRemove.setVisible(true);
		btnRemove.setSize(21, 8);

		/* Ϊ�����Ӱ�Ť */
		this.CtaPredecessorTask.addButton(btnAdd);
		this.CtaPredecessorTask.addButton(btnRemove);
		
		/* modified by zhaoqin for R140430-0044 on 2014/05/26 start */
		// "ר��ƻ�ģ��" ����ʾ"ҵ������","�ɹ����"
		if(null != editData.getTemplate()) {
			ScheduleTemplateTypeEnum templateType = editData.getTemplate().getTemplateType();
			if(ScheduleTemplateTypeEnum.OtherPlanTemplate.equals(templateType)) {
				kDLabelContainer1.setVisible(false);
				contAchievementType.setVisible(false);
			}
		}
		/* modified by zhaoqin for R140430-0044 on 2014/05/26 end */
	}

	/**
	 * @discription <��ʼ��ǰ�������>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/11>
	 *             <p>
	 * @param <��>
	 * @return <����ֵ����>
	 * 
	 *         modifier <��>
	 *         <p>
	 *         modifyDate <��>
	 *         <p>
	 *         modifyInfo <��>
	 *         <p>
	 * @see <��ص���>
	 */
	public void initTaskTableDisplay() {
		/* Ϊ���������а�F7�ؼ� */
		this.tblTaskMain.checkParsed();
		final KDBizPromptBox f7PrmpTask = new KDBizPromptBox();
		f7PrmpTask.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7RESchTemplateTaskQuery");
		f7PrmpTask.setDisplayFormat("$name$");
		f7PrmpTask.setEditFormat("$name$");
		f7PrmpTask.setCommitFormat("$name$");
		f7PrmpTask.setRequired(true);
		f7PrmpTask.setEditable(false);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(editData != null && editData.getTemplate().getId() != null){			
			filter.appendFilterItem("template.id", editData.getTemplate().getId().toString());
		}
		/** ��ѯ���ǵ�ǰ���� modify by duhongming **/
		// if (editData != null && editData.getNumber() != null) {
		// filter.getFilterItems().add(new FilterItemInfo("number",
		// editData.getNumber(), CompareType.NOTEQUALS));
		// }
		view.setFilter(filter);
		f7PrmpTask.setEntityViewInfo(view);
		f7PrmpTask.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				if (e.getNewValue() != null && e.getNewValue() instanceof RESchTemplateTaskInfo) {
					RESchTemplateTaskInfo task = (RESchTemplateTaskInfo) e.getNewValue();
					if (validateF7PrmpTaskIsExist(f7PrmpTask)) {
						// ���ñ����е�ֵ
						setNumberValue();
					} else {
						f7PrmpTask.setValue(null);
					}
				}
			}
		});
		KDTDefaultCellEditor f7EditorPrmpTask = new KDTDefaultCellEditor(f7PrmpTask);
		this.tblTaskMain.getColumn(COL_NAME).setEditor(f7EditorPrmpTask);

		/* Ϊ��ӹ�ϵ�г�ʼ����ʾ */
		KDComboBox relationCboBox = new KDComboBox();
		relationCboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum").toArray());
		relationCboBox.setRequired(true);
		relationCboBox.setEditable(false);
		KDTDefaultCellEditor cboEditorRelationCboBox = new KDTDefaultCellEditor(relationCboBox);
		this.tblTaskMain.getColumn(COL_DIFFERENT_RELATION).setEditor(cboEditorRelationCboBox);

		/* ���ñ�¼�� */
		this.tblTaskMain.getColumn(COL_NAME).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		this.tblTaskMain.getColumn(COL_DIFFERENT_RELATION).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		this.tblTaskMain.getColumn(COL_DIFFERENT_DAY).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);

		// ���������
		this.tblTaskMain.getColumn(COL_NUMBER).getStyleAttributes().setLocked(true);

		/* ���ʱ�� */
		final KDNumberTextField kDNumberTextField = new KDNumberTextField();
		kDNumberTextField.setEditable(true);
		kDNumberTextField.setRequired(true);
		// kDNumberTextField.setMaximumNumber(BigDecimal.valueOf(100000));
		KDTDefaultCellEditor dayNumerEditor = new KDTDefaultCellEditor(kDNumberTextField);
		this.tblTaskMain.getColumn(COL_DIFFERENT_DAY).setEditor(dayNumerEditor);
		this.tblTaskMain.addKDTEditListener(new KDTEditListener() {

			public void editCanceled(KDTEditEvent arg0) {
			}

			public void editStarted(KDTEditEvent arg0) {
			}

			public void editStarting(KDTEditEvent arg0) {
			}

			public void editStopped(KDTEditEvent e) {
				if (!validateDiffDayValue(kDNumberTextField)) {
					FDCMsgBox.showInfo("��������������ƣ�");
					return;
				}
			}

			public void editStopping(KDTEditEvent arg0) {
			}

			public void editValueChanged(KDTEditEvent arg0) {
			}

		});

	}

	/**
	 * @discription <�Դ��������ֵ�����ƣ������˾͸�����ʾ�����������>
	 * @author <Xiaolong Luo>
	 * @createDate <2011//>
	 *             <p>
	 * @param <��>
	 * @return <����ֵ����>
	 * 
	 *         modifier <��>
	 *         <p>
	 *         modifyDate <��>
	 *         <p>
	 *         modifyInfo <��>
	 *         <p>
	 * @see <��ص���>
	 */
	public boolean validateDiffDayValue(KDNumberTextField kDNumberTextField) {
		int rowIndex = this.tblTaskMain.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0) {
			return true;
		}
		IRow row = this.tblTaskMain.getRow(rowIndex);
		Object diffDayObj = row.getCell(COL_DIFFERENT_DAY).getValue();
		if (null != diffDayObj && !"".equals(diffDayObj.toString().trim())) {
			double diffDay = Double.parseDouble(diffDayObj.toString().trim());
			if (diffDay > 1000000000) {
				kDNumberTextField.setValue(null);
				row.getCell(COL_DIFFERENT_DAY).setValue("");
				return false;
			}
		}
		return true;
	}

	/**
	 * @discription <�жϸ�ǰ�������Ƿ��Ѿ���ѡ>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/25>
	 *             <p>
	 * @param <��>
	 * @return <����ֵ����>
	 * 
	 *         modifier <��>
	 *         <p>
	 *         modifyDate <��>
	 *         <p>
	 *         modifyInfo <��>
	 *         <p>
	 * @see <��ص���>
	 */
	public boolean validateF7PrmpTaskIsExist(KDBizPromptBox f7PrmpTask) {
		int rowIndex = this.tblTaskMain.getSelectManager().getActiveRowIndex();
		Object taskObj = f7PrmpTask.getValue();
		for (int i = 0; i < this.tblTaskMain.getRowCount(); i++) {
			if (i == rowIndex) {
				continue;
			}
			IRow row = this.tblTaskMain.getRow(i);
			Object celltaskObj = row.getCell(COL_PTASK).getValue();
			if (null != taskObj && null != celltaskObj && taskObj.toString().equals(celltaskObj.toString())) {
				FDCMsgBox.showInfo("��ǰ�������Ѿ���ѡ��");
				return false;
			}
		}
		if (f7PrmpTask.getValue() instanceof RESchTemplateTaskInfo) {
			RESchTemplateTaskInfo f7Value = (RESchTemplateTaskInfo) f7PrmpTask.getValue();
			if (f7Value != null && editData != null && editData.getId() != null
					&& f7Value.getId().toString().equals(editData.getId().toString())) {
				FDCMsgBox.showInfo("����ѡ��������Ϊǰ������������ѡ��");
				return false;
			}
		}
		
		return true;
	}

	protected void initWorkButton() {
		super.initWorkButton();

		/* F7�ؼ� ��׼���� �� query */
//		this.prmtStandardTask.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7StandardTaskGuideNewQuery");
//		this.prmtStandardTask.setRefresh(true);
//		this.prmtStandardTask.setEditable(false);

		/* F7�ؼ� ҵ������ �� query */
		this.prmpBusinessType.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7ScheduleBizTypeQuery");
		this.prmpBusinessType.setEditable(false);

		/* F7�ؼ� �ɹ���� �� query */
		this.prmtAchievementType.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7AchievementTypeQuery");
		this.prmtAchievementType.setEditable(false);

		// ����ҵ�����Ϳ��Զ�ѡ
		this.prmpBusinessType.setEnabledMultiSelection(true);
		// this.prmpBusinessType.setCommitFormat("$name$");
		this.prmpBusinessType.setEditFormat("$name$");
		this.prmpBusinessType.setDisplayFormat("$name$");

		/* �������Ƶ���󳤶� */
		this.txtName.setMaxLength(100);

		/* ���ñ������󳤶� */
		this.txtNumber.setMaxLength(4);

		/* ������������󳤶� */
		this.txtDescription.setMaxLength(200);
		if (getOprtState().equals(OprtState.VIEW)) {
			this.actionEdit.setEnabled(false);
		}
	}

	/**
	 * @discription <���ݸ��ڵ�id�����Ĭ��ͬ���ε�������+1>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/18>
	 *             <p>
	 * @param <��>
	 * @return <����ֵ����>
	 * 
	 *         modifier <��>
	 *         <p>
	 *         modifyDate <��>
	 *         <p>
	 *         modifyInfo <��>
	 *         <p>
	 * @see <��ص���>
	 */
	public String getDefaultNumber(Object parentId, String templateId) {
		String number = "";
		EntityViewInfo view = getTempLateSelector(parentId, templateId);
		RESchTemplateTaskCollection templateTaskCollection = new RESchTemplateTaskCollection();
		try {
			templateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		// ��ʱ������ȡ���ڵ�ͬ���������ֵ
		int numberInt = 0;
		String[] numberArray = new String[templateTaskCollection.size()];
		for (int temp = 0; temp < templateTaskCollection.size(); temp++) {
			RESchTemplateTaskInfo schTemplateTaskInfo = (RESchTemplateTaskInfo) templateTaskCollection.get(temp);
			Pattern tDouble = Pattern.compile("([0-9]{1,20})");
			if (tDouble.matcher(schTemplateTaskInfo.getNumber().toString()).matches()) {
				if (numberInt < Integer.parseInt(schTemplateTaskInfo.getNumber().toString())) {
					numberInt = Integer.parseInt(schTemplateTaskInfo.getNumber().toString());
				}
			}
			numberArray[temp] = schTemplateTaskInfo.getNumber().toString();
		}
		if (numberInt < 10) {
			number = "0" + (numberInt + 1);
		} else {
			number = "" + (numberInt + 1);
		}
		return number;
	}

	/**
	 * 
	 * @description ���ݸ�������������ȡһ��sql
	 * @author �ź���
	 * @createDate 2011-10-12
	 * @param parentId
	 * @param templateId
	 * @return EntityViewInfo
	 * @version EAS7.0
	 * @see
	 */
	private EntityViewInfo getTempLateSelector(Object parentId, String templateId) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (parentId != null && !parentId.equals("")) {
			filter.appendFilterItem("parent.id", parentId.toString());
			view.setFilter(filter);
			return view;
		}
		if (templateId != null && !templateId.equals("")) {
			filter.appendFilterItem("template.id", templateId);
			view.setFilter(filter);
			return view;
		}
		return view;
	}

	public void loadFields() {
		super.loadFields();

		/* ���óɹ������� */
		this.prmtAchievementType.setEnabled(false);
		this.comboTaskType.setSelectedItem(editData.getTaskType());
		// ������(ְԱ)
		this.prmtDutyPerson.setValue(editData.getAdminPerson());
		// ���β���
		// if (FDCHelper.isEmpty(fdcScheudleTask.getAdminDept())) {
		// taskProperties.prmtDutyDep.setValue(SysContext.getSysContext().
		// getCurrentOrgUnit());
		// } else {
		this.prmtDutyDep.setValue(editData.getAdminDept());
		/* ������ʱ��Ϊ���븳Ĭ��ֵ */
		RESchTemplateListUI schTemplateListUI = (RESchTemplateListUI) this.getUIContext().get("Owner");
		/*parentID_temp = schTemplateListUI.parentId;
		tempLateId_temp = schTemplateListUI.templateId;
		if (getOprtState().equals(OprtState.ADDNEW)) {
			this.txtNumber.setText(getDefaultNumber(parentID_temp, tempLateId_temp));
		}*/

		if (null == this.editData.getId()) {
			return;
		}
		
		List objList = new ArrayList();
		try {
			RESchTemplateTaskInfo schTemplateTaskInfo = editData;
			RESchTemplateTaskBizTypeCollection schTemplateTaskBizTypeCollection = schTemplateTaskInfo.getBusinessType();
			for (int s = 0; s < schTemplateTaskBizTypeCollection.size(); s++) {
				RESchTemplateTaskBizTypeInfo schTemplateTaskBizTypeInfo = schTemplateTaskBizTypeCollection.get(s);
				objList.add(schTemplateTaskBizTypeInfo.getBizType());
			}
			this.prmpBusinessType.setValue(objList.toArray());
			this.tblTaskMain.checkParsed(true);
			initTaskTableDisplay();
			
			/* ��ʼ��ǰ������ָ���б� */
			RESchTemplateTaskPredecessorCollection schTemplateTaskPredecessorCollection = schTemplateTaskInfo.getPredecessors();
			for (int st = 0; st < schTemplateTaskPredecessorCollection.size(); st++) {
				RESchTemplateTaskPredecessorInfo schTemplateTaskPredecessorInfo = schTemplateTaskPredecessorCollection.get(st);
				IRow row = this.tblTaskMain.addRow();
				row.getCell(COL_ID).setValue(schTemplateTaskPredecessorInfo.getId());
				row.getCell(COL_PTASK).setValue(schTemplateTaskPredecessorInfo.getPredecessorTask());
				row.getCell(COL_NAME).setValue(schTemplateTaskPredecessorInfo.getPredecessorTask().getName());
				row.getCell(COL_NUMBER).setValue(schTemplateTaskPredecessorInfo.getPredecessorTask().getNumber());
				row.getCell(COL_DIFFERENT_RELATION).setValue(schTemplateTaskPredecessorInfo.getPredecessorType());
				row.getCell(COL_DIFFERENT_DAY).setValue(schTemplateTaskPredecessorInfo.getDifferenceDay() + "");
				this.tblTaskMain.setVisible(true);
			}
		} catch (Exception e1) {
			logger.error(e1);
		}

		this.KDPanelImg.updateUI();
		KDPanelImg.removeAll();
		loadFieldOfImage();
		loadFieldOfEntry();
	}

	/**
	 * @discription <����ͼƬ>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/12>
	 *             <p>
	 * @param <��>
	 * @return <����ֵ����>
	 * 
	 *         modifier <��>
	 *         <p>
	 *         modifyDate <��>
	 *         <p>
	 *         modifyInfo <��>
	 *         <p>
	 * @see <��ص���>
	 */
	private void loadFieldOfImage() {
		try {
			// ��ʼ��ͼƬ��ʾ
			if (null != this.prmtStandardTask.getValue() && !"".equals(this.prmtStandardTask.getValue().toString().trim())) {
				if (this.prmtStandardTask.getValue() instanceof StandardTaskGuideInfo) {
					StandardTaskGuideInfo standardTaskGuideInfo = (StandardTaskGuideInfo) this.prmtStandardTask.getValue();
					if (null != standardTaskGuideInfo.getId() && !"".equals(standardTaskGuideInfo.getId().toString().trim())) {
						imageAttUtil = new FDCImageAttUtil(this, standardTaskGuideInfo.getId().toString().trim(), null);
						List imageList = imageAttUtil.getImgAtts();
						if (!imageList.isEmpty()) {
							attID = ((AttachmentInfo) imageList.get(0)).getId().toString();
							imageAttUtil.setIMGByAttID(attID, this.KDPanelImg);
						}else{
							this.KDPanelImg.removeAll();
							KDPanelImg.updateUI();
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("RESchTemplateTaskEditUI 1027 error��");
		} 
	}

	/**
	 * @description ��ʵ��ӻ�ȡ���ݸ������
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void loadFieldOfEntry() {
		this.tblGuideOne.removeRows();
		this.tblGuideTwo.removeRows();
		this.tblGuideOne.checkParsed();
		this.tblGuideTwo.checkParsed();
		try {
			if (null != this.prmtStandardTask.getValue() && !"".equals(this.prmtStandardTask.getValue().toString().trim())) {
				if (this.prmtStandardTask.getValue() instanceof StandardTaskGuideInfo) {
					StandardTaskGuideInfo standardTaskGuideInfo = (StandardTaskGuideInfo) this.prmtStandardTask.getValue();
					StandardTaskGuideInfo taskGuideInfo = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideInfo(
							new ObjectUuidPK(standardTaskGuideInfo.getId()));
					for (int i = 0; i < taskGuideInfo.getEntrys().size(); i++) {
						StandardTaskGuideEntryInfo entryInfo = taskGuideInfo.getEntrys().get(i);
						IRow row = null;
						if (GuideTypeEnum.systemFlow.equals(entryInfo.getGuideType())) {// ����ָʾ���������
							row = this.tblGuideOne.addRow();
						} else {
							row = this.tblGuideTwo.addRow();
						}
						copyProperies(entryInfo, row);
					}
				}
			}
		} catch (Exception e) {
			logger.error("RESchTemplateTaskEditUI 1064 error��");
		}

	}

	/**
	 * @description ��ʵ���ȡ������������
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void copyProperies(StandardTaskGuideEntryInfo entryInfo, IRow row) {
		row.getCell(COL_DOCNAME).setValue(entryInfo.getDocName());
		row.getCell(COL_DOCPATH).setValue(entryInfo.getDocPath());
		row.getCell(COL_ID).setValue(entryInfo.getId().toString());
	}
}