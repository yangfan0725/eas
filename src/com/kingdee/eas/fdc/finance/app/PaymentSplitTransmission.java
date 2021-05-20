/**
 * 
 */
package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
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
 * @description	��ͬ������
 * @author			yongdingwen
 * @version		EAS7.0		
 * @createDate		2011-6-9	 
 * @see						
 */
public class PaymentSplitTransmission extends AbstractDataTransmission {
	
	// ��Դ�ļ�
	private static String resource = "com.kingdee.eas.fdc.finance.PaymentSplitResource";
	
	/** �����뵥�ݶ�Ӧ�����з�¼�����ɱ����ϵ�ӳ�� */
	private Map arrMap = new HashMap();
	/** ���صĵ�ͷӳ�� */
	private Map infoMap = new HashMap();
	/** ��¼�����ɱ���Ŀ����*��֤Ψһ�� */
	private Set costAccountSet = new HashSet();

	/** ��ͬ������ */
	private PaymentSplitInfo info = new PaymentSplitInfo();
	/** ��ͬ�����ַ�¼ */
	private PaymentSplitEntryInfo entryInfo = null;
	/** ������Ŀ */
	private CurProjectInfo curProject = null;
	/** ��ͬ */
	private ContractBillInfo contractBill = null;
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
	/** ������ */
	private BigDecimal paymentBillAmount = null;
	/** ��ͬ��ֿ�Ŀӳ�� */
	private Map contractSplitAccountMap = new HashMap();
	
	/**
	 * 
	 * @description		
	 * @author			Ӻ����		
	 * @createDate		2011-7-12
	 * @param			context
	 * @return			ICoreBase		
	 * @version		EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#getController(com.kingdee.bos.Context)
	 */
	protected ICoreBase getController(Context context) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = PaymentSplitFactory.getLocalInstance(context);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * 
	 * @description		
	 * @author			Ӻ����		
	 * @createDate		2011-7-12
	 * @param			hastable
	 * @param			context
	 * @return			CoreBaseInfo
	 * @version		EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
//		try {
//			info = transmitHead(hashtable, context);
//
//			if (info == null) {
//				return null;
//			}
//			PaymentSplitEntryInfo entry = transmitEntry(hashtable, context);
//			entry.setParent(info);
//			int seq = info.getEntrys().size() + 1;
//			entry.setSeq(seq);
//			entry.setIndex(seq);
//			info.getEntrys().add(entry);
//		} catch (TaskExternalException e) {
//			info = null;
//			throw e;
//		}
//		return info;

