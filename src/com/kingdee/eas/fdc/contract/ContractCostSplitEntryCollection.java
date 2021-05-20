package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractCostSplitEntryCollection extends AbstractObjectCollection 
{
    public ContractCostSplitEntryCollection()
    {
        super(ContractCostSplitEntryInfo.class);
    }
    public boolean add(ContractCostSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractCostSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractCostSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractCostSplitEntryInfo get(int index)
    {
        return(ContractCostSplitEntryInfo)getObject(index);
    }
    public ContractCostSplitEntryInfo get(Object key)
    {
        return(ContractCostSplitEntryInfo)getObject(key);
    }
    public void set(int index, ContractCostSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractCostSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractCostSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}