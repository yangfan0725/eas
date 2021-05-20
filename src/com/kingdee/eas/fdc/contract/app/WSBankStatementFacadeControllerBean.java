package com.kingdee.eas.fdc.contract.app;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Timestamp;

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
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.eas.base.vc.VerifyScopeEnum;
import com.kingdee.eas.basedata.assistant.AccountBankCollection;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fi.cas.BSSourceEnum;
import com.kingdee.eas.fi.cas.BankStatementFactory;
import com.kingdee.eas.fi.cas.BankStatementInfo;

public class WSBankStatementFacadeControllerBean extends AbstractWSBankStatementFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.WSBankStatementFacadeControllerBean");

	@Override
	protected String _synBankStatement(Context ctx, String str)throws BOSException, EASBizException {
		JSONObject obj = JSONObject.fromObject(str);
		
		JSONObject rs = new JSONObject();
		
		BankStatementInfo info=new BankStatementInfo();
		info.setNumber(obj.getString("ID"));
		
		String BANK_ACC=obj.getString("BANK_ACC");
		AccountBankCollection col=AccountBankFactory.getLocalInstance(ctx).getAccountBankCollection("select *,account.* from where bankaccountnumber='"+BANK_ACC+"'");
		if(col.size()>0){
			info.setAccountBank(col.get(0));
			info.setAccountView(col.get(0).getAccount());
		}else{
			rs.put("ID", obj.getString("ID"));
			rs.put("STATUS", "1");
			rs.put("MESSAGE", "本方账号不存在！");
			return rs.toString();
		}
		String CORP_CODE=obj.getString("CORP_CODE");
		CompanyOrgUnitCollection companyCol=CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection("select * from where number='"+CORP_CODE+"'");
		if(companyCol.size()>0){
			info.setCompany(companyCol.get(0));
		}else{
			rs.put("ID", obj.getString("ID"));
			rs.put("STATUS", "1");
			rs.put("MESSAGE", "公司不存在！");
			return rs.toString();
		}
		
		if(obj.getString("CD_SIGN").equals("1")){
			info.setDebitAmount(new BigDecimal(obj.getString("AMT")));
			info.setIsDebit(true);
		}else{
			info.setCreditAmount(new BigDecimal(obj.getString("AMT")));
			info.setIsDebit(false);
		}
		info.setSource(BSSourceEnum.BE_IMPORT);
		
		info.setVerifyStatus(VerifyScopeEnum.NOVA);
		info.setBizTime(new Timestamp(FDCDateHelper.stringToDate(obj.getString("TRANS_TIME")).getTime()));
		info.setBizDate(FDCDateHelper.stringToDate(obj.getString("TRANS_TIME")));
		info.setCreateDate(FDCDateHelper.stringToDate(obj.getString("TRANS_TIME")));
		
		CompanyOrgUnitInfo com=CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(info.getCompany().getId()));
		PeriodInfo period = FDCUtils.fetchPeriod(ctx,com.getAccountPeriodType().getId().toString(),info.getBizDate());
		
		info.setPeriod(period);
		
		info.setDescription(obj.getString("ABS"));
		
		info.setOppAccountNumber(obj.getString("OPP_ACC_NO"));
		info.setOppUnit(obj.getString("OPP_ACC_NAME"));
		info.setCurrency(CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("select * from where number='RMB'").get(0));
		
		
		
		BankStatementFactory.getLocalInstance(ctx).submit(info);
		
		rs.put("ID", obj.getString("ID"));
		rs.put("STATUS", "2");
		return rs.toString();
		
	}
    
    
}