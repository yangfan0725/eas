package com.kingdee.eas.fdc.sellhouse;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBillEntry;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryCollection;
import com.kingdee.eas.fdc.basecrm.RevFDCCustomerEntryInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryCollection;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;


/**
 * ת�������
 * 
 *  �������裺
 *  1	genTrasfBill()
 *  2	setTrasfEntry()
 *  3	submitNewTrasfBill()
 * @author jeegan_wang
 */

public class GenFdcTrasfBillFactory {
	

	//����ĳһ���Ϲ�����Ӧ����ϸ��id,��ѯ��һ���Ѵ��ڵ��տ,���Ƴ�һ���µ��տ
	public static FDCReceivingBillInfo genTrasfBill(Context ctx,String revListId) throws BOSException, EASBizException	{
		FDCReceivingBillInfo fdcRevBillInfo = null;	//����ת�
		//�����Ѵ��ڵ��տ������һ���µ�
		IFDCReceivingBillEntry fdcBillEntryFactry = null;
		if(ctx!=null)	fdcBillEntryFactry = FDCReceivingBillEntryFactory.getLocalInstance(ctx);
		else fdcBillEntryFactry = FDCReceivingBillEntryFactory.getRemoteInstance();
		
		if(revListId==null || revListId.trim().equals("") ) {
			throw new EASBizException(new NumericExceptionSubItem("100","�տ���ϸid����Ϊ��!"));
		}
		
		FDCReceivingBillEntryCollection fdcBillEntryColl = fdcBillEntryFactry
				.getFDCReceivingBillEntryCollection("select head.id where revListId = '"+ revListId +"' and " +
							" (head.revBillType = '"+RevBillTypeEnum.GATHERING_VALUE+"' or head.revBillType = '"+RevBillTypeEnum.TRANSFER_VALUE+"') ");	
		if(fdcBillEntryColl.size()==0) 
			throw new EASBizException(new NumericExceptionSubItem("100","���Ҳ����տ���ϸ��Ӧ���տ�ݣ�����ת�ʧ��!"));
		
		fdcRevBillInfo = (FDCReceivingBillInfo)fdcBillEntryColl.get(0).getHead().clone();
		IFDCReceivingBill fdcBillFactory = null;
		if(ctx!=null) fdcBillFactory = FDCReceivingBillFactory.getLocalInstance(ctx);
		else fdcBillFactory = FDCReceivingBillFactory.getRemoteInstance();
		
		fdcRevBillInfo = fdcBillFactory.getFDCReceivingBillInfo("select *,entries.*,fdcCustomers.*,entries.sourceEntries.* " +
							" where id ='"+fdcRevBillInfo.getId().toString()+"'");			
		
		fdcRevBillInfo.setId(null);
		fdcRevBillInfo.setFiVouchered(false);
		fdcRevBillInfo.setBizDate(new Date());
		fdcRevBillInfo.setAmount(FDCHelper.ZERO);
		fdcRevBillInfo.setOriginalAmount(FDCHelper.ZERO);
		fdcRevBillInfo.setRevBillType(RevBillTypeEnum.transfer);
		fdcRevBillInfo.setBillStatus(RevBillStatusEnum.RECED);
		fdcRevBillInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		if(ctx!=null)
			fdcRevBillInfo.setLastUpdateUser(ContextUtil.getCurrentUserInfo(ctx));
		else
			fdcRevBillInfo.setLastUpdateUser(SysContext.getSysContext().getCurrentUserInfo());
		fdcRevBillInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		if(ctx!=null)
			fdcRevBillInfo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
		else
			fdcRevBillInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		fdcRevBillInfo.setBillStatus(RevBillStatusEnum.SUBMIT);
		fdcRevBillInfo.setState(FDCBillStateEnum.SUBMITTED);
		fdcRevBillInfo.getEntries().clear();
		
		FDCReceivingBillEntryCollection addEntryColl = fdcRevBillInfo.getEntries();;
		for(int i=0;i<addEntryColl.size();i++) {
			FDCReceivingBillEntryInfo addEntryInfo = addEntryColl.get(i);
			addEntryInfo.setId(BOSUuid.create(addEntryInfo.getBOSType()));
			addEntryInfo.setHead(fdcRevBillInfo);
		}
		
		RevFDCCustomerEntryCollection fdcCustColl = fdcRevBillInfo.getFdcCustomers();
		for(int i=0;i<fdcCustColl.size();i++) {
			RevFDCCustomerEntryInfo fdcCustInfo = fdcCustColl.get(i);
			fdcCustInfo.setId(null);
			fdcCustInfo.setHead(fdcRevBillInfo);
		}			
		

		
		if(fdcRevBillInfo==null) {
			throw new EASBizException(new NumericExceptionSubItem("100","����ת�ʧ��!"));
		}
		
		//���ñ���
		
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
		if(orgUnit!=null)
			fdcRevBillInfo.setOrgUnit(orgUnit.castToFullOrgUnitInfo());
		
		if(!iCodingRuleManager.isExist(fdcRevBillInfo, orgUnit.getId().toString())) {
			//throw new EASBizException(new NumericExceptionSubItem("100","δ���ñ������ �������Զ�����ת�!(��֯��"+orgUnit==null?"":orgUnit.getId().toString()+")"));	
			throw new EASBizException(new NumericExceptionSubItem("100","δ���ñ������ �������Զ�����ת�!"));
		}
		
		String retNumber = iCodingRuleManager.getNumber(fdcRevBillInfo, orgUnit.getId().toString());
		fdcRevBillInfo.setNumber(retNumber); 
		
		return fdcRevBillInfo;
	}
	