		setArrMap(hashtable, context);
		innerTransform(hashtable, context);
		return null;
	}
	
	/**
	 * 
	 * @description		��ͬ�����ֵ�ͷ 
	 * @author				dingwen_yong		
	 * @createDate			2011-6-14
	 * @param 				hashtable
	 * @param 				context
	 * @return				PaymentSplitInfo
	 * @throws 			TaskExternalException PaymentSplitInfo
	 * @version			EAS1.0
	 * @see
	 */
	private PaymentSplitInfo transmitHead(Hashtable hashtable, Context context) throws TaskExternalException {
		
		// 1.��֯������
		String fOrgUnitNumber  = ((String) ((DataToken)hashtable.get("FOrgUnit_number")).data).trim();
		// 2.������Ŀ����
		String fCurProjectCodingNumber  = ((String) ((DataToken)hashtable.get("FCurProject_codingNumber")).data).trim();
		// 3.������Ŀ����
		String fCurProjectNameL2 = ((String) ((DataToken)hashtable.get("FCurProject_name_l2")).data).trim();
		// 4.��ͬ����
		String fContractBillCodingNumber = ((String) ((DataToken)hashtable.get("FContractBill_codingNumber")).data).trim();
		// 5.��ͬ����
		String fContractBillName  = ((String) ((DataToken)hashtable.get("FContractBill_name")).data).trim();
		// 6.�������
		String fPaymentBillNumber  = ((String) ((DataToken)hashtable.get("FPaymentBill_number")).data).trim();
		// 7.������
		String fPaymentBillActPayAmt = ((String) ((DataToken)hashtable.get("FPaymentBill_actPayAmt")).data).trim();
		// 12.�����ɱ����
		String fEntrysAmount = ((String) ((DataToken)hashtable.get("FEntrys_amount")).data).trim();
		// 14.����˱���
		String fCreateNumber  = ((String) ((DataToken)hashtable.get("FCreate_number")).data).trim();
		// 15.���ʱ��
		String fCreateTime  = ((String) ((DataToken)hashtable.get("FCreateTime")).data).trim();
		// 16.�����˱���
		String fPersonNumber = ((String) ((DataToken)hashtable.get("FPerson_number")).data).trim();
		// 17.����ʱ��
		String fAuditTime = ((String) ((DataToken)hashtable.get("FAuditTime")).data).trim();
		// 18.����״̬
		String fState = ((String) ((DataToken)hashtable.get("FState")).data).trim();
		
		/**
		 * �ж��Ƿ�Ϊ��
		 */
		if (StringUtils.isEmpty(fCurProjectCodingNumber)) {
			// "������Ŀ���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCurProjectCodingNumber"));
		}
		if (StringUtils.isEmpty(fContractBillCodingNumber)) {
			// "��ͬ���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fContractBillCodingNumber"));
		}
		if (StringUtils.isEmpty(fPaymentBillNumber)) {
			// "������벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fPaymentBillNumber"));
		}
		if (!StringUtils.isEmpty(fPaymentBillActPayAmt)) {
			if (!fPaymentBillActPayAmt.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "������¼�벻�Ϸ���"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fPaymentBillActPayAmt"));
			}
		}
		if (!StringUtils.isEmpty(fEntrysAmount)) {
			if (!fEntrysAmount.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "�����ɱ����¼�벻�Ϸ���"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysAmount"));
			}
		} else {
			// "�����ɱ�����Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysAmountNOTNULL"));
		}
		if (StringUtils.isEmpty(fCreateNumber)) {
			// "����˱��벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCreateNumber"));
		}
		if (StringUtils.isEmpty(fCreateTime)) {
			// "���ʱ�䲻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCreateTime"));
		}
		if (StringUtils.isEmpty(fPersonNumber)) {
			// "�����˱��벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fPersonNumber"));
		}
		if (StringUtils.isEmpty(fAuditTime)) {
			// "����ʱ�䲻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fAuditTime"));
		}
		
		/** ����Ѿ������˵�ͷ����ֱ�ӷ��� */
		if (infoMap.get(fPaymentBillNumber) != null) {
			return (PaymentSplitInfo) infoMap.get(fPaymentBillNumber);
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
				// �жϵ�ǰ�ĸ�������Ƿ�Ϊ������״̬���Ѹ���״̬
				if (!(paymentBill.getBillStatus().getName().equals("AUDITED") || paymentBill.getBillStatus().getName().equals("PAYED"))){
					// ��ǰ��ѡ����������δ������δ����
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
					if (info.getSplitState().equals("3ALLSPLIT")) {
						// �ú�ͬ��Ӧ�ĸ������ȫ��֣����ܵ��롣
						throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_ghtbndr"));
					}
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
			filterCurProject.getFilterItems().add(new FilterItemInfo("longnumber", fCurProjectCodingNumber.replace('.', '!')));
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
				if (ccouc.size() > 0) {
					costCenterOrgUnit = ccouc.get(0);
					if (!StringUtils.isEmpty(fOrgUnitNumber) && !fOrgUnitNumber.replace('.', '!').equals(ccouc.get(0).getLongNumber())) {
						// "��֯�������ڹ�����Ŀ����Ӧ�ĳɱ����Ĳ�����!"
	 					throw new TaskExternalException(getResource(context, "Import_zzbmbcz"));
					}
					if (!StringUtils.isEmpty(fCurProjectNameL2) && !curProject.getName().equals(fCurProjectNameL2)) {
						// ������Ŀ�����빤����Ŀ��������Ӧ�����Ʋ���ͬ��
						throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_gcxmmccw"));
					}
				} else {
					// "��֯�������ڹ�����Ŀ����Ӧ�ĳɱ����Ĳ�����!"
 					throw new TaskExternalException(getResource(context, "Import_zzbmbcz"));
				}
				info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
			} else {
				// 1 "������Ŀ����Ϊ��,��ù�����Ŀ���� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "DeductBill_Import_fCurProjectLongNumber1") + fCurProjectCodingNumber + getResource(context, "DeductBill_Import_NOTNULL"));
			}
			// ��ú�ͬ��ֿ�Ŀӳ��
			getContractSplitAccountMap(curProject.getId().toString(), context, contractSplitAccountMap);
			// ��ͬ����
			FilterInfo filtercontractBill = new FilterInfo();
			filtercontractBill.getFilterItems().add(new FilterItemInfo("number", fContractBillCodingNumber));
			EntityViewInfo viewcontractBill = new EntityViewInfo();
			viewcontractBill.setFilter(filtercontractBill);
			ContractBillCollection contractBillColl = ContractBillFactory.getLocalInstance(context).getContractBillCollection(viewcontractBill);
			if (contractBillColl.size() > 0){
				contractBill = contractBillColl.get(0);
				if (!StringUtils.isEmpty(fContractBillName) && !fContractBillName.equals(contractBill.getName())) {
					// ��ͬ���ƶ�Ӧ��ͬ�����е����Ʋ���ͬ��
					throw new TaskExternalException(getResource(context, "htmcbxt"));
				}
				// if (contractBill.getSplitState().equals("3ALLSPLIT")) {
				// if (CostSplitStateEnum.NOSPLIT_VALUE.equals(contractBill.getSplitState().getValue())) {
				if (!CostSplitStateEnum.ALLSPLIT_VALUE.equals(contractBill.getSplitState().getValue())) {
					// �ú�ͬδ��ȫ��֣����ܵ��룡
					throw new TaskExternalException(getResource(context, "PaymentSplit_Import_ghtwwqcf"));
				}
			}else{
				// 1 "��ͬ���벻����,���߸ú�ͬ���� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fContractBillCodingNumber1") + fContractBillCodingNumber + getResource(context, "Import_NOTNULL"));
			}
			info.setContractBill(contractBill);
			if (!StringUtils.isEmpty(fPaymentBillActPayAmt) && new BigDecimal(fPaymentBillActPayAmt).compareTo(paymentBill.getActPayAmt()) != 0) {
				// �������븶���������Ӧ�ĸ������ȣ�
				throw new TaskExternalException(getResource(context, "fkjeyfkdzdydjebxd"));
			}
			ArrayList list=(ArrayList)arrMap.get(fPaymentBillNumber);
			BigDecimal bd = new BigDecimal(0);
			for (int i = 0; i < list.size(); i++) {
				bd = bd.add((BigDecimal)list.get(i));
			}
			paymentBillAmount = paymentBill.getActPayAmt();
			if (bd.compareTo(paymentBillAmount) != 0) {
				// "��������ڹ����ɱ����,���ܵ�������!"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysAmount1"));
			}
			info.setAmount(paymentBill.getActPayAmt());
			info.setCompletePrjAmt(paymentBill.getActPayAmt());
			info.setPayAmount(paymentBill.getActPayAmt());
			info.setOriginalAmount(paymentBill.getActPayAmt());
			// ����˱���
			FilterInfo filtercreateUser = new FilterInfo();
			filtercreateUser.getFilterItems().add(new FilterItemInfo("number", fCreateNumber));
			EntityViewInfo viewcreateUser = new EntityViewInfo();
			viewcreateUser.setFilter(filtercreateUser);
			UserCollection createUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewcreateUser);
			if (createUserColl.size() > 0){
				createUser = createUserColl.get(0);
			} else {
				// 1 "����˱���Ϊ��,���߸ò���˱��� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCreateNumber1") + fCreateNumber + getResource(context, "Import_NOTNULL"));
			}
			info.setCreator(createUser);
			// ���ʱ��
			info.setCreateTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fCreateTime, getResource(context, "PaymentSplitWithoutTxtCon_Import_cfsjcw")).getTime()));
			// �����˱���
			FilterInfo filterauditUser = new FilterInfo();
			filterauditUser.getFilterItems().add(new FilterItemInfo("number", fPersonNumber));
			EntityViewInfo viewauditUser = new EntityViewInfo();
			viewauditUser.setFilter(filterauditUser);
			UserCollection auditUserColl = UserFactory.getLocalInstance(context).getUserCollection(viewauditUser);
			if (auditUserColl.size() > 0){
				auditUser = auditUserColl.get(0);
			} else {
				// 1 "�����˱���Ϊ��,���߸������˱��� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fPersonNumber1") + fPersonNumber + getResource(context, "Import_NOTNULL"));
			}
			info.setAuditor(auditUser);
			// ����ʱ��
			info.setAuditTime(new Timestamp(FDCTransmissionHelper.checkDateFormat(fAuditTime, getResource(context, "PaymentSplitWithoutTxtCon_Import_spsjcw")).getTime()));
			// ����״̬:ö��ֵ�����桢��������Ĭ��Ϊ��������
			if (fState.trim().equals(getResource(context,"SAVE"))) {
				info.setState(FDCBillStateEnum.SAVED);
			} else {
				info.setState(FDCBillStateEnum.AUDITTED);
			}
			info.setSplitState(CostSplitStateEnum.ALLSPLIT);	// ���״̬��ö��ֵ��δ��֡���ȫ��֡�Ĭ��Ϊ��ȫ��֡�
			info.setHasEffected(false);
			info.setIsNeedTransit(true);
			info.setIslastVerThisPeriod(true);
			info.setHasInitIdx(true);  // HasInitIdx  HasInitIdx
			info.setIsColsed(true);    //isclosed  isclosed
			info.setFivouchered(true);
			infoMap.put(fPaymentBillNumber, info);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return info;
	}

	/**
	 * 
	 * @description		��ͬ�����ַ�¼ 
	 * @author				dingwen_yong		
	 * @createDate			2011-6-14
	 * @param 				hashtable
	 * @param 				context
	 * @return				PaymentSplitEntryInfo
	 * @throws 			TaskExternalException PaymentSplitEntryInfo
	 * @version			EAS1.0
	 * @see
	 */
	private PaymentSplitEntryInfo transmitEntry (Hashtable hashtable, Context context) throws TaskExternalException {

		// 7.������
		String fPaymentBillActPayAmt = ((String) ((DataToken)hashtable.get("FPaymentBill_actPayAmt")).data).trim();
		// 8.�����������������
		String fEntrysCurProjectCodingNumber = ((String) ((DataToken)hashtable.get("FEntrys_curProject_codingNumber")).data).trim();
		// 9.��������������
		String fEntrysCurProjectNameL2  = ((String) ((DataToken)hashtable.get("FEntrys_curProject_name_l2")).data).trim();
		// 10.�����ɱ���Ŀ����
		String fCostAccountLongNumber  = ((String) ((DataToken)hashtable.get("FCostAccount_longNumber")).data).trim();
		// 11.�����ɱ���Ŀ
		String fCostAccountNameL2 = ((String) ((DataToken)hashtable.get("FCostAccount_name_l2")).data).trim();
		// 12.�����ɱ����
		String fEntrysAmount = ((String) ((DataToken)hashtable.get("FEntrys_amount")).data).trim();
		// 13.����������
		String fEntrysPayedAmt = ((String) ((DataToken)hashtable.get("FEntrysPayedAmt")).data).trim();
		
		/**
		 * �ж��Ƿ�Ϊ��
		 */
		if (StringUtils.isEmpty(fEntrysCurProjectCodingNumber)) {
			// "����������������벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysCurProjectCodingNumber"));
		}
		if (StringUtils.isEmpty(fCostAccountLongNumber)) {
			// "�����ɱ���Ŀ���벻��Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCostAccountLongNumber"));
		}
		if (StringUtils.isEmpty(fEntrysPayedAmt)) {
			// "�����������Ϊ�գ�"
			throw new TaskExternalException(getResource(context, "PaymentSplit_Import_gsfkje"));
		} else {
			if (!fEntrysPayedAmt.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// "����������¼�벻�Ϸ���"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_gsfkjecw"));
			}
		}
		
		/**
		 * ��¼��Ϣ��ֵ
		 */
		try {
			// ��������������  fCurProjectNumber
			CurProjectInfo curProjectInfo = null;
			FilterInfo curProjectFilter = new FilterInfo();
			curProjectFilter.getFilterItems().add(new FilterItemInfo("longnumber", fEntrysCurProjectCodingNumber.replace('.', '!')));
			EntityViewInfo curProjectView = new EntityViewInfo();
			curProjectView.setFilter(curProjectFilter);
			CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(curProjectView);
			if(curProjectCollection.size() > 0) {
				curProjectInfo = curProjectCollection.get(0);
			} else {
				// �������������������ϵͳ�в�����
				throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_bcz"));
			}
			// �����ɱ���Ŀ����  
			FilterInfo filtercostAccount = new FilterInfo();
			filtercostAccount.getFilterItems().add(new FilterItemInfo("longnumber", fCostAccountLongNumber.replace('.', '!')));
			filtercostAccount.getFilterItems().add(new FilterItemInfo("curproject", curProjectInfo.getId().toString()));
			EntityViewInfo viewcostAccount = new EntityViewInfo();
			viewcostAccount.setFilter(filtercostAccount);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewcostAccount);
			if (costAccountColl.size() > 0){
				costAccount = costAccountColl.get(0);
				costAccount.setCurProject(curProjectInfo);
			} else {
				// 1 "�����ɱ���Ŀ����Ϊ��,���߸ù����ɱ���Ŀ���� "
				// 2 " ��ϵͳ�в����ڣ�"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCostAccountLongNumber1") + fCostAccountLongNumber + getResource(context, "Import_NOTNULL"));
			}
			// �õ���¼		<����Ҫ�жϷ�¼�ظ�>
			PaymentSplitEntryCollection entryColl = info.getEntrys();
			if (entryColl.size() > 0) {
				entryInfo = null;
				for (int i = 0; i < entryColl.size(); i++) {
					entryInfo = entryColl.get(i);
					// �����¼����ʱ �ȽϹ����������������������ɱ���Ŀ�����Ƿ���ͬ��
					if (entryInfo.getCostAccount().getId().equals(costAccount.getId())) {
						// �����ɱ���Ŀ�������
						FilterInfo filtercostA1 = new FilterInfo();
						filtercostA1.getFilterItems().add(new FilterItemInfo("id", entryInfo.getCostAccount().getId()));
						EntityViewInfo viewcostA1 = new EntityViewInfo();
						viewcostA1.setFilter(filtercostA1);
						CostAccountCollection costA1Coll = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(viewcostA1);
						if (costA1Coll.size() > 0){
							// ��������������
							FilterInfo curProjectF = new FilterInfo();
							curProjectF.getFilterItems().add(new FilterItemInfo("id", costA1Coll.get(0).getCurProject().getId()));
							EntityViewInfo curProjectE = new EntityViewInfo();
							curProjectE.setFilter(curProjectF);
							CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(curProjectE);
							if(curProjectColl.size() > 0) {
								if (curProjectColl.get(0).getId().equals(curProjectInfo.getId())){
									// ��¼�ظ�
									throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_entry"));
								}
							} else {
								// �����¼���ڣ��򲻿��ܻ�ִ�е�else�е����ݣ�
							}
						} else {
							// �����¼���ڣ��򲻿��ܻ�ִ�е�else�е����ݣ�
						}
					}
				}
			}
//			// �����ͷ���ڵ�����£� ��Ҫ�жϵ�ͷ�ĸ������Ƿ���ڹ����ɱ���������ȣ�������¼�룡
//			FilterInfo filter1 = new FilterInfo();
//			filter1.getFilterItems().add(new FilterItemInfo("id", info.getPaymentBill().getId()));
//			EntityViewInfo view1 = new EntityViewInfo();
//			view1.setFilter(filter1);
//			PaymentBillCollection pbc = PaymentBillFactory.getLocalInstance(context).getPaymentBillCollection(view1);
//			if (!(pbc.size() > 0)) {
//				throw new TaskExternalException("");
//			}
//			if (!StringUtils.isEmpty(fPaymentBillActPayAmt) && new BigDecimal(fPaymentBillActPayAmt).compareTo(pbc.get(0).getActPayAmt()) != 0) {
//				// �������븶������ж�Ӧ�ĸ������ȣ�
//				throw new TaskExternalException(getResource(context, "fkjeyfkdzdydjebxd"));
//			}
			entryInfo = new PaymentSplitEntryInfo();
			if (!StringUtils.isEmpty(fEntrysCurProjectNameL2)) {
				if (!curProjectInfo.getName().equals(fEntrysCurProjectNameL2)) {
					// ���������������������������������д��ڵĹ���������������ͬ��
					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_gcxmmcbxt"));
				}
			}
			if (!StringUtils.isEmpty(fCostAccountNameL2)) {
				if (!costAccount.getName().equals(fCostAccountNameL2)) {
					// �����ɱ���Ŀ������ɱ���Ŀ�����ж�Ӧ�����Ʋ���ͬ��
					throw new TaskExternalException(getResource(context, "PaymentSplitWithoutTxtCon_Import_gscbkmbxt"));
				}
			}
			
			entryInfo.setCostAccount(costAccount);						 // ���óɱ���Ŀ����
			// entryInfo.setAmount(new BigDecimal(fEntrysAmount));			 // �����ɱ����
			entryInfo.setAmount(paymentBillAmount);			 	 		 // ������
			entryInfo.setPayedAmt(new BigDecimal(fEntrysPayedAmt));		 // ����������
			entryInfo.setIsLeaf(true);
			entryInfo.setLevel(0);
//			entryInfo.setDirectAmount(new BigDecimal(fEntrysPayedAmt));  // ֱ�Ӹ�����
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return entryInfo;
	}
	
	/**
	 * 
	 * @description	����ӳ������
	 * @author			Ӻ����	
	 * @createDate		2011-7-12
	 * @param			hashtable
	 * @param 			context
	 * @throws 		TaskExternalException void
	 * @version		EAS1.0
	 * @see
	 */
	private void setArrMap(Hashtable hashtable, Context context) throws TaskExternalException {
		for (int i = 0; i < hashtable.size(); i++) {
			Hashtable lineData = (Hashtable) hashtable.get(new Integer(i));
			// �������
			String paymentBillNumber = ((String)((DataToken) lineData.get("FPaymentBill_number")).data).trim();
			// �����ɱ���Ŀ����
			String costAccountLongNumber = ((String)((DataToken) lineData.get("FCostAccount_longNumber")).data).trim();
			// �����ɱ����
			String entrysAmount = ((String)((DataToken) lineData.get("FEntrys_amount")).data).trim();
			
			/**
			 * ��¼���Ƿ�¼�� <�ж�>
			 */
			if (StringUtils.isEmpty(paymentBillNumber)) {
				// "������벻��Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fPaymentBillNumber"));
			}
			if (StringUtils.isEmpty(costAccountLongNumber)) {
				// "�����ɱ���Ŀ���벻��Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fCostAccountLongNumber"));
			}
			if (!StringUtils.isEmpty(entrysAmount)) {
				if (!entrysAmount.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
					// "�����ɱ����¼�벻�Ϸ���"
					throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysAmount"));
				}
			} else {
				// "�����ɱ�����Ϊ�գ�"
				throw new TaskExternalException(getResource(context, "PaymentSplit_Import_fEntrysAmountNOTNULL"));
			}
			
			if(!costAccountSet.add(paymentBillNumber+costAccountLongNumber)){
				// ��¼�ظ���
				FDCTransmissionHelper.isThrow(getResource(context, "PaymentSplitWithoutTxtCon_Import_entry"));
			}
			
			if (arrMap.get(paymentBillNumber) == null) {
				arrMap.put(paymentBillNumber, new ArrayList());
			}

			BigDecimal decimal = new BigDecimal(entrysAmount);
			((ArrayList) arrMap.get(paymentBillNumber)).add(decimal);
			
		}
	}
	
	/**
	 * 
	 * @description	�ڵ�ת��������	
	 * @author			Ӻ����	
	 * @createDate		2011-7-12
	 * @param			hashtable
	 * @param 		    context
	 * @return			CoreBaseInfo
	 * @throws 		TaskExternalException CoreBaseInfo
	 * @version		EAS1.0
	 * @see
	 */
	protected CoreBaseInfo innerTransform(Hashtable hashtable, Context context) throws TaskExternalException {
		try {
			for (int i = 0; i < hashtable.size(); i++) {
				Hashtable lineData = (Hashtable) hashtable.get(new Integer(i));
				info = innerTransformHead(lineData, context);
				PaymentSplitEntryInfo entry = innerTransformEntry(lineData, context);
				int seq = info.getEntrys().size() + 1;
				entry.setSeq(seq);
				entry.setParent(info);
				info.getEntrys().add(entry);
			}
			// ����infoMAP
			Collection coll = infoMap.values();
			Iterator it = coll.iterator();
			while (it.hasNext()) {
				PaymentSplitInfo info = (PaymentSplitInfo) it.next();
				// ��������ֿ�Ŀ���ͬ��ֿ�Ŀһ����У��
				HashMap contractSplitAccountMapTemp = (HashMap)contractSplitAccountMap.get(info.getCurProject().getId().toString() + info.getContractBill().getId().toString());
				if (contractSplitAccountMapTemp == null) {
					FDCTransmissionHelper.isThrow(getResource(context, "PaymentSplit_Import_ContractSplitNotExist"));
				} else {
					PaymentSplitEntryCollection entrys = info.getEntrys();
					for (int i = 0; i < entrys.size(); i++) {
						PaymentSplitEntryInfo entry = entrys.get(i);
						ContractCostSplitEntryInfo  contractCostSplitEntryInfo = (ContractCostSplitEntryInfo)contractSplitAccountMapTemp.get(entry.getCostAccount().getId().toString());
						if (contractCostSplitEntryInfo == null) {
							FDCTransmissionHelper.isThrow(getResource(context, "PaymentSplit_Import_ContractSplitNotExist"));
						}
					}
				}
				if (info.getId() == null || !getController(context).exists(new ObjectUuidPK(info.getId())))
					getController(context).save(info);
				else
					getController(context).update(new ObjectUuidPK(info.getId()), info);
			}
		}catch (TaskExternalException e) {
			info = null;
			throw e;
		} catch (EASBizException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		return null;
	}
	
	
	/**
	 * 
	 * @description	����ͷ*ת��������
	 * @author			Ӻ����	
	 * @createDate		2011-7-12
	 * @param 			hashtable
	 * @param 			context
	 * @return			PaymentSplitInfo
	 * @throws 		TaskExternalException PaymentSplitInfo
	 * @version		EAS1.0
	 * @see
	 */
	private PaymentSplitInfo innerTransformHead(Hashtable hashtable, Context context) throws TaskExternalException {
		return this.transmitHead(hashtable, context);
	}
	
	
	/**
	 * 
	 * @description	��¼*ת��������	
	 * @author			Ӻ����	
	 * @createDate		2011-7-12
	 * @param 			hashtable
	 * @param 			context
	 * @return			PaymentSplitEntryInfo
	 * @throws 		TaskExternalException PaymentSplitEntryInfo
	 * @version		EAS1.0
	 * @see
	 */
	private PaymentSplitEntryInfo innerTransformEntry(Hashtable hashtable, Context context) throws TaskExternalException {
		return this.transmitEntry(hashtable, context);
	}
	
	/** 
	 * ��д���෽��
	 */
	public boolean isSameBlock(Hashtable firstData, Hashtable currentData){
		return firstData != null && currentData != null ;
	}
	
	/**
	 * ����һ���Լ�������
	 */
	public int getSubmitType() {
		return SUBMITMULTIRECTYPE;
	}
	
	/**
	 * �õ���Դ�ļ�
	 */
	public static String getResource(Context context, String key) {
		return ResourceBase.getString(resource, key, context.getLocale());
	}
	
	private void getContractSplitAccountMap(String curProjectID, Context ctx, Map contractSplitAccountMap) throws TaskExternalException{
		try {
			Map contractCostSplitEntryMap = new HashMap();
			ContractCostSplitInfo contractCostSplitInfo = null;
			ContractCostSplitEntryInfo contractCostSplitEntryInfo = null;
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectID));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			ContractCostSplitCollection info = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(view);
			for (int i = 0; i < info.size(); i++) {
				contractCostSplitInfo = info.get(i);
				ContractCostSplitEntryCollection contractCostSplitEntrys = contractCostSplitInfo.getEntrys();
				for (int j = 0; j < contractCostSplitEntrys.size(); j++) {
					contractCostSplitEntryInfo = contractCostSplitEntrys.get(j);
					contractCostSplitEntryMap.put(contractCostSplitEntryInfo.getCostAccount().getId().toString(), contractCostSplitEntryInfo);
				}
				contractSplitAccountMap.put(curProjectID + contractCostSplitInfo.getContractBill().getId().toString(), contractCostSplitEntryMap);
			}
			return ;
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		} catch (Exception e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
	}
}
