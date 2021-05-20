package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvoiceBatchImportCollection extends AbstractObjectCollection 
{
    public InvoiceBatchImportCollection()
    {
        super(InvoiceBatchImportInfo.class);
    }
    public boolean add(InvoiceBatchImportInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvoiceBatchImportCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvoiceBatchImportInfo item)
    {
        return removeObject(item);
    }
    public InvoiceBatchImportInfo get(int index)
    {
        return(InvoiceBatchImportInfo)getObject(index);
    }
    public InvoiceBatchImportInfo get(Object key)
    {
        return(InvoiceBatchImportInfo)getObject(key);
    }
    public void set(int index, InvoiceBatchImportInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvoiceBatchImportInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvoiceBatchImportInfo item)
    {
        return super.indexOf(item);
    }
}