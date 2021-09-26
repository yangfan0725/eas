/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.JTTypeEnum;
import com.kingdee.eas.fdc.contract.MarketMonthProjectCollection;
import com.kingdee.eas.fdc.contract.MarketMonthProjectFactory;
import com.kingdee.eas.fdc.contract.MarketYearProjectCollection;
import com.kingdee.eas.fdc.contract.MarketYearProjectFactory;
import com.kingdee.eas.fdc.contract.RecommendTypeInfo;
import com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillCollection;
import com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillEntryFactory;
import com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillEntryInfo;
import com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillFactory;
import com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillInfo;
import com.kingdee.eas.fdc.contract.app.MarketCostTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillFactory;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingCommissionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingCommissionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.AdvMsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ThirdPartyExpenseBillEditUI extends AbstractThirdPartyExpenseBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ThirdPartyExpenseBillEditUI.class);
    public ThirdPartyExpenseBillEditUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	
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
    	
    	this.tblMgrBonus.checkParsed();
    	KDBizPromptBox f7Box = new KDBizPromptBox(); 
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.contract.app.RecommendTypeQuery");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		
		FilterInfo	filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(filter);
		f7Box.setEntityViewInfo(view);
		
		this.tblMgrBonus.getColumn("recommendType").setEditor(f7Editor);
		
		f7Box = new KDBizPromptBox(); 
		f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$longNumber$;$name$");
		f7Box.setEditFormat("$longNumber$;$name$");
		f7Box.setCommitFormat("$longNumber$;$name$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		
		filter = new FilterInfo();
		filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("fullOrgUnit.id", this.editData.getOrgUnit().getId().toString()));
		filterItems.add(new FilterItemInfo("isMarket", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		view=new EntityViewInfo();
		view.setFilter(filter);
		f7Box.setEntityViewInfo(view);
		
		this.tblMgrBonus.getColumn("costAccount").setEditor(f7Editor);
		this.tblMgrBonus.getColumn("costAccount").setRequired(true);
		
		this.tblMgrBonus.getColumn("costAccount").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof CostAccountInfo){
					CostAccountInfo info = (CostAccountInfo)obj;
					return info.getLongNumber().replaceAll("!", ".")+";"+info.getName();
				}
				return super.getText(obj);
			}
		});
		
    	 ObjectValueRender r=new ObjectValueRender(){
     		public String getText(Object obj) {
     			if(obj!=null){
     				return obj.toString()+"%";
     			}
     			return super.getText(obj);
     		}
     	};
     	this.tblMgrBonus.getColumn("dsxs").setRenderer(r);
     	
     	KDComboBox combo = new KDComboBox();
        for(int i = 0; i < TransactionStateEnum.getEnumList().size(); i++){
        	combo.addItem(TransactionStateEnum.getEnumList().get(i));
        }
        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.tblMgrBonus.getColumn("bizType").setEditor(comboEditor);
		
		combo = new KDComboBox();
        for(int i = 0; i < JTTypeEnum.getEnumList().size(); i++){
        	combo.addItem(JTTypeEnum.getEnumList().get(i));
        }
        comboEditor = new KDTDefaultCellEditor(combo);
		this.tblMgrBonus.getColumn("jtType").setEditor(comboEditor);
         
		
		this.tblHand.checkParsed();
    	f7Box = new KDBizPromptBox(); 
		f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.contract.app.RecommendTypeQuery");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		
		filter = new FilterInfo();
		filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		
		view=new EntityViewInfo();
		view.setFilter(filter);
		f7Box.setEntityViewInfo(view);
		
		this.tblHand.getColumn("recommendType").setEditor(f7Editor);
		
		f7Box = new KDBizPromptBox(); 
		f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$longNumber$;$name$");
		f7Box.setEditFormat("$longNumber$;$name$");
		f7Box.setCommitFormat("$longNumber$;$name$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		
		filter = new FilterInfo();
		filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("fullOrgUnit.id", this.editData.getOrgUnit().getId().toString()));
		filterItems.add(new FilterItemInfo("isMarket", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		view=new EntityViewInfo();
		view.setFilter(filter);
		f7Box.setEntityViewInfo(view);
		
		this.tblHand.getColumn("costAccount").setEditor(f7Editor);
		this.tblHand.getColumn("costAccount").setRequired(true);
		
		this.tblHand.getColumn("costAccount").setRenderer(new ObjectValueRender(){
			public String getText(Object obj) {
				if(obj instanceof CostAccountInfo){
					CostAccountInfo info = (CostAccountInfo)obj;
					return info.getLongNumber().replaceAll("!", ".")+";"+info.getName();
				}
				return super.getText(obj);
			}
		});
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		this.tblHand.getColumn("purDate").setEditor(dateEditor);
		this.tblHand.getColumn("signDate").setEditor(dateEditor);
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setNegatived(true);
		amount.setPrecision(2);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		this.tblHand.getColumn("sqjl").setEditor(amountEditor);
		this.tblHand.getColumn("remark").setWidth(250);
    	this.contOrgUnit.setEnabled(false);
    	this.prmtOrgUnit.setEnabled(false);
    	
        this.prmtSellProject.setEnabled(false);
        this.pkBizDate.setRequired(true);
        
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionRemoveMgrBonus.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contManager.add(this.actionRemoveMgrBonus);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		KDWorkButton btnHandAddRowinfo = new KDWorkButton();
		KDWorkButton btnHandDeleteRowinfo = new KDWorkButton();

		this.actionAddHand.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnHandAddRowinfo = (KDWorkButton)contHand.add(this.actionAddHand);
		btnHandAddRowinfo.setText("新增行");
		btnHandAddRowinfo.setSize(new Dimension(140, 19));

		this.actionRemoveHand.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnHandDeleteRowinfo = (KDWorkButton) contHand.add(this.actionRemoveHand);
		btnHandDeleteRowinfo.setText("删除行");
		btnHandDeleteRowinfo.setSize(new Dimension(140, 19));
    }
    @Override
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add("month");
    	sic.add("state");
    	sic.add("isRespite");
    	return sic;
    }
    public void loadFields() {
    	this.tblTotal.checkParsed();
    	this.tblMgrBonus.checkParsed();
    	this.tblHand.checkParsed();
    	super.loadFields();
    	
    	this.tblTotal.removeRows();
    	Map rowMap=new HashMap();
    	for(int i=0;i<tblMgrBonus.getRowCount();i++){
    		RecommendTypeInfo rt=(RecommendTypeInfo) tblMgrBonus.getRow(i).getCell("recommendType").getValue();
    		CostAccountInfo ca=(CostAccountInfo) tblMgrBonus.getRow(i).getCell("costAccount").getValue();
    		BigDecimal sqjl=(BigDecimal) tblMgrBonus.getRow(i).getCell("sqjl").getValue();
    		if(rt!=null){
    			IRow r=null;
    			if(rowMap.containsKey(rt.getId().toString())){
    				r=(IRow) rowMap.get(rt.getId().toString());
    			}else{
    				r=this.tblTotal.addRow();
    				r.getCell("recommendType").setValue(rt.getName());
    				r.getCell("costAccount").setValue(ca.getName());
    				rowMap.put(rt.getId().toString(), r);
    			}
    			r.getCell("sqjl").setValue(FDCHelper.add(r.getCell("sqjl").getValue(),sqjl));
    		}
    		JTTypeEnum type=(JTTypeEnum) tblMgrBonus.getRow(i).getCell("jtType").getValue();
			if(type!=null){
				if(type.equals(JTTypeEnum.DS)){
					tblMgrBonus.getRow(i).getCell("tsxs").getStyleAttributes().setLocked(true);
					tblMgrBonus.getRow(i).getCell("dsxs").getStyleAttributes().setLocked(false);
				}else{
					tblMgrBonus.getRow(i).getCell("dsxs").getStyleAttributes().setLocked(true);
					tblMgrBonus.getRow(i).getCell("tsxs").getStyleAttributes().setLocked(false);
				}
			}
    	}
    	
    	for(int i=0;i<tblHand.getRowCount();i++){
    		RecommendTypeInfo rt=(RecommendTypeInfo) tblHand.getRow(i).getCell("recommendType").getValue();
    		CostAccountInfo ca=(CostAccountInfo) tblHand.getRow(i).getCell("costAccount").getValue();
    		BigDecimal sqjl=(BigDecimal) tblHand.getRow(i).getCell("sqjl").getValue();
    		if(rt!=null){
    			IRow r=null;
    			if(rowMap.containsKey(rt.getId().toString())){
    				r=(IRow) rowMap.get(rt.getId().toString());
    			}else{
    				r=this.tblTotal.addRow();
    				r.getCell("recommendType").setValue(rt.getName());
    				r.getCell("costAccount").setValue(ca.getName());
    				rowMap.put(rt.getId().toString(), r);
    			}
    			r.getCell("sqjl").setValue(FDCHelper.add(r.getCell("sqjl").getValue(),sqjl));
    		}
    	}
    	
        ClientHelper.getFootRow(tblMgrBonus, new String[]{"buildingArea","dealTotalAmount","tsxs","jl","xz","sqjl"});
        ClientHelper.getFootRow(tblHand, new String[]{"buildingArea","dealTotalAmount","sqjl"});
        ClientHelper.getFootRow(tblTotal, new String[]{"sqjl"});
    }
    
    public void storeFields()
    {
        super.storeFields();
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String bizDate=formatter.format(this.pkBizDate.getValue());
        
        OrgUnitInfo org = editData.getOrgUnit();
		editData.setName(org.getName()+"_"+editData.getSellProject().getName()+"_第三方费用申请单("+bizDate+")");
		this.txtName.setName(editData.getName());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String strMonth =sdf.format(this.pkBizDate.getValue());
		
		this.editData.setMonth(strMonth);
    }
    protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ThirdPartyExpenseBillFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	protected IObjectValue createNewData() {
		ThirdPartyExpenseBillInfo info = new ThirdPartyExpenseBillInfo();
		SysContext ctx = SysContext.getSysContext();
		info.setId(BOSUuid.create(info.getBOSType()));
		info.setCreator(ctx.getCurrentUserInfo());
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setOrgUnit(ctx.getCurrentOrgUnit().castToFullOrgUnitInfo());
		Map uiContext = (Map) getUIContext();
		SellProjectInfo sellProject = (SellProjectInfo) uiContext.get("sellProject");
		info.setSellProject(sellProject);		
		return info;
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//		String strMonth =sdf.format(this.pkBizDate.getValue());
//		
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("id",editData.getId().toString(),CompareType.NOTEQUALS));
//    	filter.getFilterItems().add(new FilterItemInfo("sellProject.id",editData.getSellProject().getId().toString()));
//    	filter.getFilterItems().add(new FilterItemInfo("month",strMonth));
//    	
//    	boolean isExist = ThirdPartyExpenseBillFactory.getRemoteInstance().exists(filter);
//    	
//    	if(isExist){
//    		FDCMsgBox.showError("当月已经 生成过第三方费用申请单，不能重复生成。");
//    		abort();
//    	}
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		super.actionSave_actionPerformed(e);
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		super.actionSubmit_actionPerformed(e);
	}
	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();
		if(tblMgrBonus.getRowCount()==0){
			FDCMsgBox.showWarning("申请明细不能为空！");
    		abort();
		}
		for(int i=0;i<tblMgrBonus.getRowCount();i++){
			for(int j=0;j<tblMgrBonus.getColumnCount();j++){
				if(tblMgrBonus.getColumn(j).isRequired()){
					if(tblMgrBonus.getColumn(j).getKey().equals("tsxs")||tblMgrBonus.getColumn(j).getKey().equals("dsxs")){
						JTTypeEnum type=(JTTypeEnum) tblMgrBonus.getRow(i).getCell("jtType").getValue();
						if(type!=null){
							if(type.equals(JTTypeEnum.DS)){
								if(tblMgrBonus.getRow(i).getCell("dsxs").getValue()==null){
									FDCMsgBox.showWarning("申请明细数据不能为空！");
									tblMgrBonus.getEditManager().editCellAt(i, j);
						    		abort();
								}
							}else{
								if(tblMgrBonus.getRow(i).getCell("tsxs").getValue()==null){
									FDCMsgBox.showWarning("申请明细数据不能为空！");
									tblMgrBonus.getEditManager().editCellAt(i, j);
						    		abort();
								}
							}
						}		
					}else{
						if(tblMgrBonus.getRow(i).getCell(j).getValue()==null){
							FDCMsgBox.showWarning("申请明细数据不能为空！");
							tblMgrBonus.getEditManager().editCellAt(i, j);
				    		abort();
						}
					}
				}
			}
		}
		for(int i=0;i<tblHand.getRowCount();i++){
			for(int j=0;j<tblHand.getColumnCount();j++){
				if(tblHand.getColumn(j).isRequired()){
					if(tblHand.getRow(i).getCell(j).getValue()==null){
						FDCMsgBox.showWarning("手工调整数据不能为空！");
						tblHand.getEditManager().editCellAt(i, j);
			    		abort();
					}
				}
			}
		}
		for(int i=0;i<tblMgrBonus.getRowCount();i++){
			String signManageId=tblMgrBonus.getRow(i).getCell("signManageId").getValue().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("signManageId", signManageId));
			filter.getFilterItems().add(new FilterItemInfo("head.id", this.editData.getId().toString(),CompareType.NOTEQUALS));
			if(ThirdPartyExpenseBillEntryFactory.getRemoteInstance().exists(filter))
			{
				FDCMsgBox.showWarning("第"+(i+1)+"行数据已经被提取,不能再次提取！");
	    		abort();
			}
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.pkBizDate.getSqlDate());
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		MarketMonthProjectCollection yearCol=MarketMonthProjectFactory.getRemoteInstance().getMarketMonthProjectCollection("select state,name from where year="+year+" and month="+month+" and orgUnit.id='"+this.editData.getOrgUnit().getId()+"'");
		if(yearCol.size()==0||!yearCol.get(0).getState().equals(FDCBillStateEnum.AUDITTED)){
			FDCMsgBox.showWarning(this,"营销月度预算未审批通过！");
			SysUtil.abort();
		}
		Map rowMap=new HashMap();
		for(int i=0;i<tblMgrBonus.getRowCount();i++){
    		CostAccountInfo ca=(CostAccountInfo) tblMgrBonus.getRow(i).getCell("costAccount").getValue();
    		BigDecimal sqjl=(BigDecimal) tblMgrBonus.getRow(i).getCell("sqjl").getValue();
    		if(ca!=null){
    			if(rowMap.containsKey(ca.getId().toString())){
    				rowMap.put(ca.getId().toString(), FDCHelper.add(rowMap.get(ca.getId().toString()),sqjl));
    			}else{
    				rowMap.put(ca.getId().toString(), sqjl);
    			}
    		}
    	}
    	
    	for(int i=0;i<tblHand.getRowCount();i++){
    		CostAccountInfo ca=(CostAccountInfo) tblHand.getRow(i).getCell("costAccount").getValue();
    		BigDecimal sqjl=(BigDecimal) tblHand.getRow(i).getCell("sqjl").getValue();
    		if(ca!=null){
    			if(rowMap.containsKey(ca.getId().toString())){
    				rowMap.put(ca.getId().toString(), FDCHelper.add(rowMap.get(ca.getId().toString()),sqjl));
    			}else{
    				rowMap.put(ca.getId().toString(), sqjl);
    			}
    		}
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String strMonth =sdf.format(this.pkBizDate.getValue());
		
    	ThirdPartyExpenseBillCollection tpCol=ThirdPartyExpenseBillFactory.getRemoteInstance().getThirdPartyExpenseBillCollection("select entry.*,entry.costAccount.*,handEntry.*,handEntry.costAccount.* from where month='"+strMonth+"' and orgUnit.id='"+editData.getOrgUnit().getId().toString()+"' and id!='"+editData.getId().toString()+"' and state!='1SAVED'");
    	if(tpCol.size()>0){
    		ThirdPartyExpenseBillInfo tpeInfo=tpCol.get(0);
    		for(int i=0;i<tpeInfo.getEntry().size();i++){
        		CostAccountInfo ca=tpeInfo.getEntry().get(i).getCostAccount();
        		BigDecimal sqjl=tpeInfo.getEntry().get(i).getSqjl();
        		if(ca!=null){
        			if(rowMap.containsKey(ca.getId().toString())){
        				rowMap.put(ca.getId().toString(), FDCHelper.add(rowMap.get(ca.getId().toString()),sqjl));
        			}
        		}
        	}
    		for(int i=0;i<tpeInfo.getHandEntry().size();i++){
        		CostAccountInfo ca=tpeInfo.getHandEntry().get(i).getCostAccount();
        		BigDecimal sqjl=tpeInfo.getHandEntry().get(i).getSqjl();
        		if(ca!=null){
        			if(rowMap.containsKey(ca.getId().toString())){
        				rowMap.put(ca.getId().toString(), FDCHelper.add(rowMap.get(ca.getId().toString()),sqjl));
        			}
        		}
        	}
    	}
    	this.editData.setIsRespite(false);
    	 Iterator<Entry<String, BigDecimal>> it = rowMap.entrySet().iterator();
         while(it.hasNext()){
              Entry<String, BigDecimal> entry = it.next();
              BigDecimal monthAmount=getMonthAmount(entry.getKey(),String.valueOf(year),String.valueOf(month));
              if(monthAmount==null){
            	  monthAmount=FDCHelper.ZERO;
  			}
  			if(entry.getValue().compareTo(monthAmount)>0){
//  				CostAccountInfo caInfo=CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(entry.getKey()));
//  				 FDCMsgBox.showWarning(this,"科目："+caInfo.getName()+" 超出月度预算！");
//  				 SysUtil.abort();
  				this.editData.setIsRespite(true);
  			}
        }
	}
	public BigDecimal getMonthAmount(String costAccountId,String year,String month) throws SQLException, BOSException{
		StringBuilder sql = new StringBuilder();
		sql.append(" select sum(entry.famount) amount from T_CON_MarketMonthProjectEntry entry left join T_CON_MarketMonthProject head on head.fid=entry.fheadid");
		sql.append(" where head.fstate='4AUDITTED' and entry.fcostaccountid='"+costAccountId+"' and head.fyear="+year+" and head.fmonth="+month);
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			return rowSet.getBigDecimal("amount");
		}
		return FDCHelper.ZERO;
	}
	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
	}
	public void actionCalcMgrBonus_actionPerformed(ActionEvent e)throws Exception {
		if(this.tblMgrBonus.getRowCount()>0&&e!=null){
			int result = FDCMsgBox.showConfirm2("是否需要重新进行计算?");
			if(AdvMsgBox.OK_OPTION != result){
				abort();
			}
		}
		this.tblMgrBonus.removeRows();
		this.tblTotal.removeRows();
		StringBuilder sql = new StringBuilder();
		sql.append(" /*dialect*/ select sign.fid signManageId,sign.fbizState bizType,r.fname_l2 room,sign.fcustomerNames customer,pur.FBusAdscriptionDate purDate, ");
		sql.append(" sign.FBusAdscriptionDate signDate,sign.FBulidingArea buildingArea,sign.FDealTotalAmount dealTotalAmount,saleMan.fname_l2 saleMan,sign.CFRecommended recommended,sign.fqdPerson qdPerson,sign.FSaleManNames nowSaleMan ");
		sql.append(" from t_she_signManage sign left join t_she_room r on sign.froomid=r.fid left join t_she_purchaseManage pur on sign.fsourcebillid=pur.fid ");
		sql.append(" left join t_pm_user saleMan on saleMan.fid=sign.FSalesmanID");
		sql.append(" where sign.CFRecommended is not null and sign.fsourceFunction is null and sign.fbizState='SignAudit' and sign.fsellProjectId='"+this.editData.getSellProject().getId().toString()+"' ");
		sql.append(" and NOT EXISTS (select t.fsignManageId from T_CON_TPartyExpenseBillEntry t where t.fheadid!='"+this.editData.getId().toString()+"' and t.fsignManageId=sign.fid )");
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(sql.toString());
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()) {
			IRow r=this.tblMgrBonus.addRow();
			r.getCell("signManageId").setValue(rowSet.getString("signManageId"));
			r.getCell("bizType").setValue(TransactionStateEnum.getEnum(rowSet.getString("bizType")));
			r.getCell("room").setValue(rowSet.getString("room"));
			r.getCell("customer").setValue(rowSet.getString("customer"));
			r.getCell("purDate").setValue(rowSet.getDate("purDate"));
			r.getCell("signDate").setValue(rowSet.getDate("signDate"));
			r.getCell("buildingArea").setValue(rowSet.getBigDecimal("buildingArea"));
			r.getCell("dealTotalAmount").setValue(rowSet.getBigDecimal("dealTotalAmount"));
			r.getCell("saleMan").setValue(rowSet.getString("saleMan"));
			r.getCell("recommended").setValue(rowSet.getString("recommended"));
			r.getCell("qdPerson").setValue(rowSet.getString("qdPerson"));
			r.getCell("nowSaleMan").setValue(rowSet.getString("nowSaleMan"));
		}
		
		ClientHelper.getFootRow(tblMgrBonus, new String[]{"buildingArea","dealTotalAmount","tsxs","jl","xz","sqjl"});
	}
	protected void tblHand_editStopped(KDTEditEvent e) throws Exception {
		IRow r = this.tblHand.getRow(e.getRowIndex());
		int colIndex = e.getColIndex();
		if(colIndex == this.tblHand.getColumnIndex("recommendType")){
			RecommendTypeInfo type=(RecommendTypeInfo) r.getCell("recommendType").getValue();
			if(type!=null){
				CostAccountInfo ca=CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(type.getCostAccount().getId()));
				CostAccountCollection co=CostAccountFactory.getRemoteInstance().getCostAccountCollection("select * from where fullOrgUnit.id='"+this.editData.getOrgUnit().getId().toString()+"' and longNumber='"+ca.getLongNumber()+"'");
				if(co.size()>0){
					r.getCell("costAccount").setValue(co.get(0));
				}
			}else{
				r.getCell("costAccount").setValue(null);
			}
		}
	}
	protected void tblMgrBonus_editStopped(KDTEditEvent e) throws Exception {
		IRow r = this.tblMgrBonus.getRow(e.getRowIndex());
		int colIndex = e.getColIndex();
		if(colIndex == this.tblMgrBonus.getColumnIndex("recommendType")){
			RecommendTypeInfo type=(RecommendTypeInfo) r.getCell("recommendType").getValue();
			if(type!=null){
				CostAccountInfo ca=CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(type.getCostAccount().getId()));
				CostAccountCollection co=CostAccountFactory.getRemoteInstance().getCostAccountCollection("select * from where fullOrgUnit.id='"+this.editData.getOrgUnit().getId().toString()+"' and longNumber='"+ca.getLongNumber()+"'");
				if(co.size()>0){
					r.getCell("costAccount").setValue(co.get(0));
				}
			}else{
				r.getCell("costAccount").setValue(null);
			}
		}
		if(colIndex == this.tblMgrBonus.getColumnIndex("jtType")){
			JTTypeEnum type=(JTTypeEnum) r.getCell("jtType").getValue();
			if(type!=null){
				if(type.equals(JTTypeEnum.DS)){
					r.getCell("tsxs").setValue(null);
					r.getCell("tsxs").getStyleAttributes().setLocked(true);
					r.getCell("dsxs").getStyleAttributes().setLocked(false);
				}else{
					r.getCell("dsxs").setValue(null);
					r.getCell("dsxs").getStyleAttributes().setLocked(true);
					r.getCell("tsxs").getStyleAttributes().setLocked(false);
				}
				r.getCell("jl").setValue(null);
				r.getCell("sqjl").setValue(FDCHelper.add(r.getCell("xz").getValue(), r.getCell("jl").getValue()));
			}
		}
		if(colIndex == this.tblMgrBonus.getColumnIndex("dsxs")){
			r.getCell("jl").setValue(FDCHelper.divide(FDCHelper.multiply(r.getCell("dealTotalAmount").getValue(), r.getCell("dsxs").getValue()), new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
			r.getCell("sqjl").setValue(FDCHelper.add(r.getCell("xz").getValue(), r.getCell("jl").getValue()));
		}
		if(colIndex == this.tblMgrBonus.getColumnIndex("tsxs")){
			r.getCell("jl").setValue(r.getCell("tsxs").getValue());
			r.getCell("sqjl").setValue(FDCHelper.add(r.getCell("xz").getValue(), r.getCell("jl").getValue()));
		}
		if(colIndex == this.tblMgrBonus.getColumnIndex("xz")){
			r.getCell("sqjl").setValue(FDCHelper.add(r.getCell("xz").getValue(), r.getCell("jl").getValue()));
		}
	}
	public void actionRemoveMgrBonus_actionPerformed(ActionEvent e)throws Exception {
		int activeRowIndex = tblMgrBonus.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("请先选择一行数据");
			abort();
		}
		tblMgrBonus.removeRow(activeRowIndex);
	}
	public void actionAddHand_actionPerformed(ActionEvent e) throws Exception {
		tblHand.addRow();
	}
	@Override
	public void actionRemoveHand_actionPerformed(ActionEvent e)
			throws Exception {
		int activeRowIndex = tblHand.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("请先选择一行数据");
			abort();
		}
		tblHand.removeRow(activeRowIndex);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionRemoveMgrBonus.setEnabled(false);
			this.actionAddHand.setEnabled(false);
			this.actionRemoveHand.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionRemoveMgrBonus.setEnabled(true);
			this.actionAddHand.setEnabled(true);
			this.actionRemoveHand.setEnabled(true);
		}
	}
}