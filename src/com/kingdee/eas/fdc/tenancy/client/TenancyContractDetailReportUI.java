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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import ucar.ma2.Range.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyContractRptFacadeFactory;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.fdc.tenancy.TenanycAppAmtInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class TenancyContractDetailReportUI extends AbstractTenancyContractDetailReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(TenancyContractDetailReportUI.class);
    
    private TenancyContractDetailReportFilterUI filterUI = new TenancyContractDetailReportFilterUI();
    
    /**
     * output class constructor
     */
    public TenancyContractDetailReportUI() throws Exception
    {
        super();
        this.tblMain.checkParsed();
    }
    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	this.buildTree();
    	this.tblMain.checkParsed();
    	this.tblMain.getColumn("contract.leaseType").setRenderer(getLeaseType());
    	this.tblMain.getColumn("contract.number").getStyleAttributes().setFontColor(Color.BLUE);
        FDCTableHelper.disableDelete(this.tblMain);
    }
    
    @Override
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
		if(treeNode!=null && treeNode.getUserObject() instanceof SellProjectInfo){
			SellProjectInfo sellProjectInfo = (SellProjectInfo) treeNode.getUserObject();
			filterUI.getUIContext().put("sellProject", sellProjectInfo);
			this.params.setObject("sellProject", sellProjectInfo);
		}
    	this.query();
    }
    
    protected void buildTree() throws Exception{
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionRow(0);
	}

    

	@Override
	protected RptParams getParamsForInit() {
		// TODO Auto-generated method stub
		return new RptParams();
	}

	@Override
	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		if(filterUI == null){
			filterUI = new TenancyContractDetailReportFilterUI();
		}
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
		if(treeNode!=null && treeNode.getUserObject() instanceof SellProjectInfo){
			SellProjectInfo sellProjectInfo = (SellProjectInfo) treeNode.getUserObject();
			filterUI.getUIContext().put("sellProject", sellProjectInfo);
		}
		return filterUI;
	}

	@Override
	protected ICommRptBase getRemoteInstance() throws BOSException {
		return TenancyContractRptFacadeFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getTableForPrintSetting() {
		// TODO Auto-generated method stub
		return this.tblMain;
	}
	
	private static  ObjectValueRender getLeaseType(){
		IDataFormat dateFormat = new IDataFormat(){

			public String format(Object obj) {
				// TODO Auto-generated method stub
				if(obj != null){
					BigDecimal times = FDCHelper.toBigDecimal(obj);
					if(times.compareTo(FDCHelper.ONE)==0){
						return "月";
					}else if(times.compareTo(new BigDecimal("3"))==0){
						return "季度";
					}else if(times.compareTo(new BigDecimal("6"))==0){
						return "半年";
					}else{
					   return "其他";
					}
					
				}
				return null;
			}};
			
			ObjectValueRender objRender = new ObjectValueRender();
			objRender.setFormat(dateFormat);
		return 	objRender;
	}
	
	private void mergerTable(KDTable table,String coloum[],List<String> mergeColoum){
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
					for(int j=0;j<mergeColoum.size();j++){
						table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum.get(j)), i, table.getColumnIndex(mergeColoum.get(j)));
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

	@Override
	protected void query() {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			String allSpIdStr = null;
			if(!StringUtils.isEmpty(FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet()))){
				allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
			}
			
			params.setObject("sellProject", allSpIdStr);
			logger.error(allSpIdStr);
		}
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

				public void afterExec(Object result) throws Exception {
					//填充报表数据
					List<String> mergeDymaticCol = new ArrayList<String>();
					RptParams param = (RptParams) result;
					RptRowSet moneyDefinesRs = (RptRowSet) param.getObject("moneyDefinesRs");
					
					Set<String> alreadyFill = new HashSet<String>();
					
					int columnIndex = tblMain.getColumnIndex("ten.unitPrice");
					
					IColumn col = null;
					String colKey = null;
					int currIndex = -1;
					String lastIndexKey = null;
					while(moneyDefinesRs.next()){
						colKey = "DepositAmount_"+moneyDefinesRs.getString("id"); 
						if(tblMain.getColumn(colKey+"_appAmt") != null){
							mergeDymaticCol.add(colKey+"_appAmt");
							mergeDymaticCol.add(colKey+"_actAmt");
							continue;
						}
						lastIndexKey =colKey+"_actAmt"; 
						col = tblMain.addColumn(columnIndex);
						col.setKey(colKey+"_appAmt");
						col.getStyleAttributes().setNumberFormat("###.00");
						col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
						mergeDymaticCol.add(colKey+"_appAmt");
						tblMain.getHeadRow(2).getCell(col.getColumnIndex()).setValue("应收金额");
						currIndex = col.getColumnIndex();
						
						col = tblMain.addColumn(currIndex+1);
						col.setKey(colKey+"_actAmt");
						col.getStyleAttributes().setNumberFormat("###.00");
						col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
						mergeDymaticCol.add(colKey+"_actAmt");
						tblMain.getHeadRow(2).getCell(col.getColumnIndex()).setValue("实收金额");
						
						tblMain.getHeadRow(0).getCell(currIndex).setValue("保证金/押金");
						tblMain.getHeadRow(1).getCell(currIndex).setValue(moneyDefinesRs.getString("name"));
						tblMain.getHeadMergeManager().mergeBlock(1,currIndex,1,currIndex+1);
					}
					tblMain.getHeadMergeManager().mergeBlock(0,tblMain.getColumnIndex("contract.leaseType")+1,0,tblMain.getColumnIndex("ten.unitPrice")-1);
					tblMain.getColumn("free.endDate").getStyleAttributes().setHided(true);
					tblMain.getHeadRow(2).getCell(tblMain.getColumnIndex("free.endDate")).setValue("免租信息");
					
					RptRowSet rs = (RptRowSet) param.getObject("allAmtRs");
					Map<String,TenanycAppAmtInfo> appMap = (Map<String, TenanycAppAmtInfo>) param.getObject("apAmt");
					
					Map<String,Integer> existsOther = (Map<String, Integer>) param.getObject("existsOther");
					Map<String,TenanycAppAmtInfo> rentMap = (Map<String, TenanycAppAmtInfo>) param.getObject("rentMap");
					Map<String,List<TenanycAppAmtInfo>> depMap = (Map<String, List<TenanycAppAmtInfo>>) param.getObject("depMap");
					
					IRow row = null;
					String key = null;
					while(rs.next()){
						String moneyType = rs.getString("moneyType");
						if((!MoneyTypeEnum.RENTAMOUNT_VALUE.equals(moneyType) 
						 &&!MoneyTypeEnum.DEPOSITAMOUNT_VALUE.equals(moneyType)) 
						 ||(MoneyTypeEnum.RENTAMOUNT_VALUE.equals(moneyType) && existsOther.get(rs.getString("conid")+"other")==null)){
							if(alreadyFill.contains(rs.getString("conid")+rs.getString("mdid"))){
								continue;
							}
							alreadyFill.add(rs.getString("conid")+rs.getString("mdid"));
							row = tblMain.addRow();
							key = rs.getString("conid")+"other";
							row.getCell("sellProject.id").setValue(rs.getString("sellProjectId"));
							row.getCell("moneyDefine.id").setValue(rs.getString("mdid"));
							row.getCell("contract.id").setValue(rs.getString("conid"));
							
							row.getCell("sellProject.name").setValue(rs.getString("sellproject"));
							
							row.getCell("contract.state").setValue(TenancyBillStateEnum.getEnum(rs.getString("constate")).getAlias());
							Date d = (Date) rs.getObject("conbizdate");
							row.getCell("contract.bizDate").setValue(FDCDateHelper.formatDate2(d));
							 d = (Date) rs.getObject("conauditdate");
							row.getCell("contract.auditDate").setValue(FDCDateHelper.formatDate2(d));
							row.getCell("contract.number").setValue(rs.getString("connumber"));
							
							if(rs.getString("roomstate")!=null){
								row.getCell("room.state").setValue(TenancyStateEnum.getEnum(rs.getString("roomstate")).getAlias());
							}
							row.getCell("room.number").setValue(rs.getString("room"));
							
							row.getCell("customer.name").setValue(rs.getString("customer"));
							
							d = (Date) rs.getObject("startdate");
							row.getCell("contract.startDate").setValue(FDCDateHelper.formatDate2(d));
							d = (Date) rs.getObject("enddate");
							row.getCell("contract.endDate").setValue(FDCDateHelper.formatDate2(d));
							row.getCell("contract.leaseDay").setValue(rs.getBigDecimal("leasedays"));
							row.getCell("contract.leaseMonth").setValue(rs.getBigDecimal("leaseMonths"));
							
							
							if(rs.getString("frdesc")!=null){
								row.getCell("free.startDate").setValue(rs.getString("frdesc"));
							}
//							if(rs.getObject("freestartdate")!=null){
//								d = (Date) rs.getObject("freestartdate");
//								row.getCell("free.startDate").setValue(FDCDateHelper.formatDate2(d));
//							}
//							if(rs.getObject("freeenddate")!=null){
//								d = (Date) rs.getObject("freeenddate");
//								row.getCell("free.endDate").setValue(FDCDateHelper.formatDate2(d));
//							}
							row.getCell("free.day").setValue(rs.getBigDecimal("freedays"));
							row.getCell("free.month").setValue(rs.getBigDecimal("freemonths"));
							
							
							row.getCell("contract.leaseType").setValue(rs.getBigDecimal("conLeaseTime"));
							
//							row.getCell("ten.unitPrice").setValue(rs.getBigDecimal("roomprice"));
							row.getCell("contract.unitPrice").setValue(rs.getBigDecimal("dealprice"));
							row.getCell("room.tenancyArea").setValue(rs.getBigDecimal("tenancyarea"));
							
                            if(rentMap != null&& rentMap.get(rs.getString("conid")+"rent") != null){
                            	
                            	TenanycAppAmtInfo rentInfo = rentMap.get(rs.getString("conid")+"rent");
                            	
                            	row.getCell("contract.totalRentAmt").setValue(rs.getBigDecimal("dealPriceA"));
                            	row.getCell("contract.realRentAmt").setValue(rentInfo.getActAmt());
                            	row.getCell("contract.needRentAmt").setValue(rentInfo.getDelayAmt());
                            }
							
                            if(appMap != null){
                            	TenanycAppAmtInfo other = appMap.get(rs.getString("conid")+rs.getString("mdid")+"other");
                            	if(other != null){
                            		row.getCell("other.moneyDefine").setValue(other.getMoneyDefineName());
                            		
                            		row.getCell("other.appAmt").setValue(other.getAppAmt());
                            		row.getCell("other.realAmt").setValue(other.getActAmt());
                            		row.getCell("other.needAmt").setValue(other.getDelayAmt());
                            	}
                            }
							if(depMap != null && depMap.get(rs.getString("conid")+"dep") !=null){
								List<TenanycAppAmtInfo> deps = depMap.get(rs.getString("conid")+"dep");
								String colkey = null;
								for(TenanycAppAmtInfo t:deps){
									colkey = "DepositAmount_"+t.getMoneyTypeId();
									if(row.getCell(colkey+"_appAmt")!=null){
										row.getCell(colkey+"_appAmt").setValue(t.getAppAmt());
									}
									if(row.getCell(colkey+"_actAmt")!=null){
										row.getCell(colkey+"_actAmt").setValue(t.getActAmt());
									}
									
								}
							}
							
						}
						
						
						
					}
					
					String[] fixedArray = new String[]{"sellProject.name","contract.state","room.number","room.state","customer.name",
							"contract.bizDate","contract.auditDate","contract.number","contract.startDate","contract.endDate","contract.leaseDay","contract.leaseMonth",
							"free.startDate","free.endDate","free.day","free.month",
							"contract.leaseType","ten.unitPrice","contract.unitPrice","room.tenancyArea",
							"contract.totalRentAmt","contract.realRentAmt","contract.needRentAmt"};
					mergeDymaticCol.addAll(Arrays.asList(fixedArray));
					
					mergerTable(tblMain,new String[]{"contract.id"},mergeDymaticCol);
					
				}

				public Object exec() throws Exception {
					 tblMain.removeRows();
					 RptParams resultRpt= getRemoteInstance().query(params);
                  	 return resultRpt;
				}
			});
            dialog.show();
    	}
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		// TODO Auto-generated method stub
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("contract.number")){
				String conId=this.tblMain.getRow(e.getRowIndex()).getCell("contract.id").getValue().toString();
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, conId);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(TenancyBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
	}

}