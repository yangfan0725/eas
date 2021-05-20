/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RevListInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CasSetting;
import com.kingdee.eas.fdc.sellhouse.ChangeBalanceObjectEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FeeFromTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEConstants;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.sun.xml.messaging.saaj.util.ByteInputStream;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;

/**
 * output class name
 */
public class ChangeRoomEditUI extends AbstractChangeRoomEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ChangeRoomEditUI.class);

	private PurchaseEditUI plOldUI = null;

	public ChangeRoomEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
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
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		
		this.txtNumber.setRequired(true);
		this.txtNumber.setMaxLength(40);
		//SHEHelper.setNumberEnabled(this.editData,this.getOprtState(),this.txtNumber);
		
		this.actionAttachment.setVisible(true);
		this.btnAttachment.setVisible(true);
		this.prmtTrsfAccount.setRequired(true);
		
		this.txtPreActTotalAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtPreActTotalAmount.setPrecision(2);
		this.txtPreActTotalAmount.setRemoveingZeroInDispaly(false);
		this.txtPreActTotalAmount.setRemoveingZeroInEdit(false);
		this.txtFeeAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFeeAmount.setPrecision(2);
		this.txtFeeAmount.setRemoveingZeroInDispaly(false);
		this.txtFeeAmount.setRemoveingZeroInEdit(false);		
		this.txtTransferAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtTransferAmount.setPrecision(2);
		this.txtTransferAmount.setRemoveingZeroInDispaly(false);
		this.txtTransferAmount.setRemoveingZeroInEdit(false);		

		
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		
		this.kdtChangeEntrys.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		this.kdtChangeEntrys.getStyleAttributes().setLocked(true);
		if(this.oprtState.equals(OprtState.EDIT) || this.oprtState.equals(OprtState.ADDNEW)) {
			this.kdtChangeEntrys.getColumn("canChangeAmount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
			this.kdtChangeEntrys.getColumn("canChangeAmount").getStyleAttributes().setLocked(false);
			this.kdtChangeEntrys.getColumn("feeAmount").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
			this.kdtChangeEntrys.getColumn("feeAmount").getStyleAttributes().setLocked(false);
		}
		
		//moneyDefine apAmount actPayAmount canChangeAmount
		
		BuildingInfo buildInfo = (BuildingInfo)this.getUIContext().get("Building");
		BuildingUnitInfo buildUnitInfo = (BuildingUnitInfo)this.getUIContext().get("BuildUnit");
		this.prmtOldRoom.setSelector(new FDCRoomPromptDialog(Boolean.FALSE, buildInfo, buildUnitInfo, MoneySysTypeEnum.SalehouseSys, null));
/*		//���ԡ�����������򲻿�������
		EntityViewInfo view = new EntityViewInfo();
		this.prmtOldRoom.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomQuery");
		if(buildInfo!=null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building.id",buildInfo.getId().toString()));
			if(buildUnitInfo!=null)
				filter.getFilterItems().add(new FilterItemInfo("buildUnit.id",buildUnitInfo.getId().toString()));
			view.setFilter(filter);
			this.prmtOldRoom.setEntityViewInfo(view);
		}*/
		
		//this.prmtNewRoom.setSelector(new FDCRoomPromptDialog(Boolean.FALSE, buildInfo, buildUnitInfo, MoneySysTypeEnum.SalehouseSys, null));
		this.prmtOldRoom.setReadOnly(false);
		this.prmtOldRoom.setEditable(false);
		
		if(this.oprtState.equals(OprtState.ADDNEW)) {
			RoomInfo oldRoom = (RoomInfo)this.getUIContext().get("RoomInfo");
			if(oldRoom!=null) {
				this.editData.setOldRoom(oldRoom);
				this.prmtOldRoom.setValue(oldRoom);
			}
		}
		
		setMoneyDefineAndAcount();

	}
	
	
	/**
	 * �����ѽ�����ڼ䣬����������տ�޸�
	 * */
	private void verifyBalance() {
		Date bizDate = (Date) this.pkChangeDate.getValue();
		Date balanceEndDate = null;
		RoomInfo room = (RoomInfo) this.prmtOldRoom.getValue();
		SelectorItemCollection selColl = new SelectorItemCollection();
		selColl.add("building.sellProject.id");
		try {
			room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId().toString()),selColl);
		} catch (EASBizException e) {
			handleException(e);
			e.printStackTrace();
		} catch (BOSException e) {
			handleException(e);
			e.printStackTrace();
		}	
		SellProjectInfo sellProject= room.getBuilding().getSellProject();
		if(sellProject != null){
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
		detailBuilder.appendSql("select FBalanceEndDate from T_SHE_SellProject where FID = ?");
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
	
	/**���÷��ÿ���ͻ�ƿ�Ŀ  �� ת������ת���Ŀ  �� ���ý������*/
	private void setMoneyDefineAndAcount(){
		CompanyOrgUnitInfo curCompany = SysContext.getSysContext().getCurrentFIUnit();
		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox opseelect = new AccountPromptBox(this, curCompany,view.getFilter(), false, true);
		this.prmtFeeAccount.setEntityViewInfo(view);
		this.prmtFeeAccount.setSelector(opseelect);
		this.prmtFeeAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
		
		this.prmtTrsfAccount.setEntityViewInfo(view);
		this.prmtTrsfAccount.setSelector(opseelect);
		this.prmtTrsfAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
		
		//���÷��ÿ���Ĺ��� ֻ�ܴ� ֻ�ܴ�������,�������������ѡ��
		view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.ELSEAMOUNT_VALUE));
		filter.setMaskString("#0 and (#1 or #2)");
		view.setFilter(filter);
		this.prmtFeeMonDefine.setEntityViewInfo(view);
		//ת�����, ѡ���������,ֻ�ܴ�Ԥ�տ������ѡ��
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.PREMONEY_VALUE));
		filter.setMaskString("#0 and #1");
		view.setFilter(filter);
		this.prmtTrsfMonDefine.setEntityViewInfo(view);
		
		
		RoomDisplaySetting setting = new RoomDisplaySetting();
		CasSetting casSet = setting.getCasSetting();
		if(casSet!=null) {
			if(casSet.getChangeMoneyType()!=null) {
				if(this.getOprtState().equals(OprtState.ADDNEW))
					this.prmtFeeMonDefine.setValue(casSet.getChangeMoneyType());
			}		
			//�Ƿ����������ÿ�����޸�
			if(!casSet.getIsChangeUpdate().booleanValue()) {
				this.prmtFeeMonDefine.setEnabled(false);
			}
			if(casSet.getChangeRoomMoney()!=null) {
				if(this.getOprtState().equals(OprtState.ADDNEW))
					this.prmtTrsfMonDefine.setValue(casSet.getChangeRoomMoney());
			}
			//�Ƿ�������ת�������޸�
			if(!casSet.getIsChangeRoomUpdate().booleanValue()) {
				this.prmtTrsfMonDefine.setEnabled(false);
			}
			
			if(casSet.getChangeBalance()!=null) {
				if(this.getOprtState().equals(OprtState.ADDNEW))
					this.comboFeeDealObject.setSelectedItem(casSet.getChangeBalance());
			}
			//�Ƿ����������������޸�
			if(!casSet.getIsChangeObjectUpdate().booleanValue()) {
				this.comboFeeDealObject.setEnabled(false);
			}		
		}
		
	}
	
	private EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo){
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// -----------------ת6.0���޸�,��Ŀ����CU����,���ݲ�����֯���и���
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
		if (companyInfo.getAccountTable() != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return ChangeRoomFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtChangeEntrys;
	}
	
	protected IObjectValue createNewData() {
		ChangeRoomInfo newChangInfo = new ChangeRoomInfo();
		newChangInfo.setChangeDate(new Date());
		
		newChangInfo.setOrgUnit(SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo());
		newChangInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());

		return newChangInfo;
	}
	
	private PurchaseEditUI getPurchaseNewUI(){
		if(this.kDNewScrollPane.getViewport().getComponentCount()==0)
			return null;
		else
			return (PurchaseEditUI)this.kDNewScrollPane.getViewport().getComponent(0);		
	}
	
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() && StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("�������¼�룡");
			return;
		}
		if (this.pkChangeDate.getValue() == null) {
			MsgBox.showInfo("�������ڱ���¼�룡");
			return;
		}
		if (StringUtils.isEmpty(this.txtQitReason.getText())) {
			MsgBox.showInfo("����ԭ�����¼�룡");
			return;
		}
		RoomInfo oldRoom = (RoomInfo)this.prmtOldRoom.getValue();
		if (oldRoom == null) {
			MsgBox.showInfo("ԭ�������¼�룡");
			return;
		}

		MoneyDefineInfo trsfMonDefine = (MoneyDefineInfo)this.prmtTrsfMonDefine.getValue();
		if(trsfMonDefine==null) {
			MsgBox.showInfo("ת��������¼�룡");
			return;
		}
		AccountViewInfo trsfAccount = (AccountViewInfo)this.prmtTrsfAccount.getValue();
		if(trsfAccount==null) {
			MsgBox.showInfo("ת���Ŀ����¼�룡");
			return;
		}		
		ChangeBalanceObjectEnum feeDealObj = (ChangeBalanceObjectEnum)this.comboFeeDealObject.getSelectedItem();
		if(feeDealObj==null) {
			MsgBox.showInfo("���ý���������¼�룡");
			return;
		}		
		MoneyDefineInfo feeMonDefine = (MoneyDefineInfo)this.prmtFeeMonDefine.getValue();
		if(feeMonDefine==null) {
			MsgBox.showInfo("���ÿ������¼�룡");
			return;
		}
		AccountViewInfo feeAccount = (AccountViewInfo)this.prmtFeeAccount.getValue();
		if(feeAccount==null) {
			MsgBox.showInfo("���ÿ�Ŀ����¼�룡");
			return;
		}		
		
		
		for(int i=0;i<this.kdtChangeEntrys.getRowCount();i++) {
			IRow row = this.kdtChangeEntrys.getRow(i);
			ChangeEntryInfo changeEntryInfo = (ChangeEntryInfo)row.getUserObject();
			
			BigDecimal canChangeAmount = (BigDecimal)row.getCell("canChangeAmount").getValue();
				if(canChangeAmount==null) canChangeAmount = new BigDecimal(0);
			BigDecimal feeAmount = (BigDecimal)row.getCell("feeAmount").getValue();
				if(feeAmount==null) feeAmount = new BigDecimal(0);	

			if(canChangeAmount.add(feeAmount).compareTo(changeEntryInfo.getMaxCanChangeAmount())>0) {
				MsgBox.showInfo("��¼���е�"+(i+1)+"�п�ת���ӷ��ý�����ʣ���");
				return;
			}
			if(canChangeAmount.compareTo(new BigDecimal(0))<0) {
				MsgBox.showInfo("��¼���е�"+(i+1)+"�п�ת��������ڵ���0��");
				return;
			}
			if(feeAmount.compareTo(new BigDecimal(0))<0) {
				MsgBox.showInfo("��¼���е�"+(i+1)+"�з��ý�������ڵ���0��");
				return;
			}			
		}
		
		PurchaseEditUI plNewUI = getPurchaseNewUI();
		RoomInfo newRoom = (RoomInfo)plNewUI.txtRoomNumber.getUserObject();
		if (newRoom == null) {
			MsgBox.showInfo("�·������¼�룡");
			return;
		}
		if (newRoom.equals(oldRoom.getId())) {
			MsgBox.showInfo("����ǰ�󷿼䲻����ͬ!");
			return;
		}
		
		Boolean bol = (Boolean)plNewUI.getUIContext().get("isPrePurchase");
		if(bol!=null && bol.booleanValue())
			plNewUI.verifySave();
		else
			plNewUI.verifySubmit();
		plNewUI.storeFields();
		
		//������������÷��䲻�ܴ����ύ״̬�Ļ�����
		if(this.editData.getId()==null) {
			boolean exitChangeInfo = ChangeRoomFactory.getRemoteInstance().exists("where oldRoom.id='"+oldRoom.getId().toString()+"' " +
					" and (state='"+ FDCBillStateEnum.SUBMITTED_VALUE +"' or state='"+ FDCBillStateEnum.SAVED_VALUE +"')");
			if(exitChangeInfo) {
				MsgBox.showInfo("�÷����Ѵ��ڱ�����ύ״̬�Ļ������������ظ��ύ��");
				return;
			}
		}

		this.editData.setNewRoom(newRoom);
		this.prmtNewRoom.setValue(newRoom);
		this.editData.setTempPurChaseObj(this.PurchaseInfoToByteArray((PurchaseInfo)plNewUI.getEditData()));		
		verifyBalance();
		
		super.actionSubmit_actionPerformed(e);
		
		this.btnAudit.setVisible(true);
		this.btnAudit.setEnabled(true);
		this.setOprtState(STATUS_VIEW);
	}

	
	//�رմ��ڵ���ʾ�������Ѹı䣬�Ƿ񱣴桱����storeFields()��ĸı�ֵ���й�ϵ������
	public void storeFields() {
		super.storeFields();		
		this.editData.setOldPurchase((PurchaseInfo)this.plOldUI.getEditData());
	}

	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selColl = super.getSelectors();
		selColl.add(new SelectorItemInfo("oldPurchase"));
		selColl.add(new SelectorItemInfo("newPurchase"));
		selColl.add(new SelectorItemInfo("tempPurChaseObj"));
		selColl.add(new SelectorItemInfo("orgUnit"));
		selColl.add(new SelectorItemInfo("CU"));
		selColl.add("state");
		return selColl;
	}
	
	public void loadFields() {
		this.removeDataChangeListener(this.prmtOldRoom);
		super.loadFields();
		this.addDataChangeListener(this.prmtOldRoom);		
		
		this.actionUnAudit.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		
		if(this.editData!=null){
			PurchaseInfo oldPur = this.editData.getOldPurchase();
			if(oldPur!=null)
				LoadPurchaseTabInfo(oldPur.getId().toString(),ChangeBalanceObjectEnum.OldRoomBalance,false);
			PurchaseInfo newPur = this.editData.getNewPurchase();
			if(newPur!=null)
				LoadPurchaseTabInfo(newPur.getId().toString(),ChangeBalanceObjectEnum.NewRoomBalance,false);
			else{
				if(this.editData.getTempPurChaseObj()!=null) {
					PurchaseEditUI purTempUI = null;
					try{
						UIContext uiContext = new UIContext(ui);
						uiContext.put("usedByChangRoom", Boolean.TRUE);
						BuildingInfo buildInfo = (BuildingInfo)this.getUIContext().get("Building");
						BuildingUnitInfo buildUnitInfo = (BuildingUnitInfo)this.getUIContext().get("BuildUnit");
						uiContext.put("building", buildInfo);
						uiContext.put("buildUnit", buildUnitInfo);
						purTempUI = (PurchaseEditUI) UIFactoryHelper.initUIObject(PurchaseEditUI.class.getName(), uiContext, null,OprtState.ADDNEW);
					}catch(Exception e){
						e.printStackTrace();
					}
					if(purTempUI!=null) {
						PurchaseInfo purTempInfo = this.ByteArrayToPurchaseInfo(this.editData.getTempPurChaseObj());
						if(purTempInfo!=null) {
							purTempUI.setDataObject(purTempInfo);
							purTempUI.editData = purTempInfo; 							
							//����Ҫ����ˢ���½����ϵ���ʾ���ݣ��߼����Ϲ�������ʾһ�������Ϲ����иĶ����˵�Ҫ��Ӧ�ĸĶ�
							purTempUI.loadFields();
							
							//����Ҫ����һЩ�Ϲ��������ϰ󶨵Ŀռ���ʾ ����Ϊ��������ʱ������id,�Ϲ������ǲ������ʾ�����ģ�
							if(purTempInfo.getPrePurchaseAmount()!= null && purTempInfo.getPrePurchaseAmount().compareTo(FDCHelper.ZERO)!=0) {
								purTempUI.pkPrePurchaseDate.setValue(purTempInfo.getPrePurchaseDate());
								purTempUI.f7PrePurchaseCurrency.setValue(purTempInfo.getPrePurchaseCurrency());
								purTempUI.txtPrePurchaseAmount.setValue(purTempInfo.getPrePurchaseAmount());
								purTempUI.txtPreLevelMoney.setValue(purTempInfo.getPrePurLevelAmount());
							}
							purTempUI.pkCreateDate.setValue(purTempInfo.getCreateTime());
							purTempUI.txtPurchaseAmount.setValue(purTempInfo.getPurchaseAmount());
							purTempUI.f7PurchaseCurrency.setValue(purTempInfo.getPurchaseCurrency());
							purTempUI.pkPurchaseDate.setValue(purTempInfo.getPurchaseDate());
							if(purTempInfo.getSincerityPurchase()!=null) {
								this.removeDataChangeListener(purTempUI.f7SincerityPurchase);
								purTempUI.f7SincerityPurchase.setValue(purTempInfo.getSincerityPurchase());
								this.addDataChangeListener(purTempUI.f7SincerityPurchase);
							}
							purTempUI.txtNumber.setText(purTempInfo.getNumber());
							purTempUI.txtDes.setText(purTempInfo.getDescription());
							purTempUI.comboSellType.setSelectedItem(purTempInfo.getSellType());
							purTempUI.txtPlanSignTimeLimit.setNumberValue(new Integer(purTempInfo.getPlanSignTimeLimit()));
							purTempUI.pkCreator.setValue(purTempInfo.getCreator());
						}
						this.kDNewScrollPane.setViewportView(purTempUI);
						this.kDNewScrollPane.setKeyBoardControl(true);
						this.kDNewScrollPane.setEnabled(false);
						purTempUI.btnAddCustomer.setEnabled(false);
						purTempUI.btnAddNewCustomer.setEnabled(false);
						purTempUI.btnDeleteCustomer.setEnabled(false);
					}
					

				}
			}
		}
	}

	
	protected void prmtOldRoom_dataChanged(DataChangeEvent e) throws Exception {
		RoomInfo roomInfo = (RoomInfo)e.getNewValue();
		boolean okFlag = true;
		if (roomInfo.getSellState()==null || roomInfo.getSellState().equals(RoomSellStateEnum.Init) ||	roomInfo.getSellState().equals(RoomSellStateEnum.OnShow)) {
			MsgBox.showInfo("�÷��仹δ���ۣ�");
			okFlag = false;
		}else if(roomInfo.getSellState().equals(RoomSellStateEnum.Sign)){
			MsgBox.showInfo("�÷�����ǩԼ�����ܻ�����");
			okFlag = false;
		}else if(roomInfo.getRoomJoinState()!=null && roomInfo.getRoomJoinState().equals(RoomJoinStateEnum.JOINED)){
			MsgBox.showInfo("�÷����Ѱ��������ܻ�����");
			okFlag = false;
		}else {
			RoomInfo tempRoom = RoomFactory.getRemoteInstance().getRoomInfo("select lastPurchase.purchaseState where id='"+roomInfo.getId().toString()+"'");
			if(tempRoom==null || tempRoom.getLastPurchase()==null){
				MsgBox.showInfo("�÷����Ӧ���Ϲ���״̬�ǡ��Ϲ���������Ԥ�����ˡ������ܻ�����");
				okFlag = false;
			}else{
				PurchaseStateEnum purState = tempRoom.getLastPurchase().getPurchaseState();
				if(purState==null ||(!purState.equals(PurchaseStateEnum.PrePurchaseCheck) && !purState.equals(PurchaseStateEnum.PurchaseAudit))) {
					MsgBox.showInfo("�÷����Ӧ���Ϲ���״̬�ǡ��Ϲ���������Ԥ�����ˡ������ܻ�����");
					okFlag = false;
				}
			}
		}

		if(!okFlag) {
			this.removeDataChangeListener(this.prmtOldRoom);
			this.prmtOldRoom.setValue(e.getOldValue());
			this.addDataChangeListener(this.prmtOldRoom);
			return;
		}
		
		if(roomInfo!=null && roomInfo.getLastPurchase()!=null) {
			LoadPurchaseTabInfo(roomInfo.getLastPurchase().getId().toString(),ChangeBalanceObjectEnum.OldRoomBalance,true);
		
			this.prmtNewRoom.setValue(null);
			
			//��Ӧ���µ��Ϲ�������ȫ�ÿͻ��Լ�¼
			LoadPurchaseTabInfo(null,ChangeBalanceObjectEnum.NewRoomBalance,false);
			
			addNewPurchaseCustomerInfo();
		}
	}
	
	
	private void addNewPurchaseCustomerInfo(){
		PurchaseCustomerInfoCollection customerInfos = ((PurchaseInfo)this.plOldUI.getEditData()).getCustomerInfo();
		for(int i=0;i<customerInfos.size();i++){
			customerInfos.get(i).setId(null);
			customerInfos.get(i).setHead((PurchaseInfo)this.plOldUI.getEditData());
		}
		FDCTreeHelper.sortCollection(customerInfos, "seq");   //���������	
		addNewPurchaseCustomerRow(customerInfos);	
	}
	
	
	
	/**�µ��Ϲ������棬����ʱĬ�ϴ���ԭ�ͻ�����Ϣ*/
	private void addNewPurchaseCustomerRow(PurchaseCustomerInfoCollection customerInfos) {
		if(customerInfos == null || customerInfos.size()==0){
			return;
		}

		PurchaseEditUI plNewUI = getPurchaseNewUI();
		for(int i=0;i<customerInfos.size();i++)  {	
			PurchaseCustomerInfoInfo customerInfo = customerInfos.get(i);
			IRow row = plNewUI.tblCustomerInfo.addRow();
			row.setUserObject(customerInfo);
			row.getCell("propertyPercent").setValue(customerInfo.getPropertyPercent());
			row.getCell("des").setValue(customerInfo.getDescription());
			FDCCustomerInfo customer = customerInfo.getCustomer();
			if (customer != null) {
				row.getCell("seq").setValue(customerInfo.getSeq()==0?Boolean.TRUE:Boolean.FALSE);
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
	
	
	
	protected void prmtFeeMonDefine_dataChanged(DataChangeEvent e)
			throws Exception {
		//�˴�Ҫ�ӿ����Ŀ���ձ���ȡ��Ӧ�Ŀ�Ŀ
		MoneyDefineInfo moneyDefine = (MoneyDefineInfo) this.prmtFeeMonDefine.getValue();
		if(e.getNewValue()==null) {
			this.prmtFeeAccount.setValue(null);
		}
		if(e.getNewValue()!=null &&(e.getOldValue()==null || !e.getOldValue().equals(e.getNewValue()))) {
			MoneySubjectContrastInfo contractTableInfo = CommerceHelper.getContractTableByMoneyDefine(moneyDefine);
			if(contractTableInfo!=null) {
				if(contractTableInfo.getAccountView()!=null) 
					this.prmtFeeAccount.setValue(contractTableInfo.getAccountView());
				if(!contractTableInfo.isIsChanged())
					this.prmtFeeAccount.setEnabled(false);
				else
					this.prmtFeeAccount.setEnabled(true);
			}
		}		
	}
	
	protected void prmtTrsfMonDefine_dataChanged(DataChangeEvent e)
			throws Exception {
		//�˴�Ҫ�ӿ����Ŀ���ձ���ȡ��Ӧ�Ŀ�Ŀ
		MoneyDefineInfo moneyDefine = (MoneyDefineInfo) this.prmtTrsfMonDefine.getValue();
		if(e.getNewValue()==null) {
			this.prmtTrsfAccount.setValue(null);
		}
		if(e.getNewValue()!=null &&(e.getOldValue()==null || !e.getOldValue().equals(e.getNewValue()))) {
			MoneySubjectContrastInfo contractTableInfo = CommerceHelper.getContractTableByMoneyDefine(moneyDefine);
			if(contractTableInfo!=null) {
				if(contractTableInfo.getAccountView()!=null) 
					this.prmtTrsfAccount.setValue(contractTableInfo.getAccountView());
				if(!contractTableInfo.isIsChanged())
					this.prmtTrsfAccount.setEnabled(false);
				else
					this.prmtTrsfAccount.setEnabled(true);
			}
		}		
	}
	
	
	
	/**
	 * �����Ϲ���id�Ͷ�Ӧ��tab��������Ϲ���;
	 * reflashEntry �Ƿ�Ҫˢ�·��÷�¼
	 * purIdStr ��Ϊ�� �������·���������Ϲ���
	 */
	private void LoadPurchaseTabInfo(String purIdStr,ChangeBalanceObjectEnum obj ,boolean reflashEntry){
		PurchaseEditUI purUI = null;

		UIContext uiContext = new UIContext(ui);
			if(purIdStr!=null) 
				uiContext.put(UIContext.ID, purIdStr);
			else{ //�·��������� ���ɷ���ΪԤ���ģ����·�����Ϲ�ҲӦ����Ԥ����
				if(this.plOldUI!=null) {										
					PurchaseInfo oldPurInfo = (PurchaseInfo)this.plOldUI.getEditData();
					if(oldPurInfo!=null) {
						if(oldPurInfo.getSellProject()!=null)  //���Ϲ����ı���--���ñ������ʱ����Ҫ�õ���Ŀ���룬���Ҫ��������Ŀ���󴫵ݹ�ȥ
							uiContext.put("sellProject", oldPurInfo.getSellProject());
						
						if((oldPurInfo.getPurchaseState().equals(PurchaseStateEnum.PrePurchaseApply) || oldPurInfo.getPurchaseState().equals(PurchaseStateEnum.PrePurchaseCheck)))
							uiContext.put("isPrePurchase", Boolean.TRUE);
						if(oldPurInfo.getSalesman()!=null)
							uiContext.put("salesMan", oldPurInfo.getSalesman());
					}
				}
			}
			
			try {
				purUI = (PurchaseEditUI) UIFactoryHelper.initUIObject(PurchaseEditUI.class.getName(), uiContext, null,
												purIdStr==null?OprtState.ADDNEW:OprtState.VIEW);
			} catch (Exception e) {
				handleException(e);
			}	
			
			if(obj!=null){
				if(obj.equals(ChangeBalanceObjectEnum.OldRoomBalance)) {
					this.kdOldScrollPane.setViewportView(purUI);
					this.kdOldScrollPane.setKeyBoardControl(true);
					this.kdOldScrollPane.setEnabled(false);		
					this.plOldUI = purUI;
					
					if(reflashEntry && purUI!=null && purIdStr != null) {   //ֻ��Ծɷ�����Ϲ���ˢ�»�����¼
						try{
							getChangeEntryList(purIdStr);
						} catch (Exception e) {
							handleException(e);
						}	
					}	
				}else if(obj.equals(ChangeBalanceObjectEnum.NewRoomBalance)) {
					this.kDNewScrollPane.setViewportView(purUI);
					this.kDNewScrollPane.setKeyBoardControl(true);
					this.kDNewScrollPane.setEnabled(false);
					
					//���Ϲ����еĿͻ���¼�����޸�
					if(purUI!=null){
						purUI.btnAddCustomer.setEnabled(false);
						purUI.btnAddNewCustomer.setEnabled(false);
						purUI.btnDeleteCustomer.setEnabled(false);
						purUI.btnUp.setEnabled(false);
						purUI.btnDown.setEnabled(false);
					}
				}
			}		
	}
	
	private void addANewChangeEntry(RevListInfo revInfo,FeeFromTypeEnum feeType){
		ChangeEntryInfo entryInfo = new ChangeEntryInfo();
		entryInfo.setHead(this.editData);
		entryInfo.setMoneyDefine(revInfo.getMoneyDefine());
		entryInfo.setApAmount(revInfo.getAppAmount());
		entryInfo.setActPayAmount(revInfo.getActRevAmount());
		entryInfo.setCanChangeAmount(revInfo.getAllRemainAmount());
		entryInfo.setMaxCanChangeAmount(revInfo.getAllRemainAmount());
		entryInfo.setFeeAmount(FDCHelper.ZERO);
		entryInfo.setFeeFromType(feeType);
		//entryInfo.setSeq(revInfo.getSeq());		�ᵼ�±����˳��仯
		entryInfo.setSeq(this.editData.getChangeEntrys().size());
		entryInfo.setHasRemitAmount(revInfo.getHasTransferredAmount());
		entryInfo.setPayListId(revInfo.getId().toString());
		IRow row = this.kdtChangeEntrys.addRow();
		//moneyDefine apAmount actPayAmount canChangeAmount 
		row.getCell("moneyDefine").setValue(entryInfo.getMoneyDefine());
		row.getCell("apAmount").setValue(entryInfo.getApAmount());
		row.getCell("actPayAmount").setValue(entryInfo.getActPayAmount());
		row.getCell("canChangeAmount").setValue(entryInfo.getCanChangeAmount());
		row.getCell("feeAmount").setValue(entryInfo.getFeeAmount());
		
		entryInfo.setHasRemitAmount(revInfo.getHasRefundmentAmount());
		row.getCell("hasRemitAmount").setValue(revInfo.getHasRefundmentAmount());
		
		row.setUserObject(entryInfo);
		this.editData.getChangeEntrys().add(entryInfo);
	}
	
	
	/**��ȫ�����˷��������������б仯��ͬʱ�޸�
	 * �򻻷���¼�б����������  ��Դ��A(�Ϲ���δ����)���˵�Ԥ���� B(�Ϲ���������) 1.�տӦ����ϸ 2.�տ����Ӧ�� */
	private void getChangeEntryList(String purchaseId) throws BOSException, EASBizException {
		this.kdtChangeEntrys.removeRows();
		this.editData.getChangeEntrys().clear();
		BigDecimal totalTrsfAmount = new BigDecimal(0);
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("isEarnestInHouseAmount");
		sels.add("auditTime");
		sels.add("payListEntry.*");	//Ӧ�ղ���
		sels.add("payListEntry.moneyDefine.moneyType");
		sels.add("payListEntry.moneyDefine.name");
		sels.add("elsePayListEntry.*");	//��������
		sels.add("elsePayListEntry.moneyDefine.moneyType");
		sels.add("elsePayListEntry.moneyDefine.name");			
		PurchaseInfo purchase = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(purchaseId), sels);			
		//boolean isEarnestInHouseAmount = purchase.isIsEarnestInHouseAmount();
		
		PurchasePayListEntryCollection purPayList = purchase.getPayListEntry();		//Ӧ�ղ���
		CRMHelper.sortCollection(purPayList, "seq",true);		
		for(int i=0; i<purPayList.size(); i++){
			PurchasePayListEntryInfo purPay = (PurchasePayListEntryInfo)purPayList.get(i);
			
			BigDecimal maxRefundmentAmount = purPay.getAllRemainAmount();
			totalTrsfAmount = totalTrsfAmount.add(maxRefundmentAmount);
			
			addANewChangeEntry(purPay,FeeFromTypeEnum.ShouldPayList);					
		}
		
		PurchaseElsePayListEntryCollection elsePayList = purchase.getElsePayListEntry();	//��������	
		CRMHelper.sortCollection(elsePayList, "seq",true);		
		for(int i=0; i<elsePayList.size(); i++){
			PurchaseElsePayListEntryInfo elsePay = (PurchaseElsePayListEntryInfo)elsePayList.get(i);

			BigDecimal maxRefundmentAmount = elsePay.getAllRemainAmount();
			totalTrsfAmount = totalTrsfAmount.add(maxRefundmentAmount);
			
			addANewChangeEntry(elsePay,FeeFromTypeEnum.ElsePayList);
		}
	
		
		this.txtTransferAmount.setNumberValue(totalTrsfAmount);
		this.txtFeeAmount.setNumberValue(new BigDecimal(0));
		this.txtPreActTotalAmount.setNumberValue(totalTrsfAmount);
	}
	
	
	protected void kdtChangeEntrys_editStopped(KDTEditEvent e) throws Exception {
		BigDecimal totalTrsfAmount = new BigDecimal(0);
		BigDecimal totalFeeAmount = new BigDecimal(0);
		for(int i=0;i<this.kdtChangeEntrys.getRowCount();i++) {
			IRow row = this.kdtChangeEntrys.getRow(i);
			BigDecimal actPayAmount = (BigDecimal)row.getCell("actPayAmount").getValue();
				if(actPayAmount==null) actPayAmount = new BigDecimal(0); 
			BigDecimal canChangeAmount = (BigDecimal)row.getCell("canChangeAmount").getValue();
				if(canChangeAmount==null) canChangeAmount = new BigDecimal(0);
			BigDecimal feeAmount = (BigDecimal)row.getCell("feeAmount").getValue();
				if(feeAmount==null) feeAmount = new BigDecimal(0);
			totalTrsfAmount = totalTrsfAmount.add(canChangeAmount);
			totalFeeAmount = totalFeeAmount.add(feeAmount);
		}
		this.txtTransferAmount.setNumberValue(totalTrsfAmount);
		this.txtTransferAmount.setValue(totalTrsfAmount);
		this.txtFeeAmount.setNumberValue(totalFeeAmount);
		this.txtFeeAmount.setValue(totalFeeAmount);
	}
	
	
	protected void attachListeners() {
		this.addDataChangeListener(this.prmtOldRoom);
		this.addDataChangeListener(this.prmtNewRoom);
	}

	protected void detachListeners() {
		this.removeDataChangeListener(this.prmtOldRoom);
		this.removeDataChangeListener(this.prmtNewRoom);
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	//�洢�����Ϲ���Ϣ��mapֵ
	private byte[] PurchaseInfoToByteArray(PurchaseInfo purInfo) {
		byte[] bytes = new byte[0];
		if(purInfo!=null)  {	
			try {
				ByteOutputStream stream = new ByteOutputStream();
				ObjectOutputStream s = new ObjectOutputStream(stream);
				s.writeObject(purInfo.getValueMap());
				bytes = stream.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
				SysUtil.abort();
			}
		}
		return bytes;
	}
	
	private PurchaseInfo ByteArrayToPurchaseInfo(byte[] bytes) {		
		PurchaseInfo purInfo = null;
		if(bytes!=null && bytes.length>0) {
			ByteInputStream stream = new ByteInputStream(bytes,bytes.length);
			try {
				ObjectInputStream s = new ObjectInputStream(stream);
				Map valuesMap = (Map)s.readObject();
				purInfo = new PurchaseInfo();
				purInfo.setValueMap(valuesMap);
			} catch (Exception e) {
				e.printStackTrace();
				SysUtil.abort();
			}
		}
		return purInfo;
	}
	

	
	
	
	
	
	
	
	
	
	
}