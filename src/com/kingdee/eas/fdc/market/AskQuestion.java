package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class AskQuestion extends QuestionBase implements IAskQuestion
{
    public AskQuestion()
    {
        super();
        registerInterface(IAskQuestion.class, this);
    }
    public AskQuestion(Context ctx)
    {
        super(ctx);
        registerInterface(IAskQuestion.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("55D20F0E");
    }
    private AskQuestionController getController() throws BOSException
    {
        return (AskQuestionController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public AskQuestionInfo getAskQuestionInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAskQuestionInfo(getContext(), pk);
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
    public AskQuestionInfo getAskQuestionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAskQuestionInfo(getContext(), pk, selector);
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
    public AskQuestionInfo getAskQuestionInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAskQuestionInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public AskQuestionCollection getAskQuestionCollection() throws BOSException
    {
        try {
            return getController().getAskQuestionCollection(getContext());
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
    public AskQuestionCollection getAskQuestionCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAskQuestionCollection(getContext(), view);
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
    public AskQuestionCollection getAskQuestionCollection(String oql) throws BOSException
    {
        try {
            return getController().getAskQuestionCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}