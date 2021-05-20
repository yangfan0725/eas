package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractMarketEntryCollection extends AbstractObjectCollection 
{
    public ContractMarketEntryCollection()
    {
        super(ContractMarketEntryInfo.class);
    }
    public boolean add(ContractMarketEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractMarketEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractMarketEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractMarketEntryInfo get(int index)
    {
        return(ContractMarketEntryInfo)getObject(index);
    }
    public ContractMarketEntryInfo get(Object key)
    {
        return(ContractMarketEntryInfo)getObject(key);
    }
    public void set(int index, ContractMarketEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractMarketEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractMarketEntryInfo item)
    {
        return super.indexOf(item);
    }
}