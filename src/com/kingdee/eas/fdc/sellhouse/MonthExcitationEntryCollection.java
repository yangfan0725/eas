package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MonthExcitationEntryCollection extends AbstractObjectCollection 
{
    public MonthExcitationEntryCollection()
    {
        super(MonthExcitationEntryInfo.class);
    }
    public boolean add(MonthExcitationEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MonthExcitationEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MonthExcitationEntryInfo item)
    {
        return removeObject(item);
    }
    public MonthExcitationEntryInfo get(int index)
    {
        return(MonthExcitationEntryInfo)getObject(index);
    }
    public MonthExcitationEntryInfo get(Object key)
    {
        return(MonthExcitationEntryInfo)getObject(key);
    }
    public void set(int index, MonthExcitationEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MonthExcitationEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MonthExcitationEntryInfo item)
    {
        return super.indexOf(item);
    }
}