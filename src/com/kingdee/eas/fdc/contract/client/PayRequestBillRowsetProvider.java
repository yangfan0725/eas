package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.multiapprove.MultiApproveCollection;
import com.kingdee.eas.base.multiapprove.MultiApproveFactory;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.DeductTypeFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.FeeTypeInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;
import com.kingdee.util.UuidException;

public class PayRequestBillRowsetProvider extends FDCBillDataProvider {
	private static final Logger logger = Logger.getLogger(PayRequestBillRowsetProvider.class);

	static public final String[] col = new String[] { "curProject.name",
			"curProject.number", "useDepartment.name", "useDepartment.number",
			"contractNo", "bookedDate", "bizDate", "number", "payDate",
			"period.periodNumber", "period.number", "paymentType.name",
			"paymentType.number", "isDifferPlace", "settlementType.name",
			"settlementType.number", "recBank", "description", "supplier.name",
			"realSupplier.name", "recAccount", "usage", "currency.name",
			"currency.number", "exchangeRate", "attachment", "originalAmount",
			"paymentProportion", "amount", "grtAmount", "completePrjAmt",
			"capitalAmount", "moneyDesc", "urgentDegree", "isPay",
			"createTime", "creator.name", "creator.number", "auditTime",
			"auditor.number", "auditor.name", "contractName", "contractPrice",
			"latestPrice", "addProjectAmt", "changeAmt", "payedAmt",
			"payPartAMatlAmt", "projectPriceInContract", "scheduleAmt",
			"curPlannedPayment", "curBackPay", "paymentPlan", "curReqPercent",
			"allReqPercent", "imageSchedule", "curPaid", "prjAllReqAmt",
			"addPrjAllReqAmt", "payPartAMatlAllReqAmt", "lstPrjAllReqAmt",
			"lstAddPrjAllReqAmt", "lstAMatlAllReqAmt", "contractId",
			"hasPayoff", "lstPrjAllPaidAmt", "lstAddPrjAllPaidAmt",
			"lstAMatlAllPaidAmt", "costAmount", "settleAmt", "payTimes",
			"lstGuerdonPaidAmt", "lstGuerdonReqAmt", "guerdonAmt",
			"allGuerdonReqAmt", "allGuerdonPaidAmt", "lstCompensationPaidAmt",
			"lstCompensationReqAmt", "compensationAmt",
			"allCompensationReqAmt", "allCompensationPaidAmt", "prjAllPaidAmt",
			"deductType", "lstDeductPaidAmt", "lstDeductReqAmt", "deductAmt",
			"allDeductReqAmt", "allDeductPaidAmt", "restAmount",
			"lstRealPaidAmt", "allPartAMatlAllPaidAmt", "lstDeductSumPaidAmt",
			"lstDeductSumReqAmt", "deductSumAmt", "allDeductSumReqAmt",
			"allDeductSumPaidAmt",
			"lstRealReqAmt",
			"allRealReqAmt",
			"allRealPaidAmt",
			"auditResult",
			"orgName",
			"oldProjNumber",
			"paidDataAmount",
			"projNameWithoutOrg",
			"paidDetail",
			
			"id",
			"contractBillAmount",//��ͬԭ�ҽ��
			"contractBillType",//��ͬ����
			"totalSettlePrice",//��ʵ�ֲ�ֵ
			"allCompletePrjAmt",
			"LstPrjAllPaidAmtAndthis",
			"latestPriceSub",
			// ����:�������뵥�״�ʱ֧�ֻ�ȡ�����ֶε�ԭ��  by Cassiel_peng  2009-10-9
			"contractOriPrice",//��ͬ���ԭ��
			"changeOriAmt",//���ǩ֤ԭ��
			"latestOriPrice",//�������ԭ��
			"grtOriAmount",//���޽�ԭ��
			"lstPrjAllPaidOriAmt",//���ں�ͬ���ۼ�ʵ��ԭ��
			"lstPrjAllReqOriAmt",//��ͬ�ڹ��̿������ۼ�����ԭ��
			"settleOriAmt",//�������
			"projectPriceInContractOri",//��ͬ�ڹ��̿�ԭ�ң����ڷ�����
			"guerdonOriAmt",//�������ڷ���ԭ��
			"compensationOriAmt",//ΥԼ���ڷ���ԭ��
			"deductOriAmt",//�ۿ�ڷ���ԭ��
			"payPartAMatlOriAmt",//�׹��ı��ڷ���ԭ��
			"thisTimeReqLocalAmt",//�������뱾�ҽ��,
			"process",//�����������
			"invoiceNumber", //��Ʊ��     
			"invoiceDate", //��Ʊ��  
			"invoiceAmt", //���η�Ʊ���      Added by Owen_wen 2010-09-24
			"allInvoiceAmt", //�ۼƷ�Ʊ���
			"conGrtAmount",//��ͬ���޽�
			"conGrtOriAmount",//��ͬ���޽�ԭ��
			"conGrtRate",//��ͬ���޽����
			"applier",
			"costedDept",
			"costedCompany",
			"payBillType",
			"payContentType",
			"requestAmount",
			"bgItem.number",
			"bgItem.name",
			"accountView.number",
			"accountView.name",
			"accountView.longNumber",
			"accountView.longName",
			"agentCompany.name",
			"payNo","appAmount"
	};

