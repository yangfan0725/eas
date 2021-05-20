package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.app.CommRptBaseController;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RoomAccountReport1FacadeController extends CommRptBaseController
{
    public RptParams createTempTable(Context ctx, RptParams params) throws BOSException, EASBizException, RemoteException;
}