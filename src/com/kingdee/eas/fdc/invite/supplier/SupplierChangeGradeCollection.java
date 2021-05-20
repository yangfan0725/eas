package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierChangeGradeCollection extends AbstractObjectCollection 
{
    public SupplierChangeGradeCollection()
    {
        super(SupplierChangeGradeInfo.class);
    }
    public boolean add(SupplierChangeGradeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierChangeGradeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierChangeGradeInfo item)
    {
        return removeObject(item);
    }
    public SupplierChangeGradeInfo get(int index)
    {
        return(SupplierChangeGradeInfo)getObject(index);
    }
    public SupplierChangeGradeInfo get(Object key)
    {
        return(SupplierChangeGradeInfo)getObject(key);
    }
    public void set(int index, SupplierChangeGradeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierChangeGradeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierChangeGradeInfo item)
    {
        return super.indexOf(item);
    }
}