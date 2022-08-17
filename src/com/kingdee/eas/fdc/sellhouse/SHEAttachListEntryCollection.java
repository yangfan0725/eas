package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHEAttachListEntryCollection extends AbstractObjectCollection 
{
    public SHEAttachListEntryCollection()
    {
        super(SHEAttachListEntryInfo.class);
    }
    public boolean add(SHEAttachListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHEAttachListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHEAttachListEntryInfo item)
    {
        return removeObject(item);
    }
    public SHEAttachListEntryInfo get(int index)
    {
        return(SHEAttachListEntryInfo)getObject(index);
    }
    public SHEAttachListEntryInfo get(Object key)
    {
        return(SHEAttachListEntryInfo)getObject(key);
    }
    public void set(int index, SHEAttachListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHEAttachListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHEAttachListEntryInfo item)
    {
        return super.indexOf(item);
    }
}