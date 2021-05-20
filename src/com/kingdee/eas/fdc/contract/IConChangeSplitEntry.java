package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.IFDCSplitBillEntry;
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

public interface IConChangeSplitEntry extends IFDCSplitBillEntry
{
    public ConChangeSplitEntryInfo getConChangeSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ConChangeSplitEntryInfo getConChangeSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ConChangeSplitEntryInfo getConChangeSplitEntryInfo(String oql) throws BOSException, EASBizException;
    public ConChangeSplitEntryCollection getConChangeSplitEntryCollection() throws BOSException;
    public ConChangeSplitEntryCollection getConChangeSplitEntryCollection(EntityViewInfo view) throws BOSException;
    public ConChangeSplitEntryCollection getConChangeSplitEntryCollection(String oql) throws BOSException;
}