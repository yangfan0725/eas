package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeAuxFactory;
import com.kingdee.eas.basedata.assistant.ExchangeAuxInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBaseDataInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.DifPlaceEnum;
import com.kingdee.eas.fi.cas.FdcPayeeTypeEnum;
import com.kingdee.eas.fi.cas.FeeTypeFactory;
import com.kingdee.eas.fi.cas.FeeTypeInfo;
import com.kingdee.eas.fi.cas.IsMergencyEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fm.fpl.FpItemFactory;
import com.kingdee.eas.fm.fpl.FpItemInfo;
import com.kingdee.eas.fm.fs.SettBizTypeFactory;
import com.kingdee.eas.fm.fs.SettBizTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.ResourceBase;

/**
 * 
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		�����������ʵ����
 *		
 * @author			liangwenjie
 * @version		EAS7.0		
 * @createDate		2011-7-22	 
 * @see
 */
public class PaymentBillImport extends AbstractDataTransmission {

	private static String resource = "com.kingdee.eas.fdc.finance.FDCBudgetAcctResource";
	
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		
		ICoreBase factory = null;
		try {
			factory = PaymentBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * 
	 * @description		������룬���������Ľܡ�����BUG�޸ģ�Ӻ����
	 * @author				Ӻ����		
	 * @createDate			2011-7-22
	 * @param				hsData
	 * @param				ctx
	 * @return				CoreBaseInfo
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)
	 */
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		
		PaymentBillInfo info = new PaymentBillInfo();
		
		//ȡֵ
		String FCostCenter_longNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCostCenter_longNumber");//��֯����
		String FCurProject_longNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCurProject_longNumber");//*������Ŀ������
		String FAgentPayCompany_name = FDCTransmissionHelper.getFieldValue(hsData, "FAgentPayCompany_name_l2");//�����˾
		String FPayBillType_name = FDCTransmissionHelper.getFieldValue(hsData, "FPayBillType_name_l2");//*��������
		String FContractNo = FDCTransmissionHelper.getFieldValue(hsData, "FContractNo");//*��ͬ���
		String FBillStatus = FDCTransmissionHelper.getFieldValue(hsData, "FBillStatus");//����״̬
		String FNumber = FDCTransmissionHelper.getFieldValue(hsData, "FNumber");//*�������
		String payReqNumber = FDCTransmissionHelper.getFieldValue(hsData, "payReqNumber");// *
																							// �������뵥����
		
		String FUseDepartment_name = FDCTransmissionHelper.getFieldValue(hsData, "FUseDepartment_name_l2");//*�ÿ��
		String FBizDate = FDCTransmissionHelper.getFieldValue(hsData, "FBizDate");//*ҵ������
		FBizDate = forMatDate(FBizDate);
		
		String FFeeType_name = FDCTransmissionHelper.getFieldValue(hsData, "FFeeType_name_l2");//�ո����
		String FRecProvince = FDCTransmissionHelper.getFieldValue(hsData, "FRecProvince");//�տʡ
		String FRecCity = FDCTransmissionHelper.getFieldValue(hsData, "FRecCity");//�տ����
		String FPayerAccountBank_bankAccountNumber = FDCTransmissionHelper.getFieldValue(hsData, "FPayerAccountBank_bankAccountNumber");//�����ʺ�
		String FFdcPayeeType = FDCTransmissionHelper.getFieldValue(hsData, "FFdcPayeeType");//�տ������
		String FIsDifferPlace = FDCTransmissionHelper.getFieldValue(hsData, "FIsDifferPlace");//ͬ�����
		String FPayerBank_name = FDCTransmissionHelper.getFieldValue(hsData, "FPayerBank_name_l2");//��������
		String FPayeeName = FDCTransmissionHelper.getFieldValue(hsData, "FPayeeName");//*�տ�������
		String FActFdcPayeeName_name = FDCTransmissionHelper.getFieldValue(hsData, "FActFdcPayeeName_name_l2");//ʵ���տλȫ��
		String FPayerAccount_name = FDCTransmissionHelper.getFieldValue(hsData, "FPayerAccount_name_l2");//�����Ŀ
		String FPayeeBank = FDCTransmissionHelper.getFieldValue(hsData, "FPayeeBank");//�տ�����
		String FCurrency_number = FDCTransmissionHelper.getFieldValue(hsData, "FCurrency_number");//*�ұ����
		String FBizType_name = FDCTransmissionHelper.getFieldValue(hsData, "FBizType_name_l2");//ҵ������
		String FPayeeAccountBank = FDCTransmissionHelper.getFieldValue(hsData, "FPayeeAccountBank");//�տ��˻�
		String FExchangeRate = FDCTransmissionHelper.getFieldValue(hsData, "FExchangeRate");//����
		
