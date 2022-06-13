/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;

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
public class MarketProjectReportFilterUI extends AbstractMarketProjectReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketProjectReportFilterUI.class);
    
    /**
     * output class constructor
     */
    public MarketProjectReportFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
		this.cbIsYE.setSelectedItem(null);
		this.pkFromBizDate.setValue(null);
		this.pkToBizDate.setValue(null);
		this.pkFromConBizDate.setValue(null);
		this.pkToConBizDate.setValue(null);
		this.cbContract.setSelected(false);
		this.cbWithoutContract.setSelected(false);
		this.cbJZ.setSelected(false);
	}

	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
		 pp.setObject("fromDate", this.pkFromDate.getValue());
		 pp.setObject("toDate", this.pkToDate.getValue());
		 pp.setObject("isYE", this.cbIsYE.getSelectedItem());
		 pp.setObject("fromBizDate", this.pkFromBizDate.getValue());
		 pp.setObject("toBizDate", this.pkToBizDate.getValue());
		 pp.setObject("fromConBizDate", this.pkFromConBizDate.getValue());
		 pp.setObject("toConBizDate", this.pkToConBizDate.getValue());
		 pp.setObject("isContract", this.cbContract.isSelected());
		 pp.setObject("isWithoutContract", this.cbWithoutContract.isSelected());
		 pp.setObject("isJZ", this.cbJZ.isSelected());
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.pkFromDate.setValue(params.getObject("fromDate"));
		this.pkToDate.setValue(params.getObject("toDate"));
		this.cbIsYE.setSelectedItem(params.getObject("isYE"));
		this.pkFromBizDate.setValue(params.getObject("fromBizDate"));
		this.pkToBizDate.setValue(params.getObject("toBizDate"));
		this.pkFromConBizDate.setValue(params.getObject("fromConBizDate"));
		this.pkToConBizDate.setValue(params.getObject("toConBizDate"));
		this.cbContract.setSelected(params.getBoolean("isContract"));
		this.cbWithoutContract.setSelected(params.getBoolean("isWithoutContract"));
		this.cbJZ.setSelected(params.getBoolean("isJZ"));
	}
}