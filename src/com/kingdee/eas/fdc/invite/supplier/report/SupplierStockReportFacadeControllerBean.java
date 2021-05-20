package com.kingdee.eas.fdc.invite.supplier.report;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
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

import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.LevelSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class SupplierStockReportFacadeControllerBean extends AbstractSupplierStockReportFacadeControllerBean
{
    private static Logger logger =Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.report.SupplierStockReportFacadeControllerBean");
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
	    initColoum(header,col,"seq",100,false);
	    initColoum(header,col,"purchaseOrgUnit",100,false);
	    initColoum(header,col,"fileType",100,false);
	    initColoum(header,col,"inviteType",100,false);
	    initColoum(header,col,"storageNumber",100,false);
	    initColoum(header,col,"name",250,false);
	    initColoum(header,col,"contractor",100,false);
	    initColoum(header,col,"contractorPhone",100,false);
	    initColoum(header,col,"isPass",100,false);
	    initColoum(header,col,"quaLevel",100,false);
	    initColoum(header,col,"levelSetUpId",100,true);
	    initColoum(header,col,"levelSetUp",100,true);
	    initColoum(header,col,"grade",100,true);
	    initColoum(header,col,"splArea",100,false);
	    initColoum(header,col,"contractor",100,true);
	    initColoum(header,col,"kcId",100,true);
	    initColoum(header,col,"kcScore",100,false);
	    initColoum(header,col,"storageDate",100,false);
	    initColoum(header,col,"inviteName",100,false);
	    initColoum(header,col,"lyzhId",100,true);
	    initColoum(header,col,"lyzhScore",100,false);
	    initColoum(header,col,"lyzhDate",100,false);
	    initColoum(header,col,"curProject",100,false);
	    initColoum(header,col,"contractId",100,true);
	    initColoum(header,col,"contractName",100,false);
	    initColoum(header,col,"contractAmount",100,false);
	    initColoum(header,col,"manager",100,true);
	    initColoum(header,col,"stage",100,false);
	    initColoum(header,col,"lypgId",100,true);
	    initColoum(header,col,"lypgScore",100,true);
	    initColoum(header,col,"lypgDate",100,true);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"id","���","������֯","��Ӧ�����","�ɹ����","��Ӧ��������","��Ӧ������","��Ŀ������","��Ŀ������","�Ƿ�ϸ�","���ʵȼ�","levelSetUpId","��Ӧ�̼���","��Ӧ�̵ȼ�","��������","ʵ�ʳа���","kcId","����÷�","�������",
	    			"��Ͷ��������","lyzhId","��Լ�ۺ�����","��Լ�ۺ���������",
	    			"����״̬","����״̬","����״̬","����״̬","����״̬","����״̬","����״̬","����״̬","����״̬"
	    		}
	    		,
	    		{
	    			"id","���","������֯","��Ӧ�����","�ɹ����","��Ӧ��������","��Ӧ������","ʵ�ʳа���","��ϵ�绰","�Ƿ�ϸ�","���ʵȼ�","levelSetUpId","��Ӧ�̼���","��Ӧ�̵ȼ�","��������","ʵ�ʳа���","kcId","����÷�","�������",
	    			"��Ͷ��������","lyzhId","��Լ�ۺ�����","��Լ�ۺ���������",
	    			"������Ŀ","contractId","��ͬ����","��ͬ���","��Ŀ����","�׶�","lypgId","��Լ�����÷�","��Լ��������"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
		params.setObject("rowset", rowSet);
		
		Map supplier=new HashMap();
		RptRowSet curProjectRowSet = executeQuery(getCurProjectSql(ctx,params), null, from, len, ctx);
		while(curProjectRowSet.next()){
			String name="��"+curProjectRowSet.getString("curProjectName")+"�� "+curProjectRowSet.getString("inviteName")+";\n";
			if(supplier.containsKey(curProjectRowSet.getString("id"))){
				name=(String) supplier.get(curProjectRowSet.getString("id"))+name;
			}
			supplier.put(curProjectRowSet.getString("id"), name);
		}
		params.setObject("curProject", supplier);
		return params;
    }
    protected String getCurProjectSql(Context ctx,RptParams params){
    	String inviteType = (String) params.getObject("inviteType");
    	String org = (String) params.getObject("org");
    	String name=(String)params.getObject("name");
    	boolean isAll=params.getBoolean("isAll");
    	IsGradeEnum isPass=(IsGradeEnum)params.getObject("isPass");
    	StringBuffer quaLevel =null;
	    if(params.getObject("quaLevel")!=null){
	    	quaLevel=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("quaLevel");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		quaLevel.append("'"+((QuaLevelInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		quaLevel.append(",'"+((QuaLevelInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer grade =null;
	    if(params.getObject("grade")!=null){
	    	grade=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("grade");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		grade.append("'"+((GradeSetUpInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		grade.append(",'"+((GradeSetUpInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer level =null;
	    if(params.getObject("level")!=null){
	    	level=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("level");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		level.append("'"+((LevelSetUpInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		level.append(",'"+((LevelSetUpInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer curProject =null;
	    if(params.getObject("curProject")!=null){
	    	curProject=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("curProject");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		curProject.append("'"+((CurProjectInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		curProject.append(",'"+((CurProjectInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select distinct supplier.fid id,inviteProject.fname inviteName,inviteProject.fcurProjectName curProjectName from T_INV_SupplierQualifyEntry entry left join T_FDC_SupplierStock supplier on supplier.fid=entry.fsupplierId");
    	sb.append(" left join T_INV_InviteType inviteType on inviteType.fid=supplier.finviteTypeId");
    	sb.append(" left join T_ORG_Purchase purchaseOrgUnit on purchaseOrgUnit.fid=supplier.FPurchaseOrgUnitId");
    	sb.append(" left join T_INV_SupplierQualify qualify on entry.FParentID=qualify.fid");
		sb.append(" left join T_INV_InviteProject inviteProject on inviteProject.fid=qualify.finviteProjectId");
    	sb.append(" where qualify.fstate='4AUDITTED'");
    	if(inviteType!=null){
    		sb.append(" and inviteType.flongNumber like '"+inviteType+"%'");
    	}
    	if(org!=null){
    		sb.append(" and purchaseOrgUnit.flongNumber like '"+org+"%'");
    	}
    	if(name!=null){
    		sb.append(" and supplier.fname_l2 like '%"+name+"%'");
    	}
    	if(quaLevel!=null){
    		sb.append(" and supplier.FQuaLevelId in("+quaLevel.toString()+")");
    	}
    	if(grade!=null){
    		sb.append(" and supplier.FGradeId in("+grade.toString()+")");
    	}
    	if(level!=null){
    		sb.append(" and supplier.FLevelId in("+level.toString()+")");
    	}
    	if(!isAll&&isPass!=null){
    		sb.append(" and supplier.fisPass ='"+isPass.getValue()+"'");
    	}
    	if(fromDate!=null){
    		sb.append(" and supplier.fstorageDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and supplier.fstorageDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
    	return sb.toString();
    }
    protected String getSql(Context ctx,RptParams params){
    	String inviteType = (String) params.getObject("inviteType");
    	String org = (String) params.getObject("org");
    	String name=(String)params.getObject("name");
    	boolean isAll=params.getBoolean("isAll");
    	boolean wqht=params.getBoolean("wqht");
    	boolean wjs=params.getBoolean("wjs");
    	boolean yjs=params.getBoolean("yjs");
    	IsGradeEnum isPass=(IsGradeEnum)params.getObject("isPass");
    	StringBuffer quaLevel =null;
	    if(params.getObject("quaLevel")!=null){
	    	quaLevel=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("quaLevel");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		quaLevel.append("'"+((QuaLevelInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		quaLevel.append(",'"+((QuaLevelInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer grade =null;
	    if(params.getObject("grade")!=null){
	    	grade=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("grade");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		grade.append("'"+((GradeSetUpInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		grade.append(",'"+((GradeSetUpInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer level =null;
	    if(params.getObject("level")!=null){
	    	level=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("level");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		level.append("'"+((LevelSetUpInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		level.append(",'"+((LevelSetUpInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    StringBuffer curProject =null;
	    if(params.getObject("curProject")!=null){
	    	curProject=new StringBuffer();
	    	Object[] userObject = (Object[])params.getObject("curProject");
        	for(int i=0;i<userObject.length;i++){
            	if(i==0){
            		curProject.append("'"+((CurProjectInfo)userObject[i]).getId().toString()+"'");
            	}else{
            		curProject.append(",'"+((CurProjectInfo)userObject[i]).getId().toString()+"'");
            	}
            }
	    }
	    Date fromDate = (Date)params.getObject("fromDate");
    	Date toDate =   (Date)params.getObject("toDate");
    	
    	boolean isAllContract=params.getBoolean("isAllContract");

    	StringBuffer sb = new StringBuffer();
    	sb.append(" select supplier.fid id,'' seq,purchaseOrgUnit.fname_l2 purchaseOrgUnit,fileType.fname_l2 fileType,inviteType.fname_l2 inviteType,supplier.fstorageNumber storageNumber,supplier.fname_l2 name,supplier.fcontractor contractor,supplier.fcontractorPhone contractorPhone,supplier.fisPass,quaLevel.fname_l2 quaLevel,jb.id levelSetUpId,levelSetUp.fname_l2 levelSetUp,grade.fname_l2 grade,supplier.fsplArea splArea,");
    	sb.append(" supplier.fcontractor contractor,kc.id kcId,kc.score kcScore,supplier.fstorageDate storageDate,'' inviteName,lyzh.id lyzhId,lyzh.score lyzhScore,");
    	sb.append(" lyzh.auditTime lyzhDate,contract.curProject curProject,contract.contractId contractId,contract.contractName contractName,contract.amount contractAmount,'' manager,(case when contract.stage is null then 'δǩ��ͬ' else contract.stage end) stage,'' lypgId,'' lypgScore,'' lypgDate");
    	sb.append(" from T_FDC_SupplierStock supplier left join T_INV_InviteType inviteType on inviteType.fid=supplier.finviteTypeId");
    	sb.append(" left join T_GYS_SupplierFileType fileType on fileType.fid=supplier.fsupplierfileTypeId");
    	sb.append(" left join T_GYS_QuaLevel quaLevel on quaLevel.fid=supplier.FQuaLevelId");
    	sb.append(" left join T_GYS_LevelSetUp levelSetUp on levelSetUp.fid=supplier.FLevelId");
    	sb.append(" left join T_FDC_GradeSetUp grade on grade.fid=supplier.FGradeId");
    	sb.append(" left join T_ORG_Purchase purchaseOrgUnit on purchaseOrgUnit.fid=supplier.FPurchaseOrgUnitId");
    	sb.append(" left join (select gather.fid id,gather.fsupplierId,gather.famount score,gather.fbizDate auditTime from T_GYS_SupplierReviewGather gather left join(select gather.fsupplierId,max(gather.fbizDate) fbizDate from T_GYS_SupplierReviewGather gather left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId where type.fnumber='002' and gather.fstate='4AUDITTED' group by gather.fsupplierId) gather1 on gather1.fsupplierId=gather.fsupplierId left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId where type.fnumber='002' and gather.fstate='4AUDITTED' and gather1.fbizDate=gather.fbizDate)kc on kc.fsupplierId=supplier.fid");
    	sb.append(" left join (select gather.fid id,gather.fsupplierId,gather.famount score,gather.fbizDate auditTime from T_GYS_SupplierReviewGather gather left join(select gather.fsupplierId,max(gather.fbizDate) fbizDate from T_GYS_SupplierReviewGather gather left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId where type.fnumber='005' and gather.fstate='4AUDITTED' group by gather.fsupplierId) gather1 on gather1.fsupplierId=gather.fsupplierId left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId where type.fnumber='005' and gather.fstate='4AUDITTED' and gather1.fbizDate=gather.fbizDate)lyzh on lyzh.fsupplierId=supplier.fid");
//    	sb.append(" left join (select gather.fid id,gather.fsupplierId,gather.famount score,gather.fbizDate auditTime from T_GYS_SupplierReviewGather gather left join(select gather.fsupplierId,max(gather.fbizDate) fbizDate from T_GYS_SupplierReviewGather gather left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId where type.fnumber in('003','004') and gather.fstate='4AUDITTED' group by gather.fsupplierId) gather1 on gather1.fsupplierId=gather.fsupplierId left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId where type.fnumber in('003','004') and gather.fstate='4AUDITTED' and gather1.fbizDate=gather.fbizDate)lypg on lypg.fsupplierId=supplier.fid");
    	sb.append(" left join (select gather.fid id,gather.fsupplierId,gather.fbizDate auditTime from T_GYS_SupplierReviewGather gather left join(select gather.fsupplierId,max(gather.fbizDate) fbizDate from T_GYS_SupplierReviewGather gather left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId where type.fnumber in('006','007') and gather.fstate='4AUDITTED' group by gather.fsupplierId) gather1 on gather1.fsupplierId=gather.fsupplierId left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId where type.fnumber in('006','007') and gather.fstate='4AUDITTED' and gather1.fbizDate=gather.fbizDate)jb on jb.fsupplierId=supplier.fid");
//    	sb.append(" left join (select qualifyEntry.fsupplierId,inviteProject.fname name from T_INV_SupplierQualifyEntry qualifyEntry left join T_INV_SupplierQualify qualify on qualify.fid=qualifyEntry.FParentID left join T_INV_InviteProject inviteProject on qualify.finviteProjectId=inviteProject.fid where qualify.fstate='4AUDITTED') invite on invite.fsupplierId=supplier.fid");
//    	if(isAllContract){
    		sb.append(" left join (select contract.fid contractId,contract.fname contractName,contract.FPartBID supplierId,(case when contract.FHasSettled=1 then '�ѽ���' else 'δ����' end) stage,curProject.fid curProjectId,curProject.fname_l2 curProject,contract.famount amount from t_con_contractBill contract left join t_fdc_curProject curProject on curProject.fid=contract.fcurprojectid where contract.fstate='4AUDITTED') contract on contract.supplierId=supplier.FSysSupplierID");
//    	}else{
//    		sb.append(" left join (select gather.fid lypgId,gather.famount lypgScore,gather.fbizDate lypgDate,rgEntry.fmanager manager,contract.famount amount,contract.fid contractId,contract.fname contractName,gather.fsupplierId supplierId,(case when contract.FHasSettled=1 then '�ѽ���' else 'δ����' end) stage,curProject.fid curProjectId,curProject.fname_l2 curProject from T_GYS_SRGContractEntry rgEntry left join T_GYS_SupplierReviewGather gather on rgEntry.fheadId=gather.fid left join(select rgEntry.fcontractBillId,gather.fsupplierId,max(gather.fbizDate) fbizDate from T_GYS_SupplierReviewGather gather left join T_GYS_SRGContractEntry rgEntry on rgEntry.fheadId=gather.fid left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId where type.fnumber in('003','004') and gather.fstate='4AUDITTED' group by rgEntry.fcontractBillId,gather.fsupplierId) gather1 on gather1.fsupplierId=gather.fsupplierId left join T_GYS_SupplierEvaluationType type on type.fid=gather.FEvaluationTypeId left join t_con_contractBill contract on rgEntry.fcontractBillId=contract.fid left join T_FDC_ContractType ct on ct.fid=contract.FContractTypeID left join t_fdc_curProject curProject on curProject.fid=contract.fcurprojectid where contract.fstate='4AUDITTED' and type.fnumber in('003','004') and gather.fstate='4AUDITTED' and gather1.fbizDate=gather.fbizDate and gather1.fcontractBillId=rgEntry.fcontractBillId and ct.fname_l2 !='�������ͬ') contract on contract.supplierId=supplier.fid");
//    	}
    	sb.append(" where 1=1");
    	if(inviteType!=null){
    		sb.append(" and inviteType.flongNumber like '"+inviteType+"%'");
    	}
    	if(org!=null){
    		sb.append(" and purchaseOrgUnit.flongNumber like '"+org+"%'");
    	}
    	if(name!=null){
    		sb.append(" and supplier.fname_l2 like '%"+name+"%'");
    	}
    	if(quaLevel!=null){
    		sb.append(" and quaLevel.fid in("+quaLevel.toString()+")");
    	}
    	if(grade!=null){
    		sb.append(" and grade.fid in("+grade.toString()+")");
    	}
    	if(level!=null){
    		sb.append(" and levelSetUp.fid in("+level.toString()+")");
    	}
    	if(curProject!=null){
    		sb.append(" and contract.curProjectId in("+curProject.toString()+")");
    	}
    	if(!isAll&&isPass!=null){
    		sb.append(" and supplier.fisPass ='"+isPass.getValue()+"'");
    	}
    	if(fromDate!=null){
    		sb.append(" and supplier.fstorageDate>={ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(fromDate))+ "'}");
		}
		if(toDate!=null){
			sb.append(" and supplier.fstorageDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate))+ "'}");
		}
		if(!(wqht&&wjs&&yjs)){
			if(wqht&&wjs){
				sb.append(" and (contract.stage is null or contract.stage='δ����')");
			}else if(wjs&&yjs){
				sb.append(" and (contract.stage='δ����' or contract.stage='�ѽ���')");
			}else if(yjs&&wqht){
				sb.append(" and (contract.stage='�ѽ���' or contract.stage is null)");
			}else if(wqht){
				sb.append(" and contract.stage is null");
			}else if(wjs){
				sb.append(" and contract.stage='δ����'");
			}else if(yjs){
				sb.append(" and contract.stage='�ѽ���'");
			}
		}
		
    	sb.append(" order by purchaseOrgUnit.flongNumber,inviteType.flongNumber,fileType.fnumber,supplier.fnumber");
    	return sb.toString();
    }
}