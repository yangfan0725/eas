package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.BalanceAdjustFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.BillAdjustInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;

public class BillAdjustControllerBean extends AbstractBillAdjustControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.BillAdjustControllerBean");
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	IObjectPK res = super._submit(ctx, model);
    	//TODO ��ʱ����ʵ�֣������ʱ�򼴰ѵ���ְΪ��������ͬʱ��д������Ϣ���Ϲ���״̬
    	
    	BillAdjustInfo billAdjust = (BillAdjustInfo) model;
    	PurchaseInfo pur = billAdjust.getPurchase();
    	if(pur != null  &&  pur.getRoom() != null){
    		SheRoomStateFactory.setRoomOnShowState(ctx, pur.getRoom(),pur, false, true);
    	}else{
    		logger.error("������������.");
    		//throw new EASBizException(new NumericExceptionSubItem("106","������������"));
    	}
    	
    	this._audit(ctx, BOSUuid.read(res.toString()));
    	
    	//��Ҫ���϶�Ӧ���տ
    	if(pur != null){
    		EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		view.setFilter(filter);
    		view.getSelector().add("id");
    		view.getSelector().add("number");
    		
    		filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.purchase.id", pur.getId().toString()));
    		ReceivingBillCollection revs = ReceivingBillFactory.getLocalInstance(ctx).getReceivingBillCollection(view);
    		for(int i=0; i<revs.size(); i++){
    			logger.info("�����տ��" + revs.get(i).getNumber());
    			BalanceAdjustFacadeFactory.getLocalInstance(ctx).blankOutRevBill(revs.get(i).getId().toString(), false, false);
    		}
    	}
    	
    	return res;
    }    
}