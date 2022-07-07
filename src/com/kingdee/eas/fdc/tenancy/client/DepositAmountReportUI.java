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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.DepositAmountReportFacadeFactory;
import com.kingdee.eas.fdc.tenancy.DepositDealBillCollection;
import com.kingdee.eas.fdc.tenancy.DepositDealBillEntryInfo;
import com.kingdee.eas.fdc.tenancy.DepositDealBillFactory;
import com.kingdee.eas.fdc.tenancy.DepositDealBillInfo;
import com.kingdee.eas.fdc.tenancy.DepositDealTypeEnum;
import com.kingdee.eas.fdc.tenancy.IDepositDealBill;
import com.kingdee.eas.fdc.tenancy.RevDetailReportFacadeFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
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
public class DepositAmountReportUI extends AbstractDepositAmountReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(DepositAmountReportUI.class);
    public DepositAmountReportUI() throws Exception
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
		return new DepositAmountReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return DepositAmountReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
	    
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"dealTotal","appAmount","actAmount","quitAmount","sub"});
		CRMClientHelper.fmtDate(tblMain, new String[]{"startDate","endDate","revDate","quitDate"});
//		
		tblMain.getColumn("conName").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("state").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof String){
					String info = (String)obj;
					if(TenancyBillStateEnum.getEnum(info)==null){
						return "";
					}else{
						return TenancyBillStateEnum.getEnum(info).getAlias();
					}
				}
				return super.getText(obj);
			}
		});
