package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.dap.DAPTransformResult;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstActGroupDetailCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IProductType;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherCollection;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo;
import com.kingdee.eas.fi.cas.AssItemsForCashPayInfo;
import com.kingdee.eas.fi.cas.AssItemsForCashRecInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.CasRecPayBillTypeEnum;
import com.kingdee.eas.fi.cas.IPaymentBillType;
import com.kingdee.eas.fi.cas.IReceivingBillType;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillTypeCollection;
import com.kingdee.eas.fi.cas.PaymentBillTypeFactory;
import com.kingdee.eas.fi.cas.PaymentBillTypeInfo;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.ReceivingBillTypeCollection;
import com.kingdee.eas.fi.cas.ReceivingBillTypeFactory;
import com.kingdee.eas.fi.cas.ReceivingBillTypeInfo;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.fi.cas.app.ArApRecPayServerHelper;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.ma.budget.BgItemFactory;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.IBgItem;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class ReceiveGatherControllerBean extends AbstractReceiveGatherControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.ReceiveGatherControllerBean");
    
    ArApRecPayServerHelper arapHelper = new ArApRecPayServerHelper();
    
    protected void checkNameDup(Context ctx, FDCBillInfo billInfo) throws BOSException, EASBizException {
    }
    /*
     * ���ɻ��ܵ��ύʱ��д��Ӧ�տ���ϸ�ϵ��Ƿ�����ֶ�
     */
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	ReceiveGatherInfo revGatherInfo = (ReceiveGatherInfo)model;
//    	revGatherInfo = this.getRevGatherInfo(ctx, revGatherInfo.getId());
    	ReceiveGatherEntryCollection revEntryColl = revGatherInfo.getEntry();
    	SelectorItemCollection sel=new SelectorItemCollection();
    	sel.add("parent.*");
    	for(int i=0;i<revEntryColl.size();i++)
    	{
    		ReceiveGatherEntryInfo revEntryInfo = revEntryColl.get(i);
    		SHERevBillEntryInfo sheRevEntryInfo = SHERevBillEntryFactory.getLocalInstance(ctx).getSHERevBillEntryInfo(new ObjectUuidPK(revEntryInfo.getSheRevBill().getId()), sel);
    		sheRevEntryInfo.setIsGathered(true);
    		SelectorItemCollection sels = new SelectorItemCollection();
    		sels.add("isGathered");
    		SHERevBillFactory.getLocalInstance(ctx).updatePartial(sheRevEntryInfo, sels);
    		
    		sheRevEntryInfo.getParent().setIsGathered(true);
    		SHERevBillFactory.getLocalInstance(ctx).updatePartial(sheRevEntryInfo.getParent(), sels);
    	}
    	return super._submit(ctx, model);
    }
    
    /*
     *���зſ����ɵĳ��ɻ��ܵ�������ɾ�����ո��ֱ�����ɵĻ��ܵ�ɾ��ʱ
     *Ҫ�����Ӧ�ո�����Ƿ�����ֶ� 
     */
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
//    	EntityViewInfo view = new EntityViewInfo();
//    	FilterInfo filter = new FilterInfo();
//    	filter.getFilterItems().add(new FilterItemInfo("receiveGather.id", pk.toString(),CompareType.EQUALS));
//    	view.setFilter(filter);
//    	BankPaymentCollection bankColl = BankPaymentFactory.getLocalInstance(ctx).getBankPaymentCollection(view);
//    	if(bankColl.size()>0)
//    	{
//    		throw new EASBizException(new NumericExceptionSubItem("100","���зſ����ɵĳ��ɻ��ܵ�������ֱ��ɾ�����ſ���������Զ�ɾ����"));
//    	}else
//    	{
    		ReceiveGatherInfo revGatherInfo = this.getRevGatherInfo(ctx, BOSUuid.read(pk.toString()));
        	ReceiveGatherEntryCollection revEntryColl = revGatherInfo.getEntry();
        	
        	SelectorItemCollection sel=new SelectorItemCollection();
        	sel.add("parent.*");
        	
        	for(int i=0;i<revEntryColl.size();i++)
        	{
        		ReceiveGatherEntryInfo revEntryInfo = revEntryColl.get(i);
        		SHERevBillEntryInfo sheRevEntryInfo = SHERevBillEntryFactory.getLocalInstance(ctx).getSHERevBillEntryInfo(new ObjectUuidPK(revEntryInfo.getSheRevBill().getId()), sel);
        		sheRevEntryInfo.setIsGathered(false);
        		SelectorItemCollection sels = new SelectorItemCollection();
        		sels.add("isGathered");
        		SHERevBillEntryFactory.getLocalInstance(ctx).updatePartial(sheRevEntryInfo, sels);
        		
        		sheRevEntryInfo.getParent().setIsGathered(false);
        		SHERevBillFactory.getLocalInstance(ctx).updatePartial(sheRevEntryInfo.getParent(), sels);
        	}
