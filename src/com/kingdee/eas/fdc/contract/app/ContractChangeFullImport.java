package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.cssp.Supplier;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.ChangeReasonFactory;
import com.kingdee.eas.fdc.basedata.ChangeReasonInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonFactory;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo;
import com.kingdee.eas.fdc.basedata.JobTypeFactory;
import com.kingdee.eas.fdc.basedata.JobTypeInfo;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeFactory;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo;
import com.kingdee.eas.fdc.basedata.VisaTypeFactory;
import com.kingdee.eas.fdc.basedata.VisaTypeInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum;
import com.kingdee.eas.fdc.contract.ContractBailFactory;
import com.kingdee.eas.fdc.contract.ContractBailInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.GraphCountEnum;
import com.kingdee.eas.fdc.contract.OfferEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.util.DateTimeUtils;

public class ContractChangeFullImport extends AbstractDataTransmission {

	private static String resource = "com.kingdee.eas.fdc.contract.ContractResource";
	private Context ctxt = null;
	
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = ContractChangeBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	public CoreBaseInfo transmit(Hashtable htable, Context ctx)
			throws TaskExternalException {
		this.ctxt = ctx;
		ContractChangeBillInfo info = new ContractChangeBillInfo();
		//��excel��ȡֵ
		String FOrgUnit_number = getFieldValue(htable,"FOrgUnit_number");//��֯������
		String FCurProject_longNumber = getFieldValue(htable, "FCurProject_longNumber");//������Ŀ������
		String FContractBillNumber = getFieldValue(htable,"FContractBillNumber");//��ͬ����
		String FNumber = getFieldValue(htable,"FNumber");//���ݱ��
		String FName = getFieldValue(htable,"FName");//��������
		String FState = getFieldValue(htable,"FState");//����״̬
		String FBizDate = getFieldValue(htable,"FBizDate");//ҵ������
		
		String FChangeType_name_l2 = getFieldValue(htable,"FChangeType_name_l2");//�������
		String FChangeReason_name_l2 = getFieldValue(htable,"FChangeReason_name_l2");//ǩ֤ԭ��
		String FJobType_name_l2 = getFieldValue(htable,"FJobType_name_l2");//�а�����
		String FChangeSubject = getFieldValue(htable,"FChangeSubject");//�������
		String FSpecialtyType_name_l2 = getFieldValue(htable,"FSpecialtyType_name_l2");//רҵ����
		String FConductDept_name_l2 = getFieldValue(htable,"FConductDept_name_l2");//�������
		String FUrgentDegree = getFieldValue(htable,"FUrgentDegree");//�����̶�
		String FGraphCount = getFieldValue(htable,"FGraphCount");//��ͼ���
		String FConductUnit_name_l2 = getFieldValue(htable,"FConductUnit_name_l2");//�����λ
		String FOffer = getFieldValue(htable,"FOffer");//�����
		String FConstrUnit_name_l2 = getFieldValue(htable,"FConstrUnit_name_l2");//ʩ����λ
		String FConstrSite = getFieldValue(htable,"FConstrSite");//ʩ����λ
		String FVisaType_name_l2 = getFieldValue(htable,"FVisaType_name_l2");//ǩ֤����
		String FIsImportChange = getFieldValue(htable,"FIsImportChange");//�Ƿ��ش���
		String FMainSupp_name_l2 = getFieldValue(htable,"FMainSupp_name_l2");//���͵�λ
		
		String FIsDeduct = getFieldValue(htable,"FIsDeduct");//�Ƿ����οۿλ
		String FDeductAmount = getFieldValue(htable,"FDeductAmount");//�ۿ���
		String FDeductReason = getFieldValue(htable,"FDeductReason");//�ۿ�ԭ��
		String FOriginalAmount = getFieldValue(htable,"FOriginalAmount");//Ԥ��ԭ�ҽ��
		String FOriBalanceAmount = getFieldValue(htable,"FOriBalanceAmount");//������ԭ��
		String FIsSureChangeAmt = getFieldValue(htable,"FIsSureChangeAmt");//�Ƿ�ȷ�ϱ�����
		String FChangeAuditNumber = getFieldValue(htable,"FChangeAuditNumber");//�������ݺ�
		
		String FDisThisTime = getFieldValue(htable,"FDisThisTime");//����ִ��˵��
		String FImpleCondition = getFieldValue(htable,"FImpleCondition");//δִ��˵��
		String FInvalidCostReason_name_l2 = getFieldValue(htable,"FInvalidCostReason_name_l2");//��Ч�ɱ�ԭ��
		String FCostNouse = getFieldValue(htable,"FCostNouse");//��Ч�ɱ����
		String FOriginalContactNum = getFieldValue(htable,"FOriginalContactNum");//ԭʼ��ϵ����
		String FConstructPrice = getFieldValue(htable,"FConstructPrice");//ʩ����������
		
		String FCreator_name_l2 = getFieldValue(htable,"FCreator_name_l2");//������
		String FCreateTime = getFieldValue(htable,"FCreateTime");//����ʱ��
		String FAuditor_name_l2 = getFieldValue(htable,"FAuditor_name_l2");//�����
		String FAuditTime = getFieldValue(htable,"FAuditTime");//���ʱ��
		
		CurProjectInfo cpinfo = null;
		ContractBillInfo cbinfo = null;
		
		boolean bo = this.isLength(getResource(ctx,"gcxmcbm"), FCurProject_longNumber, "String", true, 40);//������Ŀ������
		
		try {
			cpinfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(getView("longnumber", FCurProject_longNumber.replace('.', '!'))).get(0);
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		//�ɱ���������   Ϊ��ȡ����֯����  ȡ������Ŀ�������Ӧ�ɱ���������
		if(this.isLength(getResource(ctx,"zzcbm"), FOrgUnit_number, "String", false, 40)){//��֯����
			FullOrgUnitInfo objInfo = null;
			if (cpinfo == null) {// ������Ŀ��������ϵͳ�в�����
				this.isThrow("", getResource(ctx, "gcxmcbmzxtzbcz"));
			}
			String ccouid = cpinfo.getCostCenter().getId().toString();
			CostCenterOrgUnitInfo ccouinfo = null;
			try {// �ɱ����Ķ���
				ccouinfo = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(this.getView("id", ccouid)).get(0);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			String costCenterLongNumber = ccouinfo.getLongNumber();
			if (FOrgUnit_number.trim().equals("") || FOrgUnit_number == null) {// Ϊ�յ�ʱ��
				// ǿת���̶���-����֯����
				objInfo = cpinfo.getCostCenter().castToFullOrgUnitInfo();
			} else {// ��Ϊ�յ�ʱ��
				if (!FOrgUnit_number.replace('.', '!').equals(costCenterLongNumber)) {// ��д����
					this.isThrow(getResource(ctx, "Import_FOrgUnitLongNumberNotE"), "");
				} else {// û����д���� //ǿת���̶���-����֯����
					objInfo = cpinfo.getCostCenter().castToFullOrgUnitInfo();
				}
			}
		    info.setOrgUnit(objInfo);
	    }
		
		if (bo) {// ������Ŀ������
			if(cpinfo!=null){
				info.setCurProject(cpinfo);
				// ��������Ŀ���ַ��� ��Ȼϵͳ���ֿ�����
				info.setCurProjectName(cpinfo.getName());
			} else {// ������Ŀ��������ϵͳ�в�����
				this.isThrow("", getResource(ctx, "gcxmcbmzxtzbcz"));
			}
		}
		if(this.isLength(getResource(ctx, "gcxmcbmzxtzbcz"), FContractBillNumber, "String", true, 40)){//��ͬ����
			try {
				cbinfo = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(getView("Number", FContractBillNumber)).get(0);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			if(cbinfo!=null){
				info.setContractBillNumber(FContractBillNumber);
				info.setContractBill(cbinfo);
				// ����ұ� ��Ȼ�ֿ�����
				info.setCurrency(cbinfo.getCurrency());
			}else{
				this.isThrow("", getResource(ctx, "htbmzxtzbcz"));// ��ͬ���벻����
			}
		}
		if(this.isLength(getResource(ctx, "danjubianhao"), FNumber, "String", true, 80)){//���ݱ��
			ContractChangeBillInfo ccbinfo = null;
			try {
				ccbinfo = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(getView("Number", FNumber)).get(0);
			} catch (BOSException e) {
				e.printStackTrace();
			} 
			if(ccbinfo!=null){
				this.isThrow("",getResource(ctx, "CanNotRepeat"));
			}else{
				info.setNumber(FNumber);
			}
		}
		if(this.isLength(getResource(ctx, "danjumingcheng"), FName, "String", true, 80)){//��������
			ContractChangeBillInfo ccbinfo = null;
			try {
				ccbinfo = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(getView("name", FName)).get(0);	
			} catch (BOSException e) {
				e.printStackTrace();
			} 
			if (ccbinfo != null) {// ���������ظ�
				this.isThrow("", getResource(ctx, "djmccf"));
			}else{
				info.setName(FName);
			}
		}
		if(this.isLength(getResource(ctx, "danjuzhaungtai"), FState, "String", false, 40)){//����״̬
			String eunms = FState.trim();
			//״̬���壺���桢�ύ�������������·�����ǩ֤���ѽ��㡣Ĭ��������
			//5CANCEL-----------��ֹ; 4AUDITTED-----------������; 2SUBMITTED-----------���ύ; 
			//9INVALID-----------����; 8VISA-----------��ǩ֤; 3AUDITTING-----------������; 
			//7ANNOUNCE-----------���·�; 1SAVED-----------����;
			if(eunms.equals(getResource(ctx, "saved"))){
				eunms = "1SAVED";
			}else if(eunms.equals(getResource(ctx, "submitted"))){
				eunms = "2SUBMITTED";
			}else if(eunms.equals(getResource(ctx, "announce"))){
				eunms = "7ANNOUNCE";
			}else if(eunms.equals(getResource(ctx, "visa"))){
				eunms = "8VISA";
			}else{
				eunms = "4AUDITTED";
			}
			
			FDCBillStateEnum enumValue = FDCBillStateEnum.getEnum(eunms);
			if(enumValue!=null){
				info.setState(enumValue);
			}else{
				this.isThrow("",getResource(ctx, "InputRightFState"));
			}		
		}
		if(this.isLength(getResource(ctx, "biangengleixing"), FChangeType_name_l2, "String", true, 40)){//�������
				ChangeTypeInfo objInfo = null;
				try {
					objInfo = ChangeTypeFactory.getLocalInstance(ctx).getChangeTypeCollection(getView("name", FChangeType_name_l2)).get(0);
			    } catch (BOSException e) {
				e.printStackTrace();
			}
				if (objInfo == null) {
					this.isThrow("", getResource(ctx, "bglxzxtzbcz"));
				}
				info.setChangeType(objInfo);
				// �������������� ��Ȼ�ֿ���
				info.setChangeTypeName(objInfo.getName());
		}
		if(this.isLength(getResource(ctx, "yewuriqi"), FBizDate, "Date", true, 40)){//ҵ������
			info.setBizDate(this.strToDate(FBizDate));
		}
		if(this.isLength(getResource(ctx, "qianzhengyuanyin"), FChangeReason_name_l2, "String", false, 40)){//ǩ֤ԭ��
			ChangeReasonInfo objInfo = null;
			try {
				objInfo = ChangeReasonFactory.getLocalInstance(ctx).getChangeReasonCollection(getView("name", FChangeReason_name_l2)).get(0);
			    info.setChangeReason(objInfo);
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if(this.isLength(getResource(ctx, "chengbaoleixing"), FJobType_name_l2, "String", false, 40)){//�а�����
			JobTypeInfo objInfo = null;
			try {
				objInfo = JobTypeFactory.getLocalInstance(ctx).getJobTypeCollection(getView("name", FJobType_name_l2)).get(0);
                info.setJobType(objInfo);
                if (objInfo != null) {// ����а����� ���� ��Ȼ �ֿ���
					info.setJobTypeName(objInfo.getName());
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if(this.isLength(getResource(ctx, "biangengzhuti"), FChangeSubject, "String", false, 40)){//�������
			info.setChangeSubject(FChangeSubject);
		}
		if(this.isLength(getResource(ctx, "zhuanyeleixing"), FSpecialtyType_name_l2, "String", true, 40)){//רҵ����
			SpecialtyTypeInfo objInfo = null;
			try {
				objInfo = SpecialtyTypeFactory.getLocalInstance(ctx).getSpecialtyTypeCollection(getView("name", FSpecialtyType_name_l2)).get(0);
				info.setSpecialtyType(objInfo);
				if (objInfo != null) {// ����רҵ�������� ��Ȼ�ֿ�����
					info.setSpecialtyTypeName(objInfo.getName());
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if(this.isLength(getResource(ctx, "tcbm"), FConductDept_name_l2, "String", true, 40)){//�������
			AdminOrgUnitInfo objInfo = null;
			try {			
				objInfo = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(getView("name", FConductDept_name_l2)).get(0);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(objInfo!=null){
				info.setConductDept(objInfo);
			} else {// ���������ϵͳ�в�����
				this.isThrow(getResource(ctx, "tcbm"), getResource(ctx, "Import_NOTNULL"));
			}
		}
		if(this.isLength(getResource(ctx, "jingjichengdu"),FUrgentDegree,"String",false,40)){//�����̶�
			String eunms = FUrgentDegree.trim();
			if (eunms.equals(getResource(ctx, "urgent"))) {
				eunms = "2urgent";
			}else{
				eunms = "1normal";
			}
			
			ChangeUrgentDegreeEnum enumValue = ChangeUrgentDegreeEnum.getEnum(eunms);
			if(enumValue!=null){
				info.setUrgentDegree(enumValue);
			}else{
				this.isThrow("",getResource(ctx, "InputRightUrgentDegree"));
			}
		}
		if(this.isLength(getResource(ctx, "futuqingkuang"),FGraphCount,"String",false,40)){//��ͼ���
			String eunms = FGraphCount.trim();
			if (eunms.equals(getResource(ctx, "electfile"))) {
				eunms = "1ElectFile";
			}else if(eunms.equals(getResource(ctx, "paperfile"))){
				eunms = "2PaperFile";
			}else{
				eunms = "3NoFile";
			}
		
			GraphCountEnum enumValue = GraphCountEnum.getEnum(eunms);
			if(enumValue!=null){
				info.setGraphCount(enumValue);
			}else{
				this.isThrow("",getResource(ctx, "InputRightGraphCount"));
			}
		}
		if(this.isLength(getResource(ctx, "tichudanwei"), FConductUnit_name_l2, "String", false, 40)){//�����λ
			SupplierInfo objInfo = null;
			try {			
				objInfo = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(getView("name", FConductUnit_name_l2)).get(0);
				info.setConductUnit(objInfo);
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if(this.isLength(getResource(ctx, "tichufang"), FOffer, "String", false, 40)){//�����
			String eunms = FOffer.trim();
			if (eunms.equals(getResource(ctx, "constrparty"))) {
				eunms = "CONSTRPARTY";
			}else if(eunms.equals(getResource(ctx, "designcom"))){
				eunms = "DESIGNCOM";
			}else if(eunms.equals(getResource(ctx, "superviser"))){
				eunms = "SUPERVISER";
			}else{ 
				eunms = "SELFCOM";//��˾
			}
																																																																																																				
			OfferEnum enumValue = OfferEnum.getEnum(eunms);
			if(enumValue!=null){
				info.setOffer(enumValue);
			}else{
				this.isThrow("",getResource(ctx, "InputRightFOffer"));
			}
		}
		if(this.isLength(getResource(ctx, "shigongdanwei"), FConstrUnit_name_l2, "String", false, 40)){//ʩ����λ
			try {	
				SupplierInfo objInfo = null;
				objInfo = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(getView("name", FConstrUnit_name_l2)).get(0);
			    info.setConstrUnit(objInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(this.isLength(getResource(ctx, "shigongbuwei"), FConstrSite, "String", false, 300)){//ʩ����λ
			info.setConstrSite(FConstrSite);	
		}
		if(this.isLength(getResource(ctx, ""), FVisaType_name_l2, "String", true, 40)){//ǩ֤����
			VisaTypeInfo objInfo = null;
			try {
				objInfo = VisaTypeFactory.getLocalInstance(ctx).getVisaTypeCollection(getView("name", FVisaType_name_l2)).get(0);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			if(objInfo!=null){
				info.setVisaType(objInfo);
			} else {// ǩ֤������ϵͳ�в�����
				this.isThrow("", getResource(ctx, "qzlxzxtzbcz"));
			}
		}
		if(this.isBoolean(getResource(ctx, "shifouzhongdabiangeng"),FIsImportChange,false,getResource(ctx, "yes"),getResource(ctx, "no"),-1)){  //�Ƿ��ش���
			FIsImportChange = FIsImportChange.trim();
			if(FIsImportChange.equals("true")){
				FIsImportChange = "true";
			}else if(FIsImportChange.equals("false")){
				FIsImportChange = "false";
			}else if(FIsImportChange.equals("") || FIsImportChange==null){
				FIsImportChange = "false";
			}
			
			info.setIsImportChange(this.strToBoolean(FIsImportChange,"true"));
		}
		if(this.isLength(getResource(ctx, "zhusongdanwei"), FMainSupp_name_l2, "String", true, 40)){//true//���͵�λ
			SupplierInfo objInfo = null;
			try {
				objInfo = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(getView("name", FMainSupp_name_l2)).get(0);
			} catch (BOSException e) {
				e.printStackTrace();
			} 
			if(objInfo!=null){
				info.setMainSupp(objInfo);
			} else {// ���͵�λ��ϵͳ�в�����
				this.isThrow("", getResource(ctx, "zsdwzxtzbcz"));
			}
		}
		if(this.isBoolean(getResource(ctx, "shifouzerenkoukuandanwei"),FIsDeduct,false,getResource(ctx, "yes"),getResource(ctx, "no"),-1)){//�Ƿ����οۿλ
			FIsDeduct = FIsDeduct.trim();
			if (FIsDeduct.equals(getResource(ctx, "yes"))) {
				FIsDeduct = "true";
			} else {
				FIsDeduct = "false";
			}
			info.setIsDeduct(this.strToBoolean(FIsDeduct,"true"));
		}
		if(this.isLength(getResource(ctx, "koukuanjine"), FDeductAmount, "Double", false, -1)){//�ۿ���
			if(FDeductAmount.trim().equals("") || FDeductAmount==null){
				FDeductAmount = "0";
			}
			info.setDeductAmount(this.strToBigDecimal(FDeductAmount));
		}
		if(this.isLength(getResource(ctx, "koukuanyuanyin"), FDeductReason, "String", false, 40)){//�ۿ�ԭ��
			info.setDeductReason(FDeductReason);
		}
		if(this.isLength(getResource(ctx, "yusuanyuanbijine"), FOriginalAmount, "Double", true, -1)){//Ԥ��ԭ�ҽ��
			info.setOriginalAmount(this.strToBigDecimal(FOriginalAmount));
		}
		if(this.isLength(getResource(ctx, "jiesuanyuanbijine"), FOriBalanceAmount, "Double", false, -1)){//������ԭ��
			info.setOriBalanceAmount(this.strToBigDecimal(FOriBalanceAmount));
		}
		if(this.isBoolean(getResource(ctx, "shifouquerenbiangengjine"),FIsSureChangeAmt,false,getResource(ctx, "yes"),getResource(ctx, "no"),-1)){//�Ƿ�ȷ�ϱ�����
			FIsSureChangeAmt = FIsSureChangeAmt.trim();
			if (FIsSureChangeAmt.equals(getResource(ctx, "yes"))) {
				FIsSureChangeAmt = "true";
			} else {
				FIsSureChangeAmt = "false";
			}
			info.setIsSureChangeAmt(this.strToBoolean(FIsSureChangeAmt,"true"));
		}
		if(this.isLength(getResource(ctx, "shenpidanjuhao"), FChangeAuditNumber, "String", false, 40)){//�������ݺ�
			info.setChangeAuditNumber(FChangeAuditNumber);
			ChangeAuditBillInfo objinfo = null;
			if (!FChangeAuditNumber.equals("")) {
				try {
					objinfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillCollection(getView("number", FChangeAuditNumber))
							.get(0);
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
			if (objinfo != null) {
				// ���������������� ��Ȼ�����Ͽ�����
				info.setChangeAudit(objinfo);
			}	
		}

		if(this.isLength("bencizhixingqingkuang", FDisThisTime, "String", false, 40)){//����ִ��˵��
			info.setDisThisTime(FDisThisTime);
		}
		if(this.isLength(getResource(ctx, "weizhixingqingkuang"), FImpleCondition, "String", false, 40)){//δִ��˵��
			info.setImpleCondition(FImpleCondition);
		}
		if(this.isLength(getResource(ctx, "wuxiaochengbenyuanyin"), FInvalidCostReason_name_l2, "String", false, 40)){//��Ч�ɱ�ԭ��
			try {
				InvalidCostReasonInfo objinfo = null;
				objinfo = InvalidCostReasonFactory.getLocalInstance(ctx).getInvalidCostReasonCollection(getView("name", FInvalidCostReason_name_l2)).get(0);
				info.setInvalidCostReason(objinfo);
			} catch (BOSException e) {
				e.printStackTrace();
			} 
		}
		if(this.isLength(getResource(ctx, "wuxiaochengbenjine"), FCostNouse, "Double", false, -1)){	//��Ч�ɱ����
			if(FCostNouse.trim().equals("") || FCostNouse==null){
				FCostNouse = "0";
			}
		    info.setCostNouse(this.strToBigDecimal(FCostNouse));
		}
		if(this.isLength(getResource(ctx, "yuanxilianxidanhao"), FOriginalContactNum, "String", false, 50)){	//ԭʼ��ϵ����
			//�����������е�ԭʼ��ϵ���ű���һ�£�����ȡ����������е�ԭʼ��ϵ����
			//��ͨ����ͬcbinfo��id �ڱ����������¼ChangeSupplierEntryInfo ���õ���¼����Ϣ    Ȼ���õ���ϵ����
			ChangeSupplierEntryInfo cspinfo = null;   
			try {
				cspinfo = ChangeSupplierEntryFactory.getLocalInstance(ctx).getChangeSupplierEntryCollection(getView("contractBill", cbinfo.getId().toString())).get(0);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if (cspinfo != null) {
				info.setOriginalContactNum(cspinfo.getOriginalContactNum());
			}
			
		}
		if(this.isLength(getResource(ctx, "shigongfangbaoshenjine"), FConstructPrice, "Double", false, -1)){//ʩ����������
			if(FConstructPrice.trim().equals("") || FConstructPrice==null){
				FConstructPrice = "0";
			}
		    info.setConstructPrice(this.strToBigDecimal(FConstructPrice));
		}
		if(this.isLength(getResource(ctx, "zhidanrbianma"), FCreator_name_l2, "String", true, 40)){//�Ƶ��˱���
			UserInfo objinfo = null;
			try {
				objinfo = UserFactory.getLocalInstance(ctx).getUserCollection(getView("number", FCreator_name_l2)).get(0);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(objinfo==null){
				this.isThrow(getResource(ctx, "Import_fCreatorNameL21"), getResource(ctx, "Import_NOTNULL"));
			}
			info.setCreator(objinfo);
		}
		if(this.isLength(getResource(ctx, "zhidanriqi"), FCreateTime, "Date", true, -1)){	//�Ƶ�����
			Timestamp tt = Timestamp.valueOf(FCreateTime+" 00:00:00.0");
			info.setCreateTime(tt);
			// ����������� ����������ʾ
			info.setConductTime(this.strToDate(FCreateTime));// ������ں��Ƶ��������
		}
		if (FState.equals(getResource(ctx, "auditted")) || FState.equals(getResource(ctx, "announce")) || FState.equals(getResource(ctx, "visa")) || FState.equals("")) {
			if(this.isLength(getResource(ctx, "shenherbianma"), FAuditor_name_l2, "String", true, 40)){//����˱���	
				UserInfo objinfo = null;
				try {
					objinfo = UserFactory.getLocalInstance(ctx).getUserCollection(getView("number", FAuditor_name_l2)).get(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(objinfo==null){
					this.isThrow(getResource(ctx, "Import_fAuditorNameL21"), getResource(ctx, "Import_NOTNULL"));
				}
				info.setAuditor(objinfo);
			}
			if(this.isLength(getResource(ctx, "shenheshijian"), FAuditTime, "Date", true, -1)){//���ʱ��	
				info.setAuditTime(this.strToDate(FAuditTime));
			}
		}else if(FState.equals(getResource(ctx, "submitted"))||FState.equals(getResource(ctx, "saved"))){
			this.isLength(getResource(ctx, "shenherbianma"), FAuditor_name_l2, "String", false, 40);//����˱���
			this.isLength(getResource(ctx, "shenheshijian"), FAuditTime, "Date", false, -1);//���ʱ��
		}
		
		
		
		return info;
	}
	

	/**
	 * ��Hashtable����ȡ�ֶ�ֵ��
	 */
	protected String getFieldValue(Hashtable htable, String key) {
		String str = ((String) ((DataToken) htable.get(key)).data).trim();
		if(str==null || str.equals("")){
		    str = "";	
		}
		return str;
	}
	
	/**
	 * �ж�ȡ�������ĸ�ʽ�Ƿ�Ϸ� �� �Ƿ�Ϊ�ա� �����ǲ��ǹ���   �ķ���  
	 * 1.��������   ��Ϊ String�ַ�  Double����  Data����
	 * 2.�����Ϊ�յ��ֶ���Ҫ ��ʾ�û�
	 * 3.���ȳ���Ҳ������ʾ�û� 
	 * 
	 * chinaName-�ֶε���������         
	 * value-�ֶε�����     
	 * dataFormat-�ֶε���������
	 * b ����Ҫ�ж�Ϊ������ֶ�
	 * length-�ֶ����ĳ���    -1��ʱ���ж�         
	 */
    private boolean isLength(String chinaName,String value,String dataFormat,boolean b,int length) throws TaskExternalException{
    	boolean isTrue = false ;
    	
    	if(dataFormat.equals("String")){//�ַ������
    		if(null != value && !"".equals(value.trim())){
    			if(value.trim().length() <= length){
    				isTrue = true ;
    			}else{
					this.isThrow(chinaName,getResource(ctxt, "Import_NumberCanNotsurpass")+length+getResource(ctxt, "Char"));
    			}
    		}else{
    			if(b){//Ϊ�յ����  �����Ǳ�����ֶ�
    				this.isThrow(chinaName,getResource(ctxt, "Import_CanNotNull"));	
    			}else{
    				isTrue = true ;
    			}
    		}
    		
    	}else if(dataFormat.equals("Double")){//���ݵ����
			if(null != value && !"".equals(value.trim()) ){
				if(value.matches("([1-9]\\d{0,16}(.)\\d{0,2})|(0(.)\\d{0,2})")){
        			isTrue = true ;
        		}else{
        			this.isThrow(chinaName,getResource(ctxt, "Import_MustIntegerOrDecimal"));
        		}
			}else{
				if(b){//Ϊ�յ����  �����Ǳ�����ֶ�
    				this.isThrow(chinaName,getResource(ctxt, "Import_CanNotNull"));	
    			}else{
    				isTrue = true ;
    			}
			}
	
    	}else if(dataFormat.equals("Date")){//���ڵ���� 
    		if(null != value && !"".equals(value.trim()) ){
				if(value.matches("([1-9]\\d{3})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])")){
					String[] strlen = value.split("-");//�����ַ���  ȡ��������
					int yearInt = Integer.parseInt(strlen[0]);
					int monthInt = Integer.parseInt(strlen[1]);
					int dayInt = Integer.parseInt(strlen[2]);
					if((yearInt%400==0 || (yearInt%4==0 && yearInt%100!=0)) && monthInt==2 && dayInt>=30 ){//���������
						this.isThrow(chinaName+getResource(ctxt, "writteddate")+value+getResource(ctxt, "isnotdate"),getResource(ctxt, "month3")+dayInt+getResource(ctxt, "day"));
					}else if(yearInt%4!=0 && monthInt==2 && dayInt>=29){//��������
						this.isThrow(chinaName+getResource(ctxt, "writteddate")+value+getResource(ctxt, "isnotdate"),getResource(ctxt, "month2")+dayInt+getResource(ctxt, "day"));
					}
					if((monthInt==4 || monthInt==6 || monthInt==9 || monthInt==11) && dayInt==31){
						this.isThrow(chinaName+getResource(ctxt, "writteddate")+value+getResource(ctxt, "isnotdate"),monthInt+getResource(ctxt, "month1")+dayInt+getResource(ctxt, "day"));
					}
					
        			isTrue = true ;
        		}else{
        			this.isThrow(chinaName,getResource(ctxt, "Import_RiQiGeShi"));
        		}

			}else{
				if(b){//Ϊ�յ����  �����Ǳ�����ֶ�
    				this.isThrow(chinaName,getResource(ctxt, "Import_CanNotNull"));	
    			}else{
    				isTrue = true ;
    			}
			}
    	}
    	
		return isTrue ;
	}
    
    /**
     * �ж�boolean ���͵�ֵ
     * �������֣�ֵ���Ƿ���ture��ֵ��false��ֵ  �жϵĳ���
     * �жϵĳ���Ϊ-1��ʱ�� ����Ҫ���ж��ǲ��ǳ��ȳ�����
     */
    private boolean isBoolean(String chinaName,String value,boolean b,String tValue,String fValue,int length) throws TaskExternalException{

    	boolean isboolean = false;
        
		//��д��ģ����  ������ֵ
		if(value.trim().equals(tValue) || value.trim().equals(fValue)){
			if(length==-1){
				isboolean = true;	
			}else{
			    if(value.length()>length){
			    	this.isThrow(chinaName,getResource(ctxt, "Import_NumberCanNotsurpass")+length+getResource(ctxt, "Char"));	
			    }else{
			    	isboolean = true;	
			    }	
			}
	    }else if(value.trim().equals("") || value==null){
	    	isboolean = true;	
	    }else{//��д�˸���Ŀ  �����Ǹ�����ֵ
	    	this.isThrow(chinaName,getResource(ctxt, "only")+tValue+getResource(ctxt, "or")+fValue+"��");	
	    }
    
    	return isboolean;
    }
    
    /**
     * �׳��쳣�ķ���
     */
    private void isThrow(String value, String isAlert)throws TaskExternalException{
		throw new TaskExternalException(value.trim() + " " + isAlert);
	}
    
    /**
	 * ������Stringת��Ϊ���ڣ�String��ʽҪ��Ϊ��yyyy-mm-dd��ʱ����0
	 */
	private Date strToDate(String str) {
		String[] strArray = str.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(strArray[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(strArray[1])-1);
		cal.set(Calendar.DATE, Integer.parseInt(strArray[2]));

		return DateTimeUtils.truncateDate(cal.getTime());
	}
	
	/**
	 * ��String  ת����boolean ���� 
	 * str�ǽ�Ҫ����ת����ֵ�� trueValue��strΪ ���ʱ��  ��ֵ
	 */
	private boolean strToBoolean(String str,String trueValue){
		if(str.trim().equals(trueValue)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * ��String  ת����BigDecimal ���� 
	 * str�ǽ�Ҫ����ת����ֵ�� trueValue��strΪ ���ʱ��  ��ֵ
	 */
	private BigDecimal strToBigDecimal(String str){
		if(str==null || str.trim().equals(""))
			return FDCHelper.toBigDecimal(str, 2);
		else	
		    return FDCHelper.toBigDecimal(str);
	}
	/**
	 * �õ���Դ�ļ�
	 * @author �쿡
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
	//������ͼ
	private EntityViewInfo getView(String sqlcolnum,Object item){
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(sqlcolnum,item,CompareType.EQUALS));
        view.setFilter(filter);
		return view;	
	}
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		if (coreBaseInfo == null || coreBaseInfo instanceof ContractChangeBillInfo == false) {
			return;
		}	
		try {
			getController(ctx).save(coreBaseInfo);  
		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex.getCause());
		}
	}
}
