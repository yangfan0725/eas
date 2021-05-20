package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ScheduleCalendarInfo extends AbstractScheduleCalendarInfo implements Serializable 
{
    public ScheduleCalendarInfo()
    {
        super();
    }
    protected ScheduleCalendarInfo(String pkField)
    {
        super(pkField);
    }
    
    private boolean weekends[]=null;//��������->������Ϊһ��
    private Map holidayMap=null;

    /**
     * �жϴ����ʱ���Ƿ�����Ϣ��
     * @param date
     * @return
     */
    public boolean isWeekTime(Date date){
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
    	return isWeekTime(calendar);    	
    }
    
    private String getHolidayKey(Date date){
    	return String.valueOf((date.getYear()+1900)*10000+(date.getMonth()+1)*100+date.getDate());
    }
    
    
    /**
     * �жϴ����ʱ���Ƿ�����Ϣ��
     * @param date
     * @return
     */
    public boolean isWeekTime(Calendar calendar){
    	if(weekends==null||holidayMap==null){
    		weekends=new boolean[]{false,false,false,false,false,false,false};
    		holidayMap=new HashMap();
    		for(Iterator iter=getWeekendEntrys().iterator();iter.hasNext();){
    			DefaultWeekendEntryInfo weekend=(DefaultWeekendEntryInfo)iter.next();
    			if(weekend.getWeekend().getValue()>0&&weekend.getWeekend().getValue()<8){
    				//��������->������Ϊһ��
    				weekends[weekend.getWeekend().getValue()%7]=true;
    			}
    		}
    		for(Iterator iter=getHolidayEntrys().iterator();iter.hasNext();){
    			HolidayEntryInfo entry=(HolidayEntryInfo)iter.next();
    			Date myDate = entry.getDate();
    			String key=getHolidayKey(myDate);
    			holidayMap.put(key,Boolean.TRUE);
    		}
    	}
    	
    	//����Ҫ�ر�ע��Calendar  SUNDAY = 1-->7,Date ��0-6
    	int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
    	if(weekends[week]){
    		return true;
    	}
    	
    	if(Boolean.TRUE.equals(holidayMap.get(getHolidayKey(calendar)))){
    		return true;
    	}
    	
    	return false;
    }
    private String getHolidayKey(Calendar calendar){
    	return String.valueOf(calendar.get(calendar.YEAR)*10000+(calendar.get(calendar.MONTH)+1)*100+calendar.get(calendar.DAY_OF_MONTH));
    }
}