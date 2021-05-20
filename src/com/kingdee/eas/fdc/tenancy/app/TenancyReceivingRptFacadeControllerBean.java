package com.kingdee.eas.fdc.tenancy.app;

import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.util.StringUtils;

public class TenancyReceivingRptFacadeControllerBean extends AbstractTenancyReceivingRptFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.TenancyReceivingRptFacadeControllerBean");

	@Override
	protected RptParams _createTempTable(Context ctx, RptParams params)
			throws BOSException, EASBizException {
		RptTableHeader header = new RptTableHeader();
  	    RptTableColumn col = null;
  	    initColoum(header,col,"companyName",100,false);
  	    initColoum(header,col,"sellProject",100,false);
  	    initColoum(header,col,"buildingId",100,true);
  	    initColoum(header,col,"productType",100,false);
  	    initColoum(header,col,"productTypeId",100,true);
  	    initColoum(header,col,"buildingName",100,false);
  	    initColoum(header,col,"buildingArea",100,false);
  	    initColoum(header,col,"buildingSellArea",100,false);
  	    initColoum(header,col,"alreayTenArea",100,false);
  	    initColoum(header,col,"tenancyRate",100,false);
  	    
  	    initColoum(header,col,"roomName",300,false);
  	    initColoum(header,col,"tenancyName",300,false);
  	    initColoum(header,col,"custName",100,false);
  	    initColoum(header,col,"brandCatagory",100,false);
  	    initColoum(header,col,"brand",100,false);
  	    initColoum(header,col,"startDate",100,false);
  	    initColoum(header,col,"endDate",100,false);
  	    initColoum(header,col,"tenancyArea",80,false);
  	    initColoum(header,col,"leaseCount",80,false);
  	    
  	    initColoum(header,col,"freedays",100,false);
  	    initColoum(header,col,"totalAmt",100,false);
  	    initColoum(header,col,"baseDayPrice",100,false);
  	    initColoum(header,col,"realDayPrice",100,false);
  	    
  	    initColoum(header,col,"revAmt",100,false);
  	    initColoum(header,col,"revDate",100,false);
  	    initColoum(header,col,"apAmt",100,true);
  	    initColoum(header,col,"revStartDate",100,false);
  	    initColoum(header,col,"revEndDate",100,false);
  	    initColoum(header,col,"leaseTime",100,false);
  	   header.setLabels(new Object[][]{ 
	    		{
	    			"公司","项目名称","buildingId","产品类型","productTypeId","楼栋","建筑面积（㎡）","可租面积（㎡）","已租面积（㎡）","出租率（%）",
	    			"房间","合同名称","客户","品牌分类","品牌","合同起始日","合同终止日","出租面积（㎡）","租期",
	    			"免租期（按日）","合同总额","基准租金单价","实际租金单价",
	    			"实收金额","实收日期","应收金额","开始日期","结束日期","收租周期（月）"
		    	}
	    },true);
  	    params.setObject("header", header);
	    return params;
	}

	@Override
	protected RptParams _init(Context ctx, RptParams params)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return super._init(ctx, params);
	}

	@Override
	protected RptParams _query(Context ctx, RptParams params)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		Date startDate = (Date) params.getObject("startDate");
		Date endDate = (Date) params.getObject("endDate");
		String sellProject = params.getString("sellProject");
		if(StringUtils.isEmpty(sellProject)){
			params.setObject("dataList", null);
			return params;
		}
		boolean isAll = params.getBoolean("isAll");
		StringBuffer paramStrb = new StringBuffer();
		String paramStr = null;
		
		if(isAll){
			paramStrb.append("'Executing','QuitTenancying','Expiration',");
		}else{
			boolean isExecuting = params.getBoolean("isExecuting");
			boolean isQuitTenancy = params.getBoolean("isQuitTenancy");
			boolean isExpiration = params.getBoolean("isExpiration");
			if(isExecuting) {
				paramStrb.append("'Executing'");
				paramStrb.append(",");
			}
			if(isQuitTenancy) {
				paramStrb.append("'QuitTenancying'");
				paramStrb.append(",");
			}
			if(isExpiration) {
				paramStrb.append("'Expiration'");
				paramStrb.append(",");
			}
			
		}
		
		if(paramStrb.length()==0){
			paramStr ="'Executing'";
		}else{
			paramStr = paramStrb.substring(0,paramStrb.length()-1);
		}
		
		
		StringBuffer str = new StringBuffer();
		str.append("select distinct org.fname_l2 companyName,                                                                                                               \n");
		str.append("       sp.fname_l2 sellProject,                                                                                                                \n");
		str.append("       bu.fid buildingId,                                                                                                                \n");
		str.append("       pt.fname_l2 productType,                                                                                                                \n");
		str.append("       pt.fid productTypeId,                                                                                                                \n");
		str.append("       bu.fname_l2 buildingName,                                                                                                               \n");
		str.append("       isnull(bae.fbuildarea, 0) buildingArea,                                                                                                             \n");
		str.append("       isnull(bae.farea, 0) buildingSellArea,                                                                                                 \n");
		str.append("       r.fname_l2 roomName,                                                                                                                    \n");
		str.append("       bill.fname tenancyName,                                                                                                                 \n");
		str.append("       bill.ftencustomerdes custName,                                                                                                          \n");
		str.append("       bc.fname_l2 brandCatagory,                                                                                                              \n");
		str.append("       bill.fbranddesc brand,                                                                                                                  \n");
		str.append("       bill.fstartdate startDate,                                                                                                              \n");
		str.append("       bill.fenddate endDate,                                                                                                                  \n");
		str.append("       re.fbuildingarea tenancyArea,                                                                                                           \n");
		str.append("       datediff(dd, bill.fstartdate, bill.fenddate)+1 leaseCount,                                                                                \n");
		str.append("       (select sum(datediff(dd, e.ffreeStartdate, e.ffreeenddate)+1)                                                                             \n");
		str.append("        from T_TEN_RentFreeEntry e                                                                                                             \n");
		str.append("       where e.ftenancyid = bill.fid) freedays,                                                                                                \n");
		str.append("       fdealtotalRent totalAmt,                                                                                                                \n");
		str.append("        case (datediff(dd, bill.fstartdate, bill.fenddate)+1)                                                                                      \n");
		str.append("         when 0 then  0                                                                                                                        \n");
		str.append("       else isnull(baseline.totalRent, 0) / re.fbuildingarea end baseDayPrice,                                              \n");
		str.append("       case (datediff(dd, bill.fstartdate, bill.fenddate)+1)                                                                                       \n");
		str.append("         when 0 then  0                                                                                                                        \n");
		str.append("         else                                                                                                                                  \n");
		str.append("          bill.fdealtotalrent / (datediff(dd, bill.fstartdate, bill.fenddate)+1) /  re.fbuildingarea                                               \n");
		str.append("       end  realDayPrice,                                                                                                                      \n");
		str.append("       rev.revAmt revAmt,                                                                                                                             \n");
		str.append("       rev.revDate revDate,                                                                                                                            \n");
		str.append("       ap.apAmount apAmt,                                                                                                                            \n");
		str.append("       rev.startDate revStartDate,                                                                                                                          \n");
		str.append("       rev.endDate revEndDate,                                                                                                                            \n");
		str.append("       bill.fleasetime  leaseTime                                                                                                                        \n");
		str.append("  from t_ten_tenancycustomerentry ce                                                                                                           \n");
		str.append(" inner join t_ten_tenancybill bill                                                                                                             \n");
		str.append("    on ce.ftenancybillid = bill.fid                                                                                                            \n");
		str.append("  left outer join t_ten_customerentrybrand cb                                                                                                  \n");
		str.append("    on cb.fparentid = ce.fid                                                                                                                   \n");
		str.append("  left outer join t_ten_brand b                                                                                                                \n");
		str.append("    on cb.fbrandid = b.fid                                                                                                                     \n");
		str.append("  left outer join t_ten_brandcategory bc                                                                                                       \n");
		str.append("    on bc.fid = b.fcategoryid                                                                                                                  \n");
		str.append(" inner join t_ten_tenancyroomentry re                                                                                                          \n");
		str.append("    on re.ftenancyid = bill.fid                                                                                                                \n");
		str.append("  left outer join t_she_room r                                                                                                                 \n");
		str.append("    on re.froomid = r.fid                                                                                                                      \n");
		str.append("  left outer join t_she_building bu                                                                                                            \n");
		str.append("    on bu.fid = r.fbuildingid                                                                                                                  \n");
		str.append("  left outer join t_fdc_producttype pt                                                                                                         \n");
		str.append("    on pt.fid = bu.fproducttypeid                                                                                                              \n");
		str.append("  left outer join (select sum(isnull(farea,0))  farea,sum(isnull(fbuildArea,0)) fbuildArea,fheadid from t_she_BuildingAreaEntry group by fheadid) bae     \n");
		str.append("    on bae.fheadid = bu.fid                                                                                                                    \n");
		str.append("  left outer join t_she_sellproject sp                                                                                                         \n");
		str.append("    on sp.fid = bu.fsellprojectid                                                                                                              \n");
		str.append("  left outer join t_org_baseunit org                                                                                                           \n");
		str.append("    on org.fid = bill.forgunitid                                                                                                               \n");
		str.append("  left outer join t_she_fdccustomer fdccust                                                                                                    \n");
		str.append("    on fdccust.fid = ce.ffdccustomerid                                                                                                         \n");
		str.append("  left outer join (select sum(pentry.fdayprice * pentry.farea) totalRent,                                                                      \n");
		str.append("                          tpentry.fparentid tenancyid                                                                                          \n");
		str.append("                     from t_ten_tenancypriceentry tpentry                                                                                      \n");
		str.append("                     left outer join t_ten_tenpriceentry pentry                                                                                \n");
		str.append("                       on tpentry.ftenpriceid = pentry.fid                                                                                     \n");
		str.append("                    group by tpentry.fparentid) baseLine                                                                                       \n");
		str.append("    on baseline.tenancyid = bill.fid                                                                                                           \n");
		str.append("  left outer join (select ftenancyobjid,min(fstartDate) startDate,max(fendDate) endDate,sum(frevamount) revAmt,max(fcreatetime) revDate from ( \n");
		str.append("                    select bill.ftenancyobjid,                                                                                                 \n");
		str.append("                          pentry.fstartdate,                                                                                                   \n");
		str.append("                          pentry.fenddate,                                                                                                     \n");
		str.append("                          bill.fcreatetime,                                                                                                    \n");
		str.append("                          entry.frevamount,                                                                                                    \n");
		str.append("                          pentry.fappamount,                                                                                                   \n");
		str.append("                          entry.frevlisttype                                                                                                   \n");
		str.append("                     from T_BDC_FDCReceivingBillEntry entry                                                                                    \n");
		str.append("                    inner join T_BDC_FDCReceivingBill bill                                                                                     \n");
		str.append("                       on bill.fid = entry.fheadid                                                                                             \n");
		str.append("                    inner join t_Ten_Tenancyroompaylistentry pentry                                                                            \n");
		str.append("                       on pentry.fid = entry.frevlistid                                                                                        \n");
		str.append("                    where frevbiztype = 'tenancy' and bill.fstate ='4AUDITTED'                                                                                             \n");
		str.append("                      and entry.frevlisttype = 'tenRoomRev' and bill.fcreatetime>={ts'"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"'}" +
				" and bill.fcreatetime<={ts'"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"'}\n");
		str.append("                   union all                                                                                                                   \n");
		str.append("                   select bill.ftenancyobjid,                                                                                                  \n");
		str.append("                          pentry.fstartdate,                                                                                                   \n");
		str.append("                          pentry.fenddate,                                                                                                     \n");
		str.append("                          bill.fcreatetime,                                                                                                    \n");
		str.append("                          entry.frevamount,                                                                                                    \n");
		str.append("                          pentry.fappamount,                                                                                                   \n");
		str.append("                          entry.frevlisttype                                                                                                   \n");
		str.append("                     from T_BDC_FDCReceivingBillEntry entry                                                                                    \n");
		str.append("                    inner join T_BDC_FDCReceivingBill bill                                                                                     \n");
		str.append("                       on bill.fid = entry.fheadid                                                                                             \n");
		str.append("                    inner join t_Ten_Tenbillotherpay pentry                                                                                    \n");
		str.append("                       on pentry.fid = entry.frevlistid                                                                                        \n");
		str.append("                    where frevbiztype = 'tenancy'                                                                                              \n");
		str.append("                      and entry.frevlisttype = 'tenOtherRev' and bill.fstate ='4AUDITTED'   and bill.fcreatetime>={ts'"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"'} " +
				                          " and bill.fcreatetime<={ts'"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"'}\n" );
		str.append("                   ) group by ftenancyobjid) rev  on rev.ftenancyobjid = bill.fid                                                              \n");
		str.append("  left outer  join (select tenancy,                                                                                                             \n");
		str.append("                          sum(fappamount) apAmount FROM(                                                                                       \n");
		str.append("                           select re.ftenancyid tenancy, pentry.fappamount                                                                     \n");
		str.append("                             from t_Ten_Tenancyroompaylistentry pentry                                                                         \n");
		str.append("                            inner join t_ten_tenancyroomentry re                                                                               \n");
		str.append("                               on re.fid = pentry.ftenroomid                                                                                   \n");
		str.append("                            where  pentry.fstartDate>={ts'"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"'} " +
				" and pentry.fstartDate<={ts'"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"'}" +
				" and pentry.fendDate>={ts'"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"'}" +
				" and pentry.fendDate<={ts'"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"'}\n");
		str.append("                           union all                                                                                                           \n");
		str.append("                           select o.ftenancybillid tenancy,                                                                                    \n");
		str.append("                                  oentry.fappamount                                                                                            \n");
		str.append("                             from t_Ten_Tenbillotherpay oentry                                                                                 \n");
		str.append("                            inner join t_ten_otherbill o                                                                                       \n");
		str.append("                               on o.fid = oentry.fotherbillid                                                                                  \n");
		str.append("                      where   oentry.fstartDate>={ts'"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"'} " +
				" and oentry.fstartDate<={ts'"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"'}" +
				" and oentry.fendDate>={ts'"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"'}" +
				" and oentry.fendDate<={ts'"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"'}\n");
		str.append("                   )                                                                                                                           \n");
		str.append("                group by tenancy) ap                                                                                                           \n");
		str.append("    on ap.tenancy = bill.fid                                                                                                                   \n");
		str.append(" where bill.ftenancystate in("+paramStr+")                                                                                          \n");
        if(!StringUtils.isEmpty(sellProject)){
        	str.append(" and sp.fid in("+sellProject+")\n");
        }
        str.append(" order by companyName,sellProject,productType, buildingName");
        System.err.println(str.toString());
        RptRowSet dataList = executeQuery(str.toString(), null, ctx);
		params.setObject("dataList", dataList);
		return params;
	}
    
	
	
	 private void initColoum(RptTableHeader header,RptTableColumn col,String name,int width,boolean isHide){
	    	col= new RptTableColumn(name);
	    	col.setWidth(width);
		    col.setHided(isHide);
		    header.addColumn(col);
	    }
    
}