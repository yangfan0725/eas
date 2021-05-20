/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCStringHelper;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.ChequeCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade;
import com.kingdee.eas.fdc.sellhouse.InvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiptInvoiceFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class ChangeInvoiceUI extends AbstractChangeInvoiceUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeInvoiceUI.class);
    
    public ChangeInvoiceUI() throws Exception
    {
        super();
    }

	// Ʊ��ID
	ChequeTypeEnum invType =null;
	//MarketDisplaySetting setting = new MarketDisplaySetting();


	public void loadFields() {
		super.loadFields();
	}

	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (getUIContext().get("invIDs") != null) {
			setDefaultFeilds();
		}
		if(getUIContext().get("invType")!=null){
			String invTypeStr = (String)getUIContext().get("invType");
			if(invTypeStr!=null){
				if(invTypeStr.equals("�վ�"))
					invType = ChequeTypeEnum.receipt;
				else if(invTypeStr.equals("��Ʊ"))
					invType = ChequeTypeEnum.invoice;
			}
		}
		actionConfirm.setEnabled(true);
		actionCancel.setEnabled(true);

		contPrmt.setVisible(true);
		contTxt.setVisible(false);		
		
/*		if (setting.getMarkInvoice() == 32) { 		��Ʊ��ͳһ���������£���Ʊû��ʲô���尡
			contPrmt.setVisible(true);
			contTxt.setVisible(false);
		} else {
			contPrmt.setVisible(false);
			contTxt.setVisible(true);
		}*/
		
		EntityViewInfo view = new EntityViewInfo();
		
		FilterInfo filter = new FilterInfo();
		if(ChequeTypeEnum.invoice.equals(invType)){
			filter.getFilterItems().add(new FilterItemInfo("cheque.chequeType", ChequeTypeEnum.INVOICE_VALUE));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("cheque.chequeType", ChequeTypeEnum.RECEIPT_VALUE));
		}
		filter.getFilterItems().add(new FilterItemInfo("status", new Integer(ChequeStatusEnum.PICKED_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("cheque.keepOrgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId()));
		filter.getFilterItems().add(new FilterItemInfo("cheque.keeper.id", SysContext.getSysContext().getCurrentUserInfo().getId()));
		
/*		String spIdsStr = (String)getUIContext().get("SellProIdsStr");
		if(spIdsStr!=null && !spIdsStr.equals(""))*/
			
			
		view.setFilter(filter);
		prmtNewInvoice.setEntityViewInfo(view);
		prmtNewInvoice.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ChequeDetailEntryQuery");
		SHEHelper.setTextFormat(tntAmount);
		
		this.setUITitle("����Ʊ��");
	}

	/**
	 * output actionConfirm_actionPerformed
	 */
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		verifyDate();
		
		ArrayList invIDs = (ArrayList) getUIContext().get("invIDs");
		if(invIDs==null) return;		

		String  oldInvoice = null;
		String chequePK = null;
		String newInvoice = null;
		// ԭ��Ʊ��
		if (txtOldInvoice.getText() != null) {
			oldInvoice = txtOldInvoice.getText();
		}
		// �·�Ʊ��
		if (txtNewInvoice.getText() != null
				&& !txtNewInvoice.getText().trim().equals("")) {
			newInvoice = txtNewInvoice.getText();
		} else if (prmtNewInvoice.getValue() != null) {
			chequePK = ((ChequeDetailEntryInfo) prmtNewInvoice.getValue()).getId()
					.toString();
			newInvoice = ((ChequeDetailEntryInfo) prmtNewInvoice.getValue())
					.getNumber();
		}
		String desc = this.txtDescription.getText();
		//��Ʊ��ID����Ʊ��ID
		ChequeDetailEntryFactory.getRemoteInstance().changeInvoice(invIDs, chequePK,desc);
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
	private void setDefaultFeilds() throws Exception {
		ArrayList invIDs = (ArrayList) getUIContext().get("invIDs");
		if(invIDs==null || invIDs.size()==0) return;
		
		String idsStr = "";
		for (int i = 0; i < invIDs.size(); i++) {
			idsStr += ",'"+ invIDs.get(i) +"'";
		}
		if(!idsStr.equals("")) idsStr = idsStr.substring(1);
		
		prmtRoom.setEnabledMultiSelection(true);
		prmtCustomer.setEnabledMultiSelection(true);
		
		
		ChequeDetailEntryCollection entryColl = ChequeDetailEntryFactory.getRemoteInstance()
				.getChequeDetailEntryCollection("select id,number,writtenOffTime,amount,room.id,room.name,room.number " +
						",customerEntry.*,customerEntry.customer.* where id in ("+ idsStr +") ");
		if(entryColl.size()==0) return;
		//�ͻ�����һ�£��������һ��
		RoomInfo oldRoomInfo = null;
		String oldCustIdsStr = null;
		String oldCustNamseStr = "";
		String oldInvoceNumbers = "";
		BigDecimal oldAmount = new BigDecimal("0");
		for (int i = 0; i < entryColl.size(); i++) {
			ChequeDetailEntryInfo info = entryColl.get(i);
			
			if(i==0)
				oldRoomInfo = info.getRoom();
			else if( (info.getRoom()==null && oldRoomInfo!=null) || (info.getRoom()!=null && oldRoomInfo ==null)   
					|| (info.getRoom()!=null && oldRoomInfo!=null && !info.getRoom().getId().equals(oldRoomInfo.getId()))	) {
				FDCMsgBox.showWarning("���ž�Ʊ�ݵķ��䲻һ�£����飡");
				this.abort();
			}

			String newCustIdsStr = "";
			for (int j = 0; j < info.getCustomerEntry().size(); j++) {
				newCustIdsStr += ","+info.getCustomerEntry().get(j).getCustomer().getId().toString();
				oldCustNamseStr += ","+info.getCustomerEntry().get(j).getCustomer().getName();
			}				
			if(!newCustIdsStr.equals(""))  newCustIdsStr = newCustIdsStr.substring(1);
			if(oldCustIdsStr==null) {
				oldCustIdsStr = newCustIdsStr;
			}else if(!oldCustIdsStr.equals(newCustIdsStr)){
				FDCMsgBox.showWarning("���ž�Ʊ�ݵĿͻ���һ�£����飡");
				this.abort();
			}	
			
			oldInvoceNumbers += "," + entryColl.get(i).getNumber();
			oldAmount = oldAmount.add(entryColl.get(i).getAmount());
		}
		
		// ����
		prmtRoom.setValue(entryColl.get(0).getRoom());
		// �ͻ�
		ChequeCustomerEntryCollection  customerColl = entryColl.get(0).getCustomerEntry();
		Object[] object = new Object[customerColl.size()];
		for(int i = 0 ; i <customerColl.size();i++){
			object[i] = customerColl.get(i).getCustomer();
		}
		prmtCustomer.setValue(object);
		// ԭ��Ʊ��
		txtOldInvoice.setText(oldInvoceNumbers.equals("")?"":oldInvoceNumbers.substring(1));
		// ԭ��Ʊ����
		pkDate.setValue(entryColl.get(0).getWrittenOffTime());
		// ���
		tntAmount.setValue(oldAmount);	
		
	}

	

}