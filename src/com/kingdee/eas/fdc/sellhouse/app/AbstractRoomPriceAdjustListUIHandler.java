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
public abstract class AbstractRoomPriceAdjustListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExecute(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExecute(request,response,context);
	}
	protected void _handleActionExecute(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewPriceHis(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewPriceHis(request,response,context);
	}
	protected void _handleActionViewPriceHis(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}