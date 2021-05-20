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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;


import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.AccActOnLoadBgFacadeFactory;
import com.kingdee.eas.fdc.contract.AccActOnLoadBgGatherFacadeFactory;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.DefaultKDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.BgCtrlTypeEnum;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultInfo;
import com.kingdee.eas.ma.budget.BgItemFactory;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;

/**
 * output class name
 */
public class AccActOnLoadBgGatherUI extends AbstractAccActOnLoadBgGatherUI
{
    private static final Logger logger = CoreUIObject.getLogger(AccActOnLoadBgGatherUI.class);
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    IUIWindow uiWindow=null;
    public AccActOnLoadBgGatherUI() throws Exception
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
		return new AccActOnLoadBgFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return AccActOnLoadBgGatherFacadeFactory.getRemoteInstance();
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
		if(this.getUIContext().get("RPTFilter")!=null){
			this.actionQuery.setVisible(false);
		}
		isOnLoad=false;
		this.query();
    }
	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
		
		tblMain.getColumn("requestAmount").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("payAmount").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("onLoadUnPayAmount").getStyleAttributes().setFontColor(Color.BLUE);
		tblMain.getColumn("requestUnOnLoadAmount").getStyleAttributes().setFontColor(Color.BLUE);
	
		ClientHelper.changeTableNumberFormat(tblMain, new String[]{"bgAmount","requestAmount","payAmount","onLoadUnPayAmount","requestUnOnLoadAmount","canRequestAmount","useRate"});
		
		
		String[] fields=new String[tblMain.getColumnCount()];
		for(int i=0;i<tblMain.getColumnCount();i++){
			fields[i]=tblMain.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(tblMain,fields);
		ClientHelper.getFootRow(tblMain, new String[]{"bgAmount","requestAmount","payAmount","onLoadUnPayAmount","requestUnOnLoadAmount","canRequestAmount"});
	
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				if(o==null) return "0.00%";
				String str = o.toString();
				if (!FDCHelper.isEmpty(str)) {
					if(str.equals("0")){
						return "0.00%";
					}else{
						return str + "%";
					}
					
				}
				return str;
			}
		});
		tblMain.getColumn("useRate").setRenderer(render_scale);
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
                
                tblMain.setRefresh(false);
                
    	        Map bgItemMap=(HashMap)((RptParams)result).getObject("bgItemMap");
    	        Map onLoadUnPayAmountMap=(HashMap)((RptParams)result).getObject("onLoadUnPayAmount");
    	        Map requestUnOnLoadAmountMap=(HashMap)((RptParams)result).getObject("requestUnOnLoadAmount");
    	        
    	        BgCtrlResultCollection bgCtrlResultCol=(BgCtrlResultCollection)((RptParams)result).getObject("bgCtrlResult");
    	        
    			Object[] bgItemObj=((Object[])params.getObject("bgItem"));
    			tblMain.setRowCount(bgItemObj.length);
        		for(int i=0;i<bgItemObj.length;i++){
    	    		BgItemInfo bgItemInfo=(BgItemInfo)bgItemObj[i];
    	    		IRow row=tblMain.addRow();
    				row.getCell("id").setValue(bgItemInfo.getId().toString());
    				row.getCell("name").setValue(bgItemInfo.getName());
    				row.getCell("remark").setValue(bgItemInfo.getDescription());
    				BigDecimal bgAmount=FDCHelper.ZERO;
    				BigDecimal bgAct=FDCHelper.ZERO;
    				BigDecimal balance=FDCHelper.ZERO;
					for (int k = 0; k < bgCtrlResultCol.size(); k++) {
						if(bgItemInfo.getNumber().equals(bgCtrlResultCol.get(k).getItemCombinNumber())){
							row.setUserObject(bgCtrlResultCol.get(k));
							
							bgAmount =bgCtrlResultCol.get(k).getBigDecimal("sumbudgetvalue")==null?FDCHelper.ZERO:bgCtrlResultCol.get(k).getBigDecimal("sumbudgetvalue");
							bgAct =bgCtrlResultCol.get(k).getBigDecimal("sumactualvalue")==null?FDCHelper.ZERO:bgCtrlResultCol.get(k).getBigDecimal("sumactualvalue");
							balance=bgAmount.subtract(bgAct);
							break;
    	        		}
					}
					row.getCell("bgAmount").setValue(bgAmount);
					row.getCell("payAmount").setValue(bgAct);
					row.getCell("useRate").setValue(bgAmount.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:bgAct.divide(bgAmount, BigDecimal.ROUND_HALF_UP,4).multiply(new BigDecimal(100)));
					
					if(bgItemMap.get(bgItemInfo.getNumber())!=null){
						Set comNumber=(HashSet) bgItemMap.get(bgItemInfo.getNumber());
						BigDecimal onLoadUnPayAmount=FDCHelper.ZERO;
						BigDecimal requestUnOnLoadAmount=FDCHelper.ZERO;
						for(int k=0;k<comNumber.toArray().length;k++){
							String comBgItemNumber=comNumber.toArray()[k].toString();
							if(onLoadUnPayAmountMap.get(comBgItemNumber)!=null){
								onLoadUnPayAmount=onLoadUnPayAmount.add((BigDecimal) onLoadUnPayAmountMap.get(comBgItemNumber));
							}
							if(requestUnOnLoadAmountMap.get(comBgItemNumber)!=null){
								requestUnOnLoadAmount=requestUnOnLoadAmount.add((BigDecimal) requestUnOnLoadAmountMap.get(comBgItemNumber));
							}
						}
						row.getCell("onLoadUnPayAmount").setValue(onLoadUnPayAmount);
						row.getCell("requestUnOnLoadAmount").setValue(requestUnOnLoadAmount);
						row.getCell("requestAmount").setValue(onLoadUnPayAmount.add(requestUnOnLoadAmount).add(bgAct));
						row.getCell("canRequestAmount").setValue(balance.subtract(onLoadUnPayAmount));
					}
                }
    	        if(tblMain.getRowCount() > 0){
    	        	tblMain.reLayoutAndPaint();
    	        }else{
    	        	tblMain.repaint();
    	        }
    	        tblMain.setRefresh(true);
            }
        }
        );
        dialog.show();
    	isQuery=false;
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row=this.tblMain.getRow(e.getRowIndex());
			BigDecimal amount=(BigDecimal)row.getCell(e.getColIndex()).getValue();
			if(amount==null||!(amount instanceof BigDecimal)||amount.compareTo(FDCHelper.ZERO)<=0){
				return;
			}
			BgCtrlResultInfo bgCtrlResult=(BgCtrlResultInfo) row.getUserObject();
			String id=row.getCell("id").getValue().toString();
			CostCenterOrgUnitInfo costedDept = (CostCenterOrgUnitInfo) params.getObject("costedDept");
			
			StringBuffer sb=null;
			Date now=FDCCommonServerHelper.getServerTime();
			if(this.tblMain.getColumnKey(e.getColIndex()).equals("payAmount")){
				sb=new StringBuffer();
				if(bgCtrlResult==null||bgCtrlResult.getCtrlType()==null){
					return;
				}
				sb.append(" select distinct payRequest.fid id from T_CON_PayRequestBillBgEntry entry left join T_CON_PayRequestBill payRequest on payRequest.fid=entry.fheadid left join T_CAS_PaymentBill pay on payRequest.fid=pay.FFdcPayReqID ");
				sb.append(" where payRequest.fisBgControl=1 and pay.fbillstatus=15 and entry.fbgItemId='"+id+"'");
				sb.append(" and payRequest.FCostedDeptId='"+costedDept.getId().toString()+"'");
				if(bgCtrlResult.getCtrlType().equals(BgCtrlTypeEnum.PeriodCtrl)){
					sb.append(" and pay.fpayDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(now)))+ "'}");
	    			sb.append(" and pay.fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(now)))+ "'}");
				}else if(bgCtrlResult.getCtrlType().equals(BgCtrlTypeEnum.ThisYearCtrl)){
					sb.append(" and pay.fpayDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(now)))+ "'}");
	    			sb.append(" and pay.fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(now)))+ "'}");
				}
			}else if(this.tblMain.getColumnKey(e.getColIndex()).equals("onLoadUnPayAmount")){
				sb=new StringBuffer();
				sb.append(" select distinct payRequest.fid id from T_CON_PayRequestBillBgEntry entry left join T_CON_PayRequestBill payRequest on payRequest.fid=entry.fheadid ");
				sb.append(" where payRequest.fstate in('3AUDITTING','4AUDITTED') and payRequest.fisBgControl=1 and entry.fbgItemId='"+id+"' and payRequest.FCostedDeptId='"+costedDept.getId().toString()+"'");
				sb.append(" group by payRequest.fid ");
				sb.append(" having sum(entry.frequestAmount-isnull(entry.factPayAmount,0))!=0 ");
			}else if(this.tblMain.getColumnKey(e.getColIndex()).equals("requestUnOnLoadAmount")){
				sb=new StringBuffer();
				sb.append(" select distinct payRequest.fid id from T_CON_PayRequestBillBgEntry entry left join T_CON_PayRequestBill payRequest on payRequest.fid=entry.fheadid ");
				sb.append(" where (payRequest.fhasClosed=1 or payRequest.fisBgControl=0 or payRequest.fstate not in('3AUDITTING','4AUDITTED'))and entry.fbgItemId='"+id+"' and payRequest.FCostedDeptId='"+costedDept.getId().toString()+"'");
				sb.append(" group by payRequest.fid ");
				sb.append(" having sum(entry.frequestAmount-isnull(entry.factPayAmount,0))!=0 ");
			}else if(this.tblMain.getColumnKey(e.getColIndex()).equals("requestAmount")){
				sb=new StringBuffer();
				sb.append(" select distinct t.id from(");
				if(bgCtrlResult==null||bgCtrlResult.getCtrlType()!=null){
					sb.append(" select distinct payRequest.fid id from T_CON_PayRequestBillBgEntry entry left join T_CON_PayRequestBill payRequest on payRequest.fid=entry.fheadid left join T_CAS_PaymentBill pay on payRequest.fid=pay.FFdcPayReqID ");
					sb.append(" where payRequest.fisBgControl=1 and pay.fbillstatus=15 and entry.fbgItemId='"+id+"'");
					sb.append(" and payRequest.FCostedDeptId='"+costedDept.getId().toString()+"'");
					if(bgCtrlResult.getCtrlType().equals(BgCtrlTypeEnum.PeriodCtrl)){
						sb.append(" and pay.fpayDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(now)))+ "'}");
		    			sb.append(" and pay.fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(now)))+ "'}");
					}else if(bgCtrlResult.getCtrlType().equals(BgCtrlTypeEnum.ThisYearCtrl)){
						sb.append(" and pay.fpayDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstYearDate(now)))+ "'}");
		    			sb.append(" and pay.fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastYearDate(now)))+ "'}");
					}
					sb.append(" union all");
				}
				sb.append(" select distinct payRequest.fid id from T_CON_PayRequestBillBgEntry entry left join T_CON_PayRequestBill payRequest on payRequest.fid=entry.fheadid ");
				sb.append(" where payRequest.fstate in('3AUDITTING','4AUDITTED') and payRequest.fisBgControl=1 and entry.fbgItemId='"+id+"' and payRequest.FCostedDeptId='"+costedDept.getId().toString()+"'");
				sb.append(" group by payRequest.fid ");
				sb.append(" having sum(entry.frequestAmount-isnull(entry.factPayAmount,0))!=0 ");
				
				sb.append(" union all");
				sb.append(" select distinct payRequest.fid id from T_CON_PayRequestBillBgEntry entry left join T_CON_PayRequestBill payRequest on payRequest.fid=entry.fheadid ");
				sb.append(" where (payRequest.fhasClosed=1 or payRequest.fisBgControl=0 or payRequest.fstate not in('3AUDITTING','4AUDITTED'))and entry.fbgItemId='"+id+"' and payRequest.FCostedDeptId='"+costedDept.getId().toString()+"'");
				sb.append(" group by payRequest.fid ");
				sb.append(" having sum(entry.frequestAmount-isnull(entry.factPayAmount,0))!=0 )t");
			}
			if(sb!=null){
				if(uiWindow!=null){
					uiWindow.close();
				}
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				RptParams param=new RptParams();
				param.setObject("sb", sb);
				param.setObject("bgItemId", row.getCell("id").getValue().toString());
				uiContext.put("RPTFilter", param);
				uiContext.put("title", row.getCell("name").getValue().toString()+" - "+this.tblMain.getHeadRow(0).getCell(e.getColIndex()).getValue().toString());
				uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(AccActOnLoadBgUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
	}
}