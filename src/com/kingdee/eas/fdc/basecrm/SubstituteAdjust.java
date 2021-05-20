package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.eas.fdc.sellhouse.CollectionInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.ICoreBillBase;

public class SubstituteAdjust extends CoreBillBase implements ISubstituteAdjust
{
    public SubstituteAdjust()
    {
        super();
        registerInterface(ISubstituteAdjust.class, this);
    }
    public SubstituteAdjust(Context ctx)
    {
        super(ctx);
        registerInterface(ISubstituteAdjust.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F552D025");
    }
    private SubstituteAdjustController getController() throws BOSException
    {
        return (SubstituteAdjustController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SubstituteAdjustCollection getSubstituteAdjustCollection() throws BOSException
    {
        try {
            return getController().getSubstituteAdjustCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public SubstituteAdjustCollection getSubstituteAdjustCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSubstituteAdjustCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public SubstituteAdjustCollection getSubstituteAdjustCollection(String oql) throws BOSException
    {
        try {
            return getController().getSubstituteAdjustCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public SubstituteAdjustInfo getSubstituteAdjustInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSubstituteAdjustInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public SubstituteAdjustInfo getSubstituteAdjustInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSubstituteAdjustInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public SubstituteAdjustInfo getSubstituteAdjustInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSubstituteAdjustInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param adjustInfo ��������ϸ����
     */
    public void transfTo(SubstituteAdjustInfo adjustInfo) throws BOSException, EASBizException
    {
        try {
            getController().transfTo(getContext(), adjustInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ü�����-User defined method
     *@param moneyDefineId ��������id
     *@param sellPorjectId ��Ŀid
     *@param buildingId ¥��id
     *@param collInfo ���շ�������
     *@return
     */
    public SubstituteAdjustEntryCollection getCalculateResult(BOSUuid moneyDefineId, BOSUuid sellPorjectId, BOSUuid buildingId, CollectionInfo collInfo) throws BOSException, EASBizException
    {
        try {
            return getController().getCalculateResult(getContext(), moneyDefineId, sellPorjectId, buildingId, collInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}