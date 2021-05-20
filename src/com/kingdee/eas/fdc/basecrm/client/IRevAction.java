package com.kingdee.eas.fdc.basecrm.client;

import java.math.BigDecimal;
import java.util.List;

import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;

/**
 * �տ�ͻ��˸���������չ�ӿ�
 * */
public interface IRevAction {
	/**
	 * ��������ֱ���տ�ʱ����һ���տ���ϸֵ����
	 * */
	IRevListInfo createRevListInfo();
	
	/**
	 * ����ʵ�ָ�ҵ��ϵͳ�ڷ�¼��ѡ��������չ��
	 * */
	List getExpandCols(RevListTypeEnum revListType);
	
	/**
	 * ���ݸ�����ϸ�õ�����
	 * */
	RoomInfo getRoomInfoByRevList(IRevListInfo revListInfo);
	
	/**
	 * ���ݸ�����ϸ��ü�����
	 * */
	BigDecimal getRemissionAmountRevList(IRevListInfo revListInfo);
}
