package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitException;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSplitBillInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.invite.app.ContractCostSplitInviteHelper;
import com.kingdee.eas.framework.AbstractBillEntryBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractCostSplitControllerBean extends AbstractContractCostSplitControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractCostSplitControllerBean");
    
    protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        ContractCostSplitInfo info = (ContractCostSplitInfo)super._getValue(ctx,pk);
        String retValue = "";
        if(info.getContractBill()!= null)
        {
        	String id = info.getContractBill().getId().toString();
        	ContractBillInfo test = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(id)));	
	        if(test.getNumber()!=null){
        		retValue = test.getNumber();
	            if(test.getName()!=null){
	            	retValue = retValue + " " + test.getName();
	            }
	        }
        }
        return retValue;
    }

	/* ���� Javadoc��
	 * @see com.kingdee.eas.framework.app.CoreBillBaseControllerBean#_save(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectValue)
	 */
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		//return super._save(ctx, model);

		ContractCostSplitInfo info = (ContractCostSplitInfo)model;
		//�Ƿ����ۺ�ͬ
		boolean isMeasureContract=info.getBoolean("isMeasureContract");
		info.setIslastVerThisPeriod(true);
		IObjectPK pk=super._save(ctx, info);
		ContractCostSplitInfo splitBillInfo=null;
		SelectorItemCollection selectorGet = new SelectorItemCollection();
		selectorGet.add("id");
		selectorGet.add("contractBill.id");
		selectorGet.add("contractBill.curProject.id");
		selectorGet.add("contractBill.period.number");
		selectorGet.add("contractBill.CU.id");
		selectorGet.add("period.id");
		selectorGet.add("period.number");
		splitBillInfo = getContractCostSplitInfo(ctx, pk, selectorGet);
		String prjID = splitBillInfo.getContractBill().getCurProject().getId().toString();
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx,prjID, true);
		if(currentPeriod!=null){
			PeriodInfo contractPeriod = splitBillInfo.getContractBill().getPeriod();
			if(contractPeriod!=null&&contractPeriod.getNumber()>currentPeriod.getNumber()){
				updatePeriod(ctx, splitBillInfo, contractPeriod);
			}else{
				updatePeriod(ctx, splitBillInfo, currentPeriod);
			}
		}else if(splitBillInfo.getPeriod()!=null){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.clear();
			builder.appendSql("update T_CON_ContractCostSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and FContractBillid=?");
			builder.addParam(splitBillInfo.getPeriod().getId().toString());
			builder.addParam(splitBillInfo.getId().toString());
			builder.addParam(splitBillInfo.getContractBill().getId().toString());
			builder.executeUpdate();
		}
    	//�����¼˳��
		/*
		SelectorItemCollection selector = new SelectorItemCollection();
		//selector.add("entys.seq");	
		//selector.add("entys.index");
		selector.add("id");	
		selector.add("seq");	
		selector.add("index");	
		//(ContractCostSplitInfo)model;//
		ContractCostSplitInfo splitBill=(ContractCostSplitInfo)model;//ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitInfo(pk,selector);
		ContractCostSplitEntryInfo entry=null;		
		for (Iterator iter = splitBill.getEntrys().iterator(); iter.hasNext();)
		{
			entry = (ContractCostSplitEntryInfo) iter.next();			

			//entry.setSeq(entry.getIndex());		jelon 12/27/2006		
			//entry.setSeq(entry.getIndex()*10000 + entry.getSeq());
			//entry.setIndex(entry.getSeq()*100);
			entry.setIndex(1000);
			
			ContractCostSplitEntryFactory.getLocalInstance(ctx).updatePartial(entry,selector);
		}
		//ContractCostSplitFactory.getLocalInstance(ctx).updatePartial(splitBill,selector);
		*/		
		
		//�����ֻ���
		ContractCostSplitInfo splitBill=(ContractCostSplitInfo)model;
		splitBill.setId(BOSUuid.read(pk.toString()));
		updateEntrySeq(ctx,splitBill);
		//�����ֻ���
		BOSUuid costBillId=splitBill.getContractBill().getId();
		collectCostSplit(ctx, CostSplitBillTypeEnum.CONTRACTSPLIT, splitBillInfo.getContractBill(),splitBill.getId(),splitBill.getEntrys());
		
		
