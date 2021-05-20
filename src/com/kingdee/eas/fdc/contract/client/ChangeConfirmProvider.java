/*jadclipse*/package com.kingdee.eas.fdc.contract.client;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;
public class ChangeConfirmProvider extends FDCBillDataProvider
{
            public ChangeConfirmProvider(String billId, IMetaDataPK mainQuery)
            {




/*  18*/        super(billId, mainQuery);
            }
            public IRowSet getOtherSubRowSet(BOSQueryDataSource ds)
            {
            	if (ds.getID().equalsIgnoreCase("AttachmentQuery")) {
        			// 合同履约保证金及返还部分
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
        		}else if(ds.getID().equalsIgnoreCase("ConChangeSettleEntryPrintQuery")){
        			 IRowSet iRowSet = null;

        			 /*  24*/        try
        			                 {
        			 /* <-MISALIGNED-> */ /*  24*/            IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.contract.app.ConChangeSettleEntryPrintQuery"));
        			 /* <-MISALIGNED-> */ /*  25*/            exec.option().isAutoTranslateEnum = true;
        			 /* <-MISALIGNED-> */ /*  26*/            EntityViewInfo ev = new EntityViewInfo();
        			 /* <-MISALIGNED-> */ /*  27*/            FilterInfo filter = new FilterInfo();
        			 /* <-MISALIGNED-> */ /*  28*/            filter.getFilterItems().add(new FilterItemInfo("parent.id", billId, CompareType.EQUALS));
        			 /* <-MISALIGNED-> */ /*  29*/            ev.setFilter(filter);
        			 /* <-MISALIGNED-> */ /*  30*/            exec.setObjectView(ev);
        			 /* <-MISALIGNED-> */ /*  31*/            iRowSet = exec.executeQuery();
        			 /* <-MISALIGNED-> */ /*  32*/            iRowSet.beforeFirst();
        			                 }
        			 /* <-MISALIGNED-> */ /*  33*/        catch(Exception e)
        			                 {
        			 /* <-MISALIGNED-> */ /*  34*/            e.printStackTrace();
        			                 }
        			 /* <-MISALIGNED-> */ /*  37*/        return iRowSet;
        		}
            	return getMainBillRowSet(ds);
            }
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\sp\sp-fdc_contract_client.jar
	Total time: 82 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/