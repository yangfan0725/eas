/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.client.ClientHelper;
import com.kingdee.eas.fdc.sellhouse.AgencyCommissionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgencyCommissionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillFactory;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingCommissionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingCommissionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.MonthCommissionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.report.PaymentReportUI;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.AdvMsgBox;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class CommissionSettlementEditUI extends AbstractCommissionSettlementEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CommissionSettlementEditUI.class);
    
    /**
     * output class constructor
     */
    public CommissionSettlementEditUI() throws Exception
    {
        super();
    }
    
    @Override
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
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
    	
    	
    	this.tblTotal.checkParsed();
    	 ObjectValueRender r=new ObjectValueRender(){
     		public String getText(Object obj) {
     			if(obj!=null){
     				return obj.toString()+"%";
     			}
     			return super.getText(obj);
     		}
     	};
     	this.tblTotal.getColumn("percent").setRenderer(r);
         
    	this.contOrgUnit.setEnabled(false);
    	this.prmtOrgUnit.setEnabled(false);
    	
    	//增加按钮
//    	this.contSalesmanBonus.addButton(this.btnCalcSalesBonus);
//    	this.contSalesmanBonus.addButton(this.btnAddSalesBonus);
//    	this.contSalesmanBonus.addButton(this.btnRemoveSalesBonus);
    	
