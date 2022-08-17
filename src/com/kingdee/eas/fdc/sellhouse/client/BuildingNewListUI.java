/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.BanBasedataEntryFactory;
import com.kingdee.eas.fdc.basedata.BanBasedataEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListFactory;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.UpdateDataFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class BuildingNewListUI extends AbstractBuildingNewListUI {
	private static final Logger logger = CoreUIObject
	.getLogger(BuildingNewListUI.class);
	
//	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	Map orgMap = FDCSysContext.getInstance().getOrgMap();
	
	protected void initTree() throws Exception {
//		this.treeMain.setModel(FDCTreeHelper.getSubareaTree(this.actionOnLoad,null));
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,null));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	
	/**
	 * output class constructor
	 */
	public BuildingNewListUI() throws Exception {
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
							LongTimeDialog longTimeDialog = UITools.getDialog(BuildingNewListUI.this);
							longTimeDialog.setLongTimeTask(new ILongTimeTask()
							{

								public void afterExec(Object arg0)	throws Exception
								{
									UITools.showMsg(BuildingNewListUI.this, "���������ɹ�", false);
									
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
//		this.tblMain.getColumn("floorCount").getStyleAttributes()
//		.setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("floorCount").getStyleAttributes()
//		.setNumberFormat(FDCHelper.getNumberFtm(0));
//		this.tblMain.getColumn("buildingHeight").getStyleAttributes()
//		.setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("buildingHeight").getStyleAttributes()
//		.setNumberFormat(FDCHelper.getNumberFtm(2));
//		this.tblMain.getColumn("buildingTerraArea").getStyleAttributes()
//		.setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("buildingTerraArea").getStyleAttributes()
//		.setNumberFormat(FDCHelper.getNumberFtm(2));
//		this.tblMain.getColumn("unitCount").getStyleAttributes()
//		.setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("unitCount").getStyleAttributes()
//		.setNumberFormat(FDCHelper.getNumberFtm(0));
		
		String formatString = "yyyy-MM-dd";
		this.tblMain.getColumn("createTime").getStyleAttributes()
				.setNumberFormat(formatString);
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
		

//		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
//		if (!saleOrg.isIsBizUnit()) {
//			this.actionAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//		}
		//����������֯�ж�,��Ϊ��¥��֯
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		//09��10�·����ӵ�¥���,��Ҫ����������.����ʵ�ֶ�����Ӱ��ϴ�.
		//���ǵ�09��10��ǰ���ߵĿͻ��϶������˺���Ĳ���,�����Ѿ���������.����������ע�ʹ���.
//		this.updateData();
		this.btnRelateBanBaseData.setIcon(EASResource.getIcon("imgTbtn_rename"));
		
		if(SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("POMadmin")){
			KDWorkButton btnUpdate=new KDWorkButton();
			btnUpdate.setText("������������¼��");
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
		this.btnToMT.setText("ͬ������");
		this.btnToMT.setIcon(EASResource.getIcon("imgTbtn_input"));
		this.btnToYB.setIcon(EASResource.getIcon("imgTbtn_input"));
	}
	protected void btnUpdate_actionPerformed(ActionEvent e) throws Exception {
		if (FDCMsgBox.showConfirm2(this,"�Ƿ�����������") != MsgBox.OK) {
			return;
		}
		int m=0;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select fid,fbanBasedataEntryId from T_SHE_Building where FDescription_l2 is null and fbanBasedataEntryId is not null");
		IRowSet rs = sqlBuilder.executeQuery();
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("description");
		sel.add("banBasedataEntry");
		while(rs.next()){
			String id=rs.getString("fid");
			BuildingInfo info=BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(id),sel);

			BanBasedataEntryListInfo entryInfo=new BanBasedataEntryListInfo();
			entryInfo.setHead(info);
			BanBasedataEntryInfo banBasedataEntry=BanBasedataEntryFactory.getRemoteInstance().getBanBasedataEntryInfo(new ObjectUuidPK(rs.getString("fbanBasedataEntryId")));
			entryInfo.setBanBasedataEntry(banBasedataEntry);
			BanBasedataEntryListFactory.getRemoteInstance().addnew(entryInfo);
		
			info.setDescription(banBasedataEntry.getBanNumber());
			info.setBanBasedataEntry(null);
			BuildingFactory.getRemoteInstance().updatePartial(info, sel);
		}
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
//		prepareUIContext((UIContext)this.getUIWindow(),this.getActionEvent());
		this.actionAddNew.setEnabled(false);
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
		
			//����������֯�ж�,��Ϊ��¥��֯
			FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
			String idStr =null;
			if(null!= orgUnit.getId()){
				idStr = orgUnit.getId().toString();
			}
			if(null!=orgMap.get(idStr)){
				FilterInfo filterParent = new FilterInfo();
				filterParent.getFilterItems().add(new FilterItemInfo("parent.id",sellProject.getId()
						.toString()));
				if(node.getChildCount()<=0&&!SellProjectFactory.getRemoteInstance().exists(filterParent)){ //ĩ�ڵ������������
					this.actionAddNew.setEnabled(true);
				}else{
					this.actionAddNew.setEnabled(false);
				}
			}else{
				this.actionAddNew.setEnabled(false);
			}
//			if (saleOrg.isIsBizUnit()) {
//				this.actionAddNew.setEnabled(true);
//			}else{
//				this.actionAddNew.setEnabled(false);
//			}
		} else if (node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode)node.getParent();
			SellProjectInfo sellProject = (SellProjectInfo)parentNode.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("subarea.id", subarea.getId().toString()));
			
		}else {
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
		
		return BuildingNewEditUI.class.getName() ;
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
	
	/**
     * output actionCopy_actionPerformed method
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
    	int index = this.tblMain.getSelectManager().getActiveRowIndex();
    	if(index<0){
    		FDCMsgBox.showWarning("��ѡ���У�");
    		SysUtil.abort();
    	}
    	IRow  rowCopy = this.tblMain.getRow(index);
    	
    	   UIContext uiContext = new UIContext(this);
           // UIContext uiContext = new
           // UIContext(getUIContext().get(UIContext.OWNER));
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
//    	   uiContext.put("sellProject", rowCopy.getCell("id").getValue().toString());
           uiContext.put("cId", rowCopy.getCell("id").getValue().toString());
           uiContext.put("COPY", Boolean.TRUE);
           IUIWindow uiWindow  = UIFactory.createUIFactory(UIFactoryName.MODEL).create(getEditUIName(), uiContext, null,
                       OprtState.ADDNEW);

         uiWindow.show();
         this.refresh(null);
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProject = (SellProjectInfo) node
						.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sellProject
								.getId().toString(), CompareType.EQUALS));
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				String orgUnitIdStr = FDCTreeHelper
						.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node,
								"OrgStructure").keySet());
				if (orgUnitIdStr.trim().length() == 0) {
					orgUnitIdStr = "'nullnull'";
				}
				filter.getFilterItems().add(
						new FilterItemInfo("orgUnit.id", orgUnitIdStr,
								CompareType.INNER));
			}
		}
		// �ϲ���ѯ����
		viewInfo = (EntityViewInfo) mainQuery.clone();
		if(viewInfo.getFilter().getFilterItems().size()>0){
			FilterItemCollection coll = viewInfo.getFilter().getFilterItems();
			if(coll.get(0).toString().startsWith("id IS NULL")){
				coll.removeObject(0);
			}
		}
		try {
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public void actionRelateBanBaseData_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
		List id=ContractClientUtils.getSelectedIdValues(this.tblMain, getKeyFieldName());
		if(id.size()>1){
			FDCMsgBox.showWarning(this,"��ѡ��һ����¼���й�����");
			return;
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("list", ContractClientUtils.getSelectedIdValues(this.tblMain, getKeyFieldName()));
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BanBaseDataUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		
		this.refresh(null);
	}
	public void actionToMT_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("number");
		sic.add("name");
		sic.add("sellProject.number");
		sic.add("productType.number");
		sic.add("productType.name");
		JSONArray arr=new JSONArray();
		 
		for(int i = 0; i < id.size(); i++){
			BuildingInfo info=BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(id.get(i).toString()), sic);
			JSONObject json=new JSONObject();
			 json.put("type", "3");
			 json.put("id", info.getSellProject().getNumber()+info.getNumber().toString());
			 
			 JSONObject jo=new JSONObject();
			 jo.put("name", info.getName());
			 jo.put("stageId", info.getSellProject().getNumber());
			 jo.put("zcpxl", "");
			 jo.put("cplxt", info.getProductType().getName());
			 jo.put("cplx", info.getProductType().getNumber());
			 json.put("building",  jo.toJSONString());
			 
			 arr.add(json);
  		}
		try {
			String rs=SHEManageHelper.execPost(null, "jd_project_sync_channel", arr.toJSONString());
			JSONObject rso = JSONObject.parseObject(rs);
			if(!"SUCCESS".equals(rso.getString("status"))){
				MsgBox.showWarning(rso.getString("message"));
			}else{
				MsgBox.showInfo("ͬ���ɹ���");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void actionToYB_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		super.actionToYB_actionPerformed(e);
		checkSelected();
		FDCSQLBuilder builder1=new FDCSQLBuilder();
		String timestamp = null;
		ArrayList id = getSelectedIdValues();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("number");
		sic.add("name");
		sic.add("sellProject.*");
		sic.add("productType.number");
		sic.add("productType.name");
		
		 JSONObject initjson=new JSONObject();
		 JSONObject esbjson=new JSONObject();
		 String instId=null;
		 String requestTime=null;
		 JSONObject datajson=new JSONObject();
		 JSONArray ybarr=new JSONArray();
		 String instsId=null;
		 String requestsTime=null;
		 
		 builder1.clear();
		 builder1.appendSql(" select instId,requestTime from esbInfo where source='building'");
		 IRowSet rs3=builder1.executeQuery();
		 while(rs3.next()){
			  instId=rs3.getString("instId");
			  requestTime=rs3.getString("requestTime");
		 }
		 if(instsId!=null){
			 esbjson.put("instId",instsId);
		 }
		 if(requestsTime!=null){
			 esbjson.put("requestTime",requestsTime);
		 }
		 
//			�ϴη�����ʱ���
		 builder1.clear();
			builder1.appendSql(" select ybtime from ybTimeRecord where source='building'");
			IRowSet rs1=builder1.executeQuery();
			try {
				if(rs1.first()&&rs1!=null){
				 timestamp=rs1.getString("ybtime");
				}else{
					timestamp="";
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		for(int i = 0; i < id.size(); i++){
			BuildingInfo info=BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(id.get(i).toString()), sic);
			 JSONObject ybjson=new JSONObject();
			 ybjson.put("projectId", info.getSellProject().getParent().getId().toString());
			 ybjson.put("projectCode", info.getSellProject().getParent().getNumber());
			 ybjson.put("projectName", info.getSellProject().getParent().getName());
			 ybjson.put("stageId", info.getSellProject().getId().toString());
			 ybjson.put("stageCode", info.getSellProject().getNumber());
			 ybjson.put("stageName", info.getSellProject().getName());
			 ybjson.put("buildingId", info.getId().toString());
			 ybjson.put("buildingName", info.getName());
			 ybjson.put("productType", info.getProductType().getName());
			 ybjson.put("status", "1");
			 
			 ybarr.add(ybjson);
  		}
		datajson.put("datas",ybarr);
		datajson.put("timestamp",timestamp);
		initjson.put("esbInfo", esbjson);
		initjson.put("requestInfo",datajson);
		
		try {
			System.out.println(initjson.toJSONString());
			String rs11=SHEManageHelper.execPostYBBuilding(null, initjson.toJSONString(),timestamp);
			JSONObject rso = JSONObject.parseObject(rs11);
			if(!"A0200".equals(rso.getJSONObject("esbInfo").getString("returnCode"))){
				String errorMsg =rso.getJSONObject("esbInfo").getString("returnMsg");
				String info="����һ��������Ϣ��";
				String sb = String.valueOf(new StringBuffer().append(errorMsg).append(info));
				throw new EASBizException(new NumericExceptionSubItem("100",sb));
			}else{
				JSONObject rst=rso.getJSONObject("esbInfo");
				 instId=rst.getString("instId");
				 requestTime=rst.getString("requestTime");
				 builder1.clear();
				 builder1.appendSql(" update esbInfo set instId='"+instId+"',requestTime='"+requestTime+"' where source='building'");
				 builder1.executeUpdate();
				 JSONObject rst1=rso.getJSONObject("resultInfo");
				 String ts=rst1.getString("timestamp");
				 builder1.clear();
				 builder1.appendSql("update ybTimeRecord set ybTime='"+ts+"' where source='building' ");
				 builder1.executeUpdate();
				MsgBox.showInfo("ͬ���ɹ�");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	
}