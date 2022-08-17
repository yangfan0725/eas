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
public abstract class AbstractSHEAttachUIHandler extends com.kingdee.eas.framework.report.app.CommRptBaseUIHandler

{
	public void handleActionAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAttachment(request,response,context);
	}
	protected void _handleActionAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEdit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEdit(request,response,context);
	}
	protected void _handleActionEdit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRemove(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemove(request,response,context);
	}
	protected void _handleActionRemove(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionWorkFlowG(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionWorkFlowG(request,response,context);
	}
	protected void _handleActionWorkFlowG(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAuditResult(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAuditResult(request,response,context);
	}
	protected void _handleActionAuditResult(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}