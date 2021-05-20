package com.kingdee.eas.fdc.sellhouse;

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

public interface IContractInvoiceEntry extends ICoreBillEntryBase
{
    public ContractInvoiceEntryInfo getContractInvoiceEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractInvoiceEntryInfo getContractInvoiceEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractInvoiceEntryInfo getContractInvoiceEntryInfo(String oql) throws BOSException, EASBizException;
    public ContractInvoiceEntryCollection getContractInvoiceEntryCollection() throws BOSException;
    public ContractInvoiceEntryCollection getContractInvoiceEntryCollection(EntityViewInfo view) throws BOSException;
    public ContractInvoiceEntryCollection getContractInvoiceEntryCollection(String oql) throws BOSException;
}