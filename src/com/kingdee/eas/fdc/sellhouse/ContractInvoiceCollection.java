package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractInvoiceCollection extends AbstractObjectCollection 
{
    public ContractInvoiceCollection()
    {
        super(ContractInvoiceInfo.class);
    }
    public boolean add(ContractInvoiceInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractInvoiceCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractInvoiceInfo item)
    {
        return removeObject(item);
    }
    public ContractInvoiceInfo get(int index)
    {
        return(ContractInvoiceInfo)getObject(index);
    }
    public ContractInvoiceInfo get(Object key)
    {
        return(ContractInvoiceInfo)getObject(key);
    }
    public void set(int index, ContractInvoiceInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractInvoiceInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractInvoiceInfo item)
    {
        return super.indexOf(item);
    }
}