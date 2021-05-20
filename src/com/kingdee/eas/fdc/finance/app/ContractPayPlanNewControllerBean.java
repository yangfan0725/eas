package com.kingdee.eas.fdc.finance.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.finance.ContractPayPlanNewCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanNewFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanNewInfo;

public class ContractPayPlanNewControllerBean extends AbstractContractPayPlanNewControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.ContractPayPlanNewControllerBean");

    /**
     * δʹ��
     */
	protected String _approveSucc(Context ctx, IObjectValue model)
			throws BOSException {
		return null;
	}
	/**
	 * ��˵���
	 */
	protected boolean _auditBill(Context ctx, IObjectValue model)
			throws BOSException {
		ContractPayPlanNewInfo info = (ContractPayPlanNewInfo)model;
		info.setStatus("������");
        try {
			ContractPayPlanNewFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()), info);
			return true;
		} catch (EASBizException e) {
			this.setRollbackOnly();
			logger.info(e.getMessage());
			return false;
		}
	}
	/**
	 * �ύ����
	 */
	protected boolean _submitBill(Context ctx, IObjectValue model)
			throws BOSException {
		ContractPayPlanNewInfo info = (ContractPayPlanNewInfo)model;
		info.setStatus("�ύ");
        try {
			ContractPayPlanNewFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()), info);
			return true;
		} catch (EASBizException e) {
			this.setRollbackOnly();
			logger.info(e.getMessage());
			return false;
		}
	}
	/**
	 * ����˵���
	 */
	protected boolean _unauditBill(Context ctx, IObjectValue model)
			throws BOSException {
		ContractPayPlanNewInfo info = (ContractPayPlanNewInfo)model;
		if(!isFinal(ctx,info.getId())){
			return false;
		}
		info.setStatus("�ύ");
        try {
			ContractPayPlanNewFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()), info);
			return true;
		} catch (EASBizException e) {
			this.setRollbackOnly();
			logger.info(e.getMessage());
			return false;
		}
	}
	/**
	 * �ж��Ƿ������հ汾,ֻ�������հ汾�Ͻ����޶�
	 */
	protected boolean _isFinal(Context ctx, BOSUuid bosId) throws BOSException {
		FilterInfo filter = new FilterInfo();
		String sql = "select fcontractcd from T_FNC_ContractPayPlanNew where fid='"+bosId+"'";
		filter.getFilterItems().add(
				new FilterItemInfo("ContractCd", sql, CompareType.INNER));
		EntityViewInfo view = new EntityViewInfo();
		view.getSorter().clear();
		view.getSorter().add(new SorterItemInfo("number"));
		SorterItemInfo sortItem = view.getSorter().get(0);
		sortItem.setSortType(SortType.DESCEND);
		view.setFilter(filter);
		ContractPayPlanNewCollection payPlans = ContractPayPlanNewFactory.getLocalInstance(ctx).getContractPayPlanNewCollection(view);
		if(payPlans.size()==1){
			return true;
		}else if(payPlans.size()>1){
			String id = String.valueOf(payPlans.get(0).getId());
			if(String.valueOf(bosId).equals(id)){
				return true;
			}
		}
		return false;
	}
    
}