package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
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

import com.kingdee.eas.base.core.util.StringUtil;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.util.StringUtils;

public class TenancyRevenueRptFacadeControllerBean extends AbstractTenancyRevenueRptFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.TenancyRevenueRptFacadeControllerBean");

	@Override
	protected RptParams _init(Context ctx, RptParams params)
			throws BOSException, EASBizException {
		RptParams pp = new RptParams();
		return pp;
	}

	@Override
	protected RptParams _query(Context ctx, RptParams params)
			throws BOSException, EASBizException {
		String sellProject = (String) params.getObject("sellProject");
		boolean isAll = params.getBoolean("isAll");
		StringBuffer paramStrb = new StringBuffer();
		String paramStr = null;
		if(StringUtils.isEmpty(sellProject)){
			params.setObject("dataList", null);
			return params;
		}
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
		TenancyBillInfo t = (TenancyBillInfo) params.getObject("tenancy");
		
		StringBuffer str = new StringBuffer();
		str.append(" select   distinct                                                                                                                        ");
		str.append("     org.fname_l2 companyName,                                                                                                    ");
		str.append("     sp.fname_l2 sellProject,                                                                                                     ");
		str.append("     bu.fid  buildingId,                                                                                                     ");
		str.append("     pt.fname_l2 productType,                                                                                                     ");
		str.append("     pt.fid productTypeId,                                                                                                     ");
		str.append("     bu.fname_l2 buildingName,                                                                                                    ");
		str.append("     isnull(bae.fbuildarea,0) buildingArea,                                                                                                  ");
		str.append("     isnull(bae.farea,0) buildingSellArea,                                                                                       ");
		str.append("     r.fname_l2 roomName,                                                                                                         ");
		str.append("     bill.fname tenancyName,                                                                                                      ");
		str.append("     bill.ftencustomerdes custName,                                                                                               ");
		str.append("     bc.fname_l2 brandCatagory,                                                                                                   ");
		str.append("     bill.fbranddesc brand,                                                                                                       ");
		str.append("     bill.fstartdate startDate,                                                                                                   ");
		str.append("     bill.fenddate endDate,                                                                                                       ");
		str.append("     re.fbuildingarea tenancyArea,                                                                                                ");
		str.append("     datediff(dd,bill.fstartdate,bill.fenddate)+1 leaseCount,                                                                       ");
		str.append("    (select sum(datediff(dd,e.ffreeStartdate,e.ffreeenddate)) from T_TEN_RentFreeEntry e where e.ftenancyid = bill.fid) freedays, ");
		str.append("    fdealtotalRent totalAmt,                                                                                                      ");
		str.append("     case (datediff(dd,bill.fstartdate,bill.fenddate)+1) when 0 then 0 else isnull(baseline.totalRent,0)/re.fbuildingarea  end baseDayPrice,                             ");
		str.append("     case (datediff(dd,bill.fstartdate,bill.fenddate)+1) when 0 then 0 else bill.fdealtotalrent/(datediff(dd,bill.fstartdate,bill.fenddate)+1)/re.fbuildingarea end realDayPrice  ");
		str.append(" from t_ten_tenancycustomerentry ce inner join t_ten_tenancybill bill on ce.ftenancybillid = bill.fid                             ");
		str.append(" left  outer join  t_ten_customerentrybrand cb on cb.fparentid = ce.fid                                                           ");
		str.append(" left outer join t_ten_brand b on cb.fbrandid = b.fid                                                                             ");
		str.append(" left outer join t_ten_brandcategory bc on bc.fid  = b.fcategoryid                                                                ");
		str.append(" inner join t_ten_tenancyroomentry re on re.ftenancyid =bill.fid                                                                  ");
		str.append(" left outer join t_she_room r on re.froomid = r.fid                                                                               ");
		str.append(" left outer join t_she_building bu on bu.fid = r.fbuildingid                                                                      ");
		str.append(" left outer join t_fdc_producttype pt on pt.fid = bu.fproducttypeid                                                               ");
		str.append(" left outer join  (select sum(isnull(farea,0))  farea,sum(isnull(fbuildArea,0)) fbuildArea,fheadid from t_she_BuildingAreaEntry group by fheadid) bae on bae.fheadid = bu.fid                                                             ");
		str.append(" left outer join t_she_sellproject sp on sp.fid = bu.fsellprojectid                                                               ");
		str.append(" left outer join t_org_baseunit org  on org.fid = bill.forgunitid                                                                 ");
		str.append(" left outer join t_she_fdccustomer fdccust on fdccust.fid  = ce.ffdccustomerid                                                    ");
		str.append(" left outer join (select sum(pentry.fdayprice*pentry.farea) totalRent,tpentry.fparentid tenancyid                                 ");
		str.append(" 					from t_ten_tenancypriceentry  tpentry                                                                         ");
		str.append(" 					left outer join t_ten_tenpriceentry pentry on tpentry.ftenpriceid =pentry.fid                                 ");
		str.append(" 					group by tpentry.fparentid ) baseLine on baseline.tenancyid = bill.fid                                        ");
		str.append("where bill.ftenancystate in("+paramStr+" )                                                                                              ");
		if(t !=null ){
			str.append(" and  bill.fid ='"+t.getId().toString()+"'");
		}
		if(sellProject!=null&&!"".equals(sellProject)){
    		str.append(" and sp.fid in("+sellProject+")");
    	}
		str.append(" order by companyName,sellProject,productType, buildingName");
		RptRowSet dataList = executeQuery(str.toString(), null, ctx);
		System.err.println(str.toString());
		params.setObject("dataList", dataList);
		return params;
	}
    
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
  	  header.setLabels(new Object[][]{ 
	    		{
	    			"公司","项目名称","buildingId","产品类型","productTypeId","楼栋","建筑面积（㎡）","可租面积（㎡）","已租面积（㎡）","出租率（%）",
	    			"房间","合同名称","客户","品牌分类","品牌","合同起始日","合同终止日","出租面积（㎡）","租期",
	    			"免租期（按日）","合同总额","基准租金单价","实际租金单价"
		    	}
	    },true);
  	    params.setObject("header", header);
	    return params;
    }
    
    private void initColoum(RptTableHeader header,RptTableColumn col,String name,int width,boolean isHide){
    	col= new RptTableColumn(name);
    	col.setWidth(width);
	    col.setHided(isHide);
	    header.addColumn(col);
    }
    
   
}