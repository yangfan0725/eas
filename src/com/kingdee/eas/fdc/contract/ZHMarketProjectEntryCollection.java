package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ZHMarketProjectEntryCollection extends AbstractObjectCollection 
{
    public ZHMarketProjectEntryCollection()
    {
        super(ZHMarketProjectEntryInfo.class);
    }
    public boolean add(ZHMarketProjectEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ZHMarketProjectEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ZHMarketProjectEntryInfo item)
    {
        return removeObject(item);
    }
    public ZHMarketProjectEntryInfo get(int index)
    {
        return(ZHMarketProjectEntryInfo)getObject(index);
    }
    public ZHMarketProjectEntryInfo get(Object key)
    {
        return(ZHMarketProjectEntryInfo)getObject(key);
    }
    public void set(int index, ZHMarketProjectEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ZHMarketProjectEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ZHMarketProjectEntryInfo item)
    {
        return super.indexOf(item);
    }
}