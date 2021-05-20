/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
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
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportCollection;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationEntryCollection;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationEntryInfo;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationFactory;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationInfo;
import com.kingdee.eas.fdc.schedule.WeekReportEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SelfEvaluationEditUI extends AbstractSelfEvaluationEditUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -770457402169497444L;
	private static final Logger logger = CoreUIObject.getLogger(SelfEvaluationEditUI.class);
	private double selfScoreRate = 0;
	private Map comMap = new HashMap();
	public SelfEvaluationEditUI() throws Exception {
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
		editData.setSelfEvaluationDate((Date) this.pkSelfEvaluationDate.getValue());
		Map map = getDeptMonthlyScheduleTaskMap();
		selfScoreRate = 0;
		for (int i = 0; i < this.kdtEntries.getRowCount(); i++) {
			IRow row = this.kdtEntries.getRow(i);
			entryInfo = new SelfAndFinalEvaluationEntryInfo();
			if (row.getCell("selfEvaluationScore").getValue() != null) {
				BigDecimal weight = new BigDecimal(row.getCell("weightRate").getValue() == null ? "0" : row.getCell("weightRate")
						.getValue().toString());
				BigDecimal bigSelfScore = new BigDecimal(row.getCell("selfEvaluationScore").getValue().toString());
				BigDecimal count = new BigDecimal(bigSelfScore.multiply(weight.divide(new BigDecimal("100"))).toString());
				selfScoreRate = selfScoreRate + count.doubleValue();
				entryInfo.setSelfEvaluationScore(new BigDecimal(row.getCell("selfEvaluationScore").getValue().toString()));
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
				entryInfo.setFinalEvaluationScore(BigDecimal.valueOf(Double.parseDouble(row.getCell("finalEvaluationScore").getValue().toString())));
			}
			if (row.getCell("finalCompleteDes").getValue() != null) {
				entryInfo.setFinalCompleteDes(row.getCell("finalCompleteDes").getValue().toString());
			}
			if (row.getCell("relateTask").getValue() != null) {
				entryInfo.setRelateTask((DeptMonthlyScheduleTaskInfo) map.get(row.getCell("relateTask").getValue().toString()));
			}
			editData.setSelfEvaluationScore(new BigDecimal(selfScoreRate, new MathContext(3)));
			this.editData.getEntries().add(entryInfo);
		}
	}
	/**
	 * ��ȡ�ƻ��������ݷ�װ��Map
	 * 
	 * @return
	 */
	public Map getDeptMonthlyScheduleTaskMap() {
		Map map = new HashMap();
		try {
			DeptMonthlyScheduleTaskCollection cols = DeptMonthlyScheduleTaskFactory.getRemoteInstance().getDeptMonthlyScheduleTaskCollection();
			if (cols.size() != 0) {
				for (int k = 0; k < cols.size(); k++) {
					DeptMonthlyScheduleTaskInfo info = cols.get(k);
					map.put(info.getId().toString(), info);
				}
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		return map;
	}
	
	/**
	 * @throws Exception
	 */
	boolean state = false;
	public void onLoad() throws Exception {
		super.onLoad();
		this.windowTitle = "����";
		
		initTable();
		/**
		 * Ϊ����Ӽ���
		 */
		
		 this.kdtEntries.addKDTEditListener(new KDTEditListener() {
			public void editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent arg0) {
				String obj = getUITitle();
				IRow rowIndex = KDTableUtil.getSelectedRow(kdtEntries);
				Object selfScoreStr = rowIndex.getCell("selfEvaluationScore").getValue();
				Object finalScore = rowIndex.getCell("finalEvaluationScore").getValue();
				if (finalScore != null && obj.equals("����")) {
					rowIndex.getCell("selfEvaluationScore").setValue(selfScoreStr);
					FDCMsgBox.showWarning("���������������޸�����!");
					SysUtil.abort();
				}
			}

			public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
				e.getValue();
				checkInputDate();
				setEvaluationScore();
			}

			public void editCanceled(KDTEditEvent kdteditevent) {
			}

			public void editStarting(KDTEditEvent kdteditevent) {
			}

			public void editStopping(KDTEditEvent kdteditevent) {
			}

			public void editValueChanged(KDTEditEvent kdteditevent) {
			}
		});
		 
		 
		 
	}

	private void initTable() {
		KDDatePicker actualEndDate = new KDDatePicker();
		KDTDefaultCellEditor endTimeFieldEditor = new KDTDefaultCellEditor(actualEndDate);
		this.kdtEntries.getColumn("actualEndDate").setEditor(endTimeFieldEditor);
		this.kdtEntries.getColumn("isCompelete").setEditor(getKDComboBoxEditor(WeekReportEnum.getEnumList()));
		KDFormattedTextField numText = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		numText.setNegatived(false);
		numText.setPrecision(0);
		numText.setSupportedEmpty(true);

		KDTDefaultCellEditor needRequestEditor = new KDTDefaultCellEditor(numText);
		kdtEntries.getColumn("completePercent").setEditor(needRequestEditor);

		KDTextField scoreText = new KDTextField();
		KDTDefaultCellEditor requestEditor = new KDTDefaultCellEditor(scoreText);
		kdtEntries.getColumn("selfEvaluationScore").setEditor(requestEditor);

		KDTextField finishStandard = new KDTextField();
		finishStandard.setMaxLength(200);
		KDTDefaultCellEditor finishStandardEditor = new KDTDefaultCellEditor(finishStandard);
		kdtEntries.getColumn("selfCompleteDes").setEditor(finishStandardEditor);
	}
	public void setEvaluationScore() {
		FDCTableHelper.apendFootRow(kdtEntries, new String[] { "weightRate" });
		if (kdtEntries.getFootRow(0).getCell("weightRate") != null) {
			kdtEntries.getFootRow(0).getCell("weightRate").getStyleAttributes().setNumberFormat("###.00");
		}
		selfScoreRate = 0;
		for (int i = 0; i < this.kdtEntries.getRowCount(); i++) {
			IRow row = this.kdtEntries.getRow(i);
			if (row.getCell("selfEvaluationScore").getValue() != null) {
				String value = row.getCell("weightRate").getValue() == null ? "0" : row.getCell("weightRate").getValue().toString();
				BigDecimal weight = new BigDecimal(value);
				BigDecimal bigSelfScore = new BigDecimal(row.getCell("selfEvaluationScore").getValue().toString());
				BigDecimal count = new BigDecimal(bigSelfScore.multiply(weight.divide(new BigDecimal("100"))).toString());
				selfScoreRate = selfScoreRate + count.doubleValue();
			}
		}
		kdtEntries.getFootRow(0).getCell("selfEvaluationScore").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		kdtEntries.getFootRow(0).getCell("selfEvaluationScore").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kdtEntries.getFootRow(0).getCell("selfEvaluationScore").setValue(new Double(selfScoreRate));
	}
	/**
	 * ������ ��֤����
	 * @author ��άǿ
	 * @createDate 2011-11-2
	 */
	public void checkInputDate() {
		IRow rowIndex = KDTableUtil.getSelectedRow(kdtEntries);
		int completePercent = -1;
		if (rowIndex.getCell("completePercent").getValue() != null && rowIndex.getCell("completePercent").getValue() instanceof Integer) {
			completePercent = new Integer(rowIndex.getCell("completePercent").getValue().toString()).intValue();
			if (completePercent == 100) {
				rowIndex.getCell("isCompelete").setValue("��");
			} else {
				rowIndex.getCell("isCompelete").setValue("��");
			}
		}
		if (rowIndex.getCell("completePercent").getValue() != null && rowIndex.getCell("completePercent").getValue() instanceof Integer
				&& completePercent < 0 || completePercent > 100) {
			FDCMsgBox.showWarning("������0��100֮ǰ������!");
			rowIndex.getCell("completePercent").setValue(null);
			return;
		}
		String regex = "^[0-9]+(.[0-9]*)?$";
		String regex2 = "^[0-9]+(.[0-9]{2,})?$";
		Object selfScoreStr = rowIndex.getCell("selfEvaluationScore").getValue();
		if (selfScoreStr != null && !selfScoreStr.toString().matches(regex)) {
			rowIndex.getCell("selfEvaluationScore").setValue(null);
			return;
		} else {
			if (selfScoreStr != null) {
				Double d = new Double(selfScoreStr.toString());
				if (d.doubleValue() < 0 || d.doubleValue() > 10) {
					FDCMsgBox.showWarning("�����÷ֽ���0~10֮��!");
					rowIndex.getCell("selfEvaluationScore").setValue(null);
					return;
				}
				String self = selfScoreStr.toString();
				if (self.matches(regex2) && self.length() > 4) {
					self = self.substring(0, 4);
					rowIndex.getCell("selfEvaluationScore").setValue(self);
				}
			}
		}
		
		
	}
	/**
	 * @throws Exception
	 */
	public void onShow() throws Exception {
		super.onShow();
		btnAudit.setVisible(false);
		btnUnAudit.setVisible(false);
		initDetailTable();
		initdata();
		
	}
	
	/* modified by zhaoqin for R140523-0226 on 2014/06/18 */
	//private boolean isFromWf() {
	protected boolean isFromWf() {
		if (getUIContext().get("isFromWorkflow") != null && Boolean.valueOf(getUIContext().get("isFromWorkflow").toString()).booleanValue()) {
			return true;
		} else {
			return false;
		}
	}

	private void loadDataFromWf() {
		AdminOrgUnitInfo adminDept = null;
		adminDept = editData.getAdminDept();
		prmtAdminDept.setValue(adminDept);
		pkScheduleMonth.setValue(editData.getScheduleMonth());
		this.pkScheduleMonth.setDatePattern("yyyy-MM");
		if (editData != null && null != editData.getSelfEvaluationPerson()) {
			prmtSelfEvaluationPerson.setValue(editData.getSelfEvaluationPerson());
		}
		this.pkSelfEvaluationDate.setValue(editData.getSelfEvaluationDate());
		getTaskExecInfo();
	}

	/**
	 * ��ʼ������
	 */
	private void initdata() {
		this.btnAttachment.setVisible(false);
		this.btnAuditResult.setVisible(false);
		if (isFromWf()) {
			loadDataFromWf();
			loadDataProgressData(editData);
			if (editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
				this.btnSubmit.setVisible(true);
				this.btnSubmit.setEnabled(true);
			}
			return;
		}
		AdminOrgUnitInfo adminDept = null;
		if (getUIContext().get("adminDept") != null)
			adminDept = (AdminOrgUnitInfo) getUIContext().get("adminDept");
		else
			adminDept = editData.getAdminDept();
		this.prmtAdminDept.setValue(adminDept.toString());
		if (getUIContext().get("scheudleMonth") != null)
			this.pkScheduleMonth.setValue(getUIContext().get("scheudleMonth"));
		else
			this.pkScheduleMonth.setValue(editData.getScheduleMonth());
		this.pkScheduleMonth.setDatePattern("yyyy-MM");
		if (editData != null && null != editData.getSelfEvaluationPerson()) {
			prmtSelfEvaluationPerson.setValue(editData.getSelfEvaluationPerson());
		} else {
			prmtSelfEvaluationPerson.setValue((SysContext.getSysContext().getCurrentUserInfo()));
		}

		this.pkSelfEvaluationDate.setValue(new Date());
		String scheduleMonth = pkScheduleMonth.getText();
		if (getOprtState().equals(OprtState.ADDNEW)) {
			getTaskExecInfo(adminDept, scheduleMonth);
		} else {
			
			getTaskExecInfo();
		}
		// if (getOprtState().equals(OprtState.EDIT)) {
		// }
		// if (getOprtState().equals(OprtState.VIEW)) {
		// getTaskExecInfo();
		// }
		FDCTableHelper.apendFootRow(kdtEntries, new String[] { "weightRate" });
		kdtEntries.getFootRow(0).getCell("weightRate").getStyleAttributes().setNumberFormat("#,##0");
		/**
		 * modify by libing
		 */
		//this.kdtEntries.getColumn("relateTask").getStyleAttributes().setHided(
		// false);
		// this.kdtEntries.getColumn("relateTask").setWidth(1);
		// this.kdtEntries.getColumn("project").getStyleAttributes().setHided(
		// false);
		// this.kdtEntries.getColumn("project").setWidth(1);
		// this.kdtEntries.getColumn("taskType").getStyleAttributes().setHided(
		// false);
		// this.kdtEntries.getColumn("taskType").setWidth(1);
		// this.kdtEntries.getColumn("comboTaskOrigin").getStyleAttributes().
		// setHided(false);
		// this.kdtEntries.getColumn("comboTaskOrigin").setWidth(1);
	}

	protected void loadDataProgressData(SelfAndFinalEvaluationInfo editData) {

	}


	/**
	 * ��ʼ����¼��,Ϊ�����ӱ༭��
	 */
	public void initDetailTable() {
		this.kdtEntries.getColumn("weightRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntries.getColumn("selfEvaluationScore").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntries.getColumn("finalEvaluationScore").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntries.getColumn("taskName").getStyleAttributes().setLocked(true);
		this.kdtEntries.getColumn("adminPerson").getStyleAttributes().setLocked(true);
		this.kdtEntries.getColumn("finishStandard").getStyleAttributes().setLocked(true);
		this.kdtEntries.getColumn("planFinishDate").getStyleAttributes().setLocked(true);
		this.kdtEntries.getColumn("weightRate").getStyleAttributes().setLocked(true);
		this.kdtEntries.getColumn("isCompelete").getStyleAttributes().setLocked(true);
		this.kdtEntries.getColumn("isCompelete").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		
		
		
		
		
	}
	
	/**
	 * �󶨵Ŀؼ�ΪKDComboBox��cellEditor
	 * 
	 * @param enumList
	 *            ö�ٵ�list ���磺CertifacateNameEnum.getEnumList()
	 * @return
	 */
	protected ICellEditor getKDComboBoxEditor(List enumList) {
		KDComboBox comboField = new KDComboBox();
		if (enumList != null)
			for (int i = 0; i < enumList.size(); i++) {
				comboField.addItem(enumList.get(i));
			}
		ICellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		return comboEditor;
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
		// ��������ɵ�
		btnAttachment.setEnabled(false);
		btnAttachment.setVisible(false);
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
		btnPrint.setVisible(false);
		btnPrintPreview.setVisible(false);
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
		btnEdit.setVisible(false);
		btnSave.setVisible(true);
		btnAudit.setVisible(false);
		btnUnAudit.setVisible(false);
	}

	public void loadFields() {
		super.loadFields();
		AdminOrgUnitInfo adminDept = (AdminOrgUnitInfo) getUIContext().get("adminDept");
		this.prmtAdminDept.setValue(adminDept);
		Date scheudleMonth = (Date) getUIContext().get("scheudleMonth");
		this.pkScheduleMonth.setValue(scheudleMonth);
		this.pkSelfEvaluationDate.setValue(new Date());

	}

	/**
	 * ��ʼ��������¼����
	 * 
	 * @param adminDept
	 *            ���β���
	 * @param scheduleMonth
	 *            �ƻ��·�
	 */
	public void getTaskExecInfo(AdminOrgUnitInfo adminDept, String scheduleMonth) {
		Map map = getScheduleTaskData();
		try {
			DeptMonthlyScheduleTaskCollection taskCollection = getDeptMonthlyScheduleTasks(adminDept, scheduleMonth);
			DeptMonthlyScheduleTaskInfo taskInfo = null;
			if (taskCollection.size() != 0) {
				kdtEntries.checkParsed();
				for (int i = 0; i < taskCollection.size(); i++) {
					taskInfo = taskCollection.get(i);
					IRow row = kdtEntries.addRow();
					copyProperties(map, taskInfo, row);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		}

	private void copyProperties(Map map, DeptMonthlyScheduleTaskInfo taskInfo, IRow row) {
		if (map.containsKey(taskInfo.getId().toString())) {
			IRow processRow = (IRow) map.get(taskInfo.getId().toString());
			row.getCell("taskName").setValue(processRow.getCell("taskName").getValue());
			row.getCell("adminPerson").setValue(processRow.getCell("adminPerson").getValue());
			row.getCell("finishStandard").setValue(processRow.getCell("finishStandard").getValue());
			row.getCell("planFinishDate").setValue(processRow.getCell("planEndDate").getValue());
			row.getCell("weightRate").setValue(processRow.getCell("weightRate").getValue());
			row.getCell("relateTask").setValue(processRow.getCell("id").getValue());
			
			row.getCell("project").setValue(processRow.getCell("project").getValue());
			row.getCell("taskType").setValue(processRow.getCell("taskType").getValue());
			row.getCell("comboTaskOrigin").setValue(processRow.getCell("comboTaskOrigin").getValue());
			FDCScheduleTaskInfo fdcTaskInfo = taskInfo.getRelatedTask();
			if (fdcTaskInfo != null) {
				row.getCell("relateTaskId").setValue(fdcTaskInfo.getId());
			}
			BigDecimal comPercent = null;
			if (processRow.getCell("completePrecent").getValue() != null) {
				comPercent = FDCHelper.toBigDecimal(processRow.getCell("completePrecent") == null ? FDCHelper.ZERO : processRow.getCell(
						"completePrecent").getValue().toString());
				comMap.put(taskInfo.getId().toString(), comPercent);
				row.getCell("completePercent").setValue(comPercent);
			}
			if (comPercent != null && FDCHelper.toBigDecimal(comPercent).compareTo(FDCHelper.ONE_HUNDRED) == 0) {
				row.getCell("isCompelete").setValue("��");
			} else {
				row.getCell("isCompelete").setValue("��");
			}
			row.getCell("actualEndDate").setValue(processRow.getCell("realEndDate").getValue());
		}
	}

	private Map getScheduleTaskData() {
		KDTable tableMain = (KDTable) getUIContext().get("tableMain");
		Map map = new HashMap();
		for (int i = 0; i < tableMain.getRowCount(); i++) {
			IRow row = tableMain.getRow(i);
			map.put(row.getCell("id").getValue(), row);
		}
		return map;
	}

	/**
	 * 
	 * @description ��̬���úϼ�ֵ
	 * @author ���
	 * @createDate 2011-8-18 void
	 * @version EAS7.0
	 * @see
	 */
	protected void setWeightRateTotal(String selfScoreStr) {
		FDCTableHelper.apendFootRow(kdtEntries, new String[] { "weightRate" });
		if (kdtEntries.getFootRow(0).getCell("weightRate") != null) {
			kdtEntries.getFootRow(0).getCell("weightRate").getStyleAttributes().setNumberFormat("###.00");
		}
		kdtEntries.getFootRow(0).getCell("selfEvaluationScore").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		kdtEntries.getFootRow(0).getCell("selfEvaluationScore").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kdtEntries.getFootRow(0).getCell("selfEvaluationScore").setValue(selfScoreStr);
	}
	

	/**
	 * 
	 */
	public void getTaskExecInfo() {
		this.pkSelfEvaluationDate.setValue(editData.getSelfEvaluationDate());
		SelfAndFinalEvaluationEntryCollection entryCollection = editData.getEntries();
		
		//modify by lihaiou,2014-08-01,����������Ҫ���б������һ�£�BOS�ײ��ڲ����ݵ�ʱ�򣬲�ѯ�ӱ��ʱ��ᶪ��������ֶ�
		Object[] resultList = orderDataByFinishDate(entryCollection);
		
		SelfAndFinalEvaluationEntryInfo entryInfo = null;
		Map map = getScheduleTaskData();
		if (entryCollection.size() != 0) {
			kdtEntries.checkParsed();
			for (int i = 0; i < resultList.length; i++) {
				entryInfo = (SelfAndFinalEvaluationEntryInfo)resultList[i];
				// IRow row = kdtEntries.getRow(i);
				IRow row = kdtEntries.addRow();
				DeptMonthlyScheduleTaskInfo relateTask = entryInfo.getRelateTask();
				copyProperties(map, relateTask, row);
				row.getCell("selfEvaluationScore").setValue(entryInfo.getSelfEvaluationScore());
				row.getCell("selfCompleteDes").setValue(entryInfo.getSelfCompleteDes());
				row.getCell("finalEvaluationScore").setValue(entryInfo.getFinalEvaluationScore());
				row.getCell("finalCompleteDes").setValue(entryInfo.getFinalCompleteDes());
			}
			this.txtSelfEvaluationScore.setValue(editData.getSelfEvaluationScore());
			// BigDecimal selfScore = new BigDecimal(0);
			String selfScoreStr = "";
			if (!txtSelfEvaluationScore.getText().equals("")) {
				selfScoreStr = new BigDecimal(txtSelfEvaluationScore.getText(), new MathContext(3)).toString();
			}
			setWeightRateTotal(selfScoreStr);
			
			
		}
		
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
	/**
	 * 
	 * @(#)		    SelfEvaluationEditUI.java
	 * ��Ȩ��	    �����������������޹�˾��Ȩ����
	 * ������	
	 * @author      ��άǿ
	 * @createDate  2011-10-25
	 */
	public DeptTaskProgressReportInfo getDeptTaskProgressReportMap(BOSUuid uuid) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("relateTask.id", uuid, CompareType.EQUALS));
		view.setFilter(filter);
		DeptTaskProgressReportInfo info = null;
		try {
			DeptTaskProgressReportCollection reportCollection = DeptTaskProgressReportFactory.getRemoteInstance()
					.getDeptTaskProgressReportCollection(view);
			if (reportCollection.size() != 0) {
				info = reportCollection.get(0);
			}

		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		return info;
	}
	/**
	 * @return
	 */
	
	  public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
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
		sic.add("entries.selfEvaluationScore");
		sic.add("entries.selfCompleteDes");
		
		sic.add("entries.completePercent");
		sic.add("entries.finalEvaluationScore");
		sic.add("entries.finalCompleteDes");
		
		return sic;
	}
	 
	
	protected IObjectValue createNewData() {
		SelfAndFinalEvaluationInfo info = new SelfAndFinalEvaluationInfo();
		AdminOrgUnitInfo adminDept = (AdminOrgUnitInfo) getUIContext().get("adminDept");
		info.setAdminDept(adminDept);
		Date scheudleMonth = (Date) getUIContext().get("scheudleMonth");
		// ���롢���Ƹ����ֵ���Ᵽ����֤
		String string = BOSUuid.create(info.getBOSType()).toString();
		info.setNumber(string);
		info.setName(string);
		info.setScheduleMonth(scheudleMonth);
		return info;

	}

	/**
	 * 
	 */
	protected void showSaveSuccess() {
		// super.showSaveSuccess();
		FDCMsgBox.showInfo("��������ɹ���");
	}

	/**
	 * @param e
	 * @throws Exception
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			Object taskId = kdtEntries.getRow(i).getCell("relateTask").getValue();
			DeptTaskProgressReportCollection reportCol = getReportByRelateTask(taskId);
			Object completePercent = kdtEntries.getRow(i).getCell("completePercent").getValue();
			Object actualEndDate = kdtEntries.getRow(i).getCell("actualEndDate").getValue();
			Object endDate = kdtEntries.getRow(i).getCell("actualEndDate").getFormattedValue();
			Object selfCompleteDes = kdtEntries.getRow(i).getCell("selfCompleteDes").getValue();
			Object planEndDate = kdtEntries.getRow(i).getCell("planFinishDate").getValue();
																								
			Object taskName = kdtEntries.getRow(i).getCell("taskName").getValue();
			Object selfScore = kdtEntries.getRow(i).getCell("selfEvaluationScore").getValue();
			Object relateTaskId = kdtEntries.getRow(i).getCell("relateTaskId").getValue();
			if (selfScore != null) {
				if (completePercent == null) {
					FDCMsgBox.showWarning("��" + (i + 1) + "����������������ɽ��Ȳ���Ϊ�գ�");
					SysUtil.abort();
				}
			}
			
			if (completePercent != null) {
				int precent = new BigDecimal(completePercent.toString()).intValue();
				
				if (comMap.size() != 0) {
					String comValue = "";
					if (comMap.get(taskId.toString()) != null) {
						comValue = comMap.get(taskId.toString()).toString();
					}
					if (!comValue.equals("")) {
						int percentOld = new BigDecimal(comValue).intValue();
						if (precent < percentOld) {
							FDCMsgBox.showInfo(this, "��" + (i + 1) + "��ǰ���񹤳���Ŀǰ�깤" + percentOld + "%,���ν��Ȳ��ܵ����ϴλ㱨����");
							SysUtil.abort();
						}
					}
				}
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql("select FCompletePrecent as complete from T_SCH_DeptTaskProgressReport where FProgressEdition='1' and FRelateTaskID='" + taskId + "'");
				IRowSet rowSet = builder.executeQuery();
				if (rowSet.size() > 0) {
					int complete = 0;
					while (rowSet.next()) {
						complete = new BigDecimal(rowSet.getString("complete").toString()).intValue();
						if (precent < complete) {
							FDCMsgBox.showInfo(this, "��" + (i + 1) + "��ǰ���񹤳���Ŀǰ�깤" + complete + "%,���ν��Ȳ��ܵ����ϴλ㱨����");
							SysUtil.abort();
						}
					}
				}
				if (precent == 100 && actualEndDate == null) {
					FDCMsgBox.showWarning("��" + (i + 1) + "���������,ʵ��������ڲ���Ϊ�գ�");
					SysUtil.abort();
				}
				if (precent != 100 && actualEndDate != null) {
					FDCMsgBox.showWarning("��" + (i + 1) + "��������ʵ���������,��ɽ��ȱ���Ϊ100%��");
					SysUtil.abort();
				}
			}
			if (completePercent == null && actualEndDate != null) {
				FDCMsgBox.showWarning("��" + (i + 1) + "��        ������ʵ���������,��ɽ��ȱ���Ϊ100%��");
				SysUtil.abort();
			}
			if (reportCol != null && reportCol.size() > 0) {
				updateReportTask(reportCol, completePercent, actualEndDate, selfCompleteDes, planEndDate, relateTaskId, endDate);
			} else {
				addNewReport(taskId, completePercent, actualEndDate, selfCompleteDes, planEndDate, taskName, relateTaskId, endDate);
			}
		}
		super.actionSave_actionPerformed(e);
		setOprtState(OprtState.VIEW);
		this.btnAudit.setVisible(false);
		this.btnSave.setEnabled(true);
	}
	

	/**
	 * 
	 */
	public void validate() {
		super.validate();
	}

	/**
	 * @throws Exception
	 */
	public void verifyData() throws Exception {
		super.verifyData();
	}
	/**
	 * @describes ���ݷ�д
	 * @author ��άǿ
	 * @createDate 2011-11-17
	 */
	private void setFDCScheduleTask(Object scheduletTaskId, Object completePercent, Object actualEndDate) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("update T_SCH_FDCScheduleTask set FComplete = '" + completePercent + "' where FID='" + scheduletTaskId.toString() + "'");
		builder.executeUpdate();
		builder.clear();
		
		if (actualEndDate != null) {
			builder.appendSql("update T_SCH_FDCScheduleTask set FactualEndDate = {ts'" + actualEndDate.toString() + "'} where FID='"
					+ scheduletTaskId.toString() + "'");
			builder.executeUpdate();
			builder.clear();
		}
		// ����ɽ��Ȳ�100%ʱ����дʵ���깤ʱ��Ϊ��
		FDCScheduleTaskInfo value = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(new ObjectStringPK(scheduletTaskId.toString()));
		Date endDate = null;
		if (value != null) {
			endDate = value.getActualEndDate();
		}
		if (!completePercent.toString().equals("100")) {
			builder.appendSql("update T_SCH_FDCScheduleTask set FactualEndDate = '' where FID='" + scheduletTaskId.toString() + "'");
			builder.executeUpdate();
		}
	}
	private void updateReportTask(DeptTaskProgressReportCollection reportCol, Object completePercent, Object actualEndDate, Object selfCompleteDes,
			Object planEndDate, Object relateTaskId, Object endDate)
			throws BOSException,
			EASBizException {
		DeptTaskProgressReportInfo report = reportCol.get(0);
		if (completePercent != null) {
			report.setCompletePrecent(new BigDecimal(completePercent.toString()));
			// ��д�ƻ�������
			FDCSQLBuilder builderTask = new FDCSQLBuilder();
			String taskId = report.getRelateTask().getId().toString();
			String complete = completePercent.toString();
			builderTask.appendSql("update T_SCH_DeptMonthlyScheduleTask set FComplete = '" + complete + "' where FID='" + taskId + "'");
			builderTask.executeUpdate();
		}
		if (completePercent != null) {
			if (completePercent.equals("100")) {
				report.setIsComplete(true);
			}
			report.setRealEndDate((Date) actualEndDate);
			report.setPlanEndDate((Date) planEndDate);
		}
		DeptTaskProgressReportFactory.getRemoteInstance().update(new ObjectStringPK(report.getId().toString()), report);
		/*if (relateTaskId != null) {
			setFDCScheduleTask(relateTaskId, completePercent, endDate);
		}*/
		
		
	}

	private void addNewReport(Object taskId, Object completePercent, Object actualEndDate, Object selfCompleteDes, Object planEndDate,
			Object taskName, Object relateTaskId, Object endDate)
			throws BOSException,
			EASBizException {
		DeptTaskProgressReportInfo newReport = new DeptTaskProgressReportInfo();
		DeptMonthlyScheduleTaskInfo relateTask = DeptMonthlyScheduleTaskFactory.getRemoteInstance().getDeptMonthlyScheduleTaskInfo(
				new ObjectStringPK(taskId.toString()));
		newReport.setRelateTask(relateTask);
		if (completePercent != null && completePercent.toString().equals("100")) {
			newReport.setIsComplete(true);
			newReport.setRealEndDate((Date) actualEndDate);
			newReport.setPlanEndDate((Date) planEndDate);
			newReport.setTaskName(taskName.toString());
		} else {
			newReport.setIsComplete(false);
		}
		if (selfCompleteDes != null) {
			newReport.setDescription(selfCompleteDes.toString());
		}
		if (completePercent != null) {
			newReport.setCompletePrecent(new BigDecimal(completePercent.toString()));
			newReport.setProgressEdition(true);
			newReport.setId(BOSUuid.create(newReport.getBOSType()));
			newReport.setReportor(relateTask.getAdminPerson());
			newReport.setReportDate(new Date());
			DeptTaskProgressReportFactory.getRemoteInstance().addnew(newReport);
			
			// ��д�ƻ�������
			FDCSQLBuilder builderTask = new FDCSQLBuilder();
			// String taskId = report.getRelateTask().getId().toString();
			String complete = completePercent.toString();
			builderTask.appendSql("update T_SCH_DeptMonthlyScheduleTask set FComplete = '" + complete + "' where FID='" + taskId + "'");
			builderTask.executeUpdate();
			/*if (relateTaskId != null) {
				setFDCScheduleTask(relateTaskId, completePercent, endDate);
			}*/
		}
	}

	private DeptTaskProgressReportCollection getReportByRelateTask(Object relateTask) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("relateTask", relateTask.toString());
		filter.appendFilterItem("progressEdition", Boolean.TRUE);
		view.setFilter(filter);
		DeptTaskProgressReportCollection reportCol = DeptTaskProgressReportFactory.getRemoteInstance().getDeptTaskProgressReportCollection(view);
		return reportCol;
	}

	/**
	 * �������β��źͼƻ��·�����ȡ�����¶ȼƻ�����
	 * 
	 * @param adminDept
	 *            ���β���
	 * @param scheduleMonth
	 *            �ƻ��·�
	 * @return
	 * @throws BOSException
	 */
	private DeptMonthlyScheduleTaskCollection getDeptMonthlyScheduleTasks(AdminOrgUnitInfo adminDept, String scheduleMonth) throws BOSException {
		// ��ȡ�����¶ȼƻ�������
		String yearStr = scheduleMonth.substring(0, scheduleMonth.indexOf("-"));
		String monthStr = scheduleMonth.substring(scheduleMonth.indexOf("-") + 1);

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		// view.setSelector(sic);
		filter.getFilterItems().add(new FilterItemInfo("schedule.adminDept.id", adminDept.getId(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("schedule.year", yearStr, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("schedule.month", monthStr, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("schedule.state", FDCBillStateEnum.AUDITTED.getValue(), CompareType.EQUALS));
		view.setFilter(filter);
		DeptMonthlyScheduleTaskCollection taskCollection = DeptMonthlyScheduleTaskFactory.getRemoteInstance().getDeptMonthlyScheduleTaskCollection(
				view);
		return taskCollection;
	}
	public boolean destroyWindow() {
		Object obj = null;
		obj = getUIContext().get("Owner");
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
}