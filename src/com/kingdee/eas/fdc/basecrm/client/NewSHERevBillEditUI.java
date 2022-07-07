/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.metas.ProcDefCollection;
import com.kingdee.bos.workflow.metas.ProcDefFactory;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.InvoiceTypeEnum;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevbillTwoEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevbillTwoEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.CRMChequeFactory;
import com.kingdee.eas.fdc.sellhouse.CRMChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeManageCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.DealStateEnum;
import com.kingdee.eas.fdc.sellhouse.ITranCustomerEntry;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.ProjectSet;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.MakeInvoiceEditUI;
import com.kingdee.eas.fdc.sellhouse.client.NewFDCRoomPromptDialog;
import com.kingdee.eas.fdc.sellhouse.client.SaleManPromptDialog;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class NewSHERevBillEditUI extends AbstractNewSHERevBillEditUI 
{
    private static final Logger logger = CoreUIObject.getLogger(NewSHERevBillEditUI.class);
    private Map PM=null;
    
    MarketDisplaySetting setting = new MarketDisplaySetting();
    
    
    public NewSHERevBillEditUI() throws Exception
    {
        super();
    }

    protected void applyDefaultValue(IObjectValue vo) {
    }
    protected void loadTblRevList(Object[] custObjs,BaseTransactionInfo relateBillInfo,SHERevBillInfo currRevBillInfo) throws BOSException{
//    	if(this.prmtRelateBizBill.getValue()==null){
//    		return ;
//    	}
    	String custIds = "";
    	CRMHelper.sortCollection(custObjs, "name", true);
    	if(custObjs!=null && custObjs.length>0){
    		for (int i = 0; i < custObjs.length; i++) {
    			SHECustomerInfo custInfo = (SHECustomerInfo)custObjs[i];
    			if(custInfo!=null){
    				custIds += "or parent.id in (select fid from t_bdc_sheRevBill where charindex('"+custInfo.getId().toString()+"',fcustomerIds)>0)";
    			}
			}
    	}
    	if(custIds.equals("")) {
    		return;
    	}
    	custIds = custIds.substring(2);
    	
		String filterBillSql = " where ";
		if(relateBillInfo!=null) filterBillSql += " (parent.relateTransId = '"+relateBillInfo.getTransactionID().toString()+"' or parent.relateTransId is null )";
		if(relateBillInfo!=null) filterBillSql += " and ("+custIds+")"; 
		
		//�������տ���ҷǱ���״̬
		filterBillSql += " and parent.revBillType in ('"+RevBillTypeEnum.GATHERING_VALUE+"','"+RevBillTypeEnum.REFUNDMENT_VALUE+"') and parent.state != '"+FDCBillStateEnum.SAVED_VALUE+"' ";
		//�Ҷ������Ǳ����� ����ֱֹ���޸��տΪ�˿����ʱ�������տ���ϸ���Լ���	��
		if(currRevBillInfo!=null && currRevBillInfo.getId()!=null)
			filterBillSql += " and parent.id != '"+currRevBillInfo.getId().toString()+"' ";
		if(this.prmtRelateBizBill.getValue()==null)
			filterBillSql += " and parent.relateBizBillId is null ";
		SHERevBillEntryCollection revEntryColl = SHERevBillEntryFactory.getRemoteInstance()
					.getSHERevBillEntryCollection("select *,moneyDefine.name,moneyDefine.moneyType,moneyDefine.isAmount,parent.bizDate,parent.revBillType  " + filterBillSql +"order by parent.bizDate ");  //
		this.tblRev.checkParsed();
		this.tblRev.removeRows();
		CRMHelper.sortCollection(revEntryColl, "moneyDefine.name", true);
		for(int i=0;i<revEntryColl.size();i++){
			SHERevBillEntryInfo revEntryInfo = revEntryColl.get(i);
			IRow addRow = this.tblRev.addRow();
			addRow.setUserObject(revEntryInfo);
			addRow.getCell("id").setValue(revEntryInfo.getId().toString());
    		addRow.getCell("moneyDefine").setValue(revEntryInfo.getMoneyDefine());
    		
    		addRow.getCell("revDate").setValue(revEntryInfo.getParent().getBizDate());
    		addRow.getCell("revAmount").setValue(revEntryInfo.getRevAmount());
    		
    		if(RevBillTypeEnum.refundment.equals(revEntryInfo.getParent().getRevBillType())){
    			addRow.getCell("isSelect").setValue(new Boolean(false));
    			addRow.getStyleAttributes().setLocked(true);
    			continue;
    		}
    		
    		if(RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())){
    			addRow.getCell("quitAmount").getStyleAttributes().setLocked(true);
    			addRow.getCell("amount").getStyleAttributes().setLocked(false);
    			
    			if(revEntryInfo.getRemainAmount().compareTo(FDCHelper.ZERO)>0&&revEntryInfo.getMoneyDefine().isIsAmount()){
    				addRow.getCell("canAmount").setValue(revEntryInfo.getRemainAmount());
//					if(revEntryInfo.getMoneyDefine().getName().equals("����")){
    					addRow.getCell("amount").setValue(revEntryInfo.getRemainAmount());
//    				}
        			addRow.getCell("isSelect").setValue(new Boolean(true));
        			addRow.getCell("isSelect").getStyleAttributes().setLocked(false);
        			addRow.getStyleAttributes().setBackground(Color.YELLOW);
        		}else{
        			addRow.getCell("isSelect").setValue(new Boolean(false));
        			addRow.getCell("isSelect").getStyleAttributes().setLocked(true);
    				addRow.getCell("amount").getStyleAttributes().setLocked(true);
        		}
        	}else if(RevBillTypeEnum.refundment.equals(this.revBillType.getSelectedItem())){
        		addRow.getCell("amount").getStyleAttributes().setLocked(true);
        		addRow.getCell("quitAmount").getStyleAttributes().setLocked(false);
        		addRow.getCell("canQuit").setValue(revEntryInfo.getRemainAmount().negate());
        		
        		if(revEntryInfo.getRemainAmount().compareTo(FDCHelper.ZERO)>0){
        			addRow.getCell("isSelect").getStyleAttributes().setLocked(false);
        			addRow.getCell("isSelect").setValue(new Boolean(true));
        			addRow.getStyleAttributes().setBackground(Color.YELLOW);
        		}else{
        			addRow.getCell("isSelect").getStyleAttributes().setLocked(true);
        			addRow.getCell("isSelect").setValue(new Boolean(false));
    				addRow.getCell("quitAmount").getStyleAttributes().setLocked(true);
        		}
        	}
		}
		
		CRMClientHelper.getFootRow(this.tblRev, new String[]{"amount","quitAmount","canAmount","revAmount","canQuit"});
    }
    public void loadFields()
    {
        super.loadFields();                		
        
        if(!this.getOprtState().equals(OprtState.ADDNEW)) {
	        dealRelateBillLoadFields();        
	        dealCustomerEntryLoadFields();     
        }
        
        if(RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())||RevBillTypeEnum.refundment.equals(this.revBillType.getSelectedItem())){
        	boolean isLoad=true;
        	for(int i=0;i<this.kdtEntrys.getRowCount();i++){
        		IRow crrRow = this.kdtEntrys.getRow(i);
        		SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)crrRow.getUserObject();
        		if(revEntryInfo.getAppRevAmount().compareTo(FDCHelper.ZERO)<0
        				&&RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())&&revEntryInfo.getMoneyDefine()!=null
        				&&revEntryInfo.getMoneyDefine().getName().equals("��������")){
        			isLoad=false;
        		}
        	}
        	 if(this.prmtCustomerEntry.getValue()!=null&&isLoad){
        		 
     			try {
     				loadTblRevList((Object[])prmtCustomerEntry.getValue(),(BaseTransactionInfo)this.prmtRelateBizBill.getValue(),this.editData);
     			} catch (BOSException e) {
     				e.printStackTrace();
     			}
     			if(this.prmtRelateBizBill.getValue()!=null){
     				try {
	     				BaseTransactionInfo billInfo = (BaseTransactionInfo)this.prmtRelateBizBill.getValue();
	     				ObjectUuidPK srcpk=new ObjectUuidPK(billInfo.getId());
	     				IObjectValue relateBillInfo=DynamicObjectFactory.getRemoteInstance().getValue(srcpk.getObjectType(),srcpk);
	     				if(relateBillInfo instanceof SincerityPurchaseInfo){
	     					SincerityPurchaseCustomerEntryCollection col= SincerityPurchaseCustomerEntryFactory.getRemoteInstance().getSincerityPurchaseCustomerEntryCollection("select customer.certificateNumber from where head.id='"+billInfo.getId()+"' and isMain=1");
	     					if(col.size()>0){
	     						this.txtCertificateNumber.setText(col.get(0).getCustomer().getCertificateNumber());
	     					}
	     				}else if(relateBillInfo instanceof PrePurchaseManageInfo){
	     					PrePurchaseCustomerEntryCollection col=PrePurchaseCustomerEntryFactory.getRemoteInstance().getPrePurchaseCustomerEntryCollection("select customer.certificateNumber from where head.id='"+billInfo.getId()+"' and isMain=1");
	     					if(col.size()>0){
	     						this.txtCertificateNumber.setText(col.get(0).getCustomer().getCertificateNumber());
	     					}
	     				}else if(relateBillInfo instanceof PurchaseManageInfo){
	     					PurCustomerEntryCollection col=PurCustomerEntryFactory.getRemoteInstance().getPurCustomerEntryCollection("select customer.certificateNumber from where head.id='"+billInfo.getId()+"' and isMain=1");
	     					if(col.size()>0){
	     						this.txtCertificateNumber.setText(col.get(0).getCustomer().getCertificateNumber());
	     					}
	     				}else if(relateBillInfo instanceof SignManageInfo){
	     					SignCustomerEntryCollection col=SignCustomerEntryFactory.getRemoteInstance().getSignCustomerEntryCollection("select customer.certificateNumber from where head.id='"+billInfo.getId()+"' and isMain=1");
	     					if(col.size()>0){
	     						this.txtCertificateNumber.setText(col.get(0).getCustomer().getCertificateNumber());
	     					}
	     				}
     				} catch (BOSException e) {
						e.printStackTrace();
					}
     			}else{
     				 if(this.prmtCustomerEntry.getValue()!=null&&prmtCustomerEntry.getValue() instanceof Object[]&&((Object[])prmtCustomerEntry.getValue())[0]!=null){
        				 this.txtCertificateNumber.setText(((SHECustomerInfo)((Object[])prmtCustomerEntry.getValue())[0]).getCertificateNumber());
        			 }
     			}
         	}
        }else if(RevBillTypeEnum.transfer.equals(this.revBillType.getSelectedItem())){
        	 if(this.prmtRelateFromCust.getValue()!=null){
      			try {
      				Object[] cus=new Object[]{prmtRelateFromCust.getValue()};
      				loadTblRevList(cus,(BaseTransactionInfo)this.prmtRelateFromBizBill.getValue(),this.editData);
      			} catch (BOSException e) {
      				e.printStackTrace();
      			}
          	}
        }
        
    	for(int i=0;i<this.kdtEntrys.getRowCount();i++){
        	IRow crrRow = this.kdtEntrys.getRow(i);
			SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)crrRow.getUserObject();
    		crrRow.getCell("appRevAmount").setValue(revEntryInfo.getAppRevAmount());
        }
    	if(RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())&&STATUS_ADDNEW.equals(this.oprtState)){
    		BigDecimal amount=FDCHelper.ZERO;
        	for(int i=0;i<tblRev.getRowCount();i++){
        		if(tblRev.getRow(i).getCell("moneyDefine").getValue()!=null&&
        				tblRev.getRow(i).getCell("moneyDefine").getValue().toString().equals("����")
        					&&((MoneyDefineInfo)tblRev.getRow(i).getCell("moneyDefine").getValue()).isIsAmount()){
        			if(tblRev.getRow(i).getCell("canAmount").getValue()!=null){
        				amount=amount.add((BigDecimal) tblRev.getRow(i).getCell("canAmount").getValue());
        			}
        		}
        	}
        	if(amount.compareTo(FDCHelper.ZERO)>0){
        		for(int i=0;i<this.kdtEntrys.getRowCount();i++){
        			IRow crrRow = this.kdtEntrys.getRow(i);
        			SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)crrRow.getUserObject();
        			if(revEntryInfo.getAmount()!=null&&revEntryInfo.getAmount().compareTo(FDCHelper.ZERO)>0) continue;
        			
        			if(revEntryInfo.getMoneyDefine()!=null&&revEntryInfo.getMoneyDefine().isIsAmount()) continue;
        			BigDecimal revAmount=revEntryInfo.getRevAmount();
        			if(revAmount.compareTo(amount)>=0){
        				crrRow.getCell("revAmount").setValue(revAmount.subtract(amount));
        				crrRow.getCell("amount").setValue(amount);
        				crrRow.getCell("appRevAmount").setValue(revAmount);
        				amount=FDCHelper.ZERO;
        			}else {
        				crrRow.getCell("revAmount").setValue(FDCHelper.ZERO);
        				crrRow.getCell("amount").setValue(revAmount);
        				crrRow.getCell("appRevAmount").setValue(revAmount);
        				amount=amount.subtract(revAmount);
        			}
        		}
        	}
    	}
        addAllEntryRevAmountToTop();
    }
    
    /**���ؿͻ���¼��Ϣ*/
    private void dealCustomerEntryLoadFields(){
    	SHERevCustEntryCollection custEntryColl = this.editData.getCustomerEntry();
    	Object[] sheCustObjs = new Object[custEntryColl.size()];
    	for (int i = 0; i < custEntryColl.size(); i++) {
    		sheCustObjs[i] = custEntryColl.get(i).getSheCustomer(); 
		}
    	this.prmtCustomerEntry.setValue(sheCustObjs);
    }
    
    /**���ؽ��׵��ݵ���Ϣ**/
    private void dealRelateBillLoadFields(){
        if(this.editData.getRelateBizBillId()!=null){
        	BaseTransactionInfo reltInfo = new BaseTransactionInfo();
        	reltInfo.put("RelateBizType", this.editData.getRelateBizType());
        	if(this.editData.getRelateTransId()!=null)
        		reltInfo.put("TransactionId", BOSUuid.read(this.editData.getRelateTransId()));
        	reltInfo.setId(BOSUuid.read(this.editData.getRelateBizBillId()));
        	reltInfo.setNumber(this.editData.getRelateBizBillNumber());
        	reltInfo.setRoom(this.editData.getRoom());
        	this.prmtRelateBizBill.setValue(reltInfo);
        }
        if(this.editData.getRelateFromBizId()!=null){
        	BaseTransactionInfo reltInfo = new BaseTransactionInfo();
        	reltInfo.put("RelateBizType", this.editData.getRelateFromBizType());
        	if(this.editData.getRelateFromTransId()!=null)
        		reltInfo.put("TransactionId", BOSUuid.read(this.editData.getRelateFromTransId()));
        	reltInfo.setId(BOSUuid.read(this.editData.getRelateFromBizId()));
        	reltInfo.setNumber(this.editData.getRelateFromBizNumber());
        	this.prmtRelateFromBizBill.setValue(reltInfo);        	
        }
    }
    
    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection selector = super.getSelectors();
    	selector.add(new SelectorItemInfo("state"));
    	selector.add(new SelectorItemInfo("relateBizType"));
    	selector.add(new SelectorItemInfo("relateBizBillId"));
    	selector.add(new SelectorItemInfo("relateBizBillNumber"));
    	selector.add(new SelectorItemInfo("relateFromBizType"));
    	selector.add(new SelectorItemInfo("relateFromBizId"));
    	selector.add(new SelectorItemInfo("relateFromBizNumber"));
    	selector.add(new SelectorItemInfo("relateTransId"));
    	selector.add(new SelectorItemInfo("relateFromTransId"));
    	
    	selector.add(new SelectorItemInfo("customerEntry.head.id"));
    	selector.add(new SelectorItemInfo("customerEntry.sheCustomer.mainCustomer.sysCustomer.*"));
    	selector.add(new SelectorItemInfo("customerEntry.sheCustomer.mainCustomer.*"));
    	selector.add(new SelectorItemInfo("customerEntry.sheCustomer.*"));
    	
    	selector.add(new SelectorItemInfo("isTansCreate"));
    	selector.add(new SelectorItemInfo("isGathered"));
    	
    	selector.add(new SelectorItemInfo("entrys.moneyDefine.*"));
    	selector.add(new SelectorItemInfo("entrys.settlementType.*"));
    	selector.add(new SelectorItemInfo("entrys.revAccountBank.*"));
    	
    	selector.add(new SelectorItemInfo("entrys.revEntry.*"));
    	selector.add(new SelectorItemInfo("CU.*"));
    	
    	selector.add(new SelectorItemInfo("moneyDefine"));
    	return selector;
    }
    

    public void storeFields()
    {
        super.storeFields();        
        
        dealRelateBillStoreFields();
        
        dealCustomerEntryStoreFields();
        
        String coloum="amount";
        
    	if(RevBillTypeEnum.refundment.equals(this.revBillType.getSelectedItem())){
    		coloum="quitAmount";
	   	}
    	int j=0;
    	String id=null;
    	BigDecimal subAmount=FDCHelper.ZERO;
    	
    	String moneyDefine="";
    	Set moneyDefineSet=new HashSet();
        for(int i=0;i<this.editData.getEntrys().size();i++){
        	if(this.editData.getEntrys().get(i).getMoneyDefine()!=null){
        		if(!moneyDefineSet.contains(this.editData.getEntrys().get(i).getMoneyDefine().getId())){
        			moneyDefineSet.add(this.editData.getEntrys().get(i).getMoneyDefine().getId());
        			moneyDefine=moneyDefine+this.editData.getEntrys().get(i).getMoneyDefine().getName()+";";
        		}
        	}
        	this.editData.getEntrys().get(i).getRevEntry().clear();
        	
        	BigDecimal amount=this.editData.getEntrys().get(i).getAmount();	
        	
        	if(RevBillTypeEnum.refundment.equals(this.revBillType.getSelectedItem())){
        		amount=this.editData.getEntrys().get(i).getRevAmount().negate();
        	}
    		if(amount!=null&&amount.compareTo(FDCHelper.ZERO)!=0){
    			while(subAmount.compareTo(FDCHelper.ZERO)>0){
					if(amount.compareTo(subAmount)>0){
						SHERevbillTwoEntryInfo entry=new SHERevbillTwoEntryInfo();
		    			entry.setSeq(j);
						entry.setAmount(subAmount);
						entry.setSheRevBillEntryId(BOSUuid.read(id));
						this.editData.getEntrys().get(i).getRevEntry().add(entry);
						
						amount=amount.subtract(subAmount);
						
						subAmount=FDCHelper.ZERO;
						id=null;
						
						j=j+1;
						
					}else if(amount.compareTo(subAmount)<0){
						SHERevbillTwoEntryInfo entry=new SHERevbillTwoEntryInfo();
		    			entry.setSeq(j);
						entry.setAmount(amount);
						entry.setSheRevBillEntryId(BOSUuid.read(id));
						this.editData.getEntrys().get(i).getRevEntry().add(entry);
						
						
						subAmount=subAmount.subtract(amount);
						amount=FDCHelper.ZERO;
						break;
					}else{
						SHERevbillTwoEntryInfo entry=new SHERevbillTwoEntryInfo();
		    			entry.setSeq(j);
						entry.setAmount(amount);
						entry.setSheRevBillEntryId(BOSUuid.read(id));
						this.editData.getEntrys().get(i).getRevEntry().add(entry);
						
						
						subAmount=FDCHelper.ZERO;
						amount=FDCHelper.ZERO;
						
						break;
					}
				}
    			if(amount!=null&&amount.compareTo(FDCHelper.ZERO)!=0){
    				while(j<this.tblRev.getRowCount()){
        				if(((Boolean)this.tblRev.getRow(j).getCell("isSelect").getValue()).booleanValue()
        	    				&&this.tblRev.getRow(j).getCell(coloum).getValue()!=null){
        					
        					BigDecimal revAmount=(BigDecimal)this.tblRev.getRow(j).getCell(coloum).getValue();
        					
        					if(RevBillTypeEnum.refundment.equals(this.revBillType.getSelectedItem())){
        						revAmount=((BigDecimal)this.tblRev.getRow(j).getCell(coloum).getValue()).negate();
        					}
        					if(amount.compareTo(revAmount)>0){
        						SHERevbillTwoEntryInfo entry=new SHERevbillTwoEntryInfo();
        		    			entry.setSeq(j);
        						entry.setAmount(revAmount);
        						entry.setSheRevBillEntryId(BOSUuid.read(this.tblRev.getRow(j).getCell("id").getValue().toString()));
        						this.editData.getEntrys().get(i).getRevEntry().add(entry);
        						
        						amount=amount.subtract(revAmount);
        						
        						j=j+1;
        						
        					}else if(amount.compareTo(revAmount)<0){
        						SHERevbillTwoEntryInfo entry=new SHERevbillTwoEntryInfo();
        		    			entry.setSeq(j);
        						entry.setAmount(amount);
        						entry.setSheRevBillEntryId(BOSUuid.read(this.tblRev.getRow(j).getCell("id").getValue().toString()));
        						this.editData.getEntrys().get(i).getRevEntry().add(entry);
        						
        						subAmount=revAmount.subtract(amount);
        						
        						amount=FDCHelper.ZERO;
        						id=this.tblRev.getRow(j).getCell("id").getValue().toString();
        						
        						break;
        					}else {
        						SHERevbillTwoEntryInfo entry=new SHERevbillTwoEntryInfo();
        		    			entry.setSeq(j);
        						entry.setAmount(amount);
        						entry.setSheRevBillEntryId(BOSUuid.read(this.tblRev.getRow(j).getCell("id").getValue().toString()));
        						this.editData.getEntrys().get(i).getRevEntry().add(entry);
        						
        						amount=FDCHelper.ZERO;
        						j=j+1;
        						break;
        					}
        				}else{
        					j=j+1;
        				}
        			}
    			}
    		}
        }
        this.editData.setMoneyDefine(moneyDefine);
    } 
    
    /**�����ͻ��Ĵ洢**/
    private void dealCustomerEntryStoreFields(){
    	Object[] custObjs = (Object[])this.prmtCustomerEntry.getValue();
    	
    	String custIdsStr = "";
    	String custNamesStr = "";
    	this.editData.getCustomerEntry().clear();
    	CRMHelper.sortCollection(custObjs, "name", true);
    	for (int i = 0; i < custObjs.length; i++) {
			SHECustomerInfo custInfo = (SHECustomerInfo)custObjs[i];
			if(custInfo!=null) {
				SHERevCustEntryInfo custEntryInfo = new SHERevCustEntryInfo();
				custEntryInfo.setHead(this.editData);
				custEntryInfo.setSheCustomer(custInfo);
				custIdsStr += ","+custInfo.getId().toString();
				custNamesStr += ","+custInfo.getName();
				this.editData.getCustomerEntry().add(custEntryInfo);
			}
		}
    	if(!custIdsStr.equals("")) {
    		this.editData.setCustomerIds(custIdsStr.substring(1));
    		this.editData.setCustomerNames(custNamesStr.substring(1));
    	}
    }
    
    
    /**����������׵��ݵĴ洢��Ϣ*/
    private void dealRelateBillStoreFields(){
        //�������ݵ�ʵ�嶼�̳���FDCBILL
        this.editData.setRelateBizType(null);
        this.editData.setRelateBizBillId(null);
        this.editData.setRelateBizBillNumber(null);
        this.editData.setRelateFromBizType(null);
        this.editData.setRelateFromBizId(null);
        this.editData.setRelateFromBizNumber(null); 
        this.editData.setRelateTransId(null);
        this.editData.setRelateFromTransId(null);
        BaseTransactionInfo relateBillInfo = (BaseTransactionInfo)this.prmtRelateBizBill.getValue();
        BaseTransactionInfo relateFrommBillInfo = (BaseTransactionInfo)this.prmtRelateFromBizBill.getValue();
        if(relateBillInfo!=null) {
	        this.editData.setRelateBizType((RelatBizType)relateBillInfo.get("RelateBizType"));
	        BOSUuid bosId = (BOSUuid)relateBillInfo.get("TransactionId");
	        if(bosId!=null) this.editData.setRelateTransId(bosId.toString());
	        this.editData.setRelateBizBillId(relateBillInfo.getId().toString());
	        this.editData.setRelateBizBillNumber(relateBillInfo.getNumber());
        }
        if(relateFrommBillInfo!=null) {
	        this.editData.setRelateFromBizType((RelatBizType)relateFrommBillInfo.get("RelateBizType"));
	        BOSUuid bosId = (BOSUuid)relateFrommBillInfo.get("TransactionId");
	        if(bosId!=null)  this.editData.setRelateFromTransId(bosId.toString());
	        this.editData.setRelateFromBizId(relateFrommBillInfo.getId().toString());
	        this.editData.setRelateFromBizNumber(relateFrommBillInfo.getNumber());
        }
//        ChequeDetailEntryInfo cheInfo = (ChequeDetailEntryInfo)this.prmtReceipt.getValue();
//        if(cheInfo!=null)	this.editData.setReceiptNumber(cheInfo.getNumber());
//        ChequeDetailEntryInfo invoiceInfo = (ChequeDetailEntryInfo)this.prmtInvoice.getValue();
//        if(invoiceInfo!=null) this.editData.setInvoiceNumber(invoiceInfo.getNumber());
    }
    private void initTblRevList() {
    	this.tblRev.checkParsed();
    	this.tblRev.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
    	this.tblRev.getStyleAttributes().setLocked(true);
    	this.tblRev.getColumn("isSelect").getStyleAttributes().setHided(true);
    	this.tblRev.getColumn("moneyDefine").getStyleAttributes().setLocked(true);
    	this.tblRev.getColumn("revAmount").getStyleAttributes().setLocked(true);
    	this.tblRev.getColumn("revDate").getStyleAttributes().setLocked(true);
    	this.tblRev.getColumn("canQuit").getStyleAttributes().setLocked(true);
    	this.tblRev.getColumn("canAmount").getStyleAttributes().setLocked(true);
    	
    	this.tblRev.getColumn("canQuit").setEditor(SHEManageHelper.getNumberCellEditor(2,2));
    	this.tblRev.getColumn("canAmount").setEditor(SHEManageHelper.getNumberCellEditor(2,2));
    	this.tblRev.getColumn("revAmount").setEditor(SHEManageHelper.getNumberCellEditor(2,2));
    	this.tblRev.getColumn("amount").setEditor(SHEManageHelper.getNumberCellEditor(2,2));
    	this.tblRev.getColumn("quitAmount").setEditor(SHEManageHelper.getNumberCellEditor(2,2));
    }
    private void initTblPayList() {
    	 this.kdtEntrys.checkParsed();
    	 this.kdtEntrys.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
    	 
    	 KDBizPromptBox box = new KDBizPromptBox();
    	 KDTDefaultCellEditor editor = new KDTDefaultCellEditor(box);
    	 box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
         box.setDisplayFormat("$name$");
         box.setEditFormat("$number$");
         box.setCommitFormat("$number$");
         this.kdtEntrys.getColumn("moneyDefine").setEditor(editor); 
     	
         ObjectValueRender kdtEntrys_moneyDefine_OVR = new ObjectValueRender();
         kdtEntrys_moneyDefine_OVR.setFormat(new BizDataFormat("$name$"));
         this.kdtEntrys.getColumn("moneyDefine").setRenderer(kdtEntrys_moneyDefine_OVR);
         
         EntityViewInfo entityViewInfo = new EntityViewInfo();
         FilterInfo filterInfo = new FilterInfo();
         entityViewInfo.setFilter(filterInfo);
         filterInfo.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));
         
     	 SellProjectInfo currSellProInfo = (SellProjectInfo)this.prmtsellProject.getValue();
     	 if(currSellProInfo!=null) {
     		 Map detailSet = RoomDisplaySetting.getNewProjectSet(null,currSellProInfo.getId().toString());
         	 boolean isMustBank = (detailSet==null)?false:((Boolean)detailSet.get(SHEParamConstant.T2_IS_MUST_BY_BANK)).booleanValue();
         	 if(isMustBank){
         		filterInfo.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.LOANAMOUNT_VALUE,CompareType.NOTEQUALS));
         		filterInfo.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.ACCFUNDAMOUNT_VALUE,CompareType.NOTEQUALS));
         	 }
     	}
     	box.setEntityViewInfo(entityViewInfo);
     	this.kdtEntrys.getColumn("moneyDefine").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
    	
        box = new KDBizPromptBox();
        editor = new KDTDefaultCellEditor(box);
        box.setQueryInfo("com.kingdee.eas.basedata.assistant.app.SettlementTypeQuery");
        box.setDisplayFormat("$name$");
        box.setEditFormat("$number$");
        box.setCommitFormat("$number$");
        this.kdtEntrys.getColumn("settlementType").setEditor(editor);
        
        box = new KDBizPromptBox();
        box.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7AccountBankQuery");
        box.setDisplayFormat("$name$");
        box.setEditFormat("$number$");
        box.setCommitFormat("$number$");
        editor = new KDTDefaultCellEditor(box);
        entityViewInfo = new EntityViewInfo();
        filterInfo = new FilterInfo();
        entityViewInfo.setFilter(filterInfo);
        filterInfo.getFilterItems().add(new FilterItemInfo("company.id",SysContext.getSysContext().getCurrentFIUnit().getId()));
        box.setEntityViewInfo(entityViewInfo);
        this.kdtEntrys.getColumn("revAccountBank").setEditor(editor);
         
        KDComboBox comboField = new KDComboBox();
		List list =InvoiceTypeEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		this.kdtEntrys.getColumn("invoiceType").setEditor(comboEditor);
		this.kdtEntrys.getColumn("invoiceType").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kdtEntrys.getColumn("invoiceType").getStyleAttributes().setHided(true);
		this.kdtEntrys.getColumn("receiptNumber").getStyleAttributes().setHided(true);
		this.kdtEntrys.getColumn("invoiceNumber").getStyleAttributes().setHided(true);
		
    	this.kdtEntrys.getColumn("amount").setEditor(SHEManageHelper.getNumberCellEditor(2,2));
    	this.kdtEntrys.getColumn("revAmount").setEditor(SHEManageHelper.getNumberCellEditor(2,2));
    	this.kdtEntrys.getColumn("appRevAmount").setEditor(SHEManageHelper.getNumberCellEditor(2,2));
    	this.kdtEntrys.getColumn("appRevAmount").getStyleAttributes().setLocked(true);
    	
    	//ֻ������ʱ����Ҫ���ý��㷽ʽ
    	int colMonDefinIndex = this.kdtEntrys.getColumnIndex("settlementType");	//���ӽ��������
    	IColumn settleMentColumn = this.kdtEntrys.addColumn(colMonDefinIndex);
    	settleMentColumn.setKey("settlementCount");
    	settleMentColumn.getStyleAttributes().setHided(false);
    	settleMentColumn.setWidth(60);
    	this.kdtEntrys.getHeadRow(0).getCell("settlementCount").setValue("�������");
		KDFormattedTextField setlCountField = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		setlCountField.setNegatived(false);
		ICellEditor setlCountEditor = new KDTDefaultCellEditor(setlCountField);
		settleMentColumn.setEditor(setlCountEditor);
		settleMentColumn.getStyleAttributes().setHided(true);
    }
    protected void initControl() throws BOSException {
    	this.txtrevAmount.setPrecision(2);
		this.txtrevAmount.setRoundingMode(4);
		this.txtDescription.setMaxLength(255);
		
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
       	this.actionCreateTo.setVisible(false);
       	this.actionTraceDown.setVisible(false);
       	this.actionTraceUp.setVisible(false);
       	this.actionWorkflowList.setVisible(false);
       	this.actionCreateFrom.setVisible(false);
       	this.actionMultiapprove.setVisible(false);
       	this.actionNextPerson.setVisible(false);
       	this.actionCopy.setVisible(false);
       	this.actionCopyFrom.setVisible(false);
       	this.actionSave.setVisible(false);
       	
       	this.actionMakeInvoice.setVisible(false);
       	this.actionRecycle.setVisible(false);	//����Ӧ�ò���Ҫ��ֱ��ͨ��ѡ���վݻ�Ʊ��f7������
       	
    	this.actionAudit.setEnabled(true);
    	this.actionUnAudit.setEnabled(true);
    	this.actionRecycle.setEnabled(true);
    	this.actionMakeInvoice.setEnabled(true);
    	this.prmtsellProject.setEnabled(false);

    	if(this.editData.getState()!=null && !this.editData.getState().equals(FDCBillStateEnum.SAVED)) {
    		this.actionSave.setVisible(false);
    	}
    	
    	this.revBillType.removeItem(RevBillTypeEnum.adjust);	//��û�е������� 
    	
    	
    	if(this.getOprtState().equals(OprtState.ADDNEW)) {
    		if(this.editData.getId()!=null)
    			this.revBillType.setEnabled(false);
    		
    		//δ����������ʱ������ֱ��<�ύ&����>;��������.  
    		EntityViewInfo procDefView = new EntityViewInfo();
    		FilterInfo proDefFilterInfo = new FilterInfo();
    		proDefFilterInfo.getFilterItems().add(new FilterItemInfo("proccode","SheRevBillWorkFlow"));
    		proDefFilterInfo.getFilterItems().add(new FilterItemInfo("enable",new Boolean(true)));
    		procDefView.setFilter(proDefFilterInfo);
    		ProcDefCollection preDefColl = ProcDefFactory.getRemoteInstance().getCollection(procDefView);
    		if(preDefColl.size()>0)
    			this.actionSubmitAudit.setVisible(false);
    		else
    			this.actionSubmitAudit.setEnabled(true);
    		
    		if(!this.editData.getRevBillType().equals(RevBillTypeEnum.transfer))
    		  this.kdtEntrys.getColumn("settlementCount").getStyleAttributes().setHided(false);
    	}else{
    		this.revBillType.setEnabled(false);	//������״̬�����޸ĵ�������
    		changeComStateByRevBillType(this.editData.getRevBillType());
    		this.actionSubmitAudit.setVisible(false);
    	}
    	
    	NewFDCRoomPromptDialog roomDialog = new NewFDCRoomPromptDialog(new Boolean(false),null,null,MoneySysTypeEnum.SalehouseSys,null,(SellProjectInfo)this.prmtsellProject.getValue());
    	this.prmtroom.setSelector(roomDialog);
    	
    	
    	this.prmtCustomerEntry.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				setPromtCustomerFilterView(NewSHERevBillEditUI.this.prmtCustomerEntry,NewSHERevBillEditUI.this.prmtRelateBizBill);
			}
		});

    	this.prmtRelateFromCust.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				setPromtCustomerFilterView(NewSHERevBillEditUI.this.prmtRelateFromCust,NewSHERevBillEditUI.this.prmtRelateFromBizBill);
			}
		});
    	
       	FDCCommonPromptBox comBox = new FDCCommonPromptBox(RelateBillQueryUI.class.getName(),this);
    	this.prmtRelateBizBill.setSelector(comBox);  
    	this.prmtRelateFromBizBill.setSelector(comBox);    
    	
    	changeComStateByRevBillType((RevBillTypeEnum) this.revBillType.getSelectedItem());
    	if(!RevBillTypeEnum.transfer.equals(this.revBillType.getSelectedItem())){
    		this.revBillType.removeItem(RevBillTypeEnum.transfer);
    	}
    	
    	dealAmount();
    	
    	this.actionAddNew.setVisible(false);
    	
    	KDBizPromptBox gatheringSubject = new KDBizPromptBox();
		CompanyOrgUnitInfo curCompany = SysContext.getSysContext().getCurrentFIUnit();
		
		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox opseelect = new AccountPromptBox(this, curCompany,view.getFilter(), false, true);
		gatheringSubject.setEntityViewInfo(view);
		gatheringSubject.setSelector(opseelect);
		gatheringSubject.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
		gatheringSubject.setEditFormat("$number$");
		gatheringSubject.setCommitFormat("$number$");
		
		this.prmtRevAccount.setDialog(opseelect);
		
		this.txtSettlementNumber.setMaxLength(44);
		
        EntityViewInfo entityViewInfo = new EntityViewInfo();
        FilterInfo filterInfo = new FilterInfo();
        entityViewInfo.setFilter(filterInfo);
        filterInfo.getFilterItems().add(new FilterItemInfo("company.id",SysContext.getSysContext().getCurrentFIUnit().getId()));
        this.prmtAccountBank.setEntityViewInfo(entityViewInfo);
        
        prmtRevAccount.setRequired(true);
        this.prmtSettlementType.setRequired(true);
        
