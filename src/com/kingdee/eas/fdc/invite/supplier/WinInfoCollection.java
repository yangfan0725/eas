package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WinInfoCollection extends AbstractObjectCollection 
{
    public WinInfoCollection()
    {
        super(WinInfoInfo.class);
    }
    public boolean add(WinInfoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WinInfoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WinInfoInfo item)
    {
        return removeObject(item);
    }
    public WinInfoInfo get(int index)
    {
        return(WinInfoInfo)getObject(index);
    }
    public WinInfoInfo get(Object key)
    {
        return(WinInfoInfo)getObject(key);
    }
    public void set(int index, WinInfoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WinInfoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WinInfoInfo item)
    {
        return super.indexOf(item);
    }
}