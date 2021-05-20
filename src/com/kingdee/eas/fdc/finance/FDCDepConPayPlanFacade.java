package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;
import java.util.Set;

public class FDCDepConPayPlanFacade extends AbstractBizCtrl implements IFDCDepConPayPlanFacade
{
    public FDCDepConPayPlanFacade()
    {
        super();
        registerInterface(IFDCDepConPayPlanFacade.class, this);
    }
    public FDCDepConPayPlanFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCDepConPayPlanFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7916EFE2");
    }
    private FDCDepConPayPlanFacadeController getController() throws BOSException
    {
        return (FDCDepConPayPlanFacadeController)getBizController();
    }
    /**
     *��ȡ��Ŀ�ƻ�ִ�����������-User defined method
     *@param prjIds ������Ŀid
     *@param param ����
     *@return
     */
    public Map getProjectPayPlanExeData(Set prjIds, Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectPayPlanExeData(getContext(), prjIds, param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ͬ����ͬ����ƻ�-User defined method
     *@param id id
     *@param isAudit isAudit
     */
    public void autoUpdateConPayPlan(String id, boolean isAudit) throws BOSException, EASBizException
    {
        try {
            getController().autoUpdateConPayPlan(getContext(), id, isAudit);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}