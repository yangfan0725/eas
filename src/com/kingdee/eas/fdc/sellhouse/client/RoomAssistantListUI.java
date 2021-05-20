/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.ISHECusAssistantData;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataFactory;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomAssistantListUI extends
		AbstractRoomAssistantListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomAssistantListUI.class);
	private static final String OPERATION_EDIT = "edit";
	private static final String OPERATION_DELETE = "delete";
	PropertyChangeListener treeMain_PropertyChangeListener = null;
//	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	protected FullOrgUnitInfo org=SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
    
	Map orgMap = FDCSysContext.getInstance().getOrgMap();
	/**
	 * output class constructor
	 */
	public RoomAssistantListUI() throws Exception {
		super();
	}
	 public void checkTreeNodeSelected(ActionEvent e)
	    {
		 
		 	super.checkTreeNodeSelected(e);
		 	 Action action = getActionFromActionEvent(e);
		 	if(action.equals(actionGroupEdit)){
		 		checkPopValueNode("�޸�");
		 	}else if(action.equals(actionGroupRemove)){
		 		checkPopValueNode("ɾ��");
		 	}
		 	
		 	//���ֻ���һ���쳣������
//		 	Object obj = e.getSource();
//		 	IItemAction act  =null;
//			if (obj != null && obj instanceof AbstractButton)
//	        {
//				act = (IItemAction) ((AbstractButton) obj).getAction();
//	        }
//		 	if(null!=act){
//		 		if(act.getItemAction() instanceof ActionGroupRemove ){
//		 			checkPopValueNode("ɾ��");
//		 		}
//		 		if(act.getItemAction() instanceof ActionGroupEdit){
//		 			checkPopValueNode("�޸�");
//		 		}
//		 	}
//		 	  
	    }
	 /**
	  * @description У��Ԥ��ڵ�
	  * @author tim_gao
	  */
	 public void checkPopValueNode(String str){
		 KDTreeNode treeNode = getSelectedTreeNode();
	 	  RoomAssistantTypeInfo roomAssistantType = (RoomAssistantTypeInfo) treeNode.getUserObject();
	 	  if(true==roomAssistantType.isIsPopValue()){
	 		  FDCMsgBox.showWarning("Ԥ��ڵ㣬����"+str+"��");
	 		  SysUtil.abort();
	 	  }
	 }
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();

		// ��ʼ����Ŀ��
//		this.kDTree1.setModel(FDCTreeHelper.getSellProjectTree(actionOnLoad,
//				null));
//		this.kDTree1.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		
		this.kDTree1.setModel(CRMTreeHelper.getSellProjectTree(actionOnLoad,false));
		kDTree1.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		kDTree1.setSelectionRow(0);
		
		this.tblMain.setEditable(false);
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);

		//����������֯�ж�,��Ϊ��¥��֯
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.btnCancel.setEnabled(false);
			this.btnCancelCancel.setEnabled(false);

			actionGroupAddNew.setEnabled(false);
			actionGroupEdit.setEnabled(false);
			this.btnGroupRemove.setEnabled(false);
		}else{
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.btnCancel.setEnabled(true);
			this.btnCancelCancel.setEnabled(true);

			actionGroupAddNew.setEnabled(true);
			actionGroupEdit.setEnabled(true);
			this.btnGroupRemove.setEnabled(true);
		}
