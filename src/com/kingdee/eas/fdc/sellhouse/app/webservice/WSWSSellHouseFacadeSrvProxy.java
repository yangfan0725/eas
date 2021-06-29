package com.kingdee.eas.fdc.sellhouse.app.webservice;

import org.apache.axis.Message;

import org.apache.axis.MessageContext;

import org.apache.axis.message.SOAPEnvelope;

import org.apache.axis.message.SOAPHeaderElement;

import com.kingdee.bos.webservice.WSConfig;

import com.kingdee.bos.webservice.WSInvokeException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ObjectMultiPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.orm.core.ORMEngine;
import com.kingdee.bos.webservice.BeanConvertHelper;
import com.kingdee.bos.webservice.BOSTypeConvertor;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.webservice.WSConfig;
import com.kingdee.bos.webservice.MetaDataHelper;
import com.kingdee.bos.BOSObjectFactory;

public class WSWSSellHouseFacadeSrvProxy { 

    public String sysCustomerValid( String str ) throws WSInvokeException {
        try {
            return getController().sysCustomerValid(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String synTransaction( String str ) throws WSInvokeException {
        try {
            return getController().synTransaction(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String isOldCustomer( String str ) throws WSInvokeException {
        try {
            return getController().isOldCustomer(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String sysCustomer( String str ) throws WSInvokeException {
        try {
            return getController().sysCustomer(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    private com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade getController() {
        try {
        if (WSConfig.getRomoteLocate()!=null&&WSConfig.getRomoteLocate().equals("false")){
            Message message =MessageContext.getCurrentContext().getRequestMessage();
            SOAPEnvelope soap =message.getSOAPEnvelope();
            SOAPHeaderElement headerElement=soap.getHeaderByName(WSConfig.loginQName,WSConfig.loginSessionId);
            String SessionId=headerElement.getValue();
            return ( com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade )BOSObjectFactory.createBOSObject( SessionId , "com.kingdee.eas.fdc.sellhouse.WSSellHouseFacade") ; 
        } else {
            return ( com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade )BOSObjectFactory.createRemoteBOSObject( WSConfig.getSrvURL() , "com.kingdee.eas.fdc.sellhouse.WSSellHouseFacade" , com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade.class ) ; 
        }
        }
        catch( Throwable e ) {
            return ( com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade )ORMEngine.createRemoteObject( WSConfig.getSrvURL() , "com.kingdee.eas.fdc.sellhouse.WSSellHouseFacade" , com.kingdee.eas.fdc.sellhouse.IWSSellHouseFacade.class ) ; 
        }
    }

    private BeanConvertHelper getBeanConvertor() {
        return new BeanConvertHelper(); 
    }

}