		String FIsEmergency = FDCTransmissionHelper.getFieldValue(hsData, "FIsEmergency");//�Ƿ�Ӽ�
		String FAmount = FDCTransmissionHelper.getFieldValue(hsData, "FAmount");//ԭ�ҽ��
		//String FFdcPayReqID = FDCTransmissionHelper.getFieldValue(hsData, "FFdcPayReqID");//�������뵥id--------
		String paymentProportion = FDCTransmissionHelper.getFieldValue(hsData, "paymentProportion");//���ȿ����
		String completePrjAmt = FDCTransmissionHelper.getFieldValue(hsData, "completePrjAmt");//�����깤������(���)
		String FDescription = FDCTransmissionHelper.getFieldValue(hsData, "FDescription");//ժҪ
		String FUsage = FDCTransmissionHelper.getFieldValue(hsData, "FUsage");//��;
		String FFpItem_name = FDCTransmissionHelper.getFieldValue(hsData, "FFpItem_name_l2");//�ƻ���Ŀ
		String FConceit = FDCTransmissionHelper.getFieldValue(hsData, "FConceit");//������
		String FSettlementType_name = FDCTransmissionHelper.getFieldValue(hsData, "FSettlementType_name_l2");//���㷽ʽ
		String FSettlementNumber = FDCTransmissionHelper.getFieldValue(hsData, "FSettlementNumber");//�����
		String grtAmount = FDCTransmissionHelper.getFieldValue(hsData, "grtAmount");//���޽���
		String invoiceDate = FDCTransmissionHelper.getFieldValue(hsData, "invoiceDate");//��Ʊ����
		String InvoiceNumber = FDCTransmissionHelper.getFieldValue(hsData, "InvoiceNumber");//��Ʊ��
		String invoiceAmt = FDCTransmissionHelper.getFieldValue(hsData, "invoiceAmt");//��Ʊ���
		String FSummary = FDCTransmissionHelper.getFieldValue(hsData, "FSummary");//��ע
		String FProject_name = FDCTransmissionHelper.getFieldValue(hsData, "FProject_name_l2");//��Ŀ����
		String FContractBase_name = FDCTransmissionHelper.getFieldValue(hsData, "FContractBase_name_l2");//��ͬ����
		String FLatestPrice = FDCTransmissionHelper.getFieldValue(hsData, "FLatestPrice");//�������
		String ChangeMoney = FDCTransmissionHelper.getFieldValue(hsData, "ChangeMoney");//���ָ����
		String FProjectPriceInContract = FDCTransmissionHelper.getFieldValue(hsData, "FProjectPriceInContract");//*��ͬ�ڹ��̿�_���ڷ�����ԭ�ң�
		String FCurPlannedPayment = FDCTransmissionHelper.getFieldValue(hsData, "FCurPlannedPayment");//���ڼƻ�����
		String FCurBackPay = FDCTransmissionHelper.getFieldValue(hsData, "FCurBackPay");//����Ƿ����
		String FCurReqPercent = FDCTransmissionHelper.getFieldValue(hsData, "FCurReqPercent");//��������%
		String FAllReqPercent = FDCTransmissionHelper.getFieldValue(hsData, "FAllReqPercent");//�ۼ�����%
		String FImageSchedule = FDCTransmissionHelper.getFieldValue(hsData, "FImageSchedule");//�������
		String payreq = FDCTransmissionHelper.getFieldValue(hsData, "payreq");//Ӧ������
		String sumpayreq = FDCTransmissionHelper.getFieldValue(hsData, "sumpayreq");//�ۼ�Ӧ������
		String FCreator_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FCreator_name_l2");//*�Ƶ��˱���
		String FCreateTime = FDCTransmissionHelper.getFieldValue(hsData, "FCreateTime");//*�Ƶ�ʱ��
		FCreateTime = forMatDate(FCreateTime);
		
		String FAuditor_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FAuditor_name_l2");//����˱���
		String FAuditDate = FDCTransmissionHelper.getFieldValue(hsData, "FAuditDate");//�������
		FAuditDate = forMatDate(FAuditDate);
		
		String FCashier_name = FDCTransmissionHelper.getFieldValue(hsData, "FCashier_name_l2");//����
		String FAccountant_name = FDCTransmissionHelper.getFieldValue(hsData, "FAccountant_name_l2");//���
		String award = FDCTransmissionHelper.getFieldValue(hsData, "award");//����
		String breakcontract = FDCTransmissionHelper.getFieldValue(hsData, "breakcontract");//ΥԼ
		String deduct = FDCTransmissionHelper.getFieldValue(hsData, "deduct");//�ۿ�
		String FPayPartAMatlAmt = FDCTransmissionHelper.getFieldValue(hsData, "FPayPartAMatlAmt");//*�׹��Ŀۿ�
		
		FDCTransmissionHelper.valueFormat("��ͬ���", FContractNo, "String", true, 80);
		FDCTransmissionHelper.valueFormat("�������", FNumber, "String", true, 80);
		FDCTransmissionHelper.valueFormat("�������뵥����", payReqNumber, "String", true, 80);
		//�����жϸ������뵥 �Ƿ����
		PayRequestBillInfo prbinfo = null;//�������뵥����
		EntityViewInfo view = null;
		FilterInfo filter = null;
		
