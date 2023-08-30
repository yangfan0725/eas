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
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
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
				new FilterItemInfo("isReceive", Boolean.FALSE));
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
    	        while(rs.next()){
    	        	IRow row=tblMain.addRow();
    	        	((KDTableInsertHandler)(new DefaultKDTableInsertHandler(rs))).setTableRowData(row, rs.toRowArray());
    	        	
    	        	row.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
					row.setTreeLevel(0);
					
					String payId=row.getCell("payId").getValue().toString();
					
					BigDecimal recAmount=FDCHelper.ZERO;
					BigDecimal payReqAmount=FDCHelper.ZERO;
					BigDecimal payTotalAmount=FDCHelper.ZERO;
					
					PayRequestBillCollection payCol=PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection("select * from where contractId='"+payId+"' and state='4AUDITTED' order by number");
					for(int j=0;j<payCol.size();j++){
						IRow payRow=tblMain.addRow();
						payRow.setTreeLevel(0);
						payRow.getCell("payReqNumber").setValue(payCol.get(j).getNumber());
						payRow.getCell("payReqBizDate").setValue(payCol.get(j).getBookedDate());
						payRow.getCell("payReqAmount").setValue(payCol.get(j).getAmount());
						payRow.getCell("payReqId").setValue(payCol.get(j).getId().toString());
						
						payRow.getCell("payReqNumber").getStyleAttributes().setBackground(Color.yellow);
						payRow.getCell("payReqBizDate").getStyleAttributes().setBackground(Color.yellow);
						payRow.getCell("payReqAmount").getStyleAttributes().setBackground(Color.yellow);
						payRow.getCell("payReqId").getStyleAttributes().setBackground(Color.yellow);
						payRow.getCell("payReqActAmount").getStyleAttributes().setBackground(Color.yellow);
						payRow.getCell("payReqActDate").getStyleAttributes().setBackground(Color.yellow);
						
						payReqAmount=FDCHelper.add(payReqAmount,payCol.get(j).getAmount());
						BigDecimal actAmount=FDCHelper.ZERO;
						Date payReqActDate=null;
						PaymentBillCollection payActCol=PaymentBillFactory.getRemoteInstance().getPaymentBillCollection("select * from where fdcPayReqId='"+payCol.get(j).getId()+"' and billStatus=15");
						for(int k=0;k<payActCol.size();k++){
							actAmount=actAmount.add(payActCol.get(k).getActPayAmt());
							payReqActDate=payActCol.get(k).getPayDate();
							
							payTotalAmount=payTotalAmount.add(payActCol.get(k).getActPayAmt());
						}
						payRow.getCell("payReqActAmount").setValue(actAmount);
						payRow.getCell("payReqActDate").setValue(payReqActDate);
					}
					
					BigDecimal recTotalAmount=FDCHelper.ZERO;
					ContractBillReceiveCollection conCol=ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveCollection("select contractType.name,curProject.name,partB.name,landDeveloper.name,*,currency.name from where state='4AUDITTED' and contractBill.id='"+payId+"' order by number");
					for(int i=0;i<conCol.size();i++){
						IRow recRow=tblMain.addRow();
						recRow.setTreeLevel(1);
						recRow.getCell("id").setValue(conCol.get(i).getId().toString());
						recRow.getCell("curProject").setValue(conCol.get(i).getCurProject().getName());
						recRow.getCell("contractType").setValue(conCol.get(i).getContractType().getName());
						recRow.getCell("number").setValue(conCol.get(i).getNumber());
						recRow.getCell("name").setValue(conCol.get(i).getName());
						recRow.getCell("bizDate").setValue(conCol.get(i).getBookedDate());
						recRow.getCell("auditTime").setValue(conCol.get(i).getAuditTime());
						recRow.getCell("landDeveloper").setValue(conCol.get(i).getLandDeveloper().getName());
						recRow.getCell("partB").setValue(conCol.get(i).getPartB().getName());
						recRow.getCell("currency").setValue(conCol.get(i).getCurrency().getName());
						recRow.getCell("amount").setValue(conCol.get(i).getAmount());
						recRow.getCell("httk").setValue(conCol.get(i).getHttk());
						
						recRow.getCell("id").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
						recRow.getCell("curProject").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
						recRow.getCell("contractType").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
						recRow.getCell("number").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
						recRow.getCell("name").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
						recRow.getCell("bizDate").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
						recRow.getCell("auditTime").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
						recRow.getCell("landDeveloper").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
						recRow.getCell("partB").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
						recRow.getCell("currency").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
						recRow.getCell("amount").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
						recRow.getCell("httk").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
						
						recAmount=FDCHelper.add(recAmount, conCol.get(i).getAmount());
						
						ContractRecBillEntryCollection col=ContractRecBillEntryFactory.getRemoteInstance().getContractRecBillEntryCollection("select moneyDefine.name,*,head.* from where head.state='4AUDITTED' and head.contractBillReceive.id='"+conCol.get(i).getId().toString()+"' order by head.number");
						for(int j=0;j<col.size();j++){
							IRow recBillRow=tblMain.addRow();
							recBillRow.setTreeLevel(1);
							recBillRow.getCell("recNumber").setValue(col.get(j).getHead().getNumber());
							recBillRow.getCell("recBizDate").setValue(col.get(j).getHead().getBizDate());
							recBillRow.getCell("recMoneyDefine").setValue(col.get(j).getMoneyDefine().getName());
							recBillRow.getCell("recAmount").setValue(col.get(j).getAmount());
							recBillRow.getCell("recId").setValue(col.get(j).getHead().getId().toString());
							
							recBillRow.getCell("recNumber").getStyleAttributes().setBackground(FDCTableHelper.dayTotalColor);
							recBillRow.getCell("recBizDate").getStyleAttributes().setBackground(FDCTableHelper.dayTotalColor);
							recBillRow.getCell("recMoneyDefine").getStyleAttributes().setBackground(FDCTableHelper.dayTotalColor);
							recBillRow.getCell("recAmount").getStyleAttributes().setBackground(FDCTableHelper.dayTotalColor);
							recBillRow.getCell("recId").getStyleAttributes().setBackground(FDCTableHelper.dayTotalColor);
							recTotalAmount=recTotalAmount.add(col.get(j).getAmount());
						}
					}
					row.getCell("recTotalAmount").setValue(recTotalAmount);
					row.getCell("recAmount").setValue(recTotalAmount);
					
					row.getCell("amount").setValue(recAmount);
					row.getCell("payTotalAmount").setValue(payTotalAmount);
					row.getCell("sub").setValue(FDCHelper.subtract(payTotalAmount, recTotalAmount));
					row.getCell("payReqAmount").setValue(payReqAmount);
					row.getCell("payReqActAmount").setValue(payTotalAmount);
    	        }
    	        String orgUnitLongNumber=params.getString("orgUnit.longNumber");
    			Set authorizedOrgs=(Set) params.getObject("orgUnit.id");
    			
    			Set curProject=(Set) params.getObject("curProject.id");
    			Set contractType=(Set) params.getObject("contractType.id");
    			
    			String companyId=(String) params.getString("companyId");
    			String moneyDefineId=(String) params.getString("moneyDefineId");
    			
    			StringBuffer sb = new StringBuffer();
    			sb.append("select contractType.name,curProject.name,partB.name,landDeveloper.name,*,currency.name from where state='4AUDITTED' and contractBill.id is null ");
    	        if(orgUnitLongNumber!=null){
    				sb.append(" and orgUnit.longnumber like '"+orgUnitLongNumber+"%'");
    			}
    			if(authorizedOrgs!=null){
    				sb.append(" and orgUnit.id in"+SetToIn(authorizedOrgs));
    			} 
    			if(curProject!=null){
    				sb.append(" and curProject.id in"+SetToIn(curProject));
    			} 
    			if(contractType!=null){
    				sb.append(" and contract.id in"+SetToIn(contractType));
    			} 
    			if(companyId!=null){
    				sb.append(" and CU.id='"+companyId+"'");
    			} 
    			if(moneyDefineId!=null){
    				sb.append(" and id in (select FParentID from T_CON_ContractBillRRateEntry where fmoneyDefineId='"+moneyDefineId+"')");
    			} 
    			sb.append(" order by number");
				ContractBillReceiveCollection conCol=ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveCollection(sb.toString());
				for(int i=0;i<conCol.size();i++){
					IRow row=tblMain.addRow();
					row.setTreeLevel(0);
					row.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
					
					BigDecimal recAmount=FDCHelper.ZERO;
					BigDecimal payReqAmount=FDCHelper.ZERO;
					BigDecimal payTotalAmount=FDCHelper.ZERO;
					
					BigDecimal recTotalAmount=FDCHelper.ZERO;
					
					IRow recRow=tblMain.addRow();
					recRow.setTreeLevel(1);
					recRow.getCell("id").setValue(conCol.get(i).getId().toString());
					recRow.getCell("curProject").setValue(conCol.get(i).getCurProject().getName());
					recRow.getCell("contractType").setValue(conCol.get(i).getContractType().getName());
					recRow.getCell("number").setValue(conCol.get(i).getNumber());
					recRow.getCell("name").setValue(conCol.get(i).getName());
					recRow.getCell("bizDate").setValue(conCol.get(i).getBookedDate());
					recRow.getCell("auditTime").setValue(conCol.get(i).getAuditTime());
					recRow.getCell("landDeveloper").setValue(conCol.get(i).getLandDeveloper().getName());
					recRow.getCell("partB").setValue(conCol.get(i).getPartB().getName());
					recRow.getCell("currency").setValue(conCol.get(i).getCurrency().getName());
					recRow.getCell("amount").setValue(conCol.get(i).getAmount());
					recRow.getCell("httk").setValue(conCol.get(i).getHttk());
					
					recRow.getCell("id").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
					recRow.getCell("curProject").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
					recRow.getCell("contractType").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
					recRow.getCell("number").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
					recRow.getCell("name").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
					recRow.getCell("bizDate").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
					recRow.getCell("auditTime").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
					recRow.getCell("landDeveloper").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
					recRow.getCell("partB").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
					recRow.getCell("currency").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
					recRow.getCell("amount").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
					recRow.getCell("httk").getStyleAttributes().setBackground(FDCTableHelper.KDTABLE_TOTAL_BG_COLOR);
					
					recAmount=FDCHelper.add(recAmount, conCol.get(i).getAmount());
					
					ContractRecBillEntryCollection col=ContractRecBillEntryFactory.getRemoteInstance().getContractRecBillEntryCollection("select moneyDefine.name,*,head.* from where head.state='4AUDITTED' and head.contractBillReceive.id='"+conCol.get(i).getId().toString()+"' order by head.number");
					for(int j=0;j<col.size();j++){
						IRow recBillRow=tblMain.addRow();
						recBillRow.setTreeLevel(1);
						recBillRow.getCell("recNumber").setValue(col.get(j).getHead().getNumber());
						recBillRow.getCell("recBizDate").setValue(col.get(j).getHead().getBizDate());
						recBillRow.getCell("recMoneyDefine").setValue(col.get(j).getMoneyDefine().getName());
						recBillRow.getCell("recAmount").setValue(col.get(j).getAmount());
						recBillRow.getCell("recId").setValue(col.get(j).getHead().getId().toString());
						
						recBillRow.getCell("recNumber").getStyleAttributes().setBackground(FDCTableHelper.dayTotalColor);
						recBillRow.getCell("recBizDate").getStyleAttributes().setBackground(FDCTableHelper.dayTotalColor);
						recBillRow.getCell("recMoneyDefine").getStyleAttributes().setBackground(FDCTableHelper.dayTotalColor);
						recBillRow.getCell("recAmount").getStyleAttributes().setBackground(FDCTableHelper.dayTotalColor);
						recBillRow.getCell("recId").getStyleAttributes().setBackground(FDCTableHelper.dayTotalColor);
						recTotalAmount=recTotalAmount.add(col.get(j).getAmount());
					}
					
					row.getCell("recTotalAmount").setValue(recTotalAmount);
					row.getCell("recAmount").setValue(recTotalAmount);
					
					row.getCell("amount").setValue(recAmount);
					row.getCell("payTotalAmount").setValue(payTotalAmount);
					row.getCell("sub").setValue(FDCHelper.subtract(payTotalAmount, recTotalAmount));
					row.getCell("payReqAmount").setValue(payReqAmount);
					row.getCell("payReqActAmount").setValue(payTotalAmount);
				}
				
    	        if(rs.getRowCount() > 0){
    	        	tblMain.reLayoutAndPaint();
    	        }else{
    	        	tblMain.repaint();
    	        }
    	        tblMain.setRefresh(true);
    	        
    	        CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"amount","recTotalAmount","payTotalAmount","sub","payAmount","recAmount","payReqAmount","payReqActAmount"});
    	        CRMClientHelper.fmtDate(tblMain, "bizDate");
    	        CRMClientHelper.fmtDate(tblMain, "auditTime");
    			CRMClientHelper.fmtDate(tblMain, "payBizDate");
    			CRMClientHelper.fmtDate(tblMain, "payAuditTime");
    			tblMain.getColumn("number").getStyleAttributes().setFontColor(Color.BLUE);
    			tblMain.getColumn("recNumber").getStyleAttributes().setFontColor(Color.BLUE);
    			tblMain.getColumn("payNumber").getStyleAttributes().setFontColor(Color.BLUE);
    			tblMain.getColumn("payReqNumber").getStyleAttributes().setFontColor(Color.BLUE);
    			tblMain.getColumn("httk").getStyleAttributes().setFontColor(Color.BLUE);
    			
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
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("recNumber")){
				String id=(String) this.tblMain.getRow(e.getRowIndex()).getCell("recId").getValue();
				if(id==null)return;
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("ID", id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractRecBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("payNumber")){
				String id=(String) this.tblMain.getRow(e.getRowIndex()).getCell("payId").getValue();
				if(id==null)return;
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("ID", id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}else if(this.tblMain.getColumn(e.getColIndex()).getKey().equals("payReqNumber")){
				String id=(String) this.tblMain.getRow(e.getRowIndex()).getCell("payReqId").getValue();
				if(id==null)return;
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("ID", id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(PayRequestBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
		if (e.getColIndex() == this.tblMain.getColumnIndex("httk")) {
			KDDetailedAreaUtil.showDialog(this, this.tblMain, 250, 200, 1000);
		}
	}
}