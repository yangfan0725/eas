package com.kingdee.eas.fdc.tenancy.app;

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
import java.math.BigDecimal;

import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.tenancy.DepositDealBillFactory;
import com.kingdee.eas.fdc.tenancy.DepositDealBillInfo;
import com.kingdee.eas.fdc.tenancy.DepositDealBillCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.tenancy.TenBillBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class DepositDealBillControllerBean extends AbstractDepositDealBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.DepositDealBillControllerBean");

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._audit(ctx, billId);
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("entry.moneyDefine.*");
		sic.add("entry.*");
		DepositDealBillInfo info=DepositDealBillFactory.getLocalInstance(ctx).getDepositDealBillInfo(new ObjectUuidPK(billId),sic);
		
		SelectorItemCollection tenSic=new SelectorItemCollection();
		tenSic.add("*");
		tenSic.add("sellProject.*");
		tenSic.add("tenCustomerList.fdcCustomer.sysCustomer.*");
		tenSic.add("tenancyRoomList.room.*");
		tenSic.add("tenancyAdviser.*");
		TenancyBillInfo ten=TenancyBillFactory.getLocalInstance(ctx).getTenancyBillInfo(new ObjectUuidPK(info.getTenancyBill().getId()),tenSic);
		
		FDCReceivingBillInfo revInfo=new FDCReceivingBillInfo();
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		revInfo.setNumber(iCodingRuleManager.getNumber(revInfo, info.getOrgUnit().getId().toString()));
		revInfo.setSourceBillId(info.getId().toString());
		revInfo.setSellProject(ten.getSellProject());
		revInfo.setTenancyObj(ten);
		CompanyOrgUnitCollection coCol=CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection("select * from where id='"+info.getOrgUnit().getId()+"'");
		if(coCol.size()>0){
			revInfo.setCompany(coCol.get(0));
		}else{
			throw new EASBizException(new NumericExceptionSubItem("100","生成退款单公司为空，请检查！"));
		}
		revInfo.setCurrency(CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("select * from where name='人民币'").get(0));
		revInfo.setExchangeRate(new BigDecimal(1));
		revInfo.setRevBillType(RevBillTypeEnum.refundment);
		revInfo.setRevBizType(RevBizTypeEnum.tenancy);
		revInfo.setOrgUnit(info.getOrgUnit());
		revInfo.setBookedDate(new Date());
		revInfo.setBizDate(new Date());
		revInfo.setCustomer(ten.getTenCustomerList().get(0).getFdcCustomer().getSysCustomer());
		revInfo.setRoom(ten.getTenancyRoomList().get(0).getRoom());
		revInfo.setTenancyUser(ten.getTenancyAdviser());
		
		BigDecimal amount=FDCHelper.ZERO;
		sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("moneyDefine.*");
		for(int i=0;i<info.getEntry().size();i++){
			if(info.getEntry().get(i).getAmount()==null||info.getEntry().get(i).getAmount().compareTo(FDCHelper.ZERO)<=0){
				continue;
			}
			FDCReceivingBillEntryInfo revEntry=new FDCReceivingBillEntryInfo();
			if(BOSUuid.read(info.getEntry().get(i).getSrcId()).getType().equals(new TenancyRoomPayListEntryInfo().getBOSType())){
				revEntry.setRevListType(RevListTypeEnum.tenRoomRev);
				
				TenancyRoomPayListEntryInfo srcInfo= TenancyRoomPayListEntryFactory.getLocalInstance(ctx).getTenancyRoomPayListEntryInfo(new ObjectUuidPK(info.getEntry().get(i).getSrcId()),sic);
				revEntry.setMoneyDefine(srcInfo.getMoneyDefine());
			}else{
				revEntry.setRevListType(RevListTypeEnum.tenOtherRev);
				
				TenBillOtherPayInfo srcInfo=TenBillOtherPayFactory.getLocalInstance(ctx).getTenBillOtherPayInfo(new ObjectUuidPK(info.getEntry().get(i).getSrcId()),sic);
				revEntry.setMoneyDefine(srcInfo.getMoneyDefine());
			}
			revEntry.setRevListId(info.getEntry().get(i).getSrcId());
			revEntry.setRoom(ten.getTenancyRoomList().get(0).getRoom());
			revEntry.setRevAmount(info.getEntry().get(i).getAmount().negate());
			
			revInfo.getEntries().add(revEntry);
			
			amount=FDCHelper.add(amount, info.getEntry().get(i).getAmount().negate());
		}
		revInfo.setBillStatus(RevBillStatusEnum.SUBMIT);
		revInfo.setAmount(amount);
		revInfo.setOriginalAmount(amount);
		AccountViewCollection acCol=AccountViewFactory.getLocalInstance(ctx).getAccountViewCollection("select * from where number='1001.01' and companyID.id='"+revInfo.getCompany().getId()+"'");
		if(acCol.size()>0){
			revInfo.setRevAccount(acCol.get(0));
		}
		FDCReceivingBillFactory.getLocalInstance(ctx).submitRev(revInfo, "com.kingdee.eas.fdc.tenancy.app.TenRevHandle");
		FDCReceivingBillFactory.getLocalInstance(ctx).submit(revInfo);
		FDCReceivingBillFactory.getLocalInstance(ctx).audit(revInfo.getId());
	}

	public void unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		FDCReceivingBillCollection col=FDCReceivingBillFactory.getLocalInstance(ctx).getFDCReceivingBillCollection("select * from where sourceBillId='"+billId+"'");
		if(col.size()>0){
			throw new EASBizException(new NumericExceptionSubItem("100","已生成租赁退款单，禁止反审批！"));
		}
		super.unAudit(ctx, billId);
	}
    
}