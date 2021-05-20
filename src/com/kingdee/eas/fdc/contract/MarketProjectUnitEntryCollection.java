package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketProjectUnitEntryCollection extends AbstractObjectCollection 
{
    public MarketProjectUnitEntryCollection()
    {
        super(MarketProjectUnitEntryInfo.class);
    }
    public boolean add(MarketProjectUnitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketProjectUnitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketProjectUnitEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketProjectUnitEntryInfo get(int index)
    {
        return(MarketProjectUnitEntryInfo)getObject(index);
    }
    public MarketProjectUnitEntryInfo get(Object key)
    {
        return(MarketProjectUnitEntryInfo)getObject(key);
    }
    public void set(int index, MarketProjectUnitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketProjectUnitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketProjectUnitEntryInfo item)
    {
        return super.indexOf(item);
    }
}