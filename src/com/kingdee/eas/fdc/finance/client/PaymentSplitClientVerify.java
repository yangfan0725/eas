package com.kingdee.eas.fdc.finance.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

public class PaymentSplitClientVerify {
	
	public static void checkBeforeSplitSettle(CoreUI ui,String paymentBillId,boolean isFinance,boolean isAdjustMode) throws BOSException, EASBizException, UuidException{
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("actPayAmt");
		sic.add("contractBillId");
		sic.add("fdcPayType.fid");
		sic.add("fdcPayType.payType.id");
		PaymentBillInfo payInfo = PaymentBillFactory
				.getRemoteInstance()
				.getPaymentBillInfo(new ObjectUuidPK(BOSUuid.read(paymentBillId)), sic);
		if (payInfo.getFdcPayType().getPayType().getId().toString().equals(
				PaymentTypeInfo.settlementID)) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("id"));
			selector.add(new SelectorItemInfo("hasSettled"));
			selector.add(new SelectorItemInfo("isCoseSplit"));
			selector.add(new SelectorItemInfo("settleAmt"));
			ContractBillInfo conInfo = ContractBillFactory
					.getRemoteInstance().getContractBillInfo(
							new ObjectUuidPK(BOSUuid.read(payInfo
									.getContractBillId())), selector);
			if (!conInfo.isHasSettled()) {
				MsgBox.showWarning("��ͬδ���ս���,���ܲ�ֽ�������ĸ��!");
				SysUtil.abort();
			} else {
				FilterInfo filterSett = new FilterInfo();
				filterSett.getFilterItems().add(
						new FilterItemInfo("settlementBill.contractBill.id", payInfo.getContractBillId()));
				filterSett.getFilterItems().add(
						new FilterItemInfo("settlementBill.isFinalSettle", Boolean.TRUE, CompareType.EQUALS));
				filterSett.getFilterItems().add(
						new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
				boolean hasSettleSplit = false;
				if (conInfo.isIsCoseSplit()) {
					hasSettleSplit = SettlementCostSplitFactory.getRemoteInstance().exists(filterSett);
				} else {
					hasSettleSplit = SettNoCostSplitFactory.getRemoteInstance().exists(filterSett);
				}
				if (!hasSettleSplit) {
					MsgBox.showWarning("��ͬ���㵥δ���,���ܲ�ֽ�������ĸ��!");
					SysUtil.abort();
				}
			}
			
			/*�ж��Ƿ���ȿ��Ѳ����,����Ǻ��ģʽ����ʾ��
			*��ͬ�´���δ����ƾ֤�Ľ��ȿ�����ȴ�����ȿ�����ֺ�����ƾ֤��������ݴ���!
			*������ʾ����ͬ�´���δ��ֵĽ��ȿ�����ȴ�����ȿ�����ֺ�����ƾ֤��������ݴ���!
			*/
			String msg=null;
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo(
							"paymentBill.fdcPayType.payType.id",
							PaymentTypeInfo.progressID,
							CompareType.EQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("isInvalid", Boolean.TRUE,
							CompareType.NOTEQUALS));
			
			if(isFinance&&!isAdjustMode){
				msg="��ͬ�´���δ����ƾ֤�Ľ��ȿ�����ȴ�����ȿ�����ֺ�����ƾ֤��������ݴ���!";
				filter.getFilterItems().add(new FilterItemInfo("Fivouchered", Boolean.TRUE));
			}else{
				msg="��ͬ�´���δ��ֵĽ��ȿ�����ȴ�����ȿ�����ֺ�����ƾ֤��������ݴ���!";
			}
			FDCSQLBuilder builder=new FDCSQLBuilder();
			String splitTable=null;
			if(conInfo.isIsCoseSplit()){
				splitTable="T_FNC_PaymentSplit";
			}else{
				splitTable="t_Fnc_Paymentnocostsplit";
			}
			
			builder.appendSql("select 1 from T_cas_paymentbill bill ");
			builder.appendSql("inner join T_FDC_PaymentType paymentType on paymentType.fid=bill.ffdcPayTypeid ");
			builder.appendSql("where paymentType.fpayTypeid=? and bill.fcontractbillid=? and ");
			builder.appendSql(" not  exists (select 1 from "+splitTable+" where fpaymentbillId=bill.fid and fisinvalid=0 ");
			builder.appendSql("   ");
			if(isFinance&&!isAdjustMode){
				builder.appendSql(" and fFivouchered=1 )");
			}else{
				builder.appendSql(" )");
			}
			
			builder.addParam(PaymentTypeInfo.progressID);
			builder.addParam(conInfo.getId().toString());
			if(builder.isExist()){
				FDCMsgBox.showWarning(ui, msg);
				SysUtil.abort();
			}
		}
	}
}
