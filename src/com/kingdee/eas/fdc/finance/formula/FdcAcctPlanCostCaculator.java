package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;

/**
 * @author xiaobin_li
 * @work: ʵ�ַ��ز��µĳɱ�ȡ�����ʽ�ƻ�ȡ��
 * @method:fdc_acct_plan_cost    (�ʽ�ƻ�ȡ��-�ƻ��ɱ�ȡ����ʽ)
 */

public class FdcAcctPlanCostCaculator implements ICalculator, IMethodBatchQuery 
{
	private Context ServerCtx = null;
	
	private String fdcCompanyNumber = null ;
	private String prjNumber = null ;
	private String acctLongNumber = null ;
	private String month = null;
	
	private ICalculateContextProvider context;
	
	public FdcAcctPlanCostCaculator()
	{
		
	}
	public Context getServerCtx()
	{
		return ServerCtx;
	}
	
	public void setServerCtx(Context serverCtx)
	{
		ServerCtx = serverCtx;
	}
	
	public void initCalculateContext(ICalculateContextProvider context)
	{
		this.context = context;
		this.ServerCtx = this.context.getServerContext();
	}
	
	public boolean batchQuery(Map methods)
	{
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_acct_plan_cost");
		if (params != null)
		{
			for(int i=0;i<params.size();i++)
			{
			Parameter param = params.getParameter(i);
			Object[] obj = param.getArgs();
			
			fdcCompanyNumber = (String) ((Variant) obj[0]).getValue();
			prjNumber =(String) ((Variant) obj[1]).getValue();
			acctLongNumber = (String) ((Variant) obj[2]).getValue();
			month = (String) ((Variant) obj[3]).getValue();
	
		
		try
		{
			BigDecimal amount = this.fdc_acct_plan_cost(fdcCompanyNumber, prjNumber,acctLongNumber, month);
			params.getParameter(i).setValue(amount);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		}
	}
		return true;
	}
	
	/**
	 * �ʽ�ƻ�ȡ��--�ƻ��ɱ�ȡ����ʽ
	 * @param fdcCompanyNumber ��˾����
	 * @param prjNumber        ��Ŀ������
	 * @param acctLongNumber   �ɱ���Ŀ������
	 * @param month            �¶�
	 * @return T_FNC_FDCMonthBudgetAcctEntry.FCost�ܺ�
	 * @throws SQLException 
	 * @throws ParseException 
	 * @throws BOSException 
	 */
	public BigDecimal fdc_acct_plan_cost(String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month) throws SQLException, ParseException, BOSException
	{
		FDCSQLBuilder builder = new FDCSQLBuilder(ServerCtx) ;
		
		/**
		 * �����滻��̾��
		 */
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//�������¶ȣ�����Ӧ�ö�����ת��������ûÿ���µ����һ���ʱ��
		Date transformDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		
		transformDate = df2.parse(month);
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
		
		IRowSet rowSet = builder.executeQuery(ServerCtx);
	
		while((rowSet != null) && (rowSet.next()))
		{
			curProjectID.add(rowSet.getString("fid"));
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
		
		//�ж�ȡ����ȡ��ǰ�»�������,����������
		Integer year = new Integer(transformDate.getYear()+1900);
		Integer month2 = new Integer(transformDate.getMonth()+1);
		int lastMonth=0;
		builder.clear();
		builder.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent ");
		builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
		builder.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ? and fisLatestVer=1 ");
		//��������Ϊ�������ſ���ȡ��
		
		builder.addParam(projectID);
		builder.addParam(year);
		builder.addParam(month2);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		if(builder.isExist()){
			lastMonth=0;
		}else{
			int myYear=year.intValue();
			int myMonth=month2.intValue();
			if(month2.intValue()==1){
				myYear=year.intValue()-1;
				myMonth=12;
			}else{
				myYear=year.intValue();
				myMonth=month2.intValue()-1;
			}
			builder.clear();
			builder.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent  ");
			builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
			builder.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ?  and fisLatestVer=1 ");
			
			//��������Ϊ�������ſ���ȡ��
			
			builder.addParam(projectID);
			builder.addParam(new Integer(myYear));
			builder.addParam(new Integer(myMonth));
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
			if(builder.isExist()){
				lastMonth=1;
				year=new Integer(myYear);
				month2=new Integer(myMonth);
			}else{
				if(month2.intValue()==1){
					myYear=year.intValue()-1;
					myMonth=11;
				}else if(month2.intValue()==2){
					myYear=year.intValue()-1;
					myMonth=12;
				}else{
					myYear=year.intValue();
					myMonth=month2.intValue()-2;
				}
				builder.clear();
				builder.appendSql(" select 1 from T_FNC_FDCMonthBudgetAcct parent ");
				builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId ");
				builder.appendSql(" where fprojectId=? and period.fyear=? and period.fmonth=? and parent.FState = ?  and fisLatestVer=1 ");
				builder.addParam(projectID);
				builder.addParam(new Integer(myYear));
				builder.addParam(new Integer(myMonth));
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
				//��������Ϊ�������ſ���ȡ��
				if(builder.isExist()){
					lastMonth=2;
					year=new Integer(myYear);
					month2=new Integer(myMonth);
				}else{
					return FDCHelper.ZERO;
				}
			}
		}
		builder.clear();
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

		//֮ǰΪ������������Դ�������һЩ���⣬�������¶������
		IRowSet rowSetRes = builder.executeQuery(ServerCtx);
		
		while((rowSetRes != null) && (rowSetRes.next()))
		{
			costAccountID.add(rowSetRes.getString("fid"));
		}

		builder.clear() ;
		
		//ִ��Sql����ȡ���������������Ϊ�գ��򷵻�FDCHelper.ZERO
		BigDecimal result = FDCHelper.ZERO ;
		
		if(costAccountID.size() > 0)
		{
			if(lastMonth==0){
				builder.appendSql(" select sum(entry.fcost) as fcost  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
				builder.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
				builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
				builder.appendSql( " where  fisLatestVer=1 and entry.FCostAccountId in ( ");
				builder.appendParam(costAccountID.toArray());
				builder.appendSql(" ) and ");
				
				//���year(���)Լ������
				//�����ȡ������ֵ��Ϊ�գ�����нضϲ�����ȡ�����(KSQL��)
				builder.appendSql(" period.FYear = ? ");
				builder.addParam( year);
				builder.appendSql( " and " );
				builder.appendSql(" period.FMonth = ? ");
				builder.addParam( month2);
				
				//��������Ϊ�������ſ���ȡ��
				builder.appendSql(" and parent.FState = ? ");
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
				
				//����¼û�а�ʱ�ſ������
				builder.appendSql( " and (entry.FIsAdd = ? or entry.FIsAdd is null)" );
				builder.addParam( new Boolean(false));
				
				IRowSet rowSetTemp = builder.executeQuery(ServerCtx);
				
				while((rowSetTemp != null) && (rowSetTemp.next()))
				{
					result = rowSetTemp.getBigDecimal("fcost");
				}
			}else{
				builder.appendSql(" select sum(item.fcost) as fcost  from T_FNC_FDCMonthBudgetAcctEntry  entry ");
				builder.appendSql(" inner join T_FNC_FDCMonthBudgetAcct parent on entry.FParentId = parent.FID ");
				builder.appendSql(" inner join T_FNC_FDCBudgetPeriod period on period.FID = parent.FfdcPeriodId");
				builder.appendSql(" inner join T_FNC_FDCMonthBudgetAcctItem item on item.fentryid=entry.fid");
				builder.appendSql( " where fisLatestVer=1 and entry.FCostAccountId in ( ");
				builder.appendParam(costAccountID.toArray());
				builder.appendSql(" ) and ");
				
				//���year(���)Լ������
				//�����ȡ������ֵ��Ϊ�գ�����нضϲ�����ȡ�����(KSQL��)
				builder.appendSql(" period.FYear = ? ");
				builder.addParam( year);
				builder.appendSql( " and " );
				builder.appendSql(" period.FMonth = ? ");
				builder.addParam( month2);
				
				//��������Ϊ�������ſ���ȡ��
				builder.appendSql(" and parent.FState = ? ");
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
				
				//����¼û�а�ʱ�ſ������
				builder.appendSql( " and (entry.FIsAdd = ? or entry.FIsAdd is null)" );
				builder.addParam( new Boolean(false));
				builder.appendSql(" and item.fmonth=?");
				builder.addParam(new Integer(transformDate.getMonth()+1));
				IRowSet rowSetTemp = builder.executeQuery(ServerCtx);
				
				while((rowSetTemp != null) && (rowSetTemp.next()))
				{
					result = rowSetTemp.getBigDecimal("fcost");
				}
			}
			
		}
		if(result == null)
		{
			result = FDCHelper.ZERO ;
		}
		return result;
	}
}
