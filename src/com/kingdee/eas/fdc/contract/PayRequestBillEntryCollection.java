package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayRequestBillEntryCollection extends AbstractObjectCollection 
{
    public PayRequestBillEntryCollection()
    {
        super(PayRequestBillEntryInfo.class);
    }
    public boolean add(PayRequestBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayRequestBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayRequestBillEntryInfo item)
    {
        return removeObject(item);
    }
    public PayRequestBillEntryInfo get(int index)
    {
        return(PayRequestBillEntryInfo)getObject(index);
    }
    public PayRequestBillEntryInfo get(Object key)
    {
        return(PayRequestBillEntryInfo)getObject(key);
    }
    public void set(int index, PayRequestBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayRequestBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayRequestBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}