package com.kingdee.eas.fdc.schedule;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;

/**
 * ���ȼ��������
 * 
 * @author xiaohong_shi
 * 
 */
public class ScheduleCalendarHelper {

	/**
	 * ������Ч���� ʹ����Ganttһ���ķ���������Ч���ڣ�������һ���ô��ǿ�����Ч�ı�����Ϣ�յ��ص�
	 * 
	 * @param startDate
	 * @param endDate
	 * @param calendarInfo
	 * @return
	 */
	public static BigDecimal getEffectTimes(Date startDate, Date endDate,
			ScheduleCalendarInfo calendarInfo) {
		if (startDate == null || endDate == null) {
			return FDCHelper.ZERO;
		}
		GregorianCalendar startCalendar = getCalendar(startDate);
		GregorianCalendar endCalendar = getCalendar(endDate);
		boolean isNeg = false;// �Ƿ��෴
		if (compareTo(startCalendar, endCalendar) > 0) {
			GregorianCalendar tempDate = startCalendar;
			startCalendar = endCalendar;
			endCalendar = tempDate;
			isNeg = true;
		}
		int day = 0;
		while (compareTo(startCalendar, endCalendar) <= 0) {
			// �˴��㷨����������-���壬�������ʱ��Ϊ1�죬����-����Ϊ2�죻��ȷ���㷨Ӧ�����λ2�졣 modify by
			// zhiqiao_yang. at 2010-07-25
			if (calendarInfo != null && !calendarInfo.isWeekTime(startCalendar)) {
				day++;
			}
			startCalendar.add(Calendar.DATE, 1);
		}
		if (isNeg) {
			day = day * -1;
		}
		return new BigDecimal(String.valueOf(day));
	}

	/**
	 * ������Ȼ���� ���쵽��������һ��
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static BigDecimal getNatureTimes(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return FDCHelper.ZERO;
		}
		GregorianCalendar startCalendar = getCalendar(startDate);
		GregorianCalendar endCalendar = getCalendar(endDate);
		boolean isNeg = false;// �Ƿ��෴
		if (compareTo(startCalendar, endCalendar) > 0) {
			GregorianCalendar tempDate = startCalendar;
			startCalendar = endCalendar;
			endCalendar = tempDate;
			isNeg = true;
		}
		long diff = (endCalendar.getTimeInMillis() - startCalendar
				.getTimeInMillis())
				/ (1000 * 60 * 60 * 24);
		// ��������
		diff = diff + 1;
		if (isNeg) {
			diff = diff * -1;
		}
		return new BigDecimal(String.valueOf(diff));
	}

	private static GregorianCalendar getCalendar(Date date) {
		if (date == null) {
			throw new NullPointerException("date cann't be null");
		}
		GregorianCalendar cal = new GregorianCalendar(date.getYear() + 1900,
				date.getMonth(), date.getDate());
		// cal.setTime(date);
		return cal;
	}

	/**
	 * 1.4 ��Calendar û��compareTo ����
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareTo(Calendar date1, Calendar date2) {
		return (int) ((date1.getTimeInMillis() - date2.getTimeInMillis()) / (1000 * 24 * 60 * 60));
	}

	/**
	 * �õ�������Ч���ں�Ľ���ʱ��
	 * 
	 * @param startDate
	 * @param effectTimes
	 * @param calendarInfo
	 * @return
	 */
	public static Date getEndDate(Date startDate, BigDecimal effectTimes,
			ScheduleCalendarInfo calendarInfo) {
		GregorianCalendar startCalendar = getCalendar(startDate);
		int length = effectTimes.intValue();// ��ȫ������Int �Ժ�Ӧ�û��а����
		boolean isNeg = length<0?true:false;
		// ��1��ʼ����Ϊ1��ʾ��ʼʱ�������ʱ��һ��
		if(isNeg){
			for (int i = 0; i < Math.abs(length); i++) {
				startCalendar.add(Calendar.DATE, -1);
				if (calendarInfo.isWeekTime(startCalendar)) {
					// startCalendar.add(Calendar.DATE, 1);
					// ���β���
					i--;
				}
			}
		}else{
			for (int i = 0; i < length; i++) {
				startCalendar.add(Calendar.DATE, 1);
				if (calendarInfo.isWeekTime(startCalendar)) {
					// startCalendar.add(Calendar.DATE, 1);
					// ���β���
					i--;
				}
			}
		}
		
		return startCalendar.getTime();
	}

	/**
	 * ȡ��ʱ�䰴��Ӳ������һ��,���ڼ��������ϵ��
	 * 
	 * @param startDate
	 * @param effectTimes
	 * @param calendarInfo
	 * @return
	 */
	public static Date getDiffDate(Date date, BigDecimal diff) {
		GregorianCalendar calendar = getCalendar(date);
		int length = diff.intValue();
		// ��1��ʼ����Ϊ1��ʾ��ʼʱ�������ʱ��һ��
		calendar.add(Calendar.DATE, length);
		return calendar.getTime();
	}

	/**
	 * �õ�������Ч���ں�Ŀ�ʼʱ��
	 * 
	 * @param endDate
	 * @param effectTimes
	 * @param calendarInfo
	 * @return
	 */
	public static Date getStartDate(Date endDate, BigDecimal effectTimes,
			ScheduleCalendarInfo calendarInfo) {
		GregorianCalendar endCalendar = getCalendar(endDate);
		int length = effectTimes.intValue();// ��ȫ������Int �Ժ�Ӧ�û��а����
		// ��1��ʼ����Ϊ1��ʾ��ʼʱ�������ʱ��һ��
		for (int i = 1; i < length; i++) {
			endCalendar.add(Calendar.DATE, -1);
			if (calendarInfo.isWeekTime(endCalendar)) {
				// startCalendar.add(Calendar.DATE, 1);
				// ���β���
				length++;
			}
		}
		return endCalendar.getTime();
	}

	/**
	 * �õ���һ������
	 * 
	 * @param date
	 * @param calendarInfo
	 * @return
	 */
	public static Date getNextWorkDay(Date date,
			ScheduleCalendarInfo calendarInfo) {
		Calendar cal = getCalendar(date);
		while (true) {
			cal.add(Calendar.DATE, 1);
			if (!calendarInfo.isWeekTime(cal)) {
				break;
			}
		}
		return cal.getTime();
	}

	/**
	 * �õ���һ������
	 * 
	 * @param date
	 * @param calendarInfo
	 * @return
	 */
	public static Date getPreWorkDay(Date date,
			ScheduleCalendarInfo calendarInfo) {
		Calendar cal = getCalendar(date);
		while (true) {
			cal.add(Calendar.DATE, -1);
			if (!calendarInfo.isWeekTime(cal)) {
				break;
			}
		}
		return cal.getTime();
	}

	/**
	 * ȡ������Ĺ�����
	 * <p>
	 * �������
	 * 
	 * @param date
	 * @param calendarInfo
	 * @return
	 */
	public static Date getClosestWorkDay(Date date,
			ScheduleCalendarInfo calendarInfo) {
		Calendar cal = getCalendar(date);
		if (!calendarInfo.isWeekTime(cal)) {
			return cal.getTime();
		} else {
			return getNextWorkDay(date, calendarInfo);
		}
	}

}