	/**
	 * �������տ�ķ�¼��ϸ		
	 * @param fdcRevBillInfo	ָ�����µ�ת�����
	 * @param trasfAmount		ת����
	 * @param settleType		���㷽ʽ	  
	 * @param revMoneyType		ת���������
	 * @param revAccount		ת���Ŀ  ������Ϊ�գ�Ϊ���Ǵӿ����Ŀ���ձ��в�
	 * @param revListId			ת����ϸid
	 * @param revType			ת����ϸ����
	 * @param fromRevMoneyType	ת����������
	 * @param fromRevListId		ת����ϸid
	 * @param fromRevType		ת����ϸ����
	 * @throws EASBizException
	 * @throws BOSException 
	 */
	public static void setTrasfEntry(Context ctx,FDCReceivingBillInfo fdcRevBillInfo,BigDecimal trasfAmount,SettlementTypeInfo settleType
			,MoneyDefineInfo revMoneyType,AccountViewInfo revAccount,String revListId,RevListTypeEnum revType,MoneyDefineInfo fromRevMoneyType,String fromRevListId,RevListTypeEnum fromRevType) throws EASBizException, BOSException {
		if(fdcRevBillInfo==null) {
			throw new EASBizException(new NumericExceptionSubItem("100","���տ����Ϊ��!"));
		}
		if(trasfAmount.compareTo(FDCHelper.ZERO)<=0 || revListId==null || fromRevListId==null ) {
			throw new EASBizException(new NumericExceptionSubItem("100","���ݵĲ���������Ҫ��!(ת���С��0��ת����ϸidΪ�ջ�ת����ϸidΪ��)"));
		}
		if(revMoneyType==null || fromRevMoneyType==null)	{
			throw new EASBizException(new NumericExceptionSubItem("100","���ݵ�ת��������ͺ�ת���������Ͳ���Ϊ��!"));
		}
		
		FDCReceivingBillEntryInfo newEntry = new FDCReceivingBillEntryInfo();
		newEntry.setRevAmount(trasfAmount);
		newEntry.setRevListId(revListId);
		if(revAccount==null)
			revAccount = getContractTableByMoneyDefine(ctx,revMoneyType); 
		AccountViewInfo oppAccount = getContractTableByMoneyDefine(ctx,fromRevMoneyType); 
		if(revAccount==null) 
			throw new EASBizException(new NumericExceptionSubItem("100","�Ҳ���ת������Ӧ�Ŀ�Ŀ����������Ŀ���ձ�"));
		if(oppAccount==null) 
			throw new EASBizException(new NumericExceptionSubItem("100","�Ҳ���ת�������Ӧ�Ŀ�Ŀ����������Ŀ���ձ�"));
		newEntry.setRevAccount(oppAccount);
		newEntry.setOppAccount(revAccount);
		
		newEntry.setMoneyDefine(revMoneyType);
		newEntry.setSettlementType(settleType);
		newEntry.setRevListType(revType);				//��ϸ����
		TransferSourceEntryInfo newTrsfEntry = new TransferSourceEntryInfo();
		newTrsfEntry.setAmount(trasfAmount);
		newTrsfEntry.setFromRevListId(fromRevListId);
		newTrsfEntry.setFromRevListType(fromRevType);	//��ϸ����
		newEntry.getSourceEntries().add(newTrsfEntry);
		fdcRevBillInfo.getEntries().add(newEntry);
		fdcRevBillInfo.setAmount(fdcRevBillInfo.getAmount().add(trasfAmount));
		fdcRevBillInfo.setOriginalAmount(fdcRevBillInfo.getOriginalAmount().add(trasfAmount));
	}
	
