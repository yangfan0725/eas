package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FeeMoneyTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BalanceAdjustFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.FeeFromTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitNewPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.QuitNewPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.QuitPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomChangeFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomChangeInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHEConstants;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class QuitRoomChangeControllerBean extends AbstractQuitRoomChangeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.QuitRoomChangeControllerBean");
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {

    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("*");
    	sels.add("quitRoom.*");
    	sels.add("quitRoom.purchase.payListEntry.*");
    	sels.add("quitRoom.purchase.elsePayListEntry.*");
    	sels.add("quitRoom.quitEntrys.*");
    	sels.add("quitNewPayList.*");
    	sels.add("quitNewPayList.moneyDefine.*");
    	sels.add("oldFeeMoneyDefine.name");
		sels.add("newFeeMoneyDefine.name");
		sels.add("oldFeeAccount.name");
		sels.add("newFeeAccount.name");
    	
		QuitRoomChangeInfo changeInfo = QuitRoomChangeFactory
				.getLocalInstance(ctx).getQuitRoomChangeInfo(new ObjectUuidPK(billId),sels);
		if(!changeInfo.getState().equals(FDCBillStateEnum.SUBMITTED)) {
			throw new EASBizException(new NumericExceptionSubItem("100","ֻ���ύ״̬�ı�������ܱ�������"));
		}
		
		QuitRoomInfo quitRoomInfo = QuitRoomFactory.getLocalInstance(ctx).getQuitRoomInfo(
				"select feeMoneyDefine,feeAccount,feeAmount,quitEntrys.*,purchase.id " +
				"where id = '"+changeInfo.getQuitRoom().getId()+"'");
		quitRoomInfo.setFeeAccount(changeInfo.getNewFeeAccount());
		quitRoomInfo.setFeeMoneyDefine(changeInfo.getNewFeeMoneyDefine());
		BigDecimal feeAmount = changeInfo.getNewFeeAmount();
		feeAmount = feeAmount==null?new BigDecimal(0):feeAmount;
		quitRoomInfo.setFeeAmount(feeAmount);
		QuitPayListEntryCollection quitPayEntryColl = quitRoomInfo.getQuitEntrys();
		QuitNewPayListEntryCollection newPayColl = changeInfo.getQuitNewPayList();
		if(quitPayEntryColl.size()!=newPayColl.size()) {
			throw new EASBizException(new NumericExceptionSubItem("100","�����ϸ��ԭ�˷�������ϸ�����Ѳ�һ�£��޷����������飡"));
		}
		
		//��������id + seq 
		Map newPayListMap = new HashMap();
		for(int i=0;i<newPayColl.size();i++) {
			QuitNewPayListEntryInfo newPayInfo = newPayColl.get(i);
			newPayListMap.put(newPayInfo.getMoneyDefine().getId()+""+newPayInfo.getSeq(), newPayInfo);
		}
		
		for(int i=0;i<quitPayEntryColl.size();i++) {
			QuitPayListEntryInfo quitPayEntryInfo = quitPayEntryColl.get(i); 
			String keyMap = quitPayEntryInfo.getMoneyDefine().getId()+""+quitPayEntryInfo.getSeq();
			
			QuitNewPayListEntryInfo newPayInfo = (QuitNewPayListEntryInfo)newPayListMap.get(keyMap);
			if(newPayInfo==null || !quitPayEntryInfo.getMoneyDefine().getId().equals(newPayInfo.getMoneyDefine().getId())) 
				throw new EASBizException(new NumericExceptionSubItem("100","�����ϸ��ԭ�˷�������ϸ�ĵ�"+(i+1)+"���Ŀ��һ�£��޷����������飡"));
			quitPayEntryInfo.setCanRefundmentAmount(newPayInfo.getCanRefundmentAmount());
			
			String payListId = quitPayEntryInfo.getPayListId();
			if(payListId==null || payListId.trim().length()==0)
				throw new EASBizException(new NumericExceptionSubItem("100","�����ϸ�ĵ�"+(i+1)+"���Ŀ����Ҳ�����Ӧ���տ���ϸ���޷����������飡"));
			
			SelectorItemCollection updateSels = new SelectorItemCollection();
			updateSels.add("limitAmount");
			if(quitPayEntryInfo.getFeeFromType().equals(FeeFromTypeEnum.ShouldPayList)){
				PurchasePayListEntryInfo pay = PurchasePayListEntryFactory.getLocalInstance(ctx)
							.getPurchasePayListEntryInfo("select * where id= '"+payListId+"' ");
				BigDecimal canRefundAmount = newPayInfo.getCanRefundmentAmount();
				canRefundAmount = canRefundAmount.add(pay.getHasRefundmentAmount()).add(pay.getHasTransferredAmount()).add(pay.getHasAdjustedAmount());
				pay.setLimitAmount(canRefundAmount);
				PurchasePayListEntryFactory.getLocalInstance(ctx).updatePartial(pay, updateSels);
			}else if(quitPayEntryInfo.getFeeFromType().equals(FeeFromTypeEnum.ElsePayList)){
				PurchaseElsePayListEntryInfo elsePay = PurchaseElsePayListEntryFactory.getLocalInstance(ctx)
								.getPurchaseElsePayListEntryInfo("select * where id ='"+payListId+"' ");
				BigDecimal canRefundAmount = newPayInfo.getCanRefundmentAmount();
				canRefundAmount = canRefundAmount.add(elsePay.getHasRefundmentAmount()).add(elsePay.getHasTransferredAmount()).add(elsePay.getHasAdjustedAmount());
				elsePay.setLimitAmount(canRefundAmount);
				PurchaseElsePayListEntryFactory.getLocalInstance(ctx).updatePartial(elsePay, updateSels);
			}
			
		}
		
		SelectorItemCollection sels2 = new SelectorItemCollection();
		sels2.add("feeMoneyDefine");
		sels2.add("feeAccount");
		sels2.add("feeAmount");	
		sels2.add("quitEntrys.canRefundmentAmount");
		QuitRoomFactory.getLocalInstance(ctx).updatePartial(quitRoomInfo, sels2);
    	

		//�˷��������,������Ӧ����ϸ�еķ��õ�Ӧ�� 
		PurchaseElsePayListEntryCollection newElseEntryColl = PurchaseElsePayListEntryFactory.getLocalInstance(ctx)
				.getPurchaseElsePayListEntryCollection("where head.id='"+quitRoomInfo.getPurchase().getId()+"' and appAmount= "+changeInfo.getOldFeeAmount()+" " +
						" and moneyDefine.id='"+changeInfo.getOldFeeMoneyDefine().getId()+"' and feeMoneyType='"+FeeMoneyTypeEnum.FEE_VALUE+"'  ");
		if(newElseEntryColl.isEmpty()) {  //Ϊ�յ����������Ӧ�ò�����
			PurchaseElsePayListEntryInfo newElseEntry = new PurchaseElsePayListEntryInfo();
			newElseEntry.setHead(quitRoomInfo.getPurchase());
			newElseEntry.setMoneyDefine(quitRoomInfo.getFeeMoneyDefine());
			CompanyOrgUnitInfo currentCompany = ContextUtil.getCurrentFIUnit(ctx);
			CurrencyInfo baseCurrency = CurrencyFactory.getLocalInstance(ctx)
					.getCurrencyInfo(new ObjectUuidPK(BOSUuid.read(currentCompany.getBaseCurrency().getId().toString())));
			newElseEntry.setCurrency(baseCurrency);
			newElseEntry.setAppAmount(quitRoomInfo.getFeeAmount());
			newElseEntry.setAppDate(new Date());
			newElseEntry.setRevMoneyType(RevMoneyTypeEnum.AppRev);
			newElseEntry.setFeeMoneyType(FeeMoneyTypeEnum.Fee);
			newElseEntry.setSeq(quitRoomInfo.getPurchase().getElsePayListEntry().size());
			PurchaseElsePayListEntryFactory.getLocalInstance(ctx).addnew(newElseEntry);
		}else{
			PurchaseElsePayListEntryInfo newElseEntry = newElseEntryColl.get(0);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("moneyDefine");
			selector.add("appAmount");
			newElseEntry.setMoneyDefine(quitRoomInfo.getFeeMoneyDefine());
			newElseEntry.setAppAmount(quitRoomInfo.getFeeAmount());
			PurchaseElsePayListEntryFactory.getLocalInstance(ctx).updatePartial(newElseEntry,selector);
		}		
		
		super._audit(ctx, billId);
    }
    
    /**
	 * 
	 * �������Ƿ�ʹ�������ֶ�
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-10-17 <p>
	 */
    protected boolean isUseName() {
		return false;
	}
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {
     	super._delete(ctx, pk);
    }
}