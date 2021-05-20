package com.kingdee.eas.fdc.sellhouse.client;
/***
 * by ....wancheng
 * */
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.ICrossPrintDataProvider;
import com.kingdee.eas.fdc.sellhouse.AccountReportFacadeFactory;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public class AccountReportPrintDataProvider  implements ICrossPrintDataProvider {
	
	public  AccountReportPrintDataProvider(List id) {
		 ids = null;
	     ishasNext = true;
	     ids = new HashSet(id);
	}
	public boolean hasNext()
	{
	     if(ishasNext)
	        {
	            ishasNext = false;
	            return true;
	        } else
	        {
	            return false;
	        }
	}

	public IRowSet execute(BOSQueryDataSource ds) 
	{
		 IRowSet iRowSet = null;
	     DynamicRowSet row = null;
	     try
	     {
	    	 row = new DynamicRowSet(col.length);
             for(int i = 0; i < col.length; i++)
             {
                 ColInfo ci = new ColInfo();
                 ci.colType = 12;
                 ci.columnName = col[i];
                 ci.nullable = 1;
				 row.setColInfo(i + 1, ci);
             }
				for(iRowSet = AccountReportFacadeFactory.getRemoteInstance().getPrintData(ids);iRowSet.next(); row.insertRow())
				  {
					row.moveToInsertRow();
					String id = iRowSet.getString("¥��");
					if(id!=null){
						row.updateString("id", id);//����
					}
					String customerNames = iRowSet.getString("�ͻ�");
					if(customerNames!=null){
						row.updateString("customerNames", customerNames);//�ͻ�
					}
					String saleManNames = iRowSet.getString("��ҵ����");
					if(saleManNames!=null){
						row.updateString("saleManNames", saleManNames);//��ҵ����
					}
					Date FBusAdscriptionDate = iRowSet.getDate("ǩԼ����");
					if(FBusAdscriptionDate!=null){
						row.updateDate("bizDate", FBusAdscriptionDate);//ǩԼ����
					}
					String  payName = iRowSet.getString("��������");
					if(payName!=null){
						row.updateString("payType.name", payName);//�����
					}
					BigDecimal FAppAmount = iRowSet.getBigDecimal("Ӧ�տ�");
					if(FAppAmount!=null){
						row.updateBigDecimal("signPayListEntry.appAmount", FAppAmount);//Ӧ�ս��
					}
					BigDecimal notFAppAmount = iRowSet.getBigDecimal("δ�տ�");
					if(notFAppAmount!=null){
						row.updateBigDecimal("signPayListEntry.actRevAmount", notFAppAmount);//δ�ս��
					}
					BigDecimal defaultAmount = iRowSet.getBigDecimal("ΥԼ��");
					if(defaultAmount!=null){
						row.updateBigDecimal("loanAmount", defaultAmount);//ΥԼ��
					} 
					int day = iRowSet.getInt("ΥԼ����");
					row.updateInt("room.unit", day);//ΥԼ����
					
					Date appDate = iRowSet.getDate("Ӧ������");
					if(appDate!=null){
						row.updateDate("signPayListEntry.appDate", appDate);//Ӧ������	
					}
					Date lastDate = iRowSet.getDate("����ؿ�����");
					if(lastDate!=null){
						row.updateDate("joinInDate", lastDate);//��������	
					}
					row.updateBigDecimal("accFundAmount", defaultAmount.add(notFAppAmount));//����
					Date   now =new   Date(System.currentTimeMillis()); //��ǰϵͳʱ��
					row.updateDate("onRecordDate", now);//ǩԼ����
					}
				} catch (BOSException e) {
					e.printStackTrace();
				} catch (SQLException e) {
				}
		return row;
	 }
	 static Class _mthclass$(String x0) throws Throwable
	    {
	        try
	        {
	            return Class.forName(x0);
	        }
	        catch(ClassNotFoundException x1)
	        {
	            throw (new NoClassDefFoundError()).initCause(x1);
	        }
	    }
	private static Logger logger;
	private Set ids;
	private boolean ishasNext;
	public static final String col[] = {
	        "id", "saleManNames", "contractTotalAmount", "bizDate", "signPayListEntry.appAmount", "signPayListEntry.appDate", 
	        "signPayListEntry.actRevAmount", "room.unit", "room.floor", "room.serialNumber", "LoanBank.name", "sellProject.name", 
	        "payType.name","customerNames","onRecordDate","joinInDate","loanAmount","accFundAmount","fitmentPrice"};

	static 
	{
	      logger = Logger.getLogger(com.kingdee.eas.fdc.sellhouse.client.AccountReportPrintDataProvider.class);
	}

}
