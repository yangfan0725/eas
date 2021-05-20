package net.sourceforge.ganttproject.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyInfo;
import com.kingdee.eas.fdc.schedule.framework.util.ScheduleTaskPropertyHelper;

/**
 * �ͻ��˻���
 * <p>
 * �������������ڶ࣬���������Ҫ�ظ�ȡ��<br>
 * ���Խ���Щ����ʹ�õ�ʵ�����浽�����У�������ͨ��Զ�̵���<br>
 * 
 * cache:startDate��endDate��activity list<br>
 * scheduleCalendar:����<br>
 * displayColumns:����ͼĬ����ʾ��������<br>
 * columnsOrder:����ͼ������Ĭ����˳��<br>
 * columnsWidth:����ͼ������Ĭ���п�<br>
 * columnsAlign:����ͼ������Ĭ�϶���<br>
 * params:����<br>
 * 
 * @author zhiqiao_yang date��2010-8-20
 * @lastUpdate emanon 2011-11-25
 * 
 * @version <EAS600>
 */
public class ActivityCache {
	public static long HIT_COUNT = 0;
	
	// Activity
	private Map cache;
	
	private Map myCustomerColoumns;
	
	// ��������
	private Map scheduleCalendar;
	
	// ����ͼ������Ĭ����ʾ�������У��贫��UI)
	private Map displayColumns;
	
	// ����ͼ������Ĭ����˳��
	private Map columnsOrder;
	// ����ͼ������Ĭ���п�
	private Map columnsWidth;
	// ����ͼ������Ĭ�϶���
	private Map columnsAlign;
	
	// ��������
	private Map params;

