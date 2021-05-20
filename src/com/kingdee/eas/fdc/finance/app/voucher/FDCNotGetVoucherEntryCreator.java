package com.kingdee.eas.fdc.finance.app.voucher;

import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;

/**
 * ��Ŀδ��ȡ
 * 
 * @author xiaohong_shi
 * 
 */
public class FDCNotGetVoucherEntryCreator extends
		AbstractFDCVoucherEntryCreator {

	public FDCNotGetVoucherEntryCreator(Context ctx) {
		super(ctx);
	}

	/****
	 * ��Ŀδ��ȡ
	 * 1�����ȿ� 
	 * �裺Ԥ���˿�_�����ɱ�_�����ɱ� 
	 * @throws EASBizException 
	 */
	public AccountViewInfo getProgressAccountView(CostAccountInfo key) throws EASBizException {
		return beforeAccountViewInfo.getBeforeOtherAccount();
	}

	/***
	 * ��Ŀδ��ȡ
	 * 2��������һ�ʣ�
	 * * �裺Ԥ���˿�_�����ɱ�_�����ɱ� 
	 * @throws EASBizException 
	 */
	public AccountViewInfo getFirstSettleAccountView(CostAccountInfo key) throws EASBizException {
		return beforeAccountViewInfo.getBeforeOtherAccount();
	}
	
	/***
	 * ��Ŀδ��ȡ
	 * 3) �����ڶ��ʣ�
	 * * �裺Ӧ���˿�_�����ɱ�_���ȿһ�廯��Ŀ���ö�ӦӦ�����ȿ 
	 */
	public AccountViewInfo getSettleAccountView(CostAccountInfo key) {
		// TODO Auto-generated method stub
		return beforeAccountViewInfo.getProAccount();
	}
	
	/***
	 * ��Ŀδ��ȡ
	 * 4�����޿�
	 * �裺Ӧ���˿�_�����ɱ�_���޿�
	 * ����
	 */
	public AccountViewInfo getGrtAccountView(CostAccountInfo key) {
		return beforeAccountViewInfo.getSettAccount();
	}

}
