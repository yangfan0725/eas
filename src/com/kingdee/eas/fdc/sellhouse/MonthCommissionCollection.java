package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MonthCommissionCollection extends AbstractObjectCollection 
{
    public MonthCommissionCollection()
    {
        super(MonthCommissionInfo.class);
    }
    public boolean add(MonthCommissionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MonthCommissionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MonthCommissionInfo item)
    {
        return removeObject(item);
    }
    public MonthCommissionInfo get(int index)
    {
        return(MonthCommissionInfo)getObject(index);
    }
    public MonthCommissionInfo get(Object key)
    {
        return(MonthCommissionInfo)getObject(key);
    }
    public void set(int index, MonthCommissionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MonthCommissionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MonthCommissionInfo item)
    {
        return super.indexOf(item);
    }
}