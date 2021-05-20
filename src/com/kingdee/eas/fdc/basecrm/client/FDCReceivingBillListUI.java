/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCStringHelper;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.propertymgmt.AdvanceReceiveCollection;
import com.kingdee.eas.fdc.propertymgmt.AdvanceReceiveInfo;
import com.kingdee.eas.fdc.propertymgmt.client.PPMNewReceiveBillEditUI;
import com.kingdee.eas.fdc.propertymgmt.client.PPMNewReceiveBillListUI;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade;
import com.kingdee.eas.fdc.sellhouse.InvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiptInvoiceFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiptStatusEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.InvoiceEditUI;
import com.kingdee.eas.fdc.sellhouse.client.MakeOutReceiptUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEReceivingBillEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEReceivingBillListUI;
import com.kingdee.eas.fdc.tenancy.client.DepositDealBillEditUI;
import com.kingdee.eas.fdc.tenancy.client.InvoiceBillEditUI;
import com.kingdee.eas.fdc.tenancy.client.OtherBillEditUI;
import com.kingdee.eas.fdc.tenancy.client.TENReceivingBillListUI;
import com.kingdee.eas.fi.ar.IVerificationBillentry;
import com.kingdee.eas.fi.ar.OtherBillBizException;
import com.kingdee.eas.fi.ar.VerificationBillentryCollection;
import com.kingdee.eas.fi.ar.VerificationBillentryFactory;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FDCReceivingBillListUI extends AbstractFDCReceivingBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCReceivingBillListUI.class);
    
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    MarketDisplaySetting setting=new MarketDisplaySetting();
    protected SellProjectInfo curSellProNode = null;
    /**
     * output class constructor
     */
    public FDCReceivingBillListUI() throws Exception
    {
        super();
    }
    
	private void tblMain_afterDataFill(KDTDataRequestEvent e) {
		int sr = e.getFirstRow();
		if(e.getFirstRow() > 0){
			sr = sr - 1;
		}
		
		this.tblMain.getGroupManager().group(sr, e.getLastRow());
	}
    
    public void onLoad() throws Exception {
    	//add by warship at 2010/09/07 �޸���������
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){
			public void afterDataFill(KDTDataRequestEvent e) {
				tblMain_afterDataFill(e);
			}
		});
    	
		super.onLoad();
		initTree();
		initControl();
		//����ʵģʽ������ᵼ��������ʾ��ȫ
		//this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		this.treeMain.setSelectionRow(0);	
	}
    
    private void initControl()
	{
    	this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionDelVoucher.setVisible(true);
		this.actionDelVoucher.setEnabled(true);
		this.actionAuditResult.setVisible(false);
		this.menuWorkFlow.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionAudit.setEnabled(true);
		this.actionReceive.setVisible(true);
		this.actionReceive.setEnabled(true);
		this.actionVoucher.setVisible(true);
		this.actionVoucher.setEnabled(true);
		this.actionAdjust.setVisible(true);
		this.actionAdjust.setEnabled(true);
		this.actionBatchReceiving.setVisible(true);
		this.actionBatchReceiving.setEnabled(true);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionCanceReceive.setVisible(true);
		this.actionCanceReceive.setEnabled(true);
		
		this.actionWorkFlowG.setVisible(true);
		this.actionAuditResult.setVisible(true);
		
		
		this.actionReceipt.setEnabled(true);
		this.actionRetakeReceipt.setEnabled(true);		
		this.actionCreateInvoice.setEnabled(true);
		this.actionClearInvoice.setEnabled(true);

		
		if(!SHEHelper.getCurrentSaleOrg().isIsBizUnit()){
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false); 
			this.actionAddNew.setEnabled(false);
		}
	}
    
    protected void initTree() throws Exception
	{
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTree(this.actionOnLoad, this.getSystemType()));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
    	if(this.getEditUIName().equals(InvoiceBillEditUI.class.getName())||
    			this.getEditUIName().equals(OtherBillEditUI.class.getName())||
    				this.getEditUIName().equals(DepositDealBillEditUI.class.getName())){
    		return super.getQueryExecutor(queryPK, viewInfo);	
    	}
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			
			FilterInfo roomFilter = new FilterInfo();
			if(node==null) {		
				Map spIdMap = FDCTreeHelper.getAllObjectIdMap((DefaultKingdeeTreeNode)treeMain.getModel().getRoot(), "SellProject");
				if(spIdMap.keySet().size()>0)
					roomFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", FDCTreeHelper.getStringFromSet(spIdMap.keySet()) ,CompareType.INNER));
				else
					roomFilter.getFilterItems().add(new FilterItemInfo("id", "nullnull"));
			}else if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
				roomFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId()));
			}else if(node.getUserObject() instanceof SubareaInfo){
				SubareaInfo subAreaInfo = (SubareaInfo)node.getUserObject();
				roomFilter.getFilterItems().add(new FilterItemInfo("building.subarea.id", subAreaInfo.getId()));
			}else if(node.getUserObject() instanceof BuildingInfo){
				BuildingInfo buildInfo = (BuildingInfo)node.getUserObject();
				roomFilter.getFilterItems().add(new FilterItemInfo("building.id", buildInfo.getId()));
			}else if(node.getUserObject() instanceof BuildingUnitInfo){
				BuildingUnitInfo buildUnitInfo = (BuildingUnitInfo)node.getUserObject();
				roomFilter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnitInfo.getId()));
			}else{
				Map spIdMap = FDCTreeHelper.getAllObjectIdMap((DefaultKingdeeTreeNode)treeMain.getModel().getRoot(), "SellProject");
				if(spIdMap.keySet().size()>0)
					roomFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", FDCTreeHelper.getStringFromSet(spIdMap.keySet()),CompareType.INNER));
				else
					roomFilter.getFilterItems().add(new FilterItemInfo("id", "nullnull"));
			}
			if(addAdjustRevBillTypeFilter()){
				roomFilter.getFilterItems().add(new FilterItemInfo("revBillType", RevBillTypeEnum.ADJUST_VALUE));
			}
			
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)		{
				viewInfo.getFilter().mergeFilter(roomFilter, "and");
			} else		{
				viewInfo.setFilter(roomFilter);
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		//TODO ��������ϵͳ�Ĺ�������
		return super.getQueryExecutor(queryPK, viewInfo);
	}
    protected boolean addAdjustRevBillTypeFilter() {
		return false;
	}
    
    public void refreshListForOrder() throws Exception
	{
		super.refreshListForOrder();
		
		this.refresh(null);
	}
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

		if (saleOrg.isIsBizUnit()) {
			if(node==null || node.getUserObject() instanceof OrgStructureInfo)
				this.actionAddNew.setEnabled(false);
			else
				this.actionAddNew.setEnabled(true);
		} else {
			this.actionAddNew.setEnabled(false);
		}		
		
		
		if(node.getUserObject() instanceof SellProjectInfo) {
			curSellProNode = (SellProjectInfo)node.getUserObject();
		}else if(node.getUserObject() instanceof SubareaInfo)  {
			SubareaInfo subAreaInfo = (SubareaInfo)node.getUserObject();
			curSellProNode = subAreaInfo.getSellProject();
		}else if(node.getUserObject() instanceof BuildingInfo){
			BuildingInfo buildInfo = (BuildingInfo)node.getUserObject();
			curSellProNode = buildInfo.getSellProject();
		}else if(node.getUserObject() instanceof BuildingUnitInfo) {
			DefaultKingdeeTreeNode parNode = (DefaultKingdeeTreeNode)node.getParent();
			BuildingInfo buildInfo = (BuildingInfo)parNode.getUserObject();
			curSellProNode = buildInfo.getSellProject();
		}else{
			curSellProNode = null;
		}
		
