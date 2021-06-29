package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
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

import com.kingdee.eas.base.multiapprove.ApproveResult;
import com.kingdee.eas.base.multiapprove.MultiApproveCollection;
import com.kingdee.eas.base.multiapprove.MultiApproveFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillInfo;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillNewEntryCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillCollection;
import com.kingdee.eas.util.app.ContextUtil;

public class DelayPayBillControllerBean extends AbstractDelayPayBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.DelayPayBillControllerBean");
    
    protected void _save(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
    	DelayPayBillInfo info = (DelayPayBillInfo) model;
		info.setState(FDCBillStateEnum.SAVED);
		
		super._save(ctx, pk, info);
	}
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		DelayPayBillInfo info = (DelayPayBillInfo) model;
		info.setState(FDCBillStateEnum.SAVED);
		
		return super._save(ctx, info);
	}
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)throws BOSException, EASBizException {
		DelayPayBillInfo info = (DelayPayBillInfo) model;
		info.setState(FDCBillStateEnum.SUBMITTED);
		
		super._submit(ctx, pk, info);
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		DelayPayBillInfo info = (DelayPayBillInfo) model;
		info.setState(FDCBillStateEnum.SUBMITTED);
		
		return super._submit(ctx, info);
	}
    protected void _audit(Context ctx, BOSUuid billID) throws BOSException {
    	DelayPayBillInfo billInfo = new DelayPayBillInfo();
		billInfo.setId(billID);
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("billId",billID.toString()));
		filter.getFilterItems().add(new FilterItemInfo("isPass",ApproveResult.PASS_VALUE));
		view.setFilter(filter);
		SorterItemInfo sorter =new SorterItemInfo();
		sorter.setPropertyName("createtime");
		sorter.setSortType(SortType.DESCEND);
		view.getSorter().add(sorter);
		view.getSelector().add("billid");
		view.getSelector().add("creator.id");
		view.getSelector().add("creator.name");
		view.getSelector().add("createtime");
		MultiApproveCollection mulColl = MultiApproveFactory.getLocalInstance(ctx).getMultiApproveCollection(view);
		if(mulColl.size()>0){
			billInfo.setAuditor(mulColl.get(0).getCreator());
			billInfo.setAuditTime(mulColl.get(0).getCreateTime());
		}else{
			billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
			billInfo.setAuditTime(new Date());
		}
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("state");
        selector.add("auditor");
        selector.add("auditTime");
        
		try {
			_updatePartial(ctx, billInfo, selector);
			DelayPayBillInfo bill= this.getDelayPayBillInfo(ctx, new ObjectUuidPK(billID));
			RoomInfo room =bill.getRoom();
			SignManageCollection col=SignManageFactory.getLocalInstance(ctx).getSignManageCollection("select signCustomerEntry.*,signCustomerEntry.customer.*,signPayListEntry.*,signPayListEntry.moneyDefine.*,payType.*,* from where room.id='"+room.getId()+"' and (bizState='SignApple' or bizState='SignAudit') order by signPayListEntry.seq");
			if(col.size()>0){
				SignManageInfo sign=col.get(0);
				
				DelayPayBillNewEntryCollection entryCol=bill.getNewEntry();
				CRMHelper.sortCollection(entryCol, new String[]{"seq"}, true);
				
				SelectorItemCollection sic=new SelectorItemCollection();
				sic.add("planSignDate");
				
				sign.setPlanSignDate(bill.getPlanSignDate());
				SignManageFactory.getLocalInstance(ctx).updatePartial(sign, sic);
				
				sic=new SelectorItemCollection();
				sic.add("appDate");
				SignPayListEntryCollection signentryCol=sign.getSignPayListEntry();
				CRMHelper.sortCollection(signentryCol, new String[]{"seq"}, true);
				for(int i=0;i<signentryCol.size();i++){
					SignPayListEntryInfo entry=signentryCol.get(i);
					entry.setAppDate(entryCol.get(i).getAppDate());
					
					SignPayListEntryFactory.getLocalInstance(ctx).updatePartial(entry, sic);
				}
				SHEManageHelper.updateTransaction(ctx,sign,RoomSellStateEnum.Sign,true);
			}
			
			PurchaseManageCollection purcol=PurchaseManageFactory.getLocalInstance(ctx).getPurchaseManageCollection("select * from where room.id='"+room.getId()+"' and (bizState='PurApple' or bizState='PurAudit' or bizState='ToSign') order by purPayListEntry.seq");
			if(col.size()>0){
				PurchaseManageInfo pur=purcol.get(0);
				
				SelectorItemCollection sic=new SelectorItemCollection();
				sic.add("planSignDate");
				
				pur.setPlanSignDate(bill.getPlanSignDate());
				PurchaseManageFactory.getLocalInstance(ctx).updatePartial(pur, sic);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }

	protected void _setAudittingStatus(Context ctx, BOSUuid billId)
			throws BOSException {
		DelayPayBillInfo billInfo = new DelayPayBillInfo();
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTING);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("state");
        selector.add("auditor");
        selector.add("auditTime");
        try {
			_updatePartial(ctx, billInfo, selector);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	protected void _setSubmitStatus(Context ctx, BOSUuid billId)
			throws BOSException {
		DelayPayBillInfo billInfo = new DelayPayBillInfo();
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("state");
        selector.add("auditor");
        selector.add("auditTime");
        try {
			_updatePartial(ctx, billInfo, selector);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException {
		DelayPayBillInfo billInfo = new DelayPayBillInfo();
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
        SelectorItemCollection selector = new SelectorItemCollection();
        selector.add("state");
        selector.add("auditor");
        selector.add("auditTime");
        try {
			_updatePartial(ctx, billInfo, selector);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}