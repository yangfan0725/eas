package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.SHERevCustEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BankPaymentInfo;
import com.kingdee.eas.fdc.sellhouse.GatherTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IRoomLoan;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class BankPaymentControllerBean extends AbstractBankPaymentControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.BankPaymentControllerBean");
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	//���зſ��ύʱ��ҪУ���Ӧ�ķ����Ƿ����ɰ��ҷ��񵥾�
    	BankPaymentInfo bankPayInfo = (BankPaymentInfo)model;
    	checkRoomLoan(ctx,bankPayInfo);
    	return super._submit(ctx, model);	
    }
    
    protected void checkRoomLoan(Context ctx,BankPaymentInfo bankPayInfo) throws BOSException, EASBizException
    {
    	BankPaymentEntryCollection bankEntryColl = bankPayInfo.getBankPaymentEntry();
    	boolean isCreate=false;
    	for(int i=0;i<bankEntryColl.size();i++)
    	{
    		BankPaymentEntryInfo bankEntryInfo = bankEntryColl.get(i);
    		RoomInfo room = bankEntryInfo.getRoom();
    		
    		MoneyDefineInfo money = bankPayInfo.getMoneyDefine();
    		
    		EntityViewInfo view = new EntityViewInfo();    	
    		FilterInfo filter = new FilterInfo();
    		SelectorItemCollection sel = new SelectorItemCollection();
    		sel.add(new SelectorItemInfo("preAfmState"));
    		sel.add(new SelectorItemInfo("aFMortgagedState"));
    		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString(),CompareType.EQUALS));
    		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.CANCELROOM_VALUE),CompareType.NOTEQUALS));
    		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.CHANGEROOM_VALUE),CompareType.NOTEQUALS));
    		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.STOPTRANSACT_VALUE),CompareType.NOTEQUALS));
    		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.BANKFUND_VALUE),CompareType.NOTEQUALS));
    		filter.getFilterItems().add(new FilterItemInfo("mmType.id",money.getId().toString(),CompareType.EQUALS));
