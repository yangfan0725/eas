package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;

import com.kingdee.bos.util.BOSUuid;

public class ScheduleTaskPropertyInfo extends AbstractScheduleTaskPropertyInfo
		implements Serializable {
	public ScheduleTaskPropertyInfo() {
		super();
	}

	protected ScheduleTaskPropertyInfo(String pkField) {
		super(pkField);
	}

	public ScheduleTaskPropertyInfo(String id, String name, String propertyId,
			TaskPropertyTypeEnum type, String valueType) {
		if (id == null) {
			this.setId(BOSUuid.create(this.getBOSType()));
		} else {
			this.setId(BOSUuid.read(id));
		}
		this.setName(name);
		this.setPropertyId(propertyId);
		this.setType(type);
		this.setValueType(valueType);
	}

	public ScheduleTaskPropertyInfo(String id, String name, String propertyId,
			TaskPropertyTypeEnum type, String valueType, String key,
			String mapKey) {
		this(id, name, propertyId, type, valueType);
		this.setKey(key);
		this.setMapKey(mapKey);
	}

	public ScheduleTaskPropertyInfo(String id, String name, String propertyId,
			TaskPropertyTypeEnum type, String valueType, String key,
			String mapKey, String query) {
		this(id, name, propertyId, type, valueType);
		this.setKey(key);
		this.setMapKey(mapKey);
		this.setQuery(query);

	}

	private static ScheduleTaskPropertyCollection taskPropertys = null;

	/**
	 * Ĭ�������ԣ������ݿ���ȡ
	 * <p>
	 * ֮ǰд�����У��ָ�Ϊ�����ݿ�ȡֵ�������Զ����С�<br>
	 * ��������ݿ�������һ�У��󲿷������Ѿ�ָ�����ˣ��������ҪĬ����ʾ��������뻹Ҫ<br>
	 * �޸�ScheduleBaseUI.load2Gantt()�������������мӺ�Ĭ����ʾ���С�<br>
	 * ϵͳԤ���21�д����ж��е��ã�����ɾ�������ⲻ��Ҫ�Ĵ���<br>
	 * add by emanon
	 * 
	 * @return
	 */
	// public static ScheduleTaskPropertyCollection
	// getDefaultScheduleTaskProperty(
	// int UI) {
	// try {
	// EntityViewInfo view = new EntityViewInfo();
	// view.getSorter().add(new SorterItemInfo("seq"));
	// taskPropertys = ScheduleTaskPropertyFactory.getRemoteInstance()
	// .getScheduleTaskPropertyCollection(view);
	// for (int i = taskPropertys.size() - 1; i >= 0; i--) {
	// int displayUI = taskPropertys.get(i).getDisplayUI();
	// int hideUI = taskPropertys.get(i).getHideUI();
	// if (ScheduleTaskPropertyHelper.isIncludeUI(UI, displayUI)
	// || ScheduleTaskPropertyHelper.isIncludeUI(UI, hideUI)) {
	//
	// } else {
	// taskPropertys.removeObject(i);
	// }
	// }
	// } catch (BOSException e) {
	// e.printStackTrace();
	// }
	// return taskPropertys;
	// }

	public String getQuery() {
		return this.getString("query");
	}

	public void setQuery(String query) {
		this.setString("query", query);
	}
}