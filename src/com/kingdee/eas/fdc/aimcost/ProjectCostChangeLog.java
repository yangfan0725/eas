package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.framework.ICoreBase;

public class ProjectCostChangeLog extends CoreBase implements IProjectCostChangeLog
{
    public ProjectCostChangeLog()
    {
        super();
        registerInterface(IProjectCostChangeLog.class, this);
    }
    public ProjectCostChangeLog(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectCostChangeLog.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7839CD65");
    }
    private ProjectCostChangeLogController getController() throws BOSException
    {
        return (ProjectCostChangeLogController)getBizController();
    }
    /**
     *����仯��־-User defined method
     *@param prjSet ��Ŀ����
     *@return
     */
    public boolean insertLog(Set prjSet) throws BOSException
    {
        try {
            return getController().insertLog(getContext(), prjSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ڴ���ɱ��仯�����Ϊ�ɱ����仯-User defined method
     *@param prjSet ��Ŀ����
     *@return
     */
    public boolean updateLog(Set prjSet) throws BOSException
    {
        try {
            return getController().updateLog(getContext(), prjSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ɾ����ɵ���־-User defined method
     *@return
     */
    public boolean deleteLog() throws BOSException
    {
        try {
            return getController().deleteLog(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ�ɱ��仯�Ĺ�����Ŀ-User defined method
     *@param prjSet ��Ŀ����
     *@return
     */
    public Set getChangePrjs(Set prjSet) throws BOSException
    {
        try {
            return getController().getChangePrjs(getContext(), prjSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ�ɱ��仯�����й�����Ŀ-User defined method
     *@return
     */
    public Set getAllChangePrjs() throws BOSException
    {
        try {
            return getController().getAllChangePrjs(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}