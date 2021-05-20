package com.kingdee.eas.fdc.finance.app.voucher;

import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.common.EASBizException;

public interface IFDCCostVoucherHandler {
	/**
	 * ���ɳɱ���¼
	 * @param dataMap
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public IObjectCollection createCostEntrys(Map dataMap) throws EASBizException, BOSException;
	/**
	 * �����¼
	 * @param param
	 * @param costEntrys
	 * @throws BOSException
	 */
	public void save(Map param,IObjectCollection costEntrys) throws BOSException;
	/**
	 * ����֮ǰ�ĳ�ʼ������
	 * @param param
	 */
	public void beforeSave(Map param)  throws BOSException ;
	/**����֮��Ĳ���
	 * @param param
	 */
	public void afterSave(Map param) throws BOSException ;
}