	public static String printStringHelper(Object o) {
		if (o == null)
			return "";
		else if(o instanceof BigDecimal){
			if(FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(o))==0)
				return "";
			else
				return String.valueOf(((BigDecimal)o).setScale(2,BigDecimal.ROUND_HALF_UP));
		}
			return String.valueOf(o);
	}

	public static String printStringHelper(boolean o) {
		return o ? "��" : "��";
	}

	private PayRequestBillInfo fdcBill = null;
	private CurProjectInfo curProjectInfo = null;
	private HashMap bindCellMap =null;
	private ContractBillInfo contractBill = null;
	
	public PayRequestBillRowsetProvider(PayRequestBillInfo editData,HashMap bindCellMap,CurProjectInfo curProjectInfo,ContractBillInfo contractBill) {
		super(editData.getId().toString(),null);
		fdcBill = editData;
		this.bindCellMap = bindCellMap;
		this.curProjectInfo = curProjectInfo;
		this.contractBill = contractBill;
		initAllCompletePrjAmt();//��ʼ��ʱһ���Ը�ֵ
	}
	private BigDecimal getContractOriAmt(String contractId) {
		//��ͬ���
		BigDecimal contractOriAmt=FDCHelper.ZERO;
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("originalAmount");
		
		try {
			ContractBillInfo contractBill = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
			contractOriAmt=contractBill.getOriginalAmount();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (UuidException e) {
			e.printStackTrace();
		}
		return contractOriAmt;
	}
	
	private BigDecimal getChangeOriAmt(String contractId){

		BigDecimal conChangeOriAmt=FDCHelper.ZERO;
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("hasSettled");
		view.getSelector().add("oriBalanceAmount");
		view.getSelector().add("originalAmount");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
		view.setFilter(filter);
		ContractChangeBillCollection changeBillColl;
		try {
			changeBillColl = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(view);
			ContractChangeBillInfo billInfo;
			//�������ۼ�=δ������+�ѽ�����
			for (Iterator iter = changeBillColl.iterator(); iter.hasNext();)
			{
				billInfo = (ContractChangeBillInfo) iter.next();
				if(billInfo.isHasSettled()){
					conChangeOriAmt = conChangeOriAmt.add(FDCHelper.toBigDecimal(billInfo.getOriBalanceAmount()));
				}else{
					conChangeOriAmt = conChangeOriAmt.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount()));
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return conChangeOriAmt;
	}
	
	private BigDecimal getGuerdonOriAmt(String contractId){
		BigDecimal guerdonBillOriAmt=FDCHelper.ZERO;
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("originalAmount");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.id",fdcBill.getId().toString()));
		/**
		 * Ӧ��ȡ��������ԭ�ҽ���ĳһ��ͬ���ۼ������ԭ�ҽ�� by Cassiel_peng 2009-10-21
		 */
		/*filter.getFilterItems().add(new FilterItemInfo("contract.id",contractId));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
		filter.getFilterItems().add(new FilterItemInfo("isGuerdoned",Boolean.TRUE));*/
		view.setFilter(filter);
		try {
//			GuerdonBillCollection guerdonBillColl = GuerdonBillFactory.getRemoteInstance().getGuerdonBillCollection(view);
			GuerdonOfPayReqBillCollection guerdonBillColl = GuerdonOfPayReqBillFactory.getRemoteInstance().getGuerdonOfPayReqBillCollection(view);
			for (Iterator iter = guerdonBillColl.iterator(); iter.hasNext();)
			{
				GuerdonOfPayReqBillInfo guerdonBill = (GuerdonOfPayReqBillInfo) iter.next();
				guerdonBillOriAmt = guerdonBillOriAmt.add(FDCHelper.toBigDecimal(guerdonBill.getOriginalAmount()));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return guerdonBillOriAmt;
	}
	private BigDecimal getCompensationOriAmt(String contractId){
		BigDecimal compensationBillOriAmt=FDCHelper.ZERO;
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("originalAmount");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.id",fdcBill.getId().toString()));
		/**
		 * Ӧ��ȡ��������ԭ�ҽ���ĳһ��ͬ���ۼ������ԭ�ҽ�� by Cassiel_peng 2009-10-21
		 */
		/*filter.getFilterItems().add(new FilterItemInfo("contract.id",contractId));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
		filter.getFilterItems().add(new FilterItemInfo("isCompensated",Boolean.TRUE));*/
		view.setFilter(filter);
		try {
			CompensationOfPayReqBillCollection compenstionColl = CompensationOfPayReqBillFactory.getRemoteInstance().getCompensationOfPayReqBillCollection(view);
//			CompensationBillCollection compenstionColl = CompensationBillFactory.getRemoteInstance().getCompensationBillCollection(view);
			for (Iterator iter = compenstionColl.iterator(); iter.hasNext();)
			{
				CompensationOfPayReqBillInfo billInfo = (CompensationOfPayReqBillInfo) iter.next();
				compensationBillOriAmt = compensationBillOriAmt.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount()));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return compensationBillOriAmt;
	}
	private BigDecimal getDeductOriAmt(String contractId) {
		BigDecimal DeductOfPayReqBillOriAmt = FDCHelper.ZERO;
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("originalAmount");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.id",fdcBill.getId().toString()));
		view.setFilter(filter);
		try {
			DeductOfPayReqBillCollection duductOfPayReqBillColl = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
			for (Iterator iter = duductOfPayReqBillColl.iterator(); iter.hasNext();)
			{
				DeductOfPayReqBillInfo billInfo = (DeductOfPayReqBillInfo) iter.next();
				DeductOfPayReqBillOriAmt = DeductOfPayReqBillOriAmt.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount()));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return DeductOfPayReqBillOriAmt;
	}
	private void insert(DynamicRowSet drs,DeductOfPayReqBillInfo info,Map deductTypeSum) throws Exception{
	
		BigDecimal sumLstDeductPaid = (BigDecimal)deductTypeSum.get("sumLstDeductPaid");
		BigDecimal sumLstDeductReq =  (BigDecimal)deductTypeSum.get("sumLstDeductReq");
		BigDecimal sumDeduct =  (BigDecimal)deductTypeSum.get("sumDeduct");
		BigDecimal sumAllDeductReq =  (BigDecimal)deductTypeSum.get("sumAllDeductReq");
		BigDecimal sumAllDeductPaid = (BigDecimal)deductTypeSum.get("sumAllDeductPaid");
		
		// �ڴ˰����ݴ��ݸ�ʵ���࣬drs.updateString(key,value) key
		// ָ�����״�ģ���ж�����ֶα��룬Valueָ���ǵ�ǰ���ݵ�����ֵ
		String orgName=((CurProjectInfo)fdcBill.getCurProject()).getFullOrgUnit().getName();
		String curProjectName = curProjectInfo.getDisplayName();		
		//ȡ��������Ҫ��ֻȡ��Ŀ����
		//2008-07-22
		String projNameWithoutOrg = curProjectName.replace('_', '\\');
		curProjectName = orgName+"\\"+curProjectName.replace('_', '\\');
		
		drs.updateString("invoiceNumber", printStringHelper(fdcBill.getInvoiceNumber()));//��Ʊ��
		drs.updateString("invoiceDate", printStringHelper(fdcBill.getInvoiceDate()));//��Ʊ��
		drs.updateString("invoiceAmt", printStringHelper(fdcBill.getInvoiceAmt()));//���η�Ʊ���     Added by Owen_wen 2010-09-24
		drs.updateString("allInvoiceAmt", printStringHelper(fdcBill.getAllInvoiceAmt()));//�ۼƷ�Ʊ���
		
		drs.updateString("orgName",orgName);
		
		drs.updateString("curProject.name", curProjectName);
		drs.updateString("projNameWithoutOrg", projNameWithoutOrg);
		drs.updateString("curProject.number", fdcBill.getCurProject()
				.getNumber());
		drs.updateString("useDepartment.name", fdcBill.getUseDepartment()
				.getName());
		drs.updateString("useDepartment.number", fdcBill.getUseDepartment()
				.getNumber());
		drs.updateString("contractNo", printStringHelper(fdcBill
				.getContractNo()));
		drs.updateString("bookedDate", printStringHelper(fdcBill
				.getBookedDate()));// ҵ������
		drs.updateString("number", printStringHelper(fdcBill.getNumber()));
		drs
				.updateString("payDate", printStringHelper(fdcBill
						.getPayDate()));
		drs.updateString("period.periodNumber", printStringHelper(fdcBill
				.getPeriod()));
		drs.updateString("period.number", fdcBill.getPeriod()==null?"":String.valueOf(fdcBill
				.getPeriod().getNumber()));
		drs.updateString("paymentType.name",
				fdcBill.getPaymentType() == null ? "" : fdcBill
						.getPaymentType().getName());
		drs.updateString("paymentType.number",
				fdcBill.getPaymentType() == null ? "" : fdcBill
						.getPaymentType().getNumber());
		drs.updateString("isDifferPlace", printStringHelper(fdcBill
				.getIsDifferPlace()));
		drs.updateString("settlementType.name",
				fdcBill.getSettlementType() == null ? "" : fdcBill
						.getSettlementType().getName());
		drs.updateString("settlementType.number", fdcBill
				.getSettlementType() == null ? "" : fdcBill
				.getSettlementType().getNumber());
		drs
				.updateString("recBank", printStringHelper(fdcBill
						.getRecBank()));
		drs.updateString("description", printStringHelper(fdcBill
				.getDescription()));
		drs.updateString("supplier.name",
				fdcBill.getSupplier() == null ? "" : fdcBill.getSupplier()
						.getName());
		drs.updateString("realSupplier.name",
				fdcBill.getRealSupplier() == null ? "" : fdcBill
						.getRealSupplier().getName());
		drs.updateString("recAccount", fdcBill.getRecAccount());
		drs.updateString("usage", fdcBill.getUsage());
		drs.updateString("currency.name",
				fdcBill.getCurrency() == null ? "" : fdcBill.getCurrency()
						.getName());
		drs.updateString("currency.number",
				fdcBill.getCurrency() == null ? "" : fdcBill.getCurrency()
						.getNumber());
		drs.updateString("exchangeRate", String.valueOf(fdcBill
				.getExchangeRate()));// 
		drs.updateString("attachment", String.valueOf(fdcBill
				.getAttachment()));
		drs.updateString("originalAmount", printStringHelper(fdcBill
				.getOriginalAmount()));
		drs.updateString("paymentProportion", printStringHelper(fdcBill
				.getPaymentProportion())+"%");
		drs.updateString("amount", printStringHelper(fdcBill.getAmount()));
		drs.updateString("grtAmount", printStringHelper(fdcBill
				.getGrtAmount()));
		//��ͬ���޽��
		if(contractBill!=null){
			drs.updateString("conGrtAmount", printStringHelper(contractBill.getGrtAmount()));
			drs.updateString("conGrtOriAmount", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(contractBill.getGrtAmount()), contractBill.getExRate(),4,BigDecimal.ROUND_HALF_UP)));
			drs.updateString("conGrtRate", printStringHelper(contractBill.getGrtRate()));
		}
		drs.updateString("completePrjAmt", fdcBill.getBoolean("isCompletePrjAmtVisible")?printStringHelper(fdcBill
				.getCompletePrjAmt()):"");
		drs.updateString("capitalAmount", printStringHelper(fdcBill
				.getCapitalAmount()));
		drs.updateString("moneyDesc", printStringHelper(fdcBill
				.getMoneyDesc()));
		drs.updateString("urgentDegree", printStringHelper(fdcBill
				.getUrgentDegree()));
		drs.updateString("isPay", printStringHelper(fdcBill.isIsPay()));
		drs.updateString("createTime", printStringHelper(fdcBill
				.getCreateTime()));
		drs.updateString("creator.name", fdcBill.getCreator().getName());
		drs
				.updateString("creator.number", fdcBill.getCreator()
						.getNumber());
		drs.updateString("auditTime", printStringHelper(fdcBill
				.getAuditTime()));
		drs.updateString("auditor.number",
				fdcBill.getAuditor() == null ? "" : fdcBill.getAuditor()
						.getNumber());
		drs.updateString("auditor.name", fdcBill.getAuditor() == null ? ""
				: fdcBill.getAuditor().getName());
		drs.updateString("contractName", printStringHelper(fdcBill
				.getContractName()));
		drs.updateString("contractPrice", printStringHelper(fdcBill
				.getContractPrice()));
		String contractId = fdcBill.getContractId();
		BigDecimal exRate = getConExRate(contractId);
		//�����ͬ��ԭ�ң��������Ϊԭ�� �����ͬΪ��ң���ȡ���  ----- ���߼������ϣ���Ϊ�Ѿ���ר�ŵ��ֶ�������ʾ������۵�ԭ��  by Cassiel_peng 2009-10-9
		if(exRate.compareTo(FDCHelper.ONE)==0 ||exRate.compareTo(FDCHelper.ZERO)==0){
			drs.updateString("latestPrice", printStringHelper(FDCHelper.toBigDecimal(fdcBill
					.getLatestPrice())));
		}
//			else{
//			drs.updateString("latestPrice", printStringHelper(FDCHelper.toBigDecimal(fdcBill
//					.getLatestPrice()).divide(exRate,4,BigDecimal.ROUND_HALF_UP)));
//		}

		drs.updateString("addProjectAmt", printStringHelper(fdcBill
				.getAddProjectAmt()));
		drs.updateString("changeAmt", printStringHelper(fdcBill
				.getChangeAmt()));
		drs.updateString("payedAmt", printStringHelper(fdcBill
				.getPayedAmt()));
		drs.updateString("payPartAMatlAmt", printStringHelper(fdcBill
				.getPayPartAMatlAmt()));
		drs.updateString("projectPriceInContract",
				printStringHelper(fdcBill.getProjectPriceInContract()));
		drs.updateString("scheduleAmt", printStringHelper(fdcBill
				.getScheduleAmt()));
		drs.updateString("curPlannedPayment", printStringHelper(fdcBill
				.getCurPlannedPayment()));
		drs.updateString("curBackPay", printStringHelper(fdcBill
				.getCurBackPay()));
		drs.updateString("paymentPlan", printStringHelper(fdcBill
				.getPaymentPlan()));
		// �������
		drs.updateString("payTimes", String.valueOf(fdcBill.getPayTimes()));
		
		drs.updateString("prjAllReqAmt", printStringHelper(fdcBill
				.getPrjAllReqAmt()));
		drs.updateString("addPrjAllReqAmt", printStringHelper(fdcBill
				.getAddPrjAllReqAmt()));
		drs.updateString("payPartAMatlAllReqAmt", printStringHelper(fdcBill
				.getPayPartAMatlAllReqAmt()));
		drs.updateString("lstPrjAllReqAmt", printStringHelper(fdcBill
				.getLstPrjAllReqAmt()));
		drs.updateString("lstAddPrjAllReqAmt", printStringHelper(fdcBill
				.getLstAddPrjAllReqAmt()));
		drs.updateString("lstAMatlAllReqAmt", printStringHelper(fdcBill
				.getLstAMatlAllReqAmt()));
		drs.updateString("contractId", fdcBill.getContractId());
		drs.updateString("hasPayoff", printStringHelper(fdcBill
				.isHasPayoff()));
		drs.updateString("lstPrjAllPaidAmt", printStringHelper(fdcBill
				.getLstPrjAllPaidAmt()));
		drs.updateString("lstAddPrjAllPaidAmt", printStringHelper(fdcBill
				.getLstAddPrjAllPaidAmt()));
		drs.updateString("lstAMatlAllPaidAmt", printStringHelper(fdcBill
				.getLstAMatlAllPaidAmt()));
		drs.updateString("costAmount", fdcBill.getBoolean("isCompletePrjAmtVisible")?printStringHelper(fdcBill
				.getCompletePrjAmt()):"");
		drs.updateString("settleAmt", printStringHelper(fdcBill
				.getSettleAmt()));
		// ��ͬ�ڸ��� ���������ۼ�ʵ�� = ���������ۼ��ۼ�ʵ�� 
		BigDecimal prjAllPaidAmt = (fdcBill.getLstPrjAllPaidAmt()!=null?fdcBill.getLstPrjAllPaidAmt():FDCHelper.ZERO);
		drs.updateString("prjAllPaidAmt", printStringHelper(prjAllPaidAmt));			
		
		// ���� ���������ۼ�ʵ��
		BigDecimal lstGuerdonPaidAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstGuerdonPaidAmt"))).getValue();				
		drs.updateString("lstGuerdonPaidAmt",printStringHelper(lstGuerdonPaidAmt));
		// ���� ���������ۼ�����
		BigDecimal lstGuerdonReqAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstGuerdonReqAmt"))).getValue();
		drs.updateString("lstGuerdonReqAmt", printStringHelper(lstGuerdonReqAmt));
		// ���� ���ڷ���				
		BigDecimal guerdonAmt = (BigDecimal)((ICell)( bindCellMap.get("guerdonAmt"))).getValue();
//			fdcBill.get("guerdonAmt")
		guerdonAmt = guerdonAmt!= null?guerdonAmt: FDCHelper.ZERO;
		drs.updateString("guerdonAmt", printStringHelper(guerdonAmt));
		// ���� ���������ۼ����� = ���� ���������ۼ����� + ���� ���ڷ���
		// ���� ���������ۼ�ʵ�� = ���� ���������ۼ�ʵ�� 
		BigDecimal allGuerdonReqAmt = guerdonAmt.add(lstGuerdonReqAmt!= null ? lstGuerdonReqAmt : FDCHelper.ZERO);
		BigDecimal allGuerdonPaidAmt = lstGuerdonPaidAmt!= null ?lstGuerdonPaidAmt: FDCHelper.ZERO;
		drs.updateString("allGuerdonReqAmt",printStringHelper(allGuerdonReqAmt));
		drs.updateString("allGuerdonPaidAmt",printStringHelper(allGuerdonPaidAmt));
		
		// ΥԼ ���������ۼ�ʵ��
		BigDecimal lstCompensationPaidAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstCompensationPaidAmt"))).getValue();
		drs.updateString("lstCompensationPaidAmt",printStringHelper(lstCompensationPaidAmt));
		
		// ΥԼ ���������ۼ�����
		BigDecimal lstCompensationReqAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstCompensationReqAmt"))).getValue();
		drs.updateString("lstCompensationReqAmt", printStringHelper(lstCompensationReqAmt));
		// ΥԼ ���ڷ���
		BigDecimal compensationAmt = (BigDecimal) ( (ICell)( bindCellMap.get("compensationAmt"))).getValue();
		compensationAmt = compensationAmt != null? compensationAmt: FDCHelper.ZERO;
		drs.updateString("compensationAmt", printStringHelper(compensationAmt));
		// ΥԼ ���������ۼ����� = ΥԼ ���������ۼ����� + ΥԼ ���ڷ���
		// ΥԼ ���������ۼ�ʵ�� = ΥԼ ���������ۼ�ʵ�� 				
		BigDecimal allCompensationReqAmt = compensationAmt.add(lstCompensationReqAmt!= null 
												? lstCompensationReqAmt: FDCHelper.ZERO);
		BigDecimal allCompensationPaidAmt = lstCompensationPaidAmt != null 
												? lstCompensationPaidAmt: FDCHelper.ZERO;
		drs.updateString("allCompensationReqAmt",	printStringHelper(allCompensationReqAmt));
		drs.updateString("allCompensationPaidAmt",printStringHelper(allCompensationPaidAmt));
		
		//��ʵ�ֲ�ֵ���ۼƽ���
		
		drs.updateString("totalSettlePrice",printStringHelper(fdcBill.getTotalSettlePrice()));
		
		//�ۿ���
		//�ۿ�����				
		if(info!=null){
			drs.updateString("deductType",info==null?"":info.getDeductType().getName());
			BigDecimal lstDeductPaidAmt = info.getAllPaidAmt()==null?FDCHelper.ZERO:info.getAllPaidAmt();
			BigDecimal lstDeductReqAmt  = info.getAllReqAmt()==null?FDCHelper.ZERO:info.getAllReqAmt();
			BigDecimal deductAmt        = info.getAmount()==null?FDCHelper.ZERO:info.getAmount();
			BigDecimal allDeductReqAmt  = lstDeductReqAmt.add(deductAmt);
			BigDecimal allDeductPaidAmt  = lstDeductPaidAmt;
			
			drs.updateString("lstDeductPaidAmt",printStringHelper(lstDeductPaidAmt));
			drs.updateString("lstDeductReqAmt",printStringHelper(lstDeductReqAmt));
			drs.updateString("deductAmt",printStringHelper(deductAmt));
			drs.updateString("allDeductReqAmt",printStringHelper(allDeductReqAmt));
			drs.updateString("allDeductPaidAmt",	printStringHelper(allDeductPaidAmt));
			
			
			//�ۿ�С��
			sumLstDeductPaid = sumLstDeductPaid.add(lstDeductPaidAmt);
			sumLstDeductReq = sumLstDeductReq.add(lstDeductReqAmt);
			sumDeduct = sumDeduct.add(deductAmt);
			sumAllDeductReq = sumAllDeductReq.add(allDeductReqAmt);
			sumAllDeductPaid = sumAllDeductPaid.add(allDeductPaidAmt);
			
			deductTypeSum.put("sumLstDeductPaid",sumLstDeductPaid);
			deductTypeSum.put("sumLstDeductReq",sumLstDeductReq);
			deductTypeSum.put("sumDeduct",sumDeduct);
			deductTypeSum.put("sumAllDeductReq",sumAllDeductReq);
			deductTypeSum.put("sumAllDeductPaid",sumAllDeductPaid);
		}
		
//		if(i==c.size()-1){
			drs.updateString("lstDeductSumPaidAmt",printStringHelper(sumLstDeductPaid));
			drs.updateString("lstDeductSumReqAmt",printStringHelper(sumLstDeductReq));
			drs.updateString("deductSumAmt",printStringHelper(sumDeduct));
			drs.updateString("allDeductSumReqAmt",printStringHelper(sumAllDeductReq));
			drs.updateString("allDeductSumPaidAmt",printStringHelper(sumAllDeductPaid));					
//		}	
		
		//�׹��� ���������ۼ�ʵ�� = �׹��� ���������ۼ�ʵ�� 
		BigDecimal allPartAMatlAllPaidAmt = (fdcBill.getLstAMatlAllPaidAmt()!=null?fdcBill
				.getLstAMatlAllPaidAmt():FDCHelper.ZERO);
		// �׹��� ���������ۼ�ʵ��
				//.add(fdcBill.getPayPartAMatlAmt()!=null?fdcBill.getPayPartAMatlAmt()
						//:FDCHelper.ZERO);// �׹��� ���ڷ���
		drs.updateString("allPartAMatlAllPaidAmt",printStringHelper(allPartAMatlAllPaidAmt));
		
		//ʵ���� ���������ۼ�ʵ�� = ��ͬ���̿� + ���� - ΥԼ�� - �ۿ�С�� - �׹���
		BigDecimal lstRealPaidAmt = fdcBill.getLstPrjAllPaidAmt()!=null?fdcBill.getLstPrjAllPaidAmt():FDCHelper.ZERO;
		BigDecimal tempLstRealPaidAmt = lstRealPaidAmt.add(FDCHelper.toBigDecimal(lstGuerdonPaidAmt))//����
		.subtract(FDCHelper.toBigDecimal(lstCompensationPaidAmt))//ΥԼ
		.subtract(FDCHelper.toBigDecimal(sumLstDeductPaid))//�ۿ�
		.subtract(fdcBill.getLstAMatlAllPaidAmt() != null ? fdcBill
						.getLstAMatlAllPaidAmt() : FDCHelper.ZERO);//�׹���
		drs.updateString("lstRealPaidAmt", printStringHelper(tempLstRealPaidAmt));
		
		//ʵ���� ���������ۼ����� = ��ͬ���̿� + ���� - ΥԼ�� - �ۿ�С�� - �׹���
		BigDecimal lstRealReqAmt = fdcBill.getLstPrjAllReqAmt()!=null?fdcBill.getLstPrjAllReqAmt():FDCHelper.ZERO;
		BigDecimal tempLstRealReqAmt = lstRealReqAmt.add(FDCHelper.toBigDecimal(lstGuerdonReqAmt))//����
		.subtract(FDCHelper.toBigDecimal(lstCompensationReqAmt))//ΥԼ
		.subtract(FDCHelper.toBigDecimal(sumLstDeductReq))//�ۿ�
		.subtract(fdcBill.getLstAMatlAllReqAmt() != null ? fdcBill
						.getLstAMatlAllReqAmt() : FDCHelper.ZERO);//�׹���
		drs.updateString("lstRealReqAmt", printStringHelper(tempLstRealReqAmt));
		
		
		//ʵ���� ���ڷ��� = ��ͬ���̿� + ���� - ΥԼ�� - �ۿ�С�� - �׹���
		BigDecimal projectPriceInContract = fdcBill.getProjectPriceInContract()!=null?fdcBill.getProjectPriceInContract():FDCHelper.ZERO;
//		BigDecimal tempCurPaid = fdcBill.getCurPaid()!= null ? fdcBill.getCurPaid(): FDCHelper.ZERO;
		BigDecimal tempCurPaid = projectPriceInContract.add(guerdonAmt)//����
		.subtract(compensationAmt)//ΥԼ
		.subtract(sumDeduct)//�ۿ�
		.subtract(fdcBill.getPayPartAMatlAmt() != null ? fdcBill
						.getPayPartAMatlAmt() : FDCHelper.ZERO);//�׹���
		drs.updateString("curPaid", printStringHelper(tempCurPaid));
		// ///
		
		
		//ʵ���� ���������ۼ����� = ��ͬ���̿� + ���� - ΥԼ�� - �ۿ�С�� - �׹���
		BigDecimal prjAllReqAmt = fdcBill.getPrjAllReqAmt()!=null?fdcBill.getPrjAllReqAmt():FDCHelper.ZERO;
		BigDecimal allRealReqAmt = prjAllReqAmt.add(allGuerdonReqAmt)//����
				.subtract(	allCompensationReqAmt)//ΥԼ
				.subtract(sumAllDeductReq)//�ۿ�
				.subtract(fdcBill.getPayPartAMatlAllReqAmt() != null ? fdcBill
								.getPayPartAMatlAllReqAmt()
								: FDCHelper.ZERO);//�׹���
		drs.updateString("allRealReqAmt",printStringHelper(allRealReqAmt));
		
		// ʵ���� ���������ۼ�ʵ�� = ��ͬ���̿� + ���� - ΥԼ�� - �ۿ�С�� - �׹���
		BigDecimal lstPrjAllPaidAmt =  FDCHelper.toBigDecimal(fdcBill.getLstPrjAllPaidAmt());
		BigDecimal allRealPaidAmt = lstPrjAllPaidAmt
				.add(allGuerdonPaidAmt)//����
				.subtract(allCompensationPaidAmt)//ΥԼ
				.subtract(sumAllDeductPaid)//�ۿ�
				.subtract(allPartAMatlAllPaidAmt);//�׹���
		drs.updateString("allRealPaidAmt",printStringHelper(allRealPaidAmt));
		
		// ��� = ������� - ��ͬ�ڸ��� ���������ۼ�ʵ��
		BigDecimal restAmount = (fdcBill
		.getLatestPrice() == null ? FDCHelper.ZERO : fdcBill.getLatestPrice())				
		.subtract(prjAllPaidAmt);//��ͬ�ڸ��� ���������ۼ�ʵ��
		
		drs.updateString("restAmount",printStringHelper(restAmount));
		// ��������% = ʵ���� ���ڷ��� / �������
		BigDecimal tempCurReqPercent = fdcBill.getLatestPrice() == null
				|| fdcBill.getLatestPrice().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO
				: tempCurPaid.divide(fdcBill.getLatestPrice(), 4,
						BigDecimal.ROUND_HALF_UP);
//		BigDecimal tempCurReqPercent = fdcBill.getCurReqPercent()!=null?
//				fdcBill.getCurReqPercent():FDCHelper.ZERO;
		drs.updateString("curReqPercent",
				printStringHelper((tempCurReqPercent.multiply(FDCConstants.ONE_HUNDRED)).setScale(2)));
		
		// �ۼ�����% = ʵ���� ���������ۼ����� / �������
		BigDecimal tempAllCurReqPercent = fdcBill.getLatestPrice() == null
				|| fdcBill.getLatestPrice().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO
				: allRealReqAmt.divide(fdcBill.getLatestPrice(), 4,
						BigDecimal.ROUND_HALF_UP);
		
//		BigDecimal tempAllCurReqPercent = fdcBill.getAllReqPercent()!=null?
//				fdcBill.getAllReqPercent():FDCHelper.ZERO;
		drs.updateString("allReqPercent",
				printStringHelper((tempAllCurReqPercent.multiply(FDCConstants.ONE_HUNDRED)).setScale(2)));
		// ///
		drs.updateString("imageSchedule", printStringHelper(fdcBill
				.getImageSchedule()));
		drs.updateString("allCompletePrjAmt", printStringHelper(fdcBill.get("allCompletePrjAmt")));
		// ��ͬ�ڹ��̿��ֹ����ʵ��+��������
		BigDecimal LstPrjAllPaidAmtAndthis = FDCHelper.add(fdcBill.getLstPrjAllPaidAmt(),fdcBill.getProjectPriceInContract());
		drs.updateString("LstPrjAllPaidAmtAndthis",printStringHelper(LstPrjAllPaidAmtAndthis));
		// ��ͬ�������-��ͬ�ڹ��̿��ֹ����ʵ��-��������
		BigDecimal latestPriceSub = FDCHelper.subtract(fdcBill.getLatestPrice(),FDCHelper.add(fdcBill.getLstPrjAllPaidAmt(),fdcBill.getProjectPriceInContract()));
		drs.updateString("latestPriceSub",printStringHelper(latestPriceSub));
		
		// �������뵥�״�ʱ֧�ֻ�ȡ�������ݵ�ԭ��  by Cassiel_peng  2009-10-9
		// ��ͬ���ԭ��
		drs.updateString("contractOriPrice", printStringHelper(getContractOriAmt(contractId)));
		// ���ǩ֤���ԭ��
		drs.updateString("changeOriAmt", printStringHelper(getChangeOriAmt(contractId)));
		// �������
		drs.updateString("latestOriPrice", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getLatestPrice()), exRate,4,BigDecimal.ROUND_HALF_UP)));
		// ���޽���
		drs.updateString("grtOriAmount", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getGrtAmount()), fdcBill.getExchangeRate(),4,BigDecimal.ROUND_HALF_UP)));
		// ���ں�ͬ���ۼ�ʵ�� 
		drs.updateString("lstPrjAllPaidOriAmt", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getLstPrjAllPaidAmt()), fdcBill.getExchangeRate(),4,BigDecimal.ROUND_HALF_UP)));
		// ��ͬ�ڹ��̿������ۼ�����
		drs.updateString("lstPrjAllReqOriAmt", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getLstPrjAllReqAmt()), fdcBill.getExchangeRate(),4,BigDecimal.ROUND_HALF_UP)));
		// ������
		drs.updateString("settleOriAmt", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getSettleAmt()), fdcBill.getExchangeRate(),4,BigDecimal.ROUND_HALF_UP)));
		// ��ͬ�ڹ��̿���ڷ�����
		drs.updateString("projectPriceInContractOri", printStringHelper(fdcBill.getProjectPriceInContractOri()));
		// �������ڷ���
		drs.updateString("guerdonOriAmt", printStringHelper(getGuerdonOriAmt(contractId)));
		// ΥԼ���ڷ���
		drs.updateString("compensationOriAmt", printStringHelper(getCompensationOriAmt(contractId)));
		// �ۿ�ڷ���
		drs.updateString("deductOriAmt", printStringHelper(FDCHelper.toBigDecimal(getDeductOriAmt(contractId))));
		// �׹��ı��ڷ���
		drs.updateString("payPartAMatlOriAmt", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getPayPartAMatlAmt()), fdcBill.getExchangeRate(),4,BigDecimal.ROUND_HALF_UP)));
		// �������뱾�ҽ��    ֱ��ȡ�� amount �ֶε�ֵ������֪���Բ����أ�����
		drs.updateString("thisTimeReqLocalAmt", printStringHelper(FDCHelper.toBigDecimal(fdcBill.getAmount())));
		drs.updateString("process", fdcBill.getProcess());
		drs.updateString("id",fdcBill.getId().toString());
	}
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds){
		/**
		 * �������뵥�״���������Դ�� �������������󡢹����ɱ���Ŀ���롢�����ɱ���Ŀ��������ֱ�ӹ�����
		 * by jian_wen 2009.12.18
		 */
		if(ds.getID().toUpperCase().startsWith("SPLITENTRYPRINTVIEWQUERY")) 
        {   
			IRowSet iRowSet = null;
	        try {
	        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK ("com.kingdee.eas.fdc.contract.app.SplitEntryPrintViewQuery"));
	            exec.option().isAutoTranslateEnum= true;
	            EntityViewInfo ev = new EntityViewInfo();
	            FilterInfo filter = new FilterInfo();
	            filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", contractBill.getId().toString(), CompareType.EQUALS));
	            ev.setFilter(filter);            
	            exec.setObjectView(ev);
	            //System.out.println(exec.getSQL());
	            iRowSet = exec.executeQuery();	
	            iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}
	        
			return iRowSet;
			
        } else if (ds.getID().toUpperCase().startsWith("CONTRACTBILLENTRYPRINT")){
        	IRowSet iRowSet = null;
	        try {
	        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK ("com.kingdee.eas.fdc.contract.app.ContractBillEntryPrintQuery"));
	            exec.option().isAutoTranslateEnum= true;
	            EntityViewInfo ev = new EntityViewInfo();
	            FilterInfo filter = new FilterInfo();
	            filter.getFilterItems().add(new FilterItemInfo("parent.id", contractBill.getId().toString(), CompareType.EQUALS));
	            ev.setFilter(filter);            
	            exec.setObjectView(ev);
	            //System.out.println(exec.getSQL());
	            iRowSet = exec.executeQuery();	
	            iRowSet.beforeFirst();
			} catch (Exception e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}
	        
			return iRowSet;
        }else if (ds.getID().toUpperCase().startsWith("CONTRACTPAYITEM")) {
        	IRowSet iRowSet = null;
	        try {
	        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK ("com.kingdee.eas.fdc.contract.app.ContractPayItemPrintQuery"));
	            exec.option().isAutoTranslateEnum= true;
	            EntityViewInfo ev = new EntityViewInfo();
	            FilterInfo filter = new FilterInfo();
	            filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractBill.getId().toString(), CompareType.EQUALS));
	            ev.setFilter(filter);            
	            exec.setObjectView(ev);
	            iRowSet = exec.executeQuery();	
	            iRowSet.beforeFirst();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return iRowSet;
        }else if (ds.getID().equalsIgnoreCase("ContractPayItemPrintQuery")) {
			// ��ͬ��������
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK(
						"com.kingdee.eas.fdc.contract.app.ContractPayItemPrintQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("contractbill.id", contractBill.getId().toString()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
	} else if (ds.getID().equalsIgnoreCase("ContractBailPrintQuery")) {
			// ��ͬ��Լ��֤�𼰷�������
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractBailPrintQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", contractBill.getBail().getId().toString()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		} else if (ds.getID().equalsIgnoreCase("ContractBillQueryForPrint")) {
			// ��ͬ��ϸ��Ϣ
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK(
								"com.kingdee.eas.fdc.contract.app.ContractBillQueryForPrint"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", fdcBill.getContractId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		}else if(ds.getID().equalsIgnoreCase("ContractBillQuery")) {
			// ��ͬ��ϸ��Ϣ
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK(
								"com.kingdee.eas.fdc.contract.app.ContractBillQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", fdcBill.getContractId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		}else if(ds.getID().equalsIgnoreCase("ContractWithoutTextPrintQuery")) {
			// ���ı���ͬ��ϸ��Ϣ
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK(
								"com.kingdee.eas.fdc.contract.app.ContractWithoutTextPrintQuery"));
				
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", fdcBill.getContractId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		} else if (ds.getID().equalsIgnoreCase("DeductOfPayReqBillPrintQuery")) {
			// �����뵥��Ӧ�Ŀۿ��¼��Ϣ
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK(
								"com.kingdee.eas.fdc.contract.app.DeductOfPayReqBillPrintQuery"));
				
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("payRequestBill.id", fdcBill.getId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}
			return iRowSet;
		}else if(ds.getID().equalsIgnoreCase("BGENTRY")){
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.contract.app.PayRequestBillBgEntryPrintQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.id", fdcBill.getId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}
		}else if (ds.getID().equalsIgnoreCase("AttachmentQuery")) {
			// ��ͬ��Լ��֤�𼰷�������
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.basedata.app.AttachmentQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("boAttchAsso.boID",billId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		}
		return getMainBillRowSet(ds);
	}
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		// TODO �Զ����ɷ������
		int colCount = col.length;
		DynamicRowSet drs = null;
		try {
			drs = new DynamicRowSet(colCount);
			
			for (int i = 0; i < colCount; i++) {
				ColInfo ci = new ColInfo();
				ci.colType = Types.VARCHAR;
				ci.columnName = col[i];
				ci.nullable = 1;
				drs.setColInfo(i + 1, ci);
			}
			

			drs.beforeFirst();
			
			
			
			String payDetails = "";
			
			if(fdcBill.getId()!=null){
				//����ۼ�ʵ�� ת����String�ַ�����ʾ
				String contractNo = fdcBill.getContractNo().toString();
				Timestamp createTime = fdcBill.getCreateTime();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);
				filter.appendFilterItem("contractNo", contractNo);
				filter.getFilterItems().add(
						new FilterItemInfo("createTime", createTime,
								CompareType.LESS));
				view.getSelector().add("entrys.paymentBill.id");
				PayRequestBillCollection c;
				Set ids = new HashSet();
				try {
					c = PayRequestBillFactory.getRemoteInstance()
							.getPayRequestBillCollection(view);
					if(c.size()>0){
						for(Iterator it=c.iterator();it.hasNext();){
							PayRequestBillInfo payRequestInfo = (PayRequestBillInfo)it.next();
							if(payRequestInfo.getEntrys().size()>0){
								for(int i=0;i<payRequestInfo.getEntrys().size();i++){
									ids.add(payRequestInfo.getEntrys().get(i).getPaymentBill().getId());
								}
							}
						}
					}
				} catch (BOSException e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				}
				filter = new FilterInfo();

				if(ids.size()>0)
					filter.getFilterItems().add(new FilterItemInfo("id",ids,CompareType.INCLUDE));
				else
					filter.getFilterItems().add(
							new FilterItemInfo("id", null));
				filter.getFilterItems().add(
						new FilterItemInfo("billStatus", new Integer(BillStatusEnum.PAYED_VALUE)));
				
				//filter.appendFilterItem("contractNo",contractNo);
				
				Map map = new HashMap();
				EntityViewInfo ev = new EntityViewInfo();
				ev.getSelector().add("contractNo");
				ev.getSelector().add("feeType.name");
				ev.getSelector().add("amount");
				ev.setFilter(filter);
				PaymentBillCollection coll = PaymentBillFactory
					.getRemoteInstance().getPaymentBillCollection(ev);
				for(int i =0;i < coll.size();i++){
					PaymentBillInfo info = coll.get(i);
					FeeTypeInfo feeType = null;
					String feeTypeName = "";
					if(info.getObjectValue("feeType") == null){
						feeTypeName = "nulltype";
					}
					else if(info.getObjectValue("feeType") instanceof FeeTypeInfo){
						feeType = (FeeTypeInfo)info.getObjectValue("feeType");
						feeTypeName = feeType.getName();
					}
					
					BigDecimal amount = info.getBigDecimal("amount");
					if(map.containsKey(feeTypeName)){
						BigDecimal amt = (BigDecimal)map.get(feeTypeName);
						map.put(feeTypeName,amount.add(amt));
					}else{
						map.put(feeTypeName,amount);
					}
			
				}
				Set set = map.keySet();
				for(Iterator it=set.iterator();it.hasNext();){
					String key = (String)it.next();
					if(key.equals("nulltype"))
						payDetails +=""+map.get(key)+"\r\n";
					else
						payDetails +=key+":"+map.get(key)+"\r\n";
				}
			}

			
			drs.beforeFirst();
			//ȡ�Ѿ���������ںͽ��
			String paidDataAmount = "";
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("contractBillId", fdcBill.getContractId()));
				view.setFilter(filter);
				SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
				sorterItemInfo.setSortType(SortType.ASCEND);
				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("payDate");
				view.getSelector().add("localAmt");
				PaymentBillCollection col = PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(view);
				for(int i=0;i<col.size();i++){
					PaymentBillInfo info = col.get(i);
					String payDate = printStringHelper(info.getPayDate());
					String Amt = printStringHelper(info.getLocalAmt());
					paidDataAmount += payDate+":"+Amt+"\r\n";
				}
			}
			//ȡԭ��ͬ����
			String oldProjNumber = "";
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("parent", fdcBill.getContractId()));
				//½���죬Ӳ����
				items.add(new FilterItemInfo("detail", "%ԭ��ͬ����%" ,CompareType.LIKE));
				view.setFilter(filter);
