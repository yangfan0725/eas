/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractDelayPayBillEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleAuditAction(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleAuditAction(request,response,context);
	}
	protected void _handleAuditAction(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleUnAuditAction(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleUnAuditAction(request,response,context);
	}
	protected void _handleUnAuditAction(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRLine(request,response,context);
	}
	protected void _handleActionRLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionALine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionALine(request,response,context);
	}
	protected void _handleActionALine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}