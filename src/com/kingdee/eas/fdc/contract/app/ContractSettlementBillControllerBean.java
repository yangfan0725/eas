package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.CompensationBillCollection;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryTempFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryTempInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitTempFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitTempInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.CostClosePeriodFacadeFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadException;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * 
 * ����:���㵥
 * 
 * @author liupd date:2006-10-13
 *         <p>
 * @version EAS5.1.3
 */
public class ContractSettlementBillControllerBean extends
		AbstractContractSettlementBillControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.contract.app.ContractSettlementBillControllerBean");
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		final ContractSettlementBillInfo settle = (ContractSettlementBillInfo) model;
		checkFinalSettleDup(ctx, settle);
		if(settle.getContractBill()!=null){
			settle.setIsCostSplit(settle.getContractBill().isIsCoseSplit());
		}
		super._submit(ctx, pk, model);
	}

	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
		super._cancel(ctx,pk);
		
		updateSettInfoForContrct(ctx, BOSUuid.read(pk.toString()), false);
	}	
	
	/**
	 * �ĺ������ֽ��ϼƲ��ȣ��������ּ���صĸ�����  by Cassiel_peng
	 */
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		
		//�ύǰ���
		final ContractSettlementBillInfo settle = (ContractSettlementBillInfo) model;
		
		//��ͬ�ɽ��ж�ν���
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("orgUnit.id");
		selector.add("isFinalSettle");
		selector.add("contractBill.id");
		selector.add("curProject.id");
		selector.add("curProject.fullOrgUnit.id");
		
		selector.add("auditTime");
		selector.add("bookedDate");
		selector.add("period.number");
		selector.add("period.beginDate");
		selector.add("period.endDate");
		//ContractSettlementBillInfo billInfo = this.getContractSettlementBillInfo(ctx,new ObjectUuidPK(billId),selector);

		if(BooleanEnum.TRUE.equals(settle.getIsFinalSettle())){
			String contractId = settle.getContractBill().getId().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING));
			filter.getFilterItems().add(new FilterItemInfo("isFinalSettle", BooleanEnum.FALSE));
			filter.setMaskString("#0 and ( #1 or #2 or #3 )  and  #4");
			if (settle.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo("id", settle.getId().toString(), CompareType.NOTEQUALS));
				filter.setMaskString("#0 and ( #1 or #2 or #3 )  and  #4  and #5");
			} 
			//�Ƿ��������δ��˵Ľ��㵥
			if (this.exists(ctx,filter)) {
				throw new ContractException(ContractException.AUDITFIRST);
			}
		}
		
		checkBillForSubmit( ctx,model);
		//�١��˴��޸ĺ�ı�λ�ҽ��
		BigDecimal amount=FDCHelper.toBigDecimal(settle.getSettlePrice(),2);
		FilterInfo filter =null;
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		if(settle!=null&&settle.getId()!=null&&settle.getContractBill()!=null&&settle.getContractBill().isIsCoseSplit()){	//�ɱ�����
				try {
					//�жϸý��㵥�Ƿ��Ѿ�����ֹ������δ����ֹ���������������ٽ��У����Ч�ʡ�  by Cassiel_peng
					builder.clear();
					builder.appendSql("select fid from T_CON_SettlementCostSplit where FSettlementBillID=? ");
					builder.addParam(settle.getId().toString());
					IRowSet set=builder.executeQuery();
					while(set.next()){//�Ѿ�����ֹ���
						builder.clear();
						builder.appendSql("select famount from T_CON_SettlementCostSplit where FSettlementBillID=? ");
						builder.addParam(settle.getId().toString());
						IRowSet rowSet=builder.executeQuery();
						while(rowSet.next()){
							//�ڡ��ý��㵥��Ӧ�Ľ����ֵ�"�Ѳ�ֽ������"����"�Ѳ�ֽ��ϼ�"
							BigDecimal splitedAmount=FDCHelper.toBigDecimal(rowSet.getBigDecimal("famount"),2);
							if(!amount.equals(splitedAmount)){
								/*String settSplitID=set.getString("fid");//�ҵ�ĳһ���㵥�Ĳ�ֵ���
								builder.clear();
								builder.appendSql("select fcostbillid from T_CON_SettlementCostSplitEntry where FParentID=? ");
								builder.addParam(settSplitID);
								IRowSet rowSet2=builder.executeQuery();
								while(rowSet.next()){
									//�ҵ���Ӧ�ĸ�����ͷ
									builder.clear();
									builder.appendSql("select distinct(fparentid) from  T_FNC_PaymentSplitEntry  where  fcostbillid=? ");
									builder.addParam(rowSet2.getString("fcostbillid"));
									IRowSet rowSet3=builder.executeQuery();
									while(rowSet3.next()){
										//ɾ��������
										filter=new FilterInfo();
										filter.appendFilterItem("costBillId", rowSet2.getString("fcostbillid"));
										PaymentSplitEntryFactory.getLocalInstance(ctx).delete(filter);//ɾ����¼
										filter=new FilterInfo();
										filter.appendFilterItem("id",rowSet3.getString("fparentid"));
										PaymentSplitFactory.getLocalInstance(ctx).delete(filter);//ɾ��ͷ
									}
								}*/
								
								/**
								 *���������ۣ�������ύ״̬�µĽ��㵥���������뵥�������Ͳ�����"�����"����ô���������ֵ�ʱ��
								 *��һ������ȥ���ý����֣��������޸ĸý��㵥�Ľ������ʱ��Ͳ���Ҫ��ȥ���"������"  by Cassiel_peng
								 */
								filter=new FilterInfo();
								// ɾ��������
								filter.appendFilterItem("parent.settlementBill.id", settle.getId().toString());
								SettlementCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);
								filter=new FilterInfo();
								filter.appendFilterItem("settlementBill.id", settle.getId().toString());
								SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
							}
						}
					}
					} catch (SQLException e) {
						e.printStackTrace();
				}
		}
		if(settle!=null&&settle.getId()!=null&&settle.getContractBill()!=null&&!settle.getContractBill().isIsCoseSplit()){//�ǳɱ�����
				try {
					//�жϸ����㵥�Ƿ��Ѿ�����ֹ������δ����ֹ���������������ٽ��У����Ч�ʡ�  by Cassiel_peng
					builder.clear();
					builder.appendSql("select fid from T_CON_SettNoCostSplit where FSettlementBillID=? ");
					builder.addParam(settle.getId().toString());
					IRowSet rowSet1=builder.executeQuery();
					while(rowSet1.next()){//�Ѿ�����ֹ���
						builder.clear();
						builder.appendSql("select famount from T_CON_SettNoCostSplit where FSettlementBillID=? ");
						builder.addParam(settle.getId().toString());
						IRowSet rowSet=builder.executeQuery();
						while(rowSet.next()){
							//�ڡ��ý��㵥��Ӧ�Ľ����ֵ�"�Ѳ�ֽ������"����"�Ѳ�ֽ��ϼ�"
							BigDecimal splitedAmount=FDCHelper.toBigDecimal(rowSet.getBigDecimal("famount"),2);
							if(!amount.equals(splitedAmount)){
								filter=new FilterInfo();
								filter.appendFilterItem("parent.settlementBill.id", settle.getId().toString());
								SettNoCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);
								filter=new FilterInfo();
								filter.appendFilterItem("settlementBill.id", settle.getId().toString());
								SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
							}
						}
					}
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		
		if(settle.getContractBill()!=null){
			settle.setIsCostSplit(settle.getContractBill().isIsCoseSplit());
		}
		checkFinalSettleDup(ctx, (ContractSettlementBillInfo) model);
		
		String costCenterId = ContextUtil.getCurrentCostUnit(ctx).getId().toString();
		boolean isContractChangeMustComplete = FDCUtils.getDefaultFDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_CONTRACTCHANGESETTLEMENTMUSTCOMPLETE);
		if (isContractChangeMustComplete) {
			autoSettlementChangeBill(ctx, model);
		}

		return super._submit(ctx, model);
	}
	
    protected void _update(Context ctx , IObjectPK pk , IObjectValue model) throws
    BOSException , EASBizException
	{
	    super._update(ctx , pk , model);
	    
	    String[] pk1=new String[]{pk.toString()};
	    List contractIds;
		try {
			contractIds = getContractIds(ctx,pk1);
			reComContractSettlementBill(ctx,contractIds) ;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	 
	}

	/**
	 * 
	 * ����������Ƿ����ս����ظ�
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:jelon ����ʱ�䣺2006-9-19
	 *               <p>
	 */
	private void checkFinalSettleDup(Context ctx,
			ContractSettlementBillInfo billInfo) throws BOSException,
			EASBizException {
//		if (billInfo.getIsFinalSettle() != BooleanEnum.TRUE) {
//			return;
//		}

		FilterInfo filter = new FilterInfo();
		// ��ͬ
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.id", billInfo
						.getContractBill().getId().toString()));
		// �Ǳ���״̬
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.SAVED,
						CompareType.NOTEQUALS));
		// ���ս���
		filter.getFilterItems().add(
				new FilterItemInfo("isFinalSettle", BooleanEnum.TRUE));
		// �ǵ�ǰ���㵥
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.FINALSETTLE_DUP);
		}
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		
		checkBillForAudit( ctx,billId,null);
		
		super._audit(ctx, billId);
		
		//��ͬ�ɽ��ж�ν���
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("orgUnit.id");
		selector.add("isFinalSettle");
		selector.add("contractBill.id");
		selector.add("curProject.id");
		selector.add("curProject.fullOrgUnit.id");
		selector.add("curProject.costCenter");
		selector.add("isCostSplit");
		selector.add("auditTime");
		selector.add("bookedDate");
		selector.add("period.number");
		selector.add("period.beginDate");
		selector.add("period.endDate");
		selector.add("splitState");
		ContractSettlementBillInfo billInfo = this.getContractSettlementBillInfo(ctx,new ObjectUuidPK(billId),selector);

		if(BooleanEnum.TRUE.equals(billInfo.getIsFinalSettle())){
			String contractId = billInfo.getContractBill().getId().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED));
			filter.getFilterItems().add(new FilterItemInfo("isFinalSettle", BooleanEnum.FALSE));
			filter.setMaskString("#0 and ( #1 or #2)  and  #3");
			//�Ƿ��������δ��˵Ľ��㵥
			if (this.exists(ctx,filter)) {
				throw new ContractException(ContractException.AUDITFIRST);
			}
			//�Ƿ����δ��˵ı��������     by Cassiel_peng
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.SAVED));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.SUBMITTED));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.AUDITTING));//��֪�����ֵ���״̬�Ƿ���Ҫ����
			filter.setMaskString("#0 and ( #1 or #2 or #3)");
			if(ChangeSupplierEntryFactory.getLocalInstance(ctx).exists(filter)){
				throw new ContractException(ContractException.CHANGEBILLNOTAUDIT);
			}
		}
		
		
		//���޸ĵ�������ǰ sxhong 2008/1/4
