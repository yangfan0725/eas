/**
 * 
 */
package com.kingdee.eas.fdc.schedule;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.kingdee.eas.fdc.basedata.FDCNumberHelper;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������
 * 
 * @author �ź���
 * @version EAS7.0
 * @createDate 2011-12-30
 * @see
 */

public class OpReportBaseHelper {
	public static final String WEEK_DESC = "�ܱ���㱨��¼:";
	public static final String MONTH_DESC = "�±���㱨��¼:";

	/**
	 * ��������ȡ���ڵ�field�����꣬�¡���
	 * @param field ��Calendar.YEAR��
	 */
	public static int getDateFiled(Date d, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal.get(field);
	}
	
	public static boolean stringCompare(String oldValue, String newValue, boolean isWeek) {
		if (oldValue == null) {
			oldValue = "";
		}
		if (newValue == null) {
			newValue = "";
		}
		if (oldValue != null && !"".equals(oldValue)) {
			if (isWeek) {
				if (oldValue.startsWith(WEEK_DESC)) {
					newValue = WEEK_DESC + newValue;
				}
			} else {
				if (oldValue.startsWith(MONTH_DESC)) {
					newValue = MONTH_DESC + newValue;
				}
			}
		}
		if ((oldValue == null && newValue == null)
				|| ((oldValue != null && newValue != null) && "".equals(oldValue.trim()) && "".equals(newValue.trim()))) {
			return true;
		}
		if (newValue != null) {
			if (newValue.length() > 80) {
				newValue = newValue.substring(0, 79);
			}
		}
		if (oldValue != null && newValue != null && oldValue.trim().equals(newValue.trim())) {
			return true;
		}
		return false;
	}

	public static boolean bigDecimalCompare(BigDecimal bd1, BigDecimal bd2) {
		if (bd1 == null) {
			bd1 = FDCNumberHelper.ZERO;
		}
		if (bd2 == null) {
			bd2 = FDCNumberHelper.ZERO;
		}
		if (bd1.compareTo(bd2) == 0) {
			return true;
		}
		return false;
	}

	public static boolean dateCompare(Date date1, Date date2) {
		if (date1 == date2) {
			return true;
		}
		if (date1 != null && date2 != null) {
			if((date1.getTime() - date2.getTime())==0){
				return true;
			}
		}
		return false;
	}
}
