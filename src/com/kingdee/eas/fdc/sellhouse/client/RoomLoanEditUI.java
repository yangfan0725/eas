/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.assistant.BankFactory;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMmmTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedFactory;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.IAFMortgaged;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.LoanNotarizeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class RoomLoanEditUI extends AbstractRoomLoanEditUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomLoanEditUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	
	/**
	 * �Ƿ����ڳ�ʼ��
	 */
	private boolean isOnload = true;
	
	/**
	 * Ĭ�ϰ��ҷ�������;��ʱδ֪	
	 */
	private AFMortgagedInfo afmInfo = null;

	public RoomLoanEditUI() throws Exception {
		super();
	}

	public void storeFields() {
		if (txtLoanFixedYear.getIntegerValue() == null)
			editData.setLoanFixedYear(new RoomLoanInfo().getLoanFixedYear());
		if ("toDo".equals(getUIContext().get("toDo"))) {
			this.editData.setIsEditData(isEditDataToDo());
		} else {
			this.editData.setIsEditData(isEditDataDone());
		}
		super.storeFields();
	}

	protected IObjectValue createNewData() {
		RoomLoanInfo objectValue = new RoomLoanInfo();
		String roomId = getUIContext().get("roomId").toString();
		
		if(this.editData.getPurchase() != null){  //�Ϲ�
			PurchaseManageInfo purchase = this.editData.getPurchase();
			objectValue.setRoom(purchase.getRoom());
			objectValue.setLoanData(purchase.getPayType().getLoanLoanData());
			objectValue.setPurchase(purchase);
			String moneyD = "";
			if (getUIContext().containsKey("mmTypeId")) {  //����id
				moneyD = getUIContext().get("mmTypeId").toString();
			}
			for (int i = 0; i < purchase.getPurPayListEntry().size(); i++) {  //����ʵ�찴�ҽ��
				if (purchase.getPurPayListEntry().get(i).getMoneyDefine().getId().toString().equals(moneyD)) {
					objectValue.setActualLoanAmt(purchase.getPurPayListEntry().get(i).getActRevAmount());
				}
			}
			if (purchase.getPayType().getAfLoanData() != null){  //��ȡ������еİ�������
				objectValue.setFundBank(purchase.getPayType().getAfLoanData());
			}
			else {
				/*txtFundFixedYear.setEnabled(false);
				txtFundAmt.setEnabled(false);
				txtFundLawyer.setEnabled(false);
				pkFundGrantDate.setEnabled(true);
				pkFundProcessDate.setEnabled(false);
				prmtFundTransactor.setEnabled(false);
				contFundTransactor.setEnabled(false);*/
			}
		}
		else if(this.editData.getSign() != null){  //ǩԼ
			SignManageInfo sign = this.editData.getSign();
			objectValue.setRoom(sign.getRoom());
			objectValue.setLoanData(sign.getPayType().getLoanLoanData());
			objectValue.setSign(sign);
			String moneyD = "";
			if (getUIContext().containsKey("mmTypeId")) {  //����id
				moneyD = getUIContext().get("mmTypeId").toString();
			}
			for (int i = 0; i < sign.getSignPayListEntry().size(); i++) {  //����ʵ�찴�ҽ��
				if (sign.getSignPayListEntry().get(i).getMoneyDefine().getId().toString().equals(moneyD)) {
					objectValue.setActualLoanAmt(sign.getSignPayListEntry().get(i).getActRevAmount());
				}
			}
			if (sign.getPayType().getAfLoanData() != null){  //��ȡ������еİ�������
				objectValue.setFundBank(sign.getPayType().getAfLoanData());
			}
			else {
				/*txtFundFixedYear.setEnabled(false);
				txtFundAmt.setEnabled(false);
				txtFundLawyer.setEnabled(false);
				pkFundGrantDate.setEnabled(true);
				pkFundProcessDate.setEnabled(false);
				prmtFundTransactor.setEnabled(false);
				contFundTransactor.setEnabled(false);*/
			}
		}
		
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setTransactor(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setFundTransactor(SysContext.getSysContext().getCurrentUserInfo());

		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		objectValue.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		objectValue.setBookedDate(new Date());
		objectValue.setLoanIsNotarize(LoanNotarizeEnum.notNotarize);
		String biz = getUIContext().get("mmTypeId").toString();
		try {
			MoneyDefineInfo moneyInfo = MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(biz));
			if (moneyInfo != null) {
				objectValue.setMmType(moneyInfo);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomLoanFactory.getRemoteInstance();
	}

	public void loadFields() {
		super.loadFields();
		String roomId = getUIContext().get("roomId").toString();
		UIContext uiContextRoom = new UIContext(ui);
		uiContextRoom.put(UIContext.ID, roomId);
		
		//���÷��������Ϣ
		CoreUIObject plUI = null;
		try {
			plUI = (CoreUIObject) UIFactoryHelper.initUIObject(RoomBizInfoUI.class.getName(), uiContextRoom, null, "VIEW");
		} catch (UIException e) {
			logger.error(e);
		}
		sclPanel.setViewportView(plUI);
		sclPanel.setKeyBoardControl(true);
		
		if (editData == null || new Integer(editData.getLoanFixedYear()) == null || getOprtState().equals(STATUS_ADDNEW))
			txtLoanFixedYear.setValue(null);
		/*if (editData == null || new Integer(editData.getFundFixedYear()) == null || getOprtState().equals(STATUS_ADDNEW))
			txtFundFixedYear.setValue(null);*/
		if (editData != null) {
			if(editData.getORSOMortgaged() != null){
				this.getLoanAFMortgaged(editData.getORSOMortgaged().getId().toString());
			}
			if (editData.getApproach() != null) {
				if (editData.getApproach().equals("�������")) {
					kDCheckBox1.setSelected(true);
				}
			}
			else if(editData.getActualFinishDate() != null){
				kDCheckBox1.setSelected(true);
			}
		}

//		loadLoanAndAccumulationFundAmount(this.editData);
		
		if(!this.kDCheckBox1.isSelected()){
			this.pkProcessLoanDate.setValue(null);
		}
		
		
	}

	/**
	 * ����ȡ���ҵ��ݵİ��ҷ���
	 * @param AfmId
	 */
	private void getLoanAFMortgaged(String AfmId){
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add(new SelectorItemInfo("ApproachEntrys.*"));
		view.getSelector().add(new SelectorItemInfo("DataEntrys.*"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", editData.getORSOMortgaged().getId()));

		view.setFilter(filter);
		try {
			AFMortgagedInfo afmInfo = AFMortgagedFactory.getRemoteInstance().getAFMortgagedInfo(new ObjectUuidPK(editData.getORSOMortgaged().getId().toString())); 
			editData.setORSOMortgaged(afmInfo);
		} catch(BOSException e){
			logger.error(e);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ���ݿ�����Ϲ�����ǩԼ���ϵİ��ҹ����������ʵ�ʰ��ҽ��
	 * @param info
	 */
	private void loadLoanAndAccumulationFundAmount(RoomLoanInfo info) {
		if (info.getPurchase() == null && info.getSign() == null) {
			return;
		}
		if (info.getPurchase() != null) {  //�Ϲ���
			PurchaseManageInfo purchaseInfo = info.getPurchase();
			if (info.getMmType() != null && info.getMmType().getMoneyType() != null) {
				if (info.getMmType().getMoneyType().equals(MoneyTypeEnum.LoanAmount)) {  //����
					if (purchaseInfo.getLoanAmount() != null) {
						this.txtActualLoanAmt.setValue(purchaseInfo.getLoanAmount());
					}

				} else if (info.getMmType().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)) {  //������
					if (purchaseInfo.getAccFundAmount() != null) {
						this.txtActualLoanAmt.setValue(purchaseInfo.getAccFundAmount());
					}
				}
			}
		}
		else 
			
		if(info.getSign() != null){  //ǩԼ��
			SignManageInfo signInfo = info.getSign();
			if (info.getMmType() != null && info.getMmType().getMoneyType() != null) {
				if (info.getMmType().getMoneyType().equals(MoneyTypeEnum.LoanAmount)) {  //����
					if (signInfo.getLoanAmount() != null) {
						this.txtActualLoanAmt.setValue(signInfo.getLoanAmount());
					}
				} else if (info.getMmType().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)) {  //������
					if (signInfo.getAccFundAmount() != null) {
						this.txtActualLoanAmt.setValue(signInfo.getAccFundAmount());
					}
				}
			}			
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew, actionSave, actionCopy, actionPre, actionNext, 
			actionFirst, actionLast, actionCancel, actionCancelCancel, actionRemove, actionPrint, actionPrintPreview }, false);
		menuView.setVisible(false);
		menuBiz.setVisible(false);
		menuSubmitOption.setVisible(false);

		this.f7LoanBank.setEditable(false);
		this.f7Afmortgaged.setEditable(false);

		handleCodingRule();

		if("VIEW".equals(this.oprtState)){
			this.btnAddData.setEnabled(false);
			this.btnRemoveData.setEnabled(false);
		}
		else{
			this.btnAddData.setEnabled(true);
			this.btnRemoveData.setEnabled(true);
		}
		
		if (!FDCSysContext.getInstance().checkIsSHEOrg()) {
			actionEdit.setEnabled(false);
		}
//		pkFundGrantDate.setEnabled(true);
		
//		getAFMortgaged();
		
		this.afmInfo = this.editData.getORSOMortgaged();
		this.f7Afmortgaged.setDataNoNotify(this.editData.getORSOMortgaged());
		
		initEntrys();
		
		this.initF7Afmortgaged();

		kDTabbedPane1_stateChanged(null);
		
		//��ʼ����ϣ����ҷ����仯�¼���ִ����ղ���
		this.isOnload = false;
		
		this.actionAttachment.setVisible(true);
		
		this.f7Creator.setDisplayFormat("$name$");
		
		this.kDTextArea1.setMaxLength(500);
	}

	/**
	 * ��������
	 * @param e
	 * @throws Exception
	 */
	protected void f7LoanBank_dataChanged(DataChangeEvent e) throws Exception {
		super.f7LoanBank_dataChanged(e);
		
	}
	
	protected void f7Afmortgaged_dataChanged(DataChangeEvent e)
			throws Exception {
		Object newObj = e.getNewValue();
		Object oldObj = e.getOldValue();

		//���ڳ�ʼ�����ݣ������editData�ķ�¼
		if(isOnload){
			return;
		}
		
		if (newObj == null && oldObj != null) {
			this.kDTable1.removeRows();
			this.kDTable2.removeRows();
			this.editData.getAFMortgaged().clear();
			this.kDCheckBox1.setSelected(false);
			this.pkProcessLoanDate.setValue(null);
		} else if (newObj != null && oldObj == null) {
			this.loadScheme();
		} else if (newObj != null && oldObj != null) {
			// ��ѡ��ͬһ������ʱ��������
			AFMortgagedInfo newScheme = (AFMortgagedInfo) newObj;
			AFMortgagedInfo oldScheme = (AFMortgagedInfo) oldObj;
			if (!newScheme.getId().equals(oldScheme.getId())) {
				this.loadScheme();
			}
		}
	}
	
	private void loadScheme(){
		this.kDTable1.removeRows();
		this.kDTable2.removeRows();
		this.editData.getAFMortgaged().clear();
		this.kDCheckBox1.setSelected(false);
		this.pkProcessLoanDate.setValue(null);
		
		AFMortgagedInfo afmInfo = (AFMortgagedInfo)this.f7Afmortgaged.getValue();
		if(afmInfo != null){
			//������ҳǩ
			if(afmInfo.getApproachEntrys() != null && !afmInfo.getApproachEntrys().isEmpty()){
				AFMortgagedApproachEntryCollection afmAppCol = afmInfo.getApproachEntrys();
				for(int i=0; i<afmAppCol.size(); i++){
					AFMortgagedApproachEntryInfo appInfo = afmAppCol.get(i);
					IRow row = this.kDTable1.addRow();
					row.getCell("aApproach").setValue(appInfo.getName());
					row.getCell("aFinish").setValue(Boolean.FALSE);
					row.getCell("aDate").setValue(new Date());
					row.getCell("proFinishDate").setValue(getApproachPromiseDate(afmAppCol, appInfo));
					row.getCell("aRemark").setValue(appInfo.getRemark());
					row.getCell("isFinishFlag").setValue(new Boolean(appInfo.isIsFinish()));
					row.getCell("aOrb").setValue(Boolean.TRUE);
				}
			}
			//�������ҳǩ
			if(afmInfo.getDataEntrys() != null && !afmInfo.getDataEntrys().isEmpty()){
				AFMortgagedDataEntryCollection afmDataCol = afmInfo.getDataEntrys();
				for(int i=0; i<afmDataCol.size(); i++){
					AFMortgagedDataEntryInfo dataInfo = afmDataCol.get(i);
					IRow row = this.kDTable2.addRow();
					row.getCell("bApproach").setValue(dataInfo.getName());
					row.getCell("bDate").setValue(new Date());
					row.getCell("bFinish").setValue(Boolean.FALSE);
					row.getCell("bRemark").setValue(dataInfo.getRemark());
					row.getCell("aOrb").setValue(Boolean.FALSE);
				}
			}
		}
	}
	
	/**
	 * ��ʼ�����ҷ���
	 * @throws EASBizException
	 * @throws BOSException
	 * @author liang_ren969
	 */
	private void initF7Afmortgaged() throws EASBizException, BOSException{
		
		if(this.editData.getSign()==null) return;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		if(this.editData.getMmType().getMoneyType() != null){
			if(this.editData.getMmType().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
				filter.getFilterItems().add(new FilterItemInfo("mmType", AFMmmTypeEnum.AccFundAmount.getValue()));
			}
			else if(this.editData.getMmType().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
				filter.getFilterItems().add(new FilterItemInfo("mmType", AFMmmTypeEnum.LoanAmount.getValue()));
			}
		}
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		
		Set id=new HashSet();
		
		id = SHEManageHelper.getAllParentSellProjectCollection(this.editData.getSign().getSellProject(),id);
	
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

		this.f7Afmortgaged.setEntityViewInfo(view);
	}
	
	private Date getApproachPromiseDate(AFMortgagedApproachEntryCollection afmAppCol, 
			AFMortgagedApproachEntryInfo currentAppInfo){
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
				int mon = currentAppInfo.getIntervalMonth();
				int day = currentAppInfo.getIntervalDays();
				return DateTimeUtils.addDuration(this.editData.getSign().getBizDate(), 0, mon, day);
			}
		}
		else{
			for(int i=0; i<afmAppCol.size(); i++){
				AFMortgagedApproachEntryInfo appInfo = afmAppCol.get(i);
				if(appInfo.getName().equals(currentAppInfo.getReferenceTime())){
					Date tempDate = getApproachPromiseDate(afmAppCol, appInfo);
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

	/**
	 * ����Ĭ�ϰ��ҷ�������ȡָ����Ŀ��ָ�������һ�����ҷ���
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public AFMortgagedInfo getAFMortgaged() throws BOSException,
			EASBizException {
		String roomID = getUIContext().get("roomId").toString();
		
		//��ȡ��Ŀ
		IRoom ir = RoomFactory.getRemoteInstance();
		SelectorItemCollection roomSic = new SelectorItemCollection();
		roomSic.add("id");
		roomSic.add("name");
		roomSic.add("number");
		roomSic.add("building.id");
		roomSic.add("building.name");
		roomSic.add("building.number");
		roomSic.add("building.sellProject.id");
		roomSic.add("building.sellProject.name");
		roomSic.add("building.sellProject.number");
		RoomInfo room = ir.getRoomInfo(new ObjectUuidPK(roomID), roomSic);
		SellProjectInfo spInfo = room.getBuilding().getSellProject();
		
		//��ȡ���ҷ���
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id", spInfo.getId()));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", "1"));

		if (getUIContext().containsKey("mmTypeId")) {
			String biz = getUIContext().get("mmTypeId").toString();
			MoneyDefineInfo moneyInfo = MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(biz));
			if (moneyInfo.getMoneyType().getValue().equals(AFMmmTypeEnum.LOANAMOUNT_VALUE)) {  //����
				filter.getFilterItems().add(new FilterItemInfo("mmType", AFMmmTypeEnum.LOANAMOUNT_VALUE));
			} 
			else {  //������
				filter.getFilterItems().add(new FilterItemInfo("mmType", AFMmmTypeEnum.ACCFUNDAMOUNT_VALUE));
			}
		}

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("isEnabled");
		sic.add("ApproachEntrys.id");
		sic.add("ApproachEntrys.name");
		sic.add("ApproachEntrys.number");
		sic.add("ApproachEntrys.remark");
		sic.add("DataEntrys.id");
		sic.add("DataEntrys.name");
		sic.add("DataEntrys.number");
		sic.add("DataEntrys.remark");
		view.setFilter(filter);
		view.setSelector(sic);

		IAFMortgaged iafm = AFMortgagedFactory.getRemoteInstance();
		afmInfo = iafm.getAFMortgagedCollection(view).get(0);
		return afmInfo;
	}

	public void initEntrys() throws BOSException, EASBizException {
		kDTable1.checkParsed();
		kDTable1.removeRows(false);
		kDTable2.checkParsed();
		kDTable2.removeRows(false);
		
		//��ʼ������б༭����
		kDTable1.getColumn("aApproach").getStyleAttributes().setLocked(true);
		kDTable1.getColumn("transactor").getStyleAttributes().setLocked(true);
		kDTable1.getColumn("actFinishDate").getStyleAttributes().setLocked(true);
		
		KDDatePicker datePicker = new KDDatePicker();
		kDTable1.getColumn("proFinishDate").setEditor(new KDTDefaultCellEditor(datePicker));
		
		KDBizPromptBox transactorBox = new KDBizPromptBox();
		transactorBox.setEditable(false);
		transactorBox.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
		kDTable1.getColumn("transactor").setEditor(new KDTDefaultCellEditor(transactorBox));
		
		KDTextField txtApproach = new KDTextField();
		txtApproach.setMaxLength(100);
		kDTable2.getColumn("bApproach").setEditor(new KDTDefaultCellEditor(txtApproach));

		
		/**
		 * ���ư���������˵���ֶεĴ�С,��ֹ�����ݿ��ֶγ����Ĵ���
		 */
		KDTextField txtARemark = new KDTextField();
		txtARemark.setMaxLength(255);
		kDTable1.getColumn("aRemark").setEditor(new KDTDefaultCellEditor(txtARemark));
		
		KDTextField txtRemark = new KDTextField();
		txtRemark.setMaxLength(255);
		kDTable2.getColumn("bRemark").setEditor(new KDTDefaultCellEditor(txtRemark));
		
		//����ʵ���Ƿ���ѡ����̺����ϣ�����չʾ
		if (this.editData.getAFMortgaged() == null || editData.getAFMortgaged().size() == 0) {  //����ʵ����δѡ����̺�����
			/*try {
				if (afmInfo != null) {  //Ĭ�ϰ��ҷ���
					for (int i = 0; i < afmInfo.getApproachEntrys().size(); i++) {
						AFMortgagedApproachEntryInfo aae = afmInfo.getApproachEntrys().get(i);
						IRow row = kDTable1.addRow();
						row.getCell("aApproach").setValue(aae.getName());
						row.getCell("aDate").setValue(new Date());
						row.getCell("proFinishDate").setValue(getApproachPromiseDate(afmInfo.getApproachEntrys(), aae));
						row.getCell("aFinish").setValue(Boolean.FALSE);
						row.getCell("transactor").setValue(SysContext.getSysContext().getCurrentUserInfo());
						row.getCell("aRemark").setValue(aae.getRemark());
						row.getCell("aOrb").setValue(Boolean.TRUE);
					}
					for (int i = 0; i < afmInfo.getDataEntrys().size(); i++) {
						AFMortgagedDataEntryInfo ade = afmInfo.getDataEntrys().get(i);
						IRow row = kDTable2.addRow();
						row.getCell("bDate").setValue(new Date());
						row.getCell("bApproach").setValue(ade.getName());
						row.getCell("bFinish").setValue(Boolean.FALSE);
						row.getCell("bRemark").setValue(ade.getRemark());
						row.getCell("aOrb").setValue(Boolean.FALSE);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		} else {  //����ʵ����ڰ��ҽ��̺�����
			for (int i = 0; i < this.editData.getAFMortgaged().size(); i++) {
				RoomLoanAFMEntrysInfo rleInfo = this.editData.getAFMortgaged().get(i);
				if (rleInfo.isIsAOrB()) {  //A��Ϊ����
					IRow row = kDTable1.addRow();
					row.getCell("id").setValue(rleInfo.getId());
					row.getCell("aDate").setValue(rleInfo.getDate());
					row.getCell("aApproach").setValue(rleInfo.getApproach());
					row.getCell("proFinishDate").setValue(rleInfo.getPromiseFinishDate());
					row.getCell("aFinish").setValue(Boolean.valueOf(rleInfo.isIsFinish()));
					row.getCell("actFinishDate").setValue(rleInfo.getActualFinishDate());
					row.getCell("transactor").setValue(rleInfo.getTransactor());
					row.getCell("aRemark").setValue(rleInfo.getRemark());
					row.getCell("isFinishFlag").setValue(Boolean.valueOf(rleInfo.isIsFinishFlag()));
					row.getCell("aOrb").setValue(Boolean.valueOf(rleInfo.isIsAOrB()));
					
					if(rleInfo.isIsFinish()){
						row.getCell("actFinishDate").getStyleAttributes().setLocked(false);
						row.getCell("transactor").getStyleAttributes().setLocked(false);
					}
				} else {  //BΪ����
					IRow row = kDTable2.addRow();
					row.getCell("id").setValue(rleInfo.getId());
					row.getCell("bDate").setValue(rleInfo.getActualFinishDate());
					row.getCell("bApproach").setValue(rleInfo.getApproach());
					row.getCell("bFinish").setValue(Boolean.valueOf(rleInfo.isIsFinish()));
					row.getCell("bRemark").setValue(rleInfo.getRemark());
					row.getCell("aOrb").setValue(Boolean.valueOf(rleInfo.isIsAOrB()));
				}
			}
		}
	}

    protected void btnAddData_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddData_actionPerformed(e);
		IRow row = kDTable2.addRow(kDTable2.getRowCount());
		row.getCell("bDate").setValue(new Date());
		row.getCell("bFinish").setValue(Boolean.FALSE);
		row.getCell("aOrb").setValue(Boolean.FALSE);
	}
	
	protected void btnRemoveData_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnRemoveData_actionPerformed(e);
		checkSelected();
		if(kDTable2.getRowCount() == 0){
			MsgBox.showInfo("��û�����Ͽ�ɾ����");
			return;
		}
		IRow row = KDTableUtil.getSelectedRow(kDTable2);
		if(((Boolean)row.getCell("bFinish").getValue()).booleanValue()){
			if(MsgBox.showConfirm2(this,"������������ɣ���ȷ��Ҫȥ������������") == MsgBox.CANCEL){
				return;
			}
		}else{
			if(MsgBox.showConfirm2(this,"��ȷ��Ҫȥ������������") == MsgBox.CANCEL){
				return;
			}
		}
		kDTable2.removeRow(row.getRowIndex());
	}
	public void checkSelected() {
		if (kDTable2.getRowCount() == 0 || kDTable2.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	protected void kDTabbedPane1_stateChanged(ChangeEvent e) throws Exception {
		super.kDTabbedPane1_stateChanged(e);
		if (kDTabbedPane1.getSelectedIndex() == 1 && !getOprtState().equals("VIEW")) {
			btnAddData.setEnabled(true);
			btnRemoveData.setEnabled(true);
		}
		else {
			btnAddData.setEnabled(false);
			btnRemoveData.setEnabled(false);
		}
	}

	/** 
	 * ����ҳǩ
	 */
	protected void kDTable1_editStopped(KDTEditEvent e) throws Exception {
		IRow row = KDTableUtil.getSelectedRow(kDTable1);
		
		if (row != null && "aFinish".equals(this.kDTable1.getColumnKey(e.getColIndex()))) {
			Object af = row.getCell("aFinish").getValue();
			if (af.equals(Boolean.TRUE)) {
				row.getCell("aDate").setValue(new Date());
				row.getCell("actFinishDate").setValue(new Date());
				row.getCell("transactor").setValue(SysContext.getSysContext().getCurrentUserInfo());
				row.getCell("actFinishDate").getStyleAttributes().setLocked(false);
				row.getCell("transactor").getStyleAttributes().setLocked(false);
				
				//��ǰ����Ϊ������ɽ��̣���ѡ�Ƿ����ʱ���轫������Ϊ�������
				if(row.getCell("isFinishFlag").getValue()!=null && ((Boolean)row.getCell("isFinishFlag").getValue()).booleanValue()){
					kDCheckBox1.setSelected(true);
				}
			} else {
				row.getCell("aDate").setValue(null);
				row.getCell("actFinishDate").setValue(null);
				row.getCell("transactor").setValue(null);
				
				row.getCell("actFinishDate").getStyleAttributes().setLocked(true);
				row.getCell("transactor").getStyleAttributes().setLocked(true);
				
				//��ǰ����Ϊ������ɽ��̣���ȡ����ѡ�Ƿ����ʱ���轫������Ϊδ���
				if(row.getCell("isFinishFlag").getValue()!=null && ((Boolean)row.getCell("isFinishFlag").getValue()).booleanValue()){
					kDCheckBox1.setSelected(false);
				}
			}
		}
	}

	/** 
	 * ����ҳǩ
	 */
	protected void kDTable2_editStopped(KDTEditEvent e) throws Exception {
		IRow row = KDTableUtil.getSelectedRow(kDTable2);
		if (row != null) {
			Object af = row.getCell("bFinish").getValue();
			if (af.equals(Boolean.TRUE)) {
				if(row.getCell("bDate").getValue() == null){
					row.getCell("bDate").setValue(new Date());
				}
			}
			else{
				row.getCell("bDate").setValue(null);
			}
		}
	}

	protected void kDTable2_tableClicked(KDTMouseEvent e) throws Exception {
		// IRow row = KDTableUtil.getSelectedRow(kDTable2);
		// if(((Boolean)row.getCell("bFinish").getValue()).booleanValue()){
		// this.btnRemoveData.setEnabled(false);
		// }else{
		// this.btnRemoveData.setEnabled(true);
		// }
	}

	/**
	 * ������ɹ�ѡ��
	 */
	protected void kDCheckBox1_stateChanged(ChangeEvent e) throws Exception {
		if (kDCheckBox1.isSelected()) {
			/*for (int i = 0; i < kDTable1.getRowCount(); i++) {
				if (kDTable1.getRow(i).getCell("aFinish").getValue() != null) {
					String isFinish = kDTable1.getRow(i).getCell("aFinish")
							.getValue().toString();
					if (isFinish.equals("0") || isFinish.equals("false")) {
						kDCheckBox1.setSelected(false);
						MsgBox.showWarning(this, "����δȫ����ɣ��޷�ѡ�������ɣ�");
						return;
					}
				}

			}
			for (int i = 0; i < kDTable2.getRowCount(); i++) {
				if (kDTable2.getRow(i).getCell("bFinish").getValue() != null) {
					String isFinish = kDTable2.getRow(i).getCell("bFinish")
							.getValue().toString();
					if (isFinish.equals("0") || isFinish.equals("false")) {
						kDCheckBox1.setSelected(false);
						MsgBox.showWarning(this, "����δȫ����ɣ��޷�ѡ�������ɣ�");
						return;
					}
				}
			}*/
			// ������ѵ�ǰ����д������������
			Date date = new Date();
			this.editData.setProcessLoanDate(date);
			this.editData.setActualFinishDate(date);
			this.pkProcessLoanDate.setValue(date);
			this.pkProcessLoanDate.setEnabled(true);
		} else {
			this.editData.setActualFinishDate(null);
			this.editData.setProcessLoanDate(null);
			this.pkProcessLoanDate.setValue(null);
			this.pkProcessLoanDate.setEnabled(false);
		}
	}

	public void onShow() throws Exception {
		super.onShow();
		FDCClientHelper.formatKDFormattedTextField(new KDFormattedTextField[] {txtActualLoanAmt });
		FDCClientHelper.formatKDFormattedINT(new KDFormattedTextField[] {txtLoanFixedYear });
		txtLoanFixedYear.setMaximumValue(new Integer("100"));
		txtActualLoanAmt.setMaximumValue(new BigDecimal("1000000000"));
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyUIControlEmpty(this);
		
//		if(this.kDTextArea1.getText()!=null && this.kDTextArea1.getText().length()>80){
//			FDCMsgBox.showInfo(this, "˵�����Ȳ��ܳ���80���ַ�");
//			this.kDTextArea1.requestFocus();
//			SysUtil.abort();
//		}
		if (kDTable1.getRowCount() == 0) {
			MsgBox.showInfo(this, "����Ŀδ���ù�����/���ҷ������޷����棡");
			SysUtil.abort();
		}
		//�����̷�¼
		for(int i=0; i<kDTable1.getRowCount(); i++){
			IRow row = kDTable1.getRow(i);
			if(row.getCell("proFinishDate").getValue()==null){
				FDCMsgBox.showInfo(this, "����д��" + (i+1) + "�н��̵�Ӧ�������");
				SysUtil.abort();
			}
			if(((Boolean)row.getCell("aFinish").getValue()).booleanValue() && row.getCell("transactor").getValue() == null){
				FDCMsgBox.showInfo(this, "����д��" + (i+1) + "�н��̵ľ�����");
				SysUtil.abort();
			}
		}
		
		for (int i = 0; i < kDTable2.getRowCount(); i++) {
			if (kDTable2.getRow(i).getCell("bApproach").getValue() == null) {
				MsgBox.showInfo(this, "����д�����ϣ�");
				abort();
			}
		}
		if (this.kDCheckBox1.isSelected()) {
			if (this.pkProcessLoanDate.getValue() == null) {
				FDCMsgBox.showInfo("��������ɣ�����д����������ڣ�");
				abort();
			}
		}
		super.verifyInput(e);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selector = super.getSelectors();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("room.*"));
		selector.add(new SelectorItemInfo("sign.*"));
		selector.add(new SelectorItemInfo("sign.sellProject.id"));
		selector.add(new SelectorItemInfo("sign.sellProject.name"));
		selector.add(new SelectorItemInfo("sign.sellProject.number"));
		selector.add(new SelectorItemInfo("approach"));
		selector.add(new SelectorItemInfo("aFMortgaged.*"));
		selector.add(new SelectorItemInfo("aFMortgaged.transactor.*"));

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
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();

		if (STATUS_ADDNEW.equals(this.oprtState) && iCodingRuleManager.isExist(editData, currentOrgId)) {
			// EditUI�ṩ�˷�������û�е��ã���onload����ã��Ը��ǳ�����loadfields����ĵ��ã��õ���û�д���Ϻ�ѡ��
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData, currentOrgId);
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
			// /CostCenterOrgUnitInfo currentCostUnit =
			// SysContext.getSysContext()
			// .getCurrentCostUnit();
			String currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (currentOrgId != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(editData, currentOrgId);

				txtNumber.setEnabled(isAllowModify);
				txtNumber.setEditable(isAllowModify);
				txtNumber.setRequired(isAllowModify);
			}
		}
	}

	/**
	 * ��鵥��״̬
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public boolean checkPurState() throws EASBizException, BOSException {
		/*if (getOprtState().equals(this.STATUS_ADDNEW)) {
			String purchaseID = getUIContext().get("purchaseID").toString();
			PurchaseInfo purInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(purchaseID));
			if (purInfo != null) {
				if (!purInfo.getPurchaseState().equals(
						PurchaseStateEnum.PurchaseAudit)) {
					MsgBox.showWarning(this, "�Ϲ���δ���������ܴ����Ϲ�������̣��޷��������ң������������������������!");
					return false;
				}
			}
		}*/
		return true;
	}

	//�Ϲ����������޸ģ�����
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getAFMortgagedState()!=null 
				&& (this.editData.getAFMortgagedState().equals(AFMortgagedStateEnum.STOPTRANSACT)
				|| this.editData.getAFMortgagedState().equals(AFMortgagedStateEnum.BANKFUND))){
			FDCMsgBox.showInfo("��ǰ״̬�µĵ��ݲ����޸�");
			SysUtil.abort();
		}
		
		this.f7Afmortgaged.setDataNoNotify(this.editData.getORSOMortgaged());
		super.actionEdit_actionPerformed(e);
		
		this.onLoad();
	}

	//�Ϲ����������޸ģ�����
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (checkPurState()) {
			setOprtState(STATUS_EDIT);
			
			//���õ�ǰ����Ͱ��ҵ���״̬
			intercalateApproach();
			
			if (afmInfo != null && getUIContext().containsKey("toDo")) {
				this.editData.setORSOMortgaged(afmInfo);
			}
			if (this.editData.getRoom() == null) {
				String roomID = getUIContext().get("roomId").toString();
				IRoom ir = RoomFactory.getRemoteInstance();
				RoomInfo room = ir.getRoomInfo(new ObjectUuidPK(roomID));
				editData.setRoom(room);
			}
			//��������Ƿ���д
			for (int i = 0; i < kDTable2.getRowCount(); i++) {
				if (kDTable2.getRow(i).getCell("bApproach").getValue() == null) {
					MsgBox.showInfo(this, "����д�����ϣ�");
					abort();
				}
			}
			
			//������̺�����
			saveEntrysToEdit();
			
			super.actionSubmit_actionPerformed(e);
			
			this.btnAddData.setEnabled(false);
			this.btnRemoveData.setEnabled(false);
			
			//���ñ����Ľ���״̬
			if (getOprtState().equals(this.STATUS_ADDNEW)) {
				MsgBox.showWarning(this, "����ɹ���");
				this.editData = null;
				this.getUIWindow().close();

			} else if (getOprtState().equals(this.STATUS_EDIT)) {
				setOprtState(STATUS_VIEW);
				lockUIForViewStatus();
			}
		}
	}

	public boolean isModify() {
		return super.isModify();
	}

	/**
	 * ���õ�ǰ����Ͱ��ҵ���״̬
	 */
	public void intercalateApproach() {
		String ApproachStr = "";

//		BusinessTypeNameEnum bizType = BusinessTypeNameEnum.MORTGAGE;  //Ĭ�ϰ���
		String bizType = SHEManageHelper.MORTGAGE;  //Ĭ�ϰ���
		if(this.editData.getMmType()!=null && this.editData.getMmType().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
			bizType = SHEManageHelper.ACCFUND;
		}
		
		if (kDCheckBox1.isSelected()) {  //�������
			ApproachStr = "�������";
			if (editData.getAFMortgagedState() == null
					|| editData.getAFMortgagedState().equals(AFMortgagedStateEnum.UNTRANSACT)
					|| editData.getAFMortgagedState().equals(AFMortgagedStateEnum.TRANSACTING)) {
				this.editData.setAFMortgagedState(AFMortgagedStateEnum.TRANSACTED);
			}
			//��дҵ�������ж�Ӧ�ķ���״̬
			SHEManageHelper.updateTransactionOverView(null, this.editData.getRoom(), bizType,
				this.editData.getPromiseDate(), this.editData.getActualFinishDate(), false);
		} 
		else {  //ȡ��һ��Ϊ��ɵĽ�����Ϊ��ǰ����
			int finishCount = 0;
			for (int i = 0; i < kDTable1.getRowCount(); i++) {
				if (kDTable1.getRow(i).getCell("aFinish").getValue().equals(Boolean.TRUE)) {
					ApproachStr = kDTable1.getRow(i).getCell("aApproach").getValue().toString();
					finishCount++;
				}
			}
			if(finishCount == 0){  //δ����
				this.editData.setAFMortgagedState(AFMortgagedStateEnum.UNTRANSACT);
			}
			else{  //������
				this.editData.setAFMortgagedState(AFMortgagedStateEnum.TRANSACTING);
			}
			//��дҵ�������ж�Ӧ�ķ���״̬
			SHEManageHelper.updateTransactionOverView(null, this.editData.getRoom(), bizType,
				this.editData.getPromiseDate(), null, false);
		}
		for (int i = 0; i < kDTable1.getRowCount(); i++) {
			if (kDTable1.getRow(i).getCell("aFinish").getValue().equals(Boolean.TRUE)) {
				this.editData.setDepositInDate((Date) kDTable1.getRow(i).getCell("proFinishDate").getValue());
				this.editData.setDepositOutDate((Date) kDTable1.getRow(i).getCell("actFinishDate").getValue());
			}
		}
		
		//���õ�ǰ����
		this.editData.setApproach(ApproachStr);
	}

	boolean isEidt = false;

	/**
	 * ��鰴�������Ƿ��޸�
	 * @return
	 */
	public boolean isEditDataDone() {
		boolean isEditData = this.editData.isIsEditData();
		int oldCount = 0;
		if (this.editData.getAFMortgaged() != null) {
			for (int i = 0; i < this.editData.getAFMortgaged().size(); i++) {
				if (!this.editData.getAFMortgaged().get(i).isIsAOrB()) {
					oldCount++;
				}
			}
		}
		if (kDTable2.getRowCount() != oldCount && this.editData.isIsEditData() == false) {
			isEditData = true;
		}
		if (afmInfo!=null && afmInfo.getDataEntrys()!=null && kDTable2.getRowCount() == afmInfo.getDataEntrys().size()) {
			for (int i = 0; i < kDTable2.getRowCount(); i++) {
				if (!kDTable2.getRow(i).getCell("bApproach").getValue().equals(afmInfo.getDataEntrys().get(i).getName())) {
					isEditData = true;
				}
			}
		}
		isEidt = isEditData;
		return isEditData;
	}

	public boolean isEditDataToDo() {
		boolean isEditData = false;
		if(afmInfo == null){
			return false;
		}
		if (afmInfo.getDataEntrys()!=null && kDTable2.getRowCount() != afmInfo.getDataEntrys().size()) {
			isEditData = true;
		}
		if (afmInfo.getDataEntrys()!=null && kDTable2.getRowCount() == afmInfo.getDataEntrys().size()) {
			for (int i = 0; i < kDTable2.getRowCount(); i++) {
				if (!kDTable2.getRow(i).getCell("bApproach").getValue().equals(
						afmInfo.getDataEntrys().get(i).getName())) {
					isEditData = true;
				}
			}
		}
		isEidt = isEditData;
		return isEditData;
	}

	public void saveEntrysToEdit() {
		if (this.editData.getAFMortgaged() == null || editData.getAFMortgaged().size() == 0) {  //�������̺�����
			for (int i = 0; i < kDTable1.getRowCount(); i++) {  //��������
				IRow row = kDTable1.getRow(i);
				RoomLoanAFMEntrysInfo rleInfo = new RoomLoanAFMEntrysInfo();
				rleInfo.setApproach(row.getCell("aApproach").getValue().toString());
				rleInfo.setDate((Date) row.getCell("aDate").getValue());
				rleInfo.setPromiseFinishDate((Date)row.getCell("proFinishDate").getValue());
				rleInfo.setIsFinish(((Boolean) row.getCell("aFinish").getValue()).booleanValue());
				rleInfo.setActualFinishDate((Date)row.getCell("actFinishDate").getValue());
				rleInfo.setTransactor((UserInfo)row.getCell("transactor").getValue());
				if (row.getCell("aRemark").getValue() != null) {
					rleInfo.setRemark(row.getCell("aRemark").getValue().toString());
				}
				rleInfo.setIsFinishFlag(((Boolean) row.getCell("isFinishFlag").getValue()).booleanValue());
				rleInfo.setIsAOrB(((Boolean) row.getCell("aOrb").getValue()).booleanValue());
				this.editData.getAFMortgaged().add(rleInfo);
			}
			if (isEditDataToDo()) {
				for (int i = 0; i < kDTable2.getRowCount(); i++) {
					IRow row = kDTable2.getRow(i);
					RoomLoanAFMEntrysInfo rleInfo = new RoomLoanAFMEntrysInfo();
					rleInfo.setActualFinishDate((Date) row.getCell("bDate").getValue());
					rleInfo.setDate((Date) row.getCell("bDate").getValue());
					rleInfo.setIsFinish(((Boolean) row.getCell("bFinish").getValue()).booleanValue());
					rleInfo.setApproach(row.getCell("bApproach").getValue().toString());
					if (row.getCell("bRemark").getValue() != null) {
						rleInfo.setRemark(row.getCell("bRemark").getValue().toString());
					}
					rleInfo.setIsAOrB(false);
					this.editData.getAFMortgaged().add(rleInfo);
				}
				this.editData.setIsEditData(true);
			} else {
				for (int i = 0; i < kDTable2.getRowCount(); i++) {
					IRow row = kDTable2.getRow(i);
					RoomLoanAFMEntrysInfo rleInfo = new RoomLoanAFMEntrysInfo();
					rleInfo.setDate((Date) row.getCell("bDate").getValue());
					rleInfo.setIsFinish(((Boolean) row.getCell("bFinish").getValue()).booleanValue());
					rleInfo.setApproach(row.getCell("bApproach").getValue().toString());
					if (row.getCell("bRemark").getValue() != null) {
						rleInfo.setRemark(row.getCell("bRemark").getValue().toString());
					}
					rleInfo.setIsAOrB(((Boolean) row.getCell("aOrb").getValue()).booleanValue());
					this.editData.getAFMortgaged().add(rleInfo);
				}
			}
		} else {  //�޸Ľ��̺�����
			if (this.editData.getAFMortgaged().get(0).getId() != null) {
				RoomLoanAFMEntrysCollection rleC = this.editData.getAFMortgaged();
				for (int i = 0; i < kDTable1.getRowCount(); i++) {
					IRow row = kDTable1.getRow(i);
					String id = null;
					if(row.getCell("id").getValue() != null){
						id = row.getCell("id").getValue().toString();
					}
						
					if(id != null){  //���½���
						for (int j = 0; j < rleC.size(); j++) {
							RoomLoanAFMEntrysInfo rlInfo = rleC.get(j);
							if (rlInfo != null && rlInfo.getId() != null && id.equals(rlInfo.getId().toString())) {
								rlInfo.setPromiseFinishDate((Date)row.getCell("proFinishDate").getValue());
								rlInfo.setDate((Date) row.getCell("aDate").getValue());
								rlInfo.setIsFinish(((Boolean) row.getCell("aFinish").getValue()).booleanValue());
								rlInfo.setActualFinishDate((Date)row.getCell("actFinishDate").getValue());
								if (row.getCell("aRemark").getValue() != null) {
									rlInfo.setRemark(row.getCell("aRemark").getValue().toString());
								}
								rlInfo.setIsFinishFlag(((Boolean) row.getCell("isFinishFlag").getValue()).booleanValue());
								rlInfo.setTransactor((UserInfo)row.getCell("transactor").getValue());
							}
						}
					}
					else{  //��������
						RoomLoanAFMEntrysInfo rleInfo = new RoomLoanAFMEntrysInfo();
						rleInfo.setApproach(row.getCell("aApproach").getValue().toString());
						rleInfo.setDate((Date) row.getCell("aDate").getValue());
						rleInfo.setPromiseFinishDate((Date)row.getCell("proFinishDate").getValue());
						rleInfo.setIsFinish(((Boolean) row.getCell("aFinish").getValue()).booleanValue());
						rleInfo.setActualFinishDate((Date)row.getCell("actFinishDate").getValue());
						rleInfo.setTransactor((UserInfo)row.getCell("transactor").getValue());
						if (row.getCell("aRemark").getValue() != null) {
							rleInfo.setRemark(row.getCell("aRemark").getValue().toString());
						}
						rleInfo.setIsFinishFlag(((Boolean) row.getCell("isFinishFlag").getValue()).booleanValue());
						rleInfo.setIsAOrB(((Boolean) row.getCell("aOrb").getValue()).booleanValue());
						this.editData.getAFMortgaged().add(rleInfo);
					}
				}
				if (isEditDataDone()) {  //�������������Ʊ��޸ĵ����
					for (int i = 0; i < rleC.size(); i++) {  //��ɾ��ԭ�еİ�������
						if (!rleC.get(i).isIsAOrB()) {
							rleC.removeObject(i);
							--i;
						}
					}
					for (int i = 0; i < kDTable2.getRowCount(); i++) {  //����������
						IRow row = kDTable2.getRow(i);
						RoomLoanAFMEntrysInfo rleInfo = new RoomLoanAFMEntrysInfo();
						rleInfo.setDate((Date) row.getCell("bDate").getValue());
						rleInfo.setIsFinish(((Boolean) row.getCell("bFinish").getValue()).booleanValue());
						rleInfo.setApproach(row.getCell("bApproach").getValue().toString());
						if (row.getCell("bRemark").getValue() != null) {
							rleInfo.setRemark(row.getCell("bRemark").getValue().toString());
						}
						rleInfo.setIsAOrB(false);
						this.editData.getAFMortgaged().add(rleInfo);
					}
					this.editData.setIsEditData(true);
				} else {  //�����������Ʋ�������
					for (int i = 0; i < kDTable2.getRowCount(); i++) {  //ֱ�Ӹ�������
						IRow row = kDTable2.getRow(i);
						String id = row.getCell("id").getValue().toString();
						for (int j = 0; j < rleC.size(); j++) {
							RoomLoanAFMEntrysInfo rlInfo = rleC.get(j);
							if (id.equals(rlInfo.getId().toString())) {
								rlInfo.setDate((Date) row.getCell("bDate").getValue());
								rlInfo.setIsFinish(((Boolean) row.getCell("bFinish").getValue()).booleanValue());
							}
						}
					}
				}
			}
		}
	}
}