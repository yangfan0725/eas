/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementSubmissionCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementSubmissionFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementSubmissionInfo;
import com.kingdee.eas.fdc.contract.IChangeAuditBill;
import com.kingdee.eas.fdc.contract.IContractSettlementSubmission;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractSettlementSubmissionListUI extends AbstractContractSettlementSubmissionListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractSettlementSubmissionListUI.class);
    
    public ContractSettlementSubmissionListUI() throws Exception
    {
        super();
    }
	protected String getEditUIName() {
		return ContractSettlementSubmissionEditUI.class.getName();
	}
	protected void audit(List ids) throws Exception {
		IContractSettlementSubmission bill = (IContractSettlementSubmission) getBizInterface();		
    	if(ids!=null){
    		bill.audit(ids);
    	}
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return ContractSettlementSubmissionFactory.getRemoteInstance();
	}
	protected void unAudit(List ids) throws Exception {
		IContractSettlementSubmission bill = (IContractSettlementSubmission) getBizInterface();		
    	if(ids!=null){
    		bill.unAudit(ids);
    	}
	}
	protected String[] getLocateNames() {
        String[] locateNames = new String[2];
        locateNames[0] = "number";
        locateNames[1] = "contractBill.name";
        return locateNames;
    }
	public void onLoad() throws Exception {		
		super.onLoad();
		this.actionAddNew.setVisible(true);
		this.actionAddNew.setEnabled(true);
		this.actionEdit.setVisible(true);
		this.actionEdit.setEnabled(true);
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAuditResult.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
	}
	protected void updateButtonStatus()
    {
		
    }
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();
		super.actionRemove_actionPerformed(e);
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEdit(e);
		super.actionEdit_actionPerformed(e);
	}
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkFlowG_actionPerformed(e);
	}
}