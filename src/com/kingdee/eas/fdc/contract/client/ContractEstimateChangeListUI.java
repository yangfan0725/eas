/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractEstimateChangeListUI extends AbstractContractEstimateChangeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractEstimateChangeListUI.class);
    
    /**
     * output class constructor
     */
    public ContractEstimateChangeListUI() throws Exception
    {
        super();
    }


    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }


    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {   
    	DefaultKingdeeTreeNode node=getProjSelectedTreeNode();
    	FDCClientUtils.checkSelectProj(this, node);
		FDCClientUtils.checkProjWithCostOrg(this, node);
		
		if (node!=null&&node.getUserObject()!=null&&node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo pro=(CurProjectInfo)node.getUserObject();
			if(pro.isIsWholeAgeStage()){
				FDCMsgBox.showWarning(this, "全期项目不能新增预估合同变动！");
				SysUtil.abort();
			}
		}
		super.actionAddNew_actionPerformed(e);
    }

    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(!checkBeforeOprate(new String[]{FDCBillStateEnum.SAVED_VALUE,FDCBillStateEnum.SUBMITTED_VALUE})){
    		MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
    	}
		super.actionEdit_actionPerformed(e);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(!checkBeforeOprate(new String[]{FDCBillStateEnum.SAVED_VALUE,FDCBillStateEnum.SUBMITTED_VALUE})){
    		MsgBox.showWarning(this, ContractClientUtils.getRes("cantRemove"));
			SysUtil.abort();
    	}
		super.actionRemove_actionPerformed(e);
	}
	protected boolean checkBeforeOprate(String[] states) throws Exception 
	{
		boolean flag = false;
		
		List idList = ContractClientUtils.getSelectedIdValues(this.getBillListTable(), getKeyFieldName());

		if(idList==null || idList.size()==0){
			return flag ;
		}

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBillBaseCollection coll = ((IFDCBill)getBizInterface()).getCoreBillBaseCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			String billState = element.getString(getBillStatePropertyName());
			boolean pass = false;
			for (int i = 0; i < states.length; i++) 
			{
				if(billState.equals(states[i])) 
				{
					pass = true;
				}
			}
			if(!pass)
			{
				flag = false;
				break ;
			}
			else
			{
				flag = pass;
			}
		}

		return flag;
	}

    protected String getEditUIName() {
    	return ContractEstimateChangeEditUI.class.getName();
    }

    protected ICoreBase getBizInterface() throws Exception {
    	return ContractEstimateChangeBillFactory.getRemoteInstance();
    }
    
    public void onLoad() throws Exception {
    	btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
    	super.onLoad();
    	initTree();
    	this.actionUnAudit.setVisible(false);
		this.btnUnAudit.setVisible(false);
		ClientHelper.changeTableNumberFormat(tblMain, new String[]{"estimateAmount","programmingContract.amount"});
    }
    public boolean isAutoIgnoreZero()
    {
    	return false;
    }
    /**
	 * 构建工程项目树
	 * 
	 * @throws Exception
	 */
    private void initTree() throws Exception {
    	ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, treeProject, actionOnLoad);
		treeProject.setSelectionRow(0);
		treeProject.expandRow(0);
	}

    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	super.prepareUIContext(uiContext, e);
    	DefaultKingdeeTreeNode projectNode  = getProjSelectedTreeNode();
		Object project  = null;
		if(projectNode!=null){
			project = projectNode.getUserObject();
		}
		if(project instanceof CurProjectInfo)
    	uiContext.put("projectId", ((CurProjectInfo) project).getId());
    }
    
    
	protected void treeProject_valueChanged(TreeSelectionEvent e)
			throws Exception {
		 treeSelectChange();
	}
	
	
	protected void treeSelectChange() throws Exception {

		DefaultKingdeeTreeNode projectNode  = getProjSelectedTreeNode();
		Object project  = null;
		if(projectNode!=null){
			project = projectNode.getUserObject();
		}
		mainQuery.setFilter(getTreeSelectFilter(project));
		execQuery();

	}
	
	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeProject
				.getLastSelectedPathComponent();
	}
	
	
	
	protected FilterInfo getTreeSelectFilter(Object projectNode) throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		/*
		 * 工程项目树
		 */
		if (projectNode != null && projectNode instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();	
				}else{
					id = ((FullOrgUnitInfo)projTreeNodeInfo).getId();
				}				
				
				String orgUnitLongNumber = null;
				if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
					orgUnitLongNumber = orgUnit.getLongNumber();
				}else{
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}
				
				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));
				if(filter!=null){
					filter.mergeFilter(f,"and");
				}
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,
						CompareType.INCLUDE));
			}

		}

		
		return filter;
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		
		checkBillState(new String[]{getStateForAudit()}, "selectRightRowForAudit");
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		Boolean isFromContract = (Boolean)this.tblMain.getRow(rowIndex).getCell("isFromContract").getValue();
		if(isFromContract != null && isFromContract.booleanValue()){
			FDCMsgBox.showInfo(this,"同合同审批不能单独审批！");
			this.abort();
		}
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		ContractEstimateChangeBillFactory.getRemoteInstance().audit(selectedIdValues);

		showOprtOKMsgAndRefresh();
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {

		checkSelected();
		checkBillState(new String[]{getStateForUnAudit()}, "selectRightRowForUnAudit");
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		ContractEstimateChangeBillFactory.getRemoteInstance().unAudit(selectedIdValues);
		showOprtOKMsgAndRefresh();
	}
	
	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}
	
	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;
	}
}