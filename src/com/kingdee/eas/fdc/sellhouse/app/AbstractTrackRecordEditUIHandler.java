/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractTrackRecordEditUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillEditUIHandler

{
	public void handleActionQuestionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQuestionPrint(request,response,context);
	}
	protected void _handleActionQuestionPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}