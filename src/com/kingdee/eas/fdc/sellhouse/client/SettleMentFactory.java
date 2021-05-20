package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.auxacct.IAsstActType;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PayQuomodoEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.util.UuidException;

public class SettleMentFactory {

	private ReceivingBillInfo receivingBillInfo = null;
	
	/**
	 * @param sysType	����ϵͳ
	 * @param revBillType �տ����   �տ� �˿� ��ת
	 * @param fdcEntryColl ���ز��տ��¼ �еĶ�������п���ͽ��  �����е��տ�����Ҳ������
	 * @param roomInfo ����  
	 * @param purInfo �Ϲ��� -- ���Ϲ�����Ŀͻ���¼�ȶ�������
	 */
	public SettleMentFactory(MoneySysTypeEnum sysType,ReceiveBillTypeEnum revBillType,FDCReceiveBillEntryCollection fdcEntryColl,
				RoomInfo roomInfo,PurchaseInfo purInfo) {
		try {
			receivingBillInfo = createBaseRecBillInfo();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (UuidException e) {
			e.printStackTrace();
		}
		FDCReceiveBillInfo fdcRevBillInfo = receivingBillInfo.getFdcReceiveBill(); 
		fdcRevBillInfo.setMoneySysType(sysType);
		fdcRevBillInfo.setBillTypeEnum(revBillType);  //�տ���� :�տ� �˿� ��ת
		receivingBillInfo.getFdcReceiveBillEntry().addCollection(fdcEntryColl);

		BigDecimal totalAmount = new BigDecimal(0);
		if(fdcEntryColl.size()>0){ //���������receivingBillInfo.setRecBillType�����ԣ�����տ����ƾ֤ʱ�ᱨ�޷���д�Ĵ�
			MoneyDefineInfo monDeInfo = fdcEntryColl.get(0).getMoneyDefine();
			if(monDeInfo.getRevBillType()!=null) 
				receivingBillInfo.setRecBillType(monDeInfo.getRevBillType());
			for(int i=0;i<fdcEntryColl.size();i++) {
				FDCReceiveBillEntryInfo  fdcBillEntryInfo = fdcEntryColl.get(i);
				totalAmount = totalAmount.add(fdcBillEntryInfo.getAmount());
			}
		}
		receivingBillInfo.setAmount(totalAmount);
		
		if (receivingBillInfo.getEntries().isEmpty()) 	{   //�տ�����¼
			ReceivingBillEntryInfo entry = new ReceivingBillEntryInfo();
			entry.setAmount(totalAmount);
			entry.setActualAmt(totalAmount);
			receivingBillInfo.getEntries().add(entry);
		} else	{
			ReceivingBillEntryInfo entry = receivingBillInfo.getEntries().get(0);
			entry.setAmount(totalAmount);
			entry.setActualAmt(totalAmount);
		}
		
		
		fdcRevBillInfo.setRoom(roomInfo);
		if(purInfo!=null)  {				//���ز��տ�е�ϵͳ�ͻ���¼
			fdcRevBillInfo.setPurchase(purInfo);
			fdcRevBillInfo.setSellProject(purInfo.getSellProject());
			PurchaseCustomerInfoCollection  purCustCol = purInfo.getCustomerInfo();
			for(int i=0;i<purCustCol.size();i++) {
				PurchaseCustomerInfoInfo custInfoInfo = purCustCol.get(i);
				CustomerInfo custInfo = null;
				if(custInfoInfo.getCustomer()!=null) {
					custInfo = custInfoInfo.getCustomer().getSysCustomer();
					if(custInfo!=null) {
						if(i==0){	//�����ͻ���Ϊ�տ�Ŀͻ�
							receivingBillInfo.setPayerID(custInfo.getId().toString());
							receivingBillInfo.setPayerName(custInfo.getName());
							receivingBillInfo.setPayerNumber(custInfo.getNumber());
						}
						
						CustomerEntryInfo custEntr = new CustomerEntryInfo();
						custEntr.setCustomer(custInfo);		
						fdcRevBillInfo.getCustomerEntrys().add(custEntr);
					}
				}				
			}
		}			
		
		
	}
	
	
	
	private AsstActTypeInfo getCustomerType() throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("realtionDataObject", "T_BD_Customer",CompareType.EQUALS));
		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(filter);
		AsstActTypeCollection asstActTypeCollection = null;

