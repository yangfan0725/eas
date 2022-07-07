/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;
import org.apache.poi4cp.ss.usermodel.Cell;
import org.apache.poi4cp.ss.usermodel.CellStyle;
import org.apache.poi4cp.ss.usermodel.DataFormat;
import org.apache.poi4cp.ss.usermodel.Row;
import org.apache.poi4cp.ss.usermodel.Sheet;
import org.apache.poi4cp.xssf.streaming.SXSSFWorkbook;
import org.apache.poi4cp.xssf.usermodel.XSSFCellStyle;

import com.kingdee.bos.ctrl.common.util.FileUtil;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.InvoiceBatchImportEntryCollection;
import com.kingdee.eas.fdc.tenancy.InvoiceBatchImportEntryFactory;
import com.kingdee.eas.fdc.tenancy.InvoiceBatchImportEntryInfo;
import com.kingdee.eas.fdc.tenancy.InvoiceBatchImportFactory;
import com.kingdee.eas.fdc.tenancy.InvoiceBatchImportInfo;
import com.kingdee.eas.fdc.tenancy.InvoiceBillCollection;
import com.kingdee.eas.fdc.tenancy.InvoiceBillFactory;
import com.kingdee.eas.fdc.tenancy.InvoiceBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InvoiceBatchImportEditUI extends AbstractInvoiceBatchImportEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(InvoiceBatchImportEditUI.class);

	private Map listennerMap = new HashMap();

	/**
	 * output class constructor
	 */
	public InvoiceBatchImportEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		if(editData.getEntry()!=null&& editData.getEntry().size()>0){
			InvoiceBatchImportEntryInfo entry = editData.getEntry().get(0);
			if(entry!=null&&entry.getInvoiceType()!=null)
			editData.setInvoiceType(entry.getInvoiceType().getAlias());
		}
		
	}
	@Override
	public boolean destroyWindow() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * output btnAddLine_actionPerformed method
	 */
	protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnAddLine_actionPerformed(e);
	}

	/**
	 * output menuItemEnterToNextRow_itemStateChanged method
	 */
	protected void menuItemEnterToNextRow_itemStateChanged(
			java.awt.event.ItemEvent e) throws Exception {
		super.menuItemEnterToNextRow_itemStateChanged(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionExportSave_actionPerformed
	 */
	public void actionExportSave_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSave_actionPerformed(e);
	}

	/**
	 * output actionExportSelectedSave_actionPerformed
	 */
	public void actionExportSelectedSave_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelectedSave_actionPerformed(e);
	}

	/**
	 * output actionKnowStore_actionPerformed
	 */
	public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception {
		super.actionKnowStore_actionPerformed(e);
	}

	/**
	 * output actionAnswer_actionPerformed
	 */
	public void actionAnswer_actionPerformed(ActionEvent e) throws Exception {
		super.actionAnswer_actionPerformed(e);
	}

	/**
	 * output actionRemoteAssist_actionPerformed
	 */
	public void actionRemoteAssist_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoteAssist_actionPerformed(e);
	}

	/**
	 * output actionPopupCopy_actionPerformed
	 */
	public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionPopupCopy_actionPerformed(e);
	}

	/**
	 * output actionHTMLForMail_actionPerformed
	 */
	public void actionHTMLForMail_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionHTMLForMail_actionPerformed(e);
	}

	/**
	 * output actionExcelForMail_actionPerformed
	 */
	public void actionExcelForMail_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExcelForMail_actionPerformed(e);
	}

	/**
	 * output actionHTMLForRpt_actionPerformed
	 */
	public void actionHTMLForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionHTMLForRpt_actionPerformed(e);
	}

	/**
	 * output actionExcelForRpt_actionPerformed
	 */
	public void actionExcelForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExcelForRpt_actionPerformed(e);
	}

	/**
	 * output actionLinkForRpt_actionPerformed
	 */
	public void actionLinkForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionLinkForRpt_actionPerformed(e);
	}

	/**
	 * output actionPopupPaste_actionPerformed
	 */
	public void actionPopupPaste_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPopupPaste_actionPerformed(e);
	}

	/**
	 * output actionToolBarCustom_actionPerformed
	 */
	public void actionToolBarCustom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionToolBarCustom_actionPerformed(e);
	}

	/**
	 * output actionCloudFeed_actionPerformed
	 */
	public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception {
		super.actionCloudFeed_actionPerformed(e);
	}

	/**
	 * output actionCloudShare_actionPerformed
	 */
	public void actionCloudShare_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCloudShare_actionPerformed(e);
	}

	/**
	 * output actionCloudScreen_actionPerformed
	 */
	public void actionCloudScreen_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCloudScreen_actionPerformed(e);
	}

	/**
	 * output actionXunTongFeed_actionPerformed
	 */
	public void actionXunTongFeed_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionXunTongFeed_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}
	
	@Override
	protected void verifyInputForSubmint() throws Exception {
		// TODO Auto-generated method stub
		super.verifyInputForSubmint();
		//检验所有的客户名称是否已经录入
		int rowCount = this.kdtEntry.getRowCount();
		Set invTypeSet = new HashSet();
		int errCount = 0;
		IRow r = null;
		StringBuffer str = new StringBuffer();
		for(int i=0;i<rowCount;i++){
			r = kdtEntry.getRow(i);
			if(r.getCell("invoiceName").getValue() == null){
				str.append("第"+(i+1)+"行发票为空\n");
				errCount++;
			}
			invTypeSet.add(r.getCell("invoiceType").getValue());
		}
		
		//判断客户名称 
		if(errCount > 0){
			FDCMsgBox.showError("分录发票名称不能为空，不能提交。 ");
			abort();
		}
		
		if(invTypeSet.size()>1){
			FDCMsgBox.showError("分录发票类型必须全部一致，请检查。 ");
			abort();
		}
		
		
		
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * output actionFirst_actionPerformed
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		super.actionFirst_actionPerformed(e);
	}

	/**
	 * output actionPre_actionPerformed
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		super.actionPre_actionPerformed(e);
	}

	/**
	 * output actionNext_actionPerformed
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		super.actionNext_actionPerformed(e);
	}

	/**
	 * output actionLast_actionPerformed
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		super.actionLast_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}

	/**
	 * output actionReset_actionPerformed
	 */
	public void actionReset_actionPerformed(ActionEvent e) throws Exception {
		super.actionReset_actionPerformed(e);
	}

	/**
	 * output actionMsgFormat_actionPerformed
	 */
	public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception {
		super.actionMsgFormat_actionPerformed(e);
	}

	/**
	 * output actionAddLine_actionPerformed
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
	}

	/**
	 * output actionCopyLine_actionPerformed
	 */
	public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyLine_actionPerformed(e);
	}

	/**
	 * output actionInsertLine_actionPerformed
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionInsertLine_actionPerformed(e);
	}

	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoveLine_actionPerformed(e);
	}

	/**
	 * output actionCreateFrom_actionPerformed
	 */
	public void actionCreateFrom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCreateFrom_actionPerformed(e);
	}

	/**
	 * output actionCopyFrom_actionPerformed
	 */
	public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyFrom_actionPerformed(e);
	}

	/**
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceUp_actionPerformed(e);
	}

	/**
	 * output actionTraceDown_actionPerformed
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceDown_actionPerformed(e);
	}

	/**
	 * output actionViewSubmitProccess_actionPerformed
	 */
	public void actionViewSubmitProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewSubmitProccess_actionPerformed(e);
	}

	/**
	 * output actionViewDoProccess_actionPerformed
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewDoProccess_actionPerformed(e);
	}

	/**
	 * output actionMultiapprove_actionPerformed
	 */
	public void actionMultiapprove_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionMultiapprove_actionPerformed(e);
	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionVoucher_actionPerformed
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);
	}

	/**
	 * output actionDelVoucher_actionPerformed
	 */
	public void actionDelVoucher_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * output actionWorkFlowG_actionPerformed
	 */
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkFlowG_actionPerformed(e);
	}

	/**
	 * output actionCreateTo_actionPerformed
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateTo_actionPerformed(e);
	}

	/**
	 * output actionSendingMessage_actionPerformed
	 */
	public void actionSendingMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendingMessage_actionPerformed(e);
	}

	/**
	 * output actionSignature_actionPerformed
	 */
	public void actionSignature_actionPerformed(ActionEvent e) throws Exception {
		super.actionSignature_actionPerformed(e);
	}

	/**
	 * output actionWorkflowList_actionPerformed
	 */
	public void actionWorkflowList_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionWorkflowList_actionPerformed(e);
	}

	/**
	 * output actionViewSignature_actionPerformed
	 */
	public void actionViewSignature_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewSignature_actionPerformed(e);
	}

	/**
	 * output actionSendMail_actionPerformed
	 */
	public void actionSendMail_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMail_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionNumberSign_actionPerformed
	 */
	public void actionNumberSign_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionNumberSign_actionPerformed(e);
	}

	/**
	 * output actionAudit_actionPerformed
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
	}

	/**
	 * output actionUnAudit_actionPerformed
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
	}

	/**
	 * output actionAttamentCtrl_actionPerformed
	 */
	public void actionAttamentCtrl_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttamentCtrl_actionPerformed(e);
	}

	@Override
	public void loadFields() {
		// TODO Auto-generated method stub
		super.loadFields();
	}

	@Override
	protected void attachListeners() {

	}

	@Override
	protected void detachListeners() {

	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return InvoiceBatchImportFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return kdtEntry;
	}

	@Override
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtNumber;
	}

	@Override
	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		InvoiceBatchImportInfo info = new InvoiceBatchImportInfo();
		info.setName(UUID.randomUUID().toString());
		info.setNumber(UUID.randomUUID().toString());
		info.setOrgUnit(SysContext.getSysContext().getCurrentFIUnit()
				.castToFullOrgUnitInfo());
		info.setStartDate(new Date());
		info.setEndDate(new Date());
		Map uiContext = getUIContext();
		if (uiContext.get("sellProject") != null) {
			SellProjectInfo sellProject = (SellProjectInfo) uiContext
					.get("sellProject");
			info.setSellProject(sellProject);
		}
		return info;
	}

	@Override
	protected IObjectValue createNewDetailData(KDTable table) {
		// TODO Auto-generated method stub
		return new InvoiceBatchImportEntryInfo();
	}

	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		
		this.btnSelectContract.setVisible(false);
		
		KDWorkButton btnSelectContract = new KDWorkButton();
		this.actionSelectContract.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnSelectContract = (KDWorkButton) contEntry.add(this.actionSelectContract);
		btnSelectContract.setText("选择开票款项");
		btnSelectContract.setSize(new Dimension(140, 19));
		
		KDWorkButton btnRemoveLine = new KDWorkButton();
		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnRemoveLine = (KDWorkButton) contEntry.add(this.actionRemoveLine);
		btnRemoveLine.setText("删除行");
		btnRemoveLine.setSize(new Dimension(140, 19));
		
