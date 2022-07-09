package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.webservice.login.EASLoginProxy;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;

import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.ctrl.analysis.web.repository.ConectionCreator;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.cbos.process.vm.internal.t.a.Int;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.AttachmentStorageTypeEnum;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.FtpConfigFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractSourceInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.LandDeveloperInfo;
import com.kingdee.eas.fdc.basedata.PaymentTypeCollection;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.BankNumInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillRateEntryInfo;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillInfo;
import com.kingdee.eas.fdc.contract.ContractMarketEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractSourceEnum;
import com.kingdee.eas.fdc.contract.ContractWFTypeInfo;
import com.kingdee.eas.fdc.contract.ContractWTInvoiceEntryInfo;
import com.kingdee.eas.fdc.contract.ContractWTMarketEntryInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextBgEntryInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.CostPropertyEnum;
import com.kingdee.eas.fdc.contract.FeeTypeEnum;
import com.kingdee.eas.fdc.contract.MarketProjectFactory;
import com.kingdee.eas.fdc.contract.MarketProjectInfo;
import com.kingdee.eas.fdc.contract.PayContentTypeInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.SupplierApplyFactory;
import com.kingdee.eas.fdc.contract.SupplierApplyInfo;
import com.kingdee.eas.fdc.contract.UrgentDegreeEnum;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.invite.TenderAccepterResultEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillTypeInfo;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;
import com.sun.imageio.plugins.common.BogusColorSpace;


