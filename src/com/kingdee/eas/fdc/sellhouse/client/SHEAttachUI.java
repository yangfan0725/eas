/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.print.KDPrinter;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypePropertyEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEAttachBillCollection;
import com.kingdee.eas.fdc.sellhouse.SHEAttachBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SHEAttachBillFactory;
import com.kingdee.eas.fdc.sellhouse.SHEAttachBillInfo;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListCollection;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListFactory;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellStageEnum;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fdc.sellhouse.report.RoomSourceReportFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.report.RoomSourceReportFilterUI;
import com.kingdee.eas.fdc.tenancy.client.DepositDealBillEditUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SHEAttachUI extends AbstractSHEAttachUI
{
    private static final Logger logger = CoreUIObject.getLogger(SHEAttachUI.class);
    public SHEAttachUI() throws Exception
    {
        super();
        disposeUIWindow();
    }
	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new RoomSourceReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return null;
	}

	protected KDTable getTableForPrintSetting() {
		// TODO Auto-generated method stub
		return tblMain;
	}

	protected void query() {
		Window win = SwingUtilities.getWindowAncestor(this);
        LongTimeDialog dialog = null;
        if(win instanceof Frame){
        	dialog = new LongTimeDialog((Frame)win);
        }else if(win instanceof Dialog){
        	dialog = new LongTimeDialog((Dialog)win);
        }
        if(dialog==null){
        	dialog = new LongTimeDialog(new Frame());
        }
        dialog.setLongTimeTask(new ILongTimeTask() {
            public Object exec()throws Exception{
            	getTableForPrintSetting().removeRows();
        		Map mapPara = new HashMap();
//        		params.setString("sellState", "('Purchase','Sign')");
        		mapPara.put("param",params );
        		
        		Map map = RoomSourceReportFacadeFactory.getRemoteInstance().getTableList(mapPara);
             	return map;
            }
            public void afterExec(Object result)throws Exception{
            	List list = (ArrayList)((Map)result).get("list");
            	RoomDisplaySetting setting = new RoomDisplaySetting();
				for(int i=0;i<list.size();i++){
					Map detialMap = (Map)list.get(i);
					IRow row = tblMain.addRow();
					row.setUserObject(detialMap.get("id"));
					row.getCell("productType").setValue(detialMap.get("productType"));//产品类型
					if(RoomSellStateEnum.getEnum((String)detialMap.get("sellState"))!=null){
						row.getCell("sellState").setValue(RoomSellStateEnum.getEnum((String)detialMap.get("sellState")).getAlias());//销售状态
					}else{
						row.getCell("sellState").setValue((String)detialMap.get("sellState"));
					}
					row.getCell("name").setValue(detialMap.get("name"));//房间
					row.getCell("roomModel").setValue(detialMap.get("roomModel"));//房型
					row.getCell("buildingArea").setValue(detialMap.get("buildingArea"));//预售建筑面积
					row.getCell("roomArea").setValue(detialMap.get("roomArea"));//预售套内面积
					row.getCell("ibasement").setValue(detialMap.get("ibasement"));//含地下室预售建筑面积
					row.getCell("ibaInnside").setValue(detialMap.get("ibaInnside"));//含地下室预售套内面积
					row.getCell("standardBuildPrice").setValue(detialMap.get("standardBuildPrice"));//标准建筑单价
					row.getCell("standardRoomPrice").setValue(detialMap.get("standardRoomPrice"));//标准套内单价
					row.getCell("standardTotalAmount").setValue(detialMap.get("standardTotalAmount"));//标准总价
					row.getCell("dealBuildPrice").setValue(detialMap.get("dealBuildPrice"));//成交建筑单价
					row.getCell("dealRoomPrice").setValue(detialMap.get("dealRoomPrice"));//成交套内单价
					row.getCell("dealTotalAmount").setValue(detialMap.get("dealTotalAmount"));//成交总价
					row.getCell("actualBuildingArea").setValue(detialMap.get("actualBuildingArea"));//实测建筑面积
					row.getCell("actualRoomArea").setValue(detialMap.get("actualRoomArea"));//实测套内面积
					row.getCell("sellAmount").setValue(detialMap.get("sellAmount"));//补差后合同价
					
					
					row.getCell("baseStandardPrice").setValue(detialMap.get("baseStandardPrice"));//实测建筑面积
					row.getCell("baseBuildPrice").setValue(detialMap.get("baseBuildPrice"));//实测套内面积
					row.getCell("baseRoomPrice").setValue(detialMap.get("baseRoomPrice"));//补差后合同价
					
					row.getCell("build").setValue(detialMap.get("build"));
					row.getCell("roomModelType").setValue(detialMap.get("roomModelType"));
					row.getCell("busAdscriptionDate").setValue(detialMap.get("busAdscriptionDate"));
					
					row.getCell("description").setValue(detialMap.get("description"));
					row.getCell("backAmount").setValue(detialMap.get("backAmount"));
					row.getCell("quitBackAmount").setValue(detialMap.get("quitBackAmount"));
					row.getCell("customer").setValue(detialMap.get("customer"));
					
					row.getCell("joinState").setValue(detialMap.get("joinState"));
					row.getCell("joinDate").setValue(detialMap.get("joinDate"));
					
					row.getCell("projectStandardPrice").setValue(detialMap.get("projectStandardPrice"));
					row.getCell("projectBuildPrice").setValue(detialMap.get("projectBuildPrice"));
					
					BigDecimal sellAmount=FDCHelper.toBigDecimal(detialMap.get("sellAmount"));
					BigDecimal backAmount=FDCHelper.toBigDecimal(detialMap.get("backAmount"));
//					boolean isColor=false;
//					if(row.getCell("sellState").getValue().toString().equals("签约")||
//							row.getCell("sellState").getValue().toString().equals("认购")){
//						isColor=true;
//					}
//					if(isColor&&sellAmount.compareTo(backAmount)==0){
//						row.getStyleAttributes().setBackground(new Color(128,255,128));
//					}
					if(row.getCell("sellState").getValue().toString().equals("未推盘")){
						row.getStyleAttributes().setBackground(setting.getBaseRoomSetting().getInitColor());
					}else if(row.getCell("sellState").getValue().toString().equals("待售")){
						row.getStyleAttributes().setBackground(setting.getBaseRoomSetting().getOnShowColor());
					}else if(row.getCell("sellState").getValue().toString().equals("预定")){
						row.getStyleAttributes().setBackground(setting.getBaseRoomSetting().getPrePurColor());
					}else if(row.getCell("sellState").getValue().toString().equals("认购")){
						row.getStyleAttributes().setBackground(setting.getBaseRoomSetting().getPurColor());
					}else if(row.getCell("sellState").getValue().toString().equals("签约")){
						row.getStyleAttributes().setBackground(setting.getBaseRoomSetting().getSignColor());
					}else if(row.getCell("sellState").getValue().toString().equals("预留")){
						row.getStyleAttributes().setBackground(setting.getBaseRoomSetting().getKeepDownColor());
					}else if(row.getCell("sellState").getValue().toString().equals("排号")){
						row.getStyleAttributes().setBackground(setting.getBaseRoomSetting().getSincerPurColor());
					}else if(row.getCell("sellState").getValue().toString().equals("销控")){
						row.getStyleAttributes().setBackground(setting.getBaseRoomSetting().getControlColor());
					}
				}
				tblMain.setRowCount(list.size());
				CRMClientHelper.getFootRow(tblMain, new String[]{"projectStandardPrice","backAmount","quitBackAmount","buildingArea","roomArea","ibasement","ibaInnside","standardTotalAmount","dealTotalAmount","actualBuildingArea","actualRoomArea","sellAmount","baseStandardPrice"});
				for(int i=0;i<tblMain.getColumnCount();i++){
		    		KDTableHelper.autoFitColumnWidth(tblMain, i);
		    	}
            }
        } );
        dialog.show();
        try {
			queryAttachList();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	public void tableDataRequest(KDTDataRequestEvent arg0) {
		
	}
	/* (non-Javadoc)
	 * @see com.kingdee.eas.framework.report.client.CommRptBaseUI#onLoad()
	 */
	public void onLoad() throws Exception {
		initTable();
		FDCSysContext.getInstance().checkIsSHEOrg(this);
		setShowDialogOnLoad(false);
		super.onLoad();
		if(getUIContext().get("RPTFilter") == null){
			initTree();
		}else{
			treeView.setVisible(false);
		}
		this.actionQuery.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		tblMain.getColumn("roomModel").getStyleAttributes().setHided(true);
		tblMain.getColumn("roomModelType").getStyleAttributes().setHided(true);
		tblMain.getColumn("ibasement").getStyleAttributes().setHided(true);
		tblMain.getColumn("ibaInnside").getStyleAttributes().setHided(true);
		tblMain.getColumn("baseRoomPrice").getStyleAttributes().setHided(true);
		tblMain.getColumn("dealRoomPrice").getStyleAttributes().setHided(true);
		tblMain.getColumn("standardRoomPrice").getStyleAttributes().setHided(true);
		
		tblMain.getColumn("roomArea").getStyleAttributes().setHided(true);
		tblMain.getColumn("actualRoomArea").getStyleAttributes().setHided(true);
		tblMain.getColumn("baseStandardPrice").getStyleAttributes().setHided(true);
		tblMain.getColumn("projectStandardPrice").getStyleAttributes().setHided(true);
		tblMain.getColumn("dealTotalAmount").getStyleAttributes().setHided(true);
		tblMain.getColumn("baseBuildPrice").getStyleAttributes().setHided(true);
		tblMain.getColumn("projectBuildPrice").getStyleAttributes().setHided(true);
		
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblAttach.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		kDTabbedPane1.removeAll();
	}
	
	protected void initTable(){
		tblMain.getStyleAttributes().setLocked(true);
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(1);
        enableExportExcel(tblMain);
        //设置垂直滚动条
        tblMain.setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_SHOW);
        //设置水平滚动条
        tblMain.setScrollStateHorizon(KDTStyleConstants.SCROLL_STATE_SHOW);
        
		String[] fields=new String[tblMain.getColumnCount()];
		for(int i=0;i<tblMain.getColumnCount();i++){
			fields[i]=tblMain.getColumnKey(i);
		}
		 KDTSortManager sortManager = new KDTSortManager(tblMain);
		 sortManager.setSortAuto(true);
		 sortManager.setClickCount(1);
		 for(int i = 0; i < fields.length; i++){
			 IColumn column = tblMain.getColumn(fields[i]);
			 if(column != null)
				 column.setSortable(true);
         }
		
		tblAttach.getStyleAttributes().setLocked(true);
		tblAttach.checkParsed();
		addCommonMenusToTable(tblAttach);
		this.tblAttach.getColumn("attach").getStyleAttributes().setFontColor(Color.BLUE);
		
		CRMClientHelper.fmtDate(tblAttach, new String[]{"createTime","auditTime"});
	}
	protected void initTree() throws Exception{
		this.treeMain.setModel(CRMTreeHelper.getBuildingTree(actionOnLoad,true));
		SHEManageHelper.expandAllNodesByBuilding(treeMain, (DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.sellhouse.report.AbstractRoomSourceReportUI#treeMain_valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
//		super.treeMain_valueChanged(e);
//		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
//		if(treeNode!=null){
//			Object obj = treeNode.getUserObject();
//			if (obj instanceof OrgStructureInfo) {
//				params.setObject("sellProject", null);
//			}else if (treeNode.getUserObject() instanceof SellProjectInfo){
//				//项目
//				if(treeNode.getUserObject() != null){
//					params.setObject("buildUnit", null);
//					params.setObject("building", null);
//					params.setObject("sellProject", treeNode.getUserObject());
//				}			
//			}else if (treeNode.getUserObject() instanceof BuildingInfo){ 
//				// 楼栋
//				if(treeNode.getUserObject() != null){
//					BuildingInfo building=(BuildingInfo)treeNode.getUserObject();
//					params.setObject("buildUnit", null);
//					params.setObject("building", building);
//					params.setObject("sellProject", building.getSellProject());
//				}
//			}else if (treeNode.getUserObject() instanceof BuildingUnitInfo){ 
//				// 单元
//				if(treeNode.getUserObject() != null){
//					BuildingUnitInfo buildUnit=(BuildingUnitInfo)treeNode.getUserObject();
//					BuildingInfo building=buildUnit.getBuilding();
//					params.setObject("buildUnit", buildUnit);
//					params.setObject("building", building);
//					params.setObject("sellProject", building.getSellProject());
//				}
//			}
//			query();
//		}
		
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				params.setObject("sellProject", allSpIdStr);
				params.setObject("buildUnit", null);
				params.setObject("building", null);
			}else if(obj instanceof SellProjectInfo){
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				params.setObject("sellProject", allSpIdStr);
				params.setObject("buildUnit", null);
				params.setObject("building", null);
			}else if (obj instanceof BuildingInfo){ 
				BuildingInfo building=(BuildingInfo)obj;
				params.setObject("sellProject", "'"+building.getSellProject().getId().toString()+"'");
				params.setObject("buildUnit", null);
				params.setObject("building", building);
			}else if (obj instanceof BuildingUnitInfo){ 
				BuildingUnitInfo buildUnit=(BuildingUnitInfo)obj;
				BuildingInfo building=buildUnit.getBuilding();
				params.setObject("sellProject", "'"+building.getSellProject().getId().toString()+"'");
				params.setObject("buildUnit", buildUnit);
				params.setObject("building", building);
			}
			query();
		}
	}
	public void actionAttachment_actionPerformed(ActionEvent e)throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("roomId", row.getUserObject().toString());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SHEAttachBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		
		queryAttachList();
	}
	protected void tableClicked(KDTMouseEvent e,KDTable table) throws BOSException{
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
			if(table.getColumnKey(e.getColIndex()).equals("attach")) {
				String id=((SHEAttachBillEntryInfo)table.getRow(e.getRowIndex()).getUserObject()).getId().toString();
				AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		        boolean isEdit = false;
		        AttachmentUIContextInfo info = new AttachmentUIContextInfo();
		        info.setBoID(id);
		        info.setEdit(isEdit);
		        String multi = (String)getUIContext().get("MultiapproveAttachment");
		        if(multi != null && multi.equals("true")){
		        	acm.showAttachmentListUIByBoIDNoAlready(this, info);
		        }else{
		        	acm.showAttachmentListUIByBoID(this, info);
		        }
			}else{
				String id=((SHEAttachBillEntryInfo)table.getRow(e.getRowIndex()).getUserObject()).getHead().getId().toString();
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("ID", id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SHEAttachBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
				
				queryAttachList();
			}
		}
	}
	protected void tblAttach_tableClicked(KDTMouseEvent e) throws Exception {
		tableClicked(e,this.tblAttach);
	}
	protected void table_tableClicked(KDTMouseEvent e) throws Exception {
		tableClicked(e,(KDTable) e.getSource());
	}
	protected void queryAttachList() throws BOSException{
		this.tblAttach.checkParsed();
		this.tblAttach.removeRows();
		kDTabbedPane1.removeAll();
		
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if(selectRows==null || selectRows.length==0){
			return;
		}
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String roomId=row.getUserObject().toString();
		
		String tranId=null;
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", roomId));
		filter.getFilterItems().add(new FilterItemInfo("isValid", new Boolean(false)));
		view.setFilter(filter);
		
		TransactionCollection trancol=TransactionFactory.getRemoteInstance().getTransactionCollection(view);
		if(trancol.size()>0){
			tranId=trancol.get(0).getId().toString();
		}
		if(tranId!=null){
			SorterItemCollection sort=new SorterItemCollection();
			sort.add(new SorterItemInfo("sellStage"));
			sort.add(new SorterItemInfo("entry.seq"));
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("*");
			sic.add("room.name");
			sic.add("entry.*");
			sic.add("creator.name");
			sic.add("auditor.name");
			view=new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("room.id", roomId));
			filter.getFilterItems().add(new FilterItemInfo("number", tranId));
			view.setFilter(filter);
			view.setSelector(sic);
			view.setSorter(sort);
			SHEAttachBillCollection col=SHEAttachBillFactory.getRemoteInstance().getSHEAttachBillCollection(view);
			if(col.size()>0){
				for(int i=0;i<col.size();i++){
					for(int j=0;j<col.get(i).getEntry().size();j++){
						SHEAttachBillEntryInfo entry=col.get(i).getEntry().get(j);
						IRow addrow=this.tblAttach.addRow();
						addrow.setUserObject(entry);
						addrow.getCell("room").setValue(col.get(i).getRoom().getName());
						addrow.getCell("customer").setValue(col.get(i).getCustomer());
						addrow.getCell("productTypeProperty").setValue(col.get(i).getProductTypeProperty().getAlias());
						addrow.getCell("sellType").setValue(col.get(i).getSellType().getAlias());
						addrow.getCell("sellStage").setValue(col.get(i).getSellStage().getAlias());
						addrow.getCell("property").setValue(entry.getProperty().getAlias());
						addrow.getCell("context").setValue(entry.getContext());
						addrow.getCell("attach").setValue(loadAttachment(entry.getId().toString()));
						addrow.getCell("id").setValue(col.get(i).getId().toString());
						addrow.getCell("state").setValue(col.get(i).getState().getAlias());
						addrow.getCell("creator").setValue(col.get(i).getCreator().getName());
						if(col.get(i).getAuditor()!=null)
							addrow.getCell("auditor").setValue(col.get(i).getAuditor().getName());
						addrow.getCell("createTime").setValue(col.get(i).getCreateTime());
						addrow.getCell("auditTime").setValue(col.get(i).getAuditTime());
					}
				}
				mergerTable(tblAttach,new String[]{"id"},new String[]{"state","room","customer","productTypeProperty","sellType","sellStage","creator","createTime","auditor","auditTime"});
				kDTabbedPane1.add(tblAttach,"正常交易附件清单");
				for(int i=0;i<tblAttach.getColumnCount();i++){
		    		KDTableHelper.autoFitColumnWidth(tblAttach, i);
		    	}
			}
		}
		SorterItemCollection sort=new SorterItemCollection();
		sort.add(new SorterItemInfo("number"));
		sort.add(new SorterItemInfo("sellStage"));
		sort.add(new SorterItemInfo("entry.seq"));
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("room.name");
		sic.add("entry.*");
		sic.add("creator.name");
		sic.add("auditor.name");
		view=new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", roomId));
		if(tranId!=null){
			filter.getFilterItems().add(new FilterItemInfo("number", tranId,CompareType.NOTEQUALS));
		}
		view.setFilter(filter);
		view.setSelector(sic);
		view.setSorter(sort);
		SHEAttachBillCollection col=SHEAttachBillFactory.getRemoteInstance().getSHEAttachBillCollection(view);
		if(col.size()>0){
			Map number=new HashMap();
			List tableList=new ArrayList();
			for(int i=0;i<col.size();i++){
				for(int j=0;j<col.get(i).getEntry().size();j++){
					String tId=col.get(i).getNumber();
					KDTable table=null;
					if(number.containsKey(tId)){
						table=(KDTable) number.get(tId);
					}else{
						table=new KDTable();
						table.checkParsed();
						table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
						table.getStyleAttributes().setLocked(true);
						table.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
					            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
					                try {
					                	table_tableClicked(e);
					                } catch (Exception exc) {
					                    handUIException(exc);
					                } finally {
					                }
					            }
					        });
						IRow headRow=table.addHeadRow();
						for(int k=0;k<this.tblAttach.getColumnCount();k++){
							IColumn colomn=table.addColumn();
							colomn.setKey(this.tblAttach.getColumnKey(k));
							colomn.setWidth(this.tblAttach.getColumn(k).getWidth());
							headRow.getCell(this.tblAttach.getColumnKey(k)).setValue(this.tblAttach.getHeadRow(0).getCell(this.tblAttach.getColumnKey(k)).getValue());
							if(this.tblAttach.getColumnKey(k).equals("id")){
								colomn.getStyleAttributes().setHided(true);
							}
							if(this.tblAttach.getColumnKey(k).equals("attach")){
								colomn.getStyleAttributes().setFontColor(Color.BLUE);
							}
						}
						addCommonMenusToTable(table);
						 
						CRMClientHelper.fmtDate(table, new String[]{"createTime","auditTime"});
						kDTabbedPane1.add(table,"历史交易附件清单");
						
						number.put(tId, table);
						tableList.add(table);
					}
					SHEAttachBillEntryInfo entry=col.get(i).getEntry().get(j);
					IRow addrow=table.addRow();
					addrow.setUserObject(entry);
					addrow.getCell("room").setValue(col.get(i).getRoom().getName());
					addrow.getCell("customer").setValue(col.get(i).getCustomer());
					addrow.getCell("productTypeProperty").setValue(col.get(i).getProductTypeProperty().getAlias());
					addrow.getCell("sellType").setValue(col.get(i).getSellType().getAlias());
					addrow.getCell("sellStage").setValue(col.get(i).getSellStage().getAlias());
					addrow.getCell("property").setValue(entry.getProperty().getAlias());
					addrow.getCell("context").setValue(entry.getContext());
					addrow.getCell("attach").setValue(loadAttachment(entry.getId().toString()));
					addrow.getCell("id").setValue(col.get(i).getId().toString());
					addrow.getCell("state").setValue(col.get(i).getState().getAlias());
					addrow.getCell("creator").setValue(col.get(i).getCreator().getName());
					if(col.get(i).getAuditor()!=null)
						addrow.getCell("auditor").setValue(col.get(i).getAuditor().getName());
					addrow.getCell("createTime").setValue(col.get(i).getCreateTime());
					addrow.getCell("auditTime").setValue(col.get(i).getAuditTime());
				}
			}
			for(int i=0;i<tableList.size();i++){
				mergerTable((KDTable) tableList.get(i),new String[]{"id"},new String[]{"state","room","customer","productTypeProperty","sellType","sellStage","creator","createTime","auditor","auditTime"});
				for(int j=0;j<((KDTable) tableList.get(i)).getColumnCount();j++){
		    		KDTableHelper.autoFitColumnWidth((KDTable) tableList.get(i), j);
		    	}
			}
		}
	}
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)throws Exception {
		queryAttachList();
	}
	protected String loadAttachment(String id) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", id));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("attachment.name");
		sels.add("attachment.size");
		view.setSelector(sels);
		BoAttchAssoCollection col = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		String name="";
		for(int i=0;i<col.size();i++){
			name=name+col.get(i).getAttachment().getName()+"("+col.get(i).getAttachment().getSize()+");";
		}
		return name;
	}
	private void mergerTable(KDTable table,String coloum[],String mergeColoum[]){
		int merger=0;
		for(int i=0;i<table.getRowCount();i++){
			if(i>0){
				boolean isMerge=true;
				for(int j=0;j<coloum.length;j++){
					Object curRow=table.getRow(i).getCell(coloum[j]).getValue();
					Object lastRow=table.getRow(i-1).getCell(coloum[j]).getValue();
					if(!getString(curRow).equals(getString(lastRow))){
						isMerge=false;
						merger=i;
					}
				}
				if(isMerge){
					for(int j=0;j<mergeColoum.length;j++){
						table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum[j]), i, table.getColumnIndex(mergeColoum[j]));
					}
				}
			}
		}
	}
	private String getString(Object value){
		if(value==null) return "";
		if(value!=null&&value.toString().trim().equals("")){
			return "";
		}else{
			return value.toString();
		}
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=(KDTable) kDTabbedPane1.getSelectedComponent();
		if(table==null){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		int[] selectRows = KDTableUtil.getSelectedRows(table);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		IRow row = table.getRow(rowIndex);
		SHEAttachBillEntryInfo info=(SHEAttachBillEntryInfo) row.getUserObject();
		
		FDCClientUtils.checkBillInWorkflow(this, info.getHead().getId().toString());
		
		SHEAttachBillInfo billInfo=SHEAttachBillFactory.getRemoteInstance().getSHEAttachBillInfo(new ObjectUuidPK(info.getHead().getId()));
		FDCBillStateEnum bizState = billInfo.getState();
		
		if (!FDCBillStateEnum.SUBMITTED.equals(bizState)) {
			FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
			SysUtil.abort();
		}
		SHEAttachBillFactory.getRemoteInstance().audit(info.getHead().getId());
		FDCClientUtils.showOprtOK(this);
		queryAttachList();
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=(KDTable) kDTabbedPane1.getSelectedComponent();
		if(table==null){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		int[] selectRows = KDTableUtil.getSelectedRows(table);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		IRow row = table.getRow(rowIndex);
		SHEAttachBillEntryInfo info=(SHEAttachBillEntryInfo) row.getUserObject();
		
		FDCClientUtils.checkBillInWorkflow(this, info.getHead().getId().toString());
		
		SHEAttachBillInfo billInfo=SHEAttachBillFactory.getRemoteInstance().getSHEAttachBillInfo(new ObjectUuidPK(info.getHead().getId()));
		FDCBillStateEnum bizState = billInfo.getState();
		
		if (!FDCBillStateEnum.SUBMITTED.equals(bizState)&&!FDCBillStateEnum.SAVED.equals(bizState)) {
			FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
			SysUtil.abort();
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("ID", info.getHead().getId().toString());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SHEAttachBillEditUI.class.getName(), uiContext, null, OprtState.EDIT);
		uiWindow.show();
		
		queryAttachList();
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=(KDTable) kDTabbedPane1.getSelectedComponent();
		if(table==null){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		int[] selectRows = KDTableUtil.getSelectedRows(table);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		IRow row = table.getRow(rowIndex);
		SHEAttachBillEntryInfo info=(SHEAttachBillEntryInfo) row.getUserObject();
		
		FDCClientUtils.checkBillInWorkflow(this, info.getHead().getId().toString());
		
		SHEAttachBillInfo billInfo=SHEAttachBillFactory.getRemoteInstance().getSHEAttachBillInfo(new ObjectUuidPK(info.getHead().getId()));
		FDCBillStateEnum bizState = billInfo.getState();
		
		if (!FDCBillStateEnum.SUBMITTED.equals(bizState)&&!FDCBillStateEnum.SAVED.equals(bizState)) {
			FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
			SysUtil.abort();
		}
		if(confirmRemove()){
			SHEAttachBillFactory.getRemoteInstance().delete(new ObjectUuidPK(info.getHead().getId()));
			FDCClientUtils.showOprtOK(this);
			queryAttachList();
		}
	}
	protected boolean confirmRemove()
    {
		return MsgBox.isYes(MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
    }
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=(KDTable) kDTabbedPane1.getSelectedComponent();
		if(table==null){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		int[] selectRows = KDTableUtil.getSelectedRows(table);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		IRow row = table.getRow(rowIndex);
		SHEAttachBillEntryInfo info=(SHEAttachBillEntryInfo) row.getUserObject();
		
		FDCClientUtils.checkBillInWorkflow(this, info.getHead().getId().toString());
		
		SHEAttachBillInfo billInfo=SHEAttachBillFactory.getRemoteInstance().getSHEAttachBillInfo(new ObjectUuidPK(info.getHead().getId()));
		FDCBillStateEnum bizState = billInfo.getState();
		if(billInfo.getSourceBillId()!=null){
			FDCMsgBox.showWarning("单据由变更生成，不能进行反审批操作！");
			SysUtil.abort();
		}
		if (!FDCBillStateEnum.AUDITTED.equals(bizState)) {
			FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
			SysUtil.abort();
		}
		SHEAttachBillFactory.getRemoteInstance().unAudit(info.getHead().getId());
		FDCClientUtils.showOprtOK(this);
		queryAttachList();
	}
	public void actionAuditResult_actionPerformed(ActionEvent e)throws Exception {
		KDTable table=(KDTable) kDTabbedPane1.getSelectedComponent();
		if(table==null){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		int[] selectRows = KDTableUtil.getSelectedRows(table);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		IRow row = table.getRow(rowIndex);
		SHEAttachBillEntryInfo info=(SHEAttachBillEntryInfo) row.getUserObject();
		String id=info.getHead().getId().toString();
		MultiApproveUtil.showApproveHis(BOSUuid.read(id), UIModelDialogFactory.class.getName(), this);
	}
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=(KDTable) kDTabbedPane1.getSelectedComponent();
		if(table==null){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		int[] selectRows = KDTableUtil.getSelectedRows(table);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		IRow row = table.getRow(rowIndex);
		SHEAttachBillEntryInfo info=(SHEAttachBillEntryInfo) row.getUserObject();
		String id=info.getHead().getId().toString();
		IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
		ProcessInstInfo processInstInfo = null;
		ProcessInstInfo procInsts[] = service.getProcessInstanceByHoldedObjectId(id);
		int i = 0;
		for(int n = procInsts.length; i < n; i++)
			if(procInsts[i].getState().startsWith("open"))
				processInstInfo = procInsts[i];
		if(processInstInfo == null){
			procInsts = service.getAllProcessInstancesByBizobjId(id);
			if(procInsts == null || procInsts.length <= 0)
				MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_WFHasNotInstance"));
			else
				if(procInsts.length == 1){
					showWorkflowDiagram(procInsts[0]);
				} else{
					UIContext uiContext = new UIContext(this);
					uiContext.put("procInsts", procInsts);
					String className = ProcessRunningListUI.class.getName();
					IUIWindow uiWindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create(className, uiContext);
					uiWindow.show();
				}
		} else{
			showWorkflowDiagram(processInstInfo);
		}
	}
	private void showWorkflowDiagram(ProcessInstInfo processInstInfo)throws Exception{
		UIContext uiContext = new UIContext(this);
		uiContext.put("id", processInstInfo.getProcInstId());
		uiContext.put("processInstInfo", processInstInfo);
		BasicWorkFlowMonitorPanel.Show(uiContext);
	}
}