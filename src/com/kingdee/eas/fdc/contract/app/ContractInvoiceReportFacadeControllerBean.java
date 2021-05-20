package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class ContractInvoiceReportFacadeControllerBean extends AbstractContractInvoiceReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractInvoiceReportFacadeControllerBean");
    
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
	    initColoum(header,col,"isValid",100,false);
	    initColoum(header,col,"invoiceType",100,false);
	    initColoum(header,col,"invNumber",100,false);
	    initColoum(header,col,"invDate",100,false);
	    initColoum(header,col,"amountNoTax",100,false);
	    initColoum(header,col,"rateAmount",100,false);
	    initColoum(header,col,"totalAmount",100,false);
	    initColoum(header,col,"contractName",100,false);
	    initColoum(header,col,"buyName",100,false);
	    initColoum(header,col,"buyTaxNo",100,false);
	    initColoum(header,col,"buyAddressAndPhone",100,false);
	    initColoum(header,col,"buyBankNo",100,false);
	    initColoum(header,col,"saleName",100,false);
	    initColoum(header,col,"saleTaxNo",100,false);
	    initColoum(header,col,"saleAddressAndPhone",100,false);
	    initColoum(header,col,"saleBankNo",100,false);
	    
	    header.setLabels(new Object[][]{
	    		{
	    			"id","发票校验状态","票据类型","票据号码","开票日期","不含税金额","税额","价税合计","合同名称","购货单位信息","购货单位信息","购货单位信息","购货单位信息","销货单位信息","销货单位信息","销货单位信息","销货单位信息"
	    		}
	    		,
	    		{
	    			"id","发票校验状态","票据类型","票据号码","开票日期","不含税金额","税额","价税合计","合同名称","名称","纳税人识别号","地址、电话","开户行及银行账号","名称","纳税人识别号","地址、电话","开户行及银行账号"
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
     	InvoiceTypeEnum type=(InvoiceTypeEnum) params.getObject("invoiceType");
     	String saleBuyName=params.getString("saleBuyName");
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select inv.fid id,inv.fisValid isValid,inv.finvoiceType invoiceType,inv.FInvoiceNumber invNumber,inv.fbizDate invDate,inv.fTotalAmount-inv.fTotalRateAmount amountNoTax,inv.fTotalRateAmount rateAmount,inv.fTotalAmount totalAmount,bill.fcontractName,");
    	sb.append(" inv.fbuyName buyName,inv.fbuyTaxNo buyTaxNo,inv.fbuyAddressAndPhone buyAddressAndPhone,inv.fbuyBankNo buyBankNo,inv.fsaleName saleName,inv.fsaleTaxNo saleTaxNo,inv.fsaleAddressAndPhone saleAddressAndPhone,inv.fsaleBankNo saleBankNo");
    	sb.append("	from T_CON_ContractInvoice inv left join t_con_contractbill con on con.fid=inv.fcontractid left join T_CON_PayReqInvoiceEntry entry on inv.fid=entry.FInvoiceId left join T_CON_PayrequestBill bill on entry.fparentid=bill.fid");
		sb.append(" where inv.fstate='4AUDITTED'");
		if(curProject!=null){
    		sb.append(" and con.FCurProjectID in("+curProject+")");
    	}else{
    		sb.append(" and con.FCurProjectID ='null'");
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
		if(saleBuyName!=null&&!"".equals(saleBuyName)){
			sb.append(" and inv.fsaleName like '%"+saleBuyName+"%'");
		}
    	sb.append(" order by inv.FInvoiceNumber");
    	return sb.toString();
    }
}