package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.String;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.kingdee.eas.base.netctrl.client.FilterItem;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerFactory;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillCollection;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.RoomTransferFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SignChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TranPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
public class SignManageControllerBean extends AbstractSignManageControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.SignManageControllerBean");
    
    
    protected void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	SignManageInfo info=new SignManageInfo();
    	SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("state");
		info.setId(billId);
		info.setBizState(TransactionStateEnum.SIGNAPPLE);
		info.setState(FDCBillStateEnum.SUBMITTED);
		_updatePartial(ctx, info, selector);
	}
    protected void _setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	SignManageInfo info=new SignManageInfo();
    	SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		info.setId(billId);
		info.setBizState(TransactionStateEnum.SIGNAUDITING);
		info.setState(FDCBillStateEnum.AUDITTING);
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime(new Date());
		_updatePartial(ctx, info, selector);
	}
    protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	SignManageInfo info = ((SignManageInfo) model);
		if(info.getBizState() == null)info.setBizState(TransactionStateEnum.SIGNSAVED);
		super._save(ctx, pk, model);
	}
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		SignManageInfo info = ((SignManageInfo) model);
		if(info.getBizState() == null)info.setBizState(TransactionStateEnum.SIGNSAVED);
		return super._save(ctx, model);
	}
	private void checkHasBankPayment(Context ctx, String billId,String warning) throws EASBizException, BOSException{
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("signManager.id", billId));
		if(BankPaymentEntryFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","单据已经产生银行放款单业务，不能进行"+warning+"操作！"));
		}
    }
  
	protected void _invalid(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		
		checkHasBankPayment(ctx,billId.toString(),"作废");
		SHEManageHelper.checkHasSHERevBill(ctx,billId.toString(),"作废");
		
		SignManageInfo info=this.getSignManageInfo(ctx, new ObjectUuidPK(billId));
		SelectorItemCollection selector = new SelectorItemCollection();
		
		selector.add("bizState");
		selector.add("state");
		selector.add("isValid");
		info.setState(FDCBillStateEnum.INVALID);
		info.setBizState(TransactionStateEnum.SIGNNULLIFY);
		info.setIsValid(true);
		_updatePartial(ctx, info, selector);
		
		SHEManageHelper.updatePayActRevAmountAndBizState(ctx, info.getSrcId(), true, TransactionStateEnum.TOSIGN);
		SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),true,RoomSellStateEnum.Sign);
		
		RoomSellStateEnum roomState=SHEManageHelper.toOldTransaction(ctx,info.getTransactionID());
		if(roomState!=null){
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("sellState");
			for(int i=0;i<info.getSignRoomAttachmentEntry().size();i++){
				RoomInfo attRoom=info.getSignRoomAttachmentEntry().get(i).getRoom();
				attRoom.setSellState(roomState);
				RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, sels);
			}
		}
	}
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		ObjectUuidPK pk=new ObjectUuidPK(billId);
		SignManageInfo info=this.getSignManageInfo(ctx, pk);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", info.getSellProject().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE,CompareType.NOTEQUALS));
		
		if(CommissionSettlementBillFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","存在未审批佣金结算审批单，不能进行审批操作！"));
		}
		
		DelayPayBillCollection col = DelayPayBillFactory.getLocalInstance(ctx).getDelayPayBillCollection("select newEntry.*,newEntry.moneyDefine.*,* from where state!='4AUDITTED' and room.id='"+info.getRoom().getId().toString()+"' and sourceFunction not like '%QUIT%'");
		if(col.size()>0){
			throw new EASBizException(new NumericExceptionSubItem("100","延期申请还未审批结束！"));
		}
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		info.setId(billId);
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime(new Date());
		info.setBizState(TransactionStateEnum.SIGNAUDIT);
		info.setState(FDCBillStateEnum.AUDITTED);
		_updatePartial(ctx, info, selector);
		
