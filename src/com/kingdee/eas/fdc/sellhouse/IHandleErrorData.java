package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;

public interface IHandleErrorData {
	
	/**
	 * ��Դ������ݽ��д���,���ش�����
	 * @param errorData ������Ĵ�������
	 * @return IHandleReport
	 * */
	IHandleReport handleErrorData(IErrorData errorData) throws EASBizException, BOSException;
	
	String getDes();
}
