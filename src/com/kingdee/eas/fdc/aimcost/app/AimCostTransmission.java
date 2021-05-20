package com.kingdee.eas.fdc.aimcost.app;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitCollection;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.util.StringUtils;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		Ŀ��ɱ���������ת����
 *		
 * @author		����	
 * @createDate	2011-6-16		
 */
public class AimCostTransmission extends AbstractDataTransmission {

	AimCostInfo info = null;
	
	CostEntryInfo infoEntry = null;
	
	private static String resource = "com.kingdee.eas.fdc.aimcost.AimCostTransmissionResource";
	
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = AimCostFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		try {
			info = transmitHead(hsData, ctx);
			if (info == null) {
				return null;
			}
			infoEntry = transmitEntry(hsData, ctx);
			infoEntry.setHead(info);
			info.getCostEntry().add(infoEntry);
		} catch (TaskExternalException e) {
			info = null;
			throw e;
		}
		
		return info;
	}
	
	
	public AimCostInfo transmitHead(Hashtable hsData, Context ctx) throws TaskExternalException {
		// ������Ŀ����
		String curProjectLongNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$costAccount$curProject_longNumber");
		// ������Ŀ����
		String curProjectName = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$costAccount$curProject_name_l2");	
		// �汾��
		String fVersionNumber = FDCTransmissionHelper.getFieldValue(hsData, "FVersionNumber");		
		// �汾����
		String fVersionName = FDCTransmissionHelper.getFieldValue(hsData, "FVersionName");		
		// �Ƶ��˱���
		String fCreatorNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCreator_number");		
		// �Ƶ�����
		String fCreateTime = FDCTransmissionHelper.getFieldValue(hsData, "FCreateTime");		
		// �����˱���
		String fAuditorNumber = FDCTransmissionHelper.getFieldValue(hsData, "FAuditor_number");		
		// ��������
		String fAuditDate = FDCTransmissionHelper.getFieldValue(hsData, "FAuditDate");	
		// ����״̬
		String fState = FDCTransmissionHelper.getFieldValue(hsData, "FState");

		/**
		 * ��¼��У��
		 */
		if(StringUtils.isEmpty(curProjectLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "CurProjectNumberNotNull"));
		}
		if(StringUtils.isEmpty(fVersionNumber)) {
			throw new TaskExternalException(getResource(ctx, "VersionNumberNotNull"));
		}
		if(StringUtils.isEmpty(fVersionName)) {
			throw new TaskExternalException(getResource(ctx, "VersionNameNotNull"));
		}
		if(StringUtils.isEmpty(fCreatorNumber)) {
			throw new TaskExternalException(getResource(ctx, "CreatorNumberNotNull"));
		}
		if(StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException(getResource(ctx, "CreateTimeNotNull"));
		}
		if(StringUtils.isEmpty(fAuditorNumber)) {
			throw new TaskExternalException(getResource(ctx, "AuditorNumberNotNull"));
		}
		if(StringUtils.isEmpty(fAuditDate)) {
			throw new TaskExternalException(getResource(ctx, "AuditorDate"));
		}
	
		/**
		 * �ַ�����У��
		 */
		if(fVersionNumber.length() > 10) {
			throw new TaskExternalException(getResource(ctx, "VersionNumberIsOver10"));
		}
		if(fVersionName.length() > 20) {
			throw new TaskExternalException(getResource(ctx, "VersionNameIsOver20"));
		}
	
		/**
		 * �����ж�
		 */
		if(!FDCTransmissionHelper.isNumber(fVersionNumber)) {
			throw new TaskExternalException(getResource(ctx, "VersionNumberNotNumber"));
		}
		if(fVersionNumber.indexOf(".") == -1) {
			fVersionNumber += ".0";
		}
		
		/**
		 * ���ڸ�ʽУ��
		 */
		FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(ctx, "CreateDateFormatError"));
		Date auditDate = FDCTransmissionHelper.checkDateFormat(fAuditDate, getResource(ctx, "AuditDateFormatError"));
		
		/**
		 * ��ѯ����У��
		 */
		CurProjectInfo curProjectInfo = null;		
		UserInfo creatot = null;
		UserInfo auditor = null;
		try {
			// ������Ŀ����
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("longnumber", curProjectLongNumber.replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "CurProjectNumberNotFound"));
			}
			
			
			// �жϵ�ͷ�Ƿ��Ѿ�����
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("orgOrProId", curProjectInfo.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("versionNumber", fVersionNumber));
			filter.getFilterItems().add(new FilterItemInfo("versionName", fVersionName));
			filter.setMaskString("#0 and #1 and #2");
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			AimCostCollection aimCostCollection = AimCostFactory.getLocalInstance(ctx).getAimCostCollection(view);
			if(aimCostCollection.size() > 0) {
				info = aimCostCollection.get(0);
				return info;
			} else {
				info = new AimCostInfo();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// �Ƶ���
		creatot = isUserNumber(fCreatorNumber, ctx);
		if(creatot == null) {
			throw new TaskExternalException(getResource(ctx, "CreatorNumberNotFound"));
		}
			
		// ������
		auditor = isUserNumber(fAuditorNumber, ctx);
		if(auditor == null){
			throw new TaskExternalException(getResource(ctx, "AuditorNumberNotFound"));
		}
		
		/**
		 * ���ֵ
		 */
		info.setVersionNumber(fVersionNumber);
		info.setVersionName(fVersionName);
		info.setOrgOrProId(curProjectInfo.getId().toString());
		// ����״̬:ö��ֵ�����桢��������Ĭ��Ϊ��������
		if (fState.trim().equals(getResource(ctx,"Saved"))) {
			info.setState(FDCBillStateEnum.SAVED);
		} else {
			info.setState(FDCBillStateEnum.AUDITTED);
		}
		info.setCreator(creatot);
		info.setCreateTime(Timestamp.valueOf(fCreateTime + " 00:00:00"));
		info.setAuditor(auditor);
		info.setAuditDate(auditDate);
		
		return info;
	}
	
	private CostEntryInfo transmitEntry(Hashtable hsData, Context ctx) throws TaskExternalException {
		// ��֯����
		String costOrg_longNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$costAccount$curProject$costOrg_longNumber");
		// ������Ŀ����
		String curProjectLongNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$costAccount$curProject_longNumber");
		// ������Ŀ����
		String curProjectName = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$costAccount$curProject_name_l2");	
		// �ɱ���Ŀ����
		String costAccountNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$costAccount_number");
		// �ɱ���Ŀ����
		String costAccountName = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$costAccount_name_l2");
		// ������
		String fCostEntryWorkload = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry_workload");
		// ��λ
		String fCostEntryUnit = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry_unit");
		// ����
		String fCostEntrPrice = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry_price");
		// Ŀ��ɱ�(Ԫ)
		String fCostEntryCostAmount = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry_costAmount");		
		// ������Ʒ
		String productNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry$product_number");		
		// ��ע
		String fCostEntryDesc = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry_desc");		
		// �仯ԭ��
		String fCostEntryChangeReason = FDCTransmissionHelper.getFieldValue(hsData, "FCostEntry_changeReason");	
		
		if(StringUtils.isEmpty(fCostEntryCostAmount)) {
			throw new TaskExternalException(getResource(ctx, "CostAmountNotNull"));
		}
		if(StringUtils.isEmpty(costAccountNumber)) {
			throw new TaskExternalException(getResource(ctx, "CostAccountNumberNotNull"));
		}
		
		if(!StringUtils.isEmpty(fCostEntryDesc) && fCostEntryDesc.length() > 200) {
			throw new TaskExternalException(getResource(ctx, "DescIsOver200"));
		}
		if(!StringUtils.isEmpty(fCostEntryChangeReason) && fCostEntryChangeReason.length() > 200) {
			throw new TaskExternalException(getResource(ctx, "ChangeReasonIsOver200"));
		}
		
		if(!StringUtils.isEmpty(fCostEntryWorkload) && !FDCTransmissionHelper.isNumber(fCostEntryWorkload)) {
			throw new TaskExternalException(getResource(ctx, "CostEntryWorkloadNotNumber"));
		}
		if(!StringUtils.isEmpty(fCostEntrPrice) && !FDCTransmissionHelper.isNumber(fCostEntrPrice)) {
			throw new TaskExternalException(getResource(ctx, "CostEntrPriceNotNuber"));
		}
		if(!FDCTransmissionHelper.isNumber(fCostEntryCostAmount)) {
			throw new TaskExternalException(getResource(ctx, "CostEntryCostAmountNotNumber"));
		}
		// �ж�С��λ
		
		
		MeasureUnitCollection measureCollection = null;
		ProductTypeCollection productTypeCollection = null;
		ProductTypeInfo productTypeInfo = null;
		CostAccountInfo costAccount = null;
		CostCenterOrgUnitInfo costCenter = null;
		CurProjectInfo curProjectInfo = null;
		try {
			// ������Ŀ����
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("longnumber", curProjectLongNumber.replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "CurProjectNumberNotFound"));
			}
			
			// ��֯����
			costCenter = curProjectInfo.getCostCenter();
			String costCenterLongNumber = "";
			if(costCenter == null) {
				throw new TaskExternalException(getResource(ctx, "CostCenterNotFound"));
			}
			BOSUuid id = costCenter.getId();
			FilterInfo costCenterFilter = new FilterInfo();
			costCenterFilter.getFilterItems().add(new FilterItemInfo("id", id));
			EntityViewInfo costCenterView = new EntityViewInfo();
			costCenterView.setFilter(costCenterFilter);
			CostCenterOrgUnitCollection costCenterOrgUnitCollection = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(costCenterView);
			if(costCenterOrgUnitCollection.size() > 0) {
				costCenter = costCenterOrgUnitCollection.get(0);
				costCenterLongNumber = costCenter.getLongNumber();
				if(!StringUtils.isEmpty(costOrg_longNumber) && !costCenterLongNumber.equals(costOrg_longNumber.replace('.', '!'))) {
					throw new TaskExternalException(getResource(ctx, "CostOrgLongNumberNotFound"));
				}
			}
			
			// �ɱ���Ŀ(���ڵ㲻�ܵ��룬ֱ���ж�isLeaf����ʱ���ţ�)
			FilterInfo costAccountFilter = new FilterInfo();
			costAccountFilter.getFilterItems().add(new FilterItemInfo("codingNumber", costAccountNumber.replace('!', '.')));
			costAccountFilter.getFilterItems().add(new FilterItemInfo("curProject", curProjectInfo.getId().toString()));
			costAccountFilter.setMaskString("#0 and #1");
			EntityViewInfo costAccountView = new EntityViewInfo();
			costAccountView.setFilter(costAccountFilter);
			CostAccountCollection costAccountCollection = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(costAccountView);
			if(costAccountCollection.size() > 0) {
				costAccount = costAccountCollection.get(0);
			} else {
				throw new TaskExternalException(getResource(ctx, "CostAccountNumberNotFound"));
			}
			
			// ��λ
			if(!StringUtils.isEmpty(fCostEntryUnit)) {
				FilterInfo measureUnitFilter = new FilterInfo();
				measureUnitFilter.getFilterItems().add(new FilterItemInfo("name", fCostEntryUnit));
				EntityViewInfo materialView = new EntityViewInfo();
				materialView.setFilter(measureUnitFilter);
				measureCollection  = MeasureUnitFactory.getLocalInstance(ctx).getMeasureUnitCollection(materialView);
				if(measureCollection.size() == 0) {
					throw new TaskExternalException(getResource(ctx, "MeasureUnitNotFound"));
				}
			}
			
			// ������Ʒ
			if(!StringUtils.isEmpty(productNumber)) {
				FilterInfo productTypeFilter = new FilterInfo();
				productTypeFilter.getFilterItems().add(new FilterItemInfo("number", productNumber.trim()));
				EntityViewInfo productTypeView = new EntityViewInfo();
				productTypeView.setFilter(productTypeFilter);
				productTypeCollection  = ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(productTypeView);
				if(productTypeCollection.size() > 0) {
					productTypeInfo = productTypeCollection.get(0);
				} else {
					throw new TaskExternalException(getResource(ctx, "ProductTypeNotFound"));
				}
			}
		} catch (BOSException e) {
				e.printStackTrace();
		}
		
		infoEntry = new CostEntryInfo();
		
		// ��֯
		costAccount.setFullOrgUnit(costCenter.castToFullOrgUnitInfo());
		// ������Ŀ
		costAccount.setCurProject(curProjectInfo);
		// �ɱ���Ŀ
		infoEntry.setCostAccount(costAccount);
		// ������
		infoEntry.setWorkload(FDCTransmissionHelper.strToBigDecimal(fCostEntryWorkload));
		// ��λ
		infoEntry.setUnit(fCostEntryUnit);
		// ����
		infoEntry.setPrice(FDCTransmissionHelper.strToBigDecimal(fCostEntrPrice));
		// Ŀ��ɱ���Ԫ��
		infoEntry.setCostAmount(FDCTransmissionHelper.strToBigDecimal(fCostEntryCostAmount));
		// ������Ʒ
		infoEntry.setProduct(productTypeInfo);
