/**
 * 
 */
package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;

import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.fdc.basedata.ChangeReasonCollection;

import com.kingdee.eas.fdc.basedata.ChangeReasonFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonCollection;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonFactory;
import com.kingdee.eas.fdc.basedata.JobTypeCollection;
import com.kingdee.eas.fdc.basedata.JobTypeFactory;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeCollection;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillCollection;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum;
import com.kingdee.eas.fdc.contract.GraphCountEnum;
import com.kingdee.eas.fdc.contract.OfferEnum;
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
public class ChangeAuditFullTransmission extends AbstractDataTransmission {

	private static String resource = "com.kingdee.eas.fdc.contract.ContractTransmissionResource";

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return ChangeAuditBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}
	
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		
		ChangeAuditBillInfo changeAuditBill = new ChangeAuditBillInfo();
	
		//��֯����
		String fOrgUnitNumber = (String)((DataToken)hsData.get("FOrgUnit_number")).data;
		//������Ŀ����
		String fCurProjectLongNumber = (String)((DataToken)hsData.get("FCurProject_longNumber")).data;
		//���ݱ��
		String fNumber = (String)((DataToken)hsData.get("FNumber")).data;
		//����
		String fName = (String)((DataToken)hsData.get("FName")).data;
		//״̬
		String fState = (String)((DataToken)hsData.get("FState")).data;
		//ҵ������
		String fBizDate = (String)((DataToken)hsData.get("FBizDate")).data;
		//�������
		String fAuditTypeName = (String)((DataToken)hsData.get("FAuditType_name_l2")).data;
		//���ԭ��
		String fChangeReasonName = (String)((DataToken)hsData.get("FChangeReason_name_l2")).data;
		//�а�����
		String fJobTypeName = (String)((DataToken)hsData.get("FJobType_name_l2")).data;
		//�������
		String fChangeSubject = (String)((DataToken)hsData.get("FChangeSubject")).data;
		//�������
		String fConductDeptName = (String)((DataToken)hsData.get("FConductDept_name_l2")).data;
		//רҵ����
		String fSpecialtyTypeName = (String)((DataToken)hsData.get("FSpecialtyType_name_l2")).data;
		//�����̶�
		String fUrgentDegree = (String)((DataToken)hsData.get("FUrgentDegree")).data;
		//�����λ
		String fConductUnitName = (String)((DataToken)hsData.get("FConductUnit_name_l2")).data;
		//ʩ����λ
		String fConstrUnitName = (String)((DataToken)hsData.get("FConstrUnit_name_l2")).data;
		//��Ƶ�λ
		String fDesignUnitName = (String)((DataToken)hsData.get("FDesignUnit_name_l2")).data;
		//�����
		String fOffer = (String)((DataToken)hsData.get("FOffer")).data;
		//ʩ����λ
		String fConstrSite = (String)((DataToken)hsData.get("FConstrSite")).data;
		//ԭ��˵��
		String fReaDesc = (String)((DataToken)hsData.get("FReaDesc")).data;
		//��ͼ���
		String fGraphCount = (String)((DataToken)hsData.get("FGraphCount")).data;
		//�Ƿ��ش���
		String fIsImportChange = (String)((DataToken)hsData.get("FIsImportChange")).data;
		//�Ƿ������Ч�ɱ�
		String fIsNoUse = (String)((DataToken)hsData.get("FIsNoUse")).data;
		//�漰��Ч�ɱ��Ľ��
		String fCostNouse = (String)((DataToken)hsData.get("FCostNouse")).data;
		//��Ч�ɱ�ԭ��
		String fInvalidCostReasonName = (String)((DataToken)hsData.get("FInvalidCostReason_name_l2")).data;
		//������
		String fCreatorName = (String)((DataToken)hsData.get("FCreator_name_l2")).data;
		//����ʱ��
		String fCreateTime = (String)((DataToken)hsData.get("FCreateTime")).data;
		//�����
		String fAuditorName= (String)((DataToken) hsData.get("FAuditor_name_l2")).data;
		//����ʱ��
		String fAuditTime = (String)((DataToken)hsData.get("FAuditTime")).data;

		if (fOrgUnitNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "OrgUnitNumberIsLong"));		
		}
		
		if (StringUtils.isEmpty(fCurProjectLongNumber)) {
			throw new TaskExternalException(getResource(ctx, "ProjectNumberIsNull"));	
		}
		if (fCurProjectLongNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "ProjectNumberIsLong"));
		}
		
		if (StringUtils.isEmpty(fNumber)) {
			throw new TaskExternalException(getResource(ctx, "FNumberIsNull"));
		} else if (fNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "FNumberIsLong"));			
		} else {		
			changeAuditBill.setNumber(fNumber);
		}
		
		if (StringUtils.isEmpty(fName)) {
			throw new TaskExternalException(getResource(ctx, "FNameIsNull"));
		} else if (fName.length() > 80) {
			throw new TaskExternalException(getResource(ctx, "FNameIsLong"));
		} else {
			changeAuditBill.setName(fName);
		}
		 
		if(getResource(ctx,"FDCBillState_SAVED").equals(fState.trim())){
			changeAuditBill.setState(FDCBillStateEnum.SAVED);
			changeAuditBill.setChangeState(ChangeBillStateEnum.Saved);
		}else if(getResource(ctx,"FDCBillState_SUBMITTED").equals(fState.trim())){
			changeAuditBill.setState(FDCBillStateEnum.SUBMITTED);
			changeAuditBill.setChangeState(ChangeBillStateEnum.Submit);
		}else if(getResource(ctx,"FDCBillState_ANNOUNCE").equals(fState.trim())){
			changeAuditBill.setState(FDCBillStateEnum.ANNOUNCE);
			changeAuditBill.setChangeState(ChangeBillStateEnum.Announce);
		}else if(getResource(ctx,"FDCBillState_VISA").equals(fState.trim())){
			changeAuditBill.setState(FDCBillStateEnum.VISA);
			changeAuditBill.setChangeState(ChangeBillStateEnum.Visa);
		}else if(getResource(ctx,"FDCBillState_CANCEL").equals(fState.trim())){
			changeAuditBill.setState(FDCBillStateEnum.CANCEL);
			changeAuditBill.setChangeState(ChangeBillStateEnum.Auditting);
		}else if(StringUtils.isEmpty(fState) || 
				 getResource(ctx,"FDCBillState_AUDITTED").equals(fState.trim())){
			changeAuditBill.setState(FDCBillStateEnum.AUDITTED);
			changeAuditBill.setChangeState(ChangeBillStateEnum.Audit);
		}else{
			throw new TaskExternalException(getResource(ctx,"FDCBillStateOutSide"));
		}
		
		if (StringUtils.isEmpty(fBizDate)) {
			throw new TaskExternalException(getResource(ctx, "BizDateIsNull"));		
		}
		
		if (StringUtils.isEmpty(fAuditTypeName)) {
			throw new TaskExternalException(getResource(ctx, "AuditTypeIsNull"));	
		}
		
		if (StringUtils.isEmpty(fChangeReasonName)) {
			throw new TaskExternalException(getResource(ctx, "ChangeReasonIsNull"));	
		}
		
		if (StringUtils.isEmpty(fJobTypeName)) {
			throw new TaskExternalException(getResource(ctx, "JobTypeIsNull"));	
		}
		
		if (fChangeSubject.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "ChangeSubjectIsLong"));	
		}
		changeAuditBill.setChangeSubject(fChangeSubject);
		
		if (StringUtils.isEmpty(fConductDeptName)) {
			throw new TaskExternalException(getResource(ctx, "ConductDeptIsNull"));	
		}
		
		if (StringUtils.isEmpty(fSpecialtyTypeName)) {
			throw new TaskExternalException(getResource(ctx, "SpecialtyTypeIsNull"));	
		}
		
		if(getResource(ctx,"UrgentDegree_Urgent").equals(fUrgentDegree.trim())){
			changeAuditBill.setUrgentDegree(ChangeUrgentDegreeEnum.Urgent);
		}else {
			changeAuditBill.setUrgentDegree(ChangeUrgentDegreeEnum.Normal);
		}
