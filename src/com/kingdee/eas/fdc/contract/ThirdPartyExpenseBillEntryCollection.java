package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ThirdPartyExpenseBillEntryCollection extends AbstractObjectCollection 
{
    public ThirdPartyExpenseBillEntryCollection()
    {
        super(ThirdPartyExpenseBillEntryInfo.class);
    }
    public boolean add(ThirdPartyExpenseBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ThirdPartyExpenseBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ThirdPartyExpenseBillEntryInfo item)
    {
        return removeObject(item);
    }
    public ThirdPartyExpenseBillEntryInfo get(int index)
    {
        return(ThirdPartyExpenseBillEntryInfo)getObject(index);
    }
    public ThirdPartyExpenseBillEntryInfo get(Object key)
    {
        return(ThirdPartyExpenseBillEntryInfo)getObject(key);
    }
    public void set(int index, ThirdPartyExpenseBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ThirdPartyExpenseBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ThirdPartyExpenseBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}