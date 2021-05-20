package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeAuxFactory;
import com.kingdee.eas.basedata.assistant.ExchangeAuxInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractSourceFactory;
import com.kingdee.eas.fdc.basedata.ContractSourceInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.LandDeveloperFactory;
import com.kingdee.eas.fdc.basedata.LandDeveloperInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBailFactory;
import com.kingdee.eas.fdc.contract.ContractBailInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractSourceEnum;
import com.kingdee.eas.fdc.contract.ContractWFTypeFactory;
import com.kingdee.eas.fdc.contract.ContractWFTypeInfo;
import com.kingdee.eas.fdc.contract.CostPropertyEnum;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.ResourceBase;

/** 					
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		
 *		
 * @author		lwj
 * @version		EAS7.0		
 * @createDate	2011-6-10	 
 * @see  ��ͬ¼�����������������
 */
public class ContractBillImport extends AbstractDataTransmission{
	
	private static String resource = "com.kingdee.eas.fdc.contract.ContractResource";
	private Context contx = null;

	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		
		ICoreBase factory = null;
		try {
			factory = ContractBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		ContractBillInfo  info = new ContractBillInfo();
		contx = ctx;
	
		//ȡֵ
//		String FOrgUnit_name = FDCTransmissionHelper.getFieldValue(hsData, "FOrgUnit_name_l2");//��֯����
		String FCurProject_longNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCurProject_longNumber");//���������
		String FState = FDCTransmissionHelper.getFieldValue(hsData, "FState");//״̬
		String FContractType_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FContractType_name_l2");//��ͬ���ͱ���
		String FCodingNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCodingNumber");//��ͬ����
		String FName = FDCTransmissionHelper.getFieldValue(hsData, "FName");//����
//		String FProgrammingContract_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FProgrammingContract_name_l2");//��ܺ�Լ����
		String FLandDeveloper_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FLandDeveloper_name_l2");//�׷�����
		String FPartB_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FPartB_name_l2");//�ҷ�����
		String FPartC_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FPartC_name_l2");//��������
		String FContractPropert = FDCTransmissionHelper.getFieldValue(hsData, "FContractPropert");//��ͬ����
//		String FSignDate = FDCTransmissionHelper.getFieldValue(hsData, "FSignDate");//ǩԼ����
		String FCurrency_number = FDCTransmissionHelper.getFieldValue(hsData, "FCurrency_number");//���ֱ���
		String FExRate = FDCTransmissionHelper.getFieldValue(hsData, "FExRate");//����
		String FRespDept_number = FDCTransmissionHelper.getFieldValue(hsData, "FRespDept_number");//���β��ű���
		String FOriginalAmount = FDCTransmissionHelper.getFieldValue(hsData, "FOriginalAmount");//ԭ�ҽ��
		String FAmount = FDCTransmissionHelper.getFieldValue(hsData, "FAmount");//��λ�ҽ��
		String FRespPerson_number = FDCTransmissionHelper.getFieldValue(hsData, "FRespPerson_number");//����������
		
		String FGrtRate = FDCTransmissionHelper.getFieldValue(hsData, "FGrtRate");//���޽����
		FGrtRate = FGrtRate.replace('%', ' ').trim();
		
		String FGrtAmount = FDCTransmissionHelper.getFieldValue(hsData, "FGrtAmount");//���޽���
		String FBizDate = FDCTransmissionHelper.getFieldValue(hsData, "FBizDate");//ҵ������
//		String FCostProperty = FDCTransmissionHelper.getFieldValue(hsData, "FCostProperty");//�������
		
//		String FPayPercForWarn = FDCTransmissionHelper.getFieldValue(hsData, "FPayPercForWarn");//������ʾ����
//		FPayPercForWarn = FPayPercForWarn.replace('%', ' ').trim();
//		
//		String FChgPercForWarn = FDCTransmissionHelper.getFieldValue(hsData, "FChgPercForWarn");//�����ʾ����
//		FChgPercForWarn = FChgPercForWarn.replace('%', ' ').trim();
//		
//		String FPayScale = FDCTransmissionHelper.getFieldValue(hsData, "FContractType_payScale");//���ȿ�����
//		FPayScale = FPayScale.replace('%', ' ').trim();
//		
//		String FOverRate = FDCTransmissionHelper.getFieldValue(hsData, "FOverRate");//������ʾ����
//		FOverRate = FOverRate.replace('%', ' ').trim();
		
//		String FStampTaxRate = FDCTransmissionHelper.getFieldValue(hsData, "FStampTaxRate");//ӡ��˰��
//		FStampTaxRate = FStampTaxRate.replace('%', ' ').trim();
//		
//		String FStampTaxAmt = FDCTransmissionHelper.getFieldValue(hsData, "FStampTaxAmt");//ӡ��˰���
		String FContractSource = FDCTransmissionHelper.getFieldValue(hsData, "FContractSource");//�γɷ�ʽ
		String FIsCoseSplit = FDCTransmissionHelper.getFieldValue(hsData, "FIsCoseSplit");//�Ƿ���붯̬�ɱ�
		String FIsPartAMaterialCon = FDCTransmissionHelper.getFieldValue(hsData, "FIsPartAMaterialCon");//�Ƿ�׹����Ϻ�ͬ
//		String FIsArchived = FDCTransmissionHelper.getFieldValue(hsData, "FIsArchived");//�Ƿ��ѹ鵵
		String FCreator_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FCreator_name_l2");//�Ƶ���
		String FCreateTime = FDCTransmissionHelper.getFieldValue(hsData, "FCreateTime");//�Ƶ�ʱ��
		String FAuditor_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FAuditor_name_l2");//�����
		String FAuditTime = FDCTransmissionHelper.getFieldValue(hsData, "FAuditTime");//���ʱ��
		
		String FNeedPerson_number = FDCTransmissionHelper.getFieldValue(hsData, "FNeedPerson_number");
		String FNeedDept_number = FDCTransmissionHelper.getFieldValue(hsData, "FNeedDept_number");
		String FContractWFType_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FContractWFType_name_l2");
		String FOrgType = FDCTransmissionHelper.getFieldValue(hsData, "FOrgType");
		String FInviteType_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FInviteType_name_l2");
		String FCreateDept_number =FDCTransmissionHelper.getFieldValue(hsData, "FCreateDept_number");
		
		//У������    ��ʽ�Ƿ�Ϸ�        ��¼���Ƿ�Ϊ��     �����Ƿ񳬳�
//		FDCTransmissionHelper.valueFormat(getResource(ctx,"zzcbm"),FOrgUnit_name,"String",false,40);//��֯������
		FDCTransmissionHelper.valueFormat(getResource(ctx,"gcxmcbm"), FCurProject_longNumber, "String", true, 40);//������Ŀ������
		FDCTransmissionHelper.valueFormat(getResource(ctx,"zhuangtai"), FState, "String", false, 40);//״̬
		FDCTransmissionHelper.valueFormat("��ͬ����", FContractType_name_l2, "String", true, 40);//��ͬ���ͱ���
		FDCTransmissionHelper.valueFormat(getResource(ctx,"htbm"), FCodingNumber, "String", true, 40);//��ͬ����
		FDCTransmissionHelper.valueFormat(getResource(ctx,"htmc"), FName, "String", true, 200);//��ͬ����
//		FDCTransmissionHelper.valueFormat(getResource(ctx,"kjhymc"), FProgrammingContract_name_l2, "String", false, 40);//��ܺ�Լ����
		FDCTransmissionHelper.valueFormat(getResource(ctx,"jfbm"), FLandDeveloper_name_l2, "String", true, 40);//�׷�����
		FDCTransmissionHelper.valueFormat(getResource(ctx,"yfbm"), FPartB_name_l2, "String", true, 40);//�ҷ�����
		if(FContractPropert.trim().equals("THREE_PARTY")){//��3����ͬ�����    ���������¼
			FDCTransmissionHelper.valueFormat(getResource(ctx,"bfbm"), FPartC_name_l2, "String", true, 40);//��������
		}else{//����3����ͬ�����
			FDCTransmissionHelper.valueFormat(getResource(ctx,"bfbm"), FPartC_name_l2, "String", false, 40);
		}
		FDCTransmissionHelper.valueFormat(getResource(ctx,"hetongxz"), FContractPropert, "String", true, 40);//��ͬ����
//		FDCTransmissionHelper.valueFormat(getResource(ctx,"qianyueriq"), FSignDate, "Date", true, -1);//ǩԼ����
		FDCTransmissionHelper.valueFormat(getResource(ctx,"bizhongbianma"), FCurrency_number, "String", false, 40);//���ֱ���
		this.bdValueFormat(getResource(ctx,"huilv"),FExRate,false,18,10);//����
		FDCTransmissionHelper.valueFormat(getResource(ctx,"zrbmbm"), FRespDept_number, "String", true, 40);//���β��ű���
		FDCTransmissionHelper.valueFormat(getResource(ctx,"zrrbm"), FRespPerson_number, "String", true, 40);//�����˱���
//		FDCTransmissionHelper.valueFormat(getResource(ctx,"zhaojiaxingzhi"), FCostProperty, "String", true, 40);//�������
		this.bdValueFormat(getResource(ctx,"yuanbijine"), FOriginalAmount, true, 15,4);//ԭ�ҽ��
		this.bdValueFormat(getResource(ctx,"bweibijine"), FAmount, true, 15,4);//��λ�ҽ��
		this.bdValueFormat(getResource(ctx,"baoxioujinbilv"), FGrtRate, true, 15, 4);//���޽����
		this.bdValueFormat(getResource(ctx,"baoxioujinjine"), FGrtAmount, true, 15,4);//���޽���
		FDCTransmissionHelper.valueFormat(getResource(ctx,"yewuriqi"), FBizDate, "Date", true, 40);//ҵ������
//		this.bdValueFormat(getResource(ctx,"fukuantishibili"), FPayPercForWarn, false, 15,4);//������ʾ����
//		this.bdValueFormat(getResource(ctx,"biangentishibili"), FChgPercForWarn, false, 15,4);//�����ʾ����
//		this.bdValueFormat(getResource(ctx,"jindukuanbili"), FPayScale, false, 15,4);//���ȿ�����
//		this.bdValueFormat(getResource(ctx,"jieduantishibilv"), FOverRate, false, 15,4);//������ʾ����
//		this.bdValueFormat(getResource(ctx,"yinhuasuilv"), FStampTaxRate, false, 15,4);//ӡ��˰��
//		this.bdValueFormat(getResource(ctx,"yinhuasuijine"), FStampTaxAmt, true, 15,4);//ӡ��˰���
		FDCTransmissionHelper.valueFormat(getResource(ctx,"xingchengfangshi"), FContractSource, "String", true, 40);//�γɷ�ʽ
		FDCTransmissionHelper.isBoolean(getResource(ctx,"shifoujinrdongtaicb"), FIsCoseSplit, false,getResource(ctx,"yes"),getResource(ctx,"no"), -1);//�Ƿ���붯̬�ɱ�
		FDCTransmissionHelper.isBoolean(getResource(ctx,"shifoujiagonghetong"), FIsPartAMaterialCon, false,getResource(ctx,"yes"),getResource(ctx,"no"), -1);//�Ƿ�׹����Ϻ�ͬ
//		FDCTransmissionHelper.isBoolean(getResource(ctx,"shifouyiguidang"), FIsArchived, false,getResource(ctx,"yes"),getResource(ctx,"no"), -1);//�Ƿ��ѹ鵵	
		FDCTransmissionHelper.valueFormat(getResource(ctx,"zhidanrbianma"), FCreator_name_l2, "String", true, 40);//�Ƶ��˱���
		FDCTransmissionHelper.valueFormat(getResource(ctx,"zhidanriqi"), FCreateTime, "Date", true, 40);//�Ƶ�����
		FState = FState.trim();
		if (!FState.equals(getResource(ctx,"saved")) && !FState.equals(getResource(ctx,"submitted")) && !FState.equals("������") && !FState.equals("����")) {
			FDCTransmissionHelper.valueFormat(getResource(ctx,"shenherbianma"), FAuditor_name_l2, "String", true, 40);//����˱���
			FDCTransmissionHelper.valueFormat(getResource(ctx,"shenheshijian"), FAuditTime, "Date", true, 40);//���ʱ��
		}else{
			FDCTransmissionHelper.valueFormat(getResource(ctx,"shenherbianma"), FAuditor_name_l2, "String", false, 40);//�����ж�
			FDCTransmissionHelper.valueFormat(getResource(ctx,"shenheshijian"), FAuditTime, "Date", false, 40);//�����ж�
		}
		
		FDCTransmissionHelper.valueFormat("�����˲��ű���", FNeedDept_number, "String", true, 40);
		FDCTransmissionHelper.valueFormat("�����˱���", FNeedPerson_number, "String", true, 40);
		FDCTransmissionHelper.valueFormat("��ͬ��������", FContractWFType_name_l2, "String", true, 40);
		FDCTransmissionHelper.valueFormat("�������̷�����֯", FOrgType, "String", true, 40);
		FDCTransmissionHelper.valueFormat("�ɹ����", FInviteType_name_l2, "String", false, 40);
		FDCTransmissionHelper.valueFormat("�Ƶ����ű���", FCreateDept_number, "String", false, 40);
		//���ݿ�����֤   ֵ
		EntityViewInfo view = null;
		FilterInfo filter = null;
		
		FullOrgUnitInfo fouinfo = null;//�ɱ����� Ϊ��ȡ��֯����
		CurProjectInfo cpjinfo = null;//������Ŀ
		FDCBillStateEnum enumValue= null;//����״̬
		ContractTypeInfo ctinfo = null;//��ͬ���ͱ���
		ContractBillInfo cbinfo=null , cbnoinfo=null;//��ͬ��������  ��ͬ���ݱ���
//		ProgrammingContractInfo pcinfo = null;//��ܺ�Լ
		LandDeveloperInfo linfo = null;//�׷���Լ����
		SupplierInfo slinfo = null;//�ҷ�����
		SupplierInfo slerinfo = null;//��������
		ContractPropertyEnum cpenum = null;//��ͬ����
		CurrencyInfo currinfo = null;//����
		ExchangeRateInfo erinfo = null;//���ʶ���
		AdminOrgUnitInfo aouinfo = null;//���β��ű���
		PersonInfo pinfo = null;//���������� FRespPerson
//		CostPropertyEnum cppenum = null;////������� FCostProperty
//		BigDecimal bd = null;//���ڴ���ӡ��˰���BigDecimal ��ֵ
		ContractSourceEnum csenum = null;//�γɷ�ʽ FContractSource
		ContractSourceInfo csinfo = null;//�γɷ�ʽid
		
		UserInfo uinfo = null,ainfo = null;//�Ƶ��� FCreator_name_l2    ,  ����� FAuditor_name_l2
		Timestamp tt = null;//��������  ʱ���
		BigDecimal bgm = null;//���ʵ�ֵ
		
		AdminOrgUnitInfo needDept = null;
		PersonInfo needPerson = null;
		ContractWFTypeInfo wfType= null;
		ContractTypeOrgTypeEnum orgType=null;
		InviteTypeInfo inviteType=null;
		AdminOrgUnitInfo createDept = null;
		try {
			//������Ŀ����
			cpjinfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(this.getView("longnumber", FCurProject_longNumber.replace('.', '!'))).get(0);
			if(cpjinfo==null){//������Ŀ������ϵͳ�в�����
				FDCTransmissionHelper.isThrow(getResource(ctx, "ContractBillImport_FCurProject_longNumber"));
			}
			CostCenterOrgUnitInfo costcouinfo = cpjinfo.getCostCenter();//ȡ�ɱ�����
			if(costcouinfo==null){//������Ŀû�ж�Ӧ�ĳɱ�����
				FDCTransmissionHelper.isThrow(getResource(ctx, "gcxmmydydcbzx"));
			}
//			String ccouid = costcouinfo.getId().toString();//��óɱ����ĵ�id
//			CostCenterOrgUnitInfo ccouinfo = CostCenterOrgUnitFactory.getLocalInstance(ctx)
//					.getCostCenterOrgUnitCollection(this.getView("id", ccouid)).get(0);// �ɱ����Ķ���
			
			//�ɱ���������   Ϊ��ȡ����֯����  ȡ������Ŀ�������Ӧ�ɱ���������
			fouinfo = cpjinfo.getCostCenter().castToFullOrgUnitInfo();//ǿת���̶���-����֯����
			
			//���̳������Ҳ�����ʱ��
			if(cpjinfo == null){
				FDCTransmissionHelper.isThrow("", getResource(ctx, "gcxmcbmzxtzbcz"));
			}
			
			//����״̬��ֵ
			String stateEnum = FState.trim();//״̬
			//״̬���壺���桢�ύ�������������ϡ�Ĭ��������
			if(stateEnum.equals(getResource(ctx,"saved"))){
				stateEnum = "1SAVED";
			}else if(stateEnum.equals(getResource(ctx,"submitted"))){
				stateEnum = "2SUBMITTED";
			}else if(stateEnum.equals(getResource(ctx,"auditting"))){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ztzntxbctjyspzfmrysp"));//״ֻ̬����д���桢���ύ�������������ϡ�Ĭ��������
			}else if(stateEnum.equals(getResource(ctx,"auditted"))){
				stateEnum = "4AUDITTED";
			}else if(stateEnum.equals(getResource(ctx,"stop"))){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ztzntxbctjyspzfmrysp"));
			}else if(stateEnum.equals(getResource(ctx,"announce"))){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ztzntxbctjyspzfmrysp"));
			}else if(stateEnum.equals(getResource(ctx,"visa"))){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ztzntxbctjyspzfmrysp"));
			}else if(stateEnum.equals(getResource(ctx,"invalid"))){
				stateEnum = "9INVALID";
			} else {// Ĭ���Ѿ�����
				stateEnum = "4AUDITTED";
			}
			enumValue = FDCBillStateEnum.getEnum(stateEnum);
			
			//��ͬ���ͱ���
			ctinfo = ContractTypeFactory.getLocalInstance(ctx).getContractTypeCollection(this.getView("name", FContractType_name_l2)).get(0);
			//��ͬ���������Ҳ�����ʱ��
			if(ctinfo == null){
				FDCTransmissionHelper.isThrow("��ͬ������ϵͳ�в�����");
			}
			
			//��ͬ���ݱ���
			cbnoinfo = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(this.getView("codingnumber", FCodingNumber)).get(0);
			if(cbnoinfo!=null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_fCodingNumber"));
			}
			
			//��ͬ������
			cbinfo = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(this.getView("name", FName)).get(0);
			//��ͬ���������ظ���ʱ����ʾ�û�
			if(cbinfo!=null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_fName"));
			}
			 
//			//��ܺ�Լ
//			pcinfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractCollection(this.getView("name", FProgrammingContract_name_l2)).get(0);
//			//��ܺ�Լ��ϵͳ���Ҳ�����ʱ��
//			if(pcinfo==null && !FProgrammingContract_name_l2.trim().equals("") && FProgrammingContract_name_l2!=null){
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FProgrammingContract_name_l2"));
//			}
			
			//�׷�����
			linfo = LandDeveloperFactory.getLocalInstance(ctx).getLandDeveloperCollection(this.getView("name", FLandDeveloper_name_l2)).get(0);
			//�׷��������Ҳ�����ʱ��
			if(linfo==null){
				FDCTransmissionHelper.isThrow("�׷���ϵͳ�в�����");
			}
			
			//�ҷ����� 
			slinfo = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(this.getView("name", FPartB_name_l2)).get(0);
			//�ҷ������Ҳ�����ʱ��
			if(slinfo==null){
				FDCTransmissionHelper.isThrow("�ҷ���ϵͳ�в�����");
			}
			
			//��������
			slerinfo = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(this.getView("name", FPartC_name_l2)).get(0);
			
			//��ͬ����
			String cproenum = FContractPropert.trim();//״̬
			if(cproenum.equals(getResource(ctx,"directContract"))){
				cproenum = "DIRECT";
			}else if(cproenum.equals(getResource(ctx,"threePartContract"))){
				cproenum = "THREE_PARTY";
			}else if(cproenum.equals(getResource(ctx,"supplyContract"))){
				cproenum = "SUPPLY";
			}else{
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_Fcpenum"));
			}
			cpenum = ContractPropertyEnum.getEnum(cproenum);
			
			//���ֱ��� 
			currinfo = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(this.getView("number", FCurrency_number)).get(0);
			//����Ϊ�յ�ʱ��ȥ��λ�� 
			if(currinfo==null){
				currinfo = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("where number='RMB'").get(0);
				if(currinfo==null){//��ϵͳ���Ҳ�����λ��
					FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FCanotRMB"));
				}	
			}
			
			CurrencyInfo ci = null;//�����ұ�λ��
			view = new EntityViewInfo();
			filter = new FilterInfo();
			//����  �������Ϊ��   ȡ��ǰ����
			if(FExRate.trim().equals("") || FExRate==null){
				//��λ�Ҷ���ci        //�û���д�ı���-ԭ�Ҷ��� currinfo
				ci = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("where number='RMB'").get(0);
				if(ci==null){//ci�Ǳ�λ�ҹ�   �Ҳ�����λ��
					FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FCanotRMB"));
				}else{//�ҵ���λ�ҵ����
					if(ci.getName().trim().equals(currinfo.getName().trim())){//��������ҵ����
						bgm = new BigDecimal(1.0000);
					}else{
						filter.getFilterItems().add(new FilterItemInfo("targetCurrency",ci.getId(),CompareType.EQUALS));//Ŀ��ұ�λ��
						filter.getFilterItems().add(new FilterItemInfo("SourceCurrency", currinfo.getId(), CompareType.EQUALS));//�û���д��ԭ��
						filter.setMaskString("#0 and #1");
						view.setFilter(filter);
						ExchangeAuxInfo xinfo = ExchangeAuxFactory.getLocalInstance(ctx).getExchangeAuxCollection(view).get(0);
						if(xinfo==null){
							FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FyuanAndRmb"));
						}else{
							erinfo = ExchangeRateFactory.getLocalInstance(ctx).
							getExchangeRate(new ObjectUuidPK(xinfo.getExchangeTable().getId()),
									new ObjectUuidPK(xinfo.getSourceCurrency().getId()), 
									new ObjectUuidPK(xinfo.getTargetCurrency().getId()), 
									Calendar.getInstance().getTime());
							bgm = erinfo.getConvertRate();
							if(erinfo==null){
								FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FyuanAndRmb"));
							}
						}
					}
				}	
			}else{
				bgm = FDCTransmissionHelper.strToBigDecimal(FExRate);
			}
			
			//���β��ű���
			aouinfo = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(this.getView("number", FRespDept_number)).get(0);
			if(aouinfo==null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FRespDept_number"));
			}
			
			//�����˱��� 
			pinfo = PersonFactory.getLocalInstance(ctx).getPersonCollection(this.getView("number", FRespPerson_number)).get(0);
			if(pinfo==null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FRespPerson_number"));
			}
//			////������� FCostProperty
//			String cptyenum = FCostProperty.trim();
//			if(cptyenum.equals(getResource(ctx,"tempeval"))){
//				cptyenum = "TEMP_EVAL";
//			}else if(cptyenum.equals(getResource(ctx,"baseconfirm"))){
//				cptyenum = "BASE_CONFIRM";
//			}else if(cptyenum.equals(getResource(ctx,"compconfirm"))){
//				cptyenum = "COMP_COMFIRM";
//			}else{
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_Fcppenum"));
//			}
//			cppenum = CostPropertyEnum.getEnum(cptyenum);
			
//			//������ʾ����
//			double b = FDCTransmissionHelper.strToDouble(FPayPercForWarn);
//			if(FPayPercForWarn.trim().equals("") || FPayPercForWarn==null){
//				FPayPercForWarn = "85";
//			} else if (b < 0 || b > 100) {
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FPayPercForWarn"));
//			}
//			
//			//�����ʾ���� FChgPercForWarn
//			b = FDCTransmissionHelper.strToDouble(FChgPercForWarn);
//			if(FChgPercForWarn==null || FChgPercForWarn.trim().equals("")){
//				FChgPercForWarn = "5";
//			} else if (b < 0 || b > 100) {
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FChgPercForWarn"));
//			}
//			
//			//���ȿ����� FPayScale
//			b = FDCTransmissionHelper.strToDouble(FPayScale);
//			if(FPayScale==null || FPayScale.trim().equals("")){
//				FPayScale = "0";
//			} else if (b < 0 || b > 100) {
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FPayScale"));
//			}
//			
//			//������ʾ���� FOverRate
//			b = FDCTransmissionHelper.strToDouble(FOverRate);
//			if(FOverRate==null || FOverRate.trim().equals("")){
//				FOverRate = "0";
//			} else if (b < 0 || b > 100) {
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FOverRate"));
//			}
			
//			//ӡ��˰�� FStampTaxRate
//			b = FDCTransmissionHelper.strToDouble(FStampTaxRate);
//			if(FStampTaxRate==null || FStampTaxRate.trim().equals("")){
//				FStampTaxRate = "0";
//			} else if (b < 0 || b > 100) {
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FStampTaxRate"));
//			}
//			
//			//ӡ��˰��� FStampTaxAmt
//			b = FDCTransmissionHelper.strToDouble(FStampTaxAmt);
//			if(b==0){
//				BigDecimal baAmount = FDCTransmissionHelper.strToBigDecimal(FOriginalAmount);
//				BigDecimal baTaxRate = FDCTransmissionHelper.strToBigDecimal(FStampTaxRate);
//				bd = baAmount.multiply(baTaxRate);
//			} 
//			else if (b < 0 || b > 100) {
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FStampTaxAmt"));
//			}
//			else{
//				bd = FDCTransmissionHelper.strToBigDecimal(FStampTaxAmt);
//			}
			
			//�γɷ�ʽ FContractSource
			String csouenum = FContractSource.trim();
			//�������õ��γɷ�ʽ  ��  ContractSourceID
			csinfo = ContractSourceFactory.getLocalInstance(ctx).getContractSourceCollection(this.getView("name", csouenum)).get(0);
			if(csinfo==null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"xcfszxtzbcz"));//�γɷ�ʽ��ϵͳ�в�����
			}
			