//		���º�ͬ�Ĳ��״̬
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_CON_ContractBill set fsplitState=?,fisMeasureContract=? where fid=?");
		builder.addParam(splitBill.getSplitState().getValue());
		builder.addParam(Boolean.valueOf(isMeasureContract));
		builder.addParam(costBillId.toString());
		builder.execute();
		//������ֹ�����,����һ���ǵ���ID
//		ContractBillFactory.getLocalInstance(ctx).split(new ObjectUuidPK(costBillId));
		
/*		//��ĿԤ����� by sxhong 2008-04-28 17:06:22
 * 		֮ǰ����ʾ���ܣ�������Ҫ��
		boolean hasUsed=FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_ACCTBUDGET);
		if(hasUsed){
			//ֻ��ʵ��,���������ܵ�
			try {
				FilterInfo filter=new FilterInfo();
				filter.appendFilterItem("bill.contractId", costBillId.toString());
				ConPayPlanSplitFactory.getLocalInstance(ctx).delete(filter);
				builder = new FDCSQLBuilder(ctx);
				builder.appendSql("select fid from T_FNC_ContractPayPlan where FContractId=?");
				builder.addParam(costBillId.toString());
				IRowSet rowSet = builder.executeQuery();
				while (rowSet.next()) {
					String conPayPlanId = rowSet.getString("fid");
					ConPayPlanSplitFactory.getLocalInstance(ctx).autoSplit(conPayPlanId);
				}
				builder.clear();
				builder.appendSql("select fid from T_CON_PayRequestBill where fcontractid=?");
				builder.addParam(costBillId.toString());
				rowSet = builder.executeQuery();
				while (rowSet.next()) {
					String reqId = rowSet.getString("fid");
					PayRequestSplitFactory.getLocalInstance(ctx).autoSplit(reqId);
				}
			} catch (Exception e) {
				throw new BOSException(e);
			}
		}*/
		
		//��ֱ�����¶�Ӧ�б������¶�Ӧ���б�Ԥ�Ȳ�ֵ�״̬Ϊ�Ѳ��
		ContractCostSplitInviteHelper.setInvitePreSplitState(ctx, pk, true);
		
		return pk;
	}

	/**�Ż�����ṹ���ع���ȡ���������ø����ڼ� -by neo
	 * @param ctx
	 * @param splitBillInfo
	 * @param contractPeriod
	 * @throws BOSException
	 */
	private void updatePeriod(Context ctx, ContractCostSplitInfo splitBillInfo,PeriodInfo period) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_CON_ContractCostSplit set FPeriodID=? where fid=?");
		builder.addParam(period.getId().toString());
		builder.addParam(splitBillInfo.getId().toString());
		builder.executeUpdate();
		builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder.appendSql("update T_CON_ContractCostSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and FContractBillid=?");
		builder.addParam(period.getId().toString());
		builder.addParam(splitBillInfo.getId().toString());
		builder.addParam(splitBillInfo.getContractBill().getId().toString());
		builder.executeUpdate();
	}



	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.contract.app.AbstractContractCostSplitControllerBean#_delete(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectPK)
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		//������ʵ�ֵ��� _delete(Context ctx, IObjectPK[] arrayPK)
		ContractCostSplitInviteHelper.setInvitePreSplitState(ctx, pk, false);
		super._delete(ctx, pk);
	}



	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.contract.app.AbstractContractCostSplitControllerBean#_delete(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectPK[])
	 */
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		
		ContractCostSplitInviteHelper.setInvitePreSplitState(ctx, arrayPK, false);
		
		// TODO �Զ����ɷ������
		if(arrayPK==null||arrayPK.length==0){
			return;
		}
		Set set=new HashSet(arrayPK.length);
		for(int i=0;i<arrayPK.length;i++){
			set.add(arrayPK[i].toString());
		}
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		view.getSelector().add("contractBill.id");
		
		ContractCostSplitCollection splits=getContractCostSplitCollection(ctx, view);
		set.clear();
		for(int i=0; i<splits.size(); i++){			
			BOSUuid costBillId=splits.get(i).getContractBill().getId();
//			checkBeforeDelete(ctx,splitBill);
			//ɾ������
			deleteCostSplit(ctx,CostSplitBillTypeEnum.CONTRACTSPLIT,costBillId);
			set.add(costBillId.toString());
		}
		//���º�ͬ���״̬
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_CON_ContractBill set ");
		builder.appendParam("fsplitState",CostSplitStateEnum.NOSPLIT_VALUE);
		builder.appendSql(" , ");
		builder.appendParam("fisMeasureContract",new Integer(0));
		builder.appendSql(" where ");
		builder.appendParam("fid",set.toArray());
		builder.execute();
		
		super._delete(ctx, arrayPK);
	}

	/**
	 * ���������������������ɾ��
	 * @param ctx
	 * @param splitBill
	 * @throws BOSException
	 * @throws EASBizException
	 */
