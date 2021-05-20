package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierChangeEntryCollection extends AbstractObjectCollection 
{
    public SupplierChangeEntryCollection()
    {
        super(SupplierChangeEntryInfo.class);
    }
    public boolean add(SupplierChangeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierChangeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierChangeEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierChangeEntryInfo get(int index)
    {
        return(SupplierChangeEntryInfo)getObject(index);
    }
    public SupplierChangeEntryInfo get(Object key)
    {
        return(SupplierChangeEntryInfo)getObject(key);
    }
    public void set(int index, SupplierChangeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierChangeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierChangeEntryInfo item)
    {
        return super.indexOf(item);
    }
}