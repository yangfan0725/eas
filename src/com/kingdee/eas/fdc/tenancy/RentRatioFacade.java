package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.tenancy.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;

public class RentRatioFacade extends AbstractBizCtrl implements IRentRatioFacade
{
    public RentRatioFacade()
    {
        super();
        registerInterface(IRentRatioFacade.class, this);
    }
    public RentRatioFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IRentRatioFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BB055EC3");
    }
    private RentRatioFacadeController getController() throws BOSException
    {
        return (RentRatioFacadeController)getBizController();
    }
    /**
     *ȡ�����޺�ͬ����-User defined method
     *@param param ��������
     *@return
     */
    public Map getRentRatioInfo(Map param) throws BOSException
    {
        try {
            return getController().getRentRatioInfo(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ������Ϣֵ-User defined method
     *@param customerParams ���Ʋ���
     *@return
     */
    public Map getBatchFilterInfo(Map customerParams) throws BOSException
    {
        try {
            return getController().getBatchFilterInfo(getContext(), customerParams);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}