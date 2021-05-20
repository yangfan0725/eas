/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����<p>		 	
 * ������		                              
 *		
 * @author		����ΰ
 * @version		EAS7.0		
 * @createDate	2011-8-29
 * @see						
 */
package com.kingdee.eas.fdc.schedule.framework;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.kingdee.bos.Context;
import com.kingdee.eas.base.usermonitor.UserMonitorFactory;
import com.kingdee.eas.common.EASBizException;



/**
 * ����: ���ڴ����ߣ����ڶ����ڵĻ�ȡ���Ƚϡ�����
 * 
 * @author daij  date:2006-11-27   �޸�ʱ��: 2010-03-12 run_zheng<p> 
 * @version EAS5.2.0
 */
public final class DateUtils {
	
	public static final int MILLIS_OF_DAY = 86400000;
	
	public static final String TIME_FORMAT_STR = "HH:mm:ss";
	public static final String SIMPLE_FORMAT_STR = "yyyy-MM-dd";
	public static final String DETAIL_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String ALLFIELD_FORMAT_STR = "yyyy-MM-dd HH:mm:ss.SS";
	//�����ڸ�ʽ������
	public static final SimpleDateFormat SIMPLE_FORMAT = new SimpleDateFormat(SIMPLE_FORMAT_STR);
	public static final SimpleDateFormat DETAIL_FORMAT = new SimpleDateFormat(DETAIL_FORMAT_STR);

////////////////////////////////////////Date��صķ���//////////////////////////////////////////////
	/**
	 * ��������Date��N���£���ʹ����ʧЧ���ڴ���
	 * EXAMPLE: date = 2006-11-29  dateAddMonth(date, 1); date = 2006-12-29
	 * 
	 * @param Date ���ΪNull���򷵻ص�ǰ���ڼ���N����.
	 * @param months  ����ΪDate����months���£�����ΪDate��ȥmonths����
	 * @return java.util.Date 
	 * @author:daij  
	 * ����ʱ�䣺2006-11-29  �޸�ʱ��: 2010-03-12 run_zheng<p> 
	 */	
	public static Date addMonth(Date date,int months){
		return fromDefNow(date).addMonth(months).date();
	}
	
	/**
	 * ��������Date��N��
	 * Example: date = 2006-11-29  dateAddDay(date, 1); date = 2006-11-30
	 * 
	 * @param Date ���ΪNull���򷵻ص�ǰ���ڼ���N��.
	 * @param days  ����ΪDate����days���£�����ΪDate��ȥdays����
	 * @return java.util.Date   �޸�ʱ��: 2010-03-12 run_zheng<p> 
	 */	
	public static Date addDay(Date date,int days){
		return fromDefNow(date).addDay(days).date();
	}
	
	/**
	 * �������������ڶ�Ӧ���Ե�ֵ��
	 * @param date  Դ����
	 * @param field ���������ԣ�  ��:Calendar.DATE,����ο�Calendar
	 * @param amount ������
	 * @return �����������
	 * @author tjw
	 * ����ʱ�䣺2009-09-14  �޸�ʱ��: 2010-03-12 run_zheng<p> 
	 */
	public static Date addDateField(Date date,int field,int amount){
		if(date == null) date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(field, amount);
		return (c.getTime());
	}
	

	
	/**
	 * ������ĳ��Ŀ�ʼʱ�䣬yyyy-MM-dd 00:00:00.000
	 * 		dateΪ�գ�Ĭ��Ϊ��ǰ����
	 * @param date java.util.Date Դ����
	 * @return Ŀ������
	 * @author:daij
	 * ����ʱ�䣺2006-11-27  �޸�ʱ��: 2010-03-12 run_zheng<p> 
	 */
	public static Date startOfDay(Date date){
		return fromDefNow(date).startOfDay().date();
	}
	
	/**
	 *������ĳ��Ľ���ʱ�䣬yyyy-MM-dd 23:59:59.999
	 *		dateΪ�գ�Ĭ��Ϊ��ǰ����
	 * @param date java.util.Date Դ����
	 * @return Ŀ������
	 */
	public static Date endOfDay(Date date){
		return fromDefNow(date).endOfDay().date();
	}
	
