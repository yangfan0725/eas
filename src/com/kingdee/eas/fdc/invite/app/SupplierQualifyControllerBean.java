package com.kingdee.eas.fdc.invite.app;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryCollection;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryFactory;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyFactory;
import com.kingdee.eas.fdc.invite.SupplierQualifyInfo;
import com.kingdee.eas.fdc.invite.supplier.ITenderInfo;
import com.kingdee.eas.fdc.invite.supplier.TenderInfo;
import com.kingdee.eas.fdc.invite.supplier.TenderInfoCollection;
import com.kingdee.eas.fdc.invite.supplier.TenderInfoFactory;
import com.kingdee.eas.fdc.invite.supplier.TenderInfoInfo;

public class SupplierQualifyControllerBean extends AbstractSupplierQualifyControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.SupplierQualifyControllerBean");

	@Override
	protected boolean _isHasStartBid(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		SupplierQualifyInfo info = SupplierQualifyFactory.getLocalInstance(ctx).getSupplierQualifyInfo(pk);
		return info.isHasStartBid();
	}
	
	@Override
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		super._audit(ctx, billId);
		syncTender(ctx,billId,"audit");
	}
	
	@Override
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		super._unAudit(ctx, billId);
		//syncTender(ctx,billId,"unAudit");
	}
	
	private void syncTender(Context ctx,BOSUuid billID,String actionName) throws EASBizException, BOSException{
		
		Set<String> supplierIDS = new HashSet<String>();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("supplier.*");
		sic.add("parent.inviteProject.*");
		view.setSelector(sic);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id",billID+""));
		view.setFilter(filter);
		
		String inviteProject = null;
		SupplierQualifyEntryCollection cols = SupplierQualifyEntryFactory.getLocalInstance(ctx).getSupplierQualifyEntryCollection(view);
	
		//查询当前招标立项关联的报名信息
		Iterator<SupplierQualifyEntryInfo> it = cols.iterator();
		SupplierQualifyEntryInfo info = null;
		for(;it.hasNext();){
			info = it.next();
			if(StringUtils.isEmpty(inviteProject)){
				inviteProject = info.getParent().getInviteProject().getId()+"";
			}
			supplierIDS.add(info.getSupplier().getId()+"");
		}
		
		
		//获取所有的外部招标报名信息
		 view = new EntityViewInfo();
		 filter = new FilterInfo();
		 filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",inviteProject));
		 view.setFilter(filter);
		TenderInfoCollection tenderCols = TenderInfoFactory.getLocalInstance(ctx).getTenderInfoCollection(view);
		ITenderInfo itenderInfo = TenderInfoFactory.getLocalInstance(ctx);
		 Iterator<TenderInfoInfo> tenderIter = tenderCols.iterator();
		 TenderInfoInfo tenderInfo = null;
		 BOSUuid billId = null;
		 String supplierId = null;
		 for(;tenderIter.hasNext();){
			 tenderInfo = tenderIter.next();
			 billId = tenderInfo.getId();
			 supplierId = tenderInfo.getSupplier().getId().toString();
			 if(supplierIDS.contains(supplierId)){//如果供应能够对应的则置报名状态为报名成功
				 if(actionName.equals("audit")){
					 itenderInfo.approveReport(tenderInfo.getId());
				 }
			 }else{//报名失败
				 itenderInfo.rejectReport(tenderInfo.getId()); 
			 }
			  if(actionName.equals("audit")){
				  itenderInfo.audit(billId);
			  }else{
				  itenderInfo.unAudit(billId);
			  }
			 
			 
		 }
		
		
	}

}