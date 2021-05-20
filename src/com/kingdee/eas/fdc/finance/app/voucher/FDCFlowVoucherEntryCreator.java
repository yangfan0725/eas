package com.kingdee.eas.fdc.finance.app.voucher;

import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;

/**
 * ��Ŀ��ʧ
 * @author xiaohong_shi
 *
 */
public class FDCFlowVoucherEntryCreator extends
		AbstractFDCVoucherEntryCreator {

	public FDCFlowVoucherEntryCreator(Context ctx) {
		super(ctx);
	}
	
	
	/***
	 * ��Ŀ��ʧ
	 * 1�����ȿ�
	 * �裺�������/Ӫ�����ã���ֵ�4301.408.03Ӫ���ƹ���µģ�
	 */
	public AccountViewInfo getProgressAccountView(CostAccountInfo key) {
		
		// TODO Auto-generated method stub
		if(key.getFlag()==1)
			return beforeAccountViewInfo.getMarketFees();
		else
			return beforeAccountViewInfo.getAdminFees();
	}
	
	/***
	 * ��Ŀ��ʧ
	 * 2��������һ�ʣ�
	 * �裺Ԥ���˿�_�����ɱ�_�����ɱ�
	 */
	public AccountViewInfo getFirstSettleAccountView(CostAccountInfo key) {
		// TODO �Զ����ɷ������
		return beforeAccountViewInfo.getBeforeOtherAccount();
	}
	
	/***
	 * ��Ŀ��ʧ
	 * 3) �����ڶ��ʣ�
	 * Ӧ���˿�_�����ɱ�_���ȿһ�廯��Ŀ���ö�ӦӦ�����ȿ
	 */
	public AccountViewInfo getSettleAccountView(CostAccountInfo key) {
		// TODO Auto-generated method stub
		return beforeAccountViewInfo.getProAccount();
	}
	
	/***
	 * ��Ŀ��ʧ
	 * 4�����޿�
	 * �裺Ӧ���˿�_�����ɱ�_���޿�
	 */
	public AccountViewInfo getGrtAccountView(CostAccountInfo key) {
		return beforeAccountViewInfo.getSettAccount();
	}

}
