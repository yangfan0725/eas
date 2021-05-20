package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ConChangeSplitEntryCollection extends AbstractObjectCollection 
{
    public ConChangeSplitEntryCollection()
    {
        super(ConChangeSplitEntryInfo.class);
    }
    public boolean add(ConChangeSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ConChangeSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ConChangeSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public ConChangeSplitEntryInfo get(int index)
    {
        return(ConChangeSplitEntryInfo)getObject(index);
    }
    public ConChangeSplitEntryInfo get(Object key)
    {
        return(ConChangeSplitEntryInfo)getObject(key);
    }
    public void set(int index, ConChangeSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ConChangeSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ConChangeSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}