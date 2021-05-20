/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractContractFullListUIHandler extends com.kingdee.eas.framework.app.BillListUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAntiAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAntiAudit(request,response,context);
	}
	protected void _handleActionAntiAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewContent(request,response,context);
	}
	protected void _handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionContractSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionContractSplit(request,response,context);
	}
	protected void _handleActionContractSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddChangeAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddChangeAudit(request,response,context);
	}
	protected void _handleActionAddChangeAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddContractSettlement(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddContractSettlement(request,response,context);
	}
	protected void _handleActionAddContractSettlement(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddPayRequest(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddPayRequest(request,response,context);
	}
	protected void _handleActionAddPayRequest(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionPaymentListUI(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionPaymentListUI(request,response,context);
	}
	protected void _handleActionPaymentListUI(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}