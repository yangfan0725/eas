package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettleWFTypeCollection extends AbstractObjectCollection 
{
    public SettleWFTypeCollection()
    {
        super(SettleWFTypeInfo.class);
    }
    public boolean add(SettleWFTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettleWFTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettleWFTypeInfo item)
    {
        return removeObject(item);
    }
    public SettleWFTypeInfo get(int index)
    {
        return(SettleWFTypeInfo)getObject(index);
    }
    public SettleWFTypeInfo get(Object key)
    {
        return(SettleWFTypeInfo)getObject(key);
    }
    public void set(int index, SettleWFTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SettleWFTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettleWFTypeInfo item)
    {
        return super.indexOf(item);
    }
}