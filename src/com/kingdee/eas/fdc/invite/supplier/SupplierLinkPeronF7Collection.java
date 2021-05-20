package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierLinkPeronF7Collection extends AbstractObjectCollection 
{
    public SupplierLinkPeronF7Collection()
    {
        super(SupplierLinkPeronF7Info.class);
    }
    public boolean add(SupplierLinkPeronF7Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierLinkPeronF7Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierLinkPeronF7Info item)
    {
        return removeObject(item);
    }
    public SupplierLinkPeronF7Info get(int index)
    {
        return(SupplierLinkPeronF7Info)getObject(index);
    }
    public SupplierLinkPeronF7Info get(Object key)
    {
        return(SupplierLinkPeronF7Info)getObject(key);
    }
    public void set(int index, SupplierLinkPeronF7Info item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierLinkPeronF7Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierLinkPeronF7Info item)
    {
        return super.indexOf(item);
    }
}