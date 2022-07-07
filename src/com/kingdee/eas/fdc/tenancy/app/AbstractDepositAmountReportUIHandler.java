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
public abstract class AbstractDepositAmountReportUIHandler extends com.kingdee.eas.framework.report.app.CommRptBaseUIHandler

{
	public void handleActionGen(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionGen(request,response,context);
	}
	protected void _handleActionGen(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionView(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionView(request,response,context);
	}
	protected void _handleActionView(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}