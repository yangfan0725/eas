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
public abstract class AbstractTenderInfoEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionApproveReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionApproveReport(request,response,context);
	}
	protected void _handleActionApproveReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRejectReport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRejectReport(request,response,context);
	}
	protected void _handleActionRejectReport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}