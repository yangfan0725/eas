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
public abstract class AbstractCostAccountListUIHandler extends com.kingdee.eas.framework.app.TreeDetailListUIHandler

{
	public void handleActionAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAssign(request,response,context);
	}
	protected void _handleActionAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionDisAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionDisAssign(request,response,context);
	}
	protected void _handleActionDisAssign(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImport(request,response,context);
	}
	protected void _handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionProjectAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionProjectAttachment(request,response,context);
	}
	protected void _handleActionProjectAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionTemplateImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionTemplateImport(request,response,context);
	}
	protected void _handleActionTemplateImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEnterDB(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEnterDB(request,response,context);
	}
	protected void _handleActionEnterDB(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCancelEnterDB(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCancelEnterDB(request,response,context);
	}
	protected void _handleActionCancelEnterDB(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAssignToOrg(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAssignToOrg(request,response,context);
	}
	protected void _handleActionAssignToOrg(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}