//		JSONArray arr=new JSONArray();
//		JSONObject jo=new JSONObject();
//		jo.put("id", info.getRoom().getId().toString());
//		jo.put("status", "签约");
//		
//		jo.put("cjdj", "");
//		jo.put("cjzj", "");
//		
//		jo.put("bzj", "");
//		jo.put("bdj", "");
//		
//		arr.add(jo);
//		try {
//			String rs=SHEManageHelper.execPost(ctx, "sap_room_status_channel", arr.toJSONString());
//			JSONObject rso = JSONObject.parseObject(rs);
//			if(!"SUCCESS".equals(rso.getString("status"))){
//				throw new EASBizException(new NumericExceptionSubItem("100",rso.getString("message")));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		toebei
		String	param1="false";
		try {
			param1 = ParamControlFactory.getLocalInstance(ctx).getParamValue(new ObjectUuidPK(info.getOrgUnit().getId()), "YF_YB");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if("true".equals(param1)){
//			toYB
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("*");
			sic.add("sellProject.number");
			sic.add("room.number");
			sic.add("building.*");
			sic.add("building.sellProject.*");
			sic.add("building.sellProject.parent.number");
			sic.add("signCustomerEntry.isMain");
			sic.add("signCustomerEntry.customer.*");
			sic.add("signCustomerEntry.customer.certificateType.name");
			sic.add("room.productType.name");
			sic.add("payType.name");
			sic.add("signSaleManEntry.user.number");
			info=this.getSignManageInfo(ctx, pk,sic);
			
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			String timestamp = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 JSONObject initjson=new JSONObject(); 
			 JSONObject esbjson=new JSONObject();
			 String instId=null;
			 String requestTime=null;
			 JSONObject datajson=new JSONObject();
			 JSONArray ybarr=new JSONArray();
//					房间
					builder.clear();
					builder.appendSql(" select instId,requestTime from esbInfo where source='room'");
					IRowSet rs3=builder.executeQuery();
			 try {
				while(rs3.next()){
					  instId=rs3.getString("instId");
					  requestTime=rs3.getString("requestTime");
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 if(instId!=null){
				 esbjson.put("instId",instId);
			 }
			 if(requestTime!=null){
				 esbjson.put("requestTime",requestTime);
			 }
			 
//				上次返回在时间戳
			 builder.clear();
			 builder.appendSql(" select ybtime from ybTimeRecord where source='room'");
				IRowSet rs1=builder.executeQuery();
				try {
					if(rs1.first()&&rs1!=null){
					 timestamp=rs1.getString("ybtime");
					}else{
						timestamp="";
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
//			客户
			JSONObject initcjson=new JSONObject();
			JSONObject esbcjson=new JSONObject();
			JSONObject datacjson=new JSONObject();
			JSONArray ybcarr=new JSONArray();
			String instcId=null;
			String requestcTime=null;
			String timestampc = null;
			builder.clear();
			builder.appendSql(" select instId,requestTime from esbInfo where source='owner'");
				IRowSet rs31=builder.executeQuery();
				try {
					while(rs31.next()){
						instcId=rs31.getString("instId"); 
				  requestcTime=rs31.getString("requestTime");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(instId!=null){
					esbcjson.put("instId",instId);
				}
				if(requestTime!=null){
					esbcjson.put("requestTime",requestTime);
				}
				
//				上次返回在时间戳
				builder.clear();
				builder.appendSql(" select ybtime from ybTimeRecord where source='owner'");
				IRowSet rs11=builder.executeQuery();
				try {
					if(rs11.first()&&rs11!=null){
						timestampc=rs11.getString("ybtime");
					}else{
					timestampc="";
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
//			toYB
			JSONObject ybjson=new JSONObject();
				if(info.getRoom().getId().toString()!=null){
					String roomId=info.getRoom().getId().toString();
					RoomInfo room1=RoomFactory.getLocalInstance(ctx).getRoomInfo(new ObjectUuidPK(roomId),sic);

					SelectorItemCollection spsic=new SelectorItemCollection();
					spsic.add("parent.*");
					String roomState=room1.getSellState().getName();
					ybjson.put("roomState", "");
					if(roomState!=null){
						if(roomState.equals("Sign")){
							ybjson.put("roomState", "签约");
						}else {
							ybjson.put("roomState", "未售");
						}
					}
					if(room1.getBuilding()!=null){
						if(room1.getBuilding().getSellProject().getParent()!=null){
							ybjson.put("projectId",room1.getBuilding().getSellProject().getParent().getId().toString());
							if(room1.getBuilding().getSellProject().getParent().getName()!=null){
								ybjson.put("projectName",room1.getBuilding().getSellProject().getParent().getName().replaceAll("\\s", ""));
							}else{
								ybjson.put("projectName","");
							}
							if(room1.getBuilding().getSellProject().getId().toString()!=null){
								ybjson.put("stageId",room1.getBuilding().getSellProject().getId().toString());
							}else{
								ybjson.put("stageId","");
							}
							if(room1.getBuilding().getSellProject().getName()!=null){
								ybjson.put("stageName",room1.getBuilding().getSellProject().getName().replaceAll("\\s", ""));
							}else{
								ybjson.put("stageName","");
							}
						}else{					
							ybjson.put("projectId",room1.getBuilding().getSellProject().getParent().getId().toString());
							if(room1.getBuilding().getSellProject().getParent().getName()!=null){
								ybjson.put("projectName",room1.getBuilding().getSellProject().getParent().getName().replaceAll("\\s", ""));
							}else{
								ybjson.put("projectName","");
							}
							if(room1.getBuilding().getSellProject().getId().toString()!=null){
								ybjson.put("stageId",room1.getBuilding().getSellProject().getId().toString());
							}else{
								ybjson.put("stageId","");
							}
							if(room1.getBuilding().getSellProject().getName()!=null){
								ybjson.put("stageName",room1.getBuilding().getSellProject().getName().replaceAll("\\s", ""));
							}else{
								ybjson.put("stageName","");
							}
						}
					}
					
					if(room1.getId()!=null){
						String roomId1 = room1.getId().toString();
						ybjson.put("roomId", roomId1);
						builder.clear();
						builder.appendSql("select FBusAdscriptionDate ,FID,FSaleManNames from T_SHE_SignManage where froomid='"+roomId1+"'");
						IRowSet rsdate=builder.executeQuery();
						try {
							while(rsdate.next()){
								String signDate=rsdate.getString("FBusAdscriptionDate");
								if(signDate!=null){
									ybjson.put("signDate", signDate);
								}else{
									ybjson.put("signDate", "");
								}
////							判断是否全部回款
								ybjson.put("payupState", "0");
								BigDecimal act=BigDecimal.ZERO;
								if(rsdate.getString("FID")!=null){
									String qucId=rsdate.getString("FID");	
									SelectorItemCollection sic1=new SelectorItemCollection();
										sic1.add("signCustomerEntry.customer.*");
										sic1.add("signCustomerEntry.customer.number");
										sic1.add("signCustomerEntry.customer.propertyConsultant.number");
										sic1.add("signCustomerEntry.isMain");
										sic1.add("sellProject.*");
										sic1.add("number");
										sic1.add("srcId");
										sic1.add("signPayListEntry.*");
										SignManageInfo srcInfo=SignManageFactory.getLocalInstance(ctx).getSignManageInfo(new ObjectUuidPK(qucId),sic1);
										String qucId1=srcInfo.getId().toString();
										builder.clear();
										builder.appendSql(" select FID from T_BDC_SHERevBill where FRelateBizBillId='"+qucId1+"' ");
										IRowSet rsquc=builder.executeQuery();
										try {
											while(rsquc.next()){
												if(rsquc.getString("FID")!=null){
													builder.clear();
													builder.appendSql("select FRevAmount from T_BDC_SHERevBillEntry where FPARENTID='"+rsquc.getString("FID")+"' ");
													IRowSet rsq=builder.executeQuery();
													while(rsq.next()){
														if(rsq.getBigDecimal("FRevAmount")!=null){
															 act=act.add(rsq.getBigDecimal("FRevAmount"));
															}
													}
												}
											}
										} catch (SQLException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										SignPayListEntryCollection entrys = srcInfo.getSignPayListEntry();
										BigDecimal app = BigDecimal.ZERO;
										for(int i=0;i<entrys.size();i++){
											if(entrys.get(i).getAppAmount()!=null){
											app  = app.add(entrys.get(i).getAppAmount());
											}
										}
										if(act.compareTo(app)==1||act.compareTo(app)==0){
											ybjson.put("payupState", "1");
										}
										ybjson.put("cst1guid", info.getSignCustomerEntry().get(0).getCustomer().getId().toString());
										if(info.getSignCustomerEntry().get(1)!=null){
												ybjson.put("cst2guid", info.getSignCustomerEntry().get(1).getCustomer().getId().toString());
										}else{
											ybjson.put("cst2guid", "");
										}
										if(info.getSignCustomerEntry().get(2)!=null){
											ybjson.put("cst3guid", info.getSignCustomerEntry().get(2).getCustomer().getId().toString());
										}else{
											ybjson.put("cst3guid", "");
										}
										if(info.getSignCustomerEntry().get(3)!=null){
											ybjson.put("cst4guid", info.getSignCustomerEntry().get(3).getCustomer().getId().toString());
										}else{
											ybjson.put("cst4guid", "");
										}
										if(info.getSignCustomerEntry().get(4)!=null){
											ybjson.put("cst5guid", info.getSignCustomerEntry().get(4).getCustomer().getId().toString());
										}else{
											ybjson.put("cst5guid", "");
										}
										for(int i1=0;i1<info.getSignCustomerEntry().size();i1++){
											SHECustomerInfo quc=info.getSignCustomerEntry().get(i1).getCustomer();
											JSONObject ybcjson=new JSONObject();
												ybcjson.put("cstguid", quc.getId().toString());							
												ybcjson.put("cstname",quc.getName().replaceAll("\\s", ""));	
												if(info.getSignCustomerEntry().get(i1).getCustomer().getFirstDate()==null&&info.getSignCustomerEntry().get(i1).getCustomer().getReportDate()==null){
													throw new EASBizException(new NumericExceptionSubItem("100","客户报备日期和首访日期都为空！"));
												}
												if(quc.getSex()!=null&&!"".equals(String.valueOf(quc.getSex()))){
													ybcjson.put("Gender",quc.getSex().getAlias().replaceAll("\\s", ""));
												}
												
												ybcjson.put("CstType",quc.getCustomerType().getAlias().replaceAll("\\s", ""));
												if(quc.getCertificateType()!=null&&quc.getCertificateType().getName()!=null){
													ybcjson.put("CardType",quc.getCertificateType().getName().toString().replaceAll("\\s", ""));
												}else{
													ybcjson.put("CardType","");
												}
												if(quc.getCertificateNumber()!=null &&quc.getCertificateNumber()!="/"){
													ybcjson.put("CardID",quc.getCertificateNumber().replaceAll("\\s", ""));
												}else{
													ybcjson.put("CardID","");
												}
												if(quc.getEmail()!=null &&quc.getEmail()!="/"){
													ybcjson.put("Email",quc.getEmail().replaceAll("\\s", ""));
												}else{
													ybcjson.put("Email","");
												}
												if(quc.getMailAddress()!=null &&quc.getMailAddress()!="/"){
													ybcjson.put("Address",quc.getMailAddress().replaceAll("\\s", ""));
												}else{
													ybcjson.put("Address","");
												}
												if(quc.getPhone()!=null &&quc.getPhone()!="/"){
													ybcjson.put("Tel",quc.getPhone().replaceAll("\\s", ""));
												}else{
													ybcjson.put("Tel","");
												}
												if(quc.getOfficeTel()!=null &&quc.getOfficeTel()!="/"){
													ybcjson.put("OfficeTel",quc.getOfficeTel().replaceAll("\\s", ""));
												}else{
													ybcjson.put("OfficeTel","");
												}
												if(quc.getTel()!=null &&quc.getTel()!="/"){
													ybcjson.put("HomeTel",quc.getTel().replaceAll("\\s", ""));
												}else{
													ybcjson.put("HomeTel","");
												}
												String createTime=sdf.format(quc.getCreateTime());
												String updateTime=sdf.format(quc.getLastUpdateTime());
												ybcjson.put("CreatedTime",createTime);
												ybcjson.put("ModifiedTime",updateTime);
												String projectId=info.getSellProject().getId().toString();
												if(projectId!=null){
													builder.clear();
													builder.appendSql("select fparentid as pid from t_she_sellproject where fid='"+projectId+"' ");
													IRowSet rspro=builder.executeQuery();
													try {
														while(rspro.next()){
															projectId=rspro.getString("pid");
														}
													} catch (SQLException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													ybcjson.put("projectId",projectId);
												}
												ybcarr.add(ybcjson);
										}			
								}
								ybjson.put("zygw","");
								if(rsdate.getString("FSaleManNames")!=null){
									ybjson.put("zygw", rsdate.getString("FSaleManNames"));
								}	
								
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						ybjson.put("roomId", "");
					}
					ybjson.put("totalPrice", "");
					if(info.getDealTotalAmount()!=null){
						ybjson.put("totalPrice", info.getDealTotalAmount().toString());
					}
					ybjson.put("unitPrice", "");
					if(info.getDealBuildPrice()!=null){
						ybjson.put("unitPrice", info.getDealBuildPrice().toString());
					}

					if(room1.getNumber()!=null){
						ybjson.put("roomCode", room1.getNumber().toString().replaceAll("\\s", ""));
					}else{
						ybjson.put("roomCode", "");
					}
					ybjson.put("buildingId","");
					ybjson.put("buildingName","");
					if(room1.getBuilding()!=null){
						ybjson.put("buildingId", room1.getBuilding().getId().toString());
						if(room1.getBuilding().getName()!=null){
							ybjson.put("buildingName", room1.getBuilding().getName().replaceAll("\\s", ""));
						}
					}
					if(!" ".equals(String.valueOf(room1.getUnit()))){
						ybjson.put("unitName", room1.getUnit());
					}else{
						ybjson.put("unitName","");
					}
					
					
					if(!"".equals(String.valueOf(room1.getFloor()))){
						ybjson.put("floorName", room1.getFloor());
					}else{
						ybjson.put("floorName", "");
					}
					
					if(room1.getDisplayName()!=null){
						ybjson.put("room", room1.getDisplayName().replaceAll("\\s", ""));
					}else{
						ybjson.put("room", "");
					}
					
					if(room1.getName()!=null){
						ybjson.put("roomInfo", room1.getName().replaceAll("\\s", ""));
					}else{
						ybjson.put("roomInfo","");
					}							
						ybjson.put("bldArea", "");
						ybjson.put("tnArea", "");			
					if(room1.getCreateTime()!=null){
						String createTime = sdf.format(room1.getCreateTime());
						ybjson.put("createTime", createTime);
					}else{
						ybjson.put("createTime", "");
					}
					if(room1.getLastUpdateTime()!=null){
						String updateTime = sdf.format(room1.getLastUpdateTime());
						ybjson.put("modifiedTime", updateTime);
					}else{
						ybjson.put("modifiedTime", "");
					}	
					ybarr.add(ybjson);
					
					if(room1.getHandoverRoomDate()!=null){
						ybjson.put("deliveryDate", room1.getHandoverRoomDate().toString());
					}else{
						ybjson.put("deliveryDate", "");
					}
					datajson.put("datas",ybarr);
					datajson.put("timestamp",timestamp);
					initjson.put("esbInfo", esbjson);
					initjson.put("requestInfo",datajson);
					
//					同步房间信息到yiBei
					if(ybarr.size()>0){
						try {
//							System.out.println(initjson.toJSONString());
							String rs111=SHEManageHelper.execPostYB(ctx, initjson.toJSONString(),timestamp);
							JSONObject rso = JSONObject.parseObject(rs111);
							if(!"A0200".equals(rso.getJSONObject("esbInfo").getString("returnCode"))){
								String errorMsg =rso.getJSONObject("esbInfo").getString("returnMsg");
								String info1="来自一碑报错信息：";
								String sb = String.valueOf(new StringBuffer().append(errorMsg).append(info1));
			    				throw new EASBizException(new NumericExceptionSubItem("100",sb));
			    			}else{
								JSONObject rst=rso.getJSONObject("esbInfo");
								 instId=rst.getString("instId");
								 requestTime=rst.getString("requestTime");
								 builder.clear();
								 builder.appendSql(" update esbInfo set instId='"+instId+"',requestTime='"+requestTime+"' where source='room'");
								 builder.executeUpdate();
								 JSONObject rst1=rso.getJSONObject("resultInfo");
								 String ts=rst1.getString("timestamp");
								 builder.clear();
								 builder.appendSql("update ybTimeRecord set ybTime='"+ts+"' where source='room' ");
								 builder.executeUpdate();	
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
//					kehu
//					yb业主客户
					datacjson.put("datas",ybcarr);
					if(timestamp!=null){
						datacjson.put("timestamp",timestampc);
					}else{
						datacjson.put("timestamp","");
					}
					initcjson.put("esbInfo", esbcjson);
					initcjson.put("requestInfo",datacjson);
					
					if(ybcarr.size()>0){
						try {
//							System.out.println(initcjson.toJSONString());
							String rs111=SHEManageHelper.execPostYBowner(ctx, initcjson.toJSONString(),timestamp);
							JSONObject rso = JSONObject.parseObject(rs111);
							if(rso!=null){
							if(!"A0200".equals(rso.getJSONObject("esbInfo").getString("returnCode"))){
								String errorMsg =rso.getJSONObject("esbInfo").getString("returnMsg");
								String info1="来自一碑报错信息：";
								String sb = String.valueOf(new StringBuffer().append(errorMsg).append(info1));
			    				throw new EASBizException(new NumericExceptionSubItem("100",sb));
			    			}else{
								JSONObject rst=rso.getJSONObject("esbInfo");
								 instcId=rst.getString("instId");
								 requestcTime=rst.getString("requestTime");
								 builder.clear();
								 builder.appendSql(" update esbInfo set instId='"+instcId+"',requestTime='"+requestcTime+"' where source='owner'");
								 builder.executeUpdate();
								 JSONObject rst1=rso.getJSONObject("resultInfo");
								 String ts=rst1.getString("timestamp");
								 builder.clear();
								 builder.appendSql("update ybTimeRecord set ybTime='"+ts+"' where source='owner' ");
								 builder.executeUpdate();
//								MsgBox.showInfo("客户同步成功");
							}
						}
						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					
				}
		}
		
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		ObjectUuidPK pk=new ObjectUuidPK(billId);
		SignManageInfo info=this.getSignManageInfo(ctx, pk);
		if(!ContextUtil.getCurrentUserInfo(ctx).getNumber().equals("ppl")){
			if(info.getAreaCompensate()!=null&&info.getAreaCompensate().compareTo(FDCHelper.ZERO)>0){
				throw new EASBizException(new NumericExceptionSubItem("100","签约单已存在面积补差，不能进行反审批操作！"));
			}
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("billId", info.getId()));
			if(DefaultAmountMangerFactory.getLocalInstance(ctx).exists(filter)){
				throw new EASBizException(new NumericExceptionSubItem("100","签约单存在违约金，不能进行反审批操作！"));
			}
		}
		
		
		checkChangeManage(ctx,billId.toString());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		info.setId(billId);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setBizState(TransactionStateEnum.SIGNAPPLE);
		info.setState(FDCBillStateEnum.SUBMITTED);
		_updatePartial(ctx, info, selector);
	}
	private IObjectPK submitAction(Context ctx, IObjectValue model) throws EASBizException, BOSException{
		IObjectPK pk=null;
		SignManageInfo info=(SignManageInfo)model;
		info.setBizState(TransactionStateEnum.SIGNAPPLE);
		RoomInfo room=info.getRoom();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("sellState");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		room.setSellState(RoomSellStateEnum.Sign);
		RoomFactory.getLocalInstance(ctx).updatePartial(room, selector);
		
		for(int i=0;i<info.getSignRoomAttachmentEntry().size();i++){
			RoomInfo attRoom=info.getSignRoomAttachmentEntry().get(i).getRoom();
			attRoom.setSellState(RoomSellStateEnum.Sign);
			RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, selector);
		}
		SHEManageHelper.updatePayActRevAmountAndBizState(ctx,info.getSrcId(),false,TransactionStateEnum.TOSIGN);
		SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),false,RoomSellStateEnum.Sign);
		pk=super._submit(ctx, model);
		SHEManageHelper.updateTransaction(ctx,info,RoomSellStateEnum.Sign,true);
		
		String	param="false";
		try {
			param = ParamControlFactory.getLocalInstance(ctx).getParamValue(new ObjectUuidPK(ContextUtil.getCurrentOrgUnit(ctx).getId()), "YF_WF");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if("true".equals(param)){
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("*");
			sic.add("sellProject.number");
			sic.add("room.number");
			sic.add("signCustomerEntry.isMain");
			sic.add("signCustomerEntry.customer.*");
			sic.add("signCustomerEntry.customer.certificateType.name");
			sic.add("room.productType.name");
			sic.add("payType.name");
			sic.add("signSaleManEntry.user.number");
			info=this.getSignManageInfo(ctx, pk,sic);
			
			JSONArray carr=new JSONArray();
			JSONObject cjo=new JSONObject();

			 
			cjo.put("id", info.getNumber().toString());
			cjo.put("type", "3");
			cjo.put("projectId",info.getSellProject().getNumber());
			String customerId="";
			SHECustomerInfo quc=null;
			Timestamp qudate=null;
			
			String	paramValue="true";
			try {
				paramValue = ParamControlFactory.getLocalInstance(ctx).getParamValue(new ObjectUuidPK(ContextUtil.getCurrentOrgUnit(ctx).getId()), "YF_QD");
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0;i<info.getSignCustomerEntry().size();i++){
				if(i==0){
					customerId=info.getSignCustomerEntry().get(i).getCustomer().getNumber();
				}else{
					customerId=customerId+"@"+info.getSignCustomerEntry().get(i).getCustomer().getNumber();
				}
				if("true".equals(paramValue)){
					if(info.getSignCustomerEntry().get(i).isIsMain()){
						quc=info.getSignCustomerEntry().get(i).getCustomer();
					}
				}else{
					if(info.getSignCustomerEntry().get(i).getCustomer().getFirstDate()==null&&info.getSignCustomerEntry().get(i).getCustomer().getReportDate()==null){
						throw new EASBizException(new NumericExceptionSubItem("100","客户报备日期和首访日期都为空！"));
					}
					if(qudate==null){
						if(info.getSignCustomerEntry().get(i).getCustomer().getReportDate()!=null){
							qudate=info.getSignCustomerEntry().get(i).getCustomer().getReportDate();
						}else{
							qudate=info.getSignCustomerEntry().get(i).getCustomer().getFirstDate();
						}
						quc=info.getSignCustomerEntry().get(i).getCustomer();
					}else{
						Timestamp comdate=null;
						if(info.getSignCustomerEntry().get(i).getCustomer().getReportDate()!=null){
							comdate=info.getSignCustomerEntry().get(i).getCustomer().getReportDate();
						}else{
							comdate=info.getSignCustomerEntry().get(i).getCustomer().getFirstDate();
						}
						if(qudate.after(comdate)){
							qudate=comdate;
							quc=info.getSignCustomerEntry().get(i).getCustomer();
						}
					}
				}
			}
			cjo.put("qdCustId",quc.getNumber());
			cjo.put("cstName",quc.getName());
			cjo.put("cstPhone",quc.getPhone());
			cjo.put("cstCardId",quc.getCertificateNumber());
			if(quc.getCertificateType()!=null)
				cjo.put("cstCardIdType",quc.getCertificateType().getName());
			
			cjo.put("customerId", customerId);
			if(info.getRoom()!=null){
				cjo.put("roomId", info.getRoom().getNumber());
				cjo.put("rcYT", info.getRoom().getProductType().getName());
			}
			cjo.put("roomStatus", "签约");
			cjo.put("userId", info.getSignSaleManEntry().get(0).getUser().getNumber());
			
			
			cjo.put("tradeDate", df.format(info.getBizDate()));
			cjo.put("tradeAmount", info.getDealTotalAmount());
			cjo.put("payType", info.getPayType().getName());
			cjo.put("expectDate", "");
			cjo.put("price", info.getDealBuildPrice());
			cjo.put("area",info.getBulidingArea());
			cjo.put("contractNo", info.getNumber());
			cjo.put("remark", "");
			cjo.put("orderType", "");
			cjo.put("saleType", "");
			
			if(SignChangeStateEnum.CHANGE.equals(info.getChangeState())){
				cjo.put("isChangeRoomNew", "1");
			}else{
				cjo.put("isChangeRoomNew", "0");
			}
			
			sic=new SelectorItemCollection();
			sic.add("number");
			if(info.getSrcId()!=null){
				if(info.getSrcId().getType().equals(new PurchaseManageInfo().getBOSType())){
					PurchaseManageInfo src=PurchaseManageFactory.getLocalInstance(ctx).getPurchaseManageInfo(new ObjectUuidPK(info.getSrcId()),sic);
					cjo.put("rengouId", src.getNumber());
				}else{
					SincerityPurchaseInfo src=SincerityPurchaseFactory.getLocalInstance(ctx).getSincerityPurchaseInfo(new ObjectUuidPK(info.getSrcId()),sic);
					cjo.put("rcId",src.getNumber());
				}
			}
			cjo.put("salesStatisticsDate", df.format(info.getBizDate()));
			cjo.put("fyyt", info.getRoom().getProductType().getName());
			
			carr.add(cjo);
			try {
				String crs=SHEManageHelper.execPost(ctx, "sap_transaction_mcrm_channel", carr.toJSONString());
				JSONObject crso = JSONObject.parseObject(crs);
				if(!"SUCCESS".equals(crso.getString("status"))){
					throw new EASBizException(new NumericExceptionSubItem("100",crso.getString("message")));
				}
				crs=SHEManageHelper.execPost(ctx, "sap_transaction_organ_personal_channel", carr.toJSONString());
				crso = JSONObject.parseObject(crs);
				if(!"SUCCESS".equals(crso.getString("status"))){
					throw new EASBizException(new NumericExceptionSubItem("100",crso.getString("message")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		
//		toebei
		String	param1="false";
		try {
			param1 = ParamControlFactory.getLocalInstance(ctx).getParamValue(new ObjectUuidPK(info.getOrgUnit().getId()), "YF_YB");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if("true".equals(param1)){
//			toYB
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("*");
			sic.add("sellProject.number");
			sic.add("room.number");
			sic.add("building.*");
			sic.add("building.sellProject.*");
			sic.add("building.sellProject.parent.number");
			sic.add("signCustomerEntry.isMain");
			sic.add("signCustomerEntry.customer.*");
			sic.add("signCustomerEntry.customer.certificateType.name");
			sic.add("room.productType.name");
			sic.add("payType.name");
			sic.add("signSaleManEntry.user.number");
			info=this.getSignManageInfo(ctx, pk,sic);
			
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			String timestamp = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date=new Date();
			String sendTime = sdf.format(date).toString();
			String connectId=null;
			String connectName=null;
			 JSONObject initjson=new JSONObject(); 
			 JSONObject esbjson=new JSONObject();
			 String instId=null;
			 String requestTime=null;
			 JSONObject datajson=new JSONObject();
			 JSONArray ybarr=new JSONArray();
//					房间
					builder.clear();
					builder.appendSql(" select instId,requestTime from esbInfo where source='room'");
					IRowSet rs3=builder.executeQuery();
			 try {
				while(rs3.next()){
					  instId=rs3.getString("instId");
					  requestTime=rs3.getString("requestTime");
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 if(instId!=null){
				 esbjson.put("instId",instId);
			 }
			 if(requestTime!=null){
				 esbjson.put("requestTime",requestTime);
			 }
			 
//				上次返回在时间戳
			 builder.clear();
			 builder.appendSql(" select ybtime from ybTimeRecord where source='room'");
				IRowSet rs1=builder.executeQuery();
				try {
					if(rs1.first()&&rs1!=null){
					 timestamp=rs1.getString("ybtime");
					}else{
						timestamp="";
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
//			客户
			JSONObject initcjson=new JSONObject();
			JSONObject esbcjson=new JSONObject();
			JSONObject datacjson=new JSONObject();
			JSONArray ybcarr=new JSONArray();
			String instcId=null;
			String requestcTime=null;
			String timestampc = null;
			builder.clear();
			builder.appendSql(" select instId,requestTime from esbInfo where source='owner'");
				IRowSet rs31=builder.executeQuery();
				try {
					while(rs31.next()){
						instcId=rs31.getString("instId"); 
				  requestcTime=rs31.getString("requestTime");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(instId!=null){
					esbcjson.put("instId",instId);
				}
				if(requestTime!=null){
					esbcjson.put("requestTime",requestTime);
				}
				
//				上次返回在时间戳
				builder.clear();
				builder.appendSql(" select ybtime from ybTimeRecord where source='owner'");
				IRowSet rs11=builder.executeQuery();
				try {
					if(rs11.first()&&rs11!=null){
						timestampc=rs11.getString("ybtime");
					}else{
					timestampc="";
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
//			toYB
			JSONObject ybjson=new JSONObject();
				if(info.getRoom().getId().toString()!=null){
					String roomId=info.getRoom().getId().toString();
					RoomInfo room1=RoomFactory.getLocalInstance(ctx).getRoomInfo(new ObjectUuidPK(roomId),sic);

					SelectorItemCollection spsic=new SelectorItemCollection();
					spsic.add("parent.*");
					String roomState=room1.getSellState().getName();
					ybjson.put("roomState", "");
					if(roomState!=null){
						if(roomState.equals("Sign")){
							ybjson.put("roomState", "签约");
						}else {
							ybjson.put("roomState", "未售");
						}
					}
					if(room1.getBuilding()!=null){
						if(room1.getBuilding().getSellProject().getParent()!=null){
							ybjson.put("projectId",room1.getBuilding().getSellProject().getParent().getId().toString());
							if(room1.getBuilding().getSellProject().getParent().getName()!=null){
								ybjson.put("projectName",room1.getBuilding().getSellProject().getParent().getName().replaceAll("\\s", ""));
							}else{
								ybjson.put("projectName","");
							}
							if(room1.getBuilding().getSellProject().getId().toString()!=null){
								ybjson.put("stageId",room1.getBuilding().getSellProject().getId().toString());
							}else{
								ybjson.put("stageId","");
							}
							if(room1.getBuilding().getSellProject().getName()!=null){
								ybjson.put("stageName",room1.getBuilding().getSellProject().getName().replaceAll("\\s", ""));
							}else{
								ybjson.put("stageName","");
							}
						}else{					
							ybjson.put("projectId",room1.getBuilding().getSellProject().getParent().getId().toString());
							if(room1.getBuilding().getSellProject().getParent().getName()!=null){
								ybjson.put("projectName",room1.getBuilding().getSellProject().getParent().getName().replaceAll("\\s", ""));
							}else{
								ybjson.put("projectName","");
							}
							if(room1.getBuilding().getSellProject().getId().toString()!=null){
								ybjson.put("stageId",room1.getBuilding().getSellProject().getId().toString());
							}else{
								ybjson.put("stageId","");
							}
							if(room1.getBuilding().getSellProject().getName()!=null){
								ybjson.put("stageName",room1.getBuilding().getSellProject().getName().replaceAll("\\s", ""));
							}else{
								ybjson.put("stageName","");
							}
						}
					}
					
					if(room1.getId()!=null){
						String roomId1 = room1.getId().toString();
						connectId=room1.getId().toString();
						ybjson.put("roomId", roomId1);
						builder.clear();
						builder.appendSql("select FBusAdscriptionDate ,FID,FSaleManNames from T_SHE_SignManage where froomid='"+roomId1+"'");
						IRowSet rsdate=builder.executeQuery();
						try {
							while(rsdate.next()){
								String signDate=rsdate.getString("FBusAdscriptionDate");
								if(signDate!=null){
									ybjson.put("signDate", signDate);
								}else{
									ybjson.put("signDate", "");
								}
////							判断是否全部回款
								ybjson.put("payupState", "0");
								BigDecimal act=BigDecimal.ZERO;
								if(rsdate.getString("FID")!=null){
									String qucId=rsdate.getString("FID");	
									SelectorItemCollection sic1=new SelectorItemCollection();
										sic1.add("signCustomerEntry.customer.*");
										sic1.add("signCustomerEntry.customer.number");
										sic1.add("signCustomerEntry.customer.propertyConsultant.number");
										sic1.add("signCustomerEntry.isMain");
										sic1.add("sellProject.*");
										sic1.add("number");
										sic1.add("srcId");
										sic1.add("signPayListEntry.*");
										SignManageInfo srcInfo=SignManageFactory.getLocalInstance(ctx).getSignManageInfo(new ObjectUuidPK(qucId),sic1);
										String qucId1=srcInfo.getId().toString();
										builder.clear();
										builder.appendSql(" select FID from T_BDC_SHERevBill where FRelateBizBillId='"+qucId1+"' ");
										IRowSet rsquc=builder.executeQuery();
										try {
											while(rsquc.next()){
												if(rsquc.getString("FID")!=null){
													builder.clear();
													builder.appendSql("select FRevAmount from T_BDC_SHERevBillEntry where FPARENTID='"+rsquc.getString("FID")+"' ");
													IRowSet rsq=builder.executeQuery();
													while(rsq.next()){
														if(rsq.getBigDecimal("FRevAmount")!=null){
															 act=act.add(rsq.getBigDecimal("FRevAmount"));
															}
													}
												}
											}
										} catch (SQLException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										SignPayListEntryCollection entrys = srcInfo.getSignPayListEntry();
										BigDecimal app = BigDecimal.ZERO;
										for(int i=0;i<entrys.size();i++){
											if(entrys.get(i).getAppAmount()!=null){
											app  = app.add(entrys.get(i).getAppAmount());
											}
										}
										if(act.compareTo(app)==1||act.compareTo(app)==0){
											ybjson.put("payupState", "1");
										}
										ybjson.put("cst1guid", info.getSignCustomerEntry().get(0).getCustomer().getId().toString());
										if(info.getSignCustomerEntry().get(1)!=null){
												ybjson.put("cst2guid", info.getSignCustomerEntry().get(1).getCustomer().getId().toString());
										}else{
											ybjson.put("cst2guid", "");
										}
										if(info.getSignCustomerEntry().get(2)!=null){
											ybjson.put("cst3guid", info.getSignCustomerEntry().get(2).getCustomer().getId().toString());
										}else{
											ybjson.put("cst3guid", "");
										}
										if(info.getSignCustomerEntry().get(3)!=null){
											ybjson.put("cst4guid", info.getSignCustomerEntry().get(3).getCustomer().getId().toString());
										}else{
											ybjson.put("cst4guid", "");
										}
										if(info.getSignCustomerEntry().get(4)!=null){
											ybjson.put("cst5guid", info.getSignCustomerEntry().get(4).getCustomer().getId().toString());
										}else{
											ybjson.put("cst5guid", "");
										}
										for(int i1=0;i1<info.getSignCustomerEntry().size();i1++){
											SHECustomerInfo quc=info.getSignCustomerEntry().get(i1).getCustomer();
											JSONObject ybcjson=new JSONObject();
												ybcjson.put("cstguid", quc.getId().toString());		
												String cstId=quc.getId().toString();
												ybcjson.put("cstname",quc.getName().replaceAll("\\s", ""));	
												String cstName=quc.getName().replaceAll("\\s", "");
												if(info.getSignCustomerEntry().get(i1).getCustomer().getFirstDate()==null&&info.getSignCustomerEntry().get(i1).getCustomer().getReportDate()==null){
													throw new EASBizException(new NumericExceptionSubItem("100","客户报备日期和首访日期都为空！"));
												}
												if(quc.getSex()!=null&&!"".equals(String.valueOf(quc.getSex()))){
													ybcjson.put("Gender",quc.getSex().getAlias().replaceAll("\\s", ""));
												}
												
												ybcjson.put("CstType",quc.getCustomerType().getAlias().replaceAll("\\s", ""));
												if(quc.getCertificateType()!=null&&quc.getCertificateType().getName()!=null){
													ybcjson.put("CardType",quc.getCertificateType().getName().toString().replaceAll("\\s", ""));
												}else{
													ybcjson.put("CardType","");
												}
												if(quc.getCertificateNumber()!=null &&quc.getCertificateNumber()!="/"){
													ybcjson.put("CardID",quc.getCertificateNumber().replaceAll("\\s", ""));
												}else{
													ybcjson.put("CardID","");
												}
												if(quc.getEmail()!=null &&quc.getEmail()!="/"){
													ybcjson.put("Email",quc.getEmail().replaceAll("\\s", ""));
												}else{
													ybcjson.put("Email","");
												}
												if(quc.getMailAddress()!=null &&quc.getMailAddress()!="/"){
													ybcjson.put("Address",quc.getMailAddress().replaceAll("\\s", ""));
												}else{
													ybcjson.put("Address","");
												}
												if(quc.getPhone()!=null &&quc.getPhone()!="/"){
													ybcjson.put("Tel",quc.getPhone().replaceAll("\\s", ""));
												}else{
													ybcjson.put("Tel","");
												}
												if(quc.getOfficeTel()!=null &&quc.getOfficeTel()!="/"){
													ybcjson.put("OfficeTel",quc.getOfficeTel().replaceAll("\\s", ""));
												}else{
													ybcjson.put("OfficeTel","");
												}
												if(quc.getTel()!=null &&quc.getTel()!="/"){
													ybcjson.put("HomeTel",quc.getTel().replaceAll("\\s", ""));
												}else{
													ybcjson.put("HomeTel","");
												}
												String createTime=sdf.format(quc.getCreateTime());
												String updateTime=sdf.format(quc.getLastUpdateTime());
												ybcjson.put("CreatedTime",createTime);
												ybcjson.put("ModifiedTime",updateTime);
												String projectId=info.getSellProject().getId().toString();
												if(projectId!=null){
													builder.clear();
													builder.appendSql("select fparentid as pid from t_she_sellproject where fid='"+projectId+"' ");
													IRowSet rspro=builder.executeQuery();
													try {
														while(rspro.next()){
															projectId=rspro.getString("pid");
														}
													} catch (SQLException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													ybcjson.put("projectId",projectId);
												}
												builder.clear();
												builder.appendSql("/*dialect*/  insert into ebeilog(sendtime,sendData,connectId,connectName,roomInfo) values('"+sendTime+"','"+ybcjson.toString()+"','"+cstId+"','"+cstName+"','"+connectId+"')");
												builder.execute();
												ybcarr.add(ybcjson);
										}			
								}
								ybjson.put("zygw","");
								if(rsdate.getString("FSaleManNames")!=null){
									ybjson.put("zygw", rsdate.getString("FSaleManNames"));
								}	
								
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						ybjson.put("roomId", "");
					}
					ybjson.put("totalPrice", "");
					if(info.getDealTotalAmount()!=null){
						ybjson.put("totalPrice", info.getDealTotalAmount().toString());
					}
					ybjson.put("unitPrice", "");
					if(info.getDealBuildPrice()!=null){
						ybjson.put("unitPrice", info.getDealBuildPrice().toString());
					}

					if(room1.getNumber()!=null){
						ybjson.put("roomCode", room1.getNumber().toString().replaceAll("\\s", ""));
					}else{
						ybjson.put("roomCode", "");
					}
					ybjson.put("buildingId","");
					ybjson.put("buildingName","");
					if(room1.getBuilding()!=null){
						ybjson.put("buildingId", room1.getBuilding().getId().toString());
						if(room1.getBuilding().getName()!=null){
							ybjson.put("buildingName", room1.getBuilding().getName().replaceAll("\\s", ""));
						}
					}
					if(!" ".equals(String.valueOf(room1.getUnit()))){
						ybjson.put("unitName", room1.getUnit());
					}else{
						ybjson.put("unitName","");
					}
					
					
					if(!"".equals(String.valueOf(room1.getFloor()))){
						ybjson.put("floorName", room1.getFloor());
					}else{
						ybjson.put("floorName", "");
					}
					
					if(room1.getDisplayName()!=null){
						ybjson.put("room", room1.getDisplayName().replaceAll("\\s", ""));
					}else{
						ybjson.put("room", "");
					}
					
					if(room1.getName()!=null){
						ybjson.put("roomInfo", room1.getName().replaceAll("\\s", ""));
						connectName=room1.getName().replaceAll("\\s", "");
					}else{
						ybjson.put("roomInfo","");
					}							
						ybjson.put("bldArea", "");
						ybjson.put("tnArea", "");			
					if(room1.getCreateTime()!=null){
						String createTime = sdf.format(room1.getCreateTime());
						ybjson.put("createTime", createTime);
					}else{
						ybjson.put("createTime", "");
					}
					if(room1.getLastUpdateTime()!=null){
						String updateTime = sdf.format(room1.getLastUpdateTime());
						ybjson.put("modifiedTime", updateTime);
					}else{
						ybjson.put("modifiedTime", "");
					}	
					if(room1.getHandoverRoomDate()!=null){
						ybjson.put("deliveryDate", room1.getHandoverRoomDate().toString());
					}else{
						ybjson.put("deliveryDate", "");
					}
					builder.clear();
					builder.appendSql("/*dialect*/  insert into ebeilog(sendtime,sendData,connectId,connectName) values('"+sendTime+"','"+ybjson.toString()+"','"+connectId+"','"+connectName+"')");
					builder.execute();
					ybarr.add(ybjson);
					datajson.put("datas",ybarr);
					datajson.put("timestamp",timestamp);
					initjson.put("esbInfo", esbjson);
					initjson.put("requestInfo",datajson);
					
//					同步房间信息到yiBei
					if(ybarr.size()>0){
						try {
							System.out.println("initjson"+initjson.toJSONString());
							String rs111=SHEManageHelper.execPostYB(ctx, initjson.toJSONString(),timestamp);
							JSONObject rso = JSONObject.parseObject(rs111);
							if(!"A0200".equals(rso.getJSONObject("esbInfo").getString("returnCode"))){
								String errorMsg =rso.getJSONObject("esbInfo").getString("returnMsg");
								String info1="来自一碑报错信息：";
								String sb = String.valueOf(new StringBuffer().append(errorMsg).append(info1));
			    				throw new EASBizException(new NumericExceptionSubItem("100",sb));
			    			}else{
								JSONObject rst=rso.getJSONObject("esbInfo");
								 instId=rst.getString("instId");
								 requestTime=rst.getString("requestTime");
								 builder.clear();
								 builder.appendSql(" update esbInfo set instId='"+instId+"',requestTime='"+requestTime+"' where source='room'");
								 builder.executeUpdate();
								 JSONObject rst1=rso.getJSONObject("resultInfo");
								 String ts=rst1.getString("timestamp");
								 builder.clear();
								 builder.appendSql("update ybTimeRecord set ybTime='"+ts+"' where source='room' ");
								 builder.executeUpdate();	
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
//					kehu
//					yb业主客户
					datacjson.put("datas",ybcarr);
					if(timestamp!=null){
						datacjson.put("timestamp",timestampc);
					}else{
						datacjson.put("timestamp","");
					}
					initcjson.put("esbInfo", esbcjson);
					initcjson.put("requestInfo",datacjson);
					
					if(ybcarr.size()>0){
						try {
							System.out.println("initcjson"+initcjson.toJSONString());
							String rs111=SHEManageHelper.execPostYBowner(ctx, initcjson.toJSONString(),timestamp);
							JSONObject rso = JSONObject.parseObject(rs111);
							if(rso!=null){
							if(!"A0200".equals(rso.getJSONObject("esbInfo").getString("returnCode"))){
								String errorMsg =rso.getJSONObject("esbInfo").getString("returnMsg");
								String info1="来自一碑报错信息：";
								String sb = String.valueOf(new StringBuffer().append(errorMsg).append(info1));
			    				throw new EASBizException(new NumericExceptionSubItem("100",sb));
			    			}else{
								JSONObject rst=rso.getJSONObject("esbInfo");
								 instcId=rst.getString("instId");
								 requestcTime=rst.getString("requestTime");
								 builder.clear();
								 builder.appendSql(" update esbInfo set instId='"+instcId+"',requestTime='"+requestcTime+"' where source='owner'");
								 builder.executeUpdate();
								 JSONObject rst1=rso.getJSONObject("resultInfo");
								 String ts=rst1.getString("timestamp");
								 builder.clear();
								 builder.appendSql("update ybTimeRecord set ybTime='"+ts+"' where source='owner' ");
								 builder.executeUpdate();
//								MsgBox.showInfo("客户同步成功");
							}
						}
						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					
				}
		}
		
		return pk;
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
		return submitAction(ctx,model);
	}
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		submitAction(ctx,model);
		super._submit(ctx, pk, model);
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		SignManageInfo info=(SignManageInfo)getValue(ctx, pk);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sourceBillId", pk.toString()));
		if(RoomTransferFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","房间已结转，不能进行删除操作！"));
		}
		if(TransactionStateEnum.SIGNAPPLE.equals(info.getBizState())||
				TransactionStateEnum.SIGNNULLIFY.equals(info.getBizState())){
			checkHasBankPayment(ctx,pk.toString(),"删除");
			SHEManageHelper.checkHasSHERevBill(ctx,pk.toString(),"删除");
		}
		RoomSellStateEnum roomState=null;
		if(!TransactionStateEnum.SIGNNULLIFY.equals(info.getBizState())&&!TransactionStateEnum.SIGNSAVED.equals(info.getBizState())){
			roomState=SHEManageHelper.toOldTransaction(ctx,info.getTransactionID());
		}
		if(TransactionStateEnum.SIGNAPPLE.equals(info.getBizState())){
			SHEManageHelper.updatePayActRevAmountAndBizState(ctx, info.getSrcId(), true, TransactionStateEnum.TOSIGN);
			SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),true,RoomSellStateEnum.Sign);
			
			if(roomState!=null){
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("sellState");
				for(int i=0;i<info.getSignRoomAttachmentEntry().size();i++){
					RoomInfo attRoom=info.getSignRoomAttachmentEntry().get(i).getRoom();
					attRoom.setSellState(roomState);
					RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, sels);
				}
			}
		}
		super._delete(ctx, pk);
		
	}
	protected void _onRecord(Context ctx, BOSUuid id,Date date,String contractNumber) throws BOSException, EASBizException {
		SignManageInfo info = (SignManageInfo) getValue(ctx, new ObjectUuidPK(id));
		
		info.setIsOnRecord(true);
		info.setOnRecordDate(date);
		info.setBizNumber(contractNumber);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("isOnRecord");
		sels.add("onRecordDate");
		sels.add("bizNumber");
		_updatePartial(ctx, info, sels);
	}

	protected void _unOnRecord(Context ctx, BOSUuid id) throws BOSException, EASBizException {
		SignManageInfo info = (SignManageInfo) getValue(ctx, new ObjectUuidPK(id));
		
		info.setIsOnRecord(false);
		info.setOnRecordDate(null);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("isOnRecord");
		sels.add("onRecordDate");
		
		_updatePartial(ctx, info, sels);
		
	}

}