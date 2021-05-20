package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;

public class ProjectReportFacade extends AbstractBizCtrl implements IProjectReportFacade
{
    public ProjectReportFacade()
    {
        super();
        registerInterface(IProjectReportFacade.class, this);
    }
    public ProjectReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EEF72511");
    }
    private ProjectReportFacadeController getController() throws BOSException
    {
        return (ProjectReportFacadeController)getBizController();
    }
    /**
     *��ȡ���ڼ���������Ĺ��ܣ�����һ������Map��Map����Ҫ����startDate��endDate��project��projectSpecial-User defined method
     *@param param �÷�������Ҫ����һ��param Map��������Ҫ���ݣ��ƻ���ʼʱ�䣬�ƻ����ʱ�䣬��Ŀ����Ŀר��
     *@return
     */
    public Map getPeriodTask(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getPeriodTask(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}