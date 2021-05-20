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
import java.util.Map;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public class RoomAreaCompensate extends FDCBill implements IRoomAreaCompensate
{
    public RoomAreaCompensate()
    {
        super();
        registerInterface(IRoomAreaCompensate.class, this);
    }
    public RoomAreaCompensate(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomAreaCompensate.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("455E117A");
    }
    private RoomAreaCompensateController getController() throws BOSException
    {
        return (RoomAreaCompensateController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomAreaCompensateInfo getRoomAreaCompensateInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomAreaCompensateInfo(getContext(), pk);
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
    public RoomAreaCompensateInfo getRoomAreaCompensateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomAreaCompensateInfo(getContext(), pk, selector);
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
    public RoomAreaCompensateInfo getRoomAreaCompensateInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomAreaCompensateInfo(getContext(), oql);
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
    public RoomAreaCompensateCollection getRoomAreaCompensateCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomAreaCompensateCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomAreaCompensateCollection getRoomAreaCompensateCollection() throws BOSException
    {
        try {
            return getController().getRoomAreaCompensateCollection(getContext());
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
    public RoomAreaCompensateCollection getRoomAreaCompensateCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomAreaCompensateCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������棨�Ѵ��ڵļ�¼�͸��£������ڵ�ֱ��������-User defined method
     *@param idList ����ID
     *@param valueMap ֵmap
     */
    public void batchSave(List idList, Map valueMap) throws BOSException, EASBizException
    {
        try {
            getController().batchSave(getContext(), idList, valueMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������-User defined method
     *@param idList ����ID
     *@param schemeId ����Id
     *@return
     */
    public Map calcAmount(List idList, String schemeId) throws BOSException, EASBizException
    {
        try {
            return getController().calcAmount(getContext(), idList, schemeId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ύ¥��������-User defined method
     *@param buildingId ¥��ID
     */
    public void submitToWorkFlow(String buildingId) throws BOSException, EASBizException
    {
        try {
            getController().submitToWorkFlow(getContext(), buildingId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�õ��������Ϣ-User defined method
     *@param filterStr filterStr
     *@return
     */
    public IRowSet getRoomInfoList(String filterStr) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomInfoList(getContext(), filterStr);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�õ��������Ϣ-User defined method
     *@param roomId roomId
     *@return
     */
    public IRowSet getCompenstateRoomInfo(String roomId) throws BOSException, EASBizException
    {
        try {
            return getController().getCompenstateRoomInfo(getContext(), roomId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param idList idList
     */
    public void setNullify(String idList) throws BOSException, EASBizException
    {
        try {
            getController().setNullify(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�µļ�����-User defined method
     *@param roomList roomList
     *@param schemeId schemeId
     *@return
     */
    public Map calcAmountForSHE(List roomList, String schemeId) throws BOSException, EASBizException
    {
        try {
            return getController().calcAmountForSHE(getContext(), roomList, schemeId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������Ҽ��������ܼ�-User defined method
     *@param id id
     */
    public void auditAndCalcSellAmount(String id) throws BOSException, EASBizException
    {
        try {
            getController().auditAndCalcSellAmount(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���������Ҽ��������ܼ�-User defined method
     *@param id id
     */
    public void unAuditAndCalcSellAmount(String id) throws BOSException, EASBizException
    {
        try {
            getController().unAuditAndCalcSellAmount(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������-User defined method
     *@param billId billId
     */
    public void setAuditing(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setAuditing(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ύ-User defined method
     *@param billId ����id
     */
    public void setSubmit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setSubmit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ���������������Ϣ-User defined method
     *@param billId billId
     */
    public void deleteCompensateInfo(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().deleteCompensateInfo(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ǩԼ��������һ���տ���ϸ-User defined method
     *@param compColl compColl
     */
    public void createBillForSign(CompensateRoomListCollection compColl) throws BOSException, EASBizException
    {
        try {
            getController().createBillForSign(getContext(), compColl);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ��ǩԼ��ϸ�еĲ������ϸ-User defined method
     *@param roomId roomId
     *@param comColl comColl
     */
    public void deleteBillFromSign(String roomId, SignManageCollection comColl) throws BOSException, EASBizException
    {
        try {
            getController().deleteBillFromSign(getContext(), roomId, comColl);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ҵ�������в���һ����¼-User defined method
     *@param roomIdList roomIdList
     *@param compId compId
     */
    public void createRoomCompensateForView(List roomIdList, String compId) throws BOSException, EASBizException
    {
        try {
            getController().createRoomCompensateForView(getContext(), roomIdList, compId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ��ҵ�������е����������Ϣ-User defined method
     *@param roomIdList roomIdList
     *@param comId comId
     */
    public void deleteRoomCompensateForView(List roomIdList, String comId) throws BOSException, EASBizException
    {
        try {
            getController().deleteRoomCompensateForView(getContext(), roomIdList, comId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}