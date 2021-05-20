package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketProjectCostEntryCollection extends AbstractObjectCollection 
{
    public MarketProjectCostEntryCollection()
    {
        super(MarketProjectCostEntryInfo.class);
    }
    public boolean add(MarketProjectCostEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketProjectCostEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketProjectCostEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketProjectCostEntryInfo get(int index)
    {
        return(MarketProjectCostEntryInfo)getObject(index);
    }
    public MarketProjectCostEntryInfo get(Object key)
    {
        return(MarketProjectCostEntryInfo)getObject(key);
    }
    public void set(int index, MarketProjectCostEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketProjectCostEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketProjectCostEntryInfo item)
    {
        return super.indexOf(item);
    }
}