package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ThirdPartyExpenseBillHandEntryCollection extends AbstractObjectCollection 
{
    public ThirdPartyExpenseBillHandEntryCollection()
    {
        super(ThirdPartyExpenseBillHandEntryInfo.class);
    }
    public boolean add(ThirdPartyExpenseBillHandEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ThirdPartyExpenseBillHandEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ThirdPartyExpenseBillHandEntryInfo item)
    {
        return removeObject(item);
    }
    public ThirdPartyExpenseBillHandEntryInfo get(int index)
    {
        return(ThirdPartyExpenseBillHandEntryInfo)getObject(index);
    }
    public ThirdPartyExpenseBillHandEntryInfo get(Object key)
    {
        return(ThirdPartyExpenseBillHandEntryInfo)getObject(key);
    }
    public void set(int index, ThirdPartyExpenseBillHandEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ThirdPartyExpenseBillHandEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ThirdPartyExpenseBillHandEntryInfo item)
    {
        return super.indexOf(item);
    }
}