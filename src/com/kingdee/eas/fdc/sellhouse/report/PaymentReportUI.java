/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.core.util.DateUtil;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.invite.markesupplier.uitl.StringUtils;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class PaymentReportUI extends AbstractPaymentReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(PaymentReportUI.class);
    protected SellProjectInfo sellProject = null;
    public PaymentReportUI() throws Exception
    {
        super();
    }

    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
	}
	public void onLoad() throws Exception{
	    	
    	actionQuery.setEnabled(false);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		
		if(this.getUIContext().get("filter")==null){
			initTree();
		}else{
			this.toolBar.setVisible(false);
			this.treeView.setVisible(false);
			this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		}
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionView.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionRemove.setVisible(false);
		this.btnLocate.setVisible(false);
		
		String[] fields=new String[this.tblMain.getColumnCount()];
		for(int i=0;i<this.tblMain.getColumnCount();i++){
			fields[i]=this.tblMain.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(this.tblMain,fields);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		
		actionQuery.setEnabled(true);
		
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try	{
			FilterInfo filter = new FilterInfo();
			if(this.getUIContext().get("filter")==null){
				if(node!=null){
					if (node.getUserObject() instanceof SellProjectInfo){
						filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));	
					}else if(node.getUserObject() instanceof OrgStructureInfo){
						Map spIdMap = FDCTreeHelper.getAllObjectIdMap(node, "SellProject");
			    		if(spIdMap.size()>0)
			    			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",FDCTreeHelper.getStringFromSet(spIdMap.keySet()),CompareType.INNER));
			    		else
			    			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",null));
					}
				}else{
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", "'null'"));
				}
			}else{
				filter.mergeFilter((FilterInfo) this.getUIContext().get("filter"), "and");
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
			
		}catch (Exception e)
		{
			handleException(e);
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		sellProject=null;
		if (node.getUserObject() instanceof SellProjectInfo){
			if(node.getUserObject() != null){
				sellProject=(SellProjectInfo) node.getUserObject();
			}			
		}
		this.refresh(null);
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		//处理按揭款的其他信息
		int rowCount = this.tblMain.getRowCount();
		Set<String> roomIDs= new HashSet<String>();
		String moneyType = null;
		for(int i=0;i<rowCount;i++){
			moneyType = ""+this.tblMain.getCell(i, "moneyType").getValue();
			if(!StringUtils.isEmpty(moneyType) && (MoneyTypeEnum.LoanAmount.getAlias().equals(moneyType)||MoneyTypeEnum.AccFundAmount.getAlias().equals(moneyType))){
				roomIDs.add(this.tblMain.getCell(i, "roomId").getValue()+"");
			}
		}
		
		StringBuffer paramStr = new StringBuffer();
		String params = null;
		for(Iterator<String> it =roomIDs.iterator();it.hasNext();){
			paramStr.append("'");
			paramStr.append(it.next());
			paramStr.append("',");
		}
		
		if(paramStr.length()>0){
			params = paramStr.substring(0,paramStr.length()-1);
		}
		
		
		FDCSQLBuilder builder  =  new FDCSQLBuilder();
		StringBuffer sql = new StringBuffer();
		sql.append("  select r.fid roomid, b.fname_l2 loanBankName,acb.fname_l2 acbankName, sm.fcontractTotalAmount+isnull(sm.FAreaCompensate,0)+isnull(sm.FPlanningCompensate,0)+isnull(sm.FCashSalesCompensate,0) contractTotalAmount,sm.fbizdate signDate, ");
		sql.append("  sm.FSaleManNames handler,sm.FSaleManNames salesman,'' loanType from T_SHE_ROOM r   left outer join t_she_signmanage sm on sm.froomid = r.fid and sm.fstate='4AUDITTED'" +
				" left outer join T_BD_Bank b  on b.fid = sm.floanbank  left outer join t_bd_bank acb on sm.FAcfBank = acb.fid " );
		sql.append("   where r.fid in("+params+")");
		
		
		builder.appendSql(sql.toString());
		
		Map<String,OtherInfo> resultMap = new HashMap<String,OtherInfo>();
		OtherInfo oi = null;
		String key = null;
		try {
			IRowSet rs = builder.executeQuery();
			while(rs.next()){
				oi = new OtherInfo();
				key = rs.getString("roomid");
				oi.setRoomId(key);
				oi.setBankName(rs.getString("loanBankName"));
				oi.setSignDate(rs.getDate("signDate"));
				oi.setContactTotalAmt(rs.getBigDecimal("contractTotalAmount"));
				oi.setBizHandler(rs.getString("handler"));
				oi.setSalesMan(rs.getString("salesman"));
				oi.setLoanType(rs.getString("loanType"));
				oi.setAcBankName(rs.getString("acbankName"));
				resultMap.put(key, oi);
				}
		} catch (BOSException e1) {
			e1.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		IRow r = null;
		for(int i=0;i<rowCount;i++){
			//moneyType = ""+this.tblMain.getCell(i, "moneyType").getValue();
			//if(!StringUtils.isEmpty(moneyType) && MoneyTypeEnum.LoanAmount.getAlias().equals(moneyType)){
				key = ""+this.tblMain.getCell(i,"roomId").getValue();
				if(resultMap.containsKey(key)){
					r= this.tblMain.getRow(i);
					oi = resultMap.get(key);
					r.getCell("signDate").setValue(oi.getSignDate());
					r.getCell("bank").setValue(oi.getBankName());
					r.getCell("contractAmount").setValue(oi.getContactTotalAmt());
					r.getCell("bizHandler").setValue(oi.getBizHandler());
					r.getCell("signDate").setValue(oi.getSignDate());
					moneyType = ""+r.getCell("moneyDefine.moneyType").getValue();
					if(!StringUtils.isEmpty(moneyType) && (MoneyTypeEnum.LoanAmount.getAlias().equals(moneyType)||MoneyTypeEnum.AccFundAmount.getAlias().equals(moneyType))){
						r.getCell("mortgageType").setValue(r.getCell("moneyDefine.name").getValue());
					}
					r.getCell("acBank").setValue(oi.getAcBankName());
					
					if(r.getCell("bizDate").getValue() != null){
						Date apDate = Date.valueOf(r.getCell("bizDate").getValue()+"");
						if(oi.getSignDate() != null ){
							long diffDay = DateTimeUtils.dateDiff("d", oi.getSignDate(),apDate);
							r.getCell("diffDay").setValue(diffDay);
						}
					}
				}
			//}
		}
		
		
		
		CRMClientHelper.getFootRow(tblMain, new String[]{"entrys.revAmount","entrys.amount","entrys.appRevAmount"});
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"entrys.revAmount","entrys.amount","entrys.appRevAmount","contractAmount"});
	}
}