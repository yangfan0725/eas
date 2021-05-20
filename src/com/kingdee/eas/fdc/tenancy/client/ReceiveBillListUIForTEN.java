package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.ReceiveBillEidtUI;
import com.kingdee.eas.fdc.sellhouse.client.ReceiveBillListUI;

/**
 * �����տ���²�
 * @author laiquan_luo
 *
 */
public class ReceiveBillListUIForTEN extends ReceiveBillListUI
{

	public ReceiveBillListUIForTEN() throws Exception
	{
		super();
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e)
	{
		super.prepareUIContext(uiContext, e);
		
		uiContext.put(ReceiveBillEidtUI.KEY_MONEYTSYSTYPE,MoneySysTypeEnum.TenancySys);
	}
	
	public void setUITitle(String title)
	{
		super.setUITitle("�����տ���²�");
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo)
	{
		viewInfo = (EntityViewInfo) mainQuery.clone();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.moneySysType",MoneySysTypeEnum.TENANCYSYS_VALUE));
		// �ϲ���ѯ����
		try
		{
			
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (Exception e)
		{
			this.handUIException(e);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.TenancySys;
	}
	
	
}
