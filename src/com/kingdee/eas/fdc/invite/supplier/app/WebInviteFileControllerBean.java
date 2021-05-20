package com.kingdee.eas.fdc.invite.supplier.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.invite.supplier.NetworkUtils;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmInfo;
import com.kingdee.eas.fdc.invite.supplier.WebInviteFileCollection;
import com.kingdee.eas.fdc.invite.supplier.WebInviteFileFactory;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.invite.supplier.WebInviteFileInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class WebInviteFileControllerBean extends AbstractWebInviteFileControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.WebInviteFileControllerBean");
    
    @Override
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	checkOprt(billId,FDCBillStateEnum.SUBMITTED,ctx);
    	super._audit(ctx, billId);
    	
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	sic.add("inviteProject.*");
    	sic.add("invitation.*");
		WebInviteFileInfo info = getWebInviteFileInfo(ctx, new ObjectUuidPK(billId),sic );
    	
    	
    	String baseUrl = NetworkUtils.getBaseUrl(ctx);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	String syncWebInviteFileUrl =baseUrl+"T_GYS_T_GYS_InviteFiles_Sync";
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("FID", info.getId()+"");
    	params.put("FNumber",info.getNumber());
    	params.put("FName", info.getInvitation()==null?"":info.getInvitation().getInviteProjectName());
    	params.put("FProjectName", info.getInviteProject().getCurProjectName());
    	params.put("FPublishTime", sdf.format(info.getCreateTime()));
    	params.put("FInviteProjectID", info.getInviteProject().getId()+"");
    	params.put("FState", "1");
    	
    	FilterInfo filter = new FilterInfo();
    	StringBuffer str = new StringBuffer();
    	filter.getFilterItems().add(new FilterItemInfo("boId",info.getId().toString()));
    	EntityViewInfo view = new EntityViewInfo();
    	view.setFilter(filter);
    	
    	sic = new SelectorItemCollection();
    	sic.add("attachment.*");
    	sic.add("*");
    	view.setSelector(sic);
    	
    	BoAttchAssoCollection fileCols = BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(view);
    	int size = fileCols.size();
    	str.append("[");
    	BoAttchAssoInfo asso = null;
    	AttachmentInfo attachment = null;
    	for(int i=0;i<size;i++){
    		asso = fileCols.get(i);
    		attachment = asso.getAttachment();
    		str.append("{");
    		str.append("\"fileId\":\"");
    		str.append(attachment.getId());
    		str.append("\",\"fileName\":\"");
    		str.append(attachment.getName());
    		str.append("\",\"fileType\":\"");
    		str.append(attachment.getType());
    		str.append("\",\"extName\":\"");
    		str.append(attachment.getSimpleName());
    		str.append("\"}");
    		if(i!=size-1){
    			str.append(",");
    		}
    	}
    	
    	str.append("]");
    	params.put("FFiles",str.toString());
    	
    		   
    	String result = NetworkUtils.post(syncWebInviteFileUrl, params);
    	NetworkUtils.processResponseResult(ctx,result);
    }
    
    
    @Override
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	checkOprt(billId,FDCBillStateEnum.AUDITTED,ctx);
    	super._unAudit(ctx, billId);
    	
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	sic.add("inviteProject.*");
    	sic.add("invitation.*");
		WebInviteFileInfo info = getWebInviteFileInfo(ctx, new ObjectUuidPK(billId),sic );
    	
    	
    	String baseUrl = NetworkUtils.getBaseUrl(ctx);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	String syncWebInviteFileUrl =baseUrl+"T_GYS_T_GYS_InviteFiles_Sync";
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("FID", info.getId()+"");
    	params.put("FNumber",info.getNumber());
    	params.put("FName", info.getInvitation()==null?"":info.getInvitation().getInviteProjectName());
    	params.put("FProjectName", info.getInviteProject().getCurProjectName());
    	params.put("FInviteProjectID", info.getInviteProject().getId()+"");
    	params.put("FPublishTime", "");
    	params.put("FState", "0");
    	
    	
    	FilterInfo filter = new FilterInfo();
    	StringBuffer str = new StringBuffer();
    	filter.getFilterItems().add(new FilterItemInfo("boId",info.getId().toString()));
    	EntityViewInfo view = new EntityViewInfo();
    	view.setFilter(filter);
    	
    	sic = new SelectorItemCollection();
    	sic.add("attachment.*");
    	sic.add("*");
    	view.setSelector(sic);
    	
    	BoAttchAssoCollection fileCols = BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(view);
    	int size = fileCols.size();
    	str.append("[");
    	BoAttchAssoInfo asso = null;
    	AttachmentInfo attachment = null;
    	for(int i=0;i<size;i++){
    		asso = fileCols.get(i);
    		attachment = asso.getAttachment();
    		str.append("{");
    		str.append("\"fileId\":\"");
    		str.append(attachment.getId());
    		str.append("\",\"fileName\":\"");
    		str.append(attachment.getName());
    		str.append("\",\"fileType\":\"");
    		str.append(attachment.getType());
    		str.append("\",\"extName\":\"");
    		str.append(attachment.getSimpleName());
    		str.append("\"}");
    		if(i!=size-1){
    			str.append(",");
    		}
    	}
    	
    	str.append("]");
    	params.put("FFiles",str.toString());
    	
    		   
    	String result = NetworkUtils.post(syncWebInviteFileUrl, params);
    	NetworkUtils.processResponseResult(ctx,result);
    }
    
    private void checkOprt(BOSUuid billId,FDCBillStateEnum state,Context ctx) throws EASBizException, BOSException{
    	WebInviteFileInfo info = getWebInviteFileInfo(ctx, new ObjectUuidPK(billId));
    	FDCBillStateEnum currState = info.getState();
    	if(!state.equals(currState)){
    		throw new ContractException(ContractException.WITHMSG,new String[]{"当前状态的单据不适合此操作."});
    	}
    }
}