//		if (!saleOrg.isIsBizUnit()) {
//			this.btnAddNew.setEnabled(false);
//			this.btnEdit.setEnabled(false);
//			this.btnRemove.setEnabled(false);
//			this.btnCancel.setEnabled(false);
//			this.btnCancelCancel.setEnabled(false);
//
//			btnGroupAddNew.setEnabled(false);
//			btnGroupEdit.setEnabled(false);
//			this.btnGroupRemove.setEnabled(false);
//		}
//		this.actionAddNew.setEnabled(false);
//		this.actionEdit.setEnabled(false);
//		this.actionRemove.setEnabled(false);
//		this.actionGroupAddNew.setEnabled(false);
//		actionGroupEdit.setEnabled(false);
//		btnLocate.setVisible(true);
//		menuItemLocate.setVisible(true);
//		btnGroupMoveTree.setVisible(false);
		treeMain.setSelectionRow(0); // Ĭ��ѡ���ڵ�
		kDTree1.setSelectionRow(0); // Ĭ��ѡ���ڵ�
	}

	protected void initTree() throws Exception {
		super.initTree();
	}

	protected String getRootName() {
		return "���丨������";
	}

	// public void onShow() throws Exception {
	// super.onShow();
	// DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode)
	// ((DefaultTreeModel) treeMain.getModel()).getRoot();
	// rootNode.setText("���丨������");
	// treeBuilder.refreshTreeNode(treeMain, (KDTreeNode) rootNode);
	// treeMain.expandAllNodes(true, (TreeNode)
	// this.treeMain.getModel().getRoot());
	// treeMain.setSelectionRow(0);
	//		
	// // treeMain_PropertyChangeListener=new PropertyChangeListener(){
	// // public void propertyChange(PropertyChangeEvent evt) {
	// // if(evt.getNewValue() instanceof TreePath && evt.getOldValue()==null){
	// // TreePath path=(TreePath)evt.getNewValue();
	// // if("[root]".equals(path.toString())){
	// // KDTree tree=(KDTree)evt.getSource();
	// //
	// // tree.removePropertyChangeListener(treeMain_PropertyChangeListener);
	// // DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode)
	// ((DefaultTreeModel) tree.getModel()).getRoot();
	// // rootNode.setText("���丨������");
	// // rootNode.setUserObject("���丨������");
	// // try {
	// // treeBuilder.refreshTreeNode(tree, (KDTreeNode) rootNode);
	// // treeMain.expandAllNodes(true, (TreeNode)
	// RoomAssistantListUI.this.treeMain.getModel().getRoot());
	// // treeMain.setSelectionRow(0);
	// // } catch (Exception e) {
	// // e.printStackTrace();
	// // }
	// // tree.addPropertyChangeListener(treeMain_PropertyChangeListener);
	// // }
	// // }
	// // }
	// // };
	// // treeMain.addPropertyChangeListener(treeMain_PropertyChangeListener);
	// }
	//    
	// public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
	// {
	// super.actionRefresh_actionPerformed(e);
	// DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode)
	// ((DefaultTreeModel) treeMain.getModel()).getRoot();
	// rootNode.setText("���丨������");
	// treeBuilder.refreshTreeNode(treeMain, (KDTreeNode) rootNode);
	// treeMain.expandAllNodes(true, (TreeNode)
	// this.treeMain.getModel().getRoot());
	// treeMain.setSelectionRow(0);
	// }

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			Boolean isEnabled = (Boolean) row.getCell("isEnabled").getValue();
			
