/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.bibench.platform.ui.util.MsgBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.MarketProjectReportFacadeFactory;
import com.kingdee.eas.fdc.contract.MarketProjectSourceEnum;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.InvoiceTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.report.RoomSourceReportFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.report.RoomSourceReportFilterUI;
import com.kingdee.eas.fdc.sellhouse.report.SaleManReportFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.report.SaleManReportFilterUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;

/**
 * output class name
 */
public class MarketProjectReport1UI extends AbstractMarketProjectReport1UI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketProjectReport1UI.class);
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    public MarketProjectReport1UI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }


    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new MarketProjectReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return MarketProjectReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}
	public void onLoad() throws Exception {
		isOnLoad=true;
    	setShowDialogOnLoad(true);
    	tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,"", SysContext.getSysContext().getCurrentFIUnit().getId().toString(), null, FDCHelper.getActionPK(this.actionOnLoad));
		this.treeMain.setModel(orgTreeModel);
		DefaultKingdeeTreeNode orgRoot = (DefaultKingdeeTreeNode) ((TreeModel) this.treeMain.getModel()).getRoot();
		this.treeMain.setSelectionNode(orgRoot);
		this.treeMain.expandAllNodes(true, orgRoot);
		
		isOnLoad=false;
    }
	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
	}
//	protected void mergerTable(KDTable table,String coloum[],String mergeColoum[]){
//		int merger=0;
//		for(int i=0;i<table.getRowCount();i++){
//			if(i>0){
//				boolean isMerge=true;
//				for(int j=0;j<coloum.length;j++){
//					Object curRow=table.getRow(i).getCell(coloum[j]).getValue();
//					Object lastRow=table.getRow(i-1).getCell(coloum[j]).getValue();
//					if(getString(curRow).equals("")||getString(lastRow).equals("")||!getString(curRow).equals(getString(lastRow))){
//						isMerge=false;
//						merger=i;
//					}
//				}
//				if(isMerge){
//					for(int j=0;j<mergeColoum.length;j++){
//						table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum[j]), i, table.getColumnIndex(mergeColoum[j]));
//					}
//					table.getRow(i).getCell("tel").setValue(null);
//					table.getRow(i).getCell("visit").setValue(null);
//				}
//			}
//		}
//	}
//	private static String getString(Object value){
//		if(value==null) return "";
//		if(value!=null&&value.toString().trim().equals("")){
//			return "";
//		}else{
//			return value.toString();
//		}
//	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
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
            	RptParams resultRpt= getRemoteInstance().query(params);
            	return resultRpt;
            }
            public void afterExec(Object result)throws Exception{
            	tblMain.setRefresh(false);
            	
            	RptParams rpt = getRemoteInstance().createTempTable(params);
                RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                KDTableUtil.setHeader(header, tblMain);
                
    	        RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
    	        tblMain.setRowCount(rs.getRowCount());
    	        KDTableUtil.insertRows(rs, 0, tblMain);
    	        if(rs.getRowCount() > 0){
    	        	tblMain.reLayoutAndPaint();
    	        }else{
    	        	tblMain.repaint();
    	        }
    	        tblMain.setRefresh(true);
    	        
    	        CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"subAmount","amount","conAmount","payAmount","unPayAmount","conMarketAmount"});
    	        CRMClientHelper.fmtDate(tblMain, "bizDate");
    	        CRMClientHelper.fmtDate(tblMain, "auditTime");
    			CRMClientHelper.fmtDate(tblMain, "conAuditTime");
    			CRMClientHelper.fmtDate(tblMain, "conBizDate");
    			
    			KDCheckBox box = new KDCheckBox(); 
    			KDTDefaultCellEditor editor = new KDTDefaultCellEditor(box);
    			tblMain.getColumn("isSelect").setEditor(editor);
    			tblMain.getColumn("isSelect").getStyleAttributes().setLocked(false);
    			
    			for(int i=0;i<tblMain.getRowCount();i++){
    				tblMain.getRow(i).getCell("isSelect").setValue(Boolean.FALSE);
    				if(tblMain.getRow(i).getCell("type").getValue()==null||(tblMain.getRow(i).getCell("type").getValue()!=null&&(tblMain.getRow(i).getCell("type").getValue().toString().equals("合同")||tblMain.getRow(i).getCell("type").getValue().toString().equals("记账单")))){
    					tblMain.getRow(i).getCell("isSelect").getStyleAttributes().setLocked(true);
    					tblMain.getRow(i).getStyleAttributes().setBackground(new Color(192,192,192));
    				}
    				if(tblMain.getRow(i).getCell("subAmount").getValue()!=null&&((BigDecimal)tblMain.getRow(i).getCell("subAmount").getValue()).compareTo(FDCHelper.ZERO)<=0){
    					tblMain.getRow(i).getCell("isSelect").getStyleAttributes().setLocked(true);
    					tblMain.getRow(i).getStyleAttributes().setBackground(new Color(192,192,192));
    				}
    				if(tblMain.getRow(i).getCell("isSub").getValue()!=null&&((BigDecimal)tblMain.getRow(i).getCell("isSub").getValue()).compareTo(new BigDecimal(1))==0){
    					tblMain.getRow(i).getCell("isSelect").getStyleAttributes().setLocked(true);
    					tblMain.getRow(i).getStyleAttributes().setBackground(new Color(192,192,192));
    				}
    			}
    			
    			tblMain.getColumn("conNumber").getStyleAttributes().setFontColor(Color.BLUE);
    			tblMain.getColumn("number").getStyleAttributes().setFontColor(Color.BLUE);
