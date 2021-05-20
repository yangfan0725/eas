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
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.jdbc.rowset.IRowSet;
/**
 * 
 * @author kelvin_yang
 * @work ���ز���ͬ������ȡ��
 * @method �¶�¼�룺����ͬδ¼���򷵻ظ������б����ֵ��ÿ�Ŀ��ֵ������ͬ¼�룬�򷵻ظ��¸ú�ͬ�����б����ֵ��ÿ�Ŀ��ֵ��
 *         ���¶�δ¼�룺����ͬδ¼���򷵻����б����ֵ��ÿ�Ŀ��ֵ������ͬ¼�룬�򷵻ظú�ͬ�����б����ֵ��ÿ�Ŀ��ֵ��
 */
public class FdcConChangeSplitCaculator implements IMethodBatchQuery,
		ICalculator {
	private Context ServerCtx = null;
	//��˾����
	private String fdcCompanyNumber = null ;
	//��Ŀ������
	private String prjNumber = null ;
	//�ɱ���Ŀ������
	private String acctLongNumber = null ;
	//��ͬ����
	private String conNumber = null;
	//�¶�
	private String month = null;
	
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
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_acct_conChange_split");
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();

				fdcCompanyNumber = (String) ((Variant) obj[0]).getValue();
				prjNumber = (String) ((Variant) obj[1]).getValue();
				acctLongNumber = (String) ((Variant) obj[2]).getValue();
				month = (String) ((Variant) obj[3]).getValue();
				conNumber = (String) ((Variant) obj[4]).getValue();

				try {
					//ͨ�����㹫ʽ���㣬���ؽ��
					BigDecimal amount = this.fdc_acct_conChange_split(
							fdcCompanyNumber, prjNumber, acctLongNumber, conNumber , month);
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
	 * ���ز���ͬ������ȡ����ʽ
	 * @param fdcCompanyNumber ��˾����
	 * @param prjNumber  ��Ŀ������
	 * @param acctLongNumber �ɱ���Ŀ������
	 * @param conNumber ��ͬ����
	 * @return
	 * @throws ParseException
	 * @throws BOSException
	 * @throws SQLException
	 */
	public BigDecimal fdc_acct_conChange_split(String fdcCompanyNumber,String prjNumber,String acctLongNumber,String conNumber, String month) throws ParseException, BOSException, SQLException
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * �����滻��̾��
		 */
//		fdcCompanyNumber = fdcCompanyNumber.replace('.', '!');
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('.','!');
		
		//�������¶ȣ�����Ӧ�ö�����ת��������ûÿ���µ����һ���ʱ��
		Date lastDay = null ;
		Date firstDay = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		//���¼�����¶�
		if(!"".equals(month)){
			lastDay = df2.parse(month);
			//���һ��ȡ���ʱ��
			lastDay = FDCDateHelper.getLastDayOfMonth(lastDay);
			firstDay = df2.parse(month);
			firstDay = FDCDateHelper.getFirstDayOfMonth(firstDay);
		}
		
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
		
		String projectID = "" ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			projectID = curProjectID.get(0).toString() ;
		}
		BigDecimal result = FDCHelper.ZERO;

		builder.appendSql(" select sum(entry.FAmount) as FAmount from T_CON_ConChangeSplitEntry entry ");
		builder.appendSql(" inner join T_CON_ConChangeSplit split on entry.FParentID = split.FID ");
		builder.appendSql(" inner join T_FDC_CurProject prj on split.FCurProjectID = prj.FID ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = entry.FCostAccountID ");
		builder.appendSql(" inner join T_CON_ContractBill con on split.FContractBillID = con.fid ");
		//������Ŀid
		builder.appendSql(" where split.FCurProjectId = ?");
		builder.addParam(projectID);
		builder.appendSql( " and ( cost.FlongNumber = ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " or charindex ( ? ");
		builder.addParam(acctLongNumber);
		builder.appendSql( " ||'!', cost.FlongNumber ) = 1 ) " );
		if(!"".equals(conNumber)){
			builder.appendSql(" and con.FNumber = ?");
			builder.addParam(conNumber);
		}
		//���¼���¶�
		if(lastDay != null && firstDay != null){
			
			builder.appendSql(" and  split.FCreateTime >= ?");
			builder.addParam(firstDay);
			builder.appendSql(" and split.FCreateTime < ?");
			builder.addParam(lastDay);
		}
		//Ҷ�ӽڵ�
		builder.appendSql(" and entry.FIsLeaf = ?");
		builder.addParam(new Integer(1));
		
		IRowSet rowSetTemp = builder.executeQuery(ServerCtx);
		
		while((rowSetTemp != null) && (rowSetTemp.next())){
			result = rowSetTemp.getBigDecimal("FAmount");
		}

		if(result == null){
			result = FDCHelper.ZERO;			
		}
		return result;
	}


}
