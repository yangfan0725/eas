package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface QuotingPriceSetControllerLocalHome extends EJBLocalHome
{
    QuotingPriceSetControllerLocal create() throws CreateException;
}