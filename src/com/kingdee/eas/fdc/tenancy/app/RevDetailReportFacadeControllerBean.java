package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

import com.kingdee.bos.*;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class RevDetailReportFacadeControllerBean extends AbstractRevDetailReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.RevDetailReportFacadeControllerBean");
    
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
	    initColoum(header,col,"mdNumber",100,true);
	    initColoum(header,col,"mdId",100,true);
	    initColoum(header,col,"conId",100,true);
	    initColoum(header,col,"quitRoomDate",100,true);
	    initColoum(header,col,"sellProject",100,false);
	    initColoum(header,col,"build",50,false);
	    initColoum(header,col,"room",270,false);
	    initColoum(header,col,"buildArea",75,false);
	    initColoum(header,col,"tenancyArea",75,false);
	    initColoum(header,col,"conNumber",100,false);
	    initColoum(header,col,"conName",200,false);
	    initColoum(header,col,"customer",200,false);
	    initColoum(header,col,"startDate",75,false);
	    initColoum(header,col,"endDate",75,false);
	    initColoum(header,col,"freeDays",80,false);
	    initColoum(header,col,"dealTotal",80,false);
	    initColoum(header,col,"dealPrice",80,false);
	    initColoum(header,col,"roomPrice",80,false);
	    initColoum(header,col,"moneyDefine",100,false);
	    initColoum(header,col,"appAmount",80,false);
	    initColoum(header,col,"invoiceAmount",80,false);
	    initColoum(header,col,"actRevAmount",80,false);
	    initColoum(header,col,"unRevAmount",80,false);
	    initColoum(header,col,"overdueDays",80,false);
	    initColoum(header,col,"accountRate",75,false);
	    initColoum(header,col,"remainingDays",80,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"mdNumber","mdId","conId","quitRoomDate","������Ϣ","������Ϣ","������Ϣ","������Ϣ","������Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","�������","�ۼƽ��","�ۼƽ��","�ۼƽ��","�ۼƽ��","�ۼƽ��","�ۼƽ��","��ͬʣ������"
	    		}
	    		,
	    		{
	    			"mdNumber","mdId","conId","quitRoomDate","��Ŀ����","¥��","����","�������","�������","��ͬ����","��ͬ����","�ͻ�����","��ͬ��ʼ��","��ͬ��ֹ��","�����ڣ����գ�","��ͬ�ܶ���+�����Է��ã�","��𵥼�","��𵥼ۣ�ÿ��Ԫ/ƽ�ף�","�������","Ӧ�ս��","Ʊ�ݽ��","ʵ�ս��","δ�ս��","��������","�˿������","��ͬʣ������"
		    	}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	String sellProject = (String) params.getObject("sellProject");
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
		
		Date toODDate=(Date)params.getObject("toODDate");
		String toODDateStr=FDCDateHelper.formatDate2(toODDate);
		
	    Boolean isAll=params.getBoolean("isAll");
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select * from (select distinct t.mdNumber,t.mdId,t.conId,t.quitRoomDate,t.sellProject,t.build,t.room,t.buildArea,t.tenancyArea,");
    	sb.append(" t.conNumber,t.conName,t.customer,t.startDate,t.endDate,t.freeDays,t.dealTotal,");
    	sb.append(" t.dealPrice,t.roomPrice,t.moneyDefine from ("); 
    	sb.append(" select md.fnumber mdNumber,md.fid mdId,con.fid conId,con.fquitRoomDate quitRoomDate,sp.fname_l2 sellProject,build.fname_l2 build,con.ftenRoomsDes room,room.FBuildingArea buildArea,roomEntry.fbuildingArea tenancyArea,");
    	sb.append(" con.fnumber conNumber,con.ftenancyName conName,con.ftencustomerDes customer,con.fstartDate startDate,con.fendDate endDate,datediff(day,rent.ffreeStartDate,rent.ffreeEndDate)+1 freeDays,con.fdealTotalRent+isnull(other.amount,0) dealTotal,");
    	sb.append(" roomEntry.fdealRoomRentPrice dealPrice,roomEntry.fdealRoomRentPrice/roomEntry.fbuildingArea roomPrice,md.fname_l2 moneyDefine from T_TEN_TenancyBill con left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join T_TEN_TenancyCustomerEntry customerEntry on con.fid=customerEntry.ftenancyBillId"); 
    	sb.append(" left join t_she_room room on room.fid=roomEntry.froomId left join t_she_building build on build.fid=room.fbuildingId left join t_she_sellProject sp on sp.fid=con.fsellProjectid");
    	sb.append(" left join T_TEN_TenancyRoomPayListEntry pay on pay.ftenRoomId=roomEntry.fid left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId left join T_TEN_RentFreeEntry rent on rent.ftenancyId=con.fid");
    	sb.append(" left join (select sum(fappAmount) amount,bill.FTenancyBillId conId from T_TEN_OtherBill bill left join  T_TEN_TenBillOtherPay entry on bill.fid=entry.FOtherBillId group by bill.FTenancyBillId) other on other.conId=con.fid");
    	sb.append(" where md.fid is not null");
    	if(isAll){
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying','Expiration')");
    	}else{
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying')");
    	}
    	if(sellProject!=null&&!"".equals(sellProject)){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	if(room!=null&&!"".equals(room.toString())){
    		sb.append(" and room.fid in("+room+")");
    	}
    	if(tenancyBill!=null&&!"".equals(tenancyBill.toString())){
    		sb.append(" and con.fid in("+tenancyBill+")");
    	}
    	if(customer!=null&&!"".equals(customer.toString())){
    		sb.append(" and EXISTS(select ftenancyBillId from T_TEN_TenancyCustomerEntry where ffdccustomerid in("+customer+") and con.fid=ftenancyBillId)");
    	}
    	if(moneyDefine!=null&&!"".equals(moneyDefine.toString())){
    		sb.append(" and md.fid in("+moneyDefine+")");
    	}
    	if(fromDate!=null){
    		sb.append(" and pay.fappDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and pay.fappDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	sb.append(" union all select md.fnumber mdNumber,md.fid mdId,con.fid conId,con.fquitRoomDate quitRoomDate,sp.fname_l2 sellProject,build.fname_l2 build,con.ftenRoomsDes room,room.FBuildingArea buildArea,roomEntry.fbuildingArea tenancyArea,");
    	sb.append(" con.fnumber conNumber,con.ftenancyName conName,con.ftencustomerDes customer,con.fstartDate startDate,con.fendDate endDate,datediff(day,rent.ffreeStartDate,rent.ffreeEndDate)+1 freeDays,con.fdealTotalRent+isnull(other.amount,0) dealTotal,");
    	sb.append(" roomEntry.fdealRoomRentPrice dealPrice,roomEntry.fdealRoomRentPrice/roomEntry.fbuildingArea roomPrice,md.fname_l2 moneyDefine from T_TEN_TenancyBill con left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join T_TEN_TenancyCustomerEntry customerEntry on con.fid=customerEntry.ftenancyBillId"); 
    	sb.append(" left join t_she_room room on room.fid=roomEntry.froomId left join t_she_building build on build.fid=room.fbuildingId left join t_she_sellProject sp on sp.fid=con.fsellProjectid");
    	sb.append(" left join T_TEN_TenBillOtherPay pay on pay.fheadId=con.fid left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId left join T_TEN_RentFreeEntry rent on rent.ftenancyId=con.fid");
    	sb.append(" left join (select sum(fappAmount) amount,bill.FTenancyBillId conId from T_TEN_OtherBill bill left join  T_TEN_TenBillOtherPay entry on bill.fid=entry.FOtherBillId group by bill.FTenancyBillId) other on other.conId=con.fid");
    	sb.append(" where md.fid is not null");
    	if(isAll){
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying','Expiration')");
    	}else{
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying')");
    	}
    	if(sellProject!=null&&!"".equals(sellProject)){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	if(room!=null&&!"".equals(room.toString())){
    		sb.append(" and room.fid in("+room+")");
    	}
    	if(tenancyBill!=null&&!"".equals(tenancyBill.toString())){
    		sb.append(" and con.fid in("+tenancyBill+")");
    	}
    	if(customer!=null&&!"".equals(customer.toString())){
    		sb.append(" and EXISTS(select ftenancyBillId from T_TEN_TenancyCustomerEntry where ffdccustomerid in("+customer+") and con.fid=ftenancyBillId)");
    	}
    	if(moneyDefine!=null&&!"".equals(moneyDefine.toString())){
    		sb.append(" and md.fid in("+moneyDefine+")");
    	}
    	if(fromDate!=null){
    		sb.append(" and pay.fappDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and pay.fappDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	sb.append(" )t )t order by t.conNumber,t.mdNumber");
    	
    	RptRowSet rs = executeQuery(sb.toString(), null, ctx);
		params.setObject("rs", rs);
		
		sb=new StringBuffer();
    	sb.append(" select md.fid mdId,con.fid conId,pay.fappDate appDate,year(pay.fappDate) appYear,month(pay.fappDate) appMonth,isnull(pay.fappAmount,0) appAmount,isnull(pay.finvoiceAmount,0) invoiceAmount,isnull(pay.factRevAmount,0)-isnull(pay.fhasrefundmentamount,0) actRevAmount,");
    	sb.append(" (case when pay.fappAmount=isnull(pay.factRevAmount,0) or datediff(day,pay.fappDate,convert(DATETIME,'"+toODDateStr+"'))<0 then 0 else datediff(day,pay.fappDate,convert(DATETIME,'"+toODDateStr+"')) end) overdueDays from T_TEN_TenancyRoomPayListEntry pay left join T_TEN_TenancyRoomEntry roomEntry on pay.ftenRoomId=roomEntry.fid left join T_TEN_TenancyBill con on con.fid=roomEntry.ftenancyId left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId");
    	sb.append(" left join T_TEN_TenancyRoomEntry roomEntry1 on con.fid=roomEntry1.ftenancyId left join t_she_room room on room.fid=roomEntry.froomId left join t_she_sellProject sp on sp.fid=con.fsellProjectid where 1=1");
    	if(isAll){
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying','Expiration')");
    	}else{
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying')");
    	}
    	if(sellProject!=null&&!"".equals(sellProject)){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	if(room!=null&&!"".equals(room.toString())){
    		sb.append(" and room.fid in("+room+")");
    	}
    	if(tenancyBill!=null&&!"".equals(tenancyBill.toString())){
    		sb.append(" and con.fid in("+tenancyBill+")");
    	}
    	if(customer!=null&&!"".equals(customer.toString())){
    		sb.append(" and EXISTS(select ftenancyBillId from T_TEN_TenancyCustomerEntry where ffdccustomerid in("+customer+") and con.fid=ftenancyBillId)");
    	}
    	if(moneyDefine!=null&&!"".equals(moneyDefine.toString())){
    		sb.append(" and md.fid in("+moneyDefine+")");
    	}
    	if(fromDate!=null){
    		sb.append(" and pay.fappDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and pay.fappDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	sb.append(" union all select md.fid mdId,con.fid conId,pay.fappDate appDate,year(pay.fappDate) appYear,month(pay.fappDate) appMonth,isnull(pay.fappAmount,0) appAmount,isnull(pay.finvoiceAmount,0) invoiceAmount,isnull(pay.factRevAmount,0)-isnull(pay.fhasrefundmentamount,0) actRevAmount,");
    	sb.append(" (case when pay.fappAmount=isnull(pay.factRevAmount,0) or datediff(day,pay.fappDate,convert(DATETIME,'"+toODDateStr+"'))<0 then 0 else datediff(day,pay.fappDate,convert(DATETIME,'"+toODDateStr+"')) end) overdueDays from T_TEN_TenBillOtherPay pay left join T_TEN_TenancyBill con on con.fid=pay.fheadId left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId");
    	sb.append(" left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join t_she_room room on room.fid=roomEntry.froomId left join t_she_sellProject sp on sp.fid=con.fsellProjectid where 1=1");
    	if(isAll){
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying','Expiration')");
    	}else{
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying')");
    	}
    	if(sellProject!=null&&!"".equals(sellProject)){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	if(room!=null&&!"".equals(room.toString())){
    		sb.append(" and room.fid in("+room+")");
    	}
    	if(tenancyBill!=null&&!"".equals(tenancyBill.toString())){
    		sb.append(" and con.fid in("+tenancyBill+")");
    	}
    	if(customer!=null&&!"".equals(customer.toString())){
    		sb.append(" and EXISTS(select ftenancyBillId from T_TEN_TenancyCustomerEntry where ffdccustomerid in("+customer+") and con.fid=ftenancyBillId)");
    	}
    	if(moneyDefine!=null&&!"".equals(moneyDefine.toString())){
    		sb.append(" and md.fid in("+moneyDefine+")");
    	}
    	if(fromDate!=null){
    		sb.append(" and pay.fappDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and pay.fappDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	RptRowSet detailrs = executeQuery(sb.toString(), null, ctx);
		params.setObject("detailrs", detailrs);
		
		
		sb=new StringBuffer();
    	sb.append(" select max(fappDate) maxAppDate,min(fappDate) minAppDate from(select pay.fappDate");
    	sb.append(" from T_TEN_TenancyRoomPayListEntry pay left join T_TEN_TenancyRoomEntry roomEntry on pay.ftenRoomId=roomEntry.fid left join T_TEN_TenancyBill con on con.fid=roomEntry.ftenancyId left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId");
    	sb.append(" left join T_TEN_TenancyRoomEntry roomEntry1 on con.fid=roomEntry1.ftenancyId left join t_she_room room on room.fid=roomEntry.froomId left join t_she_sellProject sp on sp.fid=con.fsellProjectid where 1=1");
    	if(isAll){
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying','Expiration')");
    	}else{
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying')");
    	}
    	if(sellProject!=null&&!"".equals(sellProject)){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	if(room!=null&&!"".equals(room.toString())){
    		sb.append(" and room.fid in("+room+")");
    	}
    	if(tenancyBill!=null&&!"".equals(tenancyBill.toString())){
    		sb.append(" and con.fid in("+tenancyBill+")");
    	}
    	if(customer!=null&&!"".equals(customer.toString())){
    		sb.append(" and EXISTS(select ftenancyBillId from T_TEN_TenancyCustomerEntry where ffdccustomerid in("+customer+") and con.fid=ftenancyBillId)");
    	}
    	if(moneyDefine!=null&&!"".equals(moneyDefine.toString())){
    		sb.append(" and md.fid in("+moneyDefine+")");
    	}
    	if(fromDate!=null){
    		sb.append(" and pay.fappDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and pay.fappDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	sb.append(" union all select pay.fappDate");
    	sb.append(" from T_TEN_TenBillOtherPay pay left join T_TEN_TenancyBill con on con.fid=pay.fheadId left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId");
    	sb.append(" left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join t_she_room room on room.fid=roomEntry.froomId left join t_she_sellProject sp on sp.fid=con.fsellProjectid where 1=1");
    	if(isAll){
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying','Expiration')");
    	}else{
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying')");
    	}
    	if(sellProject!=null&&!"".equals(sellProject)){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	if(room!=null&&!"".equals(room.toString())){
    		sb.append(" and room.fid in("+room+")");
    	}
    	if(tenancyBill!=null&&!"".equals(tenancyBill.toString())){
    		sb.append(" and con.fid in("+tenancyBill+")");
    	}
    	if(customer!=null&&!"".equals(customer.toString())){
    		sb.append(" and EXISTS(select ftenancyBillId from T_TEN_TenancyCustomerEntry where ffdccustomerid in("+customer+") and con.fid=ftenancyBillId)");
    	}
    	if(moneyDefine!=null&&!"".equals(moneyDefine.toString())){
    		sb.append(" and md.fid in("+moneyDefine+")");
    	}
    	if(fromDate!=null){
    		sb.append(" and pay.fappDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and pay.fappDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	sb.append(" )");
    	RptRowSet appdaters = executeQuery(sb.toString(), null, ctx);
		params.setObject("appdaters", appdaters);
		
		return params;
    }
}