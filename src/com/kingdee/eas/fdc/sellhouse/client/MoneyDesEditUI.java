/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.SettlementTypeCollection;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SettlementTypeEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SettlementTypeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryFactory;
import com.kingdee.eas.fdc.tenancy.SincerPaylistEntrysFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fi.cas.ReceivingBillTypeFactory;
import com.kingdee.eas.fi.cas.ReceivingBillTypeInfo;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;


/**
 * output class name
 */
public class MoneyDesEditUI extends AbstractMoneyDesEditUI {

	private static final Logger logger = CoreUIObject.getLogger(MoneyDesEditUI.class);

	/**
	 * output class constructor
	 */
	public MoneyDesEditUI() throws Exception {
		super();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("*");
		selectors.add("orgUnit.*");
		selectors.add("revBillType.SourceSysType");
		selectors.add("settlementTypeEntry.*");
		selectors.add("settlementTypeEntry.settlementType.*");
		selectors.add("cognateAccountView.name");
		selectors.add("cognateAccountView.number");
		
		return selectors;
	}

	public void onLoad() throws Exception {
		this.txtName.setRequired(true);
		this.txtNumber.setRequired(true);
		this.txtNumber.setMaxLength(80);
		this.txtName.setMaxLength(80);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.chkIsMeterItem.setVisible(false);
		this.btnAdd.setEnabled(true);	
		this.btnDel.setEnabled(true);
		this.menuItemSettlementAdd.setVisible(true);
		this.menuItemSettlementDel.setVisible(true);
		this.menuItemSettlementAdd.setEnabled(true);
		this.menuItemSettlementDel.setEnabled(true);
		super.onLoad();		
		this.storeFields();
		this.initOldData(this.editData);
		if (this.getOprtState().equals(OprtState.VIEW)) {
			this.btnAdd.setEnabled(false);
			this.btnDel.setEnabled(false);
			this.menuItemSettlementDel.setEnabled(false);
			this.menuItemSettlementAdd.setEnabled(false);
		}
	}
	
	
	public EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo) {
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// -----------------ת6.0���޸�,��Ŀ����CU����,���ݲ�����֯���и���
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
		if (companyInfo.getAccountTable() != null) {
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}

	public void loadFields() {
		// ��������ʼ��Ϊ��¥��Ŀ���
		MoneySysTypeEnum sysType = ((MoneyDefineInfo) this.editData).getSysType();
		if (sysType == null)
			sysType = MoneySysTypeEnum.SalehouseSys;
		setComboMoneyTypeItems(sysType);
		
		super.loadFields();
		SHEHelper.setNumberEnabled(editData, getOprtState(), txtNumber);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}

		MoneyTypeEnum moneyType = ((MoneyDefineInfo) this.editData).getMoneyType();
		if (moneyType != null)
			this.comboMoneyType.setSelectedItem(moneyType);

		this.tblMain.removeRows();
		this.tblMain.checkParsed();
		MoneyDefineInfo info = (MoneyDefineInfo) this.editData;
		if (info != null) {
			if (info.getSettlementTypeEntry().size() > 0) {
				
				for (int i = 0; i < info.getSettlementTypeEntry().size(); i++) {
					SettlementTypeEntryInfo setInfo = info.getSettlementTypeEntry().get(i);
					if (setInfo != null) {
						IRow row = (IRow) this.tblMain.addRow();
						row.setUserObject(setInfo);
						if (setInfo.isIsBrought()) {
							row.getCell("isBrought").setValue(Boolean.TRUE);
						} else {
							row.getCell("isBrought").setValue(Boolean.FALSE);
						}
						if (setInfo.getSettlementType() != null) {
							SettlementTypeInfo setTypeInfo = (SettlementTypeInfo) setInfo.getSettlementType();
							row.getCell("settlementType").setUserObject(setTypeInfo);
							row.getCell("settlementType").setValue(setTypeInfo.getName());
						}
						row.getCell("paymentAccount").setValue(setInfo.getPaymentAccount());
					}

				}
			}
		}

	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		MoneyDefineInfo info = (MoneyDefineInfo) this.editData;
		if (info != null) {
			if (this.tblMain.getRowCount() > 0) {
				SettlementTypeEntryCollection setCol = new SettlementTypeEntryCollection();
				for (int i = 0; i < this.tblMain.getRowCount(); i++) {
					IRow row = this.tblMain.getRow(i);
					if (row != null) {
						SettlementTypeEntryInfo setInfo = new SettlementTypeEntryInfo();
						setInfo.setHead(info);
						if (Boolean.TRUE.equals(row.getCell("isBrought").getValue())) {
							setInfo.setIsBrought(Boolean.TRUE.booleanValue());
						} else {
							setInfo.setIsBrought(Boolean.FALSE.booleanValue());
						}
						SettlementTypeInfo setTypeInfo = (SettlementTypeInfo) row.getCell("settlementType").getUserObject();
						if (setTypeInfo != null) {
							setInfo.setSettlementType(setTypeInfo);
						}
						if (row.getCell("paymentAccount").getValue() != null) {
							setInfo.setPaymentAccount(row.getCell("paymentAccount").getValue().toString());
						}
						setCol.add(setInfo);
					}

				}
				info.getSettlementTypeEntry().clear();
				info.getSettlementTypeEntry().addCollection(setCol);
			}
		}
	}

	protected IObjectValue createNewData() {
		MoneyDefineInfo des = new MoneyDefineInfo();
		des.setSysType(MoneySysTypeEnum.SalehouseSys);
		des.setMoneyType(MoneyTypeEnum.HouseAmount);

		// FullOrgUnitInfo org = (FullOrgUnitInfo)
		// this.getUIContext().get("org");
		FullOrgUnitInfo org = null;
		try {
			org = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(BOSUuid.read(OrgConstants.DEF_CU_ID)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		des.setOrgUnit(org);
		des.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		des.setIsGroup(false);

//		setComboMoneyTypeItems(MoneySysTypeEnum.SalehouseSys);

		return des;
	}

	private void setComboMoneyTypeItems(MoneySysTypeEnum sysType) {
		this.comboMoneyType.removeAllItems();

		if (sysType == null)
			return;
		// (del)��ö����ɾ���� ; (hide)���ز���ʾ , (add)����
		if (sysType.equals(MoneySysTypeEnum.TenancySys)) {
			//RentAmount,DepositAmount,LateFee,ComissionC,ElseAmount,Refundment,
			// CarryForwar ,BreachFee
			// ��� ,Ѻ�� ,���ɽ� ,������ ,���� ,(hide)�˿�,(hide)��ת�� , (add)ΥԼ��
			this.comboMoneyType.addItem(MoneyTypeEnum.PreMoney);
			this.comboMoneyType.addItem(MoneyTypeEnum.RentAmount);
			this.comboMoneyType.addItem(MoneyTypeEnum.DepositAmount);
			this.comboMoneyType.addItem(MoneyTypeEnum.LateFee);
			this.comboMoneyType.addItem(MoneyTypeEnum.CommissionCharge);
			this.comboMoneyType.addItem(MoneyTypeEnum.BreachFee);
			// this.comboMoneyType.addItem(MoneyTypeEnum.Refundment);
			// this.comboMoneyType.addItem(MoneyTypeEnum.CarryForwardAmount);
			this.comboMoneyType.addItem(MoneyTypeEnum.PeriodicityAmount);
			this.comboMoneyType.addItem(MoneyTypeEnum.ElseAmount);
		} else if (sysType.equals(MoneySysTypeEnum.ManageSys)) {
			// preMoney,DepositAmount,TradeFee, ReplaceFee
			// ,WaterFee,EletricityFee,GasFee ,GarbageFee ,LateFee,ComissionC
			// ,ElseAmount,Refundment
			// Ԥ�տ� ,Ѻ�� ,(add)��Ӫ�Է���,(add)���շ���,(del)ˮ�� ,(del)���
			// ,(del)ú����,(del)���������,���ɽ� ,(hide)������ ,���� ,(hide)�˿�
			this.comboMoneyType.addItem(MoneyTypeEnum.PreMoney);
			this.comboMoneyType.addItem(MoneyTypeEnum.DepositAmount);
			this.comboMoneyType.addItem(MoneyTypeEnum.TradeFee);
			this.comboMoneyType.addItem(MoneyTypeEnum.ReplaceFee);
			// this.comboMoneyType.addItem(MoneyTypeEnum.WaterFee);
			// this.comboMoneyType.addItem(MoneyTypeEnum.EletricityFee);
			// this.comboMoneyType.addItem(MoneyTypeEnum.GasFee);
			// this.comboMoneyType.addItem(MoneyTypeEnum.GarbageFee);
			//this.comboMoneyType.addItem(MoneyTypeEnum.LateFee);
			this.comboMoneyType.addItem(MoneyTypeEnum.BreachFee);
			// this.comboMoneyType.addItem(MoneyTypeEnum.CommissionCharge);
			// this.comboMoneyType.addItem(MoneyTypeEnum.Refundment);
			this.comboMoneyType.addItem(MoneyTypeEnum.ElseAmount);
		} else if (sysType.equals(MoneySysTypeEnum.SalehouseSys)) {
			// preMoney,EarnestMoney,FistAmout,HouseAmount,LoanAmount,
			// AccFundAmount,Refundment,FitmentAmount,TaxFee , ReplaceFee
			// ,LateFee,CommissionC ,ElseAmount,CarryFor
			// Ԥ�տ� ,���� ,���� ,¥�� ,���ҿ� ,������ ,�˿� ,װ�޿� ,(hide)˰�� ,(add)���շ��� ,���ɽ� ,������
			// ,���� ,(hide)��ת��
			this.comboMoneyType.addItem(MoneyTypeEnum.PreMoney);
			this.comboMoneyType.addItem(MoneyTypeEnum.PreconcertMoney);
			this.comboMoneyType.addItem(MoneyTypeEnum.EarnestMoney);
			this.comboMoneyType.addItem(MoneyTypeEnum.FisrtAmount);
			this.comboMoneyType.addItem(MoneyTypeEnum.HouseAmount);
			this.comboMoneyType.addItem(MoneyTypeEnum.LoanAmount);
			this.comboMoneyType.addItem(MoneyTypeEnum.AccFundAmount);
			this.comboMoneyType.addItem(MoneyTypeEnum.Refundment);
			this.comboMoneyType.addItem(MoneyTypeEnum.FitmentAmount);
			this.comboMoneyType.addItem(MoneyTypeEnum.LateFee);
			// this.comboMoneyType.addItem(MoneyTypeEnum.TaxFee);
			this.comboMoneyType.addItem(MoneyTypeEnum.CompensateAmount);
			this.comboMoneyType.addItem(MoneyTypeEnum.ReplaceFee);
			this.comboMoneyType.addItem(MoneyTypeEnum.CommissionCharge);
			// this.comboMoneyType.addItem(MoneyTypeEnum.CarryForwardAmount);
			this.comboMoneyType.addItem(MoneyTypeEnum.ElseAmount);
			this.comboMoneyType.addItem(MoneyTypeEnum.SinPur);
		}

	}

	protected ICoreBase getBizInterface() throws Exception {
		return MoneyDefineFactory.getRemoteInstance();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = this.editData.getId().toString();
		String revBillTypeId = null;
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id", id));
			if (PayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ��������ʹ��,����ɾ��!");
				return;
			}
			if (SincerReceiveEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ���ԤԼ�źŸ�����ϸʹ��,����ɾ��!");
				return;
			}
			if (PrePurchasePayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ���Ԥ����������ϸʹ��,����ɾ��!");
				return;
			}
			if (PurchasePayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ����Ϲ���������ϸʹ��,����ɾ��!");
				return;
			}
			if (SignPayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ���ǩԼ��������ϸʹ��,����ɾ��!");
				return;
			}
			if (SHERevBillEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ����տʹ��,����ɾ��!");
				return;
			}
			if (TenBillOtherPayFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ������޺�ͬ����Ӧ��ʹ��,����ɾ��!");
				return;
			}
			if (TenancyRoomPayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ������޺�ͬ�տ���ϸʹ��,����ɾ��!");
				return;
			}
		}
		MoneyDefineInfo des = (MoneyDefineInfo) this.editData;
		revBillTypeId = des.getRevBillType().getId().toString();
		super.actionRemove_actionPerformed(e);
		if (revBillTypeId != null) {
			ReceivingBillTypeFactory.getRemoteInstance().delete(new ObjectUuidPK(BOSUuid.read(revBillTypeId)));
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String id = this.editData.getId().toString();
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id", id));
			if (PayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ��������ʹ��,�����޸�!");
				return;
			}
			if (SincerReceiveEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ���ԤԼ�źŸ�����ϸʹ��,�����޸�!");
				return;
			}
			if (PrePurchasePayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ���Ԥ����������ϸʹ��,�����޸�!");
				return;
			}
			if (PurPayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ����Ϲ���������ϸʹ��,�����޸�!");
				return;
			}
			if (SignPayListEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ���ǩԼ��������ϸʹ��,�����޸�!");
				return;
			}
			if (SHERevBillEntryFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ����տʹ��,�����޸�!");
				return;
			}
			this.btnAdd.setEnabled(true);
			this.btnDel.setEnabled(true);
			this.menuItemSettlementDel.setEnabled(true);
			this.menuItemSettlementAdd.setEnabled(true);
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		String number = this.txtNumber.getText();
		if (StringUtils.isEmpty(number)) {
			MsgBox.showInfo("�������¼�룡");
			return;
		}
		try {

			Integer integer = Integer.valueOf(number);
		} catch (Exception e1) {
			MsgBox.showInfo("Ϊ�������ϵͳ���ɣ����������10λ���������֣�");
			return;
		}
		String name = this.txtName.getText();
		if (StringUtils.isEmpty(name)) {
			MsgBox.showInfo("���Ʊ���¼�룡");
			return;
		}
		MoneySysTypeEnum moneySys = (MoneySysTypeEnum) this.comboSysType.getSelectedItem();
		if("ADDNEW".equals(this.oprtState))
		{
			if ((MoneySysTypeEnum.TenancySys).equals(moneySys)) {
				MoneyTypeEnum moneyType = (MoneyTypeEnum) this.comboMoneyType.getSelectedItem();
//				if (MoneyTypeEnum.RentAmount.equals(moneyType) || MoneyTypeEnum.DepositAmount.equals(moneyType)) {
				if (MoneyTypeEnum.RentAmount.equals(moneyType)) {
					EntityViewInfo view = new EntityViewInfo();

					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("moneyType", moneyType));
					filter.getFilterItems().add(new FilterItemInfo("sysType", moneySys));
					view.setFilter(filter);
					MoneyDefineCollection monDefineColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
					if (monDefineColl.size() > 0) {
						MsgBox.showInfo(moneyType + "�Ѿ����ڣ������ظ���ӣ�");
						return;
					}
				}
			}
		}
				
		String des = this.txtDescription.getText();
		MoneyDefineInfo def = (MoneyDefineInfo) this.editData;
		ReceivingBillTypeInfo type = def.getRevBillType();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", number));
		if (type != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", type.getId().toString(), CompareType.NOTEQUALS));
		}
		if (ReceivingBillTypeFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("�������տ������д��ڣ��޷�ͬ����");
			return;
		}
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", name));
		if (type != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", type.getId().toString(), CompareType.NOTEQUALS));
		}
		if (ReceivingBillTypeFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("�������տ������д��ڣ��޷�ͬ����");
			return;
		}
		int m=0;
		for(int i=0;i<this.tblMain.getRowCount();i++){
		   if(Boolean.TRUE.equals(this.tblMain.getRow(i).getCell("isBrought").getValue())){
			   m++;
		   }	
		   if(m>1){
			   MsgBox.showInfo("���㷽ʽ��¼�����������������Զ�������");
			   return;
		   }
		}
		
		if (type == null) {
			type = new ReceivingBillTypeInfo();
			type.setId(BOSUuid.create(type.getBOSType()));
			type.setSourceSysType(SourceTypeEnum.FDC);
			def.setRevBillType(type);
		}
		if(type.getSourceSysType()==null)
			type.setSourceSysType(SourceTypeEnum.FDC);
		
		type.setNumber(number);
		type.setName(name);
		type.setDescription(des);
		ReceivingBillTypeFactory.getRemoteInstance().submit(type);
		super.actionSubmit_actionPerformed(e);

	}

	protected void comboSysType_itemStateChanged(ItemEvent e) throws Exception {
		super.comboSysType_itemStateChanged(e);

		setComboMoneyTypeItems((MoneySysTypeEnum) this.comboSysType.getSelectedItem());
		if (MoneySysTypeEnum.ManageSys.equals((MoneySysTypeEnum) this.comboSysType.getSelectedItem())) {
			this.chkIsMeterItem.setVisible(true);
		} else {
			this.chkIsMeterItem.setVisible(false);
			this.chkIsMeterItem.setSelected(false);
		}

	}

	public void actionSettlementShow_actionPerformed(ActionEvent e) throws Exception {
		try {
			UIContext uiContext = new UIContext(ui);

			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SettlementTypeListUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();

			Map uiMap = uiWindow.getUIObject().getUIContext();
			if (uiMap != null) {
				SettlementTypeCollection setTypeCol = (SettlementTypeCollection) uiMap.get("setTypeCol");
				if (setTypeCol == null) {
					return;
				}
				if(setTypeCol.size()>0){
					this.tblMain.removeRows();
					this.tblMain.checkParsed();
				}
				for (int i = 0; i < setTypeCol.size(); i++) {
					SettlementTypeInfo setTypeInfo = setTypeCol.get(i);
					if (setTypeInfo != null) {
						IRow row = this.tblMain.addRow();
						row.getCell("settlementType").setUserObject(setTypeInfo);
						row.getCell("settlementType").setValue(setTypeInfo.getName());
						if (setTypeInfo.isIsDefault()) {
							row.getCell("isBrought").setValue(Boolean.TRUE);
						} else {
							row.getCell("isBrought").setValue(Boolean.FALSE);
						}

					}
				}
			}

		} catch (UIException ex) {
			handleException(ex);
		}

	}
	
	public void actionSettlementDel_actionPerformed(ActionEvent e) throws Exception {
		super.actionSettlementDel_actionPerformed(e);
		int rownums[] = KDTableUtil.getSelectedRows(this.tblMain);
		Arrays.sort(rownums);
		for (int i = rownums.length - 1; i >= 0; i--) {
			if (rownums[i] >= 0) {
//				IRow thisRow = this.tblMain.getRow(rownums[i]);
//				Object thisObject = thisRow.getUserObject();
//				if (thisObject != null)					
//				this.editData.getSettlementTypeEntry().remove((SettlementTypeEntryInfo) thisObject);
				this.tblMain.removeRow(rownums[i]);
			}

		}
	}

}