		try {// �õ��������뵥�Ķ���
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number",payReqNumber,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("ContractNo",FContractNo,CompareType.EQUALS));
	        view.setFilter(filter);
			prbinfo = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view).get(0);
			if (prbinfo == null) {// ��������Ӧ�ĸ������뵥�����ڣ����ȵ��븶�����뵥
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "payreqnumberisnull"));
				FDCTransmissionHelper.isThrow("��������Ӧ�ĸ������뵥�����ڣ����ȵ��븶�����뵥");
			}
			// added by Owen_wen 2011-08-23 ���ǵ��ͻ���Ӧ�ó�������ʷ���ݸ������뵥һ�����С���������״̬��
			if (!FDCBillStateEnum.AUDITTED_VALUE.equals(prbinfo.getState().getValue())) {
				FDCTransmissionHelper.isThrow("��Ӧ�������뵥δ���������ܵ��롣");
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		//��֤��ʽ�Ƿ���ȷ  �Ƿ�Ϊ��   �����Ƿ񳬳�
		FDCTransmissionHelper.valueFormat("��֯����", FCostCenter_longNumber, "String", false, 80);
		FDCTransmissionHelper.valueFormat("������Ŀ������", FCurProject_longNumber, "String", true, 80);
		FDCTransmissionHelper.valueFormat("�����˾", FAgentPayCompany_name, "String", false, 80);
		FDCTransmissionHelper.valueFormat("��������", FPayBillType_name, "String", true, 80);
		FDCTransmissionHelper.valueFormat("����״̬", FBillStatus, "String", false, 80);
		FDCTransmissionHelper.valueFormat("�ÿ��", FUseDepartment_name, "String", true, 200);
		FDCTransmissionHelper.valueFormat("ҵ������", FBizDate, "Date", true, -1);
		FDCTransmissionHelper.valueFormat("�ո����", FFeeType_name, "String", false, 150);
		FDCTransmissionHelper.valueFormat("�տʡ", FRecProvince, "String", false, 80);
		FDCTransmissionHelper.valueFormat("�տ����", FRecCity, "String", false, 150);
		FDCTransmissionHelper.valueFormat("�����ʺ�", FPayerAccountBank_bankAccountNumber, "String", false, 100);
		FDCTransmissionHelper.valueFormat("�տ������", FFdcPayeeType, "String", false, 80);
		FDCTransmissionHelper.valueFormat("ͬ�����", FIsDifferPlace, "String", false, 80);
		FDCTransmissionHelper.valueFormat("��������", FPayerBank_name, "String", false, 200);
		FDCTransmissionHelper.valueFormat("�տ�������", FPayeeName, "String", true, 80);
		FDCTransmissionHelper.valueFormat("ʵ���տλȫ��", FActFdcPayeeName_name, "String", false, 200);
		FDCTransmissionHelper.valueFormat("�����Ŀ", FPayerAccount_name, "String", false, 100);
		FDCTransmissionHelper.valueFormat("�տ�����", FPayeeBank, "String", false, 90);
		FDCTransmissionHelper.valueFormat("�ұ����", FCurrency_number, "String", true, 80);
		FDCTransmissionHelper.valueFormat("ҵ������", FBizType_name, "String", false, 80);
		FDCTransmissionHelper.valueFormat("�տ��˻�", FPayeeAccountBank, "String", false, 80);
		FDCTransmissionHelper.bdValueFormat("����", FExchangeRate, false, 10,4);
		FDCTransmissionHelper.isBoolean("�Ƿ�Ӽ�", FIsEmergency, false,"��", "��", 80);
		FDCTransmissionHelper.valueFormat("ԭ�ҽ��", FAmount, "Double", false, 80);
		//FDCTransmissionHelper.valueFormat("�������뵥id-----", FFdcPayReqID, "String", false, 80);
		this.ptValueFormat("���ȿ����",paymentProportion,false);
		FDCTransmissionHelper.valueFormat("�����깤������(���)", completePrjAmt, "Double", false, 80);
		FDCTransmissionHelper.valueFormat("ժҪ", FDescription, "String", false, 80);
		FDCTransmissionHelper.valueFormat("��;", FUsage, "String", false, 80);
		FDCTransmissionHelper.valueFormat("�ƻ���Ŀ", FFpItem_name, "String", false, 80);
		FDCTransmissionHelper.valueFormat("������", FConceit, "String", false, 80);
		FDCTransmissionHelper.valueFormat("���㷽ʽ", FSettlementType_name, "String", false, 80);
		FDCTransmissionHelper.valueFormat("�����", FSettlementNumber, "String", false, 80);
		FDCTransmissionHelper.valueFormat("���޽���", grtAmount, "Double", false, -1);
		FDCTransmissionHelper.valueFormat("��Ʊ����", invoiceDate, "Date", false, -1);
		FDCTransmissionHelper.valueFormat("��Ʊ��", InvoiceNumber, "String", false, 80);
		FDCTransmissionHelper.valueFormat("��Ʊ���", invoiceAmt, "Double", false, -1);
		FDCTransmissionHelper.valueFormat("��ע", FSummary, "String", false, 500);
		FDCTransmissionHelper.valueFormat("��Ŀ����", FProject_name, "String", false, 80);
		FDCTransmissionHelper.valueFormat("��ͬ����", FContractBase_name, "String", false, 80);
		FDCTransmissionHelper.valueFormat("�������", FLatestPrice, "Double", false, 80);
		FDCTransmissionHelper.valueFormat("���ָ����", ChangeMoney, "Double", false, 80);
		FDCTransmissionHelper.valueFormat("��ͬ�ڹ��̿�_���ڷ���(ԭ��)", FProjectPriceInContract, "Double", true, 80);
		FDCTransmissionHelper.valueFormat("���ڼƻ�����", FCurPlannedPayment, "Double", false, 80);
		FDCTransmissionHelper.valueFormat("����Ƿ����", FCurBackPay, "Double", false, 80);
		this.ptValueFormat("��������%", FCurReqPercent, false);
		this.ptValueFormat("�ۼ�����%", FAllReqPercent, false);
		this.ptValueFormat("�������%", FImageSchedule, false);
		this.ptValueFormat("Ӧ������%", payreq, false);
		this.ptValueFormat("�ۼ�Ӧ������%", sumpayreq, false);
		FDCTransmissionHelper.valueFormat("�Ƶ��˱���", FCreator_name_l2, "String", true, 80);
		FDCTransmissionHelper.valueFormat("�Ƶ�ʱ��", FCreateTime, "Date", true, 80);
		if(FBillStatus.trim().equals("������") || FBillStatus.trim().equals("") || FBillStatus==null){//����״̬�Ѿ�������Ϊ��  ��Ϊ����¼��Ŀ
			FDCTransmissionHelper.valueFormat("����˱���", FAuditor_name_l2, "String", true, 80);
			FDCTransmissionHelper.valueFormat("�������", FAuditDate, "Date", true, 80);
		}else{
			FDCTransmissionHelper.valueFormat("����˱���", FAuditor_name_l2, "String", false, 80);
			FDCTransmissionHelper.valueFormat("�������", FAuditDate, "Date", false, 80);
			if(!FAuditor_name_l2.equals("")){
//				FDCTransmissionHelper.isThrow("������״̬��ʱ�򣬲�����д����ˣ�");
			}
			if(!FAuditDate.equals("")){
//				FDCTransmissionHelper.isThrow("������״̬��ʱ�򣬲�����д������ڣ�");
			}
		}
		FDCTransmissionHelper.valueFormat("����", FCashier_name, "String", false, 80);
		FDCTransmissionHelper.valueFormat("���", FAccountant_name, "String", false, 80);
		
		FDCTransmissionHelper.valueFormat("����", award, "Double", false, 80);
		FDCTransmissionHelper.valueFormat("ΥԼ", breakcontract, "Double", false, 80);
		FDCTransmissionHelper.valueFormat("�ۿ�", deduct, "Double", false, 80);
		FDCTransmissionHelper.valueFormat("�׹��Ŀۿ�", FPayPartAMatlAmt, "Double", false, 80);
		
	
		//���ݿ�У��

		
		CostCenterOrgUnitInfo ccinfo = null;//�ɱ�����
		CurProjectInfo cpinfo =  null;//������Ŀ��
		CompanyOrgUnitInfo coinfo =  null;//����˾
		PaymentTypeInfo ptinfo =  null;//��������
		ContractBillInfo cbinfo =  null;//��ͬ
		BillStatusEnum bsenum = null;//����״̬
		PaymentBillInfo pbinfo =  null;//�����
		
		AdminOrgUnitInfo aouinfo =  null;//�ÿ��
		FeeTypeInfo fyinfo =  null;//�ո����
		AccountBankInfo abinfo =  null;//�����ʺ�
		FdcPayeeTypeEnum fptenum = null;//�տ������
		DifPlaceEnum dpenum = null;//ͬ�����
		BankInfo binfo =  null;//������Ϣ
		SupplierInfo sinfo =  null,/*ʵ���տλ*/ skrinfo=null;//�տ���
		AccountViewInfo avinfo =  null;//�����Ŀ
		CurrencyInfo cinfo =  null;//�ұ�
		SettBizTypeInfo sbtinfo =  null;//ҵ������
		ExchangeRateInfo erinfo = null;//����
		IsMergencyEnum imenum = null;//�Ƿ�Ӽ�
		FpItemInfo fiinfo =  null;//��Ŀ�ƻ�
		SettlementTypeInfo syinfo  =  null;//���㷽ʽ
		ProjectInfo pinfo =  null;//��Ŀ
		ContractBaseDataInfo  cbdinfo =  null;//��ͬ��������
		UserInfo uinfo = null, ainfo=null ,cuserinfo=null , accuserinfo=null;//�����˺������ ���� ���
		Timestamp tt = null;//����ʱ��
		BigDecimal bgm = null;

		  
		try {
			//�ҳ�������Ŀ
			cpinfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(this.getView("longnumber", FCurProject_longNumber.replace('.', '!'))).get(0);
              
			//��֯����
			String ccouid = cpinfo.getCostCenter().getId().toString();//�ɱ�����id
			CostCenterOrgUnitInfo ccouinfo = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(this.getView("id", ccouid)).get(0);//�ɱ����Ķ���
			String longnumber = ccouinfo.getLongNumber();//������Ŀ��Ӧ�ĳɱ����ĳ�����
			if(!FCostCenter_longNumber.trim().equals("") && FCostCenter_longNumber!=null){//��д����֯��������
				if(!FCostCenter_longNumber.trim().replace('.', '!').equals(longnumber)){//��д����֯����͹�����Ŀ��Ӧ�ĳɱ����ĳ����벻��ȵ����
					//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource
					// (ctx, "costnumberisnull"));// ��֯������ϵͳ�в�����
					FDCTransmissionHelper.isThrow("��֯������ϵͳ�в�����");// ��֯������ϵͳ�в�����
				}else{
					ccinfo = ccouinfo;
				}
			}else{
				ccinfo = ccouinfo;
			}
			
			//�жϹ�����Ŀ�������Ƿ����
			if(cpinfo==null){
				FDCTransmissionHelper.isThrow("������Ŀ��ϵͳ�в�����");
				// /FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(
				// ctx, "curprojectisnull"));// ������Ŀ��ϵͳ�в�����
			}
			
			//����˾
			coinfo = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection(this.getView("name", FAgentPayCompany_name)).get(0);
			
			//��������
			ptinfo = PaymentTypeFactory.getLocalInstance(ctx).getPaymentTypeCollection(this.getView("name", FPayBillType_name)).get(0);
			
			//��ͬ����
			cbinfo = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(this.getView("number", FContractNo)).get(0);
			if(cbinfo==null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "contractnumberisnull"));// ��ͬ������ϵͳ�в�����
				FDCTransmissionHelper.isThrow("��ͬ������ϵͳ�в�����");
			}
			//�鿴��ͬ�Ƿ��Ѿ�����hasSettled
			BigDecimal bdset =  cbinfo.getSettleAmt();//�����
			if(bdset.compareTo(new BigDecimal(0))==0 && FPayBillType_name.equals("�����")){//����۵�0  ˵��û�н���
				FDCTransmissionHelper.isThrow("��ͬδ����ʱ�����ܵ��븶������Ϊ�������ĸ��!");
			}
			
			//����״̬
			String enums = FBillStatus.trim();
			if (enums.equals("���ύ")) {
				enums = "SUBMIT";
			}else if(enums.equals("������")){
				enums = "AUDITING";
			}else if(enums.equals("���տ�")){
				enums = "RECED";
			}else if(enums.equals("����")){
				enums = "SAVE";
			}else if(enums.equals("�Ѹ���")){
				enums = "PAYED";
			}else if(enums.equals("������")){
				enums = "AUDITED";
			}else{
				enums = "AUDITED";
			}
			
			bsenum = BillStatusEnum.getEnum(enums);//����״̬
			if(bsenum==null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "stateiserror"));// �밴��ģ����ʾ��д��ȷ�ĵ���״̬
				FDCTransmissionHelper.isThrow("�밴��ģ����ʾ��д��ȷ�ĵ���״̬");// �밴��ģ����ʾ��д��ȷ�ĵ���״̬
			}
			
			//�������
			view = new EntityViewInfo();
			filter = new FilterInfo();//ͬһ����ͬ�µ�  ���и�������ظ���˵��
			filter.getFilterItems().add(new FilterItemInfo("number",FNumber,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("contractno",cbinfo.getNumber(),CompareType.EQUALS));
			view.setFilter(filter);
			pbinfo = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(view).get(0);
			if(pbinfo!=null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "paymentnumberiserror"));// ��������ظ�
				FDCTransmissionHelper.isThrow("��������ظ�");// ��������ظ�
			}
			
			
			//�ÿ��
			aouinfo = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(this.getView("name", FUseDepartment_name)).get(0);
			if(aouinfo==null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "aorisnull"));// �ÿ����ϵͳ�в�����
				FDCTransmissionHelper.isThrow("�ÿ����ϵͳ�в�����");
			}

			//�ո����
			fyinfo = FeeTypeFactory.getLocalInstance(ctx).getFeeTypeCollection(this.getView("name", FFeeType_name)).get(0);
			
			//�����ʺ�
			abinfo = AccountBankFactory.getLocalInstance(ctx).getAccountBankCollection(this.getView("number", FPayerAccountBank_bankAccountNumber)).get(0);
			
			//�տ������
			fptenum = FdcPayeeTypeEnum.getEnum("1OTHER");//FFdcPayeeType
			
			//ͬ�����
			enums = FIsDifferPlace.trim();
			
			if (enums.equals("���")) {
				enums = "difPlace";
			}else if(enums.equals("ͬ��")){
				enums = "samePlace";
			}else if(enums.equals("")){
				enums = "samePlace";
			}
			
			dpenum = DifPlaceEnum.getEnum(enums);//ͬ�����
			if(dpenum==null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "diffplaceiserror"));// �밴��ģ����ʾ��д��ȷ��ͬ�����
				FDCTransmissionHelper.isThrow("�밴��ģ����ʾ��д��ȷ��ͬ�����");
			}
	
			//������Ϣ
			if(abinfo!=null){
				binfo = abinfo.getBank();//����ѡ��ĸ����ʺţ��Զ�����
			}
		    
		    //�տ�������
			skrinfo = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(this.getView("name", FPayeeName)).get(0);
			if(skrinfo==null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "skrisnull"));// �տ�����ϵͳ�в�����
				FDCTransmissionHelper.isThrow("�տ�����ϵͳ�в�����");
			}
			
		    //ʵ���տλȫ��
		    sinfo = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(this.getView("name", FActFdcPayeeName_name)).get(0);
		    
		    //�����Ŀ
		    avinfo = AccountViewFactory.getLocalInstance(ctx).getAccountViewCollection(this.getView("name", FPayerAccount_name)).get(0);

			//�ұ�
			cinfo = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(this.getView("number", FCurrency_number)).get(0);
			//����Ϊ�յ�ʱ��ȥ��λ�� 
			if(cinfo==null){
				cinfo = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("where name='�����'").get(0);
				if (cinfo == null) {// �ұ������ϵͳ�в����ڻ��Ҳ���Ĭ�ϱұ�����ϵϵͳ����Ա!
					//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource
					// (ctx, "bbisnull"));
					FDCTransmissionHelper.isThrow("�ұ������ϵͳ�в����ڻ��Ҳ���Ĭ�ϱұ�����ϵϵͳ����Ա!");
				}	
			}
			
			//ҵ������
			sbtinfo = SettBizTypeFactory.getLocalInstance(ctx).getSettBizTypeCollection(this.getView("name", FBizType_name)).get(0);

			//����
			if(FExchangeRate==null){//����Ϊ�� Ĭ��  �ұ�Ļ���
				erinfo = ExchangeRateFactory.getLocalInstance(ctx).getExchangeRateInfo(new ObjectUuidPK(cinfo.getId()));
			}
			CurrencyInfo ci = null;//�����ұ�λ��
			view = new EntityViewInfo();
			filter = new FilterInfo();
			//����  �������Ϊ��   ȡ��ǰ����
			if(FExchangeRate.trim().equals("") || FExchangeRate==null){
				//��λ�Ҷ���ci        //�û���д�ı���-ԭ�Ҷ��� cinfo
				ci = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("where name='�����'").get(0);
				if(ci==null){//ci�Ǳ�λ�ҹ�   �Ҳ�����λ��
					//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource
					// (ctx, "bbisnull"));//�ұ������ϵͳ�в����ڻ��Ҳ���Ĭ�ϱұ�����ϵϵͳ����Ա!
					// �ұ������ϵͳ�в����ڻ��Ҳ���Ĭ�ϱұ�����ϵϵͳ����Ա!
					FDCTransmissionHelper.isThrow("�ұ������ϵͳ�в����ڻ��Ҳ���Ĭ�ϱұ�����ϵϵͳ����Ա!");
				
				}else{//�ҵ���λ�ҵ����
					if(ci.getName().trim().equals(cinfo.getName().trim())){//��������ҵ����
						bgm = new BigDecimal(1.0000);
					}else{
						filter.getFilterItems().add(new FilterItemInfo("targetCurrency.id",ci.getId().toString(),CompareType.EQUALS));//Ŀ��ұ�λ��
						filter.getFilterItems().add(new FilterItemInfo("SourceCurrency.id", cinfo.getId().toString(), CompareType.EQUALS));//�û���д��ԭ��
						filter.setMaskString("#0 and #1");
						view.setFilter(filter);
						ExchangeAuxInfo xinfo = ExchangeAuxFactory.getLocalInstance(ctx).getExchangeAuxCollection(view).get(0);
						if(xinfo==null){
							// FDCTransmissionHelper.isThrow(PaymentBillImport.
							// getResource(ctx, "hlisnull"));//
							// ���ʱ����Ҳ���ԭ�Һͱ�λ�ҵĶһ�����
							FDCTransmissionHelper.isThrow("���ʱ����Ҳ���ԭ�Һͱ�λ�ҵĶһ�����");// ���ʱ����Ҳ���ԭ�Һͱ�λ�ҵĶһ�����
						}else{
							erinfo = ExchangeRateFactory.getLocalInstance(ctx).
							getExchangeRate(new ObjectUuidPK(xinfo.getExchangeTable().getId()),
									new ObjectUuidPK(xinfo.getSourceCurrency().getId()), 
									new ObjectUuidPK(xinfo.getTargetCurrency().getId()), 
									Calendar.getInstance().getTime());
							bgm = erinfo.getConvertRate();
							if(erinfo==null){
								//FDCTransmissionHelper.isThrow(PaymentBillImport
								// .getResource(ctx, "hlisnull"));//
								// ���ʱ����Ҳ���ԭ�Һͱ�λ�ҵĶһ�����
								FDCTransmissionHelper.isThrow("���ʱ����Ҳ���ԭ�Һͱ�λ�ҵĶһ�����");// ���ʱ����Ҳ���ԭ�Һͱ�λ�ҵĶһ�����
							}
						}
					}
				}	
			}else{
				bgm = FDCTransmissionHelper.strToBigDecimal(FExchangeRate);
			}
		
			//�Ƿ�Ӽ� 
			enums = FIsEmergency.trim();
			if(enums.equals("�Ӽ�")){
				enums = "mergercy";
			} else {
				enums = "normal";
			}
			
			imenum = IsMergencyEnum.getEnum(enums);//�Ƿ�Ӽ�
			if(imenum==null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "sfjjiserror"));// �밴��ģ����ʾ��д��ȷ���Ƿ�Ӽ�
				FDCTransmissionHelper.isThrow("�밴��ģ����ʾ��д��ȷ���Ƿ�Ӽ�");// �밴��ģ����ʾ��д��ȷ���Ƿ�Ӽ�
			}
			
			//��Ŀ�ƻ�
			fiinfo = FpItemFactory.getLocalInstance(ctx).getFpItemCollection(this.getView("name", FFpItem_name)).get(0);
			
			//���㷽ʽ 
			syinfo = SettlementTypeFactory.getLocalInstance(ctx).getSettlementTypeCollection(this.getView("name", FFpItem_name)).get(0);
	
			//��Ŀ����   ��ͬ����       �������        ���ָ�      ����ͬ�ڹ��̿�_���ڷ�����ԭ�ң����ɺ�ͬ��Ŵ���    ȡ�ú�ͬ��Ϣ
			//conbinfo = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection("where number='"+FContractNo+"'").get(0);
			//��Ŀ����  FProject_name
			//pinfo = cbinfo.getCurProject();
			//��ͬ����
			cbdinfo = cbinfo.getContractBaseData();
			
			//�������
			
			//������
			uinfo = UserFactory.getLocalInstance(ctx).getUserCollection(this.getView("number", FCreator_name_l2)).get(0);
			if(uinfo==null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "zdrisnull"));// �Ƶ��˱�����ϵͳ�в�����
				FDCTransmissionHelper.isThrow("�Ƶ��˱�����ϵͳ�в�����");// �Ƶ��˱�����ϵͳ�в�����
			}
			
			//����ʱ��
			tt = Timestamp.valueOf(FCreateTime+" 00:00:00.0");
			
			//����˱���
			ainfo = UserFactory.getLocalInstance(ctx).getUserCollection(this.getView("number", FAuditor_name_l2)).get(0);
			if(!FAuditor_name_l2.trim().equals("")){//��д��  �����˱���
				if(ainfo==null){
					//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource
					// (ctx, "shrisnull"));// ����˱�����ϵͳ�в�����
					FDCTransmissionHelper.isThrow("����˱�����ϵͳ�в�����");// ����˱�����ϵͳ�в�����
				}
			}
			
			// ����
			cuserinfo=UserFactory.getLocalInstance(ctx).getUserCollection(this.getView("name", FCashier_name)).get(0);
			
			// ���
			accuserinfo=UserFactory.getLocalInstance(ctx).getUserCollection(this.getView("name", FAccountant_name)).get(0);

			BigDecimal payReqMoney = prbinfo.getOriginalAmount();//�������뵥ԭ�ҽ��
			BigDecimal conProMoney = prbinfo.getProjectPriceInContract();//�������뵥�ĺ�ͬ�ڹ��̿�
			BigDecimal proPriceInContract = FDCTransmissionHelper.strToBigDecimal(FProjectPriceInContract);//����ĺ�ͬ�ڹ��̿�
			BigDecimal PayMentMoney = FDCHelper.ZERO;//��ʼ�����ԭ�ҽ��Ϊ0
			
			//����ĸ�����ܽ��  ����ҪС�ڻ��ߵ���  �������뵥���
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("FdcPayReqNumber",payReqNumber,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("ContractNo",FContractNo,CompareType.EQUALS));
	        view.setFilter(filter);//�������
	        PaymentBillCollection conn  = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(view);
	        //�жϸ��  �����ݿ����Ƿ��Ѿ�����   ����ǵ�һ�ε��븶�����ô��Ҫ���ۿ��֮��Ľ��������������ǵ�2�λ��ߵ�2�����ϵĵ��룬��ֻ��Ҫ���㹤�̿���
	        int ikm = conn.size();
			
			if(ikm==0){//��һ�ε��븶�����뵥��Ӧ��   ���
				//�Ƚϸ������뵥�͸���ĺ�ͬ�ڹ��̿�
				if(proPriceInContract.compareTo(conProMoney)>0){///������ڸ������뵥���  ���ܵ���
					FDCTransmissionHelper.isThrow("�˴ε����Ժ��ۼƵĸ��������ڸ������뵥�����Դ˴ε��벻������");
				}else{
					PayMentMoney = FDCHelper.add(FDCHelper.subtract(payReqMoney, conProMoney), proPriceInContract);
				}
			}else{//��2��  ���ߵ�2�����ϵĵ���
				Iterator it = conn.iterator();
				while (it.hasNext()){
					PaymentBillInfo item = (PaymentBillInfo)it.next();
					PayMentMoney = PayMentMoney.add(item.getAmount());//ԭ�ҽ��
				}
				PayMentMoney = proPriceInContract;//2�λ���2�����ϵĵ��룬��ͬ�ڹ��̿��=ԭ�ҽ��
				if(PayMentMoney.add(proPriceInContract).compareTo(payReqMoney)>0){//������ڸ������뵥���  ���ܵ���
					FDCTransmissionHelper.isThrow("�˴ε����Ժ��ۼƵĸ��������ڸ������뵥�����Դ˴ε��벻������");
				}
			}
			
			
			/**
			 * 	����У����ɺ�����Ҫ���Ĳ������£� 
			 *  ���ý��ø������뵥δ�����޸�Ϊ����״̬
			 *  ��������������뵥֮���ȡ�ø������뵥��Ӧ��״̬Ϊ����ĸ������
			 */
			info = this.auditPayRequestBill(ctx, prbinfo);
			
			//����ֵ