//    		filter.setMaskString("#0 and (#1 or #2 or #3 or #4) and #5");
    		view.setFilter(filter);
    		view.setSelector(sel);   		 
    		//У�鵱ǰ�����Ƿ����״̬��Ϊ���˷���ֹ������������ֹ�������ֹ����ϡ��������зſ�İ��ҵ���
    		RoomLoanCollection roomLoanColl = RoomLoanFactory.getLocalInstance(ctx).getRoomLoanCollection(view);
    		if(roomLoanColl.size()>1)
    			throw new EASBizException(new NumericExceptionSubItem("100","���ݳ���һ�����䲻����ͬʱ���ڶ����Ч״̬�İ��ҷ��񵥾ݣ�"));
    		if(roomLoanColl.size()==1)
    		{
    			RoomLoanInfo roomLoanInfo = (RoomLoanInfo)roomLoanColl.get(0);
    			//���ð��ҵ��ݵ���һ״̬Ϊ�ı�ǰ״̬
    			roomLoanInfo.setPreAfmState(roomLoanInfo.getAFMortgagedState());  			
    			roomLoanInfo.setAFMortgagedState(AFMortgagedStateEnum.BANKFUND);   			
    			
    			SelectorItemCollection updateSels = new SelectorItemCollection();
    			updateSels.add("preAfmState");
    			updateSels.add("aFMortgagedState");
    			RoomLoanFactory.getLocalInstance(ctx).updatePartial(roomLoanInfo, updateSels);
//    			_updatePartial(ctx, roomLoanInfo, updateSels);	
    		}else{	//���������зſ״̬�İ��ҵ���
    			isCreate=true;
    		}
    	} 	  
    	if(isCreate){
//    		this.createRoomLoanBill(ctx, bankPayInfo);    			
    	}
    }
    
    //ɾ��ʱ��Ҫ�Ѷ�Ӧ�İ��ҷ��񵥾�״̬�Ļ�ԭ״̬
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
    	BankPaymentInfo bankPayInfo = this.getBankPaymentInfo(ctx, BOSUuid.read(pk.toString()));
    	BankPaymentEntryCollection bankEntryColl = bankPayInfo.getBankPaymentEntry();
    	for(int i=0;i<bankEntryColl.size();i++)
    	{
    		BankPaymentEntryInfo bankEntryInfo = bankEntryColl.get(i);
    		RoomInfo room = bankEntryInfo.getRoom();
    		MoneyDefineInfo money = bankPayInfo.getMoneyDefine();
    		
    		EntityViewInfo view = new EntityViewInfo();    	
    		FilterInfo filter = new FilterInfo();
    		SelectorItemCollection sel = new SelectorItemCollection();
    		sel.add(new SelectorItemInfo("preAfmState"));
    		sel.add(new SelectorItemInfo("aFMortgagedState"));
    		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString(),CompareType.EQUALS));
    		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",AFMortgagedStateEnum.BANKFUND,CompareType.EQUALS));
    		filter.getFilterItems().add(new FilterItemInfo("mmType.id",money.getId().toString(),CompareType.EQUALS));
    		view.setFilter(filter);
    		view.setSelector(sel);
    		//У�鵱ǰ�����Ƿ���ڡ����зſ״̬�İ��ҵ���
    		RoomLoanCollection roomLoanColl = RoomLoanFactory.getLocalInstance(ctx).getRoomLoanCollection(view);
    		if(roomLoanColl.size()>1)
    			throw new EASBizException(new NumericExceptionSubItem("100","���ݳ���һ�����䲻����ͬʱ���ڶ�����зſ�״̬�İ��ҷ��񵥾ݣ�"));
    		if(roomLoanColl.size()==0)
    			continue;
    		if(roomLoanColl.size()==1)
    		{
    			RoomLoanInfo info = (RoomLoanInfo)roomLoanColl.get(0);
    			//ɾ�����зſʱ��������״̬��Ϊ��һ״̬,����һ״̬���
    			info.setAFMortgagedState(info.getPreAfmState()); 
    			info.setPreAfmState(null);   			
    			
    			SelectorItemCollection updateSels = new SelectorItemCollection();
    			updateSels.add("preAfmState");
    			updateSels.add("aFMortgagedState");
    			RoomLoanFactory.getLocalInstance(ctx).updatePartial(info, updateSels);
//    			_updatePartial(ctx, roomLoanInfo, updateSels);	
    		} 
    	}
    	super._delete(ctx, pk);
    }
    
    //ͨ��ǩԼ����¼�ҵ�ǩԼ���ͻ�
    protected SignManageInfo getSignInfo(Context ctx,String signManagID) throws EASBizException, BOSException
    {
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("id");
    	sels.add("customerNames");
    	sels.add("signCustomerEntry.id");
    	sels.add("signCustomerEntry.customer.id");
    	SignManageInfo signInfo = SignManageFactory.getLocalInstance(ctx).getSignManageInfo(new ObjectUuidPK(signManagID), sels);
    	return signInfo;
    }
    
    //������¥�տ����
    private String getSHERevBillNumber(Context ctx,SHERevBillInfo sheRevInfo,BankPaymentInfo bankPayInfo) throws EASBizException, BOSException
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
		String retNumber ="";
		if(iCodingRuleManager.isExist(sheRevInfo, orgUnit.getId().toString()))
		     retNumber = iCodingRuleManager.getNumber(sheRevInfo, orgUnit.getId().toString());
		if(retNumber!=null && !"".equals(retNumber)){
			sheRevInfo.setNumber(retNumber);
		}else
		{
			retNumber = bankPayInfo.getNumber()+"_���зſ����";
		}
		
		return retNumber;
    }
    
    //���ɰ��ҷ���ı���
    private String getRoomLoanBillNumber(Context ctx,RoomLoanInfo roomLoanInfo,BankPaymentInfo bankPayInfo) throws EASBizException, BOSException
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
		String retNumber ="";
		if(iCodingRuleManager.isExist(roomLoanInfo, orgUnit.getId().toString()))
		     retNumber = iCodingRuleManager.getNumber(roomLoanInfo, orgUnit.getId().toString());
		if(retNumber!=null && !"".equals(retNumber)){
			roomLoanInfo.setNumber(retNumber);
		}else
		{
			retNumber = bankPayInfo.getNumber()+"_���зſ����";
		}
		
		return retNumber;
    }
    
    protected void creteSHERevBill(Context ctx,BankPaymentInfo bankPayInfo) throws EASBizException, BOSException
    {
    	//�������зſ�����տ��ͬһǩԼ���µĴ�����ϸ��������һ���տ
		CoreBaseCollection sheRevColl = new CoreBaseCollection();
		BankPaymentEntryCollection bankEntryColl = bankPayInfo.getBankPaymentEntry();
		Map entryMap = new HashMap();
		List signList = new ArrayList();

		for(int i=0;i<bankEntryColl.size();i++)
		{
			//�����ͬһǩԼ������ϸ�򱣴���һ������һ���տ�������ͬһǩԼ�������������տ�
			BankPaymentEntryInfo bankEntryInfo = bankEntryColl.get(i);
			BigDecimal sumAmount = FDCHelper.ZERO;
			SHERevBillEntryInfo sheRevEntryInfo = new SHERevBillEntryInfo();
			String signManaID = bankEntryInfo.getSignManager().getId().toString();
			
			//�������ͬһ��ǩԼ����������һ���տ
			if(!isInclude(signManaID,signList)){
				signList.add(signManaID);
				SignManageInfo signInfo = getSignInfo(ctx, signManaID);
				SignCustomerEntryCollection signCusColl = signInfo.getSignCustomerEntry();
				if(signCusColl==null || signCusColl.size()==0)
				{
					throw new EASBizException(new NumericExceptionSubItem("100","���ݴ���ǩԼ����û�пͻ���"));					
				}
				SHERevBillInfo sheRevInfo = new SHERevBillInfo();
				//�տ�ͻ��Ͷ�Ӧ��ǩԼ���ͻ���ͬ
				for(int j=0;j<signCusColl.size();j++)
				{
					SHERevCustEntryInfo sheCusEntryInfo = new SHERevCustEntryInfo();
					SignCustomerEntryInfo signCusInfo = signCusColl.get(j);
					SHECustomerInfo sheCusInfo = signCusInfo.getCustomer();
					sheCusEntryInfo.setSheCustomer(sheCusInfo);
					sheRevInfo.getCustomerEntry().add(sheCusEntryInfo);
				}
				sheRevInfo.setCustomerNames(signInfo.getCustomerNames());
				//�տͷ
				//���зſ��Ӧ���տ�ͳ��ɻ��ܵ�ͬʱ����
				sheRevInfo.setIsGathered(false);	
				CompanyOrgUnitInfo company = ContextUtil.getCurrentFIUnit(ctx);
				sheRevInfo.setCurrency(company.getBaseCurrency());					
				sheRevInfo.setExchangeRate(FDCHelper.ONE);
				sheRevInfo.setSellProject(bankPayInfo.getProject());
				sheRevInfo.setState(FDCBillStateEnum.SUBMITTED);
				sheRevInfo.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
				sheRevInfo.setNumber(getSHERevBillNumber(ctx, sheRevInfo, bankPayInfo));
				sheRevInfo.setRoom(bankEntryInfo.getRoom());
				sheRevInfo.setRevBillType(RevBillTypeEnum.gathering);
				sheRevInfo.setRelateBizType(RelatBizType.Sign);
				sheRevInfo.setRelateBizBillId(bankEntryInfo.getSignManager().getId().toString());
				sheRevInfo.setRelateTransId(bankEntryInfo.getSignManager().getTransactionID().toString());
				sheRevInfo.setRelateBizBillNumber(bankEntryInfo.getSignManager().getNumber());
				
				sheRevInfo.setRelateTransId(bankEntryInfo.getSignManager().getTransactionID().toString());
				sheRevInfo.setCreateTime(bankPayInfo.getCreateTime());
				sheRevInfo.setCreator(bankPayInfo.getCreator());
				sheRevInfo.setBizDate(new Date());
				sheRevInfo.setDescription("���зſ"+bankPayInfo.getNumber()+"���ɵ��տ");
				
				sheRevInfo.setSettlementType(bankPayInfo.getSettlementType());
				sheRevInfo.setSettlementNumber(bankPayInfo.getSettlementNumber());
				
				AccountBankInfo payAccBankInfo = bankPayInfo.getRevAccountBank();
				if(payAccBankInfo!=null){
					SelectorItemCollection sels = new SelectorItemCollection();
					sels.add("account.*");
			    	payAccBankInfo = (AccountBankInfo) AccountBankFactory.getLocalInstance(ctx).getValue(new ObjectUuidPK(payAccBankInfo.getId()), sels);
			    	sheRevInfo.setRevAccount(payAccBankInfo.getAccount());
				}
				sheRevInfo.setAccountBank(payAccBankInfo);
				sheRevInfo.setBank(bankPayInfo.getRevBank());
				sheRevInfo.setPayCustomerName(signInfo.getCustomerNames());
				
				sheRevInfo.setSaleOrgUnit(ContextUtil.getCurrentSaleUnit(ctx));
				//�տ��¼
				sheRevEntryInfo.setMoneyDefine(bankPayInfo.getMoneyDefine());
//				sheRevEntryInfo.setSettlementType(bankPayInfo.getSettlementType());
//				sheRevEntryInfo.setSettlementNumber(bankPayInfo.getSettlementNumber());
//				sheRevEntryInfo.setRevAccountBank(bankPayInfo.getRevAccountBank());
//				if(bankPayInfo.getRevAccount()!=null)
//				{
//					sheRevEntryInfo.setRevAccountNumber(bankPayInfo.getRevAccount().getNumber());
//				}	
				sheRevEntryInfo.setRevAmount(bankEntryInfo.getPaymentAmount());
				BigDecimal paymentAmount = bankEntryInfo.getPaymentAmount();
				if(paymentAmount==null)paymentAmount = FDCHelper.ZERO;
				sumAmount = sumAmount.add(paymentAmount);
				sheRevInfo.setRevAmount(sumAmount);
				sheRevInfo.getEntrys().add(sheRevEntryInfo);
				entryMap.put(signManaID, sheRevInfo);
				sheRevColl.add(sheRevInfo);
				bankEntryInfo.setSheRevBill(sheRevEntryInfo);
			}else
			{
				//�����ͬһ���տ����ȡ��ԭ�����տ��ֻ�����տ���ϸ				
				sheRevEntryInfo.setMoneyDefine(bankPayInfo.getMoneyDefine());
				sheRevEntryInfo.setRevAmount(bankEntryInfo.getPaymentAmount());
				SHERevBillInfo sheRevInfo = (SHERevBillInfo)entryMap.get(signManaID);
				BigDecimal revAmount = sheRevInfo.getRevAmount();
				BigDecimal paymentAmount = bankEntryInfo.getPaymentAmount();
				if(paymentAmount==null)paymentAmount = FDCHelper.ZERO;
				if(revAmount==null)revAmount = FDCHelper.ZERO;
				sheRevInfo.setRevAmount(paymentAmount.add(revAmount));
				sheRevInfo.getEntrys().add(sheRevEntryInfo);
				bankEntryInfo.setSheRevBill(sheRevEntryInfo);
			}
		}
		
		for (int i = 0; i < sheRevColl.size(); i++) {
			SHERevBillFactory.getLocalInstance(ctx).submit(sheRevColl.get(i));
		}
		
//		SHERevBillFactory.getLocalInstance(ctx).addnew(sheRevColl);
		SelectorItemCollection updateSels = new SelectorItemCollection();
		updateSels.add("bankPaymentEntry.sheRevBill");
		_updatePartial(ctx, bankPayInfo, updateSels);		
    }
    
    //���ɻ��ܵ�����
    private String getGatherNumber(Context ctx,ReceiveGatherInfo revGatherInfo,BankPaymentInfo bankPayInfo) throws BOSException, EASBizException
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
		String retNumber ="";
		if(iCodingRuleManager.isExist(revGatherInfo, orgUnit.getId().toString()))
		    retNumber = iCodingRuleManager.getNumber(revGatherInfo, orgUnit.getId().toString());
		if(retNumber!=null && !"".equals(retNumber)){
			revGatherInfo.setNumber(retNumber);
		}else
		{
			retNumber = bankPayInfo.getNumber()+"_���зſ����";
		}
		
		return retNumber;
    }
    
    protected void createReceiveGather(Context ctx,BankPaymentInfo bankPayInfo) throws EASBizException, BOSException
    {
    	ReceiveGatherInfo revGatherInfo = new ReceiveGatherInfo();
    	//��¼���зſ���ɵĳ��ɻ��ܵ�������
    	bankPayInfo.setReceiveGather(revGatherInfo);
	
    	CompanyOrgUnitInfo company = ContextUtil.getCurrentFIUnit(ctx);
    	revGatherInfo.setCompany(company);
    	revGatherInfo.setCurrency(company.getBaseCurrency());

    	revGatherInfo.setExchangeRate(FDCHelper.ONE);
    	revGatherInfo.setNumber(getGatherNumber(ctx, revGatherInfo, bankPayInfo));
    	revGatherInfo.setProject(bankPayInfo.getProject());
    	revGatherInfo.setRevBillType(RevBillTypeEnum.gathering);
    	revGatherInfo.setGatherType(GatherTypeEnum.ReceiveGather);
    	revGatherInfo.setBank(bankPayInfo.getRevBank());
    	revGatherInfo.setAccountBank(bankPayInfo.getRevAccountBank());
    	revGatherInfo.setSumAmount(bankPayInfo.getPaymentAmout());
    	revGatherInfo.setSettlementType(bankPayInfo.getSettlementType());
    	revGatherInfo.setSettlementNumber(bankPayInfo.getSettlementNumber());
    	revGatherInfo.setBizDate(bankPayInfo.getPaymentDate());
    	revGatherInfo.setRevAccount(bankPayInfo.getRevAccount());
    	revGatherInfo.setOppAccount(bankPayInfo.getOppAccount());
    	revGatherInfo.setCreateTime(bankPayInfo.getCreateTime());
    	revGatherInfo.setCreator(bankPayInfo.getCreator());
    	revGatherInfo.setState(FDCBillStateEnum.SUBMITTED);
    	revGatherInfo.setDescription("���зſ"+bankPayInfo.getNumber()+"���ɵĳ��ɻ��ܵ�");
    	BankPaymentEntryCollection bankEntryColl = bankPayInfo.getBankPaymentEntry();
    	for(int i=0;i<bankEntryColl.size();i++)
    	{
    		BankPaymentEntryInfo bankEntryInfo = bankEntryColl.get(i);
    		ReceiveGatherEntryInfo revEntryInfo = new ReceiveGatherEntryInfo();
    		revEntryInfo.setRoom(bankEntryInfo.getRoom());
    		revEntryInfo.setCustomerDisName(bankEntryInfo.getCustomerDisName());
    		revEntryInfo.setMoneyDefine(bankPayInfo.getMoneyDefine());
    		revEntryInfo.setSettlementType(bankPayInfo.getSettlementType());
    		revEntryInfo.setSettlementNumber(bankPayInfo.getSettlementNumber());
    		revEntryInfo.setRevAmount(bankEntryInfo.getPaymentAmount());
    		revEntryInfo.setOppAccount(bankPayInfo.getOppAccount());
//    		revEntryInfo.setCusAccountBankNumber();
    		revEntryInfo.setSheRevBill(bankEntryInfo.getSheRevBill());
    		revGatherInfo.getEntry().add(revEntryInfo);
    	}
    	ReceiveGatherFactory.getLocalInstance(ctx).addnew(revGatherInfo);
    	
    	SelectorItemCollection updateSels = new SelectorItemCollection();
		updateSels.add("receiveGather");
		_updatePartial(ctx, bankPayInfo, updateSels);
    }
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {				
		BankPaymentInfo bankPayInfo = getBankPaymentInfo(ctx,billId);		
		//���зſ�����տ
		creteSHERevBill(ctx,bankPayInfo);
		//���зſ���ɳ��ɻ��ܵ�
//		createReceiveGather(ctx,bankPayInfo);
		
    	super._audit(ctx, billId);
    }      
    
    /**���ɰ��ҵ�*/
    private void createRoomLoanBill(Context ctx,BankPaymentInfo bankPayInfo) throws EASBizException, BOSException {
    	BankPaymentEntryCollection bankPayEntryColl = bankPayInfo.getBankPaymentEntry();
    	IRoomLoan loanFactory = RoomLoanFactory.getLocalInstance(ctx);
    	for (int i = 0; i < bankPayEntryColl.size(); i++) {
    		BankPaymentEntryInfo bkPayEntryInfo = bankPayEntryColl.get(i);
    		
    		RoomLoanInfo roomLoan = new RoomLoanInfo();
    		roomLoan.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
    		roomLoan.setRoom(bkPayEntryInfo.getRoom());
    		//���ݱ�����������ɱ���
    		String number ="";
    		number = this.getRoomLoanBillNumber(ctx,roomLoan,bankPayInfo);
    		if(number!=null && !"".equals(number)){
    			if(bankPayEntryColl.size()>1){
    				roomLoan.setNumber(number+i);
    			}
    		}
    		roomLoan.setAFMortgagedState(AFMortgagedStateEnum.UNTRANSACT);
    		roomLoan.setMmType(bankPayInfo.getMoneyDefine());
    		roomLoan.setCreator(SysContext.getSysContext().getCurrentUserInfo());
    		roomLoan.setSign(bkPayEntryInfo.getSignManager());
    		roomLoan.setPurchase(null);
    		roomLoan.setAFMortgagedState(AFMortgagedStateEnum.BANKFUND);  
    		roomLoan.setLoanBank(bankPayInfo.getPaymentBank());
    		
    		//����ҵ��������Ӧ�ķ���
    		//SHEManageHelper.updateTransactionOverView(ctx, roomLoan.getRoom(), SHEManageHelper.MORTGAGE,
    		//		roomLoan.getPromiseDate(), null, false);
    		
    		loanFactory.save(roomLoan);
    		
		}

    }
    
    
    
    //����������������ɵ��տ�����ɵĳ��ɻ��ܵ�
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	BankPaymentInfo bankPayInfo = getBankPaymentInfo(ctx,billId);
//    	ReceiveGatherInfo recGatherInfo = bankPayInfo.getReceiveGather();
//    	if(recGatherInfo.isFiVouchered())
//    		throw new EASBizException(new NumericExceptionSubItem("100","��Ӧ�ĳ��ɻ��ܵ�������ƾ֤��"));
//    	if(FDCBillStateEnum.AUDITTED.equals(recGatherInfo.getState()))
//    		throw new EASBizException(new NumericExceptionSubItem("100","��Ӧ�ĳ��ɻ��ܵ������������ȷ��������ɻ��ܵ���"));
    	
