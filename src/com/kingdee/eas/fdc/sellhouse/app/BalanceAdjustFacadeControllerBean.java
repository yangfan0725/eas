package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.GatheringEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fi.cas.AssItemsForCashRecCollection;
import com.kingdee.eas.fi.cas.AssItemsForCashRecInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.ReceivingBillEntryCollection;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class BalanceAdjustFacadeControllerBean extends AbstractBalanceAdjustFacadeControllerBean
{
    private static final String ADJUST_PREFIX = "Adjust_Prefix_";
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.BalanceAdjustFacadeControllerBean");
    
    /**
     * �½������
     * 1.�����տ(�����Ͻ�������¥���տ)
     * 2.ֻ�ܶ����½��ڼ���տ������
     * 3.��̨�Զ����ɵ������͵��տ,�տ����ͬԭ�տ��ȫһ�µ����Ϊ����.
     * 4.ת��ĵ���,��̨Ҫ�Զ�ɾ��ԭת���Ӧ�ĺ�嵥,ͬʱ��ԭԭ�տ�Ŀɺ������Ѻ����,ʵ�ս�����.ͬʱ�ٷ�д�տ��Ӧ���տ���ϸ.
     * 5.���Ԥ�����͵��տ�Ѵ��ں�嵥���ܵ���.
     * 6.�����������ٱ�����.
     * 7.�����������ı�ԭ�տ��״̬���������(����ͨ����������),Ŀǰ�ݲ�����,�ֳ���Ϊ����..
     * 8.������������ƾ֤.
     * @param id �����տID
     * 
     * <br>
     * 1.���ز��տ�����ֶΣ��Ƿ������ϡ�
     * 2.���ز��տ�����ֶΣ�ԭ�տID��������ʶ������������ĸ��տ���еĵ�����
     * 3.
     * 
     * @param isInv �Ƿ�д����տ���ϸ��ʵ�յ�����
     * @param isVerify �Ƿ�У���ܷ����
     * 
     * PS:��Ҳ������ôд��������Ŀǰ�տ������ô����...
     * */
    protected void _blankOutRevBill(Context ctx, String id, boolean isInv, boolean isVerify)throws BOSException, EASBizException{
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("*");
    	sels.add("entries.*");
    	sels.add("fdcReceiveBill.*");
    	sels.add("fdcReceiveBill.purchase.*");
    	sels.add("fdcReceiveBill.customerEntrys.*");
    	sels.add("fdcReceiveBill.distillCommisionEntry.*");
    	sels.add("fdcReceiveBillEntry.*");
    	sels.add("fdcReceiveBillEntry.moneyDefine.*");
    	ReceivingBillInfo rev = ReceivingBillFactory.getLocalInstance(ctx).getReceivingBillInfo(new ObjectUuidPK(id), sels);
    	
    	FDCReceiveBillInfo fdcRev = rev.getFdcReceiveBill();
    	if(fdcRev == null){
    		throw new EASBizException(new NumericExceptionSubItem("101","���ݴ����տ��������"));
    	}
    	
    	//���������ܵ���
    	if(ReceiveBillTypeEnum.adjust.equals(fdcRev.getBillTypeEnum())){
    		if(isVerify){
    			throw new EASBizException(new NumericExceptionSubItem("100","��������������е�����"));
    		}else{
    			//��������������е�����
    			return;
    		}
    	}
    	
    	//����Ƿ��������
    	if(!MoneySysTypeEnum.SalehouseSys.equals(fdcRev.getMoneySysType())){
    		throw new EASBizException(new NumericExceptionSubItem("102","ֻ�ܶ���¥ϵͳ���տ���е�����"));
    	}
    	
    	if(fdcRev.isIsBlankOut()){
    		if(isVerify){
    			throw new EASBizException(new NumericExceptionSubItem("104","�Ѿ��������տ�����ٽ��е�����"));
    		}else{//����Ѿ�������,ֱ�ӷ���
    			return;
    		}
    	}
    	
    	if(ReceiveBillTypeEnum.settlement.equals(fdcRev.getBillTypeEnum())){
    		if(isVerify){
    			throw new EASBizException(new NumericExceptionSubItem("103","ֻ�ܶԸõ��ݵ�ת����е�����ϵͳ���Զ������˺�嵥��"));
    		}else{
    			//��嵥���������ϣ������϶�Ӧת�ʱͬʱ���Ϻ�嵥.
    			return;
    		}
    	}
    	
    	//TODO
    	//�����Ԥ������տ������Ԥ�����Ƿ��Ѿ��˿�����Ѿ�ת���������棬����տ������������ϲ���
    	
    	if(isVerify){
    		for(int i=0; i<rev.getFdcReceiveBillEntry().size(); i++){
    			FDCReceiveBillEntryInfo fdcRevEntry = rev.getFdcReceiveBillEntry().get(i);
    			BigDecimal counteractAmount = fdcRevEntry.getCounteractAmount();
    			if(counteractAmount != null  &&  counteractAmount.compareTo(FDCHelper.ZERO) > 0){
    				//������˽���0���������������
//    				FilterInfo tmpFilter = new FilterInfo();
//    				tmpFilter.getFilterItems().add(new FilterItemInfo("FCounteractId", fdcRevEntry.getId().toString()));
//    				tmpFilter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.isBlankOut", Boolean.FALSE));
//    				if(FDCReceiveBillEntryFactory.getLocalInstance(ctx).exists(tmpFilter)){
    					throw new EASBizException(new NumericExceptionSubItem("105","���տ������߱��˹��������������"));
//    				}
    			}
    		}
    	}
    	
    	//�ύһ����ԭ�տ�տ����෴���տ
    	ReceivingBillInfo adjustRev = buildAdjustRev(ctx, rev);
    	FDCReceiveBillInfo adjustFdcRev = adjustRev.getFdcReceiveBill();
    	FDCReceiveBillFactory.getLocalInstance(ctx).submit(adjustFdcRev);
    	ReceivingBillFactory.getLocalInstance(ctx).submit(adjustRev);
    	
    	//����ǰ�տ��Ϊ����
    	fdcRev.setIsBlankOut(true);
    	SelectorItemCollection blankOutSels = new SelectorItemCollection();
    	blankOutSels.add("isBlankOut");
    	FDCReceiveBillFactory.getLocalInstance(ctx).updatePartial(fdcRev, blankOutSels);
    	
    	//��Ҫ������صķ�д��������Ҫ����ԭ�տ���տ�˿��ת��
    	ReceiveBillTypeEnum billType = fdcRev.getBillTypeEnum();
    	if(ReceiveBillTypeEnum.gathering.equals(billType)){
    		//�տ���Ҫ��д��Ӧ������ϸ��ʵ�ս��
    		for(int i=0; i<rev.getFdcReceiveBillEntry().size(); i++){
    			FDCReceiveBillEntryInfo fdcRevEntry = rev.getFdcReceiveBillEntry().get(i);
    			String payId = fdcRevEntry.getPayListId();
    			BigDecimal amount = fdcRevEntry.getAmount();
    			if(amount == null) amount = FDCHelper.ZERO;
    			boolean isValidPayId = BOSUuid.isValid(payId, true);
    			
    			if(GatheringEnum.SinPurchase.equals(fdcRev.getGathering())){
    				if(!isValidPayId){
    					logger.error("������տ��¼��" + fdcRevEntry.getId().toString());
    					throw new EASBizException(new NumericExceptionSubItem("106","������������.revId" + id));
    				}
        			SincerReceiveEntryInfo sinPay = null;
					try {
						sinPay = SincerReceiveEntryFactory.getLocalInstance(ctx).getSincerReceiveEntryInfo(new ObjectUuidPK(payId));
					} catch (EASBizException e) {
						logger.error("������տ��¼��" + fdcRevEntry.getId().toString());
						throw new EASBizException(new NumericExceptionSubItem("106","������������.revId" + id));
					}
        			BigDecimal actAmount = sinPay.getActRevAmount();
        			if(actAmount == null) actAmount = FDCHelper.ZERO;
        			BigDecimal canRefundmentAmount = sinPay.getCanRefundmentAmount();
        			if(canRefundmentAmount == null) canRefundmentAmount = FDCHelper.ZERO;
        			
        			sinPay.setActRevAmount(actAmount.subtract(amount));
//        			sinPay.setLimitAmount(canRefundmentAmount.subtract(amount));
        			SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
        			tmpUpdateSels.add("actAmount");
        			tmpUpdateSels.add("canRefundmentAmount");
        			if(isInv)SincerReceiveEntryFactory.getLocalInstance(ctx).updatePartial(sinPay, tmpUpdateSels);
    			}else{//�տ����Ϊ���۷������Ϊ�ա�Ϊ��ʱҲ�������۷��䴦��
    				//���۷�����տ�����и�����ϸ��������ϸ��Ԥ���𼰲����
    				
    				PurchaseInfo purchase = fdcRev.getPurchase();
    				if(purchase == null){
    					logger.error("������������.revId" + id);
    					throw new EASBizException(new NumericExceptionSubItem("106","������������.revId" + id));
    				}
    				
    				//�����Ӧ���Ϲ����Ѿ��˷����ϻ��߻������ϣ�����տ������������
    				if(isVerify  &&  (PurchaseStateEnum.QuitRoomBlankOut.equals(purchase.getPurchaseState())
    						|| PurchaseStateEnum.ChangeRoomBlankOut.equals(purchase.getPurchaseState()))){
    					throw new EASBizException(new NumericExceptionSubItem("107","�Ϲ����Ѿ����ϣ����տ�����ٵ�����"));
    				}
    				
    				if(isValidPayId){//
    					//��Ч��payId�������Ǹ�����ϸ��������ϸ��
    					PurchasePayListEntryInfo purPay = null;
						try {
							purPay = PurchasePayListEntryFactory.getLocalInstance(ctx).getPurchasePayListEntryInfo(new ObjectUuidPK(payId));
						} catch (EASBizException e) {
							e.printStackTrace();
						}
    					if(purPay != null){
    						BigDecimal actAmount = purPay.getActPayAmount();
		        			if(actAmount == null) actAmount = FDCHelper.ZERO;
		        			
		        			purPay.setActRevAmount(actAmount.subtract(amount));
		        			SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
		        			tmpUpdateSels.add("actPayAmount");
//		        			if(isInv)PurchasePayListEntryFactory.getLocalInstance(ctx).updatePartial(purPay, tmpUpdateSels);
    					}else{
    						PurchaseElsePayListEntryInfo elsePay = PurchaseElsePayListEntryFactory.getLocalInstance(ctx).getPurchaseElsePayListEntryInfo(new ObjectUuidPK(payId));
    						if(elsePay != null){
    							BigDecimal actAmount = elsePay.getActPayAmount();	
    		        			if(actAmount == null) actAmount = FDCHelper.ZERO;
    		        			
    		        			elsePay.setActRevAmount(actAmount.subtract(amount));
    		        			SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
    		        			tmpUpdateSels.add("actPayAmount");
//    		        			if(isInv)PurchaseElsePayListEntryFactory.getLocalInstance(ctx).updatePartial(elsePay, tmpUpdateSels);
    						}else{
    							//�Ҳ���payId��Ӧ����ϸ�����տ������Ԥ���𣬲����
    							//TODO 
    						}
    					}
    				}else{//���Ϸ���payId�����տ������Ԥ���𣬲����
    					//���ô���
    				}
    			}
    		}
    	}else if(ReceiveBillTypeEnum.refundment.equals(billType)){
    		//�˿���Ҫ��дʵ�ս��.�Լ����˽������˽��
    		for(int i=0; i<rev.getFdcReceiveBillEntry().size(); i++){
    			FDCReceiveBillEntryInfo fdcRevEntry = rev.getFdcReceiveBillEntry().get(i);
    			String payId = fdcRevEntry.getPayListId();
    			
    			BigDecimal amount = fdcRevEntry.getAmount();
    			if(amount == null) amount = FDCHelper.ZERO;
    			boolean isValidPayId = BOSUuid.isValid(payId, true);
    			
    			if(GatheringEnum.SinPurchase.equals(fdcRev.getGathering())){
    				if(!isValidPayId){
    					logger.error("������տ��¼��" + fdcRevEntry.getId().toString());
    					throw new EASBizException(new NumericExceptionSubItem("106","������������.revId" + id));
    				}
    				SincerReceiveEntryInfo sinPay = null;
    				try{
    					sinPay = SincerReceiveEntryFactory.getLocalInstance(ctx).getSincerReceiveEntryInfo(new ObjectUuidPK(payId));
    				}catch(EASBizException e){
    				}
    				
    				if(sinPay == null){
    					throw new EASBizException(new NumericExceptionSubItem("106","������������.revId" + id));
    				}
    				
        			//�����Ϲ��˿�ʱ��δ�Ը�����ϸ��ʵ������д
        			//���ö�ʵ������д������Ҫ�Կ��˽������˽������д
        			BigDecimal canRefundmentAmount = sinPay.getCanRefundmentAmount();
    				if(canRefundmentAmount == null) canRefundmentAmount = FDCHelper.ZERO;
        			BigDecimal refundmentAmount = sinPay.getRefundmentAmount();
        			if(refundmentAmount == null) refundmentAmount = FDCHelper.ZERO;
        			
//        			sinPay.setLimitAmount(canRefundmentAmount.add(amount.abs()));
//        			sinPay.setHasRefundmentAmount(refundmentAmount.subtract(amount.abs()));
        			SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
        			tmpUpdateSels.add("canRefundmentAmount");
        			tmpUpdateSels.add("refundmentAmount");
        			
        			if(isInv)SincerReceiveEntryFactory.getLocalInstance(ctx).updatePartial(sinPay, tmpUpdateSels);
        			
        			//��Ӧ���տ��ҲҪ���п��˽����޸� TODO
    			}else if(GatheringEnum.CustomerRev.equals(fdcRev.getGathering())){//�˿��������ݶ�����˿
    				//�ҵ���Ӧ���ݶ��Ȼ��д��Ӧ�Ŀ��˽��
    				//TODO �����Ĺ��ܣ��Ȳ�����
    			}else{//�˿����Ϊ���۷������Ϊ�գ�Ϊ��ʱ�������۷��䴦��
    				if(isValidPayId){
    					//������ϸ���˿��������ϸ���˿�.
    					//��Ҫ��д���˽����˽���ʵ�ս��
    					PurchasePayListEntryInfo purPay = null;
						try {
							purPay = PurchasePayListEntryFactory.getLocalInstance(ctx).getPurchasePayListEntryInfo(new ObjectUuidPK(payId));
						} catch (EASBizException e) {
							e.printStackTrace();
						}
    					if(purPay != null){
    						BigDecimal canRefundmentAmount = purPay.getCanRefundmentAmount();
    	    				if(canRefundmentAmount == null) canRefundmentAmount = FDCHelper.ZERO;
    	        			BigDecimal refundmentAmount = purPay.getRefundmentAmount();
    	        			if(refundmentAmount == null) refundmentAmount = FDCHelper.ZERO;
    						BigDecimal actAmount = purPay.getActPayAmount();
    						if(actAmount == null) actAmount = FDCHelper.ZERO;
    						
    						purPay.setLimitAmount(canRefundmentAmount.add(amount.abs()));
    						purPay.setHasRefundmentAmount(refundmentAmount.subtract(amount.abs()));
    						purPay.setActRevAmount(actAmount.add(amount.abs()));
    						
    						SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
    	        			tmpUpdateSels.add("canRefundmentAmount");
    	        			tmpUpdateSels.add("refundmentAmount");
    	        			tmpUpdateSels.add("actPayAmount");
    						
//    	        			if(isInv)PurchasePayListEntryFactory.getLocalInstance(ctx).updatePartial(purPay, tmpUpdateSels);
    					}else{
    						PurchaseElsePayListEntryInfo elsePay = null;
							try {
								elsePay = PurchaseElsePayListEntryFactory.getLocalInstance(ctx).getPurchaseElsePayListEntryInfo(new ObjectUuidPK(payId));
							} catch (EASBizException e) {
								e.printStackTrace();
							}
    						if(elsePay != null){
    							BigDecimal canRefundmentAmount = elsePay.getCanRefundmentAmount();
        	    				if(canRefundmentAmount == null) canRefundmentAmount = FDCHelper.ZERO;
        	        			BigDecimal refundmentAmount = elsePay.getRefundmentAmount();
        	        			if(refundmentAmount == null) refundmentAmount = FDCHelper.ZERO;
        						BigDecimal actAmount = elsePay.getActPayAmount();
        						if(actAmount == null) actAmount = FDCHelper.ZERO;
        						
        						elsePay.setLimitAmount(canRefundmentAmount.add(amount.abs()));
        						elsePay.setHasRefundmentAmount(refundmentAmount.subtract(amount.abs()));
        						elsePay.setActRevAmount(actAmount.add(amount.abs()));
        						
        						SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
        	        			tmpUpdateSels.add("canRefundmentAmount");
        	        			tmpUpdateSels.add("refundmentAmount");
        	        			tmpUpdateSels.add("actPayAmount");
        						
//        	        			if(isInv)PurchaseElsePayListEntryFactory.getLocalInstance(ctx).updatePartial(elsePay, tmpUpdateSels);
    						}else{
    							//Ԥ������˿�,�������˿�,��Ӧ����5
    							//TODO Ҫ�ҵ���Ӧ��Ԥ�����տ��д����˽��
    							if(isInv){
    								PurchaseInfo purchase = fdcRev.getPurchase();
    								if(purchase == null){
    			    					logger.error("������������.revId" + id);
    			    					throw new EASBizException(new NumericExceptionSubItem("106","������������.revId" + id));
    			    				}
    								String purId = purchase.getId().toString();
    								//TODO 
    							}
    						}
    					}
    				}else{
    					//Ԥ������˿�,�������˿�
    					//TODO
    				}
    			}
    		}
    	}else if(ReceiveBillTypeEnum.transfer.equals(billType)){
    		//ת����Ҫ�ۺ�����2�ַ�д����ת��������ϣ�ͬʱ�����Ӧ�ĺ�嵥��������
    		//ת�������۷��䣬�ͻ���2��
    		PurchaseInfo purchase = fdcRev.getPurchase();			
			if(purchase != null  &&  isVerify  &&  (PurchaseStateEnum.QuitRoomBlankOut.equals(purchase.getPurchaseState())
					|| PurchaseStateEnum.ChangeRoomBlankOut.equals(purchase.getPurchaseState()))){
				throw new EASBizException(new NumericExceptionSubItem("107","�Ϲ����Ѿ����ϣ���ת������ٵ�����"));
			}
    		
    		for(int i=0; i<rev.getFdcReceiveBillEntry().size(); i++){
    			FDCReceiveBillEntryInfo fdcRevEntry = rev.getFdcReceiveBillEntry().get(i);
    			
    			MoneyDefineInfo moneyDefine = fdcRevEntry.getMoneyDefine();
    			//����ת��������ѣ�˵�����˷���ת�򻻷���ת���ɵ�.
    			if(MoneyTypeEnum.CommissionCharge.equals(moneyDefine.getMoneyType())){
        			if(isVerify){
        				throw new EASBizException(new NumericExceptionSubItem("112","�������ɵ������Ѳ����������"));
        			}
    			}
    			
    			String payId = fdcRevEntry.getPayListId();
    			
    			BigDecimal amount = fdcRevEntry.getAmount();
    			if(amount == null) amount = FDCHelper.ZERO;
    			boolean isValidPayId = BOSUuid.isValid(payId, true);
    			
    			if(isValidPayId){
    				PurchasePayListEntryInfo purPay = null;
					try {
						purPay = PurchasePayListEntryFactory.getLocalInstance(ctx).getPurchasePayListEntryInfo(new ObjectUuidPK(payId));
					} catch (EASBizException e) {
						e.printStackTrace();
					}
    				if(purPay != null){
    					BigDecimal actAmount = purPay.getActPayAmount();	
	        			if(actAmount == null) actAmount = FDCHelper.ZERO;
	        			
	        			purPay.setActRevAmount(actAmount.subtract(amount));
	        			SelectorItemCollection tmpUpdateSels = new SelectorItemCollection();
	        			tmpUpdateSels.add("actPayAmount");
//	        			if(isInv)PurchasePayListEntryFactory.getLocalInstance(ctx).updatePartial(purPay, tmpUpdateSels);
    				}else{//Ϊ�������ΪԤ����, ���ô���
    					
    				}
    			}else{//payIdΪ�գ�˵��ת��Ϊ�˷��������ѣ����߻����������ѣ����߻������м��
    				//���ô���
    			}
    		}
    		
    		//�����ת����տ�����ǿͻ�����˵���� ��������
    		if(GatheringEnum.CustomerRev.equals(fdcRev.getGathering())){
    			if(isVerify){
    				throw new EASBizException(new NumericExceptionSubItem("112","�����������ɵ�ת����������"));
    			}
    		}
    		
    		ReceivingBillInfo settleRev = fdcRev.getSettlementBill();
    		if(settleRev == null){
    			logger.error("ת�û���ҵ���Ӧ�ĺ�嵥ѽ��");
    			throw new EASBizException(new NumericExceptionSubItem("111","ת�û���ҵ���Ӧ�ĺ�嵥ѽ��"));
    		}
    		
        	settleRev = ReceivingBillFactory.getLocalInstance(ctx).getReceivingBillInfo(new ObjectUuidPK(settleRev.getId()), sels);
        	
    		//�������嵥��ҲҪ���е���
    		ReceivingBillInfo adjustSettleRev = buildAdjustRev(ctx, settleRev);
        	FDCReceiveBillInfo adjustSettleFdcRev = adjustSettleRev.getFdcReceiveBill();
        	//�ύ��һ����ԭ�տ�տ����෴���տ
        	FDCReceiveBillFactory.getLocalInstance(ctx).submit(adjustSettleFdcRev);
        	ReceivingBillFactory.getLocalInstance(ctx).submit(adjustSettleRev);
        	
        	//���ϸú�嵥
        	adjustSettleFdcRev.setIsBlankOut(true);
        	FDCReceiveBillFactory.getLocalInstance(ctx).updatePartial(fdcRev, blankOutSels);
        	
        	//��дԭ�������տ�Ŀɳ�����ѳ������и�����ϸ�Ļ�����Ҫ��д������ϸ�Ŀɳ�����ѳ��ʵ�ս��
        	//TODO �˴�ת�����ֻ��������ת�Ŀ��Ե���
        	//ת�������Ѻ��ݶ���������
        	for(int i=0; i<settleRev.getFdcReceiveBillEntry().size(); i++){
        		FDCReceiveBillEntryInfo fdcRevEntry = settleRev.getFdcReceiveBillEntry().get(i);
        		
        		BigDecimal amount = fdcRevEntry.getAmount();
        		if(amount == null) amount = FDCHelper.ZERO;
        		
        		String counteractId = fdcRevEntry.getFCounteractId();
        		if(!BOSUuid.isValid(counteractId, true)){
        			logger.error("��嵥�Ҳ�����Ӧ�ı���嵥�ݣ�");
        			throw new EASBizException(new NumericExceptionSubItem("112","��嵥�Ҳ�����Ӧ�ı���嵥�ݣ�"));
        		}
        		
        		FDCReceiveBillEntryInfo counteractRevEntry = FDCReceiveBillEntryFactory.getLocalInstance(ctx).getFDCReceiveBillEntryInfo(new ObjectUuidPK(counteractId));
        		
        		BigDecimal cCan = counteractRevEntry.getCanCounteractAmount();
        		if(cCan == null) cCan = FDCHelper.ZERO;
        		
        		BigDecimal cCou = counteractRevEntry.getCounteractAmount();
        		if(cCou == null) cCou = FDCHelper.ZERO;
        		
        		counteractRevEntry.setCanCounteractAmount(cCan.add(amount.abs()));
        		counteractRevEntry.setCounteractAmount(cCou.subtract(amount.abs()));
        		
        		SelectorItemCollection cUpdateSels = new SelectorItemCollection();
        		cUpdateSels.add("canCounteractAmount");
        		cUpdateSels.add("counteractAmount");
        		if(isInv)FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(counteractRevEntry, cUpdateSels);
        		
        		//�ҵ�����嵥�ĸ�����ϸ�����ڳ����ת�Ҳ��Ҫ��д������ϸ�Ŀ��˽����ֶ�
        		String counteractPayId = counteractRevEntry.getPayListId();
        		
        		if(!BOSUuid.isValid(counteractPayId, true)){
        			//������ݶ���ת�ķ����嵥��û��Ӧ����ϸ��.
        			continue;
//        			logger.error("����嵥�Ҳ�����Ӧ�ĳ����Ϲ��տ���ϸ��");
//        			throw new EASBizException(new NumericExceptionSubItem("112","����嵥�Ҳ�����Ӧ�ĳ����Ϲ��տ���ϸ��"));
        		}
        		
        		SincerReceiveEntryInfo sinPay = null;
				try {
					sinPay = SincerReceiveEntryFactory.getLocalInstance(ctx).getSincerReceiveEntryInfo(new ObjectUuidPK(counteractPayId));
				} catch (EASBizException e) {
					e.printStackTrace();
				}
				if(sinPay == null){
					continue;
				}
        		BigDecimal sinCanRefundmentAmount = sinPay.getCanRefundmentAmount();
        		if(sinCanRefundmentAmount == null) sinCanRefundmentAmount = FDCHelper.ZERO;
        		BigDecimal sinRefundmentAmount = sinPay.getRefundmentAmount();
        		if(sinRefundmentAmount == null) sinRefundmentAmount = FDCHelper.ZERO;
        		
//        		sinPay.setLimitAmount(sinCanRefundmentAmount.add(amount.abs()));
//        		sinPay.setRefundmentAmount(sinRefundmentAmount.subtract(amount.abs()));
        		SelectorItemCollection sinUpdateSels = new SelectorItemCollection();
        		sinUpdateSels.add("canRefundmentAmount");
//        		sinUpdateSels.add("refundmentAmount");
        		if(isInv)SincerReceiveEntryFactory.getLocalInstance(ctx).updatePartial(sinPay, sinUpdateSels);
        	}
    	}
    }
    
    /**
     * ����ԭ����������һ�ŵ������͵��տ�������ԭ�տ�෴.
     * @throws BOSException 
     * @throws EASBizException 
     * */
    private ReceivingBillInfo buildAdjustRev(Context ctx, ReceivingBillInfo rev) throws BOSException, EASBizException{
    	ReceivingBillInfo adjustRev = (ReceivingBillInfo) rev.clone();
    	FDCReceiveBillInfo adjustFdcRev = adjustRev.getFdcReceiveBill();
    	adjustRev.setId(null);
    	adjustRev.setBillStatus(BillStatusEnum.SUBMIT);
    	Timestamp t = FDCSQLFacadeFactory.getLocalInstance(ctx).getServerTime();
    	adjustRev.setBizDate(t);
    	adjustRev.setCreateTime(t);
//    	adjustRev.setIsAppointVoucher(false);
    	adjustRev.setFiVouchered(false);
    	
		OrgUnitInfo crrOu = ContextUtil.getCurrentSaleUnit(ctx);   //��ǰ������֯;
		if(crrOu==null)
			crrOu = ContextUtil.getCurrentOrgUnit(ctx);            //��ǰ��֯
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		if(iCodingRuleManager.isExist(new ReceivingBillInfo(), crrOu.getId().toString())){
			String number = iCodingRuleManager.getNumber(new ReceivingBillInfo(), crrOu.getId().toString());
			adjustRev.setNumber(number);
		}else{
			adjustRev.setNumber(ADJUST_PREFIX + rev.getNumber());
		}
		adjustFdcRev.setNumber(null);
		
    	BigDecimal srcRevAmount = adjustRev.getAmount();
		if(srcRevAmount != null){
			srcRevAmount = FDCHelper.ZERO.subtract(srcRevAmount);
		}
    	adjustRev.setAmount(srcRevAmount);
		adjustRev.setActRecAmt(srcRevAmount);
		
    	adjustFdcRev.setId(BOSUuid.create(new FDCReceiveBillInfo().getBOSType()));
    	adjustFdcRev.setBillTypeEnum(ReceiveBillTypeEnum.adjust);
    	adjustFdcRev.setAdjustSrcBill(rev);
    	//���ȫ�տ�ķ�Ʊ��Ʊ��
    	adjustFdcRev.setInvoice(null);
    	adjustFdcRev.setCheque(null);
    	adjustFdcRev.setReceiptNumber(null);
    	
    	FDCReceiveBillEntryCollection fdcRevEntrys = adjustRev.getFdcReceiveBillEntry();
    	for(int i=0; i<fdcRevEntrys.size(); i++){
    		FDCReceiveBillEntryInfo fdcRevEntry = fdcRevEntrys.get(i);
    		fdcRevEntry.setId(null);
    		BigDecimal srcRevEntryAmount = fdcRevEntry.getAmount();
    		if(srcRevEntryAmount != null){
    			srcRevEntryAmount = FDCHelper.ZERO.subtract(srcRevEntryAmount);
    		}
    		fdcRevEntry.setAmount(srcRevEntryAmount);
    	}
    	
    	CustomerEntryCollection customerEntrys = adjustFdcRev.getCustomerEntrys();
    	for(int i=0; i<customerEntrys.size(); i++){
    		CustomerEntryInfo customerEntry = customerEntrys.get(i);
    		customerEntry.setId(null);
    	}
    	
    	ReceiveDistillCommisionEntryCollection dises =  adjustFdcRev.getDistillCommisionEntry();
    	for(int i=0; i<dises.size(); i++){
    		ReceiveDistillCommisionEntryInfo dis = dises.get(i);
    		dis.setId(null);
    	}
    	
    	AssItemsForCashRecCollection assItems = adjustRev.getAssItems();
    	for(int i=0; i<assItems.size(); i++){
    		AssItemsForCashRecInfo assItem = assItems.get(i);
    		assItem.setId(null);
    	}
    	
    	ReceivingBillEntryCollection revEntrys = adjustRev.getEntries();
    	for(int i=0; i<revEntrys.size(); i++){
    		ReceivingBillEntryInfo revEntry = revEntrys.get(i);
    		revEntry.setId(null);
    	}
    	
    	return adjustRev;
    }
    
    //�����տ�����������ʵ�յķ�д
    private void blankOutRev(){
    	
    }
    
    /**
     * �����Ϲ�����ʵ�������˷����ƵĲ������������Ϊ����״̬����ͬʱ���ϸ��Ϲ������е��տ
     * */
    protected void _blankOutPurchase(Context ctx, String id)throws BOSException,EASBizException
    {
    }
}