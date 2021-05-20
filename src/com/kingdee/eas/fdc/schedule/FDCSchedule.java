package com.kingdee.eas.fdc.schedule;

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
import com.kingdee.eas.fdc.schedule.framework.ScheduleBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import java.util.Set;
import com.kingdee.eas.fdc.schedule.framework.IScheduleBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public class FDCSchedule extends ScheduleBase implements IFDCSchedule
{
    public FDCSchedule()
    {
        super();
        registerInterface(IFDCSchedule.class, this);
    }
    public FDCSchedule(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSchedule.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D0FA7B86");
    }
    private FDCScheduleController getController() throws BOSException
    {
        return (FDCScheduleController)getBizController();
    }
    /**
     *exists-System defined method
     *@param pk pk
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
     *exists-System defined method
     *@param filter filter
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
     *exists-System defined method
     *@param oql oql
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
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCScheduleInfo getFDCScheduleInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCScheduleInfo(getContext(), pk);
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
    public FDCScheduleInfo getFDCScheduleInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCScheduleInfo(getContext(), pk, selector);
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
    public FDCScheduleInfo getFDCScheduleInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCScheduleInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-System defined method
     *@param model model
     *@return
     */
    public IObjectPK addnew(FDCScheduleInfo model) throws BOSException, EASBizException
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
     *@param pk pk
     *@param model model
     */
    public void addnew(IObjectPK pk, FDCScheduleInfo model) throws BOSException, EASBizException
    {
        try {
            getController().addnew(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-System defined method
     *@param pk pk
     *@param model model
     */
    public void update(IObjectPK pk, FDCScheduleInfo model) throws BOSException, EASBizException
    {
        try {
            getController().update(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updatePartial-System defined method
     *@param model model
     *@param selector selector
     */
    public void updatePartial(FDCScheduleInfo model, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            getController().updatePartial(getContext(), model, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updateBigObject-System defined method
     *@param pk pk
     *@param model model
     */
    public void updateBigObject(IObjectPK pk, FDCScheduleInfo model) throws BOSException
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
     *@param pk pk
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
     *getPKList-System defined method
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
     *getPKList-System defined method
     *@param oql oql
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
     *getPKList-System defined method
     *@param filter filter
     *@param sorter sorter
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
     *getCollection-System defined method
     *@return
     */
    public FDCScheduleCollection getFDCScheduleCollection() throws BOSException
    {
        try {
            return getController().getFDCScheduleCollection(getContext());
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
    public FDCScheduleCollection getFDCScheduleCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCScheduleCollection(getContext(), view);
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
    public FDCScheduleCollection getFDCScheduleCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCScheduleCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ��-System defined method
     *@param filter filter
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
     *@param oql oql
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
     *@param arrayPK arrayPK
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
     *����-User defined method
     *@param ids ID����
     *@return
     */
    public Map cancel(Set ids) throws BOSException, EASBizException
    {
        try {
            return getController().cancel(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param ids ID����
     *@return
     */
    public Map cancelCancel(Set ids) throws BOSException, EASBizException
    {
        try {
            return getController().cancelCancel(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���� -User defined method
     *@param ids ����
     *@return
     */
    public Map audit(Set ids) throws BOSException, EASBizException
    {
        try {
            return getController().audit(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������ -User defined method
     *@param ids ID����
     *@return
     */
    public Map unAudit(Set ids) throws BOSException, EASBizException
    {
        try {
            return getController().unAudit(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ر�-User defined method
     *@param ids ID����
     *@return
     */
    public Map close(Set ids) throws BOSException, EASBizException
    {
        try {
            return getController().close(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����������-User defined method
     *@param param ����
     *@return
     */
    public FDCScheduleInfo getNewData(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getNewData(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���üƻ�ִ��-User defined method
     *@param days ��ǰX��ִ��,Ĭ�ϲ���ǰ
     */
    public void setExecuting(int days) throws BOSException, EASBizException
    {
        try {
            getController().setExecuting(getContext(), days);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getScheduleInfo-User defined method
     *@param pk �ƻ�ID
     *@return
     */
    public FDCScheduleInfo getScheduleInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���üƻ�����-User defined method
     *@param days ��ǰX������
     */
    public void setStart(int days) throws BOSException, EASBizException
    {
        try {
            getController().setStart(getContext(), days);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���÷�����Ϣ-User defined method
     *@param days ��ǰX��֪ͨ
     */
    public void setSendMsg(int days) throws BOSException, EASBizException
    {
        try {
            getController().setSendMsg(getContext(), days);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������-User defined method
     *@param schedule ���ȼƻ�
     */
    public void saveNewTask(FDCScheduleInfo schedule) throws BOSException, EASBizException
    {
        try {
            getController().saveNewTask(getContext(), schedule);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����ύ״̬-User defined method
     *@param billID ���ݱ��
     */
    public void setSubmitStatus(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().setSubmitStatus(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������״̬-User defined method
     *@param billID ���ݱ��
     */
    public void setAudittingStatus(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().setAudittingStatus(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���� -User defined method
     *@param billID ���ݱ��
     */
    public void audit(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������ -User defined method
     *@param billID ���ݱ��
     */
    public void unAudit(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ر�-User defined method
     *@param idSet ID����
     */
    public void unClose(Set idSet) throws BOSException, EASBizException
    {
        try {
            getController().unClose(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ƻ��Ƿ�Ϊ��ִ��״̬-User defined method
     *@param ids ID����
     */
    public void checkScheduleState(Set ids) throws BOSException, EASBizException
    {
        try {
            getController().checkScheduleState(getContext(), ids);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getScheduleInfo-User defined method
     *@param isMainSchedule �Ƿ�����ƻ�
     *@param isAdjust �Ƿ���������������ťʱ��ȡ���ݴ˲�����Ϊtrue
     *@param project ������Ŀ����Ϊnull
     *@param projectSpecial ��Ŀר���Ϊnull
     *@param param ��������
     *@return
     */
    public FDCScheduleInfo getScheduleInfo(boolean isMainSchedule, boolean isAdjust, CurProjectInfo project, ProjectSpecialInfo projectSpecial, Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleInfo(getContext(), isMainSchedule, isAdjust, project, projectSpecial, param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�汾�Ա�-User defined method
     *@param baseVerID ��׼�汾id��Ϊ��ʱȥ�Ƚϰ汾����һ�汾�ƻ�id������ƻ����ƽ���İ汾�Ա�ʱ����ֵΪ��
     *@param compareVerID �Ƚϰ汾id������Ϊ��
     *@return
     */
    public FDCScheduleInfo getSchedule4Compare(String baseVerID, String compareVerID) throws BOSException
    {
        try {
            return getController().getSchedule4Compare(getContext(), baseVerID, compareVerID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������°汾�Ľ��ȼƻ�-User defined method
     *@param curProject ������Ŀ
     *@param projectSpecial ��Ŀר��
     *@return
     */
    public FDCScheduleInfo getNewestVerSchedule(CurProjectInfo curProject, ProjectSpecialInfo projectSpecial) throws BOSException, EASBizException
    {
        try {
            return getController().getNewestVerSchedule(getContext(), curProject, projectSpecial);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����޸��������ԣ������ˡ����β��š�ҵ�������Լ��������ȣ�-User defined method
     *@param param �޸Ĳ���
     *@return
     */
    public Map batchChangeTaskProperties(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().batchChangeTaskProperties(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ƻ�����-User defined method
     *@param mainSchedule mainSchedule
     */
    public void submitMainSchedule(FDCScheduleInfo mainSchedule) throws BOSException, EASBizException
    {
        try {
            getController().submitMainSchedule(getContext(), mainSchedule);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ר��ƻ�����-User defined method
     *@param specialSchedule ר��ƻ�
     */
    public void submitSpecialSchedule(FDCScheduleInfo specialSchedule) throws BOSException, EASBizException
    {
        try {
            getController().submitSpecialSchedule(getContext(), specialSchedule);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ÿ���-User defined method
     *@param billId ID
     */
    public void setCheckVersion(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().setCheckVersion(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}