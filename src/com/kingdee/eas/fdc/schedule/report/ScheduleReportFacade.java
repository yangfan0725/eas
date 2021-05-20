package com.kingdee.eas.fdc.schedule.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.eas.fdc.schedule.report.app.*;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class ScheduleReportFacade extends AbstractBizCtrl implements IScheduleReportFacade
{
    public ScheduleReportFacade()
    {
        super();
        registerInterface(IScheduleReportFacade.class, this);
    }
    public ScheduleReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IScheduleReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("19531543");
    }
    private ScheduleReportFacadeController getController() throws BOSException
    {
        return (ScheduleReportFacadeController)getBizController();
    }
    /**
     *��ȡ���ȱ�������-User defined method
     *@param orgIds ���˵���֯���뼯��
     *@param dates ��ѯ�Ŀ�ʼ���ڼ���������
     *@param scheduleType �����˽ڵ㡢����ƻ���ר��ƻ���
     *@return
     */
    public Map getScheduleReportData(Map orgIds, Map dates, String scheduleType) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleReportData(getContext(), orgIds, dates, scheduleType);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ������ר��ƻ�����Ϣ-User defined method
     *@param dates ��ʼ���ںͽ�������
     *@param projectID ר���������̵�ID
     *@return
     */
    public Map getScheduleExpertReportData(Map dates, String projectID) throws BOSException
    {
        try {
            return getController().getScheduleExpertReportData(getContext(), dates, projectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ����������Ϣ-User defined method
     *@param paramMap ������
     *@return
     */
    public Map getScheduleTaskInfo(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleTaskInfo(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}