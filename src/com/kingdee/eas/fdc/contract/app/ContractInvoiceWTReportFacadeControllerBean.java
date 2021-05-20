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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class ContractInvoiceWTReportFacadeControllerBean extends AbstractContractInvoiceWTReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractInvoiceWTReportFacadeControllerBean");
    
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
	    
	    initColoum(header,col,"invid",100,true);
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"isValid",100,false);
	    initColoum(header,col,"invoiceType",100,false);
	    initColoum(header,col,"invNumber",100,false);
	    initColoum(header,col,"invDate",100,false);
	    initColoum(header,col,"amountNoTax",100,false);
	    initColoum(header,col,"rateAmount",100,false);
	    initColoum(header,col,"totalAmount",100,false);
	    initColoum(header,col,"conNum",100,false);
	    initColoum(header,col,"conName",100,false);
	    initColoum(header,col,"revName",100,false);
	    
	    header.setLabels(new Object[][]{
	    		{
	    			"invid","id","发票校验状态","票据类型","票据号码","开票日期","不含税金额","税额","价税合计","无文本单据编号","无文本单据名称","收款人名称"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	RptRowSet rs = executeQuery(getSql(ctx,params), null, from, len, ctx);
		params.setObject("rs", rs);
		return params;
    }
    protected String getSql(Context ctx,RptParams params){
    	String curProject=(String) params.getObject("curProject");
    	Date fromDate = (Date)params.getObject("fromDate");
     	Date toDate =   (Date)params.getObject("toDate");
     	WTInvoiceTypeEnum type=(WTInvoiceTypeEnum) params.getObject("invoiceType");
     	String revName=params.getString("revName");
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select inv.fid invid,bill.fid id,inv.fisValid isValid,inv.finvoiceType invoiceType,inv.FInvoiceNumber invNumber,inv.fbizDate invDate,inv.fTotalAmount-inv.FAmount amountNoTax,inv.FAmount rateAmount,inv.fTotalAmount totalAmount,bill.fnumber conNum,bill.fname conName,case when supplier.fname_l2 is null then person.fname_l2 else supplier.fname_l2 end revName");
    	sb.append("	from T_CON_ContractWTInvoiceEntry inv left join T_CON_Contractwithouttext bill on inv.fparentid=bill.fid left join t_bd_supplier supplier on bill.FReceiveUnitID=supplier.fid left join t_bd_person person on bill.fpersonid=person.fid");
		sb.append(" where bill.fstate='4AUDITTED'");
		if(curProject!=null){
    		sb.append(" and bill.FCurProjectID in("+curProject+")");
    	}else{
    		sb.append(" and bill.FCurProjectID ='null'");
    	}
		if(fromDate!=null){
    		sb.append(" and inv.fbizDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and inv.fbizDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(type!=null){
			sb.append(" and inv.finvoiceType='"+type.getValue()+"'");
		}
		if(revName!=null&&!"".equals(revName)){
			sb.append(" and case when supplier.fname_l2 is null then person.fname_l2 else supplier.fname_l2 end like '%"+revName+"%'");
		}
    	sb.append(" order by inv.FInvoiceNumber");
    	return sb.toString();
    }
}