package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenPriceBaseLineCollection extends AbstractObjectCollection 
{
    public TenPriceBaseLineCollection()
    {
        super(TenPriceBaseLineInfo.class);
    }
    public boolean add(TenPriceBaseLineInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenPriceBaseLineCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenPriceBaseLineInfo item)
    {
        return removeObject(item);
    }
    public TenPriceBaseLineInfo get(int index)
    {
        return(TenPriceBaseLineInfo)getObject(index);
    }
    public TenPriceBaseLineInfo get(Object key)
    {
        return(TenPriceBaseLineInfo)getObject(key);
    }
    public void set(int index, TenPriceBaseLineInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenPriceBaseLineInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenPriceBaseLineInfo item)
    {
        return super.indexOf(item);
    }
}