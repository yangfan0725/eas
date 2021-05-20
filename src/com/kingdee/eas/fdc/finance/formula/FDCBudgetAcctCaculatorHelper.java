package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.excel.model.util.ObjectCache;
import com.kingdee.bos.dao.ObjectNotFoundException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.ma.budget.BgPeriodFactory;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.IBgPeriod;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * Ԥ��ϵͳȡ����ʽ������
 * 
 * @author pengwei_hou
 * @date 2009-06-10
 */
public class FDCBudgetAcctCaculatorHelper {

	/**
	 * ���ݳɱ����ĳ�����ӹ�����Ŀ��ɱ����Ķ�Ӧ��ϵ���л�ȡ������ĿID
	 * 
	 * @param ctx
	 * @param costCenterNumber
	 *            �ɱ����ĳ�����
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static List initProjectIDList(Context ctx, String costCenterNumber)
			throws BOSException, SQLException {
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql("select withCost.FCurProjectID from T_FDC_ProjectWithCostCenterOU withCost ");
		builder
				.appendSql("inner join T_ORG_CostCenter costCenter on withCost.FCostCenterOUID = costCenter.fid ");
		builder.appendSql("where ");
		builder.appendParam("costCenter.FNumber", costCenterNumber);

		List curProjectID = new ArrayList();
		IRowSet rowSet = builder.executeQuery();
		while ((rowSet != null) && (rowSet.next())) {
			curProjectID.add(rowSet.getString("FCurProjectID"));
		}
		builder.clear();
		return curProjectID;
	}

	/**
	 * ��ȡ������Ŀ��Ŀ
	 * 
	 * @param ctx
	 * @param projectID
	 *            ����ID
	 * @param acctLongNumber
	 *            ��Ŀ������
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static List initCostAccount(Context ctx, String projectID,
			String acctLongNumber) throws BOSException, SQLException {
		/**
		 * �����滻��̾��
		 */
		acctLongNumber = acctLongNumber.replace('.', '!');
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		builder.appendSql(" and ( ");
		builder.appendSql(" FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql(" or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) and fisleaf=1" );

		List costAccountID = new ArrayList();

