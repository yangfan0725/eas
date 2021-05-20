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
public abstract class AbstractSelectTenancyContractUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionSearch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSearch(request,response,context);
	}
	protected void _handleActionSearch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionReset(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionReset(request,response,context);
	}
	protected void _handleActionReset(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConfirm(request,response,context);
	}
	protected void _handleActionConfirm(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDCancel(request,response,context);
	}
	protected void _handleActionDCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}