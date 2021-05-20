package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.ExpertTypeInfo;

public class ExpertTypeControllerBean extends AbstractExpertTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.ExpertTypeControllerBean");

	protected void _checkNameDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		super._checkNameDup(ctx, model);
	}

	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		super._checkNumberDup(ctx,model);
		super._checkNameDup(ctx,model);
		
		IObjectPK pk = super._save(ctx, model);
//		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		builder.appendSql("update t_inv_appraiseExpertType set flongnumber=fnumber||'!'||? where fparentid=?");
//		builder.addParam(((ExpertTypeInfo)model).getLongNumber());
//		builder.addParam(pk.toString());
//		builder.execute();
		
		return pk;
		
	}

	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		super._checkNumberDup(ctx,model);
		//2009-10-2��Զ�����ģ�Ӧ��̷��������ģ�Ҫ��ר���������ƿ����ظ���
		//super._checkNameDup(ctx,model);
		
		IObjectPK pk = super._submit(ctx, model);
//		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		builder.appendSql("update t_inv_appraiseExpertType set flongnumber=?||'!'||fnumber where fparentid=?");
//		builder.addParam(((ExpertTypeInfo)model).getLongNumber());
//		builder.addParam(pk.toString());
//		builder.execute();
		
		return pk;
	}
}