package com.kingdee.eas.fdc.contract;

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
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class WSContractChangeFacade extends AbstractBizCtrl implements IWSContractChangeFacade
{
    public WSContractChangeFacade()
    {
        super();
        registerInterface(IWSContractChangeFacade.class, this);
    }
    public WSContractChangeFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IWSContractChangeFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6FB76773");
    }
    private WSContractChangeFacadeController getController() throws BOSException
    {
        return (WSContractChangeFacadeController)getBizController();
    }
    /**
     *同步合同变更-User defined method
     *@param str str
     *@return
     */
    public String synContractChange(String str) throws BOSException, EASBizException
    {
        try {
            return getController().synContractChange(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步变更确认-User defined method
     *@param str str
     *@return
     */
    public String synChangeSettlement(String str) throws BOSException, EASBizException
    {
        try {
            return getController().synChangeSettlement(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *合同结算送审单-User defined method
     *@param str str
     *@return
     */
    public String synContractSettlementSubmission(String str) throws BOSException, EASBizException
    {
        try {
            return getController().synContractSettlementSubmission(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *合同结算送审单状态更新-User defined method
     *@param str str
     *@return
     */
    public String synCSSubmissionState(String str) throws BOSException, EASBizException
    {
        try {
            return getController().synCSSubmissionState(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *合同结算送审单附件追加-User defined method
     *@param str str
     *@return
     */
    public String synCSSubmissionAttach(String str) throws BOSException, EASBizException
    {
        try {
            return getController().synCSSubmissionAttach(getContext(), str);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}