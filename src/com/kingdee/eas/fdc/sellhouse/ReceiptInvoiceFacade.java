package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.fdc.sellhouse.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public class ReceiptInvoiceFacade extends AbstractBizCtrl implements IReceiptInvoiceFacade
{
    public ReceiptInvoiceFacade()
    {
        super();
        registerInterface(IReceiptInvoiceFacade.class, this);
    }
    public ReceiptInvoiceFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IReceiptInvoiceFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4B3B8E2A");
    }
    private ReceiptInvoiceFacadeController getController() throws BOSException
    {
        return (ReceiptInvoiceFacadeController)getBizController();
    }
    /**
     *ȡ�ò�����¼-User defined method
     *@param id �տ��Ʊ��id
     *@return
     */
    public IObjectCollection getRecord(BOSUuid id) throws BOSException
    {
        try {
            return getController().getRecord(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���²�����¼-User defined method
     *@param billType ����
     *@param pk pk
     *@param recordType ��������
     *@param content ����
     *@param description ��ע
     */
    public void updateRecord(int billType, String pk, RecordTypeEnum recordType, String content, String description) throws BOSException
    {
        try {
            getController().updateRecord(getContext(), billType, pk, recordType, content, description);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����վݣ������²�����¼-User defined method
     *@param pk pk
     *@param fdcPK fdcPK
     *@param receiptID �վ�ID
     *@param receiptNum �վݺ�
     *@param oldReceiptNum ԭ�վݺ�
     *@param description ��ע
     */
    public void updateReceipt(String pk, String fdcPK, String receiptID, String receiptNum, String oldReceiptNum, String description) throws BOSException
    {
        try {
            getController().updateReceipt(getContext(), pk, fdcPK, receiptID, receiptNum, oldReceiptNum, description);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����վݣ������¼�¼-User defined method
     *@param pk pk
     *@param fdcPK fdcPK
     *@param receiptNum �վݺ�
     *@param description ��ע
     *@param paraMap ����
     */
    public void retackReceipt(String pk, String fdcPK, String receiptNum, String description, Map paraMap) throws BOSException
    {
        try {
            getController().retackReceipt(getContext(), pk, fdcPK, receiptNum, description, paraMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���·�Ʊ�������²�����¼-User defined method
     *@param pk pk
     *@param oldInvoice ԭ��Ʊ��
     *@param chequePK Ʊ��PK
     *@param newInvoice �·�Ʊ��
     *@param description ��ע
     */
    public void updateInvoice(String pk, String oldInvoice, String chequePK, String newInvoice, String description) throws BOSException
    {
        try {
            getController().updateInvoice(getContext(), pk, oldInvoice, chequePK, newInvoice, description);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ͨ��������ȡ��Ʊ����Ϣ-User defined method
     *@param chgID ������ID
     *@return
     */
    public List getChequebyChgID(String chgID) throws BOSException
    {
        try {
            return getController().getChequebyChgID(getContext(), chgID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������Ʊ��-User defined method
     *@param param ��������
     */
    public void updateChequeForChg(List param) throws BOSException
    {
        try {
            getController().updateChequeForChg(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ͨ���˷����򻻷�ȡ��Ʊ����Ϣ-User defined method
     *@param sourceID pk
     *@param sourceType Դ������
     *@return
     */
    public List getChequeForRoom(String sourceID, String sourceType) throws BOSException
    {
        try {
            return getController().getChequeForRoom(getContext(), sourceID, sourceType);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�˷��򻻷�ʱ����Ʊ��-User defined method
     *@param param ��������
     */
    public void retakeChequeForRoom(List param) throws BOSException
    {
        try {
            getController().retakeChequeForRoom(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���շ�Ʊ-User defined method
     *@param pk pk
     *@param number ��Ʊ��
     *@param description ��ע
     */
    public void retackInvoice(String pk, String number, String description) throws BOSException
    {
        try {
            getController().retackInvoice(getContext(), pk, number, description);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�տ�����Ʊ-User defined method
     *@param pk pk
     *@param isClearAll �Ƿ��������ͬ��Ʊ���տ
     */
    public void clearInvoice(String pk, boolean isClearAll) throws BOSException
    {
        try {
            getController().clearInvoice(getContext(), pk, isClearAll);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ݷ���IDȡ�����Ϲ��������ͻ�-User defined method
     *@param roomID ����ID
     *@return
     */
    public FDCCustomerInfo getCustomerByRoom(String roomID) throws BOSException
    {
        try {
            return getController().getCustomerByRoom(getContext(), roomID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���������վݣ����Ҹ�������-User defined method
     *@param paramMap ����
     *@param fdcPK fdcPK
     *@param receiptNum �վݺ�
     *@param description ��ע
     */
    public void retackReceiptBatch(Map paramMap, String fdcPK, String receiptNum, String description) throws BOSException
    {
        try {
            getController().retackReceiptBatch(getContext(), paramMap, fdcPK, receiptNum, description);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}