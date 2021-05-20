package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.Set;

public class ScheduleFacade extends AbstractBizCtrl implements IScheduleFacade
{
    public ScheduleFacade()
    {
        super();
        registerInterface(IScheduleFacade.class, this);
    }
    public ScheduleFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IScheduleFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("32D25F67");
    }
    private ScheduleFacadeController getController() throws BOSException
    {
        return (ScheduleFacadeController)getBizController();
    }
    /**
     *���㸸�ڵ��깤����-User defined method
     *@param wbsIds WBSID����
     */
    public void reCalcParentTaskComplete(Set wbsIds) throws BOSException, EASBizException
    {
        try {
            getController().reCalcParentTaskComplete(getContext(), wbsIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ�ñ����ż��������ŵĽ��ȼƻ�-User defined method
     *@param scheduleId ���ȼƻ�ID
     *@return
     */
    public FDCScheduleInfo getOtherDeptSchedule(String scheduleId) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherDeptSchedule(getContext(), scheduleId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ݹ�����¼�뷴д�����깤������-User defined method
     *@param wbsId WBSId
     */
    public void reCalLoadFromTaskLoad(String wbsId) throws BOSException, EASBizException
    {
        try {
            getController().reCalLoadFromTaskLoad(getContext(), wbsId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ͬ�������깤�������ֶ�-User defined method
     *@param taskId ����ID
     *@param wbsId WBSID
     *@param param ����
     */
    public void synchronizeTask(Set taskId, Set wbsId, String param) throws BOSException, EASBizException
    {
        try {
            getController().synchronizeTask(getContext(), taskId, wbsId, param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����������깤����-User defined method
     *@param wbsIds WBS����
     *@param param ������������
     */
    public void upateCompleteDate(Set wbsIds, String param) throws BOSException, EASBizException
    {
        try {
            getController().upateCompleteDate(getContext(), wbsIds, param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������-User defined method
     *@param scheduleId Ҫ����ļƻ�id�� ����ʱ�������Ϊ��
     *@param projectId ������Ŀid
     *@param taskTypeId ���������뻹��ר��������
     *@param parentWbsId ������������ר��ʱ����ֵ����Ϊ��
     *@param tasks ����������
     *@return
     */
    public FDCScheduleInfo importTasks(String scheduleId, String projectId, String taskTypeId, String parentWbsId, FDCScheduleTaskCollection tasks) throws BOSException, EASBizException
    {
        try {
            return getController().importTasks(getContext(), scheduleId, projectId, taskTypeId, parentWbsId, tasks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}