public class OAContractFacadeControllerBean extends AbstractOAContractFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.OAContractFacadeControllerBean");

    protected String _ifHasAttachFile(Context ctx, String str)
			throws BOSException {
		String result="F";
		JSONObject data = JSONObject.fromObject(str);
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		String contractBillId = data.getString("contractBillId");
		if(contractBillId!=null && contractBillId!=""){
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("attachment.id"));
		sic.add(new SelectorItemInfo("attachment.name"));
		sic.add(new SelectorItemInfo("attachment.createTime"));
		sic.add(new SelectorItemInfo("attachment.attachID"));
		sic.add(new SelectorItemInfo("attachment.beizhu"));
		sic.add(new SelectorItemInfo("assoType"));
		sic.add(new SelectorItemInfo("boID"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", contractBillId));
		filter.getFilterItems().add(new FilterItemInfo("attachment.beizhu", "1",CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("attachment.beizhu", null,CompareType.EQUALS));
		filter.setMaskString("#0 and (#1 or #2)");
		EntityViewInfo evi = new EntityViewInfo();
		evi.getSorter().add(new SorterItemInfo("boID"));
		evi.getSorter().add(new SorterItemInfo("attachment.name"));
		evi.setFilter(filter);
		evi.setSelector(sic);
		BoAttchAssoCollection cols = null;
		try {
			cols = BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(evi);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		boolean flag = false;
		if (cols != null && cols.size() > 0) {
			result="T";
			}
		}
		data.put("file",result);
		return data.toString();
	}
	protected String _saveSupplierApply(Context ctx, String str)throws BOSException {
		JSONObject data = JSONObject.fromObject(str);
		SupplierApplyInfo info=new SupplierApplyInfo();
		if(data.get("id")!=null){
			String id=data.getString("id");
			info.setId(BOSUuid.read(id));
		}
		this.setSupplierApply(str, info);
		JSONObject obj=new JSONObject();
		try {
			String billId = SupplierApplyFactory.getLocalInstance(ctx).save(info).toString();
			setAttachment(ctx,data,billId);
			obj.put("result", "success");
			obj.put("id", billId);
		} catch (EASBizException e) {
			e.printStackTrace();
			obj.put("message", e.getMessage());
			obj.put("result", "fault");
		}
		return obj.toString();
	}
	public void setAttachment(Context ctx,JSONObject data,String billId) throws EASBizException, BOSException{
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID",billId));
		BoAttchAssoFactory.getLocalInstance(ctx).delete(filter);
		
		Object attach=data.get("attach");
		if(attach!=null){
			JSONArray attachs=data.getJSONArray("attach");
			for(int i=0;i<attachs.size();i++){
				String FAttachName=attachs.getJSONObject(i).getString("FName");
				String FRemotePath=attachs.getJSONObject(i).getString("FRemotePath");
				String FNumber=attachs.getJSONObject(i).getString("FNumber");
				String FDescription=attachs.getJSONObject(i).getString("FDescription");
				String FSize=attachs.getJSONObject(i).getString("FSize");
				
				AttachmentInfo attachmentInfo=new AttachmentInfo();
				attachmentInfo.setNumber(FNumber);
				attachmentInfo.setName(FAttachName);
				attachmentInfo.setRemotePath(FRemotePath);
				attachmentInfo.setSize(FSize);
				attachmentInfo.setSimpleName(FRemotePath.substring(FRemotePath.lastIndexOf(".")+1, FRemotePath.length()));
				
				BoAttchAssoInfo boAttchAssoInfo=new BoAttchAssoInfo();
				
				if(FDescription!=null &&!"".equals(FDescription)){
					attachmentInfo.setDescription(FDescription);
					if(FDescription.equals("EAS")){
						attachmentInfo.setStorageType(AttachmentStorageTypeEnum.EASSERVER);
					}else if(FDescription.equals("WEB")){
						attachmentInfo.setStorageType(AttachmentStorageTypeEnum.FTP);
						attachmentInfo.setFtp(FtpConfigFactory.getLocalInstance(ctx).getFtpConfigCollection("select * from where name='OA'").get(0));
					}else if(FDescription.equals("天联云")){
						attachmentInfo.setStorageType(AttachmentStorageTypeEnum.FTP);
						attachmentInfo.setFtp(FtpConfigFactory.getLocalInstance(ctx).getFtpConfigCollection("select * from where name='天联云'").get(0));
					}
					
					boAttchAssoInfo.setAssoType(FDescription+"附件");
				}
				boAttchAssoInfo.setBoID(billId);
				boAttchAssoInfo.setAttachment(attachmentInfo);
				
				attachmentInfo.getBoAttchAsso().add(boAttchAssoInfo);
				
				AttachmentFactory.getLocalInstance(ctx).save(attachmentInfo);
			}
		}
	}
	protected String _auditSupplierApply(Context ctx, String str) throws BOSException {
		JSONObject data = JSONObject.fromObject(str);
		JSONObject obj = new JSONObject();
		String id = data.getString("id");
		try {
			SupplierApplyFactory.getLocalInstance(ctx).audit(BOSUuid.read(id));
			obj.put("result", "success");
		} catch (EASBizException e) {
			e.printStackTrace();
			obj.put("message", e.getMessage());
			obj.put("result", "fault");
		}
		return obj.toString();
	}
	public ContractWithoutTextInfo setConractWithoutText(Context ctx,String str,ContractWithoutTextInfo info) throws BOSException, EASBizException{
		info.setFeeType(FeeTypeEnum.FEE);
		info.setSourceType(SourceTypeEnum.ADDNEW);
		info.setIsCostSplit(true);
		info.setConSplitExecState(ConSplitExecStateEnum.COMMON);
		info.setSplitState(CostSplitStateEnum.NOSPLIT);
		info.setIsInvoice(true);
		info.setIsBgControl(true);
		info.setIsNeedPaid(false);
		
		JSONObject data = JSONObject.fromObject(str);
		Object number=data.get("number");
		Object name=data.get("name");
		Object projectId=data.get("projectId");
		Object conTypeId=data.get("conTypeId");
		Object hygh=data.get("hygh");
		Object marketProjectId = data.get("marketProjectId");
		Object costAccountId = data.get("costAccountId");
		Object currencyId = data.get("currencyId");
		Object originalAmount = data.get("originalAmount");
		Object amount = data.get("amount");
		Object payBillTypeId=data.get("payBillTypeId");
		Object payContentTypeId=data.get("payContentTypeId");
		Object bizDate=data.get("bizDate");
		Object unionBankId = data.get("unionBankId"); 
		Object bank = data.get("bank"); 
		Object bankAccount = data.get("bankAccount"); 
		Object taxerQua = data.get("taxerQua"); 
		Object taxerNum = data.get("taxerNum");
		Object receiveUnitId=data.get("receiveUnitId");
		Object personId=data.get("personId");
		Object settlementTypeId=data.get("settlementTypeId");
		Object applierId = data.get("applierId");
		Object useDepartmentId=data.get("useDepartmentId");
		Object costedCompanyId=data.get("costedCompanyId");
		Object costedDeptId=data.get("costedDeptId");
		Object isJT=data.get("isJT");
		Object invoiceAmt=data.get("invoiceAmt");
		Object rateAmount=data.get("rateAmount");
		Object description=data.get("description");
		Object periodId=data.get("periodId");
		
		if(periodId!=null){
			PeriodInfo p=new PeriodInfo();
			p.setId(BOSUuid.read(periodId.toString()));
			info.setPeriod(p);
		}
		if(description!=null){
			info.setDescription(description.toString());
		}
		if(projectId!=null){
			CurProjectInfo projectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId.toString()));
			info.setCurProject(projectInfo);
			info.setCU(projectInfo.getCU());
			
			info.setCompany(projectInfo.getCostCenter().castToFullOrgUnitInfo());
			info.setOrgUnit(projectInfo.getCostCenter().castToFullOrgUnitInfo());
		}
		if(name!=null){
			info.setName(name.toString());
		}
		if(number!=null){
			info.setNumber(number.toString());
		}
		if(conTypeId!=null){
			ContractTypeInfo cInfo = new ContractTypeInfo();
			cInfo.setId(BOSUuid.read(conTypeId.toString()));
			info.setContractType(cInfo);
		}
		if(hygh!=null){
			ProgrammingContractInfo pInfo = new ProgrammingContractInfo();
			pInfo.setId(BOSUuid.read(hygh.toString()));
			info.setProgrammingContract(pInfo);
		}
		if(marketProjectId!=null){
			MarketProjectInfo mInfo = new MarketProjectInfo();
			mInfo.setId(BOSUuid.read(marketProjectId.toString()));
			info.setMarketProject(mInfo);
		}
		if(costAccountId!=null){
			CostAccountInfo cinfo=new CostAccountInfo();
			cinfo.setId(BOSUuid.read(costAccountId.toString()));
			info.setMpCostAccount(cinfo);
		}
		if(currencyId!=null){
			CurrencyInfo cInfo = new CurrencyInfo();
			cInfo.setId(BOSUuid.read(currencyId.toString()));
			info.setCurrency(cInfo);
		}
		if(originalAmount!=null){
			BigDecimal orgAmount = new BigDecimal(originalAmount.toString());
			info.setOriginalAmount(orgAmount);
		}
		if(amount!=null){
			BigDecimal amount1 = new BigDecimal(amount.toString());
			info.setAmount(amount1);
		}
		if(payBillTypeId!=null){
			PaymentBillTypeInfo pb=new PaymentBillTypeInfo();
			pb.setId(BOSUuid.read(payBillTypeId.toString()));
			info.setPayBillType(pb);
		}
		if(payContentTypeId!=null){
			PayContentTypeInfo pct=new PayContentTypeInfo();
			pct.setId(BOSUuid.read(payContentTypeId.toString()));
			info.setPayContentType(pct);
		}
		if(bizDate!=null){
			Date date = FDCDateHelper.stringToDate(bizDate.toString());
			info.setBookedDate(date);
		}
		if(unionBankId!=null){
			BankNumInfo bnInfo=new BankNumInfo();
			bnInfo.setId(BOSUuid.read(unionBankId.toString()));
			info.setLxNum(bnInfo);
		}
		if(bank!=null){
			info.setBank(bank.toString());
		}
		if(bankAccount!=null){
			info.setBankAcct(bankAccount.toString());
		}
		if(taxerQua!=null){
			if(taxerQua.toString().equals("一般纳税人")){
				info.setTaxerQua(TaxerQuaEnum.NOMAL);
			}
			if(taxerQua.toString().equals("小规模纳税人")){
				info.setTaxerQua(TaxerQuaEnum.SMALL);
			}
			if(taxerQua.toString().equals("非增值税纳税人")){
				info.setTaxerQua(TaxerQuaEnum.UNNOMAL);
			}
		}
		if(taxerNum!=null){
			info.setTaxerNum(taxerNum.toString());
		}
		if(receiveUnitId!=null){
			SupplierInfo s=new SupplierInfo();
			s.setId(BOSUuid.read(receiveUnitId.toString()));
			info.setReceiveUnit(s);
		}
		if(personId!=null){
			PersonInfo p=new PersonInfo();
			p.setId(BOSUuid.read(personId.toString()));
			info.setPerson(p);
		}
		if(settlementTypeId!=null){
			SettlementTypeInfo st=new SettlementTypeInfo();
			st.setId(BOSUuid.read(settlementTypeId.toString()));
			info.setSettlementType(st);
		}
		if(applierId!=null){
			PersonInfo p=new PersonInfo();
			p.setId(BOSUuid.read(applierId.toString()));
			info.setApplier(p);
		}
		if(useDepartmentId!=null){
			AdminOrgUnitInfo aou=new AdminOrgUnitInfo();
			aou.setId(BOSUuid.read(useDepartmentId.toString()));
			info.setUseDepartment(aou);
		}
		if(costedCompanyId!=null){
			CompanyOrgUnitInfo cou=new CompanyOrgUnitInfo();
			cou.setId(BOSUuid.read(costedCompanyId.toString()));
			info.setCostedCompany(cou);
		}
		if(costedDeptId!=null){
			CostCenterOrgUnitInfo ccou=new CostCenterOrgUnitInfo();
			ccou.setId(BOSUuid.read(costedDeptId.toString()));
			info.setCostedDept(ccou);
		}
		if(isJT!=null){
			info.setIsJT(Boolean.valueOf(isJT.toString()));
		}
		if(invoiceAmt!=null){
			info.setInvoiceAmt(new BigDecimal(invoiceAmt.toString()));
		}
		if(rateAmount!=null){
			info.setRateAmount(new BigDecimal(rateAmount.toString()));
		}
		info.getMarketEntry().clear();
		if(data.get("marketConArray")!=null){
			JSONArray marketConArray=data.getJSONArray("marketConArray");
			for(int i=0;i<marketConArray.size();i++){
				ContractWTMarketEntryInfo entry=new ContractWTMarketEntryInfo();
				if(marketConArray.getJSONObject(i).get("date")!=null){
					entry.setDate(FDCDateHelper.stringToDate(marketConArray.getJSONObject(i).getString("date")));
				}
				if(marketConArray.getJSONObject(i).get("amount")!=null){
					entry.setAmount(new BigDecimal(marketConArray.getJSONObject(i).getString("amount")));
				}
				if(marketConArray.getJSONObject(i).get("rate")!=null){
					entry.setRate(new BigDecimal(marketConArray.getJSONObject(i).getString("rate")));
				}
				if(marketConArray.getJSONObject(i).get("remark")!=null){
					entry.setRemark(marketConArray.getJSONObject(i).getString("remark"));
				}
				if(marketConArray.getJSONObject(i).get("content")!=null){
					entry.setContent(marketConArray.getJSONObject(i).getString("content"));
				}
				info.getMarketEntry().add(entry);
			}
		}
		info.getBgEntry().clear();
		if(data.get("bgArray")!=null){
			JSONArray bgArray=data.getJSONArray("bgArray");
			for(int i=0;i<bgArray.size();i++){
				ContractWithoutTextBgEntryInfo entry=new ContractWithoutTextBgEntryInfo();
				if(bgArray.getJSONObject(i).get("expenseTypeId")!=null){
					ExpenseTypeInfo et=new ExpenseTypeInfo();
					et.setId(BOSUuid.read(bgArray.getJSONObject(i).get("expenseTypeId").toString()));
					entry.setExpenseType(et);
				}
				if(bgArray.getJSONObject(i).get("accountViewId")!=null){
					AccountViewInfo av=new AccountViewInfo();
					av.setId(BOSUuid.read(bgArray.getJSONObject(i).get("accountViewId").toString()));
					entry.setAccountView(av);
				}
				if(bgArray.getJSONObject(i).get("amount")!=null){
					entry.setRequestAmount(new BigDecimal(bgArray.getJSONObject(i).get("amount").toString()));
					entry.setAmount(new BigDecimal(bgArray.getJSONObject(i).get("amount").toString()));
				}
				info.getBgEntry().add(entry);
			}
		}
		info.getInvoiceEntry().clear();
		if(data.get("invoiceArray")!=null){
			JSONArray invoiceArray=data.getJSONArray("invoiceArray");
			for(int i=0;i<invoiceArray.size();i++){
				ContractWTInvoiceEntryInfo entry=new ContractWTInvoiceEntryInfo();
				if(invoiceArray.getJSONObject(i).get("invoiceNumber")!=null){
					entry.setInvoiceNumber(invoiceArray.getJSONObject(i).get("invoiceNumber").toString());
				}
//				if(invoiceArray.getJSONObject(i).get("invoiceType")!=null){
//					if(invoiceArray.getJSONObject(i).get("invoiceType").toString().equals("增值税专用发票")){
//						entry.setInvoiceType(WTInvoiceTypeEnum.SPECIAL);
//					}
//					if(invoiceArray.getJSONObject(i).get("invoiceType").toString().equals("增值税普通发票")){
//						entry.setInvoiceType(WTInvoiceTypeEnum.ORDINARY);
//					}
//					if(invoiceArray.getJSONObject(i).get("invoiceType").toString().equals("普通发票")){
//						entry.setInvoiceType(WTInvoiceTypeEnum.NOMAL);
//					}
//					if(invoiceArray.getJSONObject(i).get("invoiceType").toString().equals("收据")){
//						entry.setInvoiceType(WTInvoiceTypeEnum.RECEIPT);
//					}
//				}
				if(invoiceArray.getJSONObject(i).get("bizDate")!=null){
					entry.setBizDate(FDCDateHelper.stringToDate(invoiceArray.getJSONObject(i).get("bizDate").toString()));
				}
				if(invoiceArray.getJSONObject(i).get("totalAmount")!=null){
					entry.setTotalAmount(new BigDecimal(invoiceArray.getJSONObject(i).get("totalAmount").toString()));
				}
				if(invoiceArray.getJSONObject(i).get("rate")!=null){
					entry.setRate(new BigDecimal(invoiceArray.getJSONObject(i).get("rate").toString()));
				}
				if(invoiceArray.getJSONObject(i).get("amount")!=null){
					entry.setAmount(new BigDecimal(invoiceArray.getJSONObject(i).get("amount").toString()));
				} 
				info.getInvoiceEntry().add(entry);
			}
		}
		PayRequestBillInfo prbi = new PayRequestBillInfo();
		String sSql = "select * where IsEnabled=1 and payType.id ='Ga7RLQETEADgAAC6wKgOlOwp3Sw='";			
		PaymentTypeCollection paymentTypes = PaymentTypeFactory.getLocalInstance(ctx).getPaymentTypeCollection(sSql);
		if(paymentTypes!=null && paymentTypes.size()>0){
			prbi.setPaymentType(paymentTypes.get(0));
		}
    	prbi.setUseDepartment(info.getUseDepartment());
    	prbi.setNumber(info.getNumber());
    	prbi.setPayDate(info.getSignDate());
    	prbi.setSupplier(info.getReceiveUnit());
    	prbi.setPerson(info.getPerson());
    	prbi.setSettlementType(info.getSettlementType());    	
    	prbi.setRecBank(info.getBank());
    	prbi.setRecAccount(info.getBankAcct());
		prbi.setUrgentDegree(UrgentDegreeEnum.NORMAL);
    	prbi.setCurrency(info.getCurrency());
    	prbi.setLatestPrice(info.getAmount());
    	prbi.setExchangeRate(new BigDecimal(1));
    	
    	prbi.setPaymentProportion(new BigDecimal(100));
    	prbi.setCompletePrjAmt(info.getAmount());
    	prbi.setAmount(info.getAmount());
    	prbi.setOriginalAmount(info.getOriginalAmount());
    	
    	prbi.setCurProject(info.getCurProject());
    	prbi.setSourceType(SourceTypeEnum.ADDNEW);
    	prbi.setInvoiceNumber(info.getInvoiceNumber());
    	prbi.setInvoiceDate(info.getInvoiceDate());
    	prbi.setInvoiceAmt(info.getInvoiceAmt());
    	prbi.setAllInvoiceAmt(new BigDecimal(info.getAllInvoiceAmt()));
    	
    	prbi.setIsBgControl(info.isIsBgControl());
    	prbi.setApplier(info.getApplier());
    	prbi.setApplierCompany(info.getApplierCompany());
    	prbi.setApplierOrgUnit(info.getApplierOrgUnit());
    	prbi.setCostedCompany(info.getCostedCompany());
    	prbi.setCostedDept(info.getCostedDept());
    	prbi.setPayBillType(info.getPayBillType());
    	prbi.setPayContentType(info.getPayContentType());
    	prbi.setLocalCurrency(info.getCurrency());
    	prbi.setLxNum(info.getLxNum());
    	prbi.setIsPay(!info.isIsNeedPaid());
    	for(int i=0;i<info.getBgEntry().size();i++){
    		PayRequestBillBgEntryInfo entry=new PayRequestBillBgEntryInfo();
    		entry.setSeq(i);
    		entry.setExpenseType(info.getBgEntry().get(i).getExpenseType());
    		entry.setAccountView(info.getBgEntry().get(i).getAccountView());
    		entry.setRequestAmount(info.getBgEntry().get(i).getRequestAmount());
    		entry.setAmount(info.getBgEntry().get(i).getAmount());
    		entry.setBgItem(info.getBgEntry().get(i).getBgItem());
    		prbi.getBgEntry().add(entry);
    	}
    	Object realSupplierId=data.get("realSupplierId");
		if(realSupplierId!=null){
			SupplierInfo s=new SupplierInfo();
			s.setId(BOSUuid.read(realSupplierId.toString()));
			prbi.setRealSupplier(s);
		}
		
		Object moneyDesc=data.get("moneyDesc");
		if(moneyDesc!=null){
			prbi.setMoneyDesc(moneyDesc.toString());
			prbi.setUsage(moneyDesc.toString());
		}
		
		Object capitalAmount=data.get("capitalAmount");
		if(capitalAmount!=null){
			prbi.setCapitalAmount(capitalAmount.toString());
		}
		
		info.put("PayRequestBillInfo",prbi);
		return info;
	}
	public SupplierApplyInfo setSupplierApply(String str,SupplierApplyInfo info){
		JSONObject data = JSONObject.fromObject(str);
		Object bank = data.get("bank");
		Object name = data.get("name");
		Object number = data.get("number");
		Object remark = data.get("remark");
		Object sourceFunction=data.get("sourceFunction");
		Object taxerQua = data.get("taxerQua");
		Object taxerNum=data.get("taxerNum");
		Object bankAccount=data.get("bankAccount");
		Object orgId=data.get("orgId");
		
		if(bank!=null){
			info.setBank(bank.toString());
		}
		if(number!=null){
			info.setNumber(number.toString());
		}
		if(name!=null){
			info.setName(name.toString());
		}
		if(orgId!=null){
			FullOrgUnitInfo unit=new FullOrgUnitInfo();
			BOSUuid unitId = BOSUuid.read(orgId.toString());
			unit.setId(unitId);
			info.setOrgUnit(unit);
		}
		if(bankAccount!=null){
			info.setBankAccount(bankAccount.toString());
		}
		if(taxerNum!=null){
			info.setTaxerNum(taxerNum.toString());
		}
		if(taxerQua!=null){
			if(taxerQua.toString().equals("一般纳税人")){
				info.setTaxerQua(TaxerQuaEnum.NOMAL);
			}
			if(taxerQua.toString().equals("小规模纳税人")){
				info.setTaxerQua(TaxerQuaEnum.SMALL);
			}
			if(taxerQua.toString().equals("非增值税纳税人")){
				info.setTaxerQua(TaxerQuaEnum.UNNOMAL);
			}
		}
		if(sourceFunction!=null){
			info.setSourceFunction(sourceFunction.toString());
		}
		if(remark!=null){
			info.setDescription(remark.toString());
		}
		return info;
	}
	public ContractBillInfo setContractBill(Context ctx,String str,ContractBillInfo info) throws BOSException, EASBizException{
		info.setPayPercForWarn(new BigDecimal("85"));
		info.setChgPercForWarn(new BigDecimal("0"));
		info.setHasSettled(false);
		info.setConSplitExecState(ConSplitExecStateEnum.COMMON);
		info.setIsOpenContract(false);
		info.setIsStardContract(false);
		info.setSourceType(SourceTypeEnum.ADDNEW);
		info.setCostProperty(CostPropertyEnum.COMP_COMFIRM);
		
		JSONObject data = JSONObject.fromObject(str);
		Object projectId = data.get("projectId");
		Object conName = data.get("conName");
		Object num = data.get("num");
		Object conTypeId = data.get("conTypeId");
		Object hygh = data.get("hygh");
		Object partA = data.get("partA");
		Object partB = data.get("partB");
		Object partC = data.get("partC");
		Object taEntryId = data.get("taEntryId");
		Object marketProjectId = data.get("marketProjectId");
		Object costAccountId = data.get("costAccountId");
		Object currencyId = data.get("currencyId");
		Object originalAmount = data.get("originalAmount");
		Object amount = data.get("amount");
		Object exRate = data.get("exRate");
		Object bizDate = data.get("bizDate");
		Object grtAmount = data.get("grtAmount");
		Object grtRate = data.get("grtRate");
		Object contractSourceId = data.get("contractSourceId");
		Object startDate = data.get("startDate");
		Object endDate = data.get("endDate");
		Object personId = data.get("personId");
		Object deptId = data.get("deptId");
		Object contractWFTypeId = data.get("contractWFTypeId");
		Object contractWFStartType = data.get("contractWFStartType");
		Object description = data.get("description");
		Object creatDeptId = data.get("creatDeptId");
		Object unionBankId = data.get("unionBankId"); 
		Object bank = data.get("bank"); 
		Object bankAccount = data.get("bankAccount"); 
		Object taxerQua = data.get("taxerQua"); 
		Object taxerNum = data.get("taxerNum"); 
		Object contractNature = data.get("contractNature"); 
		Object isJT=data.get("isJT");
		Object isCoseSplit=data.get("isCoseSplit");
		
		if(isCoseSplit!=null){
			info.setIsCoseSplit(Boolean.valueOf(isCoseSplit.toString()));
		}
		if(projectId!=null){
			CurProjectInfo projectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId.toString()));
			info.setCurProject(projectInfo);
			info.setCU(projectInfo.getCU());
			
			info.setOrgUnit(projectInfo.getCostCenter().castToFullOrgUnitInfo());
		}
		if(conName!=null){
			info.setName(conName.toString());
		}
		if(num!=null){
			info.setNumber(num.toString());
		}
		if(conTypeId!=null){
			ContractTypeInfo cInfo = new ContractTypeInfo();
			cInfo.setId(BOSUuid.read(conTypeId.toString()));
			info.setContractType(cInfo);
		}
		if(hygh!=null){
			ProgrammingContractInfo pInfo = new ProgrammingContractInfo();
			pInfo.setId(BOSUuid.read(hygh.toString()));
			info.setProgrammingContract(pInfo);
		}
		if(partA!=null){
			LandDeveloperInfo dInfo = new LandDeveloperInfo();
			dInfo.setId(BOSUuid.read(partA.toString()));
			info.setLandDeveloper(dInfo);
		}
		if(partB!=null){
			SupplierInfo sInfo = new SupplierInfo();
			sInfo.setId(BOSUuid.read(partB.toString()));
			info.setPartB(sInfo);
		}
		if(partC!=null){
			SupplierInfo sInfo = new SupplierInfo();
			sInfo.setId(BOSUuid.read(partC.toString()));
			info.setPartC(sInfo);
		}
		if(taEntryId!=null){
			TenderAccepterResultEntryInfo eInfo = new TenderAccepterResultEntryInfo();
			eInfo.setId(BOSUuid.read(taEntryId.toString()));
			info.setTaEntry(eInfo);
		}
		if(marketProjectId!=null){
			MarketProjectInfo mInfo = new MarketProjectInfo();
			mInfo.setId(BOSUuid.read(marketProjectId.toString()));
			info.setMarketProject(mInfo);
		}
		if(costAccountId!=null){
			CostAccountInfo cinfo=new CostAccountInfo();
			cinfo.setId(BOSUuid.read(costAccountId.toString()));
			info.setMpCostAccount(cinfo);
		}
		if(currencyId!=null){
			CurrencyInfo cInfo = new CurrencyInfo();
			cInfo.setId(BOSUuid.read(currencyId.toString()));
			info.setCurrency(cInfo);
		}
		if(originalAmount!=null){
			BigDecimal orgAmount = new BigDecimal(originalAmount.toString());
			info.setOriginalAmount(orgAmount);
		}
		if(amount!=null){
			BigDecimal amount1 = new BigDecimal(amount.toString());
			info.setAmount(amount1);
		}
		if(exRate!=null){
			BigDecimal rate = new BigDecimal(exRate.toString());
			info.setExRate(rate);
		}
		if(bizDate!=null){
			Date date = FDCDateHelper.stringToDate(bizDate.toString());
			info.setBookedDate(date);
		}
		if(grtAmount!=null){
			BigDecimal grt = new BigDecimal(grtAmount.toString());
			info.setGrtAmount(grt);
		}
		if(grtRate!=null){
			BigDecimal grt = new BigDecimal(grtRate.toString());
			info.setGrtRate(grt);
		}
		if(contractSourceId!=null){
			ContractSourceInfo cs=new ContractSourceInfo();
			cs.setId(BOSUuid.read(contractSourceId.toString()));
			info.setContractSourceId(cs);
		}
		if(startDate!=null){
			Date date = FDCDateHelper.stringToDate(startDate.toString());
			info.setStartDate(date);
		}
		if(endDate!=null){
			Date date = FDCDateHelper.stringToDate(endDate.toString());
			info.setEndDate(date);
		}
		if(personId!=null){
			PersonInfo pInfo = new PersonInfo();
			pInfo.setId(BOSUuid.read(personId.toString()));
			info.setRespPerson(pInfo);
		}
		if(deptId!=null){
			AdminOrgUnitInfo dInfo = new AdminOrgUnitInfo();
			dInfo.setId(BOSUuid.read(deptId.toString()));
			info.setRespDept(dInfo);
		}
		if(contractWFTypeId!=null){
			ContractWFTypeInfo cInfo = new ContractWFTypeInfo();
			cInfo.setId(BOSUuid.read(contractWFTypeId.toString()));
			info.setContractWFType(cInfo);
		}
		if(contractWFStartType!=null){
			if(contractWFStartType.toString().equals("集团/事业部/城市公司")){
				info.setOrgType(ContractTypeOrgTypeEnum.BIGRANGE);
			}
			if(contractWFStartType.toString().equals("项目部")){
				info.setOrgType(ContractTypeOrgTypeEnum.SMALLRANGE);
			}
			if(contractWFStartType.toString().equals("集团/事业部/城市公司-项目部")){
				info.setOrgType(ContractTypeOrgTypeEnum.ALLRANGE);
			}
			if(contractWFStartType.toString().equals("内部关联公司往来类")){
				info.setOrgType(ContractTypeOrgTypeEnum.NEIBU);
			}
			if(contractWFStartType.toString().equals("外部供应商客户往来类")){
				info.setOrgType(ContractTypeOrgTypeEnum.WAIBU);
			}
		}
		if(description!=null){
			info.setDescription(description.toString());
		}
		if(creatDeptId!=null){
			AdminOrgUnitInfo aInfo = new AdminOrgUnitInfo();
			aInfo.setId(BOSUuid.read(creatDeptId.toString()));
			info.setCreateDept(aInfo);
		}
		if(unionBankId!=null){
			BankNumInfo bnInfo=new BankNumInfo();
			bnInfo.setId(BOSUuid.read(unionBankId.toString()));
			info.setLxNum(bnInfo);
		}
		if(bank!=null){
			info.setBank(bank.toString());
		}
		if(bankAccount!=null){
			info.setBankAccount(bankAccount.toString());
		}
		if(taxerQua!=null){
			if(taxerQua.toString().equals("一般纳税人")){
				info.setTaxerQua(TaxerQuaEnum.NOMAL);
			}
			if(taxerQua.toString().equals("小规模纳税人")){
				info.setTaxerQua(TaxerQuaEnum.SMALL);
			}
			if(taxerQua.toString().equals("非增值税纳税人")){
				info.setTaxerQua(TaxerQuaEnum.UNNOMAL);
			}
		}
		if(taxerNum!=null){
			info.setTaxerNum(taxerNum.toString());
		}
		if(isJT!=null){
			info.setIsJT(Boolean.valueOf(isJT.toString()));
		}
		if(contractNature!=null){
			if(contractNature.toString().equals("直接合同")){
				info.setContractPropert(ContractPropertyEnum.DIRECT);
			}
			if(contractNature.toString().equals("三方合同")){
				info.setContractPropert(ContractPropertyEnum.THREE_PARTY);
			}
			if(contractNature.toString().equals("补充合同")){
				info.setContractPropert(ContractPropertyEnum.SUPPLY);
			}
			if(contractNature.toString().equals("战略协议")){
				info.setContractPropert(ContractPropertyEnum.STRATEGY);
			}
		}
		info.getRateEntry().clear();
		if(data.get("signDetailArray")!=null){
			JSONArray signDetailArray=data.getJSONArray("signDetailArray");
			for(int i=0;i<signDetailArray.size();i++){
				ContractBillRateEntryInfo entry=new ContractBillRateEntryInfo();
				if(signDetailArray.getJSONObject(i).get("detail")!=null){
					entry.setDetail(signDetailArray.getJSONObject(i).getString("detail"));
				}
				if(signDetailArray.getJSONObject(i).get("totalAmount")!=null){
					entry.setTotalAmount(new BigDecimal(signDetailArray.getJSONObject(i).getString("totalAmount")));
				}
				if(signDetailArray.getJSONObject(i).get("amount")!=null){
					entry.setAmount(new BigDecimal(signDetailArray.getJSONObject(i).getString("amount")));
				}
				if(signDetailArray.getJSONObject(i).get("rate")!=null){
					entry.setRate(new BigDecimal(signDetailArray.getJSONObject(i).getString("rate")));
				}
				if(signDetailArray.getJSONObject(i).get("description")!=null){
					entry.setRemark(signDetailArray.getJSONObject(i).getString("description"));
				}
				info.getRateEntry().add(entry);
			}
		}
		info.getMarketEntry().clear();
		if(data.get("marketConArray")!=null){
			JSONArray marketConArray=data.getJSONArray("marketConArray");
			for(int i=0;i<marketConArray.size();i++){
				ContractMarketEntryInfo entry=new ContractMarketEntryInfo();
				if(marketConArray.getJSONObject(i).get("date")!=null){
					entry.setDate(FDCDateHelper.stringToDate(marketConArray.getJSONObject(i).getString("date")));
				}
				if(marketConArray.getJSONObject(i).get("amount")!=null){
					entry.setAmount(new BigDecimal(marketConArray.getJSONObject(i).getString("amount")));
				}
				if(marketConArray.getJSONObject(i).get("rate")!=null){
					entry.setRate(new BigDecimal(marketConArray.getJSONObject(i).getString("rate")));
				}
				if(marketConArray.getJSONObject(i).get("remark")!=null){
					entry.setRemark(marketConArray.getJSONObject(i).getString("remark"));
				}
				if(marketConArray.getJSONObject(i).get("content")!=null){
					entry.setContent(marketConArray.getJSONObject(i).getString("content"));
				}
				info.getMarketEntry().add(entry);
			}
		}
		info.getEntrys().clear();
		if(data.get("conDetailArray")!=null){
			JSONArray conDetailArray=data.getJSONArray("conDetailArray");
			for(int i=0;i<conDetailArray.size();i++){
				ContractBillEntryInfo entry=new ContractBillEntryInfo();
				if(conDetailArray.getJSONObject(i).get("detailInfo")!=null){
					entry.setDetail(conDetailArray.getJSONObject(i).getString("detailInfo"));
					
					if(conDetailArray.getJSONObject(i).getString("detailInfo").equals("是否使用电子章")){
						entry.setDataType(DataTypeEnum.BOOL);
						entry.setDetailDefID(ContractDetailDefFactory.getLocalInstance(ctx).getContractDetailDefCollection("select * from where name='是否使用电子章' and contractType.id='"+info.getContractType().getId()+"'").get(0).getId().toString());
					}else{
						entry.setDataType(DataTypeEnum.STRING);
					}
				}
				if(conDetailArray.getJSONObject(i).get("content")!=null){
					entry.setContent(conDetailArray.getJSONObject(i).getString("content"));
				}
				if(conDetailArray.getJSONObject(i).get("description")!=null){
					entry.setDesc(conDetailArray.getJSONObject(i).getString("description"));
				}
				info.getEntrys().add(entry);
			}
		}
		return info;
	}
	protected String _saveContractBill(Context ctx, String str)throws BOSException {
		JSONObject data = JSONObject.fromObject(str);
		ContractBillInfo info=new ContractBillInfo();
		if(data.get("id")!=null){
			String id = data.getString("id");
			info.setId(BOSUuid.read(id));
		}
		JSONObject obj = new JSONObject();
		try{
			this.setContractBill(ctx,str, info);
			
			String billId = ContractBillFactory.getLocalInstance(ctx).save(info).toString();
			setAttachment(ctx,data,billId);
			obj.put("result", "success");
			obj.put("id", billId);
		}catch(Exception e){
			e.printStackTrace();
			obj.put("message", e.getMessage());
			obj.put("result", "fault");
		}
		return obj.toString();
	}
	protected String _submitContractBill(Context ctx, String str)throws BOSException {
		JSONObject data = JSONObject.fromObject(str);
		ContractBillInfo info=new ContractBillInfo();
		if(data.get("id")!=null){
			String id = data.getString("id");
			info.setId(BOSUuid.read(id));
		}
		JSONObject obj = new JSONObject();
		String billId=null;
		try{
			this.setContractBill(ctx,str, info);
			info.put("fromweb", 1);
			
			if(info.getProgrammingContract()!=null){
				this.updateProgrammingContract(ctx,true,info.getProgrammingContract().getId().toString(), 0, billId);
			}
			billId = ContractBillFactory.getLocalInstance(ctx).submit(info).toString();
			
			if(info.getProgrammingContract()!=null){
				this.updateProgrammingContract(ctx,true,info.getProgrammingContract().getId().toString(), 1, billId);
			}
			
			setAttachment(ctx,data,billId);
			obj.put("result", "success");
			obj.put("id", billId);
		}catch(Exception e){
			e.printStackTrace();
			obj.put("message", e.getMessage());
			obj.put("result", "fault");
		}
		return obj.toString();
	}
	protected String _auditContractBill(Context ctx, String str)throws BOSException {
		JSONObject data = JSONObject.fromObject(str);
		JSONObject obj = new JSONObject();
		try{
			ContractBillFactory.getLocalInstance(ctx).audit(BOSUuid.read(data.getString("id")));
			obj.put("result", "success");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("message", e.getMessage());
			obj.put("result", "fault");
		}
		return obj.toString();
	}
	protected String _deleteContractBill(Context ctx, String str)
			throws BOSException {
		JSONObject data=JSONObject.fromObject(str);
		JSONObject obj=new JSONObject();
		JSONArray idArray=data.getJSONArray("idArray");
		for(int i=0;i<idArray.size();i++){
			try{
				ContractBillFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(idArray.getJSONObject(i).get("id").toString()));
			}catch(Exception e){
				e.printStackTrace();
				obj.put("message", e.getMessage());
				obj.put("result", "fault");
			}
		}
		if(obj.get("message")==null){
			obj.put("result", "success");
		}
		return obj.toString();
	}
	protected String _deleteContractwithouttext(Context ctx, String str)
			throws BOSException {
		JSONObject data=JSONObject.fromObject(str);
		JSONObject obj=new JSONObject();
		JSONArray idArray=data.getJSONArray("idArray");
		for(int i=0;i<idArray.size();i++){
			try{
				ContractWithoutTextFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(idArray.getJSONObject(i).get("id").toString()));
			}catch(Exception e){
				e.printStackTrace();
				obj.put("message", e.getMessage());
				obj.put("result", "fault");
			}
		}
		if(obj.get("message")==null){
			obj.put("result", "success");
		}
		return obj.toString();
	}
	protected String _deleteSupplierApply(Context ctx, String str)
			throws BOSException {
		JSONObject data=JSONObject.fromObject(str);
		JSONObject obj=new JSONObject();
		JSONArray idArray=data.getJSONArray("idArray");
		for(int i=0;i<idArray.size();i++){
			try{
				SupplierApplyFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(idArray.getJSONObject(i).get("id").toString()));
			}catch(Exception e){
				e.printStackTrace();
				obj.put("message", e.getMessage());
				obj.put("result", "fault");
			}
		}
		if(obj.get("message")==null){
			obj.put("result", "success");
		}
		return obj.toString();
	}
	protected String _auditContractwithouttext(Context ctx, String str)
			throws BOSException {
		JSONObject data = JSONObject.fromObject(str);
		JSONObject obj = new JSONObject();
		try{
			ContractWithoutTextFactory.getLocalInstance(ctx).audit(BOSUuid.read(data.getString("id")));
			obj.put("result", "success");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("message", e.getMessage());
			obj.put("result", "fault");
		}
		return obj.toString();
	}
	protected String _saveContractwithouttext(Context ctx, String str)throws BOSException {
		JSONObject data = JSONObject.fromObject(str);
		ContractWithoutTextInfo info=new ContractWithoutTextInfo();
		if(data.get("id")!=null){
			String id = data.getString("id");
			info.setId(BOSUuid.read(id));
		}
		JSONObject obj = new JSONObject();
		try{
			this.setConractWithoutText(ctx,str, info);
			
			String billId = ContractWithoutTextFactory.getLocalInstance(ctx).save(info).toString();
			setAttachment(ctx,data,billId);
			obj.put("result", "success");
			obj.put("id", billId);
		}catch(Exception e){
			e.printStackTrace();
			obj.put("message", e.getMessage());
			obj.put("result", "fault");
		}
		return obj.toString();
	}
	protected String _submitContractwithouttext(Context ctx, String str)throws BOSException {
		JSONObject data = JSONObject.fromObject(str);
		ContractWithoutTextInfo info=new ContractWithoutTextInfo();
		if(data.get("id")!=null){
			String id = data.getString("id");
			info.setId(BOSUuid.read(id));
		}
		JSONObject obj = new JSONObject();
		String billId=null;
		try{
			this.setConractWithoutText(ctx,str, info);
			info.put("fromweb", 1);
			
			if(info.getProgrammingContract()!=null){
				this.updateProgrammingContract(ctx,true,info.getProgrammingContract().getId().toString(), 0, billId);
			}
			billId = ContractWithoutTextFactory.getLocalInstance(ctx).submit(info).toString();
			
			if(info.getProgrammingContract()!=null){
				this.updateProgrammingContract(ctx,true,info.getProgrammingContract().getId().toString(), 1, billId);
			}
			
			setAttachment(ctx,data,billId);
			obj.put("result", "success");
			obj.put("id", billId);
		}catch(Exception e){
			e.printStackTrace();
			obj.put("message", e.getMessage());
			obj.put("result", "fault");
		}
		return obj.toString();
	}
	protected String _auditChangeAuditBill(Context ctx, String str)throws BOSException {
		JSONObject data = JSONObject.fromObject(str);
		JSONObject obj = new JSONObject();
		try{
			ChangeAuditBillFactory.getLocalInstance(ctx).audit(BOSUuid.read(data.getString("id")));
			obj.put("result", "success");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("message", e.getMessage());
			obj.put("result", "fault");
		}
		return obj.toString();
	}
	protected String _auditContractChangeSettleBill(Context ctx, String str)throws BOSException {
		JSONObject data = JSONObject.fromObject(str);
		JSONObject obj = new JSONObject();
		try{
			ContractChangeSettleBillFactory.getLocalInstance(ctx).audit(BOSUuid.read(data.getString("id")));
			obj.put("result", "success");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("message", e.getMessage());
			obj.put("result", "fault");
		}
		return obj.toString();
	}
	protected String _auditPayRequestBill(Context ctx, String str)throws BOSException {
		JSONObject data = JSONObject.fromObject(str);
		JSONObject obj = new JSONObject();
		try{
			PayRequestBillFactory.getLocalInstance(ctx).audit(BOSUuid.read(data.getString("id")));
			obj.put("result", "success");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("message", e.getMessage());
			obj.put("result", "fault");
		}
		return obj.toString();
	}
	protected String _auditMarketProject(Context ctx, String str)
			throws BOSException {
		JSONObject data = JSONObject.fromObject(str);
		JSONObject obj = new JSONObject();
		try{
			MarketProjectFactory.getLocalInstance(ctx).audit(BOSUuid.read(data.getString("id")));
			obj.put("result", "success");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("message", e.getMessage());
			obj.put("result", "fault");
		}
		return obj.toString();
	}
	
	public void updateProgrammingContract(Context ctx,boolean isContract,String pcId,int isCiting,String billId) {
		try {
			String oldPCId=null;
			FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
			IRowSet iRowSet=null;
			if(billId!=null){
				if(isContract){
					buildSQL.appendSql("select fprogrammingContract from t_con_contractBill where fid='" + billId + "'");
				}else{
					buildSQL.appendSql("select fprogrammingContract from T_CON_ContractWithoutText where fid='" + billId + "'");
				}
				iRowSet = buildSQL.executeQuery();
				while (iRowSet.next()) {
					oldPCId=iRowSet.getString("fprogrammingContract");
				}
			}
			if(isCiting==0){
				if(oldPCId==null||(pcId!=null&&oldPCId.equals(pcId))){
					return;
				}
				buildSQL = new FDCSQLBuilder();
				if(isContract){
					buildSQL.appendSql("select count(*) count from t_con_contractBill where fprogrammingContract='" + oldPCId + "' and fid!='" + billId + "'");
				}else{
					buildSQL.appendSql("select count(*) count from T_CON_ContractWithoutText where fprogrammingContract='" + oldPCId + "' and fid!='" + billId + "'");
				}
				int count = 0;
				iRowSet = buildSQL.executeQuery();
				if (iRowSet.next()) {
					count = iRowSet.getInt("count");
				}
				if(count>0){
					return;
				}
				buildSQL = new FDCSQLBuilder();
				buildSQL.appendSql("update T_CON_ProgrammingContract set FIsWTCiting = " + isCiting + " where FID = '" + oldPCId + "'");
				buildSQL.executeUpdate();
			}else if(pcId!=null){
				buildSQL = new FDCSQLBuilder();
				buildSQL.appendSql("update T_CON_ProgrammingContract set FIsWTCiting = " + isCiting + " where FID = '" + pcId + "'");
				buildSQL.executeUpdate();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	protected String _unAuditBill(Context ctx, String str) throws BOSException {
		JSONObject data = JSONObject.fromObject(str);
		JSONObject obj = new JSONObject();
		String type=data.getString("type");
		String id=data.getString("id");
		try{
			//type 01:合同、02:合同付款申请单、03:无合同付款;04：供应商申请，05变更审批单，06变更确认单,07营销立项
	        if(type.equals("01")){
	        	ContractBillInfo info=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(id));
	        	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
	        		ContractBillFactory.getLocalInstance(ctx).unAudit(BOSUuid.read(id));
	        	}else{
	        		info.setState(FDCBillStateEnum.SUBMITTED);
	        		info.setSourceFunction(null);
	        		
	        		SelectorItemCollection sic=new SelectorItemCollection();
	        		sic.add("state");
	        		sic.add("sourceFunction");
	        		ContractBillFactory.getLocalInstance(ctx).updatePartial(info, sic);
	        	}
	        }else if(type.equals("02")){
	        	PayRequestBillInfo info=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(id));
	        	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
	        		PayRequestBillFactory.getLocalInstance(ctx).unAudit(BOSUuid.read(id));
	        	}else{
	        		info.setState(FDCBillStateEnum.SUBMITTED);
	        		info.setSourceFunction(null);
	        		
	        		SelectorItemCollection sic=new SelectorItemCollection();
	        		sic.add("state");
	        		sic.add("sourceFunction");
	        		PayRequestBillFactory.getLocalInstance(ctx).updatePartial(info, sic);
	        	}
	        }else if(type.equals("03")){
	        	ContractWithoutTextInfo info=ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(id));
	        	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
	        		ContractWithoutTextFactory.getLocalInstance(ctx).unAudit(BOSUuid.read(id));
	        	}else{
	        		info.setState(FDCBillStateEnum.SUBMITTED);
	        		info.setSourceFunction(null);
	        		
	        		SelectorItemCollection sic=new SelectorItemCollection();
	        		sic.add("state");
	        		sic.add("sourceFunction");
	        		ContractWithoutTextFactory.getLocalInstance(ctx).updatePartial(info, sic);
	        	}
	        }else if(type.equals("04")){
	        	SupplierApplyInfo info=SupplierApplyFactory.getLocalInstance(ctx).getSupplierApplyInfo(new ObjectUuidPK(id));
	        	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
	        		SupplierApplyFactory.getLocalInstance(ctx).unAudit(BOSUuid.read(id));
	        	}else{
	        		info.setState(FDCBillStateEnum.SUBMITTED);
	        		info.setSourceFunction(null);
	        		
	        		SelectorItemCollection sic=new SelectorItemCollection();
	        		sic.add("state");
	        		sic.add("sourceFunction");
	        		SupplierApplyFactory.getLocalInstance(ctx).updatePartial(info, sic);
	        	}
	        }else if(type.equals("05")){
	        	ChangeAuditBillInfo info=ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(id));
	        	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
	        		ChangeAuditBillFactory.getLocalInstance(ctx).unAudit(BOSUuid.read(id));
	        	}else{
	        		info.setChangeState(ChangeBillStateEnum.Submit);
	        		info.setState(FDCBillStateEnum.SUBMITTED);
	        		info.setSourceFunction(null);
	        		
	        		SelectorItemCollection sic=new SelectorItemCollection();
	        		sic.add("changeState");
	        		sic.add("state");
	        		sic.add("sourceFunction");
	        		ChangeAuditBillFactory.getLocalInstance(ctx).updatePartial(info, sic);
	        	}
	        }else if(type.equals("06")){
	        	ContractChangeSettleBillInfo info=ContractChangeSettleBillFactory.getLocalInstance(ctx).getContractChangeSettleBillInfo(new ObjectUuidPK(id));
	        	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
	        		ContractChangeSettleBillFactory.getLocalInstance(ctx).unAudit(BOSUuid.read(id));
	        	}else{
	        		info.setState(FDCBillStateEnum.SUBMITTED);
	        		info.setSourceFunction(null);
	        		
	        		SelectorItemCollection sic=new SelectorItemCollection();
	        		sic.add("state");
	        		sic.add("sourceFunction");
	        		ContractChangeSettleBillFactory.getLocalInstance(ctx).updatePartial(info, sic);
	        	}
	        }else if(type.equals("07")){
	        	MarketProjectInfo info=MarketProjectFactory.getLocalInstance(ctx).getMarketProjectInfo(new ObjectUuidPK(id));
	        	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
	        		MarketProjectFactory.getLocalInstance(ctx).unAudit(BOSUuid.read(id));
	        	}else{
	        		info.setState(FDCBillStateEnum.SUBMITTED);
	        		info.setSourceFunction(null);
	        		
	        		SelectorItemCollection sic=new SelectorItemCollection();
	        		sic.add("state");
	        		sic.add("sourceFunction");
	        		MarketProjectFactory.getLocalInstance(ctx).updatePartial(info, sic);
	        	}
	        }
			obj.put("result", "success");
		}catch(Exception e){
			e.printStackTrace();
			obj.put("message", e.getMessage());
			obj.put("result", "fault");
		}
		return obj.toString();
	}
	protected String _acceptHandle(final Context ctx, String str) throws BOSException {
		JSONObject data = JSONObject.fromObject(str);
		JSONObject obj = new JSONObject();
		
//    	01:审批通过,02:废弃,03:驳回,修订
		String result = data.getString("result");
//    	type 01:合同、02:合同付款申请单、03:无合同付款;04：供应商申请，05变更审批单，06变更确认单,07营销立项
		String type = data.getString("type");
		Object oaState=data.get("oaState");
		final String easid = data.getString("easid");
		try {
			if(result.equals("01")){
				if(type.equals("01")){
					ContractBillFactory.getLocalInstance(ctx).audit(BOSUuid.read(easid));
				}else if(type.equals("02")){
					PayRequestBillFactory.getLocalInstance(ctx).audit(BOSUuid.read(easid));
				}else if(type.equals("03")){
					ContractWithoutTextFactory.getLocalInstance(ctx).audit(BOSUuid.read(easid));
				}else if(type.equals("04")){
					new Thread(new Runnable() {
				           public void run() {
				        	   try {
								SupplierApplyFactory.getLocalInstance(ctx).audit(BOSUuid.read(easid));
							} catch (Exception e) {
								e.printStackTrace();
							}
				    }
				}).start();
				}else if(type.equals("05")){
					ChangeAuditBillFactory.getLocalInstance(ctx).audit(BOSUuid.read(easid));
				}else if(type.equals("06")){
					ContractChangeSettleBillFactory.getLocalInstance(ctx).audit(BOSUuid.read(easid));
				}else if(type.equals("07")){
					MarketProjectFactory.getLocalInstance(ctx).audit(BOSUuid.read(easid));
				}
			}else if(result.equals("02")){
				if(type.equals("01")){
		        	ContractBillInfo info=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(easid));
		        	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
		        		ContractBillFactory.getLocalInstance(ctx).unAudit(BOSUuid.read(easid));
		        		String sql = "update t_con_contractbill set fsourceFunction=null,foaState=null where fid=?";
		        		DbUtil.execute(ctx,sql,new Object[]{easid});
		        	}else{
		        		info.setState(FDCBillStateEnum.SUBMITTED);
		        		info.setSourceFunction(null);
	        			info.setOaState(null);
		        		
		        		SelectorItemCollection sic=new SelectorItemCollection();
		        		sic.add("state");
		        		sic.add("sourceFunction");
		        		sic.add("oaState");
		        		ContractBillFactory.getLocalInstance(ctx).updatePartial(info, sic);
		        	}
		        }else if(type.equals("02")){
		        	PayRequestBillInfo info=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(easid));
		        	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
		        		PayRequestBillFactory.getLocalInstance(ctx).unAudit(BOSUuid.read(easid));
		        		String sql = "update T_CON_PayRequestBill set fsourceFunction=null where fid=?";
		        		DbUtil.execute(ctx,sql,new Object[]{easid});
		        	}else{
		        		info.setState(FDCBillStateEnum.SUBMITTED);
		        		info.setSourceFunction(null);
		        		
		        		SelectorItemCollection sic=new SelectorItemCollection();
		        		sic.add("state");
		        		sic.add("sourceFunction");
		        		PayRequestBillFactory.getLocalInstance(ctx).updatePartial(info, sic);
		        	}
		        }else if(type.equals("03")){
		        	ContractWithoutTextInfo info=ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(easid));
		        	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
		        		ContractWithoutTextFactory.getLocalInstance(ctx).unAudit(BOSUuid.read(easid));
		        		String sql = "update T_CON_ContractWithoutText set fsourceFunction=null where fid=?";
		        		DbUtil.execute(ctx,sql,new Object[]{easid});
		        	}else{
		        		info.setState(FDCBillStateEnum.SUBMITTED);
		        		info.setSourceFunction(null);
		        		
		        		SelectorItemCollection sic=new SelectorItemCollection();
		        		sic.add("state");
		        		sic.add("sourceFunction");
		        		ContractWithoutTextFactory.getLocalInstance(ctx).updatePartial(info, sic);
		        	}
		        }else if(type.equals("04")){
		        	SupplierApplyInfo info=SupplierApplyFactory.getLocalInstance(ctx).getSupplierApplyInfo(new ObjectUuidPK(easid));
		        	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
		        		SupplierApplyFactory.getLocalInstance(ctx).unAudit(BOSUuid.read(easid));
		        		String sql = "update T_CON_SupplierApply set fsourceFunction=null where fid=?";
		        		DbUtil.execute(ctx,sql,new Object[]{easid});
		        	}else{
		        		info.setState(FDCBillStateEnum.SUBMITTED);
		        		info.setSourceFunction(null);
		        		
		        		SelectorItemCollection sic=new SelectorItemCollection();
		        		sic.add("state");
		        		sic.add("sourceFunction");
		        		SupplierApplyFactory.getLocalInstance(ctx).updatePartial(info, sic);
		        	}
		        }else if(type.equals("05")){
		        	ChangeAuditBillInfo info=ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(easid));
		        	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
		        		ChangeAuditBillFactory.getLocalInstance(ctx).unAudit(BOSUuid.read(easid));
		        		String sql = "update T_CON_ChangeAuditBill set fsourceFunction=null where fid=?";
		        		DbUtil.execute(ctx,sql,new Object[]{easid});
		        	}else{
		        		info.setChangeState(ChangeBillStateEnum.Submit);
		        		info.setState(FDCBillStateEnum.SUBMITTED);
		        		info.setSourceFunction(null);
		        		
		        		SelectorItemCollection sic=new SelectorItemCollection();
		        		sic.add("changeState");
		        		sic.add("state");
		        		sic.add("sourceFunction");
		        		ChangeAuditBillFactory.getLocalInstance(ctx).updatePartial(info, sic);
		        	}
		        }else if(type.equals("06")){
		        	ContractChangeSettleBillInfo info=ContractChangeSettleBillFactory.getLocalInstance(ctx).getContractChangeSettleBillInfo(new ObjectUuidPK(easid));
		        	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
		        		ContractChangeSettleBillFactory.getLocalInstance(ctx).unAudit(BOSUuid.read(easid));
		        		String sql = "update T_CON_ContractChangeSettleBill set fsourceFunction=null where fid=?";
		        		DbUtil.execute(ctx,sql,new Object[]{easid});
		        	}else{
		        		info.setState(FDCBillStateEnum.SUBMITTED);
		        		info.setSourceFunction(null);
		        		
		        		SelectorItemCollection sic=new SelectorItemCollection();
		        		sic.add("state");
		        		sic.add("sourceFunction");
		        		ContractChangeSettleBillFactory.getLocalInstance(ctx).updatePartial(info, sic);
		        	}
		        }else if(type.equals("07")){
		        	MarketProjectInfo info=MarketProjectFactory.getLocalInstance(ctx).getMarketProjectInfo(new ObjectUuidPK(easid));
		        	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
		        		MarketProjectFactory.getLocalInstance(ctx).unAudit(BOSUuid.read(easid));
		        		String sql = "update T_CON_MarketProject set fsourceFunction=null where fid=?";
		        		DbUtil.execute(ctx,sql,new Object[]{easid});
		        	}else{
		        		info.setState(FDCBillStateEnum.SUBMITTED);
		        		info.setSourceFunction(null);
		        		
		        		SelectorItemCollection sic=new SelectorItemCollection();
		        		sic.add("state");
		        		sic.add("sourceFunction");
		        		MarketProjectFactory.getLocalInstance(ctx).updatePartial(info, sic);
		        	}
		        }
			}else if(result.equals("03")){
				if(type.equals("01")){
		        	ContractBillInfo info=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(easid));
	        		info.setState(FDCBillStateEnum.SUBMITTED);
	        		if(oaState!=null){
	        			info.setOaState(oaState.toString());
	        		}
	        		SelectorItemCollection sic=new SelectorItemCollection();
	        		sic.add("state");
	        		sic.add("oaState");
	        		ContractBillFactory.getLocalInstance(ctx).updatePartial(info, sic);
		        }else if(type.equals("02")){
		        	PayRequestBillInfo info=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(easid));
	        		info.setState(FDCBillStateEnum.SUBMITTED);
	        		SelectorItemCollection sic=new SelectorItemCollection();
	        		sic.add("state");
	        		PayRequestBillFactory.getLocalInstance(ctx).updatePartial(info, sic);
		        }else if(type.equals("03")){
		        	ContractWithoutTextInfo info=ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(easid));
	        		info.setState(FDCBillStateEnum.SUBMITTED);
	        		SelectorItemCollection sic=new SelectorItemCollection();
	        		sic.add("state");
	        		ContractWithoutTextFactory.getLocalInstance(ctx).updatePartial(info, sic);
		        }else if(type.equals("04")){
		        	SupplierApplyInfo info=SupplierApplyFactory.getLocalInstance(ctx).getSupplierApplyInfo(new ObjectUuidPK(easid));
	        		info.setState(FDCBillStateEnum.SUBMITTED);
	        		SelectorItemCollection sic=new SelectorItemCollection();
	        		sic.add("state");
	        		SupplierApplyFactory.getLocalInstance(ctx).updatePartial(info, sic);
		        }else if(type.equals("05")){
		        	ChangeAuditBillInfo info=ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(easid));
	        		info.setChangeState(ChangeBillStateEnum.Submit);
	        		info.setState(FDCBillStateEnum.SUBMITTED);
	        		SelectorItemCollection sic=new SelectorItemCollection();
	        		sic.add("changeState");
	        		sic.add("state");
	        		ChangeAuditBillFactory.getLocalInstance(ctx).updatePartial(info, sic);
		        }else if(type.equals("06")){
		        	ContractChangeSettleBillInfo info=ContractChangeSettleBillFactory.getLocalInstance(ctx).getContractChangeSettleBillInfo(new ObjectUuidPK(easid));
	        		info.setState(FDCBillStateEnum.SUBMITTED);
	        		SelectorItemCollection sic=new SelectorItemCollection();
	        		sic.add("state");
	        		ContractChangeSettleBillFactory.getLocalInstance(ctx).updatePartial(info, sic);
		        }else if(type.equals("07")){
		        	MarketProjectInfo info=MarketProjectFactory.getLocalInstance(ctx).getMarketProjectInfo(new ObjectUuidPK(easid));
	        		info.setState(FDCBillStateEnum.SUBMITTED);
	        		SelectorItemCollection sic=new SelectorItemCollection();
	        		sic.add("state");
	        		MarketProjectFactory.getLocalInstance(ctx).updatePartial(info, sic);
		        }
			}
		} catch (EASBizException e) {
			e.printStackTrace();
			obj.put("code", "2");
            obj.put("msg", "fault");
            obj.put("content", e.getMessage());
            
            String logStr="insert into t_log (name,context,number,createtime,state,msg) values('OA回调接口','"+data+"','"+easid+"',now(),'失败','"+e.getMessage()+"')";
    		DbUtil.execute(ctx,logStr);
            return obj.toString();
		} catch (UuidException e) {
			e.printStackTrace();
			obj.put("code", "2");
            obj.put("msg", "fault");
            obj.put("content", e.getMessage());
            
            String logStr="insert into t_log (name,context,number,createtime,state,msg) values('OA回调接口','"+data+"','"+easid+"',now(),'失败','"+e.getMessage()+"')";
            DbUtil.execute(ctx,logStr);
            return obj.toString();
		}
		
		String logStr="insert into t_log (name,context,number,createtime,state,msg) values('OA回调接口','"+data+"','"+easid+"',now(),'成功','')";
		DbUtil.execute(ctx,logStr);
		
		obj.put("code", "1");
        obj.put("msg", "success");
		return obj.toString();
	}
	
	
}