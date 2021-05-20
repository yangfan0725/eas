package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenPriceEntryCollection extends AbstractObjectCollection 
{
    public TenPriceEntryCollection()
    {
        super(TenPriceEntryInfo.class);
    }
    public boolean add(TenPriceEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenPriceEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenPriceEntryInfo item)
    {
        return removeObject(item);
    }
    public TenPriceEntryInfo get(int index)
    {
        return(TenPriceEntryInfo)getObject(index);
    }
    public TenPriceEntryInfo get(Object key)
    {
        return(TenPriceEntryInfo)getObject(key);
    }
    public void set(int index, TenPriceEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenPriceEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenPriceEntryInfo item)
    {
        return super.indexOf(item);
    }
}