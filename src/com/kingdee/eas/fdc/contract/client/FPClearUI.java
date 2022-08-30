/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.bibench.platform.ui.util.MsgBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class FPClearUI extends AbstractFPClearUI
{
    private static final Logger logger = CoreUIObject.getLogger(FPClearUI.class);
    
    /**
     * output class constructor
     */
    public FPClearUI() throws Exception
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
    	String number=null;
    	String invoiceNumber=null;
    	JSONArray a=new JSONArray();
    	for(int i=0;i<this.kdtable.getRowCount();i++){
			Boolean isSelect=(Boolean) this.kdtable.getRow(i).getCell("select").getValue();
			if(isSelect){
				a.add(this.kdtable.getRow(i).getCell("invoiceDataCode").getValue().toString());
				number=this.kdtable.getRow(i).getCell("invoiceDataCode").getValue().toString();
				invoiceNumber=this.kdtable.getRow(i).getCell("invoiceNumber").getValue().toString();
			}
		}
    	if(a.size()==0||a.size()>1){
    		FDCMsgBox.showWarning(this,"请选择一张发票！");
    		return;
    	}
    	FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select fparentid from T_CON_ContractWTInvoiceEntry where FInvoiceNumber='" + invoiceNumber + "'");
		IRowSet rs=builder.executeQuery();
		while(rs.next()){
			FDCMsgBox.showWarning(this,"已关联无文本付款申请单据，禁止解锁！");
    		return;
		}
		builder=new FDCSQLBuilder();
		builder.appendSql("select fparentid from T_CON_PayReqInvoiceEntry where FInvoiceNumber='" + invoiceNumber + "'");
		rs=builder.executeQuery();
		while(rs.next()){
			FDCMsgBox.showWarning(this,"已关联合同付款申请单据，禁止解锁！");
    		return;
		}
    	String result=ContractWithoutTextFactory.getRemoteInstance().clearMKFP(number);
    	if(result==null){
    		FDCMsgBox.showInfo(this,"操作成功！");
    	}
    	btnSelect_actionPerformed(null);
    }
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
					 row.getCell("invoiceDataCode").setValue(value.getString("invoiceDataCode"));
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
				row.getCell("invoiceDataCode").setValue(value.getString("invoiceDataCode"));
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
					 row.getCell("invoiceDataCode").setValue(value.getString("invoiceDataCode"));
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
				row.getCell("invoiceDataCode").setValue(value.getString("invoiceDataCode"));
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
    	if(offset-1<0){
    		FDCMsgBox.showWarning(this,"无发票数据！");
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
					 row.getCell("invoiceDataCode").setValue(value.getString("invoiceDataCode"));
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
				row.getCell("invoiceDataCode").setValue(value.getString("invoiceDataCode"));
		    }
		}
	}

   
}