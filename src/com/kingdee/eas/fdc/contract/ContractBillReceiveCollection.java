package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBillReceiveCollection extends AbstractObjectCollection 
{
    public ContractBillReceiveCollection()
    {
        super(ContractBillReceiveInfo.class);
    }
    public boolean add(ContractBillReceiveInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBillReceiveCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBillReceiveInfo item)
    {
        return removeObject(item);
    }
    public ContractBillReceiveInfo get(int index)
    {
        return(ContractBillReceiveInfo)getObject(index);
    }
    public ContractBillReceiveInfo get(Object key)
    {
        return(ContractBillReceiveInfo)getObject(key);
    }
    public void set(int index, ContractBillReceiveInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBillReceiveInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBillReceiveInfo item)
    {
        return super.indexOf(item);
    }
}