//		this.contEntry.addButton(this.btnRemoveLine);
		
		this.actionSave.setVisible(false);
		this.actionUnAudit.setVisible(false);
		
		if(FDCBillStateEnum.AUDITTED.equals(this.editData.getState())&&this.oprtState.equals(OprtState.VIEW)){
 			this.kdtEntry.setEditable(true);
 			this.actionEditEntry.setEnabled(true);
 			this.kdtEntry.getColumn("saleNumber").getStyleAttributes().setLocked(false);
 		}else{
 			this.kdtEntry.getColumn("saleNumber").getStyleAttributes().setLocked(true);
 			this.actionEditEntry.setEnabled(false);
 		}
	}

	public void loadInvoiceInfo(InvoiceBatchImportEntryCollection cols) {

		this.kdtEntry.checkParsed();
		IRow r = null;
		int size = cols.size();
		if (size > 0) {
			this.kdtEntry.removeRows();
		}
		for (int i = 0; i < size; i++) {
			r = this.kdtEntry.addRow();
			fillDataToRow(cols.get(i), r);
		}
	}

	private void fillDataToRow(InvoiceBatchImportEntryInfo entry, IRow r) {
		// TODO Auto-generated method stub
		r.getCell("revId").setValue(entry.getRevId());
		r.getCell("revType").setValue(entry.getRevType());
		r.getCell("moneyDefineName").setValue(entry.getMoneyDefineName());
		r.getCell("roomName").setValue(entry.getRoomName());
		r.getCell("appDate").setValue(entry.getAppDate());
		r.getCell("appAmountWithoutTax").setValue(
				entry.getAppAmountWithoutTax());
		r.getCell("startDate").setValue(entry.getStartDate());
		r.getCell("endDate").setValue(entry.getEndDate());
		r.getCell("alreadyInvoiceAmt").setValue(entry.getAlreadyInvoiceAmt());
		r.getCell("invoiceAmt").setValue(
				entry.getAppAmountWithoutTax() != null
						&& entry.getAlreadyInvoiceAmt() != null ? entry
						.getAppAmountWithoutTax().subtract(
								entry.getAlreadyInvoiceAmt()) : entry
						.getAppAmountWithoutTax());
		r.getCell("taxRate").setValue(entry.getTaxRate());
		r.getCell("invoiceName").setValue(entry.getInvoiceName());
		r.getCell("invoiceType").setValue(entry.getInvoiceType());
		r.getCell("taxIdentified").setValue(entry.getTaxIdentified());
		r.getCell("addAndPhone").setValue(entry.getAddAndPhone());
		r.getCell("bankAndAccount").setValue(entry.getBankAndAccount());
		r.getCell("taxCode").setValue(entry.getTaxCode());
		r.getCell("contractName").setValue(entry.getTenancybill().getName());
		r.getCell("contractName").setUserObject(entry.getTenancybill());
		r.getCell("custName").setValue(entry.getCustomer().getName());
		r.getCell("custName").setUserObject(entry.getCustomer());
		r.getCell("amount").setValue(entry.getAmount());
		r.setUserObject(entry);
	}

	@Override
	public void actionSelectContract_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionSelectContract_actionPerformed(e);
		UIContext uiContext = new UIContext();
		uiContext.put("ownerUI", this);
		uiContext.put("sellProject", editData.getSellProject());

		UIFactory.createUIFactory().create(
				SelectTenancyContractUI.class.getName(), uiContext).show();
	}

	@Override
	public void actionExportInvoice_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionExportInvoice_actionPerformed(e);
		// 检查是否是审批状态，否则不能导出
		if (editData.getState()==null||!editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
			FDCMsgBox.showError("非审批状态的单据不能导出开票明细.");
			this.abort();
		}
		KDFileChooser ch = new KDFileChooser();

		ch.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				if (file.isDirectory())
					return true;
				String extension = FileUtil.getExtension(file);
				if (extension != null)
					return extension.equalsIgnoreCase("xlsx");
				else
					return false;

			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "EXCEL ( *.xlsx )";
			}
		});

		ch.showSaveDialog(this);
		File f = ch.getSelectedFile();
		if (f == null) {
			return;
		}
		int index = f.getAbsolutePath().lastIndexOf(f.separator);
		String p = f.getAbsolutePath().substring(0, index);
		f = new File(p + f.separator + f.getName() + ".xlsx");

		SXSSFWorkbook wb = new SXSSFWorkbook();
		Sheet s = wb.createSheet();
		Cell c = null;

		// 生成标题
		Row r = s.createRow(0);
		c = r.createCell(0);
		c.setCellValue("销售编号");

		c = r.createCell(1);
		c.setCellValue("客户名称");

		c = r.createCell(2);
		c.setCellValue("税号");

		c = r.createCell(3);
		c.setCellValue("地址");

		c = r.createCell(4);
		c.setCellValue("银行帐号");

		c = r.createCell(5);
		c.setCellValue("备注");

		c = r.createCell(6);
		c.setCellValue("应税劳务名");

		c = r.createCell(7);
		c.setCellValue("规格型号");

		c = r.createCell(8);
		c.setCellValue("计量单位");

		c = r.createCell(9);
		c.setCellValue("数量");

		c = r.createCell(10);
		c.setCellValue("金额");

		c = r.createCell(11);
		c.setCellValue("税率");

		c = r.createCell(12);
		c.setCellValue("折扣");

		c = r.createCell(13);
		c.setCellValue("零税率标识");

		c = r.createCell(14);
		c.setCellValue("税收分类编号");

		c = r.createCell(15);
		c.setCellValue("版本号");

		// 生成开票信息行

		createInvoiceEntry(s);

		wb.write(new FileOutputStream(f));
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("update T_TEN_InvoiceBatchImport set FIsExported = 1 where fid = ?");
		builder.addParam(editData.getId()+"");
		builder.executeUpdate();

		FDCMsgBox.showInfo("导出成功.");
	}

	private void createInvoiceEntry(Sheet s) {

		InvoiceBatchImportEntryCollection cols = editData.getEntry();
		InvoiceBatchImportEntryInfo b = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		CellStyle cstyle = (XSSFCellStyle) s.getWorkbook().createCellStyle();
		DataFormat df = s.getWorkbook().createDataFormat();
		cstyle.setDataFormat(df.getFormat("##0.00"));

		for (int i = 0; i < cols.size(); i++) {
			b = cols.get(i);
			Row r = s.createRow(i + 1);

			Cell c = r.createCell(0);
			c.setCellValue(b.getSaleNumber());

			c = r.createCell(1);
			c.setCellValue(b.getInvoiceName());

			c = r.createCell(2);
			c.setCellValue(b.getTaxIdentified());

			c = r.createCell(3);
			c.setCellValue(b.getAddAndPhone());

			c = r.createCell(4);
			c.setCellValue(b.getBankAndAccount());

			c = r.createCell(5);
			String value = b.getRoomName()+(b.getDescription()==null?"":b.getDescription());
			c.setCellValue(value);

			c = r.createCell(6);
			c.setCellValue(b.getMoneyDefineName());

			String special = sdf.format(b.getStartDate()) + "-"	+ sdf.format(b.getEndDate());
			c = r.createCell(7);
			c.setCellValue(special);

			c = r.createCell(9);
			if(b.getAmount()!=null){
				c.setCellValue(b.getAmount().doubleValue());
			}
			
			c = r.createCell(10);
			c.setCellStyle(cstyle);
			c.setCellValue(b.getAppAmountWithoutTax().doubleValue());

			c = r.createCell(11);
			c.setCellStyle(cstyle);
			c.setCellValue(FDCHelper.divide(b.getTaxRate(),
					FDCHelper.ONE_HUNDRED).doubleValue());

			c = r.createCell(14);
			c.setCellValue(b.getTaxCode());

			c = r.createCell(15);
			c.setCellValue("13.0");

		}

	}

	@Override
	public SelectorItemCollection getSelectors() {
		// TODO Auto-generated method stub
		SelectorItemCollection sic = super.getSelectors();
		sic.add("state");
		sic.add("entry.*");
		sic.add("entry.saleNumber");
		return sic;
	}

	@Override
	public void actionEditEntry_actionPerformed(ActionEvent e) throws Exception {
		if (MsgBox.showConfirm2("是否确定修改？") == MsgBox.CANCEL) {
			return;
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("saleNumber"));
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			if(this.kdtEntry.getRow(i).getCell("saleNumber").getValue()==null){
				FDCMsgBox.showError("销售编号不能为空！ ");
				abort();
			}
			InvoiceBatchImportEntryInfo entry=(InvoiceBatchImportEntryInfo) this.kdtEntry.getRow(i).getUserObject();
			String saleNumber=entry.getSaleNumber();
			entry.setSaleNumber(this.kdtEntry.getRow(i).getCell("saleNumber").getValue().toString());
			InvoiceBatchImportEntryFactory.getRemoteInstance().updatePartial(entry,sic);
			
			InvoiceBillCollection col=InvoiceBillFactory.getRemoteInstance().getInvoiceBillCollection("select saleNumber from where sourceBillId='"+entry.getId()+"'");
			for(int j=0;j<col.size();j++){
				InvoiceBillInfo info=col.get(j);
				info.setSaleNumber(this.kdtEntry.getRow(i).getCell("saleNumber").getValue().toString());
				InvoiceBillFactory.getRemoteInstance().updatePartial(info, sic);
			}
		}
		
	}

}