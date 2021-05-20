/**
 * 
 */
package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ChangeAuditBillCollection;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBailInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.CopySupplierEntryInfo;
import com.kingdee.eas.fdc.contract.SupplierContentEntryInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������
 * 
 * @author �쿡
 * @version EAS7.0
 * @createDate 2011-6-13
 * @see
 */
public class ChangeSupplierEntryTransmission extends AbstractDataTransmission {

	private static String resource = "com.kingdee.eas.fdc.contract.ContractTransmissionResource";

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return ChangeSupplierEntryFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {

		ChangeSupplierEntryInfo info = new ChangeSupplierEntryInfo();
		ChangeAuditBillCollection coll = null;
		ContractBillCollection contractBillCollection = null;
		UserCollection userCollection = null;
		AdminOrgUnitCollection adminOrgUnitCollection = null;
		// String fSign = null;
		// ChangeAuditEntryInfo changeAuditEntryInfo = null;

		// ���������
		String fChangeauditNumber = (String) ((DataToken) hsData.get("FParentID_number")).data;
		// ���к�
		String fSeq = (String) ((DataToken) hsData.get("FSeq")).data;
		// ��ͬ����
		String fContractBillCodingNumber = (String) ((DataToken) hsData.get("FContractBill_codingNumber")).data;
		// ���͵�λ
		String fMainSuppName = (String) ((DataToken) hsData.get("FMainSupp_name_l2")).data;
		// ���͵�λ
		String fCopySuppName = (String) ((DataToken) hsData.get("FCopySupp$copySupp_name_l2")).data;
//		// ԭʼ��ϵ����
//		String fOriginalContactNum = (String) ((DataToken) hsData.get("FOriginalContactNum")).data;
		// ִ���������к�
		String fContentSeq = (String) ((DataToken) hsData.get("FEntrys$content_seq")).data;
		// ������
		String fCostAmount = (String) ((DataToken) hsData.get("FCostAmount")).data;
//		// �Ƿ�ȷ�ϱ�����
//		String fIsSureChangeAmt = (String) ((DataToken) hsData.get("FIsSureChangeAmt")).data;
//		// ʩ����������
//		String fConstructPrice = (String) ((DataToken) hsData.get("FConstructPrice")).data;
		// ����˵��
		String fCostDescription = (String) ((DataToken) hsData.get("FCostDescription")).data;
//		// �Ƿ����οۿλ
//		String fIsDeduct = (String) ((DataToken) hsData.get("FIsDeduct")).data;
		// ���οۿ���
//		String fDeductAmount = (String) ((DataToken) hsData.get("FDeductAmount")).data;
//		// ���㷽ʽ
//		String fBalanceType = (String) ((DataToken) hsData.get("FBalanceType")).data;
		// ����������
		String fReckonorName = (String) ((DataToken) hsData.get("FReckonor_name_l2")).data;
		// ���ι�������
		String fDutyOrgName = (String) ((DataToken) hsData.get("FDutyOrg_name_l2")).data;

		String FCurrency_name_l2 = (String) ((DataToken) hsData.get("FCurrency_name_l2")).data;
		String FExRate = (String) ((DataToken) hsData.get("FExRate")).data;
		String FOriCostAmount = (String) ((DataToken) hsData.get("FOriCostAmount")).data;
		// ���������
		if (StringUtils.isEmpty(fChangeauditNumber)) {
			throw new TaskExternalException(getResource(ctx,
					"Import_ChangeAuditNumberIsNull"));
		}
		if (fChangeauditNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx,
					"Import_ChangeAuditNumberIsTooLong"));
		}
		try {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id",
						"select fid from t_con_changeauditbill where fnumber='"
								+ fChangeauditNumber + "'", CompareType.INNER));
		view.setFilter(filter);

			coll = ChangeAuditBillFactory.getLocalInstance(ctx)
					.getChangeAuditBillCollection(view);
			if (coll.size() > 0) {
				ChangeAuditBillInfo changeAuditBill = coll.get(0);
				// changeAuditEntryInfo = changeAuditBill.getEntrys().get(0);
				info.setParent(changeAuditBill);
			} else {
				throw new TaskExternalException(getResource(ctx,
						"Import_ChangeAuditNumberIsNotExsit"));
			}
		} catch (BOSException e) {
			throw new RuntimeException(e.getMessage(), e.getCause());
		}

		// ���к�
		if (StringUtils.isEmpty(fSeq)) {
			throw new TaskExternalException(
					getResource(ctx, "Import_SeqIsNull"));
		}
		if (fSeq.length() > 4) {
			throw new TaskExternalException(getResource(ctx,
					"Import_SeqIsTooLong"));
		}
		info.setSeq(Integer.parseInt(fSeq));

		// ���͵�λ
		if (StringUtils.isEmpty(fMainSuppName)) {
			throw new TaskExternalException(getResource(ctx,
					"Import_fMainSuppNameIsNull"));
		}
		if (fMainSuppName.length() > 40) {
			fMainSuppName = (fMainSuppName.substring(0, 40));
		}
		try {
			FilterInfo supplierFilter = new FilterInfo();
			supplierFilter.getFilterItems().add(
					new FilterItemInfo("name", fMainSuppName));
			EntityViewInfo supplierView = new EntityViewInfo();
			supplierView.setFilter(supplierFilter);
			SupplierCollection supplierColl = SupplierFactory.getLocalInstance(
					ctx).getSupplierCollection(supplierView);
			if (supplierColl != null && supplierColl.size() > 0) {
				SupplierInfo supplierInfo = supplierColl.get(0);
				info.setMainSupp(supplierInfo);
			} else {
				throw new TaskExternalException(getResource(ctx,
						"Import_fMainSuppNameIsNotExsit"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

		// ���͵�λ TODO
		try {
//			String fCopySuppNames = fCopySuppName.replaceAll(",", "','") ;
//			String queryStr = "('"+fCopySuppNames+ "')";
			FilterInfo supplierFilter = new FilterInfo();
			supplierFilter.getFilterItems().add(new FilterItemInfo("name",fCopySuppName,CompareType.INCLUDE));
			EntityViewInfo supplierView = new EntityViewInfo();
			supplierView.setFilter(supplierFilter);
			SupplierCollection csec = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(supplierView);
			if (csec.size() < 0) {
				throw new TaskExternalException(getResource(ctx,
				"fCopySuppNameIsNotExsit"));
			}
			for(int i=0;i<csec.size()-1;i++) {
				SupplierInfo csdwInfo = csec.get(i);
				CopySupplierEntryInfo cseInfo = new CopySupplierEntryInfo();
				cseInfo.setCopySupp(csdwInfo);
				int seq = info.getCopySupp().size()+1;
				cseInfo.setSeq(seq);
				cseInfo.setParent(info);
				info.getCopySupp().add(cseInfo);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

//		// ԭʼ��ϵ����
//		info.setOriginalContactNum(fOriginalContactNum);

		// ִ���������к� TODO:����������кţ�����ʾ���Ǳ������
		if (StringUtils.isEmpty(fContentSeq)) {
			throw new TaskExternalException(getResource(ctx,
					"fContentSeqIsNull"));
		}
		
//		 String a [] =
//		 {"A","B","C","D","E","F","G","H","I","J","K","L","M","N"
//		 ,"O","P","Q","R","S","T","U","V","W","X","Y","Z"};
//		 for(int i=1; i<27;i++) {
//		 if(Integer.parseInt(fContentSeq) == i) {
//		 fSign = a[i-1];
//		 }
//		 }
//		 String changeContent = changeAuditEntryInfo.getChangeContent();

		// ������
		if (StringUtils.isEmpty(fCostAmount)) {
			throw new TaskExternalException(getResource(ctx,
					"fCostAmountIsNull"));
		}
		if (fCostAmount.matches("^\\d+(\\.\\d+)?$")) {
			if (!fCostAmount.matches("^([1-9]\\d{0,16}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,16})||0$")) {
				// ������¼�벻�Ϸ�
				throw new TaskExternalException(getResource(ctx, "Import_fCostAmountIsWrong"));
			}
			info.setCostAmount(new BigDecimal(fCostAmount));
		}else {
			throw new TaskExternalException(getResource(ctx, "Import_fCostAmountIsNotNumber"));
		}
		

//		// ���οۿ���
//		if(!StringUtils.isEmpty(fDeductAmount)) {
//			if(fDeductAmount.matches("^\\d+(\\.\\d+)?$")) {
//				if (!fDeductAmount.matches("^([1-9]\\d{0,16}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,16})||0$")) {
//					// ���οۿ���¼�벻�Ϸ�
//					throw new TaskExternalException(getResource(ctx, "Import_fDeductAmountIsWrong"));
//				}
//				info.setDeductAmount(new BigDecimal(fDeductAmount));
//			}else {
//				throw new TaskExternalException(getResource(ctx, "Import_fDeductAmountIsNotNumber"));
//			}
//		}
		

		// ʩ����������
//		if (fConstructPrice.trim().length() > 19) {
//			throw new TaskExternalException(getResource(ctx,
//					"fConstructPriceIsOverLength"));
//		}
//		if(!StringUtils.isEmpty(fConstructPrice)) {
//			if (fConstructPrice.matches("^\\d+(\\.\\d+)?$")) {
//				if (!fConstructPrice.matches("^([1-9]\\d{0,16}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,16})||0$")) {
//					// ʩ����������¼�벻�Ϸ�
//					throw new TaskExternalException(getResource(ctx, "Import_fConstructPriceIsWrong"));
//				}
//				info.setConstructPrice(new BigDecimal(fConstructPrice));
//			}else {
//				throw new TaskExternalException(getResource(ctx, "Import_fConstructPriceIsNotNumber"));
//			}
//		}
		

//		// �Ƿ�ȷ�ϱ�����
//		if (fIsSureChangeAmt.equals(getResource(ctx, "Yes"))) {
//			info.setIsSureChangeAmt(true);
//		} else {
//			info.setIsSureChangeAmt(false);
//		}

		// ����˵��
		if (fCostDescription.length() > 40) {
			throw new TaskExternalException(getResource(ctx,
					"fCostDescriptionIsTooLong"));
		}
		info.setCostDescription(fCostDescription);

		// �Ƿ����οۿλ
//		if (fIsDeduct.trim().equals(getResource(ctx, "Yes"))) {
//			info.setIsDeduct(true);
//		} else {
//			info.setIsDeduct(false);
//		}

		// ���㷽ʽ
//		if (fBalanceType.length() > 40) {
//			throw new TaskExternalException(getResource(ctx,
//					"fBalanceTypeIsTooLong"));
//		}
//		info.setBalanceType(fBalanceType);

		// ����������
		try {
			FilterInfo userFilter = new FilterInfo();
			userFilter.getFilterItems().add(
					new FilterItemInfo("name", fReckonorName));
			EntityViewInfo userView = new EntityViewInfo();
			userView.setFilter(userFilter);
			userCollection = UserFactory.getLocalInstance(ctx)
					.getUserCollection(userView);
			if (userCollection.size() > 0) {
				UserInfo userInfo = userCollection.get(0);
				info.setReckonor(userInfo);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		// ���ι�������
		if (StringUtils.isEmpty(fDutyOrgName)) {
			throw new TaskExternalException(getResource(ctx,
					"fDutyOrgNameIsNull"));
		}
		try {
			FilterInfo adminOrgUnitFilter = new FilterInfo();
			EntityViewInfo adminOrgUnitView = new EntityViewInfo();
			adminOrgUnitFilter.getFilterItems().add(
					new FilterItemInfo("id",
							"select fid from T_ORG_Admin where fname_l2='"
									+ fDutyOrgName + "'", CompareType.INNER));
			adminOrgUnitView.setFilter(adminOrgUnitFilter);
			adminOrgUnitCollection = AdminOrgUnitFactory.getLocalInstance(ctx)
					.getAdminOrgUnitCollection(adminOrgUnitView);
			if (adminOrgUnitCollection.size() > 0) {
				AdminOrgUnitInfo adminOrgUnitInfo = adminOrgUnitCollection
						.get(0);
				info.setDutyOrg(adminOrgUnitInfo);
			} else {
				throw new TaskExternalException(getResource(ctx,
						"fDutyOrgNameIsNotExsit"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

		// ��ͬ����
		if (StringUtils.isEmpty(fContractBillCodingNumber)) {
			throw new TaskExternalException(getResource(ctx,
					"Import_fContractBillCodingNumberIsNull"));
		}
		if (fContractBillCodingNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx,
					"Import_fContractBillCodingNumberIsTooLong"));
		}
		try {
			FilterInfo contractBillFilter = new FilterInfo();
			EntityViewInfo contractBillView = new EntityViewInfo();
			contractBillFilter.getFilterItems().add(
					new FilterItemInfo("id",
							"select fid from t_con_contractbill where fnumber='"
									+ fContractBillCodingNumber + "'",
							CompareType.INNER));
			contractBillView.setFilter(contractBillFilter);
			contractBillCollection = ContractBillFactory.getLocalInstance(ctx)
					.getContractBillCollection(contractBillView);
			if (contractBillCollection.size() > 0) {
				ContractBillInfo contractBillInfo = contractBillCollection.get(0);
//				String a = contractBillInfo.getExRate().toString();
//				info.setExRate(FDCNumberHelper.toBigDecimal(a, 2));
				info.setContractBill(contractBillInfo);
			} else {
				throw new TaskExternalException(getResource(ctx,
						"Import_fContractBillCodingNumberIsNotExsit"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		if (StringUtils.isEmpty(FOriCostAmount)) {
			throw new TaskExternalException("���Խ��ԭ�Ҳ���Ϊ�գ�");
		}
		if (StringUtils.isEmpty(FExRate)) {
			throw new TaskExternalException("���ʲ���Ϊ�գ�");
		}
		if (StringUtils.isEmpty(FCurrency_name_l2)) {
			throw new TaskExternalException("�ұ���Ϊ�գ�");
		}
		if(!StringUtils.isEmpty(FOriCostAmount)) {
			if(FOriCostAmount.matches("^\\d+(\\.\\d+)?$")) {
				if (!FOriCostAmount.matches("^([1-9]\\d{0,16}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,16})||0$")) {
					// ���οۿ���¼�벻�Ϸ�
					throw new TaskExternalException("���Խ��ԭ��¼�벻�Ϸ���");
				}
				info.setOriCostAmount(new BigDecimal(FOriCostAmount));
			}else {
				throw new TaskExternalException("������ԭ�Ҳ������֣�");
			}
		}
		if(!StringUtils.isEmpty(FExRate)) {
			if(FExRate.matches("^\\d+(\\.\\d+)?$")) {
				if (!FExRate.matches("^([1-9]\\d{0,16}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,16})||0$")) {
					// ���οۿ���¼�벻�Ϸ�
					throw new TaskExternalException("����¼�벻�Ϸ���");
				}
				info.setExRate(new BigDecimal(FExRate));
			}else {
				throw new TaskExternalException("���ʲ������֣�");
			}
		}
		try {
			CurrencyCollection col = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("select * from where name='"+FCurrency_name_l2+"'");
			if (col.size() > 0) {
				info.setCurrency(col.get(0));
			} else {
				throw new TaskExternalException("�ұ���ϵͳ�в����ڣ�");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		try {
			ChangeAuditEntryCollection col = ChangeAuditEntryFactory.getLocalInstance(ctx).getChangeAuditEntryCollection("select * from where parent.id='"+info.getParent().getId().toString()+"' and seq="+fContentSeq);
			if(col.size()>0){
				SupplierContentEntryInfo entry=new SupplierContentEntryInfo();
				entry.setContent(col.get(0));
				info.getEntrys().add(entry);
			}else{
				throw new TaskExternalException("ִ���������к���ϵͳ�в����ڣ�");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * �õ���Դ�ļ�
	 * 
	 * @author �쿡
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		if (coreBaseInfo == null || coreBaseInfo instanceof ChangeSupplierEntryInfo == false) {
			return;
		}	
		try {
			getController(ctx).save(coreBaseInfo);
		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex.getCause());
		}
	}
}
