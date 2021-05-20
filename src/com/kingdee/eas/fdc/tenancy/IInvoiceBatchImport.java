package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IInvoiceBatchImport extends IFDCBill
{
    public InvoiceBatchImportInfo getInvoiceBatchImportInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InvoiceBatchImportInfo getInvoiceBatchImportInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InvoiceBatchImportInfo getInvoiceBatchImportInfo(String oql) throws BOSException, EASBizException;
    public InvoiceBatchImportCollection getInvoiceBatchImportCollection() throws BOSException;
    public InvoiceBatchImportCollection getInvoiceBatchImportCollection(EntityViewInfo view) throws BOSException;
    public InvoiceBatchImportCollection getInvoiceBatchImportCollection(String oql) throws BOSException;
    public Map queryInvoiceData(Map paramMap) throws BOSException, EASBizException;
}