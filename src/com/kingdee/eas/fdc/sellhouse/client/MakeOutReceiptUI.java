/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCStringHelper;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade;
import com.kingdee.eas.fdc.sellhouse.ReceiptInvoiceFacadeFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MakeOutReceiptUI extends AbstractMakeOutReceiptUI {
	private static final Logger logger = CoreUIObject
			.getLogger(MakeOutReceiptUI.class);

	private MarketDisplaySetting marketSetting = new MarketDisplaySetting();

	/**
	 * output class constructor
	 */
	public MakeOutReceiptUI() throws Exception {
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
		if (getUIContext().get("recID") != null) {
			String recID = (String) getUIContext().get("recID");
			setDefaultFeilds(recID, false);
		}

		setF7ChequeFilterAsOnload();
		actionConfirm.setEnabled(true);
		actionCancel.setEnabled(true);
		if (marketSetting.getMarkInvoice() == 32) {
			lcReceiptTXT.setVisible(false);
			lcReceiptF7.setVisible(true);
		} else {
			lcReceiptTXT.setVisible(true);
			lcReceiptF7.setVisible(false);
		}
		
		txtReceipt.setRequired(true);
		prmtReceipt.setRequired(true);
		prmtReciving.setRequired(true);
		
		SHEHelper.setTextFormat(tntRecAmt);
	}

	/**
	 * ȷ�ϣ������վݺ�رմ���
	 */
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		verifyDate();
		String pk = null;
		String receiptID = null;
		String receiptNum = null;
		String oldReceiptNum = null;
		String description = null;
		if (prmtReciving.getValue() != null) {
			pk = ((FDCReceivingBillInfo) prmtReciving.getValue()).getId().toString();
		}
		if (lcReceiptTXT.isVisible()) {
			receiptNum = txtReceipt.getText();
		} else {
			ChequeInfo chequeInfo = (ChequeInfo)prmtReceipt.getValue();
			receiptID = chequeInfo.getId().toString();
			receiptNum = chequeInfo.getNumber();
		}
		
		//�վݺ��벻���ظ���
		if(FDCReceivingBillFactory.getRemoteInstance()
			.exists("where id!='"+pk+"' and receiptNumber = '"+receiptNum+"'")){
			MsgBox.showWarning(this, "�վݺ�"+receiptNum+"�Ѿ�ʹ�ù��������ظ�ʹ��!");
			SysUtil.abort();	
		}
		
		if (!txtRcpStatus.getText().equals("δ��")) {
			oldReceiptNum = txtRcpStatus.getText().substring(2);
		}
		description = txtDescription.getText();
		
		IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory.getRemoteInstance();
		facade.updateReceipt(pk, null, receiptID, receiptNum, oldReceiptNum,description);

		if (FDCStringHelper.isEmpty(oldReceiptNum)) {
			MsgBox.showInfo("���վݳɹ����վݺ�Ϊ" + receiptNum);
		} else {
			MsgBox.showInfo("�ɹ����վ�" + oldReceiptNum + "��Ϊ" + receiptNum);
		}
		getUIWindow().close();
	}

	/**
	 * ȡ��
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		getUIWindow().close();
	}

	/**
	 * output prmtReciving_dataChanged method
	 */
	protected void prmtReciving_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		if (prmtReciving.getValue() != null) {
			String recID = ((FDCReceivingBillInfo) prmtReciving.getValue()).getId().toString();
			setDefaultFeilds(recID, true);
		}
	}

	/**
	 * У��
	 */
	private void verifyDate() {
		if(this.prmtReciving.getValue()==null){
			MsgBox.showWarning(this, "������д�տ��Ųſɿ����վ�!");
			SysUtil.abort();			
		}
		
		if ((txtReceipt.getText() == null || txtReceipt.getText().trim()
				.equals(""))
				&& prmtReceipt.getValue() == null) {
			MsgBox.showWarning(this, "������д�վݺŲſɿ����վ�!");
			SysUtil.abort();
		}
	}

	/**
	 * ���տID��ȡҳ��Ĭ��ֵ
	 * 
	 * @param recID
	 * @param isPrmtSet
	 *            �Ƿ�f7ѡ���տ�仯
	 * @throws Exception
	 */
	private void setDefaultFeilds(String recID, boolean isPrmtSet)
			throws Exception {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("number");
		sic.add("name");
		sic.add("room.id");
		sic.add("room.number");
		sic.add("room.name");
		sic.add("fdcCustomers.fdcCustomer.sysCustomer.id");
		sic.add("fdcCustomers.fdcCustomer.sysCustomer.name");
		sic.add("fdcCustomers.fdcCustomer.sysCustomer.number");
		sic.add("amount");
		sic.add("receiptNumber");
		
		FDCReceivingBillInfo recInfo = FDCReceivingBillFactory.getRemoteInstance()
				.getFDCReceivingBillInfo(new ObjectUuidPK(recID), sic);
		if (recInfo != null) {
			// ����
			prmtRoom.setValue(recInfo.getRoom());
			// �ͻ�
			RevFDCCustomerEntryCollection cusCol = recInfo.getFdcCustomers();					
			if (cusCol != null && cusCol.size() > 0) {
				Object[] o = new Object[cusCol.size()];
				RevFDCCustomerEntryInfo customerEntryInfo;
				for (int i = 0; i < cusCol.size(); i++) {
					customerEntryInfo = cusCol.get(i);
					if (customerEntryInfo != null)
						o[i] = customerEntryInfo.getFdcCustomer().getSysCustomer();
				}
				prmtCustomer.setValue(o);
			}
			// �տ
			if (!isPrmtSet) {
				prmtReciving.setValue(recInfo);
			}
			// ���
			tntRecAmt.setValue(recInfo.getAmount());
			
			// �վݺ�
			// ���վ�״̬
			if (recInfo.getReceiptNumber() == null) {
				txtRcpStatus.setText("δ��");
			} else {
				txtRcpStatus.setText("�ѿ�"	+ recInfo.getReceiptNumber());
			}
		}
	}

	/**
	 * ����Ʊ�ݵĹ�������,ר��onload�¼��е���
	 */
	private void setF7ChequeFilterAsOnload() {
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("keeper.id", SysContext.getSysContext()
						.getCurrentUserInfo().getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("status", new Integer(
						ChequeStatusEnum.BOOKED_VALUE)));
		filter.getFilterItems().add(
				new FilterItemInfo("chequeType", ChequeTypeEnum.RECEIPT_VALUE));
		viewInfo.setFilter(filter);
		viewInfo.getSorter().add(new SorterItemInfo("number"));
		prmtReceipt.setEntityViewInfo(viewInfo);

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("applyRec.*");
		prmtReceipt.setSelectorCollection(sels);
	}

}