package com.kingdee.eas.fdc.schedule.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.List;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.IFDCTreeBaseData;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseData;
import com.kingdee.eas.fdc.schedule.report.app.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class ScheduleReportOrg extends FDCTreeBaseData implements IScheduleReportOrg
{
    public ScheduleReportOrg()
    {
        super();
        registerInterface(IScheduleReportOrg.class, this);
    }
    public ScheduleReportOrg(Context ctx)
    {
        super(ctx);
        registerInterface(IScheduleReportOrg.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("714FB2DB");
    }
    private ScheduleReportOrgController getController() throws BOSException
    {
        return (ScheduleReportOrgController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ScheduleReportOrgInfo getScheduleReportOrgInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleReportOrgInfo(getContext(), pk);
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
    public ScheduleReportOrgInfo getScheduleReportOrgInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleReportOrgInfo(getContext(), pk, selector);
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
    public ScheduleReportOrgInfo getScheduleReportOrgInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleReportOrgInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ScheduleReportOrgCollection getScheduleReportOrgCollection() throws BOSException
    {
        try {
            return getController().getScheduleReportOrgCollection(getContext());
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
    public ScheduleReportOrgCollection getScheduleReportOrgCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getScheduleReportOrgCollection(getContext(), view);
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
    public ScheduleReportOrgCollection getScheduleReportOrgCollection(String oql) throws BOSException
    {
        try {
            return getController().getScheduleReportOrgCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���½��ȱ�������-User defined method
     *@param scheduleReportCollection ���ȱ��漯��,�����޸ģ����ӣ�δɾ���ı�������
     *@param deleteScheduleReportDataList ��ɾ���ı�������
     */
    public void updateScheduleReportData(List scheduleReportCollection, List deleteScheduleReportDataList) throws BOSException, EASBizException
    {
        try {
            getController().updateScheduleReportData(getContext(), scheduleReportCollection, deleteScheduleReportDataList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��鼯����֯�Ƿ���ڣ������������-User defined method
     *@return
     */
    public boolean checkDefaultGroupOrg() throws BOSException, EASBizException
    {
        try {
            return getController().checkDefaultGroupOrg(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}