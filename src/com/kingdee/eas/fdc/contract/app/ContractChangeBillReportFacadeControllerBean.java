package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class ContractChangeBillReportFacadeControllerBean extends AbstractContractChangeBillReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractChangeBillReportFacadeControllerBean");
    
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
	    initColoum(header,col,"contractType",100,true);
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"caId",100,true);
	    initColoum(header,col,"cbId",100,true);
	    initColoum(header,col,"number",120,false);
	    initColoum(header,col,"name",200,false);
	    initColoum(header,col,"supplier",200,false);
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"changeType",100,false);
	    initColoum(header,col,"reason",100,false);
	    initColoum(header,col,"caNumber",100,false);
	    initColoum(header,col,"copies",100,false);
	    initColoum(header,col,"cbNumber",100,false);
	    initColoum(header,col,"cbName",100,false);
	    
	    initColoum(header,col,"changeContent",100,false);
	    
	    initColoum(header,col,"conductDept",100,false);
	    initColoum(header,col,"specialtyTypeName",100,false);
	    
	    initColoum(header,col,"cbAmount",100,false);
	    initColoum(header,col,"UNCONFIRM",100,false);
	    initColoum(header,col,"CONFIRM",100,false);
	    initColoum(header,col,"confirmCopies",100,false);
	    
	    initColoum(header,col,"caBizDate",100,false);
	    initColoum(header,col,"csAuditDate",100,false);
	    initColoum(header,col,"caCreator",100,false);
	    initColoum(header,col,"caAuditor",100,false);
	    header.setLabels(new Object[][]{
	    		{
	    			"��ͬ����","id","caId","cbId","��ͬ����","��ͬ����","ǩԼ��λ","��ͬ���","�������","����ԭ��","�������������",
	    			"��ϸ˵��","��ϸ˵��","��ϸ˵��","��ϸ˵��","��ϸ˵��","��ϸ˵��","��ϸ˵��","��ϸ˵��","��ϸ˵��","��ϸ˵��",
	    			"��������","��������","������","������"
	    		}
	    		,
	    		{
	    			"��ͬ����","id","caId","cbId","��ͬ����","��ͬ����","ǩԼ��λ","��ͬ���","�������","����ԭ��","�������������",
	    			"����","	���ָ�����","ָ���������","�������","�������/��λ","רҵ����","Ԥ�����","δȷ�Ͻ��","ȷ�Ͻ��","ȷ������",
	    			"��������","��������","������","������"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		Map value=new HashMap();
		while(rowSet.next()){
			String contractTypeName=rowSet.getString("contractType");
			if(value.containsKey(contractTypeName)){
				((List)value.get(contractTypeName)).add(rowSet.toRowArray());
			}else{
				List list=new ArrayList();
				list.add(rowSet.toRowArray());
				value.put(contractTypeName, list);
			}
		}
		
		
		Map detailValue=new HashMap();
		String curProject=(String) params.getObject("curProject");
		CurProjectInfo curProjectinfo=(CurProjectInfo) params.getObject("curProjectInfo");
    	String number=(String)params.getObject("number");
    	String changeTypeId=(String)params.getObject("changeTypeId");
    	Boolean isClick=(Boolean)params.getObject("isClick");
    	Boolean CONFIRM=(Boolean)params.getObject("CONFIRM");
    	StringBuffer sb = new StringBuffer();
	    		
    	if(isClick!=null&&isClick){
    		sb.append(" select t.contractType,t.id,t.caId,t.cbId,t.number,t.name,t.supplier,t.amount,t.changeType,t.reason,t.caNumber,t.copies,t.cbNumber,t.cbName,t.changeContent,t.conductDept,t.specialtyTypeName,t.cbAmount,t.UNCONFIRM,t.CONFIRM,t.confirmCopies,t.caBizDate,t.csAuditDate,t.caCreator,t.caAuditor from(");
    		sb.append(" select contract.fnumber contractNumber,contractType.flongNumber contractTypeNumber,contract.fmainContractNumber mainContractNumber,contract.fcontractPropert contractPropert,'' contractType,contract.fid id,ca.fid caId,cb.fid cbId,'' number,'' name,'' supplier,'' amount,ct.fname_l2 changeType,reason.fname_l2 reason,ca.fnumber caNumber,(case when cb.fid is not null then 1 else 0 end)copies,cb.fnumber cbNumber,cb.fname cbName,cb.freaDesc changeContent,(case when org.fname_l2 is null then unit.fname_l2 else org.fname_l2 end) conductDept,cb.fspecialtyTypeName specialtyTypeName,cb.famount cbAmount,");
        	sb.append("	(case when cb.fstate in('4AUDITTED','7ANNOUNCE') then cb.famount else 0 end) UNCONFIRM,(case when cb.fstate='8VISA' then (changeSettle.fallowAmount*cb.fexRate) else 0 end) CONFIRM,(case when cb.fstate='8VISA' then 1 else 0 end)confirmCopies,ca.fbookedDate caBizDate, (case when cb.fstate!='8VISA' then null else changeSettle.fauditTime end) csAuditDate,creator.fname_l2 caCreator,auditor.fname_l2 caAuditor");
        	sb.append("	from T_CON_ChangeAuditBill ca left join T_FDC_ChangeType ct on ct.fid=ca.FAuditTypeID left join T_FDC_ChangeReason reason on reason.fid=ca.FChangeReasonID left join T_CON_ChangeSupplierEntry caEntry on ca.fid=caEntry.FParentID left join t_con_contractBill contract on contract.fid=caEntry.FContractBillID");
        	sb.append(" left join T_CON_ProgrammingContract pc on pc.fid=contract.fProgrammingContract left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join t_bd_supplier supplier on supplier.fid=contract.fpartBId left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId left join T_CON_ContractChangeBill cb on cb.fid=caEntry.FContractChangeID left join T_ORG_Admin org on org.fid=cb.FConductDeptID left join T_BD_Supplier unit on unit.fid=cb.FConductUnitID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.FConChangeBillID=cb.fid");
        	sb.append(" left join t_pm_user creator on creator.fid=ca.fcreatorId left join t_pm_user auditor on auditor.fid=ca.fauditorId where ca.fstate in('4AUDITTED','7ANNOUNCE')"); 
        	if(curProjectinfo!=null&&number!=null){
        		sb.append(" and pro.fprojectID ='"+curProjectinfo.getId().toString()+"' and pc.flongNumber like '"+number+"%'");
        	}else{
        		sb.append(" and ca.FCurProjectID ='null'");
        	}
        	if(changeTypeId!=null){
        		sb.append(" and ct.fid ='"+changeTypeId+"'");
        	}
        	sb.append(" union all select contract.fnumber contractNumber,contractType.flongNumber contractTypeNumber,contract.fmainContractNumber mainContractNumber,contract.fcontractPropert contractPropert,'' contractType,contract.fid id,ca.fid caId,cb.fid cbId,'' number,'' name,'' supplier,'' amount,ct.fname_l2 changeType,reason.fname_l2 reason,ca.fnumber caNumber,(case when cb.fid is not null then 1 else 0 end)copies,cb.fnumber cbNumber,cb.fname cbName,cb.freaDesc changeContent,(case when org.fname_l2 is null then unit.fname_l2 else org.fname_l2 end) conductDept,cb.fspecialtyTypeName specialtyTypeName,cb.famount cbAmount,");
        	sb.append("	(case when cb.fstate in('4AUDITTED','7ANNOUNCE') then cb.famount else 0 end) UNCONFIRM,(case when cb.fstate='8VISA' then (changeSettle.fallowAmount*cb.fexRate) else 0 end) CONFIRM,(case when cb.fstate='8VISA' then 1 else 0 end)confirmCopies,ca.fbookedDate caBizDate, (case when cb.fstate!='8VISA' then null else changeSettle.fauditTime end) csAuditDate,creator.fname_l2 caCreator,auditor.fname_l2 caAuditor");
        	sb.append("	from T_CON_ChangeAuditBill ca left join T_FDC_ChangeType ct on ct.fid=ca.FAuditTypeID left join T_FDC_ChangeReason reason on reason.fid=ca.FChangeReasonID left join T_CON_ChangeSupplierEntry caEntry on ca.fid=caEntry.FParentID left join t_con_contractBill contract on contract.fid=caEntry.FContractBillID");
        	sb.append(" left join t_bd_supplier supplier on supplier.fid=contract.fpartBId left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId left join T_CON_ContractChangeBill cb on cb.fid=caEntry.FContractChangeID left join T_ORG_Admin org on org.fid=cb.FConductDeptID left join T_BD_Supplier unit on unit.fid=cb.FConductUnitID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.FConChangeBillID=cb.fid");
        	sb.append(" left join t_pm_user creator on creator.fid=ca.fcreatorId left join t_pm_user auditor on auditor.fid=ca.fauditorId");
        	if(CONFIRM){
        		sb.append(" left join T_CON_ContractPCSplitBill split on split.FContractChangeSettleBillId=changeSettle.fid left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID where split.FContractChangeSettleBillId is not null and ca.fstate in('4AUDITTED','7ANNOUNCE')"); 
        	}else{
        		sb.append(" left join T_CON_ContractPCSplitBill split on split.FContractChangeBillId=cb.fid left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID where split.FContractChangeBillId is not null and ca.fstate in('4AUDITTED','7ANNOUNCE')"); 
        	}
        	if(curProjectinfo!=null&&number!=null){
        		sb.append(" and pro.fprojectID ='"+curProjectinfo.getId().toString()+"' and pc.flongNumber like '"+number+"%'");
        	}else{
        		sb.append(" and ca.FCurProjectID ='null'");
        	}
        	if(changeTypeId!=null){
        		sb.append(" and ct.fid ='"+changeTypeId+"'");
        	}
        	sb.append(" )t order by t.contractTypeNumber,(case when t.contractPropert='SUPPLY' then t.mainContractNumber else t.contractNumber end),t.contractPropert,t.caNumber,t.cbNumber,t.cbName");
    	}else{
    		sb.append(" select '' contractType,contract.fid id,ca.fid caId,cb.fid cbId,'' number,'' name,'' supplier,'' amount,ct.fname_l2 changeType,reason.fname_l2 reason,ca.fnumber caNumber,(case when cb.fid is not null then 1 else 0 end)copies,cb.fnumber cbNumber,cb.fname cbName,cb.freaDesc changeContent,(case when org.fname_l2 is null then unit.fname_l2 else org.fname_l2 end) conductDept,cb.fspecialtyTypeName specialtyTypeName,cb.famount cbAmount,");
        	sb.append("	(case when cb.fstate in('4AUDITTED','7ANNOUNCE') then cb.famount else 0 end) UNCONFIRM,(case when cb.fstate='8VISA' then (changeSettle.fallowAmount*cb.fexRate) else 0 end) CONFIRM,(case when cb.fstate='8VISA' then 1 else 0 end)confirmCopies,ca.fbookedDate caBizDate, (case when cb.fstate!='8VISA' then null else changeSettle.fauditTime end) csAuditDate,creator.fname_l2 caCreator,auditor.fname_l2 caAuditor");
        	sb.append("	from T_CON_ChangeAuditBill ca left join T_FDC_ChangeType ct on ct.fid=ca.FAuditTypeID left join T_FDC_ChangeReason reason on reason.fid=ca.FChangeReasonID left join T_CON_ChangeSupplierEntry caEntry on ca.fid=caEntry.FParentID left join t_con_contractBill contract on contract.fid=caEntry.FContractBillID");
        	sb.append(" left join T_CON_ProgrammingContract pc on pc.fid=contract.fProgrammingContract left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join t_bd_supplier supplier on supplier.fid=contract.fpartBId left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId left join T_CON_ContractChangeBill cb on cb.fid=caEntry.FContractChangeID left join T_ORG_Admin org on org.fid=cb.FConductDeptID left join T_BD_Supplier unit on unit.fid=cb.FConductUnitID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.FConChangeBillID=cb.fid");
        	sb.append(" left join t_pm_user creator on creator.fid=ca.fcreatorId left join t_pm_user auditor on auditor.fid=ca.fauditorId"); 
        	sb.append(" where ca.fstate in('4AUDITTED','7ANNOUNCE')");
        	if(curProject!=null){
        		sb.append(" and ca.FCurProjectID in("+curProject+")");
        	}else{
        		sb.append(" and ca.FCurProjectID ='null'");
        	}
        	if(changeTypeId!=null){
        		sb.append(" and ct.fid ='"+changeTypeId+"'");
        	}
        	sb.append(" order by contractType.flongNumber,(case when contract.fcontractPropert='SUPPLY' then contract.fmainContractNumber else contract.fnumber end),contract.fcontractPropert,ca.fnumber,cb.fnumber");
    	}
    	RptRowSet detailRowSet = executeQuery(sb.toString(), null, from, len, ctx);
    	while(detailRowSet.next()){
    		String id=detailRowSet.getString("id");
			if(detailValue.containsKey(id)){
				((List)detailValue.get(id)).add(detailRowSet.toRowArray());
			}else{
				List list=new ArrayList();
				list.add(detailRowSet.toRowArray());
				detailValue.put(id, list);
			}
    	}
    	Object[] key = value.keySet().toArray(); 
		Arrays.sort(key); 
		params.setObject("value", value);
		params.setObject("key", key);
    	params.setObject("detailValue", detailValue);
    	
    	sb = new StringBuffer();
    	sb.append(" select c.fname_l2 name,a.fid id from T_CON_ContractChangeBill a left join T_CON_SpecialtyTypeEntry b on b.FCONTRACCHANGEPARENTID=a.fid left join T_FDC_SpecialtyType c on c.fid=b.FSPECIALTYTYPEID where 1=1");
    	if(curProject!=null){
    		sb.append(" and a.FCurProjectID in("+curProject+")");
    	}else{
    		sb.append(" and a.FCurProjectID ='null'");
    	}
    	RptRowSet stRowSet = executeQuery(sb.toString(), null, from, len, ctx);
    	Map spMap=new HashMap();
    	while(stRowSet.next()){
    		String id=stRowSet.getString("id");
    		String name=stRowSet.getString("name");
    		if(spMap.containsKey(id)){
				((List)spMap.get(id)).add(name);
			}else{
				List list=new ArrayList();
				list.add(name);
				spMap.put(id, list);
			}
    	}
    	params.setObject("spMap", spMap);
		return params;
    }
    protected String getSql(Context ctx,RptParams params){
    	String curProject=(String) params.getObject("curProject");
    	CurProjectInfo curProjectinfo=(CurProjectInfo) params.getObject("curProjectInfo");
    	String number=(String)params.getObject("number");
    	String changeTypeId=(String)params.getObject("changeTypeId");
    	Boolean isClick=(Boolean)params.getObject("isClick");
    	Boolean CONFIRM=(Boolean)params.getObject("CONFIRM");
    	StringBuffer sb = new StringBuffer();
    	if(isClick!=null&&isClick){
    		sb.append(" select t.contractType,t.id,t.caId,t.cbId,t.number,t.name,t.supplier,t.amount,t.changeType,t.reason,t.caNumber,t.copies,t.cbNumber,t.cbName,t.changeContent,t.conductDept,t.specialtyTypeName,t.cbAmount,t.UNCONFIRM,t.CONFIRM,t.confirmCopies,t.caBizDate,t.csAuditDate,t.caCreator,t.caAuditor from(");
    		sb.append(" select contractType.flongNumber contractTypeNumber,contract.fmainContractNumber mainContractNumber,contract.fcontractPropert contractPropert,REPLACE(contractType.flongNumber, '!', '.')||'   '||contractType.fname_l2 contractType,contract.fid id,'' caId,'' cbId,contract.fnumber number,contract.fname name,supplier.fname_l2 supplier,contract.famount amount,'' changeType,'' reason,'' caNumber,sum(case when cb.fid is not null then 1 else 0 end)copies,'' cbNumber,'' cbName,'' changeContent,'' conductDept,'' specialtyTypeName,sum(cb.famount) cbAmount,");
        	sb.append("	sum(case when cb.fstate in('4AUDITTED','7ANNOUNCE') then cb.famount else 0 end) UNCONFIRM,sum(case when cb.fstate='8VISA' then (changeSettle.fallowAmount*cb.fexRate) else 0 end) CONFIRM,sum(case when cb.fstate='8VISA' then 1 else 0 end)confirmCopies,'' caBizDate, '' csAuditDate,'' caCreator,'' caAuditor");
        	sb.append("	from T_CON_ChangeAuditBill ca left join T_FDC_ChangeType ct on ct.fid=ca.FAuditTypeID left join T_CON_ChangeSupplierEntry caEntry on ca.fid=caEntry.FParentID left join t_con_contractBill contract on contract.fid=caEntry.FContractBillID");
        	sb.append(" left join T_CON_ProgrammingContract pc on pc.fid=contract.fProgrammingContract left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join t_bd_supplier supplier on supplier.fid=contract.fpartBId left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId left join T_CON_ContractChangeBill cb on cb.fid=caEntry.FContractChangeID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.FConChangeBillID=cb.fid");
        	sb.append(" where ca.fstate in('4AUDITTED','7ANNOUNCE')"); 
        	if(curProjectinfo!=null&&number!=null){
        		sb.append(" and pro.fprojectID ='"+curProjectinfo.getId().toString()+"' and pc.flongNumber like '"+number+"%'");
        	}else{
        		sb.append(" and ca.FCurProjectID ='null'");
        	}
        	if(changeTypeId!=null){
        		sb.append(" and ct.fid ='"+changeTypeId+"'");
        	}
        	sb.append(" group by contract.fmainContractNumber,contract.fcontractPropert,contractType.flongNumber,contractType.fname_l2,contract.fid,contract.fnumber,contract.fname,supplier.fname_l2,contract.famount");
        	sb.append(" union all select contractType.flongNumber contractTypeNumber,contract.fmainContractNumber mainContractNumber,contract.fcontractPropert contractPropert,REPLACE(contractType.flongNumber, '!', '.')||'   '||contractType.fname_l2 contractType,contract.fid id,'' caId,'' cbId,contract.fnumber number,contract.fname name,supplier.fname_l2 supplier,contract.famount amount,'' changeType,'' reason,'' caNumber,sum(case when cb.fid is not null then 1 else 0 end)copies,'' cbNumber,'' cbName,'' changeContent,'' conductDept,'' specialtyTypeName,sum(cb.famount) cbAmount,");
        	sb.append("	sum(case when cb.fstate in('4AUDITTED','7ANNOUNCE') then cb.famount else 0 end) UNCONFIRM,sum(case when cb.fstate='8VISA' then (changeSettle.fallowAmount*cb.fexRate) else 0 end) CONFIRM,sum(case when cb.fstate='8VISA' then 1 else 0 end)confirmCopies,'' caBizDate, '' csAuditDate,'' caCreator,'' caAuditor");
        	sb.append("	from T_CON_ChangeAuditBill ca left join T_FDC_ChangeType ct on ct.fid=ca.FAuditTypeID left join T_CON_ChangeSupplierEntry caEntry on ca.fid=caEntry.FParentID left join t_con_contractBill contract on contract.fid=caEntry.FContractBillID");
        	sb.append(" left join t_bd_supplier supplier on supplier.fid=contract.fpartBId left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId left join T_CON_ContractChangeBill cb on cb.fid=caEntry.FContractChangeID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.FConChangeBillID=cb.fid");
        	if(CONFIRM){
        		sb.append(" left join T_CON_ContractPCSplitBill split on split.FContractChangeSettleBillId=changeSettle.fid left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID where split.FContractChangeSettleBillId is not null and ca.fstate in('4AUDITTED','7ANNOUNCE')"); 
        	}else{
        		sb.append(" left join T_CON_ContractPCSplitBill split on split.FContractChangeBillId=cb.fid left join T_CON_ContractPCSplitBillEntry entry on split.fid=entry.fheadId left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID where split.FContractChangeBillId is not null and ca.fstate in('4AUDITTED','7ANNOUNCE')"); 
        	}
        	if(curProjectinfo!=null&&number!=null){
        		sb.append(" and pro.fprojectID ='"+curProjectinfo.getId().toString()+"' and pc.flongNumber like '"+number+"%'");
        	}else{
        		sb.append(" and ca.FCurProjectID ='null'");
        	}
        	if(changeTypeId!=null){
        		sb.append(" and ct.fid ='"+changeTypeId+"'");
        	}
        	sb.append(" group by contract.fmainContractNumber,contract.fcontractPropert,contractType.flongNumber,contractType.fname_l2,contract.fid,contract.fnumber,contract.fname,supplier.fname_l2,contract.famount");
        	sb.append(" )t order by t.contractTypeNumber,(case when t.contractPropert='SUPPLY' then t.mainContractNumber else t.number end),t.contractPropert");
    	}else{
    		sb.append(" select REPLACE(contractType.flongNumber, '!', '.')||'   '||contractType.fname_l2 contractType,contract.fid id,'' caId,'' cbId,contract.fnumber number,contract.fname name,supplier.fname_l2 supplier,contract.famount amount,'' changeType,'' reason,'' caNumber,sum(case when cb.fid is not null then 1 else 0 end)copies,'' cbNumber,'' cbName,'' changeContent,'' conductDept,'' specialtyTypeName,sum(cb.famount) cbAmount,");
        	sb.append("	sum(case when cb.fstate in('4AUDITTED','7ANNOUNCE') then cb.famount else 0 end) UNCONFIRM,sum(case when cb.fstate='8VISA' then (changeSettle.fallowAmount*cb.fexRate) else 0 end) CONFIRM,sum(case when cb.fstate='8VISA' then 1 else 0 end)confirmCopies,'' caBizDate, '' csAuditDate,'' caCreator,'' caAuditor");
        	sb.append("	from T_CON_ChangeAuditBill ca left join T_FDC_ChangeType ct on ct.fid=ca.FAuditTypeID left join T_CON_ChangeSupplierEntry caEntry on ca.fid=caEntry.FParentID left join t_con_contractBill contract on contract.fid=caEntry.FContractBillID");
        	sb.append(" left join T_CON_ProgrammingContract pc on pc.fid=contract.fProgrammingContract left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join t_bd_supplier supplier on supplier.fid=contract.fpartBId left join T_FDC_ContractType contractType on contractType.fid=contract.fcontractTypeId left join T_CON_ContractChangeBill cb on cb.fid=caEntry.FContractChangeID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.FConChangeBillID=cb.fid");
        	sb.append(" where ca.fstate in('4AUDITTED','7ANNOUNCE')"); 
        	if(curProject!=null){
        		sb.append(" and ca.FCurProjectID in("+curProject+")");
        	}else{
        		sb.append(" and ca.FCurProjectID ='null'");
        	}
        	sb.append(" group by contract.fmainContractNumber,contract.fcontractPropert,contractType.flongNumber,contractType.fname_l2,contract.fid,contract.fnumber,contract.fname,supplier.fname_l2,contract.famount");
        	sb.append(" order by contractType.flongNumber,(case when contract.fcontractPropert='SUPPLY' then contract.fmainContractNumber else contract.fnumber end),contract.fcontractPropert");
    	}
    	return sb.toString();
    }
}