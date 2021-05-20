package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ReturnTenancyContractCollection extends AbstractObjectCollection 
{
    public ReturnTenancyContractCollection()
    {
        super(ReturnTenancyContractInfo.class);
    }
    public boolean add(ReturnTenancyContractInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ReturnTenancyContractCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ReturnTenancyContractInfo item)
    {
        return removeObject(item);
    }
    public ReturnTenancyContractInfo get(int index)
    {
        return(ReturnTenancyContractInfo)getObject(index);
    }
    public ReturnTenancyContractInfo get(Object key)
    {
        return(ReturnTenancyContractInfo)getObject(key);
    }
    public void set(int index, ReturnTenancyContractInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ReturnTenancyContractInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ReturnTenancyContractInfo item)
    {
        return super.indexOf(item);
    }
}