/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.report;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.InviteTypeCollection;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAnnualSummaryFacadeFactory;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class SupplierAnnualSummaryListUI extends AbstractSupplierAnnualSummaryListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierAnnualSummaryListUI.class);
    
    private List<String> footSumArrayList = new ArrayList<String>();
    
    /**
     * output class constructor
     */
    public SupplierAnnualSummaryListUI() throws Exception
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
    
    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.kDSApproveYear.getModel().setValue(2016);
    	this.kDSValidateYear.getModel().setValue(2016);
    }


    /**
     * output actionSearchSupplier_actionPerformed
     */
    public void actionSearchSupplier_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSearchSupplier_actionPerformed(e);
        this.tblSupplier.removeRows();
        
        int approveYear = Integer.parseInt(this.kDSApproveYear.getModel().getValue()+"");
        int validateYear = Integer.parseInt(this.kDSValidateYear.getModel().getValue()+"");
        Map param = new HashMap();
        param.put("approveYear",approveYear);
        param.put("validateYear", validateYear);
        
        param =  SupplierAnnualSummaryFacadeFactory.getRemoteInstance().getSupplierSummaryInfo(param);
        if(param == null){
        	return ;
        }
        
        //处理后台返回的结果
        
        //1、渲染出供应商分类
        
        //按供应商等级增加列
        GradeSetUpCollection grades = (GradeSetUpCollection) param.get("grades");
        initTable(grades);
        
        
        
        InviteTypeCollection inviteTypes = (InviteTypeCollection) param.get("inviteType");
        fillTable(inviteTypes,param);
      
        footSumArrayList.add("supplierQuanty");
        footSumArrayList.add("approveOfYear");
        footSumArrayList.add("validateOfYear");
        footSumArrayList.add("newQualifyCount");
        footSumArrayList.add("newInviteCount");
        footSumArrayList.add("newWinCount");
        footSumArrayList.add("newSignCount");
        
        String [] footArray = new String[footSumArrayList.size()];
        footSumArrayList.toArray(footArray);
    	
        appendFootrow(this.tblSupplier,footArray);
        
        
    }

	private void initTable(GradeSetUpCollection grades) {
		footSumArrayList.clear();
		int startIndex = this.tblSupplier.getColumnIndex("percentOfSupplier");
		int currentIndex = startIndex;
		GradeSetUpInfo grade = null;
		IColumn column = null;
		boolean isFillGrade = false;
		for(Iterator<GradeSetUpInfo> it = grades.iterator();it.hasNext();){
			grade = it.next();
			if(!footSumArrayList.contains(grade.getId().toString())){
				footSumArrayList.add(grade.getId().toString());
			}
			if(tblSupplier.getColumnIndex(grade.getId().toString())==-1){
				isFillGrade = true;
				currentIndex+=1;
				column =this.tblSupplier.addColumn(currentIndex);
				column.setKey(grade.getId().toString());
				this.tblSupplier.getHeadRow(1).getCell(currentIndex).setValue(grade.getName());
				this.tblSupplier.getHeadRow(2).getCell(currentIndex).setValue("数量");
				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				column.getStyleAttributes().setNumberFormat("###.00");
				currentIndex+=1;
				column = this.tblSupplier.addColumn(currentIndex);
				column.setKey("percentOf"+grade.getId().toString());
				this.tblSupplier.getHeadRow(2).getCell(currentIndex).setValue("占比(%)");
				this.tblSupplier.getHeadMergeManager().mergeBlock(1, currentIndex-1, 1, currentIndex);
				column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				column.getStyleAttributes().setNumberFormat("###.00");
			}
			
			
		}
		if(isFillGrade){
			this.tblSupplier.getHeadRow(0).getCell(startIndex+1).setValue("供应商等级");
			this.tblSupplier.getHeadMergeManager().mergeBlock(0, startIndex+1, 0, currentIndex);
		}
	}

	private void fillTable(InviteTypeCollection inviteTypes,Map map) {
		FDCTableHelper.disableDelete(this.tblSupplier);
		this.tblSupplier.setEnabled(false);
	    IRow row = null;
	    InviteTypeInfo inviteType = null;
	    int totalCount = Integer.parseInt(map.get("totalCount")+"");
	    logger.error("totalCount:"+totalCount);
	    Map countMap = (Map) map.get("countMap");
	    Map<String,BigDecimal> gradeMap = (Map<String, BigDecimal>) map.get("gradeMap");
	    Map<String,BigDecimal> yearedApprovedMap = (Map<String, BigDecimal>) map.get("yearedApprovedMap");
	    Map<String,BigDecimal> yearedValidateMap = (Map<String, BigDecimal>) map.get("yearedValidateMap");
	    Map<String,BigDecimal> rateOfPassMap = (Map<String, BigDecimal>) map.get("rateOfPassMap");
	    Map<String,BigDecimal> qualifyMap = (Map<String, BigDecimal>) map.get("qualifyMap");
	    Map<String,BigDecimal> inviteMap = (Map<String, BigDecimal>) map.get("inviteMap");
	    Map<String,BigDecimal> winMap = (Map<String, BigDecimal>) map.get("winMap");
	    GradeSetUpCollection grades = (GradeSetUpCollection) map.get("grades");
	    Iterator<GradeSetUpInfo> gradeIterator = null;
	    GradeSetUpInfo gradeSetupInfo = null;
	    BigDecimal rate = FDCHelper.ZERO;
	    BigDecimal count = FDCHelper.ZERO;
	    String key = null;
	    Map<String,Integer> parentRowIndexMap = new HashMap<String,Integer>();
	    int parentIndex = -1;
	    	    
	    for(Iterator<InviteTypeInfo> it = inviteTypes.iterator();it.hasNext();){
	    	row = this.tblSupplier.addRow();
	    	inviteType = it.next();
	    	if(!inviteType.isIsLeaf()){
	    		row.setUserObject("isNotLeaf");
	    		parentRowIndexMap.put(inviteType.getId().toString(),row.getRowIndex());
	    		row.getStyleAttributes().setBackground(new Color(113,208,255));
	    	}else{
	    		if(inviteType.getParent()!=null){
	    			parentIndex = parentRowIndexMap.get(inviteType.getParent().getId().toString());
	    		}
	    	}
	    	
	    	key = inviteType.getId().toString();
	    	row.setTreeLevel(inviteType.getLevel());
	    	row.getCell("inviteType").setValue(inviteType.isIsLeaf()?"       "+inviteType.getName():inviteType.getName());
	    	
	    	
	    	if(countMap.containsKey(inviteType.getId().toString())){
	    		row.getCell("supplierQuanty").setValue(countMap.get(key));
	    		addCurrentValueToParent(parentIndex,"supplierQuanty",FDCHelper.toBigDecimal(countMap.get(key)));
	    		rate =FDCHelper.toBigDecimal(countMap.get(key)).divide(FDCHelper.toBigDecimal(totalCount),8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED);
	    		row.getCell("percentOfSupplier").setValue(rate);
	    	}
	    	
	    	for(gradeIterator = grades.iterator();gradeIterator.hasNext();){
	    		gradeSetupInfo = gradeIterator.next();
	    		key = inviteType.getId().toString()+"_"+gradeSetupInfo.getId().toString();
	    		if(gradeMap.containsKey(key)){
	    			count = gradeMap.get(key);
	    			row.getCell(gradeSetupInfo.getId().toString()).setValue(count);
		    		addCurrentValueToParent(parentIndex,gradeSetupInfo.getId().toString(),count);
	    			rate =count.divide(FDCHelper.toBigDecimal(row.getCell("supplierQuanty").getValue()),8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED);
	    			 logger.error(inviteType+":  totalCount:"+totalCount+"count:"+count+"rate:"+rate);
	    			row.getCell("percentOf"+gradeSetupInfo.getId().toString()).setValue(rate);
	    		}
	    	}
	    	key = inviteType.getId().toString();
	    	row.getCell("approveOfYear").setValue(yearedApprovedMap.get(key));
	    	addCurrentValueToParent(parentIndex,"approveOfYear",yearedApprovedMap.get(key));
	    	
	    	row.getCell("validateOfYear").setValue(yearedValidateMap.get(key));
	    	addCurrentValueToParent(parentIndex,"validateOfYear",yearedValidateMap.get(key));
	    	row.getCell("percentOfApprove").setValue(rateOfPassMap.get(key));
	    	
	    	row.getCell("newQualifyCount").setValue(qualifyMap.get(key));
	    	addCurrentValueToParent(parentIndex,"newQualifyCount",qualifyMap.get(key));
	    	rate = countMap.get(key) == null ? null:qualifyMap.get(key) != null ?(qualifyMap.get(key).divide(FDCHelper.toBigDecimal(countMap.get(key)),8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED)):null;
	    	row.getCell("newQualifyPercent").setValue(rate);
	    	
	    	row.getCell("newInviteCount").setValue(inviteMap.get(key));
	    	addCurrentValueToParent(parentIndex,"newInviteCount",inviteMap.get(key));
	    	rate = countMap.get(key) == null ? null:inviteMap.get(key) != null ?(inviteMap.get(key).divide(FDCHelper.toBigDecimal(countMap.get(key)),8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED)):null;
	    	row.getCell("newInvitePercent").setValue(rate);
	    	
	    	row.getCell("newWinCount").setValue(winMap.get(key));
	    	addCurrentValueToParent(parentIndex,"newWinCount",winMap.get(key));
	    	rate = countMap.get(key) == null ? null:winMap.get(key) != null ?(winMap.get(key).divide(FDCHelper.toBigDecimal(countMap.get(key)),8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED)):null;
	    	row.getCell("newWinPercent").setValue(rate);
	    	
	    }
	    
	    if(!parentRowIndexMap.isEmpty()){
	    	//处理非明细行的比率部分
	    	
	    	Collection<Integer> c = parentRowIndexMap.values();
	    	IRow r = null;
	    	String colKey = null;
	    	BigDecimal currentTotal = FDCHelper.ZERO;
	    	for(Integer i: c){
	    	 r = tblSupplier.getRow(i);
	    	 if(r.getCell("supplierQuanty").getValue() != null){
	    		 currentTotal = FDCHelper.toBigDecimal(r.getCell("supplierQuanty").getValue());
	    		 for(int j=0;j<this.tblSupplier.getColumnCount();j++){
	    			 colKey = tblSupplier.getColumn(j).getKey();
	    			 if(colKey.contains("Percent") || colKey.contains("percent") ||colKey.equals("newInvitePercent") ){
	    				 
	    				 rate = currentTotal.compareTo(FDCHelper.ZERO) == 0 ? null:r.getCell(j-1).getValue() != null ?(FDCHelper.toBigDecimal(r.getCell(j-1).getValue()).divide(currentTotal,8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED)):null;
	    				 r.getCell(j).setValue(rate);
	    			 } 
	    			 
	    		 }
	    		 r.getCell("percentOfSupplier").setValue(FDCHelper.toBigDecimal(r.getCell("supplierQuanty").getValue()).divide(FDCHelper.toBigDecimal(totalCount),8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
	    	 }
	    	}
	    	
	    	
	    }
	    
		
	}
	
	
	private void addCurrentValueToParent(int parentIndex,String columnKey,BigDecimal val){
		if(parentIndex != -1 && val != null){
			IRow r = this.tblSupplier.getRow(parentIndex);
			if(r.getCell(columnKey)!=null ){
				BigDecimal amount = r.getCell(columnKey).getValue()!= null ? FDCHelper.toBigDecimal(r.getCell(columnKey).getValue()).add(val):val;
				r.getCell(columnKey).setValue(amount);
			}
		}
		
	}
	
	
	public static IRow appendFootrow(KDTable table, String[] fields)
    {
      int size = fields.length;
      if (size == 0) {
        return null;
      }
      Map sumValue = new HashMap();
      
      int count = table.getRowCount3();
      for (int i = 0; i < fields.length; i++) {
        sumValue.put(fields[i], new BigDecimal("0.00"));
      }
      IRow footRow = null;
      KDTFootManager footManager = table.getFootManager();
      if (footManager == null)
      {
        footManager = new KDTFootManager(table);
        footManager.addFootView();
        table.setFootManager(footManager);
      }
      footRow = footManager.getFootRow(0);
      for (int i = 0; i < count; i++)
      {
        IRow row = table.getRow(i);
        if(row.getUserObject()!= null && row.getUserObject().equals("isNotLeaf")){
        	continue;
        }
        for (int j = 0; j < fields.length; j++) {
          sumValueForCell(row, fields[j], sumValue);
        }
      }
      if (footRow == null) {
        footRow = footManager.addFootRow(0);
      }
      String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
      
      table.getIndexColumn().setWidthAdjustMode((short)1);
      table.getIndexColumn().setWidth(30);
      footManager.addIndexText(0, total);
      footRow.getStyleAttributes().setBackground(new Color(246, 246, 191));
      for (int i = 0; i < size; i++)
      {
        String colName = fields[i];
        
        footRow.getCell(colName).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
        footRow.getCell(colName).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
        
        footRow.getCell(colName).getStyleAttributes().setFontColor(Color.black);
      }
      for (int i = 0; i < fields.length; i++) {
        footRow.getCell(fields[i]).setValue(sumValue.get(fields[i]));
      }
      return footRow;
    }
    
    private static void sumValueForCell(IRow row, String key, Map sumValue)
    {
      ICell cell = row.getCell(key);
      if (cell != null)
      {
        Object obj = cell.getValue();
        if (obj != null)
        {
          BigDecimal keyValue = (BigDecimal)sumValue.get(key);
          keyValue = keyValue.add(new BigDecimal(obj.toString()));
          sumValue.put(key, keyValue);
        }
      }
    }
	

}