package com.kingdee.eas.fdc.schedule.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.AchievementProfessionFactory;
import com.kingdee.eas.fdc.schedule.AchievementProfessionInfo;
import com.kingdee.eas.fdc.schedule.AchievementTypeInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.jdbc.rowset.IRowSet;

public class LbFacadeControllerBean extends AbstractLbFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.app.LbFacadeControllerBean");

	/**
	 * �õ��ɹ�����������
	 * @throws SQLException 
	 */
	protected String _getBiggestNumber(Context ctx) throws BOSException {
		String strNum = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// ֱ��дSQL��ѯ��������
		builder.appendSql("select max(fnumber) as fnumber from T_SCH_AchievementType");
		IRowSet rowSet = builder.executeQuery();
		try {
			// �Ƿ���ֵ
			while (rowSet.next()) {
				strNum = rowSet.getString("fnumber");
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		if (strNum == "" || strNum == null) {
			strNum = "00";
		}
		return strNum;
	}

	protected boolean _isQuoteProfeseeion(Context ctx, String fprofession) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select 1 from t_sch_achievementtype where fprofession = ?");
		builder.addParam(fprofession);
		return builder.isExist();
	}

	protected boolean _isQuoteStage(Context ctx, String fstage) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select 1 from t_sch_achievementtype where fstage = ?");
		builder.addParam(fstage);
		return builder.isExist();
	}

	// "֧�ֶ���ɾ�������ѡ��ר������ѱ��Ƶġ�ר��ƻ�����������ɾ����"�����28ҳɾ��������
	protected boolean _isQuoteScheduleTask(Context ctx, String strFachievementTypeId) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select head.fid from t_sch_fdcschedule head join t_sch_fdcscheduletask task ");
		builder.appendSql(" on head.fid = task.fscheduleid where task.fachievementTypeId=?");
		builder.addParam(strFachievementTypeId);
		return builder.isExist();
	}

	protected String _getBiggestNumber2(Context ctx) throws BOSException {
		String strNum = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// ֱ��дSQL��ѯ��������
		builder.appendSql("select max(fnumber) as fnumber from T_SCH_AchievementProfession");
		IRowSet rowSet = builder.executeQuery();
		try {
			// �Ƿ���ֵ
			while (rowSet.next()) {
				strNum = rowSet.getString("fnumber");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (strNum == "" || strNum == null) {
			strNum = "00";
		}
		return strNum;
	}

	protected String _getBiggestNumber3(Context ctx) throws BOSException {
		String strNum = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// ֱ��дSQL��ѯ��������
		builder.appendSql("select max(fnumber) as fnumber from T_SCH_AchievementStage");
		IRowSet rowSet = builder.executeQuery();
		try {
			// �Ƿ���ֵ
			while (rowSet.next()) {
				strNum = rowSet.getString("fnumber");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (strNum == "" || strNum == null) {
			strNum = "00";
		}
		return strNum;
	}

	// ���������׶Σ��ֵ�����ط��ǲ�����������
	protected List _getAchievementManagerHeader1(Context ctx) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// �����Եļ��������ֶζ���fname_l2
		builder.appendSql("select fid as id ,fname_l2 as name from t_sch_achievementstage order by fnumber");

		IRowSet set = builder.executeQuery();
		List list = new ArrayList();
		try {
			while (set.next()) {
				Map map = new HashMap();
				map.put("id", set.getString("id"));
				map.put("name", set.getString("name"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// ��������רҵ
	protected List _getAchievementManagerHeader2(Context ctx) throws BOSException {
		EntityViewInfo info = new EntityViewInfo();
		SelectorItemCollection col = new SelectorItemCollection();
		col.add(new SelectorItemInfo("id"));
		col.add(new SelectorItemInfo("name"));
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo sorterItem = new SorterItemInfo("number");
		sorterItem.setSortType(SortType.ASCEND);
		sorter.add(sorterItem);
		info.setSorter(sorter);
		info.setSelector(col);
		CoreBaseCollection col2 = AchievementProfessionFactory.getLocalInstance(ctx).getCollection(info);
		List list = new ArrayList();
		for (int i = 0; i < col2.size(); i++) {
			AchievementProfessionInfo info2 = (AchievementProfessionInfo) col2.get(i);
			Map map = new HashMap();
			map.put("id", info2.getId().toString());
			map.put("name", info2.getName());
			list.add(map);
		}
		return list;
	}

	protected List _getAchievementManagerData(Context ctx, String curproID) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select mag.FID id,mag.FName name,aState.FID rowIdd,aPro.FID colIdd,mag.fstate fstate");
		builder.appendSql(" from T_SCH_AchievementManager as mag");
		builder.appendSql(" left join T_SCH_AchievementType as aType");
		builder.appendSql(" on aType.FId = mag.FAchievementType");
		builder.appendSql(" left join T_SCH_AchievementStage as aState");
		builder.appendSql(" on aState.FID = aType.FStage");
		builder.appendSql(" left join T_SCH_AchievementProfession as aPro");
		builder.appendSql(" on aPro.FID = aType.FProfession");

		// ���ӹ������� �ɹ����������и��ֶι������������
		builder.appendSql(" left join t_sch_fdcscheduletask as task");
		builder.appendSql("	on task.fid = mag.FRelateTaskID");
		// ����鵽�ƻ�
		builder.appendSql("	left join t_sch_fdcschedule as fdc");
		builder.appendSql("	on task.FScheduleID = fdc.fid");
		// �ƻ��ڲ鵽������Ϣ
		builder.appendSql(" left join T_FDC_CurProject as curpro");
		builder.appendSql("	on fdc.FProjectID = curpro.fid");
		// ҵ������Ϊ�׶��Գɹ�Ϊ�յ���������(���������Ľ�)
		// builder.appendSql(" where curpro.fid like '%" + curproID
		// +
		// "%' and task.fbiztype='Rz+dS7ECSfqM4kEJqGawYWLF6cA=' and fdc.fprojectspecialid is null and mag.fachievementtype is not null"
		// );
		builder.appendSql(" where curpro.fid like '%" + curproID + "%' and mag.fachievementtype is not null");
		// builder.addParam(curproID);
		// builder.appendSql(" where mag.fachievementtype is not null");
		// if (FDCHelper.isEmpty(curproID)) {
		// builder.appendSql(" and curpro.fid is null");
		// } else {
		// builder.appendSql(" and curpro.fid =?");
		// builder.addParam(curproID);
		// }
		IRowSet set = builder.executeQuery();
		List data = new ArrayList();
		try {
			Map map = new HashMap();
			while (set.next()) {
				String id = set.getString("id");
				String name = set.getString("name");
				String state = set.getString("fstate");
				String rowID = set.getString("rowIdd");
				String colID = set.getString("colIdd");

				Map cellData = new HashMap();
				cellData.put("id", id);
				cellData.put("name", name);
				cellData.put("state", state);

				String key = rowID + "@" + colID;
				if (map.containsKey(key)) {
					List lst = (List) map.get(key);
					lst.add(cellData);
					map.put(key, lst);
				} else {
					List lst = new ArrayList();
					lst.add(cellData);
					map.put(key, lst);
				}
			}
			data.add(map);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data;
	}

	protected AchievementTypeInfo _getAchievementManagerInfo(Context ctx, String taskTypeName) throws BOSException {
		return null;
	}

	protected FDCScheduleInfo _isMain(Context ctx, String taskid) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select shead.fid fid,shead.fprojectSpecialid fpsid from t_sch_fdcschedule as shead join t_sch_fdcscheduletask as sbody");
		builder.appendSql(" on shead.fid = sbody.fscheduleid");
		builder.appendSql(" where sbody.fid=?");
		builder.addParam(taskid);
		IRowSet irowSet = builder.executeQuery();
		FDCScheduleInfo info = new FDCScheduleInfo();
		try {
			if (irowSet.next()) {
				info.setId(BOSUuid.read(irowSet.getString("fid")));
				if (FDCHelper.isEmpty(irowSet.getString("fpsid"))) {
					info.setProjectSpecial(null);
					return info;
				}
				info.setProjectSpecial(new ProjectSpecialInfo());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}
}