package com.kingdee.eas.fdc.finance.app;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.ormapping.SQLAccessException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.ISystemStatusCtrol;
import com.kingdee.eas.basedata.assistant.PeriodException;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolFactory;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProjectPeriodStatusControllerBean extends AbstractProjectPeriodStatusControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusControllerBean");


	
	protected Map _closeInit(Context ctx, List projectIds, String curOrgId, boolean isCompany) throws BOSException, EASBizException {
		
		//û�н�����ʼ���Ľ�����ʼ��
		String statusSelectSql = " Select a.fid ,a.fprojectid,a.FIsClosed,a.Fstartperiodid,b.ffullorgunit from T_FNC_ProjectPeriodStatus  a   \r\n"
				+ " inner join t_fdc_curproject b on a.fprojectid=b.fid where a.fid=? ";//and   a.FIsClosed=0 
		String updateSql = 		"update T_FNC_ProjectPeriodStatus set FIsClosed=1 where fprojectid=? ";
		
		IRowSet rs2 = null;
        CompanyOrgUnitInfo companyInfo = null ;	        
        Map comMap = new HashMap();
        
		//������ʼ��
		for(int i=0,count=projectIds.size();i<count;i++){
			String statusId = (String)projectIds.get(i);
			
			//�ж��Ƿ��Ѿ�����T_FNC_ProjectPeriodStatus
			rs2 = DbUtil.executeQuery(ctx,statusSelectSql,new Object[]{statusId});

			//�������е����ڼ�Ϊ�յ�����
			String periodId;
			try {
				if(rs2!=null && rs2.next()){
					String projectId =  rs2.getString("fprojectid");	
					periodId = rs2.getString("Fstartperiodid");		
					
					//������Ŀ������ʼ��
					DbUtil.execute(ctx,updateSql,new Object[]{projectId}); 
					
					//��¼��֯
					String comId = rs2.getString("ffullorgunit");
					if(!comMap.containsKey(comId)){
						comMap.put(rs2.getString("ffullorgunit"),Boolean.TRUE);
					}
					
					//ͬ������
					syscBill(ctx,projectId,comId,periodId);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		
		String statusComSql = " Select a.fid ,a.FIsClosed,a.Fstartperiodid,b.ffullorgunit from T_FNC_ProjectPeriodStatus  a   \r\n"
			+ " inner join t_fdc_curproject b on a.fprojectid=b.fid where b.ffullorgunit=? and   a.FIsClosed=0 ";
		//������й�����Ŀ������ʼ����,��ô:������֯����
		
		//������֯����
		Set set = comMap.keySet();
		Iterator it = set.iterator();
		try {
			while(it.hasNext()){
				String comId = (String)it.next();
				//��˾
				rs2 = DbUtil.executeQuery(ctx,statusComSql,new Object[]{comId});

				if(rs2==null ||  !rs2.next()){
					companyInfo = GlUtils.getCurrentCompany(ctx,comId,null,false);
		            ISystemStatusCtrol sysStatus = SystemStatusCtrolFactory.getLocalInstance(ctx);
		            sysStatus.start(SystemEnum.FDC, companyInfo,true);
				}			
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		
		return null;//_fetchInitData( ctx,  curOrgId,  isCompany);
	}

	/**
	 * �رչ��̣��ر�֮�󣬲����ټ���������ͬ�����ı������������ݿ��Ժ���������
	 */
	protected void _closeProject(Context ctx, List projectStatusIds) throws BOSException, EASBizException {
		//�ҵ�����֯���еĹ�����Ŀ
		String lang = ctx.getLocale().getLanguage();
		StringBuffer selectCurProSql = new StringBuffer();
		selectCurProSql.append("Select b.fid ,a.FIsEnd,a.FIsClosed ,a.FCostPeriodID,FFinacialPeriodID,b.FDisplayName_"+lang);
		selectCurProSql.append("  FDisplayName from T_FNC_ProjectPeriodStatus 	a							\r\n");
		selectCurProSql.append("��inner join t_fdc_curproject b on a.fprojectid=b.fid where a.fid=?    		\r\n");
		
		String updateSql = 		"update T_FNC_ProjectPeriodStatus set FIsEnd=1 where fprojectid=? ";
		
		IRowSet rs2 = null;
		try {
			for(int i=0,count=projectStatusIds.size();i<count;i++){
				String id = (String)projectStatusIds.get(i);
				
				rs2 = DbUtil.executeQuery(ctx,selectCurProSql.toString(),new Object[]{id});
				if(rs2!=null && rs2.next()){
					String projectId = rs2.getString("fid");
					String displayName = rs2.getString("FDisplayName");
					
					String FCostPeriodID = rs2.getString("FCostPeriodID");
					String FFinacialPeriodID = rs2.getString("FFinacialPeriodID");
					//������Ŀ{0}�Ѿ��ر���ʾ�ѹر�
					if(rs2.getBoolean("FIsEnd")){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASEND,new Object[]{displayName});
					}
					//������Ŀ{0}δ������ʼ�����ܹر�
					if(!rs2.getBoolean("FIsClosed")){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_UNINIT,new Object[]{displayName});
					}
					
					//������Ŀ{0}�ɱ��Ѿ��½ᣬ����ɱ�û���½᲻һ��
					if(rs2.getString("FCostPeriodID").equals("FFinacialPeriodID")){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASCLOSED,new Object[]{displayName});	
					}
					//������Ŀ{0}�ĵ�ǰ�ڼ��Ƿ�������˵ĺ�ͬ�����ı���ͬ��
					if(checkContractBill( ctx, projectId,FCostPeriodID,false)){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASCONTR,new Object[]{displayName});
					}
					if(checkContractBill( ctx, projectId,FFinacialPeriodID,true)){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASCONTRNO,new Object[]{displayName});
					}
					
					DbUtil.execute(ctx,updateSql,new Object[]{projectId});
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		
	}

	protected Map _closeAll(Context ctx, String orgUnit,boolean isCompany) throws BOSException, EASBizException {
		
		//�ҵ�����֯���еĹ�����Ŀ
		StringBuffer selectCurProSql = new StringBuffer();
		if(isCompany){//������֯
			selectCurProSql.append("select cp.fid Fid,cp.FstartDate FstartDate,cp.ffullorgunit from t_fdc_curproject cp 		\r\n");
			selectCurProSql.append("inner join t_org_baseUnit cbu on cp.ffullorgunit=cbu.fid									\r\n");
			selectCurProSql.append("inner join t_org_baseUnit pbu on charindex(pbu.FLongNumber || '!', cbu.FLongNumber || '!') = 1   				\r\n");
			selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1 and  pbu.fid=?		");
		}else{
			selectCurProSql.append("select cp.fid Fid,cp.FstartDate FstartDate ,cp.ffullorgunit from T_FDC_ProjectWithCostCenterOU wi 		\r\n");
			selectCurProSql.append("inner join t_fdc_curproject pcp on wi.fcurprojectid=pcp.fid				\r\n");
			selectCurProSql.append("inner join t_fdc_curproject cp on charindex(pcp.FLongNumber || '!', cp.FLongNumber || '!') = 1				\r\n");
			selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1 and wi.fcostcenterouid=?			");
		}
		
		//û�н�����ʼ���Ľ�����ʼ��
		String statusSelectSql = " Select fid ,FIsClosed,Fstartperiodid from T_FNC_ProjectPeriodStatus where fprojectid=?  ";
		String updateSql = 		"update T_FNC_ProjectPeriodStatus set FIsClosed=1 where fprojectid=? ";
	
		List projectList = new ArrayList();
		Map projectComMap =  new HashMap();
		Map projectStartPrdMap =  new HashMap();
		
		IRowSet rs = DbUtil.executeQuery(ctx,selectCurProSql.toString(),new Object[]{orgUnit}); 
		IRowSet rs2 = null;
        CompanyOrgUnitInfo companyInfo = null ;	
        
        Map comMap = new HashMap();
        Map comPeriodMap = new HashMap();
        
		if(rs!=null){
			try {
				while(rs.next()){
					String projectid = rs.getString("Fid");
					Date startDate = rs.getDate("FstartDate");
					String comId = rs.getString("ffullorgunit"); 
					
					//�ж��Ƿ��Ѿ�����T_FNC_ProjectPeriodStatus
					rs2 = DbUtil.executeQuery(ctx,statusSelectSql,new Object[]{projectid});
					
					CurProjectInfo curProject = new CurProjectInfo();
					curProject.setId(BOSUuid.read(projectid));
					FullOrgUnitInfo company = new FullOrgUnitInfo();
					company.setId(BOSUuid.read(comId));
					
					projectList.add(projectid);
					//������Ŀ��Ӧ�Ĳ�����֯
					projectComMap.put(projectid,comId);
						
					if(comMap.containsKey(comId)){
						companyInfo = (CompanyOrgUnitInfo)comMap.get(comId);
					}else{
						companyInfo = GlUtils.getCurrentCompany(ctx, comId,null,false) ;
						comMap.put(comId,companyInfo);
					}
					
					//�Ѿ�������ʼ��������
					boolean isClose = false;
					if(rs2!=null && rs2.next()){
						//������Ŀ��Ӧ���ڼ�
						projectStartPrdMap.put(projectid,rs2.getString("Fstartperiodid"));
						
						if(rs2.getBoolean("FIsClosed")){
							continue ;
						}
						isClose = true;
					}
					
					if(isClose)
					{
						//���ڵ�ǰ��֯��ProjectPeriodStatus�Ѿ��������ݵ�,��û�н�����ʼ������update
						DbUtil.execute(ctx,updateSql,new Object[]{projectid}); 
					}else{

						//��ȡ��ǰ�ڼ�
				        PeriodInfo period = null;
				        if(comPeriodMap.containsKey(comId)){
				        	period = (PeriodInfo)comPeriodMap.get(comId);
				        }else{
				        	period = //FDCUtils.getCurrentPeriod(ctx,projectid,true); 
				        		SystemStatusCtrolUtils.getCurrentPeriod(ctx, SystemEnum.FDC, companyInfo);
				        	comPeriodMap.put(comId,period);
				        }
				        if(period==null){
				        	throw new PeriodException(PeriodException.NOT_SET_STARTPERIOD);
				        }
				        
				        //����startDate��ȡһ���ڼ䣬���ĸ��ڼ���ǰ
				        PeriodInfo startperiod =  
				        	FDCUtils.fetchPeriod(ctx,companyInfo.getAccountPeriodType().getId().toString(),startDate);
				        	//PeriodUtils.getPeriodInfo(ctx,startDate,companyInfo);
				        if(startperiod!=null && startperiod.getBeginDate().after(period.getBeginDate())){
				        	period = startperiod;
				        }
				        
						//���ڵ�ǰ��֯��ProjectPeriodStatus���������ݵ�,��insert
						ProjectPeriodStatusInfo info = new ProjectPeriodStatusInfo();
						info.setIsClosed(true);
						info.setStartPeriod(period);
						info.setCostPeriod(period);
						info.setFinacialPeriod(period);
						info.setOrgUnit(company);
						info.setProject(curProject);
						
						_addnew(ctx,info);
						
						//������Ŀ��Ӧ���ڼ�
						projectStartPrdMap.put(projectid,period.getId().toString());
					}
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		
		//���õ���
		for(int i=0,count=projectList.size();i<count;i++){
			String projectId = (String)projectList.get(i);
			String comId = (String)projectComMap.get(projectId);
			String periodId = (String)projectStartPrdMap.get(projectId);
			
			syscBill(ctx,projectId,comId,periodId);
		}
		
		//������֯����
		Set set = comMap.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			String comId = (String)it.next();
			companyInfo = (CompanyOrgUnitInfo)comMap.get(comId);
            ISystemStatusCtrol sysStatus = SystemStatusCtrolFactory.getLocalInstance(ctx);
            sysStatus.start(SystemEnum.FDC, companyInfo,true);
		}
		

		return null;//_fetchInitData( ctx,  orgUnit,  isCompany);
	}
	
	private void syscBill(Context ctx,String projectId,String comId,String periodId) throws BOSException{
		//�������е����ڼ�Ϊ�յ�����
//		update  T_CON_ContractBill  set FBookedDate = FSignDate where FBookedDate is null and Fcurprojectid=? ;
//		update  T_CON_ContractWithoutText  set FBookedDate = FSignDate where FBookedDate is null;
//		update  T_CON_ContractBillRevise  set FBookedDate = FSignDate where FBookedDate is null;
//		update   T_CON_ContractSettlementBill   set FBookedDate = fcreatetime where FBookedDate is null;
//		update  T_CON_PayRequestBill   set FBookedDate = FPayDate where FBookedDate is null;
//		update  t_con_changeauditbill   set FBookedDate = fcreatetime where FBookedDate is null;
		
		//��ͬ
		String contractSql = "update T_CON_ContractBill set FBookedDate = FSignDate,FperiodId=? where ( FperiodId is null or FBookedDate is null) and Fcurprojectid=? ";
		DbUtil.execute(ctx,contractSql,new Object[]{periodId,projectId});
		
		//���ı���ͬ
		String contractWithSql = "update T_CON_ContractWithoutText set FBookedDate = FSignDate,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,contractWithSql,new Object[]{periodId,projectId});
		
		//�޶�����
		String contractRevSql = "update T_CON_ContractBillRevise set FBookedDate = FSignDate,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,contractRevSql,new Object[]{periodId,projectId});	
				
		//�������
		String changeauditSql = "update t_con_changeauditbill set FBookedDate = fcreatetime,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,changeauditSql,new Object[]{periodId,projectId});	
		
		String conchangeSql = "update t_con_contractchangebill set FBookedDate = fcreatetime,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,conchangeSql,new Object[]{periodId,projectId});	
		
		//���㵥
		String contractSettleSql = "update T_CON_ContractSettlementBill set FBookedDate = fcreatetime,FperiodId=? where ( FperiodId is null or FBookedDate is null) and Fcurprojectid=?   ";
		DbUtil.execute(ctx,contractSettleSql,new Object[]{periodId,projectId});	
		
		//�������뵥
		String payRequestSql = "update T_CON_PayRequestBill set FBookedDate = FPayDate,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,payRequestSql,new Object[]{periodId,projectId});	
		
		//��ͬ���
		String contractSplitSql = "update T_CON_ContractCostSplit set FBookedDate = FCreatetime,FperiodId=? where FperiodId is null and FContractBillid in (select fid from t_con_contractbill where fcurprojectid=?) ";
		DbUtil.execute(ctx, contractSplitSql, new Object[] { periodId,
				projectId });

		// ������
		String conChangeSplitSql = "update T_CON_ConChangeSplit set FBookedDate = FCreatetime,FperiodId=? where FperiodId is null and fcontractchangeid in (select fid from t_con_contractchangebill where fcurprojectid=?) ";
		DbUtil.execute(ctx, conChangeSplitSql, new Object[] { periodId,
				projectId });

		// ������
		String settlementSplitSql = "update t_Con_Settlementcostsplit set FBookedDate = FCreatetime,FperiodId=? where FperiodId is null and fsettlementbillid in (select fid from t_con_contractsettlementbill  where fcurprojectid=?) ";
		DbUtil.execute(ctx, settlementSplitSql, new Object[] { periodId,
				projectId });

		// ������
		String paymentSplitSql = "update T_FNC_PaymentSplit set FBookedDate = FCreatetime,FperiodId=? where FperiodId is null and FPaymentBillid in (select fid from t_cas_paymentbill where fcurprojectid=?) ";
		DbUtil.execute(ctx, paymentSplitSql, new Object[] { periodId,
				projectId });
		
		//�������ɱ�����
		String adjustSql = "update T_AIM_AdjustRecordEntry set FperiodId=? where FperiodId is null and FParentID in (select FID from T_AIM_DynamicCost where FAccountID in (select FID from T_FDC_CostAccount where FCurProject=?)) ";
		DbUtil.execute(ctx, adjustSql, new Object[] { periodId,
				projectId });
		
		//ָ��
		String prjIndexSql = "update T_FDC_ProjectIndexData set FperiodId=? where FperiodId is null and FProjOrOrgID=? ";
		DbUtil.execute(ctx, prjIndexSql, new Object[] { periodId,
				projectId });
		
		//Ŀ��ɱ�
		String aimCostSql = "update T_AIM_AimCost set FperiodId=? where FperiodId is null and FOrgOrProId=? ";
		DbUtil.execute(ctx, aimCostSql, new Object[] { periodId,
				projectId });
	}
	
	private boolean checkBill(Context ctx,String projectId,int i) throws BOSException, SQLException{
		
		//�Ƿ�
		String checkSql = " Select top 1 Fid from t_con_contractBill where fcurProjectId=?  and fstate='4AUDITTED' 			";
		if(i==2){
			 checkSql = "  Select top 1 Fid from t_con_contractwithouttext where fcurProjectId=?  and fstate='4AUDITTED' 		";
		}else if(i==3){
			checkSql = "  Select top 1 Fid from t_con_ChangeAuditBill where fcurProjectId=?  and fstate='4AUDITTED' 		";
		}
		
		IRowSet rs = DbUtil.executeQuery(ctx,checkSql,new Object[]{projectId});
		if(rs!=null && rs.next()){
			return true;
		}
		
		return false;
	}
	
	private boolean checkContractBill(Context ctx,String projectId,String periodId,boolean isNotext) throws BOSException, SQLException{
		
		//�Ƿ�
		String checkSql = (!isNotext)?" Select top 1 Fid from t_con_contractBill where fcurProjectId=? and fPeriodid=? and fstate='4AUDITTED' 			\r\n" 
				:"  Select top 1 Fid from t_con_contractwithouttext where fcurProjectId=?  and fPeriodid=? and fstate='4AUDITTED' 		";
		
		IRowSet rs = DbUtil.executeQuery(ctx,checkSql,new Object[]{projectId,periodId});
		if(rs!=null && rs.next()){
			return true;
		}
		
		return false;
	}

	protected Map _closeUnInit(Context ctx, List projectIds,String orgUnit, boolean isCompany) throws BOSException, EASBizException {
		
		//û�н�����ʼ���Ľ�����ʼ��
		String lang = ctx.getLocale().getLanguage();
		String statusSelectSql = " Select a.fid ,a.fprojectid,a.FIsClosed,a.Fstartperiodid,a.FCostPeriodID,b.ffullorgunit,b.FDisplayName_"+lang+
				" FDisplayName" 
				+ " from T_FNC_ProjectPeriodStatus  a   									\r\n"
				+ " inner join t_fdc_curproject b on a.fprojectid=b.fid where a.fid=? 		";//and   a.FIsClosed=0 
		
		
		String updateSql = 		"update T_FNC_ProjectPeriodStatus set FIsClosed=0 where fprojectid=? ";
		
		IRowSet rs2 = null;
        CompanyOrgUnitInfo companyInfo = null ;	        
        Map comMap = new HashMap();
        
		//������ʼ��
		for(int i=0,count=projectIds.size();i<count;i++){
			String statusId = (String)projectIds.get(i);
			
			//�ж��Ƿ��Ѿ�����T_FNC_ProjectPeriodStatus
			rs2 = DbUtil.executeQuery(ctx,statusSelectSql,new Object[]{statusId});

			//�������е����ڼ�Ϊ�յ�����
			try {
				if(rs2!=null && rs2.next()){
					String projectId =  rs2.getString("fprojectid");	
					String startPeriodId = rs2.getString("Fstartperiodid");		
					String costPeriodId = rs2.getString("FCostPeriodID");
					String displayName = rs2.getString("FDisplayName");
					
					if(costPeriodId==null){
						return null;
					}
					//�ɱ��Ѿ��½ᣬ������Ŀ{��}���ܷ���ʼ��
					if(!costPeriodId.equals(startPeriodId)){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCOS,new Object[]{displayName});
					}
					
					//IRowSet rs = DbUtil.executeQuery(ctx,checkSql,new Object[]{projectId});
					if(checkBill( ctx, projectId,1)){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCON,new Object[]{displayName});
					}
					if(checkBill( ctx, projectId,2)){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCONNO,new Object[]{displayName});
					}
					if(checkBill( ctx, projectId,3)){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCONAU,new Object[]{displayName});
					}
					
					//������Ŀ����ʼ��
					DbUtil.execute(ctx,updateSql,new Object[]{projectId}); 
					
					//��¼��֯
					String comId = rs2.getString("ffullorgunit");
					if(!comMap.containsKey(comId)){
						comMap.put(rs2.getString("ffullorgunit"),startPeriodId);
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		
		//������֯����
		Set set = comMap.keySet();
		Iterator it = set.iterator();

		while(it.hasNext()){
				String comId = (String)it.next();
				
				
				companyInfo = GlUtils.getCurrentCompany(ctx,comId,null,false);
				
				PeriodInfo curPeriod = SystemStatusCtrolUtils.getCurrentPeriod(ctx,SystemEnum.FDC,companyInfo);
				PeriodInfo startPeriod = SystemStatusCtrolUtils.getStartPeriod(ctx,SystemEnum.FDC,companyInfo);
				String startPeriodId = startPeriod.getId().toString();
				
				if(curPeriod.getId().toString().equals(startPeriodId)){
					//��˾					
		            ISystemStatusCtrol sysStatus = SystemStatusCtrolFactory.getLocalInstance(ctx);
		            sysStatus.start(SystemEnum.FDC, companyInfo,false);		
				}
		}

			
		return null;//_fetchInitData( ctx,  orgUnit,  isCompany);
	}

	protected void _closeUnProject(Context ctx, List projectStatusIds) throws BOSException, EASBizException {
		//�ҵ�����֯���еĹ�����Ŀ
		String lang = ctx.getLocale().getLanguage();
		StringBuffer selectCurProSql = new StringBuffer();
		selectCurProSql.append("Select p.FBeginDate,b.fid ,a.FIsEnd,a.FIsClosed ,a.FCostPeriodID,FFinacialPeriodID,b.FDisplayName_"+lang);
		selectCurProSql.append("  FDisplayName,b.ffullorgunit											\r\n");
		selectCurProSql.append("  from T_FNC_ProjectPeriodStatus 	a									\r\n");
		selectCurProSql.append("��inner join t_fdc_curproject b on a.fprojectid=b.fid   					\r\n");
		selectCurProSql.append("��inner join t_bd_period p on a.FCostPeriodID=p.fid where a.fid=?    	\r\n");
		
		String updateSql = 		"update T_FNC_ProjectPeriodStatus set FIsEnd=0 where fprojectid=? ";
		
        CompanyOrgUnitInfo companyInfo = null ;	
        Map comMap = new HashMap();
        Map comPeriodMap = new HashMap();
		IRowSet rs2 = null;
		try {
			for(int i=0,count=projectStatusIds.size();i<count;i++){
				String id = (String)projectStatusIds.get(i);
				
				rs2 = DbUtil.executeQuery(ctx,selectCurProSql.toString(),new Object[]{id});
				if(rs2!=null && rs2.next()){
					String projectId = rs2.getString("fid");
					String displayName = rs2.getString("FDisplayName");
					//������Ŀ{0}δ�ر�,��ʾδ�ر�
					if(!rs2.getBoolean("FIsEnd")){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_NOTEND,new Object[]{displayName});
					}
						
					String comId = rs2.getString("ffullorgunit");
					if(comMap.containsKey(comId)){
						companyInfo = (CompanyOrgUnitInfo)comMap.get(comId);
					}else{
						companyInfo = GlUtils.getCurrentCompany(ctx, comId,null,false) ;
						comMap.put(comId,companyInfo);
					}
			        PeriodInfo period = null;
			        if(comPeriodMap.containsKey(comId)){
			        	period = (PeriodInfo)comPeriodMap.get(comId);
			        }else{
			        	period = SystemStatusCtrolUtils.getCurrentPeriod(ctx, SystemEnum.FDC, companyInfo);
			        	comPeriodMap.put(comId,period);
			        }
			        Date year = rs2.getDate("FBeginDate");			      
			        //������Ŀ��ǰ�ɱ��ڼ�Ȳ���ϵͳ��ǰ�ڼ�С�����ܷ��ر�
			        if(year.before(period.getBeginDate())){
			        	throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_BEFORSY,new Object[]{displayName});
			        }					
					
					//������Ŀ��ǰ�ڼ��Ƿ�û������˵ĺ�ͬ�����ı���ͬ��
//					if(checkBill( ctx, projectId)){
//						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCON,new Object[]{displayName});
//					}
					
					DbUtil.execute(ctx,updateSql,new Object[]{projectId});
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		
	}

	protected Map _closeUnAll(Context ctx, String orgUnit, boolean isCompany) throws BOSException, EASBizException {
		
		//�ҵ�����֯���еĹ�����Ŀ
		String lang = ctx.getLocale().getLanguage();
		StringBuffer selectCurProSql = new StringBuffer();
		if(isCompany){//������֯
			selectCurProSql.append("select cp.fid Fid,cp.FstartDate FstartDate,cp.ffullorgunit,cp.FDisplayName_"+lang+" FDisplayName from t_fdc_curproject cp 		\r\n");
			selectCurProSql.append("inner join t_org_baseUnit cbu on cp.ffullorgunit=cbu.fid									\r\n");
			selectCurProSql.append("inner join t_org_baseUnit pbu on charindex(pbu.FLongNumber || '!', cbu.FLongNumber || '!') = 1   				\r\n");
			selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1 and  pbu.fid=?		");
		}else{
			selectCurProSql.append("select cp.fid Fid,cp.FstartDate FstartDate ,cp.ffullorgunit,cp.FDisplayName_"+lang+" FDisplayName from T_FDC_ProjectWithCostCenterOU wi 		\r\n");
			selectCurProSql.append("inner join t_fdc_curproject pcp on wi.fcurprojectid=pcp.fid				\r\n");
			selectCurProSql.append("inner join t_fdc_curproject cp on charindex(pcp.FLongNumber || '!', cp.FLongNumber || '!') = 1				\r\n");
			selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1 and wi.fcostcenterouid=?			");
		}
		
		//û�н�����ʼ���Ľ�����ʼ��
		String statusSelectSql = " Select fid ,FIsClosed,Fstartperiodid,FCostPeriodID from T_FNC_ProjectPeriodStatus where fprojectid=?  and FIsClosed=1 ";
		String updateSql = 		"update T_FNC_ProjectPeriodStatus set FIsClosed=0 where fprojectid=? ";
		String checkSql = " Select fid from t_con_contractBill where fcurProjectId=? and fstate='4AUDITTED' ";
		
		
		IRowSet rs = DbUtil.executeQuery(ctx,selectCurProSql.toString(),new Object[]{orgUnit}); 
		IRowSet rs2 = null;
        CompanyOrgUnitInfo companyInfo = null ;	
        
        Map comMap = new HashMap();
        
		if(rs!=null){
			try {
				while(rs.next()){
					String projectid = rs.getString("Fid");
					String comId = rs.getString("ffullorgunit"); 
					String displayName = rs.getString("FDisplayName");
					
					//�ж��Ƿ��Ѿ�����T_FNC_ProjectPeriodStatus
					rs2 = DbUtil.executeQuery(ctx,statusSelectSql,new Object[]{projectid});
					
					if(rs2!=null && rs2.next()){	
						String startPeriodId = rs2.getString("Fstartperiodid");		
						String costPeriodId = rs2.getString("FCostPeriodID");						
						
						//�ɱ��Ѿ��½ᣬ������Ŀ{��}���ܷ���ʼ��
						if(!costPeriodId.equals(startPeriodId)){
							throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCOS,new Object[]{displayName});
						}
						
						IRowSet rs3 = DbUtil.executeQuery(ctx,checkSql,new Object[]{projectid});
						if(rs3!=null && rs3.next()){
							throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCON,new Object[]{displayName});
						}
						
						//IRowSet rs = DbUtil.executeQuery(ctx,checkSql,new Object[]{projectId});
						if(checkBill( ctx, projectid,1)){
							throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCON,new Object[]{displayName});
						}					
						if(checkBill( ctx, projectid,2)){
							throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCONNO,new Object[]{displayName});
						}
						if(checkBill( ctx, projectid,3)){
							throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCONAU,new Object[]{displayName});
						}
												
						//������Ŀ����ʼ��
						DbUtil.execute(ctx,updateSql,new Object[]{projectid}); 
						
						//��¼��֯
						if(!comMap.containsKey(comId)){
							comMap.put(comId,startPeriodId);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		
		
		//������֯����
		Set set = comMap.keySet();
		Iterator it = set.iterator();

		while(it.hasNext()){
				String comId = (String)it.next();
				
				//String startPeriodId = (String)comMap.get(comId);
				companyInfo = GlUtils.getCurrentCompany(ctx,comId,null,false);
				
				PeriodInfo curPeriod = SystemStatusCtrolUtils.getCurrentPeriod(ctx,SystemEnum.FDC,companyInfo);
				PeriodInfo startPeriod = SystemStatusCtrolUtils.getStartPeriod(ctx,SystemEnum.FDC,companyInfo);
				String startPeriodId = startPeriod.getId().toString();
				
				if(curPeriod.getId().toString().equals(startPeriodId)){
					//��˾					
		            ISystemStatusCtrol sysStatus = SystemStatusCtrolFactory.getLocalInstance(ctx);
		            sysStatus.start(SystemEnum.FDC, companyInfo,false);		
				}
		}
			
		return null;//_fetchInitData( ctx,  orgUnit,  isCompany);
	}

	/**
	 * �������ڻ��һ���ڼ�
	 * ����Ҳ����ڼ�,����ʾ:��������û���ҵ����ʵ��ڼ�
	 * ����ҵ����ڼ�ȵ�ǰ������Ŀ�ĳɱ���ǰ�ڼ�ǰ,��ô��ʾ:��ȡ���ڼ䲻�����ڵ�ǰ�ڼ�
	 * @param projectId   	������ĿId	
	 * @param bookedDate	ҵ��������
	 * @param isCost		�Ƿ�ɱ�����
	 */
	protected IObjectValue _fetchPeriod(Context ctx, String projectId, java.util.Date bookedDate,boolean isCost) throws BOSException, EASBizException {
        
        SelectorItemCollection sic = new SelectorItemCollection();//view.getSelector();
        sic.add(new SelectorItemInfo("fullOrgUnit.id"));
        
        CurProjectInfo info = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId),sic);
        String comId = info.getFullOrgUnit().getId().toString();
        
        CompanyOrgUnitInfo company = GlUtils.getCurrentCompany(ctx,comId,null,true);
        
        //�������ڻ��һ���ڼ�
        PeriodInfo period = FDCUtils.fetchPeriod(ctx,company.getAccountPeriodType().getId().toString(),bookedDate);
        //��ǰ������Ŀ�ĳɱ���ǰ�ڼ�
        PeriodInfo curPeriod = FDCUtils.getCurrentPeriod(ctx,projectId,isCost);
        
        if(curPeriod!=null && period!=null && period.getBeginDate().before(curPeriod.getBeginDate())){
        	//throw new ProjectPeriodStatusException(ProjectPeriodStatusException.AUDIT_CUR_PERIOD);
        	return curPeriod;
        }
        
		return period;
	}

	protected Map _fetchInitData(Context ctx, String curOrgId, boolean isCompany) throws BOSException, EASBizException {

		Map initData = new HashMap();
		boolean isAllClosed = true;
		boolean isUnAllClosed = true;
		boolean hasClosed = false;
		
		//�ҵ�����֯���еĹ�����Ŀ
		StringBuffer selectCurProSql = new StringBuffer();
		if(isCompany){//������֯
			selectCurProSql.append("select cp.fid Fid,cp.FstartDate FstartDate,cp.ffullorgunit from t_fdc_curproject cp 		\r\n");
			selectCurProSql.append("inner join t_org_baseUnit cbu on cp.ffullorgunit=cbu.fid									\r\n");
			selectCurProSql.append("inner join t_org_baseUnit pbu on charindex(pbu.FLongNumber || '!', cbu.FLongNumber || '!') = 1   				\r\n");
			selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1  and  pbu.fid=?		");
		}else{
			selectCurProSql.append("select cp.fid Fid,cp.FstartDate FstartDate ,cp.ffullorgunit from T_FDC_ProjectWithCostCenterOU wi 		\r\n");
			selectCurProSql.append("inner join t_fdc_curproject pcp on wi.fcurprojectid=pcp.fid				\r\n");
			selectCurProSql.append("inner join t_fdc_curproject cp on charindex(pcp.FLongNumber || '!', cp.FLongNumber || '!') = 1				\r\n");
			selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1 and wi.fcostcenterouid=?			");
		}
		
		IRowSet rs = DbUtil.executeQuery(ctx,selectCurProSql.toString(),new Object[]{curOrgId}); 
		String statusSelectSql = " Select FIsClosed from T_FNC_ProjectPeriodStatus where fprojectid=?  ";
		IRowSet rs2 = null;
		
		if(rs!=null){
			try {
				while(rs.next()){
					
					String projectid = rs.getString("Fid");					
					//�ж��Ƿ��Ѿ�����T_FNC_ProjectPeriodStatus
					rs2 = DbUtil.executeQuery(ctx,statusSelectSql,new Object[]{projectid});
					
					if(rs2!=null && rs2.next()){
						boolean isClosed = rs2.getBoolean("FIsClosed");
						if(isClosed){
							isUnAllClosed = false;
						}else{
							isAllClosed = false;
						}
					}
				}
				
				hasClosed = !isUnAllClosed && !isAllClosed;
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		//�Ƿ�ȫ��������ʼ��		
		//�Ƿ�ȫ��δ������ʼ��
		
		initData.put("isAllClosed",Boolean.valueOf(isAllClosed));
		initData.put("isUnAllClosed",Boolean.valueOf(isUnAllClosed));
		initData.put("hasClosed",Boolean.valueOf(hasClosed));
		
		return initData;
	}

	//
	protected void _paramCheck(Context ctx, String orgUnit) throws BOSException, EASBizException {
		
		List projectList = new ArrayList();
		Map projectComMap =  new HashMap();
		Map projectStartPrdMap =  new HashMap();
		
		
		CompanyOrgUnitInfo companyInfo = GlUtils.getCurrentCompany(ctx, orgUnit,
				null, false);
		// ��ȡ�ڼ�;
		PeriodInfo period = SystemStatusCtrolUtils.getCurrentPeriod(ctx,
				SystemEnum.FDC, companyInfo);
		// ��ǰ��֯�ɱ������һ�廯��ϵͳ���õ�ϵͳ״̬�����У���û�����÷��ز�ϵͳ�����ڼ䡣
		if (period == null) {
			throw new ProjectPeriodStatusException(
					ProjectPeriodStatusException.NOT_COM_START);
		}
		
		try {
			
			
	        Map comMap = new HashMap();
	        comMap.put(orgUnit,companyInfo);
//	        CompanyOrgUnitInfo companyInfo = null ;	
	        Map comPeriodMap = new HashMap();

				//�ҵ�����֯���еĹ�����Ŀ
				StringBuffer selectCurProSql = new StringBuffer();
				selectCurProSql.append("select cp.fid Fid,cp.FstartDate FstartDate,cp.ffullorgunit from t_fdc_curproject cp 		\r\n");
//				selectCurProSql.append("inner join t_org_baseUnit cbu on cp.ffullorgunit=cbu.fid									\r\n");
//				selectCurProSql.append("inner join t_org_baseUnit pbu on charindex(pbu.FLongNumber || '!', cbu.FLongNumber || '!') = 1   				\r\n");
//				selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1 and  pbu.fid=?	and cp.fid not in ( " );
				selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1 and  cp.ffullorgunit=?	and cp.fid not in ( " );
				selectCurProSql.append(		" select fprojectid from T_FNC_ProjectPeriodStatus where  FOrgUnitID=? )	");
				
				//û�н�����ʼ���Ľ�����ʼ��
//				String statusSelectSql = " Select fid  from T_CON_ContractBill where fcurprojectid=? and Fstate ='4AUDITTED' ";
			
				IRowSet rs = DbUtil.executeQuery(ctx,selectCurProSql.toString(),new Object[]{orgUnit,orgUnit}); 
//				IRowSet rs2 = null;
				
				while(rs.next()){
					String projectid = rs.getString("Fid");
					Date startDate = rs.getDate("FstartDate");
					String comId = rs.getString("ffullorgunit"); 
					
					projectList.add(projectid);
					projectComMap.put(projectid,comId);
			
					if(comMap.containsKey(comId)){
						companyInfo = (CompanyOrgUnitInfo)comMap.get(comId);
					}else{
						companyInfo = GlUtils.getCurrentCompany(ctx, comId,null,false) ;
						comMap.put(comId,companyInfo);
					}
					
					CurProjectInfo curProject = new CurProjectInfo();
					curProject.setId(BOSUuid.read(projectid));
					FullOrgUnitInfo company = new FullOrgUnitInfo();
					company.setId(BOSUuid.read(comId));
					
					//��ȡ��ǰ�ڼ�
				    period = null;
				    if(comPeriodMap.containsKey(comId)){
				        period = (PeriodInfo)comPeriodMap.get(comId);
				    }else{
				        period =
				        		SystemStatusCtrolUtils.getCurrentPeriod(ctx, SystemEnum.FDC, companyInfo);
				        comPeriodMap.put(comId,period);
				    }
				    if(period==null){
				        	throw new PeriodException(PeriodException.NOT_SET_STARTPERIOD);
				    }
				    
				    boolean isStart = SystemStatusCtrolUtils.isStart(ctx,SystemEnum.FDC, companyInfo);
				        
				     //����startDate��ȡһ���ڼ䣬���ĸ��ڼ���ǰ
				    PeriodInfo startperiod =  
				        	FDCUtils.fetchPeriod(ctx,companyInfo.getAccountPeriodType().getId().toString(),startDate);
				    if(startperiod!=null && startperiod.getBeginDate().after(period.getBeginDate())){
				        	period = startperiod;
				    }
				        
					//���ڵ�ǰ��֯��ProjectPeriodStatus���������ݵ�,��insert
					ProjectPeriodStatusInfo info = new ProjectPeriodStatusInfo();
					info.setIsClosed(isStart);
					info.setStartPeriod(period);
					info.setCostPeriod(period);
					info.setFinacialPeriod(period);
					
					info.setOrgUnit(company);
					info.setProject(curProject);
						
					projectStartPrdMap.put(projectid,period.getId().toString());
					
					IObjectPK prPpk = this.addnew(ctx,info);
//						
//					//�ж��Ƿ��Ѿ�����T_FNC_ProjectPeriodStatus
//					rs2 = DbUtil.executeQuery(ctx,statusSelectSql,new Object[]{projectid});
//					if(rs2!=null && rs2.next()){
//						info.setIsClosed(isStart);
//						info.setId(BOSUuid.read(prPpk.toString()));
//						this.update(ctx,prPpk,info);
//					}
				}
			
		} catch (SQLException e) {
			throw new SQLAccessException(e);
		}
		
		//���õ���
		for(int i=0,count=projectList.size();i<count;i++){
			String projectId = (String)projectList.get(i);
			String comId = (String)projectComMap.get(projectId);
			String periodId = (String)projectStartPrdMap.get(projectId);
			
			syscBill(ctx,projectId,comId,periodId);
		}
		
	}
}