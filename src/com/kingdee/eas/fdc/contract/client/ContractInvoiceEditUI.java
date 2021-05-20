/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ActionMap;
import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractInvoiceEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.ContractInvoiceFactory;
import com.kingdee.eas.fdc.contract.ContractInvoiceInfo;
import com.kingdee.eas.fi.gl.common.KDSpinnerCellEditor;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractInvoiceEditUI extends AbstractContractInvoiceEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractInvoiceEditUI.class);
    public ContractInvoiceEditUI() throws Exception
    {
        super();
    }
    protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ContractInvoiceFactory.getRemoteInstance();
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
		this.txtNumber.setMaxLength(255);

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
		
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		
//		this.actionWorkFlowG.setVisible(false);
//		this.actionAuditResult.setVisible(false);
		
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPre.setVisible(false);
		this.menuView.setVisible(false);
		
		this.menuBiz.setVisible(false);
//		this.menuWorkflow.setVisible(false);
		this.actionAddNew.setVisible(false);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
		}
	}
	protected void initTable() {
		this.kdtEntry.checkParsed();
		
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton) contEntry.add(this.actionALine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton) contEntry.add(this.actionILine);
		btnInsertRowinfo.setText("插入行");
		btnInsertRowinfo.setSize(new Dimension(140, 19));

		this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		
		this.kdtEntry.getColumn("goods").setRequired(true);
		this.kdtEntry.getColumn("model").setRequired(true);
		this.kdtEntry.getColumn("unit").setRequired(true);
		this.kdtEntry.getColumn("quantity").setRequired(true);
		this.kdtEntry.getColumn("price").setRequired(true);
		this.kdtEntry.getColumn("amount").setRequired(true);
		this.kdtEntry.getColumn("rate").setRequired(true);
		this.kdtEntry.getColumn("rateAmount").setRequired(true);
		
		KDFormattedTextField price = new KDFormattedTextField();
		price.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		price.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		price.setNegatived(false);
		price.setPrecision(8);
		KDTDefaultCellEditor priceEditor = new KDTDefaultCellEditor(price);
		this.kdtEntry.getColumn("price").setEditor(priceEditor);
		this.kdtEntry.getColumn("price").getStyleAttributes().setNumberFormat("#,##0.00000000;-#,##0.00000000");
		this.kdtEntry.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setNegatived(false);
		amount.setPrecision(2);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		this.kdtEntry.getColumn("quantity").setEditor(amountEditor);
		this.kdtEntry.getColumn("quantity").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("quantity").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.kdtEntry.getColumn("amount").setEditor(amountEditor);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		KDFormattedTextField rateamount = new KDFormattedTextField();
		rateamount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		rateamount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		rateamount.setNegatived(false);
		rateamount.setPrecision(2);
		rateamount.setMaximumValue(new BigDecimal(100));
		rateamount.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor rateamountEditor = new KDTDefaultCellEditor(rateamount);
		
		this.kdtEntry.getColumn("rate").setEditor(rateamountEditor);
		this.kdtEntry.getColumn("rate").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("rate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.kdtEntry.getColumn("rateAmount").setEditor(amountEditor);
		this.kdtEntry.getColumn("rateAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("rateAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		this.kdtEntry.getColumn("rateAmount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		ActionMap actionMap = this.kdtEntry.getActionMap();
		actionMap.remove(KDTAction.CUT);
		actionMap.remove(KDTAction.DELETE);
		actionMap.remove(KDTAction.PASTE);
		
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				String str = o.toString();
				if (!FDCHelper.isEmpty(str)) {
					return str + "%";
				}
				return str;
			}
		});
		kdtEntry.getColumn("rate").setRenderer(render_scale);
	}
	
	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		int year=0;
		int month=0;
		IRow row = this.kdtEntry.addRow();
		ContractInvoiceEntryInfo info = new ContractInvoiceEntryInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		
		row.setUserObject(info);
	}
	public void actionILine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = null;
		if (this.kdtEntry.getSelectManager().size() > 0) {
			int top = this.kdtEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(this.kdtEntry)){
				actionALine_actionPerformed(e);
			}else{
				ContractInvoiceEntryInfo info = new ContractInvoiceEntryInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				
				row = this.kdtEntry.addRow(top);
				row.setUserObject(info);
			}
		} else {
			actionALine_actionPerformed(e);
		}
	}
	public boolean confirmRemove(Component comp) {
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	public void actionRLine_actionPerformed(ActionEvent e) throws Exception {
		if (this.kdtEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtEntry)) {
			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}
		if (confirmRemove(this)) {
			int top = this.kdtEntry.getSelectManager().get().getBeginRow();
			int bottom = this.kdtEntry.getSelectManager().get().getEndRow();
			for (int i = top; i <= bottom; i++) {
				if (this.kdtEntry.getRow(top) == null) {
					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				this.kdtEntry.removeRow(top);
			}
			BigDecimal totalAmount=FDCHelper.ZERO;
			BigDecimal amount=FDCHelper.ZERO;
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				totalAmount=FDCHelper.add(totalAmount, FDCHelper.add(this.kdtEntry.getRow(i).getCell("amount").getValue(), this.kdtEntry.getRow(i).getCell("rateAmount").getValue()));
				amount=FDCHelper.add(amount,this.kdtEntry.getRow(i).getCell("rateAmount").getValue());
			}
			this.txtTotalAmount.setValue(totalAmount);
			this.txtTotalAmountCapital.setText(FDCClientHelper.getChineseFormat(totalAmount, false));
			this.editData.setTotalRateAmount(amount);
		}
	}
	public void onLoad() throws Exception {
		initTable();
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
		
		if(this.editData.getContract()!=null){
			this.txtContractInfo.setText(this.editData.getContract().getNumber() + " " + editData.getContract().getName());
		}
		
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
    	sel.add("contract.*");
    	sel.add("contract.contractType.*");
    	sel.add("contract.partB.*");
    	sel.add("totalRateAmount");
		return sel;
	}
	protected IObjectValue createNewData() {
		ContractInvoiceInfo info=(ContractInvoiceInfo)this.getUIContext().get("info");
		if(info==null){
			info= new ContractInvoiceInfo();
			String contractBillId = (String)getUIContext().get("contractBillId");
		
			ContractBillInfo contractBillInfo = null;
			try {
				SelectorItemCollection sic=new SelectorItemCollection();
				sic.add("*");
				sic.add("curProject.*");
				sic.add("orgUnit.*");
				sic.add("contractType.*");
				sic.add("partB.*");
				sic.add("landDeveloper.*");
				contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(contractBillId), sic);	
			} catch (Exception e1) {
				handUIException(e1);
			}
			if(contractBillInfo!=null){
				if(contractBillInfo.getLandDeveloper()!=null){
					info.setBuyTaxNo(contractBillInfo.getLandDeveloper().getTaxNo());
					info.setBuyBankNo(contractBillInfo.getLandDeveloper().getBankNo());
					info.setBuyAddressAndPhone(contractBillInfo.getLandDeveloper().getAddressAndPhone());
					info.setBuyName(contractBillInfo.getLandDeveloper().getName());
				}
				if(contractBillInfo.getPartB()!=null){
					info.setSaleName(contractBillInfo.getPartB().getName());
				}
				info.setSaleTaxNo(contractBillInfo.getTaxerNum());
				info.setSaleBankNo(contractBillInfo.getBankAccount());
				info.setOrgUnit(contractBillInfo.getOrgUnit());
				info.setContract(contractBillInfo);
			}else{
				FDCMsgBox.showWarning(this,"合同为空！");
	    		SysUtil.abort();
			}
		}else{
			info.setId(null);
		}
		info.setState(FDCBillStateEnum.SAVED);
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setBizDate(now);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setCreator(null);
		info.setCreateTime(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setLastUpdateUser(null);
		info.setLastUpdateTime(null);
		return info;
	}
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
	}
	
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, this.cbInvoiceType);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtInvoiceNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtBuyAddressAndPhone);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtBuyBankNo);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtBuyName);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtBuyTaxNo);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtSaleAddressAndPhone);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtSaleBankNo);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtSaleName);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtSaleTaxNo);
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"发票明细不能为空！");
			SysUtil.abort();
		}
		for(int i = 0; i < this.kdtEntry.getRowCount(); i++){
			IRow row=this.kdtEntry.getRow(i);
			if(row.getCell("goods").getValue()==null||"".equals(row.getCell("goods").getValue().toString().trim())){
				FDCMsgBox.showWarning(this,"货物或应税劳务名称不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("goods"));
				SysUtil.abort();
			}
			if(row.getCell("model").getValue()==null||"".equals(row.getCell("model").getValue().toString().trim())){
				FDCMsgBox.showWarning(this,"规格型号不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("model"));
				SysUtil.abort();
			}
			if(row.getCell("unit").getValue()==null||"".equals(row.getCell("unit").getValue().toString().trim())){
				FDCMsgBox.showWarning(this,"单位不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("unit"));
				SysUtil.abort();
			}
			if(row.getCell("quantity").getValue()==null){
				FDCMsgBox.showWarning(this,"数量不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("quantity"));
				SysUtil.abort();
			}
			if(row.getCell("price").getValue()==null){
				FDCMsgBox.showWarning(this,"单价不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("price"));
				SysUtil.abort();
			}
			if(row.getCell("amount").getValue()==null){
				FDCMsgBox.showWarning(this,"金额不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("amount"));
				SysUtil.abort();
			}
			if(row.getCell("rate").getValue()==null){
				FDCMsgBox.showWarning(this,"税率不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("rate"));
				SysUtil.abort();
			}
			if(row.getCell("rateAmount").getValue()==null){
				FDCMsgBox.showWarning(this,"税额不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("rateAmount"));
				SysUtil.abort();
			}
		}
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		if("amount".equals(this.kdtEntry.getColumnKey(e.getColIndex()))
				||"rateAmount".equals(this.kdtEntry.getColumnKey(e.getColIndex()))){
			BigDecimal totalAmount=FDCHelper.ZERO;
			BigDecimal amount=FDCHelper.ZERO;
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				totalAmount=FDCHelper.add(totalAmount, FDCHelper.add(this.kdtEntry.getRow(i).getCell("amount").getValue(), this.kdtEntry.getRow(i).getCell("rateAmount").getValue()));
				amount=FDCHelper.add(amount,this.kdtEntry.getRow(i).getCell("rateAmount").getValue());
			}
			this.txtTotalAmount.setValue(totalAmount);
			this.txtTotalAmountCapital.setText(FDCClientHelper.getChineseFormat(totalAmount, false));
			this.editData.setTotalRateAmount(amount);
		}
	}

}