//    	}   		   	   	
    	super._delete(ctx, pk);
    }
    
    /*
     * ���ܵ�����ʱ�Զ����ɶ�Ӧ�ĳ����տ
     */
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
//    	ReceiveGatherInfo revGatherInfo = this.getRevGatherInfo(ctx, billId);
    	this.createReceivingBill(ctx, billId);
    	super._audit(ctx, billId);
    }
    
    /*
     * ���ܵ�������ʱɾ����Ӧ�ĳ����տ,����Ҫ���Ķ�Ӧ�����Ƿ����ɳ��ɵ��ݵ�״̬
     */
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	ReceiveGatherInfo revGatherInfo = this.getRevGatherInfo(ctx, billId);
    	delReceiveingBill(ctx,revGatherInfo);
    	updateReceiveGatTOUnaudit(ctx,revGatherInfo);
    	super._unAudit(ctx, billId);
    }
    
    private void updateReceiveGatTOUnaudit(Context ctx,ReceiveGatherInfo revGatherInfo) throws EASBizException, BOSException
    {
    	revGatherInfo.setFiRevOrPay(false);
		revGatherInfo.setReceivingBill(null);
		revGatherInfo.setPaymentBill(null);
		ReceiveGatherEntryCollection revEntryColl = revGatherInfo.getEntry();
		for(int i=0;i<revEntryColl.size();i++)
		{
			ReceiveGatherEntryInfo revEntryInfo = revEntryColl.get(i);
			revEntryInfo.setReceivingBillEntry(null);
			revGatherInfo.getEntry().add(revEntryInfo);
		}
		//�����տɾ��ʱ�����ķ��ز����ܵ��Ƿ����ɳ��ɵ��ݵ�״̬�Լ�ɾ���ͷ��ز����ܵ������ܵ���¼�Ĺ���
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("fiRevOrPay");
		sels.add("receivingBill");
		sels.add("paymentBill");
		sels.add("entry.receivingBillEntry");
		ReceiveGatherFactory.getLocalInstance(ctx).updatePartial(revGatherInfo, sels);
    }
    
    private void delReceiveingBill(Context ctx,ReceiveGatherInfo revGatherInfo) throws EASBizException, BOSException
    {
    	ReceivingBillInfo  receivingBillInfo = revGatherInfo.getReceivingBill();
    	if(receivingBillInfo!=null)
    	{
    		FilterInfo filter = new FilterInfo();
        	filter.getFilterItems().add(new FilterItemInfo("id", receivingBillInfo.getId().toString(),CompareType.EQUALS));
        	if(ReceivingBillFactory.getLocalInstance(ctx).exists(filter)){
    			throw new EASBizException(new NumericExceptionSubItem("100","����ɾ����Ӧ�ĳ����տ"));
        	}
    	}  
    	PaymentBillInfo  paymentBillInfo = revGatherInfo.getPaymentBill();
    	if(paymentBillInfo!=null)
    	{
    		FilterInfo filter = new FilterInfo();
        	filter.getFilterItems().add(new FilterItemInfo("id", paymentBillInfo.getId().toString(),CompareType.EQUALS));
        	if(PaymentBillFactory.getLocalInstance(ctx).exists(filter)){
    			throw new EASBizException(new NumericExceptionSubItem("100","����ɾ����Ӧ�ĳ��ɸ���"));
        	}
    	}  
    }
    
    protected void _createReceivingBill(Context ctx, BOSUuid billID) throws BOSException, EASBizException{
		ReceiveGatherInfo revGatherInfo = this.getRevGatherInfo(ctx, billID);
		if(revGatherInfo.getRevBillType().equals(RevBillTypeEnum.refundment)){
			this.createPayBill(ctx, revGatherInfo);
		}else{
			this.createReceivingBill(ctx, revGatherInfo);
		}
	}
    private void createPayBill(Context ctx,ReceiveGatherInfo revGatherInfo)throws EASBizException, BOSException {
    	PaymentBillInfo payment=new PaymentBillInfo();
    	revGatherInfo.setPaymentBill(payment);
    	revGatherInfo.setFiRevOrPay(true);
    	//�õ�����ϵͳ��Ĭ�ϵ��տ�����
    	PaymentBillTypeInfo paymentBillTypeInfo = getPaymentBillType(ctx);
			
		UserInfo userInfo = ContextUtil.getCurrentUserInfo(ctx);
		CtrlUnitInfo cuInfo = ContextUtil.getCurrentCtrlUnit(ctx);
		
		payment.setPayBillType(paymentBillTypeInfo);
		payment.setSourceType(SourceTypeEnum.CASH);
		payment.setSourceSysType(SourceTypeEnum.CASH);
		//��˾
		payment.setCompany(revGatherInfo.getCompany());
		//ҵ������
		payment.setBizDate(revGatherInfo.getBizDate());
		//����
		payment.setExchangeRate(revGatherInfo.getExchangeRate());
		//�ұ�
		payment.setCurrency(revGatherInfo.getCurrency());
		//�տ���
		payment.setActPayAmt(revGatherInfo.getSumAmount());
		
		payment.setCreator(userInfo);
		payment.setCreateTime(new Timestamp(System.currentTimeMillis()));
		payment.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		payment.setLastUpdateUser(userInfo);
		payment.setCU(cuInfo);
		
		payment.setNumber(getNumber(ctx,payment,revGatherInfo));
		
		/**
		 * ����տ���
		 */
//		CustomerInfo custInfo  = revGatherInfo.getCustomer();
//		if(custInfo!=null){
//			receivingBillInfo.setPayerID(custInfo.getId().toString());
//			receivingBillInfo.setPayerNumber(custInfo.getNumber());
//			receivingBillInfo.setPayerName(custInfo.getName());
//		}
		//�۱�λ��
		BigDecimal actrecLocAmt = FDCHelper.ZERO;
		BigDecimal ecchangeRate = FDCHelper.ONE;
		
		if(revGatherInfo.getExchangeRate()!=null){
			ecchangeRate = revGatherInfo.getExchangeRate();
		}
		
		actrecLocAmt  = FDCHelper.multiply(revGatherInfo.getSumAmount(), ecchangeRate);
		
		payment.setActPayLocAmt(actrecLocAmt);
		
		//�տ��Ŀ
		if(revGatherInfo.getRevAccount()!=null){
			payment.setPayerAccount(revGatherInfo.getRevAccount());	
		}
		
		// ����Ŀ�Ϸ���
//		try {
//			arapHelper.verifyAccountView(ctx, revGatherInfo.getRevAccount(), receivingBillInfo
//					.getCurrency(), receivingBillInfo.getCompany());
//		} catch (EASBizException e) {
//			logger.error(e.getMessage()+"��Ŀ����ָ���ұ���㣬��ѡ�������ұ�");
//			throw new BOSException("��Ŀ����ָ���ұ���㣬��ѡ�������ұ�");
//		}
		
		//�տ��˻�
		if(revGatherInfo.getAccountBank()!=null){
			payment.setPayerAccountBank(revGatherInfo.getAccountBank());	
		}
		payment.setPayerBank(revGatherInfo.getBank());
		
		//���㷽ʽ
		payment.setSettlementType(revGatherInfo.getSettlementType());
		//�����
		payment.setSettlementNumber(revGatherInfo.getSettlementNumber());
		
		/**
		 * ���÷ǿ��ֶ�
		 */
		payment.setPaymentBillType(CasRecPayBillTypeEnum.RealType);
		payment.setIsExchanged(false);
		payment.setIsInitializeBill(false);
		payment.setIsImport(false);
		payment.setFiVouchered(false);
		payment.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
		payment.setIsAppointVoucher(false);
		payment.setIsCoopBuild(false);
//		receivingBillInfo.setSourceType(SourceTypeEnum.CASH);
		
		//����״̬
		payment.setBillStatus(BillStatusEnum.SAVE);
		//ԭʼ����id
		payment.setSourceBillId(revGatherInfo.getId().toString());
		
		payment.setDescription(revGatherInfo.getProject().getName()+"����");
		
		SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("asstActGpDt.asstActType.*");
    	sels.add("asstActGpDt.*");
		/**
		 * �����Ƿ�¼����
		 */
    	IBgItem bgItem = BgItemFactory.getLocalInstance(ctx);
		ReceiveGatherEntryCollection revGatherEntryColl = revGatherInfo.getEntry();
		for(int i=0;i<revGatherEntryColl.size();i++)
		{
			ReceiveGatherEntryInfo revGatherEntryInfo = revGatherEntryColl.get(i);
			PaymentBillEntryInfo payBillEntryInfo = new PaymentBillEntryInfo();
			payBillEntryInfo.setActualAmt(revGatherEntryInfo.getRevAmount().negate());
			payBillEntryInfo.setSeq(i+1);
			
			if(revGatherEntryInfo.getOppAccount()!=null)
			{
				payBillEntryInfo.setOppAccount(revGatherEntryInfo.getOppAccount());
				if(revGatherEntryInfo.getRoom() != null){
					ProductTypeInfo pt = revGatherEntryInfo.getRoom().getProductType();
					if(pt != null && pt.getBgItem() != null){
						
						BgItemInfo bg =pt.getBgItem();
						payBillEntryInfo.setOutBgItemName(bg.getName());
						payBillEntryInfo.setOutBgItemNumber(bg.getNumber());
					}
					
				}
				else{
					
					EntityViewInfo viewInfo = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("number","888.02"));
					viewInfo.setFilter(filter);
					
					BgItemInfo bg = bgItem.getBgItemCollection(viewInfo).get(0);
					if(bg!=null){
						//receBillEntryInfo.setOppBgItemId(bg.getId()+"");
						payBillEntryInfo.setOutBgItemName(bg.getName());
						payBillEntryInfo.setOutBgItemNumber(bg.getNumber());
					}
					viewInfo = new EntityViewInfo();
					filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("number","888.01"));
					viewInfo.setFilter(filter);
					
					ExpenseTypeInfo expenseType = ExpenseTypeFactory.getLocalInstance(ctx).getExpenseTypeCollection(viewInfo).get(0);
					if(expenseType != null){
						payBillEntryInfo.setExpenseType(expenseType);
					}
					
					
				}
				if(revGatherEntryInfo.getOppAccount().getCAA()!=null){
					AsstAccountInfo account=AsstAccountFactory.getLocalInstance(ctx).getAsstAccountInfo(new ObjectUuidPK(revGatherEntryInfo.getOppAccount().getCAA().getId()),sels);
					AsstActGroupDetailCollection aacol=account.getAsstActGpDt();
					CRMHelper.sortCollection(aacol, "seq", true);
					
					for(int j=0;j<aacol.size();j++){
						AsstActTypeInfo asstActType=aacol.get(j).getAsstActType();
						AssItemsForCashPayInfo ass=new AssItemsForCashPayInfo();
						ass.setTableName(asstActType.getRealtionDataObject());
						ass.setMappingFileds(asstActType.getMappingFieldName());
						ass.setAsstActType(asstActType);
						ass.setIsSelected(false);
						ass.setSeq(aacol.get(j).getSeq());
						ass.setEntrySeq(i+1);
						
						
						if(asstActType.getRealtionDataObject().equals("T_BD_Customer")){
							if(revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer()!=null){
								ass.setFromID(revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer().getId().toString());
								ass.setFromNumber(revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer().getNumber());
								ass.setIsSelected(true);
							}
						}else if(asstActType.getRealtionDataObject().equals("T_SHE_Room")){
							if(revGatherEntryInfo.getRoom()!=null){
								ass.setFromID(revGatherEntryInfo.getRoom().getId().toString());
								ass.setFromNumber(revGatherEntryInfo.getRoom().getNumber());
								ass.setIsSelected(true);
							}
						}
						payBillEntryInfo.getAssItemsEntries().add(ass);
					}
				}
			}	
			payBillEntryInfo.setCostCenter(ContextUtil.getCurrentCostUnit(ctx));
			payBillEntryInfo.setRemark(revGatherEntryInfo.getRemark());
			payment.getEntries().add(payBillEntryInfo);
		}
		PaymentBillFactory.getLocalInstance(ctx).save(payment);
		
		SelectorItemCollection updateSels = new SelectorItemCollection();
		updateSels.add("fiRevOrPay");
		updateSels.add("paymentBill");
		_updatePartial(ctx, revGatherInfo, updateSels);
    }
    /*
     * ���ݻ��ܵ����ɳ����տһ��һ����
     */
    private void createReceivingBill(Context ctx,ReceiveGatherInfo revGatherInfo) throws EASBizException, BOSException
    {
    	ReceivingBillInfo  receivingBillInfo = new ReceivingBillInfo();
    	revGatherInfo.setReceivingBill(receivingBillInfo);
    	revGatherInfo.setFiRevOrPay(true);
    	//�õ�����ϵͳ��Ĭ�ϵ��տ�����
		ReceivingBillTypeInfo receivingBillTypeInfo = getReceivingBillType(ctx);
			
		UserInfo userInfo = ContextUtil.getCurrentUserInfo(ctx);
		CtrlUnitInfo cuInfo = ContextUtil.getCurrentCtrlUnit(ctx);
		
		receivingBillInfo.setRecBillType(receivingBillTypeInfo);
		receivingBillInfo.setSourceType(SourceTypeEnum.CASH);
		receivingBillInfo.setSourceSysType(SourceTypeEnum.CASH);
		//��˾
		receivingBillInfo.setCompany(revGatherInfo.getCompany());
		//ҵ������
		receivingBillInfo.setBizDate(revGatherInfo.getBizDate());
		//����
		receivingBillInfo.setExchangeRate(revGatherInfo.getExchangeRate());
		//�ұ�
		receivingBillInfo.setCurrency(revGatherInfo.getCurrency());
		//�տ���
		receivingBillInfo.setActRecAmt(revGatherInfo.getSumAmount());
		
		receivingBillInfo.setCreator(userInfo);
		receivingBillInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		receivingBillInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		receivingBillInfo.setLastUpdateUser(userInfo);
		receivingBillInfo.setCU(cuInfo);
		
		receivingBillInfo.setNumber(getNumber(ctx,receivingBillInfo,revGatherInfo));
		
		/**
		 * ����տ���
		 */
//		CustomerInfo custInfo  = revGatherInfo.getCustomer();
//		if(custInfo!=null){
//			receivingBillInfo.setPayerID(custInfo.getId().toString());
//			receivingBillInfo.setPayerNumber(custInfo.getNumber());
//			receivingBillInfo.setPayerName(custInfo.getName());
//		}
		//�۱�λ��
		BigDecimal actrecLocAmt = FDCHelper.ZERO;
		BigDecimal ecchangeRate = FDCHelper.ONE;
		
		if(revGatherInfo.getExchangeRate()!=null){
			ecchangeRate = revGatherInfo.getExchangeRate();
		}
		
		actrecLocAmt  = FDCHelper.multiply(revGatherInfo.getSumAmount(), ecchangeRate);
		
		receivingBillInfo.setActRecLocAmt(actrecLocAmt);
		
		//�տ��Ŀ
		if(revGatherInfo.getRevAccount()!=null){
			receivingBillInfo.setPayeeAccount(revGatherInfo.getRevAccount());	
		}
		
		// ����Ŀ�Ϸ���
//		try {
//			arapHelper.verifyAccountView(ctx, revGatherInfo.getRevAccount(), receivingBillInfo
//					.getCurrency(), receivingBillInfo.getCompany());
//		} catch (EASBizException e) {
//			logger.error(e.getMessage()+"��Ŀ����ָ���ұ���㣬��ѡ�������ұ�");
//			throw new BOSException("��Ŀ����ָ���ұ���㣬��ѡ�������ұ�");
//		}
		
		//�տ��˻�
		if(revGatherInfo.getAccountBank()!=null){
			receivingBillInfo.setPayeeAccountBank(revGatherInfo.getAccountBank());	
		}
		receivingBillInfo.setPayeeBank(revGatherInfo.getBank());
		
		//���㷽ʽ
		receivingBillInfo.setSettlementType(revGatherInfo.getSettlementType());
		//�����
		receivingBillInfo.setSettlementNumber(revGatherInfo.getSettlementNumber());
		
		/**
		 * ���÷ǿ��ֶ�
		 */
		receivingBillInfo.setReceivingBillType(CasRecPayBillTypeEnum.RealType);
		receivingBillInfo.setIsExchanged(false);
		receivingBillInfo.setIsInitializeBill(false);
		receivingBillInfo.setIsImport(false);
		receivingBillInfo.setFiVouchered(false);
		receivingBillInfo.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
		receivingBillInfo.setIsAppointVoucher(false);
		receivingBillInfo.setIsCoopBuild(false);
//		receivingBillInfo.setSourceType(SourceTypeEnum.CASH);
		
		//����״̬
		receivingBillInfo.setBillStatus(BillStatusEnum.SAVE);
		//ԭʼ����id
		receivingBillInfo.setSourceBillId(revGatherInfo.getId().toString());
		
		/**
		 * �����Ƿ�¼����
		 */
		ReceiveGatherEntryCollection revGatherEntryColl = revGatherInfo.getEntry();
		
		SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("asstActGpDt.asstActType.*");
    	sels.add("asstActGpDt.*");
    	
    	IProductType productType = ProductTypeFactory.getLocalInstance(ctx);
    	IBgItem bgItem = BgItemFactory.getLocalInstance(ctx);
    	
    	
    	
    	
		for(int i=0;i<revGatherEntryColl.size();i++)
		{
			ReceiveGatherEntryInfo revGatherEntryInfo = revGatherEntryColl.get(i);
			ReceivingBillEntryInfo receBillEntryInfo = new ReceivingBillEntryInfo();
			revGatherEntryInfo.setReceivingBillEntry(receBillEntryInfo);
			receBillEntryInfo.setActualAmt(revGatherEntryInfo.getRevAmount());
			receBillEntryInfo.setSeq(i+1);
			if(revGatherEntryInfo.getOppAccount()!=null)
			{
				receBillEntryInfo.setOppAccount(revGatherEntryInfo.getOppAccount());
				
				if(revGatherEntryInfo.getRoom() == null){
					EntityViewInfo viewInfo = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("number","888.01"));
					viewInfo.setFilter(filter);
					
					BgItemInfo bg = bgItem.getBgItemCollection(viewInfo).get(0);
					if(bg!=null){
						//receBillEntryInfo.setOppBgItemId(bg.getId()+"");
						receBillEntryInfo.setOppBgItemName(bg.getName());
						receBillEntryInfo.setOppBgItemNumber(bg.getNumber());
					}
					
				}else{
					
					//��ȡ��Ʒ���Ͷ�Ӧ��Ԥ���Ŀ
					ProductTypeInfo pt = revGatherEntryInfo.getRoom().getProductType();
					if(pt != null && pt.getBgItem()!=null){
						BgItemInfo bg = pt.getBgItem();
						//receBillEntryInfo.setOppBgItemId(bg.getId()+"");
						receBillEntryInfo.setOppBgItemName(bg.getName());
						receBillEntryInfo.setOppBgItemNumber(bg.getNumber());
					}
				}
				
				
				
				
				
				receBillEntryInfo.setOppBgItemId(getBindingProperty());
				if(revGatherEntryInfo.getOppAccount().getCAA()!=null){
					AsstAccountInfo account=AsstAccountFactory.getLocalInstance(ctx).getAsstAccountInfo(new ObjectUuidPK(revGatherEntryInfo.getOppAccount().getCAA().getId()),sels);
					AsstActGroupDetailCollection aacol=account.getAsstActGpDt();
					CRMHelper.sortCollection(aacol, "seq", true);
					for(int j=0;j<aacol.size();j++){
						AsstActTypeInfo asstActType=aacol.get(j).getAsstActType();
						AssItemsForCashRecInfo ass=new AssItemsForCashRecInfo();
						ass.setTableName(asstActType.getRealtionDataObject());
						ass.setMappingFileds(asstActType.getMappingFieldName());
						ass.setAsstActType(asstActType);
						ass.setIsSelected(false);
						ass.setSeq(aacol.get(j).getSeq());
						ass.setEntrySeq(i+1);
						
						if(asstActType.getRealtionDataObject().equals("T_BD_Customer")){
							if(revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer()!=null){
								ass.setFromID(revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer().getId().toString());
								ass.setFromNumber(revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer().getNumber());
								ass.setIsSelected(true);
							}
						}else if(asstActType.getRealtionDataObject().equals("T_SHE_Room")){
							if(revGatherEntryInfo.getRoom()!=null){
								ass.setFromID(revGatherEntryInfo.getRoom().getId().toString());
								ass.setFromNumber(revGatherEntryInfo.getRoom().getNumber());
								ass.setIsSelected(true);
							}
						}
						receBillEntryInfo.getAssItemsEntries().add(ass);
					}
				}
			}	
			if(revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer()!=null){
				receBillEntryInfo.setRemark(revGatherEntryInfo.getRemark()+revGatherEntryInfo.getSheRevBill().getParent().getSysCustomer().getName());
			}else{
				receBillEntryInfo.setRemark(revGatherEntryInfo.getRemark());
			}
			if(i==0){
				receivingBillInfo.setDescription(receBillEntryInfo.getRemark());
			}
			receBillEntryInfo.setCostCenter(ContextUtil.getCurrentCostUnit(ctx));
			receivingBillInfo.getEntries().add(receBillEntryInfo);
		}
		ReceivingBillFactory.getLocalInstance(ctx).save(receivingBillInfo);
		
		SelectorItemCollection updateSels = new SelectorItemCollection();
		updateSels.add("fiRevOrPay");
		updateSels.add("receivingBill");
		updateSels.add("entry.receivingBillEntry");
		_updatePartial(ctx, revGatherInfo, updateSels);
    }  
    
    /**
	 * �õ�����ϵͳ��Ĭ�ϵ��տ�����
	 * @return
	 */
	private ReceivingBillTypeInfo getReceivingBillType(Context ctx){
		ReceivingBillTypeInfo typeInfo = null;
		ReceivingBillTypeCollection coll =null;
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("number", "999",
						CompareType.EQUALS));
		IReceivingBillType iReceivingBillType;
		try {
			iReceivingBillType = ReceivingBillTypeFactory.getLocalInstance(ctx);			
			coll = iReceivingBillType.getReceivingBillTypeCollection(ev);
		} catch (BOSException e) {
			logger.error(e.getMessage()+"�õ�����ϵͳ��Ĭ�ϵ��տ�����!");
		}
		

		if (coll != null && !coll.isEmpty()) {
			typeInfo = coll.get(0);
		}
		
		return typeInfo;
	}
	 /**
	 * �õ�����ϵͳ��Ĭ�ϵ��տ�����
	 * @return
	 */
	private PaymentBillTypeInfo getPaymentBillType(Context ctx){
		PaymentBillTypeInfo typeInfo = null;
		PaymentBillTypeCollection coll =null;
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("number", "999",
						CompareType.EQUALS));
		IPaymentBillType iPaymentBillType;
		try {
			iPaymentBillType = PaymentBillTypeFactory.getLocalInstance(ctx);			
			coll = iPaymentBillType.getPaymentBillTypeCollection(ev);
		} catch (BOSException e) {
			logger.error(e.getMessage()+"�õ�����ϵͳ��Ĭ�ϵ��տ�����!");
		}
		
		if (coll != null && !coll.isEmpty()) {
			typeInfo = coll.get(0);
		}
		
		return typeInfo;
	}
	/**
	 * ���ñ���		
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private String getNumber(Context ctx,ReceivingBillInfo  receivingBillInfo,ReceiveGatherInfo revGatherInfo) throws BOSException, EASBizException
	{		
		ICodingRuleManager iCodingRuleManager = null;
		if(ctx!=null)	iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		else 	iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		
		OrgUnitInfo orgUnit = null;
		if(ctx!=null) {
			orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
			if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		}else {
			orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
			if(orgUnit==null) orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		}
		
		String retNumber = iCodingRuleManager.getNumber(receivingBillInfo, orgUnit.getId().toString());
		if(retNumber!=null && !"".equals(retNumber)){
			receivingBillInfo.setNumber(retNumber);
		}else
		{
			retNumber = revGatherInfo.getNumber()+"_toReceiving";
		}
		
		return retNumber;
	}	
	/**
	 * ���ñ���		
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private String getNumber(Context ctx,PaymentBillInfo  receivingBillInfo,ReceiveGatherInfo revGatherInfo) throws BOSException, EASBizException
	{		
		ICodingRuleManager iCodingRuleManager = null;
		if(ctx!=null)	iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		else 	iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		
		OrgUnitInfo orgUnit = null;
		if(ctx!=null) {
			orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
			if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		}else {
			orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
			if(orgUnit==null) orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		}
		
		String retNumber = iCodingRuleManager.getNumber(receivingBillInfo, orgUnit.getId().toString());
		if(retNumber!=null && !"".equals(retNumber)){
			receivingBillInfo.setNumber(retNumber);
		}else
		{
			retNumber = revGatherInfo.getNumber()+"_toPayment";
		}
		
		return retNumber;
	}
	
	//���ܵ�����ƾ֤ʱͬ�����³����տ���Ƿ�����ƾ֤���������ظ�����
	protected DAPTransformResult _generateVoucher(Context ctx, IObjectCollection sourceBillCollection,
			IObjectPK botMappingPK) throws BOSException, EASBizException {		
		CoreBillBaseCollection coll = (CoreBillBaseCollection) sourceBillCollection;
		for (Iterator it = coll.iterator(); it.hasNext();) {
			ReceiveGatherInfo info = (ReceiveGatherInfo) it.next();
			info = this.getRevGatherInfo(ctx,info.getId());
			ReceivingBillInfo revInfo = info.getReceivingBill();
			revInfo.setFiVouchered(true);
			SelectorItemCollection updateSels = new SelectorItemCollection();
			updateSels.add("fiVouchered");
			ReceivingBillFactory.getLocalInstance(ctx).updatePartial(revInfo, updateSels);
		}
		return super._generateVoucher(ctx, sourceBillCollection, botMappingPK);						
	}	
	
	protected void _delReceivingToRev(Context ctx, IObjectValue info) throws BOSException, EASBizException {
		ReceivingBillInfo recBillInfo = (ReceivingBillInfo)info;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("receivingBill.id", recBillInfo.getId().toString(),CompareType.EQUALS));
    	view.setFilter(filter);
    	ReceiveGatherCollection revGatherColl = ReceiveGatherFactory.getLocalInstance(ctx).getReceiveGatherCollection(view);
    	if(revGatherColl.size()>0)
    	{
    		ReceiveGatherInfo revGatherInfo = revGatherColl.get(0);
    		revGatherInfo.setFiRevOrPay(false);
    		revGatherInfo.setReceivingBill(null);
    		ReceiveGatherEntryCollection revEntryColl = revGatherInfo.getEntry();
    		for(int i=0;i<revEntryColl.size();i++)
    		{
    			ReceiveGatherEntryInfo revEntryInfo = revEntryColl.get(i);
    			revEntryInfo.setReceivingBillEntry(null);
    			revGatherInfo.getEntry().add(revEntryInfo);
    		}
    		//�����տɾ��ʱ�����ķ��ز����ܵ��Ƿ����ɳ��ɵ��ݵ�״̬�Լ�ɾ���ͷ��ز����ܵ������ܵ���¼�Ĺ���
    		SelectorItemCollection sels = new SelectorItemCollection();
    		sels.add("fiRevOrPay");
    		sels.add("receivingBill");
    		sels.add("entry.receivingBillEntry");
    		ReceiveGatherFactory.getLocalInstance(ctx).updatePartial(revGatherInfo, sels);
    	}
	}
	
	//�����տ����ƾ֤���»��ܵ��Ƿ�����ƾ֤
	protected void _createVoucherToRev(Context ctx, ArrayList revList) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("receivingBill.id",listToSet(revList),CompareType.INCLUDE));
    	view.setFilter(filter);
    	ReceiveGatherCollection revGatherColl = ReceiveGatherFactory.getLocalInstance(ctx).getReceiveGatherCollection(view);
    	for(int i=0;i<revGatherColl.size();i++)
    	{
    		ReceiveGatherInfo revGatherInfo = revGatherColl.get(i); 
    		revGatherInfo.setFiVouchered(true);
    		//�����տ����ƾ֤ʱ�����»��ܵ���Ӧ������ƾ֤
    		SelectorItemCollection sels = new SelectorItemCollection();
    		sels.add("fiVouchered");
    		ReceiveGatherFactory.getLocalInstance(ctx).updatePartial(revGatherInfo, sels);
    	}
	}
	private Set listToSet(ArrayList list)
	{
		Set set = new HashSet();
		for(int i=0;i<list.size();i++)
		{
			set.add(list.get(i));
		}
		return set;
	}
	
	//�����տ����ɾ��ƾ֤
	protected void _delVoucherToRev(Context ctx, ArrayList revList) throws BOSException, EASBizException {
		for(int i=0;i<revList.size();i++)
		{
			_delVoucherToRev(ctx,new ObjectUuidPK((String)revList.get(i)));
		}		
	}
	
	protected void _delVoucherToRev(Context ctx, IObjectPK sourceBillPk) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("receivingBill.id", sourceBillPk.toString(),CompareType.EQUALS));
    	view.setFilter(filter);
    	ReceiveGatherCollection revGatherColl = ReceiveGatherFactory.getLocalInstance(ctx).getReceiveGatherCollection(view);
    	if(revGatherColl.size()>0)
    	{
    		ReceiveGatherInfo revGatherInfo = revGatherColl.get(0);    		
    		_deleteVoucher(ctx, new ObjectUuidPK(revGatherInfo.getId()));
    	}else
    	{
    		//˵���ǳ����տ����
    		Set idRevSet = FMHelper.getSrcBillIdCollBySrcId(ctx, sourceBillPk.toString());
			ReceivingBillFactory.getLocalInstance(ctx).deleteVoucher(sourceBillPk);
			//�����տ�����Ƕ����տ����һ��ƾ֤���ҳ�����Щ�տ��Ӧ�˻��ܵ�
			EntityViewInfo view2 = new EntityViewInfo();
			FilterInfo filter2 = new FilterInfo();
	    	filter.getFilterItems().add(new FilterItemInfo("receivingBill.id",idRevSet,CompareType.INCLUDE));
	    	view2.setFilter(filter2);
	    	ReceiveGatherCollection revGatherColl2 = ReceiveGatherFactory.getLocalInstance(ctx).getReceiveGatherCollection(view2);
	    	for(int i=0;i<revGatherColl2.size();i++)
	    	{
	    		ReceiveGatherInfo revGatherInfo = revGatherColl2.get(i);
	    		revGatherInfo.setFiVouchered(false);
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add("fiVouchered");
				ReceiveGatherFactory.getLocalInstance(ctx).updatePartial(revGatherInfo, updateSels);
	    	}
    	}
	}
	
	//ɾ��ƾ֤��ʱ��ͬ�����³����տ���Ƿ�����ƾ֤
	protected boolean _deleteVoucher(Context ctx, IObjectPK sourceBillPk) throws BOSException, EASBizException {
		ReceiveGatherInfo info = this.getRevGatherInfo(ctx,BOSUuid.read(sourceBillPk.toString()));
		ReceivingBillInfo revInfo = info.getReceivingBill();
		//����ƾ֤�� �ɻ��ܵ����ɻ��ǳ����տ����
		Set idSet = FMHelper.getSrcBillIdCollBySrcId(ctx, sourceBillPk.toString());
		if(idSet.size()>0)
		{
			//˵���ǻ��ܵ�����
			
			revInfo.setFiVouchered(false);
			SelectorItemCollection updateSels = new SelectorItemCollection();
			updateSels.add("fiVouchered");
			ReceivingBillFactory.getLocalInstance(ctx).updatePartial(revInfo, updateSels);
			return super._deleteVoucher(ctx, sourceBillPk);	
		}else
		{
			//˵���ǳ����տ����
			//��ô�ҳ���Ӧ�����е�Դ����ID����
			Set idRevSet = FMHelper.getSrcBillIdCollBySrcId(ctx, revInfo.getId().toString());
			ReceivingBillFactory.getLocalInstance(ctx).deleteVoucher(new ObjectUuidPK(revInfo.getId()));
			//�����տ�����Ƕ����տ����һ��ƾ֤���ҳ�����Щ�տ��Ӧ�˻��ܵ�
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
	    	filter.getFilterItems().add(new FilterItemInfo("receivingBill.id",idRevSet,CompareType.INCLUDE));
	    	view.setFilter(filter);
	    	ReceiveGatherCollection revGatherColl = ReceiveGatherFactory.getLocalInstance(ctx).getReceiveGatherCollection(view);
	    	for(int i=0;i<revGatherColl.size();i++)
	    	{
	    		ReceiveGatherInfo revGatherInfo = revGatherColl.get(i);
	    		revGatherInfo.setFiVouchered(false);
				SelectorItemCollection updateSels = new SelectorItemCollection();
				updateSels.add("fiVouchered");
				ReceiveGatherFactory.getLocalInstance(ctx).updatePartial(revGatherInfo, updateSels);
	    	}
			
			return true;
		}
		
					
	}
    
    private ReceiveGatherInfo getRevGatherInfo(Context ctx,BOSUuid billId) throws EASBizException, BOSException
    {
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("id");
    	sels.add("revBillType");
    	sels.add("sumAmount");
    	sels.add("settlementNumber");
    	sels.add("gatherType");
    	sels.add("fiVouchered");
    	sels.add("fiRevOrPay");
    	sels.add("exchangeRate");
    	sels.add("bizDate");
    	sels.add("description");
    	
    	sels.add("project.id");
    	sels.add("project.name");
    	sels.add("project.number");
    	
    	sels.add("bank.id");
    	sels.add("bank.name");
    	sels.add("bank.number");
    	
    	sels.add("accountBank.id");
    	sels.add("accountBank.name");
    	sels.add("accountBank.number");
    	
    	sels.add("settlementType.id");
    	sels.add("settlementType.name");
    	sels.add("settlementType.number");
    	
    	sels.add("revAccount.id");
    	sels.add("revAccount.name");
    	sels.add("revAccount.number");
    	
    	sels.add("oppAccount.id");
    	sels.add("oppAccount.name");
    	sels.add("oppAccount.number");
    	
    	sels.add("company.id");
    	sels.add("company.name");
    	sels.add("company.number");
    	
    	sels.add("currency.id");
    	sels.add("currency.name");
    	sels.add("currency.number");
    	
    	sels.add("receivingBill.id");
    	
    	sels.add("entry.id");
    	sels.add("entry.customerDisName");
    	sels.add("entry.settlementNumber");
    	sels.add("entry.cusAccountBankNumber");
    	sels.add("entry.revAmount");
    	
    	sels.add("entry.sheRevBill.id");
    	sels.add("entry.sheRevBill.parent.sysCustomer.*");
    	
    	sels.add("entry.moneyDefine.id");
    	sels.add("entry.moneyDefine.name");
    	sels.add("entry.moneyDefine.number");
    	sels.add("entry.receiptNumber");
    	sels.add("entry.invoiceNumber");
    	
    	sels.add("entry.room.id");
    	sels.add("entry.room.name");
    	sels.add("entry.room.number");
    	
    	sels.add("entry.settlementType.id");
    	sels.add("entry.settlementType.name");
    	sels.add("entry.settlementType.number");
    	
    	sels.add("entry.oppAccount.id");
    	sels.add("entry.oppAccount.name");
    	sels.add("entry.oppAccount.number");
    	sels.add("entry.oppAccount.CAA.*");
    	
    	sels.add("entry.remark");
    	sels.add("entry.room.producttype.id");
    	sels.add("entry.room.producttype.bgitem.id");
    	sels.add("entry.room.producttype.bgitem.name");
    	sels.add("entry.room.producttype.bgitem.number");
   	
    	return (ReceiveGatherInfo) getValue(ctx, new ObjectUuidPK(billId),sels);
    }
    
    
	/**
	 * ����������״̬
	 */
	protected void _setAudittingStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
	    ReceiveGatherInfo rev = new ReceiveGatherInfo();
		rev.setId(billId);
		rev.setState(FDCBillStateEnum.AUDITTING);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("state");
		this.updatePartial(ctx, rev, sels);
	}

    /**
	 * �����ύ״̬
	 */
	protected void _setSubmitStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		ReceiveGatherInfo rev = new ReceiveGatherInfo();
		rev.setId(billId);
		rev.setState(FDCBillStateEnum.SUBMITTED);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("state");
		this.updatePartial(ctx, rev, sels);		
	}    
    
    
}