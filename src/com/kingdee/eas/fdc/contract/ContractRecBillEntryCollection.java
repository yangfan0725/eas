package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractRecBillEntryCollection extends AbstractObjectCollection 
{
    public ContractRecBillEntryCollection()
    {
        super(ContractRecBillEntryInfo.class);
    }
    public boolean add(ContractRecBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractRecBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractRecBillEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractRecBillEntryInfo get(int index)
    {
        return(ContractRecBillEntryInfo)getObject(index);
    }
    public ContractRecBillEntryInfo get(Object key)
    {
        return(ContractRecBillEntryInfo)getObject(key);
    }
    public void set(int index, ContractRecBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractRecBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractRecBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}