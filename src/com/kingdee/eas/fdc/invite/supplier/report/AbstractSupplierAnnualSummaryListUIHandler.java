/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.report;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractSupplierAnnualSummaryListUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionSearchSupplier(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSearchSupplier(request,response,context);
	}
	protected void _handleActionSearchSupplier(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}