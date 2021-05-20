package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface CurProjectController extends ProjectController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public CurProjectInfo getCurProjectInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public CurProjectInfo getCurProjectInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public CurProjectInfo getCurProjectInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, CurProjectInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, CurProjectInfo model) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, CurProjectInfo model) throws BOSException, EASBizException, RemoteException;
    public void updatePartial(Context ctx, CurProjectInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, CurProjectInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException, RemoteException;
    public CurProjectCollection getCurProjectCollection(Context ctx) throws BOSException, RemoteException;
    public CurProjectCollection getCurProjectCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public CurProjectCollection getCurProjectCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public boolean enabled(Context ctx, IObjectPK cpPK) throws BOSException, EASBizException, RemoteException;
    public boolean disEnabled(Context ctx, IObjectPK cpPK) throws BOSException, EASBizException, RemoteException;
    public int idxRefresh(Context ctx, String projId) throws BOSException, RemoteException;
    public void traceVoucher4Flow(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException, RemoteException;
    public void traceVoucher4Get(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException, RemoteException;
    public void changeStatus(Context ctx, String projectId, String changeCase) throws BOSException, EASBizException, RemoteException;
    public int idxRefresh(Context ctx, String projId, String productId, List apportions) throws BOSException, RemoteException;
    public boolean setProjectTpe(Context ctx, Map projectTypeMap) throws BOSException, EASBizException, RemoteException;
    public String synchronousProjects(Context ctx, Map projectMap) throws BOSException, EASBizException, RemoteException;
    public void updateSortNo(Context ctx, String cuId) throws BOSException, RemoteException;
    public void setIsDevPrj(Context ctx, IObjectPK pk, boolean isDevPrj) throws BOSException, EASBizException, RemoteException;
}