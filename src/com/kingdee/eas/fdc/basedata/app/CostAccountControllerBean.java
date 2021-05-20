package com.kingdee.eas.fdc.basedata.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAcctFacadeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.util.FilterUtility;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ����:�ɱ���Ŀ
 * 
 * @author jackwang date:2006-9-6
 *         <p>
 * @version EAS5.1
 */
public class CostAccountControllerBean extends AbstractCostAccountControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.CostAccountControllerBean");

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		trimBlank(model);
		CostAccountInfo newCai = (CostAccountInfo) model;
		//������ȥ����Ҫ�����޸�,�¼��ڵ���벻�ܳ��ֶϲ�
		if (newCai.isIsSource()) {
			//�µĳ����롢���͡��Ƿ�ɱ���Ŀ
			String newLn = newCai.getLongNumber();
			String newName = newCai.getName();
			String newType = newCai.getType() == null ? "" : newCai.getType().getValue();
			Integer iCostAccount = newCai.isIsCostAccount() ? Integer.valueOf("1") : Integer.valueOf("0");
			Integer isProgramming = newCai.isIsProgramming() ? Integer.valueOf("1") : Integer.valueOf("0");
			BigDecimal rate=newCai.getRate();
			//δ����֮ǰ,��ȡ��ԭ���ĳ����롢���͡��Ƿ�ɱ���Ŀ		
			CostAccountInfo  oldCai = CostAccountFactory.getLocalInstance(ctx).getCostAccountInfo(pk);
			String oldLn = oldCai.getLongNumber();
			String oldName = oldCai.getName();
			
			//���µ�ǰ��Ŀ
			super._update(ctx, pk, model);
			//��ȡ��ǰ��Ŀ�ڵ�ǰ��֯����Ŀ�������¼���ĿID
			HashSet set = new HashSet();
			String sql = null;
			Object[] params = null;
			if (oldCai.getCurProject() != null) {
				sql = "select fid from t_fdc_costaccount where FCurProject=? and FLongNumber like ?";
				params = new Object[]{oldCai.getCurProject().getId().toString(),oldLn + "!%"};
			}else if (oldCai.getFullOrgUnit() != null) {
				sql = "select fid from t_fdc_costaccount where FFullOrgUnit=? and FLongNumber like ?";
				params = new Object[]{oldCai.getFullOrgUnit().getId().toString(),oldLn + "!%"};
			}		
			RowSet rs = DbUtil.executeQuery(ctx,sql,params);
			try{
				while(rs.next()){
					set.add(rs.getString("fid"));
				}
			} catch (SQLException e) {		
				throw new BOSException(e);
			}
			
			//ͬ�����µ�ǰ��Ŀ���䵽�¼���֯����Ŀ�п�Ŀ�ĳ����롢���롢���ơ����͡��Ƿ�ɱ���Ŀ ����
			params = null;
			sql =  "update T_FDC_CostAccount set FLongNumber=?,FNumber=?,FName_l2=?,FType=?,FIsCostAccount=?,FIsProgramming=?,FRate=? where fsrccostaccountid = ?";	
			params = new Object[]{newLn,newCai.getNumber(),newCai.getName(),newType,iCostAccount,isProgramming,rate,oldCai.getId().toString()};
			DbUtil.execute(ctx,sql,params);
			
			FDCSQLBuilder builder = new FDCSQLBuilder();
			if(set.size()>0){
				//�޸ĵ�ǰ��֯����Ŀ�������¼���Ŀ�ĳ����롢���͡��Ƿ�ɱ���Ŀ ����
				builder.appendSql("update T_FDC_CostAccount set FLongNumber=REPLACE(FLongNumber,?,?),FType=?,FIsCostAccount=? where ");
				builder.addParam(oldLn + "!");
				builder.addParam(newLn + "!");
				builder.addParam(newType);
				builder.addParam(iCostAccount);
				builder.appendParam("fid", set.toArray());
				builder.executeUpdate(ctx);	
				
				//ͬ�����������¼���Ŀ���䵽�¼���֯����Ŀ�п�Ŀ�ĳ����롢���͡��Ƿ�ɱ���Ŀ ����
				builder.clear();
				builder.appendSql("update T_FDC_CostAccount set FLongNumber=REPLACE(FLongNumber,?,?),FType=?,FIsCostAccount=? where ");
				builder.addParam(oldLn + "!");
				builder.addParam(newLn + "!");
				builder.addParam(newType);
				builder.addParam(iCostAccount);
				builder.appendParam("fsrccostaccountid", set.toArray());
				builder.executeUpdate(ctx);	
			}
			//ͬ�����±����޸����漰��Ŀ��FDisplayName_L2����	
			set.add(newCai.getId().toString());
			if (!newName.equals(oldName)) {
				String displayName = null;
				builder.clear();
				builder.appendSql("select FID,FLongNumber,FFullOrgUnit,FCurproject from T_FDC_CostAccount where ");
				builder.appendParam("fid",set.toArray());
				builder.appendSql(" order by FLongNumber");
				rs = builder.executeQuery(ctx);
				try{
					while (rs.next()) {
						String sId = rs.getString("FID").trim();
						String tempNumber = rs.getString("FLongNumber").trim();
						
						String orgUnit = rs.getString("FFullOrgUnit");
						String curProject = rs.getString("FCurproject");
						
						displayName = getNewDisplayName(ctx,tempNumber,orgUnit,curProject);
						
						sql = "update T_FDC_CostAccount set FDisplayName_L2=? where FID=? OR fsrccostaccountid=?";
						params = new Object[]{displayName,sId,sId};
						DbUtil.execute(ctx,sql,params);
					}
				} catch (SQLException e) {		
					throw new BOSException(e);
				}
			}
		}
		
		//������Ӫ����Ķ�Ӧ�ɱ���Ŀflag��־��Ϊ1
		String sqlNew = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '5001!08!03%'";
		DbUtil.execute(ctx,sqlNew);
		
		String sqlOld = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '4301!408!03%'";
		DbUtil.execute(ctx,sqlOld);
	}

	//�����¼��ĳ����� 5001!02!01
	private String getNewDisplayName(Context ctx, String longNumber,String orgUnit,String curProject) throws BOSException {
		String displayName = null;
		String tempParams = "?";
		
		String[] numberArray = longNumber.split("!");
		for(int i = 0;i<numberArray.length-1;i++){					
			numberArray[i+1] = numberArray[i].concat("!".concat(numberArray[i+1]));
			tempParams = tempParams.concat(",?");
		}	
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FName_L2 from T_FDC_CostAccount where ");
		if(orgUnit == null){
			builder.appendSql(" FFullOrgUnit is null ");
		}else{
			builder.appendSql(" FFullOrgUnit=? ");
			builder.addParam(orgUnit);
		}
		
		if(curProject == null){
			builder.appendSql(" and FCurproject is null ");
		}else{
			builder.appendSql(" and FCurproject=? ");
			builder.addParam(curProject);
		}

		builder.appendSql(" and ");
		builder.appendParam("flongnumber",numberArray);
		builder.appendSql(" order by flongnumber ");
		IRowSet rs = builder.executeQuery(ctx);
		try{
			while (rs.next()) {
				displayName = displayName == null ? rs.getString("FName_L2") : displayName.concat("_".concat(rs.getString("FName_L2")));
			}
		} catch (SQLException e) {		
			throw new BOSException(e);
		}
		return displayName;
	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		trimBlank(model);
		CostAccountInfo costAccountInfo = (CostAccountInfo) model;
/*		if(costAccountInfo.getParent() != null) {
			_isReferenced(ctx, new ObjectUuidPK(costAccountInfo.getParent().getId()));
		}*/
		
		// ���ŷ����Ŀ��ʹ����һ��ʱ�䣬���Ҫ������Ŀ������ʱ��Ҫ����¼���˾��������Ŀ�Ƿ��ѽ�����ͬ�Ŀ�Ŀ����Ŀ������ͬ����
		if ((costAccountInfo.getFullOrgUnit() != null)) {//����֯�ڵ�
			// &&((OrgConstants.DEF_CU_ID).equals(costAccountInfo.getFullOrgUnit().getId().toString()))){
			// ��ǰҪ�����Ŀ�Ŀ�ǹ��ڼ����ϵ�
			// ICostAccount iCostAccount =
			// CostAccountFactory.getLocalInstance(ctx);
			if(OrgConstants.DEF_CU_ID.equals(costAccountInfo.getFullOrgUnit().getId().toString())){			
				if (costAccountInfo.getParent() != null) {// �������ӽڵ�
					String longNumber = costAccountInfo.getParent().getLongNumber() + "!" + costAccountInfo.getNumber();
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber));
					evi.setFilter(filter);
					// CostAccountInfo costAccount
					// =iCostAccount.getCostAccountInfo("select where longNumber =
					// '" +longNumber+ "'");
					CostAccountCollection cac = getCostAccountCollection(ctx, evi);
					if (cac.size() != 0) {
						throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.COSTACCOUNT_ADD_SUBHAVE);
					}
					
					costAccountInfo.setIsEnabled(costAccountInfo.getParent().isIsEnabled());
				}
			}
		}
