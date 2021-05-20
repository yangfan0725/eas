package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BrandCollection extends AbstractObjectCollection 
{
    public BrandCollection()
    {
        super(BrandInfo.class);
    }
    public boolean add(BrandInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BrandCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BrandInfo item)
    {
        return removeObject(item);
    }
    public BrandInfo get(int index)
    {
        return(BrandInfo)getObject(index);
    }
    public BrandInfo get(Object key)
    {
        return(BrandInfo)getObject(key);
    }
    public void set(int index, BrandInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BrandInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BrandInfo item)
    {
        return super.indexOf(item);
    }
}