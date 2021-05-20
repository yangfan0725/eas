package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DelayPayBillCollection extends AbstractObjectCollection 
{
    public DelayPayBillCollection()
    {
        super(DelayPayBillInfo.class);
    }
    public boolean add(DelayPayBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DelayPayBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DelayPayBillInfo item)
    {
        return removeObject(item);
    }
    public DelayPayBillInfo get(int index)
    {
        return(DelayPayBillInfo)getObject(index);
    }
    public DelayPayBillInfo get(Object key)
    {
        return(DelayPayBillInfo)getObject(key);
    }
    public void set(int index, DelayPayBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DelayPayBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DelayPayBillInfo item)
    {
        return super.indexOf(item);
    }
}