/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.backport.Arrays;
import com.kingdee.bos.util.backport.Collections;
import com.kingdee.eas.base.core.util.KDTableUtil;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.ReturnTenacyRptFacadeFactory;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;

/**
 * output class name
 */
public class ReturnTenancyRptUI extends AbstractReturnTenancyRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(ReturnTenancyRptUI.class);
    private static final String [] columnNames = new String[]{"one","two","three","four","five","six","seven","eight","nine","ten"};
    private static final String [] mergeColumns = new   String[]{"sellProject.name","room.number","area","state","salePrice","saleCustomer","returnyears","startYear","endYear","one","two","three","four","five","six","seven","eight","nine","ten"};    
    private ReturnTenancyFilterUI filterUI = new ReturnTenancyFilterUI();
    private static final ObjectValueRender percentRender = getPercent();
    
    /**
     * output class constructor
     */
    public ReturnTenancyRptUI() throws Exception
    {
        super();
        //setShowDialogOnLoad(false);
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionXunTongFeed_actionPerformed
     */
    public void actionXunTongFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionXunTongFeed_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionChart_actionPerformed
     */
    public void actionChart_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionChart_actionPerformed(e);
    }
    
    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		tblMain.getColumn("one").getStyleAttributes().setHided(false);
    }

	@Override
	protected RptParams getParamsForInit() {
		// TODO Auto-generated method stub
		RptParams params = new RptParams();
		params.setObject("startDate", new Date());
		params.setObject("endDate", new Date());
		return params;
	}

	@Override
	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		// TODO Auto-generated method stub
		if(filterUI == null){
			filterUI = new ReturnTenancyFilterUI();
		}
		return filterUI;
	}

	@Override
	protected ICommRptBase getRemoteInstance() throws BOSException {
		// TODO Auto-generated method stub
		return ReturnTenacyRptFacadeFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getTableForPrintSetting() {
		// TODO Auto-generated method stub
		return tblMain;
	}

	@Override
	protected void query() {
		
		
		this.tblMain.removeRows();
		this.tblMain.checkParsed();
		
		// TODO Auto-generated method stub
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
			    if(result == null ){
			    	return ;
			    }
			    List<String> amtColumns = new ArrayList<String>();
			    List<String> totalColumns = new ArrayList<String>();
			    tblMain.checkParsed();
				int bIndex = tblMain.getColumn("signEndYear").getColumnIndex();
				int mIndex = tblMain.getColumnCount();
			    for(int y=mIndex;y>bIndex;y--){
			    	logger.error(tblMain.getColumn(y-1).getKey());
			    	tblMain.removeColumn(y);
			    }
			    tblMain.refresh();
			   
			    for(String s : columnNames){
//			    	tblMain.getColumn(s).setRenderer(getPercent());
			    	if(!s.equals("one")){
			    		tblMain.getColumn(s).getStyleAttributes().setHided(true);
			    	}
			    	
			    }
			    
			    RptParams r = (RptParams) result;
				int maxYear =  r.getInt("maxYear");
				int minYear =  r.getInt("minYear");
				Set<Integer> allyear = new HashSet<Integer>();
				IColumn c = null;
				int startColIndex = -1;
				for(int x=minYear;x<=maxYear;x++){
					allyear.add(x);
					c = tblMain.addColumn();
					c.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
					c.getStyleAttributes().setNumberFormat("###,###.00");
					c.setKey(x+"_returnAmt");
					amtColumns.add(x+"_returnAmt");
					totalColumns.add(x+"_returnAmt");
					
					startColIndex = c.getColumnIndex();
					tblMain.getHeadRow(0).getCell(startColIndex).setValue(x);
					tblMain.getHeadRow(1).getCell(startColIndex).setValue("返租年租金");
					tblMain.getHeadMergeManager().mergeBlock(1, startColIndex, 2, startColIndex);
					
					c = tblMain.addColumn();
					c.setKey(x+"_rentAmt");
					totalColumns.add(x+"_rentAmt");
					c.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
					c.getStyleAttributes().setNumberFormat("###,###.00");
					tblMain.getHeadRow(1).getCell(c.getColumnIndex()).setValue("租金");
					tblMain.getHeadMergeManager().mergeBlock(1, c.getColumnIndex(), 2, c.getColumnIndex());
					
					c = tblMain.addColumn();
					c.setKey(x+"_diffAmt");
					amtColumns.add(x+"_diffAmt");
					totalColumns.add(x+"_diffAmt");
					c.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
					c.getStyleAttributes().setNumberFormat("###,###.00");
					tblMain.getHeadRow(1).getCell(c.getColumnIndex()).setValue("差额");
					tblMain.getHeadMergeManager().mergeBlock(1, c.getColumnIndex(), 2, c.getColumnIndex());
					//amtColumns.add(x+"_returnAmt");
					
					tblMain.getHeadMergeManager().mergeBlock(0, startColIndex, 0, startColIndex+2);
					
					
					
					
				}
				
				
				c = tblMain.addColumn();
				c.setKey("totalReturn");
				amtColumns.add("totalReturn");
				c.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				c.getStyleAttributes().setNumberFormat("###,###.00");
				tblMain.getHeadRow(1).getCell(c.getColumnIndex()).setValue("返租年租金");
				tblMain.getHeadRow(0).getCell(c.getColumnIndex()).setValue("合计");
				tblMain.getHeadMergeManager().mergeBlock(1, c.getColumnIndex(), 2, c.getColumnIndex());
				startColIndex = c.getColumnIndex();
				
				c = tblMain.addColumn();
				c.setKey("totalRent");
				amtColumns.add("totalRent");
				c.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				c.getStyleAttributes().setNumberFormat("###,###.00");
				tblMain.getHeadRow(1).getCell(c.getColumnIndex()).setValue("租金");
				tblMain.getHeadMergeManager().mergeBlock(1, c.getColumnIndex(), 2, c.getColumnIndex());
				
				c = tblMain.addColumn();
				c.setKey("hjzjhbl");
				amtColumns.add("hjzjhbl");
				tblMain.getHeadRow(1).getCell(c.getColumnIndex()).setValue("合计租金回报率（%）（合计租金/（成交价格/0.95）/5）");
				tblMain.getHeadMergeManager().mergeBlock(1, c.getColumnIndex(), 2, c.getColumnIndex());
				tblMain.getHeadMergeManager().mergeBlock(0, startColIndex, 0, c.getColumnIndex());
				
				c = tblMain.addColumn();
				c.setKey("diffAmt");
				amtColumns.add("diffAmt");
				c.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				c.getStyleAttributes().setNumberFormat("###,###.00");
				tblMain.getHeadRow(1).getCell(c.getColumnIndex()).setValue("差额");
				tblMain.getHeadMergeManager().mergeBlock(1, c.getColumnIndex(), 2, c.getColumnIndex());
				tblMain.getHeadMergeManager().mergeBlock(0, startColIndex, 0, c.getColumnIndex());
				
				
				
				
				//获取基本信息
				RptRowSet baseInfoSet = (RptRowSet) r.getObject("baseInfo");
				Map<String,Map> rateMap = (Map<String, Map>) r.getObject("rateMap");
				Map<String,Map> amtMap = (Map<String, Map>) r.getObject("amtMap");
				Map<String,Map> rentAmtMap = (Map<String, Map>) r.getObject("rentAmtMap");
				IRow row = null;
				String key  = null;
				String columnKey = null;
				String customerKey = null;
				while(baseInfoSet.next()){
					Set<Integer> currCustAllYear = new HashSet<Integer>();
					currCustAllYear.addAll(allyear);
					row  = tblMain.addRow();
					key = baseInfoSet.getString("projectId")+"_"+baseInfoSet.getString("roomId");
					customerKey = key+"_"+baseInfoSet.getString("custName");
					row.getCell("sellProject.id").setValue(baseInfoSet.getString("projectId"));
					row.getCell("sellProject.name").setValue(baseInfoSet.getString("projectName"));
					row.getCell("room.id").setValue(baseInfoSet.getString("roomId"));
					row.getCell("room.number").setValue(baseInfoSet.getString("roomNumber"));
					row.getCell("area").setValue(baseInfoSet.getBigDecimal("area"));
					row.getCell("state").setValue(baseInfoSet.getString("state")!=null?RoomSellStateEnum.getEnum(baseInfoSet.getString("state")).getAlias():"");
					row.getCell("salePrice").setValue(baseInfoSet.getBigDecimal("salePrice"));
					row.getCell("saleCustomer").setValue(baseInfoSet.getString("saleCustomer"));
					row.getCell("signCustomer").setValue(baseInfoSet.getString("custName"));
					row.getCell("signYears").setValue(baseInfoSet.getBigDecimal("signYears")+"");
					row.getCell("signStartYear").setValue(baseInfoSet.getString("startDate"));
					row.getCell("signEndYear").setValue(baseInfoSet.getString("endDate"));
					row.getCell("returnyears").setValue(baseInfoSet.getBigDecimal("returnYears"));
					row.getCell("startYear").setValue(baseInfoSet.getString("rStartDate"));
					row.getCell("endYear").setValue(baseInfoSet.getString("rEndYear"));
					row.getCell("one").setValue(baseInfoSet.getString("strOfRate"));
					
					
					
					if(rentAmtMap.containsKey(customerKey)){//以租金为维度填充
						Map<Integer,BigDecimal[]> rentYearAmt = rentAmtMap.get(customerKey);
						Set<Integer> rentAmtSet = rentAmtMap.get(customerKey).keySet();
						
						Integer curKey = null;
						for(Iterator<Integer> iit = rentAmtSet.iterator();iit.hasNext();){
							BigDecimal returnAmt = FDCHelper.ZERO; 
							curKey = iit.next();
 							BigDecimal[] rentAmts = rentYearAmt.get(curKey);
							row.getCell(tblMain.getColumnIndex(curKey+"_rentAmt")).setValue(rentAmts[0]);
							if(amtMap.containsKey(key) && amtMap.get(key).containsKey(curKey)){
								returnAmt = (BigDecimal) amtMap.get(key).get(curKey);
							}
							row.getCell(tblMain.getColumnIndex(curKey+"_returnAmt")).setValue(returnAmt);
							if(rentAmts[1] != null && returnAmt != null){
								row.getCell(tblMain.getColumnIndex(curKey+"_diffAmt")).setValue(FDCHelper.subtract(rentAmts[1],returnAmt));
							}
							currCustAllYear.remove(curKey);
							row.getCell("totalReturn").setValue(rentAmts[2]==null?FDCHelper.ZERO:rentAmts[2]);
							row.getCell("totalRent").setValue(rentAmts[3]==null?FDCHelper.ZERO:rentAmts[3]);
							row.getCell("diffAmt").setValue(rentAmts[4]==null?FDCHelper.ZERO:rentAmts[4]);
							
							BigDecimal a=FDCHelper.divide(baseInfoSet.getBigDecimal("salePrice"), 0.95, 4, BigDecimal.ROUND_HALF_UP);
							BigDecimal b=FDCHelper.multiply(FDCHelper.divide(row.getCell("totalRent").getValue(), a, 4, BigDecimal.ROUND_HALF_UP), 100);
							row.getCell("hjzjhbl").setValue(FDCHelper.divide(b, 5, 2, BigDecimal.ROUND_HALF_UP)+"%");
							
						}
						
						
						for(Iterator<Integer> iiit = currCustAllYear.iterator();iiit.hasNext();){//没有租金
							BigDecimal returnAmt = FDCHelper.ZERO; 
							curKey = iiit.next();
							row.getCell(tblMain.getColumnIndex(curKey+"_rentAmt")).setValue(FDCHelper.ZERO);
							if(amtMap.containsKey(key) && amtMap.get(key).containsKey(curKey)){
								returnAmt = (BigDecimal) amtMap.get(key).get(curKey);
							}
							row.getCell(tblMain.getColumnIndex(curKey+"_returnAmt")).setValue(returnAmt);
							row.getCell(tblMain.getColumnIndex(curKey+"_diffAmt")).setValue(FDCHelper.subtract(FDCHelper.ZERO,returnAmt));
						}
						
						
					}else{
						for(Iterator<Integer> nit = allyear.iterator();nit.hasNext();){
							Integer curkey = nit.next();
							row.getCell(tblMain.getColumnIndex(curkey+"_rentAmt")).setValue(FDCHelper.ZERO);
						}
					}
					
					//计算有返租信息但是没有租金信息的行
					
					
					
				}
				
				totalColumns.add("totalReturn");
				totalColumns.add("totalRent");
				totalColumns.add("diffAmt");
				
			    String[] totalCols = new String[totalColumns.size()];
			    totalColumns.toArray(totalCols);
				amtColumns.addAll(Arrays.asList(mergeColumns));
				
				mergerTable(tblMain, 
						new String[]{"sellProject.name","room.number"}, 
						amtColumns);
						//new String[]{"sellProject.name","room.number","area","state","salePrice","saleCustomer","returnyears","startYear","endYear","one","two","three","four","five","six","seven","eight","nine","ten"});
				
				FDCTableHelper.generateFootRow(tblMain);
			
				//计算汇总信息
				for(String ct: totalCols){
				 tblMain.getFootRow(0).getCell(ct).setValue(getTotalValue(ct));
				}
				
				
			}

			private BigDecimal getTotalValue(String ct) {
				String key ;
				String prevKey = "";
				BigDecimal totalAmt = FDCHelper.ZERO;
				for(int i=0;i<tblMain.getRowCount();i++){
					IRow r = tblMain.getRow(i);
					key = r.getCell("sellProject.id").getValue().toString()+r.getCell("room.id").getValue().toString();
					if(prevKey.equals(key)&& (ct.contains("_returnAmt") 
							|| ct.contains("totalReturn")
							|| ct.contains("totalRent")
							|| ct.contains("diffAmt")		
							)){
						continue;
					}else{
						totalAmt = totalAmt.add(FDCHelper.toBigDecimal(r.getCell(ct).getValue()));
						prevKey = key;
					}
					
				}
				
				
				// TODO Auto-generated method stub
				return totalAmt;
			}

			public Object exec() throws Exception {
				// TODO Auto-generated method stub
				RptParams queryParam = getQueryDialogUserPanel().getCustomCondition();
				SellProjectInfo prj  = null;
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
				if (node != null  &&  node.getUserObject() instanceof SellProjectInfo) {
					 prj = (SellProjectInfo)node.getUserObject();
					 queryParam.setString("sellProject",prj.getId().toString());
				}
				if(prj == null){
					return null;
				}
				return getRemoteInstance().query(queryParam);
			}});
        dialog.show();
		
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
	
	private static  ObjectValueRender getPercent(){
		IDataFormat dateFormat = new IDataFormat(){

			public String format(Object obj) {
				// TODO Auto-generated method stub
				if(obj != null){
					return obj.toString()+"%";
				}
				return null;
			}};
			
			ObjectValueRender objRender = new ObjectValueRender();
			objRender.setFormat(dateFormat);
		return 	objRender;
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
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		// TODO Auto-generated method stub
		this.query();
	}
	

}