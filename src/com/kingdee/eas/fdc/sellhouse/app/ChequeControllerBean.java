package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.ChequeCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.VerifyStatusEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;

public class ChequeControllerBean extends AbstractChequeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.ChequeControllerBean");    
    
    /**
     * �Ǽǣ�����������
     * @param ctx
     * @param models
     * 			��������ʵ�弯��
     * @throws BOSException
     * @throws EASBizException
     * */
	protected void _addBatch(Context ctx, IObjectCollection models) throws BOSException, EASBizException {
		ChequeCollection chequeCollection = (ChequeCollection) models;
		for (int i = 0; i < chequeCollection.size(); i++) {
			ChequeInfo info = (ChequeInfo) chequeCollection.get(i);
			info.setStatus(ChequeStatusEnum.Booked);
			this.addnew(ctx, info);
		}
	}

	/**
	 * ������������
	 * @param ctx
	 * @param ids
	 * 			��������Ʊ��ID����
	 * @throws BOSException
	 * @throws EASBizException
	 * */
	protected void _vc(Context ctx, List ids) throws BOSException, EASBizException {
		if(ids == null  ||  ids.isEmpty()){
			logger.warn("�ͻ��˿����߼���������ϸ���");
			return;
		}
		
		FullOrgUnitInfo curOrg = ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo();
		UserInfo curUser = ContextUtil.getCurrentUserInfo(ctx);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_SHE_Cheque set FVerifyOrgUnitID=?,FVerifierID=?,FVerifyStatus=?,FVerifyTime=? ");
		sql.append("where fid in (");
		for(int i=0; i<ids.size(); i++){
			if(i != 0){
				sql.append(",");
			}
			sql.append("'");
			sql.append(ids.get(i));
			sql.append("'");
		}
		sql.append(")");
		logger.debug("sql:" + sql.toString());
		DbUtil.execute(ctx, sql.toString(), new Object[]{curOrg.getId().toString(), curUser.getId().toString(), 
			 new Integer(VerifyStatusEnum.VERIFIED_VALUE), new Timestamp(new Date().getTime())});
	}

	/**
	 * ���ϣ�������
	 * @param ctx
	 * @param ids
	 * 			�����ϵ�Ʊ��ID����
	 * @throws BOSException
	 * @throws EASBizException
	 * */
	protected void _abandon(Context ctx, List ids) throws BOSException, EASBizException {
		if(ids == null  ||  ids.isEmpty()){
			logger.warn("�ͻ��˿����߼���������ϸ���");
			return;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("update T_SHE_Cheque set FStatus=? ");
		sql.append("where fid in (");
		for(int i=0; i<ids.size(); i++){
			if(i != 0){
				sql.append(",");
			}
			sql.append("'");
			sql.append(ids.get(i));
			sql.append("'");
		}
		sql.append(")");
		logger.debug("sql:" + sql.toString());
		DbUtil.execute(ctx, sql.toString(), new Object[]{ new Integer(ChequeStatusEnum.CANCELLED_VALUE)});
	}
	
	/**
	 * ���䣨������
	 * @param ctx
	 * @param ids
	 * 			�������Ʊ��ID����
	 * @param newKeepOrgUnitId
	 * 			�±�����֯���
	 * @param newKeeperId
	 * 			�±�����UserInfo���
	 * @throws BOSException
	 * @throws EASBizException
	 * */
	protected void _distribute(Context ctx, String[] ids, String newKeepOrgUnitId, String newKeeperId) throws BOSException, EASBizException {
		if(ids == null  ||  ids.length == 0){
			logger.warn("�ͻ��˿����߼���������ϸ���");
			return;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("update T_SHE_Cheque set FKeepOrgUnitID=?,FKeeperID=? ");
		sql.append("where fid in (");
		for(int i=0; i<ids.length; i++){
			if(i != 0){
				sql.append(",");
			}
			sql.append("'");
			sql.append(ids[i]);
			sql.append("'");
		}
		sql.append(")");
		logger.debug("sql:" + sql.toString());
		DbUtil.execute(ctx, sql.toString(), new Object[]{newKeepOrgUnitId, newKeeperId});
	}
}