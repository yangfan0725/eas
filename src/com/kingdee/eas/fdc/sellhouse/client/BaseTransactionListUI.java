/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CommercialStageEnum;
import com.kingdee.eas.fdc.sellhouse.IBaseTransaction;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ProjectProductTypeSet;
import com.kingdee.eas.fdc.sellhouse.PurChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.app.MarketingUnitControllerBean;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.jdbc.rowset.IRowSet;
/**
 * output class name
 */
public abstract class BaseTransactionListUI extends AbstractBaseTransactionListUI implements SHEParamConstant
{
    private static final Logger logger = CoreUIObject.getLogger(BaseTransactionListUI.class);
    
    protected SellProjectInfo sellProject = null;
    protected BuildingInfo building = null;
    protected BuildingUnitInfo buildUnit = null;
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
    protected static String IAMPURCHASE = "purchase";
    protected static String IAMPREPURCHASE = "prePurchase";
    protected static String IAMSIGN = "sign";
    protected UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();		
    protected boolean isControl=SHEManageHelper.isControl(null, currentUserInfo);
    protected static final String CANTEDIT = "cantEdit";
    protected static final String CANTREMOVE = "cantRemove";
    protected Map roomDisplay=new HashMap();
    protected Map permit=new HashMap();
    public BaseTransactionListUI() throws Exception
    {
        super();
    }

    protected String[] getLocateNames()
    {
        String[] locateNames = new String[4];
        locateNames[0] = "number";
        locateNames[1] = "room.number";
        locateNames[2] = "customerNames";
        locateNames[3] = "customerPhone";
        return locateNames;
    }
    protected void initTree() throws Exception
	{
    	this.treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
    	SHEManageHelper.expandAllNodesByBuilding(treeMain, (DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
    }
    public void onLoad() throws Exception
	{
    	
    	actionQuery.setEnabled(false);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		
		if(this.getUIContext().get("filter")==null){
			initTree();
		}else{
			this.toolBar.setVisible(false);
			this.treeView.setVisible(false);
		}
		initControl();
		
		actionQuery.setEnabled(true);
	}
    protected void initControl(){
    	this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	this.treeMain.setSelectionRow(0);
    	   
		if (!isSaleHouseOrg)
		{
//			this.actionAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//			this.actionAudit.setEnabled(false);
//			this.actionUnAudit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//			this.actionInvalid.setEnabled(false);
//			this.actionReceiveBill.setEnabled(false);
//			
//			this.actionChangeName.setEnabled(false);
//			this.actionChangeRoom.setEnabled(false);
//			this.actionQuitRoom.setEnabled(false);
//			this.actionPriceChange.setEnabled(false);
		}

		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);

		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);

		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		
		this.actionReceiveBill.setVisible(false);
		
		FDCHelper.formatTableDate(getBillListTable(), "lastUpdateTime");
		FDCHelper.formatTableDate(getBillListTable(), "bizDate");
		FDCHelper.formatTableDate(getBillListTable(), "busAdscriptionDate");
		
		this.actionPriceChange.setVisible(false);
		this.actionChangeRoom.setVisible(false);
		
		if(IAMPREPURCHASE.equals(whoAmI())){
			this.actionUpdateRC.setVisible(false);
		}else{
			this.actionUpdateRC.setVisible(true);
		}
    }
    protected void initWorkButton() {
		super.initWorkButton();
		
        this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
       
        this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
        this.menuItemUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
        
        this.btnInvalid.setIcon(EASResource.getIcon("imgTbtn_blankout"));
        this.menuItemInvalid.setIcon(EASResource.getIcon("imgTbtn_blankout"));
        
        this.btnReceiveBill.setIcon(EASResource.getIcon("imgTbtn_monadismpostil"));
        this.menuItemReceiveBill.setIcon(EASResource.getIcon("imgTbtn_monadismpostil"));
        
        this.menuItemChangeName.setIcon(EASResource.getIcon("imgTbtn_recieversetting"));
        
        this.menuItemQuitRoom.setIcon(EASResource.getIcon("imgTbtn_quit"));
        
        this.menuItemChangeRoom.setIcon(EASResource.getIcon("imgTbtn_assetchange"));
        
        this.menuItemPriceChange.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
        
        KDMenuItem menuItem1 = new KDMenuItem();
    	menuItem1.setAction(this.actionChangeName);
    	menuItem1.setText("更名");
    	menuItem1.setIcon(EASResource.getIcon("imgTbtn_recieversetting"));
        
        KDMenuItem menuItem2 = new KDMenuItem();
        menuItem2.setAction(this.actionQuitRoom);
        menuItem2.setText("退房");
    	menuItem2.setIcon(EASResource.getIcon("imgTbtn_quit"));
    	
        KDMenuItem menuItem3 = new KDMenuItem();
        menuItem3.setAction(this.actionChangeRoom);
        menuItem3.setText("换房");
    	menuItem3.setIcon(EASResource.getIcon("imgTbtn_assetchange"));
    	
        KDMenuItem menuItem4 = new KDMenuItem();
        menuItem4.setAction(this.actionPriceChange);
        menuItem4.setText("价格变更");
    	menuItem4.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
    	
		this.btnSpecialBiz.setIcon(EASResource.getIcon("imgTbtn_disassemble"));
		this.btnSpecialBiz.addAssistMenuItem(menuItem1);
		this.btnSpecialBiz.addAssistMenuItem(menuItem2);
		this.btnSpecialBiz.addAssistMenuItem(menuItem3);
		this.btnSpecialBiz.addAssistMenuItem(menuItem4);
		
		this.btnMultiSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		this.btnImport.setIcon(EASResource.getIcon("imgTbtn_input"));
	}

	protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
	}
    protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
    protected void prepareUIContext(UIContext uiContext, ActionEvent e)
	{
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("sellProject", sellProject);
		uiContext.put("building", building);
		uiContext.put("buildUnit", buildUnit);
	}
    /**
     * 更名
     */
    public void actionChangeName_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	
    	changeNameCheck(id);
    	
    	SHEManageHelper.toChangeManage(ChangeBizTypeEnum.CHANGENAME,BOSUuid.read(id), this, false);
		this.refresh(null);
    }

    /**
     * 退房
     */
    public void actionQuitRoom_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	
    	quitRoomCheck(id);

    	SHEManageHelper.toChangeManage(ChangeBizTypeEnum.QUITROOM,BOSUuid.read(id), this, false);
		this.refresh(null);
    }

    /**
     * 换房
     */
    public void actionChangeRoom_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	
    	changeRoomCheck(id);
    	
    	SHEManageHelper.toChangeManage(ChangeBizTypeEnum.CHANGEROOM,BOSUuid.read(id), this, false);
		this.refresh(null);
    }

    /**
     * 价格变更
     */
    public void actionPriceChange_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	
    	priceChangeCheck(id);
    	
    	SHEManageHelper.toChangeManage(ChangeBizTypeEnum.PRICECHANGE,BOSUuid.read(id), this, false);
		this.refresh(null);
    }
    public TransactionStateEnum getBizState(String id) throws EASBizException, BOSException, Exception{
    	if(id==null) return null;
    	SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("bizState");
    	return ((BaseTransactionInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getBizState();
    }
    /**
     * 审批
     */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	checkRef(id.get(i).toString());
	    	auditCheck(id.get(i).toString());
	    	
			if (!getBizStateSubmitEnum().equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IBaseTransaction)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	/**
	 * 反审批
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	checkRef(id.get(i).toString());
	    	unAuditCheck(id.get(i).toString());
	    	
			if (!getBizStateAuditEnum().equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((IBaseTransaction)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	 /**
     * 作废
     */
    public void actionInvalid_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	checkRef(id.get(i).toString());
	    	invalidCheck(id.get(i).toString());
	    	
			if (!getBizStateSubmitEnum().equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行作废操作！");
				return;
			}
			((IBaseTransaction)getBizInterface()).invalid(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
    protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		TransactionStateEnum bizState = getBizState(id);
		
		if (!getBizStateSubmitEnum().equals(bizState)&&!getBizStateSavedEnum().equals(bizState)) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
				SysUtil.abort();
			}
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	checkRef(id);
    	editCheck(id);
    	
    	checkBeforeEditOrRemove(CANTEDIT,id);
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
	    	checkRef(id.get(i).toString());
	    	removeCheck(id.get(i).toString());
	    	checkBeforeEditOrRemove(CANTREMOVE,id.get(i).toString());
		}
		super.actionRemove_actionPerformed(e);
	}
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = this.getSelectedKeyValue();
        checkSelected();
        if (boID == null)
        {
            return;
        }
        acm.showAttachmentListUIByBoID(boID, this,true);
	}
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		sellProject=null;
		building=null;
		buildUnit=null;
		if (node.getUserObject() instanceof SellProjectInfo){
			//项目
			if(node.getUserObject() != null){
				sellProject=(SellProjectInfo) node.getUserObject();
			}			
		}else if (node.getUserObject() instanceof BuildingInfo){ 
			// 楼栋
			if(node.getUserObject() != null){
				building=(BuildingInfo)node.getUserObject();
				sellProject = building.getSellProject();
			}
		}else if (node.getUserObject() instanceof BuildingUnitInfo){ 
			// 单元
			if(node.getUserObject() != null){
				buildUnit=(BuildingUnitInfo)node.getUserObject();
				building=buildUnit.getBuilding();
				sellProject = buildUnit.getBuilding().getSellProject();
			}
		}
		
		if (node.getUserObject() instanceof OrgStructureInfo){
			this.actionAddNew.setEnabled(false);
		}else if(node.getUserObject() instanceof SellProjectInfo){
			if(SHEManageHelper.isSellProjectHasChild((SellProjectInfo) node.getUserObject())){
				this.actionAddNew.setEnabled(false);
			}else if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}else{
			if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}
		this.refresh(null);
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try	{
			FilterInfo filter = new FilterInfo();
			if(this.getUIContext().get("filter")==null){
				if(node!=null&&node.getUserObject()!=null){
					if (node.getUserObject() instanceof SellProjectInfo){
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", SHEManageHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet()),CompareType.INNER));	
					}else if (node.getUserObject() instanceof BuildingInfo){ 
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
						filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
					}else if (node.getUserObject() instanceof BuildingUnitInfo){ 
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
						filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
						filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
					} else if (node.getUserObject() instanceof OrgStructureInfo){
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", "'null'",CompareType.INNER));
					}
					if (!(node.getUserObject() instanceof OrgStructureInfo)&&!isControl){
						if(IAMPREPURCHASE.equals(whoAmI())){
							filter.getFilterItems().add(new FilterItemInfo("prePurchaseSaleManEntry.user.id", SHEManageHelper.getPermitSaleManSet(sellProject,permit),CompareType.INCLUDE));
						}else if(IAMPURCHASE.equals(whoAmI())){
							filter.getFilterItems().add(new FilterItemInfo("purSaleManEntry.user.id", SHEManageHelper.getPermitSaleManSet(sellProject,permit),CompareType.INCLUDE));
						}else if(IAMSIGN.equals(whoAmI())){
							filter.getFilterItems().add(new FilterItemInfo("signSaleManEntry.user.id", SHEManageHelper.getPermitSaleManSet(sellProject,permit),CompareType.INCLUDE));
						}
					}
				}else{
					filter.getFilterItems().add(new FilterItemInfo("id", "'null'"));
				}
			}else{
				filter.mergeFilter((FilterInfo) this.getUIContext().get("filter"), "and");
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
			if(viewInfo.getSorter()!=null&&viewInfo.getSorter().size()<2){
				viewInfo.getSorter().clear();
				viewInfo.getSorter().add(new SorterItemInfo("building.number"));
				viewInfo.getSorter().add(new SorterItemInfo("room.unit"));
				viewInfo.getSorter().add(new SorterItemInfo("room.floor"));
				viewInfo.getSorter().add(new SorterItemInfo("room.number"));
			}
		}catch (Exception e)
		{
			handleException(e);
		}
		if(IAMSIGN.equals(whoAmI())){
			tblMain.getColumn("saleManNames").getStyleAttributes().setHided(true);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	
	/**
	 * 审批事件扩充状态校验
	 */
	protected void auditCheck(String id)throws EASBizException, BOSException{
	}
	
	/**
	 * 反审批事件扩充状态校验
	 */
	protected void unAuditCheck(String id)throws EASBizException, BOSException{
	}
	
	/**
	 * 作废事件扩充状态校验
	 */
    protected void invalidCheck(String id)throws EASBizException, BOSException{
    }
	
    /**
	 * 修改事件扩充状态校验
	 */
	protected void editCheck(String id)throws EASBizException, BOSException{
	}
	
	/**
	 * 删除事件扩充状态校验
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	protected void removeCheck(String id) throws EASBizException, BOSException{
	}
	
	/**
	 * 更名事件扩充状态校验
	 */
	protected void changeNameCheck(String id)throws EASBizException, BOSException{
		
	}
	
	/**
	 * 换房事件扩充状态校验
	 */
	protected void changeRoomCheck(String id)throws EASBizException, BOSException{
		
	}
	
	/**
	 * 退房扩充状态校验
	 */
	protected void quitRoomCheck(String id)throws EASBizException, BOSException{
		
	}
	
	/**
	 * 价格变更扩充状态校验
	 */
	protected void priceChangeCheck(String id)throws EASBizException, BOSException{
		
	}
	
	/**
    *
    * 检查是否有关联对象
    *
    */
   protected void checkRef(String id) throws Exception {

   }
   
   /**
    * 屏蔽掉FDCBILLLIST里的这个方法，因为这个这是考虑到了成本模块，CRM这边无用
    */
   protected void fetchInitData() throws Exception {
	   
   }
   /**
    * 返回业务状态枚举审批通过状态枚举值
    */
   protected abstract TransactionStateEnum getBizStateAuditEnum();
   
   /**
    * 返回业务状态枚举提交状态枚举值
    */
   protected abstract TransactionStateEnum getBizStateSubmitEnum();
   
   protected abstract TransactionStateEnum getBizStateSavedEnum();
   
   /**
    * 返回业务状态枚举作废状态枚举值
    */
   protected abstract TransactionStateEnum getBizStateInvalidEnum();
   
   protected void afterTableFillData(KDTDataRequestEvent e) {
	   super.afterTableFillData(e);
	   setInvalidColor(whoAmI());
   }
   protected Map getRoomDisplay(String sellProjectId,String projectTypeId){
	   if(roomDisplay.containsKey(sellProjectId)){
		   Map projectType=(Map) roomDisplay.get(sellProjectId);
		   if(projectType.containsKey(projectTypeId)){
			   return (Map) projectType.get(projectTypeId);
		   }else{
			   Map map=RoomDisplaySetting.getNewProjectProductTypeSet(null,sellProjectId, projectTypeId);
			   projectType.put(projectTypeId, map);
			   return map;
		   }
	   }else{
		   Map map=RoomDisplaySetting.getNewProjectProductTypeSet(null,sellProjectId, projectTypeId);
		   Map sellProject=new HashMap();
		   sellProject.put(projectTypeId, map);
		   roomDisplay.put(sellProjectId, sellProject);
		   return map;
	   }
   }
   abstract protected String whoAmI();
   
   protected void setInvalidColor(String who){
	   long dayTime = 3600*24*1000;
	  Date curDate =  new Date();
	   if(IAMPREPURCHASE.equals(who)){
		   for(int i = 0 ; i < tblMain.getRowCount();i++){
			    IRow row = tblMain.getRow(i);
			    String sellProjectId = (String)getSelectedKeyValue(row,"sellProject.id");
			    String projectTypeId =(String)getSelectedKeyValue(row,"productType.id");
			    Map typeSet = getRoomDisplay(sellProjectId, projectTypeId);
			    Date bizDate = (Date)getSelectedKeyValue(row,"bizDate");
			    if(bizDate==null) continue;
			    long  diffd =  FDCDateHelper.dateDiff(bizDate,curDate )/dayTime ;
			    if(typeSet == null)continue;
			    int in =  ((Integer)typeSet.get(T1_PRE_PURCHASE_LIMIT_TIME)).intValue();
			    if(diffd > in){
			    	row.getStyleAttributes().setBackground(Color.pink);
			    }
		   }
	   }else if(IAMPURCHASE.equals(who)){
		   for(int i = 0 ; i < tblMain.getRowCount();i++){
			    IRow row = tblMain.getRow(i);
			    String sellProjectId = (String)getSelectedKeyValue(row,"sellProject.id");
			    String projectTypeId =(String)getSelectedKeyValue(row,"productType.id");
			    Map typeSet = getRoomDisplay(sellProjectId, projectTypeId);
			    Date bizDate = (Date)getSelectedKeyValue(row,"bizDate");
			    if(bizDate==null)continue;
			    long  diffd =  FDCDateHelper.dateDiff(bizDate,curDate )/dayTime ;
			    if(typeSet == null)continue;
			    int in =  ((Integer)typeSet.get(T1_PURCHASE_LIMIT_TIME)).intValue();
			    if(diffd > in){
			    	row.getStyleAttributes().setBackground(Color.pink);
			    }
//			    int in2 =  ((Integer)typeSet.get(T1_TO_SIGN_LIMIT_TIME)).intValue();
//			    Date planSignDate = (Date)getSelectedKeyValue(row,"planSignDate");
//			    if(planSignDate==null) continue;
//			    long  diffd2 =  FDCDateHelper.dateDiff(planSignDate,curDate )/dayTime ;
//			    if(diffd2 > in2){
//			    	row.getStyleAttributes().setBackground(Color.pink);
//			    }
		   }
	   }else if(IAMSIGN.equals(who)){
		   for(int i = 0 ; i < tblMain.getRowCount();i++){
			    IRow row = tblMain.getRow(i);
			    String sellProjectId = (String)getSelectedKeyValue(row,"sellProject.id");
			    String projectTypeId =(String)getSelectedKeyValue(row,"productType.id");
			    Map typeSet = getRoomDisplay(sellProjectId, projectTypeId);
			    Date bizDate = (Date)getSelectedKeyValue(row,"bizDate");
			    if(bizDate==null) continue;
			    long  diffd =  FDCDateHelper.dateDiff(bizDate,curDate )/dayTime ;
			    if(typeSet == null) continue;
			    int in =  ((Integer)typeSet.get(T1_SIGN_LIMIT_TIME)).intValue();
			    if(diffd > in){
			    	row.getStyleAttributes().setBackground(Color.pink);
			    }
		   }
	   }
   }
   
   protected Object getSelectedKeyValue(IRow row,String feildName) {
       if (row == null){
    	   return null;
       }
       ICell cell = row.getCell(feildName);
       return cell.getValue();
   }
   public void actionMultiSubmit_actionPerformed(ActionEvent e) throws Exception {
	   checkSelected();
	   Window win = SwingUtilities.getWindowAncestor(this);
       LongTimeDialog dialog = null;
       if(win instanceof Frame)
           dialog = new LongTimeDialog((Frame)win);
       else
       if(win instanceof Dialog)
           dialog = new LongTimeDialog((Dialog)win);
       
       dialog.setLongTimeTask(new ILongTimeTask() {
           public Object exec()
               throws Exception
           {
        	   ArrayList id = getSelectedIdValues();
        	   if(IAMPREPURCHASE.equals(whoAmI())){
        		   for(int i = 0; i < id.size(); i++){
        			   UIContext uiContext = new UIContext(this);
        			   uiContext.put("ID", id.get(i).toString());
        			   PrePurchaseManageEditUI ui=(PrePurchaseManageEditUI) UIFactoryHelper.initUIObject(PrePurchaseManageEditUI.class.getName(), uiContext, null,OprtState.EDIT);
        			   TransactionStateEnum state = ((BaseTransactionInfo)ui.getEditData()).getBizState();
        			   FDCClientUtils.checkBillInWorkflow(ui, ui.getEditData().getId().toString());
        				
        			   if(state==null||!(TransactionStateEnum.PREAPPLE.equals(state)||TransactionStateEnum.PRESAVED.equals(state))){
        					MsgBox.showWarning("单据不是保存或者提交状态，不能进行提交操作！");
        					SysUtil.abort();
        			   }
        			   ui.verifyInputForSubmint();
        			   ui.runSubmit();
        			   ui.destroyWindow();
        		   }
        	   }else if(IAMPURCHASE.equals(whoAmI())){
        		   for(int i = 0; i < id.size(); i++){
        			   UIContext uiContext = new UIContext(this);
        			   uiContext.put("ID", id.get(i).toString());
        			   PurchaseManageEditUI ui=(PurchaseManageEditUI) UIFactoryHelper.initUIObject(PurchaseManageEditUI.class.getName(), uiContext, null,OprtState.EDIT);
        			   TransactionStateEnum state = ((BaseTransactionInfo)ui.getEditData()).getBizState();
        			   FDCClientUtils.checkBillInWorkflow(ui, ui.getEditData().getId().toString());
        				
        			   if(state==null||!(TransactionStateEnum.PURAPPLE.equals(state)||TransactionStateEnum.PURSAVED.equals(state))){
        					MsgBox.showWarning("单据不是保存或者提交状态，不能进行提交操作！");
        					SysUtil.abort();
        			   }
	       			   ui.verifyInputForSubmint();
	       			   ui.runSubmit();
	       			   ui.destroyWindow();
        		   }
        	   }else if(IAMSIGN.equals(whoAmI())){
        		   for(int i = 0; i < id.size(); i++){
        			   UIContext uiContext = new UIContext(this);
        			   uiContext.put("ID", id.get(i).toString());
        			   SignManageEditUI ui=(SignManageEditUI) UIFactoryHelper.initUIObject(SignManageEditUI.class.getName(), uiContext, null,OprtState.EDIT);
        			   TransactionStateEnum state = ((BaseTransactionInfo)ui.getEditData()).getBizState();
        			   FDCClientUtils.checkBillInWorkflow(ui, ui.getEditData().getId().toString());
        				
        			   if(state==null||!(TransactionStateEnum.SIGNAPPLE.equals(state)||TransactionStateEnum.SIGNSAVED.equals(state))){
        					MsgBox.showWarning("单据不是保存或者提交状态，不能进行提交操作！");
        					SysUtil.abort();
        			   }
	       			   ui.verifyInputForSubmint();
	       			   ui.runSubmit();
	       			   ui.destroyWindow();
        		   }
        	   }
               return new Boolean(true);
           }
           public void afterExec(Object result)
               throws Exception
           {
        	   FDCMsgBox.showWarning("操作成功！");
           }
       }
);
       dialog.show();
	   this.refresh(null);
   }
   public void actionImport_actionPerformed(ActionEvent e) throws Exception {
	   String strSolutionName = "";
	   if(IAMPREPURCHASE.equals(whoAmI())){
		   strSolutionName = "eas.fdc.sellhouse.prePurchaseManage";
	   }else if(IAMPURCHASE.equals(whoAmI())){
		   strSolutionName = "eas.fdc.sellhouse.purchaseManage";
	   }else if(IAMSIGN.equals(whoAmI())){
		   strSolutionName = "eas.fdc.sellhouse.signManage";
	   }
       DatataskCaller task = new DatataskCaller();
       task.setParentComponent(this);
       DatataskParameter param = new DatataskParameter();
       String solutionName = strSolutionName;
       param.solutionName = solutionName;
       ArrayList paramList = new ArrayList();
       paramList.add(param);
       task.invoke(paramList, DatataskMode.UPDATE, true);
       this.refresh(null);
   }
   protected CommonQueryDialog initCommonQueryDialog() {
		CommonQueryDialog commonQueryDialog = super.initCommonQueryDialog();
		try
		{
			if(IAMSIGN.equals(whoAmI())){
				commonQueryDialog.addUserPanel(new SignManageFilterUI(whoAmI()));
				commonQueryDialog.setHeight(400);
			}else{
				commonQueryDialog.addUserPanel(new BaseTransactionFilterUI(whoAmI()));
				commonQueryDialog.setHeight(350);
			}
			commonQueryDialog.setWidth(600);
		}
		catch(Exception e)
		{
			super.handUIException(e);
		}
		return commonQueryDialog;
	}
   protected boolean initDefaultFilter() {
	   if(this.getUIContext().get("filter")==null){
		   return true;
	   }else{
		   return false;
	   }
   }
	public void actionUpdateRC_actionPerformed(ActionEvent e) throws Exception {
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		
		String	param="false";
		String	paramValue="true";
		try {
			param = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_WF");
			paramValue = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_QD");
		} catch (EASBizException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(IAMPURCHASE.equals(whoAmI())){
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("*");
			sic.add("sellProject.number");
			sic.add("room.number");
			sic.add("purCustomerEntry.isMain");
			sic.add("purCustomerEntry.customer.*");
			sic.add("purCustomerEntry.customer.certificateType.name");
			sic.add("purCustomerEntry.customer.propertyConsultant.*");
			sic.add("room.productType.name");
			sic.add("payType.name");
			sic.add("purSaleManEntry.*");
			sic.add("purSaleManEntry.user.number");
			
			PurchaseManageInfo info=PurchaseManageFactory.getRemoteInstance().getPurchaseManageInfo(new ObjectUuidPK(id),sic);
			if(!info.getBizState().equals(TransactionStateEnum.PURAUDIT)&&!info.getBizState().equals(TransactionStateEnum.PURAPPLE)&&!info.getBizState().equals(TransactionStateEnum.TOSIGN)){
				MsgBox.showWarning("状态不是提交、审批、转签约状态！");
				return;
			}
			if("true".equals(param)){
				JSONArray carr=new JSONArray();
				JSONObject cjo=new JSONObject();
				cjo.put("id", info.getNumber().toString());
				cjo.put("type", "2");
				cjo.put("projectId",info.getSellProject().getNumber());
				String customerId="";
				SHECustomerInfo quc=null;
				Timestamp qudate=null;
				
				for(int i=0;i<info.getPurCustomerEntry().size();i++){
					if(i==0){
						customerId=info.getPurCustomerEntry().get(i).getCustomer().getNumber();
					}else{
						customerId=customerId+"@"+info.getPurCustomerEntry().get(i).getCustomer().getNumber();
					}
					if("true".equals(paramValue)){
						if(info.getPurCustomerEntry().get(i).isIsMain()){
							quc=info.getPurCustomerEntry().get(i).getCustomer();
						}
					}else{
						if(info.getPurCustomerEntry().get(i).getCustomer().getFirstDate()==null&&info.getPurCustomerEntry().get(i).getCustomer().getReportDate()==null){
							MsgBox.showWarning("客户报备日期和首访日期都为空！");
							return;
						}
						if(qudate==null){
							if(info.getPurCustomerEntry().get(i).getCustomer().getReportDate()!=null){
								qudate=info.getPurCustomerEntry().get(i).getCustomer().getReportDate();
							}else{
								qudate=info.getPurCustomerEntry().get(i).getCustomer().getFirstDate();
							}
							quc=info.getPurCustomerEntry().get(i).getCustomer();
						}else{
							Timestamp comdate=null;
							if(info.getPurCustomerEntry().get(i).getCustomer().getReportDate()!=null){
								comdate=info.getPurCustomerEntry().get(i).getCustomer().getReportDate();
							}else{
								comdate=info.getPurCustomerEntry().get(i).getCustomer().getFirstDate();
							}
							if(qudate.after(comdate)){
								qudate=comdate;
								quc=info.getPurCustomerEntry().get(i).getCustomer();
							}
						}
					}
				}
				cjo.put("qdCustId",quc.getNumber());
				cjo.put("customerId", customerId);
				if(info.getRoom()!=null){
					cjo.put("roomId", info.getRoom().getNumber());
					cjo.put("rcYT", info.getRoom().getProductType().getName());
				}
				cjo.put("roomStatus", "认购");
				cjo.put("userId", quc.getPropertyConsultant().getNumber());
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				cjo.put("tradeDate", df.format(info.getBizDate()));
				cjo.put("tradeAmount", info.getDealTotalAmount());
				cjo.put("payType", info.getPayType().getName());
				cjo.put("expectDate", df.format(info.getPlanSignDate()));
				cjo.put("price", info.getDealBuildPrice());
				cjo.put("area",info.getBulidingArea());
				cjo.put("contractNo", info.getNumber());
				cjo.put("rengouId", "");
				cjo.put("remark", "");
				cjo.put("orderType", "");
				cjo.put("saleType", "");
				
				if(PurChangeStateEnum.CHANGE.equals(info.getChangeState())){
					cjo.put("isChangeRoomNew", "1");
				}else{
					cjo.put("isChangeRoomNew", "0");
				}
				
				sic=new SelectorItemCollection();
				sic.add("number");
				if(info.getSrcId()!=null){
					SincerityPurchaseInfo src=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(info.getSrcId()),sic);
					cjo.put("rcId", src.getNumber());
				}
				cjo.put("salesStatisticsDate", "");
				cjo.put("fyyt", info.getRoom().getProductType().getName());
				
				carr.add(cjo);
	  		
				try {
					String rs=SHEManageHelper.execPost(null, "sap_transaction_mcrm_channel", carr.toJSONString());
					JSONObject rso = JSONObject.parseObject(rs);
					if(!"SUCCESS".equals(rso.getString("status"))){
						MsgBox.showWarning(rso.getString("message"));
						return;
					}
					rs=SHEManageHelper.execPost(null, "sap_transaction_organ_personal_channel", carr.toJSONString());
					rso = JSONObject.parseObject(rs);
					if(!"SUCCESS".equals(rso.getString("status"))){
						MsgBox.showWarning(rso.getString("message"));
						return;
					}else{
						MsgBox.showInfo("同步成功！");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					MsgBox.showWarning(e1.getMessage());
					return;
				} catch (IOException e1) {
					MsgBox.showWarning(e1.getMessage());
					return;
				}
			}
			_builder.addBatch(" update t_she_purchaseManage t set fqdperson=(select t2.FQdPersontxt from t_she_purcustomerentry t1 left join t_she_shecustomer t2 on t2.fid=t1.fcustomerid where t1.fismain=1 and t1.fheadid=t.fid) where fid='"+id+"'");
			_builder.addBatch(" update t_she_purchaseManage t set CFRecommended=(select t2.CFRecommended from t_she_purcustomerentry t1 left join t_she_shecustomer t2 on t2.fid=t1.fcustomerid where t1.fismain=1 and t1.fheadid=t.fid) where fid='"+id+"'");
			_builder.addBatch(" update t_she_pursalemanentry t set FUserId=(select t2.FPropertyConsultantID from t_she_purcustomerentry t1 left join t_she_shecustomer t2 on t2.fid=t1.fcustomerid where t1.fismain=1 and t1.fheadid=t.fheadid) where fheadid='"+id+"'");
			_builder.addBatch(" update t_she_purchaseManage t set fsalesmanid=(select t2.FPropertyConsultantID from t_she_purcustomerentry t1 left join t_she_shecustomer t2 on t2.fid=t1.fcustomerid where t1.fismain=1 and t1.fheadid=t.fid),FSALEMANNAMES=(select t3.fname_l2 from t_she_purcustomerentry t1 left join t_she_shecustomer t2 on t2.fid=t1.fcustomerid left join t_pm_user t3 on t3.fid=t2.FPropertyConsultantID where t1.fismain=1 and t1.fheadid=t.fid) where fid='"+id+"'");
			
			_builder.executeBatch();
	    }else if(IAMSIGN.equals(whoAmI())){
	    	SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("*");
			sic.add("sellProject.number");
			sic.add("room.number");
			sic.add("signCustomerEntry.isMain");
			sic.add("signCustomerEntry.customer.*");
			sic.add("signCustomerEntry.customer.certificateType.name");
			sic.add("signCustomerEntry.customer.propertyConsultant.*");
			sic.add("room.productType.name");
			sic.add("payType.name");
			sic.add("signSaleManEntry.user.number");
			sic.add("signSaleManEntry.*");
			
			SignManageInfo info=SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id),sic);
			if(!info.getBizState().equals(TransactionStateEnum.SIGNAUDIT)&&!info.getBizState().equals(TransactionStateEnum.SIGNAPPLE)){
				MsgBox.showWarning("状态不是提交、审批状态！");
				return;
			}
			if("true".equals(param)){
				JSONArray carr=new JSONArray();
				JSONObject cjo=new JSONObject();
				cjo.put("id", info.getNumber());
				cjo.put("type", "3");
				cjo.put("projectId",info.getSellProject().getNumber());
				String customerId="";
				SHECustomerInfo quc=null;
				Timestamp qudate=null;
				for(int i=0;i<info.getSignCustomerEntry().size();i++){
					if(i==0){
						customerId=info.getSignCustomerEntry().get(i).getCustomer().getNumber();
					}else{
						customerId=customerId+"@"+info.getSignCustomerEntry().get(i).getCustomer().getNumber();
					}
					if("true".equals(paramValue)){
						if(info.getSignCustomerEntry().get(i).isIsMain()){
							quc=info.getSignCustomerEntry().get(i).getCustomer();
						}
					}else{
						if(info.getSignCustomerEntry().get(i).getCustomer().getFirstDate()==null&&info.getSignCustomerEntry().get(i).getCustomer().getReportDate()==null){
							MsgBox.showWarning("客户报备日期和首访日期都为空！");
							return;
						}
						if(qudate==null){
							if(info.getSignCustomerEntry().get(i).getCustomer().getReportDate()!=null){
								qudate=info.getSignCustomerEntry().get(i).getCustomer().getReportDate();
							}else{
								qudate=info.getSignCustomerEntry().get(i).getCustomer().getFirstDate();
							}
							quc=info.getSignCustomerEntry().get(i).getCustomer();
						}else{
							Timestamp comdate=null;
							if(info.getSignCustomerEntry().get(i).getCustomer().getReportDate()!=null){
								comdate=info.getSignCustomerEntry().get(i).getCustomer().getReportDate();
							}else{
								comdate=info.getSignCustomerEntry().get(i).getCustomer().getFirstDate();
							}
							if(qudate.after(comdate)){
								qudate=comdate;
								quc=info.getSignCustomerEntry().get(i).getCustomer();
							}
						}
					}
				}
				cjo.put("qdCustId",quc.getNumber());
				cjo.put("cstName",quc.getName());
				cjo.put("cstPhone",quc.getPhone());
				cjo.put("cstCardId",quc.getCertificateNumber());
				if(quc.getCertificateType()!=null)
					cjo.put("cstCardIdType",quc.getCertificateType().getName());
				
				cjo.put("customerId", customerId);
				if(info.getRoom()!=null){
					cjo.put("roomId", info.getRoom().getNumber());
					cjo.put("rcYT", info.getRoom().getProductType().getName());
				}
				cjo.put("roomStatus", "签约");
				cjo.put("userId", quc.getPropertyConsultant().getNumber());
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				cjo.put("tradeDate", df.format(info.getBizDate()));
				cjo.put("tradeAmount", info.getDealTotalAmount());
				cjo.put("payType", info.getPayType().getName());
				cjo.put("expectDate", "");
				cjo.put("price", info.getDealBuildPrice());
				cjo.put("area",info.getBulidingArea());
				cjo.put("contractNo", info.getNumber());
				cjo.put("remark", "");
				cjo.put("orderType", "");
				cjo.put("saleType", "");
				
				if(SignChangeStateEnum.CHANGE.equals(info.getChangeState())){
					cjo.put("isChangeRoomNew", "1");
				}else{
					cjo.put("isChangeRoomNew", "0");
				}
				
				sic=new SelectorItemCollection();
				sic.add("number");
				if(info.getSrcId()!=null){
					if(info.getSrcId().getType().equals(new PurchaseManageInfo().getBOSType())){
						PurchaseManageInfo src=PurchaseManageFactory.getRemoteInstance().getPurchaseManageInfo(new ObjectUuidPK(info.getSrcId()),sic);
						cjo.put("rengouId", src.getNumber());
					}else{
						SincerityPurchaseInfo src=SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(info.getSrcId()),sic);
						cjo.put("rcId",src.getNumber());
					}
				}
				
				cjo.put("salesStatisticsDate", df.format(info.getBizDate()));
				cjo.put("fyyt", info.getRoom().getProductType().getName());
				
				carr.add(cjo);
	  		
				try {
					String rs=SHEManageHelper.execPost(null, "sap_transaction_mcrm_channel", carr.toJSONString());
					JSONObject rso = JSONObject.parseObject(rs);
					if(!"SUCCESS".equals(rso.getString("status"))){
						MsgBox.showWarning(rso.getString("message"));
						return;
					}
					rs=SHEManageHelper.execPost(null, "sap_transaction_organ_personal_channel", carr.toJSONString());
					rso = JSONObject.parseObject(rs);
					if(!"SUCCESS".equals(rso.getString("status"))){
						MsgBox.showWarning(rso.getString("message"));
					}else{
						MsgBox.showInfo("同步成功！");
					}
				} catch (SQLException e1) {
					MsgBox.showWarning(e1.getMessage());
					return;
				} catch (IOException e1) {
					MsgBox.showWarning(e1.getMessage());
					return;
				}
			}
			_builder.addBatch(" update t_she_signManage t set fqdperson=(select t2.FQdPersontxt from t_she_signcustomerentry t1 left join t_she_shecustomer t2 on t2.fid=t1.fcustomerid where t1.fismain=1 and t1.fheadid=t.fid) where fid='"+id+"'");
			_builder.addBatch(" update t_she_signManage t set CFRecommended=(select t2.CFRecommended from t_she_signcustomerentry t1 left join t_she_shecustomer t2 on t2.fid=t1.fcustomerid where t1.fismain=1 and t1.fheadid=t.fid) where fid='"+id+"'");
			_builder.addBatch(" update t_she_signsalemanentry t set FUserId=(select t2.FPropertyConsultantID from t_she_signcustomerentry t1 left join t_she_shecustomer t2 on t2.fid=t1.fcustomerid where t1.fismain=1 and t1.fheadid=t.fheadid) where fheadid='"+id+"'");
			_builder.addBatch(" update t_she_signManage t set fsalesmanid=(select t2.FPropertyConsultantID from t_she_signcustomerentry t1 left join t_she_shecustomer t2 on t2.fid=t1.fcustomerid where t1.fismain=1 and t1.fheadid=t.fid),FSALEMANNAMES=(select t3.fname_l2 from t_she_signcustomerentry t1 left join t_she_shecustomer t2 on t2.fid=t1.fcustomerid left join t_pm_user t3 on t3.fid=t2.FPropertyConsultantID where t1.fismain=1 and t1.fheadid=t.fid) where fid='"+id+"'");
			
			_builder.executeBatch();
	    }
		FDCMsgBox.showInfo("更新成功！");
	}
   
}