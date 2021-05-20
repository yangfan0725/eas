/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.market.MediaFactory;
import com.kingdee.eas.fdc.market.MediaInfo;
import com.kingdee.eas.fdc.market.MediaTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;

import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MediaListUI extends AbstractMediaListUI {
	private static final Logger logger = CoreUIObject.getLogger(MediaListUI.class);
	private SellProjectInfo sellProject = null;
	private MediaTypeInfo mediaType = null;
	private String allProjectIds = null;
	//by tim_gao 2010-11-12 ��֯�²�ѯ����Ӫ���ڵ�
	private Set muSetIds = null;
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public MediaListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {

		if (sellProject == null) {
			com.kingdee.eas.util.client.MsgBox.showInfo(this, "��ѡ�������Ŀ��");
			com.kingdee.eas.util.SysUtil.abort();
		}
		if (mediaType == null) {
			com.kingdee.eas.util.client.MsgBox.showInfo(this, "��ѡ�����ý����࣡");
			com.kingdee.eas.util.SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionView_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String id = this.getSelectedKeyValue();
		FilterInfo filter  =  new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",id));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled","1"));
		if(MediaFactory.getRemoteInstance().exists(filter)){
			MsgBox.showWarning("�Ѿ����õ�ý��������ϲ����Խ���ɾ����");
			SysUtil.abort();
		}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	/**
	 * output actionImportData_actionPerformed
	 */
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportData_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionExportData_actionPerformed
	 */
	public void actionExportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportData_actionPerformed(e);
	}

	/**
	 * output actionToExcel_actionPerformed
	 */
	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		super.actionToExcel_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionPublishReport_actionPerformed
	 */
	public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception {
		super.actionPublishReport_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = this.getSelectedKeyValue();
		MediaInfo mediaInfo = MediaFactory.getRemoteInstance().getMediaInfo(new ObjectUuidPK(BOSUuid.read(id)));
		if (!mediaInfo.isIsEnabled()) {
			MsgBox.showInfo("�Ѿ����ã�");			
			return;
		}
		String cancelMsg = EASResource.getString(FrameWorkClientUtils.strResource + "Confirm_Cancel");
		if (confirmDialog(cancelMsg)) {
			
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("isEnabled");
				mediaInfo.setIsEnabled(false);
				MediaFactory.getRemoteInstance().updatePartial(mediaInfo, sel);
				this.actionRefresh_actionPerformed(e);
		}
		cancel();
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = this.getSelectedKeyValue();
		MediaInfo mediaInfo = MediaFactory.getRemoteInstance().getMediaInfo(new ObjectUuidPK(BOSUuid.read(id)));
		if (mediaInfo.isIsEnabled()) {
			MsgBox.showInfo("�Ѿ����ã�");
			return;
		}
		String cancelMsg = EASResource.getString(FrameWorkClientUtils.strResource + "Confirm_CancelCancel");
		if (confirmDialog(cancelMsg)) {
			
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("isEnabled");
				mediaInfo.setIsEnabled(true);
				MediaFactory.getRemoteInstance().updatePartial(mediaInfo, sel);
				this.actionRefresh_actionPerformed(e);

		}
		cancelCancel();
	}

	/**
	 * output actionQueryScheme_actionPerformed
	 */
	public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception {
		super.actionQueryScheme_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.MediaFactory.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.MediaInfo objectValue = new com.kingdee.eas.fdc.market.MediaInfo();

		return objectValue;
	}

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new MediaInfo();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		menuWorkFlow.setVisible(false);
		menuItemVoucher.setVisible(false);
		menuItemCreateTo.setVisible(false);
		MenuItemAttachment.setVisible(false);
		menuItemCopyTo.setVisible(false);
		menuTool.setVisible(false);
		menuBiz.setVisible(false);
		btnCreateTo.setVisible(false);
		btnTraceUp.setVisible(false);
		btnTraceDown.setVisible(false);
		btnAuditResult.setVisible(false);
		btnWorkFlowG.setVisible(false);
		btnCancelCancel.setVisible(true);
		btnCancel.setVisible(true);
		MediaTypeHelper.getMediaTypeTree(mediaTypeTree);
		// ��ʼ��������Ŀ��
		this.sellProjectTree.setModel(FDCTreeHelper.getSellProjectTree(actionOnLoad, null));
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		}
		if (sellProjectTree.getRowCount() > 0) {
			sellProjectTree.setSelectionRow(0);
		}
		  this.tblMain.addKDTSelectListener(new ProcessDefSelectListener());
		 
	}
	
	/**
	 * 
	 * ������Ӫ����Ŀ���ڵ�仯�¼������Ұ�ѡ�е�Ӫ����Ŀ����UI���ǵ�������ҳ���Զ���ֵ
	 * 
	 * @author:ZhangFeng
	 * @see com.kingdee.eas.fdc.market.client.AbstractMarketManageListUI#sellProjectTree_valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	protected void sellProjectTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		//by tim_gao ��֯�ڵ�ÿ��Ҫ��ղ�Ȼ��Ӱ���ж�
		muSetIds=new HashSet();
		this.actionAddNew.setEnabled(true);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) sellProjectTree.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() != null && node.getUserObject() instanceof SellProjectInfo) {
			getAllProjectIds(node);
			SellProjectInfo sellProjectInfo = (SellProjectInfo) node.getUserObject();
			if (sellProjectInfo != null) {
				sellProject = sellProjectInfo;
				getUIContext().put("project", sellProjectInfo);
			}
		}
		//by tim_gao 2010-11-12������֯�ڵ���ж�
		if(node.getUserObject()!=null&&node.getUserObject()instanceof OrgStructureInfo)
		{	
			
			getAllSellProjectIds(node,muSetIds);
			OrgStructureInfo orgStructureInfo = (OrgStructureInfo) node.getUserObject();
			this.actionAddNew.setEnabled(false);
		}
		refreshTableData();
	}

	protected void mediaTypeTree_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		//by tim_gao ��֯�ڵ�ÿ��Ҫ��ղ�Ȼ��Ӱ���ж�
//		muSetIds=new HashSet();
		this.actionAddNew.setEnabled(true);
		DefaultKingdeeTreeNode mediaTypeNode = (DefaultKingdeeTreeNode) mediaTypeTree.getLastSelectedPathComponent();
		if (mediaTypeNode == null) {
			return;
		}
		mediaType=null;//ÿ��ִ�ж���Ϊ�� �����ڵ�����ý������ �ж�
		if (mediaTypeNode.getUserObject() != null && mediaTypeNode.getUserObject() instanceof MediaTypeInfo) {
			MediaTypeInfo mediaTypeInfo = (MediaTypeInfo) mediaTypeNode.getUserObject();
			if (mediaTypeInfo != null) {
				mediaType = mediaTypeInfo;
				getUIContext().put("mediaType", mediaTypeInfo);
			}
		}
		//��֯�ڵ㲻���������Ϣ����ѡ��Ϊ��֯�ڵ�ʱΪnull
		if (mediaType==null)
		{
			this.actionAddNew.setEnabled(false);
		}
		refreshTableData();
	}
	/**
	 * ��ѯ��ѡ�ڵ������е�Ӫ����Ŀ��ids
	 * @author tim_gao
	 * @date 2010-11-12
	 * @param treeNode
	 */
	private void getAllSellProjectIds(TreeNode treeNode, Set muIds) {
		if (treeNode != null) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
			if (thisNode.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo muInfo = (SellProjectInfo) thisNode
						.getUserObject();
				muIds.add(muInfo.getId().toString());
			}

			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllSellProjectIds(treeNode.getChildAt(childCount - 1), muIds);
				childCount--;
			}
		}
	}
	/**
	 * ��ѯ���е�������Ŀid
	 *
	 * @param treeNode
	 */
	private void getAllProjectIds(TreeNode treeNode) {
		if (treeNode.isLeaf()) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
			if (thisNode.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo project = (SellProjectInfo) thisNode.getUserObject();
				allProjectIds += "," + project.getId().toString();
			}
		} else {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllProjectIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	protected void refreshTableData() throws Exception {
		this.mainQuery = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(muSetIds!=null&&muSetIds.size()>0){
			if(mediaType != null){
				filter.getFilterItems().add(new FilterItemInfo("mediaType.longNumber", mediaType.getLongNumber() + "%", CompareType.LIKE));
			}
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", muSetIds,CompareType.INCLUDE));
		}
		else{
			if (sellProject != null) {
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
			}
			if (mediaType != null) {
				filter.getFilterItems().add(new FilterItemInfo("mediaType.longNumber", mediaType.getLongNumber() + "%", CompareType.LIKE));
			}
		}
	
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
	}
	//������֯����
	protected boolean isIgnoreCUFilter() {
		return false;
	}
	/**
	 * ����ѡ�����¼�
	 * 
	 * @author pu_zhang
	 *
	 */
	class ProcessDefSelectListener implements KDTSelectListener {
        public void tableSelectChanged(KDTSelectEvent e) {
            if (e == null) {
                return;
            }

            KDTSelectBlock selectBlock = e.getSelectBlock();
            if (selectBlock == null) {
                return;
            }

            int beginRow = selectBlock.getBeginRow();
            if (beginRow < 0) {
                return;
            }

            synchronizeButtonState(beginRow);
        }
    }  	
	private void synchronizeButtonState(int beginRow){
		  boolean enableValue = this.getEnable(beginRow);
	      this.setProcessEnableButtonEnable(enableValue);
	}
	private boolean getEnable(int rowIndex) {
        IRow currentRow = this.tblMain.getRow(rowIndex);

        if (currentRow != null) {
            ICell destCell = currentRow.getCell("isEnabled");

            if (destCell != null) {
                Object destValue = destCell.getValue();

                if (destValue != null) {
                    if (destValue instanceof Boolean) {
                        Boolean enableValue = (Boolean) destValue;
                        return enableValue.booleanValue();
                    }
                }
            }
        }

        return false;
    }
	 private void setProcessEnableButtonEnable(boolean enable) {
	        this.btnCancel.setEnabled(enable);
	        this.btnCancelCancel.setEnabled(!enable);
	        this.btnEdit.setEnabled(!enable);
	 }
}