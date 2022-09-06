package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Calendar;

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

import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basecrm.CreateWayEnum;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntry;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntryInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.NoTradingSellBillCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.NoTradingSellBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class NoTradingSellBillControllerBean extends AbstractNoTradingSellBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.NoTradingSellBillControllerBean");
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		super._audit(ctx, billId);
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("entry.*");
		sic.add("entry.sellProject.orgUnit.*");
		sic.add("entry.sellProject.CU.*");
		sic.add("entry.room.*");
//		sic.add("entry.customer.*");
//		sic.add("entry.customer.propertyConsultant.*");
		NoTradingSellBillInfo info=this.getNoTradingSellBillInfo(ctx, new ObjectUuidPK(billId),sic);
		for(int i=0;i<info.getEntry().size();i++){
			SellProjectInfo sp=info.getEntry().get(i).getSellProject();
			SignManageInfo sign=new SignManageInfo();
			sign.setId(BOSUuid.create(sign.getBOSType()));
			sign.setSourceBillId(billId.toString());
			sign.setOrgUnit(sp.getOrgUnit());
			sign.setSellProject(sp);
			sign.setRoom(info.getEntry().get(i).getRoom());
			sign.setBusAdscriptionDate(new Timestamp(info.getBizDate().getTime()));
			sign.setBizDate(info.getBizDate());
			sign.setCU(sp.getCU());
			
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
			sign.setNumber(iCodingRuleManager.getNumber(sign, sp.getOrgUnit().getId().toString()));
			
			sign.setDealTotalAmount(info.getEntry().get(i).getSellAmount());
			sign.setSellAmount(info.getEntry().get(i).getSellAmount());
			
			SignCustomerEntryInfo customEntry=new SignCustomerEntryInfo();
			customEntry.setIsMain(true);
			
			SellProjectInfo psp=SHEManageHelper.getParentSellProject(ctx, sp);
			SHECustomerInfo sheCustomer=null;
			SHECustomerCollection sheCustomerCol=SHECustomerFactory.getLocalInstance(ctx).getSHECustomerCollection("select *,propertyConsultant.* from where name='非操盘项目客户' and sellProject.id='"+psp.getId()+"'");
			if(sheCustomerCol.size()>0){
				sheCustomer=sheCustomerCol.get(0);
			}else{
				sheCustomer=new SHECustomerInfo();
				sheCustomer.setCustomerType(CustomerTypeEnum.PERSONALCUSTOMER);
				sheCustomer.setName("非操盘项目客户");
				sheCustomer.setPhone("12345678");
				sheCustomer.setPropertyConsultant(UserFactory.getLocalInstance(ctx).getUserInfo("select * from where number='00561'"));
				sheCustomer.setFirstConsultant(sheCustomer.getPropertyConsultant());
				sheCustomer.setIsEnabled(true);
				sheCustomer.setCreateUnit(sp.getOrgUnit());
				sheCustomer.setLastUpdateUnit(sp.getOrgUnit());
				sheCustomer.setCreateWay(CreateWayEnum.HAND);
				sheCustomer.setIsPublic(false);
				sheCustomer.setCU(sp.getCU());
				sheCustomer.setSellProject(psp);
				sheCustomer.setNumber(iCodingRuleManager.getNumber(sheCustomer, sp.getOrgUnit().getId().toString()));
				SHECustomerFactory.getLocalInstance(ctx).submit(sheCustomer);
			}
			customEntry.setCustomer(sheCustomer);
			
			sign.getSignCustomerEntry().add(customEntry);
			
			SignSaleManEntryInfo saleManEntry=new SignSaleManEntryInfo();
			saleManEntry.setUser(sheCustomer.getPropertyConsultant());
			
			sign.getSignSaleManEntry().add(saleManEntry);
			sign.setSalesman(sheCustomer.getPropertyConsultant());
			sign.setSaleManNames(sheCustomer.getPropertyConsultant().getName());
			
			sign.setCustomerNames(sheCustomer.getName());

			SignPayListEntryInfo payListEntry=new SignPayListEntryInfo();
			payListEntry.setAppDate(info.getBizDate());
			payListEntry.setAppAmount(info.getEntry().get(i).getSellAmount());
			payListEntry.setMoneyDefine(MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineInfo("select * from where name='一次性款'"));
			sign.getSignPayListEntry().add(payListEntry);
			
			SignManageFactory.getLocalInstance(ctx).submit(sign);
			
			SHERevBillInfo rev=new SHERevBillInfo();
			rev.setSourceBillId(billId.toString());
			rev.setBizOU(sp.getOrgUnit());
			rev.setSellProject(sp);
			rev.setRoom(info.getEntry().get(i).getRoom());
			rev.setBizDate(info.getBizDate());
			rev.setCU(sp.getCU());
			rev.setSaleOrgUnit(SaleOrgUnitFactory.getLocalInstance(ctx).getSaleOrgUnitInfo(new ObjectUuidPK(sp.getOrgUnit().getId())));
			rev.setRevAmount(info.getEntry().get(i).getBackAmount());
			rev.setRevBillType(RevBillTypeEnum.gathering);
			rev.setRelateBizType(RelatBizType.Sign);
			rev.setRelateBizBillId(sign.getId().toString());
			rev.setRelateBizBillNumber(sign.getNumber());
			rev.setCustomerIds(sheCustomer.getId().toString());
			rev.setCustomerNames(sheCustomer.getName());
			rev.setRelateTransId(sign.getTransactionID().toString());
			
			rev.setNumber(iCodingRuleManager.getNumber(rev, sp.getOrgUnit().getId().toString()));
			
			SHERevBillEntryInfo revEntry=new SHERevBillEntryInfo();
			revEntry.setMoneyDefine(payListEntry.getMoneyDefine());
			revEntry.setRevAmount(info.getEntry().get(i).getBackAmount());
			rev.getEntrys().add(revEntry);
			
			SHERevCustEntryInfo revCustomerEntry=new SHERevCustEntryInfo();
			revCustomerEntry.setSheCustomer(sheCustomer);
			rev.getCustomerEntry().add(revCustomerEntry);
			
			SHERevBillFactory.getLocalInstance(ctx).submit(rev);
		}
    }
    
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		super._unAudit(ctx, billId);
		NoTradingSellBillInfo info=this.getNoTradingSellBillInfo(ctx, new ObjectUuidPK(billId));
		SHERevBillCollection revCol=SHERevBillFactory.getLocalInstance(ctx).getSHERevBillCollection("select id from where sourceBillId='"+info.getId()+"'");
		for(int i=0;i<revCol.size();i++){
			SHERevBillFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(revCol.get(i).getId()));
		}
		
		SignManageCollection signCol=SignManageFactory.getLocalInstance(ctx).getSignManageCollection("select id from where sourceBillId='"+info.getId()+"'");
		for(int i=0;i<signCol.size();i++){
			SignManageFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(signCol.get(i).getId()));
		}
    }
}