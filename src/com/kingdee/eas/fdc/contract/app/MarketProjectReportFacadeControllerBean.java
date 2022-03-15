package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
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

public class MarketProjectReportFacadeControllerBean extends AbstractMarketProjectReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.MarketProjectReportFacadeControllerBean");
    
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
	    initColoum(header,col,"entryId",100,true);
	    initColoum(header,col,"isSelect",50,false);
	    initColoum(header,col,"source",100,true);
	    initColoum(header,col,"number",100,false);
	    initColoum(header,col,"bizDate",100,false);
	    initColoum(header,col,"auditTime",100,false);
	    initColoum(header,col,"name",100,false);
	    initColoum(header,col,"costAccount",100,false);
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"subAmount",100,false);
	    initColoum(header,col,"partB",100,true);
	    initColoum(header,col,"type",100,false);
	    initColoum(header,col,"conId",100,true);
	    initColoum(header,col,"conAuditTime",100,false);
	    initColoum(header,col,"isTimeOut",70,false);
	    initColoum(header,col,"conHasSettled",70,false);
	    initColoum(header,col,"conNumber",100,false);
	    initColoum(header,col,"conName",100,false);
	    initColoum(header,col,"conPartB",100,false);
	    initColoum(header,col,"conAmount",100,false);
	    initColoum(header,col,"payAmount",100,false);
	    initColoum(header,col,"unPayAmount",100,false);
	    initColoum(header,col,"conBizDate",100,false);
	    initColoum(header,col,"conMarketAmount",100,false);
	    
	    header.setLabels(new Object[][]{ 
	    		{
//	    			"id","营销立项单据编号","立项审批通过时间","立项事项","费用归口口径","立项金额","签约单位/个人","单据类型","conId","合同审批通过时间","合同编号","合同名称","签约单位/个人","合同总金额","累计付款金额","剩余未付款金额","营销费用归属月份","营销费用发生额（含分摊）"
	    			"id","entryId","选择","立项来源","营销立项单据编号","事项预估发生日期","立项审批通过日期","立项事项","费用归口口径","立项金额","立项可用余额","签约单位/个人","单据类型","conId","合同审批通过时间","是否超时","已结算","合同编号","合同名称","签约单位/个人","合同总金额","累计付款金额","剩余未付款金额","营销费用归属月份","营销费用发生额（含分摊）"

	    		}
	    		,
	    		{
//	    			"id","营销立项单据编号","立项审批通过时间","立项事项","费用归口口径","立项金额","签约单位/个人","单据类型","conId","合同审批通过时间","合同编号","合同名称","签约单位/个人","合同总金额","累计付款金额","剩余未付款金额","营销费用归属月份","营销费用发生额（含分摊）"
	    			"id","entryId","选择","立项来源","营销立项单据编号","事项预估发生日期","立项审批通过日期","立项事项","费用归口口径","立项金额","立项可用余额","签约单位/个人","单据类型","conId","合同审批通过时间","是否超时","已结算","合同编号","合同名称","签约单位/个人","合同总金额","累计付款金额","剩余未付款金额","营销费用归属月份","营销费用发生额（含分摊）"

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
    	Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	YesOrNoEnum isYE=(YesOrNoEnum)params.getObject("isYE");
    	String org=params.getString("org");
    	StringBuffer sb = new StringBuffer();
    	
    	sb.append(" select m.fid id,cost.fid entryId,0 isSelect,m.fsource source,m.fnumber number,m.fbizDate bizDate,case when m.fstate='4AUDITTED' then m.fauditTime else null end auditTime,m.fname name,c.fname_l2 costAccount,cost.famount amount,cost.famount-isnull(tt.amount,0)+isnull(fcost.famount,0),'' partB,case when cost.ftype='JZ' then '记账单' when cost.ftype='CONTRACT' then '合同' else '无文本' end,t.conId,t.conAuditTime,t.isTimeOut,t.conHasSettled,t.conNumber,");
    	sb.append(" t.conName,t.conPartB,t.conAmount,t.payAmount,(t.conAmount-isnull(t.payAmount,0)) unPayAmount,case when cost.ftype='JZ' then m.fbizDate else t.conBizdate end,case when cost.ftype='JZ' then cost.famount else t.conMarketAmount end");
    	sb.append(" from T_CON_MarketProject m left join T_CON_MarketProjectCostEntry cost on cost.fheadid=m.fid ");
    	sb.append(" left join T_CON_MarketProject fm on fm.FMpId=m.fid");
    	sb.append(" left join T_CON_MarketProjectCostEntry fcost on cost.fcostaccountid=fcost.fcostaccountid and fm.fid=fcost.fheadid");
    	sb.append(" left join T_FDC_CostAccount c on c.fid=cost.fcostaccountid ");
    	sb.append(" left join (select '合同' type,con.fid conId,con.fmarketProjectId,con.FMpCostAccountId,con.fauditTime conAuditTime,con.fIsTimeOut isTimeOut,con.fnumber conNumber,(case when con.fhasSettled=0 then '否' else '是' end) conHasSettled, con.fname conName,supplier.fname_l2 conPartB,(case when t.fsettleprice is null then con.famount else t.fsettleprice end) conAmount,entry.fdate conBizdate,(case when t.fsettleprice is null then con.famount else t.fsettleprice end)*entry.frate/100  conMarketAmount,p.amount payAmount from");
    	sb.append(" t_con_contractbill con left join (select sb.fsettleprice,sb.fcontractbillid from T_CON_ContractSettlementBill sb where sb.fstate='4AUDITTED') t on t.fcontractbillid=con.fid left join t_bd_supplier supplier on supplier.fid=con.fpartBid left join (select p.fcontractid conId,sum(fActualPayAmount) amount from t_con_payrequestbill p left join t_cas_paymentbill t on t.ffdcPayReqID=p.fid where t.fbillStatus=15 group by p.fcontractid ) p on p.conId=con.fid");
    	sb.append(" left join T_CON_ContractMarketEntry entry on entry.fheadid=con.fid where con.fstate='4AUDITTED'");
    	
//    	sb.append(" left join T_CON_ContractBill con1 on entry.fheadid=con.fid where con.fstate!='1SAVED'");

    	
    	sb.append(" union all select '无文本' type,con.fid conId,con.fmarketProjectId,con.FMpCostAccountId,con.fauditTime conAuditTime,con.fIsTimeOut isTimeOut,con.fnumber conNumber,'否' conHasSettled,con.fname conName,(case when supplier.fname_l2 is null then p.fname_l2 else supplier.fname_l2 end) conPartB,con.famount conAmount,entry.fdate conBizdate,entry.famount conMarketAmount,p.amount payAmount from");
    	sb.append(" T_CON_ContractWithoutText con left join t_bd_supplier supplier on supplier.fid=con.FReceiveUnitID left join (select p.fcontractid conId,sum(fActualPayAmount) amount from t_con_payrequestbill p left join t_cas_paymentbill t on t.ffdcPayReqID=p.fid where t.fbillStatus=15 group by p.fcontractid ) p on p.conId=con.fid");
    	sb.append(" left join t_bd_person p on p.fid=con.fpersonid left join T_CON_ContractWTMarketEntry entry on entry.fheadid=con.fid");
    	sb.append(" )t on t.fmarketProjectId=m.fid and t.FMpCostAccountId=cost.fcostAccountId");
    	
    	sb.append(" left join (select sum(t.conAmount) amount,t.fmarketProjectId,t.FMpCostAccountId from (select con.fmarketProjectId,con.FMpCostAccountId,(case when t.fsettleprice is null then con.famount else t.fsettleprice end) conAmount from");
    	sb.append(" t_con_contractbill con left join (select sb.fsettleprice,sb.fcontractbillid from T_CON_ContractSettlementBill sb where sb.fstate='4AUDITTED') t on t.fcontractbillid=con.fid ");
    	sb.append(" where con.fstate='4AUDITTED'");
    	
    	
    	sb.append(" union all select con.fmarketProjectId,con.FMpCostAccountId,con.famount conAmount from");
    	sb.append(" T_CON_ContractWithoutText con ");
    	sb.append(" )t group by t.fmarketProjectId,t.FMpCostAccountId)tt  on tt.fmarketProjectId=m.fid and tt.FMpCostAccountId=cost.fcostAccountId");
    
    	
    	sb.append(" where 1=1");
    	if(fromDate!=null){
			sb.append(" and m.fauditTime>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and m.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}  
    	if(org!=null){
    		sb.append(" and m.forgUnitid='"+org+"'");
    	}
    	if(isYE!=null){
    		if(isYE.equals(YesOrNoEnum.YES)){
    			sb.append(" and (cost.famount-isnull(tt.amount,0)+isnull(fcost.famount,0))>0");
    		}else{
    			sb.append(" and (cost.famount-isnull(tt.amount,0)+isnull(fcost.famount,0))=0");
    		}
    	}
    	sb.append(" order by m.fnumber,c.flongnumber,t.conNumber,t.conBizdate");
    	return sb.toString();
    }
}