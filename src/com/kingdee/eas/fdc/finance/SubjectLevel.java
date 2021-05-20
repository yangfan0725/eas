package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class SubjectLevel extends DataBase implements ISubjectLevel
{
    public SubjectLevel()
    {
        super();
        registerInterface(ISubjectLevel.class, this);
    }
    public SubjectLevel(Context ctx)
    {
        super(ctx);
        registerInterface(ISubjectLevel.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("596E1847");
    }
    private SubjectLevelController getController() throws BOSException
    {
        return (SubjectLevelController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public SubjectLevelInfo getSubjectLevelInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSubjectLevelInfo(getContext(), pk);
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
    public SubjectLevelInfo getSubjectLevelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSubjectLevelInfo(getContext(), pk, selector);
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
    public SubjectLevelInfo getSubjectLevelInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSubjectLevelInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SubjectLevelCollection getSubjectLevelCollection() throws BOSException
    {
        try {
            return getController().getSubjectLevelCollection(getContext());
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
    public SubjectLevelCollection getSubjectLevelCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSubjectLevelCollection(getContext(), view);
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
    public SubjectLevelCollection getSubjectLevelCollection(String oql) throws BOSException
    {
        try {
            return getController().getSubjectLevelCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ�÷�¼ѡ���ĿID����-User defined method
     *@param pk ����
     *@return
     */
    public Map getSelectedIDS(String pk) throws BOSException
    {
        try {
            return getController().getSelectedIDS(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}