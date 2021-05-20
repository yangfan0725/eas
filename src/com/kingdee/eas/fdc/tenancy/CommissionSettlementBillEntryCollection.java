package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CommissionSettlementBillEntryCollection extends AbstractObjectCollection 
{
    public CommissionSettlementBillEntryCollection()
    {
        super(CommissionSettlementBillEntryInfo.class);
    }
    public boolean add(CommissionSettlementBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CommissionSettlementBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CommissionSettlementBillEntryInfo item)
    {
        return removeObject(item);
    }
    public CommissionSettlementBillEntryInfo get(int index)
    {
        return(CommissionSettlementBillEntryInfo)getObject(index);
    }
    public CommissionSettlementBillEntryInfo get(Object key)
    {
        return(CommissionSettlementBillEntryInfo)getObject(key);
    }
    public void set(int index, CommissionSettlementBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CommissionSettlementBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CommissionSettlementBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}