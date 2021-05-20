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
import com.kingdee.eas.fdc.invite.supplier.ChooseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.supplier.ChooseInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ChooseController extends FDCDataBaseController
{
    public ChooseInfo getChooseInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ChooseInfo getChooseInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ChooseInfo getChooseInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ChooseCollection getChooseCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public ChooseCollection getChooseCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ChooseCollection getChooseCollection(Context ctx) throws BOSException, RemoteException;
    public void IsNdelete(Context ctx, String areaName) throws BOSException, EASBizException, RemoteException;
}