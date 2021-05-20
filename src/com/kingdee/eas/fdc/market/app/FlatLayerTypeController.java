package com.kingdee.eas.fdc.market.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.market.FlatLayerTypeCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.FlatLayerTypeInfo;
import com.kingdee.eas.fdc.propertymgmt.app.PPMDataBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FlatLayerTypeController extends PPMDataBaseController
{
    public FlatLayerTypeInfo getFlatLayerTypeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FlatLayerTypeInfo getFlatLayerTypeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FlatLayerTypeInfo getFlatLayerTypeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FlatLayerTypeCollection getFlatLayerTypeCollection(Context ctx) throws BOSException, RemoteException;
    public FlatLayerTypeCollection getFlatLayerTypeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FlatLayerTypeCollection getFlatLayerTypeCollection(Context ctx, String oql) throws BOSException, RemoteException;
}