//        if(editData.getRelateTransId()==null){
        	this.prmtRelateBizBill.setEnabled(false);
//        }
        if(RevBillTypeEnum.refundment.equals(this.revBillType.getSelectedItem())&&PM!=null){
        	this.kdtEntrys.getColumn("revAmount").getStyleAttributes().setLocked(true);
        	this.kdtEntrys.getColumn("settlementCount").getStyleAttributes().setLocked(true);
        	
        	this.actionAddLine.setEnabled(false);
        	this.actionInsertLine.setEnabled(false);
        	this.actionRemoveLine.setEnabled(false);
        	
        	PM.put("PMAmount", null);
        }
        
        if(RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())){
        	for(int i=0;i<this.kdtEntrys.getRowCount();i++){
        		IRow crrRow = this.kdtEntrys.getRow(i);
        		SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)crrRow.getUserObject();
        		if(revEntryInfo.getAppRevAmount().compareTo(FDCHelper.ZERO)<0
        				&&RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())&&revEntryInfo.getMoneyDefine()!=null
        				&&revEntryInfo.getMoneyDefine().getName().equals("��������")){
        			this.actionAddLine.setEnabled(false);
        			this.actionInsertLine.setEnabled(false);
        			this.actionRemoveLine.setEnabled(false);
        			this.kdtEntrys.getRow(i).getCell("moneyDefine").getStyleAttributes().setLocked(true);
        			this.kdtEntrys.getRow(i).getCell("revAmount").getStyleAttributes().setLocked(true);
        		}
        	}
        }
        this.contCertificateNumber.getBoundLabel().setForeground(Color.BLUE);
	}
    
    protected void prmtAccountBank_dataChanged(DataChangeEvent e) throws Exception {
    	if(this.prmtAccountBank.getValue()==null) return;
    	AccountBankInfo revAccBankInfo = (AccountBankInfo)this.prmtAccountBank.getValue();
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("bank.id");
		sels.add("bank.name");
		sels.add("bank.number");
		sels.add("account.*");
		revAccBankInfo = (AccountBankInfo) AccountBankFactory.getRemoteInstance().getValue(new ObjectUuidPK(revAccBankInfo.getId()), sels);
    	this.prmtBank.setValue(revAccBankInfo.getBank());
    	this.prmtRevAccount.setValue(revAccBankInfo.getAccount());
	}
    
    protected void prmtBank_dataChanged(DataChangeEvent e) throws Exception {
    	if(CommerceHelper.isF7DataChanged(e))
    	{
    		initrevAccountBankFilter();
    	}
	}

	/*
     * �����˻��������������й���
     */
    private void initrevAccountBankFilter() throws BOSException, EASBizException {
    	EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit(); 
    	if(this.prmtBank.getValue()!=null)
		{
			BankInfo revBank = (BankInfo)this.prmtBank.getValue();
			filter.getFilterItems().add(new FilterItemInfo("bank.id", revBank.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("company.id", company.getId().toString()));
			viewInfo.setFilter(filter);
			prmtAccountBank.setEntityViewInfo(viewInfo);
		}else
		{
			filter.getFilterItems().add(new FilterItemInfo("company.id", company.getId().toString()));
			viewInfo.setFilter(filter);
			prmtAccountBank.setEntityViewInfo(viewInfo);
		}
    }
    private EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo)
	{
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
    public void onLoad() throws Exception {   	
    	initTblPayList();
    	initTblRevList();
    	super.onLoad();    	
//    	handleCodingRule();
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
    	initControl();
    	
    	if(SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("900002")||
				SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("00561")||
				SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("00799")){
    		this.kdtEntrys.setEditable(true);
    		this.kdtEntrys.getColumn("moneyDefine").getStyleAttributes().setLocked(false);
			KDWorkButton btnReCal=new KDWorkButton();
			btnReCal.setText("���·�¼����");
			btnReCal.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent e) {
		                beforeActionPerformed(e);
		                try {
		                	btnReCal_actionPerformed(e);
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                    afterActionPerformed(e);
		                }
		            }
		        });
			this.toolBar.add(btnReCal);
    	}
    }
    protected void btnReCal_actionPerformed(ActionEvent e) throws Exception {
    	if (MsgBox.showConfirm2("�Ƿ�ȷ���޸ģ�") == MsgBox.CANCEL) {
			return;
		}
    	FDCSQLBuilder fdcSB = new FDCSQLBuilder();
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
    	for(int i=0;i<this.kdtEntrys.getRowCount();i++){
    		String id=this.kdtEntrys.getRow(i).getCell("id").getValue().toString();
    		MoneyDefineInfo md=(MoneyDefineInfo)this.kdtEntrys.getRow(i).getCell("moneyDefine").getValue();
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append("update T_BDC_SHERevBillEntry set fmoneyDefineId = '"+md.getId().toString()+"' where fid = '").append(id).append("'");
    		fdcSB.addBatch(sql.toString());
    	}
    	fdcSB.executeBatch();
    	FDCClientUtils.showOprtOK(this);
	}
    public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		this.revBillType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
    	if (!STATUS_ADDNEW.equals(this.oprtState)) {
			this.revBillType.setEnabled(false);
		}else{
			this.revBillType.setEnabled(true);
		}
	}
    
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.basecrm.SHERevBillFactory.getRemoteInstance();
    }

    protected IObjectValue createNewDetailData(KDTable table)
    {	
        return new SHERevBillEntryInfo();
    }

    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        SHERevBillInfo objectValue = new SHERevBillInfo();
    	Timestamp curTime = null;
		try {
			curTime = FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e1) {
			e1.printStackTrace();
			curTime = new Timestamp(System.currentTimeMillis());
		}
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		objectValue.setState(FDCBillStateEnum.SUBMITTED);
		objectValue.put("saleOrgUnit",SysContext.getSysContext().getCurrentOrgUnit(OrgType.getEnum("Sale")));
        objectValue.setCreator((UserInfo)(SysContext.getSysContext().getCurrentUserInfo()));
		
        try {
        	CompanyOrgUnitInfo tempCompany = SysContext.getSysContext().getCurrentFIUnit();
			CompanyOrgUnitInfo company = GlUtils.getCurrentCompany(null,tempCompany.getId().toString(),null,false);
			objectValue.setCurrency(company.getBaseCurrency());
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}	
        
        objectValue.setExchangeRate(FDCHelper.ONE);
        objectValue.setBizDate(new Date(curTime.getTime()));
        objectValue.setIsTansCreate(false);
        objectValue.setRevBillType(RevBillTypeEnum.gathering);
        
		RevBillTypeEnum billType = (RevBillTypeEnum)this.getUIContext().get("RevBillTypeEnum");
		if(billType!=null)	{
			objectValue.setRevBillType(billType);
			this.revBillType.setEnabled(false);
			this.revBillType.setSelectedItem(billType);
		}
		
		BaseTransactionInfo fdcBillInfo = (BaseTransactionInfo)this.getUIContext().get("FDCBillInfo");
		BaseTransactionInfo newfdcBillInfo = (BaseTransactionInfo)this.getUIContext().get("NewFDCBillInfo");
		
		Object[] custEntrys = (Object[])this.getUIContext().get("CustomerEntrys");
		if(billType!=null && billType.equals(RevBillTypeEnum.transfer)) {   //ת�����
			if(newfdcBillInfo!=null)	{
				this.prmtRelateBizBill.setValue(newfdcBillInfo);
				this.prmtRelateBizBill.setEnabled(false);
				if(newfdcBillInfo.get("room")!=null){
					objectValue.setRoom((RoomInfo)newfdcBillInfo.get("room"));
					this.prmtroom.setEnabled(false);
				}
			}
			if(fdcBillInfo!=null) {	
    			this.prmtRelateFromBizBill.setValue(fdcBillInfo);
    			this.prmtRelateFromBizBill.setEnabled(false);
			}
		}else{
    		if(fdcBillInfo!=null) {	
    			this.prmtRelateBizBill.setValue(fdcBillInfo);
    			this.prmtRelateBizBill.setEnabled(false);
    			if(fdcBillInfo.get("room")!=null)
    				objectValue.setRoom((RoomInfo)fdcBillInfo.get("room"));
    		}
		}
		
		if(custEntrys!=null){
			removeDataChangeListener(this.prmtCustomerEntry);
			this.prmtCustomerEntry.setValue(custEntrys);
			addDataChangeListener(this.prmtCustomerEntry);
			this.prmtCustomerEntry.setEnabled(false);
		}
		SHECustomerInfo customer = (SHECustomerInfo)this.getUIContext().get("customer");
		if(customer!=null){
			objectValue.setRelateFromCust(customer);
			this.prmtRelateFromCust.setEnabled(false);
		}
		
		if(custEntrys!=null && custEntrys.length>0){
			String custNames = "";
			for (int i = 0; i < custEntrys.length; i++) {
				SHECustomerInfo sheCustInfo = (SHECustomerInfo)custEntrys[i];
				custNames += "," + sheCustInfo.getName();
			}
			if(!custNames.equals("")) custNames = custNames.substring(1);
			objectValue.setPayCustomerName(custNames);
		}
		
		SellProjectInfo spInfo = (SellProjectInfo)this.getUIContext().get("SellProjectInfo");
		if(spInfo!=null)	objectValue.setSellProject(spInfo);
		
		SHERevBillEntryCollection revEntryColl = (SHERevBillEntryCollection)this.getUIContext().get("RevEntryColl");
		if(revEntryColl!=null) {
			for (int i = 0; i < revEntryColl.size(); i++) {
				SHERevBillEntryInfo retObjInfo = (SHERevBillEntryInfo)revEntryColl.get(i);
				objectValue.getEntrys().add(retObjInfo);  
			}
		}
		PM=(Map)this.getUIContext().get("PM");
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		if(PM==null&&objectValue.getRoom()!=null){
			try {
				ChangeManageCollection col=ChangeManageFactory.getRemoteInstance().getChangeManageCollection("select * from where room.id='"+objectValue.getRoom().getId()+"' or srcRoom.id='"+objectValue.getRoom().getId()+"'");
				for(int i=0;i<col.size();i++){
					if(DealStateEnum.NOTDEAL.equals(col.get(i).getDealState())&&col.get(i).getBizType().equals(ChangeBizTypeEnum.CHANGEROOM)){
						FDCMsgBox.showWarning("��������ҵ��δ���в�����");
						this.abort();
					} 
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
        return objectValue; 
    }

    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getId()!=null) {
    		if(!this.editData.getState().equals(FDCBillStateEnum.SUBMITTED) && !this.editData.getState().equals(FDCBillStateEnum.AUDITTING)){
    			FDCMsgBox.showInfo("�õ��ݷ��ύ��������״̬����ֹ������");
    			return;
    		}
//    		if(this.editData.getRevBillType().equals(RevBillTypeEnum.gathering)){
//    			for(int j=0;j<this.editData.getEntrys().size();j++){
//        			if(this.editData.getEntrys().get(j).getInvoiceType()==null){
//        				FDCMsgBox.showInfo("�õ��ݿ�Ʊ״̬Ϊ��,��ֹ������");
//            			return;
//        			}
//        		}
//    		}
    		SHERevBillFactory.getRemoteInstance().audit(this.editData.getId());
    		FDCMsgBox.showInfo("�����ɹ���");
    	}
    	syncDataFromDB();
		handleOldData();
		
		this.actionSubmit.setEnabled(false);
    	this.actionEdit.setEnabled(true);
    }
    protected void handleOldData() {
		if(!(getOprtState()==STATUS_FINDVIEW||getOprtState()==STATUS_VIEW)){
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}
    protected void syncDataFromDB() throws Exception {
		//�ɴ��ݹ�����ID��ȡֵ����
        if(getUIContext().get(UIContext.ID) == null)
        {
            String s = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_IDIsNull");
            MsgBox.showError(s);
            SysUtil.abort();
        }
        IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
        setDataObject(getValue(pk));
        loadFields();
	}
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getId()!=null) {
    		if(!this.editData.getState().equals(FDCBillStateEnum.AUDITTED)){
    			FDCMsgBox.showInfo("�õ��ݷ�������״̬����ֹ��������");
    			return;
    		}
    		SHERevBillFactory.getRemoteInstance().unAudit(this.editData.getId());
    		FDCMsgBox.showInfo("�������ɹ���");
    	}
    	syncDataFromDB();
		handleOldData();
    }
    
    protected void revBillType_itemStateChanged(ItemEvent e) throws Exception {
    	
//    	RevBillTypeEnum revType = (RevBillTypeEnum)e.getItem();
//    	changeComStateByRevBillType(revType);
//	    if(RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())||
//	    		RevBillTypeEnum.refundment.equals(this.revBillType.getSelectedItem())){
//	    	if(this.prmtCustomerEntry.getValue()!=null){
//				loadTblRevList((Object[])prmtCustomerEntry.getValue(),(BaseTransactionInfo)this.prmtRelateBizBill.getValue(),this.editData);
//	    	}
//    	}
    }
    
    /**���ݵ������͵���������ʾ*/
    private void changeComStateByRevBillType(RevBillTypeEnum revType) {
    	if(RevBillTypeEnum.gathering.equals(revType)){
        	if(this.getOprtState().equals(OprtState.ADDNEW) && this.kdtEntrys.getColumn("settlementCount")!=null)
        		this.kdtEntrys.getColumn("settlementCount").getStyleAttributes().setHided(false);
        	
        	this.kdtEntrys.getColumn("amount").getStyleAttributes().setLocked(false);
        	
    	}else if(RevBillTypeEnum.refundment.equals(revType)){
        	if(this.kdtEntrys.getColumn("settlementCount")!=null)
        		this.kdtEntrys.getColumn("settlementCount").getStyleAttributes().setHided(false);
        	
        	this.kdtEntrys.getColumn("amount").getStyleAttributes().setLocked(true);
    	}
    	
    	if(this.getOprtState().equals(OprtState.VIEW)){
    		this.kdAddnewLine.setEnabled(false);
    		this.kdInserLine.setEnabled(false);
    		this.kdRemoveLine.setEnabled(false);
    	}else{
    		this.kdAddnewLine.setEnabled(true);
    		this.kdInserLine.setEnabled(true);
    		this.kdRemoveLine.setEnabled(true);
    	}
    	
    	this.contreceiptNumber.setVisible(false);
    	this.continvoiceNumber.setVisible(false);
    	this.contInvoice.setVisible(false);
    	this.contReceipt.setVisible(false); 
    	this.contrelateFromBizBill.setVisible(false);
		this.contRelateFromCust.setVisible(false);
		
		this.kdtEntrys.getColumn("revAccountBank").getStyleAttributes().setHided(true);
		this.kdtEntrys.getColumn("settlementType").getStyleAttributes().setHided(true);
		this.kdtEntrys.getColumn("settlementNumber").getStyleAttributes().setHided(true);
		
		this.prmtroom.setEnabled(false);
		this.kdSelectRefundDetail.setVisible(false);
    	this.kDSelectTransDetail.setVisible(false);
    	
    }
    private void dealAmount(){
    	boolean isLock=true;
    	for(int i=0;i<this.tblRev.getRowCount();i++){
    		if(RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())){
    			if(((Boolean)this.tblRev.getRow(i).getCell("isSelect").getValue()).booleanValue()){
        			isLock=false;
        		}
        	}else{
        		if(((Boolean)this.tblRev.getRow(i).getCell("isSelect").getValue()).booleanValue()){
        			isLock=false;
        		}
        	}
    	}
    	if(isLock){
    		if(RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())){
    			this.kdtEntrys.getColumn("amount").getStyleAttributes().setLocked(true);
    		}else{
    			this.kdtEntrys.getColumn("revAmount").getStyleAttributes().setLocked(true);
    		}
    	}else{
    		if(RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())){
    			this.kdtEntrys.getColumn("amount").getStyleAttributes().setLocked(false);
    		}else{
    			this.kdtEntrys.getColumn("revAmount").getStyleAttributes().setLocked(false);
    		}
    	}
    	
    }
    
    protected void tblRev_editStopped(KDTEditEvent e) throws Exception {
    	dealAmount();
    	CRMClientHelper.getFootRow(this.tblRev, new String[]{"amount","quitAmount","canAmount","revAmount","canQuit"});
	}

	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
    	if(e.getType()!=1)  return;
    	if(e.getColIndex()==this.kdtEntrys.getColumnIndex("moneyDefine")){
    		MoneyDefineInfo md=(MoneyDefineInfo) this.kdtEntrys.getRow(e.getRowIndex()).getCell("moneyDefine").getValue();
    		if(md!=null&&this.prmtroom.getValue()==null){
    			if(!md.getMoneyType().equals(MoneyTypeEnum.PreMoney)&&!md.getMoneyType().equals(MoneyTypeEnum.SinPur)){
    				FDCMsgBox.showWarning(this,"�޷���ֻ���ճ�������Ԥ�տ");
    				this.kdtEntrys.getRow(e.getRowIndex()).getCell("moneyDefine").setValue(null);
    			}
    		}
    	}else if(e.getColIndex()==this.kdtEntrys.getColumnIndex("invoiceType")){
    		if(this.kdtEntrys.getRow(e.getRowIndex()).getCell("invoiceType").getValue()!=null){
    			InvoiceTypeEnum type=(InvoiceTypeEnum)this.kdtEntrys.getRow(e.getRowIndex()).getCell("invoiceType").getValue();
    			if(type.equals(InvoiceTypeEnum.INVOICE)){
    				this.kdtEntrys.getRow(e.getRowIndex()).getCell("invoiceNumber").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
    				this.kdtEntrys.getRow(e.getRowIndex()).getCell("receiptNumber").getStyleAttributes().setLocked(true);
    				this.kdtEntrys.getRow(e.getRowIndex()).getCell("invoiceNumber").getStyleAttributes().setLocked(false);
    				this.kdtEntrys.getRow(e.getRowIndex()).getCell("receiptNumber").setValue(null);
    			}else if(type.equals(InvoiceTypeEnum.RECEIPT)){
    				this.kdtEntrys.getRow(e.getRowIndex()).getCell("receiptNumber").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
    				this.kdtEntrys.getRow(e.getRowIndex()).getCell("invoiceNumber").getStyleAttributes().setLocked(true);
    				this.kdtEntrys.getRow(e.getRowIndex()).getCell("receiptNumber").getStyleAttributes().setLocked(false);
    				this.kdtEntrys.getRow(e.getRowIndex()).getCell("invoiceNumber").setValue(null);
    			}else if(type.equals(InvoiceTypeEnum.CHANGE)||type.equals(InvoiceTypeEnum.QUIT)){
    				this.kdtEntrys.getRow(e.getRowIndex()).getCell("invoiceNumber").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
    				this.kdtEntrys.getRow(e.getRowIndex()).getCell("receiptNumber").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
    				this.kdtEntrys.getRow(e.getRowIndex()).getCell("invoiceNumber").getStyleAttributes().setLocked(false);
    				this.kdtEntrys.getRow(e.getRowIndex()).getCell("receiptNumber").getStyleAttributes().setLocked(false);
    			}
    		}
    	}
    	if(e.getColIndex()==this.kdtEntrys.getColumnIndex("amount")){
    		if(RevBillTypeEnum.gathering.equals(revBillType.getSelectedItem())){
    			BigDecimal amount=this.kdtEntrys.getRow(e.getRowIndex()).getCell("amount").getValue()==null?FDCHelper.ZERO:(BigDecimal)this.kdtEntrys.getRow(e.getRowIndex()).getCell("amount").getValue();
    			BigDecimal revAmount=this.kdtEntrys.getRow(e.getRowIndex()).getCell("appRevAmount").getValue()==null?FDCHelper.ZERO:(BigDecimal)this.kdtEntrys.getRow(e.getRowIndex()).getCell("appRevAmount").getValue();
    			this.kdtEntrys.getRow(e.getRowIndex()).getCell("revAmount").setValue(revAmount.subtract(amount));
    		}else if(RevBillTypeEnum.refundment.equals(revBillType.getSelectedItem())){
    			this.kdtEntrys.getRow(e.getRowIndex()).getCell("revAmount").setValue(this.kdtEntrys.getRow(e.getRowIndex()).getCell("amount").getValue()==null?FDCHelper.ZERO:
    				FDCHelper.ZERO.subtract((BigDecimal)this.kdtEntrys.getRow(e.getRowIndex()).getCell("amount").getValue()));
    		}
    		else if(RevBillTypeEnum.transfer.equals(revBillType.getSelectedItem())){
    			this.kdtEntrys.getRow(e.getRowIndex()).getCell("revAmount").setValue(this.kdtEntrys.getRow(e.getRowIndex()).getCell("amount").getValue());
    		}
    		addAllEntryRevAmountToTop();
    	}else if(e.getColIndex()==this.kdtEntrys.getColumnIndex("revAmount")){
    		
			BigDecimal amount=this.kdtEntrys.getRow(e.getRowIndex()).getCell("amount").getValue()==null?FDCHelper.ZERO:(BigDecimal)this.kdtEntrys.getRow(e.getRowIndex()).getCell("amount").getValue();
			BigDecimal revAmount=this.kdtEntrys.getRow(e.getRowIndex()).getCell("revAmount").getValue()==null?FDCHelper.ZERO:(BigDecimal)this.kdtEntrys.getRow(e.getRowIndex()).getCell("revAmount").getValue();
			this.kdtEntrys.getRow(e.getRowIndex()).getCell("appRevAmount").setValue(revAmount.add(amount));
    		addAllEntryRevAmountToTop();
    		
    	}else if(e.getColIndex()==this.kdtEntrys.getColumnIndex("settlementCount")){
    		Integer oldValue = (Integer)e.getOldValue();
    		if(oldValue==null || oldValue.intValue()==0) 
    			oldValue = new Integer(1);
    		Integer newValue = (Integer)e.getValue();
    		if(newValue==null || newValue.intValue()==0) 
    			newValue = new Integer(1);
    		
    		int changCount = newValue.intValue() - oldValue.intValue();
    		if(changCount==0) return;
    		
    		if(changCount>0) {	//������
    			IRow currRow = this.kdtEntrys.getRow(e.getRowIndex());
    			Object usObj = currRow.getCell("settlementCount").getUserObject();
    			if(usObj==null) 	{
    				String settleFlagStr = String.valueOf(System.currentTimeMillis()); //��Ϊ�����ʶ
    				usObj = settleFlagStr;
    				currRow.getCell("settlementCount").setUserObject(settleFlagStr);
    			}

    			for (int i = 0; i < changCount; i++) {
        			IRow addRow = this.kdtEntrys.addRow(e.getRowIndex());
        			addRow.setUserObject(new SHERevBillEntryInfo());
        			addRow.getCell("settlementCount").setUserObject(usObj);
        			addRow.getCell("moneyDefine").setValue(currRow.getCell("moneyDefine").getValue());
				}
    			
    			this.kdtEntrys.getMergeManager().mergeBlock(e.getRowIndex(), e.getColIndex(), e.getRowIndex()+newValue.intValue()-1, e.getColIndex());	
    			this.kdtEntrys.getMergeManager().mergeBlock(e.getRowIndex(), e.getColIndex()-1, e.getRowIndex()+newValue.intValue()-1, e.getColIndex()-1);
    		}else{			//ɾ����
    			for(int i=0;i<(-changCount);i++){
    				this.kdtEntrys.removeRow(e.getRowIndex()+oldValue.intValue()-1-i);
    			}
    		}    		
    	}
    }
    
    
    private void addAllEntryRevAmountToTop(){
		BigDecimal allRevAmount = new BigDecimal("0");
		for(int i=0;i<this.kdtEntrys.getRowCount();i++){
			IRow thisRow = this.kdtEntrys.getRow(i);
			BigDecimal revAmount = (BigDecimal)thisRow.getCell("revAmount").getValue();
			if(revAmount==null) revAmount = new BigDecimal("0");
			allRevAmount = allRevAmount.add(revAmount);
		}
		this.txtrevAmount.setValue(allRevAmount);	//�տ������
    }
    
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
		if(this.getOprtState().equals(OprtState.ADDNEW)) {
			IRow addRow = this.kdtEntrys.getRow(this.kdtEntrys.getRowCount()-1);
			addRow.getCell("settlementCount").setValue(new Integer(1));
		}
    }
    
    public void actionInsertLine_actionPerformed(ActionEvent e)
    		throws Exception {
    	//��ǰ�м���һ�ж��Ƕ��кϲ���(�����ʶһ��)����������Ҫ��һ
    	boolean isAddCount = false;
    	Object settleFlagStr = null;
		if(this.getOprtState().equals(OprtState.ADDNEW)) {
			IRow selectRow = KDTableUtil.getSelectedRow(this.kdtEntrys);
			if(selectRow!=null && selectRow.getRowIndex()>0) { 
				IRow upRow = this.kdtEntrys.getRow(selectRow.getRowIndex()-1);
				Object upFlag = upRow.getCell("settlementCount").getUserObject();
				Object selFlag = selectRow.getCell("settlementCount").getUserObject();
				if(upFlag!=null && selFlag!=null && upFlag.equals(selFlag)) {
					settleFlagStr = upFlag;
					//ֻ�жԷ����ʶ�еĵ�һ�е�ֵ�����ǻ���Ч��
					IRow firstRow = upRow;
					while(firstRow.getRowIndex()>0){
						IRow tempRow = this.kdtEntrys.getRow(firstRow.getRowIndex()-1);
						Object tempObj = tempRow.getCell("settlementCount").getUserObject();
						if(tempObj==null || !tempObj.equals(settleFlagStr)) break;
						firstRow = tempRow;
					}
					Integer setlCount = (Integer)firstRow.getCell("settlementCount").getValue();
					firstRow.getCell("settlementCount").setValue(new Integer(setlCount.intValue()+1));
					isAddCount = true;
				}	
			}
		}
    	
    	super.actionInsertLine_actionPerformed(e);
    	IRow selectRow = KDTableUtil.getSelectedRow(this.kdtEntrys);    
    	if(selectRow!=null) {
	    	if(isAddCount){
	    		selectRow.getCell("settlementCount").setUserObject(settleFlagStr);
	    	}else{
	    		selectRow.getCell("settlementCount").setValue(new Integer(1));
	    	}
    	}

    }
    
    public void actionRemoveLine_actionPerformed(ActionEvent e)
    		throws Exception {
    	IRow selectRow = KDTableUtil.getSelectedRow(this.kdtEntrys);
    	if(selectRow==null) return;
    	boolean isDeleteCount = false;
    	Object settleFlagStr = selectRow.getCell("settlementCount").getUserObject();
    	
		IRow firstRow = selectRow;
		while(firstRow.getRowIndex()>0){
			IRow tempRow = this.kdtEntrys.getRow(firstRow.getRowIndex()-1);
			Object tempObj = tempRow.getCell("settlementCount").getUserObject();
			if(tempObj==null || !tempObj.equals(settleFlagStr)) break;
			firstRow = tempRow;
		}
    	
    	Integer setlCount = (Integer)firstRow.getCell("settlementCount").getValue(); 
    	if(setlCount==null || setlCount.intValue()==0) setlCount = new Integer(1);
    	if(setlCount.intValue()>1){
    		isDeleteCount = true;			
    	}
    	
    	super.actionRemoveLine_actionPerformed(e);
    	
    	if(isDeleteCount){
    		for(int i=0;i<this.kdtEntrys.getRowCount();i++) {
    			IRow tempRow = this.kdtEntrys.getRow(i);
    			Object tempObj = tempRow.getCell("settlementCount").getUserObject();
    			if(tempObj!=null && tempObj.equals(settleFlagStr)){
    				tempRow.getCell("settlementCount").setValue(new Integer(setlCount.intValue()-1));
    				break;
    			}
    		}
    	}
    	addAllEntryRevAmountToTop();
    }
    
    protected void kdSelectRefundDetail_actionPerformed(ActionEvent e)
    		throws Exception {
		Object[] custObjs = (Object[])this.prmtCustomerEntry.getValue();
		if(custObjs==null || custObjs.length==0 || custObjs[0]==null){			
			FDCMsgBox.showWarning("�ͻ�������ѡ��");
			return;			 
		}    	
    

		UIContext uiContext = new UIContext(this);	
		CRMHelper.sortCollection(custObjs, "name", true);
		uiContext.put("CustomerObjs", custObjs);
		BaseTransactionInfo relateBillInfo = (BaseTransactionInfo)this.prmtRelateBizBill.getValue();
		uiContext.put("RelateBillInfo", relateBillInfo);
		uiContext.put("CurrRevBillInfo", this.editData);	
		
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)//��ת����ϸѡ�񴰿�
								.create(SelectRefundDetailUI.class.getName(), uiContext);
			uiWindow.show();
			//����ת���ʵ����ϸ
			Map resContext = uiWindow.getUIObject().getUIContext();
			SHERevBillEntryCollection retObjColl = (SHERevBillEntryCollection)resContext.get("RetObjColl");
			if(retObjColl!=null) {
				this.kdtEntrys.removeRows();
				for(int i=0;i<retObjColl.size();i++){
					SHERevBillEntryInfo retObjInfo = (SHERevBillEntryInfo)retObjColl.get(i);
					retObjInfo.setParent(this.editData);
					IRow addRow = this.kdtEntrys.addRow();
					addRow.setUserObject(retObjInfo);
					addRow.getCell("moneyDefine").setValue(retObjInfo.getMoneyDefine());
					addRow.getCell("revAmount").setValue(retObjInfo.getRevAmount());
					addRow.getCell("transferFromEntryId").setValue(retObjInfo.getTransferFromEntryId());
					
					addRow.getCell("hasRefundmentAmount").setValue(retObjInfo.getHasRefundmentAmount());
					addRow.getCell("hasTransferAmount").setValue(retObjInfo.getHasTransferAmount());
					addRow.getCell("leftAmount").setValue(retObjInfo.getRemainAmount());
				}
				addAllEntryRevAmountToTop();
			}
		} catch (UIException e1) {
			e1.printStackTrace();
		}
    
    }
    
    protected void kDSelectTransDetail_actionPerformed(ActionEvent e)
    		throws Exception {
		Object[] custObjs = (Object[])this.prmtCustomerEntry.getValue();
		if(custObjs==null || custObjs.length==0 || custObjs[0]==null){			
			FDCMsgBox.showWarning("�ͻ�������ѡ��");
			return;			 
		}    	

		BaseTransactionInfo reltInfo = (BaseTransactionInfo)this.prmtRelateBizBill.getValue();		
		/*		if(reltInfo==null){ //ת�뵥��һ��Ҫѡ��
			FDCMsgBox.showWarning("�������ݱ���ѡ��");
			return;
		}		*/
		
		UIContext uiContext = new UIContext(this);
		BaseTransactionInfo relateFromInfo = (BaseTransactionInfo)this.prmtRelateFromBizBill.getValue();
		SHECustomerInfo reltCustInfo = (SHECustomerInfo)this.prmtRelateFromCust.getValue();
		if(relateFromInfo==null && reltCustInfo==null){ 
			FDCMsgBox.showWarning("ת���������ݺ�ת������ͻ�����ѡ��һ����");
			return;
		}				
		
		uiContext.put("RelateBizBillInfo", reltInfo);
		uiContext.put("RelateFromBizBillInfo", relateFromInfo);
		uiContext.put("RelateFromCustInfo", reltCustInfo);
		CRMHelper.sortCollection(custObjs, "name", true);
		uiContext.put("CustomerObjs", custObjs);
		
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)//��ת����ϸѡ�񴰿�
								.create(SelectTransfDetailUI.class.getName(), uiContext);
			uiWindow.show();
			
			//����ת���ʵ����ϸ
			Map resContext = uiWindow.getUIObject().getUIContext();
			SHERevBillEntryCollection retObjColl = (SHERevBillEntryCollection)resContext.get("RetObjColl");
			
			if(retObjColl!=null) {
				this.kdtEntrys.removeRows();
				for(int i=0;i<retObjColl.size();i++){
					SHERevBillEntryInfo retObjInfo = (SHERevBillEntryInfo)retObjColl.get(i);
					retObjInfo.setParent(this.editData);
					IRow addRow = this.kdtEntrys.addRow();
					addRow.setUserObject(retObjInfo);
					addRow.getCell("moneyDefine").setValue(retObjInfo.getMoneyDefine());
					addRow.getCell("revAmount").setValue(retObjInfo.getRevAmount());
					addRow.getCell("transferFromEntryId").setValue(retObjInfo.getTransferFromEntryId());
					if(retObjInfo.getTransferFromEntryId()!=null){
						addRow.getStyleAttributes().setBackground(new Color(0xFCFBDF));
						addRow.getCell("moneyDefine").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
						addRow.getCell("revAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
					}else{
						addRow.getCell("hasRefundmentAmount").setValue(retObjInfo.getHasRefundmentAmount());
						addRow.getCell("hasTransferAmount").setValue(retObjInfo.getHasTransferAmount());
						addRow.getCell("leftAmount").setValue(retObjInfo.getRemainAmount());
					}
				}
				addAllEntryRevAmountToTop();
			}
		} catch (UIException e1) {
			e1.printStackTrace();
		}
    
    }
    
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	if(this.txtNumber.isEditable()  &&  this.txtNumber.isEnabled()  &&  StringUtils.isEmpty(this.txtNumber.getText())){
			FDCMsgBox.showInfo(this, "���벻��Ϊ�գ�");
			this.abort();	
		}    	
		Object[] custObjs = (Object[])this.prmtCustomerEntry.getValue();
		if(custObjs==null || custObjs.length==0) {
			FDCMsgBox.showInfo(this, "�ͻ�����Ϊ�գ�");
			this.abort();	
		}
        if(this.prmtRelateBizBill.getValue()==null) {
        	if(custObjs.length>1) {
    			FDCMsgBox.showInfo(this, "ֻ��ѡ��һλԤ�տ�ͻ���");
    			this.abort();	
    		} 
        }
		if(this.prmtSettlementType.getValue()==null){
			FDCMsgBox.showInfo(this, "���㷽ʽ����Ϊ�գ�");
			this.abort();
		}
		
		if(this.kdtEntrys.getRowCount()==0){
			FDCMsgBox.showInfo(this, "��ϸ�б���Ϊ�գ�");
			this.abort();	
		}
		if(this.prmtRevAccount.getValue()==null){
			FDCMsgBox.showInfo(this, "�տ��Ŀ����Ϊ�գ�");
			this.abort();	
		}
		ChequeDetailEntryInfo invoiceInfo = (ChequeDetailEntryInfo)this.prmtInvoice.getValue();
		if(invoiceInfo!=null) {
			 BigDecimal revAllAmount = (BigDecimal)this.txtrevAmount.getBigDecimalValue();
			 if(revAllAmount==null) revAllAmount = new BigDecimal("0");
			 if(invoiceInfo.getCheque()!=null){
				 CRMChequeInfo crmCheqInfo = CRMChequeFactory.getRemoteInstance().getCRMChequeInfo("select limitAmount where id = '"+invoiceInfo.getCheque().getId()+"'");
				 if(crmCheqInfo.getLimitAmount()!=null && revAllAmount.compareTo(crmCheqInfo.getLimitAmount())>0){
					 FDCMsgBox.showWarning("��Ʊ���ܴ���Ʊ���޶");
						this.abort();
			 	}
			 }
		}		
		
		SellProjectInfo currSellProInfo = (SellProjectInfo)this.prmtsellProject.getValue();
		if(currSellProInfo==null){
			FDCMsgBox.showInfo(this, "��Ŀ����Ϊ�գ�");
			this.abort();	
		}
		
		boolean isMustBank = false;		//������������зſ��������а��ҿ�͹������տ�
		RevBillTypeEnum currRevBillType = (RevBillTypeEnum)this.revBillType.getSelectedItem();
		if(currRevBillType.equals(RevBillTypeEnum.transfer)){ //ת��ʱת����ת�뵥�ݲ���һ�����ͻ�Ҳ�ǲ���һ��
	        BaseTransactionInfo relateBillInfo = (BaseTransactionInfo)this.prmtRelateBizBill.getValue();
	        BaseTransactionInfo relateFrommBillInfo = (BaseTransactionInfo)this.prmtRelateFromBizBill.getValue();
			if(relateBillInfo!=null && relateFrommBillInfo!=null){
				if(relateBillInfo.getId().equals(relateFrommBillInfo.getId())) {
					FDCMsgBox.showInfo(this, "ת��й������ݺ�ת���������ݲ�����ͬ��");
					this.abort();						
				}
			}
	        if(relateBillInfo==null && relateFrommBillInfo==null){
	        	SHECustomerInfo reltCustInfo = (SHECustomerInfo)this.prmtRelateFromCust.getValue();
	        	if(reltCustInfo!=null && custObjs.length==1){
	        		SHECustomerInfo toCustInfo = (SHECustomerInfo)custObjs[0];
	        		if(reltCustInfo.getId().equals(toCustInfo.getId())){
						FDCMsgBox.showInfo(this, "ת��пͻ���ת������ͻ�������ͬ��");
						this.abort();			
	        		}
	        	}
	        }
		}else if(currRevBillType.equals(RevBillTypeEnum.gathering)){	
        	Map detailSet = RoomDisplaySetting.getNewProjectSet(null,currSellProInfo.getId().toString());
        	isMustBank = (detailSet==null)?false:((Boolean)detailSet.get(SHEParamConstant.T2_IS_MUST_BY_BANK)).booleanValue();
		}
		
		
		RevBillTypeEnum revBillType = (RevBillTypeEnum)this.revBillType.getSelectedItem();
		for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow currRow = this.kdtEntrys.getRow(i);
			MoneyDefineInfo defineInfo = (MoneyDefineInfo)currRow.getCell("moneyDefine").getValue();
			if(defineInfo==null){
				Object settleFlagStr = currRow.getCell("settlementCount").getUserObject();
				IRow firstRow = currRow;
				while(firstRow.getRowIndex()>0){
					IRow tempRow = this.kdtEntrys.getRow(firstRow.getRowIndex()-1);
					Object tempObj = tempRow.getCell("settlementCount").getUserObject();
					if(tempObj==null || !tempObj.equals(settleFlagStr)) break;
					firstRow = tempRow;
				}				
				if(firstRow!=null && firstRow.getRowIndex()!=currRow.getRowIndex()) {
					if(firstRow.getCell("moneyDefine").getValue()==null){
						MsgBox.showInfo(this, "�շѿ����Ϊ�գ�");
						this.abort();
					}else{
						currRow.getCell("moneyDefine").setValue(firstRow.getCell("moneyDefine").getValue());
					}
				}
			}
			
			if(isMustBank){
				if(defineInfo.getMoneyType().equals(MoneyTypeEnum.LoanAmount) || defineInfo.getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
					MsgBox.showInfo(this, "���������ͱ������зſ����˿�����а��ҿ�򹫻���");
					this.abort();		
				}
			}
			
			BigDecimal revAmount = (BigDecimal)currRow.getCell("revAmount").getValue();
			BigDecimal appRevAmount = (BigDecimal)currRow.getCell("appRevAmount").getValue();
			if(RevBillTypeEnum.gathering.equals(revBillType)){
				if(appRevAmount==null || appRevAmount.compareTo(FDCHelper.ZERO)==0) {
					MsgBox.showInfo(this, "�տ����Ϊ�ջ���0��");
					this.abort();				
				}	
			}else if(RevBillTypeEnum.refundment.equals(revBillType)){
				if(revAmount==null || revAmount.compareTo(new BigDecimal("0"))>=0) {
					MsgBox.showInfo(this, "�˿������С��0��");
					this.abort();				
				}					
			}
			
