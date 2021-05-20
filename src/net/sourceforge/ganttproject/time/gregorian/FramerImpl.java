/*
 LICENSE:
 
 This program is free software; you can redistribute it and/or modify  
 it under the terms of the GNU General Public License as published by  
 the Free Software Foundation; either version 2 of the License, or     
 (at your option) any later version.                                   
 
 Copyright (C) 2004, GanttProject Development Team
 */
package net.sourceforge.ganttproject.time.gregorian;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import net.sourceforge.ganttproject.calendar.CalendarFactory;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.time.DateFrameable;

/**
 * 
 * �������޸�ΪadjustRight�ȷ�������ÿ�ε���ʱ������Calendar
 * @author zhiqiao_yang date��2010-8-20<p>
 * �޸��ˣ�zhiqiao_yang <p>
 * �޸�ʱ�䣺2010-8-20 <p>
 * @version <EAS600>
 */
public class FramerImpl implements DateFrameable {
	/**
	 * ����adjustRight�ȷ���ʱ�� �Ƿ���������ֶα�ʾ�� ��ʼ������ʱ����Ϊfalse�� �������ӦΪtrue
	 */
	public static boolean isClearFields = true;
    private final int myCalendarField;
    private static Calendar calendar = CalendarFactory.newCalendar();
    public FramerImpl(int calendarField) {
        myCalendarField = calendarField;
    }

    public Date adjustRight(Date baseDate) {
        calendar.setTime(baseDate);
        clearFields(calendar);
        calendar.add(myCalendarField, 1);
        return calendar.getTime();
    }

    private void clearFields(Calendar c) {
    	if(isClearFields){
	        for (int i = myCalendarField + 1; i <= Calendar.MILLISECOND; i++) {
	            c.clear(i);
	        }
    	}
    }

    public Date adjustLeft(Date baseDate) {
        calendar.setTime(baseDate);
        clearFields(calendar);
        // if (beforeClear.compareTo(c.getTime())==0) {
        // c.add(Calendar.MILLISECOND, -1);
        // }
        Date result = calendar.getTime();
        return result;
    }

    public Date jumpLeft(Date baseDate) {
        calendar.setTime(baseDate);
        calendar.add(myCalendarField, -1);
        return calendar.getTime();
    }
    
    public static void main(String[] args) {
        GanttLanguage.getInstance().setLocale(null);
        Calendar c = (Calendar) GanttLanguage.getInstance().newCalendar();
        Date now = GregorianCalendar.getInstance().getTime();
        c.setTime(now);
        for (int i = Calendar.DAY_OF_MONTH + 1; i <= Calendar.MILLISECOND; i++) {
            c.clear(i);
        }
        Date result = c.getTime();
        System.err.println(result);
    }
}
