/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.Calendar;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class MarketProjectFKReportFilterUI extends AbstractMarketProjectFKReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketProjectFKReportFilterUI.class);
    public MarketProjectFKReportFilterUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
		super.onLoad();
		Calendar c = Calendar.getInstance();
    	int year = c.get(Calendar.YEAR);
    	int month=c.get(Calendar.MONTH)+1;
    	SpinnerNumberModel m = new SpinnerNumberModel();
        m.setMaximum(year+100);
        m.setMinimum(year-100);
    	this.spFromYear.setModel(m);
    	this.spFromYear.setValue(c.get(Calendar.YEAR));
    	
    	m = new SpinnerNumberModel();
        m.setMaximum(year+100);
        m.setMinimum(year-100);
    	this.spToYear.setModel(m);
    	this.spToYear.setValue(c.get(Calendar.YEAR));
    	
    	m = new SpinnerNumberModel();
        m.setMaximum(12);
        m.setMinimum(1);
        
    	this.spFromMonth.setModel(m);
    	this.spFromMonth.setValue(month);
    	
    	m = new SpinnerNumberModel();
        m.setMaximum(12);
        m.setMinimum(1);
        
    	this.spToMonth.setModel(m);
    	this.spToMonth.setValue(month);
	}

	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         if(this.spFromYear.getValue()!=null){
    		 pp.setObject("fromYear", this.spFromYear.getValue());
         }else{
        	 MsgBox.showInfo("请选择年份！");
        	 SysUtil.abort();
         }
         if(this.spFromMonth.getValue()!=null){
    		 pp.setObject("fromMonth", this.spFromMonth.getValue());
         }else{
        	 MsgBox.showInfo("请选择月份！");
        	 SysUtil.abort();
         }
         if(this.spToYear.getValue()!=null){
    		 pp.setObject("toYear", this.spToYear.getValue());
         }else{
        	 MsgBox.showInfo("请选择年份！");
        	 SysUtil.abort();
         }
         if(this.spToMonth.getValue()!=null){
    		 pp.setObject("toMonth", this.spToMonth.getValue());
         }else{
        	 MsgBox.showInfo("请选择月份！");
        	 SysUtil.abort();
         }
         if(this.spFromYear.getIntegerVlaue().intValue()!=this.spToYear.getIntegerVlaue().intValue()){
        	 MsgBox.showInfo("请选择同一年！");
        	 SysUtil.abort();
         }
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.spFromYear.setValue(params.getObject("fromYear"));
		this.spFromMonth.setValue(params.getObject("fromMonth"));
		
		this.spToYear.setValue(params.getObject("toYear"));
		this.spToMonth.setValue(params.getObject("toMonth"));
	}

}