/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import bsh.This;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ClientHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.BrandCollection;
import com.kingdee.eas.fdc.tenancy.CommissionSettlementBillEntryCollection;
import com.kingdee.eas.fdc.tenancy.CommissionSettlementBillEntryInfo;
import com.kingdee.eas.fdc.tenancy.CommissionSettlementBillFactory;
import com.kingdee.eas.fdc.tenancy.CommissionSettlementBillInfo;
import com.kingdee.eas.fdc.tenancy.CommissionTenancyEntryCollection;
import com.kingdee.eas.fdc.tenancy.CommissionTenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.CustomerEntryBrandCollection;
import com.kingdee.eas.fdc.tenancy.CustomerEntryBrandInfo;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.QuitTenancyInfo;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.AdvMsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class CommissionSettlementBillEditUI extends AbstractCommissionSettlementBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CommissionSettlementBillEditUI.class);
    private Boolean isLoad=false;
    private int year_old = 0;
	private int month_old =0;
    public CommissionSettlementBillEditUI() throws Exception
    {
        super();
    }
    private void spinnerChange() throws Exception{
    	if (isLoad) {
			return;
		}
		int result = MsgBox.OK;
		if (this.tblMgrBonus.getRowCount() > 0) {
			result = FDCMsgBox.showConfirm2(this,"改变编制月份将重新获取分录数据！");
		}
		if (result == MsgBox.OK) {
			year_old = this.kDSpinnerYear.getIntegerVlaue().intValue();
			month_old=this.kDSpinnerMonth.getIntegerVlaue().intValue();
		} else {
			this.kDSpinnerYear.setValue(year_old, false);
			this.kDSpinnerMonth.setValue(month_old,false);
			return;
		}
		this.actionCalcMgrBonus_actionPerformed(null);
    }
    protected void kDSpinnerMonth_stateChanged(ChangeEvent e) throws Exception {
    	spinnerChange();
	}

	protected void kDSpinnerYear_stateChanged(ChangeEvent e) throws Exception {
		spinnerChange();
	}
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	isLoad=true;
    	this.txtName.setEnabled(false);
    	this.contName.setEnabled(false);
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
    	
    	Calendar c = Calendar.getInstance();
    	int year = c.get(Calendar.YEAR);
    	SpinnerNumberModel m = new SpinnerNumberModel();
        m.setMaximum(year+100);
        m.setMinimum(year-100);
    	this.kDSpinnerYear.setModel(m);
    	this.kDSpinnerYear.setValue(editData.getYear());
    	
    	
    	SpinnerNumberModel mm = new SpinnerNumberModel();
        mm.setMaximum(12);
        mm.setMinimum(1);
        this.kDSpinnerMonth.setModel(mm);
        this.kDSpinnerMonth.setValue(editData.getMonth());
    	
    	this.contManager.addButton(this.btnAddMgrBonus);
    	this.contManager.addButton(this.btnRemoveMgrBonus);
    	this.contKD.addButton(this.btnRemoveKD);
    	
    	this.checkAllTableRows();
    	
    	this.tblTotal.setEnabled(false);
    	
    	for(int i=0;i<this.tblFF.getColumnCount();i++){
    		if(!this.tblFF.getColumnKey(i).equals("remark")){
    			this.tblFF.getColumn(i).getStyleAttributes().setLocked(true);
    		}
    	}
    	
    	for(int i=0;i<this.tblKD.getColumnCount();i++){
    		if(!this.tblKD.getColumnKey(i).equals("remark")&&!this.tblKD.getColumnKey(i).equals("revRent")){
    			this.tblKD.getColumn(i).getStyleAttributes().setLocked(true);
    		}
    	}
    	this.tblKD.getColumn("revRent").setRequired(true);
    	
    	for(int i=0;i<this.tblRev.getColumnCount();i++){
    		if(!this.tblRev.getColumnKey(i).equals("remark")){
    			this.tblRev.getColumn(i).getStyleAttributes().setLocked(true);
    		}
    	}
    	for(int i=0;i<this.tblRevKD.getColumnCount();i++){
    		if(!this.tblRevKD.getColumnKey(i).equals("remark")){
    			this.tblRevKD.getColumn(i).getStyleAttributes().setLocked(true);
    		}
    	}
    	for(int i=0;i<this.tblQuit.getColumnCount();i++){
    		if(!this.tblQuit.getColumnKey(i).equals("remark")){
    			this.tblQuit.getColumn(i).getStyleAttributes().setLocked(true);
    		}
    	}
    	this.tblQuit.getColumn("baseAmount").getStyleAttributes().setFontColor(Color.RED);
    	KDBizPromptBox personSelector = new KDBizPromptBox();
    	personSelector.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent e) {
				KDBizPromptBox c = (KDBizPromptBox) e.getSource();
				tblMgrBonus.getCell(tblMgrBonus.getSelectManager().getActiveRowIndex(), "person").setUserObject(e.getNewValue());
				
			}});
    	FDCClientUtils.setPersonF7(personSelector, null, null);
    	KDTDefaultCellEditor personEditor = new KDTDefaultCellEditor(personSelector);
    	this.tblMgrBonus.getColumn("person").setEditor(personEditor);
    	this.tblMgrBonus.getColumn("person").setRequired(true);
    	this.tblMgrBonus.getColumn("position").setRequired(true);
    	this.tblMgrBonus.getColumn("monthArea").setRequired(true);
    	this.tblMgrBonus.getColumn("zcRate").setRequired(true);
    	this.tblMgrBonus.getColumn("zcCommissionRate").setRequired(true);
    	this.tblMgrBonus.getColumn("zjRate").setRequired(true);
    	this.tblMgrBonus.getColumn("zjCommissionRate").setRequired(true);
    	this.tblMgrBonus.getColumn("quitAmount").setRequired(true);
    	this.tblMgrBonus.getColumn("tc").setRequired(true);
    	this.tblMgrBonus.getColumn("xz").setRequired(true);
    	this.tblMgrBonus.getColumn("ylRate").setRequired(true);
    	this.tblMgrBonus.getColumn("jl").setRequired(true);
    	
    	this.tblMgrBonus.getColumn("orgUnit").getStyleAttributes().setLocked(true);
    	this.tblMgrBonus.getColumn("sellProject").getStyleAttributes().setLocked(true);
    	this.tblMgrBonus.getColumn("contractArea").getStyleAttributes().setLocked(true);
    	this.tblMgrBonus.getColumn("rate").getStyleAttributes().setLocked(true);
    	this.tblMgrBonus.getColumn("zcBaseAmount").getStyleAttributes().setLocked(true);
    	this.tblMgrBonus.getColumn("zcAmount").getStyleAttributes().setLocked(true);
    	this.tblMgrBonus.getColumn("zjBaseAmount").getStyleAttributes().setLocked(true);
    	this.tblMgrBonus.getColumn("zjAmount").getStyleAttributes().setLocked(true);
    	this.tblMgrBonus.getColumn("zjBaseAmount").getStyleAttributes().setLocked(true);
    	this.tblMgrBonus.getColumn("yl").getStyleAttributes().setLocked(true);
    	this.tblMgrBonus.getColumn("yj").getStyleAttributes().setLocked(true);
    	this.tblMgrBonus.getColumn("quitAmount").getStyleAttributes().setFontColor(Color.RED);
    	 ObjectValueRender r=new ObjectValueRender(){
     		public String getText(Object obj) {
     			if(obj!=null){
     				return obj.toString()+"%";
     			}
     			return super.getText(obj);
     		}
     	};
     	this.tblMgrBonus.getColumn("rate").setRenderer(r);
     	this.tblMgrBonus.getColumn("zcRate").setRenderer(r);
     	this.tblMgrBonus.getColumn("zjRate").setRenderer(r);
     	this.tblMgrBonus.getColumn("ylRate").setRenderer(r);
         
     	this.tblFF.getColumn("freeDays").getStyleAttributes().setHided(true);
     	this.tblKD.getColumn("freeDays").getStyleAttributes().setHided(true);
     	this.tblRev.getColumn("freeDays").getStyleAttributes().setHided(true);
     	this.tblRevKD.getColumn("freeDays").getStyleAttributes().setHided(true);
     	this.tblQuit.getColumn("freeDays").getStyleAttributes().setHided(true);
     	
        isLoad=false;
        
        this.contOrgUnit.setVisible(false);
    }
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add("CU.*");
    	sic.add("state");
    	sic.add("entry.*");
    	sic.add("entry.orgUnit.*");
    	sic.add("entry.person.*");
    	sic.add("entry.sellProject.*");
    	sic.add("tenancyEntry.*");
    	sic.add("tenancyEntry.tenancyBill.id");
    	sic.add("tenancyEntry.revEntry.id");
    	return sic;
    }
    public void loadFields() {
    	isLoad=true;
    	super.loadFields();

    	this.checkAllTableRows();
    	this.removeAllTableRows();
    	try {
			this.loadDataOnTable(this.editData.getEntry(),false,null,null,null);
			this.loadDataOnTable(this.editData.getTenancyEntry(), false);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		this.loadTotalDataOnTable();
		this.groupAllTableRows();
		this.addFootRow();
		this.fitColumnWidth();
		
		year_old=this.kDSpinnerYear.getIntegerVlaue();
		month_old=this.kDSpinnerMonth.getIntegerVlaue();
		
		isLoad=false;
    }
    private void addFootRow(){
    	getFootRow(this.tblTotal, new String[]{"tc","xz","yl","jl","yj"});
    	getFootRow(this.tblMgrBonus, new String[]{"monthArea","contractArea","zcBaseAmount","zjBaseAmount","zcAmount","zjBaseAmount","quitAmount","tc","xz","yl","jl","yj"});
        getFootRow(this.tblFF, new String[]{"leaseCount","freeDays","area","","baseAmount","totalRent","bz"});
        getFootRow(this.tblKD, new String[]{"leaseCount","freeDays","area","","baseAmount","revRent"});
        getFootRow(this.tblRev, new String[]{"leaseCount","freeDays","area","","baseAmount","appAmount"});
        getFootRow(this.tblRevKD, new String[]{"leaseCount","freeDays","area","","baseAmount","appAmount"});
        getFootRow(this.tblQuit, new String[]{"leaseCount","freeDays","area","","baseAmount","days","revRent","revBZRent"});
    }
    public void getFootRow(KDTable tblMain,String[] columnName){
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
    public BigDecimal getColumnValueSum(KDTable table,String columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
        	if(table.equals(this.tblTotal)&&table.getRow(i).getCell("person").getValue().toString().equals("小计")){
        		continue;
        	}
        	if(table.getRow(i).getCell(columnName).getValue()!=null ){
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
    private void checkAllTableRows(){
    	this.tblTotal.checkParsed();
    	this.tblMgrBonus.checkParsed();
    	this.tblFF.checkParsed();
    	this.tblKD.checkParsed();
    	this.tblRev.checkParsed();
    	this.tblQuit.checkParsed();
    	this.tblRevKD.checkParsed();
    }
    private void removeAllTableRows(){
    	this.tblTotal.removeRows();
    	this.tblMgrBonus.removeRows();
    	this.tblFF.removeRows();
    	this.tblKD.removeRows();
    	this.tblRev.removeRows();
    	this.tblQuit.removeRows();
    	this.tblRevKD.removeRows();
    }
    private void fitColumnWidth(KDTable table){
    	for(int i=0;i<table.getColumnCount();i++){
    		KDTableHelper.autoFitColumnWidth(table, i);
    	}
    }
    private void fitColumnWidth(){
    	fitColumnWidth(this.tblTotal);
    	fitColumnWidth(this.tblMgrBonus);
    	fitColumnWidth(this.tblFF);
    	fitColumnWidth(this.tblKD);
    	fitColumnWidth(this.tblRev);
    	fitColumnWidth(this.tblQuit);
    	fitColumnWidth(this.tblRevKD);
    }
    private void groupAllTableRows(){
    	this.tblTotal.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
    	this.tblTotal.getGroupManager().setGroup(true);
    	this.tblTotal.getColumn("person").setGroup(true);
    	this.tblTotal.getColumn("orgUnit").setGroup(true);
    	this.tblTotal.getGroupManager().group();
    	
    	this.tblMgrBonus.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
    	this.tblMgrBonus.getGroupManager().setGroup(true);
    	this.tblMgrBonus.getColumn("orgUnit").setGroup(true);
    	this.tblMgrBonus.getColumn("sellProject").setGroup(true);
    	this.tblMgrBonus.getColumn("person").setGroup(false);
    	this.tblMgrBonus.getColumn("position").setGroup(false);
    	this.tblMgrBonus.getGroupManager().group();
    	
    	this.tblFF.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
    	this.tblFF.getGroupManager().setGroup(true);
    	this.tblFF.getColumn("orgUnit").setGroup(true);
    	this.tblFF.getColumn("sellProject").setGroup(true);
    	this.tblFF.getColumn("productType").setGroup(true);
    	this.tblFF.getColumn("build").setGroup(true);
    	this.tblFF.getGroupManager().group();
    	
    	this.tblKD.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
    	this.tblKD.getGroupManager().setGroup(true);
    	this.tblKD.getColumn("orgUnit").setGroup(true);
    	this.tblKD.getColumn("sellProject").setGroup(true);
    	this.tblKD.getColumn("productType").setGroup(true);
    	this.tblKD.getColumn("build").setGroup(true);
    	this.tblKD.getGroupManager().group();
    	
    	this.tblRev.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
    	this.tblRev.getGroupManager().setGroup(true);
    	for(int i=0;i<this.tblRev.getColumnCount();i++){
    		if(!this.tblRev.getColumnKey(i).equals("moneyDefine")&&!this.tblRev.getColumnKey(i).equals("appDate")&&!this.tblRev.getColumnKey(i).equals("appAmount")
    				&&!this.tblRev.getColumnKey(i).equals("revDate")&&!this.tblRev.getColumnKey(i).equals("baseAmount")&&!this.tblRev.getColumnKey(i).equals("adviser")&&!this.tblRev.getColumnKey(i).equals("remark")){
    			this.tblRev.getColumn(i).setGroup(true);
    		}
    	}
    	this.tblRev.getGroupManager().group();
    	
    	this.tblRevKD.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
    	this.tblRevKD.getGroupManager().setGroup(true);
    	for(int i=0;i<this.tblRevKD.getColumnCount();i++){
    		if(!this.tblRevKD.getColumnKey(i).equals("moneyDefine")&&!this.tblRev.getColumnKey(i).equals("appDate")&&!this.tblRevKD.getColumnKey(i).equals("appAmount")
    				&&!this.tblRevKD.getColumnKey(i).equals("revDate")&&!this.tblRev.getColumnKey(i).equals("baseAmount")&&!this.tblRevKD.getColumnKey(i).equals("adviser")&&!this.tblRev.getColumnKey(i).equals("remark")){
    			this.tblRevKD.getColumn(i).setGroup(true);
    		}
    	}
    	this.tblRevKD.getGroupManager().group();
    	
    	this.tblQuit.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
    	this.tblQuit.getGroupManager().setGroup(true);
    	this.tblQuit.getColumn("orgUnit").setGroup(true);
    	this.tblQuit.getColumn("sellProject").setGroup(true);
    	this.tblQuit.getColumn("productType").setGroup(true);
    	this.tblQuit.getColumn("build").setGroup(true);
    	this.tblQuit.getGroupManager().group();
    }
    public void storeFields()
    {
        super.storeFields();
        this.editData.getEntry().clear();
        this.editData.getTenancyEntry().clear();
        
        storeEntryTable();
        storeTenTable(this.tblFF);
        storeTenTable(this.tblKD);
        storeTenTable(this.tblRev);
        storeTenTable(this.tblRevKD);
        storeTenTable(this.tblQuit);
        
        editData.setYear(Integer.valueOf( this.kDSpinnerYear.getModel().getValue()+""));
        editData.setMonth(Integer.valueOf( this.kDSpinnerMonth.getModel().getValue()+""));
        
        
		editData.setName(editData.getYear()+"年"+editData.getMonth()+"月_佣金结算审批单");
		this.txtName.setName(editData.getName());
    }
    private void storeTenTable(KDTable table){
    	IRow r = null;
        for(int i =0;i<table.getRowCount();i++){
        	r = table.getRow(i);
        	CommissionTenancyEntryInfo entry = (CommissionTenancyEntryInfo) r.getUserObject();
        	if(entry.getType()==2){
        		entry.setRevRent(toBigDecimal(r.getCell("revRent").getValue()));
        	}else if(entry.getType()==4){
        		entry.setRevRent(toBigDecimal(r.getCell("revRent").getValue()));
        		entry.setRevBZRent(toBigDecimal(r.getCell("revBZRent").getValue()));
        	}
        	entry.setBaseAmount(toBigDecimal(r.getCell("baseAmount").getValue()));
        	entry.setRemark((String) r.getCell("remark").getValue());
        	editData.getTenancyEntry().add(entry);
        }
    }
    private void storeEntryTable(){
    	IRow r = null;
        for(int i =0;i<this.tblMgrBonus.getRowCount();i++){
        	r = this.tblMgrBonus.getRow(i);
        	CommissionSettlementBillEntryInfo entry = (CommissionSettlementBillEntryInfo) r.getUserObject();
			
			entry.setPerson((PersonInfo) r.getCell("person").getValue());
			entry.setPosition((String) r.getCell("position").getValue());
        	entry.setMonthArea(toBigDecimal(r.getCell("monthArea").getValue()));
        	entry.setContractArea(toBigDecimal(r.getCell("contractArea").getValue()));
        	entry.setRate(toBigDecimal(r.getCell("rate").getValue()));
        	entry.setZcBaseAmount(toBigDecimal(r.getCell("zcBaseAmount").getValue()));
        	entry.setZcRate(toBigDecimal(r.getCell("zcRate").getValue()));
        	entry.setZcCommissionRate(toBigDecimal(r.getCell("zcCommissionRate").getValue()));
        	entry.setZcAmount(toBigDecimal(r.getCell("zcAmount").getValue()));
        	entry.setZjBaseAmount(toBigDecimal(r.getCell("zjBaseAmount").getValue()));
        	entry.setZjRate(toBigDecimal(r.getCell("zjRate").getValue()));
        	entry.setZjCommissionRate(toBigDecimal(r.getCell("zjCommissionRate").getValue()));
        	entry.setZjAmount(toBigDecimal(r.getCell("zjAmount").getValue()));
        	
        	entry.setQuitAmount(toBigDecimal(r.getCell("quitAmount").getValue()));
        	entry.setTc(toBigDecimal(r.getCell("tc").getValue()));
        	entry.setXz(toBigDecimal(r.getCell("xz").getValue()));
        	entry.setYlRate(toBigDecimal(r.getCell("ylRate").getValue()));
        	entry.setYl(toBigDecimal(r.getCell("yl").getValue()));
        	entry.setJl(toBigDecimal(r.getCell("jl").getValue()));
        	entry.setYj(toBigDecimal(r.getCell("yj").getValue()));
        	entry.setRemark((String) r.getCell("remark").getValue());
        	
        	editData.getEntry().add(entry);
        }
    }
	protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return CommissionSettlementBillFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String strMonth =sdf.format(new Date());
		
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("month",strMonth));
    	
    	boolean isExist = CommissionSettlementBillFactory.getRemoteInstance().exists(filter);
    	
    	if(isExist){
    		FDCMsgBox.showError("当月已经 生成过佣金结算审批单，不能重复生成。");
    		abort();
    	}
		super.actionAddNew_actionPerformed(e);
	}
	
	protected IObjectValue createNewData() {
		CommissionSettlementBillInfo info = new CommissionSettlementBillInfo();
		SysContext ctx = SysContext.getSysContext();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String strMonth =sdf.format(new Date());
		info.setMonth(c.get(Calendar.MONTH));
		info.setYear(c.get(Calendar.YEAR));
		info.setCreator(ctx.getCurrentUserInfo());
		try {
			info.setOrgUnit(FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID)));
			info.setCU(CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID)));
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("year",editData.getYear()));
		filter.getFilterItems().add(new FilterItemInfo("month",editData.getMonth()));
		if(editData.getId() != null){
			filter.getFilterItems().add(new FilterItemInfo("id",editData.getId().toString(),CompareType.NOTEQUALS));
		}
		boolean isExist = CommissionSettlementBillFactory.getRemoteInstance().exists(filter);
		if(isExist){
			FDCMsgBox.showError("当月已经 生成过佣金结算审批单，不能重复生成。");
    		abort();
		}
		super.verifyInput(e);
	}
	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();
		if(tblMgrBonus.getRowCount()==0){
			FDCMsgBox.showWarning("佣金结算计算表数据不能为空！");
    		abort();
		}
		for(int i=0;i<tblMgrBonus.getRowCount();i++){
			for(int j=0;j<tblMgrBonus.getColumnCount();j++){
				if(tblMgrBonus.getColumn(j).isRequired()){
					if(tblMgrBonus.getRow(i).getCell(j).getValue()==null){
						FDCMsgBox.showWarning("佣金结算计算表数据不能为空！");
						tblMgrBonus.getEditManager().editCellAt(i, j);
			    		abort();
					}
				}
			}
		}
		for(int i=0;i<tblKD.getRowCount();i++){
			if(tblKD.getRow(i).getCell("revRent").getValue()==null){
				FDCMsgBox.showWarning("招商租赁佣金（扣点方式）数据不能为空！");
				tblKD.getEditManager().editCellAt(i, tblKD.getColumnIndex("revRent"));
	    		abort();
			}
		}
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.actionAddMgrBonus.setEnabled(false);
			this.actionRemoveMgrBonus.setEnabled(false);
			this.actionCalcMgrBonus.setEnabled(false);
			this.actionRemoveKD.setEnabled(false);
		}else{
			this.actionAddMgrBonus.setEnabled(true);
			this.actionRemoveMgrBonus.setEnabled(true);
			this.actionCalcMgrBonus.setEnabled(true);
			this.actionRemoveKD.setEnabled(true);
		}
	}
	public void actionAddMgrBonus_actionPerformed(ActionEvent e)throws Exception {
		int activeRowIndex = tblMgrBonus.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("请先选择一行数据");
			abort();
		}
		FullOrgUnitInfo orgUnit=((CommissionSettlementBillEntryInfo)tblMgrBonus.getRow(activeRowIndex).getUserObject()).getOrgUnit();
		SellProjectInfo sellProject=((CommissionSettlementBillEntryInfo)tblMgrBonus.getRow(activeRowIndex).getUserObject()).getSellProject();
		
		Object contractArea=tblMgrBonus.getRow(activeRowIndex).getCell("contractArea").getValue();
		Object zcBaseAmount=tblMgrBonus.getRow(activeRowIndex).getCell("zcBaseAmount").getValue();
		Object zjBaseAmount=tblMgrBonus.getRow(activeRowIndex).getCell("zjBaseAmount").getValue();
		
		int top = tblMgrBonus.getSelectManager().get().getTop();
		IRow row = tblMgrBonus.addRow(top+1);
		CommissionSettlementBillEntryInfo entry=new CommissionSettlementBillEntryInfo();
		entry.setOrgUnit(orgUnit);
		entry.setSellProject(sellProject);
		row.setUserObject(entry);
		row.getCell("orgUnit").setValue(entry.getOrgUnit().getName());
		row.getCell("sellProject").setValue(entry.getSellProject().getName());
		row.getCell("contractArea").setValue(contractArea);
		row.getCell("zcBaseAmount").setValue(zcBaseAmount);
		row.getCell("zjBaseAmount").setValue(zjBaseAmount);
		
		this.tblMgrBonus.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
		this.tblMgrBonus.getGroupManager().setGroup(true);
		this.tblMgrBonus.getColumn("orgUnit").setGroup(true);
    	this.tblMgrBonus.getColumn("sellProject").setGroup(true);
    	this.tblMgrBonus.getColumn("person").setGroup(false);
    	this.tblMgrBonus.getColumn("position").setGroup(false);
    	this.tblMgrBonus.getGroupManager().group();
	}
	private void removeRows(KDTable eventSource){
		int activeRowIndex = eventSource.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("请先选择一行数据");
			abort();
		}
		eventSource.removeRow(activeRowIndex);
	}
	public void actionRemoveMgrBonus_actionPerformed(ActionEvent e)
			throws Exception {
		int activeRowIndex = tblMgrBonus.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("请先选择一行数据");
			abort();
		}
		removeRows(tblMgrBonus);
	}
	public void actionRemoveKD_actionPerformed(ActionEvent e)
		throws Exception {
		int activeRowIndex = tblKD.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("请先选择一行数据");
			abort();
		}
		IRow r = this.tblKD.getRow(activeRowIndex);
		String orgUnitId=((CommissionTenancyEntryInfo)r.getUserObject()).getTenancyBill().getOrgUnit().getId().toString();
		removeRows(tblKD);
		setZCBaseAmount(orgUnitId);
	}
	public void actionCalcMgrBonus_actionPerformed(ActionEvent e)throws Exception {
		if(this.tblMgrBonus.getRowCount()>0&&e!=null){
			int result = FDCMsgBox.showConfirm2("是否需要重新进行计算?");
			if(AdvMsgBox.OK_OPTION != result){
				abort();
			}
		}
		this.removeAllTableRows();
		
		Set sellProjectSet=new HashSet();
		CommissionSettlementBillEntryCollection entryCol=new CommissionSettlementBillEntryCollection();
		int year = this.kDSpinnerYear.getIntegerVlaue();
        int month = this.kDSpinnerMonth.getIntegerVlaue();
        
        Date t=FDCDateHelper.stringToDate(year+"-"+month+"-01");
	    Date startDate = FDCDateHelper.getFirstDayOfMonth(t);
    	Date endDate =  FDCDateHelper.getLastDayOfMonth(t);
        
		StringBuilder sql = new StringBuilder();
		sql.append(" /*dialect*/ select t.sellProjectId,t.id,t.tenRoomsDes,t.orgId,t.orgName,t.orgLongNumber from(select con.fsellProjectId sellProjectId,con.fid id,con.ftenRoomsDes tenRoomsDes,con.forgUnitid orgId,org.fname_l2 orgName,org.flongNumber orgLongNumber from t_ten_tenancyBill con left join T_TEN_TenancyRoomEntry room on con.fid=room.ftenancyId ");
		sql.append(" left join T_TEN_TenancyRoomPayListEntry pay on room.fid=pay.ftenRoomId left join T_SHE_MoneyDefine md on pay.fmoneyDefineId=md.fid left join T_ORG_BaseUnit org on org.fid=con.forgUnitId ");
		sql.append(" where con.fsourceFunction is null and con.ftenancyState='Executing' and con.ftenancyType='1NewTenancy' and con.fconRentType='ZJ' ");
		sql.append(" and pay.fappAmount=pay.factRevAmount and con.fauditTime<=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss') " );
		sql.append(" and md.fname_l2 in('租金','押金') group by con.fsellProjectId,con.fid,con.ftenRoomsDes,con.forgUnitid,org.fname_l2,org.flongNumber,md.fname_l2)t group by t.sellProjectId,t.id,t.tenRoomsDes,t.orgId,t.orgName,t.orgLongNumber having count(*)=2" );
		
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		IRowSet rowSet = _builder.executeQuery();
		CommissionTenancyEntryCollection col=new CommissionTenancyEntryCollection();
		while(rowSet.next()) {
			CommissionTenancyEntryInfo info=new CommissionTenancyEntryInfo();
			TenancyBillInfo ten=new TenancyBillInfo();
			ten.setId(BOSUuid.read(rowSet.getString("id")));
			ten.setTenRoomsDes(rowSet.getString("tenRoomsDes"));
			info.setTenancyBill(ten);
			info.setType(1);
			
			col.add(info);
			
			if(!sellProjectSet.contains(rowSet.getString("sellProjectId"))){
				sellProjectSet.add(rowSet.getString("sellProjectId"));
				CommissionSettlementBillEntryInfo entry=new CommissionSettlementBillEntryInfo();
				FullOrgUnitInfo orgInfo=new FullOrgUnitInfo();
				orgInfo.setId(BOSUuid.read(rowSet.getString("orgId")));
				orgInfo.setName(rowSet.getString("orgName"));
				orgInfo.setLongNumber(rowSet.getString("orgLongNumber"));
				entry.setOrgUnit(orgInfo);
				
				SellProjectInfo sellProject=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(rowSet.getString("sellProjectId")));
				entry.setSellProject(sellProject);
				
				
				entryCol.add(entry);
			}
		}
		this.loadDataOnTable(col, true);
		
		sql = new StringBuilder();
		sql.append(" /*dialect*/ select con.fsellProjectId sellProjectId,con.fid id,con.ftenRoomsDes tenRoomsDes,con.forgUnitid orgId,org.fname_l2 orgName,org.flongNumber orgLongNumber from t_ten_tenancyBill con left join T_ORG_BaseUnit org on org.fid=con.forgUnitId ");
		sql.append(" where con.fsourceFunction is null and con.ftenancyState='Executing' and con.ftenancyType='1NewTenancy' and con.fconRentType='KD'");
		
		_builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		rowSet = _builder.executeQuery();
		col=new CommissionTenancyEntryCollection();
		while(rowSet.next()) {
			CommissionTenancyEntryInfo info=new CommissionTenancyEntryInfo();
			TenancyBillInfo ten=new TenancyBillInfo();
			ten.setId(BOSUuid.read(rowSet.getString("id")));
			ten.setTenRoomsDes(rowSet.getString("tenRoomsDes"));
			info.setTenancyBill(ten);
			info.setType(2);
			
			col.add(info);
			
			if(!sellProjectSet.contains(rowSet.getString("sellProjectId"))){
				sellProjectSet.add(rowSet.getString("sellProjectId"));
				CommissionSettlementBillEntryInfo entry=new CommissionSettlementBillEntryInfo();
				FullOrgUnitInfo orgInfo=new FullOrgUnitInfo();
				orgInfo.setId(BOSUuid.read(rowSet.getString("orgId")));
				orgInfo.setName(rowSet.getString("orgName"));
				orgInfo.setLongNumber(rowSet.getString("orgLongNumber"));
				entry.setOrgUnit(orgInfo);
				
				SellProjectInfo sellProject=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(rowSet.getString("sellProjectId")));
				entry.setSellProject(sellProject);
				
				entryCol.add(entry);
			}
		}
		this.loadDataOnTable(col, true);
		
		sql = new StringBuilder();
		sql.append(" /*dialect*/ select con.fsellProjectId sellProjectId,entry.fid id,rev.ftenancyObjId conId,entry.frevAmount baseAmount,con.ftenRoomsDes tenRoomsDes,con.forgUnitid orgId,org.fname_l2 orgName,org.flongNumber orgLongNumber from T_BDC_FDCReceivingBillEntry entry left join T_BDC_FDCReceivingBill rev on entry.fheadid=rev.fid ");
		sql.append(" left join T_SHE_MoneyDefine md on entry.fmoneyDefineId=md.fid left join t_ten_tenancyBill con on con.fid=rev.ftenancyObjId left join T_ORG_BaseUnit org on org.fid=con.forgUnitId ");
		sql.append(" where md.fname_l2 ='租金' and rev.fbillStatus=12 and con.fconRentType!='KD'");
		sql.append(" and rev.fbizDate<=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss') " );
		sql.append(" and rev.fbizDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss') " );
		
		_builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		rowSet = _builder.executeQuery();
		col=new CommissionTenancyEntryCollection();
		while(rowSet.next()) {
			CommissionTenancyEntryInfo info=new CommissionTenancyEntryInfo();
			
			TenancyBillInfo ten=new TenancyBillInfo();
			ten.setId(BOSUuid.read(rowSet.getString("conId")));
			ten.setTenRoomsDes(rowSet.getString("tenRoomsDes"));
			info.setTenancyBill(ten);
			
			FDCReceivingBillEntryInfo revEntry=new FDCReceivingBillEntryInfo();
			revEntry.setId(BOSUuid.read(rowSet.getString("id")));
			info.setRevEntry(revEntry);
			info.setType(3);
			
			info.setBaseAmount(rowSet.getBigDecimal("baseAmount"));
			col.add(info);
			
			if(!sellProjectSet.contains(rowSet.getString("sellProjectId"))){
				sellProjectSet.add(rowSet.getString("sellProjectId"));
				CommissionSettlementBillEntryInfo entry=new CommissionSettlementBillEntryInfo();
				FullOrgUnitInfo orgInfo=new FullOrgUnitInfo();
				orgInfo.setId(BOSUuid.read(rowSet.getString("orgId")));
				orgInfo.setName(rowSet.getString("orgName"));
				orgInfo.setLongNumber(rowSet.getString("orgLongNumber"));
				entry.setOrgUnit(orgInfo);
				
				SellProjectInfo sellProject=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(rowSet.getString("sellProjectId")));
				entry.setSellProject(sellProject);
				
				entryCol.add(entry);
			}
		}
		this.loadDataOnTable(col, true);
		
		sql = new StringBuilder();
		sql.append(" /*dialect*/ select con.fsellProjectId sellProjectId,entry.fid id,rev.ftenancyObjId conId,entry.frevAmount baseAmount,con.ftenRoomsDes tenRoomsDes,con.forgUnitid orgId,org.fname_l2 orgName,org.flongNumber orgLongNumber from T_BDC_FDCReceivingBillEntry entry left join T_BDC_FDCReceivingBill rev on entry.fheadid=rev.fid ");
		sql.append(" left join T_SHE_MoneyDefine md on entry.fmoneyDefineId=md.fid left join t_ten_tenancyBill con on con.fid=rev.ftenancyObjId left join T_ORG_BaseUnit org on org.fid=con.forgUnitId ");
		sql.append(" where (md.fname_l2 ='租金（按照营业额）' or md.fname_l2 ='租金') and rev.fbillStatus=12 and con.fconRentType='KD'");
		sql.append(" and rev.fbizDate<=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss') " );
		sql.append(" and rev.fbizDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss') " );
		
		_builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		rowSet = _builder.executeQuery();
		col=new CommissionTenancyEntryCollection();
		while(rowSet.next()) {
			CommissionTenancyEntryInfo info=new CommissionTenancyEntryInfo();
			
			TenancyBillInfo ten=new TenancyBillInfo();
			ten.setId(BOSUuid.read(rowSet.getString("conId")));
			ten.setTenRoomsDes(rowSet.getString("tenRoomsDes"));
			info.setTenancyBill(ten);
			
			FDCReceivingBillEntryInfo revEntry=new FDCReceivingBillEntryInfo();
			revEntry.setId(BOSUuid.read(rowSet.getString("id")));
			info.setRevEntry(revEntry);
			info.setType(5);
			
			info.setBaseAmount(rowSet.getBigDecimal("baseAmount"));
			col.add(info);
			
			if(!sellProjectSet.contains(rowSet.getString("sellProjectId"))){
				sellProjectSet.add(rowSet.getString("sellProjectId"));
				CommissionSettlementBillEntryInfo entry=new CommissionSettlementBillEntryInfo();
				FullOrgUnitInfo orgInfo=new FullOrgUnitInfo();
				orgInfo.setId(BOSUuid.read(rowSet.getString("orgId")));
				orgInfo.setName(rowSet.getString("orgName"));
				orgInfo.setLongNumber(rowSet.getString("orgLongNumber"));
				entry.setOrgUnit(orgInfo);
				
				SellProjectInfo sellProject=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(rowSet.getString("sellProjectId")));
				entry.setSellProject(sellProject);
				
				entryCol.add(entry);
			}
		}
		
		this.loadDataOnTable(col, true);
		
		sql = new StringBuilder();
		sql.append(" select con.fid id,t1.amount revRent,t2.amount revBZRent,con.fsourceFunction baseAmount,con.ftenRoomsDes tenRoomsDes from t_ten_tenancyBill con left join T_TEN_QuitTenancy quit on quit.ftenancyBillId=con.fid ");
		sql.append(" left join(select sum(entry.frevAmount) amount,rev.ftenancyObjId conId from T_BDC_FDCReceivingBillEntry entry left join T_BDC_FDCReceivingBill rev on entry.fheadid=rev.fid ");
		sql.append(" left join T_SHE_MoneyDefine md on entry.fmoneyDefineId=md.fid ");
		sql.append(" where md.fname_l2 ='租金' and rev.fbillStatus=12 group by rev.ftenancyObjId ) t1 on t1.conId=con.fid");
		
		sql.append(" left join(select sum(entry.frevAmount) amount,rev.ftenancyObjId conId from T_BDC_FDCReceivingBillEntry entry left join T_BDC_FDCReceivingBill rev on entry.fheadid=rev.fid ");
		sql.append(" left join T_SHE_MoneyDefine md on entry.fmoneyDefineId=md.fid ");
		sql.append(" where md.fname_l2 ='押金' and rev.fbillStatus=12 group by rev.ftenancyObjId ) t2 on t2.conId=con.fid");
		
		sql.append(" where con.fsourceFunction is not null and quit.fsourceFunction is null and quit.fstate='4AUDITTED' and DATEDIFF(dd,con.fstartDate,quit.fquitDate)<=365 and con.fconRentType='ZJ' ");
		
		_builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		rowSet = _builder.executeQuery();
		col=new CommissionTenancyEntryCollection();
		while(rowSet.next()) {
			CommissionTenancyEntryInfo info=new CommissionTenancyEntryInfo();
			TenancyBillInfo ten=new TenancyBillInfo();
			ten.setId(BOSUuid.read(rowSet.getString("id")));
			ten.setTenRoomsDes(rowSet.getString("tenRoomsDes"));
			info.setTenancyBill(ten);
			
			info.setRevRent(rowSet.getBigDecimal("revRent"));
			info.setRevBZRent(rowSet.getBigDecimal("revBZRent"));
			info.setBaseAmount(rowSet.getBigDecimal("baseAmount"));
			info.setType(4);
			
			col.add(info);
		}
		this.loadDataOnTable(col, true);
		
		sql = new StringBuilder();
		sql.append(" /*dialect*/ select con.forgUnitid orgId,org.fname_l2 orgName,org.flongNumber orgLongNumber,con.fsellProjectId sellProjectId from t_ten_tenancyBill con left join T_TEN_TenancyRoomEntry room on con.fid=room.ftenancyId ");
		sql.append(" left join T_ORG_BaseUnit org on org.fid=con.forgUnitId where con.ftenancyState='Executing' and con.ftenancyType='1NewTenancy' ");
		sql.append(" and con.fauditTime<=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss') " );
		sql.append(" and con.fauditTime>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss') " );
		sql.append(" group by con.fsellProjectId,con.forgUnitid,org.fname_l2,org.flongNumber ");
		
		_builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		rowSet = _builder.executeQuery();
		col=new CommissionTenancyEntryCollection();
		while(rowSet.next()) {
			if(!sellProjectSet.contains(rowSet.getString("sellProjectId"))){
				sellProjectSet.add(rowSet.getString("sellProjectId"));
				CommissionSettlementBillEntryInfo entry=new CommissionSettlementBillEntryInfo();
				FullOrgUnitInfo orgInfo=new FullOrgUnitInfo();
				orgInfo.setId(BOSUuid.read(rowSet.getString("orgId")));
				orgInfo.setName(rowSet.getString("orgName"));
				orgInfo.setLongNumber(rowSet.getString("orgLongNumber"));
				entry.setOrgUnit(orgInfo);
				
				SellProjectInfo sellProject=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(rowSet.getString("sellProjectId")));
				entry.setSellProject(sellProject);
				
				entryCol.add(entry);
			}
		}
		
		this.loadDataOnTable(col, true);
		this.loadDataOnTable(entryCol,true,this.getAreaMap(),this.sumBaseAmount(),this.sumRevBaseAmount());
		
		this.groupAllTableRows();
		this.addFootRow();
		this.fitColumnWidth();
	}
	private Map sumBaseAmount(){
		Map baseAmountMap=new HashMap();
		for(int i=0;i<this.tblFF.getRowCount();i++){
			String sellProjectId=((CommissionTenancyEntryInfo)this.tblFF.getRow(i).getUserObject()).getTenancyBill().getSellProject().getId().toString();
			BigDecimal baseAmount=toBigDecimal(this.tblFF.getRow(i).getCell("baseAmount").getValue());
			if(baseAmountMap.containsKey(sellProjectId)){
				baseAmountMap.put(sellProjectId, baseAmount.add((BigDecimal) baseAmountMap.get(sellProjectId)));
			}else{
				baseAmountMap.put(sellProjectId, baseAmount);
			}
		}
		for(int i=0;i<this.tblKD.getRowCount();i++){
			String sellProjectId=((CommissionTenancyEntryInfo)this.tblKD.getRow(i).getUserObject()).getTenancyBill().getSellProject().getId().toString();
			BigDecimal baseAmount=toBigDecimal(this.tblKD.getRow(i).getCell("baseAmount").getValue());
			BigDecimal area=toBigDecimal(this.tblKD.getRow(i).getCell("area").getValue());
			if(baseAmountMap.containsKey(sellProjectId)){
				baseAmountMap.put(sellProjectId, baseAmount.add((BigDecimal) baseAmountMap.get(sellProjectId)));
			}else{
				baseAmountMap.put(sellProjectId, baseAmount);
			}
		}
		return baseAmountMap;
	}
	private Map getAreaMap() throws BOSException, SQLException{
		int year = this.kDSpinnerYear.getIntegerVlaue();
        int month = this.kDSpinnerMonth.getIntegerVlaue();
        
        Date t=FDCDateHelper.stringToDate(year+"-"+month+"-01");
	    Date startDate = FDCDateHelper.getFirstDayOfMonth(t);
    	Date endDate =  FDCDateHelper.getLastDayOfMonth(t);
		StringBuilder sql = new StringBuilder();
		sql.append(" /*dialect*/ select con.fsellProjectId sellProjectId,sum(room.fbuildingArea) area from t_ten_tenancyBill con left join T_TEN_TenancyRoomEntry room on con.fid=room.ftenancyId ");
		sql.append(" where con.ftenancyState='Executing' and con.ftenancyType='1NewTenancy' ");
		sql.append(" and con.fauditTime<=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss') " );
		sql.append(" and con.fauditTime>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss') " );
		sql.append(" group by con.fsellProjectId ");
		
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		IRowSet rowSet = _builder.executeQuery();
		
		Map areaMap=new HashMap();
		while(rowSet.next()) {
			areaMap.put(rowSet.getString("sellProjectId"), rowSet.getBigDecimal("area"));
		}
		return areaMap;
	}
	private Map sumRevBaseAmount(){
		Map baseAmountMap=new HashMap();
		for(int i=0;i<this.tblRev.getRowCount();i++){
			String sellProjectId=((CommissionTenancyEntryInfo)this.tblRev.getRow(i).getUserObject()).getTenancyBill().getSellProject().getId().toString();
			BigDecimal baseAmount=toBigDecimal(this.tblRev.getRow(i).getCell("baseAmount").getValue());
			if(baseAmountMap.containsKey(sellProjectId)){
				baseAmountMap.put(sellProjectId, baseAmount.add((BigDecimal) baseAmountMap.get(sellProjectId)));
			}else{
				baseAmountMap.put(sellProjectId, baseAmount);
			}
		}
		for(int i=0;i<this.tblRevKD.getRowCount();i++){
			String sellProjectId=((CommissionTenancyEntryInfo)this.tblRevKD.getRow(i).getUserObject()).getTenancyBill().getSellProject().getId().toString();
			BigDecimal baseAmount=toBigDecimal(this.tblRevKD.getRow(i).getCell("baseAmount").getValue());
			if(baseAmountMap.containsKey(sellProjectId)){
				baseAmountMap.put(sellProjectId, baseAmount.add((BigDecimal) baseAmountMap.get(sellProjectId)));
			}else{
				baseAmountMap.put(sellProjectId, baseAmount);
			}
		}
		return baseAmountMap;
	}
	private void bindDataToRow(IRow r, CommissionTenancyEntryInfo entry,boolean isUpdate) throws EASBizException, BOSException {
		r.setUserObject(entry);
		
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("orgUnit.name");
		sic.add("sellProject.name");
		sic.add("tenancyRoomList.buildingArea");
		sic.add("tenancyRoomList.room.productType.name");
		sic.add("tenancyRoomList.room.building.name");
		sic.add("tenancyState");
		sic.add("tenRoomsDes");
		sic.add("tenancyName");
		sic.add("tenCustomerDes");
		sic.add("tenCustomerList.brandEntry.brand.name");
		sic.add("startDate");
		sic.add("endDate");
		sic.add("freeDays");
		sic.add("tenancyAdviser.name");
		sic.add("dealTotalRent");
		sic.add("depositAmount");
		sic.add("tenancyRoomList.roomPayList.startDate");
		sic.add("tenancyRoomList.roomPayList.endDate");
		sic.add("tenancyRoomList.roomPayList.appAmount");
		sic.add("tenancyRoomList.roomPayList.leaseSeq");
		sic.add("tenancyRoomList.roomPayList.moneyDefine.moneyType");
		sic.add("tenPrice");
		
		TenancyBillInfo ten=TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(entry.getTenancyBill().getId()), sic);
		entry.setTenancyBill(ten);
		r.getCell("orgUnit").setValue(ten.getOrgUnit().getName());
		r.getCell("sellProject").setValue(ten.getSellProject().getName());
		r.getCell("productType").setValue(ten.getTenancyRoomList().get(0).getRoom().getProductType().getName());
		r.getCell("build").setValue(ten.getTenancyRoomList().get(0).getRoom().getBuilding().getName());
		r.getCell("state").setValue(ten.getTenancyState().getAlias());
		r.getCell("room").setValue(ten.getTenRoomsDes());
		r.getCell("name").setValue(ten.getTenancyName());
		r.getCell("customer").setValue(ten.getTenCustomerDes());
	    r.getCell("startDate").setValue(ten.getStartDate());
	    r.getCell("endDate").setValue(ten.getEndDate());
	    r.getCell("leaseCount").setValue(divide(FDCDateHelper.getDiffDays(ten.getStartDate(), ten.getEndDate())+1, new BigDecimal(365),2, BigDecimal.ROUND_HALF_UP));
	    r.getCell("freeDays").setValue(ten.getFreeDays());
	    r.getCell("area").setValue(ten.getTenancyRoomList().get(0).getBuildingArea());
	    
	    r.getCell("adviser").setValue(ten.getTenancyAdviser().getName());
	    r.getCell("remark").setValue(entry.getRemark());
	    
	    CustomerEntryBrandCollection cbc = ten.getTenCustomerList().get(0).getBrandEntry();
		BrandCollection bc = new BrandCollection();
		CustomerEntryBrandInfo cebi = null;
		StringBuffer str = new StringBuffer();
		
		for(Iterator it = cbc.iterator();it.hasNext();){
			cebi = (CustomerEntryBrandInfo) it.next();
			bc.add(cebi.getBrand());
			str.append(cebi.getBrand().getName()+";");
		}
		if(bc.size()>0){
			r.getCell("brand").setValue(str.toString());
		}
		
	    if(!isUpdate){
	    	r.getCell("baseAmount").setValue(entry.getBaseAmount());
	    }
	    if(entry.getType()==1){
	    	r.getCell("totalRent").setValue(ten.getDealTotalRent());
	    	r.getCell("bz").setValue(ten.getDepositAmount());
	    	r.getCell("price").setValue(ten.getTenPrice());
	    	
	    	if(isUpdate){
	    		r.getCell("baseAmount").setValue(this.toBigDecimal(ten.getTenPrice()).multiply(ten.getTenancyRoomList().get(0).getBuildingArea()).multiply(new BigDecimal(45)));
	    	}
	    }else if(entry.getType()==2){
	    	if(!isUpdate){
	    		r.getCell("revRent").setValue(entry.getRevRent());
	    	}
	    }else if(entry.getType()==3||entry.getType()==5){
	    	SelectorItemCollection revSic=new SelectorItemCollection();
	    	revSic.add("moneyDefine.name");
	    	revSic.add("head.bizDate");
	    	revSic.add("revListId");
	    	
	    	FDCReceivingBillEntryInfo revEntry=FDCReceivingBillEntryFactory.getRemoteInstance().getFDCReceivingBillEntryInfo(new ObjectUuidPK(entry.getRevEntry().getId()), revSic);
	    	
	    	r.getCell("moneyDefine").setValue(revEntry.getMoneyDefine().getName());
	    	
	    	if(entry.getType()==3){
	    		TenancyRoomPayListEntryCollection app=TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryCollection("select appDate,appAmount from where id='"+revEntry.getRevListId()+"'");
		    	if(app.size()>0){
		    		r.getCell("appDate").setValue(app.get(0).getAppDate());
			    	r.getCell("appAmount").setValue(app.get(0).getAppAmount());
		    	}
	    	}else{
	    		TenBillOtherPayCollection app=TenBillOtherPayFactory.getRemoteInstance().getTenBillOtherPayCollection("select appDate,appAmount from where id='"+revEntry.getRevListId()+"'");
		    	if(app.size()>0){
		    		r.getCell("appDate").setValue(app.get(0).getAppDate());
			    	r.getCell("appAmount").setValue(app.get(0).getAppAmount());
		    	}
	    	}
	    	r.getCell("revDate").setValue(revEntry.getHead().getBizDate());
	    	if(isUpdate){
	    		r.getCell("baseAmount").setValue(entry.getBaseAmount());
	    	}
	    }else if(entry.getType()==4){
	    	QuitTenancyInfo info=QuitTenancyFactory.getRemoteInstance().getQuitTenancyInfo("select quitDate from where tenancyBill.id='"+ten.getId()+"'");
	    	
	    	r.getCell("quitDate").setValue(info.getQuitDate());
	    	r.getCell("days").setValue(FDCDateHelper.getDiffDays(ten.getStartDate(),info.getQuitDate()));
	    	
	    	r.getCell("baseAmount").setValue(entry.getBaseAmount());
	    	r.getCell("revRent").setValue(entry.getRevRent());
	    	r.getCell("revBZRent").setValue(entry.getRevBZRent());
	    }
	}
	private void bindDataToRow(IRow r, CommissionSettlementBillEntryInfo entry,boolean isUpdate,Map area,Map baseAmount,Map revBaseAmount){
		r.setUserObject(entry);
		r.getCell("orgUnit").setValue(entry.getOrgUnit().getName());
		r.getCell("sellProject").setValue(entry.getSellProject().getName());
		if(!isUpdate){
			r.getCell("person").setValue(entry.getPerson());
			r.getCell("position").setValue(entry.getPosition());
			r.getCell("monthArea").setValue(entry.getMonthArea());
			r.getCell("contractArea").setValue(entry.getContractArea());
			r.getCell("rate").setValue(entry.getRate());
			r.getCell("zcBaseAmount").setValue(entry.getZcBaseAmount());
			r.getCell("zcRate").setValue(entry.getZcRate());
			r.getCell("zcCommissionRate").setValue(entry.getZcCommissionRate());
			r.getCell("zcAmount").setValue(entry.getZcAmount());
			r.getCell("zjBaseAmount").setValue(entry.getZjBaseAmount());
			r.getCell("zjRate").setValue(entry.getZjRate());
			r.getCell("zjCommissionRate").setValue(entry.getZjCommissionRate());
			r.getCell("zjAmount").setValue(entry.getZjAmount());
			
			r.getCell("quitAmount").setValue(entry.getQuitAmount());
			r.getCell("tc").setValue(entry.getTc());
			r.getCell("xz").setValue(entry.getXz());
			r.getCell("ylRate").setValue(entry.getYlRate());
			r.getCell("yl").setValue(entry.getYl());
			r.getCell("jl").setValue(entry.getJl());
			r.getCell("yj").setValue(entry.getYj());
			r.getCell("remark").setValue(entry.getRemark());
		}else{
			String sellProjectId=entry.getSellProject().getId().toString();
			r.getCell("zcBaseAmount").setValue(baseAmount.get(sellProjectId));
			r.getCell("contractArea").setValue(area.get(sellProjectId));
			r.getCell("zjBaseAmount").setValue(revBaseAmount.get(sellProjectId));
		}
	}
	private void loadTotalDataOnTable(){
		Map personMap=new HashMap();
		Map totalMap=new HashMap();
		CommissionSettlementBillEntryCollection col=this.editData.getEntry();
		CRMHelper.sortCollection(col, new String[]{"person.name","orgUnit.longNumber","sellProject.number"}, true);
		for(int i=0;i<col.size();i++){
			FullOrgUnitInfo orgUnit=col.get(i).getOrgUnit();
			SellProjectInfo sellPorject=col.get(i).getSellProject();
			PersonInfo person=col.get(i).getPerson();
			if(person!=null){
				if(this.tblTotal.getRowCount()>0){
					PersonInfo upPerson=(PersonInfo)this.tblTotal.getRow(this.tblTotal.getRowCount()-1).getUserObject();
					if(upPerson!=null&&!upPerson.getId().toString().equals(person.getId().toString())){
						IRow sumRow=this.tblTotal.addRow();
						sumRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
						Map map=(Map) totalMap.get(upPerson.getId().toString());
						sumRow.getCell("person").setValue("小计");
						sumRow.getCell("tc").setValue(map.get("tc"));
						sumRow.getCell("xz").setValue(map.get("xz"));
						sumRow.getCell("yl").setValue(map.get("yl"));
						sumRow.getCell("jl").setValue(map.get("jl"));
						sumRow.getCell("yj").setValue(map.get("yj"));
					}
				}
				if(personMap.containsKey(sellPorject.getId().toString()+person.getId().toString())){
					IRow totalRow=(IRow) personMap.get(sellPorject.getId().toString()+person.getId().toString());
					totalRow.getCell("tc").setValue(toBigDecimal(totalRow.getCell("tc").getValue()).add(toBigDecimal(col.get(i).getTc())));
					totalRow.getCell("xz").setValue(toBigDecimal(totalRow.getCell("xz").getValue()).add(toBigDecimal(col.get(i).getXz())));
					totalRow.getCell("yl").setValue(toBigDecimal(totalRow.getCell("yl").getValue()).add(toBigDecimal(col.get(i).getYl())));
					totalRow.getCell("jl").setValue(toBigDecimal(totalRow.getCell("jl").getValue()).add(toBigDecimal(col.get(i).getJl())));
					totalRow.getCell("yj").setValue(toBigDecimal(totalRow.getCell("yj").getValue()).add(toBigDecimal(col.get(i).getYj())));
				}else{
					IRow totalRow=this.tblTotal.addRow();
					totalRow.setUserObject(person);
					totalRow.getCell("person").setValue(person.getName());
					totalRow.getCell("orgUnit").setValue(orgUnit.getName());
					totalRow.getCell("sellProject").setValue(sellPorject.getName());
					totalRow.getCell("xz").setValue(toBigDecimal(col.get(i).getXz()));
					totalRow.getCell("yl").setValue(toBigDecimal(col.get(i).getYl()));
					totalRow.getCell("tc").setValue(toBigDecimal(col.get(i).getTc()));
					totalRow.getCell("jl").setValue(toBigDecimal(col.get(i).getJl()));
					totalRow.getCell("yj").setValue(toBigDecimal(col.get(i).getYj()));
					
					personMap.put(sellPorject.getId().toString()+person.getId().toString(), totalRow);
				}
				if(totalMap.containsKey(person.getId().toString())){
					Map map=(Map) totalMap.get(person.getId().toString());
					map.put("tc", FDCHelper.add(map.get("tc"), col.get(i).getTc()));
					map.put("xz", FDCHelper.add(map.get("xz"), col.get(i).getXz()));
					map.put("yl", FDCHelper.add(map.get("yl"), col.get(i).getYl()));
					map.put("jl", FDCHelper.add(map.get("jl"), col.get(i).getJl()));
					map.put("yj", FDCHelper.add(map.get("yj"), col.get(i).getYj()));
				}else{
					Map map=new HashMap();
					map.put("tc", col.get(i).getTc());
					map.put("xz", col.get(i).getXz());
					map.put("yl", col.get(i).getYl());
					map.put("jl", col.get(i).getJl());
					map.put("yj", col.get(i).getYj());
					totalMap.put(person.getId().toString(), map);
				}
			}
			if(i==col.size()-1&&this.tblTotal.getRowCount()>0){
				PersonInfo upPerson=(PersonInfo)this.tblTotal.getRow(this.tblTotal.getRowCount()-1).getUserObject();
				IRow sumRow=this.tblTotal.addRow();
				sumRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
				Map map=(Map) totalMap.get(upPerson.getId().toString());
				sumRow.getCell("person").setValue("小计");
				sumRow.getCell("tc").setValue(map.get("tc"));
				sumRow.getCell("xz").setValue(map.get("xz"));
				sumRow.getCell("yl").setValue(map.get("yl"));
				sumRow.getCell("jl").setValue(map.get("jl"));
				sumRow.getCell("yj").setValue(map.get("yj"));
			}
		}
	}
	private void loadDataOnTable(CommissionTenancyEntryCollection col,boolean isUpdate) throws EASBizException, BOSException {
		CRMHelper.sortCollection(col, new String[]{"type","tenancyBill.tenRoomsDes"}, true);
		KDTable table=null;
		int size = col.size();
		CommissionTenancyEntryInfo entry = null;
		IRow r = null;
		for(int i=0;i<size;i++){
			entry = col.get(i);
			if(entry.getType() == 1){
				table = this.tblFF;
    		}else if(entry.getType() ==2){
    			table = this.tblKD;
    		}else if(entry.getType() ==3){
    			table = this.tblRev;
    		}else if(entry.getType() ==4){
    			table = this.tblQuit;
    		}else if(entry.getType() ==5){
    			table = this.tblRevKD;
    		}
			r = table.addRow();
			bindDataToRow(r,entry,isUpdate);
		}
	}
	private void loadDataOnTable(CommissionSettlementBillEntryCollection col,boolean isUpdate,Map area,Map baseAmount,Map revBaseAmount) throws EASBizException, BOSException {
		CRMHelper.sortCollection(col, new String[]{"orgUnit.longNumber","sellProject.number"}, true);
		int size = col.size();
		CommissionSettlementBillEntryInfo entry = null;
		IRow r = null;
		for(int i=0;i<size;i++){
			r = this.tblMgrBonus.addRow();
			entry = col.get(i);
			bindDataToRow(r,entry,isUpdate,area,baseAmount,revBaseAmount);
		}
	}
	private void setZCBaseAmount(String sellProjectId){
		Map baseAmountMap=(Map)this.sumBaseAmount();
		for(int i=0;i<this.tblMgrBonus.getRowCount();i++){
			String comSellProjectId=((CommissionSettlementBillEntryInfo)this.tblMgrBonus.getRow(i).getUserObject()).getSellProject().getId().toString();
			if(sellProjectId.equals(comSellProjectId)){
				this.tblMgrBonus.getRow(i).getCell("zcBaseAmount").setValue(baseAmountMap.get(comSellProjectId));
			}
		}
	}
	protected void tblKD_editStopped(KDTEditEvent e) throws Exception {
		IRow r = this.tblKD.getRow(e.getRowIndex());
		int colIndex = e.getColIndex();
		String sellProjectId=((CommissionTenancyEntryInfo)r.getUserObject()).getTenancyBill().getSellProject().getId().toString();
		if(colIndex == this.tblKD.getColumnIndex("revRent")){
			BigDecimal revRent=toBigDecimal(r.getCell("revRent").getValue());
			r.getCell("baseAmount").setValue(revRent.divide(new BigDecimal(6), 4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1.5)));
			setZCBaseAmount(sellProjectId);
		}
		this.fitColumnWidth(this.tblKD);
	}
	protected void tblMgrBonus_editStopped(KDTEditEvent e) throws Exception {
		 IRow r = this.tblMgrBonus.getRow(e.getRowIndex());
		 int colIndex = e.getColIndex();
		 if(colIndex == this.tblMgrBonus.getColumnIndex("monthArea")){
			 BigDecimal monthArea=toBigDecimal(r.getCell("monthArea").getValue());
			 BigDecimal contractArea=toBigDecimal(r.getCell("contractArea").getValue());
			 r.getCell("rate").setValue(divide(contractArea,monthArea, 4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
		 }
		 if(colIndex == this.tblMgrBonus.getColumnIndex("zcRate")||colIndex == this.tblMgrBonus.getColumnIndex("zjRate")
				 ||colIndex == this.tblMgrBonus.getColumnIndex("zcCommissionRate")||colIndex == this.tblMgrBonus.getColumnIndex("zjCommissionRate")
				 ||colIndex == this.tblMgrBonus.getColumnIndex("quitAmount")||colIndex == this.tblMgrBonus.getColumnIndex("xz")
				 ||colIndex == this.tblMgrBonus.getColumnIndex("ylRate")||colIndex == this.tblMgrBonus.getColumnIndex("jl")){
			 calEditMgr(r);
		 }
		 this.fitColumnWidth(this.tblMgrBonus);
	}
	private void calEditMgr(IRow r){
		 BigDecimal zcBaseAmount=toBigDecimal(r.getCell("zcBaseAmount").getValue());
		 BigDecimal zcRate=toBigDecimal(r.getCell("zcRate").getValue());
		 BigDecimal zcCommissionRate=toBigDecimal(r.getCell("zcCommissionRate").getValue());
		 BigDecimal zcAmount=divide(zcBaseAmount.multiply(zcRate),new BigDecimal(100), 4,BigDecimal.ROUND_HALF_UP).multiply(zcCommissionRate);
		 r.getCell("zcAmount").setValue(zcAmount);
		 
		 BigDecimal zjBaseAmount=toBigDecimal(r.getCell("zjBaseAmount").getValue());
		 BigDecimal zjRate=toBigDecimal(r.getCell("zjRate").getValue());
		 BigDecimal zjCommissionRate=toBigDecimal(r.getCell("zjCommissionRate").getValue());
		 BigDecimal zjAmount=divide(zjBaseAmount.multiply(zjRate),new BigDecimal(100), 4,BigDecimal.ROUND_HALF_UP).multiply(zjCommissionRate);
		 r.getCell("zjAmount").setValue(zjAmount);
		 
		 BigDecimal quitAmount=toBigDecimal(r.getCell("quitAmount").getValue());
		 BigDecimal tc=zcAmount.add(zjAmount).subtract(quitAmount);
		 r.getCell("tc").setValue(tc);
		 
		 BigDecimal xz=toBigDecimal(r.getCell("xz").getValue());
		 BigDecimal ylRate=toBigDecimal(r.getCell("ylRate").getValue());
		 BigDecimal yl=divide((tc.add(xz)).multiply(ylRate),new BigDecimal(100), 4,BigDecimal.ROUND_HALF_UP);
		 r.getCell("yl").setValue(yl);
		 
		 BigDecimal jl=toBigDecimal(r.getCell("jl").getValue());
		 r.getCell("yj").setValue(tc.add(xz).subtract(yl).add(jl));
	}
	public static BigDecimal divide(Object dec1, Object dec2)
    {
/*  64*/        return divide(dec1, dec2, 4, 4);
    }
    public static BigDecimal divide(Object dec1, Object dec2, int scale, int roundingMode)
    {/*  67*/        if(dec1 == null && dec2 == null)
/*  68*/            return FDCHelper.ZERO;

/*  70*/        if(toBigDecimal(dec2).signum() == 0)
/*  71*/            return FDCHelper.ZERO;

/*  73*/        else/*  73*/            return toBigDecimal(dec1).divide(toBigDecimal(dec2), scale, roundingMode);
    }
    public static BigDecimal toBigDecimal(Object obj)
    {
/*  13*/        if(obj == null)
/*  14*/            return FDCHelper.ZERO;

/*  16*/        if(obj instanceof BigDecimal)
/*  17*/            return (BigDecimal)obj;
/*  18*/        if(obj instanceof Integer)
/*  19*/            return new BigDecimal(((Integer)obj).toString());
/*  20*/        if(obj instanceof Long)
/*  21*/            return new BigDecimal(((Long)obj).toString());
/*  22*/        if(obj instanceof Double)
/*  23*/            return new BigDecimal(((Double)obj).doubleValue());
/*  24*/        if(obj.toString() == null)
/*  25*/            return FDCHelper.ZERO;

/*  27*/        String str = obj.toString().trim();

/*  29*/        if(str.toLowerCase().indexOf("e") > -1)

/*  31*/            try
            {
/* <-MISALIGNED-> */ /*  31*/                return new BigDecimal(str);
            }
/* <-MISALIGNED-> */ /*  32*/            catch(NumberFormatException e)
            {
/* <-MISALIGNED-> */ /*  34*/                return FDCHelper.ZERO;
            }
/* <-MISALIGNED-> */ /*  37*/        if(str.matches("^[+-]?\\d+[\\.\\d]?\\d*+$"))
/* <-MISALIGNED-> */ /*  38*/            return new BigDecimal(str);

/*  42*/        else/*  42*/            return FDCHelper.ZERO;
    }
}