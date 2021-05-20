package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.finance.DeductBillEntryCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryFactory;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.jdbc.rowset.IRowSet;


public class ConSettlementPrintProvider extends FDCBillDataProvider {


	private IMetaDataPK attchQuery;

	public ConSettlementPrintProvider(String billId, IMetaDataPK mainQuery,IMetaDataPK attchQuery) {
		super(billId, mainQuery);
		this.attchQuery=attchQuery;
	}

	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds){
		if (ds.getID().equalsIgnoreCase("AttachmentQuery")) {
			// ��ͬ��Լ��֤�𼰷�������
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.basedata.app.AttachmentQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("boAttchAsso.boID",billId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return iRowSet;
		}
		return super.getMainBillRowSet(ds);
	}
	
	public IRowSet getAttchRowSet(BOSQueryDataSource ds){
		IRowSet iRowSet = null;
        try {
        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(attchQuery);
            exec.option().isAutoTranslateEnum= true;
            EntityViewInfo ev = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("boAttchAsso.boID", billId, CompareType.EQUALS));
            ev.setFilter(filter);
            exec.setObjectView(ev);
            iRowSet = exec.executeQuery();	
            iRowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowSet;
	}
	
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds){
		IRowSet iRowSet = null;
        try {
        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(mainQuery);
            exec.option().isAutoTranslateEnum= true;
            EntityViewInfo ev = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("id", billId, CompareType.EQUALS));
            ev.setFilter(filter);            
            exec.setObjectView(ev);
            iRowSet = exec.executeQuery();
            if(iRowSet.next()){
            	 //�Ѹ����̿�
            	ContractSettlementBillInfo settle=ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillInfo("select contractBill.id from where id='"+billId+"'");
                BigDecimal hasPayAmount = getHasPayAmount(settle.getContractBill().getId().toString());
                //����ˮ���
                BigDecimal weCharge = getweCharge(settle.getContractBill().getId().toString());
               //����Ӧ�ۿ� = �Ѹ����̿� + ����ˮ���
                BigDecimal deductAmount = hasPayAmount.add(weCharge);
                //�������� = ȷ����� - ����Ӧ�ۿ�
                BigDecimal curOriginalAmount =  iRowSet.getBigDecimal("curOriginalAmount");
                BigDecimal remainAmount = curOriginalAmount.subtract(deductAmount);
                //Ӧ�����  = �������  - ���޽�
                BigDecimal guaranteAmt = iRowSet.getBigDecimal("guaranteAmt");
                BigDecimal paymentAmount = remainAmount.subtract(guaranteAmt);
                //����ֵ
                iRowSet.updateBigDecimal("hasPayAmount", hasPayAmount);
                iRowSet.updateBigDecimal("weCharge", weCharge);
                iRowSet.updateBigDecimal("deductAmount", deductAmount);
                iRowSet.updateBigDecimal("remainAmount", remainAmount);
                iRowSet.updateBigDecimal("paymentAmount", paymentAmount);
            }
           
            iRowSet.beforeFirst();
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
        
		return iRowSet;
	}

	private BigDecimal getweCharge(String billId) throws BOSException {
		BigDecimal weCharge = FDCHelper.ZERO;
		String bill = billId;
		if(bill != null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("contractId", bill));
			
			DeductBillEntryCollection col = DeductBillEntryFactory.getRemoteInstance().getDeductBillEntryCollection(view);
			for (int i = 0; i < col.size(); i++) {
				DeductBillEntryInfo info = col.get(i);
				weCharge  = weCharge.add(info.getDeductAmt()); 
			}
		}
		return weCharge;
	}

	private BigDecimal getHasPayAmount(String billId) throws BOSException, SQLException {
		BigDecimal hasPayAmt=FDCHelper.ZERO;
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select sum(FActPayLocAmt) hasPayed from T_CAS_PaymentBill \n ");
		builder.appendSql("where  FContractBillId=?  \n ");
	    //�ĳ��Ѹ���״̬�ĸ�����ۼƸ����� 
		builder.appendSql("and FBillStatus =15 \n");
		//builder.appendSql("and FBillStatus in (12,15) \n");
		builder.addParam(billId);
		IRowSet rowSet3=builder.executeQuery();
		if(rowSet3.next()){
			hasPayAmt=FDCHelper.toBigDecimal(rowSet3.getBigDecimal("hasPayed"), 2);
		}
		return hasPayAmt;
	}
}
