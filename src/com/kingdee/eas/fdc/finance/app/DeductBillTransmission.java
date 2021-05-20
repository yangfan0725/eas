/**
 * 
 */
package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.DeductTypeFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.finance.DeductBillCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fdc.finance.DeductBillFactory;
import com.kingdee.eas.fdc.finance.DeductBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		
 *		
 * @author		dingwen_yong
 * @version		EAS7.0		
 * @createDate	2011-6-14	 
 * @see						
 */
public class DeductBillTransmission extends AbstractDataTransmission {
	
	// ��Դ�ļ�
	private static String resource = "com.kingdee.eas.fdc.finance.PaymentSplitResource";
	
	/** �ۿ */
	private DeductBillInfo info = null;
	/** �ۿ��¼ */
	private DeductBillEntryInfo entryInfo = null;
	/** ������Ŀ */
	private CurProjectInfo curProject = null;
	/** �Ƶ��� */
	private UserInfo createUser = null;
	/** ������ */
	private UserInfo auditUser = null;
	/** ��ͬ */
	private ContractBillInfo contractBill = null;
	/** �ۿλ */
	private SupplierInfo supplier = null;
	/** �ۿ����� */
	private DeductTypeInfo deductType = null;
	/** �ұ� */
	private CurrencyInfo currency = null;
	private CurrencyCollection currencyCollection = null;
	
