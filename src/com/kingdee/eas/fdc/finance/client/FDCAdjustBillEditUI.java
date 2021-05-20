/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.eas.fdc.finance.FDCAdjustBillEntryCollection;
import com.kingdee.eas.fdc.finance.FDCAdjustBillEntryInfo;
import com.kingdee.eas.fdc.finance.FDCAdjustBillFactory;
import com.kingdee.eas.fdc.finance.FDCAdjustProductEntryCollection;
import com.kingdee.eas.fdc.finance.FDCAdjustProductEntryInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class FDCAdjustBillEditUI extends AbstractFDCAdjustBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCAdjustBillEditUI.class);
    
    /**
     * output class constructor
     */
    public FDCAdjustBillEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void fetchInitData() throws Exception {
    	
    }

    protected ICoreBase getBizInterface() throws Exception {
    	// TODO �Զ����ɷ������
    	return FDCAdjustBillFactory.getRemoteInstance();
    }
    protected KDTable getDetailTable() {
    	return kdtEntrys;
    }
    protected void initListener() {
		//��������ʹ��ѡ��selectchange�Լ���ͷ����Ĺ���
    	//super.initListener();
	}
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	//�ڴ�ʱ��ʾ�Ż��ڽ����ϳ���
    	this.actionDelVoucher.setEnabled(false);
    	this.actionVoucher.setEnabled(false);