//    	ReceiveGatherFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(recGatherInfo.getId()));
    	
//    	Set sheRevBillIDSet = new HashSet();
    	BankPaymentEntryCollection bankEntryColl = bankPayInfo.getBankPaymentEntry();
    	for(int i=0;i<bankEntryColl.size();i++)
    	{
    		BankPaymentEntryInfo bankEntryInfo = bankEntryColl.get(i);
    		if(bankEntryInfo.getSheRevBill()!=null)
    		{
    			throw new EASBizException(new NumericExceptionSubItem("100","����ɾ����Ӧ���տ��"));
//    			sheRevBillIDSet.add(bankEntryInfo.getSheRevBill().getParent().getId().toString());
    		} 		
    	}    	    	
//    	if(sheRevBillIDSet.size()>0)
//    	{
//    		Iterator iter = sheRevBillIDSet.iterator();
//    		while(iter.hasNext())
//    		{
//    			String sheRevID = (String)iter.next();
//    			SHERevBillFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(sheRevID));
//    		}
//    	}   	
    	super._unAudit(ctx, billId);
    }
    
    protected BankPaymentInfo getBankPaymentInfo(Context ctx, BOSUuid billId) throws EASBizException, BOSException
    {
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("id");
		sels.add("customerDisName");
		sels.add("appAmount");
		sels.add("paymentAmout");		
		sels.add("number");
		sels.add("paymentDate");
		sels.add("settlementNumber");
		
		sels.add("receiveGather.id");
		sels.add("receiveGather.number");
		sels.add("receiveGather.state");
		
		sels.add("moneyDefine.id");
		sels.add("moneyDefine.number");
		sels.add("moneyDefine.name");
		
		sels.add("project.name");
		sels.add("project.number");
		sels.add("paymentBank.name");
		sels.add("paymentBank.number");
		sels.add("paymentAccountBank.name");
		sels.add("paymentAccountBank.number");
		sels.add("settlementType.name");
		sels.add("settlementType.number");
		sels.add("revBank.name");
		sels.add("revBank.number");
		sels.add("revAccountBank.name");
		sels.add("revAccountBank.number");
		sels.add("revAccount.name");
		sels.add("revAccount.number");
		sels.add("oppAccount.name");
		sels.add("oppAccount.number");
		sels.add("bankPaymentEntry.id");
		sels.add("bankPaymentEntry.moneyDefine.id");
		sels.add("bankPaymentEntry.moneyDefine.name");
		sels.add("bankPaymentEntry.moneyDefine.number");
		sels.add("bankPaymentEntry.room.id");
		sels.add("bankPaymentEntry.room.name");
		sels.add("bankPaymentEntry.room.number");
		sels.add("bankPaymentEntry.signManager.id");
		sels.add("bankPaymentEntry.signManager.name");
		sels.add("bankPaymentEntry.signManager.number");
		sels.add("bankPaymentEntry.signManager.id");
		sels.add("bankPaymentEntry.signManager.name");
		sels.add("bankPaymentEntry.signManager.number");
		sels.add("bankPaymentEntry.signManager.transactionID");
		
		sels.add("bankPaymentEntry.customerDisName");
		sels.add("bankPaymentEntry.appAmount");
		sels.add("bankPaymentEntry.paymentAmount");
		sels.add("bankPaymentEntry.receiptDisName");
		sels.add("bankPaymentEntry.invoiceDisName");
		sels.add("bankPaymentEntry.sheRevBill.id");
		sels.add("bankPaymentEntry.sheRevBill.parent.id");
		BankPaymentInfo bankPayInfo = (BankPaymentInfo) this.getValue(ctx,new ObjectUuidPK(billId), sels);
		return bankPayInfo;
    }
    
	private boolean isInclude(String str,List list)
	{
		boolean result = false;
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i)!=null && str!=null)
			{
				if(str.equals(list.get(i).toString()))
				{
					result = true;
					return result;
				}else
				{
					result = false;
				}
			}			
		}
		return result;
	}
	
	
	
	/**
	 * ����������״̬
	 */
	protected void _setAudittingStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
	    BankPaymentInfo rev = new BankPaymentInfo();
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
		BankPaymentInfo rev = new BankPaymentInfo();
		rev.setId(billId);
		rev.setState(FDCBillStateEnum.SUBMITTED);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("state");
		this.updatePartial(ctx, rev, sels);		
	}	
	protected void checkNumberDup(Context ctx, BankPaymentInfo billInfo) throws BOSException, EASBizException {
		if(!isUseNumber()) return;
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("number", billInfo.getNumber()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("project.id", billInfo.getProject().getId()));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}
		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}
	}
	protected void checkBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {

		BankPaymentInfo FDCBillInfo = ((BankPaymentInfo) model);
		
		checkNumberDup(ctx, FDCBillInfo);

	}
}