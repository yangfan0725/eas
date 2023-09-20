package com.kingdee.eas.fdc.contract.app;

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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class ContractBillReceiveDetailReportFacadeControllerBean extends AbstractContractBillReceiveDetailReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractBillReceiveDetailReportFacadeControllerBean");
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
	    initColoum(header,col,"id",200,true);
	    initColoum(header,col,"number",200,false);
	    initColoum(header,col,"bizDate",200,false);
	    initColoum(header,col,"amount",200,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"id","单据编号","业务日期","金额"
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
		String type=params.getString("type");
		String id=params.getString("id");
		
    	StringBuffer sb = new StringBuffer();
    	if(type.equals("recCount")){
    		sb.append(" select a.fid id,a.fnumber number,a.fbizDate bizDate,a.famount amount from T_CON_ContractRecBill a where a.FContractBillReceiveId='"+id+"'");
    	}else if(type.equals("payReqCount")){
    		sb.append(" select a.fid id,a.fnumber number,a.fbookedDate bizDate,a.famount amount from T_CON_PayRequestBill a where a.fcontractId='"+id+"'");
    	}else if(type.equals("payReqActCount")){
    		sb.append(" select a.fid id,a.fnumber number,a.fbizDate bizDate,a.famount amount from T_cas_PaymentBill a where a.fcontractBillId='"+id+"'");
    	}
    	sb.append(" order by a.fnumber");
    	return sb.toString();
    }
}