//		else{
//			throw new TaskExternalException(getResource(ctx, "UrgentDegreeOutSide"));	
//		}
		
		if(getResource(ctx,"Offer_DesignCom").equals(fOffer.trim())){
			changeAuditBill.setOffer(OfferEnum.DESIGNCOM);
		}else{
			changeAuditBill.setOffer(OfferEnum.SELFCOM);
		}
//		else{
//			throw new TaskExternalException(getResource(ctx, "OfferOutside"));
//		}

					
		if (fConstrSite.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "ConstrSiteIsLong"));	
		}
		changeAuditBill.setConstrSite(fConstrSite);
			
		if (fReaDesc.length() > 40) {
			throw new TaskExternalException(getResource(ctx, "ReaDescIsLong"));	
		}
		changeAuditBill.setReaDesc(fReaDesc);
		
		if(getResource(ctx,"GraphCount_ElectFile").equals(fGraphCount.trim())){
			changeAuditBill.setGraphCount(GraphCountEnum.ElectFile);
		}else if(getResource(ctx,"GraphCount_PaperFile").equals(fGraphCount.trim())){
			changeAuditBill.setGraphCount(GraphCountEnum.PaperFile);
		}else{
			changeAuditBill.setGraphCount(GraphCountEnum.NoFile);
		}
