package com.kingdee.eas.fdc.market.app;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.market.MarketManageEntryCollection;
import com.kingdee.eas.fdc.market.MarketManageEntryInfo;
import com.kingdee.eas.fdc.market.MarketManageInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

public class MarketManageControllerBean extends AbstractMarketManageControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.market.app.MarketManageControllerBean");

	public IObjectPK save(Context ctx, CoreBaseInfo model) throws EASBizException, BOSException {
		IObjectPK pk = super.save(ctx, model);
		MarketManageEntryInfo marketManageEntryInfo = null;
		MarketManageEntryCollection marketManageEntryCollection = null;
		MarketManageInfo marketManageInfo = (MarketManageInfo) model;
		marketManageEntryCollection = marketManageInfo.getEntrys();
		BigDecimal totalMoney = new BigDecimal(0);
		for (int i = 0; i < marketManageEntryCollection.size(); i++) {
			marketManageEntryInfo = marketManageEntryCollection.get(i);
			totalMoney = totalMoney.add(marketManageEntryInfo.getAmount());
		}
		marketManageInfo.setFactTotalMoney(totalMoney);
		handleIntermitNumber(ctx, marketManageInfo);
		super.save(ctx, marketManageInfo);
		return pk;
	}

	public IObjectPK submit(Context ctx, CoreBaseInfo model) throws BOSException, EASBizException {
		MarketManageEntryInfo marketManageEntryInfo = null;
		MarketManageEntryCollection marketManageEntryCollection = null;
		MarketManageInfo marketManageInfo = (MarketManageInfo) model;
		marketManageEntryCollection = marketManageInfo.getEntrys();
		BigDecimal totalMoney = new BigDecimal(0);
		for (int i = 0; i < marketManageEntryCollection.size(); i++) {
			marketManageEntryInfo = marketManageEntryCollection.get(i);
			totalMoney = totalMoney.add(marketManageEntryInfo.getAmount());
		}
		marketManageInfo.setFactTotalMoney(totalMoney);
		handleIntermitNumber(ctx, marketManageInfo);
		return super.submit(ctx, marketManageInfo);
	}

	protected void handleIntermitNumber(Context ctx, MarketManageInfo info) throws BOSException, CodingRuleException, EASBizException {
		// ����û��ڿͻ����ֹ�ѡ���˶Ϻ�,��˴�����������
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		String costUnitId = "";
		if (info.getCompany() != null) {
			costUnitId = info.getCompany().getId().toString();
		} else {
			costUnitId = ContextUtil.getCurrentSaleUnit(ctx).getId().toString();
		}
		if (StringUtils.isEmpty(costUnitId)) {
			return;
		}
		boolean isExist = true;
		if (!iCodingRuleManager.isExist(info, costUnitId)) {
			costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
			if (!iCodingRuleManager.isExist(info, costUnitId)) {
				isExist = false;
			}
		}
		if (isExist) {
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