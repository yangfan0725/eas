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
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.jdbc.rowset.IRowSet;
/**
 * 
 * @author kelvin_yang
 * @work ���ز��������ɱ�����ȡ������
 * @method fdc_acct_intending_cost �ÿ�Ŀ�õ���ԭ��Ĵ������������ܣ����ɱ���Ŀû��¼�룬��Ϊ�ù�����Ŀ�õ���ԭ��Ĵ������ɱ�������
 *                                 ���ɱ���Ŀ�͵���ԭ��û��¼�룬��Ϊ�ù�����Ŀ���д������ɱ������ۼ�
 */
public class FdcIntendingCostCaculator implements ICalculator,
		IMethodBatchQuery {
	private Context ServerCtx = null;

	
	private ICalculateContextProvider context;
	
	public Context getServerCtx()
	{
		return ServerCtx;
	}

	public void setServerCtx(Context serverCtx)
	{
		ServerCtx = serverCtx;
	}
	public void initCalculateContext(ICalculateContextProvider context) {
		this.context = context;
		this.ServerCtx = this.context.getServerContext();
		
	}

	public boolean batchQuery(Map methods) {
		//��ôӽ��洫�ݹ�������ز���
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_acct_intending_cost");
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();

				 //��˾����
				String fdcCompanyNumber = (String) ((Variant) obj[0]).getValue();
				//��Ŀ������
				String prjNumber = (String) ((Variant) obj[1]).getValue();
				//�ɱ���Ŀ������
				String acctLongNumber = (String) ((Variant) obj[2]).getValue();
				//����ԭ��
				String adjustReason = (String) ((Variant) obj[3]).getValue();

				try {
					//ͨ�����㹫ʽ���㣬���ؽ��
					BigDecimal amount = this.fdc_acct_intending_cost(
							fdcCompanyNumber, prjNumber, acctLongNumber, adjustReason);
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
	 * ���ز��������ɱ�����ȡ������
	 * @param fdcCompanyNumber ��˾����
	 * @param prjNumber  ��Ŀ������
	 * @param acctLongNumber �ɱ���Ŀ������
	 * @param adjustReason ����ԭ�����
	 * @return
	 * @throws ParseException
	 * @throws BOSException
	 * @throws SQLException
	 */
	public BigDecimal fdc_acct_intending_cost(String fdcCompanyNumber,String prjNumber,String acctLongNumber,String adjustReason) throws ParseException, BOSException, SQLException
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		/**
		 * �����滻��̾��
		 */
//		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		//��Ϊ�ɱ���Ŀ�Ǳ�¼�����Ա����ж�һ��
		if(!"".equals(acctLongNumber))
			acctLongNumber = acctLongNumber.replace('.','!');
		
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
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
		
		//���builder�е�sql���
		builder.clear();
		builder.appendSql("select FID from T_FDC_CostAccount ");
		builder.appendSql(" where FCurProject = ? ");
		builder.addParam(projectID);
		//Ҷ�ӽڵ�
		builder.appendSql(" and FIsLeaf = ? ");
		builder.addParam(Boolean.TRUE);
		if(!"".equals(acctLongNumber)){
			builder.appendSql( " and ( ");
			builder.appendSql( " FlongNumber = ? ");
			builder.addParam(acctLongNumber);
			builder.appendSql( " or charindex ( ? ");
			builder.addParam(acctLongNumber);
			builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );			
		}	
		
		List costAccountID = new ArrayList();
		
		//֮ǰΪ������������Դ�������һЩ���⣬�������¶������
		IRowSet rowSetRes = builder.executeQuery(ServerCtx);
		
		while((rowSetRes != null) && (rowSetRes.next()))
		{
			costAccountID.add(rowSetRes.getString("fid"));
		}
		builder.clear();
		
		BigDecimal result = FDCHelper.ZERO;
		if(costAccountID.size() <= 0)
			return result;
		builder.appendSql(" select sum(entry.FCostAmount) as FAmount from T_AIM_AdjustRecordEntry entry ");
		builder.appendSql(" inner join T_AIM_DynamicCost dynamic on dynamic.FID = entry.FParentID ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = dynamic.FAccountID ");
		if(!"".equals(adjustReason)){
			builder.appendSql(" inner join T_FDC_AdjustReason reason on entry.FAdjustReasonID = reason.FID ");			
		}
		builder.appendSql( " where dynamic.FAccountID in ( ");
		builder.appendParam(costAccountID.toArray());
		builder.appendSql(" )  ");
		if(!"".equals(adjustReason)){
			builder.appendSql(" and reason.FNumber = ?");
			builder.addParam(adjustReason);
		}
		IRowSet rowSetTemp = builder.executeQuery(ServerCtx);

		while((rowSetTemp != null) && (rowSetTemp.next()))
		{
			result = rowSetTemp.getBigDecimal("FAmount");
		}
		if(result == null)
		{
			result = FDCHelper.ZERO ;
		}
		return result;
	}
}
