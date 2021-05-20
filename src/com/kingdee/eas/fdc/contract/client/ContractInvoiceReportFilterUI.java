/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public class ContractInvoiceReportFilterUI extends AbstractContractInvoiceReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractInvoiceReportFilterUI.class);
    public ContractInvoiceReportFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
		this.cbInvoiceType.setSelectedItem(null);
    }
	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         pp.setObject("fromDate", this.pkFromDate.getValue());
         pp.setObject("toDate", this.pkToDate.getValue());
		 pp.setObject("invoiceType", this.cbInvoiceType.getSelectedItem());
		 pp.setObject("saleBuyName", this.txtSaleBuyName.getText());
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.pkFromDate.setValue(params.getObject("fromDate"));
		this.pkToDate.setValue(params.getObject("toDate"));
		this.cbInvoiceType.setSelectedItem(params.getObject("invoiceType"));
		this.txtSaleBuyName.setText(params.getString("saleBuyName"));
	}

}