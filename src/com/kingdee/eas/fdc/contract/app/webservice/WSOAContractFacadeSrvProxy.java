package com.kingdee.eas.fdc.contract.app.webservice;

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

public class WSOAContractFacadeSrvProxy { 

    public String ifHasAttachFile( String str ) throws WSInvokeException {
        try {
            return getController().ifHasAttachFile(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String auditMarketProject( String str ) throws WSInvokeException {
        try {
            return getController().auditMarketProject(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String deleteSupplierApply( String str ) throws WSInvokeException {
        try {
            return getController().deleteSupplierApply(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String submitContractBill( String str ) throws WSInvokeException {
        try {
            return getController().submitContractBill(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String auditContractBill( String str ) throws WSInvokeException {
        try {
            return getController().auditContractBill(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String auditPayRequestBill( String str ) throws WSInvokeException {
        try {
            return getController().auditPayRequestBill(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String deleteContractBill( String str ) throws WSInvokeException {
        try {
            return getController().deleteContractBill(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String auditContractChangeSettleBill( String str ) throws WSInvokeException {
        try {
            return getController().auditContractChangeSettleBill(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String deleteContractwithouttext( String str ) throws WSInvokeException {
        try {
            return getController().deleteContractwithouttext(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String saveSupplierApply( String str ) throws WSInvokeException {
        try {
            return getController().saveSupplierApply(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String submitContractwithouttext( String str ) throws WSInvokeException {
        try {
            return getController().submitContractwithouttext(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String auditContractwithouttext( String str ) throws WSInvokeException {
        try {
            return getController().auditContractwithouttext(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String saveContractwithouttext( String str ) throws WSInvokeException {
        try {
            return getController().saveContractwithouttext(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String unAuditBill( String str ) throws WSInvokeException {
        try {
            return getController().unAuditBill(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String auditChangeAuditBill( String str ) throws WSInvokeException {
        try {
            return getController().auditChangeAuditBill(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String acceptHandle( String str ) throws WSInvokeException {
        try {
            return getController().acceptHandle(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String auditSupplierApply( String str ) throws WSInvokeException {
        try {
            return getController().auditSupplierApply(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    public String saveContractBill( String str ) throws WSInvokeException {
        try {
            return getController().saveContractBill(
            str);
        }
        catch( Throwable e ) {
            throw new WSInvokeException( e ) ;
        }
    }

    private com.kingdee.eas.fdc.contract.IOAContractFacade getController() {
        try {
        if (WSConfig.getRomoteLocate()!=null&&WSConfig.getRomoteLocate().equals("false")){
            Message message =MessageContext.getCurrentContext().getRequestMessage();
            SOAPEnvelope soap =message.getSOAPEnvelope();
            SOAPHeaderElement headerElement=soap.getHeaderByName(WSConfig.loginQName,WSConfig.loginSessionId);
            String SessionId=headerElement.getValue();
            return ( com.kingdee.eas.fdc.contract.IOAContractFacade )BOSObjectFactory.createBOSObject( SessionId , "com.kingdee.eas.fdc.contract.OAContractFacade") ; 
        } else {
            return ( com.kingdee.eas.fdc.contract.IOAContractFacade )BOSObjectFactory.createRemoteBOSObject( WSConfig.getSrvURL() , "com.kingdee.eas.fdc.contract.OAContractFacade" , com.kingdee.eas.fdc.contract.IOAContractFacade.class ) ; 
        }
        }
        catch( Throwable e ) {
            return ( com.kingdee.eas.fdc.contract.IOAContractFacade )ORMEngine.createRemoteObject( WSConfig.getSrvURL() , "com.kingdee.eas.fdc.contract.OAContractFacade" , com.kingdee.eas.fdc.contract.IOAContractFacade.class ) ; 
        }
    }

    private BeanConvertHelper getBeanConvertor() {
        return new BeanConvertHelper(); 
    }

}