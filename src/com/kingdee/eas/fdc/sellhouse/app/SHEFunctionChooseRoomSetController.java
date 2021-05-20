package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.SHEFunctionChooseRoomSetInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.SHEFunctionChooseRoomSetCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.CoreBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SHEFunctionChooseRoomSetController extends CoreBaseController
{
    public SHEFunctionChooseRoomSetInfo getSHEFunctionChooseRoomSetInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SHEFunctionChooseRoomSetInfo getSHEFunctionChooseRoomSetInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SHEFunctionChooseRoomSetInfo getSHEFunctionChooseRoomSetInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SHEFunctionChooseRoomSetCollection getSHEFunctionChooseRoomSetCollection(Context ctx) throws BOSException, RemoteException;
    public SHEFunctionChooseRoomSetCollection getSHEFunctionChooseRoomSetCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SHEFunctionChooseRoomSetCollection getSHEFunctionChooseRoomSetCollection(Context ctx, String oql) throws BOSException, RemoteException;
}