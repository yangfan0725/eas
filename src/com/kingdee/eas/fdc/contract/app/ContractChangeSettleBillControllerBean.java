/*jadclipse*/package com.kingdee.eas.fdc.contract.app;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.*;
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
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.*;
import com.kingdee.eas.fdc.contract.*;
import com.kingdee.eas.fdc.contract.programming.*;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;
public class ContractChangeSettleBillControllerBean extends AbstractContractChangeSettleBillControllerBean
{
            public ContractChangeSettleBillControllerBean()
            {
            }
            protected IObjectPK _submit(Context ctx, IObjectValue model)
                throws BOSException, EASBizException
            {



























































/*  87*/        ContractChangeSettleBillInfo info = (ContractChangeSettleBillInfo)model;
/*  88*/        IContractChangeEntry contractFacad = ContractChangeEntryFactory.getLocalInstance(ctx);
/*  89*/        if(info.isIsFinish())
                {/*  90*/            ContractChangeBillInfo conInfo = info.getConChangeBill();
/*  91*/            ContractChangeEntryCollection entrys = conInfo.getEntrys();
/*  92*/            SelectorItemCollection sel = new SelectorItemCollection();
/*  93*/            sel.add("isAllExe");
/*  94*/            for(int i = 0; i < entrys.size(); i++)
                    {/*  95*/                ContractChangeEntryInfo entryInfo = entrys.get(i);
/*  96*/                entryInfo.setIsAllExe(true);
/*  97*/                contractFacad.updatePartial(entryInfo, sel);
                    }
                }
				
/* 101*/        IObjectPK pk= super._submit(ctx,info);

				if(model.get("fromweb")==null){
					sendtoOA(ctx,info);
				}
				
				return pk;
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
            public void deleteOA(Context ctx,ContractChangeSettleBillInfo info) throws EASBizException{
            	if(info.getSourceFunction()!=null){
            		throw new EASBizException(new NumericExceptionSubItem("100","已存在OA流程，禁止删除！"));
            	}
//            	try {
//        			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
//        			builder.appendSql("select furl from t_oa");
//        			IRowSet rs=builder.executeQuery();
//        			String url=null;
//        			while(rs.next()){
//        				url=rs.getString("furl");
//        			}
//        			if(url!=null){
//        				Service s=new Service();
//        				Call call=(Call)s.createCall();
//        				
//        				call.setTargetEndpointAddress(new java.net.URL(url));
//        	            call.addParameter("arg0", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//        	            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
//        	            call.setUseSOAPAction(true);
//        				call.setTimeout(Integer.valueOf(1000*600000*60));
//        		        call.setMaintainSession(true);
//        				call.setOperationName("deleteEkpFlow");
//        		        String result=(String)call.invoke(new Object[]{info.getSourceFunction()} );
//        		        JSONObject rso = JSONObject.fromObject(result);
//        		        if(!rso.getString("code").equals("1")){
//        		        	throw new EASBizException(new NumericExceptionSubItem("100",rso.getString("massage")));
//        		        }
//        			}
//        		} catch (SQLException e) {
//        			e.printStackTrace();
//        		} catch (Exception e) {
//        			throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
//        		}
            }
            
            public void sendtoOA(Context ctx,ContractChangeSettleBillInfo info) throws EASBizException{
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
        				info=this.getContractChangeSettleBillInfo(ctx, new ObjectUuidPK(info.getId()));
        				
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
        					call.setOperationName("updateEkpReview");
        					json.put("id", info.getSourceFunction());
        					json.put("flowParam", info.getOaOpinion());
        				}else{
        					call.setOperationName("addEkpReview");
        					json.put("id", info.getId().toString());
        				}
        				builder.clear();
        				builder.appendSql("select TID from oatemplate where TYPE='changeSettle'");
        				IRowSet tidrs = builder.executeQuery();
        				String tmplateId=null;
        				while(tidrs.next()){
        					tmplateId=tidrs.getString("TID");
        				}
        				json.put("tmplateId", tmplateId);
        				json.put("fdType", "06");
        				json.put("docSubject", info.getName());
        				
        				json.put("loginName", u.getNumber());
        				JSONObject obj = new JSONObject();
        				if(info.getConChangeBill()!=null){
        					ContractChangeBillInfo cb=ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillInfo("select changeType.name from where id='"+info.getConChangeBill().getId()+"'");
        					obj.put("fd_38ee64606ee39c", cb.getChangeType().getName());
        				}
        				obj.put("fd_38ee6461df7954", info.getAllowAmount().doubleValue());
//        				web页面查看路径
        				builder.clear();
        				builder.appendSql("select url from weburl where type='view'");
        				IRowSet rsv = builder.executeQuery();
        				String sendUrl=null;
        				while(rsv.next()){
//        					http://172.17.4.125:8082/easWeb/#/
        					StringBuffer sbv = new StringBuffer();
        					String appendUrl = rsv.getString("url");
//        					确认单 confirm
        					String appendType="confirm/view?from=oa&id=";
//        					id
        					String idv = info.getId().toString();
        					String appendId = URLEncoder.encode(idv, "utf-8");
//        					token
        					String token = "&token=";
        					sendUrl = String.valueOf(sbv.append(appendUrl).append(appendType).append(appendId).append(token));
        					System.out.println(sendUrl);
        				}
        				json.put("fdPcViewLink", sendUrl);
        				
//        				app查看地址
        				String sendAppUrl = null;
        				StringBuffer sba = new StringBuffer();
        				builder.clear();
        				builder.appendSql("select url from weburl where type ='app'");
        				IRowSet rsapp = builder.executeQuery();
        				while(rsapp.next()){
//        					http://172.17.4.125:8082/easWeb/#/
        					String appendUrl = rsapp.getString("url");
//        					审批单 approval
        					String appendType="confirm/?from=oa&id=";
//        					id
        					String idv = info.getId().toString();
        					String appendId = URLEncoder.encode(idv, "utf-8");
//        					token
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
        		                attObj.put("fileType", "06");
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
        					ContractChangeSettleBillFactory.getLocalInstance(ctx).updatePartial(info,sic);
        					
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
            
            protected IObjectPK _save(Context ctx, IObjectValue model)
                throws BOSException, EASBizException
            {

/* 107*/        ContractChangeSettleBillInfo info = (ContractChangeSettleBillInfo)model;
/* 108*/        IContractChangeEntry contractFacad = ContractChangeEntryFactory.getLocalInstance(ctx);
/* 109*/        if(info.isIsFinish())
                {/* 110*/            ContractChangeBillInfo conInfo = info.getConChangeBill();
/* 111*/            ContractChangeEntryCollection entrys = conInfo.getEntrys();
/* 112*/            SelectorItemCollection sel = new SelectorItemCollection();
/* 113*/            sel.add("isAllExe");
/* 114*/            for(int i = 0; i < entrys.size(); i++)
                    {/* 115*/                ContractChangeEntryInfo entryInfo = entrys.get(i);
/* 116*/                entryInfo.setIsAllExe(true);
/* 117*/                contractFacad.updatePartial(entryInfo, sel);
                    }
                }

/* 121*/        return super._save(ctx, model);
            }
            protected void _audit(Context ctx, BOSUuid billId)
                throws BOSException, EASBizException
            {






















/* 148*/        SelectorItemCollection getsel = new SelectorItemCollection();
/* 149*/        getsel.add("*");
/* 150*/        getsel.add("conChangeBill.*");
/* 151*/        getsel.add("orgUnit.id");

/* 153*/        ContractChangeSettleBillInfo bill = getContractChangeSettleBillInfo(ctx, new ObjectUuidPK(billId), getsel);
/* 154*/        ContractChangeBillInfo conChangeBill = bill.getConChangeBill();
/* 155*/        BigDecimal balanceAmt = conChangeBill.getBalanceAmount();
/* 156*/        SelectorItemCollection sel = new SelectorItemCollection();
/* 157*/        conChangeBill.setState(FDCBillStateEnum.VISA);
/* 158*/        BigDecimal allowAmount = bill.getAllowAmount();
/* 159*/        conChangeBill.setOriBalanceAmount(allowAmount);
/* 160*/        BigDecimal exRate = conChangeBill.getExRate();
/* 161*/        conChangeBill.setBalanceAmount(allowAmount.multiply(exRate).setScale(2, 4));
/* 162*/        conChangeBill.setHasSettled(true);
/* 163*/        conChangeBill.setSettleTime(DateTimeUtils.truncateDate(new Date()));
/* 164*/        sel.clear();
/* 165*/        sel.add("balanceAmount");
/* 166*/        sel.add("oriBalanceAmount");
/* 167*/        sel.add("state");
/* 168*/        sel.add("hasSettled");
/* 169*/        sel.add("settleTime");
/* 170*/        ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(conChangeBill, sel);

/* 172*/        String cotractId = bill.getContractBill().getId().toString();
/* 173*/        bill.setLastAmount(FDCUtils.getContractLastAmt(ctx, cotractId));
/* 174*/        sel.clear();
/* 175*/        sel.add("lastAmount");
/* 176*/        ContractChangeSettleBillFactory.getLocalInstance(ctx).updatePartial(bill, sel);

/* 178*/        try
                {
/* <-MISALIGNED-> */ /* 178*/            synContractProgAmt(ctx, balanceAmt, conChangeBill, true, bill.getOrgUnit().getId().toString());
                }
/* <-MISALIGNED-> */ /* 179*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 180*/            e.printStackTrace();
                }
/* <-MISALIGNED-> */ /* 183*/        super._audit(ctx, billId);

			if(bill.getSourceBillId()!=null){
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
					call.setOperationName(new QName(soapaction,"UpdateCompleteStatus"));
					call.addParameter(new QName(soapaction,"FID"),org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN); 
					call.addParameter(new QName(soapaction,"FState"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); 
					call.addParameter(new QName(soapaction,"FAllowAmount"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); 
					call.setUseSOAPAction(true); 
					call.setSOAPActionURI(soapaction + "UpdateCompleteStatus");
					call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
					String r=(String)call.invoke(new Object[]{bill.getSourceBillId(),"6",bill.getAllowAmount().toString()});
					JSONObject obj = JSONObject.fromObject(r);
					if(!"1".equals(obj.getString("State"))){
						throw new EASBizException(new NumericExceptionSubItem("100","同步天联云失败:"+obj.getString("Msg")));
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
            
			protected void _delete(Context ctx, IObjectPK pk)
					throws BOSException, EASBizException {
				ContractChangeSettleBillInfo bill=this.getContractChangeSettleBillInfo(ctx, pk);
				if(bill.getSourceBillId()!=null){
					try {
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		    			builder.appendSql("select furl from t_tlsc");
		    			IRowSet rs=builder.executeQuery();
		    			String url=null;
				        while(rs.next()){
				        	url=rs.getString("furl");
				        }
//						String url="http://172.17.16.30/TLSC.WebService/CostSynergy.asmx";
						String soapaction="http://tempuri.org/";
						Service service=new Service();
						Call call = (Call)service.createCall();
					
						call.setTargetEndpointAddress(url);            
						call.setOperationName(new QName(soapaction,"UpdateCompleteStatus"));
						call.addParameter(new QName(soapaction,"FID"),org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN); 
						call.addParameter(new QName(soapaction,"FState"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); 
						call.addParameter(new QName(soapaction,"FAllowAmount"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); 
						call.setUseSOAPAction(true); 
						call.setSOAPActionURI(soapaction + "UpdateCompleteStatus");
						call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
						String r=(String)call.invoke(new Object[]{bill.getSourceBillId(),"255",bill.getAllowAmount().toString()});
						JSONObject obj = JSONObject.fromObject(r);
						if(!"1".equals(obj.getString("State"))){
							throw new EASBizException(new NumericExceptionSubItem("100","同步天联云失败:"+obj.getString("Msg")));
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
				deleteOA(ctx,bill);
				super._delete(ctx, pk);
			}
			protected void _unAudit(Context ctx, BOSUuid billId)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /* 187*/        FilterInfo filter = new FilterInfo();
/* <-MISALIGNED-> */ /* 188*/        filter.getFilterItems().add(new FilterItemInfo("contractChangeSettleBill.id", billId.toString()));
/* <-MISALIGNED-> */ /* 189*/        if(ContractPCSplitBillFactory.getLocalInstance(ctx).exists(filter))
/* <-MISALIGNED-> */ /* 190*/            throw new EASBizException(new NumericExceptionSubItem("100", "\u5B58\u5728\u5408\u7EA6\u89C4\u5212\u62C6\u5206\uFF0C\u4E0D\u80FD\u8FDB\u884C\u53CD\u5BA1\u6279\u64CD\u4F5C\uFF01"));
/* <-MISALIGNED-> */ /* 192*/        SelectorItemCollection getsel = new SelectorItemCollection();
/* <-MISALIGNED-> */ /* 193*/        getsel.add("*");
/* <-MISALIGNED-> */ /* 194*/        getsel.add("conChangeBill.*");
/* <-MISALIGNED-> */ /* 195*/        getsel.add("orgUnit.id");
/* <-MISALIGNED-> */ /* 197*/        ContractChangeSettleBillInfo bill = getContractChangeSettleBillInfo(ctx, new ObjectUuidPK(billId), getsel);
/* <-MISALIGNED-> */ /* 198*/        ContractChangeBillInfo conChangeBill = bill.getConChangeBill();/* 200*/        SelectorItemCollection sel = new SelectorItemCollection();
/* 201*/        conChangeBill.setState(FDCBillStateEnum.ANNOUNCE);

/* 203*/        conChangeBill.setOriBalanceAmount(FDCHelper.ZERO);
/* 204*/        conChangeBill.setBalanceAmount(FDCHelper.ZERO);
/* 205*/        conChangeBill.setHasSettled(false);
/* 206*/        conChangeBill.setSettleTime(null);
/* 207*/        sel.clear();
/* 208*/        sel.add("balanceAmount");
/* 209*/        sel.add("oriBalanceAmount");
/* 210*/        sel.add("state");
/* 211*/        sel.add("hasSettled");
/* 212*/        sel.add("settleTime");
/* 213*/        ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(conChangeBill, sel);

/* 215*/        String cotractId = bill.getContractBill().getId().toString();
/* 216*/        bill.setLastAmount(FDCUtils.getContractLastAmt(ctx, cotractId));
/* 217*/        sel = new SelectorItemCollection();
/* 218*/        sel.add("lastAmount");
/* 219*/        ContractChangeSettleBillFactory.getLocalInstance(ctx).updatePartial(bill, sel);

/* 221*/        try
                {
/* <-MISALIGNED-> */ /* 221*/            synContractProgAmt(ctx, bill.getAllowAmount(), conChangeBill, false, bill.getOrgUnit().getId().toString());
                }
/* <-MISALIGNED-> */ /* 222*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 223*/            e.printStackTrace();
                }
/* <-MISALIGNED-> */ /* 225*/        super._unAudit(ctx, billId);
            }
            public void synContractProgAmt(Context ctx, BigDecimal oldChangeAmount, ContractChangeBillInfo model, boolean flag, String orgId)
                throws EASBizException, BOSException, SQLException
            {
/* <-MISALIGNED-> */ /* 229*/        BigDecimal newChangeAmount = model.getBalanceAmount();
/* <-MISALIGNED-> */ /* 230*/        SelectorItemCollection sictc = new SelectorItemCollection();
/* <-MISALIGNED-> */ /* 231*/        sictc.add("*");
/* <-MISALIGNED-> */ /* 232*/        sictc.add("amount");
/* <-MISALIGNED-> */ /* 233*/        ContractChangeBillInfo contractChangeBillInfo = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillInfo(new ObjectUuidPK(model.getId()), sictc);
/* <-MISALIGNED-> */ /* 235*/        BigDecimal oldChangeAmt = oldChangeAmount;
/* <-MISALIGNED-> */ /* 236*/        if(oldChangeAmount == null)
/* <-MISALIGNED-> */ /* 237*/            oldChangeAmt = contractChangeBillInfo.getAmount();
/* <-MISALIGNED-> */ /* 239*/        BOSUuid contractBillId = model.getContractBill().getId();
/* <-MISALIGNED-> */ /* 240*/        SelectorItemCollection sic = new SelectorItemCollection();
/* <-MISALIGNED-> */ /* 241*/        sic.add("*");
/* <-MISALIGNED-> */ /* 242*/        sic.add("programmingContract.*");
/* <-MISALIGNED-> */ /* 243*/        IContractBill serviceCon = null;
/* <-MISALIGNED-> */ /* 244*/        IProgrammingContract service = null;
/* <-MISALIGNED-> */ /* 245*/        ContractBillInfo contractBillInfo = null;
/* <-MISALIGNED-> */ /* 246*/        FDCSQLBuilder builder = null;
/* <-MISALIGNED-> */ /* 247*/        serviceCon = ContractBillFactory.getLocalInstance(ctx);
/* <-MISALIGNED-> */ /* 248*/        service = ProgrammingContractFactory.getLocalInstance(ctx);
/* <-MISALIGNED-> */ /* 249*/        builder = new FDCSQLBuilder();
/* <-MISALIGNED-> */ /* 250*/        contractBillInfo = serviceCon.getContractBillInfo(new ObjectUuidPK(contractBillId.toString()), sic);
/* <-MISALIGNED-> */ /* 251*/        ProgrammingContractInfo pcInfo = contractBillInfo.getProgrammingContract();
/* <-MISALIGNED-> */ /* 252*/        if(pcInfo == null)
/* <-MISALIGNED-> */ /* 252*/            return;
/* <-MISALIGNED-> */ /* 254*/        BigDecimal balanceAmt = pcInfo.getBalance();


/* 260*/        BigDecimal changeAmountProg = pcInfo.getChangeAmount();



/* 264*/        SelectorItemCollection sict = new SelectorItemCollection();
/* 265*/        sict.add("balance");
/* 266*/        sict.add("controlBalance");
/* 267*/        sict.add("signUpAmount");
/* 268*/        sict.add("changeAmount");
/* 269*/        sict.add("settleAmount");
/* 270*/        sict.add("srcId");

/* 272*/        if(flag)
                {/* 273*/            pcInfo.setBalance(FDCHelper.add(balanceAmt, FDCHelper.subtract(contractChangeBillInfo.getAmount(), newChangeAmount)));
/* 274*/            pcInfo.setChangeAmount(FDCHelper.add(FDCHelper.subtract(changeAmountProg, oldChangeAmt), newChangeAmount));


/* 277*/            service.updatePartial(pcInfo, sict);

/* 279*/            ContractEstimateChangeBillFactory.getLocalInstance(ctx).sub(pcInfo, ContractEstimateChangeTypeEnum.CHANGE, FDCHelper.subtract(newChangeAmount, contractChangeBillInfo.getAmount()), true, orgId);
                } else
                {














/* 296*/            pcInfo.setBalance(FDCHelper.subtract(balanceAmt, FDCHelper.subtract(contractChangeBillInfo.getAmount(), oldChangeAmt)));
/* 297*/            pcInfo.setChangeAmount(FDCHelper.subtract(FDCHelper.subtract(changeAmountProg, oldChangeAmt), newChangeAmount));


/* 300*/            service.updatePartial(pcInfo, sict);
                }
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
            private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractChangeSettleBillControllerBean");
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\sp\sp-fdc_contract_client.jar
	Total time: 180 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/