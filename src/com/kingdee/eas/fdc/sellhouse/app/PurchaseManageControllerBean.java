package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.util.NumericExceptionSubItem;
public class PurchaseManageControllerBean extends AbstractPurchaseManageControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.PurchaseManageControllerBean");


    protected void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	PurchaseManageInfo info=new PurchaseManageInfo();
    	SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("state");
		info.setId(billId);
		info.setBizState(TransactionStateEnum.PURAPPLE);
		info.setState(FDCBillStateEnum.SUBMITTED);
		_updatePartial(ctx, info, selector);
	}
    protected void _setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	PurchaseManageInfo info=new PurchaseManageInfo();
    	SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		info.setId(billId);
		info.setBizState(TransactionStateEnum.PURAUDITING);
		info.setState(FDCBillStateEnum.AUDITTING);
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime(new Date());
		_updatePartial(ctx, info, selector);
	}

    protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	PurchaseManageInfo info = ((PurchaseManageInfo) model);
		if(info.getBizState() == null)info.setBizState(TransactionStateEnum.PURSAVED);
		super._save(ctx, pk, model);
	}
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		PurchaseManageInfo info = ((PurchaseManageInfo) model);
		if(info.getBizState() == null)info.setBizState(TransactionStateEnum.PURSAVED);
		return super._save(ctx, model);
	}
	protected void _invalid(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	SHEManageHelper.checkHasSHERevBill(ctx,billId.toString(),"作废");
    	
		PurchaseManageInfo info=this.getPurchaseManageInfo(ctx, new ObjectUuidPK(billId));
		SelectorItemCollection selector = new SelectorItemCollection();
		
		selector.add("bizState");
		selector.add("state");
		selector.add("isValid");
		info.setState(FDCBillStateEnum.INVALID);
		info.setBizState(TransactionStateEnum.PURNULLIFY);
		info.setIsValid(true);
		_updatePartial(ctx, info, selector);
		
		SHEManageHelper.updatePayActRevAmountAndBizState(ctx, info.getSrcId(), true, TransactionStateEnum.TOPUR);
		SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),true,RoomSellStateEnum.Purchase);
	
		RoomSellStateEnum roomState=SHEManageHelper.toOldTransaction(ctx,info.getTransactionID());
		
		if(roomState!=null){
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("sellState");
			for(int i=0;i<info.getPurRoomAttachmentEntry().size();i++){
				RoomInfo attRoom=info.getPurRoomAttachmentEntry().get(i).getRoom();
				attRoom.setSellState(roomState);
				RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, sels);
			}
		}
	}
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		ObjectUuidPK pk=new ObjectUuidPK(billId);
		PurchaseManageInfo info=(PurchaseManageInfo)this.getValue(ctx, pk);
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime(new Date());
		info.setBizState(TransactionStateEnum.PURAUDIT);
		info.setState(FDCBillStateEnum.AUDITTED);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		_updatePartial(ctx, info, selector);
		
