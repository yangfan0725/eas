package com.kingdee.eas.fdc.contract.programming.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;

public class ProgDynaTableFacadeControllerBean extends AbstractProgDynaTableFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.contract.programming.app.ProgDynaTableFacadeControllerBean");

	/**
	 * �滮��̬��ȡ�� ����: selectObjID ��ǰ��ѡ�ڵ㣬�����ǹ�����Ŀ��Ҳ��������֯ID selectObjIsPrj ��ǰ��ѡ�ڵ��Ƿ񹤳���Ŀ leafPrjIDs ͨ���ݹ�������ȡ��ѡ�ڵ��µ�������ϸ������Ŀ��
	 */
	protected RetValue _getCollectionProgDynTab(Context ctx, ParamValue paramValue) throws BOSException, EASBizException {
		if (paramValue == null) {
			throw new NullPointerException("bad param!");
		}
		String selectObjID = (String) paramValue.get("selectObjID");
		RetValue retValue = new RetValue();// ���ڴ洢��������
		List lineList = new ArrayList();// ���ڴ洢һ����Լ��Ӧ������ͬ

		// // ȡ��ܺ�Լ�������
		// FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// builder.appendSql(" select prog.fid progId,prog.flevel progLevel,prog.flongNumber progLongNumber, ");
		// builder.appendSql(" prog.fname_l2 progName,prog.famount progAmount,prog.fcontrolAmount progControlAmount, ");
		// builder.appendSql(" prog.fbalance progBalance,prog.fcontrolBalance progControlBalance from T_CON_ProgrammingContract prog ");
		// builder.appendSql(" left join T_CON_Programming programming on prog.FProgrammingID = programming.FID ");
		// builder.appendSql(" where programming.FIsLatest = 1 ");
		// builder.appendSql(" programming.FProjectID = '" + selectObjID + "' ");
		// builder.appendSql(" order by flongNumber");
		// IRowSet rowSet = builder.executeQuery();
		// try {
		// while (rowSet.next()) {
		// Map lineMap = new HashMap();// ���ڴ洢һ������
		// lineMap.put("progContId", rowSet.getString("progId"));// ��ܺ�ԼID
		// lineMap.put("level", rowSet.getString("progLevel"));// ��ܺ�Լ��������
		// lineMap.put("longNumber", rowSet.getString("flongNumber"));// ��ܺ�Լ������
		// lineMap.put("name", rowSet.getString("progName"));// ��ܺ�Լ����
		// lineMap.put("amount", rowSet.getString("progAmount"));// ��ܺ�Լ�滮���
		// lineMap.put("controlAmount", rowSet.getString("progControlAmount"));// ��ܺ�Լ���ƽ��
		// lineMap.put("balance", rowSet.getString("progBalance"));// ��ܺ�Լ�滮���
		// lineMap.put("controlBalance", rowSet.getString("progControlBalance"));// ��ܺ�Լ���ƽ��
		//
		// // ȡ�����б��������ɺ�ͬ���������
		//
		// // ȡ��ֻ�������б�����
		//
		// }
		// } catch (SQLException e1) {
		// e1.printStackTrace();
		// }
		// retValue.put("AllInfo", lineList);
		// builder.appendSql("select prog.fdescription_l2 progDescription,prog.flevel progLevel,prog.flongNumber progNumber,prog.fname_l2 progName,prog.famount progAmount,prog.fcontrolAmount progControlAmount,prog.fbalance progBalance,");
		// builder.appendSql("prog.fcontrolBalance progControlBalance,invite.fname inviteName,cont.fname contName,SUPPLIER.FName_l2 supplierName,cont.famount contAmount,");
		// builder.appendSql("change.famount changeAmount,pay.FLatestPrice payLatestPrice,settle.fcurSettlePrice settleCurSettlePrice from T_CON_PROGRAMMINGCONTRACT prog ");
		// builder.appendSql(" left outer join T_INV_InviteProject invite on prog.fid = FProgrammingContractId ");
		// builder.appendSql(" left outer JOIN T_CON_CONTRACTBILL cont on prog.fid = cont.FProgrammingContract");
		// builder.appendSql(" left outer join t_con_programming programming on programming.fid = prog.FProgrammingID");
		// builder.appendSql(" left outer JOIN T_BD_Supplier SUPPLIER ON SUPPLIER.fid = cont.FPartBID");
		// builder.appendSql(" left outer JOIN T_con_contractchangebill change on cont.fid = change.FContractBillID ");
		// builder.appendSql(" left outer JOIN T_CON_PayRequestBill pay on cont.fid = pay.fcontractId ");
		// builder.appendSql(" left outer JOIN T_CON_ContractSettlementBill settle on cont.fid = settle.FContractBillID ");
		// builder.appendSql(" where prog.fid in( "+getParam(progContIds));
		// // builder.appendParam("prog.fid", progContIds.toArray());
		// builder.appendSql(") and programming.FIsLatest = 1 and programming.fstate = '4AUDITTED'  (or cont.fstate='4AUDITTED'");
		// builder.appendSql(" or invite.fstate='4AUDITTED' or change.fstate in('4AUDITTED','8VISA','7ANNOUNCE') or settle.fstate='4AUDITTED' ");
		// builder.appendSql(" or pay.fstate='4AUDITTED')");
		// // builder.appendSql(" and prog.fid in("+progContIds+")");
		// builder.appendSql(" order by prog.flongNumber");
		// // IRowSet rowSet = builder.executeQuery();
		// try {
		// while(rowSet.next()){
		// RetValue subRet = new RetValue();
		// subRet.put("progDescription", rowSet.getString("progDescription"));
		// subRet.put("progLevel", rowSet.getString("progLevel"));
		// subRet.put("progNumber", rowSet.getString("progNumber"));
		// subRet.put("progName", rowSet.getString("progName"));
		// subRet.put("progAmount", rowSet.getString("progAmount"));
		// subRet.put("progControlAmount", rowSet.getString("progControlAmount"));
		// subRet.put("progBalance", rowSet.getString("progBalance"));
		// subRet.put("progControlBalance", rowSet.getString("progControlBalance"));
		// subRet.put("inviteName", rowSet.getString("inviteName"));
		// subRet.put("contName", rowSet.getString("contName"));
		// subRet.put("supplierName", rowSet.getString("supplierName"));
		// subRet.put("contAmount", rowSet.getString("contAmount"));
		// subRet.put("changeAmount", rowSet.getString("changeAmount"));
		// subRet.put("payLatestPrice", rowSet.getString("payLatestPrice"));
		// subRet.put("settleCurSettlePrice", rowSet.getString("settleCurSettlePrice"));
		// temp.add(subRet);
		// }
		// } catch (SQLException e) {
		// throw new BOSException(e);
		// }

		// ȡ��ܺ�Լ�������ĺ�ͬ��Ϣ
		// FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// IRowSet rowSet = null;
		// builder.clear();
		// builder.appendSql("select fid,fname,FPartBID,famount from t_con_contractbill where ");
		// builder.appendParam("programmingContract", progContIds);
		// // builder.appendSql(" group by fproducttypeid, fcostAcctLgNumber");
		// rowSet=builder.executeQuery();
		// RetValue productCostValues = new RetValue();
		// try{
		// ContractBillCollection contractBillCollection=new ContractBillCollection();
		// while(rowSet.next()){
		// ContractBillInfo info=new ContractBillInfo();
		// info.setId(BOSUuid.read(rowSet.getString("fid")));
		// info.setName(rowSet.getString("fname"));
		// contractBillCollection.add(info);
		// }
		// retValue.put("ContractBillCollection", contractBillCollection);
		// }catch(SQLException e){
		// throw new BOSException(e);
		// }

		return null;
	}

	private String getParam(Set list) {
		Object[] array = list.toArray();
		String temp = "";
		if (array.length == 0)
			return "0";
		for (int i = 0; i < array.length; i++) {
			temp += "'" + array[i] + "',";
		}
		return temp.substring(0, temp.length() - 1);
	}
}