			if(csouenum.equals(getResource(ctx,"trust"))){
				csouenum = "TRUST";
			}else if(csouenum.equals(getResource(ctx,"invite"))){
				csouenum = "INVITE";
			}else if(csouenum.equals(getResource(ctx,"discuss"))){
				csouenum = "DISCUSS";
			}else if(csouenum.equals(getResource(ctx,"coop"))){
				csouenum = "COOP";
			}else{
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_Fcppenum"));
			}
			csenum = ContractSourceEnum.getEnum(csouenum);
			
			
			//�Ƿ���붯̬�ɱ� FIsCoseSplit
			FIsCoseSplit = FIsCoseSplit.trim();
			if(FIsCoseSplit==null || FIsCoseSplit.equals("")){
				FIsCoseSplit = "true";
			}else if(FIsCoseSplit.equals(getResource(ctx,"yes"))){
				FIsCoseSplit = "true";
			}else if(FIsCoseSplit.equals(getResource(ctx,"no"))){
				FIsCoseSplit = "false";
			}
			
			////�Ƿ�׹����Ϻ�ͬ FIsPartAMaterialCon
			FIsPartAMaterialCon = FIsPartAMaterialCon.trim();
			if(FIsPartAMaterialCon==null || FIsPartAMaterialCon.equals("")){
				FIsPartAMaterialCon = "false";
			}else if(FIsPartAMaterialCon.equals(getResource(ctx,"yes"))){
				FIsPartAMaterialCon = "true";
			}else if(FIsPartAMaterialCon.equals(getResource(ctx,"no"))){
				FIsPartAMaterialCon = "false";
			}
			
//			//�Ƿ��ѹ鵵 FIsArchived
//			FIsArchived = FIsArchived.trim();
//			if(FIsArchived==null || FIsArchived.equals("")){
//				FIsArchived = "false";
//			}else if(FIsArchived.equals(getResource(ctx,"yes"))){
//				FIsArchived = "true";
//			}else if(FIsArchived.equals(getResource(ctx,"no"))){
//				FIsArchived = "false";
//			}
			
