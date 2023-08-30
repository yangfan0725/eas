/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractRecBillCollection;
import com.kingdee.eas.fdc.contract.ContractRecBillFactory;
import com.kingdee.eas.fdc.contract.ContractRecBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.client.CasPaymentBillUI;
import com.kingdee.eas.fi.cas.client.CasReceivingBillUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractRecBillListUI extends AbstractContractRecBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractRecBillListUI.class);
    
    /**
     * output class constructor
     */
    public ContractRecBillListUI() throws Exception
    {
        super();
    }
    protected String[] getLocateNames() {
		return new String[] {"number", "contractName", "contractType.name", "signDate", "partB.name",};
	}
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// 检查是否选中合同
		checkSelected(getMainTable());

		super.actionAddNew_actionPerformed(e);
	}
    
	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){

			public void afterDataFill(KDTDataRequestEvent e) {
				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					IRow row = tblMain.getRow(i);
					BigDecimal amount = (BigDecimal) row.getCell("amount").getValue();
					if(amount==null){
						row.getCell("amount").setValue(FDCHelper.ZERO);
					}
				}
			}
		});
		super.onLoad();
		this.actionTraceDown.setVisible(true);
		this.actionTraceDown.setEnabled(true);
	}
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(this.tblSettlementList);
		int rowIndex = this.tblSettlementList.getSelectManager().getActiveRowIndex();
		IRow row = this.tblSettlementList.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		ReceivingBillCollection col=ReceivingBillFactory.getRemoteInstance().getReceivingBillCollection("select id from where sourceBillId='"+id+"'");
		if(col.size()>0){
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", col.get(0).getId().toString());
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(CasReceivingBillUI.class.getName(), uiContext,null,OprtState.VIEW);
	        uiWindow.show();
	        return;
		}
		FDCMsgBox.showInfo(this,"目标单据为空！");
	}
	@Override
	protected void audit(List ids) throws Exception {
		ContractRecBillFactory.getRemoteInstance().audit(ids);
	}
	@Override
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("*");
		selectors.add("creator.name");
		selectors.add("auditor.name");
		selectors.add("customer.name");
		selectors.add("revAccount.*");
		selectors.add("accountBank.*");
		selectors.add("settlementType.*");
		selectors.add("bank.*");
		selectors.add("oppAccount.*");
		return selectors;
	}
	@Override
	protected KDTable getBillListTable() {
		// TODO Auto-generated method stub
		return this.tblSettlementList;
	}
	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return ContractRecBillFactory.getRemoteInstance();
	}
	@Override
	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return ContractRecBillEditUI.class.getName();
	}
	@Override
	protected ICoreBillBase getRemoteInterface() throws BOSException {
		// TODO Auto-generated method stub
		return ContractRecBillFactory.getRemoteInstance();
	}
	@Override
	protected boolean isFootVisible() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void unAudit(List ids) throws Exception {
		ContractRecBillFactory.getRemoteInstance().unAudit(ids);
		
	}
	protected EntityViewInfo genBillQueryView(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
		KDTSelectBlock selectBlock = e.getSelectBlock();
    	int top = selectBlock.getTop();
    	if(getMainTable().getCell(top, getKeyFieldName())==null){
    		return null;
    	}
    	
    	String contractId = (String)getMainTable().getCell(top, getKeyFieldName()).getValue();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBillReceive.id", contractId));
    	view.setFilter(filter);
    	view.getSorter().add(new SorterItemInfo(getBillStatePropertyName()));
    	SelectorItemCollection selectors = genBillQuerySelector();
    	if(selectors != null && selectors.size() > 0) {
    		for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);
				
			}
    	}
		return view;
	}
	@Override
	protected boolean displayBillByContract(KDTSelectEvent e,
			EntityViewInfo view) throws BOSException {
		if(view==null){
			return false;
		}

		int pre = getPre(e);
		

		
		ContractRecBillCollection contractSettlementBillCollection = ContractRecBillFactory
				.getRemoteInstance().getContractRecBillCollection(view);
		for (Iterator iter = contractSettlementBillCollection.iterator(); iter
				.hasNext();) {
			ContractRecBillInfo element = (ContractRecBillInfo) iter
					.next();

			IRow row = getBillListTable().addRow();
			row.getCell("id").setValue(element.getId().toString());
			row.getCell("number").setValue(element.getNumber());
			row.getCell("state").setValue(element.getState().getAlias());
			row.getCell("bizDate").setValue(element.getBizDate());
			row.getCell("amount").setValue(element.getAmount());
			row.getCell("creator.name").setValue(element.getCreator().getName());
			row.getCell("createTime").setValue(element.getCreateTime());
			if(element.getAuditor()!=null)
				row.getCell("auditor.name").setValue(element.getAuditor().getName());
			row.getCell("auditTime").setValue(element.getAuditTime());
			row.getCell("description").setValue(element.getDescription());
			if(element.getCustomer()!=null)
				row.getCell("customer").setValue(element.getCustomer().getName());
			
			if(element.getRevAccount()!=null)
				row.getCell("revAccount").setValue(element.getRevAccount().getName());
			
			if(element.getAccountBank()!=null)
				row.getCell("accountBank").setValue(element.getAccountBank().getName());
			
			if(element.getSettlementType()!=null)
				row.getCell("settlementType").setValue(element.getSettlementType().getName());
			
			row.getCell("settlementNumber").setValue(element.getSettlementNumber());
			
			if(element.getBank()!=null)
				row.getCell("bank").setValue(element.getBank().getName());
			
		}
		return true;
	}
  
	protected FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("isReceive", Boolean.TRUE));
		return filter;
	}
	 protected String getEditUIModal()
		{
			return UIFactoryName.NEWTAB;
		}
}