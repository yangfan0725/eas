package com.kingdee.eas.fdc.basedata;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public class CurProject extends Project implements ICurProject
{
    public CurProject()
    {
        super();
        registerInterface(ICurProject.class, this);
    }
    public CurProject(Context ctx)
    {
        super(ctx);
        registerInterface(ICurProject.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F9E5E92B");
    }
    private CurProjectController getController() throws BOSException
    {
        return (CurProjectController)getBizController();
    }
    /**
     *存在-System defined method
     *@param pk 存在
     *@return
     */
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *存在-System defined method
     *@param filter 存在
     *@return
     */
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *存在-System defined method
     *@param oql 存在
     *@return
     */
    public boolean exists(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public CurProjectInfo getCurProjectInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCurProjectInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public CurProjectInfo getCurProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCurProjectInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public CurProjectInfo getCurProjectInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCurProjectInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增-System defined method
     *@param model 新增
     *@return
     */
    public IObjectPK addnew(CurProjectInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().addnew(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增-System defined method
     *@param pk 新增
     *@param model 新增
     */
    public void addnew(IObjectPK pk, CurProjectInfo model) throws BOSException, EASBizException
    {
        try {
            getController().addnew(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *修改-System defined method
     *@param pk 更新
     *@param model 更新
     */
    public void update(IObjectPK pk, CurProjectInfo model) throws BOSException, EASBizException
    {
        try {
            getController().update(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *局部更新-System defined method
     *@param model 局部更新
     *@param selector 局部更新
     */
    public void updatePartial(CurProjectInfo model, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            getController().updatePartial(getContext(), model, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新大对象-System defined method
     *@param pk 更新大对象
     *@param model 更新大对象
     */
    public void updateBigObject(IObjectPK pk, CurProjectInfo model) throws BOSException
    {
        try {
            getController().updateBigObject(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除-System defined method
     *@param pk 删除
     */
    public void delete(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取主键-System defined method
     *@return
     */
    public IObjectPK[] getPKList() throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取主键-System defined method
     *@param oql 取主键
     *@return
     */
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取主键-System defined method
     *@param filter 取主键
     *@param sorter 取主键
     *@return
     */
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), filter, sorter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public CurProjectCollection getCurProjectCollection() throws BOSException
    {
        try {
            return getController().getCurProjectCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public CurProjectCollection getCurProjectCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCurProjectCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public CurProjectCollection getCurProjectCollection(String oql) throws BOSException
    {
        try {
            return getController().getCurProjectCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除-System defined method
     *@param filter 删除
     *@return
     */
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除-System defined method
     *@param oql 删除
     *@return
     */
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *删除-System defined method
     *@param arrayPK 删除
     */
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), arrayPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *启用工程项目-User defined method
     *@param cpPK cpPK
     *@return
     */
    public boolean enabled(IObjectPK cpPK) throws BOSException, EASBizException
    {
        try {
            return getController().enabled(getContext(), cpPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *禁用工程项目-User defined method
     *@param cpPK cpPK
     *@return
     */
    public boolean disEnabled(IObjectPK cpPK) throws BOSException, EASBizException
    {
        try {
            return getController().disEnabled(getContext(), cpPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *按产品ID及指标进行细力度的指标刷新-User defined method
     *@param projId 工程项目ID
     *@return
     */
    public int idxRefresh(String projId) throws BOSException
    {
        try {
            return getController().idxRefresh(getContext(), projId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *流失工程项目处理凭证-User defined method
     *@param projectPK 工程项目pk
     */
    public void traceVoucher4Flow(IObjectPK projectPK) throws BOSException, EASBizException
    {
        try {
            getController().traceVoucher4Flow(getContext(), projectPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取工程项目处理凭证-User defined method
     *@param projectPK 工程项目pk
     */
    public void traceVoucher4Get(IObjectPK projectPK) throws BOSException, EASBizException
    {
        try {
            getController().traceVoucher4Get(getContext(), projectPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *工程项目状态更改-User defined method
     *@param projectId 工程项目id
     *@param changeCase 状态更改类型
     */
    public void changeStatus(String projectId, String changeCase) throws BOSException, EASBizException
    {
        try {
            getController().changeStatus(getContext(), projectId, changeCase);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *按产品ID及指标进行细力度的指标刷新-User defined method
     *@param projId 工程项目ID
     *@param productId 产品ID
     *@param apportions 指标列表
     *@return
     */
    public int idxRefresh(String projId, String productId, List apportions) throws BOSException
    {
        try {
            return getController().idxRefresh(getContext(), projId, productId, apportions);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置项目系列-User defined method
     *@param projectTypeMap projectTypeMap
     *@return
     */
    public boolean setProjectTpe(Map projectTypeMap) throws BOSException, EASBizException
    {
        try {
            return getController().setProjectTpe(getContext(), projectTypeMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步项目-User defined method
     *@param projectMap projectMap
     *@return
     */
    public String synchronousProjects(Map projectMap) throws BOSException, EASBizException
    {
        try {
            return getController().synchronousProjects(getContext(), projectMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新开发顺序-User defined method
     *@param cuId controlUnitId
     */
    public void updateSortNo(String cuId) throws BOSException
    {
        try {
            getController().updateSortNo(getContext(), cuId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置是否开发项目-User defined method
     *@param pk 主键
     *@param isDevPrj 是否开发项目
     */
    public void setIsDevPrj(IObjectPK pk, boolean isDevPrj) throws BOSException, EASBizException
    {
        try {
            getController().setIsDevPrj(getContext(), pk, isDevPrj);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}