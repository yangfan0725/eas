package com.kingdee.eas.fdc.contract.app;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.AttachmentStorageTypeEnum;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.eas.fdc.contract.MarketProjectCollection;
import com.kingdee.eas.fdc.contract.MarketProjectCostEntryInfo;
import com.kingdee.eas.fdc.contract.MarketProjectEntryInfo;
import com.kingdee.eas.fdc.contract.MarketProjectFactory;
import com.kingdee.eas.fdc.contract.MarketProjectInfo;
import com.kingdee.eas.fdc.contract.MarketProjectSourceEnum;
import com.kingdee.eas.fdc.contract.MarketProjectUnitEntryInfo;
import com.kingdee.eas.fdc.contract.RecommendTypeInfo;
import com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillCollection;
import com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillEntryInfo;
import com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillFactory;
import com.kingdee.eas.fdc.contract.ThirdPartyExpenseBillInfo;
import com.kingdee.eas.fdc.contract.ZHMarketProjectEntryInfo;
import com.kingdee.eas.fdc.contract.ZHMarketProjectInfo;
import com.kingdee.eas.fdc.tenancy.CommissionTenancyEntryInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.util.NumericExceptionSubItem;

public class ThirdPartyExpenseBillControllerBean extends AbstractThirdPartyExpenseBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ThirdPartyExpenseBillControllerBean");
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		super._audit(ctx, billId);
		ThirdPartyExpenseBillInfo info=ThirdPartyExpenseBillFactory.getLocalInstance(ctx).getThirdPartyExpenseBillInfo(new ObjectUuidPK(billId));
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.setBatchType("StatementType");
		for(int i=0;i<info.getEntry().size();i++){
			ThirdPartyExpenseBillEntryInfo entry=info.getEntry().get(i);
			StringBuffer sql = new StringBuffer();
			sql.append(" update t_she_signManage set fsourceFunction='"+info.getId().toString()+"' where fid='"+entry.getSignManageId()+"' ");
			builder.addBatch(sql.toString());
		}
		builder.executeBatch();
		
