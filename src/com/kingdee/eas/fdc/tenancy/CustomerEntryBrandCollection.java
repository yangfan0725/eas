package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CustomerEntryBrandCollection extends AbstractObjectCollection 
{
    public CustomerEntryBrandCollection()
    {
        super(CustomerEntryBrandInfo.class);
    }
    public boolean add(CustomerEntryBrandInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CustomerEntryBrandCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CustomerEntryBrandInfo item)
    {
        return removeObject(item);
    }
    public CustomerEntryBrandInfo get(int index)
    {
        return(CustomerEntryBrandInfo)getObject(index);
    }
    public CustomerEntryBrandInfo get(Object key)
    {
        return(CustomerEntryBrandInfo)getObject(key);
    }
    public void set(int index, CustomerEntryBrandInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CustomerEntryBrandInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CustomerEntryBrandInfo item)
    {
        return super.indexOf(item);
    }
}