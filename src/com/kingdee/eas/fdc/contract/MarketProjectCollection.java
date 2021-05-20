package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketProjectCollection extends AbstractObjectCollection 
{
    public MarketProjectCollection()
    {
        super(MarketProjectInfo.class);
    }
    public boolean add(MarketProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketProjectInfo item)
    {
        return removeObject(item);
    }
    public MarketProjectInfo get(int index)
    {
        return(MarketProjectInfo)getObject(index);
    }
    public MarketProjectInfo get(Object key)
    {
        return(MarketProjectInfo)getObject(key);
    }
    public void set(int index, MarketProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketProjectInfo item)
    {
        return super.indexOf(item);
    }
}