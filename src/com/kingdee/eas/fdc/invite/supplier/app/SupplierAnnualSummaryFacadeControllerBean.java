package com.kingdee.eas.fdc.invite.supplier.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.db.TempTablePool;
import com.kingdee.bos.db.TempTableUtil;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.purchasebaseinfomation.PurchaseCategoryInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteTypeCollection;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.WinRptInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierBidInfo;
import com.kingdee.jdbc.rowset.IRowSet;


public class SupplierAnnualSummaryFacadeControllerBean extends AbstractSupplierAnnualSummaryFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.SupplierAnnualSummaryFacadeControllerBean");
    
    
    
    protected Map _getSupplierWinInfo(Context ctx, Map param)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	Map<String,Object> result = new HashMap<String, Object>();
    	
    	//获取所有招标立项的采购大类、采购类别，key为inviteProjectId
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select count(prj.fid) cnt,pc.fid pcid,pc.fname_l2 pcname,pc.fnumber pcnumber,it.fid itid,it.fname_l2 itname,it.fnumber itnumber  "+
    					  "	from t_inv_inviteproject prj left outer join  T_PUR_PurchaseCategory pc on prj.fpurchasecategoryid = pc.fid "+
                          " left outer join t_inv_invitetype it on it.fid = prj.finvitetypeid where prj.fstate = '4AUDITTED'"+
                          " group by pc.fid,pc.fname_l2,pc.fnumber,it.fid,it.fname_l2,it.fnumber");
        IRowSet rs = builder.executeQuery();
        WinRptInfo prj  = null;
        PurchaseCategoryInfo pc = null;
        InviteTypeInfo it = null;
        String key = null;
        Map<String,List<String>> inviteProjectMap = new HashMap<String,List<String>>();
        List<WinRptInfo> inviteProjectList = new ArrayList<WinRptInfo>();
    	try {
			while(rs.next()){
				prj = new WinRptInfo();
				
				pc = new PurchaseCategoryInfo();
				pc.setId(BOSUuid.read(rs.getString("pcid")));
                pc.setName(rs.getString("pcname"));
                pc.setNumber(rs.getString("pcnumber"));
                prj.setPc(pc);
                
                it = new InviteTypeInfo();
                it.setId(BOSUuid.read(rs.getString("itid")));
                it.setName(rs.getString("itname"));
                it.setNumber(rs.getString("itnumber"));
                prj.setIt(it);
                
                prj.setCount(rs.getInt("cnt"));
                
                inviteProjectList.add(prj);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("inviteProjectList",inviteProjectList );
		
		
		//按照采购大类和采购类别进行分组，查询中标金额和中标次数
		Map<String,BigDecimal[]> bidAmtMap = new HashMap<String,BigDecimal[]>();
		String sql = " select count(*) cnt,sum(fbidamount) bidAmt,finvitetypeid,fpurchasecategoryid from(                      " +                                                     
				" select dare.fbidamount,prj.finvitetypeid,prj.fpurchasecategoryid                                             " +
				" from T_INV_TenderAccepterResultE  dare inner join T_INV_TenderAccepterResult dar on dare.fparentid = dar.fid " +
				"      left outer join t_inv_inviteproject prj on prj.fid = dar.finviteprojectid                               " +
				"      left outer join t_inv_invitetype it on it.fid = prj.finvitetypeid                                       " +
				"      left outer join t_pur_purchasecategory pc on pc.fid = prj.fpurchasecategoryid                           " +
				"  where dar.fstate = '4AUDITTED' and dare.fhit =1                                                             " +
				" union  all                                                                                                   " +
				" select dare.fbidamount,prj.finvitetypeid,prj.fpurchasecategoryid                                             " +
				" from t_inv_directaccepterresulte  dare inner join t_inv_directaccepterresult dar on dare.fparentid = dar.fid " +
				"      left outer join t_inv_inviteproject prj on prj.fid = dar.finviteprojectid                               " +
				"      left outer join t_inv_invitetype it on it.fid = prj.finvitetypeid                                       " +
				"      left outer join t_pur_purchasecategory pc on pc.fid = prj.fpurchasecategoryid where dar.fstate = '4AUDITTED' and dare.fhit =1)    " +
				" group by fpurchasecategoryid,finvitetypeid                                                                   " ;
		builder.clear();
		builder.appendSql(sql);
		rs =builder.executeQuery();
		try {
			while(rs.next()){
				key = rs.getString("fpurchasecategoryid")+"_"+rs.getString("finvitetypeid");
				bidAmtMap.put(key, new BigDecimal[]{rs.getBigDecimal("bidAmt"),rs.getBigDecimal("cnt")});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result.put("bidAmtMap",bidAmtMap);
		
		
		//获取采购方式为邀请招标的中标信息
		Map<String,BigDecimal []> selectedBidMap = new HashMap<String, BigDecimal []>();
		
		sql = " select sum(fbidamount) bidAmt,count(*) cnt, finvitetypeid, fpurchasecategoryid                                 "+
		"          from T_INV_TenderAccepterResultE dare                                                     "+
		"         inner join T_INV_TenderAccepterResult dar                                                  "+
		"            on dare.fparentid = dar.fid                                                             "+
		"          left outer join t_inv_inviteproject prj                                                   "+
		"            on prj.fid = dar.finviteprojectid                                                       "+
		"          left outer join t_inv_invitetype it                                                       "+
		"            on it.fid = prj.finvitetypeid                                                           "+
		"          left outer join t_pur_purchasecategory pc                                                 "+
		"            on pc.fid = prj.fpurchasecategoryid                                                     "+
		"          left outer join T_INV_InviteForm ipm                                                      "+
		"            on ipm.fid = prj.FInviteFormID                                                          "+
		"         where dar.fstate = '4AUDITTED'                                                             "+
		"           and dare.fhit = 1                                                                        "+
		"           and ipm.ftype='1INVITETYPE'                                                            "+
		"   group by fpurchasecategoryid,finvitetypeid                                                       ";

		builder.clear();
		builder.appendSql(sql);
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				key = rs.getString("fpurchasecategoryid")+"_"+rs.getString("finvitetypeid");
				selectedBidMap.put(key, new BigDecimal[]{ rs.getBigDecimal("bidAmt"),rs.getBigDecimal("cnt")});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("selectedBidMap",selectedBidMap);
		
		//获取采购方式为议标的中标信息
		Map<String,BigDecimal []> inquiryBidMap = new HashMap<String, BigDecimal []>();
		
		sql = " select sum(fbidamount) bidAmt, count(*) cnt,finvitetypeid, fpurchasecategoryid,ipm.fid  "+
				" from T_INV_TenderAccepterResultE dare "+
				" inner join T_INV_TenderAccepterResult dar "+
				" on dare.fparentid = dar.fid       left outer join t_inv_inviteproject prj   on prj.fid = dar.finviteprojectid "+
                " left outer join t_inv_invitetype it    on it.fid = prj.finvitetypeid "+
                " left outer join t_pur_purchasecategory pc  on pc.fid = prj.fpurchasecategoryid "+
                " left outer join T_INV_InviteForm ipm   on ipm.fid = prj.FInviteFormID  "+
                " where dar.fstate = '4AUDITTED'   and dare.fhit = 1 and ipm.ftype='2TENDERDISCUSSION'    group by fpurchasecategoryid,finvitetypeid,ipm.fid ";
		builder.clear();
		builder.appendSql(sql);
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				key = rs.getString("fpurchasecategoryid")+"_"+rs.getString("finvitetypeid");
				inquiryBidMap.put(key, new BigDecimal[]{ rs.getBigDecimal("bidAmt"),rs.getBigDecimal("cnt")});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("inquiryBidMap",inquiryBidMap);
		
		
		//获取采购方式为直接委托的中标信息
		Map<String,BigDecimal []> uniqueBidMap = new HashMap<String, BigDecimal []>();
		
		sql = " select sum(fbidamount) bidAmt, count(*) cnt,finvitetypeid, fpurchasecategoryid  "+
				" from t_inv_directaccepterresulte dare "+
				" inner join t_inv_directaccepterresult dar "+
				" on dare.fparentid = dar.fid       left outer join t_inv_inviteproject prj   on prj.fid = dar.finviteprojectid "+
                " left outer join t_inv_invitetype it    on it.fid = prj.finvitetypeid "+
                " left outer join t_pur_purchasecategory pc  on pc.fid = prj.fpurchasecategoryid "+
                " where dar.fstate = '4AUDITTED'   and dare.fhit = 1     group by fpurchasecategoryid,finvitetypeid ";
		builder.clear();
		builder.appendSql(sql);
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				key = rs.getString("fpurchasecategoryid")+"_"+rs.getString("finvitetypeid");
				uniqueBidMap.put(key, new BigDecimal[]{ rs.getBigDecimal("bidAmt"),rs.getBigDecimal("cnt")});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("uniqueBidMap",uniqueBidMap);
		
		//获取招标方式采购的供应商投标次数
		Map<String,BigDecimal[]> inviteTimesMap = new HashMap<String, BigDecimal[]>();
		sql=" /*dialect*/ select fpurchasecategoryid,finvitetypeid, sum(firsttimes) firsttimes,sum(secondtimes) secondtimes,sum(thirdtimes) thirdtimes from(                                         "+
		" select fpurchasecategoryid,finvitetypeid,fsupplierid,decode(times,1,1,2,0,3,0) firsttimes,decode(times,1,0,2,1,3,0) secondtimes,decode(times,1,0,2,0,3,1) thirdtimes from ("+
		" select fpurchasecategoryid, finvitetypeid, sir.fsupplierid ,max(ftimes) times                                                                                              "+
		" from t_inv_supplierinviterecord sir inner join (                                                                                                                           "+
		" select distinct fsupplierid                                                                                                                                                "+
		" from t_inv_tenderaccepterresulte dare                                                                                                                                      "+
		"      inner join t_inv_tenderaccepterresult dar on dare.fparentid = dar.fid                                                                                                 "+
		"      left outer join t_inv_inviteproject prj on prj.fid = dar.finviteprojectid                                                                                             "+
		"      left outer join t_inv_inviteform form on form.fid = prj.finviteformid                                                                                                 "+
		" where form.ftype = '1INVITETYPE' and dar.fstate = '4AUDITTED' and dare.fhit =1) s  on sir.fsupplierid = s.fsupplierid                                                      "+
		"   left outer join t_inv_inviteproject prj on prj.fid = sir.finviteprojectid                                                                                                "+
		"  left outer join t_inv_invitetype it on it.fid = prj.finvitetypeid                                                                                                         "+
		"  left outer join t_pur_purchasecategory pc on pc.fid = prj.fpurchasecategoryid                                                                                             "+
		" where sir.fstate = '4AUDITTED'                                                                                                                                             "+
		" group by fpurchasecategoryid, finvitetypeid,sir.fsupplierid))                                                                                                              "+
		" group by fpurchasecategoryid, finvitetypeid                                                                                                                                ";

		builder.clear();
		builder.appendSql(sql);
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				key = rs.getString("fpurchasecategoryid")+"_"+rs.getString("finvitetypeid");
				inviteTimesMap.put(key, new BigDecimal[]{rs.getBigDecimal("firsttimes"),rs.getBigDecimal("secondtimes"),rs.getBigDecimal("thirdtimes")});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("inviteTimesMap", inviteTimesMap);
		
		Map<String,BigDecimal[]>  lowestBidMap = new HashMap<String, BigDecimal[]>();
		sql = " select count(*) cnt,pc.fid pcid,it.fid itid,sum(dare.fbidamount) amt                                       "+
		" from t_inv_tenderaccepterresulte dare                                                                  "+
		"      inner join t_inv_tenderaccepterresult dar on dare.fparentid = dar.fid                             "+
		"       left outer join t_inv_inviteproject prj on prj.fid = dar.finviteprojectid                        "+
		"       left outer join t_inv_inviteform form on form.fid = prj.finviteformid                            "+
		"       left outer join t_inv_invitetype it on it.fid = prj.finvitetypeid                                "+
		"       left outer join t_pur_purchasecategory pc on pc.fid = prj.fpurchasecategoryid                    "+
		" where dare.fhit = 1 and dare.fislowest = 1 and dar.fstate = '4AUDITTED' and form.ftype = '1INVITETYPE' "+
		" group by pc.fid,it.fid                                                                                 ";
		
		builder.clear();
		builder.appendSql(sql);
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				key = rs.getString("pcid")+"_"+rs.getString("itid");
				lowestBidMap.put(key, new BigDecimal[]{rs.getBigDecimal("cnt"),rs.getBigDecimal("amt")});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("lowestBidMap", lowestBidMap);
		
		//首次签约单位
		Map<String,BigDecimal[]> firstSignMap = new HashMap<String, BigDecimal[]>();
		sql = "/*dialect*/ with supp as                                                                                                "+ 
		" (select fdcsupplier.fsyssupplierid supplierid,prj.finvitetypeid itid,prj.fpurchasecategoryid pcid,dare.fbidamount "+ 
		" from t_inv_tenderaccepterresulte dare inner join t_inv_tenderaccepterresult dar on dare.fparentid = dar.fid       "+ 
		"      left outer join t_inv_inviteproject prj on prj.fid = dar.finviteprojectid                                    "+ 
		"      left outer join t_inv_inviteform form on form.fid = prj.finviteformid                                        "+ 
		"      left outer join t_fdc_supplierstock fdcsupplier on fdcsupplier.fid = dare.fsupplierid                        "+ 
		" where form.ftype = '1INVITETYPE' and dare.fhit = 1 and dar.fstate= '4AUDITTED')                                   "+ 
		" select sum(cn) cnt,sum(amount) amt,itid,pcid from (select s.itid,s.pcid,count(*) cn ,sum(s.fbidamount) amount     "+ 
		" from t_con_contractbill c inner join supp s on c.fpartbid = supplierid  and c.fstate='4AUDITTED'                  "+ 
		" group by s.itid,s.pcid,supplierid                                                                                 "+ 
		" having count(*) = 1) group by itid,pcid                                                                           " ;
		
		builder.clear();
		builder.appendSql(sql);
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				key = rs.getString("pcid")+"_"+rs.getString("itid");
				firstSignMap.put(key, new BigDecimal[]{rs.getBigDecimal("cnt"),rs.getBigDecimal("amt")});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("firstSignMap", firstSignMap);
		
		//询价比选供应商首次中标信息
		
		Map<String,BigDecimal[]> firstwinbyinvite = new HashMap<String, BigDecimal[]>();
		sql = " select sum(amt) amt,sum(cnt) cnt,finvitetypeid itid,fpurchasecategoryid pcid from(                           "+
		" select sum(fbidamount) amt,count(*) cnt,dare.fsupplierid,prj.finvitetypeid,prj.fpurchasecategoryid         "+
		" from t_inv_tenderaccepterresulte dare inner join t_inv_tenderaccepterresult dar on dare.fparentid = dar.fid"+
		" left outer join t_inv_inviteproject prj on prj.fid = dar.finviteprojectid                                  "+
		" left outer join t_inv_inviteform form on form.fid = prj.finviteformid                                      "+
		" where dare.fhit = 1 and dar.fstate = '4AUDITTED' and form.ftype = '2TENDERDISCUSSION'                      "+
		" group by  finvitetypeid,fpurchasecategoryid,fsupplierid having count(*) = 1)                               "+
		" group by finvitetypeid,fpurchasecategoryid                                                                 ";
		builder.clear();
		builder.appendSql(sql);
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				key = rs.getString("pcid")+"_"+rs.getString("itid");
				firstwinbyinvite.put(key, new BigDecimal[]{rs.getBigDecimal("cnt"),rs.getBigDecimal("amt")});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		result.put("firstwinbyinvite", firstwinbyinvite);
		
		Map<String,BigDecimal[]> firstwinbyunique = new HashMap<String, BigDecimal[]>();
		sql = " select sum(amt) amt,sum(cnt) cnt,finvitetypeid itid,fpurchasecategoryid pcid from(                           "+
		" select sum(fbidamount) amt,count(*) cnt,dare.fsupplierid,prj.finvitetypeid,prj.fpurchasecategoryid         "+
		" from t_inv_directaccepterresulte dare inner join t_inv_directaccepterresult dar on dare.fparentid = dar.fid"+
		" left outer join t_inv_inviteproject prj on prj.fid = dar.finviteprojectid                                  "+
		" left outer join t_inv_inviteform form on form.fid = prj.finviteformid                                      "+
		" where dare.fhit = 1 and dar.fstate = '4AUDITTED' and form.ftype = '2TENDERDISCUSSION'                      "+
		" group by  finvitetypeid,fpurchasecategoryid,fsupplierid having count(*) = 1)                               "+
		" group by finvitetypeid,fpurchasecategoryid                                                                 ";
		builder.clear();
		builder.appendSql(sql);
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				key = rs.getString("pcid")+"_"+rs.getString("itid");
				firstwinbyunique.put(key, new BigDecimal[]{rs.getBigDecimal("cnt"),rs.getBigDecimal("amt")});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		result.put("firstwinbyunique", firstwinbyunique);
		
		
    	return result;
    }
    
    
    /**
     * 根据传入的采购类别进行过滤，取出所有的供应商投标信息
     */
    protected Map _getSupplierBidInfo(Context ctx, Map param)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	Map resultMap = new HashMap();
    	String inviteTypeId = null;
    	if(param.get("inviteTypeId")  != null ){
    		inviteTypeId = param.get("inviteTypeId")+"";
    	}
    	
    	//查询出所有的采购分类 InviteType
    	EntityViewInfo view = new EntityViewInfo();
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("id");
    	sic.add("name");
    	sic.add("number");
    	sic.add("longNumber");
    	sic.add("level");
    	sic.add("isLeaf");
    	sic.add("parent.id");
    	sic.add("parent.name");
    	sic.add("parent.number");
    	sic.add("parent.longNumber");
    	sic.add("isEnabled");
    	view.setSelector(sic);
    	FilterInfo filter =  new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	if(!StringUtils.isEmpty(inviteTypeId)){
    		filter.getFilterItems().add(new FilterItemInfo("id",inviteTypeId));
    	}
    	SorterItemCollection sort = new SorterItemCollection();
    	sort.add(new SorterItemInfo("longNumber"));
    	view.setSorter(sort);
    	view.setFilter(filter);
    	InviteTypeCollection inviteTypes = InviteTypeFactory.getLocalInstance(ctx).getInviteTypeCollection(view);
    	resultMap.put("inviteType", inviteTypes);
    	
    	if(inviteTypes == null || inviteTypes.isEmpty()){
    		return null;
    	}
    	
    	//获取各个采购类别中的招标数量
    	int size = inviteTypes.size();
    	Map<String,BigDecimal> inviteCount =new HashMap<String, BigDecimal>();
    	String [] strIds = new String[size] ;
    	for(int i = 0;i < size; i++){
    		strIds[i] = inviteTypes.get(i).getId()+"";
    	}
    	
    	IRowSet rs = null;
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select count(*) cnt,finvitetypeid from t_inv_inviteproject  t where "+ FDCSQLBuilder.getInSql("t.finvitetypeid", strIds)+" and t.fstate = '4AUDITTED' group by finvitetypeid");
    	rs = builder.executeQuery();
    	
    	
    	try {
			while(rs.next()){
				inviteCount.put(rs.getString("finvitetypeid"),rs.getBigDecimal("cnt"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		resultMap.put("inviteCount", inviteCount);

		String sql =  " select s.finvitetypeid, sum(e.fbidamount) amt    "+        
		              "		          from t_inv_directaccepterresulte e           "+        
		              "		          join t_inv_directaccepterresult d            "+        
		              "		            on e.fparentid = d.fid                     "+        
		              "		          left outer join t_inv_inviteproject p        "+        
		              "		            on p.fid = d.finviteprojectid              "+        
		              "		          left outer join t_Fdc_supplierstock s        "+        
		              "		            on s.fid = e.fsupplierid              "+        
		              "		         where "+FDCSQLBuilder.getInSql("s.finvitetypeid", strIds)+" and e.fhit= 1 and d.fstate = '4AUDITTED'"+        
		              "		         group by s.finvitetypeid                      ";        

		Map<String,BigDecimal> directAmt =new HashMap<String, BigDecimal>();
		builder.clear();
		builder.appendSql(sql);
    	rs = builder.executeQuery();
    	
    	
    	try {
			while(rs.next()){
				directAmt.put(rs.getString("finvitetypeid"),rs.getBigDecimal("amt"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		resultMap.put("directAmt", directAmt);
		//直接委托金额
		 sql =  "		        select s.finvitetypeid, sum(e.fbidamount) amt  "+        
		"		          from T_INV_TenderAccepterResultE e           "+        
		"		          join T_INV_TenderAccepterResult d            "+        
		"		            on e.fparentid = d.fid                     "+        
		"		          left outer join t_inv_inviteproject p        "+        
		"		            on p.fid = d.finviteprojectid              "+        
		"		          left outer join t_Fdc_supplierstock s        "+        
		"		            on s.fid = e.fsupplierid              "+        
		"		         where "+FDCSQLBuilder.getInSql("s.finvitetypeid", strIds)+"  and e.fhit = 1"+        
		"		           and d.fstate = '4AUDITTED'                  "+        
		"		         group by s.finvitetypeid                 ";       
		
		Map<String,BigDecimal> inviteAmt =new HashMap<String, BigDecimal>();
		builder.clear();
		builder.appendSql(sql);
		rs = builder.executeQuery();
		
		
		try {
			while(rs.next()){
				inviteAmt.put(rs.getString("finvitetypeid"),rs.getBigDecimal("amt"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		resultMap.put("inviteAmt", inviteAmt);

		//取出关联的供应商类型
		TempTablePool ttp = TempTablePool.getInstance(ctx);
		String tempTableName = null;
		try {
			String createTableSql = "CREATE TABLE TEMP_SUPPLIER_SCORE(       "+
			                        "      FINVITETYPEID VARCHAR(44),        "+
			                        "      FINVITETYPENAME VARCHAR(255),        "+
			                        "      FSUPLLIERNAME NVARCHAR(255),      "+
			                        "      FSUPPLIERID  VARCHAR(44),         "+
			                        "      FYEARSCORE NUMERIC(28,2),          "+
			                        "      FEXECUTESCORE NUMERIC(28,2),       "+
			                        "      FCHECKSCORE NUMERIC(28,2),         "+
			                        "      FSCORE NUMERIC(28,2),              "+
			                        "      FGRADE VARCHAR(100),              "+
			                        "      FQUALIFICATIONCOUNT NUMERIC(28,2),  "+
			                        "      FHIGH NUMERIC(28,2),  "+
			                        "      FNONE NUMERIC(28,2),  "+
			                        "      FILLEGAL NUMERIC(28,2),  "+
			                        "      FUNQUALIFIED NUMERIC(28,2),  "+
			                        "      FGIVEUP NUMERIC(28,2),  "+
			                        "      FLOWEST NUMERIC(28,2),  "+
			                        "      FWINAMT NUMERIC(28,2),  "+
			                        "      FDIRECTAMT NUMERIC(28,2),  "+
			                        "      FDIRECTCOUNT NUMERIC(28,2),  "+
			                        "      FWINCOUNT NUMERIC(28,2)) ";

			
			tempTableName = ttp.createTempTable(createTableSql);
			
			//更新供应商信息进去
			String insertSupplierInfo = "insert into " +tempTableName+"(FSUPPLIERID,FSUPLLIERNAME,FINVITETYPEID,FGRADE,FINVITETYPENAME) SELECT DISTINCT IR.FSUPPLIERID, S.FNAME_L2, S.FINVITETYPEID, G.FNAME_L2,IT.FNAME_L2"+
										" FROM T_INV_SUPPLIERINVITERECORD IR"+
										" LEFT OUTER JOIN T_FDC_SUPPLIERSTOCK S"+
										" ON IR.FSUPPLIERID = S.FID "+
										" LEFT OUTER JOIN T_INV_INVITETYPE IT ON IT.FID = S.FINVITETYPEID"+
										" LEFT OUTER JOIN T_FDC_GRADESETUP G"+
										" ON S.FGRADEID = G.FID"+
										" WHERE IR.FSTATE = '4AUDITTED'"+(StringUtils.isEmpty(inviteTypeId)?"":" and S.FINVITETYPEID = '"+inviteTypeId+"'");
 			builder.clear();
 			builder.appendSql(insertSupplierInfo);
 			builder.executeUpdate();
 			
 			//更新直接委托的供应商信息
 			
 			
 			insertSupplierInfo = "insert into " +tempTableName+"(FSUPPLIERID,FSUPLLIERNAME,FINVITETYPEID,FGRADE,FINVITETYPENAME) "+
			" select distinct s.fid,s.fname_l2,it.fid,g.fname_l2,it.fname_l2 "+
 			" from t_inv_directaccepterresulte e  "+
 			"     inner join t_inv_directaccepterresult d on d.fid = e.fparentid "+
 			"     left outer join t_fdc_supplierstock s on s.fid = e.fsupplierid "+
 			"     left outer join t_inv_invitetype it on it.fid = s.finvitetypeid "+
 			"     left outer join t_fdc_gradesetup g on g.fid = s.fgradeid "+
 			" where e.fhit = 1 and d.fstate = '4AUDITTED'"+(StringUtils.isEmpty(inviteTypeId)?"":" and s.FINVITETYPEID = '"+inviteTypeId+"'");
 			builder.clear();
 			builder.appendSql(insertSupplierInfo);
 			builder.executeUpdate();
 			
 			
 			
 			
 			//更新供应商考察得分信息
 			
 			
 			
 			
 			String updateCheckScore = " /*dialect*/ MERGE INTO " +tempTableName+" T USING (                                                                                         "+
 			" SELECT SCORE,FSUPPLIERID                                                                                         "+
 			"  FROM (SELECT NVL(ENTRY.FSCORE, 0) SCORE,                                                                       "+
 			"               G.FSUPPLIERID,                                                                                    "+
 			"               ROW_NUMBER() OVER(PARTITION BY FSUPPLIERID, FEVALUATIONTYPEID ORDER BY G.FLASTUPDATETIME DESC) RN,"+
 			"               ET.FNAME_L2                                                                                       "+
 			"          FROM T_GYS_SUPPLIERREVIEWGENTRY ENTRY                                                                  "+
 			"          JOIN T_GYS_SUPPLIERREVIEWGATHER G                                                                      "+
 			"            ON G.FID = ENTRY.FHEADID                                                                             "+
 			"          LEFT OUTER JOIN T_GYS_SUPPLIEREVALUATIONTYPE ET                                                        "+
 			"            ON ET.FID = G.FEVALUATIONTYPEID                                                                      "+
 			"         WHERE ET.FNUMBER = '002'                                                                                "+
 			"           AND G.FSTATE = '4AUDITTED')                                                                           "+
 			"  WHERE RN = 1                                                                                                    "+
 			" ) S ON (S.FSUPPLIERID = T.FSUPPLIERID)                                                                           "+
 			" WHEN MATCHED THEN UPDATE SET FCHECKSCORE = SCORE                                                                 ";
                                                             

			
		   builder.clear();
		   builder.appendSql(updateCheckScore);
		   builder.executeUpdate();
		   
			//更新供应商履约得分信息
			
			String updateExecute = " /*dialect*/ MERGE INTO " +tempTableName+" T USING (         \n                              "+
 			" SELECT SCORE,FSUPPLIERID                                                                                         \n"+
 			"  FROM (SELECT NVL(ENTRY.FSCORE, 0) SCORE,                                                                       \n"+
 			"               G.FSUPPLIERID,                                                                                    \n"+
 			"               ROW_NUMBER() OVER(PARTITION BY FSUPPLIERID, FEVALUATIONTYPEID ORDER BY G.FLASTUPDATETIME DESC) RN,\n"+
 			"               ET.FNAME_L2                                                                                       \n"+
 			"          FROM T_GYS_SUPPLIERREVIEWGENTRY ENTRY                                                                  \n"+
 			"          JOIN T_GYS_SUPPLIERREVIEWGATHER G                                                                     \n "+
 			"            ON G.FID = ENTRY.FHEADID                                                                            \n "+
 			"          LEFT OUTER JOIN T_GYS_SUPPLIEREVALUATIONTYPE ET                                                       \n "+
 			"            ON ET.FID = G.FEVALUATIONTYPEID                                                                    \n  "+
 			"         WHERE ET.FNUMBER = '005'                                                                                \n"+
 			"           AND G.FSTATE = '4AUDITTED')                                                                          \n "+
 			"  WHERE RN = 1                                                                                                  \n  "+
 			" ) S ON (S.FSUPPLIERID = T.FSUPPLIERID)                                                                         \n  "+
 			"  WHEN MATCHED THEN UPDATE SET FEXECUTESCORE = SCORE                                                                 ";

			
		   builder.clear();
		   builder.appendSql(updateExecute);
		   builder.executeUpdate();
		   //更新供应商年度得分信息
		   
		   
		   
		   String updateYearScore = " /*dialect*/ MERGE INTO " +tempTableName+" T USING (                                       "+
 			" SELECT SCORE,FSUPPLIERID                                                                                         "+
 			"  FROM (SELECT NVL(ENTRY.FSCORE, 0) SCORE,                                                                       "+
 			"               G.FSUPPLIERID,                                                                                    "+
 			"               ROW_NUMBER() OVER(PARTITION BY FSUPPLIERID, FEVALUATIONTYPEID ORDER BY G.FLASTUPDATETIME DESC) RN,"+
 			"               ET.FNAME_L2                                                                                       "+
 			"          FROM T_GYS_SUPPLIERREVIEWGENTRY ENTRY                                                                  "+
 			"          JOIN T_GYS_SUPPLIERREVIEWGATHER G                                                                      "+
 			"            ON G.FID = ENTRY.FHEADID                                                                             "+
 			"          LEFT OUTER JOIN T_GYS_SUPPLIEREVALUATIONTYPE ET                                                        "+
 			"            ON ET.FID = G.FEVALUATIONTYPEID                                                                      "+
 			"         WHERE ET.FNUMBER = '003'                                                                                "+
 			"           AND G.FSTATE = '4AUDITTED')                                                                           "+
 			"  WHERE RN = 1                                                                                                    "+
 			" ) S ON (S.FSUPPLIERID = T.FSUPPLIERID)                                                                           "+
 			" WHEN MATCHED THEN UPDATE SET FYEARSCORE = SCORE                                                                 ";
		   
		   builder.clear();
		   builder.appendSql(updateYearScore);
		   builder.executeUpdate();
		   
		   //取得供应商入围信息信息
		   
		   String updateQualification = "UPDATE "+tempTableName+"  T SET FQUALIFICATIONCOUNT = (   "+
			   " SELECT CNT FROM (                                                 "+
			   " SELECT COUNT(*) CNT, E.FSUPPLIERID                                "+
			   "  FROM T_INV_SUPPLIERQUALIFYENTRY E                               "+
			   "  JOIN T_INV_SUPPLIERQUALIFY Q                                    "+
			   "    ON E.FPARENTID = Q.FID                                        "+
			   " WHERE Q.FSTATE = '4AUDITTED'                                     "+
			   " GROUP BY E.FSUPPLIERID )S  WHERE T.FSUPPLIERID =  S.FSUPPLIERID)  ";
		   
		   builder.clear();
		   builder.appendSql(updateQualification);
		   builder.executeUpdate();
		   
		   //更新投标异常信息(NONE,ILLEGAL,HIGH,UNQUALIFIED,UNQUALIFIED)
		   String updateNoneCount = "UPDATE "+tempTableName+" T SET FNONE = (  SELECT CNT FROM ( SELECT COUNT(*) CNT, R.FSUPPLIERID FROM T_INV_SUPPLIERINVITERECORD R WHERE R.FABNORMALBID= 'NONE' GROUP BY R.FSUPPLIERID) S WHERE S.FSUPPLIERID = T.FSUPPLIERID)";
		   builder.clear();
		   builder.appendSql(updateNoneCount);
		   builder.executeUpdate();
		 
		   String updateILLEGALCount = "UPDATE "+tempTableName+" T SET FILLEGAL = (  SELECT CNT FROM ( SELECT COUNT(*) CNT, R.FSUPPLIERID FROM T_INV_SUPPLIERINVITERECORD R WHERE R.FABNORMALBID= 'ILLEGAL' GROUP BY R.FSUPPLIERID) S WHERE S.FSUPPLIERID = T.FSUPPLIERID)";
		   builder.clear();
		   builder.appendSql(updateILLEGALCount);
		   builder.executeUpdate();
		   
		   String updateHIGHCount = "UPDATE "+tempTableName+" T SET FHIGH = (  SELECT CNT FROM ( SELECT COUNT(*) CNT, R.FSUPPLIERID FROM T_INV_SUPPLIERINVITERECORD R WHERE R.FABNORMALBID= 'HIGH' GROUP BY R.FSUPPLIERID) S WHERE S.FSUPPLIERID = T.FSUPPLIERID)";
		   builder.clear();
		   builder.appendSql(updateHIGHCount);
		   builder.executeUpdate();
		  
		   String updateUNQUALIFIEDCount = "UPDATE "+tempTableName+" T SET FUNQUALIFIED = (  SELECT CNT FROM ( SELECT COUNT(*) CNT, R.FSUPPLIERID FROM T_INV_SUPPLIERINVITERECORD R WHERE R.FABNORMALBID= 'UNQUALIFIED' GROUP BY R.FSUPPLIERID) S WHERE S.FSUPPLIERID = T.FSUPPLIERID)";
		   builder.clear();
		   builder.appendSql(updateUNQUALIFIEDCount);
		   builder.executeUpdate();
		  
		   String updateGIVEUPCount = "UPDATE "+tempTableName+" T SET FGIVEUP = (  SELECT CNT FROM ( SELECT COUNT(*) CNT, R.FSUPPLIERID FROM T_INV_SUPPLIERINVITERECORD R WHERE R.FABNORMALBID= 'GIVEUP' GROUP BY R.FSUPPLIERID) S WHERE S.FSUPPLIERID = T.FSUPPLIERID)";
		   builder.clear();
		   builder.appendSql(updateGIVEUPCount);
		   builder.executeUpdate();
		   
	   
		   String  updateWin = " /*dialect*/ MERGE INTO "+  tempTableName +" T USING (  \n"+
				   " SELECT COUNT(*) CNT, SUM(FBIDAMOUNT) WINAMT,FSUPPLIERID                       \n  "+
				   "                 FROM (   "+
				   "                      SELECT FSUPPLIERID,E.FBIDAMOUNT     \n"+
				   "                         FROM T_INV_TENDERACCEPTERRESULTE E \n"+
				   "                        JOIN T_INV_TENDERACCEPTERRESULT T \n"+
				   "                          ON T.FID = E.FPARENTID          \n"+
				   "                       WHERE E.FHIT = 1                   \n"+
				   "                         AND T.FSTATE = '4AUDITTED')      \n"+
				   "               GROUP BY FSUPPLIERID      \n"+
				" ) I ON (T.FSUPPLIERID = I.FSUPPLIERID) \n"+
				" WHEN MATCHED THEN  UPDATE SET T.FWINCOUNT = I.CNT,T.FWINAMT = I.WINAMT \n";
				  
		   
		   
		   
		   
              //取得供应商中标信息信息
		   
		   
		   builder.clear();
		   builder.appendSql(updateWin);
		   builder.executeUpdate();
		 
		   String  updateDirectAmt = " /*dialect*/ MERGE INTO "+  tempTableName +" T USING (  \n"+
		   " SELECT COUNT(*) CNT, SUM(FBIDAMOUNT) WINAMT,FSUPPLIERID                       \n  "+
		   "                 FROM (SELECT E.FSUPPLIERID,E.FBIDAMOUNT             \n  "+
		   "                       FROM T_INV_DIRECTACCEPTERRESULTE E \n "+
		   "                        JOIN T_INV_DIRECTACCEPTERRESULT T  \n "+
		   "                          ON T.FID = E.FPARENTID          \n"+
		   "                      WHERE E.FHIT = 1                   \n"+
		   "                         AND T.FSTATE = '4AUDITTED'      \n"+
		   "                                                  )      \n"+
		   "               GROUP BY FSUPPLIERID      \n"+
		   " ) I ON (T.FSUPPLIERID = I.FSUPPLIERID) \n"+
		   " WHEN MATCHED THEN  UPDATE SET T.FDIRECTCOUNT = I.CNT,T.FDIRECTAMT = I.WINAMT \n";
		   
		   
		   
		   
		   
		   //取得供应商中标信息信息
		   
		   
		   builder.clear();
		   builder.appendSql(updateDirectAmt);
		   builder.executeUpdate();
		   
		   String updateLowestCount = "UPDATE "+tempTableName+" T SET FLOWEST = (  SELECT CNT FROM ( SELECT COUNT(*) CNT,FSUPPLIERID FROM T_INV_TENDERACCEPTERRESULTE E JOIN T_INV_TENDERACCEPTERRESULT T ON E.FPARENTID = T.FID  WHERE T.FSTATE='4AUDITTED' AND E.FHIT =1 AND E.FISLOWEST =1 GROUP BY FSUPPLIERID ) S WHERE S.FSUPPLIERID = T.FSUPPLIERID)";
		   builder.clear();
		   builder.appendSql(updateLowestCount);
		   builder.executeUpdate();
		   
		   
		   Map<String,List<SupplierBidInfo>> supplierListMap = new HashMap<String, List<SupplierBidInfo>>();
		   builder.clear();
		   builder.appendSql("select * from "+tempTableName);
		   rs = builder.executeQuery();
		   SupplierBidInfo bidInfo = null;
		   List<SupplierBidInfo> list = null;
		   String key = null;
		   while(rs.next()){
			   key = rs.getString("FINVITETYPEID");
			   if(supplierListMap.containsKey(key)){
				   list = supplierListMap.get(key);
				   list.add( mappingRSToSupplier(rs));
			   }else{
				   list = new ArrayList<SupplierBidInfo>();
				   list.add(mappingRSToSupplier(rs));
				   supplierListMap.put(key, list);
			   }
			   
			   
		   }
		   
		   resultMap.put("supplierListMap",supplierListMap);
		   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			ttp.releaseTable(tempTableName);
		}	

    	return resultMap;
    	
    }
    
    
    private SupplierBidInfo mappingRSToSupplier(IRowSet rs) throws SQLException  {
    	SupplierBidInfo bid = new SupplierBidInfo();
        bid.setGiveUpCount(rs.getBigDecimal("FGIVEUP"));
        bid.setGrade(rs.getString("FGRADE"));
        bid.setHighCount(rs.getBigDecimal("FHIGH"));
        bid.setIllegalCount(rs.getBigDecimal("FILLEGAL"));
        bid.setInviteTypeId(rs.getString("FINVITETYPEID"));
        bid.setInviteTypeName(rs.getString("FINVITETYPENAME"));
        bid.setNoneCount(rs.getBigDecimal("FNONE"));
        bid.setQualificationCount(rs.getBigDecimal("FQUALIFICATIONCOUNT"));
        bid.setScore(rs.getBigDecimal("FSCORE"));
        bid.setSupplierName(rs.getString("FSUPLLIERNAME"));
        bid.setUnQualifed(rs.getBigDecimal("FUNQUALIFIED"));
        bid.setWinCount(rs.getBigDecimal("FWINCOUNT"));
        bid.setLowestWinCount(rs.getBigDecimal("FLOWEST"));
        bid.setCheckScore(rs.getBigDecimal("FCHECKSCORE"));
        bid.setYearScore(rs.getBigDecimal("FYEARSCORE"));
        bid.setExecuteScore(rs.getBigDecimal("FEXECUTESCORE"));
        bid.setWinAmt(rs.getBigDecimal("FWINAMT"));
        bid.setDirectAmt(rs.getBigDecimal("FDIRECTAMT"));
        bid.setDirectCount(rs.getBigDecimal("FDIRECTCOUNT"));
		return bid;
	}
	/**
     * 
     * 根据传入的年度参数进行查询
     * 传入参数说明：
     *  approveYear 核准年度
     *  validateYear 入库年度
     * 
     */
    
    protected Map _getSupplierSummaryInfo(Context ctx, Map param)
    		throws BOSException {
    	Map resultMap = new HashMap();
    	
    	Integer approveYear = (Integer) param.get("approveYear");
    	Integer validateYear = (Integer) param.get("validateYear");
    	
    	
    	//查询出所有的采购分类 InviteType
    	EntityViewInfo view = new EntityViewInfo();
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("id");
    	sic.add("name");
    	sic.add("number");
    	sic.add("longNumber");
    	sic.add("level");
    	sic.add("isLeaf");
    	sic.add("parent.id");
    	sic.add("parent.name");
    	sic.add("parent.number");
    	sic.add("parent.longNumber");
    	sic.add("isEnabled");
    	view.setSelector(sic);
    	FilterInfo filter =  new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	SorterItemCollection sort = new SorterItemCollection();
    	sort.add(new SorterItemInfo("longNumber"));
    	view.setSorter(sort);
    	view.setFilter(filter);
    	InviteTypeCollection inviteTypes = InviteTypeFactory.getLocalInstance(ctx).getInviteTypeCollection(view);
    	resultMap.put("inviteType", inviteTypes);
    	
    	//查询出所有供应商等级
    	GradeSetUpCollection grades = GradeSetUpFactory.getLocalInstance(ctx).getGradeSetUpCollection();
    	resultMap.put("grades", grades);    	
    	
    	//查出所有符合条件的供应商ID
    	IRowSet rs = null;
    	String evaluationType = null;
    	Set<String> supplierIDs = new HashSet<String>();
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select fid from T_GYS_SupplierEvaluationType where fnumber = '002'");
    	rs = builder.executeQuery();
    	try {
			while(rs.next()){
				evaluationType = rs.getString(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(evaluationType == null){
			try {
				throw new ContractException(ContractException.WITHMSG,new String[]{"没有定义供应商考察类型的评审类型，请定义此类型。"});
			} catch (ContractException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
    	
    	builder.clear();
    //	builder.appendSql(" /*dialect*/ select fid from t_fdc_supplierstock where fcreatetime  >= to_date('"+approveYear+"-01-01 00:00:00 ','yyyy-mm-dd hh24:mi:ss') and fcreatetime <=to_date('"+approveYear+"-12-31 23:59:59' ,'yyyy-mm-dd hh24:mi:ss') and fgradeid is not null and fstate=3 "+
    /*                      "  INTERSECT"+
                          " select fsupplierid From T_GYS_SupplierReviewGather where faudittime >= to_date('"+validateYear+"-01-01 00:00:00' ,'yyyy-mm-dd hh24:mi:ss') and faudittime <=to_date('"+validateYear+"-12-31 23:59:59 ','yyyy-mm-dd hh24:mi:ss') and fstate='4AUDITTED'" +
                          		" and fispass=1 and fevaluationtypeid='"+evaluationType+"'");
    	
    	rs = builder.executeQuery();
    	logger.error(builder.getTestSql());
    	try {
			while(rs.next()){
				supplierIDs.add(rs.getString(1));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		logger.error("SupplierIDs:"+supplierIDs.size());
		if(supplierIDs.isEmpty()){
			return null;
		}*/
    	
    	//查出各种分类的供应商数量
		String [] strIds = new String[supplierIDs.size()] ;
		supplierIDs.toArray(strIds);
    	builder.clear();
    	//builder.appendSql("select count(*) cnt, finviteTypeid from t_fdc_supplierstock where "+FDCSQLBuilder.getInSql("fid", strIds)+" group by finvitetypeid");
    	builder.appendSql("select count(*) cnt, finviteTypeid from t_fdc_supplierstock where fstate = 3  group by finvitetypeid");
    	logger.error(builder.getTestSql());
    	rs = builder.executeQuery();
    	Map countMap = new HashMap();
    	int totalCount = 0;
    	int count = 0;
    	try {
			while(rs.next()){
				count = rs.getInt("cnt");
				countMap.put(rs.getString("finviteTypeid"),count);
				totalCount += count;
			}
		} catch (SQLException e) {
			logger.error(e);
		}
    
    	
		resultMap.put("totalCount", totalCount);
    	resultMap.put("countMap", countMap);
    	//获取相应等级的供应商
    	Map<String,BigDecimal> gradeCountMap = new HashMap<String, BigDecimal>();
    	builder.clear();
    	//builder.appendSql("select finvitetypeid,fgradeid,count(*) cnt from t_fdc_supplierstock where "+FDCSQLBuilder.getInSql("fid", strIds)+"group by finvitetypeid,fgradeid");
    	builder.appendSql("select finvitetypeid,fgradeid,count(*) cnt from t_fdc_supplierstock where fstate = 3 group by finvitetypeid,fgradeid");
    	logger.error(builder.getTestSql());
    	rs = builder.executeQuery();
    	try {
			while(rs.next()){
				gradeCountMap.put(rs.getString("finvitetypeid")+"_"+rs.getString("fgradeid"), rs.getBigDecimal("cnt"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	resultMap.put("gradeMap", gradeCountMap);
    	//年度核准数量
    	Map<String,BigDecimal> yearedApprovedMap = new HashMap<String, BigDecimal>();
    	builder.clear();
    	builder.appendSql("select finvitetypeid,count(*) cnt"+
    	          " from t_fdc_supplierstock where fcreatetime >= {ts '"+approveYear+"-01-01 00:00:00'} and fcreatetime <={ts '"+approveYear+"-12-31 23:59:59'} and fstate=3 group by finvitetypeid  ");
    	
    	rs = builder.executeQuery();
    	try {
			while(rs.next()){
				yearedApprovedMap.put(rs.getString("finvitetypeid"), rs.getBigDecimal("cnt"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultMap.put("yearedApprovedMap", yearedApprovedMap);
    	
    	//年度入库数量
		Map<String,BigDecimal> yearedValidateMap = new HashMap<String, BigDecimal>();
		builder.clear();
		builder.appendSql("select count(*) cnt, s.finvitetypeid finvitetypeid from T_GYS_SupplierReviewGather gather " +
				" left outer join t_fdc_supplierstock  s on gather.fsupplierid = s.fid  where gather.faudittime >= {ts '"+validateYear+"-01-01 00:00:00'} " +
				" and gather.faudittime <={ts '"+validateYear+"-12-31 23:59:59'} and gather.fstate ='4AUDITTED'" +
				" and gather.fispass=1 and fevaluationtypeid='"+evaluationType+"' group by s.finvitetypeid");
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				yearedValidateMap.put(rs.getString("finvitetypeid"), rs.getBigDecimal("cnt"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	resultMap.put("yearedValidateMap", yearedValidateMap);
    	//年度考察合格率
    	Map<String,BigDecimal> rateOfPassMap = new HashMap<String, BigDecimal>();
    	builder.clear();
    	builder.appendSql("select \n"+ 
    	  " sum(1),sum(notpass),sum(pass),100*round(sum(pass)/sum(1),4) rate,s.finvitetypeid  \n"+
    	"  from \n"+
    	" (select case   when gather.fispass = 0 then  1 else 0 end notpass,\n"+
    	"        case   when gather.fispass = 1 then  1 else 0 end pass,fsupplierid \n"+
    	"  from T_GYS_SupplierReviewGather gather \n"+
    	"  where gather.faudittime >= {ts '"+validateYear+"-01-01 00:00:00' }\n"+
    	"   and gather.faudittime <= {ts '"+validateYear+"-12-31 23:59:59' }\n"+
    	"   and  gather.fstate = '4AUDITTED'\n"+
    	"   and fevaluationtypeid = '"+evaluationType+"') t left outer join t_fdc_supplierstock s on t.fsupplierid = s.fid \n"+
    	" group by s.finvitetypeid ");
    	
    	rs = builder.executeQuery();
		try {
			while(rs.next()){
				rateOfPassMap.put(rs.getString("finvitetypeid"), rs.getBigDecimal("rate"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	resultMap.put("rateOfPassMap", rateOfPassMap);
    	
    	//取出年度新核准的 供应商的入围次数
    	Map<String,BigDecimal> qualifyMap = new HashMap<String, BigDecimal>();
    	
    	builder.clear();
    	builder.appendSql("select count(*) cnt,s.finvitetypeid  finvitetypeid from  t_inv_supplierqualifyentry qualify "+
    					"  left outer join t_Fdc_Supplierstock s on qualify.fsupplierid = s.fid  join t_inv_supplierqualify q on q.fid = qualify.fparentid"+
    					"  where q.fstate='4AUDITTED' and s.fcreatetime >= {ts '"+validateYear+"-01-01 00:00:00'} and s.fcreatetime <= {ts '"+validateYear+"-12-31 23:59:59'}" +
    							"  group by s.finvitetypeid");
    	rs = builder.executeQuery();
    	try {
			while(rs.next()){
				qualifyMap.put(rs.getString("finvitetypeid"), rs.getBigDecimal("cnt"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultMap.put("qualifyMap", qualifyMap);
		Map<String,BigDecimal> inviteMap = new HashMap<String, BigDecimal>();
		
		builder.clear();
		builder.appendSql("select count(*) cnt,s.finvitetypeid  finvitetypeid from  T_INV_SupplierInviteRecord inviterecord "+
				"  left outer join t_Fdc_Supplierstock s on inviterecord.fsupplierid = s.fid "+
				"  where inviterecord.fstate='4AUDITTED' and s.fcreatetime >= {ts '"+validateYear+"-01-01 00:00:00'} and s.fcreatetime <= {ts '"+validateYear+"-12-31 23:59:59'}" +
		"  group by s.finvitetypeid");
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				inviteMap.put(rs.getString("finvitetypeid"), rs.getBigDecimal("cnt"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultMap.put("inviteMap", inviteMap);
		Map<String,BigDecimal> winMap = new HashMap<String, BigDecimal>();
		builder.clear();
		
		builder.appendSql(" select count(*) cnt ,s.finvitetypeid"+
		"  From T_INV_TenderAccepterResultE e "+
		"  join T_INV_TenderAccepterResult r  on r.fid = e.fparentid "+
		"  left outer join t_fdc_supplierstock s   on s.fid = e.fsupplierid "+
		"  where r.fstate = '4AUDITTED' and e.fhit=1 and s.fcreatetime >= {ts '"+validateYear+"-01-01 00:00:00'} and s.fcreatetime <= {ts '"+validateYear+"-12-31 23:59:59'} "+
		"  group by s.finvitetypeid");
		
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				winMap.put(rs.getString("finvitetypeid"), rs.getBigDecimal("cnt"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resultMap.put("winMap", winMap);
		
		
    	
    	return resultMap;
    }
    
    
    
    
    
}