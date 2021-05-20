package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierInviteListTypeEntryCollection extends AbstractObjectCollection 
{
    public SupplierInviteListTypeEntryCollection()
    {
        super(SupplierInviteListTypeEntryInfo.class);
    }
    public boolean add(SupplierInviteListTypeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierInviteListTypeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierInviteListTypeEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierInviteListTypeEntryInfo get(int index)
    {
        return(SupplierInviteListTypeEntryInfo)getObject(index);
    }
    public SupplierInviteListTypeEntryInfo get(Object key)
    {
        return(SupplierInviteListTypeEntryInfo)getObject(key);
    }
    public void set(int index, SupplierInviteListTypeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierInviteListTypeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierInviteListTypeEntryInfo item)
    {
        return super.indexOf(item);
    }
}