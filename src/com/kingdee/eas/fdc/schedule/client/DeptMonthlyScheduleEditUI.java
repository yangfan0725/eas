/**
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleCollection;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleInfo;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.OpReportBaseHelper;
import com.kingdee.eas.fdc.schedule.RESchTaskOriginEnum;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.ResponseConstant;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.AdvMsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * �����¶ȼƻ� �༭���� update by libing at 2011-11-3 and 2011-11-4p
 */
public class DeptMonthlyScheduleEditUI extends AbstractDeptMonthlyScheduleEditUI {
	private static final Logger logger = CoreUIObject.getLogger(DeptMonthlyScheduleEditUI.class);
	
	public DeptMonthlyScheduleEditUI() throws Exception {
		super();
	}

	protected boolean isModifySave() {
		return false;
	}

	public boolean isModify() {
		return false;
	}

	private void initButtonStatus() {
		this.btnEdit.setText("����");
		this.btnEdit.setToolTipText("����");
		this.menuItemEdit.setToolTipText("����");
		this.btnView.setIcon(EASResource.getIcon("imgTbtn_view"));
		this.btnView.setEnabled(true);
		this.btnSave.setVisible(false);
		this.btnEdit.setEnabled(true);
		this.btnSubmit.setEnabled(true);
		this.btnPrint.setVisible(true);
		this.btnPrintPreview.setVisible(true);
		this.btnPrint.setEnabled(true);
		this.btnPrintPreview.setEnabled(true);
		this.menuItemPrint.setVisible(true);
		this.menuItemPrintPreview.setVisible(true);
		this.menuItemPrint.setEnabled(true);
		this.menuItemPrintPreview.setEnabled(true);
		this.menuFile.setVisible(true);
		this.btnAudit.setEnabled(true);
		this.btnAudit.setVisible(false);
		this.btnUnAudit.setEnabled(true);
		this.btnUnAudit.setVisible(true);
		this.kdtTransmitExcel.setEnabled(true);
		this.kdtTransmitExcel.setIcon(EASResource.getIcon("imgTbtn_output"));
	}

	/**
	 * ��ʼ����¼QUERY
	 * 
	 * @description
	 * @author ���
	 * @createDate 2011-8-19 void
	 * @version EAS7.0
	 * @see
	 */
	private void initF7() {
		FDCClientUtils.setRespDeptF7(prmtAdminDept, this);
		if (editData.getAdminDept() == null)
			prmtAdminDept.setValue(SysContext.getSysContext().getCurrentAdminUnit());
		prmtAdminDept.setEditable(false);
	}

	/**
	 * 
	 * @description Ч�����ں�Ȩ������
	 * @author ���
	 * @createDate 2011-9-8 void
	 * @version EAS7.0
	 * @see
	 */
	private void checkDateAndWeightRate() {
		IRow rowIndex = KDTableUtil.getSelectedRow(kdtTasks);
		if (null != rowIndex) {
			if (rowIndex.getCell("planStartDate").getValue() != null && rowIndex.getCell("planFinishDate").getValue() != null) {
				int temp = FDCDateHelper.getDiffDays((Date) rowIndex.getCell("planStartDate").getValue(), (Date) rowIndex.getCell("planFinishDate").getValue());
				if (temp <= 0) {
					FDCMsgBox.showInfo("�ƻ�������ڲ������ڼƻ���ʼ���ڣ�");
					SysUtil.abort();
					setLockCanEdit();
				}
			}
			int newtemp = 0;
			if (rowIndex.getCell("weightRate").getValue() != null) {
				newtemp = new BigDecimal(rowIndex.getCell("weightRate").getValue().toString()).intValue();
				// ���û�����Ȩ��>100ʱ��ˢ������Ȩ�غϼ�ֵ
				if (newtemp > 100) {
					rowIndex.getCell("weightRate").setValue("0");
					setWeightRateTotal();
				}
			}

		}
	}

	/**
	 * @description
	 * @author ���kindee������д�������������
	 * @createDate 2011-9-13
	 * @version EAS7.0
	 * @see
	 */

	protected void fetchInitData() throws Exception {
	}

