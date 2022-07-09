package com.kingdee.eas.fdc.contract.app;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
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

import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
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
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.BankNumCollection;
import com.kingdee.eas.fdc.contract.BankNumFactory;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillTypeFactory;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.ma.budget.BgItemCollection;
import com.kingdee.eas.ma.budget.BgItemFactory;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.util.app.DbUtil;

public class WSPaymentBillFacadeControllerBean extends AbstractWSPaymentBillFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.WSPaymentBillFacadeControllerBean");

	@Override
	protected String _synPaymentBill(Context ctx, String str)throws BOSException, EASBizException {
		JSONObject rs=genPaymentBill(ctx,str);
		String result="成功";
		if(rs.getString("state").equals("0")){
			result="失败";
		}
		String logStr="insert into t_log (name,context,number,createtime,state,msg) values('每刻付款单同步接口',?,'"+rs.get("number")+"',now(),'"+result+"','"+rs.get("msg")+"')";
	    Object[] params = new Object[]{str};
	    DbUtil.execute(ctx,logStr,params);
	    
		return rs.toString();
	}
	public JSONObject genPaymentBill(Context ctx,String str){
		JSONObject rs = new JSONObject();
		String rsNumber=null;
		try{
			JSONObject obj = JSONObject.fromObject(str);
			if(obj.get("number")==null||"".equals(obj.getString("number").trim())){
				rs.put("state", "0");
				rs.put("msg", "编码number为空！");
				
				return rs;
			}else{
				rsNumber=obj.getString("number").split(",")[0];
			}
			if(obj.get("company")==null||"".equals(obj.getString("company").trim())){
				rs.put("number", rsNumber);
				rs.put("state", "0");
				rs.put("msg", "公司company为空！");
				
				return rs;
			}
			if(obj.get("bizDate")==null||"".equals(obj.getString("bizDate").trim())){
				rs.put("number", rsNumber);
				rs.put("state", "0");
				rs.put("msg", "业务日期bizDate为空！");
				
				return rs;
			}
			if(obj.get("payeeName")==null||"".equals(obj.getString("payeeName").trim())){
				rs.put("number", rsNumber);
				rs.put("state", "0");
				rs.put("msg", "收款人名称payeeName为空！");
				
				return rs;
			}
			if(obj.get("payeeAccountBank")==null||"".equals(obj.getString("payeeAccountBank").trim())){
				rs.put("number", rsNumber);
				rs.put("state", "0");
				rs.put("msg", "收款账号payeeAccountBank为空！");
				
				return rs;
			}
			if(obj.get("type")==null||"".equals(obj.getString("type").trim())){
				rs.put("number", rsNumber);
				rs.put("state", "0");
				rs.put("msg", "类型type为空！");
				
				return rs;
			}
			if(obj.get("lxNumber")==null||"".equals(obj.getString("lxNumber").trim())){
				rs.put("number", rsNumber);
				rs.put("state", "0");
				rs.put("msg", "联行号lxNumber为空！");
				
				return rs;
			}
			if(obj.get("entry")==null){
				rs.put("number", rsNumber);
				rs.put("state", "0");
				rs.put("msg", "明细entry为空！");
				
				return rs;
			}
			String number=obj.getString("number");
			String company=obj.getString("company");
			String bizDate=obj.getString("bizDate");
			String payeeNumber=null;
			if(obj.get("payeeNumber")!=null){
				payeeNumber=obj.getString("payeeNumber");
			}
			String payeeName=obj.getString("payeeName");
			String payeeAccountBank=obj.getString("payeeAccountBank");
			String type=obj.getString("type");
			String lxNumber=obj.getString("lxNumber");
			
			JSONArray entryjs=obj.getJSONArray("entry");
			
			if(entryjs.size()==0){
				rs.put("number", rsNumber);
				rs.put("state", "0");
				rs.put("msg", "明细entry为空！");
				
				return rs;
			}
			PaymentBillInfo pay=new PaymentBillInfo();
			pay.setSourceSysType(SourceTypeEnum.CASH);
			pay.setSourceType(SourceTypeEnum.CASH);
			pay.setBizDate(FDCDateHelper.stringToDate(bizDate));
			pay.setDescription(number);
			pay.setFdcPayReqNumber("MK-"+number.split(",")[0]+"-"+type);
		
			BankNumCollection bankNum=BankNumFactory.getLocalInstance(ctx).getBankNumCollection("select * from where number='"+lxNumber+"'");
			if(bankNum.size()>0){
				pay.setPayeeBank(bankNum.get(0).getName());
			}else{
				rs.put("number", rsNumber);
				rs.put("state", "0");
				rs.put("msg", "联行号不存在！");
				
				return rs;
			}
			pay.setBankNumber(lxNumber);
			
			CompanyOrgUnitCollection companyCol=CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection("select * from where number='"+company+"'");
			if(companyCol.size()>0){
				pay.setCompany(companyCol.get(0));
			}else{
				rs.put("number", rsNumber);
				rs.put("state", "0");
				rs.put("msg", "公司不存在！");
				
				return rs;
			}
			pay.setCurrency(CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("select * from where number='RMB'").get(0));
			pay.setExchangeRate(new BigDecimal(1));
			pay.setPayBillType(PaymentBillTypeFactory.getLocalInstance(ctx).getPaymentBillTypeInfo("select * from where number='999'"));
			
			pay.setPayeeNumber(payeeNumber);
			pay.setPayeeName(payeeName);
			pay.setPayeeAccountBank(payeeAccountBank);
			
			BigDecimal total=new BigDecimal(0);
			for(int i=0;i<entryjs.size();i++){
				if(entryjs.getJSONObject(i).get("bgItem")==null||"".equals(entryjs.getJSONObject(i).getString("bgItem").trim())){
					rs.put("number", rsNumber);
					rs.put("state", "0");
					rs.put("msg", "费用类型bgItem为空！");
					
					return rs;
				}
				if(entryjs.getJSONObject(i).get("amount")==null||"".equals(entryjs.getJSONObject(i).getString("amount").trim())){
					rs.put("number", rsNumber);
					rs.put("state", "0");
					rs.put("msg", "金额amount为空！");
					
					return rs;
				}
				if(entryjs.getJSONObject(i).get("costCenter")==null||"".equals(entryjs.getJSONObject(i).getString("costCenter").trim())){
					rs.put("number", rsNumber);
					rs.put("state", "0");
					rs.put("msg", "成本中心costCenter为空！");
					
					return rs;
				}
				String bgItem=entryjs.getJSONObject(i).getString("bgItem");
				String amount=entryjs.getJSONObject(i).getString("amount");
				String costCenter=entryjs.getJSONObject(i).getString("costCenter");
//				String oppAccount=entryjs.getJSONObject(i).getString("oppAccount");
				String remark=null;
				if(entryjs.getJSONObject(i).get("remark")!=null){
					remark=entryjs.getJSONObject(i).getString("remark");
				}
				
				PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
				entry.setAmount(new BigDecimal(amount));
				entry.setLocalAmt(new BigDecimal(amount));
				entry.setActualAmt(new BigDecimal(amount));
				entry.setRemark(remark);
				
				ExpenseTypeCollection etCol=ExpenseTypeFactory.getLocalInstance(ctx).getExpenseTypeCollection("select * from where number='"+bgItem+"'");
				if(etCol.size()>0){
					entry.setExpenseType(etCol.get(0));
				}else{
					rs.put("number", rsNumber);
					rs.put("state", "0");
					rs.put("msg", "费用类型不存在！");
					
					return rs;
				}
				
//				AccountViewCollection accountCol=AccountViewFactory.getLocalInstance(ctx).getAccountViewCollection("select * from where number='"+oppAccount+"'");
//				if(accountCol.size()>0){
//					entry.setOppAccount(accountCol.get(0));
//				}else{
//					rs.put("state", "0");
//					rs.put("msg", "对方科目不存在！");
//					return rs;
//				}
				
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("number",costCenter,CompareType.EQUALS));
				view.setFilter(filter);
				CostCenterOrgUnitCollection costOrg =CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(view);
				if(costOrg.size()>0){
					entry.setCostCenter(costOrg.get(0));
				}else{
					rs.put("number", rsNumber);
					rs.put("state", "0");
					rs.put("msg", "成本中心不存在！");
					
					return rs;
				}
				
				total=total.add(new BigDecimal(amount));
				pay.getEntries().add(entry);
			}
			pay.setAmount(total);
			pay.setLocalAmt(total);
			pay.setActPayAmt(total);
			pay.setActPayLocAmt(total);
			
			PaymentBillFactory.getLocalInstance(ctx).save(pay);
		}catch (Exception e) {
			rs.put("number", rsNumber);
			rs.put("state", "0");
			rs.put("msg", e.getMessage());
			return rs;
		}
		rs.put("number", rsNumber);
    	rs.put("state", "1");
		rs.put("msg", "同步成功！");
		
		return rs;
	}
}