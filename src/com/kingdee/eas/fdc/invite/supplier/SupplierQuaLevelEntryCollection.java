package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierQuaLevelEntryCollection extends AbstractObjectCollection 
{
    public SupplierQuaLevelEntryCollection()
    {
        super(SupplierQuaLevelEntryInfo.class);
    }
    public boolean add(SupplierQuaLevelEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierQuaLevelEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierQuaLevelEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierQuaLevelEntryInfo get(int index)
    {
        return(SupplierQuaLevelEntryInfo)getObject(index);
    }
    public SupplierQuaLevelEntryInfo get(Object key)
    {
        return(SupplierQuaLevelEntryInfo)getObject(key);
    }
    public void set(int index, SupplierQuaLevelEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierQuaLevelEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierQuaLevelEntryInfo item)
    {
        return super.indexOf(item);
    }
}