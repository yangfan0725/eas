/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.CustomerCardUI;
import com.kingdee.eas.fdc.sellhouse.client.CustomerEditUI;
import com.kingdee.eas.fdc.sellhouse.client.RoomSelectUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.BizStateEnum;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fdc.tenancy.SinCustomerEntrysCollection;
import com.kingdee.eas.fdc.tenancy.SinCustomerEntrysInfo;
import com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryCollection;
import com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryInfo;
import com.kingdee.eas.fdc.tenancy.SincerObligateFactory;
import com.kingdee.eas.fdc.tenancy.SincerObligateInfo;
import com.kingdee.eas.fdc.tenancy.SincerPaylistEntrysCollection;
import com.kingdee.eas.fdc.tenancy.SincerPaylistEntrysInfo;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class SincerObligateEditUI extends AbstractSincerObligateEditUI
{
	private static final Logger logger = CoreUIObject.getLogger(SincerObligateEditUI.class);
	private UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	public SincerObligateEditUI() throws Exception
	{
		super();
	}

	public void storeFields()
	{
		super.storeFields();
		storeSinRoomsEntry();
		storeSinAttachResources();
		storeSinCustomer();
		storeSinReceive();
	}

	public void loadFields() {
		this.pkPlightStrartDate.setValue(null);
		this.pkPlightEndDate.setValue(null);
		super.loadFields();
		SincerObligateInfo sinInfo = this.editData;
		this.chkIsRecSincer.setSelected(sinInfo.isIsSincerReceive());
		this.txtSellProjectNumber.setText(sinInfo.getSellProject().getNumber());
		this.f7SellProject.setValue(sinInfo.getSellProject());
		this.tblRooms.removeRows();

		addRoomRows(sinInfo.getSinRoomList());
		loadCustomers(sinInfo.getSinCustomerList());

		if(this.chkIsRecSincer.isSelected())
		{
			SincerPaylistEntrysCollection sinPaylistColl = sinInfo
			.getPayListEntrys();
			loadSincerPayList(sinPaylistColl);
		}
		
		updateTotalRent();
	}

	public void actionExecute_actionPerformed(ActionEvent e) throws Exception {
		super.actionExecute_actionPerformed(e);
		if (this.editData != null) {
			Boolean isExecuted = Boolean.valueOf(this.editData.isIsExecuted());
			if (isExecuted.booleanValue()) {
				MsgBox.showInfo("����Ԥ�����Ѿ�ִ��!");
				return;
			}
			if (this.editData.getAuditor() == null) {
				MsgBox.showInfo("����Ԥ����û�����!");
				return;
			}
			BizStateEnum state = (BizStateEnum) this.editData.getBizState();
			if (BizStateEnum.INVALID_VALUE.equals(state.getValue())) {
				MsgBox.showInfo("����Ԥ�����Ѿ�����!");
				return;
			}
			if (MsgBox.showConfirm2New(this, "�Ƿ�ִ��?") == MsgBox.YES) {
				String id = this.editData.getId().toString();
				if (SincerObligateFactory.getRemoteInstance().execute(id)) {
					MsgBox.showInfo("ִ�гɹ�!");
					actionUnAudit.setEnabled(false);
				}
			}
		}
	}
	private void loadSincerPayList(SincerPaylistEntrysCollection sinPaylistColl)
	{
		IRow row = null;
		this.tblSinReceive.removeRows();
		for (int i = 0; i < sinPaylistColl.size(); i++) {
			this.tblSinReceive.checkParsed();
			row = this.tblSinReceive.addRow();
			SincerPaylistEntrysInfo sincerPayListInfo = sinPaylistColl.get(i);
			row.getCell("moneyTypeName").setValue(
					sincerPayListInfo.getMoneyDefine());
			row.getCell("appAmount").setValue(
					sincerPayListInfo.getAppAmount());
			row.getCell("actAmount").setValue(
					sincerPayListInfo.getActRevAmount());
			row.getCell("bizDate")
					.setValue(sincerPayListInfo.getActRevDate());
			row.getCell("refundmentAmount").setValue(
					sincerPayListInfo.getHasRefundmentAmount());
			row.getCell("canRefundmentAmount").setValue(
					sincerPayListInfo.getRemainLimitAmount());
			//TODO ��ת���
			row.getCell("hasTransferredAmount").setValue(sincerPayListInfo.getHasTransferredAmount());
			row.setUserObject(sincerPayListInfo);
		}
	}

	private void loadCustomers(SinCustomerEntrysCollection sinCustomerList) {
		this.tblCustomer.removeRows();
		this.tblCustomer.checkParsed();
		for(int i=0; i<sinCustomerList.size(); i++){
			SinCustomerEntrysInfo sinCustomer = sinCustomerList.get(i);
			IRow row = this.tblCustomer.addRow();
			row.setHeight(20);
			row.setUserObject(sinCustomer);
			row.getCell("propertyPercent").setValue(
					sinCustomer.getPropertyPercent());
			row.getCell("description").setValue(sinCustomer.getDescription());
			FDCCustomerInfo customer = sinCustomer.getFdcCustomer();
			if (customer != null) {
				row.getCell("customer").setValue(customer);
				row.getCell("postalcode").setValue(customer.getPostalcode());
				row.getCell("phone").setValue(customer.getPhone());
				row.getCell("certificateName").setValue(
						customer.getCertificateName());
				row.getCell("certificateNumber").setValue(
						customer.getCertificateNumber());
				row.getCell("mailAddress").setValue(customer.getMailAddress());
				row.getCell("bookDate").setValue(customer.getCreateTime());
			}
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.btnExecute.setVisible(false);
		initControl();
		initRoomTable();
		initCustomeTable();
		initSincerReceiveTable();
		//TODO ��ʱ����������Դ
		if(tabbedPaneRoom.getTabCount() == 2){
			this.tabbedPaneRoom.remove(1);
		}
		actionExecute.setEnabled(true);
		actionBlankOut.setVisible(false);
		this.actionAuditResult.setVisible(false);
		btnExecute.setIcon(EASResource.getIcon("imgTbtn_execute"));
		satisfaction.setDataType(0);
		satisfaction.setDataVerifierType(12);
		satisfaction.setMaximumValue(FDCHelper.ONE_HUNDRED);
		satisfaction.setMinimumValue(FDCHelper.ZERO);
		
		
		
		if(STATUS_VIEW.equals(this.getOprtState()))
		{
			this.btnAddAttach.setEnabled(false);
			this.btnDelAttach.setEnabled(false);
			this.btnAddCustomer.setEnabled(false);
			this.btnAddNewCustomer.setEnabled(false);
			this.btnDelCustomer.setEnabled(false);
			this.btnAddRooms.setEnabled(false);
			this.btnDelRooms.setEnabled(false);
			this.btnAddPayList.setEnabled(false);
			this.btndelPayList.setEnabled(false);
		}if(STATUS_EDIT.equals(this.getOprtState()))
		{
			this.txtTotalRoomStandardRent.setEnabled(false);
			this.txtTotalBuildingArea.setEnabled(false);
			this.txtPlightRoomDealRent.setEnabled(false);
			this.txtPlightAttachRent.setEnabled(false);
			this.txtTotalAttachResStandardRent.setEnabled(false);
			this.txtName.setEnabled(false);
			this.txtNumber.setEnabled(false);
		}
		//ִ�еĵ��ݲ��ܽ��з�����
		Boolean isExecuted = Boolean.valueOf(this.editData.isIsExecuted());
		if (isExecuted.booleanValue()) {
			actionUnAudit.setEnabled(false);
		}
		//ת����״̬�ĵ��ݲ��ܽ���������ִ��
		BizStateEnum state = (BizStateEnum) this.editData.getBizState();
		if (BizStateEnum.LEASE.equals(state)) {
			actionExecute.setEnabled(false);
			actionAudit.setEnabled(false);
		}
		this.storeFields();
		initOldData(this.editData);
		FDCHelper.formatTableDate(tblCustomer, "bookDate");
		
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		BizStateEnum state = (BizStateEnum) this.editData.getBizState();
		if (!BizStateEnum.SUBMIT_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("�ύ״̬����Ԥ������������!");
			return;
		}
		if(this.editData.getId()!=null)
		SincerObligateFactory.getRemoteInstance().audit(BOSUuid.read(this.editData.getId().toString()));
		super.actionAudit_actionPerformed(e);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getId()!=null)
			SincerObligateFactory.getRemoteInstance().unAudit(BOSUuid.read(this.editData.getId().toString()));
		super.actionUnAudit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		BizStateEnum state = (BizStateEnum) this.editData.getBizState();
		if(!BizStateEnum.SAVE_VALUE.equals(state.getValue()) && !BizStateEnum.SUBMIT_VALUE.equals(state.getValue()))
		 {
			 MsgBox.showInfo("��������ύ״̬��Ԥ��������ɾ��!");
			 return;
		 }
		super.actionRemove_actionPerformed(e);
	}
	/** ��ʼ���ؼ���������,��Ҫ���ò˵�,�ؼ��ɷ�༭(��״̬����仯) */
	private void initControl(){
		this.menuTable1.setVisible(false);

		this.actionCopy.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionCopyFrom.setVisible(false);

		this.actionCreateFrom.setVisible(false);

		this.actionAddLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);

		this.txtTotalBuildingArea.setEditable(false);
		this.txtTotalRoomStandardRent.setEditable(false);
		this.txtPlightRoomDealRent.setEditable(false);

		this.txtTotalAttachResStandardRent.setEditable(false);
		this.txtPlightAttachRent.setEditable(false);

		this.txtSellProjectNumber.setEnabled(false);
		this.f7SellProject.setEnabled(false);
		
		this.pkPlightStrartDate.setRequired(true);
		this.pkPlightEndDate.setRequired(true);

		SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 100000, 1);
		this.spinTermLength.setModel(model);
		model = new SpinnerNumberModel(0, 0, 100000, 1);
		this.spinFreeDays.setModel(model);
		model = new SpinnerNumberModel(0, 0, 100000, 1);
		this.spinObligateDateCount.setModel(model);
		
		setSincerPayListVisable();
	}

	/** ��ʼ������Ԥ��������ϸ�б� 
	 * @throws BOSException 
	 * @throws EASBizException */
	private void initSincerReceiveTable()
	{
		this.tblSinReceive.checkParsed();
		this.tblSinReceive
		.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblSinReceive.getColumn("bizDate").getStyleAttributes()
		.setLocked(true);
		this.tblSinReceive.getColumn("refundmentAmount").getStyleAttributes()
		.setLocked(true);
		this.tblSinReceive.getColumn("hasTransferredAmount").getStyleAttributes()
		.setLocked(true);
		this.tblSinReceive.getColumn("canRefundmentAmount")
		.getStyleAttributes().setLocked(true);
		this.tblSinReceive.getColumn("actAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblSinReceive.getColumn("actAmount").getStyleAttributes()
		.setLocked(true);
		this.tblSinReceive.getColumn("appAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		KDBizPromptBox f7MoenyDefine = new KDBizPromptBox();
		f7MoenyDefine
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("sysType",
						MoneySysTypeEnum.TENANCYSYS_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType", MoneyTypeEnum.PREMONEY_VALUE));
		view.setFilter(filter);
		f7MoenyDefine.setEntityViewInfo(view);
		f7MoenyDefine.setEditable(true);
		f7MoenyDefine.setDisplayFormat("$name$");
		f7MoenyDefine.setEditFormat("$number$");
		f7MoenyDefine.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7MoenyDefine);
		this.tblSinReceive.getColumn("moneyTypeName").setEditor(f7Editor);

		this.tblSinReceive.getColumn("appAmount").getStyleAttributes()
				.setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblSinReceive.getColumn("moneyTypeName").getStyleAttributes()
				.setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblSinReceive.getColumn("appAmount").setEditor(numberEditor);
		this.tblSinReceive.getColumn("appAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblSinReceive.getColumn("actAmount").setEditor(numberEditor);
		this.tblSinReceive.getColumn("actAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		// this.tblSincerPrice.getColumn("refundmentAmount").setEditor(numberEditor);
		this.tblSinReceive.getColumn("refundmentAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblSinReceive.getColumn("refundmentAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		// this.tblSincerPrice.getColumn("canRefundmentAmount").setEditor(numberEditor);
		this.tblSinReceive.getColumn("canRefundmentAmount")
				.getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblSinReceive.getColumn("canRefundmentAmount")
				.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);
		
		this.tblSinReceive.getColumn("hasTransferredAmount")
		.getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
        this.tblSinReceive.getColumn("hasTransferredAmount")
		.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);

		String formatString = "yyyy-MM-dd";
		this.tblSinReceive.getColumn("bizDate").getStyleAttributes()
				.setNumberFormat(formatString);
		

	}

	/** ��ʼ���ͻ��б� 
	 * @throws BOSException 
	 * @throws EASBizException */
	private void initCustomeTable() throws BOSException, EASBizException {
		this.tblCustomer.checkParsed();
		this.tblCustomer.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblCustomer.getColumn("bookDate").getStyleAttributes().setLocked(true);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblCustomer.getColumn("propertyPercent").setEditor(numberEditor);
		this.tblCustomer.getColumn("propertyPercent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblCustomer.getColumn("propertyPercent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		//this.tblCustomer.getColumn("propertyPercent").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		KDBizPromptBox f7Customer = new KDBizPromptBox();
		f7Customer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
		f7Customer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(this.editData.getSellProject(),userInfo));
		f7Customer.setEditable(true);
		f7Customer.setDisplayFormat("$name$");
		f7Customer.setEditFormat("$number$");
		f7Customer.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Customer);
		this.tblCustomer.getColumn("customer").setEditor(f7Editor);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn("description").setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn("phone").setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn("mailAddress").setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn("postalcode").setEditor(txtEditor);
		KDComboBox comboField = new KDComboBox();
		List list = CertifacateNameEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		this.tblCustomer.getColumn("certificateName").setEditor(comboEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn("certificateNumber").setEditor(txtEditor);
		//		setCustomerInfoEnable(true);
	}

	private void initRoomTable()
	{
		this.tblRooms.checkParsed();
		this.tblRooms.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		
		this.tblRooms.getColumn("actDeliverDate").getStyleAttributes().setLocked(true);
		
		this.tblRooms.getColumn("roomState").setEditor(createTxtCellEditor(80, false));
		this.tblRooms.getColumn("room").setEditor(createTxtCellEditor(80, false));
		this.tblRooms.getColumn("floor").setEditor(createTxtCellEditor(80, false));

		this.tblRooms.getColumn("standardRent").setEditor(createFormattedCellEditor(false));
		this.tblRooms.getColumn("standardRent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("standardRent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		KDComboBox comboField = new KDComboBox();
		List list = RentTypeEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		this.tblRooms.getColumn("standardRentType").setEditor(comboEditor);
		this.tblRooms.getColumn("standardRentType").getStyleAttributes().setLocked(true);

		this.tblRooms.getColumn("standardRentPrice").setEditor(createFormattedCellEditor(false));
		this.tblRooms.getColumn("standardRentPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("standardRentPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblRooms.getColumn("plightRent").setEditor(createFormattedCellEditor());
		this.tblRooms.getColumn("plightRent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("plightRent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		comboField = new KDComboBox();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		comboEditor = new KDTDefaultCellEditor(comboField);
		this.tblRooms.getColumn("plightRentType").setEditor(comboEditor);

		this.tblRooms.getColumn("plightRentPrice").setEditor(createFormattedCellEditor());
		this.tblRooms.getColumn("plightRentPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("plightRentPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRooms.getColumn("plightRentPrice").getStyleAttributes().setLocked(true);

		this.tblRooms.getColumn("description").setEditor(createTxtCellEditor(255, false));

		this.tblRooms.getColumn("tenancyArea").setEditor(createFormattedCellEditor(false));
		this.tblRooms.getColumn("tenancyArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("tenancyArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblRooms.getColumn("buildingArea").setEditor(createFormattedCellEditor(false));
		this.tblRooms.getColumn("buildingArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRooms.getColumn("buildingArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblRooms.getColumn("fitment").setEditor(createTxtCellEditor(80, false));
		this.tblRooms.getColumn("roomModel").setEditor(createTxtCellEditor(80, false));
		this.tblRooms.getColumn("direction").setEditor(createTxtCellEditor(80, false));

		this.tblRooms.getColumn("actDeliverDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");

	}

	protected ICoreBase getBizInterface() throws Exception {
		return SincerObligateFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	/**
	 * ����һ�����Ԥ�������¼�����ڷ����б�������һ��
	 * @param tenancyRoom
	 * */
	private void addRoomRow(SinObligateRoomsEntryInfo sinRoom){
		if(sinRoom == null){
			return;
		}
		RoomInfo room = sinRoom.getRoom();
		if(room == null){//����ROOM����ΪNull��
			return;
		}

		IRow row = this.tblRooms.addRow();
		row.setUserObject(sinRoom);
		row.getCell("roomState").setValue(sinRoom.getTenRoomState());
		row.getCell("room").setValue(sinRoom.getRoomLongNum());
		row.getCell("floor").setValue(new Integer(room.getFloor()));
		row.getCell("standardRent").setValue(sinRoom.getStandardRoomRent());
		row.getCell("standardRentType").setValue(sinRoom.getStandardRentType());
		row.getCell("standardRentPrice").setValue(sinRoom.getStandardRoomRentPrice());
		row.getCell("plightRent").setValue(sinRoom.getPlightRoomRent());
		RentTypeEnum plightType = sinRoom.getPlightRentType();
		if(plightType==null)
		{
			row.getCell("plightRentType").setValue(RentTypeEnum.RentByMonth);
		}else
		{
			row.getCell("plightRentType").setValue(sinRoom.getPlightRentType());
		}

		row.getCell("plightRentPrice").setValue(sinRoom.getPlightRoomRentPrice());

		row.getCell("description").setValue(room.getDirection()==null ? null : room.getDirection().getName());
		row.getCell("tenancyArea").setValue(sinRoom.getTenancyArea());
		row.getCell("buildingArea").setValue(sinRoom.getBuildingArea());
		row.getCell("fitment").setValue(room.getFitmentStandard());

		row.getCell("roomModel").setValue(room.getRoomModel()==null ? null : room.getRoomModel().getName());
		row.getCell("direction").setValue(room.getDirection());
		row.getCell("actDeliverDate").setValue(sinRoom.getActDeliveryRoomDate());

	}

	private void addRoomRows(SinObligateRoomsEntryCollection sinRooms){
		for(int i=0; i<sinRooms.size(); i++){
			SinObligateRoomsEntryInfo sinRoom = sinRooms.get(i);
			this.tblRooms.checkParsed();
			addRoomRow(sinRoom);
		}
	}

	/**
	 * ���ݷ����װһ������Ԥ�������¼ <br>
	 * ����׼���,�������,���䳤���뱸���ڳ���Ԥ�������¼��; <br>
	 * Ĭ�����óɽ��۸�Ϊ����ı�׼�۸�,���õ��ڱ��Ϊδ֪,�������޷���״̬Ϊ����
	 * @param room
	 * 			�������
	 * @return ����Ԥ����¼����
	 * */
	private SinObligateRoomsEntryInfo roomToSinRoomEntry(RoomInfo room){
		if(room == null){
			return null;
		}
		SinObligateRoomsEntryInfo sinRoom = new SinObligateRoomsEntryInfo();
		sinRoom.setRoom(room);
		sinRoom.setStandardRentType(room.getRentType());
		sinRoom.setStandardRoomRent(room.getStandardRent());
		sinRoom.setStandardRoomRentPrice(room.getStandardRentPrice());

		//ִ�����Ĭ�ϸ���׼���۸�
		//    	tenRoom.setDealRentType(room.getRentType());
		//    	tenRoom.setDealRoomRent(room.getStandardRent());
		//    	tenRoom.setDealRoomRentPrice(room.getStandardRentPrice());

		//ȡ����Ľ��������Ϊ�������.����ȡʵ�⽨��,���û����ȡ�������
		BigDecimal area = room.getActualBuildingArea();
		if(area == null  ||  area.compareTo(FDCHelper.ZERO) == 0){
			area = room.getBuildingArea();
		}
		sinRoom.setBuildingArea(area); 
		sinRoom.setTenancyArea(room.getTenancyArea());
		//tenRoom.setFlagAtTerm(FlagAtTermEnum.Unknown);
		sinRoom.setTenRoomState(room.getTenancyState());

		//��ó����� TODO �ó��������Ҫ��Room�ϼ��ֶ�,�˴�ֱ�Ӵӷ�����Ϣ��getLongNumber
		StringBuffer sbRoomLongNum = new StringBuffer();
		BuildingInfo building = room.getBuilding();
		SellProjectInfo sellPro = (building == null) ? null : building.getSellProject();

		final String spitStr = "-";
		if(sellPro != null){
			sbRoomLongNum.append(sellPro.getName());
			sbRoomLongNum.append(spitStr);
		}
		if(building != null){
			sbRoomLongNum.append(building.getName());
			sbRoomLongNum.append(spitStr);
		}
		if(room.getUnit() != 0){
			sbRoomLongNum.append(room.getUnit());
			sbRoomLongNum.append(spitStr);
		}
		sbRoomLongNum.append(room.getNumber());

		sinRoom.setRoomLongNum(sbRoomLongNum.toString());    	    	

		return sinRoom;
	}

	protected void btnAddRooms_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddRooms_actionPerformed(e);

		BuildingInfo buildingInfo = (BuildingInfo) this.getUIContext().get("building");
		BuildingUnitInfo buildUnit = (BuildingUnitInfo) this.getUIContext().get("buildUnit");

		RoomCollection rooms = RoomSelectUI.showMultiRoomSelectUI(this, buildingInfo,
				buildUnit, MoneySysTypeEnum.TenancySys, null, (SellProjectInfo)this.getUIContext().get("sellProject"));
		if (rooms == null  ||  rooms.isEmpty()) {
			return;
		}

		SinObligateRoomsEntryCollection tenRooms = new SinObligateRoomsEntryCollection();
		for(int i=0; i<rooms.size(); i++){
			RoomInfo room = rooms.get(i);
			if(room.isIsForTen()==false)
			{
				MsgBox.showInfo(room.getNumber() + " �����޷���!");
				return;
			}
			if(room.getTenancyState() == null  ||  TenancyStateEnum.unTenancy.equals(room.getTenancyState())){
				MsgBox.showInfo(this, "����" + room.getNumber() + "δ���⣬���ܳ��⣡");
				this.abort();
			}
			if (TenancyStateEnum.newTenancy.equals(room.getTenancyState()) ||
					TenancyStateEnum.continueTenancy.equals(room.getTenancyState())
					|| TenancyStateEnum.enlargeTenancy.equals(room.getTenancyState())) {
				MsgBox.showInfo(room.getNumber() + " �Ѿ�����!");
				return;
			}
			if (TenancyStateEnum.keepTenancy.equals(room.getTenancyState())) {
				MsgBox.showInfo(room.getNumber() + " �ѷ��!");
				return;
			}
			if (isExist(room)) {
				MsgBox.showInfo(room.getNumber() + " �Ѿ����б���!");
				continue;
			}
			SinObligateRoomsEntryInfo sinObliRoomInfo = roomToSinRoomEntry(room);
			tenRooms.add(sinObliRoomInfo);
		}

		addRoomRows(tenRooms);
		updateTotalRent();
	}

	private boolean isExist(RoomInfo room) {
		for (int j = 0; j < this.tblRooms.getRowCount(); j++) {
			SinObligateRoomsEntryInfo roomEntry = (SinObligateRoomsEntryInfo) this.tblRooms
			.getRow(j).getUserObject();
			if (roomEntry.getRoom().getId().toString().equals(
					room.getId().toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �������з���,������Դ��1�����ڵ����.���µ��ɽ����,��׼���ͷ��������,������Դ�����
	 * */
	private void updateTotalRent(){
		BigDecimal totalRoomStandardRent = FDCHelper.ZERO;
		BigDecimal totalRoomDealRent = FDCHelper.ZERO;
		BigDecimal totalBuildingArea = FDCHelper.ZERO;


		int termLength = this.spinTermLength.getIntegerVlaue().intValue();
		for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
			IRow row = this.tblRooms.getRow(i);
			SinObligateRoomsEntryInfo sinRoomInfo = (SinObligateRoomsEntryInfo) row.getUserObject();
			RentTypeEnum standardRentType = sinRoomInfo.getStandardRentType();
			BigDecimal standardRent = sinRoomInfo.getStandardRoomRent();
			totalRoomStandardRent = totalRoomStandardRent.add(getRentPerLease(standardRentType, standardRent, termLength));

			RentTypeEnum plightRentType = sinRoomInfo.getPlightRentType();
			BigDecimal plightRent = sinRoomInfo.getPlightRoomRent();
			totalRoomDealRent = totalRoomDealRent.add(getRentPerLease(plightRentType, plightRent, termLength));

			BigDecimal buildingArea = sinRoomInfo.getBuildingArea();
			BigDecimal tenancyArea = sinRoomInfo.getTenancyArea();
			totalBuildingArea = totalBuildingArea.add(tenancyArea == null ? FDCHelper.ZERO : tenancyArea);
		}


		this.txtTotalRoomStandardRent.setValue(totalRoomStandardRent);
		this.txtPlightRoomDealRent.setValue(totalRoomDealRent);
		this.txtTotalBuildingArea.setValue(totalBuildingArea);

	}

	/**
	 * ���ݶ����������Ϊ1�����ڵļ۸�
	 * ע:����Ͱ��ܼ���ʱ��һ���¹̶���30�����
	 * @param rentType
	 * @param rent
	 * @param leaseTime 
	 * 			1�����ڵ�����
	 * @return ������1�����ڵ����.
	 * */
	private BigDecimal getRentPerLease(RentTypeEnum rentType, BigDecimal rent, int termLength) {
		if(rentType == null  ||  rent == null  || rent.compareTo(FDCHelper.ZERO) == 0 ){
			return FDCHelper.ZERO;
		}

		if(RentTypeEnum.RentByDay.equals(rentType)){
			return rent.multiply(int2BigDecimal(30 * termLength));
		}else if(RentTypeEnum.RentByMonth.equals(rentType)){
			return rent.multiply(int2BigDecimal(termLength));
		}else if(RentTypeEnum.RentByQuarter.equals(rentType)){
			return rent.multiply(int2BigDecimal(termLength)).divide(int2BigDecimal(3), 2, BigDecimal.ROUND_HALF_UP);
		}else if(RentTypeEnum.RentByWeek.equals(rentType)){
			return rent.multiply(int2BigDecimal(30 * termLength)).divide(int2BigDecimal(7), 2, BigDecimal.ROUND_HALF_UP);
		}else if(RentTypeEnum.RentByYear.equals(rentType)){
			return rent.multiply(int2BigDecimal(termLength)).divide(int2BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
		}else{
			return FDCHelper.ZERO;
		}
	}

	private BigDecimal int2BigDecimal(int i){
		return new BigDecimal(new Integer(i).toString());
	}

	protected void tblRooms_editStopped(KDTEditEvent e) throws Exception {
		super.tblRooms_editStopped(e);
		//����޸ķ������,����Ҫ��д��𵥼�,���¸�����ϸ
		int colIndex = e.getColIndex();
		String colKey = this.tblRooms.getColumnKey(colIndex);

		if(!"plightRent".equals(colKey) && !"plightRentType".equals(colKey)){
			return;
		}
		//��ֵû�б仯ʱ,�����и��²���
		if(e.getOldValue() != null){
			if(e.getOldValue().equals(e.getValue())){
				return;
			}
		}

		IRow row = this.tblRooms.getRow(e.getRowIndex());
		SinObligateRoomsEntryInfo sinRoom = (SinObligateRoomsEntryInfo) row.getUserObject();

		RentTypeEnum rentType = (RentTypeEnum) row.getCell("plightRentType").getValue();
		BigDecimal rent = (BigDecimal) row.getCell("plightRent").getValue();
		BigDecimal area = row.getCell("tenancyArea").getValue() == null ? FDCHelper.ZERO : new BigDecimal(row.getCell("tenancyArea").getValue().toString());

		sinRoom.setPlightRentType(rentType);
		sinRoom.setPlightRoomRent(rent);
		if(rent == null){
			rent = FDCHelper.ZERO;
		}

		if("plightRent".equals(colKey)){
			BigDecimal rentPrice = null;
			if(area != null  &&  FDCHelper.ZERO.compareTo(area) != 0){
				rentPrice = rent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
			}
			row.getCell("plightRentPrice").setValue(rentPrice);
			sinRoom.setPlightRoomRentPrice(rentPrice);
		}
		updateTotalRent();
	}
	protected void tblCustomer_editStopped(KDTEditEvent e) throws Exception {
		super.tblCustomer_editStopped(e);
		if (e.getColIndex() == 1) {
			IRow row = this.tblCustomer.getRow(e.getRowIndex());
			FDCCustomerInfo customer = (FDCCustomerInfo) row
			.getCell("customer").getValue();
			if (customer != null) {
				row.getCell("phone").setValue(customer.getPhone());
				row.getCell("postalcode").setValue(customer.getPostalcode());
				row.getCell("certificateName").setValue(
						customer.getCertificateName());
				row.getCell("certificateNumber").setValue(
						customer.getCertificateNumber());
				row.getCell("mailAddress").setValue(customer.getMailAddress());
				row.getCell("bookDate").setValue(customer.getCreateTime());
			} else {
				row.getCell("phone").setValue(null);
				row.getCell("postalcode").setValue(null);
				row.getCell("certificateName").setValue(null);
				row.getCell("certificateNumber").setValue(null);
				row.getCell("mailAddress").setValue(null);
				row.getCell("bookDate").setValue(null);
			}
		}
	}

	protected void btnDelRooms_actionPerformed(ActionEvent e) throws Exception {
		super.btnDelRooms_actionPerformed(e);
		removeRow(this.tblRooms);
	}

	protected void btnAddAttach_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddAttach_actionPerformed(e);
	}

	protected void btnDelAttach_actionPerformed(ActionEvent e) throws Exception {
		super.btnDelAttach_actionPerformed(e);
	}

	protected void btnAddCustomer_actionPerformed(ActionEvent e)
	throws Exception {
		super.btnAddCustomer_actionPerformed(e);
		int activeRowIndex = this.tblCustomer.getSelectManager()
		.getActiveRowIndex();
		IRow row = null;
		if (activeRowIndex == -1) {
			row = this.tblCustomer.addRow();
		} else {
			row = this.tblCustomer.addRow(activeRowIndex + 1);
		}
		row.setHeight(20);
		row.setUserObject(new SinCustomerEntrysInfo());
		if (this.tblCustomer.getRowCount() == 1) {
			row.getCell("propertyPercent").setValue(new BigDecimal("100"));
		}
	}

	protected void btnAddNewCustomer_actionPerformed(ActionEvent e)
	throws Exception {
		UIContext uiContext = new UIContext(this);
		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(CustomerEditUI.class.getName(), uiContext, null,
		"ADDNEW");
		uiWindow.show();
		
		CustomerEditUI cusEditUI = (CustomerEditUI) uiWindow.getUIObject();
		FDCCustomerInfo cus = (FDCCustomerInfo) cusEditUI.getUIContext().get(CustomerEditUI.KEY_SUBMITTED_DATA);
		if (cus != null) {
			addCustomerRow(cus);
		}
	}

	private void addCustomerRow(FDCCustomerInfo customer) {
		int activeRowIndex = this.tblCustomer.getSelectManager().getActiveRowIndex();
		IRow row = null;
		if (activeRowIndex == -1) {
			row = this.tblCustomer.addRow();
		} else {
			row = this.tblCustomer.addRow(activeRowIndex + 1);
		}
		row.setHeight(20);
		row.setUserObject(new SinCustomerEntrysInfo());
		if (this.tblCustomer.getRowCount() == 1) {
			row.getCell("propertyPercent").setValue(new BigDecimal("100"));
		}
		row.getCell("customer").setValue(customer);
		row.getCell("phone").setValue(customer.getPhone());
		if(customer.getPostalcode()!=null){
			row.getCell("postalcode").setValue(customer.getPostalcode());
		}
		if(customer.getCertificateName()!=null){
			row.getCell("certificateName").setValue(customer.getCertificateName());
		}
		if(customer.getCertificateNumber()!=null){
			row.getCell("certificateNumber").setValue(customer.getCertificateNumber());
		}
		if(customer.getMailAddress()!=null){
			row.getCell("mailAddress").setValue(customer.getMailAddress());
		}
		row.getCell("bookDate").setValue(customer.getCreateTime());
		if(customer.getDescription()!=null){
			row.getCell("des").setValue(customer.getDescription());
		}
	}
	protected void btnDelCustomer_actionPerformed(ActionEvent e)
	throws Exception {
		super.btnDelCustomer_actionPerformed(e);
		removeRow(this.tblCustomer);
	}

	/** ɾ��table�е�һ��,��ѡ��ʱɾ��ѡ����,����ɾ�����һ�� */
	private void removeRow(KDTable table) {
		int activeRowIndex = table.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = table.getRowCount();
		}
		table.removeRow(activeRowIndex);
	}

	/**
	 * �������ı��༭���cellEditor
	 * @param length
	 * 			�ı���������볤��
	 * @param editable
	 * 			�Ƿ�ɱ༭
	 * */
	private KDTDefaultCellEditor createTxtCellEditor(int length, boolean editable) {
		KDTextField textField = new KDTextField();
		textField.setMaxLength(length);
		textField.setEditable(editable);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		return txtEditor;
	}

	private ICellEditor createFormattedCellEditor(){
		return createFormattedCellEditor(true);
	}

	private ICellEditor createFormattedCellEditor(boolean editable){
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setEditable(editable);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor cellEditor = new KDTDefaultCellEditor(formattedTextField);
		return cellEditor;
	}

	protected void spinTermLength_stateChanged(ChangeEvent e) throws Exception {
		updateTotalRent();
	}

	private void storeSinRoomsEntry()
	{
		SincerObligateInfo sinInfo = this.editData;
		SinObligateRoomsEntryCollection sinRoomsColl = sinInfo.getSinRoomList();
		sinRoomsColl.clear();
		//��Ϊ�ڷ����б��editstopped�¼���,�Ѿ����䶯��ֵ���µ�row.getUserObject��,��������ֻ��Ҫ��userObjectȡ������		
		for (int i = 0; i < this.tblRooms.getRowCount(); i++) {
			IRow row = this.tblRooms.getRow(i);
			SinObligateRoomsEntryInfo sinRoom = (SinObligateRoomsEntryInfo) row.getUserObject();

			sinRoom.setDescription((String) row.getCell("description").getValue());
			sinRoom.setStartDate((Date)this.pkPlightStrartDate.getValue());
			sinRoom.setEndDate((Date)this.pkPlightEndDate.getValue());
			sinRoomsColl.add(sinRoom);
			sinInfo.getSinRoomList().addCollection(sinRoomsColl);
		}
	}

	private void storeSinAttachResources()
	{

	}
	
	private void storeSinReceive()
	{
		SincerObligateInfo sinInfo = this.editData;
		sinInfo.setIsSincerReceive(this.chkIsRecSincer.isSelected());
		SincerPaylistEntrysCollection sinColl = new SincerPaylistEntrysCollection();
		for (int i = 0; i < this.tblSinReceive.getRowCount(); i++) {
			IRow row = tblSinReceive.getRow(i);
			SincerPaylistEntrysInfo sinEntryInfo = new SincerPaylistEntrysInfo();
			SincerPaylistEntrysInfo oldInfo = (SincerPaylistEntrysInfo) row
					.getUserObject();
			if (oldInfo != null) { // ���ֻ���޸ģ�����֮ǰ�ķ�¼id
				sinEntryInfo.setId(oldInfo.getId());
			}
			MoneyDefineInfo moneyInfo = (MoneyDefineInfo) row.getCell(
					"moneyTypeName").getValue();
			BigDecimal appAmount = (BigDecimal) row.getCell("appAmount")
					.getValue();
			if (appAmount == null)
				continue;
			sinEntryInfo.setMoneyDefine(moneyInfo);
			sinEntryInfo.setAppAmount(appAmount);
			sinColl.add(sinEntryInfo);
		}
		sinInfo.getPayListEntrys().clear();
		sinInfo.getPayListEntrys().addCollection(sinColl);
		
	}

	private void storeSinCustomer()
	{
		SincerObligateInfo sinInfo = this.editData;
		SinCustomerEntrysCollection customerInfos = sinInfo.getSinCustomerList();
		customerInfos.clear();
		for (int i = 0; i < this.tblCustomer.getRowCount(); i++) {
			IRow row = this.tblCustomer.getRow(i);
			SinCustomerEntrysInfo customerInfo =  (SinCustomerEntrysInfo) row
			.getUserObject();
			FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell("customer").getValue();
			if (customer != null) {
				customer.setMailAddress((String) row.getCell("mailAddress")
						.getValue());
				customer.setPhone((String) row.getCell("phone").getValue());
				customer.setPostalcode((String) row.getCell("postalcode")
						.getValue());
//				if (row.getCell("certificateName").getValue() != null) {
//					customer.setCertificateName((CertifacateNameEnum) row
//							.getCell("certificateName").getValue());
//				}
				customer.setCertificateNumber((String) row.getCell("certificateNumber").getValue());
			}
			customerInfo.setFdcCustomer(customer);
			customerInfo.setPropertyPercent((BigDecimal) row.getCell("propertyPercent").getValue());
			customerInfo.setDescription((String) row.getCell("description").getValue());
			customerInfos.add(customerInfo);
			sinInfo.getSinCustomerList().addCollection(customerInfos);
		}  	
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		this.btnAddAttach.setEnabled(true);
		this.btnDelAttach.setEnabled(true);
		this.btnAddCustomer.setEnabled(true);
		this.btnAddNewCustomer.setEnabled(true);
		this.btnDelCustomer.setEnabled(true);
		this.btnAddRooms.setEnabled(true);
		this.btnDelRooms.setEnabled(true);
		this.txtTotalRoomStandardRent.setEnabled(false);
		this.txtTotalBuildingArea.setEnabled(false);
		this.txtPlightRoomDealRent.setEnabled(false);
		this.txtPlightAttachRent.setEnabled(false);
		this.txtTotalAttachResStandardRent.setEnabled(false);
		this.txtName.setEnabled(false);
		this.txtNumber.setEnabled(false);
		this.btnAddPayList.setEnabled(true);
		this.btndelPayList.setEnabled(true);
		BizStateEnum state = (BizStateEnum) this.editData.getBizState();
		 if(!BizStateEnum.SAVE_VALUE.equals(state.getValue()) && !BizStateEnum.SUBMIT_VALUE.equals(state.getValue()))
		 {
			 MsgBox.showInfo("��������ύ״̬��Ԥ���������޸�!");
			 return;
		 }
		
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		SincerObligateInfo sincerInfo = this.editData;
		sincerInfo.setBizState(BizStateEnum.SUBMIT);
		super.actionSubmit_actionPerformed(e);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		SincerObligateInfo sincerInfo = this.editData;
		sincerInfo.setBizState(BizStateEnum.SAVE);
		super.actionSave_actionPerformed(e);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("sellProject.*");
		sels.add("sinCustomerList.*");
		sels.add("sinCustomerList.fdcCustomer.*");
		sels.add("sinRoomList.*");
		sels.add("sinRoomList.room.*");
		sels.add("sinRoomList.room.building.*");
		sels.add("sinRoomList.room.building.sellProject.*");
		sels.add("payListEntrys.*");
		sels.add("payListEntrys.moneyDefine.*");
		return sels;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(this.txtNumber.isEditable() && this.txtNumber.isEnabled() && StringUtils.isEmpty(this.txtNumber.getText())){
			MsgBox.showInfo(this, "���ݱ��벻��Ϊ�գ�");
			this.abort();
		}

		if(StringUtils.isEmpty(this.txtName.getText())){
			MsgBox.showInfo(this, "�������Ʋ���Ϊ�գ�");
			this.abort();
		}
		
		if(Integer.parseInt(this.satisfaction.getText())<0||(Integer.parseInt(this.satisfaction.getText())>100)){
			MsgBox.showInfo(this, "�����ȡֵӦ��0-100֮�䣬������¼�룡");
			//this.satisfaction.setValue(Integer.toString(0));
			this.satisfaction.setText("0");
			this.abort();
		}

		//		if(this.f7SellProject.getValue() == null){
		//			MsgBox.showInfo(this, "������Ŀ����Ϊ�գ�");
		//			this.abort();
		//		}

		if(this.tblRooms.getRowCount() == 0  &&  this.tblAttach.getRowCount() == 0){
			MsgBox.showInfo(this, "û�����޵���Դ��");
			this.abort();
		}

//		if(this.tblCustomer.getRowCount() == 0){
//			MsgBox.showInfo(this, "����Ҫ��һ�����޿ͻ���");
//			this.abort();
//		}
		//��ӿͻ�����֤ eric_wang 2010.09.16
		verifyCustomer();
		Date startDate = (Date) this.pkPlightStrartDate.getValue();
		Date endDate = (Date) this.pkPlightEndDate.getValue();
		if(startDate == null  ||  endDate == null){
			MsgBox.showInfo(this, "Լ��������ʼʱ��ͽ���ʱ�䲻��Ϊ�գ�");
			this.abort();
		}

		startDate = FDCDateHelper.getDayBegin(startDate);
		endDate = FDCDateHelper.getDayBegin(endDate);
		if(startDate.after(endDate)){
			MsgBox.showInfo(this, "Լ��������ʼʱ�䲻�ܳ��ڽ���ʱ�䣡");
			this.abort();
		}
	}
	/*
	 * ��� �Կͻ���Ϣ����֤
	 */
	private void verifyCustomer() throws BOSException {
		BigDecimal percent = FDCHelper.ZERO;
		Map customerMap = new HashMap();
		if (this.tblCustomer.getRowCount() == 0) {
			MsgBox.showInfo("û�����޿ͻ�,�����!");
			this.abort();
		}

		for (int i = 0; i < this.tblCustomer.getRowCount(); i++) {
			IRow row = this.tblCustomer.getRow(i);
			if (row.getCell("propertyPercent").getValue() == null) {
				MsgBox.showInfo("��" + (row.getRowIndex() + 1) + "�пͻ�û�����ó������!");
				this.abort();
			}

			FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell("customer").getValue();
			if (customer == null) {
				MsgBox.showInfo("��" + (row.getRowIndex() + 1) + "�пͻ�û��¼��!");
				this.abort();
			}
//			if (customer.getCertificateName() == null || customer.getCertificateNumber() == null) {
//				MsgBox.showInfo("�ͻ�" + customer.getName() + "֤������Ϊ��!!");
//				CustomerCardUI.addTheCustomerAuthority(customer, this);
//				setCustomerRowData(customer, row);
//				this.abort();
//			}
			if (customerMap.containsKey(customer)) {
				MsgBox.showInfo("��" + (row.getRowIndex() + 1) + "�пͻ��ظ�!");
				this.abort();
			} else {
				customerMap.put(customer, customer);
			}
			percent = percent.add((BigDecimal) row.getCell("propertyPercent").getValue());
		}
		if (percent.compareTo(new BigDecimal("100")) != 0) {
			MsgBox.showInfo("�����������100%!");
			this.abort();
		}
	}
	
	
	private void setSincerPayListVisable()
	{
		if(this.chkIsRecSincer.isSelected())
		{
			this.pnlSincerObligate.add(paneSinReceive,"�տ���Ϣ");
		}else
		{
			this.pnlSincerObligate.remove(paneSinReceive);
		}
	}

	protected void chkIsRecSincer_actionPerformed(ActionEvent e)
	throws Exception {
		super.chkIsRecSincer_actionPerformed(e);
		setSincerPayListVisable();
	}
	
	protected void btnAddPayList_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnAddPayList_actionPerformed(e);
		IRow row = this.tblSinReceive.addRow();
		this.tblSinReceive.checkParsed();
		showTblSinRecEntryRow(row);
	}
	
	protected void showTblSinRecEntryRow(IRow row) {
		SincerPaylistEntrysInfo info = new SincerPaylistEntrysInfo();
		row.getCell("actAmount").getStyleAttributes().setLocked(true);
		row.setUserObject(info);
	}
	
	protected void btndelPayList_actionPerformed(ActionEvent e)
			throws Exception {
		super.btndelPayList_actionPerformed(e);
		int activeRowIndex = this.tblSinReceive.getSelectManager()
		 .getActiveRowIndex();
		IRow row = this.tblSinReceive.getRow(activeRowIndex);
		if (row == null) {
			return;
		}
		SincerPaylistEntrysInfo entry = (SincerPaylistEntrysInfo) row.getUserObject();
		BigDecimal actAmount = entry.getActRevAmount();
		if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) != 0) {
			MsgBox.showInfo("�÷�¼�Ѿ�����,����ɾ��!");
			return;
		}
		 this.tblSinReceive.removeRow(activeRowIndex);
	}

	protected IObjectValue createNewData() {
		SincerObligateInfo sincerInfo = new SincerObligateInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
		.get("sellProject");
		if(sellProject != null)
		{
			sincerInfo.setSellProject(sellProject);
		}
		try {
			sincerInfo.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
		} catch (BOSException e) {
			logger.error(e.getMessage());
			this.handleException(e);
		}
		sincerInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		sincerInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		sincerInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		sincerInfo.setBizDate(new Date());
		return sincerInfo;
	}

}