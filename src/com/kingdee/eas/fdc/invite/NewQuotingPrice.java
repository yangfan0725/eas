package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class NewQuotingPrice extends FDCBill implements INewQuotingPrice
{
    public NewQuotingPrice()
    {
        super();
        registerInterface(INewQuotingPrice.class, this);
    }
    public NewQuotingPrice(Context ctx)
    {
        super(ctx);
        registerInterface(INewQuotingPrice.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("268DAEAC");
    }
    private NewQuotingPriceController getController() throws BOSException
    {
        return (NewQuotingPriceController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public NewQuotingPriceInfo getNewQuotingPriceInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNewQuotingPriceInfo(getContext(), pk);
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
    public NewQuotingPriceInfo getNewQuotingPriceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNewQuotingPriceInfo(getContext(), pk, selector);
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
    public NewQuotingPriceInfo getNewQuotingPriceInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNewQuotingPriceInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public NewQuotingPriceCollection getNewQuotingPriceCollection() throws BOSException
    {
        try {
            return getController().getNewQuotingPriceCollection(getContext());
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
    public NewQuotingPriceCollection getNewQuotingPriceCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNewQuotingPriceCollection(getContext(), view);
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
    public NewQuotingPriceCollection getNewQuotingPriceCollection(String oql) throws BOSException
    {
        try {
            return getController().getNewQuotingPriceCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�б겢��������Ϣ���浽�嵥��Ŀ���ο���-User defined method
     *@param quotingId ����id
     *@param listingId �嵥id
     */
    public void acceptBidExportQuoting(String quotingId, String listingId) throws BOSException, EASBizException
    {
        try {
            getController().acceptBidExportQuoting(getContext(), quotingId, listingId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�б꣬�������嵥��Ŀ���ο�������-User defined method
     *@param quotingId ����id
     */
    public void acceptBid(String quotingId) throws BOSException, EASBizException
    {
        try {
            getController().acceptBid(getContext(), quotingId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����嵥��Ŀ���ο��۵������ݿ�-User defined method
     *@param quotingId ����id
     *@param listingId �嵥id
     */
    public void exportQuoting(String quotingId, String listingId) throws BOSException, EASBizException
    {
        try {
            getController().exportQuoting(getContext(), quotingId, listingId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���б�-User defined method
     *@param quotingId ����id
     */
    public void unacceptBid(String quotingId) throws BOSException, EASBizException
    {
        try {
            getController().unacceptBid(getContext(), quotingId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}