package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface LinkmanEntryControllerRemoteHome extends EJBHome
{
    LinkmanEntryControllerRemote create() throws CreateException, RemoteException;
}