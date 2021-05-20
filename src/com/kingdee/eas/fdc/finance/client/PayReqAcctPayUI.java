/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.PreChangeEvent;
import com.kingdee.bos.ctrl.swing.event.PreChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCRenderHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryCollection;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctFacadeFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryCollection;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.PayRequestAcctPay;
import com.kingdee.eas.fdc.finance.PayRequestAcctPayCollection;
import com.kingdee.eas.fdc.finance.PayRequestAcctPayFactory;
import com.kingdee.eas.fdc.finance.PayRequestAcctPayInfo;
import com.kingdee.eas.fi.arap.util.EntityViewHelper;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class PayReqAcctPayUI extends AbstractPayReqAcctPayUI
{
    private static final Logger logger = CoreUIObject.getLogger(PayReqAcctPayUI.class);
    
    /**
     * output class constructor
     */
    public PayReqAcctPayUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output prmtPeriod_dataChanged method
     */
    protected void prmtPeriod_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        super.prmtPeriod_dataChanged(e);
        if(e.getNewValue()!=null){
        	getUIContext().put("period", e.getNewValue());
        	EventListener[] listeners = prmtPeriod.getListeners(DataChangeListener.class);
        	for(int i=0;i<listeners.length;i++){
        		prmtPeriod.removeDataChangeListener((DataChangeListener)listeners[i]);
        	}
        	try{
        		fillTable();
        	}finally{
        		//���������뵥���������¶��ϴ��ڿ�Ŀ��������ʱfillTable��û��ִ�кϼ��м��㷽�����ڴ˵���
        		setUnionAll();
        		for(int i=0;i<listeners.length;i++){
        			prmtPeriod.addDataChangeListener((DataChangeListener)listeners[i]);
        		}
        	}
        }
    }

    public static void showPayReqAcctPayUI(CoreUI ui,PayRequestBillInfo editData,FDCBudgetPeriodInfo period,String optState) throws UIException{
		// ����UI������ʾ
    	UIContext context=new UIContext(ui);
    	
    	context.put("payReqId", editData.getId().toString());
    	context.put("period", period);
//    	context.put("editData", editData);
    	BigDecimal requestAmount = FDCHelper.toBigDecimal(editData.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP);
    	context.put("requestAmount", requestAmount);
    	context.put("basePeriod", FDCBudgetPeriodInfo.getPeriod(editData.getPayDate(), false));
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(com.kingdee.eas.fdc.finance.client.PayReqAcctPayUI.class.getName(), context, null,
						optState);
		uiWindow.show();
    }
    private PayRequestBillInfo getEditData(){
    	return (PayRequestBillInfo)getUIContext().get("editData");
    }
    private void storeRows(){
    	
    }
    private void verify(){
    	boolean select=false;
    	for(int i=0;i<table.getRowCount();i++){
    		IRow row=table.getRow(i);
    		boolean isSelect=row.getCell("isSelect").getValue()==Boolean.TRUE;
    		if(isSelect){
    			select=true;
    			if(row.getCell("amount").getValue()==null){
    				table.getSelectManager().select(i, 0,KDTSelectManager.ROW_SELECT);
    				FDCMsgBox.showWarning(this, "��ǰ��ѡ���˵�û��¼�������");
    	    		SysUtil.abort();
    			}
    		}
    	}
    	if(!select){
    		FDCMsgBox.showWarning(this, "û��ѡ����");
    		SysUtil.abort();
    	}else{
    		BigDecimal requestAmount=(BigDecimal)getUIContext().get("requestAmount");
    		BigDecimal tmpSum=FDCHelper.ZERO;
    		for(int i=0;i<table.getRowCount();i++){
    			tmpSum=tmpSum.add(FDCHelper.toBigDecimal(table.getCell(i, "amount").getValue()));
    		}
    		if(FDCHelper.subtract(requestAmount, tmpSum).signum()!=0){
    			FDCMsgBox.showWarning(this, "���������֮�Ͳ����ڸ������뵥���");
        		SysUtil.abort();
    		}
    	}
    	
    	for(int i=0;i<table.getRowCount();i++){
    		IRow row=table.getRow(i);
    		boolean isSelect=row.getCell("isSelect").getValue()==Boolean.TRUE;
    		if(isSelect){
    			if(FDCHelper.subtract(row.getCell("balanceAmt").getValue(), row.getCell("amount").getValue()).signum()<0){
    				table.getSelectManager().select(i, 0,KDTSelectManager.ROW_SELECT);
    				int v=FDCMsgBox.showConfirm2New(this, "��ǰ�б����������ڼƻ����,�Ƿ����?");
    				if(v==FDCMsgBox.NO){
    					SysUtil.abort();
    				}else{
    					break;
    				}
    			}
    		}
    	}
    }
    protected void btnOk_actionPerformed(ActionEvent e) throws Exception {
    	verify();
    	storeRows();
    	CoreBaseCollection acctPays=new CoreBaseCollection();
    	PayRequestBillInfo payReqInfo=new PayRequestBillInfo();
    	payReqInfo.setId(BOSUuid.read((String)getUIContext().get("payReqId")));
    	String contractId=(String)getUIContext().get("contractId");
    	String prjId=(String)getUIContext().get("prjId");
    	for(int i=0;i<table.getRowCount();i++){
    		IRow row=table.getRow(i);
    		boolean isSelect=row.getCell("isSelect").getValue()==Boolean.TRUE;
    		if(row.getUserObject() instanceof ContractCostSplitEntryInfo&&isSelect){
    			ContractCostSplitEntryInfo splitEntry=(ContractCostSplitEntryInfo)row.getUserObject();
    			PayRequestAcctPayInfo acctPay=new PayRequestAcctPayInfo();
    			acctPay.setAmount(FDCHelper.toBigDecimal(row.getCell("amount").getValue()));
    			acctPay.setLstAllAmount(FDCHelper.toBigDecimal(row.getCell("requestedAmt").getValue()));
    			acctPay.setCostAccount(splitEntry.getCostAccount());
    			acctPay.setPayRequestBill(payReqInfo);
    			acctPay.setPeriod((FDCBudgetPeriodInfo)prmtPeriod.getValue());
    			acctPay.setProjectId(prjId);
    			acctPay.setContractId(contractId);
    			acctPays.add(acctPay);
    		}
    	}
    	if(acctPays.size()>0){
    		getUIContext().put("acctPays", acctPays);
    		PayRequestAcctPayFactory.getRemoteInstance().save(acctPays);
    		FDCMsgBox.showInfo(this, "����ɹ�");
    		destroyWindow();
    	}else{
    		FDCMsgBox.showWarning(this, "�����ڷ��ϱ�����������");
    		SysUtil.abort();
    	}
    }
    protected void btnNo_actionPerformed(ActionEvent e) throws Exception {
    	super.btnNo_actionPerformed(e);
//    	String payReqId=(String)getUIContext().get("payReqId");
//    	if (payReqId != null) {
//			FDCSQLBuilder builder = new FDCSQLBuilder();
//			builder.appendSql("delete from t_fnc_payrequestacctpay where fpayrequestbillid=?");
//			builder.addParam(payReqId);
//			builder.execute();
//		}
    	this.destroyWindow();
    }
    
    protected void table_tableClicked(KDTMouseEvent e) throws Exception {
    	super.table_tableClicked(e);
		if(getOprtState()!=null&&getOprtState().equals(OprtState.VIEW)){
			return;
    	}
		
    	if(e.getType()==1&&e.getRowIndex()>-1&&e.getColIndex()>-1){
    		IRow row=table.getRow(e.getRowIndex());
    		if(e.getColIndex()==table.getColumnIndex("isSelect")){
				Boolean isSelect=(Boolean)row.getCell(e.getColIndex()).getValue();
				row.getCell(e.getColIndex()).setValue(Boolean.valueOf(isSelect==Boolean.FALSE));
				row.getCell("amount").getStyleAttributes().setLocked(isSelect.booleanValue());
				if(isSelect.booleanValue()){
					row.getCell("amount").getStyleAttributes().setBackground(Color.WHITE);
					row.getCell("amount").setValue(null);
				}else{
					row.getCell("amount").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
					row.getCell("amount").setValue(row.getCell("balanceAmt").getValue());
				}
				setUnionAll();
    		}
    		
    	}
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	initTable();
    	fillTable();
    	initCtrListener();
    	if(getOprtState()!=null&&getOprtState().equals(OprtState.VIEW)){
    		table.getStyleAttributes().setLocked(true);
    		btnOk.setEnabled(false);
    		btnNo.setEnabled(false);
    	}
    	prmtPeriod.setEditable(false);
    }
    private void initCtrListener()throws Exception{
    	EntityViewInfo view=prmtPeriod.getEntityViewInfo();
    	if(view==null){
    		view=new EntityViewInfo();
    	}
    	if(view.getFilter()==null){
    		view.setFilter(new FilterInfo());
    	}
    	FilterInfo filter=new FilterInfo();
    	filter.appendFilterItem("isYear", Boolean.FALSE);
    	view.getSorter().add(new SorterItemInfo("year"));
    	view.getSorter().add(new SorterItemInfo("month"));
    	view.getFilter().mergeFilter(filter, "and");
    	prmtPeriod.setEntityViewInfo(view);
    	prmtPeriod.addPreChangeListener(new PreChangeListener(){
    		public void preChange(PreChangeEvent e) {
    			FDCBudgetPeriodInfo newPeriod=(FDCBudgetPeriodInfo)e.getData();
    			FDCBudgetPeriodInfo basePeriod=(FDCBudgetPeriodInfo)getUIContext().get("basePeriod");
    			if(newPeriod.getYear()<basePeriod.getYear()||(newPeriod.getYear()==basePeriod.getYear()&&newPeriod.getMonth()<basePeriod.getMonth())){
    				FDCMsgBox.showWarning(table, "�·ݲ������ڸ�������");
    				e.setResult(PreChangeEvent.S_FALSE);
    			}
    		}
    	});
    	table.addKDTEditListener(new KDTEditAdapter(){
    		public void editStopped(KDTEditEvent e) {
    			if(e.getColIndex()==table.getColumnIndex("amount")){
        			setUnionAll();
        		}
    		}
    	});
    	table.setAfterAction(new BeforeActionListener(){
    		public void beforeAction(BeforeActionEvent e) {
				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					int activeColumnIndex = table.getSelectManager().getActiveColumnIndex();
					if(activeColumnIndex==table.getColumnIndex("amount")){
	        			setUnionAll();
	        		}
				}
    		}
    	});
    }
    private void fillTable() throws EASBizException, BOSException{
    	table.removeRows();
    	String payReqId=(String)getUIContext().get("payReqId");
    	FDCBudgetPeriodInfo period=(FDCBudgetPeriodInfo)getUIContext().get("period");
    	Map dataMap=FDCBudgetAcctFacadeFactory.getRemoteInstance().getAssociateAcctPay(payReqId, period);

    	if(dataMap==null||dataMap.get("splitEntrys")==null){
    		FDCMsgBox.showWarning(this, "û�и��µ���Ŀ�¶ȼƻ�����");
    		SysUtil.abort();
    	}
    	if(dataMap.get("noPlan")!=null){
    		int v=FDCMsgBox.showConfirm2New(this, "�ø������뵥��Ӧ�ĺ�ͬû�гɱ���Ŀ����ƻ�,�Ƿ����?\n ������������ڸ���������潫��ͬ��������ǩ����ͬ");
    		if(v!=FDCMsgBox.YES){
    			SysUtil.abort();
    		}
    	}
    	if(dataMap.get("NoData")!=null){
    		FDCMsgBox.showWarning(this, "����û���¶ȸ���ƻ�");
    		return;
    	}
//    	getUIContext().put("requestAmount", dataMap.get("requestAmount"));
    	getUIContext().put("contractId", dataMap.get("contractId"));
    	getUIContext().put("prjId", dataMap.get("prjId"));
    	prmtProject.setValue(dataMap.get("curProject"));
    	EventListener[] listeners = prmtPeriod.getListeners(DataChangeListener.class);
    	for(int i=0;i<listeners.length;i++){
    		prmtPeriod.removeDataChangeListener((DataChangeListener)listeners[i]);
    	}
    	try{
    		prmtPeriod.setValue(dataMap.get("period"));
    	}finally{
    		for(int i=0;i<listeners.length;i++){
    			prmtPeriod.addDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}
    	ContractCostSplitEntryCollection splitEntrys=(ContractCostSplitEntryCollection)dataMap.get("splitEntrys");
    	for(Iterator iter=splitEntrys.iterator();iter.hasNext();){
    		ContractCostSplitEntryInfo entry=(ContractCostSplitEntryInfo)iter.next();
    		IRow row=table.addRow();
    		row.setUserObject(entry);
    		loadRow(row);
    	}
    	KDTableHelper.autoFitColumnWidth(table, table.getColumnIndex("acctNumber"),2);
    	KDTableHelper.autoFitColumnWidth(table, table.getColumnIndex("acctName"),2);
    	KDTableHelper.autoFitColumnWidth(table, table.getColumnIndex("isSelect"),2);
    	setUnionAll();
    }
    
    /**
     * ��ӻ����н��л���
     */
    private void setUnionAll(){
    	//���úϼ���
    	KDTFootManager footRowManager= table.getFootManager();
    	IRow footRow = null;
    	if(footRowManager==null){
    		String total="�ϼ�";
    		if(footRowManager==null){
    			footRowManager = new KDTFootManager(table);
    			footRowManager.addFootView();
    			table.setFootManager(footRowManager);
    			footRow= footRowManager.addFootRow(0);
    			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
    			table.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
    			table.getIndexColumn().setWidth(60);
    			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
    			//���õ���һ��������
    			footRowManager.addIndexText(0, total);
    		}
    	}else{
    		footRow=table.getFootRow(0);
    	}
    	List colList=Arrays.asList((new String[]{"planAmt","requestedAmt","balanceAmt","amount"}));
    	Map map=new HashMap();
    	for(int i=0;i<table.getRowCount();i++){
    		IRow row=table.getRow(i);
	    	for(Iterator iter=colList.iterator();iter.hasNext();){
	    		String colKey=(String)iter.next();
	    		map.put(colKey, FDCNumberHelper.add(map.get(colKey), row.getCell(colKey).getValue()));
	    	}
    	}
    	for(Iterator iter=colList.iterator();iter.hasNext();){
    		String colKey=(String)iter.next();
    		footRow.getCell(colKey).setValue(map.get(colKey));
    	}
    	
    }
    
    private void loadRow(IRow row){
		ContractCostSplitEntryInfo entry=(ContractCostSplitEntryInfo)row.getUserObject();
		row.getCell("id").setValue(entry.getId().toString());
		row.getCell("acctNumber").setValue(entry.getCostAccount().getLongNumber());
		row.getCell("acctName").setValue(entry.getCostAccount().getName());
		row.getCell("planAmt").setValue(entry.getBigDecimal("planAmt"));
		BigDecimal reqAllAmount = entry.getBigDecimal("reqAllAmt");
		row.getCell("requestedAmt").setValue(reqAllAmount);
		BigDecimal amount = entry.getBigDecimal("reqAmt");
		row.getCell("amount").setValue(amount);
		row.getCell("balanceAmt").setValue(FDCNumberHelper.subtract(entry.getBigDecimal("planAmt"), reqAllAmount));
		if(entry.getCostAccount()!=null){
			row.getCell("project").setValue(entry.getCostAccount().getCurProject());	
		}
		
    	boolean isSelect = FDCNumberHelper.isPositiveBigDecimal(amount);
		row.getCell("isSelect").setValue(Boolean.valueOf(isSelect));
    	boolean isLock=false;
		if(getOprtState()!=null&&getOprtState().equals(OprtState.VIEW)){
    		isLock=true;
    	}
		if(isSelect&&!isLock){
			row.getCell("amount").getStyleAttributes().setLocked(false);
			row.getCell("amount").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
			if(row.getCell("amount").getValue()==null){
				row.getCell("amount").setValue(row.getCell("balanceAmt").getValue());
			}
		}
    	
    }
    private void initTable() {
    	table.checkParsed();
    	table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	table.getColumn("acctNumber").setRenderer(FDCRenderHelper.getLongNumberRender());
    	table.getColumn("amount").setEditor(FDCTableHelper.getCellNumberEditor(FDCNumberHelper.ZERO, FDCNumberHelper.MAX_VALUE, true));
    	table.getStyleAttributes().setLocked(true);
    	FDCHelper.formatTableNumber(table, "amount");
    	FDCHelper.formatTableNumber(table, "requestedAmt");
    	FDCHelper.formatTableNumber(table, "planAmt");
    	FDCHelper.formatTableNumber(table, "balanceAmt");
	}
}