package com.kingdee.eas.fdc.invite.supplier.app;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.KAClassficationInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierPurchaseInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierPurchaseInfoInfo;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.master.cssp.client.CSClientUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.INewOrgUnitFacade;
import com.kingdee.eas.basedata.org.NewOrgUnitFacadeFactory;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fi.gl.common.toolkit.sqlbuilder.SqlBuilder;
import com.kingdee.eas.fm.common.EJBAccessFactory;
import com.kingdee.eas.framework.util.TimeStampUtility;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public abstract class FDCSplAuditBaseBillControllerBean extends AbstractFDCSplAuditBaseBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.FDCSplAuditBaseBillControllerBean");
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	super._audit(ctx, billId);
    	writeBackToSupplier(ctx, billId);
    }
	/**
     * ��Ӧ�������ݻ�д����
     * @param ctx
     * @param billId
     * @throws BOSException
     * @throws EASBizException
     */
    private void writeBackToSupplier(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	FDCSplAuditBaseBillInfo info = getFDCSplAuditBaseBillInfo(ctx, billId);
    	if (isNeedWriteBack(ctx, info)) {      		
    		createSupplierBill(ctx, info);
    	}
    }
    
    /**
     * �����ز���Ӧ�̵����ݻ�д�������ݵĹ�Ӧ����
     * @param ctx
     * @param info
     * @throws BOSException
     * @throws EASBizException
     */
    private void createSupplierBill(Context ctx, FDCSplAuditBaseBillInfo info) throws BOSException, EASBizException {
    	SupplierInfo supplier = new SupplierInfo();
    	SelectorItemCollection selector = new SelectorItemCollection();
    	selector.add("id");
    	selector.add("number");
    	selector.add("name");
    	selector.add("supplierType.id");
    	selector.add("supplierType.number");
    	selector.add("supplierType.name");
    	selector.add("supplierType.groupStandard.id");
    	selector.add("supplierType.groupStandard.name");
    	selector.add("businessNum");
    	selector.add("province.id");
    	selector.add("province.number");
    	selector.add("province.name");
    	selector.add("city.id");
    	selector.add("city.number");
    	selector.add("city.name");
    	selector.add("address");
    	selector.add("bankName");
    	selector.add("bankCount");
    	selector.add("hasCreatedSupplierBill");
    	SupplierStockInfo supplierStock = SupplierStockFactory.getLocalInstance(ctx).getSupplierStockInfo(new ObjectUuidPK(info.getSupplier().getId()), selector);
    	
    	//����Ѿ����ɹ������ݵĹ�Ӧ���ˣ��Ͳ���Ҫ�ٴ���
    	if (supplierStock.isHasCreatedSupplierBill()) {
    		return;
    	}
    	/*
    	���	���ز���Ӧ���ֶ�	�����ݹ�Ӧ���ֶ�
    	1	��������			����
    	2	����				����
    	3	��˾����			����
    	4	Ӫҵִ��			Ӫҵִ��
    	5	����ʡ��			ʡ��
    	6	���ڳ���			����
    	7	��˾��ַ			��ַ
    	8	��������			��������
    	9	�����ʺ�			�����ʺ�
    	*/
    	supplier.setBrowseGroup(supplierStock.getSupplierType());
    	supplier.setNumber(supplierStock.getNumber());
    	supplier.setName(supplierStock.getName());
    	supplier.setBusiLicence(supplierStock.getBusinessNum());
    	supplier.setProvince(supplierStock.getProvince());
    	supplier.setCity(supplierStock.getCity());
    	supplier.setAddress(supplierStock.getAddress());
    	
    	supplier.setIsInternalCompany(false);
    	supplier.setCreator(ContextUtil.getCurrentUserInfo(ctx)); // ��ǰ�û�
    	supplier.setCreateTime(TimeStampUtility.getTime());
    	supplier.setCU(ContextUtil.getCurrentCtrlUnit(ctx)); // ������˾
    	supplier.setAdminCU(ContextUtil.getCurrentCtrlUnit(ctx));
    	supplier.setVersion(1);// �汾
    	supplier.setUsedStatus(UsedStatusEnum.UNAPPROVE);// ��Ӧ��״̬
    	supplier.setTaxRate(new BigDecimal("0.00"));
    	supplier.setCreateTime(TimeStampUtility.getTime());
    	supplier.setCreator(ContextUtil.getCurrentUserInfo(ctx));
    	supplier.setLastUpdateTime(null);
    	supplier.setLastUpdateUser(null);
    	
    	//��Ӧ�̷�����ϸ
    	SupplierGroupDetailInfo groupDetailInfo = new SupplierGroupDetailInfo();
    	groupDetailInfo.setSupplierGroup(supplierStock.getSupplierType());
    	groupDetailInfo.setSupplierGroupStandard(supplierStock.getSupplierType().getGroupStandard());
    	groupDetailInfo.setSupplierGroupFullName(supplierStock.getSupplierType().getGroupStandard().getName() + supplierStock.getSupplierType().getName());
    	supplier.getSupplierGroupDetails().add(groupDetailInfo);
		
    	IObjectPK supplierPK = SupplierFactory.getLocalInstance(ctx).submit(supplier);
    	supplier.setId(BOSUuid.read(supplierPK.toString()));
    	
    	updateHasSupplierBill(ctx, supplierStock);
    	
    	createDefaultOtherInfo(ctx, supplier, supplierStock);
    }
    
    
    /**
     * �������ҳǩ����Ϣͬʱ��������Ҳҳǩ��Ĭ����Ϣ
     * @param ctx
     * @param info
     * @param supplierStock
     * @throws BOSException
     * @throws EASBizException
     */
    private void createDefaultOtherInfo(Context ctx, SupplierInfo info, SupplierStockInfo supplierStock) throws BOSException, EASBizException {
    	//������Ϣ
		CompanyOrgUnitInfo company = ContextUtil.getCurrentFIUnit(ctx);
    	INewOrgUnitFacade iNewOrgUnit = null;
		iNewOrgUnit = NewOrgUnitFacadeFactory.getLocalInstance(ctx);
		if (company != null) {// Ĭ�ϵ�ǰ������֯����û�з��
			SupplierCompanyInfoInfo vo = new SupplierCompanyInfoInfo();
			vo.setCompanyOrgUnit(company);
			vo.setSupplier(info);
			vo.setCU(company.getCU());
			vo.setIsFreezePayment(false);
			vo.setCreateTime(TimeStampUtility.getTime());
			vo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
			vo.setLastUpdateTime(null);
			vo.setLastUpdateUser(null);
			
			// ��ǰ������֯�Ƿ񱻷��
			iNewOrgUnit.checkIsOUSealUp(company.getId().toString(), OrgType.COMPANY_VALUE);
			
			vo.setSettlementCurrency(company.getBaseCurrency());
			//��Ӧ������
			if (supplierStock.getBankCount() != null) {
				SupplierCompanyBankInfo supplierBank = new SupplierCompanyBankInfo();
				if (supplierStock.getBankName() != null) {
					supplierBank.setBank(supplierStock.getBankName());
				}
				supplierBank.setBankAccount(supplierStock.getBankCount());
				vo.getSupplierBank().add(supplierBank);
			}
			
			SupplierCompanyInfoFactory.getLocalInstance(ctx).submit(vo);
		}
		
		// �ɹ���Ϣ
		PurchaseOrgUnitInfo purOrg = ContextUtil.getCurrentPurchaseUnit(ctx);
		if (purOrg!= null) {// Ĭ�ϵ�ǰ�ɹ���֯
			SupplierPurchaseInfoInfo purchaseInfo = new SupplierPurchaseInfoInfo();
			purchaseInfo.setPurchaseOrgUnit(purOrg);
			purchaseInfo.setDeliverOrgUnit(info);
			purchaseInfo.setBillingOrgUnit(info);
			purchaseInfo.setSupplier(info);
			purchaseInfo.setGrade("0");
			purchaseInfo.setCU(purOrg.getCU());
			purchaseInfo.setIsFreezeMakeOrder(false);
			purchaseInfo.setCreateTime(TimeStampUtility.getTime());
			purchaseInfo.setCreator(ContextUtil.getCurrentUserInfo(ctx));
			purchaseInfo.setLastUpdateTime(null);
			purchaseInfo.setLastUpdateUser(null);
				// ��ǰ������֯�Ƿ񱻷��
				iNewOrgUnit.checkIsOUSealUp(purOrg.getId().toString(),OrgType.PURCHASE_VALUE);
				SupplierPurchaseInfoFactory.getLocalInstance(ctx).submit(purchaseInfo);
		}
    }
    
    
    /**
     * ���ݵ���ID������ȡInfo
     * @param ctx
     * @param billId
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected FDCSplAuditBaseBillInfo getFDCSplAuditBaseBillInfo(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	EntityObjectInfo info = EJBAccessFactory.createLocalInstance(ctx).getEntityInfo(billId.getType());
		if(info == null) {
			return null;
		}
		//by tim_gao �õ����ع�������Ϣ 2011-04-20
		SelectorItemCollection sic = new SelectorItemCollection();
	    sic.add(new SelectorItemInfo("*"));
	    sic.add(new SelectorItemInfo("supplier.name"));
	    sic.add(new SelectorItemInfo("supplier.number"));
		if("FDCSplKeepContractAuditBill".equals(info.getRealName())){
			return FDCSplKeepContractAuditBillFactory.getLocalInstance(ctx).getFDCSplKeepContractAuditBillInfo(new ObjectUuidPK(billId));
		} else if("FDCSplQualificationAuditBill".equals(info.getRealName())){
			return FDCSplQualificationAuditBillFactory.getLocalInstance(ctx).getFDCSplQualificationAuditBillInfo(new ObjectUuidPK(billId),sic);
		} else if("FDCSplPeriodAuditBill".equals(info.getRealName())){
			return FDCSplPeriodAuditBillFactory.getLocalInstance(ctx).getFDCSplPeriodAuditBillInfo(new ObjectUuidPK(billId));
		}
		return null;
	}
    
    /**
     * �������ʱ�Ƿ���Ҫ��Ӧ�������ݻ�д��(������Ҫ��д�˷���)
     * ����õ������жԹ�Ӧ�̵ķ���������ۣ�����������һ������Ǻϸ�ģ�����Ҫ��Ӧ�������ݻ�д
     * @param ctx
     * @param info
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    protected boolean isNeedWriteBack(Context ctx, FDCSplAuditBaseBillInfo info) throws BOSException, EASBizException {
    	
    	return false;
    }
    
    /**
     * ���¹�Ӧ�̼�¼��hasCreatedSupplierBill�ֶ�Ϊtrue
     * @param ctx
     * @param info
     * @throws BOSException
     * @throws EASBizException
     */
    protected void updateHasSupplierBill(Context ctx, SupplierStockInfo info) throws BOSException, EASBizException {
    	SupplierStockInfo billInfo = new SupplierStockInfo(); 
    	billInfo.setId(info.getId());
    	billInfo.setHasCreatedSupplierBill(true);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("hasCreatedSupplierBill");
		SupplierStockFactory.getLocalInstance(ctx).updatePartial(billInfo, selector);
    }
    
}