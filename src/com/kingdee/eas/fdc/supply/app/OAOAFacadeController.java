package com.kingdee.eas.fdc.supply.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.xml.rpc.ServiceException;

import com.kingdee.bos.framework.ejb.BizController;

public interface OAOAFacadeController extends BizController
{
    public void pushMessageTOOA(Context ctx) throws BOSException, RemoteException, SQLException, MalformedURLException, ServiceException;
}