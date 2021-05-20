package com.kingdee.eas.fdc.finance.app.voucher;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;

/**
 * ��Ŀ�ѻ�ȡ
 * ��Ӧ"������"״̬
 * ProjectStatusInfo.proceedingID
 * @author xiaohong_shi
 *
 */
public class FDCGetVoucherEntryCreator extends
		AbstractFDCVoucherEntryCreator {

	public FDCGetVoucherEntryCreator(Context ctx) {
		super(ctx);
	}	
	

	/***
	 * ��Ŀ������
	 * 1) ���ȿ�
	 * �裺�����ɱ�
	 * ����
	 */
	public AccountViewInfo getProgressAccountView(CostAccountInfo key) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return getAccountWithCostAccount(key);
	}
	
	/***
	 * ��Ŀ������
	 * 2) ����� ��һ�� 
	 * �裺�����ɱ�
	 * ����
	 */
	public AccountViewInfo getFirstSettleAccountView(CostAccountInfo key) throws EASBizException, BOSException {
		// TODO �Զ����ɷ������
		return getAccountWithCostAccount(key);
	}
	
	/***
	 * ��Ŀ������
	 * 3) �����ڶ���
	 * �裺Ӧ���˿�_�����ɱ�_���ȿһ�廯��Ŀ���ö�ӦӦ�����ȿ
	 * ����
	 */
	public AccountViewInfo getSettleAccountView(CostAccountInfo key) {		
		return beforeAccountViewInfo.getProAccount();
	}
	
	/***
	 * ��Ŀ������
	 * 4) ���޿�
	 * �裺Ӧ���˿�_�����ɱ�_���޿�
	 * ����
	 */
	public AccountViewInfo getGrtAccountView(CostAccountInfo key) {
		return beforeAccountViewInfo.getSettAccount();
	}

}
