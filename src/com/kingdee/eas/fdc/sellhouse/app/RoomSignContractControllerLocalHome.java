package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RoomSignContractControllerLocalHome extends EJBLocalHome
{
    RoomSignContractControllerLocal create() throws CreateException;
}