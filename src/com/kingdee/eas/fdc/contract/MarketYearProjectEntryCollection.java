package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketYearProjectEntryCollection extends AbstractObjectCollection 
{
    public MarketYearProjectEntryCollection()
    {
        super(MarketYearProjectEntryInfo.class);
    }
    public boolean add(MarketYearProjectEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketYearProjectEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketYearProjectEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketYearProjectEntryInfo get(int index)
    {
        return(MarketYearProjectEntryInfo)getObject(index);
    }
    public MarketYearProjectEntryInfo get(Object key)
    {
        return(MarketYearProjectEntryInfo)getObject(key);
    }
    public void set(int index, MarketYearProjectEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketYearProjectEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketYearProjectEntryInfo item)
    {
        return super.indexOf(item);
    }
}