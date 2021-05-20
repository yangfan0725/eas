/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.app.DbUtil;

/**
 * output class name
 */
public class ModifyCustomerNameUI extends AbstractModifyCustomerNameUI
{
    private MarketDisplaySetting setting = new MarketDisplaySetting();
	
	private static final Logger logger = CoreUIObject.getLogger(ModifyCustomerNameUI.class);
	
	private static boolean isUpdateName = true;

	/**
     * output class constructor
     */
    public ModifyCustomerNameUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	this.txtName.setMaxLength(80);
    	String actionCommand = (String) this.getUIContext().get("ActionCommand");
    	if(actionCommand!=null) {
    		if(actionCommand.equals("modifyName")) {
    			this.contName.setVisible(true);
    			this.contPhone.setVisible(false);
    			this.setUITitle("�ͻ������޸�");
    			isUpdateName = true;
    		}else if(actionCommand.equals("modifyPhone")) {
    			this.contName.setVisible(false);
    			this.contPhone.setVisible(true);
    			this.setUITitle("�ƶ��绰�޸�");
    			isUpdateName = false;
    		}    		
    	}
    }
    
	protected IObjectValue createNewData() {
		return new FDCCustomerInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCCustomerFactory.getRemoteInstance();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		/*
		 * if (!FDCCustomerHelper.verifyInputNotNull(this.txtName, "��������Ϊ�գ�") ||
		 * !FDCCustomerHelper.verifyInputNotNull(this.txtPhone, "�ƶ��绰����Ϊ�գ�")) {
		 * this.abort(); }
		 */

		if (isUpdateName) {
			if (!FDCCustomerHelper.verifyInputNotNull(this.txtName, "��������Ϊ�գ�")) {
				this.abort();
			}
		} else {
			if (!FDCCustomerHelper.verifyInputNotNull(this.txtPhone,
					"�ƶ��绰����Ϊ�գ�")) {
				this.abort();
			}
		}
		
		FDCCustomerInfo otherInfo = (FDCCustomerInfo) FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(this.editData.getId())).clone();
		otherInfo.setPhone(this.txtPhone.getText().trim());
		otherInfo.setName(this.txtName.getText().trim());
		//����û�
		Map rMap = FDCCustomerFactory.getRemoteInstance().verifySave(otherInfo,false);
		
		otherInfo = null;
