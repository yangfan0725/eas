package com.kingdee.eas.fdc.market.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.market.PolicyManageInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.fdc.market.PolicyManageCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

public class PolicyManageControllerBean extends AbstractPolicyManageControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.PolicyManageControllerBean");
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	handleIntermitNumber(ctx, (PolicyManageInfo) model);
    	return super._submit(ctx, model);
	}
    
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		handleIntermitNumber(ctx, (PolicyManageInfo) model);
		return super._save(ctx, model);
	}
    
    
    private static void handleIntermitNumber(Context ctx, PolicyManageInfo info) throws BOSException, CodingRuleException, EASBizException {
		// ����û��ڿͻ����ֹ�ѡ���˶Ϻ�,��˴�����������
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;

		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);

		String costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();

		if (StringUtils.isEmpty(costUnitId)) {
			return;
		}

		if (iCodingRuleManager.isExist(info, costUnitId)) {
			// ѡ���˶Ϻ�֧�ֻ���û��ѡ��������ʾ,��ȡ�����ñ��
			if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId) || !iCodingRuleManager.isAddView(info, costUnitId))
			// �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
			{
				// �����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
				String number = iCodingRuleManager.getNumber(info, costUnitId);
				info.setNumber(number);
			}
		}
	}
}