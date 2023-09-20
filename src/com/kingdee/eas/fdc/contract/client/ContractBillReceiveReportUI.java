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
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
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
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.VerticalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgViewType;
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
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillReceiveCollection;
import com.kingdee.eas.fdc.contract.ContractBillReceiveFactory;
import com.kingdee.eas.fdc.contract.ContractBillReceiveReportFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractRecBillCollection;
import com.kingdee.eas.fdc.contract.ContractRecBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractRecBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractRecBillFactory;
import com.kingdee.eas.fdc.contract.MarketProjectReportFacadeFactory;
import com.kingdee.eas.fdc.contract.MarketProjectSourceEnum;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceTypeEnum;
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
public class ContractBillReceiveReportUI extends AbstractContractBillReceiveReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractBillReceiveReportUI.class);
    
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    public ContractBillReceiveReportUI() throws Exception
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
		return ContractBillReceiveReportFacadeFactory.getRemoteInstance();
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
		this.btnToZHMP.setVisible(false);
		this.actionQuery.setVisible(false);
		
		if(params.getString("companyId")==null){
			this.buildContractTypeTree();
			this.buildProjectTree();
			
			KDTreeView treeView=new KDTreeView();
			treeView.setTree(treeProject);
			treeView.setShowButton(false);
			treeView.setTitle("工程项目");
	        kDSplitPane2.add(treeView, "left");
			treeView.setShowControlPanel(true);
			
			treeProject.setShowsRootHandles(true);
			
			treeProject.setSelectionRow(0);
			treeProject.expandRow(0);
			treeContractType.setSelectionRow(0);
			treeContractType.expandRow(1);
		}else{
			pnlLeftTree.setVisible(false);
		}
		isOnLoad=false;
    }
	protected Set authorizedOrgs = null;
	public void buildProjectTree() throws Exception {

		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

		projectTreeBuilder.build(this, treeProject, actionOnLoad);
		
		authorizedOrgs = (Set)ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if(authorizedOrgs==null){
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
			            OrgType.CostCenter, 
			            null,  null, null);
			if(orgs!=null){
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while(it.hasNext()){
					authorizedOrgs.add(it.next());
				}
			}		
		}
		
	}
	protected int getTreeInitialLevel() {
		return TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
	}
	private ITreeBase getTreeInterface() {

		ITreeBase treeBase = null;
		try {
			treeBase = ContractTypeFactory.getRemoteInstance();
		} catch (BOSException e) {
			abort(e);
		}

		return treeBase;
	}
	protected int getTreeExpandLevel() {
		return TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
	}
	protected ILNTreeNodeCtrl getLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getTreeInterface());
	}
	protected FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("isReceive", Boolean.TRUE));
		return filter;
	}
	protected void buildContractTypeTree() throws Exception {
		ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(getLNTreeNodeCtrl(),
				getTreeInitialLevel(), getTreeExpandLevel(), this
						.getDefaultFilterForTree());

		if (ContractClientUtils.getRes("allContractType") != null) {
			KDTreeNode rootNode = new KDTreeNode(ContractClientUtils.getRes("allContractType"));
			((DefaultTreeModel) treeContractType.getModel()).setRoot(rootNode);
			
		} else {
			((DefaultTreeModel) treeContractType.getModel()).setRoot(null);
		}
		
		treeBuilder.buildTree(treeContractType);
		treeContractType.setShowPopMenuDefaultItem(false);

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
    	        tblMain.getTreeColumn().setDepth(2);
    	        String curProjectId=null;
    	        Map comMap=new HashMap();
    	        while(rs.next()){
    	        	IRow row=tblMain.addRow();
    	        	((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
    	        	
    	        	String comCurProjectId=row.getCell("curProjectId").getValue().toString();
    	        	
    	        	row.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
					row.setTreeLevel(0);
					
					String id=row.getCell("id").getValue().toString();
					
					BigDecimal recAmount=FDCHelper.ZERO;
					ContractRecBillCollection col=ContractRecBillFactory.getRemoteInstance().getContractRecBillCollection("select * from where state='4AUDITTED' and contractBillReceive.id='"+id+"' order by number");
					for(int j=0;j<col.size();j++){
						recAmount=recAmount.add(col.get(j).getAmount());
					}
					row.getCell("recCount").setValue(col.size());
					row.getCell("recAmount").setValue(recAmount);
					
					BigDecimal payAmount=FDCHelper.ZERO;
					BigDecimal payReqAmountT=FDCHelper.ZERO;
					BigDecimal payReqActAmountT=FDCHelper.ZERO;
					
					ContractBillCollection conCol=ContractBillFactory.getRemoteInstance().getContractBillCollection("select contractType.name,curProject.name,partB.name,landDeveloper.name,*,currency.name from where state='4AUDITTED' and contractBillReceive.id='"+id+"' order by number");
					for(int i=0;i<conCol.size();i++){
						IRow recRow=tblMain.addRow();
						recRow.setTreeLevel(1);
						recRow.getCell("payId").setValue(conCol.get(i).getId().toString());
						recRow.getCell("payCurProject").setValue(conCol.get(i).getCurProject().getName());
						recRow.getCell("payContractType").setValue(conCol.get(i).getContractType().getName());
						recRow.getCell("payNumber").setValue(conCol.get(i).getNumber());
						recRow.getCell("payName").setValue(conCol.get(i).getName());
						recRow.getCell("payBizDate").setValue(conCol.get(i).getBookedDate());
						recRow.getCell("payAuditTime").setValue(conCol.get(i).getAuditTime());
						recRow.getCell("payLandDeveloper").setValue(conCol.get(i).getLandDeveloper().getName());
						recRow.getCell("payPartB").setValue(conCol.get(i).getPartB().getName());
						recRow.getCell("payCurrency").setValue(conCol.get(i).getCurrency().getName());
						recRow.getCell("payAmount").setValue(conCol.get(i).getAmount());
						
						payAmount=FDCHelper.add(payAmount,conCol.get(i).getAmount());
						
						int payReqActCount=0;
						BigDecimal payReqAmount=FDCHelper.ZERO;
						BigDecimal payReqActAmount=FDCHelper.ZERO;
						PayRequestBillCollection payCol=PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection("select * from where contractId='"+conCol.get(i).getId().toString()+"' and state='4AUDITTED' order by number");
						for(int j=0;j<payCol.size();j++){
							payReqAmount=FDCHelper.add(payReqAmount,payCol.get(j).getAmount());
							payReqAmountT=FDCHelper.add(payReqAmountT,payCol.get(j).getAmount());
							
							PaymentBillCollection payActCol=PaymentBillFactory.getRemoteInstance().getPaymentBillCollection("select * from where fdcPayReqId='"+payCol.get(j).getId()+"' and billStatus=15");
							
							payReqActCount=payReqActCount+payActCol.size();
							for(int k=0;k<payActCol.size();k++){
								payReqActAmount=payReqActAmount.add(payActCol.get(k).getActPayAmt());
								payReqActAmountT=payReqActAmountT.add(payActCol.get(k).getActPayAmt());
							}
						}
						recRow.getCell("payReqCount").setValue(payCol.size());
						recRow.getCell("payReqAmount").setValue(payReqAmount);
						recRow.getCell("payReqActCount").setValue(payReqActCount);
						recRow.getCell("payReqActAmount").setValue(payReqActAmount);
					}
				
					row.getCell("payAmount").setValue(payAmount);
					row.getCell("payReqAmount").setValue(payReqAmountT);
					row.getCell("payReqActAmount").setValue(payReqActAmountT);
					
					
					BigDecimal recContractAmount=(BigDecimal) row.getCell("recContractAmount").getValue();
    	        	BigDecimal recTotalAmount=(BigDecimal) row.getCell("recTotalAmount").getValue();
    	        	BigDecimal payContractAmount=(BigDecimal) row.getCell("payContractAmount").getValue();
    	        	BigDecimal payReqAmountAuditting=(BigDecimal) row.getCell("payReqAmountAuditting").getValue();
    	        	BigDecimal payReqAmountAuditted=(BigDecimal) row.getCell("payReqAmountAuditted").getValue();
    	        	BigDecimal payTotalAmount=(BigDecimal) row.getCell("payTotalAmount").getValue();
    	        	BigDecimal sub1=(BigDecimal) row.getCell("sub1").getValue();
    	        	BigDecimal sub2=(BigDecimal) row.getCell("sub2").getValue();
    	        	BigDecimal recAmount1=(BigDecimal) row.getCell("recAmount").getValue();
    	        	BigDecimal payAmount1=(BigDecimal) row.getCell("payAmount").getValue();
    	        	BigDecimal payReqAmount=(BigDecimal) row.getCell("payReqAmount").getValue();
    	        	BigDecimal payReqActAmount=(BigDecimal) row.getCell("payReqActAmount").getValue();
    	        	
    	        	Map map=null;
    	        	if(comMap.containsKey(comCurProjectId)){
    	        		map=(Map) comMap.get(comCurProjectId);
    	        	}else{
    	        		map=new HashMap();
    	        		comMap.put(comCurProjectId, map);
    	        	}
    	        	map.put("curProject", row.getCell("curProject").getValue());
    	        	map.put("recContractAmount", FDCHelper.add(map.get("recContractAmount"), recContractAmount));
	        		map.put("recTotalAmount", FDCHelper.add(map.get("recTotalAmount"), recTotalAmount));
	        		map.put("payContractAmount", FDCHelper.add(map.get("payContractAmount"), payContractAmount));
	        		map.put("payReqAmountAuditting", FDCHelper.add(map.get("payReqAmountAuditting"), payReqAmountAuditting));
	        		map.put("payReqAmountAuditted", FDCHelper.add(map.get("payReqAmountAuditted"), payReqAmountAuditted));
	        		map.put("payTotalAmount", FDCHelper.add(map.get("payTotalAmount"), payTotalAmount));
	        		map.put("sub1", FDCHelper.add(map.get("sub1"), sub1));
	        		map.put("sub2", FDCHelper.add(map.get("sub2"), sub2));
	        		map.put("recAmount", FDCHelper.add(map.get("recAmount"), recAmount1));
	        		map.put("payAmount", FDCHelper.add(map.get("payAmount"), payAmount1));
	        		map.put("payReqAmount", FDCHelper.add(map.get("payReqAmount"), payReqAmount));
	        		map.put("payReqActAmount", FDCHelper.add(map.get("payReqActAmount"), payReqActAmount));
    	        	if(curProjectId!=null&&!curProjectId.equals(comCurProjectId)){
    	        		IRow totalRow=tblMain.addRow(row.getRowIndex());
    	        		Map preMap=(Map) comMap.get(curProjectId);
    	        		totalRow.getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
    	        		totalRow.getCell("curProjectId").setValue(curProjectId);
    	        		if(preMap!=null){
    	        			totalRow.getCell("curProject").setValue(preMap.get("curProject")+"-小计");
        	        		totalRow.getCell("recContractAmount").setValue(preMap.get("recContractAmount"));
        	        		totalRow.getCell("recTotalAmount").setValue(preMap.get("recTotalAmount"));
        	        		totalRow.getCell("payContractAmount").setValue(preMap.get("payContractAmount"));
        	        		totalRow.getCell("payReqAmountAuditting").setValue(preMap.get("payReqAmountAuditting"));
        	        		totalRow.getCell("payReqAmountAuditted").setValue(preMap.get("payReqAmountAuditted"));
        	        		totalRow.getCell("payTotalAmount").setValue(preMap.get("payTotalAmount"));
        	        		totalRow.getCell("sub1").setValue(preMap.get("sub1"));
        	        		totalRow.getCell("sub2").setValue(preMap.get("sub2"));
        	        		totalRow.getCell("recAmount").setValue(preMap.get("recAmount"));
        	        		totalRow.getCell("payAmount").setValue(preMap.get("payAmount"));
        	        		totalRow.getCell("payReqAmount").setValue(preMap.get("payReqAmount"));
        	        		totalRow.getCell("payReqActAmount").setValue(preMap.get("payReqActAmount"));
    	        		}
    	        	}
    	        	curProjectId=comCurProjectId;
    	        }
    	        
    	        Map preMap=(Map) comMap.get(curProjectId);
    	    	if(preMap!=null){
    	    		 IRow totalRow=tblMain.addRow();
    	    		 totalRow.getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
    	        	 totalRow.getCell("curProjectId").setValue(curProjectId);
    	        	 totalRow.getCell("curProject").setValue(preMap.get("curProject")+"-小计");
 	        		totalRow.getCell("recContractAmount").setValue(preMap.get("recContractAmount"));
 	        		totalRow.getCell("recTotalAmount").setValue(preMap.get("recTotalAmount"));
 	        		totalRow.getCell("payContractAmount").setValue(preMap.get("payContractAmount"));
 	        		totalRow.getCell("payReqAmountAuditting").setValue(preMap.get("payReqAmountAuditting"));
 	        		totalRow.getCell("payReqAmountAuditted").setValue(preMap.get("payReqAmountAuditted"));
 	        		totalRow.getCell("payTotalAmount").setValue(preMap.get("payTotalAmount"));
 	        		totalRow.getCell("sub1").setValue(preMap.get("sub1"));
 	        		totalRow.getCell("sub2").setValue(preMap.get("sub2"));
 	        		totalRow.getCell("recAmount").setValue(preMap.get("recAmount"));
 	        		totalRow.getCell("payAmount").setValue(preMap.get("payAmount"));
 	        		totalRow.getCell("payReqAmount").setValue(preMap.get("payReqAmount"));
 	        		totalRow.getCell("payReqActAmount").setValue(preMap.get("payReqActAmount"));
    	    	}
    	        if(rs.getRowCount() > 0){
    	        	tblMain.reLayoutAndPaint();
    	        }else{
    	        	tblMain.repaint();
    	        }
    	        tblMain.setRefresh(true);
    	        
    	        String []sum=new String[]{"recContractAmount","recTotalAmount","payContractAmount","payReqAmountAuditting","payReqAmountAuditted","payTotalAmount","sub1","sub2","recAmount","payAmount","payReqAmount","payReqActAmount"};
    	        CRMClientHelper.changeTableNumberFormat(tblMain, sum);
    	        CRMClientHelper.fmtDate(tblMain, "bizDate");
    	        CRMClientHelper.fmtDate(tblMain, "auditTime");
    			CRMClientHelper.fmtDate(tblMain, "payBizDate");
    			CRMClientHelper.fmtDate(tblMain, "payAuditTime");
    			tblMain.getColumn("number").getStyleAttributes().setFontColor(Color.BLUE);
    			tblMain.getColumn("payNumber").getStyleAttributes().setFontColor(Color.BLUE);
    			tblMain.getColumn("recCount").getStyleAttributes().setFontColor(Color.BLUE);
    			tblMain.getColumn("payReqCount").getStyleAttributes().setFontColor(Color.BLUE);
    			tblMain.getColumn("payReqActCount").getStyleAttributes().setFontColor(Color.BLUE);
    			tblMain.getColumn("httk").getStyleAttributes().setFontColor(Color.BLUE);
    			
     	        getFootRow(tblMain, sum);
    			
    			final KDBizMultiLangArea textField = new KDBizMultiLangArea();
    			//更改变更内容控件--以适应换行内容
    			textField.setMaxLength(1000);
    			textField.setAutoscrolls(true);
    			textField.setEditable(true);
    			textField.setToolTipText("Alt+Enter换行");
    			KDTDefaultCellEditor editor = new KDTDefaultCellEditor(textField);
    			tblMain.getColumn("httk").setEditor(editor);
    			tblMain.getColumn("httk").getStyleAttributes().setVerticalAlign(VerticalAlignment.TOP);
    			tblMain.getColumn("httk").getStyleAttributes().setWrapText(true);
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
	        	if(table.getRow(i).getCell(columnName).getValue()!=null &&table.getRow(i).getStyleAttributes().getBackground().equals(FDCTableHelper.cantEditColor)){
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
	  private String SetToIn(Set set){
	    	Iterator it=set.iterator();
	    	String in="(";
	    	while(it.hasNext()){
	    		in=in+"'"+it.next().toString()+"',";
	    	}
	    	in=in+"'')";
	    	return in;
	    }
	protected void setFilter(Object projectNode,Object  typeNode) throws Exception {
		if (projectNode != null 	&& projectNode instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();	
				}else{
					id = ((FullOrgUnitInfo)projTreeNodeInfo).getId();
				}
				FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(id));
				String orgUnitLongNumber = orgUnitInfo.getLongNumber();
				
				params.setObject("orgUnit.longNumber", orgUnitLongNumber);
				params.setObject("orgUnit.id", authorizedOrgs);
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				
				params.setObject("curProject.id", idSet);
			}

		}

		FilterInfo typefilter =  new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();	
		/*
		 * 合同类型树
		 */
		if (typeNode != null&& typeNode instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo)typeNode;
			BOSUuid id = typeTreeNodeInfo.getId();
			Set idSet = FDCClientUtils.genContractTypeIdSet(id);
			
			params.setObject("contractType.id", idSet);
		}else{
			params.setObject("contractType.id", null);
		}
	}
	protected void treeContractType_valueChanged(TreeSelectionEvent e)
			throws Exception {
		Object project = null;
		DefaultKingdeeTreeNode projectNode=(DefaultKingdeeTreeNode)treeProject.getLastSelectedPathComponent();
		if(projectNode!=null)project=projectNode.getUserObject();
		
		Object contractType = null;
		DefaultKingdeeTreeNode contractTypeNode=(DefaultKingdeeTreeNode)treeContractType.getLastSelectedPathComponent();
		if(contractTypeNode!=null)contractType=contractTypeNode.getUserObject();
		setFilter(project,contractType);
		query();
	}
	protected void treeProject_valueChanged(TreeSelectionEvent e)
			throws Exception {
		Object project = null;
		DefaultKingdeeTreeNode projectNode=(DefaultKingdeeTreeNode)treeProject.getLastSelectedPathComponent();
		if(projectNode!=null)project=projectNode.getUserObject();
		
		Object contractType = null;
		DefaultKingdeeTreeNode contractTypeNode=(DefaultKingdeeTreeNode)treeContractType.getLastSelectedPathComponent();
		if(contractTypeNode!=null)contractType=contractTypeNode.getUserObject();
		setFilter(project,contractType);
		query();
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("number")){
				String id=(String) this.tblMain.getRow(e.getRowIndex()).getCell("id").getValue();
				if(id==null)return;
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("ID", id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractBillReceiveEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("payNumber")){
				String id=(String) this.tblMain.getRow(e.getRowIndex()).getCell("payId").getValue();
				if(id==null)return;
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("ID", id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("recCount")){
				String id=(String) this.tblMain.getRow(e.getRowIndex()).getCell("id").getValue();
				if(id==null)return;
				UIContext uiContext = new UIContext(this);
				RptParams param=new RptParams();
				param.setObject("id", id);
				param.setObject("type", "recCount");
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("RPTFilter", param);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractBillReceiveDetailReportUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("payReqCount")){
				String id=(String) this.tblMain.getRow(e.getRowIndex()).getCell("payId").getValue();
				if(id==null)return;
				UIContext uiContext = new UIContext(this);
				RptParams param=new RptParams();
				param.setObject("id", id);
				param.setObject("type", "payReqCount");
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("RPTFilter", param);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractBillReceiveDetailReportUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("payReqActCount")){
				String id=(String) this.tblMain.getRow(e.getRowIndex()).getCell("payId").getValue();
				if(id==null)return;
				UIContext uiContext = new UIContext(this);
				RptParams param=new RptParams();
				param.setObject("id", id);
				param.setObject("type", "payReqActCount");
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("RPTFilter", param);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractBillReceiveDetailReportUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
		if (e.getColIndex() == this.tblMain.getColumnIndex("httk")) {
			KDDetailedAreaUtil.showDialog(this, this.tblMain, 250, 200, 1000);
		}
	}
}