package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.AttachmentStorageTypeEnum;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeEntryCollection;
import com.kingdee.eas.fdc.contract.ContractChangeEntryFactory;
import com.kingdee.eas.fdc.contract.ContractChangeEntryInfo;
import com.kingdee.eas.fdc.contract.ContractChangeException;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeTypeEnum;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.CopySupplierEntryFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.NewMainSupplierEntryCollection;
import com.kingdee.eas.fdc.contract.NewMainSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.NewMainSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.SpecialtyTypeEntryCollection;
import com.kingdee.eas.fdc.contract.SpecialtyTypeEntryFactory;
import com.kingdee.eas.fdc.contract.SpecialtyTypeEntryInfo;
import com.kingdee.eas.fdc.contract.SupplierContentEntryCollection;
import com.kingdee.eas.fdc.contract.SupplierContentEntryFactory;
import com.kingdee.eas.fdc.contract.SupplierContentEntryInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

public class ChangeAuditBillControllerBean extends AbstractChangeAuditBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ChangeAuditBillControllerBean");
    protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
	throws BOSException, EASBizException {
    	ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
    	if(info.getChangeState()==null)
    		info.setChangeState(ChangeBillStateEnum.Saved);
    	if(info.getAuditType()!=null)
    		info.setAuditTypeName(info.getAuditType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
    	super._save(ctx, pk, info);
    }
    
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
    	if(info.getChangeState()==null)
    		info.setChangeState(ChangeBillStateEnum.Saved);
    	if(info.getAuditType()!=null)
    		info.setAuditTypeName(info.getAuditType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
    	return super._save(ctx, info);
    }
    
    protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
	throws BOSException, EASBizException {
    	ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
//    	if(info.getChangeState()==null)
    		info.setChangeState(ChangeBillStateEnum.Submit);
    	if(info.getAuditType()!=null)
    		info.setAuditTypeName(info.getAuditType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
    	super._submit(ctx, pk, info);
    	if(info.getSuppEntry()!=null&&info.getSuppEntry().size()>0){
    		if(isGenerateAfterAudit(ctx)){//����ģʽ������������
    			return;
    		}
    		//Ŀǰϵͳ�У�����������ύ���Զ����ɵ�ָ�Ϊ����״̬����Ҫ��Ϊ��������״̬һ�£������ύ״̬�� by Cassiel_peng
    		ChangeBill(ctx,model,FDCBillStateEnum.SUBMITTED);
    	}
    	if(model.get("fromweb")==null){
    		sendtoOA(ctx,info);
    	}
    }
    public boolean isBillInWorkflow(Context ctx,String id) throws BOSException{
		ProcessInstInfo instInfo = null;
		ProcessInstInfo procInsts[] = null;

		IEnactmentService service2 = EnactmentServiceFactory.createEnactService(ctx);
		procInsts = service2.getProcessInstanceByHoldedObjectId(id);
		int i = 0;
		for(int n = procInsts.length; i < n; i++){
			if("open.running".equals(procInsts[i].getState()) || "open.not_running.suspended".equals(procInsts[i].getState())){
				instInfo = procInsts[i];
			}
		}
		if(instInfo != null){
			return true;
		}else{
			return false;
		}
    }
    public void deleteOA(Context ctx,ChangeAuditBillInfo info) throws EASBizException{
    	if(info.getSourceFunction()!=null){
    		throw new EASBizException(new NumericExceptionSubItem("100","�Ѵ���OA���̣���ֹɾ����"));
    	}
//    	try {
//			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
//			builder.appendSql("select furl from t_oa");
//			IRowSet rs=builder.executeQuery();
//			String url=null;
//			while(rs.next()){
//				url=rs.getString("furl");
//			}
//			if(url!=null){
//				Service s=new Service();
//				Call call=(Call)s.createCall();
//				
//				call.setTargetEndpointAddress(new java.net.URL(url));
//	            call.addParameter("arg0", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//	            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
//	            call.setUseSOAPAction(true);
//				call.setTimeout(Integer.valueOf(1000*600000*60));
//		        call.setMaintainSession(true);
//				call.setOperationName("deleteEkpFlow");
//		        String result=(String)call.invoke(new Object[]{info.getSourceFunction()} );
//		        JSONObject rso = JSONObject.fromObject(result);
//		        if(!rso.getString("code").equals("1")){
//		        	throw new EASBizException(new NumericExceptionSubItem("100",rso.getString("massage")));
//		        }
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
//		}
    }
    
    public void sendtoOA(Context ctx,ChangeAuditBillInfo info) throws EASBizException{
    	try {
    		UserInfo u=UserFactory.getLocalInstance(ctx).getUserByID(ctx.getCaller());
    		if(u.getPerson()==null)return;
    		if(isBillInWorkflow(ctx,info.getId().toString()))return;
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("select furl from t_oa");
			IRowSet rs=builder.executeQuery();
			String url=null;
			while(rs.next()){
				url=rs.getString("furl");
			}
			if(url!=null){
				info=this.getChangeAuditBillInfo(ctx, new ObjectUuidPK(info.getId()));
				
				CurProjectInfo curProject=CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(info.getCurProject().getId()));
				if(!curProject.isIsOA()) return;
				Service s=new Service();
				Call call=(Call)s.createCall();
				
				call.setTargetEndpointAddress(new java.net.URL(url+"kmReviewWebserviceService?wsdl"));
	            call.addParameter("arg0", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
	            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
	            call.setUseSOAPAction(true);
				call.setTimeout(Integer.valueOf(1000*600000*60));
			        
				JSONObject json=new JSONObject();
				
				if(info.getSourceFunction()!=null){
					call.setOperationName("updatetestEkpReview");
					json.put("id", info.getSourceFunction());
					json.put("flowParam", info.getOaOpinion());
				}else{
					call.setOperationName("addtestEkpReview");
					json.put("id", info.getId().toString());
				}
				builder.clear();
				builder.appendSql("select TID from oatemplate where TYPE='changeAudit'");
				IRowSet tidrs = builder.executeQuery();
				String tmplateId=null;
				while(tidrs.next()){
					tmplateId=tidrs.getString("TID");
				}
				json.put("tmplateId", tmplateId);
				json.put("fdType", "05");
				json.put("docSubject", info.getName());

				json.put("loginName", u.getNumber());
				JSONObject obj = new JSONObject();
				if(info.getAuditType()!=null){
					ChangeTypeInfo ct=ChangeTypeFactory.getLocalInstance(ctx).getChangeTypeInfo(new ObjectUuidPK(info.getAuditType().getId()));
					obj.put("fd_38ee7d12aa7fa4", ct.getName());
				}
				obj.put("fd_38ee7d14312bb0", info.getTotalCost().doubleValue());
//				webҳ��鿴·��
				builder.clear();
				builder.appendSql("select url from weburl where type='view'");
				IRowSet rsv = builder.executeQuery();
				String sendUrl=null;
				StringBuffer sbv = new StringBuffer();
				while(rsv.next()){
//					http://172.17.4.125:8082/easWeb/#/
					String appendUrl = rsv.getString("url");
//					������ approval
					String appendType="approval/view?from=oa&id=";
//					id
					String idv = info.getId().toString();
					String appendId = URLEncoder.encode(idv, "utf-8");
//					token
					String token = "&token=";
					sendUrl = String.valueOf(sbv.append(appendUrl).append(appendType).append(appendId).append(token));
					System.out.println(sendUrl);
				}
				json.put("fdPcViewLink", sendUrl);
//				app�鿴��ַ
				String sendAppUrl = null;
				StringBuffer sba = new StringBuffer();
				builder.clear();
				builder.appendSql("select url from weburl where type ='app'");
				IRowSet rsapp = builder.executeQuery();
				while(rsapp.next()){
//					http://172.17.4.125:8082/easWeb/#/
					String appendUrl = rsapp.getString("url");
//					������ approval
					String appendType="approval/?from=oa&id=";
//					id
					String idv = info.getId().toString();
					String appendId = URLEncoder.encode(idv, "utf-8");
//					token
					String token = "&token=";
					sendAppUrl = String.valueOf(sba.append(appendUrl).append(appendType).append(appendId).append(token));
					System.out.println(sendUrl);
				}
				json.put("fdMobileViewLink", sendAppUrl);

				json.put("data",obj.toString());
				
				json.put("fd_application", info.getOaPosition().split(":")[0]);
				
				JSONArray attFile = new JSONArray();
				
				SelectorItemCollection attsic = new SelectorItemCollection();
				attsic.add(new SelectorItemInfo("id"));
				attsic.add(new SelectorItemInfo("attachment.name"));
				attsic.add(new SelectorItemInfo("attachment.remotePath"));
				attsic.add(new SelectorItemInfo("attachment.storageType"));
				attsic.add(new SelectorItemInfo("attachment.simpleName"));
				attsic.add(new SelectorItemInfo("boID"));
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("boID", info.getId().toString()));
				EntityViewInfo evi = new EntityViewInfo();
				evi.setFilter(filter);
				evi.setSelector(attsic);
				BoAttchAssoCollection cols= BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(evi);
				
	            for (int i=0;i<cols.size();i++) {
	                JSONObject attObj = new JSONObject();
	                AttachmentInfo att=cols.get(i).getAttachment();
	                if(att.getStorageType().equals(AttachmentStorageTypeEnum.FTP)){
	                	String simpleName=att.getSimpleName();
	                	if(att.getName()!=null){
	                		if(att.getName().indexOf("."+simpleName)<0){
		                		attObj.put("filename", att.getName()+"."+att.getSimpleName());
		                	}else{
		                		attObj.put("filename", att.getName());
		                	}
	                	}else{
	                		attObj.put("filename", "."+att.getSimpleName());
	                	}
		                attObj.put("filepath", att.getRemotePath());
		                attObj.put("fileType", "05");
		                attFile.add(attObj);
	                }
	            }
	            json.put("attFile", attFile);
	            
		        String result=(String)call.invoke(new Object[]{json.toString()} );
		        JSONObject rso = JSONObject.fromObject(result);
		        if(!rso.getString("code").equals("1")){
		        	throw new EASBizException(new NumericExceptionSubItem("100",rso.getString("massage")));
		        }else{
		        	info.setSourceFunction(rso.getString("oaid"));
		        	info.setState(FDCBillStateEnum.AUDITTING);
		        	info.setChangeState(ChangeBillStateEnum.Auditting);
		        	SelectorItemCollection sic=new SelectorItemCollection();
					sic.add("sourceFunction");
					sic.add("state");
					sic.add("changeState");
					ChangeAuditBillFactory.getLocalInstance(ctx).updatePartial(info,sic);
					
					Object atturl=rso.get("atturl");
					if(atturl!=null){
						SelectorItemCollection attupdatesic = new SelectorItemCollection();
						attupdatesic.add(new SelectorItemInfo("beizhu"));
						
						JSONArray atturlarry = rso.getJSONArray("atturl");
						for (int i=0;i<cols.size();i++) {
							AttachmentInfo att=cols.get(i).getAttachment();
							for(int j=0;j<atturlarry.size();j++){
								String simpleName=att.getSimpleName();
								String fileName=null;
								if(att.getName()!=null){
									if(att.getName().indexOf("."+simpleName)<0){
										fileName=att.getName()+"."+att.getSimpleName();
				                	}else{
				                		fileName=att.getName();
				                	}
								}else{
									fileName="."+att.getSimpleName();
								}
								if(fileName.equals(atturlarry.getJSONObject(j).getString("name"))){
									att.setBeizhu(atturlarry.getJSONObject(j).getString("url"));
									AttachmentFactory.getLocalInstance(ctx).updatePartial(att, attupdatesic);
				                }
							}
			            }
					}
		        }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
		}
    }
    /**
     * by Cassiel_peng
     */
    protected IObjectPK _submit(Context ctx, IObjectValue model)
	throws BOSException, EASBizException {
    	ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
    	
    	//�ύǰ���
		checkBillForSubmit( ctx,info);
		
    	ChangeSupplierEntryCollection infoCollection=info.getSuppEntry();
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	for(int i=0;i<infoCollection.size();i++){
    		ChangeSupplierEntryInfo entryInfo=(ChangeSupplierEntryInfo)infoCollection.get(i);
    		//�١��˴��޸ĺ�Ľ��
    		BigDecimal oriCostAmount=FDCHelper.toBigDecimal(entryInfo.getCostAmount(),2);
    		/**
    		 * �ɱ�����
    		 */
    		if(entryInfo.getContractBill()!=null&&entryInfo.getContractBill().isIsCoseSplit()&&entryInfo.getContractChange()!=null){
        		try {
        			//���ݱ��ָ���id���ñ䵥���Ƿ��Ѿ������   δ����ֺ����������ٽ��У��������   
        			String contractChangeId=entryInfo.getContractChange().getId().toString();//���ָ�ID
        			boolean isCostSplit = false;
        			FilterInfo filter=null;
        			builder.clear();
        			builder.appendSql("select fid from t_con_conchangesplit where FContractChangeID =? ");
        			builder.addParam(contractChangeId);
        			IRowSet rowSet1=builder.executeQuery();
        			while(rowSet1.next()){
        				isCostSplit=true;
        			}
        			if(isCostSplit){//�������ֹ���
        				builder.clear();
        				builder.appendSql("select famount from t_con_conchangesplit where FContractChangeID=? ");
        				builder.addParam(contractChangeId);
        				IRowSet rowSet2=builder.executeQuery(ctx);
        				while(rowSet2.next()){
        					//�ڡ���ȡ���ָ��Ĳ�ֺϼƽ��
        					BigDecimal splitCostAmountSum=FDCHelper.toBigDecimal(rowSet2.getBigDecimal("famount"),2);
        					if(oriCostAmount!=null&&!oriCostAmount.equals(splitCostAmountSum)){
        						//���������         ���ݱ��ָ�Id�ҵ�������������ָ��ĸ����ַ�¼��ͷ   
        						builder.clear();
        						builder.appendSql("select distinct(fparentid) from  T_FNC_PaymentSplitEntry  where  fcostbillid=? ");
        						builder.addParam(contractChangeId);
        						IRowSet rowSet3=builder.executeQuery();
        						while(rowSet3.next()){
        							filter=new FilterInfo();
        							filter.appendFilterItem("costBillId", contractChangeId);
        							PaymentSplitEntryFactory.getLocalInstance(ctx).delete(filter);//ɾ����¼
        							filter=new FilterInfo();
        							filter.appendFilterItem("id", rowSet3.getString("fparentid"));
        							PaymentSplitFactory.getLocalInstance(ctx).delete(filter);//ɾ��ͷ
        						}
        						//���������        ���ݱ��ָ�Id�ҵ�������������ָ��Ľ����ַ�¼��ͷ
        						builder.clear();
        						builder.appendSql("select distinct(fparentid) from  T_CON_SettlementCostSplitEntry  where  fcostbillid=? ");
        						builder.addParam(contractChangeId);
        						IRowSet rowSet4=builder.executeQuery();
        						while(rowSet4.next()){
        							filter=new FilterInfo();
        							filter.appendFilterItem("costBillId", contractChangeId);
        							SettlementCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);
        							filter=new FilterInfo();
        							filter.appendFilterItem("id", rowSet4.getString("fparentid"));
        							SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
        						}
        						//���������      ��������Ҫ����ָ���ID��ɾ��
        						filter=new FilterInfo();
        						filter.appendFilterItem("parent.contractChange.id", contractChangeId);
        						ConChangeSplitEntryFactory.getLocalInstance(ctx).delete(filter);//ɾ����¼
        						filter=new FilterInfo();
        						filter.appendFilterItem("contractChange.id", contractChangeId);
        						ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);//ɾ��ͷ
        					}
        				}
        			}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    		/**
			 * �ǳɱ�����
			 */
    		if(entryInfo.getContractBill()!=null&&!entryInfo.getContractBill().isIsCoseSplit()&&entryInfo.getContractChange()!=null){
    			try {
    				String contractChangeId=entryInfo.getContractChange().getId().toString();//���ָ�ID
//    				FDCSplitClientHelper.isBillSplited(entryInfo.getContractChange().getId().toString(),"T_CON_ConChangeNoCostSplit", "FContractChangeID");
    				boolean isCostSplit=false;
    				builder.clear();
        			builder.appendSql("select fid from T_CON_ConChangeNoCostSplit where FContractChangeID =? ");
        			builder.addParam(contractChangeId);
        			IRowSet rowSet1=builder.executeQuery();
        			while(rowSet1.next()){
        				isCostSplit=true;
        			}
    				FilterInfo filter =null;
    				if(isCostSplit){
    					builder.clear();
    					builder.appendSql("select famount from T_CON_ConChangeNoCostSplit where FContractChangeID=? ");
    					builder.addParam(contractChangeId);
    					IRowSet rowSet=builder.executeQuery(ctx);
    					while(rowSet.next()){
    						//�ڡ���ȡ���ָ��Ĳ�ֺϼƽ��
    						BigDecimal splitCostAmountSum=FDCHelper.toBigDecimal(rowSet.getBigDecimal("famount"),2);
    						if(oriCostAmount!=null&&!oriCostAmount.equals(splitCostAmountSum)){
        						//���������         ���ݱ��ָ�Id�ҵ�������������ָ��ĸ����ַ�¼��ͷ   
        						builder.clear();
        						builder.appendSql("select distinct(fparentid) from  T_FNC_PaymentNoCostSplitEntry  where  fcostbillid=? ");
        						builder.addParam(contractChangeId);
        						IRowSet rowSet3=builder.executeQuery();
        						while(rowSet3.next()){
        							filter=new FilterInfo();
        							filter.appendFilterItem("costBillId", contractChangeId);
        							PaymentNoCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);//ɾ����¼
        							filter=new FilterInfo();
        							filter.appendFilterItem("id", rowSet3.getString("fparentid"));
        							PaymentNoCostSplitFactory.getLocalInstance(ctx).delete(filter);//ɾ��ͷ
        						}
        						//���������        ���ݱ��ָ�Id�ҵ�������������ָ��Ľ����ַ�¼��ͷ
        						builder.clear();
        						builder.appendSql("select distinct(fparentid) from  T_CON_SettNoCostSplitEntry  where  fcostbillid=? ");
        						builder.addParam(contractChangeId);
        						IRowSet rowSet4=builder.executeQuery();
        						while(rowSet4.next()){
        							filter=new FilterInfo();
        							filter.appendFilterItem("costBillId", contractChangeId);
        							SettNoCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);
        							filter=new FilterInfo();
        							filter.appendFilterItem("id", rowSet4.getString("fparentid"));
        							SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
        						}
        						//���������      ��������Ҫ����ָ���ID��ɾ��
        						filter=new FilterInfo();
        						filter.appendFilterItem("parent.contractChange.id", contractChangeId);
        						ConChangeNoCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);//ɾ����¼
        						filter=new FilterInfo();
        						filter.appendFilterItem("contractChange.id", contractChangeId);
        						ConChangeNoCostSplitFactory.getLocalInstance(ctx).delete(filter);//ɾ��ͷ
    						}
    					}
    				}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}
		
//    	if(info.getChangeState()==null)
    		info.setChangeState(ChangeBillStateEnum.Submit);
    	System.err.println("");
    	if(info.getAuditType()!=null)
    		info.setAuditTypeName(info.getAuditType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
    	
    	if(info.getSuppEntry()!=null&&info.getSuppEntry().size()>0){
    		if(isGenerateAfterAudit(ctx)){//����ģʽ������������
    			return super._submit(ctx, info);
    		}
    		//ԭϵͳ�У�����������ύ���Զ����ɵ�ָ�Ϊ����״̬����Ҫ��Ϊ��������״̬һ�£������ύ״̬��
    		//by Cassiel_peng   2009-7-8
    		ChangeBill(ctx,model,FDCBillStateEnum.SUBMITTED);
    	}
    	
    	IObjectPK pk=super._submit(ctx, info);
    	
    	if(model.get("fromweb")==null){
    		sendtoOA(ctx,info);
    	}
    	
    	return pk;
    }

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ChangeAuditBillInfo info = (ChangeAuditBillInfo)super._getValue(ctx,pk);
		ChangeSupplierEntryCollection c = info.getSuppEntry();
		for(int i=0;i<c.size();i++){
			FilterInfo filter = new FilterInfo();
			FilterItemCollection filterItems = filter.getFilterItems();
			filterItems.add(new FilterItemInfo("parent.id", c.get(i).getId().toString()));
			SupplierContentEntryFactory.getLocalInstance(ctx).delete(filter);
			CopySupplierEntryFactory.getLocalInstance(ctx).delete(filter);
		}
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("changeAudit.id",info.getId().toString()));
		ContractChangeBillFactory.getLocalInstance(ctx).delete(filter);
		
		if(info.getSourceBillId()!=null){
			try {
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    			builder.appendSql("select furl from t_tlsc");
    			IRowSet rs=builder.executeQuery();
    			String url=null;
		        while(rs.next()){
		        	url=rs.getString("furl");
		        }
//				String url="http://172.17.16.30/TLSC.WebService/CostSynergy.asmx";
				String soapaction="http://tempuri.org/";
				Service service=new Service();
				Call call = (Call)service.createCall();
			
				call.setTargetEndpointAddress(url);            
				call.setOperationName(new QName(soapaction,"UpdateContactStatus"));
				call.addParameter(new QName(soapaction,"FID"),org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN); 
				call.addParameter(new QName(soapaction,"FState"), org.apache.axis.encoding.XMLType.XSD_INT, javax.xml.rpc.ParameterMode.IN); 
				call.setUseSOAPAction(true); 
				call.setSOAPActionURI(soapaction + "UpdateContactStatus");
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
				String r=(String)call.invoke(new Object[]{info.getSourceBillId(),"255"});
				JSONObject obj = JSONObject.fromObject(r);
				if(!"1".equals(obj.getString("State"))){
					throw new EASBizException(new NumericExceptionSubItem("100","ͬ��������ʧ��:"+obj.getString("Msg")));
				}
			} catch (ServiceException e) {
				logger.error(e.getMessage());
				throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
			} catch (RemoteException e) {
				logger.error(e.getMessage());
				throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		deleteOA(ctx,info);
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		for(int i=0;i<arrayPK.length;i++){			
			ChangeAuditBillInfo info= getChangeAuditBillInfo(ctx,arrayPK[i]);
			ChangeSupplierEntryCollection c = info.getSuppEntry();
			for(int j=0;j<c.size();j++){
				info.getSuppEntry().removeObject(j);
			}
			FilterInfo filter = new FilterInfo();
			FilterItemCollection filterItems = filter.getFilterItems();
			filterItems.add(new FilterItemInfo("changeAudit.id",info.getId().toString()));
			ContractChangeBillFactory.getLocalInstance(ctx).delete(filter);
			
			if(info.getSourceBillId()!=null){
				try {
					FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
	    			builder.appendSql("select furl from t_tlsc");
	    			IRowSet rs=builder.executeQuery();
	    			String url=null;
			        while(rs.next()){
			        	url=rs.getString("furl");
			        }
//					String url="http://172.17.16.30/TLSC.WebService/CostSynergy.asmx";
					String soapaction="http://tempuri.org/";
					Service service=new Service();
					Call call = (Call)service.createCall();
				
					call.setTargetEndpointAddress(url);            
					call.setOperationName(new QName(soapaction,"UpdateContactStatus"));
					call.addParameter(new QName(soapaction,"FID"),org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN); 
					call.addParameter(new QName(soapaction,"FState"), org.apache.axis.encoding.XMLType.XSD_INT, javax.xml.rpc.ParameterMode.IN); 
					call.setUseSOAPAction(true); 
					call.setSOAPActionURI(soapaction + "UpdateContactStatus");
					call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
					String r=(String)call.invoke(new Object[]{info.getSourceBillId(),"255"});
					JSONObject obj = JSONObject.fromObject(r);
					if(!"1".equals(obj.getString("State"))){
						throw new EASBizException(new NumericExceptionSubItem("100","ͬ��������ʧ��:"+obj.getString("Msg")));
					}
				} catch (ServiceException e) {
					logger.error(e.getMessage());
					throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
				} catch (RemoteException e) {
					logger.error(e.getMessage());
					throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		super._delete(ctx, arrayPK);
	}
    
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
	throws BOSException, EASBizException {
		ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
		if(info.getAuditType()!=null)
    		info.setAuditTypeName(info.getAuditType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
//		removeDetailEntries(ctx, info);		
		super._update(ctx, pk, info);
	}
	
	private void removeDetailEntries(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ChangeAuditBillInfo info = (ChangeAuditBillInfo) model;
		ChangeSupplierEntryCollection c = info.getSuppEntry();
		for(int i=0;i<c.size();i++){
			FilterInfo filter = new FilterInfo();
			FilterItemCollection filterItems = filter.getFilterItems();
			if(c.get(i).getId()!=null){
				filterItems.add(new FilterItemInfo("parent.id", c.get(i).getId().toString()));
				SupplierContentEntryFactory.getLocalInstance(ctx).delete(filter);
			}
		}
	}
	
	private void ChangeBill(Context ctx, IObjectValue model, FDCBillStateEnum state) throws BOSException, EASBizException {
		ChangeAuditBillInfo info = (ChangeAuditBillInfo) model;
		
		ChangeSupplierEntryCollection c = info.getSuppEntry();
		//֮ǰϵͳ���������������·���λ��¼Ϊ���ǲ������ύ��,��������ģʽ����û�д����Ƶ�,�ʱ��봦��Ϊ�յ���� by Cassiel_peng 2009-9-25
		if(c!=null){
			for(int i=0;i<c.size();i++){			
				ChangeSupplierEntryInfo entry = c.get(i);
				if(entry.getContractChange()==null){
					ContractChangeBillInfo change = new ContractChangeBillInfo();
					//�ܳа���
					NewMainSupplierEntryCollection colll = (NewMainSupplierEntryCollection)entry.get("newMainSupp");
					NewMainSupplierEntryCollection newColl = change.getNewMainSupp();
					NewMainSupplierEntryInfo newInfo = null;
					for(int j = 0 ; j < colll.size() ; j++ ){
						newInfo = new NewMainSupplierEntryInfo();
						newInfo.setMainSupp(colll.get(j).getMainSupp());
						newInfo.setSeq( j + 1);
						newColl.add(newInfo);
					}
					change.setSourceBillId(info.getSourceBillId());
					change.setConductTime(FDCHelper.getCurrentDate());
					change.setSettleTime(FDCHelper.getCurrentDate());	    	
					change.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
					change.setSourceType(info.getSourceType());
//					change.setOrgUnit(ContextUtil.getCurrentCostUnit(ctx).castToFullOrgUnitInfo());
					change.setOrgUnit(info.getOrgUnit());
					change.setChangeAudit(info);
					if(info.getNumber()!=null)
						change.setChangeAuditNumber(info.getNumber());
					change.setState(state);
					change.setChangeType(info.getAuditType());
					if(info.getAuditTypeName()!=null)
						change.setChangeTypeName(info.getAuditTypeName());
					//Ч�ɱ����ֻ�Ǽ��ڵ�1�����ָ��ϣ������ڱ��ָ������ӡ���Ч�ɱ���������Ч�ɱ�ԭ��  20080324
					if(i==0){
						change.setInvalidCostReason(info.getInvalidCostReason());
						change.setCostNouse(info.getCostNouse());
					}else{
						change.setInvalidCostReason(null);
						change.setCostNouse(GlUtils.zero);
					}
					//�ڼ�
					change.setBookedDate(info.getBookedDate());
					change.setPeriod(info.getPeriod());
					
					change.setChangeReason(info.getChangeReason());
					change.setChangeSubject(info.getChangeSubject());
					change.setConductDept(info.getConductDept());
					change.setUrgentDegree(info.getUrgentDegree());
					change.setCurProject(info.getCurProject());
					if(info.getCurProjectName()!=null)
						change.setCurProjectName(info.getCurProjectName());
					change.setJobType(info.getJobType());
					if(info.getJobTypeName()!=null)
						change.setJobTypeName(info.getJobTypeName());
					//רҵ���Ϳ��Զ�ѡ
//					change.setSpecialtyType(info.getSpecialtyType());
					SpecialtyTypeEntryCollection cColl = change.getMutiSpecialtyType();
					SpecialtyTypeEntryCollection stColl = info.getMutiSpecialtyType();
					SpecialtyTypeEntryInfo sinfo = null;
					for(int z = 0 ; z < stColl.size() ; z ++) {
						sinfo =  new SpecialtyTypeEntryInfo();
						sinfo.setSpecialtyType(stColl.get(z).getSpecialtyType());
						sinfo.setSeq(z + 1);
						cColl.add(sinfo);
					}
//					if(info.getSpecialtyTypeName()!=null)
//						change.setSpecialtyTypeName(info.getSpecialtyTypeName());
					change.setGraphCount(info.getGraphCount());
					change.setConductTime(info.getCreateTime());
					change.setMainSupp(entry.getMainSupp());
					change.setContractBill(entry.getContractBill());
					if(entry.getContractBill()!=null&&entry.getContractBill().getNumber()!=null){
						change.setContractBillNumber(entry.getContractBill().getNumber());
						change.setIsConSetted(entry.getContractBill().isHasSettled());
						change.setIsCostSplit(entry.getContractBill().isIsCoseSplit());
					}
					change.setBalanceType(entry.getBalanceType());
					change.setIsDeduct(entry.isIsDeduct());
					change.setDeductAmount(entry.getDeductAmount());
					change.setDeductReason(entry.getDeductReason());
					//ԭ�ҽ��
					change.setAmount(entry.getCostAmount());
					change.setOriginalAmount(entry.getOriCostAmount());
					//����
					change.setCurrency(entry.getCurrency());
					change.setExRate(entry.getExRate());
					//����ԭʼ��ϵ���� eric_wang 2010.05.31
					change.setOriginalContactNum(entry.getOriginalContactNum());
					change.setIsSureChangeAmt(entry.isIsSureChangeAmt());
					change.setIsImportChange(info.isIsImportChange());
					change.setConstructPrice(entry.getConstructPrice());
					change.setIsFee(info.getIsFee());
					change.setWfType(info.getWfType());
					
					SupplierContentEntryCollection coll = new SupplierContentEntryCollection();
					EntityViewInfo vit = new EntityViewInfo();
					FilterInfo fit = new FilterInfo();
					FilterItemCollection itt = fit.getFilterItems();	
					if(info.getId()!=null&&entry.getId()!=null){
						itt.add(new FilterItemInfo("parent.id", entry.getId().toString(),CompareType.EQUALS));
						vit.setFilter(fit);
						vit.getSelector().add("content.*");
						SorterItemInfo sortName = new SorterItemInfo("seq");
		                sortName.setSortType(SortType.ASCEND);
						vit.getSorter().add(sortName);
						coll = SupplierContentEntryFactory.getLocalInstance(ctx).getSupplierContentEntryCollection(vit);
					}else{
						coll = entry.getEntrys();
					}
					ContractChangeEntryCollection entrycoll = change.getEntrys();
					for(int j=0;j<coll.size();j++){
						SupplierContentEntryInfo con = coll.get(j);
						ContractChangeEntryInfo test = new ContractChangeEntryInfo();
						test.setNumber(con.getContent().getNumber());
						test.setChangeContent(con.getContent().getChangeContent());
						test.setIsBack(con.getContent().isIsBack());
						test.setSeq(con.getContent().getSeq());
						test.setEffect(con.getContent().getEffect());
						entrycoll.add(test);
					}
					change.setName(info.getName()+"_"+(i+1));
					String orgId = ContextUtil.getCurrentCostUnit(ctx).getId().toString();
					String billNumber = null;
					/***
					 * ���ָ�����ʱ�����ñ�����򱨴������������Ƿ�������,���磺˳����Ƿ��Ѿ�ʹ����
					 * �쳣���Ե���ʹ��Ӧ�õģ����������̨����������
					 */
					try {
						billNumber = FDCHelper.getNumberByCodingRule(ctx, change, orgId);
					} catch (Exception e1){
						logger.error("���ָ�����ʱ�����ñ�����򱨴������������Ƿ�������,���磺˳����Ƿ��Ѿ�ʹ���꣡", e1);
						if(e1 instanceof BOSException)
							throw (BOSException)e1;
						if(e1 instanceof EASBizException)
							throw (EASBizException)e1;
					}
					if(billNumber == null) {			
						billNumber = info.getNumber()+"_"+(i+1);
					}			
					change.setNumber(billNumber);
					//���������ֶ�
					if(info.getConductUnit()!=null){
						change.setConductUnit(info.getConductUnit());
					}
					if(info.getOffer()!=null){
						change.setOffer(info.getOffer());
					}
					if(info.getConstrUnit()!=null){
						change.setConstrUnit(info.getConstrUnit());
					}
					if(info.getConstrSite()!=null){
						change.setConstrSite(info.getConstrSite());
					}
					change.setIsChangeContract(info.isIsChangeContract());
					change.setIsReceipts(info.isIsReceipts());
					change.setCostUnit(info.getCostUnit());
					change.setReaDesc(info.getReaDesc());
					ContractChangeBillFactory.getLocalInstance(ctx).addnew(change);
					entry.setContractChange(change);
					//�����ı������������ָ�֮�󲻻Ὣָ����·���λ��¼���������������ڴ˴��·���λ��¼�Լ�ά����
					//���������һ������һ ��     by Cassiel_peng  2009-9-26
					if(entry!=null&&entry.getId()!=null){
						SelectorItemCollection _selector=new SelectorItemCollection();
						_selector.add("contractChange");
						ChangeSupplierEntryFactory.getLocalInstance(ctx).updatePartial(entry, _selector);
					}
				}
				else{
					ContractChangeBillInfo change = entry.getContractChange();
//					for(i=0;i<change.getEntrys().size();i++){
//						change.getEntrys().removeObject(i);
//						i--;
//					}
					FilterInfo fi = new FilterInfo();
					FilterItemCollection it = fi.getFilterItems();	
					if(change.getId()!=null){
						it.add(new FilterItemInfo("parent.id", change.getId().toString(),CompareType.EQUALS));
						ContractChangeEntryFactory.getLocalInstance(ctx).delete(fi);
					}
					change.setName(info.getName()+"_"+(i+1));
					if(info.getNumber()!=null)
						change.setChangeAuditNumber(info.getNumber());
					//Ч�ɱ����ֻ�Ǽ��ڵ�1�����ָ��ϣ������ڱ��ָ������ӡ���Ч�ɱ���������Ч�ɱ�ԭ��  20080324
					if(i==0){
						change.setInvalidCostReason(info.getInvalidCostReason());
						change.setCostNouse(info.getCostNouse());
					}else{
						change.setInvalidCostReason(null);
						change.setCostNouse(GlUtils.zero);
					}
					
					//�ڼ�
					change.setBookedDate(info.getBookedDate());
					change.setPeriod(info.getPeriod());
					change.setSourceType(info.getSourceType());
					
					change.setChangeType(info.getAuditType());
					if(info.getAuditTypeName()!=null)
						change.setChangeTypeName(info.getAuditTypeName());
					change.setChangeReason(info.getChangeReason());
					change.setChangeSubject(info.getChangeSubject());
					change.setConductDept(info.getConductDept());
					change.setUrgentDegree(info.getUrgentDegree());
					change.setCurProject(info.getCurProject());
					if(info.getCurProjectName()!=null)
						change.setCurProjectName(info.getCurProjectName());
					change.setJobType(info.getJobType());
					if(info.getJobTypeName()!=null)
						change.setJobTypeName(info.getJobTypeName());
					change.setState(state);
//					//רҵ���Ϳ��Զ�ѡ
					SpecialtyTypeEntryCollection newColl = change.getMutiSpecialtyType();
					newColl.clear();
					
					//ɾ���������ݿ������
					FilterInfo fiil = new FilterInfo();
					FilterItemCollection iitl = fiil.getFilterItems();
					iitl.add(new FilterItemInfo("contracChangeParent.id",change.getId().toString(),CompareType.EQUALS));
					SpecialtyTypeEntryFactory.getLocalInstance(ctx).delete(fiil);
					
					
					SpecialtyTypeEntryCollection stColl = info.getMutiSpecialtyType();
					SpecialtyTypeEntryInfo sinfo = null;
					for(int z = 0 ; z < stColl.size() ; z ++) {
						sinfo =  new SpecialtyTypeEntryInfo();
						sinfo.setSpecialtyType(stColl.get(z).getSpecialtyType());
						sinfo.setContracChangeParent(change);
						sinfo.setSeq(z+1);
						SpecialtyTypeEntryFactory.getLocalInstance(ctx).addnew(sinfo);
					}
//					change.setSpecialtyType(info.getSpecialtyType());
//					if(info.getSpecialtyTypeName()!=null)
//						change.setSpecialtyTypeName(info.getSpecialtyTypeName());
					change.setGraphCount(info.getGraphCount());
					change.setConductTime(info.getCreateTime());
					change.setMainSupp(entry.getMainSupp());
					
////					//�ܳа���
					NewMainSupplierEntryCollection colll = (NewMainSupplierEntryCollection)entry.get("mySupplier");
					if(colll != null){ 
//						NewMainSupplierEntryCollection newMainColl = change.getNewMainSupp();
//						newMainColl.clear();
					
						NewMainSupplierEntryFactory.getLocalInstance(ctx).delete(fiil);
						
						NewMainSupplierEntryInfo newInfo = null;
						for(int j = 0 ; j < colll.size() ; j++ ){
							newInfo = new NewMainSupplierEntryInfo();
							newInfo.setMainSupp(colll.get(j).getMainSupp());
							newInfo.setContracChangeParent(change);
							newInfo.setSeq(j +1);
							NewMainSupplierEntryFactory.getLocalInstance(ctx).addnew(newInfo);
//							newMainColl.add(newInfo);
						}
					}
					
					change.setContractBill(entry.getContractBill());
					if(entry.getContractBill()!=null&&entry.getContractBill().getNumber()!=null){
						change.setContractBillNumber(entry.getContractBill().getNumber());
						change.setIsConSetted(entry.getContractBill().isHasSettled());
						change.setIsCostSplit(entry.getContractBill().isIsCoseSplit());
					}
					change.setBalanceType(entry.getBalanceType());
					change.setIsDeduct(entry.isIsDeduct());
					change.setDeductAmount(entry.getDeductAmount());
					change.setDeductReason(entry.getDeductReason());
					//ԭ�ҽ��
					change.setAmount(entry.getCostAmount());
					change.setOriginalAmount(entry.getOriCostAmount());
					//����
					change.setCurrency(entry.getCurrency());
					change.setExRate(entry.getExRate());
					//ԭʼ��ϵ���� eric_wang 2010.05.31
					change.setOriginalContactNum(entry.getOriginalContactNum());
					change.setIsSureChangeAmt(entry.isIsSureChangeAmt());
					change.setIsImportChange(info.isIsImportChange());
					change.setConstructPrice(entry.getConstructPrice());
					
					change.setIsChangeContract(info.isIsChangeContract());
					change.setIsReceipts(info.isIsReceipts());
					change.setCostUnit(info.getCostUnit());
					change.setReaDesc(info.getReaDesc());
					change.setIsFee(info.getIsFee());
					change.setWfType(info.getWfType());
					
					EntityViewInfo vit = new EntityViewInfo();
					FilterInfo fit = new FilterInfo();
					FilterItemCollection itt = fit.getFilterItems();	
					if(entry.getId()!=null)
						itt.add(new FilterItemInfo("parent.id", entry.getId().toString(),CompareType.EQUALS));
					vit.setFilter(fit);
					vit.getSelector().add("content.*");
					SorterItemInfo sortName = new SorterItemInfo("seq");
	                sortName.setSortType(SortType.ASCEND);
					vit.getSorter().add(sortName);
					SupplierContentEntryCollection coll = SupplierContentEntryFactory.getLocalInstance(ctx).getSupplierContentEntryCollection(vit);
					for(int j=0;j<coll.size();j++){
						SupplierContentEntryInfo con = coll.get(j);
						ContractChangeEntryInfo test = new ContractChangeEntryInfo();
						test.setNumber(con.getContent().getNumber());
						test.setChangeContent(con.getContent().getChangeContent());
						test.setIsBack(con.getContent().isIsBack());
						test.setSeq(con.getContent().getSeq());
						test.setEffect(con.getContent().getEffect());
						test.setParent(change);
						ContractChangeEntryFactory.getLocalInstance(ctx).addnew(test);
					}
					//���������ֶ�
					if(info.getConductUnit()!=null){
						change.setConductUnit(info.getConductUnit());
					}
					if(info.getOffer()!=null){
						change.setOffer(info.getOffer());
					}
					if(info.getConstrUnit()!=null){
						change.setConstrUnit(info.getConstrUnit());
					}
					if(info.getConstrSite()!=null){
						change.setConstrSite(info.getConstrSite());
					}
					SelectorItemCollection selector = new SelectorItemCollection();
//					selector.add("mutiSpecialtyType");
//					selector.add("newMainSupp");
					selector.add("changeType");
					selector.add("changeReason");
					selector.add("changeSubject");
					selector.add("conductDept");
					selector.add("urgentDegree");
					selector.add("curProject");
					selector.add("jobType");
					selector.add("specialtyType");
					selector.add("graphCount");
					selector.add("mainSupp");
					selector.add("contractBill");
					selector.add("balanceType");
					selector.add("isDeduct");
					selector.add("deductAmount");
					selector.add("deductReason");
//					selector.add("entrys.*");
					selector.add("amount");
					selector.add("contractBillNumber");
					selector.add("conductTime");
					selector.add("state");
					selector.add("changeAuditNumber");
					selector.add("changeTypeName");
					selector.add("curProjectName");
					selector.add("jobTypeName");
					selector.add("specialtyTypeName");
					selector.add("costNouse");
					selector.add("invalidCostReason");
					selector.add("originalAmount");
					selector.add("bookedDate");
					selector.add("period");
					selector.add("isConSetted");
					selector.add("isCostSplit");
					selector.add("conductUnit");
					selector.add("offer");
					selector.add("constrUnit");
					selector.add("constrSite");
					selector.add("originalContactNum");
					selector.add("isSureChangeAmt");
					selector.add("isImportChange");
					selector.add("constructPrice");
					
					selector.add("isChangeContract");
					selector.add("isReceipts");
					selector.add("costUnit");
					selector.add("reaDesc");
					selector.add("isFee");
					selector.add("wfType");
					selector.add("name");
					ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, selector);
				}
			}
		}
//		if(info.getId()!=null){
//		FilterInfo filter=new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("changeAudit.id",info.getId().toString(),CompareType.EQUALS));
//		ContractChangeBillFactory.getLocalInstance(ctx).delete(filter);
//		}
	}

	protected void _register(Context ctx, Set idSet) throws BOSException, EASBizException {
		for (Iterator iter = idSet.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			_register4WF(ctx, new ObjectUuidPK(BOSUuid.read(id)));
//			ChangeAuditBillInfo billInfo = new ChangeAuditBillInfo();
//			billInfo.setId(BOSUuid.read(id));
//			billInfo.setChangeState(ChangeBillStateEnum.Register);
//			SelectorItemCollection selector = new SelectorItemCollection();
//			selector.add("changeState");
//			_updatePartial(ctx, billInfo, selector);

		}		
	}

	protected void _disPatch(Context ctx, Set idSet) throws BOSException, EASBizException {
		for (Iterator iter = idSet.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			_disPatch4WF(ctx, new ObjectUuidPK(BOSUuid.read(id)));
//			ChangeAuditBillInfo billInfo = new ChangeAuditBillInfo();
//			billInfo.setId(BOSUuid.read(id));
//			billInfo.setChangeState(ChangeBillStateEnum.Announce);
//			SelectorItemCollection selector = new SelectorItemCollection();
//			selector.add("changeState");
//			_updatePartial(ctx, billInfo, selector);
		}	
		
	}

	protected void _aheadDisPatch(Context ctx, Set idSet) throws BOSException, EASBizException {
		for (Iterator iter = idSet.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			IObjectPK pk = new ObjectUuidPK(BOSUuid.read(id));
			_aheadDisPatch4WF(ctx,pk);
//			ChangeAuditBillInfo info = (ChangeAuditBillInfo)super._getValue(ctx,pk);
//			if(info.getSuppEntry().size()>0){
//	    		ChangeBill(ctx,info,FDCBillStateEnum.ANNOUNCE);
//	    	}
//			info.setChangeState(ChangeBillStateEnum.AheadDisPatch);
//			SelectorItemCollection selector = new SelectorItemCollection();
//			selector.add("changeState");
//			_updatePartial(ctx, info, selector);			
		}			
	}
	
	/**
	 * ����
	 */
	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ChangeAuditBillInfo billInfo = new ChangeAuditBillInfo();

		billInfo.setId(BOSUuid.read(pk.toString()));
		billInfo.setState(FDCBillStateEnum.INVALID);
		billInfo.setChangeState(ChangeBillStateEnum.INVALID);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("changeState");
		
		_updatePartial(ctx, billInfo, selector);
	}

	/**
	 * ��Ч
	 */
	protected void _cancelCancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ChangeAuditBillInfo billInfo = new ChangeAuditBillInfo();

		billInfo.setId(BOSUuid.read(pk.toString()));
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		billInfo.setChangeState(ChangeBillStateEnum.Audit);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("changeState");
		
		_updatePartial(ctx, billInfo, selector);
		
	}

	protected void _setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {		
		ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(billId));
		billInfo.setChangeState(ChangeBillStateEnum.Auditting);
		billInfo.setState(FDCBillStateEnum.AUDITTING);
		int num = billInfo.getSuppEntry().size();
		ChangeSupplierEntryCollection c = billInfo.getSuppEntry();
		if(num>0){
			for(int i=0; i<num; i++){
				ChangeSupplierEntryInfo entry = c.get(i);
				if(entry.getContractChange()!=null){
					ContractChangeBillInfo change = entry.getContractChange();
					ContractChangeBillFactory.getLocalInstance(ctx).setAudittingStatus(change.getId());
					/*change.setState(FDCBillStateEnum.AUDITTING);
					SelectorItemCollection sele = new SelectorItemCollection();
					sele.add("state");
					ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, sele);*/
				}				
			}
		}
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("changeState");
		selector.add("state");
		_updatePartial(ctx, billInfo, selector);
	}

	protected void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(billId));
		billInfo.setChangeState(ChangeBillStateEnum.Submit);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		int num = billInfo.getSuppEntry().size();
		ChangeSupplierEntryCollection c = billInfo.getSuppEntry();
		if(num>0){
			for(int i=0; i<num; i++){
				ChangeSupplierEntryInfo entry = c.get(i);
				if(entry.getContractChange()!=null){
					ContractChangeBillInfo change = entry.getContractChange();
					ContractChangeBillFactory.getLocalInstance(ctx).setSubmitStatus(change.getId());
					/*change.setState(FDCBillStateEnum.AUDITTING);
					SelectorItemCollection sele = new SelectorItemCollection();
					sele.add("state");
					ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, sele);*/
					}				
			}
		}
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("changeState");
		selector.add("state");
		_updatePartial(ctx, billInfo, selector);
	}
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
	EASBizException {
		
//		ChangeAuditBillInfo billInfo = new ChangeAuditBillInfo();
		ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx)
			.getChangeAuditBillInfo(new ObjectUuidPK(billId));

		checkBillForAudit( ctx,billId,billInfo);
		
