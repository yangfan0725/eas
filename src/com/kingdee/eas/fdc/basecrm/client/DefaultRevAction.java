package com.kingdee.eas.fdc.basecrm.client;

import java.math.BigDecimal;
import java.util.List;

import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;

/**
 * δ�����޸Ľӿڵ������м̳еĵط����޸�,����Ĭ�Ͽ�ʵ��
 * */
public abstract class DefaultRevAction implements IRevAction {

	public IRevListInfo createRevListInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getExpandCols(RevListTypeEnum revListType) {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getRemissionAmountRevList(IRevListInfo revListInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public RoomInfo getRoomInfoByRevList(IRevListInfo revListInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
