package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MonthExcitationCollection extends AbstractObjectCollection 
{
    public MonthExcitationCollection()
    {
        super(MonthExcitationInfo.class);
    }
    public boolean add(MonthExcitationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MonthExcitationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MonthExcitationInfo item)
    {
        return removeObject(item);
    }
    public MonthExcitationInfo get(int index)
    {
        return(MonthExcitationInfo)getObject(index);
    }
    public MonthExcitationInfo get(Object key)
    {
        return(MonthExcitationInfo)getObject(key);
    }
    public void set(int index, MonthExcitationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MonthExcitationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MonthExcitationInfo item)
    {
        return super.indexOf(item);
    }
}