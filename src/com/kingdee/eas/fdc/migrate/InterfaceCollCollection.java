package com.kingdee.eas.fdc.migrate;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InterfaceCollCollection extends AbstractObjectCollection 
{
    public InterfaceCollCollection()
    {
        super(InterfaceCollInfo.class);
    }
    public boolean add(InterfaceCollInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InterfaceCollCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InterfaceCollInfo item)
    {
        return removeObject(item);
    }
    public InterfaceCollInfo get(int index)
    {
        return(InterfaceCollInfo)getObject(index);
    }
    public InterfaceCollInfo get(Object key)
    {
        return(InterfaceCollInfo)getObject(key);
    }
    public void set(int index, InterfaceCollInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InterfaceCollInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InterfaceCollInfo item)
    {
        return super.indexOf(item);
    }
}