//			//����������֯�ж�,��Ϊ��¥��֯
//			FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
//			String idStr =null;
//			if(null!= orgUnit.getId()){
//				idStr = orgUnit.getId().toString();
//			}
//			if(null==orgMap.get(idStr)){
//				this.btnCancel.setEnabled(false);
//				this.btnCancelCancel.setEnabled(false);
//			} else {
//				if (isEnabled.booleanValue()) {// ���������,���ð�ť����,�޸İ�ť������
//					actionCancel.setEnabled(true);
//					actionCancelCancel.setEnabled(false);
//				} else {
//					actionCancel.setEnabled(false);
//					actionCancelCancel.setEnabled(true);
//				}
//			}
//			if (!saleOrg.isIsBizUnit()) {
//				this.btnCancel.setEnabled(false);
//				this.btnCancelCancel.setEnabled(false);
//			} else {
//				if (isEnabled.booleanValue()) {// ���������,���ð�ť����,�޸İ�ť������
//					actionCancel.setEnabled(true);
//					actionCancelCancel.setEnabled(false);
//				} else {
//					actionCancel.setEnabled(false);
//					actionCancelCancel.setEnabled(true);
//				}
//			}
		}
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		super.treeMain_valueChanged(e);
		valueChanged(e);
	}

	/**
	 * output kDTree1_valueChanged method
	 */
	protected void kDTree1_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		super.kDTree1_valueChanged(e);
		valueChanged(e);
	}

	/**
	 * �����ߵ���,��ѯ���丨������
	 * 
	 * @param e
	 * @throws Exception
	 */
	private void valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode topNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		DefaultKingdeeTreeNode btmNode = (DefaultKingdeeTreeNode) kDTree1
				.getLastSelectedPathComponent();
		mainQuery = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		// ���丨������
		if (topNode != null) {
			if (topNode.getUserObject() instanceof RoomAssistantTypeInfo) {
				RoomAssistantTypeInfo typeInfo = (RoomAssistantTypeInfo) topNode
						.getUserObject();
				
				this.getUIContext().put("level", new Integer(typeInfo.getLevel()));
				filter.getFilterItems().add(
						new FilterItemInfo("type.id", typeInfo.getId()
								.toString()));
				
				//����������֯�ж�,��Ϊ��¥��֯
				FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
				String idStr =null;
				if(null!= orgUnit.getId()){
					idStr = orgUnit.getId().toString();
				}
				if(null==orgMap.get(idStr)){
					this.actionGroupAddNew.setEnabled(false);
					this.actionGroupEdit.setEnabled(false);
					this.actionGroupRemove.setEnabled(false);
					this.actionAddNew.setEnabled(false);
					this.actionEdit.setEnabled(false);
					this.actionRemove.setEnabled(false);
				}else{
					this.actionGroupAddNew.setEnabled(true);
					this.actionGroupEdit.setEnabled(true);
					this.actionGroupRemove.setEnabled(true);
					this.actionAddNew.setEnabled(true);
					this.actionEdit.setEnabled(true);
					this.actionRemove.setEnabled(true);
				}
//				if (saleOrg.isIsBizUnit()) {
//					this.actionAddNew.setEnabled(true);
//				}
//				// if(typeInfo.isIsDefault()){
//				// this.actionRemove.setEnabled(false);
//				// }else{
//				// this.actionRemove.setEnabled(true);
//				// }
//			} else {
//				this.actionAddNew.setEnabled(false);
//			}
		}else{
			
			this.actionGroupEdit.setEnabled(false);
			this.actionGroupRemove.setEnabled(false);
		
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			
			filter.getFilterItems().add(
					new FilterItemInfo("type.id", null));
		}
		}
		// ��Ŀ
		if (btmNode != null) {
			this.actionAddNew.setEnabled(true);
			if (btmNode.getUserObject() instanceof SellProjectInfo) {
				
				//����������֯�ж�,��Ϊ��¥��֯
//				FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
//				String idStr =null;
//				if(null!= orgUnit.getId()){
//					idStr = orgUnit.getId().toString();
//				}
//				if(null==orgMap.get(idStr)){
//					actionGroupAddNew.setEnabled(false);
//					actionGroupEdit.setEnabled(false);
//					this.actionGroupRemove.setEnabled(false);
//					this.actionAddNew.setEnabled(false);
//					this.actionEdit.setEnabled(false);
//					this.actionRemove.setEnabled(false);
//				}else{
//					actionGroupAddNew.setEnabled(true);
//					actionGroupEdit.setEnabled(true);
//					this.actionGroupRemove.setEnabled(true);
//					this.actionAddNew.setEnabled(true);
//					this.actionEdit.setEnabled(true);
//					this.actionRemove.setEnabled(true);
//				}
				SellProjectInfo sellProject = (SellProjectInfo) btmNode.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sellProject
								.getId().toString()));
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", null));
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", getAllOrgIDSet(org),CompareType.INNER));	
				
				if (topNode != null) {
					if (topNode.getUserObject() instanceof RoomAssistantTypeInfo) {
						filter.setMaskString(" #0 and (#1 or #2) and #3"); // ѡ����������,
					}
				} else {
					filter.setMaskString(" (#0 or #1) and #2 "); // ѡ������,��ѯ
				}
	
			}else{
				filter.getFilterItems().add(
							new FilterItemInfo("sellProject.id", null));
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", getAllOrgIDSet(org),CompareType.INNER));	
//				filter.setMaskString(" #0 and #1 and # 2"); // ѡ������,��ѯ
				
				this.actionAddNew.setEnabled(false);
			}
		}
		mainQuery.setFilter(filter);
		mainQuery.getSorter().add(new SorterItemInfo("sellProject.name"));
		this.tblMain.removeRows();
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		
	}
	protected Set getAllOrgIDSet(FullOrgUnitInfo info) throws BOSException{
		Set id=new HashSet();
		if(info!=null&&info.getParent()!=null){
			id.add(info.getParent().getId());
			id.addAll(getAllOrgIDSet(info.getParent()));
		}
		id.add(info.getId());
		return id;
	}
	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		RoomAssistantInfo info = getSelectInfo();
		if (info.isIsEnabled()) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isEnabled");
			info.setIsEnabled(false);
			RoomAssistantFactory.getRemoteInstance().updatePartial(
					info, selector);
			this.refresh(e);
			MsgBox.showInfo("���óɹ�");
		} else {
			MsgBox.showInfo("����״̬������Ҫ��");
		}

	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		RoomAssistantInfo info = getSelectInfo();
		if (!info.isIsEnabled()) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isEnabled");
			info.setIsEnabled(true);
			RoomAssistantFactory.getRemoteInstance().updatePartial(
					info, selector);
			this.refresh(e);
			MsgBox.showInfo("���óɹ�");
		} else {
			MsgBox.showInfo("����״̬������Ҫ��");
		}
	}

	// ����ѡ�е���
	private RoomAssistantInfo getSelectInfo() throws EASBizException,
			BOSException {
		RoomAssistantInfo info = RoomAssistantFactory
				.getRemoteInstance().getRoomAssistantInfo(
						new ObjectUuidPK(getSelsectRowId()));
		return info;
	}

	// ��ȡѡ �е���ID
	public String getSelsectRowId() {
		IRow selectRow = KDTableUtil.getSelectedRow(tblMain);
		return selectRow.getCell("id").getValue().toString();
	}

	/**
	 * �������丨������
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode topNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		DefaultKingdeeTreeNode btmNode = (DefaultKingdeeTreeNode) kDTree1
				.getLastSelectedPathComponent();

		if (topNode != null) {
			Object topObject = topNode.getUserObject();
			if (topObject != null
					&& topObject instanceof RoomAssistantTypeInfo) {
				if(null!=topObject){
					if(RoomAssistantTypeEnum.Single.equals(((RoomAssistantTypeInfo)topObject).getSelType())){
						getUIContext().put("isDefault", Boolean.TRUE);
					}else{
						getUIContext().put("isDefault", Boolean.FALSE);
					}
				}
				getUIContext().put("typeInfo", topObject);
				if (btmNode != null) {
					Object btmObject = btmNode.getUserObject(); //��Ŀ������ѡ,����Ŀ�Զ�����
					if (btmObject != null
							&& btmObject instanceof SellProjectInfo) {
						getUIContext().put("sellProjectInfo", btmObject);
					} else {
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

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(this.tblMain.getSelectManager().getActiveRowIndex()<0){
			FDCMsgBox.showWarning("��ѡ����ϸ�У�");
			SysUtil.abort();
		}
		RoomAssistantInfo info = getSelectInfo();
		boolean result = info.isIsEnabled();
		if (result) {
			isEditOrDelete(OPERATION_EDIT, "�޸�!");
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(this.tblMain.getSelectManager().getActiveRowIndex()<0){
			FDCMsgBox.showWarning("��ѡ����ϸ�У�");
			SysUtil.abort();
		}
		RoomAssistantInfo info = getSelectInfo();
		boolean result = info.isIsEnabled();
		if (result) {
			isEditOrDelete(OPERATION_DELETE, "ɾ��!");
		}
		super.actionRemove_actionPerformed(e);
	}

	private void isEditOrDelete(String operation, String msg) {
		if (operation.equals(OPERATION_EDIT)) {
			FDCMsgBox.showWarning(this, "����¼�Ѿ����ã�����" + msg);
			this.abort();
		} else if (operation.equals(OPERATION_DELETE)) {
			FDCMsgBox.showWarning(this, "����¼�Ѿ����ã�����" + msg);
			this.abort();
		}
	}

	/**
	 * output actionGroupAddNew_actionPerformed
	 */
	public void actionGroupAddNew_actionPerformed(ActionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode topNode = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();

    	FilterInfo filter = new FilterInfo();

	// ���丨������
	if (topNode != null) {
		if (topNode.getUserObject() instanceof RoomAssistantTypeInfo) {
			RoomAssistantTypeInfo roomAssisType = (RoomAssistantTypeInfo) topNode.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("type.id",roomAssisType.getId().toString()));
			if(!this.getBizInterface().exists(filter)){
				super.actionGroupAddNew_actionPerformed(e);
			}else{
				FDCMsgBox.showWarning("���������и���������Ϣ���ɣ������������ͣ�");
			}
		}else{
			super.actionGroupAddNew_actionPerformed(e);
		}
	}else{
		FDCMsgBox.showWarning("��ѡ��ڵ㣡");
	}

		
	}

	protected boolean allowAddDetailNode() {
		return true;
	}

	protected String getGroupEditUIName() {
		return RoomAssistantTypeEditUI.class.getName();
	}

	protected String getQueryFieldName() {
		return "type.id";
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		return null;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return RoomAssistantTypeFactory.getRemoteInstance();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomAssistantFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return RoomAssistantEditUI.class.getName();
	}

	/**
	 * ����CU����
	 */
	protected boolean isIgnoreCUFilter() {
		return true;
	}
		
	    /**
	     * ���ɾ��
	     */
	    public void actionGroupRemove_actionPerformed(ActionEvent e) throws Exception
	    {
	        
	    	DefaultKingdeeTreeNode topNode = (DefaultKingdeeTreeNode) treeMain
			.getLastSelectedPathComponent();
	
	    	FilterInfo filter = new FilterInfo();

		// ���丨������
		if (topNode != null) {
			if (topNode.getUserObject() instanceof RoomAssistantTypeInfo) {
				RoomAssistantTypeInfo roomAssisType = (RoomAssistantTypeInfo) topNode.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("type.id",roomAssisType.getId().toString()));
			}
		}
		if(!this.getBizInterface().exists(filter)){
			 super.actionGroupRemove_actionPerformed(e);
		}else{
			FDCMsgBox.showWarning("���������и���������Ϣ���ɣ�����ɾ����");
		}
	       
	    }