	/**
	 * @description		
	 * @author			dingwen_yong		
	 * @createDate		2011-6-14
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	protected ICoreBase getController(Context context) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = DeductBillFactory.getLocalInstance(context);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * @description		
	 * @author			dingwen_yong		
	 * @createDate		2011-6-14
	 * @param 			hashtable
	 * @param 			context	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see						
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
		try {
			info = transmitHead(hashtable, context);
			if (info == null) {
				return null;
			}
			DeductBillEntryInfo entry = transmitEntry(hashtable, context);
            entry.setParent(info);
            int seq = info.getEntrys().size() + 1;
            entry.setSeq(seq);
            info.getEntrys().add(entry);
		} catch (TaskExternalException e) {
			info = null;
			throw e;
		}
		return info;
	}
	
	/**
	 * 
	 * @description		�ۿ��ͷ
	 * @author			dingwen_yong		
	 * @createDate		2011-6-14
	 * @param 			hashtable
	 * @param 			context
	 * @return
	 * @throws 			TaskExternalException DeductBillInfo
	 * @version			EAS1.0
	 * @see
	 */
	private DeductBillInfo transmitHead(Hashtable hashtable, Context context) throws TaskExternalException {
		/**
		 * ��ȡExcel�ı��ж�Ӧ��ֵ
		 */
		// 1.��֯���� 
		String fOrgUnitNumber = ((String) ((DataToken) hashtable.get("FOrgUnit_number")).data).trim();
		// 2.������Ŀ����
		String fCurProjectLongNumber = ((String) ((DataToken) hashtable.get("FCurProject_longNumber")).data).trim();
		// 3.����
		String fNumber = ((String) ((DataToken) hashtable.get("FNumber")).data).trim();
		// 4.����
		String fName = ((String) ((DataToken) hashtable.get("FName")).data).trim();
		// 5.״̬
		String fState = ((String) ((DataToken) hashtable.get("FState")).data).trim();
		// 6.�Ƶ��˱���
		String fCreatorNameL2 = ((String) ((DataToken) hashtable.get("FCreator_name_l2")).data).trim();
		// 7.�Ƶ�ʱ��
		String fCreateTime = ((String) ((DataToken) hashtable.get("FCreateTime")).data).trim();
		// 8.�����˱���
		String fAuditorNameL2 = ((String) ((DataToken) hashtable.get("FAuditor_name_l2")).data).trim();
		// 9.���ʱ��
		String fAuditTime = ((String) ((DataToken) hashtable.get("FAuditTime")).data).trim();

		/**
		 * �жϲ����ֶ��Ƿ�Ϊ��
		 */
		if (!StringUtils.isEmpty(fOrgUnitNumber)) {
			if (fOrgUnitNumber.length() > 40) {
				// "��֯�����ֶγ��Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fOrgUnitNumber"));
			}
		}
		if (!StringUtils.isEmpty(fCurProjectLongNumber)) {
			if (fCurProjectLongNumber.length() > 40) {
				// "������Ŀ�����ֶγ��Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fCurProjectLongNumber"));
			}
		} else {
			// "������Ŀ���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fCurProjectLongNumberNotNull"));
		}
		if (!StringUtils.isEmpty(fNumber)) {
			if (fNumber.length() > 80) {
				// "�����ֶγ��Ȳ��ܳ���80��"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fNumber"));
			}
		} else {
			// "���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fNumberNotNull"));
		}
		if (!StringUtils.isEmpty(fName)) {
			if (fName.length() > 80) {
				// "�����ֶγ��Ȳ��ܳ���80��"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fName"));
			}
		} else {
			// "���Ʋ���Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fNameNotNull"));
		}
		
		/**
		 * ��ֵ���������
		 */
		try {
			info = this.getDeductBillInfoFromNumber(fNumber,context);
			if (info != null) {
				return info;
			} else {
				info = new DeductBillInfo();
			}
			// ������Ŀ����
			FilterInfo filterCurProject = new FilterInfo();
			filterCurProject.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo viewCurProject = new EntityViewInfo();
			viewCurProject.setFilter(filterCurProject);
			CurProjectCollection curProjectBillColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(viewCurProject);
			if (curProjectBillColl.size() > 0){
				curProject = curProjectBillColl.get(0);
				info.setCurProject(curProject);
				// ��֯������
				CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); //  ������Ŀ��Ӧ�ɱ�������֯
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("id", costCenterOrgUnit.getId().toString()));
				EntityViewInfo view1 = new EntityViewInfo();
				view1.setFilter(filter1);
				CostCenterOrgUnitCollection ccouc = CostCenterOrgUnitFactory.getLocalInstance(context).getCostCenterOrgUnitCollection(view1);
				if (!StringUtils.isEmpty(fOrgUnitNumber) && !fOrgUnitNumber.replace('.', '!').equals(ccouc.get(0).getLongNumber())) {
					// "��֯�������ڹ�����Ŀ����Ӧ�ĳɱ����Ĳ�����!"
 					throw new TaskExternalException(getResource(context, "Import_zzbmbcz"));
					// ԭת����֯���󷽷� <�ݲ��Ƽ�ʹ��> (FullOrgUnitInfo)FullOrgUnitInfo.class.cast(costCenterOrgUnit)
				}
				info.setOrgUnit(ccouc.get(0).castToFullOrgUnitInfo());
			} else {
				// 1 "������Ŀ����Ϊ��,��ù�����Ŀ���� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fCurProjectLongNumber1") + fCurProjectLongNumber + getResource(context, "DeductBill_Import_NOTNULL"));
			}
			// ��Ҫ�жϱ�������Ʋ����ظ�
			FilterInfo filter5 = new FilterInfo();
			filter5.getFilterItems().add(new FilterItemInfo("number", fNumber));
			EntityViewInfo view5 = new EntityViewInfo();
			view5.setFilter(filter5);
			DeductBillCollection dbc = DeductBillFactory.getLocalInstance(context).getDeductBillCollection(view5);
			if (dbc.size() > 0) {
				// �ۿ�����ظ���
				throw new TaskExternalException(getResource(context, "DeductBill_Import_bmcf"));
			}
			filter5 = new FilterInfo();
			filter5.getFilterItems().add(new FilterItemInfo("name", fName));
			view5 = new EntityViewInfo();
			view5.setFilter(filter5);
			dbc = DeductBillFactory.getLocalInstance(context).getDeductBillCollection(view5);
			if (dbc.size() > 0) {
				// �ۿ�����ظ���
				throw new TaskExternalException(getResource(context, "DeductBill_Import_mccf"));
			}
			// ����  
			info.setNumber(fNumber);
			// ����
			info.setName(fName);
			// ״̬
			if (fState.equals(getResource(context, "SAVE"))) {
				info.setState(FDCBillStateEnum.SAVED);
			} else if (fState.equals(getResource(context, "SUBMITTED"))) {
				info.setState(FDCBillStateEnum.SUBMITTED);
			} else if (fState.equals(getResource(context, "AUDITTED")) || StringUtils.isEmpty(fState)) {
				info.setState(FDCBillStateEnum.AUDITTED);
				// ������ <"״̬"�ֶ������ĵ��в�����,��Ҫ�޸������ĵ���Ӹ��ֶ�>
				if (!StringUtils.isEmpty(fAuditorNameL2)) {
					FilterInfo filterauditUser = new FilterInfo();
					filterauditUser.getFilterItems().add(new FilterItemInfo("number", fAuditorNameL2));
					EntityViewInfo viewauditUser = new EntityViewInfo();
					viewauditUser.setFilter(filterauditUser);
					UserCollection auditUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewauditUser);
					if (auditUserColl.size() > 0){
						auditUser = auditUserColl.get(0);
						info.setAuditor(auditUser);
					} else {
						// 1 "�����˱��� "
						// 2 " ��ϵͳ�в����ڣ�"
						throw new TaskExternalException(getResource(context, "DeductBill_Import_fAuditorNameL2") + fAuditorNameL2 + getResource(context, "DeductBill_Import_NOTNULL"));
					}
				} else {
					// "��������״̬�µ��������ֶβ���Ϊ�գ�"
					throw new TaskExternalException(getResource(context, "DeductBill_Import_fAuditorNameL2NotNull"));
				}
				//����ʱ��
				if (!StringUtils.isEmpty(fAuditTime)) {
					info.setAuditTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "PaymentSplitWithoutTxtCon_Import_spsjcw")).getTime()));
				} else {
					// "��������״̬�µ����������ֶβ���Ϊ�գ�"
					throw new TaskExternalException(getResource(context, "DeductBill_Import_fAuditTime"));
				}
			} else {
				// 1 "״̬ "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fState") + fState + getResource(context, "DeductBill_Import_NOTNULL"));
			}
			// �Ƶ��˱���
			if (!StringUtils.isEmpty(fCreatorNameL2)) {
				FilterInfo filtercreateUser = new FilterInfo();
				filtercreateUser.getFilterItems().add(new FilterItemInfo("number", fCreatorNameL2));
				EntityViewInfo viewcreateUser = new EntityViewInfo();
				viewcreateUser.setFilter(filtercreateUser);
				UserCollection createUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewcreateUser);
				if (createUserColl.size() > 0){
					createUser = createUserColl.get(0);
					info.setCreator(createUser);
				}else{
					// 1 "�Ƶ��˱���Ϊ��,�������Ƶ��˱��� "
					// 2 " ��ϵͳ�в����ڣ�"
					throw new TaskExternalException(getResource(context, "DeductBill_Import_fCreatorNameL21") + fCreatorNameL2 + getResource(context, "DeductBill_Import_NOTNULL"));
				}
			}
			// �Ƶ�ʱ�� // �Ƶ�ʱ��¼�벻��ȷ ��ʽΪ��YYYY-MM-DD
			if (!StringUtils.isEmpty(fCreateTime)) {
				info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "PaymentSplitWithoutTxtCon_Import_zdsjcw")).getTime()));
			}
			info.setConTypeBefContr(false);
			info.setSourceType(SourceTypeEnum.IMP);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}
	
	/**
	 * 
	 * @description		�ۿ��¼
	 * @author			dingwen_yong		
	 * @createDate		2011-6-14
	 * @param 			hashtable
	 * @param 			context
	 * @return
	 * @throws 			TaskExternalException DeductBillEntryInfo
	 * @version			EAS1.0
	 * @see
	 */
	private DeductBillEntryInfo transmitEntry(Hashtable hashtable, Context context) throws TaskExternalException {
		
		/**
		 * �õ�Excel�з�¼��Ϣ
		 */
		// 10.��ͬ����
		String fEntrysContractId = ((String) ((DataToken) hashtable.get("FEntrys_contractId")).data).trim();
		// 11.�ۿλ
		String fDeductUnitNameL2 = ((String) ((DataToken) hashtable.get("FDeductUnit_name_l2")).data).trim();
		// 12.�ۿ�����
		String fDeductTypeNameL2 = ((String) ((DataToken) hashtable.get("FDeductType_name_l2")).data).trim();
		// 13.�ۿ�����
		String fEntrysDeductItem = ((String) ((DataToken) hashtable.get("FEntrys_deductItem")).data).trim();
		// 14.�ұ����
		String fCurrencyIDNumber = ((String) ((DataToken) hashtable.get("FCurrencyID_number")).data).trim();
		// 15.�ۿ���
		String fEntrysDeductAmt = ((String) ((DataToken) hashtable.get("FEntrys_deductAmt")).data).trim();
		// 16.�ۿ�����
		String fEntrysDeductDate = ((String) ((DataToken) hashtable.get("FEntrys_deductDate")).data).trim();
		// 17.��ע
		String fEntrysRemark = ((String) ((DataToken) hashtable.get("FEntrys_remark")).data).trim();
		
		/**
		 * �жϲ����ֶβ���Ϊ��
		 */
		if (!StringUtils.isEmpty(fEntrysContractId)) {
			if (fEntrysContractId.length() > 40) {
				// "��ͬ��������ֶγ��Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysContractId"));
			}
		} else {
			// "��ͬ���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysContractIdNotNull"));
		}
		if (!StringUtils.isEmpty(fDeductUnitNameL2)) {
			if (fDeductUnitNameL2.length() > 40) {
				// "�ۿλ�ֶγ��Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fDeductUnitNameL2"));
			}
		} else {
			// "�ۿλ����Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fDeductUnitNameL2NotNull"));
		}
		if (!StringUtils.isEmpty(fDeductTypeNameL2)) {
			if (fDeductTypeNameL2.length() > 40) {
				// "�ۿ������ֶγ��Ȳ��ܳ���40��"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fDeductTypeNameL2"));
			}
		} else {
			// "�ۿ����Ͳ���Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fDeductTypeNameL2NotNull"));
		}
		if (!StringUtils.isEmpty(fEntrysDeductItem) && fEntrysDeductItem.length() > 40) {
			// "�ۿ������ֶγ��Ȳ��ܳ���40��"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysDeductItem"));
		}
		if (!StringUtils.isEmpty(fCurrencyIDNumber) && fCurrencyIDNumber.length() > 40) {
			// "�ұ�����ֶγ��Ȳ��ܳ���40��"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fCurrencyIDNumber"));
		}
		if (!StringUtils.isEmpty(fEntrysDeductAmt)) {
			if (!fEntrysDeductAmt.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "�ۿ���¼�벻�Ϸ���"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysDeductAmt"));
			}
		} else {
			// "�ۿ����Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysDeductAmtNotNull"));
		}
		if (StringUtils.isEmpty(fEntrysDeductDate)) {
			// "�ۿ����ڲ���Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysDeductDate"));
		}
		if (!StringUtils.isEmpty(fEntrysRemark) && fEntrysRemark.length() > 40) {
			// "��ע�ֶγ��Ȳ��ܳ���40��"
			throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysRemark"));
		}
		
		/**
		 * ��¼��Ϣ��ֵ
		 */
		try {
			// �Ѿ�ȷ�Ϸ�¼�����ظ����룬��˲����жϷ�¼�ظ�
			entryInfo = new DeductBillEntryInfo();
			// ��ͬ����
			FilterInfo filtercontractBill = new FilterInfo();
			filtercontractBill.getFilterItems().add(new FilterItemInfo("number", fEntrysContractId));
			EntityViewInfo viewcontractBill = new EntityViewInfo();
			viewcontractBill.setFilter(filtercontractBill);
			ContractBillCollection contractBillColl = ContractBillFactory.getLocalInstance(context).getContractBillCollection(viewcontractBill);
			if (contractBillColl.size() > 0){
				contractBill = contractBillColl.get(0);
				entryInfo.setContractId(contractBill.getId().toString());  // ���ú�ͬ����
			}else{
				// 1 "��ͬ����Ϊ��,���߸ú�ͬ���� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fEntrysContractId1") + getResource(context, "DeductBill_Import_NOTNULL"));
			}
			// �ۿλ
			FilterInfo filtersupplier = new FilterInfo();
			filtersupplier.getFilterItems().add(new FilterItemInfo("name", fDeductUnitNameL2));
			EntityViewInfo viewsupplier = new EntityViewInfo();
			viewsupplier.setFilter(filtersupplier);
			SupplierCollection supplierColl = SupplierFactory.getLocalInstance(context).getSupplierCollection(viewsupplier);
			if (supplierColl.size() > 0){
				supplier = supplierColl.get(0);
				entryInfo.setDeductUnit(supplier);	// ���ÿۿλ
			}else{
				// 1 "�ÿۿλ "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fDeductUnitNameL21")  + getResource(context, "DeductBill_Import_NOTNULL"));
			}
			// �ۿ�����
			FilterInfo filterdeductType = new FilterInfo();
			filterdeductType.getFilterItems().add(new FilterItemInfo("name", fDeductTypeNameL2));
			EntityViewInfo viewdeductType = new EntityViewInfo();
			viewdeductType.setFilter(filterdeductType);
			DeductTypeCollection deductTypeColl = DeductTypeFactory.getLocalInstance(context).getDeductTypeCollection(viewdeductType);
			if (deductTypeColl.size() > 0){
				deductType = deductTypeColl.get(0);
				entryInfo.setDeductType(deductType);	// ���ÿۿ�����
			}else{
				// 1 "�ÿۿ����� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fDeductTypeNameL21")+ getResource(context, "DeductBill_Import_NOTNULL"));
			}
			
			// �ۿ�����
			entryInfo.setDeductItem(fEntrysDeductItem);
			// �ұ����  �ۿ������ֱ�Ӵ�ű��ֵ��ֶΡ����ֻ��Ҫ�жϱ��ֱ����Ƿ����
//			contractBill.getCurrency();	// �ұ���Դ�ں�ͬ
			FilterInfo filter = new FilterInfo();
			EntityViewInfo view = new EntityViewInfo();
			if (StringUtils.isEmpty(fCurrencyIDNumber)) {
				// ����ұ��������Ϊ��,������Ĭ��ֵ
				filter.getFilterItems().add(new FilterItemInfo("number", "RMB"));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("number", fCurrencyIDNumber));
			}
			view.setFilter(filter);
			currencyCollection = CurrencyFactory.getLocalInstance(context).getCurrencyCollection(view);
			if (!(currencyCollection.size() > 0)) {
				// 1 "�ñұ����"
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fCurrencyIDNumber1") + getResource(context, "DeductBill_Import_NOTNULL"));
			}
			// �ۿ���
			entryInfo.setDeductAmt(new BigDecimal(fEntrysDeductAmt));
			// �ۿ����� // �ۿ�����¼�벻��ȷ ��ʽΪ��YYYY-MM-DD
			entryInfo.setDeductDate(new Timestamp(FDCTransmissionHelper.checkDateFormat(fEntrysDeductDate, getResource(context, "DeductBill_Import_kkrqcw")).getTime()));
			// ��ע
			entryInfo.setRemark(fEntrysRemark);
			entryInfo.setHasApplied(false);
			entryInfo.setDeductOriginalAmt(new BigDecimal(fEntrysDeductAmt));
			entryInfo.setExRate(contractBill.getExRate()); // ������ģ����û�и����ֶΣ�������Դ�ں�ͬ��������û���Ϊ��ͬ����
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return entryInfo;
	}
	
	
	/**
	 * ����number��ȡid�����û���򷵻�null
	 * @param number
	 * @param ctx
	 * @return
	 * @throws TaskExternalException
	 * @author 
	 * @throws EASBizException 
	 */
	private DeductBillInfo getDeductBillInfoFromNumber(String number, Context ctx) throws TaskExternalException{
		try {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number,CompareType.EQUALS));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			DeductBillCollection deductBillCollection = DeductBillFactory.getLocalInstance(ctx).getDeductBillCollection(view);

			if (deductBillCollection.size() > 0){
				DeductBillInfo info = deductBillCollection.get(0);
			
				if (info != null) {
					return info;
				}
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		return null;
	}


	/**
	 * �õ���Դ�ļ�
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
	
}
