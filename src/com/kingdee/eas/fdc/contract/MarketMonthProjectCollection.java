package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketMonthProjectCollection extends AbstractObjectCollection 
{
    public MarketMonthProjectCollection()
    {
        super(MarketMonthProjectInfo.class);
    }
    public boolean add(MarketMonthProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketMonthProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketMonthProjectInfo item)
    {
        return removeObject(item);
    }
    public MarketMonthProjectInfo get(int index)
    {
        return(MarketMonthProjectInfo)getObject(index);
    }
    public MarketMonthProjectInfo get(Object key)
    {
        return(MarketMonthProjectInfo)getObject(key);
    }
    public void set(int index, MarketMonthProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketMonthProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketMonthProjectInfo item)
    {
        return super.indexOf(item);
    }
}