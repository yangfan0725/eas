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
public abstract class AbstractWinInfoListUIHandler extends com.kingdee.eas.fdc.invite.app.BaseInviteListUIHandler

{
	public void handleActionPublish(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPublish(request,response,context);
	}
	protected void _handleActionPublish(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnPublish(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnPublish(request,response,context);
	}
	protected void _handleActionUnPublish(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}