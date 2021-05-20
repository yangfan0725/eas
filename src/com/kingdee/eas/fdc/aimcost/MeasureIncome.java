package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class MeasureIncome extends FDCBill implements IMeasureIncome
{
    public MeasureIncome()
    {
        super();
        registerInterface(IMeasureIncome.class, this);
    }
    public MeasureIncome(Context ctx)
    {
        super(ctx);
        registerInterface(IMeasureIncome.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C1C610B0");
    }
    private MeasureIncomeController getController() throws BOSException
    {
        return (MeasureIncomeController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public MeasureIncomeInfo getMeasureIncomeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureIncomeInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public MeasureIncomeInfo getMeasureIncomeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMeasureIncomeInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public MeasureIncomeCollection getMeasureIncomeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMeasureIncomeCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public MeasureIncomeCollection getMeasureIncomeCollection() throws BOSException
    {
        try {
            return getController().getMeasureIncomeCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public MeasureIncomeCollection getMeasureIncomeCollection(String oql) throws BOSException
    {
        try {
            return getController().getMeasureIncomeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��������Ȼ�ȡ���ݷ���-User defined method
     *@param params �����б�
     *@return
     */
    public Map getImportData(Map params) throws BOSException, EASBizException
    {
        try {
            return getController().getImportData(getContext(), params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}