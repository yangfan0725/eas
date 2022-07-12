package com.kingdee.eas.fdc.tenancy.app;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.db.TempTablePool;
import com.kingdee.bos.db.TempTableUtil;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.IInvoiceBill;
import com.kingdee.eas.fdc.tenancy.InvoiceBatchImportEntryCollection;
import com.kingdee.eas.fdc.tenancy.InvoiceBatchImportEntryInfo;
import com.kingdee.eas.fdc.tenancy.InvoiceBatchImportFactory;
import com.kingdee.eas.fdc.tenancy.InvoiceBatchImportInfo;
import com.kingdee.eas.fdc.tenancy.InvoiceBillEntryInfo;
import com.kingdee.eas.fdc.tenancy.InvoiceBillFactory;
import com.kingdee.eas.fdc.tenancy.InvoiceBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class InvoiceBatchImportControllerBean extends AbstractInvoiceBatchImportControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.InvoiceBatchImportControllerBean");
    @Override
    protected Map _queryInvoiceData(Context ctx, Map paramMap)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	
    	Map resultMap = new HashMap();
    	Date startDate = (Date) paramMap.get("startDate");
    	Date endDate = (Date)paramMap.get("endDate");
    	FDCCustomerInfo  c = null;
    	String customerSql = null;
    	if(paramMap.get("customer")!=null){
    		Object[] o = (Object[]) paramMap.get("customer");
    		if(o[0] != null){
    			c = (FDCCustomerInfo)o[0];
    			customerSql= "and select ftenancybillid from T_TEN_TenancyCustomerEntry where ffdccustomerid = '"+c.getId()+"'";
    		}
    	}
    	Object[] m=(Object[]) paramMap.get("moneyDefine");
    	String moneyDefine="";
    	for(int i=0;i<m.length;i++){
    		if(i==m.length-1){
    			moneyDefine=moneyDefine+"'"+((MoneyDefineInfo)m[i]).getId().toString()+"'";
    		}else{
    			moneyDefine=moneyDefine+"'"+((MoneyDefineInfo)m[i]).getId().toString()+"',";
    		}
    	}
    	
    	SellProjectInfo sellProject = (SellProjectInfo) paramMap.get("sellProject");
    	String sql = " select * from (select pentry.fid            revId,                          "+
    	"        '"+RevListTypeEnum.tenRoomRev.getValue()+"' revType,               "+
    	"        pentry.fmoneydefineid moneyId,                                     "+
    	"        m.fname_l2 moneyName         ,m.FMoneyType  moneyType,                            "+
    	"        tb.fid    contractId,                                              "+
    	"        tb.fname              contractName,                                "+
    	"        tb.fnumber            contractNumber,                              "+
    	"        r.fname_l2            roomName,                                    "+
    	"        pentry.fappdate appDate,                                           "+
    	"        pentry.fappamount appAmt,                                          "+
    	"        pentry.finvoiceamount invoiceAmt,                                  "+
    	"        pentry.fstartDate startDate,                                       "+
    	"        pentry.fendDate    endDate,                                        "+
    	"        cust.fname_l2 custName,                                            "+
    	"        cust.fnumber custNumber,                                           "+
    	"        cust.fid custId,                                                   "+
    	"        cust.fInvoiceName invoiceName,                                     "+
    	"        cust.FTaxIdNumber invoiceIdentified,                               "+
    	"        cust.FInvoiceAddress||cust.FInvoicePhone addAndPhone,              "+
    	"        cust.FInvoiceBankAccount bankAndAccount,                           "+
    	"        cust.FINVTYPE invoiceType,                                         "+
    	"        m.FTaxCode taxCode,                                                "+
    	"        m.frate taxRate,null amount                                                    "+
    	"   from t_ten_tenancyroompaylistentry pentry                               "+
    	"  inner join t_ten_tenancyroomentry rentry                                 "+
    	"     on pentry.ftenroomid = rentry.fid                                     "+
    	"   left outer join t_she_room r                                            "+
    	"     on r.fid = rentry.froomid                                             "+
    	"   left outer join t_ten_tenancybill tb                                    "+
    	"     on tb.fid = rentry.ftenancyid                                         "+
    	"   left outer join t_she_moneydefine m                                     "+
    	"     on m.fid = pentry.fmoneydefineid                                      "+
    	"   inner join t_ten_tenancycustomerentry centry                            "+
    	"     on centry.ftenancybillid = tb.fid                                     "+
    	"   left outer join t_She_Fdccustomer cust                                  "+
    	"     on centry.ffdccustomerid = cust.fid                                   "+
    	"  where pentry.fappDate >= {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getDayBegin(startDate))+"'}   "+
    	"    and pentry.fappDate <= {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getDayEnd(endDate))+"'}   "+
    	"    and pentry.fmoneyDefineId in("+moneyDefine+")"+
    	"    and tb.fsellprojectid ='"+sellProject.getId().toString()+"'"+
    	"    and tb.ftenancystate ='Executing'                                      ";
    	if(c!=null){
    		sql = sql+" and centry.ffdccustomerid ='" +c.getId().toString()+"'  ";
    	}
    	
    	sql=sql+"    union all                                                              "+
    	"    select                                                                 "+
    	"        oentry.fid            revId,                                       "+
    	"        '"+RevListTypeEnum.tenOtherRev.getValue()+"' revType,              "+
    	"        oentry.fmoneydefineid moneyId,                                     "+
    	"        m.fname_l2 moneyName         ,m.FMoneyType  moneyType,                     "+
    	"        tb.fid contractId,                                                 "+
    	"        tb.fname              contractName,                                "+
    	"        tb.fnumber            contractNumber,                              "+
    	"        r.fname_l2            roomName,                                    "+
    	"        oentry.fappdate  appDate,                                          "+
    	"        oentry.fappamount  appAmt,                                         "+
    	"        oentry.finvoiceamount invoiceAmt,                                  "+
    	"        oentry.fstartDate startDate,                                       "+
    	"        oentry.fendDate    endDate,                                        "+
    	"        cust.fname_l2 custName,                                            "+
    	"        cust.fnumber custNumber,                                           "+
    	"        cust.fid custId,                                                   "+
    	"        cust.fInvoiceName invoiceName,                                     "+
    	"        cust.FTaxIdNumber invoiceIdentified,                               "+
    	"        cust.FInvoiceAddress||cust.FInvoicePhone addAndPhone,              "+
    	"        cust.FInvoiceBankAccount bankAndAccount,                           "+
    	"        cust.FINVTYPE invoiceType,                                         "+
    	"        m.FTaxCode taxCode,                                                "+
    	"        m.frate taxRate,oentry.famount amount                                                    "+
    	"    from t_ten_tenbillotherpay oentry                                      "+
    	"    inner join t_ten_tenancybill tb on tb.fid = oentry.fheadid             "+
    	"    inner join t_ten_tenancyroomentry rentry on tb.fid = rentry.ftenancyid "+
    	"    left outer join t_she_room r on r.fid = rentry.froomid                 "+
    	"    left outer join t_she_moneydefine m on m.fid = oentry.fmoneydefineid   "+ 
    	"    inner join t_ten_tenancycustomerentry centry                           "+ 
    	"     on centry.ftenancybillid = tb.fid                                     "+ 
    	"   left outer join t_She_Fdccustomer cust                                  "+ 
    	"     on centry.ffdccustomerid = cust.fid                                   "+ 
    	"    where oentry.fappDate >= {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getDayBegin(startDate))+"'} "+ 
    	"    and oentry.fappDate <= {ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getDayEnd(endDate))+"'}   "+
    	"    and oentry.fmoneyDefineId in("+moneyDefine+")"+
    	"    and tb.ftenancystate in ('Executing','QuitTenancying','Expiration','ContinueTenancying')            "+
    	"    and tb.fsellprojectid = '"+sellProject.getId().toString()+"'" ;
    	if(c != null){
    		sql = sql+" and centry.ffdccustomerid ='"+c.getId().toString()+"' ";
    	}
    	sql = sql+") order by roomName,moneyName ";
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql(sql);
    	
    	IRowSet rs = builder.executeQuery();
    	InvoiceBatchImportEntryCollection cols = new InvoiceBatchImportEntryCollection();
    	InvoiceBatchImportEntryInfo entry = null;
    	TenancyBillInfo tb = null;
    	FDCCustomerInfo customer = null;
    	try {
			while(rs.next()){
				entry = new InvoiceBatchImportEntryInfo();
				entry.setRevId(rs.getString("revId"));
				entry.setRevType(RevListTypeEnum.getEnum(rs.getString("revType")));
				tb = new TenancyBillInfo();
				tb.setId(BOSUuid.read(rs.getString("contractId")));
				tb.setName(rs.getString("contractName"));
				tb.setNumber(rs.getString("contractNumber"));
				entry.setTenancybill(tb);
				
				customer = new FDCCustomerInfo();
				customer.setId(BOSUuid.read(rs.getString("custId")));
				customer.setName(rs.getString("custName"));
				customer.setNumber(rs.getString("custNumber"));
				entry.setCustomer(customer);
				
				
				entry.setMoneyDefineName(rs.getString("moneyName"));
				
				entry.setRoomName(rs.getString("roomName"));
				entry.setAppDate(rs.getDate("appDate"));
				entry.setAppAmountWithoutTax(rs.getBigDecimal("appAmt"));
				entry.setAlreadyInvoiceAmt(rs.getBigDecimal("invoiceAmt"));
				entry.setStartDate(rs.getDate("startDate"));
				entry.setEndDate(rs.getDate("endDate"));
				entry.setTaxCode(rs.getString("taxCode"));
				entry.setTaxRate(rs.getBigDecimal("taxRate"));
				entry.setTaxIdentified(rs.getString("invoiceIdentified"));
				entry.setAddAndPhone(rs.getString("addAndPhone"));
				entry.setInvoiceName(rs.getString("invoiceName"));
				entry.setBankAndAccount(rs.getString("bankAndAccount"));
				if(rs.getString("moneyType").equals(MoneyTypeEnum.DEPOSITAMOUNT_VALUE)){
					entry.setInvoiceType(ChequeTypeEnum.receipt);
				}else{
					entry.setInvoiceType(rs.getString("invoiceType")!=null?ChequeTypeEnum.getEnum(rs.getString("invoiceType")):ChequeTypeEnum.normal);
				}
				entry.setAmount(rs.getBigDecimal("amount"));
				cols.add(entry);
			}
		} catch (SQLException e) {
			throw new ContractException(ContractException.WITHMSG,new String[]{"获取开票数据出错"});
		}
    	resultMap.put("result", cols);
    	return resultMap;
    }
    
    @Override
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super._audit(ctx, billId);
    	/**
    	 * 生成发票开票纪录
    	 * 获取分录信息，生成开票纪录
    	 * 
    	 * 
    	 * 
    	 */
    	
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	sic.add("moneyDefine.*");
    	sic.add("sellproject.*");
    	sic.add("entry.*");
    	sic.add("entry.tenancybill.*");
    	sic.add("entry.customer.*");
    	
    	
    	InvoiceBatchImportInfo info = InvoiceBatchImportFactory.getLocalInstance(ctx).getInvoiceBatchImportInfo(new ObjectUuidPK(billId), sic);
    	InvoiceBatchImportEntryCollection cols = info.getEntry();
    	int size = cols.size();
    	InvoiceBatchImportEntryInfo entry = null;
    	InvoiceBillInfo invoice = null;
    	IInvoiceBill iib = InvoiceBillFactory.getLocalInstance(ctx);
    	 ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
         boolean isExist = true;
         String orgId =ContextUtil.getCurrentOrgUnit(ctx).getId()+"";
    	for(int i = 0;i<size;i++){
    		entry = cols.get(i);
    		invoice = new InvoiceBillInfo();
    		//生成发票头
    		invoice.setTenancyBill(entry.getTenancybill());
    		invoice.setFdcCustomer(entry.getCustomer());
    		invoice.setAmount(entry.getInvoiceAmt());
    		invoice.setCreator(SysContext.getSysContext().getCurrentUserInfo());
    		invoice.setCreateTime(new Timestamp(System.currentTimeMillis()));
    		invoice.setType(entry.getInvoiceType());
    		//invoice.setName("批量开票-"+entry.getRoomName()+"_"+entry.getStartDate()+"_"+entry.getEndDate()+Math.random());
    		 if(!iCodingRuleManager.isExist(info, orgId)){
    			 invoice.setNumber(iCodingRuleManager.getNumber(invoice, orgId));
    		 }else{
    			throw new ContractException(ContractException.WITHMSG,new String[]{"请配置票据管理的编码规则"});
    		 }
    		InvoiceBillEntryInfo invoiceEntry = new InvoiceBillEntryInfo();
    		invoiceEntry.setAmount(entry.getInvoiceAmt());
    		invoiceEntry.setRevListId(entry.getRevId());
    		invoiceEntry.setRevListType(entry.getRevType());
    		invoiceEntry.setStartDate(entry.getStartDate());
    		invoiceEntry.setEndDate(entry.getEndDate());
    		invoiceEntry.setReMark(entry.getMoneyDefineName());
    		invoice.setSaleNumber(entry.getSaleNumber());
    		
    		invoice.getEntry().add(invoiceEntry);
    		invoice.setSourceBillId(entry.getId().toString());
    		
    		ObjectUuidPK pk = (ObjectUuidPK) iib.submit(invoice);
    		iib.audit(pk.getKeyValue());
    	}
    	
    	
    }
    
    @Override
    protected IObjectPK _save(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	return super._save(ctx, model);
    }
    
    @Override
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	IObjectPK pk = super._submit(ctx, model);
    	String parentId = pk.getKeyValue("id").toString();
    	String tableName = null;
		try {
			tableName = TempTablePool.getInstance(ctx).createTempTable("create table t_ten_salenumber(sn varchar(100),fid varchar(44))");
			
			FDCSQLBuilder builder  = new FDCSQLBuilder(ctx);
			String sql=	" /*dialect*/insert into "+tableName+" select  to_char(sysdate,'yyyyMMdd')||invoicebatchimportentry_seq.nextVal sn,e.FTenancybillID from(select e.FTenancybillID from t_ten_invoicebatchimportentry e where fparentid = ? group by e.FTenancybillID) e";
			builder.appendSql(sql);
			builder.addParam(parentId);
			builder.execute();
			
			
			builder.clear();
			builder.appendSql("update t_ten_invoicebatchimportentry set fsalenumber = (select sn from "+tableName+" where fid =t_ten_invoicebatchimportentry.FTenancybillID ) where fparentid = ? and fsalenumber is null");
			builder.addParam(parentId);
			builder.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally{
			if(!StringUtils.isEmpty(tableName)){
				TempTablePool.getInstance(ctx).releaseTable(tableName);
			}
			
		}
    	
    	
    	
    	
    	
    	
    	
    	return pk;
    }
    
}