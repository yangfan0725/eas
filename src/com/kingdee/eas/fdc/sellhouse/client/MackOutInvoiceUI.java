/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCStringHelper;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade;
import com.kingdee.eas.fdc.sellhouse.InvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiptInvoiceFacadeFactory;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MackOutInvoiceUI extends AbstractMackOutInvoiceUI {
	private static final Logger logger = CoreUIObject
			.getLogger(MackOutInvoiceUI.class);

	// Ʊ��ID
	String invID = null;
	ChequeTypeEnum invType =null;
	MarketDisplaySetting setting = new MarketDisplaySetting();

	/**
	 * output class constructor
	 */
	public MackOutInvoiceUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();
	}

	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (getUIContext().get("invID") != null) {
			invID = (String) getUIContext().get("invID");
			setDefaultFeilds(invID);
		}
		if(getUIContext().get("invType")!=null){
			invType =(ChequeTypeEnum)getUIContext().get("invType");
		}
		actionConfirm.setEnabled(true);
		actionCancel.setEnabled(true);
		if (setting.getMarkInvoice() == 32) {
			contPrmt.setVisible(true);
			contTxt.setVisible(false);
		} else {
			contPrmt.setVisible(false);
			contTxt.setVisible(true);
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(ChequeTypeEnum.invoice.equals(invType)){
			filter.getFilterItems().add(new FilterItemInfo("chequeType", ChequeTypeEnum.INVOICE_VALUE));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("chequeType", ChequeTypeEnum.RECEIPT_VALUE));
		}
		filter.getFilterItems().add(new FilterItemInfo("status", new Integer(ChequeStatusEnum.BOOKED_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("keepOrgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId().toString()));
		view.setFilter(filter);
		prmtNewInvoice.setEntityViewInfo(view);
		SHEHelper.setTextFormat(tntAmount);
		
		this.setUITitle("����Ʊ��");
	}

	/**
	 * output actionConfirm_actionPerformed
	 */
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		verifyDate();
		if (invID != null) {
			IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory
					.getRemoteInstance();
			String oldInvoice = null;
			String chequePK = null;
			String newInvoice = null;
			String description = null;
			// ԭ��Ʊ��
			if (txtOldInvoice.getText() != null) {
				oldInvoice = txtOldInvoice.getText();
			}
			// �·�Ʊ��
			if (txtNewInvoice.getText() != null
					&& !txtNewInvoice.getText().trim().equals("")) {
				newInvoice = txtNewInvoice.getText();
			} else if (prmtNewInvoice.getValue() != null) {
				chequePK = ((ChequeInfo) prmtNewInvoice.getValue()).getId()
						.toString();
				newInvoice = ((ChequeInfo) prmtNewInvoice.getValue())
						.getNumber();
			}
			// ��ע
			if (txtDescription.getText() != null) {
				description = txtDescription.getText();
			}
			facade.updateInvoice(invID, oldInvoice, chequePK, newInvoice,description);
			if(ChequeTypeEnum.invoice.equals(invType)){
				if (FDCStringHelper.isEmpty(oldInvoice)) {
					MsgBox.showInfo("����Ʊ�ɹ�����Ʊ��Ϊ" + newInvoice);
				} else {
					MsgBox.showInfo("�ɹ�����Ʊ" + oldInvoice + "��Ϊ" + newInvoice);
				}
			}else{
				if (FDCStringHelper.isEmpty(oldInvoice)) {
					MsgBox.showInfo("���վݳɹ����վݺ�Ϊ" + newInvoice);
				} else {
					MsgBox.showInfo("�ɹ����վ�" + oldInvoice + "��Ϊ" + newInvoice);
				}
			}
			getUIWindow().close();
		}
	}

	/**
	 * ȡ��
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		getUIWindow().close();
	}

	/**
	 * У��
	 */
	private void verifyDate() {
		if ((txtNewInvoice.getText() == null || txtNewInvoice.getText().trim()
				.equals(""))
				&& prmtNewInvoice.getValue() == null) {
			MsgBox.showWarning(this, "������дƱ�ݺŲſɻ�Ʊ!");
			SysUtil.abort();
		}
	}

	/**
	 * ���տID��ȡҳ��Ĭ��ֵ
	 * 
	 * @param invID
	 * @param isPrmtSet
	 * @throws Exception
	 */
	private void setDefaultFeilds(String invID) throws Exception {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("date");
		sic.add("amount");
		sic.add("room.id");
		sic.add("room.number");
		sic.add("room.name");
		// sic.add("room.lastPurchase.id");
		sic.add("customer.id");
		sic.add("customer.number");
		sic.add("customer.name");
		InvoiceInfo invInfo = InvoiceFactory.getRemoteInstance()
				.getInvoiceInfo(new ObjectUuidPK(invID), sic);
		if (invInfo != null) {
			// ����
			prmtRoom.setValue(invInfo.getRoom());
			// �ͻ�
			prmtCustomer.setValue(invInfo.getCustomer());
			// if (invInfo.getRoom().getLastPurchase() != null) {
			// String purchaseID = invInfo.getRoom().getLastPurchase().getId()
			// .toString();
			// EntityViewInfo view = new EntityViewInfo();
			// sic = new SelectorItemCollection();
			// sic.add("id");
			// sic.add("propertyPercent");
			// sic.add("customer.id");
			// sic.add("customer.number");
			// sic.add("customer.name");
			// view.setSelector(sic);
			// FilterInfo filter = new FilterInfo();
			// filter.getFilterItems().add(
			// new FilterItemInfo("head.id", purchaseID));
			// view.setFilter(filter);
			// SorterItemInfo sort = new SorterItemInfo("propertyPercent");
			// sort.setSortType(SortType.DESCEND);
			// view.getSorter().add(sort);
			// PurchaseCustomerInfoCollection col = PurchaseCustomerInfoFactory
			// .getRemoteInstance().getPurchaseCustomerInfoCollection(
			// view);
			// if (col != null && col.size() > 0) {
			// prmtCustomer.setValue(col.get(0).getCustomer());
			// }
			// }
			// ԭ��Ʊ��
			txtOldInvoice.setText(invInfo.getNumber());
			// ԭ��Ʊ����
			pkDate.setValue(invInfo.getDate());
			// ���
			tntAmount.setValue(invInfo.getAmount());
		}
	}

	/**
	 * ���Ϲ��ͻ����ղ�Ȩ������������ (copy from PurchaseEditUI)
	 **/
	private void sortByPropertyPercent(
			PurchaseCustomerInfoCollection customerInfos) {
		if (customerInfos == null || customerInfos.size() <= 1)
			return;
		Object[] objs = customerInfos.toArray();
		Arrays.sort(objs, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				PurchaseCustomerInfoInfo tmp0 = (PurchaseCustomerInfoInfo) arg0;
				PurchaseCustomerInfoInfo tmp1 = (PurchaseCustomerInfoInfo) arg1;
				if (tmp0 == null || tmp1 == null) {
					return 0;
				}
				BigDecimal dec0 = tmp0.getPropertyPercent();
				BigDecimal dec1 = tmp1.getPropertyPercent();
				if (dec0 == null)
					dec0 = FDCHelper.ZERO;
				if (dec1 == null)
					dec1 = FDCHelper.ZERO;
				return dec1.compareTo(dec0);
			}
		});
		customerInfos.clear();

		for (int i = 0; i < objs.length; i++) {
			PurchaseCustomerInfoInfo purCustInfo = (PurchaseCustomerInfoInfo) objs[i];
			purCustInfo.setSeq(i);
			customerInfos.add(purCustInfo);
		}
	}

}