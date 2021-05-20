package com.kingdee.eas.fdc.invite.supplier.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.invite.supplier.WinInfoCollection;
import com.kingdee.eas.fdc.invite.supplier.WinInfoInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface WinInfoController extends FDCBillController
{
    public WinInfoInfo getWinInfoInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public WinInfoInfo getWinInfoInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public WinInfoInfo getWinInfoInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public WinInfoCollection getWinInfoCollection(Context ctx) throws BOSException, RemoteException;
    public WinInfoCollection getWinInfoCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public WinInfoCollection getWinInfoCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void publish(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void unPublish(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
}