package com.kingdee.eas.fdc.finance.app.voucher;

import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitException;
import com.kingdee.eas.fi.cas.PaymentBillInfo;

public interface IFDCVoucherEntryCreator {
	/**
	 * �ͻ��ഫ����������������¼������ƾ֤
	 * @param parma param�ڰ����������PaymentBillCollection coll= param.get("PaymentBillCollection");
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws Exception 
	 */
	public void createEntrys(Map parma) throws BOSException, EASBizException;
	
	
	
	/**
	 * ���ȿ��Ӧ��Ŀ
	 * @param acctId
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	abstract AccountViewInfo getProgressAccountView(CostAccountInfo key) throws BOSException, EASBizException;
	/**
	 * ��һ�ʽ�����Ӧ��Ŀ
	 * @param acctId
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	abstract AccountViewInfo getFirstSettleAccountView(CostAccountInfo key) throws EASBizException, BOSException;
	/**
	 * �ڶ��ʽ�����Ӧ��Ŀ
	 * @param acctId
	 * @return
	 */
	abstract AccountViewInfo getSettleAccountView(CostAccountInfo key);
	/**
	 * ���޿��Ӧ��Ŀ
	 * @param acctId
	 * @return
	 */
	abstract AccountViewInfo getGrtAccountView(CostAccountInfo key);
	
	abstract AccountViewInfo getAccountView(CostAccountInfo costAccount,PaymentBillInfo paymentBillInfo,boolean isProgress,boolean isConWithoutText) throws BOSException, EASBizException;
	/**
	 * ͨ���ɱ���Ŀ���Ҷ�Ӧ�ķ�Ʊ��Ŀ���
	 * ��Ʊ��Ŀ����Ŀ״̬�޹�
	 * @param key
	 * @return
	 * @throws EASBizException 
	 */
	abstract AccountViewInfo getInvoiceAccountView(CostAccountInfo costAccount) throws EASBizException;
	
	/**
	 * ������Ŀ״̬ͨ���ɱ���Ŀ���Ҷ�Ӧ�Ļ�ƿ�Ŀ
	 * @param key
	 * @return
	 * @throws EASBizException 
	 */
	abstract AccountViewInfo getAccountWithCostAccount(CostAccountInfo costAccountInfo) throws EASBizException;
	abstract Map getCacheMap();
}
