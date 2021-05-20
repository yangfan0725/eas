package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BrandCategoryCollection extends AbstractObjectCollection 
{
    public BrandCategoryCollection()
    {
        super(BrandCategoryInfo.class);
    }
    public boolean add(BrandCategoryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BrandCategoryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BrandCategoryInfo item)
    {
        return removeObject(item);
    }
    public BrandCategoryInfo get(int index)
    {
        return(BrandCategoryInfo)getObject(index);
    }
    public BrandCategoryInfo get(Object key)
    {
        return(BrandCategoryInfo)getObject(key);
    }
    public void set(int index, BrandCategoryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BrandCategoryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BrandCategoryInfo item)
    {
        return super.indexOf(item);
    }
}