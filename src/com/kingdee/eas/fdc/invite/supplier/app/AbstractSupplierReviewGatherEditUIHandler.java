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
public abstract class AbstractSupplierReviewGatherEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionViewBPM(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewBPM(request,response,context);
	}
	protected void _handleActionViewBPM(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}