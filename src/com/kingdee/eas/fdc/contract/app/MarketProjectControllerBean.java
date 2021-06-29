package com.kingdee.eas.fdc.contract.app;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
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

import java.lang.String;
import java.math.BigDecimal;
import java.net.URLEncoder;

import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.AttachmentStorageTypeEnum;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWFTypeFactory;
import com.kingdee.eas.fdc.contract.ContractWFTypeInfo;
import com.kingdee.eas.fdc.contract.MarketProjectCollection;
import com.kingdee.eas.fdc.contract.MarketProjectFactory;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.contract.MarketProjectInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class MarketProjectControllerBean extends AbstractMarketProjectControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.MarketProjectControllerBean");
    
    protected void checkNameDup(Context ctx, FDCBillInfo billInfo)throws BOSException, EASBizException{
    	
    	
    }
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		MarketProjectInfo info=this.getMarketProjectInfo(ctx, pk);
		if(info.getSourceFunction()!=null){
    		throw new EASBizException(new NumericExceptionSubItem("100","已存在OA流程，禁止删除！"));
    	}
		super._delete(ctx, pk);
	}

	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		MarketProjectInfo info = (MarketProjectInfo)model;
		super._submit(ctx, pk, model);
		if(model.get("fromweb")==null){
    		sendtoOA(ctx,info);
    	}
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		MarketProjectInfo info = (MarketProjectInfo)model;
		IObjectPK pk=super._submit(ctx, model);
		if(model.get("fromweb")==null){
    		sendtoOA(ctx,info);
    	}
		return pk;
	}
	public void sendtoOA(Context ctx,MarketProjectInfo info) throws EASBizException{
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
				info=this.getMarketProjectInfo(ctx, new ObjectUuidPK(info.getId()));
				
				Service s=new Service();
				Call call=(Call)s.createCall();
				
				call.setTargetEndpointAddress(new java.net.URL(url+"kmReviewWebserviceService?wsdl"));
	            call.addParameter("arg0", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
	            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
	            call.setUseSOAPAction(true);
				call.setTimeout(Integer.valueOf(1000*600000*60));
			        
				JSONObject json=new JSONObject();
				if(info.getSourceFunction()!=null){
					call.setOperationName("updateEkpReview");
					json.put("id", info.getSourceFunction());
					json.put("flowParam", info.getOaOpinion());
				}else{
					call.setOperationName("addEkpReview");
					json.put("id", info.getId().toString());
				}
				builder.clear();
				builder.appendSql("select TID from oatemplate where TYPE='marketproject'");
				IRowSet tidrs = builder.executeQuery();
				String tmplateId=null;
				while(tidrs.next()){
					tmplateId=tidrs.getString("TID");
				}
				json.put("tmplateId", tmplateId);
				json.put("fdType", "07");
				json.put("docSubject", info.getName());

				json.put("loginName", u.getNumber());
				JSONObject obj = new JSONObject();
				//最大费用科目类型 fd_cost_account 、最大费用科目金额 fd_amount、状态 fd_status 放 流程data 參數中
				builder.clear();
				builder.appendSql("select cost.FNAME_L2 as title,en.FAMOUNT as amount from T_FDC_CostAccount cost " +
						"left join T_CON_MARKETPROJECTCOSTENTRY en on cost.fid=en.FCOSTACCOUNTID " +
						"where en.FHEADID='"+info.getId()+"' order by en.FAMOUNT asc ");
				IRowSet costAccounts = builder.executeQuery();
				String fdCostAccount=null;
				BigDecimal fdAmount=BigDecimal.ZERO;
				while(costAccounts.next()){
					fdCostAccount=costAccounts.getString("title");
					fdAmount=costAccounts.getBigDecimal("amount");
				}
				obj.put("fd_38f672bcb4a998", fdCostAccount);
				obj.put("fd_38cf1798043f94", String.valueOf(fdAmount));
				
				if(info.getNw().equals(ContractTypeOrgTypeEnum.NEIBU)){
					obj.put("fd_38cf17bb650026", "内部关联公司往来类");
				}else{
					obj.put("fd_38cf17bb650026", "外部供应商客户往来类");
				}
				
//				web页面查看路径
				builder.clear();
				builder.appendSql("select url from weburl where type='view'");
				IRowSet rsv = builder.executeQuery();
				String sendUrl=null;
				while(rsv.next()){
//					http://172.17.4.125:8082/easWeb/#/
					StringBuffer sbv = new StringBuffer();
					String appendUrl = rsv.getString("url");
//					审批单 approval
					String appendType="initiation/view?from=oa&id=";
//					id
					String idv = info.getId().toString();
					String appendId = URLEncoder.encode(idv, "utf-8");
//					token
					String token = "&token=";
					sendUrl = String.valueOf(sbv.append(appendUrl).append(appendType).append(appendId).append(token));
				}
				json.put("fdPcViewLink", sendUrl);

//				app查看地址
				String sendAppUrl = null;
				StringBuffer sba = new StringBuffer();
				builder.clear();
				builder.appendSql("select url from weburl where type ='app'");
				IRowSet rsapp = builder.executeQuery();
				while(rsapp.next()){
//					http://172.17.4.125:8082/easWeb/#/
					String appendUrl = rsapp.getString("url");
//					审批单 approval
					String appendType="initiation/?from=oa&id=";
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
		                attObj.put("fileType", "07");
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
		        	SelectorItemCollection sic=new SelectorItemCollection();
					sic.add("sourceFunction");
					sic.add("state");
					MarketProjectFactory.getLocalInstance(ctx).updatePartial(info,sic);
					
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
}