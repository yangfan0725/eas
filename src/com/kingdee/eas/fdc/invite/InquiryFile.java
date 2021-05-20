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
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public class InquiryFile extends FDCBill implements IInquiryFile
{
    public InquiryFile()
    {
        super();
        registerInterface(IInquiryFile.class, this);
    }
    public InquiryFile(Context ctx)
    {
        super(ctx);
        registerInterface(IInquiryFile.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3FE9963F");
    }
    private InquiryFileController getController() throws BOSException
    {
        return (InquiryFileController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public InquiryFileCollection getInquiryFileCollection() throws BOSException
    {
        try {
            return getController().getInquiryFileCollection(getContext());
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
    public InquiryFileCollection getInquiryFileCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInquiryFileCollection(getContext(), view);
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
    public InquiryFileCollection getInquiryFileCollection(String oql) throws BOSException
    {
        try {
            return getController().getInquiryFileCollection(getContext(), oql);
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
    public InquiryFileInfo getInquiryFileInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInquiryFileInfo(getContext(), pk);
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
    public InquiryFileInfo getInquiryFileInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInquiryFileInfo(getContext(), pk, selector);
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
    public InquiryFileInfo getInquiryFileInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInquiryFileInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param billId billId
     */
    public void audit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param idList idList
     */
    public void audit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������-User defined method
     *@param billId billId
     */
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������-User defined method
     *@param idList idList
     */
    public void unAudit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}