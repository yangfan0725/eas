/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonF7Factory;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonF7Info;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SupplierLinkPersonF7ListUI extends AbstractSupplierLinkPersonF7ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierLinkPersonF7ListUI.class);
    private SupplierFileTypeInfo supplierFileType=null;
    public SupplierLinkPersonF7ListUI() throws Exception
    {
        super();
    }
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new SupplierLinkPersonF7Info();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierLinkPersonF7Factory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return SupplierLinkPersonF7EditUI.class.getName();
	}
	protected void initTree() throws Exception {
		KDTreeNode root = new KDTreeNode("µµ∞∏∑÷¿‡");
		KingdeeTreeModel mode=new KingdeeTreeModel(root);
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view=new EntityViewInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));	
		view.setFilter(filter);
		SupplierFileTypeCollection col=SupplierFileTypeFactory.getRemoteInstance().getSupplierFileTypeCollection(view);
		CRMHelper.sortCollection(col, "number", true);
		for(int i=0;i<col.size();i++){
			KDTreeNode child = new KDTreeNode(col.get(i).getName());
			child.setUserObject(col.get(i));
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
		uiContext.put("supplierFileType", supplierFileType);
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
		if(node!=null&&node.getUserObject() instanceof SupplierFileTypeInfo){
			supplierFileType=(SupplierFileTypeInfo)node.getUserObject();
			this.actionAddNew.setEnabled(true);
		}else{
			supplierFileType=null;
			this.actionAddNew.setEnabled(false);
		}
		this.execQuery();
	}
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try {
			FilterInfo filter = new FilterInfo();
			if(node!=null&&node.getUserObject() instanceof SupplierFileTypeInfo){
				filter.getFilterItems().add(new FilterItemInfo("supplierFileType.id", supplierFileType.getId().toString()));	
			}
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
    protected void setIsEnabled(boolean flag)
    throws Exception
{
/* <-MISALIGNED-> */ /* 134*/        getCtrler().checkPermission("ACTION_MODIFY");
/* <-MISALIGNED-> */ /* 135*/        int activeRowIndex = tblMain.getSelectManager().getActiveRowIndex();
/* <-MISALIGNED-> */ /* 136*/        if(activeRowIndex < 0)
/* <-MISALIGNED-> */ /* 137*/            return;
/* <-MISALIGNED-> */ /* 139*/        if(!flag && !true && isSystemDefaultData(activeRowIndex))
    {
/* <-MISALIGNED-> */ /* 141*/            MsgBox.showError(EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "operate_SysData"));
/* <-MISALIGNED-> */ /* 142*/            return;
    }
/* <-MISALIGNED-> */ /* 144*/        String id = tblMain.getRow(activeRowIndex).getCell("id").getValue().toString().trim();
/* <-MISALIGNED-> */ /* 145*/        FDCDataBaseInfo info = getBaseDataInfo();
/* <-MISALIGNED-> */ /* 146*/        info.setId(BOSUuid.read(id));
/* <-MISALIGNED-> */ /* 147*/        info.setIsEnabled(flag);
///* <-MISALIGNED-> */ /* 149*/        String number = tblMain.getRow(activeRowIndex).getCell("number").getValue().toString().trim();
///* <-MISALIGNED-> */ /* 150*/        String name = tblMain.getRow(activeRowIndex).getCell("name").getValue().toString().trim();
///* <-MISALIGNED-> */ /* 151*/        info.setNumber(number);
///* <-MISALIGNED-> */ /* 152*/        info.setName(name);
/* <-MISALIGNED-> */ /* 153*/        SelectorItemCollection sic = new SelectorItemCollection();
/* <-MISALIGNED-> */ /* 155*/        sic.add(new SelectorItemInfo("isEnabled"));
/* <-MISALIGNED-> */ /* 156*/        String message = null;
/* <-MISALIGNED-> */ /* 157*/        if(flag)
    {
/* <-MISALIGNED-> */ /* 158*/            getBizInterface().updatePartial(info, sic);
/* <-MISALIGNED-> */ /* 159*/            message = EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Enabled_OK");
    } else
    {
/* <-MISALIGNED-> */ /* 164*/            getBizInterface().updatePartial(info, sic);
/* <-MISALIGNED-> */ /* 165*/            message = EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "DisEnabled_OK");
    }
/* <-MISALIGNED-> */ /* 170*/        setMessageText(message);
/* <-MISALIGNED-> */ /* 171*/        showMessage();
/* <-MISALIGNED-> */ /* 172*/        tblMain.refresh();
/* <-MISALIGNED-> */ /* 173*/        loadFields();
/* <-MISALIGNED-> */ /* 174*/        actionCancel.setEnabled(false);
/* <-MISALIGNED-> */ /* 175*/        actionCancelCancel.setEnabled(false);
}
}