//    			String[] fields=new String[tblMain.getColumnCount()];
//    			for(int i=0;i<tblMain.getColumnCount();i++){
//    				fields[i]=tblMain.getColumnKey(i);
//    			}
//    			KDTableHelper.setSortedColumn(tblMain,fields);
    			
    			CRMClientHelper.getFootRow(tblMain, new String[]{"conAmount","payAmount","unPayAmount"});
//    			BigDecimal roomArea=tblMain.getFootRow(0).getCell("roomArea").getValue()==null?FDCHelper.ZERO:(BigDecimal)tblMain.getFootRow(0).getCell("roomArea").getValue();
//    			BigDecimal buildArea=tblMain.getFootRow(0).getCell("buildArea").getValue()==null?FDCHelper.ZERO:(BigDecimal)tblMain.getFootRow(0).getCell("buildArea").getValue();
//    			BigDecimal account=tblMain.getFootRow(0).getCell("account").getValue()==null?FDCHelper.ZERO:(BigDecimal)tblMain.getFootRow(0).getCell("account").getValue();
//    			BigDecimal revAccount=tblMain.getFootRow(0).getCell("revAccount").getValue()==null?FDCHelper.ZERO:(BigDecimal)tblMain.getFootRow(0).getCell("revAccount").getValue();
//    			tblMain.getFootRow(0).getCell("buildPrice").setValue(buildArea.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:account.divide(buildArea, 2, BigDecimal.ROUND_HALF_UP));
//    			tblMain.getFootRow(0).getCell("roomPrice").setValue(roomArea.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:account.divide(roomArea, 2, BigDecimal.ROUND_HALF_UP));
//    			tblMain.getFootRow(0).getCell("revAccountRate").setValue(account.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:revAccount.divide(account, 2, BigDecimal.ROUND_HALF_UP));
//    			
//    			tblMain.getColumn("amount").getStyleAttributes().setFontColor(Color.BLUE);
    			
    			
    			tblMain.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
    	    	tblMain.getGroupManager().setGroup(true);
    	    	tblMain.getColumn("number").setGroup(true);
    	    	tblMain.getColumn("auditTime").setGroup(true);
    	    	tblMain.getColumn("name").setGroup(true);
    	    	tblMain.getColumn("costAccount").setGroup(true);
    	    	tblMain.getColumn("amount").setGroup(true);
    	    	tblMain.getColumn("subAmount").setGroup(true);
    	    	
    	    	tblMain.getColumn("type").setGroup(true);
    	    	tblMain.getColumn("conHasSettled").setGroup(true);
    	    	tblMain.getColumn("conAuditTime").setGroup(true);
    	    	tblMain.getColumn("isTimeOut").setGroup(true);
    	    	tblMain.getColumn("conNumber").setGroup(true);
    	    	tblMain.getColumn("conName").setGroup(true);
    	    	tblMain.getColumn("conPartB").setGroup(true);
    	    	tblMain.getColumn("conAmount").setGroup(true);
    	    	tblMain.getColumn("payAmount").setGroup(true);
    	    	tblMain.getColumn("unPayAmount").setGroup(true);
    	    	tblMain.getGroupManager().group();
    	    	
    			tblMain.getColumn("source").setRenderer(new ObjectValueRender(){
    				public String getText(Object obj) {
    					if(obj instanceof String){
    						String info = (String)obj;
    						if(MarketProjectSourceEnum.getEnum(info)==null){
    							return "";
    						}else{
    							return InvoiceTypeEnum.getEnum(info).getAlias();
    						}
    					}
    					return super.getText(obj);
    				}
    			});
            }
        }
        );
        dialog.show();
        isQuery=false;
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if(obj!=null){
				params.setObject("org", ((OrgStructureInfo)obj).getUnit().getId().toString());
			}else{
				params.setObject("org", null);
			}
			query();
		}
	}


	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("conNumber")){
				String id=(String) this.tblMain.getRow(e.getRowIndex()).getCell("conId").getValue();
				if(id!=null){
					if(BOSUuid.read(id).getType().equals(new ContractBillInfo().getBOSType())){
						UIContext uiContext = new UIContext(this);
						uiContext.put(UIContext.OWNER, this);
						uiContext.put("ID", id);
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
						uiWindow.show();
					}else{
						UIContext uiContext = new UIContext(this);
						uiContext.put(UIContext.OWNER, this);
						uiContext.put("ID", id);
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractWithoutTextEditUI.class.getName(), uiContext, null, OprtState.VIEW);
						uiWindow.show();
					}
				}
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("number")){
				String id=(String) this.tblMain.getRow(e.getRowIndex()).getCell("id").getValue();
				if(id!=null){
					UIContext uiContext = new UIContext(this);
					uiContext.put(UIContext.OWNER, this);
					uiContext.put("ID", id);
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(MarketProjectEditUI.class.getName(), uiContext, null, OprtState.VIEW);
					uiWindow.show();
				}
			}
		}
	}

	protected void btnToZHMP_actionPerformed(ActionEvent e) throws Exception {
		Set entryId=new HashSet();
		for(int i=0;i<tblMain.getRowCount();i++){
			if((Boolean)tblMain.getRow(i).getCell("isSelect").getValue()){
				entryId.add(tblMain.getRow(i).getCell("entryId").getValue().toString());
				
				if(tblMain.getRow(i).getCell("state").getValue()!=null&&!tblMain.getRow(i).getCell("state").getValue().toString().equals("4AUDITTED")){
					FDCMsgBox.showWarning(this,"当前存在未审批完的无文本报销单或保存状态的无文本报销单，不允许提交负立项！");
					return;
				}
			}
		}
		if(entryId.size()==0){
			FDCMsgBox.showWarning(this,"请选择行！");
			return;
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("entryId", entryId);
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if(obj!=null){
				uiContext.put("org", ((OrgStructureInfo)obj).getUnit());
			}else{
				FDCMsgBox.showWarning(this,"请选择左树！");
				return;
			}
		}
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ZHMarketProjectEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	
}