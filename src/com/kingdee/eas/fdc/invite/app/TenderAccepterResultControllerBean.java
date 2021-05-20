package com.kingdee.eas.fdc.invite.app;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.InviteFixCollection;
import com.kingdee.eas.fdc.invite.InviteFixFactory;
import com.kingdee.eas.fdc.invite.InviteFixInfo;
import com.kingdee.eas.fdc.invite.TenderAccepterResultCollection;
import com.kingdee.eas.fdc.invite.TenderAccepterResultEntryCollection;
import com.kingdee.eas.fdc.invite.TenderAccepterResultEntryFactory;
import com.kingdee.eas.fdc.invite.TenderAccepterResultEntryInfo;
import com.kingdee.eas.fdc.invite.TenderAccepterResultFactory;
import com.kingdee.eas.fdc.invite.TenderAccepterResultInfo;

public class TenderAccepterResultControllerBean extends AbstractTenderAccepterResultControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.TenderAccepterResultControllerBean");

    public HashMap getFixMap(Context ctx, BOSUuid inviteProjectId) throws BOSException, EASBizException {
    	return this._getFixMap(ctx, inviteProjectId);
    }
    
    @Override
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super._audit(ctx, billId);
    	updateInviteStatus(ctx, billId.toString());
    }
    
    @Override
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super._unAudit(ctx, billId);
    	unHit(ctx, billId.toString());
    }
    
    private void updateInviteStatus(Context ctx,String billId) throws BOSException{
    	EntityViewInfo viewInfo = new EntityViewInfo();
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("parent.inviteProject.id");
    	sic.add("parent.number");
    	sic.add("supplier.id");
    	sic.add("supplier.name");
    	sic.add("supplier.number");
		sic.add("*");
		viewInfo.setSelector(sic);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id",billId));
		viewInfo.setFilter(filter);
		
		TenderAccepterResultEntryCollection cols = TenderAccepterResultEntryFactory.getLocalInstance(ctx).getTenderAccepterResultEntryCollection(viewInfo);
		if(cols.isEmpty()){
			return ;
		}
		TenderAccepterResultEntryInfo entry = null;
		String inviteProjectId = null;
		String winBillNo = null;
		StringBuffer str = new StringBuffer();
		for(int i=0;i<cols.size();i++){
			entry = cols.get(i);
			if(entry.isHit()){
				str.append(entry.getSupplier().getName()+";");
				winBillNo = entry.getParent().getNumber();
				inviteProjectId =entry.getParent().getInviteProject().getId()+"";
			}
			
		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update t_inv_inviteproject set fhit =1,fwinsupplier=? ,fwinbillno=?,FAccepterBillID=? where fid = ?");
		builder.addParam(str.toString());
		builder.addParam(winBillNo);
		builder.addParam(billId);
		builder.addParam(inviteProjectId);
		builder.executeUpdate();
    	
    }
    private void unHit(Context ctx,String billId) throws BOSException{
    	
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("update t_inv_inviteproject set fhit =0,fwinsupplier=null ,fwinbillno=null,FAccepterBillID= null where fid =(select finviteprojectid from T_INV_TenderAccepterResult where fid = ?)");
    	builder.addParam(billId);
    	builder.executeUpdate();
    	
    }
    
	@Override
	protected HashMap _getFixMap(Context ctx, BOSUuid inviteProjectId) throws BOSException, EASBizException {
		HashMap<String,InviteFixInfo > map = new HashMap<String, InviteFixInfo>();
		
		EntityViewInfo fixView = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("head.inviteProject.id");
		sic.add("*");
		sic.add("head.state");
		fixView.setSelector(sic);
		
		FilterInfo fixFilter = new FilterInfo();
		fixView.setFilter(fixFilter);
		fixFilter.getFilterItems().add(new FilterItemInfo("head.inviteProject.id",inviteProjectId.toString()));
		fixFilter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
		
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo time = new SorterItemInfo("head.auditTime");
		time.setSortType(SortType.DESCEND);
		sorter.add(time);
		fixView.setSorter(sorter);
		
		InviteFixCollection fixCol=InviteFixFactory.getLocalInstance(ctx).getInviteFixCollection(fixView);
		for(int i=0; i<fixCol.size(); i++) {
			InviteFixInfo fix = fixCol.get(i);
			BOSUuid sid = fix.getSupplier().getId();
			if( sid !=null && map.get(sid.toString()) ==null ) {
				map.put(sid.toString(), fix);
			}
			
		}
		
		// TODO Auto-generated method stub
		return map;
	}
    
    

}