			//�Ƶ��� FCreator_name_l2
			uinfo = UserFactory.getLocalInstance(ctx).getUserCollection(this.getView("number", FCreator_name_l2)).get(0);
			if(uinfo==null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FCreator_name_l2"));
			}
			
			//�Ƶ����� FCreateTime
			tt = Timestamp.valueOf(FCreateTime+" 00:00:00.0");
			
			//����� FAuditor_name_l2
			uinfo = UserFactory.getLocalInstance(ctx).getUserCollection(this.getView("number", FAuditor_name_l2)).get(0);
			if(!FAuditor_name_l2.trim().equals("")){
				if(uinfo==null){
					FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FAuditor_name_l2"));
				}
			}
			
			needDept = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(this.getView("number", FNeedDept_number)).get(0);
			if(needDept==null){
				FDCTransmissionHelper.isThrow("�����ű�����ϵͳ�в�����");
			}
			
			needPerson = PersonFactory.getLocalInstance(ctx).getPersonCollection(this.getView("number", FNeedPerson_number)).get(0);
			if(needPerson==null){
				FDCTransmissionHelper.isThrow("�����˱�����ϵͳ�в�����");
			}
			
			wfType = ContractWFTypeFactory.getLocalInstance(ctx).getContractWFTypeCollection(this.getView("name", FContractWFType_name_l2)).get(0);
			if(wfType == null){
				FDCTransmissionHelper.isThrow("��ͬ����������ϵͳ�в�����");
			}
			
