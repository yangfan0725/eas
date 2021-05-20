/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractReportConditionFilterUIHandler extends com.kingdee.eas.framework.app.CoreUIHandler

{
	public void handleActionConditionQuery(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionConditionQuery(request,response,context);
	}
	protected void _handleActionConditionQuery(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionResetCondition(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionResetCondition(request,response,context);
	}
	protected void _handleActionResetCondition(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}