//    	this.contManager.addButton(this.btnCalcMgrBonus);
    	this.contManager.addButton(this.btnAddMgrBonus);
    	this.contManager.addButton(this.btnRemoveMgrBonus);
    	
    	this.contQdM.addButton(this.btnAddQdM);
    	this.contQdM.addButton(this.btnRemoveQdM);
    	
    	 initTable(tblMgrBonus);
         initTable(tblSalesmanBonus);
         initTable(tblQdM);
         initTable(tblQd);
         initTable(tblRec);
    	
    	KDBizPromptBox personSelector = new KDBizPromptBox();
    	personSelector.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent e) {
				// TODO Auto-generated method stub
				KDBizPromptBox c = (KDBizPromptBox) e.getSource();
				tblMgrBonus.getCell(tblMgrBonus.getSelectManager().getActiveRowIndex(), "person").setUserObject(e.getNewValue());
				
			}});
    	FDCClientUtils.setPersonF7(personSelector, null, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
    	KDTDefaultCellEditor personEditor = new KDTDefaultCellEditor(personSelector);
    	this.tblMgrBonus.getColumn("person").setEditor(personEditor);
    	
    	KDBizPromptBox salesSelector = new KDBizPromptBox();
    	salesSelector.addDataChangeListener(new DataChangeListener(){
    		
    		public void dataChanged(DataChangeEvent e) {
    			// TODO Auto-generated method stub
    			KDBizPromptBox c = (KDBizPromptBox) e.getSource();
    			tblSalesmanBonus.getCell(tblSalesmanBonus.getSelectManager().getActiveRowIndex(), "person").setUserObject(e.getNewValue());
    			
    		}});
    	FDCClientUtils.setPersonF7(salesSelector, null, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
    	KDTDefaultCellEditor salesEditor = new KDTDefaultCellEditor(salesSelector);
    	this.tblSalesmanBonus.getColumn("person").setEditor(salesEditor);
    	
    	KDBizPromptBox qdmSelector = new KDBizPromptBox();
    	qdmSelector.addDataChangeListener(new DataChangeListener(){
    		
    		public void dataChanged(DataChangeEvent e) {
    			// TODO Auto-generated method stub
    			KDBizPromptBox c = (KDBizPromptBox) e.getSource();
    			tblQdM.getCell(tblQdM.getSelectManager().getActiveRowIndex(), "person").setUserObject(e.getNewValue());
    			
    		}});
    	FDCClientUtils.setPersonF7(qdmSelector, null, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
    	KDTDefaultCellEditor qdmEditor = new KDTDefaultCellEditor(qdmSelector);
    	this.tblQdM.getColumn("person").setEditor(qdmEditor);
    	
        this.tblBounsTotal.setEnabled(false);
        this.prmtSellProject.setEnabled(false);
        
        isLoad=false;
    }
    public void initTable(KDTable table){
    	table.checkParsed();
    	if(!tblQdM.equals(table)){
    		table.getColumn("contractAmt").getStyleAttributes().setFontColor(Color.BLUE);
            table.getColumn("backAmt").getStyleAttributes().setFontColor(Color.BLUE);
            table.getColumn("pur").getStyleAttributes().setFontColor(Color.BLUE);
            table.getColumn("contract").getStyleAttributes().setFontColor(Color.BLUE);
            table.getColumn("calcBonus").getStyleAttributes().setFontColor(Color.BLUE);
            table.getColumn("purAmt").getStyleAttributes().setFontColor(Color.BLUE);
    	}
        table.getColumn("person").setRequired(true);
        table.getColumn("contractAmtTarget").setRequired(true);
        table.getColumn("backAmtTarget").setRequired(true);
        table.getColumn("mpur").setRequired(true);
        table.getColumn("mcontract").setRequired(true);
        table.getColumn("calcBonusRate").setRequired(true);
        table.getColumn("calcTBonusRate").setRequired(true);
        table.getColumn("xz").setRequired(true);
        table.getColumn("keepRate").setRequired(true);
        table.getColumn("adjustAmt").setRequired(true);
        table.getColumn("zbRate").setRequired(true);
        table.getColumn("position").setRequired(true);
        table.getColumn("purTarget").setRequired(true);
        
        ObjectValueRender r=new ObjectValueRender(){
    		public String getText(Object obj) {
    			if(obj!=null){
    				return obj.toString()+"%";
    			}
    			return super.getText(obj);
    		}
    	};
        table.getColumn("contractCompleteRate").setRenderer(r);
        table.getColumn("backCompleteRate").setRenderer(r);
        table.getColumn("contractCompleteRate").setRenderer(r);
        table.getColumn("purRate").setRenderer(r);
        table.getColumn("contractRate").setRenderer(r);
        table.getColumn("calcBonusRate").setRenderer(r);
        table.getColumn("keepRate").setRenderer(r);
        table.getColumn("zbRate").setRenderer(r);
        table.getColumn("purComplateRate").setRenderer(r);
        if(tblQdM.equals(table)){
        	table.getColumn("contractAmt").setRequired(true);
            table.getColumn("backAmt").setRequired(true);
            table.getColumn("productType").setRequired(true);
            table.getColumn("pur").setRequired(true);
            table.getColumn("contract").setRequired(true);
            table.getColumn("calcBonus").setRequired(true);
            table.getColumn("calcTBonus").setRequired(true);
            table.getColumn("purAmt").setRequired(true);
        }
    }
    
    @Override
    public SelectorItemCollection getSelectors() {
    	// TODO Auto-generated method stub
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add("month");
    	sic.add("year");
    	sic.add("bonus");
    	sic.add("adjustAmt");
    	sic.add("contractAmt");
    	sic.add("backAmt");
    	sic.add("salary");
    	sic.add("percent");
    	sic.add("description");
    	sic.add("baseSalary");
    	sic.add("state");
    	
    	sic.add("empBonusEntry.*");
    	sic.add("empBonusEntry.contractAmt");
    	sic.add("empBonusEntry.backAmt");
    	sic.add("empBonusEntry.contractAmtTarget");
    	sic.add("empBonusEntry.backAmtTarget");
    	sic.add("empBonusEntry.contractCompleteRate ");
    	sic.add("empBonusEntry.backCompleteRate ");
    	sic.add("empBonusEntry.productType ");
    	sic.add("empBonusEntry.position");
    	sic.add("empBonusEntry.calcBonus");
    	sic.add("empBonusEntry.calcBonusRate");
    	sic.add("empBonusEntry.bonus");
    	sic.add("empBonusEntry.keepAmt");
    	sic.add("empBonusEntry.adjustAmt ");
    	sic.add("empBonusEntry.keepAmt");
    	sic.add("empBonusEntry.bonusType");
    	sic.add("empBonusEntry.description");
    	sic.add("empBonusEntry.realBonusAmt");
    	sic.add("empBonusEntry.person.*");
    	
    	
    	sic.add("agencyEntry.*");
    	return sic;
    }
    
    @Override
    public void loadFields() {
    	isLoad=true;
    	// TODO Auto-generated method stub
    	super.loadFields();
    	//加载empBonus
    	
    	this.kDSpinnerMonth.setValue(editData.getMonth());
    	this.kDSpinnerYear.setValue(editData.getYear());
    	
    	year_old=editData.getYear();
    	month_old=editData.getMonth();
    	
    	MarketingCommissionEntryCollection empBonus = editData.getEmpBonusEntry();
    	MarketingCommissionEntryInfo entry = null;
    	int size = empBonus.size();
    	KDTable t = null;
    	IRow r = null;
    	this.tblMgrBonus.removeRows();
    	this.tblSalesmanBonus.removeRows();
    	this.tblQdM.removeRows();
    	this.tblQd.removeRows();
    	this.tblRec.removeRows();
    	for(int i=0;i<size;i++ ){
    		entry = empBonus.get(i);
    		if(entry.getBonusType() == 1){
    			t = tblMgrBonus;
    		}else if(entry.getBonusType() ==2){
    			t = tblSalesmanBonus;
    		}else if(entry.getBonusType() ==3){
    			t = tblQdM;
    		}else if(entry.getBonusType() ==4){
    			t = tblQd;
    		}else if(entry.getBonusType() ==5){
    			t = tblRec;
    		}
    		t.checkParsed();
    		r = t.addRow();
    		r.setUserObject(entry);
    		bindDataToRow(r, entry);
    		
    	}
    	this.tblMgrBonus.getGroupManager().group();
    	this.tblSalesmanBonus.getGroupManager().group();
    	this.tblQdM.getGroupManager().group();
    	this.tblQd.getGroupManager().group();
    	this.tblRec.getGroupManager().group();
    	
    	this.tblBounsTotal.checkParsed();
    	this.tblTotal.checkParsed();
        refreshTotalData();
        refreshBonusTotal();
        
        
        ClientHelper.getFootRow(tblMgrBonus, new String[]{"calcTBonus","xz","mpur","mcontract","pur","contract","calcBonus","bonus","keepAmt","adjustAmt","realBonusAmt"});
        ClientHelper.getFootRow(tblSalesmanBonus, new String[]{"calcTBonus","xz","mpur","mcontract","pur","contract","calcBonus","bonus","keepAmt","adjustAmt","realBonusAmt"});
        ClientHelper.getFootRow(tblQdM, new String[]{"calcTBonus","xz","mpur","mcontract","pur","contract","calcBonus","bonus","keepAmt","adjustAmt","realBonusAmt"});
        ClientHelper.getFootRow(tblQd, new String[]{"calcTBonus","xz","mpur","mcontract","pur","contract","calcBonus","bonus","keepAmt","adjustAmt","realBonusAmt"});
        ClientHelper.getFootRow(tblRec, new String[]{"calcTBonus","xz","mpur","mcontract","pur","contract","calcBonus","bonus","keepAmt","adjustAmt","realBonusAmt"});
        ClientHelper.getFootRow(tblBounsTotal, new String[]{"bonus","xz","keepAmt","adjustAmt","realAmt"});
        
        
    
        try {
			loadQuit();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		isLoad=false;
    }
    private Boolean isLoad=false;
    private int year_old = 0;
	private int month_old =0;

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
        //保存MarketingCommissionEntry
        editData.getEmpBonusEntry().clear();
        storeTable(tblMgrBonus,1);
        storeTable(tblSalesmanBonus,2);
        storeTable(tblQdM,3);
        storeTable(tblQd,4);
        storeTable(tblRec,5);
        
        //保存total信息
        IRow r  = tblTotal.getRow(0);
        if(r!= null){
        	editData.setBackAmt(r.getCell("backAmt").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmt").getValue()));
        	editData.setContractAmt(r.getCell("contractAmt").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmt").getValue()));
        	editData.setSalary(r.getCell("salary").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("salary").getValue()));
        	editData.setPercent(r.getCell("percent").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("percent").getValue()));
        	editData.setBonus(r.getCell("bonus").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue()));
        	editData.setAdjustAmt(r.getCell("adjustAmt").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue()));
//        	editData.setDescription(r.getCell("description").getValue()==null?"":""+r.getCell("description").getValue());
        	editData.setBaseSalary(r.getCell("baseSalary").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("baseSalary").getValue()));
        	
        }
        
        editData.setYear(Integer.valueOf( this.kDSpinnerYear.getModel().getValue()+""));
        editData.setMonth(Integer.valueOf( this.kDSpinnerMonth.getModel().getValue()+""));
        
        
        OrgUnitInfo org = editData.getOrgUnit();
		editData.setName(org.getName()+"_"+editData.getSellProject().getName()+"_"+editData.getYear()+"年"+editData.getMonth()+"月_佣金审批单");
		this.txtName.setName(editData.getName());
    }
    public void storeTable(KDTable table,int type){
    	MarketingCommissionEntryCollection empBonus = new MarketingCommissionEntryCollection();
        MarketingCommissionEntryInfo mcEntry = null;
        int size = table.getRowCount();
        IRow r = null;
       
        for(int i =0;i<size;i++){
        	r = table.getRow(i);
        	mcEntry = (MarketingCommissionEntryInfo) r.getUserObject();
        	mcEntry.setParent(editData);
        	mcEntry.setBonusType(type);
        	if(table.equals(tblQdM)){
        		mcEntry.setPerson((PersonInfo) r.getCell("person").getValue());
        	}
        	mcEntry.setPosition(r.getCell("position").getValue()==null?"":""+r.getCell("position").getValue());
        	
        	mcEntry.setBackAmt(r.getCell("backAmt").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmt").getValue()));
        	mcEntry.setContractAmt(r.getCell("contractAmt").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmt").getValue()));
        	
        	mcEntry.setBackAmtTarget(r.getCell("backAmtTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmtTarget").getValue()));
        	mcEntry.setContractAmtTarget(r.getCell("contractAmtTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmtTarget").getValue()));
        	
        	mcEntry.setContractCompleteRate(r.getCell("contractCompleteRate").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractCompleteRate").getValue()));
        	mcEntry.setBackCompleteRate(r.getCell("backCompleteRate").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backCompleteRate").getValue()));
        	
        	mcEntry.setProductType(r.getCell("productType").getValue()==null?"":""+r.getCell("productType").getValue());
        	
        	mcEntry.setCalcBonusAmt(r.getCell("calcBonus").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcBonus").getValue()));
        	mcEntry.setCalcBonusRate(r.getCell("calcBonusRate").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcBonusRate").getValue()));
        	
        	mcEntry.setBonus(r.getCell("bonus").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue()));
        	mcEntry.setKeepAmt(r.getCell("keepAmt").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepAmt").getValue()));
        	mcEntry.setAdjustAmt(r.getCell("adjustAmt").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue()));
        	mcEntry.setRealBonusAmt(r.getCell("realBonusAmt").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("realBonusAmt").getValue()));
        	mcEntry.setDescription(r.getCell("description").getValue()==null?"":""+r.getCell("description").getValue());
        	
        	mcEntry.setMpur(r.getCell("mpur").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("mpur").getValue()));
        	mcEntry.setMcontract(r.getCell("mcontract").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("mcontract").getValue()));
        	mcEntry.setPur(r.getCell("pur").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("pur").getValue()));
        	mcEntry.setContract(r.getCell("contract").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contract").getValue()));
        	mcEntry.setPurRate(r.getCell("purRate").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purRate").getValue()));
        	mcEntry.setContractRate(r.getCell("contractRate").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractRate").getValue()));
        	
        	mcEntry.setCalcTBonus(r.getCell("calcTBonus").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcTBonus").getValue()));
        	mcEntry.setCalcTBonusRate(r.getCell("calcTBonusRate").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcTBonusRate").getValue()));
        	mcEntry.setXz(r.getCell("xz").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue()));
        	mcEntry.setKeepRate(r.getCell("keepRate").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepRate").getValue()));
        	
        	mcEntry.setZbRate(r.getCell("zbRate").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("zbRate").getValue()));
        	
        	mcEntry.setPurAmt(r.getCell("purAmt").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purAmt").getValue()));
        	mcEntry.setPurTarget(r.getCell("purTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purTarget").getValue()));
        	mcEntry.setPurComplateRate(r.getCell("purComplateRate").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purComplateRate").getValue()));
        	
        	
        	empBonus.add(mcEntry);
        }
        editData.getEmpBonusEntry().addCollection(empBonus);
    }
	@Override
	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return CommissionSettlementBillFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return txtNumber;
	}
	
	@Override
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String strMonth =sdf.format(new Date());
		
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",editData.getOrgUnit().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("month",strMonth));
    	
    	boolean isExist = CommissionSettlementBillFactory.getRemoteInstance().exists(filter);
    	
    	if(isExist){
    		FDCMsgBox.showError("当月已经 生成过佣金结算单，不能重复生成。");
    		abort();
    	}
		
		super.actionAddNew_actionPerformed(e);
	}
	
	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		CommissionSettlementBillInfo info = new CommissionSettlementBillInfo();
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
	@Override
	protected void verifyInput(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("year",editData.getYear()));
		filter.getFilterItems().add(new FilterItemInfo("month",editData.getMonth()));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",editData.getSellProject().getId().toString()));
		if(editData.getId() != null){
			filter.getFilterItems().add(new FilterItemInfo("id",editData.getId().toString(),CompareType.NOTEQUALS));
		}
		
		boolean isExist = CommissionSettlementBillFactory.getRemoteInstance().exists(filter);
		if(isExist){
			FDCMsgBox.showError("当月已经 生成过佣金结算单，不能重复生成。");
    		abort();
		}
		super.verifyInput(e);
		
	}
	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();
		for(int i=0;i<tblMgrBonus.getRowCount();i++){
			for(int j=0;j<tblMgrBonus.getColumnCount();j++){
				if(tblMgrBonus.getColumn(j).isRequired()){
					if(tblMgrBonus.getRow(i).getCell(j).getValue()==null){
						FDCMsgBox.showWarning("案场管理佣金计算数据不能为空！");
						tblMgrBonus.getEditManager().editCellAt(i, j);
			    		abort();
					}
				}
			}
		}
		for(int i=0;i<this.tblSalesmanBonus.getRowCount();i++){
			for(int j=0;j<tblSalesmanBonus.getColumnCount();j++){
				if(tblSalesmanBonus.getColumn(j).isRequired()){
					if(tblSalesmanBonus.getRow(i).getCell(j).getValue()==null){
						FDCMsgBox.showWarning("置业顾问佣金计算数据不能为空！");
						tblSalesmanBonus.getEditManager().editCellAt(i, j);
			    		abort();
					}
				}
			}
		}
		for(int i=0;i<this.tblQdM.getRowCount();i++){
			for(int j=0;j<tblQdM.getColumnCount();j++){
				if(tblQdM.getColumn(j).isRequired()){
					if(tblQdM.getRow(i).getCell(j).getValue()==null){
						FDCMsgBox.showWarning("渠道管理佣金计算数据不能为空！");
						tblQdM.getEditManager().editCellAt(i, j);
			    		abort();
					}
				}
			}
		}
		for(int i=0;i<this.tblQd.getRowCount();i++){
			for(int j=0;j<tblQd.getColumnCount();j++){
				if(tblQd.getColumn(j).isRequired()){
					if(tblQd.getRow(i).getCell(j).getValue()==null){
						FDCMsgBox.showWarning("渠道人员佣金计算数据不能为空！");
						tblQd.getEditManager().editCellAt(i, j);
			    		abort();
					}
				}
			}
		}
		for(int i=0;i<this.tblRec.getRowCount();i++){
			for(int j=0;j<tblRec.getColumnCount();j++){
				if(tblRec.getColumn(j).isRequired()){
					if(tblRec.getRow(i).getCell(j).getValue()==null){
						FDCMsgBox.showWarning("推荐人佣金计算数据不能为空！");
						tblRec.getEditManager().editCellAt(i, j);
			    		abort();
					}
				}
			}
		}
	}

	@Override
	protected IObjectValue createNewDetailData(KDTable table) {
		// TODO Auto-generated method stub
		return new MarketingCommissionEntryInfo();
	}

	public void actionAddMgrBonus_actionPerformed(ActionEvent e)
			throws Exception {
		this.tblMgrBonus.addRow();
	}
	public MonthCommissionEntryInfo getMonthCommissionEntry(String person) throws SQLException, BOSException{
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select fpurTarget,fsignTarget,fbackTarget from T_SHE_MonthCommissionEntry mce left join T_SHE_MonthCommission mc on mc.fid=mce.fparentid");
		sql.append(" left join T_BD_Person p on p.fid=mce.fpersonid");
		sql.append(" where mc.fstate='4AUDITTED' and mc.fsellprojectid='"+this.editData.getSellProject().getId().toString()+"' and mc.fyear="+this.kDSpinnerYear.getValue()+" and mc.fmonth="+this.kDSpinnerMonth.getValue());
		sql.append(" and p.fname_l2='"+person+"'");
		MonthCommissionEntryInfo entry=new MonthCommissionEntryInfo();
		
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		final IRowSet rowSet = _builder.executeQuery();
		Set id=new HashSet();
		while(rowSet.next()) {
			entry.setPurTarget(rowSet.getBigDecimal("fpurTarget"));
			entry.setSignTarget(rowSet.getBigDecimal("fsignTarget"));
			entry.setBackTarget(rowSet.getBigDecimal("fbackTarget"));
		}
		return entry;
	}
	@Override
	public void actionCalcMgrBonus_actionPerformed(ActionEvent e)
			throws Exception {
		boolean isAlreadyCalc = false;
		if(this.tblMgrBonus.getRowCount() == 0){
			FDCMsgBox.showError("没有录入任何案场管理人员信息，请先录入案场管理人员信息再进行奖金计算.");
			SysUtil.abort();
		}
		int result = FDCMsgBox.showConfirm2("是否需要进行计算?");
		if(AdvMsgBox.NO_OPTION == result){
			abort();
		}
		//保存当前界面参数
		int size = this.tblMgrBonus.getRowCount();
		Map<String,Map> userInputData = new HashMap<String, Map>();
		IRow r = null;
		PersonInfo person = null;
		for(int i=0;i<size;i++){
	    	r = this.tblMgrBonus.getRow(i);
	    	person  = (PersonInfo) r.getCell("person").getUserObject();
	    	if(person == null){
	    		continue;
	    	}
	    	Map<String,Object> goalAmtMap = new HashMap<String,Object>();
	    	goalAmtMap.put("contractAmtTarget", r.getCell("contractAmtTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmtTarget").getValue()));
	    	goalAmtMap.put("backAmtTarget", r.getCell("backAmtTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmtTarget").getValue()));
	    	goalAmtMap.put("purTarget", r.getCell("purTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purTarget").getValue()));
	    	goalAmtMap.put("person", person);
	    	goalAmtMap.put("position",r.getCell("position").getValue());
	    	
	    	userInputData.put(person.getId().toString(),goalAmtMap);
	    }
		
		//查询出当前组织下的已签约的合同金额和回款金额
		Map paramMap = new  HashMap();
		paramMap.put("sellProject", editData.getSellProject().getId().toString());
		paramMap.put("year", this.kDSpinnerYear.getValue());
		paramMap.put("month",this.kDSpinnerMonth.getValue());
		paramMap = CommissionSettlementBillFactory.getRemoteInstance().calcMgrBonus(paramMap);
		
		//生成MarketingCommissionEntryInfo并把结果填充到表格上。
	     Set<String> userInput = userInputData.keySet();
	     MarketingCommissionEntryCollection commissionEntry = new MarketingCommissionEntryCollection();
	     MarketingCommissionEntryInfo entry = null;
	     String pKey = null;
	     Map dataMap = (Map)paramMap.get("data");
	     Map amtMap  = null;
	     Map userInputMap = null;
	     String positionName = null;
	     Set<String> productSet = ((Map)paramMap.get("data")).keySet();
	     BigDecimal backAmt=null;
	     BigDecimal contractAmt=null;
	     BigDecimal purAmt=null;
	     for(Iterator<String> it = userInput.iterator();it.hasNext();){
	    	 userInputMap = userInputData.get(it.next());
	    	 person = (PersonInfo) userInputMap.get("person");
	    	 positionName = ""+userInputMap.get("position");
	    	 //后台返回数据以productType为维度
	    	 for(Iterator<String> pIt = productSet.iterator();pIt.hasNext();){
	    		 pKey = pIt.next();
	    		 amtMap = (Map) dataMap.get(pKey);
	    		 entry = new MarketingCommissionEntryInfo();
	    		 entry.setPerson(person);
	    		 entry.setPosition(positionName);
	    		 MonthCommissionEntryInfo me=getMonthCommissionEntry(person.getName());
	    		 entry.setContractAmtTarget(me.getSignTarget());
	    		 entry.setBackAmtTarget(me.getBackTarget());
	    		 entry.setPurTarget(me.getPurTarget());
	    		 
	    		 if(amtMap.get("purAmt")!=null){
	    			 purAmt=FDCHelper.toBigDecimal(amtMap.get("purAmt"));
	    		 }
	    		 entry.setPurAmt(amtMap.get("purAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("purAmt")));
	    		 if(entry.getPurTarget() != null){
	    			 entry.setPurComplateRate(entry.getPurTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getPurAmt()!=null&& entry.getPurAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getPurAmt(), entry.getPurTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
	    		 }
	    		 if(amtMap.get("contractAmt")!=null){
	    			 contractAmt=FDCHelper.toBigDecimal(amtMap.get("contractAmt"));
	    			 editData.setContractAmt(contractAmt);
	    		 }
	    		 entry.setContractAmt(amtMap.get("contractAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("contractAmt")));
	    		 
	    		 if(amtMap.get("backAmt")!=null){
	    			 backAmt=FDCHelper.toBigDecimal(amtMap.get("backAmt"));
		    		 editData.setBackAmt(backAmt);
	    		 }
	    		 entry.setBackAmt(amtMap.get("backAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("backAmt")));
	    		 
	    		 entry.setBonusType(1);
	    		 entry.setCalcBonusAmt(amtMap.get("calcBonus")==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("calcBonus")));
	    		 entry.setProductType(amtMap.get("productTypeName")==null?null:""+amtMap.get("productTypeName"));
	    		 //计算各种完成比率
	    		 if(entry.getContractAmtTarget() != null){
	    			 entry.setContractCompleteRate(entry.getContractAmtTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getContractAmt()!=null&& entry.getContractAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getContractAmt(), entry.getContractAmtTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
	    		 }
	    		 if(entry.getBackAmtTarget() != null){
	    			 entry.setBackCompleteRate(entry.getBackAmtTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getBackAmt()!=null&& entry.getBackAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getBackAmt(), entry.getBackAmtTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
	    		 }
	    		 
	    		 entry.setPur(amtMap.get("pur") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("pur")));
	    		 entry.setContract(amtMap.get("contract") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("contract")));
	    		 if(entry.getMpur() != null){
	    			 entry.setPurRate(entry.getMpur().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getPur()!=null&& entry.getPur().compareTo(FDCHelper.ZERO)!=0?divide(entry.getPur(), entry.getMpur()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
	    		 }
	    		 if(entry.getMcontract() != null){
	    			 entry.setContractRate(entry.getMcontract().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getContract()!=null&& entry.getContract().compareTo(FDCHelper.ZERO)!=0?divide(entry.getContract(), entry.getMcontract()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
	    		 }
	    		 
	    		 entry.setCalcTBonus(amtMap.get("calcTBonus")==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("calcTBonus")));
	    		 
	    		 commissionEntry.add(entry);
	    	 }
	     }
	     for(int i=0;i<commissionEntry.size();i++){
	    	 entry=commissionEntry.get(i);
	    	 entry.setBackAmt(backAmt==null?FDCHelper.ZERO:backAmt);
	    	 entry.setContractAmt(contractAmt==null?FDCHelper.ZERO:contractAmt);
	    	 entry.setPurAmt(purAmt==null?FDCHelper.ZERO:purAmt);
	    	 if(entry.getPurTarget() != null){
				 entry.setPurComplateRate(entry.getPurTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getPurAmt()!=null&& entry.getPurAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getPurAmt(), entry.getPurTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
			 }
	    	 if(entry.getContractAmtTarget() != null){
    			 entry.setContractCompleteRate(entry.getContractAmtTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getContractAmt()!=null&& entry.getContractAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getContractAmt(), entry.getContractAmtTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
    		 }
    		 if(entry.getBackAmtTarget() != null){
    			 entry.setBackCompleteRate(entry.getBackAmtTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getBackAmt()!=null&& entry.getBackAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getBackAmt(), entry.getBackAmtTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
    		 }
    	 }
	     loadDataOnTable(commissionEntry,tblMgrBonus);
	     
	    size = this.tblSalesmanBonus.getRowCount();
		userInputData = new HashMap<String, Map>();
		r = null;
		person = null;
		for(int i=0;i<size;i++){
	    	r = this.tblSalesmanBonus.getRow(i);
	    	person  = (PersonInfo) r.getCell("person").getUserObject();
	    	if(person == null){
	    		continue;
	    	}
	    	Map<String,Object> goalAmtMap = new HashMap<String,Object>();
	    	goalAmtMap.put("contractAmtTarget", r.getCell("contractAmtTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmtTarget").getValue()));
	    	goalAmtMap.put("backAmtTarget", r.getCell("backAmtTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmtTarget").getValue()));
	    	goalAmtMap.put("purTarget", r.getCell("purTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purTarget").getValue()));
	    	goalAmtMap.put("person", person);
	    	goalAmtMap.put("position",r.getCell("position").getValue());
	    		    	
	    	userInputData.put(person.getId().toString(),goalAmtMap);
	    }
		
		this.tblSalesmanBonus.removeRows();
	     paramMap = new  HashMap();
		 paramMap.put("sellProject", editData.getSellProject().getId().toString());
		 paramMap.put("year", this.kDSpinnerYear.getValue());
		 paramMap.put("month",this.kDSpinnerMonth.getValue());
		 paramMap = CommissionSettlementBillFactory.getRemoteInstance().calcSalesBonus(paramMap);
	    
		
		 commissionEntry = new MarketingCommissionEntryCollection();
	     entry = null;
	     pKey = null;
	     dataMap = (Map)paramMap.get("data");
	     amtMap  = null;
	     userInputMap = null;
	     productSet = ((Map)paramMap.get("data")).keySet();
	     backAmt=null;
	     contractAmt=null;
	    	 //后台返回数据以productType为维度
	     List<Map.Entry<String,Map>> list = new ArrayList<Map.Entry<String,Map>>(((Map)paramMap.get("data")).entrySet());
	     Collections.sort(list,new Comparator<Map.Entry<String,Map>>() {
	            //升序排序
				public int compare(Entry<String, Map> o1,
						Entry<String, Map> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}

	        });
	     Map purMap=new HashMap();
	     Map backMap=new HashMap();
	     Map contractMap=new HashMap();
	     for(Map.Entry<String,Map> mapping:list){
    		 pKey = mapping.getKey();
    		 amtMap = (Map) mapping.getValue();
    		 String pid=pKey.split("%")[0];
    		 Map userDate=(Map)userInputData.get(pid);
    		 entry = new MarketingCommissionEntryInfo();
    		 if(amtMap.get("backAmt") == null)continue;
    		 if(userDate!=null){
    			 person=(PersonInfo) userDate.get("person");
    			 positionName =""+userDate.get("position");
    			 
        		 entry.setPerson(person);
        		 entry.setPosition(positionName);
    		 }else{
    			 person=PersonFactory.getRemoteInstance().getPersonInfo(new ObjectUuidPK(pid));
    			 entry.setPerson(person);
    		 }
    		 
    		 MonthCommissionEntryInfo me=getMonthCommissionEntry(person.getName());
			 
			 entry.setContractAmtTarget(me.getSignTarget());
    		 entry.setBackAmtTarget(me.getBackTarget());
    		 entry.setPurTarget(me.getPurTarget());
    		 
    		 if(amtMap.get("purAmt")!=null){
    			 purMap.put(person.getId().toString(), FDCHelper.toBigDecimal(amtMap.get("purAmt")));
    		 }
    		 if(amtMap.get("contractAmt")!=null){
    			 contractMap.put(person.getId().toString(), FDCHelper.toBigDecimal(amtMap.get("contractAmt")));
    		 }
    		 if(amtMap.get("backAmt")!=null){
    			 backMap.put(person.getId().toString(), FDCHelper.toBigDecimal(amtMap.get("backAmt")));
    		 }
    		 entry.setPurAmt(amtMap.get("purAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("purAmt")));
    		 entry.setContractAmt(amtMap.get("contractAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("contractAmt")));
    		 
    		 entry.setBackAmt(amtMap.get("backAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("backAmt")));
    		 
    		 entry.setBonusType(2);
    		 entry.setCalcBonusAmt(amtMap.get("calcBonus")==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("calcBonus")));
    		 entry.setProductType(amtMap.get("productTypeName")==null?null:""+amtMap.get("productTypeName"));
    		 //计算各种完成比率
    		 if(entry.getPurTarget() != null){
    			 entry.setPurComplateRate(entry.getPurTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getPurAmt()!=null&& entry.getPurAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getPurAmt(), entry.getPurTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
    		 }
    		 if(entry.getContractAmtTarget() != null){
    			 entry.setContractCompleteRate(entry.getContractAmtTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getContractAmt()!=null&& entry.getContractAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getContractAmt(), entry.getContractAmtTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
    		 }
    		 if(entry.getBackAmtTarget() != null){
    			 entry.setBackCompleteRate(entry.getBackAmtTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getBackAmt()!=null&& entry.getBackAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getBackAmt(), entry.getBackAmtTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
    		 }
    		 
    		 entry.setPur(amtMap.get("pur") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("pur")));
    		 entry.setContract(amtMap.get("contract") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("contract")));
    		 if(entry.getMpur() != null){
    			 entry.setPurRate(entry.getMpur().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getPur()!=null&& entry.getPur().compareTo(FDCHelper.ZERO)!=0?divide(entry.getPur(), entry.getMpur()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
    		 }
    		 if(entry.getMcontract() != null){
    			 entry.setContractRate(entry.getMcontract().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getContract()!=null&& entry.getContract().compareTo(FDCHelper.ZERO)!=0?divide(entry.getContract(), entry.getMcontract()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
    		 }
    		 
    		 entry.setCalcTBonus(amtMap.get("calcTBonus")==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("calcTBonus")));
    		 
    		 commissionEntry.add(entry);
    	 }
	     for(int i=0;i<commissionEntry.size();i++){
	    	 entry=commissionEntry.get(i);
	    	 entry.setPurAmt(FDCHelper.toBigDecimal(purMap.get(entry.getPerson().getId().toString())));
	    	 entry.setContractAmt(FDCHelper.toBigDecimal(contractMap.get(entry.getPerson().getId().toString())));
	    	 entry.setBackAmt(FDCHelper.toBigDecimal(backMap.get(entry.getPerson().getId().toString())));
	    	 if(entry.getPurTarget() != null){
				 entry.setPurComplateRate(entry.getPurTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getPurAmt()!=null&& entry.getPurAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getPurAmt(), entry.getPurTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
			 }
    	 }
	    loadDataOnTable(commissionEntry,tblSalesmanBonus);
		
	    size = this.tblQd.getRowCount();
		userInputData = new HashMap<String, Map>();
		r = null;
		String personStr = null;
		for(int i=0;i<size;i++){
	    	r = this.tblQd.getRow(i);
	    	personStr  = (String) r.getCell("person").getUserObject();
	    	if(personStr == null){
	    		continue;
	    	}
	    	Map<String,Object> goalAmtMap = new HashMap<String,Object>();
	    	goalAmtMap.put("contractAmtTarget", r.getCell("contractAmtTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmtTarget").getValue()));
	    	goalAmtMap.put("backAmtTarget", r.getCell("backAmtTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmtTarget").getValue()));
	    	goalAmtMap.put("purTarget", r.getCell("purTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purTarget").getValue()));
	    	goalAmtMap.put("person", personStr);
	    	goalAmtMap.put("position",r.getCell("position").getValue());
	    		    	
	    	userInputData.put(personStr,goalAmtMap);
	    }
	    this.tblQd.removeRows();
	     paramMap = new  HashMap();
		 paramMap.put("sellProject", editData.getSellProject().getId().toString());
		 paramMap.put("year", this.kDSpinnerYear.getValue());
		 paramMap.put("month",this.kDSpinnerMonth.getValue());
		 paramMap = CommissionSettlementBillFactory.getRemoteInstance().calcQd(paramMap);
	    
		
		 commissionEntry = new MarketingCommissionEntryCollection();
	     entry = null;
	     pKey = null;
	     dataMap = (Map)paramMap.get("data");
	     amtMap  = null;
	     userInputMap = null;
	     productSet = ((Map)paramMap.get("data")).keySet();
	     backAmt=null;
	     contractAmt=null;
	    	 //后台返回数据以productType为维度
	     list = new ArrayList<Map.Entry<String,Map>>(((Map)paramMap.get("data")).entrySet());
	     Collections.sort(list,new Comparator<Map.Entry<String,Map>>() {
	            //升序排序
				public int compare(Entry<String, Map> o1,
						Entry<String, Map> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}

	        });
	     purMap=new HashMap();
	     backMap=new HashMap();
	     contractMap=new HashMap();
	     for(Map.Entry<String,Map> mapping:list){
   		 pKey = mapping.getKey();
   		 amtMap = (Map) mapping.getValue();
   		personStr=pKey.split("%")[0];
   		 Map userDate=(Map)userInputData.get(personStr);
   		 entry = new MarketingCommissionEntryInfo();
//   		if(amtMap.get("backAmt") == null)continue;
   		 entry.setQdPerson(personStr);
   		 if(userDate!=null){
   			 positionName =""+userDate.get("position");
       		 entry.setPosition(positionName);
   		 }
   		MonthCommissionEntryInfo me=getMonthCommissionEntry(personStr);
		 
		 entry.setContractAmtTarget(me.getSignTarget());
		 entry.setBackAmtTarget(me.getBackTarget());
		 entry.setPurTarget(me.getPurTarget());
   		 if(amtMap.get("purAmt")!=null){
   			purMap.put(personStr, FDCHelper.toBigDecimal(amtMap.get("purAmt")));
		 }
   		if(amtMap.get("contractAmt")!=null){
   			contractMap.put(personStr, FDCHelper.toBigDecimal(amtMap.get("contractAmt")));
		 }
   		
   		if(amtMap.get("backAmt")!=null){
   			backMap.put(personStr, FDCHelper.toBigDecimal(amtMap.get("backAmt")));
		 }
   		
   		 entry.setPurAmt(amtMap.get("purAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("purAmt")));
   		 entry.setContractAmt(amtMap.get("contractAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("contractAmt")));
   		 
   		 entry.setBackAmt(amtMap.get("backAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("backAmt")));
   		 
   		
   		 entry.setBonusType(4);
   		 entry.setCalcBonusAmt(amtMap.get("calcBonus")==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("calcBonus")));
   		 entry.setProductType(amtMap.get("productTypeName")==null?null:""+amtMap.get("productTypeName"));
   		 //计算各种完成比率
   		if(entry.getPurTarget() != null){
  			 entry.setPurComplateRate(entry.getPurTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getPurAmt()!=null&& entry.getPurAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getPurAmt(), entry.getPurTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
  		 }
   		 if(entry.getContractAmtTarget() != null){
   			 entry.setContractCompleteRate(entry.getContractAmtTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getContractAmt()!=null&& entry.getContractAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getContractAmt(), entry.getContractAmtTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
   		 }
   		 if(entry.getBackAmtTarget() != null){
   			 entry.setBackCompleteRate(entry.getBackAmtTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getBackAmt()!=null&& entry.getBackAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getBackAmt(), entry.getBackAmtTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
   		 }
   		 
   		 entry.setPur(amtMap.get("pur") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("pur")));
   		 entry.setContract(amtMap.get("contract") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("contract")));
   		 if(entry.getMpur() != null){
   			 entry.setPurRate(entry.getMpur().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getPur()!=null&& entry.getPur().compareTo(FDCHelper.ZERO)!=0?divide(entry.getPur(), entry.getMpur()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
   		 }
   		 if(entry.getMcontract() != null){
   			 entry.setContractRate(entry.getMcontract().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getContract()!=null&& entry.getContract().compareTo(FDCHelper.ZERO)!=0?divide(entry.getContract(), entry.getMcontract()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
   		 }
   		 
   		 entry.setCalcTBonus(amtMap.get("calcTBonus")==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("calcTBonus")));
   		 
   		 commissionEntry.add(entry);
   	 }
	     for(int i=0;i<commissionEntry.size();i++){
	    	 entry=commissionEntry.get(i);
	    	 entry.setPurAmt(FDCHelper.toBigDecimal(purMap.get(entry.getQdPerson())));
	    	 entry.setContractAmt(FDCHelper.toBigDecimal(contractMap.get(entry.getQdPerson())));
	    	 entry.setBackAmt(FDCHelper.toBigDecimal(backMap.get(entry.getQdPerson())));
	    	 if(entry.getPurTarget() != null){
				 entry.setPurComplateRate(entry.getPurTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getPurAmt()!=null&& entry.getPurAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getPurAmt(), entry.getPurTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
			 }
    	 }
	    loadDataOnTable(commissionEntry,tblQd);
	    
	    
	    
	    size = this.tblRec.getRowCount();
		userInputData = new HashMap<String, Map>();
		r = null;
		personStr = null;
		for(int i=0;i<size;i++){
	    	r = this.tblRec.getRow(i);
	    	personStr  = (String) r.getCell("person").getUserObject();
	    	if(personStr == null){
	    		continue;
	    	}
	    	Map<String,Object> goalAmtMap = new HashMap<String,Object>();
	    	goalAmtMap.put("contractAmtTarget", r.getCell("contractAmtTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmtTarget").getValue()));
	    	goalAmtMap.put("backAmtTarget", r.getCell("backAmtTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmtTarget").getValue()));
	    	goalAmtMap.put("purTarget", r.getCell("purTarget").getValue()==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purTarget").getValue()));
	    	goalAmtMap.put("person", personStr);
	    	goalAmtMap.put("position",r.getCell("position").getValue());
	    		    	
	    	userInputData.put(personStr,goalAmtMap);
	    }
	    this.tblRec.removeRows();
	     paramMap = new  HashMap();
		 paramMap.put("sellProject", editData.getSellProject().getId().toString());
		 paramMap.put("year", this.kDSpinnerYear.getValue());
		 paramMap.put("month",this.kDSpinnerMonth.getValue());
		 paramMap = CommissionSettlementBillFactory.getRemoteInstance().calcRec(paramMap);
	    
		
		 commissionEntry = new MarketingCommissionEntryCollection();
	     entry = null;
	     pKey = null;
	     dataMap = (Map)paramMap.get("data");
	     amtMap  = null;
	     userInputMap = null;
	     productSet = ((Map)paramMap.get("data")).keySet();
	     backAmt=null;
	     contractAmt=null;
	    	 //后台返回数据以productType为维度
	     list = new ArrayList<Map.Entry<String,Map>>(((Map)paramMap.get("data")).entrySet());
	     Collections.sort(list,new Comparator<Map.Entry<String,Map>>() {
	            //升序排序
				public int compare(Entry<String, Map> o1,
						Entry<String, Map> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}

	        });
	     purMap=new HashMap();
	     contractMap=new HashMap();
	     backMap=new HashMap();
	     for(Map.Entry<String,Map> mapping:list){
   		 pKey = mapping.getKey();
   		 amtMap = (Map) mapping.getValue();
   		 personStr=pKey.split("%")[0];
   		 Map userDate=(Map)userInputData.get(personStr);
   		 entry = new MarketingCommissionEntryInfo();
//   		if(amtMap.get("backAmt") == null)continue;
   		 entry.setRecommended(personStr);
   		 if(userDate!=null){
   			 positionName =""+userDate.get("position");
       		 entry.setPosition(positionName);
   		 }
   		MonthCommissionEntryInfo me=getMonthCommissionEntry(personStr);
		 
		 entry.setContractAmtTarget(me.getSignTarget());
		 entry.setBackAmtTarget(me.getBackTarget());
		 entry.setPurTarget(me.getPurTarget());
		 
   		 if(amtMap.get("purAmt")!=null){
   			purMap.put(personStr, FDCHelper.toBigDecimal(amtMap.get("purAmt")));
		 }
   		if(amtMap.get("contractAmt")!=null){
   			contractMap.put(personStr, FDCHelper.toBigDecimal(amtMap.get("contractAmt")));
		 }
   		if(amtMap.get("backAmt")!=null){
   			backMap.put(personStr, FDCHelper.toBigDecimal(amtMap.get("backAmt")));
		 }
   		entry.setPurAmt(amtMap.get("purAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("purAmt")));
   		 entry.setContractAmt(amtMap.get("contractAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("contractAmt")));
   		 
   		 entry.setBackAmt(amtMap.get("backAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("backAmt")));
   		 
   		 entry.setBonusType(5);
   		 entry.setCalcBonusAmt(amtMap.get("calcBonus")==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("calcBonus")));
   		 entry.setProductType(amtMap.get("productTypeName")==null?null:""+amtMap.get("productTypeName"));
   		 //计算各种完成比率
   		if(entry.getPurTarget() != null){
  			 entry.setPurComplateRate(entry.getPurTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getPurAmt()!=null&& entry.getPurAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getPurAmt(), entry.getPurTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
  		 }
   		 if(entry.getContractAmtTarget() != null){
   			 entry.setContractCompleteRate(entry.getContractAmtTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getContractAmt()!=null&& entry.getContractAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getContractAmt(), entry.getContractAmtTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
   		 }
   		 if(entry.getBackAmtTarget() != null){
   			 entry.setBackCompleteRate(entry.getBackAmtTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getBackAmt()!=null&& entry.getBackAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getBackAmt(), entry.getBackAmtTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
   		 }
   		 
   		 entry.setPur(amtMap.get("pur") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("pur")));
   		 entry.setContract(amtMap.get("contract") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("contract")));
   		 if(entry.getMpur() != null){
   			 entry.setPurRate(entry.getMpur().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getPur()!=null&& entry.getPur().compareTo(FDCHelper.ZERO)!=0?divide(entry.getPur(), entry.getMpur()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
   		 }
   		 if(entry.getMcontract() != null){
   			 entry.setContractRate(entry.getMcontract().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getContract()!=null&& entry.getContract().compareTo(FDCHelper.ZERO)!=0?divide(entry.getContract(), entry.getMcontract()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
   		 }
   		 
   		 entry.setCalcTBonus(amtMap.get("calcTBonus")==null?FDCHelper.ZERO:FDCHelper.toBigDecimal(amtMap.get("calcTBonus")));
   		 
   		 commissionEntry.add(entry);
   	 }
	     for(int i=0;i<commissionEntry.size();i++){
	    	 entry=commissionEntry.get(i);
	    	 entry.setPurAmt(FDCHelper.toBigDecimal(purMap.get(entry.getRecommended())));
	    	 entry.setContractAmt(FDCHelper.toBigDecimal(contractMap.get(entry.getRecommended())));
	    	 entry.setBackAmt(FDCHelper.toBigDecimal(backMap.get(entry.getRecommended())));
	    	 if(entry.getPurTarget() != null){
				 entry.setPurComplateRate(entry.getPurTarget().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO:(entry.getPurAmt()!=null&& entry.getPurAmt().compareTo(FDCHelper.ZERO)!=0?divide(entry.getPurAmt(), entry.getPurTarget()).multiply(FDCHelper.ONE_HUNDRED):FDCHelper.ZERO));
			 }
    	 }
	    loadDataOnTable(commissionEntry,tblRec);
	    
		
		refreshTotalData();
		refreshBonusTotal();
		//标识表格已经进行计算过，提示客户是否进行重算
		this.tblMgrBonus.setUserObject(Boolean.TRUE);
		
		loadQuit();
	}
	public void loadQuit() throws EASBizException, BOSException{
		this.tblQuit.checkParsed();
		this.tblQuit.removeRows();
		this.tblQuit.setEnabled(false);
		Map paramMap = new  HashMap();
		 paramMap.put("sellProject", editData.getSellProject().getId().toString());
		 paramMap.put("year", this.kDSpinnerYear.getValue());
		 paramMap.put("month",this.kDSpinnerMonth.getValue());
		 paramMap = CommissionSettlementBillFactory.getRemoteInstance().calcQuit(paramMap);
		
		 List list=(List) paramMap.get("data");
		 for(int i=0;i<list.size();i++){
			 IRow row=tblQuit.addRow();
			 Map rsMap=(Map) list.get(i);
			 Iterator<Map.Entry<String, String>> it = rsMap.entrySet().iterator();
			 while (it.hasNext()) {
				 Map.Entry<String, String> entry = it.next();
				 row.getCell(entry.getKey()).setValue(entry.getValue());
			 }
		 }

	}
	public static BigDecimal divide(Object dec1, Object dec2)
    {
/*  64*/        return divide(dec1, dec2, 4, 4);
    }
    public static BigDecimal divide(Object dec1, Object dec2, int scale, int roundingMode)
    {/*  67*/        if(dec1 == null && dec2 == null)
/*  68*/            return null;

/*  70*/        if(toBigDecimal(dec2).signum() == 0)
/*  71*/            return null;

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
	private void refreshTotalData(){
		
	    BigDecimal totalBonus = FDCHelper.ZERO;
	    
	    totalBonus = sumTheBonus();
		editData.setBonus(totalBonus);
		
		editData.setAdjustAmt(sumTheAdjustAmt());
		
		this.tblTotal.checkParsed();
		int rc = this.tblTotal.getRowCount();
		IRow r = null;
		if(rc < 1){
			r = this.tblTotal.addRow();
		}else{
			r = this.tblTotal.getRow(0);
		}
		//填充数据到
        r.getCell("bonus").setValue(editData.getBonus());
        r.getCell("contractAmt").setValue(editData.getContractAmt());
        r.getCell("backAmt").setValue(editData.getBackAmt());
        r.getCell("adjustAmt").setValue(editData.getAdjustAmt());
        r.getCell("percent").setValue(editData.getPercent());
        r.getCell("salary").setValue(editData.getSalary());
        r.getCell("baseSalary").setValue(editData.getBaseSalary());
//        r.getCell("description").setValue(editData.getDescription());
        
		
	}

	private BigDecimal sumTheBonus() {
		
		BigDecimal bonus = FDCHelper.ZERO;
		bonus = bonus.add(getSingleTotal(this.tblMgrBonus));
		bonus = bonus.add(getSingleTotal(this.tblSalesmanBonus));
		bonus = bonus.add(getSingleTotal(this.tblQdM));
		bonus = bonus.add(getSingleTotal(this.tblQd));
//		bonus = bonus.add(getSingleTotal(this.tblRec));
		return bonus;
	}
	private BigDecimal sumTheAdjustAmt() {
		
		BigDecimal bonus = FDCHelper.ZERO;
		bonus = bonus.add(getAdjustAmtTotal(this.tblMgrBonus));
		bonus = bonus.add(getAdjustAmtTotal(this.tblSalesmanBonus));
		bonus = bonus.add(getAdjustAmtTotal(this.tblQdM));
		bonus = bonus.add(getAdjustAmtTotal(this.tblQd));
//		bonus = bonus.add(getSingleTotal(this.tblRec));
		return bonus;
	}
	private BigDecimal getSingleTotal(KDTable t){
		int size = 0 ;
		BigDecimal singleTotal = FDCHelper.ZERO;
		BigDecimal bonus = FDCHelper.ZERO;
		BigDecimal xz = FDCHelper.ZERO;
		IRow r = null;
		size  = t.getRowCount();
		
		for(int i = 0 ;i < size;i++){
			r = t.getRow(i);
			bonus = r.getCell("bonus").getValue() == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
			xz = r.getCell("xz").getValue() == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
			singleTotal = FDCHelper.add(singleTotal,FDCHelper.add(bonus, xz));
		
		}
		return singleTotal;
	}
	private BigDecimal getAdjustAmtTotal(KDTable t){
		int size = 0 ;
		BigDecimal singleTotal = FDCHelper.ZERO;
		BigDecimal adjustAmt = FDCHelper.ZERO;
		IRow r = null;
		size  = t.getRowCount();
		
		for(int i = 0 ;i < size;i++){
			r = t.getRow(i);
			adjustAmt = r.getCell("adjustAmt").getValue() == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
			singleTotal = FDCHelper.add(singleTotal,adjustAmt);
		}
		return singleTotal;
	}
	
	private void loadDataOnTable(MarketingCommissionEntryCollection commissionEntry,KDTable ctrl) {
		CRMHelper.sortCollection(commissionEntry, "position", true);
		ctrl.removeRows();
		int size = commissionEntry.size();
		MarketingCommissionEntryInfo entry = null;
		IRow r = null;
		for(int i=0;i<size;i++){
			r = ctrl.addRow();
			entry = commissionEntry.get(i);
			bindDataToRow(r,entry);
		}
		ctrl.getGroupManager().group();
		
	}

	private void bindDataToRow(IRow r, AgencyCommissionEntryInfo entry) {
		// TODO Auto-generated method stub
		r.setUserObject(entry);
		r.getCell("person").setValue(entry.getPerson());
		r.getCell("person").setUserObject(entry.getPerson());
		r.getCell("position").setValue(entry.getPosition()==null?"":entry.getPosition());
		r.getCell("contractAmtTarget").setValue(entry.getContractAmtTarget());
		r.getCell("backAmtTarget").setValue(entry.getBackAmtTarget());
		r.getCell("contractAmt").setValue(entry.getContractAmt());
		r.getCell("backAmt").setValue(entry.getBackAmt());
		r.getCell("contractCompleteRate").setValue(entry.getContractCompleteRate());
		r.getCell("backCompleteRate").setValue(entry.getBackCompleteRate());
		r.getCell("productType").setValue(entry.getProductType());
		r.getCell("calcBonus").setValue(entry.getCalcBonusAmt());
		//为了重用加载
		r.getCell("calcBonusRate").setValue(entry.getCalcBonusRate());
		r.getCell("bonus").setValue(entry.getBonus());
		r.getCell("keepAmt").setValue(entry.getKeepAmt());
		r.getCell("adjustAmt").setValue(entry.getAdjustAmt());
		r.getCell("realBonusAmt").setValue(entry.getRealBonusAmt());
		r.getCell("description").setValue(entry.getDescription());
	}
	
	private void bindDataToRow(IRow r, MarketingCommissionEntryInfo entry) {
		// TODO Auto-generated method stub
		r.setUserObject(entry);
		if(entry.getBonusType()==1||entry.getBonusType()==2||entry.getBonusType()==3){
			r.getCell("person").setUserObject(entry.getPerson());
			r.getCell("person").setValue(entry.getPerson());
		}else if(entry.getBonusType()==4){
			r.getCell("person").setUserObject(entry.getQdPerson());
			r.getCell("person").setValue(entry.getQdPerson());
		}else if(entry.getBonusType()==5){
			r.getCell("person").setUserObject(entry.getRecommended());
			r.getCell("person").setValue(entry.getRecommended());
		}
		
		r.getCell("position").setValue(entry.getPosition()==null||entry.getPosition().equals("null")?"":entry.getPosition());
		r.getCell("contractAmtTarget").setValue(entry.getContractAmtTarget());
		r.getCell("backAmtTarget").setValue(entry.getBackAmtTarget());
		r.getCell("contractAmt").setValue(entry.getContractAmt());
		r.getCell("backAmt").setValue(entry.getBackAmt());
		r.getCell("contractCompleteRate").setValue(entry.getContractCompleteRate());
		r.getCell("backCompleteRate").setValue(entry.getBackCompleteRate());
		r.getCell("productType").setValue(entry.getProductType());
		r.getCell("calcBonus").setValue(entry.getCalcBonusAmt());
		//为了重用加载
	    r.getCell("calcBonusRate").setValue(entry.getCalcBonusRate());
	    r.getCell("bonus").setValue(entry.getBonus());
	    r.getCell("keepAmt").setValue(entry.getKeepAmt());
	    r.getCell("adjustAmt").setValue(entry.getAdjustAmt());
	    r.getCell("realBonusAmt").setValue(entry.getRealBonusAmt());
	    r.getCell("description").setValue(entry.getDescription());
	    r.getCell("mpur").setValue(entry.getMpur());
	    r.getCell("mcontract").setValue(entry.getMcontract());
	    r.getCell("pur").setValue(entry.getPur());
	    r.getCell("contract").setValue(entry.getContract());
	    r.getCell("purRate").setValue(entry.getPurRate());
	    r.getCell("contractRate").setValue(entry.getContractRate());
	    
	    r.getCell("calcTBonus").setValue(entry.getCalcTBonus());
	    r.getCell("calcTBonusRate").setValue(entry.getCalcTBonusRate());
	    r.getCell("xz").setValue(entry.getXz());
	    r.getCell("keepRate").setValue(entry.getKeepRate());
	    
	    r.getCell("zbRate").setValue(entry.getZbRate());
	    
	    r.getCell("purAmt").setValue(entry.getPurAmt());
	    r.getCell("purTarget").setValue(entry.getPurTarget());
	    r.getCell("purComplateRate").setValue(entry.getPurComplateRate());
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
		PersonInfo person=(PersonInfo) tblMgrBonus.getRow(activeRowIndex).getCell("person").getValue();
		if(person==null){
			removeRows(tblMgrBonus);
		}
		for(int i=tblMgrBonus.getRowCount()-1;i>=0;i--){
			PersonInfo p=(PersonInfo) tblMgrBonus.getRow(i).getCell("person").getValue();
			if(p!=null&&person!=null&&p.getId().equals(person.getId())){
				tblMgrBonus.removeRow(i);
			}
		}
	}
	@Override
	protected void tblTotal_editStopped(KDTEditEvent e) throws Exception {
		// TODO Auto-generated method stub
		IRow r = this.tblTotal.getRow(0);
		if(e.getColIndex() == this.tblTotal.getColumnIndex("adjustAmt")){
			//薪资重新计算
			BigDecimal baseSalary=r.getCell("baseSalary").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("baseSalary").getValue());
			BigDecimal adjustAmt=r.getCell("adjustAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
			BigDecimal bonus=r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
			BigDecimal contractAmt=r.getCell("contractAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmt").getValue());
			BigDecimal salary = FDCHelper.add(adjustAmt,bonus).add(baseSalary);
			r.getCell("salary").setValue(salary);
			if(contractAmt.compareTo(FDCHelper.ZERO) != 0)
			r.getCell("percent").setValue(divide(salary, contractAmt).multiply(FDCHelper.ONE_HUNDRED));
			
		}
		
		if(e.getColIndex() == this.tblTotal.getColumnIndex("bonus")){
			//薪资重新计算
			BigDecimal baseSalary=r.getCell("baseSalary").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("baseSalary").getValue());
			BigDecimal adjustAmt=r.getCell("adjustAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
			BigDecimal bonus=e.getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(e.getValue());
			BigDecimal contractAmt=r.getCell("contractAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmt").getValue());
			BigDecimal salary = FDCHelper.add(adjustAmt,bonus).add(baseSalary);
			r.getCell("salary").setValue(salary);
			if(contractAmt.compareTo(FDCHelper.ZERO) != 0)
			r.getCell("percent").setValue(divide(salary, contractAmt).multiply(FDCHelper.ONE_HUNDRED));
			
		}
		if(e.getColIndex() == this.tblTotal.getColumnIndex("baseSalary")){
			BigDecimal baseSalary=e.getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(e.getValue());
			BigDecimal adjustAmt=r.getCell("adjustAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
			BigDecimal bonus=r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
			BigDecimal contractAmt=r.getCell("contractAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmt").getValue());
			BigDecimal salary = FDCHelper.add(adjustAmt,bonus).add(baseSalary);
			r.getCell("salary").setValue(salary);
			if(contractAmt.compareTo(FDCHelper.ZERO) != 0)
			r.getCell("percent").setValue(divide(salary, contractAmt).multiply(FDCHelper.ONE_HUNDRED));
		}
		
	}
	
	
	
	
	@Override
	protected void tblSalesmanBonus_editStopped(KDTEditEvent e)
			throws Exception {
		IRow r = this.tblSalesmanBonus.getRow(e.getRowIndex());
		int colIndex = e.getColIndex();
		PersonInfo person=(PersonInfo) tblSalesmanBonus.getRow(e.getRowIndex()).getCell("person").getValue();
		
		if(colIndex == this.tblSalesmanBonus.getColumnIndex("purTarget")){
        	BigDecimal purTarget = r.getCell("purTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purTarget").getValue());
        	BigDecimal purAmt = r.getCell("purAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purAmt").getValue());
        	if(purTarget==null||purTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("purComplateRate").setValue(divide(purAmt,purTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblSalesmanBonus.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblSalesmanBonus.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblSalesmanBonus.getRow(i).getCell("purTarget").setValue(purTarget);
        			tblSalesmanBonus.getRow(i).getCell("purComplateRate").setValue(divide(purAmt,purTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if(colIndex == this.tblSalesmanBonus.getColumnIndex("contractAmtTarget")){
        	BigDecimal contractAmtTarget = r.getCell("contractAmtTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmtTarget").getValue());
        	BigDecimal contractAmt = r.getCell("contractAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmt").getValue());
        	if(contractAmtTarget==null||contractAmtTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("contractCompleteRate").setValue(divide(contractAmt,contractAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblSalesmanBonus.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblSalesmanBonus.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblSalesmanBonus.getRow(i).getCell("contractAmtTarget").setValue(contractAmtTarget);
        			tblSalesmanBonus.getRow(i).getCell("contractCompleteRate").setValue(divide(contractAmt,contractAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblSalesmanBonus.getColumnIndex("backAmtTarget")){
        	BigDecimal backAmtTarget = r.getCell("backAmtTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmtTarget").getValue());
        	BigDecimal backAmt = r.getCell("backAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmt").getValue());
        	if(backAmtTarget==null||backAmtTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("backCompleteRate").setValue(divide(backAmt,backAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblSalesmanBonus.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblSalesmanBonus.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblSalesmanBonus.getRow(i).getCell("backAmtTarget").setValue(backAmtTarget);
        			tblSalesmanBonus.getRow(i).getCell("backCompleteRate").setValue(divide(backAmt,backAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblSalesmanBonus.getColumnIndex("zbRate")){
        	BigDecimal zbRate = r.getCell("zbRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("zbRate").getValue());
        	for(int i=e.getRowIndex()+1;i<tblSalesmanBonus.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblSalesmanBonus.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblSalesmanBonus.getRow(i).getCell("zbRate").setValue(zbRate);
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblSalesmanBonus.getColumnIndex("position")){
        	String position = (String) r.getCell("position").getValue();
        	for(int i=e.getRowIndex()+1;i<tblSalesmanBonus.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblSalesmanBonus.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblSalesmanBonus.getRow(i).getCell("position").setValue(position);
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblSalesmanBonus.getColumnIndex("calcBonusRate")||colIndex == this.tblSalesmanBonus.getColumnIndex("calcTBonusRate")
        		||colIndex == this.tblSalesmanBonus.getColumnIndex("xz")
        		||colIndex == this.tblSalesmanBonus.getColumnIndex("keepRate")){
        	BigDecimal calcBonus = r.getCell("calcBonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcBonus").getValue());
        	BigDecimal calcBonusRate = r.getCell("calcBonusRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcBonusRate").getValue());
        	BigDecimal calcTBonus = r.getCell("calcTBonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcTBonus").getValue());
        	BigDecimal calcTBonusRate = r.getCell("calcTBonusRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcTBonusRate").getValue());
        	r.getCell("bonus").setValue(divide(FDCHelper.multiply(calcBonus,calcBonusRate),FDCHelper.ONE_HUNDRED).add(FDCHelper.multiply(calcTBonus,calcTBonusRate)));
        	
        	BigDecimal bonus = r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
        	
        	BigDecimal xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
        	BigDecimal keepRate = r.getCell("keepRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepRate").getValue());
        	r.getCell("keepAmt").setValue(divide(FDCHelper.multiply(bonus.add(xz),keepRate),FDCHelper.ONE_HUNDRED));
        	
        	
        	calcRealBonusAmt(r);
        }
        if( colIndex == this.tblSalesmanBonus.getColumnIndex("keepAmt")||colIndex == this.tblSalesmanBonus.getColumnIndex("adjustAmt")){
        	calcRealBonusAmt(r);
        }
        if( colIndex == this.tblSalesmanBonus.getColumnIndex("mpur")){
        	BigDecimal mpur = r.getCell("mpur").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("mpur").getValue());
        	BigDecimal pur = r.getCell("pur").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("pur").getValue());
        	if(mpur==null||mpur.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("purRate").setValue(divide(pur,mpur).multiply(FDCHelper.ONE_HUNDRED));
        }
        if( colIndex == this.tblSalesmanBonus.getColumnIndex("mcontract")){
        	BigDecimal mcontract =r.getCell("mcontract").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("mcontract").getValue());
        	BigDecimal contract = r.getCell("contract").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contract").getValue());
        	if(mcontract==null||mcontract.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("contractRate").setValue(divide(contract,mcontract).multiply(FDCHelper.ONE_HUNDRED));
        }
	}
	protected void tblQd_editStopped(KDTEditEvent e) throws Exception {
		IRow r = this.tblQd.getRow(e.getRowIndex());
		int colIndex = e.getColIndex();
		String person=(String) tblQd.getRow(e.getRowIndex()).getCell("person").getValue();
		if(colIndex == this.tblQd.getColumnIndex("purTarget")){
        	BigDecimal purTarget = r.getCell("purTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purTarget").getValue());
        	BigDecimal purAmt = r.getCell("purAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purAmt").getValue());
        	if(purTarget==null||purTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("purComplateRate").setValue(divide(purAmt,purTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblQd.getRowCount();i++){
        		String p=(String) tblQd.getRow(i).getCell("person").getValue();
        		if(person.equals(p)){
        			tblQd.getRow(i).getCell("purTarget").setValue(purTarget);
        			tblQd.getRow(i).getCell("purComplateRate").setValue(divide(purAmt,purTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if(colIndex == this.tblQd.getColumnIndex("contractAmtTarget")){
        	BigDecimal contractAmtTarget = r.getCell("contractAmtTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmtTarget").getValue());
        	BigDecimal contractAmt = r.getCell("contractAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmt").getValue());
        	if(contractAmtTarget==null||contractAmtTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("contractCompleteRate").setValue(divide(contractAmt,contractAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblQd.getRowCount();i++){
        		String p=(String) tblQd.getRow(i).getCell("person").getValue();
        		if(person.equals(p)){
        			tblQd.getRow(i).getCell("contractAmtTarget").setValue(contractAmtTarget);
        			tblQd.getRow(i).getCell("contractCompleteRate").setValue(divide(contractAmt,contractAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblQd.getColumnIndex("backAmtTarget")){
        	BigDecimal backAmtTarget = r.getCell("backAmtTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmtTarget").getValue());
        	BigDecimal backAmt = r.getCell("backAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmt").getValue());
        	if(backAmtTarget==null||backAmtTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("backCompleteRate").setValue(divide(backAmt,backAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblQd.getRowCount();i++){
        		String p=(String) tblQd.getRow(i).getCell("person").getValue();
        		if(person.equals(p)){
        			tblQd.getRow(i).getCell("backAmtTarget").setValue(backAmtTarget);
        			tblQd.getRow(i).getCell("backCompleteRate").setValue(divide(backAmt,backAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblQd.getColumnIndex("zbRate")){
        	BigDecimal zbRate = r.getCell("zbRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("zbRate").getValue());
        	for(int i=e.getRowIndex()+1;i<tblQd.getRowCount();i++){
        		String p=(String) tblQd.getRow(i).getCell("person").getValue();
        		if(person.equals(p)){
        			tblQd.getRow(i).getCell("zbRate").setValue(zbRate);
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblQd.getColumnIndex("position")){
        	String position = (String) r.getCell("position").getValue();
        	for(int i=e.getRowIndex()+1;i<tblQd.getRowCount();i++){
        		String p=(String) tblQd.getRow(i).getCell("person").getValue();
        		if(person.equals(p)){
        			tblQd.getRow(i).getCell("position").setValue(position);
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblQd.getColumnIndex("calcBonusRate")||colIndex == this.tblQd.getColumnIndex("calcTBonusRate")
        		||colIndex == this.tblQd.getColumnIndex("xz")
        		||colIndex == this.tblQd.getColumnIndex("keepRate")){
        	BigDecimal calcBonus = r.getCell("calcBonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcBonus").getValue());
        	BigDecimal calcBonusRate = r.getCell("calcBonusRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcBonusRate").getValue());
        	BigDecimal calcTBonus = r.getCell("calcTBonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcTBonus").getValue());
        	BigDecimal calcTBonusRate = r.getCell("calcTBonusRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcTBonusRate").getValue());
        	r.getCell("bonus").setValue(divide(FDCHelper.multiply(calcBonus,calcBonusRate),FDCHelper.ONE_HUNDRED).add(FDCHelper.multiply(calcTBonus,calcTBonusRate)));
        	
        	BigDecimal bonus = r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
        	
        	BigDecimal xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
        	BigDecimal keepRate = r.getCell("keepRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepRate").getValue());
        	r.getCell("keepAmt").setValue(divide(FDCHelper.multiply(bonus.add(xz),keepRate),FDCHelper.ONE_HUNDRED));
        	
        	
        	calcRealBonusAmt(r);
        }
        if( colIndex == this.tblQd.getColumnIndex("keepAmt")||colIndex == this.tblQd.getColumnIndex("adjustAmt")){
        	calcRealBonusAmt(r);
        }
        if( colIndex == this.tblQd.getColumnIndex("mpur")){
        	BigDecimal mpur = r.getCell("mpur").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("mpur").getValue());
        	BigDecimal pur = r.getCell("pur").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("pur").getValue());
        	if(mpur==null||mpur.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("purRate").setValue(divide(pur,mpur).multiply(FDCHelper.ONE_HUNDRED));
        }
        if( colIndex == this.tblQd.getColumnIndex("mcontract")){
        	BigDecimal mcontract =r.getCell("mcontract").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("mcontract").getValue());
        	BigDecimal contract = r.getCell("contract").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contract").getValue());
        	if(mcontract==null||mcontract.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("contractRate").setValue(divide(contract,mcontract).multiply(FDCHelper.ONE_HUNDRED));
        }
	}
	protected void tblRec_editStopped(KDTEditEvent e) throws Exception {
		IRow r = this.tblRec.getRow(e.getRowIndex());
		int colIndex = e.getColIndex();
		String person=(String) tblRec.getRow(e.getRowIndex()).getCell("person").getValue();
		if(colIndex == this.tblRec.getColumnIndex("purTarget")){
        	BigDecimal purTarget = r.getCell("purTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purTarget").getValue());
        	BigDecimal purAmt = r.getCell("purAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purAmt").getValue());
        	if(purTarget==null||purTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("purComplateRate").setValue(divide(purAmt,purTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblRec.getRowCount();i++){
        		String p=(String) tblQd.getRow(i).getCell("person").getValue();
        		if(person.equals(p)){
        			tblRec.getRow(i).getCell("purTarget").setValue(purTarget);
        			tblRec.getRow(i).getCell("purComplateRate").setValue(divide(purAmt,purTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if(colIndex == this.tblRec.getColumnIndex("contractAmtTarget")){
        	BigDecimal contractAmtTarget = r.getCell("contractAmtTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmtTarget").getValue());
        	BigDecimal contractAmt = r.getCell("contractAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmt").getValue());
        	if(contractAmtTarget==null||contractAmtTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("contractCompleteRate").setValue(divide(contractAmt,contractAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblRec.getRowCount();i++){
        		String p=(String) tblRec.getRow(i).getCell("person").getValue();
        		if(person.equals(p)){
        			tblRec.getRow(i).getCell("contractAmtTarget").setValue(contractAmtTarget);
        			tblRec.getRow(i).getCell("contractCompleteRate").setValue(divide(contractAmt,contractAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblRec.getColumnIndex("backAmtTarget")){
        	BigDecimal backAmtTarget = r.getCell("backAmtTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmtTarget").getValue());
        	BigDecimal backAmt = r.getCell("backAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmt").getValue());
        	if(backAmtTarget==null||backAmtTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("backCompleteRate").setValue(divide(backAmt,backAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblRec.getRowCount();i++){
        		String p=(String) tblRec.getRow(i).getCell("person").getValue();
        		if(person.equals(p)){
        			tblRec.getRow(i).getCell("backAmtTarget").setValue(backAmtTarget);
        			tblRec.getRow(i).getCell("backCompleteRate").setValue(divide(backAmt,backAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblRec.getColumnIndex("zbRate")){
        	BigDecimal zbRate = r.getCell("zbRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("zbRate").getValue());
        	for(int i=e.getRowIndex()+1;i<tblRec.getRowCount();i++){
        		String p=(String) tblRec.getRow(i).getCell("person").getValue();
        		if(person.equals(p)){
        			tblRec.getRow(i).getCell("zbRate").setValue(zbRate);
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblRec.getColumnIndex("position")){
        	String position = (String) r.getCell("position").getValue();
        	for(int i=e.getRowIndex()+1;i<tblRec.getRowCount();i++){
        		String p=(String) tblRec.getRow(i).getCell("person").getValue();
        		if(person.equals(p)){
        			tblRec.getRow(i).getCell("position").setValue(position);
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblRec.getColumnIndex("calcBonusRate")||colIndex == this.tblRec.getColumnIndex("calcTBonusRate")
        		||colIndex == this.tblRec.getColumnIndex("xz")
        		||colIndex == this.tblRec.getColumnIndex("keepRate")){
        	BigDecimal calcBonus = r.getCell("calcBonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcBonus").getValue());
        	BigDecimal calcBonusRate = r.getCell("calcBonusRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcBonusRate").getValue());
        	BigDecimal calcTBonus = r.getCell("calcTBonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcTBonus").getValue());
        	BigDecimal calcTBonusRate = r.getCell("calcTBonusRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcTBonusRate").getValue());
        	r.getCell("bonus").setValue(divide(FDCHelper.multiply(calcBonus,calcBonusRate),FDCHelper.ONE_HUNDRED).add(FDCHelper.multiply(calcTBonus,calcTBonusRate)));
        	
        	BigDecimal bonus = r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
        	
        	BigDecimal xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
        	BigDecimal keepRate = r.getCell("keepRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepRate").getValue());
        	r.getCell("keepAmt").setValue(divide(FDCHelper.multiply(bonus.add(xz),keepRate),FDCHelper.ONE_HUNDRED));
        	
        	
        	calcRealBonusAmt(r);
        }
        if( colIndex == this.tblRec.getColumnIndex("keepAmt")||colIndex == this.tblRec.getColumnIndex("adjustAmt")){
        	calcRealBonusAmt(r);
        }
        if( colIndex == this.tblRec.getColumnIndex("mpur")){
        	BigDecimal mpur = r.getCell("mpur").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("mpur").getValue());
        	BigDecimal pur = r.getCell("pur").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("pur").getValue());
        	if(mpur==null||mpur.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("purRate").setValue(divide(pur,mpur).multiply(FDCHelper.ONE_HUNDRED));
        }
        if( colIndex == this.tblRec.getColumnIndex("mcontract")){
        	BigDecimal mcontract =r.getCell("mcontract").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("mcontract").getValue());
        	BigDecimal contract = r.getCell("contract").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contract").getValue());
        	if(mcontract==null||mcontract.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("contractRate").setValue(divide(contract,mcontract).multiply(FDCHelper.ONE_HUNDRED));
        }
	}
	@Override
	protected void btnAddQdM_actionPerformed(ActionEvent e) throws Exception {
		if(tblMgrBonus.getRowCount()==0
				||tblMgrBonus.getRow(0).getCell("productType").getValue()==null){
			FDCMsgBox.showWarning(this, "请先提取数据！");
			return;
		}
		Set s=new HashSet();
		for(int i=0;i<tblQdM.getRowCount();i++){
			if(tblQdM.getRow(i).getCell("person").getValue()==null){
				FDCMsgBox.showWarning(this, "请先选择人员！");
				tblQdM.getEditManager().editCellAt(i, 0);
				return;
			}
		}
		for(int i=0;i<tblMgrBonus.getRowCount();i++){
			String p=tblMgrBonus.getRow(i).getCell("productType").getValue().toString();
			if(s.contains(p)){
				continue;
			}else{
				s.add(p);
				IRow r=this.tblQdM.addRow();
				MarketingCommissionEntryInfo info=new MarketingCommissionEntryInfo();
				r.setUserObject(info);
				r.getCell("productType").setValue(p);
			}
		}
		this.tblQdM.getGroupManager().group();
	}
	protected void btnRemoveQdM_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = tblQdM.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("请先选择一行数据");
			abort();
		}
		PersonInfo person=(PersonInfo) tblQdM.getRow(activeRowIndex).getCell("person").getValue();
		
		for(int i=tblQdM.getRowCount()-1;i>=0;i--){
			PersonInfo p=(PersonInfo) tblQdM.getRow(i).getCell("person").getValue();
			if(p==null&&person==null){
				tblQdM.removeRow(i);
			}
			if(p!=null&&person!=null&&p.getId().equals(person.getId())){
				tblQdM.removeRow(i);
			}
		}
	}

	protected void tblQdM_editStopped(KDTEditEvent e) throws Exception {
		IRow r = this.tblQdM.getRow(e.getRowIndex());
		int colIndex = e.getColIndex();
		PersonInfo person=(PersonInfo) tblQdM.getRow(e.getRowIndex()).getCell("person").getValue();
		if(colIndex != this.tblQdM.getColumnIndex("person")&&person==null){
			FDCMsgBox.showWarning(this,"请先选择人员！");
			tblQdM.getEditManager().editCellAt(e.getRowIndex(), 0);
			return;
		}
		if(colIndex == this.tblQdM.getColumnIndex("person")){
			PersonInfo source=(PersonInfo) e.getOldValue();
			if(source!=null&&person==null){
				FDCMsgBox.showWarning(this,"人员不能为空！");
				tblQdM.getRow(e.getRowIndex()).getCell("person").setValue(source);
				return;
			}
			for(int i=0;i<e.getRowIndex();i++){
        		PersonInfo p=(PersonInfo) tblQdM.getRow(i).getCell("person").getValue();
        		if(person!=null&&p!=null&&person.getId().equals(p.getId())){
        			FDCMsgBox.showWarning(this,"人员已存在！");
        			tblQdM.getRow(e.getRowIndex()).getCell("person").setValue(source);
    				return;
        		}
        	}
        	for(int i=e.getRowIndex()+1;i<tblQdM.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblQdM.getRow(i).getCell("person").getValue();
        		if(source==null&&p==null){
        			tblQdM.getRow(i).getCell("person").setValue(person);
        		}
        		if(source!=null&&p!=null&&source.getId().equals(p.getId())){
        			tblQdM.getRow(i).getCell("person").setValue(person);
        		}
        		if(source!=null&&p!=null&&!source.getId().equals(p.getId())){
        			break;
        		}
        	}
        	MonthCommissionEntryInfo entry=getMonthCommissionEntry(person.getName());
			BigDecimal purTarget=entry.getPurTarget();
			tblQdM.getRow(e.getRowIndex()).getCell("purTarget").setValue(purTarget);
			BigDecimal purAmt = r.getCell("purAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purAmt").getValue());
			if(purTarget!=null&&purTarget.compareTo(FDCHelper.ZERO)!=0){
				r.getCell("purComplateRate").setValue(divide(purAmt,purTarget).multiply(FDCHelper.ONE_HUNDRED));
	        	for(int i=e.getRowIndex()+1;i<tblQdM.getRowCount();i++){
	        		PersonInfo p=(PersonInfo) tblQdM.getRow(i).getCell("person").getValue();
	        		if(person.getId().equals(p.getId())){
	        			tblQdM.getRow(i).getCell("purTarget").setValue(purTarget);
	        			tblQdM.getRow(i).getCell("purComplateRate").setValue(divide(purAmt,purTarget).multiply(FDCHelper.ONE_HUNDRED));
	        		}else{
	        			break;
	        		}
	        	}
        	}
			
			BigDecimal contractAmtTarget =entry.getSignTarget();
			tblQdM.getRow(e.getRowIndex()).getCell("contractAmtTarget").setValue(contractAmtTarget);
        	BigDecimal contractAmt = r.getCell("contractAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmt").getValue());
        	if(contractAmtTarget!=null&&contractAmtTarget.compareTo(FDCHelper.ZERO)!=0){
        		r.getCell("contractCompleteRate").setValue(divide(contractAmt,contractAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
            	for(int i=e.getRowIndex()+1;i<tblQdM.getRowCount();i++){
            		PersonInfo p=(PersonInfo) tblQdM.getRow(i).getCell("person").getValue();
            		if(person.getId().equals(p.getId())){
            			tblQdM.getRow(i).getCell("contractAmtTarget").setValue(contractAmtTarget);
            			tblQdM.getRow(i).getCell("contractCompleteRate").setValue(divide(contractAmt,contractAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
            		}else{
            			break;
            		}
            	}
        	}
        	BigDecimal backAmtTarget = entry.getBackTarget();
        	tblQdM.getRow(e.getRowIndex()).getCell("backAmtTarget").setValue(backAmtTarget);
        	BigDecimal backAmt = r.getCell("backAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmt").getValue());
        	if(backAmtTarget!=null&&backAmtTarget.compareTo(FDCHelper.ZERO)!=0){
        		r.getCell("backCompleteRate").setValue(divide(backAmt,backAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
            	for(int i=e.getRowIndex()+1;i<tblQdM.getRowCount();i++){
            		PersonInfo p=(PersonInfo) tblQdM.getRow(i).getCell("person").getValue();
            		if(person.getId().equals(p.getId())){
            			tblQdM.getRow(i).getCell("backAmtTarget").setValue(backAmtTarget);
            			tblQdM.getRow(i).getCell("backCompleteRate").setValue(divide(backAmt,backAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
            		}else{
            			break;
            		}
            	}
        	}
        }
		if(colIndex == this.tblQdM.getColumnIndex("purTarget")||colIndex == this.tblQdM.getColumnIndex("purAmt")){
        	BigDecimal purTarget = r.getCell("purTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purTarget").getValue());
        	BigDecimal purAmt = r.getCell("purAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purAmt").getValue());
        	if(purTarget==null||purTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("purComplateRate").setValue(divide(purAmt,purTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblQdM.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblQdM.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblQdM.getRow(i).getCell("purAmt").setValue(purAmt);
        			tblQdM.getRow(i).getCell("purTarget").setValue(purTarget);
        			tblQdM.getRow(i).getCell("purComplateRate").setValue(divide(purAmt,purTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if(colIndex == this.tblQdM.getColumnIndex("contractAmtTarget")||colIndex == this.tblQdM.getColumnIndex("contractAmt")){
        	BigDecimal contractAmtTarget = r.getCell("contractAmtTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmtTarget").getValue());
        	BigDecimal contractAmt = r.getCell("contractAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmt").getValue());
        	if(contractAmtTarget==null||contractAmtTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("contractCompleteRate").setValue(divide(contractAmt,contractAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblQdM.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblQdM.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblQdM.getRow(i).getCell("contractAmt").setValue(contractAmt);
        			tblQdM.getRow(i).getCell("contractAmtTarget").setValue(contractAmtTarget);
        			tblQdM.getRow(i).getCell("contractCompleteRate").setValue(divide(contractAmt,contractAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if(colIndex == this.tblQdM.getColumnIndex("backAmtTarget")||colIndex == this.tblQdM.getColumnIndex("backAmt")){
        	BigDecimal backAmtTarget = r.getCell("backAmtTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmtTarget").getValue());
        	BigDecimal backAmt = r.getCell("backAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmt").getValue());
        	if(backAmtTarget==null||backAmtTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("backCompleteRate").setValue(divide(backAmt,backAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblQdM.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblQdM.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblQdM.getRow(i).getCell("backAmt").setValue(backAmt);
        			tblQdM.getRow(i).getCell("backAmtTarget").setValue(backAmtTarget);
        			tblQdM.getRow(i).getCell("backCompleteRate").setValue(divide(backAmt,backAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblQdM.getColumnIndex("zbRate")){
        	BigDecimal zbRate = r.getCell("zbRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("zbRate").getValue());
        	for(int i=e.getRowIndex()+1;i<tblQdM.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblQdM.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblQdM.getRow(i).getCell("zbRate").setValue(zbRate);
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblQdM.getColumnIndex("position")){
        	String position = (String) r.getCell("position").getValue();
        	for(int i=e.getRowIndex()+1;i<tblQdM.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblQdM.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblQdM.getRow(i).getCell("position").setValue(position);
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblQdM.getColumnIndex("calcBonus")||colIndex == this.tblQdM.getColumnIndex("calcTBonus")
        		||colIndex == this.tblQdM.getColumnIndex("calcBonusRate")||colIndex == this.tblQdM.getColumnIndex("calcTBonusRate")
        		||colIndex == this.tblQdM.getColumnIndex("xz")
        		||colIndex == this.tblQdM.getColumnIndex("keepRate")){
        	BigDecimal calcBonus = r.getCell("calcBonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcBonus").getValue());
        	BigDecimal calcBonusRate = r.getCell("calcBonusRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcBonusRate").getValue());
        	BigDecimal calcTBonus = r.getCell("calcTBonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcTBonus").getValue());
        	BigDecimal calcTBonusRate = r.getCell("calcTBonusRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcTBonusRate").getValue());
        	r.getCell("bonus").setValue(divide(FDCHelper.multiply(calcBonus,calcBonusRate),FDCHelper.ONE_HUNDRED).add(FDCHelper.multiply(calcTBonus,calcTBonusRate)));
        	
        	BigDecimal bonus = r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
        	
        	BigDecimal xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
        	BigDecimal keepRate = r.getCell("keepRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepRate").getValue());
        	r.getCell("keepAmt").setValue(divide(FDCHelper.multiply(bonus.add(xz),keepRate),FDCHelper.ONE_HUNDRED));
        	
        	
        	calcRealBonusAmt(r);
        }
        if( colIndex == this.tblQdM.getColumnIndex("keepAmt")||colIndex == this.tblQdM.getColumnIndex("adjustAmt")){
        	calcRealBonusAmt(r);
        }
        if( colIndex == this.tblQdM.getColumnIndex("mpur")||colIndex == this.tblQdM.getColumnIndex("pur")){
        	BigDecimal mpur = r.getCell("mpur").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("mpur").getValue());
        	BigDecimal pur = r.getCell("pur").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("pur").getValue());
        	if(mpur==null||mpur.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("purRate").setValue(divide(pur,mpur).multiply(FDCHelper.ONE_HUNDRED));
        }
        if( colIndex == this.tblQdM.getColumnIndex("mcontract")||colIndex == this.tblQdM.getColumnIndex("contract")){
        	BigDecimal mcontract =r.getCell("mcontract").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("mcontract").getValue());
        	BigDecimal contract = r.getCell("contract").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contract").getValue());
        	if(mcontract==null||mcontract.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("contractRate").setValue(divide(contract,mcontract).multiply(FDCHelper.ONE_HUNDRED));
        }
	}

	private void refreshBonusTotal(){
		Map<String,Map<String,Object>> resultMap = calcBonusTotalByPerson();
		this.tblBounsTotal.removeRows();
		IRow r  = null;
		
		 List<Map.Entry<String,Map<String,Object>>> list = new ArrayList<Map.Entry<String,Map<String,Object>>>(resultMap.entrySet());
	     Collections.sort(list,new Comparator<Map.Entry<String,Map<String,Object>>>() {
	            //升序排序
				public int compare(Entry<String,Map<String,Object>> o1,
						Entry<String,Map<String,Object>> o2) {
					Comparable tmp0 = (Comparable)o1.getKey();
					Comparable tmp1 = (Comparable)o2.getKey();
					
					return tmp0.compareTo(tmp1);
				}
	        });
	     
		Map<String,BigDecimal>  rMap = null;
		String key = null;
		for(Map.Entry<String,Map<String,Object>> mapping:list){
			key = mapping.getKey();
			rMap = (Map) mapping.getValue();
			r = this.tblBounsTotal.addRow();
			
			r.getCell("person").setValue(key);
			r.getCell("bonus").setValue(rMap.get("bonus"));
			r.getCell("keepAmt").setValue(rMap.get("keepAmt"));
			r.getCell("adjustAmt").setValue(rMap.get("adjustAmt"));
			r.getCell("realAmt").setValue(rMap.get("realAmt"));
			r.getCell("xz").setValue(rMap.get("xz"));
			r.getCell("position").setValue(rMap.get("position"));
		}
	}
	
	private Map<String, Map<String, Object>> calcBonusTotalByPerson() {
        
		Map<String,Map<String,Object>> rMap = new HashMap<String, Map<String,Object>>();
		int rc = tblMgrBonus.getRowCount();
		String key = null;
		IRow r = null;
		PersonInfo p = null;
		
		BigDecimal bonus = FDCHelper.ZERO;
		BigDecimal keepAmt = FDCHelper.ZERO;
		BigDecimal adjustAmt = FDCHelper.ZERO;
		BigDecimal realAmt = FDCHelper.ZERO;
		BigDecimal xz = FDCHelper.ZERO;
		for(int i =0;i<rc;i++){
			r =tblMgrBonus.getRow(i);
			p = (PersonInfo) r.getCell("person").getUserObject();
			if(p==null) continue;
		    key = p.getName().toString();
		    Map<String,Object> bonusMap = null;
			if(rMap.containsKey(key)){
				bonusMap = rMap.get(key);
				bonus =r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
				keepAmt = r.getCell("keepAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepAmt").getValue());
				adjustAmt = r.getCell("adjustAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
				realAmt = r.getCell("realBonusAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("realBonusAmt").getValue());
				xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
				if(bonusMap.get("bonus") != null){
					bonusMap.put("bonus", bonus.add((BigDecimal)bonusMap.get("bonus")));
				}
				if(bonusMap.get("keepAmt") != null){
					bonusMap.put("keepAmt", keepAmt.add((BigDecimal)bonusMap.get("keepAmt")));
				}
				if(bonusMap.get("adjustAmt") != null){
					bonusMap.put("adjustAmt", adjustAmt.add((BigDecimal)bonusMap.get("adjustAmt")));
				}
				if(bonusMap.get("realAmt") != null){
					bonusMap.put("realAmt", realAmt.add((BigDecimal)bonusMap.get("realAmt")));
				}
				if(bonusMap.get("xz") != null){
					bonusMap.put("xz", xz.add((BigDecimal)bonusMap.get("xz")));
				}
			}else{
				bonusMap = new HashMap<String, Object>();
				bonus =r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
				keepAmt = r.getCell("keepAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepAmt").getValue());
				adjustAmt = r.getCell("adjustAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
				realAmt = r.getCell("realBonusAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("realBonusAmt").getValue());
				xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
				
				
				bonusMap.put("bonus", bonus);
				bonusMap.put("keepAmt", keepAmt);
				bonusMap.put("adjustAmt", adjustAmt);
				bonusMap.put("realAmt", realAmt);
				bonusMap.put("xz", xz);
				bonusMap.put("position", r.getCell("position").getValue());
			}
			
			rMap.put(key, bonusMap);
		}
		
		rc = this.tblSalesmanBonus.getRowCount();
		for(int i =0;i<rc;i++){
			r =tblSalesmanBonus.getRow(i);
			p = (PersonInfo) r.getCell("person").getUserObject();
			
		    key = p.getName().toString();
		    Map<String,Object> bonusMap = null;
			if(rMap.containsKey(key)){
				bonusMap = rMap.get(key);
				bonus =r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
				keepAmt = r.getCell("keepAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepAmt").getValue());
				adjustAmt = r.getCell("adjustAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
				realAmt = r.getCell("realBonusAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("realBonusAmt").getValue());
				xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
				
				if(bonusMap.get("bonus") != null){
					bonusMap.put("bonus", bonus.add((BigDecimal)bonusMap.get("bonus")));
				}
				if(bonusMap.get("keepAmt") != null){
					bonusMap.put("keepAmt", keepAmt.add((BigDecimal)bonusMap.get("keepAmt")));
				}
				if(bonusMap.get("adjustAmt") != null){
					bonusMap.put("adjustAmt", adjustAmt.add((BigDecimal)bonusMap.get("adjustAmt")));
				}
				if(bonusMap.get("realAmt") != null){
					bonusMap.put("realAmt", realAmt.add((BigDecimal)bonusMap.get("realAmt")));
				}
				if(bonusMap.get("xz") != null){
					bonusMap.put("xz", xz.add((BigDecimal)bonusMap.get("xz")));
				}
			}else{
				bonusMap = new HashMap<String, Object>();
				bonus =r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
				keepAmt = r.getCell("keepAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepAmt").getValue());
				adjustAmt = r.getCell("adjustAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
				realAmt = r.getCell("realBonusAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("realBonusAmt").getValue());
				xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
				
				
				bonusMap.put("bonus", bonus);
				bonusMap.put("keepAmt", keepAmt);
				bonusMap.put("adjustAmt", adjustAmt);
				bonusMap.put("realAmt", realAmt);
				bonusMap.put("xz", xz);
				bonusMap.put("position", r.getCell("position").getValue());
			}
			
			rMap.put(key, bonusMap);
		}
		
		rc = this.tblQdM.getRowCount();
		for(int i =0;i<rc;i++){
			r =tblQdM.getRow(i);
			p = (PersonInfo) r.getCell("person").getValue();
			if(p==null)continue;
		    key = p.getName().toString();
		    Map<String,Object> bonusMap = null;
			if(rMap.containsKey(key)){
				bonusMap = rMap.get(key);
				bonus =r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
				keepAmt = r.getCell("keepAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepAmt").getValue());
				adjustAmt = r.getCell("adjustAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
				realAmt = r.getCell("realBonusAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("realBonusAmt").getValue());
				xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
				
				if(bonusMap.get("bonus") != null){
					bonusMap.put("bonus", bonus.add((BigDecimal)bonusMap.get("bonus")));
				}
				if(bonusMap.get("keepAmt") != null){
					bonusMap.put("keepAmt", keepAmt.add((BigDecimal)bonusMap.get("keepAmt")));
				}
				if(bonusMap.get("adjustAmt") != null){
					bonusMap.put("adjustAmt", adjustAmt.add((BigDecimal)bonusMap.get("adjustAmt")));
				}
				if(bonusMap.get("realAmt") != null){
					bonusMap.put("realAmt", realAmt.add((BigDecimal)bonusMap.get("realAmt")));
				}
				if(bonusMap.get("xz") != null){
					bonusMap.put("xz", xz.add((BigDecimal)bonusMap.get("xz")));
				}
			}else{
				bonusMap = new HashMap<String, Object>();
				bonus =r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
				keepAmt = r.getCell("keepAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepAmt").getValue());
				adjustAmt = r.getCell("adjustAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
				realAmt = r.getCell("realBonusAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("realBonusAmt").getValue());
				xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
				
				
				bonusMap.put("bonus", bonus);
				bonusMap.put("keepAmt", keepAmt);
				bonusMap.put("adjustAmt", adjustAmt);
				bonusMap.put("realAmt", realAmt);
				bonusMap.put("xz", xz);
				bonusMap.put("position", r.getCell("position").getValue());
			}
			rMap.put(key, bonusMap);
		}
		
		rc = this.tblQd.getRowCount();
		for(int i =0;i<rc;i++){
			r =tblQd.getRow(i);
			
		    key =r.getCell("person").getValue().toString();
		    Map<String,Object> bonusMap = null;
			if(rMap.containsKey(key)){
				bonusMap = rMap.get(key);
				bonus =r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
				keepAmt = r.getCell("keepAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepAmt").getValue());
				adjustAmt = r.getCell("adjustAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
				realAmt = r.getCell("realBonusAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("realBonusAmt").getValue());
				xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
				
				if(bonusMap.get("bonus") != null){
					bonusMap.put("bonus", bonus.add((BigDecimal)bonusMap.get("bonus")));
				}
				if(bonusMap.get("keepAmt") != null){
					bonusMap.put("keepAmt", keepAmt.add((BigDecimal)bonusMap.get("keepAmt")));
				}
				if(bonusMap.get("adjustAmt") != null){
					bonusMap.put("adjustAmt", adjustAmt.add((BigDecimal)bonusMap.get("adjustAmt")));
				}
				if(bonusMap.get("realAmt") != null){
					bonusMap.put("realAmt", realAmt.add((BigDecimal)bonusMap.get("realAmt")));
				}
				if(bonusMap.get("xz") != null){
					bonusMap.put("xz", xz.add((BigDecimal)bonusMap.get("xz")));
				}
			}else{
				bonusMap = new HashMap<String, Object>();
				bonus =r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
				keepAmt = r.getCell("keepAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepAmt").getValue());
				adjustAmt = r.getCell("adjustAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
				realAmt = r.getCell("realBonusAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("realBonusAmt").getValue());
				xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
				
				
				bonusMap.put("bonus", bonus);
				bonusMap.put("keepAmt", keepAmt);
				bonusMap.put("adjustAmt", adjustAmt);
				bonusMap.put("realAmt", realAmt);
				bonusMap.put("xz", xz);
				bonusMap.put("position", r.getCell("position").getValue());
			}
			
			rMap.put(key, bonusMap);
		}
		
		
		rc = this.tblRec.getRowCount();
		for(int i =0;i<rc;i++){
			r =tblRec.getRow(i);
			
		    key =r.getCell("person").getValue().toString();
		    Map<String,Object> bonusMap = null;
			if(rMap.containsKey(key)){
				bonusMap = rMap.get(key);
				bonus =r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
				keepAmt = r.getCell("keepAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepAmt").getValue());
				adjustAmt = r.getCell("adjustAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
				realAmt = r.getCell("realBonusAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("realBonusAmt").getValue());
				xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
				
				if(bonusMap.get("bonus") != null){
					bonusMap.put("bonus", bonus.add((BigDecimal)bonusMap.get("bonus")));
				}
				if(bonusMap.get("keepAmt") != null){
					bonusMap.put("keepAmt", keepAmt.add((BigDecimal)bonusMap.get("keepAmt")));
				}
				if(bonusMap.get("adjustAmt") != null){
					bonusMap.put("adjustAmt", adjustAmt.add((BigDecimal)bonusMap.get("adjustAmt")));
				}
				if(bonusMap.get("realAmt") != null){
					bonusMap.put("realAmt", realAmt.add((BigDecimal)bonusMap.get("realAmt")));
				}
				if(bonusMap.get("xz") != null){
					bonusMap.put("xz", xz.add((BigDecimal)bonusMap.get("xz")));
				}
			}else{
				bonusMap = new HashMap<String, Object>();
				bonus =r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
				keepAmt = r.getCell("keepAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepAmt").getValue());
				adjustAmt = r.getCell("adjustAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
				realAmt = r.getCell("realBonusAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("realBonusAmt").getValue());
				xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
				
				
				bonusMap.put("bonus", bonus);
				bonusMap.put("keepAmt", keepAmt);
				bonusMap.put("adjustAmt", adjustAmt);
				bonusMap.put("realAmt", realAmt);
				bonusMap.put("xz", xz);
				bonusMap.put("position", r.getCell("position").getValue());
			}
			
			rMap.put(key, bonusMap);
		}
		return rMap;
	}

	protected void tblMgrBonus_editStopped(KDTEditEvent e) throws Exception {
	    IRow r = this.tblMgrBonus.getRow(e.getRowIndex());
		int colIndex = e.getColIndex();
		PersonInfo person=(PersonInfo) tblMgrBonus.getRow(e.getRowIndex()).getCell("person").getValue();
		if(colIndex != this.tblMgrBonus.getColumnIndex("person")&&person==null){
			FDCMsgBox.showWarning(this,"请先选择人员！");
			tblMgrBonus.getEditManager().editCellAt(e.getRowIndex(), 0);
			return;
		}
		if(colIndex == this.tblMgrBonus.getColumnIndex("person")){
			PersonInfo source=(PersonInfo) e.getOldValue();
			if(source!=null&&person==null){
				FDCMsgBox.showWarning(this,"人员不能为空！");
				tblMgrBonus.getRow(e.getRowIndex()).getCell("person").setValue(source);
				return;
			}
			for(int i=0;i<e.getRowIndex();i++){
        		PersonInfo p=(PersonInfo) tblMgrBonus.getRow(i).getCell("person").getValue();
        		if(person!=null&&p!=null&&person.getId().equals(p.getId())){
        			FDCMsgBox.showWarning(this,"人员已存在！");
        			tblMgrBonus.getRow(e.getRowIndex()).getCell("person").setValue(source);
    				return;
        		}
        	}
        	for(int i=e.getRowIndex()+1;i<tblMgrBonus.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblMgrBonus.getRow(i).getCell("person").getValue();
        		if(source==null&&p==null){
        			tblMgrBonus.getRow(i).getCell("person").setValue(person);
        		}
        		if(source!=null&&p!=null&&source.getId().equals(p.getId())){
        			tblMgrBonus.getRow(i).getCell("person").setValue(person);
        		}
        		if(source!=null&&p!=null&&!source.getId().equals(p.getId())){
        			break;
        		}
        	}
        	MonthCommissionEntryInfo entry=getMonthCommissionEntry(person.getName());
			BigDecimal purTarget=entry.getPurTarget();
			tblMgrBonus.getRow(e.getRowIndex()).getCell("purTarget").setValue(purTarget);
			BigDecimal purAmt = r.getCell("purAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purAmt").getValue());
			if(purTarget!=null&&purTarget.compareTo(FDCHelper.ZERO)!=0){
				r.getCell("purComplateRate").setValue(divide(purAmt,purTarget).multiply(FDCHelper.ONE_HUNDRED));
	        	for(int i=e.getRowIndex()+1;i<tblMgrBonus.getRowCount();i++){
	        		PersonInfo p=(PersonInfo) tblMgrBonus.getRow(i).getCell("person").getValue();
	        		if(person.getId().equals(p.getId())){
	        			tblMgrBonus.getRow(i).getCell("purTarget").setValue(purTarget);
	        			tblMgrBonus.getRow(i).getCell("purComplateRate").setValue(divide(purAmt,purTarget).multiply(FDCHelper.ONE_HUNDRED));
	        		}else{
	        			break;
	        		}
	        	}
        	}
			
			BigDecimal contractAmtTarget =entry.getSignTarget();
			tblMgrBonus.getRow(e.getRowIndex()).getCell("contractAmtTarget").setValue(contractAmtTarget);
        	BigDecimal contractAmt = r.getCell("contractAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmt").getValue());
        	if(contractAmtTarget!=null&&contractAmtTarget.compareTo(FDCHelper.ZERO)!=0){
        		r.getCell("contractCompleteRate").setValue(divide(contractAmt,contractAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
            	for(int i=e.getRowIndex()+1;i<tblMgrBonus.getRowCount();i++){
            		PersonInfo p=(PersonInfo) tblMgrBonus.getRow(i).getCell("person").getValue();
            		if(person.getId().equals(p.getId())){
            			tblMgrBonus.getRow(i).getCell("contractAmtTarget").setValue(contractAmtTarget);
            			tblMgrBonus.getRow(i).getCell("contractCompleteRate").setValue(divide(contractAmt,contractAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
            		}else{
            			break;
            		}
            	}
        	}
        	BigDecimal backAmtTarget = entry.getBackTarget();
        	tblMgrBonus.getRow(e.getRowIndex()).getCell("backAmtTarget").setValue(backAmtTarget);
        	BigDecimal backAmt = r.getCell("backAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmt").getValue());
        	if(backAmtTarget!=null&&backAmtTarget.compareTo(FDCHelper.ZERO)!=0){
        		r.getCell("backCompleteRate").setValue(divide(backAmt,backAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
            	for(int i=e.getRowIndex()+1;i<tblMgrBonus.getRowCount();i++){
            		PersonInfo p=(PersonInfo) tblMgrBonus.getRow(i).getCell("person").getValue();
            		if(person.getId().equals(p.getId())){
            			tblMgrBonus.getRow(i).getCell("backAmtTarget").setValue(backAmtTarget);
            			tblMgrBonus.getRow(i).getCell("backCompleteRate").setValue(divide(backAmt,backAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
            		}else{
            			break;
            		}
            	}
        	}
        }
		if(colIndex == this.tblMgrBonus.getColumnIndex("purTarget")){
        	BigDecimal purTarget = r.getCell("purTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purTarget").getValue());
        	BigDecimal purAmt = r.getCell("purAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("purAmt").getValue());
        	if(purTarget==null||purTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("purComplateRate").setValue(divide(purAmt,purTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblMgrBonus.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblMgrBonus.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblMgrBonus.getRow(i).getCell("purTarget").setValue(purTarget);
        			tblMgrBonus.getRow(i).getCell("purComplateRate").setValue(divide(purAmt,purTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if(colIndex == this.tblMgrBonus.getColumnIndex("contractAmtTarget")){
        	BigDecimal contractAmtTarget = r.getCell("contractAmtTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmtTarget").getValue());
        	BigDecimal contractAmt = r.getCell("contractAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contractAmt").getValue());
        	if(contractAmtTarget==null||contractAmtTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("contractCompleteRate").setValue(divide(contractAmt,contractAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblMgrBonus.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblMgrBonus.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblMgrBonus.getRow(i).getCell("contractAmtTarget").setValue(contractAmtTarget);
        			tblMgrBonus.getRow(i).getCell("contractCompleteRate").setValue(divide(contractAmt,contractAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblMgrBonus.getColumnIndex("backAmtTarget")){
        	BigDecimal backAmtTarget = r.getCell("backAmtTarget").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmtTarget").getValue());
        	BigDecimal backAmt = r.getCell("backAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("backAmt").getValue());
        	if(backAmtTarget==null||backAmtTarget.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("backCompleteRate").setValue(divide(backAmt,backAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        	for(int i=e.getRowIndex()+1;i<tblMgrBonus.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblMgrBonus.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblMgrBonus.getRow(i).getCell("backAmtTarget").setValue(backAmtTarget);
        			tblMgrBonus.getRow(i).getCell("backCompleteRate").setValue(divide(backAmt,backAmtTarget).multiply(FDCHelper.ONE_HUNDRED));
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblMgrBonus.getColumnIndex("zbRate")){
        	BigDecimal zbRate = r.getCell("zbRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("zbRate").getValue());
        	for(int i=e.getRowIndex()+1;i<tblMgrBonus.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblMgrBonus.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblMgrBonus.getRow(i).getCell("zbRate").setValue(zbRate);
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblMgrBonus.getColumnIndex("position")){
        	String position = (String) r.getCell("position").getValue();
        	for(int i=e.getRowIndex()+1;i<tblMgrBonus.getRowCount();i++){
        		PersonInfo p=(PersonInfo) tblMgrBonus.getRow(i).getCell("person").getValue();
        		if(person.getId().equals(p.getId())){
        			tblMgrBonus.getRow(i).getCell("position").setValue(position);
        		}else{
        			break;
        		}
        	}
        }
        if( colIndex == this.tblMgrBonus.getColumnIndex("calcBonusRate")||colIndex == this.tblMgrBonus.getColumnIndex("calcTBonusRate")
        		||colIndex == this.tblMgrBonus.getColumnIndex("xz")
        		||colIndex == this.tblMgrBonus.getColumnIndex("keepRate")){
        	BigDecimal calcBonus = r.getCell("calcBonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcBonus").getValue());
        	BigDecimal calcBonusRate = r.getCell("calcBonusRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcBonusRate").getValue());
        	BigDecimal calcTBonus = r.getCell("calcTBonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcTBonus").getValue());
        	BigDecimal calcTBonusRate = r.getCell("calcTBonusRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("calcTBonusRate").getValue());
        	r.getCell("bonus").setValue(divide(FDCHelper.multiply(calcBonus,calcBonusRate),FDCHelper.ONE_HUNDRED).add(FDCHelper.multiply(calcTBonus,calcTBonusRate)));
        	
        	BigDecimal bonus = r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
        	
        	BigDecimal xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
        	BigDecimal keepRate = r.getCell("keepRate").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepRate").getValue());
        	r.getCell("keepAmt").setValue(divide(FDCHelper.multiply(bonus.add(xz),keepRate),FDCHelper.ONE_HUNDRED));
        	
        	
        	calcRealBonusAmt(r);
        }
        if( colIndex == this.tblMgrBonus.getColumnIndex("keepAmt")||colIndex == this.tblMgrBonus.getColumnIndex("adjustAmt")){
        	calcRealBonusAmt(r);
        }
        if( colIndex == this.tblMgrBonus.getColumnIndex("mpur")){
        	BigDecimal mpur = r.getCell("mpur").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("mpur").getValue());
        	BigDecimal pur = r.getCell("pur").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("pur").getValue());
        	if(mpur==null||mpur.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("purRate").setValue(divide(pur,mpur).multiply(FDCHelper.ONE_HUNDRED));
        }
        if( colIndex == this.tblMgrBonus.getColumnIndex("mcontract")){
        	BigDecimal mcontract =r.getCell("mcontract").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("mcontract").getValue());
        	BigDecimal contract = r.getCell("contract").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("contract").getValue());
        	if(mcontract==null||mcontract.compareTo(FDCHelper.ZERO)==0){
        		return;
        	}
        	r.getCell("contractRate").setValue(divide(contract,mcontract).multiply(FDCHelper.ONE_HUNDRED));
        }
        refreshTotalData();
        refreshBonusTotal();
	}
	
	private void  calcRealBonusAmt(IRow r){
	    BigDecimal bonus = r.getCell("bonus").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("bonus").getValue());
	    BigDecimal xz = r.getCell("xz").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("xz").getValue());
	    BigDecimal keepAmt = r.getCell("keepAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("keepAmt").getValue());
	    BigDecimal adjustAmt = r.getCell("adjustAmt").getValue() == null?FDCHelper.ZERO:FDCHelper.toBigDecimal(r.getCell("adjustAmt").getValue());
	    r.getCell("realBonusAmt").setValue(FDCHelper.subtract(bonus.add(xz), keepAmt).add(adjustAmt));
	}
	
	protected void toBaseTransaction(String sellProjectId,String pid,String qd,String rec) throws BOSException, SQLException{
		
	    int year = Integer.valueOf(this.kDSpinnerYear.getValue()+"");
	    int month = Integer.valueOf(this.kDSpinnerMonth.getValue()+"");
	    
		Date t=FDCDateHelper.stringToDate(year+"-"+month+"-01");
	    Date startDate = FDCDateHelper.getFirstDayOfMonth(t);
    	Date endDate =  FDCDateHelper.getLastDayOfMonth(t);
    	
		StringBuilder sql = new StringBuilder();
		sql.append("\n   /*dialect*/ select sign.fid id from t_she_signManage sign                                                                                        ");
		 sql.append("\n       left outer join T_PM_User u  on u.fid = sign.FSalesmanID                                                         ");
	        sql.append("\n       left outer join t_bd_person p  on p.fid = u.FpersonID                                                         ");
		sql.append("\n   where sign.fbizState in ('ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit') ");
	       sql.append("\n     and NOT EXISTS                                                                                                   ");
	       sql.append("\n   (select tt.fnewId                                                                                                  ");
	       sql.append("\n            from t_she_changeManage tt                                                                                ");
	       sql.append("\n           where tt.fstate in ('2SUBMITTED', '3AUDITTING')                                                  ");
	       sql.append("\n             and sign.fid = tt.fnewId)                                                                                ");
	       sql.append("         and  sign.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
	       sql.append("        and  sign.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
	       sql.append("\n     and sign.fsellprojectid  ='"+sellProjectId+"'                                                                       ");
	       if(pid!=null){
	    	   sql.append("\n     and p.fid  ='"+pid+"'");
	       }
	       if(qd!=null){
	    	   sql.append("\n     and sign.FQdPerson  ='"+qd+"'");
	       }
	       if(rec!=null){
	    	   sql.append("\n     and sign.CFRecommended  ='"+rec+"'");
	       }
	       
//	       sql.append("\n   union select                                                                                                            ");
//	       sql.append("\n         sign.fid id ");
//	       sql.append("\n    from t_she_changeManage change                                                                                        ");
//	       sql.append("\n       left outer join t_she_signManage sign  on sign.fid = change.fsrcId                                                          ");
//	       sql.append("\n       left outer join T_PM_User u  on u.fid = sign.FSalesmanID     ");
//	       sql.append("\n       left outer join t_bd_person p  on p.fid = u.FpersonID                                                         ");
//	       sql.append("\n   where change.fstate in ('4AUDITTED') and change.FBizType='quitRoom'    ");
//	       sql.append("         and  change.fchangeDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//	       sql.append("        and  change.fchangeDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//	       sql.append("\n     and change.fsellprojectid  ='"+sellProjectId+"'                                                                       ");
//	       if(pid!=null){
//	    	   sql.append("\n     and p.fid  ='"+pid+"'");
//	       }
//	       if(qd!=null){
//	    	   sql.append("\n     and sign.FQdPerson  ='"+qd+"'");
//	       }
//	       if(rec!=null){
//	    	   sql.append("\n     and sign.CFRecommended  ='"+rec+"'");
//	       }
	       
		   	FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(sql.toString());
			final IRowSet rowSet = _builder.executeQuery();
			Set id=new HashSet();
			while(rowSet.next()) {
				id.add(rowSet.getString("id"));
			}
			if(id.size()==0)return;
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.INCLUDE));

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("filter", filter);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(SignManageListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
protected void toPur(String sellProjectId,String productTypeName,String pid,String qd,String rec) throws BOSException, SQLException{
		
	    int year = Integer.valueOf(this.kDSpinnerYear.getValue()+"");
	    int month = Integer.valueOf(this.kDSpinnerMonth.getValue()+"");
	    
	    Date t=FDCDateHelper.stringToDate(year+"-"+month+"-01");
	    Date startDate = FDCDateHelper.getFirstDayOfMonth(t);
    	Date endDate =  FDCDateHelper.getLastDayOfMonth(t);
    	
		StringBuilder sql = new StringBuilder();
		sql.append(" /*dialect*/ select pur.fid id from t_she_purchaseManage pur     ");
		sql.append("\n       left join T_SHE_PurSaleManEntry pe  on pe.fheadid = pur.fid                                                         ");
	     sql.append("\n       left join T_PM_User u  on u.fid = pe.fuserid                                                         ");
	        sql.append("\n       left outer join t_bd_person p  on p.fid = u.FpersonID                                                         ");
		
		sql.append(" \n       left outer join t_she_room r  on r.fid = pur.froomid                               ");
	 	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
	 	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
	 	sql.append("\n   where pur.fbizState in ('PurAudit','ToSign') ");
	 	sql.append(" \n       and pur.fsellprojectid  ='"+sellProjectId+"'" );
	 	if(productTypeName!=null){
	 		sql.append(" \n       and pt.fname_l2  ='"+productTypeName+"'" );
	 	}
	 	sql.append("         and  pur.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
	    sql.append("        and  pur.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
	    if(pid!=null){
	    	   sql.append("\n     and p.fid  ='"+pid+"'");
	       }
	    if(qd!=null){
	    	   sql.append("\n     and pur.FQdPerson  ='"+qd+"'");
	       }
	       if(rec!=null){
	    	   sql.append("\n     and pur.CFRecommended  ='"+rec+"'");
	       }
		   	FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(sql.toString());
			final IRowSet rowSet = _builder.executeQuery();
			Set id=new HashSet();
			while(rowSet.next()) {
				id.add(rowSet.getString("id"));
			}
			if(id.size()==0)return;
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.INCLUDE));

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("filter", filter);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(PurchaseManageListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
protected void toContract(String sellProjectId,String productTypeId,String pid,String qd,String rec) throws BOSException, SQLException{
	
    int year = Integer.valueOf(this.kDSpinnerYear.getValue()+"");
    int month = Integer.valueOf(this.kDSpinnerMonth.getValue()+"");
    
    Date t=FDCDateHelper.stringToDate(year+"-"+month+"-01");
    Date startDate = FDCDateHelper.getFirstDayOfMonth(t);
	Date endDate =  FDCDateHelper.getLastDayOfMonth(t);
	
	StringBuilder sql = new StringBuilder();
	sql.append("\n   /*dialect*/ select sign.fid id from t_she_signManage sign                                                                                        ");
	 sql.append("\n       left outer join T_PM_User u  on u.fid = sign.FSalesmanID                                                         ");
     sql.append("\n       left outer join t_bd_person p  on p.fid = u.FpersonID                                                         ");
	sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
    sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
    sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
    sql.append("\n   where sign.fbizState in ('SignAudit') ");
    sql.append("         and  sign.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
    sql.append("        and  sign.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
    sql.append("\n     and sign.fsellprojectid  ='"+sellProjectId+"'                                                                       ");
    sql.append("\n     and pt.fname_l2  ='"+productTypeId+"'                                                                ");
    if(pid!=null){
 	   sql.append("\n     and p.fid  ='"+pid+"'");
    }
    if(qd!=null){
 	   sql.append("\n     and sign.FQdPerson  ='"+qd+"'");
    }
    if(rec!=null){
 	   sql.append("\n     and sign.CFRecommended  ='"+rec+"'");
    }
    
    sql.append("\n   union select sign.fid from t_she_changeManage change                                                                                        ");
     sql.append("\n       left outer join t_she_signManage sign  on sign.fid = change.fsrcId                                                          ");
     sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
     sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
     sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
     sql.append("\n   where change.fstate in ('4AUDITTED') and change.FBizType='quitRoom'    ");
     sql.append("         and  change.fchangeDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     sql.append("        and  change.fchangeDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     sql.append("\n     and change.fsellprojectid  ='"+sellProjectId+"'                                                                       ");
     sql.append("\n     and pt.fname_l2  ='"+productTypeId+"'                                                                ");
     if(pid!=null){
  	   sql.append("\n     and p.fid  ='"+pid+"'");
     }
     if(qd!=null){
  	   sql.append("\n     and sign.FQdPerson  ='"+qd+"'");
     }
     if(rec!=null){
  	   sql.append("\n     and sign.CFRecommended  ='"+rec+"'");
     }
	   	FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		final IRowSet rowSet = _builder.executeQuery();
		Set id=new HashSet();
		while(rowSet.next()) {
			id.add(rowSet.getString("id"));
		}
		if(id.size()==0)return;
	FilterInfo filter=new FilterInfo();
	filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.INCLUDE));

	UIContext uiContext = new UIContext(this);
	uiContext.put(UIContext.OWNER, this);
	uiContext.put("filter", filter);
	uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(SignManageListUI.class.getName(), uiContext, null, OprtState.VIEW);
	uiWindow.show();
}
	protected void toSheRevBill(String sellProjectId,String productTypeName,String pid,String qd,String rec) throws BOSException, SQLException{
		int year = Integer.valueOf(this.kDSpinnerYear.getValue()+"");
	    int month = Integer.valueOf(this.kDSpinnerMonth.getValue()+"");
	    
	    Date t=FDCDateHelper.stringToDate(year+"-"+month+"-01");
	    Date startDate = FDCDateHelper.getFirstDayOfMonth(t);
    	Date endDate =  FDCDateHelper.getLastDayOfMonth(t);
    	
    	String cs=String.valueOf(year)+String.valueOf(month);
		StringBuilder sql = new StringBuilder();
		
		sql.append(" \n /*dialect*/ select entry.fid id                                              ");    
    	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
    	if(pid!=null){
    		sql.append("\n       left join (select max(p.fid) pid,sign.ftransactionid tranId from t_she_signManage sign                                                      ");
         	sql.append("\n       left join T_PM_User u  on u.fid = sign.FSalesmanID                                                         ");
            sql.append("\n       left join t_bd_person p  on p.fid = u.FpersonID group by sign.ftransactionid)t on t.tranId=revBill.frelateTransId                                                ");
    	}
    	if(qd!=null){
    		sql.append("\n       left join (select max(sign.FQdPerson) qd,sign.ftransactionid tranId from t_she_signManage sign                                                      ");
            sql.append("\n       group by sign.ftransactionid)t on t.tranId=revBill.frelateTransId                                                ");
    	}
    	if(rec!=null){
    		sql.append("\n       left join (select max(sign.CFRecommended) rec,sign.ftransactionid tranId from t_she_signManage sign                                                      ");
            sql.append("\n       group by sign.ftransactionid)t on t.tranId=revBill.frelateTransId                                                ");
    	}
       	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
    	sql.append(" \n       left outer join t_she_room r  on r.fid = revBill.froomid                               ");
    	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
     	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
    	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
    	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
    	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
    	sql.append(" \n       and md.fname_l2 !='面积补差款' ");
    	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
    	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
    	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('QRNullify','ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
    	if(FDCBillStateEnum.AUDITTED.equals(this.editData.getState())){
    		sql.append(" \n       and  entry.fisCS="+cs);
    	}else{
    		sql.append(" \n       and  entry.fisCS is null");
    	}
    	if(productTypeName!=null){
    		sql.append(" \n       and pt.fname_l2  ='"+productTypeName+"'" );
    	}
    	if(pid!=null){
    		sql.append(" \n       and t.pid  ='"+pid+"'" );
    	}
    	if(qd!=null){
	    	   sql.append("\n     and t.qd  ='"+qd+"'");
       }
       if(rec!=null){
    	   sql.append("\n     and t.rec  ='"+rec+"'");
       }
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		final IRowSet rowSet = _builder.executeQuery();
		Set id=new HashSet();
		while(rowSet.next()) {
			id.add(rowSet.getString("id"));
		}
		if(id.size()==0)return;
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("entrys.id",id,CompareType.INCLUDE));
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("filter", filter);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(PaymentReportUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
    }
	
	protected void tblMgrBonus_tableClicked(KDTMouseEvent e) throws Exception {
		int index = e.getColIndex();
		int clickNumber = e.getClickCount();
		if(clickNumber < 2){
			return;
		}
		if(tblMgrBonus.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue()==null||tblMgrBonus.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue() instanceof String
				||((BigDecimal)tblMgrBonus.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue()).compareTo(FDCHelper.ZERO)==0){
			return;
		}
		String pt=(String) tblMgrBonus.getRow(e.getRowIndex()).getCell("productType").getValue();
		if(pt==null) return;
		String orgId = editData.getSellProject().getId().toString();
		if(tblMgrBonus.getColumnIndex("contractAmt") == index){
			toBaseTransaction(orgId,null,null,null);
		}
		if(tblMgrBonus.getColumnIndex("backAmt") == index){
			toSheRevBill(orgId,null,null,null,null);
		}
		if(tblMgrBonus.getColumnIndex("calcBonus") == index){
			toSheRevBill(orgId,pt,null,null,null);
		}
		if(tblMgrBonus.getColumnIndex("pur") == index){
			toPur(orgId,pt,null,null,null);
		}
		if(tblMgrBonus.getColumnIndex("contract") == index){
			toContract(orgId, pt,null,null,null);
		}
		if(tblMgrBonus.getColumnIndex("purAmt") == index){
			toPur(orgId, null,null,null,null);
		}
	}
	protected void tblSalesmanBonus_tableClicked(KDTMouseEvent e)
			throws Exception {
		int index = e.getColIndex();
		int clickNumber = e.getClickCount();
		if(clickNumber < 2){
			return;
		}
		if(tblSalesmanBonus.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue()==null||tblSalesmanBonus.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue() instanceof String
				||((BigDecimal)tblSalesmanBonus.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue()).compareTo(FDCHelper.ZERO)==0){
			return;
		}
		String pt=(String) tblSalesmanBonus.getRow(e.getRowIndex()).getCell("productType").getValue();
		String pid=((PersonInfo) tblSalesmanBonus.getRow(e.getRowIndex()).getCell("person").getValue()).getId().toString();
		if(pt==null) return;
		String orgId = editData.getSellProject().getId().toString();
		if(tblSalesmanBonus.getColumnIndex("contractAmt") == index){
			toBaseTransaction(orgId,pid,null,null);
		}
		if(tblSalesmanBonus.getColumnIndex("backAmt") == index){
			toSheRevBill(orgId,null,pid,null,null);
		}
		if(tblSalesmanBonus.getColumnIndex("calcBonus") == index){
			toSheRevBill(orgId,pt,pid,null,null);
		}
		if(tblSalesmanBonus.getColumnIndex("pur") == index){
			toPur(orgId,pt,pid,null,null);
		}
		if(tblSalesmanBonus.getColumnIndex("contract") == index){
			toContract(orgId, pt,pid,null,null);
		}
		if(tblSalesmanBonus.getColumnIndex("purAmt") == index){
			toPur(orgId, null,pid,null,null);
		}
	}
	protected void tblQd_tableClicked(KDTMouseEvent e) throws Exception {
		int index = e.getColIndex();
		int clickNumber = e.getClickCount();
		if(clickNumber < 2){
			return;
		}
		if(tblQd.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue()==null||tblQd.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue() instanceof String
				||((BigDecimal)tblQd.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue()).compareTo(FDCHelper.ZERO)==0){
			return;
		}
		String pt=(String) tblQd.getRow(e.getRowIndex()).getCell("productType").getValue();
		String qd=(String) tblQd.getRow(e.getRowIndex()).getCell("person").getValue();
		if(pt==null) return;
		String orgId = editData.getSellProject().getId().toString();
		if(tblQd.getColumnIndex("contractAmt") == index){
			toBaseTransaction(orgId,null,qd,null);
		}
		if(tblQd.getColumnIndex("backAmt") == index){
			toSheRevBill(orgId,null,null,qd,null);
		}
		if(tblQd.getColumnIndex("calcBonus") == index){
			toSheRevBill(orgId,pt,null,qd,null);
		}
		if(tblQd.getColumnIndex("pur") == index){
			toPur(orgId,pt,null,qd,null);
		}
		if(tblQd.getColumnIndex("contract") == index){
			toContract(orgId, pt,null,qd,null);
		}
		if(tblQd.getColumnIndex("purAmt") == index){
			toPur(orgId, null,null,qd,null);
		}
	}
	protected void tblRec_tableClicked(KDTMouseEvent e) throws Exception {
		int index = e.getColIndex();
		int clickNumber = e.getClickCount();
		if(clickNumber < 2){
			return;
		}
		if(tblRec.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue()==null||tblRec.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue() instanceof String
				||((BigDecimal)tblRec.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue()).compareTo(FDCHelper.ZERO)==0){
			return;
		}
		String pt=(String) tblRec.getRow(e.getRowIndex()).getCell("productType").getValue();
		String rec=(String) tblRec.getRow(e.getRowIndex()).getCell("person").getValue();
		if(pt==null) return;
		String orgId = editData.getSellProject().getId().toString();
		if(tblRec.getColumnIndex("contractAmt") == index){
			toBaseTransaction(orgId,null,null,rec);
		}
		if(tblRec.getColumnIndex("backAmt") == index){
			toSheRevBill(orgId,null,null,null,rec);
		}
		if(tblRec.getColumnIndex("calcBonus") == index){
			toSheRevBill(orgId,pt,null,null,rec);
		}
		if(tblRec.getColumnIndex("pur") == index){
			toPur(orgId,pt,null,null,rec);
		}
		if(tblRec.getColumnIndex("contract") == index){
			toContract(orgId, pt,null,null,rec);
		}
		if(tblRec.getColumnIndex("purAmt") == index){
			toPur(orgId, null,null,null,rec);
		}
	}
	protected void kDSpinnerMonth_stateChanged(ChangeEvent e) throws Exception {
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

	protected void kDSpinnerYear_stateChanged(ChangeEvent e) throws Exception {
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
	

   

}