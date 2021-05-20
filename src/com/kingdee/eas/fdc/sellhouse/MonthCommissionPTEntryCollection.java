package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MonthCommissionPTEntryCollection extends AbstractObjectCollection 
{
    public MonthCommissionPTEntryCollection()
    {
        super(MonthCommissionPTEntryInfo.class);
    }
    public boolean add(MonthCommissionPTEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MonthCommissionPTEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MonthCommissionPTEntryInfo item)
    {
        return removeObject(item);
    }
    public MonthCommissionPTEntryInfo get(int index)
    {
        return(MonthCommissionPTEntryInfo)getObject(index);
    }
    public MonthCommissionPTEntryInfo get(Object key)
    {
        return(MonthCommissionPTEntryInfo)getObject(key);
    }
    public void set(int index, MonthCommissionPTEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MonthCommissionPTEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MonthCommissionPTEntryInfo item)
    {
        return super.indexOf(item);
    }
}