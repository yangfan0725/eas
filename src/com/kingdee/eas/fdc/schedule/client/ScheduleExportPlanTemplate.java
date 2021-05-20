/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.framework.CoreBaseCollection;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������ �����ƻ�ģ��
 * 
 * @author �ź���
 * @version EAS7.0
 * @createDate 2011-9-23
 * @see
 */

public class ScheduleExportPlanTemplate {
	private FDCScheduleInfo editData;

	public ScheduleExportPlanTemplate(FDCScheduleInfo editData) {
		this.editData = editData;
	}

	public void exportPlanTemplate(RESchTemplateInfo schTemplateInfo) {
		if (null == schTemplateInfo.getName() || "".equals(schTemplateInfo.getName().trim())) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("schedule.id", this.editData.getId());
		view.setFilter(filter);
		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("longNumber");
		sii.setSortType(SortType.ASCEND);
		view.getSorter().add(sii);
		try {
			FDCScheduleTaskCollection fDCScheduleTaskCollection = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);

			Map ttMap = new HashMap();
			Map tpMap = new HashMap();
			RESchTemplateTaskCollection rESchTemplateTaskCollection = new RESchTemplateTaskCollection();

			/* ȡ���� */
			for (int k = 0; k < fDCScheduleTaskCollection.size(); k++) {
				RESchTemplateTaskInfo rESchTemplateTaskInfo = new RESchTemplateTaskInfo();
				FDCScheduleTaskInfo fDCScheduleTaskInfo = fDCScheduleTaskCollection.get(k);
				/* �������� */
				rESchTemplateTaskInfo.setId(BOSUuid.create((new RESchTemplateTaskInfo()).getBOSType()));
				/* ������������ */
				rESchTemplateTaskInfo.setName(fDCScheduleTaskInfo.getName());
				/* ����ǰ������ */

				/* ���� ������� */
				rESchTemplateTaskInfo.setTaskType(fDCScheduleTaskInfo.getTaskType());
				/* ����ҵ������ */
				for (int s = 0; s < fDCScheduleTaskInfo.getBizType().size(); s++) {
					ScheduleTaskBizTypeInfo scheduleTaskBizTypeInfo = fDCScheduleTaskInfo.getBizType().get(s);
					RESchTemplateTaskBizTypeInfo rESchTemplateTaskBizTypeInfo = new RESchTemplateTaskBizTypeInfo();
					rESchTemplateTaskBizTypeInfo.setId(BOSUuid.create((new RESchTemplateTaskBizTypeInfo()).getBOSType()));
					rESchTemplateTaskBizTypeInfo.setBizType(scheduleTaskBizTypeInfo.getBizType());
					rESchTemplateTaskInfo.getBusinessType().add(rESchTemplateTaskBizTypeInfo);
				}
				/* ���ù��� */
				rESchTemplateTaskInfo.setReferenceDay(fDCScheduleTaskInfo.getDuration());
				/* ���óɹ���� */
				rESchTemplateTaskInfo.setAchievementType(fDCScheduleTaskInfo.getAchievementType());
				/* ���ñ�׼���� */
				rESchTemplateTaskInfo.setStandardTask(fDCScheduleTaskInfo.getStandardTask());
				/* ���ñ�ע */
				rESchTemplateTaskInfo.setDescription(fDCScheduleTaskInfo.getDescription());
				/* ����ģ�� */
				rESchTemplateTaskInfo.setTemplate(schTemplateInfo);
				/* ���ñ��� */
				rESchTemplateTaskInfo.setNumber(fDCScheduleTaskInfo.getNumber());
				rESchTemplateTaskCollection.add(rESchTemplateTaskInfo);
				ttMap.put(fDCScheduleTaskInfo.getId().toString(), rESchTemplateTaskInfo);
				if (null != fDCScheduleTaskInfo.getParent() && null != fDCScheduleTaskInfo.getParent().getId()
						&& !"".equals(fDCScheduleTaskInfo.getParent().getId().toString().trim())) {
					tpMap.put(rESchTemplateTaskInfo.getId().toString(), fDCScheduleTaskInfo.getParent().getId());
				}
				/* ���ó����� */
				String longNumber = fDCScheduleTaskInfo.getLongNumber();
				rESchTemplateTaskInfo.setLongNumber(longNumber.replaceAll("\\.", "!"));
				/* ���ü��� */
				rESchTemplateTaskInfo.setLevel(fDCScheduleTaskInfo.getLevel());
				/* �����Ƿ����ӽڵ� */
				rESchTemplateTaskInfo.setIsLeaf(fDCScheduleTaskInfo.isIsLeaf());
			}

			CoreBaseCollection coreBaseCollection = new CoreBaseCollection();
			/* ������ */
			for (int j = 0; j < rESchTemplateTaskCollection.size(); j++) {
				RESchTemplateTaskInfo rESchTemplateTaskInfo = rESchTemplateTaskCollection.get(j);

				Object pObject = tpMap.get(rESchTemplateTaskInfo.getId().toString());

				/* �ж��Ƿ���ڸ��ڵ� */
				if (null != pObject && !"".equals(pObject.toString().trim())) {
					Object sObject = ttMap.get(pObject.toString().trim());
					if (null != sObject && sObject instanceof RESchTemplateTaskInfo) {
						RESchTemplateTaskInfo chTemplateTaskInfo = (RESchTemplateTaskInfo) sObject;
						rESchTemplateTaskInfo.setParent(chTemplateTaskInfo);
						coreBaseCollection.add(chTemplateTaskInfo);
					}
				}
				RESchTemplateTaskFactory.getRemoteInstance().addnew(rESchTemplateTaskInfo);
			}
			// RESchTemplateTaskFactory.getRemoteInstance().updateBatchData(
			// coreBaseCollection);
		} catch (BOSException e) {
			// logger.error(e.getMessage());
		} catch (Exception e) {
			// logger.error(e.getMessage());
		}
	}
}
