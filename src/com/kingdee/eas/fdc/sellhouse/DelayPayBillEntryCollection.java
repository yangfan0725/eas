package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DelayPayBillEntryCollection extends AbstractObjectCollection 
{
    public DelayPayBillEntryCollection()
    {
        super(DelayPayBillEntryInfo.class);
    }
    public boolean add(DelayPayBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DelayPayBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DelayPayBillEntryInfo item)
    {
        return removeObject(item);
    }
    public DelayPayBillEntryInfo get(int index)
    {
        return(DelayPayBillEntryInfo)getObject(index);
    }
    public DelayPayBillEntryInfo get(Object key)
    {
        return(DelayPayBillEntryInfo)getObject(key);
    }
    public void set(int index, DelayPayBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DelayPayBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DelayPayBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}