//			if(currRow.getCell("invoiceType").getValue()!=null){
//    			InvoiceTypeEnum type=(InvoiceTypeEnum)currRow.getCell("invoiceType").getValue();
//    			if(type.equals(InvoiceTypeEnum.INVOICE)){
//    				if(currRow.getCell("invoiceNumber").getValue()==null){
//    					MsgBox.showInfo(this, "��Ʊ�Ų���Ϊ�գ�");
//    					this.abort();	
//    				}
//    				if(currRow.getCell("invoiceNumber").toString().trim().equals("")){
//    					MsgBox.showInfo(this, "��Ʊ�Ų���Ϊ�գ�");
//    					this.abort();	
//    				}
//    			}else if(type.equals(InvoiceTypeEnum.RECEIPT)){
//    				if(currRow.getCell("receiptNumber").getValue()==null){
//    					MsgBox.showInfo(this, "�վݺŲ���Ϊ�գ�");
//    					this.abort();	
//    				}
//    				if(currRow.getCell("receiptNumber").toString().trim().equals("")){
//    					MsgBox.showInfo(this, "�վݺŲ���Ϊ�գ�");
//    					this.abort();	
//    				}
//    			}else if(type.equals(InvoiceTypeEnum.CHANGE)||type.equals(InvoiceTypeEnum.QUIT)){
//    				if(currRow.getCell("invoiceNumber").getValue()==null){
//    					MsgBox.showInfo(this, "��Ʊ�Ų���Ϊ�գ�");
//    					this.abort();	
//    				}
//    				if(currRow.getCell("invoiceNumber").toString().trim().equals("")){
//    					MsgBox.showInfo(this, "��Ʊ�Ų���Ϊ�գ�");
//    					this.abort();	
//    				}
//    				if(currRow.getCell("receiptNumber").getValue()==null){
//    					MsgBox.showInfo(this, "�վݺŲ���Ϊ�գ�");
//    					this.abort();	
//    				}
//    				if(currRow.getCell("receiptNumber").toString().trim().equals("")){
//    					MsgBox.showInfo(this, "�վݺŲ���Ϊ�գ�");
//    					this.abort();	
//    				}
//    			}
//    		}else{
//    			MsgBox.showInfo(this, "��Ʊ״̬����Ϊ�գ�");
//				this.abort();	
//    		}
		}
    	
    	this.chkMenuItemSubmitAndPrint.setSelected(false);
    	
    	BigDecimal allrevAmount = FDCHelper.ZERO;
    	BigDecimal allAmount = FDCHelper.ZERO;
    	BigDecimal all = FDCHelper.ZERO;
    	
    	String coloum="amount";
    	String cancoloum="canAmount";
         
     	if(RevBillTypeEnum.refundment.equals(this.revBillType.getSelectedItem())){
     		coloum="quitAmount";
     		cancoloum="canQuit";
 	   	}
     	
    	for (int i = 0; i < this.tblRev.getRowCount(); i++) {
    		if(((Boolean)this.tblRev.getRow(i).getCell("isSelect").getValue()).booleanValue()
    				&&this.tblRev.getRow(i).getCell(coloum).getValue()!=null){
    			BigDecimal can=(BigDecimal)this.tblRev.getRow(i).getCell(cancoloum).getValue();
    			BigDecimal amount=(BigDecimal)this.tblRev.getRow(i).getCell(coloum).getValue();
    			if(RevBillTypeEnum.refundment.equals(this.revBillType.getSelectedItem())){
    				can=can.negate();
    				amount=amount.negate();
    			}
    			if(amount.compareTo(can)>0){
    				if(RevBillTypeEnum.refundment.equals(this.revBillType.getSelectedItem())){
    					MsgBox.showInfo(this, "�����˿�����ڿ��˿��");
        				this.abort();
    				}else{
    					MsgBox.showInfo(this, "���γ�ֽ����ڿɳ�ֽ�");
        				this.abort();	
    				}
    			}
    			allrevAmount=allrevAmount.add((BigDecimal)this.tblRev.getRow(i).getCell(coloum).getValue());
    		}
    	}
    	for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
    		if(RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())){
    			allAmount=allAmount.add(this.kdtEntrys.getRow(i).getCell("amount").getValue()==null?FDCHelper.ZERO:(BigDecimal)this.kdtEntrys.getRow(i).getCell("amount").getValue());
    		}else{
    			allAmount=allAmount.add(this.kdtEntrys.getRow(i).getCell("revAmount").getValue()==null?FDCHelper.ZERO:(BigDecimal)this.kdtEntrys.getRow(i).getCell("revAmount").getValue());
    		}
    		all=all.add(this.kdtEntrys.getRow(i).getCell("appRevAmount").getValue()==null?FDCHelper.ZERO:(BigDecimal)this.kdtEntrys.getRow(i).getCell("appRevAmount").getValue());
		}
		if(allrevAmount.compareTo(allAmount)!=0){
    		if(RevBillTypeEnum.gathering.equals(revBillType)){
    			MsgBox.showInfo(this, "���γ�ֽ���ܶ���ڳ�ֽ���ܶ");
				this.abort();		
    		}else if(RevBillTypeEnum.refundment.equals(revBillType)){
    			MsgBox.showInfo(this, "�����˿����ܶ�����˿����ܶ");
				this.abort();		
    		}
    	}
    	this.editData.setOriginalAmount(all);
    	  
    	if(PM!=null&&RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())){
    		this.editData.setIsTansCreate(true);
    		this.editData.setIsGathered(true);
			if(((BigDecimal)PM.get("PMAmount")).compareTo(this.txtrevAmount.getBigDecimalValue())!=0){
				MsgBox.showInfo(this, "���ҵ���˿��ܶ�����տ��ܶ");
				this.abort();
			}
    	}
    	if(PM!=null&&RevBillTypeEnum.refundment.equals(this.revBillType.getSelectedItem())){
    		this.editData.setIsTansCreate(true);
    		this.editData.setIsGathered(true);
    	}
    	
    	String cerNum = this.txtCertificateNumber.getText().trim();
		if (cerNum != null && !cerNum.equals("") && (cerNum.length() != 15 && cerNum.length() != 18)) {
			if(!(FDCMsgBox.showConfirm2(this, "�ͻ�֤�������쳣���Ƿ������")== FDCMsgBox.YES)){
    			SysUtil.abort();
    		}
		}
		if(PM==null){
			if(this.txtrevAmount.getBigDecimalValue()==null||this.txtrevAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO)==0){
				this.editData.setIsGathered(true);
			}else{
				this.editData.setIsGathered(false);
			}
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSubmit_actionPerformed(e);
    	
    	if(PM!=null&&RevBillTypeEnum.refundment.equals(this.revBillType.getSelectedItem())){
  			BigDecimal amount=this.txtrevAmount.getBigDecimalValue().negate();
  			PM.put("PMAmount", amount);
  			setOprtState(STATUS_VIEW);
  			this.destroyWindow();
  		}
    	if(PM!=null&&RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())){
    		PM.put("PMAmount", null);
    		setOprtState(STATUS_VIEW);
    	}
    	this.kdtEntrys.getColumn("settlementCount").getStyleAttributes().setHided(true);
    	
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		handleOldData();
    }
 
    
    public boolean destroyWindow() {
    	
    	if(PM!=null&&RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())&&PM.get("PMAmount")!=null){
			MsgBox.showInfo(this, "��������ҵ��������Ѿ�����˿�����������ж�Ӧ�տ������");
			this.abort();	
    	}
    	return super.destroyWindow();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getId()!=null){
    		if(this.editData.getState()==null || this.editData.getState().equals(FDCBillStateEnum.SAVED)
    				|| this.editData.getState().equals(FDCBillStateEnum.SUBMITTED) ){
    			if(this.editData.getRelateBizType()!=null && this.editData.getRelateBizType().equals(RelatBizType.Change)){
    				FDCMsgBox.showWarning("�˵����ɱ�������ɣ���ֹ�޸ģ�");
    			}else{
    		    	if(this.editData.getRevBillType()!=null && this.editData.getRevBillType().equals(RevBillTypeEnum.gathering)) {
    			    	boolean isQuitOrTrans = SHERevBillEntryFactory.getRemoteInstance().exists(" where hasRefundmentAmount > 0 and hasTransferAmount >0 and parent.id = '"+this.editData.getId()+"' ");
    			    	if(isQuitOrTrans){
    			    		FDCMsgBox.showWarning("���տ���ѷ����˿��ת���ֹ�޸ģ�");
    			    		return;    		
    			    	}
    		    	}
    				
    		    	if(this.editData.isIsTansCreate()){
    					FDCMsgBox.showWarning("�˵������ڱ��ҵ���������ֹ�޸ģ�");
    					return;
    				}	
    		    	
    				super.actionEdit_actionPerformed(e);
    				
    				if(RevBillTypeEnum.transfer.equals(this.editData.getRevBillType())){
    					
    				}else if(RevBillTypeEnum.refundment.equals(this.editData.getRevBillType())){
    					this.kdAddnewLine.setEnabled(true);
    					this.kdInserLine.setEnabled(true);
    					this.kdRemoveLine.setEnabled(true);
    				}else if(RevBillTypeEnum.gathering.equals(this.editData.getRevBillType())){
    					this.kdAddnewLine.setEnabled(true);
    					this.kdInserLine.setEnabled(true);
    					this.kdRemoveLine.setEnabled(true);
    				}
    			}
    		}else{
    			FDCMsgBox.showWarning("��ǰ���ݷǱ�����ύ״̬����ֹ�޸ģ�");
    		}
    	}
    	if(RevBillTypeEnum.refundment.equals(this.revBillType.getSelectedItem())&&PM!=null){
        	this.kdtEntrys.getColumn("revAmount").getStyleAttributes().setLocked(true);
        	this.kdtEntrys.getColumn("settlementCount").getStyleAttributes().setLocked(true);
        	
        	this.actionAddLine.setEnabled(false);
        	this.actionInsertLine.setEnabled(false);
        	this.actionRemoveLine.setEnabled(false);
        	
        	PM.put("PMAmount", null);
        }
        
        if(RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())){
        	for(int i=0;i<this.kdtEntrys.getRowCount();i++){
        		IRow crrRow = this.kdtEntrys.getRow(i);
        		SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)crrRow.getUserObject();
        		if(revEntryInfo.getAppRevAmount().compareTo(FDCHelper.ZERO)<0
        				&&RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())&&revEntryInfo.getMoneyDefine()!=null
        				&&revEntryInfo.getMoneyDefine().getName().equals("��������")){
        			this.actionAddLine.setEnabled(false);
        			this.actionInsertLine.setEnabled(false);
        			this.actionRemoveLine.setEnabled(false);
        			this.kdtEntrys.getRow(i).getCell("moneyDefine").getStyleAttributes().setLocked(true);
        			this.kdtEntrys.getRow(i).getCell("revAmount").getStyleAttributes().setLocked(true);
        		}
        	}
        }
    }
    
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getId()!=null){
			if(this.editData.getRelateBizType()!=null && this.editData.getRelateBizType().equals(RelatBizType.Change)){
				FDCMsgBox.showWarning("�˵����ɱ�������ɣ���ֹɾ����");
				return;
			}
			if(this.editData.isIsTansCreate()){
				FDCMsgBox.showWarning("�˵������ڱ��ҵ���������ֹɾ����");
				return;
			}			
    		if(this.editData.getState()!=null && !this.editData.getState().equals(FDCBillStateEnum.SUBMITTED)
    				 && !this.editData.getState().equals(FDCBillStateEnum.SAVED)) {
	    		FDCMsgBox.showWarning("�˵��ݷǱ�����ύ״̬����ֹɾ����");
	    		return;    	
    		} 
    	}
    	if(editData.isIsGathered()&&editData.getRevAmount()!=null&&editData.getRevAmount().compareTo(FDCHelper.ZERO)!=0){
    		FDCMsgBox.showWarning("�˵����Ѿ��������ɻ��ܵ�����ֹɾ����");
    		return;   
    	}
    	super.actionRemove_actionPerformed(e);
    }
    
    
	protected Map listenersMap = new HashMap();
    
    protected void addDataChangeListener(JComponent com) {
    	
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	} else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}
    	}
		
    }
        
    protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
  	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	} 
    	else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	} 
		
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }    
 
    
    
    public void actionMakeInvoice_actionPerformed(ActionEvent e)
    		throws Exception {
    	if(this.editData.getId()==null) return;
    	
    	SHERevBillEntryCollection revEntryColl = SHERevBillEntryFactory.getRemoteInstance()
			.getSHERevBillEntryCollection("select *,moneyDefine.name,parent.* where parent.id = '"+this.editData.getId()+"' ");

    	UIContext uiContext = new UIContext(this); 		
    	uiContext.put("revBillColl", revEntryColl);
    	uiContext.put("customer", this.prmtCustomerEntry.getValue());    	
    	
    	RoomInfo roomInfo = (RoomInfo)this.prmtroom.getValue();
    	if(roomInfo!=null) uiContext.put("Room", roomInfo);
    	
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
							.create(MakeInvoiceEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
    }
    
    public void actionRecycle_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getId()!=null && (this.editData.getInvoiceNumber()!=null || this.editData.getReceiptNumber()!=null))	{
    		ChequeDetailEntryFactory.getRemoteInstance().clearInvoice(this.editData.getId().toString(), true);
    		
    		FDCMsgBox.showInfo("���Ʊ�ݳɹ� ��");
    		this.editData.setInvoiceNumber(null);
    		this.editData.setReceiptNumber(null);
    	}
    }
    
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.editData.getId()==null) return;

        BOSQueryDelegate data = new CRMDataProvider(this.editData.getId().toString()
        			,"com.kingdee.eas.fdc.basecrm.app.SHERevBillQuery","com.kingdee.eas.fdc.basecrm.app.SHERevBillEntryQuery","parent.id");
        KDNoteHelper appHlp = new KDNoteHelper();
        appHlp.print(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
    }    	

    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.editData.getId()==null) return;
        BOSQueryDelegate data = new CRMDataProvider(this.editData.getId().toString()
    			,"com.kingdee.eas.fdc.basecrm.app.SHERevBillQuery","com.kingdee.eas.fdc.basecrm.app.SHERevBillEntryQuery","parent.id");
        KDNoteHelper appHlp = new KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, SwingUtilities.getWindowAncestor(this));
    }
 
    
   
    public void actionSubmitAudit_actionPerformed(ActionEvent e)
    		throws Exception {
//    	this.editData.put("SubmitAndAudit", "TRUE");
    	this.actionSubmit_actionPerformed(e);
    	this.actionAudit_actionPerformed(e);
    	SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		
    }
    
    
    protected void prmtCustomerEntry_dataChanged(DataChangeEvent e) throws Exception {
    	 if(RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())){
    		 if(this.prmtCustomerEntry.getValue()!=null&&prmtCustomerEntry.getValue() instanceof Object[]){
    			 boolean isLoad=true;
    	        	for(int i=0;i<this.kdtEntrys.getRowCount();i++){
    	        		IRow crrRow = this.kdtEntrys.getRow(i);
    	        		SHERevBillEntryInfo revEntryInfo = (SHERevBillEntryInfo)crrRow.getUserObject();
    	        		if(revEntryInfo.getAppRevAmount().compareTo(FDCHelper.ZERO)<0
    	        				&&RevBillTypeEnum.gathering.equals(this.revBillType.getSelectedItem())&&revEntryInfo.getMoneyDefine()!=null
    	        				&&revEntryInfo.getMoneyDefine().getName().equals("��������")){
    	        			isLoad=false;
    	        		}
    	        	}
    	        	if(isLoad){
    	        		 loadTblRevList((Object[])prmtCustomerEntry.getValue(),(BaseTransactionInfo)this.prmtRelateBizBill.getValue(),this.editData);
    	        	}
    			 if(((Object[])prmtCustomerEntry.getValue())[0]!=null){
    				 this.txtCertificateNumber.setText(((SHECustomerInfo)((Object[])prmtCustomerEntry.getValue())[0]).getCertificateNumber());
    			 }
    		 }
    	 }
	}

	protected void prmtRelateBizBill_dataChanged(DataChangeEvent e)
    		throws Exception {
    	Object billInfo = this.prmtRelateBizBill.getValue();
    	this.prmtroom.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.prmtCustomerEntry.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
    	if(billInfo!=null) {
    		if(billInfo instanceof BaseTransactionInfo){
    			BaseTransactionInfo transInfo = (BaseTransactionInfo)billInfo;
				this.prmtroom.setValue(transInfo.getRoom());
				this.prmtroom.setEnabled(false);
				
				SHECustomerCollection sheCustColl = (SHECustomerCollection)transInfo.get("SHECustomerColl");
				if(sheCustColl!=null){	//�ͻ�һ���ǽ������ȫ���ͻ��������޸�
					this.prmtCustomerEntry.setValue(sheCustColl.toArray());
					String custNamsStr = "";
					for (int i = 0; i < sheCustColl.size(); i++) {
						if(sheCustColl.get(i)!=null)
							custNamsStr += ","+sheCustColl.get(i).getName();
					}
					if(!custNamsStr.equals("")) custNamsStr = custNamsStr.substring(1);
					this.txtpayCustomerName.setText(custNamsStr);
				}    			
    		}
    		this.prmtCustomerEntry.setEnabled(false);
    	}else{
    		this.prmtCustomerEntry.setValue(null);
    		this.prmtCustomerEntry.setEnabled(true);
    		
    		this.prmtroom.setValue(null);
			this.prmtroom.setEnabled(true);
    	}
    }
    
    
    protected void prmtRelateFromBizBill_dataChanged(DataChangeEvent e)
    		throws Exception {
//    	Object billFromInfo = this.prmtRelateFromBizBill.getValue();
//    	this.prmtRelateFromCust.setValue(null);
//    	if(billFromInfo!=null){
//    		this.prmtRelateFromCust.setEnabled(false);
//    	}else{
//    		this.prmtRelateFromCust.setEnabled(true);
//    	}
    }
    
    /**������ѡ��ҵ�񵥾�f7��ֵ�����ƿͻ�f7�Ĺ�������*/
    private void setPromtCustomerFilterView(KDBizPromptBox prmtCustBox,KDBizPromptBox prmtBizBox){
		BaseTransactionInfo relateBillInfo = (BaseTransactionInfo)prmtBizBox.getValue();
		try {				
			if(relateBillInfo==null){
				UserInfo currInfo = SysContext.getSysContext().getCurrentUserInfo();	
				SellProjectInfo rootSpInfo = this.editData.getSellProject();	//��ѡ��Ŀ��Ӧ�ĸ���Ŀ
				if(rootSpInfo!=null) {
					while(rootSpInfo.getLevel()!=1)
						rootSpInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo("select * where id = '"+rootSpInfo.getParent().getId()+"' ");
				}	
				prmtCustBox.setEntityViewInfo(NewCommerceHelper.getPermitCustomerView(rootSpInfo,currInfo));	
			}else{	//ֻ��ѡ����ҵ�񵥾���Ŀͻ�
				String idStr = "'nullnull'";
				ITranCustomerEntry custFacFactory = CRMHelper.getCustmerEntryInterFace(null,relateBillInfo.getId());
				TranCustomerEntryCollection custEntryColl = custFacFactory.getTranCustomerEntryCollection("select customer.id where head.id='"+relateBillInfo.getId()+"' ");
				for (int i = 0; i < custEntryColl.size(); i++) {
					if(custEntryColl.get(i).getCustomer()!=null)
						idStr += ",'" + custEntryColl.get(i).getCustomer().getId()+"'";
				}
				EntityViewInfo custFilterView = new EntityViewInfo();
				FilterInfo custFilter = new FilterInfo();
				custFilterView.setFilter(custFilter);
				custFilter.getFilterItems().add(new FilterItemInfo("id",idStr,CompareType.INNER));
				prmtCustBox.setEntityViewInfo(custFilterView);
			}
			prmtCustBox.getQueryAgent().resetRuntimeEntityView();
			prmtCustBox.setRefresh(true);
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
    }
    
    
    
    
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
		            
		            if(!iCodingRuleManager.isModifiable(editData, companyID))
		            	txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }

	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}
	protected String getTDFileName() {
    	return "/bim/fdc/basecrm/SHERevBill";
	}
	
	/**
	 * ����������
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

		/*
		 * 2008-09-27��������ȡֵȡ���ˣ�Ӧ��ȡFDCBillInfo�й�����ID
		 */
		String currentOrgId = "";//FDCClientHelper.getCurrentOrgId();
			//-- ������֯�»�ȡ�ɱ�����Ϊ�յĴ��� zhicheng_jin 090319 --
		OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
		if(org == null){
			org = SysContext.getSysContext().getCurrentOrgUnit();
		}
		currentOrgId = org.getId().toString();

		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
		.getRemoteInstance();
		/*
		 * 2008-09-27�����������״̬��ֱ�ӷ���
		 * Ȼ��ֱ��жϳɱ����ĺ͵�ǰ��֯�Ƿ���ڱ������
		 */
		if(!STATUS_ADDNEW.equals(this.oprtState)){
			return;
		}
		boolean isExist = true;
		if(currentOrgId.length()==0||!iCodingRuleManager.isExist(editData, currentOrgId))
		{
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if(!iCodingRuleManager.isExist(editData, currentOrgId)) {
				//EditUI�ṩ�˷�������û�е��ã���onload����ã��Ը��ǳ�����loadfields����ĵ��ã��õ���û�д���Ϻ�ѡ��
				isExist = false;
			}
		}
				
		if( isExist ){
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData, currentOrgId);
			if(isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			}
			else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId)) { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
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
				getNumberCtrl().setText(number);
			}

			setNumberTextEnabled();
		}
	}
	//��дEditUI�Ļ�ȡ����
    //ͨ����������ȡ���롣������á�
    protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
        try {
            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
            if (orgId == null || orgId.trim().length() == 0)
            {
//              ��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
                 orgId = OrgConstants.DEF_CU_ID;
            }
            if (iCodingRuleManager.isExist(caller, orgId))
            {
                String number = "";
                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
                { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
                    if (iCodingRuleManager.isUserSelect(caller, orgId))
                    {
                        // �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
                        // KDBizPromptBox pb = new KDBizPromptBox();
                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
                                caller, orgId, null, null);
                        // pb.setSelector(intermilNOF7);
                        // Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
                        Object object = null;
                        if (iCodingRuleManager.isDHExist(caller, orgId))
                        {
                            intermilNOF7.show();
                            object = intermilNOF7.getData();
                        }
                        if (object != null)
                        {
                            number = object.toString();
                        }
                        else
                        {
                            // ���û��ʹ���û�ѡ����,ֱ��getNumber���ڱ���,Ϊʲô����read?����Ϊʹ���û�ѡ����Ҳ��get!
                            number = iCodingRuleManager
                                    .getNumber(caller, orgId);
                        }
                    }
                    else
                    {
                        // ֻ�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
                        number = iCodingRuleManager.readNumber(caller, orgId);
                    }
                }
                else
                {
                    // û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
                	String costCenterId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
                    number =  prepareNumberForAddnew(iCodingRuleManager,editData, orgId,costCenterId, null); 
                    	//iCodingRuleManager.getNumber(caller, orgId);
                }

                // ��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
                prepareNumber(caller, number);
                if (iCodingRuleManager.isModifiable(caller, orgId))
                {
                    //����������û����޸�
                    setNumberTextEnabled();
                }
                return;
            }

            /*
            if (orgId != null && orgId.trim().length() > 0 && iCodingRuleManager.isExist(caller, orgId)) {
                String number = "";

                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId)) //�˴���orgId
                                                                           // �벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
                {
                    //�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
                    number = iCodingRuleManager.readNumber(caller, orgId);
                } else {
                    //û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
                    number = iCodingRuleManager.getNumber(caller, orgId);
                }

                //��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
                prepareNumber(caller, number);

                return;
            } else {
                //��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
                String companyId = getNextCompanyId();

                if (companyId != null && companyId.trim().length() > 0 && iCodingRuleManager.isExist(caller, companyId)) {
                    String number = "";

                    if (iCodingRuleManager.isUseIntermitNumber(caller, companyId)) //�˴���orgId
                                                                                   // �벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
                    {
                        //�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
                        number = iCodingRuleManager.readNumber(caller, companyId);
                    } else {
                        //û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
                        number = iCodingRuleManager.getNumber(caller, companyId);
                    }

                    //��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
                    prepareNumber(caller, number);

                    return;
                }
            }
            */
        } catch (Exception err) {
            //��ȡ�����������ֿ����ֹ�������룡
            handleCodingRuleError(err);

            //�ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
            setNumberTextEnabled();
        }

        //�ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
        setNumberTextEnabled();
    }
    
	
	//��������������ˡ�������ʾ��,��������Ƿ��Ѿ��ظ�
	protected String prepareNumberForAddnew (ICodingRuleManager iCodingRuleManager,SHERevBillInfo info,String orgId,String costerId,String bindingProperty)throws Exception{

		String number = null;
		FilterInfo filter = null;
		int i=0;
		do {
			//��������ظ�����ȡ����
			if(bindingProperty!=null){
				number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
			}else{
				number = iCodingRuleManager.getNumber(editData, orgId);
			}
			
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("number", number));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID.getValue(),CompareType.NOTEQUALS));		
			filter.getFilterItems()
					.add(new FilterItemInfo("saleOrgUnit.id", costerId));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
			}
			i++;
		}while (((ICoreBase)getBizInterface()).exists(filter) && i<1000);
		
		return number;
	}
	
	/**
	 * getNumberByCodingRuleֻ�ṩ�˻�ȡ����Ĺ��ܣ�û���ṩ���õ��ؼ��Ĺ��ܣ�ʵ�ִ˷��������ݱ�������"�Ƿ�������ʾ"�������ñ��뵽�ؼ�
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);

		getNumberCtrl().setText(number);

	}

	protected void setNumberTextEnabled() {

		if(getNumberCtrl() != null) {
			OrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentOrgUnit();
			if(currentCostUnit == null){
				currentCostUnit = SysContext.getSysContext().getCurrentSaleUnit();
			}
			
			if(currentCostUnit != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(this.editData, currentCostUnit.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
			}
		}	
	}

	/**
	 *
	 * ���������ر���ؼ����������ʵ�֣�
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-10-13 <p>
	 */
	protected KDTextField getNumberCtrl(){
		return this.txtNumber;
	}

}