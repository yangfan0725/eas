package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBillReceiveEntryCollection extends AbstractObjectCollection 
{
    public ContractBillReceiveEntryCollection()
    {
        super(ContractBillReceiveEntryInfo.class);
    }
    public boolean add(ContractBillReceiveEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBillReceiveEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBillReceiveEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractBillReceiveEntryInfo get(int index)
    {
        return(ContractBillReceiveEntryInfo)getObject(index);
    }
    public ContractBillReceiveEntryInfo get(Object key)
    {
        return(ContractBillReceiveEntryInfo)getObject(key);
    }
    public void set(int index, ContractBillReceiveEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBillReceiveEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBillReceiveEntryInfo item)
    {
        return super.indexOf(item);
    }
}