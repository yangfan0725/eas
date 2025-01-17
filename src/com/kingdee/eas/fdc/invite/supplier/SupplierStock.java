package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import java.util.List;

public class SupplierStock extends FDCDataBase implements ISupplierStock
{
    public SupplierStock()
    {
        super();
        registerInterface(ISupplierStock.class, this);
    }
    public SupplierStock(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierStock.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3B04106C");
    }
    private SupplierStockController getController() throws BOSException
    {
        return (SupplierStockController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SupplierStockInfo getSupplierStockInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierStockInfo(getContext(), pk);
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
    public SupplierStockInfo getSupplierStockInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierStockInfo(getContext(), pk, selector);
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
    public SupplierStockInfo getSupplierStockInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierStockInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SupplierStockCollection getSupplierStockCollection() throws BOSException
    {
        try {
            return getController().getSupplierStockCollection(getContext());
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
    public SupplierStockCollection getSupplierStockCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupplierStockCollection(getContext(), view);
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
    public SupplierStockCollection getSupplierStockCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupplierStockCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *核准-User defined method
     *@param pks 供应商PK
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
     *反核准-User defined method
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
     *提交-User defined method
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
     *从主数据中得到供应商信息-User defined method
     *@param supLongNumber supLongNumber
     *@return
     */
    public SupplierCollection getSupplierDataBase(String supLongNumber) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierDataBase(getContext(), supLongNumber);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *导入供应商-User defined method
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
     *取得服务区域-User defined method
     *@return
     */
    public FDCSplAreaCollection getSericeArea() throws BOSException, EASBizException
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
     *评审历史查询-User defined method
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
     *入围历史查询-User defined method
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
     *签约历史查询-User defined method
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
     *评审指标是否被引用的查询-User defined method
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
     *更新历史次数-User defined method
     *@param historyType 历史类型,要回写的历史次数历史,如入围历史.评审历史等,回写即其它地方引用了该供应商,比如招标合同模块引用了此供应商那么在供应商库里相对应的次数加1
     *@param pk 供应商PK
     *@param isAudit 回写功能.是否是审批类型更新历史次数,如果满足增加次数1为审批true,其它为false,即次数减1
     */
    public void updateHisCount(UpdateHistoryTypeEnum historyType, IObjectPK pk, boolean isAudit) throws BOSException, EASBizException
    {
        try {
            getController().updateHisCount(getContext(), historyType, pk, isAudit);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量更新历史次数-User defined method
     *@param historyType histype
     *@param pks 供应商PK
     *@param isAudit 描述同上一个方法
     */
    public void beachUpdateHisCount(UpdateHistoryTypeEnum historyType, IObjectPK[] pks, boolean isAudit) throws BOSException, EASBizException
    {
        try {
            getController().beachUpdateHisCount(getContext(), historyType, pks, isAudit);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *批量分配-User defined method
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
     *批量分配-User defined method
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
     *同步主数据-User defined method
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
    /**
     *核准-User defined method
     *@param id id
     */
    public void approve(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().approve(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反核准-User defined method
     *@param id id
     */
    public void unApprove(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().unApprove(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置为提交-User defined method
     *@param id id
     */
    public void setSubmitStatus(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().setSubmitStatus(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置为审批中-User defined method
     *@param id id
     */
    public void setAudittingStatus(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().setAudittingStatus(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}