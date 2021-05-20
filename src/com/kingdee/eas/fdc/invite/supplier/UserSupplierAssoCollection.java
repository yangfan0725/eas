package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class UserSupplierAssoCollection extends AbstractObjectCollection 
{
    public UserSupplierAssoCollection()
    {
        super(UserSupplierAssoInfo.class);
    }
    public boolean add(UserSupplierAssoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(UserSupplierAssoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(UserSupplierAssoInfo item)
    {
        return removeObject(item);
    }
    public UserSupplierAssoInfo get(int index)
    {
        return(UserSupplierAssoInfo)getObject(index);
    }
    public UserSupplierAssoInfo get(Object key)
    {
        return(UserSupplierAssoInfo)getObject(key);
    }
    public void set(int index, UserSupplierAssoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(UserSupplierAssoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(UserSupplierAssoInfo item)
    {
        return super.indexOf(item);
    }
}