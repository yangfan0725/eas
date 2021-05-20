package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.sellhouse.app.*;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.framework.IDataBase;

public class FDCCustomer extends DataBase implements IFDCCustomer
{
    public FDCCustomer()
    {
        super();
        registerInterface(IFDCCustomer.class, this);
    }
    public FDCCustomer(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCCustomer.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("682588A8");
    }
    private FDCCustomerController getController() throws BOSException
    {
        return (FDCCustomerController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public FDCCustomerInfo getFDCCustomerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCustomerInfo(getContext(), pk, selector);
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
    public FDCCustomerInfo getFDCCustomerInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCustomerInfo(getContext(), pk);
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
    public FDCCustomerInfo getFDCCustomerInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCustomerInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public FDCCustomerCollection getFDCCustomerCollection() throws BOSException
    {
        try {
            return getController().getFDCCustomerCollection(getContext());
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
    public FDCCustomerCollection getFDCCustomerCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCCustomerCollection(getContext(), view);
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
    public FDCCustomerCollection getFDCCustomerCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCCustomerCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ͬ����ϵͳ�û�-User defined method
     *@param id ID
     */
    public void addToSysCustomer(String id) throws BOSException, EASBizException
    {
        try {
            getController().addToSysCustomer(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param idList ����IDString��ʽ�ļ���
     */
    public void blankOut(List idList) throws BOSException
    {
        try {
            getController().blankOut(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���-User defined method
     *@param idList ����IDStirng��ʽ�ļ���
     */
    public void pickUp(List idList) throws BOSException
    {
        try {
            getController().pickUp(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param fdccustomer ���ز��ͻ�
     */
    public void modifyName(FDCCustomerInfo fdccustomer) throws BOSException
    {
        try {
            getController().modifyName(getContext(), fdccustomer);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���Ϊ�ص����-User defined method
     *@param idList �ͻ�String��ʽPKID�ļ���
     */
    public void signImportantTrack(List idList) throws BOSException
    {
        try {
            getController().signImportantTrack(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������ص������¼-User defined method
     *@param idList �ͻ�String��ʽPKID�ļ���
     */
    public void cancelImportantTrack(List idList) throws BOSException
    {
        try {
            getController().cancelImportantTrack(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ת��-User defined method
     *@param idList �ͻ�String��ʽPKID�ļ���
     *@param salesmanId ������String��ʽ������ID
     */
    public void switchTo(List idList, String salesmanId) throws BOSException
    {
        try {
            getController().switchTo(getContext(), idList, salesmanId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ͬ����ϵͳ�û�-User defined method
     *@param id ID
     *@param list ���༯��
     */
    public void addToSysCustomer(String id, List list) throws BOSException, EASBizException
    {
        try {
            getController().addToSysCustomer(getContext(), id, list);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ж��ܷ񱣴�-User defined method
     *@param model model
     *@return
     */
    public Map verifySave(FDCCustomerInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().verifySave(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����Ϲ�״̬-User defined method
     *@param idList �û�ID�б�
     */
    public void setStatus(List idList) throws BOSException
    {
        try {
            getController().setStatus(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ж��ܷ񱣴�-User defined method
     *@param model model
     *@param isSingle isSingle
     *@return
     */
    public Map verifySave(IObjectValue model, boolean isSingle) throws BOSException, EASBizException
    {
        try {
            return getController().verifySave(getContext(), model, isSingle);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������޺�ͬ�ϵĿͻ���Ϣ-User defined method
     *@param fdcCustID �ͻ�ID
     */
    public void updateTenancyBill(String fdcCustID) throws BOSException, EASBizException
    {
        try {
            getController().updateTenancyBill(getContext(), fdcCustID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}