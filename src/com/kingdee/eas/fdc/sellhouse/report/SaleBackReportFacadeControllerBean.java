package com.kingdee.eas.fdc.sellhouse.report;

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

public class SaleBackReportFacadeControllerBean extends AbstractSaleBackReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.SaleBackReportFacadeControllerBean");
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
	    initColoum(header,col,"id",150,true);
	    initColoum(header,col,"number",150,false);
	    initColoum(header,col,"name",150,false);
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"unSignAmount",100,false);
	    initColoum(header,col,"signAmount",100,false);
	    initColoum(header,col,"dealTotalAmount",150,false);
	    initColoum(header,col,"sellAmount",150,false);
	    initColoum(header,col,"backAmount",150,false);
	    initColoum(header,col,"quitBackAmount",150,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"id","项目编码","项目名称","总套数","认购未转签约套数","签约套数","成交总价","补差后合同价","回款金额","退房未退房款"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
		RptRowSet roomRS = executeQuery(getRoom(), null, ctx);
		params.setObject("roomRS", roomRS);
		
		RptRowSet purRS = executeQuery(getPur(), null, ctx);
		params.setObject("purRS", purRS);
		
		RptRowSet signRS = executeQuery(getSign(), null, ctx);
		params.setObject("signRs", signRS);
		
		RptRowSet backRS = executeQuery(getBack(), null, ctx);
		params.setObject("backRS", backRS);
		
		RptRowSet quitBackRS = executeQuery(getQuitBack(), null, ctx);
		params.setObject("quitBackRS", quitBackRS);
		return params;
    }
    private String getRoom(){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select s.fid id,s.fnumber number,s.fname_l2 name,count(r.fid) amount");
    	sb.append(" from t_she_room r left join t_she_building b on r.fbuildingid=b.fid left join t_she_sellProject s on b.fsellProjectid=s.fid");
    	sb.append(" where r.fisforten=0 group by s.fid,s.fnumber,s.fname_l2 order by s.fnumber");
    	return sb.toString();
    }
    
    private String getPur(){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select t.id, count(t.roomId) unSignAmount");
    	sb.append(" from(select distinct pur.froomId roomId,pur.fsellProjectId id from t_she_purchaseManage pur ");
    	sb.append(" where pur.fbizState in('PurApple','PurAudit','ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing'))t");
    	sb.append(" group by t.id");
    	return sb.toString();
    }
    
    private String getSign(){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select t.id, count(t.roomId) signAmount,sum(t.dealTotalAmount) dealTotalAmount,sum(t.sellAmount) sellAmount");
    	sb.append(" from(select distinct sign.froomId roomId,sign.fsellProjectId id,sign.FDealTotalAmount dealTotalAmount,sign.FSellAmount sellAmount from t_she_signManage sign ");
    	sb.append(" where sign.fbizState in('SignApple','SignAudit','ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing'))t");
    	sb.append(" group by t.id");
    	return sb.toString();
    }
    
    private String getBack(){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select revBill.fsellProjectId id,sum(isnull(entry.famount,0)+isnull(entry.frevAmount,0)) backAmount");
    	sb.append(" from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid");
		sb.append(" left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId left join T_SHE_Transaction trans on revBill.frelateTransId=trans.fid");
		sb.append(" where trans.FIsValid=0 and revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') group by revBill.fsellProjectId");
    	return sb.toString();
    }
    
    private String getQuitBack(){
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select revBill.fsellProjectId id,sum(isnull(entry.famount,0)+isnull(entry.frevAmount,0)) quitBackAmount");
    	sb.append(" from T_BDC_SHERevBillEntry entry left join T_BDC_SHERevBill revBill on revBill.fid=entry.fparentid");
		sb.append(" left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId left join T_SHE_Transaction trans on revBill.frelateTransId=trans.fid");
		sb.append(" where trans.FIsValid=1 and revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') group by revBill.fsellProjectId");
    	return sb.toString();
    }
}