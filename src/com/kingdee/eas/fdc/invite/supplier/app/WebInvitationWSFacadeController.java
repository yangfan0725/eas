package com.kingdee.eas.fdc.invite.supplier.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.Date;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface WebInvitationWSFacadeController extends BizController
{
    public String syncEASBasedata(Context ctx, Date lastUpdateDate) throws BOSException, EASBizException, RemoteException;
    public String registerUser(Context ctx, String userParam) throws BOSException, EASBizException, RemoteException;
    public String apply(Context ctx, String applyParam) throws BOSException, EASBizException, RemoteException;
    public byte[] download(Context ctx, String downloadParam) throws BOSException, EASBizException, RemoteException;
    public String saveSupplier(Context ctx, String supplier) throws BOSException, EASBizException, RemoteException;
    public String changePassword(Context ctx, String param) throws BOSException, EASBizException, RemoteException;
}