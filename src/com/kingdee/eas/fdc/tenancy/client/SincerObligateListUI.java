/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
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
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.FDCReceivingBillEditUI;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.BizStateEnum;
import com.kingdee.eas.fdc.tenancy.SincerObligateFactory;
import com.kingdee.eas.fdc.tenancy.SincerObligateInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SincerObligateListUI extends AbstractSincerObligateListUI
{
	private static final Logger logger = CoreUIObject.getLogger(SincerObligateListUI.class);
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	public SincerObligateListUI() throws Exception
	{
		super();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected String getEditUIName() {
		return SincerObligateEditUI.class.getName();
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		initTree();
		this.treeMain.setSelectionRow(0);
		this.actionAttachment.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);

		this.actionUnAudit.setVisible(false);
		this.actionAudit.setEnabled(true);
		this.actionBlankOut.setEnabled(false);
		this.actionExecute.setEnabled(true);
		this.actionCancelSincer.setEnabled(true);
		this.actionToTenancy.setEnabled(false);
		this.actionAuditResult.setVisible(false);
		
		this.actionReceiveBill.setEnabled(false);
		this.actionBlankOut.setVisible(false);
	}
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		checkSelected();
		if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
			if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex())
					.getCell("bizState").getValue().toString().equals(BizStateEnum.LEASE.toString())) {
				actionBlankOut.setEnabled(false);
				actionExecute.setEnabled(false);
				actionAudit.setEnabled(false);
				actionCancelSincer.setEnabled(false);
			}else{
				actionBlankOut.setEnabled(true);
				actionExecute.setEnabled(true);
				actionAudit.setEnabled(true);
				actionCancelSincer.setEnabled(true);
			}
			if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
				if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex())
						.getCell("bizState").getValue().toString().equals(BizStateEnum.SINCEROBLIGATED.toString())) {
					actionAudit.setEnabled(false);
				}else{
					actionAudit.setEnabled(true);
				}
			}
			if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex())
					.getCell("bizState").getValue().toString().equals(BizStateEnum.AUDITTED.toString())) {
				actionAudit.setEnabled(false);
				actionUnAudit.setVisible(true);
				actionUnAudit.setEnabled(true);
			}else{
				actionUnAudit.setVisible(false);
			}
			if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex())
					.getCell("bizState").getValue().toString().equals(BizStateEnum.SAVE.toString())||(this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex())
							.getCell("bizState").getValue().toString().equals(BizStateEnum.SUBMIT.toString()))) {
				actionRemove.setEnabled(true);
			}else{
				actionRemove.setEnabled(false);
			}
		}
		super.tblMain_tableSelectChanged(e);
	}
	
	/*
	 * ��鵱ǰѡ�м�¼�Ƿ�Ϊ��
	 */
    private IRow checkSelected(ActionEvent e) {
    	IRow row = null;
    	if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
    		// ����ָ��һ����¼
    		MsgBox.showWarning("����ָ��һ����¼!");
    	} else {
    		row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
    	}
    	return row;
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
	
	/*ȡ��Ԥ��*/
	public void actionCancelSincer_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelSincer_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo tenancyState = (BizEnumValueInfo) row.getCell("bizState").getValue();
		Object obj = row.getCell("isExecuted").getValue();
		if (BizStateEnum.SINCEROBLIGATED_VALUE.equals(tenancyState.getValue()) && ((Boolean)obj).booleanValue()==true) {
			String id = (String) row.getCell(this.getKeyFieldName()).getValue();
			 if (SincerObligateFactory.getRemoteInstance().cancelSincer(id)) {
				 MsgBox.showInfo("ȡ���ɹ�!");
				 this.refresh(null);
			 }
		}else
		{
			MsgBox.showInfo("����Ԥ����û��ִ��!");
			return;			
		}
	}
	
	/*ת����*/
	public void actionToTenancy_actionPerformed(ActionEvent e) throws Exception {
		super.actionToTenancy_actionPerformed(e);
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		IRow row = this.tblMain.getRow(index);
		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("bizState")
				.getValue();
		if (!state.getValue().equals(BizStateEnum.SINCEROBLIGATED_VALUE)) {
			MsgBox.showInfo("ֻ����Ԥ���ĵ��ݿ��Բ���ת����!");
			return;
		}
//		if (state.getValue()
//				.equals(BizStateEnum.TENANCY_VALUE)) {
//			MsgBox.showInfo("�Ѿ�ת����!");
//			return;
//		}
//		if (state.getValue()
//				.equals(BizStateEnum.LEASE_VALUE)) {
//			MsgBox.showInfo("���ⵥ�Ѿ�ת����󣬲����ٴν���ת�������!");
//			return;
//		}
		String id = this.getSelectedKeyValue();
		if (id == null) {
			return;
		}
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
		sels.add("sinRoomList.*");
		sels.add("sinRoomList.room.*");	
		sels.add("sinAttachList.*");
		sels.add("sinAttachList.attachResource.*");
		sels.add("sinCustomerList.*");
		sels.add("sinCustomerList.fdcCustomer.*");
		SincerObligateInfo sin = SincerObligateFactory
				.getRemoteInstance().getSincerObligateInfo(
						new ObjectUuidPK(BOSUuid.read(id)), sels);
		UIContext uiContext = new UIContext(this);
		uiContext.put("sincerObligate", sin);
		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(TenancyBillEditUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();
		if (uiWindow != null)
        {
        	if(UtilRequest.isPrepare("ActionRefresh",this)) {
        		prepareRefresh(null).callHandler();
        	}
            refresh(e);
            setPreSelecteRow();
        }
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

	 /** ���� */
	 public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		 super.actionAudit_actionPerformed(e);
		 int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		 if (rowIndex == -1) {
			 MsgBox.showInfo("��ѡ����!");
			 return;
		 }
		 IRow row = this.tblMain.getRow(rowIndex);
		 BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("bizState")
		 .getValue();
		 if (!BizStateEnum.SUBMIT_VALUE.equals(state.getValue())) {
			 MsgBox.showInfo("�ύ״̬����Ԥ������������!");
			 return;
		 }
		 if (BizStateEnum.LEASE_VALUE.equals(state.getValue())) {
			 MsgBox.showInfo("ת����״̬�ĵ��ݲ��ܽ�������������!");
			 return;
		 }
		 String id = getSelectedId();
		 SincerObligateFactory.getRemoteInstance().audit(BOSUuid.read(id));
		 this.refresh(null);
	 }

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		String id = getSelectedId();
		SincerObligateFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
		this.refresh(null);
	}
	 
	 /**
	  * ����Ԥ���������¼�
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
			 MsgBox.showInfo("����Ԥ������ִ�У���������!");
			 return;
		 }
		 BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("bizState")
		 .getValue();
		 if(BizStateEnum.INVALID_VALUE.equals(state.getValue()))
		 {
			 MsgBox.showInfo("����Ԥ�����Ѿ�����!");
			 return;
		 }
		 if(BizStateEnum.LEASE_VALUE.equals(state.getValue()))
		 {
			 MsgBox.showInfo("ת����״̬�ĵ��ݲ�������!");
			 return;
		 }
		 String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		 if (MsgBox.showConfirm2New(this, "���Ϻ󲻿ɻָ�����ȷ���Ƿ�����?") == MsgBox.YES) {
			 //����ʱ��Ҫ��״̬д�뵥���У�����ֹͣ��ǰ�������еĹ�����
			 SincerObligateInfo sinGateInfo = SincerObligateFactory.getRemoteInstance()
			 .getSincerObligateInfo(new ObjectUuidPK(BOSUuid.read(id)));
			 sinGateInfo.setState(FDCBillStateEnum.INVALID);
			 sinGateInfo.setBizState(BizStateEnum.INVALID);
			 SelectorItemCollection sels = new SelectorItemCollection();
			 sels.add("state");
			 sels.add("bizState");
			 SincerObligateFactory.getRemoteInstance().updatePartial(sinGateInfo, sels);

			 ProcessInstInfo instInfo = null;
			 ProcessInstInfo[] procInsts = null;
			 IEnactmentService service = EnactmentServiceFactory
			 .createRemoteEnactService();
			 procInsts = service.getProcessInstanceByHoldedObjectId(sinGateInfo.getId()
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
	  * ����Ԥ����ִ���¼�
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
			 MsgBox.showInfo("����Ԥ�����Ѿ�ִ��!");
			 return;
		 }
		 String auditorName = (String) row.getCell("auditor.name").getValue();
		 if (auditorName == null) {
			 MsgBox.showInfo("����Ԥ����û�����!");
			 return;
		 }
		 BizEnumValueInfo tenancyState = (BizEnumValueInfo) row.getCell(
		 "bizState").getValue();
		 if(BizStateEnum.INVALID_VALUE.equals(tenancyState.getValue()))
		 {
			 MsgBox.showInfo("����Ԥ�����Ѿ�����!");
			 return;
		 }
		 if(BizStateEnum.LEASE_VALUE.equals(tenancyState.getValue()))
		 {
			 MsgBox.showInfo("ת����״̬�ĵ��ݲ���ִ��!");
			 return;
		 }
		 if (MsgBox.showConfirm2New(this, "�Ƿ�ִ��?") == MsgBox.YES) {
			 String id = (String) row.getCell(this.getKeyFieldName()).getValue();
			 if (SincerObligateFactory.getRemoteInstance().execute(id)) {
				 MsgBox.showInfo("ִ�гɹ�!");
				 this.refresh(null);
			 }
		 }
	 }
	 
	 public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		 int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		 if (rowIndex == -1) {
			 MsgBox.showInfo("��ѡ����!");
			 return;
		 }
		 IRow row = this.tblMain.getRow(rowIndex);
		 BizEnumValueInfo state = (BizEnumValueInfo) row.getCell(
		 "bizState").getValue();
		 if(!BizStateEnum.SAVE_VALUE.equals(state.getValue()) && !BizStateEnum.SUBMIT_VALUE.equals(state.getValue()))
		 {
			 MsgBox.showInfo("��������ύ״̬��Ԥ���������޸�!");
			 return;
		 }
		super.actionEdit_actionPerformed(e);
	}
	 
	 public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		 int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		 if (rowIndex == -1) {
			 MsgBox.showInfo("��ѡ����!");
			 return;
		 }
		 IRow row = this.tblMain.getRow(rowIndex);
		 BizEnumValueInfo state = (BizEnumValueInfo) row.getCell(
		 "bizState").getValue();
		 if(!BizStateEnum.SAVE_VALUE.equals(state.getValue()) && !BizStateEnum.SUBMIT_VALUE.equals(state.getValue()))
		 {
			 MsgBox.showInfo("��������ύ״̬��Ԥ��������ɾ��!");
			 return;
		 }
		super.actionRemove_actionPerformed(e);
	}

	 public void actionReceiveBill_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionReceiveBill_actionPerformed(e);
		String id = getSelectedId();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
		SincerObligateInfo obligateInfo = SincerObligateFactory.getRemoteInstance().getSincerObligateInfo(new ObjectUuidPK(id),sels);
		BizStateEnum state = obligateInfo.getBizState();
		if(!BizStateEnum.AUDITTED.equals(state) && !BizStateEnum.SINCEROBLIGATED.equals(state)&&!BizStateEnum.LEASE.equals(state))
		{
			MsgBox.showInfo("������ĳ���Ԥ���������տ�");
			this.abort();
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("sellProject", obligateInfo.getSellProject());
    	uiContext.put(FDCReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.obligate);
    	uiContext.put(FDCReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.gathering);
    	uiContext.put(FDCReceivingBillEditUI.KEY_SELL_PROJECT, obligateInfo.getSellProject());
    	uiContext.put(FDCReceivingBillEditUI.KEY_SINCER_OBLIGATE, obligateInfo);
    	uiContext.put(FDCReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, Boolean.TRUE);
//    	uiContext.put(ReceiveBillEidtUI.KEY_TENANCYBILLID, id);
//    	uiContext.put(ReceiveBillEidtUI.KEY_MONEYTSYSTYPE, MoneySysTypeEnum.TenancySys);
    	IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(TENReceivingBillEditUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();
		
		this.refresh(null);
	}
	 
	 protected ICoreBase getBizInterface() throws Exception {
		 return SincerObligateFactory.getRemoteInstance();
	 }

	 protected ITreeBase getTreeInterface() throws Exception {
		 return null;
	 }

	 protected String getLongNumberFieldName() {
		 return "number";
	 }
}