		IAsstActType asstActType = AsstActTypeFactory.getRemoteInstance();
		asstActTypeCollection = asstActType.getAsstActTypeCollection(evi);

		if (asstActTypeCollection != null && asstActTypeCollection.size() == 1)
		{
			return asstActTypeCollection.get(0);
		}
		return null;
	}    
	
	//����������Ĳ����տ����
	private ReceivingBillInfo createBaseRecBillInfo() throws EASBizException, BOSException, UuidException {
		receivingBillInfo = new ReceivingBillInfo();

		CompanyOrgUnitInfo currentCompany = SysContext.getSysContext().getCurrentFIUnit();
		CurrencyInfo baseCurrency = CurrencyFactory.getRemoteInstance()
				.getCurrencyInfo(new ObjectUuidPK(BOSUuid.read(currentCompany.getBaseCurrency().getId().toString())));
		receivingBillInfo.setCurrency(baseCurrency);
		
		receivingBillInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		receivingBillInfo.setCreateTime(FDCCommonServerHelper.getServerTimeStamp());
		
		receivingBillInfo.setIsInitializeBill(false);
		receivingBillInfo.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
		receivingBillInfo.setBizDate(new Date());
		
		receivingBillInfo.setPayerType(getCustomerType());		//����������
		receivingBillInfo.setSourceType(SourceTypeEnum.FDC);	//��Դ���ͣ����ز��ɱ�����
		receivingBillInfo.setSourceSysType(SourceTypeEnum.FDC);
		
		receivingBillInfo.setBillStatus(BillStatusEnum.RECED);	//���տ�״̬   -- ��̨����ģ����ֱ����Ϊ���տ�

		
		FDCReceiveBillInfo fdcReceiveBillInfo = new FDCReceiveBillInfo();	 //�����ķ��ز��տ
		fdcReceiveBillInfo.setId(BOSUuid.create(fdcReceiveBillInfo.getBOSType()));
	
		fdcReceiveBillInfo.setPayQuomodo(PayQuomodoEnum.CASH);	//���ʽ
		
		fdcReceiveBillInfo.setGatheringObject(GatheringObjectEnum.room);  //�ݶ�ֻ����Է����տ� -- ����
		receivingBillInfo.setFdcReceiveBill(fdcReceiveBillInfo);		

		return receivingBillInfo;
	}
	

	
	
	public void submit() throws EASBizException, BOSException{	 
			FDCReceiveBillInfo fdcRevBillInfo = receivingBillInfo.getFdcReceiveBill();
			
			RoomInfo roomInfo = fdcRevBillInfo.getRoom();
			if(roomInfo==null)	return;
			
/*			
 * 			FDCReceiveBillEntryCollection  fdcRecBillEntryColl = receivingBillInfo.getFdcReceiveBillEntry();
 * 			if(fdcRecBillEntryColl.size()==0) return;
			for(int i=0;i<fdcRecBillEntryColl.size();i++) {
				FDCReceiveBillEntryInfo billEntryInfo =	fdcRecBillEntryColl.get(i);
				if(billEntryInfo.getAmount().compareTo(new BigDecimal(0))==0) return;
			}	*/
		
			ReceivingBillFactory.getRemoteInstance().addnew(receivingBillInfo);
			FDCReceiveBillFactory.getRemoteInstance().addnew(fdcRevBillInfo);
		
	}
	
	
	
	
	
	
	
	
}
