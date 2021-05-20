package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * @author xiaobin_li
 * @work: ʵ�ַ��ز��µĳɱ�ȡ�����ʽ�ƻ�ȡ��
 * @method:fdc_acct_dyncost      (�ɱ�ȡ��-��̬�ɱ�ȡ����ʽ) 
 * @method:fdc_acct_aimcost      (�ɱ�ȡ��-Ŀ��ɱ�ȡ����ʽ)
 * @method:fdc_acct_budget_pay   (�ʽ�ƻ�ȡ��-Ԥ�㸶��ȡ����ʽ)
 * @method:fdc_acct_plan_pay     (�ʽ�ƻ�ȡ��-�ƻ�����ȡ����ʽ)
 * @method:fdc_acct_actual_pay   (�ʽ�ƻ�ȡ��-ʵ�ʸ���ȡ����ʽ)
 * @method:fdc_acct_budget_cost  (�ʽ�ƻ�ȡ��-Ԥ��ɱ�ȡ����ʽ)
 * @method:fdc_acct_plan_cost    (�ʽ�ƻ�ȡ��-�ƻ��ɱ�ȡ����ʽ)
 * @method:fdc_acct_actual_cost  (�ʽ�ƻ�ȡ��-ʵ�ʳɱ�ȡ����ʽ)
 *            
 */
public class FDCAccttormulaHelper 
{
	/**
	 * �ɱ�ȡ��--��̬�ɱ�ȡ����ʽ
	 * @param ctx              ������
	 * @param fdcCompanyNumber ��˾����
	 * @param prjNumber        ��Ŀ������
	 * @param acctLongNumber   �ɱ���Ŀ������
	 * @param month            �¶�
	 * @return DynCostSnapShot�е�dynCostAmt 
	 */
	public BigDecimal fdc_acct_dyncost(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month) 
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * �����滻��̾��
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//�������¶ȣ�����Ӧ�ö�����ת��������ûÿ���µ����һ���ʱ��
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
		transformDate = FDCDateHelper.getLastDayOfMonth2(transformDate);
		
		/**
		 * ͨ��T_ORG_CostCenter�е�number��fdcCompanyNumber,��ȡFCostCenterId
		 * Ȼ����T_FDC_CurProject��FCostCenterId��Ӧ�ļ����У���FlongNumber��prjNumber
		 * ��Ӧ�������Ȼ�󷵻�fid���ϣ���ͨ����˾����Ŀѡȡ��ǰ��Ŀ��ID
		 */
		builder.appendSql("select curProject.fid from T_FDC_CurProject curProject ");
		builder.appendSql(" inner join T_ORG_CostCenter costCenter on curProject.FFullOrgUnit = costCenter.fid ");
		builder.appendSql(" where costCenter.FNumber = ? ");
		builder.addParam(fdcCompanyNumber);
		builder.appendSql(" and ");
		builder.appendSql(" curProject.FLongNumber = ? ");
		builder.addParam(prjNumber);

		/**
		 * ����ǰ��Ŀ��ID����List�У������ڳɱ���Ŀ�в��Ҷ�Ӧ�ĳɱ���Ŀ
		 */
		List curProjectID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		//���builder�е�sql���
		builder.clear();
		
