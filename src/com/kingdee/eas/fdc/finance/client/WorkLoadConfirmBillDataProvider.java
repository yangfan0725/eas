package com.kingdee.eas.fdc.finance.client;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 工程量确认单套打数据提供者
 * 
 * @author owen_wen 2011-03-07
 * 
 */
public class WorkLoadConfirmBillDataProvider extends FDCBillDataProvider {
	private static final Logger logger = Logger.getLogger(WorkLoadConfirmBillDataProvider.class);
	private boolean isBaseTask = true;

	/**
	 * 是否基于进度任务工程量填报，需要外部指定，
	 * 
	 * @param isBaseTask
	 */
	public void setIsBaseTask(boolean isBaseTask) {
		this.isBaseTask = isBaseTask;
	}
	
	public WorkLoadConfirmBillDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}

	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
//		if (this.isBaseTask && !ds.getID().equalsIgnoreCase("WorkLoadConfirmBill_baseTask_ForPrintQuery")) {
//			FDCMsgBox.showWarning("启用了参数数FDCSCH04_BASEONTASK，" + "套打模板中的Query数据源设置请选择WorkLoadConfirmBill_baseTask_ForPrintQuery");
//			SysUtil.abort();
//		}
//
//		if (!this.isBaseTask && !ds.getID().equalsIgnoreCase("WorkLoadConfirmBill_baseContract_ForPrintQuery")) {
//			FDCMsgBox.showWarning("启用了参数数FDCSCH003，套打模板中的Query数据源设置请选择WorkLoadConfirmBill_baseContract_ForPrintQuery");
//			SysUtil.abort();
//		}
		
		IRowSet rowSet = super.getMainBillRowSet(ds);
		preRowSetForPeriod(rowSet);
		return rowSet;
	}

	/**
	 * 准备会计期间，确认期间：periodYear + "年" + periodNumber + "期"
	 * 
	 * @author owen_wen 2011-03-19
	 * @param ds
	 * @return
	 */
	private IRowSet preRowSetForPeriod(IRowSet rowSet) {
		try {
			while (rowSet.next()) {
				int periodYear = rowSet.getInt("period.periodYear");
				int periodNumber = rowSet.getInt("period.periodNumber");
				rowSet.updateString("period", periodYear + "年" + periodNumber + "期");
			}
			rowSet.first();
		} catch (SQLException e) {
			logger.debug(e.getMessage(), e);
			e.printStackTrace();
		}
		return rowSet;
	}
	
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
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
		} else {
			return getMainBillRowSet(ds);
		}
	}

}
