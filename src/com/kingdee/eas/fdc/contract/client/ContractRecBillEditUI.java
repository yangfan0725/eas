/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.framework.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillReceiveFactory;
import com.kingdee.eas.fdc.contract.ContractBillReceiveInfo;
import com.kingdee.eas.fdc.contract.ContractRecBillCollection;
import com.kingdee.eas.fdc.contract.ContractRecBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractRecBillFactory;
import com.kingdee.eas.fdc.contract.ContractRecBillInfo;
import com.kingdee.eas.fdc.contract.MarketProjectCollection;
import com.kingdee.eas.fdc.contract.MarketProjectFactory;
import com.kingdee.eas.fdc.contract.MarketProjectInfo;
import com.kingdee.eas.fdc.contract.MarketProjectSourceEnum;
import com.kingdee.eas.fdc.contract.ZHMarketProjectEntryInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastCollection;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ContractRecBillEditUI extends AbstractContractRecBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractRecBillEditUI.class);
    public ContractRecBillEditUI() throws Exception
    {
        super();
    }
	@Override
	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return ContractRecBillFactory.getRemoteInstance();
	}
	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtNumber;
	}
	public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add("CU.*");
    	sic.add("state");
    	sic.add("source");
    	sic.add("sourceFunction");
    	return sic;
    }
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		this.storeFields();
		this.verifyInputForSubmint();
		super.actionSubmit_actionPerformed(e);
		if (editData.getState() == FDCBillStateEnum.AUDITTING) {
			btnSave.setEnabled(false);
			btnSubmit.setEnabled(false);
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
		}
		this.setOprtState("VIEW");
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
	protected IObjectValue createNewData() {
		ContractRecBillInfo info=new ContractRecBillInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setBizDate(now);
		info.setState(FDCBillStateEnum.SAVED);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setOrgUnit(SysContext.getSysContext().getCurrentCtrlUnit().castToFullOrgUnitInfo());
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("curProject.*");
		sic.add("partB.*");
		sic.add("rateEntry.*");
		sic.add("rateEntry.moneyDefine.*");
		try {
			ContractBillReceiveInfo cb = ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveInfo(new ObjectUuidPK(this.getUIContext().get("contractBillId").toString()),sic);
			info.setContractBillReceive(cb);
			info.setCustomer(cb.getPartB());
			
			info.setCurrency(CurrencyFactory.getRemoteInstance().getCurrencyCollection("select * from where name='人民币'").get(0));
			
			for(int i=0;i<cb.getRateEntry().size();i++){
				ContractRecBillEntryInfo entry=new ContractRecBillEntryInfo();
				entry.setMoneyDefine(cb.getRateEntry().get(i).getMoneyDefine());
				MoneySubjectContrastCollection col=MoneySubjectContrastFactory.getRemoteInstance().getMoneySubjectContrastCollection("select accountView.* from where fullOrgUnit.id='"+info.getOrgUnit().getId()+"' and moneyDefine.id='"+cb.getRateEntry().get(i).getMoneyDefine().getId()+"'");
				if(col.size()>0) entry.setOppAccount(col.get(0).getAccountView());
				info.getEntry().add(entry);
			}
			if(cb.getRateEntry().size()==0)info.getEntry().add(new ContractRecBillEntryInfo());
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		info.setRate(new BigDecimal(1));
		
		return info;
	}
	
	@Override
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.kdtEntry_editStopped(e);
		int colIndex=e.getColIndex();
		int rowIndex=e.getRowIndex();
		IColumn moneyDefine=this.kdtEntry.getColumn("moneyDefine");
		if(colIndex==moneyDefine.getColumnIndex()){
			MoneyDefineInfo md=(MoneyDefineInfo) this.kdtEntry.getRow(rowIndex).getCell("moneyDefine").getValue();
			if(md!=null){
				MoneySubjectContrastCollection col=MoneySubjectContrastFactory.getRemoteInstance().getMoneySubjectContrastCollection("select accountView.* from where fullOrgUnit.id='"+this.editData.getOrgUnit().getId()+"' and moneyDefine.id='"+md.getId()+"'");
				if(col.size()>0) {
					this.kdtEntry.getRow(rowIndex).getCell("oppAccount").setValue(col.get(0).getAccountView());
				}
			}
		}
	}
	@Override
	public void storeFields() {
		super.storeFields();
		BigDecimal tol = FDCHelper.ZERO;
		for(int i=0; i<this.kdtEntry.getRowCount(); i++){
			IRow tmpRow = this.kdtEntry.getRow(i);
			BigDecimal t = (BigDecimal) tmpRow.getCell("amount").getValue();
			if(t != null){
				tol = tol.add(t);
			}
		}
		this.txtAmount.setValue(tol);
		this.txtOriAmount.setValue(tol);
	}
	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
	}
	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtCustomer);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtAmount);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtAccountBank);
		
		ContractRecBillCollection col=ContractRecBillFactory.getRemoteInstance().getContractRecBillCollection("select amount from where contractBillReceive.id='"+this.editData.getContractBillReceive().getId()+"' and state!='"+FDCBillStateEnum.SAVED_VALUE+"' and id!='"+this.editData.getId()+"'");
		BigDecimal amount=FDCHelper.ZERO;
		for(int i=0;i<col.size();i++){
			amount=FDCHelper.add(amount,col.get(i).getAmount());
		}
		ContractBillReceiveInfo bill=ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveInfo(new ObjectUuidPK(this.editData.getContractBillReceive().getId()));
		
		
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"收款明细不能为空！");
			SysUtil.abort();
		}
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row = this.kdtEntry.getRow(i);
			if(row.getCell("moneyDefine").getValue()==null){
				FDCMsgBox.showWarning(this,"收款款项不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("moneyDefine"));
				SysUtil.abort();
			}
			if(row.getCell("amount").getValue()==null){
				FDCMsgBox.showWarning(this,"收款金额不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("amount"));
				SysUtil.abort();
			}
			if(((BigDecimal)row.getCell("amount").getValue()).compareTo(FDCHelper.ZERO)==0){
				FDCMsgBox.showWarning(this,"收款金额不能为0！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("amount"));
				SysUtil.abort();
			}
//			if(row.getCell("oppAccount").getValue()==null){
//				FDCMsgBox.showWarning(this,"对方科目不能为空！");
//				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("oppAccount"));
//				SysUtil.abort();
//			}
		}
//		if(FDCHelper.add(amount,this.txtAmount.getBigDecimalValue()).compareTo(bill.getAmount())>0){
//			FDCMsgBox.showWarning(this,"收款金额总和大于合同金额！");
//			SysUtil.abort();
//		}
	}
	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		this.actionRemove.setVisible(false);
    	this.actionAddLine.setVisible(false);
    	this.actionInsertLine.setVisible(false);
    	this.actionRemoveLine.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	this.actionNext.setVisible(false);
    	this.actionPre.setVisible(false);
    	this.actionTraceDown.setVisible(false);
//    	this.actionTraceUp.setVisible(false);
    	this.actionFirst.setVisible(false);
    	this.actionLast.setVisible(false);
    	
    	this.actionAudit.setEnabled(true);
    	this.actionUnAudit.setEnabled(true);
    	
    	this.actionAudit.setVisible(true);
    	this.actionUnAudit.setVisible(true);
    	
    	this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
//		GeneralKDPromptSelectorAdaptor selectorLisenterSupplier = new GeneralKDPromptSelectorAdaptor(this.prmtCustomer, "com.kingdee.eas.basedata.master.cssp.client.F7CustomerTreeDetailListUI", this, CSSPGroupInfo.getBosType(), "com.kingdee.eas.basedata.master.cssp.app.F7CustomerQuery", "browseGroup.id", "com.kingdee.eas.basedata.master.cssp.app.F7CustomerQuery", "com.kingdee.eas.basedata.master.cssp.app.F7CustomerQueryWithDefaultStandard");
//
//
//
//
//		String cuId = editData.getCU().getId().toString();
//		 /*1300*/        if(cuId != null)
//		                 {/*1301*/            selectorLisenterSupplier.setCUId(cuId);
//
//		 /*1303*/            CtrlUnitInfo ctrl = new CtrlUnitInfo();
//		 /*1304*/            ctrl.setId(BOSUuid.read(cuId));
//		 /*1305*/            prmtCustomer.setCurrentCtrlUnit(ctrl);
//		                 }
//		 /*1307*/        prmtCustomer.setSelector(selectorLisenterSupplier);
//		 /*1308*/        prmtCustomer.setHasCUDefaultFilter(false);
//		 /*1309*/        prmtCustomer.getQueryAgent().setDefaultFilterInfo(null);

		 EntityViewInfo view=new EntityViewInfo();
		 FilterInfo filter = new FilterInfo();
		 /*1328*/        filter.getFilterItems().add(new FilterItemInfo("usedStatus", String.valueOf(UsedStatusEnum.APPROVED.getValue()), CompareType.EQUALS));
		 view.setFilter(filter);
		 prmtCustomer.setEntityViewInfo(view);
		 prmtCustomer.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7CustomerQueryWithDefaultStandard");
		 
		 KDBizPromptBox gatheringSubject = new KDBizPromptBox();
			CompanyOrgUnitInfo curCompany = SysContext.getSysContext().getCurrentFIUnit();
			
			view = this.getAccountEvi(curCompany);
			AccountPromptBox opseelect = new AccountPromptBox(this, curCompany,view.getFilter(), false, true);
			gatheringSubject.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");
			gatheringSubject.setEditFormat("$number$");
			gatheringSubject.setCommitFormat("$number$");
			gatheringSubject.setDisplayFormat("$name$");
			gatheringSubject.setEntityViewInfo(view);
			ICellEditor f7Editor = new KDTDefaultCellEditor(gatheringSubject);
			this.kdtEntry.getColumn("oppAccount").setEditor(f7Editor);
			
			this.prmtRevAccount.setEntityViewInfo(view);
			this.prmtRevAccount.setSelector(opseelect);
			
	        EntityViewInfo entityViewInfo = new EntityViewInfo();
	        FilterInfo filterInfo = new FilterInfo();
	        entityViewInfo.setFilter(filterInfo);
	        filterInfo.getFilterItems().add(new FilterItemInfo("company.id",SysContext.getSysContext().getCurrentFIUnit().getId()));
	        this.prmtAccountBank.setEntityViewInfo(entityViewInfo);
	        
	        this.prmtRevAccount.setRequired(true);
	        
	        KDBizPromptBox f7Box = new KDBizPromptBox();
			f7Box.setDisplayFormat("$name$");
			f7Box.setCommitFormat("$number$");
			f7Box.setEditFormat("$number$");
			f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");

			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			// 这里应该和认购单上的过滤一致
			filter.getFilterItems().add(
					new FilterItemInfo("sysType",
							MoneySysTypeEnum.CONTRACTREC_VALUE,
							CompareType.EQUALS));
			f7Box.setEntityViewInfo(view);
			KDTDefaultCellEditor moneyDefineEditor=new KDTDefaultCellEditor(f7Box);
			this.kdtEntry.getColumn("moneyDefine").setEditor(moneyDefineEditor);
			
			KDFormattedTextField amount = new KDFormattedTextField();
			amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
			amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
			amount.setNegatived(true);
			amount.setPrecision(2);
			KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
			this.kdtEntry.getColumn("amount").setEditor(amountEditor);
			this.kdtEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			this.kdtEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			this.kdtEntry.getColumn("amount").setRequired(true);
			
			KDWorkButton btnAddRowinfo = new KDWorkButton();
	    	KDWorkButton btnInsertRowinfo = new KDWorkButton();
			KDWorkButton btnDeleteRowinfo = new KDWorkButton();
			this.actionALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
			btnAddRowinfo = (KDWorkButton)contEntry.add(this.actionALine);
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
	}
	
	public void loadFields() {
		this.kdtEntry.checkParsed();
		super.loadFields();
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionALine.setEnabled(false);
			this.actionRLine.setEnabled(false);
			this.actionILine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
			
		}
	}
	public void actionILine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = null;
		if (this.kdtEntry.getSelectManager().size() > 0) {
			int top = kdtEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(kdtEntry)) {
				row = kdtEntry.addRow();
				ContractRecBillEntryInfo entry = new ContractRecBillEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				row.setUserObject(entry);
			} else {
				row = kdtEntry.addRow(top);
				ContractRecBillEntryInfo entry = new ContractRecBillEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				row.setUserObject(entry);
			}
		} else {
			row = kdtEntry.addRow();
			ContractRecBillEntryInfo entry = new ContractRecBillEntryInfo();
			entry.setId(BOSUuid.create(entry.getBOSType()));
			row.setUserObject(entry);
		}
	}

	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtEntry.addRow();
		ContractRecBillEntryInfo entry = new ContractRecBillEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		row.setUserObject(entry);
	}
	public void actionRLine_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("请先选择一行数据");
			abort();
		}
		kdtEntry.removeRow(activeRowIndex);
	}
	private EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo)
	{
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// -----------------转6.0后修改,科目不按CU隔离,根据财务组织进行隔离
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
		if (companyInfo.getAccountTable() != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}
	protected void prmtAccountBank_dataChanged(DataChangeEvent e) throws Exception {
    	if(this.prmtAccountBank.getValue()==null) return;
    	AccountBankInfo revAccBankInfo = (AccountBankInfo)this.prmtAccountBank.getValue();
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("bank.id");
		sels.add("bank.name");
		sels.add("bank.number");
		sels.add("account.*");
		revAccBankInfo = (AccountBankInfo) AccountBankFactory.getRemoteInstance().getValue(new ObjectUuidPK(revAccBankInfo.getId()), sels);
    	this.prmtBank.setValue(revAccBankInfo.getBank());
    	this.prmtRevAccount.setValue(revAccBankInfo.getAccount());
	}
	 protected void prmtBank_dataChanged(DataChangeEvent e) throws Exception {
	    	if(CommerceHelper.isF7DataChanged(e))
	    	{
	    		initrevAccountBankFilter();
	    	}
		}
	 
	 private void initrevAccountBankFilter() throws BOSException, EASBizException {
	    	EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			CompanyOrgUnitInfo company = SysContext.getSysContext().getCurrentFIUnit(); 
	    	if(this.prmtBank.getValue()!=null)
			{
				BankInfo revBank = (BankInfo)this.prmtBank.getValue();
				filter.getFilterItems().add(new FilterItemInfo("bank.id", revBank.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("company.id", company.getId().toString()));
				viewInfo.setFilter(filter);
				prmtAccountBank.setEntityViewInfo(viewInfo);
			}else
			{
				filter.getFilterItems().add(new FilterItemInfo("company.id", company.getId().toString()));
				viewInfo.setFilter(filter);
				prmtAccountBank.setEntityViewInfo(viewInfo);
			}
	    }
}