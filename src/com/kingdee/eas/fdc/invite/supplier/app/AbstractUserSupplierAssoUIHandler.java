/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractUserSupplierAssoUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionSaveAsso(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSaveAsso(request,response,context);
	}
	protected void _handleActionSaveAsso(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMatch(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMatch(request,response,context);
	}
	protected void _handleActionMatch(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}