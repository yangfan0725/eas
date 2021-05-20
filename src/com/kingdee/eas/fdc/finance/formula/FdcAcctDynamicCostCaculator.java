package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.jdbc.rowset.IRowSet;
/**
 * 
 * @author kelvin_yang
 * @work ���ز�������Ŀ��̬�ɱ�ȡ��
 * @method fdc_prj_dynamic_cost ���ɱ���Ŀδ¼�룬���ع�����Ŀ��̬�ɱ����������ɱ���Ŀ¼�룬�򷵻ع�����Ŀ�óɱ���Ŀ�Ķ�̬�ɱ�
 */
public class FdcAcctDynamicCostCaculator implements IMethodBatchQuery,
		ICalculator {
	private Context ServerCtx = null;
	//��˾����
	private String fdcCompanyNumber = null ;
	//��Ŀ������
	private String prjNumber = null ;
	//�ɱ���Ŀ������
	private String acctLongNumber = null ;
	
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
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_acct_dynamic_cost");
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();

				fdcCompanyNumber = (String) ((Variant) obj[0]).getValue();
				prjNumber = (String) ((Variant) obj[1]).getValue();
				acctLongNumber = (String) ((Variant) obj[2]).getValue();

				try {
					//ͨ�����㹫ʽ���㣬���ؽ��
					BigDecimal amount = this.fdc_acct_dynamic_cost(
							fdcCompanyNumber, prjNumber, acctLongNumber);
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
	 * ���ز�������Ŀ��̬�ɱ�ȡ��
	 * @param fdcCompanyNumber ��˾����
	 * @param prjNumber  ��Ŀ������
	 * @param acctLongNumber �ɱ���Ŀ������
	 * @return
	 * @throws ParseException
	 * @throws BOSException
	 * @throws SQLException
	 */
	public BigDecimal fdc_acct_dynamic_cost(String fdcCompanyNumber,String prjNumber,String acctLongNumber) throws ParseException, BOSException, SQLException
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
		BigDecimal result = FDCHelper.ZERO;
		//δ¼���Ŀ����
		if("".equals(acctLongNumber)){
			result = getDynCost(projectID,new HashSet());
		}else{
			builder.clear();
			builder.appendSql("select FID from T_FDC_CostAccount ");
			builder.appendSql(" where FCurProject = ? ");
			builder.addParam(projectID);
			//Ҷ�ӽڵ�
			builder.appendSql(" and FIsLeaf = ?");
			builder.addParam(new Integer(1));
			if(!"".equals(acctLongNumber)){
				builder.appendSql( " and ( ");
				builder.appendSql( " FlongNumber = ? ");
				builder.addParam(acctLongNumber);
				builder.appendSql( " or charindex ( ? ");
				builder.addParam(acctLongNumber);
				builder.appendSql( " ||'!', FlongNumber ) = 1 ) " );			
			}	
			
			Set costAccountIds = new HashSet();
			
			//֮ǰΪ������������Դ�������һЩ���⣬�������¶������
			IRowSet rowSetRes = builder.executeQuery(ServerCtx);
			
			while((rowSetRes != null) && (rowSetRes.next()))
			{
				costAccountIds.add(rowSetRes.getString("fid"));
			}
			if(costAccountIds.size() > 0){
				result = getDynCost(projectID,costAccountIds);
			}
		}
		if(result == null)
		{
			result = FDCHelper.ZERO ;
		}
		return result;
	}
	/**
	 * ��ö�̬�ɱ�  ��̬�ɱ� = Ŀ��ɱ� - �����ɱ�
	 * @param prjId
	 * @param acctIds
	 * @return
	 * @throws BOSException
	 */
	protected BigDecimal getDynCost(String prjId,Set acctIds) throws BOSException {
		BigDecimal aimcost = getAimCost(prjId,acctIds);
		BigDecimal adjustCost = getAdjustCost(prjId,acctIds);
		return aimcost.add(adjustCost);

	}

	/** 
	 * ���Ŀ��ɱ�
	 * @param prjId ������Ŀid
	 * @param acctIds �ɱ���Ŀids
	 * @return 
	 * @throws BOSException 
	 */
	private BigDecimal getAimCost(String prjId,Set acctIds) throws BOSException{
		if("".equals(prjId)){
			return FDCHelper.ZERO;
		}
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql(" select sum(FCostAmount) as amount from T_AIM_CostEntry entry ");
			builder.appendSql(" inner join T_AIM_AimCost head on head.fid=entry.fheadId ");
			builder.appendSql(" inner join T_FDC_CostAccount cost on entry.FCostAccountID = cost.FID ");
			builder.appendSql(" where head.FIsLastVersion=1 and ");
			builder.appendParam("head.FOrgOrProId", prjId);
			if(acctIds.size() > 0){
				builder.appendSql(" and entry.FCostAccountID in (");
				builder.appendParam(acctIds.toArray());
				builder.appendSql(" )  ");				
			}
			IRowSet rowSet=builder.executeQuery(ServerCtx);
			if(rowSet.size()==1){
				rowSet.next();
				return FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		return FDCHelper.ZERO;
	}
	/**
	 * ��õ����ɱ�
	 * @param prjId ������Ŀid
	 * @param acctIds �ɱ���Ŀids
	 * @return 
	 * @throws BOSException
	 */
	private BigDecimal getAdjustCost(String prjId,Set acctIds) throws BOSException {
		if("".equals(prjId)){
			return FDCHelper.ZERO;
		}
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(" select sum(FAdjustSumAmount) as amount from T_AIM_DynamicCost dyn");
			builder.appendSql(" inner join T_FDC_CostAccount acct on dyn.faccountid=acct.fid where ");
			builder.appendParam(" acct.fcurproject", prjId);
			if(acctIds.size() > 0){
				builder.appendSql(" and acct.Fid in (");
				builder.appendParam(acctIds.toArray());
				builder.appendSql(" )  ");				
			}
			IRowSet rowSet = builder.executeQuery(ServerCtx);
			if(rowSet.size()==1){
				rowSet.next();
				return FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return FDCHelper.ZERO;
	}

}