	/**
	 *��������ĳ��Ϊ��׼�������µ�һ��Ŀ�ʼʱ�䣬yyyy-MM-01 00:00:00.000
	 *		dateΪ�գ�Ĭ��Ϊ��ǰ����
	 * @param date Դ����
	 * @return  Ŀ������
	 */
	public static Date startOfMonth(Date date){
		return fromDefNow(date).startOfMonth().date();
	}
	
	/**
	 *������ ��ĳ��Ϊ��׼�������µ�һ��Ľ���ʱ�䣬yyyy-MM-XX 23:59:59.999��
	 * 		 ����XXΪ�����µ����һ�졣dateΪ�գ�Ĭ��Ϊ��ǰ����
	 * @param date Դ����
	 * @return	Ŀ������
	 */
	public static Date endOfMonth(Date date){
		return fromDefNow(date).endOfMonth().date();
	}
	
	/**
	 * ���������ؾ�ȷ���������,��ʾ��ʽΪyyyy-MM-dd������
	 * 		dateΪ�գ�Ĭ��Ϊ��ǰ����
	 * @param date java.util.Date Դ����
	 * @return String ��yyyy-MM-dd��ʽ���������.
	 * @author:daij
	 * ����ʱ�䣺2009-8-8 �޸�ʱ��: 2010-03-12 run_zheng<p> 
	 */
	public static String simpleDateString(Date date) {
		return fromDefNow(date).startOfDay().simpleString();
	}
	/**
	 * ���������ؾ�ȷ���������,��ʾ��ʽΪyyyy-MM-dd HH:mm:ss������
	 * 		dateΪ�գ�Ĭ��Ϊ��ǰ����
	 * @param date java.util.Date Դ����
	 * @return String ��yyyy-MM-dd HH:mm:ss��ʽ���������.
	 * @author:tjw
	 * ����ʱ�䣺2010-6-23 
	 */
	public static String detailDateString(Date date){
		SimpleDateFormat formater = new SimpleDateFormat(DETAIL_FORMAT_STR);
		return formater.format(date);
	}
    
    /**
	 * ��ȡӦ�÷�������ǰʱ�䣬֧�ֿͻ��˺ͷ���˵���
     * @author:hongguang_wang
	 * @param ctx
	 *            ����������ģ��ͻ��˵���ʱ�봫��NULL
	 * @return Date
	 * @throws EASBizException
	 */
	public static Date serverDate(Context ctx) throws EASBizException {
		Date serverDate = null;
		if (ctx == null) {
			serverDate = UserMonitorFactory.getRemoteInstance().getCurrentTime();
		} else {
			serverDate = UserMonitorFactory.getLocalInstance(ctx).getCurrentTime();
		}
		return serverDate;
	}
	
	/**
	 * ��õ�ǰ����������,����ʱ��,��ʽΪyyyy-MM-dd
	 * @return
	 * @throws EASBizException
	 * @throws ParseException
	 */
	public static Date getServerDate(Context ctx) throws EASBizException, ParseException{
		Date serverDate = null;
		if (ctx == null) {
			serverDate = UserMonitorFactory.getRemoteInstance().getCurrentTime();
		} else {
			serverDate = UserMonitorFactory.getLocalInstance(ctx).getCurrentTime();
		}		
		
		Date curDate = DateUtils.parseSimpleString(DateUtils.simpleDateString(serverDate));
		return curDate;
	}
	
///////////////////////////////////////////Timestamp��صķ���/////////////////////////////////////    
	/**
	 * ��������Dateת��ΪTimestamp
	 * ע�⣺��dateֵΪNULLʱ���ɷ��ص�ǰ���ڵ�Timestamp
	 * 
	 * @param date Դ����
	 * @return Timestamp Ŀ�����͵�����
	 * @author:daij
	 * ����ʱ�䣺2006-11-29  �޸�ʱ��: 2010-03-12 run_zheng<p> 
	 */
	public static Timestamp dateToTimestamp(Date date){
		return new Timestamp((date == null? new Date(): date).getTime());
		//return fromDefNow(date).timestamp();
	}
	
	/**
	 * ��������Timestampת��ΪDate.
	 * ע�⣺��TimestampֵΪNULLʱ���ɷ��ص�ǰ���ڵ�Date
	 * 
	 * @param time Դ����Timestampֵ
	 * @return java.util.Date  Ŀ�����͵�����
	 * @author:daij
	 * ����ʱ�䣺2006-11-29 �޸�ʱ��: 2010-03-12 run_zheng<p> 
	 */
	public static Date timestampToDate(Timestamp time){
		return new Date(time.getTime());
		//return fromDefNow(time).date();
	}
    
