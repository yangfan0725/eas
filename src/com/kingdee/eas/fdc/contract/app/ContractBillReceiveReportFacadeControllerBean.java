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
	   
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"curProjectId",100,true);
	    initColoum(header,col,"curProject",150,false);
	    initColoum(header,col,"contractType",180,false);
	    initColoum(header,col,"number",200,false);
	    initColoum(header,col,"name",300,false);
	    initColoum(header,col,"bizDate",100,false);
	    initColoum(header,col,"auditTime",100,false);
	    initColoum(header,col,"landDeveloper",200,false);
	    initColoum(header,col,"partB",200,false);
	    initColoum(header,col,"currency",100,false);
	    initColoum(header,col,"httk",100,false);
	    
	    initColoum(header,col,"recContractAmount",100,false);
	    initColoum(header,col,"recTotalAmount",100,false);
	    initColoum(header,col,"payContractAmount",100,false);
	    initColoum(header,col,"payReqAmountAuditting",100,false);
	    initColoum(header,col,"payReqAmountAuditted",100,false);
	    initColoum(header,col,"payTotalAmount",100,false);
	    initColoum(header,col,"sub1",120,false);
	    initColoum(header,col,"sub2",120,false);
	    
	    initColoum(header,col,"recCount",100,false);
	    initColoum(header,col,"recAmount",100,false);
	    
	    initColoum(header,col,"payId",100,true);
	    initColoum(header,col,"payCurProject",100,false);
	    initColoum(header,col,"payContractType",100,false);
	    initColoum(header,col,"payNumber",200,false);
	    initColoum(header,col,"payName",300,false);
	    initColoum(header,col,"payBizDate",100,false);
	    initColoum(header,col,"payAuditTime",100,false);
	    initColoum(header,col,"payLandDeveloper",200,false);
	    initColoum(header,col,"payPartB",200,false);
	    initColoum(header,col,"payCurrency",100,false);
	    initColoum(header,col,"payAmount",100,false);
	    
	    initColoum(header,col,"payReqCount",100,false);
	    initColoum(header,col,"payReqAmount",100,false);
	    
	    initColoum(header,col,"payReqActCount",100,false);
	    initColoum(header,col,"payReqActAmount",100,false);
	    
	    header.setLabels(new Object[][]{ 
	    		{
	    			"收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息","收款合同基本信息",
	    			"增值服务汇总信息","增值服务汇总信息","增值服务汇总信息","增值服务汇总信息","增值服务汇总信息","增值服务汇总信息","增值服务汇总信息","增值服务汇总信息",
	    			"收款明细","收款明细",
	    			"对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息","对应支付类合同基本信息",
	    			"付款明细","付款明细","付款明细","付款明细"
	    			}
	    		,
	    		{
	    			"id","curProjectId","项目名称","合同类型","合同编码","合同名称","业务日期","审批日期","本方单位","服务单位","币别","合同关键条款",
	    			"收款合同金额","收款金额","支付合同金额","付款申请金额","付款申请金额","实付付款","差额","差额",
	    			"收款笔数","收款金额",
	    			"payId","项目名称","合同类型","合同编码","合同名称","业务日期","审批日期","甲方","乙方","币别","合同金额",
	    			"付款申请笔数","付款申请金额","实际付款金笔数","实际付款金额"
	    		}
	    		,
	    		{
	    			"id","curProjectId","项目名称","合同类型","合同编码","合同名称","业务日期","审批日期","本方单位","服务单位","币别","合同关键条款",
	    			"收款合同金额","收款金额","支付合同金额","（审批中）","（已审批）","实付付款","收款合同-支付合同","收款金额-实付金额",
	    			"收款笔数","收款金额",
	    			"payId","项目名称","合同类型","合同编码","合同名称","业务日期","审批日期","甲方","乙方","币别","合同金额",
	    			"付款申请笔数","付款申请金额","实际付款金笔数","实际付款金额"
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
		
		String companyId=(String) params.getString("companyId");
		String moneyDefineId=(String) params.getString("moneyDefineId");
		
    	StringBuffer sb = new StringBuffer();
    	
    	sb.append(" select distinct a.fid id,d.fid curProjectId,d.fname_l2 curProject,e.fname_l2 contractType,a.fnumber number,a.fname name,a.fbookedDate bizDate,a.fauditTime auditTime,");
    	sb.append(" c.fname_l2 landDeveloper,b.fname_l2 partB,f.fname_l2 currency,a.fhttk httk,isnull(a.famount,0) recContractAmount,isnull(t.recTotalAmount,0),isnull(t4.payContractAmount,0),isnull(t1.payReqAmountAuditting,0),isnull(t2.payReqAmountAuditted,0),isnull(t3.payTotalAmount,0),isnull(a.famount,0)-isnull(t4.payContractAmount,0) sub1,isnull(t.recTotalAmount,0)-isnull(t3.payTotalAmount,0) sub2");
    	sb.append(" from T_CON_ContractBillReceive a left join T_bd_customer b on a.fpartBid=b.fid");
    	sb.append(" left join T_FDC_LandDeveloper c on a.FLandDeveloperID=c.fid");
    	sb.append(" left join T_FDC_CurProject d on a.fcurProjectid=d.fid");
    	sb.append(" left join T_FDC_ContractType e on a.fcontracttypeid=e.fid");
    	sb.append(" left join t_bd_currency f on a.fcurrencyid=f.fid");
    	sb.append(" left join T_ORG_BaseUnit g on a.forgUnitid=g.fid");
    	sb.append(" left join T_CON_ContractBillRRateEntry h on h.FParentID=a.fid");
    	sb.append(" left join (select sum(b.FAmount) recTotalAmount,a.FContractBillReceiveId from T_CON_ContractRecBill a left join T_CON_ContractRecBillEntry b on a.fid=b.FHeadId where a.fstate='4AUDITTED' group by FContractBillReceiveId )t on t.FContractBillReceiveId=a.fid");
    	sb.append(" left join (select sum(a.FAmount) payReqAmountAuditting,b.fcontractbillreceiveid from T_CON_PayRequestBill a left join t_con_contractbill b on a.fcontractId=b.fid where a.fstate='3AUDITTING' group by b.fcontractbillreceiveid )t1 on t1.fcontractbillreceiveid=a.fid");
    	sb.append(" left join (select sum(a.FAmount) payReqAmountAuditted,b.fcontractbillreceiveid from T_CON_PayRequestBill a left join t_con_contractbill b on a.fcontractId=b.fid where a.fstate='4AUDITTED' group by b.fcontractbillreceiveid  )t2 on t2.fcontractbillreceiveid=a.fid");
    	sb.append(" left join (select sum(a.factualPayAmount) payTotalAmount,b.fcontractbillreceiveid from T_cas_PaymentBill a left join t_con_contractbill b on a.fcontractBillId=b.fid where a.fbillStatus=15 group by b.fcontractbillreceiveid )t3 on t3.fcontractbillreceiveid=a.fid");
    	sb.append(" left join (select sum(famount) payContractAmount,fcontractbillreceiveid from t_con_contractBill a where a.fstate='4AUDITTED' group by fcontractbillreceiveid )t4 on t4.fcontractbillreceiveid=a.fid");
    	
    	sb.append(" where a.fstate='4AUDITTED'");
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
		if(companyId!=null){
			sb.append(" and a.fcontrolunitid='"+companyId+"'");
		} 
		if(moneyDefineId!=null){
			sb.append(" and h.fmoneyDefineId='"+moneyDefineId+"'");
		}
    	sb.append(" order by a.fnumber");
    	return sb.toString();
    }
}