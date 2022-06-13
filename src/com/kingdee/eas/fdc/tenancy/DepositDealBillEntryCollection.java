package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DepositDealBillEntryCollection extends AbstractObjectCollection 
{
    public DepositDealBillEntryCollection()
    {
        super(DepositDealBillEntryInfo.class);
    }
    public boolean add(DepositDealBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DepositDealBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DepositDealBillEntryInfo item)
    {
        return removeObject(item);
    }
    public DepositDealBillEntryInfo get(int index)
    {
        return(DepositDealBillEntryInfo)getObject(index);
    }
    public DepositDealBillEntryInfo get(Object key)
    {
        return(DepositDealBillEntryInfo)getObject(key);
    }
    public void set(int index, DepositDealBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DepositDealBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DepositDealBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}