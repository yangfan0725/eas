package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierAppraiseChooseEntryCollection extends AbstractObjectCollection 
{
    public SupplierAppraiseChooseEntryCollection()
    {
        super(SupplierAppraiseChooseEntryInfo.class);
    }
    public boolean add(SupplierAppraiseChooseEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierAppraiseChooseEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierAppraiseChooseEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierAppraiseChooseEntryInfo get(int index)
    {
        return(SupplierAppraiseChooseEntryInfo)getObject(index);
    }
    public SupplierAppraiseChooseEntryInfo get(Object key)
    {
        return(SupplierAppraiseChooseEntryInfo)getObject(key);
    }
    public void set(int index, SupplierAppraiseChooseEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierAppraiseChooseEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierAppraiseChooseEntryInfo item)
    {
        return super.indexOf(item);
    }
}