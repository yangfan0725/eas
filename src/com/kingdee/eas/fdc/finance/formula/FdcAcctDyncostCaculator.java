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
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;

/**
 * @author xiaobin_li
 * @work: ʵ�ַ��ز��µĳɱ�ȡ�����ʽ�ƻ�ȡ��
 * @method:fdc_acct_dyncost      (�ɱ�ȡ��-��̬�ɱ�ȡ����ʽ) 
 */
public class FdcAcctDyncostCaculator implements ICalculator, IMethodBatchQuery
{
	private Context ServerCtx = null;
	
	private String fdcCompanyNumber = null ;
	private String prjNumber = null ;
	private String acctLongNumber = null ;
	private String month = null;
	
	private ICalculateContextProvider context;
	public FdcAcctDyncostCaculator()
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
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_acct_dyncost");
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
			BigDecimal amount = this.fdc_acct_dyncost(fdcCompanyNumber, prjNumber,acctLongNumber, month);
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
	 * �ɱ�ȡ��--��̬�ɱ�ȡ����ʽ
	 * @param ServerCtx              ������
	 * @param fdcCompanyNumber ��˾����
	 * @param prjNumber        ��Ŀ������
	 * @param acctLongNumber   �ɱ���Ŀ������
	 * @param month            �¶�
	 * @return DynCostSnapShot�е�dynCostAmt 
	 * @throws SQLException 
	 * @throws ParseException 
	 * @throws BOSException 
	 */
	public BigDecimal fdc_acct_dyncost(String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month) throws SQLException, ParseException, BOSException 
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * �����滻��̾��
		 */
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('!','.');
		
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
		BigDecimal result = FDCHelper.ZERO ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			String projectID = curProjectID.get(0).toString() ;

			builder.appendSql(" select sum( FDynCostAmt ) as FDynCostAmt from T_AIM_DynCostSnapShot ");
			builder.appendSql(" where fprojectid=? and charindex(?||'.',fcostacctlgnumber||'.')=1 and FSnapShotDate=? and FSavedType =?");
			builder.addParam(projectID);
			builder.addParam(acctLongNumber);
			builder.addParam( transformDate );
			builder.addParam(CostMonthlySaveTypeEnum.AUTOSAVE_VALUE);
			
			IRowSet rowSetTemp = builder.executeQuery(ServerCtx);
			
			while((rowSetTemp != null) && (rowSetTemp.next()))
			{
				result = rowSetTemp.getBigDecimal("FDynCostAmt");
			}
		}
		
		if(result == null)
		{
			result = FDCHelper.ZERO ;
		}
		return result;
	}
}

