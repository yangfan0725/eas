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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.eas.fdc.market.app.*;

public class QuestionPaperAnswerEntry extends BillEntryBase implements IQuestionPaperAnswerEntry
{
    public QuestionPaperAnswerEntry()
    {
        super();
        registerInterface(IQuestionPaperAnswerEntry.class, this);
    }
    public QuestionPaperAnswerEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IQuestionPaperAnswerEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("63809CFF");
    }
    private QuestionPaperAnswerEntryController getController() throws BOSException
    {
        return (QuestionPaperAnswerEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public QuestionPaperAnswerEntryInfo getQuestionPaperAnswerEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperAnswerEntryInfo(getContext(), pk);
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
    public QuestionPaperAnswerEntryInfo getQuestionPaperAnswerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperAnswerEntryInfo(getContext(), pk, selector);
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
    public QuestionPaperAnswerEntryInfo getQuestionPaperAnswerEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperAnswerEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public QuestionPaperAnswerEntryCollection getQuestionPaperAnswerEntryCollection() throws BOSException
    {
        try {
            return getController().getQuestionPaperAnswerEntryCollection(getContext());
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
    public QuestionPaperAnswerEntryCollection getQuestionPaperAnswerEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuestionPaperAnswerEntryCollection(getContext(), view);
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
    public QuestionPaperAnswerEntryCollection getQuestionPaperAnswerEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuestionPaperAnswerEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}