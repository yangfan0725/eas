package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractWTInvoiceEntryCollection extends AbstractObjectCollection 
{
    public ContractWTInvoiceEntryCollection()
    {
        super(ContractWTInvoiceEntryInfo.class);
    }
    public boolean add(ContractWTInvoiceEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractWTInvoiceEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractWTInvoiceEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractWTInvoiceEntryInfo get(int index)
    {
        return(ContractWTInvoiceEntryInfo)getObject(index);
    }
    public ContractWTInvoiceEntryInfo get(Object key)
    {
        return(ContractWTInvoiceEntryInfo)getObject(key);
    }
    public void set(int index, ContractWTInvoiceEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractWTInvoiceEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractWTInvoiceEntryInfo item)
    {
        return super.indexOf(item);
    }
}