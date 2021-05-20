package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketingCommissionEntryCollection extends AbstractObjectCollection 
{
    public MarketingCommissionEntryCollection()
    {
        super(MarketingCommissionEntryInfo.class);
    }
    public boolean add(MarketingCommissionEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketingCommissionEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketingCommissionEntryInfo item)
    {
        return removeObject(item);
    }
    public MarketingCommissionEntryInfo get(int index)
    {
        return(MarketingCommissionEntryInfo)getObject(index);
    }
    public MarketingCommissionEntryInfo get(Object key)
    {
        return(MarketingCommissionEntryInfo)getObject(key);
    }
    public void set(int index, MarketingCommissionEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketingCommissionEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketingCommissionEntryInfo item)
    {
        return super.indexOf(item);
    }
}