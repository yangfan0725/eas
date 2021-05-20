package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayReqInvoiceEntryCollection extends AbstractObjectCollection 
{
    public PayReqInvoiceEntryCollection()
    {
        super(PayReqInvoiceEntryInfo.class);
    }
    public boolean add(PayReqInvoiceEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayReqInvoiceEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayReqInvoiceEntryInfo item)
    {
        return removeObject(item);
    }
    public PayReqInvoiceEntryInfo get(int index)
    {
        return(PayReqInvoiceEntryInfo)getObject(index);
    }
    public PayReqInvoiceEntryInfo get(Object key)
    {
        return(PayReqInvoiceEntryInfo)getObject(key);
    }
    public void set(int index, PayReqInvoiceEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayReqInvoiceEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayReqInvoiceEntryInfo item)
    {
        return super.indexOf(item);
    }
}