//		else{
//			throw new TaskExternalException(getResource(ctx, "GraphCountOutside"));	
//		}
		
		if(getResource(ctx,"EntityStatusYes").equals(fIsImportChange.trim())){
			changeAuditBill.setIsImportChange(true);
		}else {
			changeAuditBill.setIsImportChange(false);
		}
		
		if(getResource(ctx,"EntityStatusYes").equals(fIsNoUse.trim())){
			changeAuditBill.setIsNoUse(true);
		}else {
			changeAuditBill.setIsNoUse(false);
		}
		
		if (changeAuditBill.isIsNoUse()){
			if (StringUtils.isEmpty(fCostNouse)) {
				// ������Ч�ɱ�ʱ����Ч�ɱ����Ϊ��¼�
				throw new TaskExternalException(getResource(ctx,"InvailCostIsNull"));
			}else if (fCostNouse.matches("^\\d+(\\.\\d+)?$")) {
				if (!fCostNouse.matches("^([1-9]\\d{0,16}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,16})||0$")) {
					// ��Ч�ɱ����¼�벻�Ϸ�
					throw new TaskExternalException(getResource(ctx, "CostNouseIsWrong"));
				}
				changeAuditBill.setCostNouse(new BigDecimal(fCostNouse));
			}else {
				throw new TaskExternalException(getResource(ctx, "CostNousePriceIsNotNumber"));	
			}
		}
		
		if (StringUtils.isEmpty(fCreatorName)) {
			throw new TaskExternalException(getResource(ctx, "CreatorIsNull"));	
		}
			
		if (StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException(getResource(ctx, "CreateTimeIsNull"));	
		}
						
		try {	
			/** ������Ŀ�����ѯ���� */
			FilterInfo curProjectInfo = new FilterInfo();
			curProjectInfo.getFilterItems().add(new FilterItemInfo("longNumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo curProjectInfoView = new EntityViewInfo();
			curProjectInfoView.setFilter(curProjectInfo);
			CurProjectCollection curProjectInfoColl = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curProjectInfoView);
			if (curProjectInfoColl != null && curProjectInfoColl.size() > 0) {
				changeAuditBill.setCurProject(curProjectInfoColl.get(0));
			} else {
				throw new TaskExternalException(getResource(ctx, "ProjectNumberNonexist"));
			}
			
			/** ��֯�����ѯ���� */
			FilterInfo fOrgUnitNumberInfo = new FilterInfo();
			EntityViewInfo fOrgUnitNumberInfoView = new EntityViewInfo();		
			if(!StringUtils.isEmpty(fOrgUnitNumber)){
				fOrgUnitNumberInfo.getFilterItems().add(new FilterItemInfo("longNumber", fOrgUnitNumber.replace('.', '!')));							
			}else{
				// ��֯����Ϊ��ʱ�����ӹ�����Ŀ��Ӧ�ĳɱ����Ļ�ȡ
				fOrgUnitNumberInfo.getFilterItems().add(new FilterItemInfo("id",changeAuditBill.getCurProject().getCostCenter().getId()));	
			}
			fOrgUnitNumberInfoView.setFilter(fOrgUnitNumberInfo);			
			FullOrgUnitCollection fOrgUnitNumberColl = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection(fOrgUnitNumberInfoView);
			if (fOrgUnitNumberColl != null && fOrgUnitNumberColl.size() > 0) {
				changeAuditBill.setOrgUnit(fOrgUnitNumberColl.get(0));
			} else {
				throw new TaskExternalException(getResource(ctx, "OrgUnitNumberNonexist"));
			}
			
			/** ��ⵥ�ݱ���Ƿ��ظ� */
			FilterInfo billNumberInfo = new FilterInfo();
			EntityViewInfo billNumberView = new EntityViewInfo();
			billNumberInfo.getFilterItems().add(new FilterItemInfo("number","select FNumber from T_CON_ChangeAuditBill where FNumber = '" + fNumber + "'",CompareType.INNER));
			billNumberView.setFilter(billNumberInfo);
			ChangeAuditBillCollection  billNumberColl = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillCollection(billNumberView);
			if (billNumberColl == null || billNumberColl.size() == 0) {
				changeAuditBill.setNumber(fNumber);
			} else {
				throw new TaskExternalException(getResource(ctx,"BillNumberExist"));
			}
			
			/** ��ⵥ�������Ƿ��ظ� */
			FilterInfo billNameInfo = new FilterInfo();
			EntityViewInfo billNameView = new EntityViewInfo();
			billNameInfo.getFilterItems().add(new FilterItemInfo("name","select FName from T_CON_ChangeAuditBill where FName = '" + fName + "'",CompareType.INNER));
			billNameView.setFilter(billNameInfo);
			ChangeAuditBillCollection billNameColl = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillCollection(billNameView);
		    if (billNameColl == null || billNameColl.size() == 0) {
				changeAuditBill.setName(fName);
			} else {
				throw new TaskExternalException(getResource(ctx,"BillNameExist"));
			}
			
			/** ������Ͳ�ѯ���� */
			FilterInfo changeTypeInfo = new FilterInfo();
			changeTypeInfo.getFilterItems().add(new FilterItemInfo("name", fAuditTypeName));
			EntityViewInfo changeTypeInfoView = new EntityViewInfo();
			changeTypeInfoView.setFilter(changeTypeInfo);
			ChangeTypeCollection changeTypeInfoColl = ChangeTypeFactory.getLocalInstance(ctx).getChangeTypeCollection(changeTypeInfoView);
			if (changeTypeInfoColl != null && changeTypeInfoColl.size() > 0) {
				changeAuditBill.setAuditType(changeTypeInfoColl.get(0));
			} else {
				throw new TaskExternalException(getResource(ctx, "AuditTypeNonexist"));
			}
			
			/** ���ԭ���ѯ���� */
			FilterInfo changeReasonInfo = new FilterInfo();
			changeReasonInfo.getFilterItems().add(new FilterItemInfo("number", fChangeReasonName));
			EntityViewInfo changeReasonInfoView = new EntityViewInfo();
			changeReasonInfoView.setFilter(changeReasonInfo);
			ChangeReasonCollection changeReasonInfoColl = ChangeReasonFactory.getLocalInstance(ctx).getChangeReasonCollection(changeReasonInfoView);
			if (changeReasonInfoColl != null && changeReasonInfoColl.size() > 0) {
				changeAuditBill.setChangeReason(changeReasonInfoColl.get(0));
			} else {
				throw new TaskExternalException(getResource(ctx, "ChangeReasonNonexist"));
			}
	
			/** �а����Ͳ�ѯ���� */
			FilterInfo jobTypeInfo = new FilterInfo();
			jobTypeInfo.getFilterItems().add(new FilterItemInfo("name",fJobTypeName));
			EntityViewInfo jobTypeInfoView = new EntityViewInfo();
			jobTypeInfoView.setFilter(jobTypeInfo);
			JobTypeCollection jobTypeInfoColl = JobTypeFactory.getLocalInstance(ctx).getJobTypeCollection(jobTypeInfoView);
			if (jobTypeInfoColl != null && jobTypeInfoColl.size() > 0) {
				changeAuditBill.setJobType(jobTypeInfoColl.get(0));
			} else {
				throw new TaskExternalException(getResource(ctx, "JobTypeNonexist"));
			}
	
			/** ������Ų�ѯ���� */
			FilterInfo conductDeptInfo = new FilterInfo();
			conductDeptInfo.getFilterItems().add(new FilterItemInfo("name",fConductDeptName));
			EntityViewInfo conductDeptInfoView = new EntityViewInfo();
			conductDeptInfoView.setFilter(conductDeptInfo);
			AdminOrgUnitCollection conductDeptInfoColl = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(conductDeptInfoView);
			if(conductDeptInfoColl != null && conductDeptInfoColl.size() > 0){
				changeAuditBill.setConductDept(conductDeptInfoColl.get(0));
			}else{
				throw new TaskExternalException(getResource(ctx, "ConductDeptNonexist"));
			}
			
			/** �����λ��ѯ���� */
			if(!OfferEnum.SELFCOM.equals(changeAuditBill.getOffer()) && 
					StringUtils.isEmpty(fConductUnitName)) {
					// �������Ϊ"��˾"ʱ,�����λΪ������
					throw new TaskExternalException(getResource(ctx, "ConductUnitIsNull"));				
			}
			FilterInfo conductUnitInfo = new FilterInfo();
			conductUnitInfo.getFilterItems().add(new FilterItemInfo("name",fConductUnitName));
			EntityViewInfo conductUnitInfoView = new EntityViewInfo();
			conductUnitInfoView.setFilter(conductUnitInfo);
			SupplierCollection supplierColl = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(conductUnitInfoView);
			if(supplierColl != null && supplierColl.size() > 0){
				changeAuditBill.setConductUnit(supplierColl.get(0));
			}
//			else{
//				throw new TaskExternalException(getResource(ctx, "ConductUnitNonexist"));
//			}
					
			/** רҵ���Ͳ�ѯ���� */
			FilterInfo specialtyTypeInfo = new FilterInfo();
			specialtyTypeInfo.getFilterItems().add(new FilterItemInfo("name",fSpecialtyTypeName));
			EntityViewInfo specialtyTypeInfoView = new EntityViewInfo();
			specialtyTypeInfoView.setFilter(specialtyTypeInfo);
			SpecialtyTypeCollection specialtyTypeInfoColl = SpecialtyTypeFactory.getLocalInstance(ctx).getSpecialtyTypeCollection(specialtyTypeInfoView);
			if(specialtyTypeInfoColl != null && specialtyTypeInfoColl.size() > 0){
				changeAuditBill.setSpecialtyType(specialtyTypeInfoColl.get(0));
			}else{
				throw new TaskExternalException(getResource(ctx, "SpecialtyTypeNonexist"));
			}
			
			/** ʩ����λ��ѯ���� */
			FilterInfo supplierInfo = new FilterInfo();
			supplierInfo.getFilterItems().add(new FilterItemInfo("name",fConstrUnitName));
			EntityViewInfo supplierInfoView = new EntityViewInfo();
			supplierInfoView.setFilter(supplierInfo);
			SupplierCollection supplierInfoColl = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(supplierInfoView);
			if(supplierInfoColl != null && supplierInfoColl.size() > 0){
				changeAuditBill.setConstrUnit(supplierInfoColl.get(0));
			}else{
				changeAuditBill.setConstrUnit(null);
			}
			
			/** ��Ƶ�λ��ѯ���� */
			FilterInfo desinerInfo = new FilterInfo();
			desinerInfo.getFilterItems().add(new FilterItemInfo("name",fDesignUnitName));
			EntityViewInfo desinerInfoView = new EntityViewInfo();
			desinerInfoView.setFilter(desinerInfo);
			SupplierCollection desinerInfoColl = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(desinerInfoView);
			if(desinerInfoColl != null && desinerInfoColl.size() > 0){
				changeAuditBill.setDesignUnit(supplierInfoColl.get(0));
			}else{
				changeAuditBill.setDesignUnit(null);
			}	
									
			/** ��Ч�ɱ�ԭ���ѯ���� */
			FilterInfo invalidCostReasonInfo = new FilterInfo();
			invalidCostReasonInfo.getFilterItems().add(new FilterItemInfo("name",fInvalidCostReasonName));
			EntityViewInfo invalidCostReasonInfoView = new EntityViewInfo();
			invalidCostReasonInfoView.setFilter(invalidCostReasonInfo);
			InvalidCostReasonCollection invalidCostReasonInfoColl = InvalidCostReasonFactory.getLocalInstance(ctx).getInvalidCostReasonCollection(invalidCostReasonInfoView);
			if(invalidCostReasonInfoColl != null && invalidCostReasonInfoColl.size() > 0){
				changeAuditBill.setInvalidCostReason(invalidCostReasonInfoColl.get(0));
			}else{
				changeAuditBill.setInvalidCostReason(null);
			}			
			changeAuditBill.setCreator(this.getUserInfo(ctx, fCreatorName));	
			if(changeAuditBill.getCreator() == null){
				throw new TaskExternalException(getResource(ctx, "CreatorNonexist"));
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;			
			date = df.parse(fBizDate.trim());
			changeAuditBill.setBizDate(date);
			date = df.parse(fCreateTime.trim());
			changeAuditBill.setCreateTime(new Timestamp(date.getTime()));
			// ����״̬
			FDCBillStateEnum state = changeAuditBill.getState();
			/** ����״̬Ϊ�����������·�����ǩ֤���ѽ���ʱ����������ƺ����ʱ��Ϊ��¼�� */
			if(FDCBillStateEnum.AUDITTED.equals(state) || FDCBillStateEnum.ANNOUNCE.equals(state) ||
				FDCBillStateEnum.VISA.equals(state)	|| FDCBillStateEnum.CANCEL.equals(state)){
				if (StringUtils.isEmpty(fAuditorName)) {				   
					throw new TaskExternalException(getResource(ctx, "AuditorIsNull"));
				}else if (StringUtils.isEmpty(fAuditTime)) {
					throw new TaskExternalException(getResource(ctx, "AuditTimeIsNull"));
				}else {
					changeAuditBill.setAuditor(this.getUserInfo(ctx, fAuditorName));
					if(changeAuditBill.getAuditor() == null){
						throw new TaskExternalException(getResource(ctx, "AuditorNonexist"));
					}
					date = df.parse(fAuditTime.trim());
					changeAuditBill.setAuditTime(date);
				}	
			}	
		} catch (ParseException e) {
			if(changeAuditBill.getBizDate() == null){
				throw new TaskExternalException(getResource(ctx, "BizDateFormatError"));
			}else if(changeAuditBill.getCreateTime() == null){
				throw new TaskExternalException(getResource(ctx, "CreateTimeFormatError"));
			}else{
				throw new TaskExternalException(getResource(ctx, "AuditTimeFormatError"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} 
		return changeAuditBill;
	}
	
	/**
	 * 
	 * @description	    ��ȡ������/�������Ϣ	
	 * @author			�ű�
	 * @createDate		2011-6-28
	 * @param ctx
	 * @param userName
	 * @return
	 * @throws BOSException UserInfo
	 * @version			EAS1.0
	 * @throws TaskExternalException 
	 * @see
	 */
	private UserInfo getUserInfo(Context ctx,String userNumber) throws BOSException, TaskExternalException {
		FilterInfo userInfoFilter = new FilterInfo();
		userInfoFilter.getFilterItems().add(new FilterItemInfo("number",userNumber));
		EntityViewInfo userFilterInfoView = new EntityViewInfo();
		userFilterInfoView.setFilter(userInfoFilter);		
		UserCollection userColl = UserFactory.getLocalInstance(ctx).getUserCollection(userFilterInfoView);
		return (userColl != null && userColl.size() > 0) ? userColl.get(0) : null;
	}
	
	/**
	 * ��ȡ��Դ�ļ���Ϣ
	 * @author �쿡
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}

}
