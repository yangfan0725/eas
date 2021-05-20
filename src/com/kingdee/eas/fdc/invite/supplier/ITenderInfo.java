package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface ITenderInfo extends IFDCBill
{
    public TenderInfoInfo getTenderInfoInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TenderInfoInfo getTenderInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TenderInfoInfo getTenderInfoInfo(String oql) throws BOSException, EASBizException;
    public TenderInfoCollection getTenderInfoCollection() throws BOSException;
    public TenderInfoCollection getTenderInfoCollection(EntityViewInfo view) throws BOSException;
    public TenderInfoCollection getTenderInfoCollection(String oql) throws BOSException;
    public void approveReport(BOSUuid billId) throws BOSException, EASBizException;
    public void rejectReport(BOSUuid billId) throws BOSException, EASBizException;
}