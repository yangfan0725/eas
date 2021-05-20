/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.propertymgmt.client.PPMHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillCollection;
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillFactory;
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ���շ���ת��listUI
 * @author yinshujuan
 */
public class InsteadCollectOutBillListUI extends AbstractInsteadCollectOutBillListUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8847175502497364012L;
	private static final Logger logger = CoreUIObject.getLogger(InsteadCollectOutBillListUI.class);
    
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	private Set sellProjectIDs = new HashSet();
	/**
	 * output class constructor
	 */
	public InsteadCollectOutBillListUI() throws Exception
	{
		super();
	}


	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		checkStatusValues();
		
		super.actionRemove_actionPerformed(e);
	}
	
	public IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo){
		  IQueryExecutor ret = super.getQueryExecutor(queryPK, viewInfo);
		  try {
		   System.out.println(ret.getSQL());
		   ///logger.debug("ReqsBillListUI.sql: "+ret.getSQL());
		  } catch (BOSException e) {
		   logger.warn(e.getCause(), e);
		  }
		  return ret;
		 }

	
	protected void checkStatusValues()
    {
		
		boolean result = true;
		//���ѡ�е���
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		
		for (int i = 0; i < selectRows.length; i++) {
			IRow row = this.tblMain.getRow(selectRows[i]);
			if(row==null){
				continue;
			}
			
			if(row.getCell("state").getValue()==null){
				result = false;
			}
		}
		
		if(!result){
			FDCMsgBox.showWarning(this,"ѡ�����в�����ɾ�������ĵ���,���ܽ���ɾ����");
			SysUtil.abort();
		}
    }

	/**
	 * ���Ϲ���������Ӧ�շ�¼��id����edit
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e)
	{
		// TODO Auto-generated method stub
		super.prepareUIContext(uiContext, e);
		IRow row = getSelectRow();
		if (row != null && row.getCell("entry.id").getValue() != null)
		{
			String entryID = row.getCell("entry.id").getValue().toString();
			uiContext.put("entryID", entryID);
		}
		//�Ѵ������
		if(row != null) {
			uiContext.put("insteadedAmount", row.getCell("insteadedAmount").getValue());
		}
		//�˿���
		if(row != null ) {
			uiContext.put("returned", row.getCell("refundmentAmount").getValue());
		}
		//Ӧ�ս��
		if(row != null ) {
			uiContext.put("apAmount", row.getCell("apAmount").getValue());
		}
		if(row != null ) {
			uiContext.put("building", row.getCell("building").getValue());
		}
		if(row != null ) {
			uiContext.put("unit", row.getCell("unit").getValue());
		}
		if(row != null ) {
			uiContext.put("roomNumber", row.getCell("room.number").getValue());
		}
		if(row != null ) {
			uiContext.put("customer", row.getCell("head.customerNames").getValue());
		}
		

	}

	private IRow getSelectRow()
	{
		int rowNumber = tblMain.getSelectManager().getActiveRowIndex();
		IRow row = null;
		if (!(rowNumber < 0))
		{
			row = this.tblMain.getRow(rowNumber);
		}
		return row;
	}

	protected MoneySysTypeEnum getTreeSysType(){
		return MoneySysTypeEnum.SalehouseSys;
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}

	public void onLoad() throws Exception
	{
		// TODO Auto-generated method stub
		super.onLoad();
		this.menuItemCreateTo.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.menuItemCopyTo.setVisible(false);
		this.menuItemTraceUp.setVisible(false);
		this.menuItemTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);

		this.MenuItemAttachment.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.menuBiz.setVisible(false);
		init();
	}
	private void init() {
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		tblMain.getColumn("insteadedAmount").setMergeable(true);
		this.btnQuery.setVisible(false);
		
		Calendar   ca   =   Calendar.getInstance();   
		ca.setTime(new Date());   //  
		ca.set(Calendar.DAY_OF_MONTH,   1);   
		Date   firstDate   =   ca.getTime(); 
		ca.add(ca.MONTH, 1);
		ca.set(ca.DATE, 1);
		ca.add(ca.DATE, -1);
		Date lastDate = ca.getTime();
		this.kddateFrom.setValue(firstDate);
		this.kddateTo.setValue(lastDate);
	}
/*
	protected void initTree() throws Exception
	{
		super.initTree();
		DefaultKingdeeTreeNode oldSelected = null;
		Object objTree = this.treeMain.getLastSelectedPathComponent();
		if (objTree != null)
		{
			oldSelected = (DefaultKingdeeTreeNode) objTree;
		}
		this.treeMain.setModel(FDCTreeHelper.getUnitTree(actionOnLoad,
				MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		if (oldSelected != null)
			this.treeMain.setSelectionNode(oldSelected);
		else
			this.treeMain
					.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain
							.getModel().getRoot());

	}
*/
	
	protected void setEntityView(DefaultMutableTreeNode node)
	{
		EntityViewInfo view =  new EntityViewInfo();
		EntityViewInfo viewInfo =  new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FilterInfo filter2 = new FilterInfo();
		if(node.getUserObject() instanceof SellProjectInfo){
			SellProjectInfo sellProject = (SellProjectInfo)node.getUserObject();		
			filter2.getFilterItems().add(new FilterItemInfo("building.sellProject.id",sellProject.getId().toString(),CompareType.EQUALS));
		}else if(node.getUserObject() instanceof BuildingInfo){
			BuildingInfo building = (BuildingInfo)node.getUserObject();			
			filter2.getFilterItems().add(new FilterItemInfo("building.id",building.getId().toString(),CompareType.EQUALS));
		}else if(node.getUserObject() instanceof BuildingUnitInfo){
			BuildingUnitInfo unitInfo = (BuildingUnitInfo)node.getUserObject();			
			filter2.getFilterItems().add(new FilterItemInfo("buildUnit.id",unitInfo.getId().toString(),CompareType.EQUALS));
		}else if(node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo subarea = (SubareaInfo)node.getUserObject();			
			filter2.getFilterItems().add(new FilterItemInfo("building.subarea.id",subarea.getId().toString(),CompareType.EQUALS));
		}else {
			//	��ѡ��Ľڵ㲻����Ŀ��������¥������Ԫʱ��ȡ�ýڵ��µ���Ŀ�ڵ�
			if(sellProjectIDs != null && sellProjectIDs.size()>0) {				
				filter2.getFilterItems().add(new FilterItemInfo("building.sellProject.id",sellProjectIDs,CompareType.INCLUDE));
				
			}else {			
				filter2.getFilterItems().add(new FilterItemInfo("building.sellProject.id",null,CompareType.EQUALS));
			}
		}
		filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		viewInfo.setFilter(filter);
		view.setFilter(filter2);
		this.prmtroomNumber.setEntityViewInfo(view);
		this.prmtMoneyDefine.setEntityViewInfo(viewInfo);
	}
	
	/**
	 * 
	 *ѡ�����ڵ�
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception
	{
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
		sellProjectIDs.clear();
		getAllUnits(node);
		if (node == null) 
		{
			return;
		}
		setEntityView(node);
		tblMain.removeRows();
//		sellProjectIDs.clear();
	}

	/**
	  
	 */
    protected void getAllUnits(DefaultMutableTreeNode treeNode) 
    {
    	DefaultMutableTreeNode thisNode = (DefaultMutableTreeNode) treeNode;
		if(!treeNode.isLeaf() && !(treeNode.getUserObject() instanceof SellProjectInfo))
		{
			int childCount = treeNode.getChildCount();
			while (childCount > 0)
			{
				getAllUnits((DefaultMutableTreeNode) treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}else{
			if(thisNode.getUserObject() instanceof SellProjectInfo)
				{
				SellProjectInfo sellProject = (SellProjectInfo) thisNode.getUserObject();
				sellProjectIDs.add(sellProject.getId().toString());
				}
		}
	}
    /**
     * ��ѡ������ݷ����仯ʱ�����������δ��Ӧת���������ܲ鿴
     */
    private void selectChanged() {
    	int rowNumber = tblMain.getSelectManager().getActiveRowIndex();
		IRow row = null;
		if(rowNumber < 0)return;
		if (!(rowNumber < 0))
		{
			row = this.tblMain.getRow(rowNumber);
		}
		if(row.getCell("id").getValue() == null) {
			this.btnRemove.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnView.setEnabled(false);
			this.btnAudit.setEnabled(false);
			this.btnUnAudit.setEnabled(false);
		}else if(row.getCell("state").getValue().toString().trim().equals("���ύ")){
			this.btnAudit.setEnabled(true);
			this.btnUnAudit.setEnabled(false);
			this.btnEdit.setEnabled(true);
			this.btnView.setEnabled(true);
			this.btnRemove.setEnabled(true);
		}else if(row.getCell("state").getValue().toString().trim().equals("������")){
			this.btnAudit.setEnabled(false);
			this.btnUnAudit.setEnabled(true);
			this.btnEdit.setEnabled(false);
			this.btnView.setEnabled(true);
			this.btnRemove.setEnabled(false);
		}
			
    }
	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception
	{
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
	           return;
		super.tblMain_tableClicked(e);
		
		 
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception
	{
		super.tblMain_tableSelectChanged(e);
		selectChanged();
	}
	/**
	 * �ж��Ƿ�ɽ��д�������
	 * ���Ӧ�տ���ȫ���˻ؿͻ������������������� 2010-2-23
	 * @throws BOSException 
	 */
	private boolean isCanInstead(IRow row) throws BOSException {
		BigDecimal actCol = (BigDecimal) row.getCell("apAmount").getValue();
		BigDecimal returned = (BigDecimal)row.getCell("refundmentAmount").getValue();
		if(actCol!=null && returned!=null) {
			if(actCol.compareTo(returned)==0) {
				return false;
			}
		}
		String entryID = row.getCell("entry.id").getValue()==null?"":row.getCell("entry.id").getValue().toString();
		BigDecimal allInsteadAmount = new BigDecimal(getTrasferedCount(entryID));//�����Ѵ����Ľ�����δ��˵�
		if(allInsteadAmount.add(returned==null?new BigDecimal(0):returned).compareTo(CRMHelper.getBigDecimal(actCol))>=0) {
			return false;
		}
		/*if(entryID==null || "".equals(entryID)){
			return false;
		}*/
		return true;
	}
	 /**
     * ��ȡĳ���Ϲ�������Ӧ����¼������ת����
     * @return
     * @throws BOSException
     */
    private InsteadCollectOutBillCollection getAllInsteadBill(String entryID) throws BOSException{
    	InsteadCollectOutBillCollection collection = null;
//    	String entryID = this.getUIContext().get("entryID").toString().trim();
    	EntityViewInfo evi = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("purchaseElsePayListEntry.id",entryID,CompareType.EQUALS));
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("*"));
    	evi.setSelector(sic);
    	evi.setFilter(filter);
    	collection = InsteadCollectOutBillFactory.getRemoteInstance().getInsteadCollectOutBillCollection(evi);
    	return collection;
    }
    /**
     * ��������ת���ķ���
     * @throws BOSException 
     */
    private float getTrasferedCount(String entryID) throws BOSException{
    	float transferedCount = 0.0f;
    	InsteadCollectOutBillCollection bills = getAllInsteadBill(entryID);
    	InsteadCollectOutBillInfo bill = null;
    	float account = 0.0f;
    	for(int i = 0;i<bills.size();i++){
    		bill = bills.get(i);
    		account = account + bill.getMoneyInsteadCur().floatValue();
    	}
    	transferedCount = account;
    	return transferedCount;
    }


	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
	{
		
		checkNodeCorrent();
		if (getSelectRow() == null)
		{
			MsgBox.showWarning("��ѡ��Ҫת���ķ������ڵ��У�");
			SysUtil.abort();
		}
		if(!isCanInstead(getSelectRow())) {
			MsgBox.showWarning("�ÿ���ܽ��д�����");
			SysUtil.abort();
		}
			
		super.actionAddNew_actionPerformed(e);
	}


	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception
	{
		return com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillFactory
				.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData()
	{
		com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo objectValue = new com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo();

		return objectValue;
	}


	protected KDTree getTreeCtrl()
	{
		// TODO Auto-generated method stub
		return treeMain;
	}

	protected String getTreeType()
	{
		// TODO Auto-generated method stub
		return PPMHelper.TREE_TYPE_UNIT;
	}
	protected String getRoomQueryStringForExecutor(){
		return "purchaseElsePayListEntry.head.room.id";
	}
	
	protected EntityViewInfo getEntityViewInfoForPPMExecutor(EntityViewInfo viewInfo){
		try {
			viewInfo =  new EntityViewInfo();
//			viewInfo = (EntityViewInfo)this.mainQuery.clone();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.treeMain.getLastSelectedPathComponent();
			FilterInfo filter = new FilterInfo();
	
			if(node.getUserObject() instanceof SellProjectInfo){
				SellProjectInfo sellProject = (SellProjectInfo)node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProject.getId().toString(),CompareType.EQUALS));				
			}else if(node.getUserObject() instanceof BuildingInfo){
				BuildingInfo building = (BuildingInfo)node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("buildings.id",building.getId().toString(),CompareType.EQUALS));
			}else if(node.getUserObject() instanceof BuildingUnitInfo){
				BuildingUnitInfo unitInfo = (BuildingUnitInfo)node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("units.id",unitInfo.getId().toString(),CompareType.EQUALS));
			}else if(node.getUserObject() instanceof SubareaInfo) {
				SubareaInfo subarea = (SubareaInfo)node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("subarea.id",subarea.getId().toString(),CompareType.EQUALS));
			}else {
				//	��ѡ��Ľڵ㲻����Ŀ��������¥������Ԫʱ��ȡ�ýڵ��µ���Ŀ�ڵ�
				if(sellProjectIDs != null && sellProjectIDs.size()>0) {
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProjectIDs,CompareType.INCLUDE));					
				}else {
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id",null,CompareType.EQUALS));
				}
			}
			//TODO:ͨ����ѯ�������˲�ѯ
			if(isConditionQuery) {
				FDCCustomerInfo customer = (FDCCustomerInfo) this.prmtcustomer.getValue();
				if(customer!=null) {
					filter.getFilterItems().add(new FilterItemInfo("customer.id",customer.getId().toString(),CompareType.EQUALS));
				}
				RoomInfo room = (RoomInfo)this.prmtroomNumber.getValue();
				if(room!=null) {
					filter.getFilterItems().add(new FilterItemInfo("room.id",room.getId().toString(),CompareType.EQUALS));
				}
				MoneyDefineInfo moneyDefine = (MoneyDefineInfo)this.prmtMoneyDefine.getValue();
				if(moneyDefine != null) {
					filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id",moneyDefine.getId().toString(),CompareType.EQUALS));
				}
				Timestamp fromDate = this.kddateFrom.getTimestamp();
				Timestamp toDate = this.kddateTo.getTimestamp();
				DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
				Date newToDate = null;
				if(toDate!=null){
					Calendar   ca   =   Calendar.getInstance();   
					ca.setTime(toDate); 
					ca.add(ca.DAY_OF_MONTH, 1);
					newToDate = ca.getTime();
				}
				
				if(fromDate!=null) {
					filter.getFilterItems().add(new FilterItemInfo("InsteadFeeOut.bizDate",FORMAT_DAY.format(fromDate),CompareType.GREATER_EQUALS));
				}
				if(toDate!=null) {
					filter.getFilterItems().add(new FilterItemInfo("InsteadFeeOut.bizDate",FORMAT_DAY.format(newToDate),CompareType.LESS_EQUALS));
				}
			}
			
			
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) 
			{
				MsgBox.showError("���ִ���");
				logger.info("��ȡ��ѯ��ʧ�ܣ�");
				this.handleException(e);
			}
		SorterItemCollection sortColl = new SorterItemCollection ();
		sortColl.add(new SorterItemInfo("buildings.name"));
		sortColl.add(new SorterItemInfo("units.name"));
		sortColl.add(new SorterItemInfo("room.number"));
		sortColl.add(new SorterItemInfo("head.customerNames"));
		sortColl.add(new SorterItemInfo("moneyDefine.name"));
		sortColl.add(new SorterItemInfo("InsteadFeeOut.state"));
		sortColl.add(new SorterItemInfo("appAmount"));
		sortColl.add(new SorterItemInfo("actRevAmount"));
		sortColl.add(new SorterItemInfo("hasRefundmentAmount"));
		viewInfo.setSorter(sortColl);
		viewInfo.setIngorePreOrders(false);
		return viewInfo;
	}
	
	private void checkNodeCorrent(){
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.treeMain.getLastSelectedPathComponent();
		if(node.getUserObject() instanceof OrgStructureInfo){
			FDCMsgBox.showWarning(this,"��ѡ����Ŀ��");
			SysUtil.abort();
		}
	}

	/**
	 * ����ת��
	 */
	public void actionBatchInstead_actionPerformed(ActionEvent e)
			throws Exception
	{
		super.actionBatchInstead_actionPerformed(e);
		int []rows = KDTableUtil.getSelectedRows(tblMain);
		if(rows == null ||rows.length == 0) {
			MsgBox.showWarning("��ѡ��Ҫת���ķ��ã�");
			SysUtil.abort();
		}
		
		Set elseEntrys = new HashSet();
		IRow row = null;
		for(int i = 0;i<rows.length;i++) {
			row = tblMain.getRow(rows[i]);
			if(!isCanInstead(row)) {
//				MsgBox.showWarning("��"+(row.getRowIndex()+1)+"�����ڵĿ���ܴ�����");
//				SysUtil.abort();
				continue;
			}
			Object entryID = row.getCell("entry.id").getValue();
			if(entryID!=null) {
				elseEntrys.add(entryID.toString());
			}
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("entryIDs", elseEntrys);
		IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory(UIFactoryName.EDITWIN);
			IUIWindow curDialog = uiFactory.create(InsteadCollectOutBatchUI.class
					.getName(), uiContext);
			curDialog.show();
			tblMain.setRefresh(true);
	}

	/**
	 * �����Ѵ�������е��ں� 2010-2-23
	 */
	public String[] getMergeColumnKeys()
    {
        return new String[] {"sellProject.name","building","unit","room.number","head.customerNames","moneyDefine.name","state","apAmount","actPayAmount","refundmentAmount","id","sellProject.id","InsteadFeeOut.moneyInsteadCur","InsteadFeeOut.bizDate","units.id","entry.id","insteadedAmount"};
    }
	
	boolean isConditionQuery = false;
	/**
	 * ͨ����ѯ������ѯ
	 */
	public void actionBtnChoose_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionBtnChoose_actionPerformed(e);
		
		Timestamp fromDate = this.kddateFrom.getTimestamp();
		Timestamp toDate = this.kddateTo.getTimestamp();
		
		if(fromDate!=null && toDate!=null){
			if(fromDate.compareTo(toDate)==1){
				FDCMsgBox.showWarning(this,"������ʼ���ڱ���С�ڵ��ڽ�������!");
				SysUtil.abort();
			}
		}
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
		getAllUnits(node);
		isConditionQuery = true;
		tblMain.removeRows();
		//sellProjectIDs.clear();
		isConditionQuery = false;
	}

}