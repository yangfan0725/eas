package com.kingdee.eas.fdc.schedule.app;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideFactory;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
/**
 * @(#)							
* ��Ȩ��	�����������������޹�˾��Ȩ����<p> 
* ������ Ϊ��׼����ָ����������
 * 
 * @author ����ΰ
 * @version EAS7.0
 * @createDate 2011-8-17
 * @see
 */
public class StandardTaskGuideFacadeControllerBean extends AbstractStandardTaskGuideFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.StandardTaskGuideFacadeControllerBean");
    /**
     						
    * 
    * ������ �÷������ڶ���ɾ��
     * 
     * @author ����ΰ
     * @version EAS7.0
     * @createDate 2011-8-17
     * @see
     */
    protected void _standardTaskGuideDel(Context ctx, List list)throws BOSException{
    	Set idSet =  new HashSet();
    	
    	for(int i=0; i< list.size();i ++){
    		StandardTaskGuideInfo info = (StandardTaskGuideInfo)list.get(i);
    		String sql = info.getLongNumber();
    		FDCSQLBuilder bulider  = new FDCSQLBuilder(ctx);
    		bulider.appendSql("select fid,flongnumber from t_sch_standardtaskguidenew where flongnumber like '"+sql+"%'");
    		IRowSet set = bulider.executeQuery();
    		try {
				while(set.next()){
					String id =  set.getString("fid").toString();
					RESchTemplateTaskFactory.getLocalInstance(ctx);
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.appendFilterItem("standardTask.id", id);
					view.setFilter(filter);
					RESchTemplateTaskCollection schTemplateTaskCollection = RESchTemplateTaskFactory
							.getLocalInstance(ctx).getRESchTemplateTaskCollection(view);
					if (schTemplateTaskCollection.size() > 0) {//�ж��Ƿ�����
						throw new EASBizException(new NumericExceptionSubItem("101", "�ñ�׼ָ���ĵ��Ѿ������ã�������ɾ��"));
					}
					idSet.add(id);
					StandardTaskGuideInfo guideInfo=StandardTaskGuideFactory.getLocalInstance(ctx).getStandardTaskGuideInfo(new ObjectStringPK(id));
//					if(guideInfo.getEntrys().size()>0){
//						for(int j=0;i<guideInfo.getEntrys().size();j++){
//							StandardTaskGuideEntryInfo entryInfo=guideInfo.getEntrys().get(j);
//							entryMap.put(id,entryInfo.getId());
//						}
//					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			}
    	}
    	IObjectPK[] pk= new IObjectPK[idSet.size()];
    	Iterator it=idSet.iterator();
    	int i=0 ;
    	
    	while(it.hasNext()){
    		String pkId=it.next().toString();
    		pk[i] = new ObjectUuidPK(pkId);
//    		deleteImg(ctx,pkId);
    		i ++;
    	}
    
    	
    	
    	try {

// 		    deledtDoc(ctx,entryMap);
    		//ɾ��
			StandardTaskGuideFactory.getLocalInstance(ctx).delete(pk);
			
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    
   

	 /*						
		  */
	protected Set _getAllId(Context ctx, List list) throws BOSException {
		Set idSet =  new HashSet();
    	
    	for(int i=0; i< list.size();i ++){
    		StandardTaskGuideInfo info = (StandardTaskGuideInfo)list.get(i);
    		String sql = info.getLongNumber();
    		FDCSQLBuilder bulider  = new FDCSQLBuilder(ctx);
    		bulider.appendSql("select fid,flongnumber from t_sch_standardtaskguidenew where flongnumber like '"+sql+"%'");
    		IRowSet set = bulider.executeQuery();
    		try {
				while(set.next()){
					String id =  set.getString("fid").toString();
					RESchTemplateTaskFactory.getLocalInstance(ctx);
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.appendFilterItem("standardTask.id", id);
					view.setFilter(filter);
					RESchTemplateTaskCollection schTemplateTaskCollection = RESchTemplateTaskFactory
							.getLocalInstance(ctx).getRESchTemplateTaskCollection(view);
					if (schTemplateTaskCollection.size() > 0) {//�ж��Ƿ�����
						throw new EASBizException(new NumericExceptionSubItem("101", "�ñ�׼ָ���ĵ��Ѿ������ã�������ɾ��"));
					}
					idSet.add(id);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			}
    	}
    
		return idSet;
	}
}