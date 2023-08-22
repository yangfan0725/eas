package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractRecBillCollection extends AbstractObjectCollection 
{
    public ContractRecBillCollection()
    {
        super(ContractRecBillInfo.class);
    }
    public boolean add(ContractRecBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractRecBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractRecBillInfo item)
    {
        return removeObject(item);
    }
    public ContractRecBillInfo get(int index)
    {
        return(ContractRecBillInfo)getObject(index);
    }
    public ContractRecBillInfo get(Object key)
    {
        return(ContractRecBillInfo)getObject(key);
    }
    public void set(int index, ContractRecBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractRecBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractRecBillInfo item)
    {
        return super.indexOf(item);
    }
}