package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.io.IOException;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CluesSourceEnum;
import com.kingdee.eas.fdc.sellhouse.CluesStatusEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.ISincerityPurchase;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.TrackPhaseEnum;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;
public class SincerityPurchaseControllerBean extends
		AbstractSincerityPurchaseControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.SincerityPurchaseControllerBean");

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo) model;
		sin.setSincerityState(SincerityPurchaseStateEnum.ArrangeNum);
		//更新房间
		if(null!=sin.getRoom()){
			RoomInfo room = (RoomInfo)sin.getRoom();
			sin.setRoom(room);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",room.getId()));
			filter.getFilterItems().add(new FilterItemInfo("sellState",RoomSellStateEnum.INIT_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("sellState",RoomSellStateEnum.ONSHOW_VALUE));
			filter.setMaskString("#0 and (#1 or #2)");
			IRoom roomfac = (IRoom)RoomFactory.getLocalInstance(ctx);
			if(roomfac.exists(filter)){
//				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//				builder.appendSql(" update t_she_room set fsellstate = 'SincerPurchase' where fid ='"+room.getId().toString()+"'");
//				builder.execute();
				SelectorItemCollection selecotr = new SelectorItemCollection();
				selecotr.add("sellState");
				room.setSellState(RoomSellStateEnum.SincerPurchase);				
				roomfac.updatePartial(room, selecotr);
			}
			
//			JSONArray arr=new JSONArray();
//			JSONObject jo=new JSONObject();
//			jo.put("id", room.getId().toString());
//			jo.put("status", "排号");
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
		}
		
//		return super._submit(ctx, model);
		
		
		//生成线索客户
		if(null!=sin.getCustomer()&&sin.getCustomer().size()<=0){
			
			CluesManageInfo cluesManage = new CluesManageInfo();
	    	cluesManage.setName(sin.getAppointmentPeople());
	    	ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
			.getLocalInstance(ctx);
			OrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
			boolean existCodingRule = iCodingRuleManager.isExist(
					new FDCOrgCustomerInfo(), orgUnit.getId().toString());
			if (existCodingRule) {
				String retNumber = iCodingRuleManager.getNumber(cluesManage, orgUnit
						.getId().toString());
				cluesManage.setNumber(retNumber);
			} else {
				Timestamp time = new Timestamp(new Date().getTime());
				String  timeStr = String.valueOf(time).replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").replaceAll(".", "");
				String number = String.valueOf(timeStr)+"001";
				cluesManage.setNumber(number);
			}
//	    	cluesManage.setNumber(sin.getAppointmentPeople()+sin.getAppointmentPhone());
	    	cluesManage.setPhone(sin.getAppointmentPhone());
	    	cluesManage.setSellProject(SHEManageHelper.getParentSellProject(ctx, sin.getSellProject()));
	    	cluesManage.setSource(CluesSourceEnum.SINCERITYPRUCHASE);
	    	if(null!=sin.getSaleMansEntry()&&sin.getSaleMansEntry().size()>0){
	    		cluesManage.setPropertyConsultant(sin.getSaleMansEntry().get(0).getUser());
	    	}else{
	    		cluesManage.setPropertyConsultant(SysContext.getSysContext().getCurrentUserInfo());
	    	}
	    
	    	cluesManage.setCluesStatus(CluesStatusEnum.CUSTOMER);
	    	if(null!=sin.getId()){//修改
	    		SelectorItemCollection selec = new SelectorItemCollection();
	    		selec.add("cluesCus.name");
	    		selec.add("cluesCus.sellProject");
	    		selec.add("cluesCus.number");
	    		selec.add("cluesCus.phone");
	    		selec.add("cluesCus.id");
	    		selec.add("cluesCus.source");
	    		selec.add("cluesCus.*");
	    		selec.add("propertyConsultant.*");
				SincerityPurchaseInfo sinPurInfo = null;
				sinPurInfo =SincerityPurchaseFactory.getLocalInstance(ctx).getSincerityPurchaseInfo(new ObjectUuidPK(BOSUuid.read(sin.getId().toString())), selec);
				if(null!=sinPurInfo.getCluesCus()){

					SelectorItemCollection selecClus = new SelectorItemCollection();
					selecClus.add("name");
					selecClus.add("number");
					selecClus.add("phone");
					selecClus.add("source");
					selecClus.add("propertyConsultant");
					cluesManage.setId(sinPurInfo.getCluesCus().getId());
					CluesManageFactory.getLocalInstance(ctx).updatePartial(cluesManage, selec);
					sin.setCluesCus(cluesManage);
				}
			
	    	}else{//新增
				IObjectPK cluesId = CluesManageFactory.getLocalInstance(ctx).addnew(cluesManage);
		    	cluesManage.setId((BOSUuid)cluesId.getKeyValue(null));
		    	sin.setCluesCus(cluesManage);
			}
		}
		//业务状态
		sin.setBizState(TransactionStateEnum.BAYNUMBER);
		
		IObjectPK sinId =  super._submit(ctx, sin);
		
    	
		//交易主线
		SHEManageHelper.updateTransaction(ctx, sin,RoomSellStateEnum.SincerPurchase,true);
		
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
			sic.add("customer.isMain");
			sic.add("customer.customer.*");
			sic.add("room.productType.name");
			sic.add("saleMansEntry.user.number");
			sic.add("sincerPriceEntrys.appAmount");
			SincerityPurchaseInfo info=this.getSincerityPurchaseInfo(ctx, sinId,sic);
			
			JSONArray carr=new JSONArray();
			JSONObject cjo=new JSONObject();
			cjo.put("id", info.getNumber().toString());
			cjo.put("type", "1");
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
			
			for(int i=0;i<info.getCustomer().size();i++){
				if(i==0){
					customerId=info.getCustomer().get(i).getCustomer().getNumber();
				}else{
					customerId=customerId+"@"+info.getCustomer().get(i).getCustomer().getNumber();
				}
				if("true".equals(paramValue)){
					if(info.getCustomer().get(i).isIsMain()){
						quc=info.getCustomer().get(i).getCustomer();
					}
				}else{
					if(info.getCustomer().get(i).getCustomer().getFirstDate()==null&&info.getCustomer().get(i).getCustomer().getReportDate()==null){
						throw new EASBizException(new NumericExceptionSubItem("100","客户报备日期和首访日期都为空！"));
					}
					if(qudate==null){
						if(info.getCustomer().get(i).getCustomer().getReportDate()!=null){
							qudate=info.getCustomer().get(i).getCustomer().getReportDate();
						}else{
							qudate=info.getCustomer().get(i).getCustomer().getFirstDate();
						}
						quc=info.getCustomer().get(i).getCustomer();
					}else{
						Timestamp comdate=null;
						if(info.getCustomer().get(i).getCustomer().getReportDate()!=null){
							comdate=info.getCustomer().get(i).getCustomer().getReportDate();
						}else{
							comdate=info.getCustomer().get(i).getCustomer().getFirstDate();
						}
						if(qudate.after(comdate)){
							qudate=comdate;
							quc=info.getCustomer().get(i).getCustomer();
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
			cjo.put("roomStatus", "排号");
			cjo.put("userId", info.getSaleMansEntry().get(0).getUser().getNumber());
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cjo.put("tradeDate", df.format(info.getBizDate()));
			if(info.getSincerPriceEntrys().size()>0){
				cjo.put("tradeAmount",info.getSincerPriceEntrys().get(0).getAppAmount());
			}else{
				cjo.put("tradeAmount", "");
			}
			cjo.put("payType", "");
			cjo.put("expectDate", "");
			cjo.put("price", "");
			cjo.put("area", "");
			cjo.put("contractNo", "");
			cjo.put("rengouId", "");
			cjo.put("remark", "");
			cjo.put("orderType", "");
			cjo.put("saleType", "");
			cjo.put("rcId", "");
			cjo.put("salesStatisticsDate", "");
			cjo.put("fyyt", "");
			
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
		return sinId ;
		//return ((IObjectPK)new ObjectUuidPK(sin.getId()));
	}

	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo) model;
//		sin.setSincerityState(SincerityPurchaseStateEnum.ArrangeNum);
		//更新房间
		if(null!=sin.getRoom()){
			SelectorItemCollection selecotr = new SelectorItemCollection();
			selecotr.add("sellState");
			RoomInfo room = (RoomInfo)sin.getRoom();
			room.setSellState(RoomSellStateEnum.SincerPurchase);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",room.getId()));
			filter.getFilterItems().add(new FilterItemInfo("sellState",RoomSellStateEnum.INIT_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("sellState",RoomSellStateEnum.ONSHOW_VALUE));
			filter.setMaskString("#0 and (#1 or #2)");
			IRoom roomfac = (IRoom)RoomFactory.getLocalInstance(ctx);
			if(roomfac.exists(filter)){
				roomfac.updatePartial(room, selecotr);
				
			}
		}
		sin.setBizState(TransactionStateEnum.BAYNUMBER);
		IObjectPK sinId = super._save(ctx, model);
		
		String	param="false";
		try {
			param = ParamControlFactory.getLocalInstance(ctx).getParamValue(new ObjectUuidPK(ContextUtil.getCurrentOrgUnit(ctx).getId()), "YF_WF");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if("true".equals(param)){
			//交易主线
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("*");
			sic.add("sellProject.number");
			sic.add("room.number");
			sic.add("customer.isMain");
			sic.add("customer.customer.*");
			sic.add("room.productType.name");
			sic.add("saleMansEntry.user.number");
			sic.add("sincerPriceEntrys.appAmount");
			SincerityPurchaseInfo info=this.getSincerityPurchaseInfo(ctx, sinId,sic);
			
			JSONArray carr=new JSONArray();
			JSONObject cjo=new JSONObject();
			cjo.put("id", info.getNumber().toString());
			cjo.put("type", "1");
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
			
			for(int i=0;i<info.getCustomer().size();i++){
				if(i==0){
					customerId=info.getCustomer().get(i).getCustomer().getNumber();
				}else{
					customerId=customerId+"@"+info.getCustomer().get(i).getCustomer().getNumber();
				}
				if("true".equals(paramValue)){
					if(info.getCustomer().get(i).isIsMain()){
						quc=info.getCustomer().get(i).getCustomer();
					}
				}else{
					if(info.getCustomer().get(i).getCustomer().getFirstDate()==null&&info.getCustomer().get(i).getCustomer().getReportDate()==null){
						throw new EASBizException(new NumericExceptionSubItem("100","客户报备日期和首访日期都为空！"));
					}
					if(qudate==null){
						if(info.getCustomer().get(i).getCustomer().getReportDate()!=null){
							qudate=info.getCustomer().get(i).getCustomer().getReportDate();
						}else{
							qudate=info.getCustomer().get(i).getCustomer().getFirstDate();
						}
						quc=info.getCustomer().get(i).getCustomer();
					}else{
						Timestamp comdate=null;
						if(info.getCustomer().get(i).getCustomer().getReportDate()!=null){
							comdate=info.getCustomer().get(i).getCustomer().getReportDate();
						}else{
							comdate=info.getCustomer().get(i).getCustomer().getFirstDate();
						}
						if(qudate.after(comdate)){
							qudate=comdate;
							quc=info.getCustomer().get(i).getCustomer();
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
			cjo.put("roomStatus", "排号");
			cjo.put("userId", info.getSaleMansEntry().get(0).getUser().getNumber());
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cjo.put("tradeDate", df.format(info.getBizDate()));
			if(info.getSincerPriceEntrys().size()>0){
				cjo.put("tradeAmount",info.getSincerPriceEntrys().get(0).getAppAmount());
			}else{
				cjo.put("tradeAmount", "");
			}
			cjo.put("payType", "");
			cjo.put("expectDate", "");
			cjo.put("price", "");
			cjo.put("area", "");
			cjo.put("contractNo", "");
			cjo.put("rengouId", "");
			cjo.put("remark", "");
			cjo.put("orderType", "");
			cjo.put("saleType", "");
			cjo.put("rcId", "");
			cjo.put("salesStatisticsDate", "");
			cjo.put("fyyt", "");
			
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
		
//		JSONArray arr=new JSONArray();
//		JSONObject jo=new JSONObject();
//		jo.put("id", info.getRoom().getId().toString());
//		jo.put("status", "排号");
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
		
		return sinId;
	}
	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		FDCBillInfo billInfo = ((FDCBillInfo) model);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("number", billInfo.getNumber()));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
								.getId()));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}

	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		SincerityPurchaseInfo info = (SincerityPurchaseInfo) getValue(ctx, pk);
		FilterInfo filter = new FilterInfo();
		this.exists(ctx, filter);
//		SHEManageHelper.updateCommerceChanceStatus(ctx,info.getTransactionID().toString());
		SHEManageHelper.toOldTransaction(ctx,info.getTransactionID());
		
		super._delete(ctx, pk);
		
	}
	protected void _submitSincer(Context ctx, IObjectCollection sincerityColl)
			throws BOSException, EASBizException {		
		SincerityPurchaseCollection sinColl = (SincerityPurchaseCollection)sincerityColl;
		for(int i=0;i<sinColl.size();i++)
		{
				this._submit(ctx, sinColl.getObject(i));		
		}
	}

	protected void _quitNum(Context ctx, IObjectValue sinPurInfo, IObjectCollection selectorCollection) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo)sinPurInfo;
		SelectorItemCollection selc = (SelectorItemCollection)selectorCollection;
		SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(sin,
				selc);
//		SHEManageHelper.updateCommerceChanceStatus(ctx,sin.getTransactionID().toString());
		SHEManageHelper.toOldTransaction(ctx,sin.getTransactionID());
	}

}