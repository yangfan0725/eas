/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.*;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.custom.purchasebaseinfomation.PurchaseCategoryInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeGradeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeGradeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class SupplierChangeGradeEditUI extends AbstractSupplierChangeGradeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierChangeGradeEditUI.class);
    public SupplierChangeGradeEditUI() throws Exception
    {
        super();
    }
    protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierChangeGradeFactory.getRemoteInstance();
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
		
		this.prmtPurchaseCategory.setDisplayFormat("$name$");
		this.prmtPurchaseCategory.setEditFormat("$name$");
		this.prmtPurchaseCategory.setCommitFormat("$number$");
		EntityViewInfo viewPurchase = new EntityViewInfo();
         FilterInfo filter2 = new FilterInfo();
         filter2.getFilterItems().add(new FilterItemInfo("BIMUDF0001", 1, CompareType.EQUALS));
         viewPurchase.setFilter(filter2);
         this.prmtPurchaseCategory.setEntityViewInfo(viewPurchase);
		this.prmtPurchaseCategory.setQueryInfo("com.kingdee.eas.custom.purchasebaseinfomation.app.PurchaseCategoryQuery");	
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
		this.editData.setPurchaseCategory((PurchaseCategoryInfo) this.prmtPurchaseCategory.getValue());
		super.storeFields();
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		this.prmtPurchaseCategory.setValue(this.editData.getPurchaseCategory());
		
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
    	sel.add("purchaseCategory.*");
		return sel;
	}
	protected IObjectValue createNewData() {
		SupplierChangeGradeInfo info= new SupplierChangeGradeInfo();
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
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtApplier);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplier);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtGrade);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtPurchaseCategory);
		verifyInputForSave();
	}
	protected void prmtSupplier_dataChanged(DataChangeEvent e) throws Exception {
		SupplierStockInfo info=(SupplierStockInfo) this.prmtSupplier.getValue();
		if(info!=null){
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("inviteType.*");
			sic.add("grade.*");
			info=SupplierStockFactory.getRemoteInstance().getSupplierStockInfo(new ObjectUuidPK(info.getId()),sic);
			if(info.getInviteType()!=null){
				this.txtInviteType.setText(info.getInviteType().getName());
			}
			if(info.getGrade()!=null){
				this.txtSrcGrade.setText(info.getGrade().getName());
			}
		}else{
			this.txtInviteType.setText(null);
			this.txtSrcGrade.setText(null);
		}
	}
}