package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillFactory;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.jdbc.rowset.IRowSet;

public class CommissionSettlementBillControllerBean extends AbstractCommissionSettlementBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.CommissionSettlementBillControllerBean");
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    @Override
    protected Map _calcMgrBonus(Context ctx, Map paramMap) throws BOSException,
    		EASBizException {
       Map resultMap = new HashMap();	
    	
       String sellProjectId=paramMap.get("sellProject").toString();
       int year = Integer.parseInt(paramMap.get("year")+"");
       int month = Integer.parseInt(paramMap.get("month")+"");
       
       Date t=FDCDateHelper.stringToDate(year+"-"+month+"-01");
	    Date startDate = FDCDateHelper.getFirstDayOfMonth(t);
   	Date endDate =  FDCDateHelper.getLastDayOfMonth(t);
       
       
       FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
       StringBuffer sql = new StringBuffer();
       //获取签约金额
       sql.append("\n  /*dialect*/ select  productTypeId,  productTypeName, sum(amount) over (partition by orgId ) contractAmt, amount calcContractAmt  ");
       sql.append("\n   from ( select                                                                                                            ");
       sql.append("\n         sign.fsellProjectid orgId,                                                                                               ");
       sql.append("\n         sum(sign.fdealTotalAmount) amount,                                                               ");
       sql.append("\n             pt.fid productTypeId,                                                                                    ");
       sql.append("\n             pt.fname_l2 productTypeName                                                                              ");
       sql.append("\n    from t_she_signManage sign                                                                                        ");
       sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
       sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
       sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
       sql.append("\n   where sign.fbizState in ('ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit') ");
       sql.append("\n     and NOT EXISTS                                                                                                   ");
       sql.append("\n   (select tt.fnewId                                                                                                  ");
       sql.append("\n            from t_she_changeManage tt                                                                                ");
       sql.append("\n           where tt.fstate in ('2SUBMITTED', '3AUDITTING')                                                          ");
       sql.append("\n             and sign.fid = tt.fnewId)                                                                                ");
       sql.append("         and  sign.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
       sql.append("        and  sign.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
       sql.append("\n     and sign.fsellprojectid  ='"+sellProjectId+"'                                                                       ");
       sql.append("\n   group by sign.fsellProjectid,pt.fid,pt.fname_l2                                              ");
       
//       sql.append("\n   union select                                                                                                            ");
//       sql.append("\n         change.fsellProjectid orgId,                                                                                               ");
//       sql.append("\n         0-sum(sign.fdealTotalAmount) amount,                                                               ");
//       sql.append("\n             pt.fid productTypeId,                                                                                    ");
//       sql.append("\n             pt.fname_l2 productTypeName                                                                              ");
//       sql.append("\n    from t_she_changeManage change                                                                                        ");
//       sql.append("\n       left outer join t_she_signManage sign  on sign.fid = change.fsrcId                                                          ");
//       sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
//       sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
//       sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
//       sql.append("\n   where change.fstate in ('4AUDITTED') and change.FBizType='quitRoom'    ");
//       sql.append("         and  change.fchangeDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//       sql.append("        and  change.fchangeDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//       sql.append("\n     and change.fsellprojectid  ='"+sellProjectId+"'                                                                       ");
//       sql.append("\n   group by change.fsellProjectid,pt.fid,pt.fname_l2                                              ");
       
       sql.append("\n   union select                                                                                                            ");
       sql.append("\n         sign.fsellProjectid orgId,                                                                                               ");
       sql.append("\n         0 amount,                                                               ");
       sql.append("\n             pt.fid productTypeId,                                                                                    ");
       sql.append("\n             pt.fname_l2 productTypeName                                                                              ");
       sql.append("\n    from t_she_signManage sign                                                                                        ");
       sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
       sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
       sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
       sql.append("\n   where sign.fbizState in ('ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit') ");
       sql.append("\n     and NOT EXISTS                                                                                                   ");
       sql.append("\n   (select tt.fnewId                                                                                                  ");
       sql.append("\n            from t_she_changeManage tt                                                                                ");
       sql.append("\n           where tt.fstate in ('2SUBMITTED', '3AUDITTING')                                                                              ");
       sql.append("\n             and sign.fid = tt.fnewId)                                                                                ");
       sql.append("        and  sign.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
       sql.append("\n     and sign.fsellprojectid  ='"+sellProjectId+"'                                                                        "); 
       sql.append(" \n and EXISTS(select                                                                         "); 
	   	sql.append(" \n        revBill.frelateTransId                                                            ");    
	   	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
	   	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
	   	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
	   	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
	   	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
		sql.append(" \n       and md.fname_l2 !='面积补差款' ");
	   	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
	   	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
	   	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
	   	sql.append(" \n       and  entry.fisCS is null and revBill.frelateTransId=sign.ftransactionId)");
       
       sql.append("\n   group by sign.fsellProjectid,pt.fid,pt.fname_l2            )                                    ");
       
       builder.appendSql(sql.toString());
       IRowSet rs  = builder.executeQuery();
       Map productTypeMap = null;
       String key =null;
       try {
		while(rs.next()){
			key  = rs.getString("productTypeId");
			productTypeMap = new HashMap();
			productTypeMap.put("contractAmt", rs.getBigDecimal("contractAmt"));
			productTypeMap.put("productTypeName", rs.getString("productTypeName"));
			productTypeMap.put("calcContractAmt", rs.getBigDecimal("calcContractAmt"));
			resultMap.put(key, productTypeMap);
	   }}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
       
       builder.clear();
       sql = new StringBuffer();
       //获取回笼金额
        sql.append(" /*dialect*/ select * from(select productTypeId,productTypeName,sum(amount) over(partition by orgId) backAmt,amount calcBonusAmt from ( ");
        sql.append(" \n select                                                                         "); 
    	sql.append(" \n        revBill.fsellprojectid orgId,                                                                     ");
    	sql.append(" \n        sum(nvl(entry.famount, 0) + nvl(entry.frevAmount, 0)) amount,                     ");
    	sql.append(" \n        pt.fname_l2 productTypeName,                                                         ");
    	sql.append(" \n        pt.fid productTypeId                                                             ");    
    	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
    	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
    	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
    	sql.append(" \n       left outer join t_she_room r  on r.fid = revBill.froomid                               ");
    	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
    	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
    	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
    	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
    	sql.append(" \n       and md.fname_l2 !='面积补差款' ");
    	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
    	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
    	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('QRNullify','ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
    	sql.append(" \n       and  entry.fisCS is null");
    	sql.append(" \n  group by revBill.fsellProjectId,pt.fid,pt.fname_l2 )      group by productTypeId,productTypeName,orgid,amount ) where backamt !=0            ");
       
    	builder.appendSql(sql.toString());
       
       rs  = builder.executeQuery();
       try {
		while(rs.next()){
			key  = rs.getString("productTypeId");
			productTypeMap = (Map) resultMap.get(key);
			if(productTypeMap ==null){
				continue;
//				productTypeMap = new HashMap();
//				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
			}
			productTypeMap.put("backAmt", rs.getBigDecimal("backAmt"));
			productTypeMap.put("calcBonus", rs.getBigDecimal("calcBonusAmt"));
			resultMap.put(key, productTypeMap);
	   }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	builder.clear();
    sql = new StringBuffer();
    //获取回笼金额
    sql.append(" /*dialect*/ select count(roomId) count,productTypeId from(select revBill.froomid roomId,pt.fid productTypeId                                                               "); 
 	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
 	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
 	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
 	sql.append(" \n       left outer join t_she_room r  on r.fid = revBill.froomid                               ");
	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
 	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
 	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
 	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
 	sql.append(" \n       and md.fname_l2 !='面积补差款' ");
 	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
 	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
 	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('QRNullify','ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
 	sql.append(" \n       and  entry.fisCS is null");
 	sql.append(" \n  group by revBill.froomid,pt.fid having sum(nvl(entry.famount, 0) + nvl(entry.frevAmount, 0))!=0) group by productTypeId");
    
 	builder.appendSql(sql.toString());
    
    rs  = builder.executeQuery();
    try {
		while(rs.next()){
			key  = rs.getString("productTypeId");
			productTypeMap = (Map) resultMap.get(key);
			if(productTypeMap ==null){
				continue;
//				productTypeMap = new HashMap();
//				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
			}
			productTypeMap.put("calcTBonus", rs.getBigDecimal("count"));
			resultMap.put(key, productTypeMap);
	   }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	builder.clear();
    sql = new StringBuffer();
    sql.append(" /*dialect*/ select productTypeId,productTypeName,pur,sum(pur) over (partition by spid) purAmt from(select pt.fid productTypeId,pt.fname_l2 productTypeName, ");
    sql.append("\n         sum(pur.fcontractTotalAmount) pur,pur.fsellProjectid spid from t_she_purchaseManage pur     ");
 	sql.append(" \n       left outer join t_she_room r  on r.fid = pur.froomid                               ");
 	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
 	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
 	sql.append("\n   where pur.fbizState in ('PurAudit','ToSign') ");
 	sql.append(" \n       and pur.fsellprojectid  ='"+sellProjectId+"'" );
 	sql.append("         and  pur.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
    sql.append("        and  pur.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
 	sql.append(" \n  group by pt.fid,pt.fname_l2,pur.fsellProjectid)");
    
 	builder.appendSql(sql.toString());
    
    rs  = builder.executeQuery();
    try {
		while(rs.next()){
			key  = rs.getString("productTypeId");
			productTypeMap = (Map) resultMap.get(key);
			if(productTypeMap ==null){
				continue;
//				productTypeMap = new HashMap();
//				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
			}
			productTypeMap.put("pur", rs.getBigDecimal("pur"));
			productTypeMap.put("purAmt", rs.getBigDecimal("purAmt"));
			resultMap.put(key, productTypeMap);
	   }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	    
	builder.clear();
    sql = new StringBuffer();
    sql.append(" /*dialect*/ select t.productTypeId,t.productTypeName,sum(contract) contract from (select pt.fid productTypeId,pt.fname_l2 productTypeName, ");
    sql.append("\n         sum(sign.fdealTotalAmount) contract                                                            ");
    sql.append("\n    from t_she_signManage sign                                                                                        ");
    sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
    sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
    sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
    sql.append("\n   where sign.fbizState in ('ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit') ");
    sql.append("\n     and NOT EXISTS                                                                                                   ");
    sql.append("\n   (select tt.fnewId                                                                                                  ");
    sql.append("\n            from t_she_changeManage tt                                                                                ");
    sql.append("\n           where tt.fstate in ('2SUBMITTED', '3AUDITTING') and tt.FBizType!='quitRoom'                                                          ");
    sql.append("\n             and sign.fid = tt.fnewId)                                                                                ");
    sql.append("         and  sign.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
    sql.append("        and  sign.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
    sql.append("\n     and sign.fsellprojectid  ='"+sellProjectId+"'                                                                       ");
    sql.append("\n   group by pt.fid,pt.fname_l2                                              ");
    
//    sql.append("\n   union select                                                                                                            ");
//   sql.append("\n             pt.fid productTypeId,                                                                                    ");
//    sql.append("\n             pt.fname_l2 productTypeName,                                                                             ");
//    sql.append("\n         0-sum(sign.fdealTotalAmount) contract                                                            ");
//    sql.append("\n    from t_she_changeManage change                                                                                        ");
//    sql.append("\n       left outer join t_she_signManage sign  on sign.fid = change.fsrcId                                                          ");
//    sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
//    sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
//    sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
//    sql.append("\n   where change.fstate in ('4AUDITTED') and change.FBizType='quitRoom'    ");
//    sql.append("         and  change.fchangeDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//    sql.append("        and  change.fchangeDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//    sql.append("\n     and change.fsellprojectid  ='"+sellProjectId+"'                                                                       ");
//    sql.append("\n   group by pt.fid,pt.fname_l2                                         ");
    sql.append("\n   ) t group by t.productTypeId,t.productTypeName                                          ");
    
    
 	builder.appendSql(sql.toString());
    
    rs  = builder.executeQuery();
    try {
		while(rs.next()){
			key  = rs.getString("productTypeId");
			productTypeMap = (Map) resultMap.get(key);
			if(productTypeMap ==null){
				continue;
//				productTypeMap = new HashMap();
//				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
			}
			productTypeMap.put("contract", rs.getBigDecimal("contract"));
			resultMap.put(key, productTypeMap);
	   }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	    Map rMap = new HashMap();
	    rMap.put("data",resultMap);
    	return rMap;
    }
    
    @Override
    protected Map _calcSalesBonus(Context ctx, Map paramMap)
    		throws BOSException, EASBizException {
    	Map resultMap = new HashMap();
    	
    	// TODO Auto-generated method stub
    	String sellProjectId=paramMap.get("sellProject").toString();
    	int year = Integer.parseInt(paramMap.get("year")+"");
        int month = Integer.parseInt(paramMap.get("month")+"");
        
        Date t=FDCDateHelper.stringToDate(year+"-"+month+"-01");
	    Date startDate = FDCDateHelper.getFirstDayOfMonth(t);
    	Date endDate =  FDCDateHelper.getLastDayOfMonth(t);
        
    	
        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
        StringBuffer sql = new StringBuffer();
        //获取签约金额
        sql.append("\n  /*dialect*/ select  pid,productTypeId,  productTypeName, sum(amount) over (partition by pid ) contractAmt");
        sql.append("\n   from ( select                                                                                                            ");
        sql.append("\n         sign.fsellProjectid orgId,                                                                                               ");
        sql.append("\n         sum(sign.fdealTotalAmount) amount,                                                               ");
        sql.append("\n             pt.fid productTypeId,                                                                                    ");
        sql.append("\n             pt.fname_l2 productTypeName,p.fid pid                                                                              ");
        sql.append("\n    from t_she_signManage sign                                                                                        ");
        sql.append("\n       left outer join T_PM_User u  on u.fid = sign.FSalesmanID                                                         ");
        sql.append("\n       left outer join t_bd_person p  on p.fid = u.FpersonID                                                         ");
        sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
        sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
        sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
        sql.append("\n   where sign.fbizState in ('ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit') ");
        sql.append("\n     and NOT EXISTS                                                                                                   ");
        sql.append("\n   (select tt.fnewId                                                                                                  ");
        sql.append("\n            from t_she_changeManage tt                                                                                ");
        sql.append("\n           where tt.fstate in ('2SUBMITTED', '3AUDITTING')                                        ");
        sql.append("\n             and sign.fid = tt.fnewId)                                                                                ");
        sql.append("         and  sign.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
        sql.append("        and  sign.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
        sql.append("\n     and sign.fsellprojectid  ='"+sellProjectId+"'   and p.fid is not null                                                                    ");
        sql.append("\n   group by sign.fsellProjectid,pt.fid,pt.fname_l2,p.fid                                              ");
        
//        sql.append("\n   union select                                                                                                            ");
//        sql.append("\n         change.fsellProjectid orgId,                                                                                               ");
//        sql.append("\n         0-sum(sign.fdealTotalAmount) amount,                                                               ");
//        sql.append("\n             pt.fid productTypeId,                                                                                    ");
//        sql.append("\n             pt.fname_l2 productTypeName,p.fid pid                                                                               ");
//        sql.append("\n    from t_she_changeManage change                                                                                        ");
//        sql.append("\n       left outer join t_she_signManage sign  on sign.fid = change.fsrcId                                                          ");
//        sql.append("\n       left outer join T_PM_User u  on u.fid = sign.FSalesmanID                                                         ");
//        sql.append("\n       left outer join t_bd_person p  on p.fid = u.FpersonID                                                         ");
//        sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
//        sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
//        sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
//        sql.append("\n   where change.fstate in ('4AUDITTED') and change.FBizType='quitRoom'    ");
//        sql.append("         and  change.fchangeDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//        sql.append("        and  change.fchangeDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//        sql.append("\n     and change.fsellprojectid  ='"+sellProjectId+"'   and p.fid is not null                                                                     ");
//        sql.append("\n   group by change.fsellProjectid,pt.fid,pt.fname_l2,p.fid                                              ");
       
        sql.append("\n   union select                                                                                                            ");
        sql.append("\n         sign.fsellProjectid orgId,                                                                                               ");
        sql.append("\n         0 amount,                                                               ");
        sql.append("\n             pt.fid productTypeId,                                                                                    ");
        sql.append("\n             pt.fname_l2 productTypeName,p.fid pid                                                                              ");
        sql.append("\n    from t_she_signManage sign                                                                                        ");
        sql.append("\n       left outer join T_PM_User u  on u.fid = sign.FSalesmanID                                                         ");
        sql.append("\n       left outer join t_bd_person p  on p.fid = u.FpersonID                                                         ");
        sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
        sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
        sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
        sql.append("\n   where sign.fbizState in ('ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit') ");
        sql.append("\n     and NOT EXISTS                                                                                                   ");
        sql.append("\n   (select tt.fnewId                                                                                                  ");
        sql.append("\n            from t_she_changeManage tt                                                                                ");
        sql.append("\n           where tt.fstate in ('2SUBMITTED', '3AUDITTING')                                                            ");
        sql.append("\n             and sign.fid = tt.fnewId)                                                                                ");
        sql.append("        and  sign.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
        sql.append("\n     and sign.fsellprojectid  ='"+sellProjectId+"'                                                                        "); 
        sql.append(" \n and EXISTS(select                                                                         "); 
 	   	sql.append(" \n        revBill.frelateTransId                                                            ");    
 	   	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
 	   	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
 	   	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
 	   	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
 	   	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
 		sql.append(" \n       and md.fname_l2 !='面积补差款' ");
 	   	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
 	   	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
 	   	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('QRNullify','ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
 	   	sql.append(" \n       and  entry.fisCS is null and revBill.frelateTransId=sign.ftransactionId) and p.fid is not null");
        
        sql.append("\n   group by sign.fsellProjectid,pt.fid,pt.fname_l2,p.fid                  ");
        
        sql.append(" union select pur.fsellProjectid orgId,0 amount,pt.fid productTypeId,pt.fname_l2 productTypeName,p.fid pid from t_she_purchaseManage pur     ");
        sql.append("\n       left join T_SHE_PurSaleManEntry pe  on pe.fheadid = pur.fid                                                         ");
        sql.append("\n       left join T_PM_User u  on u.fid = pe.fuserid                                                         ");
        sql.append("\n       left join t_bd_person p  on p.fid = u.FpersonID                                           ");
     	sql.append(" \n       left outer join t_she_room r  on r.fid = pur.froomid                               ");
     	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
     	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
     	sql.append("\n   where p.fid is not null and pur.fbizState in ('PurAudit','ToSign') ");
     	sql.append(" \n       and pur.fsellprojectid  ='"+sellProjectId+"'" );
     	sql.append("         and  pur.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
        sql.append("        and  pur.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     	
     	sql.append("\n   group by pur.fsellProjectid,pt.fid,pt.fname_l2,p.fid            )    order by pid                                ");
        builder.appendSql(sql.toString());
        IRowSet rs  = builder.executeQuery();
        Map productTypeMap = null;
        String key =null;
        String ptkey =null;
        try {
 		while(rs.next()){
 			if(rs.getString("pid")==null) continue;
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
			productTypeMap=new HashMap();
 			productTypeMap.put("contractAmt", rs.getBigDecimal("contractAmt"));
 			productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			resultMap.put(key, productTypeMap);
 	   }}catch (SQLException e) {
 			e.printStackTrace();
 		}
        
        
        builder.clear();
        sql = new StringBuffer();
        //获取回笼金额
         sql.append(" /*dialect*/ select * from(select pid,productTypeId,productTypeName,sum(amount) over(partition by pid) backAmt,amount calcBonusAmt from ( ");
         sql.append(" \n select                                                                         "); 
     	sql.append(" \n        t.pid pid,revBill.fsellprojectid orgId,                                                                     ");
     	sql.append(" \n        sum(nvl(entry.famount, 0) + nvl(entry.frevAmount, 0)) amount,                     ");
     	sql.append(" \n        pt.fname_l2 productTypeName,                                                         ");
     	sql.append(" \n        pt.fid productTypeId                                                             ");    
     	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
     	sql.append("\n       left join (select max(p.fid) pid,sign.ftransactionid tranId from t_she_signManage sign                                                      ");
     	sql.append("\n       left join T_PM_User u  on u.fid = sign.FSalesmanID                                                         ");
        sql.append("\n       left join t_bd_person p  on p.fid = u.FpersonID group by sign.ftransactionid)t on t.tranId=revBill.frelateTransId                                                ");
        
     	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
     	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
     	sql.append(" \n       left outer join t_she_room r  on r.fid = revBill.froomid                               ");
     	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
     	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
     	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
     	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
     	sql.append(" \n       and md.fname_l2 !='面积补差款' ");
     	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
     	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('QRNullify','ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
     	sql.append(" \n       and  entry.fisCS is null");
     	sql.append(" \n  group by revBill.fsellProjectId,pt.fid,pt.fname_l2,t.pid )      group by productTypeId,productTypeName,orgid,amount,pid ) where backamt !=0            ");
        
     	builder.appendSql(sql.toString());
        
        rs  = builder.executeQuery();
        try {
 		while(rs.next()){
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
 			productTypeMap = (Map) resultMap.get(key);
 			if(productTypeMap ==null){
 				continue;
// 				productTypeMap = new HashMap();
// 				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			}
 			productTypeMap.put("backAmt", rs.getBigDecimal("backAmt"));
 			productTypeMap.put("calcBonus", rs.getBigDecimal("calcBonusAmt"));
 			resultMap.put(key, productTypeMap);
 	   }
 	} catch (SQLException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}
 	builder.clear();
     sql = new StringBuffer();
     //获取回笼金额
     sql.append(" /*dialect*/ select count(roomId) count,productTypeId,pid from(select t.pid pid,revBill.froomid roomId,pt.fid productTypeId                                                               "); 
  	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
  	sql.append("\n       left join (select max(p.fid) pid,sign.ftransactionid tranId from t_she_signManage sign                                                      ");
 	sql.append("\n       left join T_PM_User u  on u.fid = sign.FSalesmanID                                                         ");
    sql.append("\n       left join t_bd_person p  on p.fid = u.FpersonID group by sign.ftransactionid)t on t.tranId=revBill.frelateTransId                                                ");
   	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
  	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
  	sql.append(" \n       left outer join t_she_room r  on r.fid = revBill.froomid                               ");
 	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
  	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
  	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
  	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
  	sql.append(" \n       and md.fname_l2 !='面积补差款' ");
  	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
  	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
  	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('QRNullify','ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
  	sql.append(" \n       and  entry.fisCS is null");
  	sql.append(" \n  group by revBill.froomid,pt.fid,t.pid having sum(nvl(entry.famount, 0) + nvl(entry.frevAmount, 0))!=0) group by productTypeId,pid");
     
  	builder.appendSql(sql.toString());
     
     rs  = builder.executeQuery();
     try {
 		while(rs.next()){
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
 			productTypeMap = (Map) resultMap.get(key);
 			if(productTypeMap ==null){
 				continue;
// 				productTypeMap = new HashMap();
// 				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			}
 			productTypeMap.put("calcTBonus", rs.getBigDecimal("count"));
 			resultMap.put(key, productTypeMap);
 	   }
 	} catch (SQLException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}
 	builder.clear();
     sql = new StringBuffer();
     sql.append(" /*dialect*/ select pid,productTypeId,productTypeName,pur,sum(pur) over (partition by pid) purAmt from(select p.fid pid, pt.fid productTypeId,pt.fname_l2 productTypeName, ");
     sql.append("\n         sum(pur.fcontractTotalAmount) pur from t_she_purchaseManage pur     ");
     sql.append("\n       left join T_SHE_PurSaleManEntry pe  on pe.fheadid = pur.fid                                                         ");
     sql.append("\n       left join T_PM_User u  on u.fid = pe.fuserid                                                         ");
     sql.append("\n       left join t_bd_person p  on p.fid = u.FpersonID                                           ");
  	sql.append(" \n       left outer join t_she_room r  on r.fid = pur.froomid                               ");
  	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
  	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
  	sql.append("\n   where pur.fbizState in ('PurAudit','ToSign') ");
  	sql.append(" \n       and pur.fsellprojectid  ='"+sellProjectId+"'" );
  	sql.append("         and  pur.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     sql.append("        and  pur.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
  	sql.append(" \n  group by pt.fid,pt.fname_l2,p.fid)");
     
  	builder.appendSql(sql.toString());
     
     rs  = builder.executeQuery();
     try {
 		while(rs.next()){
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
 			productTypeMap = (Map) resultMap.get(key);
 			if(productTypeMap ==null){
 				continue;
// 				productTypeMap = new HashMap();
// 				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			}
 			productTypeMap.put("purAmt", rs.getBigDecimal("purAmt"));
 			productTypeMap.put("pur", rs.getBigDecimal("pur"));
 			resultMap.put(key, productTypeMap);
 	   }
 	} catch (SQLException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}
 	    
 	builder.clear();
     sql = new StringBuffer();
     sql.append(" /*dialect*/ select t.pid,t.productTypeId,t.productTypeName,sum(t.contract)contract from(select p.fid pid,pt.fid productTypeId,pt.fname_l2 productTypeName, ");
     sql.append("\n         sum(sign.fdealTotalAmount) contract                                                            ");
     sql.append("\n    from t_she_signManage sign                                                                                        ");
 	sql.append("\n       left join T_PM_User u  on u.fid = sign.FSalesmanID                                                         ");
    sql.append("\n       left join t_bd_person p  on p.fid = u.FpersonID                                                      ");
     sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
     sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
     sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
     sql.append("\n   where sign.fbizState in ('ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit') ");
     sql.append("         and  sign.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     sql.append("        and  sign.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     sql.append("\n     and sign.fsellprojectid  ='"+sellProjectId+"'                                                                       ");
     sql.append("\n     and NOT EXISTS                                                                                                   ");
     sql.append("\n   (select tt.fnewId                                                                                                  ");
     sql.append("\n            from t_she_changeManage tt                                                                                ");
     sql.append("\n           where tt.fstate in ('2SUBMITTED', '3AUDITTING')                                        ");
     sql.append("\n             and sign.fid = tt.fnewId)                                                                                ");
     sql.append("\n   group by pt.fid,pt.fname_l2,p.fid                                              ");
    
//     sql.append("\n   union select                                                                                                            ");
//     sql.append("\n             p.fid pid,pt.fid productTypeId,                                                                                    ");
//      sql.append("\n             pt.fname_l2 productTypeName,                                                                             ");
//      sql.append("\n         0-sum(sign.fdealTotalAmount) contract                                                            ");
//      sql.append("\n    from t_she_changeManage change                                                                                        ");
//      sql.append("\n       left outer join t_she_signManage sign  on sign.fid = change.fsrcId                                                          ");
//      sql.append("\n       left join T_PM_User u  on u.fid = sign.FSalesmanID                                                         ");
//      sql.append("\n       left join t_bd_person p  on p.fid = u.FpersonID                                                      ");
//      sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
//      sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
//      sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
//      sql.append("\n   where change.fstate in ('4AUDITTED') and change.FBizType='quitRoom'    ");
//      sql.append("         and  change.fchangeDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//      sql.append("        and  change.fchangeDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//      sql.append("\n     and change.fsellprojectid  ='"+sellProjectId+"'                                                                       ");
//      sql.append("\n   group by pt.fid,pt.fname_l2,p.fid                                        ");
      sql.append("\n   )t group by t.pid,t.productTypeId,t.productTypeName                                         ");
      
  	builder.appendSql(sql.toString());
     
     rs  = builder.executeQuery();
     try {
 		while(rs.next()){
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
 			productTypeMap = (Map) resultMap.get(key);
 			if(productTypeMap ==null){
 				continue;
// 				productTypeMap = new HashMap();
// 				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			}
 			productTypeMap.put("contract", rs.getBigDecimal("contract"));
 			resultMap.put(key, productTypeMap);
 	   }
 	} catch (SQLException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}
 	    Map rMap = new HashMap();
 	    rMap.put("data",resultMap);
     	return rMap;
    }

	@Override
	protected Map _calcQd(Context ctx, Map paramMap) throws BOSException,
			EASBizException {
		Map resultMap = new HashMap();
    	
    	// TODO Auto-generated method stub
    	String sellProjectId=paramMap.get("sellProject").toString();
    	int year = Integer.parseInt(paramMap.get("year")+"");
        int month = Integer.parseInt(paramMap.get("month")+"");
        
        Date t=FDCDateHelper.stringToDate(year+"-"+month+"-01");
	    Date startDate = FDCDateHelper.getFirstDayOfMonth(t);
    	Date endDate =  FDCDateHelper.getLastDayOfMonth(t);
        
    	
        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
        StringBuffer sql = new StringBuffer();
        //获取签约金额
        sql.append("\n  /*dialect*/ select  pid,productTypeId,  productTypeName, sum(amount) over (partition by pid ) contractAmt");
        sql.append("\n   from ( select                                                                                                            ");
        sql.append("\n         sign.fsellProjectid orgId,                                                                                               ");
        sql.append("\n         sum(sign.fdealTotalAmount) amount,                                                               ");
        sql.append("\n             pt.fid productTypeId,                                                                                    ");
        sql.append("\n             pt.fname_l2 productTypeName,sign.fqdPerson pid                                                                              ");
        sql.append("\n    from t_she_signManage sign                                                                                        ");
        sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
        sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
        sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
        sql.append("\n   where sign.fbizState in ('ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit') ");
        sql.append("\n     and NOT EXISTS                                                                                                   ");
        sql.append("\n   (select tt.fnewId                                                                                                  ");
        sql.append("\n            from t_she_changeManage tt                                                                                ");
        sql.append("\n           where tt.fstate in ('2SUBMITTED', '3AUDITTING')                                                            ");
        sql.append("\n             and sign.fid = tt.fnewId)                                                                                ");
        sql.append("         and  sign.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
        sql.append("        and  sign.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
        sql.append("\n     and sign.fsellprojectid  ='"+sellProjectId+"'   and sign.fqdPerson is not null                                                                    ");
        sql.append("\n   group by sign.fsellProjectid,pt.fid,pt.fname_l2,sign.fqdPerson                                              ");
        
//        sql.append("\n   union select                                                                                                            ");
//        sql.append("\n         change.fsellProjectid orgId,                                                                                               ");
//        sql.append("\n         0-sum(sign.fdealTotalAmount) amount,                                                               ");
//        sql.append("\n             pt.fid productTypeId,                                                                                    ");
//        sql.append("\n             pt.fname_l2 productTypeName,sign.fqdPerson pid                                                                               ");
//        sql.append("\n    from t_she_changeManage change                                                                                        ");
//        sql.append("\n       left outer join t_she_signManage sign  on sign.fid = change.fsrcId                                                          ");
//        sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
//        sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
//        sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
//        sql.append("\n   where change.fstate in ('4AUDITTED') and change.FBizType='quitRoom'    ");
//        sql.append("         and  change.fchangeDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//        sql.append("        and  change.fchangeDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//        sql.append("\n     and change.fsellprojectid  ='"+sellProjectId+"' and sign.fqdPerson is not null                                                                     ");
//        sql.append("\n   group by change.fsellProjectid,pt.fid,pt.fname_l2,sign.fqdPerson                                               ");
      
        sql.append("\n   union select                                                                                                            ");
        sql.append("\n         sign.fsellProjectid orgId,                                                                                               ");
        sql.append("\n         0 amount,                                                               ");
        sql.append("\n             pt.fid productTypeId,                                                                                    ");
        sql.append("\n             pt.fname_l2 productTypeName,sign.fqdPerson pid                                                                              ");
        sql.append("\n    from t_she_signManage sign                                                                                        ");
        sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
        sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
        sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
        sql.append("\n   where sign.fbizState in ('ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit') ");
        sql.append("\n     and NOT EXISTS                                                                                                   ");
        sql.append("\n   (select tt.fnewId                                                                                                  ");
        sql.append("\n            from t_she_changeManage tt                                                                                ");
        sql.append("\n           where tt.fstate in ('2SUBMITTED', '3AUDITTING')                                                            ");
        sql.append("\n             and sign.fid = tt.fnewId)                                                                                ");
        sql.append("        and  sign.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
        sql.append("\n     and sign.fsellprojectid  ='"+sellProjectId+"'                                                                        "); 
        sql.append(" \n and EXISTS(select                                                                         "); 
 	   	sql.append(" \n        revBill.frelateTransId                                                            ");    
 	   	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
 	   	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
 	   	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
 	   	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
 	   	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
 		sql.append(" \n       and md.fname_l2 !='面积补差款' ");
 	   	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
 	   	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
 	   	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('QRNullify','ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
 	   	sql.append(" \n       and  entry.fisCS is null and revBill.frelateTransId=sign.ftransactionId) and sign.fqdPerson is not null");
 	   sql.append("\n   group by sign.fsellProjectid,pt.fid,pt.fname_l2,sign.fqdPerson                          ");
 	   
 	   sql.append(" union select pur.fsellProjectid orgId,0 amount, pt.fid productTypeId,pt.fname_l2 productTypeName,pur.fqdPerson pid from t_she_purchaseManage pur     ");
 	     sql.append("\n       left join T_SHE_PurSaleManEntry pe  on pe.fheadid = pur.fid                                                         ");
 	  	sql.append(" \n       left outer join t_she_room r  on r.fid = pur.froomid                               ");
 	  	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
 	  	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
 	  	sql.append("\n   where pur.fqdPerson is not null and pur.fbizState in ('PurAudit','ToSign') ");
 	  	sql.append(" \n       and pur.fsellprojectid  ='"+sellProjectId+"'" );
 	  	sql.append("         and  pur.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
 	     sql.append("        and  pur.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
 	  	
        sql.append("\n   group by pur.fsellProjectid,pt.fid,pt.fname_l2,pur.fqdPerson         )    order by pid                                ");
        
        builder.appendSql(sql.toString());
        IRowSet rs  = builder.executeQuery();
        Map productTypeMap = null;
        String key =null;
        String ptkey =null;
        try {
 		while(rs.next()){
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
			productTypeMap=new HashMap();
 			productTypeMap.put("contractAmt", rs.getBigDecimal("contractAmt"));
 			productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			resultMap.put(key, productTypeMap);
 	   }}catch (SQLException e) {
 			e.printStackTrace();
 		}
        
        
        builder.clear();
        sql = new StringBuffer();
        //获取回笼金额
         sql.append(" /*dialect*/ select * from(select pid,productTypeId,productTypeName,sum(amount) over(partition by pid) backAmt,amount calcBonusAmt from ( ");
         sql.append(" \n select                                                                         "); 
     	sql.append(" \n        t.pid pid,revBill.fsellprojectid orgId,                                                                     ");
     	sql.append(" \n        sum(nvl(entry.famount, 0) + nvl(entry.frevAmount, 0)) amount,                     ");
     	sql.append(" \n        pt.fname_l2 productTypeName,                                                         ");
     	sql.append(" \n        pt.fid productTypeId                                                             ");    
     	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
     	sql.append("\n       left join (select max(sign.fqdPerson) pid,sign.ftransactionid tranId from t_she_signManage sign                                                      ");
        sql.append("\n       group by sign.ftransactionid)t on t.tranId=revBill.frelateTransId                                                ");
        
     	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
     	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
     	sql.append(" \n       left outer join t_she_room r  on r.fid = revBill.froomid                               ");
     	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
     	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
     	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
     	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
     	sql.append(" \n       and md.fname_l2 !='面积补差款' ");
     	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
     	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('QRNullify','ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
     	sql.append(" \n       and  entry.fisCS is null");
     	sql.append(" \n  group by revBill.fsellProjectId,pt.fid,pt.fname_l2,t.pid )      group by productTypeId,productTypeName,orgid,amount,pid ) where backamt !=0            ");
        
     	builder.appendSql(sql.toString());
        
        rs  = builder.executeQuery();
        try {
 		while(rs.next()){
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
 			productTypeMap = (Map) resultMap.get(key);
 			if(productTypeMap ==null){
 				continue;
// 				productTypeMap = new HashMap();
// 				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			}
 			productTypeMap.put("backAmt", rs.getBigDecimal("backAmt"));
 			productTypeMap.put("calcBonus", rs.getBigDecimal("calcBonusAmt"));
 			resultMap.put(key, productTypeMap);
 	   }
 	} catch (SQLException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}
 	builder.clear();
     sql = new StringBuffer();
     //获取回笼金额
     sql.append(" /*dialect*/ select count(roomId) count,productTypeId,pid from(select t.pid pid,revBill.froomid roomId,pt.fid productTypeId                                                               "); 
  	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
  	sql.append("\n       left join (select max(sign.fqdPerson) pid,sign.ftransactionid tranId from t_she_signManage sign                                                      ");
    sql.append("\n       group by sign.ftransactionid)t on t.tranId=revBill.frelateTransId                                                ");
   	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
  	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
  	sql.append(" \n       left outer join t_she_room r  on r.fid = revBill.froomid                               ");
 	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
  	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
  	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
  	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
  	sql.append(" \n       and md.fname_l2 !='面积补差款' ");
  	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
  	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
  	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('QRNullify','ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
  	sql.append(" \n       and  entry.fisCS is null");
  	sql.append(" \n  group by revBill.froomid,pt.fid,t.pid having sum(nvl(entry.famount, 0) + nvl(entry.frevAmount, 0))!=0) group by productTypeId,pid");
     
  	builder.appendSql(sql.toString());
     
     rs  = builder.executeQuery();
     try {
 		while(rs.next()){
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
 			productTypeMap = (Map) resultMap.get(key);
 			if(productTypeMap ==null){
 				continue;
// 				productTypeMap = new HashMap();
// 				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			}
 			productTypeMap.put("calcTBonus", rs.getBigDecimal("count"));
 			resultMap.put(key, productTypeMap);
 	   }
 	} catch (SQLException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}
 	builder.clear();
     sql = new StringBuffer();
     sql.append(" /*dialect*/ select pid,productTypeId,productTypeName,pur,sum(pur) over (partition by pid) purAmt from( select pur.fqdPerson pid, pt.fid productTypeId,pt.fname_l2 productTypeName, ");
     sql.append("\n         sum(pur.fcontractTotalAmount) pur from t_she_purchaseManage pur     ");
     sql.append("\n       left join T_SHE_PurSaleManEntry pe  on pe.fheadid = pur.fid                                                         ");
  	sql.append(" \n       left outer join t_she_room r  on r.fid = pur.froomid                               ");
  	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
  	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
  	sql.append("\n   where pur.fbizState in ('PurAudit','ToSign') ");
  	sql.append(" \n       and pur.fsellprojectid  ='"+sellProjectId+"'" );
  	sql.append("         and  pur.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     sql.append("        and  pur.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
  	sql.append(" \n  group by pt.fid,pt.fname_l2,pur.fqdPerson) ");
     
  	builder.appendSql(sql.toString());
     
     rs  = builder.executeQuery();
     try {
 		while(rs.next()){
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
 			productTypeMap = (Map) resultMap.get(key);
 			if(productTypeMap ==null){
 				continue;
// 				productTypeMap = new HashMap();
// 				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			}
 			productTypeMap.put("purAmt", rs.getBigDecimal("purAmt"));
 			productTypeMap.put("pur", rs.getBigDecimal("pur"));
 			resultMap.put(key, productTypeMap);
 	   }
 	} catch (SQLException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}
 	    
 	builder.clear();
     sql = new StringBuffer();
     sql.append(" /*dialect*/ select t.pid,t.productTypeid,t.productTypeName,sum(t.contract) contract from(select sign.fqdPerson pid,pt.fid productTypeId,pt.fname_l2 productTypeName, ");
     sql.append("\n         sum(sign.fdealTotalAmount) contract                                                            ");
     sql.append("\n    from t_she_signManage sign                                                                                        ");
     sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
     sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
     sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
     sql.append("\n   where sign.fbizState in ('ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit') ");
     sql.append("         and  sign.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     sql.append("        and  sign.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     sql.append("\n     and sign.fsellprojectid  ='"+sellProjectId+"'                                                                     ");
     sql.append("\n     and NOT EXISTS                                                                                                   ");
     sql.append("\n   (select tt.fnewId                                                                                                  ");
     sql.append("\n            from t_she_changeManage tt                                                                                ");
     sql.append("\n           where tt.fstate in ('2SUBMITTED', '3AUDITTING')                                        ");
     sql.append("\n             and sign.fid = tt.fnewId)                                                                                ");
     sql.append("\n   group by pt.fid,pt.fname_l2,sign.fqdPerson                                            ");
    
//     sql.append("\n   union select                                                                                                            ");
//     sql.append("\n            sign.fqdPerson pid, pt.fid productTypeId,                                                                                    ");
//      sql.append("\n             pt.fname_l2 productTypeName,                                                                             ");
//      sql.append("\n         0-sum(sign.fdealTotalAmount) contract                                                            ");
//      sql.append("\n    from t_she_changeManage change                                                                                        ");
//      sql.append("\n       left outer join t_she_signManage sign  on sign.fid = change.fsrcId                                                          ");
//      sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
//      sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
//      sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
//      sql.append("\n   where change.fstate in ('4AUDITTED') and change.FBizType='quitRoom'    ");
//      sql.append("         and  change.fchangeDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//      sql.append("        and  change.fchangeDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//      sql.append("\n     and change.fsellprojectid  ='"+sellProjectId+"'                                                                     ");
//      sql.append("\n   group by pt.fid,pt.fname_l2,sign.fqdPerson                                                ");
      sql.append("\n   )t     group by t.pid,t.productTypeid,t.productTypeName                                              ");
      
  	builder.appendSql(sql.toString());
     
     rs  = builder.executeQuery();
     try {
 		while(rs.next()){
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
 			productTypeMap = (Map) resultMap.get(key);
 			if(productTypeMap ==null){
 				continue;
// 				productTypeMap = new HashMap();
// 				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			}
 			productTypeMap.put("contract", rs.getBigDecimal("contract"));
 			resultMap.put(key, productTypeMap);
 	   }
 	} catch (SQLException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}
 	    Map rMap = new HashMap();
 	    rMap.put("data",resultMap);
     	return rMap;
	}

	@Override
	protected Map _calcRec(Context ctx, Map paramMap) throws BOSException,
			EASBizException {
		Map resultMap = new HashMap();
    	
    	// TODO Auto-generated method stub
    	String sellProjectId=paramMap.get("sellProject").toString();
    	int year = Integer.parseInt(paramMap.get("year")+"");
        int month = Integer.parseInt(paramMap.get("month")+"");
        
        Date t=FDCDateHelper.stringToDate(year+"-"+month+"-01");
	    Date startDate = FDCDateHelper.getFirstDayOfMonth(t);
    	Date endDate =  FDCDateHelper.getLastDayOfMonth(t);
        
    	
        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
        StringBuffer sql = new StringBuffer();
        //获取签约金额
        sql.append("\n  /*dialect*/ select  pid,productTypeId,  productTypeName, sum(amount) over (partition by pid ) contractAmt");
        sql.append("\n   from ( select                                                                                                            ");
        sql.append("\n         sign.fsellProjectid orgId,                                                                                               ");
        sql.append("\n         sum(sign.fdealTotalAmount) amount,                                                               ");
        sql.append("\n             pt.fid productTypeId,                                                                                    ");
        sql.append("\n             pt.fname_l2 productTypeName,sign.CFRecommended pid                                                                              ");
        sql.append("\n    from t_she_signManage sign                                                                                        ");
        sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
        sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
        sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
        sql.append("\n   where sign.fbizState in ('ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit') ");
        sql.append("\n     and NOT EXISTS                                                                                                   ");
        sql.append("\n   (select tt.fnewId                                                                                                  ");
        sql.append("\n            from t_she_changeManage tt                                                                                ");
        sql.append("\n           where tt.fstate in ('2SUBMITTED', '3AUDITTING')                                                            ");
        sql.append("\n             and sign.fid = tt.fnewId)                                                                                ");
        sql.append("         and  sign.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
        sql.append("        and  sign.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
        sql.append("\n     and sign.fsellprojectid  ='"+sellProjectId+"'   and sign.CFRecommended is not null                                                                    ");
        sql.append("\n   group by sign.fsellProjectid,pt.fid,pt.fname_l2,sign.CFRecommended                                              ");
        
//        sql.append("\n   union select                                                                                                            ");
//        sql.append("\n         change.fsellProjectid orgId,                                                                                               ");
//        sql.append("\n         0-sum(sign.fdealTotalAmount) amount,                                                               ");
//        sql.append("\n             pt.fid productTypeId,                                                                                    ");
//        sql.append("\n             pt.fname_l2 productTypeName,sign.CFRecommended pid                                                                                 ");
//        sql.append("\n    from t_she_changeManage change                                                                                        ");
//        sql.append("\n       left outer join t_she_signManage sign  on sign.fid = change.fsrcId                                                          ");
//        sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
//        sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
//        sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
//        sql.append("\n   where change.fstate in ('4AUDITTED') and change.FBizType='quitRoom'    ");
//        sql.append("         and  change.fchangeDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//        sql.append("        and  change.fchangeDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//        sql.append("\n     and change.fsellprojectid  ='"+sellProjectId+"'   and sign.CFRecommended is not null                                                                ");
//        sql.append("\n   group by change.fsellProjectid,pt.fid,pt.fname_l2,sign.CFRecommended                                                 ");
        
        sql.append("\n   union select                                                                                                            ");
        sql.append("\n         sign.fsellProjectid orgId,                                                                                               ");
        sql.append("\n         0 amount,                                                               ");
        sql.append("\n             pt.fid productTypeId,                                                                                    ");
        sql.append("\n             pt.fname_l2 productTypeName,sign.CFRecommended pid                                                                              ");
        sql.append("\n    from t_she_signManage sign                                                                                        ");
        sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
        sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
        sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
        sql.append("\n   where sign.fbizState in ('ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit') ");
        sql.append("\n     and NOT EXISTS                                                                                                   ");
        sql.append("\n   (select tt.fnewId                                                                                                  ");
        sql.append("\n            from t_she_changeManage tt                                                                                ");
        sql.append("\n           where tt.fstate in ('2SUBMITTED', '3AUDITTING')                                                            ");
        sql.append("\n             and sign.fid = tt.fnewId)                                                                                ");
        sql.append("        and  sign.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
        sql.append("\n     and sign.fsellprojectid  ='"+sellProjectId+"'                                                                        "); 
        sql.append(" \n and EXISTS(select                                                                         "); 
 	   	sql.append(" \n        revBill.frelateTransId                                                            ");    
 	   	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
 	   	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
 	   	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
 	   	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
 	   	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
 		sql.append(" \n       and md.fname_l2 !='面积补差款' ");
 	   	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
 	   	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
 	   	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('QRNullify','ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
 	   	sql.append(" \n       and  entry.fisCS is null and revBill.frelateTransId=sign.ftransactionId) and sign.CFRecommended is not null");
        
        sql.append("\n   group by sign.fsellProjectid,pt.fid,pt.fname_l2,sign.CFRecommended                 ");
        
        sql.append(" union select pur.fsellProjectid orgId,0 amount,pt.fid productTypeId,pt.fname_l2 productTypeName,pur.CFRecommended pid from t_she_purchaseManage pur     ");
        sql.append("\n       left join T_SHE_PurSaleManEntry pe  on pe.fheadid = pur.fid                                                         ");
     	sql.append(" \n       left outer join t_she_room r  on r.fid = pur.froomid                               ");
     	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
     	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
     	sql.append("\n   where pur.CFRecommended is not null and pur.fbizState in ('PurAudit','ToSign') ");
     	sql.append(" \n       and pur.fsellprojectid  ='"+sellProjectId+"'" );
     	sql.append("         and  pur.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
        sql.append("        and  pur.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
        
        sql.append("\n   group by pur.fsellProjectid,pt.fid,pt.fname_l2,pur.CFRecommended         )    order by pid                                ");
        
        builder.appendSql(sql.toString());
        IRowSet rs  = builder.executeQuery();
        Map productTypeMap = null;
        String key =null;
        String ptkey =null;
        try {
 		while(rs.next()){
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
			productTypeMap=new HashMap();
 			productTypeMap.put("contractAmt", rs.getBigDecimal("contractAmt"));
 			productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			resultMap.put(key, productTypeMap);
 	   }}catch (SQLException e) {
 			e.printStackTrace();
 		}
        
        
        builder.clear();
        sql = new StringBuffer();
        //获取回笼金额
         sql.append(" /*dialect*/ select * from(select pid,productTypeId,productTypeName,sum(amount) over(partition by pid) backAmt,amount calcBonusAmt from ( ");
         sql.append(" \n select                                                                         "); 
     	sql.append(" \n        t.pid pid,revBill.fsellprojectid orgId,                                                                     ");
     	sql.append(" \n        sum(nvl(entry.famount, 0) + nvl(entry.frevAmount, 0)) amount,                     ");
     	sql.append(" \n        pt.fname_l2 productTypeName,                                                         ");
     	sql.append(" \n        pt.fid productTypeId                                                             ");    
     	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
     	sql.append("\n       left join (select max(sign.CFRecommended) pid,sign.ftransactionid tranId from t_she_signManage sign                                                      ");
        sql.append("\n       group by sign.ftransactionid)t on t.tranId=revBill.frelateTransId                                                ");
        
     	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
     	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
     	sql.append(" \n       left outer join t_she_room r  on r.fid = revBill.froomid                               ");
     	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
     	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
     	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
     	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
     	sql.append(" \n       and md.fname_l2 !='面积补差款' ");
     	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
     	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('QRNullify','ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
     	sql.append(" \n       and  entry.fisCS is null");
     	sql.append(" \n  group by revBill.fsellProjectId,pt.fid,pt.fname_l2,t.pid )      group by productTypeId,productTypeName,orgid,amount,pid ) where backamt !=0            ");
        
     	builder.appendSql(sql.toString());
        
        rs  = builder.executeQuery();
        try {
 		while(rs.next()){
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
 			productTypeMap = (Map) resultMap.get(key);
 			if(productTypeMap ==null){
 				continue;
// 				productTypeMap = new HashMap();
// 				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			}
 			productTypeMap.put("backAmt", rs.getBigDecimal("backAmt"));
 			productTypeMap.put("calcBonus", rs.getBigDecimal("calcBonusAmt"));
 			resultMap.put(key, productTypeMap);
 	   }
 	} catch (SQLException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}
 	builder.clear();
     sql = new StringBuffer();
     //获取回笼金额
     sql.append(" /*dialect*/ select count(roomId) count,productTypeId,pid from(select t.pid pid,revBill.froomid roomId,pt.fid productTypeId                                                               "); 
  	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
  	sql.append("\n       left join (select max(sign.CFRecommended) pid,sign.ftransactionid tranId from t_she_signManage sign                                                      ");
    sql.append("\n       group by sign.ftransactionid)t on t.tranId=revBill.frelateTransId                                                ");
   	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
  	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
  	sql.append(" \n       left outer join t_she_room r  on r.fid = revBill.froomid                               ");
 	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
  	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
  	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
  	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
  	sql.append(" \n       and md.fname_l2 !='面积补差款' ");
  	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
  	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
  	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('QRNullify','ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
  	sql.append(" \n       and  entry.fisCS is null");
  	sql.append(" \n  group by revBill.froomid,pt.fid,t.pid having sum(nvl(entry.famount, 0) + nvl(entry.frevAmount, 0))!=0) group by productTypeId,pid");
     
  	builder.appendSql(sql.toString());
     
     rs  = builder.executeQuery();
     try {
 		while(rs.next()){
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
 			productTypeMap = (Map) resultMap.get(key);
 			if(productTypeMap ==null){
 				continue;
// 				productTypeMap = new HashMap();
// 				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			}
 			productTypeMap.put("calcTBonus", rs.getBigDecimal("count"));
 			resultMap.put(key, productTypeMap);
 	   }
 	} catch (SQLException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}
 	builder.clear();
     sql = new StringBuffer();
     sql.append(" /*dialect*/ select pid,productTypeId,productTypeName,pur,sum(pur) over (partition by pid) purAmt from(select pur.CFRecommended pid, pt.fid productTypeId,pt.fname_l2 productTypeName, ");
     sql.append("\n         sum(pur.fcontractTotalAmount) pur from t_she_purchaseManage pur     ");
     sql.append("\n       left join T_SHE_PurSaleManEntry pe  on pe.fheadid = pur.fid                                                         ");
  	sql.append(" \n       left outer join t_she_room r  on r.fid = pur.froomid                               ");
  	sql.append(" \n       left outer join t_she_building b  on b.fid = r.fbuildingid                          ");
  	sql.append(" \n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                  ");
  	sql.append("\n   where pur.fbizState in ('PurAudit','ToSign') ");
  	sql.append(" \n       and pur.fsellprojectid  ='"+sellProjectId+"'" );
  	sql.append("         and  pur.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     sql.append("        and  pur.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
  	sql.append(" \n  group by pt.fid,pt.fname_l2,pur.CFRecommended )");
     
  	builder.appendSql(sql.toString());
     
     rs  = builder.executeQuery();
     try {
 		while(rs.next()){
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
 			productTypeMap = (Map) resultMap.get(key);
 			if(productTypeMap ==null){
 				continue;
// 				productTypeMap = new HashMap();
// 				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			}
 			productTypeMap.put("purAmt", rs.getBigDecimal("purAmt"));
 			productTypeMap.put("pur", rs.getBigDecimal("pur"));
 			resultMap.put(key, productTypeMap);
 	   }
 	} catch (SQLException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}
 	    
 	builder.clear();
     sql = new StringBuffer();
     sql.append(" /*dialect*/ select t.pid,t.productTypeid,t.productTypeName,sum(t.contract) contract from( select sign.CFRecommended pid,pt.fid productTypeId,pt.fname_l2 productTypeName, ");
     sql.append("\n         sum(sign.fdealTotalAmount) contract                                                            ");
     sql.append("\n    from t_she_signManage sign                                                                                        ");
     sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
     sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
     sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
     sql.append("\n   where sign.fbizState in ('ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit') ");
     sql.append("         and  sign.fbusAdscriptionDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     sql.append("        and  sign.fbusAdscriptionDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
     sql.append("\n     and sign.fsellprojectid  ='"+sellProjectId+"'                                                                       ");
     sql.append("\n     and NOT EXISTS   ");
     sql.append("\n   (select tt.fnewId                                                                                                  ");
     sql.append("\n            from t_she_changeManage tt                                                                                ");
     sql.append("\n           where tt.fstate in ('2SUBMITTED', '3AUDITTING')                                        ");
     sql.append("\n             and sign.fid = tt.fnewId)                                                                                ");
     sql.append("\n   group by pt.fid,pt.fname_l2,sign.CFRecommended                                            ");
    
//     sql.append("\n   union select                                                                                                            ");
//     sql.append("\n             sign.CFRecommended pid,pt.fid productTypeId,                                                                                    ");
//      sql.append("\n             pt.fname_l2 productTypeName,                                                                             ");
//      sql.append("\n         0-sum(sign.fdealTotalAmount) contract                                                            ");
//      sql.append("\n    from t_she_changeManage change                                                                                        ");
//      sql.append("\n       left outer join t_she_signManage sign  on sign.fid = change.fsrcId                                                          ");
//      sql.append("\n       left outer join t_she_room r  on r.fid = sign.froomid                                                          ");
//      sql.append("\n       left outer join t_she_building b  on b.fid = r.fbuildingid                                                     ");
//      sql.append("\n       left outer join T_FDC_ProductType pt  on pt.fid = b.fproducttypeid                                             ");
//      sql.append("\n   where change.fstate in ('4AUDITTED') and change.FBizType='quitRoom'    ");
//      sql.append("         and  change.fchangeDate>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//      sql.append("        and  change.fchangeDate<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
//      sql.append("\n     and change.fsellprojectid  ='"+sellProjectId+"'                                                                       ");
//      sql.append("\n   group by pt.fid,pt.fname_l2,sign.CFRecommended                                                        ");
      sql.append("\n   )t group by  t.pid,t.productTypeid,t.productTypeName                                                       ");
      
  	builder.appendSql(sql.toString());
     
     rs  = builder.executeQuery();
     try {
 		while(rs.next()){
 			key  = rs.getString("pid")+"%"+rs.getString("productTypeId");
 			productTypeMap = (Map) resultMap.get(key);
 			if(productTypeMap ==null){
 				continue;
// 				productTypeMap = new HashMap();
// 				productTypeMap.put("productTypeName", rs.getString("productTypeName"));
 			}
 			productTypeMap.put("contract", rs.getBigDecimal("contract"));
 			resultMap.put(key, productTypeMap);
 	   }
 	} catch (SQLException e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 	}
 	    Map rMap = new HashMap();
 	    rMap.put("data",resultMap);
     	return rMap;
	}

	@Override
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		super._unAudit(ctx, billId);
		CommissionSettlementBillInfo info=CommissionSettlementBillFactory.getLocalInstance(ctx).getCommissionSettlementBillInfo(new ObjectUuidPK(billId));
		String sellProjectId=info.getSellProject().getId().toString();
	       int year = info.getYear();
	       int month = info.getMonth();
	       String cs=String.valueOf(year)+String.valueOf(month);
	       Date t=FDCDateHelper.stringToDate(year+"-"+month+"-01");
		    Date startDate = FDCDateHelper.getFirstDayOfMonth(t);
	    	Date endDate =  FDCDateHelper.getLastDayOfMonth(t);
	       
	       
	       FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	       StringBuffer sql = new StringBuffer();
	       sql = new StringBuffer();
	       //获取回笼金额
	        sql.append("  /*dialect*/ update t_bdc_sherevbillentry set fisCS=null where fid in( ");
	        sql.append(" \n select                                                                         "); 
	    	sql.append(" \n        entry.fid                                                            ");    
	    	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
	    	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
	    	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
	    	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
	    	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
	    	sql.append(" \n       and md.fname_l2 !='面积补差款' ");
	    	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
	    	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
	    	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('QRNullify','ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
	    	sql.append(" \n       and  entry.fisCS ="+cs+")");
	    	
	    	builder.appendSql(sql.toString());
		    builder.execute();
	}

	@Override
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._audit(ctx, billId);
		CommissionSettlementBillInfo info=CommissionSettlementBillFactory.getLocalInstance(ctx).getCommissionSettlementBillInfo(new ObjectUuidPK(billId));
		String sellProjectId=info.getSellProject().getId().toString();
	       int year = info.getYear();
	       int month = info.getMonth();
	       String cs=String.valueOf(year)+String.valueOf(month);
	       Date t=FDCDateHelper.stringToDate(year+"-"+month+"-01");
		    Date startDate = FDCDateHelper.getFirstDayOfMonth(t);
	    	Date endDate =  FDCDateHelper.getLastDayOfMonth(t);
	       
	       
	       FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	       StringBuffer sql = new StringBuffer();
	       sql = new StringBuffer();
	       //获取回笼金额
	        sql.append("  /*dialect*/ update t_bdc_sherevbillentry set fisCS="+cs+" where fid in( ");
	        sql.append(" \n select                                                                         "); 
	    	sql.append(" \n        entry.fid                                                            ");    
	    	sql.append(" \n   from T_BDC_SHERevBill revBill                                                     ");
	    	sql.append(" \n       left join T_BDC_SHERevBillEntry entry  on revBill.fid = entry.fparentid     ");
	    	sql.append(" \n       left join t_she_moneyDefine md  on md.fid = entry.fmoneyDefineId                    ");
	    	sql.append(" \n       where revBill.fstate in ('4AUDITTED')                 ");
	    	sql.append(" \n       and md.fmoneyType in ('FisrtAmount', 'HouseAmount', 'LoanAmount', 'AccFundAmount') ");
	    	sql.append(" \n       and md.fname_l2 !='面积补差款' ");
	    	sql.append(" \n       and revBill.fsellprojectid  ='"+sellProjectId+"'" );
	    	sql.append(" \n       and revBill.fbizDate <to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
	    	sql.append(" \n       and EXISTS (select t.ftransactionid from t_she_signManage t where t.ftransactionid=revBill.frelateTransId and t.fbizState in ('QRNullify','ChangeNameAuditing', 'QuitRoomAuditing', 'ChangeRoomAuditing', 'SignAudit')) ");
	    	sql.append(" \n       and  entry.fisCS is null)");
	    	
	    	builder.appendSql(sql.toString());
		    builder.execute();
	}

	@Override
	protected Map _calcQuit(Context ctx, Map paramMap) throws BOSException,
			EASBizException {
		Map resultMap = new HashMap();
    	
    	// TODO Auto-generated method stub
    	String sellProjectId=paramMap.get("sellProject").toString();
    	int year = Integer.parseInt(paramMap.get("year")+"");
        int month = Integer.parseInt(paramMap.get("month")+"");
        
        Date t=FDCDateHelper.stringToDate(year+"-"+month+"-01");
	    Date startDate = FDCDateHelper.getFirstDayOfMonth(t);
    	Date endDate =  FDCDateHelper.getLastDayOfMonth(t);
        
    	
        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
        StringBuffer sql = new StringBuffer();
        //获取签约金额
        sql.append(" /*dialect*/ select t.fid id,t.fnumber cnumber,t.fstate state,t.fchangeDate changeDate,r.fname_l2 srcRoom,t.fbizState bizState,t.fbusAdscriptionDate busAdscriptionDate,");
        sql.append(" t.fsrcCustomerNames srcCustomerNames,t.fsrcCustomerPhone srcCustomerPhone,t.fbizType bizType,cr.fname_l2 changeReason,t.fdetails details,c.fname_l2 creator,t.fcreateTime createTime,");
        sql.append(" a.fname_l2 auditor,t.fauditTime auditTime,t1.saleMan saleMan,t1.amount amt from t_she_changeManage t");
        sql.append(" left join t_she_room r on r.fid=t.fsrcRoomId left join T_SHE_ChangeReason cr on cr.fid=t.fchangeReasonId left join t_pm_user c on c.fid=t.fcreatorId left join t_pm_user a on a.fid=t.fauditorid");
        sql.append(" left join (select u.fname_l2 saleMan,sign.fid id,sign.fcontractTotalAmount+nvl(sign.FAreaCompensate, 0)+nvl(sign.FPlanningCompensate, 0)+nvl(sign.FCashSalesCompensate, 0)+nvl(sign.FPlanningCompensate, 0)+nvl(sign.FCashSalesCompensate, 0) amount from t_she_signManage sign left join T_SHE_SignSaleManEntry e on e.fheadid=sign.fid left join t_pm_user u on u.fid=e.fuserid");    
        sql.append(" union select u.fname_l2 saleMan,pur.fid id,pur.fcontractTotalAmount amount from t_she_purchaseManage pur left join T_SHE_PurSaleManEntry e on e.fheadid=pur.fid left join t_pm_user u on u.fid=e.fuserid) t1 on t1.id=t.fsrcId");    
        sql.append(" where t.fstate in ('4AUDITTED') and t.fsellprojectid  ='"+sellProjectId+"' and t.fauditTime>=to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(startDate))+"','yyyy-mm-dd hh24:mi:ss')" );
        sql.append(" and  t.fauditTime<to_date('"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(endDate))+"','yyyy-mm-dd hh24:mi:ss')" );
       
        builder.appendSql(sql.toString());
        
        IRowSet rs  = builder.executeQuery();
        List list=new ArrayList();
        try {
    		while(rs.next()){
    			Map rsMap=new HashMap();
    			rsMap.put("id", rs.getString("id"));
    			rsMap.put("number", rs.getString("cnumber"));
    			rsMap.put("state", FDCBillStateEnum.getEnum(rs.getString("state")).getAlias());
    			rsMap.put("changeDate", rs.getString("changeDate"));
    			rsMap.put("srcRoom", rs.getString("srcRoom"));
    			rsMap.put("bizState", TransactionStateEnum.getEnum(rs.getString("bizState")).getAlias());
    			rsMap.put("busAdscriptionDate", rs.getString("busAdscriptionDate"));
    			rsMap.put("srcCustomerNames", rs.getString("srcCustomerNames"));
    			rsMap.put("srcCustomerPhone", rs.getString("srcCustomerPhone"));
    			rsMap.put("bizType", ChangeBizTypeEnum.getEnum(rs.getString("bizType")).getAlias());
    			rsMap.put("changeReason", rs.getString("changeReason"));
    			rsMap.put("details", rs.getString("details"));
    			rsMap.put("creator", rs.getString("creator"));
    			rsMap.put("createTime", rs.getString("createTime"));
    			rsMap.put("auditor", rs.getString("auditor"));
    			rsMap.put("auditTime", rs.getString("auditTime"));
    			rsMap.put("saleMan", rs.getString("saleMan"));
    			rsMap.put("amt", rs.getString("amt"));
    			
    			list.add(rsMap);
    	   }
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	resultMap.put("data", list);
    	return resultMap;
	}
    
}