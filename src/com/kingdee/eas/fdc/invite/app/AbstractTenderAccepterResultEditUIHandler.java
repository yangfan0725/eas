/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractTenderAccepterResultEditUIHandler extends com.kingdee.eas.fdc.invite.app.BaseInviteEditUIHandler

{
	public void handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewAttachment(request,response,context);
	}
	protected void _handleActionViewAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInviteListEntryALine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInviteListEntryALine(request,response,context);
	}
	protected void _handleActionInviteListEntryALine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInviteListEntryILine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInviteListEntryILine(request,response,context);
	}
	protected void _handleActionInviteListEntryILine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionInviteListEntryRLine(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInviteListEntryRLine(request,response,context);
	}
	protected void _handleActionInviteListEntryRLine(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}