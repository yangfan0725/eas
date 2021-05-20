package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierChangeConfirmCollection extends AbstractObjectCollection 
{
    public SupplierChangeConfirmCollection()
    {
        super(SupplierChangeConfirmInfo.class);
    }
    public boolean add(SupplierChangeConfirmInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierChangeConfirmCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierChangeConfirmInfo item)
    {
        return removeObject(item);
    }
    public SupplierChangeConfirmInfo get(int index)
    {
        return(SupplierChangeConfirmInfo)getObject(index);
    }
    public SupplierChangeConfirmInfo get(Object key)
    {
        return(SupplierChangeConfirmInfo)getObject(key);
    }
    public void set(int index, SupplierChangeConfirmInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierChangeConfirmInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierChangeConfirmInfo item)
    {
        return super.indexOf(item);
    }
}