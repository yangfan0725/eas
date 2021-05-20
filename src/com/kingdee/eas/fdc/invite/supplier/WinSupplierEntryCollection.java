package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WinSupplierEntryCollection extends AbstractObjectCollection 
{
    public WinSupplierEntryCollection()
    {
        super(WinSupplierEntryInfo.class);
    }
    public boolean add(WinSupplierEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WinSupplierEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WinSupplierEntryInfo item)
    {
        return removeObject(item);
    }
    public WinSupplierEntryInfo get(int index)
    {
        return(WinSupplierEntryInfo)getObject(index);
    }
    public WinSupplierEntryInfo get(Object key)
    {
        return(WinSupplierEntryInfo)getObject(key);
    }
    public void set(int index, WinSupplierEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WinSupplierEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WinSupplierEntryInfo item)
    {
        return super.indexOf(item);
    }
}