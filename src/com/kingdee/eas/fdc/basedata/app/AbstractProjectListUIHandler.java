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
public abstract class AbstractProjectListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionVersionRedact(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionVersionRedact(request,response,context);
	}
	protected void _handleActionVersionRedact(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEnabled(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEnabled(request,response,context);
	}
	protected void _handleActionEnabled(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDisEnabled(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisEnabled(request,response,context);
	}
	protected void _handleActionDisEnabled(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionIdxRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionIdxRefresh(request,response,context);
	}
	protected void _handleActionIdxRefresh(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSetProjectType(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSetProjectType(request,response,context);
	}
	protected void _handleActionSetProjectType(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}