package com.kingdee.eas.fdc.schedule.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;

public class DeptTaskProgressReportControllerBean extends AbstractDeptTaskProgressReportControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.app.DeptTaskProgressReportControllerBean");

	protected boolean _checkNumberBlank(Context ctx, IObjectPK pk, IObjectValue model) throws EASBizException, BOSException {
		// ��У�����Ϊ��
		// return super._checkNumberBlank(ctx, pk, model);
		return true;
	}

	protected boolean _checkNumberDup(Context ctx, IObjectPK pk, IObjectValue model) throws EASBizException, BOSException {
		// ��У�������ظ�
		// return super._checkNumberDup(ctx, pk, model);
		return true;
	}

	public void checkBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// ��дFdcBillControllerBean�еķ���������ҪУ�����ƺͱ���
		// super.checkBill(ctx, model);
	}
	
	/**
	 * @param ctx
	 * @param model
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		return super._submit(ctx, model);
	}
}