package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;

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
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;

public class PrePurchaseManageControllerBean extends AbstractPrePurchaseManageControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.PrePurchaseManageControllerBean");

    protected void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	PrePurchaseManageInfo info=new PrePurchaseManageInfo();
    	SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("state");
		info.setId(billId);
		info.setBizState(TransactionStateEnum.PREAPPLE);
		info.setState(FDCBillStateEnum.SUBMITTED);
		_updatePartial(ctx, info, selector);
	}
    
    protected void _setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	PrePurchaseManageInfo info=new PrePurchaseManageInfo();
    	SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		info.setId(billId);
		info.setBizState(TransactionStateEnum.PREAUDITING);
		info.setState(FDCBillStateEnum.AUDITTING);
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime(new Date());
		_updatePartial(ctx, info, selector);
	}
    protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	PrePurchaseManageInfo info = ((PrePurchaseManageInfo) model);
		if(info.getBizState() == null)info.setBizState(TransactionStateEnum.PRESAVED);
		super._save(ctx, pk, model);
	}
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		PrePurchaseManageInfo info = ((PrePurchaseManageInfo) model);
		if(info.getBizState() == null)info.setBizState(TransactionStateEnum.PRESAVED);
		return super._save(ctx, model);
	}
	private IObjectPK submitAction(Context ctx, IObjectValue model) throws EASBizException, BOSException{
		IObjectPK pk=null;
		PrePurchaseManageInfo info=(PrePurchaseManageInfo)model;
		info.setBizState(TransactionStateEnum.PREAPPLE);
		RoomInfo room=info.getRoom();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("sellState");
		room.setSellState(RoomSellStateEnum.PrePurchase);
		RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
		
		for(int i=0;i<info.getPrePurchaseRoomAttachmentEntry().size();i++){
			RoomInfo attRoom=info.getPrePurchaseRoomAttachmentEntry().get(i).getRoom();
			attRoom.setSellState(RoomSellStateEnum.PrePurchase);
			RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, sels);
		}
		SHEManageHelper.updatePayActRevAmountAndBizState(ctx, info.getSrcId(), false, TransactionStateEnum.TOPRE);
		SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),false,RoomSellStateEnum.PrePurchase);
		pk=super._submit(ctx, model);
		SHEManageHelper.updateTransaction(ctx, info,RoomSellStateEnum.PrePurchase,true);
		return pk;
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
		return submitAction(ctx,model);
	}
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		submitAction(ctx,model);
		super._submit(ctx, pk, model);
	}
   
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		PrePurchaseManageInfo info=(PrePurchaseManageInfo)getValue(ctx, pk);
		
		if(TransactionStateEnum.PREAPPLE.equals(info.getBizState())||
				TransactionStateEnum.PRENULLIFY.equals(info.getBizState())){
			SHEManageHelper.checkHasSHERevBill(ctx,pk.toString(),"ɾ��");
		}
		RoomSellStateEnum roomState=null;
		if(!TransactionStateEnum.PRENULLIFY.equals(info.getBizState())&&!TransactionStateEnum.PRESAVED.equals(info.getBizState())){
			roomState=SHEManageHelper.toOldTransaction(ctx,info.getTransactionID());
		} 
		
		if(TransactionStateEnum.PREAPPLE.equals(info.getBizState())){
			SHEManageHelper.updatePayActRevAmountAndBizState(ctx, info.getSrcId(), true, TransactionStateEnum.TOPRE);
			SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),true,RoomSellStateEnum.PrePurchase);
			
			if(roomState!=null){
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("sellState");
				for(int i=0;i<info.getPrePurchaseRoomAttachmentEntry().size();i++){
					RoomInfo attRoom=info.getPrePurchaseRoomAttachmentEntry().get(i).getRoom();
					attRoom.setSellState(roomState);
					RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, sels);
				}
			}
		}
		super._delete(ctx, pk);
		
	}
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		ObjectUuidPK pk=new ObjectUuidPK(billId);
		PrePurchaseManageInfo info=(PrePurchaseManageInfo)this.getValue(ctx, pk);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime(new Date());
		info.setBizState(TransactionStateEnum.PREAUDIT);
		info.setState(FDCBillStateEnum.AUDITTED);
		_updatePartial(ctx, info, selector);
		
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		checkChangeManage(ctx,billId.toString());
		
		ObjectUuidPK pk=new ObjectUuidPK(billId);
		PrePurchaseManageInfo info=(PrePurchaseManageInfo)this.getValue(ctx, pk);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setBizState(TransactionStateEnum.PREAPPLE);
		info.setState(FDCBillStateEnum.SUBMITTED);
		_updatePartial(ctx, info, selector);
	}
	protected void _invalid(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		
		SHEManageHelper.checkHasSHERevBill(ctx,billId.toString(),"����");
		
		PrePurchaseManageInfo info=this.getPrePurchaseManageInfo(ctx, new ObjectUuidPK(billId));
		SelectorItemCollection selector = new SelectorItemCollection();
		
		selector.add("bizState");
		selector.add("state");
		selector.add("isValid");
		info.setState(FDCBillStateEnum.INVALID);
		info.setBizState(TransactionStateEnum.PRENULLIFY);
		info.setIsValid(true);
		_updatePartial(ctx, info, selector);
		
		SHEManageHelper.updatePayActRevAmountAndBizState(ctx, info.getSrcId(), true, TransactionStateEnum.TOPRE);
		SHEManageHelper.updateChooseRoomState(ctx,info.getSrcId(),true,RoomSellStateEnum.PrePurchase);
		RoomSellStateEnum roomState=SHEManageHelper.toOldTransaction(ctx,info.getTransactionID());
		
		if(roomState!=null){
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("sellState");
			for(int i=0;i<info.getPrePurchaseRoomAttachmentEntry().size();i++){
				RoomInfo attRoom=info.getPrePurchaseRoomAttachmentEntry().get(i).getRoom();
				attRoom.setSellState(roomState);
				RoomFactory.getLocalInstance(ctx).updatePartial(attRoom, sels);
			}
		}
	}
}