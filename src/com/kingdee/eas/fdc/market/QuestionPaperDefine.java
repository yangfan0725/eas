package com.kingdee.eas.fdc.market;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillBase;
import com.kingdee.eas.fdc.market.app.*;

public class QuestionPaperDefine extends BillBase implements IQuestionPaperDefine
{
    public QuestionPaperDefine()
    {
        super();
        registerInterface(IQuestionPaperDefine.class, this);
    }
    public QuestionPaperDefine(Context ctx)
    {
        super(ctx);
        registerInterface(IQuestionPaperDefine.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("82FA1D30");
    }
    private QuestionPaperDefineController getController() throws BOSException
    {
        return (QuestionPaperDefineController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public QuestionPaperDefineInfo getQuestionPaperDefineInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperDefineInfo(getContext(), pk);
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
    public QuestionPaperDefineInfo getQuestionPaperDefineInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperDefineInfo(getContext(), pk, selector);
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
    public QuestionPaperDefineInfo getQuestionPaperDefineInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperDefineInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public QuestionPaperDefineCollection getQuestionPaperDefineCollection() throws BOSException
    {
        try {
            return getController().getQuestionPaperDefineCollection(getContext());
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
    public QuestionPaperDefineCollection getQuestionPaperDefineCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuestionPaperDefineCollection(getContext(), view);
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
    public QuestionPaperDefineCollection getQuestionPaperDefineCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuestionPaperDefineCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������Ŀ-User defined method
     *@param model model
     */
    public void action_NewSubject(QuestionPaperDefineInfo model) throws BOSException
    {
        try {
            getController().action_NewSubject(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ����Ŀ-User defined method
     *@param model model
     */
    public void action_DeleteSubject(QuestionPaperDefineInfo model) throws BOSException
    {
        try {
            getController().action_DeleteSubject(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ѡ��-User defined method
     *@param model model
     */
    public void action_SelectSubject(QuestionPaperDefineInfo model) throws BOSException
    {
        try {
            getController().action_SelectSubject(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������Ŀ-User defined method
     *@param model model
     */
    public void action_UpdateSubject(QuestionPaperDefineInfo model) throws BOSException
    {
        try {
            getController().action_UpdateSubject(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}