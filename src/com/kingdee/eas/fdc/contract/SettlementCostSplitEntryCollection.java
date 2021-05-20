package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettlementCostSplitEntryCollection extends AbstractObjectCollection 
{
    public SettlementCostSplitEntryCollection()
    {
        super(SettlementCostSplitEntryInfo.class);
    }
    public boolean add(SettlementCostSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettlementCostSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettlementCostSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public SettlementCostSplitEntryInfo get(int index)
    {
        return(SettlementCostSplitEntryInfo)getObject(index);
    }
    public SettlementCostSplitEntryInfo get(Object key)
    {
        return(SettlementCostSplitEntryInfo)getObject(key);
    }
    public void set(int index, SettlementCostSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SettlementCostSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettlementCostSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}