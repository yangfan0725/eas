package com.kingdee.eas.fdc.tenancy.app;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.jdbc.rowset.IRowSet;

public class ReturnTenacyRptFacadeControllerBean extends AbstractReturnTenacyRptFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.ReturnTenacyRptFacadeControllerBean");
    
    @Override
    protected RptParams _query(Context ctx, RptParams params)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	String projectId = null;
    	int startYear = 0;
    	int endYear = 9999;
    	
    	if(params.getString("sellProject") != null){
    		projectId = params.getString("sellProject");
    	}
    	
    	if(params.getObject("startDate") != null){
    		Date sd = (Date) params.getObject("startDate");
    		Calendar startCal = Calendar.getInstance();
    		startCal.setTime(sd);
    		startYear = startCal.get(Calendar.YEAR);
    		
    	}
    	
        if(params.getObject("endDate") != null){
        	Date ed = (Date) params.getObject("endDate");
    		Calendar edCal = Calendar.getInstance();
    		edCal.setTime(ed);
    		endYear = edCal.get(Calendar.YEAR);
    	}
    	
    	
    	
    	String sql = "/*dialect*/select                                            "
    		+"    sp.fid projectId,                                                "
    		+"    sp.fname_l2 projectName,                                         "
    		+"    r.fname_l2 roomNumber,                                            "
    		+"    r.fid roomId,                                                    "
    		+"    r.fbuildingarea area,r.fsellstate state, pm.fdealtotalamount salePrice, pm.fcustomernames saleCustomer, "
    		+"    rtc.fsigncustomer custName,                                      "
    		+"    rtc.fsignyears signYears,                                        "
    		+"    to_char(rtc.fsignstartdate,'yyyy-mm-dd') startDate,              "
    		+"    to_char(rtc.fsignenddate,'yyyy-mm-dd') endDate,                  "
    		+"    rtb.freturnyear returnYears,                                     "
    	    +"    to_char(rtb.fstartdate,'yyyy-mm-dd') rStartDate,                 "
    	    +"    to_char(rtb.fenddate,'yyyy-mm-dd') rEndYear,rtb.FStrOfReturnRate strOfRate    " 
    		+" from t_ten_returntenancycontract rtc                                 "
    		+" left join t_ten_returntenancybill rtb on rtb.fid = rtc.freturnbillid "     		
    		+" left join t_she_room r on rtb.froomid = r.fid                        "
    		+" left join t_she_signmanage pm on pm.froomid = r.fid              "
    		+" left join t_she_building b on b.fid = r.fbuildingid                  "
    		+" left join t_she_sellproject sp on sp.fid  = b.fsellprojectid  " ;
    				
    	if(!StringUtil.isEmptyString(projectId)){
    		sql=sql+" where b.fsellprojectid='"+projectId+"' order by  r.fnumber,rtc.fseq ";
    	}else{
    		sql=sql+" order by  r.fnumber,rtc.fseq ";
    	}
    	RptRowSet baseInfo = executeQuery(sql, null, ctx);
    	params.setObject("baseInfo", baseInfo);
    	
    	
    	//处理返租分录信息
    	sql =   "select                                                             "+
		    	"    sp.fid projectId,                                              "+
		    	"    r.fid roomId,                                                  "+
		    	"    ent.fyear year,                                                "+
		    	"    ent.fseq seq,                                                  "+
		    	"    ent.frateofreturn rate,                                        "+
		    	"    ent.famtofreturn amt                                           "+
		    	"from t_ten_returntenancyentry ent                                  "+
		    	"left join t_ten_returntenancybill rtb on rtb.fid = ent.fparentid   "+
		    	"left join t_she_room r on rtb.froomid = r.fid                      "+
		    	"left join t_she_building b on b.fid = r.fbuildingid                "+
		    	"left join t_she_sellproject sp on sp.fid  = b.fsellprojectid       ";
		    	
    	
    	if(!StringUtil.isEmptyString(projectId)){
    		sql=sql+" where b.fsellprojectid='"+projectId+"' order by r.fid,fseq   ";
    	}else{
    		sql=sql+" order by r.fid,fseq ";
    	}
    	RptRowSet rs = executeQuery(sql, null, ctx);
    	Map<String,Map> rateMap = new HashMap<String,Map>();
    	Map<String,Map> amtMap = new HashMap<String,Map>();
    	String key = null;
    	while(rs.next()){
    		key =rs.getString("projectId")+"_"+rs.getString("roomId");
    		if(rateMap.containsKey(key)){
    			rateMap.get(key).put(rs.getInt("seq"),rs.getBigDecimal("rate"));
    		}else{
    			Map<Integer,BigDecimal> yearrateMap = new HashMap<Integer,BigDecimal>();
    			yearrateMap.put(rs.getInt("seq"),rs.getBigDecimal("rate"));
    			rateMap.put(key, yearrateMap);
    		}
    		
    		if(amtMap.containsKey(key)){
    			amtMap.get(key).put(rs.getInt("year"),rs.getBigDecimal("amt"));
    		}else{
    			Map<Integer,BigDecimal> yearAmtMap = new HashMap<Integer,BigDecimal>();
    			yearAmtMap.put(rs.getInt("year"),rs.getBigDecimal("amt"));
    			amtMap.put(key, yearAmtMap);
    		}
    		
    	}
    	
    	params.setObject("rateMap", rateMap);
    	params.setObject("amtMap", amtMap);
    	
    	
    	sql =   " /*dialect*/select                                                                                                                                             "+
		    	"        tt.*,                                                                                                                                       "+
		    	"        totalRent-tt.totalReturn  diffAmt from (                                                                                                   "+
		    	" select projectId,                                                                                                                                  "+
		    	"        roomid,                                                                                                                                     "+
		    	"        customer,                                                                                                                                   "+
		    	"        year,                                                                                                                                       "+
		    	"        nvl(rent, 0) rent,                                                                                                                          "+
		    	"        sum(nvl(rent, 0)) over(partition by projectid, roomid, year order by 1) realrent,                                                           "+
		    	"        sum(nvl(rent, 0)) over(partition by projectid, roomid order by 1) totalRent,                                                                "+
		    	"        (select sum(re.famtofreturn) from t_ten_returntenancyentry re where re.fparentid=rtbid and re.fyear >= "+startYear+ "and re.fyear<="+endYear+") totalReturn "+
		    	"   from (select sp.fid                 projectId,                                                                                                   "+
		    	"                r.fid                  roomId,                                                                                                      "+
		    	"                contract.fsigncustomer customer,                                                                                                    "+
		    	"                fyear                  year,                                                                                                        "+
		    	"                famtofrent             rent,                                                                                                        "+
		    	"                rtb.fid rtbid                                                                                                                       "+
		    	"           from t_ten_returntenancyrententry rententry                                                                                              "+
		    	"          inner join t_ten_returntenancycontract contract                                                                                           "+
		    	"             on rententry.fcontractid = contract.fid                                                                                                "+
		    	"                                                                                                                                                    "+
		    	"           left join t_ten_returntenancybill rtb                                                                                                    "+
		    	"             on rtb.fid = contract.freturnbillid                                                                                                    "+
		    	"           left join t_she_room r                                                                                                                   "+
		    	"             on rtb.froomid = r.fid                                                                                                                 "+
		    	"           left join t_she_building b                                                                                                               "+
		    	"             on b.fid = r.fbuildingid                                                                                                               "+
		    	"           left join t_she_sellproject sp                                                                                                           "+
		    	"             on sp.fid = b.fsellprojectid                                                                                                           "+
		    	"  where fyear >= "+startYear+ " and fyear<= "+ endYear;
    	if(!StringUtil.isEmptyString(projectId)){
    		sql=sql+" and b.fsellprojectid='"+projectId+"' ";
    	}
    	sql =sql+")) tt";
    	
    	
    	
    	logger.info(sql);
    	rs = executeQuery(sql, null, ctx);
    	int maxYear = 0;
    	int minYear = 9999;
    	Map<String,Map> rentAmtMap = new HashMap<String,Map>();
    	while(rs.next()){
    		int curYear = rs.getInt("year");
    		if(curYear>maxYear){
    			maxYear = curYear;
    		}
    		if(curYear < minYear){
    			minYear = curYear;
    		}
    		key = rs.getString("projectId")+"_"+rs.getString("roomId")+"_"+rs.getString("customer");
    		if(rentAmtMap.containsKey(key)){
    			rentAmtMap.get(key).put(curYear,new BigDecimal[]{rs.getBigDecimal("rent"),rs.getBigDecimal("realrent"),rs.getBigDecimal("totalReturn"),rs.getBigDecimal("totalRent"),rs.getBigDecimal("diffAmt")});
    		}else{
    			Map<Integer,BigDecimal[]> yearOfRent  = new HashMap<Integer, BigDecimal[]>();
    			yearOfRent.put(curYear,new BigDecimal[]{rs.getBigDecimal("rent"),rs.getBigDecimal("realrent"),rs.getBigDecimal("totalReturn"),rs.getBigDecimal("totalRent"),rs.getBigDecimal("diffAmt")});
    			rentAmtMap.put(key, yearOfRent);
    		}
    	}
    	params.setInt("maxYear", maxYear);
    	params.setInt("minYear", minYear);
    	
    	params.setObject("rentAmtMap", rentAmtMap);
    	
    	return params;
    }
    
   
    
    
}