//			info.setCostCenter(ccinfo);//��֯����
//			info.setSourceType(SourceTypeEnum.FDC);
//			
//			info.setCurProject(cpinfo);//������Ŀ������
			info.setAgentPayCompany(coinfo);//�����˾
			info.setFdcPayType(ptinfo);//��������
			info.setBillStatus(bsenum);//����״̬
			
			info.setUseDepartment(aouinfo);//�ÿ��
			info.setBizDate(FDCTransmissionHelper.strToDate(FBizDate));//ҵ������
			info.setFeeType(fyinfo);//�ո����
			info.setRecProvince(FRecProvince);//�տʡ
			info.setRecCity(FRecCity);//�տ����
			info.setPayerAccountBank(abinfo);//�����ʺ�
			info.setFdcPayeeType(fptenum);//�տ������
			info.setIsDifferPlace(dpenum);//ͬ�����
			info.setPayerBank(binfo);//������Ϣ]
			
			info.setPayeeName(FPayeeName);//�տ�������
			info.setPayeeID(skrinfo.getId().toString());//�տ���id
			info.setPayeeNumber(skrinfo.getNumber());//�տ��˱��
//			
			info.setActFdcPayeeName(sinfo);//ʵ���տλȫ��
			info.setPayerAccount(avinfo);//�����Ŀ
			info.setPayeeBank(FPayeeBank);//�տ�����
			info.setCurrency(cinfo);//�ұ����
			info.setBizType(sbtinfo);//ҵ������
			info.setPayeeAccountBank(FPayeeAccountBank);//�տ��˻�
			info.setExchangeRate(bgm);//����
			info.setIsEmergency(imenum);//�Ƿ�Ӽ�

			info.setAmount(PayMentMoney);//ԭ�ҽ��--���⴦��
			info.setLocalAmt(FDCHelper.multiply(PayMentMoney, bgm));//��λ�ҽ��--���⴦��
