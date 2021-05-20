package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ZHMarketProjectCollection extends AbstractObjectCollection 
{
    public ZHMarketProjectCollection()
    {
        super(ZHMarketProjectInfo.class);
    }
    public boolean add(ZHMarketProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ZHMarketProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ZHMarketProjectInfo item)
    {
        return removeObject(item);
    }
    public ZHMarketProjectInfo get(int index)
    {
        return(ZHMarketProjectInfo)getObject(index);
    }
    public ZHMarketProjectInfo get(Object key)
    {
        return(ZHMarketProjectInfo)getObject(key);
    }
    public void set(int index, ZHMarketProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ZHMarketProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ZHMarketProjectInfo item)
    {
        return super.indexOf(item);
    }
}