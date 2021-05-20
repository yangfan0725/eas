package com.kingdee.eas.fdc.sellhouse.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.CalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomOrderStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class PushManageControllerBean extends AbstractPushManageControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.PushManageControllerBean");
    
	/**
	 * 重写校验:
	 * 1.不检查编码名称是否为空。 2.不检查编码名称是否重复。
	 */
	protected void addnewCheck(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
	}
	
	protected void updateCheck(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
	}


	protected void _executed(Context ctx, Set pushRoomIds) throws BOSException,EASBizException {
		if(pushRoomIds == null  ||  pushRoomIds.isEmpty()){
			return;
		}
		StringBuffer roomIdFilter = new StringBuffer("(");		
		int counter = 0;
		for(Iterator itor = pushRoomIds.iterator(); itor.hasNext(); ){
			String roomId = (String) itor.next();
			if(counter != 0){
				roomIdFilter.append(",");
			}
			roomIdFilter.append("'");
			roomIdFilter.append(roomId);
			roomIdFilter.append("'");
			counter++;
		}
		roomIdFilter.append(")");
		
		String orderStateSql = "update T_SHE_SellOrderRoomEntry set FState=?, FQuitOrderDate=now() where froomid in " + roomIdFilter;
		DbUtil.execute(ctx, orderStateSql, new Object[] {RoomOrderStateEnum.EXECUTED_VALUE});
		
		String roomSql = "update t_she_room set fsellstate=?,FSellOrderId=? ,FIsPush=? where fid in " + roomIdFilter+" and fsellstate!='KeepDown'";		
		DbUtil.execute(ctx, roomSql, new Object[] {RoomSellStateEnum.ONSHOW_VALUE,"",Boolean.TRUE});
		
		String roomkeepDownSql = "update t_she_room set FSellOrderId=? ,FIsPush=? where fid in " + roomIdFilter+" and fsellstate='KeepDown'";		
		DbUtil.execute(ctx, roomkeepDownSql, new Object[] {"",Boolean.TRUE});
		
		
//		JSONArray arr=new JSONArray();
//		for(Iterator itor = pushRoomIds.iterator(); itor.hasNext(); ){
//			JSONObject jo=new JSONObject();
//			jo.put("id", itor.next().toString());
//			jo.put("status", "待售");
//			
//			jo.put("cjdj", "");
//			jo.put("cjzj", "");
//			
//			jo.put("bzj", "");
//			jo.put("bdj", "");
//			
//			arr.add(jo);
//		}
//		try {
//			String rs=SHEManageHelper.execPost(ctx, "sap_room_status_channel", arr.toJSONString());
//			JSONObject rso = JSONObject.parseObject(rs);
//			if(!"SUCCESS".equals(rso.getString("status"))){
//				throw new EASBizException(new NumericExceptionSubItem("100",rso.getString("message")));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	protected void _quitOrder(Context ctx, Set quitRoomIds) throws BOSException,EASBizException {
		if(quitRoomIds == null  ||  quitRoomIds.isEmpty()){
			return;
		}
		StringBuffer roomIdFilter = new StringBuffer("(");		
		int counter = 0;
		for(Iterator itor = quitRoomIds.iterator(); itor.hasNext(); ){
			String roomId = (String) itor.next();
			if(counter != 0){
				roomIdFilter.append(",");
			}
			roomIdFilter.append("'");
			roomIdFilter.append(roomId);
			roomIdFilter.append("'");
			counter++;
		}
		roomIdFilter.append(")");
		
		String orderStateSql = "update T_SHE_SellOrderRoomEntry set FState=?, FQuitOrderDate=now() where froomid in " + roomIdFilter;
		DbUtil.execute(ctx, orderStateSql, new Object[] {RoomOrderStateEnum.QUITORDER_VALUE});
		
		String roomSql = "update t_she_room set fsellstate=?,FSellOrderId=? ,FIsPush=? where fid in " + roomIdFilter;		
		DbUtil.execute(ctx, roomSql, new Object[] {RoomSellStateEnum.INIT_VALUE, "",Boolean.FALSE});
		
//		JSONArray arr=new JSONArray();
//		for(Iterator itor = quitRoomIds.iterator(); itor.hasNext(); ){
//			JSONObject jo=new JSONObject();
//			jo.put("id", itor.next().toString());
//			jo.put("status", "未推盘");
//			
//			jo.put("cjdj", "");
//			jo.put("cjzj", "");
//			
//			jo.put("bzj", "");
//			jo.put("bdj", "");
//			
//			arr.add(jo);
//		}
//		try {
//			String rs=SHEManageHelper.execPost(ctx, "sap_room_status_channel", arr.toJSONString());
//			JSONObject rso = JSONObject.parseObject(rs);
//			if(!"SUCCESS".equals(rso.getString("status"))){
//				throw new EASBizException(new NumericExceptionSubItem("100",rso.getString("message")));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
}