//			info.setCapitalAmount(FDCHelper.transCap(cinfo, PayMentMoney));//��д���
//			
			info.setDescription(FDescription);//ժҪ
			info.setUsage(FUsage);//��;
			info.setFpItem(fiinfo);//��Ŀ�ƻ�
			info.setConceit(FConceit);//������
			info.setSettlementType(syinfo);//���㷽ʽ
			info.setSettlementNumber(FSettlementNumber);//�����

			info.setCreator(uinfo);// �Ƶ��˱���
			info.setCreateTime(tt);// �Ƶ�ʱ��
			info.setAuditor(ainfo);// ����˱���
			info.setAuditDate(FDCTransmissionHelper.strToDate(FAuditDate));// �������
			info.setCashier(cuserinfo);// ����
			info.setAccountant(accuserinfo);// ���

			info.setSummary(FSummary);// ��ע
//			info.setLatestPrice(FDCTransmissionHelper.strToBigDecimal(FLatestPrice));// �������
//			// ��ͬ�ڹ��̿�_���ڷ���(ԭ�� )
//			info.setProjectPriceInContract(FDCTransmissionHelper.strToBigDecimal(FProjectPriceInContract));
//			info.setCurPlannedPayment(FDCTransmissionHelper.strToBigDecimal(FCurPlannedPayment));// ���ڼƻ�����
//			info.setCurBackPay(FDCTransmissionHelper.strToBigDecimal(FCurBackPay));// ����Ƿ����
//			info.setCurReqPercent(FDCTransmissionHelper.strToBigDecimal(FCurReqPercent));// ��������
//																							// %
//			info.setAllReqPercent(FDCTransmissionHelper.strToBigDecimal(FAllReqPercent));// �ۼ�����
//																							// %
//			info.setImageSchedule(FDCTransmissionHelper.strToBigDecimal(FImageSchedule));// �������
//
//			info.setAddProjectAmt(FDCTransmissionHelper.strToBigDecimal(FProjectPriceInContract));//���̸���������ڷ���ԭ�Һ�ͬ�ڹ��̿�
			
			
			
			// -------------------------------------�û���д��������
			// ������Ҫ�����ݿ���ȥ����--------------------------------
			// // ���ȿ���� ��Ϊ�� ������������//
			// if (!StringUtils.isEmpty(paymentProportion)) {
			// prbinfo.setGrtAmount(FDCTransmissionHelper.strToBigDecimal(
			// paymentProportion));
			// }
			//
			// // �����깤������ false ��Ϊ��// ����������
			// if (!StringUtils.isEmpty(completePrjAmt)) {
			// prbinfo.setCompletePrjAmt(FDCTransmissionHelper.strToBigDecimal(
			// completePrjAmt));
			// }
			//
			// // ���޽��� false // ��Ϊ�� ����������
			// if (!StringUtils.isEmpty(grtAmount)) {
			// prbinfo.setGrtAmount(FDCTransmissionHelper.strToBigDecimal(
			// grtAmount));
			// }
			//
			// // ��Ʊ���� // ��Ϊ�� ����������
			// if (!StringUtils.isEmpty(invoiceDate)) {
			//prbinfo.setInvoiceDate(FDCTransmissionHelper.strToDate(invoiceDate
			// ));
			// }
			//
			// // ��Ʊ�� // ��Ϊ�� ����������
			// if (!StringUtils.isEmpty(InvoiceNumber)) {
			// prbinfo.setInvoiceNumber(InvoiceNumber);
			// }
			//
			// // ��Ʊ��� // ��Ϊ�� ����������
			// if (!StringUtils.isEmpty(invoiceAmt)) {
			// prbinfo.setInvoiceAmt(FDCTransmissionHelper.strToBigDecimal(
			// invoiceAmt));
			// }
			//			
			// // "���ָ����", ChangeMoney ��Ϊ�� ����������
			// if (!StringUtils.isEmpty(ChangeMoney)) {
			// prbinfo.setChangeAmt(FDCTransmissionHelper.strToBigDecimal(
			// ChangeMoney));
			// }
			
			// "Ӧ������", payreq ��Ϊ�� ����������
			
			// "�ۼ�Ӧ������", sumpayreq ��Ϊ�� ����������
			
			
	        // "����", award ��Ϊ�� ����������
			// "ΥԼ", breakcontract ��Ϊ�� ����������
			// "�ۿ�", deduct ��Ϊ�� ����������
			// �׹��Ŀۿ� ��Ϊ�� ����������
			info.setPayPartAMatlAmt(FDCTransmissionHelper.strToBigDecimal(FPayPartAMatlAmt));
			