//		MarketProjectCollection col=MarketProjectFactory.getLocalInstance(ctx).getMarketProjectCollection("select * from where sourceBillId='"+billId+"'");
//		if(col.size()>0){
//			MarketProjectInfo mp=col.get(0);
//			MarketProjectFactory.getLocalInstance(ctx).audit(mp.getId());
//		}
		createMarketProjectInfo(ctx,info);
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		if(billId!=null){
			throw new EASBizException(new NumericExceptionSubItem("100","½ûÖ¹·´ÉóÅú²Ù×÷£¡"));
		}
		super._unAudit(ctx, billId);
		ThirdPartyExpenseBillInfo info=ThirdPartyExpenseBillFactory.getLocalInstance(ctx).getThirdPartyExpenseBillInfo(new ObjectUuidPK(billId));
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.setBatchType("StatementType");
		for(int i=0;i<info.getEntry().size();i++){
			ThirdPartyExpenseBillEntryInfo entry=info.getEntry().get(i);
			StringBuffer sql = new StringBuffer();
			sql.append(" update t_she_signManage set fsourceFunction=null where fid='"+entry.getSignManageId()+"' ");
			builder.addBatch(sql.toString());
		}
		builder.executeBatch();
	}
	 protected void checkNameDup(Context ctx, FDCBillInfo billInfo)throws BOSException, EASBizException{
	    	
	    }
		protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
				throws BOSException, EASBizException {
//			createMarketProjectInfo(ctx,this.getThirdPartyExpenseBillInfo(ctx, pk));
			super._submit(ctx, pk, model);
		}

		protected IObjectPK _submit(Context ctx, IObjectValue model)
				throws BOSException, EASBizException {
//			createMarketProjectInfo(ctx,(ThirdPartyExpenseBillInfo) model);
			return super._submit(ctx, model);
		}
		public void createMarketProjectInfo(Context ctx,ThirdPartyExpenseBillInfo info) throws EASBizException, BOSException{
			MarketProjectCollection col=MarketProjectFactory.getLocalInstance(ctx).getMarketProjectCollection("select * from where sourceBillId='"+info.getId().toString()+"'");
			if(col.size()>0){
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("boID", col.get(0).getId()));
				BoAttchAssoFactory.getLocalInstance(ctx).delete(filter);
				MarketProjectFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(col.get(0).getId()));
			}
			Map rowMap=new HashMap();
	    	for(int i=0;i<info.getEntry().size();i++){
	    		CostAccountInfo ca=info.getEntry().get(i).getCostAccount();
	    		BigDecimal sqjl=info.getEntry().get(i).getSqjl();
	    		if(ca!=null){
	    			if(rowMap.containsKey(ca.getId().toString())){
	    				rowMap.put(ca.getId().toString(), FDCHelper.add(rowMap.get(ca.getId().toString()),sqjl));
	    			}else{
	    				rowMap.put(ca.getId().toString(), sqjl);
	    			}
	    		}
	    	}
	    	for(int i=0;i<info.getHandEntry().size();i++){
	    		CostAccountInfo ca=info.getHandEntry().get(i).getCostAccount();
	    		BigDecimal sqjl=info.getHandEntry().get(i).getSqjl();
	    		if(ca!=null){
	    			if(rowMap.containsKey(ca.getId().toString())){
	    				rowMap.put(ca.getId().toString(), FDCHelper.add(rowMap.get(ca.getId().toString()),sqjl));
	    			}else{
	    				rowMap.put(ca.getId().toString(), sqjl);
	    			}
	    		}
	    	}
	    	int i=0;
	    	Iterator<Entry<String, BigDecimal>> it = rowMap.entrySet().iterator();
	    	while(it.hasNext()){
	    		Entry<String, BigDecimal> entry = it.next();
	    		
	    		MarketProjectInfo mp=new MarketProjectInfo();
				mp.setNumber(info.getNumber()+"-"+(i+1));
				mp.setName(info.getName()+"-"+(i+1));
				mp.setOrgUnit(info.getOrgUnit());
				mp.setSellProjecttxt(info.getSellProject().getName());
//				mp.setIsJT(info.isIsJT());
//				mp.setNw(info.getNw());
				mp.setSourceBillId(info.getId().toString());
				mp.setSource(MarketProjectSourceEnum.DSF);
				mp.setIsSub(false);
				mp.setState(FDCBillStateEnum.SAVED);
				
				mp.setDescription(info.getDescription());
				mp.setBizDate(info.getBizDate());
				
				
				MarketProjectCostEntryInfo costEntry=new MarketProjectCostEntryInfo();
				costEntry.setCostAccount(CostAccountFactory.getLocalInstance(ctx).getCostAccountInfo(new ObjectUuidPK(entry.getKey())));
				costEntry.setAmount(entry.getValue());
				costEntry.setType(MarketCostTypeEnum.NOTEXTCONTRACT);
				mp.getCostEntry().add(costEntry);
				mp.setAmount(entry.getValue());
				
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
	            i++;
	    	}
		}
		protected void _setAudittingStatus(Context ctx, BOSUuid billId)
				throws BOSException, EASBizException {
//			MarketProjectCollection col=MarketProjectFactory.getLocalInstance(ctx).getMarketProjectCollection("select * from where sourceBillId='"+billId+"'");
//			if(col.size()>0){
//				MarketProjectInfo mp=col.get(0);
//				mp.setState(FDCBillStateEnum.AUDITTING);
//				
//				SelectorItemCollection selector = new SelectorItemCollection();
//				selector.add("state");
//				MarketProjectFactory.getLocalInstance(ctx).updatePartial(mp, selector);
//			}
			super._setAudittingStatus(ctx, billId);
		}
		protected void _setSubmitStatus(Context ctx, BOSUuid billId)
				throws BOSException, EASBizException {
//			MarketProjectCollection col=MarketProjectFactory.getLocalInstance(ctx).getMarketProjectCollection("select * from where sourceBillId='"+billId+"'");
//			if(col.size()>0){
//				MarketProjectInfo mp=col.get(0);
//				mp.setState(FDCBillStateEnum.SUBMITTED);
//				
//				SelectorItemCollection selector = new SelectorItemCollection();
//				selector.add("state");
//				MarketProjectFactory.getLocalInstance(ctx).updatePartial(mp, selector);
//			}
			super._setSubmitStatus(ctx, billId);
		}
		protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
//			MarketProjectCollection col=MarketProjectFactory.getLocalInstance(ctx).getMarketProjectCollection("select * from where sourceBillId='"+pk.toString()+"'");
//			if(col.size()>0){
//				MarketProjectFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(col.get(0).getId()));
//			}
			super._delete(ctx, pk);
		}
	
}