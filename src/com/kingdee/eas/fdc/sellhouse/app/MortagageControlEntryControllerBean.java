package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;

public class MortagageControlEntryControllerBean extends AbstractMortagageControlEntryControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.MortagageControlEntryControllerBean");

    /**
	 * ��дУ��:
	 * �������������Ƿ��ظ�
	 */
	protected void checkBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
	}
}