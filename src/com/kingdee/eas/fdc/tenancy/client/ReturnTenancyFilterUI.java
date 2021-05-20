/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.Date;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class ReturnTenancyFilterUI extends AbstractReturnTenancyFilterUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ReturnTenancyFilterUI.class);

	/**
	 * output class constructor
	 */
	public ReturnTenancyFilterUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	@Override
	public RptParams getCustomCondition() {
		// TODO Auto-generated method stub
		RptParams params = new RptParams();

		Date sd = (Date) this.pkStartDate.getValue();
		Date ed = (Date) this.pkEndDate.getValue();

		params.setObject("startDate", sd);
		params.setObject("endDate", ed);

		return params;
	}

	@Override
	public void onInit(RptParams params) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void setCustomCondition(RptParams params) {
		// TODO Auto-generated method stub
		if (params.getObject("startDate") != null) {
           Date sd = (Date)params.getObject("startDate");
           this.pkStartDate.setValue(sd);
		}

		if (params.getObject("endDate") != null) {
			Date ed = (Date) params.getObject("endDate");
			this.pkEndDate.setValue(ed);
		}
	}

}