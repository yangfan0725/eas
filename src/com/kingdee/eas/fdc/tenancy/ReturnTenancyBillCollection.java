package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ReturnTenancyBillCollection extends AbstractObjectCollection 
{
    public ReturnTenancyBillCollection()
    {
        super(ReturnTenancyBillInfo.class);
    }
    public boolean add(ReturnTenancyBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ReturnTenancyBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ReturnTenancyBillInfo item)
    {
        return removeObject(item);
    }
    public ReturnTenancyBillInfo get(int index)
    {
        return(ReturnTenancyBillInfo)getObject(index);
    }
    public ReturnTenancyBillInfo get(Object key)
    {
        return(ReturnTenancyBillInfo)getObject(key);
    }
    public void set(int index, ReturnTenancyBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ReturnTenancyBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ReturnTenancyBillInfo item)
    {
        return super.indexOf(item);
    }
}