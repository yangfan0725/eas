package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewCollection;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewFactory;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.PaySplitUtilFacadeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.util.CostAccountWithAccountHelper;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ConNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.finance.FDCAdjustBillEntryInfo;
import com.kingdee.eas.fdc.finance.FDCAdjustBillFactory;
import com.kingdee.eas.fdc.finance.FDCAdjustBillInfo;
import com.kingdee.eas.fdc.finance.FDCAdjustProductEntryInfo;
import com.kingdee.eas.fdc.finance.IFDCAdjustBill;
import com.kingdee.eas.fdc.finance.IPaymentNoCostSplit;
import com.kingdee.eas.fdc.finance.IPaymentSplit;
import com.kingdee.eas.fdc.finance.PayNoCostSplit4VoucherInfo;
import com.kingdee.eas.fdc.finance.PaySplit4VoucherInfo;
import com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitException;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitHelper;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusInfo;
import com.kingdee.eas.fdc.finance.SettledMonthlyCollection;
import com.kingdee.eas.fdc.finance.SettledMonthlyFactory;
import com.kingdee.eas.fdc.finance.SettledMonthlyInfo;
import com.kingdee.eas.fdc.finance.TraceOldSplitHelper;
import com.kingdee.eas.fdc.finance.VoucherAdjustReasonEnum;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.gl.IVoucher;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.framework.AbstractBillEntryBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class TraceOldSplitVoucherFacadeControllerBean extends
		AbstractTraceOldSplitVoucherFacadeControllerBean {
	private static final String SHOULD_SAVE_COST_AMT = "shouldSaveCostAmt";

	private static final String SHOULD_SAVE_PAYED_AMT = "shouldSavePayedAmt";

	private static final String SHOULD_SAVE_QUALITY_AMT = "shouldSaveQualityAmt";
	private static final String SHOULD_SAVE_INVOICE_AMT = "shouldSaveInvoiceAmt";
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.TraceOldSplitVoucherFacadeControllerBean");

	private int groupIndex = 0;

	private boolean isCostSplit(Context ctx, String id) throws EASBizException,
			BOSException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("isCoseSplit");
		ContractBillInfo info = ContractBillFactory.getLocalInstance(ctx)
				.getContractBillInfo(new ObjectUuidPK(id), selector);
		return info.isIsCoseSplit();
	}

	protected void _traceSplitVoucher(Context ctx, String contractID)
			throws BOSException, EASBizException {
		PaymentBillCollection shouldTreat = new PaymentBillCollection();
		IVoucher voucher = VoucherFactory.getLocalInstance(ctx);
		EntityViewInfo payBillView = new EntityViewInfo();
		FilterInfo payBillFilter = new FilterInfo();
		payBillFilter.getFilterItems().add(
				new FilterItemInfo("contractBillId", contractID));
		// payBillFilter.getFilterItems().add(
		// new FilterItemInfo("fiVouchered", Boolean.TRUE));
		payBillView.setFilter(payBillFilter);
		payBillView.getSelector().add("id");

		PaymentBillCollection paymentBillCollection = PaymentBillFactory
				.getLocalInstance(ctx).getPaymentBillCollection(payBillView);
		/***
		 * ��ͬ��صĸ��IDS
		 */
		Set payIdSet = new HashSet();

		for (Iterator iter = paymentBillCollection.iterator(); iter.hasNext();) {
			PaymentBillInfo element = (PaymentBillInfo) iter.next();
			String id = element.getId().toString();

			payIdSet.add(id);
		}

		EntityViewInfo paySplitView = new EntityViewInfo();
		FilterInfo paySplitFilter = new FilterInfo();
		paySplitFilter.getFilterItems().add(
				new FilterItemInfo("paymentBill.contractBillId", contractID,
						CompareType.EQUALS));
		paySplitFilter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.EQUALS));
		// paySplitFilter.getFilterItems().add(
		// new FilterItemInfo("isRedVouchered", Boolean.TRUE,
		// CompareType.NOTEQUALS));
		paySplitView.setFilter(paySplitFilter);
		paySplitView.getSorter().add(new SorterItemInfo("createTime"));
		paySplitView.getSelector().add("id");
		paySplitView.getSelector().add("state");
		paySplitView.getSelector().add("paymentBill.id");
		paySplitView.getSelector().add("hisVoucher.id");
		paySplitView.getSelector().add("isRedVouchered");
		paySplitView.getSelector().add("Fivouchered");
		SelectorItemCollection psUpdSelector = new SelectorItemCollection();
		psUpdSelector.add("state");
		psUpdSelector.add("hisVoucher.id");
		psUpdSelector.add("isInvalid");
		SorterItemInfo sort = new SorterItemInfo("paymentBill.createTime");
		sort.setSortType(SortType.DESCEND);
		paySplitView.getSorter().add(sort);
		
		List idList = new ArrayList();
		/***
		 * ����ǳɱ����
		 */
		if (isCostSplit(ctx, contractID)) {
			/***
			 * ���ҵ���ͬ��ص����ϵĸ�����
			 */
			PaymentSplitCollection paymentSplitCollection = PaymentSplitFactory
					.getLocalInstance(ctx).getPaymentSplitCollection(
							paySplitView);
			VoucherInfo newVoucher = new VoucherInfo();
			for (Iterator iter = paymentSplitCollection.iterator(); iter
					.hasNext();) {
				PaymentSplitInfo element = (PaymentSplitInfo) iter.next();
				
				if (element.isFivouchered()) {
					/***
					 * ���������ϵĸ������Ѿ�����ƾ֤��δ���ɺ��ƾ֤
					 * �ͼ�����Ҫ�����map��
					 */
					EntityViewInfo mapping = new EntityViewInfo();
					FilterInfo mappingFilter = new FilterInfo();
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcEntityID", element
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("destEntityID", newVoucher
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcObjectID", element.getId()
									.toString()));
					mapping.setFilter(mappingFilter);
					mapping.getSelector().add("id");
					mapping.getSelector().add("destObjectID");
					BOTRelationCollection relationColl = BOTRelationFactory
							.getLocalInstance(ctx).getCollection(mapping);
					/****
					 * ���ϵĸ����ֵ�����Ҫ���ɺ��ƾ֤
					 */
					for (Iterator it = relationColl.iterator(); it.hasNext();) {
						BOTRelationInfo botInfo = (BOTRelationInfo) it.next();
						BOSUuid voucherId = BOSUuid.read(botInfo
								.getDestObjectID());
						SelectorItemCollection origen = new SelectorItemCollection();
						origen.add("id");
						origen.add("hasReversed");
						VoucherInfo oldInfo = (VoucherInfo) voucher.getValue(
								new ObjectUuidPK(voucherId), origen);
						/***
						 * �����û�к������ͼ���������MAP
						 */
						if (!oldInfo.isHasReversed()) {
							idList.add(voucherId.toString());
							shouldTreat.add(element.getPaymentBill());
						}
					}
				}
			}
		} 
		/***
		 * �����Ƿǳɱ����
		 */
		else {
			/***
			 * �ͳɱ�����Ƕ�Ӧ��
			 * ���ҵ���ͬ��Ӧ�ĸ����ֵ�S
			 */
			PaymentNoCostSplitCollection paymentNoSplitCollection = PaymentNoCostSplitFactory
					.getLocalInstance(ctx).getPaymentNoCostSplitCollection(
							paySplitView);
			VoucherInfo newVoucher = new VoucherInfo();
			
			for (Iterator iter = paymentNoSplitCollection.iterator(); iter
					.hasNext();) {
				PaymentNoCostSplitInfo element = (PaymentNoCostSplitInfo) iter
						.next();
				/***
				 * �������������Ѿ�����ƾ֤
				 */
				if (element.isFivouchered()) {
					EntityViewInfo mapping = new EntityViewInfo();
					FilterInfo mappingFilter = new FilterInfo();
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcEntityID", element
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("destEntityID", newVoucher
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcObjectID", element.getId()
									.toString()));
					mapping.setFilter(mappingFilter);
					mapping.getSelector().add("id");
					mapping.getSelector().add("destObjectID");
					BOTRelationCollection relationColl = BOTRelationFactory
							.getLocalInstance(ctx).getCollection(mapping);
					/***
					 * ���ϵĸ����ֵ�����Ҫ���ɺ��ƾ֤
					 */
					for (Iterator it = relationColl.iterator(); it.hasNext();) {
						BOTRelationInfo botInfo = (BOTRelationInfo) it.next();
						BOSUuid voucherId = BOSUuid.read(botInfo
								.getDestObjectID());
						SelectorItemCollection origen = new SelectorItemCollection();
						origen.add("id");
						origen.add("hasReversed");
						VoucherInfo oldInfo = (VoucherInfo) voucher.getValue(
								new ObjectUuidPK(voucherId), origen);
						if (!oldInfo.isHasReversed()) {
							idList.add(voucherId.toString());
							shouldTreat.add(element.getPaymentBill());
						}
					}
				}
			}
		}
		/***
		 *���ɺ��ƾ֤
		 */
		if (idList.size() > 0) {
			IObjectPK newVoucherPk = voucher.reverseSaveBatch(idList);
			PaySplitUtilFacadeFactory.getLocalInstance(ctx)
					.traceReverseVoucher(newVoucherPk);			
		}
		/***
		 * ��Ҫ���²��
		 */
		if (shouldTreat.size() > 0)
			_splitContract(ctx, contractID, shouldTreat);
		
				
		/***
		 * ���º�ͬ�Ĵ���״̬
		 */
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("update T_CON_ContractBill set FConSplitExecState=? where fid=?");
		builder.addParam(ConSplitExecStateEnum.INVALIDDID_VALUE);
		builder.addParam(contractID);
		builder.executeUpdate();
	}
	/***
	 * ���´�����غ�ͬ
	 */
	protected IObjectPK[] _splitContract(Context ctx, String contractID,
			IObjectCollection paymentBill) throws BOSException, EASBizException {
		PaymentBillCollection payColl = (PaymentBillCollection) paymentBill;
		boolean isAdjust=isAdjustModel(ctx);
		boolean isFinancial=isFinancialModel(ctx);//�����һ�ʽ���
		boolean isSplitBaseOnProp=isSplitBaseOnProp(ctx);
		//TODO ������Ϊmap����
		/***
		 * ����ǳɱ����
		 */
		if (isCostSplit(ctx, contractID)) {
			return splitCostContract(ctx, contractID, payColl,isFinancial,isAdjust,isSplitBaseOnProp);
		} else {
			return splitNoCostContract(ctx, contractID, payColl,isFinancial,isAdjust,isSplitBaseOnProp);
		}
	}

	private IObjectPK[] splitNoCostContract(Context ctx, String contractID, PaymentBillCollection payColl,boolean isFinancial,boolean isAdjust,boolean isSplitBaseOnProp) throws BOSException, EASBizException {
		IPaymentNoCostSplit splitNoCost = PaymentNoCostSplitFactory.getLocalInstance(ctx);
		IObjectPK[] pks = new IObjectPK[payColl.size()];
		List idList = new ArrayList();
		
		for (int pay = 0, size = payColl.size(); pay < size; pay++) {
			PaymentNoCostSplitInfo objectValue = new PaymentNoCostSplitInfo();
			// objectValue.setCompany((com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit()));
			objectValue
					.setCreator((com.kingdee.eas.common.client.SysContext
							.getSysContext().getCurrentUserInfo()));
			objectValue.setIsInvalid(false);
			objectValue.setVoucherTimes(1);
			String costBillID;
			costBillID = payColl.get(pay).getId().toString();
			PaymentBillInfo costBillInfo = null;

			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("*");
			selectors.add("id");
			selectors.add("number");
			selectors.add("name");
			selectors.add("amount");
			selectors.add("contractBillId");
			selectors.add("fdcPayReqID");
			selectors.add("fdcPayType.*");
			selectors.add("curProject.id");
			selectors.add("curProject.longNumber");
			selectors.add("fdcPayType.payType.*");
			costBillInfo = PaymentBillFactory.getLocalInstance(ctx)
					.getPaymentBillInfo(
							new ObjectUuidPK(BOSUuid.read(costBillID)),
							selectors);
			if(FDCUtils.IsSimpleFinacial(ctx,ContextUtil.getCurrentFIUnit(ctx).getId().toString())){
				if(FDCUtils.IsSimpleFinacialExtend(ctx,ContextUtil.getCurrentFIUnit(ctx).getId().toString())){
					objectValue.setCompletePrjAmt(costBillInfo.getProjectPriceInContract());
//					objectValue.setAmount(costBillInfo.getProjectPriceInContract());
					objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
				}else{
					objectValue.setPayAmount(costBillInfo.getActPayAmt());
					objectValue.setCompletePrjAmt(costBillInfo.getActPayAmt());
				}
				
			}else if(FDCUtils.IsFinacial(ctx,ContextUtil.getCurrentFIUnit(ctx).getId().toString())){
				//����ģʽ������=��ͬ�ڹ��̿�
				//���ģʽҲ=��ͬ�ڹ��̿�  2009-5-16
				objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
			}
			if (FDCUtils.IsFinacial(ctx,ContextUtil.getCurrentFIUnit(ctx).getId().toString())&&costBillInfo.getFdcPayReqID() != null) {
				//������Ÿ��,��ô�ɱ�����ֻҪ��һ�ŵ�:�Ƿ��Ѵ��ڲ�ֵĸ��
				boolean isClosed = true ;
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isClosed",Boolean.TRUE));
				filter.getFilterItems().add(new FilterItemInfo("isInvalid",Boolean.FALSE));
				filter.getFilterItems().add(new FilterItemInfo("paymentBill.fdcPayReqID",costBillInfo.getFdcPayReqID()));
				
				if (PaymentNoCostSplitFactory.getLocalInstance(ctx).exists(filter)) {
					isClosed = false ;
				}
				objectValue.setIsClosed(isClosed);
				if (costBillInfo.getFdcPayReqID() != null) {
					
					if (costBillInfo.getFdcPayType().getPayType().getId()
							.toString().equals(PaymentTypeInfo.settlementID)) {
	
						FilterInfo filterPay = new FilterInfo();
						filterPay.getFilterItems().add(
								new FilterItemInfo(
										"paymentBill.contractBillId",
										costBillInfo.getContractBillId()));
						filterPay.getFilterItems().add(
								new FilterItemInfo("state",
										FDCBillStateEnum.INVALID_VALUE,
										CompareType.NOTEQUALS));
						filterPay.getFilterItems().add(
								new FilterItemInfo(
										"paymentBill.fdcPayType.payType.id",
										PaymentTypeInfo.settlementID));
						if (PaymentNoCostSplitFactory.getLocalInstance(ctx)
								.exists(filterPay)) {
							objectValue.setIsProgress(false);
							objectValue.setCompletePrjAmt(null);
							objectValue.setPayAmount(costBillInfo
									.getProjectPriceInContract());
						} else {
	
							handleNoCostFirstSettleSplit(ctx, objectValue, costBillInfo,isAdjust,isClosed);
						}
					} else if (costBillInfo.getFdcPayType().getPayType()
							.getId().toString().equals(PaymentTypeInfo.keepID)) {
						objectValue.setIsProgress(false);
						objectValue.setCompletePrjAmt(null);
//						objectValue.setPayAmount(costBillInfo.getActPayAmt());
					} else {
						SelectorItemCollection selectorReq = new SelectorItemCollection();
						selectorReq.add("id");
						selectorReq.add("completePrjAmt");
						PayRequestBillInfo info = PayRequestBillFactory
								.getLocalInstance(ctx).getPayRequestBillInfo(
										new ObjectUuidPK(BOSUuid
												.read(costBillInfo
														.getFdcPayReqID())),
										selectorReq);
						if (isClosed && info.getCompletePrjAmt() != null) {
							objectValue.setCompletePrjAmt(info
									.getCompletePrjAmt());
							isClosed = false;
						}else{
							objectValue.setCompletePrjAmt(FDCHelper.ZERO);
						}
//						objectValue.setPayAmount(costBillInfo.getActPayAmt());
					}
				}
			}
			objectValue.setPaymentBill(costBillInfo);
			objectValue.setIsConWithoutText(false);
			objectValue.setFivouchered(false);
			if(costBillInfo.getCurProject()!=null)
				objectValue.setCurProject(costBillInfo.getCurProject());
			FDCNoCostSplitBillEntryCollection conColl = importConNoCostSplit(
					ctx, contractID);
			FDCNoCostSplitBillEntryCollection chanColl = importChangeNoCostSplit(
					ctx, contractID);
			PaymentNoCostSplitEntryCollection paySplit = new PaymentNoCostSplitEntryCollection();
			for (int i = 0, conSize = conColl.size(); i < conSize; i++) {
				FDCNoCostSplitBillEntryInfo info = conColl.get(i);
				PaymentNoCostSplitEntryInfo splitInfo = new PaymentNoCostSplitEntryInfo();
				splitInfo.putAll(info);
				paySplit.add(splitInfo);
			}
			if (chanColl != null) {
				for (int i = 0, conSize = chanColl.size(); i < conSize; i++) {
					FDCNoCostSplitBillEntryInfo info = chanColl.get(i);
					PaymentNoCostSplitEntryInfo splitInfo = new PaymentNoCostSplitEntryInfo();
					splitInfo.putAll(info);
					paySplit.add(splitInfo);
				}
			}
			objectValue.getEntrys().addCollection(paySplit);
			setNoCostAdd(ctx, objectValue, contractID,isFinancial,isAdjust);
			objectValue.put("isSplitBaseOnProp", Boolean.valueOf(isSplitBaseOnProp));
			pks[pay] = _splitNoCostPayment(ctx, objectValue);
			idList.add(pks[pay].toString());
			if(!isAdjust){
				splitNoCost.traceData(idList);
				splitNoCost.generateVoucher(pks[pay]);
			}
		}
		return pks;
	}

	private void handleNoCostFirstSettleSplit(Context ctx, PaymentNoCostSplitInfo objectValue, PaymentBillInfo costBillInfo,boolean isAdjust,boolean isClosed) throws BOSException, EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("id"));
		selector.add(new SelectorItemInfo("hasSettled"));
		selector.add(new SelectorItemInfo("isCoseSplit"));
		selector.add(new SelectorItemInfo("settleAmt"));
		ContractBillInfo conInfo = ContractBillFactory
				.getLocalInstance(ctx)
				.getContractBillInfo(
						new ObjectUuidPK(
								BOSUuid
										.read(costBillInfo
												.getContractBillId())),
						selector);

		if (conInfo.isHasSettled()
				&& conInfo.getSettleAmt() != null) {
			BigDecimal amountSplit = FDCHelper.ZERO;
			amountSplit = amountSplit.setScale(2);
			EntityViewInfo viewSplit = new EntityViewInfo();
			FilterInfo filterSplit = new FilterInfo();
			filterSplit.getFilterItems().add(
					new FilterItemInfo(
							"paymentBill.contractBillId",
							costBillInfo
									.getContractBillId()));
			filterSplit
					.getFilterItems()
					.add(
							new FilterItemInfo(
									"paymentBill.fdcPayType.payType.id",
									PaymentTypeInfo.progressID));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("Fivouchered",
							Boolean.TRUE));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("isInvalid",
							Boolean.TRUE,
							CompareType.NOTEQUALS));
			viewSplit.setFilter(filterSplit);
			viewSplit.getSelector().add("id");
			viewSplit.getSelector().add("payAmount");
			viewSplit.getSelector().add(
					"paymentBill.actPayAmt");
			viewSplit.getSelector().add(
					"paymentBill.payerAccount.*");
			viewSplit.getSelector().add(
					"paymentBill.payerAccountBank.*");
			viewSplit.getSelector().add(
					"paymentBill.currency.*");
			viewSplit.getSelector().add(
					"paymentBill.exchangeRate");
			viewSplit.getSelector().add(
					"paymentBill.fdcPayReqID");
			viewSplit.getSelector().add(
					"paymentBill.company.id");
			PaymentNoCostSplitCollection splitColl = PaymentNoCostSplitFactory
					.getLocalInstance(ctx)
					.getPaymentNoCostSplitCollection(
							viewSplit);
			PaymentNoCostSplitInfo splitInfo = new PaymentNoCostSplitInfo();
			int sizeSplit = splitColl.size();
			for (int i = 0; i < sizeSplit; i++) {
				splitInfo = splitColl.get(i);
				PayNoCostSplit4VoucherInfo forEntry = new PayNoCostSplit4VoucherInfo();
				forEntry.setParent(objectValue);
				forEntry.setPaymentBill(splitInfo
						.getPaymentBill());
				forEntry
						.setAccountView(splitInfo
								.getPaymentBill()
								.getPayerAccount());
				forEntry.setBankAccount(splitInfo
						.getPaymentBill()
						.getPayerAccountBank());
				forEntry.setCurrency(splitInfo
						.getPaymentBill().getCurrency());
				forEntry.setAmount(splitInfo
						.getPaymentBill().getActPayAmt());
				forEntry
						.setExchangeRate(splitInfo
								.getPaymentBill()
								.getExchangeRate());
				if (splitInfo.getPaymentBill() != null
						&& splitInfo.getPaymentBill()
								.getPayerAccount() != null
						&& (splitInfo.getPaymentBill()
								.getPayerAccount()
								.isIsBank()
								|| splitInfo
										.getPaymentBill()
										.getPayerAccount()
										.isIsCash() || splitInfo
								.getPaymentBill()
								.getPayerAccount()
								.isIsCashEquivalent())) {
					forEntry.setIsNeedTransit(true);
					forEntry
							.setTransitAccount(FDCUtils
									.getDefaultFDCParamAccount(
											ctx,
											splitInfo
													.getPaymentBill()
													.getCompany()
													.getId()
													.toString()));
				}
				String payReqID = splitInfo
						.getPaymentBill().getFdcPayReqID();
				PayRequestBillInfo request = PayRequestBillFactory
						.getLocalInstance(ctx)
						.getPayRequestBillInfo(
								new ObjectUuidPK(BOSUuid
										.read(payReqID)));
				forEntry.setPayRequestBill(request);
				forEntry.setCostAmount(request
						.getCostAmount());
				objectValue.getVoucherEntrys()
						.add(forEntry);
				if (splitInfo.getPayAmount() != null)
					amountSplit = amountSplit.add(splitInfo
							.getPayAmount());
			}
			objectValue.setPayAmount(amountSplit
					.add(costBillInfo.getProjectPriceInContract()));
			if(isClosed)
				objectValue.setCompletePrjAmt(conInfo.getSettleAmt());
			else
				objectValue.setCompletePrjAmt(FDCHelper.ZERO);

			// �ʱ���
			EntityViewInfo viewSett = new EntityViewInfo();
			FilterInfo filterSett = new FilterInfo();
			filterSett.getFilterItems().add(
					new FilterItemInfo("contractBill.id",
							costBillInfo
									.getContractBillId()));
			viewSett.setFilter(filterSett);
			viewSett.getSelector().add("id");
			viewSett.getSelector().add("qualityGuarante");
			ContractSettlementBillCollection settColl = ContractSettlementBillFactory
					.getLocalInstance(ctx)
					.getContractSettlementBillCollection(
							viewSett);
			if (settColl.size() == 1) {
				ContractSettlementBillInfo settInfo = settColl
						.get(0);
				if (settInfo.getQualityGuarante() != null)
					objectValue.setQualityAmount(settInfo
							.getQualityGuarante());
			}

			objectValue.setIsProgress(true);
			
			if(isAdjust){
				if(isClosed){
					//����ģʽ��//���깤������������ۣ����ȿ����깤������֮��
					//ʵ������ͬ���̿�
					FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
					builder.appendSql("select sum(FCompletePrjAmt) FCompletePrjAmt from T_Con_PayRequestBill payReq ");
					builder.appendSql("inner join T_FDC_PaymentType  paymentType on paymentType.fid=payReq.fpaymentType ");
					builder.appendSql("inner join T_FDC_Type type on type.fid=paymentType.fpaytypeid ");
					builder.appendSql("where fcontractid=? and type.fid=? ");
					
					builder.addParam(costBillInfo.getContractBillId());
					builder.addParam(PaymentTypeInfo.progressID);
					IRowSet rowSet=builder.executeQuery();
					try {
						if (rowSet.next()) {
							objectValue.setCompletePrjAmt(FDCNumberHelper.subtract(conInfo.getSettleAmt(), rowSet.getBigDecimal("FCompletePrjAmt")));
						}
					}catch(SQLException e){
						throw new BOSException(e);
					}
				}else{
					objectValue.setCompletePrjAmt(FDCHelper.ZERO);
				}
				objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
			}
		}
	}

	private IObjectPK[] splitCostContract(Context ctx, String contractID, PaymentBillCollection payColl,boolean isFinancial,boolean isAdjust,boolean isSplitBaseOnProp) throws BOSException, EASBizException {
		IPaymentSplit split = PaymentSplitFactory.getLocalInstance(ctx);
		List idList = new ArrayList();
		String companyId=ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		HashMap paramMap = FDCUtils.getDefaultFDCParam(ctx, companyId);
		boolean isInvoiceMgr=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INVOICEMRG);
		boolean isSeparateFromPayment=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		/**
		 * ���²�ֹ�����ȷ�ϵ�
		 */
		if(isSeparateFromPayment){
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			//�����˵Ĳ�ּ�isbeforeadjust=1 �Ĳ���Ҫ������  by sxhong 2009-08-01 18:59:34
			builder.appendSql("select fid from T_FNC_WorkLoadConfirmBill where fcontractbillId=? and fstate=? and exists (select 1 from T_fnc_paymentSplit where fworkloadbillid=T_FNC_WorkLoadConfirmBill.fid and fisBeforeAdjust=1 )");
			builder.addParam(contractID);
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
			IRowSet rowSet=builder.executeQuery();
			try{
				Map param=new HashMap();
				boolean hasSettleSplit = false;// ��ֵʵ���ڷ�������û���õ�����֪������Ϊʲô��������� by sxhong
				while(rowSet.next()){
					String id=rowSet.getString("fid");
					param.put("workLoadBillId", id);
					PaymentSplitInfo paymentSplitInfo = (PaymentSplitInfo)PaymentSplitFactory.getLocalInstance(ctx).getNewData(param);
					paymentSplitInfo.setSplitState(CostSplitStateEnum.ALLSPLIT);
					paymentSplitInfo.setHasInitIdx(true);
					paymentSplitInfo.setDescription("AutoSplit");
					if(isSplitBaseOnProp){
						paymentSplitInfo.setDescription("splitBaseOnProp");
						BigDecimal costAmt = FDCHelper.ZERO;
						BigDecimal splitedCostAmt = FDCHelper.ZERO;
						if(paymentSplitInfo.getEntrys()!=null){
							PaymentSplitEntryCollection entrys = paymentSplitInfo.getEntrys();
							for(Iterator iter=entrys.iterator();iter.hasNext();){
								PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo)iter.next();
								if(!entry.isIsLeaf()){
									continue;
								}
								costAmt=FDCHelper.add(costAmt, entry.getCostAmt());
								splitedCostAmt=FDCHelper.add(splitedCostAmt, entry.getSplitedCostAmt());
							}
						}
						//�����ɱ����
						HashMap costMap = new HashMap();
						costMap.put("headCostAmt", paymentSplitInfo.getCompletePrjAmt());//��ͷ��ʵ�����
						costMap.put("splitCostAmtSum",costAmt);//�ɱ���ֽ�����ϸ�кϼ�
						costMap.put("hadSplitCostAmtSum", splitedCostAmt);//�Ѳ�ָ��������ϸ�кϼ�
						TraceOldSplitHelper.splitCostAmtBaseOnProp(paymentSplitInfo.getEntrys(), costMap);
					}else{
						TraceOldSplitHelper.autoSplit(paymentSplitInfo.getEntrys(), paymentSplitInfo.getCompletePrjAmt(), hasSettleSplit);
					}
					calDirAmt(ctx, paymentSplitInfo);
					PaymentSplitFactory.getLocalInstance(ctx).save(paymentSplitInfo);
				}
			}catch(SQLException e){
				throw new BOSException(e);
			}
		}
		if(payColl==null)
			return new IObjectPK[0];
		IObjectPK[] pks = new IObjectPK[payColl.size()];
		/***
		 * ���ÿһ������������ɸ�����
		 */
		for (int pay = 0, size = payColl.size(); pay < size; pay++) {
			PaymentSplitInfo objectValue = new PaymentSplitInfo();
			// objectValue.setCompany((com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit()));
			objectValue
					.setCreator((com.kingdee.eas.common.client.SysContext
							.getSysContext().getCurrentUserInfo()));
			objectValue.setCreateTime(new Timestamp(new Date().getTime()));
			objectValue.setIsInvalid(false);
			objectValue.setVoucherTimes(1);
			String costBillID;
			costBillID = payColl.get(pay).getId().toString();
			PaymentBillInfo costBillInfo = null;

			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("id");
			selectors.add("number");
			selectors.add("name");
			selectors.add("amount");
			selectors.add("projectPriceInContract");
			selectors.add("contractBillId");
			selectors.add("fdcPayReqID");
			selectors.add("curProject.id");
			selectors.add("curProject.longNumber");
			selectors.add("fdcPayType.payType.id");
			selectors.add("actPayAmt");
			costBillInfo = PaymentBillFactory.getLocalInstance(ctx)
					.getPaymentBillInfo(
							new ObjectUuidPK(BOSUuid.read(costBillID)),
							selectors);
			if(FDCUtils.IsSimpleFinacial(ctx,ContextUtil.getCurrentFIUnit(ctx).getId().toString())){
				if(FDCUtils.IsSimpleFinacialExtend(ctx,ContextUtil.getCurrentFIUnit(ctx).getId().toString())){
					objectValue.setCompletePrjAmt(costBillInfo.getProjectPriceInContract());
//					objectValue.setAmount(costBillInfo.getProjectPriceInContract());
					objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
				}else{
					objectValue.setPayAmount(costBillInfo.getActPayAmt());
					objectValue.setCompletePrjAmt(costBillInfo.getActPayAmt());
				}
				
			}else if(FDCUtils.IsFinacial(ctx,ContextUtil.getCurrentFIUnit(ctx).getId().toString())){
				//����ģʽ������=��ͬ�ڹ��̿�
				//���ģʽҲ=��ͬ�ڹ��̿�  2009-5-16
				objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
			}
			
			if (FDCUtils.IsFinacial(ctx,ContextUtil.getCurrentFIUnit(ctx).getId().toString())&&costBillInfo.getFdcPayReqID() != null) {
				//������Ÿ��,��ô�ɱ�����ֻҪ��һ�ŵ�:�Ƿ��Ѵ��ڲ�ֵĸ��
				 /* 
				  * ������Ÿ��,��ô�ɱ�����ֻҪ��һ�ŵ�:�Ƿ��Ѵ��ڲ�ֵĸ��
				 * ��һ�Ÿ��Ӧ�����������ɵ����ţ��������ȡ����Ϊ�깤����Ʊ�����ڵ�һ�����棬������ȡ�û������ᵼ�µ��ݸ���ֵĲ�һ��
				 * 
				 */
				boolean isClosed = true ;	//Ӧ��ָ��һ�ŵ���
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
				//�ж��Ƿ��бȵ�ǰ����������,��������������ǰ���ݲ��ǵ�һ��
				builder.appendSql("select 1 from T_CAS_PaymentBill a,T_CAS_PaymentBill b where a.ffdcPayReqID=b.ffdcPayReqID and b.fid=? and a.fcreateTime<b.fcreateTime");
				builder.addParam(costBillInfo.getId().toString());
				if (builder.isExist()) {
					isClosed = false ;
				}
				PayRequestBillInfo payReqInfo =null;
				if(isClosed){
					SelectorItemCollection selectorReq = new SelectorItemCollection();
					selectorReq.add("id");
					selectorReq.add("completePrjAmt");
					selectorReq.add("invoiceAmt");
					payReqInfo = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(costBillInfo.getFdcPayReqID())), selectorReq);
					//���÷�Ʊ���
					if(isInvoiceMgr){
						objectValue.setInvoiceAmt(payReqInfo.getInvoiceAmt());
					}else{
						objectValue.setInvoiceAmt(FDCHelper.ZERO);
					}

				}
				
				objectValue.setIsColsed(isClosed);
				
				/***
				 * ���㵥
				 */
				if (costBillInfo.getFdcPayType().getPayType().getId()
						.toString().equals(PaymentTypeInfo.settlementID)) {

					FilterInfo filterPay = new FilterInfo();
					filterPay.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.contractBillId",
									costBillInfo.getContractBillId()));
					filterPay.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					filterPay.getFilterItems().add(
							new FilterItemInfo(
									"paymentBill.fdcPayType.payType.id",
									PaymentTypeInfo.settlementID));
					/***
					 * ����з����ϵĽ������ǵ�һ�ʽ����
					 */
					if (PaymentSplitFactory.getLocalInstance(ctx).exists(
							filterPay)) {
						objectValue.setIsProgress(false);//�ǵ�һ�ʽ����
						objectValue.setCompletePrjAmt(null);
						objectValue.setPayAmount(costBillInfo
								.getProjectPriceInContract());
					} else {
						handleCostFirstSettleSplit(ctx, objectValue, costBillInfo,isAdjust,isClosed);
					}
				} 
				/***
				 * ���޿�
				 */
				else if (costBillInfo.getFdcPayType().getPayType()
						.getId().toString().equals(PaymentTypeInfo.keepID)) {
					objectValue.setIsProgress(false);
					objectValue.setCompletePrjAmt(null);
//					objectValue.setPayAmount(costBillInfo.getActPayAmt());
				} 
				/***
				 * ���ȿ�
				 */
				else {
					if (isClosed) {
						objectValue.setCompletePrjAmt(FDCHelper.toBigDecimal(payReqInfo.getCompletePrjAmt()));
						isClosed = false;
					}
					/***
					 * ��ָ���������
					 */
//					objectValue.setPayAmount(costBillInfo.getActPayAmt());
				}
			}
			/***
			 * 
			 * 
			 */
			objectValue.setPaymentBill(costBillInfo);
			objectValue.setIsConWithoutText(false);
			objectValue.setFivouchered(false);
			if(costBillInfo.getCurProject()!=null)
				objectValue.setCurProject(costBillInfo.getCurProject());
			/**
			 * ��ͬ���
			 */
			FDCSplitBillEntryCollection conColl = new FDCSplitBillEntryCollection();
			/**
			 * ��ͬ������
			 */
			FDCSplitBillEntryCollection chanColl = new FDCSplitBillEntryCollection();
			if (objectValue.getPaymentBill() != null
					&& objectValue.getPaymentBill().getCurProject() != null
					&& objectValue.getPaymentBill().getCurProject().getId() != null) {
				String prjID = objectValue.getPaymentBill().getCurProject().getId().toString();
				ProjectPeriodStatusInfo prjStatusInfo = getPrjPeriodStatus(ctx, prjID);
				/***
				 * ������Ŀ״̬
				 * 
				 * �ɱ��Ѿ��½ᣬ����δ��
				 */
				if (prjStatusInfo!=null && prjStatusInfo.isIsCostEnd()
						&& !prjStatusInfo.isIsFinacialEnd()&& (FDCUtils.getCurrentPeriod(
								ctx, prjID, true)!=null)) {
					PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(
							ctx, prjID, true);
					PeriodInfo usedPeriod = PeriodUtils.getPrePeriodInfo(
							ctx, currentPeriod);
					conColl = importContractCostSplit(ctx, prjID,usedPeriod.getId().toString(), contractID);
					chanColl = importChangeCostSplit(ctx, prjID, usedPeriod.getId().toString(), contractID);

				} else {
					conColl = importContractCostSplist(ctx, contractID);
					chanColl = importChangeCostSplist(ctx, contractID);
				}
			} else {
				conColl = importContractCostSplist(ctx, contractID);
				chanColl = importChangeCostSplist(ctx, contractID);
			}
			/***
			 * ��ʼ�������ֵķ�¼
			 * 
			 */
			PaymentSplitEntryCollection paySplit = new PaymentSplitEntryCollection();
			for (int i = 0, conSize = conColl.size(); i < conSize; i++) {
				FDCSplitBillEntryInfo info = conColl.get(i);
				PaymentSplitEntryInfo splitInfo = new PaymentSplitEntryInfo();
				splitInfo.putAll(info);
				paySplit.add(splitInfo);
				splitInfo.setIndex(paySplit.size());
			}
			if (chanColl != null) {
				for (int i = 0, conSize = chanColl.size(); i < conSize; i++) {
					FDCSplitBillEntryInfo info = chanColl.get(i);
					PaymentSplitEntryInfo splitInfo = new PaymentSplitEntryInfo();
					splitInfo.putAll(info);
					paySplit.add(splitInfo);
					splitInfo.setIndex(paySplit.size());
				}
			}
			objectValue.getEntrys().addCollection(paySplit);
			/***
			 * �����Ѳ�ֳɱ����Ѳ�ָ�����
			 */
			setAdd(ctx, objectValue, contractID,isFinancial,isAdjust);
			objectValue.put("isSplitBaseOnProp", Boolean.valueOf(isSplitBaseOnProp));
			pks[pay] = _splitPayment(ctx, objectValue);
			idList.add(pks[pay].toString());
			/***
			 * �����Ǹ���������ƾ֤
			 * ����ǵ���ƾ֤ģʽ����ִ����������ƾ֤��
			 */
			if(!isAdjust){
				split.traceData(idList);
				split.generateVoucher(pks[pay]);
			}
			
		}
		
		
		return pks;
		// split.generateVoucher(pks);
	}

	private void handleCostFirstSettleSplit(Context ctx, PaymentSplitInfo objectValue, PaymentBillInfo costBillInfo,boolean isAdjust,boolean isClosed) throws BOSException, EASBizException {
		/**
		 * ��һ�ʽ����
		 */
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("id"));
		selector.add(new SelectorItemInfo("hasSettled"));
		selector.add(new SelectorItemInfo("isCoseSplit"));
		selector.add(new SelectorItemInfo("settleAmt"));
		ContractBillInfo conInfo = ContractBillFactory
				.getLocalInstance(ctx)
				.getContractBillInfo(
						new ObjectUuidPK(BOSUuid.read(costBillInfo.getContractBillId())),
						selector);
		
		if (conInfo.isHasSettled()
				&& conInfo.getSettleAmt() != null) {
			/***
			 * ��ͬ�ѽ���
			 */
			BigDecimal amountSplit = FDCHelper.ZERO;
			amountSplit = amountSplit.setScale(2);
			EntityViewInfo viewSplit = new EntityViewInfo();
			FilterInfo filterSplit = new FilterInfo();
			filterSplit.getFilterItems().add(
					new FilterItemInfo("contractBillId",
							costBillInfo
									.getContractBillId()));
			filterSplit.getFilterItems().add(
					new FilterItemInfo(
							"fdcPayType.payType.id",
							PaymentTypeInfo.progressID));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("fiVouchered",
							Boolean.TRUE));
			/***
			 * �ҵ��Ѿ�����ƾ֤�Ľ��ȿ�
			 */
			viewSplit.setFilter(filterSplit);
			viewSplit.getSelector().add("id");
			// viewSplit.getSelector().add("payAmount");
			viewSplit.getSelector().add("actPayAmt");
			viewSplit.getSelector().add("payerAccount.*");
			viewSplit.getSelector().add("company.id");
			viewSplit.getSelector().add("payerAccountBank.*");
			viewSplit.getSelector().add("currency.*");
			viewSplit.getSelector().add("exchangeRate");
			viewSplit.getSelector().add("fdcPayReqID");
			viewSplit.getSelector().add("projectPriceInContract");

			PaymentBillCollection splitColl = PaymentBillFactory
					.getLocalInstance(ctx)
					.getPaymentBillCollection(viewSplit);
			PaymentBillInfo splitInfo = new PaymentBillInfo();
			int sizeSplit = splitColl.size();
			/***
			 * �����Ǹ�ô�ģ�
			 * �����Ǻ��ģʽ�洢��ʷ�ĸ�����ȿ���Ϊ���������ƾ֤��һ����
			 * ��������ʷ���ȿ�
			 */
			for (int i = 0; i < sizeSplit; i++) {
				splitInfo = splitColl.get(i);
				PaySplit4VoucherInfo forEntry = new PaySplit4VoucherInfo();
				forEntry.setParent(objectValue);
				forEntry.setPaymentBill(splitInfo);
				forEntry.setAccountView(splitInfo.getPayerAccount());
				forEntry.setBankAccount(splitInfo.getPayerAccountBank());
				forEntry.setCurrency(splitInfo.getCurrency());
				forEntry.setAmount(splitInfo.getActPayAmt());
				forEntry.setExchangeRate(splitInfo.getExchangeRate());
				if (splitInfo.getPayerAccount() != null
						&& (splitInfo.getPayerAccount().isIsBank()
								|| splitInfo.getPayerAccount().isIsCash() 
								|| splitInfo.getPayerAccount().isIsCashEquivalent()
							)
					) {
					forEntry.setIsNeedTransit(true);
					forEntry.setTransitAccount(FDCUtils
							.getDefaultFDCParamAccount(ctx,
									splitInfo.getCompany()
											.getId()
											.toString()));
				}
				String payReqID = splitInfo
						.getFdcPayReqID();
				PayRequestBillInfo request = PayRequestBillFactory
						.getLocalInstance(ctx)
						.getPayRequestBillInfo(
								new ObjectUuidPK(BOSUuid
										.read(payReqID)));
				forEntry.setPayRequestBill(request);
				forEntry.setCostAmount(request
						.getCostAmount());
				objectValue.getVoucherEntrys()
						.add(forEntry);
				if (splitInfo.getProjectPriceInContract() != null)
					amountSplit = amountSplit.add(splitInfo
							.getProjectPriceInContract());
			}
