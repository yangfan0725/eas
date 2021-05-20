package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayRequestBillCollection extends AbstractObjectCollection 
{
    public PayRequestBillCollection()
    {
        super(PayRequestBillInfo.class);
    }
    public boolean add(PayRequestBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayRequestBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayRequestBillInfo item)
    {
        return removeObject(item);
    }
    public PayRequestBillInfo get(int index)
    {
        return(PayRequestBillInfo)getObject(index);
    }
    public PayRequestBillInfo get(Object key)
    {
        return(PayRequestBillInfo)getObject(key);
    }
    public void set(int index, PayRequestBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayRequestBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayRequestBillInfo item)
    {
        return super.indexOf(item);
    }
}