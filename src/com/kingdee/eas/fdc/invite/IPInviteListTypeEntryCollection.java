package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class IPInviteListTypeEntryCollection extends AbstractObjectCollection 
{
    public IPInviteListTypeEntryCollection()
    {
        super(IPInviteListTypeEntryInfo.class);
    }
    public boolean add(IPInviteListTypeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(IPInviteListTypeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(IPInviteListTypeEntryInfo item)
    {
        return removeObject(item);
    }
    public IPInviteListTypeEntryInfo get(int index)
    {
        return(IPInviteListTypeEntryInfo)getObject(index);
    }
    public IPInviteListTypeEntryInfo get(Object key)
    {
        return(IPInviteListTypeEntryInfo)getObject(key);
    }
    public void set(int index, IPInviteListTypeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(IPInviteListTypeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(IPInviteListTypeEntryInfo item)
    {
        return super.indexOf(item);
    }
}