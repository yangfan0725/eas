package com.kingdee.eas.fdc.schedule.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleException;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

public class RESchTemplateTaskControllerBean extends AbstractRESchTemplateTaskControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.RESchTemplateTaskControllerBean");
    
    protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
//    	super._checkNumberDup(ctx, model);
    }
    
    protected void checkLNForTree(Context ctx, TreeBaseInfo treeBaseInfo) throws BOSException, EASBizException, TreeBaseException {
//    	super.checkLNForTree(ctx, treeBaseInfo);
    }

	protected void _templateTaskDel(Context ctx, List list) throws BOSException, EASBizException {
		
		/*ɾ��������Ҫ���
		 * 1��ɾ��ѡ������񣨣�
		 * 2�������Ӧ��ǰ�ù�ϵ
		 * 3������ɾ���ڵ�ĸ��ڵ��isLeaf����
		 **/
		if(list.size()<3){
			throw new ScheduleException(ScheduleException.WITHMSG,new Object[]{"������������"});
		}
		List templateList = (List) list.get(0);
		List idList = (List) list.get(1);
		List longNumberList = (List) list.get(2);
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder.appendSql("select distinct fparentid from t_sch_RESchTemplateTask where ");
		builder.appendParam("fid", idList.toArray());
		IRowSet rs = builder.executeQuery();
		Set pidSet = new HashSet();
		try {
			while(rs.next()){
				pidSet.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new ScheduleException(ScheduleException.WITHMSG,new Object[]{"ɾ��ģ������ʱ����"});
		}
		
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
		int longNumberListSize = longNumberList.size();
		if(longNumberListSize>0){
			FilterInfo longNumberFilter = null;
			for(int i=0;i<longNumberListSize;i++){
				longNumberFilter = new FilterInfo();
				longNumberFilter.getFilterItems().add(new FilterItemInfo("template",templateList.get(0).toString()));
				longNumberFilter.getFilterItems().add(new FilterItemInfo("longNumber",longNumberList.get(i).toString()+"!%",CompareType.LIKE));
				filter.mergeFilter(longNumberFilter, "or");
			}
		}
		IObjectPK[] pks = _delete(ctx, filter);
		
		/**���������seq */
		EntityViewInfo view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",FDCHelper.list2Set(idList),CompareType.NOTINCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("template",templateList.get(0).toString()));
		SorterItemCollection sorter = new SorterItemCollection();
	    SorterItemInfo sort = new SorterItemInfo("longNumber");
	    sort.setSortType(SortType.ASCEND);
	    sorter.add(sort);
	    view.setSorter(sorter);
		view.setFilter(filter);
		RESchTemplateTaskCollection cols = getRESchTemplateTaskCollection(ctx, view);
		int size = cols.size();
		String updatesql = "update T_SCH_RESchTemplateTask set fseq = ? where fid = ?";
		List params = new ArrayList(size);
		RESchTemplateTaskInfo temTask = null;
		List innerParamList = null;
		for(int i = 0;i<size;i++){
			temTask = cols.get(i);
			innerParamList = new ArrayList();
			innerParamList.add(new Integer(i+1));
			innerParamList.add(temTask.getId().toString());
			params.add(innerParamList);
		}
		builder.clear();
		builder.executeBatch(updatesql, params);
		
		
		
		
		/**��������������*/
		
		String [] idsArray = new String[pks.length];
		for(int i=0;i<pks.length;i++){
		  idsArray[i] = pks[i].toString();
		}
		
		if(idsArray.length>0){
			builder.clear();
			builder.appendSql("delete from T_SCH_RESchTplTaskPredecessor where ");
			builder.appendParam("FPredecessorTaskID", idsArray);
			builder.execute();
		}
		
		
		/**��������FISLEAF,ֻҪ���ɾ��������ĸ��ڵ㼴��*/
		builder.clear();
		StringBuffer updateLeafSql = new StringBuffer();
		updateLeafSql.append("update t_sch_reschtemplatetask set fisleaf =(select ");
		updateLeafSql.append(" case ");
		updateLeafSql.append(" when (select  ");
		updateLeafSql.append(" count(*) num from t_sch_reschtemplatetask  where flongnumber like (select flongnumber || '!%' from  ");
		updateLeafSql.append("  t_sch_reschtemplatetask  where fid = ? )  and ftemplateid = ? ) >0  then 0  ");
		updateLeafSql.append("  else 1 end ) where fid = ? ");

        params =new ArrayList(3);
        if(innerParamList!=null && !innerParamList.isEmpty())
           innerParamList.clear();
        String id = null;
    	Object o = null;
        if(pidSet.size()>0){
			for (Iterator it = pidSet.iterator(); it.hasNext();) {
					innerParamList = new ArrayList();
					o = it.next();
					if(o == null){
						continue;
					}
					id = o.toString();
					innerParamList.add(id);
					innerParamList.add(templateList.get(0).toString());
					innerParamList.add(id);
					params.add(innerParamList);
				}
	
			builder.executeBatch(updateLeafSql.toString(), params);
		
        }
    }

	protected void _importTasks(Context ctx, CoreBaseCollection coreBaseCollection, RESchTemplateTaskCollection currentTemplateTaskCollection) throws BOSException, EASBizException {
		for(int i = 0; i < coreBaseCollection.size(); i ++){
			String displayName = (String) coreBaseCollection.get(i).get("displayName");
			if(!StringUtils.isEmpty(displayName)&& displayName.length()>80){
				((RESchTemplateTaskInfo)coreBaseCollection.get(i)).setDisplayName(displayName.substring(80));
			}
			addnew(ctx, coreBaseCollection.get(i));
		}
//		for(int k = 0; k < ){
//			
//		}
//		FDCWBSFactory.getLocalInstance(ctx).addnew(corebasecollection);
		/* ɾ��ԭ��ģ������ */
		IObjectPK[] pks = new IObjectPK[currentTemplateTaskCollection.size()];
		for(int f = 0; f < currentTemplateTaskCollection.size(); f ++){
			RESchTemplateTaskInfo schTemplateTaskInfo = currentTemplateTaskCollection.get(f);
			pks[f] = new ObjectUuidPK(schTemplateTaskInfo.getId()); 
		}
		deleteBatchData(ctx,pks);
	}
	
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		IObjectPK pk = super._save(ctx, model);
		// ���¸�ģ����������������򣬰���LongNumber����
		// ��Ϊչʾʱʹ��FSeq�ֶ�����λǰ�����������������;�����ɾ��һ����Ҫ����
		String tempID = ((RESchTemplateTaskInfo) model).getTemplate().getId().toString();
		FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
		sql.appendSql(" update T_SCH_RESchTemplateTask as tbl2 set FSeq =  ");
		sql.appendSql(" (SELECT COUNT(tbl1.FID) FROM T_SCH_RESchTemplateTask AS tbl1 ");
		sql.appendSql(" WHERE tbl1.FLongNumber<=tbl2.FLongNumber ");
		sql.appendSql(" and tbl1.fTempLateid = ? ) ");
		sql.appendSql(" where tbl2.fTempLateid = ? ");
		sql.addParam(tempID);
		sql.addParam(tempID);
		sql.executeUpdate();
		return pk;
	}
    /**
     * ����ɾ��
     */
    protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		for(int i = 0; i<arrayPK.length;i++){
			_delete( ctx, arrayPK[i]); 
		}
	}
}