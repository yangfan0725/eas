package com.kingdee.eas.fdc.tenancy.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.util.DBUtil;
import com.kingdee.bos.db.TempTablePool;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenanycAppAmtInfo;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;

public class TenancyContractRptFacadeControllerBean extends AbstractTenancyContractRptFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.TenancyContractRptFacadeControllerBean");
    
    @Override
    protected RptParams _query(Context ctx, RptParams params)
    		throws BOSException, EASBizException {
    	
    	String contractId = null;
    	StringBuffer room =null;
	    if(params.getObject("room")!=null){
	    	room=new StringBuffer();
	    	Object[] roomObject = (Object[])params.getObject("room");
        	for(int i=0;i<roomObject.length;i++){
        		if(roomObject[i]==null) continue;
            	if(i==0){
            		room.append("'"+((RoomInfo)roomObject[i]).getId().toString()+"'");
            	}else{
            		room.append(",'"+((RoomInfo)roomObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer tenancyBill =null;
	    if(params.getObject("tenancyBill")!=null){
	    	tenancyBill=new StringBuffer();
	    	Object[] tenancyBillObject = (Object[])params.getObject("tenancyBill");
        	for(int i=0;i<tenancyBillObject.length;i++){
        		if(tenancyBillObject[i]==null) continue;
            	if(i==0){
            		tenancyBill.append("'"+((TenancyBillInfo)tenancyBillObject[i]).getId().toString()+"'");
            	}else{
            		tenancyBill.append(",'"+((TenancyBillInfo)tenancyBillObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer customer =null;
	    if(params.getObject("customer")!=null){
	    	customer=new StringBuffer();
	    	Object[] customerObject = (Object[])params.getObject("customer");
        	for(int i=0;i<customerObject.length;i++){
        		if(customerObject[i]==null) continue;
            	if(i==0){
            		customer.append("'"+((FDCCustomerInfo)customerObject[i]).getId().toString()+"'");
            	}else{
            		customer.append(",'"+((FDCCustomerInfo)customerObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer moneyDefine =null;
	    if(params.getObject("moneyDefine")!=null){
	    	moneyDefine=new StringBuffer();
	    	Object[] moneyDefineObject = (Object[])params.getObject("moneyDefine");
        	for(int i=0;i<moneyDefineObject.length;i++){
        		if(moneyDefineObject[i]==null) continue;
            	if(i==0){
            		moneyDefine.append("'"+((MoneyDefineInfo)moneyDefineObject[i]).getId().toString()+"'");
            	}else{
            		moneyDefine.append(",'"+((MoneyDefineInfo)moneyDefineObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    Integer syear = (Integer)params.getObject("syear");
	    Integer smonth =   (Integer)params.getObject("smonth");
	    
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, syear);
		cal.set(Calendar.MONTH, smonth-1);

		Date fromDate=FDCDateHelper.getFirstDayOfMonth(cal.getTime());
		
		
		Integer eyear = (Integer)params.getObject("eyear");
	    Integer emonth =   (Integer)params.getObject("emonth");
	    
		cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, eyear);
		cal.set(Calendar.MONTH, emonth-1);

		Date toDate=FDCDateHelper.getLastDayOfMonth(cal.getTime());
		
		
	    Boolean isAll=params.getBoolean("isAll");
    	
	    
	    StringBuffer conSql = new StringBuffer();
	    conSql.append(" and con.ftenancyState in " + (isAll?"('Audited','Executing','ContinueTenancying','Expiration')":"('Audited', 'Executing', 'ContinueTenancying')"));
	    conSql.append(StringUtils.isNotBlank(params.getString("sellProject"))?" and sp.fid in ("+params.getString("sellProject")+")":"  and 1=2 ");
	    if(room!=null&&!"".equals(room.toString())){
	    	conSql.append(" and room.fid in("+room+")");
    	}
    	if(tenancyBill!=null&&!"".equals(tenancyBill.toString())){
    		conSql.append(" and con.fid in("+tenancyBill+")");
    	}
    	if(customer!=null&&!"".equals(customer.toString())){
    		conSql.append(" and EXISTS(select ftenancyBillId from T_TEN_TenancyCustomerEntry where ffdccustomerid in("+customer+") and con.fid=ftenancyBillId)");
    	}
    	if(moneyDefine!=null&&!"".equals(moneyDefine.toString())){
    		conSql.append(" and md.fid in("+moneyDefine+")");
    	}
//    	if(fromDate!=null){
//    		conSql.append(" and pay.fappDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
//		}
//		if(toDate!=null){
//			conSql.append(" and pay.fappDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
//		}
    	
    	String tempSql = "  select                                                                                                                  "+
					    	"    wm_concat(to_char(ffreestartdate,'yyyy-mm-dd')||'-'||to_char(ffreeenddate,'yyyy-mm-dd')) freedesc,                           "+
					    	"    sum(decode(fmonths,0,1,fmonths)) allMonth,                                                                                   "+
					    	"    sum(fdays) allDays,                                                                                                          "+
					    	"    ftenancyid                                                                                                                   "+
					    	" from(                                                                                                                           "+
					    	" SELECT  ROUND(TRUNC(CAST(t.ffreeenddate AS DATE), 'DD') - TRUNC(CAST(t.ffreestartdate AS DATE), 'DD'))+1 fdays,                 "+
					    	"         ROUND (MONTHS_BETWEEN (TRUNC (CAST (t.ffreeenddate AS DATE),'MM'),TRUNC (CAST (ffreestartdate AS DATE),'MM'))) fmonths, "+
					    	"         t.ffreestartdate,                                                                                                       "+
					    	"         t.ffreeenddate,                                                                                                         "+
					    	"         t.ftenancyid                                                                                                            "+
					    	"   FROM t_ten_rentfreeentry T                                                                                                    "+
					    	"   order by t.ftenancyid, t.ffreestartdate)                                                                                      "+
					    	"   group by ftenancyid                                                                                                           ";
//    	
    	
    	
    	StringBuffer str = new StringBuffer();
    	str.append(" select fid id,fname_l2 name from t_she_moneydefine md where md.fmoneytype = 'DepositAmount' and md.fsystype = 'TenancySys' order by md.fnumber ");
    	RptRowSet moneyDefinesRs = executeQuery(str.toString(), null, ctx);
    	params.setObject("moneyDefinesRs",moneyDefinesRs);
    	//查询基础信息
    	String sql = "/*dialect*/select *                                                                                                       "+
			    	"  from (select distinct t.mdNumber,                                                                             "+
			    	"                        t.mdId,                                                                                 "+
			    	"                        t.mdName,                                                                               "+
			    	"                        t.conId,                                                                                "+
			    	"                        t.sellProject,                                                                          "+
			    	"                        t.room,                                                                                 "+
			    	"                        t.buildArea,                                                                            "+
			    	"                        t.tenancyArea,                                                                          "+
			    	"                        t.conNumber,                                                                            "+
			    	"                        t.conName,                                                                              "+
			    	"                        t.customer,                                                                             "+
			    	"                        t.constate,                                                                             "+
			    	"                        t.startDate,                                                                            "+
			    	"                        t.endDate,                                                                              "+
			    	"                        t.conBizDate,                                                                           "+
			    	"                        t.conAuditDate,                                                                         "+
			    	"                        t.freeDays,                                                                             "+
			    	"                        t.dealTotal,                                                                            "+
			    	"                        t.dealPrice,                                                                            "+
			    	"                        t.roomPrice,                                                                            "+
			    	"                        t.moneyDefine,                                                                          "+
			    	"                        t.moneyType,                                                                            "+
			    	"                        t.roomState,                                                                            "+
			    	"                        t.freeStartDate,                                                                        "+
			    	"                        t.freeEndDate,                                                                          "+
			    	"                        t.leaseMonths,                                                                          "+
			    	"                        t.leaseDays,                                                                            "+
			    	"                        t.conLeaseTime,                                                                         "+
			    	"                        t.freeMonths, " +
			    	"                        t.dealPriceA,                                                                           "+
			    	"                        t.sellProjectId, to_char(t.freedesc) frdesc                                             " +
			    	"          from (select md.fnumber mdNumber,                                                                     "+
			    	"                       md.fid mdId,                                                                             "+
			    	"                       md.fname_l2 mdName,                                                                      "+
			    	"                       con.fid conId,                                                                           "+
			    	"                       sp.fid sellProjectId,                                                                    "+
			    	"                       sp.fname_l2 sellProject,                                                                 "+
			    	"                       con.ftenRoomsDes room,                                                                   "+
			    	"                       room.FBuildingArea buildArea,                                                            "+
			    	"                       roomEntry.fbuildingArea tenancyArea,                                                     "+
			    	"                       con.fnumber conNumber,                                                                   "+
			    	"                       con.ftenancyName conName,                                                                "+
			    	"                       con.ftencustomerDes customer,                                                            "+
			    	"                       con.ftenancystate constate,                                                              "+
			    	"                       con.fstartDate startDate,                                                                "+
			    	"                       con.fendDate endDate,                                                                    "+
			    	"                       con.fcreatetime conbizDate,                                                              "+
			    	"                       con.faudittime conAuditDate,                                                             "+
			    	"                       rent.ffreeStartDate freeStartDate,                                                       "+
			    	"                       rent.ffreeEndDate freeEndDate,                                                           "+
			    	"                       ftt.alldays  freeDays,  ftt.allMonth  freeMonths, ftt.freedesc ,                          "+
			    	"                       ROUND(TRUNC(CAST(con.fendDate AS DATE), 'DD') - TRUNC(CAST(con.fstartDate AS DATE), 'DD'))+1 leaseDays, " +
			    	"                       ROUND (MONTHS_BETWEEN (TRUNC (CAST (con.fendDate AS DATE),'MM'),TRUNC (CAST (con.fstartDate AS DATE),'MM')))+1   leaseMonths, "+
			    	                        "con.fdealTotalRent dealPriceA,  "+
			    	"                       con.fdealTotalRent + nvl(other.amount, 0) dealTotal,                                  "+
			    	"                       roomEntry.fdealRoomRentPrice dealPrice,                                                  "+
			    	"                       roomEntry.fdealRoomRentPrice / roomEntry.fbuildingArea roomPrice,                        "+
			    	"                       md.fname_l2 moneyDefine,                                                                 "+
			    	"                       md.fmoneytype moneyType,                                                                 "+
			    	"                       room.ftenancystate roomstate,                                                            "+
			    	"                       con.fleasetime conLeaseTime                                                             "+
			    	"                  from T_TEN_TenancyBill con                                                                    "+
			    	"                  left join T_TEN_TenancyRoomEntry roomEntry on con.fid = roomEntry.ftenancyId                  "+
			    	"                  left join T_TEN_TenancyCustomerEntry customerEntry on con.fid = customerEntry.ftenancyBillId  "+
			    	"                  left join t_she_room room on room.fid = roomEntry.froomId                                     "+
			    	"                  left join t_she_building build on build.fid = room.fbuildingId  left join ("+tempSql+ ") ftt on ftt.ftenancyid =con.fid "+
			    	"                  left join t_she_sellProject sp on sp.fid = con.fsellProjectid                                 "+
			    	"                  left join T_TEN_TenancyRoomPayListEntry pay on pay.ftenRoomId = roomEntry.fid                 "+
			    	"                  left join t_she_moneyDefine md on md.fid = pay.fmoneyDefineId                                 "+
			    	"                  left join T_TEN_RentFreeEntry rent on rent.ftenancyId = con.fid                               "+
			    	"                  left join (select sum(fappAmount) amount,                                                     "+
			    	"                                   bill.FTenancyBillId conId                                                    "+
			    	"                              from T_TEN_OtherBill bill                                                         "+
			    	"                              left join T_TEN_TenBillOtherPay entry                                             "+
			    	"                                on bill.fid = entry.FOtherBillId                                                "+
			    	"                             group by bill.FTenancyBillId) other                                                "+
			    	"                    on other.conId = con.fid                                                                    "+
			    	"                 where md.fid is not null                                                                       "+conSql.toString()+
			    	"                union all                                                                                       "+
			    	"                select md.fnumber mdNumber,                                                                     "+
			    	"                       md.fid mdId,                                                                             "+
			    	"                       md.fname_l2 mdName,                                                                      "+
			    	"                       con.fid conId,                                                                           "+
			    	"                       sp.fid sellProjectId,                                                                    "+
			    	"                       sp.fname_l2 sellProject,                                                                 "+
			    	"                       con.ftenRoomsDes room,                                                                   "+
			    	"                       room.FBuildingArea buildArea,                                                            "+
			    	"                       roomEntry.fbuildingArea tenancyArea,                                                     "+
			    	"                       con.fnumber conNumber,                                                                   "+
			    	"                       con.ftenancyName conName,                                                                "+
			    	"                       con.ftencustomerDes customer,                                                            "+
			    	"                       con.ftenancystate constate,                                                              "+
			    	"                       con.fstartDate startDate,                                                                "+
			    	"                       con.fendDate endDate,                                                                    "+
			    	"                       con.fcreatetime conbizDate,                                                              "+
			    	"                       con.faudittime conAuditDate,                                                             "+
			    	"                       rent.ffreeStartDate freeStartDate,                                                       "+
			    	"                       rent.ffreeEndDate freeEndDate,                                                           "+
			    	"                      ftt.alldays  freeDays,  ftt.allMonth  freeMonths, ftt.freedesc ,     "+
			    	"                      ROUND(TRUNC(CAST(con.fendDate AS DATE), 'DD') - TRUNC(CAST(con.fstartDate AS DATE), 'DD'))+1 leaseDays, " +
			    	"                      ROUND (MONTHS_BETWEEN (TRUNC (CAST (con.fendDate AS DATE),'MM'),TRUNC (CAST (con.fstartDate AS DATE),'MM')))+1   leaseMonths,  con.fdealTotalRent dealPriceA,                           "+
			    	"                       con.fdealTotalRent + nvl(other.amount, 0) dealTotal,                                  "+
			    	"                       roomEntry.fdealRoomRentPrice dealPrice,                                                  "+
			    	"                       roomEntry.fdealRoomRentPrice / roomEntry.fbuildingArea roomPrice,                        "+
			    	"                       md.fname_l2 moneyDefine,                                                                 "+
			    	"                       md.fmoneytype moneyType,                                                                 "+
			    	"                       room.ftenancystate roomState,                                                            "+
			    	"                       con.fleasetime conLeaseTime                                                              "+
			    	"                  from T_TEN_TenancyBill con                                                                    "+
			    	"                  left join T_TEN_TenancyRoomEntry roomEntry on con.fid = roomEntry.ftenancyId                  "+
			    	"                  left join T_TEN_TenancyCustomerEntry customerEntry on con.fid = customerEntry.ftenancyBillId  "+
			    	"                  left join t_she_room room on room.fid = roomEntry.froomId                                     "+
			    	"                  left join t_she_building build  on build.fid = room.fbuildingId     left join ("+tempSql+ ") ftt on ftt.ftenancyid =con.fid "+                          
			    	"                  left join t_she_sellProject sp  on sp.fid = con.fsellProjectid                                "+
			    	"                  left join T_TEN_TenBillOtherPay pay on pay.fheadId = con.fid                                  "+
			    	"                  left join t_she_moneyDefine md on md.fid = pay.fmoneyDefineId                                 "+
			    	"                  left join T_TEN_RentFreeEntry rent on rent.ftenancyId = con.fid                               "+
			    	"                  left join (select sum(fappAmount) amount,                                                     "+
			    	"                                   bill.FTenancyBillId conId                                                    "+
			    	"                              from T_TEN_OtherBill bill                                                         "+
			    	"                              left join T_TEN_TenBillOtherPay entry                                             "+
			    	"                                on bill.fid = entry.FOtherBillId                                                "+
			    	"                             group by bill.FTenancyBillId) other                                                "+
			    	"                    on other.conId = con.fid                                                                    "+
			    	"                 where md.fid is not null                                                                       "+conSql.toString()+
			    	"                  ) t) t  order by t.sellProject,t.room, t.conNumber, t.mdNumber ";
    	
    	RptRowSet allAmtRs = executeQuery(sql, null, ctx);
    	params.setObject("allAmtRs",allAmtRs);

    	sql = " select                                                                                                                                          "+
	    	"     mdid, moneyName,                                                                                                                            "+
	    	"     conid,                                                                                                                                      "+
	    	"     moneyType,                                                                                                                                  "+
	    	"     sum(appAmount) appAmt,                                                                                                                      "+
	    	"     sum(actrevamount) revAmt,                                                                                                                   "+
	    	"     sum(appAmount)-sum(actrevamount) delayAmt                                                                                                                       "+
	    	" from(                                                                                                                                           "+
	    	" select                                                                                                                                          "+
	    	" 	md.fid mdId,                                                                                                                                  "+
	    	" 	md.fmoneyType moneyType,  md.fname_l2 moneyName,                                                                                               "+
	    	" 	con.fid conId,                                                                                                                                "+
	    	" 	isnull (pay.fappAmount,	0) appAmount,                                                                                                         "+
	    	" 	isnull (pay.factRevAmount,	0) - isnull (pay.fhasrefundmentamount,	0) actRevAmount                                                         "+
	    	" from                                                                                                                                            "+
	    	" 	T_TEN_TenancyRoomPayListEntry pay                                                                                                             "+
	    	" 	left join T_TEN_TenancyRoomEntry roomEntry on pay.ftenRoomId = roomEntry.fid                                                                  "+
	    	" 	left join T_TEN_TenancyBill con on con.fid = roomEntry.ftenancyId                                                                             "+
	    	" 	left join t_she_moneyDefine md on md.fid = pay.fmoneyDefineId                                                                                 "+
	    	" 	left join T_TEN_TenancyRoomEntry roomEntry1 on con.fid = roomEntry1.ftenancyId                                                                "+
	    	" 	left join t_she_room room on room.fid = roomEntry.froomId                                                                                     "+
	    	" 	left join t_she_sellProject sp on sp.fid = con.fsellProjectid                                                                                 "+
	    	" where                                                                                                                                           "+
	    	" 	1 = 1                                                                                                                                      "+conSql.toString()+
	    	" union all                                                                                                                                       "+
	    	" select                                                                                                                                          "+
	    	" 	md.fid mdId,                                                                                                                                  "+
	    	" 	md.fmoneyType moneyType, md.fname_l2 moneyName,                                                                                                "+
	    	" 	con.fid conId,                                                                                                                                "+
	    	" 	isnull (pay.fappAmount,	0) appAmount,	                                                                                                      "+
	    	" 	isnull (pay.factRevAmount,0) - isnull (pay.fhasrefundmentamount,0) actRevAmount                                                            "+
	    	" from                                                                                                                                            "+
	    	" 	T_TEN_TenBillOtherPay pay                                                                                                                     "+
	    	" 	left join T_TEN_TenancyBill con on con.fid = pay.fheadId                                                                                      "+
	    	" 	left join t_she_moneyDefine md on md.fid = pay.fmoneyDefineId                                                                                 "+
	    	" 	left join T_TEN_TenancyRoomEntry roomEntry on con.fid = roomEntry.ftenancyId                                                                  "+
	    	" 	left join t_she_room room on room.fid = roomEntry.froomId left join t_she_sellProject sp on sp.fid = con.fsellProjectid                       "+
	    	" where                                                                                                                                           "+
	    	" 	1 = 1                                                                                                                                      "+conSql.toString()+
	    	" 	)                                                                                                                                             "+
	    	" 	group by mdid,moneyName,moneyType,conid                                                                                                                 ";
    	
    	   RptRowSet rs = executeQuery(sql, null, ctx);
    	   Map<String,TenanycAppAmtInfo> appMap = new HashMap<String, TenanycAppAmtInfo>();
    	   Map<String,Integer> existsOther = new HashMap<String, Integer>();
    	   Map<String,TenanycAppAmtInfo> rentMap = new HashMap<String, TenanycAppAmtInfo>();
    	   Map<String,List<TenanycAppAmtInfo>> depMap = new HashMap<String, List<TenanycAppAmtInfo>>();
    	   String key = null;
    	   String moneyType = null;
    	   while(rs.next()){
    		   TenanycAppAmtInfo info = new TenanycAppAmtInfo();
    		   key =rs.getString("conid")+rs.getString("mdid")+"other";
    		   moneyType = rs.getString("moneyType");
    		   info.setKey(key);
    		   info.setMoneyTypeId(rs.getString("mdid"));
    		   info.setMoneyDefineName(rs.getString("moneyName"));
    		   info.setMoneyDefineType(rs.getString("moneyType"));
    		   info.setAppAmt(rs.getBigDecimal("appAmt"));
    		   info.setActAmt(rs.getBigDecimal("revAmt"));
    		   info.setDelayAmt(rs.getBigDecimal("delayAmt"));
    		   if(moneyType.equals(MoneyTypeEnum.DEPOSITAMOUNT_VALUE)){
    			   String depKey = rs.getString("conid")+"dep";
    			   if(depMap.containsKey(depKey)){
    				   depMap.get(depKey).add(info);
    			   }else{
    				  List<TenanycAppAmtInfo>  list = new ArrayList<TenanycAppAmtInfo>();
    				  list.add(info);
    				  depMap.put(depKey, list);
    			   }
    			   
    		   }else if(moneyType.equals(MoneyTypeEnum.RENTAMOUNT_VALUE)){
    			   rentMap.put(rs.getString("conid")+"rent", info);
    		   }else if(moneyType.equals(MoneyTypeEnum.ELSEAMOUNT_VALUE)){
    			   appMap.put(key, info);
    			   existsOther.put(rs.getString("conid")+"other", 1);
    		   }
    		  
    	   }

    	   params.setObject("existsOther", existsOther);
    	   params.setObject("apAmt", appMap);
    	   params.setObject("rentMap", rentMap);
    	   params.setObject("depMap", depMap);
    	
    	return params;
    }
}