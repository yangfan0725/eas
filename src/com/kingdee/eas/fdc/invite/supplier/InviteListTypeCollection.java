package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteListTypeCollection extends AbstractObjectCollection 
{
    public InviteListTypeCollection()
    {
        super(InviteListTypeInfo.class);
    }
    public boolean add(InviteListTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteListTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteListTypeInfo item)
    {
        return removeObject(item);
    }
    public InviteListTypeInfo get(int index)
    {
        return(InviteListTypeInfo)getObject(index);
    }
    public InviteListTypeInfo get(Object key)
    {
        return(InviteListTypeInfo)getObject(key);
    }
    public void set(int index, InviteListTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteListTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteListTypeInfo item)
    {
        return super.indexOf(item);
    }
}