package com.kingdee.eas.fdc.invite.supplier.app;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.FtpConfigFactory;
import com.kingdee.eas.base.attachment.FtpConfigInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelFactory;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierQuaLevelEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierQuaLevelEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierRGContractEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierRGContractEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierRGContractEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierSplAreaEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierSplAreaEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockContractEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockContractEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockContractEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class SupplierBpmAuditFacadeControllerBean extends AbstractSupplierBpmAuditFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.SupplierBpmAuditFacadeControllerBean");
    
    
    
    
	//K2在流程审批结束后（包括审批通过，不同意，有异议），调用ERP的Close接口,向业务系统汇报审批结果。
    protected String _approveClose(Context ctx, String strBSID, String strBOID,int procInstID, String strStepName, int processInstanceResult,String strApproverId, String strComment, Timestamp dtTime)throws BOSException, EASBizException {
    //	strBOID=java.net.URLDecoder.decode(strBOID);
		StringBuffer str_xml = new StringBuffer(); 
		//xml格式
		str_xml = str_xml.append(GetMtXml());
		if(!"".equals(strBOID)&&strBOID!=null){
			try{
				SupplierReviewGatherInfo supplierReviewGatherInfo = SupplierReviewGatherFactory.getLocalInstance(ctx).getSupplierReviewGatherInfo(new ObjectStringPK(strBOID));
				//SupplierStockInfo supplierStockInfo = SupplierStockFactory.getLocalInstance(ctx).getSupplierStockInfo(new ObjectStringPK(strBOID));
				if(!"".equals(supplierReviewGatherInfo)&&supplierReviewGatherInfo!=null){
					try{
						if(processInstanceResult==1){
							//BPM流程审批通过  
							supplierReviewGatherInfo.setState(FDCBillStateEnum.AUDITTED);
							SelectorItemCollection selectorItem = new SelectorItemCollection();
							selectorItem.add("state");
							selectorItem.add("id");
							SupplierReviewGatherFactory.getLocalInstance(ctx).updatePartial(supplierReviewGatherInfo, selectorItem);
							
							SupplierStockInfo info = SupplierStockFactory.getLocalInstance(ctx).getSupplierStockInfo(new ObjectUuidPK(supplierReviewGatherInfo.getSupplier().getId()));
							info.setIsPass(IsGradeEnum.ELIGIBILITY);
							SelectorItemCollection sItem = new SelectorItemCollection();
							sItem.add("isPass");
							sItem.add("id");
							SupplierStockFactory.getLocalInstance(ctx).updatePartial(info, sItem);
							
							
							str_xml = str_xml.append(GetSuccess(11,null));
							
							
						}else if(processInstanceResult==0){
							//BOM流程审批未通过
							supplierReviewGatherInfo.setState(FDCBillStateEnum.SAVED);
							SelectorItemCollection selectorItem = new SelectorItemCollection();
							selectorItem.add("state");
							selectorItem.add("id");
							SupplierReviewGatherFactory.getLocalInstance(ctx).updatePartial(supplierReviewGatherInfo, selectorItem);
							//将供应商 设置为
							SupplierStockInfo info = SupplierStockFactory.getLocalInstance(ctx).getSupplierStockInfo(new ObjectUuidPK(supplierReviewGatherInfo.getSupplier().getId()));
							info.setIsPass(IsGradeEnum.ENGRADE);
							SelectorItemCollection sItem = new SelectorItemCollection();
							sItem.add("isPass");
							sItem.add("id");
							SupplierStockFactory.getLocalInstance(ctx).updatePartial(info, sItem);
							str_xml = str_xml.append(GetSuccess(10,null));
						}else if(processInstanceResult==2){
							//BOM流程作废
							supplierReviewGatherInfo.setState(FDCBillStateEnum.SAVED);
							SelectorItemCollection selectorItem = new SelectorItemCollection();
							selectorItem.add("state");
							selectorItem.add("id");
							SupplierReviewGatherFactory.getLocalInstance(ctx).updatePartial(supplierReviewGatherInfo, selectorItem);
							str_xml = str_xml.append(GetSuccess(10,null));
						}else{
							str_xml.append(GetErrorXml(3,null));
						}
					}catch (Exception e) {
						e.printStackTrace();
						str_xml.append(GetErrorXml(2,null));
					}
				} 
			}catch (Exception e) {
				str_xml.append(GetErrorXml(1,null));
			}
			//表头字段设置
		}else{
			str_xml.append(GetErrorXml(0,null));
		}
		return str_xml.toString();
	}
	/**
     * 业务系统回报流程创建结果，便于业务系统做后续的工作。
     */
	protected String _createResult(Context ctx, String strBSID, String strBOID,boolean success, int procInstID, String strMessage)throws BOSException, EASBizException {
		//strBOID=java.net.URLDecoder.decode(strBOID);
		StringBuffer str_xml = new StringBuffer(); 
		//xml格式
		str_xml = str_xml.append(GetMtXml());
		if(!"".equals(strBOID)&&strBOID!=null){
			try{
				SupplierReviewGatherInfo supplierReviewGatherInfo = SupplierReviewGatherFactory.getLocalInstance(ctx).getSupplierReviewGatherInfo(new ObjectStringPK(strBOID));
				//SupplierStockInfo supplierStockInfo = SupplierStockFactory.getLocalInstance(ctx).getSupplierStockInfo(new ObjectStringPK(strBOID));
				if(!"".equals(supplierReviewGatherInfo)&&supplierReviewGatherInfo!=null){
						if(success==true){
							//BPM流程创建 成功  单据状态修改为 审批中
							supplierReviewGatherInfo.setState(FDCBillStateEnum.AUDITTING);
							supplierReviewGatherInfo.setProcInstId(String.valueOf(procInstID));
							SelectorItemCollection selectorItem = new SelectorItemCollection();
							selectorItem.add("state");
							selectorItem.add("id");
							selectorItem.add("procInstId");
							SupplierReviewGatherFactory.getLocalInstance(ctx).updatePartial(supplierReviewGatherInfo, selectorItem);
							str_xml = str_xml.append(GetSuccess(11,null));
						}else if(success==false){
							//BOM流程创建失败   单据修改为保存状态
							supplierReviewGatherInfo.setState(FDCBillStateEnum.SAVED);
							SelectorItemCollection selectorItem = new SelectorItemCollection();
							selectorItem.add("state");
							selectorItem.add("id");
							SupplierReviewGatherFactory.getLocalInstance(ctx).updatePartial(supplierReviewGatherInfo, selectorItem);
							str_xml = str_xml.append(GetSuccess(10,null));
						}else{
							str_xml.append(GetErrorXml(3,null));
						}
				} 
			}catch (Exception e) {
				str_xml.append(GetErrorXml(1,null));
			}
			//表头字段设置
		}else{
			str_xml.append(GetErrorXml(0,null));
		}
		return str_xml.toString();
		
	}
    //供应商汇总表头XML
	public String GetSupplierReviewGatherHeadXml(Context ctx,SupplierReviewGatherInfo info,SupplierStockInfo supplierStockInfo){
		StringBuffer str_xml = new StringBuffer();
		str_xml.append("");
					String sql = "select a.fnumber as Fnumber,b.fname_l2 as Orgname,c.fname_l2 as Fcreator,m.fname_l2 as Fmodel,s.fname_l2 as Fsuppliername,s.fnumber as Fsupplierno,ts.fname_l2 as Ftype,tt.fname as Ftemp, tu.fname_l2 as FGradeId,a.FDescription as FDescription,a.FCREATETIME as FCreateTime from T_GYS_SupplierReviewGather a left join T_ORG_BaseUnit b on a.FOrgUnitID =b.fid left join T_PM_User c  on c.fid = a.FCreatorId";
					sql =sql+"  left join T_FDC_SupplierStock s on s.fid = a.FSupplierId  left join  T_GYS_SupplierBusinessMode m on  s.FSupplierBusinessModeId = m.fid  left join T_GYS_SupplierEvaluationType ts on ts.fid = a.FEvaluationTypeId"; 
					sql =sql+" left join T_FDC_SupplierAppraiseTemplate tt on tt.fid = a.FTemplateId    left join  T_FDC_GradeSetUp tu on a.FGradeId = tu.fid   where a.fid = '"+info.getId()+"'";
					ISQLExecutor executor = SQLExecutorFactory.getLocalInstance(ctx,sql);
					try { 
						IRowSet rowSet = executor.executeSQL();
						if (!"".equals(rowSet) && rowSet != null && rowSet.size() > 0) {
							while (rowSet.next()) {
								String Fnumber = rowSet.getString("Fnumber");//
								str_xml.append("<FPnumber>"+info.getNumber()+"</FPnumber>"); //
								String Fsuppliername = rowSet.getString("Fsuppliername");//
								str_xml.append("<FPsuppliername>"+Fsuppliername+"</FPsuppliername>"); //
								String Fsupplierno = rowSet.getString("Fsupplierno");//
								str_xml.append("<FPsupplierno>"+Fsupplierno+"</FPsupplierno>"); //
								String Orgname = rowSet.getString("Orgname");//
								str_xml.append("<FPOrgname>"+Orgname+"</FPOrgname>"); //
								String FCreator = rowSet.getString("Fcreator");//
								str_xml.append("<FPcreator>"+FCreator+"</FPcreator>"); //
								String Fmodel = rowSet.getString("Fmodel");//
								str_xml.append("<FPmodel>"+Fmodel+"</FPmodel>"); //
								String Ftype = rowSet.getString("Ftype");//考察类型
								str_xml.append("<FEvaluationTypeId>"+Ftype+"</FEvaluationTypeId>"); //
								String Ftemp = rowSet.getString("Ftemp");//评审模版
								str_xml.append("<FTemplateId>"+Ftemp+"</FTemplateId>"); //
								if(rowSet.getString("FDescription")!=null&&!"".equals(rowSet.getString("FDescription"))){
									String FDescription = rowSet.getString("FDescription");//
									str_xml.append("<FDescription>"+FDescription+"</FDescription>"); 
								}else{
									str_xml.append("<FDescription></FDescription>"); 
								}
								String FGradeId = rowSet.getString("FGradeId");//等级
								str_xml.append("<FGradeId>"+FGradeId+"</FGradeId>"); //
								//创建时间
								String FCreateTime = rowSet.getString("FCreateTime");//创建时间
								str_xml.append("<FCreateTime>"+FCreateTime.substring(0, 10)+"</FCreateTime>"); //
							}
						}
					}catch (Exception e) {
						// TODO: handle exception
					}
		return str_xml.toString();
	}
	//考察
	public String GetSupplierRGSurveyEntryXml(Context ctx,SupplierReviewGatherInfo info){
		StringBuffer str_xml = new StringBuffer();
		str_xml.append("");
		String Ksql = "select a.fid,a.fnumber as Fnumber,a.fname as Fname,a.FDescription,b.FDESCRIPTION_L2 as FPHtype from T_GYS_SupplierRGSurveyEntry a left join T_GYS_SupplierRGSurvey b on b.fnumber = a.fnumber";
		Ksql =Ksql+" where a.fheadid='"+info.getId()+"'    ";
		if(info.getTemplate().getId().toString().equals("1roAAAAAPp0hj0Tv")){
			Ksql =Ksql+"  and b.FDESCRIPTION_L2 is not null";	
		}
		ISQLExecutor texecutor = SQLExecutorFactory.getLocalInstance(ctx,Ksql);
		try { 
			if(info.getTemplate().getId().toString().equals("1roAAAAAPp0hj0Tv")){
				IRowSet trowSet = texecutor.executeSQL();
				StringBuffer a_xml = new StringBuffer();
				StringBuffer b_xml = new StringBuffer();
				StringBuffer c_xml = new StringBuffer();
				if (!"".equals(trowSet) && trowSet != null && trowSet.size() > 0) {
					String str_type ="";
					int count =0;
					while (trowSet.next()) {
						count =count+1;
						if(trowSet.getString("FPHtype")!=null&&!"".equals(trowSet.getString("FPHtype"))||1==1){
							String ftype = trowSet.getString("FPHtype");//
							if(ftype.equals("A")){
								a_xml.append("<Item>");
								String Fnumber = trowSet.getString("Fnumber");//
								a_xml.append("<Fnumber>"+Fnumber+"</Fnumber>"); //
								String Fname = trowSet.getString("Fname");//
								a_xml.append("<Fname>"+Fname+"</Fname>"); //
								if(trowSet.getString("FDescription")!=null&&!"".equals(trowSet.getString("FDescription"))){
									String FDescription = trowSet.getString("FDescription");//
									a_xml.append("<FDescription>"+FDescription+"</FDescription>"); //
								}else{
									a_xml.append("<FDescription></FDescription>"); //
								}
								a_xml.append("</Item>");
							}else if(ftype.equals("B")){
								b_xml.append("<Item>");
								String Fnumber = trowSet.getString("Fnumber");//
								b_xml.append("<Fnumber>"+Fnumber+"</Fnumber>"); //
								String Fname = trowSet.getString("Fname");//
								b_xml.append("<Fname>"+Fname+"</Fname>"); //
								if(trowSet.getString("FDescription")!=null&&!"".equals(trowSet.getString("FDescription"))){
									String FDescription = trowSet.getString("FDescription");//
									b_xml.append("<FDescription>"+FDescription+"</FDescription>"); //
								}else{
									b_xml.append("<FDescription></FDescription>"); //
								}
								b_xml.append("</Item>");
							}else if(ftype.equals("C")){
								c_xml.append("<Item>");
								String Fnumber = trowSet.getString("Fnumber");//
								c_xml.append("<Fnumber>"+Fnumber+"</Fnumber>"); //
								String Fname = trowSet.getString("Fname");//
								c_xml.append("<Fname>"+Fname+"</Fname>"); //
								if(trowSet.getString("FDescription")!=null&&!"".equals(trowSet.getString("FDescription"))){
									String FDescription = trowSet.getString("FDescription");//
									c_xml.append("<FDescription>"+FDescription+"</FDescription>"); //
								}else{
									c_xml.append("<FDescription></FDescription>"); //
								}
								c_xml.append("</Item>");
							}
						}
					}   
					str_xml.append("<FtypeEntryA>"+a_xml+"</FtypeEntryA>");
					str_xml.append("<FtypeEntryB>"+b_xml+"</FtypeEntryB>");
					str_xml.append("<FtypeEntryC>"+c_xml+"</FtypeEntryC>");
				}
			}else{   
				IRowSet rowSet = texecutor.executeSQL();
				if (!"".equals(rowSet) && rowSet != null && rowSet.size() > 0) {
					//
					str_xml.append("<Sre>");
					while (rowSet.next()) {
						str_xml.append("<Item>");
						String Fnumber = rowSet.getString("Fnumber");//
						str_xml.append("<Fnumber>"+Fnumber+"</Fnumber>"); //
						String Fname = rowSet.getString("Fname");//
						str_xml.append("<Fname>"+Fname+"</Fname>"); //
						if(rowSet.getString("FDescription")!=null&&!"".equals(rowSet.getString("FDescription"))){
							String FDescription = rowSet.getString("FDescription");//
							str_xml.append("<FDescription>"+FDescription+"</FDescription>"); //
						}else{
							str_xml.append("<FDescription></FDescription>"); //
						}
						str_xml.append("</Item>");
					}
					str_xml.append("</Sre>");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.appendFilterItem("head", supplierReviewGatherInfo.getId());
//		view.setFilter(filter);
//		SupplierReviewGatherSurveyEntryCollection coll = SupplierReviewGatherSurveyEntryFactory.getLocalInstance(ctx).getSupplierReviewGatherSurveyEntryCollection(view);
//		if(!"".equals(coll)&&coll!=null&&coll.size()>0){
//			for (int i = 0; i < coll.size(); i++) {
//				SupplierReviewGatherSurveyEntryInfo entry = coll.get(i);
//				SupplierReviewGatherSurveyInfo info = 
//				
//			}
//		}
		return str_xml.toString();
	}
	
	//供应商评审汇总
	public String GetSupplierAppraiseTemplateXml(Context ctx,SupplierReviewGatherInfo supplierReviewGatherInfo){
		StringBuffer str_xml = new StringBuffer();
		str_xml.append("");
		String sql = "select e.fname_l2 as Fname,ISNULL(e.FFullMarkStandard,'') AS FFullMarkStandard,ISNULL(p.fname_l2,'') as Fperson,ISNULL(ta.fname_l2,'') as Fdept,ISNULL(a.FWrite,'') as FWrite,ISNULL(tc.fname_l2,'') as Fchoose,ISNULL(a.fremark,'') as Fremark from T_GYS_SupplierReviewGEntry K LEFT JOIN  T_SPL_FDCSplQuaAuditTemplate a ON K.FAuditTemplateId = A.FID left join T_BD_Person p on p.fid = a.FAuditPersonId";
		sql = sql +" left join T_ORG_Admin ta on ta.fid = a.FAuditDeptId  left join T_FDC_SupplierGuideEntry ts on ts.fid=a.FTemplateEntryID left join T_SPL_AuditIndex e on e.fid =ts.FSplAuditIndexID";
		sql = sql +" left join T_GYS_Choose tc on tc.fid = a.FChooseId";
		sql = sql +" where K.fheadid  ='"+supplierReviewGatherInfo.getId()+"' ORDER BY p.fname_l2,ta.fname_l2";
		Map map_user = new HashMap(); 
		Map map = new HashMap();
		ISQLExecutor executor = SQLExecutorFactory.getLocalInstance(ctx,sql);
		try { 
			IRowSet rowSet = executor.executeSQL();
			if (!"".equals(rowSet) && rowSet != null && rowSet.size() > 0) {
				str_xml.append("<Tgs>");
				while (rowSet.next()) {
					StringBuffer str_entry = new StringBuffer();
					str_entry.append("");
					String Fperson = rowSet.getString("Fperson");
					String Fdept = rowSet.getString("Fdept");
					map_user.put(Fperson, Fdept);
					str_entry.append("<Item>");
					String Fname = rowSet.getString("Fname");//
					if(Fname!=null&&!"".equals(Fname)){
						str_entry.append("<Fname>"+Fname+"</Fname>");
					}else{
						str_entry.append("<Fname></Fname>");
					}
					String FFullMarkStandard = rowSet.getString("FFullMarkStandard");//
					if(FFullMarkStandard!=null&&!"".equals(FFullMarkStandard)){
						str_entry.append("<FFullMarkStandard>"+FFullMarkStandard+"</FFullMarkStandard>");
					}else{
						str_entry.append("<FFullMarkStandard></FFullMarkStandard>");
					}
					String Fchoose = rowSet.getString("Fchoose");//
					if(Fchoose!=null&&!"".equals(Fchoose)){
						str_entry.append("<Fchoose>"+Fchoose+"</Fchoose>");
					}else{
						str_entry.append("<Fchoose></Fchoose>");
					}
					String FWrite = rowSet.getString("FWrite");//
					if(FWrite!=null&&!"".equals(FWrite)){
						str_entry.append("<FWrite>"+FWrite+"</FWrite>");
					}else{
						str_entry.append("<FWrite></FWrite>");
					}
					String Fremark = rowSet.getString("Fremark");//
					if(Fremark!=null&&!"".equals(Fremark)){
						str_entry.append("<Fremark>"+Fremark+"</Fremark>");
					}else{
						str_entry.append("<Fremark></Fremark>");
					}
					str_entry.append("</Item>");
					map.put(Fperson, str_entry);
				}
				Iterator iter = map_user.keySet().iterator();
				while (iter.hasNext()) {
					Object key = iter.next();
					Object val = map_user.get((String)key);
					str_xml.append("<Item><Fname><b>部门:"+val+"    人员:"+key+"</b></Fname><FFullMarkStandard></FFullMarkStandard><Fchoose></Fchoose><FWrite></FWrite><Fremark></Fremark></Item>"+map.get(key)+"");
				}
				str_xml.append("</Tgs>");
			}
		}catch (Exception e) {
		}
		
		
		
		
		
		
//		ISQLExecutor executor = SQLExecutorFactory.getLocalInstance(ctx,sql);
//		try { 
//			IRowSet rowSet = executor.executeSQL();
//			if (!"".equals(rowSet) && rowSet != null && rowSet.size() > 0) {
//				String Fusername="";
//				String Fdeptname ="";
//				str_xml.append("<Sat>");
//				while (rowSet.next()) {
//					str_xml.append("<Item>");
//					String Fname = rowSet.getString("Fname");//
//					str_xml.append("<Fname>"+Fname+"</Fname>"); //
//					String FFullMarkStandard = rowSet.getString("FFullMarkStandard");//
//					str_xml.append("<FFullMarkStandard>"+FFullMarkStandard+"</FFullMarkStandard>"); 
//					String Fchoose = rowSet.getString("Fchoose");//
//					str_xml.append("<Fchoose>"+Fchoose+"</Fchoose>");
//					String FWrite = rowSet.getString("FWrite");//
//					str_xml.append("<FWrite>"+FWrite+"</FWrite>");
//			    	Fusername = rowSet.getString("Fperson");
//					Fdeptname=rowSet.getString("Fdept");;	
//					String Fremark = rowSet.getString("Fremark");//
//					str_xml.append("<Fremark>"+Fremark+"</Fremark>");
//					str_xml.append("</Item>");
//				}
//				str_xml.append("<Fperson>"+Fusername+"</Fperson>");
//				str_xml.append("<Fdept>"+Fdeptname+"</Fdept>");
//				str_xml.append("</Sat>");
//			}
//		}catch (Exception e) {
//			
//		}   
		return str_xml.toString();
	}   
	protected String _getBillInfo(Context ctx, String strBSID, String strBOID)throws BOSException, EASBizException {
		//strBOID=java.net.URLDecoder.decode(strBOID);   
		StringBuffer str_xml = new StringBuffer(); 
		//xml格式
		str_xml = str_xml.append(GetMtXml());
		int flag_int =0;
		if(!"".equals(strBOID)&&strBOID!=null){
			try{
				SupplierReviewGatherInfo supplierReviewGatherInfo = SupplierReviewGatherFactory.getLocalInstance(ctx).getSupplierReviewGatherInfo(new ObjectStringPK(strBOID));
				if(!"".equals(supplierReviewGatherInfo)&&supplierReviewGatherInfo!=null){
					str_xml.append("<DATA>");
					SupplierStockInfo supplierStockInfo = SupplierStockFactory.getLocalInstance(ctx).getSupplierStockInfo(new ObjectUuidPK(supplierReviewGatherInfo.getSupplier().getId()));
					if(!"".equals(supplierStockInfo)&&supplierStockInfo!=null){
						str_xml.append(GetSupplierReviewGatherHeadXml(ctx,supplierReviewGatherInfo,supplierStockInfo));
						str_xml.append(GetSupplierRGSurveyEntryXml(ctx,supplierReviewGatherInfo ));
						str_xml.append(GetSRGContractEntry(ctx,supplierReviewGatherInfo));
						str_xml.append(GetSupplierAppraiseTemplateXml(ctx,supplierReviewGatherInfo ));
						/***************************************供应商档案等级************************************************/
						try{
							str_xml.append(GetSupplierStockInfoXml(ctx,supplierStockInfo));
							str_xml.append((GetSupplierLinkPersonXml(ctx,supplierStockInfo.getId().toString())));
							str_xml.append((GetSupplierSContractEntryXml(ctx,supplierStockInfo.getId().toString())));
							str_xml.append(GetSupplierAttachListEntryXml(ctx,supplierStockInfo.getId().toString()));
						}catch (Exception e) {
							GetErrorXml(2,null);
						}
					}
					/***************************************************************************************/
					str_xml.append("</DATA>");
				}
				
			}catch (Exception e) {
				str_xml.append(GetErrorXml(1,null));
			}
			//表头字段设置
		}else{
			str_xml.append(GetErrorXml(0,null));
		}
		return str_xml.toString();
	}
    // 流程审批(退回、发起人取消)
	public String GetSRGContractEntry(Context ctx,SupplierReviewGatherInfo supplierReviewGatherInfo){
		StringBuffer str_xml = new StringBuffer(); 
		str_xml.append("");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("head", supplierReviewGatherInfo.getId());
		view.setFilter(filter);
		try {
			SupplierRGContractEntryCollection collection = SupplierRGContractEntryFactory.getLocalInstance(ctx).getSupplierRGContractEntryCollection(view);
			if(collection!=null&&!"".equals(collection)&&collection.size()>0){
				str_xml.append("<Contract>");
				for (int i = 0; i < collection.size(); i++) {
					SupplierRGContractEntryInfo contractEntryInfo = collection.get(i);
					str_xml.append("<Item>");
					boolean  flag = contractEntryInfo.isIsHasContract();
					if(flag==false){
						str_xml.append("<FIsHasContract>否</FIsHasContract>");
					}else{
						str_xml.append("<FIsHasContract>是</FIsHasContract>");
					}
					if(contractEntryInfo.getContract()!=null&&!"".equals(contractEntryInfo.getContract())){
						str_xml.append("<FContract>"+contractEntryInfo.getContract()+"</FContract>");
					}else{
						str_xml.append("<FContract></FContract>");
					}
					if(contractEntryInfo.getContractAmount()!=null&&!"".equals(contractEntryInfo.getContractAmount())){
						str_xml.append("<FContractAmount>"+contractEntryInfo.getContractAmount()+"</FContractAmount>");
					}else{
						str_xml.append("<FContractAmount>0</FContractAmount>");
					}
					if(contractEntryInfo.getManager()!=null&&!"".equals(contractEntryInfo.getManager())){
						str_xml.append("<FManager>"+contractEntryInfo.getManager()+"</FManager>");
					}else{
						str_xml.append("<FManager></FManager>");
					}
					if(contractEntryInfo.getManagerPhone()!=null&&!"".equals(contractEntryInfo.getManagerPhone())){
						str_xml.append("<FManagerPhone>"+contractEntryInfo.getManagerPhone()+"</FManagerPhone>");
					}else{
						str_xml.append("<FManagerPhone></FManagerPhone>");
					}
					str_xml.append("</Item>");
				}
				
				str_xml.append("</Contract>");
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		
		
		return str_xml.toString();
	}
	
	
	
	//供应商档案等级表头
	public String GetSupplierStockInfoXml(Context ctx,SupplierStockInfo supplierStockInfo){
		StringBuffer str_xml = new StringBuffer(); 
		str_xml.append("<FName>"+supplierStockInfo.getName()+"</FName>"); //供应商名称
		//取表头字段
		String sql ="select ISNULL(c.fname_l2,'') as FCityID,ISNULL(p.fname_l2,'') as  FProvinceID ,ISNULL(b.fname_l2,'') as  FPurchaseOrgUnitId,ISNULL(tt.fname_l2,'') as FSupplierIListTypeEntry,ISNULL(ti.fname_l2,'') as FInviteTypeId,ISNULL(tf.fname_l2,'') as FSupplierFileTypeId from T_FDC_SupplierStock a left join T_BD_City c on ";
		sql =sql+"  a.FCityID =c.fid left join T_BD_Province p on p.fid = a.FProvinceID left join T_ORG_Purchase b on b.fid = a.FPurchaseOrgUnitId  left join T_INV_InviteType e on e.fid = a.FInviteTypeId left join T_GYS_SupplierIListTypeEntry f on  f.fheadid =a.fid ";
		sql =sql+" left join T_GYS_InviteListType tt on  tt.Fid = f.FInviteListTypeId left join T_INV_InviteType ti on ti.fid = a.FInviteTypeId left join T_GYS_SupplierFileType  tf on tf.fid = a.FSupplierFileTypeId ";
		sql = sql+" where  a.fid ='"+supplierStockInfo.getId()+"'";
		ISQLExecutor executor = SQLExecutorFactory.getLocalInstance(ctx,sql);
		try {   
			IRowSet rowSet = executor.executeSQL();
			if (!"".equals(rowSet) && rowSet != null && rowSet.size() > 0) {
				while (rowSet.next()) {
					if(rowSet.getString("FSupplierFileTypeId")!=null&&!"".equals(rowSet.getString("FSupplierFileTypeId"))){
						String FSupplierFileTypeId = rowSet.getString("FSupplierFileTypeId");//
						str_xml.append("<FSupplierFileTypeId>"+FSupplierFileTypeId+"</FSupplierFileTypeId>"); //
					}else{
						str_xml.append("<FSupplierFileTypeId></FSupplierFileTypeId>"); //
					}
					if(rowSet.getString("FInviteTypeId")!=null&&!"".equals(rowSet.getString("FInviteTypeId"))){
						String FInviteTypeId = rowSet.getString("FInviteTypeId");//
						str_xml.append("<FInviteTypeId>"+FInviteTypeId+"</FInviteTypeId>"); //
					}else{
						str_xml.append("<FInviteTypeId></FInviteTypeId>"); //
					}
					if(rowSet.getString("FSupplierIListTypeEntry")!=null&&!"".equals(rowSet.getString("FSupplierIListTypeEntry"))){
						String FSupplierIListTypeEntry = rowSet.getString("FSupplierIListTypeEntry");//
						str_xml.append("<FSupplierIListTypeEntry>"+FSupplierIListTypeEntry+"</FSupplierIListTypeEntry>"); //
					}else{
						str_xml.append("<FSupplierIListTypeEntry></FSupplierIListTypeEntry>"); //
					}
					if(rowSet.getString("FPurchaseOrgUnitId")!=null&&!"".equals(rowSet.getString("FPurchaseOrgUnitId"))){
						String FPurchaseOrgUnitId = rowSet.getString("FPurchaseOrgUnitId");//
						str_xml.append("<FPurchaseOrgUnitId>"+FPurchaseOrgUnitId+"</FPurchaseOrgUnitId>"); //
					}else{
						str_xml.append("<FPurchaseOrgUnitId></FPurchaseOrgUnitId>"); //
					}
					if(rowSet.getString("FProvinceID")!=null&&!"".equals(rowSet.getString("FProvinceID"))){
						String FProvinceID = rowSet.getString("FProvinceID");//
						str_xml.append("<FProvinceID>"+FProvinceID+"</FProvinceID>"); //
					}else{
						str_xml.append("<FCityID></FCityID>"); //
					}
					if(rowSet.getString("FCityID")!=null&&!"".equals(rowSet.getString("FCityID"))){
						String FCityID = rowSet.getString("FCityID");//
						str_xml.append("<FCityID>"+FCityID+"</FCityID>"); //
					}else{
						str_xml.append("<FCityID></FCityID>"); //
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(supplierStockInfo.getEnterpriseMaster()!=null&&!"".equals(supplierStockInfo.getEnterpriseMaster())){
			str_xml.append("<FEnterpriseMaster>"+supplierStockInfo.getEnterpriseMaster()+"</FEnterpriseMaster>");//法人代表 
		}else{
			str_xml.append("<FEnterpriseMaster></FEnterpriseMaster>");//法人代表 
		}
		if(supplierStockInfo.getBuildDate()!=null&&!"".equals(supplierStockInfo.getBuildDate())){
			str_xml.append("<FBuildDate>"+supplierStockInfo.getBuildDate()+"</FBuildDate>");
		}else{
			str_xml.append("<FBuildDate></FBuildDate>");
		}
		if(supplierStockInfo.getAddress()!=null&&!"".equals(supplierStockInfo.getAddress())){
			str_xml.append("<FAddress>"+supplierStockInfo.getAddress()+"</FAddress>");
		}else{
			str_xml.append("<FAddress></FAddress>");
		}
		if(supplierStockInfo.getRegisterMoney()!=null&&!"".equals(supplierStockInfo.getRegisterMoney())){
			str_xml.append("<FRegisterMoney>"+supplierStockInfo.getRegisterMoney()+"</FRegisterMoney>");
		}else{
			str_xml.append("<FRegisterMoney></FRegisterMoney>");
		}
		if(supplierStockInfo.getEnterpriseKind()!=null&&!"".equals(supplierStockInfo.getEnterpriseKind())){
			str_xml.append("<FEnterpriseKind>"+supplierStockInfo.getEnterpriseKind()+"</FEnterpriseKind>");
		}else{
			str_xml.append("<FEnterpriseKind></FEnterpriseKind>");
		}
		if(supplierStockInfo.getBizRegisterNo()!=null&&!"".equals(supplierStockInfo.getBizRegisterNo())){
			str_xml.append("<FBizRegisterNo>"+supplierStockInfo.getBizRegisterNo()+"</FBizRegisterNo>");
		}else{
			str_xml.append("<FBizRegisterNo></FBizRegisterNo>");
		}
		if(supplierStockInfo.getTaxRegisterNo()!=null&&!"".equals(supplierStockInfo.getTaxRegisterNo())){
			str_xml.append("<FTaxRegisterNo>"+supplierStockInfo.getTaxRegisterNo()+"</FTaxRegisterNo>");
		}else{
			str_xml.append("<FTaxRegisterNo></FTaxRegisterNo>");
		}
		if(supplierStockInfo.getBankName()!=null&&!"".equals(supplierStockInfo.getBankName())){
			str_xml.append("<FBankName>"+supplierStockInfo.getBankName()+"</FBankName>");
		}else{
			str_xml.append("<FBankName></FBankName>");
		}
		if(supplierStockInfo.getBankCount()!=null&&!"".equals(supplierStockInfo.getBankCount())){
			str_xml.append("<FBankCount>"+supplierStockInfo.getBankCount()+"</FBankCount>");
		}else{
			str_xml.append("<FBankCount></FBankCount>");
		}
		String str_area="";
		SupplierSplAreaEntryCollection supplierSplAreaList = supplierStockInfo.getSupplierSplAreaEntry();
		if(!"".equals(supplierSplAreaList)&&supplierSplAreaList!=null&&supplierSplAreaList.size()>0){
			for (int i = 0; i < supplierSplAreaList.size(); i++) {
				SupplierSplAreaEntryInfo info = supplierSplAreaList.get(i);
				try {
					FDCSplAreaInfo areainfo = FDCSplAreaFactory.getLocalInstance(ctx).getFDCSplAreaInfo(new ObjectUuidPK(info.getFdcSplArea().getId()));
					if(i>0){
						str_area=str_area+"、";
					}   
					str_area = str_area + areainfo.getName();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		str_xml.append("<FSupplierSplAreaEntry>"+str_area+"</FSupplierSplAreaEntry>"); //服务区域
		String str_leve ="";
		SupplierQuaLevelEntryCollection quaLevelList = supplierStockInfo.getQuaLevelEntry();
		if(!"".equals(quaLevelList)&&quaLevelList!=null&&quaLevelList.size()>0){
			for (int i = 0; i < quaLevelList.size(); i++) {
				SupplierQuaLevelEntryInfo info = quaLevelList.get(i);
				try {
					QuaLevelInfo quaLevelinfo = QuaLevelFactory.getLocalInstance(ctx).getQuaLevelInfo(new ObjectUuidPK(info.getQuaLevel().getId()));
					if(i>0){
						str_leve=str_leve+"、";
					}   
					str_leve = str_leve + quaLevelinfo.getName();
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		
		
		//str_xml.append("<FSupplierBusinessModeId>"+supplierStockInfo.getSupplierBusinessModeId()+"</FSupplierBusinessModeId>"); //
		str_xml.append("<FQuaLevelEntry>"+str_leve+"</FQuaLevelEntry>"); //资质等级
		
		if(supplierStockInfo.getContractor()!=null&&!"".equals(supplierStockInfo.getContractor())){
			str_xml.append("<FContractor>"+supplierStockInfo.getContractor()+"</FContractor>");
		}else{
			str_xml.append("<FContractor></FContractor>");
		}
		return str_xml.toString();
	}
	protected String _rework(Context ctx, String strBSID, String strBOID,int procInstID, String strStepName, String strApproverId,int ieAction, String strComment, Timestamp dtTime)throws BOSException {
		//strBOID=java.net.URLDecoder.decode(strBOID);
		StringBuffer str_xml = new StringBuffer(); 
		//xml格式
		str_xml = str_xml.append(GetMtXml());
		if(!"".equals(strBOID)&&strBOID!=null){
			try{
				SupplierReviewGatherInfo supplierReviewGatherInfo = SupplierReviewGatherFactory.getLocalInstance(ctx).getSupplierReviewGatherInfo(new ObjectStringPK(strBOID));
				//SupplierStockInfo supplierStockInfo = SupplierStockFactory.getLocalInstance(ctx).getSupplierStockInfo(new ObjectStringPK(strBOID));
				if(!"".equals(supplierReviewGatherInfo)&&supplierReviewGatherInfo!=null){
					try{
						if(ieAction==1){
							//发起人撤回
							supplierReviewGatherInfo.setState(FDCBillStateEnum.SAVED);
							SelectorItemCollection selectorItem = new SelectorItemCollection();
							selectorItem.add("state");
							selectorItem.add("id");
							SupplierReviewGatherFactory.getLocalInstance(ctx).updatePartial(supplierReviewGatherInfo, selectorItem);
							str_xml = str_xml.append(GetSuccess(11,null));
						}else if(ieAction==2){
							//审批人打回发起人
							supplierReviewGatherInfo.setState(FDCBillStateEnum.SAVED);
							SelectorItemCollection selectorItem = new SelectorItemCollection();
							selectorItem.add("state");
							selectorItem.add("id");
							SupplierReviewGatherFactory.getLocalInstance(ctx).updatePartial(supplierReviewGatherInfo, selectorItem);
							str_xml = str_xml.append(GetSuccess(10,null));
						}else if(ieAction==3){
							//发起人取消流程
							supplierReviewGatherInfo.setState(FDCBillStateEnum.SAVED);
							SelectorItemCollection selectorItem = new SelectorItemCollection();
							selectorItem.add("state");
							selectorItem.add("id");
							SupplierReviewGatherFactory.getLocalInstance(ctx).updatePartial(supplierReviewGatherInfo, selectorItem);
							str_xml = str_xml.append(GetSuccess(10,null));
						}else{
							str_xml.append(GetErrorXml(3,null));
						}
					}catch (Exception e) {
						e.printStackTrace();
						str_xml.append(GetErrorXml(2,null));
					}
				} 
			}catch (Exception e) {
				str_xml.append(GetErrorXml(1,null));
			}
			//表头字段设置
		}else{
			str_xml.append(GetErrorXml(0,null));
		}
		return str_xml.toString();
	}
	//联系人  
	public String GetSupplierLinkPersonXml(Context ctx,String fid) throws BOSException{
		StringBuffer str_xml = new StringBuffer();
		str_xml.append("");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("parent", fid);
		view.setFilter(filter);
			SupplierLinkPersonCollection collect = SupplierLinkPersonFactory.getLocalInstance(ctx).getSupplierLinkPersonCollection(view);
			if(collect!=null&&!"".equals(collect)&&collect.size()>0){
				str_xml.append("<LinkPersons>");
				for (int i = 0; i < collect.size(); i++) {
					SupplierLinkPersonInfo info = collect.get(i);
					str_xml.append("<Item>");
					str_xml.append("<FSeq>"+info.getSeq()+"</FSeq>"); //
					if(info.getPersonName()!=null&&!"".equals(info.getPersonName())){
						str_xml.append("<FPersonName>"+info.getPersonName()+"</FPersonName>");
					}else{
						str_xml.append("<FPersonName></FPersonName>");
					}
					if(info.getPosition()!=null&&!"".equals(info.getPosition())){
						str_xml.append("<FPosition>"+info.getPosition()+"</FPosition>");
					}else{
						str_xml.append("<FPosition></FPosition>");
					}
					if(info.getPhone()!=null&&!"".equals(info.getPhone())){
						str_xml.append("<FPhone>"+info.getPhone()+"</FPhone>");
					}else{
						str_xml.append("<FPhone></FPhone>");
					}
					if(info.getPersonFax()!=null&&!"".equals(info.getPersonFax())){
						str_xml.append("<FPersonFax>"+info.getPersonFax()+"</FPersonFax>");
					}else{
						str_xml.append("<FPersonFax></FPersonFax>");
					}
					if(info.getWorkPhone()!=null&&!"".equals(info.getWorkPhone())){
						str_xml.append("<FWorkPhone>"+info.getWorkPhone()+"</FWorkPhone>");
					}else{
						str_xml.append("<FWorkPhone></FWorkPhone>");
					}
					if(info.getEmail()!=null&&!"".equals(info.getEmail())){
						str_xml.append("<FEmail>"+info.getEmail()+"</FEmail>");
					}else{
						str_xml.append("<FEmail></FEmail>");
					}
					if(info.getContact()!=null&&!"".equals(info.getContact())){
						str_xml.append("<FContact>"+info.getContact()+"</FContact>");
					}else{
						str_xml.append("<FContact></FContact>");
					}
					if(info.isIsDefault()==true){
						str_xml.append("<FIsDefault>是</FIsDefault>");
					}else{
						str_xml.append("<FIsDefault>否</FIsDefault>");
					}
					if(info.getType()!=null&&!"".equals(info.getType())){
						str_xml.append("<FType>"+info.getType()+"</FType>");
					}else{
						str_xml.append("<FType></FType>");
					}
					str_xml.append("</Item>");
				}
				str_xml.append("</LinkPersons>");
			}
		return str_xml.toString();
		
	}
	//三年主要业绩
	public String GetSupplierSContractEntryXml(Context ctx,String fid) throws BOSException{
		StringBuffer str_xml = new StringBuffer();
		str_xml.append("");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("head", fid);
		view.setFilter(filter);
		SupplierStockContractEntryCollection collect = SupplierStockContractEntryFactory.getLocalInstance(ctx).getSupplierStockContractEntryCollection(view);
		if(collect!=null&&!"".equals(collect)&&collect.size()>0){
			str_xml.append("<SupplierSContracts>");
			for (int i = 0; i < collect.size(); i++) {
				SupplierStockContractEntryInfo info = collect.get(i);
				str_xml.append("<Item>");
				str_xml.append("<FSeq>"+info.getSeq()+"</FSeq>"); //
				if(info.getContractName()!=null&&!"".equals(info.getContractName())){
					str_xml.append("<FContractName>"+info.getContractName()+"</FContractName>"); //
				}else{
					str_xml.append("<FContractName></FContractName>"); //
				}
				if(info.getContractAmount()!=null&&!"".equals(info.getContractAmount())){
					str_xml.append("<FContractAmount>"+info.getContractAmount()+"</FContractAmount>"); //
				}else{
					str_xml.append("<FContractAmount></FContractAmount>"); //
				}
				if(info.getModel()!=null&&!"".equals(info.getModel())){
					str_xml.append("<FModel>"+info.getModel()+"</FModel>"); //
				}else{
					str_xml.append("<FModel></FModel>"); //
				}
				if(info.getManager()!=null&&!"".equals(info.getManager())){
					str_xml.append("<FManager>"+info.getManager()+"</FManager>"); //
				}else{
					str_xml.append("<FManager></FManager>"); //
				}
				if(info.getWorkModel()!=null&&!"".equals(info.getWorkModel())){
					str_xml.append("<FWorkModel>"+info.getWorkModel()+"</FWorkModel>"); //
				}else{
					str_xml.append("<FWorkModel></FWorkModel>"); //
				}
				if(info.getPlace()!=null&&!"".equals(info.getPlace())){
					str_xml.append("<FPlace>"+info.getPlace()+"</FPlace>"); //
				}else{
					str_xml.append("<FPlace></FPlace>"); //
				}
				if(info.getSupplierName()!=null&&!"".equals(info.getSupplierName())){
					str_xml.append("<FSupplierName>"+info.getSupplierName()+"</FSupplierName>"); //
				}else{
					str_xml.append("<FSupplierName></FSupplierName>"); //
				}
				str_xml.append("</Item>");
			}
			str_xml.append("</SupplierSContracts>");
		}
		
		return str_xml.toString();
	}
	//其他信息附件
	public String GetSupplierAttachListEntryXml(Context ctx,String fid) throws BOSException{
		StringBuffer str_xml = new StringBuffer();
		str_xml.append("");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("head", fid);
		view.setFilter(filter);
		SupplierAttachListEntryCollection collect = SupplierAttachListEntryFactory.getLocalInstance(ctx).getSupplierAttachListEntryCollection(view);
		if(collect!=null&&!"".equals(collect)&&collect.size()>0){
			str_xml.append("<SupplierAttachListEntrys>");
			for (int i = 0; i < collect.size(); i++) {
				SupplierAttachListEntryInfo info = collect.get(i);
				str_xml.append("<Item>");
				str_xml.append("<FSeq>"+info.getSeq()+"</FSeq>"); //
				str_xml.append("<FNumber>"+info.getNumber()+"</FNumber>"); //
				str_xml.append("<FName>"+info.getName()+"</FName>"); //
				/**********************设置附件的目录**************************/
				
				
				EntityViewInfo boview = new EntityViewInfo();
				FilterInfo bofilter = new FilterInfo();
				bofilter.appendFilterItem("boID", info.getId());
				boview.setFilter(bofilter);
				BoAttchAssoCollection bocollect = BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(boview);
				if(!"".equals(bocollect)&&bocollect!=null&&bocollect.size()>0){
					str_xml.append("<Attachments>");
					for (int j = 0; j < bocollect.size(); j++) {
						BoAttchAssoInfo boinfo = bocollect.get(j);
						try {
							SelectorItemCollection selectorItem = new SelectorItemCollection();
							selectorItem.add("*");
							selectorItem.add("ftp.*");
							
							AttachmentInfo attinfo = AttachmentFactory.getLocalInstance(ctx).getAttachmentInfo(new ObjectStringPK(boinfo.getAttachment().getId().toString()),selectorItem);
							str_xml.append("<Item>");
							str_xml.append("<FSeq>"+(j+1)+"</FSeq>"); //
							str_xml.append("<FName>"+attinfo.getName()+"."+attinfo.getSimpleName()+"</FName>"); //
							str_xml.append("<FRemotePath>ftp://"+attinfo.getFtp().getHost()+"/"+attinfo.getRemotePath()+"</FRemotePath>"); //
							str_xml.append("<FAttachID>"+attinfo.getAttachID()+"</FAttachID>"); //
							str_xml.append("</Item>");
						} catch (EASBizException e) {
							e.printStackTrace();
						}
					}
					str_xml.append("</Attachments>");
				}
				if(info.getDescription()!=null&&!"".equals(info.getDescription())){
					str_xml.append("<FDescription>"+info.getDescription()+"</FDescription>"); //
				}else{
					str_xml.append("<FDescription></FDescription>"); //
				}
				str_xml.append("</Item>");
			}
			str_xml.append("</SupplierAttachListEntrys>");
		}
		
		return str_xml.toString();
	}
	
    //XML系统生产
	public String GetMtXml(){
		StringBuffer str_xml = new StringBuffer();   
		str_xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>") ; 
		return str_xml.toString();
	}
	public String GetErrorXml(int flag,String message){
		StringBuffer str_xml = new StringBuffer();
		if(flag==0){
			str_xml.append("<error>参数strBOID不行为空</error>"); // 
		}
		if(flag==1){
			str_xml.append("<error>参数strBOID错误</error>"); // 
		}
		if(flag==2){
			str_xml.append("<error>EAS系统取值错误</error>"); // 
		}
		if(flag==2){
			str_xml.append("<error>接口参数传入错误</error>"); // 
		}
		return str_xml.toString();
	}
	public String GetSuccess(int flag,String message){
		StringBuffer str_xml = new StringBuffer();
		if(flag==10){
			str_xml.append("<success>单据操作成功</success>"); // 
		}
		if(flag==11){
			str_xml.append("<success>单据操作成功</success>"); // 
		}
		return str_xml.toString();
	}
    
    
    
    
    
    
}