package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierLinkPersonF7Collection extends AbstractObjectCollection 
{
    public SupplierLinkPersonF7Collection()
    {
        super(SupplierLinkPersonF7Info.class);
    }
    public boolean add(SupplierLinkPersonF7Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierLinkPersonF7Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierLinkPersonF7Info item)
    {
        return removeObject(item);
    }
    public SupplierLinkPersonF7Info get(int index)
    {
        return(SupplierLinkPersonF7Info)getObject(index);
    }
    public SupplierLinkPersonF7Info get(Object key)
    {
        return(SupplierLinkPersonF7Info)getObject(key);
    }
    public void set(int index, SupplierLinkPersonF7Info item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierLinkPersonF7Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierLinkPersonF7Info item)
    {
        return super.indexOf(item);
    }
}