//	/**
//	 * ��֯����,�����֯Ϊ�յ���ЩԤ������
//	 */
//	protected FilterInfo getDefaultFilterForTree() {
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("orgUnit.id", SysContext.getSysContext()
//						.getCurrentOrgUnit().castToFullOrgUnitInfo().getId()));
//		filter.getFilterItems().add(
//				new FilterItemInfo("orgUnit.id", null, CompareType.EQUALS));
//		filter.setMaskString(" #0 or #1 ");
//		return filter;
//	}
	/**
	 * ��֯����,�����֯Ϊ�յ���ЩԤ������
	 */
	protected FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("orgUnit.id", SysContext.getSysContext()
						.getCurrentOrgUnit().castToFullOrgUnitInfo().getId()));
		
		String longNumber = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
		String numbers[] = longNumber.split("!");// ���ϼ���������
		
		FilterInfo numFilter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		Set set = new HashSet();
		try {
		for (int i = 0; i < numbers.length; i++) {// �����ϼ��ı���
			FilterInfo parentFilter = new FilterInfo();
			parentFilter.getFilterItems().add(
					new FilterItemInfo("number",
							numbers[i]));
			parentFilter.mergeFilter(parentFilter, "OR");
			numFilter.mergeFilter(parentFilter, "OR");
		}
		view.setFilter(numFilter);
		FullOrgUnitCollection FullOrgUnitColl = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
		if(FullOrgUnitColl != null && FullOrgUnitColl.size() > 0){
			for(int i = 0;i<FullOrgUnitColl.size();i++){
				String id = FullOrgUnitColl.get(i).getId().toString();
				set.add(id);
			}
		}
		filter.getFilterItems().add(
				new FilterItemInfo("orgUnit.id",set,CompareType.INCLUDE));
		
		} catch (BOSException e) {
			e.printStackTrace();
		}
		filter.getFilterItems().add(
				new FilterItemInfo("orgUnit.id", null, CompareType.EQUALS));
		filter.setMaskString(" #0 or #1 or #2");
		return filter;
	}
	
}