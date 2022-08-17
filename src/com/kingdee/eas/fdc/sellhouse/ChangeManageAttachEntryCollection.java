package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeManageAttachEntryCollection extends AbstractObjectCollection 
{
    public ChangeManageAttachEntryCollection()
    {
        super(ChangeManageAttachEntryInfo.class);
    }
    public boolean add(ChangeManageAttachEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeManageAttachEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeManageAttachEntryInfo item)
    {
        return removeObject(item);
    }
    public ChangeManageAttachEntryInfo get(int index)
    {
        return(ChangeManageAttachEntryInfo)getObject(index);
    }
    public ChangeManageAttachEntryInfo get(Object key)
    {
        return(ChangeManageAttachEntryInfo)getObject(key);
    }
    public void set(int index, ChangeManageAttachEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeManageAttachEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeManageAttachEntryInfo item)
    {
        return super.indexOf(item);
    }
}