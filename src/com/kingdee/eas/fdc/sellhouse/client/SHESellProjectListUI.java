/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.MarketUnitSellProFactory;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractBillEditAuditDateUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class SHESellProjectListUI extends AbstractSHESellProjectListUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3065120200033884140L;
	private static final Logger logger = CoreUIObject
			.getLogger(SHESellProjectListUI.class);

	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	private FullOrgUnitInfo org = SysContext.getSysContext()
			.getCurrentOrgUnit().castToFullOrgUnitInfo();

	/**
	 * output class constructor
	 */
	public SHESellProjectListUI() throws Exception {
		super();
	}

	protected void initTree() throws Exception {
	
		DefaultKingdeeTreeNode oldSelected = null;
		Object objTree = this.treeMain.getLastSelectedPathComponent();
		if (objTree != null) {
			oldSelected = (DefaultKingdeeTreeNode) objTree;
		}
		this.treeMain.setModel(FDCTreeHelper.getSellProjectForSHESellProject(
				actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		if (oldSelected != null)
			this.treeMain.setSelectionNode(oldSelected);
		else{
			this.treeMain
			.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain
					.getModel().getRoot());

		}
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.execQuery();
		initTree();
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
	
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo project = (SellProjectInfo)node.getUserObject();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",project.getId().toString(),CompareType.EQUALS));
			if(BuildingFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this,"该项目下已经建立楼栋，不能新增!");
				this.abort();
			}
			
			
			IRow row = KDTableUtil.getSelectedRow(this.tblMain);
			
			if(row==null){
				row = this.tblMain.getRow(0);
			}else{
				FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
				if(row.getCell("orgUnit.id").getValue()!=null){
					String orgUnitId = row.getCell("orgUnit.id").getValue().toString();
					if(!orgUnitId.equals(orgUnit.getId().toString())){
						FDCMsgBox.showWarning(this,"共享项目不能建立下级!");
						this.abort();
					}
				}
			}
		}
		super.actionAddNew_actionPerformed(e);
		//this.initTree();
		this.treeMain
		.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain
				.getModel().getRoot());
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
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			if (isSellOrg()) {
				FDCClientHelper.setActionEnableAndNotSetVisible(
						new ItemAction[] { actionAddNew, actionEdit,
								actionRemove, this.actionImport }, true);

				TreePath path = this.treeMain.getSelectionPath();
				if (path == null) {
					return;
				}
				DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path
						.getLastPathComponent();
				if (treenode.getUserObject() != null
						&& treenode.getUserObject() instanceof SellProjectInfo) {
					SellProjectInfo sheProjectInfo = (SellProjectInfo) treenode
							.getUserObject();
					if (sheProjectInfo != null) {
						getUIContext().put("sheProject", sheProjectInfo);
					}
				}
			} else {
				FDCClientHelper.setActionEnableAndNotSetVisible(
						new ItemAction[] { actionAddNew, actionEdit,
								actionRemove, this.actionImport }, false);
				this.actionImport.setEnabled(false);
				this.actionCancelCancel.setEnabled(false);
				this.actionCancel.setEnabled(false);
			}
		} else {
			getUIContext().put("sheProject", null);
		}

		this.execQuery();
		
		KDTableUtil.setSelectedRow(this.tblMain, 0);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return SellProjectFactory.getRemoteInstance();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SellProjectFactory.getRemoteInstance();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected String getEditUIName() {
		return SHESellProjectEditUI.class.getName();
	}

	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		SellProjectInfo objectValue = new SellProjectInfo();
		return objectValue;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				String allSpIdStr = FDCTreeHelper
						.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node,
								"SellProject").keySet());
				if (allSpIdStr.trim().length() == 0) {
					allSpIdStr = "'nullnull'";
				}

				filter.getFilterItems()
						.add(
								new FilterItemInfo("id", allSpIdStr,
										CompareType.INNER));
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
		// 合并查询条件
		viewInfo = (EntityViewInfo) mainQuery.clone();
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

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		String orgUnitIdStr = "";
		String orgUnitIdStrRoot = "";
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				orgUnitIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper
						.getAllObjectIdMap(node, "SellProject").keySet());
				orgUnitIdStrRoot = FDCTreeHelper
						.getStringFromSet(FDCTreeHelper
								.getAllObjectIdMapForRoot(node, "SellProject")
								.keySet());
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				orgUnitIdStr = "";
				orgUnitIdStrRoot = "";

			}
		}
		if (!"".equals(orgUnitIdStrRoot) && orgUnitIdStrRoot.length() > 0) {
			orgUnitIdStr = orgUnitIdStr + "," + orgUnitIdStrRoot;
		}
		if (!"".equals(orgUnitIdStr) && orgUnitIdStr.length() > 0) {
			uiContext.put("IDSTR", orgUnitIdStr);
		} else {
			uiContext.put("IDSTR", null);
		}

	}

	protected String[] getLocateNames() {

		String[] locateNames = new String[4];
		locateNames[0] = "number";
		locateNames[1] = "name";
		locateNames[2] = "project";
		locateNames[3] = "companyOrgUnit.name";
		return locateNames;
	}
	
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		IMetaDataLoader loader = MetaDataLoaderFactory
				.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK(
				"com.kingdee.eas.fdc.sellhouse.app.SellProjectForSHEQuery")));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isForSHE", null, CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("isForSHE", new Integer("0"), CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("level", new Integer("1"),
						CompareType.EQUALS));
		filter.setMaskString("(#0 or #1) and #2");
		
		view.setFilter(filter);
		dlg.setEntityViewInfo(view);
		// 设置多选
		dlg.setEnabledMultiSelection(false);
		dlg.show();
		Object[] object = (Object[]) dlg.getData();
		if (object != null && object.length > 0) {
			SellProjectInfo info = (SellProjectInfo) object[0];
			if (info != null && info.getId() != null) {

				FilterInfo newFilter = new FilterInfo();
				newFilter.getFilterItems().add(
						new FilterItemInfo("importID", info.getId().toString(),
								CompareType.EQUALS));

				boolean result = SellProjectFactory.getRemoteInstance().exists(
						newFilter);

				if (!result) {
					SellProjectFactory.getRemoteInstance().updateToSHEProject(
							info.getId(), this.org.getId(),
							info.getLongNumber());
					FDCMsgBox.showWarning(this, "引入成功!");
				} else {
					logger.error("该项目已经存在，不能重复被引入!"+info.getId().toString());
					FDCMsgBox.showWarning(this, "该项目已经存在，不能重复被引入!");
					this.abort();
				}

			}
		}
		this.initTree();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionImport.setEnabled(true);
		this.btnImport.setIcon(EASResource.getIcon("imgTbtn_input"));
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);

		if (isSellOrg()) {
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
		} else {
			this.actionCancelCancel.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionImport.setEnabled(false);
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		this.btnEditEndDate.setIcon(EASResource.getIcon("imgTbtn_rename"));
		
		this.btnToMT.setIcon(EASResource.getIcon("imgTbtn_input"));
	}
	
	private boolean isSellOrg() {
		boolean res = false;
		//modify by youzhen,项目建立用实体销售组织判断，不用管是否售楼组织（方波2011年08月29日确认）
		//BT583560
		if(saleOrg.isIsBizUnit()){
			res = true;
		}
//		try {
//			FullOrgUnitInfo fullOrginfo = SysContext.getSysContext()
//					.getCurrentOrgUnit().castToFullOrgUnitInfo();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("sellHouseStrut", Boolean.TRUE,
//							CompareType.EQUALS));
//			if (fullOrginfo != null) {
//				filter.getFilterItems().add(
//						new FilterItemInfo("unit.id", fullOrginfo.getId()
//								.toString(), CompareType.EQUALS));
//			}
//			filter.getFilterItems().add(
//					new FilterItemInfo("tree.id", OrgConstants.SALE_VIEW_ID,
//							CompareType.EQUALS));
//			res = FDCOrgStructureFactory.getRemoteInstance().exists(filter);
//		} catch (Exception ex) {
//			logger.error(ex.getMessage() + "获得售楼组织失败!");
//		}

		return res;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		IRow row = KDTableUtil.getSelectedRow(this.tblMain);
		if(row==null){
			return;
		}else{
			FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
			if(row.getCell("orgUnit.id").getValue()!=null){
				String orgUnitId = row.getCell("orgUnit.id").getValue().toString();
				if(!orgUnitId.equals(orgUnit.getId().toString())){
					FDCMsgBox.showWarning(this,"共享项目不能修改!");
					this.abort();
				}
			}
		}
		super.actionEdit_actionPerformed(e);
		this.initTree();
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {

		super.actionView_actionPerformed(e);
		this.initTree();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		
		IRow row = KDTableUtil.getSelectedRow(this.tblMain);
		if(row==null){
			return;
		}else{
			FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
			if(row.getCell("orgUnit.id").getValue()!=null){
				String orgUnitId = row.getCell("orgUnit.id").getValue().toString();
				if(!orgUnitId.equals(orgUnit.getId().toString())){
					FDCMsgBox.showWarning(this,"共享项目不能删除!");
					this.abort();
				}
			}
		}
		
		String id = this.getSelectedKeyValue();
		
		
		
		isExistProjectInBuilding();
	
		checkRemoveForMarkinget(id);
		
		checkSelected();
		
		if (confirmRemove()) {
			
			FilterInfo newFilter = new FilterInfo();
			newFilter.getFilterItems().add(
					new FilterItemInfo("id",id,
							CompareType.EQUALS));

			SellProjectInfo project= SellProjectFactory.getRemoteInstance().getSellProjectInfo("select id,importID where id='"+id+"'");
			if(project!=null && project.getImportID()!=null){
				boolean isUse = false;
				
				isUse = isUseForOtherSystem(id);
				//判断项目资料使用是否被其他系统使用
				if(isUse){
					SellProjectFactory.getRemoteInstance().updateDeleteStatus(BOSUuid.read(id));
				}else{
					//没有被使用,则删除
					SellProjectFactory.getRemoteInstance().deleteProjectInSystem(BOSUuid.read(id));
				}
			}else{
				//是自己建立的项目,则直接删除
				SellProjectFactory.getRemoteInstance().deleteSellProject(BOSUuid.read(id));
			}
		}
		initTree();
		this.treeMain
		.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain
				.getModel().getRoot());
	}
	
	private boolean isUseForOtherSystem(String id){
		boolean result =false;
		
		
		
		return result;
	}
	
	private void isExistProjectInBuilding() throws BOSException,
			EASBizException {
		String longNumber = "";
		
		IRow row = KDTableUtil.getSelectedRow(this.tblMain);
		if(row==null){
			return;
		}
		
		if(row.getCell("longNumber").getValue()!=null){
			longNumber= row.getCell("longNumber").getValue().toString();
		}
		if (longNumber != null && !"".equals(longNumber)) {

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();

			filter.getFilterItems().add(
					new FilterItemInfo("longNumber", longNumber, CompareType.EQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("longNumber", longNumber+ "!%", CompareType.LIKE));

			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add(new SelectorItemInfo("id"));
			coll.add(new SelectorItemInfo("name"));
			filter.setMaskString("#0 or #1");
			view.setFilter(filter);
			view.setSelector(coll);
			SellProjectCollection projectColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection(
					view);

			StringBuffer idStr = new StringBuffer();
			for (int i = 0; i < projectColl.size(); i++) {
				 SellProjectInfo info= projectColl.get(i);
				 if(info!=null){
					 idStr.append("'");
					 idStr.append(info.getId().toString());
					 idStr.append("'");
					 if(i!=projectColl.size()-1){
						 idStr.append(",");
					 }
				 }
			}
			
			
			FilterInfo newFilter = new FilterInfo();
			if(idStr.length()==0){
				newFilter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", null,CompareType.INNER));
				
			}else{
				newFilter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", idStr.toString(),CompareType.INNER));
			}
			if (BuildingFactory.getRemoteInstance().exists(newFilter)) {
				FDCMsgBox.showInfo("已经建立楼栋信息,不能删除!");
				this.abort();
			}
		} else {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			this.abort();
		}
		
	}
	
	private void checkRemoveForMarkinget(String id) throws EASBizException, BOSException {
		
		//SellProjectInfo info = getRootSellProject();
		//if(info==null){
			//FDCMsgBox.showWarning(this,"请选择项目!");
			//this.abort();
		//}
		if(id==null || "".equals(id)){
			return;
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", id,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", SysContext.getSysContext().getCurrentSaleUnit().getId(),CompareType.EQUALS));
		if (MarketUnitSellProFactory.getRemoteInstance().exists(filter)) {
			FDCMsgBox.showInfo("该项目已经被营销单元引用,不能删除!");
			this.abort();
		}
		
	}
	
	private SellProjectInfo getRootSellProject() {

		SellProjectInfo sellInfo = null;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {

			DefaultKingdeeTreeNode nodeTemp = getParentNode(node);
			if (nodeTemp != null) {
				sellInfo = (SellProjectInfo) nodeTemp.getUserObject();
			}
		}

		return sellInfo;
	}

	private DefaultKingdeeTreeNode getParentNode(DefaultKingdeeTreeNode node) {
		if (node == null) {
			return null;
		}

		if (node.getParent() != null) {

			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) node
					.getParent();
			if (treeNode.getUserObject() instanceof SellProjectInfo) {
				return getParentNode(treeNode);
			} else {
				return node;
			}

		}

		return null;
	}

	private int getExistChild() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		int number = 0;
		if (node != null) {
			number = node.getChildCount();
		}

		return number;
	}

	protected String getRootName() {
		String name = saleOrg.getName().toString();
		return name;
	}
	public void actionEditEndDate_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
		List id=ContractClientUtils.getSelectedIdValues(this.tblMain, getKeyFieldName());
		if(id.size()>1){
			FDCMsgBox.showWarning(this,"请选择一条记录！");
			return;
		}
		SelectorItemCollection selCol=new SelectorItemCollection();
		selCol.add("level");
		for(int i=0;i<id.size();i++){
			SellProjectInfo info=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(id.get(i).toString()),selCol);
			if(info.getLevel()>1){
				FDCMsgBox.showWarning(this,"请选择根项目！");
				return;
			}
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("list", ContractClientUtils.getSelectedIdValues(this.tblMain, getKeyFieldName()));
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SellProjectEditEndDateUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		
		this.refresh(null);
	}
	public void actionToMT_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("number");
		sic.add("name");
		sic.add("parent.number");
		sic.add("orgUnit.number");
		sic.add("orgUnit.name");
		JSONArray arr=new JSONArray();
		 
		for(int i = 0; i < id.size(); i++){
			SellProjectInfo project=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(id.get(i).toString()), sic);
			if(project.getParent()!=null){
				 JSONObject json=new JSONObject();
				 json.put("type", "2");
				 json.put("id", project.getNumber().toString());
				 
				 JSONObject jo=new JSONObject();
				 jo.put("name", project.getName());
				 jo.put("ProjId", project.getParent().getNumber());
				 
				 json.put("stage", jo.toJSONString());
				 
				 arr.add(json);
			}else{
				 JSONObject json=new JSONObject();
				 json.put("type", "1");
				 json.put("id", project.getNumber().toString());
				 
				 JSONObject jo=new JSONObject();
				 jo.put("name", project.getName());
				 jo.put("cityId", project.getOrgUnit().getNumber());
				 jo.put("citycode", project.getOrgUnit().getNumber());
				 jo.put("cityName", project.getOrgUnit().getName());
				 
				 json.put("project", jo.toJSONString());
				 
				 arr.add(json);
			}
  		}
		try {
			String rs=SHEManageHelper.execPost(null, "jd_project_sync_channel", arr.toJSONString());
			JSONObject rso = JSONObject.parseObject(rs);
			if(!"SUCCESS".equals(rso.getString("status"))){
				MsgBox.showWarning(rso.getString("message"));
			}else{
				MsgBox.showInfo("同步成功！");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	//同步项目到YB
	@Override
	public void actionToYB_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionToYB_actionPerformed(e);
		FDCSQLBuilder builder=new FDCSQLBuilder();
		String timestamp = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		String sendTime = sdf.format(date).toString();
		String sendData = null;
		String connectId=null;
		String connectName=null;
		checkSelected();
		ArrayList id = getSelectedIdValues();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		sic.add("creator.*");
		sic.add("createTime");
		sic.add("lastUpdateUser");
		sic.add("lastUpdateTime");
		sic.add("parent.number");
		sic.add("parent.name");
		sic.add("orgUnit.number");
		sic.add("orgUnit.name");
		
//		project & stage 
		JSONObject initjson=new JSONObject();
		JSONObject initsjson=new JSONObject();
		
	
		
//		 to_yb
		
//		esbInfo  project
		 JSONObject esbjson=new JSONObject();
		
		 String instId=null;
		 String requestTime=null;
		 
		 builder.appendSql(" select instId,requestTime from esbInfo where source='project'");
		 IRowSet rs2=builder.executeQuery();
		 while(rs2.next()){
			  instId=rs2.getString("instId");
			  requestTime=rs2.getString("requestTime");
		 }
		 if(instId!=null){
			 esbjson.put("instId",instId);
		 }
		 if(requestTime!=null){
			 esbjson.put("requestTime",requestTime);
		 }
		
//			esbInfo  stage
		 JSONObject esbsjson=new JSONObject();
		 
		 String instsId=null;
		 String requestsTime=null;
		 
		 builder.clear();
		 builder.appendSql(" select instId,requestTime from esbInfo where source='stage'");
		 IRowSet rs3=builder.executeQuery();
		 while(rs3.next()){
			  instsId=rs3.getString("instId");
			  requestsTime=rs3.getString("requestTime");
		 }
		 if(instsId!=null){
			 esbsjson.put("instId",instsId);
		 }
		 if(requestsTime!=null){
			 esbsjson.put("requestTime",requestsTime);
		 }
		

//		 requestInfo
		 JSONObject datajson=new JSONObject();
		 JSONArray ybarr=new JSONArray();
//		stage
		 
		 JSONObject datasjson=new JSONObject();
		 JSONArray ybsarr=new JSONArray();
			
		for(int i = 0; i < id.size(); i++){
			SellProjectInfo project=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(id.get(i).toString()), sic);
//			 project		 
			 JSONObject ybjson=new JSONObject();
//			 stage
			 JSONObject ybsjson=new JSONObject();
			if(project.getParent()!=null){
				 ybsjson.put("projectID", project.getParent().getId().toString());
				 connectId=project.getParent().getId().toString();
				 ybsjson.put("projectFID", project.getId().toString());
				 ybsjson.put("stageName", project.getName());
				 connectName=project.getName();
				 ybsjson.put("stageCode", project.getNumber());
				 if(project.getCreateTime()!=null){
					 String createTime=sdf.format(project.getCreateTime());
					 ybsjson.put("createdTime", createTime);
				 }else{
					 ybsjson.put("createdTime", "");
				 }
				 if(project.getLastUpdateTime()!=null){
					 String updateTime=sdf.format(project.getLastUpdateTime());
					 ybsjson.put("updatedTime",updateTime );
				 }else{
					 ybsjson.put("updatedTime","");
				 }
				 ybsjson.put("status", "1");
				 builder.clear();
					builder.appendSql("/*dialect*/  insert into ebeilog(sendtime,sendData,connectId,connectName) values('"+sendTime+"','"+ybsjson.toString()+"','"+connectId+"','"+connectName+"')");
					builder.execute();
				ybsarr.add(ybsjson);
			}else{
				String createTime=sdf.format(project.getCreateTime());
				String updateTime=sdf.format(project.getLastUpdateTime());
				 ybjson.put("projectID", project.getId().toString());
				 connectId=project.getId().toString();
				 ybjson.put("projectCode", project.getNumber());
				 ybjson.put("projectName", project.getName());
				 connectName=project.getName();
				 ybjson.put("createdTime", createTime);
				 ybjson.put("updatedTime",updateTime );
				 if(project.getCreator()!=null){
					 ybjson.put("createdBy", project.getCreator().getName());
				 }else{
					 ybjson.put("createdBy", "");
				 }
				
				 if(project.getLastUpdateUser()!=null){
					 ybsjson.put("updatedBy", project.getLastUpdateUser().getName() );
				 }else{
					 ybsjson.put("updatedBy","");
				 }
				 ybjson.put("projectStatus", "1");
				 
				 builder.clear();
					builder.appendSql("/*dialect*/  insert into ebeilog(sendtime,sendData,connectId,connectName) values('"+sendTime+"','"+ybjson.toString()+"','"+connectId+"','"+connectName+"')");
					builder.execute();
				 ybarr.add(ybjson);
			}
  		}
		datajson.put("datas",ybarr);
		
		initjson.put("esbInfo", esbjson);
		initjson.put("requestInfo",datajson);
				
		datasjson.put("datas",ybsarr);
		initsjson.put("esbInfo", esbsjson);
		initsjson.put("requestInfo",datasjson);

		
//		to_yb project
		if(ybarr.size()>0){
			try {
//				System.out.println(initjson.toJSONString());
				String rs11=SHEManageHelper.execPostYBSProject(null, initjson.toJSONString(),timestamp);
				JSONObject rso = JSONObject.parseObject(rs11);
				if(rso!=null){
				if(!"A0200".equals(rso.getJSONObject("esbInfo").getString("returnCode"))){
					String errorMsg =rso.getJSONObject("esbInfo").getString("returnMsg");
					String info="来自一碑报错信息：";
					String sb = String.valueOf(new StringBuffer().append(errorMsg).append(info));
    				throw new EASBizException(new NumericExceptionSubItem("100",sb));
    			}else{
					JSONObject rst=rso.getJSONObject("esbInfo");
					 instId=rst.getString("instId");
					 requestTime=rst.getString("requestTime");
					 builder.clear();
					 builder.appendSql(" update esbInfo set instId='"+instId+"',requestTime='"+requestTime+"' where source='project'");
					 builder.executeUpdate();
       				 MsgBox.showInfo("同步项目成功");
				}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		

//		to_yb stage
  
		if(ybsarr.size()>0){
			try {
//				System.out.println(initsjson.toJSONString());
				String rs111=SHEManageHelper.execPostYBstage(null, initsjson.toJSONString(),timestamp);
				JSONObject rso = JSONObject.parseObject(rs111);
				if(rso!=null){
				if(!"A0200".equals(rso.getJSONObject("esbInfo").getString("returnCode"))){
					String errorMsg =rso.getJSONObject("esbInfo").getString("returnMsg");
					String info="来自一碑报错信息：";
					String sb = String.valueOf(new StringBuffer().append(errorMsg).append(info));
    				throw new EASBizException(new NumericExceptionSubItem("100",sb));
    			}else{
					JSONObject rst=rso.getJSONObject("esbInfo");
					 instId=rst.getString("instId");
					 requestTime=rst.getString("requestTime");
					 builder.clear();
					 builder.appendSql(" update esbInfo set instId='"+instId+"',requestTime='"+requestTime+"' where source='stage'");
					 builder.executeUpdate();
					MsgBox.showInfo("同步分期成功");
				}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
}