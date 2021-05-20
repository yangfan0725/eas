package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketMonthProjectEntryCollection extends AbstractObjectCollection 
{
    public MarketMonthProjectEntryCollection()
    {
        super(MarketMonthProjectEntryInfo.class);
    }
    public boolean add(MarketMonthProjectEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketMonthProjectEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketMonthProjectEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketMonthProjectEntryInfo get(int index)
    {
        return(MarketMonthProjectEntryInfo)getObject(index);
    }
    public MarketMonthProjectEntryInfo get(Object key)
    {
        return(MarketMonthProjectEntryInfo)getObject(key);
    }
    public void set(int index, MarketMonthProjectEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketMonthProjectEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketMonthProjectEntryInfo item)
    {
        return super.indexOf(item);
    }
}