/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractInvoiceBillFullListUIHandler extends com.kingdee.eas.fdc.basecrm.app.FDCReceivingBillListUIHandler

{
	public void handleActionImportInoviceInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImportInoviceInfo(request,response,context);
	}
	protected void _handleActionImportInoviceInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}