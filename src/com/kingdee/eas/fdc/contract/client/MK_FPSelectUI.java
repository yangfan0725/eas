/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class MK_FPSelectUI extends AbstractMK_FPSelectUI
{
    private static final Logger logger = CoreUIObject.getLogger(MK_FPSelectUI.class);
    
    /**
     * output class constructor
     */
    public MK_FPSelectUI() throws Exception
    {
        super();
    }
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.destroyWindow();
    }
    protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    	for(int i=0;i<this.kdtable.getRowCount();i++){
			Boolean isSelect=(Boolean) this.kdtable.getRow(i).getCell("select").getValue();
			if(isSelect){
				String id=(String) this.kdtable.getRow(i).getCell("invoiceNumber").getValue();
				boolean isExist=false;
				for(int j=0;j<table.getRowCount();j++){
					String conId=(String) table.getRow(j).getCell("invoiceNumber").getValue();
					if(id.equals(conId)){
						isExist=true;
						break;
					}
				}
				if(!isExist){
					IRow row=table.addRow();
					row.getCell("invoiceNumber").setValue(this.kdtable.getRow(i).getCell("invoiceNumber").getValue());
					row.getCell("totalPriceAndTax").setValue(this.kdtable.getRow(i).getCell("totalPriceAndTax").getValue());
//					row.getCell("supplierName").setValue(this.kdtable.getRow(i).getCell("supplierName").getValue());
					row.getCell("invoiceTypeDesc").setValue(this.kdtable.getRow(i).getCell("invoiceTypeDesc").getValue());
					if(this.kdtable.getRow(i).getCell("bizDate").getValue()!=null&& !String.valueOf(this.kdtable.getRow(i).getCell("bizDate").getValue()).equals("")){
					Date d = new Date(Long.valueOf((String) this.kdtable.getRow(i).getCell("bizDate").getValue()));
						if(row.getCell("bizDate")!=null){
							row.getCell("bizDate").setValue(d);
						}else{
							row.getCell("issueDate").setValue(d);
						}
					}
					row.getCell("specialVATTaxRate").setValue(this.kdtable.getRow(i).getCell("specialVATTaxRate").getValue());
					row.getCell("totalTaxAmount").setValue(this.kdtable.getRow(i).getCell("totalTaxAmount").getValue());
//					row.getCell("fromMk").setValue(1);
				}
			}
		}
    	this.destroyWindow();
    }
    KDTable table=null;
    int offset=0;
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		this.toolBar.setVisible(false);
		this.menuBar.setVisible(false);
		this.kdtable.checkParsed();
		KDCheckBox checkBox = new KDCheckBox();
		ICellEditor checkBoxEditor = new KDTDefaultCellEditor(checkBox);
		this.kdtable.getColumn("select").setEditor(checkBoxEditor);
		this.kdtable.getColumn("invoiceNumber").getStyleAttributes().setLocked(true);
		this.kdtable.getColumn("totalPriceAndTax").getStyleAttributes().setLocked(true);
		this.kdtable.getColumn("supplierName").getStyleAttributes().setLocked(true);
		table=(KDTable) this.getUIContext().get("table");
		this.pkStartDate.setValue(FDCDateHelper.addDays(new Date(), -29));
		this.pkEndDate.setValue(new Date());
	}

    protected void btnSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	FDCClientVerifyHelper.verifyEmpty(this, this.pkStartDate);
    	FDCClientVerifyHelper.verifyEmpty(this, this.pkEndDate);
    	if(FDCDateHelper.getDiffDays((Date)this.pkStartDate.getValue(), (Date)this.pkEndDate.getValue())>30){
    		FDCMsgBox.showWarning(this,"发票导出日期不能超过一个月！");
    		return;
    	}
    	this.kdtable.removeRows();
    	Map map = ContractWithoutTextFactory.getRemoteInstance().getMKFP((Date)this.pkStartDate.getValue(), (Date)this.pkEndDate.getValue(),0);
    	offset=0;
		Iterator<Entry<String, JSONObject>> entries = map.entrySet().iterator();
		while(entries.hasNext()){
		    Entry<String, JSONObject> entry = entries.next();
		    String key = entry.getKey();
		    JSONObject value = entry.getValue();
		    if(this.txtInvoiceNumber.getText()!=null&&!"".equals(this.txtInvoiceNumber.getText().trim())){
		    	if(value.getString("invoiceNumber").contains(this.txtInvoiceNumber.getText())){
		    		 IRow row=this.kdtable.addRow();
		    		 row.getCell("select").setValue(Boolean.FALSE);
		    	     row.getCell("invoiceNumber").setValue(value.getString("invoiceNumber"));
					 row.getCell("totalPriceAndTax").setValue(value.getString("totalPriceAndTax"));
					 row.getCell("supplierName").setValue(value.getString("supplierName"));
					 row.getCell("invoiceTypeDesc").setValue(value.getString("invoiceTypeDesc"));
					 row.getCell("bizDate").setValue(value.getString("issueDate"));
					 row.getCell("specialVATTaxRate").setValue(value.getString("specialVATTaxRate"));
					 row.getCell("totalTaxAmount").setValue(value.getString("totalTaxAmount"));
			    }
		    }else{
		    	IRow row=this.kdtable.addRow();
	    		row.getCell("select").setValue(Boolean.FALSE);
	    	    row.getCell("invoiceNumber").setValue(value.getString("invoiceNumber"));
				row.getCell("totalPriceAndTax").setValue(value.getString("totalPriceAndTax"));
				row.getCell("supplierName").setValue(value.getString("supplierName"));
				row.getCell("invoiceTypeDesc").setValue(value.getString("invoiceTypeDesc"));
				row.getCell("bizDate").setValue(value.getString("issueDate"));
				row.getCell("specialVATTaxRate").setValue(value.getString("specialVATTaxRate"));
				row.getCell("totalTaxAmount").setValue(value.getString("totalTaxAmount"));
		    }
		}
    }
	protected void btnNext_actionPerformed(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.pkStartDate);
    	FDCClientVerifyHelper.verifyEmpty(this, this.pkEndDate);
    	if(FDCDateHelper.getDiffDays((Date)this.pkStartDate.getValue(), (Date)this.pkEndDate.getValue())>30){
    		FDCMsgBox.showWarning(this,"发票导出日期不能超过一个月！");
    		return;
    	}
    	Map map = ContractWithoutTextFactory.getRemoteInstance().getMKFP((Date)this.pkStartDate.getValue(), (Date)this.pkEndDate.getValue(),offset+1);
		Iterator<Entry<String, JSONObject>> entries = map.entrySet().iterator();
		if(map.entrySet().size()>0){
			this.kdtable.removeRows();
			offset=offset+1;
		}else{
			FDCMsgBox.showWarning(this,"无发票数据！");
    		return;
		}
		while(entries.hasNext()){
		    Entry<String, JSONObject> entry = entries.next();
		    String key = entry.getKey();
		    JSONObject value = entry.getValue();
		    if(this.txtInvoiceNumber.getText()!=null&&!"".equals(this.txtInvoiceNumber.getText().trim())){
		    	if(value.getString("invoiceNumber").contains(this.txtInvoiceNumber.getText())){
		    		 IRow row=this.kdtable.addRow();
		    		 row.getCell("select").setValue(Boolean.FALSE);
		    	     row.getCell("invoiceNumber").setValue(value.getString("invoiceNumber"));
					 row.getCell("totalPriceAndTax").setValue(value.getString("totalPriceAndTax"));
					 row.getCell("supplierName").setValue(value.getString("supplierName"));
					 row.getCell("invoiceTypeDesc").setValue(value.getString("invoiceTypeDesc"));
					 row.getCell("bizDate").setValue(value.getString("issueDate"));
					 row.getCell("specialVATTaxRate").setValue(value.getString("specialVATTaxRate"));
					 row.getCell("totalTaxAmount").setValue(value.getString("totalTaxAmount"));
			    }
		    }else{
		    	IRow row=this.kdtable.addRow();
	    		row.getCell("select").setValue(Boolean.FALSE);
	    	    row.getCell("invoiceNumber").setValue(value.getString("invoiceNumber"));
				row.getCell("totalPriceAndTax").setValue(value.getString("totalPriceAndTax"));
				row.getCell("supplierName").setValue(value.getString("supplierName"));
				row.getCell("invoiceTypeDesc").setValue(value.getString("invoiceTypeDesc"));
				row.getCell("bizDate").setValue(value.getString("issueDate"));
				row.getCell("specialVATTaxRate").setValue(value.getString("specialVATTaxRate"));
				row.getCell("totalTaxAmount").setValue(value.getString("totalTaxAmount"));
		    }
		}
	}
	@Override
	protected void btnPre_actionPerformed(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.pkStartDate);
    	FDCClientVerifyHelper.verifyEmpty(this, this.pkEndDate);
    	if(FDCDateHelper.getDiffDays((Date)this.pkStartDate.getValue(), (Date)this.pkEndDate.getValue())>30){
    		FDCMsgBox.showWarning(this,"发票导出日期不能超过一个月！");
    		return;
    	}
    	Map map = ContractWithoutTextFactory.getRemoteInstance().getMKFP((Date)this.pkStartDate.getValue(), (Date)this.pkEndDate.getValue(),offset-1);
		Iterator<Entry<String, JSONObject>> entries = map.entrySet().iterator();
		if(map.entrySet().size()>0){
			this.kdtable.removeRows();
			offset=offset-1;
		}else{
			FDCMsgBox.showWarning(this,"无发票数据！");
    		return;
		}
		while(entries.hasNext()){
		    Entry<String, JSONObject> entry = entries.next();
		    String key = entry.getKey();
		    JSONObject value = entry.getValue();
		    if(this.txtInvoiceNumber.getText()!=null&&!"".equals(this.txtInvoiceNumber.getText().trim())){
		    	if(value.getString("invoiceNumber").contains(this.txtInvoiceNumber.getText())){
		    		 IRow row=this.kdtable.addRow();
		    		 row.getCell("select").setValue(Boolean.FALSE);
		    	     row.getCell("invoiceNumber").setValue(value.getString("invoiceNumber"));
					 row.getCell("totalPriceAndTax").setValue(value.getString("totalPriceAndTax"));
					 row.getCell("supplierName").setValue(value.getString("supplierName"));
					 row.getCell("invoiceTypeDesc").setValue(value.getString("invoiceTypeDesc"));
					 row.getCell("bizDate").setValue(value.getString("issueDate"));
					 row.getCell("specialVATTaxRate").setValue(value.getString("specialVATTaxRate"));
					 row.getCell("totalTaxAmount").setValue(value.getString("totalTaxAmount"));
			    }
		    }else{
		    	IRow row=this.kdtable.addRow();
	    		row.getCell("select").setValue(Boolean.FALSE);
	    	    row.getCell("invoiceNumber").setValue(value.getString("invoiceNumber"));
				row.getCell("totalPriceAndTax").setValue(value.getString("totalPriceAndTax"));
				row.getCell("supplierName").setValue(value.getString("supplierName"));
				row.getCell("invoiceTypeDesc").setValue(value.getString("invoiceTypeDesc"));
				row.getCell("bizDate").setValue(value.getString("issueDate"));
				row.getCell("specialVATTaxRate").setValue(value.getString("specialVATTaxRate"));
				row.getCell("totalTaxAmount").setValue(value.getString("totalTaxAmount"));
		    }
		}
	}
    



}