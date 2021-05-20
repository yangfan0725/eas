/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.FDCRoomPromptDialog;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class CustomerTrackReportFilterUI extends AbstractCustomerTrackReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerTrackReportFilterUI.class);
    public CustomerTrackReportFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();

		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		
		this.pkFromDate.setValue(now);
		this.pkToDate.setValue(now);
		
	
    }
    public boolean verify()
    {
    	if(!this.cbWeek.isSelected()&&!this.cbMonth.isSelected()){
    		if(this.pkFromDate.getValue()==null){
        		FDCMsgBox.showWarning(this,"开始日期不能为空！");
        		return false;
        	}
        	if(this.pkToDate.getValue()==null){
        		FDCMsgBox.showWarning(this,"结束日期不能为空！");
        		return false;
        	}
        	if(((Date)this.pkFromDate.getValue()).compareTo((Date)this.pkToDate.getValue())>0){
        		FDCMsgBox.showWarning(this,"开始日期不能大于结束日期！");
        		return false;
        	}
    	}
        return true;
    }
	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         pp.setObject("fromDate", this.pkFromDate.getValue());
         pp.setObject("toDate", this.pkToDate.getValue());
         if(this.txtCustomer.getText()!=null
        		 &&!"".equals(this.txtCustomer.getText().trim())){
        	 pp.setObject("customer", this.txtCustomer.getText());
         }
         pp.setObject("isAll", this.cbIsAll.isSelected());
         pp.setObject("week", this.cbWeek.isSelected());
         pp.setObject("month", this.cbMonth.isSelected());
         pp.setObject("saleMan", this.prmtSaleMan.getValue());
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.pkFromDate.setValue(params.getObject("fromDate"));
		this.pkToDate.setValue(params.getObject("toDate"));
		this.txtCustomer.setText(params.getString("customer"));
		this.cbIsAll.setSelected(params.getBoolean("isAll"));
		this.cbWeek.setSelected(params.getBoolean("week"));
		this.cbMonth.setSelected(params.getBoolean("month"));
		this.prmtSaleMan.setValue(params.getObject("saleMan"));
	}
	protected void cbMonth_itemStateChanged(ItemEvent e) throws Exception {
		if(this.cbMonth.isSelected()){
			this.cbWeek.setSelected(false);
			this.pkFromDate.setValue(null);
			this.pkToDate.setValue(null);
			this.pkFromDate.setEnabled(false);
			this.pkToDate.setEnabled(false);
		}else if(!this.cbWeek.isSelected()){
			this.pkFromDate.setEnabled(true);
			this.pkToDate.setEnabled(true);
		}
	}
	protected void cbWeek_itemStateChanged(ItemEvent e) throws Exception {
		if(this.cbWeek.isSelected()){
			this.cbMonth.setSelected(false);
			this.pkFromDate.setValue(null);
			this.pkToDate.setValue(null);
			this.pkFromDate.setEnabled(false);
			this.pkToDate.setEnabled(false);
		}else if(!this.cbMonth.isSelected()){
			this.pkFromDate.setEnabled(true);
			this.pkToDate.setEnabled(true);
		}
	}
	
}