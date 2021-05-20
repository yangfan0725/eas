package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntry;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntryInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.AfterServiceInfo;
import com.kingdee.eas.fdc.sellhouse.AgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeManageCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeManageInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.DealStateEnum;
import com.kingdee.eas.fdc.sellhouse.IBaseTransaction;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ProjectSet;
import com.kingdee.eas.fdc.sellhouse.PurAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinCollection;
import com.kingdee.eas.fdc.sellhouse.RoomJoinFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SignAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranLinkEnum;
import com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranStateHisCollection;
import com.kingdee.eas.fdc.sellhouse.TranStateHisFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
public class ChangeManageControllerBean extends AbstractChangeManageControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.ChangeManageControllerBean");
    
    protected void checkNameDup(Context ctx, FDCBillInfo billInfo) throws BOSException, EASBizException {
     
	}
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		ChangeManageInfo info=getChangeManageInfo(ctx, new ObjectUuidPK(billId));
		if(info.getSrcId()!=null){
			ObjectUuidPK pk=new ObjectUuidPK(info.getSrcId());
			IObjectValue objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(pk.getObjectType(),pk);
			
			if(ChangeBizTypeEnum.QUITROOM.equals(info.getBizType())||ChangeBizTypeEnum.CHANGEROOM.equals(info.getBizType())){
				SHEManageHelper.updateRoomState(ctx,((BaseTransactionInfo)objectValue).getRoom(),RoomSellStateEnum.OnShow);
				SHEManageHelper.validTransaction(ctx,info.getTransactionID(),true);
				this.setOtherBillState(ctx, info, AFMortgagedStateEnum.CANCELROOM,true);
			}
			setBizState(ctx,objectValue,info.getBizType(),FDCBillStateEnum.AUDITTED,false);
			
			if(info.getNewId()!=null){
				ObjectUuidPK newpk=new ObjectUuidPK(info.getNewId());
				IObjectValue newobjectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(newpk.getObjectType(),newpk);
				
				if(ChangeBizTypeEnum.PRICECHANGE.equals(info.getBizType())){
					SHEManageHelper.updateTransaction(ctx, (BaseTransactionInfo)newobjectValue, this.getRoomSellState(newobjectValue),true);
				}
				if(ChangeBizTypeEnum.CHANGENAME.equals(info.getBizType())){
					SHEManageHelper.updateTransaction(ctx, (BaseTransactionInfo)newobjectValue, this.getRoomSellState(newobjectValue),false);
				
//					boolean isClearActRevAmount=isExistSameCustomer(objectValue,info);
//					if(isClearActRevAmount){
//						ObjectUuidPK tranpk=new ObjectUuidPK(info.getTransactionID());
//						TransactionInfo	tranInfo=TransactionFactory.getLocalInstance(ctx).getTransactionInfo(tranpk);
//						SHERevBillFactory.getLocalInstance(ctx).whenTransCustChange(tranInfo);
//					}
					FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
					fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
					
					StringBuffer sql = new StringBuffer();
					sql.append("update T_BDC_SHERevBill set frelateBizBillId='"+info.getNewId()+"' where frelateBizBillId='"+info.getSrcId()+"'");
					fdcSB.addBatch(sql.toString());
					
					sql = new StringBuffer();
					sql.append("update T_BDC_SHERevBill set frelateBizBillNumber='"+((BaseTransactionInfo)newobjectValue).getNumber()+"' where frelateBizBillId='"+info.getNewId()+"'");
					fdcSB.addBatch(sql.toString());
					
					String customerIds="";
					if(info.getCustomerEntry().size()==1){
						customerIds=info.getCustomerEntry().get(0).getCustomer().getId().toString();
					}else{
						for(int i=0;i<info.getCustomerEntry().size();i++){
							if(i==info.getCustomerEntry().size()-1){
								customerIds=customerIds+info.getCustomerEntry().get(i).getCustomer().getId().toString();
							}else{
								customerIds=customerIds+info.getCustomerEntry().get(i).getCustomer().getId().toString()+",";
							}
						}
					}
					sql = new StringBuffer();
					sql.append("update T_BDC_SHERevBill set fcustomerids='"+customerIds+"' where frelateTransId='"+info.getTransactionID()+"'");
					fdcSB.addBatch(sql.toString());
					
					sql = new StringBuffer();
					sql.append("update T_BDC_SHERevBill set fcustomernames='"+info.getCustomerNames()+"' where frelateTransId='"+info.getTransactionID()+"'");
					fdcSB.addBatch(sql.toString());
					
					fdcSB.executeBatch();
				}
				setBizState(ctx,newobjectValue,info.getBizType(),FDCBillStateEnum.AUDITTED,true);
			}
		}
		genPayBill(ctx,info);
		
		ChangeManageInfo billInfo = new ChangeManageInfo();
		
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		billInfo.setAuditTime(new Date());
		billInfo.setSheRevBill(info.getSheRevBill());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("sheRevBill");
		
		if(ChangeBizTypeEnum.CHANGENAME.equals(info.getBizType())){
			selector.add("dealState");
			info.setDealState(DealStateEnum.DEAL);
		}
		_updatePartial(ctx, billInfo, selector);
		
