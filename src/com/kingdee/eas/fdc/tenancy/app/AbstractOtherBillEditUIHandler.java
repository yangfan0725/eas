/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractOtherBillEditUIHandler extends com.kingdee.eas.fdc.tenancy.app.TenBillBaseEditUIHandler

{
	public void handleActionAdjustTaxRate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAdjustTaxRate(request,response,context);
	}
	protected void _handleActionAdjustTaxRate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}