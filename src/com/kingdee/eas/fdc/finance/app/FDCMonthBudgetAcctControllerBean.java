package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctDataInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctException;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctFacadeFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctFactory;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctCollection;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryCollection;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryFactory;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctFactory;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctInfo;
import com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctItemInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCMonthBudgetAcctControllerBean extends AbstractFDCMonthBudgetAcctControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.FDCMonthBudgetAcctControllerBean");

	public FDCBudgetAcctInfo getMyFDCBudgetAcctInfo(Context ctx,String id,String prjId,FDCBudgetPeriodInfo period) throws EASBizException, BOSException {
		if(id!=null){
			return getFDCMonthBudgetAcctInfo(ctx, new ObjectUuidPK(id),getSelectors());
		}else{
			//TODO ȡ�ϸ��µļƻ�
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			view.setFilter(filter);
			filter.appendFilterItem("curProject.id", prjId);
			FDCBudgetPeriodInfo prePeriod=period.getPrePeriod();
			filter.appendFilterItem("fdcPeriod.year", new Integer(prePeriod.getYear()));
			filter.appendFilterItem("fdcPeriod.month", new Integer(prePeriod.getMonth()));
			filter.appendFilterItem("isLatestVer", Boolean.TRUE);
			filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
			view.put("selector", getPreMonthSelectors());
			FDCMonthBudgetAcctCollection c = FDCMonthBudgetAcctFactory.getLocalInstance(ctx).getFDCMonthBudgetAcctCollection(view);
			if(c!=null&&c.size()>1){
				throw new NullPointerException("exist repeat FDCMonthBudgetAcctInfo in one month");
			}
			if(c!=null&&c.size()==1){
				FDCMonthBudgetAcctInfo info=c.get(0);
				info.setId(null);
				int preMonth=prePeriod.getMonth();
				for(int j=info.getEntrys().size()-1;j>=0;j--){
					FDCMonthBudgetAcctEntryInfo entry=(FDCMonthBudgetAcctEntryInfo)info.getEntrys().get(j);
					if(entry.getItemType()==FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT){
						info.getEntrys().remove(entry);
					}
					entry.setId(null);
					entry.setAmount(null);
					entry.setIsAdd(false);
					for(int i=entry.getItems().size()-1;i>=0;i--){
						FDCMonthBudgetAcctItemInfo item=entry.getItems().get(i);
						if(item.getMonth()==preMonth){
							entry.getItems().remove(item);
						}
						if(item.getMonth()==preMonth+1){
							entry.setAmount(item.getAmount());
						}
					}
					
				}
				return info;
			}
			return new FDCMonthBudgetAcctInfo();
		}
		
	}

	public FDCBudgetAcctEntryInfo getMyFDCBudgetAcctEntryInfo() throws EASBizException, BOSException {
		return new FDCMonthBudgetAcctEntryInfo();
	}
	
	protected void handleRecension(Context ctx,FDCBudgetAcctInfo budgetAcctInfo) throws BOSException {
		super.handleRecension(ctx,budgetAcctInfo);
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select max(fvernumber) as vernumber from t_fnc_fdcmonthbudgetacct where fprojectid=? and ffdcperiodId=?");
		builder.addParam(budgetAcctInfo.getCurProject().getId().toString());
		builder.addParam(budgetAcctInfo.getFdcPeriod().getId().toString());
		IRowSet row=builder.executeQuery();
		try{
			if(row.next()){
				budgetAcctInfo.setVerNumber(row.getFloat("vernumber")+0.1f);
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
	}
	/**
	 * 
	 * ����checkNumberDup˽�У���дcheckBill
	 * by hpw 2010-02-10
	 */
	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);

//		che ckNumberDup(ctx, FDCBillInfo);

		checkNameDup(ctx, FDCBillInfo);

	}
	protected void checkNumberDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
		// ��������޶�����  by hpw 2010-2-10
		FDCBudgetAcctInfo budgetAcctInfo = (FDCBudgetAcctInfo)billInfo;
		if(budgetAcctInfo.getId()==null&&budgetAcctInfo.getVerNumber()>1){
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
			if(iCodingRuleManager.isExist(budgetAcctInfo, budgetAcctInfo.getOrgUnit().getId().toString())){
				String number = iCodingRuleManager.getNumber(budgetAcctInfo, budgetAcctInfo.getOrgUnit().getId().toString());
				budgetAcctInfo.setNumber(number);
			}
		}
	}
	
	/**
	 * ȡ���¸���ƻ���selector
	 * @return
	 */
	public SelectorItemCollection getPreMonthSelectors(){
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("curProject.id");
		selector.add("curProject.number");
		selector.add("curProject.name");
		selector.add("entrys.*");
		selector.add("entrys.contractBill.id");
		selector.add("entrys.costAccount.id");
		selector.add("entrys.items.*");
		return selector;
	}
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("curProject.id");
		selector.add("fdcPeriod.id");
		selector.add("sourceBillId");
		FDCMonthBudgetAcctInfo budgetInfo = (FDCMonthBudgetAcctInfo)_getValue(ctx, new ObjectUuidPK(billId), selector);
		//����:�����¶��걨���ύ������,����ͬ������Ŀ�¶��걨����,����Ŀ�¶ȱ�����ʱҪ����е��µĲ��ű�δ����,������ʾ.�˵�ϵͳ��������
		//����������ڹ�����������,�ǲ��ܳ�����ʾ��Ϣ��(�����Ǳ߲�֧���ڹ�����������Ϣ��ʾ)������Ҫ�������߼��ŵ���Ŀ�¶��걨���ύ֮ʱ  by Cassiel_peng 2009-10-20
		//�����߼��ŵ��ύǰ��У������ȷ�ģ�����Ҫע�����³������ύ���������¶ȼƻ��걨��֮����������Ŀ�¶ȼƻ��걨��
		//���ҽ���Ŀ�¶ȼƻ��걨��Ҳ�ύ����ͨ����Ȼ��������Ŀ�ģ��ٷ��������ŵģ��ع�ͷ����������Ŀ�¶ȼƻ��걨����ʱ"��Ŀ�¶ȱ�����ʱҪ����е��µĲ��ű�δ����,������ʾ"
		//����߼���ʧЧ�ˣ��������´�����뻹�Ǳ��뱣�� by cassiel_peng 2010-01-22
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("curProject.id", budgetInfo.getCurProject().getId().toString());
		filter.appendFilterItem("fdcPeriod.id", budgetInfo.getFdcPeriod().getId().toString());
		filter.appendFilterItem("state", FDCBillStateEnum.SAVED);
		filter.appendFilterItem("state", FDCBillStateEnum.SUBMITTED);
		filter.appendFilterItem("state", FDCBillStateEnum.AUDITTING);
		filter.setMaskString(" #0 and #1 and ( #2 or #3 or #4 )");
		boolean isExist = FDCDepMonBudgetAcctFactory.getLocalInstance(ctx).exists(filter);
		if(isExist){
			throw new FDCBudgetAcctException(FDCBudgetAcctException.EXISTUNAUDITTEDDEPMON);
		}
		super._audit(ctx, billId);
		//������
