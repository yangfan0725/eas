/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.swing.ActionMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.sellhouse.ContractInvoiceEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ContractInvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.ContractInvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.Invoice;
import com.kingdee.eas.fdc.sellhouse.InvoiceTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fdc.tenancy.client.OtherBillDataProvider;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

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
		
		
		this.kdtEntry.getColumn("moneyDefine").setRequired(true);
		this.kdtEntry.getColumn("totalAmountNoTax").setRequired(true);
		this.kdtEntry.getColumn("rate").setRequired(true);
		this.kdtEntry.getColumn("rateAmount").setRequired(true);
		this.kdtEntry.getColumn("totalAmount").setRequired(true);
		
		this.kdtEntry.getColumn("rateAmount").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("totalAmountNoTax").getStyleAttributes().setLocked(true);
		
		KDBizPromptBox box = new KDBizPromptBox();
    	KDTDefaultCellEditor editor = new KDTDefaultCellEditor(box);
    	box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
        box.setDisplayFormat("$name$");
        box.setEditFormat("$number$");
        box.setCommitFormat("$number$");
        
        EntityViewInfo entityViewInfo = new EntityViewInfo();
        FilterInfo filterInfo = new FilterInfo();
        entityViewInfo.setFilter(filterInfo);
        filterInfo.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));
    	box.setEntityViewInfo(entityViewInfo);
    	
    	this.kdtEntry.getColumn("moneyDefine").setEditor(editor); 
        
        ObjectValueRender kdtEntrys_moneyDefine_OVR = new ObjectValueRender();
        kdtEntrys_moneyDefine_OVR.setFormat(new BizDataFormat("$name$"));
        this.kdtEntry.getColumn("moneyDefine").setRenderer(kdtEntrys_moneyDefine_OVR);
    	
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setNegatived(false);
		amount.setPrecision(2);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		this.kdtEntry.getColumn("totalAmountNoTax").setEditor(amountEditor);
		this.kdtEntry.getColumn("totalAmountNoTax").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("totalAmountNoTax").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.kdtEntry.getColumn("totalAmount").setEditor(amountEditor);
		this.kdtEntry.getColumn("totalAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("totalAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.kdtEntry.getColumn("rate").setEditor(amountEditor);
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

		if(cbInvoiceType.getSelectedItem()!=null){
			if(cbInvoiceType.getSelectedItem().equals(InvoiceTypeEnum.RECEIPT)){
				row.getCell("rate").setValue(FDCHelper.ZERO);
			}else{
				row.getCell("rate").setValue(this.txtSaleRate.getBigDecimalValue());
			}
		}
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
				
				if(cbInvoiceType.getSelectedItem()!=null){
					if(cbInvoiceType.getSelectedItem().equals(InvoiceTypeEnum.RECEIPT)){
						row.getCell("rate").setValue(new BigDecimal(0));
					}else{
						row.getCell("rate").setValue(this.txtSaleRate.getBigDecimalValue());
					}
				}
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
			BigDecimal totalAmountNoTax=FDCHelper.ZERO;
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				totalAmount=FDCHelper.add(totalAmount, this.kdtEntry.getRow(i).getCell("totalAmount").getValue());
				amount=FDCHelper.add(amount,this.kdtEntry.getRow(i).getCell("rateAmount").getValue());
				totalAmountNoTax=FDCHelper.add(totalAmountNoTax, this.kdtEntry.getRow(i).getCell("totalAmountNoTax").getValue()); 
			}
			this.txtTotalAmount.setValue(totalAmount);
			this.txtCapital.setText(FDCClientHelper.getChineseFormat(totalAmount, false));
			this.txtAmount.setValue(amount);
			this.txtTotalAmountNoTax.setValue(totalAmountNoTax);
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
		
		if(this.editData.getRoom()!=null){
			try {
				TransactionInfo info=SHEManageHelper.getTransactionInfo(null, this.editData.getRoom());
				this.txtCustomer.setText(info.getCustomerNames());
			} catch (BOSException e) {
				e.printStackTrace();
			}
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
    	sel.add("amount");
		return sel;
	}
	protected IObjectValue createNewData() {
		ContractInvoiceInfo info= new ContractInvoiceInfo();
		RoomInfo room = (RoomInfo)getUIContext().get("room");

		if(room!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("building.sellProject.name");
			sel.add("*");
			try {
				room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId()),sel);
			} catch (EASBizException e1) {
				e1.printStackTrace();
			} catch (BOSException e1) {
				e1.printStackTrace();
			}
			info.setRoom(room);
		}
		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
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
		FDCClientVerifyHelper.verifyEmpty(this, this.txtPaymentMethod);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtRoom);
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"发票明细不能为空！");
			SysUtil.abort();
		}
		for(int i = 0; i < this.kdtEntry.getRowCount(); i++){
			IRow row=this.kdtEntry.getRow(i);
			if(row.getCell("moneyDefine").getValue()==null){
				FDCMsgBox.showWarning(this,"款项不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("moneyDefine"));
				SysUtil.abort();
			}
			if(row.getCell("totalAmountNoTax").getValue()==null){
				FDCMsgBox.showWarning(this,"金额（不含税）不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("totalAmountNoTax"));
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
			if(row.getCell("totalAmount").getValue()==null){
				FDCMsgBox.showWarning(this,"金额（含税）不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("totalAmount"));
				SysUtil.abort();
			}
		}
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		if("totalAmount".equals(this.kdtEntry.getColumnKey(e.getColIndex()))||"rate".equals(this.kdtEntry.getColumnKey(e.getColIndex()))){
			BigDecimal div=FDCHelper.add(new BigDecimal(1), FDCHelper.divide(this.kdtEntry.getRow(e.getRowIndex()).getCell("rate").getValue(), new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP));
			this.kdtEntry.getRow(e.getRowIndex()).getCell("totalAmountNoTax").setValue(FDCHelper.divide(this.kdtEntry.getRow(e.getRowIndex()).getCell("totalAmount").getValue(), div, 2, BigDecimal.ROUND_HALF_UP));
			this.kdtEntry.getRow(e.getRowIndex()).getCell("rateAmount").setValue(FDCHelper.multiply(this.kdtEntry.getRow(e.getRowIndex()).getCell("totalAmountNoTax").getValue(), FDCHelper.divide(this.kdtEntry.getRow(e.getRowIndex()).getCell("rate").getValue(), new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP)));
			
			BigDecimal totalAmount=FDCHelper.ZERO;
			BigDecimal amount=FDCHelper.ZERO;
			BigDecimal totalAmountNoTax=FDCHelper.ZERO;
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				totalAmount=FDCHelper.add(totalAmount, this.kdtEntry.getRow(i).getCell("totalAmount").getValue());
				amount=FDCHelper.add(amount,this.kdtEntry.getRow(i).getCell("rateAmount").getValue());
				totalAmountNoTax=FDCHelper.add(totalAmountNoTax, this.kdtEntry.getRow(i).getCell("totalAmountNoTax").getValue()); 
			}
			this.txtTotalAmount.setValue(totalAmount);
			this.txtCapital.setText(FDCClientHelper.getChineseFormat(totalAmount, false));
			this.txtAmount.setValue(amount);
			this.txtTotalAmountNoTax.setValue(totalAmountNoTax);
		}
	}
	protected void cbInvoiceType_itemStateChanged(ItemEvent e) throws Exception {
		if(cbInvoiceType.getSelectedItem()!=null){
			BigDecimal rate=FDCHelper.ZERO;
			if(cbInvoiceType.getSelectedItem().equals(InvoiceTypeEnum.RECEIPT)){
				this.kdtEntry.getColumn("rate").getStyleAttributes().setLocked(true);
				rate=FDCHelper.ZERO;
			}else{
				this.kdtEntry.getColumn("rate").getStyleAttributes().setLocked(false);
				rate=this.txtSaleRate.getBigDecimalValue();
			}
			
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				this.kdtEntry.getRow(i).getCell("rate").setValue(rate);
				
				this.kdtEntry.getRow(i).getCell("rateAmount").setValue(FDCHelper.multiply(this.kdtEntry.getRow(i).getCell("totalAmountNoTax").getValue(), FDCHelper.divide(this.kdtEntry.getRow(i).getCell("rate").getValue(), new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP)));
				this.kdtEntry.getRow(i).getCell("totalAmount").setValue(FDCHelper.add(this.kdtEntry.getRow(i).getCell("totalAmountNoTax").getValue(), this.kdtEntry.getRow(i).getCell("rateAmount").getValue()));
			}
			
			BigDecimal totalAmount=FDCHelper.ZERO;
			BigDecimal amount=FDCHelper.ZERO;
			BigDecimal totalAmountNoTax=FDCHelper.ZERO;
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				totalAmount=FDCHelper.add(totalAmount, this.kdtEntry.getRow(i).getCell("totalAmount").getValue());
				amount=FDCHelper.add(amount,this.kdtEntry.getRow(i).getCell("rateAmount").getValue());
				totalAmountNoTax=FDCHelper.add(totalAmountNoTax, this.kdtEntry.getRow(i).getCell("totalAmountNoTax").getValue()); 
			}
			this.txtTotalAmount.setValue(totalAmount);
			this.txtCapital.setText(FDCClientHelper.getChineseFormat(totalAmount, false));
			this.txtAmount.setValue(amount);
			this.txtTotalAmountNoTax.setValue(totalAmountNoTax);
		}
	}
	 protected void checkBeforeEditOrRemove(String warning)throws BOSException{
		 FDCBillStateEnum state = getFDCBillInfo().getState();
		 if(state != null && (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL|| state == FDCBillStateEnum.INVALID)){
			 MsgBox.showWarning(this, ContractClientUtils.getRes(warning));
			 SysUtil.abort();
		 }
	 }
	 public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
			ArrayList idList = new ArrayList();
			if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
				idList.add(editData.getString("id"));
			}
			if (idList == null || idList.size() == 0 || getTDQueryPK() == null
					|| getTDFileName() == null) {
				MsgBox.showWarning(this, EASResource.getString(
						"com.kingdee.eas.fdc.basedata.client.FdcResource",
				"cantPrint"));
				return;
			}
			ContractInvoiceDataProvider data = new ContractInvoiceDataProvider(editData.getString("id"),getTDQueryPK(),this.txtCustomer.getText());
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
					.getWindowAncestor(this));
		}

		public void actionPrintPreview_actionPerformed(ActionEvent e)
		throws Exception {
			ArrayList idList = new ArrayList();
			if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
				idList.add(editData.getString("id"));
			}
			if (idList == null || idList.size() == 0 || getTDQueryPK() == null
					|| getTDFileName() == null) {
				MsgBox.showWarning(this, EASResource.getString(
						"com.kingdee.eas.fdc.basedata.client.FdcResource",
				"cantPrint"));
				return;

			}
			ContractInvoiceDataProvider data = new ContractInvoiceDataProvider(
					editData.getString("id"),getTDQueryPK(),this.txtCustomer.getText());
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
					.getWindowAncestor(this));
		}
		protected String getTDFileName() {
			return "/bim/fdc/sellhouse/ContractInvoice";
		}

		protected IMetaDataPK getTDQueryPK() {
			return new MetaDataPK(
			"com.kingdee.eas.fdc.sellhouse.app.ContractInvoicePrintQuery");
		}
}