package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketProjectEntryCollection extends AbstractObjectCollection 
{
    public MarketProjectEntryCollection()
    {
        super(MarketProjectEntryInfo.class);
    }
    public boolean add(MarketProjectEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketProjectEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketProjectEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketProjectEntryInfo get(int index)
    {
        return(MarketProjectEntryInfo)getObject(index);
    }
    public MarketProjectEntryInfo get(Object key)
    {
        return(MarketProjectEntryInfo)getObject(key);
    }
    public void set(int index, MarketProjectEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketProjectEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketProjectEntryInfo item)
    {
        return super.indexOf(item);
    }
}