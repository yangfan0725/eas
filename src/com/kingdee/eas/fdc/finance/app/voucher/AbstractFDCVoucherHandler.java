package com.kingdee.eas.fdc.finance.app.voucher;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fi.cas.PaymentBillInfo;

public abstract class AbstractFDCVoucherHandler{
	static final String PARTAOFBILLS = "partAOfBills_";
	static final String DEDUCTBILLS = "deductBills_";
	static final String PARTACONFIRMBILLS = "partAConfirmBills_";
	static final String COMPENSATIONBILLS = "compensationBills_";
	static final String GUERDONBILLS = "guerdonBills_";
	private Context ctx = null;
	private IFDCVoucherEntryCreator creator=null;
	protected Context getCtx() {
		return this.ctx;
	}

	protected void setCtx(Context ctx) {
		this.ctx = ctx;
	}
	protected IFDCVoucherEntryCreator getFDCVoucherEntryCreator(){
		return this.creator;
	}
	
	protected void setFDCVoucherEntryCreator(IFDCVoucherEntryCreator creator){
		this.creator = creator;
	}
	
	/***
	 * һ�廯��ģʽ
	 */
	protected String financial = "";
	/***
	 * ��һ�廯����ۿ�ΥԼ
	 */
	protected boolean financialExtend = false;
	/***
	 * �������븶�����
	 */
	protected boolean isSeparate = false;
	/***
	 * ����һ�廯����Ʊ
	 */
	protected boolean isInvoiceMrg = false;
	/**
	 * ��ģʽ����Ʊ
	 */
	protected boolean isSimpleInvoice = false;
	/**
	 * �Է�Ʊ���Ϊ׼�ƿ����ɱ�ʱ��ƾ֤��¼Ӧ���˿�ĳ�ֹ���
	 */
	protected boolean isInvoiceOffset = false;
	/**
	 * ��ģʽ����ƾ֤�Ƿ�У����
	 */
	protected boolean isPaymentSplit = false;
	
	/***
	 * �Ѿ�ͬ������¼��bills
	 */
	protected Map hasSynchBills = null;
	/***
	 * ȫ�ֵ�һ�廯��Ŀ����
	 */
	protected BeforeAccountViewInfo beforeAccountViewInfo = null;
	/***
	 * �ɱ���Ŀ��Ӧ�Ļ�ƿ�Ŀ
	 * �ռ任ʱ��
	 * �������еĳɱ���Ŀ��Ӧ�Ļ�ƿ�Ŀ
	 * [�ø����Ӧ�Ĺ�����Ŀ����]
	 */
	protected Map costAccountWithAccountMap = null; 
	/***
	 * �ۿ��Ӧ�Ŀ�Ŀ����Map
	 */
	protected Map deductAccountMap = null; 
	/***
	 * ��Ҫ���µĳɱ������ֵ�idSet
	 */
	protected Set needUpdatePaymentSplitSet = null;
	/***
	 * ��Ҫ���µķǳɱ������ֵ�idSet
	 */
	protected Set needUpdatePaymentNoCostSplitSet = null;
	

	/****
	 * ��Ŀδ��ȡ
	 * 1�����ȿ� 
	 * �裺Ԥ���˿�_�����ɱ�_�����ɱ� 
	 * @throws EASBizException 
	 * @throws BOSException 
	 */
	public AccountViewInfo getProgressAccountView(CostAccountInfo key) throws EASBizException, BOSException {
		return getFDCVoucherEntryCreator().getProgressAccountView(key);
	}

	/***
	 * ��Ŀδ��ȡ
	 * 2��������һ�ʣ�
	 * * �裺Ԥ���˿�_�����ɱ�_�����ɱ� 
	 * @throws EASBizException 
	 * @throws BOSException 
	 */
	public AccountViewInfo getFirstSettleAccountView(CostAccountInfo key) throws EASBizException, BOSException {
		return getFDCVoucherEntryCreator().getFirstSettleAccountView(key);
	}
	
	/***
	 * ��Ŀδ��ȡ
	 * 3) �����ڶ��ʣ�
	 * * �裺Ӧ���˿�_�����ɱ�_���ȿһ�廯��Ŀ���ö�ӦӦ�����ȿ 
	 */
	public AccountViewInfo getSettleAccountView(CostAccountInfo key) {
		return getFDCVoucherEntryCreator().getSettleAccountView(key);
	}
	
