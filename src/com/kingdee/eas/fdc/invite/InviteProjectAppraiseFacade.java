package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;

public class InviteProjectAppraiseFacade extends AbstractBizCtrl implements IInviteProjectAppraiseFacade
{
    public InviteProjectAppraiseFacade()
    {
        super();
        registerInterface(IInviteProjectAppraiseFacade.class, this);
    }
    public InviteProjectAppraiseFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IInviteProjectAppraiseFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9B0FA931");
    }
    private InviteProjectAppraiseFacadeController getController() throws BOSException
    {
        return (InviteProjectAppraiseFacadeController)getBizController();
    }
    /**
     *�ж��Ƿ��ܹ�����ר������-User defined method
     *@param inviteProjectID �б�����ID
     */
    public void checkCanAddExpertAppraise(String inviteProjectID) throws BOSException, EASBizException
    {
        try {
            getController().checkCanAddExpertAppraise(getContext(), inviteProjectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�Զ������б�����������-User defined method
     */
    public void aotuSetInviteStatusAppraise() throws BOSException, EASBizException
    {
        try {
            getController().aotuSetInviteStatusAppraise(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}