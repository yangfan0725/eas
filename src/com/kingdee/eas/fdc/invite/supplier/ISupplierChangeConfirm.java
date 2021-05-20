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

public interface ISupplierChangeConfirm extends IFDCBill
{
    public SupplierChangeConfirmInfo getSupplierChangeConfirmInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SupplierChangeConfirmInfo getSupplierChangeConfirmInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SupplierChangeConfirmInfo getSupplierChangeConfirmInfo(String oql) throws BOSException, EASBizException;
    public SupplierChangeConfirmCollection getSupplierChangeConfirmCollection() throws BOSException;
    public SupplierChangeConfirmCollection getSupplierChangeConfirmCollection(EntityViewInfo view) throws BOSException;
    public SupplierChangeConfirmCollection getSupplierChangeConfirmCollection(String oql) throws BOSException;
    public void changeSupplierInfo(BOSUuid billID) throws BOSException, EASBizException;
}