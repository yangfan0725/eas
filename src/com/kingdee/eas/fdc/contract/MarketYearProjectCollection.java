package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketYearProjectCollection extends AbstractObjectCollection 
{
    public MarketYearProjectCollection()
    {
        super(MarketYearProjectInfo.class);
    }
    public boolean add(MarketYearProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketYearProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketYearProjectInfo item)
    {
        return removeObject(item);
    }
    public MarketYearProjectInfo get(int index)
    {
        return(MarketYearProjectInfo)getObject(index);
    }
    public MarketYearProjectInfo get(Object key)
    {
        return(MarketYearProjectInfo)getObject(key);
    }
    public void set(int index, MarketYearProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketYearProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketYearProjectInfo item)
    {
        return super.indexOf(item);
    }
}