//		changeSettle(ctx,billId.toString());
		
		if(BooleanEnum.FALSE.equals(billInfo.getIsFinalSettle())){
			FilterInfo filter = new FilterInfo();
			// ��ͬ
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", billInfo.getContractBill().getId().toString()));
			// ���ս���
			filter.getFilterItems().add(new FilterItemInfo("isFinalSettle", BooleanEnum.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
			
			if (_exists(ctx, filter)) {
				throw new ContractException(ContractException.FINALSETTLE_DUP);
			}
		}
//		HashMap param = FDCUtils.getDefaultFDCParam(null,billInfo.getOrgUnit().getId().toString());
//		boolean canSetterMore = false;
//		if(param.get(FDCConstants.FDC_PARAM_MORESETTER)!=null){
//			canSetterMore = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_MORESETTER).toString()).booleanValue();
//		}
		
		if(BooleanEnum.TRUE.equals(billInfo.getIsFinalSettle())){
			updateSettInfoForContrct(ctx, billId, true);
			
			//���º�ͬ�ѽ���
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("update T_CON_ContractSettlementBill set FIsSettled=1 where fcontractBillId=? ");
			builder.addParam(billInfo.getContractBill().getId().toString());
			builder.execute();
	
			// �Զ�������ֵ�(��ȫ���״̬��)
			CostSplitBillAutoAuditor.autoAudit(ctx, new ObjectUuidPK(billId),
					"T_CON_SettlementCostSplit", "FSettlementBillID");
			
		}
		//���㵥����ʱ�����¶�Ӧִ�б������Ϣ
		ContractExecInfosFactory.getLocalInstance(ctx)
			.updateSettle(ContractExecInfosInfo.EXECINFO_AUDIT,billInfo.getContractBill().getId().toString());
		
		//��ԷҪ�� ���ݵ�ǰʱ�����ö�Ӧ�ڼ�
		String companyID = billInfo.getCurProject().getFullOrgUnit().getId().toString();
		Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyID);
//		boolean isResetPeriod = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_RESETPERIOD);
		boolean isInCore = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INCORPORATION);
		if(isInCore){//ȥ�������������½�ͳһ�������߼�����
			String prjId = billInfo.getCurProject().getId().toString();
			// �����ڼ�
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
			PeriodInfo billPeriod = billInfo.getPeriod();
			PeriodInfo shouldPeriod = null;//���������ڼ�
			Date bookedDate = DateTimeUtils.truncateDate(billInfo.getBookedDate());
			
			if(finPeriod==null){
				throw new EASBizException(new NumericExceptionSubItem("100","��������Ӧ����֯û�е�ǰʱ����ڼ䣬�������ã�"));
			}
			/***************
			 * ��1�����������뵥�ϵġ�ҵ�����ڡ��͡�ҵ���ڼ䡱���ڡ��������ڡ���������Ŀ�ɱ����񡰵�ǰ�ڼ䡱��ʱ������ġ������ڼ䡱�͡��������ڡ�ȡ�������뵥�ġ�ҵ���ڼ䡱�͡�ҵ�����ڡ�
			 * ��2�����������뵥�ϵġ�ҵ�����ڡ��͡�ҵ���ڼ䡱С�ڵ��ڡ��������ڡ���������Ŀ�ɱ����񡰵�ǰ�ڼ䡱��ʱ������ġ������ڼ䡱�͡��������ڡ�ȡ��������Ŀ�ɱ����񡰵�ǰ�ڼ䡱�����������ڣ��ҽ��������ڷ�д�ظ������뵥�ϵġ�ҵ�����ڡ��͡�ҵ���ڼ䡱��
			 *	
			 *	ԭ�����ֱ���ʱ��ͬ���ڼ��ϳ�����
			 */
			if(billPeriod!=null&&billPeriod.getNumber()>finPeriod.getNumber()){
				if (bookedDate.before(billPeriod.getBeginDate())) {
					bookedDate = billPeriod.getBeginDate();
				} else if (bookedDate.after(billPeriod.getEndDate())) {
					bookedDate = billPeriod.getEndDate();
				}
				shouldPeriod = billPeriod;
			}else if(finPeriod!=null){
				if (bookedDate.before(finPeriod.getBeginDate())) {
					bookedDate = finPeriod.getBeginDate();
				} else if (bookedDate.after(finPeriod.getEndDate())) {
					bookedDate = finPeriod.getEndDate();
				}
				shouldPeriod = finPeriod;
			}
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("update T_CON_ContractSettlementBill set FPeriodId = ?,FBookedDate = ? where fId=? ");
			builder.addParam(shouldPeriod.getId().toString());
			builder.addParam(bookedDate);
			builder.addParam(billId.toString());
			builder.execute();
		}
			
		// ֻ����ɱ��ࣺ��ͬ�������ͬ����Ĳ���Զ����ú�ͬ��ֵĳɱ���Ŀ����ֱ���
		if (billInfo != null && billInfo.isIsCostSplit()&&billInfo.getCurProject() != null && billInfo.getCurProject().getCostCenter() != null && billInfo.getCurProject().getCostCenter().getId() != null
				&& !CostSplitStateEnum.ALLSPLIT.equals(billInfo.getSplitState())) {
			boolean isImportConSplit = FDCUtils.getDefaultFDCParamByKey(ctx, billInfo.getCurProject().getCostCenter().getId().toString(), FDCConstants.FDC_PARAM_IMPORTCONSPLIT);
			if (isImportConSplit) {
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("select fsplitstate from t_con_contractcostsplit where fcontractbillid=? and fisinvalid=0 ");
				builder.addParam(billInfo.getContractBill().getId().toString());
				IRowSet rs = builder.executeQuery();
				try {
					if (rs != null && rs.size() == 1) {
						rs.next();
						String splitState = rs.getString("fsplitstate");
						if(!CostSplitStateEnum.ALLSPLIT_VALUE.equals(splitState)){
							throw new EASBizException(new NumericExceptionSubItem("100","�����˲�������ͬ�������ͬ����Ĳ���Ƿ��Զ����ú�ͬ��ֵĳɱ���Ŀ����ֱ���������δ��ȫ��ֵĺ�ͬ������ȡ����"));
						}
					} else {
						throw new EASBizException(new NumericExceptionSubItem("100","�����˲�������ͬ�������ͬ����Ĳ���Ƿ��Զ����ú�ͬ��ֵĳɱ���Ŀ����ֱ���������δ��ֵĺ�ͬ������ȡ����"));
					}
					builder.clear();
					builder.appendSql("select split.fsplitstate from t_con_contractchangebill bill ");
					builder.appendSql("left outer join t_con_conchangesplit split on bill.fid=split.fcontractchangeID ");
					builder.appendSql("where bill.fcontractbillid=? ");
					builder.addParam(billInfo.getContractBill().getId().toString());
					rs = builder.executeQuery();
					if(rs!=null){
						while(rs.next()){
							String splitState = rs.getString("fsplitstate");
							if(!CostSplitStateEnum.ALLSPLIT_VALUE.equals(splitState)){
								throw new EASBizException(new NumericExceptionSubItem("100","�����˲�������ͬ�������ͬ����Ĳ���Ƿ��Զ����ú�ͬ��ֵĳɱ���Ŀ����ֱ���.����δ��ȫ��ֵı��������ȡ����"));
							}
						}
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				SettlementCostSplitFactory.getLocalInstance(ctx).autoSplit4(billId);
			}
		}
		try {
			synContractProgAmt(ctx,billInfo,true);
		} catch (SQLException e) {
			logger.error(e);
		}
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractSettleBill.id", billId.toString()));
		if(ContractPCSplitBillFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","���ں�Լ�滮��֣����ܽ��з�����������"));
		}
		checkBillForUnAudit( ctx,billId,null);
		
		//��ͬ�ɽ��ж�ν���
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("orgUnit.id");
		selector.add("isFinalSettle");
		//contractBill
		selector.add("contractBill.id");
		selector.add("period.id");
		ContractSettlementBillInfo billInfo = this.getContractSettlementBillInfo(ctx,new ObjectUuidPK(billId),selector);
		
		if(BooleanEnum.FALSE.equals(billInfo.getIsFinalSettle())){
			filter = new FilterInfo();
			// ��ͬ
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", billInfo.getContractBill().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
			// ���ս���
			filter.getFilterItems().add(new FilterItemInfo("isFinalSettle", BooleanEnum.TRUE));
			//����ͬ״̬Ϊ������ ����Ϊ���ս��㵥 ����������
			if (_exists(ctx, filter)) {
				throw new ContractException(ContractException.CANTUNAUDITSETTER);
			}

		}
		if(BooleanEnum.FALSE.equals(billInfo.getIsFinalSettle())){
			String contractId = billInfo.getContractBill().getId().toString();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
			filter.getFilterItems().add(new FilterItemInfo("isFinalSettle", BooleanEnum.TRUE));
			filter.setMaskString("#0 and ( #1 or #2 or #3)  and  #4");
			//��������ύ�������С�����״̬�����ս��㵥��������ս��㵥���ܷ�����
			if (this.exists(ctx,filter)) {
				throw new ContractException(ContractException.CANTUNAUDITSETTER);
			}
		}
		//���а��������ĸ������뵥 �����ս��㵥���ܷ�����
		if(BooleanEnum.TRUE.equals(billInfo.getIsFinalSettle())){
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("contractId");
			view.getSelector().add("paymentType.payType.id");
			view.setFilter(new FilterInfo());
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("contractId",billInfo.getContractBill().getId().toString()));
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("paymentType.payType.id","Ga7RLQETEADgAAC/wKgOlOwp3Sw="));
			PayRequestBillCollection cons = PayRequestBillFactory.getLocalInstance(ctx)
				.getPayRequestBillCollection(view);
			for(int i = 0;i < cons.size();i++){
				PayRequestBillInfo info = cons.get(i);
				if(info.getId() != null){
					throw new ContractException(ContractException.HASSETTLEPAYREQUESTBILL);
				}
			}
			

		}
		//�Ѳ�ֵĽ��㵥����������
		FilterInfo filterSett = new FilterInfo();
		filterSett.getFilterItems().add(new FilterItemInfo("settlementBill.id", billId.toString()));
		filterSett.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		if (SettlementCostSplitFactory.getLocalInstance(ctx).exists(filterSett)
				|| SettNoCostSplitFactory.getLocalInstance(ctx).exists(filterSett)) {
			throw new ContractException(ContractException.SETTLESPLIEDCANNTUNAUDIT);
		}

		
		/**
		 * ��������˹�����ȷ�ϵ������㵥�����Է�������
		 */
		if(BooleanEnum.TRUE.equals(billInfo.getIsFinalSettle())){
			FilterInfo workLoadFilter = new FilterInfo();
			workLoadFilter.getFilterItems().add(
					new FilterItemInfo("contractBill.id",billInfo.getContractBill().
							getId().toString()));
			workLoadFilter.getFilterItems().add(
					new FilterItemInfo("hasSettled",Boolean.TRUE));
			if(WorkLoadConfirmBillFactory.getLocalInstance(ctx).
					exists(workLoadFilter)){
				throw new WorkLoadException(WorkLoadException.HAS_SETTLEDBILL);
			}
		}
		// ���������㵥��д�滮��Լ
		try {
			synContractProgAmt(ctx, billInfo, false);
		} catch (SQLException e) {
			logger.error(e);
		}
		super._unAudit(ctx, billId);
		//���������ύ״̬�µĵ��ݲ��������ս��㵥(��Ҫ���ͬ�������)
		boolean isCanSplitForSubmit = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_SPLITSUBMIT);
		if(!isCanSplitForSubmit&&BooleanEnum.TRUE.equals(billInfo.getIsFinalSettle())){
			dealBackData(ctx, billInfo);
		}
		
		if(BooleanEnum.TRUE.equals(billInfo.getIsFinalSettle())){
//			SysUtil.abort();
			updateSettInfoForContrct(ctx, billId, false);
	
			// �Զ���������ֵ�
			CostSplitBillAutoAuditor.autoUnAudit(ctx, new ObjectUuidPK(billId),
					"T_CON_SettlementCostSplit", "FSettlementBillID");
			
			//���º�ͬ�����н���Ϊδ���� by sxhong
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("update T_CON_ContractSettlementBill set FIsSettled=0 where fcontractBillId=? ");
			builder.addParam(billInfo.getContractBill().getId().toString());
			builder.execute();
		}
		
		//�Զ�ɾ�������½�����
		if(billInfo.getPeriod()!=null){
			CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(billInfo.getContractBill().getId().toString(),
					billInfo.getPeriod().getId().toString());
		}
		
		//���㵥����ʱ�����¶�Ӧִ�б������Ϣ
		ContractExecInfosFactory.getLocalInstance(ctx)
			.updateSettle(ContractExecInfosInfo.EXECINFO_UNAUDIT,billInfo.getContractBill().getId().toString());
		try {
			synContractProgAmt(ctx,billInfo,true);
		} catch (SQLException e) {
			logger.error(e);
		}
	}

	/**
	 * ����ͬδ����ʱ(�����ս�������ս���δ����)���滮���=�滮���-��ǩԼ���+��������������=���ƽ��-ǩԼ��
	 * ����ͬ�ѽ���ʱ(���ս���������)���滮���=�滮���-������������=���ƽ��-������
	 * 
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	private void synContractProgAmt(Context ctx, ContractSettlementBillInfo model, boolean flag) throws EASBizException, BOSException, SQLException {
		BOSUuid contractBillId = model.getContractBill().getId();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(
				new ObjectUuidPK(contractBillId.toString()), sic);
		ProgrammingContractInfo pcInfo = contractBillInfo.getProgrammingContract();
		if (pcInfo == null)
			return;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		IRowSet rowSet = null;
		builder.appendSql("select fid  from T_CON_CONTRACTBILL where fstate='4AUDITTED' and ");
		builder.appendSql(" fHasSettled =1 and  ");
		builder.appendParam("fid", contractBillId.toString());
		rowSet = builder.executeQuery();
		if (rowSet.next()) {
			// ��ͬǩԼ���
			BigDecimal conSignAmt = FDCHelper.ZERO;
			// ��ͬ������
			BigDecimal conChangeAmt = FDCHelper.ZERO;
			// ��ͬ������
			BigDecimal conSettleAmt = FDCHelper.ZERO;
			builder.clear();
			builder = new FDCSQLBuilder(ctx);
			rowSet = null;
			builder
					.appendSql("select con.famount conSignAmt,change.changeAmount conChangeAmt,settle.settleAmount conSettleAmt from t_con_contractbill con ");
			builder
					.appendSql(" left join (select FContractBillID,sum(case when fhassettled = 1 then FBalanceAmount else famount end ) changeAmount from t_con_contractChangeBill where fstate in  ");
			builder.appendSql(" ('4AUDITTED','7ANNOUNCE','8VISA') group by FContractBillID ) change on change.FContractBillID = con.fid ");
			builder
					.appendSql(" left join (select FContractBillID,sum(case when fstate in( '4AUDITTED','7ANNOUNCE','8VISA') then FCurSettlePrice else 0 end) settleAmount from");
			builder
					.appendSql(" T_CON_ContractSettlementBill where fisSettled = 1  group by FContractBillID)  settle on con.fid =  settle.FContractBillID where ");
			builder.appendParam("con.fid", contractBillId.toString());
			rowSet = builder.executeQuery();
			while (rowSet.next()) {
				conSignAmt = FDCHelper.toBigDecimal(rowSet.getString("conSignAmt"));
				conChangeAmt = FDCHelper.toBigDecimal(rowSet.getString("conChangeAmt"));
				conSettleAmt = FDCHelper.toBigDecimal(rowSet.getString("conSettleAmt"));
			}
			// �滮���
			BigDecimal balanceAmt = pcInfo.getBalance();
			// �������
			BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
					// //���㱾�ҽ��
					// BigDecimal settleAmount = model.getTotalSettlePrice();
			//��ܺ�ԼǩԼ���
			BigDecimal settleAmountProg = pcInfo.getSettleAmount();
			// ���
			
			BigDecimal otherBalanceAmount = FDCHelper.ZERO;
			BigDecimal otherControlBalanceAmount = FDCHelper.ZERO;
			BigDecimal otherSettleAmount = FDCHelper.ZERO;
			
			SelectorItemCollection sict = new SelectorItemCollection();
			sict.add("balance");
			sict.add("controlBalance");
			sict.add("signUpAmount");
			sict.add("changeAmount");
			sict.add("settleAmount");
			sict.add("srcId");
			sict.add("estimateAmount");
			sict.add("unAuditContractSettleEA");
			if(flag){
				pcInfo.setBalance(FDCHelper.subtract(FDCHelper.add(balanceAmt, FDCHelper.add(conSignAmt, conChangeAmt)), conSettleAmt));
				pcInfo.setControlBalance(FDCHelper.subtract(FDCHelper.add(controlBalanceAmt, conSignAmt), conSettleAmt));
				pcInfo.setSettleAmount(FDCHelper.add(settleAmountProg, conSettleAmt));
				
				otherBalanceAmount = FDCHelper.subtract(FDCHelper.subtract(FDCHelper.add(balanceAmt, FDCHelper.add(conSignAmt, conChangeAmt)),
						conSettleAmt), balanceAmt);
				otherControlBalanceAmount = FDCHelper.subtract(FDCHelper.subtract(FDCHelper.add(controlBalanceAmt, conSignAmt), conSettleAmt),
						controlBalanceAmt);
				otherSettleAmount = FDCHelper.subtract(FDCHelper.add(settleAmountProg, conSettleAmt), settleAmountProg);
				
				//��Ҫ�ǲ����ͬ���Ѻ�Լ�滮��Ԥ�������Ϊ�㣬
//				BigDecimal  estimateAmount = pcInfo.getEstimateAmount();
//				pcInfo.setUnAuditContractSettleEA(estimateAmount);
				pcInfo.setEstimateAmount(FDCHelper.ZERO);
//				if(estimateAmount!=null){
//					pcInfo.setBalance(pcInfo.getBalance().add(estimateAmount));
//				}
				IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
				service.updatePartial(pcInfo, sict);
				//��Ӧ��Ԥ���䶯����Ϊ�Ƿ�����Ϊfalse
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
//				filter.getFilterItems().add(new FilterItemInfo("isLastest",Boolean.TRUE));
//				EntityViewInfo view = new EntityViewInfo();
//				view.setFilter(filter);
//				ContractEstimateChangeBillCollection  coll = ContractEstimateChangeBillFactory.getLocalInstance(ctx).getContractEstimateChangeBillCollection(view);
//				if(coll.size()>0){
//					ContractEstimateChangeBillInfo contInfo = coll.get(0);
//					contInfo.setIsLastest(false);
//					contInfo.setIsRespite(true);
//					SelectorItemCollection sel = new SelectorItemCollection();
//					sel.add("isLastest");
//					ContractEstimateChangeBillFactory.getLocalInstance(ctx).updatePartial(contInfo, sel);
//				}
				
//				String progId = pcInfo.getId().toString();
//				while (progId != null) {
//					String nextVersionProgId = getNextVersionProg(ctx, progId, builder, rowSet);
//					if (nextVersionProgId != null) {
//						pcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
//						pcInfo.setBalance(FDCHelper.add(pcInfo.getBalance(), otherBalanceAmount));
//						pcInfo.setControlBalance(FDCHelper.add(pcInfo.getControlBalance(), otherControlBalanceAmount));
//						pcInfo.setSettleAmount(FDCHelper.add(pcInfo.getSettleAmount(), otherSettleAmount));
//						service.updatePartial(pcInfo, sict);
//						progId = pcInfo.getId().toString();
//					} else {
//						progId = null;
//					}
//				}
			}else{
				pcInfo.setBalance(FDCHelper.subtract(FDCHelper.add(balanceAmt, conSettleAmt), FDCHelper.add(conSignAmt, conChangeAmt)));
				pcInfo.setControlBalance(FDCHelper.subtract(FDCHelper.add(controlBalanceAmt, conSettleAmt), conSignAmt));
				pcInfo.setSettleAmount(FDCHelper.subtract(settleAmountProg, conSettleAmt));
				
				otherBalanceAmount = FDCHelper.subtract(FDCHelper.subtract(FDCHelper.add(balanceAmt, conSettleAmt), FDCHelper.add(conSignAmt,
						conChangeAmt)), balanceAmt);
				otherControlBalanceAmount = FDCHelper.subtract(FDCHelper.subtract(FDCHelper.add(controlBalanceAmt, conSettleAmt), conSignAmt),
						controlBalanceAmt);
				otherSettleAmount = FDCHelper.subtract(FDCHelper.subtract(settleAmountProg, conSettleAmt), settleAmountProg);
				
//				BigDecimal  estimateAmount = FDCHelper.ZERO;
//				if(pcInfo.getUnAuditContractSettleEA()!=null){
//					estimateAmount=pcInfo.getUnAuditContractSettleEA();
//				}
//				if(!(pcInfo.getEstimateAmount()!=null&&pcInfo.getEstimateAmount().compareTo(FDCHelper.ZERO)>0)){
//					pcInfo.setEstimateAmount(estimateAmount);
//				}
//				pcInfo.setBalance(pcInfo.getBalance().subtract(estimateAmount));
				IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
				service.updatePartial(pcInfo, sict);
				
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
//				filter.getFilterItems().add(new FilterItemInfo("isRespite",Boolean.TRUE));
//				EntityViewInfo view = new EntityViewInfo();
//				view.setFilter(filter);
//				ContractEstimateChangeBillCollection  coll = ContractEstimateChangeBillFactory.getLocalInstance(ctx).getContractEstimateChangeBillCollection(view);
//				if(coll.size()>0){
//					ContractEstimateChangeBillInfo contInfo = coll.get(0);
//					contInfo.setIsLastest(true);
//					contInfo.setIsRespite(false);
//					SelectorItemCollection sel = new SelectorItemCollection();
//					sel.add("isLastest");
//					sel.add("isRespite");
//					ContractEstimateChangeBillFactory.getLocalInstance(ctx).updatePartial(contInfo, sel);
//				}
//				String progId = pcInfo.getId().toString();
//				while (progId != null) {
//					String nextVersionProgId = getNextVersionProg(ctx, progId, builder, rowSet);
//					if (nextVersionProgId != null) {
//						pcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
//						pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), otherBalanceAmount));
//						pcInfo.setControlBalance(FDCHelper.subtract(pcInfo.getControlBalance(), otherControlBalanceAmount));
//						pcInfo.setSettleAmount(FDCHelper.subtract(pcInfo.getSettleAmount(), otherSettleAmount));
//						service.updatePartial(pcInfo, sict);
//						progId = pcInfo.getId().toString();
//					} else {
//						progId = null;
//					}
//				}
			}
		}
	}

	private String getNextVersionProg(Context ctx, String nextProgId, FDCSQLBuilder builder, IRowSet rowSet) throws BOSException, SQLException {
		String tempId = null;
		builder.clear();
		builder.appendSql(" select fid from t_con_programmingContract where  ");
		builder.appendParam("FSrcId", nextProgId);
		rowSet = builder.executeQuery();
		while (rowSet.next()) {
			tempId = rowSet.getString("fid");
		}
		return tempId;
	}
	
	private void dealBackData(Context ctx, ContractSettlementBillInfo billInfo)
			throws BOSException, EASBizException {
		//���Դ��룬�ǵøɵ�
//		if(true){
//			return;
//		}
		SelectorItemCollection selector = new SelectorItemCollection();;
		//��ͬ���ս���ʱ�Զ����б�����㣬�����ս��㵥������ɾ���󣬱������������Ȼ���ڡ�
		//��Ȼ���ս��㵥����ʱ����Ӱ�������㣬�ǽ��㵥������ʱ�������������ҲӦ�ûص�ԭ��״̬ by cassiel 2010-10-08
			String contractBillId = billInfo.getContractBill().getId().toString();
			//���ָ�
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractBillId));
			selector = new SelectorItemCollection();
			selector.add("oriBalanceAmount");
			selector.add("balanceAmount");
			selector.add("hasSettled");
			selector.add("settleTime");
			view.setFilter(filter);
			view.setSelector(selector);
			ContractChangeBillCollection conChangeColl = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(view);
			for(Iterator iter = conChangeColl.iterator();iter.hasNext();){
				ContractChangeBillInfo conChangeInfo = (ContractChangeBillInfo)iter.next();
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.appendSql("select FOriBalanceAmount,FBalanceAmount,FHasSettled,FSettleTime,FSettleID,FSourceID from T_CON_ConChangeTemp where FSourceID=? ");
				builder.addParam(conChangeInfo.getId().toString());
				IRowSet rowSet = builder.executeQuery();
				try {
					if(rowSet.next()){
						BigDecimal oriBalanceAmt = rowSet.getBigDecimal("FOriBalanceAmount");
						BigDecimal BalanceAmt = rowSet.getBigDecimal("FBalanceAmount");
						boolean hasSettled = rowSet.getBoolean("FHasSettled");
						Date settleTime = rowSet.getDate("FSettleTime");
						
						conChangeInfo.setOriBalanceAmount(oriBalanceAmt);
						conChangeInfo.setBalanceAmount(BalanceAmt);
						conChangeInfo.setHasSettled(hasSettled);
						conChangeInfo.setSettleTime(settleTime);
						
						ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(conChangeInfo, selector);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			//��ԭ�����ֵ���
//			String companyId = FDCHelper.getCurCompanyId(ctx, info.getCurProject().getId().toString());
			String companyId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
			Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyId);
			boolean isFinacial = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_FINACIAL);
			Set allTempSplitSet = new HashSet();
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select FSplitID from T_CON_ConChangeSplitTemp ");
			IRowSet rowSet1 = builder.executeQuery();
			try {
				while(rowSet1.next()){
					allTempSplitSet.add(rowSet1.getString("FSplitID"));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if(allTempSplitSet != null&&allTempSplitSet.size()>0){
			if(isFinacial){//ֻ�����״̬
					builder.clear();
					builder.appendSql("update T_CON_ConChangeSplit set FIsInValid = 1 where FContractBillID=? ");
					builder.addParam(contractBillId);
					builder.executeUpdate();
					builder.clear();
					builder.appendSql("update T_CON_ConChangeSplit set FIsInValid = 0 where FContractBillID = ? ");
					builder.addParam(contractBillId);
					builder.appendParam("and  fid  ",allTempSplitSet.toArray());
					builder.executeUpdate();
				}else{//�������ʱ�����ҵ���Ӧ���Ȼ��ӱ����ֱ�����ҵ���Ҫ��ԭ��Ψһ��һ������
					EntityViewInfo _view = new EntityViewInfo();
					SelectorItemCollection _selector = new SelectorItemCollection();
					_selector.add("amount");
					_selector.add("splitID");
					FilterInfo _filter = new FilterInfo();
					_filter.getFilterItems().add(new FilterItemInfo("splitID",allTempSplitSet,CompareType.INCLUDE));
					_view.setFilter(_filter);
					_view.setSelector(_selector);
					CoreBaseCollection splitTempColls = ConChangeSplitTempFactory.getLocalInstance(ctx).getCollection(_view);
					
					_view = new EntityViewInfo();
					_selector = new SelectorItemCollection();
					_selector.add("amount");
					_selector.add("entryID");
					_filter = new FilterInfo();
					_filter.getFilterItems().add(new FilterItemInfo("parent.splitID",allTempSplitSet,CompareType.INCLUDE));
					_view.setFilter(_filter);
					_view.setSelector(_selector);
					CoreBaseCollection splitEntryTempColls = ConChangeSplitEntryTempFactory.getLocalInstance(ctx).getCollection(_view);
					
					for(Iterator iter = splitTempColls.iterator();iter.hasNext();){
						ConChangeSplitTempInfo splitTempInfo = (ConChangeSplitTempInfo)iter.next();
						builder.clear();
						builder.appendSql("update T_CON_ConChangeSplit set famount=? where fid=? ");
						builder.addParam(splitTempInfo.getAmount());
						builder.addParam(splitTempInfo.getSplitID());
						builder.executeUpdate();
					}
					for(Iterator iter = splitEntryTempColls.iterator();iter.hasNext();){
						ConChangeSplitEntryTempInfo splitEntryTempInfo = (ConChangeSplitEntryTempInfo)iter.next();
						builder.clear();
						builder.appendSql("update T_CON_ConChangeSplitEntry set famount = ? where fid=? ");
						builder.addParam(splitEntryTempInfo.getAmount());
						builder.addParam(splitEntryTempInfo.getEntryID());
						builder.executeUpdate();
					}
				}
			}
		}
	/**
	 * 
	 * ���������º�ͬ����״̬�ͽ�����
	 * 
	 * @param ctx
	 * @param billId
	 * @param isAudit
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd ����ʱ�䣺2006-10-13
	 *               <p>
	 */
	private void updateSettInfoForContrct(Context ctx, BOSUuid billId,
			boolean isAudit) throws BOSException, EASBizException {
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("contractBill.id");
		sic.add("isFinalSettle");
		sic.add("settlePrice");
		sic.add("curProject.fullOrgUnit.id");
		ContractSettlementBillInfo billInfo = getContractSettlementBillInfo(
				ctx, new ObjectUuidPK(billId),sic);
		SelectorItemCollection selector = new SelectorItemCollection();

		ContractBillInfo contractBillInfo = billInfo.getContractBill();
		String contractID = billInfo.getContractBill().getId().toString();
		String companyID = billInfo.getCurProject().getFullOrgUnit().getId().toString();
		// ��ǰ���㵥���Ƿ����ս���
		if (billInfo.getIsFinalSettle().equals(BooleanEnum.TRUE)) {
			contractBillInfo.setHasSettled(isAudit);
			selector.add("hasSettled");
		}

		// ���������㵥�����ϼ�
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("contractBill.id", contractBillInfo.getId()
//						.toString()));
//		filter.getFilterItems().add(
//				new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
//		view.setFilter(filter);
//		view.getSelector().add("settlePrice");
//		ContractSettlementBillCollection coll = getContractSettlementBillCollection(
//				ctx, view);
//
//		BigDecimal amount = new BigDecimal(0);
//		for (Iterator iter2 = coll.iterator(); iter2.hasNext();) {
//			billInfo = (ContractSettlementBillInfo) iter2.next();
//			amount = amount.add(billInfo.getSettlePrice());
//		}
		
		//�����ۼƽ�����settlePrice
		BigDecimal amount = GlUtils.zero;
		if(isAudit){	
			 amount = billInfo.getSettlePrice();
		}
		
		contractBillInfo.setSettleAmt(amount);
		selector.add("settleAmt");

		IContractBill iContract = ContractBillFactory.getLocalInstance(ctx);
		iContract.updatePartial(contractBillInfo, selector);
		
		// �������ⵥ,����������״̬
		auditCompensationBill(ctx, contractID, isAudit);
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_Con_ContractChangeBill set FISConSetted=? where fcontractBillId=?");
		builder.addParam(Boolean.valueOf(isAudit));
		builder.addParam(contractBillInfo.getId().toString());
		builder.execute();
		//ͬ����ִͬ��״̬
//		ContractStateHelper.synToExecState(ctx, contractBillInfo.getId().toString());
		
		//�����뷴����ʱ��Ҫ��֤��С�ڹ�����
//		BigDecimal latestPrice = FDCUtils.getContractLastAmt(ctx, contractID);
////		boolean isSeparate = FDCUtils.getDefaultFDCParamByKey(ctx, companyID, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
////		if(isSeparate){
////			BigDecimal workload = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(contractID);
////			if (latestPrice.compareTo(FDCHelper.toBigDecimal(workload, 2)) == -1) {
////				throw new EASBizException(new NumericExceptionSubItem("111",
////						"��ͬ�������С�ں�ͬ�Ĺ���ȷ�ϵ��������ۼƽ������޸Ĺ�����ȷ�ϵ���"));
////			}
////		}
	}

	private void auditCompensationBill(Context ctx, String contractId,
			boolean isAudited) throws BOSException, EASBizException {
		FilterInfo filter;
		EntityViewInfo conView = new EntityViewInfo();
		filter = new FilterInfo();
		conView.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("contract.Id", contractId));
		CompensationBillCollection compensations = CompensationBillFactory
				.getLocalInstance(ctx).getCompensationBillCollection(conView);
		for (int i = 0; i < compensations.size(); i++) {
			CompensationBillInfo info = compensations.get(i);
			if (info.getDescription() != null) {
				info.setIsCompensated(isAudited);
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add(new SelectorItemInfo("isCompensated"));
				CompensationBillFactory.getLocalInstance(ctx).updatePartial(
						info, sels);
			}
		}
	}
	
	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		//�������ΥԼ���ۿ����
		//
		if(arrayPK.length<=0) return;
		String[] pk=new String[arrayPK.length];
		for(int i=0;i<pk.length;i++){
			pk[i]=arrayPK[i].toString();						
		}
		
		List contractIds= null;
		try{
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			contractIds= getContractIds(ctx,pk);
			if(contractIds==null){
				return ;
			}
			
			//ΥԼ
			builder.clear();
			builder.appendSql("update T_CON_Compensationbill set fiscompensated=0 where ");
			builder.appendParam("fcontractid", contractIds.toArray());
			builder.appendSql(" and fid not in (select FCompensationID as fid from T_CON_CompensationOfPayReqBill t1 ");
			builder.appendSql("inner join t_con_payrequestbill t2 on t1.fpayrequestbillid=t2.fid and ");
			builder.appendParam("t2.fcontractid", contractIds.toArray());
			builder.appendSql(")");
			builder.execute();
			
			//����
			builder.clear();
			builder.appendSql("update T_CON_Guerdonbill set fisGuerdoned=0 where ");
			builder.appendParam("fcontractid", contractIds.toArray());
			builder.appendSql(" and fid not in (select FGuerdonID as fid from t_con_guerdonofpayreqbill t1 ");
			builder.appendSql("inner join t_con_payrequestbill t2 on t1.fpayrequestbillid=t2.fid and ");
			builder.appendParam("t2.fcontractid", contractIds.toArray());
			builder.appendSql(")");
			builder.execute();
			
			//�ۿ
			builder.clear();
			builder.appendSql("update t_fnc_deductbillentry set fhasApplied=0 where ");
			builder.appendParam("fcontractid", contractIds.toArray());
			builder.appendSql(" and fid not in (select fdeductbillentryid from t_con_deductofpayreqbillentry) ");
			builder.execute();
			
			
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
		//���ڽ��㵥
		
		super._delete(ctx, arrayPK);
		
		reComContractSettlementBill(ctx,contractIds) ;
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		_delete(ctx, new IObjectPK[]{pk});
	}
	
	/**
	 * �������
	 */
	private void changeSettle(Context ctx,String settleId) throws BOSException,EASBizException {
		if(FDCHelper.isEmpty(settleId)){
			return;
		}
/*		
 * 		�����Ԥ��ۣ�����ֵ���������
 *		���������ͬ����ۣ���ͬǩԼ�ۣ���ͬ����ָ�Ԥ����ܺͣ��������ָ�Ԥ���/��ͬ����ָ�Ԥ����ܺͣ�
*/
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("contractBill.id");
		selector.add("contractBill.amount");
		selector.add("settlePrice");
		ContractSettlementBillInfo settleInfo=ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillInfo(new ObjectUuidPK(settleId), selector);
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("contractBill.id", settleInfo.getContractBill().getId().toString());
		view.getSelector().add("number");
		view.getSelector().add("amount");
		view.getSelector().add("balanceAmount");
		view.getSelector().add("state");
		ContractChangeBillCollection changes=ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(view);
		BigDecimal total=FDCHelper.ZERO;
		for(Iterator iter=changes.iterator();iter.hasNext();){
			ContractChangeBillInfo change=(ContractChangeBillInfo)iter.next();
			if(change.getState()!=FDCBillStateEnum.VISA){
				throw new EASBizException(
						new NumericExceptionSubItem("120","�����ָ�����"+change.getNumber()+"�� ״̬Ϊ����"+change.getState() +"�� ���ܽ��б������,����Ϊ����ǩ֤��״̬")
				);
			}
			total=total.add(FDCHelper.toBigDecimal(change.getAmount()));
		}
		if(total.compareTo(FDCHelper.ZERO)==0){
			return;
		}
		BigDecimal tempAmt=FDCHelper.toBigDecimal(settleInfo.getSettlePrice()).subtract(FDCHelper.toBigDecimal(settleInfo.getContractBill().getAmount()));
		tempAmt=tempAmt.subtract(FDCHelper.toBigDecimal(total));
		BigDecimal settleDifAmt=FDCHelper.ZERO;
		BigDecimal balanceAmt=FDCHelper.ZERO;
		SelectorItemCollection updateSelector=new SelectorItemCollection();
		updateSelector.add("balanceAmount");
		updateSelector.add("hasSettled");
		updateSelector.add("settleTime");
		Date settleTime=new Date();
		for(Iterator iter=changes.iterator();iter.hasNext();){
			ContractChangeBillInfo change=(ContractChangeBillInfo)iter.next();
			settleDifAmt=tempAmt.multiply(FDCHelper.toBigDecimal(change.getAmount()));
			settleDifAmt=FDCHelper.divide(settleDifAmt, total, 2, BigDecimal.ROUND_HALF_UP);
//			settleDifAmt=settleDifAmt.divide(total, 2, BigDecimal.ROUND_HALF_UP);
			balanceAmt=settleDifAmt.add(FDCHelper.toBigDecimal(change.getAmount()));
			change.setBalanceAmount(FDCHelper.toBigDecimal(balanceAmt,2));
			change.setHasSettled(true);
			change.setSettleTime(settleTime);
			ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, updateSelector);
			ConChangeSplitFactory.getLocalInstance(ctx).autoSplit(new ObjectUuidPK(change.getId()));
		}
	}
	
	//������ʱ���༭�����ʼ���ݴ����ȷ���
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		
		Map initMap = super._fetchInitData(ctx,paramMap);		
		
		//��ͬ����
		String contractBillId = (String)paramMap.get("contractBillId");
		if(contractBillId!=null && initMap.get(FDCConstants.FDC_INIT_CONTRACT)==null){
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("id");
			selectors.add("number");
			selectors.add("name");
			selectors.add("exRate");
			selectors.add("orgUnit.id");
			selectors.add("currency.number");
			selectors.add("currency.name");
			selectors.add("CU.id");
			selectors.add("grtRate");
			selectors.add("curProject.id");
			selectors.add("isCoseSplit");
	
			selectors.add("contractType.longNumber");
			selectors.add("respDept.number");
			
			ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
			getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selectors);
			initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
		}
		
		return initMap;
	}
	
	   private SelectorItemCollection getSic(){
			// �˹���Ϊ��ϸ��Ϣ����
	        SelectorItemCollection sic = new SelectorItemCollection();
	        sic.add(new SelectorItemInfo("id"));
	        sic.add(new SelectorItemInfo("number"));
	        sic.add(new SelectorItemInfo("period.id"));
	        sic.add(new SelectorItemInfo("period.beginDate"));	
	        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
	        sic.add(new SelectorItemInfo("curProject.id"));
	        sic.add(new SelectorItemInfo("curProject.displayName"));	
	        
	        return sic;
	    }
	    
		//�ύʱУ�鵥���ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ
	    private void checkBillForSubmit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
			
			//�������ڵ�ǰ�ɱ��ڼ�֮ǰ
	    	ContractSettlementBillInfo contractSettleBill = (ContractSettlementBillInfo)model;
			
			//�Ƿ����ò���һ�廯
			String comId = null;
			if( contractSettleBill.getCurProject().getFullOrgUnit()==null){
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("fullOrgUnit.id");
				CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx)
						.getCurProjectInfo(new ObjectUuidPK(contractSettleBill.getCurProject().getId().toString()),sic);
				
				comId = curProject.getFullOrgUnit().getId().toString();
			}else{
				comId = contractSettleBill.getCurProject().getFullOrgUnit().getId().toString();
			}
			
			boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
			//��Ϊ�½�ֻ����˳ɱ���ģ����Դ˴������Ʋ������
			if(isInCore&&contractSettleBill.isIsCostSplit()){
				PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,contractSettleBill.getCurProject().getId().toString(),true);
				if(contractSettleBill.getPeriod()!=null && contractSettleBill.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
					//�����ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ CNTPERIODBEFORE
					throw new ContractException(ContractException.CNTPERIODBEFORE);
				}
				
				
				if(contractSettleBill.getContractBill()!=null){
			    	// �˹���Ϊ��ϸ��Ϣ����
			        SelectorItemCollection sic = new SelectorItemCollection();
			        sic.add(new SelectorItemInfo("period.beginDate"));	

			        ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractSettleBill.getContractBill().getId().toString()),sic);
					//��鹦���Ƿ��Ѿ�������ʼ��

					if(contractSettleBill.getPeriod()!=null && 
							contractSettleBill.getPeriod().getBeginDate().before(contractBillInfo.getPeriod().getBeginDate())){
						//�����ڼ䲻���ں�ͬ�ĵ�ǰ�ڼ�֮ǰ CNTPERIODBEFORE
						throw new ContractException(ContractException.CNTPERIODBEFORECON);
					}
				}
			}
		}
		
		//���У��
		private void checkBillForAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
			if(true){
				return;//����ҪУ�飬����ʱ�Ѵ����ڼ�
			}
			/*ContractSettlementBillInfo model = (ContractSettlementBillInfo)billInfo;

	        if(model==null || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null){
	        	model= this.getContractSettlementBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
	        }

			//��鹦���Ƿ��Ѿ�������ʼ��
			String comId = model.getCurProject().getFullOrgUnit().getId().toString();
				
			//�Ƿ����ò���һ�廯
			boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
			if(isInCore){
				String curProject = model.getCurProject().getId().toString();	
				//�ɱ��Ѿ��½�
				PeriodInfo costPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
				if(model.getPeriod()!=null && model.getPeriod().getBeginDate().after(costPeriod.getEndDate())){
					throw new  ContractException(ContractException.AUD_AFTERPERIOD,new Object[]{model.getNumber()});
				}
				
//				PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,false);
//				if(bookedPeriod.getBeginDate().after(finPeriod.getEndDate())){
//					throw new  ContractException(ContractException.AUD_FINNOTCLOSE,new Object[]{model.getNumber()});
//				}	
			}*/
		}
		//���У��
		private void checkBillForUnAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
			if(true){
				return;//
			}
			/*ContractSettlementBillInfo model = (ContractSettlementBillInfo)billInfo;

	        if(model==null || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null){
	        	model= this.getContractSettlementBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
	        }
			//��鹦���Ƿ��Ѿ�������ʼ��
			String comId = model.getCurProject().getFullOrgUnit().getId().toString();
				
			//�Ƿ����ò���һ�廯
			boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
			if(isInCore){
				String curProject = model.getCurProject().getId().toString();

				//�����ڼ��ڹ�����Ŀ��ǰ�ڼ�֮ǰ�����ܷ����
				PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
				if(model.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
					throw new  ContractException(ContractException.CNTPERIODBEFORE);
				}	
				
//				if(ProjectPeriodStatusUtil._isEnd(ctx,curProject)){
//					throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASEND,new Object[]{model.getCurProject().getDisplayName()});
//				}	
			}*/
		}
		
	private List getContractIds(Context ctx,String[] pk) throws BOSException, SQLException{
		List contractIds= null;
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fcontractBillid from T_CON_ContractSettlementBill where ");
		builder.appendParam("fid", pk);
		IRowSet rowSet = builder.executeQuery();
		if(rowSet==null||rowSet.size()<1){
			return null;
		}
		contractIds=new ArrayList(rowSet.size());
		
		while(rowSet.next()){
			String contractId = rowSet.getString("fcontractBillid");
			contractIds.add(contractId);

		}
		
		return contractIds;
	}

	private void reComContractSettlementBill(Context ctx,List contractIds) throws BOSException, EASBizException	{
			if(contractIds!=null){
				//�����ۼ�
				ContractSettlementBillCollection col = null;
				ContractSettlementBillInfo info = null;			
				
				PayRequestBillCollection payCol = null;
				PayRequestBillInfo payInfo=null;
				 BigDecimal B100 = new BigDecimal("100");
//				info.getContractBill()
				for(int i=0;i<contractIds.size();i++){
					String contractId = (String)contractIds.get(i);
						
					//������㵥
					{
						EntityViewInfo view=new EntityViewInfo();
						FilterInfo filter=new FilterInfo();
						view.setFilter(filter);
						filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
						SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
						sorterItemInfo.setSortType(SortType.ASCEND);
						view.getSorter().add(sorterItemInfo);
						
						view.getSelector().add("isFinalSettle");
						view.getSelector().add("createTime");
						view.getSelector().add("originalAmount");
						view.getSelector().add("settlePrice");
						view.getSelector().add("curOriginalAmount");
						view.getSelector().add("curSettlePrice");
						view.getSelector().add("totalOriginalAmount");
						view.getSelector().add("totalSettlePrice");
						//���޽�
						view.getSelector().add("qualityGuaranteRate");
						view.getSelector().add("qualityGuarante");
						
						col = this.getContractSettlementBillCollection(ctx,view);
						if(col.size()>0){
							for(int j=0;j<col.size();j++){
								info=col.get(j);
								if(info.getQualityGuaranteRate()==null){
									info.setQualityGuaranteRate(GlUtils.zero);
								}
								if(j==0){
									info.setTotalOriginalAmount(info.getCurOriginalAmount());
									info.setTotalSettlePrice(info.getCurSettlePrice());
		
									info.setOriginalAmount(info.getCurOriginalAmount());
									info.setSettlePrice(info.getCurSettlePrice());
									
									info.setQualityGuarante(
											FDCHelper.toBigDecimal(info.getQualityGuaranteRate().multiply(info.getTotalSettlePrice()).divide(B100,BigDecimal.ROUND_HALF_UP,2),2));
								}else{
									BigDecimal oriAmount = col.get(j-1).getTotalOriginalAmount();
									BigDecimal settlePrice = col.get(j-1).getTotalSettlePrice();
									
									if(BooleanEnum.TRUE.equals(info.getIsFinalSettle())){
										oriAmount = info.getCurOriginalAmount().add(oriAmount);
										settlePrice = info.getCurSettlePrice().add(settlePrice);
										info.setTotalOriginalAmount(oriAmount);
										info.setTotalSettlePrice(settlePrice);
		
										info.setOriginalAmount(oriAmount);
										info.setSettlePrice(settlePrice);
									}else{
										oriAmount = info.getCurOriginalAmount().add(oriAmount);
										settlePrice = info.getCurSettlePrice().add(settlePrice);
										
										info.setTotalOriginalAmount(oriAmount);
										info.setTotalSettlePrice(settlePrice);
		
										info.setOriginalAmount(info.getCurOriginalAmount());
										info.setSettlePrice(info.getCurSettlePrice());
									}
								}
								
								_updatePartial(ctx, info, view.getSelector());
								
							}
						}
					}
					//���㸶�����뵥
					{
						EntityViewInfo view=new EntityViewInfo();
						FilterInfo filter=new FilterInfo();
						view.setFilter(filter);
						filter.getFilterItems().add(new FilterItemInfo("contractId",contractId));
						SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
						sorterItemInfo.setSortType(SortType.ASCEND);
						view.getSorter().add(sorterItemInfo);

						view.getSelector().add("createTime");
						view.getSelector().add("totalSettlePrice");
					
						payCol = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
						
						if(payCol.size()>0){
							EntityViewInfo settView=new EntityViewInfo();
							
							settView.getSelector().add("curOriginalAmount");
							settView.getSelector().add("curSettlePrice");
							
							for(int j=0;j<payCol.size();j++){
								payInfo=payCol.get(j);
								Timestamp time = payInfo.getCreateTime();
								
								FilterInfo settFilter=new FilterInfo();
								settView.setFilter(settFilter);
								settFilter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
								settFilter.getFilterItems().add(new FilterItemInfo("createTime",time,CompareType.LESS_EQUALS));	
								
								col = this.getContractSettlementBillCollection(ctx,settView);
								BigDecimal settlePrice =  GlUtils.zero;
								if(col.size()>0){
									BigDecimal oriAmount = GlUtils.zero;								
									
									for(int kk=0;kk<col.size();kk++){
										info=col.get(kk);
										
										oriAmount = oriAmount.add(info.getCurOriginalAmount());
										settlePrice = settlePrice.add(info.getCurSettlePrice());
									}												
								}
									
								payInfo.setTotalSettlePrice(settlePrice);					
								
								PayRequestBillFactory.getLocalInstance(ctx).updatePartial(payInfo, view.getSelector());
							}
						}
						
					}
				}
			}
		}
	
	//��������ظ�����ȡ����
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, EASBizException
	{
		handleIntermitNumberForReset(ctx, info);
	}

	protected void _split(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		
	}
	
	/**
	 * ��������δ����ı��ָ������Զ����㣬��������д���������У������½���״̬
	 * @param ctx				������<p>
	 * @param model				��ͬ���㵥
	 * @throws BOSException
	 * @throws EASBizException
	 * @author zhiyuan_tang 2010/07/27
	 */
	protected void autoSettlementChangeBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		//��ȡ��ͬ���㵥
		ContractSettlementBillInfo contractSettlementBill = (ContractSettlementBillInfo)model;
		//��ȡ���㵥��Ӧ�ĺ�ͬ
		ContractBillInfo contractBill =  contractSettlementBill.getContractBill();
		//���ݺ�ͬ��ȡ���е�δ����ı��ָ�
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractBill",contractBill.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("hasSettled", Boolean.FALSE));
		//������������� ֻ��ǩ֤�Ĳ��ܽ��� add by zhiqiao_yang at 2010-10-14
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.VISA));
//		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED, CompareType.NOTEQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED, CompareType.NOTEQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING, CompareType.NOTEQUALS));
		view.getSelector().add("*");
