package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class ContractBillReceiveReportFacadeControllerBean extends AbstractContractBillReceiveReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractBillReceiveReportFacadeControllerBean");
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
	    initColoum(header,col,"payId",100,true);
	    initColoum(header,col,"payCurProject",100,false);
	    initColoum(header,col,"payContractType",100,false);
	    initColoum(header,col,"payNumber",200,false);
	    initColoum(header,col,"payName",200,false);
	    initColoum(header,col,"payBizDate",100,false);
	    initColoum(header,col,"payAuditTime",100,false);
	    initColoum(header,col,"payLandDeveloper",200,false);
	    initColoum(header,col,"payPartB",200,false);
	    initColoum(header,col,"payCurrency",100,false);
	    initColoum(header,col,"payAmount",100,false);
	    
	    initColoum(header,col,"payTotalAmount",100,false);
	    initColoum(header,col,"recTotalAmount",100,false);
	    initColoum(header,col,"sub",100,false);
	    
	    initColoum(header,col,"payReqId",100,true);
	    initColoum(header,col,"payReqNumber",200,false);
	    initColoum(header,col,"payReqBizDate",100,false);
	    initColoum(header,col,"payReqAmount",100,false);
	    initColoum(header,col,"payReqActDate",100,false);
	    initColoum(header,col,"payReqActAmount",100,false);
	    
	    
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"curProject",100,false);
	    initColoum(header,col,"contractType",100,false);
	    initColoum(header,col,"number",200,false);
	    initColoum(header,col,"name",200,false);
	    initColoum(header,col,"bizDate",100,false);
	    initColoum(header,col,"auditTime",100,false);
	    initColoum(header,col,"partB",200,false);
	    initColoum(header,col,"landDeveloper",200,false);
	    initColoum(header,col,"currency",100,false);
	    initColoum(header,col,"httk",100,false);
	    initColoum(header,col,"amount",100,false);
	    
	    initColoum(header,col,"recId",100,true);
	    initColoum(header,col,"recNumber",200,false);
	    initColoum(header,col,"recBizDate",100,false);
	    initColoum(header,col,"recMoneyDefine",100,false);
	    initColoum(header,col,"recAmount",100,false);
	    
	    header.setLabels(new Object[][]{ 
	    		{
	    			"对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息",
	    			"付款明细","付款明细","付款明细","付款明细","付款明细","付款明细",
	    			"收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息",
	    			"收款明细","收款明细","收款明细","收款明细","收款明细"
	    			}
	    		,
	    		{

	    			"payId","项目名称","合同类型","合同编码","合同名称","业务日期","审批日期","甲方","乙方","币别","合同金额","累计付款","累计收款","差额",
	    			"payReqId","单据编码","付款申请日期","付款申请金额","实际付款日期","实际付款金额",
	    			"id","项目名称","合同类型","合同编码","合同名称","业务日期","审批日期","甲方","乙方","币别","合同关键条款","合同金额",
	    			"recId","单据编码","收款日期","款项类型","收款金额"
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
    private String SetToIn(Set set){
    	Iterator it=set.iterator();
    	String in="(";
    	while(it.hasNext()){
    		in=in+"'"+it.next().toString()+"',";
    	}
    	in=in+"'')";
    	return in;
    }
    protected String getSql(Context ctx,RptParams params){
		String orgUnitLongNumber=params.getString("orgUnit.longNumber");
		Set authorizedOrgs=(Set) params.getObject("orgUnit.id");
		
		Set curProject=(Set) params.getObject("curProject.id");
		Set contractType=(Set) params.getObject("contractType.id");
		
    	StringBuffer sb = new StringBuffer();
    	
    	sb.append(" select distinct t.fid payId,d.fname_l2 payCurProject,e.fname_l2 payContractType,t.fnumber payNumber,t.fname payName,t.fbookedDate payBizDate,t.fauditTime payAuditTime, ");
    	sb.append(" c.fname_l2 payLandDeveloper,b.fname_l2 payPartB,f.fname_l2 payCurrency,t.famount payAmount ");
    	sb.append(" from T_CON_ContractBillReceive a left join t_con_contractBill t on a.fcontractbillid=t.fid left join T_bd_supplier b on t.fpartBid=b.fid ");
    	sb.append(" left join T_FDC_LandDeveloper c on t.FLandDeveloperID=c.fid");
    	sb.append(" left join T_FDC_CurProject d on t.fcurProjectid=d.fid");
    	sb.append(" left join T_FDC_ContractType e on t.fcontracttypeid=e.fid ");
    	sb.append(" left join t_bd_currency f on t.fcurrencyid=f.fid ");
    	sb.append(" left join T_ORG_BaseUnit g on t.forgUnitid=g.fid ");
    	sb.append(" where t.fid is not null ");
    	if(orgUnitLongNumber!=null){
			sb.append(" and g.flongnumber like '"+orgUnitLongNumber+"%'");
		}
		if(authorizedOrgs!=null){
			sb.append(" and g.fid in"+SetToIn(authorizedOrgs));
		} 
		if(curProject!=null){
			sb.append(" and d.fid in"+SetToIn(curProject));
		} 
		if(contractType!=null){
			sb.append(" and e.fid in"+SetToIn(contractType));
		} 
    	sb.append(" order by t.fnumber");
    	return sb.toString();
    }
}