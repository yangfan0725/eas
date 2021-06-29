package com.kingdee.eas.fdc.sellhouse.app;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
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

import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataCollection;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataFactory;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SexEnum;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class WSSellHouseFacadeControllerBean extends AbstractWSSellHouseFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.WSSellHouseFacadeControllerBean");

	protected String _sysCustomer(Context ctx, String str) throws BOSException,EASBizException {
//		JSONObject obj = JSONObject.fromObject(str);
//		
//		JSONObject rs = new JSONObject();
//		
//		try {
//			if(obj.get("number")==null||"".equals(obj.getString("number").trim())){
//				rs.put("state", "0");
//				rs.put("msg", "编码不能为空！");
//				
//				FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
//				sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步接口','"+str+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
//				sql.execute();
//				return rs.toString();
//			}
//			if(obj.get("id")==null||"".equals(obj.getString("id").trim())){
//				rs.put("state", "0");
//				rs.put("msg", "id不能为空！");
//				FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
//				sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步接口','"+str+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
//				sql.execute();
//				return rs.toString();
//			}
//			if(obj.get("name")==null||"".equals(obj.getString("name").trim())){
//				rs.put("state", "0");
//				rs.put("msg", "名称不能为空！");
//				FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
//				sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步接口','"+str+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
//				sql.execute();
//				return rs.toString();
//			}
//			SHECustomerInfo cus=null;
//			SHECustomerCollection shecol=SHECustomerFactory.getLocalInstance(ctx).getSHECustomerCollection("select * from where number='"+obj.getString("number")+"'");
//			if(shecol.size()>0){
//				cus=shecol.get(0);
//			}else{
//				cus=new SHECustomerInfo();
//			}
//			cus.setIsEnabled(true);
//			cus.setIsPublic(false);
//			SellProjectCollection spCol=SellProjectFactory.getLocalInstance(ctx).getSellProjectCollection("select *,orgUnit.* from where number='"+obj.getString("sellProject")+"' and parent.id is null");
//			if(spCol.size()>0){
//				cus.setSellProject(spCol.get(0));
//				cus.setCreateUnit(spCol.get(0).getOrgUnit());
//			}else{
//				rs.put("state", "0");
//				rs.put("msg", "项目不存在或者不为根项目！");
//				FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
//				sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步接口','"+str+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
//				sql.execute();
//				return rs.toString();
//			}
//			cus.setSimpleName(obj.getString("id"));
//			cus.setNumber(obj.getString("number"));
//			cus.setName(obj.getString("name"));
//			if(obj.getString("type").equals("0")){
//				cus.setCustomerType(CustomerTypeEnum.PERSONALCUSTOMER);
//			}else{
//				cus.setCustomerType(CustomerTypeEnum.ENTERPRICECUSTOMER);
//				if(obj.get("creditCode")!=null)
//					cus.setCreditCode(obj.getString("creditCode"));
//				if(obj.get("bank")!=null)
//					cus.setBank(obj.getString("bank"));
//				if(obj.get("bankAccount")!=null)
//					cus.setBankAccount(obj.getString("bankAccount"));
//				if(obj.get("legalPerson")!=null)
//					cus.setLegalPerson(obj.getString("legalPerson"));
//			}
//			if(obj.getString("sex").equals("0")){
//				cus.setSex(SexEnum.Mankind);
//			}else{
//				cus.setSex(SexEnum.Womenfolk);
//			}
//			if(obj.get("birth")!=null&&!"".equals(obj.getString("birth").trim())){
//				cus.setDayOfbirth(FDCDateHelper.stringToDate(obj.getString("birth")));
//			}
//			if(obj.get("insiderCode")!=null&&!"".equals(obj.getString("insiderCode").trim())){
//				cus.setInsiderCode(obj.getString("insiderCode"));
//			}
//			if(obj.get("phone")!=null)
//				cus.setPhone(obj.getString("phone"));
//			if(obj.get("tel")!=null)
//				cus.setTel(obj.getString("tel"));
//			if(obj.get("officeTel")!=null)
//				cus.setOfficeTel(obj.getString("officeTel"));
//			
//			if(obj.get("certificateType")!=null&&!"".equals(obj.getString("certificateType").trim())){
//				FDCCusBaseDataCollection ct=FDCCusBaseDataFactory.getLocalInstance(ctx).getFDCCusBaseDataCollection("select * from where name='"+obj.getString("certificateType")+"'");
//				if(ct.size()>0){
//					cus.setCertificateType(ct.get(0));
//				}else{
//					rs.put("state", "0");
//					rs.put("msg", "证件类型不存在！");
//					FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
//					sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步接口','"+str+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
//					sql.execute();
//					return rs.toString();
//				}
//				cus.setCertificateNumber(obj.getString("certificateNumber"));
//			}
//			if(obj.get("mailAddress")!=null)
//				cus.setMailAddress(obj.getString("mailAddress"));
//			
//			if(obj.get("bookedAddress")!=null){
//				cus.setBookedAddress(obj.getString("bookedAddress"));
//			}
//			UserCollection user=UserFactory.getLocalInstance(ctx).getUserCollection("select * from where number='"+obj.getString("saleMan")+"'");
//			if(user.size()>0){
//				cus.setFirstConsultant(user.get(0));
//				cus.setPropertyConsultant(user.get(0));
//			}else{
//				rs.put("state", "0");
//				rs.put("msg", "置业顾问不存在！");
//				FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
//				sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步接口','"+str+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
//				sql.execute();
//				return rs.toString();
//			}
//			if(obj.get("firstDate")!=null&&!"".equals(obj.getString("firstDate").trim())){
//				cus.setFirstDate(Timestamp.valueOf(obj.getString("firstDate")));
//			}
//			if(obj.get("latestDate")!=null&&!"".equals(obj.getString("latestDate").trim())){
//				cus.setLatestDate(Timestamp.valueOf(obj.getString("latestDate")));
//			}
//			if(obj.get("reportDate")!=null&&!"".equals(obj.getString("reportDate").trim())){
//				cus.setReportDate(Timestamp.valueOf(obj.getString("reportDate")));
//			}
//			cus.setLevel(obj.getString("level"));
//			cus.setOneQd(obj.getString("oneQd"));
//			cus.setTwoQd(obj.getString("twoQd"));
//			
//			if(obj.get("qdPerson")!=null&&!"".equals(obj.getString("qdPerson").trim())){
//				cus.setQdPersontxt(obj.getString("qdPerson"));
//				cus.setQdDate(Timestamp.valueOf(obj.getString("qdDate")));
//			}
//			if(obj.get("recommend")!=null&&!"".equals(obj.getString("recommend").trim())){
//				cus.setRecommended(obj.getString("recommend"));
//				cus.setRecommendDate(Timestamp.valueOf(obj.getString("recommendDate")));
//			}
//			SHECustomerFactory.getLocalInstance(ctx).submit(cus);
//			
//			
//		} catch (Exception e) {
//			rs.put("state", "0");
//			rs.put("msg", e.getMessage());
//			
//			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
//			sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步接口','"+str+"','"+obj.get("number")+"',now(),'失败','"+ e.getMessage()+"')");
//			sql.execute();
//			return rs.toString();
//		}
//		rs.put("state", "1");
//		rs.put("msg", "同步成功！");
//		
//		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
//		sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步接口','"+str+"','"+obj.get("number")+"',now(),'成功','')");
//		sql.execute();
//		return rs.toString();
		
		JSONArray arr = JSONArray.fromObject(str);
		JSONArray arrrs = new JSONArray();
		JSONObject rs = new JSONObject();
		try {
			
			for(int i=0;i<arr.size();i++){
				rs = new JSONObject();
				JSONObject obj=arr.getJSONObject(i);
				if(obj.get("number")==null||"".equals(obj.getString("number").trim())){
					rs.put("state", "0");
					rs.put("msg", "编码不能为空！");
					arrrs.add(rs);
					
					continue;
				}
				if(obj.get("id")==null||"".equals(obj.getString("id").trim())){
					rs.put("number", obj.getString("number"));
					rs.put("state", "0");
					rs.put("msg", "id不能为空！");
					arrrs.add(rs);
					
					FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
					sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步接口','"+obj+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
					sql.execute();
					
					continue;
				}
				if(obj.get("name")==null||"".equals(obj.getString("name").trim())){
					rs.put("number", obj.getString("number"));
					rs.put("state", "0");
					rs.put("msg", "名称不能为空！");
					arrrs.add(rs);
					
					FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
					sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步接口','"+obj+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
					sql.execute();
					
					continue;
				}
				SHECustomerInfo cus=null;
				SHECustomerCollection shecol=SHECustomerFactory.getLocalInstance(ctx).getSHECustomerCollection("select * from where number='"+obj.getString("number")+"'");
				if(shecol.size()>0){
					cus=shecol.get(0);
				}else{
					cus=new SHECustomerInfo();
					cus.setIsPublic(false);
				}
				cus.setIsEnabled(true);
				SellProjectCollection spCol=SellProjectFactory.getLocalInstance(ctx).getSellProjectCollection("select *,orgUnit.* from where number='"+obj.getString("sellProject")+"' and parent.id is null");
				if(spCol.size()>0){
					cus.setSellProject(spCol.get(0));
					cus.setCreateUnit(spCol.get(0).getOrgUnit());
				}else{
					rs.put("number", obj.getString("number"));
					rs.put("state", "0");
					rs.put("msg", "项目不存在或者不为根项目！");
					arrrs.add(rs);
					
					FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
					sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步接口','"+obj+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
					sql.execute();
					
					continue;
				}
				cus.setSimpleName(obj.getString("id"));
				cus.setNumber(obj.getString("number"));
				cus.setName(obj.getString("name"));
				if(obj.getString("type").equals("0")){
					cus.setCustomerType(CustomerTypeEnum.PERSONALCUSTOMER);
				}else{
					cus.setCustomerType(CustomerTypeEnum.ENTERPRICECUSTOMER);
					if(obj.get("creditCode")!=null)
						cus.setCreditCode(obj.getString("creditCode"));
					if(obj.get("bank")!=null)
						cus.setBank(obj.getString("bank"));
					if(obj.get("bankAccount")!=null)
						cus.setBankAccount(obj.getString("bankAccount"));
					if(obj.get("legalPerson")!=null)
						cus.setLegalPerson(obj.getString("legalPerson"));
				}
				if(obj.getString("sex").equals("0")){
					cus.setSex(SexEnum.Mankind);
				}else{
					cus.setSex(SexEnum.Womenfolk);
				}
				if(obj.get("birth")!=null&&!"".equals(obj.getString("birth").trim())){
					cus.setDayOfbirth(FDCDateHelper.stringToDate(obj.getString("birth")));
				}
				if(obj.get("insiderCode")!=null&&!"".equals(obj.getString("insiderCode").trim())){
					cus.setInsiderCode(obj.getString("insiderCode"));
				}
				if(obj.get("phone")!=null)
					cus.setPhone(obj.getString("phone"));
				if(obj.get("tel")!=null)
					cus.setTel(obj.getString("tel"));
				if(obj.get("officeTel")!=null)
					cus.setOfficeTel(obj.getString("officeTel"));
				
				if(obj.get("certificateType")!=null&&!"".equals(obj.getString("certificateType").trim())){
					FDCCusBaseDataCollection ct=FDCCusBaseDataFactory.getLocalInstance(ctx).getFDCCusBaseDataCollection("select * from where name='"+obj.getString("certificateType")+"'");
					if(ct.size()>0){
						cus.setCertificateType(ct.get(0));
					}else{
						rs.put("number", obj.getString("number"));
						rs.put("state", "0");
						rs.put("msg", "证件类型不存在！");
						arrrs.add(rs);
						
						FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
						sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步接口','"+obj+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
						sql.execute();
						
						continue;
					}
					cus.setCertificateNumber(obj.getString("certificateNumber"));
				}
				if(obj.get("mailAddress")!=null)
					cus.setMailAddress(obj.getString("mailAddress"));
				
				if(obj.get("bookedAddress")!=null){
					cus.setBookedAddress(obj.getString("bookedAddress"));
				}
				UserCollection user=UserFactory.getLocalInstance(ctx).getUserCollection("select * from where number='"+obj.getString("saleMan")+"'");
				if(user.size()>0){
					cus.setFirstConsultant(user.get(0));
					cus.setPropertyConsultant(user.get(0));
				}else{
					rs.put("number", obj.getString("number"));
					rs.put("state", "0");
					rs.put("msg", "置业顾问不存在！");
					arrrs.add(rs);
					
					FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
					sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步接口','"+obj+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
					sql.execute();
					
					continue;
				}
				if(obj.get("firstDate")!=null&&!"".equals(obj.getString("firstDate").trim())){
					cus.setFirstDate(Timestamp.valueOf(obj.getString("firstDate")));
				}
				if(obj.get("latestDate")!=null&&!"".equals(obj.getString("latestDate").trim())){
					cus.setLatestDate(Timestamp.valueOf(obj.getString("latestDate")));
				}
				if(obj.get("reportDate")!=null&&!"".equals(obj.getString("reportDate").trim())){
					cus.setReportDate(Timestamp.valueOf(obj.getString("reportDate")));
				}
				cus.setLevel(obj.getString("level"));
				cus.setOneQd(obj.getString("oneQd"));
				cus.setTwoQd(obj.getString("twoQd"));
				
				if(obj.get("qdPerson")!=null&&!"".equals(obj.getString("qdPerson").trim())){
					cus.setQdPersontxt(obj.getString("qdPerson"));
					cus.setQdDate(Timestamp.valueOf(obj.getString("qdDate")));
				}
				if(obj.get("recommend")!=null&&!"".equals(obj.getString("recommend").trim())){
					cus.setRecommended(obj.getString("recommend"));
					cus.setRecommendDate(Timestamp.valueOf(obj.getString("recommendDate")));
				}
				SHECustomerFactory.getLocalInstance(ctx).submit(cus);
				
				FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
				sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步接口','"+obj+"','"+obj.get("number")+"',now(),'成功','')");
				sql.execute();
				
				rs.put("number", obj.getString("number"));
				rs.put("state", "1");
				rs.put("msg", "同步成功！");
				arrrs.add(rs);
			}
		} catch (Exception e) {
			rs.put("state", "0");
			rs.put("msg", e.getMessage());
			arrrs.add(rs);
			
			return arrrs.toString();
		}
		return arrrs.toString();
	}
	protected String _sysCustomerValid(Context ctx, String str)
			throws BOSException, EASBizException {
		JSONArray arr = JSONArray.fromObject(str);
		JSONArray arrrs = new JSONArray();
		
		JSONObject rs = new JSONObject();
		try {
			for(int i=0;i<arr.size();i++){
				rs = new JSONObject();
				JSONObject obj=arr.getJSONObject(i);
				SHECustomerCollection sc=SHECustomerFactory.getLocalInstance(ctx).getSHECustomerCollection("select * from where number='"+obj.getString("number")+"'");
				if(sc.size()>0){
					SHECustomerInfo info=sc.get(0);
					
					String valid=obj.getString("valid");
					if("1".equals(valid)){
						UserCollection user=UserFactory.getLocalInstance(ctx).getUserCollection("select * from where number='"+obj.getString("saleMan")+"'");
						if(user.size()>0){
							SelectorItemCollection collection = new SelectorItemCollection();
							collection.add(new SelectorItemInfo("propertyConsultant"));
							collection.add(new SelectorItemInfo("isPublic"));
							
							SHECustomerInfo info_forupdate = new SHECustomerInfo();
							info_forupdate.setId(info.getId());
							info_forupdate.setIsPublic(false);
							info_forupdate.setPropertyConsultant(user.get(0));
							SHECustomerFactory.getLocalInstance(ctx).updatePartial(info_forupdate, collection);
							
							rs.put("number", obj.getString("number"));
							rs.put("state", "1");
							rs.put("msg", "同步成功！");
							arrrs.add(rs);
							
							FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
							sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步状态接口','"+obj+"','"+obj.get("number")+"',now(),'成功','')");
							sql.execute();
						}else{
							rs.put("number", obj.getString("number"));
							rs.put("state", "0");
							rs.put("msg", "置业顾问不存在！");
							arrrs.add(rs);
							
							FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
							sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步状态接口','"+obj+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
							sql.execute();
						}
					}else if("0".equals(valid)){
						SelectorItemCollection sic=new SelectorItemCollection();
						sic.add("isPublic");
						info.setIsPublic(true);
						SHECustomerFactory.getLocalInstance(ctx).updatePartial(info, sic);
						
						rs.put("number", obj.getString("number"));
						rs.put("state", "1");
						rs.put("msg", "同步成功！");
						arrrs.add(rs);
						
						FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
						sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步状态接口','"+obj+"','"+obj.get("number")+"',now(),'成功','')");
						sql.execute();
					}else if("3".equals(valid)){
						SelectorItemCollection sic=new SelectorItemCollection();
						sic.add("isPublic");
						info.setIsPublic(false);
						SHECustomerFactory.getLocalInstance(ctx).updatePartial(info, sic);
						
						rs.put("number", obj.getString("number"));
						rs.put("state", "1");
						rs.put("msg", "同步成功！");
						arrrs.add(rs);
						
						FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
						sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步状态接口','"+obj+"','"+obj.get("number")+"',now(),'成功','')");
						sql.execute();
					}else if("2".equals(valid)){
						SelectorItemCollection sic=new SelectorItemCollection();
						sic.add("oneQd");
						sic.add("twoQd");
						sic.add("recommended");
						sic.add("recommendDate");
						sic.add("qdPersontxt");
						sic.add("qdDate");
						info.setOneQd("自然来访");
						info.setTwoQd("自然来访");
						info.setRecommended(null);
						info.setRecommendDate(null);
						info.setQdPersontxt(null);
						info.setQdDate(null);
						SHECustomerFactory.getLocalInstance(ctx).updatePartial(info, sic);
						
						rs.put("number", obj.getString("number"));
						rs.put("state", "1");
						rs.put("msg", "同步成功！");
						arrrs.add(rs);
						
						FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
						sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步状态接口','"+obj+"','"+obj.get("number")+"',now(),'成功','')");
						sql.execute();
					}
				}else{
					rs.put("number", obj.getString("number"));
					rs.put("state", "0");
					rs.put("msg", "客户不存在！");
					arrrs.add(rs);
					
					FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
					sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户同步状态接口','"+obj+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
					sql.execute();
				}
			}
		} catch (Exception e) {
			rs.put("state", "0");
			rs.put("msg", e.getMessage());
			arrrs.add(rs);
			return rs.toString();
		}
		return arrrs.toString();
	}
	@Override
	protected String _isOldCustomer(Context ctx, String str)
			throws BOSException, EASBizException {
		JSONObject obj = JSONObject.fromObject(str);
		
		JSONObject rs = new JSONObject();
		if(obj.get("certificateNumber")==null||"".equals(obj.getString("certificateNumber").trim())){
			rs.put("isOld", "0");
			rs.put("msg", "证件号码不能为空！");
			
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户老业主接口','"+obj+"','',now(),'失败','"+rs.getString("msg")+"')");
			sql.execute();
		}
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
		sql.appendSql("select * from t_she_signmanage s left join T_SHE_SignCustomerEntry e on e.fheadid=s.fid where s.fbizstate in('SignApple','SignAudit') and e.fcertificateNumber='"+obj.getString("certificateNumber")+"'");
		IRowSet rowset=sql.executeQuery();
		try {
			if(rowset.next()){
				rs.put("isOld", "1");
			}else{
				rs.put("isOld", "0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = new FDCSQLBuilder(ctx);
		sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('客户老业主接口','"+obj+"','"+obj.get("certificateNumber")+"',now(),'成功','')");
		sql.execute();
		return rs.toString();
	}
	@Override
	protected String _synTransaction(Context ctx, String str)
			throws BOSException, EASBizException {
		JSONObject obj = JSONObject.fromObject(str);
		
		JSONObject rs = new JSONObject();
		if(obj.get("number")==null||"".equals(obj.getString("number").trim())){
			rs.put("state", "0");
			rs.put("msg", "单据编号不能为空！");
			
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('认购签约更新渠道接口','"+obj+"','',now(),'失败','"+rs.getString("msg")+"')");
			sql.execute();
		}
		if(obj.get("type")==null||"".equals(obj.getString("type").trim())){
			rs.put("state", "0");
			rs.put("msg", "单据类型不能为空！");
			
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('认购签约更新渠道接口','"+obj+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
			sql.execute();
		}
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("oneQd");
		sic.add("twoQd");
		if(obj.getString("type").equals("SIGN")){
			SignManageCollection signCol=SignManageFactory.getLocalInstance(ctx).getSignManageCollection("select * from where number='"+obj.getString("number")+"'");
			if(signCol.size()>0){
				SignManageInfo sign=signCol.get(0);
				sign.setOneQd(obj.getString("oneQd"));
				sign.setTwoQd(obj.getString("twoQd"));
				
				SignManageFactory.getLocalInstance(ctx).updatePartial(sign, sic);
				
				rs.put("state", "1");
				rs.put("msg", "同步成功！");
				
				FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
				sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('认购签约更新渠道接口','"+obj+"','"+obj.get("number")+"',now(),'成功','')");
				sql.execute();
			}else{
				rs.put("state", "0");
				rs.put("msg", "签约单不存在！");
				
				FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
				sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('认购签约更新渠道接口','"+obj+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
				sql.execute();
			}
		}else if(obj.getString("type").equals("PUR")){
			PurchaseManageCollection purCol=PurchaseManageFactory.getLocalInstance(ctx).getPurchaseManageCollection("select * from where number='"+obj.getString("number")+"'");
			if(purCol.size()>0){
				PurchaseManageInfo pur=purCol.get(0);
				pur.setOneQd(obj.getString("oneQd"));
				pur.setTwoQd(obj.getString("twoQd"));
				
				PurchaseManageFactory.getLocalInstance(ctx).updatePartial(pur, sic);
				
				rs.put("state", "1");
				rs.put("msg", "同步成功！");
				
				FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
				sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('认购签约更新渠道接口','"+obj+"','"+obj.get("number")+"',now(),'成功','')");
				sql.execute();
			}else{
				rs.put("state", "0");
				rs.put("msg", "认购单不存在！");
				
				FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
				sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('认购签约更新渠道接口','"+obj+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
				sql.execute();
			}
		}else{
			rs.put("state", "0");
			rs.put("msg", "类型错误！");
			
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			sql.appendSql("insert into t_log (name,context,number,createtime,state,msg) values('认购签约更新渠道接口','"+obj+"','"+obj.get("number")+"',now(),'失败','"+rs.getString("msg")+"')");
			sql.execute();
		}
		return rs.toString();
	}
    
}