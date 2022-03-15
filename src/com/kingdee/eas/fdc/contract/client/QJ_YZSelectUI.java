/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.alibaba.fastjson.*;

/**
 * output class name
 */
public class QJ_YZSelectUI extends AbstractQJ_YZSelectUI
{
    private static final Logger logger = CoreUIObject.getLogger(QJ_YZSelectUI.class);
    
    /**
     * output class constructor
     */
    public QJ_YZSelectUI() throws Exception
    {
        super();
    }
	protected void btnNo_actionPerformed(ActionEvent e) throws Exception {
		this.destroyWindow();
	}
	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		for(int i=0;i<this.kdtable.getRowCount();i++){
			Boolean isSelect=(Boolean) this.kdtable.getRow(i).getCell("select").getValue();
			if(isSelect){
				String id=(String) this.kdtable.getRow(i).getCell("yzID").getValue();
				boolean isExist=false;
				for(int j=0;j<table.getRowCount();j++){
					String conId=(String) table.getRow(j).getCell("yzID").getValue();
					if(id.equals(conId)){
						isExist=true;
						break;
					}
				}
				if(!isExist){
					IRow row=table.addRow();
					row.getCell("name").setValue(this.kdtable.getRow(i).getCell("name").getValue());
					row.getCell("admin").setValue(this.kdtable.getRow(i).getCell("admin").getValue());
					row.getCell("type").setValue(this.kdtable.getRow(i).getCell("type").getValue());
					row.getCell("yzID").setValue(this.kdtable.getRow(i).getCell("yzID").getValue());
					row.getCell("adminID").setValue(this.kdtable.getRow(i).getCell("adminID").getValue());
				}
			}
		}
		this.destroyWindow();
	}
	KDTable table=null;
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		this.toolBar.setVisible(false);
		this.menuBar.setVisible(false);
		this.kdtable.checkParsed();
		KDCheckBox checkBox = new KDCheckBox();
		ICellEditor checkBoxEditor = new KDTDefaultCellEditor(checkBox);
		this.kdtable.getColumn("select").setEditor(checkBoxEditor);
		this.kdtable.getColumn("name").getStyleAttributes().setLocked(true);
		this.kdtable.getColumn("admin").getStyleAttributes().setLocked(true);
		this.kdtable.getColumn("type").getStyleAttributes().setLocked(true);
		table=(KDTable) this.getUIContext().get("table");
		Map map = ContractBillFactory.getRemoteInstance().getQJYZ();
		Iterator<Entry<String, JSONObject>> entries = map.entrySet().iterator();
		while(entries.hasNext()){
			
		    Entry<String, JSONObject> entry = entries.next();
		    String key = entry.getKey();
		    JSONObject value = entry.getValue();
		    
		    IRow row=this.kdtable.addRow();
		    row.getCell("select").setValue(Boolean.FALSE);
			row.getCell("name").setValue(value.getString("sealName"));
			row.getCell("yzID").setValue(value.getString("id"));
			row.getCell("admin").setValue(value.getString("adminName"));
			row.getCell("adminID").setValue(value.getString("adminId"));
			row.getCell("type").setValue(value.getString("sealType"));
		}
	}
	protected void btnSelect_actionPerformed(ActionEvent e) throws Exception {
		this.kdtable.removeRows();
		Map map = ContractBillFactory.getRemoteInstance().getQJYZ();
		Iterator<Entry<String, JSONObject>> entries = map.entrySet().iterator();
		while(entries.hasNext()){
			
		    Entry<String, JSONObject> entry = entries.next();
		    String key = entry.getKey();
		    JSONObject value = entry.getValue();
		    if(this.textName.getText()!=null&&!"".equals(this.textName.getText().trim())){
		    	if(value.getString("sealName").contains(this.textName.getText())){
		    		IRow row=this.kdtable.addRow();
				    row.getCell("select").setValue(Boolean.FALSE);
					row.getCell("name").setValue(value.getString("sealName"));
					row.getCell("yzID").setValue(value.getString("id"));
					row.getCell("admin").setValue(value.getString("adminName"));
					row.getCell("adminID").setValue(value.getString("adminId"));
					row.getCell("type").setValue(value.getString("sealType"));
			    }
		    }else{
		    	IRow row=this.kdtable.addRow();
			    row.getCell("select").setValue(Boolean.FALSE);
				row.getCell("name").setValue(value.getString("sealName"));
				row.getCell("yzID").setValue(value.getString("id"));
				row.getCell("admin").setValue(value.getString("adminName"));
				row.getCell("adminID").setValue(value.getString("adminId"));
				row.getCell("type").setValue(value.getString("sealType"));
		    }
		}
	}


}