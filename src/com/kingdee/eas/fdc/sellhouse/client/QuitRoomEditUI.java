/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CasSetting;
import com.kingdee.eas.fdc.sellhouse.FeeFromTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomCollection;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomReasonInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class QuitRoomEditUI extends AbstractQuitRoomEditUI {

	private CoreUIObject plUI = null;
	private String purchaseIdStr = null;
	private RoomDisplaySetting setting = new RoomDisplaySetting();

	protected void setAuditButtonStatus(String oprtType) {
	}

	/**
	 * output class constructor
	 */
	public QuitRoomEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.txtRemark.setCustomForegroundColor(Color.red);
		
		this.comQuitRoomState.setEnabled(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.actionPrint.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setVisible(false);

		this.txtTolActAmount.setEnabled(false);
		this.txtCanRefundment.setEnabled(false);
		this.txtFeeAmount.setEnabled(false);

		this.f7Room.setRequired(true);
		this.txtQuitReason.setRequired(true);
		this.f7FeeMoneyDefine.setRequired(true);
		this.f7FeeAccount.setRequired(true);

		// ����ԭ�������������ÿ��˽���ʵ��
		this.btnCanRefundment.setVisible(false);

		SHEHelper.setTextFormat(txtFeeAmount);

		this.txtNumber.setRequired(true);
		this.pkQuitDate.setRequired(true);
		this.comQuitRoomState.setRequired(true);
		this.f7QuitRoomReason.setRequired(true);
		
		initTblSet();
		f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE, null, null,
				MoneySysTypeEnum.SalehouseSys, null));

		if ("VIEW".equals(this.getOprtState())
				|| "FINDVIEW".equals(this.getOprtState())) {
			this.chkIsAdjust.setEnabled(false);
			this.btnCanRefundment.setText("�鿴");
		} else {
			this.btnCanRefundment.setText("����");
		}

		setMoneyDefineAndAcount();

		// �޸ĵ�����Ҫ���Ʋ��ܸ������䣬�����޷�����ס���������
		if (this.getOprtState().equals(OprtState.EDIT)) {
			this.f7Room.setEnabled(false);
		}
		if(this.getUIContext().get("sellProject") != null){
			RoomInfo room = (RoomInfo) this.f7Room.getValue();
			if(room != null)
			{
				if("ADDNEW".equals(this.getOprtState())){
					//ͨ��room�ҵ�����������Ŀ���Ӷ����в�������
					SellProjectInfo sellProject = (SellProjectInfo)this.getUIContext().get("sellProject");
					Map funcSetMap = setting.getFunctionSetMap();
					FunctionSetting funcSet = (FunctionSetting)funcSetMap.get(sellProject.getId()==null?null:sellProject.getId().toString());
					if(funcSet != null){
						if(funcSet.getIsAdjustPrices().booleanValue()){
							this.chkIsAdjust.setSelected(true);
							this.chkIsAdjust.setEnabled(false);
							chkIsAdjust_actionPerformed(null);
							this.txtNewBuildingPrice.setValue(null);
//							if (this.chkIsSellByRoom.isSelected()) {
//								this.contNewBuildingPrice.setEnabled(false);
//								this.contNewRoomPrice.setEnabled(true);
//							} else {
//								this.contNewBuildingPrice.setEnabled(true);
//								this.contNewRoomPrice.setEnabled(false);
//							}
//							this.txtRemark.setVisible(true);
						}else{
							this.txtRemark.setVisible(false);
						}
					}
				}
				
			}
		}
		
		this.txtNewBuildingPrice.setSupportedEmpty(true);
	}

	/** ���÷��ÿ���ͻ�ƿ�Ŀ */
	private void setMoneyDefineAndAcount() {
		CompanyOrgUnitInfo curCompany = SysContext.getSysContext()
				.getCurrentFIUnit();
		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox opseelect = new AccountPromptBox(this, curCompany,
				view.getFilter(), false, true);
		this.f7FeeAccount.setEntityViewInfo(view);
		this.f7FeeAccount.setSelector(opseelect);
		this.f7FeeAccount
				.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");

		// ���÷��ÿ���Ĺ��� ֻ�ܴ� ֻ�ܴ�������,�������������ѡ��
		view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("sysType",
						MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType",
						MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("moneyType",
								MoneyTypeEnum.ELSEAMOUNT_VALUE));
		filter.setMaskString("#0 and (#1 or #2)");
		view.setFilter(filter);
		this.f7FeeMoneyDefine.setEntityViewInfo(view);

		RoomDisplaySetting setting = new RoomDisplaySetting();
		CasSetting casSet = setting.getCasSetting();
		if(casSet!=null) {
			if (casSet.getQuitMoneyType() != null) {
				if (this.getOprtState().equals(OprtState.ADDNEW))
					this.f7FeeMoneyDefine.setValue(casSet.getQuitMoneyType());
			}
			// �Ƿ������˷����ÿ�����޸�
			if (!casSet.getIsQuitUpdate().booleanValue()) {
				this.f7FeeMoneyDefine.setEnabled(false);
			}
		}

	}

	private EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo) {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// -----------------ת6.0���޸�,��Ŀ����CU����,���ݲ�����֯���и���
		filter.getFilterItems().add(
				new FilterItemInfo("companyID.id", companyInfo.getId()
						.toString()));
		if (companyInfo.getAccountTable() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("accountTableID.id", companyInfo
							.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(
				new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}

	private void initTblSet() {
		this.tblSet.checkParsed();
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setMinimumValue(FDCHelper.MIN_DECIMAL);
		formattedTextField.setMaximumValue(new BigDecimal(Integer.MAX_VALUE));
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);

		this.tblSet.getColumn("canRefundmentAmount").setEditor(numberEditor);

		this.tblSet.getColumn("appAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblSet.getColumn("appAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblSet.getColumn("actAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblSet.getColumn("actAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblSet.getColumn("canRefundmentAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblSet.getColumn("canRefundmentAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblSet.getColumn("hasRemitAmount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblSet.getColumn("hasRemitAmount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.tblSet.getColumn("moneyDefine").getStyleAttributes().setLocked(
				true);
		this.tblSet.getColumn("appAmount").getStyleAttributes().setLocked(true);
		this.tblSet.getColumn("actAmount").getStyleAttributes().setLocked(true);
		this.tblSet.getColumn("hasRemitAmount").getStyleAttributes().setLocked(true);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() == null) {
			MsgBox.showInfo("�˷����벻��Ϊ��!");
			this.abort();
		}
		String quitRoomID = this.editData.getId().toString();
		QuitRoomDataProvider data = new QuitRoomDataProvider(quitRoomID,
				new MetaDataPK(
						"com.kingdee.eas.fdc.sellhouse.app.QuitRoomPrintQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/quitRoom", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPrint_actionPerformed(e);
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.editData.getId() == null) {
			MsgBox.showInfo("�˷����벻��Ϊ��!");
			this.abort();
		}
		String quitRoomID = this.editData.getId().toString();
		HashMap dataMap = this.getDataMap(quitRoomID);
		QuitRoomDataProvider data = new QuitRoomDataProvider(quitRoomID,
				new MetaDataPK(
						"com.kingdee.eas.fdc.sellhouse.app.QuitRoomPrintQuery"),dataMap);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview("/bim/fdc/sellhouse/quitRoom", data,
				javax.swing.SwingUtilities.getWindowAncestor(this));
		super.actionPrintPreview_actionPerformed(e);
	}

	private HashMap getDataMap(String quitRoomId) throws EASBizException, BOSException{
		HashMap dataMap = new HashMap();
		SelectorItemCollection select = new SelectorItemCollection();
		select.add("quitEntrys.*");
		QuitRoomInfo quitroom = QuitRoomFactory.getRemoteInstance().getQuitRoomInfo(new ObjectUuidPK(quitRoomId));
		QuitPayListEntryCollection quitPayListEntrys = quitroom.getQuitEntrys();
		BigDecimal quitAmount = FDCHelper.ZERO;
		if(quitPayListEntrys!=null){
			for(int i=0; i<quitPayListEntrys.size(); i++){
				QuitPayListEntryInfo quitPayList = quitPayListEntrys.get(i);
				quitAmount = quitAmount.add(quitPayList.getCanRefundmentAmount());
			}
			dataMap.put("quitAmount", quitAmount);
		}
		return dataMap;
	}
	
	private void vertifyBeforeSubmit() throws Exception {
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable()
				&& StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("�������¼�룡");
			this.abort();
		}
		if (this.pkQuitDate.getValue() == null) {
			MsgBox.showInfo("�˷����ڱ���¼�룡");
			this.abort();
		}
		if (StringUtils.isEmpty(this.txtQuitReason.getText())) {
			MsgBox.showInfo("�˷�ԭ����ϸ����¼�룡");
			this.abort();
		}
		if (this.f7FeeMoneyDefine.getValue() == null) {
			MsgBox.showInfo("�˷��������¼�룡");
			this.abort();
		}
		if (this.f7FeeAccount.getValue() == null) {
			MsgBox.showInfo("���ÿ�Ŀ����¼�룡");
			this.abort();
		}

		if (this.f7Room.getValue() == null) {
			MsgBox.showInfo("�˷��������¼�룡");
			this.abort();
		}
		
		if(this.comQuitRoomState.getSelectedIndex()==-1){
			MsgBox.showInfo("�˷�״̬����¼�룡");
			this.abort();
		}
		
		if(this.f7QuitRoomReason.getValue() == null){
			MsgBox.showInfo("�˷�ԭ�����¼�룡");
			this.abort();
		}
		
		if (this.chkIsAdjust.isSelected()) {
			if (this.chkIsSellByRoom.isSelected()) {
				BigDecimal newRoomPrice = this.txtNewRoomPrice
						.getBigDecimalValue();
				if (newRoomPrice == null
						|| newRoomPrice.compareTo(FDCHelper.ZERO) == 0) {
					MsgBox.showInfo("��¼��������ڵ��ۣ�");
					this.abort();
				}
			} else {
				BigDecimal newBuildingPrice = this.txtNewBuildingPrice
						.getBigDecimalValue();
				if (newBuildingPrice == null
						|| newBuildingPrice.compareTo(FDCHelper.ZERO) == 0) {
					MsgBox.showInfo("��¼����������ۣ�");
					this.abort();
				}
			}
		}
		verifyBalance();//�����ѽ�����ڼ䣬����������˷����޸�
		verifyQuitPayList();
	}
	
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		vertifyBeforeSubmit();
		QuitRoomInfo quit = (QuitRoomInfo) this.editData;
		if (quit.getId() == null) {
			RoomInfo room = quit.getRoom();
			if (room == null) {
				MsgBox.showInfo("��ѡ��Ҫ�˵ķ��䣡");
				return;
			}
			existUnAuditQuitInfo(room.getId().toString());
		}
		super.actionSave_actionPerformed(e);
	}
	
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		vertifyBeforeSubmit();
		// �ֳ�����һ�����������2��δ�������˷���������������ظ��ύ����У��
		QuitRoomInfo quit = (QuitRoomInfo) this.editData;
		if (quit.getId() == null) {
			RoomInfo room = quit.getRoom();
			if (room == null) {
				MsgBox.showInfo("��ѡ��Ҫ�˵ķ��䣡");
				return;
			}

			/*if (existUnAuditQuitInfo(room.getId().toString())) {
				MsgBox.showInfo("�÷����Ѿ��˹�������Ҫ���ظ��ύ�˷���!");
				return;
			}*/
			existUnAuditQuitInfo(room.getId().toString());
		}
		if (this.getOprtState().equals(OprtState.ADDNEW)
				&& someThingHasChanged(quit.getPurchase().getId().toString())) {
			MsgBox.showWarning("�������Ϲ���������ϸ�ѷ��������ݸ��£������´��˷����棡");
			return;
		}

		String oldOprt = this.oprtState;
		// this.setOprtState("EDIT");
		super.actionSubmit_actionPerformed(e);

		// �̻�����ҵ�񵥾ݰ���,�˷��� �����ύ�������̻�����
		if (oldOprt.equals(OprtState.ADDNEW)) {
			if (quit.getPurchase() != null) {
				PurchaseInfo purInfo = PurchaseFactory.getRemoteInstance()
						.getPurchaseInfo(
								"select salesman,customerInfo.customer where id ='"
										+ quit.getPurchase().getId().toString()
										+ "'");
				List list = new ArrayList();
				for (int i = 0; i < purInfo.getCustomerInfo().size(); i++) {
					PurchaseCustomerInfoInfo purCust = purInfo
							.getCustomerInfo().get(i);
					if (purCust.getCustomer() != null)
						list.add(purCust.getCustomer());
				}
				CommerceHelper.addCommerceTrackRecord(this, list, purInfo
						.getSalesman(), TrackRecordEnum.CancelApp,
						this.editData.getNumber(),
						this.editData.getId() == null ? null : this.editData
								.getId().toString());
			}
		}
		this.setOprtState(STATUS_VIEW);
	}

	boolean loadData = false;

	public void loadFields() {
		loadData = true;
		super.loadFields();
		QuitRoomInfo quit = (QuitRoomInfo) this.editData;
		
		this.txtNumber.setText(quit.getNumber());
		this.pkQuitDate.setValue(quit.getQuitDate());
		this.txtQuitReason.setText(quit.getQuitReason());
		this.chkIsAdjust.setSelected(quit.isIsAdjustPrice());
		this.txtNewBuildingPrice.setValue(quit.getNewBuildingPrice());
		this.txtNewRoomPrice.setValue(quit.getNewRoomPrice());
		if (quit.isIsAdjustPrice()) {
			this.txtNewBuildingPrice.setEnabled(true);
			this.txtNewRoomPrice.setEnabled(true);
		} else {
			this.txtNewBuildingPrice.setEnabled(false);
			this.txtNewRoomPrice.setEnabled(false);
		}

		this.f7FeeMoneyDefine.setValue(quit.getFeeMoneyDefine());
		this.f7FeeAccount.setValue(quit.getFeeAccount());
		this.txtFeeAmount.setValue(quit.getFeeAmount());
		this.f7Room.setValue(quit.getRoom());
		this.f7QuitRoomReason.setValue(quit.getQuitRoomReason());
		this.comQuitRoomState.setSelectedItem(quit.getQuitRoomState());
		publicLoad(quit);
	

		loadData = false;
	}
	/**
	 * @�޸��� tim_gao
	 * @�޸�ʱ�� 2010-9-8
	 * @���� ��520�У���ԭ����������ԭ���ڵ�����Ϊ�Ϲ������Ϲ�ʱ���ۿ���
	 */
	public void publicLoad(QuitRoomInfo quit)  {
		loadQuitPayList(quit.getQuitEntrys());
		
		/**
		 * ֻ����Ԥ�����Ϲ���ǩԼ�ķ���������˷�������
		 * Purchase,PrePurchase,Sign
		 */
		final RoomInfo quitRoom = quit.getRoom();
		
		if(quitRoom==null){
			return;
		}
		
		if(!STATUS_VIEW.equals(getOprtState())){
			if(quitRoom.getSellState().equals(RoomSellStateEnum.Init)
					||quitRoom.getSellState().equals(RoomSellStateEnum.OnShow)
					||quitRoom.getSellState().equals(RoomSellStateEnum.KeepDown)
					||quitRoom.getSellState().equals(RoomSellStateEnum.SincerPurchase)){
						FDCMsgBox.showWarning(this,"ֻ����Ԥ�����Ϲ���ǩԼ�ķ���������˷�������");
						this.f7Room.setValue(null);
						SysUtil.abort();
					}
		}
					if (quitRoom != null) {
						//ԭ����������ԭ���ڵ�����Ϊ�Ϲ������Ϲ�ʱ���ۿ���
						//by tim_gao
//						PurchaseInfo  purcInfo = quit.getPurchase();
						BOSUuid idStr = quit.getPurchase().getId();
						PurchaseInfo purcInfo = null;
						try {
							purcInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo("select snapBuildPrice,snapRoomPrice where id='"+idStr+"'");
						} catch (EASBizException e) {
							e.printStackTrace();
						} catch (BOSException e) {
							e.printStackTrace();
						}
						this.txtOldBuildingPrice.setValue(purcInfo.getSnapBuildPrice());
						this.txtOldRoomPrice.setValue(purcInfo.getSnapRoomPrice());
						this.chkIsSellByRoom.setSelected(quitRoom.isIsCalByRoomArea());
						if (quitRoom.isIsCalByRoomArea()) {
							this.contNewBuildingPrice.setEnabled(false);
							this.txtNewRoomPrice
									.addDataChangeListener(new DataChangeListener() {
										public void dataChanged(DataChangeEvent eventObj) {
											updateBuildingPriceByRoomPrice(quitRoom,
													eventObj);
										}
									});
						} else {
							this.contNewRoomPrice.setEnabled(false);
							this.txtNewBuildingPrice
									.addDataChangeListener(new DataChangeListener() {
										public void dataChanged(DataChangeEvent eventObj) {
											updateRoomPriceByBuildingPrice(quitRoom,
													eventObj);
										}
									});
						}
					}

					KDScrollPane scrollPane = new KDScrollPane();
					this.kDTabbedPane1.removeAll();
					if (quit.getPurchase() != null) {
						this.kDTabbedPane1.add(scrollPane, "ԭ�Ϲ���");
						UIContext uiContext = new UIContext(ui);
						uiContext.put(UIContext.ID, quit.getPurchase().getId().toString());
						try {
							//modify for wangpeng
							uiContext.put("quitRoom", "quitRoom");
							plUI = (CoreUIObject) UIFactoryHelper
									.initUIObject(PurchaseEditUI.class.getName(),
											uiContext, null, "VIEW");
						} catch (Exception e) {
							handleException(e);
						}
					}

					loadCanRefundmentAmount(quit.getQuitEntrys());

					scrollPane.setViewportView(plUI);
					scrollPane.setKeyBoardControl(true);
					scrollPane.setEnabled(false);
//		}
		
	}
	
	 protected IObjectValue getValue(IObjectPK pk) throws Exception {
	    	QuitRoomInfo roomInfo = (QuitRoomInfo) super.getValue(pk);
	    	QuitPayListEntryCollection quitEntrys = roomInfo.getQuitEntrys();
	    	CRMHelper.sortCollection(quitEntrys, "seq", true);
	    	return roomInfo;
	    }

	/** �����˷����ķ�¼�б� */
	private void loadQuitPayList(QuitPayListEntryCollection quitEntrys) {
		this.tblSet.checkParsed();
		this.tblSet.removeRows(false);
		for (int i = 0; i < quitEntrys.size(); i++) {
			QuitPayListEntryInfo quitEntry = quitEntrys.get(i);
			IRow row = this.tblSet.addRow();
			row.setUserObject(quitEntry);
			row.getCell("moneyDefine").setValue(
					quitEntry.getMoneyDefine().getName());
			row.getCell("appAmount").setValue(quitEntry.getApAmount());
			row.getCell("actAmount").setValue(quitEntry.getActPayAmount());
			row.getCell("canRefundmentAmount").setValue(
					quitEntry.getCanRefundmentAmount());
			row.getCell("hasRemitAmount").setValue(quitEntry.getHasRemitAmount());			
		}
	}

	protected void tblSet_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		// int colIndex = e.getColIndex();

		IRow row = this.tblSet.getRow(rowIndex);
		if (row == null) {
			return;
		}

		reflushTotalCanRefundmentAmount();
	}

	private void reflushTotalCanRefundmentAmount() {
		BigDecimal totalValue = FDCHelper.ZERO;
		BigDecimal allRemain = FDCHelper.ZERO;
		for (int i = 0; i < this.tblSet.getRowCount(); i++) {
			IRow row = this.tblSet.getRow(i);
			QuitPayListEntryInfo quitInfo = (QuitPayListEntryInfo)row.getUserObject();
			allRemain = allRemain.add(quitInfo.getCanRefundmentAmount());
			
			BigDecimal value = (BigDecimal) row.getCell("canRefundmentAmount")
					.getValue();
			if (value != null) {
				totalValue = totalValue.add(value);
			}
		}
		this.txtCanRefundment.setValue(totalValue);
		BigDecimal totalActAmount = this.txtTolActAmount.getBigDecimalValue();
		if (totalActAmount == null) {
			totalActAmount = FDCHelper.ZERO;
		}
		this.txtFeeAmount.setValue(allRemain.subtract(totalValue));	//���� = ��ʣ���� - ���˽��
	}

	protected void f7FeeMoneyDefine_dataChanged(DataChangeEvent e)
			throws Exception {
		// �˴�Ҫ�ӿ����Ŀ���ձ���ȡ��Ӧ�Ŀ�Ŀ
		MoneyDefineInfo moneyDefine = (MoneyDefineInfo) this.f7FeeMoneyDefine
				.getValue();
		if (e.getNewValue() == null) {
			this.f7FeeAccount.setValue(null);
		}
		if (e.getNewValue() != null
				&& (e.getOldValue() == null || !e.getOldValue().equals(
						e.getNewValue()))) {
			MoneySubjectContrastInfo contractTableInfo = CommerceHelper
					.getContractTableByMoneyDefine(moneyDefine);
			if (contractTableInfo != null) {
				if (contractTableInfo.getAccountView() != null)
					this.f7FeeAccount.setValue(contractTableInfo
							.getAccountView());
				if (!contractTableInfo.isIsChanged())
					this.f7FeeAccount.setEnabled(false);
				else
					this.f7FeeAccount.setEnabled(true);
			}
		}
	}

	protected void f7Room_dataChanged(DataChangeEvent e) throws Exception {
 		if (loadData) {
			return;
		}
		if(e.getOldValue()!=null && e.getNewValue()!=null){
			RoomInfo roomNew = (RoomInfo)e.getNewValue();
			RoomInfo roomOld = (RoomInfo)e.getOldValue();
			if(roomNew.getId().toString().equals(roomOld.getId().toString())){
				return;
			}
		}
		RoomInfo room = (RoomInfo) this.f7Room.getValue();
		setQuitRoomInfoByRoomId((QuitRoomInfo) this.editData,
				room == null ? null : room.getId().toString());

		publicLoad((QuitRoomInfo) this.editData);
		
		if(room != null)
		{
			this.txtRoomNo.setText(room.getRoomNo());
			if("ADDNEW".equals(this.getOprtState())){
				//ͨ��room�ҵ�����������Ŀ���Ӷ����в�������
				SellProjectInfo sellProject = room.getBuilding().getSellProject();
				Map funcSetMap = setting.getFunctionSetMap();
				FunctionSetting funcSet = (FunctionSetting)funcSetMap.get(sellProject.getId()==null?null:sellProject.getId().toString());
				if(funcSet != null){
					if(funcSet.getIsAdjustPrices().booleanValue()){
						this.chkIsAdjust.setSelected(true);
						this.chkIsAdjust.setEnabled(false);
						chkIsAdjust_actionPerformed(null);
//						if (this.chkIsSellByRoom.isSelected()) {
//							this.contNewBuildingPrice.setEnabled(false);
//							this.contNewRoomPrice.setEnabled(true);
//						} else {
//							this.contNewBuildingPrice.setEnabled(true);
//							this.contNewRoomPrice.setEnabled(false);
//						}
//						this.txtRemark.setVisible(true);
					}else{
						this.txtRemark.setVisible(false);
					}
				}
			}
			
		}
		
		if(room!=null && room.getSellState().equals(RoomSellStateEnum.Init)) {
			MsgBox.showInfo("����û�п���!");
			this.comQuitRoomState.setSelectedIndex(-1);
			this.abort();
		}
		if(room!=null && room.getSellState().equals(RoomSellStateEnum.OnShow)) {
			MsgBox.showInfo("����û������!");
			this.comQuitRoomState.setSelectedIndex(-1);
			this.abort();
		}
		
		chkIsAdjust_actionPerformed(null);
		
	}

	private void loadCanRefundmentAmount(QuitPayListEntryCollection quitEntrys) {
		this.txtCanRefundment.setUserObject(quitEntrys);
		StringBuffer sb = new StringBuffer();
		BigDecimal tolActPay = FDCHelper.ZERO;
		BigDecimal tolCanRefundment = FDCHelper.ZERO;
		BigDecimal maxCanRefund = FDCHelper.ZERO;
		for (int i = 0; i < quitEntrys.size(); i++) {
			QuitPayListEntryInfo quitEntry = quitEntrys.get(i);

			BigDecimal actPay = quitEntry.getActPayAmount();
			BigDecimal hasRemitAmount = quitEntry.getHasRemitAmount();
			if(actPay == null) actPay = FDCHelper.ZERO;
			if(hasRemitAmount == null) hasRemitAmount = FDCHelper.ZERO;
			
			tolActPay = tolActPay.add(actPay).subtract(hasRemitAmount);
			BigDecimal canRefundmentAmount = quitEntry.getCanRefundmentAmount();
			MoneyDefineInfo moneyDefine = quitEntry.getMoneyDefine();
			if (i != 0)
				sb.append(";");
			sb.append(moneyDefine.getName());
			sb.append("����");
			if (canRefundmentAmount == null
					|| canRefundmentAmount.compareTo(FDCHelper.ZERO) == 0) {
				canRefundmentAmount = FDCHelper.ZERO;
			} else {
				canRefundmentAmount = canRefundmentAmount.setScale(2,
						BigDecimal.ROUND_HALF_UP);
			}

			tolCanRefundment = tolCanRefundment.add(canRefundmentAmount);
			maxCanRefund = maxCanRefund.add(quitEntry.getMaxCanRefundAmount());
			sb.append(canRefundmentAmount);
		}
		this.txtTolActAmount.setValue(tolActPay);
		// this.txtCanRefundment.
		this.txtCanRefundment.setValue(tolCanRefundment);

		this.txtFeeAmount.setValue(maxCanRefund.subtract(tolCanRefundment));
	}

	/**
	 * @deprecated
	 * */
	protected void btnCanRefundment_actionPerformed(ActionEvent e)
			throws Exception {
		QuitPayListEntryCollection quitEntrys = (QuitPayListEntryCollection) this.txtCanRefundment
				.getUserObject();
		UIContext uiContext = new UIContext(this);
		uiContext.put("quitEntrys", quitEntrys);

		String op = "EDIT";
		if ("VIEW".equals(this.getOprtState())
				|| "FINDVIEW".equals(this.getOprtState())) {
			op = "VIEW";
		}

		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(CanRefundmentAmountSetUI.class.getName(), uiContext,
						null, op);
		uiWindow.show();
	}

	/**
	 * ���ݽ����۸��������ڼ۸�
	 * */
	private void updateRoomPriceByBuildingPrice(RoomInfo quitRoom,
			DataChangeEvent eventObj) {
		BigDecimal newRoomPrice = null;
		Object obj = eventObj.getNewValue();
		if (obj != null) {
			BigDecimal buildingPrice = (BigDecimal) obj;
			BigDecimal roomArea = quitRoom.getRoomArea();
			if (roomArea != null && roomArea.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal buildingArea = FDCHelper.ZERO;
				if (quitRoom.getBuildingArea() != null) {
					buildingArea = quitRoom.getBuildingArea();
				}
				newRoomPrice = buildingPrice.multiply(buildingArea).divide(
						roomArea, 2, BigDecimal.ROUND_HALF_UP);
			}
		}
		this.txtNewRoomPrice.setValue(newRoomPrice);
	}

	/**
	 * �������ڼ۸����������۸�
	 * */
	private void updateBuildingPriceByRoomPrice(RoomInfo quitRoom,
			DataChangeEvent eventObj) {		
		BigDecimal newBuildingPrice = null;
		Object obj = eventObj.getNewValue();
		if (obj != null) {
			BigDecimal roomPrice = (BigDecimal) obj;
			BigDecimal buildingArea = quitRoom.getBuildingArea();
			if (buildingArea != null
					&& buildingArea.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal roomArea = FDCHelper.ZERO;
				if (quitRoom.getRoomArea() != null) {
					roomArea = quitRoom.getRoomArea();
				}
				newBuildingPrice = roomPrice.multiply(roomArea).divide(
						buildingArea, 2, BigDecimal.ROUND_HALF_UP);
			}
		}
		this.txtNewBuildingPrice.setValue(newBuildingPrice);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		QuitRoomInfo quit = (QuitRoomInfo) this.editData;
		quit.setQuitDate((Date) this.pkQuitDate.getValue());
		quit.setNumber(this.txtNumber.getText());
		quit.setNewBuildingPrice(this.txtNewBuildingPrice.getBigDecimalValue());
		quit.setNewRoomPrice(this.txtNewRoomPrice.getBigDecimalValue());
		quit.setIsAdjustPrice(this.chkIsAdjust.isSelected());

		quit.setFeeMoneyDefine((MoneyDefineInfo) this.f7FeeMoneyDefine
				.getValue());
		quit.setFeeAccount((AccountViewInfo) this.f7FeeAccount.getValue());
		
		quit.setQuitRoomReason((QuitRoomReasonInfo)this.f7QuitRoomReason.getValue());
		quit.setQuitReason(this.txtQuitReason.getText());
		quit.setQuitRoomState((QuitRoomStateEnum)this.comQuitRoomState.getSelectedItem());
		
		if (this.txtFeeAmount.getBigDecimalValue() == null
				|| (this.txtFeeAmount.getBigDecimalValue() != null && this.txtFeeAmount
						.getBigDecimalValue().compareTo(FDCHelper.ZERO) == 0)) {
			quit.setFeeAmount(FDCHelper.ZERO);
		} else {
			quit.setFeeAmount(this.txtFeeAmount.getBigDecimalValue());
		}

		for (int i = 0; i < this.tblSet.getRowCount(); i++) {
			IRow row = this.tblSet.getRow(i);
			QuitPayListEntryInfo quitPay = (QuitPayListEntryInfo) row
					.getUserObject();
			quitPay.setCanRefundmentAmount((BigDecimal) row.getCell(
					"canRefundmentAmount").getValue());
		}
	}

	protected IObjectValue createNewData() {
		QuitRoomInfo quit = new QuitRoomInfo();
		quit.setQuitDate(new Date());
		String roomId = (String) this.getUIContext().get("roomId");
		try {
			setQuitRoomInfoByRoomId(quit, roomId);
		} catch (Exception e) {
			this.handleException(e);
			this.abort(e);
		}
		quit.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		quit.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit()
				.castToFullOrgUnitInfo());
		quit.setBookedDate(new Date());
		quit.setFeeAmount(FDCHelper.ZERO);
		/**
		 * �˷������Ƿ�����ʶ��fisblance�ֶΣ�������Ϊnullֵ����Ҫ������Ŀǰϵͳ������NULL���ڣ���������ʷ��������
		 * ���ݴ��������޸Ĵ�bug
		 * update by renliang at2011-1-11
		 */
		quit.setIsBlance(0);

		return quit;
	}

	private void setQuitRoomInfoByRoomId(QuitRoomInfo quit, String roomId)
			throws EASBizException, BOSException, UuidException {
		if (roomId == null) {
			quit.setPurchase(null);
			quit.setRoom(null);
			quit.setNewBuildingPrice(null);
			quit.setNewRoomPrice(null);
			quit.setIsAdjustPrice(false);

			quit.getQuitEntrys().clear();
			return;
		}

		RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(
				new ObjectUuidPK(BOSUuid.read(roomId)));

		verifyAddNewRoom(room);
		PurchaseInfo purchase = room.getLastPurchase();

		quit.getQuitEntrys().clear();
		// ����Ĭ�Ͽ��˽��Ϊ   ʵ�ս��
		if (purchase != null && purchase.getId() != null) {
			QuitPayListEntryCollection quitEntrys = getDefaultCanRefundmentAmount(purchase
					.getId().toString());
			quit.getQuitEntrys().addCollection(quitEntrys);
		}

		quit.setPurchase(purchase);
		quit.setRoom(room);
		quit.setNewBuildingPrice(room.getBuildPrice());
		quit.setNewRoomPrice(room.getRoomPrice());
		quit.setIsAdjustPrice(false);
		
		
		if(room.getSellState().equals(RoomSellStateEnum.PrePurchase)){
			quit.setQuitRoomState(QuitRoomStateEnum.PreconcertQuitRoom);
			this.comQuitRoomState.setSelectedItem(QuitRoomStateEnum.PreconcertQuitRoom);
		}else if(room.getSellState().equals(RoomSellStateEnum.Purchase)){
			quit.setQuitRoomState(QuitRoomStateEnum.TakeUpQuitRoom);
			this.comQuitRoomState.setSelectedItem(QuitRoomStateEnum.TakeUpQuitRoom);
		}else if(room.getSellState().equals(RoomSellStateEnum.Sign)){
			quit.setQuitRoomState(QuitRoomStateEnum.SignUpQuitRoom);
			this.comQuitRoomState.setSelectedItem(QuitRoomStateEnum.SignUpQuitRoom);
		}		
	}

	/** ���˷���¼�б���������� ��Դ��A(�Ϲ���δ����)���˵�Ԥ���� B(�Ϲ���������) 1.�տӦ����ϸ 2.�տ����Ӧ�� */
	private QuitPayListEntryCollection getDefaultCanRefundmentAmount(
			String purchaseId) throws BOSException, EASBizException {
		timeMap = new HashMap();
		QuitPayListEntryCollection quitPayList = new QuitPayListEntryCollection();
		SelectorItemCollection sels = new SelectorItemCollection();
		// sels.add("*");
		// sels.add("payListEntry.*");
		sels.add("isEarnestInHouseAmount");
		sels.add("auditTime");
		sels.add("payListEntry.*"); // Ӧ�ղ���
		sels.add("payListEntry.moneyDefine.moneyType");
		sels.add("payListEntry.moneyDefine.name");
		sels.add("elsePayListEntry.*"); // ��������
		sels.add("elsePayListEntry.moneyDefine");
		sels.add("elsePayListEntry.moneyDefine.name");

		PurchaseInfo purchase = PurchaseFactory.getRemoteInstance()
				.getPurchaseInfo(new ObjectUuidPK(purchaseId), sels);
		
		PurchasePayListEntryCollection purPayList = purchase.getPayListEntry(); // Ӧ�ղ���
		CRMHelper.sortCollection(purPayList, "seq",true);
		for (int i = 0; i < purPayList.size(); i++) {
			PurchasePayListEntryInfo purPay = (PurchasePayListEntryInfo) purPayList.get(i);
			timeMap.put(purPay.getId().toString(), purPay.getLastUpdateTime());
			
			BigDecimal maxRefundmentAmount = purPay.getAllRemainAmount(); // Ĭ�Ͽ��˽�
//			MoneyTypeEnum monType = purPay.getMoneyDefine().getMoneyType();
//			if(monType!=null && !monType.equals(MoneyTypeEnum.PreconcertMoney)) {
				QuitPayListEntryInfo quitPay = new QuitPayListEntryInfo();
				quitPay.setMoneyDefine(purPay.getMoneyDefine());
				quitPay.setSeq(purPay.getSeq());
				quitPay.setPayListId(purPay.getId().toString());
				quitPay.setApAmount(purPay.getAppAmount());
				quitPay.setActPayAmount(purPay.getActRevAmount());
				quitPay.setCanRefundmentAmount(maxRefundmentAmount);
				quitPay.setMaxCanRefundAmount(maxRefundmentAmount);
				quitPay.setHasRemitAmount(purPay.getHasTransferredAmount());
				quitPay.setFeeFromType(FeeFromTypeEnum.ShouldPayList);
				quitPayList.add(quitPay);
//			}
		}

		PurchaseElsePayListEntryCollection elsePayList = purchase
				.getElsePayListEntry(); // ��������
		CRMHelper.sortCollection(elsePayList, "seq", true);
		for (int i = 0; i < elsePayList.size(); i++) {
			PurchaseElsePayListEntryInfo elsePay = (PurchaseElsePayListEntryInfo) elsePayList
					.get(i);
			BigDecimal maxRefundmentAmount = elsePay.getAllRemainAmount(); 

			QuitPayListEntryInfo quitPay = new QuitPayListEntryInfo();
			quitPay.setMoneyDefine(elsePay.getMoneyDefine());
			//�����Ӧ����ϸ����ô����Ӧ�յ����Ӧ����Ӧ�յĻ���������   by_lijun
			if(purPayList.size()>0)
			{
				int seq = purPayList.size()+i;
				quitPay.setSeq(seq);
			}else
			{
				quitPay.setSeq(elsePay.getSeq());
			}
			
			quitPay.setPayListId(elsePay.getId().toString());
			quitPay.setApAmount(elsePay.getAppAmount());
			quitPay.setActPayAmount(elsePay.getActRevAmount());
			quitPay.setCanRefundmentAmount(maxRefundmentAmount);
			quitPay.setMaxCanRefundAmount(maxRefundmentAmount);
			quitPay.setHasRemitAmount(elsePay.getHasTransferredAmount());
			quitPay.setFeeFromType(FeeFromTypeEnum.ElsePayList);
			quitPayList.add(quitPay);

			timeMap.put(elsePay.getId().toString(), elsePay
					.getLastUpdateTime());
		}


		return quitPayList;
	}

	/**
	 * �����ѽ�����ڼ䣬����������˷����޸�
	 * */
	private void verifyBalance() {
		Date bizDate = (Date) this.pkQuitDate.getValue();
		if (bizDate == null) {
			MsgBox.showInfo("�˷����ڲ���Ϊ�ա�");
			this.abort();
		}
		Date balanceEndDate = null;
		RoomInfo room = (RoomInfo) this.f7Room.getValue();
		SelectorItemCollection selColl = new SelectorItemCollection();
		selColl.add("building.sellProject.*");
		try {
			room = RoomFactory.getRemoteInstance().getRoomInfo(
					new ObjectUuidPK(BOSUuid.read(room.getId().toString())),
					selColl);
		} catch (EASBizException e) {
			handleException(e);
			e.printStackTrace();
		} catch (BOSException e) {
			handleException(e);
			e.printStackTrace();
		} catch (UuidException e) {
			handleException(e);
			e.printStackTrace();
		}
		SellProjectInfo sellProject = room.getBuilding().getSellProject();
		if (sellProject != null) {
			try {
				balanceEndDate = getLastEndDate(sellProject.getId().toString());
			} catch (Exception e) {
				handleException(e);
				e.printStackTrace();
			}
			SHEHelper.verifyBalance(bizDate, balanceEndDate);
		}
	}

	/***
	 * ��������Ŀȡ�ϴν���Ľ�ֹ���ڡ�
	 * **/

	private Date getLastEndDate(String sellProjectId) throws Exception {
		Date lastEndDate = null;
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder
				.appendSql("select FBalanceEndDate from T_SHE_SellProject where FID = ?");
		detailBuilder.addParam(sellProjectId);
		try {
			IRowSet detailSet = detailBuilder.executeQuery();
			if (detailSet.next()) {
				lastEndDate = detailSet.getDate("FBalanceEndDate");
			}
		} catch (Exception e) {
			handleException(e);
			e.printStackTrace();
		}
		return lastEndDate;
	}

	private void verifyAddNewRoom(RoomInfo room) {
//		if (room.getSellState().equals(RoomSellStateEnum.Init)) {
//			MsgBox.showInfo("����û�п���!");
//			this.abort();
//		}
//		if (room.getSellState().equals(RoomSellStateEnum.OnShow)) {
//			MsgBox.showInfo("����û������!");
//			this.abort();
//		}

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("attachmentEntry.room.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("attachmentEntry.room.id", room.getId()
						.toString()));
		PurchaseRoomAttachmentEntryCollection attachmentEntry = null;
		try {
			attachmentEntry = PurchaseRoomAttachmentEntryFactory
					.getRemoteInstance()
					.getPurchaseRoomAttachmentEntryCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}

		if (attachmentEntry.size() > 0) {
			MsgBox.showInfo("�÷����Ѿ�����������󶨲��Ҳ����ͬ,������������в����˷�!");
			this.abort();
		}
	}

	private void verifyQuitPayList() {
		for (int i = 0; i < this.tblSet.getRowCount(); i++) {
			IRow row = this.tblSet.getRow(i);
			QuitPayListEntryInfo quitEntry = (QuitPayListEntryInfo) row
					.getUserObject();
			BigDecimal canRefundmentAmount = (BigDecimal) row.getCell(
					"canRefundmentAmount").getValue();
			BigDecimal maxCanRefundAmount = quitEntry.getMaxCanRefundAmount();
			if (canRefundmentAmount != null && maxCanRefundAmount != null) {
				if (canRefundmentAmount.compareTo(FDCHelper.ZERO) < 0) {
					maxCanRefundAmount = maxCanRefundAmount.setScale(2);
					MsgBox.showInfo(this, "��" + (i + 1) + "�п��˽��С��0��");
					this.abort();
				}

				if (canRefundmentAmount.compareTo(maxCanRefundAmount) > 0) {
					maxCanRefundAmount = maxCanRefundAmount.setScale(2);
					MsgBox.showInfo(this, "��" + (i + 1) + "�п��˽��������˽�� "
							+ maxCanRefundAmount + "��");
					this.abort();
				}
			}
		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		return QuitRoomFactory.getRemoteInstance();
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("feeMoneyDefine.name");
		sels.add("feeAccount.name");
		sels.add("room.*");
		sels.add("quitEntrys.*");
		sels.add("quitEntrys.moneyDefine.*");
		sels.add("quitRoomReason.id");
		sels.add("quitRoomReason.name");
		sels.add("quitRoomReason.number");
		sels.add("state");
		sels.add("lastPurchase.snapRoomPrice");
		sels.add("lastPurchase.snapBuildPrice");
		return sels;
	}

	protected void chkIsAdjust_actionPerformed(ActionEvent e) throws Exception {
		super.chkIsAdjust_actionPerformed(e);
		if (this.chkIsAdjust.isSelected()) {
			String changeLine = this.txtRemark.getText().replaceAll("\\\\r", "\r")
			.replaceAll("\\\\n", "\n");
			this.txtRemark.setText(changeLine);
			this.txtRemark.setVisible(true);
			this.txtNewBuildingPrice.setValue(null);
			this.txtNewRoomPrice.setValue(null);
			if (this.chkIsSellByRoom.isSelected()) {
				this.contNewBuildingPrice.setEnabled(false);
				this.contNewRoomPrice.setEnabled(true);
			} else {
				this.contNewBuildingPrice.setEnabled(true);
				this.contNewRoomPrice.setEnabled(false);
			}
		} else {
			this.txtRemark.setVisible(false);
			this.txtNewBuildingPrice.setEnabled(false);
			this.txtNewRoomPrice.setEnabled(false);
		}
	}

	protected KDTable getDetailTable() {
		return null;
	}

	public boolean destroyWindow() {
		if (plUI != null) {
			plUI.destroyWindow();
		}
		return super.destroyWindow();
	}

	/*** ĳ�����Ƿ����δ��˵��˷���   �ҷ�����״̬��*/
	/*private boolean existUnAuditQuitInfo(String roomIdStr)
			throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", roomIdStr));
		filter.getFilterItems().add(new FilterItemInfo("auditor", null));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		return QuitRoomFactory.getRemoteInstance().exists(filter);
	}*/
	/***
	 * �ж��Ƿ��Ѿ����ڱ��棬δ�ύ����������״̬���˷���
	 * by renliang at 2010-12-6
	 */
	private void existUnAuditQuitInfo(String roomIdStr)throws EASBizException, BOSException {
		
		EntityViewInfo view  = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", roomIdStr));
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("state"));
		view.setFilter(filter);
		view.setSelector(coll);
		QuitRoomCollection roomColl = QuitRoomFactory.getRemoteInstance().getQuitRoomCollection(view);
		if(roomColl!=null && roomColl.size()>0){
			for (int i = 0; i < roomColl.size(); i++) {
				QuitRoomInfo roomInfo = roomColl.get(i);
				if (roomInfo != null) {
					if (roomInfo.getState().equals(FDCBillStateEnum.SAVED)
							|| roomInfo.getState().equals(FDCBillStateEnum.SUBMITTED)
//							|| roomInfo.getState().equals(FDCBillStateEnum.AUDITTED)
							|| roomInfo.getState().equals(FDCBillStateEnum.AUDITTING)) {
						FDCMsgBox.showWarning(this, "�÷����Ѿ��˹�������Ҫ���ظ��ύ�˷���!");
						this.abort();
					}
				}
			}
		}
	}

	/** У���� �˷����� ���ύ�����ʱ���� ������ϸ��û�з������ı� */
	private Map timeMap = new HashMap();

	private boolean someThingHasChanged(String purchaseId) throws BOSException,
			EASBizException {
		if (purchaseId == null)
			return true;

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("auditTime");
		sels.add("payListEntry.lastUpdateTime");
		sels.add("elsePayListEntry.lastUpdateTime");
		PurchaseInfo purchase = PurchaseFactory.getRemoteInstance()
				.getPurchaseInfo(new ObjectUuidPK(purchaseId), sels);

		PurchasePayListEntryCollection purPayList = purchase.getPayListEntry(); // Ӧ�ղ���
		for (int i = 0; i < purPayList.size(); i++) {
			PurchasePayListEntryInfo purPay = (PurchasePayListEntryInfo)purPayList.get(i);
			if (!timeMap.containsKey(purPay.getId().toString()))
				return true;
			if (timeMap.get(purPay.getId().toString()) == null) {
				if (purPay.getLastUpdateTime() != null)
					return true;
			} else {
				Timestamp lastUpTime = (Timestamp) timeMap.get(purPay.getId().toString());
				if (purPay.getLastUpdateTime() != null
						&& lastUpTime != null) {
					if (purPay.getLastUpdateTime().compareTo(lastUpTime) != 0)
						return true;
				} else if (purPay.getLastUpdateTime() != null
						&& lastUpTime == null) {
					return true;
				} else if (purPay.getLastUpdateTime() == null
						&& lastUpTime != null) {
					return true;
				}
			}
		}

		PurchaseElsePayListEntryCollection elsePayList = purchase.getElsePayListEntry(); // ��������
		for (int i = 0; i < elsePayList.size(); i++) {
			PurchaseElsePayListEntryInfo elsePay = (PurchaseElsePayListEntryInfo)elsePayList.get(i);
			if (!timeMap.containsKey(elsePay.getId().toString()))
				return true;
			if (timeMap.get(elsePay.getId().toString()) == null) {
				if (elsePay.getLastUpdateTime() != null)
					return true;
			} else {
				Timestamp lastUpTime = (Timestamp) timeMap.get(elsePay.getId().toString());
				if (elsePay.getLastUpdateTime() != null
						&& lastUpTime != null) {
					if (elsePay.getLastUpdateTime().compareTo(lastUpTime) != 0)
						return true;
				} else if (elsePay.getLastUpdateTime() != null
						&& lastUpTime == null) {
					return true;
				} else if (elsePay.getLastUpdateTime() == null
						&& lastUpTime != null) {
					return true;
				}
			}
		}

		return false;
	}	

}