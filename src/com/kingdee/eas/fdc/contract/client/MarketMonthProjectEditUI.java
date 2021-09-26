/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ActionMap;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
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
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.MarketMonthProjectCollection;
import com.kingdee.eas.fdc.contract.MarketMonthProjectEntryInfo;
import com.kingdee.eas.fdc.contract.MarketMonthProjectFactory;
import com.kingdee.eas.fdc.contract.MarketMonthProjectInfo;
import com.kingdee.eas.fdc.contract.MarketYearProjectEntryInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MarketMonthProjectEditUI extends AbstractMarketMonthProjectEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketMonthProjectEditUI.class);
    
    public MarketMonthProjectEditUI() throws Exception
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
		
		this.kdtEntry.addKDTMouseListener(new KDTSortManager(kdtEntry));
		kdtEntry.getSortMange().setSortAuto(false);
    }
	protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return MarketMonthProjectFactory.getRemoteInstance();
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
		editData.setName(editData.getOrgUnit().getName()+"_"+editData.getYear()+"年"+editData.getMonth()+"月月度营销预算");
		this.txtName.setName(editData.getName());
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
		headRow.getCell("happenAmount").setValue("年度预算可用余额");
		
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
//		column=this.kdtEntry.addColumn();
//		column.setKey("happenAmount");
//		column.getStyleAttributes().setLocked(true);
//		headRow.getCell("happenAmount").setValue("已发生金额");
		
//		column.setEditor(amountEditor);
//		column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
//		
//		column=this.kdtEntry.addColumn();
//		column.setKey("totalAmount");
//		column.getStyleAttributes().setLocked(true);
//		headRow.getCell("totalAmount").setValue("合计");
		
