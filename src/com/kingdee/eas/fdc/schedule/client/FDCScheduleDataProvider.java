package com.kingdee.eas.fdc.schedule.client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleDependHelper;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ���ȼƻ��״�����Դ
 * <p>
 * ��ǰ������ҵ�����͵������⴦��
 * 
 * @author emanon
 * 
 */
public class FDCScheduleDataProvider extends FDCBillDataProvider {

	// �״��У������ǰ�������Ƿ���ʾȫ����
	boolean isDetail = false;

	public FDCScheduleDataProvider(String billId, IMetaDataPK mainQuery,
			boolean isShowDetailDependInPrint) {
		super(billId, mainQuery);
		this.isDetail = isShowDetailDependInPrint;
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		Variant paramVal = null;
		ArrayList ps = ds.getParams();
		IRowSet iRowSet = null;
		if (ps.size() > 0) {
			DSParam param = (DSParam) ps.get(0);
			paramVal = param.getValue();
		}

		String dsID = ds.getID().toUpperCase();
		// ������FDCScheduleTDQuery
		if ("FDCSCHEDULETDQUERY".equals(dsID)) {
			// ����������Դ�Ľ����
			iRowSet = getMainBillRowSet(ds);
		} else if (dsID.startsWith("AUDITINFO")) {
			// ���ز���ֵΪparamVal�Ĵ�����Դ1�Ľ����
			return getAuditInfoRowSet(ds);
		} else {
			return getOtherSubRowSet(ds);
		}
		return iRowSet;
	}

	/**
	 * Ĭ��������ԴΪFDCScheduleTDQuery
	 * <p>
	 * ����ҵ�����͡�ǰ��������ֶ���Ҫ�ѷ�¼�ϲ���һ���ֶΣ�����Խ�����ٽ��д���<br>
	 * ���������Դ����FDCScheduleTDQuery���޷���֤������ЩҪ������ֶΣ�����д��<br>
	 */
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		IRowSet MRowSet = super.getMainBillRowSet(ds);
		if (MRowSet == null || MRowSet.size() < 1) {
			return MRowSet;
		}
		try {
			// ȡ����������ID
			Set ids = new HashSet();
			while (MRowSet.next()) {
				ids.add(MRowSet.getString("taskEntrys.id"));
			}
			MRowSet.beforeFirst();
			if (ids.size() < 1) {
				return MRowSet;
			}
			// ������Щ�����ǰ������ҵ������
			FDCScheduleTaskCollection taskCol = getTaskDetails(ids);
			// ��ǰ�������ҵ������ת�����ַ�����ʽ���ö��ŷָ�
			if (taskCol != null && taskCol.size() > 0) {
				Map depMap = new HashMap();
				Map bizMap = new HashMap();
				for (int i = 0; i < taskCol.size(); i++) {
					FDCScheduleTaskInfo taskInfo = taskCol.get(i);
					// ����ǰ�������ַ���
					handleDepStr(depMap, taskInfo);
					// ����ҵ�������ַ���
					handleBizStr(bizMap, taskInfo);
				}
				// ���ַ������µ������
				updateRS(MRowSet, depMap, bizMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return MRowSet;
	}

	/**
	 * �����״����������Ϣ
	 * <p>
	 * 
	 * @param ids
	 * @return
	 * @throws BOSException
	 */
	private FDCScheduleTaskCollection getTaskDetails(Set ids)
			throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("dependEntrys.id");
		sic.add("dependEntrys.type");
		sic.add("dependEntrys.difference");
		sic.add("dependEntrys.task.id");
		sic.add("dependEntrys.task.seq");
		sic.add("dependEntrys.task.name");
		sic.add("dependEntrys.dependTask.id");
		sic.add("dependEntrys.dependTask.seq");
		sic.add("dependEntrys.dependTask.name");
		sic.add("bizType.id");
		sic.add("bizType.bizType.id");
		sic.add("bizType.bizType.name");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", ids, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("longNumber"));
		FDCScheduleTaskCollection taskCol = FDCScheduleTaskFactory
				.getRemoteInstance().getFDCScheduleTaskCollection(view);
		return taskCol;
	}

	/**
	 * ����ǰ�������ַ���
	 * 
	 * @param depMap
	 * @param taskInfo
	 */
	private void handleDepStr(Map depMap, FDCScheduleTaskInfo taskInfo) {
		FDCScheduleTaskDependCollection depCol = taskInfo.getDependEntrys();
		if (depCol != null && depCol.size() > 0) {
			for (int j = 0; j < depCol.size(); j++) {
				FDCScheduleTaskDependInfo depInfo = depCol.get(j);
				String depID = depInfo.getDependTask().getId().toString();
				String dep = isDetail ? depInfo.getTask().getName() : String
						.valueOf(depInfo.getTask().getSeq());
				String type = ScheduleDependHelper.getSimpleName(depInfo
						.getType());
				int diff = depInfo.getDifference();
				String strDep = (String) depMap.get(depID);
				if (FDCHelper.isEmpty(strDep)) {
					strDep = ScheduleDependHelper.getDependStr(dep, type, diff);
				} else {
					strDep = strDep
							+ ","
							+ ScheduleDependHelper
									.getDependStr(dep, type, diff);
				}
				depMap.put(depID, strDep);
			}
		}
	}

	/**
	 * ����ҵ�������ַ���
	 * 
	 * @param bizMap
	 * @param taskInfo
	 */
	private void handleBizStr(Map bizMap, FDCScheduleTaskInfo taskInfo) {
		ScheduleTaskBizTypeCollection bizCol = taskInfo.getBizType();
		if (bizCol != null && bizCol.size() > 0) {
			String taskID = taskInfo.getId().toString();
			for (int j = 0; j < bizCol.size(); j++) {
				String bizName = bizCol.get(j).getBizType().getName();
				String strBiz = (String) bizMap.get(taskID);
				if (FDCHelper.isEmpty(strBiz)) {
					strBiz = bizName;
				} else {
					strBiz = strBiz + "," + bizName;
				}
				bizMap.put(taskID, strBiz);
			}
		}
	}

	/**
	 * ���ַ������µ������
	 * 
	 * @param MRowSet
	 * @param depMap
	 * @param bizMap
	 * @throws SQLException
	 */
	private void updateRS(IRowSet MRowSet, Map depMap, Map bizMap)
			throws SQLException {
		if (depMap.size() > 0 || bizMap.size() > 0) {
			while (MRowSet.next()) {
				String taskID = MRowSet.getString("taskEntrys.id");
				MRowSet.updateString("taskEntrys.dependTasks", (String) depMap
						.get(taskID));
				MRowSet.updateString("taskEntrys.bizType", (String) bizMap
						.get(taskID));
			}
			MRowSet.beforeFirst();
		}
	}

}
