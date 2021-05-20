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
public abstract class AbstractProgressReportBaseSchTaskPropertiesNewUIHandler extends com.kingdee.eas.fdc.schedule.app.FDCScheduleTaskPropertiesNewUIHandler

{
	public void handleActionAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAttachment(request,response,context);
	}
	protected void _handleActionAttachment(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}