//		column.setEditor(amountEditor);
//		column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		column=this.kdtEntry.addColumn();
		column.setKey("amount");
		headRow.getCell("amount").setValue("月度预算金额");
		
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		for(int i=0;i<this.editData.getEntry().size();i++){
			MarketMonthProjectEntryInfo entry=this.editData.getEntry().get(i);
			IRow r=null;
			r=this.kdtEntry.addRow();
			r.getCell("number").setValue(entry.getCostAccount().getLongNumber().replaceAll("!", "."));
			r.getCell("costAccount").setValue(entry.getCostAccount().getName());
			
			if(!entry.getCostAccount().isIsLeaf()){
				r.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
				r.getStyleAttributes().setLocked(true);
			}
			r.getCell("amount").setValue(entry.getAmount());
			
			r.getCell("amount").setUserObject(entry);
			
			try {
				r.getCell("happenAmount").setValue(getHappenAmount(entry.getCostAccount().getId().toString(),this.editData.getYear()));
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		for(int i=this.kdtEntry.getRowCount()-1;i>=0;i--){
			MarketMonthProjectEntryInfo entryInfo=(MarketMonthProjectEntryInfo)this.kdtEntry.getRow(i).getCell("amount").getUserObject();
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
		isLoad=false;
	}
	private BigDecimal getHappenAmount(String costAccountId,int year) throws BOSException, SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append(" select entry.amount-isnull(t6.amount,0) amount from T_CON_MarketYearProject m left join (select entry.fcostAccountid,sum(entry.famount) amount,entry.fheadid from T_CON_MarketYearProjectentry entry group by entry.fcostAccountid,entry.fheadid) entry on entry.fheadid=m.fid ");
    	
//    	sql.append(" left join(select sum(t.famount) amount,FMpCostAccountId from(select entry.famount,head.FMpCostAccountId from T_CON_ContractMarketEntry entry left join t_con_contractbill head on head.fid=entry.fheadid");
//    	sql.append(" where head.fstate!='1SAVED'");
//    	sql.append(" and year(entry.fdate)="+year);
//    	
//    	sql.append(" union all select mpEntry.famount,mpEntry.fcostAccountid from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid where mp.fstate='4AUDITTED'");
//    	sql.append(" and year(mp.fbizDate)="+year);
//    	sql.append(" and not exists(select t.fid from t_con_contractBill t where t.fstate!='1SAVED' and t.FMarketProjectId=mp.fid and t.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sql.append(" )t group by t.FMpCostAccountId) t6 on t6.FMpCostAccountId=entry.fcostAccountId");
    	
    	sql.append(" left join(select sum(t.famount) amount,fcostAccountid from(");
    	sql.append(" select (case when t.fsettleprice is null then entry.famount else t.fsettleprice*entry.frate/100 end)famount ,head.FMpCostAccountId fcostAccountid from T_CON_ContractMarketEntry entry left join t_con_contractbill head on head.fid=entry.fheadid");
    	sql.append(" left join (select sb.fsettleprice,sb.fcontractbillid from T_CON_ContractSettlementBill sb where sb.fstate='4AUDITTED') t on t.fcontractbillid=head.fid");
    	sql.append(" where head.fstate='4AUDITTED' and year(entry.fdate)="+year);
    	sql.append(" union all select mpEntry.famount,mpEntry.fcostAccountid from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid");
    	sql.append(" where mp.fstate!='1SAVED' and year(mp.fbizDate)="+year);
    	sql.append(" and not exists(select t1.fid from t_con_contractBill t1 where t1.fstate='4AUDITTED' and t1.FMarketProjectId=mp.fid and t1.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sb.append(" and mp.fbizDate>{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
//    	sb.append(" and mp.fbizDate<={ts '" + FDCConstants.FORMAT_TIME.formast(FDCDateHelper.getSQLEnd(toDate))+ "'}");
//    	sb.append(" and not exists(select t1.fid from t_con_contractBill t1 where t1.fstate='4AUDITTED' and t1.FMarketProjectId=mp.fid and t1.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sql.append(" and not exists(select t.fid from T_CON_ContractWithoutText t where t.fstate!='1SAVED' and t.FMarketProjectId=mp.fid and t.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sql.append(" union all select mpEntry.famount,mpEntry.fcostAccountid from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid where mp.fstate!='1SAVED'");
//    	sql.append(" and year(mp.fbizDate)="+year);
//    	sql.append(" and  exists(select t.fid from T_CON_ContractWithoutText t where t.fstate!='1SAVED' and t.FMarketProjectId=mp.fid and t.FMpCostAccountId=mpEntry.fcostAccountid) ");
    	sql.append(" )t group by t.fcostAccountid) t6 on t6.fcostAccountid=entry.fcostAccountId");
    	
    	
    	sql.append(" where m.FIsLatest=1 and m.fyear="+year+" and entry.fcostAccountid='"+costAccountId+"'");
    	
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			return rowSet.getBigDecimal("amount");
		}
		return FDCHelper.ZERO;
	}
	protected IObjectValue createNewData() {
		MarketMonthProjectInfo info=(MarketMonthProjectInfo)this.getUIContext().get("info");
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info=new MarketMonthProjectInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		info.setOrgUnit((FullOrgUnitInfo) this.getUIContext().get("org"));
		
		try {
			CostAccountCollection col=CostAccountFactory.getRemoteInstance().getCostAccountCollection("select * from where isMarket=1 and fullOrgUnit.id='"+info.getOrgUnit().getId()+"' and isEnabled=1 order by longNumber");
			for(int i=0;i<col.size();i++){
				CostAccountInfo cost=col.get(i);
				MarketMonthProjectEntryInfo entry=new MarketMonthProjectEntryInfo();
				entry.setCostAccount(cost);
				info.getEntry().add(entry);
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		info.setDescription("XX年营销费用预算（考核科目）总额XX万，考核费率X%。\n"+
				"截止X年X月X日，累计签约销售额XX万，累计发生营销费用(考核科目)XX万，累计费率X%。\n"+
				"X月营销费用（考核科目）预算X万，预估当月费率为X%。");
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
		if(this.spMonth.getIntegerVlaue()==0){
			FDCMsgBox.showWarning(this,"月度必须大于0！");
			SysUtil.abort();
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.txtDescription,"备注");
		
		
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			BigDecimal happenAmount=(BigDecimal) this.kdtEntry.getRow(i).getCell("happenAmount").getValue();
			BigDecimal amount=(BigDecimal) this.kdtEntry.getRow(i).getCell("amount").getValue();
			if(happenAmount==null) happenAmount=FDCHelper.ZERO;
			if(amount==null) amount=FDCHelper.ZERO;
			if(amount.compareTo(happenAmount)>0){
				FDCMsgBox.showWarning(this,"月度预算金额超过年度预算可用余额，不允许提交！");
				SysUtil.abort();
			}
		}
		
		super.verifyInputForSubmint();
		
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , this.editData.getId().toString()));
		if(!BoAttchAssoFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this,"请先上传附件！");
			SysUtil.abort();
		}
	}
	private Boolean isLoad=false;
	private int year_old = 0;
	private int month_old = 0;
	protected void spYear_stateChanged(ChangeEvent e) throws Exception {
		if (isLoad) {
			return;
		}
		int result = MsgBox.OK;
		MarketMonthProjectCollection col=MarketMonthProjectFactory.getRemoteInstance().getMarketMonthProjectCollection("select * from where year="+this.spYear.getIntegerVlaue()+" and month="+this.spMonth.getIntegerVlaue()+" and orgUnit.id='"+this.editData.getOrgUnit().getId()+"'");
		if(col.size()>0){
			FDCMsgBox.showWarning(this,"已存在月度计划！");
			this.spYear.setValue(year_old, false);
			return;
		}else{
			year_old = this.spYear.getIntegerVlaue().intValue();
		}
	}
	
	protected void spMonth_stateChanged(ChangeEvent e) throws Exception {
		if (isLoad) {
			return;
		}
		int result = MsgBox.OK;
		MarketMonthProjectCollection col=MarketMonthProjectFactory.getRemoteInstance().getMarketMonthProjectCollection("select * from where year="+this.spYear.getIntegerVlaue()+" and month="+this.spMonth.getIntegerVlaue()+" and orgUnit.id='"+this.editData.getOrgUnit().getId()+"'");
		if(col.size()>0){
			FDCMsgBox.showWarning(this,"已存在月度计划！");
			this.spMonth.setValue(month_old, false);
			return;
		}else{
			month_old = this.spMonth.getIntegerVlaue().intValue();
		}
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		IRow r = this.kdtEntry.getRow(e.getRowIndex());
		int colIndex = e.getColIndex();
		MarketMonthProjectEntryInfo entry=(MarketMonthProjectEntryInfo) r.getCell(colIndex).getUserObject();
		entry.setAmount((BigDecimal) r.getCell(colIndex).getValue());
		
		for(int i=this.kdtEntry.getRowCount()-1;i>=0;i--){
			MarketMonthProjectEntryInfo entryInfo=(MarketMonthProjectEntryInfo)this.kdtEntry.getRow(i).getCell(colIndex).getUserObject();
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
	}
}