//				SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
//				sorterItemInfo.setSortType(SortType.ASCEND);
//				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("content");				
				ContractBillEntryCollection col = ContractBillEntryFactory.getRemoteInstance().getContractBillEntryCollection(view);
				for(int i=0;i<col.size();i++){
					ContractBillEntryInfo info = col.get(i);
					String temp = printStringHelper(info.getContent());
					oldProjNumber += temp+"\r\n";
				}
			}
			
			//ȡ������� auditResult
			String auditResult = "";
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("billId", fdcBill.getId().toString()));
				view.setFilter(filter);
				SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
				sorterItemInfo.setSortType(SortType.ASCEND);
				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("opinion");
				view.getSelector().add("creator.person.name");
				MultiApproveCollection col = MultiApproveFactory.getRemoteInstance().getMultiApproveCollection(view);
				for(int i=0;i<col.size();i++){
					MultiApproveInfo info = col.get(i);
					String option = info.getOpinion();
					String person = info.getCreator().getPersonId().getName();
					auditResult += person+":"+option+"\r\n";
				}
			}
			
			//ȡ�ۿ�����
			DeductOfPayReqBillCollection c = null;
			DeductOfPayReqBillInfo info = null;
			
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("payRequestBill.id", fdcBill.getId().toString()));
				view.setFilter(filter);
				SorterItemInfo sorterItemInfo = new SorterItemInfo("deductType.number");
				sorterItemInfo.setSortType(SortType.ASCEND);
				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("deductType.number");
				view.getSelector().add("deductType.name");
				view.getSelector().add("*");
				c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
			}
			
			//ȡ��ͬ��Ϣ
			
			String contractBillAmount = "";
			String contractBillType   = "";
			if(fdcBill.getId()!=null){
				// �޸� R100707-082һ�Ÿ������뵥�״�ʱȡ�������ݡ� By Owen_wen 2010-7-27
				// �ı���ͬ�����ı���ͬ�ĸ������뵥����EditUI�������״�ʱҪ�ֱ���
				boolean isCon = FDCUtils.isContractBill(null, fdcBill.getContractId().toString());
				if(isCon){
					ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo("select amount,contractType.name where id='"+fdcBill.getContractId().toString()+"'");
					contractBillAmount = printStringHelper(contract.getAmount());
					contractBillType   = contract.getContractType().getName();
				}else{
					ContractWithoutTextInfo contract = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo("select amount,contractType.name where id='"+fdcBill.getContractId().toString()+"'");
					contractBillAmount = printStringHelper(contract.getAmount());
					contractBillType   = contract.getContractType().getName();
				}
			}
			
			
			Map deductTypeSum = new HashMap();
			deductTypeSum.put("sumLstDeductPaid",FDCHelper.ZERO);
			deductTypeSum.put("sumLstDeductReq",FDCHelper.ZERO);
			deductTypeSum.put("sumDeduct",FDCHelper.ZERO);
			deductTypeSum.put("sumAllDeductReq",FDCHelper.ZERO);
			deductTypeSum.put("sumAllDeductPaid",FDCHelper.ZERO);
			
			//TODO ����û��ѭ���Ĵ�ӡ����ÿ��drs��ֵ������Ϊ���һ��
			String lstRealPaidAmt = "";
			/*
			 * R110726-0274:�״�-���ز��ĸ������뵥�״�Ĭ�ϴ�ӡ5��  by zhiyuan_tang
			 * ��ʵ���̲�סҪ�²�һ�£�������·�㲻�ߣ���֪��ΪʲôҪ�����뵥���״�������������ӣ����źúõ�Query���ã�
			 * ��Ҫ�Լ��ֶ�ȡ��һ���ֶ�һ���ֶεĸ��£����������ˡ���
			 */
			if(c==null || c.size()==0){
				DeductTypeCollection typeCol  = null;
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				view.setFilter(filter);
				typeCol  = DeductTypeFactory.getRemoteInstance().getDeductTypeCollection(view);
				
				for(int i=0;i<typeCol.size();i++){
					DeductTypeInfo deductTypeInfo = typeCol.get(i);
					drs.moveToInsertRow();
					insert( drs,null,deductTypeSum );
					drs.updateString("deductType",deductTypeInfo==null?"":deductTypeInfo.getName());
				}
				//�������
				drs.updateString("auditResult",auditResult);
				//�Ѹ����ڽ��
				drs.updateString("paidDataAmount",paidDataAmount);
				//ԭ��ͬ����
				drs.updateString("oldProjNumber",oldProjNumber);
				drs.updateString("paidDetail",payDetails);
				
				drs.updateString("contractBillAmount",contractBillAmount);
				drs.updateString("contractBillType",contractBillType);
				
				if(fdcBill.getApplier()!=null){
					drs.updateString("applier", fdcBill.getApplier().getName());
				}
				if(fdcBill.getCostedDept()!=null){
					drs.updateString("costedDept", fdcBill.getCostedDept().getName());
				}
				if(fdcBill.getCostedCompany()!=null){
					drs.updateString("costedCompany", fdcBill.getCostedCompany().getName());
				}
				if(fdcBill.getPayBillType()!=null){
					drs.updateString("payBillType", fdcBill.getPayBillType().getName());
				}
				if(fdcBill.getPayContentType()!=null){
					drs.updateString("payContentType", fdcBill.getPayContentType().getName());
				}
				if(fdcBill.getBgEntry().size()>0){
					drs.updateString("requestAmount", printStringHelper(fdcBill.getBgEntry().get(0).getRequestAmount()));
					if(fdcBill.getBgEntry().get(0).getBgItem()!=null){
						drs.updateString("bgItem.number", fdcBill.getBgEntry().get(0).getBgItem().getNumber());
						drs.updateString("bgItem.name", fdcBill.getBgEntry().get(0).getBgItem().getName());
					}
					if(fdcBill.getBgEntry().get(0).getAccountView()!=null){
						drs.updateString("accountView.number", fdcBill.getBgEntry().get(0).getAccountView().getNumber());
						drs.updateString("accountView.name", fdcBill.getBgEntry().get(0).getAccountView().getName());
						drs.updateString("accountView.longNumber", fdcBill.getBgEntry().get(0).getAccountView().getLongNumber());
						drs.updateString("accountView.longName", fdcBill.getBgEntry().get(0).getAccountView().getLongName());
					}
				}
				drs.updateString("accountView.longName", fdcBill.getBgEntry().get(0).getAccountView().getLongName());
				
				String agentPayCompany="";
//				FDCSQLBuilder _builder = new FDCSQLBuilder();
//				_builder.appendSql(" select distinct company.fname_l2 name from T_CAS_PaymentBill pay left join T_ORG_Company company on pay.FAgentPayCompanyID=company.fid ");
//				_builder.appendSql(" where pay.fbillstatus=15 and company.fid is not null and pay.FFdcPayReqID='"+fdcBill.getId().toString()+"'");
//				IRowSet rowSet = _builder.executeQuery();
//				if(rowSet.size()==1){
//					while (rowSet.next()) {
//						agentPayCompany=rowSet.getString("name");
//					}
//				}else if(rowSet.size()>1){
//					while (rowSet.next()) {
//						agentPayCompany=rowSet.getString("name")+","+agentPayCompany;
//					}
//					agentPayCompany=agentPayCompany.substring(0,agentPayCompany.lastIndexOf(","));
//				}
				drs.updateString("agentCompany.name", agentPayCompany);
				
				String payNo="";
				FDCSQLBuilder _builder = new FDCSQLBuilder();
				_builder.appendSql(" select pay.fnumber number from T_CAS_PaymentBill pay ");
				_builder.appendSql(" where pay.FFdcPayReqID='"+fdcBill.getId().toString()+"' order by pay.fcreateTime");
				IRowSet rowSet = _builder.executeQuery();
				while (rowSet.next()) {
					payNo=rowSet.getString("number");
				}
				drs.updateString("payNo", payNo);
				
				drs.updateString("appAmount", printStringHelper(fdcBill.getAppAmount()));
				
				drs.insertRow();
				
			}else{
				
				for(int i=0;i<c.size();i++){
					info = c.get(i);
					
					drs.moveToInsertRow();
					insert( drs,info ,deductTypeSum);
				}
				
				//�������
				drs.updateString("auditResult",auditResult);
				//�Ѹ����ڽ��
				drs.updateString("paidDataAmount",paidDataAmount);
				//ԭ��ͬ����
				drs.updateString("oldProjNumber",oldProjNumber);
				drs.updateString("paidDetail",payDetails);
				
				drs.updateString("contractBillAmount",contractBillAmount);
				drs.updateString("contractBillType",contractBillType);
				
				if(fdcBill.getApplier()!=null){
					drs.updateString("applier", fdcBill.getApplier().getName());
				}
				if(fdcBill.getCostedDept()!=null){
					drs.updateString("costedDept", fdcBill.getCostedDept().getName());
				}
				if(fdcBill.getCostedCompany()!=null){
					drs.updateString("costedCompany", fdcBill.getCostedCompany().getName());
				}
				if(fdcBill.getPayBillType()!=null){
					drs.updateString("payBillType", fdcBill.getPayBillType().getName());
				}
				if(fdcBill.getPayContentType()!=null){
					drs.updateString("payContentType", fdcBill.getPayContentType().getName());
				}
				if(fdcBill.getBgEntry().size()>0){
					drs.updateString("requestAmount", printStringHelper(fdcBill.getBgEntry().get(0).getRequestAmount()));
					if(fdcBill.getBgEntry().get(0).getBgItem()!=null){
						drs.updateString("bgItem.number", fdcBill.getBgEntry().get(0).getBgItem().getNumber());
						drs.updateString("bgItem.name", fdcBill.getBgEntry().get(0).getBgItem().getName());
					}
					if(fdcBill.getBgEntry().get(0).getAccountView()!=null){
						drs.updateString("accountView.number", fdcBill.getBgEntry().get(0).getAccountView().getNumber());
						drs.updateString("accountView.name", fdcBill.getBgEntry().get(0).getAccountView().getName());
						drs.updateString("accountView.longNumber", fdcBill.getBgEntry().get(0).getAccountView().getLongNumber());
						drs.updateString("accountView.longName", fdcBill.getBgEntry().get(0).getAccountView().getLongName());
					}
				}
				String agentPayCompany="";
//				FDCSQLBuilder _builder = new FDCSQLBuilder();
//				_builder.appendSql(" select distinct company.fname_l2 name from T_CAS_PaymentBill pay left join T_ORG_Company company on pay.FAgentPayCompanyID=company.fid ");
//				_builder.appendSql(" where pay.fbillstatus=15 and company.fid is not null and pay.FFdcPayReqID='"+fdcBill.getId().toString()+"'");
//				IRowSet rowSet = _builder.executeQuery();
//				if(rowSet.size()==1){
//					while (rowSet.next()) {
//						agentPayCompany=rowSet.getString("name");
//					}
//				}else if(rowSet.size()>1){
//					while (rowSet.next()) {
//						agentPayCompany=rowSet.getString("name")+","+agentPayCompany;
//					}
//					agentPayCompany=agentPayCompany.substring(0,agentPayCompany.lastIndexOf(","));
//				}
				drs.updateString("agentCompany.name", agentPayCompany);
				
				
				String payNo="";
				FDCSQLBuilder _builder = new FDCSQLBuilder();
				_builder.appendSql(" select pay.fnumber number from T_CAS_PaymentBill pay ");
				_builder.appendSql(" where pay.FFdcPayReqID='"+fdcBill.getId().toString()+"' order by pay.fcreateTime");
				IRowSet rowSet = _builder.executeQuery();
				while (rowSet.next()) {
					payNo=rowSet.getString("number");
				}
				drs.updateString("payNo", payNo);
				
				drs.updateString("appAmount", printStringHelper(fdcBill.getAppAmount()));
				
				lstRealPaidAmt = drs.getString("lstRealPaidAmt");
				drs.insertRow();
			}			
			drs.beforeFirst();
			
			while(drs.next()){
				drs.updateString("lstRealPaidAmt",lstRealPaidAmt);
			}
			drs.beforeFirst();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		return drs;
	}

	//��ȡ��Ӧ��ͬ�Ļ���
	public BigDecimal getConExRate(String contractId){
		ContractBillInfo info = null;
		BigDecimal exRate = FDCHelper.ONE;
		try {
			info =ContractBillFactory
				.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
			exRate = info.getExRate();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (UuidException e) {
			e.printStackTrace();
		}
		return exRate;
	}
	/**
	 * �ۼ��깤������ȡ�����ϡ��ۼ��깤���������Ľ�������������ʾ�����깤������ʱ��������Դ����ʾ���ݣ�����Ϊ�� ��
	 * @return
	 */
	private void initAllCompletePrjAmt(){
		if(!fdcBill.getBoolean("isCompletePrjAmtVisible")){
			fdcBill.put("allCompletePrjAmt", "");
			return;
		}
//		BigDecimal allCompleteProjAmt = FDCHelper.ZERO;
//		String paymentType = fdcBill.getPaymentType().getPayType().getId().toString();
//    	String progressID = TypeInfo.progressID;
//    	if(!paymentType.equals(progressID)){
//    		allCompleteProjAmt =FDCHelper.toBigDecimal(contractBill.getSettleAmt(),2);
//    		fdcBill.put("allCompletePrjAmt", allCompleteProjAmt);
//    		return;
//    	}
//    	
//    	EntityViewInfo view = new EntityViewInfo();
//    	view.getSelector().add("completePrjAmt");
//    	view.getSelector().add("state");
//    	FilterInfo filter = new FilterInfo();
//    	filter.getFilterItems().add(new FilterItemInfo("contractId", fdcBill.getContractId()));
//    	filter.getFilterItems().add(new FilterItemInfo("paymentType.payType.id", progressID));
//    	filter.getFilterItems().add(new FilterItemInfo("createTime", fdcBill.getCreateTime(), CompareType.LESS_EQUALS));
//    	view.setFilter(filter);
//    	PayRequestBillCollection payReqColl = null;
//		try {
//			payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
//		} catch (BOSException e) {
//			// TODO �Զ����� catch ��
//			e.printStackTrace();
//		}
//    	
//    	if(payReqColl != null){
//    		for(int i=0;i<payReqColl.size();i++){
//    			PayRequestBillInfo info = payReqColl.get(i);
//    			allCompleteProjAmt = allCompleteProjAmt.add(FDCHelper.toBigDecimal(info.getCompletePrjAmt()));
//    		}
//    	}
//    	allCompleteProjAmt = FDCHelper.toBigDecimal(allCompleteProjAmt, 2);
    	fdcBill.put("allCompletePrjAmt", fdcBill.get("allCompletePrjAmt"));
	}
}
