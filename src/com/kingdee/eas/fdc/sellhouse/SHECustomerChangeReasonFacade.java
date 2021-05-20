package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;

public class SHECustomerChangeReasonFacade extends AbstractBizCtrl implements ISHECustomerChangeReasonFacade
{
    public SHECustomerChangeReasonFacade()
    {
        super();
        registerInterface(ISHECustomerChangeReasonFacade.class, this);
    }
    public SHECustomerChangeReasonFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISHECustomerChangeReasonFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3B7F1021");
    }
    private SHECustomerChangeReasonFacadeController getController() throws BOSException
    {
        return (SHECustomerChangeReasonFacadeController)getBizController();
    }
    /**
     *�����䶯��¼-User defined method
     *@param company ��˾����
     *@param reason ���ԭ��
     *@param sheCustomer �ͻ�
     *@param propertyConsultant ��ҵ����
     */
    public void addNewMessage(IObjectValue company, String reason, IObjectValue sheCustomer, IObjectValue propertyConsultant) throws BOSException, SellHouseException, EASBizException
    {
        try {
            getController().addNewMessage(getContext(), company, reason, sheCustomer, propertyConsultant);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}