/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ClientHelper;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillInfo;
import com.kingdee.eas.fdc.sellhouse.MonthCommissionFactory;
import com.kingdee.eas.fdc.sellhouse.MonthExcitationEntryInfo;
import com.kingdee.eas.fdc.sellhouse.MonthExcitationFactory;
import com.kingdee.eas.fdc.sellhouse.MonthExcitationInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class MonthExcitationEditUI extends AbstractMonthExcitationEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MonthExcitationEditUI.class);
    public MonthExcitationEditUI() throws Exception
    {
        super();
    }
    public void storeFields()
    {
    	this.txtName.setText(this.editData.getOrgUnit().getName()+"_"+editData.getSellProject().getName()+"_"+editData.getYear()+"年"+editData.getMonth()+"月_销售激励测算明细表");
    	this.editData.setName(this.editData.getOrgUnit().getName()+"_"+editData.getSellProject().getName()+"_"+editData.getYear()+"年"+editData.getMonth()+"月_销售激励测算明细表");
    	super.storeFields();
    }
	protected void entry_editStopped(KDTEditEvent e) throws Exception {
		IRow r = this.entry.getRow(e.getRowIndex());
		if(e.getColIndex() == this.entry.getColumnIndex("amount1")||e.getColIndex() == this.entry.getColumnIndex("amount2")||e.getColIndex() == this.entry.getColumnIndex("amount3")
				||e.getColIndex() == this.entry.getColumnIndex("amount4")||e.getColIndex() == this.entry.getColumnIndex("amount5")||e.getColIndex() == this.entry.getColumnIndex("amount6")
				||e.getColIndex() == this.entry.getColumnIndex("amount7")||e.getColIndex() == this.entry.getColumnIndex("amount8")){
			
			BigDecimal amount1=r.getCell("amount1").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("amount1").getValue());
			BigDecimal amount2=r.getCell("amount2").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("amount2").getValue());
			BigDecimal amount3=r.getCell("amount3").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("amount3").getValue());
			BigDecimal amount4=r.getCell("amount4").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("amount4").getValue());
			BigDecimal amount5=r.getCell("amount5").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("amount5").getValue());
			BigDecimal amount6=r.getCell("amount6").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("amount6").getValue());
			BigDecimal amount7=r.getCell("amount7").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("amount7").getValue());
			BigDecimal amount8=r.getCell("amount8").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("amount8").getValue());
			BigDecimal total=amount1.add(amount2).add(amount3).add(amount4).add(amount5).add(amount6).add(amount7).add(amount8);
			r.getCell("total").setValue(total);
			ClientHelper.getFootRow(this.entry, new String[]{"amount1","amount2","amount3","amount4","amount5","amount6","amount7","amount8","total"});
			
			KDTFootManager footRowManager = this.entry.getFootManager();
			IRow footRow = footRowManager.getFootRow(0);
			this.txtMax.setValue(footRow.getCell("total").getValue());
			
			BigDecimal target=this.txtTarget.getBigDecimalValue();
			BigDecimal max=this.txtMax.getBigDecimalValue();
			
			this.txtRate.setValue(max==null||target==null||max.compareTo(FDCHelper.ZERO)==0||target.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:max.divide(target, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
		}
	}
	protected void txtTarget_dataChanged(DataChangeEvent e) throws Exception {
		BigDecimal target=this.txtTarget.getBigDecimalValue();
		BigDecimal max=this.txtMax.getBigDecimalValue();
		
		this.txtRate.setValue(max==null||target==null||max.compareTo(FDCHelper.ZERO)==0||target.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:max.divide(target, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
	}
	public void loadFields() {
		super.loadFields();
		ClientHelper.getFootRow(this.entry, new String[]{"amount1","amount2","amount3","amount4","amount5","amount6","amount7","amount8","total"});
	}
	public void onLoad() throws Exception {
		super.onLoad();
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
    	
    	this.btnAddLine.setVisible(false);
    	this.btnInsertLine.setVisible(false);
    	this.btnRemoveLine.setVisible(false);
    	this.contEntry.add(this.actionAddLine);
    	this.contEntry.add(this.actionInsertLine);
    	this.contEntry.add(this.actionRemoveLine);
    	
    	KDBizPromptBox personSelector = new KDBizPromptBox();
    	FDCClientUtils.setPersonF7(personSelector, null, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
    	KDTDefaultCellEditor personEditor = new KDTDefaultCellEditor(personSelector);
		this.entry.getColumn("person").setEditor(personEditor);
		this.txtName.setEnabled(false);
	}
	
	protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return MonthExcitationFactory.getRemoteInstance();
	}
	
	protected KDTable getDetailTable() {
		return this.entry;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewDetailData(KDTable table) {
		return new MonthExcitationEntryInfo();
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("year",editData.getYear()));
		filter.getFilterItems().add(new FilterItemInfo("month",editData.getMonth()));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",editData.getSellProject().getId().toString()));
		if(editData.getId() != null){
			filter.getFilterItems().add(new FilterItemInfo("id",editData.getId().toString(),CompareType.NOTEQUALS));
		}
		
		boolean isExist = MonthExcitationFactory.getRemoteInstance().exists(filter);
		if(isExist){
			FDCMsgBox.showError("当月已经 生成过月度销售激励测算明细表，不能重复生成。");
    		abort();
		}
		super.verifyInput(e);
	}
	protected IObjectValue createNewData() {
		MonthExcitationInfo info = new MonthExcitationInfo();
		SysContext ctx = SysContext.getSysContext();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String strMonth =sdf.format(new Date());
		info.setMonth(c.get(Calendar.MONTH));
		info.setYear(c.get(Calendar.YEAR));
		
		info.setCreator(ctx.getCurrentUserInfo());
		info.setOrgUnit(ctx.getCurrentOrgUnit().castToFullOrgUnitInfo());
		Map uiContext = (Map) getUIContext();
		SellProjectInfo sellProject = (SellProjectInfo) uiContext.get("sellProject");
		info.setSellProject(sellProject);		
		return info;
	}
	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();
		for(int i=0;i<this.entry.getRowCount();i++){
			for(int j=0;j<entry.getColumnCount();j++){
				if(entry.getColumn(j).isRequired()){
					if(entry.getRow(i).getCell(j).getValue()==null){
						FDCMsgBox.showWarning("明细不能为空！");
						entry.getEditManager().editCellAt(i, j);
			    		abort();
					}
				}
			}
		}
	}
}