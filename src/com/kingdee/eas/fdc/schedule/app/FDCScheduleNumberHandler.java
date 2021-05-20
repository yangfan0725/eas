/**
 * 
 */
package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCodingTypeInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������
 * 
 * @author �ź���
 * @version EAS7.0
 * @createDate 2011-9-2
 * @see
 */

public class FDCScheduleNumberHandler {

	public static void execute(Context ctx, FDCScheduleInfo info) throws BOSException, CodingRuleException, EASBizException {
		// ����û��ڿͻ����ֹ�ѡ���˶Ϻ�,��˴�����������

		// add by david_yang R110411-511 2011.04.20
		if (info.getProject() != null) {
			info.setProject(CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(info.getProject().getId())));
		}

		String bindingProperty = "scheduleCodingType.number";
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		// �Գɱ����Ľ��д���
		String orgId = info.getAdminDept().getId().toString();
		if (StringUtils.isEmpty(orgId))
			return;
		boolean isExist = true;
		if (!iCodingRuleManager.isExist(info, orgId, bindingProperty)) {
			orgId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
			if (!iCodingRuleManager.isExist(info, orgId, bindingProperty)) {
				isExist = false;
			}
		}
		if (isExist) {
			// ѡ���˶Ϻ�֧�ֻ���û��ѡ��������ʾ,��ȡ�����ñ��
			if (iCodingRuleManager.isUseIntermitNumber(info, orgId, bindingProperty) || !iCodingRuleManager.isAddView(info, orgId, bindingProperty)) { // �˴���orgId�벽��1
				// ��
				// ��orgIdʱһ�µ�
				// ��
				// �ж��û��Ƿ����öϺ�֧�ֹ���
				// �����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
				String number = iCodingRuleManager.getNumber(info, orgId, bindingProperty, "");
				info.setNumber(number);
			}
		}
	}

	public static void recycleNumber(Context ctx, IObjectPK pk, IObjectValue objectValue) throws BOSException, EASBizException, CodingRuleException {
		String bindingProperty = "scheduleCodingType.number";
		FDCScheduleInfo info = (FDCScheduleInfo) objectValue;
		ScheduleCodingTypeInfo scheduleType = info.getScheduleCodingType();
		if (scheduleType == null)
			return;
		if (ScheduleCodingTypeInfo.MAINTASK_CODINGID.equals(info.getScheduleCodingType().getId().toString())) {
			scheduleType.setNumber(ScheduleCodingTypeInfo.MAINTASK_CODINGNUM);
		} else if (ScheduleCodingTypeInfo.SPECIALTASK_CODINGID.equals(info.getScheduleCodingType().getId().toString())) {
			scheduleType.setNumber(ScheduleCodingTypeInfo.SPECIALTASK_CODINGNUM);
		}
		OrgUnitInfo orgInfo = ContextUtil.getCurrentOrgUnit(ctx);
		String curOrgId = orgInfo.getId().toString();
		if (info.getNumber() != null && info.getNumber().length() > 0) {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
			if (iCodingRuleManager.isExist(info, curOrgId, bindingProperty)
					&& iCodingRuleManager.isUseIntermitNumber(info, curOrgId, bindingProperty)) {
				iCodingRuleManager.recycleNumber(info, curOrgId, bindingProperty, "", info.getNumber());
			}
		}
	}
}
