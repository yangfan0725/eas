package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
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
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class ContractBillReceiveTotalReportFacadeControllerBean extends AbstractContractBillReceiveTotalReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractBillReceiveTotalReportFacadeControllerBean");
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
	    initColoum(header,col,"companyId",200,true);
	    initColoum(header,col,"company",300,false);
	    initColoum(header,col,"moneyDefineId",200,true);
	    initColoum(header,col,"moneyDefine",200,false);
	    initColoum(header,col,"contractAmount",100,false);
	    initColoum(header,col,"recAmount",100,false);
	    initColoum(header,col,"payContractAmount",100,false);
	    initColoum(header,col,"payReqAmountAuditting",150,false);
	    initColoum(header,col,"payReqAmountAuditted",150,false);
	    initColoum(header,col,"payAmount",100,false);
	    
	    header.setLabels(new Object[][]{ 
	    		{
	    			"companyId","公司","moneyDefineId","款项类型","增值服务","增值服务","对应支付","对应支付","对应支付","对应支付"
	    		}
	    		,
	    		{
	    			"companyId","公司","moneyDefineId","款项类型","合同金额","收款金额","合同金额","付款申请金额（审批中）","付款申请金额（已审批）","实付金额"
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
		String orgUnitLongNumber=params.getString("orgUnit.longNumber");
		
    	StringBuffer sb = new StringBuffer();
    	
    	sb.append(" select e.fid companyId,e.fname_l2 company,d.fid moneyDefineId,d.fname_l2 moneyDefine, isnull(sum(a.famount),0) contractAmount,isnull(sum(t.recAmount),0) recAmount,isnull(sum(b.famount),0) payContractAmount,");
    	sb.append(" isnull(sum(t1.payReqAmount),0) payReqAmountAuditting,isnull(sum(t2.payReqAmount),0) payReqAmountAuditted,isnull(sum(t3.payAmount),0) payAmount ");
    	sb.append(" from T_CON_ContractBillReceive a left join t_con_contractBill b on a.fcontractbillid=b.fid ");
    	sb.append(" left join T_CON_ContractBillRRateEntry c on c.FParentID=a.fid left join T_SHE_MoneyDefine d on d.fid=c.fmoneyDefineId");
    	sb.append(" left join T_ORG_BaseUnit e on a.fcontrolunitid=e.fid ");
    	sb.append(" left join (select sum(b.FAmount) recAmount,FContractBillReceiveId from T_CON_ContractRecBill a left join T_CON_ContractRecBillEntry b on a.fid=b.FHeadId where a.fstate='4AUDITTED' group by FContractBillReceiveId )t on t.FContractBillReceiveId=a.fid");
    	sb.append(" left join (select sum(FAmount) payReqAmount,fcontractId from T_CON_PayRequestBill a where a.fstate='3AUDITTING' group by fcontractId )t1 on t1.fcontractId=b.fid");
    	sb.append(" left join (select sum(FAmount) payReqAmount,fcontractId from T_CON_PayRequestBill a where a.fstate='4AUDITTED' group by fcontractId )t2 on t2.fcontractId=b.fid");
    	sb.append(" left join (select sum(factualPayAmount) payAmount,fcontractBillId from T_cas_PaymentBill a where a.fbillStatus=15 group by fcontractBillId )t3 on t3.fcontractBillId=b.fid");
    	
    	sb.append(" where a.fstate='4AUDITTED' ");
    	if(orgUnitLongNumber!=null){
			sb.append(" and e.flongnumber like '"+orgUnitLongNumber+"%'");
		}
    	sb.append(" group by e.fid,e.fname_l2,d.fid,d.fname_l2,e.flongNumber,d.fnumber order by e.flongNumber,d.fnumber");
    	return sb.toString();
    }
}