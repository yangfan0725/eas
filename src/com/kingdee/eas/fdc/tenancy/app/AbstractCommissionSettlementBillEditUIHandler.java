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
public abstract class AbstractCommissionSettlementBillEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

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
	public void handleActionRemoveKD(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionRemoveKD(request,response,context);
	}
	protected void _handleActionRemoveKD(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}