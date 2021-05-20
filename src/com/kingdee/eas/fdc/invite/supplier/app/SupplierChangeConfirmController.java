package com.kingdee.eas.fdc.invite.supplier.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmInfo;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SupplierChangeConfirmController extends FDCBillController
{
    public SupplierChangeConfirmInfo getSupplierChangeConfirmInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SupplierChangeConfirmInfo getSupplierChangeConfirmInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SupplierChangeConfirmInfo getSupplierChangeConfirmInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SupplierChangeConfirmCollection getSupplierChangeConfirmCollection(Context ctx) throws BOSException, RemoteException;
    public SupplierChangeConfirmCollection getSupplierChangeConfirmCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SupplierChangeConfirmCollection getSupplierChangeConfirmCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void changeSupplierInfo(Context ctx, BOSUuid billID) throws BOSException, EASBizException, RemoteException;
}