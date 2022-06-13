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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
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
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.CustomerEditUI;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SignManageListUI;
import com.kingdee.eas.fdc.sellhouse.report.PaymentReportUI;
import com.kingdee.eas.fdc.sellhouse.report.SaleScheduleReportFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.report.SaleScheduleReportFilterUI;
import com.kingdee.eas.fdc.tenancy.RevDetailReportFacadeFactory;
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
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RevDetailReportUI extends AbstractRevDetailReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(RevDetailReportUI.class);
    public RevDetailReportUI() throws Exception
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
		return new RevDetailReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return RevDetailReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
	    
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"buildArea","tenancyArea","dealTotal","dealPrice","roomPrice","appAmount","invoiceAmount","actRevAmount","accountRate"});
		CRMClientHelper.fmtDate(tblMain, new String[]{"startDate","endDate"});
		
		tblMain.getColumn("conName").getStyleAttributes().setFontColor(Color.BLUE);
		
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
		this.tblMain.getColumn("accountRate").setRenderer(render_scale);
		mergerTable(tblMain,new String[]{"conId"},new String[]{"sellProject","build","room","buildArea","tenancyArea","conNumber","conName","customer","startDate","endDate","freeDays","dealTotal","dealPrice","roomPrice","remainingDays"});
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
	                   	 if(params.getBoolean("isNeedTotal")&&  conId!=null&&!conId.equals(rs.getString("conId")) ){
	                   		IRow totalrow=tblMain.addRow();
	                   		for(int i=0;i<17;i++){
	                   			totalrow.getCell(i).setValue(tblMain.getRow(totalrow.getRowIndex()-1).getCell(i).getValue());
	                   		}
	                   		totalrow.getCell(17).setValue("�ϼ�");
	                   		totalrow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
	                   		totalrowMap.put(conId, totalrow);
	                   	 }
	                   	 conId=rs.getString("conId");
	                   	 
	                   	 IRow row=tblMain.addRow();
	                   	 ((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
	                   	 
	                   	 int remainingDays=(int) FDCDateHelper.dateDiff("d", (Date) params.getObject("toRDDate"), (Date)row.getCell("endDate").getValue());
	                   	 row.getCell("remainingDays").setValue(remainingDays<0?0:remainingDays);
	                   	 rowMap.put(rs.getString("conId")+rs.getString("mdId"), row);
         	         }
         	         if(tblMain.getRowCount()>0 ){
         	        	 IRow lastTotalrow=tblMain.addRow();
	   	           		 for(int i=0;i<17;i++){
	   	           			 lastTotalrow.getCell(i).setValue(tblMain.getRow(lastTotalrow.getRowIndex()-1).getCell(i).getValue());
	   	           		 }
	   	           		 lastTotalrow.getCell(17).setValue("�ϼ�");
	   	           		 lastTotalrow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
	   	           		 totalrowMap.put(conId, lastTotalrow);
         	         }
               	 
         	         RptRowSet appdaters = (RptRowSet)((RptParams)result).getObject("appdaters");
         	         Date maxAppDate=null;
         	         Date minAppDate=null;
         	         while(appdaters.next()){
         	        	maxAppDate=(Date) appdaters.getObject("maxAppDate");
         	        	minAppDate=(Date) appdaters.getObject("minAppDate");
         	         }
         	         Calendar cal = Calendar.getInstance();
         	         Date addDate=minAppDate;
         	         
         	         if(maxAppDate!=null||minAppDate!=null){
         	        	for(int i=0;i<getMonthDiff(minAppDate,maxAppDate)+1;i++){
            	        	 if(i>0){
            	        		addDate=FDCDateHelper.getNextMonth(addDate);
            	        	 }
            	        	 cal.setTime(addDate);

            	        	 int year  = cal.get(Calendar.YEAR);
            	        	 int month = cal.get(Calendar.MONTH)+1;
           	        	 
            	        	 IColumn column=tblMain.addColumn();
            	        	 column.setKey(year+"Y"+month+"M"+"appDate");
            	        	 column.setWidth(70);
            	        	 int merge=tblMain.getHeadRow(0).getCell(column.getKey()).getColumnIndex();
            	        	 
            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("Ӧ������");
            	        	 CRMClientHelper.fmtDate(tblMain, new String[]{year+"Y"+month+"M"+"appDate"});
            	        	 
            	        	 column=tblMain.addColumn();
            	        	 column.setKey(year+"Y"+month+"M"+"isUnPay");
            	        	 column.setWidth(70);
            	        	 column.getStyleAttributes().setHided(true);
            	        	 
            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("�Ƿ����踶��");
            	        	 
            	        	 column=tblMain.addColumn();
            	        	 column.setKey(year+"Y"+month+"M"+"appAmount");
            	        	 column.setWidth(70);
            	        	 
            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("Ӧ�ս��");
            	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
            	        	 
            	        	 column=tblMain.addColumn();
	           	        	 column.setKey(year+"Y"+month+"M"+"invoiceAmount");
	           	        	 column.setWidth(70);
	           	        	 
	           	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
	           	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("Ʊ�ݽ��");
	           	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
	           	        	 
	           	        	 column=tblMain.addColumn();
            	        	 column.setKey(year+"Y"+month+"M"+"actRevAmount");
            	        	 column.setWidth(70);
            	        	
            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("ʵ�ս��");
            	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
            	        	 
            	        	 column=tblMain.addColumn();
            	        	 column.setKey(year+"Y"+month+"M"+"unRevAmount");
            	        	 column.setWidth(70);
            	        	
            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("δ�ս��");
            	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
            	        	 
            	        	 column=tblMain.addColumn();
            	        	 column.setKey(year+"Y"+month+"M"+"overdueDays");
            	        	 column.setWidth(70);
            	        	
            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("��������");
//            	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
            	        	 
            	        	 column=tblMain.addColumn();
            	        	 column.setKey(year+"Y"+month+"M"+"accountRate");
            	        	 column.setWidth(70);
            	        	
            	        	 tblMain.getHeadRow(0).getCell(column.getKey()).setValue(year+"-"+month);
            	        	 tblMain.getHeadRow(1).getCell(column.getKey()).setValue("�˿������");
            	        	 CRMClientHelper.changeTableNumberFormat(tblMain, column.getKey());
            	        	 
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
            	     		 tblMain.getColumn(column.getKey()).setRenderer(render_scale);
            	        	 
            	        	 tblMain.getHeadMergeManager().mergeBlock(0, merge, 0, merge+7);
            	         }
         	         }
         	         
         	         RptRowSet detailrs = (RptRowSet)((RptParams)result).getObject("detailrs");
        	         while(detailrs.next()){
        	        	 String key=detailrs.getString("conId")+detailrs.getString("mdId");
        	        	 if(rowMap.containsKey(key)){
        	        		IRow row=(IRow) rowMap.get(key);
        	        		int year=detailrs.getInt("appYear");
        	        		int month=detailrs.getInt("appMonth");
        	        		if(row.getCell(year+"Y"+month+"M"+"appAmount")==null){
        	        			continue;
        	        		}
        	        		row.getCell(year+"Y"+month+"M"+"isUnPay").setValue(detailrs.getObject("isUnPay"));
        	        		row.getCell(year+"Y"+month+"M"+"appDate").setValue(detailrs.getObject("appDate"));
        	        		row.getCell(year+"Y"+month+"M"+"appAmount").setValue(detailrs.getBigDecimal("appAmount"));
        	        		row.getCell(year+"Y"+month+"M"+"invoiceAmount").setValue(detailrs.getBigDecimal("invoiceAmount"));
        	        		row.getCell(year+"Y"+month+"M"+"actRevAmount").setValue(detailrs.getBigDecimal("actRevAmount"));
        	        		BigDecimal unRevAmount=FDCHelper.subtract(detailrs.getBigDecimal("appAmount"),detailrs.getBigDecimal("actRevAmount"));
        	        		row.getCell(year+"Y"+month+"M"+"unRevAmount").setValue(unRevAmount);
        	        		row.getCell(year+"Y"+month+"M"+"overdueDays").setValue(detailrs.getBigDecimal("overdueDays"));
        	        		if(detailrs.getBigDecimal("appAmount")!=null&&detailrs.getBigDecimal("appAmount").compareTo(FDCHelper.ZERO)!=0){
        	        			row.getCell(year+"Y"+month+"M"+"accountRate").setValue(FDCHelper.divide(detailrs.getBigDecimal("actRevAmount"),detailrs.getBigDecimal("appAmount"), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        	        		}else{
        	        			row.getCell(year+"Y"+month+"M"+"accountRate").setValue(FDCHelper.ZERO);
        	        		}
        	        		
        	        		row.getCell("appAmount").setValue(FDCHelper.add(row.getCell("appAmount").getValue(), detailrs.getBigDecimal("appAmount")));
        	        		row.getCell("invoiceAmount").setValue(FDCHelper.add(row.getCell("invoiceAmount").getValue(), detailrs.getBigDecimal("invoiceAmount")));
        	        		row.getCell("actRevAmount").setValue(FDCHelper.add(row.getCell("actRevAmount").getValue(), detailrs.getBigDecimal("actRevAmount")));
        	        		row.getCell("unRevAmount").setValue(FDCHelper.add(row.getCell("unRevAmount").getValue(), unRevAmount));
        	        		row.getCell("overdueDays").setValue(FDCHelper.add(row.getCell("overdueDays").getValue(), detailrs.getBigDecimal("overdueDays")));
        	        		if(row.getCell("appAmount").getValue()!=null&&((BigDecimal)(row.getCell("appAmount").getValue())).compareTo(FDCHelper.ZERO)!=0){
        	        			row.getCell("accountRate").setValue(FDCHelper.divide(row.getCell("actRevAmount").getValue(),row.getCell("appAmount").getValue(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        	        		}else{
        	        			row.getCell("accountRate").setValue(FDCHelper.ZERO);
        	        		}
        	        		if(totalrowMap.containsKey(detailrs.getString("conId")) && params.getBoolean("isNeedTotal")){
        	        			IRow totalrow=(IRow) totalrowMap.get(detailrs.getString("conId"));
        	        			totalrow.getCell("appAmount").setValue(FDCHelper.add(totalrow.getCell("appAmount").getValue(), detailrs.getBigDecimal("appAmount")));
        	        			totalrow.getCell("invoiceAmount").setValue(FDCHelper.add(totalrow.getCell("invoiceAmount").getValue(), detailrs.getBigDecimal("invoiceAmount")));
        	        			totalrow.getCell("actRevAmount").setValue(FDCHelper.add(totalrow.getCell("actRevAmount").getValue(), detailrs.getBigDecimal("actRevAmount")));
        	        			totalrow.getCell("unRevAmount").setValue(FDCHelper.add(totalrow.getCell("unRevAmount").getValue(), unRevAmount));
        	        			totalrow.getCell("overdueDays").setValue(FDCHelper.add(totalrow.getCell("overdueDays").getValue(), detailrs.getBigDecimal("overdueDays")));
        	        			if(totalrow.getCell("appAmount").getValue()!=null&&((BigDecimal)(totalrow.getCell("appAmount").getValue())).compareTo(FDCHelper.ZERO)!=0){
        	        				totalrow.getCell("accountRate").setValue(FDCHelper.divide(totalrow.getCell("actRevAmount").getValue(),totalrow.getCell("appAmount").getValue(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
            	        		}else{
            	        			totalrow.getCell("accountRate").setValue(FDCHelper.ZERO);
            	        		}
        	        			
        	        			totalrow.getCell(year+"Y"+month+"M"+"appAmount").setValue(FDCHelper.add(totalrow.getCell(year+"Y"+month+"M"+"appAmount").getValue(), detailrs.getBigDecimal("appAmount")));
        	        			totalrow.getCell(year+"Y"+month+"M"+"invoiceAmount").setValue(FDCHelper.add(totalrow.getCell(year+"Y"+month+"M"+"invoiceAmount").getValue(), detailrs.getBigDecimal("invoiceAmount")));
        	        			totalrow.getCell(year+"Y"+month+"M"+"actRevAmount").setValue(FDCHelper.add(totalrow.getCell(year+"Y"+month+"M"+"actRevAmount").getValue(), detailrs.getBigDecimal("actRevAmount")));
        	        			totalrow.getCell(year+"Y"+month+"M"+"unRevAmount").setValue(FDCHelper.add(totalrow.getCell(year+"Y"+month+"M"+"unRevAmount").getValue(), unRevAmount));
        	        			totalrow.getCell(year+"Y"+month+"M"+"overdueDays").setValue(FDCHelper.add(totalrow.getCell(year+"Y"+month+"M"+"overdueDays").getValue(), detailrs.getBigDecimal("overdueDays")));
        	        			if(totalrow.getCell(year+"Y"+month+"M"+"appAmount").getValue()!=null&&((BigDecimal)(totalrow.getCell(year+"Y"+month+"M"+"appAmount").getValue())).compareTo(FDCHelper.ZERO)!=0){
        	        				totalrow.getCell(year+"Y"+month+"M"+"accountRate").setValue(FDCHelper.divide(totalrow.getCell(year+"Y"+month+"M"+"actRevAmount").getValue(),totalrow.getCell(year+"Y"+month+"M"+"appAmount").getValue(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
            	        		}else{
            	        			totalrow.getCell(year+"Y"+month+"M"+"accountRate").setValue(FDCHelper.ZERO);
            	        		}
        	        		}
        	        		if(detailrs.getBigDecimal("appAmount").compareTo(detailrs.getBigDecimal("actRevAmount"))==0){
        	        			row.getCell(year+"Y"+month+"M"+"appDate").getStyleAttributes().setBackground(Color.GREEN);
        	        			row.getCell(year+"Y"+month+"M"+"appAmount").getStyleAttributes().setBackground(Color.GREEN);
        	        			row.getCell(year+"Y"+month+"M"+"invoiceAmount").getStyleAttributes().setBackground(Color.GREEN);
        	        			row.getCell(year+"Y"+month+"M"+"actRevAmount").getStyleAttributes().setBackground(Color.GREEN);
        	        			row.getCell(year+"Y"+month+"M"+"unRevAmount").getStyleAttributes().setBackground(Color.GREEN);
        	        			row.getCell(year+"Y"+month+"M"+"overdueDays").getStyleAttributes().setBackground(Color.GREEN);
        	        			row.getCell(year+"Y"+month+"M"+"accountRate").getStyleAttributes().setBackground(Color.GREEN);
        	        		}
        	        		if(detailrs.getBigDecimal("appAmount").compareTo(detailrs.getBigDecimal("actRevAmount"))>0){
        	        			row.getCell(year+"Y"+month+"M"+"appDate").getStyleAttributes().setBackground(Color.YELLOW);
        	        			row.getCell(year+"Y"+month+"M"+"appAmount").getStyleAttributes().setBackground(Color.YELLOW);
        	        			row.getCell(year+"Y"+month+"M"+"invoiceAmount").getStyleAttributes().setBackground(Color.YELLOW);
        	        			row.getCell(year+"Y"+month+"M"+"actRevAmount").getStyleAttributes().setBackground(Color.YELLOW);
        	        			row.getCell(year+"Y"+month+"M"+"unRevAmount").getStyleAttributes().setBackground(Color.YELLOW);
        	        			row.getCell(year+"Y"+month+"M"+"overdueDays").getStyleAttributes().setBackground(Color.YELLOW);
        	        			row.getCell(year+"Y"+month+"M"+"accountRate").getStyleAttributes().setBackground(Color.YELLOW);
        	        		}
        	        		Date quitRoomDate=(Date)row.getCell("quitRoomDate").getValue();
        	        		
        	        		Boolean isShow=true;
        	        		if(quitRoomDate!=null){
        	        			cal = Calendar.getInstance();
            	    			cal.setTime(quitRoomDate);
            	    			int quitYear=cal.get(Calendar.YEAR);
            	    			int quitMonth=cal.get(Calendar.MONTH)+1;
            	    			if(!(quitYear>year||quitYear==year&&quitMonth>month)){
            	    				isShow=false;
            	    			}
        	        		}
        	        		if(detailrs.getBigDecimal("appAmount").compareTo(FDCHelper.ZERO)>0
        	        				&&detailrs.getBigDecimal("actRevAmount").compareTo(FDCHelper.ZERO)==0
        	        					&&isShow){
        	        			row.getCell(year+"Y"+month+"M"+"appDate").getStyleAttributes().setBackground(Color.RED);
        	        			row.getCell(year+"Y"+month+"M"+"appAmount").getStyleAttributes().setBackground(Color.RED);
        	        			row.getCell(year+"Y"+month+"M"+"invoiceAmount").getStyleAttributes().setBackground(Color.RED);
        	        			row.getCell(year+"Y"+month+"M"+"actRevAmount").getStyleAttributes().setBackground(Color.RED);
        	        			row.getCell(year+"Y"+month+"M"+"unRevAmount").getStyleAttributes().setBackground(Color.RED);
        	        			row.getCell(year+"Y"+month+"M"+"overdueDays").getStyleAttributes().setBackground(Color.RED);
        	        			row.getCell(year+"Y"+month+"M"+"accountRate").getStyleAttributes().setBackground(Color.RED);
        	        		}
        	        		
        	        		if(detailrs.getBigDecimal("isUnPay")!=null&&detailrs.getBigDecimal("isUnPay").compareTo(new BigDecimal(1))==0){
        	        			row.getCell(year+"Y"+month+"M"+"appDate").getStyleAttributes().setBackground(Color.CYAN);
        	        			row.getCell(year+"Y"+month+"M"+"appAmount").getStyleAttributes().setBackground(Color.CYAN);
        	        			row.getCell(year+"Y"+month+"M"+"invoiceAmount").getStyleAttributes().setBackground(Color.CYAN);
        	        			row.getCell(year+"Y"+month+"M"+"actRevAmount").getStyleAttributes().setBackground(Color.CYAN);
        	        			row.getCell(year+"Y"+month+"M"+"unRevAmount").getStyleAttributes().setBackground(Color.CYAN);
        	        			row.getCell(year+"Y"+month+"M"+"overdueDays").getStyleAttributes().setBackground(Color.CYAN);
        	        			row.getCell(year+"Y"+month+"M"+"accountRate").getStyleAttributes().setBackground(Color.CYAN);
        	        		}
        	        		
        	        		getFootRow(tblMain, new String[]{"appAmount","invoiceAmount","actRevAmount","unRevAmount","overdueDays",year+"Y"+month+"M"+"appAmount",year+"Y"+month+"M"+"invoiceAmount",year+"Y"+month+"M"+"actRevAmount",year+"Y"+month+"M"+"unRevAmount",year+"Y"+month+"M"+"overdueDays"});
        	        		
        	        		KDTFootManager footRowManager = tblMain.getFootManager();
        	        		IRow footRow = footRowManager.getFootRow(0);
        	        		footRow.getCell("overdueDays").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
        	        		footRow.getCell(year+"Y"+month+"M"+"overdueDays").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
        	        	 }
        	         }
        	         
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
            		if(value.indexOf("��")==-1 && value.indexOf("[]")==-1){
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
}