//		if(FDCBillStateEnum.AUDITTED.toString().equals("������")){
			FDCBudgetAcctFacadeFactory.getLocalInstance(ctx).autoUpdateContractPayPlan(billId.toString());
//		}
		if (budgetInfo.getSourceBillId() != null) {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql(" select 1 from  t_fnc_fdcmonthbudgetacctentry  where fparentid=? and fisadd=1 ");
			builder.addParam(billId.toString());
			if(builder.isExist()){
				return;
			}
			// append
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(new FilterInfo());
			view.getFilter().appendFilterItem("parent.id", budgetInfo.getSourceBillId());
			view.getFilter().appendFilterItem("isAdd", Boolean.TRUE);
			view.getSelector().add("*");
			view.getSelector().add("items.*");
			FDCMonthBudgetAcctEntryCollection entrys=FDCMonthBudgetAcctEntryFactory.getLocalInstance(ctx).getFDCMonthBudgetAcctEntryCollection(view);
			if(entrys.size()  <=0) 
				return;
			for(Iterator iter=entrys.iterator();iter.hasNext();){
				FDCMonthBudgetAcctEntryInfo entry=(FDCMonthBudgetAcctEntryInfo)iter.next();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				entry.setParent(budgetInfo);
				for(Iterator iter2=entry.getItems().iterator();iter2.hasNext();){
					FDCMonthBudgetAcctItemInfo item=(FDCMonthBudgetAcctItemInfo)iter2.next();
					item.setEntry(entry);
					item.setId(BOSUuid.create(item.getBOSType()));
				}
			}
			CoreBaseCollection colls=(CoreBaseCollection)entrys.cast(CoreBaseCollection.class);
			if(colls.size()>0){
				FDCMonthBudgetAcctEntryFactory.getLocalInstance(ctx).addnew(colls);	
			}
			
			// update
			builder.clear();
			builder.appendSql(" update t_fnc_fdcmonthbudgetacctentry set (famount,fcost)= "); 	
			builder.appendSql("	(select sum(famount),sum(fcost) from t_fnc_fdcmonthbudgetacctentry sument "); 	
			builder.appendSql("	where sument.fparentid=t_fnc_fdcmonthbudgetacctentry.fparentid and sument.fcontractbillid=t_fnc_fdcmonthbudgetacctentry.fcontractbillid and fitemtype=? and sument.fcostaccountid=t_fnc_fdcmonthbudgetacctentry.fcostaccountid) "); 	
			builder.appendSql(" where fparentid=? and fisadd=1 ");
			builder.addParam(FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT_VALUE);
			builder.addParam(billId.toString());
			builder.execute();
		
		}
	}
	
	protected Map getCostMap(Context ctx, FDCBudgetAcctInfo info) throws BOSException, EASBizException {
		Map costMap = super.getCostMap(ctx, info);
		//����0�㱣����
		Map map=FDCBudgetAcctHelper.getLastPeriodCost(ctx, info.getCurProject().getId().toString(), info.getFdcPeriod());
		for(Iterator iter=map.keySet().iterator();iter.hasNext();){
			String costAccountId=(String)iter.next();
			BigDecimal dyncostamt=(BigDecimal)map.get(costAccountId);
			FDCBudgetAcctDataInfo dataInfo=null;
			if(costMap.get(costAccountId)!=null){
				dataInfo=(FDCBudgetAcctDataInfo)costMap.get(costAccountId);
			}else{
				dataInfo=new FDCBudgetAcctDataInfo();
				costMap.put(costAccountId, dataInfo);
			}
			dataInfo.setLstCost(dyncostamt);
		}
		return costMap;
	}
	protected Map getPayedMap(Context ctx, FDCBudgetAcctInfo budgetAcctInfo) throws BOSException {
		//��������ʵ�ʸ���(��������ǰ��)
		Map payedMap =FDCBudgetAcctHelper.getToPeriodRequestedAmt(ctx, budgetAcctInfo.getCurProject().getId().toString(), budgetAcctInfo.getFdcPeriod().getPrePeriod());
		return payedMap;
	}
	
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		/**
		 * Ŀǰʵ�֣�����ð汾(�����°汾)�����޶��汾���ܷ�����,�Ժ�ɸ��ݾ�������������.
		 */
		FDCMonthBudgetAcctInfo info = 
			FDCMonthBudgetAcctFactory.getLocalInstance(ctx).getFDCMonthBudgetAcctInfo(new ObjectUuidPK(billId));
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select max(fvernumber) as maxvernumber,min(fvernumber) as minvernumber from t_fnc_fdcMonthbudgetacct where fprojectid=? and ffdcperiodId=?");
		builder.addParam(info.getCurProject().getId().toString());
		builder.addParam(info.getFdcPeriod().getId().toString());
		IRowSet row=builder.executeQuery();
		float minVerNumber = 0;
		float maxVerNumber = 0;
		try{
			if(row.next()){
				minVerNumber = row.getFloat("minvernumber");
				maxVerNumber = row.getFloat("maxvernumber");
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		if(Float.compare(info.getVerNumber(),maxVerNumber) < 0 && Float.compare(info.getVerNumber(),minVerNumber) >=0){
			throw new FDCBudgetAcctException(FDCBudgetAcctException.EXISTRECENSIONVER);
		}
		
		//������������뵥�Ŀ�Ŀ������������������
		builder.clear();
		builder.appendSql("select 1 from T_FNC_PayRequestAcctPay acctPay ");
		builder.appendSql("inner join T_FNC_FDCMonthBudgetAcct budget on acctPay.fprojectid=budget.fprojectid and acctPay.fperiodid=budget.ffdcperiodid ");
		builder.appendSql("where budget.fid=?");
		builder.addParam(billId.toString());
		IRowSet rowSet=builder.executeQuery();
		if(rowSet.size()>0){
			throw new FDCBudgetAcctException(FDCBudgetAcctException.CANNTUNAUDIT);
		}
		builder.clear();
		builder.appendSql("update T_FNC_FDCMonthBudgetAcct set fislatestVer=? where fid=?");
		builder.addParam(Boolean.FALSE);
		builder.addParam(billId.toString());
		builder.execute();
		super._unAudit(ctx, billId);
		
		//������°汻���������Ҵ�����һ��������Ļ����Ͱ���һ����Ϊ���°�
		String lastId = null; 
		builder.clear();
		builder.appendSql("select top 1 fid from t_fnc_fdcMonthbudgetacct where fstate='4AUDITTED' and fprojectid=? and ffdcperiodId=? ");
		builder.appendSql("order by fvernumber desc ");
		builder.addParam(info.getCurProject().getId().toString());
		builder.addParam(info.getFdcPeriod().getId().toString());
		IRowSet rowSet1=builder.executeQuery();
		if(rowSet1 != null && rowSet1.size()>0){
			try{
				if(rowSet1.next()){
					lastId = rowSet1.getString("fid");
				}
			}catch(SQLException e){
				throw new BOSException(e);
			}
			
			builder.clear();
			builder.appendSql("update T_FNC_FDCMonthBudgetAcct set fislatestVer=? where fid=?");
			builder.addParam(Boolean.TRUE);
			builder.addParam(lastId);
			builder.execute();
		}
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		//���������⣬����ָ��������
		super._delete(ctx, pk);
	}
	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		Set idSet = new HashSet();
		for(int i=0;i<arrayPK.length;i++){
			idSet.add(arrayPK[i].toString());
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.clear(); 
		builder.appendSql("select 1 from T_FNC_FDCMonthBudgetAcctEntry entry ");
		builder.appendSql(" inner join T_FNC_FDCDepMonBudgetAcct depMon on depMon.fid = entry.fsourceid ");
		builder.appendSql(" where ");
		builder.appendParam("fparentid", idSet.toArray());
		builder.appendSql(" and ");
		builder.appendParam("depMon.fstate", FDCBillStateEnum.AUDITTED_VALUE);
		IRowSet rowSet2=builder.executeQuery();
		if(rowSet2.size()>0){
			throw new FDCBudgetAcctException(FDCBudgetAcctException.EXISTAUDITTEDDEPMON);
		}
		super._delete(ctx, arrayPK);
	}
	public String getActionUI() {
		return "com.kingdee.eas.fdc.finance.client.FDCMonthBudgetAcctEditUI";
	}

	protected void _setAudited(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		
	}

	protected void _update(Context ctx, BOSUuid billId) throws BOSException {
		// TODO �Զ����ɷ������
		
	}
}