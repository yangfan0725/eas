package com.kingdee.eas.fdc.sellhouse.client;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fi.fa.manage.FaCurCardInfo;
import com.kingdee.eas.framework.client.CoreBillEditUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class VerifyBigDecimalOperation {

	/**EaEb2Ec
	 * ͬһ��¼TABLE�� ��Stringһ������������String2����Ԫ����� ��ֵ ����һ����ԪString3��
	 * @param string1 
	 * @param string2 
	 * @param string3 
	 * */

		public static void Eab2c(KDTable kdtEntrys,String name1,String name2, String name3) {
			BigDecimal count1=new BigDecimal(0);//name1
			BigDecimal count2=new BigDecimal(0);//name2
			BigDecimal sum   =new BigDecimal(0);// ��
			for(int i=0;i<kdtEntrys.getRowCount();i++)
			{
				if(kdtEntrys.getRow(i).getCell(name1)!=null&&kdtEntrys.getRow(i).getCell(name1).getValue()!=null){
					count1=new BigDecimal(""+kdtEntrys.getRow(i).getCell(name1).getValue().toString()+"");
				}
				if(kdtEntrys.getRow(i).getCell(name2)!=null&&kdtEntrys.getRow(i).getCell(name2).getValue()!=null){
					count2=new BigDecimal(""+kdtEntrys.getRow(i).getCell(name2).getValue().toString()+"");
				}
				sum=count1.add(count2);
				if(kdtEntrys.getRow(i).getCell(name3)!=null){
					kdtEntrys.getRow(i).getCell(name3).setValue(sum);
				}
				count1=new BigDecimal(0);//name1
				count2=new BigDecimal(0);//name2
			}

		}
		/**E2E
		 * ͬһ��¼�� ��Stringһ������������String2����Ԫ������� ������ 
		 * @param string1 
		 * @param string2 
		 * */
		public static BigDecimal E2EADD(KDTable kdtEntrys,String name1,String name2) {
			BigDecimal count1=new BigDecimal(0);//name1
			BigDecimal count2=new BigDecimal(0);//name2
			BigDecimal sum   =new BigDecimal(0);// ��
			for(int i=0;i<kdtEntrys.getRowCount();i++)
			{
				if(kdtEntrys.getRow(i).getCell(name1)!=null&&kdtEntrys.getRow(i).getCell(name1).getValue()!=null){
					count1=count1.add((BigDecimal)kdtEntrys.getRow(i).getCell(name1).getValue());
				}
				if(kdtEntrys.getRow(i).getCell(name2)!=null&&kdtEntrys.getRow(i).getCell(name1).getValue()!=null){
					count2=count2.add((BigDecimal)kdtEntrys.getRow(i).getCell(name2).getValue());
				}
			}
			sum=count1.add(count2);
			return sum;
		}
		/**E2E
		 * ͬһ��¼��ͬһ�� ������� ������ 
		 * @param string1 
		 * @param string2 
		 * */
		public static void E2EADDonly(KDTable kdtEntrys,String name1,String name2,String name3) {
			BigDecimal count1=new BigDecimal(0);//name1
			BigDecimal count2=new BigDecimal(0);//name2
			BigDecimal sum   =new BigDecimal(0);// ��
			for(int i=0;i<kdtEntrys.getRowCount();i++)
			{
				if(kdtEntrys.getRow(i).getCell(name1)!=null&&kdtEntrys.getRow(i).getCell(name1).getValue()!=null){
					if(kdtEntrys.getRow(i).getCell(name2)!=null&&kdtEntrys.getRow(i).getCell(name1).getValue()!=null){
						if(kdtEntrys.getRow(i).getCell(name3)!=null){
							count1=((BigDecimal)kdtEntrys.getRow(i).getCell(name1).getValue()).add((BigDecimal)kdtEntrys.getRow(i).getCell(name2).getValue());
							kdtEntrys.getRow(i).getCell(name3).setValue(count1);
						}
						
					}
				}
			}
		}
		/**E2E
		 * ͬһ��¼�� ��Stringһ����Ԫ����� ������ ��
		 * @param string1 
		 * */
		public static BigDecimal EADD(KDTable kdtEntrys,String name1) {
			BigDecimal count1=new BigDecimal(0);//name1
			BigDecimal sum   =new BigDecimal(0);// ��
			for(int i=0;i<kdtEntrys.getRowCount();i++)
			{
				if(kdtEntrys.getRow(i).getCell(name1)!=null&&kdtEntrys.getRow(i).getCell(name1).getValue()!=null){
					count1=count1.add((BigDecimal)kdtEntrys.getRow(i).getCell(name1).getValue());
				}
			}
			sum=count1;
			return sum;
		}
		/**E2E �˷�
		 * ͬһ��¼�� ��Stringһ������������String2����Ԫ������� ������ 
		 * @param string1 
		 * @param string2 
		 * */
		public static BigDecimal E2EMultiply(KDTable kdtEntrys,String price,String count,String amount) {
			BigDecimal sart=new BigDecimal(0);//name1
			BigDecimal count1=new BigDecimal(0);//name1
			BigDecimal count2=new BigDecimal(0);//name2
			BigDecimal mult  =new BigDecimal(0);// �ܻ�
			BigDecimal sum	 =new BigDecimal(0);// �ܻ���
			for(int i=0;i<kdtEntrys.getRowCount();i++)
			{
				count1=sart;
				count2=sart;
				if(kdtEntrys.getRow(i).getCell(price)!=null&&kdtEntrys.getRow(i).getCell(price).getValue()!=null){
					count1=(BigDecimal)kdtEntrys.getRow(i).getCell(price).getValue();
				}
				if(kdtEntrys.getRow(i).getCell(count)!=null&&kdtEntrys.getRow(i).getCell(count).getValue()!=null){
					count2=(BigDecimal)kdtEntrys.getRow(i).getCell(count).getValue();
				}
				mult=count1.multiply(count2);
				sum=sum.add(mult);
				if(kdtEntrys.getRow(i).getCell(amount)!=null){
					kdtEntrys.getRow(i).getCell(amount).setValue(mult);
				}
			}
			return sum;
		}
		/**D2B
		 * 
		 * @param KDDatePicker          ʱ��ؼ� ��ʼʱ��
		 * @param KDFormattedTextField  BigDecimal����   ����
		 * @param KDDatePicker  		ʱ��ؼ� ����ʱ��
		 * */
		public static void Date2BigDecimal(KDDatePicker pkplanStartDate,KDFormattedTextField txtplanDays,KDDatePicker pkplanEndDate) {
			Calendar calendarNowBefore = Calendar.getInstance();
			calendarNowBefore.setTime(new Date());
			if(pkplanStartDate.getValue()!=null&&txtplanDays.getValue()!=null){
				calendarNowBefore.setTime((Date)pkplanStartDate.getValue());
				calendarNowBefore.add(Calendar.DAY_OF_YEAR,((BigDecimal)txtplanDays.getValue()).intValue());
			}else{
				txtplanDays.setValue(null);
		   		SysUtil.abort();
			}
			pkplanEndDate.setValue(calendarNowBefore.getTime());
		}


}
