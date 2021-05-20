package com.kingdee.eas.fdc.finance;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

public class FDCPaymentBillHelper {

	
	/**
	 * @param ctx
	 * @param paymentBill
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws UuidException
	 */
	public static void recountInvoiceAmt(Context ctx, PaymentBillInfo paymentBill) throws BOSException, EASBizException,
			UuidException {
		//��д�������ز�����ķ�Ʊ�ۼƽ��
		FDCSQLBuilder builder = null;
		if (ctx == null) {
			builder = new FDCSQLBuilder();
		} else {
			builder = new FDCSQLBuilder(ctx);
		}
		
		//ȡ���뵥�µ����еĸ���ķ�Ʊ���ĺϼ�
		BigDecimal allInvoiceAmt = FDCHelper.ZERO;
		builder.appendSql("select sum(isnull(FInvoiceAmt, 0)) as allInvoiceAmt from T_FNC_FDCPaymentBill where FPaymentBillID in ( select fid from T_CAS_PaymentBill where FFdcPayReqID = ?) ");
		builder.addParam(paymentBill.getFdcPayReqID());
		IRowSet rowSet = builder.executeQuery();
		if(rowSet!=null && rowSet.size()==1){
			try {
				rowSet.next();
				allInvoiceAmt = rowSet.getBigDecimal("allInvoiceAmt");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//���·��ز�����м��ķ�Ʊ���ϼ�
		builder.clear();
		builder.appendSql("update T_FNC_FDCPaymentBill set FAllInvoiceAmt = ? where FPaymentBillID in ( select fid from T_CAS_PaymentBill where FFdcPayReqID = ?)");
		builder.addParam(allInvoiceAmt);
		builder.addParam(paymentBill.getFdcPayReqID());
		builder.executeUpdate();
		
		
		//��д�������뵥�ķ�Ʊ���
		builder.clear();
		builder.appendSql("update T_CON_PayRequestBill set FInvoiceAmt = ? where fid  = ?");
		builder.addParam(allInvoiceAmt);
		builder.addParam(paymentBill.getFdcPayReqID());
		builder.executeUpdate();
		
		//�����ͬ�ĸ�������Ķ�Ӧ���ۼƷ�Ʊ���
		IPayRequestBill iPayRequestBill = null;
		if (ctx == null) {
			iPayRequestBill = PayRequestBillFactory.getRemoteInstance();
		} else {
			iPayRequestBill = PayRequestBillFactory.getLocalInstance(ctx);
		}
		PayRequestBillInfo payReqInfo = new PayRequestBillInfo();
		payReqInfo = iPayRequestBill.getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(paymentBill.getFdcPayReqID())));
		FDCPaymentBillHelper.recountReqInvoiceAmt(ctx, payReqInfo.getContractId());
	}
	
	/**
	 * @param ctx
	 * @param fdcPayment
	 * @param paymentBill
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws UuidException
	 */
	public static void handleFDCPaymentBillInvoice(Context ctx, IObjectValue fdcPayment, PaymentBillInfo paymentBill)
			throws BOSException, EASBizException, UuidException {
		FDCPaymentBillInfo fdcPaymentBill = (FDCPaymentBillInfo)fdcPayment;
		//���ж��Ƿ���ڷ��ز������������������
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", paymentBill.getId()));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		
		IFDCPaymentBill iFDCPaymentBill = null;
		IPayRequestBill iPayRequestBill = null;
		if (ctx == null) {
			iFDCPaymentBill = FDCPaymentBillFactory.getRemoteInstance();
			iPayRequestBill = PayRequestBillFactory.getRemoteInstance();
		} else {
			iFDCPaymentBill = FDCPaymentBillFactory.getLocalInstance(ctx);
			iPayRequestBill = PayRequestBillFactory.getLocalInstance(ctx);
		}
		if(!iFDCPaymentBill.exists(filter)) {
			if (fdcPaymentBill == null) {
				fdcPaymentBill = new FDCPaymentBillInfo();
			}
			fdcPaymentBill.setPaymentBill(paymentBill);
			fdcPaymentBill.setIsRespite(false);
			// һЩ��Ҫ��Ϣ
			fdcPaymentBill.setCreateTime(paymentBill.getCreateTime());
			fdcPaymentBill.setCreator(paymentBill.getCreator());
			fdcPaymentBill.setLastUpdateTime(paymentBill.getLastUpdateTime());
			fdcPaymentBill.setLastUpdateUser(paymentBill.getLastUpdateUser());
			
			// �ڼ�����Ϊ�������뵥���ڼ�
			String payReqBillId = paymentBill.getFdcPayReqID();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("*");
			selector.add("period.id");
			selector.add("orgUnit.id");
			PayRequestBillInfo payReqBill = iPayRequestBill.getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(payReqBillId)),selector);
			fdcPaymentBill.setPeriod(payReqBill.getPeriod());
			fdcPaymentBill.setBookedDate(payReqBill.getBookedDate());
			fdcPaymentBill.setOrgUnit(payReqBill.getOrgUnit());

			iFDCPaymentBill.addnew(fdcPaymentBill);
		} else {
			if (fdcPaymentBill != null) {
				FDCPaymentBillCollection col  = iFDCPaymentBill.getFDCPaymentBillCollection(view);
				if (col != null && col.size() > 0) {
					FDCPaymentBillInfo oldInfo = col.get(0);
					fdcPaymentBill.setId(oldInfo.getId());
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("invoiceAmt");
					selector.add("invoiceNumber");
					selector.add("allInvoiceAmt");
					selector.add("invoiceDate");
					iFDCPaymentBill.updatePartial(fdcPaymentBill, selector);
				}
			}
		}
	}
	
	
	public static void recountReqInvoiceAmt(Context ctx, String contractId) throws BOSException, EASBizException {
		if (contractId == null) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
		SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
		sorterItemInfo.setSortType(SortType.ASCEND);
		view.getSorter().add(sorterItemInfo);
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("createTime");
		view.getSelector().add("invoiceAmt");
		view.getSelector().add("allInvoiceAmt");
		
		IPayRequestBill iPayRequestBill = null;
		if (ctx == null) {
			iPayRequestBill = PayRequestBillFactory.getRemoteInstance();
		} else {
			iPayRequestBill = PayRequestBillFactory.getLocalInstance(ctx);
		}
		
		PayRequestBillCollection c = iPayRequestBill.getPayRequestBillCollection(view);
		PayRequestBillInfo info = null;

		for (int i = 0; i < c.size(); i++) {
			info = c.get(i);
			if (i == 0) {
				// ����0
				info.setAllInvoiceAmt(info.getInvoiceAmt());
			} else {
				// ���������ۼƣ����ڷ���
				if (c.get(i - 1).getAllInvoiceAmt() instanceof BigDecimal && info.getInvoiceAmt() instanceof BigDecimal) {
					info.setAllInvoiceAmt(c.get(i - 1).getAllInvoiceAmt().add(info.getInvoiceAmt()));
				}
			}
			iPayRequestBill.updatePartial(info, view.getSelector());
		}
	}
}
