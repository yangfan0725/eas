package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHEAttachBillEntryCollection extends AbstractObjectCollection 
{
    public SHEAttachBillEntryCollection()
    {
        super(SHEAttachBillEntryInfo.class);
    }
    public boolean add(SHEAttachBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHEAttachBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHEAttachBillEntryInfo item)
    {
        return removeObject(item);
    }
    public SHEAttachBillEntryInfo get(int index)
    {
        return(SHEAttachBillEntryInfo)getObject(index);
    }
    public SHEAttachBillEntryInfo get(Object key)
    {
        return(SHEAttachBillEntryInfo)getObject(key);
    }
    public void set(int index, SHEAttachBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHEAttachBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHEAttachBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}