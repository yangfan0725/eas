package com.kingdee.eas.fdc.invite.app;

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

import java.lang.String;
import com.kingdee.eas.framework.app.TreeBaseControllerBean;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.fdc.invite.TempletTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.invite.TempletTypeCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;

public class TempletTypeControllerBean extends AbstractTempletTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.TempletTypeControllerBean");
    protected boolean _updateRelateData(Context ctx, String oldID, String newID, Object[] tables)throws BOSException
    {
        return false;
    }
    protected Object[] _getRelateData(Context ctx, String id, String[] tables)throws BOSException
    {
        return new Object[0];
    }
}