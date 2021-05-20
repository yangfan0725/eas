/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractDeductListUIHandler extends com.kingdee.eas.framework.app.BillListUIHandler

{
	public void handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionOK(request,response,context);
	}
	protected void _handleActionOK(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionbtnCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionbtnCancel(request,response,context);
	}
	protected void _handleActionbtnCancel(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}