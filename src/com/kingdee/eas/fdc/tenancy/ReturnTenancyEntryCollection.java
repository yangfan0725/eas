package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ReturnTenancyEntryCollection extends AbstractObjectCollection 
{
    public ReturnTenancyEntryCollection()
    {
        super(ReturnTenancyEntryInfo.class);
    }
    public boolean add(ReturnTenancyEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ReturnTenancyEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ReturnTenancyEntryInfo item)
    {
        return removeObject(item);
    }
    public ReturnTenancyEntryInfo get(int index)
    {
        return(ReturnTenancyEntryInfo)getObject(index);
    }
    public ReturnTenancyEntryInfo get(Object key)
    {
        return(ReturnTenancyEntryInfo)getObject(key);
    }
    public void set(int index, ReturnTenancyEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ReturnTenancyEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ReturnTenancyEntryInfo item)
    {
        return super.indexOf(item);
    }
}