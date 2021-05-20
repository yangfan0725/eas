package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import java.lang.Object;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.contract.app.*;

public class ChangeAuditBill extends FDCBill implements IChangeAuditBill
{
    public ChangeAuditBill()
    {
        super();
        registerInterface(IChangeAuditBill.class, this);
    }
    public ChangeAuditBill(Context ctx)
    {
        super(ctx);
        registerInterface(IChangeAuditBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("70116117");
    }
    private ChangeAuditBillController getController() throws BOSException
    {
        return (ChangeAuditBillController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public ChangeAuditBillInfo getChangeAuditBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeAuditBillInfo(getContext(), pk);
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
    public ChangeAuditBillInfo getChangeAuditBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeAuditBillInfo(getContext(), pk, selector);
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
    public ChangeAuditBillInfo getChangeAuditBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeAuditBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ChangeAuditBillCollection getChangeAuditBillCollection() throws BOSException
    {
        try {
            return getController().getChangeAuditBillCollection(getContext());
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
    public ChangeAuditBillCollection getChangeAuditBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChangeAuditBillCollection(getContext(), view);
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
    public ChangeAuditBillCollection getChangeAuditBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getChangeAuditBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ǽ�-User defined method
     *@param idSet id����
     */
    public void register(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().register(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�·�-User defined method
     *@param idSet id����
     */
    public void disPatch(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().disPatch(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ǰ�·�-User defined method
     *@param idSet id����
     */
    public void aheadDisPatch(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().aheadDisPatch(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������״̬-User defined method
     *@param billId billId
     */
    public void setAudittingStatus(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setAudittingStatus(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ύ״̬-User defined method
     *@param billId billId
     */
    public void setSubmitStatus(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setSubmitStatus(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ǽ�-User defined method
     *@param pk pk
     */
    public void register4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().register4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�·�-User defined method
     *@param pk pk
     */
    public void disPatch4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().disPatch4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ǰ�·�-User defined method
     *@param pk pk
     */
    public void aheadDisPatch4WF(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().aheadDisPatch4WF(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��Լ�滮�����-User defined method
     *@param pk ����������ͬǩԼ������֮�����ĺ�Լ�滮���ʱ�Ƿ��ϸ���ơ��Ĳ���ֵΪ���ϸ���ơ�ʱ������������ύʱУ�飺��ǰ��������Ϲ����Ķ����ͬ�еġ������+��ǩԼ��+���ۼƱ�����Ƿ���ڡ���ܺ�Լ���ġ��滮����������ڣ���ʾ������ͬXX���ġ������+��ǩԼ��+���ۼƱ�������ڡ���ܺ�Լ���ġ��滮�����������ύ��
     *@param contractMap key:��ͬId,value:��ͬ��Ӧ�Ĳ�����
     *@return
     */
    public Object checkAmount(IObjectPK pk, Map contractMap) throws BOSException, EASBizException
    {
        try {
            return getController().checkAmount(getContext(), pk, contractMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}