//		ObjectValueRender render_scale = new ObjectValueRender();
//		render_scale.setFormat(new IDataFormat() {
//			public String format(Object o) {
//				if(o==null){
//					return null;
//				}else{
//					String str = o.toString();
//					return str + "%";
//				}
//				
//			}
//		});
//		this.tblMain.getColumn("accountRate").setRenderer(render_scale);
		mergerTable(tblMain,new String[]{"conId"},new String[]{"sellProject","build","room","conNumber","state","conName","customer","startDate","endDate","dealTotal"});
		tblMain.getColumn("state").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		tblMain.getColumn("moneyDefine").getStyleAttributes().setBackground(new Color(225,225,225));
		tblMain.getColumn("appAmount").getStyleAttributes().setBackground(new Color(225,225,225));
		tblMain.getColumn("actAmount").getStyleAttributes().setBackground(new Color(225,225,225));
		tblMain.getColumn("revDate").getStyleAttributes().setBackground(new Color(225,225,225));
	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
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
                     
                     RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rs");
         	         tblMain.setRowCount(rs.getRowCount());
         	         Map rowMap=new HashMap();
         	         Map totalrowMap=new HashMap();
         	         String conId=null;
         	         Date now=FDCCommonServerHelper.getServerTimeStamp();
         	         while(rs.next()){
//	                   	 if(params.getBoolean("isNeedTotal")&&  conId!=null&&!conId.equals(rs.getString("conId")) ){
//	                   		IRow totalrow=tblMain.addRow();
//	                   		for(int i=0;i<17;i++){
//	                   			totalrow.getCell(i).setValue(tblMain.getRow(totalrow.getRowIndex()-1).getCell(i).getValue());
//	                   		}
//	                   		totalrow.getCell(17).setValue("合计");
//	                   		totalrow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//	                   		totalrowMap.put(conId, totalrow);
//	                   	 }
//	                   	 conId=rs.getString("conId");
	                   	 
	                   	 IRow row=tblMain.addRow();
	                   	 ((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
	                   	 if(rs.getString("state").equals("Executing")){
	                   		 row.getStyleAttributes().setBackground(new Color(130,240,130));
	                   	 }
	                   	if(rs.getBigDecimal("isGen").compareTo(new BigDecimal(1))==0){
	                   		row.getCell("isGen").setValue(Boolean.TRUE);
	                   	}else{
	                   		row.getCell("isGen").setValue(Boolean.FALSE);
	                   	}
	                   	if(!SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("900002")){
	                   		tblMain.getColumn("isGen").getStyleAttributes().setHided(true);
	                   	}
//	                   	 int remainingDays=(int) FDCDateHelper.dateDiff("d", (Date) params.getObject("toRDDate"), (Date)row.getCell("endDate").getValue());
//	                   	 row.getCell("remainingDays").setValue(remainingDays<0?0:remainingDays);
//	                   	 rowMap.put(rs.getString("conId")+rs.getString("mdId"), row);
         	         }
//         	         if(tblMain.getRowCount()>0 ){
//         	        	 IRow lastTotalrow=tblMain.addRow();
//	   	           		 for(int i=0;i<17;i++){
//	   	           			 lastTotalrow.getCell(i).setValue(tblMain.getRow(lastTotalrow.getRowIndex()-1).getCell(i).getValue());
//	   	           		 }
//	   	           		 lastTotalrow.getCell(17).setValue("合计");
//	   	           		 lastTotalrow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//	   	           		 totalrowMap.put(conId, lastTotalrow);
//         	         }
               	 
//         	         RptRowSet appdaters = (RptRowSet)((RptParams)result).getObject("appdaters");
//         	         Date maxAppDate=null;
//         	         Date minAppDate=null;
//         	         while(appdaters.next()){
//         	        	maxAppDate=(Date) appdaters.getObject("maxAppDate");
//         	        	minAppDate=(Date) appdaters.getObject("minAppDate");
//         	         }
//         	         Calendar cal = Calendar.getInstance();
//         	         Date addDate=minAppDate;
//         	         
//         	         if(maxAppDate!=null||minAppDate!=null){
//         	        	for(int i=0;i<getMonthDiff(minAppDate,maxAppDate)+1;i++){
//            	        	 if(i>0){
//            	        		addDate=FDCDateHelper.getNextMonth(addDate);
//            	        	 }
//            	        	 cal.setTime(addDate);
//
//            	        	 int year  = cal.get(Calendar.YEAR);
//            	        	 int month = cal.get(Calendar.MONTH)+1;
//           	        	 
//            	        	 IColumn column=tblMain.addColumn();
//            	        	 column.setKey(year+"Y"+month+"M"+"appDate");
//            	        	 column.setWidth(70);
//            	        	 int merge=tblMain.getHeadRow(0).getCell(column.getKey()).getColumnIndex();
//            	        	 
//            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
//            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("应收日期");
//            	        	 CRMClientHelper.fmtDate(tblMain, new String[]{year+"Y"+month+"M"+"appDate"});
//            	        	 
//            	        	 column=tblMain.addColumn();
//            	        	 column.setKey(year+"Y"+month+"M"+"appAmount");
//            	        	 column.setWidth(70);
//            	        	 
//            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
//            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("应收金额");
//            	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
//            	        	 
//            	        	 column=tblMain.addColumn();
//	           	        	 column.setKey(year+"Y"+month+"M"+"invoiceAmount");
//	           	        	 column.setWidth(70);
//	           	        	 
//	           	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
//	           	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("票据金额");
//	           	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
//	           	        	 
//	           	        	 column=tblMain.addColumn();
//            	        	 column.setKey(year+"Y"+month+"M"+"actRevAmount");
//            	        	 column.setWidth(70);
//            	        	
//            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
//            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("实收金额");
//            	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
//            	        	 
//            	        	 column=tblMain.addColumn();
//            	        	 column.setKey(year+"Y"+month+"M"+"unRevAmount");
//            	        	 column.setWidth(70);
//            	        	
//            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
//            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("未收金额");
//            	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
//            	        	 
//            	        	 column=tblMain.addColumn();
//            	        	 column.setKey(year+"Y"+month+"M"+"overdueDays");
//            	        	 column.setWidth(70);
//            	        	
//            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
//            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("过期天数");
////            	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
//            	        	 
//            	        	 column=tblMain.addColumn();
//            	        	 column.setKey(year+"Y"+month+"M"+"accountRate");
//            	        	 column.setWidth(70);
//            	        	
//            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
//            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("账款回收率");
//            	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
//            	        	 
//            	        	 ObjectValueRender render_scale = new ObjectValueRender();
//            	     		 render_scale.setFormat(new IDataFormat() {
//            	     			 public String format(Object o) {
//            	     				 if(o==null){
//            	     					 return null;
//            	     				 }else{
//            	     					 String str = o.toString();
//            	     					 return str + "%";
//            	     				 }
//            	     			 }
//            	     		 });
//            	     		 tblMain.getColumn(column.getKey()).setRenderer(render_scale);
//            	        	 
//            	        	 tblMain.getHeadMergeManager().mergeBlock(0, merge, 0, merge+6);
//            	         }
//         	         }
         	         getFootRow(tblMain, new String[]{"dealTotal","appAmount","actAmount","quitAmount","sub"});
        	         
         	         tblMain.setRefresh(true);
         	         if(rs.getRowCount() > 0){
         	        	tblMain.reLayoutAndPaint();
         	         }else{
         	        	tblMain.repaint();
         	         }
         	        tblMain.getGroupManager().setGroup(true);
                 }
            }
            );
            dialog.show();
    	}
    	isQuery=false;
	}
	public int getMonthDiff(Date start, Date end) {
		if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
		Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);
        
        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
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
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionRow(0);
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
			String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
			params.setObject("sellProject", allSpIdStr);
			query();
		}
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh();
	}
	public void onLoad() throws Exception {
		isOnLoad=true;
		setShowDialogOnLoad(true);
		tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.btnGen.setIcon(EASResource.getIcon("imgTbtn_createcredence"));
		this.btnView.setIcon(EASResource.getIcon("imgTbtn_view"));
		if(!SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("900002")){
			this.actionGen.setVisible(false);
			this.actionView.setVisible(false);
		}
		buildTree();
		isOnLoad=false;
		this.refresh();
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("conName")){
				String conId=this.tblMain.getRow(e.getRowIndex()).getCell("conId").getValue().toString();
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, conId);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TenancyBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
	}
	public void getFootRow(KDTable tblMain,String[] columnName){
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
	public BigDecimal getColumnValueSum(KDTable table,String columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
        	if(table.getRow(i).getCell(columnName).getValue()!=null ){
        		if(table.getRow(i).getStyleAttributes().getBackground().equals(FDCHelper.KDTABLE_TOTAL_BG_COLOR)){
        			continue;
        		}
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

	public void actionGen_actionPerformed(ActionEvent e) throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		Map billMap=new HashMap();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("orgUnit.*");
		
		for (int i = 0; i < selectRows.length; i++) {
			IRow row = this.tblMain.getRow(selectRows[i]);
			BigDecimal sub=(BigDecimal) row.getCell("sub").getValue();
			Boolean isGen=(Boolean) row.getCell("isGen").getValue();
			if(sub.compareTo(FDCHelper.ZERO)<=0||isGen)continue;
			String conId=row.getCell("conId").getValue().toString();
			String srcId=row.getCell("id").getValue().toString();
			DepositDealBillInfo info=null;
			if(billMap.containsKey(conId)){
				info=(DepositDealBillInfo) billMap.get(conId);
			}else{
				info=new DepositDealBillInfo();
				info.setType(DepositDealTypeEnum.QUITYJ);
				
				TenancyBillInfo ten=TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(conId),sic);
				info.setTenancyBill(ten);
				info.setOrgUnit(ten.getOrgUnit());
				
				Date now=new Date();
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
		        
				ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
				info.setNumber(iCodingRuleManager.getNumber(info, info.getOrgUnit().getId().toString()));
				info.setName(ten.getTenCustomerDes()+" "+DepositDealTypeEnum.QUITYJ.getAlias()+" "+sdf.format(now));
	      		
				billMap.put(conId, info);
			}
			
			DepositDealBillEntryInfo entryInfo=new DepositDealBillEntryInfo();
			entryInfo.setSrcId(srcId);
			entryInfo.setAmount(sub);
			
			info.getEntry().add(entryInfo);
		}
		if(billMap.size()==0){
			FDCMsgBox.showInfo(this,"无退款申请单生成！");
			return;
		}
		Set set=billMap.keySet();
		Iterator iterator=set.iterator();
		IDepositDealBill iDepositDealBill=DepositDealBillFactory.getRemoteInstance();
		while(iterator.hasNext()){
			DepositDealBillInfo info=(DepositDealBillInfo) billMap.get(iterator.next());
			IObjectPK pk=iDepositDealBill.submit(info);
			iDepositDealBill.audit(BOSUuid.read(pk.toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.query();
	}

	IUIWindow uiWindow=null;
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell("depositBillId").getValue();
		if(id==null){
			FDCMsgBox.showInfo(this,"无退款申请单！");
			return;
		}
		if(uiWindow!=null)uiWindow.close();
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("ID", id);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(DepositDealBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}

}