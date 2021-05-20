package net.sourceforge.ganttproject.calendar;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttProject;
import net.sourceforge.ganttproject.parser.HolidayTagHandler;
import net.sourceforge.ganttproject.task.TaskLength;
import net.sourceforge.ganttproject.time.TimeUnit;
import net.sourceforge.ganttproject.time.gregorian.FramerImpl;

import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.schedule.framework.DefaultWeekendEntryCollection;
import com.kingdee.eas.fdc.schedule.framework.HolidayEntryCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleWeekendEnum;

/**
 * ����������<br>
 * ���ÿһ����������������������Ч������Ҫ���ݶ�Ӧ����������
 * 
 * @author emanon
 * 
 */
public class ScheduleCalendarImpl extends GPCalendarBase implements GPCalendar {

	// ����һ��Ĭ��˫����������������������
	public static ScheduleCalendarImpl DEFAULT_CALENDAR = new ScheduleCalendarImpl(
			null);

	private final Calendar myCalendar = CalendarFactory.newCalendar();
	// ��ĩ����
	private DayType[] myTypes = new DayType[7];
	// ��ĩ����
	private int myWeekendDaysCount;
	// �ڼ���
	private Set publicHolidays = new LinkedHashSet();
	// ����Ϣ����������̫��̬�˰�
	private AlwaysWorkingTimeCalendarImpl myRestlessCalendar = new AlwaysWorkingTimeCalendarImpl();

	private final FramerImpl myFramer = new FramerImpl(Calendar.DAY_OF_WEEK);

	public ScheduleCalendarImpl(ScheduleCalendarInfo taskCal) {
		if (taskCal == null) {
			for (int i = 0; i < 5; i++) {
				myTypes[i] = GPCalendar.DayType.WORKING;
			}
			setWeekDayType(GregorianCalendar.SATURDAY,
					GPCalendar.DayType.WEEKEND);
			setWeekDayType(GregorianCalendar.SUNDAY, GPCalendar.DayType.WEEKEND);
		} else {
			// ������ĩ
			for (int i = 0; i < myTypes.length; i++) {
				myTypes[i] = GPCalendar.DayType.WORKING;
			}
			DefaultWeekendEntryCollection weekends = taskCal.getWeekendEntrys();
			for (int i = 0; i < weekends.size(); i++) {
				ScheduleWeekendEnum weekend = weekends.get(i).getWeekend();
				int value = weekend.getValue();
				switch (value) {
				case ScheduleWeekendEnum.SATURDAY_VALUE:
					setWeekDayType(GregorianCalendar.SATURDAY,
							GPCalendar.DayType.WEEKEND);
					break;
				case ScheduleWeekendEnum.SUNDAY_VALUE:
					setWeekDayType(GregorianCalendar.SUNDAY,
							GPCalendar.DayType.WEEKEND);
					break;
				case ScheduleWeekendEnum.MONDAY_VALUE:
					setWeekDayType(GregorianCalendar.MONDAY,
							GPCalendar.DayType.WEEKEND);
					break;
				case ScheduleWeekendEnum.TUESDAY_VALUE:
					setWeekDayType(GregorianCalendar.TUESDAY,
							GPCalendar.DayType.WEEKEND);
					break;
				case ScheduleWeekendEnum.WEDNESDAY_VALUE:
					setWeekDayType(GregorianCalendar.WEDNESDAY,
							GPCalendar.DayType.WEEKEND);
					break;
				case ScheduleWeekendEnum.THURSDAY_VALUE:
					setWeekDayType(GregorianCalendar.THURSDAY,
							GPCalendar.DayType.WEEKEND);
					break;
				case ScheduleWeekendEnum.FRIDAY_VALUE:
					setWeekDayType(GregorianCalendar.FRIDAY,
							GPCalendar.DayType.WEEKEND);
					break;
				}
			}
			// ���ýڼ���
			HolidayEntryCollection holidays = taskCal.getHolidayEntrys();
			for (int i = 0; i < holidays.size(); i++) {
				Date holiday = holidays.get(i).getDate();
				setPublicHoliDayType(holiday);
			}
		}
	}

	protected List getActivitiesBackward(Date startDate, TimeUnit timeUnit,
			long l) {
		return null;
	}

	protected List getActivitiesForward(Date startDate, TimeUnit timeUnit,
			long l) {
		// TODO Auto-generated method stub
		return null;
	}

