/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.MarketProjectFactory;
import com.kingdee.eas.fdc.contract.MarketProjectInfo;
import com.kingdee.eas.fdc.contract.MarketProjectSourceEnum;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.ZHMarketProjectEntryCollection;
import com.kingdee.eas.fdc.contract.ZHMarketProjectEntryFactory;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillFactory;
import com.kingdee.eas.fdc.sellhouse.client.CommissionSettlementEditUI;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MarketProjectListUI extends AbstractMarketProjectListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketProjectListUI.class);
    
    protected OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();
    protected static final String CANTEDIT = "cantEdit";
	protected static final String CANTREMOVE = "cantRemove";
	
    public MarketProjectListUI() throws Exception
    {
        super();
    }
    protected void buildOrgTree() throws Exception {
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,"", SysContext.getSysContext().getCurrentFIUnit().getId().toString(), null, FDCHelper.getActionPK(this.actionOnLoad));
		this.treeOrg.setModel(orgTreeModel);
	}
    public void onLoad() throws Exception {
		if (currentOrg == null || !currentOrg.isIsCompanyOrgUnit()) {
			MsgBox.showInfo("非财务组织不能进入!");
			this.abort();
		}
		super.onLoad();
	
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		buildOrgTree();
		DefaultKingdeeTreeNode orgRoot = (DefaultKingdeeTreeNode) ((TreeModel) this.treeOrg.getModel()).getRoot();
		this.treeOrg.setSelectionNode(orgRoot);
		this.treeOrg.expandAllNodes(true, orgRoot);
		
		this.tblMain.setColumnMoveable(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.setAutoscrolls(true);
	}
    public void removeAllBrotherNode(DefaultKingdeeTreeNode node) {
		DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) node.getParent();
		if (parent == null) {
			return;
		}
		this.treeOrg.removeAllChildrenFromParent(parent);
		this.treeOrg.addNodeInto(node, parent);
		this.removeAllBrotherNode(parent);
	}
    private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode node,
			String id) {
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			if (projectInfo.getId().toString().equals(id)) {
				return node;
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			FullOrgUnitInfo info = oui.getUnit();
			if (info.getId().toString().equals(id)) {
				return node;
			}
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode findNode = findNode(
					(DefaultKingdeeTreeNode) node.getChildAt(i), id);
			if (findNode != null) {
				return findNode;
			}
		}
		return null;
	}
    protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		
		FDCBillInfo info=(FDCBillInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels);
		
		FDCBillStateEnum state = info.getState();
		
		if (state != null&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED)) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
				SysUtil.abort();
			}
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
    	checkBeforeEditOrRemove(CANTEDIT,id);
    	
    	MarketProjectInfo info=MarketProjectFactory.getRemoteInstance().getMarketProjectInfo(new ObjectUuidPK(id));
    	if(MarketProjectSourceEnum.ZHLX.equals(info.getSource())){
			FDCMsgBox.showWarning(this, "综合立项禁止修改！");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
	    	checkBeforeEditOrRemove(CANTREMOVE,id.get(i).toString());
		}
		super.actionRemove_actionPerformed(e);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.SUBMITTED.equals(MarketProjectFactory.getRemoteInstance().getMarketProjectInfo(new ObjectUuidPK(id.get(i).toString())).getState())) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IFDCBill)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			MarketProjectInfo info=MarketProjectFactory.getRemoteInstance().getMarketProjectInfo(new ObjectUuidPK(id.get(i).toString()));
	    	if(MarketProjectSourceEnum.ZHLX.equals(info.getSource())){
				FDCMsgBox.showWarning(this, "综合立项禁止反审批！");
				SysUtil.abort();
			}
			if (!FDCBillStateEnum.AUDITTED.equals(info.getState())) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((IFDCBill)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
		}
		return null;
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode orgNode = getSelectedTreeNode(treeOrg);
		if(orgNode!=null&&orgNode.getUserObject()!=null){
			uiContext.put("org", ((OrgStructureInfo) orgNode.getUserObject()).getUnit());
		}else{
			uiContext.put("org", null);
		}
	}
	protected ICoreBase getBizInterface() throws Exception {
		return MarketProjectFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return MarketProjectEditUI.class.getName();
	}
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		FilterInfo filter = null;
		try {
			filter = this.getFilter();
			if (this.getDialog() != null) {
				FilterInfo commFilter = this.getDialog().getCommonFilter();
				if (filter != null && commFilter != null)
					filter.mergeFilter(commFilter, "and");
			} else {
				QuerySolutionInfo querySolution = this.getQuerySolutionInfo();
				if (querySolution != null
						&& querySolution.getEntityViewInfo() != null) {
					EntityViewInfo ev = Util.getInnerFilterInfo(querySolution);
					if (ev.getFilter() != null) {
						filter.mergeFilter(ev.getFilter(), "and");
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		viewInfo.setFilter(filter);
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	protected FilterInfo getFilter() throws Exception {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode orgNode = getSelectedTreeNode(treeOrg);
		if (orgNode != null&& orgNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) orgNode.getUserObject();
			String orgId = org.getUnit().getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgId));
		}
		return filter;
	}
	protected void treeOrg_valueChanged(TreeSelectionEvent e) throws Exception {
		refresh(null);
	}
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id=row.getCell("id").getValue().toString();
		MarketProjectInfo info=MarketProjectFactory.getRemoteInstance().getMarketProjectInfo(new ObjectUuidPK(id));
		if(MarketProjectSourceEnum.ZHLX.equals(info.getSource())){
			ZHMarketProjectEntryCollection col=ZHMarketProjectEntryFactory.getRemoteInstance().getZHMarketProjectEntryCollection("select head.id from where id='"+info.getSourceBillId()+"'");
			if(col.size()>0){
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", col.get(0).getHead().getId());
		        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(ZHMarketProjectEditUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
			}
		}
	}
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell("id").getValue();
    	MarketProjectInfo info=MarketProjectFactory.getRemoteInstance().getMarketProjectInfo(new ObjectUuidPK(id));
    	if(info.getSourceFunction()!=null){
    		FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select fviewurl from t_oa");
			IRowSet rs=builder.executeQuery();
			String url=null;
			while(rs.next()){
				url=rs.getString("fviewurl");
			}
			if(url!=null){
				String mtLoginNum = OaUtil.encrypt(SysContext.getSysContext().getCurrentUserInfo().getNumber());
				String s2 = "&MtFdLoinName=";
				StringBuffer stringBuffer = new StringBuffer();
	            String oaid = URLEncoder.encode(info.getSourceFunction());
	            String link = String.valueOf(stringBuffer.append(url).append(oaid).append(s2).append(mtLoginNum));
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);  
			}
    	}else{
    		super.actionWorkFlowG_actionPerformed(e);
    	}
	}
	 public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception {
		 checkSelected();
			int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			IRow row = this.tblMain.getRow(rowIndex);
	    	String id = (String) row.getCell("id").getValue();
	    	MarketProjectInfo info=MarketProjectFactory.getRemoteInstance().getMarketProjectInfo(new ObjectUuidPK(id));
	    	if(info.getSourceFunction()!=null){
	    		FDCSQLBuilder builder=new FDCSQLBuilder();
				builder.appendSql("select fviewurl from t_oa");
				IRowSet rs=builder.executeQuery();
				String url=null;
				while(rs.next()){
					url=rs.getString("fviewurl");
				}
				if(url!=null){
					String mtLoginNum = OaUtil.encrypt(SysContext.getSysContext().getCurrentUserInfo().getNumber());
					String s2 = "&MtFdLoinName=";
					StringBuffer stringBuffer = new StringBuffer();
		            String oaid = URLEncoder.encode(info.getSourceFunction());
		            String link = String.valueOf(stringBuffer.append(url).append(oaid).append(s2).append(mtLoginNum));
					Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);  
				}
	    	}else{
	    		super.actionAuditResult_actionPerformed(e);
	    	}
	 }
}