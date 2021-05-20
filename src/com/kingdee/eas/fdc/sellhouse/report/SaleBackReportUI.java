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
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
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
import com.kingdee.bos.ctrl.swing.KDTree;
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
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SignManageListUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SaleBackReportUI extends AbstractSaleBackReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(SaleBackReportUI.class);
    
    /**
     * output class constructor
     */
    public SaleBackReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }

    private boolean isQuery=false;
    private boolean isOnLoad=false;
    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return null;
	}

	public boolean isShowFilter() {
		return false;
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return SaleBackReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
	    
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"dealTotalAmount","sellAmount","backAmount","quitBackAmount"});
		CRMClientHelper.getFootRow(tblMain, new String[]{"amount","unSignAmount","signAmount","dealTotalAmount","sellAmount","backAmount","quitBackAmount"});
	}
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
             	 RptParams rpt = getRemoteInstance().createTempTable(params);
                 RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                 KDTableUtil.setHeader(header, tblMain);
                 
                 RptRowSet roomRS = (RptRowSet)((RptParams)result).getObject("roomRS");
                 RptRowSet purRS = (RptRowSet)((RptParams)result).getObject("purRS");
                 RptRowSet signRs = (RptRowSet)((RptParams)result).getObject("signRs");
                 RptRowSet backRS = (RptRowSet)((RptParams)result).getObject("backRS");
                 RptRowSet quitBackRS = (RptRowSet)((RptParams)result).getObject("quitBackRS");
                 
                 Map map=new HashMap();
                 
                 while(roomRS.next()){
                	 IRow row=tblMain.addRow();
                	 row.getCell("id").setValue(roomRS.getString("id"));
                	 row.getCell("number").setValue(roomRS.getString("number"));
                	 row.getCell("name").setValue(roomRS.getString("name"));
                	 row.getCell("amount").setValue(roomRS.getInt("amount"));
                	 map.put(roomRS.getString("id"), row);
                	 
            	 }
                 while(purRS.next()){
            		 if(map.containsKey(purRS.getString("id"))){
            			 IRow row=(IRow) map.get(purRS.getString("id"));
                    	 row.getCell("unSignAmount").setValue(purRS.getInt("unSignAmount"));
            		 }
            	 }
                 while(signRs.next()){
                	 if(map.containsKey(signRs.getString("id"))){
            			 IRow row=(IRow) map.get(signRs.getString("id"));
                    	 row.getCell("signAmount").setValue(signRs.getInt("signAmount"));
                    	 row.getCell("dealTotalAmount").setValue(signRs.getBigDecimal("dealTotalAmount"));
                    	 row.getCell("sellAmount").setValue(signRs.getBigDecimal("sellAmount"));
            		 }
            	 }
                 while(backRS.next()){
                	 if(map.containsKey(backRS.getString("id"))){
            			 IRow row=(IRow) map.get(backRS.getString("id"));
                    	 row.getCell("backAmount").setValue(backRS.getBigDecimal("backAmount"));
            		 }
            	 }
                 while(quitBackRS.next()){
                	 if(map.containsKey(quitBackRS.getString("id"))){
            			 IRow row=(IRow) map.get(quitBackRS.getString("id"));
                    	 row.getCell("quitBackAmount").setValue(quitBackRS.getBigDecimal("quitBackAmount"));
            		 }
            	 }
     	         tblMain.setRefresh(true);
     	         if(signRs.getRowCount() > 0){
     	        	tblMain.reLayoutAndPaint();
     	         }else{
     	        	tblMain.repaint();
     	         }
             }
        }
        );
        dialog.show();
    	isQuery=false;
	}
	private void refresh() throws Exception {
		query();
	}
	public void onLoad() throws Exception {
		isOnLoad=true;
		setShowDialogOnLoad(false);
		this.actionQuery.setVisible(false);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		isOnLoad=false;
		this.refresh();
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		
	}
}