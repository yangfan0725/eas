package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.ctrl.common.util.DBUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class UpdateDataFacadeControllerBean extends AbstractUpdateDataFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.UpdateDataFacadeControllerBean");
    
    /**
     * �� ���� ¥�� ʵ���ϵ� ¥���¼�����ݣ������� �������棬 ¥�� �������Ե�����
     */
    protected boolean _updateBuildingFloorForRoom(Context ctx)throws BOSException, EASBizException
    {
    	boolean debug = false;
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	view.getSelector().add("floorCount");
    	view.getSelector().add("subFloorCount");
    	view.getSelector().add("floorEntry.*");
    	view.getSelector().add("id");
    	
    	BuildingCollection buildingColl = BuildingFactory.getLocalInstance(ctx).getBuildingCollection(view);
    	
    	for(int i = 0; i < buildingColl.size(); i ++)
    	{
    		BuildingInfo building = buildingColl.get(i);
    		
    		BuildingFloorEntryCollection floorColl = building.getFloorEntry();
    		
    		int floorCount = building.getFloorCount();
			int subFloorCount = building.getSubFloorCount();
			if((floorCount != 0 || subFloorCount != 0) && (floorColl == null || floorColl.size() < 1) )
    		{
    			this.generateFloorEntry(ctx, building);
    		}
    	}
    	
    	//���·����� ¥�������������
    	
    /*	view = new EntityViewInfo();
    	filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	view.getSelector().add("id");
    	view.getSelector().add("buildingFloor.id");
    	view.getSelector().add("building.id");
    	view.getSelector().add("floor");
    	
    	filter.getFilterItems().add(new FilterItemInfo("buildingFloor",null));
    	
    	RoomCollection roomColl = RoomFactory.getLocalInstance(ctx).getRoomCollection(view);
    	
    	for(int i = 0; i < roomColl.size(); i ++)
    	{
    		RoomInfo room = roomColl.get(i);
    		
    		BuildingFloorEntryInfo entry = this.getBuildingFloorEntryInfo(ctx, room);
    		
    		room.setBuildingFloor(entry);
    		
    		SelectorItemCollection selColl = new SelectorItemCollection();
    		selColl.add("buildingFloor");
    		
    		RoomFactory.getLocalInstance(ctx).updatePartial(room, selColl);
    		
    	}*/
    	
    	String sql = " update t_she_room as r set fbuildingfloorid = " +
    			" (select fid from t_she_buildingfloorentry as b where r.fbuildingid = b.fbuildingid and r.ffloor = b.ffloor) " +
    			" where r.fbuildingfloorid is null ";
    	
    	DbUtil.execute(ctx, sql);
    	
    	
        return debug;
    }
    
    /**
     * 
     * @param building
     * @param room
     * @return
     * @throws BOSException 
     */
    private BuildingFloorEntryInfo getBuildingFloorEntryInfo(Context ctx, RoomInfo room) throws BOSException
    {
    	BuildingFloorEntryInfo info = null;
    	
    	int floor = room.getFloor();
    	String id = room.getBuilding().getId().toString();
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	filter.getFilterItems().add(new FilterItemInfo("building",id));
    	filter.getFilterItems().add(new FilterItemInfo("floor",new Integer(floor)));
    	
    	BuildingFloorEntryCollection entryColl = BuildingFloorEntryFactory.getLocalInstance(ctx).getBuildingFloorEntryCollection(view);
    	
    	if(entryColl.size() == 1)
    	{
    		info = entryColl.get(0);
    	}
    	else
    	{
    		logger.error("����ID "+ room.getId()+ " ,¥�� Ϊ "+ floor+ " �� ��¥���¼�У��ж��¥���Ӧ");
    	}
    	return info;
    }
    
    
    
    /**
     * ���� ¥�� ¥���¼����
     * @param ctx
     * @param building
     * @throws EASBizException
     * @throws BOSException
     */
    private void generateFloorEntry(Context ctx,BuildingInfo building) throws EASBizException, BOSException
    {
    	int floorCount = building.getFloorCount();
		int subFloorCount = building.getSubFloorCount();
		
		if(floorCount <= subFloorCount)
		{
			logger.error("IDΪ "+ building.getId() + " �� ¥����¥�����ô���...");
		}
		

		for(int i = subFloorCount; i <= floorCount; i ++)
		{
			if(i == 0)
				continue;
			
			BuildingFloorEntryInfo entry = new BuildingFloorEntryInfo();
			entry.setBuilding(building);
			entry.setFloor(i);
			entry.setFloorAlias(String.valueOf(i));
			entry.setIsEnable(true);
			
			BuildingFloorEntryFactory.getLocalInstance(ctx).addnew(entry);
		}

    }
    
    
    
    
    /**
     * ��� ¥���ϣ�¥��ķ�¼�����Ƿ�Ҫ��������
     */
    protected boolean _isNeedUpdateBuildingFloorForRoom(Context ctx)throws BOSException, EASBizException
    {
    	boolean debug = false;
    	
    	String sql = "select fid from t_she_building";
    	
    	IRowSet rowSet =  DbUtil.executeQuery(ctx, sql);
    	
    	try
		{
			while(rowSet.next())
			{
				String fid = rowSet.getString("fid");
				
				String tempSql ="select fid from T_SHE_BuildingFloorEntry where FBuildingID = '"+ fid + "'";
				IRowSet tempSet = DbUtil.executeQueryNoTx(ctx, tempSql);
			
				if(!tempSet.next())
				{
					debug = true;
					return debug;
				}
				
			}
		} catch (SQLException e)
		{
			throw new BOSException(e);
		}
    	
        return debug;
    }
}