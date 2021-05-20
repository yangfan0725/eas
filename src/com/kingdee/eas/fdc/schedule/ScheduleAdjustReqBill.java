package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ScheduleAdjustReqBill extends FDCBill implements IScheduleAdjustReqBill
{
    public ScheduleAdjustReqBill()
    {
        super();
        registerInterface(IScheduleAdjustReqBill.class, this);
    }
    public ScheduleAdjustReqBill(Context ctx)
    {
        super(ctx);
        registerInterface(IScheduleAdjustReqBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B955E3C9");
    }
    private ScheduleAdjustReqBillController getController() throws BOSException
    {
        return (ScheduleAdjustReqBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ScheduleAdjustReqBillInfo getScheduleAdjustReqBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleAdjustReqBillInfo(getContext(), pk);
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
    public ScheduleAdjustReqBillInfo getScheduleAdjustReqBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleAdjustReqBillInfo(getContext(), pk, selector);
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
    public ScheduleAdjustReqBillInfo getScheduleAdjustReqBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleAdjustReqBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ScheduleAdjustReqBillCollection getScheduleAdjustReqBillCollection() throws BOSException
    {
        try {
            return getController().getScheduleAdjustReqBillCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public ScheduleAdjustReqBillCollection getScheduleAdjustReqBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getScheduleAdjustReqBillCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public ScheduleAdjustReqBillCollection getScheduleAdjustReqBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getScheduleAdjustReqBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����°汾����-User defined method
     *@param model ������
     */
    public void createNewVerData(ScheduleAdjustReqBillInfo model) throws BOSException, EASBizException
    {
        try {
            getController().createNewVerData(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�༭����ȡֵ-User defined method
     *@param pk ������PK
     *@param selector ѡ����
     *@return
     */
    public ScheduleAdjustReqBillInfo getValue2(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getValue2(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ������ʱ����-User defined method
     *@param param ����
     *@return
     */
    public ScheduleAdjustReqBillInfo getNewData(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getNewData(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}