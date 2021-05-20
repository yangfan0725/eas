package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeSupplierEntryCollection extends AbstractObjectCollection 
{
    public ChangeSupplierEntryCollection()
    {
        super(ChangeSupplierEntryInfo.class);
    }
    public boolean add(ChangeSupplierEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeSupplierEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeSupplierEntryInfo item)
    {
        return removeObject(item);
    }
    public ChangeSupplierEntryInfo get(int index)
    {
        return(ChangeSupplierEntryInfo)getObject(index);
    }
    public ChangeSupplierEntryInfo get(Object key)
    {
        return(ChangeSupplierEntryInfo)getObject(key);
    }
    public void set(int index, ChangeSupplierEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeSupplierEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeSupplierEntryInfo item)
    {
        return super.indexOf(item);
    }
}