    /**
     * ��������ȡ��ǰ��ȷ��ʱ���
     * @return java.sql.Timestamp
     * @author:daij
     * ����ʱ�䣺2007-4-19 �޸�ʱ��: 2010-03-12 run_zheng<p> 
     */
    public static Timestamp nowTimestamp(){
    	return new Timestamp((new Date()).getTime());
    	//return fromNow().timestamp(); 
    }
    
    /**
     * 
     * ���������ز���dateʱ�������ʱ�䴦��Ϊ��ʱ���֣��룺0, 0, 0 3��NULL��ΪNOW����
     * @return Timestamp
     * @author:daij
     * ����ʱ�䣺2006-11-27 �޸�ʱ��: 2010-03-12 run_zheng<p> 
     */
    public static Timestamp timestampStartOfDay(Date date){
    	return fromDefNow(date).startOfDay().timestamp();
    }
	
    /**
     * ���������ز���dateʱ�������ʱ�䴦��Ϊ��ʱ���֣��� ����Ϊ��23, 59, 59��NULL��ΪNOW����
     * @return Timestamp
     * @author:daij
     * ����ʱ�䣺2006-11-27 �޸�ʱ��: 2010-03-12 run_zheng<p> 
     */
    public static Timestamp timestampEndOfDay(Date date){
    	return fromDefNow(date).endOfDay().timestamp();
    }
    
    /**
     * ��������ĳ��Ϊ��׼�������µ�һ��Ŀ�ʼʱ�䣬yyyy-MM-01 00:00:00.000
     * 		dateΪ�գ�Ĭ��Ϊ��ǰ����
     * @param date Դ����
     * @return Ŀ��Timestamp����
     */
    public static Timestamp timestampStartOfMonth(Date date){
    	return fromDefNow(date).startOfMonth().timestamp();
    }
    
    /**
     * ��������ĳ��Ϊ��׼�������µ�һ��Ľ���ʱ�䣬yyyy-MM-XX 23:59:59.999��
	 * 		 ����XXΪ�����µ����һ�졣dateΪ�գ�Ĭ��Ϊ��ǰ����
     * @param date Դ����
     * @return Ŀ��Timestamp����
     */
    public static Timestamp timestampEndOfMonth(Date date){
    	return fromDefNow(date).endOfMonth().timestamp();
    }
	
	/**
	 * ������ĳ��������ǰ���ߺ��Ƽ���. 
	 * 		dateΪ�գ�Ĭ��Ϊ��ǰ����
	 * @param amount ǰ����Ƽ��죬 ����:amount = 1 ��Ϊ���죬 -1��Ϊǰ�죬�Դ�����
	 * @return Timestamp
	 * @author:daij
	 * ����ʱ�䣺2009-7-18 �޸�ʱ��: 2010-03-12 run_zheng<p> 
	 */
	public static Timestamp timestampAddDay(Date date, int amount){
		return fromDefNow(date).addDay(amount).timestamp();
	}
	
	/**
	 * ������ĳ�����ڵ�����ǰ���ߺ��Ƽ���. 
	 * 		dateΪ�գ�Ĭ��Ϊ��ǰ����
	 * @param amount ǰ����Ƽ��£� ����:amount = 1 ��Ϊ�¸��£� -1��Ϊ�ϸ��£��Դ�����
	 * @return Timestamp
	 * @author:daij
	 * ����ʱ�䣺2009-7-18 <p>
	 */
	public static Timestamp timestampAddMonth(Date date, int amount){
		return fromDefNow(date).addMonth(amount).timestamp();
	}