			String orgTypeEnum = FOrgType.trim();
			if(orgTypeEnum.equals("����/��ҵ��/���й�˾-��Ŀ��")){
				orgTypeEnum = "ALLRANGE";
			}else if(orgTypeEnum.equals("����/��ҵ��/���й�˾")){
				orgTypeEnum = "BIGRANGE";
			}else if(orgTypeEnum.equals("��Ŀ��")){
				orgTypeEnum = "SMALLRANGE";
			}else{
				FDCTransmissionHelper.isThrow("�������̷�����֯������");
			}
			orgType = ContractTypeOrgTypeEnum.getEnum(orgTypeEnum);
			
			inviteType = InviteTypeFactory.getLocalInstance(ctx).getInviteTypeCollection(this.getView("name", FInviteType_name_l2)).get(0);
			createDept = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(this.getView("number", FCreateDept_number)).get(0);
			//����  ֵ
			info.setOrgUnit(fouinfo);//��֯����1
			info.setCurProject(cpjinfo);//������Ŀ������2
			//״̬3
			info.setState(enumValue);
				
			info.setContractType(ctinfo);//��ͬ���ͱ���4
			info.setNumber(FCodingNumber);//��ͬ���ݱ���5
			info.setCodingNumber(FCodingNumber);
			info.setName(FName);//��ͬ����6
//			info.setProgrammingContract(pcinfo);//��ܺ�Լ7
			info.setLandDeveloper(linfo);//�׷�����8
			info.setPartB(slinfo);//�ҷ�����9
			info.setPartC(slerinfo);//��������10
			//��ͬ����11 
			info.setContractPropert(cpenum);
			
