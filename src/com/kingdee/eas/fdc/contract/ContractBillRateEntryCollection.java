package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBillRateEntryCollection extends AbstractObjectCollection 
{
    public ContractBillRateEntryCollection()
    {
        super(ContractBillRateEntryInfo.class);
    }
    public boolean add(ContractBillRateEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBillRateEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBillRateEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractBillRateEntryInfo get(int index)
    {
        return(ContractBillRateEntryInfo)getObject(index);
    }
    public ContractBillRateEntryInfo get(Object key)
    {
        return(ContractBillRateEntryInfo)getObject(key);
    }
    public void set(int index, ContractBillRateEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBillRateEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBillRateEntryInfo item)
    {
        return super.indexOf(item);
    }
}