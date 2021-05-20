package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.jdbc.rowset.IRowSet;
/**
 * @author Cassiel_peng
 * 2009-10-21
 */
public class FdcAcctActualPayCaculatorForR090921_057 implements
		IMethodBatchQuery, ICalculator {
	private Context ServerCtx = null;

	private String fdcCompanyNumber = null;
	private boolean displayAllCompany = false;
	private String prjNumber = null;
	private String acctLongNumber = null;
	private String beginPeriod = null;
	private String endPeriod = null;

	private ICalculateContextProvider context;

	public FdcAcctActualPayCaculatorForR090921_057() {

	}

	public Context getServerCtx() {
		return ServerCtx;
	}

	public void setServerCtx(Context serverCtx) {
		ServerCtx = serverCtx;
	}

	public void initCalculateContext(ICalculateContextProvider context) {
		this.context = context;
		this.ServerCtx = this.context.getServerContext();
	}

	public boolean batchQuery(Map methods) {
		SortedParameterArray params = (SortedParameterArray) methods
				.get("fdc_acct_actual_pay_filterByDate");
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();

				fdcCompanyNumber = (String) ((Variant) obj[0]).getValue();
				prjNumber = (String) ((Variant) obj[2]).getValue();
				acctLongNumber = (String) ((Variant) obj[3]).getValue();
				beginPeriod = (String) ((Variant) obj[4]).getValue();
				endPeriod = (String) ((Variant) obj[5]).getValue();

				try {
					BigDecimal amount = this.fdc_acct_actual_pay_filterByDate(
							fdcCompanyNumber, displayAllCompany, prjNumber,
							acctLongNumber, beginPeriod, endPeriod);
					params.getParameter(i).setValue(amount);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * �ʽ�ƻ�ȡ��--ʵ�ʸ���ȡ����ʽ
	 * 
	 * @param fdcCompanyNumber
	 *            ��˾����
	 * @param prjNumber
	 *            ��Ŀ������
	 * @param acctLongNumber
	 *            �ɱ���Ŀ������
	 * @param month
	 *            �¶�
	 * @return
	 * @throws ParseException
	 * @throws BOSException
	 * @throws SQLException
	 */
	public BigDecimal fdc_acct_actual_pay_filterByDate(String fdcCompanyNumber,
			boolean displayAllCompany, String prjNumber, String acctLongNumber,
			String beginPeriod, String endPeriod) throws ParseException,
			BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();

		/**
		 * �����滻��̾��
		 */
		// // fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		// //��֯���벻���滻������Ŀ��ͬ
		prjNumber = prjNumber.replace('.', '!');
		acctLongNumber = acctLongNumber.replace('.', '!');

		/**
		 * ͨ��T_ORG_CostCenter�е�number��fdcCompanyNumber,��ȡFCostCenterId
		 * Ȼ����T_FDC_CurProject��FCostCenterId��Ӧ�ļ����У���FlongNumber��prjNumber
		 * ��Ӧ�������Ȼ�󷵻�fid���ϣ���ͨ����˾����Ŀѡȡ��ǰ��Ŀ��ID
		 */
		builder
				.appendSql("select curProject.fid from T_FDC_CurProject curProject ");
		builder
				.appendSql(" inner join T_ORG_BaseUnit costCenter on curProject.FFullOrgUnit = costCenter.fid ");
		builder.appendSql(" where costCenter.FLongNumber = ? ");
		builder.addParam(fdcCompanyNumber);
		builder.appendSql(" and ");
		builder.appendSql(" curProject.FLongNumber = ? ");
		builder.addParam(prjNumber);

		/**
		 * ����ǰ��Ŀ��ID����List�У������ڳɱ���Ŀ�в��Ҷ�Ӧ�ĳɱ���Ŀ
		 */
		List curProjectID = new ArrayList();

		IRowSet rowSet = builder.executeQuery(ServerCtx);

		while ((rowSet != null) && (rowSet.next())) {
			curProjectID.add(rowSet.getString("fid"));
		}

		// ���builder�е�sql���
		builder.clear();

		/**
		 * ͨ��������õĵ�ǰ��ĿID��T_FDC_CostAccount�еĵ�ǰ��ĿID����ƥ��
		 * �ټ��ϳɱ���Ŀ�������Լ��������ȷ������ɱ���Ŀ��ID
		 */
		String projectID = "";
		if (curProjectID != null && curProjectID.size() == 1) {
			projectID = curProjectID.get(0).toString();
		}
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		builder.appendSql(" and ( ");
		builder.appendSql(" FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql(" or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql(" ||'!', FlongNumber ) = 1 ) ");

		List costAccountID = new ArrayList();

		// ֮ǰΪ������������Դ�������һЩ���⣬�������¶������
		IRowSet rowSetRes = builder.executeQuery(ServerCtx);

		while ((rowSetRes != null) && (rowSetRes.next())) {
			costAccountID.add(rowSetRes.getString("fid"));
		}

		builder.clear();

		// ִ��Sql����ȡ���������������Ϊ�գ��򷵻�FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO;
		BigDecimal acctPayAmount = FDCHelper.ZERO;

		if (costAccountID.size() > 0) {
			builder.clear();
			builder.appendSql(" select sum(entry.FPayedAmt) as FAmount from T_FNC_PaymentSplitEntry entry ");
			builder.appendSql(" inner join T_FNC_PaymentSplit head on entry.FParentId=head.FId ");
			builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = entry.FCostAccountID ");
			builder.appendSql(" inner join T_FDC_CurProject prj on cost.FCurProject = prj.FID ");
			builder.appendSql(" inner join T_CAS_PaymentBill pay on pay.FID= head.FPaymentBillID ");
			builder.appendSql(" where head.FIsInvalid=0 and cost.FCurProject =? ");
			builder.addParam(projectID);
			builder.appendSql(" and pay.FBillStatus = ? ");
			builder.addParam(new Integer(BillStatusEnum.PAYED_VALUE));
			builder.appendSql(" and head.FCreateTime < {ts '"+FDCDateHelper.DateToString(FDCDateHelper.getNextDay(FDCDateHelper.stringToDate(endPeriod)))+"'}");
//			builder.addParam(FDCDateHelper.getNextDay(FDCDateHelper.stringToDate(endPeriod)));
			builder.appendSql(" and head.FCreateTime > {ts '"+beginPeriod+"'}");
//			builder.addParam(FDCDateHelper.stringToDate(beginPeriod));
			builder.appendSql( " and ( cost.FlongNumber = ? ");
			builder.addParam(acctLongNumber);
			builder.appendSql( " or charindex ( ? ");
			builder.addParam(acctLongNumber);
			builder.appendSql( " ||'!', cost.FlongNumber ) = 1 ) " );
			//Ҷ�ӽڵ�
			builder.appendSql(" and entry.FIsLeaf = ?");
			builder.addParam(new Integer(1));

			IRowSet rowSetTemp = builder.executeQuery(ServerCtx);

			while ((rowSetTemp != null) && (rowSetTemp.next())) {
				acctPayAmount = rowSetTemp.getBigDecimal("FAmount");
			}
		}
		
		if (result == null) {
			result = FDCHelper.ZERO;
		}else{
//			result = FDCHelper.add(acctPayAmount, paySplitAmount);
			result = FDCHelper.toBigDecimal(acctPayAmount);
		}
		return result;
	}
}
