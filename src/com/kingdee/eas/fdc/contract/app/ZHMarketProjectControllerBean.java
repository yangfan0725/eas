package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.List;

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

import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.contract.MarketProjectCollection;
import com.kingdee.eas.fdc.contract.MarketProjectCostEntryInfo;
import com.kingdee.eas.fdc.contract.MarketProjectEntryInfo;
import com.kingdee.eas.fdc.contract.MarketProjectFactory;
import com.kingdee.eas.fdc.contract.MarketProjectInfo;
import com.kingdee.eas.fdc.contract.MarketProjectSourceEnum;
import com.kingdee.eas.fdc.contract.MarketProjectUnitEntryInfo;
import com.kingdee.eas.fdc.contract.ZHMarketProjectEntryInfo;
import com.kingdee.eas.fdc.contract.ZHMarketProjectInfo;
import com.kingdee.eas.fdc.contract.ZHMarketProjectCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.util.NumericExceptionSubItem;

public class ZHMarketProjectControllerBean extends AbstractZHMarketProjectControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ZHMarketProjectControllerBean");
    
    protected void checkNameDup(Context ctx, FDCBillInfo billInfo)throws BOSException, EASBizException{
    	
    }
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		createMarketProjectInfo(ctx,this.getZHMarketProjectInfo(ctx, pk));
		super._submit(ctx, pk, model);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		createMarketProjectInfo(ctx,(ZHMarketProjectInfo) model);
		return super._submit(ctx, model);
	}
	public void deleteMarketProjectInfo(Context ctx,String id) throws EASBizException, BOSException{
		MarketProjectCollection col=MarketProjectFactory.getLocalInstance(ctx).getMarketProjectCollection("select * from where sourceBillId='"+id+"'");
		for(int i=0;i<col.size();i++){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", col.get(i).getId()));
			BoAttchAssoFactory.getLocalInstance(ctx).delete(filter);
			MarketProjectFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(col.get(i).getId()));
		}
	}
	public void createMarketProjectInfo(Context ctx,ZHMarketProjectInfo info) throws EASBizException, BOSException{
		deleteMarketProjectInfo(ctx,info.getId().toString());
		for(int i=0;i<info.getEntry().size();i++){
			ZHMarketProjectEntryInfo entry=info.getEntry().get(i);
			
			MarketProjectInfo mp=new MarketProjectInfo();
			mp.setNumber(info.getNumber()+"-"+(i+1));
			mp.setName(info.getName()+"-"+(i+1));
			mp.setOrgUnit(info.getOrgUnit());
			mp.setSellProjecttxt(info.getSellProjecttxt());
			mp.setIsJT(info.isIsJT());
			mp.setNw(info.getNw());
			mp.setSourceBillId(info.getId().toString());
			mp.setSource(MarketProjectSourceEnum.ZHLX);
			mp.setIsSub(info.isIsSub());
			mp.setState(FDCBillStateEnum.AUDITTING);
			
			mp.setDescription(entry.getDescription());
			mp.setBizDate(entry.getBizDate());
			
			MarketProjectCostEntryInfo costEntry=new MarketProjectCostEntryInfo();
			costEntry.setCostAccount(entry.getCostAccount());
			costEntry.setAmount(entry.getAmount());
			costEntry.setType(entry.getType());
			mp.getCostEntry().add(costEntry);
			mp.setAmount(entry.getAmount());
			mp.setMp(entry.getMarketProject());
			
			if(entry.getSupplier()!=null){
				MarketProjectEntryInfo mpEntry=new MarketProjectEntryInfo();
				mpEntry.setSupplier(entry.getSupplier());
				mpEntry.setAmount(entry.getSdAmount());
				mpEntry.setRemark(entry.getSdRemark());
				mp.getEntry().add(mpEntry);
			}
			
			MarketProjectUnitEntryInfo unitEntry=new MarketProjectUnitEntryInfo();
			unitEntry.setRemark(entry.getUnit());
			mp.getUnitEntry().add(unitEntry);
			
			IObjectPK pk=MarketProjectFactory.getLocalInstance(ctx).addnew(mp);
			
			SelectorItemCollection attsic = new SelectorItemCollection();
			attsic.add(new SelectorItemInfo("*"));
			attsic.add(new SelectorItemInfo("attachment.*"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", info.getId().toString()));
			EntityViewInfo evi = new EntityViewInfo();
			evi.setFilter(filter);
			evi.setSelector(attsic);
			BoAttchAssoCollection cols= BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(evi);
			
            for (int j=0;j<cols.size();j++) {
            	BoAttchAssoInfo boinfo=cols.get(j);
            	BoAttchAssoInfo newInfo=(BoAttchAssoInfo) boinfo.clone();
            	newInfo.setId(BOSUuid.create(boinfo.getBOSType()));
            	newInfo.setBoID(pk.toString());
            	BoAttchAssoFactory.getLocalInstance(ctx).addnew(newInfo);
            }
		}
	}
	protected void _setAudittingStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		MarketProjectCollection col=MarketProjectFactory.getLocalInstance(ctx).getMarketProjectCollection("select * from where sourceBillId='"+billId+"'");
		for(int i=0;i<col.size();i++){
			MarketProjectInfo mp=col.get(i);
			mp.setState(FDCBillStateEnum.AUDITTING);
			
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("state");
			MarketProjectFactory.getLocalInstance(ctx).updatePartial(mp, selector);
		}
		super._setAudittingStatus(ctx, billId);
	}
	protected void _setSubmitStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		MarketProjectCollection col=MarketProjectFactory.getLocalInstance(ctx).getMarketProjectCollection("select * from where sourceBillId='"+billId+"'");
		for(int i=0;i<col.size();i++){
			MarketProjectInfo mp=col.get(i);
			mp.setState(FDCBillStateEnum.SUBMITTED);
			
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("state");
			MarketProjectFactory.getLocalInstance(ctx).updatePartial(mp, selector);
		}
		super._setSubmitStatus(ctx, billId);
	}
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		MarketProjectCollection col=MarketProjectFactory.getLocalInstance(ctx).getMarketProjectCollection("select * from where sourceBillId='"+billId+"'");
		for(int i=0;i<col.size();i++){
			MarketProjectInfo mp=col.get(i);
			MarketProjectFactory.getLocalInstance(ctx).audit(mp.getId());
		}
		
		super._audit(ctx, billId);
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		deleteMarketProjectInfo(ctx,pk.toString());
		super._delete(ctx, pk);
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		if(billId!=null){
			throw new EASBizException(new NumericExceptionSubItem("100","½ûÖ¹·´ÉóÅú²Ù×÷£¡"));
		}
		super._unAudit(ctx, billId);
	}
	
}