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
public class ContractBillReceiveTotalReportFilterUI extends AbstractContractBillReceiveTotalReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractBillReceiveTotalReportFilterUI.class);
    
    public ContractBillReceiveTotalReportFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.pkStartDate.setValue(null);
		this.pkEndDate.setValue(null);
	}

	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
		 pp.setObject("startDate", this.pkStartDate.getValue());
		 pp.setObject("endDate", this.pkEndDate.getValue());
		 pp.setObject("type", this.cbType.getSelectedItem());
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.pkStartDate.setValue(params.getObject("startDate"));
		this.pkEndDate.setValue(params.getObject("endDate"));
		
		this.cbType.setSelectedItem(params.getObject("type"));
	}
}