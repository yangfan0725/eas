package com.kingdee.eas.fdc.contract.app;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.entrust.toolkit.util.Map;
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
import java.io.UnsupportedEncodingException;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardInfo;
import com.kingdee.eas.basedata.master.cssp.StandardTypeEnum;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitCollection;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.SupplierApplyCollection;
import com.kingdee.eas.fdc.contract.SupplierApplyFactory;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DeletedStatusEnum;
import com.kingdee.eas.framework.EffectedStatusEnum;
import com.kingdee.eas.fdc.contract.SupplierApplyInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class SupplierApplyControllerBean extends AbstractSupplierApplyControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.SupplierApplyControllerBean");

	public static String SHA(final String strText) {
		MessageDigest messageDigest;
        String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(strText.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeStr;
	}
	private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		super._audit(ctx, billId);
		SupplierApplyInfo info=this.getSupplierApplyInfo(ctx, new ObjectUuidPK(billId));
		addToSysSupplier(ctx,info);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = ts.getTime();
		FDCSQLBuilder builder1=new FDCSQLBuilder(ctx);
		String timestamp = null;
		
//		String entCode="EC181207Y18YFSN";
//		String entCode=null;
//		if(ctx.getAIS().equals("easdb")){
//			entCode="EC181215SWO1GB9";
//			login.put("appCode", "UI9BG1OWS512181");
//			login.put("secret", SHA("zHuEPZ4Z5i6SnXfSy4WS7otpNBtkdpv5Nv2y5xQEYH8LQhaZyQ2up7qCcrbcUrWv:UI9BG1OWS512181:"+lt));
//		}else{
//			login.put("appCode", "UI181212SUNDY001");
//			login.put("secret", SHA("Lqecp6GGSaj68s9anpfuJkVkz:UI181212SUNDY001:"+lt));
			
//			entCode="EC181215SWO1GBB";
//			login.put("appCode", "UIBBG1OWS512181");
//			login.put("secret", SHA("NNcu5Rgdy2GZYMibzK8NXtWGVhqhJ8FjYgo9KMJABviKcp6VeHbpdcU9CGHL3Zh4:UIBBG1OWS512181:"+lt));
//		}
		JSONObject login=new JSONObject();
		String tokenId=null;
		String entCode=null;
		String appCode=null;
		String secret=null;
		String mturl=null;
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("select * from t_mk");
			IRowSet rs=builder.executeQuery();
			while(rs.next()){
				mturl=rs.getString("furl");
				entCode=rs.getString("fentCode");
				appCode=rs.getString("fappCode");
				secret=rs.getString("fsecret");
			}
			login.put("appCode", appCode);
			login.put("secret", SHA(secret+lt));
			login.put("timestamp", lt);
			
			String respStr = HttpClientUtil.sendRequest(mturl+"/auth/login", "POST", "application/json;charse=UTF-8", "UTF-8", null, login.toJSONString());
			
			JSONObject crso = JSONObject.parseObject(respStr);
			JSONObject d=crso.getJSONObject("data");
			if(!"true".equals(crso.getString("success"))){
				throw new EASBizException(new NumericExceptionSubItem("100",crso.getString("message")));
			}else{
				tokenId=d.getString("tokenId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
		}
		JSONArray arr=new JSONArray();
		JSONObject cjo=new JSONObject();
		cjo.put("businessCode", "FDC-APPLY-" + info.getNumber());
		cjo.put("name", info.getName());
		cjo.put("taxpayerID", info.getTaxerNum());
		
		JSONArray accounts=new JSONArray();
		JSONObject act=new JSONObject();
		
		act.put("name", info.getBank());
		act.put("bankName", info.getBank());
		act.put("businessCode", "FDC-APPLY-" + info.getNumber()+"-Account");
		act.put("account", info.getBankAccount());
		act.put("paymentType", "BANK");
		act.put("bankBranchNo", info.getSourceFunction());
		act.put("customBranchFlag", false);
		act.put("active", true);
		
		accounts.add(act);
		
		cjo.put("accounts",accounts);
		
		
		arr.add(cjo);
		
		HashMap header=new HashMap();
		header.put("tokenId", tokenId);
		header.put("entCode", entCode);
		
		JSONObject json=new JSONObject();
		json.put("data",  arr);
		json.put("timestamp", lt);
		
		try {
			String crs = HttpClientUtil.sendRequest(mturl+"/supplier/save", "POST", "application/json;charse=UTF-8", "UTF-8", header, json.toJSONString());
			JSONObject crso = JSONObject.parseObject(crs);
			JSONArray d=crso.getJSONArray("data");
			if(!"ACK".equals(crso.getString("code"))&&d.getJSONObject(0).getString("error")!=null&&d.getJSONObject(0).getString("error").indexOf(info.getTaxerNum())<0){
				throw new EASBizException(new NumericExceptionSubItem("100",d.getJSONObject(0).getString("error")));
			}
		} catch (Exception e) {
			throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
		}
//		to_yb
		JSONObject initjson=new JSONObject();
		 JSONObject esbjson=new JSONObject();
		 String instId=null;
		 String requestTime=null;
		 JSONObject datajson=new JSONObject();
		 JSONArray ybarr=new JSONArray();
		 String instsId=null;
		 String requestsTime=null;
		 JSONObject ybjson=new JSONObject();
		 String	param1="false";
			try {
				param1 = ParamControlFactory.getLocalInstance(ctx).getParamValue(new ObjectUuidPK(info.getOrgUnit().getId()), "YF_YB");
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if("true".equals(param1)){
				builder1.clear();
				builder1.appendSql(" select instId,requestTime from esbInfo where source='supplier'");
				IRowSet rs3=builder1.executeQuery();
		try {
			while(rs3.next()){
				  instId=rs3.getString("instId");
				  requestTime=rs3.getString("requestTime");
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 if(instsId!=null){
			 esbjson.put("instId",instsId);
		 }
		 if(requestsTime!=null){
			 esbjson.put("requestTime",requestsTime);
		 }
		 
//			上次返回在时间戳
		 builder1.clear();
		 builder1.appendSql(" select ybtime from ybTimeRecord where source='supplier'");
			IRowSet rs1=builder1.executeQuery();
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
//		to_yb
			if(info.getId()!=null){
				ybjson.put("builderId",info.getId().toString());
			}else{
				ybjson.put("builderId","");
			}
			if(info.getName()!=null){
				ybjson.put("builderName",info.getName().replaceAll("\\s", ""));
			}else{
				ybjson.put("builderName","");
			}
			
			ybjson.put("contactName",null);
			ybjson.put("contactDetail",null);
			ybjson.put("contactAddress",null);
			ybjson.put("contactEmail",null);
			ybjson.put("contactFax",null);
			ybjson.put("remark",null);
			ybjson.put("state","1");
			if(info.getCreateTime()!=null){
				String createTime=sdf.format(info.getCreateTime());
				ybjson.put("createTime",createTime);
			}else{
				ybjson.put("createTime","");
			}
			if(info.getLastUpdateTime()!=null){
				String updateTime=sdf.format(info.getLastUpdateTime());
				ybjson.put("updateTime",updateTime);
			}else{
				ybjson.put("modifiedTime","");
			}

			if(info.getTaxerNum()!=null){
				ybjson.put("creditCode", info.getTaxerNum().toString().replaceAll("\\s", ""));   				
		}else{
			ybjson.put("creditCode", "");
		}

		ybarr.add(ybjson);
		 datajson.put("datas",ybarr);
			datajson.put("timestamp",timestamp);
			initjson.put("esbInfo", esbjson);
			initjson.put("requestInfo",datajson);
		
//		同步supplier信息到yiBei
		try {
//			System.out.println(initjson.toJSONString());
			String rs11=SHEManageHelper.execPostYBsupplier(ctx, initjson.toJSONString(),timestamp);
			JSONObject rso = JSONObject.parseObject(rs11);
			if(!"A0200".equals(rso.getJSONObject("esbInfo").getString("returnCode"))){
				throw new EASBizException(new NumericExceptionSubItem("100",rso.getJSONObject("esbInfo").getString("returnMsg")));
			}else{
				JSONObject rst=rso.getJSONObject("esbInfo");
				 instId=rst.getString("instId");
				 requestTime=rst.getString("requestTime");
				 builder1.clear();
				 builder1.appendSql(" update esbInfo set instId='"+instId+"',requestTime='"+requestTime+"' where source='supplier'");
				 builder1.executeUpdate();
				 JSONObject rst1=rso.getJSONObject("resultInfo");
				 String ts1=rst1.getString("timestamp");
				 builder1.clear();
				 builder1.appendSql("update ybTimeRecord set ybTime='"+ts1+"' where source='supplier' ");
				 builder1.executeUpdate();
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	}
	private void updateSysSupplier(Context ctx, SupplierApplyInfo info, SupplierInfo supplier) throws BOSException, EASBizException {
		setSysSupplierValue(ctx, info, supplier);
		
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("number");
		selCol.add("name");
		selCol.add("description");
		selCol.add("adminCU");
		selCol.add("CU");
		selCol.add("version");
		selCol.add("usedStatus");
		selCol.add("effectedStatus");
		selCol.add("simpleName");
		selCol.add("isInternalCompany");
		SupplierFactory.getLocalInstance(ctx).updatePartial(supplier, selCol);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("supplier.id", supplier.getId().toString()));
		SupplierCompanyInfoCollection comCol=SupplierCompanyInfoFactory.getLocalInstance(ctx).getSupplierCompanyInfoCollection(view);
		for(int i=0;i<comCol.size();i++){
			SupplierCompanyBankInfo cb=comCol.get(i).getSupplierBank().get(0);
			if(cb!=null){
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("bank");
				sel.add("bankAddress");
				sel.add("bankAccount");
				cb.setBank(info.getBank());
				cb.setBankAccount(info.getBankAccount());
				cb.setBankAddress(info.getSourceFunction());
				SupplierCompanyBankFactory.getLocalInstance(ctx).updatePartial(cb, sel);
			}
		}
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
//		SupplierApplyInfo info=this.getSupplierApplyInfo(ctx, new ObjectUuidPK(billId));
//		if(info.getSourceBillId()!=null&&SupplierFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(info.getSourceBillId()))){
//			throw new EASBizException(new NumericExceptionSubItem("101","已关联主数据供应商,不能进行反审批操作！"));
//		}
		super._unAudit(ctx, billId);
	}
	protected void addToSysSupplier(Context ctx, IObjectValue objectValue) throws BOSException, EASBizException {
		SupplierApplyInfo info = (SupplierApplyInfo)objectValue;
		if(info==null){
			return;
		}
		if(info.getSourceBillId()==null){
			SupplierInfo supplier = new SupplierInfo();
			supplier.setId(BOSUuid.create(supplier.getBosType()));
			setSysSupplierValue(ctx, info, supplier);
			
			CtrlUnitInfo cu = new CtrlUnitInfo();
			cu.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
			
			CSSPGroupInfo groupInfo =null;
			SupplierGroupDetailInfo Gdinfo = null;
			CSSPGroupCollection groupCol = CSSPGroupFactory.getLocalInstance(ctx).getCSSPGroupCollection("select number,name,groupStandard.id from where groupStandard.id='00000000-0000-0000-0000-000000000001BC122A7F' and cu.id='"+OrgConstants.DEF_CU_ID+"'");
			if(groupCol.size()>0){
				groupInfo=groupCol.get(0);
				supplier.setBrowseGroup(groupInfo);
	    		
	    		Gdinfo = new SupplierGroupDetailInfo();
	    		Gdinfo.setSupplierGroup(groupInfo);
	    		Gdinfo.setSupplierGroupFullName(groupInfo.getName());
	    		Gdinfo.setSupplierGroupStandard(groupInfo.getGroupStandard());
	    		supplier.getSupplierGroupDetails().add(Gdinfo);
			}
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("name", "房地产供应商"));
			filter.getFilterItems().add(new FilterItemInfo("CU.id", OrgConstants.DEF_CU_ID));
			
			CSSPGroupCollection sheGroupCol = CSSPGroupFactory.getLocalInstance(ctx).getCSSPGroupCollection(view);
			if(sheGroupCol.isEmpty()){
				CSSPGroupStandardInfo strd = new CSSPGroupStandardInfo();
				strd.setId(BOSUuid.create(strd.getBOSType()));
				strd.setNumber("fdcsupplierGstrd");
				strd.setName("房地产供应商分类标准");
				strd.setType(2);
				strd.setIsBasic(StandardTypeEnum.defaultStandard);
				strd.setCU(cu);
				
				CSSPGroupStandardFactory.getLocalInstance(ctx).addnew(strd);
				
				CSSPGroupInfo gr = new CSSPGroupInfo();
				gr.setDeletedStatus(DeletedStatusEnum.NORMAL);
				gr.setId(BOSUuid.create(gr.getBOSType()));
				gr.setNumber("fdcsupplierG");
				gr.setName("房地产供应商");
				gr.setCU(cu);
				gr.setGroupStandard(strd);
				
				CSSPGroupFactory.getLocalInstance(ctx).addnew(gr);
				
				groupInfo = gr;
			}else{
				groupInfo = sheGroupCol.get(0);
			}
			if(supplier.getBrowseGroup()!=null){
				supplier.setBrowseGroup(groupInfo);
			}
			Gdinfo = new SupplierGroupDetailInfo();
			Gdinfo.setSupplierGroup(groupInfo);
			Gdinfo.setSupplierGroupFullName(groupInfo.getName());
			Gdinfo.setSupplierGroupStandard(groupInfo.getGroupStandard());
			supplier.getSupplierGroupDetails().add(Gdinfo);
			
			SupplierFactory.getLocalInstance(ctx).addnew(supplier);
			
			SupplierCompanyInfoInfo com = new SupplierCompanyInfoInfo();
			CompanyOrgUnitInfo company=new CompanyOrgUnitInfo();
			company.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
			com.setCompanyOrgUnit(company);
			com.setSupplier(supplier);
			com.setCU(cu);
			
			SupplierCompanyBankInfo bankInfo=new SupplierCompanyBankInfo();
			bankInfo.setBank(info.getBank());
			bankInfo.setBankAccount(info.getBankAccount());
			bankInfo.setBankAddress(info.getSourceFunction());
			com.getSupplierBank().add(bankInfo);
			
			SupplierCompanyInfoFactory.getLocalInstance(ctx).addnew(com);
			
			Set cuIds = getSupplierMgeCu(ctx,supplier.getAdminCU().getId().toString());
	    	for(Iterator itor = cuIds.iterator(); itor.hasNext(); ){
	    		String cuId = (String) itor.next();
	    		SupplierFactory.getLocalInstance(ctx).assign(new ObjectUuidPK(supplier.getAdminCU().getId()), new ObjectUuidPK(supplier.getId()), new ObjectUuidPK(cuId));
	    	}
	    	
	    	SelectorItemCollection sic=new SelectorItemCollection();
	    	sic.add("sourceBillId");
	    	
	    	info.setSourceBillId(supplier.getId().toString());
	    	SupplierApplyFactory.getLocalInstance(ctx).updatePartial(info,sic);
		}else{
			updateSysSupplier(ctx, info, SupplierFactory.getLocalInstance(ctx).getSupplierInfo(new ObjectUuidPK(info.getSourceBillId())));
		}
	}
	private Set getSupplierMgeCu(Context ctx,String cuId) throws BOSException {
		Set set = new HashSet();
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", OrgConstants.SYS_CU_ID,CompareType.NOTEQUALS));
		view.setFilter(filter);
		view.setSelector(sel);
		
		CtrlUnitCollection orgColl = CtrlUnitFactory.getLocalInstance(ctx).getCtrlUnitCollection(view);
		for (int i = 0; i < orgColl.size(); i++) {
			if(cuId.equals(orgColl.get(i).getId().toString())){
				continue;
			}
			set.add(orgColl.get(i).getId().toString());
		}
		return set;
	}
	private void setSysSupplierValue(Context ctx, SupplierApplyInfo info, SupplierInfo supplier) throws BOSException, EASBizException {
		supplier.setNumber("FDC-APPLY-" + info.getNumber());
		supplier.setName(info.getName());
		supplier.setDescription(info.getDescription());
		CtrlUnitInfo cu = new CtrlUnitInfo();
		cu.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		supplier.setCU(cu);
		supplier.setAdminCU(cu);
		
		supplier.setVersion(1);
		supplier.setUsedStatus(UsedStatusEnum.APPROVED);
		supplier.setEffectedStatus(EffectedStatusEnum.EFFECTED);
		supplier.setIsInternalCompany(false);
		
		supplier.setTaxRegisterNo(info.getTaxerNum());
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
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo)throws BOSException, EASBizException{
		if(!isUseNumber())
			return;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", billInfo.getName().trim()));
		if(billInfo.getId() != null)
			filter.getFilterItems().add(new FilterItemInfo("id", billInfo.getId().toString(), CompareType.NOTEQUALS));

		if(_exists(ctx, filter))
			throw new ContractException(ContractException.NAME_DUP);
		else return;
	}
	protected void recycleNumber(Context ctx, IObjectPK pk)throws BOSException, EASBizException, CodingRuleException{
		
	}
    
}