//		billInfo.setId(billId);
		billInfo.setChangeState(ChangeBillStateEnum.Audit);
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		billInfo.setAuditTime(new Date());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("changeState");
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		ChangeSupplierEntryCollection c = billInfo.getSuppEntry();
		//֮ǰϵͳ���������������·���λ��¼Ϊ���ǲ������ύ��,��������ģʽ����û�д�����,�ʱ��봦��Ϊ�յ����  by Cassiel_peng 2009-9-25
		if(c!=null){
			int num = billInfo.getSuppEntry().size();
			if(num>0){
				Set set=new HashSet();
				for(int i=0;i<num;i++){
					ChangeSupplierEntryInfo entry = c.get(i);
					set.add(entry.getContractBill().getId().toString());
				}
				EntityViewInfo view=new EntityViewInfo();
				view.setFilter(new FilterInfo());
				view.getFilter().getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
				view.getSelector().add("hasSettled");
				view.getSelector().add("number");
				ContractBillCollection cons=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
				boolean hasSettled=false;
				String conNumber="";
				for(int i=0;i<cons.size();i++){
					if(cons.get(i).isHasSettled()){
						hasSettled=true;
						conNumber+="����"+cons.get(i).getNumber()+"��";
					}
				}
				if(hasSettled){
					throw new EASBizException(new NumericExceptionSubItem("111","������ͨ��������������Ǽ��·���λ�ڴ����ѽ���ĺ�ͬ����ͬ���룺"+conNumber.substring(1)));
				}
				List list = new ArrayList();
				for(int i=0; i<num; i++){
					ChangeSupplierEntryInfo entry = c.get(i);
					if(entry.getContractChange()!=null){
						ContractChangeBillInfo change = entry.getContractChange();
						list.add(change.getId().toString());
//					change.setState(FDCBillStateEnum.AUDITTED);
//					change.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
//					change.setAuditTime(new Date());
//					SelectorItemCollection sele = new SelectorItemCollection();
//					sele.add("auditor");
//					sele.add("auditTime");
//					sele.add("state");
//					ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, sele);
					}				
				}
				
				Map initParam = FDCUtils.getDefaultFDCParam(ctx,null);//����ȡ��	
				boolean isGenerateAfterAuidt = true;
				if(initParam.get(FDCConstants.FDC_PARAM_AUTOCHANGETOPROJECTVISA)!=null){
					isGenerateAfterAuidt = Boolean.valueOf(initParam.get(FDCConstants.FDC_PARAM_GENERATEAFTERAUDIT).toString()).booleanValue();
				}
				if(c!=null&&c.size()>0){
					if(isGenerateAfterAuidt){
						ChangeBill(ctx, billInfo, FDCBillStateEnum.SAVED);
					}
				}
				//2008-11-19 ��ΪContractChangeBill����������
				if(list!=null && list.size()>0){
					if(!isGenerateAfterAuidt){
						ContractChangeBillFactory.getLocalInstance(ctx).audit(list);
					}
				}
			}
		}
		_updatePartial(ctx, billInfo, selector);
		
		
		updatePeriod(ctx, billId);
		
		if(billInfo.getSourceBillId()!=null){
			try {
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    			builder.appendSql("select furl from t_tlsc");
    			IRowSet rs=builder.executeQuery();
    			String url=null;
		        while(rs.next()){
		        	url=rs.getString("furl");
		        }
//				String url="http://172.17.16.30/TLSC.WebService/CostSynergy.asmx";
				String soapaction="http://tempuri.org/";
				Service service=new Service();
				Call call = (Call)service.createCall();
			
				call.setTargetEndpointAddress(url);            
				call.setOperationName(new QName(soapaction,"UpdateContactStatus"));
				call.addParameter(new QName(soapaction,"FID"),org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN); 
				call.addParameter(new QName(soapaction,"FState"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); 
				call.addParameter(new QName(soapaction,"FChangeContent"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); 
				call.addParameter(new QName(soapaction,"FTotalCost"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); 
				call.setUseSOAPAction(true);
				call.setSOAPActionURI(soapaction + "UpdateContactStatus");
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
				call.setEncodingStyle("UTF-8");
				String r=(String)call.invoke(new Object[]{billInfo.getSourceBillId(),"4",billInfo.getEntrys().get(0).getChangeContent(),billInfo.getTotalCost()});
				JSONObject obj = JSONObject.fromObject(r);
				if(!"1".equals(obj.getString("State"))){
					throw new EASBizException(new NumericExceptionSubItem("100","ͬ��������ʧ��:"+obj.getString("Msg")));
				}
			} catch (ServiceException e) {
				logger.error(e.getMessage());
				throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
			} catch (RemoteException e) {
				logger.error(e.getMessage());
				throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ���ݵ�ǰ��Ŀ�ɱ��ڼ�����ݻ����ݵ�ҵ�����ںͶ����ڼ�
	 * @param ctx
	 * @param billId
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void updatePeriod(Context ctx, BOSUuid billId) throws EASBizException, BOSException {
		
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("isRespite");
		selectors.add("curProject.id");
		selectors.add("curProject.fullOrgUnit.id");
		selectors.add("bookedDate");
		selectors.add("period.*");
		ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(billId), selectors);
		
		String companyID = billInfo.getCurProject().getFullOrgUnit().getId().toString();
		Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyID);
		boolean isInCore = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INCORPORATION);
		if(isInCore){
			//�����½�ͳһ�������߼�����
			String prjId = billInfo.getCurProject().getId().toString();
			//�ɱ��ڼ�
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
			PeriodInfo billPeriod = billInfo.getPeriod();
			PeriodInfo shouldPeriod = null;//���������ڼ�
			Date bookedDate = DateTimeUtils.truncateDate(billInfo.getBookedDate());
			
			if(finPeriod==null){
				throw new EASBizException(new NumericExceptionSubItem("100","��������Ӧ����֯û�е�ǰʱ����ڼ䣬�������ã�"));
			}
			/***************
			 * ��1����������ȷ�ϵ��ϵġ�ҵ�����ڡ��͡�ҵ���ڼ䡱���ڹ�����Ŀ�ɱ����񡰵�ǰ�ڼ䡱ʱ����ҵ���ڼ䡱����
			 * ��2����������ȷ�ϵ��ϵġ�ҵ�����ڡ��͡�ҵ���ڼ䡱С�ڵ��ڹ�����Ŀ�ɱ����񡰵�ǰ�ڼ䡱ʱ����ҵ���ڼ䡱����Ϊ������Ŀ�ɱ����񡰵�ǰ�ڼ䡱
			 *	
			 *	ԭ�����ֱ���ʱ��ͬ���ڼ��ϳ�����
			 */
			if (billPeriod != null && billPeriod.getNumber() > finPeriod.getNumber()) {
				if (bookedDate.before(billPeriod.getBeginDate())) {
					bookedDate = billPeriod.getBeginDate();
				} else if (bookedDate.after(billPeriod.getEndDate())) {
					bookedDate = billPeriod.getEndDate();
				}
				shouldPeriod = billPeriod;
			} else if (finPeriod != null) {
				if (bookedDate.before(finPeriod.getBeginDate())) {
					bookedDate = finPeriod.getBeginDate();
				} else if (bookedDate.after(finPeriod.getEndDate())) {
					bookedDate = finPeriod.getEndDate();
				}
				shouldPeriod = finPeriod;
			}
			
			//���±����������ҵ�����ڣ�����ڼ���ݻ�״̬
			
			selectors = new SelectorItemCollection();
			selectors.add("period");
			selectors.add("bookedDate");
			selectors.add("isRespite");
			billInfo.setBookedDate(bookedDate);
			billInfo.setPeriod(shouldPeriod);
			billInfo.setIsRespite(false);
			_updatePartial(ctx, billInfo, selectors);
		}
	}
	
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException{
//		ChangeAuditBillInfo billInfo = new ChangeAuditBillInfo();
		ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx)
		.getChangeAuditBillInfo(new ObjectUuidPK(billId));
		
		//�����У��
		checkBillForUnAudit( ctx,billId,billInfo);
		
//		billInfo.setId(billId);
		billInfo.setChangeState(ChangeBillStateEnum.Submit);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("changeState");
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		int num = billInfo.getSuppEntry().size();
		ChangeSupplierEntryCollection c = billInfo.getSuppEntry();
		if(num>0){
			List list = new ArrayList();
			for(int i=0; i<num; i++){
				ChangeSupplierEntryInfo entry = c.get(i);
				if(entry.getContractChange()!=null){
					ContractChangeBillInfo change = entry.getContractChange();
					
					//����Ƿ��Ѿ������
					FilterInfo filterSett = new FilterInfo();
					filterSett.getFilterItems().add(
							new FilterItemInfo("contractChange.id", change.getId().toString()));
					filterSett.getFilterItems().add(
							new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					boolean hasSettleSplit = false;
					if (ConChangeSplitFactory.getLocalInstance(ctx).exists(filterSett)
							|| ConChangeNoCostSplitFactory.getLocalInstance(ctx)
							.exists(filterSett)) {
						hasSettleSplit = true;
					}
					if(hasSettleSplit){
						throw new ContractChangeException(ContractChangeException.HASSPLIT);
					}
					list.add(change.getId().toString());
//					change.setState(FDCBillStateEnum.SAVED);
//					change.setAuditor(null);
//					change.setAuditTime(null);
//					SelectorItemCollection sele = new SelectorItemCollection();
//					sele.add("auditor");
//					sele.add("auditTime");
//					sele.add("state");
//					ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, sele);
				}
			}
			//2008-11-19 ��ΪContractChangeBill�ķ���������
			if(list!=null && list.size()>0){
				boolean isGenerateAfterAuidt=FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_GENERATEAFTERAUDIT);
				if(!isGenerateAfterAuidt){
					ContractChangeBillFactory.getLocalInstance(ctx).unAudit(list);
				}
			}
		}
		_updatePartial(ctx, billInfo, selector);

	}

	protected void _register4WF(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ChangeAuditBillInfo billInfo = (ChangeAuditBillInfo)super._getValue(ctx,pk);
		billInfo.setChangeState(ChangeBillStateEnum.Register);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("changeState");
		_updatePartial(ctx, billInfo, selector);
	}

	protected void _disPatch4WF(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ChangeAuditBillInfo billInfo = (ChangeAuditBillInfo)super._getValue(ctx,pk);
		billInfo.setChangeState(ChangeBillStateEnum.Announce);
		SelectorItemCollection selector = new SelectorItemCollection();
		//TODO �󻪣��ύ״̬ʱ���ύ�·���Ҫ������״̫��Ϊ������
		
		selector.add("changeState");
		_updatePartial(ctx, billInfo, selector);
	}

	protected void _aheadDisPatch4WF(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ChangeAuditBillInfo info = (ChangeAuditBillInfo)super._getValue(ctx,pk);
		if(info.getSuppEntry().size()>0){
    		ChangeBill(ctx,info,FDCBillStateEnum.ANNOUNCE);
    	}
		info.setChangeState(ChangeBillStateEnum.AheadDisPatch);
		//TODO �󻪣��ύ״̬ʱ���ύ�·���Ҫ������״̫��Ϊ������
		
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("changeState");
		_updatePartial(ctx, info, selector);	
	}
	
	
    private SelectorItemCollection getSic(){
		// �˹���Ϊ��ϸ��Ϣ����
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("period.id"));
        sic.add(new SelectorItemInfo("period.beginDate"));	
        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.displayName"));	
        
        return sic;
    }
    
	//�ύʱУ�鵥���ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ
    private void checkBillForSubmit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		
		//�������ڵ�ǰ�ɱ��ڼ�֮ǰ
    	ChangeAuditBillInfo contractBill = (ChangeAuditBillInfo)model;
		
		String comId = null;
		if( contractBill.getCurProject().getFullOrgUnit()==null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("fullOrgUnit.id");
			CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(new ObjectUuidPK(contractBill.getCurProject().getId().toString()),sic);
			
			comId = curProject.getFullOrgUnit().getId().toString();
		}else{
			comId = contractBill.getCurProject().getFullOrgUnit().getId().toString();
		}
		
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,contractBill.getCurProject().getId().toString(),true);
			if(bookedPeriod!=null && contractBill.getPeriod()!=null && contractBill.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
				//�����ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
		}
		
		
		ChangeSupplierEntryCollection c = contractBill.getSuppEntry();
		for(int i=0;i<c.size();i++){			
			ChangeSupplierEntryInfo entry = c.get(i);
			//entry.getContractBill();
			
			//�����ڼ䲻���ں�ͬ�ĵ�ǰ�ڼ�֮ǰ
			if(entry.getContractBill()!=null){
		    	// �˹���Ϊ��ϸ��Ϣ����
		        SelectorItemCollection sic = new SelectorItemCollection();
		        sic.add(new SelectorItemInfo("period.beginDate"));	

		        ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(entry.getContractBill().getId().toString()),sic);
				//�����ڼ䲻���ں�ͬ�ĵ�ǰ�ڼ�֮ǰ
		        //2008-11-26 ���Ӷ�contractBillInfo.getPeriod()�Ƿ�Ϊ�յ��ж�
				if(contractBill.getPeriod()!=null && contractBillInfo.getPeriod()!=null &&
						contractBill.getPeriod().getBeginDate().before(contractBillInfo.getPeriod().getBeginDate())){
					//�����ڼ䲻���ں�ͬ�ĵ�ǰ�ڼ�֮ǰ CNTPERIODBEFORE
					throw new ContractException(ContractException.CNTPERIODBEFORECON);
				}
			}
		}
	}
	
	//���У��
	private void checkBillForAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
    
		ChangeAuditBillInfo model = (ChangeAuditBillInfo)billInfo;

        if(model==null|| model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null){
        	model= this.getChangeAuditBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }

		//��鹦���Ƿ��Ѿ�������ʼ��
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
			
		//�Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();	
			//û�н�����ʼ��
			if(!ProjectPeriodStatusUtil._isClosed(ctx,curProject)){
				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.ISNOT_INIT,new Object[]{model.getCurProject().getDisplayName()});
			}	
			//�ɱ��Ѿ��½�
			PeriodInfo costPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
			
			if (costPeriod == null) {
				// ������Ŀ�½�״̬���ڼ䲻��Ϊ�ա�
				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.PERIOD_CNT_EMPTY, new Object[] { model.getCurProject()
						.getDisplayName() });
			}
			
			if(model.getPeriod().getBeginDate().after(costPeriod.getEndDate())){
				throw new  ContractException(ContractException.AUD_AFTERPERIOD,new Object[]{model.getNumber()});
			}
		}
	}
	
	/**
	 * ������У��
	 * 
	 * @param ctx
	 * @param billId
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {

		checkContractChangeRef(ctx, billId.toString());
		
		ChangeAuditBillInfo model = (ChangeAuditBillInfo)billInfo;

        if(model==null|| model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null){
        	model= this.getChangeAuditBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }
		//��鹦���Ƿ��Ѿ�������ʼ��
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
			
		//�Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();

			//�����ڼ��ڹ�����Ŀ��ǰ�ڼ�֮ǰ�����ܷ����
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
			if(model.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
				throw new  ContractException(ContractException.CNTPERIODBEFORE);
			}	
			
//			if(ProjectPeriodStatusUtil._isEnd(ctx,curProject)){
//				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASEND,new Object[]{model.getCurProject().getDisplayName()});
//			}	
		}
	}
	
	private boolean isGenerateAfterAudit(Context ctx) throws BOSException,
			EASBizException {
		return FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_GENERATEAFTERAUDIT);
	}
	
	/**
	 * ����Ƿ����ò���FDC222_GENERATEAFTERAUDIT��������ã�������ʱҪ����Ƿ�������ָ�
	 * 
	 * @author owen_wen 2011-03-09
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkContractChangeRef(Context ctx, String billId) throws BOSException, EASBizException {
		// R090827-093:�������"ָ��Ƿ�������������������������"���ã���ô�û�Ҫ�������������������Ҫ��ɾ��ָ� by
		// Cassiel_peng 2009-9-24
		boolean isGenerateAfterAuidt = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_GENERATEAFTERAUDIT);
		if (isGenerateAfterAuidt) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("changeAudit.id", billId));
			if (ContractChangeBillFactory.getLocalInstance(ctx).exists(filter)) {
				throw new EASBizException(new NumericExceptionSubItem("001", "���д˲���֮ǰ����ɾ����Ӧ�ı��ָ���"));
			}
		}
	}

	@Override
	protected Object _checkAmount(Context ctx, IObjectPK pk, Map contractMap)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return null;
	}
	protected void checkNumberDup(Context ctx, FDCBillInfo billInfo)throws BOSException, EASBizException{
		if(!isUseNumber())
			return;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", billInfo.getNumber().trim()));
		if(billInfo.getId() != null)
			filter.getFilterItems().add(new FilterItemInfo("id", billInfo.getId().toString(), CompareType.NOTEQUALS));

		if(_exists(ctx, filter))
			throw new ContractException(ContractException.NUMBER_DUP);
		else return;
	}
}