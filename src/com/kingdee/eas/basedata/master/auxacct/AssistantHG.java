package com.kingdee.eas.basedata.master.auxacct;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ObjectBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.basedata.master.auxacct.app.*;
import com.kingdee.eas.framework.IObjectBase;

public class AssistantHG extends ObjectBase implements IAssistantHG
{
    public AssistantHG()
    {
        super();
        registerInterface(IAssistantHG.class, this);
    }
    public AssistantHG(Context ctx)
    {
        super(ctx);
        registerInterface(IAssistantHG.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("418A6CBB");
    }
    private AssistantHGController getController() throws BOSException
    {
        return (AssistantHGController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public AssistantHGCollection getAssistantHGCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAssistantHGCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public AssistantHGCollection getAssistantHGCollection() throws BOSException
    {
        try {
            return getController().getAssistantHGCollection(getContext());
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
    public AssistantHGCollection getAssistantHGCollection(String oql) throws BOSException
    {
        try {
            return getController().getAssistantHGCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ����-User defined method
     *@param assGrp �����ҵĸ����]
     *@param bookId bookId
     *@param items items
     *@return
     */
    public AssistantHGInfo getAssistantHG(AssistantHGInfo assGrp, String bookId, AsstActTypeCollection items) throws BOSException, EASBizException
    {
        try {
            return getController().getAssistantHG(getContext(), assGrp, bookId, items);
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
    public AssistantHGInfo getAssistantHGInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAssistantHGInfo(getContext(), pk);
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
    public AssistantHGInfo getAssistantHGInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAssistantHGInfo(getContext(), pk, selector);
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
    public AssistantHGInfo getAssistantHGInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAssistantHGInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ����-User defined method
     *@param asstHGPK asstHGPK
     *@param bookId bookId
     *@param items items
     *@return
     */
    public IObjectValue getAssistantHG(IObjectPK asstHGPK, String bookId, IObjectPK[] items) throws BOSException, EASBizException
    {
        try {
            return getController().getAssistantHG(getContext(), asstHGPK, bookId, items);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}