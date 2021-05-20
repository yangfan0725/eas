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

public class QuestionPaperDefineEntry extends BillEntryBase implements IQuestionPaperDefineEntry
{
    public QuestionPaperDefineEntry()
    {
        super();
        registerInterface(IQuestionPaperDefineEntry.class, this);
    }
    public QuestionPaperDefineEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IQuestionPaperDefineEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1BB8B922");
    }
    private QuestionPaperDefineEntryController getController() throws BOSException
    {
        return (QuestionPaperDefineEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public QuestionPaperDefineEntryInfo getQuestionPaperDefineEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperDefineEntryInfo(getContext(), pk);
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
    public QuestionPaperDefineEntryInfo getQuestionPaperDefineEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperDefineEntryInfo(getContext(), pk, selector);
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
    public QuestionPaperDefineEntryInfo getQuestionPaperDefineEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuestionPaperDefineEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public QuestionPaperDefineEntryCollection getQuestionPaperDefineEntryCollection() throws BOSException
    {
        try {
            return getController().getQuestionPaperDefineEntryCollection(getContext());
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
    public QuestionPaperDefineEntryCollection getQuestionPaperDefineEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuestionPaperDefineEntryCollection(getContext(), view);
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
    public QuestionPaperDefineEntryCollection getQuestionPaperDefineEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuestionPaperDefineEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}