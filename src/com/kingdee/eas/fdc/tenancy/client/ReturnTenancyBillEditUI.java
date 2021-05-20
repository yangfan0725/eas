/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.ext.IFilterInfoProducer;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.util.StringUtil;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.eqm.common.core.client.DateTimePickerEditor;
import com.kingdee.eas.fdc.basedata.ChinaNumberFormat;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyContractCollection;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyContractInfo;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyRentEntryCollection;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyRentEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ReturnTenancyBillEditUI extends AbstractReturnTenancyBillEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ReturnTenancyBillEditUI.class);

	/**
	 * output class constructor
	 */
	public ReturnTenancyBillEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		editData.getTenancyContractEntry().clear();
		editData.setReturnYear(this.txtReturnYear.getBigDecimalValue());
		ReturnTenancyContractCollection  contractColl = new ReturnTenancyContractCollection();
		
		//读取分录信息 1-4行是合同 信息 5-14行是租金信息
		KDTable table = this.kdtTenancyContractEntry;
		table.checkParsed();
		int count = table.getRowCount()/14;
		ReturnTenancyContractInfo contract = null;
		int startIndex = -1;
		for(int i=0;i<count;i++){
			contract = new ReturnTenancyContractInfo();
			startIndex = i*14;
			//签约客户
			if(table.getCell(i*14, 3).getValue() != null){
				String signCustomer =  table.getCell(i*14, 3).getValue().toString();
				contract.setSignCustomer(signCustomer);
			}
			//租赁年限
			if(table.getCell(startIndex+1, 3).getValue() != null){
				BigDecimal years = FDCHelper.toBigDecimal(table.getCell(startIndex+1, 3).getValue());
				contract.setSignYears(years);
			}
			//开始
			Date startDate = null;
			if(table.getCell(startIndex+2, 3).getValue() != null ){
				startDate = (Date)table.getCell(startIndex+2, 3).getValue();
				contract.setSignStartDate(startDate);
			}
			//结束日期
			if(table.getCell(startIndex+3, 3).getValue() != null){
				Date endDate = (Date) table.getCell(startIndex+3, 3).getValue();
				contract.setSignEndDate(endDate);
			}
			
			ReturnTenancyRentEntryCollection rentCollection = new ReturnTenancyRentEntryCollection();
			Calendar start = Calendar.getInstance();
			if(startDate != null){
				start.setTime(startDate);
			}
			int y = start.get(Calendar.YEAR);
			int entryStartIndex =startIndex+4; 
			for(int j=0;j<10;j++){
				
				ReturnTenancyRentEntryInfo entryInfo = new ReturnTenancyRentEntryInfo();
				entryInfo.setContract(contract);
				entryInfo.setYear(y+j);
				if(table.getCell(entryStartIndex+j, 3).getValue() != null){
					entryInfo.setAmtOfRent(FDCHelper.toBigDecimal(table.getCell(entryStartIndex+j, 3).getValue()));
				}
				rentCollection.add(entryInfo);
			}
			
			contract.getRentEntry().addCollection(rentCollection);
			contractColl.add(contract);
			table.getCell(i*14, 3).setUserObject(contract);
		}
		
		editData.getTenancyContractEntry().addCollection(contractColl);
		
		
	}
	@Override
	public boolean destroyWindow() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void fetchInitData() throws Exception {
		// TODO Auto-generated method stub
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
	 * /** output actionPageSetup_actionPerformed
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
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyInputForSubmint();
		super.actionSubmit_actionPerformed(e);
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
		this.kdtTenancyContractEntry.removeRows();
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

	@Override
	protected void attachListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void detachListeners() {
		// TODO Auto-generated method stub

	}

	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) {

		// 检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		boolean b = editData != null && editData.getState() != null
				&& editData.getState().equals(state);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}

		if (getOprtState().equals(STATUS_EDIT)) {
			String warn = null;
			if (state.equals(FDCBillStateEnum.AUDITTED)) {
				warn = "cantAuditEditState";
			} else {
				warn = "cantUnAuditEditState";
			}
			MsgBox.showWarning(this, FDCClientUtils.getRes(warn));
			SysUtil.abort();
		}
	}

	protected void checkBeforeEditOrRemove(String warning) {
		FDCBillStateEnum state = editData.getState();
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING
						|| state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL)) {
			MsgBox.showWarning(this, ContractClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return ReturnTenancyBillFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return this.kdtEntry;
	}

	@Override
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtNumber;
	}

	protected void setSaveActionStatus() {
		if (editData.getState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getState() == null)
			editData.setState(FDCBillStateEnum.SAVED);
		setSaveAction(true);
		verifyInputForSave();
		super.actionSave_actionPerformed(e);

		handleOldData();

	}

	@Override
	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException {
		// TODO Auto-generated method stub
		/*
		 * 2008-09-27编码规则的取值取错了，应当取FDCBillInfo中关联的ID
		 */
		String currentOrgId = "";// FDCClientHelper.getCurrentOrgId();
		if (((FDCBillInfo) editData).getOrgUnit() != null) {
			currentOrgId = ((FDCBillInfo) editData).getOrgUnit().getId()
					.toString();
		} else {
			currentOrgId = SysContext.getSysContext().getCurrentSaleUnit()
					.getId().toString();
		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		/*
		 * 2008-09-27如果不是新增状态，直接返回 然后分别判断成本中心和当前组织是否存在编码规则
		 */
		if (!STATUS_ADDNEW.equals(this.oprtState)) {
			return;
		}
		boolean isExist = true;
		if (currentOrgId.length() == 0
				|| !iCodingRuleManager.isExist(editData, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(editData, currentOrgId)) {
				//EditUI提供了方法，但没有调用，在onload后调用，以覆盖抽象类loadfields里面的调用（该调用没有处理断号选择
				// ）
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData,
					currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			} else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId)) { //此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						//要判断是否存在断号,是则弹出,否则不弹///////////////////////////////////
						// ///////
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}

			setNumberTextEnabled();
		}
	}

	protected void setNumberTextEnabled() {

		if (getNumberCtrl() != null) {
			SaleOrgUnitInfo currentSaleUnit = SysContext.getSysContext()
					.getCurrentSaleUnit();

			if (currentSaleUnit != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(
						editData, currentSaleUnit.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
			}
		}
	}

	public boolean checkBeforeWindowClosing() {
		if (hasWorkThreadRunning()) {
			return false;
		}

		if (getTableForPrintSetting() != null) {
			this.savePrintSetting(this.getTableForPrintSetting());
		}

		boolean b = true;

		if (!b) {
			return b;
		} else {

			// 校验storeFields()是否抛出异常
			// Exception err = verifyStoreFields();

			// storeFields()抛出异常或者editdata有改变，询问是否保存退出
			if (isModify()) {
				// editdata有改变
				int result = MsgBox.showConfirm3(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Confirm_Save_Exit"));

				if (result == KDOptionPane.YES_OPTION) {

					try {
						if (editData.getState() == null
								|| editData.getState() == FDCBillStateEnum.SAVED) {
							actionSave.setDaemonRun(false);
							// actionSubmit.actionPerformed(null);
							// by jakcy 2005-1-6
							// 会出空指针错，因为beforeAction()会使用ActionEvent。
							ActionEvent event = new ActionEvent(btnSave,
									ActionEvent.ACTION_PERFORMED, btnSave
											.getActionCommand());
							actionSave.actionPerformed(event);
							return !actionSave.isInvokeFailed();
						} else {
							actionSubmit.setDaemonRun(false);
							// actionSubmit.actionPerformed(null);
							// by jakcy 2005-1-6
							// 会出空指针错，因为beforeAction()会使用ActionEvent。
							ActionEvent event = new ActionEvent(btnSubmit,
									ActionEvent.ACTION_PERFORMED, btnSubmit
											.getActionCommand());
							actionSubmit.actionPerformed(event);
							return !actionSubmit.isInvokeFailed();
							// actionSubmit_actionPerformed(event);
						}
						// return true;
					} catch (Exception exc) {
						// handUIException(exc);
						return false;
					}

				} else if (result == KDOptionPane.NO_OPTION) {
					// stopTempSave();
					return true;
				} else {
					return false;
				}
			} else {
				// stopTempSave();
				return true;
			}

		}

	}

	@Override
	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		ReturnTenancyBillInfo info = new ReturnTenancyBillInfo();
		info.setName(UUID.randomUUID().toString());
		info.setBizDate(new Date());
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		info.setLastUpdateUser(SysContext.getSysContext().getCurrentUserInfo());
		info.setStartDate(new Date());
		info.setReturnYear(BigDecimal.ONE);
		info.setEndDate(FDCDateHelper.addDays(info.getStartDate(), 360));
		Map uiContext = getUIContext();
		if (uiContext.get("sellProject") != null) {
			info.setProject((SellProjectInfo) uiContext.get("sellProject"));
		}

		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit()
				.castToFullOrgUnitInfo());
		return info;
	}

	@Override
	protected IObjectValue createNewDetailData(KDTable table) {
		// TODO Auto-generated method stub
		return new ReturnTenancyEntryInfo();
	}

	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		this.txtName.setVisible(false);
		this.contName.setVisible(false);
		this.pkCreateTime.setDateEnabled(true);
		this.pkLastUpdateTime.setDateEnabled(true);
		this.txtCustomer.setEnabled(false);
		this.contSalesCustomer.setEnabled(false);
		this.contSalesPrice.setEnabled(false);
		this.txtPrice.setEnabled(false);
		
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionCopyLine.setVisible(false);

		this.contTenancyContract.addButton(this.btnAddContract);
		this.contTenancyContract.addButton(this.btnRemoveContract);
		
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		
		this.kdtEntry.getColumn("rateOfReturn").getStyleAttributes().setHided(true);
		
		
		if(getUIContext().get("fromTC")!=null){
			RoomInfo room = (RoomInfo) getUIContext().get("room");
			SellProjectInfo project = room.getBuilding().getSellProject();
			editData.setProject(project);
			this.prmtProject.setData(project);
			
			this.editData.setRoom(room);
			this.prmtRoom.setData(room);
		}
		

		this.prmtRoom.setFilterInfoProducer(new IFilterInfoProducer() {

			public FilterInfo getFilterInfo() {
				// TODO Auto-generated method stub
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", editData
								.getProject().getId()));
				filter.getFilterItems().add(
						new FilterItemInfo("isReturn", Boolean.TRUE));
				filter.getFilterItems().add(new FilterItemInfo("id", "select froomid from T_TEN_RETURNTENANCYBILL te where te.fproject='"+ editData.getProject().getId()+"'",CompareType.NOTINNER));
				return filter;
			}

			public void setCurrentCtrlUnit(CtrlUnitInfo arg0) {
				// TODO Auto-generated method stub

			}

			public void setCurrentMainBizOrgUnit(OrgUnitInfo arg0, OrgType arg1) {
				// TODO Auto-generated method stub

			}

		});
		this.kdtTenancyContractEntry.checkParsed();
		FDCTableHelper.disableAutoAddLine(this.kdtEntry);
		FDCTableHelper.disableAutoAddLineDownArrow(this.kdtEntry);
        
	}

	@Override
	protected void prmtRoom_dataChanged(DataChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.prmtRoom_dataChanged(e);
		if(e.getNewValue() == null ){
			return;
		}
		if (e.getNewValue() != null && e.getNewValue().equals(e.getOldValue())) {
			return;
		}

		RoomInfo room = (RoomInfo) e.getNewValue();
		
		FDCSQLBuilder builder  = new FDCSQLBuilder();
		builder.appendSql("select sg.fdealtotalamount dealAmt,sg.fcustomernames custName from t_she_signmanage sg where froomid = ? ");
		builder.addParam(room.getId().toString());
		IRowSet rs = builder.executeQuery();
		
		while(rs.next()){
			this.txtPrice.setValue(rs.getBigDecimal(1));
			this.txtCustomer.setText(rs.getString(2));
		}
		
		

		this.txtArea.setValue(room.getRoomArea());
		this.txtState.setText(room.getSellState().getAlias());
		this.txtBuildArea.setValue(room.getBuildingArea());

	}
	
	
	private void verifyTenancyContract(){
		KDTable table = this.kdtTenancyContractEntry;
		table.checkParsed();
		int count = table.getRowCount()/14;
		int startIndex = -1;
		for(int i=0;i<count;i++){
			startIndex = i*14;
			//签约客户
			if(table.getCell(i*14, 3).getValue() == null){
				FDCMsgBox.showError("合同"+ChinaNumberFormat.getChinaNumberValue(i+1)+" 签约商户不能为空");
		    	 SysUtil.abort();
			}
			//租赁年限
			if(table.getCell(startIndex+1, 3).getValue() == null){
				FDCMsgBox.showError("合同"+ChinaNumberFormat.getChinaNumberValue(i+1)+" 租赁年限不能为空");
		    	 SysUtil.abort();
			}
			//开始
			if(table.getCell(startIndex+2, 3).getValue() == null ){
				FDCMsgBox.showError("合同"+ChinaNumberFormat.getChinaNumberValue(i+1)+" 开始日期不能为空");
		    	 SysUtil.abort();
			}
			//结束日期
			if(table.getCell(startIndex+3, 3).getValue() == null){
				FDCMsgBox.showError("合同"+ChinaNumberFormat.getChinaNumberValue(i+1)+" 结束日期不能为空");
		    	 SysUtil.abort();
				
			}
			
		}
	}
	
	@Override
	protected void verifyInputForSave() throws Exception {
		// TODO Auto-generated method stub
		super.verifyInputForSave();
		
	     if(prmtRoom.getData() == null){
	    	 FDCMsgBox.showError("房间不能为空");
	    	 SysUtil.abort();
	     }
	     
	     if(StringUtil.isEmpty(txtReturnYear.getText()) ){
	    	 FDCMsgBox.showError("返租年限不能为空");
	    	 SysUtil.abort();
	     }
	     verifyTenancyContract();
	}
	
	@Override
	protected void verifyInputForSubmint() throws Exception {
		// TODO Auto-generated method stub
		super.verifyInputForSubmint();
		verifyInputForSave();
		if(StringUtil.isEmpty(this.txtNumber.getText())){
			FDCMsgBox.showError("编码不能为空");
	    	SysUtil.abort();
		}
		
	}

	@Override
	protected void pkEndDate_dataChanged(DataChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.pkEndDate_dataChanged(e);
		if (e.getNewValue() != null && e.getNewValue().equals(e.getOldValue())) {
			return;
		}

		Date endYear = (Date) e.getNewValue();
		Date startDate = (Date) this.pkStartDate.getValue();

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endYear);
		
		generateEntry(startDate);

	}

	@Override
	protected void pkStartDate_dataChanged(DataChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.pkStartDate_dataChanged(e);

		if (e.getNewValue() != null && e.getNewValue().equals(e.getOldValue())) {
			return;
		}

		Date startDate = (Date) e.getNewValue();
		Date endYear = (Date) this.pkEndDate.getValue();
		
		Calendar curr = Calendar.getInstance();
		int currYear = curr.get(Calendar.YEAR);
		
		curr.setTime(startDate);
		int startYear = curr.get(Calendar.YEAR);
		
		if(startYear < currYear){
//			FDCMsgBox.showError("开始日期不能小于当前年份");
//			abort();
		}
		
		generateEntry(startDate);

	}

	@Override
	protected void txtReturnYear_dataChanged(DataChangeEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.txtReturnYear_dataChanged(e);

		if (e.getNewValue() != null && e.getNewValue().equals(e.getOldValue())) {
			return;
		}

//		修改结束日期
		Date startDate = (Date) pkStartDate.getValue();

		generateEntry(startDate);

	}

	private void checkDate(Date startDate, Date endDate) {
		if (endDate != null && startDate != null
				&& endDate.getTime() - startDate.getTime() > 0) {
			FDCMsgBox.showError("开始日期不能大于结束日期!");
			abort();
		}
	}

	private void generateEntry(Date startYears) {

		int years = 1;
		if(this.txtReturnYear.getText()!=null) 
			years = (int)Math.ceil(FDCHelper.toBigDecimal(this.txtReturnYear.getText()).doubleValue());
		
		this.kdtEntry.removeRows();
 		int startYear = startYears.getYear();
		this.kdtEntry.checkParsed();
		IRow row = null;
		for (int i = 0; i <= years; i++) {
			row = this.kdtEntry.addRow();
			row.getCell("year").setValue(1900+startYear + i);
		}

	}

	/*
	 * output btnAddContract_actionPerformed method
	 */
	protected void btnAddContract_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnAddContract_actionPerformed(e);
        KDTable table = this.kdtTenancyContractEntry;
        int existsRowCount = table.getRowCount();
        
        logger.error(existsRowCount+":"+existsRowCount/14);
        table.addRows(14);
        table.getCell(existsRowCount+0,0).setValue(ChinaNumberFormat.getChinaNumberValue(existsRowCount/14+1));
        table.getCell(existsRowCount+0,1).setValue("合同信息");
        table.getCell(existsRowCount+0,2).setValue("签约商户");
        table.getCell(existsRowCount+0,3).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
        table.getCell(existsRowCount+1,2).setValue("租赁年限(年)");
        table.getCell(existsRowCount+1,3).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
        table.getCell(existsRowCount+2,2).setValue("租赁合同开始日期");
        table.getCell(existsRowCount+2,3).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
       
        
        
        KDDatePicker datePicker = new KDDatePicker();
	     datePicker.setDatePattern("yyyy-MM-dd");
	     KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(datePicker);
        
        table.getCell(existsRowCount+2,3).setEditor(DateTimePickerEditor.getInstance(false));
        table.getCell(existsRowCount+2,3).setRenderer(getDateRender());
       
        table.getCell(existsRowCount+3,2).setValue("租赁合同结束日期");
        table.getCell(existsRowCount+3,3).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
        table.getCell(existsRowCount+3,3).setEditor(dateEditor);
        table.getCell(existsRowCount+3,3).setRenderer(getDateRender());
        
        table.getCell(existsRowCount+4,1).setValue("租金信息");
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        for(int i=0;i<10;i++){
        	table.getCell(existsRowCount+4+i,2).setRenderer(getYearRender());
        	table.getCell(existsRowCount+4+i,2).setValue(year+i);
        	table.getCell(existsRowCount+4+i,3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
        	table.getCell(existsRowCount+4+i,3).getStyleAttributes().setNumberFormat("###.00");
        }
        
        
        table.getMergeManager().mergeBlock(existsRowCount,0, existsRowCount+13, 0);
        table.getMergeManager().mergeBlock(existsRowCount, 1, existsRowCount+3, 1);
        table.getMergeManager().mergeBlock(existsRowCount+4, 1, existsRowCount+13, 1);
        
        int beginNumber = existsRowCount;
        setNode(table,beginNumber,4,"合同信息");
        setNode(table,existsRowCount+4,10,"租金信息");
        
        table.addKDTEditListener(new KDTEditListener(){

			public void editCanceled(KDTEditEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void editStarted(KDTEditEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void editStarting(KDTEditEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void editStopped(KDTEditEvent e) {
				// TODO Auto-generated method stub
				int  curRowIndex = e.getRowIndex();
				
				if(curRowIndex%14 == 1 && e.getColIndex() == 3 ){//年数
					if(e.getValue()!=null 
							&& kdtTenancyContractEntry.getCell(e.getRowIndex()+1, 3).getValue() != null 
							&&  kdtTenancyContractEntry.getCell(e.getRowIndex()+2, 3).getValue() !=null){
						
						int diff = Integer.parseInt(e.getValue()+"");
						if(diff<0){
							FDCMsgBox.showError("租赁年限不能小于0.");
							return;
						}
						
						Date startDate = (Date) kdtTenancyContractEntry.getCell(e.getRowIndex()+1, 3).getValue();
						Calendar c =Calendar.getInstance();
						c.setTime(startDate);
						
						Calendar sCalendar = Calendar.getInstance();
						sCalendar.setTime(startDate);
						
//						c.add(Calendar.YEAR, diff);
//						kdtTenancyContractEntry.getCell(e.getRowIndex()+2, 3).setValue(c.getTime());
						
						fillYear(e.getRowIndex()+3,sCalendar.get(Calendar.YEAR));
					}
				}
				if(curRowIndex%14 == 2 && e.getColIndex() == 3 ){//开始日期
					if(e.getValue() == null){
						return;
					}
					if(kdtTenancyContractEntry.getCell(e.getRowIndex()+1, 3).getValue() != null && kdtTenancyContractEntry.getCell(e.getRowIndex()-1, 3) != null){
						
						Date start  = (Date) e.getValue();
						Calendar sd = Calendar.getInstance();
						sd.setTime(start);
						
						Date endDate = (Date) kdtTenancyContractEntry.getCell(e.getRowIndex()+1, 3).getValue();
						Calendar c =Calendar.getInstance();
						c.setTime(endDate);
						
//						kdtTenancyContractEntry.getCell(e.getRowIndex()-1, 3).setValue(c.get(Calendar.YEAR)-sd.get(Calendar.YEAR));
						fillYear(e.getRowIndex()+2,sd.get(Calendar.YEAR));
					}
					
					
					
				}
				if(curRowIndex%14 == 3 && e.getColIndex() == 3 ){//结束日期
					if(e.getValue() == null){
						return;
					}
					
					if(kdtTenancyContractEntry.getCell(e.getRowIndex()-1, 3).getValue() != null && kdtTenancyContractEntry.getCell(e.getRowIndex()-2, 3) != null){
						
						Date endDate  = (Date) e.getValue();
						Calendar ed = Calendar.getInstance();
						ed.setTime(endDate);
						
						Date startDate = (Date) kdtTenancyContractEntry.getCell(e.getRowIndex()-1, 3).getValue();
						Calendar c =Calendar.getInstance();
						c.setTime(startDate);
						
//						kdtTenancyContractEntry.getCell(e.getRowIndex()-2, 3).setValue(ed.get(Calendar.YEAR)-c.get(Calendar.YEAR));
						fillYear(e.getRowIndex()+1,c.get(Calendar.YEAR));
					}
				}
				
			}

			public void editStopping(KDTEditEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void editValueChanged(KDTEditEvent arg0) {
				// TODO Auto-generated method stub
				
			}});
	
		
	}
	
	
	private void fillYear(int startRowIndex,int startYear){
	
		for(int i=0;i<10;i++){
			kdtTenancyContractEntry.getCell(startRowIndex+i, 2).setValue(startYear+i);
		}
		
	}
	
	
	private Calendar getCalendar(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}
	
	private ObjectValueRender getYearRender(){
		IDataFormat yearFormat = new IDataFormat(){

			public String format(Object obj) {
				if(obj != null){
					return obj.toString()+"年";
				}
				return null;
			}
			
		};
		
		ObjectValueRender objRender = new ObjectValueRender();
		objRender.setFormat(yearFormat);
		return objRender;
	}
	
	private ObjectValueRender getDateRender(){
		IDataFormat dateFormat = new IDataFormat(){

			public String format(Object obj) {
				// TODO Auto-generated method stub
				if(obj instanceof Date){
					return FDCDateHelper.formatDate2((Date)obj);
				}
				return null;
			}};
			
			ObjectValueRender objRender = new ObjectValueRender();
			objRender.setFormat(dateFormat);
		return 	objRender;
	}
	
	
	 private void addContract(int current,ReturnTenancyContractInfo contract){
		 KDTable table = this.kdtTenancyContractEntry;
	     int existsRowCount = current*14;
	     
	     logger.error(existsRowCount+":"+existsRowCount/14);
	     table.addRows(14);
	     table.getCell(existsRowCount+0,0).setValue(ChinaNumberFormat.getChinaNumberValue(existsRowCount/14+1));
	     table.getCell(existsRowCount+0,1).setValue("合同信息");
	     table.getCell(existsRowCount+0,2).setValue("签约商户");
	     table.getCell(existsRowCount+0,3).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
	     table.getCell(existsRowCount+1,2).setValue("租赁年限(年)");
	     table.getCell(existsRowCount+1,3).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
	     table.getCell(existsRowCount+2,2).setValue("租赁合同开始日期");
	     table.getCell(existsRowCount+2,3).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
	    
	     
	     KDDatePicker datePicker = new KDDatePicker();
	     datePicker.setDatePattern("yyyy-MM-dd");
	     KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(datePicker);
	   
	     
	     table.getCell(existsRowCount+2,3).setEditor(DateTimePickerEditor.getInstance(false));
	     table.getCell(existsRowCount+3,2).setValue("租赁合同结束日期");
	     table.getCell(existsRowCount+3,3).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
	     table.getCell(existsRowCount+3,3).setEditor(dateEditor);
	     table.getCell(existsRowCount+2,3).setRenderer(getDateRender());
	     
	     //设置合同信息
	     table.getCell(existsRowCount+0,3).setValue(contract.getSignCustomer());
	     table.getCell(existsRowCount+1,3).setValue(contract.getSignYears());
	     table.getCell(existsRowCount+2,3).setValue(contract.getSignStartDate());
	     table.getCell(existsRowCount+3,3).setValue(contract.getSignEndDate());
	     table.getCell(existsRowCount+3,3).setRenderer(getDateRender());
	     
	     table.getCell(existsRowCount+4,1).setValue("租金信息");
	     
	     ReturnTenancyRentEntryCollection rents = contract.getRentEntry();
	     Calendar c = Calendar.getInstance();
	     c.setTime(contract.getSignStartDate());
	     int startYear = c.get(Calendar.YEAR);
	     if(!rents.isEmpty()){
	    	 for(int j = 0;j < 10; j++){
		    	 table.getCell(existsRowCount+4+j,2).setValue(startYear+j); 
		    	 table.getCell(existsRowCount+4+j,2).setRenderer(getYearRender());
		    	 table.getCell(existsRowCount+4+j,3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		         table.getCell(existsRowCount+4+j,3).getStyleAttributes().setNumberFormat("###.00");
		    	 if(rents.get(j).getAmtOfRent() != null){
		    		 table.getCell(existsRowCount+4+j,3).setValue(rents.get(j).getAmtOfRent());		    		
		    	 }
		     }
	     }
	    
	     
	     table.getMergeManager().mergeBlock(existsRowCount,0, existsRowCount+13, 0);
	     table.getMergeManager().mergeBlock(existsRowCount, 1, existsRowCount+3, 1);
	     table.getMergeManager().mergeBlock(existsRowCount+4, 1, existsRowCount+13, 1);
	     
	     int beginNumber = existsRowCount;
	     setNode(table,beginNumber,4,"合同信息");
	     setNode(table,existsRowCount+4,10,"租金信息");
	 }
	
	
	
	private void setNode(KDTable table, int i, final int count, String value) {
		CellTreeNode node=new CellTreeNode();
		node.addClickListener(new NodeClickListener(){			
			public void doClick(CellTreeNode source, ICell cell, int type) {
				if(source.isCollapse()){
					for(int k=cell.getRowIndex()+1;k<cell.getRowIndex()+count;k++){
						ReturnTenancyBillEditUI.this.kdtTenancyContractEntry.getRow(k).getStyleAttributes().setHided(true);
					}
				}else{
					for(int k=cell.getRowIndex()+1;k<cell.getRowIndex()+count;k++){
					    ReturnTenancyBillEditUI.this.kdtTenancyContractEntry.getRow(k).getStyleAttributes().setHided(false);
					}
				}				
			}			
		});		
		node.setHasChildren(true);
		node.setTreeLevel(0);
		node.setValue(value);
		table.getCell(i, 1).setValue(node);
	}
    

	/**
	 * output btnRemoveContract_actionPerformed method
	 */
	protected void btnRemoveContract_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.btnRemoveContract_actionPerformed(e);
		KDTable table = this.kdtTenancyContractEntry;
		if ((table.getSelectManager().size() == 0)
                || isTableColumnSelected(table))
        {
            MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_NoneEntry"));
            return;
        }
		
		int top = table.getSelectManager().get().getBeginRow();
        int bottom = table.getSelectManager().get().getEndRow();

        int start = top/14;
        int end = bottom/14+1;
        
        for(int i=end*14-1;i>=start*14;i--)
        {
            table.removeRow(i);
        }

		
	}
	
	@Override
	public void loadFields() {
		// TODO Auto-generated method stub
		super.loadFields();
		//加载租赁合同及租金信息
		 kdtTenancyContractEntry.checkParsed();
		ReturnTenancyContractCollection cols = editData.getTenancyContractEntry();
		int size = cols.size();
		kdtTenancyContractEntry.removeRows();
		for(int i=0;i<size;i++){
			addContract(i,cols.get(i));
		}
	}
	
	@Override
	public SelectorItemCollection getSelectors() {
		// TODO Auto-generated method stub
		SelectorItemCollection sic = super.getSelectors();
		sic.add("tenancyContractEntry.*");
		sic.add("tenancyContractEntry.rentEntry.*");
		return sic;
	}
	
	@Override
	protected void lockUIForViewStatus() {
		// TODO Auto-generated method stub
		super.lockUIForViewStatus();
		this.btnAddContract.setEnabled(false);
		this.btnRemoveContract.setEnabled(false);
	}
	
	@Override
	protected void unLockUI() {
		// TODO Auto-generated method stub
		super.unLockUIAndAction();
		this.btnAddContract.setEnabled(true);
		this.btnRemoveContract.setEnabled(true);
		this.kdtTenancyContractEntry.setEnabled(true);
	}

}