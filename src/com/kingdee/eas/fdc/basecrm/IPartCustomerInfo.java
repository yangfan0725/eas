package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.IObjectValue;

//�ͻ�ֵ����ӿڣ�����ͳһ�����ҵ��ϵͳ�ͻ��Ĳ����߼�
public interface IPartCustomerInfo extends IObjectValue{
	 FDCMainCustomerInfo getMainCustomer();
	 
	 void setMainCustomer(FDCMainCustomerInfo item);
}
