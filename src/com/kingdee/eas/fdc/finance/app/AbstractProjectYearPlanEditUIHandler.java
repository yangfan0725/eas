/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractProjectYearPlanEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionALine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionALine(request,response,context);
	}
	protected void _handleActionALine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionILine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionILine(request,response,context);
	}
	protected void _handleActionILine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRLine(request,response,context);
	}
	protected void _handleActionRLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}