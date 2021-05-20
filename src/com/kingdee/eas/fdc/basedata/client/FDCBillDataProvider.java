package com.kingdee.eas.fdc.basedata.client;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCBillWFAuditUtil;
import com.kingdee.eas.fdc.basedata.FDCBillWFFacadeFactory;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public abstract class FDCBillDataProvider implements BOSQueryDelegate {

	public String billId = "";
	public List auditAllList = null;
	public IMetaDataPK mainQuery=null;
	public FDCBillDataProvider(String billId,IMetaDataPK mainQuery) {
		this.billId = billId;
		this.mainQuery = mainQuery;
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
        
        if ("MAINBILL".equals(ds.getID().toUpperCase()))//����������Դ����Ϊmainbill
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
	/****
	 * ���������������Ԫ������ʵ�ִ˺���
	 * 
	 * ���Ԫ����û��ָ�����ƣ�û������mainbill����Ϣ����ֻ��һ��Ԫ����
	 * ����ֱ�Ӹ��Ǵ˺�����������getMainBillRowSet()
	 * �������Լ���һЩ�ɵ�ģ��
	 */
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds){
		return null;
	}
	/****
	 * ��ȡ�༶������Ϣ�Ľ��
	 * �����ڹ�����������ĳһ���ڵ����Ϣ����ӡ
	 * �״���������Ϣ�����ã�Ԫ�������Ʊ�����auditInfo��һ������
	 * ���������ò����������ݣ�mainbill������
	 * 
	 * @param ds
	 * @return
	 */
	public IRowSet getAuditInfoRowSet(BOSQueryDataSource ds){
		DynamicRowSet drs = null;
		try {
    		
			String[] col = FDCBillWFAuditUtil.AuditInfoCols;
			
			drs = new DynamicRowSet(col.length);
			for(int i=0;i<col.length;i++){
				ColInfo ci = new ColInfo();
				ci.colType = Types.VARCHAR;
				ci.columnName = col[i];
				ci.nullable = 1;
				drs.setColInfo(i + 1, ci);
			}
			drs.beforeFirst();
			
    		String id = ds.getID();
    		int index = Integer.parseInt(id.substring("auditInfo".length(),id.length()));
    		if(auditAllList==null)
    			auditAllList = FDCBillWFFacadeFactory.getRemoteInstance().getWFAuditResultForPrint(billId);
    		
    		List auditList = new ArrayList();
			if(index-1 >= auditAllList.size()){
				return drs;
			} else if (index-1<0) {
				for(Iterator it = auditAllList.iterator();it.hasNext();){
					List list = (List)it.next();
					auditList.addAll(list);
				}
			} else {
				auditList = (List)auditAllList.get(index-1);
			}
			
			int ind=0;
			for(Iterator it = auditList.iterator();it.hasNext();){
				Map auditInfo = (HashMap)it.next();
				drs.moveToInsertRow();
				Set keySet = auditInfo.keySet();
				for(Iterator keys = keySet.iterator();keys.hasNext();){
					String iKey = (String)keys.next();
					drs.updateString(iKey,(String)auditInfo.get(iKey));
				}
				drs.updateString(FDCBillWFAuditUtil.ID,String.valueOf(++ind));
				drs.updateString(FDCBillWFAuditUtil.BillID,billId);
				drs.insertRow();
			}
			drs.beforeFirst();
			return drs;
			
    	} catch (Exception e) {
            //ExceptionHandler.handle((CoreUI) null,e);
    		e.printStackTrace();
        }
    	return drs;
	}
	/***
	 * ������ʵ�������ݵ��м�����
	 * ���������״������У�����Ԫ���ݵ����ƣ�������mainbill
	 * @param ds
	 * @return
	 */
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
            //System.out.println(exec.getSQL());
            iRowSet = exec.executeQuery();	
            iRowSet.beforeFirst();
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
        
		return iRowSet;
	}

}
