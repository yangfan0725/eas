package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListingPageControllerLocalHome extends EJBLocalHome
{
    NewListingPageControllerLocal create() throws CreateException;
}