	public Date findClosestWorkingTime(Date time) {
		if (myWeekendDaysCount == 0 && publicHolidays.isEmpty()) {
			return time;
		}
		if (!isNonWorkingDay(time)) {
			return time;
		}
		return getStateChangeDate(time, false);
	}

	public List getActivities(Date startDate, Date endDate) {
		// ActivityCache cache = ActivityCache.getInstance();
		// List ret = cache.getActivity(startDate, endDate);
		// if (ret != null) {
		// return ret;
		// }
		if (myWeekendDaysCount == 0 && publicHolidays.isEmpty()) {
			return myRestlessCalendar.getActivities(startDate, endDate);
		}
		List result = new ArrayList();
		Date curDayStart = startDate;
		boolean isWeekendState = isNonWorkingDay(curDayStart);
		while (curDayStart.compareTo(endDate) <= 0) {
			// ��ȡ״̬�ı��ʱ���,����ĩת�����ա�������ת��ĩ
			Date changeStateDayStart = getBeforeStateChangeDate(curDayStart,
					endDate, !isWeekendState);
			if (changeStateDayStart.compareTo(endDate) <= 0) {
				result.add(new CalendarActivityImpl(curDayStart,
						changeStateDayStart, !isWeekendState));
				curDayStart = myFramer.adjustRight(changeStateDayStart);
				isWeekendState = !isWeekendState;
				continue;
			} else {
				result.add(new CalendarActivityImpl(curDayStart, endDate,
						!isWeekendState));
				break;
			}
		}
		// cache.putActivity(startDate, endDate, result);
		return result;
	}

	public List getActivities(Date startingFrom, TaskLength period) {
		return getActivities(startingFrom, period.getTimeUnit(), period
				.getLength());
	}

	public List getActivities(Date startDate, TimeUnit timeUnit, long unitCount) {
		List result = new ArrayList();
		Date unitStart = startDate;// timeUnit.adjustLeft(startDate);
		while (unitCount > 0) {
			// if ((myWeekendDaysCount == 0 || myWeekendDaysCount == 7)
			// && publicHolidays.isEmpty()) {
			// return myRestlessCalendar.getActivities(startDate, FDCDateHelper
			// .addDays(startDate, (int) unitCount));
			// }
			boolean isWeekendState = isNonWorkingDay(unitStart);
			if (isWeekendState) {
				Date workingUnitStart = getBeforeStateChangeDate(unitStart,
						timeUnit, false, true);
				result.add(new CalendarActivityImpl(unitStart,
						workingUnitStart, false));
				unitStart = timeUnit.adjustRight(workingUnitStart);
				continue;
			}
			result.add(new CalendarActivityImpl(unitStart, unitStart, true));
			unitCount--;
			unitStart = timeUnit.adjustRight(unitStart);
		}
		return result;
	}

	public DayType getDayTypeDate(Date curDayStart) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection getPublicHolidays() {
		// TODO Auto-generated method stub
		return null;
	}

	public DayType getWeekDayType(int day) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isNonWorkingDay(Date curDayStart) {
		// NonWorkingDayCache cache = NonWorkingDayCache.getInstance();
		// Boolean b = cache.get(curDayStart);
		// if (b != null) {
		// return b.booleanValue();
		// }
		boolean ret = false;
		if (isWeekend(curDayStart) || isPublicHoliDay(curDayStart)) {
			ret = true;
		}
		// cache.put(curDayStart, ret);
		return ret;
	}

	public boolean isPublicHoliDay(Date curDayStart) {
		boolean result = publicHolidays.contains(curDayStart);
		// if (!result) {
		// result = myStableHolidays.contains(new GanttCalendar(1, curDayStart
		// .getMonth(), curDayStart.getDate()).getTime());
		// }
		return result;
	}

	public void setPublicHoliDayType(Date curDayStart) {
		publicHolidays.add(curDayStart);
	}

	public void setPublicHoliDayType(int month, int date) {
		Date time = new GanttCalendar(1, month - 1, date).getTime();
		setPublicHoliDayType(time);
		// myStableHolidays.add(time);
	}

