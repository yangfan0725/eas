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
public abstract class AbstractThirdPartyExpenseBillEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionCalcMgrBonus(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCalcMgrBonus(request,response,context);
	}
	protected void _handleActionCalcMgrBonus(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddMgrBonus(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddMgrBonus(request,response,context);
	}
	protected void _handleActionAddMgrBonus(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRemoveMgrBonus(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemoveMgrBonus(request,response,context);
	}
	protected void _handleActionRemoveMgrBonus(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAddHand(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAddHand(request,response,context);
	}
	protected void _handleActionAddHand(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionRemoveHand(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemoveHand(request,response,context);
	}
	protected void _handleActionRemoveHand(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}