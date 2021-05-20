package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MonthCommissionEntryCollection extends AbstractObjectCollection 
{
    public MonthCommissionEntryCollection()
    {
        super(MonthCommissionEntryInfo.class);
    }
    public boolean add(MonthCommissionEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MonthCommissionEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MonthCommissionEntryInfo item)
    {
        return removeObject(item);
    }
    public MonthCommissionEntryInfo get(int index)
    {
        return(MonthCommissionEntryInfo)getObject(index);
    }
    public MonthCommissionEntryInfo get(Object key)
    {
        return(MonthCommissionEntryInfo)getObject(key);
    }
    public void set(int index, MonthCommissionEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MonthCommissionEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MonthCommissionEntryInfo item)
    {
        return super.indexOf(item);
    }
}