	//handleClassName Ĭ���� "com.kingdee.eas.fdc.sellhouse.app.SheRevHandle"
	//����ύʱ���뿼�����տ��ύ��ɾ���Ĳ�������ķ�д���� �����ʹ�� "com.kingdee.eas.fdc.sellhouse.app.SheRevNoHandle"
	public static void submitNewTrasfBill(Context ctx,FDCReceivingBillInfo fdcRevBillInfo,String handleClassName) throws BOSException, EASBizException	{
		IFDCReceivingBill fdcBillFactory = null;
		if(ctx!=null)  fdcBillFactory = FDCReceivingBillFactory.getLocalInstance(ctx);
		else fdcBillFactory = FDCReceivingBillFactory.getRemoteInstance();
		
		if(fdcRevBillInfo==null) {
			throw new EASBizException(new NumericExceptionSubItem("100","���տ����Ϊ��!"));
		}
		
		if(fdcRevBillInfo.getEntries()==null || fdcRevBillInfo.getEntries().size()==0) {
			throw new EASBizException(new NumericExceptionSubItem("100","���տ��ת�����ϸ����Ϊ��!"));
		}
		
		FDCReceivingBillEntryCollection entryColl = fdcRevBillInfo.getEntries();
		for(int i=0;i<entryColl.size();i++)	{
			FDCReceivingBillEntryInfo entryInfo = entryColl.get(i);
			if(entryInfo.getRevAmount()==null || entryInfo.getRevAmount().compareTo(FDCHelper.ZERO)<=0 || entryInfo.getRevListId()==null) {
				throw new EASBizException(new NumericExceptionSubItem("100","���տ�ĵ�("+(i+1)+")��ת�����ϸ����Ϣ����ȷ!"));
			}
			if(entryInfo.getSourceEntries()==null || entryInfo.getSourceEntries().size()==0) {
				throw new EASBizException(new NumericExceptionSubItem("100","���տ�ĵ�("+(i+1)+")����Ӧ��ת����Ϊ��!"));
			}
			TransferSourceEntryCollection trasfColl = entryInfo.getSourceEntries();
			BigDecimal allTrasfAmount = FDCHelper.ZERO;
			for(int j=0;j<trasfColl.size();j++)	{
				if(trasfColl.get(j).getAmount()==null || trasfColl.get(j).getAmount().compareTo(FDCHelper.ZERO)<=0)
					throw new EASBizException(new NumericExceptionSubItem("100","���տ�ĵ�("+(i+1)+")����Ӧ��ת�������ȷ!"));
				allTrasfAmount = allTrasfAmount.add(trasfColl.get(j).getAmount());
			}
			if(entryInfo.getRevAmount().compareTo(allTrasfAmount)!=0){
				throw new EASBizException(new NumericExceptionSubItem("100","���տ�ĵ�("+(i+1)+")����Ӧ��ת���ת�����һ��!"));
			}
		}
		
		fdcBillFactory.submitRev(fdcRevBillInfo, handleClassName);
	}
	
	
	
	
	
	/**
	 * ���ݿ������Ͳ�ѯ�������Ŀ���ձ�
	 * */
	private static AccountViewInfo getContractTableByMoneyDefine(Context ctx,MoneyDefineInfo moneyDefine) throws EASBizException, BOSException {
		if(moneyDefine==null) return null;
		CompanyOrgUnitInfo company = null;
		if(ctx!=null) company = ContextUtil.getCurrentFIUnit(ctx);
		else company = SysContext.getSysContext().getCurrentFIUnit();
			
		String kSql = "select accountView.*,isChanged where moneyDefine.id='"+moneyDefine.getId().toString()+"'";
		if(company != null){
			kSql += " and fullOrgUnit.id='"+ company.getId().toString() +"'";
		}else{
			kSql += " and fullOrgUnit.id='idnull'";
		}
		MoneySubjectContrastCollection monContractInfoColl = null;
		if(ctx!=null)
			monContractInfoColl = MoneySubjectContrastFactory.getLocalInstance(ctx).getMoneySubjectContrastCollection(kSql);
		else
			monContractInfoColl = MoneySubjectContrastFactory.getRemoteInstance().getMoneySubjectContrastCollection(kSql);
		
		if(monContractInfoColl.size()>0) {
			return monContractInfoColl.get(0).getAccountView();
		}else{
			return null;
		}
		
	}
	
	
	
	
}
