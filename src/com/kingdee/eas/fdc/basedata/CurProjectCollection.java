package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CurProjectCollection extends AbstractObjectCollection 
{
    public CurProjectCollection()
    {
        super(CurProjectInfo.class);
    }
    public boolean add(CurProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CurProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CurProjectInfo item)
    {
        return removeObject(item);
    }
    public CurProjectInfo get(int index)
    {
        return(CurProjectInfo)getObject(index);
    }
    public CurProjectInfo get(Object key)
    {
        return(CurProjectInfo)getObject(key);
    }
    public void set(int index, CurProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CurProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CurProjectInfo item)
    {
        return super.indexOf(item);
    }
}