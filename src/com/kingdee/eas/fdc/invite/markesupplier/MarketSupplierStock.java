package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import java.util.HashSet;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;
import java.util.List;
import com.kingdee.eas.fdc.invite.markesupplier.app.*;

public class MarketSupplierStock extends FDCDataBase implements IMarketSupplierStock
{
    public MarketSupplierStock()
    {
        super();
        registerInterface(IMarketSupplierStock.class, this);
    }
    public MarketSupplierStock(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierStock.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("98690D80");
    }
    private MarketSupplierStockController getController() throws BOSException
    {
        return (MarketSupplierStockController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarketSupplierStockInfo getMarketSupplierStockInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public MarketSupplierStockInfo getMarketSupplierStockInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public MarketSupplierStockInfo getMarketSupplierStockInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarketSupplierStockCollection getMarketSupplierStockCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierStockCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public MarketSupplierStockCollection getMarketSupplierStockCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public MarketSupplierStockCollection getMarketSupplierStockCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��׼-User defined method
     *@param pks ��Ӧ��PK
     *@return
     */
    public Map approve(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            return getController().approve(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����׼-User defined method
     *@param pks pks
     *@return
     */
    public Map unApprove(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            return getController().unApprove(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ύ-User defined method
     *@param supObjectValue supObjectValue
     */
    public void submitTwo(IObjectValue supObjectValue) throws BOSException, EASBizException
    {
        try {
            getController().submitTwo(getContext(), supObjectValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���������еõ���Ӧ����Ϣ-User defined method
     *@param supLongNumber supLongNumber
     *@return
     */
    public MarketSupplierStockCollection getSupplierDataBase(String supLongNumber) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierDataBase(getContext(), supLongNumber);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���빩Ӧ��-User defined method
     *@param objCollection objCollection
     *@param typeLongNumber typeLongNumber
     *@return
     */
    public Map importDataBase(IObjectCollection objCollection, String typeLongNumber) throws BOSException, EASBizException
    {
        try {
            return getController().importDataBase(getContext(), objCollection, typeLongNumber);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ�÷�������-User defined method
     *@return
     */
    public MarketSupplierStockCollection getSericeArea() throws BOSException, EASBizException
    {
        try {
            return getController().getSericeArea(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getDataBase-User defined method
     *@param list list
     *@return
     */
    public Set getDataBase(List list) throws BOSException, EASBizException
    {
        try {
            return getController().getDataBase(getContext(), list);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������ʷ��ѯ-User defined method
     *@param list list
     *@return
     */
    public Set getAHDBValue(List list) throws BOSException, EASBizException
    {
        try {
            return getController().getAHDBValue(getContext(), list);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��Χ��ʷ��ѯ-User defined method
     *@param list list
     *@return
     */
    public Set getEHDBValue(List list) throws BOSException, EASBizException
    {
        try {
            return getController().getEHDBValue(getContext(), list);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ǩԼ��ʷ��ѯ-User defined method
     *@param list list
     *@return
     */
    public Set getSHDBValue(List list) throws BOSException, EASBizException
    {
        try {
            return getController().getSHDBValue(getContext(), list);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ָ���Ƿ����õĲ�ѯ-User defined method
     *@param list list
     *@return
     */
    public boolean getATRFValue(List list) throws BOSException, EASBizException
    {
        try {
            return getController().getATRFValue(getContext(), list);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������ʷ����-User defined method
     *@param historyType ��ʷ����,Ҫ��д����ʷ������ʷ,����Χ��ʷ.������ʷ��,��д�������ط������˸ù�Ӧ��,�����б��ͬģ�������˴˹�Ӧ����ô�ڹ�Ӧ�̿������Ӧ�Ĵ�����1
     *@param pk ��Ӧ��PK
     *@param isAudit ��д����.�Ƿ����������͸�����ʷ����,����������Ӵ���1Ϊ����true,����Ϊfalse,��������1
     */
    public void updateHisCount(String historyType, IObjectPK pk, boolean isAudit) throws BOSException, EASBizException
    {
        try {
            getController().updateHisCount(getContext(), historyType, pk, isAudit);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������ʷ����-User defined method
     *@param historyType histype
     *@param pks ��Ӧ��PK
     *@param isAudit ����ͬ��һ������
     */
    public void beachUpdateHisCount(String historyType, IObjectPK[] pks, boolean isAudit) throws BOSException, EASBizException
    {
        try {
            getController().beachUpdateHisCount(getContext(), historyType, pks, isAudit);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������-User defined method
     *@param cuid cuid
     *@param diddata diddata
     *@param cuiddata cuiddata
     */
    public void batchAssign2(String cuid, String[] diddata, String[] cuiddata) throws BOSException, EASBizException
    {
        try {
            getController().batchAssign2(getContext(), cuid, diddata, cuiddata);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������-User defined method
     *@param cuid cuid
     *@param sql sql
     *@param basedata basedata
     *@param cuData cuData
     */
    public void batchAssign2(String cuid, String sql, HashSet basedata, HashSet cuData) throws BOSException, EASBizException
    {
        try {
            getController().batchAssign2(getContext(), cuid, sql, basedata, cuData);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ͬ��������-User defined method
     *@param objectValue objectValue
     */
    public void addToSysSupplier(IObjectValue objectValue) throws BOSException, EASBizException
    {
        try {
            getController().addToSysSupplier(getContext(), objectValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}