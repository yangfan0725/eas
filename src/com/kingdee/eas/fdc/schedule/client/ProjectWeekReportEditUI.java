/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
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
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.OpReportBaseHelper;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportCollection;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryCollection;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryFactory;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportFactory;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportInfo;
import com.kingdee.eas.fdc.schedule.WeekReportEnum;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * ��Ŀ�ܱ��� �༭����
 */
public class ProjectWeekReportEditUI extends AbstractProjectWeekReportEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectWeekReportEditUI.class);

	/** ��Ŀ���� **/
	public CurProjectInfo curProjectInfo = null;
	
	/** ��Ŀ�ܱ��� **/
	public ProjectWeekReportInfo weekReportInfo = null;

	/**
	 * output class constructor
	 */
	public ProjectWeekReportEditUI() throws Exception {
		super();
	}


	/*
	 * �����ݣ�����Ҫ�������
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

	}

	/*
	 * ��������һ�ܿ����ʱ��JDK���ڼ���������Ա������ر���֮ǰ�ĸ����pkPeriodǰ��������һ����ʾ�ܿؼ�textWeek
	 * ��Ҫʵ�ִ˼��㣬��ԭ�еļ���������������EditData�� �޸�jdk bug���߼����£�
	 * ����ʹ��Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)�õ���ǰ�Ǳ���ȵڼ���
	 * �жϣ�����ǵ�һ�ܣ��� �·ݵ���һ�£���˵����ȷ������˵���Ǳ���ȵ����һ�����ǿ��ܣ���ʵ������һ��ȵĵ�һ��
	 * �Ͱ��������Ϊ��һ��ȣ�������Ϊ��һ�� ������ʼ���ڣ�����Ϊ���ܵĵ�һ�죬�������ڣ�����Ϊ���ܵ����һ��
	 * ���ѱ����ݵ�numberֵ������Ϊ������Ŀ����+"-"+"yyyy���ww���ܱ�" ���ѱ����ݵ�name
	 * ֵ������Ϊ������Ŀ����+"-"+"yyyy���ww���ܱ�"
	 * 
	 * �����ڷ����ı�ʱ������Ҫ��ʾ�û����ı���ѡ���ڣ�����ձ�����ݣ����»�ȡ���������ݣ�����fetchData
	 */
	protected void pkPeriod_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
	}

	public void onShow() throws Exception {
		this.menuItemAddNew.setVisible(false);
		setBtnStateByBillState();
		this.thisTable.getColumn("description").setWidth(400);
		// ������������ add by duhongming
		this.thisTable.getColumn("description").setWidth(400);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(70);
		ICellEditor edit = new KDTDefaultCellEditor(textField);
		this.thisTable.getColumn("description").setEditor(edit);
		KDFormattedTextField kdfTextField = new KDFormattedTextField();
		kdfTextField.setPrecision(10);
		kdfTextField.setScrollOffset(0);
		kdfTextField.setSelectionStart(0);
		kdfTextField.setSelectionEnd(0);
		kdfTextField.setCaretPosition(0);
		kdfTextField.setMultiplier(100);
		kdfTextField.setDataType(BigDecimal.class);
		edit = new KDTDefaultCellEditor(kdfTextField);
		this.thisTable.getColumn("completeAmount").setEditor(edit);
		this.textWeek.setRequired(true);
		textWeek.setEditable(false);
		super.onShow();
		
		// ��֤�������кͲ鿴��ʷ����򿪵Ľ��棬����ؼ�������Ч
		kDCNoShowHistory.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		// ��ť����
		initButtonStyle();
		// ���ñ�ͷ
		Date today = FDCDateHelper.getServerTimeStamp();
		
		

		int year = OpReportBaseHelper.getDateFiled(today, Calendar.YEAR);
		int week = OpReportBaseHelper.getDateFiled(today, Calendar.WEEK_OF_YEAR);

		Date firstDate = getFirstDayOfWeek(today);
		Date lastDate = getLastDayOfWeek(today);
		pkStartDate.setValue(firstDate);
		pkEndDate.setValue(lastDate);
		textWeek.setText(year + "���" + week + "��");

		pkPeriod.addDataChangeListener(new DataChangeListener() {// �����¼���
					// ����ѡ������ڲ�ͬ�����ݴ���
					public void dataChanged(DataChangeEvent arg0) {
						try {
							setTextWeek();
						} catch (Exception e) {
							handUIException(e);
						}
					}
				});

		if (getUIContext().get("isFromWorkflow") != null && Boolean.valueOf(getUIContext().get("isFromWorkflow").toString()).booleanValue()) {
			// �������鿴
			
			loadExistsProjectWeekReport(editData);
		} else if (getOprtState().equals(OprtState.EDIT)) {// �༭
			getHaveReport();
			this.txtDescription.setEnabled(false);
			this.thisTable.getStyleAttributes().setLocked(true);
			this.btnEdit.setEnabled(true);
			setBtnStateByBillState();
			
		} else if (getOprtState().equals(OprtState.VIEW)) {// �鿴
			initBottn();
			if (editData.getId() != null) {
				this.pkPeriod.setEnabled(false);
			}
			if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof ProjectWeekReportListUI) {
				loadExistsProjectWeekReport(editData);
			}
		} else {//������ǰ������
			editData.setWeek(week);
			editData.setYear(year);
			loadField();
			this.btnEdit.setEnabled(false);
			this.btnEdit.setVisible(false);
		}
		onladCtrl();
		setTableUnEnable();
		this.actionAttachment.setEnabled(true);
		this.btnAttachment.setEnabled(true);
		
		this.thisTable.getColumn("attach").getStyleAttributes().setFontColor(Color.BLUE);
  		this.thisTable.getColumn("attach").setWidth(400);
	}
	
	private void loadHead(ProjectWeekReportInfo info) {
		textWeek.setText(info.getYear() + "���" + info.getWeek() + "��");
		this.prmtCreator.setValue(info.getCreator());
		this.pkCreateTime.setValue(info.getCreateTime());
		this.pkStartDate.setValue(info.getStartDate());
		this.pkEndDate.setValue(info.getEndDate());
		this.txtDescription.setText(info.getDescription());
		this.comboState.setSelectedItem(info.getState());
	}

	private void initButtonStyle() {
		btnHistory.setEnabled(true);

		this.kDContainer1.add(kDPanel2);
		kDPanel2.setVisible(true);
		btnUnAudit.setEnabled(true);
		txtDescription.setMaxLength(255);
		btnAudit.setEnabled(true);
		btnAudit.setVisible(true);
		btnPrintReport.setIcon(EASResource.getIcon("imgTbtn_print"));
		btnHistory.setIcon(EASResource.getIcon("imgTbtn_archive"));
		btnLeadout.setEnabled(true);
		btnLeadout.setIcon(EASResource.getIcon("imgTbtn_output"));
		btnEdit.setEnabled(true);
		this.menuItemEdit.setEnabled(true);		
		this.prmtCreator.setDisplayFormat("$user.person.name$");
	}

	public void setTableUnEnable() {
		thisTable.checkParsed();
		thisTable.getColumn("startDate").getStyleAttributes().setLocked(true);
		thisTable.getColumn("taskName").getStyleAttributes().setLocked(true);
		thisTable.getColumn("taskType").getStyleAttributes().setLocked(true);
		thisTable.getColumn("planEndDate").getStyleAttributes().setLocked(true);
		thisTable.getColumn("planEndDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		thisTable.getColumn("respPerson").getStyleAttributes().setLocked(true);
		thisTable.getColumn("respDept").getStyleAttributes().setLocked(true);
		thisTable.getColumn("quanlityPerson").getStyleAttributes().setLocked(true);
		thisTable.getColumn("qEvaluationDate").getStyleAttributes().setLocked(true);
		thisTable.getColumn("qEvaluationResult").getStyleAttributes().setLocked(true);
		thisTable.getColumn("planPerson").getStyleAttributes().setLocked(true);
		thisTable.getColumn("pEvaluationDate").getStyleAttributes().setLocked(true);
		thisTable.getColumn("pEvaluationResult").getStyleAttributes().setLocked(true);
		thisTable.getColumn("simpleName").getStyleAttributes().setLocked(true);
	}

	/**
	 * @description �鿴״̬ʱ��ť��״̬
	 * @author ����ΰ
	 * @createDate 2011-9-20
	 * @version EAS7.0
	 */
	public void initBottn() {
		this.btnEdit.setEnabled(false);
		this.btnSave.setEnabled(false);
		this.btnSubmit.setEnabled(false);
		this.btnAudit.setEnabled(false);
		this.btnUnAudit.setEnabled(false);
		this.btnHistory.setEnabled(false);
		btnLeadout.setEnabled(false);
		/* modified by zhaoqin for R140918-0150 on 2015/01/19 */
		//btnAttachment.setEnabled(false);
		btnPrintReport.setEnabled(false);
		btnAuditResult.setEnabled(false);
	}

	/**
	 * @description �鿴״̬��ʼ���������
	 * @author ����ΰ
	 * @createDate 2011-9-20
	 * @version EAS7.0
	 */
	public void initTableForView(String reportId) throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("entrys.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.relateTask.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respPerson.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.respDept.*"));
		SorterItemCollection sic = new SorterItemCollection();
		sic.add(new SorterItemInfo("entrys.planEndDate"));
		view.setSorter(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", reportId));
		view.setFilter(filter);
		ProjectWeekReportInfo weekreportInfo = ProjectWeekReportFactory.getRemoteInstance().getProjectWeekReportCollection(view).get(0);
		getInitDate(weekreportInfo);
	}
	
	private boolean isCurrentWeek(Date selectDate) throws BOSException {
		// �˴�Ӧ��ȡ���������
		boolean isCurrentWeek = false;
		Date d = FDCDateHelper.getServerTimeStamp();
		int currentYear = OpReportBaseHelper.getDateFiled(d, Calendar.YEAR);
		int currentWeek = OpReportBaseHelper.getDateFiled(d, Calendar.WEEK_OF_YEAR);

		int selectYear = OpReportBaseHelper.getDateFiled(selectDate, Calendar.YEAR);
		int selectWeek = OpReportBaseHelper.getDateFiled(selectDate, Calendar.WEEK_OF_YEAR);
		if (selectYear == currentYear && selectWeek == currentWeek) {
			isCurrentWeek = true;
		}

		return isCurrentWeek;
	}

	/**
	 * @description ����ѡ����������ñ���е�����
	 * @author ����ΰ
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 * @see
	 */
	public void setTextWeek() throws Exception {
		Date date = (Date) pkPeriod.getValue();
		
		int year = OpReportBaseHelper.getDateFiled(date, Calendar.YEAR);
		int week = OpReportBaseHelper.getDateFiled(date, Calendar.WEEK_OF_YEAR);

		if (isCurrentWeek(date)) {
			unlockui();
		} else {
			btnEdit.setEnabled(false);
			btnSave.setEnabled(false);
			btnSubmit.setEnabled(false);
			txtDescription.setEnabled(false);
			thisTable.getStyleAttributes().setLocked(true);
		}
		
		Date firstDate = getFirstDayOfWeek(date);
		Date lastDate = getLastDayOfWeek(date);
		
		this.textWeek.setText(year + "���" + week + "��");
		pkStartDate.setValue(firstDate);
		pkEndDate.setValue(lastDate);
		
		ProjectWeekReportCollection collection = getProjectWeekReportData(year, week);
	    
		if (collection.size() > 0) {
			loadExistsProjectWeekReport(collection.get(0));
			this.btnEdit.setEnabled(true);
			
		} else {
			// �˴��϶���û��������Ŀ���±�������״̬������
			Date d = (Date) pkPeriod.getValue();
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			unlockui();
			this.comboState.setSelectedItem(FDCBillStateEnum.SAVED);
			// ��������ר��
			if (editData.getProjectSpecial() != null) {
				this.projectSpecialInfo = editData.getProjectSpecial();
			}
			editData = (ProjectWeekReportInfo) createNewData();
			editData.setYear(c.get(Calendar.YEAR));
			editData.setWeek(getWeekOfYear(d));
			editData.setMonth(c.get(Calendar.MONTH) + 1);
			editData.setState(FDCBillStateEnum.SAVED);
			editData.setProjectSpecial(this.projectSpecialInfo);
			setDataObject(editData);
			againLoadTable();
		}
		
		setBtnStateByBillState();
	}

	private void unlockui() {
		btnEdit.setEnabled(true);
		btnSave.setEnabled(true);
		btnSubmit.setEnabled(true);
		txtDescription.setEnabled(true);
		thisTable.getStyleAttributes().setLocked(false);
		thisTable.getColumn("taskName").getStyleAttributes().setLocked(true);
		thisTable.getColumn("planEndDate").getStyleAttributes().setLocked(true);
		thisTable.getColumn("respPerson").getStyleAttributes().setLocked(true);
		thisTable.getColumn("respDept").getStyleAttributes().setLocked(true);
	}

	/**
	 * @description ���ڷ����仯�����ñ�������
	 * @author ����ΰ
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 * @see
	 */
	public void againSetThisTable(List thisWeek) {
		this.thisTable.removeRows(false);
		for (int i = 0; i < thisWeek.size(); i++) {// ѭ������¼��������
			ProjectWeekReportEntryInfo entryInfo = (ProjectWeekReportEntryInfo) thisWeek.get(i);
			fillWeekReportToTable(entryInfo);
		}
	}

	/**
	 * @description ���ñ������
	 * @author ����ΰ
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 * @throws Exception
	 * @see
	 */
	public void againLoadTable() throws Exception {
		// FDCMsgBox.showWarning("���ڸı䣬���ݷ����仯");
		Map map = fetchData();
		List thisWeekList = (List) map.get("thisWeek");
		List nextWeekList = (List) map.get("nextWeek");

		loadNextTableFields(nextWeekList);
		againSetThisTable(thisWeekList);
	}

	/**
	 * @deprecated
	 * @description ���ڷ����仯ʱ����������������(�ظ�����,����)
	 * @author ����ΰ
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 */
	public void againSetNextTable() {

		this.nextTable.removeRows(false);
		try {
			Map map = fetchData();
			if (map != null && !map.isEmpty()) {// ��Ϊ��
				List nextWeek = (List) map.get("nextWeek");
				if (nextWeek.size() > 0) {// ���ϴ���0
					for (int i = 0; i < nextWeek.size(); i++) {// ѭ������¼��������
						ProjectWeekReportEntryInfo entryInfo = (ProjectWeekReportEntryInfo) nextWeek.get(i);
						IRow row = nextTable.addRow();
						row.getCell("taskName").setValue(entryInfo.getTaskName());
						row.getCell("planStartDate").setValue(entryInfo.getPlanStartDate());
						row.getCell("planEndDate").setValue(entryInfo.getPlanEndDate());
						row.getCell("respPerson").setValue(entryInfo.getRespPerson());
						row.getCell("respDept").setValue(entryInfo.getRespDept());
						row.getCell("entryID").setValue(entryInfo.getId());
						row.getCell("taskID").setValue(entryInfo.getRelateTask());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description �������ڿؼ��õ���ǰ�����Ǳ���ȵĵڼ���
	 * @author ����ΰ
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 */
	public int getWeekOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * @description ����ѡ������ڵõ���ǰ�ܵĵ�һ�������
	 * @author ����ΰ
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 * @see
	 */
	public Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK); // ��ȡ�ܿ�ʼ��׼
		int current = calendar.get(Calendar.DAY_OF_WEEK); // ��ȡ������������
		calendar.add(Calendar.DAY_OF_WEEK, min - current); // ����-��׼����ȡ�ܿ�ʼ����
		Date start = calendar.getTime();
		return start;
	}

	/**
	 * @description ����ѡ������ڵõ���ǰ�ܵ����һ�������
	 * @author ����ΰ
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 * @see
	 */
	public Date getLastDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK); // ��ȡ�ܿ�ʼ��׼
		int current = calendar.get(Calendar.DAY_OF_WEEK); // ��ȡ������������
		calendar.add(Calendar.DAY_OF_WEEK, min - current); // ����-��׼����ȡ�ܿ�ʼ����
		calendar.add(Calendar.DAY_OF_WEEK, 6); // ��ʼ+6����ȡ�ܽ�������
		Date end = calendar.getTime();
		return end;
	}

	public ProjectSpecialInfo getProjectSpecialInfo() {
		ProjectSpecialInfo projectSpecialInfo = null;
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof SpecialScheduleExecuteUI) {
			SpecialScheduleExecuteUI executeUI = (SpecialScheduleExecuteUI) getUIContext().get("Owner");
			projectSpecialInfo = executeUI.projectSpecialInfo;
		}
		return projectSpecialInfo;
	}

	public void storeFields() {
		super.storeFields();
		CurProjectInfo info = getCurProject();
		editData.setName(info.getName() + this.textWeek.getText() + "�ܱ�");
		editData.setNumber(info.getName() + this.textWeek.getText() + "�ܱ�");
		
		/* modified by zhaoqin for R140606-0197 on 2014/07/04 */
		//editData.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		
		editData.setDescription(this.txtDescription.getText().trim());
		if (comboState.getSelectedItem() != null) {
			if (comboState.getSelectedItem().equals(FDCBillStateEnum.SAVED)) {
				editData.setState(FDCBillStateEnum.SAVED);
			}
			if (comboState.getSelectedItem().equals(FDCBillStateEnum.SUBMITTED)) {
				editData.setState(FDCBillStateEnum.SUBMITTED);
			}
		}
		
		storeLineFiles();
	}

	/***
	 * ��дgetSelector()
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("entrys.relateTask.*"));
		sic.add(new SelectorItemInfo("entrys.respPerson.*"));
		sic.add(new SelectorItemInfo("entrys.respDept.*"));
		sic.add("projectSpecial.id");
		sic.add("projectSpecial.number");
		sic.add("projectSpecial.name");
		sic.add("project.name");
		sic.add("project.id");
		sic.add("project.number");
		sic.add("entrys.planEndDate");
		sic.add("creator.id");
		sic.add("creator.name");
		sic.add("creator.number");
		sic.add("creator.person.id");
		sic.add("creator.person.name");
		sic.add("creator.person.number");
		return sic;
	}

	/*
	 * ��ȡ���ֵ ���÷������˷��� 1����ȡ���ܵ����񣬼������������ 2����ȡ���ܵ�����
	 * �뽫��ȡ��ֵ������ProjectWeekReportEntryInfo�����뵽editData��
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#fetchInitData()
	 */
	protected Map fetchData() throws Exception {
		return super.fetchData();
	}

	/*
	 * ��ȡһЩ��ʼ����ֵ
	 * 
	 * @see com.kingdee.eas.fdc.schedule.client.OpReportBaseUI#fetchInitData()
	 */
	protected void fetchInitData() throws Exception {
	}

	/*
	 * ͨ��UIContext,��SysContextȡ���� ��ʼ����Ӧ����֯��������Ŀ����Ϣ
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#createNewData()
	 */
	protected IObjectValue createNewData() {
		ProjectWeekReportInfo info = new ProjectWeekReportInfo();
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		if (getCurProject() != null) {
			CurProjectInfo project = getCurProject();
			info.setProject(project);
			info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
			info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		}
		if (getUIContext().get("projectSpecial") != null) {
			ProjectSpecialInfo projectSpecila = (ProjectSpecialInfo) getUIContext().get("projectSpecial");
			info.setProjectSpecial(projectSpecila);
			this.projectSpecialInfo = projectSpecila;
		}
		info.setState(FDCBillStateEnum.SAVED);
		return info;
	}

	/**
	 * @description ���ڷ����仯ʱ����ǰ�ܴ��ڱ���
	 * @author ����ΰ
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 * @see
	 */
	public void getHaveReport() throws Exception {
		Date currSelectDate = (Date) this.pkPeriod.getValue();
		int year = OpReportBaseHelper.getDateFiled(currSelectDate, Calendar.YEAR);
		int week = OpReportBaseHelper.getDateFiled(currSelectDate, Calendar.WEEK_OF_YEAR);
		ProjectWeekReportCollection collection = getProjectWeekReportData(year, week);
		if (collection.size() > 0) {
			ProjectWeekReportInfo projectWeekReportInfo = collection.get(0);
			loadExistsProjectWeekReport(projectWeekReportInfo);
		}
	}

	/***
	 * 
	 * ������������Ŀ�ܱ���
	 * 
	 * @param projectWeekReportInfo
	 *            �����ˣ�yuanjun_lan ����ʱ�䣺2012-5-19
	 */
	private void loadExistsProjectWeekReport(ProjectWeekReportInfo projectWeekReportInfo) {
		editData = projectWeekReportInfo;
		setDataObject(editData);
		loadHead(projectWeekReportInfo);
		getInitDate(projectWeekReportInfo);
	}

	/**
	 * 
	 * ���������ݵ�ǰ��ȥȡ��
	 * 
	 * @param year
	 * @param week
	 * @return
	 * @throws BOSException
	 *             �����ˣ�yuanjun_lan ����ʱ�䣺2012-5-19
	 */
	private ProjectWeekReportCollection getProjectWeekReportData(int year, int week) throws BOSException, EASBizException, SQLException {
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(getSelectors());		
		SorterItemCollection sic = new SorterItemCollection();
		sic.add(new SorterItemInfo("entrys.planEndDate"));
		view.setSorter(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", getCurProjectIDForAll()));
		filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
		filter.getFilterItems().add(new FilterItemInfo("week", new Integer(week)));
		filter.getFilterItems().add(new FilterItemInfo("creator.id", SysContext.getSysContext().getCurrentUserInfo().getId().toString(),CompareType.EQUALS));
		view.setFilter(filter);
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof SpecialScheduleExecuteUI) {
			filter.getFilterItems().add(
					new FilterItemInfo("projectSpecial.id", editData.getProjectSpecial().getId().toString(), CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
		}

		ProjectWeekReportCollection collection = ProjectWeekReportFactory.getRemoteInstance().getProjectWeekReportCollection(view);
		return collection;
	}

	/**
	 * @description �õ�����
	 * @author ����ΰ
	 * @createDate 2011-9-21
	 * @version EAS7.0
	 */
	private void loadField() {
		if (editData.getProjectSpecial() != null) {
			this.projectSpecialInfo = editData.getProjectSpecial();
		}
		if (editData.getCreator() != null) {
			this.prmtCreator.setValue(editData.getCreator());
		}
		
		try {
			Map map = fetchData();
			if (map != null && !map.isEmpty()) {// ��Ϊ��
				List thisWeek = (List) map.get("thisWeek");
				List nextWeek = (List) map.get("nextWeek");
				loadThisTableFields(thisWeek);
				loadNextTableFields(nextWeek);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @description ��ʼ������
	 * @author ����ΰ
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */
	public void getInitDate(ProjectWeekReportInfo info) {
		this.txtDescription.setText(info.getDescription());
		List thisWeek = new ArrayList();
		List nextWeek = new ArrayList();
		ProjectWeekReportEntryCollection collection = info.getEntrys();
		if (collection.size() > 0) {
			for (int i = 0; i < collection.size(); i++) {
				ProjectWeekReportEntryInfo entryInfo = collection.get(i);
				if (entryInfo.isIsNext()) {
					nextWeek.add(entryInfo);
				} else {
					thisWeek.add(entryInfo);
				}
			}
		}
		loadThisTableFields(thisWeek);
		loadNextTableFields(nextWeek);
	}

	/*
	 * �������е����ݣ���ʾ�����ڱ����
	 */
	public void loadNextTableFields(List list) {
		nextTable.removeRows(false);
		this.nextTable.checkParsed();
		for (int i = 0; i < list.size(); i++) {// ѭ������¼��������
			ProjectWeekReportEntryInfo entryInfo = (ProjectWeekReportEntryInfo) list.get(i);
			IRow row = nextTable.addRow();
			row.getCell("taskName").setValue(entryInfo.getTaskName());
			row.getCell("planStartDate").setValue(entryInfo.getPlanStartDate());
			row.getCell("planEndDate").setValue(entryInfo.getPlanEndDate());
			row.getCell("taskID").setValue(entryInfo.getRelateTask());
			row.getCell("respPerson").setValue(entryInfo.getRespPerson());
			row.getCell("respDept").setValue(entryInfo.getRespDept());
			row.getCell("entryID").setValue(entryInfo.getId());

			row.getCell("quanlityPerson").setValue(entryInfo.getQuanlityPerson());
			row.getCell("qEvaluationDate").setValue(entryInfo.getQEvaluationDate());
			row.getCell("qEvaluationResult").setValue(entryInfo.getQEvaluationResult());
			
			row.getCell("planPerson").setValue(entryInfo.getPlanPerson());
			row.getCell("pEvaluationDate").setValue(entryInfo.getPEvaluationDate());
			row.getCell("pEvaluationResult").setValue(entryInfo.getPEvaluationResult());
			
			/* modified by zhaoqin for R140812-0088 on 2014/09/24 */
			row.setUserObject(entryInfo);
		}
	}

	/*
	 * �������е����ݣ���ʾ�����ڱ����
	 * 
	 * @see
	 * com.kingdee.eas.fdc.schedule.client.OpReportBaseUI#loadThisTableFields()
	 */
	public void loadThisTableFields(List list) {
		thisTable.removeRows(false);
		this.thisTable.checkParsed();
		for (int i = 0; i < list.size(); i++) {// ѭ������¼��������
			ProjectWeekReportEntryInfo entryInfo = (ProjectWeekReportEntryInfo) list.get(i);
			fillWeekReportToTable(entryInfo);
		}
	}

	private void fillWeekReportToTable(ProjectWeekReportEntryInfo entryInfo) {
		IRow row = thisTable.addRow();
		row.getCell("taskName").setValue(entryInfo.getTaskName());
		row.getCell("taskType").setValue(entryInfo.getRelateTask().getTaskType().getAlias());
		
		Date planEndDate=entryInfo.getPlanEndDate() == null ? entryInfo.getRelateTask().getEnd() : entryInfo.getPlanEndDate();
		row.getCell("planEndDate").setValue(planEndDate);
		if(FDCDateHelper.getDiffDays(planEndDate, (Date) this.pkEndDate.getValue())-1>=0&&FDCDateHelper.getDiffDays(planEndDate, (Date) this.pkStartDate.getValue())-1<=0){
			row.getCell("taskName").getStyleAttributes().setFontColor(Color.BLUE);
		}
		
		int completePrecent = entryInfo.getCompletePrecent();
		if (completePrecent == 100) {
			row.getCell("isComplete").setValue(WeekReportEnum.YES);
		} else {
			row.getCell("isComplete").setValue(WeekReportEnum.NO);
		}
		row.getCell("completePercent").setValue(new Integer(completePrecent));
		row.getCell("realEndDate").setValue(entryInfo.getRealEndDate());
		row.getCell("simpleName").setValue(entryInfo.getRelateTask().getSimpleName());
		if (entryInfo.getDescription() != null && !"".equals(entryInfo.getDescription())) {
			row.getCell("description").setValue(entryInfo.getDescription());
		} else {
			row.getCell("description").setValue("");
		}
		row.getCell("respPerson").setValue(entryInfo.getRespPerson());
		row.getCell("respDept").setValue(entryInfo.getRespDept());
		row.getCell("completeAmount").setValue(entryInfo.getCompleteAmount());
		row.getCell("entryID").setValue(entryInfo.getId());
		FDCScheduleTaskInfo task = entryInfo.getRelateTask();
		row.getCell("intendEndDate").setValue(
				entryInfo.getIntendEndDate() == null ? (task.getIntendEndDate() == null ? task.getEnd() : task.getIntendEndDate())
						: entryInfo.getIntendEndDate());
		row.getCell("startDate").setValue(task.getStart());
		row.getCell("taskID").setValue(task);
		Date actualStartDate = task.getActualStartDate() == null ? task.getStart() : task.getActualStartDate();
		row.getCell("actualStartDate").setValue(entryInfo.getPlanStartDate() == null ? actualStartDate : entryInfo.getPlanStartDate());
		lockedCell(new Integer(entryInfo.getCompletePrecent()), row);
		row.setUserObject(entryInfo);
		
		row.getCell("quanlityPerson").setValue(entryInfo.getQuanlityPerson());
		row.getCell("qEvaluationDate").setValue(entryInfo.getQEvaluationDate());
		row.getCell("qEvaluationResult").setValue(entryInfo.getQEvaluationResult());
		
		row.getCell("planPerson").setValue(entryInfo.getPlanPerson());
		row.getCell("pEvaluationDate").setValue(entryInfo.getPEvaluationDate());
		row.getCell("pEvaluationResult").setValue(entryInfo.getPEvaluationResult());
		
		try {
			row.getCell("attach").setValue(loadAttachment(task.getId().toString()));
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	public void storeLineFiles() {
		storeThisTableFields();
		storeNextTableFields();
	}

	/*
	 * ����editData�е�ֵ
	 * 
	 * @see
	 * com.kingdee.eas.fdc.schedule.client.OpReportBaseUI#storeNextTableFields()
	 */
	public void storeNextTableFields() {
		weekReportInfo = (ProjectWeekReportInfo) editData;

		int count = nextTable.getRowCount();
		for (int i = 0; i < count; i++) {
			IRow row = nextTable.getRow(i);
			Object obj = weekReportInfo.getId();
			String taskName = row.getCell("taskName").getValue().toString();
			Date planStartDate = (Date) row.getCell("planStartDate").getValue();
			Date planEndDate = (Date) row.getCell("planEndDate").getValue();
			ProjectWeekReportEntryInfo entryInfo = new ProjectWeekReportEntryInfo();

			if (!FDCHelper.isEmpty(obj)) {
				weekReportInfo.setId(BOSUuid.read(obj.toString()));
				CoreBaseCollection collection = new CoreBaseCollection();
				collection.add(weekReportInfo);

				try {
					ProjectWeekReportEntryFactory.getRemoteInstance().update(collection);
				} catch (Exception e) {
					logger.info(e.getMessage(), e);
				}
			} else {
				entryInfo.setTaskName(taskName);
				entryInfo.setPlanStartDate(planStartDate);
				entryInfo.setPlanEndDate(planEndDate);
				PersonInfo personInfo = (PersonInfo) row.getCell("respPerson").getValue();
				FullOrgUnitInfo unitInfo = (FullOrgUnitInfo) row.getCell("respDept").getValue();
				entryInfo.setRespDept(unitInfo);
				entryInfo.setRespPerson(personInfo);
				entryInfo.setIsNext(true);
				entryInfo.setHead(weekReportInfo);
				entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
				
				if (row.getCell("quanlityPerson").getValue() != null)
					entryInfo.setQuanlityPerson(row.getCell("quanlityPerson")
							.getValue()
							+ "");
				if (row.getCell("qEvaluationDate").getValue() != null)
					entryInfo.setQEvaluationDate(FDCDateHelper.stringToDate(row
							.getCell("qEvaluationDate").getValue()
							+ ""));
				if (row.getCell("qEvaluationResult").getValue() != null)
					entryInfo.setQEvaluationResult(row.getCell(
							"qEvaluationResult").getValue()
							+ "");
				if (row.getCell("planPerson").getValue() != null)
					entryInfo.setPlanPerson(row.getCell("planPerson")
							.getValue()
							+ "");
				if (row.getCell("pEvaluationDate").getValue() != null)
					entryInfo.setPEvaluationDate(FDCDateHelper.stringToDate(row
							.getCell("pEvaluationDate").getValue()
							+ ""));
				if (row.getCell("pEvaluationResult").getValue() != null)
					entryInfo.setPEvaluationResult(row.getCell(
							"pEvaluationResult").getValue()
							+ "");
				if (null != row.getCell("taskID") && row.getCell("taskID").getValue() instanceof FDCScheduleTaskInfo) {
					entryInfo.setRelateTask((FDCScheduleTaskInfo) row.getCell("taskID").getValue());
				}
				weekReportInfo.getEntrys().add(entryInfo);
			}
		}
	}

	/*
	 * ����editData�е�ֵ
	 * 
	 * @see
	 * com.kingdee.eas.fdc.schedule.client.OpReportBaseUI#storeThisTableFields()
	 */
	public void storeThisTableFields() {
		weekReportInfo = (ProjectWeekReportInfo) editData;
//		if (weekReportInfo.getId() == null) {// û��ID��˵�����������ܱ���
			/* ��Ϊ����������ύʱ�ᱻ���ö�Σ����·�¼�����ظ���
			 * �����������ܱ��棬ÿ�ε���ǰ�����һ�·�¼������Ĵ�����ټ��� Added by Owen_wen 2013-06-25
			 */
			weekReportInfo.getEntrys().clear();
//		}
		
		for (int i = 0; i < thisTable.getRowCount(); i++) {
			IRow row = thisTable.getRow(i);
			ProjectWeekReportEntryInfo entryInfo = new ProjectWeekReportEntryInfo();
			if(row.getCell("quanlityPerson").getValue() != null)
			  entryInfo.setQuanlityPerson(row.getCell("quanlityPerson").getValue()+"");
			if(row.getCell("qEvaluationDate").getValue() != null)
			  entryInfo.setQEvaluationDate(FDCDateHelper.stringToDate(row.getCell("qEvaluationDate").getValue()+""));
			if(row.getCell("qEvaluationResult").getValue()!= null)
			  entryInfo.setQEvaluationResult(row.getCell("qEvaluationResult").getValue()+"");
			if(row.getCell("planPerson").getValue() != null)
			  entryInfo.setPlanPerson(row.getCell("planPerson").getValue()+"");
			if(row.getCell("pEvaluationDate").getValue() != null)
			  entryInfo.setPEvaluationDate( FDCDateHelper.stringToDate(row.getCell("pEvaluationDate").getValue()+""));
			if(row.getCell("pEvaluationResult").getValue() != null)
			  entryInfo.setPEvaluationResult(row.getCell("pEvaluationResult").getValue()+"");
			
			String taskName = row.getCell("taskName").getValue().toString().trim();
			entryInfo.setTaskName(taskName);
			Date endDate = (Date) row.getCell("planEndDate").getValue();
			entryInfo.setIsNext(false);
			Date realEndDate = (Date) row.getCell("realEndDate").getValue();
			Date intendEndDate = (Date) row.getCell("intendEndDate").getValue();
			
			BigDecimal completeAmount = (BigDecimal) row.getCell("completeAmount").getValue();
			entryInfo.setCompleteAmount(completeAmount);
			
			int newtemp = 0;
			if (row.getCell("completePercent").getValue() != null) {
				newtemp = new BigDecimal(row.getCell("completePercent").getValue().toString()).intValue();
			}
			entryInfo.setHead(weekReportInfo);
			if (weekReportInfo.getId() != null) {

				if (weekReportInfo.getEntrys().size() > 0) {
					weekReportInfo.getEntrys().get(i).setIsNext(false);
					if (row.getCell("completePercent").getValue() != null) {
						if (weekReportInfo.getEntrys().size() > 0) {
							if (newtemp > 100) {
								FDCMsgBox.showInfo("ֻ������0-100������");
								SysUtil.abort();
							} else {
								weekReportInfo.getEntrys().get(i).setCompletePrecent(newtemp);
							}
						}
					}
					if (row.getCell("isComplete").getValue().equals(WeekReportEnum.YES)) {
						if (weekReportInfo.getEntrys().size() > 0) {
							weekReportInfo.getEntrys().get(i).setIsComplete(true);
						}

					} else {
						if (weekReportInfo.getEntrys().size() > 0) {
							weekReportInfo.getEntrys().get(i).setIsComplete(false);
						}
					}
					weekReportInfo.getEntrys().get(i).setCompleteAmount(completeAmount);
					weekReportInfo.getEntrys().get(i).setPlanEndDate(endDate);
					weekReportInfo.getEntrys().get(i).setRealEndDate(realEndDate);
					weekReportInfo.getEntrys().get(i).setIntendEndDate(intendEndDate);
					String description = (String) row.getCell("description").getValue();
					if (description == null) {
						weekReportInfo.getEntrys().get(i).setDescription("");
					} else {
						weekReportInfo.getEntrys().get(i).setDescription(description.trim());
					}

				}
				// �˴�������ʵ�ʿ�ʼ���ڣ�����planStartDate
				if (entryInfo.getCompletePrecent() > 0) {
					Date actualStartDate = (Date) (row.getCell("actualStartDate").getValue() == null ? null : row
							.getCell("actualStartDate").getValue());
					entryInfo.setPlanStartDate(actualStartDate);
				}
				CoreBaseCollection collection = new CoreBaseCollection();
				collection.add(weekReportInfo);

				try {
					ProjectWeekReportEntryFactory.getRemoteInstance().update(collection);
				} catch (EASBizException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BOSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else { // ���IDΪ�գ�˵����������editData
				if (row.getCell("completePercent").getValue() != null) {
						if (newtemp > 100) {
							FDCMsgBox.showInfo("ֻ������0-100������");
							SysUtil.abort();
						} else {
							entryInfo.setCompletePrecent(newtemp);
					}
				}

				if (row.getCell("isComplete").getValue().equals(WeekReportEnum.YES)) {
					entryInfo.setIsComplete(true);
				} else {
					entryInfo.setIsComplete(false);
				}
				entryInfo.setRealEndDate(realEndDate);
				entryInfo.setPlanEndDate(endDate);
				entryInfo.setIntendEndDate(intendEndDate);
				entryInfo.setCompleteAmount(completeAmount);
				String description = (String) row.getCell("description").getValue();
				if (description == null) {
					entryInfo.setDescription("");
				} else {
					entryInfo.setDescription(description.trim());
				}
				PersonInfo personInfo = (PersonInfo) row.getCell("respPerson").getValue();
				FullOrgUnitInfo unitInfo = (FullOrgUnitInfo) row.getCell("respDept").getValue();
				entryInfo.setRespDept(unitInfo);
				entryInfo.setRespPerson(personInfo);
				if (null != row.getCell("taskID") && row.getCell("taskID").getValue() instanceof FDCScheduleTaskInfo) {
					entryInfo.setRelateTask((FDCScheduleTaskInfo) row.getCell("taskID").getValue());
				}
				entryInfo.setHead(weekReportInfo);
				entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
				if (entryInfo.getCompletePrecent() > 0) {
					Date actualStartDate = (Date) (row.getCell("actualStartDate").getValue() == null ? null : row
							.getCell("actualStartDate").getValue());
					entryInfo.setPlanStartDate(actualStartDate);
				}				
				weekReportInfo.getEntrys().add(entryInfo);
			}
		}
	}

	protected void verifyInputForSave() throws Exception {
	}

	/**
	 * @description ������Ӧ�Ĳ���
	 * @author ����ΰ
	 * @createDate 2011-8-28
	 * @version EAS7.0
	 * @see
	 */
	protected Map getMainFilterParam() throws Exception {
		// ���ڴ�Ų���
		Map param = new HashMap();
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("projectSpecial.*"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", this.getCurProjectIDForAll()));
		filter.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.valueOf(true)));
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof SpecialScheduleExecuteUI) {
			filter.getFilterItems().add(
					new FilterItemInfo("projectSpecial.id", editData.getProjectSpecial().getId().toString(), CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
		}
		view.setFilter(filter);
		FDCScheduleCollection collection = FDCScheduleFactory.getRemoteInstance().getFDCScheduleCollection(view);

		if (collection.size() > 0) {
			FDCScheduleInfo scheduleInfo = collection.get(0);
			param.put("stringPk", scheduleInfo.getId().toString());
			param.put("projectSpecial", scheduleInfo.getProjectSpecial());
		}
		
		Date starDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkEndDate.getValue();
		Date nextFist = DateUtils.addDay(starDate, 7);
		Date nextLast = DateUtils.addDay(endDate, 7);
		param.put("starDate", starDate);
		param.put("endDate", endDate);
		param.put("nextFist", nextFist);
		param.put("nextLast", nextLast);
		param.put("creator",SysContext.getSysContext().getCurrentUserInfo());
		return param;
	}

	/**
	 * @description ���ر��ؼ�
	 * @author ����ΰ
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */
	public void onladCtrl() {
		thisTable.checkParsed();
		nextTable.checkParsed();
		KDComboBox box = new KDComboBox();
		Vector vector = new Vector(WeekReportEnum.getEnumList());
		box.addItems(vector);
		this.thisTable.getColumn("isComplete").setEditor(new KDTDefaultCellEditor(box));
		KDDatePicker date = new KDDatePicker();
		date.setEditable(false);
		KDTDefaultCellEditor editorDate = new KDTDefaultCellEditor(date);
		thisTable.getColumn("realEndDate").setEditor(editorDate);
		thisTable.getColumn("intendEndDate").setEditor(editorDate);
		thisTable.getColumn("realEndDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		thisTable.getColumn("intendEndDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		KDFormattedTextField completePercent = new KDFormattedTextField();
		completePercent.setPrecision(0);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(completePercent);
		thisTable.getColumn("completePercent").setEditor(txtEditor);
		this.actionUnAudit.setEnabled(false);
		this.actionUnAudit.setVisible(false);
	}

	public void checkThisTable() {
		thisTable.checkParsed();
		IRow row = KDTableUtil.getSelectedRow(thisTable);
		if (null != row) {
			if (row.getCell("isComplete").getValue().equals(WeekReportEnum.YES)) {
				row.getCell("completePercent").setValue(new Integer(100));
			} else {
				row.getCell("completePercent").setValue(new Integer(0));
			}
			if (row.getCell("completePercent").getValue() != null && !"".equals(row.getCell("completePercent").getValue().toString().trim())) {

				if ("100".equals((row.getCell("completePercent").getValue().toString().trim()))) {
					row.getCell("isComplete").setValue(WeekReportEnum.YES);

				}
				if (Integer.parseInt(row.getCell("completePercent").getValue().toString()) > 100) {
					FDCMsgBox.showWarning(this, "��ɽ��Ȳ��ܴ���100%��");
					row.getCell("completePercent").setValue("0");
				}

			}
			if (row.getCell("isComplete").getValue().equals(WeekReportEnum.NO)) {
				row.getCell("realEndDate").getStyleAttributes().setLocked(true);
			} else {
				row.getCell("realEndDate").getStyleAttributes().setLocked(false);
			}
			if (row.getCell("isComplete").getValue().equals(WeekReportEnum.YES)) {
				row.getCell("intendEndDate").getStyleAttributes().setLocked(true);
			} else {
				row.getCell("intendEndDate").getStyleAttributes().setLocked(false);
			}
		}
	}

	/**
	 * @description ����ʾ��ʷ��¼
	 * @author ����ΰ
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */
	protected void kDCNoShowHistory_mouseClicked(MouseEvent e) throws Exception {
		if (this.kDCNoShowHistory.isSelected()) {// ����ʾ��ʷ��¼
			int count = thisTable.getRowCount();
			for (int i = count - 1; i >= 0; i--) {
				IRow row = this.thisTable.getRow(i);
				Date date = (Date) this.pkStartDate.getValue();
				Date plantEndDate = (Date) row.getCell("planEndDate").getValue();

				if (date != null && plantEndDate != null) {
					if (DateUtils.diffdates(plantEndDate, date) > 0) {
						thisTable.removeRow(i);
					}
				}
			}

		} else {
			thisTable.removeRows(false);
			if (STATUS_FINDVIEW.equals(getOprtState()) || getOprtState().equals(OprtState.VIEW)) {// �鿴ʱ���ݵı仯
				initTableForView(editData.getId().toString());
			} else {
				recoverThisWeek();
			}
		}
	}

	/**
	 * @description �ָ���������
	 * @author ����ΰ
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */
	public void recoverThisWeek() throws Exception {
		Date d = (Date) this.pkPeriod.getValue();

		int year = OpReportBaseHelper.getDateFiled(d, Calendar.YEAR);
		int week = OpReportBaseHelper.getDateFiled(d, Calendar.WEEK_OF_YEAR);
		ProjectWeekReportCollection collection = getProjectWeekReportData(year, week);
		if (collection.size() > 0) {
			List thisWeek = new ArrayList();
			ProjectWeekReportInfo projectWeekReportInfo = collection.get(0);
			ProjectWeekReportEntryCollection entrycollection = projectWeekReportInfo.getEntrys();
			if (entrycollection.size() > 0) {

				for (int i = 0; i < entrycollection.size(); i++) {
					ProjectWeekReportEntryInfo entryInfo = entrycollection.get(i);
					if (!entryInfo.isIsNext()) {
						thisWeek.add(entryInfo);
					}
				}
			}
			loadThisTableFields(thisWeek);
		} else {
			Map map = fetchData();
			againSetThisTable((List) map.get("thisWeek"));
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
		btnAddLine.setVisible(false);
		btnCopy.setVisible(false);
		btnCopyFrom.setVisible(false);
		btnCopyLine.setVisible(false);
		btnCreateTo.setVisible(false);
		btnCreateFrom.setVisible(false);
		btnAddNew.setVisible(false);
		btnFirst.setVisible(false);
		btnInsertLine.setVisible(false);
		btnLast.setVisible(false);
		btnMultiapprove.setVisible(false);
		btnNext.setVisible(false);
		btnNextPerson.setVisible(false);
		btnFirst.setVisible(false);
		btnPageSetup.setVisible(false);
		btnPre.setVisible(false);
		btnCancelCancel.setVisible(false);
		btnRemoveLine.setVisible(false);
		btnRemove.setVisible(false);
		btnReset.setVisible(false);
		btnTraceDown.setVisible(false);
		btnTraceUp.setVisible(false);
		/* modfied by zhaoqin for R20141225-0158 on 2015/01/15 */
		//btnWorkFlowG.setVisible(false);
		btnWFViewdoProccess.setVisible(false);
		btnWFViewSubmitProccess.setVisible(false);
		btnCalculator.setVisible(false);
		btnPrintReport.setEnabled(true);
		chkMenuItemSubmitAndAddNew.setSelected(false);//����Ҫ��������
	}

	protected void thisTable_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getColIndex() == thisTable.getColumn("description").getColumnIndex()) {
			if (e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 1) {
				thisTable.showCellDetailInfo();
			}
		}
		
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2&&
				this.thisTable.getColumnKey(e.getColIndex()).equals("attach")) {
			String id=((FDCScheduleTaskInfo)this.thisTable.getRow(e.getRowIndex()).getCell("taskID").getValue()).getId().toString();
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
	        boolean isEdit = false;
	        if((OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) &&!FDCBillStateEnum.AUDITTED.equals(this.editData.getState()))
	            isEdit = true;
	        AttachmentUIContextInfo info = new AttachmentUIContextInfo();
	        info.setBoID(id);
	        info.setEdit(isEdit);
	        String multi = (String)getUIContext().get("MultiapproveAttachment");
	        if(multi != null && multi.equals("true")){
	        	acm.showAttachmentListUIByBoIDNoAlready(this, info);
	        }else{
	        	acm.showAttachmentListUIByBoID(this, info);
	        }
	        this.thisTable.getRow(e.getRowIndex()).getCell("attach").setValue(loadAttachment(id));
		}
	}
	protected String loadAttachment(String id) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", id));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("attachment.name");
		view.setSelector(sels);
		BoAttchAssoCollection col = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		String name="";
		for(int i=0;i<col.size();i++){
			name=name+col.get(i).getAttachment().getName()+" ";
		}
		return name;
	}

	/**
	 * @description ���������б��ɱ༭
	 * @author ����ΰ
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */
	protected void nextTable_tableClicked(KDTMouseEvent e) throws Exception {
		this.nextTable.getStyleAttributes().setLocked(true);
	}

	/**
	 * @description ���������б������ݿɱ༭
	 * @author ����ΰ
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */
	protected void thisTable_editStopped(KDTEditEvent e) throws Exception {
		thisTable.checkParsed();
		IRow row = this.thisTable.getRow(this.thisTable.getSelectManager().getActiveRowIndex());
		if (null == row) {
			SysUtil.abort();
		}
		if (e.getColIndex() == thisTable.getColumn("completePercent").getColumnIndex()) {
			Object completePercent = row.getCell("completePercent").getValue();
			if (completePercent != null && !"".equals(completePercent)) {
				int intValue_completePercent = new Integer(completePercent.toString()).intValue();
				if (100 == intValue_completePercent) {
					row.getCell("isComplete").setValue(WeekReportEnum.YES);
					if (row.getCell("realEndDate").getValue() == null) {
						if(isDisableChEndDate){
							row.getCell("realEndDate").getStyleAttributes().setLocked(true);
						}else{
							row.getCell("realEndDate").getStyleAttributes().setLocked(false);
							row.getCell("realEndDate").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
						}
						row.getCell("intendEndDate").getStyleAttributes().setLocked(true);
						
						/* modified by zhaoqin for ������������ on 2014/10/14 start */
						//row.getCell("realEndDate").setValue(FDCDateHelper.getServerTimeStamp());
						Date actualStartDate = (Date)row.getCell("actualStartDate").getValue();
						Date now = new Date();
						if(DateUtils.diffdates(actualStartDate, now) >= 0) {
							row.getCell("realEndDate").setValue(now);
						} else {
							row.getCell("realEndDate").setValue(actualStartDate);
						}
						/* modified by zhaoqin for ������������ on 2014/10/14 end */
						
					} else {
						row.getCell("realEndDate").getStyleAttributes().setBackground(Color.WHITE);
					}
				} else {
					row.getCell("isComplete").setValue(WeekReportEnum.NO);
					row.getCell("intendEndDate").getStyleAttributes().setLocked(false);
					row.getCell("realEndDate").getStyleAttributes().setLocked(true);
				}
			}
			resetCompletePercent(row, e);
			if (completePercent == null || 0 == new Integer(completePercent.toString()).intValue()) {
				row.getCell("completeAmount").getStyleAttributes().setLocked(true);
			} else {
				row.getCell("completeAmount").getStyleAttributes().setLocked(false);
			}
		}
		if (e.getColIndex() == thisTable.getColumn("isComplete").getColumnIndex()) {
			Object isComplete = row.getCell("isComplete").getValue();
			Object completePercent = row.getCell("completePercent").getValue();
			if (completePercent == null || 0 == new Integer(completePercent.toString()).intValue()) {
				row.getCell("completeAmount").getStyleAttributes().setLocked(true);
			} else {
				row.getCell("completeAmount").getStyleAttributes().setLocked(false);
			}
			if (isComplete == null) {
				isComplete = WeekReportEnum.NO;
				row.getCell("isComplete").setValue(isComplete);
			}
			
			if (isComplete.equals(WeekReportEnum.YES)) {
				row.getCell("completePercent").setValue(new Integer(100));
				row.getCell("completePercent").getStyleAttributes().setLocked(true);
				row.getCell("realEndDate").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
	
				/* modified by zhaoqin for ������������ on 2014/10/14 start */
				//row.getCell("realEndDate").setValue(FDCDateHelper.getServerTimeStamp());
				Date actualStartDate = (Date)row.getCell("actualStartDate").getValue();
				Date now = new Date();
				if(DateUtils.diffdates(actualStartDate, now) >= 0) {
					row.getCell("realEndDate").setValue(now);
				} else {
					row.getCell("realEndDate").setValue(actualStartDate);
				}
				/* modified by zhaoqin for ������������ on 2014/10/14 end */
	
				row.getCell("intendEndDate").getStyleAttributes().setLocked(true);
				row.getCell("realEndDate").getStyleAttributes().setLocked(false);
			} else {
				row.getCell("completePercent").getStyleAttributes().setLocked(false);
				row.getCell("realEndDate").getStyleAttributes().setLocked(true);
				row.getCell("realEndDate").setValue(null);
				row.getCell("intendEndDate").getStyleAttributes().setLocked(false);
				// ����ط��϶����ܱ����
				if (completePercent != null && !"".equals(completePercent) && 100 == new Integer(completePercent.toString()).intValue()) {
					row.getCell("completePercent").setValue(100 - 1);
				}
				row.getCell("realEndDate").getStyleAttributes().setBackground(Color.WHITE);
			}
		}
		if (e.getColIndex() == thisTable.getColumn("intendEndDate").getColumnIndex()) {
			verifyIntendEndDate(row, e);
		}
		
		/* modified by zhaoqin for ������������ on 2014/10/14 start */
		if (e.getColIndex() == thisTable.getColumn("realEndDate").getColumnIndex()) {
			Date actualStartDate = (Date)row.getCell("actualStartDate").getValue();
			Date realEndDate = (Date)e.getValue();
			//ʵ��������ڲ�������ʵ�ʿ�ʼ����
			if(DateUtils.diffdates(actualStartDate, realEndDate) < 0) {
				row.getCell("realEndDate").setValue(e.getOldValue());
				FDCMsgBox.showWarning("ʵ��������ڲ�������ʵ�ʿ�ʼ����");
				SysUtil.abort();
			}
		}
		/* modified by zhaoqin for ������������ on 2014/10/14 end */
	}

	public void lockedCell(Object completePercent, IRow row) {
		if (completePercent == null || "".equals(completePercent)) {
			completePercent = new Integer(0);
		}
			if (100 == new Integer(completePercent.toString()).intValue()) {
				row.getCell("intendEndDate").getStyleAttributes().setLocked(true);
//				row.getCell("completePercent").getStyleAttributes().setLocked(true);
//				row.getCell("isComplete").getStyleAttributes().setLocked(true);
				row.getCell("realEndDate").getStyleAttributes().setLocked(false);
			} else {
				row.getCell("intendEndDate").getStyleAttributes().setLocked(false);
//				row.getCell("completePercent").getStyleAttributes().setLocked(false);
//				row.getCell("isComplete").getStyleAttributes().setLocked(false);
				row.getCell("realEndDate").getStyleAttributes().setLocked(true);
			}
//			if (0 == new Integer(completePercent.toString()).intValue()) {
//				row.getCell("completeAmount").getStyleAttributes().setLocked(true);
//			} else {
//				row.getCell("completeAmount").getStyleAttributes().setLocked(false);
//			}
		}

	/**
	 * 
	 * @description ������ɽ���
	 * @author �ź���
	 * @createDate 2011-12-26
	 * @param row
	 *            void
	 * @version EAS7.0
	 * @see
	 */
	public void resetCompletePercent(IRow row, KDTEditEvent e) {
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		if (oldValue == null || "".equals(oldValue)) {
			oldValue = new Integer(0);
		}
		if (newValue == null || "".equals(newValue)) {
			newValue = new Integer(0);
		}
		if (new Integer(newValue.toString()).intValue() > 100 || new Integer(newValue.toString()).intValue() < 0) {
			row.getCell("completePercent").setValue(oldValue);
		}
	}

	/**
	 * 
	 * @description ��֤Ԥ���������
	 * @author �ź���
	 * @createDate 2011-12-26
	 * @param row
	 *            void
	 * @version EAS7.0
	 * @see
	 */
	public void verifyIntendEndDate(IRow row, KDTEditEvent e) {
		Date oldValue = (Date) e.getOldValue();
		Date newValue = (Date) e.getValue();
		boolean error = false;
		if (oldValue == null && newValue == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (oldValue == null && newValue != null) {
			if (DateUtils.diffdates(newValue, new Date()) > 0) {
				error = true;
			}
		}
		if (oldValue instanceof java.sql.Date || oldValue instanceof java.util.Date) {
			if (!sdf.format(oldValue).equals(sdf.format(newValue))) {
				Date intendEndDate = (Date) row.getCell("intendEndDate").getValue();
				if (intendEndDate != null && DateUtils.diffdates(intendEndDate, new Date()) > 0) {
					error = true;
				}
			}
		}
		if (error) {
			row.getCell("intendEndDate").setValue(oldValue);
		}
	}

	public ProjectSpecialInfo projectSpecialInfo;

	/**
	 * @description �鿴��ʷ��¼
	 * @author ����ΰ
	 * @createDate 2011-8-30
	 * @version EAS7.0
	 * @see
	 */
	public void actionHistory_actionPerformed(ActionEvent e) throws Exception {
		CurProjectInfo curProjectInfo = getCurProject();
		this.curProjectInfo = curProjectInfo;
		UIContext uiContext = new UIContext(this);
		getUIContext().put("curprojectInfo", curProjectInfo);
		if (editData.getProjectSpecial() != null)
			this.projectSpecialInfo = editData.getProjectSpecial();
		uiContext.put(UIContext.ID, null);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(ProjectWeekReportListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}

	public boolean isModify() {
		return false;
	}

	public void beforeSave() {
		int num = this.thisTable.getRowCount();
		if (num > 0) {
			for (int i = 0; i < this.thisTable.getRowCount(); i++) {
				IRow row = this.thisTable.getRow(i);
				if (row.getCell("isComplete").getValue().equals(WeekReportEnum.YES)) {
					if (row.getCell("realEndDate").getValue() == null || "".equals(row.getCell("realEndDate").getValue())
							|| row.getCell("completePercent").getValue() == null || "".equals(row.getCell("completePercent").getValue())) {
						FDCMsgBox.showWarning("��ɽ���Ϊ100%ʱ,ʵ��������ڲ���Ϊ�գ�");
						SysUtil.abort();
					}
				}
				verifyCompletePercent(row);
			}
		}
	}

	/**
	 * 
	 * @description ��֤��ɽ���,�������ڼƻ������еĽ������ֵ
	 * @author �ź���
	 * @createDate 2011-12-26
	 * @version EAS7.0
	 */
	public void verifyCompletePercent(IRow row) {
		BOSUuid entryID = null;
		entryID = (BOSUuid) row.getCell("entryID").getValue();
		if (entryID == null) {
			entryID = ((FDCScheduleTaskInfo) row.getCell("taskID").getValue()).getId();
		}
		Object completePercent = row.getCell("completePercent").getValue();
		if (completePercent == null || "".equals(completePercent)) {
			completePercent = new Integer(0);
		}
		if (entryID != null && completePercent != null) {
			int temp = ((Integer) completePercent).intValue();
			ProjectWeekReportEntryInfo entryInfo = (ProjectWeekReportEntryInfo) row.getUserObject();
			int lastTaskComplete = entryInfo.getRelateTask().getComplete().intValue();
			if (lastTaskComplete > temp) {
				FDCMsgBox.showWarning(this, row.getCell("taskName").getValue() + "������ɽ���С���ϴλ㱨��¼!");
				SysUtil.abort();
			}
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {

		beforeSave();
		comboState.setSelectedItem(FDCBillStateEnum.SAVED);
		super.actionSave_actionPerformed(e);
		prmtCreator.setValue(SysContext.getSysContext().getCurrentUserInfo());
		this.thisTable.getStyleAttributes().setLocked(true);
		this.btnSave.setEnabled(false);
		this.btnEdit.setVisible(true);
		this.btnEdit.setEnabled(true);
		this.menuItemEdit.setEnabled(true);
		this.menuItemSave.setEnabled(false);
		this.txtDescription.setEnabled(false);
		this.thisTable.getStyleAttributes().setLocked(true);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		setBtnStateByBillState();
		this.txtDescription.setEnabled(true);
		this.thisTable.getStyleAttributes().setLocked(false);
		setTableUnEnable();
		for (int i = 0; i < thisTable.getRowCount(); i++) {
			lockedCell(thisTable.getRow(i).getCell("completePercent").getValue(), thisTable.getRow(i));
		}
	}
	
	
	private void setBtnStateByBillState() {
		if (isFromListUI()) {
			return;
		}
		FDCBillStateEnum state = editData.getState();
		if (FDCBillStateEnum.SAVED.equals(state)) {
			this.actionSave.setEnabled(true);
			this.btnSave.setEnabled(true);
			this.menuItemSave.setEnabled(true);
			this.menuItemSubmit.setEnabled(true);
			this.btnSubmit.setEnabled(true);
			this.actionSubmit.setEnabled(true);
			this.menuItemAudit.setEnabled(false);
			this.btnAudit.setEnabled(false);
			this.actionAudit.setEnabled(false);
		} else if (FDCBillStateEnum.SUBMITTED.equals(state)) {
			this.actionSave.setEnabled(false);
			this.btnSave.setEnabled(false);
			this.menuItemSave.setEnabled(false);
			this.actionSubmit.setEnabled(true);
			this.btnSubmit.setEnabled(true);
			this.menuItemSave.setEnabled(true);
			this.menuItemAudit.setEnabled(true);
			this.btnAudit.setEnabled(true);
			this.actionAudit.setEnabled(true);
		} else {
			this.btnEdit.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionSave.setEnabled(false);
			this.btnSave.setEnabled(false);
			this.menuItemSave.setEnabled(false);
			this.actionSubmit.setEnabled(false);
			this.btnSubmit.setEnabled(false);
			this.menuItemSave.setEnabled(false);
			this.menuItemAudit.setEnabled(false);
			this.btnAudit.setEnabled(false);
			this.actionAudit.setEnabled(false);
		}
	}
	private boolean isFromListUI() {
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof ProjectWeekReportListUI) {
			return true;
		}
		return false;
	}

	/**
	 * @description ������
	 * @author ����ΰ
	 * @createDate 2011-9-1
	 * @version EAS7.0
	 * @see
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (comboState.getSelectedItem() != null) {
			if (comboState.getSelectedItem().equals(FDCBillStateEnum.AUDITTED)) {
				ProjectWeekReportFactory.getRemoteInstance().unAudit(BOSUuid.read(editData.getId().toString()));
				FDCMsgBox.showWarning("�����ɹ�");
				this.btnUnAudit.setEnabled(false);
				this.btnAudit.setEnabled(true);
				editData.setState(FDCBillStateEnum.SUBMITTED);
				comboState.setSelectedItem(FDCBillStateEnum.SUBMITTED);
			} else {
				FDCMsgBox.showWarning("����״̬��������д˲���");
			}
		}
	}

	/**
	 * @description ����
	 * @author ����ΰ
	 * @createDate 2011-9-1
	 * @version EAS7.0
	 * @see
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (isModify()) {
			FDCMsgBox.showInfo("�����ѱ��޸ģ������ύ��");
			this.abort();
		}

		if (getSelectBOID() != null) {
			editData.setState(FDCBillStateEnum.AUDITTED);
			ProjectWeekReportFactory.getRemoteInstance().audit(editData.getId());
			this.btnAudit.setEnabled(false);
			comboState.setSelectedItem(FDCBillStateEnum.AUDITTED);
			FDCClientUtils.showOprtOK(this);
			setSaveActionStatus();
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		beforeSave();
		comboState.setSelectedItem(FDCBillStateEnum.SUBMITTED);
		Date firstDate = (Date) pkStartDate.getValue();
		Date lastDate = (Date) pkEndDate.getValue();
		super.actionSubmit_actionPerformed(e);
		comboState.setSelectedItem(FDCBillStateEnum.SUBMITTED);
		prmtCreator.setValue(SysContext.getSysContext().getCurrentUser());
		if (pkStartDate.getValue() == null && pkEndDate.getValue() == null) {
			this.pkStartDate.setValue(firstDate);
			this.pkEndDate.setValue(lastDate);
		}
		this.btnEdit.setVisible(true);
		this.btnEdit.setEnabled(true);
		this.menuItemSubmit.setEnabled(true);
		submitAfterNotEdit();
	}

	/**
	 * �ύ�����޸ı�
	 * 
	 * @description
	 * @author
	 * @createDate 2011-9-1
	 * @version EAS7.0
	 * @see
	 */
	public void submitAfterNotEdit() {
		// this.btnEdit.setEnabled(false);
		this.btnSave.setEnabled(false);
		this.actionAudit.setEnabled(true);
		this.menuItemEdit.setEnabled(false);
		this.menuItemSave.setEnabled(false);
		this.btnSubmit.setEnabled(false);
		txtDescription.setEnabled(false);
		thisTable.getStyleAttributes().setLocked(true);
	}

	/**
	 * @description ��ӡ����
	 * @author ����ΰ
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	public void actionPrintReport_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() == null) {
			FDCMsgBox.showWarning("���ȱ��浥��");
			return;
		}
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, null);
			uiContext.put("reportInfo", editData);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			IUIWindow uiWindow = uiFactory.create(ViewProjectWeekReportPrintUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
	}

	/**
	 * @description ����Excel
	 * @author ����ΰ
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception {
		List tables = new ArrayList();
		tables.add(new Object[] { "��������", thisTable });
		tables.add(new Object[] { "��������", nextTable });
		FDCTableHelper.exportExcel(this, tables);
	}
	
	protected String getReportTableName() {
		return "t_sch_projectweekreport";
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProjectWeekReportFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return null;
	}

	public void loadNextTableFields() {

	}

	public void loadThisTableFields() {

	}

}