	public void setPublicHolidays(URL calendar, GanttProject gp) {
		publicHolidays.clear();
		if (calendar != null) {
			XMLCalendarOpen opener = new XMLCalendarOpen();

			HolidayTagHandler dependencyHandler = new HolidayTagHandler(gp);

			opener.addTagHandler(dependencyHandler);
			opener.addParsingListener(dependencyHandler);
			try {
				opener.load(calendar.openStream());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void setWeekDayType(int day, DayType type) {
		if (type != myTypes[day - 1]) {
			myWeekendDaysCount += (type == DayType.WEEKEND ? 1 : -1);
		}
		myTypes[day - 1] = type;
	}

	public boolean isWeekend(Date curDayStart) {
		myCalendar.setTime(curDayStart);
		int dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek > 0 && dayOfWeek <= 7)
			return myTypes[dayOfWeek - 1] == GPCalendar.DayType.WEEKEND;
		else
			return true;
	}

	/**
	 * ���ݵ�ǰ������ȡ����Ч����<br>
	 * ��ʼ���ںͽ������ڶ���һ�죬����1.1-1.1����1�죬1.1-1.3��3��
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public long getEffectTimes(Date start, Date end) {
		long length = 0;
		List activities = getActivities(start, end);
		for (int i = 0; i < activities.size(); i++) {
			GPCalendarActivity nextCalendarActivity = (GPCalendarActivity) activities
					.get(i);
			if (nextCalendarActivity.isWorkingTime()) {
				int diff = FDCDateHelper.getDiffDays(nextCalendarActivity
						.getStart(), nextCalendarActivity.getEnd());
				length += diff;
			}
		}
		return length;
	}

	public Date shiftDate(Date input, TaskLength shift) {
		List activities = getActivities(input, shift);
		if (activities.isEmpty()) {
			return input;
		}
		Date result;
		if (shift.getValue() >= 0) {
			GPCalendarActivity lastActivity = (GPCalendarActivity) activities
					.get(activities.size() - 1);
			result = lastActivity.getEnd();
		} else {
			GPCalendarActivity firstActivity = (GPCalendarActivity) activities
					.get(0);
			result = firstActivity.getStart();
		}
		return result;
	}

	/**
	 * ��ǰ�ķ���,����ȡ�ýڼ��ոı��ĵ�һ��,����12,13,14,15 �� 13,14�ǽڼ���,���ȡ14
	 * 
	 * @param startDate
	 * @param changeToWeekend
	 * @return
	 */
	private Date getBeforeStateChangeDate(Date startDate, Date endDate,
			boolean changeToWeekend) {
		if (startDate.after(endDate)) {
			return endDate;
		}
		Date nextDayStart = myFramer.adjustRight(startDate);
		if (!(changeToWeekend ^ isNonWorkingDay(nextDayStart))) {// �������ʽ��ͬʱ����
			return startDate; // ���ڵ�����1�ĺ���֢
		} else {
			return getBeforeStateChangeDate(nextDayStart, endDate,
					changeToWeekend);
		}
	}

	/**
	 * ����״̬�仯ǰ������<br>
	 * �������������Ȼû��һ��
	 * 
	 * @param startDate
	 * @param timeUnit
	 * @param changeToWeekend
	 * @param moveRightNotLeft
	 * @return
	 */
	private Date getBeforeStateChangeDate(Date startDate, TimeUnit timeUnit,
			boolean changeToWeekend, boolean moveRightNotLeft) {
		if ((myWeekendDaysCount == 0 || myWeekendDaysCount == 7)
				&& publicHolidays.size() < 1) {
			return startDate;
		}
		Date nextUnitStart = moveRightNotLeft ? timeUnit.adjustRight(startDate)
				: timeUnit.jumpLeft(startDate);
		if (!(changeToWeekend ^ isNonWorkingDay(nextUnitStart))) {
			return startDate;// ����״̬�仯ǰ������
		} else {
			return getBeforeStateChangeDate(nextUnitStart, timeUnit,
					changeToWeekend, moveRightNotLeft);
		}
	}

	/**
	 * ��ǰ�ķ���,����ȡ�ýڼ��ոı��ĵ�һ��,����12,13,14,15 �� 13,14�ǽڼ���,���ȡ15
	 * 
	 * @param startDate
	 * @param changeToWeekend
	 * @return
	 */
	private Date getStateChangeDate(Date startDate, boolean changeToWeekend) {
		Date nextDayStart = myFramer.adjustRight(startDate);
		if (!(changeToWeekend ^ isNonWorkingDay(nextDayStart))) {// �������ʽ��ͬʱ����
			return nextDayStart;
		} else {
			return getStateChangeDate(nextDayStart, changeToWeekend);
		}
	}
}