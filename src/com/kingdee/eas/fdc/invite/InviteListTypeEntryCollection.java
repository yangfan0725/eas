package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteListTypeEntryCollection extends AbstractObjectCollection 
{
    public InviteListTypeEntryCollection()
    {
        super(InviteListTypeEntryInfo.class);
    }
    public boolean add(InviteListTypeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteListTypeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteListTypeEntryInfo item)
    {
        return removeObject(item);
    }
    public InviteListTypeEntryInfo get(int index)
    {
        return(InviteListTypeEntryInfo)getObject(index);
    }
    public InviteListTypeEntryInfo get(Object key)
    {
        return(InviteListTypeEntryInfo)getObject(key);
    }
    public void set(int index, InviteListTypeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteListTypeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteListTypeEntryInfo item)
    {
        return super.indexOf(item);
    }
}