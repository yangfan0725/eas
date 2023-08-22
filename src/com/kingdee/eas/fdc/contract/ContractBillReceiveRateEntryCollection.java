package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBillReceiveRateEntryCollection extends AbstractObjectCollection 
{
    public ContractBillReceiveRateEntryCollection()
    {
        super(ContractBillReceiveRateEntryInfo.class);
    }
    public boolean add(ContractBillReceiveRateEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBillReceiveRateEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBillReceiveRateEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractBillReceiveRateEntryInfo get(int index)
    {
        return(ContractBillReceiveRateEntryInfo)getObject(index);
    }
    public ContractBillReceiveRateEntryInfo get(Object key)
    {
        return(ContractBillReceiveRateEntryInfo)getObject(key);
    }
    public void set(int index, ContractBillReceiveRateEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBillReceiveRateEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBillReceiveRateEntryInfo item)
    {
        return super.indexOf(item);
    }
}