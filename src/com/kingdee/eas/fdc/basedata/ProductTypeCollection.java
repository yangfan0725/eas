package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProductTypeCollection extends AbstractObjectCollection 
{
    public ProductTypeCollection()
    {
        super(ProductTypeInfo.class);
    }
    public boolean add(ProductTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProductTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProductTypeInfo item)
    {
        return removeObject(item);
    }
    public ProductTypeInfo get(int index)
    {
        return(ProductTypeInfo)getObject(index);
    }
    public ProductTypeInfo get(Object key)
    {
        return(ProductTypeInfo)getObject(key);
    }
    public void set(int index, ProductTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProductTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProductTypeInfo item)
    {
        return super.indexOf(item);
    }
}