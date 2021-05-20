package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IProjectMonthReport extends IOpReportBase
{
    public ProjectMonthReportInfo getProjectMonthReportInfo(String oql) throws BOSException, EASBizException;
    public ProjectMonthReportInfo getProjectMonthReportInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectMonthReportInfo getProjectMonthReportInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProjectMonthReportCollection getProjectMonthReportCollection(String oql) throws BOSException;
    public ProjectMonthReportCollection getProjectMonthReportCollection(EntityViewInfo view) throws BOSException;
    public ProjectMonthReportCollection getProjectMonthReportCollection() throws BOSException;
}