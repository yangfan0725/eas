/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.ProductTypePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListFactory;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.KDTreeNode;

/**
 * output class name
 */
public class SHEAttachListListUI extends AbstractSHEAttachListListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SHEAttachListListUI.class);
    
    /**
     * output class constructor
     */
    private ProductTypePropertyEnum productTypeProperty=null;
    public SHEAttachListListUI() throws Exception
    {
        super();
    }
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new SHEAttachListInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SHEAttachListFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return SHEAttachListEditUI.class.getName();
	}
	protected void initTree() throws Exception {
		KDTreeNode root = new KDTreeNode("产品类型属性");
		KingdeeTreeModel mode=new KingdeeTreeModel(root);
		for(int i=0;i<ProductTypePropertyEnum.getEnumList().size();i++){
			KDTreeNode child = new KDTreeNode(((ProductTypePropertyEnum)ProductTypePropertyEnum.getEnumList().get(i)).getAlias());
			child.setUserObject(ProductTypePropertyEnum.getEnumList().get(i));
			root.add(child);
		}
		mode.setRoot(root);
		this.treeMain.setModel(mode);
	}
    protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put("productTypeProperty", productTypeProperty);
	}
	public void onLoad() throws Exception {
		initTree();
		super.onLoad();
		this.treeMain.setSelectionRow(0);
	}
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if(node!=null&&node.getUserObject() instanceof ProductTypePropertyEnum){
			productTypeProperty=(ProductTypePropertyEnum)node.getUserObject();
			this.actionAddNew.setEnabled(true);
		}else{
			productTypeProperty=null;
			this.actionAddNew.setEnabled(false);
		}
		this.execQuery();
	}
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try {
			FilterInfo filter = new FilterInfo();
			if(node!=null&&node.getUserObject() instanceof ProductTypePropertyEnum){
				filter.getFilterItems().add(new FilterItemInfo("productTypeProperty", productTypeProperty.getValue()));	
			}
			filter.getFilterItems().add(new FilterItemInfo("room.id", null,CompareType.EQUALS));
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (BOSException e) {
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

}