/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRevenueRptFacade;
import com.kingdee.eas.fdc.tenancy.TenancyRevenueRptFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.DefaultKDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;

/**
 * output class name
 */
public class TenancyRevenueRptUI extends AbstractTenancyRevenueRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(TenancyRevenueRptUI.class);
    private boolean isOnload = false;
    private boolean isQuery = false;
    
    /**
     * output class constructor
     */
    public TenancyRevenueRptUI() throws Exception
    {
        super();
    }

    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	isOnload =true;
    	super.onLoad();
    	buildTree();
    	isOnload =false;
    	
    }
    
	@Override
	protected RptParams getParamsForInit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		// TODO Auto-generated method stub
		return new TenancyRevenueFilterUI();
	}

	@Override
	protected ICommRptBase getRemoteInstance() throws BOSException {
		// TODO Auto-generated method stub
		return TenancyRevenueRptFacadeFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getTableForPrintSetting() {
		// TODO Auto-generated method stub
		return this.tblMain;
	}

	@Override
	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		tblMain.checkParsed();
		
		if(isQuery) return;
		isQuery=true;
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.companyTree.getLastSelectedPathComponent();
    	if(treeNode!=null){
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
                 	 RptParams rpt = getRemoteInstance().createTempTable(params);
                     RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                     KDTableUtil.setHeader(header, tblMain);
          	        // tblMain.getGroupManager().setGroup(true);
          	        RptParams r = (RptParams)result;
          	        if(r.getObject("dataList") == null){
          	        	return ;
          	        }
          	        RptRowSet rs =(RptRowSet) r.getObject("dataList");
          	        IRow row= null;
          	        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-mm-dd");
          	      tblMain.getColumn("baseDayPrice").getStyleAttributes().setNumberFormat("###,###.0000000");
          	        while(rs.next()){
          	        	row = tblMain.addRow();
          	        	row.getCell("companyName").setValue(rs.getString("companyName"));
          	        	row.getCell("sellProject").setValue(rs.getString("sellProject"));
          	        	row.getCell("buildingId").setValue(rs.getString("buildingId"));
          	        	row.getCell("productType").setValue(rs.getString("productType"));
          	        	row.getCell("productTypeId").setValue(rs.getString("productTypeId"));
          	        	row.getCell("buildingName").setValue(rs.getString("buildingName"));
          	        	row.getCell("buildingSellArea").setValue(rs.getBigDecimal("buildingSellArea"));
          	        	row.getCell("buildingArea").setValue(rs.getBigDecimal("buildingArea"));
          	        	row.getCell("roomName").setValue(rs.getString("roomName"));
          	        	row.getCell("custName").setValue(rs.getString("custName"));
          	        	row.getCell("tenancyName").setValue(rs.getString("tenancyName"));
          	        	row.getCell("brandCatagory").setValue(rs.getString("brandCatagory"));
          	        	row.getCell("brand").setValue(rs.getString("brand"));
          	        	row.getCell("startDate").setValue(rs.getObject("startDate"));
          	        	row.getCell("endDate").setValue(rs.getObject("endDate"));
          	        	row.getCell("tenancyArea").setValue(rs.getBigDecimal("tenancyArea"));
          	        	row.getCell("leaseCount").setValue(rs.getBigDecimal("leaseCount"));
          	        	row.getCell("freedays").setValue(rs.getBigDecimal("freedays"));
          	        	row.getCell("totalAmt").setValue(rs.getBigDecimal("totalAmt"));
          	        	row.getCell("baseDayPrice").setValue(rs.getBigDecimal("baseDayPrice"));
          	        	row.getCell("realDayPrice").setValue(rs.getBigDecimal("realDayPrice"));
          	        }
          	         
                 }
            }
            );
            dialog.show();
    	}
    	isQuery=false;
	    
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"buildingArea","buildingSellArea","tenancyArea","leaseCount","freedays","totalAmt","baseDayPrice","realDayPrice"});
		CRMClientHelper.fmtDate(tblMain, new String[]{"startDate","endDate"});
		
		
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				if(o==null){
					return null;
				}else{
					String str = o.toString();
					return str + "%";
				}
				
			}
		});
		addRentArea();
		mergerTable(tblMain,new String[]{"productType","buildingName"},new String[]{"companyName","sellProject","productType","buildingName","buildingArea","buildingSellArea","alreayTenArea","tenancyRate"});
		
	}
	
	private void addRentArea(){
		int rowCount = tblMain.getRowCount();
		if(rowCount<1){
			return;
		}
		boolean isNeedFill = false;
		String value =getString(tblMain.getCell(0, "buildingId").getValue()+"_"+tblMain.getCell(0, "productTypeId").getValue());
		BigDecimal tenArea = FDCHelper.toBigDecimal(tblMain.getCell(0, "tenancyArea").getValue());
		int lastIndex = 0;
		String currentBuilding = null;
		BigDecimal currentArea = FDCHelper.ZERO;
		for(int i=1;i<rowCount;i++){
			System.out.println(tblMain.getCell(i, "buildingName").getValue());
			currentBuilding = getString(tblMain.getCell(i, "buildingId").getValue()+"_"+tblMain.getCell(i, "productTypeId").getValue());
			currentArea = FDCHelper.toBigDecimal(tblMain.getCell(i, "tenancyArea").getValue());
			if(currentBuilding.equals(value)){
				tenArea = FDCHelper.add(tenArea, currentArea);
			}else{
				BigDecimal buildingSellArea = FDCHelper.ZERO;
				for(int j=i-1;j>lastIndex;j--){
					tblMain.getCell(j, "alreayTenArea").setValue(tenArea);
					buildingSellArea =  FDCHelper.toBigDecimal(tblMain.getCell(j, "buildingSellArea").getValue());
					if(buildingSellArea.compareTo(FDCHelper.ZERO) !=0){
						tblMain.getCell(j,"tenancyRate").setValue(FDCHelper.multiply(FDCHelper.divide(tenArea, buildingSellArea),FDCHelper.ONE_HUNDRED));
      	        	}
				}
				value = currentBuilding;
				lastIndex =i-1;
				tenArea = currentArea;
			}
			
			if(i==rowCount-1){
				BigDecimal buildingSellArea = FDCHelper.ZERO;
				for(int j=i-1;j>lastIndex;j--){
					tblMain.getCell(j, "alreayTenArea").setValue(tenArea);
					buildingSellArea =  FDCHelper.toBigDecimal(tblMain.getCell(j, "buildingSellArea").getValue());
					if(buildingSellArea.compareTo(FDCHelper.ZERO) !=0){
						tblMain.getCell(j,"tenancyRate").setValue(FDCHelper.multiply(FDCHelper.divide(tenArea, buildingSellArea),FDCHelper.ONE_HUNDRED));
      	        	}
				}
			}
		}
		
//		//只有一个楼栋
//		if(isNeedFill=true){
//			BigDecimal buildingSellArea = FDCHelper.ZERO;
//			for(int j=rowCount-1;j>=0;j--){
//				tblMain.getCell(j, "alreayTenArea").setValue(tenArea);
//				buildingSellArea =  FDCHelper.toBigDecimal(tblMain.getCell(j, "buildingSellArea").getValue());
//				if(buildingSellArea.compareTo(FDCHelper.ZERO) !=0){
//					tblMain.getCell(j,"tenancyRate").setValue(FDCHelper.multiply(FDCHelper.divide(tenArea, buildingSellArea),FDCHelper.ONE_HUNDRED));
//  	        	}
//			}
//		}
	}
	
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.companyTree.getLastSelectedPathComponent();
    	if(treeNode!=null){
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
                 	 RptParams rpt = getRemoteInstance().createTempTable(params);
                     RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                     KDTableUtil.setHeader(header, tblMain);
          	         tblMain.getGroupManager().setGroup(true);
                 }
            }
            );
            dialog.show();
    	}
    	isQuery=false;
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
	protected void buildTree() throws Exception{
		this.companyTree.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.companyTree.expandAllNodes(true, (TreeNode) this.companyTree.getModel().getRoot());
		this.companyTree.setSelectionRow(0);
	}
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
		}
		return null;
	}
	private void refresh() throws Exception {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)companyTree.getLastSelectedPathComponent();
		if(treeNode!=null){
			String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
			params.setObject("sellProject", allSpIdStr);
			query();
		}
	}
	@Override
	protected void companyTree_valueChanged(TreeSelectionEvent e)
			throws Exception {
		this.refresh();
	}

}