			info.setSignDate(FDCTransmissionHelper.strToDate(FBizDate));//FSignDateǩԼ����12
			info.setCurrency(currinfo);// ���ֱ��� 13
		    info.setExRate(bgm);//����14,FExRate
			info.setRespDept(aouinfo);//���β��ű���15 FRespDept_number
			info.setRespPerson(pinfo);//����������16 FRespPerson_name_l2
			//������� 17FCostProperty
			info.setCostProperty(CostPropertyEnum.BASE_CONFIRM);
			
			info.setOriginalAmount(FDCTransmissionHelper.strToBigDecimal(FOriginalAmount));//ԭ�ҽ��18 FOriginalAmount
			info.setAmount(FDCTransmissionHelper.strToBigDecimal(FAmount));//��λ�ҽ��19 
			info.setGrtRate(FDCTransmissionHelper.strToBigDecimal(FGrtRate));//���޽����20 FGrtRate
			info.setGrtAmount(FDCTransmissionHelper.strToBigDecimal(FGrtAmount));//���޽���21 FGrtAmount
			info.setBizDate(FDCTransmissionHelper.strToDate(FBizDate));//FBizDate22 ҵ������
			
			info.setPayPercForWarn(new BigDecimal("85"));
			info.setChgPercForWarn(new BigDecimal("5"));
			
//			info.setPayPercForWarn(FDCTransmissionHelper.strToBigDecimal(FPayPercForWarn));//������ʾ����23 FPayPercForWarn
//		    info.setChgPercForWarn(FDCTransmissionHelper.strToBigDecimal(FPayPercForWarn));//�����ʾ����24 FChgPercForWarn
//			info.setPayScale(FDCTransmissionHelper.strToBigDecimal(FPayScale));//���ȿ�����25 payScale
//			info.setOverRate(FDCTransmissionHelper.strToDouble(FOverRate));//������ʾ����26 FOverRate
//			info.setStampTaxRate(FDCTransmissionHelper.strToBigDecimal(FStampTaxRate));//ӡ��˰��27 FStampTaxRate
//			info.setStampTaxAmt(bd);//ӡ��˰���28 FStampTaxAmt
			
