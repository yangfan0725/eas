package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CustomerBrandCollection extends AbstractObjectCollection 
{
    public CustomerBrandCollection()
    {
        super(CustomerBrandInfo.class);
    }
    public boolean add(CustomerBrandInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CustomerBrandCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CustomerBrandInfo item)
    {
        return removeObject(item);
    }
    public CustomerBrandInfo get(int index)
    {
        return(CustomerBrandInfo)getObject(index);
    }
    public CustomerBrandInfo get(Object key)
    {
        return(CustomerBrandInfo)getObject(key);
    }
    public void set(int index, CustomerBrandInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CustomerBrandInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CustomerBrandInfo item)
    {
        return super.indexOf(item);
    }
}