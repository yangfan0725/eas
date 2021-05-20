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
     *����-System defined method
     *@param pk ����
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
     *����-System defined method
     *@param filter ����
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
     *����-System defined method
     *@param oql ����
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
     *ȡֵ-System defined method
     *@param pk ȡֵ
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
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
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
     *ȡֵ-System defined method
     *@param oql ȡֵ
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
     *����-System defined method
     *@param model ����
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
     *����-System defined method
     *@param pk ����
     *@param model ����
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
     *�޸�-System defined method
     *@param pk ����
     *@param model ����
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
     *�ֲ�����-System defined method
     *@param model �ֲ�����
     *@param selector �ֲ�����
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
     *���´����-System defined method
     *@param pk ���´����
     *@param model ���´����
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
     *ɾ��-System defined method
     *@param pk ɾ��
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
     *ȡ����-System defined method
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
     *ȡ����-System defined method
     *@param oql ȡ����
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
     *ȡ����-System defined method
     *@param filter ȡ����
     *@param sorter ȡ����
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
     *ȡ����-System defined method
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
     *ȡ����-System defined method
     *@param view ȡ����
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
     *ȡ����-System defined method
     *@param oql ȡ����
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
     *ɾ��-System defined method
     *@param filter ɾ��
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
     *ɾ��-System defined method
     *@param oql ɾ��
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
     *ɾ��-System defined method
     *@param arrayPK ɾ��
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
     *���ù�����Ŀ-User defined method
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
     *���ù�����Ŀ-User defined method
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
     *����ƷID��ָ�����ϸ���ȵ�ָ��ˢ��-User defined method
     *@param projId ������ĿID
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
     *��ʧ������Ŀ����ƾ֤-User defined method
     *@param projectPK ������Ŀpk
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
     *��ȡ������Ŀ����ƾ֤-User defined method
     *@param projectPK ������Ŀpk
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
     *������Ŀ״̬����-User defined method
     *@param projectId ������Ŀid
     *@param changeCase ״̬��������
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
     *����ƷID��ָ�����ϸ���ȵ�ָ��ˢ��-User defined method
     *@param projId ������ĿID
     *@param productId ��ƷID
     *@param apportions ָ���б�
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
     *������Ŀϵ��-User defined method
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
     *ͬ����Ŀ-User defined method
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
     *���¿���˳��-User defined method
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
     *�����Ƿ񿪷���Ŀ-User defined method
     *@param pk ����
     *@param isDevPrj �Ƿ񿪷���Ŀ
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