//		JSONArray arr=new JSONArray();
//		JSONObject jo=new JSONObject();
//		jo.put("id", info.getRoom().getId().toString());
//		jo.put("status", "认购");
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
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		checkChangeManage(ctx,billId.toString());
		
		ObjectUuidPK pk=new ObjectUuidPK(billId);
		PurchaseManageInfo info=(PurchaseManageInfo)this.getValue(ctx, pk);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setBizState(TransactionStateEnum.PURAPPLE);
		info.setState(FDCBillStateEnum.SUBMITTED);
		
		_updatePartial(ctx, info, selector);
	}
	private IObjectPK submitAction(Context ctx, IObjectValue model) throws EASBizException, BOSException{
		IObjectPK pk=null;
		PurchaseManageInfo info=(PurchaseManageInfo)model;
		info.setBizState(TransactionStateEnum.PURAPPLE);
		RoomInfo room=info.getRoom();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("sellState");
		room.setSellState(RoomSellStateEnum.Purchase);
		RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
		
		for(int i=0;i<info.getPurRoomAttachmentEntry().size();i++){
			RoomInfo attRoom=info.getPurRoomAttachmentEntry().get(i).getRoom();
			attRoom.setSellState(RoomSellStateEnum.Purchase);
			RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, sels);
		}
		SHEManageHelper.updatePayActRevAmountAndBizState(ctx, info.getSrcId(), false, TransactionStateEnum.TOPUR);
		SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),false,RoomSellStateEnum.Purchase);
		pk=super._submit(ctx, model);
		SHEManageHelper.updateTransaction(ctx, info,RoomSellStateEnum.Purchase,true);
		
		
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
			sic.add("purCustomerEntry.isMain");
			sic.add("purCustomerEntry.customer.*");
			sic.add("room.productType.name");
			sic.add("payType.name");
			sic.add("purSaleManEntry.user.number");
			info=this.getPurchaseManageInfo(ctx, pk,sic);
			
			JSONArray carr=new JSONArray();
			JSONObject cjo=new JSONObject();
			cjo.put("id", info.getNumber().toString());
			cjo.put("type", "2");
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
			
			for(int i=0;i<info.getPurCustomerEntry().size();i++){
				if(i==0){
					customerId=info.getPurCustomerEntry().get(i).getCustomer().getNumber();
				}else{
					customerId=customerId+"@"+info.getPurCustomerEntry().get(i).getCustomer().getNumber();
				}
				if("true".equals(paramValue)){
					if(info.getPurCustomerEntry().get(i).isIsMain()){
						quc=info.getPurCustomerEntry().get(i).getCustomer();
					}
				}else{
					if(info.getPurCustomerEntry().get(i).getCustomer().getFirstDate()==null&&info.getPurCustomerEntry().get(i).getCustomer().getReportDate()==null){
						throw new EASBizException(new NumericExceptionSubItem("100","客户报备日期和首访日期都为空！"));
					}
					if(qudate==null){
						if(info.getPurCustomerEntry().get(i).getCustomer().getReportDate()!=null){
							qudate=info.getPurCustomerEntry().get(i).getCustomer().getReportDate();
						}else{
							qudate=info.getPurCustomerEntry().get(i).getCustomer().getFirstDate();
						}
						quc=info.getPurCustomerEntry().get(i).getCustomer();
					}else{
						Timestamp comdate=null;
						if(info.getPurCustomerEntry().get(i).getCustomer().getReportDate()!=null){
							comdate=info.getPurCustomerEntry().get(i).getCustomer().getReportDate();
						}else{
							comdate=info.getPurCustomerEntry().get(i).getCustomer().getFirstDate();
						}
						if(qudate.after(comdate)){
							qudate=comdate;
							quc=info.getPurCustomerEntry().get(i).getCustomer();
						}
					}
				}
			}
			cjo.put("qdCustId",quc.getNumber());
			cjo.put("customerId", customerId);
			if(info.getRoom()!=null){
				cjo.put("roomId", info.getRoom().getNumber());
				cjo.put("rcYT", info.getRoom().getProductType().getName());
			}
			cjo.put("roomStatus", "认购");
			cjo.put("userId", info.getPurSaleManEntry().get(0).getUser().getNumber());
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cjo.put("tradeDate", df.format(info.getBizDate()));
			cjo.put("tradeAmount", info.getDealTotalAmount());
			cjo.put("payType", info.getPayType().getName());
			cjo.put("expectDate", df.format(info.getPlanSignDate()));
			cjo.put("price", info.getDealBuildPrice());
			cjo.put("area",info.getBulidingArea());
			cjo.put("contractNo", info.getNumber());
			cjo.put("rengouId", "");
			cjo.put("remark", "");
			cjo.put("orderType", "");
			cjo.put("saleType", "");
			
			if(PurChangeStateEnum.CHANGE.equals(info.getChangeState())){
				cjo.put("isChangeRoomNew", "1");
			}else{
				cjo.put("isChangeRoomNew", "0");
			}
			
			sic=new SelectorItemCollection();
			sic.add("number");
			if(info.getSrcId()!=null){
				SincerityPurchaseInfo src=SincerityPurchaseFactory.getLocalInstance(ctx).getSincerityPurchaseInfo(new ObjectUuidPK(info.getSrcId()),sic);
				cjo.put("rcId", src.getNumber());
			}
			cjo.put("salesStatisticsDate", "");
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
		PurchaseManageInfo info=(PurchaseManageInfo)getValue(ctx, pk);
		
		if(TransactionStateEnum.PURAPPLE.equals(info.getBizState())||
				TransactionStateEnum.PURNULLIFY.equals(info.getBizState())){
			SHEManageHelper.checkHasSHERevBill(ctx,pk.toString(),"删除");
		}
		RoomSellStateEnum roomState=null;
		if(!TransactionStateEnum.PURNULLIFY.equals(info.getBizState())&&!TransactionStateEnum.PURSAVED.equals(info.getBizState())){
			roomState=SHEManageHelper.toOldTransaction(ctx,info.getTransactionID());
		}
		if(TransactionStateEnum.PURAPPLE.equals(info.getBizState())){
			SHEManageHelper.updatePayActRevAmountAndBizState(ctx, info.getSrcId(), true, TransactionStateEnum.TOPUR);
			SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),true,RoomSellStateEnum.Purchase);
			
			if(roomState!=null){
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("sellState");
				for(int i=0;i<info.getPurRoomAttachmentEntry().size();i++){
					RoomInfo attRoom=info.getPurRoomAttachmentEntry().get(i).getRoom();
					attRoom.setSellState(roomState);
					RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, sels);
				}
			}
		}
		super._delete(ctx, pk);
		
	}
}