//		try {
//			// ��������?
//			BigDecimal buildArea = FDCHelper.getApportionValue(curProjectInfo.getId().toString(),
//					ApportionTypeInfo.buildAreaType, ProjectStageEnum.AIMCOST);
//			BigDecimal build = FDCTransmissionHelper.strToBigDecimal(fCostEntryCostAmount).divide(buildArea, 2, BigDecimal.ROUND_HALF_UP);
//			
//			// ���۵���?
//			BigDecimal sellArea = FDCHelper.getApportionValue(curProjectInfo.getId().toString(),
//					ApportionTypeInfo.sellAreaType, ProjectStageEnum.AIMCOST);
//			BigDecimal sell = FDCTransmissionHelper.strToBigDecimal(fCostEntryCostAmount).divide(sellArea, 2, BigDecimal.ROUND_HALF_UP);
//			
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
		// ��ע
		infoEntry.setDesc(fCostEntryDesc);
		// �仯ԭ��
		infoEntry.setChangeReason(fCostEntryChangeReason);
		
		return infoEntry;
	}
	
	/**
	 * 	�ж��û��Ƿ����
	 * @param number �û�����
	 * @param ctx
	 * @return	true/false  ����/������
	 */
	private UserInfo  isUserNumber(String number, Context ctx) {
		UserInfo user = null;
		try {
			FilterInfo userFilter = new FilterInfo();
			userFilter.getFilterItems().add(new FilterItemInfo("number",number));
			EntityViewInfo userView = new EntityViewInfo();
			userView.setFilter(userFilter);
			UserCollection userCollection;
			userCollection = UserFactory.getLocalInstance(ctx).getUserCollection(userView);
			if(userCollection.size() > 0) {
				user = userCollection.get(0);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}

}