package com.kingdee.eas.fdc.invite.supplier.app;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.invite.supplier.NetworkUtils;
import com.kingdee.eas.fdc.invite.supplier.RegistStateEnum;
import com.kingdee.eas.fdc.invite.supplier.UserSupplierAssoCollection;
import com.kingdee.eas.fdc.invite.supplier.UserSupplierAssoInfo;
import com.kingdee.eas.fdc.invite.supplier.WebUserFactory;
import com.kingdee.eas.fdc.invite.supplier.WebUserInfo;
import com.kingdee.eas.framework.CoreBaseInfo;

public class WebUserControllerBean extends AbstractWebUserControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.WebUserControllerBean");
    
    @Override
    protected boolean isUseName() {
    	return false;
    }
    
    @Override
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	WebUserInfo webUser = WebUserFactory.getLocalInstance(ctx).getWebUserInfo(pk);
    	super._delete(ctx, pk);
    	String url = NetworkUtils.getBaseUrl(ctx);
    	url =url+"T_GYS_DeleteUserInfoPermanently";
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("UserID", webUser.getWebUserID());
    	params.put("AuditUser", "wanhua");
    	String result = NetworkUtils.post(url, params);
    	NetworkUtils.processResponseResult(ctx,result);
    	
    }
    
    
    @Override
	public IObjectPK save(Context ctx, CoreBaseInfo model) throws BOSException,
			EASBizException {
    	IObjectPK pk =super.save(ctx, model);
    	WebUserInfo info = (WebUserInfo) model;
    	String baseUrl = NetworkUtils.getBaseUrl(ctx);
    	if(info.get("formAsso")!=null){//来自关联供应商界面
        	String syncWebUserUrl = baseUrl+"T_GYS_UserInfo_AuditStatus";;
        	Map<String,String> params = new HashMap<String,String>();
        	params.put("FID", info.getWebUserID());
        	params.put("FSTATE", "2REGISTED");
        	
        	UserSupplierAssoCollection supplierCols =  info.getEntry();
        	int size = supplierCols.size();
        	StringBuffer assoSupplier = new StringBuffer();
        	assoSupplier.append("[");
        	UserSupplierAssoInfo supplierInfo = null;
        	StringBuffer str = new StringBuffer();
        	for(int i=0;i<size;i++){
        		supplierInfo = supplierCols.get(i);
        		str.append(supplierInfo.getSupplier().getName());
//        		assoSupplier.append("{");
//        		assoSupplier.append("\"supplier\":\"");
        		assoSupplier.append("\"");
        		assoSupplier.append(supplierInfo.getSupplier().getId());
        		assoSupplier.append("\"");
//        		assoSupplier.append(supplierInfo.getSupplier().getId()+"\",");
//        		assoSupplier.append("\"user\":\"");
//        		assoSupplier.append(info.getId()+"\"");
//        		assoSupplier.append("}");
        		if(i!=size-1){
        			assoSupplier.append(",");
        			str.append(";");
        		}
        	}
        	if(size>0){
        		FDCSQLBuilder b = new FDCSQLBuilder(ctx);
        		b.appendSql("update t_gys_webuser set FRelateSupp = ? where fid = ?");
        		b.addParam(str.toString());
        		b.addParam(info.getId().toString());
        		b.executeUpdate();
        	}
        	
        	
        	assoSupplier.append("]");
        	params.put("FSUPPLIERIDS", assoSupplier.toString());
        	String result = NetworkUtils.post(syncWebUserUrl, params);
        	NetworkUtils.processResponseResult(ctx,result);
        	logger.info("同步微信用户信息,结果:"+result);
        	//更新用户信息关联的供应商信息
        	
    	}else if(info.get("isChangePasswd")!=null){//EAS修改微信和网站用户密码
    		String syncWebUserUrl = baseUrl+"T_GYS_UpdateUserPsw";;
        	Map<String,String> params = new HashMap<String,String>();
    		params.put("AuditUser", "wanhua");
    		params.put("Mobile", info.getMobileNumber());
    		params.put("NewPsw", info.getPassword());
    		String result = NetworkUtils.post(syncWebUserUrl, params);
        	NetworkUtils.processResponseResult(ctx,result);
    	}
		return pk;
	}

    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	//TODO 回写前端信息
    	WebUserInfo webUser = WebUserFactory.getLocalInstance(ctx).getWebUserInfo(new ObjectUuidPK(billId));
    	if((FDCBillStateEnum.SUBMITTED.equals(webUser.getState())||FDCBillStateEnum.AUDITTING.equals(webUser.getState())) && RegistStateEnum.REGISTAUDITTING.equals(webUser.getRegisterState())){
    		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    		builder.appendSql("update t_gys_webuser set fregisterstate = '2REGISTED' where fid = ?");
    		builder.addParam(billId+"");
    		builder.executeUpdate();
    	}else{
    		throw new ContractException(ContractException.WITHMSG,new Object[]{"只有提交状态并且注册状态为注册审批中的单据才能进行审批操作。"});
    	}
    	super._audit(ctx, billId);
    	
    	String baseUrl = NetworkUtils.getBaseUrl(ctx);
    	String syncWebUserUrl = baseUrl+"T_GYS_UserInfo_AuditStatus";;
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("FID", webUser.getWebUserID());
    	params.put("FSTATE", "2REGISTED");
    	params.put("FSUPPLIERIDS", "");
    	String result = NetworkUtils.post(syncWebUserUrl, params);
    	NetworkUtils.processResponseResult(ctx,result);
    
    }
    
    @Override
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	WebUserInfo webUser = WebUserFactory.getLocalInstance(ctx).getWebUserInfo(new ObjectUuidPK(billId));
    	if(!RegistStateEnum.INVALID.equals(webUser.getRegisterState())){
    		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    		builder.appendSql("update t_gys_webuser set fregisterstate = '1REGISTAUDITTING' where fid = ?");
    		builder.addParam(billId+"");
    		builder.executeUpdate();
    	}else{
    		throw new ContractException(ContractException.WITHMSG,new Object[]{"只有非作废状态的单据才能进行反审批操作。"});
    	}
    	super._unAudit(ctx, billId);
    	
    	
    	//TODO 回写前端信息
    	String url = NetworkUtils.getBaseUrl(ctx);
    	url =url+"T_GYS_UserInfo_AuditStatus";
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("FID", webUser.getWebUserID());
    	params.put("FSTATE", "1REGISTAUDITTING");
    	params.put("FSUPPLIERIDS", "");
    	String result = NetworkUtils.post(url, params);
    	NetworkUtils.processResponseResult(ctx,result);
    }
    
    
    @Override
    protected void _invalidRegister(Context ctx, BOSUuid billId)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	super._invalidRegister(ctx, billId);
    	
    	WebUserInfo webUser = WebUserFactory.getLocalInstance(ctx).getWebUserInfo(new ObjectUuidPK(billId));
    	if(!RegistStateEnum.INVALID.equals(webUser.getRegisterState())){
    		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
        	builder.appendSql("update t_gys_webuser set fregisterstate = '4INVALID' where fid = ?");
        	builder.addParam(billId+"");
        	builder.executeUpdate();
    	}else{
    		throw new ContractException(ContractException.WITHMSG,new Object[]{"当前状态的单据不可以进行操作。"});
    	}
    	
    	String url = NetworkUtils.getBaseUrl(ctx);
    	url =url+"T_GYS_DeleteUserInfoPermanently";
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("UserID", webUser.getWebUserID());
    	params.put("AuditUser", "wanhua");
    	String result = NetworkUtils.post(url, params);
    	NetworkUtils.processResponseResult(ctx,result);
    	
    }
    
}