		/**
		 * ͨ��������õĵ�ǰ��ĿID��T_FDC_CostAccount�еĵ�ǰ��ĿID����ƥ��
		 * �ټ��ϳɱ���Ŀ�������Լ��������ȷ������ɱ���Ŀ��ID
		 */
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		builder.appendSql( " and ( ");
		builder.appendSql( " FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
//		builder.appendSql(" select sum( FAimCostAmt ) as FAimCostAmt  from T_AIM_DynCostSnapShot where FCostAccountId in ( ");
		builder.appendSql(" select sum( FDynCostAmt ) as FDynCostAmt  from T_AIM_DynCostSnapShot where FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) ");
		
		//���month(�¶�)Լ������
		builder.appendSql(" and ");
		builder.appendSql(" FSnapShotDate = ? ");
		builder.addParam( transformDate );
		
		builder.appendSql(" and FSavedType = ? ");
		builder.addParam(CostMonthlySaveTypeEnum.AUTOSAVE_VALUE);
		
		//ִ��Sql����ȡ���������������Ϊ�գ��򷵻�FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FDynCostAmt");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * �ɱ�ȡ��--Ŀ��ɱ�ȡ����ʽ
	 * @param ctx              ������
	 * @param fdcCompanyNumber ��˾����
	 * @param prjNumber        ��Ŀ������
	 * @param acctLongNumber   �ɱ���Ŀ������
	 * @param month            �¶�
	 * @return DynCostSnapShot�е�aimCost
	 */
	public BigDecimal fdc_acct_aimcost(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * �����滻��̾��
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//�������¶ȣ�����Ӧ�ö�����ת��������ûÿ���µ����һ���ʱ��
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
		transformDate = FDCDateHelper.getLastDayOfMonth2(transformDate);
		
		/**
		 * ͨ��T_ORG_CostCenter�е�number��fdcCompanyNumber,��ȡFCostCenterId
		 * Ȼ����T_FDC_CurProject��FCostCenterId��Ӧ�ļ����У���FlongNumber��prjNumber
		 * ��Ӧ�������Ȼ�󷵻�fid���ϣ���ͨ����˾����Ŀѡȡ��ǰ��Ŀ��ID
		 */
		builder.appendSql("select curProject.fid from T_FDC_CurProject curProject ");
		builder.appendSql(" inner join T_ORG_CostCenter costCenter on curProject.FFullOrgUnit = costCenter.fid ");
		builder.appendSql(" where costCenter.FNumber = ? ");
		builder.addParam(fdcCompanyNumber);
		builder.appendSql(" and ");
		builder.appendSql(" curProject.FLongNumber = ? ");
		builder.addParam(prjNumber);

		/**
		 * ����ǰ��Ŀ��ID����List�У������ڳɱ���Ŀ�в��Ҷ�Ӧ�ĳɱ���Ŀ
		 */
		List curProjectID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		//���builder�е�sql���
		builder.clear();
		
		/**
		 * ͨ��������õĵ�ǰ��ĿID��T_FDC_CostAccount�еĵ�ǰ��ĿID����ƥ��
		 * �ټ��ϳɱ���Ŀ�������Լ��������ȷ������ɱ���Ŀ��ID
		 */
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		builder.appendSql( " and ( ");
		builder.appendSql( " FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
		
		builder.appendSql(" select sum( FAimCostAmt ) as FAimCostAmt  from T_AIM_DynCostSnapShot where FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) ");
		
		//���month(�¶�)Լ������
		builder.appendSql(" and ");
		//�����ȡ������ֵ��Ϊ�գ�����нضϲ�����ȡ�����(KSQL��)
		builder.appendSql(" FSnapShotDate = ? ");
		builder.addParam( transformDate );
		
		builder.appendSql(" and FSavedType = ? ");
		builder.addParam(CostMonthlySaveTypeEnum.AUTOSAVE_VALUE);
		
		//ִ��Sql����ȡ���������������Ϊ�գ��򷵻�FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FAimCostAmt");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * �ʽ�ƻ�ȡ��--Ԥ�㸶��ȡ����ʽ
	 * @param ctx              ������
	 * @param fdcCompanyNumber ��˾����
	 * @param prjNumber        ��Ŀ������
	 * @param acctLongNumber   �ɱ���Ŀ������
	 * @param month            �¶�
	 * @return T_FNC_FDCYearBudgetAcctEntry.FAmount�ܺ�
	 */
	public BigDecimal fdc_acct_budget_pay(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * �����滻��̾��
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//�������¶ȣ�����Ӧ�ö�����ת��������ûÿ���µ����һ���ʱ��
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
		transformDate = FDCDateHelper.getLastDayOfMonth2(transformDate);
		
		/**
		 * ͨ��T_ORG_CostCenter�е�number��fdcCompanyNumber,��ȡFCostCenterId
		 * Ȼ����T_FDC_CurProject��FCostCenterId��Ӧ�ļ����У���FlongNumber��prjNumber
		 * ��Ӧ�������Ȼ�󷵻�fid���ϣ���ͨ����˾����Ŀѡȡ��ǰ��Ŀ��ID
		 */
		builder.appendSql("select curProject.fid from T_FDC_CurProject curProject ");
		builder.appendSql(" inner join T_ORG_CostCenter costCenter on curProject.FFullOrgUnit = costCenter.fid ");
		builder.appendSql(" where costCenter.FNumber = ? ");
		builder.addParam(fdcCompanyNumber);
		builder.appendSql(" and ");
		builder.appendSql(" curProject.FLongNumber = ? ");
		builder.addParam(prjNumber);

		/**
		 * ����ǰ��Ŀ��ID����List�У������ڳɱ���Ŀ�в��Ҷ�Ӧ�ĳɱ���Ŀ
		 */
		List curProjectID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		//���builder�е�sql���
		builder.clear();
		
		/**
		 * ͨ��������õĵ�ǰ��ĿID��T_FDC_CostAccount�еĵ�ǰ��ĿID����ƥ��
		 * �ټ��ϳɱ���Ŀ�������Լ��������ȷ������ɱ���Ŀ��ID
		 */
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		builder.appendSql( " and ( ");
		builder.appendSql( " FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
		builder.appendSql(" select sum(entry.FAmount) as FAmount  from T_FNC_FDCYearBudgetAcctEntry  entry ");
		builder.appendSql(" inner join T_FNC_FDCYearBudgetAcct parent on entry.FParentId = parent.FID ");
		builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
		builder.appendSql( " where entry.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");
		
		//���year(���)Լ������
		//�����ȡ������ֵ��Ϊ�գ�����нضϲ�����ȡ�����(KSQL��)
		builder.appendSql(" period.FYear = ? ");
		builder.addParam( new Integer(transformDate.getYear()+1900) );
		
		builder.appendSql(" and parent.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		
		builder.appendSql( " and parent.FIsLatestVer = ? ");
		builder.addParam( new Integer(1));
		
		//ִ��Sql����ȡ���������������Ϊ�գ��򷵻�FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FAmount");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * �ʽ�ƻ�ȡ��--�ƻ�����ȡ����ʽ
	 * @param ctx              ������
	 * @param fdcCompanyNumber ��˾����
	 * @param prjNumber        ��Ŀ������
	 * @param acctLongNumber   �ɱ���Ŀ������
	 * @param month            �¶�
	 * @return T_FNC_FDCMonthBudgetAcctEntry.FAmount�ܺ�
	 */
	public BigDecimal fdc_acct_plan_pay(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * �����滻��̾��
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//�������¶ȣ�����Ӧ�ö�����ת��������ûÿ���µ����һ���ʱ��
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
		transformDate = FDCDateHelper.getLastDayOfMonth2(transformDate);
		
		/**
		 * ͨ��T_ORG_CostCenter�е�number��fdcCompanyNumber,��ȡFCostCenterId
		 * Ȼ����T_FDC_CurProject��FCostCenterId��Ӧ�ļ����У���FlongNumber��prjNumber
		 * ��Ӧ�������Ȼ�󷵻�fid���ϣ���ͨ����˾����Ŀѡȡ��ǰ��Ŀ��ID
		 */
		builder.appendSql("select curProject.fid from T_FDC_CurProject curProject ");
		builder.appendSql(" inner join T_ORG_CostCenter costCenter on curProject.FFullOrgUnit = costCenter.fid ");
		builder.appendSql(" where costCenter.FNumber = ? ");
		builder.addParam(fdcCompanyNumber);
		builder.appendSql(" and ");
		builder.appendSql(" curProject.FLongNumber = ? ");
		builder.addParam(prjNumber);

		/**
		 * ����ǰ��Ŀ��ID����List�У������ڳɱ���Ŀ�в��Ҷ�Ӧ�ĳɱ���Ŀ
		 */
		List curProjectID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		//���builder�е�sql���
		builder.clear();
		
		/**
		 * ͨ��������õĵ�ǰ��ĿID��T_FDC_CostAccount�еĵ�ǰ��ĿID����ƥ��
		 * �ټ��ϳɱ���Ŀ�������Լ��������ȷ������ɱ���Ŀ��ID
		 */
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		builder.appendSql( " and ( ");
		builder.appendSql( " FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
		
		builder.appendSql(" select sum(entry.FAmount) as FAmount  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
		builder.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
		builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
		builder.appendSql( " where entry.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");
		
		//���year(���)Լ������
		//�����ȡ������ֵ��Ϊ�գ�����нضϲ�����ȡ�����(KSQL��)
		builder.appendSql(" period.FYear = ? ");
		builder.addParam( new Integer(transformDate.getYear()+1900));
		builder.appendSql( " and " );
		builder.appendSql(" period.FMonth = ? ");
		builder.addParam( new Integer(transformDate.getMonth()+1));
		
		//��������Ϊ�������ſ���ȡ��
		builder.appendSql(" and parent.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		
		//����¼û�а�ʱ�ſ������
		builder.appendSql( " and entry.FIsAdd = ? " );
		builder.addParam( new Boolean(false));
		
		//ִ��Sql����ȡ���������������Ϊ�գ��򷵻�FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FAmount");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * �ʽ�ƻ�ȡ��--ʵ�ʸ���ȡ����ʽ
	 * @param ctx              ������
	 * @param fdcCompanyNumber ��˾����
	 * @param prjNumber        ��Ŀ������
	 * @param acctLongNumber   �ɱ���Ŀ������
	 * @param month            �¶�
	 * @return 
	 */
	public BigDecimal fdc_acct_actual_pay(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * �����滻��̾��
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//�������¶ȣ�����Ӧ�ö�����ת��������ûÿ���µ����һ���ʱ��
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
		transformDate = FDCDateHelper.getLastDayOfMonth2(transformDate);
		
		/**
		 * ͨ��T_ORG_CostCenter�е�number��fdcCompanyNumber,��ȡFCostCenterId
		 * Ȼ����T_FDC_CurProject��FCostCenterId��Ӧ�ļ����У���FlongNumber��prjNumber
		 * ��Ӧ�������Ȼ�󷵻�fid���ϣ���ͨ����˾����Ŀѡȡ��ǰ��Ŀ��ID
		 */
		builder.appendSql("select curProject.fid from T_FDC_CurProject curProject ");
		builder.appendSql(" inner join T_ORG_CostCenter costCenter on curProject.FFullOrgUnit = costCenter.fid ");
		builder.appendSql(" where costCenter.FNumber = ? ");
		builder.addParam(fdcCompanyNumber);
		builder.appendSql(" and ");
		builder.appendSql(" curProject.FLongNumber = ? ");
		builder.addParam(prjNumber);

		/**
		 * ����ǰ��Ŀ��ID����List�У������ڳɱ���Ŀ�в��Ҷ�Ӧ�ĳɱ���Ŀ
		 */
		List curProjectID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		//���builder�е�sql���
		builder.clear();
		
		/**
		 * ͨ��������õĵ�ǰ��ĿID��T_FDC_CostAccount�еĵ�ǰ��ĿID����ƥ��
		 * �ټ��ϳɱ���Ŀ�������Լ��������ȷ������ɱ���Ŀ��ID
		 */
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		builder.appendSql( " and ( ");
		builder.appendSql( " FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
		
		builder.appendSql(" select sum(acctPay.FAmount) as FAmount  from T_FNC_PayRequestAcctPay acctPay ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = acctPay.FCostAccountId ");
		builder.appendSql(" inner join T_CON_PayRequestBill bill on bill.FID = acctPay.FPayRequestBillId ");
		builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = acctPay.FPeriodId ");
		builder.appendSql( " where acctPay.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");
		
		builder.appendSql( " period.FYear = ? " );
		builder.addParam(new Integer(transformDate.getYear()+1900));
		
		builder.appendSql( " and period.FMonth = ? ");
		builder.addParam(new Integer(transformDate.getMonth()+1));
		
		builder.appendSql(" and bill.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		
		//ִ��Sql����ȡ���������������Ϊ�գ��򷵻�FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FAmount");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * �ʽ�ƻ�ȡ��--Ԥ��ɱ�ȡ����ʽ
	 * @param ctx              ������
	 * @param fdcCompanyNumber ��˾����
	 * @param prjNumber        ��Ŀ������
	 * @param acctLongNumber   �ɱ���Ŀ������
	 * @param month            �¶�
	 * @return T_FNC_FDCYearBudgetAcctEntry.FCost�ܺ�
	 */
	public BigDecimal fdc_acct_budget_cost(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * �����滻��̾��
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//�������¶ȣ�����Ӧ�ö�����ת��������ûÿ���µ����һ���ʱ��
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
		transformDate = FDCDateHelper.getLastDayOfMonth2(transformDate);
		
		/**
		 * ͨ��T_ORG_CostCenter�е�number��fdcCompanyNumber,��ȡFCostCenterId
		 * Ȼ����T_FDC_CurProject��FCostCenterId��Ӧ�ļ����У���FlongNumber��prjNumber
		 * ��Ӧ�������Ȼ�󷵻�fid���ϣ���ͨ����˾����Ŀѡȡ��ǰ��Ŀ��ID
		 */
		builder.appendSql("select curProject.fid from T_FDC_CurProject curProject ");
		builder.appendSql(" inner join T_ORG_CostCenter costCenter on curProject.FFullOrgUnit = costCenter.fid ");
		builder.appendSql(" where costCenter.FNumber = ? ");
		builder.addParam(fdcCompanyNumber);
		builder.appendSql(" and ");
		builder.appendSql(" curProject.FLongNumber = ? ");
		builder.addParam(prjNumber);

		/**
		 * ����ǰ��Ŀ��ID����List�У������ڳɱ���Ŀ�в��Ҷ�Ӧ�ĳɱ���Ŀ
		 */
		List curProjectID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		//���builder�е�sql���
		builder.clear();
		
		/**
		 * ͨ��������õĵ�ǰ��ĿID��T_FDC_CostAccount�еĵ�ǰ��ĿID����ƥ��
		 * �ټ��ϳɱ���Ŀ�������Լ��������ȷ������ɱ���Ŀ��ID
		 */
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		builder.appendSql( " and ( ");
		builder.appendSql( " FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
		builder.appendSql(" select sum(entry.FCost) as FCost  from T_FNC_FDCYearBudgetAcctEntry  entry ");
		builder.appendSql(" inner join T_FNC_FDCYearBudgetAcct parent on entry.FParentId = parent.FID ");
		builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
		builder.appendSql( " where entry.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");
		
		//���year(���)Լ������
		//�����ȡ������ֵ��Ϊ�գ�����нضϲ�����ȡ�����(KSQL��)
		builder.appendSql(" period.FYear = ? ");
		builder.addParam( new Integer(transformDate.getYear()+1900));
		
		builder.appendSql(" and parent.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		
		builder.appendSql( " and parent.FIsLatestVer = ? ");
		builder.addParam( new Integer(1));
		
		//ִ��Sql����ȡ���������������Ϊ�գ��򷵻�FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FCost");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * �ʽ�ƻ�ȡ��--�ƻ��ɱ�ȡ����ʽ
	 * @param ctx              ������
	 * @param fdcCompanyNumber ��˾����
	 * @param prjNumber        ��Ŀ������
	 * @param acctLongNumber   �ɱ���Ŀ������
	 * @param month            �¶�
	 * @return T_FNC_FDCMonthBudgetAcctEntry.FCost�ܺ�
	 */
	public BigDecimal fdc_acct_plan_cost(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * �����滻��̾��
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
	
		//�������¶ȣ�����Ӧ�ö�����ת��������ûÿ���µ����һ���ʱ��
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
		transformDate = FDCDateHelper.getLastDayOfMonth2(transformDate);
		
		/**
		 * ͨ��T_ORG_CostCenter�е�number��fdcCompanyNumber,��ȡFCostCenterId
		 * Ȼ����T_FDC_CurProject��FCostCenterId��Ӧ�ļ����У���FlongNumber��prjNumber
		 * ��Ӧ�������Ȼ�󷵻�fid���ϣ���ͨ����˾����Ŀѡȡ��ǰ��Ŀ��ID
		 */
		builder.appendSql("select curProject.fid from T_FDC_CurProject curProject ");
		builder.appendSql(" inner join T_ORG_CostCenter costCenter on curProject.FFullOrgUnit = costCenter.fid ");
		builder.appendSql(" where costCenter.FNumber = ? ");
		builder.addParam(fdcCompanyNumber);
		builder.appendSql(" and ");
		builder.appendSql(" curProject.FLongNumber = ? ");
		builder.addParam(prjNumber);

		/**
		 * ����ǰ��Ŀ��ID����List�У������ڳɱ���Ŀ�в��Ҷ�Ӧ�ĳɱ���Ŀ
		 */
		List curProjectID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		//���builder�е�sql���
		builder.clear();
		
		/**
		 * ͨ��������õĵ�ǰ��ĿID��T_FDC_CostAccount�еĵ�ǰ��ĿID����ƥ��
		 * �ټ��ϳɱ���Ŀ�������Լ��������ȷ������ɱ���Ŀ��ID
		 */
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		builder.appendSql( " and ( ");
		builder.appendSql( " FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
		
		builder.appendSql(" select sum(entry.FCost) as FCost  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
		builder.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
		builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
		builder.appendSql( " where entry.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");
		
		//���year(���)Լ������
		//�����ȡ������ֵ��Ϊ�գ�����нضϲ�����ȡ�����(KSQL��)
		builder.appendSql(" period.FYear = ? ");
		builder.addParam( new Integer(transformDate.getYear()+1900) );
		builder.appendSql( " and " );
		builder.appendSql(" period.FMonth = ? ");
		builder.addParam( new Integer(transformDate.getMonth()+1) );
		
		//��������Ϊ�������ſ���ȡ��
		builder.appendSql(" and parent.FState = ? ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		
		//����¼û�а�ʱ�ſ������
		builder.appendSql( " and entry.FIsAdd = ? " );
		builder.addParam( new Boolean(false));
		
		//ִ��Sql����ȡ���������������Ϊ�գ��򷵻�FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FCost");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * �ʽ�ƻ�ȡ��--ʵ�ʳɱ�ȡ����ʽ
	 * @param ctx              ������
	 * @param fdcCompanyNumber ��˾����
	 * @param prjNumber        ��Ŀ������
	 * @param acctLongNumber   �ɱ���Ŀ������
	 * @param month            �¶�
	 * @return 
	 */
	public BigDecimal fdc_acct_actual_cost(Context ctx,String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * �����滻��̾��
		 */
		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//�������¶ȣ�����Ӧ�ö�����ת��������ûÿ���µ����һ���ʱ��
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		  
		try {
			transformDate = df2.parse(month);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		   
		transformDate = FDCDateHelper.getLastDayOfMonth2(transformDate);
		
		/**
		 * ͨ��T_ORG_CostCenter�е�number��fdcCompanyNumber,��ȡFCostCenterId
		 * Ȼ����T_FDC_CurProject��FCostCenterId��Ӧ�ļ����У���FlongNumber��prjNumber
		 * ��Ӧ�������Ȼ�󷵻�fid���ϣ���ͨ����˾����Ŀѡȡ��ǰ��Ŀ��ID
		 */
		builder.appendSql("select curProject.fid from T_FDC_CurProject curProject ");
		builder.appendSql(" inner join T_ORG_CostCenter costCenter on curProject.FFullOrgUnit = costCenter.fid ");
		builder.appendSql(" where costCenter.FNumber = ? ");
		builder.addParam(fdcCompanyNumber);
		builder.appendSql(" and ");
		builder.appendSql(" curProject.FLongNumber = ? ");
		builder.addParam(prjNumber);

		/**
		 * ����ǰ��Ŀ��ID����List�У������ڳɱ���Ŀ�в��Ҷ�Ӧ�ĳɱ���Ŀ
		 */
		List curProjectID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					curProjectID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		//���builder�е�sql���
		builder.clear();
		
		/**
		 * ͨ��������õĵ�ǰ��ĿID��T_FDC_CostAccount�еĵ�ǰ��ĿID����ƥ��
		 * �ټ��ϳɱ���Ŀ�������Լ��������ȷ������ɱ���Ŀ��ID
		 */
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		builder.appendSql( " and ( ");
		builder.appendSql( " FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );
		

		List costAccountID = new ArrayList();
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			
			try {
				while(rowSet != null && rowSet.next())
				{
					costAccountID.add(rowSet.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
	
		builder.clear() ;
		
		builder.appendSql(" select sum(snap.FRealizedValue) as FRealizedValue  from T_AIM_DynCostSnapShot snap ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = snap.FCostAccountId ");
		builder.appendSql( " where snap.FCostAccountId in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" ) and ");
		
		//�����ȡ������ֵ��Ϊ�գ�����нضϲ�����ȡ�����(KSQL��)
		builder.appendSql(" snap.FSnapShotDate = ? ");
		builder.addParam( transformDate );
		
		builder.appendSql(" and snap.FSavedType = ? ");
		builder.addParam(CostMonthlySaveTypeEnum.AUTOSAVE_VALUE);
		
		//ִ��Sql����ȡ���������������Ϊ�գ��򷵻�FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		try {
			IRowSet rowSet = builder.executeQuery(ctx);
			try {
				while(rowSet != null && rowSet.next())
				{
					result = rowSet.getBigDecimal("FRealizedValue");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
