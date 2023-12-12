package com.kingdee.eas.fdc.tenancy.app;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;


public class GetTenSqlResultFacadeControllerBean extends AbstractGetTenSqlResultFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.GetTenSqlResultFacadeControllerBean");

	@Override
	protected String _getSqlResult(Context ctx, String name)
			throws BOSException, EASBizException {
		JSONArray arrrs = new JSONArray();
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select * from "+name);
		IRowSet rs=builder.executeQuery();
		try {
			ResultSetMetaData md=rs.getMetaData();
			while(rs.next()){
				JSONObject obj = new JSONObject();
				for(int i=1;i<=md.getColumnCount();i++){
					obj.put(md.getColumnName(i), rs.getString(md.getColumnName(i)));
				}
				
				arrrs.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrrs.toString();
	}
    
    
}