//			info.setContractNo(FContractNo);//��ͬ���
//			info.setProject(pinfo);//��Ŀ����
//			info.setContractBase(cbdinfo);//��ͬ����
//			info.setContractBillId(cbinfo.getId().toString());//��ͬid
			
			info.setNumber(FNumber);//�������
//			info.setFdcPayReqNumber(prbinfo.getNumber());// �������뵥����
//			info.setFdcPayReqID(prbinfo.getId().toString());// �������뵥
		    
	  		// ����        (�м��<T_CON_GuerdonOfPayReqBill>)
			// ΥԼ        (�м��<T_CON_CompensationOfPayReqBill>)
			// �ۿ�        (�м��<T_CON_DeductOfPayReqBill>)
			// �׹��Ŀۿ�  (�м��<T_CON_PartAOfPayReqBill>)
			
			// ���µĸ��������յ�֮��ֱ�ӽ���Update�޸�
			PaymentBillFactory.getLocalInstance(ctx).update(new ObjectUuidPK(String.valueOf(info.getId())), info);
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		} 
		
		return null;// ��Ϊ�������뵥������֮�󸶿�����Ѿ���������ѡ�񷵻�null
	}
	
//	/**
//	 * ��ע�� �˷����Ѿ���ʱ�����Ա�ע���ˣ�
//	 * @description		�ڱ��渶�֮�󣬰������޸ĸ������뵥��״̬Ϊ��������		
//	 * @author				Ӻ����		
//	 * @createDate			2011-7-21
//	 * @param				
//	 * @return							
//	 * @version			EAS1.0
//	 * @see					
//	 * (non-Javadoc)
//	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#submit(com.kingdee.eas.framework.CoreBaseInfo, com.kingdee.bos.Context)					
//	 */
//	public void submit(CoreBaseInfo coreBaseInfo, Context context) throws TaskExternalException {
//		
//		super.submit(coreBaseInfo, context);
//		
//		/* ����������뵥״̬����������ʱ����һ�����������Ҫ�ڸ������֮������޸ĸ������뵥��״̬ */
//		if (coreBaseInfo instanceof PaymentBillInfo) {
//			PaymentBillInfo paymentBillInfo = (PaymentBillInfo) coreBaseInfo;
//			// �õ��������뵥��ţ�
//			String pbiNumber = paymentBillInfo.getFdcPayReqNumber();
//			// �õ��������뵥ID��
//			String pbiID = String.valueOf(paymentBillInfo.getFdcPayReqID());
//			// �õ���ͬ����
//			String cbID = String.valueOf(paymentBillInfo.getContractNo());
//			try {
//				/** ��ѯ�������뵥 */
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("number", pbiNumber));
//				filter.getFilterItems().add(new FilterItemInfo("id", pbiID));
//				filter.getFilterItems().add(new FilterItemInfo("contractNo", cbID));
//				EntityViewInfo view = new EntityViewInfo();
//				view.setFilter(filter);
//				PayRequestBillCollection payRequestBillColl = PayRequestBillFactory.getLocalInstance(context).getPayRequestBillCollection(view);
//				if (payRequestBillColl.size() > 0) {
//					PayRequestBillInfo payRequestBillInfo = payRequestBillColl.get(0);
//					payRequestBillInfo.setState(FDCBillStateEnum.AUDITTED); // ���ø������뵥״̬Ϊ������
//					payRequestBillInfo.setAuditor(paymentBillInfo.getCreator()); // ����������
//					payRequestBillInfo.setAuditTime(paymentBillInfo.getCreateTime()); // ��������ʱ��
//					/** ִ�и��ĸ������뵥��״̬Ϊ������ */
////					PayRequestBillFactory.getLocalInstance(context).update(new ObjectUuidPK(String.valueOf(payRequestBillInfo.getId())), payRequestBillInfo);
//					PayRequestBillFactory.getLocalInstance(context).audit(payRequestBillInfo.getId());
//					payRequestBillInfo.setNumber("");
//				}
//			} catch (BOSException e) {
//				e.printStackTrace();
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	/**
	 * @description		����������뵥δ�������������ø������뵥��	
	 * @author				Ӻ����	
	 * @param				context
	 * @param				payRequestBillInfo
	 * @createDate			2011-7-22
	 * @throws 			TaskExternalException
	 * @void 				PaymentBillInfo
	 * @version			EAS1.0
	 * @see
	 */
	private PaymentBillInfo auditPayRequestBill(Context context, PayRequestBillInfo payRequestBillInfo) throws TaskExternalException {
		
		try {
			/** ִ�и��ĸ������뵥��״̬Ϊ������ */
			PayRequestBillFactory.getLocalInstance(context).audit(payRequestBillInfo.getId());
			
			PaymentBillInfo paymentBillInfo = null;
			
			// ��ѯ�ø������뵥�������Ӧ�ĸ�������޸���ֵ<����һ���������뵥ֻ��Ӧһ�����>
			// ��ͬ��ţ�
			String contractNo = String.valueOf(payRequestBillInfo.getContractNo());
			// �������뵥���룡
			String payRequestBillInfo_Number = payRequestBillInfo.getNumber();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractNo", contractNo));
			filter.getFilterItems().add(new FilterItemInfo("FdcPayReqNumber", payRequestBillInfo_Number));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			PaymentBillCollection paymentBillColl = PaymentBillFactory.getLocalInstance(context).getPaymentBillCollection(view);
			if (!(paymentBillColl.size() > 0)) {
				// ò����Զ��ִ�в�����
				FDCTransmissionHelper.isThrow("������ݳ��ֲ��ɹ���Ĵ������뿪����Ա��ϵ��");	
			}
			paymentBillInfo = paymentBillColl.get(0);
			
			return paymentBillInfo;
			
		} catch (EASBizException e) {
			FDCTransmissionHelper.isThrow(e.getMessage() + "\n��ѡ��ĸ������뵥����ʧ�ܣ����ܵ��븶���");	
			// ����׳��쳣��˵������ʧ��
//			e.printStackTrace();
		} catch (BOSException e) {
			FDCTransmissionHelper.isThrow("��ѡ��ĸ������뵥����ʧ�ܣ����ܵ��븶���");	
			// ����׳��쳣��˵������ʧ��
//			e.printStackTrace();
		}
		
		return null;
		
	}
	
	//������ͼ
	private EntityViewInfo getView(String sqlcolnum,Object item){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(sqlcolnum,item,CompareType.EQUALS));
        view.setFilter(filter);
		return view;	
	}
	
	//�жϰٷ���
	private void ptValueFormat(String cName,String value,boolean b) throws TaskExternalException{
		if(null != value && !"".equals(value.trim()) ){
			if(!value.matches("^([1-9]\\d{0,2}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,2})||0$")){
				FDCTransmissionHelper.isThrow(cName,"������0-100������,������2λС��" );
    		}
		}else{
			if(b){//Ϊ�յ����  �����Ǳ�����ֶ�
				FDCTransmissionHelper.isThrow(cName,"����Ϊ�գ�");	
			}
		}
	}
	
	private String forMatDate(String str ){
		String strDate = str;
		String[] strlen = str.split("-");
		if(strlen.length==3){
			if(strlen[1].length()==1 && strlen[1].matches("[1-9]")){
				strlen[1]="0"+strlen[1];
			}
			if(strlen[2].length()==1 && strlen[2].matches("[1-9]")){
				strlen[2]="0"+strlen[1];
			}
			strDate = strlen[0]+"-"+strlen[1]+"-"+strlen[2];
		}
		return strDate;
	}
	
	
	/**
	 * �õ���Դ�ļ�
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
