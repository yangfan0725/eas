/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.eas.base.commonquery.QueryPanelCollection;
import com.kingdee.eas.base.commonquery.QueryPanelInfo;
import com.kingdee.eas.base.commonquery.QuerySolutionFacadeFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.XMLBean;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.UpdateDataFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.app.UpdateDataFacadeControllerBean;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class BuildingListUI extends AbstractBuildingListUI {
	private static final Logger logger = CoreUIObject
	.getLogger(BuildingListUI.class);
	
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	
	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSubareaTree(this.actionOnLoad,null));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	
	/**
	 * output class constructor
	 */
	public BuildingListUI() throws Exception {
		super();
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
	throws Exception {
		super.tblMain_tableClicked(e);
	}
	
	/**
	 * ��� 2009.10.06 ���� ¥����  ¥���¼֮�� ���Ƿ� ���й� ���������������˵Ļ����Ͳ�������
	 */
	private void updateData()
	{
		try
		{
			boolean debug = UpdateDataFacadeFactory.getRemoteInstance().isNeedUpdateBuildingFloorForRoom();
			if(debug)
			{
				int i = MsgBox.showConfirm2("��¥��ʵ���� 2009��10��6�գ��������� ¥����¼������������������������ѽ��й����������ִ˶Ի�������ϵ�����Ա����" +
						"��Ҫ 20 �������ҵ�ʱ�䣬�����ĵȴ����Ƿ��������������");
				if(i == 0)
				{
				
					SwingUtilities.invokeLater(new Runnable()
					{

						public void run()
						{
							LongTimeDialog longTimeDialog = UITools.getDialog(BuildingListUI.this);
							longTimeDialog.setLongTimeTask(new ILongTimeTask()
							{

								public void afterExec(Object arg0)	throws Exception
								{
									UITools.showMsg(BuildingListUI.this, "���������ɹ�", false);
									
								}

								public Object exec() throws Exception
								{
									UpdateDataFacadeFactory.getRemoteInstance().updateBuildingFloorForRoom();
									return "";
								}
								
							});
							longTimeDialog.show();
							
						}
						
					});
				
			     }
				else
				{
					this.abort();
				}
			}
			
			
		} catch (EASBizException e)
		{
			e.printStackTrace();
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
	}
	
	public void onLoad() throws Exception {
		
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		this.actionQuery.setVisible(false);
		this.tblMain.getColumn("floorCount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("floorCount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(0));
		this.tblMain.getColumn("buildingHeight").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildingHeight").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("buildingTerraArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildingTerraArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("unitCount").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("unitCount").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(0));
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		
		//09��10�·����ӵ�¥���,��Ҫ����������.����ʵ�ֶ�����Ӱ��ϴ�.
		//���ǵ�09��10��ǰ���ߵĿͻ��϶������˺���Ĳ���,�����Ѿ���������.����������ע�ʹ���.
//		this.updateData();
	}
	
	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
	throws Exception {
	}
	
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", sellProject.getId()
							.toString()));
			// filter.getFilterItems().add(new FilterItemInfo("subarea.id",
			// null));
			if (saleOrg.isIsBizUnit()) {
				this.actionAddNew.setEnabled(true);
			}
		} else if (node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode)node.getParent();
			SellProjectInfo sellProject = (SellProjectInfo)parentNode.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("subarea.id", subarea.getId().toString()));
			if (saleOrg.isIsBizUnit()) {
				this.actionAddNew.setEnabled(true);
			}
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
			this.actionAddNew.setEnabled(false);
		}
		
		
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
	}
	
	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	
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
			uiContext.put("subarea", null);
		} else if (node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			
			SellProjectInfo sellProject = null;
			
			if(subarea.getSellProject()!=null && subarea.getSellProject().getId()!=null)
				try
			{
					sellProject = (SellProjectInfo) SellProjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(subarea.getSellProject().getId().toString())));
			}catch (Exception e1)
			{
				e1.printStackTrace();
			}
			uiContext.put("sellProject", sellProject);
			uiContext.put("subarea", subarea);
		}
		super.prepareUIContext(uiContext, e);
	}
	
	protected String getEditUIName() {
		return BuildingEditUI.class.getName();
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return BuildingFactory.getRemoteInstance();
	}
	
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building.id", id));
			if (RoomDesFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ������䶨��ʹ��,����ɾ��!");
				return;
			}
			if (RoomFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("�Ѿ�������ʹ��,����ɾ��!");
				return;
			}
		}
		checkSelected();
		if (confirmRemove()) {
//			prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
			.getUserObject();
			CacheServiceFactory.getInstance().discardType(sellProject.getBOSType());
		}
		if(node.getUserObject() instanceof SubareaInfo)
		{
		  try
			{
			  SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			  SellProjectInfo sellProject = null;
			if(subarea.getSellProject()!=null && subarea.getSellProject().getId()!=null)
			{
					sellProject = (SellProjectInfo) SellProjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(subarea.getSellProject().getId().toString())));
			        CacheServiceFactory.getInstance().discardType(sellProject.getBOSType());
			}
			}catch (Exception e1)
			{
				this.handleException(e1);
			}
		}
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
}