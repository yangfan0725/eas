/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractProjectEditUIHandler extends com.kingdee.eas.framework.app.EditUIHandler

{
	public void handleActionProductAddLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProductAddLine(request,response,context);
	}
	protected void _handleActionProductAddLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionProductInsertLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProductInsertLine(request,response,context);
	}
	protected void _handleActionProductInsertLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionProductDelLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProductDelLine(request,response,context);
	}
	protected void _handleActionProductDelLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}