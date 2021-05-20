/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.SpinnerNumberModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class CommissionReportFilterUI extends AbstractCommissionReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(CommissionReportFilterUI.class);
    private TreeSelectDialog dialog=new TreeSelectDialog(this,TimeAccountReportFilterUI.getSellProjectForSHESellProject(null, MoneySysTypeEnum.SalehouseSys,true));
    public CommissionReportFilterUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
		super.onLoad();
		Calendar c = Calendar.getInstance();
    	int year = c.get(Calendar.YEAR);
    	SpinnerNumberModel m = new SpinnerNumberModel();
        m.setMaximum(year+100);
        m.setMinimum(year-100);
    	this.spYear.setModel(m);
    	this.spYear.setValue(c.get(Calendar.YEAR));
	}

	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         if(this.spYear.getValue()!=null){
    		 pp.setObject("year", this.spYear.getValue());
         }else{
        	 MsgBox.showInfo("«Î—°‘ÒƒÍ∑›£°");
        	 SysUtil.abort();
         }
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.spYear.setValue(params.getObject("year"));
	}
}