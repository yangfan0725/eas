package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.util.FDCSplitAcctHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillCollection;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class PaymentSplitFacadeControllerBean extends AbstractPaymentSplitFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.PaymentSplitFacadeControllerBean");

	protected RetValue _getPaymentSplit(Context ctx, ParamValue paramValue) throws BOSException, EASBizException {
		RetValue retValue = new RetValue();
		String prjId = paramValue.getString("prjId");
		//����ڼ�һ��ȡ����������
		String beginPeriod = paramValue.getString("begainPeriod");
		String lastPeriod = paramValue.getString("lastPeriod");
		boolean isDealWithPeriod =paramValue.getBoolean("isDealWithPeriod");
		if(prjId==null){
			throw new NullPointerException("bad prjId!");
		}
		
		/**
		 * R110228-403�����ֱ����ڼ�ȡ���Ǹ����ֵ��ڼ�<br>
		 * �������ֵ��ڼ�����ȡ�����뵥�ڼ䣬���Ե�������ı丶�����<br>
		 * �������ս���븶������һ��<br>
		 * ���ڸ�Ϊֱ��ȡ���������������ͣ����˸��ҵ������<br>
		 * 2011-3-23��emanon
		 */
		Date begin = paramValue.getDate("begin");
		Date end = paramValue.getDate("end");
		
		//������
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select acct.FID acctId, sum(entry.FPayedAmt) payAmt,split.FPaymentBillID payId,TOCHAR(year(pay.FBizDate)) || CASE when month(pay.FBizDate) < 10 then '0' else '' END || TOCHAR(month(pay.FBizDate)) bizDate from T_FNC_PaymentSplitEntry entry \n");
		builder.appendSql("inner join T_FNC_PaymentSplit split on split.FID=entry.FParentID \n");
		builder.appendSql("inner join T_CAS_PaymentBill pay on pay.FID = split.FPaymentBillID \n");
		builder.appendSql("inner join T_FDC_CostAccount acct on acct.FID=entry.FCostAccountID \n");
		builder.appendSql("inner join T_BD_Period bd on bd.FID=split.FPeriodID \n");
		builder.appendSql("where (split.FState<>'9INVALID' or (split.FIslastVerThisPeriod=1 and split.fstate='9INVALID' and split.fperiodid not in (select FID from T_BD_Period where FNumber=?))) \n");
		builder.addParam(new Integer(lastPeriod));
		if(!isDealWithPeriod){
//			builder.appendSql("and bd.FID in (select FID from T_BD_Period where FNumber >= ?) \n");
//			builder.addParam(beginPeriod);
//			builder.appendSql("and bd.FID in (select FID from T_BD_Period where FNumber <= ?) \n");
//			builder.addParam(lastPeriod);
			
			builder.appendSql("and pay.FBizDate >= ? \n");
			builder.addParam(begin);
			builder.appendSql("and pay.FBizDate <= ? \n");
			builder.addParam(end);
		}
		builder.appendSql("and entry.FIsLeaf=1 and split.FIsworkloadbill=0 and acct.FCurProject=? \n");
		builder.addParam(prjId);
		builder.appendSql("group by acct.FID,TOCHAR(year(pay.FBizDate)) || CASE when month(pay.FBizDate) < 10 then '0' else '' END || TOCHAR(month(pay.FBizDate)),split.FPaymentBillID \n");
//		builder.appendSql("order by bd.FNumber ");
		
		
		//��Ŀ�µĶ�����
		Map value = new HashMap();
		//һ���������
		Map split = null;
		Set payIds = new HashSet();
		//�ڼ�
		List periodList = new ArrayList();
		
		retValue.put("splits", value);
		retValue.put("periodList", periodList);
		String temp = null;
		IRowSet rs = builder.executeQuery();
		try {
//			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMM");
			while(rs.next()){
				String acctId = rs.getString("acctId");
//				String period = rs.getString("period");
				String payId = rs.getString("payId");
				BigDecimal payAmt = rs.getBigDecimal("payAmt");
				String period = rs.getString("bizDate");
//				String period = fmt.format(bizDate);
				System.out.print(acctId+"    "+period+"    "+payId +"  "  +payAmt);
				String key = acctId + period;
				if(value.containsKey(key)){
					split = (Map)value.get(key);
					split.put(payId, payAmt);
				}else{
					split = new HashMap();
					split.put(payId, payAmt);
					value.put(key, split);
				}
				payIds.add(payId);
				// �ڼ䲻���ظ�
				if (period != null && !period.equals(temp) && !periodList.contains(period)) {
					periodList.add(period);
					temp=period;
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		if(payIds.size()==0){
			return retValue;
		}
		
		//���
		value = new HashMap();
		retValue.put("pays", value);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("fdcPayReqNumber");
		view.getSelector().add("currency.id");
		view.getSelector().add("currency.name");
		view.getSelector().add("currency.precision");
		view.getSelector().add("contractNo");
		view.getSelector().add("contractNum");
		view.getSelector().add("bizDate");
		view.getSelector().add("number");
		view.getSelector().add("exchangeRate");
		view.getSelector().add("contractBillId");
		view.getSelector().add("actFdcPayeeName.name");
		view.getSelector().add("company.baseCurrency");
		view.getSelector().add("company.baseExchangeTable");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", payIds, CompareType.INCLUDE));
		view.setFilter(filter);
		PaymentBillCollection pays = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(view);
		Set conIds = new HashSet();
		for(Iterator iter=pays.iterator();iter.hasNext();){
			PaymentBillInfo info = (PaymentBillInfo)iter.next();
			value.put(info.getId().toString(), info);
			conIds.add(info.getContractBillId());
		}
		
		if(conIds.size()>0){
			//��ͬ
			value = new HashMap();
			retValue.put("cons", value);
			view = new EntityViewInfo();
			view.getSelector().add("number");
			view.getSelector().add("name");
			view.getSelector().add("partB.name");
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("id", conIds, CompareType.INCLUDE));
			ContractBillCollection cons = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
			for(Iterator iter=cons.iterator();iter.hasNext();){
				ContractBillInfo info = (ContractBillInfo)iter.next();
				value.put(info.getId().toString(), info);
			}
			//���ı�
			value = new HashMap();
			retValue.put("txts", value);
			view = new EntityViewInfo();
			view.getSelector().add("number");
			view.getSelector().add("name");
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("id", conIds, CompareType.INCLUDE));
			ContractWithoutTextCollection txts = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextCollection(view);
			for(Iterator iter=txts.iterator();iter.hasNext();){
				ContractWithoutTextInfo info = (ContractWithoutTextInfo)iter.next();
				value.put(info.getId().toString(), info);
			}
		}
		return retValue;
	}
	
	/**
	 * ����ʵ��Ͷ���ȡ��
	 * @param ctx
	 * @param paramValue
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private RetValue getWorkloadSplitData(Context ctx, Map paramValue) throws BOSException, EASBizException {
		RetValue retValue = new RetValue();
		String prjId = paramValue.get("prjId")!=null?paramValue.get("prjId").toString():"";
		//����ڼ�һ��ȡ����������
		String beginPeriod = paramValue.get("beginPeriod").toString();
		String lastPeriod = paramValue.get("lastPeriod").toString();
		boolean filterByPeriod = ((Boolean)paramValue.get("filterByPeriod")).booleanValue();
		boolean filterByPrj = ((Boolean)paramValue.get("filterByPrj")).booleanValue();
		Set costAccounts = (Set)paramValue.get("costAccounts");
		if(prjId==null){
			throw new NullPointerException("bad prjId!");
		}
		
		// ��ͬȡ���ǹ�������ֵ�����
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select acct.FID acctId, sum(entry.FAmount) amt,bd.FNumber period,split.FWorkLoadBillID confirmID from T_FNC_PaymentSplitEntry entry \n ");
		builder.appendSql("inner join T_FDC_CostAccount acct on acct.FID=entry.FCostAccountID \n");
		builder.appendSql("inner join T_FNC_PaymentSplit split on split.FID=entry.FParentID and split.FIsWorkloadBill=1 \n ");
		builder.appendSql("inner join T_FNC_WorkLoadConfirmBill bill on bill.FID=split.FWorkloadBillid \n ");
		builder.appendSql("inner join  \n ");
		//ѡȡ���ڼ��ȡ�ڼ�֮�ڵ����ݣ�δѡȡ�ڼ�����Ҫȡ���е�����
		if(filterByPeriod){
			builder.appendSql("(select fid,fnumber from T_BD_Period where FNumber >= ?  and FNumber <= ? ) bd on bd.FID=bill.FPeriodID \n");
			builder.addParam(beginPeriod);
			builder.addParam(lastPeriod);
		} else {
			builder.appendSql("	T_BD_Period bd on bd.FID=bill.FPeriodID  \n");
		}
		builder.appendSql("inner join  \n ");
		if(filterByPeriod){
			builder.appendSql("(select fid,fnumber from T_BD_Period where FNumber >= ?  and FNumber <= ? ) bd2 on bd2.FID=split.FPeriodID \n");
			builder.addParam(beginPeriod);
			builder.addParam(lastPeriod);
			builder.appendSql("where (split.fstate<>'9INVALID' \n");  
			builder.appendSql("      or (split.fid in (	\n");
			builder.appendSql("         select head1.fid from t_fnc_paymentsplit head1 where \n");
			builder.appendSql("         head1.flastupdatetime =(select max(ps.flastupdatetime) from t_fnc_paymentsplit ps where ps.fworkloadbillid =head1.fworkloadbillid and  ps.fperiodid in (select fid from t_bd_period where fnumber<=?) )\n");
			builder.appendSql("    		and head1.fworkloadbillid=split.fworkloadbillid and head1.fstate='9INVALID' \n");
			builder.appendSql("   ) \n");
			builder.appendSql("   ) \n");
			builder.appendSql("   ) \n");
			builder.addParam(lastPeriod);
		}else{
			builder.appendSql("	T_BD_Period bd2 on bd2.FID=split.FPeriodID  \n");
			builder.appendSql("where (split.FState<>'9INVALID' )");
		}
		//��ȫ��Ŀ��̬�ɱ���һ�£�����ʾ����
		builder.appendSql("and acct.fcurproject=? and entry.fisleaf=1 \n ");
		builder.addParam(prjId);
		if(!filterByPrj && costAccounts!=null && costAccounts.size()>0 ){
			builder.appendParam(" and acct.fid  ",costAccounts.toArray());
		}
		builder.appendSql("group by acct.FID , bd.FNumber ,split.FWorkLoadBillID\n ");
		builder.appendSql("order by bd.FNumber \n ");
		
		//��Ŀ�µĶ�����
		Map confirmSplits = new HashMap();
		//һ���������
		Map split = null;
		Set confirmBillIds = new HashSet();
		//�ڼ�
		List confirmPeriodList = new ArrayList();
		
		retValue.put("confirmSplits", confirmSplits);
		retValue.put("confirmPeriodList", confirmPeriodList);
		String temp = null;
		IRowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				String acctId = rs.getString("acctId");
				String period = rs.getString("period");
				String confirmID = rs.getString("confirmID");
				BigDecimal payAmt = rs.getBigDecimal("amt");
				if (confirmID != null) {	
					PaymentSplitInfo info = new PaymentSplitInfo();
					info.setAmount(payAmt);
					String key = acctId + period;//��Ŀ+�ڼ�
					
					List splits = null;
					if(confirmSplits.containsKey(key)){
						split = (Map)confirmSplits.get(key);
					}else{
						split = new HashMap();
						confirmSplits.put(key, split);
					}
					confirmBillIds.add(confirmID);
					
					if(split.containsKey(confirmID)){
						splits = (List)split.get(confirmID);
					}else{
						splits = new ArrayList();
						split.put(confirmID, splits) ;
					}
					splits.add(info);
				}
				
				if(period != null && !period.equals(temp)){
					confirmPeriodList.add(period);
					temp=period;
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		if(confirmBillIds.size()==0){
			return retValue;
		}
		
		//������ȷ�ϵ�
		confirmSplits = new HashMap();
		retValue.put("confirms", confirmSplits);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("number");
		view.getSelector().add("name");
		view.getSelector().add("id");
		view.getSelector().add("confirmDate");
		view.getSelector().add("contractBill.id");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", confirmBillIds, CompareType.INCLUDE));
		view.setFilter(filter);
		WorkLoadConfirmBillCollection confirmColls = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoadConfirmBillCollection(view);
		Set conIds = new HashSet();
		for(Iterator iter=confirmColls.iterator();iter.hasNext();){
			WorkLoadConfirmBillInfo info = (WorkLoadConfirmBillInfo)iter.next();
			confirmSplits.put(info.getId().toString(), info);
			conIds.add(info.getContractBill().getId().toString());
		}
		
		if(conIds.size()>0){
			//��ͬ
			confirmSplits = new HashMap();
			String[] contractIds = new String[conIds.size()];
			retValue.put("cons", confirmSplits);
			view = new EntityViewInfo();
			view.getSelector().add("number");
			view.getSelector().add("name");
			view.getSelector().add("partB.name");
			view.getSelector().add("hasSettled");
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("id", conIds, CompareType.INCLUDE));
			ContractBillCollection cons = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
			int i=0;
			for(Iterator iter=cons.iterator();iter.hasNext();){
				ContractBillInfo info = (ContractBillInfo)iter.next();
				confirmSplits.put(info.getId().toString(), info);
				contractIds[i]=info.getId().toString();
				i++;
			}
			try {
				retValue.put("conSplitMap", FDCSplitAcctHelper.getContractSplitData(ctx, prjId));
				retValue.put("changeSplitMap", FDCSplitAcctHelper.getChangeSplitData(ctx, prjId));
				retValue.put("settleSplitMap", FDCSplitAcctHelper.getSettleSplitData(ctx, prjId));
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		// ���ı���ͬ�ĸ���������
		builder.clear();
		builder.appendSql("select acct.FID acctId, sum(entry.FAmount) amt,bd.FNumber period,split.FPaymentBillID paymentId from T_FNC_PaymentSplitEntry entry \n ");
		builder.appendSql("inner join T_FDC_CostAccount acct on acct.FID=entry.FCostAccountID \n");
		builder.appendSql("inner join T_FNC_PaymentSplit split on split.FID=entry.FParentID and split.FIsConWithOutText=1 \n ");
		builder.appendSql("inner join T_Con_ContractWithoutText bill on bill.FID=split.FConWithoutTextID \n ");
		builder.appendSql("inner join  \n ");
		//ѡȡ���ڼ��ȡ�ڼ�֮�ڵ����ݣ�δѡȡ�ڼ�����Ҫȡ���е�����
		if(filterByPeriod){
			builder.appendSql("(select fid,fnumber from T_BD_Period where FNumber >= ?  and FNumber <= ? ) bd on bd.FID=bill.FPeriodID \n");
			builder.addParam(beginPeriod);
			builder.addParam(lastPeriod);
		} else {
			builder.appendSql("	T_BD_Period bd on bd.FID=bill.FPeriodID  \n");
		}
		builder.appendSql("inner join  \n ");
		if(filterByPeriod){
			builder.appendSql("(select fid,fnumber from T_BD_Period where FNumber >= ?  and FNumber <= ? ) bd2 on bd2.FID=split.FPeriodID \n");
			builder.addParam(beginPeriod);
			builder.addParam(lastPeriod);
			builder.appendSql("where (split.fstate<>'9INVALID'   \n");
			builder.appendSql("or (split.fid in (			 \n");
			builder.appendSql("         select head1.fid from t_fnc_paymentsplit head1 where \n");
			builder.appendSql("         head1.flastupdatetime =(select max(ps.flastupdatetime) from t_fnc_paymentsplit ps where ps.fconwithouttextid  =head1.fconwithouttextid  and  ps.fperiodid in (select fid from t_bd_period where fnumber<=?) ) \n");
			builder.appendSql("         and head1.fconwithouttextid =split.fconwithouttextid and head1.fstate='9INVALID' \n"); 
			builder.appendSql(") \n");
			builder.appendSql(") \n");
			builder.appendSql(") \n");
			builder.addParam(lastPeriod);
		}else{
			builder.appendSql("	T_BD_Period bd2 on bd2.FID=split.FPeriodID  \n");
			builder.appendSql("where (split.FState<>'9INVALID' )");
		}
		
		builder.appendSql("and acct.fcurproject=?  and  entry.fisleaf=1  \n ");
		builder.addParam(prjId);
		if(!filterByPrj && costAccounts!=null && costAccounts.size()>0 ){
			builder.appendParam(" and acct.fid  ",costAccounts.toArray());
		}
		builder.appendSql("group by acct.FID , bd.FNumber ,split.FPaymentBillID\n ");
		builder.appendSql("order by bd.FNumber \n ");
		
		//��Ŀ�µĶ�����
		Map paymentSplits = new HashMap();
		//һ���������
		Map _split = null;
		Set payBillIds = new HashSet(); 
		//�ڼ�
		List paymentPeriodList = new ArrayList();
		
		retValue.put("paymentSplits", paymentSplits);
		retValue.put("paymentPeriodList", paymentPeriodList);
		String _temp = null;
		IRowSet _rs = builder.executeQuery();
		try {
			while(_rs.next()){
				String acctId = _rs.getString("acctId");
				String period = _rs.getString("period");
				String payID = _rs.getString("paymentId");
				BigDecimal payAmt = _rs.getBigDecimal("amt");
				if (payID != null) {
					String key = acctId + period;
					if(paymentSplits.containsKey(key)){
						_split = (Map)paymentSplits.get(key);
						_split.put(payID, payAmt);
					}else{
						_split = new HashMap();
						_split.put(payID, payAmt);
						paymentSplits.put(key, _split);
					}
					payBillIds.add(payID);
				}
				
				if(period != null && !period.equals(_temp)){
					paymentPeriodList.add(period);
					_temp=period;
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		if(payBillIds.size()==0){
			return retValue;
		}
		//���
		confirmSplits = new HashMap();
		retValue.put("pays", confirmSplits);
		EntityViewInfo _view = new EntityViewInfo();
		_view.getSelector().add("fdcPayReqNumber");
		_view.getSelector().add("currency.name");
		_view.getSelector().add("currency.precision");
		_view.getSelector().add("contractNo");
		_view.getSelector().add("contractNum");
		_view.getSelector().add("bizDate");
		_view.getSelector().add("number");
		_view.getSelector().add("exchangeRate");
		_view.getSelector().add("contractBillId");
		_view.getSelector().add("actFdcPayeeName.name");
		
		FilterInfo _filter = new FilterInfo();
		_filter.getFilterItems().add(new FilterItemInfo("id", payBillIds, CompareType.INCLUDE));
		_view.setFilter(_filter);
		
		Set txtIds = new HashSet();		
		
		PaymentBillCollection pays = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(_view);
		for(Iterator iter=pays.iterator();iter.hasNext();){
			PaymentBillInfo info = (PaymentBillInfo)iter.next();
			confirmSplits.put(info.getId().toString(), info);
			txtIds.add(info.getContractBillId());
		}
		
		if(txtIds.size()>0){
			//���ı�
			paymentSplits = new HashMap();
			retValue.put("txts", paymentSplits);
			EntityViewInfo view1 = new EntityViewInfo();
			view1.getSelector().add("number");
			view1.getSelector().add("name");
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("id", txtIds, CompareType.INCLUDE));
			view1.setFilter(filter1);
			ContractWithoutTextCollection txts = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextCollection(view1);
			for(Iterator iter=txts.iterator();iter.hasNext();){
				ContractWithoutTextInfo info = (ContractWithoutTextInfo)iter.next();
				paymentSplits.put(info.getId().toString(), info);
			}
			try {
				retValue.put("noTextSplitMap", FDCSplitAcctHelper.getNoTextSplitData(ctx, prjId));
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		
		return retValue;
	}
	/**
	 * ����ʵ��Ͷ����ϸ��ȡ�� by cassiel 2010-07-14
	 */
	protected RetValue _getWorkLoadConfirmSplitDatas(Context ctx, Map paramValue) throws BOSException, EASBizException {
		if(true){
			//�Ľ�����ԭ�����ϸ�̫�����������֤ͨ����ע��������� by hpw 2010.08.11
			return getWorkloadSplitData(ctx,paramValue);
		}
		RetValue retValue = new RetValue();
		String prjId = paramValue.get("prjId")!=null?paramValue.get("prjId").toString():"";
		//����ڼ�һ��ȡ����������
		String beginPeriod = paramValue.get("beginPeriod").toString();
		String lastPeriod = paramValue.get("lastPeriod").toString();
		boolean filterByPeriod = ((Boolean)paramValue.get("filterByPeriod")).booleanValue();
		boolean filterByPrj = ((Boolean)paramValue.get("filterByPrj")).booleanValue();
		Set costAccounts = (Set)paramValue.get("costAccounts");
		if(prjId==null){
			throw new NullPointerException("bad prjId!");
		}
		
		// ��ͬȡ���ǹ�������ֵ�����
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select acct.FID acctId, sum(entry.FAmount) amt,bd.FNumber period,split.FWorkLoadBillID confirmID,split.FIsInvalid isValid \n ");
		builder.appendSql(" from T_FDC_CostAccount acct  \n");
		builder.appendSql("left outer join \n ");
		builder.appendSql("(select * from T_FNC_PaymentSplitEntry where FIsLeaf=1 ) entry on acct.FID=entry.FCostAccountID  \n ");
		builder.appendSql("left outer join \n ");
		builder.appendSql("(select * from T_FNC_PaymentSplit  where  FIsworkloadbill=1) split on split.FID=entry.FParentID  \n ");
		builder.appendSql("left outer join \n ");
//		builder.appendSql("(select * from T_BD_Period  where FNumber > ? and FNumber < ?) bd on bd.FID=split.FPeriodID  \n  ");
		if(filterByPeriod){//ѡȡ���ڼ��ȡ�ڼ�֮�ڵ����ݣ�δѡȡ�ڼ�����Ҫȡ���е�����
			builder.appendSql("(select fid from T_BD_Period  ");
			builder.appendSql("where FNumber >= ? ");
			builder.addParam(beginPeriod);
			builder.appendSql("and FNumber <= ? ");
			builder.addParam(lastPeriod);
			builder.appendSql(") bd on bd.FID=split.FPeriodID \n ");
		} else {
			builder.appendSql("	 T_BD_Period bd on bd.FID=split.FPeriodID  \n");
		}
		builder.appendSql("where acct.fcurproject=?  \n ");
		builder.addParam(prjId);
		if(!filterByPrj && costAccounts!=null && costAccounts.size()>0 ){
			builder.appendParam(" and acct.fid  ",costAccounts.toArray());
		}
		builder.appendSql("group by acct.FID , bd.FNumber ,split.FWorkLoadBillID,split.FIsInvalid \n ");
		builder.appendSql("order by bd.FNumber \n ");
		
		//��Ŀ�µĶ�����
		Map confirmSplits = new HashMap();
		//һ���������
		Map split = null;
		Set confirmBillIds = new HashSet();
		//�ڼ�
		List confirmPeriodList = new ArrayList();
		
		retValue.put("confirmSplits", confirmSplits);
		retValue.put("confirmPeriodList", confirmPeriodList);
		String temp = null;
		IRowSet rs = builder.executeQuery();
		/***
		 * key = confirmId
		 * value = PaymentSplitInfo[���ϵĹ����������Ϣ]
		 */
		Map confirmSplitCatchMap = new HashMap(); 
		try {
			
			while(rs.next()){
				String acctId = rs.getString("acctId");
				String period = rs.getString("period");
				String confirmID = rs.getString("confirmID");
				BigDecimal payAmt = rs.getBigDecimal("amt");
				boolean isInvalid = rs.getBoolean("isValid");//�Ƿ�����
				if (confirmID != null) {	
					PaymentSplitInfo info = new PaymentSplitInfo();
					info.setIsInvalid(isInvalid);
					info.setAmount(payAmt);
					String key = acctId + period;//��Ŀ+�ڼ�
					
					List splits = null;
					if(confirmSplits.containsKey(key)){
						split = (Map)confirmSplits.get(key);
					}else{
						split = new HashMap();
						confirmSplits.put(key, split);
					}
					confirmBillIds.add(confirmID);
					
					if(split.containsKey(confirmID)){
						splits = (List)split.get(confirmID);
					}else{
						splits = new ArrayList();
						split.put(confirmID, splits) ;
					}
					
					splits.add(info);

					
					/**
					 * �����������ϵĹ���������У�������������Ĳ����Ϣ
					 * �ڱ��ڼ��list�У�����һ�����ϵļ�¼
					 */
					if(confirmSplitCatchMap.containsKey(confirmID)){
						PaymentSplitInfo lastInfo = (PaymentSplitInfo)confirmSplitCatchMap.get(confirmID);
						
						PaymentSplitInfo infoCopy = new PaymentSplitInfo();
						
						infoCopy.setIsInvalid(lastInfo.isIsInvalid());
						infoCopy.setAmount(FDCHelper.multiply(lastInfo.getAmount(), FDCHelper._ONE));
						List copySplits = null;
						Map copySplit = null;
						String invalidAcctId = (String)confirmSplitCatchMap.get("acctId");
						String copyKey = invalidAcctId + period;
						if(confirmSplits.containsKey(copyKey)){
							copySplit = (Map)confirmSplits.get(copyKey);
						}else{
							copySplit = new HashMap();
							confirmSplits.put(copyKey, copySplit);
						}
						
						if(copySplit.containsKey(confirmID)){
							copySplits = (List)copySplit.get(confirmID);
						}else{
							copySplits = new ArrayList();
							copySplit.put(confirmID, copySplits) ;
						}
						copySplits.add(infoCopy);
					}
					
					if(isInvalid)
					{
						confirmSplitCatchMap.put(confirmID, info);
						confirmSplitCatchMap.put("acctId", acctId);
					}
				}
				
				if(period != null && !period.equals(temp)){
					confirmPeriodList.add(period);
					temp=period;
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		if(confirmBillIds.size()==0){
			return retValue;
		}
		
		//������ȷ�ϵ�
		confirmSplits = new HashMap();
		retValue.put("confirms", confirmSplits);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("number");
		view.getSelector().add("name");
		view.getSelector().add("id");
		view.getSelector().add("confirmDate");
		view.getSelector().add("contractBill.id");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", confirmBillIds, CompareType.INCLUDE));
		view.setFilter(filter);
		WorkLoadConfirmBillCollection confirmColls = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoadConfirmBillCollection(view);
		Set conIds = new HashSet();
		for(Iterator iter=confirmColls.iterator();iter.hasNext();){
			WorkLoadConfirmBillInfo info = (WorkLoadConfirmBillInfo)iter.next();
			confirmSplits.put(info.getId().toString(), info);
			conIds.add(info.getContractBill().getId().toString());
		}
		
		if(conIds.size()>0){
			//��ͬ
			confirmSplits = new HashMap();
			String[] contractIds = new String[conIds.size()];
			retValue.put("cons", confirmSplits);
			view = new EntityViewInfo();
			view.getSelector().add("number");
			view.getSelector().add("name");
			view.getSelector().add("partB.name");
			view.getSelector().add("amount");
			view.getSelector().add("hasSettled");
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("id", conIds, CompareType.INCLUDE));
			ContractBillCollection cons = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
			int i=0;
			for(Iterator iter=cons.iterator();iter.hasNext();){
				ContractBillInfo info = (ContractBillInfo)iter.next();
				confirmSplits.put(info.getId().toString(), info);
				contractIds[i]=info.getId().toString();
				i++;
			}
			try {
				retValue.put("conSplitMap", FDCSplitAcctHelper.getContractSplitData(ctx, prjId));
				retValue.put("changeSplitMap", FDCSplitAcctHelper.getChangeSplitData(ctx, prjId));
				retValue.put("settleSplitMap", FDCSplitAcctHelper.getSettleSplitData(ctx, prjId));
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		// ���ı���ͬ�ĸ���������
		builder.clear();
		builder.appendSql("select acct.FID acctId, sum(entry.FAmount) amt,bd.FNumber period,split.FPaymentBillID payID \n ");
		builder.appendSql("from T_FDC_CostAccount acct \n");
		builder.appendSql("left outer join (  ");
		builder.appendSql("		select * from T_FNC_PaymentSplitEntry \n ");
		builder.appendSql("		where FIsLeaf=1 \n  ");
		builder.appendSql(" ) entry on acct.FID=entry.FCostAccountID \n ");
		builder.appendSql("left outer join (  \n");
		builder.appendSql("		select * from T_FNC_PaymentSplit  \n");
		builder.appendSql("		where (FState<>'9INVALID' or (FIslastVerThisPeriod=1 and fstate='9INVALID' and fperiodid <> (select FID from T_BD_Period where FNumber=?))) \n");
		builder.addParam(lastPeriod);
		builder.appendSql(" 	and FIsConWithoutText=1  \n  ");
		builder.appendSql(" ) split on split.FID=entry.FParentID \n");
		builder.appendSql("left outer join \n");
		if(filterByPeriod){//ѡȡ���ڼ��ȡ�ڼ�֮�ڵ����ݣ�δѡȡ�ڼ�����Ҫȡ���е�����
			builder.appendSql("	 (  select fid from T_BD_Period  \n");
			builder.appendSql("	 where FNumber >= ? \n");
			builder.addParam(beginPeriod);
			builder.appendSql("	 and FNumber <= ? \n");
			builder.addParam(lastPeriod);
			builder.appendSql("  ) \n");
		} else {
			builder.appendSql("	 T_BD_Period  \n");
		}
		builder.appendSql(" bd on bd.FID=split.FPeriodID \n");
		builder.appendSql(" where acct.fcurproject=? \n  ");  
		builder.addParam(prjId);
		if(!filterByPrj && costAccounts!=null && costAccounts.size()>0 ){
			builder.appendParam(" and acct.fid  ",costAccounts.toArray());
		}
		builder.appendSql("group by acct.FID , bd.FNumber ,split.FPaymentBillID   \n ");
		builder.appendSql("order by bd.FNumber \n ");
		
		//��Ŀ�µĶ�����
		Map paymentSplits = new HashMap();
		//һ���������
		Map _split = null;
		Set payBillIds = new HashSet();
		//�ڼ�
		List paymentPeriodList = new ArrayList();
		
		retValue.put("paymentSplits", paymentSplits);
		retValue.put("paymentPeriodList", paymentPeriodList);
		String _temp = null;
		IRowSet _rs = builder.executeQuery();
		try {
			while(_rs.next()){
				String acctId = _rs.getString("acctId");
				String period = _rs.getString("period");
				String payID = _rs.getString("payID");
				BigDecimal payAmt = _rs.getBigDecimal("amt");
				if (payID != null) {
					String key = acctId + period;
					if(paymentSplits.containsKey(key)){
						_split = (Map)paymentSplits.get(key);
						_split.put(payID, payAmt);
					}else{
						_split = new HashMap();
						_split.put(payID, payAmt);
						paymentSplits.put(key, _split);
					}
					payBillIds.add(payID);
				}
				
				if(period != null && !period.equals(_temp)){
					paymentPeriodList.add(period);
					_temp=period;
				}
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		if(payBillIds.size()==0){
			return retValue;
		}
		//���
		confirmSplits = new HashMap();
		retValue.put("pays", confirmSplits);
		EntityViewInfo _view = new EntityViewInfo();
		_view.getSelector().add("fdcPayReqNumber");
		_view.getSelector().add("currency.name");
		_view.getSelector().add("currency.precision");
		_view.getSelector().add("contractNo");
		_view.getSelector().add("contractNum");
		_view.getSelector().add("bizDate");
		_view.getSelector().add("number");
		_view.getSelector().add("exchangeRate");
		_view.getSelector().add("contractBillId");
		_view.getSelector().add("actFdcPayeeName.name");
		
		FilterInfo _filter = new FilterInfo();
		_filter.getFilterItems().add(new FilterItemInfo("id", payBillIds, CompareType.INCLUDE));
		_view.setFilter(_filter);
		
		Set txtIds = new HashSet();		
		
		PaymentBillCollection pays = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(_view);
		for(Iterator iter=pays.iterator();iter.hasNext();){
			PaymentBillInfo info = (PaymentBillInfo)iter.next();
			confirmSplits.put(info.getId().toString(), info);
			txtIds.add(info.getContractBillId());
		}
		
		if(txtIds.size()>0){
			//���ı�
			paymentSplits = new HashMap();
			retValue.put("txts", paymentSplits);
			EntityViewInfo view1 = new EntityViewInfo();
			view1.getSelector().add("number");
			view1.getSelector().add("name");
			view1.getSelector().add("amount");
			
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("id", txtIds, CompareType.INCLUDE));
			view1.setFilter(filter1);
			ContractWithoutTextCollection txts = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextCollection(view1);
			for(Iterator iter=txts.iterator();iter.hasNext();){
				ContractWithoutTextInfo info = (ContractWithoutTextInfo)iter.next();
				paymentSplits.put(info.getId().toString(), info);
			}
			try {
				retValue.put("noTextSplitMap", FDCSplitAcctHelper.getNoTextSplitData(ctx, prjId));
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		
		return retValue;
	}
}