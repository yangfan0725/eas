/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.report;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.WinRptInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAnnualSummaryFacadeFactory;

/**
 * output class name
 */
public class SupplierWinInfoRptUI extends AbstractSupplierWinInfoRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierWinInfoRptUI.class);
    
    /**
     * output class constructor
     */
    public SupplierWinInfoRptUI() throws Exception
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
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	FDCTableHelper.disableDelete(this.kDTable1);
		this.kDTable1.setEnabled(false);
    	query();
    	
    }
    
    private void query() throws Exception{
    	
    	//调用 后台取数据接口获取信息
    	Map<String,Object> param =  null;
    	Map<String,Object> result = new HashMap<String, Object>();
    	result = SupplierAnnualSummaryFacadeFactory.getRemoteInstance().getSupplierWinInfo(null);
    	
    	fillTable(result);
    }
    
    public void actionWinRefresh_actionPerformed(ActionEvent e)
    		throws Exception {
    	query();
    }
    
	private void fillTable(Map<String, Object> result) {
		this.kDTable1.removeRows();
		this.kDTable1.checkParsed();
		List<WinRptInfo> inviteProject = (List<WinRptInfo>) result.get("inviteProjectList");
		Map<String,BigDecimal[]> bidAmtMap = (Map<String, BigDecimal[]>) result.get("bidAmtMap");
		Map<String,BigDecimal[]> selectedBidMap = (Map<String, BigDecimal[]>) result.get("selectedBidMap");
		Map<String,BigDecimal[]> inquiryBidMap = (Map<String, BigDecimal[]>) result.get("inquiryBidMap");
		Map<String,BigDecimal[]> uniqueBidMap = (Map<String, BigDecimal[]>) result.get("uniqueBidMap");
		Map<String,BigDecimal[]> inviteTimesMap = (Map<String, BigDecimal[]>) result.get("inviteTimesMap");
		Map<String,BigDecimal[]> lowestBidMap = (Map<String, BigDecimal[]>) result.get("lowestBidMap");
		Map<String,BigDecimal[]> firstSignMap = (Map<String, BigDecimal[]>) result.get("firstSignMap");
		Map<String,BigDecimal[]> firstwinbyinvite = (Map<String, BigDecimal[]>) result.get("firstwinbyinvite");
		Map<String,BigDecimal[]> firstwinbyunique = (Map<String, BigDecimal[]>) result.get("firstwinbyunique");
		int size = inviteProject.size();
		
		WinRptInfo prj = null;
		String key = null;
		IRow row = null;
		Set<String> alreadyFilled = new HashSet<String>();
		BigDecimal amt = FDCHelper.ZERO;
		BigDecimal amtByType = FDCHelper.ZERO;
		BigDecimal [] countArray = null;
		BigDecimal baseCount = FDCHelper.ZERO;
		BigDecimal baseAmt = FDCHelper.ZERO;
 		
		for(int i=0;i<size;i++){
			prj = inviteProject.get(i);
			key = prj.getPc().getId().toString()+"_"+prj.getIt().getId().toString();
			
			row = this.kDTable1.addRow();
			if(prj.getPc() != null){
				row.getCell("puchaseCatagory").setValue(prj.getPc().getName());
				row.getCell("puchaseCatagory").setUserObject(prj.getPc());
			}
			
			row.getCell("inviteType").setValue(prj.getIt().getName());
			row.getCell("inviteType").setUserObject(prj.getIt());
			
			countArray = bidAmtMap.get(key);
			if(countArray != null ){
				amtByType = countArray[0];
				if(countArray[1] != null){
					prj.setCount(countArray[1].intValue());
				}
				row.getCell("inviteCount").setValue(prj.getCount());
				row.getCell("inviteAmt").setValue(amtByType);
			}
			
			/**邀请招标方式的相关数据**/
			
			countArray = selectedBidMap.get(key);
			if(countArray != null && countArray[0] != null){
				
				amt = countArray[0];
				baseAmt = amt;
				row.getCell("selectedAmt").setValue(amt);
				row.getCell("selectedAmtRate").setValue(amtByType==null?FDCHelper.ZERO:amt.divide(amtByType,8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}
			if(countArray != null && countArray[1] != null){
				amt = countArray[1];
				baseCount = amt;
				row.getCell("selectedCount").setValue(amt);
				row.getCell("selectedRate").setValue(prj.getCount()== 0?FDCHelper.ZERO:amt.divide(FDCHelper.toBigDecimal(prj.getCount()+""),8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}
			
			
			countArray = inviteTimesMap.get(key);
			if(countArray != null && countArray[0] != null){
				row.getCell("firstBid").setValue(countArray[0]);
			}
			
			if(countArray != null && countArray[1] != null){
				row.getCell("secondBid").setValue(countArray[1]);
			}
			
			if(countArray != null && countArray[2] != null){
				row.getCell("thirdBid").setValue(countArray[2]);
			}
			
			countArray =lowestBidMap.get(key);
			if(countArray != null && countArray[0] != null){
				amt = countArray[0];
				row.getCell("normalCount").setValue(amt);
				row.getCell("normalRate").setValue(baseCount.compareTo(FDCHelper.ZERO) == 0 ?FDCHelper.ZERO:amt.divide(baseCount,8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}
			if(countArray != null && countArray[1] != null){
				amt = countArray[1];
				row.getCell("normalAmt").setValue(amt);
				row.getCell("normalAmtRate").setValue(baseAmt.compareTo(FDCHelper.ZERO) == 0?FDCHelper.ZERO:amt.divide(baseAmt,8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
				
			}
			countArray =firstSignMap.get(key);
			if(countArray != null && countArray[0] != null){
				amt = countArray[0];
				row.getCell("firstSignCount").setValue(amt);
				row.getCell("firstSignRate").setValue(baseCount.compareTo(FDCHelper.ZERO)== 0?FDCHelper.ZERO:amt.divide(baseCount,8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}
			if(countArray != null && countArray[1] != null){
				amt = countArray[1];
				row.getCell("firstSignAmt").setValue(amt);
				row.getCell("firstSignAmtCount").setValue(baseAmt.compareTo(FDCHelper.ZERO) == 0?FDCHelper.ZERO:amt.divide(baseAmt,8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
				
			}
			
			
			
			/**询价比选方式的相关数据**/
			countArray = inquiryBidMap.get(key);
			if(countArray != null && countArray[0] != null){
				amt = countArray[0];
				baseAmt = amt;
				row.getCell("inquiryAmt").setValue(amt);
				row.getCell("inquiryAmtRate").setValue(amtByType==null?FDCHelper.ZERO:amt.divide(amtByType,8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}
			if(countArray != null && countArray[1] != null){
				amt = countArray[1];
				baseCount = amt;
				row.getCell("inquiryCount").setValue(amt);
				row.getCell("inquiryRate").setValue(prj.getCount()== 0?FDCHelper.ZERO:amt.divide(FDCHelper.toBigDecimal(prj.getCount()+""),8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}
			
			countArray =firstwinbyinvite.get(key);
			if(countArray != null && countArray[0] != null){
				amt = countArray[0];
				row.getCell("inquiryFirstBidCount").setValue(amt);
				row.getCell("inquiryFirstBidCRate").setValue(baseCount.compareTo(FDCHelper.ZERO)== 0?FDCHelper.ZERO:amt.divide(baseCount,8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}
			if(countArray != null && countArray[1] != null){
				amt = countArray[1];
				row.getCell("inquiryFirstBidAmt").setValue(amt);
				row.getCell("inquiryFirstBidAmtRate").setValue(baseAmt.compareTo(FDCHelper.ZERO)== 0?FDCHelper.ZERO:amt.divide(baseAmt,8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
				
			}
			
			
			/**单一性的相关数据**/
			countArray = uniqueBidMap.get(key);
			if(countArray != null && countArray[0] != null){
				amt = countArray[0];
				baseAmt = amt;
				row.getCell("uniqueAmt").setValue(amt);
				row.getCell("uniqueAmtRate").setValue(amtByType==null?FDCHelper.ZERO:amt.divide(amtByType,8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}
			if(countArray != null && countArray[1] != null){
				amt = countArray[1];
				baseCount = amt;
				row.getCell("uniqueCount").setValue(amt);
				row.getCell("uniqueRate").setValue(prj.getCount()== 0?FDCHelper.ZERO:amt.divide(FDCHelper.toBigDecimal(prj.getCount()+""),8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}
			
			
			
		
			countArray =firstwinbyunique.get(key);
			if(countArray != null && countArray[0] != null){
				amt = countArray[0];
				row.getCell("uniqueFirstBidCount").setValue(amt);
				row.getCell("uniqueFirstBidRate").setValue(baseCount.compareTo(FDCHelper.ZERO)== 0?FDCHelper.ZERO:amt.divide(baseCount,8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}
			if(countArray != null && countArray[1] != null){
				amt = countArray[1];
				row.getCell("uniqueFirstBidAmt").setValue(amt);
				row.getCell("uniqueFirstBidAmtRate").setValue(baseAmt.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:amt.divide(baseAmt,8,RoundingMode.HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
				
			}
			
		}
		
		
		this.kDTable1.getGroupManager().group();
	}

}