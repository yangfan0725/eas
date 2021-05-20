package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IReturnTenancyBill extends IFDCBill
{
    public ReturnTenancyBillCollection getReturnTenancyBillCollection() throws BOSException;
    public ReturnTenancyBillCollection getReturnTenancyBillCollection(EntityViewInfo view) throws BOSException;
    public ReturnTenancyBillCollection getReturnTenancyBillCollection(String oql) throws BOSException;
    public ReturnTenancyBillInfo getReturnTenancyBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ReturnTenancyBillInfo getReturnTenancyBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ReturnTenancyBillInfo getReturnTenancyBillInfo(String oql) throws BOSException, EASBizException;
}