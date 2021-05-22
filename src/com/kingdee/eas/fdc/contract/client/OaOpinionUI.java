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
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.MarketProjectInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class OaOpinionUI extends AbstractOaOpinionUI
{
    private static final Logger logger = CoreUIObject.getLogger(OaOpinionUI.class);
    
    /**
     * output class constructor
     */
    public OaOpinionUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.toolBar.setVisible(false);
		this.menuBar.setVisible(false);
	}
	protected void btnNo_actionPerformed(ActionEvent e) throws Exception {
		this.destroyWindow();
	}
	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		if(this.txtOaOpinion==null||this.txtOaOpinion.getText().trim().equals("")){
			FDCMsgBox.showWarning(this,"审批意见不能为空！");
			SysUtil.abort();
		}
		IObjectValue value=(IObjectValue) this.getUIContext().get("editData");
		String opinion=this.txtOaOpinion.getText();
		if(value instanceof ContractBillInfo){
			((ContractBillInfo)value).setOaOpinion(opinion);
		}else if(value instanceof ChangeAuditBillInfo){
			((ChangeAuditBillInfo)value).setOaOpinion(opinion);
		}else if(value instanceof ContractChangeSettleBillInfo){
			((ContractChangeSettleBillInfo)value).setOaOpinion(opinion);
		}else if(value instanceof ContractWithoutTextInfo){
			((ContractWithoutTextInfo)value).setOaOpinion(opinion);
		}else if(value instanceof PayRequestBillInfo){
			((PayRequestBillInfo)value).setOaOpinion(opinion);
		}else if(value instanceof MarketProjectInfo){
			((MarketProjectInfo)value).setOaOpinion(opinion);
		}
		this.destroyWindow();
	}
}