/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.ActionMap;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.MarketYearProjectCollection;
import com.kingdee.eas.fdc.contract.MarketYearProjectEntryInfo;
import com.kingdee.eas.fdc.contract.MarketYearProjectFactory;
import com.kingdee.eas.fdc.contract.MarketYearProjectInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MarketYearProjectEditUI extends AbstractMarketYearProjectEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketYearProjectEditUI.class);
    public MarketYearProjectEditUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	this.actionRemove.setVisible(false);
    	this.actionAddLine.setVisible(false);
    	this.actionInsertLine.setVisible(false);
    	this.actionRemoveLine.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	this.actionNext.setVisible(false);
    	this.actionPre.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionFirst.setVisible(false);
    	this.actionLast.setVisible(false);
    	
    	this.actionAudit.setEnabled(true);
    	this.actionUnAudit.setEnabled(true);
    	
    	this.actionAudit.setVisible(true);
    	this.actionUnAudit.setVisible(true);
    	
    	this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
		this.txtVersion.setDataType(0);
		
		this.kdtEntry.addKDTMouseListener(new KDTSortManager(kdtEntry));
		kdtEntry.getSortMange().setSortAuto(false);
		
		this.actionAddNew.setVisible(false);
    }
	protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return MarketYearProjectFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add("CU.*");
    	sic.add("state");
    	sic.add("entry.*");
    	sic.add("entry.costAccount.*");
    	return sic;
    }
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
		} else {
			this.unLockUI();
		}
	}
	public void storeFields() {
		super.storeFields();
		editData.setName(editData.getOrgUnit().getName()+"_"+editData.getYear()+"年年度营销预算-"+this.editData.getVersion());
		this.txtName.setName(editData.getName());
		
		if(this.kdtEntry.getRowCount()>0){
			BigDecimal totalAmount=FDCHelper.ZERO;
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				MarketYearProjectEntryInfo entryInfo=(MarketYearProjectEntryInfo)this.kdtEntry.getRow(i).getCell("amount1").getUserObject();
				if(entryInfo.getCostAccount().getLevel()==2){
					totalAmount=FDCHelper.add(totalAmount, this.kdtEntry.getRow(i).getCell("totalAmount").getValue());
				}
			}
			this.editData.setTotalAmount(totalAmount);
			this.txtTotalAmount.setValue(totalAmount);
		}
	}
	public void loadFields() {
		isLoad=true;
		this.kdtEntry.checkParsed();
		this.kdtEntry.removeRows();
		this.kdtEntry.removeColumns();
		
		ActionMap actionMap = this.kdtEntry.getActionMap();
		actionMap.remove(KDTAction.CUT);
		actionMap.remove(KDTAction.DELETE);
		actionMap.remove(KDTAction.PASTE);
		
		IRow headRow=this.kdtEntry.addHeadRow();
		
		KDFormattedTextField amounttext = new KDFormattedTextField();
		amounttext.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amounttext.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amounttext.setPrecision(2);
		amounttext.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amounttext);
		
		IColumn column=this.kdtEntry.addColumn();
		column.setKey("number");
		column.getStyleAttributes().setLocked(true);
		headRow.getCell("number").setValue("编码");
		
		column=this.kdtEntry.addColumn();
		column.setKey("costAccount");
		column.getStyleAttributes().setLocked(true);
		headRow.getCell("costAccount").setValue("成本科目");
		
		column=this.kdtEntry.addColumn();
		column.setKey("happenAmount");
		column.getStyleAttributes().setLocked(true);
		headRow.getCell("happenAmount").setValue("已发生金额");
		
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		column=this.kdtEntry.addColumn();
		column.setKey("totalAmount");
		column.getStyleAttributes().setLocked(true);
		headRow.getCell("totalAmount").setValue("合计");
		
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		for(int i=1;i<13;i++){
			column=this.kdtEntry.addColumn();
			column.setKey("amount"+i);
			headRow.getCell("amount"+i).setValue(i+"月");
			
			column.setEditor(amountEditor);
			column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		}
		Map map=new HashMap();
		try {
			MarketYearProjectCollection col=MarketYearProjectFactory.getRemoteInstance().getMarketYearProjectCollection("select entry.*,entry.costAccount.id from where orgUnit.id='"+this.editData.getOrgUnit().getId().toString()+"' and year="+this.editData.getYear()+" and version="+(this.editData.getVersion()-1));
			if(col.size()>0){
				for(int i=0;i<col.get(0).getEntry().size();i++){
					map.put(col.get(0).getEntry().get(i).getCostAccount().getId().toString()+"@"+col.get(0).getEntry().get(i).getMonth(), col.get(0).getEntry().get(i).getAmount());
				}
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		Map happenMap=new HashMap();
		try {
			happenMap=getHappenAmount(this.editData.getYear());
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Map rowMap=new HashMap();
		for(int i=0;i<this.editData.getEntry().size();i++){
			MarketYearProjectEntryInfo entry=this.editData.getEntry().get(i);
			IRow r=null;
			if(entry.getMonth()==1){
				r=this.kdtEntry.addRow();
				r.getCell("number").setValue(entry.getCostAccount().getLongNumber().replaceAll("!", "."));
				r.getCell("costAccount").setValue(entry.getCostAccount().getName());
				
				rowMap.put(entry.getCostAccount().getId(), r);
			}else{
				r=(IRow) rowMap.get(entry.getCostAccount().getId());
			}
			if(!entry.getCostAccount().isIsLeaf()){
				r.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
				r.getStyleAttributes().setLocked(true);
			}else if(this.editData.getYear()!=0){
				r.getCell("happenAmount").setValue(happenMap.get(entry.getCostAccount().getId().toString()));
			}
			r.getCell("amount"+entry.getMonth()).setValue(entry.getAmount());
			
			if(this.editData.getVersion()!=1){
				BigDecimal comAmount=(BigDecimal) map.get(entry.getCostAccount().getId().toString()+"@"+entry.getMonth());
				if(comAmount!=null&&entry.getAmount()!=null){
					if(comAmount.compareTo(entry.getAmount())>0){
						r.getCell("amount"+entry.getMonth()).getStyleAttributes().setBackground(Color.GREEN);
					}else if(comAmount.compareTo(entry.getAmount())<0){
						r.getCell("amount"+entry.getMonth()).getStyleAttributes().setBackground(Color.RED);
					}
				}else if(entry.getAmount()!=null&&entry.getAmount().compareTo(FDCHelper.ZERO)!=0){
					r.getCell("amount"+entry.getMonth()).getStyleAttributes().setBackground(Color.RED);
				}
			}
			
			r.getCell("totalAmount").setValue(FDCHelper.add(r.getCell("totalAmount").getValue(), entry.getAmount()));
			r.getCell("amount"+entry.getMonth()).setUserObject(entry);
		}
		for(int i=this.kdtEntry.getRowCount()-1;i>=0;i--){
			MarketYearProjectEntryInfo entryInfo=(MarketYearProjectEntryInfo)this.kdtEntry.getRow(i).getCell("amount1").getUserObject();
			if(entryInfo.getCostAccount().isIsLeaf()){
				continue;
			}
			String totalNumber=this.kdtEntry.getRow(i).getCell("number").getValue().toString();
			BigDecimal amount=FDCHelper.ZERO;
			for(int j=i+1;j<this.kdtEntry.getRowCount();j++){
				String number=this.kdtEntry.getRow(j).getCell("number").getValue().toString();
				if(number.substring(0, number.lastIndexOf(".")).equals(totalNumber)){
					amount=FDCHelper.add(amount,this.kdtEntry.getRow(j).getCell("happenAmount").getValue());
				}
			}
//			if(amount.compareTo(FDCHelper.ZERO)>0){
				this.kdtEntry.getRow(i).getCell("happenAmount").setValue(amount);
//			}
		}
		super.loadFields();
		if(this.txtVersion.getBigDecimalValue().compareTo(new BigDecimal(1))>0){
			this.spYear.setEnabled(false);
		}
		isLoad=false;
	}
	private Map getHappenAmount(int year) throws BOSException, SQLException{
		Map map=new HashMap();
		StringBuilder sql = new StringBuilder();
//		sql.append(" select sum(entry.famount) amount from T_CON_ContractMarketEntry entry left join t_Con_contractBill head on head.fid=entry.fheadid");
//		sql.append(" where year(fdate)="+year+" and head.fstate!='1SAVED' and head.FMpCostAccountId='"+costAccountId+"'");
		sql.append(" select sum(t.famount) amount,t.fcostAccountid costAccountId from(");
		sql.append(" select (case when t.fsettleprice is null then entry.famount else t.fsettleprice*entry.frate/100 end)famount ,head.FMpCostAccountId fcostAccountid from T_CON_ContractMarketEntry entry left join t_con_contractbill head on head.fid=entry.fheadid");
		sql.append(" left join (select sb.fsettleprice,sb.fcontractbillid from T_CON_ContractSettlementBill sb where sb.fstate='4AUDITTED') t on t.fcontractbillid=head.fid");
		sql.append(" where head.fstate='4AUDITTED' and year(entry.fdate)="+year);
		sql.append(" union all select mpEntry.famount,mpEntry.fcostAccountid from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid");
		sql.append(" where mp.fstate!='1SAVED' and year(mp.fbizDate)="+year);
		sql.append(" and not exists(select t1.fid from t_con_contractBill t1 where t1.fstate='4AUDITTED' and t1.FMarketProjectId=mp.fid and t1.FMpCostAccountId=mpEntry.fcostAccountid) ");
		sql.append(" )t group by t.fcostAccountid ");
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			map.put(rowSet.getString("costAccountId"), rowSet.getBigDecimal("amount"));
		}
		return map;
	}
	protected IObjectValue createNewData() {
		MarketYearProjectInfo info=(MarketYearProjectInfo)this.getUIContext().get("info");
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		if(info==null){
			
			info=new MarketYearProjectInfo();
			info.setVersion(1);
			info.setOrgUnit((FullOrgUnitInfo) this.getUIContext().get("org"));
			
			try {
//				SellProjectCollection scol=SellProjectFactory.getRemoteInstance().getSellProjectCollection("select * from where orgUnit.id='"+info.getOrgUnit().getId().toString()+"' and parent.id is null");
//				if(scol.size()>0){
//					info.setSellProject(scol.get(0));
//				}
			
				CostAccountCollection col=CostAccountFactory.getRemoteInstance().getCostAccountCollection("select * from where isMarket=1 and fullOrgUnit.id='"+info.getOrgUnit().getId()+"' and isEnabled=1 order by longNumber");
				for(int i=0;i<col.size();i++){
					CostAccountInfo cost=col.get(i);
					for(int j=1;j<13;j++){
						MarketYearProjectEntryInfo entry=new MarketYearProjectEntryInfo();
						entry.setCostAccount(cost);
						entry.setMonth(j);
						info.getEntry().add(entry);
					}
				}
				
			} catch (BOSException e) {
				e.printStackTrace();
			}
			
		}else{
			info.setVersion(info.getVersion()+1);
			info.setId(null);
			BigDecimal totalAmount=FDCHelper.ZERO;
			try {
				CostAccountCollection col=CostAccountFactory.getRemoteInstance().getCostAccountCollection("select * from where isMarket=1 and fullOrgUnit.id='"+info.getOrgUnit().getId()+"' and isEnabled=1 order by longNumber");
				Set costAccount=new HashSet();
				for(int i=0;i<info.getEntry().size();i++){
					info.getEntry().get(i).setId(BOSUuid.create(info.getEntry().get(i).getBOSType()));
					costAccount.add(info.getEntry().get(i).getCostAccount().getId().toString());
					if(info.getEntry().get(i).getCostAccount().isIsLeaf()){
						totalAmount=FDCHelper.add(totalAmount,info.getEntry().get(i).getAmount());
					}
				}
				for(int i=0;i<col.size();i++){
					CostAccountInfo cost=col.get(i);
					if(costAccount.contains(cost.getId().toString())){
						continue;
					}
					for(int j=1;j<13;j++){
						MarketYearProjectEntryInfo entry=new MarketYearProjectEntryInfo();
						entry.setCostAccount(cost);
						entry.setMonth(j);
						info.getEntry().add(entry);
					}
				}
				CRMHelper.sortCollection(info.getEntry(), "costAccount.longNumber", true);
			}catch (BOSException e) {
				e.printStackTrace();
			}
			info.setLastTotalAmount(totalAmount);
		}
		info.setIsLatest(false);
		info.setBizDate(now);
		info.setState(FDCBillStateEnum.SAVED);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return info;
	}
	protected void verifyInputForSubmint() throws Exception {
		if(this.spYear.getIntegerVlaue()==0){
			FDCMsgBox.showWarning(this,"年度必须大于0！");
			SysUtil.abort();
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.txtDescription,"备注");
		FDCClientVerifyHelper.verifyEmpty(this, this.txtSign);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtRate);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtYearAmount);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtYearRate);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtSellProjecttxt);
		
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			BigDecimal totalAmount=(BigDecimal) this.kdtEntry.getRow(i).getCell("totalAmount").getValue();
			BigDecimal happenAmount=(BigDecimal) this.kdtEntry.getRow(i).getCell("happenAmount").getValue();
			if(totalAmount==null) totalAmount=FDCHelper.ZERO;
			if(happenAmount==null) happenAmount=FDCHelper.ZERO;
			if(totalAmount.compareTo(happenAmount)<0){
				FDCMsgBox.showWarning(this,"合计数低于已发生金额，不允许提交！");
				SysUtil.abort();
			}
		}
		super.verifyInputForSubmint();
	}
	private Boolean isLoad=false;
	private int year_old = 0;
	protected void spYear_stateChanged(ChangeEvent e) throws Exception {
		if (isLoad) {
			return;
		}
		int result = MsgBox.OK;
		MarketYearProjectCollection col=MarketYearProjectFactory.getRemoteInstance().getMarketYearProjectCollection("select * from where year="+this.spYear.getIntegerVlaue()+" and orgUnit.id='"+this.editData.getOrgUnit().getId()+"'");
		if(col.size()>0){
			FDCMsgBox.showWarning(this,"已存在年度计划！");
			this.spYear.setValue(year_old, false);
			return;
		}else{
			year_old = this.spYear.getIntegerVlaue().intValue();
		}
		Map happenMap=getHappenAmount(this.spYear.getIntegerVlaue().intValue());
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			MarketYearProjectEntryInfo entry=(MarketYearProjectEntryInfo)this.kdtEntry.getRow(i).getCell("amount1").getUserObject();
			if(entry.getCostAccount().isIsLeaf()){
				this.kdtEntry.getRow(i).getCell("happenAmount").setValue(happenMap.get(entry.getCostAccount().getId().toString()));
			}
		}
		for(int i=this.kdtEntry.getRowCount()-1;i>=0;i--){
			MarketYearProjectEntryInfo entryInfo=(MarketYearProjectEntryInfo)this.kdtEntry.getRow(i).getCell("amount1").getUserObject();
			if(entryInfo.getCostAccount().isIsLeaf()){
				continue;
			}
			String totalNumber=this.kdtEntry.getRow(i).getCell("number").getValue().toString();
			BigDecimal amount=FDCHelper.ZERO;
			for(int j=i+1;j<this.kdtEntry.getRowCount();j++){
				String number=this.kdtEntry.getRow(j).getCell("number").getValue().toString();
				if(number.substring(0, number.lastIndexOf(".")).equals(totalNumber)){
					amount=FDCHelper.add(amount,this.kdtEntry.getRow(j).getCell("happenAmount").getValue());
				}
			}
			if(amount.compareTo(FDCHelper.ZERO)>0){
				this.kdtEntry.getRow(i).getCell("happenAmount").setValue(amount);
			}
		}
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		IRow r = this.kdtEntry.getRow(e.getRowIndex());
		int colIndex = e.getColIndex();
		MarketYearProjectEntryInfo entry=(MarketYearProjectEntryInfo) r.getCell(colIndex).getUserObject();
		entry.setAmount((BigDecimal) r.getCell(colIndex).getValue());
		
		for(int i=this.kdtEntry.getRowCount()-1;i>=0;i--){
			MarketYearProjectEntryInfo entryInfo=(MarketYearProjectEntryInfo)this.kdtEntry.getRow(i).getCell(colIndex).getUserObject();
			if(entryInfo.getCostAccount().isIsLeaf()){
				continue;
			}
			String totalNumber=this.kdtEntry.getRow(i).getCell("number").getValue().toString();
			BigDecimal amount=FDCHelper.ZERO;
			for(int j=i+1;j<this.kdtEntry.getRowCount();j++){
				String number=this.kdtEntry.getRow(j).getCell("number").getValue().toString();
				if(number.substring(0, number.lastIndexOf(".")).equals(totalNumber)){
					amount=FDCHelper.add(amount,this.kdtEntry.getRow(j).getCell(colIndex).getValue());
				}
			}
//			if(amount.compareTo(FDCHelper.ZERO)>0){
				this.kdtEntry.getRow(i).getCell(colIndex).setValue(amount);
//			}
			entryInfo.setAmount(amount);
		}
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row=this.kdtEntry.getRow(i);
			BigDecimal totalAmount=FDCHelper.ZERO;
			for(int j=1;j<13;j++){
				totalAmount=FDCHelper.add(totalAmount,row.getCell("amount"+j).getValue());
			}
			row.getCell("totalAmount").setValue(totalAmount);
		}
	}

}