	/**
	 * ��ȡӦ�÷�������ǰʱ�䣬֧�ֿͻ��˺ͷ���˵���
     * @author:hongguang_wang
	 * @param ctx
	 *            ����������ģ��ͻ��˵���ʱ�봫��NULL
	 * @return Timestamp
	 * @throws EASBizException
	 */
	public static Timestamp serverTimestamp(Context ctx) throws EASBizException {
		return from(serverDate(ctx)).timestamp();
	}
/////////////////////////////////////////////////���ڱȽϡ��жϺͼ���/////////////////////////////////	
	/**
	 * ��ʼ���ڲ���ƫ�ƺ���������ڱȽϽ��ֵ
	 * @param offsetPart ƫ�Ʋ��� Calendar.YEAR, Calendar.MONTH, Calendar.DATE,
	 *          Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND
	 * @param startDate ��ʼ����
	 * @param endDate ��������
	 * @param offset ƫ��ֵ
	 * @author tjw
	 * @return int 
     * 		-1  startDate < endDate
     * 		0   startDate = endDate
     * 	    1   startDate > endDate
	 */
	public static int offset(int offsetPart,Date startDate,Date endDate,int offset){		
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		
		switch (offsetPart) {
		case Calendar.YEAR:
			startCalendar.add(Calendar.YEAR, offset);
			break;
		case Calendar.MONTH:
			startCalendar.add(Calendar.MONTH, offset);
			break;
		case Calendar.DATE:
			startCalendar.add(Calendar.DATE, offset);
			break;
		case Calendar.HOUR_OF_DAY:
			startCalendar.add(Calendar.HOUR_OF_DAY, offset);
			break;
		case Calendar.MINUTE:
			startCalendar.add(Calendar.MINUTE, offset);
			break;
		case Calendar.SECOND:
			startCalendar.add(Calendar.SECOND, offset);
			break;
		case Calendar.MILLISECOND:
			startCalendar.add(Calendar.MILLISECOND, offset);
			break;
		}
		long result = endDate.getTime() - startCalendar.getTimeInMillis();
		return result > 0 ? -1 : result < 0 ? 1 : 0;
	}
	
    /**
     * ������������֮����������
     * 		����ķ���ֵΪdate2 - date1 ������
     * @author danny_du
     * @param date1
     * @param date2
     * @return
     */
    public static int diffdates(Date date1, Date date2) {
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		return (int) ((time2 - time1) / MILLIS_OF_DAY);
	} 
    /**
     * �������������ھ�ȷ����Ƚ�.
     * ע�⣺����Ƚϲ�����ʱ,�֣��룬����.
     * 
     * @param dateLeft ��ΪNULL��ΪNOW����
     * @param dateRight ��ΪNULL��ΪNOW����
     * @return int 
     * 		-1  dateLeft < dateRight
     * 		0   dateLeft = dateRight
     * 	    1   dateLeft > dateRight
     * @author:daij
     * ����ʱ�䣺2006-11-27  �޸�ʱ��: 2010-03-12 run_zheng<p> 
     */
    public static int compareUpToDay(Date dateLeft, Date dateRight) {
       long result =  fromDefNow(dateLeft).startOfDay().milliseconds() 
       					- fromDefNow(dateRight).startOfDay().milliseconds();
       return result > 0 ? 1 : result < 0 ? -1 : 0;
    }
    
    /**
     * ����������Date�뵱ǰ����NOW�Ƚ�
     * @param date ���Ƚϵ����ڣ���ΪNULL��ΪNOW����
     * @return int 
     * 		-1  NOW < dateRight
     * 		0   NOW = dateRight
     * 	    1   NOW > dateRight 
     * @author:daij
     * ����ʱ�䣺2006-11-27 �޸�ʱ��: 2010-03-12 run_zheng<p> 
     */
    public static int compareNowUpToDay(Date date){
    	return compareUpToDay(null,date);
    }
    /**
     * �������������ڱȽ�.
     * 
     * @param left ��ΪNULL��ΪNOW����
     * @param right ��ΪNULL��ΪNOW����
     * @return int 
     * 		-1  dateLeft < dateRight
     * 		0   dateLeft = dateRight
     * 	    1   dateLeft > dateRight
     * ����ʱ��: 2010-03-12 run_zheng<p> 
     */
    public static int compareDate(Date left, Date right){
    	long result = fromDefNow(left).milliseconds() - fromDefNow(right).milliseconds();
    	return result > 0 ? 1 : result < 0 ? -1 : 0;
    }
    
    public static int compareTime(Date left, Date right){
    	long result = fromDefNow(left).time().getTime() - fromDefNow(right).time().getTime();
    	return result > 0 ? 1 : result < 0 ? -1 : 0;
    }
    /**
	 * �����������һ���е���һ��.���������� Calendar.MONDAY ,Calendar.TUESDAY �жϡ�
	 * @param date
	 * @return
	 */
	 public static int dayOfWeek(Date date) {
		return getDateField(date, Calendar.DAY_OF_WEEK);
	} 
	 
