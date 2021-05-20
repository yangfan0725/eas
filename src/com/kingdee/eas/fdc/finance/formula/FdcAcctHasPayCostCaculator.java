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
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.jdbc.rowset.IRowSet;
/**
 * 
 * @author kelvin_yang
 * @work ���ز���������ʵ�ֳɱ�ȡ����ʽ
 * @method fdc_acct_hasPaid_cost �¶�¼�룺����ͬδ¼���򷵻ظ������и����ֵ��ÿ�Ŀ����ʵ�ֳɱ���
 * 								 		 ����ͬ¼�룬 �򷵻ظ��¸ú�ͬ�����и����ֵ��ÿ�Ŀ����ʵ�ֳɱ���
 *  							 ���¶�δ¼�룺����ͬδ¼���򷵻����и����ֵ��ÿ�Ŀ����ʵ�ֳɱ���
 *  										����ͬ¼�룬�򷵻ظú�ͬ�����и����ֵ��ÿ�Ŀ����ʵ�ֳɱ���
 */
public class FdcAcctHasPayCostCaculator implements ICalculator, IMethodBatchQuery {
	private Context ServerCtx = null;
	//��˾����
	private String fdcCompanyNumber = null ;
	//��Ŀ������
	private String prjNumber = null ;
	//�ɱ���Ŀ������
	private String acctLongNumber = null ;
	//�¶�
	private String month = null;
	//��ͬ
	private String conNumber = null;
	//CUid
	private String cuId = null;
	
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
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_acct_hasPay_cost");
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
					BigDecimal amount = this.fdc_acct_hasPay_cost(
							fdcCompanyNumber, prjNumber, acctLongNumber , month, conNumber);
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
	 * ���ز���������ʵ�ֳɱ�ȡ����ʽ
	 * @param fdcCompanyNumber ��˾����
	 * @param prjNumber  ��Ŀ������
	 * @param acctLongNumber �ɱ���Ŀ������
	 * @param conNumber ��ͬ����
	 * @param month �¶�
	 * @return
	 * @throws ParseException
	 * @throws BOSException
	 * @throws SQLException
	 */
	public BigDecimal fdc_acct_hasPay_cost(String fdcCompanyNumber,String prjNumber,String acctLongNumber,String month, String conNumber) throws ParseException, BOSException, SQLException
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
//		try {
//			initCU(projectID);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		builder.appendSql(" select sum(entry.FAmount) as FAmount from T_FNC_PaymentSplitEntry entry ");
		builder.appendSql(" inner join T_FNC_PaymentSplit head on entry.FParentId=head.FId ");
		builder.appendSql(" inner join T_FDC_CurProject prj on head.FCurProjectId = prj.FID ");
		builder.appendSql(" inner join T_FDC_CostAccount cost on cost.FID = entry.FCostAccountID ");
		if(!"".equals(conNumber)){
			builder.appendSql(" inner join T_CON_ContractBill con on head.FContractBillID = con.FID ");
		}
//		builder.appendSql(" where FCostBillType='PAYMENTSPLIT' and head.fcontrolUnitId=? ");
//		builder.addParam(cuId);
		builder.appendSql(" where head.FIsInvalid=0 and head.FCurProjectId =? ");
		builder.addParam(projectID);
		builder.appendSql(" and head.FConWithoutTextID is null ");
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
			
			builder.appendSql(" and  head.FCreateTime >= ?");
			builder.addParam(firstDay);
			builder.appendSql(" and head.FCreateTime < ?");
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
	public void initCU(String prjId) throws Exception{
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select fcontrolUnitId from T_FDC_CurProject where fid=?");
		IRowSet rowSet=builder.executeQuery(ServerCtx);
		if(rowSet.size()>0){
			rowSet.next();
			this.cuId=rowSet.getString("fcontrolUnitId");
		}
	}
}