//	private void checkBeforeDelete(Context ctx,ContractCostSplitInfo splitBill) throws BOSException,EASBizException {
//		if(splitBill == null || splitBill.getState() == null){
//			return;
//		}
//		if(FDCBillStateEnum.AUDITTED.equals(splitBill.getState())){
//			throw new CostSplitException(CostSplitException.ISAUDITTED);
//		}
//	}


	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_save(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectPK, com.kingdee.bos.dao.IObjectValue)
	 */
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		super._save(ctx, pk, model);	
		
		//��ֱ�����¶�Ӧ�б������¶�Ӧ���б�Ԥ�Ȳ�ֵ�״̬Ϊ�Ѳ��
		ContractCostSplitInviteHelper.setInvitePreSplitState(ctx, pk, true);
	}
	
	public void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("id", billId.toString());
		view.getSelector().add("splitState");
		view.getSelector().add("contractBill.state");
		ContractCostSplitCollection c = getContractCostSplitCollection(ctx, view);
		if(c.size()!=1){
			throw new EASBizException(EASBizException.CHECKEXIST); 
		}else{
			if(c.get(0).getContractBill().getState()!=FDCBillStateEnum.AUDITTED){
				throw new CostSplitException(CostSplitException.CONNOTAUDIT);
			}else if(c.get(0).getSplitState()!=CostSplitStateEnum.ALLSPLIT){
				throw new CostSplitException(CostSplitException.PARTSPLIT);
			}else{
				super._audit(ctx, billId);
			}
		}
		
	}
	
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._unAudit(ctx, billId);
		
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("contractBill.state");
		sic.add("splitState");
        sic.add(new SelectorItemInfo("contractBill.orgUnit.id"));
		
		ContractCostSplitInfo splitBill=(ContractCostSplitInfo)super._getValue(ctx,new ObjectUuidPK(billId.toString()),sic);
		
		HashMap param = FDCUtils.getDefaultFDCParam(ctx,splitBill.getContractBill().getOrgUnit().getId().toString());		
		boolean splitBeforeAudit = false;
		if(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT)!=null){
			splitBeforeAudit = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT).toString()).booleanValue();
		}
		//�ú�ͬ�Ѿ���û�в�֣��������
		if(splitBeforeAudit ){
//			MsgBox.showWarning(this, "�ú�ͬ�Ѿ������˲�֣����ܽ����޸�");
//			SysUtil.abort();
			throw new ContractException(ContractException.SPLITBEFOREAUDIT);
		}