	/**
	 * ȡ����ֵ
	 * 
	 * @param paramNum
	 * @return
	 */
	public String getParam(String paramNum) {
		if (params == null) {
			params = new HashMap();
		}
		String orgID = SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();
		String pValue = (String) params.get(orgID + paramNum);
		if (FDCHelper.isEmpty(pValue)) {
			try {
				String value = ParamControlFactory.getRemoteInstance()
						.getParamValue(new ObjectUuidPK(orgID), paramNum);
				params.put(orgID + paramNum, value);
				return value;
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		} else {
			return pValue;
		}
		return null;
	}
	
	// �ɱ����ļ�����
	public String getParam(String paramNum, String orgID) {
		if (params == null) {
			params = new HashMap();
		}
		String pValue = (String) params.get(orgID + paramNum);
		if (FDCHelper.isEmpty(pValue)) {
			try {
				String value = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(orgID), paramNum);
				params.put(orgID + paramNum, value);
				return value;
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		} else {
			return pValue;
		}
		return null;
	}
	
	public Map getScheduleCalendar() {
		return scheduleCalendar;
	}
	
	public Map getcolumnsWidth() {
		if (columnsWidth == null) {
			initColumnsProperty();
		}
		return columnsWidth;
	}

	public Map getColumnsOrder() {
		if (columnsOrder == null) {
			initColumnsProperty();
		}
		return columnsOrder;
	}

	public Map getColumnsAlign() {
		if (columnsAlign == null) {
			initColumnsProperty();
		}
		return columnsAlign;
	}

	private void initColumnsProperty() {
		columnsOrder = new HashMap();
		columnsWidth = new HashMap();
		columnsAlign = new HashMap();
		try {
			ScheduleTaskPropertyCollection allColumns = ScheduleTaskPropertyHelper
					.getAllColumns(0);
			for (int i = 0; i < allColumns.size(); i++) {
				ScheduleTaskPropertyInfo tpInfo = allColumns.get(i);
				String name = tpInfo.getName();
				String pID = tpInfo.getPropertyId();
				int width = tpInfo.getWidth();
				columnsWidth.put(name, new Integer(width));
				columnsWidth.put(pID, new Integer(width));
				int order = tpInfo.getSeq();
				columnsOrder.put(name, new Integer(order));
				columnsOrder.put(pID, new Integer(order));
				int align = tpInfo.getAlign();
				columnsAlign.put(name, new Integer(align));
				columnsAlign.put(pID, new Integer(align));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	
	public Map getDisplayColumns(int uiMark) {
		if (displayColumns == null) {
			displayColumns = new HashMap();
		}
		
		Integer mark = new Integer(uiMark);
		if (displayColumns.containsKey(mark)) {
			return (Map) displayColumns.get(mark);
		} else {
			try {
				ScheduleTaskPropertyCollection allColumns = ScheduleTaskPropertyHelper
						.getAllColumns(uiMark);
				if (allColumns != null) {
					Map disMap = new HashMap();
					for (int i = 0; i < allColumns.size(); i++) {
						ScheduleTaskPropertyInfo tpInfo = allColumns.get(i);
						String name = tpInfo.getName();
						String pID = tpInfo.getPropertyId();
						int displayUI = tpInfo.getDisplayUI();
						boolean isDis = ScheduleTaskPropertyHelper.isIncludeUI(
								uiMark, displayUI);
						disMap.put(name, new Boolean(isDis));
						disMap.put(pID, new Boolean(isDis));
					}
					displayColumns.put(mark, disMap);
					return disMap;
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public ScheduleTaskPropertyCollection getDisplayColumn(int uiMark) {
		if (myCustomerColoumns == null) {
			myCustomerColoumns = new HashMap();
		}

		Integer mark = new Integer(uiMark);
		if (myCustomerColoumns.containsKey(mark)) {
			return (ScheduleTaskPropertyCollection) myCustomerColoumns.get(mark);
		} else {
			try {
				ScheduleTaskPropertyCollection allColumns = ScheduleTaskPropertyHelper.getAllColumns(uiMark);
				if (allColumns != null) {

					myCustomerColoumns.put(mark, allColumns);
					return allColumns;
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * �ӻ���ȡ����Ŀ��Ĭ������
	 * 
	 * @param prjID
	 * @return
	 */
	public ScheduleCalendarInfo getDefaultCal(String prjID) {
		return (ScheduleCalendarInfo) scheduleCalendar.get(prjID);
	}

	/**
	 * �ӻ���ȡ����id��Ӧ������<br>
	 * �������û�У�������ݿ��ȡ�����沢����<br>
	 * ���ݿ�Ҳû�У��򷵻ؿ�
	 */
	public ScheduleCalendarInfo getCalendar(String calID) {
		ScheduleCalendarInfo rt = (ScheduleCalendarInfo) scheduleCalendar
				.get(calID);
		if (rt == null) {
			try {
				rt = ScheduleCalendarFactory.getRemoteInstance()
						.getScheduleCalendarInfo(new ObjectUuidPK(calID));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if (rt != null) {
				addScheduleCalendar(rt, true);
			}
		}
		return rt;
	}

	/**
	 * ���һ�����������棬��Ϊ�ܶ�����û��ѡ��������ʹ����ĿĬ�ϣ����Դӻ����ȡ
	 * 
	 * @param cal
	 */
	public void addScheduleCalendar(ScheduleCalendarInfo cal) {
		if (cal != null && cal.getObjectId() != null) {
			this.scheduleCalendar.put(cal.getObjectId(), cal);
		}
	}

	public void addScheduleCalendar(ScheduleCalendarInfo cal, boolean isIDKey) {
		if (cal != null) {
			this.scheduleCalendar.put(cal.getId().toString(), cal);
		}
		if (!isIDKey) {
			addScheduleCalendar(cal);
		}
	}

	/**
	 * �����������
	 */
	public void clearScheduleCalendar() {
		this.scheduleCalendar.clear();
	}

	private static ActivityCache instance = new ActivityCache();

	public static ActivityCache getInstance() {
		return instance;
	}

	private ActivityCache() {
		this.scheduleCalendar = new HashMap(10);
		this.cache = new HashMap(2000);
	}

	public void putActivity(Date start, Date end, List activities) {
		Map temp = (Map) cache.get(start);
		if (temp == null) {
			temp = new HashMap();
			cache.put(start, temp);
		}
		temp.put(end, activities);
	}

	public List getActivity(Date start, Date end) {
		Map temp = (Map) cache.get(start);
		if (temp != null) {
			return (List) temp.get(end);
		}
		return null;
	}

	public int size() {
		return cache.size();
	}

	public void clear() {
		Set set = cache.entrySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			Entry entry = (Entry) iter.next();
			((Map) entry.getValue()).clear();
		}
		cache.clear();
	}
}