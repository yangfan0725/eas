package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class DrawingDepth extends FDCDataBase implements IDrawingDepth
{
    public DrawingDepth()
    {
        super();
        registerInterface(IDrawingDepth.class, this);
    }
    public DrawingDepth(Context ctx)
    {
        super(ctx);
        registerInterface(IDrawingDepth.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DBE77669");
    }
    private DrawingDepthController getController() throws BOSException
    {
        return (DrawingDepthController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public DrawingDepthInfo getDrawingDepthInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDrawingDepthInfo(getContext(), pk);
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
    public DrawingDepthInfo getDrawingDepthInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDrawingDepthInfo(getContext(), pk, selector);
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
    public DrawingDepthInfo getDrawingDepthInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDrawingDepthInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public DrawingDepthCollection getDrawingDepthCollection() throws BOSException
    {
        try {
            return getController().getDrawingDepthCollection(getContext());
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
    public DrawingDepthCollection getDrawingDepthCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDrawingDepthCollection(getContext(), view);
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
    public DrawingDepthCollection getDrawingDepthCollection(String oql) throws BOSException
    {
        try {
            return getController().getDrawingDepthCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ϸ�ڵ��ɷ���ϸ�ڵ�ʱ,Ӧ��������ݸ���Ϊ�Լ���ϸ�ڵ��ID-User defined method
     *@param oldID oldID
     *@param newID newID
     *@param tables tables
     *@return
     */
    public boolean updateRelateData(String oldID, String newID, Object[] tables) throws BOSException
    {
        try {
            return getController().updateRelateData(getContext(), oldID, newID, tables);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�õ��й��������ݵı�-User defined method
     *@param id id
     *@param tables tables
     *@return
     */
    public Object[] getRelateData(String id, String[] tables) throws BOSException
    {
        try {
            return getController().getRelateData(getContext(), id, tables);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}