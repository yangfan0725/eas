package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
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
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.PriceSetSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class BuildingControllerBean extends AbstractBuildingControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.BuildingControllerBean");

	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		BuildingInfo building = (BuildingInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(
				IFWEntityStruct.dataBase_Number, building.getNumber(),
				CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (building.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID,
					building.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		filterItem = new FilterItemInfo("sellProject.id", building
				.getSellProject().getId().toString());
		filter.getFilterItems().add(filterItem);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));

		if (super._exists(ctx, filter)) {
			String number = this._getPropertyAlias(ctx, building,
					IFWEntityStruct.dataBase_Number)
					+ building.getNumber();
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { number });
		}
	}

    protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
    	for (int i = 0; i < arrayPK.length; i++) {
    		DataBaseCodeRuleHelper.recycleNumber(ctx, (DataBaseInfo) _getValue(ctx, arrayPK[i]));
		}
    	super._delete(ctx, arrayPK);
    }
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
    	
    	DataBaseCodeRuleHelper.recycleNumber(ctx, (DataBaseInfo) _getValue(ctx, pk));
    	super._delete(ctx, pk);
    }
	
	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		BuildingInfo building = (BuildingInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(
				IFWEntityStruct.dataBase_Name, building.getName(),
				CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (building.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID,
					building.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		filterItem = new FilterItemInfo("sellProject.id", building
				.getSellProject().getId().toString());
		filter.getFilterItems().add(filterItem);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));

		if (super._exists(ctx, filter)) {
			String name = this._getPropertyAlias(ctx, building,
					IFWEntityStruct.dataBase_Name)
					+ building.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED,
					new Object[] { name });
		}
	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		DataBaseCodeRuleHelper.handleIntermitNumber(ctx, (DataBaseInfo)model);
		IObjectPK pk= super._submit(ctx, model);
		FDCSQLBuilder builder1=new FDCSQLBuilder(ctx);
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date();
		String sendTime = sdf.format(date).toString();
		String connectId=null;
		String connectName=null;
		String timestamp = null;
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("number");
		sic.add("name");
		sic.add("sellProject.*");
		sic.add("productType.number");
		sic.add("productType.name");
		sic.add("isReturn");
		sic.add("id");
		
		BuildingInfo info=this.getBuildingInfo(ctx, pk,sic);
		 
		String	param="false";
		try {
			param = ParamControlFactory.getLocalInstance(ctx).getParamValue(new ObjectUuidPK(ContextUtil.getCurrentOrgUnit(ctx).getId()), "YF_WF");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if("true".equals(param)){
			JSONArray arr=new JSONArray();
			
			JSONObject json=new JSONObject();
			 json.put("type", "3");
			 json.put("id", info.getSellProject().getNumber()+info.getNumber().toString());
			 
			 JSONObject jo=new JSONObject();
			 jo.put("name", info.getName());
			 jo.put("stageId", info.getSellProject().getNumber());
			 jo.put("zcpxl", "");
			 jo.put("cplxt", info.getProductType().getName());
			 jo.put("cplx", info.getProductType().getNumber());
			 json.put("building",  jo.toJSONString());
			 
			 arr.add(json);
			 
			 try {
					String rs1=SHEManageHelper.execPost(ctx, "jd_project_sync_channel", arr.toJSONString());
					JSONObject rso = JSONObject.parseObject(rs1);
					if(!"SUCCESS".equals(rso.getString("status"))){
						throw new EASBizException(new NumericExceptionSubItem("100",rso.getString("message")));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
//		to_yb
		 String	param1="false";
			try {
				param1 = ParamControlFactory.getLocalInstance(ctx).getParamValue(new ObjectUuidPK(ContextUtil.getCurrentOrgUnit(ctx).getId()), "YF_YB");
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if("true".equals(param1)){
				
//				to_yb
				 JSONObject initjson=new JSONObject();
				 JSONObject esbjson=new JSONObject();
				 String instId=null;
				 String requestTime=null;
				 JSONObject datajson=new JSONObject();
				 JSONArray ybarr=new JSONArray();
				 JSONObject ybjson=new JSONObject();
//			上次返回在请求时间
				 builder1.clear();
				 builder1.appendSql(" select instId,requestTime from esbInfo where source='building'");
				 IRowSet rs3=builder1.executeQuery();
				 try {
					while(rs3.next()){
						  instId=rs3.getString("instId");
						  requestTime=rs3.getString("requestTime");
					 }
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				 if(instId!=null){
					 esbjson.put("instId",instId);
				 }
				 if(requestTime!=null){
					 esbjson.put("requestTime",requestTime);
				 }
				 
//					上次返回在时间戳
				 builder1.clear();
				 builder1.appendSql(" select ybtime from ybTimeRecord where source='building'");
					IRowSet rs4=builder1.executeQuery();
					try {
						if(rs4.first()&&rs4!=null){
						 timestamp=rs4.getString("ybtime");
						}else{
							timestamp="";
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			 ybjson.put("projectId", info.getSellProject().getParent().getId().toString());
			 ybjson.put("projectCode", info.getSellProject().getParent().getNumber());
			 ybjson.put("projectName", info.getSellProject().getParent().getName());
			 ybjson.put("stageId", info.getSellProject().getId().toString());
			 ybjson.put("stageCode", info.getSellProject().getNumber());
			 ybjson.put("stageName", info.getSellProject().getName());
			 ybjson.put("buildingId", info.getId().toString());
			 connectId= info.getId().toString();
			 connectName=info.getName();
			 ybjson.put("buildingName", info.getName());
			 ybjson.put("productType", info.getProductType().getName());
			 ybjson.put("status", "1");

			 builder.clear();
				builder.appendSql("/*dialect*/  insert into ebeilog(sendtime,sendData,connectId,connectName) values('"+sendTime+"','"+ybjson.toString()+"','"+connectId+"','"+connectName+"')");
				builder.execute();
			 ybarr.add(ybjson);
			 
			 datajson.put("datas",ybarr);
				datajson.put("timestamp",timestamp);
				initjson.put("esbInfo", esbjson);
				initjson.put("requestInfo",datajson);
		try {
//			System.out.println(initjson.toJSONString());
			String rs11=SHEManageHelper.execPostYBBuilding(ctx, initjson.toJSONString(),timestamp);
				JSONObject rso = JSONObject.parseObject(rs11);
				if(rso!=null){
				if(!"A0200".equals(rso.getJSONObject("esbInfo").getString("returnCode"))){
					String errorMsg =rso.getJSONObject("esbInfo").getString("returnMsg");
					String info1="来自一碑报错信息：";
					String sb = String.valueOf(new StringBuffer().append(errorMsg).append(info1));
					throw new EASBizException(new NumericExceptionSubItem("100",sb));
				}else{
					JSONObject rst=rso.getJSONObject("esbInfo");
					 instId=rst.getString("instId");
					 requestTime=rst.getString("requestTime");
					 builder1.clear();
					 builder1.appendSql(" update esbInfo set instId='"+instId+"',requestTime='"+requestTime+"' where source='building'");
					 builder1.executeUpdate();
					 JSONObject rst1=rso.getJSONObject("resultInfo");
					 String ts=rst1.getString("timestamp");
					 builder1.clear();
					 builder1.appendSql("update ybTimeRecord set ybTime='"+ts+"' where source='building' ");
					 builder1.executeUpdate();
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			}
		
		
		//检查是否返租
        builder.clear();
        builder.appendSql("update t_she_room set FIsReturn = ? where fbuildingid = ?");
        builder.addParam(info.isIsReturn()?1:0);
        builder.addParam(info.getId().toString());
        builder.executeUpdate();
		
		return pk;
	}
}