			//�γɷ�ʽ29  FContractSource
			info.setContractSource(csenum);
			info.setContractSourceId(csinfo);
			
			info.setIsCoseSplit(FDCTransmissionHelper.strToBoolean(FIsCoseSplit, "true"));//�Ƿ���붯̬�ɱ�30 FIsCoseSplit
			info.setIsPartAMaterialCon(FDCTransmissionHelper.strToBoolean(FIsPartAMaterialCon, "true"));//�Ƿ�׹����Ϻ�ͬ31 FIsPartAMaterialCon
//			info.setIsArchived(FDCTransmissionHelper.strToBoolean(FIsArchived, "true"));//�Ƿ��ѹ鵵32 FIsArchived
			
			info.setCreator(uinfo);//�Ƶ���33 FCreator_name_l2
			info.setCreateTime(tt);//�Ƶ�����34 FCreateTime
			info.setAuditor(ainfo);//�����35 FAuditor_name_l2
			info.setAuditTime(FDCTransmissionHelper.strToDate(FAuditTime));//�������36 FAuditTime
			
			ContractBailInfo bailInfo = new ContractBailInfo();//��ͬ��������id �ڽ�����¼���ͬ��ʱ����Ѿ��Զ�����
			BOSUuid id = BOSUuid.create(bailInfo.getBOSType());//��������Ҳ��Ҫ�Զ����ɾ��������id
			bailInfo.setId(id);
			info.setBail(bailInfo);

