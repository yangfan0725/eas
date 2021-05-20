package com.kingdee.eas.fdc.sellhouse.report;

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
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class CommissionReportFacadeControllerBean extends AbstractCommissionReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.CommissionReportFacadeControllerBean");
    
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
	
	protected RptParams _createTempTable(Context ctx, RptParams params) throws BOSException, EASBizException {
		RptTableHeader header = new RptTableHeader();
		RptTableColumn col = null;

		initColoum(header,col,"person",100,false);
		initColoum(header,col,"xsyj",100,false);
		initColoum(header,col,"yl",100,false);
		initColoum(header,col,"jl",100,false);
		initColoum(header,col,"sfyj",100,false);

		for(int i=1;i<13;i++){
			initColoum(header,col,"position"+i,100,false);
			initColoum(header,col,"xsyj"+i,100,false);
			initColoum(header,col,"yl"+i,100,false);
			initColoum(header,col,"jl"+i,100,false);
			initColoum(header,col,"sfyj"+i,100,false);
		}
		header.setLabels(new Object[][]{ 
				{
					"年度合计总数","年度合计总数","年度合计总数","年度合计总数","年度合计总数",
					"1月","1月","1月","1月","1月",
					"2月","2月","2月","2月","2月",
					"3月","3月","3月","3月","3月",
					"4月","4月","4月","4月","4月",
					"5月","5月","5月","5月","5月",
					"6月","6月","6月","6月","6月",
					"7月","7月","7月","7月","7月",
					"8月","8月","8月","8月","8月",
					"9月","9月","9月","9月","9月",
					"10月","10月","10月","10月","10月",
					"11月","11月","11月","11月","11月",
					"12月","12月","12月","12月","12月",
				}
				,
				{
					"姓名","销售佣金（元）","预留（交付/年度）（元）","正负激励（元）","实发佣金（元）",
					"岗位","销售佣金（元）","预留（交付/年度）（元）","正负激励（元）","实发佣金（元）",
					"岗位","销售佣金（元）","预留（交付/年度）（元）","正负激励（元）","实发佣金（元）",
					"岗位","销售佣金（元）","预留（交付/年度）（元）","正负激励（元）","实发佣金（元）",
					"岗位","销售佣金（元）","预留（交付/年度）（元）","正负激励（元）","实发佣金（元）",
					"岗位","销售佣金（元）","预留（交付/年度）（元）","正负激励（元）","实发佣金（元）",
					"岗位","销售佣金（元）","预留（交付/年度）（元）","正负激励（元）","实发佣金（元）",
					"岗位","销售佣金（元）","预留（交付/年度）（元）","正负激励（元）","实发佣金（元）",
					"岗位","销售佣金（元）","预留（交付/年度）（元）","正负激励（元）","实发佣金（元）",
					"岗位","销售佣金（元）","预留（交付/年度）（元）","正负激励（元）","实发佣金（元）",
					"岗位","销售佣金（元）","预留（交付/年度）（元）","正负激励（元）","实发佣金（元）",
					"岗位","销售佣金（元）","预留（交付/年度）（元）","正负激励（元）","实发佣金（元）",
					"岗位","销售佣金（元）","预留（交付/年度）（元）","正负激励（元）","实发佣金（元）",
				}
		},true);
		params.setObject("header", header);
		return params;
	}
	protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
		RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);;
		params.setObject("rowset", rowSet);
		params.setInt("count", rowSet.getRowCount());
		return params;
	}

	//核心查询方法
	protected String getSql(Context ctx,RptParams params){
		SellProjectInfo sellProject = (SellProjectInfo) params.getObject("sellProject");
		String org=(String) params.getObject("org");
		String sellProjectStr=null;
		if(sellProject!=null){
			try {
				sellProjectStr=SHEManageHelper.getStringFromSet(SHEManageHelper.getAllSellProjectCollection(ctx,sellProject));
			} catch (BOSException e) {
				e.printStackTrace();
			} 
		}
		Integer year = (Integer) params.getObject("year");
		StringBuffer sb = new StringBuffer();
		sb.append(" /*dialect*/ select decode(entry.FBonusType,1,p.fname_l2,2,p.fname_l2,3,p.fname_l2,4,entry.fqdperson,5,entry.frecommended,null) perosn,");
		
		sb.append(" sum(entry.FBonus+entry.fxz) xsyj,");
		sb.append(" sum(entry.FKeepAmt) yl,");
		sb.append(" sum(entry.FAdjustAmt) jl,");
		sb.append(" sum(entry.FRealBonusAmt) sfyj,");
		for(int i=1;i<13;i++){
			sb.append(" max(decode(cs.fmonth,"+i+",entry.fposition,'')) position,");
			sb.append(" sum(decode(cs.fmonth,"+i+",entry.FBonus,0)+decode(cs.fmonth,"+i+",entry.fxz,0)) xsyj"+i+",");
			sb.append(" sum(decode(cs.fmonth,"+i+",entry.FKeepAmt,0)) yl"+i+",");
			sb.append(" sum(decode(cs.fmonth,"+i+",entry.FAdjustAmt,0)) jl"+i+",");
			if(i==12){
				sb.append(" sum(decode(cs.fmonth,"+i+",FRealBonusAmt,0)) sfyj"+i);
			}else{
				sb.append(" sum(decode(cs.fmonth,"+i+",FRealBonusAmt,0)) sfyj"+i+",");
			}
		}
		sb.append(" from T_SHE_MarketingCommissionEntry entry left join T_SHE_CommissionSettlementBill cs on cs.fid=entry.fparentid left join T_BD_Person p on p.fid=entry.fpersonid");
		
		sb.append(" where cs.fstate='4AUDITTED' and cs.fyear="+year);
		if(sellProjectStr!=null){
			sb.append(" and cs.fsellProject in ("+sellProjectStr+")");
		}else if(org!=null){
			sb.append(" and cs.fsellProject in ("+org+")");
		}else{
			sb.append(" and cs.fsellProject in ('null')");
		}
		sb.append(" group by decode(entry.FBonusType,1,p.fname_l2,2,p.fname_l2,3,p.fname_l2,4,entry.fqdperson,5,entry.frecommended,null)");
		return sb.toString();
	}
}