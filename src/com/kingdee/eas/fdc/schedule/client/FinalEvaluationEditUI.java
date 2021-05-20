/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
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
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportCollection;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationEntryCollection;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationEntryInfo;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationFactory;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FinalEvaluationEditUI extends AbstractFinalEvaluationEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(FinalEvaluationEditUI.class);
    double selfScoreRate = 0;
	double finalScoreRate = 0;
	private Map comMap = new HashMap();
    /**
     * output class constructor
     */
    public FinalEvaluationEditUI() throws Exception
    {
        super();
    }
    public void storeFields() {
		super.storeFields();
		this.editData.getEntries().clear();
		SelfAndFinalEvaluationEntryInfo entryInfo = null;
		Object selfPerson = prmtSelfEvaluationPerson.getValue();
		if (null != selfPerson && selfPerson instanceof PersonInfo) {
			editData.setSelfEvaluationPerson((PersonInfo) selfPerson);
		} else if (null != selfPerson && selfPerson instanceof UserInfo) {
			editData.setSelfEvaluationPerson(((UserInfo) selfPerson).getPerson());
		} else {
			editData.setSelfEvaluationPerson(null);
		}
		Object finalPerson = finalEvaluationPerson.getValue();
		if (null != finalPerson && finalPerson instanceof PersonInfo) {
			editData.setFinalEvaluationPerson((PersonInfo) finalPerson);
		} else if (null != finalPerson && finalPerson instanceof UserInfo) {
			editData.setFinalEvaluationPerson(((UserInfo) finalPerson).getPerson());
		} else {
			editData.setFinalEvaluationPerson(null);
		}
		Map map = getDeptMonthlyScheduleTaskMap();
		selfScoreRate = 0;
		finalScoreRate = 0;
		for (int i = 0; i < this.kdtEntries.getRowCount(); i++) {
			IRow row = this.kdtEntries.getRow(i);
			entryInfo = new SelfAndFinalEvaluationEntryInfo();
			if (row.getCell("selfEvaluationScore").getValue() != null) {
				entryInfo.setSelfEvaluationScore(BigDecimal.valueOf(Double.parseDouble(row.getCell("selfEvaluationScore").getValue().toString())));
				double d = Double.parseDouble(row.getCell("weightRate").getValue().toString());
				double WeightRate = d / 100;
				double selfScore = Double.parseDouble(row.getCell("selfEvaluationScore").getValue().toString());
				selfScoreRate += WeightRate * selfScore;
			}
			if (row.getCell("completePercent").getValue() != null) {
				entryInfo.setCompletePercent(new BigDecimal(row.getCell("completePercent").getValue().toString()));
			}
			if (row.getCell("actualEndDate").getValue() != null) {
				entryInfo.setActualEndDate((Date) row.getCell("actualEndDate").getValue());
			}
			if (row.getCell("selfCompleteDes").getValue() != null) {
				entryInfo.setSelfCompleteDes(row.getCell("selfCompleteDes").getValue().toString());
			}
			if (row.getCell("finalEvaluationScore").getValue() != null) {
				BigDecimal weight = new BigDecimal(row.getCell("weightRate").getValue().toString());
				BigDecimal bigFinalScore = new BigDecimal(row.getCell("finalEvaluationScore").getValue().toString());
				BigDecimal count = new BigDecimal(bigFinalScore.multiply(weight.divide(new BigDecimal("100"))).toString());
				finalScoreRate = finalScoreRate + count.doubleValue();
				entryInfo.setFinalEvaluationScore(new BigDecimal(row.getCell("finalEvaluationScore").getValue().toString(), new MathContext(2)));
			}
			if (row.getCell("finalCompleteDes").getValue() != null) {
				entryInfo.setFinalCompleteDes(row.getCell("finalCompleteDes").getValue().toString());
			}
			if (row.getCell("relateTask").getValue() != null) {
				entryInfo.setRelateTask((DeptMonthlyScheduleTaskInfo) map.get(row.getCell("relateTask").getValue().toString()));
			}
			editData.setFinalEvaluationScore(new BigDecimal(finalScoreRate, new MathContext(3)));
			editData.setSelfEvaluationScore(new BigDecimal(selfScoreRate, new MathContext(3)));
			this.editData.getEntries().add(entryInfo);
		}
		
		/* modified by zhaoqin for R140523-0226 on 2014/06/18 */
		updateDeptMonthlyScheduleTaskEntry();
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return SelfAndFinalEvaluationFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kdtEntries;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		// �����ɵ�
		btnAddNew.setEnabled(false);
		btnAddNew.setVisible(false);
		// ɾ���ɵ�
		btnRemove.setEnabled(false);
		btnRemove.setVisible(false);
		// ���Ƹɵ�
		btnCopy.setEnabled(false);
		btnCopy.setVisible(false);
		
		// ǰһ
		btnPre.setEnabled(false);
		btnPre.setVisible(false);
		// ��һ
		btnFirst.setEnabled(false);
		btnFirst.setVisible(false);
		// ��һ
		btnNext.setEnabled(false);
		btnNext.setVisible(false);
		// ���
		btnLast.setEnabled(false);
		btnLast.setVisible(false);
		btnSubmit.setVisible(true);
		btnSubmit.setEnabled(false);
		btnPrint.setVisible(false);
		btnPrintPreview.setVisible(false);
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
		btnEdit.setVisible(false);
		btnAudit.setVisible(true);
		btnUnAudit.setVisible(true);
		btnSave.setEnabled(true);
	}

	/**
	 * 
	 */
	public void loadFields() {
		super.loadFields();
		if (editData != null && null != editData.getFinalEvaluationPerson()) {
			finalEvaluationPerson.setValue(editData.getFinalEvaluationPerson());
		} else {
			finalEvaluationPerson.setValue((SysContext.getSysContext().getCurrentUserInfo()));
		}

		if (editData != null && null != editData.getSelfEvaluationPerson()) {
			this.prmtSelfEvaluationPerson.setValue(editData.getSelfEvaluationPerson());
		} else {
			prmtSelfEvaluationPerson.setValue((SysContext.getSysContext().getCurrentUserInfo()));
		}
		AdminOrgUnitInfo adminDept = (AdminOrgUnitInfo) getUIContext().get("adminDept");
		this.prmtAdminDept.setValue(adminDept);
		Date scheudleMonth = (Date) getUIContext().get("scheudleMonth");
		this.pkScheduleMonth.setValue(scheudleMonth);
		this.finalEvaluationDate.setValue(new Date());
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.windowTitle = "����";
		/**
		 * Ϊ����Ӽ���
		 */
		this.kdtEntries.addKDTEditListener(new KDTEditListener() {
			public void editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent arg0) {

			}

			public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent arg0) {
				checkInput();
				setEvaluationScore();
				
				/* modified by zhaoqin for R140523-0226 on 2014/06/18 */
				isFWSaved = false;
			}

			public void editCanceled(KDTEditEvent kdteditevent) {
			}

			public void editStarting(KDTEditEvent kdteditevent) {
			}

			public void editStopping(KDTEditEvent kdteditevent) {
			}

			public void editValueChanged(KDTEditEvent kdteditevent) {
				state = true;
				// checkInputDate();
			}
		});
	}
	/**
	 * 
	 */
	public void setEvaluationScore() {
		finalScoreRate = 0;
		for (int i = 0; i < this.kdtEntries.getRowCount(); i++) {
			IRow row = this.kdtEntries.getRow(i);
			if (row.getCell("finalEvaluationScore").getValue() != null) {
				BigDecimal weight = new BigDecimal(row.getCell("weightRate").getValue().toString());
				BigDecimal bigFinalScore = new BigDecimal(row.getCell("finalEvaluationScore").getValue().toString());
				BigDecimal count = new BigDecimal(bigFinalScore.multiply(weight.divide(new BigDecimal("100"))).toString());
				finalScoreRate = finalScoreRate + count.doubleValue();
			}
		}
		kdtEntries.getFootRow(0).getCell("finalEvaluationScore").setValue(new Double(finalScoreRate));
	}
	public void checkInput() {
		IRow rowIndex = KDTableUtil.getSelectedRow(kdtEntries);
		String regex = "^[0-9]+(.[0-9]*)?$";
		String regex2 = "^[0-9]+(.[0-9]{2,})?$";
		Object selfScoreStr = rowIndex.getCell("finalEvaluationScore").getValue();
		if (selfScoreStr != null && !selfScoreStr.toString().matches(regex)) {
			rowIndex.getCell("finalEvaluationScore").setValue(null);
			return;
		} else {
			if (selfScoreStr != null) {
				Double d = new Double(selfScoreStr.toString());
				if (d.doubleValue() < 0 || d.doubleValue() > 10) {
					FDCMsgBox.showWarning("�����÷ֽ���0~10֮��!");
					rowIndex.getCell("finalEvaluationScore").setValue(null);
					return;
				}
				String self = selfScoreStr.toString();
				if (self.matches(regex2) && self.length() > 4) {
					self = self.substring(0, 4);
					rowIndex.getCell("finalEvaluationScore").setValue(self);
				}
			}
		}
	}
	/**
	 * @throws Exception
	 */
	public void onShow() throws Exception {
		super.onShow();
		this.windowTitle = "����";
		// ��������
		btnAttachment.setEnabled(true);
		btnAttachment.setVisible(true);
		// ��������鿴
		btnAuditResult.setVisible(true);
		btnAudit.setVisible(true);
		btnUnAudit.setVisible(true);
		btnAudit.setEnabled(false);
		initdata();
		initDetailTable();
		/**
		 * modify by libing
		 */
		this.kdtEntries.getColumn("relateTask").getStyleAttributes().setHided(false);
		this.kdtEntries.getColumn("relateTask").setWidth(1);
		this.kdtEntries.getColumn("relateTaskId").getStyleAttributes().setHided(false);
		this.kdtEntries.getColumn("relateTaskId").setWidth(1);
		this.kdtEntries.getColumn("project").getStyleAttributes().setHided(false);
		this.kdtEntries.getColumn("project").setWidth(1);
		this.kdtEntries.getColumn("taskType").getStyleAttributes().setHided(false);
		this.kdtEntries.getColumn("taskType").setWidth(1);
		this.kdtEntries.getColumn("comboTaskOrigin").getStyleAttributes().setHided(false);
		this.kdtEntries.getColumn("comboTaskOrigin").setWidth(1);
		if (editData.getState() == FDCBillStateEnum.AUDITTED) {
			this.btnAudit.setEnabled(false);
			this.btnUnAudit.setEnabled(true);
			this.btnSave.setEnabled(false);
		}
		//BT737805��׼��Ʒ�У��ڲ����¶ȼƻ�ִ���У�δ����������ֱ�ӵ�������ϵͳ���ж�
		if (editData.getState() == FDCBillStateEnum.SUBMITTED) {
//		if (editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
			this.btnAudit.setVisible(true);
			this.btnAudit.setEnabled(true);
			this.btnUnAudit.setVisible(true);
			this.btnSubmit.setEnabled(true);
			this.btnSave.setEnabled(false);
		}
		KDTextField scoreText = new KDTextField();
		KDTDefaultCellEditor requestEditor = new KDTDefaultCellEditor(scoreText);
		kdtEntries.getColumn("finalEvaluationScore").setEditor(requestEditor);
		if (kdtEntries.getRowCount() > 0) {
			for (int i = 0; i < kdtEntries.getRowCount(); i++) {
				Object taskId = kdtEntries.getRow(i).getCell("relateTask").getValue();
				Object completePercent = kdtEntries.getRow(i).getCell("completePercent").getValue();
				String comValue = "";
				if (completePercent != null) {
					comValue = completePercent.toString();
					comMap.put(taskId.toString(), comValue);
					
				}
			}
		}
		
	}
	private void initdata() {
		this.txtSelfDes.setText(editData.getSelfEvaluationDes());
		this.finalEvaluationDate.setValue(new Date());
		FDCTableHelper.apendFootRow(kdtEntries, new String[] { "weightRate" });
		kdtEntries.getFootRow(0).getCell("weightRate").getStyleAttributes().setNumberFormat("#,##0");
	}
	/**
	 * @param e
	 * @throws Exception
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			Object selfScore = kdtEntries.getRow(i).getCell("selfEvaluationScore").getValue();
			Object finalScore = kdtEntries.getRow(i).getCell("finalEvaluationScore").getValue();
			Object completePercent = kdtEntries.getRow(i).getCell("completePercent").getValue();
			Object relateTaskId = kdtEntries.getRow(i).getCell("relateTaskId").getValue();
			int comInt = 0;
			if (completePercent != null) {
				comInt = new BigDecimal(completePercent.toString()).intValue();
			}
			if (comMap.size() > 0 && relateTaskId != null) {
				int comOld = 0;
				Object objectOld = comMap.get(relateTaskId.toString());
				if (objectOld != null) {
					comOld = new BigDecimal(objectOld.toString()).intValue();
					if (comInt < comOld) {
						FDCMsgBox.showInfo(this, "��" + (i + 1) + "��ǰ���񹤳���Ŀǰ�깤" + comOld + "%,���ν��Ȳ��ܵ����ϴλ㱨����");
						SysUtil.abort();
					}
				}
			}
			if (selfScore == null && finalScore != null) {
				FDCMsgBox.showWarning(this, "��" + (i + 1) + "������û�����������������������������");
				SysUtil.abort();
			}
			
		}
		super.actionSave_actionPerformed(e);
		editData.setState(FDCBillStateEnum.SAVED);
		setOprtState(OprtState.VIEW);
		btnAudit.setVisible(true);
		btnAudit.setEnabled(false);
		btnUnAudit.setVisible(true);
		btnUnAudit.setEnabled(false);
		btnSubmit.setEnabled(true);
		
	
	}
	
	
	

	/**
	 * @param e
	 * @throws Exception
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		
		if (null == editData.getId()) {
			FDCMsgBox.showWarning(this, "���ȱ��浥��");
			SysUtil.abort();
		} else {
			SelfAndFinalEvaluationFactory.getRemoteInstance().audit(BOSUuid.read(editData.getId().toString()));
			editData.setState(FDCBillStateEnum.AUDITTED);
			FDCMsgBox.showInfo(this, "�����ɹ�");
		}
		setOprtState(OprtState.VIEW);
		this.btnAudit.setVisible(true);
		this.btnSave.setEnabled(false);
		this.btnUnAudit.setVisible(true);
		this.btnAudit.setEnabled(false);
		this.btnUnAudit.setEnabled(true);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		
		if (editData.getId() != null) {
			SelfAndFinalEvaluationFactory.getRemoteInstance().unAudit(BOSUuid.read(editData.getId().toString()));
			editData.setState(FDCBillStateEnum.SAVED);
			FDCMsgBox.showInfo(this, "�����ɹ�");
		} else {
			FDCMsgBox.showWarning("����״̬��������д˲���");
			SysUtil.abort();
		}
		this.btnAudit.setVisible(true);
		this.btnAudit.setEnabled(false);
		this.btnUnAudit.setVisible(true);
		this.btnSave.setEnabled(true);
		this.btnUnAudit.setEnabled(false);
	}
	/**
	 * @param e
	 * @throws Exception
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		Object selfScore = null;
		Object completePercent = null;
		Object finalScore = null;
		this.storeFields();//�󶨵����ݴ浽editData��
		SelfAndFinalEvaluationEntryCollection cols = editData.getEntries();
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			selfScore = kdtEntries.getRow(i).getCell("selfEvaluationScore").getValue();
			completePercent = kdtEntries.getRow(i).getCell("completePercent").getValue();
			if (completePercent != null) {
				completePercent = kdtEntries.getRow(i).getCell("completePercent").getValue().toString();
			} else {
				cols.get(i).setCompletePercent(FDCHelper.toBigDecimal(completePercent));
			}
			finalScore = kdtEntries.getRow(i).getCell("finalEvaluationScore").getValue();
			if (completePercent != null && !completePercent.equals("0") && selfScore == null) {
				//FDCMsgBox.showWarning(this, "��" + (i + 1) + "������û���������������ύ��");
				FDCMsgBox.showWarning(this, "��" + (i + 1) + "������û�����������ȶ��Լ��������������ܽ���������");
				SysUtil.abort();
			}
			
			if (completePercent != null && !completePercent.equals("0") && finalScore == null) {
				FDCMsgBox.showWarning(this, "��" + (i + 1) + "������û��� �������������ύ��");
				SysUtil.abort();
			}
			if (finalScore != null) {
				cols.get(i).setFinalEvaluationScore(FDCHelper.toBigDecimal(finalScore));
			}
		}
		setWeightRateTotal();
		SelfAndFinalEvaluationFactory.getRemoteInstance().submit(editData);
		showSubmitSuccess();

		editData.setState(FDCBillStateEnum.SUBMITTED);
		setOprtState(OprtState.VIEW);
		this.btnAudit.setVisible(true);
		this.btnAudit.setEnabled(true);
		this.btnUnAudit.setVisible(true);
		this.btnSubmit.setEnabled(false);
		this.btnSave.setEnabled(false);
		
	}
	
	/**
	 * 
	 */
	protected void showSubmitSuccess() {
		FDCMsgBox.showInfo(this, "�����ύ�ɹ���");
		this.btnAudit.setVisible(true);
		this.btnUnAudit.setVisible(true);
	}

	/**
	 * ��ʼ����¼��,Ϊ�����ӱ༭��
	 */
	public void initDetailTable() {
		super.initDetailTable();
		FDCTableHelper.apendFootRow(kdtEntries, new String[] { "weightRate" });
		kdtEntries.getFootRow(0).getCell("weightRate").getStyleAttributes().setNumberFormat("#,##0");
		this.kdtEntries.getColumn("selfEvaluationScore").getStyleAttributes().setLocked(true);
		KDFormattedTextField scoreText = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		scoreText.setPrecision(0);
		scoreText.setSupportedEmpty(true);
		KDTDefaultCellEditor requestEditor = new KDTDefaultCellEditor(scoreText);
		kdtEntries.getColumn("finalEvaluationScore").setEditor(requestEditor);

		KDTextField finishStandard = new KDTextField();
		finishStandard.setMaxLength(200);
		KDTDefaultCellEditor finishStandardEditor = new KDTDefaultCellEditor(finishStandard);
		kdtEntries.getColumn("finalCompleteDes").setEditor(finishStandardEditor);

	}

	private void copyProperties(Map map, DeptMonthlyScheduleTaskInfo taskInfo, IRow row) {
		if (map == null || map.isEmpty()) {
			return;
		}
		if (map.containsKey(taskInfo.getId().toString())) {
			IRow processRow = (IRow) map.get(taskInfo.getId().toString());
			//row.getCell("completePercent").setValue(processRow.getCell("completePrecent").getValue());
			if(processRow.getCell("realEndDate").getValue()!=null){
				row.getCell("actualEndDate").setValue(processRow.getCell("realEndDate").getValue());
			}
			Object comPercent = null;
			if (processRow.getCell("completePrecent").getValue() != null) {
				comPercent = processRow.getCell("completePrecent").getValue().toString();
				comMap.put(taskInfo.getId().toString(), comPercent);
			}
			row.getCell("completePercent").setValue(comPercent);
			if (comPercent != null && FDCHelper.toBigDecimal(comPercent).compareTo(FDCHelper.ONE_HUNDRED) == 0) {
				row.getCell("isCompelete").setValue("��");
			} else {
				row.getCell("isCompelete").setValue("��");
			}
		}
	}

	private Map getScheduleTaskData() {
		KDTable tableMain = (KDTable) getUIContext().get("tableMain");
		if (tableMain == null) {
			return null;
		}
		Map map = new HashMap();
		for (int i = 0; i < tableMain.getRowCount(); i++) {
			IRow row = tableMain.getRow(i);
			map.put(row.getCell("id").getValue(), row);
		}
		return map;
	}

	protected IObjectValue createNewData() {
		SelfAndFinalEvaluationInfo info = new SelfAndFinalEvaluationInfo();
		return info;
	}

	public boolean destroyWindow() {
		Object obj = null;
		obj = getUIContext().get("Owner2");
		if (obj != null && obj instanceof DeptMonthlyTaskExecListUI) {
			DeptMonthlyTaskExecListUI ui = (DeptMonthlyTaskExecListUI) obj;
			ui.execQuery();
			try {
				ui.setScoreZero();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.destroyWindow();
	}

	/**
 * 
 */
	protected void showSaveSuccess() {
		// super.showSaveSuccess();
		MsgBox.showInfo(this, "��������ɹ���");
	}

	/**
	 * 
	 */
	public void getTaskExecInfo() {
		if (editData != null && editData.getFinalEvaluationDate() != null) {
			this.finalEvaluationDate.setValue(editData.getFinalEvaluationDate());
		} else {
			this.finalEvaluationDate.setValue(new Date());
		}
		this.txtFinalDes.setText(editData.getFinalEvaluationDes());
		SelfAndFinalEvaluationEntryCollection entryCollection = editData.getEntries();
		
		//modify by lihaiou,2014-08-01,����������Ҫ���б������һ�£�BOS�ײ��ڲ����ݵ�ʱ�򣬲�ѯ�ӱ��ʱ��ᶪ��������ֶ�
		Object[] resultList = orderDataByFinishDate(entryCollection);
		
		SelfAndFinalEvaluationEntryInfo entryInfo = null;
		Map map = getScheduleTaskData();
		double selfScoreRate = 0;
		double finalScoreRate = 0;
		kdtEntries.checkParsed();
		for (int i = 0; i < resultList.length; i++) {
			entryInfo = (SelfAndFinalEvaluationEntryInfo)resultList[i];
			IRow row = kdtEntries.addRow();
			DeptMonthlyScheduleTaskInfo relateTask = entryInfo.getRelateTask();
			row.getCell("taskName").setValue(relateTask.getTaskName());
			row.getCell("adminPerson").setValue(relateTask.getAdminPerson());
			row.getCell("finishStandard").setValue(relateTask.getFinishStandard());
			row.getCell("planFinishDate").setValue(relateTask.getPlanFinishDate());
			row.getCell("weightRate").setValue(relateTask.getWeightRate());
			row.getCell("relateTask").setValue(relateTask.getId());
			//row.getCell("actualEndDate").setValue(entryInfo.getActualEndDate());
			row.getCell("selfEvaluationScore").setValue(entryInfo.getSelfEvaluationScore());
			row.getCell("completePercent").setValue(entryInfo.getCompletePercent());
			FDCScheduleTaskInfo fdcTaskInfo = relateTask.getRelatedTask();
			if (fdcTaskInfo != null) {
				row.getCell("relateTaskId").setValue(fdcTaskInfo.getId());
			}
			row.getCell("finalEvaluationScore").setValue(entryInfo.getFinalEvaluationScore());
			row.getCell("finalCompleteDes").setValue(entryInfo.getFinalCompleteDes());
			row.getCell("selfCompleteDes").setValue(entryInfo.getSelfCompleteDes());
			selfScoreRate += FDCNumberHelper.multiply(entryInfo.getSelfEvaluationScore(), relateTask.getWeightRate()).doubleValue();
			finalScoreRate += FDCNumberHelper.multiply(entryInfo.getFinalEvaluationScore(), relateTask.getWeightRate()).doubleValue();
			copyProperties(map, relateTask, row);
		}
		// this.setWeightRateTotal();
		// �ϼ�����ֵ���Ҷ�����С����ʽ��ʾ
		kdtEntries.getFootRow(0).getCell("selfEvaluationScore").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		kdtEntries.getFootRow(0).getCell("finalEvaluationScore").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		kdtEntries.getFootRow(0).getCell("selfEvaluationScore").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kdtEntries.getFootRow(0).getCell("finalEvaluationScore").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kdtEntries.getFootRow(0).getCell("selfEvaluationScore").setValue(new Double(selfScoreRate / 100).toString());
		kdtEntries.getFootRow(0).getCell("finalEvaluationScore").setValue(new Double(finalScoreRate / 100).toString());
	}
	
	/**���ݽ������ڶ��ֶν�������,�ȸ��ݽ������ڣ��ٸ�����������
	 * @param entryCollection
	 * @return
	 */
	private Object[] orderDataByFinishDate(
			SelfAndFinalEvaluationEntryCollection entryCollection) {
		Object[] resultList = entryCollection.toArray();
		if(entryCollection != null && entryCollection.size() > 0){
			Arrays.sort(resultList, 0, entryCollection.size() , new Comparator<Object>(){
				public int compare(Object objectValue1,
						Object objectValue2) {
					SelfAndFinalEvaluationEntryInfo o1 = (SelfAndFinalEvaluationEntryInfo)objectValue1;
					SelfAndFinalEvaluationEntryInfo o2 = (SelfAndFinalEvaluationEntryInfo)objectValue2;
					if(o1.getRelateTask().getPlanFinishDate().after(o2.getRelateTask().getPlanFinishDate())){
						return 1;
					} else if(o1.getRelateTask().getPlanFinishDate().before(o2.getRelateTask().getPlanFinishDate())){
						return -1;
					} else {
						if(o1.getRelateTask().getSeq() > (o2.getRelateTask().getSeq())){
							return 1;
						} else if(o1.getRelateTask().getSeq() < (o2.getRelateTask().getSeq())){
							return -1;
						} else {
							return 0;
						}
						
					}
				}
				
			});
		}
		return resultList;
	}
	
	protected void setWeightRateTotal() {
		DecimalFormat d = new DecimalFormat("#0");

		int rowIndex = kdtEntries.getSelectManager().getActiveRowIndex();
		FDCTableHelper.apendFootRow(kdtEntries, new String[] { "weightRate" });
		if (null != kdtEntries.getFootRow(0).getCell("weightRate").getValue()) {
			if (Integer.parseInt(d.format(kdtEntries.getFootRow(0).getCell("weightRate").getValue())) > 100) {
				FDCMsgBox.showWarning(this, "Ȩ�غϼƲ��ܴ���100��");
				SysUtil.abort();
				//FDCTableHelper.apendFootRow(kdtEntries, new String[] { "weightRate" });
				// kdtEntries.getCell(rowIndex, "weightRate").setValue("0");
				
			} else {

			}
		}
		if (kdtEntries.getRowCount() < 1) {
			kdtEntries.getFootRow(0).getCell("weightRate").setValue("0");
		}
		if (kdtEntries.getFootRow(0).getCell("weightRate") != null) {
			kdtEntries.getFootRow(0).getCell("weightRate").getStyleAttributes().setNumberFormat("#,##0");
		}
	}
	
	protected void loadDataProgressData(SelfAndFinalEvaluationInfo editData) {
		SelfAndFinalEvaluationEntryCollection cols = editData.getEntries();
		int size = cols.size();
		Map<String, DeptTaskProgressReportInfo> processMap = new HashMap<String, DeptTaskProgressReportInfo>();
		Set<String> taskSet = new HashSet<String>();
		DeptTaskProgressReportInfo reportInfo, otherInfo = null;
		for (int i = 0; i < size; i++) {
			taskSet.add(cols.get(i).getRelateTask().getId().toString());
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("relateTask.id", taskSet, CompareType.INCLUDE));
		filter.appendFilterItem("progressEdition", Boolean.TRUE);	/* modified by zhaoqin for R140523-0226 on 2014/06/18 */
		view.setFilter(filter);
		try {
			DeptTaskProgressReportCollection reports = DeptTaskProgressReportFactory.getRemoteInstance()
					.getDeptTaskProgressReportCollection(view);
			size = reports.size();
			for (int i = 0; i < size; i++) {
				reportInfo = reports.get(i);
				String key = reportInfo.getRelateTask().getId().toString();
				if (processMap.containsKey(key)) {
					otherInfo = processMap.get(key);
					/* modified by zhaoqin for R140523-0226 on 2014/06/18 */
					if(FDCNumberHelper.compareValue(otherInfo.getCompleteAmount(), reportInfo.getCompleteAmount()) < 0) {
					//if (otherInfo.getCompleteAmount().compareTo(reportInfo.getCompleteAmount()) < 0) {
						otherInfo = reportInfo;
					}
				} else {
                     processMap.put(key, reportInfo);
				}
			}
		} catch (BOSException e) {
			handUIException(e);
		}
		size = kdtEntries.getRowCount();
		IRow processRow = null;
		BigDecimal completePercent = FDCHelper.ZERO;
		for (int i = 0; i < size; i++) {
			processRow = kdtEntries.getRow(i);
			String key = processRow.getCell("relateTask").getValue() + "";
			if (processMap.containsKey(key)) {
				reportInfo = processMap.get(key);
				if (reportInfo.getRealEndDate() != null) {
					processRow.getCell("actualEndDate").setValue(reportInfo.getRealEndDate());
				}
				if (reportInfo.getCompletePrecent() != null) {
					completePercent = reportInfo.getCompletePrecent();
					comMap.put(key, completePercent);
					processRow.getCell("completePercent").setValue(completePercent);
					if (FDCHelper.toBigDecimal(completePercent).compareTo(FDCHelper.ONE_HUNDRED) == 0) {
						processRow.getCell("isCompelete").setValue("��");
					} else {
						processRow.getCell("isCompelete").setValue("��");
					}
				}
			}
		}

	}
	/**
	 * @return
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("selfEvaluationDes");
		sic.add("finalEvaluationDes");
		sic.add("finalEvaluationDate");
		sic.add("selfEvaluationPerson");
		sic.add(new SelectorItemInfo("selfEvaluationPerson.*"));
		sic.add("entries.relateTask.id");
		sic.add("entries.relateTask.taskType");
		sic.add("entries.relateTask.taskName");
		sic.add("entries.relateTask.planFinishDate");
		sic.add("entries.relateTask.finishStandard");
		sic.add("entries.relateTask.weightRate");
		sic.add("entries.relateTask.adminPerson.name");
		sic.add("entries.relateTask.adminPerson.id");
		sic.add("entries.relateTask.relatedTask.id");
		sic.add("entries.actualEndDate");
		sic.add("entries.completePercent");
		sic.add("entries.selfEvaluationScore");
		sic.add("entries.selfCompleteDes");
		sic.add("entries.finalEvaluationScore");
		sic.add("entries.finalCompleteDes");
		sic.add("state");
		return sic;
	}

	/**
	 * @param adminDept
	 * @param scheduleMonth
	 */
	public void getTaskExecInfo(AdminOrgUnitInfo adminDept, String scheduleMonth) {
		super.getTaskExecInfo(adminDept, scheduleMonth);
	}

	/**
	 * ͨ�����������ˡ���ɽ���%���͡�ʵ��������ڡ�,��д�������¶ȼƻ���¼ - R140523-0226
	 * @author zhaoqin
	 * @date 2014/06/18
	 */
	private boolean isFWSaved = true;
	private void updateDeptMonthlyScheduleTaskEntry() {
		if(!isFromWf())
			return;
		if(isFWSaved)
			return;
		isFWSaved = true;

		SelfAndFinalEvaluationEntryCollection entrys = this.editData.getEntries();
		if(null == entrys || entrys.size() == 0)
			return;
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		for(int i = 0; i < entrys.size(); i++) {
			SelfAndFinalEvaluationEntryInfo info = entrys.get(i);
			if(null == info.getRelateTask() || null == info.getCompletePercent())
				continue;
			try {
				String taskId = info.getRelateTask().getId().toString();
				builder.appendSql("update T_SCH_DeptMonthlyScheduleTask set FComplete = ? where FID= ?");
				builder.addParam(info.getCompletePercent());
				builder.addParam(taskId);
				builder.executeUpdate();
				builder.clear();
				
				builder.appendSql("update T_SCH_DeptTaskProgressReport set FCompletePrecent = ?, ");
				builder.addParam(info.getCompletePercent());
				if(FDCNumberHelper.compareValue(info.getCompletePercent(), FDCNumberHelper.ONE_HUNDRED) == 0) {
					builder.appendSql("FRealEndDate = ?, ");
					builder.addParam(info.getActualEndDate());
				} else {
					builder.appendSql("FRealEndDate = null, ");
				}
				builder.appendSql("FIsComplete = 1 where FRelateTaskID = ? and FProgressEdition = 1");
				builder.addParam(taskId);
				builder.executeUpdate();
				builder.clear();
			} catch (BOSException e1) {
				e1.printStackTrace();
			}
		}
	}
}