//		else if((costAccountInfo.getCurProject() != null)){//�ҹ�����Ŀ�ڵ�
//			//��ȡ�¼�������Ŀ����
//			
//			if (costAccountInfo.isIsLeaf()) {// Ҫ�鿴�¼�������Ŀ�ڵ��Ƿ��ѽ�����ͬ�Ŀ�Ŀ����Ŀ������ͬ��
//				String longNumber = costAccountInfo.getParent().getLongNumber() + "!" + costAccountInfo.getNumber();
//				EntityViewInfo evi = new EntityViewInfo();
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber));
//				filter.getFilterItems().add(new FilterItemInfo("curProject.id", costAccountInfo.getCurProject().getId().toString()));
//				evi.setFilter(filter);
//				// CostAccountInfo costAccount
//				// =iCostAccount.getCostAccountInfo("select where longNumber =
//				// '" +longNumber+ "'");
//				CostAccountCollection cac = getCostAccountCollection(ctx, evi);
//				if (cac.size() != 0) {
//					throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.COSTACCOUNT_ADD_SUBHAVE);
//				}
//			}
//		}
		String sql = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '5001!08!03%'";
		DbUtil.execute(ctx,sql);
		
		String sqlOR = "update t_fdc_costaccount set FFlag = 1 where flongnumber like '4301!408!03%'";
		DbUtil.execute(ctx,sqlOR);
		IObjectPK pk = super._addnew(ctx, costAccountInfo);
		//����ʱͬʱҲҪ������Ӧ��ϵ
		Map param=new HashMap();
		Set set=new HashSet();
		set.add(pk.toString());
		param.put("acctIdSet", set);
		CostAccountWithAcctFacadeFactory.getLocalInstance(ctx).insert(param);
		return pk;
	}

	protected boolean _enable(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		boolean flag = false;
		// ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		CostAccountInfo costAccountInfo = new CostAccountInfo();

		costAccountInfo = getCostAccountInfo(ctx, pk);
		
		
		if (costAccountInfo.getParent() != null) {
			IObjectPK parentPK = new ObjectStringPK(costAccountInfo.getParent().getId().toString());
			CostAccountInfo parentCostAccountInfo = getCostAccountInfo(ctx, parentPK);
			if (!parentCostAccountInfo.isIsEnabled()) {
				// ����ϼ�������,������ʾ������
				throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.COSTACCOUNT_PARENT_DISENABLE);
			} else {
				if (changeUseStatus(ctx, pk, true))
					flag = true;
			}

		} else {
			if (changeUseStatus(ctx, pk, true))
				flag = true;
		}
		
		if(flag) {
//			4301.401.01.01�����ϼ����������Ŀ�Ŀ�����ϼ����õ��ÿ�Ŀ���¼����ú��ϼ���֯�ĸÿ�Ŀû���Զ�����
			if(costAccountInfo.getSrcCostAccountId() != null) {
				DbUtil.execute(ctx, "update T_FDC_CostAccount set  FIsEnable = 1 where fid = ?", new Object[]{costAccountInfo.getSrcCostAccountId()});
			}
		}
		return flag;
	}

	protected boolean _disable(Context ctx, IObjectPK pk) throws BOSException, EASBizException {	
		_isReferenced(ctx, pk);
		
		CostAccountInfo costAccountInfo = this.getCostAccountInfo(ctx, pk);

    	if((costAccountInfo.getFullOrgUnit()!=null)&&(OrgConstants.DEF_CU_ID.equals(costAccountInfo.getFullOrgUnit().getId().toString()))){
    		//������ڼ����½���,��Ҫ�ж��¼������ѷ�����ȥ�Ŀ�Ŀ,�Ƿ��б����õ�,
        	//���������,ֹͣ
        	//���û������,����,������ϵͳ������(������������ȥ��)
    		
    		//Ѱ�ҵ�ǰ�ڵ�(����)�µ��¼���Ŀ
    		StringBuffer checkExistsSql = new StringBuffer();
    		checkExistsSql.append("select A.FCostAccountID AS FID FROM T_AIM_CostEntry A 		\r\n");
    		checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
    		checkExistsSql.append("where B.FFullOrgUnit <>? and B.FLongNumber like ? 		\r\n");
    		checkExistsSql.append("UNION 		\r\n");
    		checkExistsSql.append("select A.FAccountID AS FID from T_AIM_DynamicCost A 		\r\n");
    		checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FAccountID=B.FID 		\r\n");
    		checkExistsSql.append("where B.FFullOrgUnit <>? and B.FLongNumber like ? 		\r\n");
    		checkExistsSql.append("UNION 		\r\n");
    		checkExistsSql.append("select A.FCostAccountID AS FID from T_AIM_COSTSPLITENTRY A 		\r\n");
    		checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
    		checkExistsSql.append("where B.FFullOrgUnit <>? and B.FLongNumber like ? 		\r\n");
    		checkExistsSql.append("UNION 		\r\n");
    		checkExistsSql.append("select A.FCostAccountID AS FID from T_AIM_MEASUREENTRY A 		\r\n");
    		checkExistsSql.append("INNER JOIN T_FDC_CostAccount B ON A.FCostAccountID=B.FID 		\r\n");
    		checkExistsSql.append("where B.FFullOrgUnit <>? and B.FLongNumber like ? 		\r\n");
    		
			String longNumber = costAccountInfo.getLongNumber() + "!%";
			Object[] params = new Object[] {OrgConstants.DEF_CU_ID, longNumber,
					OrgConstants.DEF_CU_ID, longNumber,
					OrgConstants.DEF_CU_ID, longNumber,
					OrgConstants.DEF_CU_ID, longNumber};
			RowSet rs = DbUtil.executeQuery(ctx, checkExistsSql.toString(), params);
			
			try {
				if(rs != null && rs.next()){
					throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DISENABLE_SUBNODE_REFERENCE);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
    		/*CostAccountCollection cac = this.getCostAccountCollection(ctx,"select id where fullOrgUnit.id !=  '" + OrgConstants.DEF_CU_ID + "' and longNumber like '" + longNumber + "!%'");
//    		13509608400
    		if(cac.size()!=0){
    			for(int i=0;i<cac.size();i++){    				
    				if ((CostEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" + cac.get(i).getId().toString() + "'"))
    						|| (DynamicCostFactory.getLocalInstance(ctx).exists("select costAccount where account.id ='" + cac.get(i).getId().toString() + "'"))){
//    						|| (ContractCostSplitEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" + PK.toString() + "'"))) {    					
    					throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DISENABLE_SUBNODE_REFERENCE);
    				}
    			}
    		}    	*/    		
    	}
    	
		if (this.checkIsUsed(ctx, pk)) {//���ڵ����ж�����
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DISENABLE_CANNOT_USED);
		}
		if (this.checkIsOnlyOneEnabled(ctx, pk)) {
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DISENABLE_CANNOT_ONLY);
		}
		if (changeUseStatus(ctx, pk, false))
			return true;
		else
			return false;

	}

	private boolean checkIsOnlyOneEnabled(Context ctx, IObjectPK PK) throws BOSException, EASBizException {
		CostAccountInfo cai = this.getCostAccountInfo(ctx, PK).getParent();
		if (cai != null) {
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", cai.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
			evi.setFilter(filter);
			if (this.getCostAccountCollection(ctx, evi).size() == 1) {
				return true;
			}
		}
		return false;
	}

	private boolean checkIsUsed(Context ctx, IObjectPK PK) throws EASBizException, BOSException {
		// ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		CostAccountInfo costAccountInfo = this.getCostAccountInfo(ctx, PK);
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", costAccountInfo.getId().toString()));
		evi.setFilter(filter);
		CostAccountCollection costAccountCollection = this.getCostAccountCollection(ctx, evi);
		// ������¼�,���ж��Լ�,���ж��¼�
		if (costAccountCollection.size() > 0) {
			// �ж��Լ�
			if ((CostEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" + PK.toString() + "'"))
					|| (DynamicCostFactory.getLocalInstance(ctx).exists("select costAccount where account.id ='" + PK.toString() + "'"))){
//					|| (ContractCostSplitEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" + PK.toString() + "'"))) {
				return true;
			} else {
				// �ж��¼�
				for (int i = 0; i < costAccountCollection.size(); i++) {
					if ((CostEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" + costAccountCollection.get(i).getId().toString() + "'"))
							|| (DynamicCostFactory.getLocalInstance(ctx).exists("select costAccount where account.id ='" + costAccountCollection.get(i).getId().toString() + "'"))){
//							|| (ContractCostSplitEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" + costAccountCollection.get(i).getId().toString() + "'"))) {
						return true;
					}else{
						checkIsUsed(ctx, new ObjectStringPK(costAccountCollection.get(i).getId().toString()));
					}
				}
			}
		} else {
			// ���û���¼�,�ж��Լ�
			if ((CostEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" + PK.toString() + "'"))
					|| (DynamicCostFactory.getLocalInstance(ctx).exists("select costAccount where account.id ='" + PK.toString() + "'"))){
//					|| (ContractCostSplitEntryFactory.getLocalInstance(ctx).exists("select costAccount where costAccount.id ='" + PK.toString() + "'"))) {
				return true;
			}
		}
		return false;
	}

	/*
	 */
	protected boolean changeUseStatus(Context ctx, IObjectPK PK, boolean flag) throws EASBizException, BOSException {
		// ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		CostAccountInfo costAccountInfo = getCostAccountInfo(ctx, PK);
		if((!flag)&&(costAccountInfo.getFullOrgUnit()!=null)&&(OrgConstants.DEF_CU_ID.equals(costAccountInfo.getFullOrgUnit().getId().toString()))){
			//����Ǽ����µĽ��ò���,ǰ���Ѿ�У����,�˴�ͨ����У��,ֱ��sql���������Ҫ��¼bug,���˵Ҫ���������20061124
			String longNumber = costAccountInfo.getLongNumber();
//    		CostAccountCollection cac = this.getCostAccountCollection(ctx,"select longNumber where longNumber like '" + longNumber + "%'");//fullOrgUnit.id =  '" + OrgConstants.DEF_CU_ID + "' and
//    	    if(cac.size()!=0){
	    		String sql = "update T_FDC_CostAccount set  FIsEnable = 0  where FLongNumber like '" + longNumber + "%'";	    		
//	    	    Object[] params ;
//	    	    for(int i = 0;i<cac.size();i++){
//	    	    	params = new Object[]{cac.get(i).getLongNumber()};
	    	    	DbUtil.execute(ctx,sql);
//	    	    }    
//    	    }
		}
		if((flag)&&(costAccountInfo.getFullOrgUnit()!=null)&&(OrgConstants.DEF_CU_ID.equals(costAccountInfo.getFullOrgUnit().getId().toString()))){
			//����Ǽ����µ����ò������������з�����ȥ��
			String longNumber = costAccountInfo.getLongNumber();
			String sql = "update T_FDC_CostAccount set  FIsEnable = 1  where FLongNumber like '" + longNumber + "%'";
			DbUtil.execute(ctx,sql);
		}
		else{
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", costAccountInfo.getId().toString()));
			evi.setFilter(filter);
			CostAccountCollection costAccountCollection = getCostAccountCollection(ctx, evi);
			// ������¼�,Ҫͬʱ����/�����¼�
			if (costAccountCollection.size() > 0) {
				// ������/�����Լ�
				String sql = "update T_FDC_CostAccount set  FIsEnable = ?  where fid = ? ";	    		
				Object[] params = new Object[]{Boolean.valueOf(flag),costAccountInfo.getId().toString()};
    	    	DbUtil.execute(ctx,sql,params);
//				costAccountInfo.setIsEnabled(flag);
//				_update(ctx, PK, costAccountInfo);
				// ������/�����¼�
				CostAccountInfo childContractTypeInfo;
				IObjectPK childPK;
				for (int i = 0; i < costAccountCollection.size(); i++) {
					if (Boolean.valueOf(costAccountCollection.get(i).isIsEnabled()) != Boolean.valueOf(flag)) {
						childContractTypeInfo = costAccountCollection.get(i);
						childContractTypeInfo.setIsEnabled(flag);
						childPK = new ObjectStringPK(childContractTypeInfo.getId().toString());
						changeUseStatus(ctx, childPK, flag);
					}
				}
			} else {
				// ���û���¼�
				String sql = "update T_FDC_CostAccount set  FIsEnable = ?  where fid = ? ";	    		
				Object[] params = new Object[]{Boolean.valueOf(flag),costAccountInfo.getId().toString()};
    	    	DbUtil.execute(ctx,sql,params);
    	    	
//				costAccountInfo.setIsEnabled(flag);
//				_update(ctx, PK, costAccountInfo);
			}
		}
		
		
		
		
		
		// }else{

		// }		
		return true;
	}

	protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		TreeBaseInfo treeModel = (TreeBaseInfo) model;
		// ����¼��Ƿ����ͬ������

		// if no parent,no need to check
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = null;
		// ���ڵ�Ϊ��ʱ������������Ƿ��ظ���
		if (treeModel.innerGetParent() == null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, treeModel.getNumber(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.tree_Parent, null, CompareType.EQUALS));
			if (((CostAccountInfo) treeModel).getCurProject() != null) {
				filter.getFilterItems().add(new FilterItemInfo("curproject.id", ((CostAccountInfo) treeModel).getCurProject().getId().toString(), CompareType.EQUALS));
			} else if (((CostAccountInfo) treeModel).getFullOrgUnit() != null) {
				filter.getFilterItems().add(new FilterItemInfo("fullorgunit.id", ((CostAccountInfo) treeModel).getFullOrgUnit().getId().toString(), CompareType.EQUALS));
			}
			filter.setMaskString("#0 and #1 and #2 ");
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				filter.setMaskString("#0 and #1 and #2 and #3 ");
			}
		} else {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, treeModel.getNumber(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			if (treeModel.innerGetParent().getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.tree_Parent, treeModel.innerGetParent().getId().toString(), CompareType.EQUALS);
				filter.getFilterItems().add(filterItem);
				filter.setMaskString("#0 and #1");
			}
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				if (treeModel.innerGetParent().getId() != null) {
					filter.setMaskString("#0 and #1 and #2");
				} else {
					filter.setMaskString("#0 and #1");
				}
			}
		}
		EntityViewInfo view = new EntityViewInfo();
		// CU����
		// FilterInfo filterCU = getFilterForDefaultCU(ctx, treeModel);
		// if (FilterUtility.hasFilterItem(filterCU)) {
		// if (FilterUtility.hasFilterItem(filter)) {
		// filter.mergeFilter(filterCU, "AND");
		// } else {
		// filter = filterCU;
		// }
		// }
		view.setFilter(filter);
		TreeBaseCollection results = this.getTreeBaseCollection(ctx, view);
		if (results != null && results.size() > 0) {
			throw new TreeBaseException(TreeBaseException.CHECKNUMBERDUPLICATED, new Object[] { treeModel.getNumber() });

		}

	}

	/**
	 * Ĭ��ʵ�ֶ����� ͬ�ĳ����룬��ID��ͬ���д�������ɰ���Ҫ����ʵ�֡�
	 * 
	 * @param ctx
	 * @param treeBaseInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws TreeBaseException
	 */
	protected void checkLNForTree(Context ctx, TreeBaseInfo treeBaseInfo) throws BOSException, EASBizException, TreeBaseException {
		// ������ݿ������ͬlongNumber��ID��ͬ�����ݣ����쳣��
		FilterInfo lNfilter = new FilterInfo();
		lNfilter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.tree_LongNumber, treeBaseInfo.getLongNumber()));
		lNfilter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.coreBase_ID, treeBaseInfo.getId().toString(), CompareType.NOTEQUALS));
		if (((CostAccountInfo) treeBaseInfo).getCurProject() != null) {
			lNfilter.getFilterItems().add(new FilterItemInfo("curproject.id", ((CostAccountInfo) treeBaseInfo).getCurProject().getId().toString(), CompareType.EQUALS));
		} else if (((CostAccountInfo) treeBaseInfo).getFullOrgUnit() != null) {
			lNfilter.getFilterItems().add(new FilterItemInfo("fullorgunit.id", ((CostAccountInfo) treeBaseInfo).getFullOrgUnit().getId().toString(), CompareType.EQUALS));
		}
		lNfilter.setMaskString("#0 AND #1 AND #2 ");

		// CU����
		FilterInfo filterCU = getFilterForDefaultCU(ctx, treeBaseInfo);
		if (FilterUtility.hasFilterItem(filterCU)) {
			lNfilter.mergeFilter(filterCU, "AND");
		}

		if (exists(ctx, lNfilter)) {
			throw new TreeBaseException(TreeBaseException.CHECKNUMBERDUPLICATED, new Object[] { treeBaseInfo.getNumber() });
		}
	}

	protected boolean _assign(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		return false;
	}

	protected boolean _disassign(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		return false;

	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		CostAccountInfo oldModel = this.getCostAccountInfo(ctx, pk);
		if (oldModel.isIsLeaf()) {
			// IContractBill iContractBill =
			// ContractBillFactory.getLocalInstance(ctx);
			// ContractBillCollection contractBillCollection =
			// iContractBill.getContractBillCollection("where contractType.id =
			// '" + oldModel.getId().toString() + "'");
			// if(contractBillCollection.size()!=0){
			//
			// throw new
			// com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.CONTRACTTYPE_DEL_REFERENCE);
			// }else{
			// super._delete(ctx, pk);
			// }
			this._isReferenced(ctx, pk);
			super._delete(ctx, pk);
		} else {
			throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.DELETE_ISPARENT_FAIL);
		}

	}

	// protected IObjectPK[] _delete(Context ctx, FilterInfo filter) throws
	// BOSException, EASBizException
	// {
	// return super._delete(ctx, filter);
	// }
	protected void _importDatas(Context ctx, IObjectCollection cac, BOSUuid addressId) throws BOSException, EASBizException {
		CurProjectInfo cpi = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(addressId.toString()));
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", addressId.toString()));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("isLeaf");
		view.getSelector().add("isEnabled");
		view.getSelector().add("longNumber");
		
		
		CostAccountCollection costAccountCollection2 = getCostAccountCollection(ctx, view);
		Map acMap = new HashMap();
		for (Iterator iter = costAccountCollection2.iterator(); iter.hasNext();) {
			CostAccountInfo element = (CostAccountInfo) iter.next();
			acMap.put(element.getLongNumber(), element);
		}
		
		CostAccountCollection costAccountCollection = (CostAccountCollection) cac;
		CostAccountInfo cai;
		SelectorItemCollection seletors = new SelectorItemCollection();
		seletors.add("*");
		seletors.add("parent.isAssigned");
		for (int i = 0; i < costAccountCollection.size(); i++) {
			cai = getCostAccountInfo(ctx, new ObjectUuidPK(costAccountCollection.get(i).getId().toString()), seletors);
			
			if(cai.getParent() != null) { 
				String longNumber = cai.getLongNumber();
				String parentLN = longNumber.substring(0, longNumber.lastIndexOf("!"));
				CostAccountInfo ca = (CostAccountInfo)acMap.get(parentLN);
				if(ca!=null){
					if(ca.getId() != null && ca.isIsLeaf()) {
						_isReferenced(ctx, new ObjectStringPK(ca.getId().toString()));
					}
				}else{
					CostAccountCollection coll = getCostAccountCollection(ctx, "select id,isleaf where longNumber = '" + cai.getLongNumber().substring(0, cai.getLongNumber().lastIndexOf('!')) + "' and curProject.id = '"
							+ addressId.toString() + "'");
					ca = coll.get(0);
				}
				
				//���Դ��Ŀ���ϼ���Ŀ���ڣ���Ŀ����Ŀ�в����ڣ��������˿�Ŀ��������һ��
				if(ca == null){continue;}
				
				cai.setIsEnabled(ca.isIsEnabled());
				//�ϼ���Ŀ��������Ŀ�е����ϼ�ID
				cai.setParent(ca);
				
				if(ca.isIsLeaf()){
					String sql = "update T_FDC_CostAccount set  FIsLeaf = ?  where fid = ? ";	    		
					Object[] params = new Object[]{Boolean.valueOf(false),ca.getId().toString()};
	    	    	DbUtil.execute(ctx,sql,params);
	    	    	
	    	    	ca.setIsLeaf(false);
    	    		acMap.put(parentLN,ca);    	    	
				}
			}
			else {
				cai.setIsEnabled(false);
			}
			
			cai.setCurProject(cpi);
			cai.setFullOrgUnit(null);
			cai.setAssigned(false);//�����������һ������ΪԴ��Ŀ
			cai.setIsSource(true);// �������Դ�ɱ���Ŀ
			cai.setSrcCostAccountId(null);

			cai.setId(null);

			super._addnew(ctx, cai);
		}
	}

	protected ArrayList _importTemplateDatas(Context ctx, IObjectCollection cac) throws BOSException, EASBizException {
		ArrayList al = new ArrayList();
		CostAccountCollection costAccountCollection = (CostAccountCollection) cac;
		CostAccountInfo cai;
		FullOrgUnitInfo foui = new FullOrgUnitInfo();
		foui.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		CtrlUnitInfo cui = new CtrlUnitInfo();
		cui.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));

		if (costAccountCollection.size() != 0) {
			for (int i = 0; i < cac.size(); i++) {
				cai = getCostAccountInfo(ctx, new ObjectUuidPK(costAccountCollection.get(i).getId().toString()));
				cai.setCurProject(null);
				cai.setFullOrgUnit(foui);
				cai.setAssigned(false);//
				cai.setIsSource(true);
				cai.setIsEnabled(false);
				cai.setCU(cui);
				cai.setId(null);
				if (cai.getParent() != null) {
					// �����ת��
					String ln = cai.getLongNumber().substring(0, cai.getLongNumber().lastIndexOf('!')) ;
					String sql = "select FID from t_fdc_costaccount where flongNumber = ?  and ffullOrgUnit = ? ";
					Object[] params = new Object[] { ln, OrgConstants.DEF_CU_ID };
					RowSet rs = DbUtil.executeQuery(ctx, sql, params);
					try {
						if (rs.next()) {
							CostAccountInfo parent = new CostAccountInfo();
							parent.setId(BOSUuid.read(rs.getString("FID")));
							cai.setParent(parent);
							if (has(ctx, cai)) {
								al.add(cai.getLongNumber().replace('!','.'));
							} else {
								super._addnew(ctx, cai);
							}
						} else {

						}
					} catch (Exception e) {

					}
				}else{
					if (has(ctx, cai)) {
						al.add(cai.getLongNumber().replace('!','.'));
					} else {
						super._addnew(ctx, cai);
					}
				}				
			}
		}
		return al;
	}

	protected boolean has(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		TreeBaseInfo treeModel = (TreeBaseInfo) model;

		// if no parent,no need to check
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = null;
		// ���ڵ�Ϊ��ʱ������������Ƿ��ظ���
		if (treeModel.innerGetParent() == null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, treeModel.getNumber(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.tree_Parent, null, CompareType.EQUALS));
			if (((CostAccountInfo) treeModel).getCurProject() != null) {
				filter.getFilterItems().add(new FilterItemInfo("curproject.id", ((CostAccountInfo) treeModel).getCurProject().getId().toString(), CompareType.EQUALS));
			} else if (((CostAccountInfo) treeModel).getFullOrgUnit() != null) {
				filter.getFilterItems().add(new FilterItemInfo("fullorgunit.id", ((CostAccountInfo) treeModel).getFullOrgUnit().getId().toString(), CompareType.EQUALS));
			}
			filter.setMaskString("#0 and #1 and #2 ");
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				filter.setMaskString("#0 and #1 and #2 and #3 ");
			}
		} else {
			filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, treeModel.getNumber(), CompareType.EQUALS);
			filter.getFilterItems().add(filterItem);
			if (treeModel.innerGetParent().getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.tree_Parent, treeModel.innerGetParent().getId().toString(), CompareType.EQUALS);
				filter.getFilterItems().add(filterItem);
				filter.setMaskString("#0 and #1");
			}
			if (treeModel.getId() != null) {
				filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, treeModel.getId().toString(), CompareType.NOTEQUALS);
				filter.getFilterItems().add(filterItem);
				if (treeModel.innerGetParent().getId() != null) {
					filter.setMaskString("#0 and #1 and #2");
				} else {
					filter.setMaskString("#0 and #1");
				}
			}
		}
		EntityViewInfo view = new EntityViewInfo();
		// CU����
		// FilterInfo filterCU = getFilterForDefaultCU(ctx, treeModel);
		// if (FilterUtility.hasFilterItem(filterCU)) {
		// if (FilterUtility.hasFilterItem(filter)) {
		// filter.mergeFilter(filterCU, "AND");
		// } else {
		// filter = filterCU;
		// }
		// }
		view.setFilter(filter);
		TreeBaseCollection results = this.getTreeBaseCollection(ctx, view);
		if (results != null && results.size() > 0) {
			return true;

		} else {
			return false;
		}
	}
	
	/**
	 * ���ǰ��ո�
	 * 
	 * @param id
	 * @return
	 */
	private IObjectValue trimBlank(IObjectValue model){
		CostAccountInfo theModel = (CostAccountInfo) model;
		if(theModel.getNumber() != null){
			theModel.setNumber(theModel.getNumber().trim());
		}
		if(theModel.getName() != null){
			theModel.setName(theModel.getName().trim());
		}
		if(theModel.getDescription() != null){
			theModel.setDescription(theModel.getDescription().trim());
		}
		
		//�������
		String lgNumber=theModel.getLongNumber();
		if(lgNumber!=null){
			lgNumber=lgNumber.replace('!', '.');
		}
		theModel.setCodingNumber(lgNumber);
		return model;
	}
}