//		ContractBillFactory.getLocalInstance(ctx).unAudit(splitBill.getContractBill().getId() );
	}

	protected SelectorItemCollection setSelectorsEntry(Context ctx,SelectorItemCollection sic, boolean isEntry) throws BOSException, EASBizException{
		if(fdcCostSplit==null){
			fdcCostSplit=new FDCCostSplit(ctx);
		}
		return fdcCostSplit.setSelectorsEntry(sic, isEntry);
	}
	protected void _autoSplit4(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		
		//�ȸ��ݱ������¿�Ŀ���
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder.appendSql("update T_Con_ContractCostSplitEntry set FAmount= \n");
		builder.appendSql(" round( \n");
		builder.appendSql(" (FSplitScale*(select FAmount from T_Con_ContractBill where FID=(select FContractBillID from T_Con_ContractCostSplit where FID=T_Con_ContractCostSplitEntry.FParentID)) \n");
		builder.appendSql(" )/100,10) \n");
		builder.appendSql("where FLevel=0 and FParentID in ( \n");
		builder.appendSql("  select split.fid from T_Con_ContractCostSplit split \n");
		builder.appendSql("  inner join T_Con_ContractBill bill on bill.fid=split.FContractBillID \n");
		builder.appendSql("  where bill.FAmount>0 and bill.fid=? \n");
		builder.appendSql(" )");
		builder.addParam(billId.toString());
		builder.executeUpdate();
		builder.clear();
		
		
		AbstractObjectCollection coll=null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();	
    	SelectorItemCollection sic=view.getSelector();  
    	setSelectorsEntry(ctx,sic,true);
    	view.getSorter().add(new SorterItemInfo("seq"));
    	filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", billId.toString()));
    	filter.getFilterItems().add(
				new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));			
    	view.setFilter(filter);
		coll = ContractCostSplitEntryFactory.getLocalInstance(ctx).getContractCostSplitEntryCollection(view);
		FDCSplitBillEntryCollection colls = new FDCSplitBillEntryCollection();
		for(Iterator iter=coll.iterator();iter.hasNext();){
			colls.add((FDCSplitBillEntryInfo)iter.next());
		}
		colls = apptAmount(ctx,colls);
		CoreBaseCollection coreBaseCollection = new CoreBaseCollection();
		for(Iterator iter=colls.iterator();iter.hasNext();){
			coreBaseCollection.add((FDCSplitBillEntryInfo)iter.next());
		}
		ContractCostSplitEntryFactory.getLocalInstance(ctx).update(coreBaseCollection);
		
//		//�������Ѳ�ֽ����״̬
//		builder.appendSql("update T_Con_ContractCostSplit set FAmount=( \n");
//		builder.appendSql(" select FAmount from T_Con_ContractBill where FID=T_Con_ContractCostSplit.FContractBillID \n");
//		builder.appendSql(" ),FSplitState=? where FContractBillID=? ");
//		builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
//		builder.addParam(billId.toString());
//		builder.executeUpdate();
//		builder.clear();
//		
//		//��ͬ���״̬
//		builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
//		builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
//		builder.addParam(billId.toString());
//		builder.getSql();
//		builder.execute();
//		builder.clear();
	}
	/**
	 * ��������̯������FDCCostSplit�ӿڣ�
	 * @return
	 */
    protected FDCSplitBillEntryCollection apptAmount(Context ctx,FDCSplitBillEntryCollection entrys){
    	    	
    	FDCSplitBillEntryInfo topEntry = null;
    	
    	for(Iterator iter=entrys.iterator();iter.hasNext();){
    		topEntry = (FDCSplitBillEntryInfo)iter.next();
	    	
			fdcCostSplit.apptAmount(entrys,topEntry);
    	}
    	return entrys;
    }
    /**
	 * ����ͬ�Ƿ������ܺ�Լ
	 * @param ctx
	 * @param contractId
	 * @return
	 * @throws BOSException
	 */
    protected String _checkIsExistProg(Context ctx, String contractId)
    		throws BOSException {
    	String flag = null;
    	String temp = null;
		String sql = "select fprogrammingcontract from t_con_contractbill where fid='"+ contractId +"'";
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx, sql.toString());
		IRowSet rs = fdcSB.executeQuery();
		try {
			if(rs.next()){
				flag = rs.getString("fprogrammingcontract");
				sql = "select fid from T_CON_PROGRAMMINGCONTRACT where fid='"+flag+"'";
				fdcSB = new FDCSQLBuilder(ctx, sql.toString());
				rs = fdcSB.executeQuery();
				if(rs.next()){
					temp = flag;
				}
			}
		} catch (SQLException e) {
			logger.error(e);
		}
		return temp;
    }
}