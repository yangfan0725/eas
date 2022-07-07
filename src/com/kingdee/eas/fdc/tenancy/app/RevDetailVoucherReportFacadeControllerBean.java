package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.util.app.ContextUtil;

public class RevDetailVoucherReportFacadeControllerBean extends AbstractRevDetailVoucherReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.RevDetailVoucherReportFacadeControllerBean");
    
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
	    initColoum(header,col,"spId",100,true);
	    initColoum(header,col,"roomId",100,true);
	    initColoum(header,col,"customerId",100,true);
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
	    initColoum(header,col,"rate",100,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"mdNumber","mdId","conId","spId","roomId","customerId","������Ϣ","������Ϣ","������Ϣ","������Ϣ","������Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","��ͬ��Ϣ","�������","˰��"
	    		}
	    		,
	    		{
	    			"mdNumber","mdId","conId","spId","roomId","customerId","��Ŀ����","¥��","����","�������","�������","��ͬ����","��ͬ����","�ͻ�����","��ͬ��ʼ��","��ͬ��ֹ��","�����ڣ����գ�","��ͬ�ܶ���+�����Է��ã�","��𵥼�","��𵥼ۣ�ÿ��Ԫ/ƽ�ף�","�������","˰��"
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
	    Integer year = (Integer)params.getObject("year");
	    Integer month =   (Integer)params.getObject("month");
	    
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, 1);
		
		Date startDate=FDCDateHelper.getFirstDayOfMonth(cal.getTime());
		Date endDate=FDCDateHelper.getLastDayOfMonth(cal.getTime());
		
	    Boolean isAll=params.getBoolean("isAll");
		Boolean isZero=params.getBoolean("isZero");

    	StringBuffer sb=new StringBuffer();
    	sb.append(" select * from (select distinct t.mdNumber,t.mdId,t.conId,t.spId,t.roomId,t.customerId,t.sellProject,t.build,t.room,t.buildArea,t.tenancyArea,");
    	sb.append(" t.conNumber,t.conName,t.customer,t.startDate,t.endDate,t.freeDays,t.dealTotal,");
    	sb.append(" t.dealPrice,t.roomPrice,t.moneyDefine,t.rate from ("); 
    	sb.append(" select md.frate rate,md.fnumber mdNumber,md.fid mdId,con.fid conId,sp.fid spId,room.fid roomId,customerEntry.ffdcCustomerId customerId,sp.fname_l2 sellProject,build.fname_l2 build,con.ftenRoomsDes room,room.FBuildingArea buildArea,roomEntry.fbuildingArea tenancyArea,");
    	sb.append(" con.fnumber conNumber,con.ftenancyName conName,con.ftencustomerDes customer,con.fstartDate startDate,con.fendDate endDate,datediff(day,rent.ffreeStartDate,rent.ffreeEndDate)+1 freeDays,con.fdealTotalRent+isnull(other.amount,0) dealTotal,");
    	sb.append(" roomEntry.fdealRoomRentPrice dealPrice,roomEntry.fdealRoomRentPrice/roomEntry.fbuildingArea roomPrice,md.fname_l2 moneyDefine from T_TEN_TenancyBill con left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join T_TEN_TenancyCustomerEntry customerEntry on con.fid=customerEntry.ftenancyBillId"); 
    	sb.append(" left join t_she_room room on room.fid=roomEntry.froomId left join t_she_building build on build.fid=room.fbuildingId left join t_she_sellProject sp on sp.fid=con.fsellProjectid");
    	sb.append(" left join T_TEN_TenancyRoomPayListEntry pay on pay.ftenRoomId=roomEntry.fid left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId left join T_TEN_RentFreeEntry rent on rent.ftenancyId=con.fid");
    	sb.append(" left join (select sum(fappAmount) amount,bill.FTenancyBillId conId from T_TEN_OtherBill bill left join  T_TEN_TenBillOtherPay entry on bill.fid=entry.FOtherBillId group by bill.FTenancyBillId) other on other.conId=con.fid");
    	sb.append(" where md.fid is not null and md.fnumber  in(select fnumber from t_she_moneydefine m where m.fdescription_l2 ='����ȷ��')");
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
    	if(startDate!=null){
    		sb.append(" and con.fstartDate<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'}");
    	}
    	if(endDate!=null){
    		sb.append(" and con.fendDate>{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+ "'}");
    	}
    	sb.append(" group by md.frate,md.fnumber,md.fid,con.fid,sp.fid,room.fid,customerEntry.ffdcCustomerId,sp.fname_l2,build.fname_l2,con.ftenRoomsDes,room.FBuildingArea,roomEntry.fbuildingArea,");
    	sb.append(" con.fnumber,con.ftenancyName,con.ftencustomerDes,con.fstartDate,con.fendDate,datediff(day,rent.ffreeStartDate,rent.ffreeEndDate)+1,con.fdealTotalRent+isnull(other.amount,0),");
    	sb.append(" roomEntry.fdealRoomRentPrice,roomEntry.fdealRoomRentPrice/roomEntry.fbuildingArea,md.fname_l2");
    	if(isZero){
    		sb.append(" having sum(pay.fappAmount)>0");
    	}
    	sb.append(" union all select md.frate rate, md.fnumber mdNumber,md.fid mdId,con.fid conId,sp.fid spId,room.fid roomId,customerEntry.ffdcCustomerId customerId,sp.fname_l2 sellProject,build.fname_l2 build,con.ftenRoomsDes room,room.FBuildingArea buildArea,roomEntry.fbuildingArea tenancyArea,");
    	sb.append(" con.fnumber conNumber,con.ftenancyName conName,con.ftencustomerDes customer,con.fstartDate startDate,con.fendDate endDate,datediff(day,rent.ffreeStartDate,rent.ffreeEndDate)+1 freeDays,con.fdealTotalRent+isnull(other.amount,0) dealTotal,");
    	sb.append(" roomEntry.fdealRoomRentPrice dealPrice,roomEntry.fdealRoomRentPrice/roomEntry.fbuildingArea roomPrice,md.fname_l2 moneyDefine from T_TEN_TenancyBill con left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join T_TEN_TenancyCustomerEntry customerEntry on con.fid=customerEntry.ftenancyBillId"); 
    	sb.append(" left join t_she_room room on room.fid=roomEntry.froomId left join t_she_building build on build.fid=room.fbuildingId left join t_she_sellProject sp on sp.fid=con.fsellProjectid");
    	sb.append(" left join T_TEN_TenBillOtherPay pay on pay.fheadId=con.fid left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId left join T_TEN_RentFreeEntry rent on rent.ftenancyId=con.fid");
    	sb.append(" left join (select sum(fappAmount) amount,bill.FTenancyBillId conId from T_TEN_OtherBill bill left join  T_TEN_TenBillOtherPay entry on bill.fid=entry.FOtherBillId group by bill.FTenancyBillId) other on other.conId=con.fid");
    	sb.append(" where md.fid is not null and md.fnumber  in(select fnumber from t_she_moneydefine m where m.fdescription_l2 ='����ȷ��')");
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
    	if(startDate!=null){
    		sb.append(" and pay.fstartDate<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'}");
    	}
    	if(endDate!=null){
    		sb.append(" and pay.fendDate>{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+ "'}");
    	}
    	sb.append(" group by md.frate,md.fnumber,md.fid,con.fid,sp.fid,room.fid,customerEntry.ffdcCustomerId,sp.fname_l2,build.fname_l2,con.ftenRoomsDes,room.FBuildingArea,roomEntry.fbuildingArea,");
    	sb.append(" con.fnumber,con.ftenancyName,con.ftencustomerDes,con.fstartDate,con.fendDate,datediff(day,rent.ffreeStartDate,rent.ffreeEndDate)+1,con.fdealTotalRent+isnull(other.amount,0),");
    	sb.append(" roomEntry.fdealRoomRentPrice,roomEntry.fdealRoomRentPrice/roomEntry.fbuildingArea,md.fname_l2");
    	if(isZero){
    		sb.append(" having sum(pay.fappAmount)>0");
    	}
    	sb.append(" )t )t order by t.conNumber,mdNumber");
    	
    	RptRowSet rs = executeQuery(sb.toString(), null, ctx);
		params.setObject("rs", rs);
		
		sb=new StringBuffer();
		sb.append(" select t.mdId,t.conId,sum(t.appAmount) appAmount,t.totalFeeAmount,sDate,eDate from (");
    	sb.append(" select md.fid mdId,con.fid conId,isnull(pay.fappAmount,0) appAmount,isnull(feeEntry.totalFeeAmount,0) totalFeeAmount," );
    	
    	HashMap hmParamIn = new HashMap();
		hmParamIn.put("ISTENTOTALDAY", ContextUtil.getCurrentOrgUnit(ctx).getId().toString());
		hmParamIn.put("ISOTHERTOTALDAY", ContextUtil.getCurrentOrgUnit(ctx).getId().toString());
		HashMap hmAllParam = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(hmParamIn);
		boolean ISTENTOTALDAY=true;
		if (hmAllParam.get("ISTENTOTALDAY") != null) {
			ISTENTOTALDAY = Boolean.valueOf(
					hmAllParam.get("ISTENTOTALDAY").toString()).booleanValue();
		}
    	if(ISTENTOTALDAY){
    		sb.append(" con.fstartDate sDate,  con.fendDate eDate" );
		}else{
			sb.append(" pay.fstartDate sDate,  pay.fendDate eDate" );
		}
    	sb.append(" from T_TEN_TenancyRoomPayListEntry pay left join T_TEN_TenancyRoomEntry roomEntry on pay.ftenRoomId=roomEntry.fid left join T_TEN_TenancyBill con on con.fid=roomEntry.ftenancyId left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId");
    	sb.append(" left join T_TEN_TenancyRoomEntry roomEntry1 on con.fid=roomEntry1.ftenancyId left join t_she_room room on room.fid=roomEntry.froomId left join t_she_sellProject sp on sp.fid=con.fsellProjectid");
    	sb.append(" left join (select feeEntry.ftenancyBillId conId,feeEntry.fmoneyDefineId mdId,sum(feeEntry.fappAmount) totalFeeAmount from T_TEN_FeesWarrantEntrys feeEntry group by ftenancyBillId,fmoneyDefineId) feeEntry on feeEntry.conId=con.fid and feeEntry.mdId=md.fid");
    	sb.append(" where md.fid is not null and md.fnumber  in(select fnumber from t_she_moneydefine m where m.fdescription_l2 ='����ȷ��')");
    	if(isAll){
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying','Expiration')");
    	}else{
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying')");
    	}
    	if(isZero){
    		sb.append(" and isnull(pay.fappAmount,0)>0");
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
    	if(ISTENTOTALDAY){
    		if(startDate!=null){
        		sb.append(" and con.fstartDate<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'}");
        	}
        	if(endDate!=null){
        		sb.append(" and con.fendDate>{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+ "'}");
        	}
		}else{
			if(startDate!=null){
	    		sb.append(" and pay.fstartDate<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'}");
	    	}
	    	if(endDate!=null){
	    		sb.append(" and pay.fendDate>{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+ "'}");
	    	}
		}
    	
    	sb.append(" union all ");
    	


    	sb.append(" select  mdid,conid,appamount,(select isnull(sum(isnull(fappamount,0)),0) from T_TEN_FeesWarrantEntrys where ftenancybillid = at.conid and fmoneydefineid = at.mdid)  totalFeeAmount,sDate,eDate ");
    	sb.append(" from (select md.fid mdId,con.fid conId,ob.fid,sum(isnull(pay.fappAmount, 0)) appAmount,");
    	boolean ISOTHERTOTALDAY=true;
		if (hmAllParam.get("ISOTHERTOTALDAY") != null) {
			ISOTHERTOTALDAY = Boolean.valueOf(
					hmAllParam.get("ISOTHERTOTALDAY").toString()).booleanValue();
		}
    	if(ISOTHERTOTALDAY){
    		sb.append(" min(ob.fstartDate) sDate, max(ob.fendDate) eDate  from T_TEN_TenBillOtherPay pay ");
    	}else{
    		sb.append(" min(pay.fstartDate) sDate, max(pay.fendDate) eDate  from T_TEN_TenBillOtherPay pay ");
    	}
    	sb.append(" left outer join t_ten_otherbill ob on ob.fid = pay.fotherbillid  left join T_TEN_TenancyBill con on con.fid = pay.fheadId   left join t_she_moneyDefine md  on md.fid = pay.fmoneyDefineId and md.fdescription_l2='����ȷ��' ");
    	sb.append(" left join T_TEN_TenancyRoomEntry roomEntry on con.fid = roomEntry.ftenancyId    left join t_she_room room  on room.fid = roomEntry.froomId  left join t_she_sellProject sp  on sp.fid = con.fsellProjectid ");
    	sb.append(" where md.fid is not null");
    	
    	 
    	 
//    	  
//    	   and con.ftenancyState in ('Audited', 'Executing', 'ContinueTenancying') and sp.fid in ('d90AAAAGAywv++Ws') and con.fid in('MNZB9WNPQceyi6FcY7aQyHupHd4=')
//    	and md.fid in ('d90AAAAJy+a4sKjg') group by md.fid, con.fid,ob.fid) t
//    	
//    	
//    	
//    	select md.fid mdId,con.fid conId,sum(isnull(pay.fappAmount,0)) appAmount,isnull(feeEntry.totalFeeAmount,0) totalFeeAmount,min(pay.fstartDate) sDate, max(pay.fendDate) eDate");
//    	sb.append(" from T_TEN_TenBillOtherPay pay left join T_TEN_TenancyBill con on con.fid=pay.fheadId left join t_she_moneyDefine md on md.fid=pay.fmoneyDefineId");
//    	sb.append(" left join T_TEN_TenancyRoomEntry roomEntry on con.fid=roomEntry.ftenancyId left join t_she_room room on room.fid=roomEntry.froomId left join t_she_sellProject sp on sp.fid=con.fsellProjectid");
//    	sb.append(" left join (select feeEntry.ftenancyBillId conId,feeEntry.fmoneyDefineId mdId,sum(feeEntry.fappAmount) totalFeeAmount from T_TEN_FeesWarrantEntrys feeEntry group by ftenancyBillId,fmoneyDefineId) feeEntry on feeEntry.conId=con.fid and feeEntry.mdId=md.fid");
//    	sb.append(" where md.fid is not null and md.fnumber  in(select fnumber from t_she_moneydefine m where m.fdescription_l2 ='����ȷ��') and pay.fappAmount>0 ");
    	if(isAll){
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying','Expiration')");
    	}else{
    		sb.append(" and con.ftenancyState in('Audited','Executing','ContinueTenancying')");
    	}
    	if(isZero){
    		sb.append(" and isnull(pay.fappAmount,0)>0");
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
    	if(ISOTHERTOTALDAY){
    		if(startDate!=null){
        		sb.append(" and ob.fstartDate<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'}");
        	}
        	if(endDate!=null){
        		sb.append(" and ob.fendDate>{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+ "'}");
        	}
    	}else{
    		if(startDate!=null){
        		sb.append(" and pay.fstartDate<={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+ "'}");
        	}
        	if(endDate!=null){
        		sb.append(" and pay.fendDate>{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+ "'}");
        	}
    	}
    	sb.append(" group by md.fid, con.fid,ob.fid) at) t group by t.mdId,t.conId,t.totalFeeAmount,sDate,eDate");
//    	sb.append(" group by md.fid,con.fid,feeEntry.totalFeeAmount)t group by t.mdId,t.conId,t.totalFeeAmount,sDate,eDate");
    	RptRowSet detailrs = executeQuery(sb.toString(), null, ctx);

    	params.setObject("detailrs", detailrs);
		
		
		
		
		return params;
    }
}