//		String phone = this.txtPhone.getText().trim();
//		//ֻ�����ƶ��绰�仯��Ž����ظ���֤
//		if(!phone.equals(this.editData.getPhone())){
//			if(!FDCCustomerHelper.verifyPhone(phone, this.editData.getId().toString())){
//				this.abort();
//			}
//		}
		if(FDCCustomerHelper.verifyPhoneAndName(rMap,this)){
//			this.txtName.setText(this.editData.getName());
//			this.txtPhone.setText(this.editData.getPhone());
			abort();
		}
		
		

		this.editData.setName(this.txtName.getText());
		this.editData.setPhone(this.txtPhone.getText());
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("name");
		sels.add("phone");
		//---����ʹ��submit zhicheng_jin 090626
		getBizInterface().updatePartial(this.editData, sels);
		initOldData(this.editData);
		
		updatePurchaseCustomerInfo(this.editData);
		//��Ӷ����޺�ͬListUI�Ŀͻ���Ϣ�ĸ��� xin_wang 2010.12.15
		
		if(this.editData.getId()!=null)FDCCustomerFactory.getRemoteInstance().updateTenancyBill(this.editData.getId().toString());

		((ListUI)this.getUIContext().get("ListUI")).refreshList();
		destroyWindow();
	}

	/**
	 * �޸Ŀͻ����ƻ�绰�����ͬ���Ϲ����ϵĿͻ��绰�������ֶ���Ϣ �� ֤������
	 * custInfo ��Ϊ��ʱ���ĳ���ͻ����Ϲ������£�������������Ϲ�������
	 */
	public static void updatePurchaseCustomerInfo(FDCCustomerInfo custInfo) throws BOSException, EASBizException{
		
		String executeSql = "select head.*, customer.name, customer.phone,customer.tel, customer.certificateNumber ";
		if(custInfo!=null) executeSql += "where head.id in (select fheadId from t_she_purchasecustomerinfo where FCustomerID = '"+custInfo.getId().toString()+"') " ;		
		
		PurchaseCustomerInfoCollection purCustColl = PurchaseCustomerInfoFactory.getRemoteInstance().getPurchaseCustomerInfoCollection(executeSql);
		Map purMap = new HashMap();
		for(int i=0;i<purCustColl.size();i++) {
			PurchaseCustomerInfoInfo purCusInfo = purCustColl.get(i);
			PurchaseInfo purInfo = purCusInfo.getHead(); 
			if(!purMap.containsKey(purInfo.getId().toString())) {
				purInfo.setCustomerNames(purCusInfo.getCustomer().getName()+";");
				purInfo.setCustomerPhones(purCusInfo.getCustomer().getPhone()+","+(purCusInfo.getCustomer().getTel()==null?" ":purCusInfo.getCustomer().getTel())+";");
				purInfo.setCustomerIDCards(purCusInfo.getCustomer().getCertificateNumber()+";");
				purMap.put(purInfo.getId().toString(), purInfo);
			}else{
				PurchaseInfo existInfo = (PurchaseInfo)purMap.get(purInfo.getId().toString());
				existInfo.setCustomerNames(existInfo.getCustomerNames() + purCusInfo.getCustomer().getName()+";");
				existInfo.setCustomerPhones(existInfo.getCustomerPhones() + purCusInfo.getCustomer().getPhone()+","+(purCusInfo.getCustomer().getTel()==null?" ":purCusInfo.getCustomer().getTel())+";");
				existInfo.setCustomerIDCards(existInfo.getCustomerIDCards() + purCusInfo.getCustomer().getCertificateNumber()+";");
				purMap.put(purInfo.getId().toString(), existInfo);
			}
		}
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("customerNames");
		sels.add("customerPhones");
		sels.add("customerIDCards");
		Iterator iter = purMap.keySet().iterator();
		while(iter.hasNext()) {
			PurchaseInfo purInfo = (PurchaseInfo)purMap.get(iter.next());
			PurchaseFactory.getRemoteInstance().updatePartial(purInfo, sels);
		}
		
		
		
	

		
	}
	
	/**
	 * �Ѿ���ȡ������
	 * @param rMap
	 * @deprecated 
	 * @see FDCCustomerHelper.verifyPhoneAndName(Map rMap,CoreUI ui)
	 * @author yuanjun_lan
	 */
	
	public void verifyPhoneAndName(Map rMap){
		
		if((rMap.containsKey("dupNameMsg")&&setting.getNameRepeat()==0)&&(rMap.containsKey("dupPhoneMsg")&&setting.getPhoneRepeat()==0)){
			//������ǿ�Ʋ����棬�ϲ���ʾ
			FDCMsgBox.showConfirm3a(this, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupNameMsg")+"\n"+(String)rMap.get("dupPhoneMsg"));
			abort();//ǿ�Ʋ�����
		}
		
		if(rMap.containsKey("dupNameMsg")){
			if(setting.getNameRepeat()==2)
				{
					//����ʾ��ֱ�ӱ���
				}else if(setting.getNameRepeat()==0){
					FDCMsgBox.showConfirm3a(this, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupNameMsg"));
					abort();//ǿ�Ʋ�����
				}else if(setting.getNameRepeat()==1){
					//������ʾ���ͻ�ѡ�񱣴�;
					if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(this, "�����ظ�����Ϣ����鿴��ϸ��Ϣ��ȷ���Ƿ񱣴棿", (String)rMap.get("dupNameMsg"))){
						abort();
					}
				}else{
					abort();
				}
			}
			
			if(rMap.containsKey("dupPhoneMsg")){
				if(setting.getPhoneRepeat()==2)
				{
					//����ʾ��ֱ�ӱ���
				}else if(setting.getPhoneRepeat()==0){
					FDCMsgBox.showConfirm3a(this, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupPhoneMsg"));
					abort();//ǿ�Ʋ�����
				}else if(setting.getPhoneRepeat()==1){
					//������ʾ���ͻ�ѡ�񱣴�;
					if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(this, "�����ظ�����Ϣ����鿴��ϸ��Ϣ��ȷ���Ƿ񱣴棿", (String)rMap.get("dupPhoneMsg"))){
						abort();
					}
				}else{
					abort();
				}
			}
			if(rMap.containsKey("dupNameAndPhoneMsg")){
				if(setting.getNameAndPhoneRepeat() == 2){
					
				}else if(setting.getNameAndPhoneRepeat()==0){
					FDCMsgBox.showConfirm3a(this, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupNameAndPhoneMsg"));
					abort();//ǿ�Ʋ�����
				}else if(setting.getNameAndPhoneRepeat()==1){
					//������ʾ���ͻ�ѡ�񱣴�;
					if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(this, "�����ظ�����Ϣ����鿴��ϸ��Ϣ��ȷ���Ƿ񱣴棿", (String)rMap.get("dupNameAndPhoneMsg"))){
						abort();
					}
				}else{
					abort();
				}
			}
	}
	
	
	
	
	
}