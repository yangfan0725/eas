package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractWTMarketEntryCollection extends AbstractObjectCollection 
{
    public ContractWTMarketEntryCollection()
    {
        super(ContractWTMarketEntryInfo.class);
    }
    public boolean add(ContractWTMarketEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractWTMarketEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractWTMarketEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractWTMarketEntryInfo get(int index)
    {
        return(ContractWTMarketEntryInfo)getObject(index);
    }
    public ContractWTMarketEntryInfo get(Object key)
    {
        return(ContractWTMarketEntryInfo)getObject(key);
    }
    public void set(int index, ContractWTMarketEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractWTMarketEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractWTMarketEntryInfo item)
    {
        return super.indexOf(item);
    }
}