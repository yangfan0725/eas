package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.invite.DirectAccepterResultCollection;
import com.kingdee.eas.fdc.invite.DirectAccepterResultEntryCollection;
import com.kingdee.eas.fdc.invite.DirectAccepterResultEntryFactory;
import com.kingdee.eas.fdc.invite.DirectAccepterResultEntryInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.invite.BaseInviteCollection;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.invite.DirectAccepterResultInfo;

public class DirectAccepterResultControllerBean extends AbstractDirectAccepterResultControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.DirectAccepterResultControllerBean");
    
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
		
		DirectAccepterResultEntryCollection cols = DirectAccepterResultEntryFactory.getLocalInstance(ctx).getDirectAccepterResultEntryCollection(viewInfo);
		if(cols.isEmpty()){
			return ;
		}
		DirectAccepterResultEntryInfo entry = null;
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
    	builder.appendSql("update t_inv_inviteproject set fhit =0,fwinsupplier=null ,fwinbillno=null,FAccepterBillID = null where fid =(select finviteprojectid from T_INV_DirectAccepterResult where fid = ?)");
    	builder.addParam(billId);
    	builder.executeUpdate();
    	
    }
}