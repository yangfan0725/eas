/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.BankNumCollection;
import com.kingdee.eas.fdc.contract.BankNumFactory;
import com.kingdee.eas.fdc.contract.SupplierApplyCollection;
import com.kingdee.eas.fdc.contract.SupplierApplyFactory;
import com.kingdee.eas.fdc.contract.SupplierApplyInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
/**
 * output class name
 */
public class SupplierApplyEditUI extends AbstractSupplierApplyEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierApplyEditUI.class);
    public SupplierApplyEditUI() throws Exception
    {
        super();
    }
    protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierApplyFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	protected void initControl() throws EASBizException, BOSException {
		this.txtNumber.setMaxLength(80);

		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPre.setVisible(false);
		this.menuView.setVisible(false);
		
		this.menuBiz.setVisible(false);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
		} else {
			this.unLockUI();
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
	}
	public void storeFields(){
		super.storeFields();
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	protected void fetchInitData() throws Exception {
		
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("sourceBillId");
		return sel;
	}
	protected IObjectValue createNewData() {
		SupplierApplyInfo info= new SupplierApplyInfo();
		info.setState(FDCBillStateEnum.SAVED);
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setBizDate(now);
		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return info;
	}
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtBank);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtBankAccount);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbTaxerQua);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtTaxerNum);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtCode);
		
		BankNumCollection bankNum=BankNumFactory.getRemoteInstance().getBankNumCollection("select number from where number='"+this.txtCode.getText()+"'");
		if(bankNum.size()==0){
			FDCMsgBox.showWarning(this,"���кŲ����ڣ�");
			SysUtil.abort();
		}
		SupplierCollection col=SupplierFactory.getRemoteInstance().getSupplierCollection("select * from where name='"+this.txtName.getText().trim()+"' and number!='FDC-APPLY-"+this.txtNumber.getText()+"'");
		if(col.size()>0){
			FDCMsgBox.showWarning(this,"ϵͳ���Ѿ�����ͬ�������ݹ�Ӧ�̣���Ӧ�̱��룺"+col.get(0).getNumber());
			SysUtil.abort();
		}
		col=SupplierFactory.getRemoteInstance().getSupplierCollection("select * from where taxRegisterNo='"+this.txtTaxerNum.getText().trim()+"' and number!='FDC-APPLY-"+this.txtNumber.getText()+"'");
		if(col.size()>0){
			FDCMsgBox.showWarning(this,"��˰��ʶ����ظ�����Ӧ�̱��룺"+col.get(0).getNumber());
			SysUtil.abort();
		}
		SupplierApplyCollection applyCol;
		if(this.editData.getId()!=null){
			applyCol=SupplierApplyFactory.getRemoteInstance().getSupplierApplyCollection("select * from where taxerNum='"+this.txtTaxerNum.getText().trim()+"' and id!='"+this.editData.getId()+"'");
		}else{
			applyCol=SupplierApplyFactory.getRemoteInstance().getSupplierApplyCollection("select * from where taxerNum='"+this.txtTaxerNum.getText().trim()+"'");
		}
		if(applyCol.size()>0){
			FDCMsgBox.showWarning(this,"��˰��ʶ����ظ�����Ӧ�����뵥���룺"+applyCol.get(0).getNumber());
			SysUtil.abort();
		}
		
		String regex = "^[a-zA-Z0-9]+$";
        if(!this.txtTaxerNum.getText().matches(regex)){
        	FDCMsgBox.showWarning(this,"��˰��ʶ���ֻ֧��Ӣ����ĸ�����֣�");
			SysUtil.abort();
        }
	}
}