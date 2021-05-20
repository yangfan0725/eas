package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.app.FDCSplitBillEntryController;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ConChangeSplitEntryController extends FDCSplitBillEntryController
{
    public ConChangeSplitEntryInfo getConChangeSplitEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ConChangeSplitEntryInfo getConChangeSplitEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ConChangeSplitEntryInfo getConChangeSplitEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ConChangeSplitEntryCollection getConChangeSplitEntryCollection(Context ctx) throws BOSException, RemoteException;
    public ConChangeSplitEntryCollection getConChangeSplitEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ConChangeSplitEntryCollection getConChangeSplitEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}