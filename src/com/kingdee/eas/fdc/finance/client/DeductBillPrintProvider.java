package com.kingdee.eas.fdc.finance.client;

import java.math.BigDecimal;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

public class DeductBillPrintProvider  extends FDCBillDataProvider{
	
	public DeductBillPrintProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		return super.getMainBillRowSet(ds);
	}
	//�ұ���Ҫ�����⴦��
	 private final BOSObjectType type=(new ContractBillInfo()).getBOSType();
	 
	 private String handleCurrency(String contractId){
	    if(contractId==null){
	    	return null;
	    }
	    try{
		    if(BOSUuid.read(contractId).getType().equals(type)){
		    	ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo("select currenty.id,currency.name where id='"+contractId+"'");
		    	return contractBillInfo.getCurrency().getName();
		    }else{
		    	PayRequestBillInfo payRequestBillInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo("select currenty.id,currency.name where contractId='"+contractId+"'");
		    	return payRequestBillInfo.getCurrency().getName();
		    }
	    }catch (Exception e) {
	    	return null;
		}
	  }
	 //�õ���ͬ����
	 private String getContractNumber(String contractId) {
		 ContractBillInfo info = null;
		 String number="";
			try {
				info =ContractBillFactory
					.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
				number=info.getNumber();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return number;
	 }
	 //�õ���ͬ����
	 private String getContractName(String contractId) {
		 ContractBillInfo info = null;
		 String name="";
			try {
				info =ContractBillFactory
					.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
				name=info.getName();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return name;
	}
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds){
		if (ds.getID().equalsIgnoreCase("DeductBillFLQuery")) {
			// ��ͬ��������
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK(
						"com.kingdee.eas.fdc.finance.app.DeductBillFLQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("Parent.id", billId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return iRowSet;
		}
		return getMainBillRowSet(ds);
//		IRowSet iRowSet = getMainBillRowSet(ds);
//		try {
//			iRowSet.beforeFirst();
//			while(iRowSet.next()){
//				iRowSet.updateObject("currency", handleCurrency(iRowSet.getString("entrys.contractId")));
//				iRowSet.updateObject("entrys.contractNumber", getContractNumber(iRowSet.getString("entrys.contractId")));
//				iRowSet.updateObject("entrys.contractName", getContractName(iRowSet.getString("entrys.contractId")));
//			}
//			iRowSet.beforeFirst();
//		} catch (Exception e) {
//		}
	}
}
