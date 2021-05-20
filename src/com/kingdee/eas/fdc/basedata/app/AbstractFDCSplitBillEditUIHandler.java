/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractFDCSplitBillEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionAcctSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAcctSelect(request,response,context);
	}
	protected void _handleActionAcctSelect(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSplitProj(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplitProj(request,response,context);
	}
	protected void _handleActionSplitProj(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSplitBotUp(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplitBotUp(request,response,context);
	}
	protected void _handleActionSplitBotUp(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSplitProd(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSplitProd(request,response,context);
	}
	protected void _handleActionSplitProd(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionImpContrSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImpContrSplit(request,response,context);
	}
	protected void _handleActionImpContrSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionViewCostInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionViewCostInfo(request,response,context);
	}
	protected void _handleActionViewCostInfo(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}