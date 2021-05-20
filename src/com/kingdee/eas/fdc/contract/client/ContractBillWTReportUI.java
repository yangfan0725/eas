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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillReportFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractBillWTReportFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;

/**
 * output class name
 */
public class ContractBillWTReportUI extends AbstractContractBillWTReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractBillWTReportUI.class);
    
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    public ContractBillWTReportUI() throws Exception
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
		return null;
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return ContractBillWTReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
		
		ClientHelper.changeTableNumberFormat(tblMain, new String[]{"originalAmount","amount","payAmount"});
		FDCHelper.formatTableDate(tblMain, "bizDate");
		FDCHelper.formatTableDate(tblMain, "auditDate");
		
		tblMain.getColumn("number").getStyleAttributes().setFontColor(Color.BLUE);
		ContractBillReportUI.getFootRow(tblMain, new String[]{"amount","payAmount"});
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    	if(treeNode!=null||isThrough){
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
                     
                     Map value = (HashMap)((RptParams)result).getObject("value");
                     Object[] key=(Object[])((RptParams)result).getObject("key");
         	         tblMain.setRefresh(false);
         	         for (int sort = 0;sort < key.length; sort++) { 
         	        	 String contractType = (String) key[sort];
         	        	 IRow addRow=tblMain.addRow();
         	        	 addRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
         	        	 addRow.setTreeLevel(0);
         	        	 addRow.getCell("name").setValue(contractType);
         	        	 List list = (ArrayList) value.get(contractType);
         	        	 BigDecimal amount=FDCHelper.ZERO;
         	        	 BigDecimal payAmount=FDCHelper.ZERO;
         	        	 for(int i=0;i<list.size();i++){
         	        		 IRow row=tblMain.addRow();
         	        		 row.setTreeLevel(1);
         	        		 Object[] rowData=(Object[]) list.get(i);
         	        		 for(int k=0;k<rowData.length;k++){
         	                    row.getCell(k).setValue(rowData[k]);
         	        		 }
         	        		 amount=amount.add(row.getCell("amount").getValue()==null?FDCHelper.ZERO:(BigDecimal)row.getCell("amount").getValue());
         	        		 payAmount=payAmount.add(row.getCell("payAmount").getValue()==null?FDCHelper.ZERO:(BigDecimal)row.getCell("payAmount").getValue());
         	        	 }
         	        	 addRow.getCell("amount").setValue(amount);
         	        	 addRow.getCell("payAmount").setValue(payAmount);
         	         }
         	         tblMain.setRowCount(tblMain.getRowCount());
         	         tblMain.setRefresh(true);
         	         if(tblMain.getRowCount() > 0){
         	        	tblMain.getTreeColumn().setDepth(2);
         	        	tblMain.reLayoutAndPaint();
         	         }else{
         	        	tblMain.repaint();
         	         }
                 }
            }
            );
            dialog.show();
    	}
    	isQuery=false;
	}
	protected void buildOrgTree() throws Exception{
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, this.treeMain, actionOnLoad);
		this.treeMain.setSelectionRow(0);
		this.treeMain.expandRow(0);
	}
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
			}
		return null;
	}
	private void refresh() throws Exception {
    	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
    	if(treeNode!=null){
			Set leafPrjIds = FDCClientHelper.getProjectLeafsOfNode(treeNode);
			String allSpIdStr = FDCTreeHelper.getStringFromSet(leafPrjIds);
			params.setObject("curProject", allSpIdStr);
			query();
		}
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh();
	}
	boolean isThrough=false;
	public void onLoad() throws Exception {
		if(this.getUIContext().get("title")!=null){
			isThrough=true;
		}
		isOnLoad=true;
		setShowDialogOnLoad(false);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		if(!isThrough){
			buildOrgTree();
		}
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		isOnLoad=false;
		
		if(isThrough){
			this.setUITitle(this.getUITitle()+" - "+this.getUIContext().get("title").toString());
			kDTreeView1.setVisible(false);
			query();
		}
		this.actionQuery.setVisible(false);
		this.refresh();
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if(tblMain.getColumnKey(e.getColIndex()).equals("number")){
				String id=(String)this.tblMain.getRow(e.getRowIndex()).getCell("id").getValue();
				if(id!=null){
					UIContext uiContext = new UIContext(this);
					uiContext.put(UIContext.OWNER, this);
					uiContext.put(UIContext.ID, id);
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractWithoutTextEditUI.class.getName(), uiContext, null, OprtState.VIEW);
					uiWindow.show();
				}
			}
		}
	}
}