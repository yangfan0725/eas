package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSplitBillEntryCollection extends AbstractObjectCollection 
{
    public FDCSplitBillEntryCollection()
    {
        super(FDCSplitBillEntryInfo.class);
    }
    public boolean add(FDCSplitBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSplitBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSplitBillEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCSplitBillEntryInfo get(int index)
    {
        return(FDCSplitBillEntryInfo)getObject(index);
    }
    public FDCSplitBillEntryInfo get(Object key)
    {
        return(FDCSplitBillEntryInfo)getObject(key);
    }
    public void set(int index, FDCSplitBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSplitBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSplitBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}