	public void onLoad() throws Exception {
		
		if (SysContext.getSysContext().getCurrentAdminUnit() == null) {
			FDCMsgBox.showWarning(this, "��֧�ַ�������֯�༭������������֯��¼�༭�����¶ȼƻ���");
			/**
			 * ����ܶ������ʹ�õ�ǰ��¼��֯Ĭ����������֯����ҵ���������ڴ˴����������ᱨNP
			 * Added by owen_wen 2013-11-27
			 */
			abort();
		}
		
		super.onLoad();
		this.kdtTasks.getStyleAttributes().setLocked(true);
		pkScheduleMonth.setEditable(false);
		// pkScheduleMonth.setEnabled(true);
		// prmtAdminDept.setEnabled(true);
		if (editData.getScheduleMonth() == null)
			pkScheduleMonth.setValue(new Date());
		// ��ʼ��F7query
		initF7();
		// ��ʼ����ť״̬
		initButtonStatus();
		// ��ʼ����¼��ť
		initEntryButton();
		// ��ʼ����¼��
		initDetailTable();
		pkScheduleMonth.setDatePattern("yyyy-MM");
		setWeightRateTotal();
		calcProjectPeriod();
		if (comboState.getSelectedItem() != null) {
			if (comboState.getSelectedItem().equals(FDCBillStateEnum.SUBMITTED)) {
				this.btnAudit.setVisible(true);
				this.btnAudit.setEnabled(true);

			}
		}
		/**
		 * Ϊ����Ӽ���
		 */
		this.kdtTasks.addKDTEditListener(new KDTEditListener() {
			public void editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent arg0) {
				setWeightRateTotal();
			}

			public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent arg0) {
				setWeightRateTotal();
				calcProjectPeriod();
				checkDateAndWeightRate();
				// ��ɱ�׼ ���200���ַ�
				KDTextField finishStandard = new KDTextField();
				finishStandard.setMaxLength(200);
				KDTDefaultCellEditor finishStandardEditor = new KDTDefaultCellEditor(finishStandard);
				kdtTasks.getColumn("finishStandard").setEditor(finishStandardEditor);
				// �������� ���40���ַ�
				KDTextField taskName = new KDTextField();
				taskName.setMaxLength(40);
				KDTDefaultCellEditor taskNameEditor = new KDTDefaultCellEditor(taskName);
				kdtTasks.getColumn("taskName").setEditor(taskNameEditor);

				// ������Դ
				KDTextField needRequest = new KDTextField();
				needRequest.setMaxLength(200);
				KDTDefaultCellEditor needRequestEditor = new KDTDefaultCellEditor(needRequest);
				kdtTasks.getColumn("requiredResource").setEditor(needRequestEditor);

				if (arg0.getColIndex() == kdtTasks.getColumn("relateTask").getColumnIndex()) {
					Object obj = arg0.getValue();
					if (null != obj) {
						if (obj instanceof Object[]) {
							calcProjectPeriod();
						}
					}
				}
				//����Ҫ��ɶ
//				setRelateTaskToNull();
				if (arg0.getColIndex() == kdtTasks.getColumn("planFinishDate").getColumnIndex()) {
					calcProjectPeriod();
					int index = arg0.getRowIndex();
					int temp = 0;
					if (null != kdtTasks.getRow(index).getCell("planStartDate").getValue() && null != kdtTasks.getRow(index).getCell("planFinishDate").getValue()) {
						temp = FDCDateHelper.getDiffDays((Date) kdtTasks.getRow(index).getCell("planStartDate").getValue(), (Date) kdtTasks.getRow(index).getCell("planFinishDate").getValue());
					}

					if (temp < 0) {
						FDCMsgBox.showInfo("�ƻ�������ڲ������ڼƻ���ʼ���ڣ�");
						kdtTasks.getStyleAttributes().setLocked(true);
					}
				}
			}

			public void editCanceled(KDTEditEvent arg0) {
				
			}

			public void editStarting(KDTEditEvent arg0) {
				setWeightRateTotal();
				calcProjectPeriod();
			}

			public void editStopping(KDTEditEvent arg0) {
				setWeightRateTotal();
				calcProjectPeriod();
			}

			public void editValueChanged(KDTEditEvent arg0) {
				
			}
		});

		ObjectValueRender ovrNum = new ObjectValueRender();
		ovrNum.setFormat(new BizDataFormat("$name$"));
		kdtTasks.getColumn("relateTask").setRenderer(ovrNum);
		if (editData.getAdminDept() == null)
		 this.prmtAdminDept.setValue(SysContext.getSysContext().getCurrentAdminUnit());
		 // ��������ֶ�
		kdtTasks.getColumn("complete").getStyleAttributes().setHided(true);
		// ��������������Դ���ܱ༭
		kdtTasks.getColumn("taskType").getStyleAttributes().setLocked(true);
		kdtTasks.getColumn("taskOrigin").getStyleAttributes().setLocked(true);
		// checkAndSetState();
		setOprtState(OprtState.VIEW);
		checkAndSetState();
	}

	public void onShow() throws Exception {
		super.onShow();
		if (editData.getState() != null && editData.getState() == FDCBillStateEnum.AUDITTED) {
			this.btnUnAudit.setVisible(true);
			this.btnUnAudit.setEnabled(true);
		} else {
			this.btnUnAudit.setVisible(false);
			this.btnUnAudit.setEnabled(false);
		}
	}
	/**
	 * 
	 * @description ��̬����Ȩ�غϼ�ֵ
	 * @author ���
	 * @createDate 2011-8-18 void
	 * @version EAS7.0
	 * @see
	 */
	public void setWeightRateTotal() {
		FDCTableHelper.apendFootRow(kdtTasks, new String[] { "weightRate" });
		if (null != kdtTasks.getFootRow(0).getCell("weightRate").getValue()) {
			FDCTableHelper.apendFootRow(kdtTasks, new String[] { "weightRate" });
		}
		if (kdtTasks.getRowCount() < 1) {
			kdtTasks.getFootRow(0).getCell("weightRate").setValue("0");
		}
		if (kdtTasks.getFootRow(0).getCell("weightRate") != null) {
			kdtTasks.getFootRow(0).getCell("weightRate").getStyleAttributes().setNumberFormat("#,##0");
		}

	}

	/**
	 * ���ù��� ���ݿ�ʼ���ںͽ�������
	 * 
	 * @description
	 * @author ���
	 * @createDate 2011-9-8 void
	 * @version EAS7.0
	 * @see
	 */
	private void calcProjectPeriod() {
		int rowIndex = kdtTasks.getSelectManager().getActiveRowIndex();
		int temp = 0;
		if (rowIndex < 0) {
			return;
		}
		if (null != kdtTasks.getRow(rowIndex).getCell("planStartDate").getValue() && null != kdtTasks.getRow(rowIndex).getCell("planFinishDate").getValue()) {
			temp = FDCDateHelper.getDiffDays((Date) kdtTasks.getRow(rowIndex).getCell("planStartDate").getValue(), (Date) kdtTasks.getRow(rowIndex).getCell("planFinishDate").getValue());
		}

		kdtTasks.getRow(rowIndex).getCell("projectPeriod").setValue(String.valueOf(temp));

	}

	/**
	 * ��ʼ����¼��ť by ���
	 */
	public void initEntryButton() {
		kdconTasks.addButton(this.btnInsertLine);
		kdconTasks.addButton(this.btnRemoveLine);
		kdconTasks.addButton(this.importProjectPlan);
		kdconTasks.addButton(this.importUnFinishTask);
		importProjectPlan.setIcon(EASResource.getIcon("imgTbtn_input"));
		importUnFinishTask.setIcon(EASResource.getIcon("imgTbtn_input"));
		this.btnInsertLine.setText("������");
		this.btnRemoveLine.setText("ɾ����");
		this.btnInsertLine.setEnabled(false);
		this.btnRemoveLine.setEnabled(false);

	}

	/**
	 * �󶨵Ŀؼ�ΪKDComboBox��cellEditor
	 * 
	 * @param enumList
	 *            ö�ٵ�list ���磺CertifacateNameEnum.getEnumList()
	 * @return
	 */
	private ICellEditor getKDComboBoxEditor(List enumList) {
		KDComboBox comboField = new KDComboBox();
		if (enumList != null)
			for (int i = 0; i < enumList.size(); i++) {
				comboField.addItem(enumList.get(i));
			}
		ICellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		return comboEditor;
	}

	protected void comboState_itemStateChanged(ItemEvent e) throws Exception {
		super.comboState_itemStateChanged(e);
		if (comboState.getSelectedItem() == FDCBillStateEnum.AUDITTED) {
			this.btnUnAudit.setVisible(true);
			this.btnUnAudit.setEnabled(true);
			this.btnSave.setVisible(false);
		} else {
			this.btnUnAudit.setVisible(false);
			this.btnUnAudit.setEnabled(false);
		}
		
	}
	/**
	 * ��ʼ�����Ϊ��������ӱ༭�� by ���
	 */
	private void initDetailTable() {

		// ��ʼ���������� ö��ֵ
		this.kdtTasks.getColumn("taskType").setEditor(getKDComboBoxEditor(RESchTaskTypeEnum.getEnumList()));
		// ��ʼ����������
		KDTextField taskNameField = new KDTextField();
		KDTDefaultCellEditor taskNameFieldEditor = new KDTDefaultCellEditor(taskNameField);
		this.kdtTasks.getColumn("taskName").setEditor(taskNameFieldEditor);
		this.kdtTasks.getColumn("taskName").setRequired(true);
		// ������
		KDBizPromptBox f7Person = new KDBizPromptBox();
		FDCClientUtils.setPersonF7(f7Person,this, null);
		f7Person.setEditable(false);
		KDTDefaultCellEditor f7PersonEditor = new KDTDefaultCellEditor(f7Person);
		kdtTasks.getColumn("adminPerson").setEditor(f7PersonEditor);
		kdtTasks.getColumn("adminPerson").setRequired(true);
		
		// ��ɱ�׼
		KDTextField finishStandard = new KDTextField();
		KDTDefaultCellEditor finishStandardFieldEditor = new KDTDefaultCellEditor(finishStandard);
		this.kdtTasks.getColumn("finishStandard").setEditor(finishStandardFieldEditor);

		// �ƻ��������
		KDDatePicker planDate = new KDDatePicker();
		KDTDefaultCellEditor planTimeFieldEditor = new KDTDefaultCellEditor(planDate);
		// planFinishDate.set
		this.kdtTasks.getColumn("planFinishDate").setEditor(planTimeFieldEditor);
		this.kdtTasks.getColumn("planFinishDate").setRequired(true);
		this.kdtTasks.getColumn("planStartDate").setEditor(planTimeFieldEditor);
		// Ȩ��
		KDFormattedTextField weightRate = new KDFormattedTextField();
		
		/* modified by zhaoqin for R140105-0033 on 2014/03/18 start */
		//weightRate.setPrecision(0);
		weightRate.setDataType(KDFormattedTextField.FLOAT_TYPE);
		weightRate.setPrecision(2);
		weightRate.setMinimumValue(0);
		weightRate.setMaximumValue(100);
		/* modified by zhaoqin for R140105-0033 on 2014/03/18 end */
		
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(weightRate);
		kdtTasks.getColumn("weightRate").setEditor(txtEditor);
		kdtTasks.getColumn("weightRate").setRequired(true);
		// �������
		KDBizPromptBox f7WorkTask = new KDBizPromptBox();//
		try {
			f7WorkTask.setDisplayFormat("$name$");
			f7WorkTask.setEditable(false);
			Date selDate = (Date) pkScheduleMonth.getValue();

			f7WorkTask.setSelector(new DeptMonthlySchedulePromptSelector(null, selDate, prerelateTask));
			 f7WorkTaskEditor = new KDTDefaultCellEditor(f7WorkTask);
			kdtTasks.getColumn("relateTask").setEditor(f7WorkTaskEditor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ������Ŀ
		KDBizPromptBox f7Project = new KDBizPromptBox();
		f7Project.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");
		f7Project.setDisplayFormat("$name$");
		f7Project.setEditable(false);
		f7Project.setEnabled(false);
		KDTDefaultCellEditor f7ProjectEditor = new KDTDefaultCellEditor(f7Project);
		kdtTasks.getColumn("project").setEditor(f7ProjectEditor);
		// ������ԴtaskOrigin
		this.kdtTasks.getColumn("taskOrigin").setEditor(getKDComboBoxEditor(RESchTaskOriginEnum.getEnumList()));

	}
	private KDTDefaultCellEditor f7WorkTaskEditor;
	private KDBizPromptBox f7WorkTask;
	private FDCScheduleTaskInfo prerelateTask;
	private void setRelateTask() {
		// �������
		if (f7WorkTask == null) {
			f7WorkTask = new KDBizPromptBox();
		}
		try {
			f7WorkTask.setDisplayFormat("$name$");
			f7WorkTask.setEditable(false);
			// add by libing at 2011-11-21 ����������������
			AdminOrgUnitInfo admininfo = (AdminOrgUnitInfo) prmtAdminDept.getValue();
			Date selDate = (Date) pkScheduleMonth.getValue();

			f7WorkTask.setSelector(new DeptMonthlySchedulePromptSelector(admininfo, selDate, prerelateTask));
			f7WorkTaskEditor = new KDTDefaultCellEditor(f7WorkTask);
			kdtTasks.getColumn("relateTask").setEditor(f7WorkTaskEditor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setRelateTaskToNull() {
		// �������
		if (f7WorkTask == null) {
			f7WorkTask = new KDBizPromptBox();
		}
		try {
			f7WorkTask.setDisplayFormat("$name$");
			f7WorkTask.setEditable(false);
			// add by libing at 2011-11-21 ����������������
			AdminOrgUnitInfo admininfo = (AdminOrgUnitInfo) prmtAdminDept.getValue();
			Date selDate = (Date) pkScheduleMonth.getValue();

			f7WorkTask.setSelector(new DeptMonthlySchedulePromptSelector(admininfo, selDate, null));
			f7WorkTaskEditor = new KDTDefaultCellEditor(f7WorkTask);
			kdtTasks.getColumn("relateTask").setEditor(f7WorkTaskEditor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadFields() {
		super.loadFields();
		this.prmtAdminDept.setDataNoNotify(editData.getAdminDept());
		this.pkScheduleMonth.setValue(editData.getScheduleMonth(), false);
		exeQueryList();
	}

	public void storeFields() {
		super.storeFields();
		getSaveInfo(editData);
		 // ����������״̬����
		if (editData.getState() != null && editData.getState() == FDCBillStateEnum.AUDITTED) {
			this.btnUnAudit.setVisible(true);
			this.btnUnAudit.setEnabled(true);
		}
	}
	
	/**
	 * 
	 * ���������ظ��෽����������½�������ҳƬ�д�
	 * 
	 * @author:Lijianbo
	 * @see com.kingdee.eas.framework.client.ListUI#getEditUIModal()
	 */
	protected String getEditUIModal() {
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return UIFactoryName.NEWWIN;
		} else {
			return UIFactoryName.NEWTAB;
		}
	}

	protected String getEditUIName() {
		return DeptMonthlyScheduleTaskEditUI.class.getName();
	}

	protected String getKeyFieldName() {
		return "id";
	}

	/**
	 * 
	 * @description ����༭���������
	 * @author lijianbo
	 * @createDate 2011-8-20
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 *             DeptMonthlyScheduleTaskInfo
	 * @version EAS7.0
	 * @see
	 */
	protected DeptMonthlyScheduleTaskInfo getSelectedInfo() throws EASBizException, BOSException {
		int rowIndex = kdtTasks.getSelectManager().getActiveRowIndex();
		// editData.getId();
		Object infoId = kdtTasks.getRow(rowIndex).getCell("id").getValue();

		if (infoId != null) {
			EntityViewInfo view = new EntityViewInfo();

			setSelector(view);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", infoId.toString()));

			view.setFilter(filter);

			DeptMonthlyScheduleTaskCollection supInfo = DeptMonthlyScheduleTaskFactory.getRemoteInstance().getDeptMonthlyScheduleTaskCollection(view);

			if (supInfo.size() > 0) {
				return supInfo.get(0);
			}
		}

		return null;
	}

	private void setSelector(EntityViewInfo view) {
		// ��ͷ��¼
		view.getSelector().add(new SelectorItemInfo("schedule.*"));
		view.getSelector().add(new SelectorItemInfo("schedule.adminDept.name"));
		view.getSelector().add(new SelectorItemInfo("schedule.adminDept.number"));
		view.getSelector().add(new SelectorItemInfo("schedule.adminDept.id"));
		// �·�
		view.getSelector().add(new SelectorItemInfo("scheduleMonth"));
		view.getSelector().add(new SelectorItemInfo("schedule.scheduleMonth"));
		// ��������
		view.getSelector().add(new SelectorItemInfo("taskName"));
		// ��������
		view.getSelector().add(new SelectorItemInfo("taskType"));
		// ������ԴTaskOrigin
		view.getSelector().add(new SelectorItemInfo("taskOrigin"));
		// ������
		view.getSelector().add(new SelectorItemInfo("adminPerson"));
		view.getSelector().add(new SelectorItemInfo("adminPerson.id"));
		view.getSelector().add(new SelectorItemInfo("adminPerson.name"));
		view.getSelector().add(new SelectorItemInfo("adminPerson.number"));
		// Ȩ��
		view.getSelector().add(new SelectorItemInfo("weightRate"));
		// ����
		view.getSelector().add(new SelectorItemInfo("projectPeriod"));
		// ��ɱ�׼
		view.getSelector().add(new SelectorItemInfo("finishStandard"));
		// �ƻ��������
		view.getSelector().add(new SelectorItemInfo("planFinishDate"));
		// �ƻ���ʼ����
		view.getSelector().add(new SelectorItemInfo("planStartDate"));
		// ������Դ
		view.getSelector().add(new SelectorItemInfo("requiredResource"));
		// ������Ŀ
		view.getSelector().add(new SelectorItemInfo("project"));
		view.getSelector().add(new SelectorItemInfo("project.id"));
		view.getSelector().add(new SelectorItemInfo("project.name"));
		view.getSelector().add(new SelectorItemInfo("project.number"));
		// �������
		view.getSelector().add(new SelectorItemInfo("relatedTask.*"));
		view.getSelector().add(new SelectorItemInfo("relatedTask.schedule.project.id"));
		view.getSelector().add(new SelectorItemInfo("relatedTask.schedule.project.name"));
	}

	/**
	 * 
	 * @param openType
	 * @throws Exception
	 * 
	 * @����������Action,openType�򿪶�Ӧ��ҳ��
	 * @���ߣ����
	 */

	private void actionOpenUI(String openType) throws Exception {
		// �����¼INFO
		DeptMonthlyScheduleTaskInfo info = getSelectedInfo();
		// ��������¼INFO
		FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
		int rowIndex = kdtTasks.getSelectManager().getActiveRowIndex();
		Object infoId = kdtTasks.getRow(rowIndex).getCell("id").getValue();
		// ȡ����������������� obj����
		Object obj = kdtTasks.getRow(rowIndex).getCell("relateTask").getValue();

		// �������ѡ��������ı��������

		// �������ƻ���ʼʱ�� - �ƻ����ʱ��
		Object startDate = kdtTasks.getRow(rowIndex).getCell("planStartDate").getValue();
		Object endDate = kdtTasks.getRow(rowIndex).getCell("planFinishDate").getValue();

		// ȡ�úϼ�ֵ����̬����Ȩ��
		Object total = kdtTasks.getFootRow(0).getCell("weightRate").getValue();
		if (null == info) {
			info = new DeptMonthlyScheduleTaskInfo();
		}
		// ���û���ҵ��������ʹ���¼������������ֵ������鿴����ҳ��
		if (obj instanceof Object[]) {
			if (obj != null) {
				Object[] objs = (Object[]) obj;
				if (objs.length > 0) {
					taskInfo = (FDCScheduleTaskInfo) objs[0];
					info.setRelatedTask(taskInfo);
					info.setTaskName(taskInfo.toString());
					info.setAdminPerson(taskInfo.getAdminPerson());
					taskInfo.setStart((Date) startDate);
					taskInfo.setEnd((Date) endDate);
				}
			}
		} else {
			info.setRelatedTask((FDCScheduleTaskInfo) obj);
		}
		info.setPlanStartDate((Date) startDate);
		info.setPlanFinishDate((Date) endDate);
		// �����ڱ༭״̬�ǣ��û��ı��ֶ�û�б��棬Ȼ�������ƽ���ʱ������Ҫ����һ��
		// ��ɱ�׼
		Object finishStandard = kdtTasks.getRow(rowIndex).getCell("finishStandard").getValue();
		Object taskOrigin = kdtTasks.getRow(rowIndex).getCell("taskOrigin").getValue();
		// Ȩ��
		Object weightRate = kdtTasks.getRow(rowIndex).getCell("weightRate").getValue();
		Object projectPeriod = kdtTasks.getRow(rowIndex).getCell("projectPeriod").getValue();
		if (null != taskOrigin) {
			if (taskOrigin.toString().equals(RESchTaskOriginEnum.getEnum(RESchTaskOriginEnum.MAIN_VALUE.toString()).toString())) {
				info.setTaskOrigin(RESchTaskOriginEnum.MAIN);
			} else if (taskOrigin.toString().equals(RESchTaskOriginEnum.getEnum(RESchTaskOriginEnum.SPECIAL_VALUE.toString()).toString())) {
				info.setTaskOrigin(RESchTaskOriginEnum.SPECIAL);
			} else {
				info.setTaskOrigin(RESchTaskOriginEnum.INPUT);
			}

		}
		if (null != finishStandard) {
			info.setFinishStandard(finishStandard.toString());
		}
		if (weightRate != null) {
			if (!weightRate.toString().equals("")) {
				info.setWeightRate(new BigDecimal(Float.parseFloat(weightRate.toString())));
			}
		}
		if (projectPeriod != null) {
			if (!projectPeriod.toString().equals("")) {
				info.setProjectPeriod(new BigDecimal(Float.parseFloat(projectPeriod.toString())));
			}
		}
		UIContext uiContext = new UIContext(this);
		if (infoId != null) {
			uiContext.put(UIContext.ID, infoId.toString());
		} else {
			return;
		}

		uiContext.put("rowIndex", (String.valueOf(rowIndex)));
		uiContext.put("total", total);
		uiContext.put("taskInfo", taskInfo);
		Date dateView = (Date) pkScheduleMonth.getValue();
		uiContext.put("datex", dateView);
		if (info.getId() != null) {
			uiContext.put("DeptMonthlyScheduleTaskInfo", info);
		} else {
			return;
		}
		uiContext.put("UIlist", this);

		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = null;
		if (openType.equals("VIEW")) {
			uiWindow = uiFactory.create(DeptMonthlyScheduleTaskEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
			return;
		} else if (openType.equals("EDIT")) {
			uiWindow = uiFactory.create(DeptMonthlyScheduleTaskEditUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
			return;
		} else {
			uiWindow = uiFactory.create(DeptMonthlyScheduleTaskEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		}
	}

	/**
	 * @description
	 * @author lijianbo
	 * @createDate 2011-8-16
	 * @version EAS7.0
	 * @see
	 */

	public void actionPlait_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		this.kdtTasks.getStyleAttributes().setLocked(false);
	}

	/**
	 * @description �鿴
	 * @author lijianbo
	 * @createDate 2011-8-26
	 * @version EAS7.0
	 * @see
	 */

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = kdtTasks.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ��һ��!");
			SysUtil.abort();
		}
		DeptMonthlyScheduleTaskInfo info = getSelectedInfo();
		Object infoId = kdtTasks.getRow(rowIndex).getCell("id").getValue();
		/**
		 * ��ȫ�ֱ���ȡ������Ŀ
		 */
		Object project = null;
		if (prerelateTask != null && prerelateTask.getSchedule() != null && prerelateTask.getSchedule().getProject() != null) {
			project = prerelateTask.getSchedule().getProject();
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("DeptMonthlyScheduleTaskInfo", info);
		if (project != null) {
			uiContext.put("selectProject", project.toString());
		}
		Object total = kdtTasks.getFootRow(0).getCell("weightRate").getValue();
		uiContext.put("total", total);
		if (infoId != null) {

			uiContext.put(UIContext.ID, infoId.toString());
			// ������Ŀ���ź�����
			AdminOrgUnitInfo adinfo = (AdminOrgUnitInfo) prmtAdminDept.getValue();
			Date datex = (Date) pkScheduleMonth.getValue();
			uiContext.put("adinfo", adinfo);
			uiContext.put("datex", datex);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = null;
			uiWindow = uiFactory.create(DeptMonthlyScheduleTaskEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		} else {
			int activeRowIndex = kdtTasks.getSelectManager().getActiveRowIndex();
			// new ���������ڴ���Ѿ�д�õ���Ϣ
			DeptMonthlyScheduleTaskInfo transinfo = new DeptMonthlyScheduleTaskInfo();
			// �������
			if (kdtTasks.getRow(activeRowIndex).getCell("taskType").getValue() != null) {
				transinfo.setTaskType((RESchTaskTypeEnum) kdtTasks.getRow(activeRowIndex).getCell("taskType").getValue());
			}
			// ��������
			if (kdtTasks.getRow(activeRowIndex).getCell("taskName").getValue() != null) {
				transinfo.setTaskName(kdtTasks.getRow(activeRowIndex).getCell("taskName").getValue().toString());
			}
			// ������
			if (kdtTasks.getRow(activeRowIndex).getCell("adminPerson").getValue() != null) {
				transinfo.setAdminPerson((PersonInfo) kdtTasks.getRow(activeRowIndex).getCell("adminPerson").getValue());
			}
			// Ȩ��
			if (kdtTasks.getRow(activeRowIndex).getCell("weightRate").getValue() != null) {
				transinfo.setWeightRate(new BigDecimal(kdtTasks.getRow(activeRowIndex).getCell("weightRate").getValue().toString()));
			}
			// ��ɱ�׼
			if (kdtTasks.getRow(activeRowIndex).getCell("finishStandard").getValue() != null) {
				transinfo.setFinishStandard(kdtTasks.getRow(activeRowIndex).getCell("finishStandard").getValue().toString());
			}
			// �ƻ��������
			if (kdtTasks.getRow(activeRowIndex).getCell("planFinishDate").getValue() != null) {
				transinfo.setPlanFinishDate((Date) kdtTasks.getRow(activeRowIndex).getCell("planFinishDate").getValue());
			}
			// �������
			if (kdtTasks.getRow(activeRowIndex).getCell("relateTask").getValue() != null) {
				transinfo.setRelatedTask((FDCScheduleTaskInfo) kdtTasks.getRow(activeRowIndex).getCell("relateTask").getValue());
			}
			// ������Ŀ
			if (kdtTasks.getRow(activeRowIndex).getCell("project").getValue() != null) {
				transinfo.setProject((CurProjectInfo) kdtTasks.getRow(activeRowIndex).getCell("project").getValue());
			}
			// ������Դ
			if (kdtTasks.getRow(activeRowIndex).getCell("taskOrigin").getValue() != null) {
				transinfo.setTaskOrigin((RESchTaskOriginEnum) kdtTasks.getRow(activeRowIndex).getCell("taskOrigin").getValue());
			}
			// ����
			if (kdtTasks.getRow(activeRowIndex).getCell("projectPeriod").getValue() != null) {
				transinfo.setProjectPeriod(new BigDecimal(kdtTasks.getRow(activeRowIndex).getCell("projectPeriod").getValue().toString()));
			}
			// �ƻ���ʼ����
			if (kdtTasks.getRow(activeRowIndex).getCell("planStartDate").getValue() != null) {
				transinfo.setPlanStartDate((Date) kdtTasks.getRow(activeRowIndex).getCell("planStartDate").getValue());
			}
			// ������Դ
			if (kdtTasks.getRow(activeRowIndex).getCell("requiredResource").getValue() != null) {
				transinfo.setRequiredResource((kdtTasks.getRow(activeRowIndex).getCell("requiredResource").getValue().toString()));
			}
			// ��ɽ���
			if (kdtTasks.getRow(activeRowIndex).getCell("complete").getValue() != null) {
				transinfo.setComplete(new BigDecimal(kdtTasks.getRow(activeRowIndex).getCell("complete").getValue().toString()));
			}
			
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			UIContext ui = new UIContext();
			// ������Ŀ���ź�����
			AdminOrgUnitInfo adinfo = (AdminOrgUnitInfo) prmtAdminDept.getValue();
			Date datex = (Date) pkScheduleMonth.getValue();
			
			if (project != null) {
				ui.put("selectProject", project.toString());
			}
			ui.put("adinfo", adinfo);
			ui.put("datex", datex);
			ui.put("Owner", this);
			ui.put("par", editData);
			ui.put("transinfo", transinfo);
			ui.put("rowIndex2", new String(activeRowIndex + ""));
			IUIWindow uiWindow = null;
			uiWindow = uiFactory.create(DeptMonthlyScheduleTaskEditUI.class.getName(), ui, null, OprtState.ADDNEW);
			uiWindow.show();
		}


	}

	/**
	 * ������ƶԲ����·������޸�
	 * 
	 * @description
	 * @author ���
	 * @createDate 2011-8-16
	 * @version EAS7.0
	 * @see
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		/*������������޸Ĳ��ܱ༭�����⣬����Ҫ����super.actionEdit_actionPerformed(e)������ж�Ӧ����*/
		getUIContext().put("CURRENT.VO", editData);//����super.actionEdit_actionPerformed(e)���"CURRENT.VO"��ȡ������Ȼ��ŵ�editData��
		super.actionEdit_actionPerformed(e);

		this.kdtTasks.getStyleAttributes().setLocked(false);
		this.btnEdit.setEnabled(false);
		this.btnSave.setVisible(true);
		actionSave.setEnabled(!FDCBillStateEnum.SUBMITTED.equals(editData.getState()));
		this.btnSubmit.setEnabled(true);
		this.btnInsertLine.setEnabled(true);
		this.btnRemoveLine.setEnabled(true);
		this.btnAudit.setVisible(true);
		this.btnUnAudit.setVisible(true);
		this.btnAudit.setEnabled(false);
		this.btnUnAudit.setEnabled(false);
		this.importProjectPlan.setEnabled(true);
		this.importUnFinishTask.setEnabled(true);
		setOprtState(OprtState.EDIT);
		setLockByCell();
		setNoUpdate();
	}

	private void setNoUpdate() {
		for (int i = 0; i < kdtTasks.getRowCount(); i++) {
			IRow row = kdtTasks.getRow(i);
			if (null != row.getCell("taskOrigin").getValue()) {
				if (!row.getCell("taskOrigin").getValue().toString().equals(RESchTaskOriginEnum.INPUT.toString())) {
					row.getCell("taskName").getStyleAttributes().setLocked(true);
					row.getCell("planFinishDate").getStyleAttributes().setLocked(true);
					row.getCell("planStartDate").getStyleAttributes().setLocked(true);
					row.getCell("projectPeriod").getStyleAttributes().setLocked(true);
				}
			}

		}
	}

	/**
	 * �ύ�¶ȼƻ�
	 * 
	 * @description
	 * @author ���
	 * @createDate 2011-8-19
	 * @version EAS7.0
	 * @see
	 */

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		int rowCount = kdtTasks.getRowCount();
		 if (rowCount < 1) {
			FDCMsgBox.showInfo("�˲��żƻ���û��¼������,�����ύ");
			abort();
		}
		int weightRate = new BigDecimal(kdtTasks.getFootRow(0).getCell("weightRate").getValue().toString()).intValue();
		if (rowCount > 0 && weightRate != 0) {

			if (weightRate != 100) {
				FDCMsgBox.showWarning(this, "������Ȩ��֮�ͱ���Ϊ100%");
				if (getOprtState().equals(OprtState.EDIT)) {
					// this.btnEdit.setEnabled(false);
					this.btnEdit.setEnabled(true);
				}
				SysUtil.abort();
			}
		}

		checkEntryIsNull();
		getSaveInfo(editData);
		// ����ǰ���ԭ��������
		// deleteAgoNumber(info);
		// ����ScheDuleInfo
		// �����¼INFO
		checkIsCanSave(editData);
		comboState.setSelectedItem(FDCBillStateEnum.SUBMITTED);
		editData.setState(FDCBillStateEnum.SUBMITTED);
		// DeptMonthlyScheduleFactory.getRemoteInstance().submit(info);
		super.actionSubmit_actionPerformed(e);

		// �ύ��������
		// DeptMonthlyScheduleFactory.getRemoteInstance().submitForWF(info);
		this.actionImportProjectPlan.setEnabled(false);
		this.actionImportUnFinishTask.setEnabled(false);
		showSubmitSuccess();
		this.btnAudit.setVisible(true);
		this.btnAudit.setEnabled(true);
		this.btnEdit.setEnabled(true);
		this.btnSubmit.setEnabled(false);
		//�ύ����������
		this.btnUnAudit.setVisible(true);
		kdtTasks.getStyleAttributes().setLocked(true);
		this.actionImportProjectPlan.setEnabled(false);
		this.actionImportUnFinishTask.setEnabled(false);
		// �ύ����Ȼ���Դ��ڱ��水ť��������Ȼ����ʾ
		this.btnSave.setVisible(true);
		this.btnSave.setEnabled(true);
		this.btnEdit.setVisible(true);
		this.btnEdit.setEnabled(false);
		this.btnSubmit.setEnabled(true);
		this.btnInsertLine.setEnabled(true);
		this.btnRemoveLine.setEnabled(true);
		this.importProjectPlan.setEnabled(true);
		this.importUnFinishTask.setEnabled(true);
		setOprtState(OprtState.EDIT);
		this.btnAudit.setVisible(true);
		this.btnAudit.setEnabled(true);
	}
	
	public IObjectPK runSubmit() throws Exception {
		if (getBizInterface() == null) {
			throw new Exception("don't implement getBizInterface()  !");
		}
		if (UtilRequest.isPrepare("ActionSubmit", this)) {// TODO �˴��Ժ�Ҫ�޸�
			Object pk = ActionCache.get(ResponseConstant.FRAMEWORK_PK);
			if (pk != null) {
				return (IObjectPK) pk;
			}
		}
		return getBizInterface().submit(editData);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (null == editData.getId()) {
			FDCMsgBox.showWarning("���ȱ��浥��");
			SysUtil.abort();
		}
		String id = editData.getId().toString();

		if (FDCUtils.isRunningWorkflow(id)) {
			MsgBox.showWarning(this, "������������");
			SysUtil.abort();
		}
		/**
		 * Ȩ�ؼ�� add by libing
		 */
		int rowCount = kdtTasks.getRowCount();
		if (rowCount < 1) {
			FDCMsgBox.showInfo("�˲��żƻ���û��¼������,����������");
			abort();
		}
		int weightRate = new BigDecimal(kdtTasks.getFootRow(0).getCell("weightRate").getValue().toString()).intValue();
		if (rowCount > 0 && weightRate != 0) {

			if (weightRate != 100) {
				FDCMsgBox.showWarning(this, "������Ȩ��֮�ͱ���Ϊ100%");
				if (getOprtState().equals(OprtState.EDIT)) {
					// this.btnEdit.setEnabled(false);
					this.btnEdit.setEnabled(true);
				}
				SysUtil.abort();
			}
		}
		
		
		
		if (editData != null) {
			if (comboState.getSelectedItem() != null) {
				if (comboState.getSelectedItem().equals(FDCBillStateEnum.SUBMITTED)) {
					if (editData.getId() != null) {
						editData.setState(FDCBillStateEnum.AUDITTED);
						comboState.setSelectedItem(FDCBillStateEnum.AUDITTED);// t
						DeptMonthlyScheduleFactory.getRemoteInstance().audit(BOSUuid.read(editData.getId().toString()));
					}
					FDCMsgBox.showWarning("�����ɹ�");
					this.actionImportProjectPlan.setEnabled(false);
					this.actionImportUnFinishTask.setEnabled(false);

				} else {
					FDCMsgBox.showWarning("����״̬��������д˲���");
					abort();
				}
			}
		}

		this.btnSave.setEnabled(false);
		this.btnEdit.setEnabled(false);
		this.btnAudit.setEnabled(false);
		this.btnUnAudit.setVisible(true);
		this.btnUnAudit.setEnabled(true);
		this.btnInsertLine.setEnabled(false);
		this.btnRemoveLine.setEnabled(false);
		this.importProjectPlan.setEnabled(false);
		this.importUnFinishTask.setEnabled(false);
		setOprtState(OprtState.VIEW);
		// ������������������
		this.btnAudit.setVisible(true);
		kdtTasks.getStyleAttributes().setLocked(true);
		checkAndSetState();
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && null == editData.getId()) {
			FDCMsgBox.showWarning("���ȱ��浥��");
			SysUtil.abort();
		}

		String id = editData.getId().toString();
		if (FDCUtils.isRunningWorkflow(id)) {
			MsgBox.showWarning(this, "������δ����");
			SysUtil.abort();
		}

		if (editData != null) {
			if (comboState.getSelectedItem() != null) {
				if (comboState.getSelectedItem().equals(FDCBillStateEnum.AUDITTED)) {
					if (editData.getId() != null) {
						DeptMonthlyScheduleFactory.getRemoteInstance().unAudit(BOSUuid.read(editData.getId().toString()));
					}
					FDCMsgBox.showWarning("�����ɹ�");
					comboState.setSelectedItem(FDCBillStateEnum.SUBMITTED);
					editData.setState(FDCBillStateEnum.SUBMITTED);
					this.btnSave.setEnabled(false);
					this.btnEdit.setEnabled(true);
					this.btnUnAudit.setEnabled(false);
					this.btnAudit.setVisible(true);
					this.btnAudit.setEnabled(true);
					this.btnInsertLine.setEnabled(false);
					this.btnRemoveLine.setEnabled(false);
				} else {
					FDCMsgBox.showWarning("����״̬��������д˲���");
				}
			}

		}
		setOprtState(OprtState.VIEW);
		// ���������ύ���Կ���
		this.btnSubmit.setVisible(true);
		this.btnSubmit.setEnabled(true);
	}

	public void actionImportUnFinishTask_actionPerformed(ActionEvent e) throws Exception {		
		upQueryList();
	}

	/*
	 * @������Ŀ����ƻ�
	 * 
	 * @author �� ���
	 * 
	 * @date ��2011-09-02
	 * 
	 * @param action
	 */
	public void actionImportProjectPlan_actionPerformed(ActionEvent e) throws Exception {
	
		Date scheduleMonth = (Date) pkScheduleMonth.getValue();
		List list = getScheduleTaskInfo((AdminOrgUnitInfo) prmtAdminDept.getValue(), scheduleMonth);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				DeptMonthlyScheduleTaskInfo taskInfo = (DeptMonthlyScheduleTaskInfo) list.get(i);

				// �ж��Ƿ��ظ�����ȡ����
				if (isRepete(taskInfo)) {
						continue;
				} else {
					setOprtState(OprtState.EDIT);
					IRow row = kdtTasks.addRow();
					row.getCell("taskType").getStyleAttributes().setLocked(true);
					row.getCell("project").getStyleAttributes().setLocked(true);
					// row.getCell("taskOrigin").getStyleAttributes().getp.
					// getProtection().setLocked(true);
					//row.getCell("taskOrigin").getStyle().set.getStyleAttributes
					// ().setBackground(Color.gray);
					row.getCell("taskType").setValue(taskInfo.getTaskType());
					row.getCell("taskName").setValue(taskInfo.getTaskName());
					row.getCell("taskName").getStyleAttributes().setLocked(true);
					row.getCell("adminPerson").setValue(taskInfo.getAdminPerson());
					row.getCell("finishStandard").setValue(taskInfo.getFinishStandard());
					row.getCell("planFinishDate").setValue(taskInfo.getPlanFinishDate());
					// ���üƻ�������ڲ��ܱ༭
					row.getCell("planFinishDate").getStyleAttributes().setLocked(true);
					// xianzai bu xuayao na wan cheng jindu
					// if (taskInfo.getComplete() != null) {
					// row.getCell("complete").setValue(taskInfo.getComplete());
					// }
					if (taskInfo.getWeightRate() != null) {
						row.getCell("weightRate").setValue(taskInfo.getWeightRate());
					}
					if (null != taskInfo.getRelatedTask()) {
						row.getCell("relateTask").setValue(taskInfo.getRelatedTask());
					}
					// ������Ŀ
					row.getCell("project").setValue(taskInfo.getProject());
					// row.getCell("project").setValue(((FDCScheduleTaskInfo)
					// list.get(i)).getSchedule().getProject());
					row.getCell("taskOrigin").setValue(taskInfo.getTaskOrigin());
					// row.getCell("id").setValue(taskInfo.getId());
					// �����ֶμƻ���ʼ���� ��������Դ������
					row.getCell("planStartDate").setValue(taskInfo.getPlanStartDate());
					if (null != row.getCell("planStartDate").getValue() && null != row.getCell("planFinishDate").getValue()) {
						int projectPeriod = FDCDateHelper.getDiffDays((Date) row.getCell("planStartDate").getValue(), (Date) row.getCell("planFinishDate").getValue());
						row.getCell("projectPeriod").setValue(String.valueOf(projectPeriod));
					}
					row.getCell("requiredResource").setValue(taskInfo.getRequiredResource());
				}
			}

		}
		
		setLockByCell();

		super.actionImportProjectPlan_actionPerformed(e);

	}
	// �ж��Ƿ��ظ�
	private boolean isRepete(DeptMonthlyScheduleTaskInfo task) {
		RESchTaskOriginEnum source = task.getTaskOrigin();
		int x = kdtTasks.getRowCount();

		if (source.equals(RESchTaskOriginEnum.INPUT)) {
			for (int ki = 0; ki < x; ki++) {
				if (kdtTasks.getRow(ki) != null) {
					if (null != kdtTasks.getRow(ki).getCell("taskOrigin").getUserObject()) {
						BOSUuid id = (BOSUuid) kdtTasks.getRow(ki).getCell("taskOrigin").getUserObject();
						if (id != null && id.equals(task.getId())) {
							return true;
						}
					}
				}
			}
			return false;
		} else {

			if (task.getRelatedTask() == null) {
				return false;
			}

			for (int ki = 0; ki < x; ki++) {
				if (kdtTasks.getRow(ki) != null) {
					if (null != kdtTasks.getRow(ki).getCell("relateTask").getValue()) {
						FDCScheduleTaskInfo currTask = (FDCScheduleTaskInfo) kdtTasks.getRow(ki).getCell("relateTask").getValue();
						if (currTask != null && currTask.getId().equals(task.getRelatedTask().getId())) {
							return true;
						}
					}
				}
			}
			return false;
		}
	}

	/**
	 * ��������ƻ�
	 * 
	 * @description
	 * @author ���
	 * @createDate 2011-9-14
	 * @param adminDept
	 * @param date
	 * @return
	 * @throws Exception
	 *             List
	 * @version EAS7.0
	 * @see
	 */
	private List getScheduleTaskInfo(AdminOrgUnitInfo adminDept, Date date) throws Exception {
		List list = new ArrayList();
		Date[] dateArrays = getMonthStartAndEndDate(date);

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("parent.id");
		view.getSelector().add("parent.name");
		view.getSelector().add("id");
		view.getSelector().add("name");
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("parent.*"));
		view.getSelector().add(new SelectorItemInfo("schedule.*"));
		view.getSelector().add(new SelectorItemInfo("adminPerson.*"));
		view.getSelector().add(new SelectorItemInfo("schedule.project.id"));
		view.getSelector().add(new SelectorItemInfo("schedule.project.name"));
		view.getSelector().add(new SelectorItemInfo("schedule.projectSpecial.id"));
		view.getSelector().add(new SelectorItemInfo("schedule.projectSpecial.name"));
		view.getSelector().add(new SelectorItemInfo("schedule.projectSpecial.number"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("adminDept.id", adminDept.getId()));
		// ��ʼ����С�ڱ��³����ҽ������ڴ��ڱ��½���
		filter.getFilterItems().add(new FilterItemInfo("start", dateArrays[0], CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("end", dateArrays[1], CompareType.GREATER));
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));

		FilterInfo thisMonth = new FilterInfo();

		thisMonth.getFilterItems().add(new FilterItemInfo("adminDept.id", adminDept.getId()));
		thisMonth.getFilterItems().add(new FilterItemInfo("start", dateArrays[0], CompareType.GREATER_EQUALS));
		thisMonth.getFilterItems().add(new FilterItemInfo("end", dateArrays[1], CompareType.LESS_EQUALS));
		thisMonth.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
		thisMonth.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));

		filter.mergeFilter(thisMonth, "or");

		FilterInfo finishedThisMonth = new FilterInfo();

		finishedThisMonth.getFilterItems().add(new FilterItemInfo("adminDept.id", adminDept.getId()));
		finishedThisMonth.getFilterItems().add(new FilterItemInfo("end", dateArrays[1], CompareType.LESS_EQUALS));
		finishedThisMonth.getFilterItems().add(new FilterItemInfo("end", dateArrays[0], CompareType.GREATER_EQUALS));
		finishedThisMonth.getFilterItems().add(new FilterItemInfo("start", dateArrays[0], CompareType.LESS));
		finishedThisMonth.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
		finishedThisMonth.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));

		filter.mergeFilter(finishedThisMonth, "or");

		FilterInfo startThisMonth = new FilterInfo();

		startThisMonth.getFilterItems().add(new FilterItemInfo("adminDept.id", adminDept.getId()));
		startThisMonth.getFilterItems().add(new FilterItemInfo("start", dateArrays[1], CompareType.LESS_EQUALS));
		startThisMonth.getFilterItems().add(new FilterItemInfo("start", dateArrays[0], CompareType.GREATER_EQUALS));
		startThisMonth.getFilterItems().add(new FilterItemInfo("end", dateArrays[1], CompareType.GREATER_EQUALS));
		startThisMonth.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
		startThisMonth.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));

		filter.mergeFilter(startThisMonth, "or");

		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo itemInfo = new SorterItemInfo("schedule.project.id");
		itemInfo.setSortType(SortType.DESCEND);
		sorter.add(itemInfo);
		itemInfo = new SorterItemInfo("longnumber");
		itemInfo.setSortType(SortType.DESCEND);
		sorter.add(itemInfo);
		view.setSorter(sorter);
		
		
		view.setFilter(filter);
		FDCScheduleTaskCollection collection = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		if (collection.size() > 0) {
			for (int i = 0; i < collection.size(); i++) {
				FDCScheduleTaskInfo scheduleTaskInfo = collection.get(i);
				DeptMonthlyScheduleTaskInfo deptMonthlyScheduleInfo = new DeptMonthlyScheduleTaskInfo();
				if (null != scheduleTaskInfo) {
					deptMonthlyScheduleInfo.setId(scheduleTaskInfo.getId());
					deptMonthlyScheduleInfo.setTaskName(scheduleTaskInfo.getName());
					deptMonthlyScheduleInfo.setTaskType(scheduleTaskInfo.getTaskType());
					deptMonthlyScheduleInfo.setAdminPerson(scheduleTaskInfo.getAdminPerson());
					deptMonthlyScheduleInfo.setPlanFinishDate(scheduleTaskInfo.getEnd());
					if (scheduleTaskInfo.getComplete() != null) {
						deptMonthlyScheduleInfo.setComplete(scheduleTaskInfo.getComplete());
					}
					
					if (null != scheduleTaskInfo.getSchedule()) {
						FDCScheduleInfo scheduleInfo = scheduleTaskInfo.getSchedule();
						if (scheduleInfo != null) {
							if (scheduleInfo.getProjectSpecial() != null) {
								deptMonthlyScheduleInfo.setTaskOrigin(RESchTaskOriginEnum.SPECIAL);
							} else {
								deptMonthlyScheduleInfo.setTaskOrigin(RESchTaskOriginEnum.MAIN);
							}
							deptMonthlyScheduleInfo.setProject(scheduleInfo.getProject());
						}

					}

					deptMonthlyScheduleInfo.setPlanStartDate(scheduleTaskInfo.getStart());
					deptMonthlyScheduleInfo.setRelatedTask(scheduleTaskInfo);
					list.add(deptMonthlyScheduleInfo);
				}
			}

		}

		return list;
	}

	/**
	 * ����ID�õ�FDCScheduleInfo
	 * 
	 * @param fdcScheduleID
	 * @return
	 * @throws Exception
	 */
	public FDCScheduleInfo getByID(String fdcScheduleID) throws Exception {
		FDCScheduleInfo info = null;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("project.*"));
		view.getSelector().add(new SelectorItemInfo("projectSpecial.*"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", fdcScheduleID));
		view.setFilter(filter);
		FDCScheduleCollection collection = FDCScheduleFactory.getRemoteInstance().getFDCScheduleCollection(view);
		if (collection.size() > 0) {
			info = collection.get(0);
		}
		return info;
	}

	public Date[] getMonthStartAndEndDate(Date date) {
		Date[] dateArrays = new Date[2];
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_MONTH);
		c.add(Calendar.DAY_OF_MONTH, -(day - 1));
		Date firstDateOfMonth = c.getTime();

		c.add(Calendar.DAY_OF_MONTH, c.getActualMaximum(c.DAY_OF_MONTH) - 1);
		Date lastDateOfMonth = c.getTime();
		dateArrays[0] = firstDateOfMonth;
		dateArrays[1] = lastDateOfMonth;
		return dateArrays;
	}
	
	/**
	 * �ƻ��·�ֵ�ı��¼� by ���
	 */
	protected void pkScheduleMonth_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		if (e.getNewValue().equals(e.getOldValue())) {
			return;
		} else {
			AdminOrgUnitInfo adminDept = (AdminOrgUnitInfo) prmtAdminDept.getValue();
			if (adminDept == null) {
				adminDept = SysContext.getSysContext().getCurrentAdminUnit();
			}
			Date selectDate = (Date) e.getNewValue();
			EntityViewInfo view = new EntityViewInfo();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(selectDate);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("adminDept.id", adminDept.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("year", year));
			filter.getFilterItems().add(new FilterItemInfo("month", month));
			view.setFilter(filter);
			view.setSelector(getSelectors());

			DeptMonthlyScheduleCollection cols = DeptMonthlyScheduleFactory.getRemoteInstance().getDeptMonthlyScheduleCollection(view);
			if (!cols.isEmpty()) {
				editData = cols.get(0);
			} else {
				editData = (DeptMonthlyScheduleInfo) createNewData();
			}
		 }

		exeQueryList();
		checkAndSetState();
	}

	private void checkAndSetState() {
		/**
		 * update by libing �޸�Ϊ�������������ȥ�ж�
		 */
		if (comboState.getSelectedItem() == null) {
			btnEdit.setEnabled(true);
			return;
		}
		
		if (((FDCBillStateEnum) comboState.getSelectedItem()).equals(FDCBillStateEnum.AUDITTED)) {
			kdtTasks.getStyleAttributes().setLocked(true);
			this.btnEdit.setEnabled(false);
			this.btnSubmit.setEnabled(false);
			this.btnInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.importProjectPlan.setEnabled(false);
			this.importUnFinishTask.setEnabled(false);
		}
		if (((FDCBillStateEnum) comboState.getSelectedItem()).equals(FDCBillStateEnum.SUBMITTED)) {
			importUnFinishTask.setEnabled(false);
			importProjectPlan.setEnabled(false);
			btnInsertLine.setEnabled(false);
			btnRemoveLine.setEnabled(false);
			btnEdit.setEnabled(true);
		}
	}

	/**
	 * ���β���ֵ�ı��¼� by ���
	 */
	protected void prmtAdminDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
        /** ��ȡ���� */
		if (e.getNewValue() != null) {
			AdminOrgUnitInfo adminDept = (AdminOrgUnitInfo) e.getNewValue();
			if (e.getNewValue().equals(e.getOldValue())) {
				return;
			} else {
				EntityViewInfo view = new EntityViewInfo();
				Date scheduleMonth = (Date) pkScheduleMonth.getValue();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(scheduleMonth);
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("adminDept.id", adminDept.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("year", year));
				filter.getFilterItems().add(new FilterItemInfo("month", month));
				view.setFilter(filter);
				view.setSelector(getSelectors());

				DeptMonthlyScheduleCollection cols = DeptMonthlyScheduleFactory.getRemoteInstance().getDeptMonthlyScheduleCollection(view);
				if (!cols.isEmpty()) {
					editData = cols.get(0);
				} else {
					editData = (DeptMonthlyScheduleInfo) createNewData();
				}
			}
			

		}
		
		setRelateTask();
		exeQueryList();
		checkAndSetState();
	}
	
	public void upQueryList() {
		DeptMonthlyScheduleInfo info = new DeptMonthlyScheduleInfo();
		info.setAdminDept(SysContext.getSysContext().getCurrentAdminUnit());

		// ������F7����
		Date date = new Date();
		if (null != this.prmtAdminDept.getValue()) {
			info.setAdminDept((AdminOrgUnitInfo) this.prmtAdminDept.getValue());
		}
		
		Calendar cal = Calendar.getInstance();
		Date selectDate = (Date) pkScheduleMonth.getValue();
		int year = OpReportBaseHelper.getDateFiled(selectDate, Calendar.YEAR);
		int month = OpReportBaseHelper.getDateFiled(selectDate, Calendar.MONTH);
		cal.setTime(date);
		cal.set(year, month, 1, 0, 0, 0);
		
		info.setScheduleMonth(cal.getTime());
		// ���ݲ��ţ��ƻ��·ݻ�ȡ��Ӧ�Ĳ����¶ȼƻ�
		DeptMonthlyScheduleInfo exsitInfo = getDeptUpMonthlyScheduleInfo(info.getAdminDept(), cal);
		if (exsitInfo != null) {
			info = exsitInfo;
			int y = info.getTasks().size();
			

			for (int i = 0; i < y; i++) {
				DeptMonthlyScheduleTaskInfo taskInfo = (DeptMonthlyScheduleTaskInfo) info.getTasks().get(i);
				// ��ɽ���Ϊ100�ģ����Ѿ���ɣ���Ӧ�����룬����ֱ����������ѭ��
				if (taskInfo.getComplete() != null) {
					if (taskInfo.getComplete().compareTo(new BigDecimal(100)) == 0) {
						continue;
					}
				}
				if (isRepete(taskInfo)) {
					continue;
				} else {
					IRow row = this.kdtTasks.addRow();
					row.getCell("taskType").setValue(taskInfo.getTaskType());
					row.getCell("taskName").setValue(taskInfo.getTaskName());
					row.getCell("adminPerson").setValue(taskInfo.getAdminPerson());
					row.getCell("finishStandard").setValue(taskInfo.getFinishStandard());
					row.getCell("planFinishDate").setValue(taskInfo.getPlanFinishDate());
					
					if (taskInfo.getWeightRate() != null) {
						row.getCell("weightRate").setValue(taskInfo.getWeightRate());
					}
					if (null != taskInfo.getRelatedTask()) {
						row.getCell("relateTask").setValue(taskInfo.getRelatedTask());
					}
					row.getCell("project").setValue(taskInfo.getProject());
					row.getCell("taskOrigin").setValue(taskInfo.getTaskOrigin());
					// row.getCell("id").setValue(taskInfo.getId());
					// �����ֶμƻ���ʼ���� ��������Դ������
					row.getCell("planStartDate").setValue(taskInfo.getPlanStartDate());
					if (taskInfo.getComplete() != null) {
						row.getCell("complete").setValue(taskInfo.getComplete());
					}
					if (null != row.getCell("planStartDate").getValue() && null != row.getCell("planFinishDate").getValue()) {
						int projectPeriod = FDCDateHelper.getDiffDays((Date) row.getCell("planStartDate").getValue(), (Date) row.getCell("planFinishDate").getValue());
						row.getCell("projectPeriod").setValue(String.valueOf(projectPeriod));
					}
					row.getCell("requiredResource").setValue(taskInfo.getRequiredResource());
					row.getCell("project").getStyleAttributes().setLocked(true);
					row.getCell("taskType").getStyleAttributes().setLocked(true);
					row.getCell("taskOrigin").getStyleAttributes().setLocked(true);
					row.getCell("taskOrigin").setUserObject(taskInfo.getId());
				}

			}

		}

		// ����Ȩ�ض�̬����
		// setWeightRateTotal();
		calcProjectPeriod();
		setWeightRateTotal();
	}

	/**
	 * 
	 * @description ��ѯ���ݣ����
	 * @author ���
	 * @createDate 2011-8-20 void
	 * @version EAS7.0
	 * @see
	 */
	public void exeQueryList() {

		//BT737787�ڱ�׼��Ʒ�У�ѡ���źͼƻ��·ݣ����������¶ȼƻ����������Ŀ�ƻ����룬���棻
		if (editData != null) {
			this.kdtTasks.removeRows();
			for (int i = 0; i < editData.getTasks().size(); i++) {
				IRow row = this.kdtTasks.addRow(i);
				DeptMonthlyScheduleTaskInfo taskInfo = (DeptMonthlyScheduleTaskInfo) editData.getTasks().get(i);
				if(taskInfo.getTaskType() != null){					
					row.getCell("taskType").setValue(taskInfo.getTaskType());
				}
				if(taskInfo.getTaskName() != null){					
					row.getCell("taskName").setValue(taskInfo.getTaskName());
				}
				if(taskInfo.getAdminPerson() != null){					
					row.getCell("adminPerson").setValue(taskInfo.getAdminPerson());
				}
				if(taskInfo.getFinishStandard() != null){					
					row.getCell("finishStandard").setValue(taskInfo.getFinishStandard());
				}
				if(taskInfo.getPlanFinishDate() != null){					
					row.getCell("planFinishDate").setValue(taskInfo.getPlanFinishDate());
				}
				if(taskInfo.getWeightRate() != null){					
					row.getCell("weightRate").setValue(taskInfo.getWeightRate());
				}
				if(null != taskInfo.getRelatedTask()) {
					row.getCell("relateTask").setValue(taskInfo.getRelatedTask());		
					row.getCell("project").setValue(taskInfo.getRelatedTask().getSchedule().getProject());					
				}
				if(taskInfo.getTaskOrigin() != null){					
					row.getCell("taskOrigin").setValue(taskInfo.getTaskOrigin());
				}
				if(taskInfo.getComplete() != null) {
					row.getCell("complete").setValue(taskInfo.getComplete());
				}
				if(taskInfo.getId() != null){					
					row.getCell("id").setValue(taskInfo.getId());
				}
				// �����ֶμƻ���ʼ���� ��������Դ������
				if(taskInfo.getPlanStartDate() != null){					
					row.getCell("planStartDate").setValue(taskInfo.getPlanStartDate());
				}
				if (row.getCell("planStartDate").getValue() != null && row.getCell("planFinishDate").getValue() != null) {
					int projectPeriod = FDCDateHelper.getDiffDays((Date) row.getCell("planStartDate").getValue(), (Date) row.getCell("planFinishDate").getValue());
					row.getCell("projectPeriod").setValue(String.valueOf(projectPeriod));
				}
				if(taskInfo.getRequiredResource() != null){					
					row.getCell("requiredResource").setValue(taskInfo.getRequiredResource());
				}

			}
		}
		this.comboState.setSelectedItem(editData.getState());
		if (comboState.getSelectedItem() != null) {
			if (comboState.getSelectedItem().equals(FDCBillStateEnum.SAVED)) {
				this.btnEdit.setEnabled(true);

			}
			if (comboState.getSelectedItem().equals(FDCBillStateEnum.AUDITTED)) {
				this.btnEdit.setEnabled(false);
				this.btnSubmit.setEnabled(false);
				// setOprtState(OprtState.VIEW);

			}
			if (comboState.getSelectedItem().equals(FDCBillStateEnum.SUBMITTED)) {
				this.btnEdit.setEnabled(true);
				this.btnAudit.setVisible(true);
				this.btnSubmit.setEnabled(false);
				// setOprtState(OprtState.VIEW);
			}
		}
		// ����Ȩ�ض�̬����
		setWeightRateTotal();
		calcProjectPeriod();
		setLockByCell();
	}

	/**
	 * 
	 * @description ��ѯ���ݣ����
	 * @author ���
	 * @createDate 2011-8-20 void
	 * @version EAS7.0
	 * @see
	 */
	public void exeQueryList2(DeptMonthlyScheduleInfo info2) {

		DeptMonthlyScheduleInfo info = info2;
		info.setAdminDept(SysContext.getSysContext().getCurrentAdminUnit());
		removeEntry();
		// ������F7����
		Date date = new Date();
		if (null != this.prmtAdminDept.getValue()) {
			info.setAdminDept((AdminOrgUnitInfo) this.prmtAdminDept.getValue());
		}
		Calendar cal = Calendar.getInstance();
		Calendar pkCal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(pkCal.get(Calendar.YEAR), pkCal.get(Calendar.MONTH) + 1, 1, 0, 0, 0);
		info.setScheduleMonth(cal.getTime());
		// ���ݲ��ţ��ƻ��·ݻ�ȡ��Ӧ�Ĳ����¶ȼƻ�
		DeptMonthlyScheduleInfo exsitInfo = getDeptMonthlyScheduleInfo(info.getAdminDept(), cal);

		if (exsitInfo != null) {
			editData.setId(exsitInfo.getId());
			this.comboState.setSelectedItem(exsitInfo.getState());
		} else {
			editData.setState(FDCBillStateEnum.SAVED);
			this.comboState.setSelectedItem(FDCBillStateEnum.SAVED);
			editData.clear();
		}
		if (exsitInfo != null) {
			info = exsitInfo;
			for (int i = 0; i < info.getTasks().size(); i++) {

				IRow row = this.kdtTasks.addRow(i);
				DeptMonthlyScheduleTaskInfo taskInfo = (DeptMonthlyScheduleTaskInfo) info.getTasks().get(i);
				row.getCell("taskType").setValue(taskInfo.getTaskType());
				row.getCell("taskName").setValue(taskInfo.getTaskName());
				row.getCell("adminPerson").setValue(taskInfo.getAdminPerson());
				row.getCell("finishStandard").setValue(taskInfo.getFinishStandard());
				row.getCell("planFinishDate").setValue(taskInfo.getPlanFinishDate());
				row.getCell("weightRate").setValue(taskInfo.getWeightRate());
				if (null != taskInfo.getRelatedTask()) {
					row.getCell("relateTask").setValue(taskInfo.getRelatedTask());
				}
				row.getCell("project").setValue(taskInfo.getProject());
				row.getCell("taskOrigin").setValue(taskInfo.getTaskOrigin());
				if (taskInfo.getComplete() != null) {
					row.getCell("complete").setValue(taskInfo.getComplete());
				}
				row.getCell("id").setValue(taskInfo.getId());
				// �����ֶμƻ���ʼ���� ��������Դ������
				row.getCell("planStartDate").setValue(taskInfo.getPlanStartDate());
				if (row.getCell("planStartDate").getValue() != null && row.getCell("planFinishDate").getValue() != null) {
					int projectPeriod = FDCDateHelper.getDiffDays((Date) row.getCell("planStartDate").getValue(), (Date) row
							.getCell("planFinishDate").getValue());
					row.getCell("projectPeriod").setValue(String.valueOf(projectPeriod));
				}

				row.getCell("requiredResource").setValue(taskInfo.getRequiredResource());

			}
		}
		if (comboState.getSelectedItem() != null) {
			if (comboState.getSelectedItem().equals(FDCBillStateEnum.SAVED)) {
				this.btnEdit.setEnabled(true);

			}
			if (comboState.getSelectedItem().equals(FDCBillStateEnum.AUDITTED)) {
				this.btnEdit.setEnabled(false);
				this.btnSubmit.setEnabled(false);
				// setOprtState(OprtState.VIEW);

			}
			if (comboState.getSelectedItem().equals(FDCBillStateEnum.SUBMITTED)) {
				this.btnEdit.setEnabled(true);
				this.btnAudit.setVisible(true);
				this.btnSubmit.setEnabled(false);
				// setOprtState(OprtState.VIEW);
			}
		}
		// ����Ȩ�ض�̬����
		setWeightRateTotal();
		calcProjectPeriod();
		setLockByCell();
	}

	/**
	 * 
	 * @description ɾ����¼��
	 * @author ���
	 * @createDate 2011-8-20 void
	 * @version EAS7.0
	 * @see
	 */
	private void removeEntry() {
		for (int i = kdtTasks.getRowCount() - 1; i >= 0; i--) {
			kdtTasks.removeRow(i);
		}
	}

	/**
	 * 
	 * @description ˫����¼�򿪶�Ӧ����
	 * @author ���
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */
	protected void kdtTasks_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		
		super.kdtTasks_tableClicked(e);
		int selcount = kdtTasks.getSelectManager().getActiveRowIndex();
		if (selcount < 0) {
			return;
		}
		// ������ר����������޸�������
		Object taskvalue = kdtTasks.getRow(selcount).getCell("taskOrigin").getValue();
		if (taskvalue != null) {
			String taskcomeorgin = taskvalue.toString();
			if (!(taskcomeorgin.equals("�ֹ�¼��"))) {
				kdtTasks.getRow(selcount).getCell("taskName").getStyleAttributes().setLocked(true);
				kdtTasks.getRow(selcount).getCell("planFinishDate").getStyleAttributes().setLocked(true);
				kdtTasks.getRow(selcount).getCell("planStartDate").getStyleAttributes().setLocked(true);
				kdtTasks.getRow(selcount).getCell("projectPeriod").getStyleAttributes().setLocked(true);
			}
		}
		Object obj = kdtTasks.getRow(selcount).getCell("relateTask").getValue();
		if (obj != null && obj instanceof FDCScheduleTaskInfo) {
			prerelateTask = (FDCScheduleTaskInfo) obj;
			setRelateTask();
		}
		if(obj != null && obj instanceof Object[]){
			Object[] bx = (Object[]) kdtTasks.getRow(selcount).getCell("relateTask").getValue();
			if (bx.length > 0) {
				kdtTasks.getRow(selcount).getCell("relateTask").setValue(bx[0]);
				prerelateTask = (FDCScheduleTaskInfo) bx[0];
				setRelateTask();
			}
		}
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			// ���е�Ԫ�����޸ĵ���������ҳ��
			// if (this.kdtTasks.getStyleAttributes().isLocked() == true) {
			String colKey = kdtTasks.getColumn(e.getColIndex()).getKey();
			// if (STATUS_ADDNEW.equals(getOprtState())) {
			// setOprtState("VIEW");
			// this.btnEdit.setEnabled(false);
			// return;
			// }
			if ("VIEW".equals(getOprtState())) {
				actionOpenUI(getOprtState());
				return;
			}
			if ("taskType".equals(colKey) || "project".equals(colKey) || "taskOrigin".equals(colKey)) {
				// actionOpenUI(getOprtState());
				int rowIndex = kdtTasks.getSelectManager().getActiveRowIndex();
				if (rowIndex == -1) {
					MsgBox.showInfo("��ѡ��һ��!");
					SysUtil.abort();
				}
				DeptMonthlyScheduleTaskInfo info = getSelectedInfo();
				Object infoId = kdtTasks.getRow(rowIndex).getCell("id").getValue();
				// ��������Ŀ����༭������ wwq
				IRow row = kdtTasks.getRow(rowIndex);
				/**
				 * ������ĿΪ����ȫ�ֱ�������ȥȡ��
				 */
				Object project = null;
				// Object project = row.getCell("project").getValue();
				if (prerelateTask != null && prerelateTask.getSchedule() != null && prerelateTask.getSchedule().getProject() != null) {
					project = prerelateTask.getSchedule().getProject();
				}
				
				
				if (row.getCell("project").getValue() != null && row.getCell("project").getValue() instanceof CurProjectInfo) {

				}
				UIContext uiContext = new UIContext(this);
				if (project != null) {
					uiContext.put("selectProject", project.toString());
				}
				uiContext.put("DeptMonthlyScheduleTaskInfo", info);
				/**
				 * ����˫��������޸�
				 */
				uiContext.put("par", editData);
				Object total = kdtTasks.getFootRow(0).getCell("weightRate").getValue();
				uiContext.put("total", total);
				if (infoId != null) {
					
					int activeRowIndex = kdtTasks.getSelectManager().getActiveRowIndex();
					// new ���������ڴ���Ѿ�д�õ���Ϣ
					DeptMonthlyScheduleTaskInfo transinfo = new DeptMonthlyScheduleTaskInfo();
					// �������
					if (kdtTasks.getRow(activeRowIndex).getCell("taskType").getValue() != null) {
						transinfo.setTaskType((RESchTaskTypeEnum) kdtTasks.getRow(activeRowIndex).getCell("taskType").getValue());
					}
					// ��������
					if (kdtTasks.getRow(activeRowIndex).getCell("taskName").getValue() != null) {
						transinfo.setTaskName(kdtTasks.getRow(activeRowIndex).getCell("taskName").getValue().toString());
					}
					// ������
					if (kdtTasks.getRow(activeRowIndex).getCell("adminPerson").getValue() != null) {
						transinfo.setAdminPerson((PersonInfo) kdtTasks.getRow(activeRowIndex).getCell("adminPerson").getValue());
					}
					// Ȩ��
					if (kdtTasks.getRow(activeRowIndex).getCell("weightRate").getValue() != null) {
						transinfo.setWeightRate(new BigDecimal(kdtTasks.getRow(activeRowIndex).getCell("weightRate").getValue().toString()));
					}
					// ��ɱ�׼
					if (kdtTasks.getRow(activeRowIndex).getCell("finishStandard").getValue() != null) {
						transinfo.setFinishStandard(kdtTasks.getRow(activeRowIndex).getCell("finishStandard").getValue().toString());
					}
					// �ƻ��������
					if (kdtTasks.getRow(activeRowIndex).getCell("planFinishDate").getValue() != null) {
						transinfo.setPlanFinishDate((Date) kdtTasks.getRow(activeRowIndex).getCell("planFinishDate").getValue());
					}
					// �������
					if (kdtTasks.getRow(activeRowIndex).getCell("relateTask").getValue() != null) {
						transinfo.setRelatedTask((FDCScheduleTaskInfo) kdtTasks.getRow(activeRowIndex).getCell("relateTask").getValue());
					}
					// ������Ŀ
					if (kdtTasks.getRow(activeRowIndex).getCell("project").getValue() != null) {
						transinfo.setProject((CurProjectInfo) kdtTasks.getRow(activeRowIndex).getCell("project").getValue());
					}
					// ������Դ
					if (kdtTasks.getRow(activeRowIndex).getCell("taskOrigin").getValue() != null) {
						transinfo.setTaskOrigin((RESchTaskOriginEnum) kdtTasks.getRow(activeRowIndex).getCell("taskOrigin").getValue());
					}
					// ����
					if (kdtTasks.getRow(activeRowIndex).getCell("projectPeriod").getValue() != null) {
						transinfo.setProjectPeriod(new BigDecimal(kdtTasks.getRow(activeRowIndex).getCell("projectPeriod").getValue().toString()));
					}
					// �ƻ���ʼ����
					if (kdtTasks.getRow(activeRowIndex).getCell("planStartDate").getValue() != null) {
						transinfo.setPlanStartDate((Date) kdtTasks.getRow(activeRowIndex).getCell("planStartDate").getValue());
					}
					// ������Դ
					if (kdtTasks.getRow(activeRowIndex).getCell("requiredResource").getValue() != null) {
						transinfo.setRequiredResource((kdtTasks.getRow(activeRowIndex).getCell("requiredResource").getValue().toString()));
					}
					// ��ɽ���
					if (kdtTasks.getRow(activeRowIndex).getCell("complete").getValue() != null) {
						transinfo.setComplete(new BigDecimal(kdtTasks.getRow(activeRowIndex).getCell("complete").getValue().toString()));
					}
					uiContext.put(UIContext.ID, infoId.toString());
					// ������Ŀ���ź�����
					AdminOrgUnitInfo adinfo = (AdminOrgUnitInfo) prmtAdminDept.getValue();
					Date datex = (Date) pkScheduleMonth.getValue();
					uiContext.put("adinfo", adinfo);
					uiContext.put("datex", datex);
					uiContext.put("transinfo", transinfo);
					uiContext.put("rowIndex2", new String(activeRowIndex + ""));
					IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
					IUIWindow uiWindow = null;
					uiWindow = uiFactory.create(DeptMonthlyScheduleTaskEditUI.class.getName(), uiContext, null, OprtState.EDIT);
					uiWindow.show();
				} else {
					int activeRowIndex = kdtTasks.getSelectManager().getActiveRowIndex();
					// new ���������ڴ���Ѿ�д�õ���Ϣ
					DeptMonthlyScheduleTaskInfo transinfo = new DeptMonthlyScheduleTaskInfo();
					// �������
					if (kdtTasks.getRow(activeRowIndex).getCell("taskType").getValue() != null) {
						transinfo.setTaskType((RESchTaskTypeEnum) kdtTasks.getRow(activeRowIndex).getCell("taskType").getValue());
					}
					// ��������
					if (kdtTasks.getRow(activeRowIndex).getCell("taskName").getValue() != null) {
						transinfo.setTaskName(kdtTasks.getRow(activeRowIndex).getCell("taskName").getValue().toString());
					}
					// ������
					if (kdtTasks.getRow(activeRowIndex).getCell("adminPerson").getValue() != null) {
						transinfo.setAdminPerson((PersonInfo) kdtTasks.getRow(activeRowIndex).getCell("adminPerson").getValue());
					}
					 // Ȩ��
					if (kdtTasks.getRow(activeRowIndex).getCell("weightRate").getValue() != null) {
						transinfo.setWeightRate(new BigDecimal(kdtTasks.getRow(activeRowIndex).getCell("weightRate").getValue().toString()));
					}
					 // ��ɱ�׼
					if (kdtTasks.getRow(activeRowIndex).getCell("finishStandard").getValue() != null) {
						transinfo.setFinishStandard(kdtTasks.getRow(activeRowIndex).getCell("finishStandard").getValue().toString());
					}
					// �ƻ��������
					if (kdtTasks.getRow(activeRowIndex).getCell("planFinishDate").getValue() != null) {
						transinfo.setPlanFinishDate((Date) kdtTasks.getRow(activeRowIndex).getCell("planFinishDate").getValue());
					}
					// �������
					if (kdtTasks.getRow(activeRowIndex).getCell("relateTask").getValue() != null) {
						transinfo.setRelatedTask((FDCScheduleTaskInfo) kdtTasks.getRow(activeRowIndex).getCell("relateTask").getValue());
					}
					// ������Ŀ
					if (kdtTasks.getRow(activeRowIndex).getCell("project").getValue() != null) {
						transinfo.setProject((CurProjectInfo) kdtTasks.getRow(activeRowIndex).getCell("project").getValue());
					}
					// ������Դ
					if (kdtTasks.getRow(activeRowIndex).getCell("taskOrigin").getValue() != null) {
						transinfo.setTaskOrigin((RESchTaskOriginEnum) kdtTasks.getRow(activeRowIndex).getCell("taskOrigin").getValue());
					}
					// ����
					if (kdtTasks.getRow(activeRowIndex).getCell("projectPeriod").getValue() != null) {
						transinfo.setProjectPeriod(new BigDecimal(kdtTasks.getRow(activeRowIndex).getCell("projectPeriod").getValue().toString()));
					}
					// �ƻ���ʼ����
					if (kdtTasks.getRow(activeRowIndex).getCell("planStartDate").getValue() != null) {
						transinfo.setPlanStartDate((Date) kdtTasks.getRow(activeRowIndex).getCell("planStartDate").getValue());
					}
					// ������Դ
					if (kdtTasks.getRow(activeRowIndex).getCell("requiredResource").getValue() != null) {
						transinfo.setRequiredResource((kdtTasks.getRow(activeRowIndex).getCell("requiredResource").getValue().toString()));
					}
					// ��ɽ���
					if (kdtTasks.getRow(activeRowIndex).getCell("complete").getValue() != null) {
						transinfo.setComplete(new BigDecimal(kdtTasks.getRow(activeRowIndex).getCell("complete").getValue().toString()));
					}
					
					IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
					UIContext ui = new UIContext();
					// ������Ŀ���ź�����
					AdminOrgUnitInfo adinfo = (AdminOrgUnitInfo) prmtAdminDept.getValue();
					Date datex = (Date) pkScheduleMonth.getValue();
					ui.put("adinfo", adinfo);
					if (project != null) {
						ui.put("selectProject", project.toString());
					}
					
					ui.put("datex", datex);
					ui.put("Owner", this);
					ui.put("par", editData);
					ui.put("transinfo", transinfo);
					ui.put("rowIndex2", new String(activeRowIndex + ""));
					IUIWindow uiWindow = null;
					uiWindow = uiFactory.create(DeptMonthlyScheduleTaskEditUI.class.getName(), ui, null, OprtState.ADDNEW);
					uiWindow.show();
				}
			}
		}
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return DeptMonthlyScheduleFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kdtTasks;
	}

	protected KDTextField getNumberCtrl() {
		return new KDTextField();
	}

	protected IObjectValue createNewData() {
		// ��ȡ��ǰ���ţ��ͼƻ��·�
		DeptMonthlyScheduleInfo info = new DeptMonthlyScheduleInfo();
		info.setAdminDept(SysContext.getSysContext().getCurrentAdminUnit());
		Date date = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
		info.setScheduleMonth(cal.getTime());
		if (null != this.prmtAdminDept.getValue()) {
			 info.setAdminDept((AdminOrgUnitInfo) this.prmtAdminDept.getValue());
		}
		// else {
		// pkScheduleMonth.setValue(new Date());
		// }
		// ��ѯ���ݿ�����û�иò��ŵ�ǰ�·ݵĲ����¶ȼƻ�
		if (!pkScheduleMonth.getValue().equals(cal.getTime())) {
			cal.setTime((Date) pkScheduleMonth.getValue());
		}
		DeptMonthlyScheduleInfo exsitInfo = getDeptMonthlyScheduleInfo(info.getAdminDept(), cal);
		if (exsitInfo != null) {
			info = exsitInfo;
			// ���������ݺ�����Ϊ�鿴״̬
			// setOprtState(OprtState.VIEW);
		}
		return info;
	}
	
	

	/**
	 * �󶨵Ŀؼ���У��
	 * 
	 * @param
	 * 
	 * @return
	 */
	private void checkEntryIsNull() {

		checkEntry();
		int num = this.kdtTasks.getRowCount();

		if (num > 0) {
			for (int i = 0; i < this.kdtTasks.getRowCount(); i++) {
				IRow row = this.kdtTasks.getRow(i);
				if (null == row.getCell("taskName").getValue()) {
					MsgBox.showWarning(this, "��" + (i + 1) + "���������Ʋ���Ϊ�գ�");
					SysUtil.abort();
					break;
				}
				if (null == row.getCell("adminPerson").getValue()) {
					MsgBox.showWarning(this, "��" + (i + 1) + "�������˲���Ϊ�գ�");
					SysUtil.abort();
					break;
				}
				if (null == row.getCell("adminPerson").getValue()) {
					MsgBox.showWarning(this, "��" + (i + 1) + "�������˲���Ϊ�գ�");
					SysUtil.abort();
					break;
				}
				if (null == row.getCell("planFinishDate").getValue()) {
					FDCMsgBox.showWarning(this, "��" + (i + 1) + "�мƻ����ڲ���Ϊ�գ���");
					SysUtil.abort();
					setLockCanEdit();
				}
				if (row.getCell("weightRate").getValue() == null) {
					MsgBox.showWarning(this, "��" + (i + 1) + "��Ȩ�ز���Ϊ�գ�");
					SysUtil.abort();
					break;
				}
			}
		}

	}

	/**
	 * ���ݲ��ţ��ƻ��·ݻ�ȡ��Ӧ�Ĳ����¶ȼƻ�
	 * 
	 * @param info
	 * @param cal
	 * @return DeptMonthlyScheduleInfo: Ϊnullʱ��ʾ�ò��ţ��·���û�б��ƶ�Ӧ�ļƻ�
	 */
	private DeptMonthlyScheduleInfo getDeptMonthlyScheduleInfo(AdminOrgUnitInfo adminDept, Calendar cal) {
		DeptMonthlyScheduleInfo info = null;
		FilterInfo filter = new FilterInfo();
		if (adminDept != null) {
			filter.getFilterItems().add(new FilterItemInfo("adminDept.id", adminDept.getId()));
		}

		filter.getFilterItems().add(new FilterItemInfo("year", new Integer(cal.get(Calendar.YEAR)), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("month", new Integer(cal.get(Calendar.MONTH)) + 1, CompareType.EQUALS));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sic = new SorterItemCollection();
		sic.add(new SorterItemInfo("tasks.planFinishDate"));
		view.setSelector(getSelectors());
		view.setSorter(sic);
		try {
			if (DeptMonthlyScheduleFactory.getRemoteInstance().exists(filter)) {
				DeptMonthlyScheduleCollection coll = DeptMonthlyScheduleFactory.getRemoteInstance().getDeptMonthlyScheduleCollection(view);
				info = coll.get(0);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * ��������δ���
	 * 
	 * @description
	 * @author ���
	 * @createDate 2011-11-6
	 * @param adminDept
	 * @param cal
	 * @return DeptMonthlyScheduleInfo
	 * @version EAS7.0
	 * @see
	 */
	private DeptMonthlyScheduleInfo getDeptUpMonthlyScheduleInfo(AdminOrgUnitInfo adminDept, Calendar cal) {
		DeptMonthlyScheduleInfo info = null;
		FilterInfo filter = new FilterInfo();
		Date selectDate = cal.getTime();
		Date addMonth = DateUtils.addMonth(selectDate, -1);
		cal.setTime(addMonth);
		if (adminDept != null) {
			filter.getFilterItems().add(new FilterItemInfo("adminDept.id", adminDept.getId()));
		}
		filter.getFilterItems().add(new FilterItemInfo("year", new Integer(cal.get(Calendar.YEAR)), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("month", new Integer(cal.get(Calendar.MONTH) + 1), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED, CompareType.EQUALS));
		 // --��ɽ��� ����
		// filter.getFilterItems().add(new FilterItemInfo("tasks.complete", new
		// BigDecimal(100), CompareType.LESS));
		// filter.getFilterItems().add(new FilterItemInfo("tasks.complete",
		// null, CompareType.EQUALS));
		// filter.setMaskString("#0 and #1 and #2 and #3 (#4 or #5)");
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sic = new SorterItemCollection();
		sic.add(new SorterItemInfo("tasks.planFinishDate"));
		view.setSelector(getSelectors());
		view.setSorter(sic);
		try {
				DeptMonthlyScheduleCollection coll = DeptMonthlyScheduleFactory.getRemoteInstance().getDeptMonthlyScheduleCollection(view);
				if (!coll.isEmpty())
				info = coll.get(0);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}
	
	private DeptMonthlyScheduleTaskCollection getDeptUpMonthlyScheduleInfo2(AdminOrgUnitInfo adminDept, Calendar cal) {
		FilterInfo filter = new FilterInfo();
		if (adminDept != null) {
			filter.getFilterItems().add(new FilterItemInfo("schedule.adminDept.id", adminDept.getId()));
		}
		filter.getFilterItems().add(new FilterItemInfo("schedule.year", new Integer(cal.get(Calendar.YEAR)), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("schedule.month", new Integer(cal.get(Calendar.MONTH)), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("schedule.state", FDCBillStateEnum.AUDITTED, CompareType.EQUALS));
		 // --��ɽ��� ����
		filter.getFilterItems().add(new FilterItemInfo("complete", new BigDecimal(100), CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("complete", null, CompareType.EQUALS));
		filter.setMaskString("#0 and #1 and #2 and #3 (#4 or #5)");
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sic = new SorterItemCollection();
		sic.add(new SorterItemInfo("tasks.planFinishDate"));
		view.setSelector(getSelectors());
		view.setSorter(sic);
		DeptMonthlyScheduleTaskCollection coll = null ;
		try {
			if (DeptMonthlyScheduleTaskFactory.getRemoteInstance().exists(filter)) {
				coll = DeptMonthlyScheduleTaskFactory.getRemoteInstance().getDeptMonthlyScheduleTaskCollection(view);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return coll;
	}
	
	private DeptMonthlyScheduleInfo getSaveInfo(DeptMonthlyScheduleInfo info) {
		
		info.setAdminDept((AdminOrgUnitInfo) prmtAdminDept.getValue());
		Date scheduleMonth = (Date) pkScheduleMonth.getValue();
        
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(scheduleMonth);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;

		info.setNumber(info.getAdminDept().getName() + year + "��" + month + "�²����¶ȼƻ�");
		info.setScheduleMonth(calendar.getTime());
		info.setYear(year);
		info.setMonth(month);

		info.getTasks().clear();
		DeptMonthlyScheduleTaskInfo taskInfo = null;
		for (int i = 0; i < kdtTasks.getRowCount(); i++) {
			taskInfo = new DeptMonthlyScheduleTaskInfo();
			taskInfo.setSeq(i + 1);
			taskInfo.setSchedule(info);
			taskInfo.setAdminPerson((PersonInfo) kdtTasks.getCell(i, "adminPerson").getValue());
			taskInfo.setTaskType((RESchTaskTypeEnum) kdtTasks.getCell(i, "taskType").getValue());
			if (kdtTasks.getCell(i, "taskName").getValue() != null) {
				taskInfo.setTaskName(kdtTasks.getCell(i, "taskName").getValue().toString());
			}
			if (kdtTasks.getCell(i, "finishStandard").getValue() != null) {
				taskInfo.setFinishStandard(kdtTasks.getCell(i, "finishStandard").getValue().toString());
			}
			if (kdtTasks.getCell(i, "planFinishDate").getValue() != null) {
				taskInfo.setPlanFinishDate((Date) kdtTasks.getCell(i, "planFinishDate").getValue());
			}
			if (kdtTasks.getCell(i, "planStartDate").getValue() != null) {
				taskInfo.setPlanStartDate((Date) kdtTasks.getCell(i, "planStartDate").getValue());
			}
			if (kdtTasks.getCell(i, "weightRate").getValue() != null) {
				taskInfo.setWeightRate(new BigDecimal(kdtTasks.getCell(i, "weightRate").getValue().toString()));
			}
			if (kdtTasks.getCell(i, "relateTask").getValue() != null) {
					taskInfo.setRelatedTask((FDCScheduleTaskInfo) kdtTasks.getCell(i, "relateTask").getValue());
			}
			if (kdtTasks.getCell(i, "project").getValue() != null) {
				taskInfo.setProject((CurProjectInfo) kdtTasks.getCell(i, "project").getValue());
			}
			if (kdtTasks.getCell(i, "taskOrigin").getValue() != null) {
				taskInfo.setTaskOrigin((RESchTaskOriginEnum) kdtTasks.getCell(i, "taskOrigin").getValue());
			}
			if (kdtTasks.getCell(i, "projectPeriod").getValue() != null) {
				taskInfo.setProjectPeriod(new BigDecimal(kdtTasks.getCell(i, "projectPeriod").getValue().toString()));
			}
			if (kdtTasks.getCell(i, "id").getValue() != null) {
				taskInfo.setId((BOSUuid) kdtTasks.getCell(i, "id").getValue());
			}
			if (kdtTasks.getCell(i, "requiredResource").getValue() != null) {
				taskInfo.setRequiredResource(kdtTasks.getCell(i, "requiredResource").getValue().toString());
			}
			if (kdtTasks.getCell(i, "complete").getValue() != null) {
				taskInfo.setComplete(new BigDecimal(kdtTasks.getCell(i, "complete").getValue().toString()));
			}
            info.getTasks().add(taskInfo);
		}
		
		return info;
	}

	/**
	 * Ч�������Ƿ���ȷ,���ݲ��ţ����汾����Ӧ�Ĳ����¶ȼƻ�
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkIsCanSave(DeptMonthlyScheduleInfo dmsInfo) throws EASBizException, BOSException {
		IRow row = null;
		int err = 0;
		StringBuffer str = new StringBuffer("��¼�������´������޸ĺ��ٽ����ύ ��");
		for (int i = 0; i < kdtTasks.getRowCount(); i++) {
			row = kdtTasks.getRow(i);
			for (int j = 0; j < kdtTasks.getColumnCount(); j++) {
				if (kdtTasks.getColumn(j).isRequired()) {
					if (row.getCell(j).getValue() == null) {
						str.append("��");
						str.append(i + 1);
						str.append("��");
						str.append(kdtTasks.getHeadRow(0).getCell(j).getValue());
						str.append("����Ϊ��\n");
						err++;
					}

				}
			}

		}
		
		if (err > 1) {
			FDCMsgBox.showDetailAndOK(this, "����������鿴��ϸ��Ϣ��", str.toString(), AdvMsgBox.ERROR_MESSAGE);
			abort();
		}
	}

	/**
	 * �Է�¼���м�飬������ڷ�¼��û������----ֱ��ɾ��
	 */
	private void checkEntry() {

		int num = this.kdtTasks.getRowCount();

		if (num > 0) {

			List numList = new ArrayList();

			for (int i = num - 1; i >= 0; i--) {
				IRow row = this.kdtTasks.getRow(i);
				if (this.kdtTasks.getRow(i) != null) {
					if (row.getCell("taskType").getValue() == null && row.getCell("taskName").getValue() == null && row.getCell("adminPerson").getValue() == null
							&& row.getCell("finishStandard").getValue() == null && row.getCell("weightRate").getValue() == null && row.getCell("planFinishDate").getValue() == null
							&& row.getCell("taskOrigin").getValue() == null && row.getCell("project").getValue() == null && row.getCell("relateTask").getValue() == null) {
						this.kdtTasks.removeRow(i);
					}

				} else {
					this.kdtTasks.removeRow(i);
					numList.add(String.valueOf(i));
				}
			}
			// //Ϊ�˱������������
			if (numList.size() > 0) {
				for (int i = numList.size() - 1; i >= 0; i++) {
					int index = Integer.parseInt(numList.get(i).toString());
					this.kdtTasks.removeRow(index);
				}
			}
		}
	}
	
	public IObjectPK runSave() throws Exception {
		if (UtilRequest.isPrepare("ActionSave", this)) {// TODO �˴��Ժ�Ҫ�޸�
			Object pk = ActionCache.get(ResponseConstant.FRAMEWORK_PK);
			if (pk != null) {
				return (IObjectPK) pk;
			}
		}
		return getBizInterface().save(editData);
	}

	/**
	 * ���沿���¼ƻ�����
	 * 
	 * @param adminPersonId
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		
		checkDateAndWeightRate();
		// getSaveInfo(editData);

		// ����ǰ���ԭ��������
		// deleteAgoNumber(info);
		// ����ScheDuleInfo
		// �����¼INFO
		checkIsCanSave(editData);
		editData.setState(FDCBillStateEnum.SAVED);
		super.actionSave_actionPerformed(e);
		// comboState.setSelectedItem(FDCBillStateEnum.SAVED);
		// ���½��ύ����ɹ�
		exeQueryList();
		this.btnInsertLine.setEnabled(false);
		this.btnRemoveLine.setEnabled(false);
		this.importProjectPlan.setEnabled(false);
		this.importUnFinishTask.setEnabled(false);
		this.btnSubmit.setEnabled(true);
		this.btnEdit.setEnabled(true);
		 // Ϊ�˱�������ڱ��棬��¼�İ�ť���Կ���
		setOprtState(OprtState.EDIT);
		this.btnSave.setVisible(true);
		this.btnSave.setEnabled(true);
		// ��¼���ĸ���ť����
		this.btnInsertLine.setEnabled(true);
		this.btnRemoveLine.setEnabled(true);
		this.importProjectPlan.setEnabled(true);
		this.importUnFinishTask.setEnabled(true);
		kdtTasks.getColumn("taskName").getStyleAttributes().setLocked(false);
		kdtTasks.getColumn("adminPerson").getStyleAttributes().setLocked(false);
		kdtTasks.getColumn("finishStandard").getStyleAttributes().setLocked(false);
		kdtTasks.getColumn("weightRate").getStyleAttributes().setLocked(false);
		kdtTasks.getColumn("relateTask").getStyleAttributes().setLocked(false);
		
		// ����������ͷ��������Բ�����
		this.btnAudit.setVisible(true);
		this.btnUnAudit.setVisible(true);
		setOprtState(OprtState.EDIT);
		if (btnEdit.isEnabled()) {
			btnEdit.setEnabled(false);
		}
		//���´���Ϊ��bug���
		//BT737776bug��ϵͳ����ɹ�����������Ϊ�գ���Ҫ����ѡ��ƻ��·ݣ����ݲų�����
		//Ҫ�󣺱���󣬽��治�������
		AdminOrgUnitInfo adminDept = (AdminOrgUnitInfo) prmtAdminDept.getValue();
		if (adminDept == null) {
			adminDept = SysContext.getSysContext().getCurrentAdminUnit();
		}
//		Date selectDate = (Date) e.getNewValue();
		Date selectDate = (Date) this.pkScheduleMonth.getValue();
		EntityViewInfo view = new EntityViewInfo();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(selectDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("adminDept.id", adminDept.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("year", year));
		filter.getFilterItems().add(new FilterItemInfo("month", month));
		view.setFilter(filter);
		view.setSelector(getSelectors());

		DeptMonthlyScheduleCollection cols = DeptMonthlyScheduleFactory.getRemoteInstance().getDeptMonthlyScheduleCollection(view);
		if (!cols.isEmpty()) {
			editData = cols.get(0);
		} else {
			editData = (DeptMonthlyScheduleInfo) createNewData();
		}
		exeQueryList();

	}

	/**
	 * 
	 * @description �趨���������Ŀ��������Դ��Ԫ���ܱ༭
	 * @author ���
	 * @createDate 2011-8-17 void
	 * @version EAS7.0
	 * @see
	 */
	private void setLockByCell() {
		for (int i = 0; i < kdtTasks.getRowCount(); i++) {

			this.kdtTasks.getCell(i, 0).getStyleAttributes().setLocked(true);
			this.kdtTasks.getCell(i, 7).getStyleAttributes().setLocked(true);
			this.kdtTasks.getCell(i, 8).getStyleAttributes().setLocked(true);
			this.kdtTasks.getCell(i, 0).getStyleAttributes().setFontColor(Color.GRAY);
			this.kdtTasks.getCell(i, 7).getStyleAttributes().setFontColor(Color.GRAY);
			this.kdtTasks.getCell(i, 8).getStyleAttributes().setFontColor(Color.GRAY);
		}
	}

	/**
	 * 
	 * @description �趨���������Ŀ��������Դ��Ԫ����Ա༭
	 * @author ���
	 * @createDate 2011-8-17 void
	 * @version EAS7.0
	 * @see
	 */
	private void setLockCanEdit() {
		for (int i = 0; i < kdtTasks.getRowCount(); i++) {

			this.kdtTasks.getCell(i, 0).getStyleAttributes().setLocked(false);
			this.kdtTasks.getCell(i, 7).getStyleAttributes().setLocked(false);
			this.kdtTasks.getCell(i, 8).getStyleAttributes().setLocked(false);
			this.kdtTasks.getCell(i, 0).getStyleAttributes().setFontColor(Color.GRAY);
			this.kdtTasks.getCell(i, 7).getStyleAttributes().setFontColor(Color.GRAY);
			this.kdtTasks.getCell(i, 8).getStyleAttributes().setFontColor(Color.GRAY);
		}
	}

	/**
	 * 
	 * @description ���������ֵ
	 * @author ���
	 * @createDate 2011-8-17
	 * @version EAS7.0
	 * @see
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("adminPerson.id"));
		sic.add(new SelectorItemInfo("adminPerson.name"));
		sic.add(new SelectorItemInfo("adminPerson.number"));
		sic.add(new SelectorItemInfo("tasks.adminPerson.id"));
		sic.add(new SelectorItemInfo("tasks.adminPerson.name"));
		sic.add(new SelectorItemInfo("tasks.adminPerson.number"));
		sic.add(new SelectorItemInfo("tasks.relatedTask.id"));
		sic.add(new SelectorItemInfo("tasks.relatedTask.name"));
		sic.add(new SelectorItemInfo("tasks.relatedTask.number"));
		sic.add(new SelectorItemInfo("tasks.relatedTask.schedule.project.id"));
		sic.add(new SelectorItemInfo("tasks.relatedTask.schedule.project.name"));
		sic.add(new SelectorItemInfo("tasks.relatedTask.schedule.project.number"));
		sic.add(new SelectorItemInfo("tasks.project.*"));
		sic.add(new SelectorItemInfo("tasks.project.id"));
		sic.add(new SelectorItemInfo("tasks.project.name"));
		sic.add(new SelectorItemInfo("tasks.project.number"));
		return sic;
	}

	/**
	 * 
	 * @description ������
	 * @author ���
	 * @createDate 2011-8-11
	 * @param
	 * @return
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableInsertLine(kdtTasks);
		this.btnSave.setVisible(true);
		this.btnSave.setEnabled(true);
		this.kdtTasks.getStyleAttributes().setLocked(false);
		// �������Ժ�,3�в����Ա༭
		// �������
		kdtTasks.getColumn("taskType").getStyleAttributes().setLocked(true);
		// ������Ŀ
		kdtTasks.getColumn("project").getStyleAttributes().setLocked(true);
		// ������Դ
		kdtTasks.getColumn("taskOrigin").getStyleAttributes().setLocked(true);
	}

	/**
	 * 
	 * @description ɾ����¼��
	 * @author ���
	 * @createDate 2011-8-11
	 * @param
	 * @return
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableDeleteRow(kdtTasks);
		setWeightRateTotal();
	}

	/**
	 * 
	 * @description ������
	 * @author ���
	 * @createDate 2011-8-11
	 * @param
	 * @return
	 */
	private void kdTableInsertLine(KDTable table) {
		if (table == null)
			return;
		IRow row = null;
		row = table.addRow();
		row.getCell("taskType").setValue(RESchTaskTypeEnum.NORMAL);
		row.getCell("taskType").getStyleAttributes().setLocked(true);
		row.getCell("taskOrigin").setValue(RESchTaskOriginEnum.INPUT);
		row.getCell("taskOrigin").getStyleAttributes().setLocked(true);
	}

	/**
	 * ����ģ��Excel
	 * 
	 * @description
	 * @author ���
	 * @createDate 2011-8-18
	 * @version EAS7.0
	 * @see
	 */

	public void actionExportData_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		List tables = new ArrayList();
		tables.add(new Object[] { "�����б�", kdtTasks });
		FDCTableHelper.exportExcel(this, tables);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData == null || editData.getId() == null) {
			MsgBox.showWarning(this, "���ύ���ٴ�ӡ!");
			SysUtil.abort();
		}
		ArrayList idList = new ArrayList();
		if (editData != null) {
			idList.add(editData);
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.basedata.client.FdcResource", "cantPrint"));
			return;
		}
		DeptMonthlyScheduleEditDataProvider dataPvd = new DeptMonthlyScheduleEditDataProvider(editData.getString("id"), getTDQueryPK());
		dataPvd.setBailId(editData.getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities.getWindowAncestor(this));

	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {

		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.basedata.client.FdcResource", "cantPrint"));
			return;

		}
		DeptMonthlyScheduleEditDataProvider dataPvd = new DeptMonthlyScheduleEditDataProvider(editData.getString("id"), getTDQueryPK());
		dataPvd.setBailId(editData.getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	protected String getTDFileName() {
		 return "/bim/fdc/process/deptMonthly";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.schedule.app.DeptMonthlyScheduleQuery");
	}

	/**
	 * 
	 * @description �������õ�,�ӻ����п�����
	 * @author ���
	 * @createDate 2011-8-11
	 * @param
	 * @return
	 */
	protected final boolean addColumn(KDTable table) {
		if (table.getSelectManager().size() > 0) {
			KDTSelectBlock block = table.getSelectManager().get();
			if (block.getMode() == 4 || block.getMode() == 8)
				return true;
		}
		return false;
	}

	private static boolean confirmRemove(Component comp) {
		return MsgBox.isYes(MsgBox.showConfirm2(comp, "�Ƿ�Ҫɾ����ѡ����"));
	}

	/**
	 * 
	 * @description ɾ����
	 * @author ���
	 * @createDate 2011-8-11
	 * @param
	 * @return
	 */
	private void kdTableDeleteRow(KDTable table) {
		kdtTasks.checkParsed();
		int m[] = KDTableUtil.getSelectedRows(kdtTasks);
		
		if (KDTableUtil.getSelectedRows(kdtTasks).length <= 0) {
			FDCMsgBox.showInfo("����ѡ���¼�У�");
			abort();
		}
		ObjectUuidPK[] pk = new ObjectUuidPK[m.length];
		if (confirmRemove(this)) {
			for (int i = pk.length - 1; i > -1; i--) {
				if (kdtTasks.getRow(m[i]).getCell("id").getValue() != null) {
					pk[i] = new ObjectUuidPK(kdtTasks.getRow(m[i]).getCell("id").getValue().toString());
					FDCSQLBuilder builder = new FDCSQLBuilder();
					builder.appendSql("delete from T_SCH_SelfAndFinalEvaluatEntry where FRelateTaskID = '" + pk[i] + "'");
					try {
						builder.execute();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}
				kdtTasks.removeRow(m[i]);
			}
			if (kdtTasks.getRowCount() == 0) {
				AdminOrgUnitInfo adminDept = (AdminOrgUnitInfo) prmtAdminDept.getValue();
				String scheduleMonth = this.pkScheduleMonth.getText();
				String yearStr = scheduleMonth.substring(0, scheduleMonth.indexOf("-"));
				String monthStr = scheduleMonth.substring(scheduleMonth.indexOf("-") + 1);
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
				sqlBuilder.appendSql("delete from T_SCH_SelfAndFinalEvaluation where FAdminDept = '" + adminDept.getId()
						+ "' and year(FscheduleMonth)= '"
						+ yearStr + "' and month(FscheduleMonth) = '" + monthStr + "'");
				try {
					sqlBuilder.execute();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			DeptMonthlyScheduleTaskFactory.getRemoteInstance().delete(pk);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	public String[] getSelectedListId() {
		checkSelected();

		// SelectManager ��kdtable���й�����
		ArrayList blocks = kdtTasks.getSelectManager().getBlocks();
		ArrayList idList = new ArrayList();
		Iterator iter = blocks.iterator();
		while (iter.hasNext()) {
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++) {
				ICell cell = kdtTasks.getRow(rowIndex).getCell(getKeyFieldName());
				if (!idList.contains(cell.getValue())) {
					idList.add(cell.getValue());
				}
			}
		}
		String[] listId = null;
		if (idList != null && idList.size() > 0) {
			Iterator iterat = idList.iterator();
			listId = new String[idList.size()];
			int index = 0;
			while (iterat.hasNext()) {
				listId[index] = iterat.next().toString();
				index++;
			}
		}
		return listId;
	}
	
	// �༭��б���ݵ���
	public void writeBacktoRow(DeptMonthlyScheduleTaskInfo info, int rowNumber) {
		IRow row = kdtTasks.getRow(rowNumber);
		// id
		if (info.getId() != null) {
			row.getCell("id").setValue(info.getId());
		}
		// Ȩ��
		if (info.getWeightRate() != null) {
			row.getCell("weightRate").setValue(new BigDecimal(info.getWeightRate().toString()));
		}
		// ��������
		if (info.getTaskName() != null) {
			row.getCell("taskName").setValue(info.getTaskName());
		}
		// ���β���
		if (info.getAdminPerson() != null) {
			row.getCell("adminPerson").setValue(info.getAdminPerson());
		}
		// �������
		if (info.getTaskType() != null) {
			row.getCell("taskType").setValue(info.getTaskType());
		}
		// ��ɱ�׼
		if (info.getFinishStandard() != null) {
			row.getCell("finishStandard").setValue(info.getFinishStandard());
		}
		// �ƻ��������
		if (info.getPlanFinishDate() != null) {
			row.getCell("planFinishDate").setValue(info.getPlanFinishDate());
		}
		// �������
		if (info.getRelatedTask() != null) {
			row.getCell("relateTask").setValue(info.getRelatedTask());
		}
		// ������Ŀ
		if (info.getProject() != null) {
			row.getCell("project").setValue(info.getProject());
		}
		// ������Դ
		if (info.getTaskOrigin() != null) {
			row.getCell("taskOrigin").setValue(info.getTaskOrigin());
		}
		// ����
		if (info.getProjectPeriod() != null) {
			row.getCell("projectPeriod").setValue(info.getProjectPeriod());
		}
		// �ƻ���ʼ����
		if (info.getPlanStartDate() != null) {
			row.getCell("planStartDate").setValue(info.getPlanStartDate());
		}
		// ������Դ
		if (info.getRequiredResource() != null) {
			row.getCell("requiredResource").setValue(info.getRequiredResource());
		}
		// ��ɽ���
		if (info.getComplete() != null) {
			row.getCell("complete").setValue(info.getProject());
		}
		setWeightRateTotal();
	}
	
	/*
	 * ����FDCBillEditUI.checkBeforeEditOrRemove(String warning)�����getFDCBillInfo()��ȡ�õ���״̬
	 * �����editData���Ǿ�������ģ���FDCBillEditUI�еģ���ȡ�õĵ���״̬�����⣬����Ҫ��д 
	 * Added by owen_wen 2013-12-26
	 */
	protected FDCBillInfo getFDCBillInfo() {
		return editData;
	}
}