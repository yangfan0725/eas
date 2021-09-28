package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

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

import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class MarketProjectFKReportFacadeControllerBean extends AbstractMarketProjectFKReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.MarketProjectFKReportFacadeControllerBean");
    
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
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"isLeaf",100,true);
	    initColoum(header,col,"number",100,false);
	    initColoum(header,col,"costAccount",100,false);
	    initColoum(header,col,"FYearAmount",100,false);
	    initColoum(header,col,"FYearLXAmount",100,false);
	    initColoum(header,col,"FYearFSAmount",100,false);
	    initColoum(header,col,"FMonthLJFSAmount",100,false);
	    initColoum(header,col,"FYearHTAmount",100,false);
	    initColoum(header,col,"FMonthAmount",100,false);
	    initColoum(header,col,"FMonthFSAmount",100,false);
	    initColoum(header,col,"FNextMonthAmount",100,false);
	    initColoum(header,col,"ZMonthAmount",100,false);
	    initColoum(header,col,"ZPayAmount",100,false);
	    initColoum(header,col,"ZYearPayAmount",100,false);
	    initColoum(header,col,"ZUnPayAmount",100,false);
	    initColoum(header,col,"ZNextMonthAmount",100,false);
	    
	    header.setLabels(new Object[][]{ 
	    		{
	    			"id","isLeaf","编码","预算项目","发生额","发生额","发生额","发生额","发生额","发生额","发生额","发生额","支付额","支付额","支付额","支付额","支付额"	    		}
	    		,
	    		{
	    			"id","isLeaf","编码","预算项目","年度预算","年度预算可用余额","年度累计发生","月度累计发生","年度合同额","本月预算","本月发生","下月预算","本月预算","本月支付","全年累计支付","累计未支付","下月预算"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
	    
		RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		params.setObject("rowset", rowSet);
		return params;
    }
    protected String getSql(Context ctx,RptParams params){
    	Integer fromYear = (Integer) params.getObject("fromYear");
    	Integer fromMonth = (Integer) params.getObject("fromMonth");
    	
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, fromYear);
		cal.set(Calendar.MONTH, fromMonth-1);
		cal.set(Calendar.DATE, 1);
		
		Date fromDate=FDCDateHelper.getFirstDayOfMonth(cal.getTime());
		
		Integer toYear = (Integer) params.getObject("toYear");
    	Integer toMonth = (Integer) params.getObject("toMonth");
    	
    	cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, toYear);
		cal.set(Calendar.MONTH, toMonth-1);
		cal.set(Calendar.DATE, 1);
		
		Date toDate=FDCDateHelper.getLastDayOfMonth(cal.getTime());
		
    	String org=params.getString("org");
    	StringBuffer sb = new StringBuffer();
    	
    	sb.append(" select c.fid,c.fisLeaf isLeaf,replace(c.flongnumber,'!','.') number,c.fname_l2 costAccount,entry.amount FYearAmount,entry.amount-isnull(t6.amount,0) FYearLXAmount,t6.amount FYearFSAmount,t11.amount FMonthLJFSAmount,t7.amount FYearHTAmount, t9.amount FMonthAmount,t8.amount FMonthFSAmount,t10.amount FNextMonthAmount,");
    	sb.append(" t1.amount ZMonthAmount,t4.amount ZPayAmount,t3.amount ZYearPayAmount,(t7.amount-t3.amount) ZUnPayAmount,t2.amount ZNextMonthAmount from T_CON_MarketYearProject m left join (select entry.fcostAccountid,sum(entry.famount) amount,entry.fheadid from T_CON_MarketYearProjectentry entry group by entry.fcostAccountid,entry.fheadid) entry on entry.fheadid=m.fid ");
    	sb.append(" left join t_fdc_costaccount c on c.fid=entry.fcostAccountId ");
    	
    	
//    	年度合同
    	sb.append(" left join(select t.fcostAccountid,sum(t.famount) amount from (");
    	sb.append(" select con.fid,con.FMpCostAccountId fcostAccountid,con.famount from");
    	sb.append(" t_con_contractbill con left join  T_CON_ContractMarketEntry entry  on con.fid=entry.fheadid ");
//    	sb.append(" and year(mp.fbizDate)="+fromYear);   	
    	sb.append(" where con.fstate='4AUDITTED' and year(entry.fdate)="+fromYear+" group by con.fid,con.FMpCostAccountId,con.famount");   	
    	sb.append(" union all select mpEntry.fid,mpEntry.fcostAccountid,mpEntry.famount from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid");
    	sb.append(" where mpEntry.ftype in('NOTEXTCONTRACT','JZ') and mp.fstate!='1SAVED' and year(mp.fbizDate)="+fromYear);
    	sb.append(" and not exists(select t1.fid from t_con_contractBill t1 where t1.fstate='4AUDITTED' and t1.FMarketProjectId=mp.fid and t1.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sb.append(" and exists(select t.fid from T_CON_ContractWithoutText t where t.fstate!='1SAVED' and t.FMarketProjectId=mp.fid and t.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sb.append(" union all select mpEntry.fcostAccountid,mpEntry.famount from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid where mp.fstate!='1SAVED'");
//    	sb.append(" and year(mp.fbizDate)="+fromYear);
//    	sb.append(" and mp.fisSub=1 ");
    	sb.append(" ) t group by t.fcostAccountid) t7 on t7.fcostAccountid=entry.fcostAccountId");
    	
//    	Calendar cal1=Calendar.getInstance();
//    	int month=cal1.get(Calendar.MONTH)+1;
//    	sb.append(" left join(select t.fcostAccountid,sum(t.famount) amount from (");
//    	sb.append(" select con.FMpCostAccountId fcostAccountid,(case when t.fsettleprice is null then entry.famount else t.fsettleprice*entry.frate/100 end) famount from");
//    	sb.append(" t_con_contractbill con left join T_CON_ContractMarketEntry entry   on con.fid=entry.fheadid left join (select sb.fsettleprice,sb.fcontractbillid from T_CON_ContractSettlementBill sb where sb.fstate='4AUDITTED') t on t.fcontractbillid=con.fid left join T_CON_MarketProject mp on mp.fid=con.fmarketProjectId where con.fstate='4AUDITTED'");
//    	sb.append(" month(entry.fdate)="+month);   	
//    	sb.append(" union all select mpEntry.fcostAccountid,mpEntry.famount from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid where mp.fstate!='1SAVED'");
//    	sb.append(" month(mp.fbizDate)="+month);  
//    	sb.append(" and  exists(select t.fid from T_CON_ContractWithoutText t where t.fstate!='1SAVED' and t.FMarketProjectId=mp.fid and t.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sb.append(" union all select mpEntry.fcostAccountid,mpEntry.famount from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid where mp.fstate!='1SAVED'");
//    	sb.append(" and month(mp.fbizDate)="+month);  
//    	sb.append(" and mp.fisSub=1 ");
//    	sb.append(" )t group by t.fcostAccountid) t8 on t8.fcostAccountid=entry.fcostAccountId");
    	 
    	
//    	年度累計
    	sb.append(" left join(select sum(t.famount) amount,fcostAccountid from(");
    	sb.append(" select (case when t.fsettleprice is null then entry.famount else t.fsettleprice*entry.frate/100 end)famount ,head.FMpCostAccountId fcostAccountid from T_CON_ContractMarketEntry entry left join t_con_contractbill head on head.fid=entry.fheadid");
    	sb.append(" left join (select sb.fsettleprice,sb.fcontractbillid from T_CON_ContractSettlementBill sb where sb.fstate='4AUDITTED') t on t.fcontractbillid=head.fid");
    	sb.append(" where head.fstate='4AUDITTED' and year(entry.fdate)="+fromYear);
    	sb.append(" union all select mpEntry.famount,mpEntry.fcostAccountid from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid");
    	sb.append(" where mp.fstate!='1SAVED' and year(mp.fbizDate)="+fromYear);
    	sb.append(" and not exists(select t1.fid from t_con_contractBill t1 where t1.fstate='4AUDITTED' and t1.FMarketProjectId=mp.fid and t1.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sb.append(" and mp.fbizDate>{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
//    	sb.append(" and mp.fbizDate<={ts '" + FDCConstants.FORMAT_TIME.formast(FDCDateHelper.getSQLEnd(toDate))+ "'}");
//    	sb.append(" and not exists(select t1.fid from t_con_contractBill t1 where t1.fstate='4AUDITTED' and t1.FMarketProjectId=mp.fid and t1.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sb.append(" and not exists(select t.fid from T_CON_ContractWithoutText t where t.fstate!='1SAVED' and t.FMarketProjectId=mp.fid and t.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sb.append(" union all select mpEntry.famount,mpEntry.fcostAccountid from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid where mp.fstate!='1SAVED'");
//    	sb.append(" and year(mp.fbizDate)="+fromYear);
//    	sb.append(" and  exists(select t.fid from T_CON_ContractWithoutText t where t.fstate!='1SAVED' and t.FMarketProjectId=mp.fid and t.FMpCostAccountId=mpEntry.fcostAccountid) ");
    	sb.append(" )t group by t.fcostAccountid) t6 on t6.fcostAccountid=entry.fcostAccountId");
    	
//    	===================================================================================================	
//    	sb.append(" left join(select sum(t.famount) amount,fcostAccountid from(");
//    	sb.append(" select (case when t.fsettleprice is null then entry.famount else t.fsettleprice*entry.frate/100 end)famount ,head.FMpCostAccountId fcostAccountid from T_CON_ContractMarketEntry entry left join t_con_contractbill head on head.fid=entry.fheadid");
//    	sb.append(" left join (select sb.fsettleprice,sb.fcontractbillid from T_CON_ContractSettlementBill sb where sb.fstate='4AUDITTED') t on t.fcontractbillid=head.fid where head.fstate='4AUDITTED'");
//    	sb.append(" and year(entry.fdate)="+fromYear+" and month(entry.fdate)="+month);
//    	sb.append(" union all select mpEntry.famount,mpEntry.fcostAccountid from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid where mp.fstate!='1SAVED'");
//    	sb.append(" and year(entry.fdate)="+fromYear+" and month(entry.fdate)="+month);
//    	sb.append(" and not exists(select t1.fid from t_con_contractBill t1 where t1.fstate='4AUDITTED' and t1.FMarketProjectId=mp.fid and t1.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sb.append(" )t group by t.fcostAccountid) t8 on t8.fcostAccountid=entry.fcostAccountId");

//    	====================================================================================================  
    	
//    	月度累计	
    	sb.append(" left join(select t.fcostAccountid,sum(t.famount) amount from (");
    	sb.append(" select con.FMpCostAccountId fcostAccountid,(case when t.fsettleprice is null then entry.famount else t.fsettleprice*entry.frate/100 end) famount from");
    	sb.append(" T_CON_ContractMarketEntry entry left join  t_con_contractbill con  on con.fid=entry.fheadid left join (select sb.fsettleprice,sb.fcontractbillid from T_CON_ContractSettlementBill sb where sb.fstate='4AUDITTED') t on t.fcontractbillid=con.fid left join T_CON_MarketProject mp on mp.fid=con.fmarketProjectId ");
    	sb.append(" where con.fstate='4AUDITTED' and year(entry.fdate)="+fromYear+" and month(entry.fdate) between "+fromMonth+" and "+toMonth);   	
    	sb.append(" union all select mpEntry.fcostAccountid,mpEntry.famount from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid ");
    	sb.append(" where mp.fstate!='1SAVED' and year(mp.fbizDate)="+fromYear+" and month(mp.fbizDate) between "+fromMonth+" and "+toMonth); 
    	sb.append(" and not exists(select t1.fid from t_con_contractBill t1 where t1.fstate='4AUDITTED' and t1.FMarketProjectId=mp.fid and t1.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sb.append(" and exists(select t.fid from T_CON_ContractWithoutText t where t.fstate!='1SAVED' and t.FMarketProjectId=mp.fid and t.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sb.append(" union all select mpEntry.fcostAccountid,mpEntry.famount from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid where mp.fstate!='1SAVED'");
//    	sb.append(" and year(mp.fbizDate)="+fromYear+" and year(mp.fbizDate) between "+fromMonth+" and "+toMonth); 
//    	sb.append(" and mp.fisSub=1 ");
    	sb.append(" )t group by t.fcostAccountid) t11 on t11.fcostAccountid=entry.fcostAccountId");
    	
//    	sb.append(" left join(select sum(t.famount) amount,fcostAccountid from(");
//    	sb.append(" select (case when t.fsettleprice is null then entry.famount else t.fsettleprice*entry.frate/100 end)famount ,head.FMpCostAccountId fcostAccountid from T_CON_ContractMarketEntry entry left join t_con_contractbill head on head.fid=entry.fheadid");
//    	sb.append(" left join (select sb.fsettleprice,sb.fcontractbillid from T_CON_ContractSettlementBill sb where sb.fstate='4AUDITTED') t on t.fcontractbillid=head.fid where head.fstate='4AUDITTED'");
//    	sb.append(" and month(entry.fdate) between "+fromMonth+" and "+toMonth);
//    	sb.append(" union all select mpEntry.famount,mpEntry.fcostAccountid from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid where mp.fstate!='1SAVED'");
//    	sb.append(" and month(mp.fbizDate) between "+fromMonth+" and "+toMonth);
//    	sb.append(" and not exists(select t1.fid from t_con_contractBill t1 where t1.fstate='4AUDITTED' and t1.FMarketProjectId=mp.fid and t1.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sb.append(" )t group by t.fcostAccountid) t11 on t11.fcostAccountid=entry.fcostAccountId");

    	
//    	本月发生
    	Calendar cal1=Calendar.getInstance();
    	int month=cal1.get(Calendar.MONTH)+1;
    	
    	
    	sb.append(" left join(select t.fcostAccountid,sum(t.famount) amount from (");
    	sb.append(" select con.FMpCostAccountId fcostAccountid,(case when t.fsettleprice is null then entry.famount else t.fsettleprice*entry.frate/100 end) famount from");
    	sb.append(" t_con_contractbill con left join T_CON_ContractMarketEntry entry  on con.fid=entry.fheadid left join (select sb.fsettleprice,sb.fcontractbillid from T_CON_ContractSettlementBill sb where sb.fstate='4AUDITTED') t on t.fcontractbillid=con.fid ");
    	sb.append(" where con.fstate='4AUDITTED' and year(entry.fdate)="+fromYear+" and month(entry.fdate)="+month);   	
    	sb.append(" union all select mpEntry.fcostAccountid,mpEntry.famount from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid");
    	sb.append(" where mp.fstate!='1SAVED' and year(mp.fbizDate)="+fromYear+" and month(mp.fbizDate)="+month);  
    	sb.append(" and not exists(select t1.fid from t_con_contractBill t1 where t1.fstate='4AUDITTED' and t1.FMarketProjectId=mp.fid and t1.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sb.append(" and not exists(select t.fid from T_CON_ContractWithoutText t where t.fstate='4AUDITTED' and t.FMarketProjectId=mp.fid and t.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sb.append(" union all select mpEntry.fcostAccountid,mpEntry.famount from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid where mp.fstate!='1SAVED'");
//    	sb.append(" and year(mp.fbizDate)="+fromYear+" and month(mp.fbizDate)="+month);  
//    	sb.append(" and mp.fisSub=1 ");
    	sb.append(" )t group by t.fcostAccountid) t8 on t8.fcostAccountid=entry.fcostAccountId");
    	
//    	sb.append(" left join(select sum(t.famount) amount,fcostAccountid from(");
//    	sb.append(" select (case when t.fsettleprice is null then entry.famount else t.fsettleprice*entry.frate/100 end)famount ,head.FMpCostAccountId fcostAccountid from T_CON_ContractMarketEntry entry left join t_con_contractbill head on head.fid=entry.fheadid");
//    	sb.append(" left join (select sb.fsettleprice,sb.fcontractbillid from T_CON_ContractSettlementBill sb where sb.fstate='4AUDITTED') t on t.fcontractbillid=head.fid where head.fstate='4AUDITTED'");
//    	sb.append(" and year(entry.fdate)="+fromYear+" and month(entry.fdate)="+month);
//    	sb.append(" union all select mpEntry.famount,mpEntry.fcostAccountid from T_CON_MarketProjectCostEntry mpEntry left join T_CON_MarketProject mp on mp.fid=mpEntry.fheadid where mp.fstate!='1SAVED'");
//    	sb.append(" and year(mp.fbizDate)="+fromYear+" and month(mp.fbizDate)="+month);
//    	sb.append(" and not exists(select t1.fid from t_con_contractBill t1 where t1.fstate='4AUDITTED' and t1.FMarketProjectId=mp.fid and t1.FMpCostAccountId=mpEntry.fcostAccountid) ");
//    	sb.append(" )t group by t.fcostAccountid) t8 on t8.fcostAccountid=entry.fcostAccountId");
    	
    	
    	sb.append(" left join(select entry.famount amount,entry.fcostAccountId from T_CON_MarketMonthProjectEntry entry left join T_CON_MarketMonthProject head on head.fid=entry.fheadid");
    	sb.append(" where head.fyear="+fromYear+" and head.fmonth="+fromMonth+") t9 on t9.fcostAccountId=entry.fcostAccountId");
    	
    	
    	int  nextYear=0;
    	int  nextMonth=0;
    	if(fromMonth==12){
    		nextYear=fromYear+1;
    		nextMonth=1;
    	}else{
    		nextYear=fromYear;
    		nextMonth=fromMonth+1;
    	}
    	sb.append(" left join(select entry.famount amount,entry.fcostAccountId from T_CON_MarketMonthProjectEntry entry left join T_CON_MarketMonthProject head on head.fid=entry.fheadid");
    	sb.append(" where head.fyear="+nextYear+" and head.fmonth="+nextMonth+") t10 on t10.fcostAccountId=entry.fcostAccountId");
    	
    	sb.append(" left join (select sum(dateEntry.famount) amount,contract.FMpCostAccountId from T_FNC_OrgUnitMonthPlanGather bill");
    	sb.append(" left join T_FNC_OrgUnitMonthPGEntry entry on bill.fid=entry.fheadId left join T_ORG_BaseUnit orgUnit on orgUnit.fid=bill.forgUnitId");
    	sb.append(" left join T_FNC_ProjectMonthPlanGather gather on gather.fid=entry.fsrcId");
    	sb.append(" left join T_FNC_ProjectMonthPlanGEntry gatherEntry on gatherEntry.fheadId=gather.fid");
    	sb.append(" left join t_con_contractbill contract on contract.fid=gatherEntry.contractBillId");
    	sb.append(" left join T_FNC_ProjectMonthPGDateEntry dateEntry on dateEntry.fheadentryId=gatherEntry.fid");
    	sb.append(" where bill.fIsLatest=1 and gatherEntry.contractBillId is not null");
    	sb.append(" and dateEntry.fyear=year(bill.fbizDate) and dateEntry.fmonth=month(bill.fbizDate)");
    	sb.append(" and year(bill.fbizDate)="+fromYear+" and month(bill.fbizDate)="+fromMonth);
    	
		sb.append(" group by contract.FMpCostAccountId ) t1 on t1.FMpCostAccountId=entry.fcostAccountId");
		
		sb.append(" left join (select sum(dateEntry.famount) amount,contract.FMpCostAccountId from T_FNC_OrgUnitMonthPlanGather bill");
    	sb.append(" left join T_FNC_OrgUnitMonthPGEntry entry on bill.fid=entry.fheadId left join T_ORG_BaseUnit orgUnit on orgUnit.fid=bill.forgUnitId");
    	sb.append(" left join T_FNC_ProjectMonthPlanGather gather on gather.fid=entry.fsrcId");
    	sb.append(" left join T_FNC_ProjectMonthPlanGEntry gatherEntry on gatherEntry.fheadId=gather.fid");
    	sb.append(" left join t_con_contractbill contract on contract.fid=gatherEntry.contractBillId");
    	sb.append(" left join T_FNC_ProjectMonthPGDateEntry dateEntry on dateEntry.fheadentryId=gatherEntry.fid");
    	sb.append(" where bill.fIsLatest=1 and gatherEntry.contractBillId is not null");
    	sb.append(" and dateEntry.fyear=year(bill.fbizDate) and dateEntry.fmonth=month(bill.fbizDate)");
    	sb.append(" and year(bill.fbizDate)="+nextYear+" and month(bill.fbizDate)="+nextMonth);
    	
		sb.append(" group by contract.FMpCostAccountId ) t2 on t2.FMpCostAccountId=entry.fcostAccountId");
		
		
		sb.append(" left join(select sum(pay.FAmount) amount,(case when contract.FMpCostAccountId is null then contractwt.FMpCostAccountId else contract.FMpCostAccountId end) FMpCostAccountId from t_cas_paymentbill pay");
		sb.append(" left join t_con_contractbill contract on contract.fid=pay.fcontractbillid");
		sb.append(" left join T_CON_ContractWithoutText contractwt on contractwt.fid=pay.fcontractbillid");
		sb.append(" where pay.fbillstatus=15 and pay.fcontractbillid is not null");
//		sb.append(" and pay.fpayDate>{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
//    	sb.append(" and pay.fpayDate<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		sb.append(" and year(pay.fpayDate)="+fromYear);
		sb.append(" group by (case when contract.FMpCostAccountId is null then contractwt.FMpCostAccountId else contract.FMpCostAccountId end) )t3 on t3.FMpCostAccountId=entry.fcostAccountId");
		
		sb.append(" left join(select sum(pay.FAmount) amount,(case when contract.FMpCostAccountId is null then contractwt.FMpCostAccountId else contract.FMpCostAccountId end) FMpCostAccountId from t_cas_paymentbill pay");
		sb.append(" left join t_con_contractbill contract on contract.fid=pay.fcontractbillid");
		sb.append(" left join T_CON_ContractWithoutText contractwt on contractwt.fid=pay.fcontractbillid");
		sb.append(" where pay.fbillstatus=15 and pay.fcontractbillid is not null");
		
		sb.append(" and pay.fpayDate>{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    	sb.append(" and pay.fpayDate<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    	
		sb.append(" group by (case when contract.FMpCostAccountId is null then contractwt.FMpCostAccountId else contract.FMpCostAccountId end) )t4 on t4.FMpCostAccountId=entry.fcostAccountId");
		
//		sb.append(" left join(select sum(case when contract.famount is null then contractwt.famount else contract.famount end)-sum(pay.FAmount) amount,(case when contract.FMpCostAccountId is null then contractwt.FMpCostAccountId else contract.FMpCostAccountId end) FMpCostAccountId from t_cas_paymentbill pay");
//		sb.append(" left join t_con_contractbill contract on contract.fid=pay.fcontractbillid");
//		sb.append(" left join T_CON_ContractWithoutText contractwt on contractwt.fid=pay.fcontractbillid");
//		sb.append(" where pay.fbillstatus=15 and pay.fcontractbillid is not null");
//		
//		sb.append(" and pay.fpayDate>{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
//    	sb.append(" and pay.fpayDate<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
//    
//		sb.append(" group by (case when contract.FMpCostAccountId is null then contractwt.FMpCostAccountId else contract.FMpCostAccountId end) )t5 on t5.FMpCostAccountId=entry.fcostAccountId");
	
    	sb.append(" where m.FIsLatest=1");
    	
		sb.append(" and m.fyear="+fromYear);
    	if(org!=null){
    		sb.append(" and m.forgUnitid='"+org+"'");
    	}
    	sb.append(" order by c.flongnumber");
    	return sb.toString();
    }
}