package com.kingdee.eas.fdc.sellhouse.app;

import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysContextConstant;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.app.DbUtil;

public class PreconcertPostponeControllerBean extends AbstractPreconcertPostponeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.PreconcertPostponeControllerBean");

    /**
     * ����
     */
    public void audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
		
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		sql.append("UPDATE T_SHE_PreconcertPostpone SET FState = '");
		sql.append(FDCBillStateEnum.AUDITTED_VALUE);
		sql.append("', FAuditorID = '");
		sql.append(((UserInfo) ctx.get(SysContextConstant.USERINFO)).getId());
		sql.append("', FAuditTime = getDate()");
		sql.append(" WHERE FID = '");
		sql.append(billId.toString());
		sql.append("'");
		
		sql1.append("UPDATE T_SHE_Purchase SET FPrePurchaseValidDate =");
		sql1.append("select FNowavailab from T_SHE_PreconcertPostpone WHERE FID = '");
		sql1.append(billId.toString());
		sql1.append("' WHERE FID in(");
		sql1.append("select FPurchaseID from T_SHE_PreconcertPostpone WHERE FID = '");
		sql1.append(billId.toString());
		sql1.append("');");
		
		/**
		 * ������ִ��sql����ʱ��һ��Ҫ����setBatchType(FDCSQLBuilder.STATEMENT_TYPE)����
		 * �����ڵ���addBatch������ʱ��һ���ᱨ��ָ�����
		 * by renliang 2010-11-16
		 * 
		 */
		FDCSQLBuilder sqlBuilder  = new FDCSQLBuilder(ctx);
		sqlBuilder.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		sqlBuilder.addBatch(sql.toString());
		sqlBuilder.addBatch(sql1.toString());
		sqlBuilder.executeBatch();
	
    }
    
    /**
     * ������
     */
    public void unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE T_SHE_PreconcertPostpone SET FState = '");
		sql.append(FDCBillStateEnum.SUBMITTED_VALUE);
		sql.append("', FAuditorID = null, FAuditTime = null");
		sql.append(" WHERE FID = '");
		sql.append(billId.toString());
		sql.append("'");
		DbUtil.execute(ctx, sql.toString());
    }
}