	/***
	 * ��Ŀδ��ȡ
	 * 4�����޿�
	 * �裺Ӧ���˿�_�����ɱ�_���޿�
	 * ����
	 */
	public AccountViewInfo getGrtAccountView(CostAccountInfo key) {
		return getFDCVoucherEntryCreator().getGrtAccountView(key);
	}
	
	
	protected void init(Map param) throws BOSException{
		needUpdatePaymentSplitSet = (Set)param.get("needUpdatePaymentSplitSet");
		needUpdatePaymentNoCostSplitSet = (Set)param.get("needUpdatePaymentNoCostSplitSet");
		
		//ͨ���������ȡ�����ɷ�¼����Ҫ������ֵ����������ۿΥԼ,�����������ֵ���Ϣ,
		//ע�⣺���������ı��Լ����ı�
		
		/***
		 * һ�廯ģʽ
		 */
		financial = (String)param.get("financial");
		financialExtend = param.get("financialExtend")!=null?((Boolean)param.get("financialExtend")).booleanValue():false;
		isSeparate = param.get("isSeparate")!=null?((Boolean)param.get("isSeparate")).booleanValue():false;
		isInvoiceMrg = param.get("isInvoiceMrg")!=null?((Boolean)param.get("isInvoiceMrg")).booleanValue():false;
		isSimpleInvoice = param.get("simpleInvoice")!=null?((Boolean)param.get("simpleInvoice")).booleanValue():false;
		isInvoiceOffset = param.get("invoiceOffset")!=null?((Boolean)param.get("invoiceOffset")).booleanValue():false;
		isPaymentSplit = param.get("isPaymentSplit")!=null?((Boolean)param.get("isPaymentSplit")).booleanValue():false;
		/***
		 * �Ѿ�ͬ������bills
		 */
		hasSynchBills = (Map)param.get("hasSynchBills");

		
		deductAccountMap = (Map)param.get("deductAccountMap");
		beforeAccountViewInfo = (BeforeAccountViewInfo)param.get("beforeAccountViewInfo");
		costAccountWithAccountMap = (Map)param.get("costAccountWithAccountMap");
		
	}

	/**
	 * ����Ŀ״̬ͨ���ɱ���Ŀ���Ҷ�Ӧ�Ļ�ƿ�Ŀ
	 * @param key
	 * @return
	 * @throws EASBizException 
	 */
	protected AccountViewInfo getAccountView(CostAccountInfo costAccount,PaymentBillInfo paymentBillInfo,boolean isProgress,boolean isConWithoutText) throws BOSException, EASBizException{
		return getFDCVoucherEntryCreator().getAccountView(costAccount, paymentBillInfo, isProgress, isConWithoutText);
	}
	
	/**
	 * ͨ���ɱ���Ŀ���Ҷ�Ӧ�ķ�Ʊ��Ŀ���
	 * ��Ʊ��Ŀ����Ŀ״̬�޹�
	 * @param key
	 * @return
	 * @throws EASBizException 
	 */
	protected AccountViewInfo getInvoiceAccountView(CostAccountInfo costAccount) throws EASBizException{
		return getFDCVoucherEntryCreator().getInvoiceAccountView(costAccount);
	}
	
	/**
	 * ������Ŀ״̬ͨ���ɱ���Ŀ���Ҷ�Ӧ�Ļ�ƿ�Ŀ
	 * @param key
	 * @return
	 * @throws EASBizException 
	 */
	protected AccountViewInfo getAccountWithCostAccount(CostAccountInfo costAccountInfo) throws EASBizException {
		return getFDCVoucherEntryCreator().getAccountWithCostAccount(costAccountInfo);
	}
	
	/**
	 * ȡ��λ�ң�ʹ�û�����д���
	 * ���漶��ΪCtx�����ɵ����ݱ�������
	 *  by sxhong 2009-07-21 19:24:03
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected CurrencyInfo getBaseCurrency() throws BOSException, EASBizException {
		if(getFDCVoucherEntryCreator().getCacheMap().get("baseCurrency")==null){
			getFDCVoucherEntryCreator().getCacheMap().put("baseCurrency", FDCHelper.getBaseCurrency(getCtx()));
		}
		return (CurrencyInfo)getFDCVoucherEntryCreator().getCacheMap().get("baseCurrency");
	}
	/**
	 * Ԥ����
	 * @param contractID
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	protected boolean isAdvance(String contractID) throws EASBizException, BOSException{
		return isSeparate&&FDCUtils.isContractBill(ctx, contractID);
	}

	/**
	 * dataMap�е�payedAmtMap�� invoiceAmtMap ������ȡһ��������Ϊ����Ƕ��Ÿ���ϲ�����ƾ֤ʱ����������û��ȡ��
	 * 
	 * @author owen_wen 2011-07-07
	 * @param dataMap
	 * @param paymentBillId
	 * @throws BOSException
	 */
	protected void initSplitBillInfos(Map dataMap, String paymentBillId) throws BOSException {
		Set paymentBillIds = new HashSet();
		paymentBillIds.add(paymentBillId);
		if (this.creator instanceof AbstractFDCVoucherEntryCreator) {
			((AbstractFDCVoucherEntryCreator) creator).initSplitBillInfos(dataMap, paymentBillIds);
		}
	}
}
