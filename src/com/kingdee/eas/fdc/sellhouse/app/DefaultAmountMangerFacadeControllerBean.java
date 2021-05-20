package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
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
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;

import com.kingdee.jdbc.rowset.IRowSet;
import java.util.Set;

/***
 * Ƿ��ҵ����Ϣ�״�
 * */
public class DefaultAmountMangerFacadeControllerBean extends AbstractDefaultAmountMangerFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.DefaultAmountMangerFacadeControllerBean");
  
    protected IRowSet _getPrintData(Context ctx, Set idSet)throws BOSException
    {   
    	FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
    	 sqlBuilder.appendSql(" ");
         sqlBuilder.appendParam(" and  ", idSet.toArray());
         logger.debug(sqlBuilder.getTestSql());
         return sqlBuilder.executeQuery();
    }
}