/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.Rectangle;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUException;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.client.ClientHelper;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationContractEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationContractEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherContractEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherContractEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.IBaseTransaction;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SupplierEvaluationListUI extends AbstractSupplierEvaluationListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierEvaluationListUI.class);
    public SupplierEvaluationListUI() throws Exception
    {
        super();
    }
	protected String getEditUIName() {
		return SupplierEvaluationEditUI.class.getName();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void buildInviteTypeTree() throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		TreeModel model = FDCClientHelper.createDataTree(InviteTypeFactory.getRemoteInstance(), filter, "�ɹ����");
		this.supplierTypeTree.setModel(model);
		this.supplierTypeTree.setSelectionRow(0);
	}
	protected void buildOrgTree() throws Exception{
		OrgUnitInfo cuInfo = SysContext.getSysContext().getCurrentOrgUnit();
		if (!cuInfo.isIsPurchaseOrgUnit()) {
			MsgBox.showInfo(this, "�ǲɹ���֯���ܲ���");
			SysUtil.abort();
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.PURCHASE,"", cuInfo.getId().toString(), null, FDCHelper.getActionPK(this.actionOnLoad));
		this.orgTree.setModel(orgTreeModel);
		this.orgTree.setSelectionRow(0);
	}
	protected void refresh(ActionEvent e) throws Exception{
		this.tblMain.removeRows();
	}
	protected void supplierTypeTree_valueChanged(TreeSelectionEvent e)throws Exception {
		this.refresh(null);
	}
	protected void orgTree_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh(null);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return FDCSplQualificationAuditBillFactory.getRemoteInstance();
	}
	protected String getEditUIModal(){
		return UIFactoryName.NEWTAB;
	}
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
			}
		return null;
	}
	private FilterInfo getTreeFilter() throws Exception {
    	FilterInfo filter = new FilterInfo();
    	FilterItemCollection filterItems = filter.getFilterItems();
    	
    	DefaultKingdeeTreeNode TypeNode = this.getSelectedTreeNode(supplierTypeTree);
    	DefaultKingdeeTreeNode OrgNode = this.getSelectedTreeNode(orgTree);
    	Object TypeInfo = null;
    	//�Ƿ�ѡ��
    	if(TypeNode != null && TypeNode.getUserObject() != null){
    		TypeInfo = TypeNode.getUserObject();
    		//����������title
    		kDContainer1.setTitle(TypeNode.getText());
    	}
    	if (TypeInfo instanceof InviteTypeInfo) {
		    String longNumber = ((InviteTypeInfo)TypeInfo).getLongNumber();
			filterItems.add(new FilterItemInfo("supplier.inviteType.longNumber", longNumber+"%",CompareType.LIKE));
		}
    	if(OrgNode.getUserObject() instanceof OrgStructureInfo){
    		SelectorItemCollection sel=new SelectorItemCollection();
    		sel.add("longNumber");
    		FullOrgUnitInfo org=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(((OrgStructureInfo)OrgNode.getUserObject()).getUnit().getId()),sel);
    		String longNumber=org.getLongNumber();
    		filterItems.add(new FilterItemInfo("orgUnit.longNumber", longNumber+"%",CompareType.LIKE));
    	}
    	return filter;
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		try {
			FilterInfo filter = getTreeFilter();
			if(filter==null){
				filter = new FilterInfo();
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
				
			} else
			{
				viewInfo.setFilter(filter);
			}
			if(viewInfo.getSorter().size() < 2  ){ //Ĭ����һ�� ID ASC
				SorterItemCollection sort=new SorterItemCollection();
				SorterItemInfo supplier=new SorterItemInfo("supplier.number");
				sort.add(supplier);
				
				SorterItemInfo evaluationType=new SorterItemInfo("evaluationType.number");
				sort.add(evaluationType);
				
				SorterItemInfo template=new SorterItemInfo("template.number");
				sort.add(template);
				
				viewInfo.setSorter(sort);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		buildOrgTree();
		buildInviteTypeTree();
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);

		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		
		this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		
		if(!SysContext.getSysContext().getCurrentOrgUnit().isIsPurchaseOrgUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
		if(SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("ppl")){
			KDWorkButton btnUpdate=new KDWorkButton();
			btnUpdate.setText("������������ͬ��¼��");
			this.toolBar.add(btnUpdate);
			btnUpdate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
		                beforeActionPerformed(e);
		                try {
		                	btnUpdate_actionPerformed(e);
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                    afterActionPerformed(e);
		                }
		            }
		        });
		}
	}
	protected void btnUpdate_actionPerformed(ActionEvent e) throws Exception {
		int m=0;
		int n=0;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select distinct head.fid,head.fishascontract,head.fcontract,head.fcontractbillid,head.fcontractAmount from T_SPL_FDCSplQualiAuditBill head left join T_GYS_SupplierEvaContractEntry entry on entry.fheadid=head.fid where (head.fcontract is not null or head.fcontractbillid is not null) and entry.fcontract is null and entry.fcontractbillid is null");
		IRowSet rs = sqlBuilder.executeQuery();
		while(rs.next()){
			String id=rs.getString("fid");
			FDCSplQualificationAuditBillInfo info=FDCSplQualificationAuditBillFactory.getRemoteInstance().getFDCSplQualificationAuditBillInfo(new ObjectUuidPK(id));
			SupplierEvaluationContractEntryInfo entry=new SupplierEvaluationContractEntryInfo();
			entry.setHead(info);
			entry.setIsHasContract(rs.getBoolean("fishascontract"));
			if(rs.getBoolean("fishascontract")){
				ContractBillInfo contract=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(rs.getString("fcontractbillid")));
				entry.setContractBill(contract);
			}else{
				entry.setContract(rs.getString("fcontract"));
			}
			entry.setContractAmount(rs.getBigDecimal("fcontractAmount"));
			SupplierEvaluationContractEntryFactory.getRemoteInstance().addnew(entry);
			m++;
		}
		sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select distinct head.fid,head.fishascontract,head.fcontract,head.fcontractbillid,head.fcontractAmount from T_GYS_SupplierReviewGather head left join T_GYS_SupplierRGContractEntry entry on entry.fheadid=head.fid where (head.fcontract is not null or head.fcontractbillid is not null) and entry.FSupplierEvaluationId is null ");
		rs = sqlBuilder.executeQuery();
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("id");
		sel.add("entry.auditTemplate.id");
		sel.add("entry.auditTemplate.parent.id");
		
		while(rs.next()){
			String id=rs.getString("fid");
			SupplierReviewGatherInfo info=SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherInfo(new ObjectUuidPK(id),sel);
			if(info.getEntry().size()==0) continue;
			for(int i=0;i<info.getEntry().size();i++){
				SupplierReviewGatherContractEntryInfo entry=new SupplierReviewGatherContractEntryInfo();
				entry.setHead(info);
				if(info.getEntry().get(i).getAuditTemplate()==null) continue;
				entry.setSupplierEvaluation(info.getEntry().get(i).getAuditTemplate().getParent());
				SupplierReviewGatherContractEntryFactory.getRemoteInstance().addnew(entry);
				n++;
			}
		}
		FDCMsgBox.showInfo(this,m+"����Ӧ���������ݳɹ�������\n"+n+"����Ӧ������������ݳɹ�������");
	}
	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		ClientHelper.getFootRow(tblMain, new String[]{"contractAmount"});
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			if (!FDCBillStateEnum.SUBMITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("���ݲ����ύ״̬�����ܽ�������������");
				return;
			}
			((IFDCBill)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			if (!FDCBillStateEnum.AUDITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("���ݲ�������״̬�����ܽ��з�����������");
				return;
			}
			((IFDCBill)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		FDCBillStateEnum state = getBizState(id);
		if (!FDCBillStateEnum.SAVED.equals(state)&&!FDCBillStateEnum.SUBMITTED.equals(state)) {
			FDCMsgBox.showWarning("���ݲ��Ǳ�������ύ״̬�����ܽ����޸Ĳ�����");
			return;
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		FDCBillStateEnum state = getBizState(id);
		if (!FDCBillStateEnum.SAVED.equals(state)&&!FDCBillStateEnum.SUBMITTED.equals(state)) {
			FDCMsgBox.showWarning("���ݲ��Ǳ�������ύ״̬�����ܽ���ɾ��������");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}
	public FDCBillStateEnum getBizState(String id) throws EASBizException, BOSException, Exception{
    	if(id==null) return null;
    	SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("state");
    	return ((FDCBillInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getState();
    }
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
    	DefaultKingdeeTreeNode OrgNode = this.getSelectedTreeNode(orgTree);
		if(OrgNode!=null&&OrgNode.getUserObject()!=null){
			if(OrgNode.getUserObject() instanceof OrgStructureInfo){
				uiContext.put("org", OrgNode.getUserObject());
			}else{
				uiContext.put("org", null);
			}
		}
	}
}