//		view.getSelector().add("number");
		ContractChangeBillCollection contractChangeCol = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(view);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("oriBalanceAmount");
		selector.add("balanceAmount");
		selector.add("hasSettled");
		selector.add("settleTime");
		ContractChangeBillInfo contractchageBill = null;
		for (int i = 0; i < contractChangeCol.size(); i++) {
			contractchageBill = contractChangeCol.get(i);
			contractchageBill.setOriBalanceAmount(contractchageBill.getOriginalAmount()); //����ԭ�ҽ��
			contractchageBill.setBalanceAmount(contractchageBill.getAmount());	//���㱾�ҽ��
			contractchageBill.setHasSettled(true);	//����״̬
			contractchageBill.setSettleTime(Calendar.getInstance().getTime());
			ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(contractchageBill, selector);
		}
	}

	protected boolean _autoDelSplit(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		//��˵Ľ��㵥�����Զ�ɾ�����
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("state"));
        ContractSettlementBillInfo settlementBillInfo = 
        	ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillInfo(pk, sic);
		if(FDCBillStateEnum.AUDITTED.equals(settlementBillInfo.getState())) {
			return false;
		}
		
		//ɾ�����
		CostSplitBillAutoAuditor.autoDelete(ctx, pk, "T_CON_SettlementCostSplit", "FSettlementBillID");
		//�ǳɱ����
		CostSplitBillAutoAuditor.autoDelete(ctx, pk, "T_CON_SettNoCostSplit", "FSettlementBillID");
		String settlementId = pk.toString();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		builder.appendSql("update T_CON_ContractSettlementBill set fsplitstate='1NOSPLIT' where fid=?");
		builder.addParam(settlementId);
		builder.execute();
		return true;
		
	}

	protected boolean _isAllSplit(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("splitState"));
        sic.add(new SelectorItemInfo("orgUnit.id"));
        ContractSettlementBillInfo settlementBillInfo = this.getContractSettlementBillInfo(ctx,pk,sic);		
		
		return CostSplitStateEnum.ALLSPLIT.equals(settlementBillInfo.getSplitState());
	}

	@Override
	protected void _costIndex(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		
	}
}