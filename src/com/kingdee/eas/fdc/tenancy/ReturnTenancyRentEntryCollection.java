package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ReturnTenancyRentEntryCollection extends AbstractObjectCollection 
{
    public ReturnTenancyRentEntryCollection()
    {
        super(ReturnTenancyRentEntryInfo.class);
    }
    public boolean add(ReturnTenancyRentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ReturnTenancyRentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ReturnTenancyRentEntryInfo item)
    {
        return removeObject(item);
    }
    public ReturnTenancyRentEntryInfo get(int index)
    {
        return(ReturnTenancyRentEntryInfo)getObject(index);
    }
    public ReturnTenancyRentEntryInfo get(Object key)
    {
        return(ReturnTenancyRentEntryInfo)getObject(key);
    }
    public void set(int index, ReturnTenancyRentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ReturnTenancyRentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ReturnTenancyRentEntryInfo item)
    {
        return super.indexOf(item);
    }
}