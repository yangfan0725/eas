package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface ISupplierChangeEntry extends ICoreBillEntryBase
{
    public SupplierChangeEntryInfo getSupplierChangeEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SupplierChangeEntryInfo getSupplierChangeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SupplierChangeEntryInfo getSupplierChangeEntryInfo(String oql) throws BOSException, EASBizException;
    public SupplierChangeEntryCollection getSupplierChangeEntryCollection() throws BOSException;
    public SupplierChangeEntryCollection getSupplierChangeEntryCollection(EntityViewInfo view) throws BOSException;
    public SupplierChangeEntryCollection getSupplierChangeEntryCollection(String oql) throws BOSException;
}