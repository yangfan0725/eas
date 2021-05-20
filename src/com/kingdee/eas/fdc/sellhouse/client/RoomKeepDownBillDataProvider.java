package com.kingdee.eas.fdc.sellhouse.client;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;

public class RoomKeepDownBillDataProvider extends FDCBillDataProvider{
private static final Logger logger = Logger.getLogger(RoomKeepDownBillDataProvider.class);
	
	public RoomKeepDownBillDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}
	
	public IRowSet execute(BOSQueryDataSource ds) {
		// TODO �Զ����ɷ������
		Variant paramVal = null;
        ArrayList ps = ds.getParams();
        IRowSet iRowSet = null;
        if (ps.size() > 0)
        {
            DSParam param = (DSParam)ps.get(0); 
            paramVal = param.getValue();	
        }
        
        if ("RoomKeepDownBillPrintQuery".equalsIgnoreCase(ds.getID().toUpperCase()))//����������Դ����Ϊmainbill
        {
            //����������Դ�Ľ����
        	iRowSet = getMainBillRowSet(ds);
        }
        else if(ds.getID().toUpperCase().startsWith("AUDITINFO")) 
        {
            //���ز���ֵΪparamVal�Ĵ�����Դ1�Ľ����
        	//iRowSet = super.execute(ds);
        	return getAuditInfoRowSet(ds);
        }
        else{
        	
        	return getOtherSubRowSet(ds);
        }
        return iRowSet;
	}
}
