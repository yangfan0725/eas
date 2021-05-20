package com.kingdee.eas.fdc.invite.supplier.app.webservice;

import java.sql.Timestamp;

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
import com.kingdee.bos.webservice.login.EASLoginProxy;
import com.kingdee.bos.BOSObjectFactory;

public class WSSupplierBpmAuditFacadeSrvProxy { 

    public String getBillInfo( String strBSID , String strBOID ) throws WSInvokeException {
        try {
            return getController().getBillInfo(
            strBSID,
            strBOID);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String rework( String strBSID , String strBOID , int iProcInstID , String strStepName , String strApproverId , int ieAction , String strComment , String dtTime ) throws WSInvokeException {
        try {
            return getController().rework(
            strBSID,
            strBOID,
            iProcInstID,
            strStepName,
            strApproverId,
            ieAction,
            strComment,
            BOSTypeConvertor.string2TimeStamp(dtTime));
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String approveClose( String strBSID , String strBOID , int iProcInstID , String strStepName , int eProcessInstanceResult , String strApproverId , String strComment , String dtTime ) throws WSInvokeException {
        try {
            return getController().approveClose(
            strBSID,
            strBOID,
            iProcInstID,
            strStepName,
            eProcessInstanceResult,
            strApproverId,
            strComment,
            BOSTypeConvertor.string2TimeStamp(dtTime));
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String createResult( String strBSID , String strBOID , boolean bSuccess , int iProcInstID , String strMessage ) throws WSInvokeException {
        try {
            return getController().createResult(
            strBSID,
            strBOID,
            bSuccess,
            iProcInstID,
            strMessage);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    private com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade getController() {
    	EASLoginProxy proxy=new EASLoginProxy();
    	proxy.login("wangzhixiong", "1", "eas", "new2030th", "L2", 2);
        try {
        if (WSConfig.getRomoteLocate()!=null&&WSConfig.getRomoteLocate().equals("false")){
            Message message =MessageContext.getCurrentContext().getRequestMessage();
            SOAPEnvelope soap =message.getSOAPEnvelope();
            SOAPHeaderElement headerElement=soap.getHeaderByName(WSConfig.loginQName,WSConfig.loginSessionId);
            String SessionId=headerElement.getValue();
            return ( com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade )BOSObjectFactory.createBOSObject( SessionId , "com.kingdee.eas.fdc.invite.supplier.SupplierBpmAuditFacade") ; 
        } else {
            return ( com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade )BOSObjectFactory.createRemoteBOSObject( WSConfig.getSrvURL() , "com.kingdee.eas.fdc.invite.supplier.SupplierBpmAuditFacade" , com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade.class ) ; 
        }
        }
        catch( Throwable e ) {
            return ( com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade )ORMEngine.createRemoteObject( WSConfig.getSrvURL() , "com.kingdee.eas.fdc.invite.supplier.SupplierBpmAuditFacade" , com.kingdee.eas.fdc.invite.supplier.ISupplierBpmAuditFacade.class ) ; 
        }
    }

    private BeanConvertHelper getBeanConvertor() {
        return new BeanConvertHelper(); 
    }

}