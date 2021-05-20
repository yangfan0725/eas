package com.kingdee.eas.fdc.sellhouse;

import java.sql.SQLException;
import java.util.List;

import com.kingdee.bos.BOSException;

public interface IError {
	
	/**
	 * ƥ��������ļ�¼,���ؼ�¼����
	 * @return List<IErrorData>
	 * @throws BOSException 
	 * @throws SQLException 
	 * */
	List matchErrorDatas() throws BOSException, SQLException;
	
	/**
	 * ���ظ������Ĵ���������
	 * @return List<IHandleErrorData>
	 * */
	List getHandles();
	
	/**
	 * ��ø�������������Ϣ
	 * */
	String getDes();
}
