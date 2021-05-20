package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AgencyCommissionEntryCollection extends AbstractObjectCollection 
{
    public AgencyCommissionEntryCollection()
    {
        super(AgencyCommissionEntryInfo.class);
    }
    public boolean add(AgencyCommissionEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AgencyCommissionEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AgencyCommissionEntryInfo item)
    {
        return removeObject(item);
    }
    public AgencyCommissionEntryInfo get(int index)
    {
        return(AgencyCommissionEntryInfo)getObject(index);
    }
    public AgencyCommissionEntryInfo get(Object key)
    {
        return(AgencyCommissionEntryInfo)getObject(key);
    }
    public void set(int index, AgencyCommissionEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AgencyCommissionEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AgencyCommissionEntryInfo item)
    {
        return super.indexOf(item);
    }
}