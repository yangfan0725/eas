package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CostAccountCollection extends AbstractObjectCollection 
{
    public CostAccountCollection()
    {
        super(CostAccountInfo.class);
    }
    public boolean add(CostAccountInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CostAccountCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CostAccountInfo item)
    {
        return removeObject(item);
    }
    public CostAccountInfo get(int index)
    {
        return(CostAccountInfo)getObject(index);
    }
    public CostAccountInfo get(Object key)
    {
        return(CostAccountInfo)getObject(key);
    }
    public void set(int index, CostAccountInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CostAccountInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CostAccountInfo item)
    {
        return super.indexOf(item);
    }
}