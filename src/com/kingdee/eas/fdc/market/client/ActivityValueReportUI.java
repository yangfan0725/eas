/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.market.client.TableUtils;
import com.kingdee.eas.fdc.market.ActivityValueReportFacadeFactory;
import com.kingdee.eas.fdc.market.ActivityValueReportFactory;
import com.kingdee.eas.fdc.market.ActivityValueReportInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.report.RoomSourceReportUI;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ActivityValueReportUI extends AbstractActivityValueReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(ActivityValueReportUI.class);
    public static String quickName = null;//��������
    public static String quickDes = null;//��������
    /**
     * output class constructor
     */
    public ActivityValueReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(1);
        enableExportExcel(tblMain);
        disposeUIWindow();
        btnChart.setVisible(true);
    }
    
    
    public void onShow()throws Exception
    {
        super.onShow();
        tblMain.setEditable(false);
        tblMain.getViewManager().setFreezeView(-1, 7);
//        int gapColumn = 4;
//		int area_cell = 5;
//		int count_cell = 6;
//		int price_cell = 7;
//		int amount_cell = 8;
//		if(tblMain.getRowCount()>0){
//			tblMain.setRowCount(tblMain.getRowCount()+4);
//		}
//		for(int i = 0 ;i<tblMain.getRowCount();i++){
//			IRow row = tblMain.getRow(i);
//			if(tblMain.getCell(i, 3)!=null){
//				String type = (String)tblMain.getCell(i, 3).getValue();//��Ʒ����
//				if(PlanIndexTypeEnum.parking.getAlias().equals(type)){//��λ  ����=���/����
//					for(int k=0;k<7;k++){
//						// ����
//						Number divideValue =(Number)TableUtils.getColumnValue(row,amount_cell+k*gapColumn);
//						Number dividedValue =  (Number)TableUtils.getColumnValue(row,count_cell+k*gapColumn); 
//						if(divideValue!=null&&dividedValue!=null){
//							row.getCell(price_cell+k*gapColumn).setValue(FDCHelper.divide(divideValue,dividedValue ));
//						}
//					}
//				}else{
//					for(int k=0;k<7;k++){
//						// ����
//						Number divideValue =(Number)TableUtils.getColumnValue(row,amount_cell+k*gapColumn);
//						Number dividedValue =  (Number)TableUtils.getColumnValue(row,area_cell+k*gapColumn); 
//						if(divideValue!=null&&dividedValue!=null){
//							row.getCell(price_cell+k*gapColumn).setValue(FDCHelper.divide(divideValue,dividedValue ));
//						}
//					}
//				}
//			}
//		}
    }
    //���úϼ����Լ��ں�
	public static void setTotalRow(KDTable table){
		int business = 0;
		int park = 0;
		int publicBuild = 0;
		KDTMergeManager mm = table.getMergeManager();
		int rowCount = table.getRowCount();
		for(int i=0;i<rowCount;i++){
			String planIndexType = (String)table.getCell(i, 4).getValue();
			if(PlanIndexTypeEnum.business.getAlias().equals(planIndexType)){
				if(business==0){
					business = i;
					IRow row = table.addRow(i);
					rowCount++;
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
					row.getCell(5).setValue("�ϼ�");
					row.getCell(4).setValue(PlanIndexTypeEnum.house.getAlias());
					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
				}
			}else if(PlanIndexTypeEnum.parking.getAlias().equals(planIndexType)){
				if(park==0){
					park = i;
					IRow row = table.addRow(i);
					rowCount++;
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
					row.getCell(5).setValue("�ϼ�");
					row.getCell(4).setValue(PlanIndexTypeEnum.business.getAlias());
					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
				}
			}else if(PlanIndexTypeEnum.publicBuild.getAlias().equals(planIndexType)){
				if(publicBuild==0){
					publicBuild=i;
					IRow row = table.addRow(i);
					rowCount++;
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
					row.getCell(5).setValue("�ϼ�");
					row.getCell(4).setValue(PlanIndexTypeEnum.parking.getAlias());
					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
				}
			}
//			else{
//				if("140�O<".equals(table.getCell(i, "areaRange").getValue().toString())){
//					IRow row = table.addRow(i+1);
//					rowCount++;
//					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//					row.getStyleAttributes().setLocked(true);
//					row.getCell(3).setValue(PlanIndexTypeEnum.house.getAlias());
//					row.getCell(4).setValue(table.getCell(i,4).getValue());
//					row.getCell("areaRange").setValue("С��");
//					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//					row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
//					row.setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
//				}
//			}
			if(i==rowCount-1){
				IRow row = table.addRow(++i);
				rowCount++;
				row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
				row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
				row.getStyleAttributes().setLocked(true);
				row.getCell(5).setValue("�ϼ�");
				row.getCell(4).setValue(PlanIndexTypeEnum.publicBuild.getAlias());
			}
		}
		EnterprisePlanEditUI.mergerTable(table,new String[]{"typename"},new String[]{"typename"});
		mm.mergeBlock(0, 4, business, 4, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(business+1, 4, park, 4, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(park+1, 4, publicBuild, 4, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(publicBuild+1, 4, rowCount-1, 4, KDTMergeManager.SPECIFY_MERGE);
		table.setRowCount(rowCount);
	}
	public static void reSetExpressions(KDTable table){
		int hjColumn = 7;
    	int dynRowBase = 4;
		int houseSubIndex=0;
		int businessSubIndex=0;
		int parkingSubIndex=0;
		int rowCount = table.getRowCount();
		if(table.getRowCount()>0){
			int publicSubIndex=rowCount-1;
			for(int i=0;i<rowCount-1;i++){
				IRow row=table.getRow(i);
				if(row.getCell(dynRowBase).getValue().equals(PlanIndexTypeEnum.house.getAlias()) && isTotalRow(row)){
					houseSubIndex=i;
				}
				if(row.getCell(dynRowBase).getValue().equals(PlanIndexTypeEnum.business.getAlias()) && isTotalRow(row)){
					businessSubIndex=i;
				}
				if(row.getCell(dynRowBase).getValue().equals(PlanIndexTypeEnum.parking.getAlias()) && isTotalRow(row)){
					parkingSubIndex=i;
				}
				
			}
			//סլ
			if(houseSubIndex>0){
				IRow houseRow=table.getRow(houseSubIndex);
				reSetExpressions(table,houseRow,hjColumn,1,houseSubIndex);
			}
			//��ҵ
			if(businessSubIndex>(houseSubIndex+1)){
				IRow businessRow=table.getRow(businessSubIndex);
				reSetExpressions(table,businessRow,hjColumn,houseSubIndex+2,businessSubIndex);
			}
			//ͣ��
			if(parkingSubIndex>(businessSubIndex+1)){
				IRow parkRow=table.getRow(parkingSubIndex);
				reSetParkExpressions(table,parkRow,hjColumn,businessSubIndex+2,parkingSubIndex);
			}
			//����
			if(publicSubIndex>(parkingSubIndex+1)){
				IRow publicRow=table.getRow(publicSubIndex);
				reSetExpressions(table,publicRow,hjColumn,parkingSubIndex+2,publicSubIndex);
			}
		}
	}
	public static void reSetParkExpressions(KDTable tblMain,IRow row,int hjColumn,int n ,int m){
		for(int j=hjColumn;j<tblMain.getColumnCount();j++){
			String c ="";
			String f ="";
			String i ="";
			String exp = "";
			if(j<=25){
			   c=String.valueOf(((char)('A'+j)));
			}else{
			   c=(char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
			}
			exp="=sum("+c+(n)+":"+c+(m)+")";
			row.getCell(j).setExpressions(exp);
			if((j-5)%4==0){
				BigDecimal sumAmount = FDCHelper.ZERO;
		        BigDecimal acreage = FDCHelper.ZERO;
		        if(row.getCell(j).getValue()!=null)
		        	sumAmount = new BigDecimal(row.getCell(j).getValue().toString());
		        if(row.getCell(j-2).getValue()!=null)
		        	acreage = new BigDecimal(row.getCell(j-2).getValue().toString());
		        if(acreage.intValue()!=0)
		        	row.getCell(j-1).setValue(sumAmount.divide(acreage,4,2));
				row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			}
		}
	}
	//��С��
	public static void reSetExpressions(KDTable tblMain,IRow row,int hjColumn,int n ,int m){
		for(int j=hjColumn;j<tblMain.getColumnCount();j++){
			String c ="";
			String f ="";
			String i ="";
			String exp = "";
			if(j<=25){
			   c=String.valueOf(((char)('A'+j)));
			}else{
			   c=(char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
			}
			exp="=sum("+c+(n)+":"+c+(m)+")";
			row.getCell(j).setExpressions(exp);
			if((j-5)%4==0){
				BigDecimal sumAmount = FDCHelper.ZERO;
		        BigDecimal acreage = FDCHelper.ZERO;
		        if(row.getCell(j).getValue()!=null)
		        	sumAmount = new BigDecimal(row.getCell(j).getValue().toString());
		        if(row.getCell(j-3).getValue()!=null)
		        	acreage = new BigDecimal(row.getCell(j-3).getValue().toString());
		        if(acreage.intValue()!=0)
		        	row.getCell(j-1).setValue(sumAmount.divide(acreage,4,2));
				row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			}
		}
	}
	private static boolean isTotalRow(IRow row){
		boolean isSubTotalRow=false;
		if(row.getCell(5).getValue()!=null&&row.getCell(5).getValue().equals("�ϼ�")){
			isSubTotalRow=true;
		}
		return isSubTotalRow;
	}
	public static void getFootRow(KDTable tblMain,Set columnName){
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
        	if(columnName.contains(String.valueOf(c))){
        		
	            ICell cell = footRow.getCell(c);
	            if(tblMain.getColumnKey(c).indexOf("count")<0){
	            	 cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	 	            cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
	            }else{
	            	cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("left"));
	            }
	            cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
	            if((c-5)%4!=0){
	            	cell.setValue(TableUtils.getColumnValueSum(tblMain,c));
        	    }else{
        	    	cell.setValue(FDCHelper.divide(TableUtils.getColumnValueSum(tblMain,c+1),TableUtils.getColumnValueSum(tblMain,c-2) ));
        	    }
        	}
        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
//        footRow.getCell(10).getStyleAttributes().setFontColor(Color.RED);
//        footRow.getCell(14).getStyleAttributes().setFontColor(Color.RED);
//        footRow.getCell(18).getStyleAttributes().setFontColor(Color.RED);
	}
	/**
	 * ���ܼ�
	 * */
	public static void getRowSum(KDTable table){
		Set columnName = new HashSet();
		for(int i=7;i<table.getColumnCount();i++){
			columnName.add(String.valueOf(i));
		}
		getFootRow(table,columnName);
		IRow footRow = null;
        KDTFootManager footRowManager = table.getFootManager();
        footRow = footRowManager.getFootRow(0);
        TableUtils.changeTableNumberFormat(table, new String[]{"tararea","tarunitprice","taramount",
        		"yeararea","yearunitprice","yearamount",
				"zjarea","zjunitprice","zjamount",
				"wqzarea","wqzunitprice","wqzamount",
				"yqzwdjarea","yqzwdjunitprice","yqzwdjamount",
				"wqzydjarea","wqzydjunitprice","wqzydjamount",
				"yqzydjarea","yqzydjunitprice","yqzydjamount",
				"ysarea","ysunitprice","ysamount"});
        footRow.getCell(5).setValue("�ܼ�");
	}
    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new ActivityValueReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return ActivityValueReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		getRowSum(tblMain);
//		setTotalRow(tblMain);
//		reSetExpressions(tblMain);
		
	}

	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		try {
        	RptParams pp=(RptParams)params.clone();
        	RptParams rpt = getRemoteInstance().createTempTable(pp);
            RptTableHeader header = (RptTableHeader)rpt.getObject("header");
            KDTableUtil.setHeader(header, tblMain);
            rpt=getRemoteInstance().query(pp);
	        tblMain.setRowCount(rpt.getInt("count"));
	        RptRowSet rs = (RptRowSet)rpt.getObject("rowset");
	        KDTableUtil.insertRows(rs, 0, tblMain);
	        
			tblMain.getColumn("yqzydjcount").getStyleAttributes().setFontColor(Color.BLUE);
			tblMain.getColumn("wqzydjcount").getStyleAttributes().setFontColor(Color.BLUE);
			tblMain.getColumn("yqzwdjcount").getStyleAttributes().setFontColor(Color.BLUE);
			
        } catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    public void onLoad() throws Exception {
    	setShowDialogOnLoad(false);
    	super.onLoad();
    	btnQuickPic.setIcon(EASResource.getIcon("imgTbtn_startupserver"));
    	btnHistory.setIcon(EASResource.getIcon("imgTbtn_demandhistorydata"));
    	btnQuickPic.setVisible(false);
    	btnHistory.setText("���׼ۼ���");
    	this.treeProject.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
    	this.actionQuery.setVisible(false);
    }
    /**
     * ��Ŀ��
     * */
    protected void treeProject_valueChanged(TreeSelectionEvent e)throws Exception {
		super.treeProject_valueChanged(e);
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeProject.getLastSelectedPathComponent();
		if(treeNode !=null && treeNode.getUserObject() instanceof SellProjectInfo){
			SellProjectInfo project = (SellProjectInfo)treeNode.getUserObject();
			params.setString("projectId", project.getId().toString());
			query();
			if(tblMain.getColumn("projectname")!=null)
				tblMain.getColumn("projectname").setWidth(0);
		}
	}
	
    /**
     * ��ʷ�汾
     * */
    public void actionHistory_actionPerformed(ActionEvent e) throws Exception {
//		super.actionHistory_actionPerformed(e);
//		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeProject.getLastSelectedPathComponent();
//		if(treeNode !=null && treeNode.getUserObject() instanceof SellProjectInfo){
//			SellProjectInfo project = (SellProjectInfo)treeNode.getUserObject();
//			UIContext uiContext = new UIContext(this);
//			uiContext.put("projectId", project.getId().toString());
//			String editUIName = ActivityValueHistoryListUI.class.getName();
//			UIFactory.createUIFactory(UIFactoryName.MODEL).create(editUIName, uiContext, null, OprtState.VIEW).show();
//
//		}
    	if(this.btnHistory.getText().equals("���׼ۼ���")){
    		this.params.setObject("isStand", Boolean.FALSE);
    		this.btnHistory.setText("����ۼ���");
    	}else{
    		this.params.setObject("isStand", Boolean.TRUE);
    		this.btnHistory.setText("���׼ۼ���");
    	}
    	this.query();
	}
    
    /**
     * �γɿ���
     * */
	public void actionQuickPic_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuickPic_actionPerformed(e);
		UIContext uiContext = new UIContext(this);
		uiContext.put("ui",this);
		quickName = null;
		quickDes = null;
		String editUIName = ActivityQuickNameUI.class.getName();
		UIFactory.createUIFactory(UIFactoryName.MODEL).create(editUIName, uiContext, null, OprtState.ADDNEW).show();
		if(quickName!=null && !"".equals(quickName)){
			ActivityValueReportInfo info = new ActivityValueReportInfo();
			String projectid =  "";
		    String version = null;
		    boolean flag = false;
			for(int i=0;i<tblMain.getRowCount();i++){
				IRow row = tblMain.getRow(i);
				if(!isTotalRow(row)){
					if(row.getCell("projectid").getValue()!=null){
						projectid = row.getCell("projectid").getValue().toString();
						info.setProjectid(projectid);
					}
					if(row.getCell("projectlongnumber").getValue()!=null)
						info.setProjectlongnumber(row.getCell("projectlongnumber").getValue().toString());
					if(row.getCell("projectname").getValue()!=null)
						info.setProjectname(row.getCell("projectname").getValue().toString());
					if(row.getCell("productConstitute").getValue()!=null)
						info.setProductConstitute(row.getCell("productConstitute").getValue().toString());
					if(row.getCell("typename").getValue()!=null)
						info.setTypename(row.getCell("typename").getValue().toString());
					if(row.getCell("areaRange").getValue()!=null)
						info.setAreaRange(row.getCell("areaRange").getValue().toString());
					
					if(row.getCell("tararea").getValue()!=null)
						info.setTararea((BigDecimal)row.getCell("tararea").getValue());
					if(row.getCell("tarcount").getValue()!=null)
						info.setTarcount((BigDecimal)row.getCell("tarcount").getValue());
					if(row.getCell("tarunitprice").getValue()!=null)
						info.setTarunitprice((BigDecimal)row.getCell("tarunitprice").getValue());
					if(row.getCell("taramount").getValue()!=null)
						info.setTaramount((BigDecimal)row.getCell("taramount").getValue());
					
					if(row.getCell("yeararea").getValue()!=null)
						info.setTararea((BigDecimal)row.getCell("yeararea").getValue());
					if(row.getCell("yearcount").getValue()!=null)
						info.setTarcount((BigDecimal)row.getCell("yearcount").getValue());
					if(row.getCell("yearunitprice").getValue()!=null)
						info.setTarunitprice((BigDecimal)row.getCell("yearunitprice").getValue());
					if(row.getCell("yearamount").getValue()!=null)
						info.setTaramount((BigDecimal)row.getCell("yearamount").getValue());
					
					
					if(row.getCell("zjarea").getValue()!=null)
						info.setZjarea((BigDecimal)row.getCell("zjarea").getValue());
					if(row.getCell("zjcount").getValue()!=null)
						info.setZjcount((BigDecimal)row.getCell("zjcount").getValue());
					if(row.getCell("zjunitprice").getValue()!=null)
						info.setZjunitprice((BigDecimal)row.getCell("zjunitprice").getValue());
					if(row.getCell("zjamount").getValue()!=null)
						info.setZjamount((BigDecimal)row.getCell("zjamount").getValue());
					
					if(row.getCell("wqzarea").getValue()!=null)
						info.setWqzarea((BigDecimal)row.getCell("wqzarea").getValue());
					if(row.getCell("wqzcount").getValue()!=null)
						info.setWqzcount((BigDecimal)row.getCell("wqzcount").getValue());
					if(row.getCell("wqzunitprice").getValue()!=null)
						info.setWqzunitprice((BigDecimal)row.getCell("wqzunitprice").getValue());
					if(row.getCell("wqzamount").getValue()!=null)
						info.setWqzamount((BigDecimal)row.getCell("wqzamount").getValue());
					
					if(row.getCell("wqzydjarea").getValue()!=null)
						info.setWqzarea((BigDecimal)row.getCell("wqzydjarea").getValue());
					if(row.getCell("wqzydjcount").getValue()!=null)
						info.setWqzydjcount((BigDecimal)row.getCell("wqzydjcount").getValue());
					if(row.getCell("wqzydjunitprice").getValue()!=null)
						info.setWqzydjunitprice((BigDecimal)row.getCell("wqzydjunitprice").getValue());
					if(row.getCell("wqzydjamount").getValue()!=null)
						info.setWqzydjamount((BigDecimal)row.getCell("wqzydjamount").getValue());
					
					if(row.getCell("yqzydjarea").getValue()!=null)
						info.setWqzarea((BigDecimal)row.getCell("yqzydjarea").getValue());
					if(row.getCell("yqzydjcount").getValue()!=null)
						info.setYqzydjcount((BigDecimal)row.getCell("yqzydjcount").getValue());
					if(row.getCell("yqzydjunitprice").getValue()!=null)
						info.setYqzydjunitprice((BigDecimal)row.getCell("yqzydjunitprice").getValue());
					if(row.getCell("yqzydjamount").getValue()!=null)
						info.setYqzydjamount((BigDecimal)row.getCell("yqzydjamount").getValue());
					
					if(row.getCell("yqzwdjarea").getValue()!=null)
						info.setYqzwdjarea((BigDecimal)row.getCell("yqzwdjarea").getValue());
					if(row.getCell("yqzwdjcount").getValue()!=null)
						info.setYqzwdjcount((BigDecimal)row.getCell("yqzwdjcount").getValue());
					if(row.getCell("yqzwdjunitprice").getValue()!=null)
						info.setYqzwdjunitprice((BigDecimal)row.getCell("yqzwdjunitprice").getValue());
					if(row.getCell("yqzwdjamount").getValue()!=null)
						info.setYqzwdjamount((BigDecimal)row.getCell("yqzwdjamount").getValue());
					
					if(row.getCell("ysarea").getValue()!=null)
						info.setYsarea((BigDecimal)row.getCell("ysarea").getValue());
					if(row.getCell("yscount").getValue()!=null)
						info.setYscount((BigDecimal)row.getCell("yscount").getValue());
					if(row.getCell("ysunitprice").getValue()!=null)
						info.setYsunitprice((BigDecimal)row.getCell("ysunitprice").getValue());
					if(row.getCell("ysamount").getValue()!=null)
						info.setYsamount((BigDecimal)row.getCell("ysamount").getValue());
					if(version==null){
						version = getVersion(projectid);
						if("".equals(version)||version==null)
							version = "V1.0";
						else {
							int index = version.indexOf(".");
							int v = Integer.parseInt(version.substring(index+1))+1;
							version = version.substring(0, index+1)+v;
						}
					}
					info.setDescription(version);
					info.setSeq(i);
					info.setSimpleName(""+i);
					info.setQuickName(quickName);
					info.setQuickDes(quickDes);
					BOSUuid id = BOSUuid.create(info.getBOSType());
					info.setId(id);
					info.setName(id.toString());
					info.setNumber(id.toString());
					ActivityValueReportFactory.getRemoteInstance().addnew(info);
					flag = true;
				}
			}
			if(flag){
				MsgBox.showInfo("�����ɹ���");
			}
		}
	}

	/**
	 * ��ȡ�汾��
	 * */
	public String getVersion(String projectid) throws Exception{
		String version = "";
		String sql = "select fdescription_l2 from T_MAR_ActivityValueReport where fprojectid='"+projectid+"' order by fdescription_l2 desc";
		FDCSQLBuilder db = new FDCSQLBuilder();
		db.appendSql(sql);
		IRowSet rowset = db.executeQuery();
		while(rowset.next()){
			version = rowset.getString("fdescription_l2");
			break;
		}
		return version;
	}

	/**
     * output actionChart_actionPerformed
     */
    public void actionChart_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionChart_actionPerformed(e);
    }
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row=this.tblMain.getRow(e.getRowIndex());
			Object amount=row.getCell(e.getColIndex()).getValue();
			if(amount==null||!(amount instanceof BigDecimal)){
				return;
			}
			 UIContext uiContext = new UIContext(this);
			 uiContext.put("Owner", this);
			 RptParams param = new RptParams();
			 
			 if(row.getCell("projectlongnumber").getValue()!=null){
				 Object[] pr=new Object[1];
				 ProductTypeInfo pt=new ProductTypeInfo();
				 pt.setId(BOSUuid.read(row.getCell("projectlongnumber").getValue().toString()));
				 pr[0]=pt;
				 param.setObject("productType", pr);
			 }
			 param.setObject("sellProject", "'"+params.getString("projectId")+"'");
			 
			 if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("yqzydjcount")){
				 param.setObject("sellState", "('Sign')");
				}else  if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("wqzydjcount")){
					param.setObject("ydj", Boolean.FALSE);
				}else  if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("yqzwdjcount")){
					param.setObject("ydj", Boolean.TRUE);
				}else{
					return;
				}
			 
			 uiContext.put("RPTFilter", param);
			 uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(RoomSourceReportUI.class.getName(), uiContext, null, OprtState.VIEW);
			 uiWindow.show();
		}
	}
	IUIWindow uiWindow;
}