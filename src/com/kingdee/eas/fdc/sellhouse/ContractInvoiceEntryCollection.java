package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractInvoiceEntryCollection extends AbstractObjectCollection 
{
    public ContractInvoiceEntryCollection()
    {
        super(ContractInvoiceEntryInfo.class);
    }
    public boolean add(ContractInvoiceEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractInvoiceEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractInvoiceEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractInvoiceEntryInfo get(int index)
    {
        return(ContractInvoiceEntryInfo)getObject(index);
    }
    public ContractInvoiceEntryInfo get(Object key)
    {
        return(ContractInvoiceEntryInfo)getObject(key);
    }
    public void set(int index, ContractInvoiceEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractInvoiceEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractInvoiceEntryInfo item)
    {
        return super.indexOf(item);
    }
}