//    	this.btnVoucher.setVisible(true);
//    	this.btnDelVoucher.setVisible(true);
//    	this.MenuItemVoucher.setVisible(true);
//    	this.menuItemDelVoucher.setVisible(true);
    	kdtEntrys.checkParsed();
    	//��ʾ��Ʒ����
    	FDCAdjustBillEntryCollection fdcAdjustColl = this.editData.getEntrys();
    	FDCAdjustProductEntryCollection fdcProductColl;
    	String productType;
    	for(int i=0;i<fdcAdjustColl.size();i++){
    		productType = "";
    		fdcProductColl =fdcAdjustColl.get(i).getProductEntrys(); 
    		if(fdcProductColl!=null){
    			for(int j=0;j<fdcProductColl.size();j++){
    				if(fdcProductColl.get(j)!=null && fdcProductColl.get(j).getProductType()!=null){
    					productType += fdcProductColl.get(j).getProductType().getName();
    					if(j+1!=fdcProductColl.size()){
    						productType +="��";
    					}
    				}
    			}
    		}
    		
    		this.kdtEntrys.getRow(i).getCell("productType").setValue(productType);
    	}
    	this.kdtEntrys.getColumn("productType").getStyleAttributes().setHided(false);
    	//����ǳɱ����ģ�����ʾ�ɱ���Ŀ��
    	//���򣬾Ͳ���ʾ�ɱ���Ŀ��
    	if(this.editData.getBoolean("isCost")==true){
    		this.kdtEntrys.getColumn("costAccount").getStyleAttributes().setHided(false);
    	}else{
    		this.kdtEntrys.getColumn("costAccount").getStyleAttributes().setHided(true);
    	}
    	
    	//������¼���������޸�
    	this.kdtEntrys.getStyleAttributes().setLocked(true);
    }
    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic2 = super.getSelectors();
    	sic2.add(new SelectorItemInfo("entrys.productEntrys.*"));
    	sic2.add(new SelectorItemInfo("entrys.productEntrys.productType.*"));
    	sic2.add(new SelectorItemInfo("isCost"));
    	sic2.add(new SelectorItemInfo("entrys.costAccount.name"));
    	sic2.add(new SelectorItemInfo("entrys.isLeaf"));
    	return sic2;
    }
   
    protected void attachListeners() {
		// TODO �Զ����ɷ������
		
	}

	protected void detachListeners() {
		// TODO �Զ����ɷ������
		
	}

	protected KDTextField getNumberCtrl() {
		// TODO �Զ����ɷ������
		return null;
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		this.btnAttachment.setVisible(false);
    	this.MenuItemAttachment.setVisible(false);
    	this.menuItemAudit.setVisible(false);
    	this.btnAudit.setVisible(false);
    	
    	this.prmtContractBill.setEnabled(false);
    	this.prmtPeriod.setEnabled(false);
    	this.prmtVoucher.setEnabled(false);
    	this.txtNumber.setEnabled(false);
    	this.chkFiVouchered.setEnabled(false);
    	

    	this.prmtPeriod.setDisplayFormat("$number$");
    	this.prmtContractBill.setDisplayFormat("$name$");
    	this.prmtVoucher.setDisplayFormat("$number");
	}

	public void loadFields() {
		// TODO �Զ����ɷ������
		super.loadFields();;
		this.actionTraceDown.setEnabled(editData.isFiVouchered());
		setMerge();
	}

	/**
	 * 
	 */
	private void setMerge() {
		KDTable table = this.getDetailTable();
		table.getMergeManager().removeAllMergeBlock();
		//ֱ���ںϼ��� by sxhong 2009-07-31 19:04:47
		table.getMergeManager().mergeBlock(0, 0, table.getRowCount()-1, table.getColumnIndex("curProject"), KDTMergeManager.FREE_ROW_MERGE);
		
		for(int i=0;i<table.getRowCount();i++){
			Boolean isLeaf = (Boolean)table.getRow(i).getCell("isLeaf").getValue();
			if(!isLeaf.booleanValue()){
				table.getRow(i).getCell("costAccount").setValue("�ϼ�");
			}
		}
/*
		int accountMergStart = 0;
		int curProjMergStart = 0;
		int invoiceMergStart = 0;
		String lastAccount = "";
		String lastCurProj = "";
		String lastInvoice = "";
		for(int i=0;i<table.getRowCount();i++){
			Boolean isLeaf = (Boolean)table.getRow(i).getCell("isLeaf").getValue();
			if(!isLeaf.booleanValue()){
				table.getRow(i).getCell("costAccount").setValue("�ϼ�");
			}
			if(lastAccount.length()==0)
			{
				lastAccount = table.getRow(i).getCell("accountView").getValue().toString();
			}else if(!lastAccount.equals(table.getRow(i).getCell("accountView").getValue().toString())){
				table.getMergeManager().mergeBlock(accountMergStart,table.getColumnIndex("accountView"),
						i-1,table.getColumnIndex("accountView"));
				lastAccount = table.getRow(i).getCell("accountView").getValue().toString();
				accountMergStart = i;
				table.getMergeManager().mergeBlock(curProjMergStart,table.getColumnIndex("curProject"),
						i-1,table.getColumnIndex("curProject"));
				curProjMergStart = i;
				lastCurProj = table.getRow(i).getCell("curProject").getValue().toString();
			}
			
			if(table.getRow(i).getCell("invoiceAcct")!=null&&table.getRow(i).getCell("invoiceAcct").getValue()!=null){
				//��������÷�Ʊ��ʱ���ֶ�ֵ����Ϊ��
				if(lastInvoice.length()==0)
				{
					lastInvoice = table.getRow(i).getCell("invoiceAcct").getValue().toString();
				}else if(!lastInvoice.equals(table.getRow(i).getCell("invoiceAcct").getValue().toString())){
					table.getMergeManager().mergeBlock(invoiceMergStart,table.getColumnIndex("invoiceAcct"),
							i-1,table.getColumnIndex("invoiceAcct"));
					lastInvoice = table.getRow(i).getCell("invoiceAcct").getValue().toString();
					invoiceMergStart = i;
					table.getMergeManager().mergeBlock(curProjMergStart,table.getColumnIndex("curProject"),
							i-1,table.getColumnIndex("curProject"));
					curProjMergStart = i;
					lastCurProj = table.getRow(i).getCell("curProject").getValue().toString();
				}
				
			}
			
			if(lastCurProj.length()==0){
				lastCurProj = table.getRow(i).getCell("curProject").getValue().toString();
			}else if(!lastCurProj.equals(table.getRow(i).getCell("curProject").getValue().toString())){
				table.getMergeManager().mergeBlock(accountMergStart,table.getColumnIndex("accountView"),
						i-1,table.getColumnIndex("accountView"));
				lastAccount = table.getRow(i).getCell("accountView").getValue().toString();
				accountMergStart = i;
				
				table.getMergeManager().mergeBlock(invoiceMergStart,table.getColumnIndex("invoiceAcct"),
						i-1,table.getColumnIndex("invoiceAcct"));
				lastInvoice = table.getRow(i).getCell("invoiceAcct").getValue().toString();
				invoiceMergStart = i;
				
				table.getMergeManager().mergeBlock(curProjMergStart,table.getColumnIndex("curProject"),
						i-1,table.getColumnIndex("curProject"));
				curProjMergStart = i;
				lastCurProj = table.getRow(i).getCell("curProject").getValue().toString();
			}
		}
		table.getMergeManager().mergeBlock(accountMergStart,table.getColumnIndex("accountView"),
				table.getRowCount()-1,table.getColumnIndex("accountView"),KDTMergeManager.FREE_MERGE);
		table.getMergeManager().mergeBlock(curProjMergStart,table.getColumnIndex("invoiceAcct"),
				table.getRowCount()-1,table.getColumnIndex("invoiceAcct"),KDTMergeManager.FREE_MERGE);
		table.getMergeManager().mergeBlock(curProjMergStart,table.getColumnIndex("curProject"),
				table.getRowCount()-1,table.getColumnIndex("curProject"),KDTMergeManager.FREE_MERGE);*/
	}

	protected void kdtEntrys_tableClicked(KDTMouseEvent e) throws Exception {
		// TODO �Զ����ɷ������
		
	}
}