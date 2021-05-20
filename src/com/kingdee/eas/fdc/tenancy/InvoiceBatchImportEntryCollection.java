package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvoiceBatchImportEntryCollection extends AbstractObjectCollection 
{
    public InvoiceBatchImportEntryCollection()
    {
        super(InvoiceBatchImportEntryInfo.class);
    }
    public boolean add(InvoiceBatchImportEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvoiceBatchImportEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvoiceBatchImportEntryInfo item)
    {
        return removeObject(item);
    }
    public InvoiceBatchImportEntryInfo get(int index)
    {
        return(InvoiceBatchImportEntryInfo)getObject(index);
    }
    public InvoiceBatchImportEntryInfo get(Object key)
    {
        return(InvoiceBatchImportEntryInfo)getObject(key);
    }
    public void set(int index, InvoiceBatchImportEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvoiceBatchImportEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvoiceBatchImportEntryInfo item)
    {
        return super.indexOf(item);
    }
}