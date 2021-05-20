package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import javax.print.attribute.standard.MediaSize.Other;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Iterator;

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

import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.tenancy.MoneyDefineAndTaxCollection;
import com.kingdee.eas.fdc.tenancy.MoneyDefineAndTaxFactory;
import com.kingdee.eas.fdc.tenancy.MoneyDefineAndTaxInfo;
import com.kingdee.eas.fdc.tenancy.OtherBillCollection;
import com.kingdee.eas.fdc.tenancy.OtherMoneyDefineTaxCollection;
import com.kingdee.eas.fdc.tenancy.OtherMoneyDefineTaxInfo;
import com.kingdee.eas.fdc.tenancy.RestReceivableInfo;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;

import java.lang.String;

import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.tenancy.OtherBillInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.tenancy.TenBillBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class OtherBillControllerBean extends AbstractOtherBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.OtherBillControllerBean");
    
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		super._audit(ctx, billId);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("tenancyBill.id");
		sels.add("payEntry.*");
		
		SelectorItemCollection updateSels = new SelectorItemCollection();
		updateSels.add("head");
		
		OtherBillInfo info=this.getOtherBillInfo(ctx, new ObjectUuidPK(billId),sels);
		for(int i=0;i<info.getPayEntry().size();i++){
			if(info.getPayEntry().get(i).getHead()==null){
				info.getPayEntry().get(i).setHead(info.getTenancyBill());
				TenBillOtherPayFactory.getLocalInstance(ctx).updatePartial(info.getPayEntry().get(i), updateSels);
			}
		}
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("tenancyBill.id");
		sels.add("payEntry.*");
		sels.add("payEntry.moneyDefine.*");
		sels.add("payEntry.head");
		
		OtherBillInfo info=this.getOtherBillInfo(ctx, new ObjectUuidPK(billId),sels);
		for(int i=0;i<info.getPayEntry().size();i++){
			if(info.getPayEntry().get(i).getActRevAmount()!=null&&info.getPayEntry().get(i).getActRevAmount().compareTo(FDCHelper.ZERO) != 0){
				throw new EASBizException(new NumericExceptionSubItem("101","���"+info.getPayEntry().get(i).getMoneyDefine().getName()+"�Ѿ��տ�,���ܽ��з�����������"));
			}
		}
		super._unAudit(ctx, billId);
		
		SelectorItemCollection updateSels = new SelectorItemCollection();
		updateSels.add("head");
		for(int i=0;i<info.getPayEntry().size();i++){
			if(info.getPayEntry().get(i).getHead()!=null){
				info.getPayEntry().get(i).setHead(null);
				TenBillOtherPayFactory.getLocalInstance(ctx).updatePartial(info.getPayEntry().get(i), updateSels);
			}
		}
	}
	
	@Override
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		// ����˰�ʷ���
		syncMoneyDefinAndTax(ctx, model);
		
		return super._save(ctx, model);
	}
	private void syncMoneyDefinAndTax(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
//		OtherBillInfo info = (OtherBillInfo) model;
//		OtherMoneyDefineTaxCollection cols = info.getTaxEntry();
//		OtherMoneyDefineTaxInfo otx = null;
//		String tenancyBillID = info.getTenancyBill().getId().toString();
//		for(Iterator<OtherMoneyDefineTaxInfo> it = cols.iterator();it.hasNext();){
//			otx = it.next();
//			MoneyDefineAndTaxInfo mdat = new MoneyDefineAndTaxInfo();
//			mdat.setTaxRate(otx.getTaxRate());
//			mdat.setTenancyBill(info.getTenancyBill());
//			mdat.setMoneyDefine(otx.getMoneyDefine());
//			MoneyDefineAndTaxFactory.getLocalInstance(ctx).addnew(mdat);
//		}
	}
	
	@Override
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		syncMoneyDefinAndTax(ctx, model);
		return super._submit(ctx, model);
	}
}