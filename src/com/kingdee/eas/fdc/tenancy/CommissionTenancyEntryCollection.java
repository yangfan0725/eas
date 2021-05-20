package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CommissionTenancyEntryCollection extends AbstractObjectCollection 
{
    public CommissionTenancyEntryCollection()
    {
        super(CommissionTenancyEntryInfo.class);
    }
    public boolean add(CommissionTenancyEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CommissionTenancyEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CommissionTenancyEntryInfo item)
    {
        return removeObject(item);
    }
    public CommissionTenancyEntryInfo get(int index)
    {
        return(CommissionTenancyEntryInfo)getObject(index);
    }
    public CommissionTenancyEntryInfo get(Object key)
    {
        return(CommissionTenancyEntryInfo)getObject(key);
    }
    public void set(int index, CommissionTenancyEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CommissionTenancyEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CommissionTenancyEntryInfo item)
    {
        return super.indexOf(item);
    }
}