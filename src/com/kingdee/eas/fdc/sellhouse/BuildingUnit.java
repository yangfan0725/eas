package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class BuildingUnit extends DataBase implements IBuildingUnit
{
    public BuildingUnit()
    {
        super();
        registerInterface(IBuildingUnit.class, this);
    }
    public BuildingUnit(Context ctx)
    {
        super(ctx);
        registerInterface(IBuildingUnit.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FDBCDCB3");
    }
    private BuildingUnitController getController() throws BOSException
    {
        return (BuildingUnitController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public BuildingUnitInfo getBuildingUnitInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingUnitInfo(getContext(), pk);
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
    public BuildingUnitInfo getBuildingUnitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingUnitInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public BuildingUnitInfo getBuildingUnitInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildingUnitInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public BuildingUnitCollection getBuildingUnitCollection() throws BOSException
    {
        try {
            return getController().getBuildingUnitCollection(getContext());
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
    public BuildingUnitCollection getBuildingUnitCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBuildingUnitCollection(getContext(), view);
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
    public BuildingUnitCollection getBuildingUnitCollection(String oql) throws BOSException
    {
        try {
            return getController().getBuildingUnitCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���µ�Ԫ-User defined method
     *@param building ¥��
     */
    public void updateBuildingUnitbyBuild(BuildingInfo building) throws BOSException, EASBizException
    {
        try {
            getController().updateBuildingUnitbyBuild(getContext(), building);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}