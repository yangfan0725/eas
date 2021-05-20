package com.kingdee.eas.fdc.schedule;

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
import java.util.Set;
import com.kingdee.eas.fdc.schedule.framework.IScheduleBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IFDCSchedule extends IScheduleBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public FDCScheduleInfo getFDCScheduleInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCScheduleInfo getFDCScheduleInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCScheduleInfo getFDCScheduleInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(FDCScheduleInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, FDCScheduleInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, FDCScheduleInfo model) throws BOSException, EASBizException;
    public void updatePartial(FDCScheduleInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, FDCScheduleInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public FDCScheduleCollection getFDCScheduleCollection() throws BOSException;
    public FDCScheduleCollection getFDCScheduleCollection(EntityViewInfo view) throws BOSException;
    public FDCScheduleCollection getFDCScheduleCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public Map cancel(Set ids) throws BOSException, EASBizException;
    public Map cancelCancel(Set ids) throws BOSException, EASBizException;
    public Map audit(Set ids) throws BOSException, EASBizException;
    public Map unAudit(Set ids) throws BOSException, EASBizException;
    public Map close(Set ids) throws BOSException, EASBizException;
    public FDCScheduleInfo getNewData(Map param) throws BOSException, EASBizException;
    public void setExecuting(int days) throws BOSException, EASBizException;
    public FDCScheduleInfo getScheduleInfo(IObjectPK pk) throws BOSException, EASBizException;
    public void setStart(int days) throws BOSException, EASBizException;
    public void setSendMsg(int days) throws BOSException, EASBizException;
    public void saveNewTask(FDCScheduleInfo schedule) throws BOSException, EASBizException;
    public void setSubmitStatus(BOSUuid billID) throws BOSException, EASBizException;
    public void setAudittingStatus(BOSUuid billID) throws BOSException, EASBizException;
    public void audit(BOSUuid billID) throws BOSException, EASBizException;
    public void unAudit(BOSUuid billID) throws BOSException, EASBizException;
    public void unClose(Set idSet) throws BOSException, EASBizException;
    public void checkScheduleState(Set ids) throws BOSException, EASBizException;
    public FDCScheduleInfo getScheduleInfo(boolean isMainSchedule, boolean isAdjust, CurProjectInfo project, ProjectSpecialInfo projectSpecial, Map param) throws BOSException, EASBizException;
    public FDCScheduleInfo getSchedule4Compare(String baseVerID, String compareVerID) throws BOSException;
    public FDCScheduleInfo getNewestVerSchedule(CurProjectInfo curProject, ProjectSpecialInfo projectSpecial) throws BOSException, EASBizException;
    public Map batchChangeTaskProperties(Map param) throws BOSException, EASBizException;
    public void submitMainSchedule(FDCScheduleInfo mainSchedule) throws BOSException, EASBizException;
    public void submitSpecialSchedule(FDCScheduleInfo specialSchedule) throws BOSException, EASBizException;
    public void setCheckVersion(BOSUuid billId) throws BOSException, EASBizException;
}