	 public static int getDateField(Date date, int field){
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 return calendar.get(field);
	 }
	 
	 public static long getDaysBetween(Date left, Date right){
		 long leftL = DateUtils.from(left).startOfDay().milliseconds();
		 long rightL = DateUtils.from(right).startOfDay().milliseconds();
		 long result = leftL - rightL;
		 return result / MILLIS_OF_DAY;
	 }
   //////////////////////////////////////////��Ч�������////////////////////////////////////////
    /**
     * ������ �ж�ĳ�������Ƿ�����ĳ�����ڷ�Χ��,�����ڷ�Χ����ʱ���봦��
     * @param date  ĳ������
     * @param dateLeft ���ڷ�Χ����ֵ 
     * @param dateRight ���ڷ�Χ����ֵ
     * @return ���ڷ�Χ�ڷ���true�����򷵻ط�
     */
    public static boolean isBetweenDate(Date date, Date dateLeft, Date dateRight){
    	return date.getTime() < dateLeft.getTime()? false : 
    				date.getTime() > dateRight.getTime() ? false : true;
    }
    
    /**
     * ������ �ж�ĳ�������Ƿ���ĳ�����ڷ�Χ����Ч��
     * 		�����ڷ�Χ����˽�ʱ��������Ϊ00��00��00.000 
     * 		�����ڷ�Χ���жν�ʱ��������Ϊ23��59��59.999
     * @param date  ĳ������
     * @param dateLeft ���ڷ�Χ����ֵ 
     * @param dateRight ���ڷ�Χ����ֵ
     * @return ���ڷ�Χ�ڷ���true�����򷵻ط�
     */
    public static boolean isEffetiveDay(Date date,Date dateLeft, Date dateRight){
    	dateLeft = from(dateLeft).startOfDay().date();
    	dateRight = from(dateRight).endOfDay().date();
    	return isBetweenDate(date, dateLeft, dateRight	);
    }
    
    /**
     * ��������ȡ�ж�����������Ч���ڷ�Χ�ڵ�sql�ַ����� ע��ʱ�侫ȷ���졣
     * 		�жϵĹ������ڵĽ���ʱ�� ��yyyy-MM-dd 23:59:59�����ڵ�����Ч��Χ�����
     * 					���ڵĿ�ʼʱ�� ��yyyy-MM-dd 00:00:00��С�ڵ�����Ч��Χ���Ҷ�
     *      �ù��򲻻���������������ڷ�Χ���ε����©�жϡ�
     * @param date     ����
     * @param effectiveDateField  ��Ч���ڵ��ֶ���
     * @param expireDateField     ʧЧ���ڵ��ֶ���
     * @return
     */
    public static StringBuffer effectiveDaySql(Date date, String effectiveDateField, String expireDateField){
		return effectiveCommonSql(expireDateField, from(date).startOfDay().formatString(DETAIL_FORMAT_STR), 
					effectiveDateField, from(date).endOfDay().formatString(DETAIL_FORMAT_STR)
    			);
    }
    
    public static StringBuffer effectiveDaySql(String field, Date left, Date right){
    	return effectiveCommonSql(field, from(left).startOfDay().formatString(DETAIL_FORMAT_STR), 
    			field, from(right).endOfDay().formatString(DETAIL_FORMAT_STR)
    			);
    }
    
    public static StringBuffer effectiveDayRangeSql(Date left, Date right, String leftField, String rightField){
    	StringBuffer sb = new StringBuffer();
    	sb.append("( ")
    	  .append(effectiveDaySql(left, leftField, rightField))
    	  .append("  or ")
    	  .append(effectiveDaySql(right, leftField, rightField))
    	  .append(" or ")
    	  .append(effectiveDaySql(leftField, left, right))
    	  .append(" )");
    	return sb;
    }
    /**
     * ��������ȡ�ж�����������Ч���ڷ�Χ�ڵ�sql�ַ���, ע��ʱ���Ǿ�ȷ��ʱ���롣
     * 		�жϵĹ������ڵĽ���ʱ�� ��yyyy-MM-dd hh:mm:ss�����ڵ�����Ч��Χ�����
     * 					���ڵĿ�ʼʱ�� ��yyyy-MM-dd hh:mm:ss��С�ڵ�����Ч��Χ���Ҷ�
     *      �ù��򲻻���������������ڷ�Χ���ε����©�жϡ�
     * @param date     ����
     * @param effectiveDateField  ��Ч���ڵ��ֶ���
     * @param expireDateField     ʧЧ���ڵ��ֶ���
     * @return
     */
    public static StringBuffer effectiveTimeSql(Date date, String effectiveDateField, String expireDateField){
    	return effectiveCommonSql(expireDateField, from(date).formatString(DETAIL_FORMAT_STR),
    			effectiveDateField, from(date).formatString(DETAIL_FORMAT_STR));
    }
    
