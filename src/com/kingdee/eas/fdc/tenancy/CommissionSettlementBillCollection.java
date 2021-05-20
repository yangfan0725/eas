package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CommissionSettlementBillCollection extends AbstractObjectCollection 
{
    public CommissionSettlementBillCollection()
    {
        super(CommissionSettlementBillInfo.class);
    }
    public boolean add(CommissionSettlementBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CommissionSettlementBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CommissionSettlementBillInfo item)
    {
        return removeObject(item);
    }
    public CommissionSettlementBillInfo get(int index)
    {
        return(CommissionSettlementBillInfo)getObject(index);
    }
    public CommissionSettlementBillInfo get(Object key)
    {
        return(CommissionSettlementBillInfo)getObject(key);
    }
    public void set(int index, CommissionSettlementBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CommissionSettlementBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CommissionSettlementBillInfo item)
    {
        return super.indexOf(item);
    }
}