//		if(ChangeBizTypeEnum.QUITROOM.equals(info.getBizType())){
//			JSONArray arr=new JSONArray();
//			JSONObject jo=new JSONObject();
//			jo.put("id", info.getRoom().getId().toString());
//			jo.put("status", "待售");
//			
//			jo.put("cjdj", "");
//			jo.put("cjzj", "");
//			
//			jo.put("bzj", "");
//			jo.put("bdj", "");
//			
//			arr.add(jo);
//			try {
//				String rs=SHEManageHelper.execPost(ctx, "sap_room_status_channel", arr.toJSONString());
//				JSONObject rso = JSONObject.parseObject(rs);
//				if(!"SUCCESS".equals(rso.getString("status"))){
//					throw new EASBizException(new NumericExceptionSubItem("100",rso.getString("message")));
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("sellProject.number");
		sic.add("srcRoom.*");
		sic.add("room.*");
		sic.add("customerEntry.customer.*");
		sic.add("customerEntry.*");
		sic.add("changeReason.name");
		sic.add("orgUnit.id");
		info=this.getChangeManageInfo(ctx, new ObjectUuidPK(billId),sic);
	
		
		String	param="false";
		try {
			param = ParamControlFactory.getLocalInstance(ctx).getParamValue(new ObjectUuidPK(info.getOrgUnit().getId()), "YF_WF");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if("true".equals(param)){
			JSONArray carr=new JSONArray();
			JSONObject cjo=new JSONObject();
			
			SHECustomerInfo oldquc=null;
			SHECustomerInfo newquc=null;
			String oldcustomerId="";
			String newcustomerId="";
			Timestamp qudate=null;
			String	paramValue="true";
			try {
				paramValue = ParamControlFactory.getLocalInstance(ctx).getParamValue(new ObjectUuidPK(info.getOrgUnit().getId()), "YF_QD");
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(info.getSrcId().getType().equals(new PurchaseManageInfo().getBOSType())){
				cjo.put("type", "1");
				
				sic=new SelectorItemCollection();
				sic.add("purCustomerEntry.customer.*");
				sic.add("purCustomerEntry.customer.number");
				sic.add("purCustomerEntry.customer.propertyConsultant.number");
				sic.add("purCustomerEntry.isMain");
				sic.add("number");
				
				PurchaseManageInfo srcInfo=PurchaseManageFactory.getLocalInstance(ctx).getPurchaseManageInfo(new ObjectUuidPK(info.getSrcId()),sic);
				for(int i=0;i<srcInfo.getPurCustomerEntry().size();i++){
					if(i==0){
						oldcustomerId=srcInfo.getPurCustomerEntry().get(i).getCustomer().getNumber();
					}else{
						oldcustomerId=oldcustomerId+"@"+srcInfo.getPurCustomerEntry().get(i).getCustomer().getNumber();
					}
					if("true".equals(paramValue)){
						if(srcInfo.getPurCustomerEntry().get(i).isIsMain()){
							oldquc=srcInfo.getPurCustomerEntry().get(i).getCustomer();
						}
					}else{
						if(srcInfo.getPurCustomerEntry().get(i).getCustomer().getFirstDate()==null&&srcInfo.getPurCustomerEntry().get(i).getCustomer().getReportDate()==null){
							throw new EASBizException(new NumericExceptionSubItem("100","客户报备日期和首访日期都为空！"));
						}
						if(qudate==null){
							if(srcInfo.getPurCustomerEntry().get(i).getCustomer().getReportDate()!=null){
								qudate=srcInfo.getPurCustomerEntry().get(i).getCustomer().getReportDate();
							}else{
								qudate=srcInfo.getPurCustomerEntry().get(i).getCustomer().getFirstDate();
							}
							oldquc=srcInfo.getPurCustomerEntry().get(i).getCustomer();
						}else{
							Timestamp comdate=null;
							if(srcInfo.getPurCustomerEntry().get(i).getCustomer().getReportDate()!=null){
								comdate=srcInfo.getPurCustomerEntry().get(i).getCustomer().getReportDate();
							}else{
								comdate=srcInfo.getPurCustomerEntry().get(i).getCustomer().getFirstDate();
							}
							if(qudate.after(comdate)){
								qudate=comdate;
								oldquc=srcInfo.getPurCustomerEntry().get(i).getCustomer();
							}
						}
					}
				}
				
				sic=new SelectorItemCollection();
				sic.add("purCustomerEntry.customer.*");
				sic.add("purCustomerEntry.customer.number");
				sic.add("purCustomerEntry.customer.propertyConsultant.number");
				sic.add("purCustomerEntry.isMain");
				sic.add("number");
				if(!info.getBizType().equals(ChangeBizTypeEnum.QUITROOM)){
					qudate=null;
					PurchaseManageInfo newInfo=PurchaseManageFactory.getLocalInstance(ctx).getPurchaseManageInfo(new ObjectUuidPK(info.getNewId()),sic);
					for(int i=0;i<newInfo.getPurCustomerEntry().size();i++){
						if(i==0){
							newcustomerId=newInfo.getPurCustomerEntry().get(i).getCustomer().getNumber();
						}else{
							newcustomerId=newcustomerId+"@"+newInfo.getPurCustomerEntry().get(i).getCustomer().getNumber();
						}
						if("true".equals(paramValue)){
							if(newInfo.getPurCustomerEntry().get(i).isIsMain()){
								newquc=newInfo.getPurCustomerEntry().get(i).getCustomer();
							}
						}else{
							if(newInfo.getPurCustomerEntry().get(i).getCustomer().getFirstDate()==null&&newInfo.getPurCustomerEntry().get(i).getCustomer().getReportDate()==null){
								throw new EASBizException(new NumericExceptionSubItem("100","客户报备日期和首访日期都为空！"));
							}
							if(qudate==null){
								if(newInfo.getPurCustomerEntry().get(i).getCustomer().getReportDate()!=null){
									qudate=newInfo.getPurCustomerEntry().get(i).getCustomer().getReportDate();
								}else{
									qudate=newInfo.getPurCustomerEntry().get(i).getCustomer().getFirstDate();
								}
								newquc=newInfo.getPurCustomerEntry().get(i).getCustomer();
							}else{
								Timestamp comdate=null;
								if(newInfo.getPurCustomerEntry().get(i).getCustomer().getReportDate()!=null){
									comdate=newInfo.getPurCustomerEntry().get(i).getCustomer().getReportDate();
								}else{
									comdate=newInfo.getPurCustomerEntry().get(i).getCustomer().getFirstDate();
								}
								if(qudate.after(comdate)){
									qudate=comdate;
									newquc=newInfo.getPurCustomerEntry().get(i).getCustomer();
								}
							}
						}
					}
				}
				
				if(info.getBizType().equals(ChangeBizTypeEnum.QUITROOM)){
					cjo.put("qdCustId",oldquc.getNumber());
					cjo.put("qdZygwId",oldquc.getPropertyConsultant().getNumber());
				}else{
					cjo.put("qdCustId",newquc.getNumber());
					cjo.put("qdZygwId",newquc.getPropertyConsultant().getNumber());
				}
				cjo.put("oldCustomerId", oldcustomerId);
				cjo.put("id", srcInfo.getNumber());
				
			}else{
				cjo.put("type", "2");
				
				sic=new SelectorItemCollection();
				sic.add("signCustomerEntry.customer.*");
				sic.add("signCustomerEntry.customer.number");
				sic.add("signCustomerEntry.customer.propertyConsultant.number");
				sic.add("signCustomerEntry.isMain");
				sic.add("number");
				sic.add("srcId");
				SignManageInfo srcInfo=SignManageFactory.getLocalInstance(ctx).getSignManageInfo(new ObjectUuidPK(info.getSrcId()),sic);
				for(int i=0;i<srcInfo.getSignCustomerEntry().size();i++){
					if(i==0){
						oldcustomerId=srcInfo.getSignCustomerEntry().get(i).getCustomer().getNumber();
					}else{
						oldcustomerId=oldcustomerId+"@"+srcInfo.getSignCustomerEntry().get(i).getCustomer().getNumber();
					}
					
					if("true".equals(paramValue)){
						if(srcInfo.getSignCustomerEntry().get(i).isIsMain()){
							oldquc=srcInfo.getSignCustomerEntry().get(i).getCustomer();
						}
					}else{
						if(srcInfo.getSignCustomerEntry().get(i).getCustomer().getFirstDate()==null&&srcInfo.getSignCustomerEntry().get(i).getCustomer().getReportDate()==null){
							throw new EASBizException(new NumericExceptionSubItem("100","客户报备日期和首访日期都为空！"));
						}
						if(qudate==null){
							if(srcInfo.getSignCustomerEntry().get(i).getCustomer().getReportDate()!=null){
								qudate=srcInfo.getSignCustomerEntry().get(i).getCustomer().getReportDate();
							}else{
								qudate=srcInfo.getSignCustomerEntry().get(i).getCustomer().getFirstDate();
							}
							oldquc=srcInfo.getSignCustomerEntry().get(i).getCustomer();
						}else{
							Timestamp comdate=null;
							if(srcInfo.getSignCustomerEntry().get(i).getCustomer().getReportDate()!=null){
								comdate=srcInfo.getSignCustomerEntry().get(i).getCustomer().getReportDate();
							}else{
								comdate=srcInfo.getSignCustomerEntry().get(i).getCustomer().getFirstDate();
							}
							if(qudate.after(comdate)){
								qudate=comdate;
								oldquc=srcInfo.getSignCustomerEntry().get(i).getCustomer();
							}
						}
					}
				}
				if(!info.getBizType().equals(ChangeBizTypeEnum.QUITROOM)){
					qudate=null;
					sic=new SelectorItemCollection();
					sic.add("signCustomerEntry.customer.*");
					sic.add("signCustomerEntry.customer.number");
					sic.add("signCustomerEntry.customer.propertyConsultant.number");
					sic.add("signCustomerEntry.isMain");
					sic.add("number");
					sic.add("srcId");
					SignManageInfo newInfo=SignManageFactory.getLocalInstance(ctx).getSignManageInfo(new ObjectUuidPK(info.getNewId()),sic);
					for(int i=0;i<newInfo.getSignCustomerEntry().size();i++){
						if(i==0){
							newcustomerId=newInfo.getSignCustomerEntry().get(i).getCustomer().getNumber();
						}else{
							newcustomerId=newcustomerId+"@"+newInfo.getSignCustomerEntry().get(i).getCustomer().getNumber();
						}
						if("true".equals(paramValue)){
							if(newInfo.getSignCustomerEntry().get(i).isIsMain()){
								newquc=newInfo.getSignCustomerEntry().get(i).getCustomer();
							}
						}else{
							if(newInfo.getSignCustomerEntry().get(i).getCustomer().getFirstDate()==null&&newInfo.getSignCustomerEntry().get(i).getCustomer().getReportDate()==null){
								throw new EASBizException(new NumericExceptionSubItem("100","客户报备日期和首访日期都为空！"));
							}
							if(qudate==null){
								if(newInfo.getSignCustomerEntry().get(i).getCustomer().getReportDate()!=null){
									qudate=newInfo.getSignCustomerEntry().get(i).getCustomer().getReportDate();
								}else{
									qudate=newInfo.getSignCustomerEntry().get(i).getCustomer().getFirstDate();
								}
								newquc=newInfo.getSignCustomerEntry().get(i).getCustomer();
							}else{
								Timestamp comdate=null;
								if(newInfo.getSignCustomerEntry().get(i).getCustomer().getReportDate()!=null){
									comdate=newInfo.getSignCustomerEntry().get(i).getCustomer().getReportDate();
								}else{
									comdate=newInfo.getSignCustomerEntry().get(i).getCustomer().getFirstDate();
								}
								if(qudate.after(comdate)){
									qudate=comdate;
									newquc=newInfo.getSignCustomerEntry().get(i).getCustomer();
								}
							}
						}
					}
				}
				
				if(info.getBizType().equals(ChangeBizTypeEnum.QUITROOM)){
					cjo.put("qdCustId",oldquc.getNumber());
					cjo.put("qdZygwId",oldquc.getPropertyConsultant().getNumber());
				}else{
					cjo.put("qdCustId",newquc.getNumber());
					cjo.put("qdZygwId",newquc.getPropertyConsultant().getNumber());
				}
				
				cjo.put("oldCustomerId", oldcustomerId);
				
				cjo.put("id", srcInfo.getNumber());
				
				sic=new SelectorItemCollection();
				sic.add("number");
				if(srcInfo.getSrcId()!=null&&srcInfo.getSrcId().getType().equals(new PurchaseManageInfo().getBOSType())){
					PurchaseManageInfo src=PurchaseManageFactory.getLocalInstance(ctx).getPurchaseManageInfo(new ObjectUuidPK(srcInfo.getSrcId()),sic);
					cjo.put("rengouId", src.getNumber());
				}
			}
			if(info.getBizType().equals(ChangeBizTypeEnum.QUITROOM)){
				cjo.put("flag", "1");
				if(info.getChangeType()!=null){
					if(info.getChangeType().equals(ChangeTypeEnum.HFTF)){
						cjo.put("isChangQuid", "1");
					}else{
						cjo.put("isChangQuid", "0");
					}
				}else{
					if(info.getChangeReason().getName().indexOf("换房")>=0){
						cjo.put("isChangQuid", "1");
					}else{
						cjo.put("isChangQuid", "0");
					}
				}
				cjo.put("customerId", oldcustomerId);
			}else{
				cjo.put("flag", "3");
				
				cjo.put("customerId", newcustomerId);
			}
			
			cjo.put("customerStatus", "");
			cjo.put("roomId", info.getSrcRoom().getNumber());
			cjo.put("roomStatus", info.getSrcRoom().getSellState().getAlias());
			cjo.put("projectId",info.getSellProject().getNumber().toString());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cjo.put("changeDate", df.format(info.getChangeDate()));
			
			carr.add(cjo);
			try {
				String crs=SHEManageHelper.execPost(ctx, "sap_change_mcrm_channel", carr.toJSONString());
				JSONObject crso = JSONObject.parseObject(crs);
				if(!"SUCCESS".equals(crso.getString("status"))){
					throw new EASBizException(new NumericExceptionSubItem("100",crso.getString("message")));
				}
				crs=SHEManageHelper.execPost(ctx, "sap_change_personal_channel", carr.toJSONString());
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
		
//		
//		同步ebei
//		
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
			
				 if(!info.getSrcId().getType().equals(new PurchaseManageInfo().getBOSType())){
					 
						RoomInfo room = RoomFactory.getLocalInstance(ctx).getRoomInfo(new ObjectUuidPK(info.getSrcRoom().getId()));
//						toYB
						JSONObject ybjson=new JSONObject();
						 Date date = new Date(System.currentTimeMillis());
							String time=sdf.format(date);
						
						if(info.getSrcRoom()!=null){
							
							if(info.getSrcRoom().getId().toString()!=null){
								if(info.getBizType().equals(ChangeBizTypeEnum.QUITROOM)){
									ybjson.put("changeInfo", "'"+time+"'，退房");
									ybjson.put("roomId", info.getSrcRoom().getId().toString());
									ybjson.put("modifiedTime", room.getLastUpdateTime().toString());
								}
								
								if(!info.getBizType().equals(ChangeBizTypeEnum.QUITROOM)){
									 sic=new SelectorItemCollection();
										sic.add("signCustomerEntry.customer.*");
										sic.add("signCustomerEntry.customer.number");
										sic.add("signCustomerEntry.customer.propertyConsultant.number");
										sic.add("signCustomerEntry.isMain");
										sic.add("sellProject.*");
										sic.add("number");
										sic.add("srcId");
										sic.add("signPayListEntry.*");
										SignManageInfo srcInfo=SignManageFactory.getLocalInstance(ctx).getSignManageInfo(new ObjectUuidPK(info.getNewId()),sic);
									ybjson.put("changeInfo", "'"+time+"'，更名");
								String roomState=room.getSellState().getName();
								ybjson.put("roomState", "");
								if(roomState!=null){
									if(roomState.equals("Sign")){
										ybjson.put("roomState", "签约");
									}else {
										ybjson.put("roomState", "未售");
									}
								}
								if(room.getBuilding()!=null&&room.getBuilding().getSellProject()!=null){
								if(room.getBuilding().getSellProject().getParent()!=null){
									ybjson.put("projectId",room.getBuilding().getSellProject().getParent().getId().toString());
									if(room.getBuilding().getSellProject().getParent().getName()!=null){
										ybjson.put("projectName",room.getBuilding().getSellProject().getParent().getName().replaceAll("\\s", ""));
									}else{
										ybjson.put("projectName","");
									}
									if(room.getBuilding().getSellProject().getId().toString()!=null){
										ybjson.put("stageId",room.getBuilding().getSellProject().getId().toString());
									}else{
										ybjson.put("stageId","");
									}
									if(room.getBuilding().getSellProject().getName()!=null){
										ybjson.put("stageName",room.getBuilding().getSellProject().getName().replaceAll("\\s", ""));
									}else{
										ybjson.put("stageName","");
									}
								}else{					
									ybjson.put("projectId",room.getBuilding().getSellProject().getParent().getId().toString());
									if(room.getBuilding().getSellProject().getParent().getName()!=null){
										ybjson.put("projectName",room.getBuilding().getSellProject().getParent().getName().replaceAll("\\s", ""));
									}else{
										ybjson.put("projectName","");
									}
									if(room.getBuilding().getSellProject().getId().toString()!=null){
										ybjson.put("stageId",room.getBuilding().getSellProject().getId().toString());
									}else{
										ybjson.put("stageId","");
									}
									if(room.getBuilding().getSellProject().getName()!=null){
										ybjson.put("stageName",room.getBuilding().getSellProject().getName().replaceAll("\\s", ""));
									}else{
										ybjson.put("stageName","");
									}
								}
								}
								if(room.getId()!=null){
									String roomId1 = room.getId().toString();
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
////										判断是否全部回款
											ybjson.put("payupState", "0");
											BigDecimal act=BigDecimal.ZERO;
											if(rsdate.getString("FID")!=null){
												String qucId=rsdate.getString("FID");	
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
																	if(rsq.getString("FRevAmount")!=null){
																		 act=rsq.getBigDecimal("FRevAmount");
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
													ybjson.put("cst1guid", srcInfo.getSignCustomerEntry().get(0).getCustomer().getId().toString());
													if(srcInfo.getSignCustomerEntry().get(1)!=null){
															ybjson.put("cst2guid", srcInfo.getSignCustomerEntry().get(1).getCustomer().getId().toString());
													}else{
														ybjson.put("cst2guid", "");
													}
													if(srcInfo.getSignCustomerEntry().get(2)!=null){
														ybjson.put("cst3guid", srcInfo.getSignCustomerEntry().get(2).getCustomer().getId().toString());
													}else{
														ybjson.put("cst3guid", "");
													}
													if(srcInfo.getSignCustomerEntry().get(3)!=null){
														ybjson.put("cst4guid", srcInfo.getSignCustomerEntry().get(3).getCustomer().getId().toString());
													}else{
														ybjson.put("cst4guid", "");
													}
													if(srcInfo.getSignCustomerEntry().get(4)!=null){
														ybjson.put("cst5guid", srcInfo.getSignCustomerEntry().get(4).getCustomer().getId().toString());
													}else{
														ybjson.put("cst5guid", "");
													}
													for(int i1=0;i1<srcInfo.getSignCustomerEntry().size();i1++){
														SHECustomerInfo quc=srcInfo.getSignCustomerEntry().get(i1).getCustomer();
														JSONObject ybcjson=new JSONObject();
															ybcjson.put("cstguid", quc.getId().toString());							
															ybcjson.put("cstname",quc.getName().replaceAll("\\s", ""));	
															if(srcInfo.getSignCustomerEntry().get(i1).getCustomer().getFirstDate()==null&&srcInfo.getSignCustomerEntry().get(i1).getCustomer().getReportDate()==null){
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
															String projectId=srcInfo.getSellProject().getId().toString();
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
								if(srcInfo.getDealTotalAmount()!=null){
									ybjson.put("totalPrice", srcInfo.getDealTotalAmount().toString());
								}
								ybjson.put("unitPrice", "");
								if(srcInfo.getDealBuildPrice()!=null){
									ybjson.put("unitPrice", srcInfo.getDealBuildPrice().toString());
								}
								if(room.getNumber()!=null){
									ybjson.put("roomCode", room.getNumber().toString().replaceAll("\\s", ""));
								}else{
									ybjson.put("roomCode", "");
								}
								
								if(room.getBuilding()!=null){
									ybjson.put("buildingId", room.getBuilding().getId().toString());
								}else{
									ybjson.put("buildingId","");
								}
								
								if(room.getBuilding().getName()!=null){
									ybjson.put("buildingName", room.getBuilding().getName().replaceAll("\\s", ""));
								}else{
									ybjson.put("buildingName","");
								}
								
								if(!" ".equals(String.valueOf(room.getUnit()))){
									ybjson.put("unitName", room.getUnit());
								}else{
									ybjson.put("unitName","");
								}
								
								
								if(!"".equals(String.valueOf(room.getFloor()))){
									ybjson.put("floorName", room.getFloor());
								}else{
									ybjson.put("floorName", "");
								}
								
								if(room.getDisplayName()!=null){
									ybjson.put("room", room.getDisplayName().replaceAll("\\s", ""));
								}else{
									ybjson.put("room", "");
								}
								
								if(room.getName()!=null){
									ybjson.put("roomInfo", room.getName().replaceAll("\\s", ""));
								}else{
									ybjson.put("roomInfo","");
								}							
									ybjson.put("bldArea", "");
									ybjson.put("tnArea", "");			
								if(room.getCreateTime()!=null){
									String createTime = sdf.format(room.getCreateTime());
									ybjson.put("createTime", createTime);
								}else{
									ybjson.put("createTime", "");
								}
								if(room.getLastUpdateTime()!=null){
									String updateTime = sdf.format(room.getLastUpdateTime());
									ybjson.put("modifiedTime", updateTime);
								}else{
									ybjson.put("modifiedTime", "");
								}	
								if(room.getHandoverRoomDate()!=null){
									ybjson.put("deliveryDate", room.getHandoverRoomDate().toString());
								}else{
									ybjson.put("deliveryDate", "");
								}
							}
								ybarr.add(ybjson);
								datajson.put("datas",ybarr);
								datajson.put("timestamp",timestamp);
								initjson.put("esbInfo", esbjson);
								initjson.put("requestInfo",datajson);
//								同步房间信息到yiBei
								if(ybarr.size()>0){
									try {
//										System.out.println(initjson.toJSONString());
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
//								kehu
//								yb业主客户
								if(!info.getBizType().equals(ChangeBizTypeEnum.QUITROOM)){
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
//											System.out.println(initcjson.toJSONString());
											String rs111=SHEManageHelper.execPostYBowner(ctx, initcjson.toJSONString(),timestamp);
											JSONObject rso = JSONObject.parseObject(rs111);
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
//												MsgBox.showInfo("客户同步成功");
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
							
				 }
		}
	}
    private RoomSellStateEnum getRoomSellState(IObjectValue objectValue){
    	RoomSellStateEnum roomState=null;
		if(objectValue instanceof PrePurchaseManageInfo){
			roomState=RoomSellStateEnum.PrePurchase;
		}
		if(objectValue instanceof PurchaseManageInfo){
			roomState=RoomSellStateEnum.Purchase;
		}
		if(objectValue instanceof SignManageInfo){
			roomState=RoomSellStateEnum.Sign;
		}
		return roomState;
    }
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		ChangeManageInfo info=getChangeManageInfo(ctx, new ObjectUuidPK(billId));
		if(info.getSrcId()!=null){
			ObjectUuidPK pk=new ObjectUuidPK(info.getSrcId());
			IObjectValue objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(pk.getObjectType(),pk);
			
			if(ChangeBizTypeEnum.QUITROOM.equals(info.getBizType())||ChangeBizTypeEnum.CHANGEROOM.equals(info.getBizType())){
				SHEManageHelper.updateRoomState(ctx,((BaseTransactionInfo)objectValue).getRoom(),this.getRoomSellState(objectValue));
				SHEManageHelper.validTransaction(ctx,((BaseTransactionInfo)objectValue).getTransactionID(),false);
				this.setOtherBillState(ctx, info,null,false);
			}
			setBizState(ctx,objectValue,info.getBizType(),FDCBillStateEnum.SUBMITTED,false);
		}
		if(info.getNewId()!=null){
			ObjectUuidPK newpk=new ObjectUuidPK(info.getNewId());
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("relateBizBillId", info.getNewId()));
			if(SHERevBillFactory.getLocalInstance(ctx).exists(filter)){
				throw new EASBizException(new NumericExceptionSubItem("100","新生成业务单据已经产生收款单业务，不能进行反审批操作！"));
			}
			
			IObjectValue newobjectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(newpk.getObjectType(),newpk);
			
			if(ChangeBizTypeEnum.PRICECHANGE.equals(info.getBizType())||ChangeBizTypeEnum.CHANGENAME.equals(info.getBizType())){
				SHEManageHelper.toOldTransaction(ctx,info.getTransactionID());
			}
			setBizState(ctx,newobjectValue,info.getBizType(),FDCBillStateEnum.SUBMITTED,true);
		}
		
		delPayBill(ctx,info);

		ChangeManageInfo billInfo = new ChangeManageInfo();
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		billInfo.setSheRevBill(null);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("sheRevBill");
		_updatePartial(ctx, billInfo, selector);
	}
	private void submitAction(Context ctx,IObjectValue model) throws BOSException, EASBizException{
		ChangeManageInfo info=(ChangeManageInfo)model;
		
		if(info.getSrcId()!=null){
			boolean isQuitRoomAudit=false;
			boolean isChangeRoomAuidt=false;
//			RoomDisplaySetting set = new RoomDisplaySetting(ctx,SHEParamConstant.PROJECT_SET_MAP);
			Map detailSet = RoomDisplaySetting.getNewProjectSet(ctx,((BaseTransactionInfo)info).getSellProject().getId().toString());
			if(detailSet!=null){
				isQuitRoomAudit=((Boolean)detailSet.get(SHEParamConstant.T2_IS_RE_PRICE_OF_QUIT_ROOM)).booleanValue();
				isChangeRoomAuidt=((Boolean)detailSet.get(SHEParamConstant.T2_IS_RE_PRICE_OF_CHANGE_ROOM)).booleanValue();
			}
			
			ObjectUuidPK pk=new ObjectUuidPK(info.getSrcId());
			IObjectValue objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(pk.getObjectType(),pk);
			RoomSellStateEnum roomsellState=null;
			BaseTransactionInfo newinfo=genNewBill(ctx,info);
			
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("changeState");

			if(ChangeBizTypeEnum.QUITROOM.equals(info.getBizType())){
				roomsellState=RoomSellStateEnum.quitRoom;
				SHEManageHelper.updateTransaction(ctx, ((BaseTransactionInfo)objectValue),roomsellState,false);
				
				if(isQuitRoomAudit){
					info.getSrcRoom().setChangeState(ChangeStateEnum.QuitRoom);
					RoomFactory.getLocalInstance(ctx).updatePartial(info.getSrcRoom(), sels);
				}
			}
			if(ChangeBizTypeEnum.CHANGEROOM.equals(info.getBizType())){
				roomsellState=RoomSellStateEnum.changeRoom;
				SHEManageHelper.updateTransaction(ctx, ((BaseTransactionInfo)objectValue), roomsellState,false);
				SHEManageHelper.updateTransaction(ctx, newinfo,this.getRoomSellState(newinfo),true);
				
				if(isChangeRoomAuidt){
					info.getSrcRoom().setChangeState(ChangeStateEnum.ChangeRoom);
					RoomFactory.getLocalInstance(ctx).updatePartial(info.getSrcRoom(), sels);
				}
			}
			if(ChangeBizTypeEnum.CHANGENAME.equals(info.getBizType())){
				roomsellState=RoomSellStateEnum.ChangeName;
				SHEManageHelper.updateTransaction(ctx, newinfo,roomsellState,false);
			}
			if(ChangeBizTypeEnum.PRICECHANGE.equals(info.getBizType())){
				roomsellState=RoomSellStateEnum.priceChange;
				SHEManageHelper.updateTransaction(ctx, newinfo,roomsellState,false);
			}
			
			setBizState(ctx,objectValue,info.getBizType(),FDCBillStateEnum.SUBMITTED,false);
			genFeeEntry(ctx,info);

		}
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		submitAction(ctx,model);
		return super._submit(ctx, model);
	}
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		submitAction(ctx,model);
		super._submit(ctx, pk, model);
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
		ChangeManageInfo info=(ChangeManageInfo) _getValue(ctx, pk);
		if(FDCBillStateEnum.SUBMITTED.equals(info.getState())){
			if(info.getSrcId()!=null){
				if(ChangeBizTypeEnum.CHANGENAME.equals(info.getBizType())){
					if(info.getNewId()!=null){
						ObjectUuidPK oldpk=new ObjectUuidPK(info.getSrcId());
						IObjectValue objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(oldpk.getObjectType(),oldpk);
						boolean isClearActRevAmount=isExistSameCustomer(objectValue,info);
						if(!isClearActRevAmount){
							updateSHERevBillCustomer(ctx,info.getTransactionID(),info,true);
						}else{
							TransactionInfo tran=TransactionFactory.getLocalInstance(ctx).getTransactionInfo(new ObjectUuidPK(info.getTransactionID()));
							SHEManageHelper.updateCommerceChanceStage(ctx,(BaseTransactionInfo)objectValue,info.getNewId(),tran.getCurrentLink(),tran.getPreLink(),true);
						}
					}
				}
				if(ChangeBizTypeEnum.CHANGEROOM.equals(info.getBizType())||ChangeBizTypeEnum.QUITROOM.equals(info.getBizType())){
					 
					SelectorItemCollection sels = new SelectorItemCollection();
					sels.add("changeState");
					info.getSrcRoom().setChangeState(null);
					RoomFactory.getLocalInstance(ctx).updatePartial(info.getSrcRoom(), sels);
				}
				if(ChangeBizTypeEnum.CHANGEROOM.equals(info.getBizType())||ChangeBizTypeEnum.QUITROOM.equals(info.getBizType())){
					SHEManageHelper.toOldTransaction(ctx, info.getTransactionID());
				}
				BaseTransactionInfo srcInfo=delFeeEntry(ctx,info);
				
				if(info.getNewId()!=null){
					SHEManageHelper.getBizInterface(ctx, srcInfo).delete(new ObjectUuidPK(info.getNewId()));
				}
			}
		}
		super._delete(ctx, pk);
	}
	
	protected void setBizState(Context ctx,IObjectValue objectValue,ChangeBizTypeEnum bizType,FDCBillStateEnum billState,boolean isNewBill) throws EASBizException, BOSException{
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		
		if(FDCBillStateEnum.SUBMITTED.equals(billState)){
			if(ChangeBizTypeEnum.CHANGENAME.equals(bizType)){
				((BaseTransactionInfo)objectValue).setBizState(TransactionStateEnum.CHANGENAMEAUDITING);
			}
			if(ChangeBizTypeEnum.CHANGEROOM.equals(bizType)){
				((BaseTransactionInfo)objectValue).setBizState(TransactionStateEnum.CHANGEROOMAUDITING);
			}
			if(ChangeBizTypeEnum.QUITROOM.equals(bizType)){
				((BaseTransactionInfo)objectValue).setBizState(TransactionStateEnum.QUITROOMAUDITING);
			}
			if(ChangeBizTypeEnum.PRICECHANGE.equals(bizType)){
				((BaseTransactionInfo)objectValue).setBizState(TransactionStateEnum.CHANGEPIRCEAUDITING);
			}
			SHEManageHelper.getBizInterface(ctx,objectValue).updatePartial(((BaseTransactionInfo)objectValue), selector);
		}
		if(FDCBillStateEnum.AUDITTED.equals(billState)){
			if(isNewBill){
				if(objectValue instanceof PurchaseManageInfo){
					PurchaseManageInfo info=(PurchaseManageInfo) objectValue;
					info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
					info.setAuditTime(new Date());
					info.setBizState(TransactionStateEnum.PURAUDIT);
					info.setState(FDCBillStateEnum.AUDITTED);
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("bizState");
					sic.add("auditor");
					sic.add("auditTime");
					sic.add("state");
					PurchaseManageFactory.getLocalInstance(ctx).updatePartial(info, sic);
				}else if(objectValue instanceof SignManageInfo){
					SignManageInfo info=(SignManageInfo) objectValue;
					info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
					info.setAuditTime(new Date());
					info.setBizState(TransactionStateEnum.SIGNAUDIT);
					info.setState(FDCBillStateEnum.AUDITTED);
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("bizState");
					sic.add("auditor");
					sic.add("auditTime");
					sic.add("state");
					SignManageFactory.getLocalInstance(ctx).updatePartial(info, sic);
				}
//				SHEManageHelper.getBizInterface(ctx,objectValue).audit(((BaseTransactionInfo)objectValue).getId());
			}else{
				if(ChangeBizTypeEnum.CHANGENAME.equals(bizType)){
					((BaseTransactionInfo)objectValue).setBizState(TransactionStateEnum.CNNULLIFY);
				}
				if(ChangeBizTypeEnum.CHANGEROOM.equals(bizType)){
					((BaseTransactionInfo)objectValue).setBizState(TransactionStateEnum.CRNULLIFY);
				}
				if(ChangeBizTypeEnum.QUITROOM.equals(bizType)){
					((BaseTransactionInfo)objectValue).setBizState(TransactionStateEnum.QRNULLIFY);
				}
				if(ChangeBizTypeEnum.PRICECHANGE.equals(bizType)){
					((BaseTransactionInfo)objectValue).setBizState(TransactionStateEnum.PCNULLIFY);
				}
				SHEManageHelper.getBizInterface(ctx,objectValue).updatePartial(((BaseTransactionInfo)objectValue), selector);
			}
		}
	}
	//更新收款单客户，删除时候返回，还要更新iD 名称等
	private void updateSHERevBillCustomer(Context ctx,BOSUuid transactionID,ChangeManageInfo changeManage,boolean isDel) throws BOSException, EASBizException{
		if(transactionID==null) return;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter =new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("relateTransId", transactionID));
		view.setFilter(filter);
		
		SHERevBillCollection col=SHERevBillFactory.getLocalInstance(ctx).getSHERevBillCollection(view);
		
		for(int i=0;i<col.size();i++){
			SHERevBillInfo sheRevInfo=col.get(i);
			String customerid="";
			sheRevInfo.getCustomerEntry().clear();
			
			if(isDel){
				sheRevInfo.setCustomerNames(changeManage.getSrcCustomerNames());
				ObjectUuidPK pk=new ObjectUuidPK(changeManage.getSrcId());
				IObjectValue objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(pk.getObjectType(),pk);
				
				if(objectValue instanceof PrePurchaseManageInfo){
					for(int j=0;j<((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().size();j++){
						SHERevCustEntryInfo entry=new SHERevCustEntryInfo();
						SHECustomerInfo customer=((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().get(j).getCustomer();
						entry.setSheCustomer(customer);
						sheRevInfo.getCustomerEntry().add(entry);
						if(j==((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().size()-1){
							customerid=customerid+customer.getId().toString();
						}else{
							customerid=customerid+customer.getId().toString()+",";
						}
					}
				}
				if(objectValue instanceof PurchaseManageInfo){
					for(int j=0;j<((PurchaseManageInfo)objectValue).getPurCustomerEntry().size();j++){
						SHERevCustEntryInfo entry=new SHERevCustEntryInfo();
						SHECustomerInfo customer=((PurchaseManageInfo)objectValue).getPurCustomerEntry().get(j).getCustomer();
						entry.setSheCustomer(customer);
						sheRevInfo.getCustomerEntry().add(entry);
						if(j==((PurchaseManageInfo)objectValue).getPurCustomerEntry().size()-1){
							customerid=customerid+customer.getId().toString();
						}else{
							customerid=customerid+customer.getId().toString()+",";
						}
						
					}
				}
				if(objectValue instanceof SignManageInfo){
					for(int j=0;j<((SignManageInfo)objectValue).getSignCustomerEntry().size();j++){
						SHERevCustEntryInfo entry=new SHERevCustEntryInfo();
						SHECustomerInfo customer=((SignManageInfo)objectValue).getSignCustomerEntry().get(j).getCustomer();
						entry.setSheCustomer(customer);
						sheRevInfo.getCustomerEntry().add(entry);
						if(j==((SignManageInfo)objectValue).getSignCustomerEntry().size()-1){
							customerid=customerid+customer.getId().toString();
						}else{
							customerid=customerid+customer.getId().toString()+",";
						}
					}
				}
			}else{
				sheRevInfo.setCustomerNames(changeManage.getCustomerNames());
				
				ChangeCustomerEntryCollection changeCustomer=changeManage.getCustomerEntry();
				for(int j=0;j<changeCustomer.size();j++){
					SHERevCustEntryInfo entry=new SHERevCustEntryInfo();
					entry.setSheCustomer(changeCustomer.get(j).getCustomer());
					sheRevInfo.getCustomerEntry().add(entry);
					
					if(i==col.size()-1){
						customerid=customerid+changeCustomer.get(j).getCustomer().getId().toString();
					}else{
						customerid=customerid+changeCustomer.get(j).getCustomer().getId().toString()+",";
					}
				}
			}
			sheRevInfo.setCustomerIds(customerid);
			SHERevBillFactory.getLocalInstance(ctx).update(new ObjectUuidPK(sheRevInfo.getId()), sheRevInfo);
		}
	}
	private boolean isExistSameCustomer(IObjectValue objectValue,ChangeManageInfo info){
		Set cusomer=new HashSet();
		boolean isNewPayList=false;
		if(objectValue instanceof PrePurchaseManageInfo){
			PrePurchaseManageInfo newinfo=(PrePurchaseManageInfo)objectValue;
			for(int i=0;i<newinfo.getPrePurchaseCustomerEntry().size();i++){
				cusomer.add(newinfo.getPrePurchaseCustomerEntry().get(i).getCustomer().getId().toString());
			}
		}
		if(objectValue instanceof PurchaseManageInfo){
			PurchaseManageInfo newinfo=(PurchaseManageInfo)objectValue;
			for(int i=0;i<newinfo.getPurCustomerEntry().size();i++){
				cusomer.add(newinfo.getPurCustomerEntry().get(i).getCustomer().getId().toString());
			}
		}
		if(objectValue instanceof SignManageInfo){
			SignManageInfo newinfo=(SignManageInfo)objectValue;
			for(int i=0;i<newinfo.getSignCustomerEntry().size();i++){
				cusomer.add(newinfo.getSignCustomerEntry().get(i).getCustomer().getId().toString());
			}
		}
		for(int i=0;i<info.getCustomerEntry().size();i++){
			if(cusomer.contains(info.getCustomerEntry().get(i).getCustomer().getId().toString())){
				isNewPayList=false;
				break;
			}else{
				isNewPayList= true;
			}
		}
		return isNewPayList;
	}
	/**
	 * 更名、价格变更、换房提交后产生一条新单据 
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected BaseTransactionInfo genNewBill (Context ctx, ChangeManageInfo info) throws BOSException, EASBizException {
		ObjectUuidPK pk=new ObjectUuidPK(info.getSrcId());
		IObjectValue objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(pk.getObjectType(),pk);
		
		BOSUuid newBillId=null;
		if(info.getNewId()!=null){
			newBillId=info.getNewId();
		}else{
			newBillId=BOSUuid.create(objectValue.getBOSType());
		}
		if(ChangeBizTypeEnum.CHANGEROOM.equals(info.getBizType())){
			if(objectValue instanceof PrePurchaseManageInfo){
				SHEManageHelper.updateRoomState(ctx, info.getRoom(), RoomSellStateEnum.PrePurchase);
			}
			if(objectValue instanceof PurchaseManageInfo){
				SHEManageHelper.updateRoomState(ctx, info.getRoom(), RoomSellStateEnum.Purchase);
			}
			if(objectValue instanceof SignManageInfo){
				SHEManageHelper.updateRoomState(ctx, info.getRoom(), RoomSellStateEnum.Sign);
			}
			ObjectUuidPK newpk=new ObjectUuidPK(info.getNewId());
			return (BaseTransactionInfo) DynamicObjectFactory.getLocalInstance(ctx).getValue(newpk.getObjectType(),newpk);
		}
		if(ChangeBizTypeEnum.CHANGENAME.equals(info.getBizType())){
			if(objectValue instanceof PrePurchaseManageInfo){
				PrePurchaseManageInfo newinfo=(PrePurchaseManageInfo)((PrePurchaseManageInfo)objectValue).clone();
				
				boolean isClearActRevAmount=isExistSameCustomer(newinfo,info);
				if(!isClearActRevAmount){
					updateSHERevBillCustomer(ctx,info.getTransactionID(),info,false);
				}
				
				newinfo.getPrePurchaseCustomerEntry().clear();
				for(int i=0;i<info.getCustomerEntry().size();i++){
					PrePurchaseCustomerEntryInfo entry=new PrePurchaseCustomerEntryInfo();
					SHEManageHelper.setCustomerListEntry(entry,info.getCustomerEntry().get(i));
					
					newinfo.getPrePurchaseCustomerEntry().add(entry);
				}
				newinfo.setCustomerNames(info.getCustomerNames());
				newinfo.setCustomerPhone(info.getCustomerPhone());
				
				clonePayListEntry(ctx,newinfo);
				cloneRoomAttachmentEntry(newinfo);
				cloneAgioEntry(newinfo);
				cloneSaleManEntry(newinfo);
				
				newinfo.setId(newBillId);
				newinfo.setAuditor(null);
				newinfo.setAuditTime(null);
				newinfo.setBizState(TransactionStateEnum.CHANGENAMEAUDITING);
				newinfo.setBusAdscriptionDate(info.getBusAdscriptionDate());
				
				if(newinfo.getNumber()==null || newinfo.getNumber().trim().equals("")){
					String retNumber = getNewBillNumber(ctx,newinfo);
					if(retNumber!=null) newinfo.setNumber(retNumber);
				}
				if(info.getNewId()!=null){
					PrePurchaseManageFactory.getLocalInstance(ctx).update(new ObjectUuidPK(newBillId), newinfo);
				}else{
					PrePurchaseManageFactory.getLocalInstance(ctx).addnew(newinfo);
				}
				info.setNewId(newBillId);
				return newinfo;
			}

			if(objectValue instanceof PurchaseManageInfo){
				PurchaseManageInfo newinfo=(PurchaseManageInfo)((PurchaseManageInfo)objectValue).clone();

				boolean	isClearActRevAmount=isExistSameCustomer(newinfo,info);
				if(!isClearActRevAmount){
					updateSHERevBillCustomer(ctx,info.getTransactionID(),info,false);
				}
				newinfo.getPurCustomerEntry().clear();
				for(int i=0;i<info.getCustomerEntry().size();i++){
					PurCustomerEntryInfo entry=new PurCustomerEntryInfo();
					SHEManageHelper.setCustomerListEntry(entry,info.getCustomerEntry().get(i));
					
					newinfo.getPurCustomerEntry().add(entry);
				}
				newinfo.setCustomerNames(info.getCustomerNames());
				newinfo.setCustomerPhone(info.getCustomerPhone());
				
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
				String recommendeds="";
				String qdPersons="";
				String oneQd="";
				String twoQd="";
				String customerCertificateNumber="";
				
				SHECustomerInfo quc=null;
				Timestamp qudate=null;
				
				for(int i=0;i<info.getCustomerEntry().size();i++){
					if("true".equals(paramValue)){
						if(info.getCustomerEntry().get(i).isIsMain()){
							quc=info.getCustomerEntry().get(i).getCustomer();
						}
					}else{
						SHECustomerInfo sheCI=info.getCustomerEntry().get(i).getCustomer();
						if(sheCI.getFirstDate()==null&&sheCI.getReportDate()==null){
							throw new EASBizException(new NumericExceptionSubItem("100","客户报备日期和首访日期都为空！"));
						}
						if(qudate==null){
							if(sheCI.getReportDate()!=null){
								qudate=sheCI.getReportDate();
							}else{
								qudate=sheCI.getFirstDate();
							}
							quc=sheCI;
						}else{
							Timestamp comdate=null;
							if(sheCI.getReportDate()!=null){
								comdate=sheCI.getReportDate();
							}else{
								comdate=sheCI.getFirstDate();
							}
							if(qudate.after(comdate)){
								qudate=comdate;
								quc=sheCI;
							}
						}
					}
					if(i==info.getCustomerEntry().size()-1){
						customerCertificateNumber=customerCertificateNumber+info.getCustomerEntry().get(i).getCustomer().getCertificateNumber();
					}else{
						customerCertificateNumber=customerCertificateNumber+info.getCustomerEntry().get(i).getCustomer().getCertificateNumber()+";";
					}
				}
				recommendeds=quc.getRecommended();
				qdPersons=quc.getQdPersontxt();
				oneQd=quc.getOneQd();
				twoQd=quc.getTwoQd();
				
				newinfo.setRecommended(recommendeds);
				newinfo.setQdPerson(qdPersons);
				newinfo.setOneQd(oneQd);
				newinfo.setTwoQd(twoQd);
				
				newinfo.setCustomerCertificateNumber(customerCertificateNumber);
				
				clonePayListEntry(ctx,newinfo);
				cloneRoomAttachmentEntry(newinfo);
				cloneAgioEntry(newinfo);
//				cloneSaleManEntry(newinfo);
				
				for(int i=0;i<newinfo.getPurSaleManEntry().size();i++){
					PurSaleManEntryInfo entry =newinfo.getPurSaleManEntry().get(i);
					entry.setUser(quc.getPropertyConsultant());
					
					SHECustomerInfo cus=SHECustomerFactory.getLocalInstance(ctx).getSHECustomerInfo("select propertyConsultant.name from where id='"+quc.getId()+"'");
					newinfo.setSaleManNames(cus.getPropertyConsultant().getName());
					newinfo.setSalesman(quc.getPropertyConsultant());
					entry.setId(null);
				}
				
				newinfo.setId(newBillId);
				newinfo.setAuditor(null);
				newinfo.setAuditTime(null);
				newinfo.setBizState(TransactionStateEnum.CHANGENAMEAUDITING);
				newinfo.setBusAdscriptionDate(info.getBusAdscriptionDate());
				
				if(newinfo.getNumber()==null || newinfo.getNumber().trim().equals("")){
					String retNumber = getNewBillNumber(ctx,newinfo);
					if(retNumber!=null) newinfo.setNumber(retNumber);
				}
				if(info.getNewId()!=null){
					PurchaseManageFactory.getLocalInstance(ctx).update(new ObjectUuidPK(newBillId), newinfo);
				}else{
					PurchaseManageFactory.getLocalInstance(ctx).addnew(newinfo);
				}
				info.setNewId(newBillId);
				return newinfo;
			}
			if(objectValue instanceof SignManageInfo){
				SignManageInfo newinfo=(SignManageInfo)((SignManageInfo)objectValue).clone();

				boolean isClearActRevAmount=isExistSameCustomer(newinfo,info);
				if(!isClearActRevAmount){
					updateSHERevBillCustomer(ctx,info.getTransactionID(),info,false);
				}
				
				newinfo.getSignCustomerEntry().clear();
				for(int i=0;i<info.getCustomerEntry().size();i++){
					SignCustomerEntryInfo entry=new SignCustomerEntryInfo();
					SHEManageHelper.setCustomerListEntry(entry,info.getCustomerEntry().get(i));
					
					newinfo.getSignCustomerEntry().add(entry);
				}
				newinfo.setCustomerNames(info.getCustomerNames());
				newinfo.setCustomerPhone(info.getCustomerPhone());
				
				
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
				String recommendeds="";
				String qdPersons="";
				String oneQd="";
				String twoQd="";
				String customerCertificateNumber="";
				
				SHECustomerInfo quc=null;
				Timestamp qudate=null;
				
				for(int i=0;i<info.getCustomerEntry().size();i++){
					if("true".equals(paramValue)){
						if(info.getCustomerEntry().get(i).isIsMain()){
							quc=info.getCustomerEntry().get(i).getCustomer();
						}
					}else{
						SHECustomerInfo sheCI=info.getCustomerEntry().get(i).getCustomer();
						if(sheCI.getFirstDate()==null&&sheCI.getReportDate()==null){
							throw new EASBizException(new NumericExceptionSubItem("100","客户报备日期和首访日期都为空！"));
						}
						if(qudate==null){
							if(sheCI.getReportDate()!=null){
								qudate=sheCI.getReportDate();
							}else{
								qudate=sheCI.getFirstDate();
							}
							quc=sheCI;
						}else{
							Timestamp comdate=null;
							if(sheCI.getReportDate()!=null){
								comdate=sheCI.getReportDate();
							}else{
								comdate=sheCI.getFirstDate();
							}
							if(qudate.after(comdate)){
								qudate=comdate;
								quc=sheCI;
							}
						}
					}
					if(i==info.getCustomerEntry().size()-1){
						customerCertificateNumber=customerCertificateNumber+info.getCustomerEntry().get(i).getCustomer().getCertificateNumber();
					}else{
						customerCertificateNumber=customerCertificateNumber+info.getCustomerEntry().get(i).getCustomer().getCertificateNumber()+";";
					}
				}
				recommendeds=quc.getRecommended();
				qdPersons=quc.getQdPersontxt();
				oneQd=quc.getOneQd();
				twoQd=quc.getTwoQd();
				
				newinfo.setRecommended(recommendeds);
				newinfo.setQdPerson(qdPersons);
				newinfo.setOneQd(oneQd);
				newinfo.setTwoQd(twoQd);
				
				newinfo.setCustomerCertificateNumber(customerCertificateNumber);
				
				clonePayListEntry(ctx,newinfo);
				cloneRoomAttachmentEntry(newinfo);
				cloneAgioEntry(newinfo);
//				cloneSaleManEntry(newinfo);
				
				for(int i=0;i<newinfo.getSignSaleManEntry().size();i++){
					SignSaleManEntryInfo entry =newinfo.getSignSaleManEntry().get(i);
					entry.setUser(quc.getPropertyConsultant());
					
					SHECustomerInfo cus=SHECustomerFactory.getLocalInstance(ctx).getSHECustomerInfo("select propertyConsultant.name from where id='"+quc.getId()+"'");
					newinfo.setSaleManNames(cus.getPropertyConsultant().getName());
					newinfo.setSalesman(quc.getPropertyConsultant());
					entry.setId(null);
				}
				
				newinfo.setId(newBillId);
				newinfo.setAuditor(null);
				newinfo.setAuditTime(null);
				newinfo.setBizState(TransactionStateEnum.CHANGENAMEAUDITING);
				newinfo.setBusAdscriptionDate(info.getBusAdscriptionDate());
				
				if(newinfo.getNumber()==null || newinfo.getNumber().trim().equals("")){
					String retNumber = getNewBillNumber(ctx,newinfo);
					if(retNumber!=null) newinfo.setNumber(retNumber);
				}
				if(info.getNewId()!=null){
					SignManageFactory.getLocalInstance(ctx).update(new ObjectUuidPK(newBillId), newinfo);
				}else{
					SignManageFactory.getLocalInstance(ctx).addnew(newinfo);
				}
				info.setNewId(newBillId);
				return newinfo;
			}
		}
		if(ChangeBizTypeEnum.PRICECHANGE.equals(info.getBizType())){
			if(objectValue instanceof PrePurchaseManageInfo){
				PrePurchaseManageInfo newinfo=(PrePurchaseManageInfo)((PrePurchaseManageInfo)objectValue).clone();
				
				SHEManageHelper.setBaseInfo(newinfo,info,true);
				
				newinfo.getPrePurchasePayListEntry().clear();
				for(int i=0;i<info.getPayListEntry().size();i++){
					PrePurchasePayListEntryInfo entry=new PrePurchasePayListEntryInfo();
					SHEManageHelper.setPayListEntry(entry,info.getPayListEntry().get(i),true);
					
					newinfo.getPrePurchasePayListEntry().add(entry);
				}
				
				newinfo.getPrePurchaseAgioEntry().clear();
				for(int i=0;i<info.getAgioEntry().size();i++){
					PrePurchaseAgioEntryInfo entry=new PrePurchaseAgioEntryInfo();
					SHEManageHelper.setAgioListEntry(entry,info.getAgioEntry().get(i));
					
					newinfo.getPrePurchaseAgioEntry().add(entry);
				}
				
				newinfo.getPrePurchaseRoomAttachmentEntry().clear();
				for(int i=0;i<info.getRoomAttachEntry().size();i++){
					PrePurchaseRoomAttachmentEntryInfo entry=new PrePurchaseRoomAttachmentEntryInfo();
					SHEManageHelper.setRoomAttachmentListEntry(entry,info.getRoomAttachEntry().get(i));
					
					for(int j=0;j<info.getRoomAttachEntry().get(i).getAgioEntry().size();j++){
						PrePurchaseAgioEntryInfo agioEntry=new PrePurchaseAgioEntryInfo();
						SHEManageHelper.setAgioListEntry(agioEntry,info.getRoomAttachEntry().get(i).getAgioEntry().get(j));
						
						entry.getPrePurchaseAgioEntry().add(agioEntry);
					}
					newinfo.getPrePurchaseRoomAttachmentEntry().add(entry);
				}
				
				
				cloneCustomerEntry(newinfo);
				cloneSaleManEntry(newinfo);
				
				newinfo.setId(newBillId);
				newinfo.setAuditor(null);
				newinfo.setAuditTime(null);
				newinfo.setBizState(TransactionStateEnum.CHANGEPIRCEAUDITING);
				newinfo.setBusAdscriptionDate(info.getBusAdscriptionDate());
				
				if(newinfo.getNumber()==null || newinfo.getNumber().trim().equals("")){
					String retNumber = getNewBillNumber(ctx,newinfo);
					if(retNumber!=null) newinfo.setNumber(retNumber);
				}
				if(info.getNewId()!=null){
					PrePurchaseManageFactory.getLocalInstance(ctx).update(new ObjectUuidPK(newBillId), newinfo);
				}else{
					PrePurchaseManageFactory.getLocalInstance(ctx).addnew(newinfo);
				}
				info.setNewId(newBillId);
				return newinfo;
			}

			if(objectValue instanceof PurchaseManageInfo){
				PurchaseManageInfo newinfo=(PurchaseManageInfo)((PurchaseManageInfo)objectValue).clone();
				
				SHEManageHelper.setBaseInfo(newinfo,info,true);
				
				newinfo.setPreArea(info.getPreArea());
				newinfo.setPlanningArea(info.getPlanningArea());
				newinfo.setActualArea(info.getActualArea());
				newinfo.setPlanningCompensate(info.getPlanningCompensate());
				newinfo.setCashSalesCompensate(info.getCashSalesCompensate());
				
				newinfo.getPurPayListEntry().clear();
				for(int i=0;i<info.getPayListEntry().size();i++){
					PurPayListEntryInfo entry=new PurPayListEntryInfo();
					SHEManageHelper.setPayListEntry(entry,info.getPayListEntry().get(i),true);
					
					newinfo.getPurPayListEntry().add(entry);
				}
				
				newinfo.getPurAgioEntry().clear();
				for(int i=0;i<info.getAgioEntry().size();i++){
					PurAgioEntryInfo entry=new PurAgioEntryInfo();
					SHEManageHelper.setAgioListEntry(entry,info.getAgioEntry().get(i));
					
					newinfo.getPurAgioEntry().add(entry);
				}
				
				newinfo.getPurRoomAttachmentEntry().clear();
				for(int i=0;i<info.getRoomAttachEntry().size();i++){
					PurRoomAttachmentEntryInfo entry=new PurRoomAttachmentEntryInfo();
					SHEManageHelper.setRoomAttachmentListEntry(entry,info.getRoomAttachEntry().get(i));
					
					for(int j=0;j<info.getRoomAttachEntry().get(i).getAgioEntry().size();j++){
						PurAgioEntryInfo agioEntry=new PurAgioEntryInfo();
						SHEManageHelper.setAgioListEntry(agioEntry,info.getRoomAttachEntry().get(i).getAgioEntry().get(j));
						
						entry.getPurAgioEntry().add(agioEntry);
					}
					newinfo.getPurRoomAttachmentEntry().add(entry);
				}
				
				
				cloneCustomerEntry(newinfo);
				cloneSaleManEntry(newinfo);
				
				newinfo.setId(newBillId);
				newinfo.setAuditor(null);
				newinfo.setAuditTime(null);
				newinfo.setBizState(TransactionStateEnum.CHANGEPIRCEAUDITING);
				newinfo.setBusAdscriptionDate(info.getBusAdscriptionDate());
				
				if(newinfo.getNumber()==null || newinfo.getNumber().trim().equals("")){
					String retNumber = getNewBillNumber(ctx,newinfo);
					if(retNumber!=null) newinfo.setNumber(retNumber);
				}
				if(info.getNewId()!=null){
					PurchaseManageFactory.getLocalInstance(ctx).update(new ObjectUuidPK(newBillId), newinfo);
				}else{
					PurchaseManageFactory.getLocalInstance(ctx).addnew(newinfo);
				}
				info.setNewId(newBillId);
				return newinfo;
			}
			if(objectValue instanceof SignManageInfo){
				SignManageInfo newinfo=(SignManageInfo)((SignManageInfo)objectValue).clone();
				
				SHEManageHelper.setBaseInfo(newinfo,info,true);
				
				newinfo.setPreArea(info.getPreArea());
				newinfo.setPlanningArea(info.getPlanningArea());
				newinfo.setActualArea(info.getActualArea());
				newinfo.setPlanningCompensate(info.getPlanningCompensate());
				newinfo.setCashSalesCompensate(info.getCashSalesCompensate());
				
				newinfo.getSignPayListEntry().clear();
				for(int i=0;i<info.getPayListEntry().size();i++){
					SignPayListEntryInfo entry=new SignPayListEntryInfo();
					SHEManageHelper.setPayListEntry(entry,info.getPayListEntry().get(i),true);
					
					newinfo.getSignPayListEntry().add(entry);
				}
				
				newinfo.getSignAgioEntry().clear();
				for(int i=0;i<info.getAgioEntry().size();i++){
					SignAgioEntryInfo entry=new SignAgioEntryInfo();
					SHEManageHelper.setAgioListEntry(entry,info.getAgioEntry().get(i));
					
					newinfo.getSignAgioEntry().add(entry);
				}
				
				newinfo.getSignRoomAttachmentEntry().clear();
				for(int i=0;i<info.getRoomAttachEntry().size();i++){
					SignRoomAttachmentEntryInfo entry=new SignRoomAttachmentEntryInfo();
					SHEManageHelper.setRoomAttachmentListEntry(entry,info.getRoomAttachEntry().get(i));
					
					for(int j=0;j<info.getRoomAttachEntry().get(i).getAgioEntry().size();j++){
						SignAgioEntryInfo agioEntry=new SignAgioEntryInfo();
						SHEManageHelper.setAgioListEntry(agioEntry,info.getRoomAttachEntry().get(i).getAgioEntry().get(j));
						
						entry.getSignAgioEntry().add(agioEntry);
					}
					newinfo.getSignRoomAttachmentEntry().add(entry);
				}
				cloneCustomerEntry(newinfo);
				cloneSaleManEntry(newinfo);
				
				newinfo.setId(newBillId);
				newinfo.setAuditor(null);
				newinfo.setAuditTime(null);
				newinfo.setBizState(TransactionStateEnum.CHANGEPIRCEAUDITING);
				newinfo.setBusAdscriptionDate(info.getBusAdscriptionDate());
				
				if(newinfo.getNumber()==null || newinfo.getNumber().trim().equals("")){
					String retNumber = getNewBillNumber(ctx,newinfo);
					if(retNumber!=null) newinfo.setNumber(retNumber);
				}
				if(info.getNewId()!=null){
					SignManageFactory.getLocalInstance(ctx).update(new ObjectUuidPK(newBillId), newinfo);
				}else{
					SignManageFactory.getLocalInstance(ctx).addnew(newinfo);
				}
				info.setNewId(newBillId);
				return newinfo;
			}
		}

		return null;
	}
	
	private String getNewBillNumber(Context ctx,IObjectValue objectValue) throws BOSException, EASBizException {
		OrgUnitInfo crrOu = ContextUtil.getCurrentOrgUnit(ctx);
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		boolean isExist = iCodingRuleManager.isExist(objectValue, crrOu.getId().toString());
		if(isExist) {
			return iCodingRuleManager.getNumber(objectValue, crrOu.getId().toString());
		}
		return null;
	}
	
	private BaseTransactionInfo cloneCustomerEntry(BaseTransactionInfo newObjectValue){
		if(newObjectValue instanceof PrePurchaseManageInfo){
			PrePurchaseManageInfo newinfo=(PrePurchaseManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getPrePurchaseCustomerEntry().size();i++){
				PrePurchaseCustomerEntryInfo entry =newinfo.getPrePurchaseCustomerEntry().get(i);
				entry.setId(null);
			}
		}
		if(newObjectValue instanceof PurchaseManageInfo){
			PurchaseManageInfo newinfo=(PurchaseManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getPurCustomerEntry().size();i++){
				PurCustomerEntryInfo entry =newinfo.getPurCustomerEntry().get(i);
				entry.setId(null);
			}
		}
		if(newObjectValue instanceof SignManageInfo){
			SignManageInfo newinfo=(SignManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getSignCustomerEntry().size();i++){
				SignCustomerEntryInfo entry =newinfo.getSignCustomerEntry().get(i);
				entry.setId(null);
			}
		}
		return newObjectValue;
	}
	//isNewPayLsit 是否重新生成应收明细，实收款为0
	private BaseTransactionInfo clonePayListEntry(Context ctx,BaseTransactionInfo newObjectValue) throws EASBizException, BOSException{
		if(newObjectValue instanceof PrePurchaseManageInfo){
			PrePurchaseManageInfo newinfo=(PrePurchaseManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getPrePurchasePayListEntry().size();i++){
				PrePurchasePayListEntryInfo entry =newinfo.getPrePurchasePayListEntry().get(i);
				entry.setId(null);
			}
		}
		if(newObjectValue instanceof PurchaseManageInfo){
			PurchaseManageInfo newinfo=(PurchaseManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getPurPayListEntry().size();i++){
				PurPayListEntryInfo entry =newinfo.getPurPayListEntry().get(i);
				entry.setId(null);
			}
		}  
		if(newObjectValue instanceof SignManageInfo){
			SignManageInfo newinfo=(SignManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getSignPayListEntry().size();i++){
				SignPayListEntryInfo entry =newinfo.getSignPayListEntry().get(i);
				entry.setId(null);
			}
		}
		return newObjectValue;
	}
	private BaseTransactionInfo cloneRoomAttachmentEntry(BaseTransactionInfo newObjectValue){
		if(newObjectValue instanceof PrePurchaseManageInfo){
			PrePurchaseManageInfo newinfo=(PrePurchaseManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getPrePurchaseRoomAttachmentEntry().size();i++){
				PrePurchaseRoomAttachmentEntryInfo entry =newinfo.getPrePurchaseRoomAttachmentEntry().get(i);
				entry.setId(null);
				for(int j=0;j<entry.getPrePurchaseAgioEntry().size();j++){
					entry.getPrePurchaseAgioEntry().get(j).setId(null);
				}
			}
		}
		if(newObjectValue instanceof PurchaseManageInfo){
			PurchaseManageInfo newinfo=(PurchaseManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getPurRoomAttachmentEntry().size();i++){
				PurRoomAttachmentEntryInfo entry =newinfo.getPurRoomAttachmentEntry().get(i);
				entry.setId(null);
				for(int j=0;j<entry.getPurAgioEntry().size();j++){
					entry.getPurAgioEntry().get(j).setId(null);
				}
			}
		}
		if(newObjectValue instanceof SignManageInfo){
			SignManageInfo newinfo=(SignManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getSignRoomAttachmentEntry().size();i++){
				SignRoomAttachmentEntryInfo entry =newinfo.getSignRoomAttachmentEntry().get(i);
				entry.setId(null);
				for(int j=0;j<entry.getSignAgioEntry().size();j++){
					entry.getSignAgioEntry().get(j).setId(null);
				}
			}
		}
		return newObjectValue;
	}
	private BaseTransactionInfo cloneAgioEntry(BaseTransactionInfo newObjectValue){
		if(newObjectValue instanceof PrePurchaseManageInfo){
			PrePurchaseManageInfo newinfo=(PrePurchaseManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getPrePurchaseAgioEntry().size();i++){
				PrePurchaseAgioEntryInfo entry =newinfo.getPrePurchaseAgioEntry().get(i);
				entry.setId(null);
			}
		}
		if(newObjectValue instanceof PurchaseManageInfo){
			PurchaseManageInfo newinfo=(PurchaseManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getPurAgioEntry().size();i++){
				PurAgioEntryInfo entry =newinfo.getPurAgioEntry().get(i);
				entry.setId(null);
			}
		}
		if(newObjectValue instanceof SignManageInfo){
			SignManageInfo newinfo=(SignManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getSignAgioEntry().size();i++){
				SignAgioEntryInfo entry =newinfo.getSignAgioEntry().get(i);
				entry.setId(null);
			}
		}
		return newObjectValue;
	}
	private BaseTransactionInfo cloneSaleManEntry(BaseTransactionInfo newObjectValue){
		if(newObjectValue instanceof PrePurchaseManageInfo){
			PrePurchaseManageInfo newinfo=(PrePurchaseManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getPrePurchaseSaleManEntry().size();i++){
				PrePurchaseSaleManEntryInfo entry =newinfo.getPrePurchaseSaleManEntry().get(i);
				entry.setId(null);
			}
		}
		if(newObjectValue instanceof PurchaseManageInfo){
			PurchaseManageInfo newinfo=(PurchaseManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getPurSaleManEntry().size();i++){
				PurSaleManEntryInfo entry =newinfo.getPurSaleManEntry().get(i);
				entry.setId(null);
			}
		}
		if(newObjectValue instanceof SignManageInfo){
			SignManageInfo newinfo=(SignManageInfo)newObjectValue;
			for(int i=0;i<newinfo.getSignSaleManEntry().size();i++){
				SignSaleManEntryInfo entry =newinfo.getSignSaleManEntry().get(i);
				entry.setId(null);
			}
		}
		return newObjectValue;
	}
	protected void setOtherBillState(Context ctx, ChangeManageInfo info,AFMortgagedStateEnum state,boolean isAuidt) throws BOSException, EASBizException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter =new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room", info.getSrcRoom().getId()));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState", AFMortgagedStateEnum.CANCELROOM,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState", AFMortgagedStateEnum.CHANGEROOM,CompareType.NOTEQUALS));
		
		view.setFilter(filter);
		
		RoomLoanCollection rlCol=RoomLoanFactory.getLocalInstance(ctx).getRoomLoanCollection(view);
		
		view = new EntityViewInfo();
		filter =new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("room", info.getSrcRoom().getId()));
		filter.getFilterItems().add(new FilterItemInfo("joinState", AFMortgagedStateEnum.CANCELROOM,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("joinState", AFMortgagedStateEnum.CHANGEROOM,CompareType.NOTEQUALS));
		view.setFilter(filter);
		
		RoomJoinCollection rjCol=RoomJoinFactory.getLocalInstance(ctx).getRoomJoinCollection(view);
		
		view = new EntityViewInfo();
		filter =new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("room", info.getSrcRoom().getId()));
		filter.getFilterItems().add(new FilterItemInfo("propState", AFMortgagedStateEnum.CANCELROOM,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("propState", AFMortgagedStateEnum.CHANGEROOM,CompareType.NOTEQUALS));
		view.setFilter(filter);
		
		RoomPropertyBookCollection rpCol=RoomPropertyBookFactory.getLocalInstance(ctx).getRoomPropertyBookCollection(view);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		
		if(rlCol.size()>0){
			selector.add("aFMortgagedState");
			selector.add("preAfmState");
			if(isAuidt){
				rlCol.get(0).setPreAfmState(rlCol.get(0).getAFMortgagedState());
				rlCol.get(0).setAFMortgagedState(state);
			}else{
				rlCol.get(0).setAFMortgagedState(rlCol.get(0).getPreAfmState());
				rlCol.get(0).setPreAfmState(state);
			}
			RoomLoanFactory.getLocalInstance(ctx).updatePartial(rlCol.get(0), selector);
		}
		if(rjCol.size()>0){
			selector.add("joinState");
			selector.add("preJoinState");
			if(isAuidt){
				rjCol.get(0).setPreJoinState(rjCol.get(0).getJoinState());
				rjCol.get(0).setJoinState(state);
			}else{
				rjCol.get(0).setJoinState(rjCol.get(0).getPreJoinState());
				rjCol.get(0).setPreJoinState(state);
			}
			RoomJoinFactory.getLocalInstance(ctx).updatePartial(rjCol.get(0), selector);
		}
		if(rpCol.size()>0){
			selector.add("propState");
			selector.add("prePropState");
			if(isAuidt){
				rpCol.get(0).setPrePropState(rpCol.get(0).getPropState());
				rpCol.get(0).setPropState(state);
			}else{
				rpCol.get(0).setPropState(rpCol.get(0).getPrePropState());
				rpCol.get(0).setPrePropState(state);
			}
			RoomPropertyBookFactory.getLocalInstance(ctx).updatePartial(rpCol.get(0), selector);
		}
	}
	protected void delPayBill(Context ctx, ChangeManageInfo info) throws EASBizException, BOSException {
		if(info.getSheRevBill()!=null){
			SHERevBillFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(info.getSheRevBill().getId()));
		}
	}
	protected void genPayBill(Context ctx, ChangeManageInfo info) throws EASBizException, BOSException {
		if(info.getNewEntryId()!=null&&info.getSrcId()!=null){
			ObjectUuidPK pk=new ObjectUuidPK(info.getSrcId());
			ObjectUuidPK entryPk=new ObjectUuidPK(info.getNewEntryId());
			
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("*");
			selector.add("moneyDefine.*");
			selector.add("currency.*");
			IObjectValue objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(pk.getObjectType(),pk);
			IObjectValue entryObjectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(entryPk.getObjectType(),entryPk,selector);
			
			SHERevBillInfo sheRevInfo = new SHERevBillInfo();
			
			sheRevInfo.setSellProject(info.getSellProject());
			sheRevInfo.setRoom(info.getSrcRoom());
			sheRevInfo.setRelateBizType(RelatBizType.Change);
			sheRevInfo.setRevBillType(RevBillTypeEnum.gathering);
			sheRevInfo.setRelateBizBillNumber(((BaseTransactionInfo)objectValue).getNumber());
			sheRevInfo.setRelateBizBillId(info.getSrcId().toString());
			String retNumber = getNewBillNumber(ctx,sheRevInfo);
			if(retNumber!=null){
				sheRevInfo.setNumber(retNumber);
			}else{
				sheRevInfo.setNumber(((BaseTransactionInfo)objectValue).getNumber()+"-"+((TranPayListEntryInfo)entryObjectValue).getMoneyDefine().getName());
			}
			sheRevInfo.setRevAmount(((TranPayListEntryInfo)entryObjectValue).getAppAmount());
			sheRevInfo.setCustomerNames(((BaseTransactionInfo)objectValue).getCustomerNames());
			sheRevInfo.setState(FDCBillStateEnum.SUBMITTED);
			sheRevInfo.setBizDate(new Date());
			sheRevInfo.setRelateTransId(info.getTransactionID().toString());
			
			SHERevBillEntryInfo sheRevEntryInfo = new SHERevBillEntryInfo();
			sheRevEntryInfo.setMoneyDefine(((TranPayListEntryInfo)entryObjectValue).getMoneyDefine());
			sheRevEntryInfo.setRevAmount(((TranPayListEntryInfo)entryObjectValue).getAppAmount());
			
			String customerid="";
			if(objectValue instanceof PrePurchaseManageInfo){
				for(int i=0;i<((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().size();i++){
					SHERevCustEntryInfo entry=new SHERevCustEntryInfo();
					SHECustomerInfo customer=((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().get(i).getCustomer();
					entry.setSheCustomer(customer);
					sheRevInfo.getCustomerEntry().add(entry);
					if(i==((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().size()-1){
						customerid=customerid+customer.getId().toString();
					}else{
						customerid=customerid+customer.getId().toString()+",";
					}
				}
			}
			if(objectValue instanceof PurchaseManageInfo){
				for(int i=0;i<((PurchaseManageInfo)objectValue).getPurCustomerEntry().size();i++){
					SHERevCustEntryInfo entry=new SHERevCustEntryInfo();
					SHECustomerInfo customer=((PurchaseManageInfo)objectValue).getPurCustomerEntry().get(i).getCustomer();
					entry.setSheCustomer(customer);
					sheRevInfo.getCustomerEntry().add(entry);
					if(i==((PurchaseManageInfo)objectValue).getPurCustomerEntry().size()-1){
						customerid=customerid+customer.getId().toString();
					}else{
						customerid=customerid+customer.getId().toString()+",";
					}
					
				}
			}
			if(objectValue instanceof SignManageInfo){
				for(int i=0;i<((SignManageInfo)objectValue).getSignCustomerEntry().size();i++){
					SHERevCustEntryInfo entry=new SHERevCustEntryInfo();
					SHECustomerInfo customer=((SignManageInfo)objectValue).getSignCustomerEntry().get(i).getCustomer();
					entry.setSheCustomer(customer);
					sheRevInfo.getCustomerEntry().add(entry);
					if(i==((SignManageInfo)objectValue).getSignCustomerEntry().size()-1){
						customerid=customerid+customer.getId().toString();
					}else{
						customerid=customerid+customer.getId().toString()+",";
					}
				}
			}
			sheRevInfo.setCustomerIds(customerid);
			sheRevInfo.getEntrys().add(sheRevEntryInfo);
			
			SHERevBillFactory.getLocalInstance(ctx).addnew(sheRevInfo);
			
			info.setSheRevBill(sheRevInfo);
		}
	}
	protected BaseTransactionInfo delFeeEntry(Context ctx, ChangeManageInfo info) throws EASBizException, BOSException{
		ObjectUuidPK pk=new ObjectUuidPK(info.getSrcId());
		IObjectValue objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(pk.getObjectType(),pk);

		BaseTransactionInfo srcInfo=(BaseTransactionInfo)objectValue;
		srcInfo.setBizState(info.getBizState());
		
		if(srcInfo instanceof PrePurchaseManageInfo){
			if(info.getNewEntryId()!=null){
				PrePurchasePayListEntryInfo entry=PrePurchasePayListEntryFactory.getLocalInstance(ctx).getPrePurchasePayListEntryInfo(new ObjectUuidPK(info.getNewEntryId()));
				((PrePurchaseManageInfo)srcInfo).getPrePurchasePayListEntry().remove(entry);
				
				if(entry.getTanPayListEntryId()!=null){
					TranBusinessOverViewFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(entry.getTanPayListEntryId()));
				}
			}
			PrePurchaseManageFactory.getLocalInstance(ctx).update(pk,srcInfo);
		}
		if(srcInfo instanceof PurchaseManageInfo){
			if(info.getNewEntryId()!=null){
				PurPayListEntryInfo entry=PurPayListEntryFactory.getLocalInstance(ctx).getPurPayListEntryInfo(new ObjectUuidPK(info.getNewEntryId()));
				((PurchaseManageInfo)srcInfo).getPurPayListEntry().remove(entry);
			
				if(entry.getTanPayListEntryId()!=null){
					TranBusinessOverViewFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(entry.getTanPayListEntryId()));
				}
			}
			PurchaseManageFactory.getLocalInstance(ctx).update(pk,srcInfo);
		}
		if(srcInfo instanceof SignManageInfo){
			if(info.getNewEntryId()!=null){
				SignPayListEntryInfo entry=SignPayListEntryFactory.getLocalInstance(ctx).getSignPayListEntryInfo(new ObjectUuidPK(info.getNewEntryId()));
				((SignManageInfo)srcInfo).getSignPayListEntry().remove(entry);
			
				if(entry.getTanPayListEntryId()!=null){
					TranBusinessOverViewFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(entry.getTanPayListEntryId()));
				}
			}
			SignManageFactory.getLocalInstance(ctx).update(pk,srcInfo);
		}
		return srcInfo;
	}
	protected void genFeeEntry(Context ctx, ChangeManageInfo info) throws EASBizException, BOSException{
		if(info.getNewEntryId()!=null) return;

		BOSUuid newEntryid=null;
		
		ObjectUuidPK pk=new ObjectUuidPK(info.getSrcId());
		IObjectValue objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(pk.getObjectType(),pk);
		
		ObjectUuidPK tranPk=new ObjectUuidPK(((BaseTransactionInfo)objectValue).getTransactionID());
		TransactionInfo transaction=TransactionFactory.getLocalInstance(ctx).getTransactionInfo(tranPk);
		
		TranBusinessOverViewInfo bo=new TranBusinessOverViewInfo();
		BOSUuid boid=BOSUuid.create(bo.getBOSType());
		bo.setId(boid);
		bo.setType(BusTypeEnum.PAY);
		
		if(objectValue instanceof PrePurchaseManageInfo){
			//更新实收金额
			PrePurchaseManageInfo bill=(PrePurchaseManageInfo)objectValue;
			for(int i=0;i<bill.getPrePurchasePayListEntry().size();i++){
				PrePurchasePayListEntryInfo billEntry=bill.getPrePurchasePayListEntry().get(i);
				billEntry.setActRevAmount(SHEManageHelper.getActRevAmount(ctx,billEntry.getTanPayListEntryId()));
			}
			
			if(info.getMoneyDefine()!=null&&info.getFeeAmount()!=null&&info.getFeeAmount().compareTo(FDCHelper.ZERO)>0){
				PrePurchasePayListEntryInfo entry=new PrePurchasePayListEntryInfo();
				newEntryid=BOSUuid.create(entry.getBOSType());
				entry.setId(newEntryid);
				entry.setSeq(((PrePurchaseManageInfo)objectValue).getPrePurchasePayListEntry().size());
				entry.setMoneyDefine(info.getMoneyDefine());
				entry.setAppAmount(info.getFeeAmount());
				entry.setActRevAmount(info.getFeeAmount());
				entry.setCurrency(SHEManageHelper.getCurrencyInfo(ctx));
				entry.setAppDate(new Date());
				entry.setTanPayListEntryId(boid);
				((PrePurchaseManageInfo)objectValue).getPrePurchasePayListEntry().add(entry);
				
				SHEManageHelper.setPayListEntry(bo,(TranPayListEntryInfo)entry,true);
			}
		}
		if(objectValue instanceof PurchaseManageInfo){
			
			//更新实收金额
			PurchaseManageInfo bill=(PurchaseManageInfo)objectValue;
			for(int i=0;i<bill.getPurPayListEntry().size();i++){
				PurPayListEntryInfo billEntry=bill.getPurPayListEntry().get(i);
				billEntry.setActRevAmount(SHEManageHelper.getActRevAmount(ctx,billEntry.getTanPayListEntryId()));
			}
			if(info.getMoneyDefine()!=null&&info.getFeeAmount()!=null&&info.getFeeAmount().compareTo(FDCHelper.ZERO)>0){
				PurPayListEntryInfo entry=new PurPayListEntryInfo();
				newEntryid=BOSUuid.create(entry.getBOSType());
				entry.setId(newEntryid);
				entry.setSeq(((PurchaseManageInfo)objectValue).getPurPayListEntry().size());
				entry.setMoneyDefine(info.getMoneyDefine());
				entry.setAppAmount(info.getFeeAmount());
				entry.setActRevAmount(info.getFeeAmount());
				entry.setCurrency(SHEManageHelper.getCurrencyInfo(ctx));
				entry.setAppDate(new Date());
				entry.setTanPayListEntryId(boid);
				((PurchaseManageInfo)objectValue).getPurPayListEntry().add(entry);
				
				SHEManageHelper.setPayListEntry(bo,(TranPayListEntryInfo)entry,true);
			}
		}
		if(objectValue instanceof SignManageInfo){
			
			SignManageInfo bill=(SignManageInfo)objectValue;
			for(int i=0;i<bill.getSignPayListEntry().size();i++){
				SignPayListEntryInfo billEntry=bill.getSignPayListEntry().get(i);
				billEntry.setActRevAmount(SHEManageHelper.getActRevAmount(ctx,billEntry.getTanPayListEntryId()));
			}
			if(info.getMoneyDefine()!=null&&info.getFeeAmount()!=null&&info.getFeeAmount().compareTo(FDCHelper.ZERO)>0){
				SignPayListEntryInfo entry=new SignPayListEntryInfo();
				newEntryid=BOSUuid.create(entry.getBOSType());
				entry.setId(newEntryid);
				entry.setSeq(((SignManageInfo)objectValue).getSignPayListEntry().size());
				entry.setMoneyDefine(info.getMoneyDefine());
				entry.setAppAmount(info.getFeeAmount());
				entry.setActRevAmount(info.getFeeAmount());
				entry.setCurrency(SHEManageHelper.getCurrencyInfo(ctx));
				entry.setAppDate(new Date());
				entry.setTanPayListEntryId(boid);
				((SignManageInfo)objectValue).getSignPayListEntry().add(entry);
				
				SHEManageHelper.setPayListEntry(bo,(TranPayListEntryInfo)entry,true);
			}
		}
		SHEManageHelper.getBizInterface(ctx,objectValue).update(pk,(BaseTransactionInfo)objectValue);
		
		if(info.getMoneyDefine()!=null&&info.getFeeAmount()!=null&&info.getFeeAmount().compareTo(FDCHelper.ZERO)>0){
			bo.setBusinessName(bo.getMoneyDefine().getName());
			bo.setType(BusTypeEnum.PAY);
			bo.setFinishDate(bo.getAppDate());
			bo.setActualFinishDate(bo.getAppDate());
			bo.setIsFinish(true);
			transaction.getTranBusinessOverView().add(bo);
			TransactionFactory.getLocalInstance(ctx).update(tranPk, transaction);
		}
		info.setNewEntryId(newEntryid);
	}
	protected void _mark(Context ctx, List idList) throws BOSException, EASBizException {
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			ChangeManageInfo info =new ChangeManageInfo();
			String id = (String) iter.next();
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("dealState");
			info.setId(BOSUuid.read(id));
			info.setDealState(DealStateEnum.DEAL);
			_updatePartial(ctx, info, sels);
		}
	}
}