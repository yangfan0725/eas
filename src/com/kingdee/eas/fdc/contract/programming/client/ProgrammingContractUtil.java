package com.kingdee.eas.fdc.contract.programming.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProgrammingContractUtil {

	/**
	 * "Ŀ��ɱ�","�ѷ���","������","����Լ����"��ֵ0,��ע���
	 * 
	 * @param table
	 * @param rowIndex
	 * @param goalCost
	 * @param assigned
	 * @param assigning
	 * @param contractAssign
	 * @param description
	 */
	public static void setZero(KDTable table, int rowIndex, String goalCost, String assigned, String assigning, String contractAssign,
			String description) {
		table.getCell(rowIndex, goalCost).setValue(FDCHelper.ZERO);
		table.getCell(rowIndex, assigned).setValue(FDCHelper.ZERO);
		table.getCell(rowIndex, assigning).setValue(FDCHelper.ZERO);
		table.getCell(rowIndex, contractAssign).setValue(FDCHelper.ZERO);
		table.getCell(rowIndex, description).setValue(null);
	}
	
	/**
	 * �����ָ�������Ҫ�Ӷ�Ӧ�ġ���ܺ�Լ���ġ��滮���пۼ�����ǰָ��ġ��������
	 * @param idList ָ�id����
	 */
	public static void amountChange(List idList) throws Exception {
		String ids = FDCUtils.buildBillIds(idList);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT FContractBillID CON, ISNULL(SUM(FOriginalAmount), 0) AMOUNT FROM T_CON_ContractChangeBill\n");
		sql.append("	WHERE FID IN ").append(ids);
		sql.append("		GROUP BY FContractBillID");
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql(sql.toString());
		IRowSet rs = sqlBuilder.executeQuery();

		Map conMap = new HashMap();
		while (rs.next()) {
			String conId = rs.getString("CON");
			BigDecimal amount = rs.getBigDecimal("AMOUNT");
			conMap.put(conId, amount);
		}
		if (conMap.isEmpty()) {
			return;
		}

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("programmingContract");
		sic.add("programmingContract.changeAmount");

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", new HashSet(conMap.keySet())));

		EntityViewInfo evi = new EntityViewInfo();
		evi.setSelector(sic);
		evi.setFilter(filter);
		ContractBillCollection collection = ContractBillFactory.getRemoteInstance().getContractBillCollection(evi);

		IProgrammingContract iService = ProgrammingContractFactory.getRemoteInstance();
		sic.clear();
		sic.add("changeAmount");
		for (int i = 0, size = collection.size(); i < size; i++) {
			ContractBillInfo con = collection.get(i);
			BigDecimal conAmount = (BigDecimal) conMap.get(con.getId().toString());
			ProgrammingContractInfo programming = con.getProgrammingContract();
			BigDecimal amount = conAmount.add(programming.getChangeAmount());
			programming.setChangeAmount(amount);
			iService.updatePartial(programming, sic);
		}
	}

	/**
	 * ��ճɱ����ɵ�ǰ�����е��޸�
	 * 
	 * @param table
	 * @param rowIndex
	 * @param project
	 * @param number
	 * @param name
	 * @param goalCost
	 * @param assigned
	 * @param assigning
	 * @param contractAssign
	 * @param description
	 */
	public static void clearCell(KDTable table, int rowIndex, String project, String number, String name, String goalCost,
			String assigned, String assigning,
			String contractAssign, String description) {
		table.getCell(rowIndex, project).setValue(null);
		table.getCell(rowIndex, number).setValue(null);
		table.getCell(rowIndex, name).setValue(null);
		table.getCell(rowIndex, goalCost).setValue(null);
		table.getCell(rowIndex, assigned).setValue(null);
		table.getCell(rowIndex, assigning).setValue(null);
		table.getCell(rowIndex, contractAssign).setValue(null);
		table.getCell(rowIndex, description).setValue(null);
	}

	/**
	 * ��ճɱ����ɵ�ǰ��:"�ɱ���Ŀ����","�ɱ���Ŀ����","Ŀ��ɱ�","�ѷ���","������","����Լ����","��ע"
	 * 
	 * @param table
	 * @param rowIndex
	 * @param number
	 * @param name
	 * @param goalCost
	 * @param assigned
	 * @param assigning
	 * @param contractAssign
	 * @param description
	 */
	public static void clearCell(KDTable table, int rowIndex, String number, String name, String goalCost, String assigned,
			String assigning, String contractAssign, String description) {
		table.getCell(rowIndex, number).setValue(null);
		table.getCell(rowIndex, name).setValue(null);
		table.getCell(rowIndex, goalCost).setValue(null);
		table.getCell(rowIndex, assigned).setValue(null);
		table.getCell(rowIndex, assigning).setValue(null);
		table.getCell(rowIndex, contractAssign).setValue(null);
		table.getCell(rowIndex, description).setValue(null);
	}

	/**
	 * ��ճɱ����ɵ�ǰ��:"Ŀ��ɱ�","�ѷ���","������","����Լ����","��ע"
	 * 
	 * @param table
	 * @param rowIndex
	 * @param goalCost
	 * @param assigned
	 * @param assigning
	 * @param contractAssign
	 * @param description
	 */
	public static void clearCell(KDTable table, int rowIndex, String goalCost, String assigned, String assigning, String contractAssign,
			String description) {
		table.getCell(rowIndex, goalCost).setValue(null);
		table.getCell(rowIndex, assigned).setValue(null);
		table.getCell(rowIndex, assigning).setValue(null);
		table.getCell(rowIndex, contractAssign).setValue(null);
		table.getCell(rowIndex, description).setValue(null);
	}

	/**
	 * �жϵ���ĳ������Ŀ���Ƿ����ĳ�ɱ���Ŀ,�����򷵻�ID�����򷵻�NULL
	 * 
	 * @param project
	 * @param costAccount
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static String isExitCostAccount(CurProjectInfo project, CostAccountInfo costAccount) throws BOSException, SQLException {
		String costAccountID = null;
		if (project == null && costAccount == null) {
			return costAccountID;
		}
		FDCSQLBuilder buider = new FDCSQLBuilder();
		buider.appendSql("select costAccount.FID costAccountID from T_FDC_CostAccount costAccount ");
		buider.appendSql("left join T_FDC_CurProject project on costAccount.FCurProject = project.FID ");
		buider.appendSql("where project.FID = '"+project.getId()+"' ");
		buider.appendSql("and costAccount.FName_L2 = '" + costAccount.getName() + "' ");
		IRowSet rowSet = buider.executeQuery();
		if(rowSet.next()){
			costAccountID = rowSet.getString("costAccountID");
			return costAccountID;
		}
		return costAccountID;
	}

	/**
	 * ͨ��������Ŀ���ɱ���Ŀ��Ŀ��ɱ���Ϊ������ȡĿ��ɱ���ֵ
	 * 
	 * @param project
	 * @param costAccount
	 * @return
	 */
	public static BigDecimal getGoalCostBy_costAcc_aimCost(CostAccountInfo costAccount, AimCostInfo aimCostInfo) {
		BigDecimal goalCost = FDCHelper.ZERO;
		FDCSQLBuilder buider = new FDCSQLBuilder();
		buider.appendSql("select sum(costEntry.FCostAmount) FCostAmount from T_AIM_CostEntry costEntry ");
		buider.appendSql("left join T_FDC_CostAccount costAccount on costEntry.FCostAccountID = costAccount.FID ");
		buider.appendSql("left join T_FDC_CurProject project on costAccount.FCurProject = project.FID ");
		buider.appendSql("where costAccount.fid = '" + costAccount.getId().toString() + "' ");
		buider.appendSql("and costEntry.FHeadID = '" + aimCostInfo.getId() + "' ");
		IRowSet rowSet;
		try {
			rowSet = buider.executeQuery();
			if (rowSet.next() && rowSet.getBigDecimal("FCostAmount") != null) {
				goalCost = rowSet.getBigDecimal("FCostAmount");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return goalCost;
	}

	/**
	 * ��ȡ�µĹ�����Ŀ�����ĳɱ���ĿID
	 * 
	 * @param project
	 * @param costAccount
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static String getNewCostAccountID(CurProjectInfo project, CostAccountInfo costAccount) throws BOSException,
			SQLException {
		String costAccountID = null;
		if (project == null && costAccount == null) {
			return costAccountID;
		}
		FDCSQLBuilder buider = new FDCSQLBuilder();
		buider.appendSql("select costAccount.FID costAccountID from T_AIM_CostEntry costEntry ");
		buider.appendSql("left join T_FDC_CostAccount costAccount on costEntry.FCostAccountID = costAccount.FID ");
		buider.appendSql("left join T_FDC_CurProject project on costAccount.FCurProject = project.FID ");
		buider.appendSql("where project.FID = '" + project.getId() + "' ");
		buider.appendSql("and costAccount.FID = '" + costAccount.getId() + "' ");
		IRowSet rowSet = buider.executeQuery();
		if (rowSet.next()) {
			costAccountID = rowSet.getString("costAccountID");
			return costAccountID;
		}
		return costAccountID;
	}
	
	/**
	 * ��ȡ�µĹ�����Ŀ������ �ɱ���Ŀ��Ϣ���ɱ���Ŀ�����Ĺ�����Ŀ��Ϣ
	 * 
	 * @param costAccount
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static CostAccountInfo getCostAccountByNewID(String newCostAccountID) throws EASBizException, BOSException {
		if (newCostAccountID != null) {
			SelectorItemCollection selectCo = new SelectorItemCollection();
			selectCo.add("*");
			selectCo.add("curProject.*");
			CostAccountInfo newCostAccountInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(
					new ObjectUuidPK(newCostAccountID));
			return newCostAccountInfo;
		}
		return null;
	}

}
