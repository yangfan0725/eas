package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface ICurProject extends IProject
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public CurProjectInfo getCurProjectInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CurProjectInfo getCurProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CurProjectInfo getCurProjectInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(CurProjectInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, CurProjectInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, CurProjectInfo model) throws BOSException, EASBizException;
    public void updatePartial(CurProjectInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, CurProjectInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public CurProjectCollection getCurProjectCollection() throws BOSException;
    public CurProjectCollection getCurProjectCollection(EntityViewInfo view) throws BOSException;
    public CurProjectCollection getCurProjectCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public boolean enabled(IObjectPK cpPK) throws BOSException, EASBizException;
    public boolean disEnabled(IObjectPK cpPK) throws BOSException, EASBizException;
    public int idxRefresh(String projId) throws BOSException;
    public void traceVoucher4Flow(IObjectPK projectPK) throws BOSException, EASBizException;
    public void traceVoucher4Get(IObjectPK projectPK) throws BOSException, EASBizException;
    public void changeStatus(String projectId, String changeCase) throws BOSException, EASBizException;
    public int idxRefresh(String projId, String productId, List apportions) throws BOSException;
    public boolean setProjectTpe(Map projectTypeMap) throws BOSException, EASBizException;
    public String synchronousProjects(Map projectMap) throws BOSException, EASBizException;
    public void updateSortNo(String cuId) throws BOSException;
    public void setIsDevPrj(IObjectPK pk, boolean isDevPrj) throws BOSException, EASBizException;
}