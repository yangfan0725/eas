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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.base.permission.UserInfo;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.framework.TreeBase;

public class SellProject extends TreeBase implements ISellProject
{
    public SellProject()
    {
        super();
        registerInterface(ISellProject.class, this);
    }
    public SellProject(Context ctx)
    {
        super(ctx);
        registerInterface(ISellProject.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2FFBE5AC");
    }
    private SellProjectController getController() throws BOSException
    {
        return (SellProjectController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public SellProjectInfo getSellProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSellProjectInfo(getContext(), pk, selector);
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
    public SellProjectInfo getSellProjectInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSellProjectInfo(getContext(), pk);
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
    public SellProjectInfo getSellProjectInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSellProjectInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SellProjectCollection getSellProjectCollection() throws BOSException
    {
        try {
            return getController().getSellProjectCollection(getContext());
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
    public SellProjectCollection getSellProjectCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSellProjectCollection(getContext(), view);
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
    public SellProjectCollection getSellProjectCollection(String oql) throws BOSException
    {
        try {
            return getController().getSellProjectCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������������¼-User defined method
     *@param idStr ID
     *@return
     */
    public SellProjectInfo getBaseValue(String idStr) throws BOSException
    {
        try {
            return getController().getBaseValue(getContext(), idStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�޷�¼��Ϣ-User defined method
     *@param idSet id����
     *@return
     */
    public SellProjectCollection getBaseCollection(Set idSet) throws BOSException
    {
        try {
            return getController().getBaseCollection(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������ʼ��-User defined method
     *@param projectIds ��ĿID
     *@param orgUnitId ��֯ID
     *@param userInfo �û�
     *@return
     */
    public boolean endInit(List projectIds, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            return getController().endInit(getContext(), projectIds, orgUnitId, userInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ʼ��-User defined method
     *@param projectIds ��ĿID
     *@param orgUnitId ��֯ID
     *@param userInfo �û�
     *@return
     */
    public boolean unInit(List projectIds, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            return getController().unInit(getContext(), projectIds, orgUnitId, userInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȫ��������ʼ��-User defined method
     *@param orgUnitId ��֯ID
     *@param userInfo ��ǰ�û�
     *@return
     */
    public boolean allEndInit(String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            return getController().allEndInit(getContext(), orgUnitId, userInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȫ������ʼ��-User defined method
     *@param orgUnitId ��֯ID
     *@param userInfo ��ǰ�û�
     *@return
     */
    public boolean allUnInit(String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            return getController().allUnInit(getContext(), orgUnitId, userInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��˾���˳ɹ����¸�ϵͳ�ڼ�-User defined method
     *@param comId ��˾ID
     *@param userInfo ��ǰ�û�
     */
    public void nextSystem(String comId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            getController().nextSystem(getContext(), comId, userInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����˵���һ���ڼ�-User defined method
     *@param comId ��˾ID
     */
    public void preSystem(String comId) throws BOSException, EASBizException
    {
        try {
            getController().preSystem(getContext(), comId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������Ŀ�����ܿ�����-User defined method
     *@param model ��Ŀ
     */
    public void projectDataUpdate(IObjectValue model) throws BOSException, EASBizException
    {
        try {
            getController().projectDataUpdate(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������Ŀ�ܿص�������-User defined method
     */
    public void allProjectDataUpdate() throws BOSException, EASBizException
    {
        try {
            getController().allProjectDataUpdate(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ŀ���ϸ��³���Ŀ-User defined method
     *@param id id
     *@param orgUnitID orgUnitID
     *@param longNumber longNumber
     */
    public void updateToSHEProject(BOSUuid id, BOSUuid orgUnitID, String longNumber) throws BOSException, EASBizException
    {
        try {
            getController().updateToSHEProject(getContext(), id, orgUnitID, longNumber);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����Ŀͬ������Ŀ����-User defined method
     *@param id id
     *@param number number
     *@param name name
     */
    public void updateToSellProject(BOSUuid id, String number, String name) throws BOSException, EASBizException
    {
        try {
            getController().updateToSellProject(getContext(), id, number, name);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�õ���Ŀ����-User defined method
     *@param type type
     *@return
     */
    public ArrayList getSellProTreeNodes(String type) throws BOSException, EASBizException
    {
        try {
            return getController().getSellProTreeNodes(getContext(), type);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����Ƿ�ɾ����״̬-User defined method
     *@param billId ����id
     */
    public void updateDeleteStatus(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().updateDeleteStatus(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ����Ŀ����-User defined method
     *@param billId billId
     */
    public void deleteSellProject(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().deleteSellProject(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ����Ŀ����-User defined method
     *@param billId billId
     */
    public void deleteProjectInSystem(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().deleteProjectInSystem(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Ѹ���Ļ���ͬ��������Ŀ��-User defined method
     *@param billId billId
     *@param roomModelList roomModelList
     */
    public void updateRoomModelForChild(BOSUuid billId, List roomModelList) throws BOSException, EASBizException
    {
        try {
            getController().updateRoomModelForChild(getContext(), billId, roomModelList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}