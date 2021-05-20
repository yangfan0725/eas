package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.ctrl.reportone.r1.print.data.AbstractPrintDataProvider;
import com.kingdee.bos.ctrl.reportone.r1.print.data.R1PrintDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class QuestionDefinitionPriverProvider extends FDCBillDataProvider {

	private static Logger log = Logger.getLogger(PaymentPrintDataProvider.class);

	private IMetaDataPK qpk = null;
	private String id = null;

	public QuestionDefinitionPriverProvider(String id, IMetaDataPK qpk) {
		super(id, qpk);
		this.id = id;
		this.qpk = qpk;
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		Variant paramVal = null;
		ArrayList ps = ds.getParams();
		IRowSet iRowSet = null;
		if (ps.size() > 0) {
			DSParam param = (DSParam) ps.get(0);
			paramVal = param.getValue();
		}
		if ("QuestionPaperDefineAndDocumentPrintQuery".equals(ds.getID().toUpperCase()))// ����������Դ����Ϊmainbill
		{
			// ����������Դ�Ľ����
			iRowSet = getMainBillRowSet(ds);
		} else if ("PAYLIST".equals(ds.getID().toUpperCase())) {
			return getOtherSubRowSet(ds);
		}
		// ����״��Ԫ���ݴ���2��ִ������ĺ���
		else {
			return getOtherSubRowSet(ds);
		}
		return iRowSet;
	}

	

	// �������Ԫ���ݵĵ�PK
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory
					.getRemoteInstance(new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.PayListQuery"));
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			ev.getSorter().add(new SorterItemInfo("seq"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", id, CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			// System.out.println(exec.getSQL());
			iRowSet = exec.executeQuery();
			while (iRowSet.next()) {
				BigDecimal apAmount = iRowSet.getBigDecimal("apAmount");
				String upperApAmount = FDCClientHelper.getChineseFormat(
						apAmount, false);
				iRowSet.updateString("upperApAmount", upperApAmount);
			}
			iRowSet.beforeFirst();
		} catch (BOSException e) {
			log.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}

		return iRowSet;
	}

	// ��ѯ��Ԫ����
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(qpk);
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.EQUALS));
			//��Ϊ�ͻ���Ϣ��ֻһ���������״������붩�����������Ի��ظ���ӡ���Ǿ�ֻ�����ͻ����˳�����
			filter.getFilterItems().add(new FilterItemInfo("customerInfo.seq", new Integer(0), CompareType.EQUALS));
			// filter.getFilterItems().add(new
			// FilterItemInfo("payListEntry.MONEYDEFINE.moneyType",
			// MoneyTypeEnum.EARNESTMONEY_VALUE, CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			// System.out.println(exec.getSQL());
			iRowSet = exec.executeQuery();			
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		return iRowSet;
	}
}

