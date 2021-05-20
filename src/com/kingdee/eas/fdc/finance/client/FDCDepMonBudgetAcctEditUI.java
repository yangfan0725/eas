/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CellBinder;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctDataInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctEntryCollection;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctFactory;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctItemInfo;
import com.kingdee.eas.fdc.finance.IFDCBudgetAcct;
import com.kingdee.eas.fdc.finance.FDCBudgetCtrlStrategy.FDCBudgetParam;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FindDialog;
import com.kingdee.eas.framework.client.FindListEvent;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IFindListListener;
import com.kingdee.eas.framework.client.ListFind;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class FDCDepMonBudgetAcctEditUI extends AbstractFDCDepMonBudgetAcctEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCDepMonBudgetAcctEditUI.class);
    private final String COLUMN_AUDITEDPAY="month_auditedPay";
    private Map preVerMap = new HashMap();
    //��ѯ���˽���
    private FDCDepMonBudgetAcctFilterUI budgetAcctFilterUI = null;
    /**
     * output class constructor
     */
    public FDCDepMonBudgetAcctEditUI() throws Exception
    {
        super();
    }
    
    public void storeFields(){
    }

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
	}

	FDCBudgetAcctInfo createNewFDCBudgetAcct() {
		FDCDepMonBudgetAcctInfo info = new FDCDepMonBudgetAcctInfo();
		//ֻ��ʾ����̨��ֵ why?
		info.setDeptment(SysContext.getSysContext().getCurrentAdminUnit());
		txtDeptment.setText(SysContext.getSysContext().getCurrentAdminUnit().getName());
		return info;
	}

	IFDCBudgetAcct getRemoteInterface() throws BOSException {
		return FDCDepMonBudgetAcctFactory.getRemoteInstance();
	}

	protected void resetTableHead() {
		super.resetTableHead();
		int startIndex=getDetailTable().getColumnIndex("allPay")+2;
		IRow headRow1=getDetailTable().getHeadRow(0);
		IRow headRow2=getDetailTable().getHeadRow(1);
		int currentMonth=getCurrentMonth();
		for(int i=currentMonth;i<currentMonth+3;i++){
			IColumn column = getDetailTable().addColumn(startIndex);
			column.setKey(getMonthCostKey(i));
			column.setEditor(CellBinder.getCellNumberEdit());
//			System.out.println("column:"+column.getColumnIndex()+"\tkey"+column.getKey());
			column=getDetailTable().addColumn(startIndex+1);
			column.setKey(getMonthPayKey(i));
			column.setEditor(CellBinder.getCellNumberEdit());
//			System.out.println("column:"+column.getColumnIndex()+"\tkey"+column.getKey());
			String title=(i>12?i%12:i)+"��Ԥ��";
			if(currentMonth==i){
				column=getDetailTable().addColumn(startIndex+2);
				column.setKey(this.COLUMN_AUDITEDPAY);
				column.setEditor(CellBinder.getCellNumberEdit());
				headRow1.getCell(startIndex).setValue(title);
				headRow1.getCell(startIndex+1).setValue(title);
				headRow1.getCell(startIndex+2).setValue(title);
				headRow2.getCell(startIndex).setValue("�ɱ�");
				headRow2.getCell(startIndex+1).setValue("�����걨");
				headRow2.getCell(startIndex+2).setValue("��������");
//				System.out.println("column:"+column.getColumnIndex()+"\tkey"+column.getKey());
				startIndex+=3;
			}else{
				headRow1.getCell(startIndex).setValue(title);
				headRow1.getCell(startIndex+1).setValue(title);
				headRow2.getCell(startIndex).setValue("�ɱ�");
				headRow2.getCell(startIndex+1).setValue("����");
				startIndex+=2;
			}
		}
//		getDetailTable().addColumn().setKey(this.COLUMN_AUDITEDPAY);
		getDetailTable().getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_MERGE);
		FDCHelper.formatTableNumber(getDetailTable(), "aimCost");
		FDCHelper.formatTableNumber(getDetailTable(), "dynCost");
		FDCHelper.formatTableNumber(getDetailTable(), getDetailTable().getColumnIndex("conAmt"), getDetailTable().getColumnIndex("desc")-1);
	}

	FDCBudgetAcctEntryInfo createNewFDCBudgetAcctEntry() {
		return new FDCDepMonBudgetAcctEntryInfo();
	}

    protected void storeRows(){
    	FDCDepMonBudgetAcctInfo monthBudgetAcctInfo = getFDCDepMonBudgetAcctInfo();
    	if(monthBudgetAcctInfo.getEntrys()==null){
    		monthBudgetAcctInfo.put("entrys", new FDCDepMonBudgetAcctEntryCollection());
    	}
    	monthBudgetAcctInfo.getEntrys().clear();
    	for(int i=0;i<getDetailTable().getRowCount();i++){;
    		IRow row=getDetailTable().getRow(i);
    		FDCDepMonBudgetAcctEntryInfo entry = (FDCDepMonBudgetAcctEntryInfo)storeRow(row);
    		if(entry!=null){
    			monthBudgetAcctInfo.getEntrys().add(entry);
    		}
    	}
    }
    protected FDCBudgetAcctEntryInfo storeRow(IRow row){
    	if(!isEmptyRow(row)&&row.getUserObject() instanceof FDCDepMonBudgetAcctEntryInfo){
    		FDCDepMonBudgetAcctEntryInfo entry=(FDCDepMonBudgetAcctEntryInfo)row.getUserObject();
    		if(isRecension()){
    			entry.setId(null);
    		}
			entry.setCreator((UserInfo)row.getCell("creator").getValue());
			entry.setDesc((String)row.getCell("desc").getValue());
			entry.setDeptment((AdminOrgUnitInfo)row.getCell("deptment").getValue());
			entry.setName((String)row.getCell("conName").getValue());
			entry.setNumber((String)row.getCell("conNumber").getValue());
			entry.setPartB((String)row.getCell("partB").getValue());
			entry.setSplitAmt((BigDecimal)row.getCell("splitAmt").getValue());
			entry.setConAmt((BigDecimal)row.getCell("conAmt").getValue());
			entry.getItems().clear();
			entry.setParent(getFDCDepMonBudgetAcctInfo());
			int currentMonth=getCurrentMonth();
			for(int i=currentMonth;i<currentMonth+3;i++){
				FDCDepMonBudgetAcctItemInfo item=new FDCDepMonBudgetAcctItemInfo();
				item.setEntry(entry);
				item.setAmount((BigDecimal)row.getCell(getMonthPayKey(i)).getValue());
				item.setCost((BigDecimal)row.getCell(getMonthCostKey(i)).getValue());
				item.setMonth(i>12?i%12:i);
				if(item.getAmount()==null&&item.getCost()==null&&row.getCell(COLUMN_AUDITEDPAY).getValue()==null){
					continue;
				}
				if(i==currentMonth){
					entry.setCost(item.getCost());
					entry.setAmount((BigDecimal)row.getCell(COLUMN_AUDITEDPAY).getValue());
				}
				entry.getItems().add(item);
			}
			
			return entry;
		}
    	return null;
    }
    
    private FDCDepMonBudgetAcctInfo getFDCDepMonBudgetAcctInfo(){
    	return (FDCDepMonBudgetAcctInfo)this.editData;
    }
    public void setDataObject(IObjectValue dataObject)
    {
    	super.setDataObject(dataObject);
    }
    protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
    	//
    }
    protected ICoreBase getBizInterface() throws Exception {
    	return FDCDepMonBudgetAcctFactory.getRemoteInstance();
    }
    public void actionHideBlankRow_actionPerformed(ActionEvent e)
    		throws Exception {
    	// TODO Auto-generated method stub
    	super.actionHideBlankRow_actionPerformed(e);
    }
    
    public void onLoad() throws Exception {
    	AdminOrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentAdminUnit();
    	getUIContext().put("deptmentId", orgUnitInfo.getId().toString());
    	super.onLoad();
//    	editData.setDeptment(orgUnitInfo);
    	if(editData.getDeptment()!=null){
    		txtDeptment.setText(editData.getDeptment().getName());
    	}
    	this.tblMain.getSelectManager().select(0,0,0,tblMain.getColumnCount());
    	
//    	editPayAuditColumn();
    	setColorByVersionDiff();
    	//���Ӽ����¼�ʹ�ڵ��delete�ȼ�ֵ��ʱ�򣬸��������ĵ�ֵ�ı�
    	tblMain.setBeforeAction(new BeforeActionListener(){
    		public void beforeAction(BeforeActionEvent e)
    		{
    			if(BeforeActionEvent.ACTION_DELETE==e.getType() || BeforeActionEvent.ACTION_PASTE==e.getType()){
    				for (int i = 0; i <getDetailTable().getSelectManager().size(); i++)
    				{
    					KDTSelectBlock block = getDetailTable().getSelectManager().get(i);
    					for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
    					{
    						for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
    							if(colIndex==getDetailTable().getColumnIndex(COLUMN_AUDITEDPAY)-1){
    								KDTEditEvent event = new KDTEditEvent(tblMain, null, null, rowIndex, colIndex, true, 1);
    								try {
    									tblMain_editStopped(event);
    								} catch (Exception e1) {
    									e1.printStackTrace();
    								}
    							}
    						}
    					}
    				}
    			}
    		}
    	});
    }
    


    public void loadRow(IRow row) {
    	super.loadRow(row);
		FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)row.getUserObject();
		//���깤�ɱ�
		if (FDCBudgetAcctItemTypeEnum.CONTRACT.equals(entry.getItemType())) {
			row.getCell("allCost").setValue(entry.getBigDecimal("allCost"));
		}
		row.getCell("allPay").setValue(entry.getBigDecimal("allPay"));
		if(entry.getContractBill()!=null&&entry.getContractBill().getRespPerson()!=null){
			row.getCell("conDeptPerson").setValue(entry.getContractBill().getRespPerson().getName());
		}
		if(entry.getContractBill()!=null&&entry.getContractBill().getCreator()!=null){
			row.getCell("conCreator").setValue(entry.getContractBill().getCreator().getName());
		}
    }
    
    public void fillItems(IRow row){
    	super.fillItems(row);
    	FDCDepMonBudgetAcctEntryInfo entry=(FDCDepMonBudgetAcctEntryInfo)row.getUserObject();
    	row.getCell(this.COLUMN_AUDITEDPAY).setValue(entry.getAmount());
    }
    protected void tblMain_editStopped(KDTEditEvent e){
    	Object newValue=e.getValue();
    	Object oldValue=e.getOldValue();
    	//����delete����backspace��������������ֵΪ��
 /*   	if(newValue==null){
    		if(isMonthColumn(e.getColIndex())){
        		IRow row=getDetailTable().getRow(e.getRowIndex());
        		if(e.getColIndex()==getDetailTable().getColumnIndex(this.COLUMN_AUDITEDPAY)-1){
        			row.getCell(this.COLUMN_AUDITEDPAY).setValue(newValue);
        		}
        	}
    	}*/
    	/*if(newValue==null&&oldValue==null){
    		return;
    	}
    	if(newValue!=null&&oldValue!=null&&newValue.equals(oldValue)){
    		return;
    	}*/
   
    	if(isMonthColumn(e.getColIndex())){
    		IRow row=getDetailTable().getRow(e.getRowIndex());
//    		BigDecimal tmpAmt=FDCHelper.ZERO;
//    		BigDecimal tmpCost=FDCHelper.ZERO;
//			int currentMonth=getCurrentMonth();
//			for(int i=currentMonth;i<currentMonth+3;i++){
//    			tmpAmt=FDCNumberHelper.add(tmpAmt, row.getCell(getMonthPayKey(i)).getValue());
//    			tmpCost=FDCNumberHelper.add(tmpCost, row.getCell(getMonthCostKey(i)).getValue());
//    		}
    		//���������е�˳��֮������ע�͵�Ӳ����ᵼ����д���걨�в���ͬ��д�������� 
    		/*if(e.getColIndex()==getDetailTable().getColumnIndex(this.COLUMN_AUDITEDPAY)-1){
    			row.getCell(this.COLUMN_AUDITEDPAY).setValue(newValue);
    		}����Ϊ��by Cassiel_peng 2009-10-20*/
//    		if(e.getColIndex()==getDetailTable().getColumnIndex("month_pay"+getCurrentMonth())){
//    			row.getCell(this.COLUMN_AUDITEDPAY).setValue(newValue);
//    		}
    		//�ϸ����columnKeyֵ�Ƚ�
    		if(getDetailTable().getColumnKey(e.getColIndex()).equals("month_pay"+getCurrentMonth())){
    			row.getCell(this.COLUMN_AUDITEDPAY).setValue(newValue);
    		}
    	}
    	super.tblMain_editStopped(e);

    }
    
    protected void setEmptRow(KDTEditEvent e){    
    	int index=e.getRowIndex();
    	IRow row=getDetailTable().getRow(index);
    	if(e.getValue()!=null){
    		setEmptyRow(row, false);
    	}else{
    		//�ж����ǲ��ǿ�
    		boolean isEmpty=true;
    		int currentMonth=getCurrentMonth();
    		for(int i=currentMonth;i<currentMonth+3;i++){
    			if(i==index){
    				continue;
    			}
    			if(row.getCell(getMonthCostKey(i)).getValue()!=null
    					||row.getCell(getMonthPayKey(i)).getValue()!=null){
    				isEmpty=false;
    				break;
    			}
    		}
    		isEmpty=isEmpty?FDCHelper.isEmpty(row.getCell("desc").getValue()):isEmpty;
    		if(isEmpty&&!row.getCell("conName").getStyleAttributes().isLocked()){
    			isEmpty=FDCHelper.isEmpty(row.getCell("conName").getValue());
    		}
    		setEmptyRow(row, isEmpty);
    	}
    }
    protected void afterFillTable() {
    	super.afterFillTable();
    }
    private int getCurrentMonth() {
		return getSpValue(spMonth);
	}
    
    
    public void setEditable() {
    	super.setEditable();
    	if(getOprtState().equals(STATUS_FINDVIEW)) {
    		getDetailTable().getColumn(this.COLUMN_AUDITEDPAY).getStyleAttributes().setLocked(false);
    	}else{
    		getDetailTable().getColumn(this.COLUMN_AUDITEDPAY).getStyleAttributes().setLocked(true);
    	}
	}
    
	protected FDCBudgetAcctInfo getFDCBudgetAcctInfo(){
		return this.editData;
	}
	public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = super.getSelectors();
        sic.add(new SelectorItemInfo("deptment.name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("curProject.name"));
        sic.add(new SelectorItemInfo("verNumber"));
        sic.add(new SelectorItemInfo("auditor.name"));
        sic.add(new SelectorItemInfo("auditTime"));
        return sic;
    }
    protected void fillCostAccountRow(IRow row) {
    	if(row.getUserObject() instanceof CostAccountInfo){
    		CostAccountInfo info=(CostAccountInfo)row.getUserObject();
    		String key=info.getId().toString();
    		Map map=(Map)getDataMap().get("costMap");
    		if(map!=null&&map.size()>0){
    			FDCBudgetAcctDataInfo dataInfo=(FDCBudgetAcctDataInfo)map.get(key);
    			if(dataInfo==null){
    				return;
    			}
    			BigDecimal aimCost = dataInfo.getAimCost();
    			BigDecimal dynCost = dataInfo.getDynCost();
    			BigDecimal lstCost = dataInfo.getLstCost();
    			BigDecimal lstPay = dataInfo.getLstPay();
    			row.getCell("aimCost").setValue(aimCost);
				row.getCell("dynCost").setValue(dynCost);
				/**
				 * ��Ŀ�� ���깤�ɱ� ��ʾ��Ϊ ��ͬ+��Ŀ˫ά�� �ں�ͬ����ʾ
				 * by hpw date:2009-07-13
				 */
//				row.getCell("allCost").setValue(lstCost);
//				row.getCell("allPay").setValue(lstPay);
    			
    		}
    	}
	}
    protected List getColumnSumList(){
    	List list=getColumnSumList();
    	int currentMonth = getCurrentMonth();
    	for(int i=currentMonth;i<currentMonth+3;i++){
    		list.add(getMonthCostKey(i));
    		list.add(getMonthPayKey(i));
    	}
    	list.add("allCost");
    	list.add("allPay");
    	return list;
    }
    
    protected void initTable() {
    	super.initTable();
    	tblMain.getColumn("creator").setWidth(130);
      }
    
	/**
	 * ��Ŀ������
	 * @return
	 */
	protected List getSumColumns(){
		List list=super.getSumColumns();
//    	list.add("allCost");
		return list;
	}
	/**
	 * ��ǩ����ͬ������
	 * @return
	 */
	protected List getUnSettledSumColumns(){
		List list=super.getUnSettledSumColumns();
		return list;
	}
	/**
	 * ��ǩ����ͬ������
	 * @return
	 */
	protected List getSettledSumColumns(){
		List list=super.getUnSettledSumColumns();
		list.add("allCost");
		list.add("allPay");
		return list;
	}
	
	public void onShow() throws Exception {
		super.onShow();
		//���ƽ���״̬
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    
        SwingUtilities.invokeLater(new Runnable(){
   	  		public void run() {
   	  			editPayAuditColumn();
   	 		}
   	   });
	}
	
	/**
	 * �������������У��޸ĸ��������С������汻���������޸ģ��ɽ�������߳����ִ��<p>
	 * @author pengwei_hou sxhong @date: 2008-11-08<p><br>
	 * ������<pre>
	 * 	  SwingUtilities.invokeLater(new Runnable(){<p>
	 * 		public void run() {<p>
	 * 			editPayAuditColumn();<p>
	 * 		}<p>
	 * 	  });
	 */
	private void editPayAuditColumn(){
		
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
    	if(isFromWorkflow!=null 
    			&& isFromWorkflow.toString().equals("true") 
    			&& getOprtState().equals(STATUS_FINDVIEW) 
    			&& actionSave.isVisible() 
    			&& (editData.getState()==FDCBillStateEnum.SUBMITTED 
    					|| editData.getState() == FDCBillStateEnum.AUDITTING)){
    	
//    		getDetailTable().getStyleAttributes().setLocked(false);
//    		getDetailTable().setEnabled(true);
    		getDetailTable().setEditable(true);
    		
    		for(int i=0;i<getDetailTable().getRowCount();i++){
    			IRow row = getDetailTable().getRow(i);
    			if(row.getUserObject() instanceof FDCBudgetAcctEntryInfo){
//    				row.getStyleAttributes().setLocked(true);
    				//�����������ڹ������в鿴���ݿ��޸�
    				row.getCell(COLUMN_AUDITEDPAY).getStyleAttributes().setLocked(false);
    			}else{
    				row.getCell(COLUMN_AUDITEDPAY).getStyleAttributes().setLocked(true);
    			}
    		}
    		this.actionSave.setEnabled(true);
    	}
    
    	if(isFromWorkflow!=null 
    			&& isFromWorkflow.toString().equals("true") 
    			&& getOprtState().equals(STATUS_EDIT)){
    		actionRemove.setEnabled(false);
    	}
	}
	
	public boolean isModify() {
		return super.isModify();
	}
	
	protected void setUnionAll(){
//    	if(true){
//    		return;
//    	}
    	super.setUnionAll();
    	for(int i=0;i<getDetailTable().getRowCount();i++){
    		IRow row=getDetailTable().getRow(i);
			//������ڳɱ��Ծ���ɫ��ʾ
			int currentMonth = getCurrentMonth();
			for(int j=currentMonth;j<currentMonth+3;j++){
				String costKey=getMonthCostKey(j);
				Color oldColor=null;
				if(row.getCell(costKey).getUserObject()==null){
					oldColor=row.getCell(costKey).getStyleAttributes().getBackground();
					row.getCell(costKey).setUserObject(oldColor);
				}else{
					oldColor=(Color)row.getCell(costKey).getUserObject();
				}
				BigDecimal cost = (BigDecimal)row.getCell(getMonthCostKey(j)).getValue();
				BigDecimal pay = (BigDecimal)row.getCell(getMonthPayKey(j)).getValue();
				if(FDCNumberHelper.subtract(FDCHelper.toBigDecimal(cost), FDCHelper.toBigDecimal(pay)).signum()<0){
					row.getCell(getMonthCostKey(j)).getStyleAttributes().setBackground(FDCTableHelper.warnColor);
					row.getCell(getMonthPayKey(j)).getStyleAttributes().setBackground(FDCTableHelper.warnColor);
				}else{
					row.getCell(getMonthCostKey(j)).getStyleAttributes().setBackground(oldColor);
					row.getCell(getMonthPayKey(j)).getStyleAttributes().setBackground(oldColor);
				}
			}
			//�������
			Object obj = row.getUserObject();
//			if (obj == null || obj instanceof CostAccountInfo) {
				BigDecimal allCost = (BigDecimal)row.getCell("allCost").getValue();
				BigDecimal allPay = (BigDecimal)row.getCell("allPay").getValue();
				if (FDCNumberHelper.subtract(allCost, FDCHelper.ZERO).signum() > 0) {
					BigDecimal payScale = FDCHelper.divide(allPay, allCost, 4, BigDecimal.ROUND_HALF_UP);
					row.getCell("payScale").setValue(payScale);
				}
//			}
    	}
    	FDCHelper.formatTableNumber(tblMain, "payScale","##0.00%");
    	afterSetUnionAll();
    }
	
	protected int getStartMonth() {
		return getCurrentMonth();
	}
	
	protected int getEndMonth() {
		int currentMonth=getCurrentMonth();
		return currentMonth+2;
	}
	
	protected Set getCostColumns(){
		Set set=super.getCostColumns();
		set.add("allCost");
		return set;
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// TODO �Զ����ɷ������
		super.actionSave_actionPerformed(e);
		if(getUIContext().get("isFromWorkflow")!=null 
				&& getUIContext().get("isFromWorkflow").toString().equals("true") 
				&&getOprtState().equals(STATUS_EDIT) 
				&&actionSave.isVisible()){
			actionAddLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			actionInsertLine.setEnabled(false);
			actionRemove.setEnabled(false);
			actionSubmit.setEnabled(false);
		}
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		// TODO �Զ����ɷ������
		super.actionSubmit_actionPerformed(e);
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if(isFromWorkflow!=null 
    			&& isFromWorkflow.toString().equals("true") 
    			&& getOprtState().equals(STATUS_EDIT)){
    		actionRemove.setEnabled(false);
    	}
	}
//	�ڡ��ύ���°汾ʱ����Ҫ�����С�����޸ĺ�ı��¼ƻ����Ƿ�С�ڹ������ƻ��ĸ������뵥���
	protected boolean checkCanSubmit() throws Exception {
		boolean canSubmit = super.checkCanSubmit();
		String tmpInfo = null;
		String rowNumbers = null;
		if(canSubmit){
			//ȡϵͳ����
			FDCBudgetParam params=null;
			try {
				params=FDCBudgetParam.getInstance(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString());
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			
			Map curPeriodPayMap=null;
			String projectId = this.editData.getCurProject().getId().toString();
			FDCBudgetPeriodInfo CurPeriod = getPeriod();
			try {
				curPeriodPayMap=FDCBudgetAcctHelper.getCurPeriodRequestedAmt(null, projectId, CurPeriod);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			
			for(int i=0;i<getDetailTable().getRowCount();i++){
	    		IRow row=getDetailTable().getRow(i);
	    		//����ϸ���ϱȽϱ��¼ƻ������������뵥���
	    		if(row.getUserObject() instanceof FDCBudgetAcctEntryInfo){
	    			FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)row.getUserObject();
	    			if(entry.getContractBill() != null){	
	    				String rowKey = entry.getCostAccount().getId().toString() + entry.getContractBill().getId().toString();
	    				String colKey = getMonthPayKey(getCurrentMonth());
	    				tmpInfo = "�� " + (i+1) + " �еı��¼ƻ���С�ڸ������뵥���";
	    				BigDecimal monthPay = (BigDecimal)row.getCell(colKey).getValue();
	    				if(monthPay == null){
	    					monthPay = FDCHelper.ZERO;
	    				}
	    				BigDecimal reqPay = (BigDecimal)curPeriodPayMap.get(rowKey);
	    				if(reqPay == null){
	    					reqPay = FDCHelper.ZERO;
	    				}
	    				if(reqPay.compareTo(monthPay)>0)
	    				{
	    					//�ϸ����
	    					if (params!=null && params.isStrictCtrl()) {
	    							canSubmit = false;
	    							MsgBox.showWarning(this, tmpInfo);
	    							this.abort();
	    					}else{
	    						if(rowNumbers == null){
	    							rowNumbers = String.valueOf(i+1);
	    						}else{
	    							rowNumbers = rowNumbers.concat("��".concat(String.valueOf(i+1)));	    							
	    						}
	    					}
	    				}
	    			}
	    		}
			}
			if(rowNumbers != null){
				tmpInfo = "�� " + rowNumbers + " �еı��¼ƻ���С�ڸ������뵥���";
				if (MsgBox.showConfirm2(this,tmpInfo) == MsgBox.CANCEL) {
					canSubmit = false;
					this.abort();
				}
			}
		}
		return canSubmit;
	}
	
	private void setColorByVersionDiff(){
		BigDecimal preVer = FDCHelper.toBigDecimal(txtVerNumber.getText());
		if(preVer.compareTo(FDCHelper.toBigDecimal("1.0")) == 0){
			return;
		}
//		try {
//			getPreVerMap(preVer);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		int currentMonth = getCurrentMonth();
		String costAccountId=null;
		for(int i=0;i<getDetailTable().getRowCount();i++){
    		IRow row=getDetailTable().getRow(i);
    		String contractBillId=null;
    		//��ϸ���ϲŻ��п�Ŀʵ��
    		if(row.getUserObject() instanceof CostAccountInfo){
    			CostAccountInfo ca = (CostAccountInfo)row.getUserObject();
    			costAccountId = ca.getId().toString();
    			continue;
    		}else if(row.getUserObject() instanceof FDCBudgetAcctEntryInfo){
    			FDCBudgetAcctEntryInfo entry=(FDCBudgetAcctEntryInfo)row.getUserObject();
    			if(entry.getContractBill() == null){
    				costAccountId = null;
    				continue;
    			}else{
    				contractBillId=entry.getContractBill().getId().toString();
    			}
    		}else if(isUnSettlecContractRow(row)){
    			contractBillId="nullid";
			}else{
				continue;
			}
    		if(costAccountId == null || contractBillId == null)	continue;
    		
			String sKey=costAccountId.concat("|".concat(contractBillId.concat("|")));
			for(int j=currentMonth;j<currentMonth+3;j++){
				String monthCostKey=getMonthCostKey(j);
				String monthPayKey=getMonthPayKey(j);
				String costKey=sKey.concat(monthCostKey);
				String payKey=sKey.concat(monthPayKey);
				BigDecimal cost = (BigDecimal)row.getCell(monthCostKey).getValue();
				BigDecimal pay = (BigDecimal)row.getCell(monthPayKey).getValue();
				BigDecimal preCost = FDCHelper.ZERO;
				BigDecimal prePay = FDCHelper.ZERO;
//				if(preVerMap.size()>0 && preVerMap.containsKey(costKey)){
//					preCost = (BigDecimal)preVerMap.get(costKey);
//				}
//				if(preVerMap.size()>0 && preVerMap.containsKey(payKey)){
//					prePay = (BigDecimal)preVerMap.get(payKey);
//				}
//				
//				if(preVerMap.size()<1 || (cost != null && cost.compareTo(preCost)!=0)){
//					row.getCell(monthCostKey).getStyleAttributes().setFontColor(Color.RED);
//				}
//				if(preVerMap.size()<1 || (pay != null && pay.compareTo(prePay)!=0)){
//					row.getCell(monthPayKey).getStyleAttributes().setFontColor(Color.RED);
//				}
			}
    	}
	}
	
	private void getPreVerMap(BigDecimal preVer)throws BOSException, SQLException {
		String projectId = this.editData.getCurProject().getId().toString();
		String periodId = this.editData.getFdcPeriod().getId().toString();
		String sKey = null;
		String sMonth = null;
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select t1.FCostAccountId,t1.FContractBillId,t0.FMonth,t0.FCost,t0.FAmount from T_FNC_FDCMonthBudgetAcctItem t0 ");
		builder.appendSql("inner join t_fnc_fdcmonthbudgetacctentry t1 on t1.fid=t0.fentryid ");
		builder.appendSql("inner join t_fnc_fdcmonthbudgetacct t2 on t2.fid=t1.fparentid ");
		builder.appendSql("where t2.FProjectId=? and t2.FfdcPeriodId=? and t2.fvernumber=?-0.1");
		builder.appendSql("and t1.fitemtype='1CONTRACT' ");
		builder.appendSql("union ");
		builder.appendSql("select t1.FCostAccountId,'nullid' as FContractBillId,t0.FMonth,sum(t0.FCost) as FCost,sum(t0.FAmount) as FAmount from T_FNC_FDCMonthBudgetAcctItem t0 ");
		builder.appendSql("inner join t_fnc_fdcmonthbudgetacctentry t1 on t1.fid=t0.fentryid ");
		builder.appendSql("inner join t_fnc_fdcmonthbudgetacct t2 on t2.fid=t1.fparentid ");
		builder.appendSql("where t2.FProjectId=? and t2.FfdcPeriodId=? and t2.fvernumber=?-0.1 ");
		builder.appendSql("and t1.fitemtype='9UNSETTLEDCONTRACT' ");
		builder.appendSql("group by t1.FCostAccountId,t0.FMonth ");
		
    	builder.addParam(projectId);
    	builder.addParam(periodId);
    	builder.addParam(preVer);
    	builder.addParam(projectId);
    	builder.addParam(periodId);
    	builder.addParam(preVer);
    	IRowSet rowSet=builder.executeQuery();
    	preVerMap.clear();
    	try {
    		while (rowSet.next()) {
				sKey = rowSet.getString("FCostAccountId").concat("|".concat(rowSet.getString("FContractBillId").concat("|")));
				sMonth = String.valueOf(rowSet.getInt("FMonth"));
				BigDecimal preValue = rowSet.getBigDecimal("FCost");
				preVerMap.put(sKey.concat("month_cost".concat(sMonth)),preValue == null?FDCHelper.ZERO:preValue);
				preValue = rowSet.getBigDecimal("FAmount");
				preVerMap.put(sKey.concat("month_pay".concat(sMonth)),preValue == null?FDCHelper.ZERO:preValue);
			}
		}catch(SQLException e){
    		throw new BOSException(e);
    	}    	
	}
	
	/**
	 * ���������˽���
	 * @throws Exception 
	 */
    protected void showFDCBudgetAcctDeptFilterUI() throws Exception{
    	if(budgetAcctFilterUI == null){
    		budgetAcctFilterUI = new FDCDepMonBudgetAcctFilterUI();
    	}
    	Map context=new UIContext(this);
    	Map param = budgetAcctFilterUI.showUI(context);
    	getUIContext().putAll(param);
    	//�ݲ�֧��
//    	AdminOrgUnitInfo orgUnitInfo = (AdminOrgUnitInfo)param.get("deptmentInfo");
//    	editData.setDeptment(orgUnitInfo);
//    	if (orgUnitInfo != null)
//			txtDeptment.setText(orgUnitInfo.getName());
    	fillTable();
    }
    
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
    	showFDCBudgetAcctDeptFilterUI();
    	this.btnShowBlankRow.doClick();
    }
    
	/*--------�¶ȸ���ƻ��걨�������Ӷ�λ���ҹ���,�ɿ��ٲ鵽'��ͬ����"�Ϳ�Ŀ��   by ling_peng 2009-6-18--------*/
	protected void initWorkButton() {
		super.initWorkButton();
		btnQuery.setText("����");
		menuItemQuery.setText("����");
		if(menuItemCopyLine!=null){
			menuItemCopyLine.setVisible(false);
			menuItemCopyLine.setEnabled(false);
		}
		this.contVerNumber.setVisible(false);
//		this.txtVerNumber.setVisible(false);
		this.menuItemLocate.setIcon(EASResource.getIcon("imgTbtn_speedgoto"));
	}
	
	
	    private boolean hasQyeryPK = false;
	    boolean isFirstFind = true;
	    private int searcheRowCount=0;
	    private  String searchText=null;
	    private  boolean isMatch=false;
	    private  FindListEvent preFindListEvent=null;
	    private  String  propertyName=null;
	    //��λ��
	    private FindDialog findDialog = null;
	    private static String locateFirst = "Msg_LocateFirst";
	    private static String locateLast = "Msg_LocateLast";
	    //��ӵȴ���ʾ��
	    KDDialog kddialog=null;
	    protected String[] keyFieldNames;
	    //��¼id
	    protected String   subKeyFieldName;
	    //��ʼ��������
	    ListUiHelper listUiHelper=null;
	    
		 public void actionLocate_actionPerformed(ActionEvent e) throws Exception
		    {
			 	/*if(tblMain.getSelectManager().get()==null){
			 		MsgBox.showWarning("����ѡ��һ��");
			 		return;
			 	}*/
			 
		        searcheRowCount=0;
		        searchText=null;
		        preFindListEvent=null;
		        Window win = SwingUtilities.getWindowAncestor(this);
		        List FindPropertyName = new ArrayList();
		        for (int i = 0; i < getLocateNames().length; i++)
		        {
		            if (tblMain.getColumn(getLocateNames()[i]) != null)
		            {
		            	//����ʱ����Ӧ����tblMain.getHeadRow(0)�������ڴ˴�Ӧ�ó���֮��Ӧ����tblMain.getHeadRow(1)��
		                ListFind cEnum = new ListFind(getLocateNames()[i], tblMain.getHeadRow(1).getCell(getLocateNames()[i])
		                        .getValue().toString());
		                FindPropertyName.add(cEnum);
		            }
		        }

		        if (FindPropertyName.size() == 0)
		        {
		            return;
		        }

		            if (win instanceof Frame)
		            {
		                findDialog = new FindDialog((Frame) win, "", FindPropertyName, true);
		            }
		            else
		            {
		                findDialog = new FindDialog((Dialog) win, "", FindPropertyName, true);
		            }
		            findDialog.addFindListListener(new IFindListListener()
		            {
		                public void FindNext(FindListEvent e)
		                {
		                    locate(e);
		                }

		                /**
		                 * FindClose
		                 *
		                 * @param e
		                 *            FindListEvent
		                 */
		                public void FindClose(FindListEvent e)
		                {
		                    findDialog.dispose();
		                    findDialog = null;
		                    searcheRowCount=0;
		                    searchText=null;
		                    isMatch=false;
		                }
		            }

		            );
		        findDialog.setLocation(600, 100);
		        findDialog.show();
		    }
		     /**
			 * ����ģ����λ
			 */
			protected String[] getLocateNames() {
				String[] locateNames = new String[5];
				locateNames[0]="conNumber";
				locateNames[1]="conName";
				locateNames[2]="partB";
				locateNames[3]="acctNumber";
				locateNames[4]="acctName";
				return locateNames;
			} 
	
    protected void locateForNotIdList(FindListEvent e)
    {
        int currentRow = 0;
        if (this.tblMain.getSelectManager().get() != null)
        {
            currentRow = this.tblMain.getSelectManager().get().getBeginRow();
        }
        int i = 0;
            if (e.getFindDeration() == FindListEvent.Down_Find)
            {
                i = currentRow + 1;
            }
            else
            {
                i = currentRow;
            }

        IRow row = tblMain.getRow(i);
        showMsgNoIdList(row,e);
        while (row != null)
        {
        	row = tblMain.getRow(i);
            // ��������ںϵ��ݣ��򲻴���
            if (i!=-1&&i!=tblMain.getRowCount()&&row.getCell(e.getPropertyName()).getValue() == null)
            {
            	if (e.getFindDeration() == FindListEvent.Down_Find)
            		i++;
            	else
            		i--;
                continue;
            }
            int curSelectIndex=this.tblMain.getSelectManager().getActiveRowIndex();
            if (curSelectIndex==i)
            {
            	if (e.getFindDeration() == FindListEvent.Down_Find)
            		i++;
            	else
            		i--;
            	 continue;
            }
            row = tblMain.getRow(i);
            if (row==null)
            {
            	showMsgNoIdList(row,e);
            	break;
            }
            ICell cell=tblMain.getRow(i).getCell(e.getPropertyName());
            Object cellValue = cell==null?null:cell.getValue();
            String Search =cellValue==null?null:cellValue.toString().replace('!', '.');
            
            if (Search != null
                    && StringUtility.isMatch(Search, e.getSearch(), e.isIsMatch(), Pattern.CASE_INSENSITIVE))
            {
                isFirstFind = false;
                searcheRowCount++;
                this.tblMain.getSelectManager().select(i, 0, i, tblMain.getColumnCount());
                //tblMain.getRow(i).getStyleAttributes().setBackground(new Color(223,239,245));
                tblMain.getLayoutManager().scrollRowToShow(i);
                break;
            }
            if (e.getFindDeration() == FindListEvent.Down_Find){
            	i++;
            }
        	else{
        		i--;
        	}
        }

    }
    /**
     * �Ҳ���ƥ���¼ʱ������ʾ
     */
    private void showMsgNoIdList(IRow row,FindListEvent e)
    {
    	if (row == null)
        {
        	String hint="";
        	if (e.getFindDeration() == FindListEvent.Down_Find)
        	{
        		if (searcheRowCount==0)
        		{
        		  hint=locateLast;
        		}else
        		{
        		  hint="Msg_LocateFirst_end";
        		}
        	}else
        	{
        		if (searcheRowCount==0)
        		{
        			hint=locateFirst;
        		}else
        		{
        			hint="Msg_LocateFirst_end";
        		}
        	}
        	String msg=EASResource.getString(FrameWorkClientUtils.strResource + hint);
        	if (searcheRowCount!=0)
        	{
        		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
        		msg = MessageFormat.format(msg, objs);
        	}
            MsgBox.showInfo(this, msg);
            this.findDialog.show();
        }
    }
	

    //�Ƿ�ʹ����ģʽԤȡ,�ڶ�λ��ʱ����ҪԤȡ
	private boolean bPreFetch = true;
	
	  public boolean isHasQyeryPK()
	    {
	        return hasQyeryPK;
	    }
	
    // ��λList���λ�á�
    protected void locate(FindListEvent e)
    {
        boolean searchResult = false;
        int currentRow = 0;
        // ������
        int RowCount = 0;

        // �������ģʽ����
        // if (this.isHasQyeryPK() && !this.isLessData()) {
        int curSelectIndex=this.tblMain.getSelectManager().getActiveRowIndex();
        if (curSelectIndex<0&&(this.tblMain.getRowCount()>0||this.tblMain.getRowCount3()>0))
        {
        	this.tblMain.getSelectManager().select(0,0);
        	curSelectIndex=0;
        }
        if (curSelectIndex<0||this.tblMain.getRowCount()==0)
        {
        	MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateLast));
        	return;
        }
        if ((preFindListEvent!=null&&e.getFindDeration()!=preFindListEvent.getFindDeration())
        	||(searchText!=null&&!searchText.equals(e.getSearch()))
        	||(propertyName!=null&&!e.getPropertyName().equals(propertyName))
        	||isMatch!=e.isIsMatch())
        {
        	searcheRowCount=0;
        	Object cellValue= null;
        	try {
        		bPreFetch = false;
        		cellValue = tblMain.getRow(curSelectIndex).getCell(e.getPropertyName()).getValue();
        	}
        	finally {
        		bPreFetch = true;
        	}
          String selectText = cellValue==null?null:cellValue.toString();
          propertyName=e.getPropertyName();
          if (selectText != null&&StringUtility.isMatch(selectText, e.getSearch(), e.isIsMatch(), Pattern.CASE_INSENSITIVE))
          {
              searcheRowCount=searcheRowCount+1;
          }
        }
        isMatch=e.isIsMatch();
        preFindListEvent=e;
        searchText=e.getSearch();
        propertyName=e.getPropertyName();
        if (this.isHasQyeryPK())
        {
            RowCount = tblMain.getBody().size();
        }
        else
        {
            locateForNotIdList(e);
        }

        if (this.tblMain.getSelectManager().get() != null)
        {
            currentRow = this.tblMain.getSelectManager().get().getBeginRow();
        }
        int i = 0;
        if (e.getFindDeration() == FindListEvent.Down_Find)
        {
           i = currentRow + 1;
        }
        else
        {
            i = currentRow;
        }


        // ���û�����򲻽��д���
        if (RowCount == 0)
        {
            return;
        }

        if (e.getFindDeration() == FindListEvent.Down_Find)
        {
            if (i == RowCount)
            {
            	if (searcheRowCount==0)
            	{
                  MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateLast));
            	}
                else
                {
            		String msg=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_LocateLast_end");
            		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
            		msg = MessageFormat.format(msg, objs);
            		MsgBox.showInfo(this, msg);
            		//searcheRowCount=0;
                }
                this.findDialog.show();
            }
            else
            {
                for (; i < RowCount; i++)
                {
                	IRow row = null;
                	try {
                		bPreFetch = false;
                		row = tblMain.getRow2(i);
                	}
                	finally {
                		bPreFetch = true;
                	}
                	ICell cell=(null == row ) ? null : row.getCell(e.getPropertyName());
                    Object o = cell==null?null:cell.getValue();
                    String Search = o == null ? null : o.toString();
                    // ��������ںϵ��ݣ��򲻴���
                    if (Search == null)
                    {
                        if (i == RowCount - 1)
                        {
                            MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateLast));
                            this.findDialog.show();
                        }
                        continue;
                    }

                    if (Search != null
                            && StringUtility.isMatch(Search, e.getSearch(), e.isIsMatch(), Pattern.CASE_INSENSITIVE))
                    {
                        searchResult = true;
                        isFirstFind = false;
                        searcheRowCount++;
                        this.tblMain.getSelectManager().select(i, 0);
                        tblMain.getLayoutManager().scrollRowToShow(i);
                        break;
                    }
                    // û���ҵ��Ͳ���ѡ�С�
                    if (i == RowCount - 1)
                    {
                    	if (searcheRowCount==0)
                    	{
                    		MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateLast));
                    	}else
                    	{
                    		String msg=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_LocateLast_end");
                    		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
                    		msg = MessageFormat.format(msg, objs);
                    		MsgBox.showInfo(this, msg);
                    		//searcheRowCount=0;
                    	}
                        this.findDialog.show();
                    }
                }
            }
        }
        else
        {
            if (i == 0)
            {
            	if (searcheRowCount==0)
            	{
            		MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateFirst));
            	}else
            	{
            		String msg=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_LocateFirst_end");
            		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
            		msg = MessageFormat.format(msg, objs);
            		MsgBox.showInfo(this, msg);
            		//searcheRowCount=0;
            	}
                this.findDialog.show();
            }

            for (; i > 0; i--)
            {
            	ICell cell=(null == tblMain.getRow2(i - 1)) ? null : tblMain.getRow2(i - 1).getCell(e.getPropertyName());
            	Object o = cell==null?null:cell.getValue();
                String Search = o == null ? null : o.toString();
                // ��������ںϵ��ݣ��򲻴���
                if (Search == null)
                {
                    if (i == 1)
                    {
                        MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateFirst));
                        this.findDialog.show();
                    }
                    continue;
                }


                if (Search != null
                        && StringUtility.isMatch(Search, e.getSearch(), e.isIsMatch(), Pattern.CASE_INSENSITIVE))
                {
                    searchResult = true;
                    isFirstFind = false;
                    searcheRowCount++;
                    this.tblMain.getSelectManager().select(i - 1, 0);
                    tblMain.getLayoutManager().scrollRowToShow(i - 1);
                    break;
                }
                // û���ҵ��Ͳ���ѡ�С�
                if (i == 1)
                {
                	if (searcheRowCount==0)
                	{
                		MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateFirst));
                	}else
                	{
                		String msg=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_LocateFirst_end");
                		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
                		msg = MessageFormat.format(msg, objs);
                		MsgBox.showInfo(this, msg);
                		//searcheRowCount=0;
                	}

                    this.findDialog.show();
                }
            }
        }
    }
    public boolean destroyWindow() {
    	if(budgetAcctFilterUI!=null){
    		budgetAcctFilterUI.clear();
    	}
    	return super.destroyWindow();
    }

	
    
    
    protected void verifyInputForSave() throws Exception {
		if(this.txtNumber.isEnabled()){
			if(this.txtNumber.getText()==null || this.txtNumber.getText().trim().length()== 0){
				FDCMsgBox.showError("���벻��Ϊ�գ�");
				abort();
			}
		}
	}
    
    
}