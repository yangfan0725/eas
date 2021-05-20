/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.AttachResourceInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentBillFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentBillInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysCollection;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceRentEntrysInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AttachResourceRentBillListUI extends AbstractAttachResourceRentBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AttachResourceRentBillListUI.class);
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    public AttachResourceRentBillListUI() throws Exception
    {
        super();
    }
    public void storeFields()
    {
        super.storeFields();
    }
    
    protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
    
    public void onLoad() throws Exception {
    	FDCClientHelper.addSqlMenu(this, this.menuEdit);
    	super.onLoad();
    	initTree();
    	this.treeMain.setSelectionRow(0);
		this.menuItemCreateTo.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.menuItemCopyTo.setVisible(false);
		this.menuItemTraceUp.setVisible(false);
		this.menuItemTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.btnAudit.setEnabled(true);
		this.btnUnAudit.setVisible(false);
		this.btnExecute.setEnabled(true);
		this.btnBlankOut.setEnabled(true);
		this.actionAuditResult.setVisible(false);
    }
    
    /**
	 * �洢��Ŀ���൱��request������
	 * */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showInfo("��ѡ�����������Ŀ��");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
			.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
		super.prepareUIContext(uiContext, e);
	}
	
    protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	protected String getEditUIName() {
		return AttachResourceRentBillEditUI.class.getName();
	}
	
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
	throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		
		if (node.getUserObject() instanceof SellProjectInfo) {
			if (this.saleOrg.isIsBizUnit()) {
				this.actionAddNew.setEnabled(true);
			}
		} else {
			this.actionAddNew.setEnabled(false);
		}
		this.execQuery();
	}
	
	/**
	 *��ѯ��������
	 * */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
			.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			
			FilterInfo filter = new FilterInfo();
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems()
				.add(
						new FilterItemInfo("sellProject.id", pro.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}
			
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}
	
	/**
	 * ������Դ���ⵥ����¼�
	 * */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state")
		.getValue();
		if (FDCBillStateEnum.SAVED_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("���ⵥû���ύ!");
			return;
		}
		if (FDCBillStateEnum.AUDITTED_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("���ⵥ�Ѿ����!");
			return;
		}
		if (FDCBillStateEnum.INVALID_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("���ⵥ�Ѿ�����!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		AttachResourceRentBillFactory.getRemoteInstance().audit(BOSUuid.read(id));
		this.refresh(null);
	}
	
	/**
	 * ���ⵥ�����¼�
	 * */
	public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		if(((Boolean)row.getCell("isExecuted").getValue()).booleanValue())
		{
			MsgBox.showInfo("���ⵥ��ִ�У���������!");
			return;
		}
		BizEnumValueInfo tenancyState = (BizEnumValueInfo) row.getCell(
		"state").getValue();
		if(FDCBillStateEnum.INVALID_VALUE.equals(tenancyState.getValue()))
		{
			MsgBox.showInfo("���ⵥ�Ѿ�����!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		if (MsgBox.showConfirm2New(this, "���Ϻ󲻿ɻָ�����ȷ���Ƿ�����?") == MsgBox.YES) {
			//����ʱ��Ҫ��״̬д�뵥���У�����ֹͣ��ǰ�������еĹ�����
			AttachResourceRentBillInfo attachInfo = AttachResourceRentBillFactory.getRemoteInstance()
			.getAttachResourceRentBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
			attachInfo.setState(FDCBillStateEnum.INVALID);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("state");
			AttachResourceRentBillFactory.getRemoteInstance().updatePartial(attachInfo, sels);
			
			ProcessInstInfo instInfo = null;
			ProcessInstInfo[] procInsts = null;
			IEnactmentService service = EnactmentServiceFactory
			.createRemoteEnactService();
			procInsts = service.getProcessInstanceByHoldedObjectId(attachInfo.getId()
					.toString());
			for (int j = 0; j < procInsts.length; j++) {
				if ("open.running".equals(procInsts[j].getState())) {
					instInfo = procInsts[j];
					service.abortProcessInst(instInfo.getProcInstId());
				}
				
			}
			this.refresh(null);
		}
	}
	
	/**
	 * ���ⵥִ���¼�
	 * */
	public void actionExecute_actionPerformed(ActionEvent e) throws Exception {
		super.actionExecute_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		Boolean isExecuted = (Boolean) row.getCell("isExecuted").getValue();
		if (isExecuted.booleanValue()) {
			MsgBox.showInfo("���ⵥ�Ѿ�ִ��!");
			return;
		}
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName == null) {
			MsgBox.showInfo("���ⵥû�����!");
			return;
		}
		if (MsgBox.showConfirm2New(this, "�Ƿ�ִ��?") == MsgBox.YES) {
			String id = (String) row.getCell(this.getKeyFieldName()).getValue();
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("head.id", id));
//			view.setFilter(filter);
//			view.getSelector().add("*");
//			view.getSelector().add("sellProject.*");
//			view.getSelector().add("entrys.*");
//			view.getSelector().add("entrys.subarea.*");
//			view.getSelector().add("entrys.building.*");
//			AttachResourceRentEntrysCollection attEntrysColl = AttachResourceRentEntrysFactory.getRemoteInstance().getAttachResourceRentEntrysCollection(view);
//			for(int i=0;i<attEntrysColl.size();i++)
//			{
//				AttachResourceRentEntrysInfo attEntrysInfo = attEntrysColl.get(i);
//				AttachResourceInfo info = attEntrysInfo.getAttach();
//				info.getAttachState();
//			}
			if(AttachResourceRentBillFactory.getRemoteInstance().execute(id)) {
			MsgBox.showInfo("ִ�гɹ�!");
			this.refresh(null);
		} else {
			MsgBox.showInfo("���ⵥû�����!");
		}
		}
	}
	
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName != null) {
			MsgBox.showInfo("������Դ���ⵥ�Ѿ�����,�����޸�!");
			return;
		}
		super.actionEdit_actionPerformed(arg0);
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return AttachResourceRentBillFactory.getRemoteInstance();
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}

}