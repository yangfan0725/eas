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
public abstract class AbstractInvoiceBatchImportEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionSelectContract(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSelectContract(request,response,context);
	}
	protected void _handleActionSelectContract(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionExportInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionExportInvoice(request,response,context);
	}
	protected void _handleActionExportInvoice(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEditEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditEntry(request,response,context);
	}
	protected void _handleActionEditEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}