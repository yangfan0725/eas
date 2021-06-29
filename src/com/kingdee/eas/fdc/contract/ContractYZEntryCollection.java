package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractYZEntryCollection extends AbstractObjectCollection 
{
    public ContractYZEntryCollection()
    {
        super(ContractYZEntryInfo.class);
    }
    public boolean add(ContractYZEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractYZEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractYZEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractYZEntryInfo get(int index)
    {
        return(ContractYZEntryInfo)getObject(index);
    }
    public ContractYZEntryInfo get(Object key)
    {
        return(ContractYZEntryInfo)getObject(key);
    }
    public void set(int index, ContractYZEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractYZEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractYZEntryInfo item)
    {
        return super.indexOf(item);
    }
}