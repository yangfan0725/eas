package com.kingdee.eas.fdc.tenancy.app;

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

import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class CustomerTrackReportFacadeControllerBean extends AbstractCustomerTrackReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.CustomerTrackReportFacadeControllerBean");
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
	    initColoum(header,col,"number",100,true);
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"type",70,false);
	    initColoum(header,col,"createTime",100,false);
	    initColoum(header,col,"customer",120,false);
	    initColoum(header,col,"remark",270,false);
	    initColoum(header,col,"sellProject",120,false);
	    initColoum(header,col,"levelt",75,false);
	    initColoum(header,col,"linkMan",100,false);
	    initColoum(header,col,"phone",100,false);
	    initColoum(header,col,"saleMan",100,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"number","id","类型","客户登记日期","客户名称","项目进度描述","意向项目","商机级别","客户联系人","联系方式","招商负责人"
	    		}
	    		,
	    		{
	    			"number","id","类型","客户登记日期","客户名称","项目进度描述","意向项目","商机级别","客户联系人","联系方式","招商负责人"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    public Date getMondayOfThisWeek() {
    	Calendar c = Calendar.getInstance();
    	int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
    	if (day_of_week == 0)
    		day_of_week = 7;
    	c.add(Calendar.DATE, -day_of_week + 1);
    	return c.getTime();
    }
    public Date getSundayOfThisWeek() {
    	Calendar c = Calendar.getInstance();
    	int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
    	if (day_of_week == 0)
    		day_of_week = 7;
    	c.add(Calendar.DATE, -day_of_week + 7);
    	return c.getTime();
    }
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	String sellProject = (String) params.getObject("sellProject");
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	String customer=(String) params.getObject("customer");
    	Boolean isAll=(Boolean)params.getBoolean("isAll");
    	Boolean week=(Boolean)params.getBoolean("week");
    	Boolean month=(Boolean)params.getBoolean("month");
    	UserInfo saleMan=(UserInfo)params.getObject("saleMan");
    	
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select * from (select cus.fnumber number,cus.fid id,'新增客户' type,cus.fcreateTime createTime,cus.fname_l2 customer,track.fdescription remark,sp.fname_l2 sellProject,levelt.fname_l2 levelt,link.fpersonName linkMan,link.fphone phone,saleMan.fname_l2 saleMan");
    	sb.append(" from T_SHE_FDCCustomer cus left join T_she_sellProject sp on sp.fid=cus.fprojectId left join T_SHE_CommerceChanceAssistant levelt on levelt.fid=cus.flevelId left join t_pm_user saleMan on saleMan.fid=cus.fsalesManid");
    	sb.append(" left join (select t.fheadid,t.fpersonName,t.fphone from T_SHE_LinkmanEntry t left join (select fheadid,max(fpersonName) max from T_SHE_LinkmanEntry group by fheadId) t1 on t.fheadid=t1.fheadId and t.fpersonName=t1.max where t1.fheadid is not null) link on link.fheadid=cus.fid ");
    	if(isAll){
    		sb.append(" left join T_SHE_TrackRecord track on track.fheadid=cus.fid");
    	}else{
    		sb.append(" left join (select t.fdescription,t.fheadid from T_SHE_TrackRecord t left join (select fheadid,max(FEventDate) max1,max(fcreateTime) max2 from T_SHE_TrackRecord group by fheadid) t1 on t.fheadid=t1.fheadId and t.feventDate=t1.max1 and t.fcreateTime=t1.max2 where t1.fheadid is not null)track on track.fheadid=cus.fid");
    	}
    	sb.append(" where 1=1");
    	if(week){
    		sb.append(" and cus.fcreatetime>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(getMondayOfThisWeek()))+ "'}");
			sb.append(" and cus.fcreatetime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(getSundayOfThisWeek()))+ "'}");
    	}else if(month){
    		sb.append(" and cus.fcreatetime>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(new Date())))+ "'}");
			sb.append(" and cus.fcreatetime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(new Date())))+ "'}");
    	}else{
    		if(fromDate!=null){
        		sb.append(" and cus.fcreatetime>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
    		}
    		if(toDate!=null){
    			sb.append(" and cus.fcreatetime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
    		}
    	}
    	
		if(sellProject!=null&&!"".equals(sellProject)){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
		if(customer!=null&&!"".equals(customer)){
			sb.append(" and cus.fname_l2 like '%"+customer+"%'");
		}
		if(saleMan!=null){
			sb.append(" and cus.FSalesmanID='"+saleMan.getId().toString()+"'");
		}
		sb.append(" union all select cus.fnumber number,cus.fid id,'跟进客户' type,cus.fcreateTime createTime,cus.fname_l2 customer,track.fdescription remark,sp.fname_l2 sellProject,levelt.fname_l2 levelt,link.fpersonName linkMan,link.fphone phone,saleMan.fname_l2 saleMan");
    	sb.append(" from T_SHE_FDCCustomer cus left join T_she_sellProject sp on sp.fid=cus.fprojectId left join T_SHE_CommerceChanceAssistant levelt on levelt.fid=cus.flevelId left join t_pm_user saleMan on saleMan.fid=cus.fsalesManid");
    	sb.append(" left join (select t.fheadid,t.fpersonName,t.fphone from T_SHE_LinkmanEntry t left join (select fheadid,max(fpersonName) max from T_SHE_LinkmanEntry group by fheadId) t1 on t.fheadid=t1.fheadId and t.fpersonName=t1.max where t1.fheadid is not null) link on link.fheadid=cus.fid ");
    	if(isAll){
    		sb.append(" left join T_SHE_TrackRecord track on track.fheadid=cus.fid");
    	}else{
    		sb.append(" left join (select t.fdescription,t.fheadid from T_SHE_TrackRecord t left join (select fheadid,max(FEventDate) max1,max(fcreateTime) max2 from T_SHE_TrackRecord group by fheadid) t1 on t.fheadid=t1.fheadId and t.feventDate=t1.max1 and t.fcreateTime=t1.max2 where t1.fheadid is not null");
    		
    		if(week){
        		sb.append(" and t.feventDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(getMondayOfThisWeek()))+ "'}");
    			sb.append(" and t.feventDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(getSundayOfThisWeek()))+ "'}");
        	}else if(month){
        		sb.append(" and t.feventDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(new Date())))+ "'}");
    			sb.append(" and t.feventDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(new Date())))+ "'}");
        	}else{
        		if(fromDate!=null){
            		sb.append(" and t.feventDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
        		}
        		if(toDate!=null){
        			sb.append(" and t.feventDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
        		}
        	}
    		
    		sb.append(")track on track.fheadid=cus.fid");
    	}
    	sb.append(" where 1=1");
    	if(isAll){
    		if(week){
        		sb.append(" and track.feventDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(getMondayOfThisWeek()))+ "'}");
    			sb.append(" and track.feventDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(getSundayOfThisWeek()))+ "'}");
        	}else if(month){
        		sb.append(" and track.feventDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(new Date())))+ "'}");
    			sb.append(" and track.feventDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(FDCDateHelper.getLastDayOfMonth(new Date())))+ "'}");
        	}else{
        		if(fromDate!=null){
            		sb.append(" and track.feventDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
        		}
        		if(toDate!=null){
        			sb.append(" and track.feventDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
        		}
        	}
    	}
    	sb.append(" and track.fheadid is not null");
//    	if(week){
//    		sb.append(" and cus.fcreatetime<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(getMondayOfThisWeek()))+ "'}");
//    	}else if(month){
//    		sb.append(" and cus.fcreatetime<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(FDCDateHelper.getFirstDayOfMonth(new Date())))+ "'}");
//    	}else{
//        	if(fromDate!=null){
//        		sb.append(" and cus.fcreatetime<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
//    		}
//    	}
    	
    	if(sellProject!=null&&!"".equals(sellProject)){
    		sb.append(" and sp.fid in("+sellProject+")");
    	}else{
    		sb.append(" and sp.fid in('null')");
    	}
    	if(customer!=null&&!"".equals(customer)){
			sb.append(" and cus.fname_l2 like '%"+customer+"%'");
		}
    	if(saleMan!=null){
			sb.append(" and cus.FSalesmanID='"+saleMan.getId().toString()+"'");
		}
    	sb.append(" ) t order by t.type,t.number,t.createTime");
    	RptRowSet rs = executeQuery(sb.toString(), null, ctx);
		params.setObject("rs", rs);
		return params;
    }
    
}