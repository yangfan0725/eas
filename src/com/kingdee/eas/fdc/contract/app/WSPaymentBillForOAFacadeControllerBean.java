package com.kingdee.eas.fdc.contract.app;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
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

import com.kingdee.eas.basedata.assistant.AccountBankCollection;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeSysEnum;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.ExpenseTypeCollection;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fi.cas.FeeTypeCollection;
import com.kingdee.eas.fi.cas.FeeTypeFactory;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillTypeFactory;
import com.kingdee.eas.fi.cas.SourceTypeEnum;

public class WSPaymentBillForOAFacadeControllerBean extends AbstractWSPaymentBillForOAFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.WSPaymentBillForOAFacadeControllerBean");

	@Override
	protected String _synPaymentBill(Context ctx, String str)
			throws BOSException, EASBizException {
		JSONObject obj = JSONObject.fromObject(str);
		String number=obj.getString("number");
		String company=obj.getString("company");
		String bizDate=obj.getString("bizDate");
		String payeeNumber=obj.getString("payeeNumber");
		String payeeName=obj.getString("payeeName");
		String payeeBank=obj.getString("payeeBank");
		String payeeAccountBank=obj.getString("payeeAccountBank");
		String bizType=obj.getString("bizType");
		
		JSONArray entryjs=obj.getJSONArray("entry");
		
		PaymentBillInfo pay=new PaymentBillInfo();
		pay.setSourceSysType(SourceTypeEnum.CASH);
		pay.setSourceType(SourceTypeEnum.CASH);
		pay.setBizDate(FDCDateHelper.stringToDate(bizDate));
		pay.setDescription(number);
		
		JSONObject rs = new JSONObject();
		
//		FeeTypeCollection feeCol=FeeTypeFactory.getLocalInstance(ctx).getFeeTypeCollection("select * from where number='"+bizType+"'");
//		if(feeCol.size()>0){
//			pay.setFeeType(feeCol.get(0));
//		}else{
//			rs.put("state", "0");
//			rs.put("msg", "收付类别不存在！");
//			return rs.toString();
//		}
		
		CompanyOrgUnitCollection companyCol=CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection("select * from where number='"+company+"'");
		if(companyCol.size()>0){
			pay.setCompany(companyCol.get(0));
		}else{
			rs.put("state", "0");
			rs.put("msg", "公司不存在！");
			return rs.toString();
		}
		pay.setCurrency(CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("select * from where number='RMB'").get(0));
		pay.setExchangeRate(new BigDecimal(1));
		pay.setPayBillType(PaymentBillTypeFactory.getLocalInstance(ctx).getPaymentBillTypeInfo("select * from where number='999'"));
		
		CompanyOrgUnitCollection pcompanyCol=CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection("select * from where number='"+payeeNumber+"'");
		if(companyCol.size()>0){
			pay.setPayeeID(pcompanyCol.get(0).getId().toString());
			pay.setPayeeNumber(payeeNumber);
			pay.setPayeeName(payeeName);
		}else{
			rs.put("state", "0");
			rs.put("msg", "收款方不存在！");
			return rs.toString();
		}
		pay.setPayeeType(AsstActTypeFactory.getLocalInstance(ctx).getAsstActType(AsstActTypeSysEnum.COMPANYORG));
		pay.setPayeeBank(payeeBank);
		pay.setPayeeAccountBank(payeeAccountBank);
		
		BigDecimal total=new BigDecimal(0);
		for(int i=0;i<entryjs.size();i++){
//			String bgItem=entryjs.getJSONObject(i).getString("bgItem");
			String amount=entryjs.getJSONObject(i).getString("amount");
//			String costCenter=entryjs.getJSONObject(i).getString("costCenter");
//			String oppAccount=entryjs.getJSONObject(i).getString("oppAccount");
			String remark=entryjs.getJSONObject(i).getString("remark");
			
			PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
			entry.setAmount(new BigDecimal(amount));
			entry.setLocalAmt(new BigDecimal(amount));
			entry.setActualAmt(new BigDecimal(amount));
			entry.setRemark(remark);
			
//			ExpenseTypeCollection etCol=ExpenseTypeFactory.getLocalInstance(ctx).getExpenseTypeCollection("select * from where number='"+bgItem+"'");
//			if(etCol.size()>0){
//				entry.setExpenseType(etCol.get(0));
//			}else{
//				rs.put("state", "0");
//				rs.put("msg", "费用类型不存在！");
//				return rs.toString();
//			}
			
//			AccountViewCollection accountCol=AccountViewFactory.getLocalInstance(ctx).getAccountViewCollection("select * from where number='"+oppAccount+"'");
//			if(accountCol.size()>0){
//				entry.setOppAccount(accountCol.get(0));
//			}else{
//				rs.put("state", "0");
//				rs.put("msg", "对方科目不存在！");
//				return rs.toString();
//			}
			
//			EntityViewInfo view=new EntityViewInfo();
//			FilterInfo filter=new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("number",costCenter,CompareType.EQUALS));
//			view.setFilter(filter);
//			CostCenterOrgUnitCollection costOrg =CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(view);
//			if(costOrg.size()>0){
//				entry.setCostCenter(costOrg.get(0));
//			}else{
//				rs.put("state", "0");
//				rs.put("msg", "成本中心不存在！");
//				return rs.toString();
//			}
			
			total=total.add(new BigDecimal(amount));
//			BgItemCollection bgitemCol=BgItemFactory.getLocalInstance(ctx).getBgItemCollection("select * from where number='"+bgItem+"'");
//			if(bgitemCol.size()>0){
//				entry.setOutBgItemId(bgitemCol.get(0).getId().toString());
//				entry.setOutBgItemName(bgitemCol.get(0).getName());
//				entry.setOutBgItemNumber(bgitemCol.get(0).getNumber());
//			}else{
//				rs.put("state", "0");
//				rs.put("msg", "预算项目不存在！");
//				return rs.toString();
//			}
			pay.getEntries().add(entry);
		}
		pay.setAmount(total);
		pay.setLocalAmt(total);
		pay.setActPayAmt(total);
		pay.setActPayLocAmt(total);
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name","公司"));
		view.setFilter(filter);
		AsstActTypeCollection assCol=AsstActTypeFactory.getLocalInstance(ctx).getAsstActTypeCollection(view);
		if(assCol.size()>0){
			pay.setPayeeType(assCol.get(0));
		}
		
		String payerBankAccountNumber=obj.getString("payerBankAccountNumber");
		
		
		AccountBankCollection abCol=AccountBankFactory.getLocalInstance(ctx).getAccountBankCollection("select *,bank.*,account.* from where bankAccountNumber='"+payerBankAccountNumber+"'");
		if(abCol.size()>0){
			pay.setPayerAccountBank(abCol.get(0));
			pay.setPayerBank(abCol.get(0).getBank());
			pay.setPayerAccount(abCol.get(0).getAccount());
		}else{
			rs.put("state", "0");
			rs.put("msg", "付款账户不存在！");
			return rs.toString();
		}
		PaymentBillFactory.getLocalInstance(ctx).save(pay);
		
    	rs.put("state", "1");
		rs.put("msg", "同步成功！");
		return rs.toString();
	}

	@Override
	protected String _synJKPaymentBill(Context ctx, String str)
			throws BOSException, EASBizException {
		JSONObject obj = JSONObject.fromObject(str);
		String company=obj.getString("company");
		String payeeName=obj.getString("payeeName");
		String description=obj.getString("description");
		String usage=obj.getString("usage");
		String url=obj.getString("url");
		
		JSONArray entryjs=obj.getJSONArray("entry");
		
		PaymentBillInfo pay=new PaymentBillInfo();
		pay.setSourceSysType(SourceTypeEnum.CASH);
		pay.setSourceType(SourceTypeEnum.CASH);
		pay.setBizDate(new Date());
		pay.setDescription(description);
		pay.setUsage(usage);
		pay.setSummary(url);
		
		JSONObject rs = new JSONObject();
		
		CompanyOrgUnitCollection companyCol=CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection("select * from where number='"+company+"'");
		if(companyCol.size()>0){
			pay.setCompany(companyCol.get(0));
		}else{
			rs.put("state", "0");
			rs.put("msg", "公司不存在！");
			return rs.toString();
		}
		pay.setCurrency(CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("select * from where number='RMB'").get(0));
		pay.setExchangeRate(new BigDecimal(1));
		pay.setPayBillType(PaymentBillTypeFactory.getLocalInstance(ctx).getPaymentBillTypeInfo("select * from where number='999'"));
		
		pay.setPayeeName(payeeName);
		
		BigDecimal total=new BigDecimal(0);
		for(int i=0;i<entryjs.size();i++){
			String bgItem=entryjs.getJSONObject(i).getString("bgItem");
			String amount=entryjs.getJSONObject(i).getString("amount");
//			String costCenter=entryjs.getJSONObject(i).getString("costCenter");
			String remark=entryjs.getJSONObject(i).getString("remark");
			
			PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
			entry.setAmount(new BigDecimal(amount));
			entry.setLocalAmt(new BigDecimal(amount));
			entry.setActualAmt(new BigDecimal(amount));
			entry.setRemark(remark);
			
			ExpenseTypeCollection etCol=ExpenseTypeFactory.getLocalInstance(ctx).getExpenseTypeCollection("select * from where number='"+bgItem+"'");
			if(etCol.size()>0){
				entry.setExpenseType(etCol.get(0));
			}else{
				rs.put("state", "0");
				rs.put("msg", "费用类型不存在！");
				return rs.toString();
			}
			
//			EntityViewInfo view=new EntityViewInfo();
//			FilterInfo filter=new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("number",costCenter,CompareType.EQUALS));
//			view.setFilter(filter);
//			CostCenterOrgUnitCollection costOrg =CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(view);
//			if(costOrg.size()>0){
//				entry.setCostCenter(costOrg.get(0));
//			}else{
//				rs.put("state", "0");
//				rs.put("msg", "成本中心不存在！");
//				return rs.toString();
//			}
			
			total=total.add(new BigDecimal(amount));
			pay.getEntries().add(entry);
		}
		pay.setAmount(total);
		pay.setLocalAmt(total);
		pay.setActPayAmt(total);
		pay.setActPayLocAmt(total);
		
		
		PaymentBillFactory.getLocalInstance(ctx).save(pay);
		
    	rs.put("state", "1");
		rs.put("msg", "同步成功！");
		return rs.toString();
	}
    
    
    
}