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
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBaseDataCollection;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
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
 * @author		�쿡
 * @version		EAS7.0		
 * @createDate	2011-6-9	 
 * @see						
 */
public class PaymentSplitWithoutTxtConTransmission extends AbstractDataTransmission {
	
	// ��Դ�ļ�
	private static String resource = "com.kingdee.eas.fdc.finance.PaymentSplitResource";

	// ���빤����
	
	/** ���ı���ͬ������ */
	private PaymentSplitInfo info = null;
	/** ���ı���ͬ�����ַ�¼ */
	private PaymentSplitEntryInfo entryInfo = null;
	/** ������Ŀ */
	private CurProjectInfo curProject = null;
	/** ��ͬ */
	private ContractWithoutTextInfo contractWithoutText = null;
	/** ��� */
	private PaymentBillInfo paymentBill = null;
	/** ����� */
	private UserInfo createUser = null;
	/** ������ */
	private UserInfo auditUser = null;
	/** �ɱ���Ŀ */
	private CostAccountInfo costAccount = null;
	/** ������Ʒ<ֱ�ӹ���> */
	private ProductTypeInfo productType = null;
	
	/**
	 * @description		
	 * @author			�쿡		
	 * @createDate		2011-6-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#getController(com.kingdee.bos.Context)					
	 */
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = PaymentSplitFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * @description		
	 * @author			�쿡		
	 * @createDate		2011-6-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)					
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
		try {
			info = transmitHead(hashtable, context);

			if (info == null) {
				return null;
			}
			PaymentSplitEntryInfo entry = transmitEntry(hashtable, context);
			entry.setParent(info);
			int seq = info.getEntrys().size() + 1;
			entry.setSeq(seq);
			entry.setIndex(seq);
			info.getEntrys().add(entry);
		} catch (TaskExternalException e) {
			info = null;
			throw e;
		}
		return info;
	}

	/**
	 * 
	 * @description		���ı���ͬ�����ֵ�ͷ 
	 * @author			dingwen_yong		
	 * @createDate		2011-6-14
	 * @param 			hashtable
	 * @param 			context
	 * @return
	 * @throws 			TaskExternalException PaymentSplitInfo
	 * @version			EAS1.0
	 * @see
	 */
	private PaymentSplitInfo transmitHead(Hashtable hashtable, Context context) throws TaskExternalException {

		// 1.��֯������
		String fCostCenterNumber  = ((String) ((DataToken)hashtable.get("FCostCenter_number")).data).trim();
		// 2.������Ŀ����
		String fCurProjectLongNumber  = ((String) ((DataToken)hashtable.get("FCurProject_longNumber")).data).trim();
		// 3.������Ŀ����
		String fCurProjectNameL2 = ((String) ((DataToken)hashtable.get("FCurProject_name_l2")).data).trim();
		// 4.��ͬ����
		String fConWithoutTextNumber = ((String) ((DataToken)hashtable.get("FConWithoutText_number")).data).trim();
		// 5.��ͬ����
		String fConWithoutTextName  = ((String) ((DataToken)hashtable.get("FConWithoutText_name")).data).trim();
		// 6.�������
		String fPaymentBillNumber  = ((String) ((DataToken)hashtable.get("FPaymentBill_number")).data).trim();
		// 7.������
		String fPaymentBillAmount = ((String) ((DataToken)hashtable.get("FPaymentBill_amount")).data).trim();
		// 12.�����ɱ����
		String fEntrysPayedAmt = ((String) ((DataToken)hashtable.get("FEntrys_payedAmt")).data).trim();
		// 14.�����
		String fCreatorNameL2  = ((String) ((DataToken)hashtable.get("FCreator_name_l2")).data).trim();
		// 15.���ʱ��
		String fCreateTime  = ((String) ((DataToken)hashtable.get("FCreateTime")).data).trim();
		// 16.������
		String fAuditorNameL2 = ((String) ((DataToken)hashtable.get("FAuditor_name_l2")).data).trim();
		// 17.����ʱ��
		String fAuditTime = ((String) ((DataToken)hashtable.get("FAuditTime")).data).trim();
		
		/**
		 * �ж��Ƿ�Ϊ��
		 */
		if (StringUtils.isEmpty(fCurProjectLongNumber)) {
			// "������Ŀ���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fCurProjectLongNumber"));
		}
		if (StringUtils.isEmpty(fConWithoutTextNumber)) {
			// "��ͬ���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fConWithoutTextNumber"));
		}
		if (StringUtils.isEmpty(fPaymentBillNumber)) {
			// "������벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fPaymentBillNumber"));
		}
		if (!StringUtils.isEmpty(fPaymentBillAmount)) {
			if (!fPaymentBillAmount.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "��������Ӧ��¼�������ͣ�"
				throw new TaskExternalException(getResource(context, "fkjeyglrszx"));
			}
		}
		if (!StringUtils.isEmpty(fEntrysPayedAmt)) {
			if(!fEntrysPayedAmt.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")){
				// "�����ɱ����Ӧ��¼�������ͣ�"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysPayedAmt"));
			}
		} else {
			// "�����ɱ�����Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysPayedAmtNotNull"));
		}
		if (StringUtils.isEmpty(fCreatorNameL2)) {
			// "����˱��벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fCreatorNameL2"));
		}
		if (StringUtils.isEmpty(fCreateTime)) {
			// "���ʱ�䲻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fCreateTime"));
		}
		if (StringUtils.isEmpty(fAuditorNameL2)) {
			// "�����˱��벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fAuditorNameL2"));
		}
		if (StringUtils.isEmpty(fAuditTime)) {
			// "����ʱ�䲻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fAuditTime"));
		}
		
		/**
		 * ��ֵ��������� 
		 */
		try {  
			// �������
			FilterInfo filterpaymentBill = new FilterInfo();
			filterpaymentBill.getFilterItems().add(new FilterItemInfo("number", fPaymentBillNumber));
			EntityViewInfo viewpaymentBill = new EntityViewInfo();
			viewpaymentBill.setFilter(filterpaymentBill);
			PaymentBillCollection paymentBillColl = PaymentBillFactory.getLocalInstance(context).getPaymentBillCollection(viewpaymentBill);
			if (paymentBillColl.size() > 0){
				paymentBill = paymentBillColl.get(0);
				// �жϵ�ǽ�ĸ�������Ƿ�Ϊ������״̬
				if (!paymentBill.getBillStatus().getName().equals("AUDITED")){
					// ��ǰ��ѡ����������δ����
					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fkdbmwsp"));
				}
				/**
				 *�жϵ�ͷ�Ƿ����ʹ�ø�����룬��Ϊһ���������ֻ�ܶ�Ӧһ�����ı���ͬ������
				 */
				FilterInfo filterinfo = new FilterInfo();
				filterinfo.getFilterItems().add(new FilterItemInfo("paymentBill", paymentBill.getId()));
				EntityViewInfo viewinfo = new EntityViewInfo();
				viewinfo.setFilter(filterinfo);
				PaymentSplitCollection paymentSplitColl = PaymentSplitFactory.getLocalInstance(context).getPaymentSplitCollection(viewinfo);
				if (paymentSplitColl.size() > 0) {
					info = paymentSplitColl.get(0);
					return info;
				} else {
					info = new PaymentSplitInfo();
				}
			}else{
				// 1 "������벻����,���߸ø������ "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fPaymentBillNumber1") + fPaymentBillNumber + getResource(context, "Import_NOTNULL"));
			}
			info.setPaymentBill(paymentBill);
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
				CostCenterOrgUnitInfo costCenterOrgUnit = curProject.getCostCenter(); // ������Ŀ��Ӧ�ɱ�������֯
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(new FilterItemInfo("id", costCenterOrgUnit.getId().toString()));
				EntityViewInfo view1 = new EntityViewInfo();
				view1.setFilter(filter1);
				CostCenterOrgUnitCollection ccouc = CostCenterOrgUnitFactory.getLocalInstance(context).getCostCenterOrgUnitCollection(view1);
				if (ccouc.size() > 0) {
					costCenterOrgUnit = ccouc.get(0);
					if (!StringUtils.isEmpty(fCostCenterNumber) && !fCostCenterNumber.replace('.', '!').equals(ccouc.get(0).getLongNumber())) {
						// "��֯�������ڹ�����Ŀ����Ӧ�ĳɱ����Ĳ�����!"
	 					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_cbzxbdy"));
					}
					if (!StringUtils.isEmpty(fCurProjectNameL2) && !curProject.getName().equals(fCurProjectNameL2)) {
						// ������Ŀ�����빤����Ŀ��������Ӧ�����Ʋ���ͬ��
						throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_gcxmmccw"));
					}
				} else {
					// "��֯�������ڹ�����Ŀ����Ӧ�ĳɱ����Ĳ�����!"
 					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_cbzxbdy"));
				}
				info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
			} else {
				// 1 "������Ŀ����Ϊ��,��ù�����Ŀ���� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fCurProjectLongNumber1") + fCurProjectLongNumber + getResource(context, "Import_NOTNULL"));
			}
			// ��ͬ����
			FilterInfo filtercontractBill = new FilterInfo();
			filtercontractBill.getFilterItems().add(new FilterItemInfo("number", fConWithoutTextNumber));
			EntityViewInfo viewcontractBill = new EntityViewInfo();
			viewcontractBill.setFilter(filtercontractBill);
			ContractWithoutTextCollection cwtc = ContractWithoutTextFactory.getLocalInstance(context).getContractWithoutTextCollection(viewcontractBill);
			if (cwtc.size() > 0) {
				contractWithoutText = cwtc.get(0);
				if (!StringUtils.isEmpty(fConWithoutTextName) && !fConWithoutTextName.equals(contractWithoutText.getName())) {
					// ��ͬ���ƶ�Ӧ��ͬ�����е����Ʋ���ͬ��
					throw new TaskExternalException(getResource(context, "htmcbxt"));
				}
				info.setConWithoutText(contractWithoutText);// ���ı���ͬ
			} else {
				// 1 "��ͬ���벻����,���߸ú�ͬ���� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fConWithoutTextNumber1") + fConWithoutTextNumber + getResource(context, "Import_NOTNULL"));
			}
			if (!StringUtils.isEmpty(fPaymentBillAmount) && new BigDecimal(fPaymentBillAmount).compareTo(paymentBill.getActPayAmt()) != 0) {
				// �������븶������ж�Ӧ�ĸ������ȣ�
				throw new TaskExternalException(getResource(context, "fkjeyfkdzdydjebxd"));
			}
			BigDecimal bigD1 = new BigDecimal(fEntrysPayedAmt);
			BigDecimal bigD2 = paymentBill.getActPayAmt();
			if (bigD1.compareTo(bigD2) != 0) {
				// "��������ڹ����ɱ����,���ܵ�������!"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysPayedAmt1"));
			}
			// ����˱���
			FilterInfo filtercreateUser = new FilterInfo();
			filtercreateUser.getFilterItems().add(new FilterItemInfo("number", fCreatorNameL2));
			EntityViewInfo viewcreateUser = new EntityViewInfo();
			viewcreateUser.setFilter(filtercreateUser);
			UserCollection createUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewcreateUser);
			if (createUserColl.size() > 0){
				createUser = createUserColl.get(0);
			}else{
				// 1 "����˱���Ϊ��,���߸ò���˱��� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fCreatorNameL21") + fCreatorNameL2 + getResource(context, "Import_NOTNULL"));
			}
			info.setCreator(createUser);
			// ���ʱ�� // ���ʱ��¼�벻��ȷ ��ʽΪ��YYYY-MM-DD
			info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "PaymentSplitWithoutTxtCon_Import_cfsjcw")).getTime()));
			// �����˱���
			FilterInfo filterauditUser = new FilterInfo();
			filterauditUser.getFilterItems().add(new FilterItemInfo("number", fAuditorNameL2));
			EntityViewInfo viewauditUser = new EntityViewInfo();
			viewauditUser.setFilter(filterauditUser);
			UserCollection auditUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewauditUser);
			if (auditUserColl.size() > 0){
				auditUser = auditUserColl.get(0);
				info.setAuditor(auditUser);
			} else {
				// 1 "�����˱���Ϊ��,���߸������˱��� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fAuditorNameL21") + fAuditorNameL2 + getResource(context, "Import_NOTNULL"));
			}
			// ����ʱ�� // ����ʱ��¼�벻��ȷ ��ʽΪ��YYYY-MM-DD
			info.setAuditTime(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "PaymentSplitWithoutTxtCon_Import_spsjcw")));
			info.setAmount(new BigDecimal(fEntrysPayedAmt));
			info.setCompletePrjAmt(new BigDecimal(fEntrysPayedAmt));
			info.setPayAmount(new BigDecimal(fEntrysPayedAmt));
			info.setOriginalAmount(new BigDecimal(fEntrysPayedAmt));
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("contractId", contractWithoutText.getId()));
			EntityViewInfo view1 = new EntityViewInfo();
			view1.setFilter(filter1);
			ContractBaseDataCollection cbdc = ContractBaseDataFactory.getLocalInstance(context).getContractBaseDataCollection(view1);
			if (cbdc.size() > 0) {
				info.setContractBaseData(cbdc.get(0));
			}
			info.setState(FDCBillStateEnum.AUDITTED);	     // ����״̬��ö��ֵ�����桢��������Ĭ��Ϊ��������
			info.setSplitState(CostSplitStateEnum.ALLSPLIT); // ���״̬��ö��ֵ��δ��֡���ȫ��֡�Ĭ��Ϊ��ȫ��֡�
			info.setHasEffected(false);
			info.setIsNeedTransit(true);
			info.setIslastVerThisPeriod(true);
			info.setHasInitIdx(true);  // HasInitIdx  HasInitIdx
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
	/**
	 * 
	 * @description		���ı���ͬ�����ַ�¼ 
	 * @author			dingwen_yong		
	 * @createDate		2011-6-14
	 * @param 			hashtable
	 * @param 			context
	 * @return
	 * @throws 			TaskExternalException PaymentSplitInfo
	 * @version			EAS1.0
	 * @see
	 */
	private PaymentSplitEntryInfo transmitEntry(Hashtable hashtable, Context context) throws TaskExternalException {

		// 7.������
		String fPaymentBillAmount = ((String) ((DataToken)hashtable.get("FPaymentBill_amount")).data).trim();
		// 8.�����������������
		String fEntrysCurProjectNumber = ((String) ((DataToken)hashtable.get("FEntrys_curProject_number")).data).trim();
		// 9.��������������
		String fEntrysCurProjectNameL2  = ((String) ((DataToken)hashtable.get("FEntrys_curProject_name_l2")).data).trim();
		// 10.�����ɱ���Ŀ����
		String fEntrysCostAccountNumber  = ((String) ((DataToken)hashtable.get("FEntrys_costAccount_number")).data).trim();
		// 11.�����ɱ���Ŀ
		String fEntrysCostAccountNameL2 = ((String) ((DataToken)hashtable.get("FEntrys_costAccount_name_l2")).data).trim();
		// 12.�����ɱ����
		String fEntrysPayedAmt = ((String) ((DataToken)hashtable.get("FEntrys_payedAmt")).data).trim();
		// 13.������Ʒ<ֱ�ӹ���>
		String fEntrysProductNameL2 = ((String) ((DataToken)hashtable.get("FEntrys_product_name_l2")).data).trim();
		
		/**
		 * �ж��Ƿ�Ϊ��
		 */
		if (StringUtils.isEmpty(fEntrysCurProjectNumber)) {
			// "����������������벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysCurProjectNumber"));
		}
		if (StringUtils.isEmpty(fEntrysCostAccountNumber)) {
			// "�����ɱ���Ŀ���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysCostAccountNumber"));
		}
		if (StringUtils.isEmpty(fEntrysProductNameL2)) {
			// ֱ�ӹ�������Ϊ�գ�
			throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysProductNameL2"));
		}
		
		/**
		 * ��¼��Ϣ��ֵ
		 */
		try {
			// ��������������  fCurProjectNumber
			CurProjectInfo curProjectInfo = null;
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("longnumber", fEntrysCurProjectNumber.replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				// �������������������ϵͳ�в�����
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_bcz"));
			}
			// ��ѯ�����ɱ���Ŀ����  
			FilterInfo filtercostAccount = new FilterInfo();
			filtercostAccount.getFilterItems().add(new FilterItemInfo("longnumber", fEntrysCostAccountNumber.replace('.', '!')));
			filtercostAccount.getFilterItems().add(new FilterItemInfo("curproject", curProjectInfo.getId().toString()));
			EntityViewInfo viewcostAccount = new EntityViewInfo();
			viewcostAccount.setFilter(filtercostAccount);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewcostAccount);
			if (costAccountColl.size() > 0){
				costAccount = costAccountColl.get(0);
				costAccount.setCurProject(curProjectInfo);
			} else {
				// 1 "�����ɱ���Ŀ���� "
				// 2 " ��ϵͳ�в�����"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysCostAccountNumber1") + fEntrysCostAccountNumber + getResource(context, "Import_NOTNULL"));
			}
			// ��ѯ������Ʒ<ֱ�ӹ���> ������,�������(�ֶ�˵����Լ����������Ʒ������Ʒ�Ĳ��֮�ͱ�����ڸ�����)
//			if (!StringUtils.isEmpty(fEntrysProductNameL2)) {
				FilterInfo filterproductType = new FilterInfo();
				filterproductType.getFilterItems().add(new FilterItemInfo("name", fEntrysProductNameL2));
				EntityViewInfo viewproductType = new EntityViewInfo();
				viewproductType.setFilter(filterproductType);
				ProductTypeCollection productTypeColl = ProductTypeFactory.getLocalInstance(context).getProductTypeCollection(viewproductType);
				if (productTypeColl.size() > 0){
					productType = productTypeColl.get(0);
				} else {
					// 1 "ֱ�ӹ��� "
					// 2 " ��ϵͳ�в�����"
					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysProductNameL21") + fEntrysProductNameL2 + getResource(context, "Import_NOTNULL"));
				}
//			}
			// �õ���¼		<�����жϷ�¼�ظ�>
//			PaymentSplitEntryCollection entryColl = info.getEntrys();
//			if (entryColl.size() > 0) {
//				entryInfo = null;
//				for (int i = 0; i < entryColl.size(); i++) {
//					entryInfo = entryColl.get(i);
//					// ���ݷ�¼�еĳɱ���Ŀ���룬������Ʒ�Լ�����������ж��Ƿ��ظ���¼
//					// ���ݳɱ���Ŀ����ID��ѯ�ɱ���Ŀ����
//					CostAccountCollection cac = CostAccountFactory.getLocalInstance(context).getCostAccountCollection("where id = '" + entryInfo.getCostAccount().getId().toString() + "'");
//					if (!StringUtils.isEmpty(fEntrysProductNameL2)) {
//						// ���ݹ�����ƷID��ѯ������Ʒ����
//						ProductTypeCollection ptc = ProductTypeFactory.getLocalInstance(context).getProductTypeCollection("where id = '" + entryInfo.getProduct().getId().toString() + "'");
//						if (cac.get(0).getLongNumber().equals(fEntrysCostAccountNumber) && ptc.get(0).getNumber().equals(fEntrysProductNameL2) && entryInfo.getAmount().compareTo(FDCTransmissionHelper.strToBigDecimal(fEntrysPayedAmt))==0) {
//							// ��¼�ظ�
//							throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_entry"));
//						}
//					}
//					if (cac.get(0).getLongNumber().equals(fEntrysCostAccountNumber)  && entryInfo.getAmount().compareTo(FDCTransmissionHelper.strToBigDecimal(fEntrysPayedAmt))==0) {
//						// ��¼�ظ�
//						throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_entry"));
//					}
//					
//				}
//			}
			// �����ͷ���ڵ�����£� ��Ҫ�жϵ�ͷ�ĸ������Ƿ���ڹ����ɱ���������ȣ�������¼�룡
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems().add(new FilterItemInfo("id", info.getPaymentBill().getId()));
			EntityViewInfo view2 = new EntityViewInfo();
			view2.setFilter(filter2);
			PaymentBillCollection pbc = PaymentBillFactory.getLocalInstance(context).getPaymentBillCollection(view2);
			if (!(pbc.size() > 0)) {
				throw new TaskExternalException("");
			}
			if (!StringUtils.isEmpty(fPaymentBillAmount) && new BigDecimal(fPaymentBillAmount).compareTo(pbc.get(0).getActPayAmt()) != 0) {
				// �������븶������ж�Ӧ�ĸ������ȣ�
				throw new TaskExternalException(getResource(context, "fkjeyfkdzdydjebxd"));
			}
			BigDecimal bigD1 = new BigDecimal(fEntrysPayedAmt);
			BigDecimal bigD2 = pbc.get(0).getActPayAmt();
			if (bigD1.compareTo(bigD2) != 0) {
				// "��������ڹ����ɱ����,���ܵ�������!"
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_fEntrysPayedAmt1"));
			}
			entryInfo = new PaymentSplitEntryInfo();
			if (!StringUtils.isEmpty(fEntrysCurProjectNameL2)) {
				if (!curProjectInfo.getName().equals(fEntrysCurProjectNameL2)) {
					// ���������������������������������д��ڵĹ���������������ͬ��
					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_gcxmmcbxt"));
				}
			}
			if (!StringUtils.isEmpty(fEntrysCostAccountNameL2)) {
				if (!costAccount.getName().equals(fEntrysCostAccountNameL2)) {
					// �����ɱ���Ŀ������ɱ���Ŀ�����ж�Ӧ�����Ʋ���ͬ��
					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_gscbkmbxt"));
				}
			}
			entryInfo.setCostAccount(costAccount);					// ���ù����ɱ���Ŀ����
			entryInfo.setAmount(new BigDecimal(fEntrysPayedAmt));	// �����ɱ����
			entryInfo.setProduct(productType);						// ������Ʒ<ֱ�ӹ���>
			entryInfo.setIsLeaf(true);
			entryInfo.setLevel(0);
			entryInfo.setPayedAmt(new BigDecimal(fEntrysPayedAmt)); // ����������
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return entryInfo;
	}

	/**
	 * �õ���Դ�ļ�
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
	
}