		// ֮ǰΪ������������Դ�������һЩ���⣬�������¶������
		IRowSet rowSetRes = builder.executeQuery();
		while ((rowSetRes != null) && (rowSetRes.next())) {
			costAccountID.add(rowSetRes.getString("fid"));
		}
		builder.clear();
		return costAccountID;
	}

	/**
	 * ���ݿ�ĿID���ڼ��ȡԤ�����ʵ�ʸ�����
	 * 
	 * @param ctx
	 * @param costAccountID
	 *            ��ĿID
	 * @param period
	 *            ����
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getAnnualBudgetPay(Context ctx,
			List costAccountID, BgPeriodInfo period)
			throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		BigDecimal result = FDCHelper.ZERO;
		builder
				.appendSql(" select sum(item.FAmount) as FAmount  from T_FNC_FDCYearBudgetAcctEntry  entry ");
		builder
				.appendSql(" inner join T_FNC_FDCYearBudgetAcct parent on entry.FParentId = parent.FID ");
		builder
				.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
		builder
				.appendSql(" inner join T_FNC_FDCYearBudgetAcctItem item on entry.fid=item.fentryid where entry.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");

		// ���year(���)Լ������
		// �����ȡ������ֵ��Ϊ�գ�����нضϲ�����ȡ�����(KSQL��)
		builder.appendSql(" period.FYear = ? ");
		builder.addParam(new Integer(period.getYear()));
//		builder.appendSql("and item.fmonth = ? ");
//		builder.addParam(new Integer(period.getMonth()));

		builder.appendSql(" and parent.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

		builder.appendSql(" and parent.FIsLatestVer = ? ");
		builder.addParam(new Integer(1));

		IRowSet rowSetTemp = builder.executeQuery();

		while ((rowSetTemp != null) && (rowSetTemp.next())) {
			result = rowSetTemp.getBigDecimal("FAmount");
		}
		if (result == null) {
			result = FDCHelper.ZERO;
		}
		return result;
	}

	/**
	 * ���ݿ�ĿID���ڼ��ȡԤ�����ʵ�ʳɱ���
	 * 
	 * @param ctx
	 * @param costAccountID
	 *            ��ĿID
	 * @param transformDate
	 *            ����
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getAnnualBudgetCost(Context ctx,
			List costAccountID, BgPeriodInfo period)
			throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		BigDecimal result = FDCHelper.ZERO;
		builder
				.appendSql(" select sum(item.FCost) as FCost  from T_FNC_FDCYearBudgetAcctEntry  entry ");
		builder
				.appendSql(" inner join T_FNC_FDCYearBudgetAcct parent on entry.FParentId = parent.FID ");
		builder
				.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
		builder
				.appendSql(" inner join T_FNC_FDCYearBudgetAcctItem item on entry.fid=item.fentryid where entry.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");

		// ���year(���)Լ������
		// �����ȡ������ֵ��Ϊ�գ�����нضϲ�����ȡ�����(KSQL��)
		builder.appendSql(" period.FYear = ? ");
		builder.addParam(new Integer(period.getYear()));
//		builder.appendSql("and item.fmonth = ? ");
//		builder.addParam(new Integer(period.getMonth()));

		builder.appendSql(" and parent.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

		builder.appendSql(" and parent.FIsLatestVer = ? ");
		builder.addParam(new Integer(1));

		IRowSet rowSetTemp = builder.executeQuery();

		while ((rowSetTemp != null) && (rowSetTemp.next())) {
			result = rowSetTemp.getBigDecimal("FCost");
		}
		if (result == null) {
			result = FDCHelper.ZERO;
		}
		return result;
	}
	
	/**
	 * ʵ�ʸ���ȡ��Ԥ���ڼ��Ӧ�·ݷ�����ʵ�ʸ�����
	 * 
	 * @param ctx
	 * @param costAccountID
	 * @param transformDate
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getActualPay1(Context ctx, List costAccountID,
			Date transformDate) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql(" select sum(acctPay.FAmount) as FAmount  from T_FNC_PayRequestAcctPay acctPay ");
		builder
				.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = acctPay.FCostAccountId ");
		builder
				.appendSql(" inner join T_CON_PayRequestBill bill on bill.FID = acctPay.FPayRequestBillId ");
		builder
				.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = acctPay.FPeriodId ");
		builder.appendSql(" where acctPay.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");

		builder.appendSql(" period.FYear = ? ");
		builder.addParam(new Integer(transformDate.getYear() + 1900));

		builder.appendSql(" and period.FMonth = ? ");
		builder.addParam(new Integer(transformDate.getMonth() + 1));

		builder.appendSql(" and bill.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

		IRowSet rowSetTemp = builder.executeQuery();
		BigDecimal result = FDCHelper.ZERO;
		while ((rowSetTemp != null) && (rowSetTemp.next())) {
			result = rowSetTemp.getBigDecimal("FAmount");
		}
		return result;
	}
	
	/**
	 * ʵ�ʸ���ȡ��Ԥ���ڼ��Ӧ�·ݷ�����ʵ�ʸ�����
	 * 
	 * @param ctx
	 * @param costAccountID
	 * @param transformDate
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getActualPay(Context ctx, List costAccountID,
			BgPeriodInfo period) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql(" select sum(acctPay.FAmount) as FAmount  from T_FNC_PayRequestAcctPay acctPay ");
		builder
				.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = acctPay.FCostAccountId ");
		builder
				.appendSql(" inner join T_CON_PayRequestBill bill on bill.FID = acctPay.FPayRequestBillId ");
		builder
				.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = acctPay.FPeriodId ");
		builder.appendSql(" where acctPay.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");

		builder.appendSql(" period.FYear = ? ");
		builder.addParam(new Integer(period.getYear()));

		builder.appendSql(" and period.FMonth = ? ");
		builder.addParam(new Integer(period.getMonth()));

		builder.appendSql(" and bill.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

		IRowSet rowSetTemp = builder.executeQuery();
		BigDecimal result = FDCHelper.ZERO;
		while ((rowSetTemp != null) && (rowSetTemp.next())) {
			result = rowSetTemp.getBigDecimal("FAmount");
		}
		return result;
	}

	/**
	 * Ԥ��ϵͳ���������ֹ�ڼ�ʵ�ʸ���ȡ������
	 * 
	 * @param ctx
	 * @param costAccountID
	 * @param transformDate
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getUpToActualPay(Context ctx, List costAccountID,
			Date startDate, Date endDate) throws BOSException, SQLException {
		endDate = FDCDateHelper.addDays(endDate, 1);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder
				.appendSql(" select sum(acctPay.FAmount) as FAmount  from T_FNC_PayRequestAcctPay acctPay ");
		builder
				.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = acctPay.FCostAccountId ");
		builder
				.appendSql(" inner join T_CON_PayRequestBill bill on bill.FID = acctPay.FPayRequestBillId ");
		builder
				.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = acctPay.FPeriodId ");
		builder.appendSql(" where acctPay.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");

		builder.appendSql(" ( period.FEndDate between ");
		builder.appendParam(new java.sql.Date(startDate.getTime()));
		builder.appendSql(" and ");
		builder.appendParam(new java.sql.Date(endDate.getTime()));

		builder.appendSql(" ) and bill.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

		IRowSet rowSetTemp = builder.executeQuery();
		BigDecimal result = FDCHelper.ZERO;
		while ((rowSetTemp != null) && (rowSetTemp.next())) {
			result = FDCHelper.add(result, rowSetTemp.getBigDecimal("FAmount"));
		}
		return result;
	}

	/**
	 * ��Ŀ�¶�Ԥ���ĸ�Ԥ���ڼ��Ӧ�·ݼƻ��ɱ�����
	 * 
	 * @param ctx
	 * @param projectID
	 * @param costAccountID
	 * @param period
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getMonthlyBudgetCost(Context ctx,
			String projectID, List costAccountID, BgPeriodInfo period)
			throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// �ж�ȡ����ȡ��ǰ�»�������,����������
		Integer year = new Integer(period.getYear());
		Integer month2 = new Integer(period.getMonth());
		int lastMonth = 0;
		builder.clear();
		builder.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent ");
		builder
				.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
		builder
				.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ? and fisLatestVer=1 ");
		// ��������Ϊ�������ſ���ȡ��

		builder.addParam(projectID);
		builder.addParam(year);
		builder.addParam(month2);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		if (builder.isExist()) {
			lastMonth = 0;
		} else {
			int myYear = year.intValue();
			int myMonth = month2.intValue();
			if (month2.intValue() == 1) {
				myYear = year.intValue() - 1;
				myMonth = 12;
			} else {
				myYear = year.intValue();
				myMonth = month2.intValue() - 1;
			}
			builder.clear();
			builder
					.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent  ");
			builder
					.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
			builder
					.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ?  and fisLatestVer=1 ");

			// ��������Ϊ�������ſ���ȡ��

			builder.addParam(projectID);
			builder.addParam(new Integer(myYear));
			builder.addParam(new Integer(myMonth));
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
			if (builder.isExist()) {
				lastMonth = 1;
				year = new Integer(myYear);
				month2 = new Integer(myMonth);
			} else {
				if (month2.intValue() == 1) {
					myYear = year.intValue() - 1;
					myMonth = 11;
				} else if (month2.intValue() == 2) {
					myYear = year.intValue() - 1;
					myMonth = 12;
				} else {
					myYear = year.intValue();
					myMonth = month2.intValue() - 2;
				}
				builder.clear();
				builder
						.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent ");
				builder
						.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
				builder
						.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ?  and fisLatestVer=1 ");
				builder.addParam(projectID);
				builder.addParam(new Integer(myYear));
				builder.addParam(new Integer(myMonth));
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
				// ��������Ϊ�������ſ���ȡ��
				if (builder.isExist()) {
					lastMonth = 2;
					year = new Integer(myYear);
					month2 = new Integer(myMonth);
				} else {
					return FDCHelper.ZERO;
				}
			}
		}
		builder.clear();

		// ִ��Sql����ȡ���������������Ϊ�գ��򷵻�FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO;

		if (costAccountID.size() > 0) {
			if (lastMonth == 0) {
				builder
						.appendSql(" select sum(entry.fcost) as fcost  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
				builder
						.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
				builder
						.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
				builder
						.appendSql(" where  fisLatestVer=1 and entry.FCostAccountId in ( ");
				builder.appendParam(costAccountID.toArray());
				builder.appendSql(" ) and ");

				// ���year(���)Լ������
				// �����ȡ������ֵ��Ϊ�գ�����нضϲ�����ȡ�����(KSQL��)
				builder.appendSql(" period.FYear = ? ");
				builder.addParam(year);
				builder.appendSql(" and ");
				builder.appendSql(" period.FMonth = ? ");
				builder.addParam(month2);

				// ��������Ϊ�������ſ���ȡ��
				builder.appendSql(" and parent.FState = ? ");
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

				// ����¼û�а�ʱ�ſ������
				builder
						.appendSql(" and (entry.FIsAdd = ? or entry.FIsAdd is null)");
				builder.addParam(new Boolean(false));

				IRowSet rowSetTemp = builder.executeQuery();

				while ((rowSetTemp != null) && (rowSetTemp.next())) {
					result = rowSetTemp.getBigDecimal("fcost");
				}
			} else {
				builder
						.appendSql(" select sum(item.fcost) as fcost  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
				builder
						.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
				builder
						.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
				builder
						.appendSql(" inner join T_FNC_FDCMonthBudgetAcctItem item on item.fentryid=entry.fid");
				builder
						.appendSql(" where fisLatestVer=1 and entry.FCostAccountId in ( ");
				builder.appendParam(costAccountID.toArray());
				builder.appendSql(" ) and ");

				// ���year(���)Լ������
				// �����ȡ������ֵ��Ϊ�գ�����нضϲ�����ȡ�����(KSQL��)
				builder.appendSql(" period.FYear = ? ");
				builder.addParam(year);
				builder.appendSql(" and ");
				builder.appendSql(" period.FMonth = ? ");
				builder.addParam(month2);

				// ��������Ϊ�������ſ���ȡ��
				builder.appendSql(" and parent.FState = ? ");
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

				// ����¼û�а�ʱ�ſ������
				builder
						.appendSql(" and (entry.FIsAdd = ? or entry.FIsAdd is null)");
				builder.addParam(new Boolean(false));
				builder.appendSql(" and item.fmonth=?");
				builder.addParam(new Integer(period.getMonth()));
				IRowSet rowSetTemp = builder.executeQuery();

				while ((rowSetTemp != null) && (rowSetTemp.next())) {
					result = rowSetTemp.getBigDecimal("fcost");
				}
			}

		}
		return result;
	}

	/**
	 * Ԥ��ϵͳ�¶�Ԥ��ƻ�����ȡ������<pre>
	 * �����¶ȸ���ƻ������������ƻ�����������ƴ˷���ʱ�������´���:<pre>
	 * ����û�ѡ��ĵ�ǰ�ڼ�û�м�¼������Ųһ�¼��ж��ϸ����Ƿ��м�¼������ϸ��»���û����ǰŲһ�¼��ж����������Ƿ������ݡ���������û�м�¼��ֱ�ӷ���0<pre>
	 * ͬʱҪע�������ǰ�¾ͼ�¼����ȡ��T_FNC_FDCMonthBudgetAcctEntry�е�amount(����������)�����ȡ�����ϸ��»������������µ����ݾͱ��T_FNC_FDCMonthBudgetAcctItem
	 * ��ȡamount(�����걨��) <pre> by Cassiel_peng 2009-11-30
	 */
	public static BigDecimal getMonthlyBudgetPay(Context ctx, String projectID,
			List costAccountID, BgPeriodInfo period) throws BOSException,
			SQLException {
		if(period.getId()==null){
			return FDCHelper.ZERO;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// �ж�ȡ����ȡ��ǰ�»�������,����������
		Integer year = new Integer(period.getYear());
		Integer month2 = new Integer(period.getMonth());
		int lastMonth = 0;
		builder.clear();
		builder.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent ");
		builder
				.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
		builder
				.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ? and fisLatestVer=1 ");
		// ��������Ϊ�������ſ���ȡ��

		builder.addParam(projectID);
		builder.addParam(year);
		builder.addParam(month2);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		if (builder.isExist()) {
			lastMonth = 0;
		} else {
			int myYear = year.intValue();
			int myMonth = month2.intValue();
			if (month2.intValue() == 1) {
				myYear = year.intValue() - 1;
				myMonth = 12;
			} else {
				myYear = year.intValue();
				myMonth = month2.intValue() - 1;
			}
			builder.clear();
			builder
					.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent  ");
			builder
					.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
			builder
					.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ?  and fisLatestVer=1 ");

			// ��������Ϊ�������ſ���ȡ��

			builder.addParam(projectID);
			builder.addParam(new Integer(myYear));
			builder.addParam(new Integer(myMonth));
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
			if (builder.isExist()) {
				lastMonth = 1;
				year = new Integer(myYear);
				month2 = new Integer(myMonth);
			} else {
				if (month2.intValue() == 1) {
					myYear = year.intValue() - 1;
					myMonth = 11;
				} else if (month2.intValue() == 2) {
					myYear = year.intValue() - 1;
					myMonth = 12;
				} else {
					myYear = year.intValue();
					myMonth = month2.intValue() - 2;
				}
				builder.clear();
				builder
						.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent ");
				builder
						.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
				builder
						.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ?  and fisLatestVer=1 ");
				builder.addParam(projectID);
				builder.addParam(new Integer(myYear));
				builder.addParam(new Integer(myMonth));
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
				// ��������Ϊ�������ſ���ȡ��
				if (builder.isExist()) {
					lastMonth = 2;
					year = new Integer(myYear);
					month2 = new Integer(myMonth);
				} else {
					return FDCHelper.ZERO;
				}
			}
		}
		builder.clear();

		// ִ��Sql����ȡ���������������Ϊ�գ��򷵻�FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO;

		if (costAccountID.size() > 0) {
			if (lastMonth == 0) {
				builder
						.appendSql(" select sum(entry.FAmount) as FAmount  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
				builder
						.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
				builder
						.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
				builder
						.appendSql(" where  fisLatestVer=1 and entry.FCostAccountId in ( ");
				builder.appendParam(costAccountID.toArray());
				builder.appendSql(" ) and ");

				// ���year(���)Լ������
				// �����ȡ������ֵ��Ϊ�գ�����нضϲ�����ȡ�����(KSQL��)
				builder.appendSql(" period.FYear = ? ");
				builder.addParam(year);
				builder.appendSql(" and ");
				builder.appendSql(" period.FMonth = ? ");
				builder.addParam(month2);

				// ��������Ϊ�������ſ���ȡ��
				builder.appendSql(" and parent.FState = ? ");
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

				// ����¼û�а�ʱ�ſ������
				builder
						.appendSql(" and (entry.FIsAdd = ? or entry.FIsAdd is null)");
				builder.addParam(new Boolean(false));

				IRowSet rowSetTemp = builder.executeQuery();

				while ((rowSetTemp != null) && (rowSetTemp.next())) {
					result = rowSetTemp.getBigDecimal("FAmount");
				}
			} else {
				builder
						.appendSql(" select sum(item.FAmount) as FAmount  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
				builder
						.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
				builder
						.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
				builder
						.appendSql(" inner join T_FNC_FDCMonthBudgetAcctItem item on item.fentryid=entry.fid");
				builder
						.appendSql(" where fisLatestVer=1 and entry.FCostAccountId in ( ");
				builder.appendParam(costAccountID.toArray());
				builder.appendSql(" ) and ");

				// ���year(���)Լ������
				// �����ȡ������ֵ��Ϊ�գ�����нضϲ�����ȡ�����(KSQL��)
				builder.appendSql(" period.FYear = ? ");
				builder.addParam(year);
				builder.appendSql(" and ");
				builder.appendSql(" period.FMonth = ? ");
				builder.addParam(month2);

				// ��������Ϊ�������ſ���ȡ��
				builder.appendSql(" and parent.FState = ? ");
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

				// ����¼û�а�ʱ�ſ������
				builder
						.appendSql(" and (entry.FIsAdd = ? or entry.FIsAdd is null)");
				builder.addParam(new Boolean(false));
				/*builder.appendSql(" and item.fmonth=?");
				builder.addParam(new Integer(lastMonth));*/
				IRowSet rowSetTemp = builder.executeQuery();

				while ((rowSetTemp != null) && (rowSetTemp.next())) {
					result = rowSetTemp.getBigDecimal("FAmount");
				}
			}

		}
		return result;
	}

	/**
	 * Ԥ��ϵͳ���������ֹ�ڼ�ƻ�����ȡ������
	 * 
	 * @param ctx
	 * @param projectID
	 * @param costAccountID
	 * @param transformDate
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static BigDecimal getUpToBudgetPay(Context ctx, List costAccountID,
			Date startDate, Date endDate) throws BOSException, SQLException {
		endDate = FDCDateHelper.addDays(endDate, 1);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		BigDecimal result = FDCHelper.ZERO;
		if (costAccountID.size() > 0) {
			builder
					.appendSql(" select sum(entry.FAmount) as FAmount  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
			builder
					.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
			builder
					.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
			builder
					.appendSql(" where  fisLatestVer=1 and entry.FCostAccountId in ( ");
			builder.appendParam(costAccountID.toArray());
			builder.appendSql(" ) and ");

			builder.appendSql(" ( period.FEndDate between ");
			builder.appendParam(new java.sql.Date(startDate.getTime()));
			builder.appendSql(" and ");
			builder.appendParam(new java.sql.Date(endDate.getTime()));

			// ��������Ϊ�������ſ���ȡ��
			builder.appendSql(" ) and parent.FState = ? ");
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);

			//����¼û�а�ʱ�ſ������
			builder
					.appendSql(" and (entry.FIsAdd = ? or entry.FIsAdd is null)");
			builder.addParam(new Boolean(false));

			IRowSet rowSetTemp = builder.executeQuery();

			while ((rowSetTemp != null) && (rowSetTemp.next())) {
				result = rowSetTemp.getBigDecimal("FAmount");
			}

		}
		return result;
	}
	/**
	 * Ԥ���ڼ�
	 * @param serverCtx
	 * @param periodNumber
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static BgPeriodInfo getBgPeriod(Context serverCtx, String periodNumber) throws BOSException, EASBizException {
		IBgPeriod iBgPeriod = serverCtx == null ? BgPeriodFactory.getRemoteInstance() : BgPeriodFactory.getLocalInstance(serverCtx);
		//Oh my god,��Ȼ������Ϊ�յ������ֱ����Ϊ��������Ϊ�ɺ޵���û�в����쳣   by Cassiel_peng 2009-11-30
		if(periodNumber==null){
			periodNumber="";
		}
//		return iBgPeriod.getBgPeriodInfo("SELECT * WHERE number = '" + periodNumber + "'")==null?new BgPeriodInfo():iBgPeriod.getBgPeriodInfo("SELECT * WHERE number = '" + periodNumber + "'");
		try {
			return iBgPeriod.getBgPeriodInfo("SELECT * WHERE number = '" + periodNumber + "'");
		} catch (ObjectNotFoundException e) {
			return new BgPeriodInfo();
		}
	}
	
}
