package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryTwoCollection;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeEntryTwoInfo;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeFactory;
import com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.PropertyStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;

public class RoomPropertyBookEditUI extends AbstractRoomPropertyBookEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomPropertyBookEditUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	private RoomInfo room = null;
	
	private boolean isOld = false;

//	private PropertyDoSchemeInfo pdsInfo = null;

	public RoomPropertyBookEditUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();

		// checkDataForSave();
		if (combPropertyState.getSelectedItem() == null) {
			editData.setPropertyState(null);
		} else {
			editData.setPropertyState((PropertyStateEnum) combPropertyState.getSelectedItem());
		}
	}

	/**
	 * ����ǰ�����ݽ����ж�:�Ƿ�󶨲�Ȩ������
	 *//*
	private void checkDataForSave() {
		// �����ǰ����û��ʵ�ֲ�Ȩ����������Ե�ʱ�Ƿ��һ����Ȩ�����������ж�
		// ����Ѿ�������ֻҪ�Ե�ǰ���ݽ��б���
		if (editData.getPropertyDoScheme() == null) {
			IRow row = tblEntry.getRow(0);
			// �����¼һ���еĵ�һ��isFinishΪtrue��˵���������޸ģ������Ȩ������
			// ���û�У���˵���������Ȩ��������
			if (((Boolean) row.getCell("isFinish").getValue())
					.equals(Boolean.TRUE)) {
				editData.setPropertyDoScheme(pdsInfo);
				tblEntryForSave();
			} else {
				for (int i = 0; i < tblEntryTwo.getRowCount(); i++) {
					IRow r = tblEntryTwo.getRow(i);
					if (((Boolean) r.getCell("isFinish").getValue())
							.equals(Boolean.TRUE)) {
						editData.setPropertyDoScheme(pdsInfo);
						tblEntryForSave();
						break;
					}
				}
			}
		} else {
			tblEntryDataForSave();
		}
	}*/

	/**
	 * ���������޸ĵ����ݽ��б���
	 *//*
	private void tblEntryDataForSave() {
		if (editData.getEntry() == null || editData.getEntryTwo() == null) {
			return;
		}
		// editData.getEntry().clear();
		// editData.getEntryTwo().clear();
		for (int i = 0; i < tblEntry.getRowCount(); i++) {
			IRow row = tblEntry.getRow(i);
			RoomPropertyBookEntryInfo rpbeInfo = editData.getEntry().get(i);
			rpbeInfo.setName(row.getCell("name").getValue().toString());
			rpbeInfo
					.setDescription(row.getCell("description").getValue() == null ? null
							: row.getCell("description").getValue().toString());
			rpbeInfo.setIsFinish(((Boolean) row.getCell("isFinish").getValue())
					.booleanValue());
			if (((Boolean) row.getCell("isFinish").getValue()).booleanValue()) {
				editData.setStep(row.getCell("name").getValue().toString());
			} else if (!((Boolean) row.getCell("isFinish").getValue())
					.booleanValue()
					&& i == 0) {
				editData.setStep(null);
			}
			rpbeInfo
					.setProcessDate(row.getCell("processDate").getValue() == null ? null
							: (Date) row.getCell("processDate").getValue());
		}

		for (int i = 0; i < tblEntryTwo.getRowCount(); i++) {
			IRow row = tblEntryTwo.getRow(i);
			RoomPropertyBookEntryTwoInfo rpbetInfo = editData.getEntryTwo()
					.get(i);
			rpbetInfo.setName(row.getCell("name").getValue().toString());
			rpbetInfo
					.setDescription(row.getCell("description").getValue() == null ? null
							: row.getCell("description").getValue().toString());
			rpbetInfo
					.setIsFinish(((Boolean) row.getCell("isFinish").getValue())
							.booleanValue());
			rpbetInfo
					.setProcessDate(row.getCell("processDate").getValue() == null ? null
							: (Date) row.getCell("processDate").getValue());
		}
	}

	*//**
	 * ���������浱ǰҳ��ķ�¼����(�������ڵ�һ�α���)
	 *//*
	private void tblEntryForSave() {
		editData.getEntry().clear();
		editData.getEntryTwo().clear();
		for (int i = 0; i < tblEntry.getRowCount(); i++) {
			IRow row = tblEntry.getRow(i);
			RoomPropertyBookEntryInfo rpbeInfo = new RoomPropertyBookEntryInfo();
			rpbeInfo.setName(row.getCell("name").getValue().toString());
			rpbeInfo
					.setDescription(row.getCell("description").getValue() == null ? null
							: row.getCell("description").getValue().toString());
			rpbeInfo.setIsFinish(((Boolean) row.getCell("isFinish").getValue())
					.booleanValue());
			if (((Boolean) row.getCell("isFinish").getValue()).booleanValue()) {
				editData.setStep(row.getCell("name").getValue().toString());
			} else if (!((Boolean) row.getCell("isFinish").getValue())
					.booleanValue()
					&& i == 0) {
				editData.setStep(null);
			}
			rpbeInfo
					.setProcessDate(row.getCell("processDate").getValue() == null ? null
							: (Date) row.getCell("processDate").getValue());
			editData.getEntry().add(rpbeInfo);
		}

		for (int i = 0; i < tblEntryTwo.getRowCount(); i++) {
			IRow row = tblEntryTwo.getRow(i);
			RoomPropertyBookEntryTwoInfo rpbetInfo = new RoomPropertyBookEntryTwoInfo();
			rpbetInfo.setName(row.getCell("name").getValue().toString());
			rpbetInfo
					.setDescription(row.getCell("description").getValue() == null ? null
							: row.getCell("description").getValue().toString());
			rpbetInfo
					.setIsFinish(((Boolean) row.getCell("isFinish").getValue())
							.booleanValue());
			rpbetInfo
					.setProcessDate(row.getCell("processDate").getValue() == null ? null
							: (Date) row.getCell("processDate").getValue());
			editData.getEntryTwo().add(rpbetInfo);
		}
	}*/

	/**
	 * ����������
	 */
	protected IObjectValue createNewData() {
		RoomPropertyBookInfo objectValue = new RoomPropertyBookInfo();
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setTransactor(SysContext.getSysContext()
				.getCurrentUserInfo());
		String roomId = getUIContext().get("roomId").toString();
		try {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("building.*");
			sic.add("building.sellProject.*");
			room = RoomFactory.getRemoteInstance().getRoomInfo(
					new ObjectUuidPK(BOSUuid.read(roomId)), sic);
			objectValue.setRoom(room);
		} catch (EASBizException e) {
			logger.error(e);
		} catch (BOSException e) {
			logger.error(e);
		} catch (UuidException e) {
			logger.error(e);
		}
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		objectValue.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit()
				.castToFullOrgUnitInfo());
		objectValue.setBookedDate(new Date());
		// ���ò�Ȩ֤״̬,Ĭ��Ϊ��
		this.combPropertyState.setSelectedIndex(-1);

		checkBox.setEnabled(false);
		combPropertyState.setEnabled(false);
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomPropertyBookFactory.getRemoteInstance();
	}

	/**
	 * ������Ч�鵱ǰѡ�����Ŀ���Ƿ���ڲ�Ȩ������ ���û�У���������޸ģ�
	 * 
	 * @return
	 *//*
	private void checkHasPropertyDoScheme() {
		boolean flag = false;
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		if (room == null) {
			return;
		}
		filter.getFilterItems().add(
				new FilterItemInfo("project.id", room.getBuilding()
						.getSellProject().getId().toString()));
		evi.setFilter(filter);
		try {
			PropertyDoSchemeCollection pdsCol = PropertyDoSchemeFactory
					.getRemoteInstance().getPropertyDoSchemeCollection(evi);
			if (pdsCol.size() == 0) {
				flag = false;
			} else {
				flag = true;
				pdsInfo = pdsCol.get(0);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		// �жϸ�������Ŀ�������õĲ�Ȩ������
		if (flag) {
			if (pdsInfo != null) {
				notHasPropertyDoScheme(pdsInfo);
			}
		} else {
			MsgBox.showWarning(this, "�������ò�Ȩ�����������ú���ܱ༭��");
			SysUtil.abort();
		}
	}

	*//**
	 * �������жϵ�ǰ�ĵ����Ƿ��Ѿ������˲�Ȩ������ ������ڣ�����Ҫ�жϵ�������Ӧ����Ŀ�Ƿ���ڲ�Ȩ������ ��������ڣ���Ҫ�жϵ��ݡ���������
	 *//*
	private boolean checkHasPropertyForThis() {
		if (editData.getId() == null) {
			return false;
		} else {
			if (editData.getPropertyDoScheme() == null
					|| editData.getPropertyDoScheme().getId() == null) {
				return false;
			} else {
				return true;
			}
		}
	}
*/
	/**
	 * ��������ǰ���ݴ��ڲ�Ȩ�������Ĵ���
	 */
	private void hasPropertyDoScheme() {
		//��ʼ������б༭����
		this.tblEntry.getColumn("actFinishDate").getStyleAttributes().setLocked(true);
		this.tblEntry.getColumn("transactor").getStyleAttributes().setLocked(true);
		
		KDDatePicker datePicker = new KDDatePicker();
		this.tblEntry.getColumn("promFinishDate").setEditor(new KDTDefaultCellEditor(datePicker));
		
		KDBizPromptBox transactorBox = new KDBizPromptBox();
		transactorBox.setEditable(false);
		transactorBox.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
		this.tblEntry.getColumn("transactor").setEditor(new KDTDefaultCellEditor(transactorBox));

		KDTextField txtRemark = new KDTextField();
		txtRemark.setMaxLength(255);
		this.tblEntry.getColumn("description").setEditor(new KDTDefaultCellEditor(txtRemark));
		this.tblEntryTwo.getColumn("description").setEditor(new KDTDefaultCellEditor(txtRemark));
		
		if (tblEntry.getRowCount() > 0) {
			tblEntry.removeRows();
		}
		if (tblEntryTwo.getRowCount() > 0) {
			tblEntryTwo.removeRows();
		}
		for (int i = 0; i < editData.getEntry().size(); i++) {
			RoomPropertyBookEntryInfo rpbeInfo = editData.getEntry().get(i);
			IRow row = tblEntry.addRow(i);
			row.getCell("name").setValue(rpbeInfo.getName());
			row.getCell("promFinishDate").setValue(rpbeInfo.getPromiseFinishDate());
			row.getCell("isFinish").setValue(Boolean.valueOf(rpbeInfo.isIsFinish()));
			row.getCell("actFinishDate").setValue(rpbeInfo.getActualFinishDate());
			row.getCell("transactor").setValue(rpbeInfo.getTransactor());
			row.getCell("processDate").setValue(rpbeInfo.getProcessDate());
			row.getCell("description").setValue(rpbeInfo.getDescription());
			row.getCell("isFinishFlag").setValue(new Boolean(rpbeInfo.isIsFinishFlag()));
			
			if(rpbeInfo.isIsFinish()){
				row.getCell("actFinishDate").getStyleAttributes().setLocked(false);
				row.getCell("transactor").getStyleAttributes().setLocked(false);
			}
		}
		for (int i = 0; i < editData.getEntryTwo().size(); i++) {
			RoomPropertyBookEntryTwoInfo rpbetInfo = editData.getEntryTwo()
					.get(i);
			IRow row = tblEntryTwo.addRow(i);
			row.getCell("processDate").setValue(rpbetInfo.getProcessDate());
			row.getCell("isFinish").setValue(
					Boolean.valueOf(rpbetInfo.isIsFinish()));
			row.getCell("name").setValue(rpbetInfo.getName());
			row.getCell("description").setValue(rpbetInfo.getDescription());
		}
	}

	/**
	 * ��������ǰ���ݲ����ڲ�Ȩ�������Ĵ���
	 *//*
	private void notHasPropertyDoScheme(PropertyDoSchemeInfo pdsInfo) {
		tblEntry.removeRows();
		tblEntryTwo.removeRows();
		for (int i = 0; i < pdsInfo.getEntry().size(); i++) {
			PropertyDoSchemeEntryInfo pdseInfo = pdsInfo.getEntry().get(i);
			IRow row = this.tblEntry.addRow(i);
			row.getCell("processDate").setValue(new Date());
			row.getCell("isFinish").setValue(Boolean.FALSE);
			row.getCell("name").setValue(pdseInfo.getName());
			row.getCell("description").setValue(pdseInfo.getDescription());
		}
		for (int i = 0; i < pdsInfo.getEntryTwo().size(); i++) {
			PropertyDoSchemeEntryTwoInfo pdsetInfo = pdsInfo.getEntryTwo().get(
					i);
			IRow row = this.tblEntryTwo.addRow(i);
			row.getCell("processDate").setValue(new Date());
			row.getCell("isFinish").setValue(Boolean.FALSE);
			row.getCell("name").setValue(pdsetInfo.getName());
			row.getCell("description").setValue(pdsetInfo.getDescription());
		}
	}*/

	public void loadFields() {
		super.loadFields();
		// ��������formatXml�Ͱ���Ϣ������Ѿ������������ٽ�����
		tblEntry.checkParsed();
		tblEntryTwo.checkParsed();
		String roomId = this.editData.getRoom().getId().toString();
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("building.*");
		sic.add("building.sellProject.*");
		try {
			room = RoomFactory.getRemoteInstance().getRoomInfo(
					new ObjectUuidPK(BOSUuid.read(roomId)), sic);
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		} catch (UuidException e1) {
			e1.printStackTrace();
		}

		hasPropertyDoScheme();

		UIContext uiContextRoom = new UIContext(ui);
		uiContextRoom.put(UIContext.ID, roomId);
		if(this.editData.getBatchManage() != null){
			uiContextRoom.put("batchNumber", this.editData.getBatchManage().getNumber());
		}
		CoreUIObject plUI = null;
		try {
			plUI = (CoreUIObject) UIFactoryHelper.initUIObject(
					RoomBizInfoUI.class.getName(), uiContextRoom, null, "VIEW");
		} catch (UIException e) {
			logger.error(e);
		}
		sclPanel.setViewportView(plUI);
		sclPanel.setKeyBoardControl(true);
	}

	public void onShow() throws Exception {
		super.onShow();
		// FDCClientHelper
		// .formatKDFormattedINT(new KDFormattedTextField[] { txtRuhuIndex });
	}

	public void onLoad() throws Exception {

		this.chkMenuItemSubmitAndAddNew.setEnabled(false);
		tblEntry.checkParsed();
		tblEntryTwo.checkParsed();

		super.onLoad();

		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionSave, actionCopy, actionPre, actionNext, actionFirst,
				actionLast, actionCancel, actionCancelCancel, actionRemove,
				actionPrint, actionPrintPreview }, false);
		menuView.setVisible(false);
		menuBiz.setVisible(false);
		menuSubmitOption.setVisible(false);
		handleCodingRule();

		if (!FDCSysContext.getInstance().checkIsSHEOrg()) {
			actionEdit.setEnabled(false);
		}
		boolean flag = true;
		for (int i = 0; i < editData.getEntry().size(); i++) {
			RoomPropertyBookEntryInfo rpbeInfo = editData.getEntry().get(i);
			flag = rpbeInfo.isIsFinish();
			if (!flag) {
				checkBox.setEnabled(false);
				combPropertyState.setEnabled(false);
				combPropertyState.setSelectedIndex(-1);
				break;
			}
		}
		for (int i = 0; i < editData.getEntryTwo().size(); i++) {
			RoomPropertyBookEntryTwoInfo rpbeetInfo = editData.getEntryTwo()
					.get(i);
			flag = rpbeetInfo.isIsFinish();
			if (!flag) {
				checkBox.setEnabled(false);
				combPropertyState.setEnabled(false);
				combPropertyState.setSelectedIndex(-1);
				break;
			}
		}
		// ��ʼ���Ƿ�������
		if (RoomBookStateEnum.BOOKED.equals(room.getRoomBookState())) {
			checkBox.setSelected(true);
			pkActFinishDate.setEnabled(true);
			combPropertyState.setEnabled(true);
			if(combPropertyState.getSelectedItem()!=null 
					&& this.combPropertyState.getSelectedItem().equals(PropertyStateEnum.bankMortgage)){
				this.contMortgageDate.setEnabled(true);
				this.contMortgageBank.setEnabled(true);
			}
			else{
				this.contMortgageDate.setEnabled(false);
				this.contMortgageBank.setEnabled(false);
			}
		} else {
			checkBox.setEnabled(false);
			checkBox.setSelected(false);
			pkActFinishDate.setEnabled(false);
			combPropertyState.setEnabled(false);
			contMortgageBank.setEnabled(false);
			contMortgageDate.setEnabled(false);
		}
		// ��ʼ����Ȩ֤״̬
		if (editData.getPropertyState() == null) {
			this.combPropertyState.setSelectedIndex(-1);
		} else {
			combPropertyState.setSelectedItem(editData.getPropertyState());
		}

		// ��ʼ����¼�п���������ݴ�С
		initMaxLength();

		if (OprtState.VIEW.equals(getOprtState())) {
			actionEdit.setEnabled(true);
		}

		if (getUIContext().get("BatchId") != null) {
			this.actionEdit.setEnabled(false);
		}

		txtMortgageBank.setMaxLength(70);

		initScheme();
	}

	/**
	 * ��ʼ����Ȩ����
	 * @throws EASBizException
	 * @throws BOSException
	 * @author liang_ren969
	 * @date 2011-9-9
	 */
	private void initScheme() throws EASBizException, BOSException {
		// ���÷����Ĺ�������
		String sellPrjId = String.valueOf(getUIContext().get("sellProjectId"));
		EntityViewInfo view = new EntityViewInfo();

		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		
		Set id=new HashSet();
		SellProjectInfo info = new SellProjectInfo();
		info.setId(BOSUuid.read(sellPrjId));
		id = SHEManageHelper.getAllParentSellProjectCollection(info,id);
	
		if (id != null && id.size() > 0) {
			filter.getFilterItems().add(
				new FilterItemInfo("project.id",FDCTreeHelper.getStringFromSet(id),
						CompareType.INNER));
		}else{
			filter.getFilterItems().add(
					new FilterItemInfo("project.id", null,
							CompareType.EQUALS));
		}
		view.setFilter(filter);
		prmtScheme.setEntityViewInfo(view);
	}

	private void initMaxLength() {
		KDTextField formattedTextField = new KDTextField();
		formattedTextField.setMaxLength(30);
		KDTDefaultCellEditor numberEditor = new KDTDefaultCellEditor(
				formattedTextField);
		tblEntry.getColumn("description").setEditor(numberEditor);
		tblEntryTwo.getColumn("description").setEditor(numberEditor);
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyUIControlEmpty(this);

		/*EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("room.id", editData.getRoom().getId()
						.toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("isBlankOut", Boolean.TRUE,
						CompareType.NOTEQUALS));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("signDate");
		view.getSelector().add("onRecordDate");
		view.getSelector().add("contractNumber");

		RoomSignContractCollection roomColl = RoomSignContractFactory
				.getRemoteInstance().getRoomSignContractCollection(view);
		if (roomColl.size() > 0) {
			RoomSignContractInfo contract = roomColl.get(0);
			if (pkTransactDate.getValue() != null
					&& (DateTimeUtils.dayBefore((Date) pkTransactDate
							.getValue(), contract.getSignDate()))) {
				MsgBox.showInfo(this, contTransactDate.getBoundLabelText()
						+ "��������ǩԼ���ڣ�");
				SysUtil.abort();
			}
		}*/

		super.verifyInput(e);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selector = super.getSelectors();
		selector.add("*");
		selector.add("room.*");
		selector.add("batchManage.number");
		selector.add("entry.*");
		selector.add("entryTwo.*");
		selector.add("propertyDoScheme.Entry.*");
		selector.add("propertyDoScheme.EntryTwo.*");
		selector.add("CU.*");
		return selector;
	}

	/**
	 * ����������
	 * 
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException {

		String currentOrgId = FDCClientHelper.getCurrentOrgId();

		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();

		if (STATUS_ADDNEW.equals(this.oprtState)
				&& iCodingRuleManager.isExist(editData, currentOrgId)) {
			// EditUI�ṩ�˷�������û�е��ã���onload����ã��Ը��ǳ�����loadfields����ĵ��ã��õ���û�д���Ϻ�ѡ��

			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData,
					currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			} else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId)) { //�˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						//Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�///////////////////////////////////
						// ///////
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				txtNumber.setText(number);
			}

			setNumberTextEnabled();
		}
	}

	/**
	 * getNumberByCodingRuleֻ�ṩ�˻�ȡ����Ĺ��ܣ�û���ṩ���õ��ؼ��Ĺ��ܣ�ʵ�ִ˷��������ݱ�������"�Ƿ�������ʾ"
	 * �������ñ��뵽�ؼ�
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);

		txtNumber.setText(number);

	}

	protected void setNumberTextEnabled() {
		if (txtNumber != null) {
			// CostCenterOrgUnitInfo currentCostUnit =
			// SysContext.getSysContext()
			// .getCurrentCostUnit();

			String currentOrgId = FDCClientHelper.getCurrentOrgId();

			if (currentOrgId != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(
						editData, currentOrgId);

				txtNumber.setEnabled(isAllowModify);
				txtNumber.setEditable(isAllowModify);
				txtNumber.setRequired(isAllowModify);
			}
		}
	}

	/**
	 * �������ύ
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		setOprtState(STATUS_EDIT);

		if (prmtScheme.getValue() == null) {
			MsgBox.showWarning("��Ȩ������Ϊ�գ�����ִ�д˲�����");
			abort();
		}

		String curApproach = "";
		if (this.checkBox.isSelected()) {  //�������
			curApproach = "�������";
			if (editData.getPropState() == null
					|| editData.getPropState().equals(AFMortgagedStateEnum.UNTRANSACT)
					|| editData.getPropState().equals(AFMortgagedStateEnum.TRANSACTING)) {
				this.editData.setPropState(AFMortgagedStateEnum.TRANSACTED);
			}
			//����ҵ��������Ӧ�ķ���
			SHEManageHelper.updateTransactionOverView(null, this.editData.getRoom(), SHEManageHelper.INTEREST,
					this.editData.getPromiseFinishDate(), (Date)this.pkActFinishDate.getValue(), false);
		} 
		else {  //ȡ��һ��Ϊ��ɵĽ�����Ϊ��ǰ����
			int finishCount = 0;
			for (int i = 0; i < this.tblEntry.getRowCount(); i++) {
				if (this.tblEntry.getRow(i).getCell("isFinish").getValue().equals(Boolean.TRUE)) {
					curApproach = this.tblEntry.getRow(i).getCell("name").getValue().toString();
					finishCount++;
				}
			}
			if(finishCount == 0){  //δ����
				this.editData.setPropState(AFMortgagedStateEnum.UNTRANSACT);
			}
			else{  //������
				this.editData.setPropState(AFMortgagedStateEnum.TRANSACTING);
			}
			//����ҵ��������Ӧ�ķ���
			SHEManageHelper.updateTransactionOverView(null, this.editData.getRoom(), SHEManageHelper.INTEREST,
				this.editData.getPromiseFinishDate(), null, false);
		}
		this.editData.setStep(curApproach);
		this.isOld = true;
		super.actionSubmit_actionPerformed(e);
		
		if (checkBox.getSelectState() == 32) {
			room.setRoomBookState(RoomBookStateEnum.BOOKED);
		} else {
			room.setRoomBookState(RoomBookStateEnum.BOOKING);
		}
		if (room != null) {
			RoomFactory.getRemoteInstance().update(
					new ObjectUuidPK(room.getId()), room);
		}

		/*
		 * //��һ��������״̬�������ҳ��رգ�����������µ�����ҳ��
		 * if(OprtState.ADDNEW.equals(getOprtState())){
		 * MsgBox.showInfo(this,"����ɹ���"); this.getUIWindow().close(); }else{
		 * this.setOprtState(OprtState.VIEW); lockUIForViewStatus(); }
		 */
	}

	/**
	 * ����������ֵ�༭�ı��
	 */
	protected void tblEntry_editStopped(KDTEditEvent e) throws Exception {
		IRow row = tblEntry.getRow(e.getRowIndex());
		if ("isFinish".equals(tblEntry.getColumnKey(e.getColIndex()))) {
			Object af = row.getCell("isFinish").getValue();
			if (af.equals(Boolean.TRUE)) {
				row.getCell("processDate").setValue(new Date());
				row.getCell("actFinishDate").setValue(new Date());
				row.getCell("actFinishDate").getStyleAttributes().setLocked(false);
				row.getCell("transactor").setValue(SysContext.getSysContext().getCurrentUserInfo());
				row.getCell("transactor").getStyleAttributes().setLocked(false);
				
				if(row.getCell("isFinishFlag").getValue()!=null && ((Boolean)row.getCell("isFinishFlag").getValue()).booleanValue()){
					checkBox.setSelected(true);
					this.pkActFinishDate.setValue(new Date());
					this.pkActFinishDate.setEnabled(true);
				}
				
			} else {
				row.getCell("processDate").setValue(null);
				row.getCell("actFinishDate").setValue(null);
				row.getCell("actFinishDate").getStyleAttributes().setLocked(true);
				row.getCell("transactor").getStyleAttributes().setLocked(true);
				
				if(row.getCell("isFinishFlag").getValue()!=null && ((Boolean)row.getCell("isFinishFlag").getValue()).booleanValue()){
					checkBox.setSelected(false);
					this.pkActFinishDate.setValue(null);
					this.pkActFinishDate.setEnabled(false);
				}
			}
		}
		if(row.getUserObject() != null){
			RoomPropertyBookEntryInfo bookEntryInfo = (RoomPropertyBookEntryInfo) row.getUserObject();
			if(row.getCell("transactor") != null){
				UserInfo transactor = (UserInfo) row.getCell("transactor").getValue();
				bookEntryInfo.setTransactor(transactor);
			}
		}
	}

	/**
	 * �������༭���¼��������ж��Ƿ��ܹ���ѡ������ɿؼ�
	 */
	protected void tblEntryTwo_editStopped(KDTEditEvent e) throws Exception {
		super.tblEntryTwo_editStopped(e);
	}

	protected void tblEntry_editValueChanged(KDTEditEvent e) throws Exception {
	}

	protected void combPropertyState_propertyChange(PropertyChangeEvent e)
			throws Exception {
		super.combPropertyState_propertyChange(e);
	}

	/**
	 * ����������״ֵ̬�ı�
	 */
	protected void checkBox_actionPerformed(ActionEvent e) throws Exception {
		super.checkBox_actionPerformed(e);

		if (this.checkBox.getSelectState() != 32) {
			int k = MsgBox.showConfirm3("�Ѱ�����ɣ��Ƿ�Ҫ��������");
			if (k == 0) {
				combPropertyState.setSelectedIndex(-1);
				this.combPropertyState.setEnabled(false);
				if (tblEntry.getColumn("isFinish") != null) {
					tblEntry.getColumn("isFinish").getStyleAttributes()
							.setLocked(false);
					tblEntryTwo.getColumn("isFinish").getStyleAttributes()
							.setLocked(false);
				}
			} else {
				this.combPropertyState.setEnabled(true);
				checkBox.setSelected(true);
				if (tblEntry.getColumn("isFinish") != null) {
					tblEntry.getColumn("isFinish").getStyleAttributes()
							.setLocked(true);
					tblEntryTwo.getColumn("isFinish").getStyleAttributes()
							.setLocked(true);
				}
			}
		} else {
			this.combPropertyState.setEnabled(true);
			if (tblEntry.getColumn("isFinish") != null) {
				tblEntry.getColumn("isFinish").getStyleAttributes().setLocked(
						true);
				tblEntryTwo.getColumn("isFinish").getStyleAttributes()
						.setLocked(true);
			}
		}
	}

	/**
	 * �������������ÿؼ���С
	 */
	public void initUIContentLayout() {
		super.initUIContentLayout();
		// ��Ϊ��ܻ�ͼ����������kdLayoutʱ������ִ�С�޷����Ƶ����⣬�������¸�panel����С
		// kDPanel2.putClientProperty("OriginalBounds", new Rectangle(0,0,952,
		// 494));
//		pnlRoomPropertyBookInfo.putClientProperty("OriginalBounds",
//				new Rectangle(0, 0, 995, 380));
	}

	protected void kDPanel1_mouseExited(MouseEvent e) throws Exception {
		super.kDPanel1_mouseExited(e);
	}

	protected void kDPanel1_keyPressed(KeyEvent e) throws Exception {
		super.kDPanel1_keyPressed(e);
	}

	protected void kDPanel1_focusGained(FocusEvent e) throws Exception {
		super.kDPanel1_focusGained(e);
	}
	
	protected void checkBox_itemStateChanged(ItemEvent e) throws Exception {
		super.checkBox_itemStateChanged(e);
		
		if(this.checkBox.isSelected()){
			this.combPropertyState.setEnabled(true);		
		}
		else{
			this.combPropertyState.setSelectedIndex(-1);
			this.combPropertyState.setEnabled(false);
			this.txtMortgageBank.setText(null);
			this.pkMortgageDate.setValue(null);
		}
	}
	
	protected void combPropertyState_itemStateChanged(ItemEvent e)
			throws Exception {
		super.combPropertyState_itemStateChanged(e);
		
		if(this.combPropertyState.getSelectedItem() != null){ 
			PropertyStateEnum proState = (PropertyStateEnum) this.combPropertyState.getSelectedItem();
			if(proState.getValue().equals(PropertyStateEnum.BANKMORTGAGE_VALUE)){
				this.contMortgageDate.setEnabled(true);
				this.contMortgageBank.setEnabled(true);
			}
		}
		else{
			this.contMortgageDate.setEnabled(false);
			this.contMortgageBank.setEnabled(false);
		}
	}

	protected void prmtScheme_dataChanged(DataChangeEvent e) throws Exception {
		Object newObj = e.getNewValue();
		Object oldObj = e.getOldValue();

		if (newObj == null && oldObj != null) {
			tblEntry.removeRows();
			tblEntryTwo.removeRows();
		} else if (newObj != null && oldObj == null) {
			loadPropertyScheme((PropertyDoSchemeInfo) newObj, tblEntry,
					tblEntryTwo);
		} else if (newObj != null && oldObj != null) {
			// ��ѡ��ͬһ������ʱ��������
			PropertyDoSchemeInfo newScheme = (PropertyDoSchemeInfo) (newObj);
			PropertyDoSchemeInfo oldScheme = (PropertyDoSchemeInfo) (oldObj);
			if (!newScheme.getId().equals(oldScheme.getId())) {
				loadPropertyScheme((PropertyDoSchemeInfo) newObj, tblEntry, tblEntryTwo);
			}
		}
	}

	/**
	 * �������̣���ȡ��ǰ���̵�Ӧ�������
	 * @param appEntryCol
	 * @param currentAppInfo
	 * @return
	 */
	private Date getApproachPromiseDate(PropertyDoSchemeEntryCollection appEntryCol, 
			PropertyDoSchemeEntryInfo currentAppInfo){
		if(currentAppInfo.getReferenceTime() == null){
			return null;
		}
		else if(currentAppInfo.getReferenceTime().equals("ָ������")){  //��������Ϊָ������
			return currentAppInfo.getScheduledDate();
		}
		else if(currentAppInfo.getReferenceTime().equals("ǩԼ����")){  //��������ΪǩԼ����
			if(this.editData.getSign()==null){
				return null;
			}
			else{
				return this.editData.getSign().getBizDate();
			}
		}
		else{
			for(int i=0; i<appEntryCol.size(); i++){
				PropertyDoSchemeEntryInfo appInfo = appEntryCol.get(i);
				if(appInfo.getName().equals(currentAppInfo.getReferenceTime())){
					Date tempDate = getApproachPromiseDate(appEntryCol, appInfo);
					if(tempDate != null){
						//���ݼ���º����������
						int mon = currentAppInfo.getIntervalMonth();
						int day = currentAppInfo.getIntervalDays();
						return DateTimeUtils.addDuration(tempDate, 0, mon, day);
					}
				}
			}
			return null;
		}
	}
	protected void loadPropertyScheme(PropertyDoSchemeInfo schemeInfo,
			KDTable stepTable, KDTable matTable) throws EASBizException,
			BOSException {
		IObjectPK pk = new ObjectUuidPK(schemeInfo.getId().toString());

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("Entry.*");
		sic.add("EntryTwo.*");
		PropertyDoSchemeInfo schemeFullInfo = PropertyDoSchemeFactory
				.getRemoteInstance().getPropertyDoSchemeInfo(pk, sic);
		PropertyDoSchemeEntryCollection stepCols = schemeFullInfo.getEntry();
		PropertyDoSchemeEntryTwoCollection matCols = schemeFullInfo
				.getEntryTwo();

		if (stepTable.getRowCount() > 0) {
			stepTable.removeRows();
		}
		for (int i = 0; i < stepCols.size(); i++) {
			PropertyDoSchemeEntryInfo stepInfo = stepCols.get(i);
			IRow row = stepTable.addRow(i);
			RoomPropertyBookEntryInfo bookEntry = new RoomPropertyBookEntryInfo();
			bookEntry.setName(stepInfo.getName());
			bookEntry.setPromiseFinishDate(this.getApproachPromiseDate(stepCols, stepInfo));
			bookEntry.setIsFinish(false);
			bookEntry.setProcessDate(new Date());
			bookEntry.setDescription(stepInfo.getDescription());
			bookEntry.setIsFinishFlag(stepInfo.isIsFinish());
			
			row.setUserObject(bookEntry);
			
			row.getCell("name").setValue(stepInfo.getName());
			row.getCell("promFinishDate").setValue(this.getApproachPromiseDate(stepCols, stepInfo));
			row.getCell("isFinish").setValue(Boolean.valueOf(false));
			row.getCell("processDate").setValue(new Date());
			row.getCell("description").setValue(stepInfo.getDescription());
			row.getCell("isFinishFlag").setValue(new Boolean(stepInfo.isIsFinish()));
		}

		if (matTable.getRowCount() > 0) {
			matTable.removeRows();
		}

		for (int i = 0; i < matCols.size(); i++) {
			PropertyDoSchemeEntryTwoInfo matInfo = matCols.get(i);
			IRow row = matTable.addRow(i);
			row.getCell("name").setValue(matInfo.getName());
			row.getCell("isFinish").setValue(Boolean.valueOf(false));
			row.getCell("processDate").setValue(new Date());
			row.getCell("description").setValue(matInfo.getDescription());
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getPropState().equals(AFMortgagedStateEnum.STOPTRANSACT)){
			FDCMsgBox.showInfo("��ǰ״̬�µĵ��ݲ����޸�");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
		
		this.prmtScheme.setEnabled(true);
		this.checkBox.setEnabled(false);
		this.pkActFinishDate.setEnabled(false);
		
		// ��ʼ���Ƿ�������
		if (RoomBookStateEnum.BOOKED.equals(room.getRoomBookState())) {
			checkBox.setSelected(true);
			pkActFinishDate.setEnabled(true);
			combPropertyState.setEnabled(true);
			if(combPropertyState.getSelectedItem()!=null 
					&& this.combPropertyState.getSelectedItem().toString().equals(PropertyStateEnum.BANKMORTGAGE_VALUE)){
				this.contMortgageDate.setEnabled(true);
				this.contMortgageBank.setEnabled(true);
			}
			else{
				this.contMortgageDate.setEnabled(false);
				this.contMortgageBank.setEnabled(false);
			}
			tblEntry.getColumn("isFinish").getStyleAttributes().setLocked(true);
		} else {
			checkBox.setEnabled(false);
			checkBox.setSelected(false);
			pkActFinishDate.setEnabled(false);
			combPropertyState.setEnabled(false);
			contMortgageBank.setEnabled(false);
			contMortgageDate.setEnabled(false);
			
			tblEntry.getColumn("isFinish").getStyleAttributes().setLocked(false);
		}
	}
	
	protected void initOldData(IObjectValue dataObject) {
		if (OprtState.ADDNEW.equals(this.getOprtState())) {
			// super.initOldData(dataObject);
		} else {
			if (!this.isOld) {
				super.initOldData(dataObject);
			}
		}
	}
}