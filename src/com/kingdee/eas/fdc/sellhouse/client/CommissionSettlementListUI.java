/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanInfo;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillFactory;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CommissionSettlementListUI extends AbstractCommissionSettlementListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CommissionSettlementListUI.class);
    
    /**
     * output class constructor
     */
    public CommissionSettlementListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

   

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        
    	super.actionAddNew_actionPerformed(e);
   
    	
    }

    

    @Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return CommissionSettlementBillFactory.getRemoteInstance();
	}

	@Override
	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return CommissionSettlementEditUI.class.getName();
	}
	
	@Override
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		this.actionAudit.setVisible(true);
		this.actionUnAudit.setVisible(true);
		initTree();
	}
	
	@Override
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		// TODO Auto-generated method stub
		super.prepareUIContext(uiContext, e);
		ItemAction action  = getActionFromActionEvent(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeOrg.getLastSelectedPathComponent();
		if (node != null  &&  node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
			    uiContext.put("sellProject", pro);
	    }
	}
	
   
	
	public void initTree() throws Exception{
        this.treeOrg.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
 		this.treeOrg.expandAllNodes(true, (TreeNode) this.treeOrg.getModel().getRoot());
     }
	
	  protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
	 		try {
	 			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeOrg.getLastSelectedPathComponent();
	 			viewInfo = (EntityViewInfo) this.mainQuery.clone();

	 			FilterInfo filter = new FilterInfo();
	 			if (node != null  &&  node.getUserObject() instanceof SellProjectInfo) {
	 				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
	 				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", pro.getId().toString()));
	 			} else {
	 				filter.getFilterItems().add(new FilterItemInfo("id", null));
	 			}

	 			if (viewInfo.getFilter() != null) {
	 				viewInfo.getFilter().mergeFilter(filter, "and");
	 			} else {
	 				viewInfo.setFilter(filter);
	 			}
	 		} catch (Exception e) {
	 			handleException(e);
	 		}

	 		return super.getQueryExecutor(queryPK, viewInfo);
	 	}

	
	@Override
	protected void treeOrg_valueChanged(TreeSelectionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.treeOrg_valueChanged(e);
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeOrg.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if(!saleOrg.isIsBizUnit())
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}else
		{
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionAudit.setEnabled(true);
			this.actionUnAudit.setEnabled(true);
		}
		this.execQuery();
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED, "cantAudit",id.get(i).toString());
			CommissionSettlementBillFactory.getRemoteInstance().audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, "cantUnAudit",id.get(i).toString());
			CommissionSettlementBillFactory.getRemoteInstance().unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	 public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state,
			String warning, String id) throws Exception {
		FDCClientUtils.checkBillInWorkflow(this, id);
		CommissionSettlementBillInfo info = CommissionSettlementBillFactory
				.getRemoteInstance().getCommissionSettlementBillInfo(
						new ObjectUuidPK(BOSUuid.read(id)));
		boolean b = info != null && info.getState() != null
				&& info.getState().equals(state);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}
		
	}
	 protected static final String CANTEDIT = "cantEdit";
		protected static final String CANTREMOVE = "cantRemove";
	 public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
			checkSelected();
			int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			IRow row = this.tblMain.getRow(rowIndex);
			String id = (String) row.getCell(this.getKeyFieldName()).getValue();
	    	
	    	checkBeforeEditOrRemove(CANTEDIT,id);
			super.actionEdit_actionPerformed(e);
		}

		public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
			checkSelected();
			ArrayList id = getSelectedIdValues();
			for(int i = 0; i < id.size(); i++){
		    	checkBeforeEditOrRemove(CANTREMOVE,id.get(i).toString());
			}
			super.actionRemove_actionPerformed(e);
		}
		protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
	    	//检查是否在工作流中
			FDCClientUtils.checkBillInWorkflow(this, id);
			
			SelectorItemCollection sels = super.getSelectors();
			sels.add("state");
			sels.add("creator.id");
			CommissionSettlementBillInfo info=(CommissionSettlementBillInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels);
			FDCBillStateEnum state = info.getState();
			if (state != null
					&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED)) {
				if(warning.equals(CANTEDIT)){
					FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
					SysUtil.abort();
				}else{
					FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
					SysUtil.abort();
				}
			}
		}
		protected boolean isIgnoreCUFilter() {
			return true;
		}
}