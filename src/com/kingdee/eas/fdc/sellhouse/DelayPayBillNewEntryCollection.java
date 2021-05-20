package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DelayPayBillNewEntryCollection extends AbstractObjectCollection 
{
    public DelayPayBillNewEntryCollection()
    {
        super(DelayPayBillNewEntryInfo.class);
    }
    public boolean add(DelayPayBillNewEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DelayPayBillNewEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DelayPayBillNewEntryInfo item)
    {
        return removeObject(item);
    }
    public DelayPayBillNewEntryInfo get(int index)
    {
        return(DelayPayBillNewEntryInfo)getObject(index);
    }
    public DelayPayBillNewEntryInfo get(Object key)
    {
        return(DelayPayBillNewEntryInfo)getObject(key);
    }
    public void set(int index, DelayPayBillNewEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DelayPayBillNewEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DelayPayBillNewEntryInfo item)
    {
        return super.indexOf(item);
    }
}