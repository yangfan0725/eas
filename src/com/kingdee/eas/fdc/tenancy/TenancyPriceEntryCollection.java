package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyPriceEntryCollection extends AbstractObjectCollection 
{
    public TenancyPriceEntryCollection()
    {
        super(TenancyPriceEntryInfo.class);
    }
    public boolean add(TenancyPriceEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyPriceEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyPriceEntryInfo item)
    {
        return removeObject(item);
    }
    public TenancyPriceEntryInfo get(int index)
    {
        return(TenancyPriceEntryInfo)getObject(index);
    }
    public TenancyPriceEntryInfo get(Object key)
    {
        return(TenancyPriceEntryInfo)getObject(key);
    }
    public void set(int index, TenancyPriceEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyPriceEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyPriceEntryInfo item)
    {
        return super.indexOf(item);
    }
}