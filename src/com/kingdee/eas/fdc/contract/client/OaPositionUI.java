/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.MarketProjectInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class OaPositionUI extends AbstractOaPositionUI
{
    private static final Logger logger = CoreUIObject.getLogger(OaPositionUI.class);
    public OaPositionUI() throws Exception
    {
        super();
    }
	protected void kdtable_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IObjectValue value=(IObjectValue) this.getUIContext().get("editData");
			String id=kdtable.getRow(e.getRowIndex()).getCell("id").getValue().toString();
			String position=kdtable.getRow(e.getRowIndex()).getCell("position").getValue().toString();
			if(value instanceof ContractBillInfo){
				((ContractBillInfo)value).setOaPosition(id+":"+position);
			}else if(value instanceof ChangeAuditBillInfo){
				((ChangeAuditBillInfo)value).setOaPosition(id+":"+position);
			}else if(value instanceof ContractChangeSettleBillInfo){
				((ContractChangeSettleBillInfo)value).setOaPosition(id+":"+position);
			}else if(value instanceof ContractWithoutTextInfo){
				((ContractWithoutTextInfo)value).setOaPosition(id+":"+position);
			}else if(value instanceof PayRequestBillInfo){
				((PayRequestBillInfo)value).setOaPosition(id+":"+position);
			}else if(value instanceof MarketProjectInfo){
				((MarketProjectInfo)value).setOaPosition(id+":"+position);
			}
			this.destroyWindow();
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.toolBar.setVisible(false);
		this.menuBar.setVisible(false);
		this.kdtable.checkParsed();
		this.kdtable.setEnabled(false);
		Map map=(Map) this.getUIContext().get("map");
		Iterator<Entry<String, String>> entries = map.entrySet().iterator();
		while(entries.hasNext()){
			
		    Entry<String, String> entry = entries.next();
		    String key = entry.getKey();
		    String value = entry.getValue();
		    IRow row=this.kdtable.addRow();
			row.getCell("position").setValue(value);
			row.getCell("id").setValue(key);
		}
	}
}