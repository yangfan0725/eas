/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyBillFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ReturnTenancyBillListUI extends AbstractReturnTenancyBillListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ReturnTenancyBillListUI.class);

	/**
	 * output class constructor
	 */
	public ReturnTenancyBillListUI() throws Exception {
		super();
	}

	@Override
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.treeMain_valueChanged(e);
		execQuery();
	}
	
	private void initTree() throws Exception{
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	

	@Override
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
			try {
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
				viewInfo = (EntityViewInfo) this.mainQuery.clone();

				FilterInfo filter = new FilterInfo();
				if (node != null  &&  node.getUserObject() instanceof SellProjectInfo) {
					SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("project.id", pro.getId().toString()));
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
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		
		
		initTree();
		
		
	
	
	}
	
	
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showInfo("请选择具体销售项目！");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
		super.prepareUIContext(uiContext, e);
	}

	@Override
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAddNew_actionPerformed(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node.getUserObject() instanceof  SellProjectInfo){
			SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
			getUIContext().put("project", pro);
		}
		
	}
	
	@Override
	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return ReturnTenancyBillEditUI.class.getName();
	}
	
	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return ReturnTenancyBillFactory.getRemoteInstance();
	}

}