//			objectValue.setPayAmount(amountSplit.add(costBillInfo.getProjectPriceInContract()));//NP
			objectValue.setPayAmount(FDCHelper.add(amountSplit,costBillInfo.getProjectPriceInContract()));
			if(isClosed)
				objectValue.setCompletePrjAmt(conInfo.getSettleAmt());
			else
				objectValue.setCompletePrjAmt(FDCHelper.ZERO);

			// �ʱ���
			EntityViewInfo viewSett = new EntityViewInfo();
			FilterInfo filterSett = new FilterInfo();
			filterSett.getFilterItems().add(
					new FilterItemInfo("contractBill.id",
							costBillInfo.getContractBillId()));
			viewSett.setFilter(filterSett);
			viewSett.getSelector().add("id");
			viewSett.getSelector().add("qualityGuarante");
			ContractSettlementBillCollection settColl = ContractSettlementBillFactory
					.getLocalInstance(ctx)
					.getContractSettlementBillCollection(viewSett);
			/***
			 * �Һ�ͬ�Ľ��㵥�ϵı��޽���
			 * 
			 */
			if (settColl.size() == 1) {
				ContractSettlementBillInfo settInfo = settColl.get(0);
				if (settInfo.getQualityGuarante() != null)
					objectValue.setQualityAmount(settInfo.getQualityGuarante());
			}
			/***
			 * ��һ�ʽ����
			 * ��
			 */
			
			objectValue.setIsProgress(true);
			String companyId=ContextUtil.getCurrentFIUnit(ctx).getId().toString();
			if(FDCUtils.getDefaultFDCParamByKey(ctx, companyId, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT)){
				//�������븶���ַ�����һ�ʽ����Ĵ����߼�������������һ����
				objectValue.setIsProgress(false);
				objectValue.setCompletePrjAmt(FDCHelper.ZERO);
			}
			if(isAdjust){
				if(isClosed){
					//����ģʽ��//���깤������������ۣ����ȿ����깤������֮��
					//ʵ������ͬ���̿�
					FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
					builder.appendSql("select sum(FCompletePrjAmt) FCompletePrjAmt from T_Con_PayRequestBill payReq ");
					builder.appendSql("inner join T_FDC_PaymentType  paymentType on paymentType.fid=payReq.fpaymentType ");
					builder.appendSql("inner join T_FDC_Type type on type.fid=paymentType.fpaytypeid ");
					builder.appendSql("where fcontractid=? and type.fid=? ");
					
					builder.addParam(costBillInfo.getContractBillId());
					builder.addParam(PaymentTypeInfo.progressID);
					IRowSet rowSet=builder.executeQuery();
					try {
						if (rowSet.next()) {
							objectValue.setCompletePrjAmt(FDCNumberHelper.subtract(conInfo.getSettleAmt(), rowSet.getBigDecimal("FCompletePrjAmt")));
						}			
					}catch(SQLException e){
						throw new BOSException(e);
					}
				}else{
					objectValue.setCompletePrjAmt(FDCHelper.ZERO);
				}
				objectValue.setPayAmount(costBillInfo.getProjectPriceInContract());
			}
		}
	}
	/**
	 * �����Ѳ�ֳɱ������Ѳ�ָ�����
	 * @param ctx
	 * @param objectValue
	 * @param contractID
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void setAdd(Context ctx, PaymentSplitInfo objectValue,
			String contractID,boolean isFinancial,boolean isAdjust) throws BOSException, EASBizException {
		ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(
				ctx).getContractBillInfo(
				new ObjectUuidPK(BOSUuid.read(contractID)));
		FilterInfo filterSett = new FilterInfo();
		filterSett.getFilterItems().add(
				new FilterItemInfo("settlementBill.contractBill.id",
						objectValue.getPaymentBill().getContractBillId()));
		filterSett.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		boolean hasSettleSplit = SettlementCostSplitFactory.getLocalInstance(
				ctx).exists(filterSett);
		/***
		 * �����ͬ���ѽ��㣬�򲻴��ڽ���Ĳ��
		 * �����ֵ��Ѳ�ֳɱ����=sum
		 * ��������Ѳ�ָ�����=sum
		 */
				
		if (!contractBill.isHasSettled() || !hasSettleSplit) {//δ���ս����û�н�����
			//ԭ�����㷨���ڴ������ƿ��,�ĳ���༭����һ�µ��Ż��㷨
			PaymentSplitHelper.handleCostAdjustModelSplitedAmt(ctx,objectValue,contractID);
			objectValue.setHasEffected(false);
		} 
		/***
		 * �ѽ��㣬���н�����
		 */
		else {
			objectValue.setHasEffected(true);
			//�����ҷǵ�����һ�ʽ����Ѳ�ֳɱ����Ϊ0 by hpw 2010-06-08 
//			setSettle(ctx, objectValue, contractID);
//			if(isAdjust){
//				PaymentSplitHelper.handleCostAdjustModelSplitedAmt(ctx,objectValue,contractID);
//			}else{
//				setSettle(ctx, objectValue, contractID);
//			}
			if(isFinancial&&!isAdjust){
				setSettle(ctx, objectValue, contractID);
			}else{
				PaymentSplitHelper.handleCostAdjustModelSplitedAmt(ctx,objectValue,contractID);
			}
		}
	}
	
	/***
	 * ���ý�����
	 * @param ctx
	 * @param objectValue
	 * @param contractID
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void setSettle(Context ctx, PaymentSplitInfo objectValue,
			String contractID) throws BOSException, EASBizException {
		if (objectValue.getEntrys().size() > 0) {
			for (int i = 0, size = objectValue.getEntrys().size(); i < size; i++) {
				PaymentSplitEntryInfo entry = objectValue.getEntrys().get(i);
				if (entry.getLevel() > -1) {
					String acc = entry.getCostAccount().getId().toString();
					String costId = entry.getCostBillId().toString();
					BigDecimal temp = FDCHelper.ZERO;
					BigDecimal tempShould = FDCHelper.ZERO;
					BigDecimal temppay = FDCHelper.ZERO;
					BigDecimal tempPayed = FDCHelper.ZERO;
					SettlementCostSplitEntryCollection coll = null;
					SettlementCostSplitEntryInfo item = null;
					PaymentSplitEntryCollection collpay = null;
					PaymentSplitEntryInfo itempay = null;
					EntityViewInfo viewPay = new EntityViewInfo();
					FilterInfo filterPay = new FilterInfo();
					filterPay.getFilterItems().add(
							new FilterItemInfo("costAccount", acc));
					filterPay.getFilterItems().add(
							new FilterItemInfo("costBillId", costId));
					filterPay.getFilterItems().add(
							new FilterItemInfo("Parent.isRedVouchered",
									Boolean.TRUE, CompareType.NOTEQUALS));
					filterPay.getFilterItems().add(
							new FilterItemInfo("Parent.state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					if (objectValue.getId() != null) {
						filterPay.getFilterItems().add(
								new FilterItemInfo("Parent.id", objectValue
										.getId().toString(),
										CompareType.NOTEQUALS));
					}
					FilterInfo filterSettPayed = new FilterInfo();
					filterSettPayed.getFilterItems().add(
							new FilterItemInfo("paymentBill.contractBillId",
									contractID));
					filterSettPayed.getFilterItems().add(
							new FilterItemInfo("isProgress", Boolean.TRUE));
					filterSettPayed.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					boolean hasSettlePayed = PaymentSplitFactory
							.getLocalInstance(ctx).exists(filterSettPayed);
					if (hasSettlePayed) {
						filterPay
								.getFilterItems()
								.add(
										new FilterItemInfo(
												"Parent.paymentBill.fdcPayType.payType.id",
												PaymentTypeInfo.settlementID));
					}
					if (entry.getProduct() != null) {
						String product = entry.getProduct().getId().toString();
						filterPay.getFilterItems().add(
								new FilterItemInfo("product.id", product));
					} else {
						filterPay.getFilterItems().add(
								new FilterItemInfo("product.id", null));
					}
					if (entry.getSplitType() != null) {
						String standard = entry.getSplitType().getValue();
						filterPay.getFilterItems().add(
								new FilterItemInfo("splitType", standard));
					} else {
						filterPay.getFilterItems().add(
								new FilterItemInfo("splitType", null));
					}
					viewPay.getSelector().add("amount");
					viewPay.getSelector().add("payedAmt");
					viewPay.setFilter(filterPay);

					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("costAccount", acc));
					filter.getFilterItems().add(
							new FilterItemInfo("costBillId", costId));
					filter.getFilterItems().add(
							new FilterItemInfo("parent.state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					if (entry.getProduct() != null) {
						String product = entry.getProduct().getId().toString();
						filter.getFilterItems().add(
								new FilterItemInfo("product.id", product));
					} else {
						filter.getFilterItems().add(
								new FilterItemInfo("product.id", null));
					}
					if (entry.getSplitType() != null) {
						String standard = entry.getSplitType().getValue();
						filter.getFilterItems().add(
								new FilterItemInfo("splitType", standard));
					} else {
						filter.getFilterItems().add(
								new FilterItemInfo("splitType", null));
					}
					view.getSelector().add("amount");
					view.getSelector().add("grtSplitAmt");
					view.setFilter(filter);

					coll = SettlementCostSplitEntryFactory
							.getLocalInstance(ctx)
							.getSettlementCostSplitEntryCollection(view);
					collpay = PaymentSplitEntryFactory.getLocalInstance(ctx)
							.getPaymentSplitEntryCollection(viewPay);
					for (Iterator iter = coll.iterator(); iter.hasNext();) {
						item = (SettlementCostSplitEntryInfo) iter.next();
						if (item.getAmount() != null) {
							temp = temp.add(item.getAmount());
							if (item.getGrtSplitAmt() != null) {
								tempShould = tempShould.add(item.getAmount()
										.subtract(item.getGrtSplitAmt()));
							} else {
								tempShould = tempShould.add(item.getAmount());
							}
						}
					}
					for (Iterator iter = collpay.iterator(); iter.hasNext();) {
						itempay = (PaymentSplitEntryInfo) iter.next();
						if (itempay.getAmount() != null)
							temppay = temppay.add(itempay.getAmount());
						if (itempay.getPayedAmt() != null)
							tempPayed = tempPayed.add(itempay.getPayedAmt());
					}

					entry.setCostAmt(temp);

					entry.setShouldPayAmt(tempShould);
					if (objectValue.getPaymentBill() != null
							&& objectValue.getPaymentBill().getContractBillId() != null) {
						FilterInfo filterPayed = new FilterInfo();
						filterPayed.getFilterItems().add(
								new FilterItemInfo(
										"paymentBill.contractBillId",
										contractID));
						filterPayed.getFilterItems().add(
								new FilterItemInfo("state",
										FDCBillStateEnum.INVALID_VALUE,
										CompareType.NOTEQUALS));
						filterPayed.getFilterItems().add(
								new FilterItemInfo(
										"paymentBill.fdcPayType.payType.id",
										PaymentTypeInfo.settlementID));
						if (!PaymentSplitFactory.getLocalInstance(ctx).exists(
								filterPayed)
								&& objectValue.getPaymentBill().getFdcPayType()
										.getPayType().getId().toString()
										.equals(PaymentTypeInfo.settlementID)) {
							entry.setSplitedCostAmt(FDCHelper.ZERO);
							entry.setSplitedPayedAmt(FDCHelper.ZERO);
						} else {
							entry.setSplitedCostAmt(temppay);
							entry.setSplitedPayedAmt(tempPayed);
						}
					}
				}
			}
		}
	}

	private FDCSplitBillEntryCollection importContractCostSplist(Context ctx,
			String contractID) throws BOSException, EASBizException {
		return loadCostSplit(CostSplitBillTypeEnum.CONTRACTSPLIT,
				getCostSplitEntryCollection(ctx,
						CostSplitBillTypeEnum.CONTRACTSPLIT, null, contractID));
	}

	protected FDCSplitBillEntryCollection loadCostSplit(
			CostSplitBillTypeEnum costBillType,
			FDCSplitBillEntryCollection entrys) {
		IObjectValue entry = null;
		if (costBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
			// contractAmt
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entry = (IObjectValue) iter.next();
				entry.put("contractAmt", entry.get("amount"));
				entry.put("shouldPayAmt", entry.get("amount"));
				// entry.put("amount", FDCHelper.ZERO);
				entry.put("amount", null);
			}
		} else if (costBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {
			// changeAmt
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entry = (IObjectValue) iter.next();
				entry.put("changeAmt", entry.get("amount"));
				entry.put("shouldPayAmt", entry.get("amount"));
				// entry.put("amount", FDCHelper.ZERO);
				entry.put("amount", null);
			}
		}
		return loadMoreCostSplit(entrys);
	}

	protected FDCSplitBillEntryCollection loadMoreCostSplit(
			FDCSplitBillEntryCollection entrys) {

		FDCSplitBillEntryInfo entry = null;
		BigDecimal amount = FDCHelper.ZERO;

		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			entry = (FDCSplitBillEntryInfo) iter.next();

			// ֱ�ӷ���
			entry.setDirectAmount(amount);
			entry.setDirectAmountTotal(amount);

			// �������֣��������ý��
			// if (splitBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT))
			// {
			// entry.setAmount(amount);
			// }

			// ������ jelon 12/28/2006
			if (entry.getLevel() == 0) {
				groupIndex++;
			}
			entry.setIndex(groupIndex);

			// addEntry(entry);

		}
		return entrys;
	}

	private FDCSplitBillEntryCollection importChangeCostSplist(Context ctx,
			String contractID) throws BOSException, EASBizException {
		// if(true) return;
		// loadCostSplit(getCostSplitEntryCollection(CostSplitBillTypeEnum.CONTRACTSPLIT));
		FDCSplitBillEntryCollection totalChange = new FDCSplitBillEntryCollection();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("contractChange.contractBill.id",
								contractID));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		view.setFilter(filter);
		SelectorItemCollection sic = view.getSelector();
		sic.add(new SelectorItemInfo("contractChange.contractBill.id"));

		ConChangeSplitCollection coll = null;

		coll = ConChangeSplitFactory.getLocalInstance(ctx)
				.getConChangeSplitCollection(view);

		if (coll == null || coll.size() == 0) {
			return null;
		}

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ConChangeSplitInfo item = (ConChangeSplitInfo) iter.next();
			totalChange.addCollection(loadCostSplit(
					CostSplitBillTypeEnum.CNTRCHANGESPLIT,
					getCostSplitEntryCollection(ctx,
							CostSplitBillTypeEnum.CNTRCHANGESPLIT, item
									.getContractChange().getId(), contractID)));
		}
		return totalChange;
	}

	protected FDCSplitBillEntryCollection getCostSplitEntryCollection(
			Context ctx, CostSplitBillTypeEnum splitBillType,
			BOSUuid costBillUuId, String contractID) throws BOSException,
			EASBizException {

		String costBillId = null;
		if (costBillUuId == null) {
			costBillUuId = BOSUuid.read(contractID);
		}
		costBillId = costBillUuId.toString();

		if (costBillId == null) {
			return new FDCSplitBillEntryCollection();
		}
		AbstractObjectCollection coll = null;
		AbstractBillEntryBaseInfo item = null;
		EntityViewInfo view = new EntityViewInfo();
		String filterField = null;
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sic = view.getSelector();
		setSelectorsEntry(ctx, sic, true);
		view.getSorter().add(new SorterItemInfo("seq"));

		BOSUuid splitBillId = null;
		FilterInfo filterSplit = new FilterInfo();
		EntityViewInfo viewSplit = new EntityViewInfo();
		FDCSplitBillCollection costColl = new FDCSplitBillCollection();
		viewSplit.getSorter().add(new SorterItemInfo("createTime"));
		if (splitBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
			filterField = "parent.contractBill.id";
			filter.getFilterItems().add(
					new FilterItemInfo(filterField, costBillId));
			filter.getFilterItems().add(
					new FilterItemInfo("parent.state",
							FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("costAccount.*");
			view.getSelector().add("product.*");
			view.getSelector().add("costAccount.curProject.*");
			view.getSelector().add("apportionType.*");

			filterSplit.getFilterItems().add(
					new FilterItemInfo("contractBill.id", costBillId));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			viewSplit.setFilter(filterSplit);
			costColl = ContractCostSplitFactory.getLocalInstance(ctx)
					.getFDCSplitBillCollection(viewSplit);

			coll = ContractCostSplitEntryFactory.getLocalInstance(ctx)
					.getContractCostSplitEntryCollection(view);

		} else if (splitBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {
			filterField = "parent.contractChange.id";
			filter.getFilterItems().add(
					new FilterItemInfo(filterField, costBillId));
			filter.getFilterItems().add(
					new FilterItemInfo("parent.state",
							FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			view.setFilter(filter);
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("costAccount.*");
			view.getSelector().add("product.*");
			view.getSelector().add("costAccount.curProject.*");
			view.getSelector().add("apportionType.*");

			filterSplit.getFilterItems().add(
					new FilterItemInfo("contractChange.id", costBillId));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			viewSplit.setFilter(filterSplit);
			costColl = ConChangeSplitFactory.getLocalInstance(ctx)
					.getFDCSplitBillCollection(viewSplit);

			coll = ConChangeSplitEntryFactory.getLocalInstance(ctx)
					.getFDCSplitBillEntryCollection(view);

		} else if (splitBillType.equals(CostSplitBillTypeEnum.SETTLEMENTSPLIT)) {
			filterField = "parent.settlementBill.id";
			filter.getFilterItems().add(
					new FilterItemInfo(filterField, costBillId));
			filter.getFilterItems().add(
					new FilterItemInfo("parent.state",
							FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			view.setFilter(filter);
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("costAccount.*");
			view.getSelector().add("product.*");
			view.getSelector().add("costAccount.curProject.*");
			view.getSelector().add("apportionType.*");

			filterSplit.getFilterItems().add(
					new FilterItemInfo("settlementBill.id", costBillId));
			filterSplit.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			viewSplit.setFilter(filterSplit);
			costColl = SettlementCostSplitFactory.getLocalInstance(ctx)
					.getFDCSplitBillCollection(viewSplit);

			coll = SettlementCostSplitEntryFactory.getLocalInstance(ctx)
					.getFDCSplitBillEntryCollection(view);

		} else {
			// ������ֵ�,�Ժ��ṩ֧��
			return new FDCSplitBillEntryCollection();
		}

		if (costColl != null && costColl.iterator().hasNext()) {
			splitBillId = costColl.get(0).getId();
		}

		FDCSplitBillEntryCollection entrys = new FDCSplitBillEntryCollection();
		FDCSplitBillEntryInfo entry = null;

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			item = (AbstractBillEntryBaseInfo) iter.next();
			item.setId(null);
			// item.setSeq(null);

			entry = new FDCSplitBillEntryInfo();
			entry.putAll(item);
			if (splitBillId != null) {
				entry.setSplitBillId(splitBillId);
			}
			// costBillUuId=item.get(costBillIdField)
			entry.setCostBillId(costBillUuId);
			/*
			 * if(entry.getProduct()!=null){ try{
			 * entry.setProduct(ProductTypeFactory.getLocalInstance(ctx).getProductTypeInfo(
			 * new ObjectUuidPK(entry.getProduct().getId()))); }catch(Exception
			 * e){ handUIException(e); } }
			 */
			entrys.add(entry);
		}

		return entrys;
	}

	protected SelectorItemCollection setSelectorsEntry(Context ctx,
			SelectorItemCollection sic, boolean isEntry) {
		FDCCostSplit fdcCostSplit = new FDCCostSplit(ctx);
		return fdcCostSplit.setSelectorsEntry(sic, isEntry);
	}
	/***
	 * �������²��
	 */
	protected IObjectPK _splitPayment(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		PaymentSplitInfo splitInfo = (PaymentSplitInfo) model;
		splitInfo.setDescription("AutoSplit");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("settlementBill.contractBill.id", splitInfo
						.getPaymentBill().getContractBillId()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		/**
		 * �Ƿ���ڷ����ϵĽ�����
		 */
		boolean hasSettleSplit = SettlementCostSplitFactory.getLocalInstance(
				ctx).exists(filter);

		FilterInfo filterPay = new FilterInfo();
		filterPay.getFilterItems().add(
				new FilterItemInfo("paymentBill.contractBillId", splitInfo
						.getPaymentBill().getContractBillId()));
		filterPay.getFilterItems().add(
				new FilterItemInfo("isProgress", Boolean.TRUE));
		filterPay.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		/***
		 * �Ƿ���ڵ�һ�ʽ����ĸ�����
		 */
		boolean hasSettlePayed = PaymentSplitFactory.getLocalInstance(ctx)
				.exists(filterPay);
		/***
		 * �Ƿ��ǵ�һ�ʽ����
		 */
		boolean isSettle = splitInfo.isIsProgress();
		/***
		 * �Ƿ��Ǳ��޿�
		 */
		boolean isKeepAmt = splitInfo.getPaymentBill().getFdcPayType()
				.getPayType().getId().toString().equals(PaymentTypeInfo.keepID);
		
		boolean isSplitBaseOnProp = splitInfo.getBoolean("isSplitBaseOnProp");
		if(isSplitBaseOnProp){
			splitInfo.setDescription("splitBaseOnProp");
			//���������
			if(splitInfo!=null&&splitInfo.getEntrys()!=null){
				BigDecimal costAmt = FDCHelper.ZERO;
				BigDecimal splitedCostAmt = FDCHelper.ZERO;
				BigDecimal splitedPayedAmt = FDCHelper.ZERO;
				BigDecimal spiitedInvoiceAmt = FDCHelper.ZERO;
				for(Iterator iter=splitInfo.getEntrys().iterator();iter.hasNext();){
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo)iter.next();
					if(!entry.isIsLeaf()){
						continue;
					}
					costAmt=FDCHelper.add(costAmt, entry.getCostAmt());
					splitedCostAmt=FDCHelper.add(splitedCostAmt, entry.getSplitedCostAmt());
					splitedPayedAmt=FDCHelper.add(splitedPayedAmt, entry.getSplitedPayedAmt());
					spiitedInvoiceAmt=FDCHelper.add(spiitedInvoiceAmt, entry.getSplitedInvoiceAmt());
				}
				//�����ɱ����
				HashMap costMap = new HashMap();
				costMap.put("headCostAmt", splitInfo.getCompletePrjAmt());//��ͷ��ʵ�����
				costMap.put("splitCostAmtSum",costAmt);//�ɱ���ֽ�����ϸ�кϼ�
				costMap.put("hadSplitCostAmtSum", splitedCostAmt);//�Ѳ�ָ��������ϸ�кϼ�
				TraceOldSplitHelper.splitCostAmtBaseOnProp(splitInfo.getEntrys(), costMap);
				
				//����������
				HashMap payedMap = new HashMap();
				payedMap.put("headPayedAmt", splitInfo.getPayAmount());//��ͷ��ʵ�����
				payedMap.put("splitCostAmtSum",costAmt);//�ɱ���ֽ�����ϸ�кϼ�
				payedMap.put("hadSplitPayedAmtSum", splitedPayedAmt);//�Ѳ�ָ��������ϸ�кϼ�
				TraceOldSplitHelper.splitPayedAmtBaseOnProp(splitInfo.getEntrys(), payedMap);
				
				//������Ʊ���
				HashMap invoiceMap = new HashMap();
				invoiceMap.put("headInvoiceAmt", splitInfo.getInvoiceAmt());//��ͷ�ķ�Ʊ���
				invoiceMap.put("splitCostAmtSum",costAmt);//�ɱ���ֽ�����ϸ�кϼ�
				invoiceMap.put("hadSplitInvoiceAmtSum", spiitedInvoiceAmt);//�Ѳ�ַ�Ʊ������ϸ�кϼ�
				TraceOldSplitHelper.splitInvoiceAmtBaseOnProp(splitInfo.getEntrys(), invoiceMap);
			}
		}else{
			/***
			 * �Զ�������
			 */
			TraceOldSplitHelper.autoSplit(splitInfo.getEntrys(), splitInfo
					.getCompletePrjAmt(), hasSettleSplit);
			//����Ҫ���ݲ����жϣ���Ϊ��֮ǰ���ݲ����趨��InvoiceAmt
			TraceOldSplitHelper.autoSplitInvoiceAmt(splitInfo.getEntrys(), splitInfo.getInvoiceAmt());
			TraceOldSplitHelper.autoSplitAmt(splitInfo.getEntrys(), splitInfo
					.getPayAmount(), true, hasSettlePayed, isSettle, isKeepAmt,isAdjustModel(ctx));
		}
		//TODO ��һ�ѷ�����Ӧ���Ż���  by sxhong 2009-07-21 16:14:46
		calDirAmt(ctx, splitInfo);
		calPayedAmt(ctx, splitInfo);
		calInvoiceAmt(ctx,splitInfo);
		
		if (splitInfo.getQualityAmount() != null&&isSettle) {
			setQuaAmt(ctx, splitInfo);
		}
		splitInfo.setAmount(splitInfo.getCompletePrjAmt());
		splitInfo.setSplitState(CostSplitStateEnum.ALLSPLIT);
		IPaymentSplit payment = PaymentSplitFactory.getLocalInstance(ctx);
		splitInfo.setHasInitIdx(true);
		IObjectPK pk = payment.save(splitInfo);
		return pk;
	}

	protected void setQuaAmt(Context ctx, PaymentSplitInfo splitInfo)
			throws BOSException, EASBizException {
		int size = splitInfo.getEntrys().size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {

				PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) splitInfo
						.getEntrys().get(i);
				if (entry.getAmount() != null
						&& splitInfo.getQualityAmount() != null) {
					BigDecimal cmpAmt = FDCHelper.ZERO;
//					cmpAmt = (entry.getAmount().multiply(splitInfo.getQualityAmount())).divide(splitInfo.getCompletePrjAmt(), 2,BigDecimal.ROUND_HALF_EVEN);
					cmpAmt = FDCHelper.divide(FDCHelper.multiply(entry.getAmount(), splitInfo.getQualityAmount()), splitInfo.getCompletePrjAmt(), 2,BigDecimal.ROUND_HALF_EVEN);
					entry.setQualityAmount(cmpAmt);

				}
			}
			if (splitInfo.getAmount() != null
					&& splitInfo.getAmount().compareTo(
							splitInfo.getCompletePrjAmt()) == 0) {
				int idx = 0;
				BigDecimal amountMax = FDCHelper.ZERO;
				BigDecimal amount = FDCHelper.ZERO;// null;
				BigDecimal amountTotal = FDCHelper.ZERO;
				amountTotal = amountTotal.setScale(10);
				for (int i = 0; i < size; i++) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) splitInfo
							.getEntrys().get(i);
					if (entry.getLevel() == 0) {
						amount = entry.getQualityAmount();
						if (amount == null) {
							amount = FDCHelper.ZERO;
						}
						amountTotal = amountTotal.add(amount);
						// ������Ϊ���������
						// if(amount.compareTo(FDCHelper.ZERO)!=0){
						if (amount.compareTo(amountMax) >= 0) {
							amountMax = amount;
							idx = i;
						}
					} else {
						continue;
					}
				}
				if (idx > 0
						&& splitInfo.getQualityAmount().compareTo(amountTotal) != 0) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) splitInfo
							.getEntrys().get(idx);
					if (entry.getQualityAmount() != null) {
						if (!(entry.getQualityAmount().equals(FDCHelper.ZERO))) {
							amount = entry.getQualityAmount();
							if (amount == null) {
								amount = FDCHelper.ZERO;
							}
							amount = amount.add(splitInfo.getQualityAmount()
									.subtract(amountTotal));
							entry.setQualityAmount(amount);
						}
					}
				}
				for (int i = 0; i < size; i++) {
					PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) splitInfo
							.getEntrys().get(i);
					if (entry.getLevel() == 0) {
						int curIndex = splitInfo.getEntrys().indexOf(entry);
						PaymentSplitHelper.adjustQuaAmount(splitInfo
								.getEntrys(), entry);
						// �������ɷ���ϸ������Ŀ�и��ӿ�Ŀ�ĳɱ� jelon 12/29/2006
						PaymentSplitHelper.totAmountQuaAddlAcct(splitInfo
								.getEntrys(), curIndex);
						// calcAmount(0);
					}
				}

			}
		}
	}

	private void calDirAmt(Context ctx, PaymentSplitInfo splitInfo) {
		FDCCostSplit fdcCostSplit = new FDCCostSplit(ctx);
		for (int i = 0, size = splitInfo.getEntrys().size(); i < size; i++) {
			PaymentSplitEntryInfo entry = splitInfo.getEntrys().get(i);
			int curIndex = splitInfo.getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumAccount(ctx, i, entry, size, splitInfo);
				fdcCostSplit.totAmountAddlAcct(splitInfo.getEntrys(), curIndex);
			}
		}
		BigDecimal total = FDCHelper.ZERO;
		for (int i = 0, size = splitInfo.getEntrys().size(); i < size; i++) {
			PaymentSplitEntryInfo entry = splitInfo.getEntrys().get(i);
			if (entry.getLevel() == 0 && entry.getAmount() != null) {
				total = total.add(entry.getAmount());
			}
		}
		for (int i = 0, size = splitInfo.getEntrys().size(); i < size; i++) {
			PaymentSplitEntryInfo entry = splitInfo.getEntrys().get(i);
			if (entry.getLevel() < 0) {
				entry.setAmount(total);
			}
		}
	}

	// ��level=0�Ľ��л���
	private void sumAccount(Context ctx, int index,
			PaymentSplitEntryInfo curEntry, int size, PaymentSplitInfo splitInfo) {
		FDCCostSplit fdcCostSplit = new FDCCostSplit(ctx);
		int curLevel = curEntry.getLevel();
		PaymentSplitEntryInfo entry;
		if ((curEntry.getCostAccount().getCurProject().isIsLeaf())
				&& (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCostAccount().getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < size; i++) {
					entry = (PaymentSplitEntryInfo) splitInfo.getEntrys()
							.get(i);
					if (fdcCostSplit.isChildProjSameAcct(entry, curEntry)) {
						// if((entry.getLevel()==curLevel+1)&&(entry.getCostAccount().getCurProject().getLevel()==prjLevel+1)){
						sumAccount(ctx, i, entry, size, splitInfo);
						if (entry.getAmount() != null)
							amtTotal = amtTotal.add(entry.getAmount());
					} else if (entry.getLevel() < curLevel + 1) {
						break;
					} else {
						continue;
					}
				}
				curEntry.setAmount(amtTotal);
			} else {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < size; i++) {
					entry = (PaymentSplitEntryInfo) splitInfo.getEntrys()
							.get(i);
					if (entry.getCostAccount().getCurProject().getId().equals(
							curEntry.getCostAccount().getCurProject().getId())) {
						if (entry.getLevel() == curLevel + 1) {
							sumAccount(ctx, i, entry, size, splitInfo);
							if (entry.getAmount() != null)
								amtTotal = amtTotal.add(entry.getAmount());
						} else if (entry.getLevel() > curLevel + 1) {
							continue;
						} else {
							break;
						}
					} else
						break;
				}
				curEntry.setAmount(amtTotal);
			}
		}
	}

	private void calPayedAmt(Context ctx, PaymentSplitInfo splitInfo) {
		for (int i = 0, size = splitInfo.getEntrys().size(); i < size; i++) {
			PaymentSplitEntryInfo entry = splitInfo.getEntrys().get(i);
			int curIndex = splitInfo.getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumPayedAmt(ctx, i, entry, size, splitInfo);
				PaymentSplitHelper.totAmountPayAddlAcct(splitInfo
						.getEntrys(), curIndex);
			}
		}
	}

	private void sumPayedAmt(Context ctx, int index,
			PaymentSplitEntryInfo curEntry, int size, PaymentSplitInfo splitInfo) {
		FDCCostSplit fdcCostSplit = new FDCCostSplit(ctx);
		int curLevel = curEntry.getLevel();
		// int prjLevel = curEntry.getCostAccount().getCurProject().getLevel();
		PaymentSplitEntryInfo entry;
		if ((curEntry.getCostAccount().getCurProject().isIsLeaf())
				&& (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCostAccount().getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < size; i++) {
					entry = (PaymentSplitEntryInfo) splitInfo.getEntrys()
							.get(i);
					if (fdcCostSplit.isChildProjSameAcct(entry, curEntry)) {
						// if((entry.getLevel()==curLevel+1)&&(entry.getCostAccount().getCurProject().getLevel()==prjLevel+1)){
						sumPayedAmt(ctx, i, entry, size, splitInfo);
						if (entry.getPayedAmt() != null)
							amtTotal = amtTotal.add(entry.getPayedAmt());
					} else if (entry.getLevel() < curLevel + 1) {
						break;
					} else {
						continue;
					}
				}
				curEntry.setPayedAmt(amtTotal);
			} else {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < size; i++) {
					entry = (PaymentSplitEntryInfo) splitInfo.getEntrys()
							.get(i);
					if (entry.getCostAccount().getCurProject().getId().equals(
							curEntry.getCostAccount().getCurProject().getId())) {
						if (entry.getLevel() == curLevel + 1) {
							sumPayedAmt(ctx, i, entry, size, splitInfo);
							if (entry.getPayedAmt() != null)
								amtTotal = amtTotal.add(entry.getPayedAmt());
						} else if (entry.getLevel() > curLevel + 1) {
							continue;
						} else {
							break;
						}
					} else
						break;
				}
				curEntry.setPayedAmt(amtTotal);
			}
		}
	}

	/**
	 * ��Ʊ�����ܣ���ʱ����  
	 * by sxhong 2009-07-21 16:24:56
	 * @param ctx
	 * @param splitInfo
	 */
	private void calInvoiceAmt(Context ctx, PaymentSplitInfo splitInfo) {
		for (int i = 0, size = splitInfo.getEntrys().size(); i < size; i++) {
			PaymentSplitEntryInfo entry = splitInfo.getEntrys().get(i);
			int curIndex = splitInfo.getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumInvoiceAmt(ctx, i, entry, size, splitInfo);
				PaymentSplitHelper.totAmountInvoiceAddlAcct(splitInfo
						.getEntrys(), curIndex);
			}
		}
	}

	private void sumInvoiceAmt(Context ctx, int index,
			PaymentSplitEntryInfo curEntry, int size, PaymentSplitInfo splitInfo) {
		int curLevel = curEntry.getLevel();
		// int prjLevel = curEntry.getCostAccount().getCurProject().getLevel();
		PaymentSplitEntryInfo entry;
		if ((curEntry.getCostAccount().getCurProject().isIsLeaf())
				&& (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCostAccount().getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < size; i++) {
					entry = (PaymentSplitEntryInfo) splitInfo.getEntrys()
							.get(i);
					if (FDCCostSplit.isChildProjSameAcct(entry, curEntry)) {
						// if((entry.getLevel()==curLevel+1)&&(entry.getCostAccount().getCurProject().getLevel()==prjLevel+1)){
						sumInvoiceAmt(ctx, i, entry, size, splitInfo);
						if (entry.getInvoiceAmt() != null)
							amtTotal = amtTotal.add(entry.getInvoiceAmt());
					} else if (entry.getLevel() < curLevel + 1) {
						break;
					} else {
						continue;
					}
				}
				curEntry.setInvoiceAmt(amtTotal);
			} else {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < size; i++) {
					entry = (PaymentSplitEntryInfo) splitInfo.getEntrys()
							.get(i);
					if (entry.getCostAccount().getCurProject().getId().equals(
							curEntry.getCostAccount().getCurProject().getId())) {
						if (entry.getLevel() == curLevel + 1) {
							sumInvoiceAmt(ctx, i, entry, size, splitInfo);
							if (entry.getInvoiceAmt() != null)
								amtTotal = amtTotal.add(entry.getInvoiceAmt());
						} else if (entry.getLevel() > curLevel + 1) {
							continue;
						} else {
							break;
						}
					} else
						break;
				}
				curEntry.setInvoiceAmt(amtTotal);
			}
		}
	}
	
	protected IObjectPK _splitNoCostPayment(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		PaymentNoCostSplitInfo splitInfo = (PaymentNoCostSplitInfo) model;
		splitInfo.setDescription("AutoSplit");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("settlementBill.contractBill.id", splitInfo
						.getPaymentBill().getContractBillId()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		boolean hasSettleSplit = SettNoCostSplitFactory.getLocalInstance(ctx)
				.exists(filter);

		FilterInfo filterPay = new FilterInfo();
		filterPay.getFilterItems().add(
				new FilterItemInfo("paymentBill.contractBillId", splitInfo
						.getPaymentBill().getContractBillId()));
		filterPay.getFilterItems().add(
				new FilterItemInfo("isProgress", Boolean.TRUE));
		filterPay.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		boolean hasSettlePayed = PaymentNoCostSplitFactory
				.getLocalInstance(ctx).exists(filterPay);
		boolean isSettle = splitInfo.isIsProgress();
		boolean isKeepAmt = splitInfo.getPaymentBill().getFdcPayType()
				.getPayType().getId().toString().equals(PaymentTypeInfo.keepID);
		
		boolean isSplitBaseOnProp = splitInfo.getBoolean("isSplitBaseOnProp");
		if (isSplitBaseOnProp) {
			splitInfo.setDescription("splitBaseOnProp");
			//���������
			if(splitInfo!=null&&splitInfo.getEntrys()!=null){
				BigDecimal costAmt = FDCHelper.ZERO;
				BigDecimal splitedCostAmt = FDCHelper.ZERO;
				BigDecimal splitedPayedAmt = FDCHelper.ZERO;
				BigDecimal spiitedInvoiceAmt = FDCHelper.ZERO;
				for(Iterator iter=splitInfo.getEntrys().iterator();iter.hasNext();){
					PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo)iter.next();
					if(!entry.isIsLeaf()){
						continue;
					}
					costAmt=FDCHelper.add(costAmt, entry.getCostAmt());
					splitedCostAmt=FDCHelper.add(splitedCostAmt, entry.getSplitedCostAmt());
					splitedPayedAmt=FDCHelper.add(splitedPayedAmt, entry.getSplitedPayedAmt());
					spiitedInvoiceAmt=FDCHelper.add(spiitedInvoiceAmt, entry.getSplitedInvoiceAmt());
				}
				//�����ɱ����
				HashMap costMap = new HashMap();
				costMap.put("headCostAmt", splitInfo.getCompletePrjAmt());//��ͷ��ʵ�����
				costMap.put("splitCostAmtSum",costAmt);//�ɱ���ֽ�����ϸ�кϼ�
				costMap.put("hadSplitCostAmtSum", splitedCostAmt);//�Ѳ�ָ��������ϸ�кϼ�
				TraceOldSplitHelper.splitCostAmtBaseOnProp(splitInfo.getEntrys(), costMap);
				
				//����������
				HashMap payedMap = new HashMap();
				payedMap.put("headPayedAmt", splitInfo.getPayAmount());//��ͷ��ʵ�����
				payedMap.put("splitCostAmtSum",costAmt);//�ɱ���ֽ�����ϸ�кϼ�
				payedMap.put("hadSplitPayedAmtSum", splitedPayedAmt);//�Ѳ�ָ��������ϸ�кϼ�
				TraceOldSplitHelper.splitPayedAmtBaseOnProp(splitInfo.getEntrys(), payedMap);
				
				}
		} else {
			//TODO������Ʊ��
			TraceOldSplitHelper.autoNoCostSplit(splitInfo.getEntrys(), splitInfo.getCompletePrjAmt(), hasSettleSplit);
			TraceOldSplitHelper.autoNoCostSplitAmt(splitInfo.getEntrys(), splitInfo.getPayAmount(), true, hasSettlePayed, isSettle, isKeepAmt, isAdjustModel(ctx));
		}
		calNoDirAmt(ctx, splitInfo);
		calNoPayedAmt(ctx, splitInfo);
		if (splitInfo.getQualityAmount() != null&&isSettle) {
			setNoQuaAmt(ctx, splitInfo);
		}
		splitInfo.setAmount(splitInfo.getCompletePrjAmt());
		splitInfo.setSplitState(CostSplitStateEnum.ALLSPLIT);
		IPaymentNoCostSplit payment = PaymentNoCostSplitFactory
				.getLocalInstance(ctx);
		IObjectPK pk = payment.save(splitInfo);
		return pk;
	}
	
	/***
	 * ���������������ͬ��
	 * ���ݵ�ǰ���°汾�ĺ�ͬ��֣������֣������֣��Ѿ�����ƾ֤�ĸ����Ӧ�ĸ����֣��������ɵ�������
	 * �������Ļ�ƿ�Ŀ �� ���պ�ͬ��ַ����͸��α����ַ�����ʾ��ͨ����ַ����ĳɱ���Ŀ�����ɱ���Ŀ���ƿ�Ŀ�Ķ�Ӧ��ϵ����ȡ���Ļ�ƿ�Ŀ���������޸ġ�
	 * �����ɱ���� = ֮ǰ������ƾ֤�ĸ����Ӧ��ǰ��Ч�����ֲ�ֵ��ÿ�Ŀ���ۻ������ɱ����
	 * 				�������ɱ���Ŀ�����������ƾ֤�ĸ������Ӧ���µĸ����֡���Ч״̬���Ĺ����ɱ����֮�͡�
	 * ���������� = ֮ǰ������ƾ֤�ĸ����Ӧ��ǰ��Ч�����ֲ�ֵ��ÿ�Ŀ���ۻ�����������
	 * �������޽�� = ֮ǰ������ƾ֤�ĸ����Ӧ��ǰ��Ч�����ֲ�ֵ��ÿ�Ŀ���ۻ��������޽��
	 * ����ǰ�����ɱ���� = ����ǰ�����ֲ�ֵ��ÿ�Ŀ���ۻ������ɱ����
	 * 					������ǰ������==���Ѿ�����ƾ֤�ĸ����Ӧ�ĸ����֣���״̬Ϊ���ϡ���
	 * ����ǰ���������� = ����ǰ�����ֲ�ֵ��ÿ�Ŀ���ۻ�����������
	 * ����ǰ�������޿��� = ����ǰ�����ֲ�ֵ��ÿ�Ŀ���ۻ�����������
	 * 
	 * ��ͬ��ֽ�� = ��ͬ��ֲ�ֵ��ÿ�Ŀ�Ľ��
	 * �����ֽ�� = �����ֲ�ֵ��ÿ�Ŀ���ۻ����
	 * �����ֽ�� = �����ֲ�ֵ��ÿ�Ŀ�Ľ��
	 * ���޽��ֽ�� = ������ʱ���޽��ֵ��ÿ�Ŀ���
	 * �ɱ���ֽ�� = ����ͬ���������ս���Ľ����֣�����ں�ͬ��ֽ��+�����ֽ�
	 * 				���������ս���Ľ����֣�����ڽ����ֽ��
	 * 
	 * 
	 * 
	 * �ɱ������ = �����ɱ����-����ǰ�����ɱ����
	 * �������� = ����������-����ǰ����������
	 * ���޽����� = �������޽��-����ǰ�������޽��
	 * 
	 * ��Ʒ = ���պ�ͬ��ַ����͸��α����ַ�����ʾ���������޸ġ���ʾΪ��ַ�����Ӧ�Ĳ�Ʒ����
	 */
	protected void _traceAdjustContracts(Context ctx,List idList,boolean isNoText,VoucherAdjustReasonEnum voucherAdjustReasonEnum)throws BOSException, EASBizException{
		
		/**
		 * �ж��Ƿ��е������ı������
		 * ���û��,���쳣�������ñ������
		 */
		
		String fcontractbillid="FContractBillId";
		if(isNoText){
			fcontractbillid="FConWithoutTextID";
		}
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		if (!iCodingRuleManager.isExist(new FDCAdjustBillInfo(), ContextUtil.getCurrentFIUnit(ctx).getId().toString())){
			throw new PaymentSplitException(PaymentSplitException.NOTSETADJUSTCOLDINGRULE);
		}
		Map adjustBillMap = new HashMap();
		Map adjustBillEntryMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		Map shouldTreatContractIdList = new HashMap(); 
//		PaymentBillCollection shouldTreat = new PaymentBillCollection();
		Map shouldTreat = new HashMap();
		//Set shouldUpdateIsBeforeAdjustFalse = new HashSet();
		Set costAccountIds = new HashSet();
		Map contractCurProject = new HashMap();
		Set conCurProjectIds = new HashSet(); 
		Map curProjectPeriod = new HashMap();
		
		/***********************************************************************
		 * �����ҵ�������Ҫ���²�ֵĺ�ͬIDS
		 */
		
		Map hasFilnalSettle = new HashMap();
		/***********************************************************************
		 * �ҵ���Ҫ���²�ֵ�paymentBillCollection ���²��
		 */
		
		
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		
		Set paymentBillIdSet = new HashSet();
		Set workLoadBillIdSet=new HashSet();
		if(!VoucherAdjustReasonEnum.INVALID.equals(voucherAdjustReasonEnum)){
			if(!isNoText)
				filterInfo.getFilterItems().add(new FilterItemInfo("contractBill.id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
			else
				filterInfo.getFilterItems().add(new FilterItemInfo("conWithoutText.id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
			filterInfo.getFilterItems().add(new FilterItemInfo("voucherRefer", null , CompareType.NOTEQUALS));	
			filterInfo.getFilterItems().add(new FilterItemInfo("voucherRefer", PaySplitVoucherRefersEnum.NOTREFER_VALUE , CompareType.NOTEQUALS));
			
			viewInfo.getSelector().add("paymentBill.id");
			viewInfo.getSelector().add("workLoadConfirmBill.id");
			viewInfo.setFilter(filterInfo);
			PaymentSplitCollection paySplitColl = PaymentSplitFactory.getLocalInstance(ctx).getPaymentSplitCollection(viewInfo);
			if(paySplitColl!=null&&paySplitColl.size()>0){
				for(Iterator it=paySplitColl.iterator();it.hasNext();){
					PaymentSplitInfo paySplitInfo = (PaymentSplitInfo) it.next();
					if(paySplitInfo.getPaymentBill()!=null){
						paymentBillIdSet.add(paySplitInfo.getPaymentBill().getId().toString());
					}
					if(paySplitInfo.getWorkLoadConfirmBill()!=null){
						workLoadBillIdSet.add(paySplitInfo.getWorkLoadConfirmBill().getId().toString());
					}
				}
			}
	
		}
		viewInfo = new EntityViewInfo();
		filterInfo = new FilterInfo();
		if(!VoucherAdjustReasonEnum.INVALID.equals(voucherAdjustReasonEnum)){
			filterInfo.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE,CompareType.EQUALS));
			if(paymentBillIdSet.size()>0){
				filterInfo.getFilterItems().add(new FilterItemInfo("paymentBill.id", paymentBillIdSet,CompareType.INCLUDE));
			}
			else{
				String noExitsID=BOSUuid.create(new PaymentBillInfo().getBOSType()).toString();
				filterInfo.getFilterItems().add(new FilterItemInfo("paymentBill.id", noExitsID,CompareType.EQUALS));
			}
			if(workLoadBillIdSet.size()>0){
				filterInfo.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", workLoadBillIdSet,CompareType.INCLUDE));
			}
			else{
				String noExitsID=BOSUuid.create(new WorkLoadConfirmBillInfo().getBOSType()).toString();
				filterInfo.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", noExitsID,CompareType.EQUALS));
			}
			filterInfo.setMaskString("#0 and (#1 or #2)");
		}else{
			if(!isNoText)
				filterInfo.getFilterItems().add(new FilterItemInfo("contractBill.id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
			else
				filterInfo.getFilterItems().add(new FilterItemInfo("conWithoutText.id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
			
			filterInfo.getFilterItems().add(new FilterItemInfo("isBeforeAdjust",Boolean.TRUE,CompareType.EQUALS));
		}
		viewInfo.getSelector().add("id");
		viewInfo.getSelector().add("paymentBill.id");
		viewInfo.getSelector().add("paymentBill.contractBillId");
		if(!isNoText)
			viewInfo.getSelector().add("contractBill.id");
		else
			viewInfo.getSelector().add("conWithoutText.id");
		viewInfo.getSelector().add("curProject.id");
		viewInfo.getSelector().add("curProject.projectStatus.id");
		viewInfo.setFilter(filterInfo);
		//������ʱ������Ƶ���������֤��¼�Ѳ������������ǰһ��
		SorterItemInfo sort = new SorterItemInfo("paymentBill.createTime");
		sort.setSortType(SortType.ASCEND);
		viewInfo.getSorter().add(sort);
		
		System.out.print("aaa");
		/**
		 * �� ����ɱ���ֵ�
		 */
		PaymentSplitCollection paySplitColl = PaymentSplitFactory.getLocalInstance(ctx).getPaymentSplitCollection(viewInfo);
		
		for(Iterator it=paySplitColl.iterator();it.hasNext();){
			PaymentSplitInfo paySplitInfo = (PaymentSplitInfo) it.next();
//			if(paySplitInfo.getPaymentBill()!=null){
//				shouldTreat.add(paySplitInfo.getPaymentBill());
//			}
			String contractId = null;
			if(!isNoText)
			{
//				String contractId = null;
				//������Щ�������û�к�ͬ�ֶε����
				if(paySplitInfo.getPaymentBill()!=null){
					contractId=paySplitInfo.getPaymentBill().getContractBillId();
				}else if(paySplitInfo.getContractBill()!=null){
					contractId=paySplitInfo.getContractBill().getId().toString();
				}
				
				shouldTreatContractIdList.put(contractId,Boolean.TRUE);
				contractCurProject.put(contractId,paySplitInfo.getCurProject());
			}
			else
			{	
//				String contractId=null;
				if(paySplitInfo.getPaymentBill()!=null){
					contractId=paySplitInfo.getPaymentBill().getContractBillId();
				}else if(paySplitInfo.getConWithoutText()!=null){
					contractId=paySplitInfo.getConWithoutText().getId().toString();
				}
				shouldTreatContractIdList.put(contractId,Boolean.TRUE);
				contractCurProject.put(contractId,paySplitInfo.getCurProject());
			}
			if(paySplitInfo.getPaymentBill()!=null){
				if(shouldTreat.containsKey(contractId)){
					PaymentBillCollection payColls = (PaymentBillCollection)shouldTreat.get(contractId);
					payColls.add(paySplitInfo.getPaymentBill());
				}else{
					PaymentBillCollection payColls = new PaymentBillCollection();
					payColls.add(paySplitInfo.getPaymentBill());
					shouldTreat.put(contractId, payColls);
				}
			}
			
			conCurProjectIds.add(paySplitInfo.getCurProject().getId().toString());
		}
		if(!isNoText&&VoucherAdjustReasonEnum.INVALID.equals(voucherAdjustReasonEnum)){
			for(Iterator it=shouldTreatContractIdList.keySet().iterator();it.hasNext();){
				String contractID = (String) it.next();
//				_splitContract(ctx, contractID, shouldTreat);
				//Ӧ����ͬID������ͬ��Ӧ�ĸ������
				_splitContract(ctx, contractID,(PaymentBillCollection)shouldTreat.get(contractID));
			}
		}
		
		
		setAdjustPreCostAmtPaidAmt(shouldTreatContractIdList, adjustBillEntryMap, builder,costAccountIds,voucherAdjustReasonEnum);
		
		setAdjustCostAmtPayedAmt(shouldTreatContractIdList, adjustBillEntryMap, builder,costAccountIds,voucherAdjustReasonEnum);
		 
		setAdjustContractSplitAmt(ctx, shouldTreatContractIdList, adjustBillEntryMap,true,costAccountIds);
		
		setAdjustChangeSplitAmt(ctx, shouldTreatContractIdList, adjustBillEntryMap,true,costAccountIds);
			
		setPreGrtSplitAmt(ctx, shouldTreatContractIdList, adjustBillEntryMap , hasFilnalSettle,true,costAccountIds,voucherAdjustReasonEnum);
		
		setSettleGrtSplitAmt(ctx, shouldTreatContractIdList, adjustBillEntryMap , hasFilnalSettle,true,costAccountIds);
		
		
		/***
		 * ������ȡ������Ŀ���ڵ��ڼ�
		 */
		curProjectPeriod.putAll(FDCUtils.getCurrentPeriod(ctx,conCurProjectIds,true));
		
		/***
		 * �ٴ���ǳɱ����
		 */
		
		filterInfo = new FilterInfo();
		if(!VoucherAdjustReasonEnum.INVALID.equals(voucherAdjustReasonEnum)){
			filterInfo.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE,CompareType.EQUALS));
			if(paymentBillIdSet.size()>0){
				filterInfo.getFilterItems().add(new FilterItemInfo("paymentBill.id", paymentBillIdSet,CompareType.INCLUDE));
			}
			else{
				//
				String noExitsID=BOSUuid.create(new PaymentBillInfo().getBOSType()).toString();
				filterInfo.getFilterItems().add(new FilterItemInfo("paymentBill.id", noExitsID,CompareType.EQUALS));
			}
		}else{
			if(!isNoText)
				filterInfo.getFilterItems().add(new FilterItemInfo("contractBill.id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
			else
				filterInfo.getFilterItems().add(new FilterItemInfo("conWithoutText.id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
			
			filterInfo.getFilterItems().add(new FilterItemInfo("isBeforeAdjust",Boolean.TRUE,CompareType.EQUALS));
		}
		viewInfo.setFilter(filterInfo);
		Map shouldTreatContractNoCostIdList= new HashMap();
		shouldTreat.clear();
		conCurProjectIds.clear();
		//Set shouldUpdateNoCostIsBeforeAdjustFalse = new HashSet();
		PaymentNoCostSplitCollection payNoCostSplitColl = PaymentNoCostSplitFactory.getLocalInstance(ctx).getPaymentNoCostSplitCollection(viewInfo);
		for(Iterator it=payNoCostSplitColl.iterator();it.hasNext();){
			PaymentNoCostSplitInfo payNoCostSplitInfo = (PaymentNoCostSplitInfo) it.next();
			//shouldUpdateNoCostIsBeforeAdjustFalse.add(payNoCostSplitInfo.getId().toString());
//			shouldTreat.add(payNoCostSplitInfo.getPaymentBill());
			String contractId = null;
			if(!isNoText)
			{
				contractId = payNoCostSplitInfo.getContractBill().getId().toString();
				contractCurProject.put(payNoCostSplitInfo.getContractBill().getId().toString(),payNoCostSplitInfo.getCurProject());
			}
			else
			{	
				contractId = payNoCostSplitInfo.getConWithoutText().getId().toString();
				contractCurProject.put(payNoCostSplitInfo.getConWithoutText().getId().toString(),payNoCostSplitInfo.getCurProject());
			}
			if(payNoCostSplitInfo.getPaymentBill()!=null){
				if(shouldTreat.containsKey(contractId)){
					PaymentBillCollection payColls = (PaymentBillCollection)shouldTreat.get(contractId);
					payColls.add(payNoCostSplitInfo.getPaymentBill());
				}else{
					PaymentBillCollection payColls = new PaymentBillCollection();
					payColls.add(payNoCostSplitInfo.getPaymentBill());
					shouldTreat.put(contractId, payColls);
				}
			}
			shouldTreatContractNoCostIdList.put(contractId,Boolean.TRUE);
			conCurProjectIds.add(payNoCostSplitInfo.getCurProject().getId().toString());
		}
		if(!isNoText){
			for(Iterator it=shouldTreatContractNoCostIdList.keySet().iterator();it.hasNext();){
				String contractId = (String) it.next();
				_splitContract(ctx, contractId, (PaymentBillCollection)shouldTreat.get(contractId));
			}
		}
		
		/***
		 * 1
		 */
		setNoCostAdjustEntryPreCostAmtPayedAmt(shouldTreatContractNoCostIdList, adjustBillEntryMap, builder,costAccountIds,voucherAdjustReasonEnum);
		/***
		 * 2
		 */
		setNoCostAdjustEntryCostAmtPayedAmt(shouldTreatContractNoCostIdList, adjustBillEntryMap, builder,costAccountIds,voucherAdjustReasonEnum);
		/***
		 * 3
		 */
		setAdjustContractSplitAmt(ctx, shouldTreatContractNoCostIdList, adjustBillEntryMap,false,costAccountIds);
		/**
		 * 4
		 */
		setAdjustChangeSplitAmt(ctx, shouldTreatContractNoCostIdList, adjustBillEntryMap,false,costAccountIds);
		/***
		 * 5
		 */
		
		setPreGrtSplitAmt(ctx, shouldTreatContractNoCostIdList, adjustBillEntryMap , hasFilnalSettle , false,costAccountIds,voucherAdjustReasonEnum);
		
		setSettleGrtSplitAmt(ctx, shouldTreatContractNoCostIdList, adjustBillEntryMap , hasFilnalSettle , false,costAccountIds);
		/***
		 * ������ȡ������Ŀ���ڵ��ڼ�
		 */
		curProjectPeriod.putAll(FDCUtils.getCurrentPeriod(ctx,conCurProjectIds,false));
		
		/**
		 * ����ɱ���Ŀ��Ӧ�Ļ�ƿ�Ŀ
		 */
		Set curProjectIds = new HashSet();
		Set costAcctIds = new HashSet();
		for(Iterator it=adjustBillEntryMap.keySet().iterator();it.hasNext();){
			String key = (String) it.next();
			FDCAdjustBillEntryInfo info = (FDCAdjustBillEntryInfo)adjustBillEntryMap.get(key);
			if(info.getCostAccount()!=null){
				curProjectIds.add(key.split("_")[1]);
				costAcctIds.add(key.split("_")[2]);
			}
		}
		Map costAccountFlagsMap = new HashMap();
		if(costAcctIds.size()>0){
			EntityViewInfo costAccountFlagsViewInfo = new EntityViewInfo();
			FilterInfo costAccountFlagsFilterInfo   = new FilterInfo();
			costAccountFlagsFilterInfo.getFilterItems().add(new FilterItemInfo("id",costAcctIds,CompareType.INCLUDE));
			costAccountFlagsViewInfo.setFilter(costAccountFlagsFilterInfo);
			costAccountFlagsViewInfo.getSelector().add("id");
			costAccountFlagsViewInfo.getSelector().add("flag");
			CostAccountCollection costAccountFlags = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(costAccountFlagsViewInfo);
			for(Iterator it = costAccountFlags.iterator();it.hasNext();){
				CostAccountInfo costAccountInfo = (CostAccountInfo) it.next();
				costAccountFlagsMap.put(costAccountInfo.getId().toString(),costAccountInfo);
			}
		}
		
		BeforeAccountViewInfo beforeAccountViewInfo = null;
		EntityViewInfo before = new EntityViewInfo();
		FilterInfo beforeFilter = new FilterInfo();
		before.setFilter(beforeFilter);
		beforeFilter.getFilterItems().add(new FilterItemInfo("company.id",ContextUtil.getCurrentFIUnit(ctx).getId().toString(),CompareType.EQUALS));
		/**
		 * ������*û������,��������С
		 */
		before.getSelector().add("*");		
		BeforeAccountViewCollection beforeAccountViewCollection = BeforeAccountViewFactory.getLocalInstance(ctx).getBeforeAccountViewCollection(before);
		if(beforeAccountViewCollection==null||beforeAccountViewCollection.size()==0){
			throw new ContractException(ContractException.CANNOTFINDBEFOREACCOUNTVIEW);
		}else{
			beforeAccountViewInfo = beforeAccountViewCollection.get(0);
		}
		
//		Map costAccountWithAccountMap = FDCUtils.getCostAccountWithAccountMapAll(ctx, curProjectIds,accountIds);
		//���½ӿ��滻 by sxhong 2009-07-23 14:54:12
		Map costAccountWithAccountMap =CostAccountWithAccountHelper.getCostAcctWithAcctMapByCostAccountIds(ctx, costAcctIds);
		/***
		 * 
		 * end.�ɱ���ֽ��
		 �ɱ���ֽ�� = ����ͬ���������ս���Ľ����֣�����ں�ͬ��ֽ��+�����ֽ�
		 * 				���������ս���Ľ����֣�����ڽ����ֽ��
		 * 
		 * ���ò��
		 * ���óɱ���Ŀ��Ӧ�Ļ�ƿ�Ŀ
		 */
		Map shouldSaveMap = new HashMap();
		//���Ĳ��������������
		setCostAmtDifAmt(ctx, adjustBillMap, adjustBillEntryMap, hasFilnalSettle,
				costAccountWithAccountMap,voucherAdjustReasonEnum,
				contractCurProject,
				curProjectPeriod,
				shouldSaveMap,
				beforeAccountViewInfo,
				costAccountFlagsMap);
		
		Set shouldSaveKeySet = shouldSaveMap.keySet();
		Map adjustShouldSaveMap = new HashMap();
		
		for(Iterator it=shouldSaveKeySet.iterator();it.hasNext();){
			String shouldSaveKey = (String)it.next();
			Map amtMap = (Map)shouldSaveMap.get(shouldSaveKey);
			BigDecimal costAmt = (BigDecimal)amtMap.get(SHOULD_SAVE_COST_AMT);
			BigDecimal payedAmt = (BigDecimal)amtMap.get(SHOULD_SAVE_PAYED_AMT);
			BigDecimal qualityAmt = (BigDecimal)amtMap.get(SHOULD_SAVE_QUALITY_AMT);
			BigDecimal invoiceAmt = (BigDecimal)amtMap.get(SHOULD_SAVE_INVOICE_AMT);
			FDCAdjustBillInfo adjustBill = null;
			String adjustBillKey = shouldSaveKey.split("_")[0];
			adjustBill = (FDCAdjustBillInfo)adjustBillMap.get(adjustBillKey);
			if(costAmt.compareTo(FDCHelper.ZERO)!=0||
					payedAmt.compareTo(FDCHelper.ZERO)!=0||
					qualityAmt.compareTo(FDCHelper.ZERO)!=0||
					invoiceAmt.signum()!=0){
				adjustBill.setBeforeAccountView(beforeAccountViewInfo);
				adjustBill.put("ShouldSave",Boolean.TRUE);
				adjustShouldSaveMap.put(adjustBill.getId().toString(),adjustBill);
			}
			
		}
		/***
		 * �������SAVE
		 * ��������isBeforeAdjust=false
		 */
		IORMappingDAO dao = ORMappingDAO.getInstance(new FDCAdjustBillInfo().getBOSType(), ctx, getConnection(ctx));
		IFDCAdjustBill iFDCAdjustBill = FDCAdjustBillFactory.getLocalInstance(ctx);
		IObjectPK[] pks = new ObjectUuidPK[adjustShouldSaveMap.size()];
//		if(true)throw new BOSException("test");
		builder.clear();
		/***
		 * ����BUILDERִ�е�����SQL����SQL��䲻ͬ�����Է�һ��ʹ�ò���������ִ��SQL
		 * ����SQLÿ��ִ�ж���Ӳ����
		 */
		builder.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
//		CoreBaseCollection coreBaseColl = new CoreBaseCollection();
		
		int i=0;
		
		List hasAdjustList=new ArrayList();//�ѽ��е����ĺ�ͬId
		String paymentIds = "";
		if(paymentBillIdSet.size()>0){
			for(Iterator it=paymentBillIdSet.iterator();it.hasNext();){
				String id = (String)it.next();
				paymentIds +="'"+id+"',";
			}
			paymentIds = paymentIds.substring(0,paymentIds.length()-1);
		}else{
			//ʹ��һ�������ڵĸ���ID
			paymentIds="'bIxTilEyS4GcNH6AH9C1lUAoToE='";
		}
		
		String workLoadIds = "";
		if(workLoadBillIdSet.size()>0){
			for(Iterator it=workLoadBillIdSet.iterator();it.hasNext();){
				String id = (String)it.next();
				workLoadIds +="'"+id+"',";
			}
			workLoadIds = workLoadIds.substring(0,workLoadIds.length()-1);
		}else{
			workLoadIds = "'Kv+1uSb8QMSy8A3Yf+HA9eSjzWE='";
		}
		for(Iterator it=adjustShouldSaveMap.keySet().iterator();it.hasNext();){
			String key = (String)it.next();
			FDCAdjustBillInfo adjustBill = (FDCAdjustBillInfo)adjustShouldSaveMap.get(key);
			String contractId = adjustBill.getContractBill().getId().toString();
			/***
			 * ���ݻ�ƿ�Ŀ���Ƿ��в�������������������Ƿ���Ҫ���档����Ҫ���棬���Զ����棬����ƾ֤��
			 * ͬʱ����������������õĲ�־Ͳ�����ֱ���޸ġ�
			 * ��ƿ�Ŀ���Ƿ��в���綨��
			 * ��ͬ������Ŀ����ͬ��ƿ�Ŀ��������ǰ�ĳɱ����������������ĳɱ�����������ȫһ�£����޲��졣
			 * �ɱ������߸������е��κ�һ�����ڲ�ͬ�������ڲ��졣
			 */
			
			if(adjustBill.containsKey("ShouldSave")
					&&((Boolean)adjustBill.get("ShouldSave")).booleanValue()){
				hasAdjustList.add(contractId);
				if(adjustBill.isIsCost()){
					/***
					 * �������²�ֵĸ����ֵ�״̬Ϊ����������
					 * �����ǵ���ǰ��״̬
					 */
					StringBuffer sql = new StringBuffer();
					//��֧��db2��sql
					sql.append("update t_fnc_paymentsplit set FIsBeforeAdjust=0,FVoucherRefer='" + PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE + "'" + ",FVoucherReferID='"
							+ adjustBill.getId().toString() + "' where FState='" + FDCBillStateEnum.SAVED_VALUE + "' and ");
					sql.append(" exists (select 1 from t_fnc_paymentsplit where (FContractBillId=t_fnc_paymentsplit.fcontractbillid or fconwithouttextid=t_fnc_paymentsplit.fconwithouttextid) and fisbeforeadjust = 1 and ((fpaymentbillid is not null and fpaymentbillid=t_fnc_paymentsplit.fpaymentbillid) or (fworkloadbillid is not null and fworkloadbillid=t_fnc_paymentsplit.fworkloadbillid))) and ");
					sql.append(" (FContractBillId='" + contractId+"'");
					sql.append("or fconwithouttextid='" + contractId+"')");
					
					/*sql.append("update t_fnc_paymentsplit set FIsBeforeAdjust=0,FVoucherRefer='" + PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE + "'"+",FVoucherReferID='"+ adjustBill.getId().toString() + "' \n");
					sql.append("	where t_fnc_paymentsplit.FState='" + FDCBillStateEnum.SAVED_VALUE + "' \n");
					sql.append("	and  t_fnc_paymentsplit.fid in  ( \n");
					sql.append("	              select split.fid from t_fnc_paymentsplit split \n");
					sql.append("				  inner join t_fnc_paymentsplit split2 on (split.FContractBillId=split2.fcontractbillid or split.fconwithouttextid=split2.fconwithouttextid) \n");
					sql.append("	              where split.fisbeforeadjust = 1  \n");
					sql.append("                  and ((split.fpaymentbillid is not null and split.fpaymentbillid=split2.fpaymentbillid) or (split.fworkloadbillid is not null and split.fworkloadbillid=split2.fworkloadbillid)) \n");
					sql.append("                  and  (split.FContractBillId='" + contractId+"' or split.fconwithouttextid='" + contractId+"') \n");
					sql.append("                 ) \n");
					sql.append("   and  (t_fnc_paymentsplit.FContractBillId='" + contractId+"' or t_fnc_paymentsplit.fconwithouttextid='" + contractId+"' ); ");
						*/
						
						
						
					builder.addBatch(sql.toString());
					
				//	builder.executeBatch();
				}
				else{
					/***
					 * �������²�ֵĸ����ֵ�״̬Ϊ����������
					 * �����ǵ���ǰ��״̬
					 */
					StringBuffer sql = new StringBuffer();
					sql.append("update t_fnc_paymentnocostsplit set FIsBeforeAdjust=0,FVoucherRefer='"+PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE+"'" +
							",FVoucherReferID='"+adjustBill.getId().toString()+"' where FState='"+FDCBillStateEnum.SAVED_VALUE+"' and "+
							" fpaymentbillid in (");
					sql.append("   select pay1.fid from "); 
					sql.append("   t_fnc_paymentnocostsplit paysplit1 ");
					sql.append("   inner join t_cas_paymentbill pay1 on paysplit1.fpaymentbillid=pay1.fid where ");
					sql.append("pay1.FContractBillId='"+contractId+"'");
					sql.append("   and paysplit1.fisbeforeadjust = 1");
					
					sql.append(")");
					builder.addBatch(sql.toString());
				}
				if(!VoucherAdjustReasonEnum.INVALID.equals(voucherAdjustReasonEnum)&&paymentBillIdSet.size()>0){
					builder.addBatch("update t_fnc_paymentsplit set FIsBeforeAdjust=0,FVoucherRefer='"+PaySplitVoucherRefersEnum.ADJUSTBILL_VALUE+"'" +
							",FVoucherReferID='"+adjustBill.getId().toString()+"' where FState='"+FDCBillStateEnum.SAVED_VALUE+"' and "+
							" fpaymentbillid in ("+paymentIds+") and fworkLoadBillid in ("+workLoadIds+") and "+fcontractbillid+"='"+contractId+"'");
				}
//				coreBaseColl.add(adjustBill);
				dao.addNewBatch(adjustBill);
				pks[i] = new ObjectUuidPK(adjustBill.getId());
				i++;
			}else{
				
			}
		}
		/***
		 * ���µ���ǰΪ0,ֻ�Խ����˵����Ľ��и��£����û�н��е����򲻸���������֤�´εĵ�������������������Ͻ������� by sxhong
		 * Ҫ�����еĶ����и��£���Ϊ������ֻ˵����û�б䷨�����ǵ�����׼����Ӧ�ô���
		 */
/*		if(idList.size()>0){ //�޸����������޸Ľ�����û�����ɵ������ĸ����ֲ��ܼ���������Ӧ��ֻ��д�����˵�  by sxhong 2009-08-02 00:12:16
			String contractIds = "";
			for(int id=0;id<idList.size();id++){
				contractIds +="'"+idList.get(id)+"',";
			}*/
		if(hasAdjustList.size()>0){
			String contractIds = "";
			for(int id=0;id<hasAdjustList.size();id++){
				contractIds +="'"+hasAdjustList.get(id)+"',";
			}
			contractIds = contractIds.substring(0,contractIds.length()-1);
			builder.addBatch("update t_fnc_paymentsplit set FIsBeforeAdjust=0 where FIsBeforeAdjust=1 and "+fcontractbillid+" in ("+contractIds+")");
			builder.addBatch("update T_CON_SettlementCostSplit set FIsBeforeAdjust=0 where FIsBeforeAdjust=1 and FContractBillId in ("+contractIds+")");
		
			builder.addBatch("update t_fnc_paymentnocostsplit set FIsBeforeAdjust=0 where FIsBeforeAdjust=1 and "+fcontractbillid+" in ("+contractIds+")");
			builder.addBatch("update T_CON_SettNoCostSplit set FIsBeforeAdjust=0 where FIsBeforeAdjust=1 and FContractBillId in ("+contractIds+")");
			
		}
		//db2�´��ڻع�����,����ORMappingDAO
		/*if(coreBaseColl.size()>0)
		{
			iFDCAdjustBill.save(coreBaseColl);
		}*/	
		if(pks.length>0)
		{
			dao.executeBatch();
		}
		builder.executeBatch();
		
		
		/***
		 * �����������ƾ֤
		 */
		
		if(pks.length > 0)
			iFDCAdjustBill.generateVoucher(pks);
		
		/***
		 * ����ƾ֤�󣬸��¸������ϣ�ƾ֤��������
		 */
				
		shouldTreatContractIdList.putAll(shouldTreatContractNoCostIdList);
		/**
		 * ����ǹ�����Ŀ״̬ת������Ҫ���´������ͬ��״̬
		 * ���´������ͬ״̬Ϊ�������
		 */
		if(VoucherAdjustReasonEnum.INVALID.equals(voucherAdjustReasonEnum)){
			//����ҲӦ����ȫ����ͬ�������Ǳ����˵������ĺ�ͬ����Ϊ������û�б�������Ϊ�����ڱ䷨�����Ǵ������Ǵ������˵�
			if(!isNoText&&idList.size()>0){
				builder.clear();
				builder.appendSql("update T_CON_ContractBill set FConSplitExecState='"+ConSplitExecStateEnum.INVALIDDID_VALUE+"' where ");
				builder.appendParam("fid",idList.toArray());
				builder.executeUpdate();
			}
			if(isNoText&&idList.size()>0){
				builder.clear();
				builder.appendSql("update t_con_ContractWithoutText set FConSplitExecState='"+ConSplitExecStateEnum.INVALIDDID_VALUE+"' where ");
				
				builder.appendParam("fid",idList.toArray());
				builder.executeUpdate();
			}
		}
	}

	/**
	 * 
	 * shouldSaveMap
	 * key=adjustbillid+curprojectid+accountviewid value=���
	 * 
	 * ���ݻ�ƿ�Ŀ���Ƿ��в�������������������Ƿ���Ҫ���档����Ҫ���棬���Զ����棬����ƾ֤��ͬʱ����������������õĲ�־Ͳ�����ֱ���޸ġ�
	 * ��ƿ�Ŀ���Ƿ��в���綨��
	 * ��ͬ������Ŀ����ͬ��ƿ�Ŀ��������ǰ�ĳɱ����������������ĳɱ�����������ȫһ�£����޲��졣�ɱ������߸������е��κ�һ�����ڲ�ͬ�������ڲ��졣
	 * 
	 * �����û�ƿ�Ŀ+������Ŀ�Ĺ鼯�ϼ���
	 * ����Ҫ���빤����Ŀ�鼯��Ӧ���ʿ�_���ȿ�ϼ���
	 *      �Լ�������Ŀ�鼯��Ӧ���ʿ�_���޿�ϼ���
	 * 
	 * @param ctx
	 * @param adjustBillMap
	 * @param adjustBillEntryMap
	 * @param hasFilnalSettle
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void setCostAmtDifAmt(Context ctx, Map adjustBillMap, Map adjustBillEntryMap, Map hasFilnalSettle,
			Map costAccountWithAccountMap,VoucherAdjustReasonEnum voucherAdjustReasonEnum,
			Map contractCurProject,
			Map curProjectPeriod,
			Map shouldSaveMap,
			BeforeAccountViewInfo beforeAccountViewInfo,
			Map costAccountFlagsMap) throws BOSException, EASBizException {

		String companyId=ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		HashMap paramMap = FDCUtils.getDefaultFDCParam(ctx, companyId);
		boolean isInvoiceMgr=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INVOICEMRG);
		
		Set keySet = adjustBillEntryMap.keySet();
		
		/***
		 * ��MAPΪ��������¼�еĺϼ��е�ֵ
		 */
		Map adjustBillEntrySumMap = new HashMap();
		/***
		 * ��MAPΪ��������¼�е�Ӧ���ʿ�ϼ��е�ֵ
		 */
		for(Iterator it=keySet.iterator();it.hasNext();){
			String key = (String)it.next();
			String contractId = key.split("_")[0];
			String curprojectId = key.split("_")[1];
			String costAccountId = key.split("_")[2];
			FDCAdjustBillEntryInfo info = (FDCAdjustBillEntryInfo)adjustBillEntryMap.get(key);
			if(!hasFilnalSettle.containsKey(contractId)){
				BigDecimal amt = FDCHelper.toBigDecimal(info.getContractSplitAmt()).add(FDCHelper.toBigDecimal(info.getChangeSplitAmt()));
				info.setCostAmt(amt);
			}else{
				info.setCostAmt(info.getSettleSplitAmt());
			}
			
			/**
			 * ���ò��
			 */
			BigDecimal difAmt = FDCHelper.toBigDecimal(info.getHappenCostAmt()).subtract(FDCHelper.toBigDecimal(info.getPreHappenCostAmt()));
			info.setCostDifAmt(difAmt);
			BigDecimal difPaidAmt = FDCHelper.toBigDecimal(info.getPayedAmt()).subtract(FDCHelper.toBigDecimal(info.getPrePayedAmt()));
			info.setPayedDifAmt(difPaidAmt);
			BigDecimal difQualityAmt = FDCHelper.toBigDecimal(info.getQualityAmt()).subtract(FDCHelper.toBigDecimal(info.getPreQualityAmt()));
			info.setQualityDifAmt(difQualityAmt);
			//��Ʊ��ֵ
			info.setInvoiceDifAmt(FDCHelper.subtract(info.getInvoiceAmt(), info.getPreInvoiceAmt()));
			
			FDCAdjustBillInfo adjustBill = null;
			if(adjustBillMap.containsKey(contractId)){
				adjustBill = (FDCAdjustBillInfo) adjustBillMap.get(contractId);
			}else{
				adjustBill = getNewAdjustBillInfo(ctx, adjustBillMap, voucherAdjustReasonEnum, contractCurProject, curProjectPeriod, contractId);
			}
			adjustBill.setAmount(adjustBill.getAmount().add(info.getCostDifAmt()));
			adjustBill.setOriginalAmount(adjustBill.getAmount());
			
			
			if(info.getCostAccount()!=null){
				
				adjustBill.setIsCost(true);
				info.setIsLeaf(true);
				String withAccountKey = curprojectId+ "_" + costAccountId;
				if(costAccountWithAccountMap.get(withAccountKey)!=null&&((CostAccountWithAccountInfo)costAccountWithAccountMap.get(withAccountKey)).getAccount()!=null){
					info.setAccountView(((CostAccountWithAccountInfo)costAccountWithAccountMap.get(withAccountKey)).getAccount());
				}else{
					throw new PaymentSplitException(PaymentSplitException.HASCOSTACCOUNTNOTMAPACCOUNT,
							new Object[]{info.getCostAccount().getCurProject().getLongNumber().replace('!','.'),
							info.getCostAccount().getLongNumber().replace('!','.')});
				}
				//R101216-162:֧���޷�Ʊ by hpw 2011.4.29
				if(isInvoiceMgr){
					//���óɱ���Ŀ��Ӧ�ķ�Ʊ�Ŀ�Ŀ
					if(costAccountWithAccountMap.get(withAccountKey)!=null&&((CostAccountWithAccountInfo)costAccountWithAccountMap.get(withAccountKey)).getInvoiceAccount()!=null){
						info.setInvoiceAcct(((CostAccountWithAccountInfo)costAccountWithAccountMap.get(withAccountKey)).getInvoiceAccount());
					}else{
						throw new PaymentSplitException(PaymentSplitException.HASCOSTACCOUNTNOTMAPINVOICEACCOUNT,
								new Object[]{info.getCostAccount().getCurProject().getLongNumber().replace('!','.'),
								info.getCostAccount().getLongNumber().replace('!','.')});
					}
				}
				/***
				 * �����ͬ��Ӧ�Ĺ�����Ŀ״̬Ϊδ��ȡ,��ô��Ӧ�Ļ�ƿ�ĿӦ��Ϊ
				 * Ԥ���ʿ�-�����ɱ�-�����ɱ�
				 */
				if(VoucherAdjustReasonEnum.INVALID.equals(voucherAdjustReasonEnum)&&ProjectStatusInfo.notGetID.equals(adjustBill.getCurProject().getProjectStatus().getId().toString())){
					info.setAccountView(beforeAccountViewInfo.getBeforeOtherAccount());
				}
				/****
				 * ����Ƿ����Ϻ�ͬ��ֲ����ĵ�����[��������Ŀ��ȡ,�͹�����Ŀ��ʧ][����ȡ������ʧ]
				 * �����  ������Ŀ��ȡ,�͹�����Ŀ��ʧ 
				 * ����ǰӦ����Ԥ���ʿ�-�����ɱ�-�����ɱ�
				 * ������Ӧ���Ƕ�Ӧ��Ӧ�Ŀ����ɱ�,���ɱ���Ŀ��Ӧ�Ŀ����ɱ�
				 * 
				 * ����� ����ȡ������ʧ
				 * ����ǰӦ���Ƕ�Ӧ��Ӧ�Ŀ����ɱ�,���ɱ���Ŀ��Ӧ�Ŀ����ɱ�
				 * ������Ӧ����Ԥ���ʿ�-�����ɱ�-�����ɱ�
				 */
				if(!VoucherAdjustReasonEnum.INVALID.equals(voucherAdjustReasonEnum)){
					
					if(info.containsKey("isBeforeAdjust")){
						Boolean isBeforeAdjust= (Boolean)info.get("isBeforeAdjust");
						if(VoucherAdjustReasonEnum.STATUSFLOW.equals(voucherAdjustReasonEnum)||
								VoucherAdjustReasonEnum.STATUSGET.equals(voucherAdjustReasonEnum)){
							/***
							 * ������Ŀ��ȡ,�͹�����Ŀ��ʧ
							 */
							if(isBeforeAdjust.booleanValue()){
								info.setAccountView(beforeAccountViewInfo.getBeforeOtherAccount());
							}
							if(!isBeforeAdjust.booleanValue()
									&&VoucherAdjustReasonEnum.STATUSFLOW.equals(voucherAdjustReasonEnum)){
								if(costAccountFlagsMap.containsKey(costAccountId)){
									CostAccountInfo flag = (CostAccountInfo) costAccountFlagsMap.get(costAccountId);
									if(flag.getFlag()==1)
										info.setAccountView(beforeAccountViewInfo.getMarketFees());
									else
										info.setAccountView(beforeAccountViewInfo.getAdminFees());
								}
							}
						}else{
							/***
							 * ������Ŀ����ȡ,�͹�����Ŀ����ʧ
							 */
							if(!isBeforeAdjust.booleanValue()){
								info.setAccountView(beforeAccountViewInfo.getBeforeOtherAccount());
							}
							if(isBeforeAdjust.booleanValue()
									&&VoucherAdjustReasonEnum.STATUSANTIFLOW.equals(voucherAdjustReasonEnum)){
								if(costAccountFlagsMap.containsKey(costAccountId)){
									CostAccountInfo flag = (CostAccountInfo) costAccountFlagsMap.get(costAccountId);
									if(flag.getFlag()==1)
										info.setAccountView(beforeAccountViewInfo.getMarketFees());
									else
										info.setAccountView(beforeAccountViewInfo.getAdminFees());
								}
							}
						}
						
					}
				
					
				}
			}else{
				adjustBill.setIsCost(false);
				info.setIsLeaf(false);
			}
			
			/***
			 * ��������¼�еĺϼ��е�key
			 * contractid + ��ƿ�Ŀid + ������Ŀid
			 */
			String entrySumKey = contractId +"_"+ info.getAccountView().getId()+"_"+info.getCurProject().getId();
			if(adjustBill.isIsCost()){
				FDCAdjustBillEntryInfo entrySum = null;
				if(adjustBillEntrySumMap.containsKey(entrySumKey))
					entrySum = (FDCAdjustBillEntryInfo)adjustBillEntrySumMap.get(entrySumKey);
				else
				{
					entrySum = new FDCAdjustBillEntryInfo();
					entrySum.setCurProject(info.getCurProject());
					entrySum.setAccountView(info.getAccountView());
					//��Ʊ��Ŀ
					entrySum.setInvoiceAcct(info.getInvoiceAcct());
					entrySum.setSeq(0);
					entrySum.setIsLeaf(false);
					adjustBillEntrySumMap.put(entrySumKey,entrySum);
					entrySum.setParent(adjustBill);
					adjustBill.getEntrys().add(entrySum);
				}
				entrySum.setCostDifAmt(FDCHelper.toBigDecimal(info.getCostDifAmt()).add(FDCHelper.toBigDecimal(entrySum.getCostDifAmt())));
				entrySum.setCostAmt(FDCHelper.toBigDecimal(info.getCostAmt()).add(FDCHelper.toBigDecimal(entrySum.getCostAmt())));
				entrySum.setPreHappenCostAmt(FDCHelper.toBigDecimal(info.getPreHappenCostAmt()).add(FDCHelper.toBigDecimal(entrySum.getPreHappenCostAmt())));
				entrySum.setHappenCostAmt(FDCHelper.toBigDecimal(info.getHappenCostAmt()).add(FDCHelper.toBigDecimal(entrySum.getHappenCostAmt())));
				entrySum.setPayedAmt(FDCHelper.toBigDecimal(info.getPayedAmt()).add(FDCHelper.toBigDecimal(entrySum.getPayedAmt())));
				entrySum.setPayedDifAmt(FDCHelper.toBigDecimal(info.getPayedDifAmt()).add(FDCHelper.toBigDecimal(entrySum.getPayedDifAmt())));
				entrySum.setPrePayedAmt(FDCHelper.toBigDecimal(info.getPrePayedAmt()).add(FDCHelper.toBigDecimal(entrySum.getPrePayedAmt())));
				entrySum.setSettleSplitAmt(FDCHelper.toBigDecimal(info.getSettleSplitAmt()).add(FDCHelper.toBigDecimal(entrySum.getSettleSplitAmt())));
				entrySum.setContractSplitAmt(FDCHelper.toBigDecimal(info.getContractSplitAmt()).add(FDCHelper.toBigDecimal(entrySum.getContractSplitAmt())));
				entrySum.setChangeSplitAmt(FDCHelper.toBigDecimal(info.getChangeSplitAmt()).add(FDCHelper.toBigDecimal(entrySum.getChangeSplitAmt())));
				entrySum.setPreGrtSplitAmt(FDCHelper.toBigDecimal(info.getPreGrtSplitAmt()).add(FDCHelper.toBigDecimal(entrySum.getPreGrtSplitAmt())));
				entrySum.setGrtSplitAmt(FDCHelper.toBigDecimal(info.getGrtSplitAmt()).add(FDCHelper.toBigDecimal(entrySum.getGrtSplitAmt())));
				entrySum.setQualityAmt(FDCHelper.toBigDecimal(info.getQualityAmt()).add(FDCHelper.toBigDecimal(entrySum.getQualityAmt())));
				entrySum.setQualityDifAmt(FDCHelper.toBigDecimal(info.getQualityDifAmt()).add(FDCHelper.toBigDecimal(entrySum.getQualityDifAmt())));
				entrySum.setPreQualityAmt(FDCHelper.toBigDecimal(info.getPreQualityAmt()).add(FDCHelper.toBigDecimal(entrySum.getPreQualityAmt())));
				//handle invoice
				entrySum.setPreInvoiceAmt(FDCHelper.add(info.getPreInvoiceAmt(),entrySum.getPreInvoiceAmt()));
				entrySum.setInvoiceAmt(FDCHelper.add(info.getInvoiceAmt(),entrySum.getInvoiceAmt()));
				entrySum.setInvoiceDifAmt(FDCHelper.add(info.getInvoiceDifAmt(),entrySum.getInvoiceDifAmt()));
				
				int index = adjustBill.getEntrys().indexOf(entrySum);
				adjustBill.getEntrys().insertObject(index-1,info);
			}else{
				adjustBill.getEntrys().add(info);
			}
				
			/***
			 * ��ͬ������Ŀ����ͬ��ƿ�Ŀ��������ǰ�ĳɱ����������������ĳɱ�����������ȫһ�£����޲��졣
			 * �ɱ������߸������е��κ�һ�����ڲ�ͬ�������ڲ��졣
			 */
			
			updateShouldSaveMap(shouldSaveMap, info, adjustBill);
			
			info.setParent(adjustBill);
			/*
			 * ��ͬ��ƿ�Ŀ��ͬ������Ŀ��Ӧ���˿�Ҫ���ϲ���ԭ����Ӧ���˿���ͬһ����Ŀ�������ɱ������-���ȿ�����������Ŀ�ϲ���һ���ϼ�������
			 *  by sxhong 2009-07-23 15:21:32 
			 */
			Map tempMap=new HashMap();
			for(Iterator iter=adjustBill.getEntrys().iterator();iter.hasNext();){
				FDCAdjustBillEntryInfo entry=(FDCAdjustBillEntryInfo)iter.next();
				if(entry.isIsLeaf()||entry.getParent()==null||entry.getParent().getContractBill()==null||entry.getCurProject()==null){
					continue;
				}
				BigDecimal shouldPayDif = FDCHelper.subtract(entry.getCostDifAmt(), entry.getPayedDifAmt());
				String myKey=entry.getParent().getContractBill().getId().toString()+entry.getCurProject().getId().toString();
				if(tempMap.containsKey(myKey)){
					FDCAdjustBillEntryInfo tmpEntry=(FDCAdjustBillEntryInfo)tempMap.get(myKey);
					tmpEntry.setShouldPayDifAmt(FDCHelper.add(shouldPayDif, tmpEntry.getShouldPayDifAmt()));
					tmpEntry.setShouldQualityDifAmt(FDCHelper.add(tmpEntry.getShouldQualityDifAmt(), entry.getQualityDifAmt()));
				}else{
					entry.setShouldPayDifAmt(shouldPayDif);
					entry.setShouldQualityDifAmt(entry.getQualityDifAmt());
					tempMap.put(myKey, entry);
				}
			}
		}
	}

	/**
	 * @param ctx
	 * @param adjustBillMap
	 * @param voucherAdjustReasonEnum
	 * @param contractCurProject
	 * @param curProjectPeriod
	 * @param contractId
	 * @return
	 */
	private FDCAdjustBillInfo getNewAdjustBillInfo(Context ctx, Map adjustBillMap, VoucherAdjustReasonEnum voucherAdjustReasonEnum, Map contractCurProject, Map curProjectPeriod, String contractId) {
		FDCAdjustBillInfo adjustBill;
		adjustBill = new FDCAdjustBillInfo();
		adjustBill.setId(BOSUuid.create(adjustBill.getBOSType()));
		adjustBill.setName(adjustBill.getId().toString());
		//adjustBillColl.add(adjustBill);
		adjustBillMap.put(contractId,adjustBill);
		
		ContractBillInfo con = new ContractBillInfo();
		con.setId(BOSUuid.read(contractId));
		adjustBill.setContractBill(con);
		
		/***
		 * ���ù�����Ŀ
		 */
		CurProjectInfo conCurProject = (CurProjectInfo)contractCurProject.get(contractId);
		adjustBill.setCurProject(conCurProject);
		/***
		 * ���ù�����Ŀ�ڼ�
		 */
		PeriodInfo period = (PeriodInfo) curProjectPeriod.get(conCurProject.getId().toString());
		adjustBill.setPeriod(period);
		
		adjustBill.setAdjustReason(voucherAdjustReasonEnum);
		
		adjustBill.setIsNeedTraceVoucher(true);
		adjustBill.setFiVouchered(false);
		
		adjustBill.setOrgUnit(ContextUtil.getCurrentFIUnit(ctx).castToFullOrgUnitInfo());
		adjustBill.setAmount(FDCHelper.ZERO);
		adjustBill.setState(FDCBillStateEnum.AUDITTED);
		adjustBill.setAuditTime(FDCHelper.getSqlDate());
		adjustBill.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		return adjustBill;
	}

	/**
	 * ���ý����
	 * @param shouldSaveMap
	 * @param info
	 * @param adjustBill
	 */
	private void updateShouldSaveMap(Map shouldSaveMap, FDCAdjustBillEntryInfo info, FDCAdjustBillInfo adjustBill) {
		String ShouldSaveKey = adjustBill.getContractBill().getId().toString();
		ShouldSaveKey += "_"+info.getCurProject().getId().toString();
		ShouldSaveKey += "_"+info.getAccountView().getId().toString();
		Map amtMap = null;
		
		if(shouldSaveMap.containsKey(ShouldSaveKey)&&shouldSaveMap.get(ShouldSaveKey)!=null)
			amtMap = (Map)shouldSaveMap.get(ShouldSaveKey);
		else{
			amtMap = new HashMap();
			amtMap.put(SHOULD_SAVE_COST_AMT,FDCHelper.ZERO);
			amtMap.put(SHOULD_SAVE_PAYED_AMT,FDCHelper.ZERO);
			amtMap.put(SHOULD_SAVE_QUALITY_AMT,FDCHelper.ZERO);
			amtMap.put(SHOULD_SAVE_INVOICE_AMT,FDCHelper.ZERO);
			shouldSaveMap.put(ShouldSaveKey,amtMap);
			
		}
		System.out.print("");
		BigDecimal shouldSaveCostAmt = (BigDecimal)amtMap.get(SHOULD_SAVE_COST_AMT);
		shouldSaveCostAmt = shouldSaveCostAmt.add(info.getCostDifAmt());
		amtMap.put(SHOULD_SAVE_COST_AMT,shouldSaveCostAmt);
		BigDecimal shouldSavePayedAmt = (BigDecimal)amtMap.get(SHOULD_SAVE_PAYED_AMT);
		shouldSavePayedAmt = shouldSavePayedAmt.add(info.getPayedDifAmt());
		amtMap.put(SHOULD_SAVE_PAYED_AMT,shouldSavePayedAmt);
		BigDecimal shouldSaveQualityAmt = (BigDecimal)amtMap.get(SHOULD_SAVE_QUALITY_AMT);
		shouldSaveQualityAmt = shouldSaveQualityAmt.add(info.getQualityDifAmt());
		amtMap.put(SHOULD_SAVE_QUALITY_AMT,shouldSavePayedAmt);
		//handle invoice
		amtMap.put(SHOULD_SAVE_INVOICE_AMT,FDCHelper.add(amtMap.get(SHOULD_SAVE_INVOICE_AMT), info.getInvoiceDifAmt()));
	}

	/**
	 * @param idList
	 * @param adjustBillEntryMap
	 * @param builder
	 * @throws BOSException
	 */
	private void setNoCostAdjustEntryCostAmtPayedAmt(Map idList, Map adjustBillEntryMap, FDCSQLBuilder builder,Set costAccountIds,VoucherAdjustReasonEnum voucherAdjustReasonEnum) throws BOSException {
		if(idList==null||idList.size()==0){
			return;
		}
//		builder.appendSql("select pay.fcontractbillid ,splitentry.fcurprojectid,splitentry.faccountviewid,sum(splitentry.famount) as amount,sum(splitentry.fpayedamt) as payedAmt,sum(splitentry.fqualityAmount) as qualityAmt from "); 
		builder.appendSql("select pay.fcontractbillid ,splitentry.fcurprojectid,splitentry.faccountviewid,splitentry.fproductid,splitentry.famount as amount,splitentry.fpayedamt as payedAmt,splitentry.fqualityAmount as qualityAmt from ");
		builder.appendSql(" t_fnc_paymentNoCostSplitEntry splitentry inner join t_fnc_paymentNoCostSplit paysplit on splitentry.fparentid=paysplit.fid");
		builder.appendSql(" inner join t_cas_paymentbill pay on paysplit.fpaymentbillid=pay.fid where ");
		
		builder.appendSql(" pay.fid in (");
		builder.appendSql("   select pay1.fid from "); 
		builder.appendSql("   t_fnc_paymentNoCostSplit paysplit1 ");
		builder.appendSql("   inner join t_cas_paymentbill pay1 on paysplit1.fpaymentbillid=pay1.fid where ");
		builder.appendParam("pay1.fcontractbillid",idList.keySet().toArray());
		builder.appendSql("   and paysplit1.fisbeforeadjust = 1");		
		builder.appendSql(" )");
		
		
		builder.appendSql(" and splitentry.faccountviewid is not null ");
		builder.appendSql(" and splitentry.fisleaf=1 ");
		builder.appendSql(" and paysplit.fisinvalid = 0 ");
//		builder.appendSql(" group by pay.fcontractbillid,splitentry.fcurprojectid,splitentry.faccountviewid");
		
		
		IRowSet rs = builder.executeQuery();
		if(rs!=null&&rs.size()>0){
			setAdjustEntryCostAmtPayedAmt(adjustBillEntryMap, rs, false , false,costAccountIds,voucherAdjustReasonEnum);
		}
		builder.clear();
	}

	/**
	 * @param idList
	 * @param adjustBillEntryMap
	 * @param builder
	 * @throws BOSException
	 */
	private void setNoCostAdjustEntryPreCostAmtPayedAmt(Map idList, Map adjustBillEntryMap, FDCSQLBuilder builder,Set costAccountIds,VoucherAdjustReasonEnum voucherAdjustReasonEnum) throws BOSException {
		if(idList==null||idList.size()==0){
			return;
		}
//		builder.appendSql("select pay.fcontractbillid ,splitentry.fcurprojectid,splitentry.faccountviewid,sum(splitentry.famount) as amount,sum(splitentry.fpayedamt) as payedAmt,sum(splitentry.fqualityAmount) as qualityAmt from "); 
		builder.appendSql("select pay.fcontractbillid ,splitentry.fcurprojectid,splitentry.faccountviewid,splitentry.fproductid,splitentry.famount as amount,splitentry.fpayedamt as payedAmt,splitentry.fqualityAmount as qualityAmt from ");
		builder.appendSql(" t_fnc_paymentNoCostSplitEntry splitentry inner join t_fnc_paymentNoCostSplit paysplit on splitentry.fparentid=paysplit.fid");
		builder.appendSql(" inner join t_cas_paymentbill pay on paysplit.fpaymentbillid=pay.fid where ");
		builder.appendParam("pay.fcontractbillid",idList.keySet().toArray());
		builder.appendSql(" and splitentry.faccountviewid is not null ");
		builder.appendSql(" and splitentry.fisleaf=1 ");
		builder.appendSql(" and paysplit.fisbeforeadjust = 1");		
//		builder.appendSql(" group by pay.fcontractbillid,splitentry.fcurprojectid,splitentry.faccountviewid");
		
		IRowSet rs = builder.executeQuery();
		if(rs!=null&&rs.size()>0){
			setAdjustEntryCostAmtPayedAmt(adjustBillEntryMap, rs , true ,false,costAccountIds,voucherAdjustReasonEnum);
		}
		builder.clear();
	}
	/****
	 * ���õ���ǰ�ı��޿���
	 * @param ctx
	 * @param idList
	 * @param adjustBillEntryMap
	 * @param hasFinalSettle
	 * @param isCostSplit
	 * @param costAccountIds
	 * @throws BOSException
	 */
	private void setPreGrtSplitAmt(Context ctx, Map idList, Map adjustBillEntryMap,Map hasFinalSettle,boolean isCostSplit,Set costAccountIds,VoucherAdjustReasonEnum voucherAdjustReasonEnum) throws BOSException {
		if(idList==null||idList.size()==0){
			return ;
		}
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("parent.contractBill.id",idList.keySet(),CompareType.INCLUDE));
		if(VoucherAdjustReasonEnum.INVALID.equals(voucherAdjustReasonEnum)){
			filterInfo.getFilterItems().add(new FilterItemInfo("parent.isInvalid",Boolean.TRUE));
			filterInfo.getFilterItems().add(new FilterItemInfo("parent.isBeforeAdjust",Boolean.TRUE));
		}else{
			filterInfo.getFilterItems().add(new FilterItemInfo("parent.isInvalid",Boolean.FALSE));
		}
		
		filterInfo.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		viewInfo.setFilter(filterInfo);
		viewInfo.getSelector().add("id");
		viewInfo.getSelector().add("parent.settlementBill.id");
		viewInfo.getSelector().add("parent.settlementBill.isFinalSettle");
		viewInfo.getSelector().add("parent.contractBill.id");
		viewInfo.getSelector().add("parent.curProject.id");
		viewInfo.getSelector().add("parent.id");
		viewInfo.getSelector().add("amount");
		viewInfo.getSelector().add("grtSplitAmt");
		IObjectCollection coll = null;
		if(isCostSplit){
			viewInfo.getSelector().add("costAccount.id");
			viewInfo.getSelector().add("costAccount.longnumber");
			viewInfo.getSelector().add("costAccount.curProject.id");
			viewInfo.getSelector().add("costAccount.curProject.longnumber");
			coll = SettlementCostSplitEntryFactory.getLocalInstance(ctx).getSettlementCostSplitEntryCollection(viewInfo);
		}
		else{
			viewInfo.getSelector().add("accountView.id");
			viewInfo.getSelector().add("curProject.id");
			coll = SettNoCostSplitEntryFactory.getLocalInstance(ctx).getSettNoCostSplitEntryCollection(viewInfo);
		}
		
		
		if(coll!=null&&coll.size()>0){
			for(Iterator it=coll.iterator();it.hasNext();){
				if(isCostSplit){
					SettlementCostSplitEntryInfo splitEntryInfo = (SettlementCostSplitEntryInfo)it.next();
					Boolean hasFinal = Boolean.FALSE;
					/**
					 * ���Ƿ������ս���ı�ʾ����¼��MAP�У��Ա�����
					 */
					if(hasFinalSettle.containsKey(splitEntryInfo.getParent().getContractBill().getId().toString())){
						hasFinal = Boolean.TRUE;
					}
					if(!hasFinal.booleanValue()&&splitEntryInfo.getParent().getSettlementBill().getIsFinalSettle().equals(BooleanEnum.TRUE)){
						hasFinalSettle.put(splitEntryInfo.getParent().getContractBill().getId().toString(),Boolean.TRUE);
					}
					String key = splitEntryInfo.getParent().getContractBill().getId().toString();
					
					key += "_" + splitEntryInfo.getCostAccount().getCurProject().getId().toString();
					
					key += "_" + splitEntryInfo.getCostAccount().getId().toString();
					FDCAdjustBillEntryInfo info = null;
					if(adjustBillEntryMap.containsKey(key)){
						info = (FDCAdjustBillEntryInfo)adjustBillEntryMap.get(key);
					}else{
						info = getNewAdjustBillEntryInfo();
						adjustBillEntryMap.put(key,info);
					}
					info.setCurProject(splitEntryInfo.getCostAccount().getCurProject());
					info.setCostAccount(splitEntryInfo.getCostAccount());					
					info.setPreGrtSplitAmt(FDCHelper.toBigDecimal(info.getGrtSplitAmt()).add(splitEntryInfo.getGrtSplitAmt()));
					costAccountIds.add(splitEntryInfo.getCostAccount().getId().toString());
				}else{
					SettNoCostSplitEntryInfo splitEntryInfo = (SettNoCostSplitEntryInfo)it.next();
					Boolean hasFinal = Boolean.FALSE;
					if(hasFinalSettle.containsKey(splitEntryInfo.getParent().getContractBill().getId().toString())){
						hasFinal = Boolean.TRUE;
					}
					if(!hasFinal.booleanValue()&&splitEntryInfo.getParent().getSettlementBill().getIsFinalSettle().equals(BooleanEnum.TRUE)){
						hasFinalSettle.put(splitEntryInfo.getParent().getContractBill().getId().toString(),Boolean.TRUE);
					}
					String key = splitEntryInfo.getParent().getContractBill().getId().toString();
					key += "_" + splitEntryInfo.getCurProject().getId().toString();
					key += "_" + splitEntryInfo.getAccountView().getId().toString();
					FDCAdjustBillEntryInfo info = null;
					if(adjustBillEntryMap.containsKey(key)){
						info = (FDCAdjustBillEntryInfo)adjustBillEntryMap.get(key);
					}else{
						info = getNewAdjustBillEntryInfo();
						adjustBillEntryMap.put(key,info);
					}
					info.setCurProject(splitEntryInfo.getCurProject());
					info.setAccountView(splitEntryInfo.getAccountView());					
					info.setPreGrtSplitAmt(FDCHelper.toBigDecimal(info.getGrtSplitAmt()).add(splitEntryInfo.getGrtSplitAmt()));
				}
			}
		}
	}

	/**
	 * 5.�����ֽ����޽��ֽ�����Ŀ
	 * 
	 * ���ܶ�ν��㣬��Ҫgroup by
	 * 
	 * @param ctx
	 * @param idList
	 * @param adjustBillEntryMap
	 * @throws BOSException
	 */
	private void setSettleGrtSplitAmt(Context ctx, Map idList, Map adjustBillEntryMap,Map hasFinalSettle,boolean isCostSplit,Set costAccountIds) throws BOSException {
		if(idList==null||idList.size()==0){
			return ;
		}
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("parent.contractBill.id",idList.keySet(),CompareType.INCLUDE));
		filterInfo.getFilterItems().add(new FilterItemInfo("parent.isInvalid",Boolean.FALSE));
		filterInfo.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		viewInfo.setFilter(filterInfo);
		viewInfo.getSelector().add("id");
		viewInfo.getSelector().add("parent.settlementBill.id");
		viewInfo.getSelector().add("parent.settlementBill.isFinalSettle");
		viewInfo.getSelector().add("parent.contractBill.id");
		viewInfo.getSelector().add("parent.curProject.id");
		viewInfo.getSelector().add("parent.id");
		viewInfo.getSelector().add("amount");
		viewInfo.getSelector().add("grtSplitAmt");
		IObjectCollection coll = null;
		if(isCostSplit){
			viewInfo.getSelector().add("costAccount.id");
			viewInfo.getSelector().add("costAccount.longnumber");
			viewInfo.getSelector().add("costAccount.curProject.id");
			viewInfo.getSelector().add("costAccount.curProject.longnumber");
			coll = SettlementCostSplitEntryFactory.getLocalInstance(ctx).getSettlementCostSplitEntryCollection(viewInfo);
		}
		else{
			viewInfo.getSelector().add("accountView.id");
			viewInfo.getSelector().add("curProject.id");
			coll = SettNoCostSplitEntryFactory.getLocalInstance(ctx).getSettNoCostSplitEntryCollection(viewInfo);
		}
		
		
		if(coll!=null&&coll.size()>0){
			for(Iterator it=coll.iterator();it.hasNext();){
				if(isCostSplit){
					SettlementCostSplitEntryInfo splitEntryInfo = (SettlementCostSplitEntryInfo)it.next();
					Boolean hasFinal = Boolean.FALSE;
					/**
					 * ���Ƿ������ս���ı�ʾ����¼��MAP�У��Ա�����
					 */
					if(hasFinalSettle.containsKey(splitEntryInfo.getParent().getContractBill().getId().toString())){
						hasFinal = Boolean.TRUE;
					}
					if(!hasFinal.booleanValue()&&splitEntryInfo.getParent().getSettlementBill().getIsFinalSettle().equals(BooleanEnum.TRUE)){
						hasFinalSettle.put(splitEntryInfo.getParent().getContractBill().getId().toString(),Boolean.TRUE);
					}
					String key = splitEntryInfo.getParent().getContractBill().getId().toString();
					
					key += "_" + splitEntryInfo.getCostAccount().getCurProject().getId().toString();
					
					key += "_" + splitEntryInfo.getCostAccount().getId().toString();
					FDCAdjustBillEntryInfo info = null;
					if(adjustBillEntryMap.containsKey(key)){
						info = (FDCAdjustBillEntryInfo)adjustBillEntryMap.get(key);
					}else{
						info = getNewAdjustBillEntryInfo();
						adjustBillEntryMap.put(key,info);
					}
					info.setCurProject(splitEntryInfo.getCostAccount().getCurProject());
					info.setCostAccount(splitEntryInfo.getCostAccount());
					info.setSettleSplitAmt(FDCHelper.toBigDecimal(info.getSettleSplitAmt()).add(splitEntryInfo.getAmount()));
					info.setGrtSplitAmt(FDCHelper.toBigDecimal(info.getGrtSplitAmt()).add(splitEntryInfo.getGrtSplitAmt()));
					costAccountIds.add(splitEntryInfo.getCostAccount().getId().toString());
				}else{
					SettNoCostSplitEntryInfo splitEntryInfo = (SettNoCostSplitEntryInfo)it.next();
					Boolean hasFinal = Boolean.FALSE;
					if(hasFinalSettle.containsKey(splitEntryInfo.getParent().getContractBill().getId().toString())){
						hasFinal = Boolean.TRUE;
					}
					if(!hasFinal.booleanValue()&&splitEntryInfo.getParent().getSettlementBill().getIsFinalSettle().equals(BooleanEnum.TRUE)){
						hasFinalSettle.put(splitEntryInfo.getParent().getContractBill().getId().toString(),Boolean.TRUE);
					}
					String key = splitEntryInfo.getParent().getContractBill().getId().toString();
					key += "_" + splitEntryInfo.getCurProject().getId().toString();
					key += "_" + splitEntryInfo.getAccountView().getId().toString();
					FDCAdjustBillEntryInfo info = null;
					if(adjustBillEntryMap.containsKey(key)){
						info = (FDCAdjustBillEntryInfo)adjustBillEntryMap.get(key);
					}else{
						info = getNewAdjustBillEntryInfo();
						adjustBillEntryMap.put(key,info);
					}
					info.setCurProject(splitEntryInfo.getCurProject());
					info.setAccountView(splitEntryInfo.getAccountView());
					info.setSettleSplitAmt(FDCHelper.toBigDecimal(info.getSettleSplitAmt()).add(splitEntryInfo.getAmount()));
					info.setGrtSplitAmt(FDCHelper.toBigDecimal(info.getGrtSplitAmt()).add(splitEntryInfo.getGrtSplitAmt()));
				}
			}
		}
	}

	/**
	 * 
	 * contractId_costaccountid=amount
	 * 4.�����ֽ��,ͬ��
		
	 * @param ctx
	 * @param idList
	 * @param adjustBillEntryMap
	 * @throws BOSException
	 */
	private void setAdjustChangeSplitAmt(Context ctx, Map idList, Map adjustBillEntryMap , boolean isCostSplit,Set costAccountIds) throws BOSException {
		if(idList==null||idList.size()==0){
			return;
		}
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("parent.contractBill.id",idList.keySet(),CompareType.INCLUDE));
		filterInfo.getFilterItems().add(new FilterItemInfo("parent.isInvalid",Boolean.FALSE));
		filterInfo.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		viewInfo.setFilter(filterInfo);
		viewInfo.getSelector().add("id");
		viewInfo.getSelector().add("parent.contractBill.id");
		viewInfo.getSelector().add("parent.curProject.id");
		viewInfo.getSelector().add("parent.id");
		viewInfo.getSelector().add("amount");
		IObjectCollection coll = null;
		if(isCostSplit)
		{
			viewInfo.getSelector().add("costAccount.id");
			viewInfo.getSelector().add("costAccount.longnumber");
			viewInfo.getSelector().add("costAccount.curProject.id");
			viewInfo.getSelector().add("costAccount.curProject.longnumber");
			coll = ConChangeSplitEntryFactory.getLocalInstance(ctx).getConChangeSplitEntryCollection(viewInfo);
		}
		else
		{
			viewInfo.getSelector().add("accountView.id");
			viewInfo.getSelector().add("curProject.id");
			coll = ConChangeNoCostSplitEntryFactory.getLocalInstance(ctx).getConChangeNoCostSplitEntryCollection(viewInfo);
		}
		
		if(coll!=null&&coll.size()>0){
			for(Iterator it=coll.iterator();it.hasNext();){
				if(isCostSplit){
					ConChangeSplitEntryInfo conSplitEntryinfo = (ConChangeSplitEntryInfo)it.next();
					String key = conSplitEntryinfo.getParent().getContractBill().getId().toString();
					key += "_" + conSplitEntryinfo.getCostAccount().getCurProject().getId().toString();
					key += "_" + conSplitEntryinfo.getCostAccount().getId().toString();
					FDCAdjustBillEntryInfo info = null;
					if(adjustBillEntryMap.containsKey(key)){
						info = (FDCAdjustBillEntryInfo)adjustBillEntryMap.get(key);
					}else{
						info = getNewAdjustBillEntryInfo();
						adjustBillEntryMap.put(key,info);
					}
					info.setCurProject(conSplitEntryinfo.getCostAccount().getCurProject());
					info.setChangeSplitAmt(FDCHelper.add(info.getChangeSplitAmt(), conSplitEntryinfo.getAmount()));
					info.setCostAccount(conSplitEntryinfo.getCostAccount());
					costAccountIds.add(conSplitEntryinfo.getCostAccount().getId().toString());
				}else{
					ConChangeNoCostSplitEntryInfo conSplitEntryinfo = (ConChangeNoCostSplitEntryInfo)it.next();
					String key = conSplitEntryinfo.getParent().getContractBill().getId().toString();
					key += "_" + conSplitEntryinfo.getCurProject().getId().toString();
					key += "_" + conSplitEntryinfo.getAccountView().getId().toString();
					FDCAdjustBillEntryInfo info = null;
					if(adjustBillEntryMap.containsKey(key)){
						info = (FDCAdjustBillEntryInfo)adjustBillEntryMap.get(key);
					}else{
						info = getNewAdjustBillEntryInfo();
						adjustBillEntryMap.put(key,info);
					}
					info.setCurProject(conSplitEntryinfo.getCurProject());
					info.setChangeSplitAmt(FDCHelper.add(info.getChangeSplitAmt(), conSplitEntryinfo.getAmount()));
					info.setAccountView(conSplitEntryinfo.getAccountView());
				}
				
			}
		}
	}

	/**
	 * **
	 * 
	 * ��ʼ����ؽ�� 1.����ǰ�����ɱ�������ǰ���������� ,����ǰ���޽���
	 * select sum(amount),sum(payedAmt),sum(splitentry.fqualityAmount) from
	 * paymentsplit split,paymentsplitentry entry where
	 * paymentsplit.paymentbill.contractid in(ids) paymentbill.vouchered=true
	 * paymentsplit.isBeforeAdjust = true group by
	 * contractid,paymentbill.curprojectid,entry.costaccountid
	 * contractid_curprojectid_costaccountid=amount,payedamt
	 * 
	 * 
	 * @param idList
	 * @param adjustBillEntryMap
	 * @param builder
	 * @throws BOSException
	 */
	private void setAdjustPreCostAmtPaidAmt(Map idList, Map adjustBillEntryMap, FDCSQLBuilder builder,Set costAccountIds,VoucherAdjustReasonEnum voucherAdjustReasonEnum) throws BOSException {
		if(idList==null||idList.size()==0){
			return;
		}
//		builder.appendSql("select pay.fcontractbillid  ,costacc.fcurproject as fcurprojectid,splitentry.fcostaccountid,costacc.flongnumber as costacclongnumber,curproj.flongnumber as curprojlongnumber,sum(splitentry.famount) as amount,sum(splitentry.fpayedamt) as payedAmt,sum(splitentry.fqualityAmount) as qualityAmt from ");
		/***
		 * ��Ҫ�������ı������
		 * ����˷�Ʊ��ֵĽ��  by sxhong 2009-07-23 14:12:24
		 */
		builder.appendSql("select isnull(paysplit.fcontractbillid,paysplit.fconwithouttextid) as fcontractbillid  ,costacc.fcurproject as fcurprojectid,splitentry.fcostaccountid,costacc.flongnumber as costacclongnumber,curproj.flongnumber as curprojlongnumber,splitentry.fproductid,splitentry.famount as amount,splitentry.fpayedamt as payedAmt,splitentry.fqualityAmount as qualityAmt ,splitentry.finvoiceAmt as invoiceAmt ");
		builder.appendSql(" from  t_fnc_paymentsplitentry splitentry ");
		builder.appendSql(" inner join t_fnc_paymentsplit paysplit on splitentry.fparentid=paysplit.fid ");
		builder.appendSql(" inner join t_fdc_costaccount costacc on costacc.fid=splitentry.fcostaccountid ");
		builder.appendSql(" inner join t_fdc_curproject curproj on curproj.fid=costacc.fcurproject ");
		builder.appendSql(" where ");
		builder.appendSql("(");
		builder.appendParam("paysplit.fcontractbillid",idList.keySet().toArray());	
		builder.appendSql(" or ");
		builder.appendParam("paysplit.fconwithouttextid", idList.keySet().toArray());
		builder.appendSql(")");
		builder.appendSql(" and splitentry.fcostaccountid is not null ");
		builder.appendSql(" and splitentry.fisleaf=1 ");
		if(VoucherAdjustReasonEnum.INVALID.equals(voucherAdjustReasonEnum))
			builder.appendSql(" and paysplit.fisbeforeadjust = 1");
		else{
			builder.appendSql(" and paysplit.fisinvalid=0");
		}

//		builder.appendSql(" group by pay.fcontractbillid,costacc.fcurproject,splitentry.fcostaccountid,costacc.flongnumber,curproj.flongnumber");
		
		IRowSet rs = builder.executeQuery();
		if(rs!=null&&rs.size()>0){
			setAdjustEntryCostAmtPayedAmt(adjustBillEntryMap, rs , true , true ,costAccountIds,voucherAdjustReasonEnum);
		}
		builder.clear();
	}

	/**
	 * 2.�����ɱ����,���������� ���պ�ͬȡ���е��Ѿ�����ƾ֤�ĸ����ֵĵ�ĳһ��Ŀ���ۼƹ������
	 * select sum(amount),sum(payedAmt) from paymentsplit split,paymentsplitentry entry
	 * where paymentsplit.paymentbill.contractid in(ids)
	 * paymentbill.vouchered=true
	 * paymentsplit.state <> FDCBillStateEnum.INVALID_VALUE
	 * group by contractid,paymentbill.curprojectid,entry.costaccountid
	 * contractid_curprojectid_costaccountid=amount,payedamt
		   
	 * @param idList
	 * @param adjustBillEntryMap
	 * @param builder
	 * @throws BOSException
	 */
	private void setAdjustCostAmtPayedAmt(Map idList, Map adjustBillEntryMap, FDCSQLBuilder builder,Set costAccountIds,VoucherAdjustReasonEnum voucherAdjustReasonEnum) throws BOSException {
		if(idList==null||idList.size()==0){
			return;
		}
		
		IRowSet rs;
//		builder.appendSql("select pay.fcontractbillid  ,costacc.fcurproject as fcurprojectid,splitentry.fcostaccountid,costacc.flongnumber as costacclongnumber,curproj.flongnumber as curprojlongnumber,sum(splitentry.famount) as amount,sum(splitentry.fpayedamt) as payedAmt,sum(splitentry.fqualityAmount) as qualityAmt from "); 
		builder.appendSql("select isnull(paysplit.fcontractbillid,paysplit.fconwithouttextid) as fcontractbillid  ,costacc.fcurproject as fcurprojectid,splitentry.fcostaccountid,costacc.flongnumber as costacclongnumber,curproj.flongnumber as curprojlongnumber,splitentry.fproductid,splitentry.famount as amount,splitentry.fpayedamt as payedAmt,splitentry.fqualityAmount as qualityAmt,splitentry.finvoiceAmt as invoiceAmt from ");
		builder.appendSql(" t_fnc_paymentsplitentry splitentry inner join t_fnc_paymentsplit paysplit on splitentry.fparentid=paysplit.fid");
		builder.appendSql(" inner join t_fdc_costaccount costacc on costacc.fid=splitentry.fcostaccountid ");
		builder.appendSql(" inner join t_fdc_curproject curproj on curproj.fid=costacc.fcurproject ");
		builder.appendSql(" where ");
		
		if(!VoucherAdjustReasonEnum.INVALID.equals(voucherAdjustReasonEnum)){
			builder.appendSql("(");
			builder.appendParam("paysplit.fcontractbillid",idList.keySet().toArray());	
			builder.appendSql(" or ");
			builder.appendParam("paysplit.fconwithouttextid", idList.keySet().toArray());
			builder.appendSql(")");
		}else{
/*			builder.appendSql(" paysplit.fid in (");
			builder.appendSql("   select pay1.fid from "); 
			builder.appendSql("   t_fnc_paymentsplit paysplit1 ");
			builder.appendSql("   inner join t_cas_paymentbill pay1 on paysplit1.fpaymentbillid=pay1.fid where ");
			builder.appendParam("pay1.fcontractbillid",idList.keySet().toArray());
			builder.appendSql("   and paysplit1.fisbeforeadjust = 1");		
			builder.appendSql(" )");*/
			builder.appendSql(" exists (select 1 from t_fnc_paymentsplit where FContractBillId=t_fnc_paymentsplit.fcontractbillid  and fisbeforeadjust = 1 and ((fpaymentbillid is not null and fpaymentbillid=t_fnc_paymentsplit.fpaymentbillid) or (fworkloadbillid is not null and fworkloadbillid=t_fnc_paymentsplit.fworkloadbillid))) and ");
			builder.appendSql("(");
			builder.appendParam("paysplit.fcontractbillid",idList.keySet().toArray());	
			builder.appendSql(" or ");
			builder.appendParam("paysplit.fconwithouttextid", idList.keySet().toArray());
			builder.appendSql(")");
		}
		
		builder.appendSql(" and splitentry.fcostaccountid is not null ");
		builder.appendSql(" and splitentry.fisleaf=1 ");
		builder.appendSql(" and paysplit.fisinvalid = 0 ");
//		builder.appendSql(" group by pay.fcontractbillid,costacc.fcurproject,splitentry.fcostaccountid,costacc.flongnumber,curproj.flongnumber");
		
		
		rs = builder.executeQuery();
		if(rs!=null&&rs.size()>0){
			setAdjustEntryCostAmtPayedAmt(adjustBillEntryMap, rs, false , true ,costAccountIds,voucherAdjustReasonEnum);
		}
		builder.clear();
	}

	/**
	 *TODO ��ʵ���ȡ����û������ģ��ڸ����ֵ�ȡ�������Ѿ�����ȡ��
	 *modify by sxhong 2009-08-01 16:41:30
	 * 3.��ͬ��ֽ�
	 * select * from contractcostsplit,...splitentry
	 * where contractsplit.state<>FDCBillStateEnum.INVALID_VALUE
	 * contractid in (ids)
		
	 * @param ctx
	 * @param idList
	 * @param adjustBillEntryMap
	 * @throws BOSException
	 */
	private void setAdjustContractSplitAmt(Context ctx, Map idList, Map adjustBillEntryMap , boolean isCostSplit,Set costAccountIds) throws BOSException {
		if(idList==null||idList.size()==0){
			return;
		}
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("parent.contractBill.id",idList.keySet(),CompareType.INCLUDE));
		filterInfo.getFilterItems().add(new FilterItemInfo("parent.isInvalid",Boolean.FALSE));
		filterInfo.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		viewInfo.setFilter(filterInfo);
		viewInfo.getSelector().add("id");
		viewInfo.getSelector().add("parent.contractBill.id");
		viewInfo.getSelector().add("parent.curProject.id");
		viewInfo.getSelector().add("parent.id");
		if(isCostSplit)
		{
			viewInfo.getSelector().add("costAccount.id");
			viewInfo.getSelector().add("costAccount.longnumber");
			viewInfo.getSelector().add("costAccount.curProject.id");
			viewInfo.getSelector().add("costAccount.curProject.longnumber");
		}
		else
		{	
			viewInfo.getSelector().add("accountView.id");
			viewInfo.getSelector().add("curProject.id");
		}
		viewInfo.getSelector().add("amount");
		IObjectCollection coll = null;
		if(isCostSplit)
			coll = ContractCostSplitEntryFactory.getLocalInstance(ctx).getContractCostSplitEntryCollection(viewInfo);
		else
			coll = ConNoCostSplitEntryFactory.getLocalInstance(ctx).getConNoCostSplitEntryCollection(viewInfo);
		if(coll!=null&&coll.size()>0){
			for(Iterator it=coll.iterator();it.hasNext();){
				if(isCostSplit){
					ContractCostSplitEntryInfo conSplitEntryinfo = (ContractCostSplitEntryInfo)it.next();
					String key = conSplitEntryinfo.getParent().getContractBill().getId().toString();
					key += "_" + conSplitEntryinfo.getCostAccount().getCurProject().getId().toString();
					key += "_" + conSplitEntryinfo.getCostAccount().getId().toString();
					FDCAdjustBillEntryInfo info = null;
					if(adjustBillEntryMap.containsKey(key)){
						info = (FDCAdjustBillEntryInfo)adjustBillEntryMap.get(key);
					}else{
						info = getNewAdjustBillEntryInfo();
						adjustBillEntryMap.put(key,info);
					}
					info.setCurProject(conSplitEntryinfo.getCostAccount().getCurProject());
					//�������ظ��Ŀ�Ŀ��Ҫ�ۼ�  modify by sxhong 2009-08-01 16:39:59
					info.setContractSplitAmt(FDCHelper.add(info.getContractSplitAmt(),conSplitEntryinfo.getAmount()));
					info.setCostAccount(conSplitEntryinfo.getCostAccount());
					costAccountIds.add(conSplitEntryinfo.getCostAccount().getId().toString());
				}else{
					ConNoCostSplitEntryInfo conSplitEntryinfo = (ConNoCostSplitEntryInfo)it.next();
					String key = conSplitEntryinfo.getParent().getContractBill().getId().toString();
					key += "_" + conSplitEntryinfo.getCurProject().getId().toString();
					key += "_" + conSplitEntryinfo.getAccountView().getId().toString();
					FDCAdjustBillEntryInfo info = null;
					if(adjustBillEntryMap.containsKey(key)){
						info = (FDCAdjustBillEntryInfo)adjustBillEntryMap.get(key);
					}else{
						info = getNewAdjustBillEntryInfo();
						adjustBillEntryMap.put(key,info);
					}
					info.setCurProject(conSplitEntryinfo.getCurProject());
					info.setContractSplitAmt(FDCHelper.toBigDecimal(info.getContractSplitAmt()).add(FDCHelper.toBigDecimal(conSplitEntryinfo.getAmount())));
					info.setAccountView(conSplitEntryinfo.getAccountView());
				}
				
			}
		}
	}
	

	/**
	 * ���÷�¼�Ĺ����ɱ�������������
	 * 
	 * ����˶Է�Ʊ�Ĵ���  by sxhong 2009-07-23 14:12:46
	 * @param adjustBillEntryMap
	 * @param rs
	 * @throws BOSException 
	 */
	private void setAdjustEntryCostAmtPayedAmt(Map adjustBillEntryMap, IRowSet rs ,boolean isBeforeAdjust,boolean isCostSplit,Set costAccountIds,VoucherAdjustReasonEnum voucherAdjustReasonEnum) throws BOSException {
		try {
			while(rs.next()){
				String key = rs.getString("fcontractbillid");
				key += "_" + rs.getString("fcurprojectid");
				if(isCostSplit)
					key += "_" + rs.getString("fcostaccountid");
				else
					key += "_" + rs.getString("faccountviewid");
				FDCAdjustBillEntryInfo info = null;
				/***
				 * �������δ��ȡ����ȡ,δ��ȡ����ʧ
				 * ����ǰ�͵�����Ŀ�Ŀ��һ�µ�
				 * keyֵҲ��һ�µ�,�ͻ���ֵ���ǰ�͵�������ȫһ�µ����
				 * ��ô�ͻ᲻���ɵ�����
				 * ����������������,�ڵ���ǰ��key�м���before������
				 */
				if(!VoucherAdjustReasonEnum.INVALID.equals(voucherAdjustReasonEnum)){
					if(isBeforeAdjust){
						key += "_" + "before";
					}
				}
				if(adjustBillEntryMap.containsKey(key)){
					info = (FDCAdjustBillEntryInfo)adjustBillEntryMap.get(key);
				}else{
					info = getNewAdjustBillEntryInfo();
					adjustBillEntryMap.put(key,info);
				}
				/**
				 * ���Ӳ�ֵ���Ʒ��ʾ
				 */
				String productId = rs.getString("fproductid");
				if(productId != null){
					//���жϸò�Ʒ�Ƿ���ڣ���������������
					boolean isExist=false;
					for(int i=0;i<info.getProductEntrys().size();i++){
						FDCAdjustProductEntryInfo entry=info.getProductEntrys().get(i);
						if(entry!=null&&entry.getProductType()!=null&&entry.getProductType().getId().toString().equals(productId)){
							isExist=true;
						}
					}
					if(!isExist){
						FDCAdjustProductEntryInfo productEntryInfo = new FDCAdjustProductEntryInfo();
						ProductTypeInfo productTypeInfo = new ProductTypeInfo();
						productTypeInfo.setId(BOSUuid.read(productId));
						productEntryInfo.setProductType(productTypeInfo);
						productEntryInfo.setAdjustBillEntry(info);
						info.getProductEntrys().add(productEntryInfo);					
					}
				}
				
				CurProjectInfo curProject = new CurProjectInfo();
				curProject.setId(BOSUuid.read(rs.getString("fcurprojectid")));
				info.setCurProject(curProject);
				BigDecimal invoiceAmt=FDCHelper.ZERO;//��Ʊ���
				if(isCostSplit){
					CostAccountInfo accountInfo = new CostAccountInfo();
					accountInfo.setId(BOSUuid.read(rs.getString("fcostaccountid")));
					accountInfo.setLongNumber(rs.getString("costacclongnumber"));
					curProject.setLongNumber(rs.getString("curprojlongnumber"));
					accountInfo.setCurProject(curProject);
					info.setCostAccount(accountInfo);
					costAccountIds.add(rs.getString("fcostaccountid"));
					//invoice handle
					invoiceAmt=FDCHelper.toBigDecimal(rs.getBigDecimal("invoiceAmt"));
				}else{
					AccountViewInfo accountInfo = new AccountViewInfo();
					accountInfo.setId(BOSUuid.read(rs.getString("faccountviewid")));
					
					info.setAccountView(accountInfo);
				}
				if(isBeforeAdjust){
					info.setPreHappenCostAmt(FDCHelper.toBigDecimal(rs.getBigDecimal("amount")).add(info.getPreHappenCostAmt()));
					info.setPrePayedAmt(FDCHelper.toBigDecimal(rs.getBigDecimal("payedAmt")).add(info.getPrePayedAmt()));
					info.setPreQualityAmt(FDCHelper.toBigDecimal(rs.getBigDecimal("qualityAmt")).add(info.getPreQualityAmt()));
					//invoice handle
					info.setPreInvoiceAmt(FDCHelper.add(info.getPreInvoiceAmt(), invoiceAmt));
				}else{
					info.setHappenCostAmt(FDCHelper.toBigDecimal(rs.getBigDecimal("amount")).add(info.getHappenCostAmt()));
					info.setPayedAmt(FDCHelper.toBigDecimal(rs.getBigDecimal("payedAmt")).add(info.getPayedAmt()));
					info.setQualityAmt(FDCHelper.toBigDecimal(rs.getBigDecimal("qualityAmt")).add(info.getQualityAmt()));
					info.setInvoiceAmt(FDCHelper.add(info.getInvoiceAmt(), invoiceAmt));
				}
				if(!VoucherAdjustReasonEnum.INVALID.equals(voucherAdjustReasonEnum)){
					info.put("isBeforeAdjust",Boolean.valueOf(isBeforeAdjust));
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
	}

	/**
	 * @return
	 */
	private FDCAdjustBillEntryInfo getNewAdjustBillEntryInfo() {
		FDCAdjustBillEntryInfo info;
		info = new FDCAdjustBillEntryInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		info.setCostDifAmt(FDCHelper.ZERO);
		info.setCostAmt(FDCHelper.ZERO);
		info.setHappenCostAmt(FDCHelper.ZERO);
		info.setPreHappenCostAmt(FDCHelper.ZERO);
		info.setPayedDifAmt(FDCHelper.ZERO);
		info.setPayedAmt(FDCHelper.ZERO);
		info.setPrePayedAmt(FDCHelper.ZERO);
		info.setContractSplitAmt(FDCHelper.ZERO);
		info.setChangeSplitAmt(FDCHelper.ZERO);
		info.setGrtSplitAmt(FDCHelper.ZERO);
		info.setPreGrtSplitAmt(FDCHelper.ZERO);
		info.setSettleSplitAmt(FDCHelper.ZERO);
		info.setPreQualityAmt(FDCHelper.ZERO);
		info.setQualityAmt(FDCHelper.ZERO);
		info.setQualityDifAmt(FDCHelper.ZERO);
		return info;
	}

	protected void _traceContracts(Context ctx, List idList)
			throws BOSException, EASBizException {
		int size = idList.size();
		
		boolean isAdjust = isAdjustModel(ctx);
		if(isAdjust){
			_traceAdjustContracts(ctx, idList,false,VoucherAdjustReasonEnum.INVALID);
		}
		else{
			/***
			 * �����ɵĺ��ģʽ
			 * ������һ��ѭ�������滹�в�ֻ����ѭ������Ҫ�Ż�
			 */
			for (int i = 0; i < size; i++) {
				String id = idList.get(i).toString();
				_traceSplitVoucher(ctx, id);
			}
		}
		
	}

	private FDCNoCostSplitBillEntryCollection importConNoCostSplit(Context ctx,
			String contractID) throws BOSException, EASBizException {
		return loadNoCostSplit(CostSplitBillTypeEnum.CONTRACTSPLIT,
				getNoCostSplitEntryCollection(ctx,
						CostSplitBillTypeEnum.CONTRACTSPLIT, null, contractID));
	}

	protected FDCNoCostSplitBillEntryCollection getNoCostSplitEntryCollection(
			Context ctx, CostSplitBillTypeEnum splitBillType,
			BOSUuid costBillUuId, String contractID) throws BOSException,
			EASBizException {

		String costBillId = null;
		if (costBillUuId == null) {
			costBillUuId = BOSUuid.read(contractID);
		}
		costBillId = costBillUuId.toString();

		if (costBillId == null) {
			return new FDCNoCostSplitBillEntryCollection();
		}
		AbstractObjectCollection coll = null;
		AbstractBillEntryBaseInfo item = null;
		EntityViewInfo view = new EntityViewInfo();
		String filterField = null;
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sic = view.getSelector();
		setSelectorsEntry(ctx, sic, true);
		view.getSorter().add(new SorterItemInfo("seq"));
		if (splitBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
			filterField = "parent.contractBill.id";
			filter.getFilterItems().add(
					new FilterItemInfo(filterField, costBillId));
			filter.getFilterItems().add(
					new FilterItemInfo("parent.state",
							FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("accountView.*");
			view.getSelector().add("product.*");
			view.getSelector().add("curProject.*");
			view.getSelector().add("apportionType.*");
			coll = ConNoCostSplitEntryFactory.getLocalInstance(ctx)
					.getConNoCostSplitEntryCollection(view);

		} else if (splitBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {
			filterField = "parent.contractChange.id";
			filter.getFilterItems().add(
					new FilterItemInfo(filterField, costBillId));
			filter.getFilterItems().add(
					new FilterItemInfo("parent.state",
							FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("accountView.*");
			view.getSelector().add("product.*");
			view.getSelector().add("curProject.*");
			view.getSelector().add("apportionType.*");
			coll = ConChangeNoCostSplitEntryFactory.getLocalInstance(ctx)
					.getFDCNoCostSplitBillEntryCollection(view);

		} else if (splitBillType.equals(CostSplitBillTypeEnum.SETTLEMENTSPLIT)) {
			filterField = "parent.settlementBill.id";
			filter.getFilterItems().add(
					new FilterItemInfo(filterField, costBillId));
			filter.getFilterItems().add(
					new FilterItemInfo("parent.state",
							FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("accountView.*");
			view.getSelector().add("product.*");
			view.getSelector().add("curProject.*");
			view.getSelector().add("apportionType.*");
			coll = SettNoCostSplitEntryFactory.getLocalInstance(ctx)
					.getFDCNoCostSplitBillEntryCollection(view);

		} else {
			// ������ֵ�,�Ժ��ṩ֧��
			return new FDCNoCostSplitBillEntryCollection();
		}

		FDCNoCostSplitBillEntryCollection entrys = new FDCNoCostSplitBillEntryCollection();
		FDCNoCostSplitBillEntryInfo entry = null;

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			item = (AbstractBillEntryBaseInfo) iter.next();
			item.setId(null);
			entry = new FDCNoCostSplitBillEntryInfo();
			entry.putAll(item);
			entry.setCostBillId(costBillUuId);
			entrys.add(entry);
		}
		return entrys;
	}

	protected FDCNoCostSplitBillEntryCollection loadNoCostSplit(
			CostSplitBillTypeEnum costBillType,
			FDCNoCostSplitBillEntryCollection entrys) {
		IObjectValue entry = null;
		if (costBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
			// contractAmt
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entry = (IObjectValue) iter.next();
				entry.put("contractAmt", entry.get("amount"));
				entry.put("shouldPayAmt", entry.get("amount"));
				// entry.put("amount", FDCHelper.ZERO);
				entry.put("amount", null);
			}
		} else if (costBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {
			// changeAmt
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entry = (IObjectValue) iter.next();
				entry.put("changeAmt", entry.get("amount"));
				entry.put("shouldPayAmt", entry.get("amount"));
				// entry.put("amount", FDCHelper.ZERO);
				entry.put("amount", null);
			}
		}
		return loadMoreNoCostSplit(entrys);
	}

	protected FDCNoCostSplitBillEntryCollection loadMoreNoCostSplit(
			FDCNoCostSplitBillEntryCollection entrys) {

		FDCNoCostSplitBillEntryInfo entry = null;
		BigDecimal amount = FDCHelper.ZERO;

		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			entry = (FDCNoCostSplitBillEntryInfo) iter.next();

			// ֱ�ӷ���
			entry.setDirectAmount(amount);
			entry.setDirectAmountTotal(amount);

			// �������֣��������ý��
			// if (splitBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT))
			// {
			// entry.setAmount(amount);
			// }

			// ������ jelon 12/28/2006
			if (entry.getLevel() == 0) {
				groupIndex++;
			}
			entry.setIndex(groupIndex);

			// addEntry(entry);

		}
		return entrys;
	}

	private FDCNoCostSplitBillEntryCollection importChangeNoCostSplit(
			Context ctx, String contractID) throws BOSException,
			EASBizException {
		// if(true) return;
		// loadCostSplit(getCostSplitEntryCollection(CostSplitBillTypeEnum.CONTRACTSPLIT));
		FDCNoCostSplitBillEntryCollection totalChange = new FDCNoCostSplitBillEntryCollection();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("contractChange.contractBill.id",
								contractID));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		view.setFilter(filter);
		SelectorItemCollection sic = view.getSelector();
		sic.add(new SelectorItemInfo("contractChange.contractBill.id"));

		ConChangeNoCostSplitCollection coll = null;

		coll = ConChangeNoCostSplitFactory.getLocalInstance(ctx)
				.getConChangeNoCostSplitCollection(view);

		if (coll == null || coll.size() == 0) {
			return null;
		}

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ConChangeNoCostSplitInfo item = (ConChangeNoCostSplitInfo) iter
					.next();
			totalChange.addCollection(loadNoCostSplit(
					CostSplitBillTypeEnum.CNTRCHANGESPLIT,
					getNoCostSplitEntryCollection(ctx,
							CostSplitBillTypeEnum.CNTRCHANGESPLIT, item
									.getContractChange().getId(), contractID)));
		}
		return totalChange;
	}

	private void setNoCostAdd(Context ctx, PaymentNoCostSplitInfo objectValue,
			String contractID,boolean isFinancial,boolean isAdjust) throws BOSException, EASBizException {
		ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(
				ctx).getContractBillInfo(
				new ObjectUuidPK(BOSUuid.read(contractID)));
		FilterInfo filterSett = new FilterInfo();
		filterSett.getFilterItems().add(
				new FilterItemInfo("settlementBill.contractBill.id",
						objectValue.getPaymentBill().getContractBillId()));
		filterSett.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		boolean hasSettleSplit = SettNoCostSplitFactory.getLocalInstance(ctx)
				.exists(filterSett);
		if (!contractBill.isHasSettled() || !hasSettleSplit) {
			if (objectValue.getEntrys().size() > 0) {
				for (int i = 0, size = objectValue.getEntrys().size(); i < size; i++) {
					PaymentNoCostSplitEntryInfo entry = objectValue.getEntrys()
							.get(i);
					if (entry.getLevel() > -1) {
						BigDecimal conAmt = FDCHelper.toBigDecimal(entry
								.getContractAmt());
						BigDecimal chaAmt = FDCHelper.toBigDecimal(entry
								.getChangeAmt());
						entry.setCostAmt(conAmt.add(chaAmt));
						String acc = entry.getAccountView().getId().toString();
						String project = entry.getCurProject().getId()
								.toString();
						String costId = entry.getCostBillId().toString();
						BigDecimal temp = FDCHelper.ZERO;
						BigDecimal tempPayed = FDCHelper.ZERO;
						PaymentNoCostSplitEntryCollection coll = null;
						PaymentNoCostSplitEntryInfo item = null;
						EntityViewInfo view = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();
						filter.getFilterItems().add(
								new FilterItemInfo("accountView", acc));
						filter.getFilterItems().add(
								new FilterItemInfo("curProject", project));
						filter.getFilterItems().add(
								new FilterItemInfo("parent.state",
										FDCBillStateEnum.INVALID_VALUE,
										CompareType.NOTEQUALS));
						filter.getFilterItems().add(
								new FilterItemInfo("costBillId", costId));
						if (entry.getProduct() != null) {
							String product = entry.getProduct().getId()
									.toString();
							filter.getFilterItems().add(
									new FilterItemInfo("product.id", product));
						} else {
							filter.getFilterItems().add(
									new FilterItemInfo("product.id", null));
						}
						view.setFilter(filter);
						view.getSelector().add("amount");
						view.getSelector().add("payedAmt");

						coll = PaymentNoCostSplitEntryFactory.getLocalInstance(
								ctx).getPaymentNoCostSplitEntryCollection(view);
						for (Iterator iter = coll.iterator(); iter.hasNext();) {
							item = (PaymentNoCostSplitEntryInfo) iter.next();
							if (item.getAmount() != null)
								temp = temp.add(item.getAmount());
							if (item.getPayedAmt() != null) {
								tempPayed = tempPayed.add(item.getPayedAmt());
							}
						}
						entry.setSplitedCostAmt(temp);
						entry.setSplitedPayedAmt(tempPayed);
					}
				}
			}
			objectValue.setHasEffected(false);
		} else {
			objectValue.setHasEffected(true);
			
			//�����ҷǵ�����һ�ʽ����Ѳ�ֳɱ����Ϊ0 by hpw 2010-06-08 
//			if(isAdjust){
//				PaymentSplitHelper.handleNoCostAdjustModelSplitedAmt(ctx,objectValue,contractID);
//			}else{
//				setNoCostSettle(ctx, objectValue, contractID);
//			}
			if(isFinancial&&!isAdjust){
				setNoCostSettle(ctx, objectValue, contractID);
			}else{
				PaymentSplitHelper.handleNoCostAdjustModelSplitedAmt(ctx,objectValue,contractID);
			}
		}
	}

	private void setNoCostSettle(Context ctx,
			PaymentNoCostSplitInfo objectValue, String contractID)
			throws BOSException, EASBizException {
		if (objectValue.getEntrys().size() > 0) {
			for (int i = 0, size = objectValue.getEntrys().size(); i < size; i++) {
				PaymentNoCostSplitEntryInfo entry = objectValue.getEntrys()
						.get(i);
				if (entry.getLevel() > -1) {
					String acc = entry.getAccountView().getId().toString();
					String project = entry.getCurProject().getId().toString();
					String costId = entry.getCostBillId().toString();
					BigDecimal temp = FDCHelper.ZERO;
					BigDecimal tempShould = FDCHelper.ZERO;
					BigDecimal temppay = FDCHelper.ZERO;
					BigDecimal tempPayed = FDCHelper.ZERO;
					SettNoCostSplitEntryCollection coll = null;
					SettNoCostSplitEntryInfo item = null;
					PaymentNoCostSplitEntryCollection collpay = null;
					PaymentNoCostSplitEntryInfo itempay = null;
					EntityViewInfo viewPay = new EntityViewInfo();
					FilterInfo filterPay = new FilterInfo();
					filterPay.getFilterItems().add(
							new FilterItemInfo("accountView", acc));
					filterPay.getFilterItems().add(
							new FilterItemInfo("curProject", project));
					filterPay.getFilterItems().add(
							new FilterItemInfo("costBillId", costId));
					filterPay.getFilterItems().add(
							new FilterItemInfo("Parent.isRedVouchered",
									Boolean.TRUE, CompareType.NOTEQUALS));
					filterPay.getFilterItems().add(
							new FilterItemInfo("Parent.state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					if (objectValue.getId() != null) {
						filterPay.getFilterItems().add(
								new FilterItemInfo("Parent.id", objectValue
										.getId().toString(),
										CompareType.NOTEQUALS));
					}
					FilterInfo filterSettPayed = new FilterInfo();
					filterSettPayed.getFilterItems().add(
							new FilterItemInfo("paymentBill.contractBillId",
									contractID));
					filterSettPayed.getFilterItems().add(
							new FilterItemInfo("isProgress", Boolean.TRUE));
					filterSettPayed.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					boolean hasSettlePayed = PaymentNoCostSplitFactory
							.getLocalInstance(ctx).exists(filterSettPayed);
					if (hasSettlePayed) {
						filterPay
								.getFilterItems()
								.add(
										new FilterItemInfo(
												"Parent.paymentBill.fdcPayType.payType.id",
												PaymentTypeInfo.settlementID));
					}
					if (entry.getProduct() != null) {
						String product = entry.getProduct().getId().toString();
						filterPay.getFilterItems().add(
								new FilterItemInfo("product.id", product));
					} else {
						filterPay.getFilterItems().add(
								new FilterItemInfo("product.id", null));
					}
					if (entry.getSplitType() != null) {
						String standard = entry.getSplitType().getValue();
						filterPay.getFilterItems().add(
								new FilterItemInfo("splitType", standard));
					} else {
						filterPay.getFilterItems().add(
								new FilterItemInfo("splitType", null));
					}
					viewPay.getSelector().add("amount");
					viewPay.getSelector().add("payedAmt");
					viewPay.setFilter(filterPay);

					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("accountView", acc));
					filter.getFilterItems().add(
							new FilterItemInfo("curProject", project));
					filter.getFilterItems().add(
							new FilterItemInfo("costBillId", costId));
					filter.getFilterItems().add(
							new FilterItemInfo("parent.state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					if (entry.getProduct() != null) {
						String product = entry.getProduct().getId().toString();
						filter.getFilterItems().add(
								new FilterItemInfo("product.id", product));
					} else {
						filter.getFilterItems().add(
								new FilterItemInfo("product.id", null));
					}
					if (entry.getSplitType() != null) {
						String standard = entry.getSplitType().getValue();
						filter.getFilterItems().add(
								new FilterItemInfo("splitType", standard));
					} else {
						filter.getFilterItems().add(
								new FilterItemInfo("splitType", null));
					}
					view.getSelector().add("amount");
					view.getSelector().add("grtSplitAmt");
					view.setFilter(filter);

					coll = SettNoCostSplitEntryFactory.getLocalInstance(ctx)
							.getSettNoCostSplitEntryCollection(view);
					collpay = PaymentNoCostSplitEntryFactory.getLocalInstance(
							ctx).getPaymentNoCostSplitEntryCollection(viewPay);
					for (Iterator iter = coll.iterator(); iter.hasNext();) {
						item = (SettNoCostSplitEntryInfo) iter.next();
						if (item.getAmount() != null) {
							temp = temp.add(item.getAmount());
							if (item.getGrtSplitAmt() != null) {
								tempShould = tempShould.add(item.getAmount()
										.subtract(item.getGrtSplitAmt()));
							} else {
								tempShould = tempShould.add(item.getAmount());
							}
						}
					}
					for (Iterator iter = collpay.iterator(); iter.hasNext();) {
						itempay = (PaymentNoCostSplitEntryInfo) iter.next();
						if (itempay.getAmount() != null)
							temppay = temppay.add(itempay.getAmount());
						if (itempay.getPayedAmt() != null)
							tempPayed = tempPayed.add(itempay.getPayedAmt());
					}

					entry.setCostAmt(temp);

					entry.setShouldPayAmt(tempShould);
					if (objectValue.getPaymentBill() != null
							&& objectValue.getPaymentBill().getContractBillId() != null) {
						FilterInfo filterPayed = new FilterInfo();
						filterPayed.getFilterItems().add(
								new FilterItemInfo(
										"paymentBill.contractBillId",
										contractID));
						filterPayed.getFilterItems().add(
								new FilterItemInfo("state",
										FDCBillStateEnum.INVALID_VALUE,
										CompareType.NOTEQUALS));
						filterPayed.getFilterItems().add(
								new FilterItemInfo(
										"paymentBill.fdcPayType.payType.id",
										PaymentTypeInfo.settlementID));
						if (!PaymentNoCostSplitFactory.getLocalInstance(ctx)
								.exists(filterPayed)
								&& objectValue.getPaymentBill().getFdcPayType()
										.getPayType().getId().toString()
										.equals(PaymentTypeInfo.settlementID)) {
							entry.setSplitedCostAmt(FDCHelper.ZERO);
							entry.setSplitedPayedAmt(FDCHelper.ZERO);
						} else {
							entry.setSplitedCostAmt(temppay);
							entry.setSplitedPayedAmt(tempPayed);
						}
					}
				}
			}
		}
	}

	private void calNoDirAmt(Context ctx, PaymentNoCostSplitInfo splitInfo) {
		for (int i = 0, size = splitInfo.getEntrys().size(); i < size; i++) {
			PaymentNoCostSplitEntryInfo entry = splitInfo.getEntrys().get(i);
			int curIndex = splitInfo.getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumNoAccount(ctx, i, entry, size, splitInfo);
			}
		}
		BigDecimal total = FDCHelper.ZERO;
		for (int i = 0, size = splitInfo.getEntrys().size(); i < size; i++) {
			PaymentNoCostSplitEntryInfo entry = splitInfo.getEntrys().get(i);
			if (entry.getLevel() == 0 && entry.getAmount() != null) {
				total = total.add(entry.getAmount());
			}
		}
		for (int i = 0, size = splitInfo.getEntrys().size(); i < size; i++) {
			PaymentNoCostSplitEntryInfo entry = splitInfo.getEntrys().get(i);
			if (entry.getLevel() < 0) {
				entry.setAmount(total);
			}
		}
	}

	private void sumNoAccount(Context ctx, int index,
			PaymentNoCostSplitEntryInfo curEntry, int size,
			PaymentNoCostSplitInfo splitInfo) {
		int curLevel = curEntry.getLevel();
		// int prjLevel = curEntry.getCostAccount().getCurProject().getLevel();
		PaymentNoCostSplitEntryInfo entry;
		if ((curEntry.getCurProject().isIsLeaf()) && (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < size; i++) {
					entry = (PaymentNoCostSplitEntryInfo) splitInfo.getEntrys()
							.get(i);
					if (entry.getLevel() == curEntry.getLevel() + 1) {
						// if((entry.getLevel()==curLevel+1)&&(entry.getCostAccount().getCurProject().getLevel()==prjLevel+1)){
						sumNoAccount(ctx, i, entry, size, splitInfo);
						if (entry.getAmount() != null)
							amtTotal = amtTotal.add(entry.getAmount());
					} else if (entry.getLevel() < curLevel + 1) {
						break;
					} else {
						continue;
					}
				}
				curEntry.setAmount(amtTotal);
			} else {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < size; i++) {
					entry = (PaymentNoCostSplitEntryInfo) splitInfo.getEntrys()
							.get(i);
					if (entry.getCurProject().getId().equals(
							curEntry.getCurProject().getId())) {
						if (entry.getLevel() == curLevel + 1) {
							sumNoAccount(ctx, i, entry, size, splitInfo);
							if (entry.getAmount() != null)
								amtTotal = amtTotal.add(entry.getAmount());
						} else if (entry.getLevel() > curLevel + 1) {
							continue;
						} else {
							break;
						}
					} else
						break;
				}
				curEntry.setAmount(amtTotal);
			}
		}
	}

	private void calNoPayedAmt(Context ctx, PaymentNoCostSplitInfo splitInfo) {
		for (int i = 0, size = splitInfo.getEntrys().size(); i < size; i++) {
			PaymentNoCostSplitEntryInfo entry = splitInfo.getEntrys().get(i);
			int curIndex = splitInfo.getEntrys().indexOf(entry);
			if (curIndex == -1) {
				return;
			}
			if (entry.getLevel() == 0) {
				sumNoPayedAmt(ctx, i, entry, size, splitInfo);
				PayNoCostSplitAppHelper.totAmountPayAddlAcct(splitInfo
						.getEntrys(), curIndex);
			}
		}
	}

	private void sumNoPayedAmt(Context ctx, int index,
			PaymentNoCostSplitEntryInfo curEntry, int size,
			PaymentNoCostSplitInfo splitInfo) {
		int curLevel = curEntry.getLevel();
		// int prjLevel = curEntry.getCostAccount().getCurProject().getLevel();
		PaymentNoCostSplitEntryInfo entry;
		if ((curEntry.getCurProject().isIsLeaf()) && (curEntry.isIsLeaf())) {
			return;
		} else {
			if (!curEntry.getCurProject().isIsLeaf()) {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < size; i++) {
					entry = (PaymentNoCostSplitEntryInfo) splitInfo.getEntrys()
							.get(i);
					if (entry.getLevel() == curEntry.getLevel() + 1) {
						// if((entry.getLevel()==curLevel+1)&&(entry.getCostAccount().getCurProject().getLevel()==prjLevel+1)){
						sumNoAccount(ctx, i, entry, size, splitInfo);
						if (entry.getPayedAmt() != null)
							amtTotal = amtTotal.add(entry.getPayedAmt());
					} else if (entry.getLevel() < curLevel + 1) {
						break;
					} else {
						continue;
					}
				}
				curEntry.setPayedAmt(amtTotal);
			} else {
				BigDecimal amtTotal = FDCHelper.ZERO;
				for (int i = index + 1; i < size; i++) {
					entry = (PaymentNoCostSplitEntryInfo) splitInfo.getEntrys()
							.get(i);
					if (entry.getCurProject().getId().equals(
							curEntry.getCurProject().getId())) {
						if (entry.getLevel() == curLevel + 1) {
							sumNoAccount(ctx, i, entry, size, splitInfo);
							if (entry.getPayedAmt() != null)
								amtTotal = amtTotal.add(entry.getPayedAmt());
						} else if (entry.getLevel() > curLevel + 1) {
							continue;
						} else {
							break;
						}
					} else
						break;
				}
				curEntry.setPayedAmt(amtTotal);
			}
		}
	}

	protected void setNoQuaAmt(Context ctx, PaymentNoCostSplitInfo splitInfo)
			throws BOSException, EASBizException {
		int size = splitInfo.getEntrys().size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {

				PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) splitInfo
						.getEntrys().get(i);
				if (entry.getAmount() != null
						&& splitInfo.getQualityAmount() != null) {
					BigDecimal cmpAmt = FDCHelper.ZERO;
//					cmpAmt = (entry.getAmount().multiply(splitInfo.getQualityAmount())).divide(splitInfo.getCompletePrjAmt(), 2,BigDecimal.ROUND_HALF_EVEN);
					cmpAmt =FDCHelper.divide(FDCHelper.multiply(entry.getAmount(), splitInfo.getQualityAmount()), splitInfo.getCompletePrjAmt(), 2,BigDecimal.ROUND_HALF_EVEN);
					entry.setQualityAmount(cmpAmt);

				}
			}
			if (splitInfo.getAmount() != null
					&& splitInfo.getAmount().compareTo(
							splitInfo.getCompletePrjAmt()) == 0) {
				int idx = 0;
				BigDecimal amountMax = FDCHelper.ZERO;
				BigDecimal amount = FDCHelper.ZERO;// null;
				BigDecimal amountTotal = FDCHelper.ZERO;
				amountTotal = amountTotal.setScale(10);
				for (int i = 0; i < size; i++) {
					PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) splitInfo
							.getEntrys().get(i);
					if (entry.getLevel() == 0) {
						amount = entry.getQualityAmount();
						if (amount == null) {
							amount = FDCHelper.ZERO;
						}
						amountTotal = amountTotal.add(amount);
						// ������Ϊ���������
						// if(amount.compareTo(FDCHelper.ZERO)!=0){
						if (amount.compareTo(amountMax) >= 0) {
							amountMax = amount;
							idx = i;
						}
					} else {
						continue;
					}
				}
				if (idx > 0
						&& splitInfo.getQualityAmount().compareTo(amountTotal) != 0) {
					PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) splitInfo
							.getEntrys().get(idx);
					if (entry.getQualityAmount() != null) {
						if (!(entry.getQualityAmount().equals(FDCHelper.ZERO))) {
							amount = entry.getQualityAmount();
							if (amount == null) {
								amount = FDCHelper.ZERO;
							}
							amount = amount.add(splitInfo.getQualityAmount()
									.subtract(amountTotal));
							entry.setQualityAmount(amount);
						}
					}
				}
				for (int i = 0; i < size; i++) {
					PaymentNoCostSplitEntryInfo entry = (PaymentNoCostSplitEntryInfo) splitInfo
							.getEntrys().get(i);
					if (entry.getLevel() == 0) {
						int curIndex = splitInfo.getEntrys().indexOf(entry);
						PayNoCostSplitAppHelper.adjustQuaAmount(splitInfo
								.getEntrys(), entry);
						// �������ɷ���ϸ������Ŀ�и��ӿ�Ŀ�ĳɱ� jelon 12/29/2006
						PayNoCostSplitAppHelper.totAmountQuaAddlAcct(
								splitInfo.getEntrys(), curIndex);
						// calcAmount(0);
					}
				}
			}
		}
	}

	private boolean isCostNoTextSplit(Context ctx, String id)
			throws EASBizException, BOSException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("isCostSplit");
		ContractWithoutTextInfo info = ContractWithoutTextFactory
				.getLocalInstance(ctx).getContractWithoutTextInfo(
						new ObjectUuidPK(id), selector);
		return info.isIsCostSplit();
	}

	protected void _traceContractNoText(Context ctx, List idList)
			throws BOSException, EASBizException {
		boolean isAdjust = isAdjustModel(ctx);
		if(isAdjust){
			_traceAdjustContracts(ctx,idList,true,VoucherAdjustReasonEnum.INVALID);
		}else{
			traceContractNoTextRedPatten(ctx, idList);
		}
		
	}
	/**
	 * @param ctx
	 * @param idList
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void traceContractNoTextRedPatten(Context ctx, List idList) throws BOSException, EASBizException {
		IVoucher voucher = VoucherFactory.getLocalInstance(ctx);
		for (int i = 0, size = idList.size(); i < size; i++) {
			String contractID = idList.get(i).toString();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(
							new FilterItemInfo("paymentBill.contractBillId",
									contractID));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.EQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("Fivouchered", Boolean.TRUE,
							CompareType.EQUALS));
			view.setFilter(filter);
			if (isCostNoTextSplit(ctx, contractID)) {
				PaymentSplitCollection paymentSplitCollection = PaymentSplitFactory
						.getLocalInstance(ctx).getPaymentSplitCollection(view);
				VoucherInfo newVoucher = new VoucherInfo();
				for (Iterator iter = paymentSplitCollection.iterator(); iter
						.hasNext();) {
					PaymentSplitInfo element = (PaymentSplitInfo) iter.next();
					EntityViewInfo mapping = new EntityViewInfo();
					FilterInfo mappingFilter = new FilterInfo();
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcEntityID", element
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("destEntityID", newVoucher
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcObjectID", element.getId()
									.toString()));
					mapping.setFilter(mappingFilter);
					mapping.getSelector().add("id");
					mapping.getSelector().add("destObjectID");
					BOTRelationCollection relationColl = BOTRelationFactory
							.getLocalInstance(ctx).getCollection(mapping);
					for (Iterator it = relationColl.iterator(); it.hasNext();) {
						BOTRelationInfo botInfo = (BOTRelationInfo) it.next();
						BOSUuid voucherId = BOSUuid.read(botInfo
								.getDestObjectID());
						SelectorItemCollection origen = new SelectorItemCollection();
						origen.add("id");
						origen.add("hasReversed");
						VoucherInfo oldInfo = (VoucherInfo) voucher.getValue(
								new ObjectUuidPK(voucherId), origen);
						if (!oldInfo.isHasReversed()) {
							SelectorItemCollection newVoucherTr = new SelectorItemCollection();
							newVoucherTr.add("voucherTimes");
							element.setVoucherTimes(1);
							PaymentSplitFactory.getLocalInstance(ctx)
									.updatePartial(element, newVoucherTr);
							ArrayList voucherIDList = new ArrayList();
							voucherIDList.add(voucherId.toString());
							if (idList.size() > 0) {
								IObjectPK newVoucherPk = voucher
										.reverseSaveBatch(voucherIDList);
								PaySplitUtilFacadeFactory.getLocalInstance(ctx)
										.traceReverseVoucher(newVoucherPk);
							}
						}
					}
				}

				EntityViewInfo viewNew = new EntityViewInfo();
				FilterInfo filterNew = new FilterInfo();
				filterNew.getFilterItems().add(
						new FilterItemInfo("paymentBill.contractBillId",
								contractID));
				filterNew.getFilterItems().add(
						new FilterItemInfo("state",
								FDCBillStateEnum.INVALID_VALUE,
								CompareType.NOTEQUALS));
				filterNew.getFilterItems().add(
						new FilterItemInfo("Fivouchered", Boolean.TRUE,
								CompareType.NOTEQUALS));
				viewNew.setFilter(filterNew);
				PaymentSplitCollection newSplitCollection = PaymentSplitFactory
						.getLocalInstance(ctx).getPaymentSplitCollection(
								viewNew);
				if (newSplitCollection.size() > 0) {
					PaymentSplitFactory.getLocalInstance(ctx)
							.generateVoucher(
									new ObjectUuidPK(newSplitCollection.get(0)
											.getId()));
				}
			} else {
				PaymentNoCostSplitCollection paymentSplitCollection = PaymentNoCostSplitFactory
						.getLocalInstance(ctx).getPaymentNoCostSplitCollection(
								view);
				VoucherInfo newVoucher = new VoucherInfo();
				for (Iterator iter = paymentSplitCollection.iterator(); iter
						.hasNext();) {
					PaymentNoCostSplitInfo element = (PaymentNoCostSplitInfo) iter
							.next();
					EntityViewInfo mapping = new EntityViewInfo();
					FilterInfo mappingFilter = new FilterInfo();
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcEntityID", element
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("destEntityID", newVoucher
									.getBOSType()));
					mappingFilter.getFilterItems().add(
							new FilterItemInfo("srcObjectID", element.getId()
									.toString()));
					mapping.setFilter(mappingFilter);
					mapping.getSelector().add("id");
					mapping.getSelector().add("destObjectID");
					BOTRelationCollection relationColl = BOTRelationFactory
							.getLocalInstance(ctx).getCollection(mapping);
					for (Iterator it = relationColl.iterator(); it.hasNext();) {
						BOTRelationInfo botInfo = (BOTRelationInfo) it.next();
						BOSUuid voucherId = BOSUuid.read(botInfo
								.getDestObjectID());
						SelectorItemCollection origen = new SelectorItemCollection();
						origen.add("id");
						origen.add("hasReversed");
						VoucherInfo oldInfo = (VoucherInfo) voucher.getValue(
								new ObjectUuidPK(voucherId), origen);
						if (!oldInfo.isHasReversed()) {
							SelectorItemCollection newVoucherTr = new SelectorItemCollection();
							newVoucherTr.add("voucherTimes");
							element.setVoucherTimes(1);
							PaymentNoCostSplitFactory.getLocalInstance(ctx)
									.updatePartial(element, newVoucherTr);
							idList.add(voucherId.toString());
							ArrayList voucherIDList = new ArrayList();
							voucherIDList.add(voucherId.toString());
							if (idList.size() > 0) {
								IObjectPK newVoucherPk = voucher
										.reverseSaveBatch(voucherIDList);
								PaySplitUtilFacadeFactory.getLocalInstance(ctx)
										.traceReverseVoucher(newVoucherPk);
								FDCSQLBuilder builderUpdate = new FDCSQLBuilder(
										ctx);
								builderUpdate
										.appendSql("update t_gl_voucher set Fsourcetype=3,fsourcesys=37 where fid=?");
								builderUpdate.addParam(newVoucherPk.toString());
								builderUpdate.execute();
							}
						}
					}

				}

				EntityViewInfo viewNew = new EntityViewInfo();
				FilterInfo filterNew = new FilterInfo();
				filterNew.getFilterItems().add(
						new FilterItemInfo("paymentBill.contractBillId",
								contractID));
				filterNew.getFilterItems().add(
						new FilterItemInfo("state",
								FDCBillStateEnum.INVALID_VALUE,
								CompareType.NOTEQUALS));
				filterNew.getFilterItems().add(
						new FilterItemInfo("Fivouchered", Boolean.TRUE,
								CompareType.NOTEQUALS));
				viewNew.setFilter(filterNew);
				PaymentNoCostSplitCollection newSplitCollection = PaymentNoCostSplitFactory
						.getLocalInstance(ctx).getPaymentNoCostSplitCollection(
								viewNew);
				if (newSplitCollection.size() > 0) {
					PaymentNoCostSplitFactory.getLocalInstance(ctx)
							.generateVoucher(
									new ObjectUuidPK(newSplitCollection.get(0)
											.getId()));
				}
			}
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder
					.appendSql("update T_CON_ContractWithoutText set FConSplitExecState=? where fid=?");
			builder.addParam(ConSplitExecStateEnum.INVALIDDID_VALUE);
			builder.addParam(contractID);
			builder.executeUpdate();

		}
	}

	private FDCSplitBillEntryCollection importContractCostSplit(Context ctx,
			String prjID, String periodID, String contractID)
			throws BOSException, EASBizException {
		FDCSplitBillEntryCollection coll = new FDCSplitBillEntryCollection();
		ContractCostSplitInfo info = new ContractCostSplitInfo();
		Set idListCon = new HashSet();
		EntityViewInfo contractView = new EntityViewInfo();
		FilterInfo contractFilter = new FilterInfo();
		contractFilter.getFilterItems().add(
				new FilterItemInfo("entityID", info.getBOSType().toString()));
		contractFilter.getFilterItems().add(
				new FilterItemInfo("contractID", contractID));
		contractFilter.getFilterItems().add(
				new FilterItemInfo("settlePeriod", periodID));
//		contractFilter.getFilterItems().add(
//				new FilterItemInfo("curProjectID", prjID));
		contractView.setFilter(contractFilter);
		SettledMonthlyCollection monthColl = SettledMonthlyFactory
				.getLocalInstance(ctx)
				.getSettledMonthlyCollection(contractView);
		for (Iterator iter = monthColl.iterator(); iter.hasNext();) {
			SettledMonthlyInfo item = (SettledMonthlyInfo) iter.next();
			idListCon.add(item.getObjectID().toString());
		}

		if (idListCon.size() == 0) {
			// MsgBox.showError(this, "�ɱ��½�ʱ�˸����Ӧ�ĺ�ͬδ���!");
//			SysUtil.abort();
			//Ӧ���׳��쳣�Ŷ� by sxhong 2008/02/24
			throw new EASBizException(new NumericExceptionSubItem("100","�ɱ��½�ʱ�˸����Ӧ�ĺ�ͬδ���"));
		}

		contractView = new EntityViewInfo();
		contractFilter = new FilterInfo();
		contractFilter.getFilterItems().add(
				new FilterItemInfo("id", idListCon, CompareType.INCLUDE));
		contractView.setFilter(contractFilter);
		contractView.getSelector().add("id");
		contractView.getSelector().add("contractBill.id");
		ContractCostSplitCollection changeColl = ContractCostSplitFactory
				.getLocalInstance(ctx).getContractCostSplitCollection(
						contractView);
		for (Iterator iter = changeColl.iterator(); iter.hasNext();) {
			ContractCostSplitInfo changeInfo = (ContractCostSplitInfo) iter
					.next();
			coll.addCollection(loadCostSplit(
					CostSplitBillTypeEnum.CONTRACTSPLIT,
					getCostSplitEntryCollection(ctx,
							CostSplitBillTypeEnum.CONTRACTSPLIT, changeInfo
									.getId(), changeInfo.getContractBill()
									.getId())));
		}
		return coll;
	}

	private FDCSplitBillEntryCollection importChangeCostSplit(Context ctx,
			String prjID, String periodID, String contractID)
			throws BOSException, EASBizException {
		FDCSplitBillEntryCollection coll = new FDCSplitBillEntryCollection();
		Set idListCon = new HashSet();
		ConChangeSplitInfo info = new ConChangeSplitInfo();
		EntityViewInfo contractView = new EntityViewInfo();
		FilterInfo contractFilter = new FilterInfo();
		contractFilter.getFilterItems().add(
				new FilterItemInfo("entityID", info.getBOSType().toString()));
		contractFilter.getFilterItems().add(
				new FilterItemInfo("contractID", contractID));
		contractFilter.getFilterItems().add(
				new FilterItemInfo("settlePeriod", periodID));
//		contractFilter.getFilterItems().add(
//				new FilterItemInfo("curProjectID", prjID));
		contractView.setFilter(contractFilter);
		SettledMonthlyCollection monthColl = SettledMonthlyFactory
				.getLocalInstance(ctx)
				.getSettledMonthlyCollection(contractView);

		for (Iterator iter = monthColl.iterator(); iter.hasNext();) {
			SettledMonthlyInfo item = (SettledMonthlyInfo) iter.next();
			idListCon.add(item.getObjectID().toString());
		}

		if (idListCon.size() > 0) {
			contractView = new EntityViewInfo();
			contractFilter = new FilterInfo();
			contractFilter.getFilterItems().add(
					new FilterItemInfo("id", idListCon, CompareType.INCLUDE));
			contractView.setFilter(contractFilter);
			contractView.getSelector().add("id");
			contractView.getSelector().add("contractChange.id");
			ConChangeSplitCollection changeColl = ConChangeSplitFactory
					.getLocalInstance(ctx).getConChangeSplitCollection(
							contractView);
			for (Iterator iter = changeColl.iterator(); iter.hasNext();) {
				ConChangeSplitInfo changeInfo = (ConChangeSplitInfo) iter
						.next();
				coll.addCollection(loadCostSplit(
						CostSplitBillTypeEnum.CNTRCHANGESPLIT,
						getCostSplitEntryCollection(ctx,
								CostSplitBillTypeEnum.CNTRCHANGESPLIT,
								changeInfo.getId(), changeInfo
										.getContractChange().getId())));
			}
		}
		return coll;
	}

	private ProjectPeriodStatusInfo getPrjPeriodStatus(Context ctx, String prjId)
			throws BOSException, EASBizException {
		ProjectPeriodStatusInfo prjInfo = new ProjectPeriodStatusInfo();
		EntityViewInfo viewPrj = new EntityViewInfo();
		FilterInfo filterPrj = new FilterInfo();
		filterPrj.appendFilterItem("project.id", prjId);
		viewPrj.setFilter(filterPrj);
		prjInfo = ProjectPeriodStatusFactory.getLocalInstance(ctx)
				.getProjectPeriodStatusCollection(viewPrj).get(0);
		return prjInfo;
	}

	/**
	 * ͨ������Ĳ�ֵ��ݵ�����(�����֣������ֵ�)����Ӧ�Ĳ��ID�õ���ַ�¼
	 * 
	 * @param splitBillType
	 * @param costBillUuId
	 *            ��ֵ��ݵ�CostBillUuid�������BOSUuid�����BOSUuid��
	 * @return
	 */
	protected FDCSplitBillEntryCollection getCostSplitEntryCollection(
			Context ctx, CostSplitBillTypeEnum splitBillType,
			BOSUuid splitBillUuId, BOSUuid costBillUuId) throws BOSException,
			EASBizException {

		String splitBillId = splitBillUuId.toString();

		if (splitBillId == null) {
			return new FDCSplitBillEntryCollection();
		}
		AbstractObjectCollection coll = null;
		AbstractBillEntryBaseInfo item = null;
		EntityViewInfo view = new EntityViewInfo();
		String filterField = null;
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sic = view.getSelector();
		setSelectorsEntry(ctx, sic, true);
		view.getSorter().add(new SorterItemInfo("seq"));

		if (splitBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
			filterField = "parent.id";
			filter.getFilterItems().add(
					new FilterItemInfo(filterField, splitBillId));
			view.setFilter(filter);

			coll = ContractCostSplitEntryFactory.getLocalInstance(ctx)
					.getContractCostSplitEntryCollection(view);

		} else if (splitBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {
			filterField = "parent.id";
			filter.getFilterItems().add(
					new FilterItemInfo(filterField, splitBillId));
			view.setFilter(filter);

			coll = ConChangeSplitEntryFactory.getLocalInstance(ctx)
					.getFDCSplitBillEntryCollection(view);

		} else if (splitBillType.equals(CostSplitBillTypeEnum.SETTLEMENTSPLIT)) {
			filterField = "parent.id";
			filter.getFilterItems().add(
					new FilterItemInfo(filterField, splitBillId));
			view.setFilter(filter);

			coll = SettlementCostSplitEntryFactory.getLocalInstance(ctx)
					.getFDCSplitBillEntryCollection(view);

		} else {
			// ������ֵ�,�Ժ��ṩ֧��
			return new FDCSplitBillEntryCollection();
		}

		FDCSplitBillEntryCollection entrys = new FDCSplitBillEntryCollection();
		FDCSplitBillEntryInfo entry = null;

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			item = (AbstractBillEntryBaseInfo) iter.next();
			item.setId(null);
			// item.setSeq(null);

			entry = new FDCSplitBillEntryInfo();
			entry.putAll(item);

			// costBillUuId=item.get(costBillIdField)
			if (splitBillId != null) {
				entry.setSplitBillId(splitBillUuId);
			}
			entry.setCostBillId(costBillUuId);
			/*
			 * if(entry.getProduct()!=null){ try{
			 * entry.setProduct(ProductTypeFactory.getLocalInstance(ctx).getProductTypeInfo(
			 * new ObjectUuidPK(entry.getProduct().getId()))); }catch(Exception
			 * e){ handUIException(e); } }
			 */
			entrys.add(entry);
		}

		return entrys;
	}
	
	/**
	 * �Ƿ���һ�廯����ƾ֤ģʽ
	 * @param ctx
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private boolean isAdjustModel(Context ctx) throws EASBizException, BOSException{
		String companyId=ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		return FDCUtils.isAdjustVourcherModel(ctx, companyId);
	}
	/**
	 * �Ƿ���һ�廯ģʽ
	 * @param ctx
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private boolean isFinancialModel(Context ctx) throws EASBizException, BOSException{
		String companyId=ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		return FDCUtils.IsFinacial(ctx, companyId);
	}
	/**
	 * ���������
	 * @param ctx
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private boolean isSplitBaseOnProp(Context ctx) throws EASBizException, BOSException{
		String companyId=ContextUtil.getCurrentCostUnit(ctx).getId().toString();
		return FDCUtils.getDefaultFDCParamByKey(ctx, companyId, FDCConstants.FDC_PARAM_SPLITBASEONPROP);
	}

}