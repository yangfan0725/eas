package com.kingdee.eas.fdc.tenancy.client;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;

public class QuitTenancyDataProvider extends FDCBillDataProvider {

	public QuitTenancyDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}
	
	@Override
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		return getMainBillRowSet(ds);
	}

}
