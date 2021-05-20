/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.BorderLayout;
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

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ClientHelper;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillFactory;
import com.kingdee.eas.fdc.sellhouse.MonthCommissionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.MonthCommissionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.MonthCommissionFactory;
import com.kingdee.eas.fdc.sellhouse.MonthCommissionInfo;
import com.kingdee.eas.fdc.sellhouse.MonthCommissionPTEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MonthCommissionEditUI extends AbstractMonthCommissionEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MonthCommissionEditUI.class);
    
    public MonthCommissionEditUI() throws Exception
    {
        super();
    }
    public void storeFields()
    {
    	this.txtName.setText(this.editData.getOrgUnit().getName()+"_"+editData.getSellProject().getName()+"_"+editData.getYear()+"年"+editData.getMonth()+"月_营销提成测算明细表");
    	this.editData.setName(this.editData.getOrgUnit().getName()+"_"+editData.getSellProject().getName()+"_"+editData.getYear()+"年"+editData.getMonth()+"月_营销提成测算明细表");
    	this.editData.getEntry().clear();
    	for(int i=0;i<this.entry.getRowCount();i++){
    		IRow row=this.entry.getRow(i);
    		MonthCommissionEntryInfo entry=(MonthCommissionEntryInfo) row.getUserObject();
    		entry.setPosition((String) row.getCell("position").getValue());
    		entry.setPerson((PersonInfo) row.getCell("person").getValue());
    		entry.setPurTarget((BigDecimal) row.getCell("purTarget").getValue());
    		entry.setSignTarget((BigDecimal) row.getCell("signTarget").getValue());
    		entry.setBackTarget((BigDecimal) row.getCell("backTarget").getValue());
    		entry.setPur((BigDecimal) row.getCell("pur").getValue());
    		entry.setSign((BigDecimal) row.getCell("sign").getValue());
    		entry.setBack((BigDecimal) row.getCell("back").getValue());
    		entry.setPurRate((BigDecimal) row.getCell("purRate").getValue());
    		entry.setSignRate((BigDecimal) row.getCell("signRate").getValue());
    		entry.setBackRate((BigDecimal) row.getCell("backRate").getValue());
    		entry.setRate((BigDecimal) row.getCell("rate").getValue());
    		entry.setSumTotal((BigDecimal) row.getCell("sumTotal").getValue());
    		entry.setAmountTotal((BigDecimal) row.getCell("amountTotal").getValue());
    		entry.setTotal((BigDecimal) row.getCell("total").getValue());
    		entry.setRemark((String) row.getCell("remark").getValue());
    		
    		entry.getPtEntry().clear();
    		for (Object it : pt.keySet()) {
    			MonthCommissionPTEntryInfo ptEntry=(MonthCommissionPTEntryInfo) row.getCell(it+"|yearRate").getUserObject();
    			ProductTypeInfo ptInfo=new ProductTypeInfo();
    			ptInfo.setId(BOSUuid.read(it.toString()));
    			if(ptEntry==null)ptEntry=new MonthCommissionPTEntryInfo();
    			ptEntry.setProductType(ptInfo);
    			ptEntry.setYearRate((BigDecimal) row.getCell(it+"|yearRate").getValue());
    			ptEntry.setMonthRate((BigDecimal) row.getCell(it+"|monthRate").getValue());
    			ptEntry.setSum1((BigDecimal) row.getCell(it+"|sum1").getValue());
    			ptEntry.setSum2((BigDecimal) row.getCell(it+"|sum2").getValue());
    			ptEntry.setSumRate1((BigDecimal) row.getCell(it+"|sumRate1").getValue());
    			ptEntry.setSumRate2((BigDecimal) row.getCell(it+"|sumRate2").getValue());
    			ptEntry.setAmount((BigDecimal) row.getCell(it+"|amount").getValue());
    			ptEntry.setAmountRate((BigDecimal) row.getCell(it+"|amountRate").getValue());
    			
    			entry.getPtEntry().add(ptEntry);
    		}
    		this.editData.getEntry().add(entry);
    	}
        super.storeFields();
    }
	protected void entry_editStopped(KDTEditEvent e) throws Exception {
		IRow r = this.entry.getRow(e.getRowIndex());
		if(e.getColIndex() == this.entry.getColumnIndex("purTarget")||e.getColIndex() == this.entry.getColumnIndex("pur")){
			BigDecimal pur=(BigDecimal) r.getCell("pur").getValue();
			BigDecimal purTarget=(BigDecimal) r.getCell("purTarget").getValue();
			r.getCell("purRate").setValue(pur==null||purTarget==null||pur.compareTo(FDCHelper.ZERO)==0||purTarget.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:pur.divide(purTarget, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
		}
		if(e.getColIndex() == this.entry.getColumnIndex("signTarget")||e.getColIndex() == this.entry.getColumnIndex("sign")){
			BigDecimal sign=(BigDecimal) r.getCell("sign").getValue();
			BigDecimal signTarget=(BigDecimal) r.getCell("signTarget").getValue();
			r.getCell("signRate").setValue(sign==null||signTarget==null||sign.compareTo(FDCHelper.ZERO)==0||signTarget.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:sign.divide(signTarget, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
		
//			BigDecimal signRate=(BigDecimal) r.getCell("signRate").getValue();
//			BigDecimal backRate=(BigDecimal) r.getCell("backRate").getValue();
//			r.getCell("rate").setValue((FDCHelper.add(signRate, backRate)).divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP));
		}
		if(e.getColIndex() == this.entry.getColumnIndex("backTarget")||e.getColIndex() == this.entry.getColumnIndex("back")){
			BigDecimal back=(BigDecimal) r.getCell("back").getValue();
			BigDecimal backTarget=(BigDecimal) r.getCell("backTarget").getValue();
			r.getCell("backRate").setValue(back==null||backTarget==null||back.compareTo(FDCHelper.ZERO)==0||backTarget.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:back.divide(backTarget, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
		
//			BigDecimal signRate=(BigDecimal) r.getCell("signRate").getValue();
//			BigDecimal backRate=(BigDecimal) r.getCell("backRate").getValue();
//			r.getCell("rate").setValue((FDCHelper.add(signRate, backRate)).divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP));
		}
		if(this.entry.getColumnKey(e.getColIndex()).indexOf("monthRate")>0||this.entry.getColumnKey(e.getColIndex()).indexOf("sum1")>0
				||this.entry.getColumnKey(e.getColIndex()).indexOf("sumRate")>0){
			
			BigDecimal sumTotal=FDCHelper.ZERO;
			for (Object it : pt.keySet()) {
				BigDecimal monthRate=(BigDecimal) r.getCell(it+"|monthRate").getValue()==null?FDCHelper.ZERO:(BigDecimal) r.getCell(it+"|monthRate").getValue();
				BigDecimal sum1=(BigDecimal) r.getCell(it+"|sum1").getValue()==null?FDCHelper.ZERO:(BigDecimal) r.getCell(it+"|sum1").getValue();
				BigDecimal sumRate1=(BigDecimal) r.getCell(it+"|sumRate1").getValue()==null?FDCHelper.ZERO:(BigDecimal) r.getCell(it+"|sumRate1").getValue();
				
				BigDecimal sum2=(BigDecimal) r.getCell(it+"|sum2").getValue()==null?FDCHelper.ZERO:(BigDecimal) r.getCell(it+"|sum2").getValue();
				BigDecimal sumRate2=(BigDecimal) r.getCell(it+"|sumRate2").getValue()==null?FDCHelper.ZERO:(BigDecimal) r.getCell(it+"|sumRate2").getValue();
				
				sumTotal=FDCHelper.add(sumTotal,FDCHelper.multiply(FDCHelper.multiply(sum2, sumRate2).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).add(FDCHelper.multiply(sum1, sumRate1).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP)), monthRate).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
			}
			r.getCell("sumTotal").setValue(sumTotal);
			
			BigDecimal amountTotal=(BigDecimal) r.getCell("amountTotal").getValue()==null?FDCHelper.ZERO:(BigDecimal) r.getCell("amountTotal").getValue();
			r.getCell("total").setValue(FDCHelper.add(amountTotal, sumTotal));
		}
		if(this.entry.getColumnKey(e.getColIndex()).indexOf("amount")>0
				||this.entry.getColumnKey(e.getColIndex()).indexOf("amountRate")>0){
			
			BigDecimal amountTotal=FDCHelper.ZERO;
			for (Object it : pt.keySet()) {
				BigDecimal amount=(BigDecimal) r.getCell(it+"|amount").getValue()==null?FDCHelper.ZERO:(BigDecimal) r.getCell(it+"|amount").getValue();
				BigDecimal amountRate=(BigDecimal) r.getCell(it+"|amountRate").getValue()==null?FDCHelper.ZERO:(BigDecimal) r.getCell(it+"|amountRate").getValue();
				
				amountTotal=FDCHelper.add(amountTotal, FDCHelper.multiply(amount, amountRate));
			}
			r.getCell("amountTotal").setValue(amountTotal);
			
			BigDecimal sumTotal=(BigDecimal) r.getCell("sumTotal").getValue()==null?FDCHelper.ZERO:(BigDecimal) r.getCell("sumTotal").getValue();
			r.getCell("total").setValue(FDCHelper.add(amountTotal, sumTotal));
		}
	}
	public Map pt=null;
	public void loadFields() {
		super.loadFields();
		entry.removeRows();
		entry.removeColumns();
		this.entry.checkParsed();
		IRow headRow=this.entry.addHeadRow();
		IRow headRowName=this.entry.addHeadRow();
		
		IColumn column=this.entry.addColumn();
		column.setRequired(true);
		column.setKey("position");
		headRow.getCell("position").setValue("岗位");
		headRowName.getCell("position").setValue("岗位");
		
		this.entry.getHeadMergeManager().mergeBlock(0, 0, 1, 0);
		
		column=this.entry.addColumn();
		column.setRequired(true);
		column.setKey("person");
		headRow.getCell("person").setValue("姓名");
		headRowName.getCell("person").setValue("姓名");
		
		this.entry.getHeadMergeManager().mergeBlock(0, 1, 1, 1);
		
    	KDBizPromptBox personSelector = new KDBizPromptBox();
    	FDCClientUtils.setPersonF7(personSelector, null, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
    	KDTDefaultCellEditor personEditor = new KDTDefaultCellEditor(personSelector);
    	column.setEditor(personEditor);
		
    	pt=new HashMap();
    	FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select distinct p.fid id,p.fname_l2 name from t_she_building b left join T_FDC_ProductType p on p.fid = b.fproducttypeid  where b.fsellProjectid='"+this.editData.getSellProject().getId().toString()+"'");
		try {
			IRowSet rs = sqlBuilder.executeQuery();
			while(rs.next()){
				pt.put(rs.getString("id"),rs.getString("name"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ObjectValueRender r=new ObjectValueRender(){
     		public String getText(Object obj) {
     			if(obj!=null){
     				return obj.toString()+"%";
     			}
     			return super.getText(obj);
     		}
     	};
		
     	KDFormattedTextField amount6 = new KDFormattedTextField();
     	amount6.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
     	amount6.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
     	amount6.setPrecision(6);
     	amount6.setNegatived(false);
		KDTDefaultCellEditor amountEditor6 = new KDTDefaultCellEditor(amount6);
		
		for (Object it : pt.keySet()) {
			column=this.entry.addColumn();
			column.setRequired(true);
			column.setEditor(amountEditor6);
			column.getStyleAttributes().setNumberFormat("###,###.000000");
			column.setRenderer(r);
			column.setKey(it+"|yearRate");
			headRow.getCell(it+"|yearRate").setValue("年度提成点数");
			headRowName.getCell(it+"|yearRate").setValue(pt.get(it));
		}
		
		this.entry.getHeadMergeManager().mergeBlock(0, 2, 0, 2+pt.size()-1);
		
		for (Object it : pt.keySet()) {
			column=this.entry.addColumn();
			column.setRequired(true);
			column.setEditor(amountEditor6);
			column.getStyleAttributes().setNumberFormat("###,###.000000");
			column.setRenderer(r);
			column.setKey(it+"|monthRate");
			headRow.getCell(it+"|monthRate").setValue("月度提成点数");
			headRowName.getCell(it+"|monthRate").setValue(pt.get(it));
		}
		this.entry.getHeadMergeManager().mergeBlock(0, 2+pt.size(), 0, 2+pt.size()*2-1);
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		column=this.entry.addColumn();
		column.setRequired(true);
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("###,###.00");
		column.setKey("purTarget");
		headRow.getCell("purTarget").setValue("销售情况");
		headRowName.getCell("purTarget").setValue("月度认购指标（元）");
		
		column=this.entry.addColumn();
		column.setRequired(true);
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("###,###.00");
		column.setKey("signTarget");
		headRow.getCell("signTarget").setValue("销售情况");
		headRowName.getCell("signTarget").setValue("月度合同指标（元）");
		
		column=this.entry.addColumn();
		column.setRequired(true);
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("###,###.00");
		column.setKey("backTarget");
		headRow.getCell("backTarget").setValue("销售情况");
		headRowName.getCell("backTarget").setValue("月度回款指标（元）");
		
		column=this.entry.addColumn();
		column.setRequired(true);
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("###,###.00");
		column.setKey("pur");
		headRow.getCell("pur").setValue("销售情况");
		headRowName.getCell("pur").setValue("完成认购额（元）");
		
		column=this.entry.addColumn();
		column.setRequired(true);
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("###,###.00");
		column.setKey("sign");
		headRow.getCell("sign").setValue("销售情况");
		headRowName.getCell("sign").setValue("完成合同额（元）");
		
		column=this.entry.addColumn();
		column.setRequired(true);
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("###,###.00");
		column.setKey("back");
		headRow.getCell("back").setValue("销售情况");
		headRowName.getCell("back").setValue("完成回款额（元）");
		
		column=this.entry.addColumn();
		column.getStyleAttributes().setLocked(true);
		column.setRequired(true);
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("###,###.00");
		column.setRenderer(r);
		column.setKey("purRate");
		headRow.getCell("purRate").setValue("销售情况");
		headRowName.getCell("purRate").setValue("认购指标完成率（%）");
		
		column=this.entry.addColumn();
		column.getStyleAttributes().setLocked(true);
		column.setRequired(true);
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("###,###.00");
		column.setRenderer(r);
		column.setKey("signRate");
		headRow.getCell("signRate").setValue("销售情况");
		headRowName.getCell("signRate").setValue("合同指标完成率（%）");
		
		column=this.entry.addColumn();
		column.getStyleAttributes().setLocked(true);
		column.setRequired(true);
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("###,###.00");
		column.setRenderer(r);
		column.setKey("backRate");
		headRow.getCell("backRate").setValue("销售情况");
		headRowName.getCell("backRate").setValue("回款指标完成率（%）");
		
		column=this.entry.addColumn();
//		column.getStyleAttributes().setLocked(true);
		column.setRequired(true);
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("###,###.00");
		column.setRenderer(r);
		column.setKey("rate");
		headRow.getCell("rate").setValue("销售情况");
		headRowName.getCell("rate").setValue("指标综合完成率（%）");
		
		this.entry.getHeadMergeManager().mergeBlock(0, 2+pt.size()*2, 0, 11+pt.size()*2);
		
		int i=0;
		for (Object it : pt.keySet()) {
			column=this.entry.addColumn();
			column.setRequired(true);
			column.setEditor(amountEditor);
			column.getStyleAttributes().setNumberFormat("###,###.00");
			column.setKey(it+"|sum1");
			headRow.getCell(it+"|sum1").setValue(pt.get(it));
			headRowName.getCell(it+"|sum1").setValue("提奖基数（元）");
			
			column=this.entry.addColumn();
			column.setRequired(true);
			column.setEditor(amountEditor);
			column.getStyleAttributes().setNumberFormat("###,###.00");
			column.setRenderer(r);
			column.setKey(it+"|sumRate1");
			headRow.getCell(it+"|sumRate1").setValue(pt.get(it));
			headRowName.getCell(it+"|sumRate1").setValue("提奖比例（%）");
			
			column=this.entry.addColumn();
			column.setRequired(true);
			column.setEditor(amountEditor);
			column.getStyleAttributes().setNumberFormat("###,###.00");
			column.setKey(it+"|sum2");
			headRow.getCell(it+"|sum2").setValue(pt.get(it));
			headRowName.getCell(it+"|sum2").setValue("提奖基数（元）");
			
			column=this.entry.addColumn();
			column.setRequired(true);
			column.setEditor(amountEditor);
			column.getStyleAttributes().setNumberFormat("###,###.00");
			column.setRenderer(r);
			column.setKey(it+"|sumRate2");
			headRow.getCell(it+"|sumRate2").setValue("提奖比例（%）");
			headRowName.getCell(it+"|sumRate2").setValue("提奖比例（%）");
			
			this.entry.getHeadMergeManager().mergeBlock(0, 12+pt.size()*2+i*4, 0, 12+pt.size()*2+(i+1)*4-1);
			i++;
		}
		i--;
		column=this.entry.addColumn();
		column.getStyleAttributes().setLocked(true);
		column.setRequired(true);
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("###,###.00");
		column.setKey("sumTotal");
		headRow.getCell("sumTotal").setValue("个人提成合计（按金额）（元）");
		headRowName.getCell("sumTotal").setValue("个人提成合计（按金额）（元）");
		
		this.entry.getHeadMergeManager().mergeBlock(0, 12+pt.size()*2+(i+1)*4, 1, 12+pt.size()*2+(i+1)*4);
		
		int j=0;
		for (Object it : pt.keySet()) {
			column=this.entry.addColumn();
			column.setRequired(true);
			column.setEditor(amountEditor);
			column.getStyleAttributes().setNumberFormat("###,###");
			column.setKey(it+"|amount");
			headRow.getCell(it+"|amount").setValue(pt.get(it));
			headRowName.getCell(it+"|amount").setValue("产品（套/个）");
			
			column=this.entry.addColumn();
			column.setRequired(true);
			column.getStyleAttributes().setNumberFormat("###,###.00");
			column.setEditor(amountEditor);
			column.setKey(it+"|amountRate");
			headRow.getCell(it+"|amountRate").setValue(pt.get(it));
			headRowName.getCell(it+"|amountRate").setValue("提奖金额（元/个套）");
			
			this.entry.getHeadMergeManager().mergeBlock(0, 13+pt.size()*2+(i+1)*4+j*2, 0, 13+pt.size()*2+(i+1)*4+(j+1)*2-1);
			j++;
		}
		j--;
		column=this.entry.addColumn();
		column.getStyleAttributes().setLocked(true);
		column.setRequired(true);
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("###,###.00");
		column.setKey("amountTotal");
		headRow.getCell("amountTotal").setValue("个人提成合计（按数量）（元）");
		headRowName.getCell("amountTotal").setValue("个人提成合计（按数量）（元）");
		
		this.entry.getHeadMergeManager().mergeBlock(0, 13+pt.size()*2+(i+1)*4+(j+1)*2, 1, 13+pt.size()*2+(i+1)*4+(j+1)*2);
		
		column=this.entry.addColumn();
		column.getStyleAttributes().setLocked(true);
		column.setRequired(true);
		column.setEditor(amountEditor);
		column.getStyleAttributes().setNumberFormat("###,###.00");
		column.setKey("total");
		headRow.getCell("total").setValue("合计佣金（元）");
		headRowName.getCell("total").setValue("合计佣金（元）");
		
		this.entry.getHeadMergeManager().mergeBlock(0, 14+pt.size()*2+(i+1)*4+(j+1)*2, 1, 14+pt.size()*2+(i+1)*4+(j+1)*2);
		
		column=this.entry.addColumn();
		column.setKey("remark");
		headRow.getCell("remark").setValue("备注");
		headRowName.getCell("remark").setValue("备注");
		
		this.entry.getHeadMergeManager().mergeBlock(0, 15+pt.size()*2+(i+1)*4+(j+1)*2, 1, 15+pt.size()*2+(i+1)*4+(j+1)*2);
		
		MonthCommissionEntryCollection col=this.editData.getEntry();
		for(int k=0;k<col.size();k++){
			IRow row=this.entry.addRow();
			row.setUserObject(col.get(k));
			row.getCell("position").setValue(col.get(k).getPosition());
			row.getCell("person").setValue(col.get(k).getPerson());
			row.getCell("purTarget").setValue(col.get(k).getPurTarget());
			row.getCell("signTarget").setValue(col.get(k).getSignTarget());
			row.getCell("backTarget").setValue(col.get(k).getBackTarget());
			row.getCell("pur").setValue(col.get(k).getPur());
			row.getCell("sign").setValue(col.get(k).getSign());
			row.getCell("back").setValue(col.get(k).getBack());
			row.getCell("purRate").setValue(col.get(k).getPurRate());
			row.getCell("signRate").setValue(col.get(k).getSignRate());
			row.getCell("backRate").setValue(col.get(k).getBackRate());
			row.getCell("rate").setValue(col.get(k).getRate());
			row.getCell("sumTotal").setValue(col.get(k).getSumTotal());
			row.getCell("amountTotal").setValue(col.get(k).getAmountTotal());
			row.getCell("total").setValue(col.get(k).getTotal());
			row.getCell("remark").setValue(col.get(k).getRemark());
			
			for(int m=0;m<col.get(k).getPtEntry().size();m++){
				MonthCommissionPTEntryInfo pt=col.get(k).getPtEntry().get(m);
				row.getCell(pt.getProductType().getId().toString()+"|yearRate").setUserObject(pt);
				row.getCell(pt.getProductType().getId().toString()+"|yearRate").setValue(pt.getYearRate());
				row.getCell(pt.getProductType().getId().toString()+"|monthRate").setValue(pt.getMonthRate());
				row.getCell(pt.getProductType().getId().toString()+"|sum1").setValue(pt.getSum1());
				row.getCell(pt.getProductType().getId().toString()+"|sum2").setValue(pt.getSum2());
				row.getCell(pt.getProductType().getId().toString()+"|sumRate1").setValue(pt.getSumRate1());
				row.getCell(pt.getProductType().getId().toString()+"|sumRate2").setValue(pt.getSumRate2());
				row.getCell(pt.getProductType().getId().toString()+"|amount").setValue(pt.getAmount());
				row.getCell(pt.getProductType().getId().toString()+"|amountRate").setValue(pt.getAmountRate());
			}
		}
		ClientHelper.getFootRow(this.entry,  new String[]{"purTarget","signTarget","backTarget","pur","sign","back","sumTotal","amountTotal","total"});
	
		for (Object it : pt.keySet()) {
			ClientHelper.getFootRow(this.entry,  new String[]{it+"|sum1",it+"|sum2",it+"|amount"});
		}
	}
	public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add("entry.*");
    	sic.add("entry.person.*");
    	sic.add("entry.ptEntry.*");
    	sic.add("entry.ptEntry.productType.*");
    	return sic;
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
    	this.txtName.setEnabled(false);
	}
	
	protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return MonthCommissionFactory.getRemoteInstance();
	}
	
	protected KDTable getDetailTable() {
		return this.entry;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewDetailData(KDTable table) {
		return new MonthCommissionEntryInfo();
	}
	protected void loadLineFields(KDTable table, IRow row, IObjectValue obj) {
		row.setUserObject(obj);
		
		for (Object it : pt.keySet()) {
			MonthCommissionPTEntryInfo ptEntry=new MonthCommissionPTEntryInfo();
			row.getCell(it+"|yearRate").setUserObject(ptEntry);
		}
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("year",editData.getYear()));
		filter.getFilterItems().add(new FilterItemInfo("month",editData.getMonth()));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",editData.getSellProject().getId().toString()));
		if(editData.getId() != null){
			filter.getFilterItems().add(new FilterItemInfo("id",editData.getId().toString(),CompareType.NOTEQUALS));
		}
		
		boolean isExist = MonthCommissionFactory.getRemoteInstance().exists(filter);
		if(isExist){
			FDCMsgBox.showError("当月已经 生成过月度营销提成测算明细表，不能重复生成。");
    		abort();
		}
		super.verifyInput(e);
	}
	protected IObjectValue createNewData() {
		MonthCommissionInfo info = new MonthCommissionInfo();
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