    public static StringBuffer effectiveTimeSql(String field, Date left, Date right){
    	return effectiveCommonSql(field, from(left).formatString(DETAIL_FORMAT_STR), 
    			field, from(right).formatString(DETAIL_FORMAT_STR) );
    }
    
    public static StringBuffer effectiveTimeRangeSql(Date left, Date right, String leftField, String rightField){
    	StringBuffer sb = new StringBuffer();
    	sb.append("( ")
    	  .append(effectiveTimeSql(left, leftField, rightField))
    	  .append("  or ")
    	  .append(effectiveTimeSql(right, leftField, rightField))
    	  .append(" or ")
    	  .append(effectiveTimeSql(leftField, left, right))
    	  .append(" )");
    	return sb;
    }
    
    private static StringBuffer effectiveCommonSql(String leftField,  String leftDateStr, String rightField, String rightDateStr){
    	StringBuffer sb = new StringBuffer();
    	sb.append(" (")
  	  	  .append(leftField).append(" >= {ts'")
  	  	  .append(leftDateStr)
  	  	  .append("'} ")
  	  	  .append("and ").append(rightField)
  	  	  .append(" <={ts'")
  	  	  .append(rightDateStr)
  	  	  .append("'} )");
    	return sb;
    }
	
//////////////////////////////////////�����ַ���������////////////////////////////////////////	
	 /**
	  * ���ô����ģʽƥ���ַ���������Date����
	 * @param str    Ŀ���ַ���
	 * @param format ģʽ�ַ���
	 * @return       ����Date����
	 * @throws ParseException  ����ʧ�ܵ�ʱ���׳��쳣
	 */
	public static Date parse(String str, String format) throws ParseException{
		 SimpleDateFormat simple = new SimpleDateFormat(format);
		 return simple.parse(str);
	 }
	 /**
	  * ����yyyy-MM-ddģʽ������str�ַ���
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date parseSimpleString(String str) throws ParseException{
		 return parse(str, SIMPLE_FORMAT_STR);
	 }
	 /**
	 * ����yyyy-MM-dd HH:mm:ssģʽ������str�ַ���
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDetailString(String str) throws ParseException{
		 return parse(str, DETAIL_FORMAT_STR);
	 }
	 
/////////////////////////////////////Date��������/////////////////////////////////////
	 /**
		 * ��ȡDate�Ļ�������
		 * ʹ�÷����磺DateUtil.from(DATE).addMonth(1).startOfDay().get()
		 *           ����¸���ͬһ��Ŀ�ʼʱ��Date���󣬱���������2010-03-11 10:49:33.23
		 * �����ڶ�����õ������ʹ�ø�API
		 * @param date ��ʼ������
		 * @return
		 */
		public static DateObject from(Date date){
			return new DateObject().from(date);
		}
		/**
		 * ��ȡָ�����ڵĻ�������
		 * @param time
		 * @return
		 */
		public static DateObject from(Timestamp time){
			return new DateObject().from(time);
		}
		/**
		 * ��ȡָ�����ڵĻ�������
		 * @param time
		 * @return
		 */
		public static DateObject from(long time){
			return new DateObject().fromMillis(time);
		}
		
		/**
		 * ��ȡָ�����ڵĻ����������Ϊ�գ�ʹ�õ�ǰ����
		 * @param time
		 * @return
		 */
		public static DateObject fromDefNow(Timestamp time){
			return time == null? fromNow() : from(time);
		}
		/**
		 * ��ȡָ�����ڵĻ����������Ϊ�գ�ʹ�õ�ǰ����
		 * @param date
		 * @return
		 */
		public static DateObject fromDefNow(Date date){
			return date == null? fromNow() : from(date);
		}
		