//		this.execQuery();
		this.refresh(null);
	}
    
    protected void refresh(ActionEvent e) throws Exception
	{
		int actRowNum = this.tblMain.getSelectManager().getActiveRowIndex();
		int actColNum = this.tblMain.getSelectManager().getActiveColumnIndex();
		setColGroups();
		this.tblMain.removeRows();
//		this.tblMain.getGroupManager().group();
		this.tblMain.scrollToVisible(actRowNum, actColNum);
		//Delete by zhiyuan_tang 2010/10/14     
		//����������ѡ�иղŵ��С�ԭ�� ��ѡ��һ���ٵ����ͷ����������ٵ���鿴�Ȱ�ť�ͻ���ʾ����༭�����ID����ȷ
		//���Ĭ���ǵ��ͷ����֮�󣬲���ѡ��ղŵ��У�������ֻѡ���˱�ͷ���ٵ�������ť�ͻ���ʾ����ѡ���С�
		//�������ѡ���еĻ�������֮�����ѡ�������У�������ʾ����ѡ���е���ʾ����������н���ȡ����Ȼ��ȡ��ͷ��ʱ�͹��ˡ�
//		this.tblMain.getSelectManager().select(actRowNum, actColNum);
	}
    
    /**
     * ��Ҫ�ϲ����У����������д
     * */
    protected void setColGroups(){
		setColGroup("id");
		setColGroup("sellProject.name");
		setColGroup("currency.name");
		setColGroup("number");
		setColGroup("amount");
		setColGroup("originalAmount");
		setColGroup("receiptNumber");		
		setColGroup("invoiceNumber");
		setColGroup("bizDate");
		setColGroup("billStatus");
		
		setColGroup("revBillType");
		setColGroup("revBizType");
		setColGroup("customer.name");
		
		//�����տ�����ж������,���ܺϲ�
//		setColGroup("roomLongNumber");
//		setColGroup("room.name");
//		setColGroup("room.number");
		
		setColGroup("fiVouchered");
		setColGroup("state");
		
		setColGroup("createTime");
		setColGroup("description");
    }
    
    protected final void setColGroup(String colKey) {
    	IColumn col = this.tblMain.getColumn(colKey);
    	if(col != null){
    		col.setGroup(true);
    	}
    }

	/**
	 * �ڲ���ѡ�е��б���֮ǰ���������Ƿ���ڡ����������쳣֪ͨ�������� Copy from ListUI
	 */
	protected void checkObjectExists() throws Exception
	{
		if (getSelectedKeyValue() == null)
		{
			return;
		}
		if (!getBizInterface().exists(
				new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue()))))
		{
			refreshList();
			throw new EASBizException(EASBizException.CHECKEXIST);
		}
	}
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		checkObjectExists();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		KDTSelectBlock block2 = (KDTSelectBlock) blocks.get(0);
		int a = block2.getTop();
		Iterator iter = blocks.iterator();
		while (iter.hasNext())
		{
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++)
			{
				IRow row = tblMain.getRow(rowIndex);
				
				/**
				 * bos ƽ̨��궨λ����
				 * by renliang ��ʱ����취
				 */
				if(row == null && rowIndex == -1){
					row = tblMain.getRow(1);
				}
				
				int billStatus = ((BizEnumValueInfo) row.getCell(getBillStatusColKey()).getValue()).getInt();
				if (BillStatusEnum.SUBMIT_VALUE != billStatus)
				{
					MsgBox.showInfo("ֻ���ύ״̬���տ��������!");
					this.abort();
				}
			}
		}
		List selectedIds = super.getSelectedIdValues();
		
		((IFDCReceivingBill) getBizInterface()).audit(selectedIds);
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		refresh(e);
		KDTableUtil.setSelectedRow(tblMain,a);
		tblMain.scrollToVisible(a,0);
    }
    
    /**
     * ��ҵ�Ǳߵ��в�һ��,��չ��ȥ�޸�
     * */
    protected String getBillStatusColKey(){
    	return "billStatus";
    }
    
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		checkObjectExists();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		KDTSelectBlock block2 = (KDTSelectBlock) blocks.get(0);
		int a = block2.getTop();
		Iterator iter = blocks.iterator();
		while (iter.hasNext())
		{
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++)
			{
				IRow row = tblMain.getRow(rowIndex);
				
				/**
				 * bos ƽ̨��궨λ����
				 * by renliang ��ʱ����취
				 */
				if(row ==null && rowIndex == -1){
					row = tblMain.getRow(1);
				}
				
				int billStatus = ((BizEnumValueInfo) row.getCell(getBillStatusColKey())
						.getValue()).getInt();
				if (BillStatusEnum.AUDITED_VALUE != billStatus)
				{
					MsgBox.showInfo("ֻ������״̬���տ���ܷ�����!");
					this.abort();
				}
			}
		}
		List selectedIds = super.getSelectedIdValues();
		
		((IFDCReceivingBill) getBizInterface()).unAudit(selectedIds);
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		refresh(e);
		KDTableUtil.setSelectedRow(tblMain,a);
		tblMain.scrollToVisible(a,0);
    }
    
    public void actionReceive_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		this.checkObjectExists();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		Iterator iter = blocks.iterator();
		KDTSelectBlock block2 = (KDTSelectBlock) blocks.get(0);
		int a = block2.getTop();
		ArrayList idList = new ArrayList();
		while (iter.hasNext())
		{
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++)
			{
				IRow row = tblMain.getRow(rowIndex);
				int billStatus = ((BizEnumValueInfo) row.getCell(getBillStatusColKey())
						.getValue()).getInt();
				if (BillStatusEnum.AUDITED_VALUE == billStatus)
				{
					String id = row.getCell("id").getValue().toString();
					if (isCanCanceRec(id))
					{
						idList.add(id);
					}
				}
			}
		}
		if (idList.size() == 0)
		{
			MsgBox.showInfo("ֻ��������״̬���տ�����տ�!");
			//return;
			SysUtil.abort();
		}
		((IFDCReceivingBill) getBizInterface()).receive(idList);
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		refresh(e);
		KDTableUtil.setSelectedRow(tblMain,a);
		tblMain.scrollToVisible(a,0);
    }
    
    public void actionCanceReceive_actionPerformed(ActionEvent e)
    		throws Exception {
    	checkSelected();
		this.checkObjectExists();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		KDTSelectBlock block2 = (KDTSelectBlock) blocks.get(0);
		int a = block2.getTop();
		Iterator iter = blocks.iterator();
		ArrayList idList = new ArrayList();
		while (iter.hasNext())
		{
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++)
			{
				IRow row = tblMain.getRow(rowIndex);
				int billStatus = ((BizEnumValueInfo) row.getCell(getBillStatusColKey())
						.getValue()).getInt();
				if (BillStatusEnum.RECED_VALUE == billStatus)
				{
					String id = row.getCell("id").getValue().toString();
					if (isCanCanceRec(id))
					{
						idList.add(id);
					}
				}
			}
		}
		if (idList.size() == 0)
		{
			MsgBox.showInfo("ֻ�����տ�״̬���տ����ȡ���տ�!");
			return;
		}
		((IFDCReceivingBill) getBizInterface()).canceReceive(idList);
		Component component = (Component) e.getSource();
		FDCClientHelper.showSuccessInfo(this, component);
		refresh(e);
		KDTableUtil.setSelectedRow(tblMain,a);
		tblMain.scrollToVisible(a,0);
    }
    
	private boolean isCanCanceRec(String billId) throws BOSException,
	OtherBillBizException
	{
		IVerificationBillentry iVerifyEntryBill = VerificationBillentryFactory
				.getRemoteInstance();
		VerificationBillentryCollection verifyEntryInfoCol = null;
		verifyEntryInfoCol = iVerifyEntryBill
				.getVerificationBillentryCollection("where billID = '" + billId
						+ "'");
		if (verifyEntryInfoCol.size() > 0)
		{
			throw new OtherBillBizException(OtherBillBizException.BILLVERIFIED);
		}
		return true;
	 }
    
    protected ICoreBase getBizInterface() throws Exception {
    	return FDCReceivingBillFactory.getRemoteInstance();
    }
    
    protected MoneySysTypeEnum getSystemType(){
    	return null;
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }
    
    protected String[] getLocateNames()
    {
        return super.getLocateNames();
    }
    
    protected String getEditUIModal() {
    	return UIFactoryName.NEWTAB;
    }
    
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
    	if(this.getEditUIName().equals(InvoiceBillEditUI.class.getName())||
    			this.getEditUIName().equals(OtherBillEditUI.class.getName())){
    		super.actionVoucher_actionPerformed(e);	
    		return;
    	}
		checkSelected();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		KDTSelectBlock block2 = (KDTSelectBlock) blocks.get(0);
		int a = block2.getTop();
		Iterator iter = blocks.iterator();
		while (iter.hasNext())
		{
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++)
			{
				IRow row = tblMain.getRow(rowIndex);
				String id = (String) row.getCell("id").getValue();
				
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("billStatus");
				sels.add("id");
				sels.add("fiVouchered");

				FDCReceivingBillInfo rev = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillInfo(new ObjectUuidPK(id), sels);
								
				if (!RevBillStatusEnum.RECED.equals(rev.getBillStatus()))
				{
					MsgBox.showInfo(this, "ֻ�����տ�״̬���տ����������ƾ֤!");
					this.abort();
				}
				
				if(rev.isFiVouchered()){
					MsgBox.showInfo(this, "�Ѿ�����ƾ֤�Ĳ���������!");
					this.abort();
				}
			}
		}
		super.actionVoucher_actionPerformed(e);
		refresh(e);
		KDTableUtil.setSelectedRow(tblMain,a);
		tblMain.scrollToVisible(a,0);
    }
    
    
    protected CoreBillBaseCollection getNewBillList() throws Exception {
		checkSelected();
		ArrayList blocks = tblMain.getSelectManager().getBlocks();
		Iterator iter = blocks.iterator();
		Set selIds = new HashSet();
		while (iter.hasNext()) {
			KDTSelectBlock block = (KDTSelectBlock) iter.next();
			int top = block.getTop();
			int bottom = block.getBottom();

			for (int rowIndex = top; rowIndex <= bottom; rowIndex++) {
				IRow row = tblMain.getRow(rowIndex);
				String id = (String) row.getCell("id").getValue();
				if (id != null)
					selIds.add(id);
			}
		}

		if (selIds == null) {
			logger.error("û��ѡ���У�");
			this.abort();
		}

		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("id", selIds, CompareType.INCLUDE));

		EntityViewInfo entityViewInfo = new EntityViewInfo();
		entityViewInfo.getSelector().add(new SelectorItemInfo("id"));
		entityViewInfo.setFilter(filterInfo);

		CoreBillBaseCollection sourceBillCollection = ((ICoreBillBase) getCoreBaseInterface()).getCoreBillBaseCollection(entityViewInfo);
		return sourceBillCollection;
	}
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		this.checkObjectExists();
		String id = this.getSelectedKeyValue();
		if (id == null)
		{
			logger.warn("�߼�����,��ϸ���");
			return;
		}
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		FDCReceivingBillInfo receivingBill = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillInfo(new ObjectUuidPK(BOSUuid.read(id)), sels);
		if (!RevBillStatusEnum.SAVE.equals(receivingBill.getBillStatus())&& !RevBillStatusEnum.SUBMIT.equals(receivingBill.getBillStatus()))
		{
			MsgBox.showInfo(this, "ֻ�����ύ�򱣴�״̬���տ�ſ����޸ģ�");
			return;
		}
		if (RevBillTypeEnum.transfer.equals(receivingBill.getRevBillType()))		{
			MsgBox.showInfo(this, "ת������޸ģ�");
			return;
		}	
		if(isVerifyAdjustBillEditable()){
			if (RevBillTypeEnum.adjust.equals(receivingBill.getRevBillType()))		{
				MsgBox.showInfo(this, "�����������޸ģ�");
				return;
			}
		}
		
		
    	super.actionEdit_actionPerformed(e);
    }
    /**
     *�������Ƿ�У����������Ա༭
     *@author pu_zhang  2010-11-10
     */
    protected boolean isVerifyAdjustBillEditable() {
    	return true;
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		this.checkObjectExists();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows != null && selectRows.length > 1) {
			MsgBox.showInfo("��ѡ�������ݽ���ɾ��������");
			this.abort();
		}
		String id = this.getSelectedKeyValue();
		if (id == null) {
			logger.warn("�߼�����,��ϸ���");
			this.abort();
		}
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("billStatus");
		FDCReceivingBillInfo rev = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillInfo(new ObjectUuidPK(BOSUuid.read(id)),sels);
		if(!RevBillStatusEnum.SUBMIT.equals(rev.getBillStatus())){
			MsgBox.showInfo("�ύ״̬�ĵ��ݲ���ɾ����");
			this.abort();
		}
		
		if(confirmRemove()){
			FDCReceivingBillFactory.getRemoteInstance().delete(BOSUuid.read(id), getHandleClazzName());
			MsgBox.showInfo(this, "ɾ���ɹ���");
			
			this.refresh(null);
		}
		

	}

    protected String getHandleClazzName(){
		return null;
	}
	
	public boolean isNeedShowBOTPRule() {
		return true;
	}
	
	public SelectorItemCollection getBOTPSelectors()
	{
		
		SelectorItemCollection sels = super.getBOTPSelectors();
		
		sels.add("entries.settlementType.*");
		sels.add("entries.revAccount.*");
		sels.add("entries.oppAccount.*");
		sels.add("entries.moneyDefine.*");
		sels.add("entries.revAccountBank.*");
		sels.add("entries.room.*");
		sels.add("entries.room.building.*");
		sels.add("entries.sourceEntries.*");

		sels.add("purchaseObj.*");
		sels.add("sincerityObj.*");
		sels.add("obligateObj.*");
		sels.add("tenancyObj.*");
		sels.add("fdcCustomers.*");
		sels.add("customer.*");
		sels.add("room.*");
		sels.add("sellProject.*");
		sels.add("currency.*");
		sels.add("company.*");
		sels.add("*");
		
		return sels;
		
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		
		sels.add("entries.*");
		sels.add("entries.revAccount.*");
		sels.add("entries.oppAccount.*");
		return sels;
	}
	
	
	public void actionAdjust_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		checkObjectExists();
		
		String idStr = this.getSelectedKeyValue();
		if(idStr==null) return;
		
		if(confirmDialog("ȷ��Ҫ���ɵ�������")){
			Map map = FDCReceivingBillFactory.getRemoteInstance().adjust(BOSUuid.read(idStr));
			MsgBox.showInfo("�����ɹ���");
			FDCReceivingBillInfo adjustInfo = (FDCReceivingBillInfo)map.get("adjustInfo"); 
			showAdjustReceivingUI(adjustInfo);

			this.refresh(null);
		}
		
	}
	/**
	 * @param gCol
	 * @param mCol
	 * @throws UIException
	 * @throws Exception
	 */
	private void showAdjustReceivingUI(FDCReceivingBillInfo adjustInfo) throws UIException, Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("adjustInfo", adjustInfo);
		uiContext.put("ID", adjustInfo.getId());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(SHEReceivingBillEditUI.class.getName(), uiContext, null, "EDIT");
		uiWindow.show();
		this.refresh(null);
	}
	
	public void actionReceipt_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		
		String idStr = this.getSelectedKeyValue();
		FDCReceivingBillInfo revInfo = FDCReceivingBillFactory.getRemoteInstance()
				.getFDCReceivingBillInfo("select billStatus,receiptState,billType,revBillType," +
						"amount,room.name,room.number,customer.name,customer.number,room.lastPurchase,sellProject.id,sellProject.number,sellProject.name where id='"+idStr+"'");

		if (!revInfo.getBillStatus().equals(RevBillStatusEnum.AUDITED)	&& !revInfo.getBillStatus().equals(RevBillStatusEnum.RECED)) {
			MsgBox.showWarning(this, "�����������տ!");
			return;
		}
		if (revInfo.getReceiptState()!=null
				&& revInfo.getReceiptState().equals(ReceiptStatusEnum.HasInvoice)) {
			MsgBox.showWarning(this, "�ѻ���Ʊ�����ٿ����վ�!");
			return;
		}
		if (!revInfo.getRevBillType().equals(RevBillTypeEnum.transfer)
				&& !revInfo.getRevBillType().equals(RevBillTypeEnum.gathering)) {
			MsgBox.showWarning(this, "ֻ���տ��ת�����͵��տ�ſ��Կ����վ�!");
			return;
		}

		Map ctx = new UIContext(this);
		ctx.put("recID", idStr);
		ctx.put(UIContext.OWNER, this);
		ctx.put("room", revInfo.getRoom());
		ctx.put("customer", revInfo.getCustomer());
		ctx.put("SystemType", this.getSystemType());
		ctx.put("thisactionReceipt", Boolean.valueOf(true));
		ctx.put("chequeType", ChequeTypeEnum.receipt);
		ctx.put("sellProjectId", revInfo.getSellProject().getId().toString());

		IUIWindow uiWindow = null;
		// ����ģʽ
