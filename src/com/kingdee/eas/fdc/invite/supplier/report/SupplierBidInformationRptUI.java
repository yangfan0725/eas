/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.report;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.InviteTypeCollection;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAnnualSummaryFacadeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierBidInfo;

/**
 * output class name
 */
public class SupplierBidInformationRptUI extends AbstractSupplierBidInformationRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierBidInformationRptUI.class);
    
    /**
     * output class constructor
     */
    public SupplierBidInformationRptUI() throws Exception
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
     * output krdInviteType_stateChanged method
     */
    protected void krdInviteType_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
        super.krdInviteType_stateChanged(e);
        
        
    }

    /**
     * output prmtInviteType_dataChanged method
     */
    protected void prmtInviteType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        super.prmtInviteType_dataChanged(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }
    
    public void actionSearch_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
    	super.actionSearch_actionPerformed(e);
    	this.tblBidInfo.removeRows();
    	Map<String,Object> paramMap = new HashMap<String,Object>();
    	if(krdInviteType.isSelected()&& this.prmtInviteType.getValue()!=null){
    		paramMap.put("inviteTypeId", ((InviteTypeInfo)this.prmtInviteType.getValue()).getId().toString());
    	}
    	
    	paramMap = SupplierAnnualSummaryFacadeFactory.getRemoteInstance().getSupplierBidInfo(paramMap);
    	//填充所有的InviteType
    	
    	
    	fillTable(paramMap);
    }
    
    private void fillTable(Map map) {
		FDCTableHelper.disableDelete(this.tblBidInfo);
		this.tblBidInfo.setEnabled(false);
	    IRow row = null;
	    IRow supplierRow =null;
	    InviteTypeInfo inviteType = null;
	    String key = null;
	   
	    Map<String,BigDecimal> inviteCountMap = (Map<String, BigDecimal>) map.get("inviteCount");
	    Map<String,BigDecimal> inviteAmtMap = (Map<String, BigDecimal>) map.get("inviteAmt");
	    Map<String,BigDecimal> directAmtMap = (Map<String, BigDecimal>) map.get("directAmt");
	    Map<String,List<SupplierBidInfo>> supplierListMap =  (Map<String, List<SupplierBidInfo>>) map.get("supplierListMap");
	    List<SupplierBidInfo> supplierList = null;
	    //获取所有的inviteType
	    InviteTypeCollection itc =  (InviteTypeCollection) map.get("inviteType");
	    Map<String,Integer> parentRowIndexMap = new HashMap<String,Integer>();
	    Map<String,Integer> inviteTyepRowIndexMap = new HashMap<String, Integer>();
	    String parentkey = null;
	    int parentIndex = -1;
	    int ownerINdex = -1;	    
	    
	    for(Iterator<InviteTypeInfo> itcIter = itc.iterator();itcIter.hasNext();){
	    	row = this.tblBidInfo.addRow();
	    	row.setUserObject("isInviteType");
	    	ownerINdex = row.getRowIndex();
	    	inviteType = itcIter.next();
	    	if(!inviteType.isIsLeaf()){
	    		row.setUserObject("isNotLeaf");
	    		parentRowIndexMap.put(inviteType.getId().toString(),row.getRowIndex());
	    		row.getStyleAttributes().setBackground(new Color(113,208,255));
	    	}else{
	    		if(inviteType.getParent()!=null && inviteType.isIsLeaf()){
	    			parentkey = inviteType.getParent().getId().toString();
	    			if(parentRowIndexMap.containsKey(parentkey)){
	    				parentIndex = parentRowIndexMap.get(parentkey);
	    			}
	    		}
	    	}
	    	
	    	key = inviteType.getId().toString();
	    	inviteTyepRowIndexMap.put(key, row.getRowIndex());
	    	row.getCell("inviteTypeId").setValue(inviteType.getId());
	    	row.getCell("inviteTypeId").setUserObject(inviteType);
	    	row.getCell("inviteType").setValue(inviteType.isIsLeaf()?"     "+inviteType.getName():inviteType.getName());
	    	if(!inviteType.isIsLeaf()){
	    		row.getStyleAttributes().setBackground(new Color(113,208,255));
	    	}
	    	if(inviteCountMap.containsKey(key)){
	    		row.getCell("inviteCount").setValue(inviteCountMap.get(key));
	    	}
	    	if(inviteAmtMap.containsKey(key)){
	    		row.getCell("inviteAmt").setValue(inviteAmtMap.get(key));
	    	}
	    	if(directAmtMap.containsKey(key)){
	    		row.getCell("directAmt").setValue(directAmtMap.get(key));
	    	}
	    	
	    	supplierList = (List<SupplierBidInfo>) supplierListMap.get(key);
	    	if(supplierList == null ||supplierList.isEmpty()){
	    		continue;
	    	}
	    	int size = supplierList.size();
	    	
	    	for(int i=0;i<size;i++){
	    		SupplierBidInfo b= supplierList.get(i);
	    		supplierRow = this.tblBidInfo.addRow();
	    		supplierRow.setUserObject("isSupplier");
	    		supplierRow.getCell("inviteTypeId").setValue(b.getInviteTypeId());
	    		supplierRow.getCell("inviteTypeId").setUserObject(b);
	    		supplierRow.getCell("supplierName").setValue(b.getSupplierName());
	    		supplierRow.getCell("supplierGrade").setValue(b.getGrade());
	    		supplierRow.getCell("supplierScore").setValue(b.getScore());
	    		supplierRow.getCell("supplierQualifyCount").setValue(b.getQualificationCount());
	    		addCurrentValueToParent(ownerINdex,"supplierQualifyCount",b.getQualificationCount());
	    		
	    		supplierRow.getCell("normal").setValue(b.getNoneCount());
	    		addCurrentValueToParent(ownerINdex,"normal",b.getNoneCount());
	    		
	    		supplierRow.getCell("highestPrice").setValue(b.getHighCount());
	    		addCurrentValueToParent(ownerINdex,"highestPrice",b.getHighCount());
	    		
	    		supplierRow.getCell("waiver").setValue(b.getGiveUpCount());
	    		addCurrentValueToParent(ownerINdex,"waiver",b.getGiveUpCount());
	    		
	    		supplierRow.getCell("technicalNotPass").setValue(b.getUnQualifed());
	    		addCurrentValueToParent(ownerINdex,"technicalNotPass",b.getUnQualifed());
	    		
	    		supplierRow.getCell("outOfLine").setValue(b.getIllegalCount());
	    		addCurrentValueToParent(ownerINdex,"outOfLine",b.getIllegalCount());
	    		
	    		supplierRow.getCell("winCount").setValue(b.getWinCount());
	    		addCurrentValueToParent(ownerINdex,"winCount",b.getWinCount());
	    		
	    		supplierRow.getCell("lowestWinCount").setValue(b.getLowestWinCount());
	    		addCurrentValueToParent(ownerINdex,"lowestWinCount",b.getLowestWinCount());
	    	
	    		supplierRow.getCell("winAmt").setValue(b.getWinAmt());
	    		addCurrentValueToParent(ownerINdex,"winAmt",b.getWinAmt());
	    		
	    		supplierRow.getCell("directCount").setValue(b.getDirectCount());
	    		addCurrentValueToParent(ownerINdex,"directCount",b.getDirectCount());
	    		
	    		supplierRow.getCell("directWinAmt").setValue(b.getDirectAmt());
	    		addCurrentValueToParent(ownerINdex,"directWinAmt",b.getDirectAmt());
	    		
	    		
	    		//计算供应商综合得分  考察评估得分*10+年度评分*30%+履约评分*60 
	    		//如果没有年度，则等于考察*10+履约90
	    		//如果只有考察得分，则取最新的考察评分 
	    		BigDecimal score = FDCHelper.ZERO;
	    		if(b.getYearScore() != null && b.getCheckScore() != null && b.getExecuteScore() != null ){
	    			score =b.getCheckScore().multiply(FDCHelper.toBigDecimal("0.1").add(b.getYearScore().multiply(FDCHelper.toBigDecimal("0.3")).add(b.getExecuteScore().max(FDCHelper.toBigDecimal("0.6")))));
	    		}else if(b.getCheckScore() != null && b.getExecuteScore() != null && b.getYearScore() == null){
	    			score =b.getCheckScore().multiply(FDCHelper.toBigDecimal("0.1").add(b.getExecuteScore().max(FDCHelper.toBigDecimal("0.9"))));
	    		}else if(b.getCheckScore() != null && b.getYearScore() == null && b.getExecuteScore() == null){
	    			score =b.getCheckScore();
	    		}
	    		if(score.compareTo(FDCHelper.ZERO)>0){
	    			supplierRow.getCell("supplierScore").setValue(score.divide(FDCHelper.toBigDecimal("5")).multiply(FDCHelper.ONE_HUNDRED));
	    		}
	    		
	    	}
	    	
	    	
	    }
	    
	    int count = tblBidInfo.getRowCount();
	    if(count > 0){
	    	
	    	//处理入围率
	    	//采购类型的行 查找出采购类的父行，然后算出比例
	    	//供应商类型 查找出供应商所属的采购类型，用当前行的信息/采购类型所在行的比率 
	    	//处理中标率采用一样的策略
	    	
	    	InviteTypeInfo ti = null;
	    	SupplierBidInfo bi = null;
	    	String rowType = null;
	    	BigDecimal rwAmt = FDCHelper.ZERO;
	    	BigDecimal pAmt = FDCHelper.ZERO;
	    	BigDecimal winAmt = FDCHelper.ZERO;
	    	BigDecimal directAmt = FDCHelper.ZERO;
	    	for(int j=0;j < count; j++ ){
	    		
	    		row = tblBidInfo.getRow(j);
	    		rowType = (String) row.getUserObject();
	    		if("isInviteType".equals(rowType)){
	    			//在map中找出上级所在行，然后去计算出比例
	    			ti =  (InviteTypeInfo) row.getCell("inviteTypeId").getUserObject();
	    			if(ti.isIsLeaf()){
	    				if(parentRowIndexMap.containsKey(ti.getId().toString())){
	    					int pIndex  =	parentRowIndexMap.get(ti.getId().toString());
	    					pAmt = tblBidInfo.getRow(pIndex).getCell("supplierQualifyCount") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(tblBidInfo.getRow(pIndex).getCell("supplierQualifyCount").getValue());
	    					rwAmt = row.getCell("supplierQualifyCount") == null ? FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("supplierQualifyCount").getValue());
	    					row.getCell("supplierQualifyRate").setValue(pAmt.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:(rwAmt.divide(pAmt, 8, RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED)));
	    				    
	    					rwAmt = row.getCell("winCount") == null ? FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("winCount").getValue());
	    					row.getCell("winRate").setValue(pAmt.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:(rwAmt.divide(pAmt, 8, RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED)));
	    					
	    					pAmt = tblBidInfo.getRow(pIndex).getCell("winAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(tblBidInfo.getRow(pIndex).getCell("winAmt").getValue());
	    					rwAmt = row.getCell("winAmt") == null ? FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("winAmt").getValue());
	    					row.getCell("winAmtRate").setValue(pAmt.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:(rwAmt.divide(pAmt, 8, RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED)));
	    					
	    					pAmt = tblBidInfo.getRow(pIndex).getCell("directWinAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(tblBidInfo.getRow(pIndex).getCell("directWinAmt").getValue());
	    					rwAmt = row.getCell("directWinAmt") == null ? FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("directWinAmt").getValue());
	    					row.getCell("directAmtRate").setValue(pAmt.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:(rwAmt.divide(pAmt, 8, RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED)));
	    					
	    					winAmt = tblBidInfo.getRow(pIndex).getCell("winAmt") == null? FDCHelper.ZERO:FDCHelper.toBigDecimal(tblBidInfo.getRow(pIndex).getCell("winAmt").getValue());
	    	    			directAmt =tblBidInfo.getRow(pIndex).getCell("directWinAmt") == null? FDCHelper.ZERO:FDCHelper.toBigDecimal(tblBidInfo.getRow(pIndex).getCell("directWinAmt").getValue());
	    	    			pAmt = directAmt.add(winAmt);
	    	    			rwAmt = rwAmt.add(row.getCell("winAmt") == null ? FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("winAmt").getValue()));
	    					row.getCell("winRateInType").setValue(pAmt.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:(rwAmt.divide(pAmt, 8, RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED)));
	    				}
	    			}
	    			
	    		}else if("isSupplier".equals(rowType)){
	    			bi = (SupplierBidInfo) row.getCell("inviteTypeId").getUserObject();
	    			int rowIndex = inviteTyepRowIndexMap.get(bi.getInviteTypeId());
	    			pAmt = tblBidInfo.getRow(rowIndex).getCell("supplierQualifyCount") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(tblBidInfo.getRow(rowIndex).getCell("supplierQualifyCount").getValue());
	    			rwAmt = row.getCell("supplierQualifyCount") == null ? FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("supplierQualifyCount").getValue());
	    			row.getCell("supplierQualifyRate").setValue(pAmt.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:(rwAmt.divide(pAmt, 8, RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED)));
	    			
	    			rwAmt = row.getCell("winCount") == null ? FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("winCount").getValue());
	    			row.getCell("winRate").setValue(pAmt.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:(rwAmt.divide(pAmt, 8, RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED)));
	    			
	    			pAmt = tblBidInfo.getRow(rowIndex).getCell("winAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(tblBidInfo.getRow(rowIndex).getCell("winAmt").getValue());
	    			rwAmt = row.getCell("winAmt") == null ? FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("winAmt").getValue());
	    			row.getCell("winAmtRate").setValue(pAmt.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:(rwAmt.divide(pAmt, 8, RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED)));
	    			
	    			pAmt = tblBidInfo.getRow(rowIndex).getCell("directAmt") == null ?FDCHelper.ZERO:FDCHelper.toBigDecimal(tblBidInfo.getRow(rowIndex).getCell("directAmt").getValue());
	    			rwAmt = row.getCell("directWinAmt").getValue() == null ? FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("directWinAmt").getValue());
	    			row.getCell("directAmtRate").setValue(pAmt.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:(rwAmt.divide(pAmt, 8, RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED)));
	    			
	    			
	    			winAmt = tblBidInfo.getRow(rowIndex).getCell("winAmt") == null? FDCHelper.ZERO:FDCHelper.toBigDecimal(tblBidInfo.getRow(rowIndex).getCell("winAmt").getValue());
	    			directAmt =tblBidInfo.getRow(rowIndex).getCell("directWinAmt") == null? FDCHelper.ZERO:FDCHelper.toBigDecimal(tblBidInfo.getRow(rowIndex).getCell("directWinAmt").getValue());
	    			pAmt = directAmt.add(winAmt);
	    			rwAmt = rwAmt.add(row.getCell("winAmt") == null ? FDCHelper.ZERO:FDCHelper.toBigDecimal(row.getCell("winAmt").getValue()));
	    			row.getCell("winRateInType").setValue(pAmt.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:(rwAmt.divide(pAmt, 8, RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED)));
	    		}
	    	}
	    	
	    }
	   
    }

    
    private void addCurrentValueToParent(int parentIndex,String columnKey,BigDecimal val){
		if(parentIndex != -1 && val != null){
			IRow r = this.tblBidInfo.getRow(parentIndex);
			if(r.getCell(columnKey)!=null ){
				BigDecimal amount = r.getCell(columnKey).getValue()!= null ? FDCHelper.toBigDecimal(r.getCell(columnKey).getValue()).add(val):val;
				r.getCell(columnKey).setValue(amount);
			}
		}
		
	}

}