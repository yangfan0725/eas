package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.bos.framework.*;

public class TenancyModification extends TenBillBase implements ITenancyModification
{
    public TenancyModification()
    {
        super();
        registerInterface(ITenancyModification.class, this);
    }
    public TenancyModification(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyModification.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4E1D0473");
    }
    private TenancyModificationController getController() throws BOSException
    {
        return (TenancyModificationController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TenancyModificationInfo getTenancyModificationInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyModificationInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public TenancyModificationInfo getTenancyModificationInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyModificationInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public TenancyModificationInfo getTenancyModificationInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyModificationInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *get-System defined method
     *@return
     */
    public TenancyModificationCollection getTenancyModificationCollection() throws BOSException
    {
        try {
            return getController().getTenancyModificationCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *get-System defined method
     *@param view view
     *@return
     */
    public TenancyModificationCollection getTenancyModificationCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenancyModificationCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *get-System defined method
     *@param oql oql
     *@return
     */
    public TenancyModificationCollection getTenancyModificationCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenancyModificationCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ĵ������ڱ������Ѿ��տ�����һ������(����������)֮��. -User defined method
     *@param tenBillId ��ͬ����
     *@param incNewDate ��������
     *@return
     */
    public boolean incNewAddCheck(String tenBillId, Date incNewDate) throws BOSException
    {
        try {
            return getController().incNewAddCheck(getContext(), tenBillId, incNewDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��֤�Ѿ��տ�����ڶ�Ӧ�ĵ����в����޸ĺ�ɾ��-User defined method
     *@param tenBillId ��ͬ����
     *@param incNewDate ��������
     *@return
     */
    public boolean incNewEditCheck(String tenBillId, Date incNewDate) throws BOSException
    {
        try {
            return getController().incNewEditCheck(getContext(), tenBillId, incNewDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����е���������������Ѿ��տ�����һ������֮��.-User defined method
     *@param tenBillId ��ͬ����
     *@param stratDate ���⿪ʼ����
     *@param endDate �����������
     *@return
     */
    public boolean freesNewAddCheck(String tenBillId, Date stratDate, Date endDate) throws BOSException
    {
        try {
            return getController().freesNewAddCheck(getContext(), tenBillId, stratDate, endDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ж�Ӧ������֮��(����������)�Ѿ��տ��˾Ͳ����޸�ɾ��-User defined method
     *@param tenBillId ��ͬ����
     *@param startDate ���⿪ʼ����
     *@param endDate �����������
     *@return
     */
    public boolean freesNewEditCheck(String tenBillId, Date startDate, Date endDate) throws BOSException
    {
        try {
            return getController().freesNewEditCheck(getContext(), tenBillId, startDate, endDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������޺�ͬ����ȡ��ͬ�����¼�ĸ�����ϸ����������еĽ�������-User defined method
     *@param tenBillID ��ͬ����
     *@return
     */
    public Date getLeastPaidDate(String tenBillID) throws BOSException
    {
        try {
            return getController().getLeastPaidDate(getContext(), tenBillID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}