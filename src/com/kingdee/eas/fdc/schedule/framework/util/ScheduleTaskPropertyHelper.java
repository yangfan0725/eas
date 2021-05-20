package com.kingdee.eas.fdc.schedule.framework.util;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyFactory;

/**
 * ����ͼ����ʾ���ؿ���
 * <p>
 * ��T_SCH_ScheduleTaskProperty���������ֶΣ�FDisplayUI��FHideUI<br>
 * �������ֶηֱ���Ƹ����Ƿ���ĳ��UI��ʾ<br>
 * ����FDisplayUI = 7�������1 + 2 + 4���������ר�����ִ��3������Ĭ����ʾ����<br>
 * ����FHideUI = 28,�����4 + 8 + 16����������ִ�С�ר��ִ�С��ܽ��ȼƻ���Ĭ�����ظ���<br>
 * �ж��㷨��<br>
 * FDisplayUI & DISPLAY_MAIN == DISPLAY_MAIN ?���ж��Ƿ���������ʾ<br>
 * 
 * @author emanon
 * 
 */
public class ScheduleTaskPropertyHelper {

	// ����UI
	public static int ALL = 0;
	// �����ʶ
	public static int MAIN = 1;
	// ר���ʶ
	public static int SPCL = 2;
	// ����ִ�б�ʶ
	public static int MAIN_EX = 4;
	// ר��ִ�б�ʶ
	public static int SPCL_EX = 8;
	// �ܽ��ȼƻ���ʶ
	public static int TOTAL = 16;

	/**
	 * �Ƿ����ָ��UI
	 * 
	 * @param UI
	 * @param field
	 * @return
	 */
	public static boolean isIncludeUI(int UI, int field) {
		return isInclude(UI, field);
	}

	/**
	 * �Ƿ�����������
	 * <p>
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isIncludeMain(int field) {
		return isInclude(MAIN, field);
	}

	/**
	 * �Ƿ����ר�����
	 * <p>
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isIncludeSpcl(int field) {
		return isInclude(SPCL, field);
	}

	/**
	 * �Ƿ��������ִ��
	 * <p>
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isIncludeMainEx(int field) {
		return isInclude(MAIN_EX, field);
	}

	/**
	 * �Ƿ����ר��ִ��
	 * <p>
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isIncludeSpclEx(int field) {
		return isInclude(SPCL_EX, field);
	}

	/**
	 * �Ƿ�����ܽ��ȼƻ�
	 * <p>
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isIncludeTotal(int field) {
		return isInclude(TOTAL, field);
	}

	/**
	 * ����field���Ƿ����include����Ԫ��
	 * <p>
	 * ԭ��<br>
	 * ����field=5��ת���ɶ�������Ϊ101<br>
	 * Ҫ�鿴field���Ƿ���������������ʾ����5 & 1���� 101 & 001 = 001����֪����<br>
	 * ͬ��鿴�Ƿ����ר����ƣ���5 & 2���� 101 & 010 = 000����֪������<br>
	 * ͬ������ִ�У�5 & 4���� 101 & 100 = 100����֪����<br>
	 * 
	 * @param include
	 * @param field
	 * @return
	 */
	private static boolean isInclude(int include, int field) {
		if (include == (include & field)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ��ȡ��Ӧ�����Ĭ����ʾ��
	 * <p>
	 * 
	 * @param UI
	 * @return
	 * @throws BOSException
	 */
	public static ScheduleTaskPropertyCollection getDisplayColumns(int UI)
			throws BOSException {
		ScheduleTaskPropertyCollection allColumns = getAllColumns(UI);
		for (int i = allColumns.size() - 1; i >= 0; i--) {
			int hideUI = allColumns.get(i).getHideUI();
			if (ScheduleTaskPropertyHelper.isIncludeUI(UI, hideUI)) {
				allColumns.removeObject(i);
			}
		}
		return allColumns;
	}

	/**
	 * ��ȡ��Ӧ����������У�������ʾ�Լ�����
	 * <p>
	 * ֮ǰд�����У��ָ�Ϊ�����ݿ�ȡֵ�������Զ����С�<br>
	 * 
	 * add by emanon
	 * 
	 * @return
	 * @throws BOSException
	 */
	public static ScheduleTaskPropertyCollection getAllColumns(int UI)
			throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("type", "custom"));
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("seq"));
		ScheduleTaskPropertyCollection taskPropertys = ScheduleTaskPropertyFactory
				.getRemoteInstance().getScheduleTaskPropertyCollection(view);
		for (int i = taskPropertys.size() - 1; i >= 0; i--) {
			int displayUI = taskPropertys.get(i).getDisplayUI();
			int hideUI = taskPropertys.get(i).getHideUI();
			if (!ScheduleTaskPropertyHelper.isIncludeUI(UI, displayUI)
					&& !ScheduleTaskPropertyHelper.isIncludeUI(UI, hideUI)) {
				taskPropertys.removeObject(i);
			}
		}
		return taskPropertys;
	}

}
