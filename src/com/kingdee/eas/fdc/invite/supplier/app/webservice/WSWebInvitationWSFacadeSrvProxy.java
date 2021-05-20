package com.kingdee.eas.fdc.invite.supplier.app.webservice;

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

public class WSWebInvitationWSFacadeSrvProxy { 

    public String changePassword( String param ) throws WSInvokeException {
        try {
            return getController().changePassword(
            param);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String registerUser( String userParam ) throws WSInvokeException {
        try {
            return getController().registerUser(
            userParam);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public byte[] download( String downloadParam ) throws WSInvokeException {
        try {
            return getController().download(
            downloadParam);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String saveSupplier( String supplier ) throws WSInvokeException {
        try {
            return getController().saveSupplier(
            supplier);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String syncEASBasedata( String lastUpdateDate ) throws WSInvokeException {
        try {
            return getController().syncEASBasedata(
            BOSTypeConvertor.string2Date(lastUpdateDate));
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String apply( String applyParam ) throws WSInvokeException {
        try {
            return getController().apply(
            applyParam);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    private com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade getController() {
        try {
        if (WSConfig.getRomoteLocate()!=null&&WSConfig.getRomoteLocate().equals("false")){
            Message message =MessageContext.getCurrentContext().getRequestMessage();
            SOAPEnvelope soap =message.getSOAPEnvelope();
            SOAPHeaderElement headerElement=soap.getHeaderByName(WSConfig.loginQName,WSConfig.loginSessionId);
            String SessionId=headerElement.getValue();
            return ( com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade )BOSObjectFactory.createBOSObject( SessionId , "com.kingdee.eas.fdc.invite.supplier.WebInvitationWSFacade") ; 
        } else {
            return ( com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade )BOSObjectFactory.createRemoteBOSObject( WSConfig.getSrvURL() , "com.kingdee.eas.fdc.invite.supplier.WebInvitationWSFacade" , com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade.class ) ; 
        }
        }
        catch( Throwable e ) {
            return ( com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade )ORMEngine.createRemoteObject( WSConfig.getSrvURL() , "com.kingdee.eas.fdc.invite.supplier.WebInvitationWSFacade" , com.kingdee.eas.fdc.invite.supplier.IWebInvitationWSFacade.class ) ; 
        }
    }

    private BeanConvertHelper getBeanConvertor() {
        return new BeanConvertHelper(); 
    }

}