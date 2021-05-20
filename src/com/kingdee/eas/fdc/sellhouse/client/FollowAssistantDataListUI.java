/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.FollowAssistantDataFactory;
import com.kingdee.eas.fdc.sellhouse.FollowAssistantDataInfo;
import com.kingdee.eas.fdc.sellhouse.FollowAssistantDataTypeCollection;
import com.kingdee.eas.fdc.sellhouse.FollowAssistantDataTypeFactory;
import com.kingdee.eas.fdc.sellhouse.FollowAssistantDataTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * @auther qinyouzhen 
 * @date 2011-06-08
 * ������������
 */
public class FollowAssistantDataListUI extends AbstractFollowAssistantDataListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FollowAssistantDataListUI.class);
    
    /**
     * output class constructor
     */
    public FollowAssistantDataListUI() throws Exception
    {
        super();
    }

	public void onLoad() throws Exception {
		//super.onLoad();
		// ��ʼ����������������
		this.getFollowAssistantDataTypeTree(topTreeMain);

		// ��ʼ����Ŀ��
		this.btmTreeMain.setModel(FDCTreeHelper.getSellProjectTree(
				actionOnLoad, null));

		this.tblMain.setEditable(false);
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		
		//��ʼ����ť
		initButton();
	}
	
	/**
	 * ��ʼ����ť
	 */
	private void initButton() {
		setButtonDefaultStyl(btnAddNew);
		this.btnAddNew.setIcon(EASResource.getIcon("imgTbtn_new"));
		this.menuItemAddNew.setIcon(EASResource.getIcon("imgTbtn_new"));

		setButtonDefaultStyl(btnView);
		this.btnView.setIcon(EASResource.getIcon("imgTbtn_view"));
		this.menuItemView.setIcon(EASResource.getIcon("imgTbtn_view"));

		setButtonDefaultStyl(btnEdit);
		this.btnEdit.setIcon(EASResource.getIcon("imgTbtn_edit"));
		this.menuItemEdit.setIcon(EASResource.getIcon("imgTbtn_edit"));

		setButtonDefaultStyl(btnRemove);
		this.btnRemove.setIcon(EASResource.getIcon("imgTbtn_delete"));
		this.menuItemRemove.setIcon(EASResource.getIcon("imgTbtn_delete"));

		setButtonDefaultStyl(btnRefresh);
		this.btnRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));
		this.menuItemRefresh.setIcon(EASResource.getIcon("imgTbtn_refresh"));

		setButtonDefaultStyl(btnPrint);
		this.btnPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		this.menuItemPrint.setIcon(EASResource.getIcon("imgTbtn_print"));

		setButtonDefaultStyl(btnPrintPreview);
		this.btnPrintPreview.setIcon(EASResource.getIcon("imgTbtn_preview"));
		this.menuItemPrintPreview.setIcon(EASResource
				.getIcon("imgTbtn_preview"));

		setButtonDefaultStyl(btnCancel);
		this.btnCancel.setIcon(EASResource.getIcon("imgTbtn_forbid"));
		this.menuItemCancel.setIcon(EASResource.getIcon("imgTbtn_forbid"));

		setButtonDefaultStyl(btnCancelCancel);
		this.btnCancelCancel.setIcon(EASResource.getIcon("imgTbtn_staruse"));
		this.menuItemCancelCancel.setIcon(EASResource
				.getIcon("imgTbtn_staruse"));
		
		actionCancel.setEnabled(false);
		actionCancelCancel.setEnabled(false);
		actionEdit.setEnabled(false);
	}
	
	 /**
     * ����
     */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * ����
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}
	
	 /**
     * �����ߵ���,��ѯ������������
     * @param e
     * @throws Exception
     */
	private void valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode topNode = (DefaultKingdeeTreeNode) topTreeMain
				.getLastSelectedPathComponent();
		DefaultKingdeeTreeNode btmNode = (DefaultKingdeeTreeNode) btmTreeMain
				.getLastSelectedPathComponent();
		mainQuery = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		//�̻���������
		if (topNode != null) {
			if (topNode.getUserObject() instanceof FollowAssistantDataTypeInfo) {
				FollowAssistantDataTypeInfo typeInfo = (FollowAssistantDataTypeInfo) topNode
						.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("type.id", typeInfo.getId()
								.toString()));
			}
		}
		
		//��Ŀ
		if (btmNode != null) {
			if (btmNode.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProject = (SellProjectInfo) btmNode
						.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sellProject
								.getId().toString()));
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", null));
				if(topNode != null){
					filter.setMaskString(" #0 and (#1 or #2) ");
				}else{
					filter.setMaskString(" #0 or #1 ");
				}
			}
		}
		
		mainQuery.setFilter(filter);
		mainQuery.getSorter().add(new SorterItemInfo("sellProject.name"));
		this.tblMain.removeRows();
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
    
	/**
	 * ������Ϸ����̻�������������ѯ
	 */
	protected void topTreeMain_valueChanged(
			javax.swing.event.TreeSelectionEvent e) throws Exception {
		valueChanged(e);
	}

	/**
	 * ������·�����Ŀ����ѯ,
	 */
	protected void btmTreeMain_valueChanged(
			javax.swing.event.TreeSelectionEvent e) throws Exception {
		valueChanged(e);
	}
	
	/**
	 * ����ÿһ�ʼ�¼�Ƿ����,�Ӷ����ð�ť�Ƿ����
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			Boolean isEnabled = (Boolean) row.getCell("isEnabled").getValue();
			if (isEnabled.booleanValue()) {// ���������,���ð�ť����,�޸İ�ť������
				actionCancel.setEnabled(true);
				actionCancelCancel.setEnabled(false);
				actionEdit.setEnabled(false);
			} else {
				actionCancel.setEnabled(false);
				actionCancelCancel.setEnabled(true);
				actionEdit.setEnabled(true);
			}
		}
	}
	
	/**
	 * ����������������
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode topNode = (DefaultKingdeeTreeNode) topTreeMain.getLastSelectedPathComponent();
		DefaultKingdeeTreeNode btmNode = (DefaultKingdeeTreeNode) btmTreeMain.getLastSelectedPathComponent();
		
		if (topNode != null) {
			Object topObject = topNode.getUserObject();
			if (topObject != null
					&& topObject instanceof FollowAssistantDataTypeInfo) {
				getUIContext().put("typeInfo", topObject);
				if (btmNode != null) {
					Object btmObject = btmNode.getUserObject(); //��Ŀ������ѡ,����Ŀ�Զ�����
					if (btmObject != null
							&& btmObject instanceof SellProjectInfo) {
						getUIContext().put("sellProjectInfo", btmObject);
					}else{
						getUIContext().put("sellProjectInfo", null);
					}
				}
			} else {
				MsgBox.showInfo("�˽ڵ��Ǹ��ڵ�,�����������������!");
				SysUtil.abort();
			}
		} else {
			MsgBox.showInfo("��ѡ��ڵ�!");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}
	
	/**
	 * ������������������
	 * 
	 * @throws Exception
	 */
	public void getFollowAssistantDataTypeTree(KDTree treeMain) throws Exception {
		DefaultKingdeeTreeNode rootNode = createFollowAssistantDataTypeNode();
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		treeMain.setModel(model);
		treeMain.expandAllNodes(true, rootNode);
		treeMain.setShowsRootHandles(true);
	}
	
	public DefaultKingdeeTreeNode createFollowAssistantDataTypeNode()
			throws Exception {
		// ���ڵ�
		DefaultKingdeeTreeNode rootTreeNode = new DefaultKingdeeTreeNode(
				"������������"), subTreeNode = null;
		HashMap nodeMap = new HashMap();

		FollowAssistantDataTypeCollection typeCollection = FollowAssistantDataTypeFactory
				.getRemoteInstance().getFollowAssistantDataTypeCollection(
						" Order by Level");
		FollowAssistantDataTypeCollection leafUnits = new FollowAssistantDataTypeCollection();
		for (int i = 0; i < typeCollection.size(); i++) {
			FollowAssistantDataTypeInfo info = typeCollection.get(i);
			if (info.getParent() != null) {
				leafUnits.add(info);
			} else {
				subTreeNode = new DefaultKingdeeTreeNode(typeCollection.get(i));
				nodeMap.put(typeCollection.get(i).getId().toString(),
						subTreeNode);
				rootTreeNode.add(subTreeNode);
			}
		}

		// ������ʹ���������߶����������нڵ�
		for (int i = 0; i < leafUnits.size(); i++) {
			FollowAssistantDataTypeInfo info = leafUnits.get(i);
			subTreeNode = new DefaultKingdeeTreeNode(info);

			// ���ҵȼ������Ļ���ͣ����뵽���ӽڵ�
			if (!info.isIsLeaf()) {
				subTreeNode = new DefaultKingdeeTreeNode(leafUnits.get(i));
				nodeMap.put(leafUnits.get(i).getId().toString(), subTreeNode);

			}
			if (info.getId().toString() != null
					&& nodeMap.containsKey(info.getParent().getId().toString())) {
				((DefaultKingdeeTreeNode) nodeMap.get(info.getParent().getId()
						.toString())).add(subTreeNode);
			}
		}
		nodeMap = null;
		return rootTreeNode;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return FollowAssistantDataTypeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return FollowAssistantDataEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FollowAssistantDataFactory.getRemoteInstance();
	}

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new FollowAssistantDataInfo();
	}


}