		/**
		 * ��ȡ��ǰ���ڵĻ�������
		 * @return
		 */
		public static DateObject fromNow(){
			return new DateObject();
		}
		
		
		/**
		 * Date�Ļ����
		 * 0������from(),fromTimestamp(),fromMillis()��Date,Timestamp,long���ʹ���������
		 * 1������year(),month(),day(),hour(),minute(),second()��ȡ��Ӧ���ڶ�Ӧ��������ʱ����
		 * 2������year(X),month(X),day(X),hour(X),minute(X),second(X)���ö�Ӧ���ڵ�������ʱ����
		 * 3������addYear(X),addMonth(X),addDay(X),addHour(X),addMinute(X),addSecond(X)
		 *           ���Ӷ�Ӧ���ڵ�������ʱ���루������ǰ�ƣ����������ƣ�
		 * 4������cutTail/millisecond(X)/millisecond() �ص�����ĺ���/����/��ȡ�����ĺ�����
		 * 5������startOfDay/endOfDay/startOfMonth/endOfMonth
		 * 			�ֱ����ڻ�ȡһ��Ŀ�ʼ/������һ���µĿ�ʼ/����������
		 * 6������reset�������û���������
		 * 7������daysOfMonth��ȥ��Ӧ���������µ�����
		 * 8������date/timestamp/milliseconds�ֱ����ڻ�ȡDate/Timestamp���ڶ���,����ϸ�ĺ�����
		 * 9������simpleString/detailString/toString/timeString/formatString(String)
		 * 			�ֱ����ڻ�ȡ������yyyy-MM-dd��/(yyyy-MM-dd HH:mm:ss)
		 * 			/��yyyy-MM-dd HH:mm:ss.SS��/��HH:mm:ss��/ָ����ʽ�ġ��������ַ���
		 * ע�⣺1�����ܶ��̲߳�������Ķ��󣬷���������Ԥ�ϡ�
		 *       2��month��������/�õ����·��Ǵ�1��ʼ��Calendar�ṩ��API�Ǵ�0��ʼ��ʹ��ʱ����ע���
		 * @author run_zheng
		 */
		public static class DateObject {
			private static final int MAX_NANOS = 999999999;
			private static final int MAX_HOUR = 23;
			private static final int MAX_MIN_SEC = 59;
			private static final int MAX_MILLIS = 999;
			private Calendar calendar =  Calendar.getInstance();
			private Date date = null;
		//	private int nanos = 0; //����
			//�������޷����������ʵ��
			DateObject(){}
			
			public DateObject from(Date date) {
				calendar.setTime(date);
				this.date  = date;
		//		this.nanos = 0;
				return this;
			}
			
			public DateObject fromTimestamp(Timestamp time){
				calendar.setTimeInMillis(time.getTime());
			//	nanos = time.getNanos();
				return this;
			}
			
			public DateObject fromMillis(long time) {
				calendar.setTimeInMillis(time);
				return this;
			}
			
			public DateObject year(int year) {
				calendar.set(Calendar.YEAR, year);
				return this;
			}
			public DateObject month(int month) {
				calendar.set(Calendar.MONTH, month - 1);
				return this;
			}
			public DateObject day(int day) {
				calendar.set(Calendar.DAY_OF_MONTH, day);
				return this;
			}
			public DateObject hour(int hour) {
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				return this;
			}
			public DateObject minute(int minute) {
				calendar.set(Calendar.MINUTE, minute);
				return this;
			}
			public DateObject second(int second) {
				calendar.set(Calendar.SECOND, second);
				return this;
			}
			
