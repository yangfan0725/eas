package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;
import com.kingdee.eas.fdc.market.app.*;

public class Media extends FDCDataBase implements IMedia
{
    public Media()
    {
        super();
        registerInterface(IMedia.class, this);
    }
    public Media(Context ctx)
    {
        super(ctx);
        registerInterface(IMedia.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FC7A04B3");
    }
    private MediaController getController() throws BOSException
    {
        return (MediaController)getBizController();
    }
    /**
     *����-System defined method
     *@param pk ����
     *@return
     */
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-System defined method
     *@param filter ����
     *@return
     */
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-System defined method
     *@param oql ����
     *@return
     */
    public boolean exists(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), oql);
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
    public MediaInfo getMediaInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMediaInfo(getContext(), pk);
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
    public MediaInfo getMediaInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMediaInfo(getContext(), pk, selector);
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
    public MediaInfo getMediaInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMediaInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-System defined method
     *@param model ����
     *@return
     */
    public IObjectPK addnew(MediaInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().addnew(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-System defined method
     *@param pk ����
     *@param model ����
     */
    public void addnew(IObjectPK pk, MediaInfo model) throws BOSException, EASBizException
    {
        try {
            getController().addnew(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�޸�-System defined method
     *@param pk ����
     *@param model ����
     */
    public void update(IObjectPK pk, MediaInfo model) throws BOSException, EASBizException
    {
        try {
            getController().update(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ֲ�����-System defined method
     *@param model �ֲ�����
     *@param selector �ֲ�����
     */
    public void updatePartial(MediaInfo model, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            getController().updatePartial(getContext(), model, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�޸�-System defined method
     *@param pk ���´����
     *@param model ���´����
     */
    public void updateBigObject(IObjectPK pk, MediaInfo model) throws BOSException
    {
        try {
            getController().updateBigObject(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ��-System defined method
     *@param pk ɾ��
     */
    public void delete(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public IObjectPK[] getPKList() throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext());
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
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param filter ȡ����
     *@param sorter ȡ����
     *@return
     */
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), filter, sorter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public MediaCollection getMediaCollection() throws BOSException
    {
        try {
            return getController().getMediaCollection(getContext());
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
    public MediaCollection getMediaCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMediaCollection(getContext(), view);
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
    public MediaCollection getMediaCollection(String oql) throws BOSException
    {
        try {
            return getController().getMediaCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ��-System defined method
     *@param filter ɾ��
     *@return
     */
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ��-System defined method
     *@param oql ɾ��
     *@return
     */
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ��-System defined method
     *@param arrayPK ɾ��
     */
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), arrayPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���õ���ԭ��-User defined method
     *@param ctPK ctPK
     *@return
     */
    public boolean enabled(IObjectPK ctPK) throws BOSException, EASBizException
    {
        try {
            return getController().enabled(getContext(), ctPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���õ���ԭ��-User defined method
     *@param ctPK ctPK
     *@return
     */
    public boolean disEnabled(IObjectPK ctPK) throws BOSException, EASBizException
    {
        try {
            return getController().disEnabled(getContext(), ctPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}