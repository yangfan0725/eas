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
import com.kingdee.eas.fdc.invite.supplier.TenderInfoCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.invite.supplier.TenderInfoInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TenderInfoController extends FDCBillController
{
    public TenderInfoInfo getTenderInfoInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TenderInfoInfo getTenderInfoInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TenderInfoInfo getTenderInfoInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TenderInfoCollection getTenderInfoCollection(Context ctx) throws BOSException, RemoteException;
    public TenderInfoCollection getTenderInfoCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TenderInfoCollection getTenderInfoCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void approveReport(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void rejectReport(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
}