			public DateObject addYear(int year) {
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year);
				return this;
			}
			public DateObject addMonth(int month) {
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
				return this;
			}
			public DateObject addDay(int day) {
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
				return this;
			}
			public DateObject addHour(int hour) {
				calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
				return this;
			}
			public DateObject addMinute(int minute) {
				calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minute);
				return this;
			}
			public DateObject addSecond(int second) {
				calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + second);
				return this;
			}
			
			public DateObject addMillisecond(int millisecond){
				calendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND) + millisecond);
				return this;
			}
			
			public int year() {
				return calendar.get(Calendar.YEAR);
			}
			public int month() {
				return calendar.get(Calendar.MONTH) + 1;
			}
			public int day() {
				return calendar.get(Calendar.DAY_OF_MONTH);
			}
			public int hour() {
				return calendar.get(Calendar.HOUR_OF_DAY);
			}
			public int minute() {
				return calendar.get(Calendar.MINUTE);
			}
			public int second() {
				return calendar.get(Calendar.SECOND);
			}
			
			public DateObject cutTail(){
				calendar.set(Calendar.MILLISECOND, 0);
			//	nanos(0);
				return this;
			}
			public DateObject millisecond(int tail){
				calendar.set(Calendar.MILLISECOND, tail);
				return this;
			}
			public int millisecond(){
				return calendar.get(Calendar.MILLISECOND);
			}
			
			/*public int nanos(){
				return nanos;
			}
			
			public DateAccompany nanos(int nanos){
				this.nanos = nanos;
				return this;
			}*/
			
			public DateObject startOfDay(){
				this.hour(0).minute(0).second(0).cutTail();
				return this;
			}
			public DateObject endOfDay(){
				this.hour(MAX_HOUR).minute(MAX_MIN_SEC)
					.second(MAX_MIN_SEC).millisecond(MAX_MILLIS); //.nanos(MAX_NANOS);
				return this;
			}
			
			public DateObject startOfMonth(){
				this.day(1).startOfDay();
				return this;
			}
			
			public DateObject endOfMonth(){
				this.day(this.daysOfMonth()).endOfDay();
				return this;
			}
			
			public int daysOfMonth(){
				return this.calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			
			public DateObject reset(){
				if(date == null)
					calendar = Calendar.getInstance();
				else calendar.setTime(date);
				return this;
			}
			
			public DateObject reset(Date date){
				calendar.setTime(date);
				return this;
			}
			public DateObject reset(DateObject ac){
				calendar.setTime(ac.date());
				return this;
			}
			
			public Date date(){
				return calendar.getTime();
			}
			
			public Timestamp timestamp(){
				Timestamp time = new Timestamp(calendar.getTimeInMillis());
			//	time.setNanos(this.nanos);
				return time;
			}
			
			public Time time(){
				this.year(1970).month(1).day(1);
				Time time = new Time(calendar.getTimeInMillis());
				return time;
			}
			public long milliseconds(){
				return calendar.getTimeInMillis();
			}
			
			public String simpleString(){
				return DateUtils.SIMPLE_FORMAT.format(date());
			}
			
			public String detailString(){
				return DateUtils.DETAIL_FORMAT.format(date());
			}
			
			public String formatString(String format){
				SimpleDateFormat formater = new SimpleDateFormat(format);
				return formater.format(date());
			}
			
			public String timeString(){
				return formatString(TIME_FORMAT_STR);
			}
			
			public String toString(){
				return formatString(ALLFIELD_FORMAT_STR);
			}
		}
		
		/**
	     * 
	     * �������������ھ�ȷ����Ƚ�.
	     * ע�⣺����Ƚϲ�����ʱ,�֣��룬����.
	     * 
	     * @param dateLeft ��ΪNULL��ΪNOW����
	     * @param dateRight ��ΪNULL��ΪNOW����
	     * @return int 
	     * 		-1  dateLeft < dateRight
	     * 		0   dateLeft = dateRight
	     * 	    1   dateLeft > dateRight
	     * 
	     * @throws ParseException
	     * @author:junwu_tang
	     * ����ʱ�䣺2006-11-27 <p>
	     */
	    public static int compareByDate(Date dateLeft, Date dateRight) throws ParseException{
	        
	        //���û�д�������������Ϊ�뵱ǰ���ڱȽ�.
	        Date ld = simpleDate(dateLeft);
	        
	        //���û�д��������ң�����Ϊ�뵱ǰ���ڱȽ�.        
	        Date sd = simpleDate(dateRight);
	        
	    	return ld.compareTo(sd);
	    }
	    
	    /**
		 * 
		 * ���������ؾ�ȷ���������,��ʾ��ʽΪyyyy-MM-dd������
		 * 
		 * @param date java.util.Date 
		 * @return java.util.Date ��yyyy-MM-dd��ʽ���������.
		 * @throws ParseException
		 * @author:junwu_tang
		 * ����ʱ�䣺2006-11-27 <p>
		 */
		public static Date simpleDate(Date date) throws ParseException{
			Date d = date==null ? date : new Date();
			return SIMPLE_FORMAT.parse(SIMPLE_FORMAT.format(d));
		}
}

