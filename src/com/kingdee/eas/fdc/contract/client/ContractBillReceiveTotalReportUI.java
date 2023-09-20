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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.VerticalAlignment;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaUtil;
import com.kingdee.eas.fdc.contract.ContractBillReceiveCollection;
import com.kingdee.eas.fdc.contract.ContractBillReceiveFactory;
import com.kingdee.eas.fdc.contract.ContractBillReceiveReportFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractBillReceiveTotalReportFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractRecBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractRecBillEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.DefaultKDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ContractBillReceiveTotalReportUI extends AbstractContractBillReceiveTotalReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractBillReceiveTotalReportUI.class);
    public ContractBillReceiveTotalReportUI() throws Exception
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

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return ContractBillReceiveTotalReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}
	public void onLoad() throws Exception {
		isOnLoad=true;
    	setShowDialogOnLoad(false);
    	tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionQuery.setVisible(false);
		
		isOnLoad=false;
    }
	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery||isOnLoad) return;
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
            	params.setObject("orgUnit.longNumber", SysContext.getSysContext().getCurrentOrgUnit().getLongNumber());
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
    	        String companyId=null;
    	        Map comMap=new HashMap();
    	        while(rs.next()){
    	        	IRow row=tblMain.addRow();
    	        	((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
    	        	
    	        	String comCompanyId=row.getCell("companyId").getValue().toString();
    	        	
    	        	BigDecimal contractAmount=(BigDecimal) row.getCell("contractAmount").getValue();
    	        	BigDecimal recAmount=(BigDecimal) row.getCell("recAmount").getValue();
    	        	BigDecimal payContractAmount=(BigDecimal) row.getCell("payContractAmount").getValue();
    	        	BigDecimal payReqAmountAuditting=(BigDecimal) row.getCell("payReqAmountAuditting").getValue();
    	        	BigDecimal payReqAmountAuditted=(BigDecimal) row.getCell("payReqAmountAuditted").getValue();
    	        	BigDecimal payAmount=(BigDecimal) row.getCell("payAmount").getValue();
    	        	BigDecimal sub1=(BigDecimal) row.getCell("sub1").getValue();
    	        	BigDecimal sub2=(BigDecimal) row.getCell("sub2").getValue();
    	        	
    	        	Map map=null;
    	        	if(comMap.containsKey(comCompanyId)){
    	        		map=(Map) comMap.get(comCompanyId);
    	        	}else{
    	        		map=new HashMap();
    	        		comMap.put(comCompanyId, map);
    	        	}
    	        	map.put("company", row.getCell("company").getValue());
    	        	map.put("contractAmount", FDCHelper.add(map.get("contractAmount"), contractAmount));
	        		map.put("recAmount", FDCHelper.add(map.get("recAmount"), recAmount));
	        		map.put("payContractAmount", FDCHelper.add(map.get("payContractAmount"), payContractAmount));
	        		map.put("payReqAmountAuditting", FDCHelper.add(map.get("payReqAmountAuditting"), payReqAmountAuditting));
	        		map.put("payReqAmountAuditted", FDCHelper.add(map.get("payReqAmountAuditted"), payReqAmountAuditted));
	        		map.put("payAmount", FDCHelper.add(map.get("payAmount"), payAmount));
	        		map.put("sub1", FDCHelper.add(map.get("sub1"), sub1));
	        		map.put("sub2", FDCHelper.add(map.get("sub2"), sub2));
    	        	
    	        	if(companyId!=null&&!companyId.equals(comCompanyId)){
    	        		IRow totalRow=tblMain.addRow(row.getRowIndex());
    	        		Map preMap=(Map) comMap.get(companyId);
    	        		totalRow.getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
    	        		totalRow.getCell("companyId").setValue(companyId);
    	        		if(preMap!=null){
    	        			totalRow.getCell("company").setValue(preMap.get("company"));
        	        		totalRow.getCell("moneyDefine").setValue("小计");
        	        		totalRow.getCell("contractAmount").setValue(preMap.get("contractAmount"));
        	        		totalRow.getCell("recAmount").setValue(preMap.get("recAmount"));
        	        		totalRow.getCell("payContractAmount").setValue(preMap.get("payContractAmount"));
        	        		totalRow.getCell("payReqAmountAuditting").setValue(preMap.get("payReqAmountAuditting"));
        	        		totalRow.getCell("payReqAmountAuditted").setValue(preMap.get("payReqAmountAuditted"));
        	        		totalRow.getCell("payAmount").setValue(preMap.get("payAmount"));
        	        		totalRow.getCell("sub1").setValue(preMap.get("sub1"));
        	        		totalRow.getCell("sub2").setValue(preMap.get("sub2"));
    	        		}
    	        	}
    	        	companyId=comCompanyId;
    	        }
    	       
    	        Map preMap=(Map) comMap.get(companyId);
    	    	if(preMap!=null){
    	    		 IRow totalRow=tblMain.addRow();
    	    		 totalRow.getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
    	        	 totalRow.getCell("companyId").setValue(companyId);
    	        	 totalRow.getCell("company").setValue(preMap.get("company"));
    	        	 totalRow.getCell("moneyDefine").setValue("小计");
    	        	 totalRow.getCell("contractAmount").setValue(preMap.get("contractAmount"));
    	        	 totalRow.getCell("recAmount").setValue(preMap.get("recAmount"));
    	        	 totalRow.getCell("payContractAmount").setValue(preMap.get("payContractAmount"));
    	        	 totalRow.getCell("payReqAmountAuditting").setValue(preMap.get("payReqAmountAuditting"));
    	        	 totalRow.getCell("payReqAmountAuditted").setValue(preMap.get("payReqAmountAuditted"));
    	        	 totalRow.getCell("payAmount").setValue(preMap.get("payAmount"));
    	        	 totalRow.getCell("sub1").setValue(preMap.get("sub1"));
    	        	 totalRow.getCell("sub2").setValue(preMap.get("sub2"));
    	    	}
    	        if(rs.getRowCount() > 0){
    	        	tblMain.reLayoutAndPaint();
    	        }else{
    	        	tblMain.repaint();
    	        }
    	        tblMain.setRefresh(true);
    	        
    	        String[] sum=new String[]{"contractAmount","recAmount","payContractAmount","payReqAmountAuditting","payReqAmountAuditted","payAmount","sub1","sub2"};
    	        CRMClientHelper.changeTableNumberFormat(tblMain,sum);
    	        getFootRow(tblMain, sum);
    	        
    	        tblMain.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
    	    	tblMain.getGroupManager().setGroup(true);
    	        tblMain.getColumn("company").setGroup(true);
    	        
    	        tblMain.getColumn("moneyDefine").getStyleAttributes().setFontColor(Color.BLUE);
            }
        }
        );
        dialog.show();
        isQuery=false;
	}
	public  void getFootRow(KDTable tblMain,String[] columnName){
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        if(footRowManager == null)
        {
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(tblMain);
            footRowManager.addFootView();
            tblMain.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
            tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else
        {
            footRow = footRowManager.getFootRow(0);
        }
        int columnCount = tblMain.getColumnCount();
        for(int c = 0; c < columnCount; c++)
        {
            String fieldName = tblMain.getColumn(c).getKey();
            for(int i = 0; i < columnName.length; i++)
            {
                String colName = (String)columnName[i];
                if(colName.equalsIgnoreCase(fieldName))
                {
                    ICell cell = footRow.getCell(c);
                    cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
                    cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
                    cell.setValue(getColumnValueSum(tblMain,colName));
                }
            }

        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
	}
	 public  BigDecimal getColumnValueSum(KDTable table,String columnName) {
	    	BigDecimal sum = new BigDecimal(0);
	        for(int i=0;i<table.getRowCount();i++){
	        	if(table.getRow(i).getCell(columnName).getValue()!=null &&table.getRow(i).getStyleAttributes().getBackground().equals(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR)){
	        		if( table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal)
	            		sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
	            	else if(table.getRow(i).getCell(columnName).getValue() instanceof String){
	            		String value = (String)table.getRow(i).getCell(columnName).getValue();
	            		if(value.indexOf("零")==-1 && value.indexOf("[]")==-1){
	            			value = value.replaceAll(",", "");
	                		sum = sum.add(new BigDecimal(value));
	            		}
	            	}
	            	else if(table.getRow(i).getCell(columnName).getValue() instanceof Integer){
	            		String value = table.getRow(i).getCell(columnName).getValue().toString();
	            		sum = sum.add(new BigDecimal(value));
	            	}
	        	}
	        }
	        return sum;
	    }
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("moneyDefine")){
				String companyId=(String) this.tblMain.getRow(e.getRowIndex()).getCell("companyId").getValue();
				String moneyDefineId=(String) this.tblMain.getRow(e.getRowIndex()).getCell("moneyDefineId").getValue();
				UIContext uiContext = new UIContext(this);
				RptParams param=new RptParams();
				param.setObject("companyId", companyId);
				param.setObject("moneyDefineId", moneyDefineId);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("RPTFilter", param);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractBillReceiveReportUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
	}

}