			info.setNeedDept(needDept);
			info.setNeedPerson(needPerson);
			info.setContractWFType(wfType);
			info.setOrgType(orgType);
			info.setInviteType(inviteType);
			info.setCreateDept(createDept);
		}catch (BOSException e) {
			FDCTransmissionHelper.isThrow("",e.toString());
		}catch (EASBizException e) {
			e.printStackTrace();
		}

		return info;
	}
	
	
	//�ύ
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		if (coreBaseInfo == null || coreBaseInfo instanceof ContractBillInfo == false) {
			return;
		}	
		try {
			ContractBillInfo billBase = (ContractBillInfo) coreBaseInfo;
			ContractBailInfo bailInfo = billBase.getBail();
			ContractBailFactory.getLocalInstance(ctx).save(bailInfo);//����bail��id
			getController(ctx).save(coreBaseInfo);  
		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex.getCause());
		}
	}
	
	
	/**
	 * �ж� �����͵������Ƿ�Ϸ�  �������Ƿ�Ϊ�ա� ���ݵĳ����ǲ��ǹ���   �ķ���  
	 * name �������� value ֵ  b�Ƿ���� iv����λ fvС��λ
	 */
	private void bdValueFormat(String name,String value,boolean b,int iv,int fv) throws TaskExternalException{
		if(null != value && !"".equals(value.trim()) ){
			if(!value.matches("([1-9]\\d{0,"+iv+"}(.)\\d{0,"+fv+"})|(0(.)\\d{0,"+fv+"})|([0-9]\\d{0,"+iv+"})")){
				FDCTransmissionHelper.isThrow(name,getResource(contx,"mustB")+iv+getResource(contx,"iNumber")+fv+getResource(contx,"fNumber") );
    		}
		}else{
			if(b){//Ϊ�յ����  �����Ǳ�����ֶ�
				FDCTransmissionHelper.isThrow(name,getResource(contx,"Import_CanNotNull"));//����Ϊ��
			}
		}
	}
	
	

	//������ͼ
	private EntityViewInfo getView(String sqlcolnum,Object item){
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(sqlcolnum,item,CompareType.EQUALS));
        view.setFilter(filter);
		return view;	
	}
	/**
	 * �õ���Դ�ļ�
	 * @author ֣��Ԫ
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