//		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
//				MakeOutReceiptUI.class.getName(), ctx, null, OprtState.ADDNEW);
		
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(InvoiceEditUI.class.getName(), ctx, null,	"ADDNEW");

		// ��ʼչ��UI
		uiWindow.show();
		refresh(null);
	}
	
	public void actionRetakeReceipt_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		
		String idStr = this.getSelectedKeyValue();
		
		
		IFDCReceivingBill fdcReceivingBill = FDCReceivingBillFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("id", idStr, CompareType.EQUALS));
		evi.setFilter(filterInfo);

		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("receiptState"));
		coll.add(new SelectorItemInfo("receipt.id"));
		coll.add(new SelectorItemInfo("receipt.number"));
		coll.add(new SelectorItemInfo("receiptNumber"));
		coll.add(new SelectorItemInfo("room.id"));
		coll.add(new SelectorItemInfo("customer.id"));
		evi.setSelector(coll);

		FDCReceivingBillCollection collection = fdcReceivingBill.getFDCReceivingBillCollection(evi);
		
		if(collection!=null && collection.size()>0){
			IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory.getRemoteInstance();
			for (int j = 0; j < collection.size(); j++) {
				FDCReceivingBillInfo revInfo = collection.get(j);
				
				int ret = MsgBox.showConfirm3(this, "�Ƿ����ͬ�վݵ������տ?");
				if (ret == MsgBox.NO)	{	
					//��Ҫ�����ֹ���д�վݺŵ���� 2010.10.08
					if(revInfo.getReceiptState() != null && revInfo.getReceiptState().equals(ReceiptStatusEnum.HasInvoice)){
						MsgBox.showWarning(this, "�ѿ���Ʊ������ֱ�ӻ����վ�!");
						SysUtil.abort();
					}
					if (revInfo.getReceiptState() != null
							&& revInfo.getReceiptState().equals(ReceiptStatusEnum.HasMake)) {
						if (( revInfo.getReceipt()==null || FDCStringHelper.isEmpty(revInfo.getReceipt().getNumber()) ) && FDCStringHelper.isEmpty(revInfo.getReceiptNumber())) {
							MsgBox.showWarning(this, "���տ�����վ�!");
						} else {
							Map paramMap = new HashMap();
							paramMap = getRetakeReceivingBillIDs(revInfo);
							paramMap.put("receivingBillInfo", revInfo);
							if(revInfo.getReceipt()!=null && revInfo.getReceipt().getNumber()!=null){
								facade.retackReceipt(idStr, null, revInfo.getReceipt().getNumber(), null,paramMap);
							}else if(!FDCStringHelper.isEmpty(revInfo.getReceiptNumber())){
								facade.retackReceipt(idStr, null, revInfo.getReceiptNumber(), null,paramMap);
							}
							MsgBox.showWarning(this, "�����վݳɹ�!");
							refresh(null);
						}
					} else {
						MsgBox.showWarning(this, "���տ�����վ�!");
					}
				}else if(ret == MsgBox.YES){	
					Map paramMap = new HashMap();
					//��Ҫ�����ֹ���д�վݺŵ����   2010.10.08
					paramMap = getRetakeReceivingBillIDs(revInfo);
					paramMap.put("receivingBillInfo", revInfo);
					if (((Set)paramMap.get("retakeReceivingBillidSet"))!=null && ((Set)paramMap.get("retakeReceivingBillidSet")).size()>0) {
						facade.retackReceiptBatch(paramMap, null, null, null);
						MsgBox.showWarning(this, "�����վݳɹ�!");
						refresh(null);
					} else {
						MsgBox.showWarning(this, "���տ�����վ�!");
					}
				}else	{
					return;
				}
			}
		}
	}
	
	
    /**
     * ������ ��ȡͬһ���տ���棬���վݵ��տ��״̬�ǿ�,�ѿ���Ʊ,���վ�(����ͳһ��Ʊ���߲�����ͳһ��Ʊ)
     * @return map
     * 			�վݵ��տIDSMap
     * @throws Exception
     * @author pu_zhang  2010-11-10
     */
	private Map getRetakeReceivingBillIDs(FDCReceivingBillInfo revInfo) throws Exception {
		Map reMap = new HashMap();
		Set idSet = new HashSet();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		if(revInfo.getRoom()!=null){
			filterInfo.getFilterItems().add(new FilterItemInfo("room", revInfo.getRoom().getId(), CompareType.EQUALS));	
		}
		filterInfo.getFilterItems().add(new FilterItemInfo("customer", revInfo.getCustomer().getId(), CompareType.EQUALS));
		evi.setFilter(filterInfo);
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("receiptState"));
		coll.add(new SelectorItemInfo("receipt.id"));
		coll.add(new SelectorItemInfo("receipt.number"));
		coll.add(new SelectorItemInfo("receiptNumber"));
		evi.setSelector(coll);
		FDCReceivingBillCollection collection =  FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillCollection(evi);
		if(collection!=null && collection.size()>0){
			for (int j = 0; j < collection.size(); j++) {
				FDCReceivingBillInfo revBillInfo = collection.get(j);
				//���˳��ĵ���   ״̬�ǿ�,�ѿ���Ʊ,���վ�(����ͳһ��Ʊ���߲�����ͳһ��Ʊ)
				if(revBillInfo.getReceiptState() != null && !revBillInfo.getReceiptState().equals(ReceiptStatusEnum.HasInvoice)
					&& ( !( revBillInfo.getReceipt()==null || FDCStringHelper.isEmpty(revBillInfo.getReceipt().getNumber()) )
					|| !FDCStringHelper.isEmpty(revBillInfo.getReceiptNumber()))) {
					//������ͬһ���վݺŲŻ���
					if(!( revBillInfo.getReceipt()==null || FDCStringHelper.isEmpty(revBillInfo.getReceipt().getNumber())) &&  revInfo.getReceipt()!=null   && revBillInfo.getReceipt().getId().equals(revInfo.getReceipt().getId())){
						idSet.add(revBillInfo.getId());
					}else if(!FDCStringHelper.isEmpty(revBillInfo.getReceiptNumber()) &&  revBillInfo.getReceiptNumber().equals(revInfo.getReceiptNumber())){
						idSet.add(revBillInfo.getId());
					}
					
				}
			}
		}
		reMap.put("retakeReceivingBillidSet", idSet);
		return reMap;	
	}

	public void actionCreateInvoice_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		UIContext uiContext = new UIContext(this);
		if (id != null)
		{
			FDCReceivingBillInfo revInfo = FDCReceivingBillFactory.getRemoteInstance()
					.getFDCReceivingBillInfo("select amount,room.name,room.number,customer.name,customer.number,billStatus ,sellProject.id,sellProject.number,sellProject.name where id='"+id+"'");
			if (revInfo.getAmount().compareTo(FDCHelper.ZERO) < 0)			{
				MsgBox.showInfo("���ֲ��ܿ���Ʊ!");
				return;
			}
			if (!revInfo.getBillStatus().equals(RevBillStatusEnum.AUDITED)	&& !revInfo.getBillStatus().equals(RevBillStatusEnum.RECED)) {
				MsgBox.showWarning(this, "�����������տ!");
				return;
			}
			uiContext.put("room", revInfo.getRoom());
			uiContext.put("customer", revInfo.getCustomer());
			uiContext.put("SystemType", this.getSystemType());
			uiContext.put("thisactionCreateInvoice", Boolean.valueOf(true));
			uiContext.put("chequeType", ChequeTypeEnum.invoice);
			uiContext.put("sellProjectId", revInfo.getSellProject().getId().toString());
		}
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(InvoiceEditUI.class.getName(), uiContext, null,	"ADDNEW");
		uiWindow.show();
		this.refresh(null);	
	}
	
	public void actionClearInvoice_actionPerformed(ActionEvent e) throws Exception {

		super.actionClearInvoice_actionPerformed(e);
		String id = this.getSelectedKeyValue();
		if (id != null)
		{
			FDCReceivingBillInfo revInfo = FDCReceivingBillFactory.getRemoteInstance()
			.getFDCReceivingBillInfo("select invoice,amount,room.name,room.number where id='"+id+"'");
			
			InvoiceInfo invoice = revInfo.getInvoice();
			if (invoice == null)			{
				MsgBox.showInfo("ѡ���տû�п���Ʊ!");
				SysUtil.abort();
			}
			// ���ӽ�״̬��Ϊ����Ʊ֮ǰ״̬�������²�����¼ update by jiyu_guan
			IReceiptInvoiceFacade facade = ReceiptInvoiceFacadeFactory.getRemoteInstance();
			int ret = MsgBox.showConfirm3(this, "�Ƿ����ͬ��Ʊ�������տ?");
			if (ret == MsgBox.YES)	{
				facade.clearInvoice(invoice.getId().toString(), true);
				
				/**
				 *CASE WHEN FReceiptNumber is Null
				 *sql�﷨���� 
				 *update by renliang 2010-9-11
				 */
				String sql =null;
				if(setting.getMarkInvoice()==32){
					sql = "update T_BDC_FDCReceivingBill set FInvoiceID=null,FReceiptState = (CASE WHEN FReceiptID is Null THEN '0' ELSE '1' END) " +
					"where FInvoiceID = '"	+ invoice.getId().toString() + "'";
				}else{
				    sql = "update T_BDC_FDCReceivingBill set FInvoiceID=null,FReceiptState = (CASE WHEN FReceiptNumber is Null THEN '0' ELSE '1' END) " +
						"where FInvoiceID = '"	+ invoice.getId().toString() + "'";
				}
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql(sql);
				builder.executeUpdate();
				
				InvoiceFactory.getRemoteInstance().delete(new ObjectUuidPK(invoice.getId()));
				FDCMsgBox.showWarning(this, "��Ʊ����ɹ���");
			} else if (ret == MsgBox.NO)	{
				facade.clearInvoice(revInfo.getId().toString(), false);
				
				/**
				 *CASE WHEN FReceiptNumber is Null
				 *sql�﷨���� 
				 *update by renliang 2010-9-11
				 */
				String sql=null;
				if(setting.getMarkInvoice()==32){//����Ʊ��ͳһ����
					sql = "update T_BDC_FDCReceivingBill set FInvoiceID=null,FReceiptState = (CASE WHEN FReceiptID is Null THEN '0' ELSE '1' END) " +
					 "where FID = '"	+ revInfo.getId() + "'";
				}else{
					sql = "update T_BDC_FDCReceivingBill set FInvoiceID=null,FReceiptState = (CASE WHEN FReceiptNumber is Null THEN '0' ELSE '1' END) " +
					"where FID = '"	+ revInfo.getId() + "'";
				}
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql(sql);
				builder.executeUpdate();
				FDCReceivingBillCollection coll = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillCollection("select id  where invoice.id = '"+invoice.getId().toString()+"'" );
				if(coll.size()==0){
					InvoiceFactory.getRemoteInstance().delete(new ObjectUuidPK(invoice.getId()));
				}
				FDCMsgBox.showWarning(this, "��Ʊ����ɹ���");
			} else	{
				return;
			}
			this.refresh(null);
		}
			
	}
	
	
	/**
	 * by jinzhicheng
	 * at renliang 2010-7-26
	 */
	public IUIObject getInstance(Map uiContext) {
		if(uiContext.get("BOTPFilter") != null  ||  uiContext.get("isFromWorkflow") != null){
			try {
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("revBizType");
				FDCReceivingBillCollection revs = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillCollection((EntityViewInfo) uiContext.get("BOTPFilter"));
				if(revs != null  &&  !revs.isEmpty()){
					RevBizTypeEnum revBizType = revs.get(0).getRevBizType();
					if(RevBizTypeEnum.manage.equals(revBizType)){
						PPMNewReceiveBillListUI rev = new PPMNewReceiveBillListUI();
						return rev;
					}else if(RevBizTypeEnum.obligate.equals(revBizType) || RevBizTypeEnum.tenancy.equals(revBizType)){
						TENReceivingBillListUI rev = new TENReceivingBillListUI();
						return rev;
					}else{
						//��¥��
						return new SHEReceivingBillListUI();
					}
				}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.getInstance(uiContext);
	}

	
	


	
}