/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProductTypePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEAttachBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEAttachBillEditUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;

/**
 * output class name
 */
public class RoomSourceReportUI extends AbstractRoomSourceReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomSourceReportUI.class);
    
    /**
     * output class constructor
     */
    public RoomSourceReportUI() throws Exception
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
        		mapPara.put("param",params );
        		
        		Map map = RoomSourceReportFacadeFactory.getRemoteInstance().getTableList(mapPara);
             	return map;
            }
            public void afterExec(Object result)throws Exception{
            	List list = (ArrayList)((Map)result).get("list");
				for(int i=0;i<list.size();i++){
					Map detialMap = (Map)list.get(i);
					IRow row = tblMain.addRow();
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
					
					row.getCell("rgAtt").setUserObject(detialMap.get("rgAttId"));
					row.getCell("qyAtt").setUserObject(detialMap.get("qyAttId"));
					row.getCell("wqAtt").setUserObject(detialMap.get("wqAttId"));
					row.getCell("jfAtt").setUserObject(detialMap.get("jfAttId"));
					
					if(detialMap.get("rgAttId")!=null){
						row.getCell("rgAtt").setValue(Boolean.TRUE);
					}else{
						row.getCell("rgAtt").setValue(Boolean.FALSE);
					}
					if(detialMap.get("qyAttId")!=null){
						row.getCell("qyAtt").setValue(Boolean.TRUE);
					}else{
						row.getCell("qyAtt").setValue(Boolean.FALSE);
					}
					if(detialMap.get("wqAttId")!=null){
						row.getCell("wqAtt").setValue(Boolean.TRUE);
					}else{
						row.getCell("wqAtt").setValue(Boolean.FALSE);
					}
					if(detialMap.get("jfAttId")!=null){
						row.getCell("jfAtt").setValue(Boolean.TRUE);
					}else{
						row.getCell("jfAtt").setValue(Boolean.FALSE);
					}
					
					BigDecimal sellAmount=FDCHelper.toBigDecimal(detialMap.get("sellAmount"));
					BigDecimal backAmount=FDCHelper.toBigDecimal(detialMap.get("backAmount"));
					boolean isColor=false;
					if(row.getCell("sellState").getValue().toString().equals("签约")||
							row.getCell("sellState").getValue().toString().equals("认购")){
						isColor=true;
					}
					if(isColor&&sellAmount.compareTo(backAmount)==0){
						row.getStyleAttributes().setBackground(new Color(128,255,128));
					}
					
					if(detialMap.get("property")!=null&&(ProductTypePropertyEnum.getEnum(detialMap.get("property").toString()).equals(ProductTypePropertyEnum.CW)||ProductTypePropertyEnum.getEnum(detialMap.get("property").toString()).equals(ProductTypePropertyEnum.QT))){
						row.getCell("wqAtt").setValue("X");
						row.getCell("jfAtt").setValue("X");
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
		
		tblMain.getColumn("wqAtt").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		tblMain.getColumn("jfAtt").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
	}
	
	protected void initTable(){
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(1);
        enableExportExcel(tblMain);
        //设置垂直滚动条
        getTableForPrintSetting().setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_SHOW);
        //设置水平滚动条
        getTableForPrintSetting().setScrollStateHorizon(KDTStyleConstants.SCROLL_STATE_SHOW);
        
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

	@Override
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
			if(tblMain.getColumnKey(e.getColIndex()).equals("rgAtt")||tblMain.getColumnKey(e.getColIndex()).equals("qyAtt")
					||tblMain.getColumnKey(e.getColIndex()).equals("wqAtt")||tblMain.getColumnKey(e.getColIndex()).equals("jfAtt")) {
				if(tblMain.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject()==null) return;
				String id=tblMain.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject().toString();
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("ID", id);
				uiContext.put("VR", Boolean.TRUE);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SHEAttachBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
	}
	
}