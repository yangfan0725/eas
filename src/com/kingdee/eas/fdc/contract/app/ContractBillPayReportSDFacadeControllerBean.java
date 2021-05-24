package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class ContractBillPayReportSDFacadeControllerBean extends AbstractContractBillPayReportSDFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractBillPayReportSDFacadeControllerBean");
    
    protected RptParams _init(Context ctx, RptParams params)throws BOSException, EASBizException
	{
	    RptParams pp = new RptParams();
	    return pp;
	}
    private void initColoum(RptTableHeader header,RptTableColumn col,String name,int width,boolean isHide){
    	col= new RptTableColumn(name);
    	col.setWidth(width);
	    col.setHided(isHide);
	    header.addColumn(col);
    }
    protected RptParams _createTempTable(Context ctx, RptParams params)    throws BOSException, EASBizException
	{
	    RptTableHeader header = new RptTableHeader();
	    RptTableColumn col = null;
	    initColoum(header,col,"contractType",100,true);
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"number",120,false);
	    initColoum(header,col,"name",200,false);
	    initColoum(header,col,"supplier",200,false);
	    initColoum(header,col,"contractPropert",100,false);
	    initColoum(header,col,"pcAmount",100,false);
	    
	    initColoum(header,col,"srcAmount",100,false);
	    initColoum(header,col,"originalAmount",100,false);
	    initColoum(header,col,"amount",100,false);
	    
	    initColoum(header,col,"settleOriginalAmount",100,false);
	    initColoum(header,col,"settleAmount",100,false);
	    
	    initColoum(header,col,"costAmount",100,true);
	    initColoum(header,col,"totalWorkLoad",100,true);
	    initColoum(header,col,"appAmount",100,true);
	    initColoum(header,col,"invoiceAmt",100,false);
	    
	    initColoum(header,col,"deductAmt",100,false);
	    
	    initColoum(header,col,"reqAmount",100,false);
	    initColoum(header,col,"payAmount",100,false);
	    initColoum(header,col,"payRate",100,false);
	    
	    initColoum(header,col,"lstAppAmount",100,true);
	    initColoum(header,col,"lstPayAmount",100,false);
	    initColoum(header,col,"lstEndAppAmount",130,true);
	    initColoum(header,col,"lstCostAmount",100,true);
	    initColoum(header,col,"workLoad",130,true);
	    initColoum(header,col,"nowDeductAmount",100,false);
	    initColoum(header,col,"nowAppAmount",100,true);
	    initColoum(header,col,"nowReqAmount",100,false);
	    initColoum(header,col,"nowPayAmount",100,false);
	    
	    initColoum(header,col,"bzj",100,true);
	    header.setLabels(new Object[][]{
	    		{
	    			"��ͬ����","id","��ͬ����","��ͬ����","ǩԼ��λ","��ͬ����","�滮���",
	    			"ǩԼ��Ϣ","ǩԼ��Ϣ","ǩԼ��Ϣ",
	    			"������Ϣ","������Ϣ",
	    			"�ۼ���Ϣ","�ۼ���Ϣ","�ۼ���Ϣ","�ۼ���Ϣ","�ۼ���Ϣ","�ۼ���Ϣ","�ۼ���Ϣ","�ۼ���Ϣ",
	    			"���¸�����Ϣ","���¸�����Ϣ","���¸�����Ϣ","���¸�����Ϣ","���¸�����Ϣ","���¸�����Ϣ","���¸�����Ϣ","���¸�����Ϣ","���¸�����Ϣ","�Ѹ���֤����"
	    		}
	    		,
	    		{
	    			"��ͬ����","id","��ͬ����","��ͬ����","ǩԼ��λ","��ͬ����","�滮���",
	    			"ԭ����ͬ���","ԭ�ҽ��","��λ�ҽ��",
	    			"ԭ�ҽ��","��λ�ҽ��",
	    			"�ۼ��깤������","�ۼ��깤��������ȷ�ϵ�ģʽ��","�ۼ�Ӧ���˿�","�ۼƷ�Ʊ���","�ۼƿۿ���","�ۼƸ�������","�ۼ��Ѹ��˿�","�������",
	    			"����Ӧ���˿�","����ʵ����","����ĩ�ۼ�Ӧ��δ����","�����깤������","�����깤��������ȷ�ϵ�ģʽ��","���¿ۿ���","����Ӧ���˿�","���¸�������","�����Ѹ��˿�","�Ѹ���֤����"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		Map value=new HashMap();
		while(rowSet.next()){
			String contractTypeName=rowSet.getString("contractType");
			if(value.containsKey(contractTypeName)){
				((List)value.get(contractTypeName)).add(rowSet.toRowArray());
			}else{
				List list=new ArrayList();
				list.add(rowSet.toRowArray());
				value.put(contractTypeName, list);
			}
		}
		Object[] key = value.keySet().toArray(); 
		Arrays.sort(key); 
		params.setObject("value", value);
		params.setObject("key", key);
		return params;
    }
    protected String getSql(Context ctx,RptParams params){
    	String curProject=(String) params.getObject("curProject");
    	String contractType=(String) params.getObject("contractType");
    	String[] contractTypeStr=null;
    	HashMap hmParamIn = new HashMap();
    	hmParamIn.put("FDC_ISREPORTSHOWCONTRACT", null);
		try {
			HashMap hmAllParam = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(hmParamIn);
			if(hmAllParam.get("FDC_ISREPORTSHOWCONTRACT")!=null&&!"".equals(hmAllParam.get("FDC_ISREPORTSHOWCONTRACT"))){
				contractTypeStr=hmAllParam.get("FDC_ISREPORTSHOWCONTRACT").toString().split(";");
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
	    
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(toDate);

	    Integer year=cal.get(Calendar.YEAR);
		Integer month=cal.get(Calendar.MONTH)+1;
		
		Date lsttoDate=FDCDateHelper.getLastDayOfMonth(FDCDateHelper.getPreMonth(toDate));
		
		cal = Calendar.getInstance();
		cal.setTime(lsttoDate);
		Integer lstyear=cal.get(Calendar.YEAR);
		Integer lstmonth=cal.get(Calendar.MONTH)+1;
		
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select REPLACE(contractType.flongNumber, '!', '.')||'   '||contractType.fname_l2 contractType,contract.fid id,contract.fnumber number,contract.fname name,supplier.fname_l2 supplier,contract.fcontractPropert contractPropert,isnull(t10.amount,0) pcAmount,contract.fsrcAmount srcAmount,contract.foriginalAmount originalAmount,contract.famount amount,");
    	sb.append("	settle.fOriginalAmount settleOriginalAmount,settle.fsettlePrice settleAmount,isnull(t1.costAmount,0),isnull(t11.amount,0) totalWorkLoad,isnull(t11.appAmount,0) appAmount,isnull(t1.invoiceAmt,0) invoiceAmt,isnull(t16.deductAmt,0) deductAmt,isnull(t1.reqAmount,0) reqAmount,isnull(t2.payAmount,0) payAmount,(case when (case when settle.fcontractBillId is null then contract.famount else settle.fsettlePrice end) is null or (case when settle.fcontractBillId is null then contract.famount else settle.fsettlePrice end)=0 then 0 else isnull(t2.payAmount,0)*100/(case when settle.fcontractBillId is null then contract.famount else settle.fsettlePrice end) end) payRate,");
    	sb.append("	isnull(t12.appAmount,0) lstAppAmount,isnull(t5.amount,0) lstPayAmount,isnull(t15.appAmount,0)-isnull(t14.payAmount,0) lstEndAppAmount,isnull(t7.costAmount,0) lstCostAmount,isnull(t12.amount,0) workLoad,isnull(t17.nowDeductAmount,0) nowDeductAmount,isnull(t13.appAmount,0) nowAppAmount,isnull(t8.amount,0) nowReqAmount,isnull(t9.amount,0) nowPayAmount");
    	sb.append("	from t_con_contractBill contract left join t_bd_supplier supplier on supplier.fid=contract.fpartBId left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId");
    	sb.append(" left join T_CON_ContractSettlementBill settle on settle.fcontractBillId=contract.fid");
    	
    	sb.append(" left join (select sum(fcompletePrjAmt) costAmount,sum(famount) reqAmount,sum(finvoiceAmt) invoiceAmt,fcontractId contractId from t_con_payrequestbill where fstate!='1SAVED'");
    	sb.append(" and fbookedDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    	sb.append(" and fbookedDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    	sb.append(" group by fcontractId) t1 on t1.contractId=contract.fid");
    	
    	sb.append(" left join (select sum(b.famount) deductAmt,fcontractId contractId from t_con_payrequestbill a left join T_CON_DeductOfPayReqBill b on a.fid=b.fpayrequestbillid where fstate!='1SAVED'");
    	sb.append(" and fbookedDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    	sb.append(" and fbookedDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    	sb.append(" group by fcontractId) t16 on t16.contractId=contract.fid");
    	
    	sb.append(" left join (select sum(b.famount) nowDeductAmount,fcontractId contractId from t_con_payrequestbill a left join T_CON_DeductOfPayReqBill b on a.fid=b.fpayrequestbillid where fstate!='1SAVED'");
    	sb.append(" and year(fbookedDate)="+year+" and month(fbookedDate)="+month);
    	sb.append(" group by fcontractId) t17 on t17.contractId=contract.fid");
    	
    	sb.append(" left join (select sum(FAmount) payAmount,fcontractbillid contractId from t_cas_paymentbill where fbillstatus=15");
    	sb.append(" and fpayDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    	sb.append(" and fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    	sb.append(" group by fcontractbillid) t2 on t2.contractId=contract.fid");
    	
    	sb.append(" left join (select sum(fcompletePrjAmt) costAmount,sum(famount) reqAmount,fcontractId contractId from t_con_payrequestbill where fstate!='1SAVED'");
    	sb.append(" and fbookedDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    	sb.append(" and fbookedDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(lsttoDate))+ "'}");
    	sb.append(" group by fcontractId) t7 on t7.contractId=contract.fid");
    	
//    	sb.append(" left join (select dateEntry.famount amount,gatherEntry.contractBillId contractId from T_FNC_OrgUnitMonthPlanGather bill");
//    	sb.append(" left join T_FNC_OrgUnitMonthPGEntry entry on bill.fid=entry.fheadId");
//    	sb.append(" left join T_FNC_ProjectMonthPlanGather gather on gather.fid=entry.fsrcId");
//    	sb.append(" left join T_FNC_ProjectMonthPlanGEntry gatherEntry on gatherEntry.fheadId=gather.fid");
//    	sb.append(" left join T_FNC_ProjectMonthPGDateEntry dateEntry on dateEntry.fheadentryId=gatherEntry.fid");
//    	sb.append(" where bill.fstate='4AUDITTED' and bill.fIsLatest=1 and year(bill.fbizDate)="+year+" and month(bill.fbizDate)="+month);
//    	sb.append(" and dateEntry.fyear="+year+" and dateEntry.fmonth="+month+") t3 on t3.contractId=contract.fid");
    	
//    	sb.append(" left join (select dateEntry.famount amount,dateEntry.FAmt amt,gatherEntry.contractBillId contractId from T_FNC_OrgUnitMonthPlanGather bill");
//    	sb.append(" left join T_FNC_OrgUnitMonthPGEntry entry on bill.fid=entry.fheadId");
//    	sb.append(" left join T_FNC_ProjectMonthPlanGather gather on gather.fid=entry.fsrcId");
//    	sb.append(" left join T_FNC_ProjectMonthPlanGEntry gatherEntry on gatherEntry.fheadId=gather.fid");
//    	sb.append(" left join T_FNC_ProjectMonthPGDateEntry dateEntry on dateEntry.fheadentryId=gatherEntry.fid");
//    	sb.append(" where bill.fstate='4AUDITTED' and bill.fIsLatest=1 and year(bill.fbizDate)="+lstyear+" and month(bill.fbizDate)="+lstmonth);
//    	sb.append(" and dateEntry.fyear="+lstyear+" and dateEntry.fmonth="+lstmonth+") t4 on t4.contractId=contract.fid");
    	
    	sb.append(" left join (select sum(FAmount) amount,fcontractbillid contractId from t_cas_paymentbill where fbillstatus=15");
    	sb.append(" and year(fpayDate)="+lstyear+" and month(fpayDate)="+lstmonth);
    	sb.append(" group by fcontractbillid) t5 on t5.contractId=contract.fid");
    	
//    	sb.append(" left join (select sum(gatherEntry.famount-gatherEntry.factPayAmount) amount,pay.fcontractId contractId from T_FNC_OrgUnitMonthPlanGather bill");
//    	sb.append(" left join T_FNC_OrgUnitMonthPGEntry entry on bill.fid=entry.fheadId");
//    	sb.append(" left join T_FNC_ProjectMonthPlanGather gather on gather.fid=entry.fsrcId");
//    	sb.append(" left join T_FNC_ProjectMPlanGPayEntry gatherEntry on gatherEntry.fheadId=gather.fid");
//    	sb.append(" left join t_con_payrequestbill pay on gatherEntry.FPayRequestBillId=pay.fid");
//    	sb.append(" where bill.fstate='4AUDITTED' and bill.fIsLatest=1 and year(bill.fbizDate)="+lstyear+" and month(bill.fbizDate)="+lstmonth);
//    	sb.append(" group by pay.fcontractId) t6 on t6.contractId=contract.fid");
    	
    	sb.append(" left join (select sum(famount) amount,fcontractId contractId from t_con_payrequestbill where fstate!='1SAVED'");
    	sb.append(" and year(fbookedDate)="+year+" and month(fbookedDate)="+month);
    	sb.append(" group by fcontractId) t8 on t8.contractId=contract.fid");
    	
    	sb.append(" left join (select sum(FAmount) amount,fcontractbillid contractId from t_cas_paymentbill where fbillstatus=15");
    	sb.append(" and year(fpayDate)="+year+" and month(fpayDate)="+month);
    	sb.append(" group by fcontractbillid) t9 on t9.contractId=contract.fid");
    	
    	sb.append(" left join (select sum(fworkLoad) amount,sum(fappAmount) appAmount,fcontractbillid contractId from T_FNC_WorkLoadConfirmBill where fstate='4AUDITTED'");
    	sb.append(" group by fcontractbillid) t11 on t11.contractId=contract.fid");
    	
    	sb.append(" left join (select sum(fworkLoad) amount,sum(fappAmount) appAmount,fcontractbillid contractId from T_FNC_WorkLoadConfirmBill where fstate='4AUDITTED'");
    	sb.append(" and year(fconfirmDate)="+lstyear+" and month(fconfirmDate)="+lstmonth);
    	sb.append(" group by fcontractbillid) t12 on t12.contractId=contract.fid");
    	
    	sb.append(" left join (select sum(fworkLoad) amount,sum(fappAmount) appAmount,fcontractbillid contractId from T_FNC_WorkLoadConfirmBill where fstate='4AUDITTED'");
    	sb.append(" and year(fconfirmDate)="+year+" and month(fconfirmDate)="+month);
    	sb.append(" group by fcontractbillid) t13 on t13.contractId=contract.fid");
    	
    	sb.append(" left join (select sum(FAmount) payAmount,fcontractbillid contractId from t_cas_paymentbill where fbillstatus=15");
    	sb.append(" and fpayDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    	sb.append(" and fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(lsttoDate))+ "'}");
    	sb.append(" group by fcontractbillid) t14 on t14.contractId=contract.fid");
    	
    	sb.append(" left join (select sum(fappAmount) appAmount,fcontractbillid contractId from T_FNC_WorkLoadConfirmBill where fstate='4AUDITTED'");
    	sb.append(" and fconfirmDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    	sb.append(" and fconfirmDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(lsttoDate))+ "'}");
    	sb.append(" group by fcontractbillid) t15 on t15.contractId=contract.fid");
    	
    	sb.append(" left join (select sum(pc.FAmount) amount,pc.fcontractTypeId contractTypeId from T_CON_ProgrammingContract pc left join T_CON_Programming pro on pro.fid=pc.FProgrammingID");
    	sb.append(" where pro.FIsLatest=1 and pro.fstate='4AUDITTED'");
    	if(curProject!=null){
    		sb.append(" and pro.fprojectId in("+curProject+")");
    	}else{
    		sb.append(" and pro.fprojectId ='null'");
    	}
    	sb.append(" group by pc.fcontractTypeId) t10 on t10.contractTypeId=contract.fcontractTypeId");
    	
    	sb.append(" where contract.fstate='4AUDITTED'");
    	if(curProject!=null){
    		sb.append(" and contract.FCurProjectID in("+curProject+")");
    	}else{
    		sb.append(" and contract.FCurProjectID ='null'");
    	}
    	if(contractTypeStr!=null&&contractTypeStr.length>0){
    		sb.append(" and (contractType.fisCost is null");
    		for(int i=0;i<contractTypeStr.length;i++){
    			sb.append(" or contractType.flongNumber like '"+contractTypeStr[i]+"%'");
    		}
    		sb.append(" )");
    	}
    	if(contractType!=null){
    		sb.append(" and contractType.fid in("+contractType+")");
    	}
    	sb.append(" order by contractType.flongNumber,(case when contract.fcontractPropert='SUPPLY' then contract.fmainContractNumber else contract.fnumber end),contract.fcontractPropert");
    	return sb.toString();
    }
}