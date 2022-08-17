package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHEAttachListCollection extends AbstractObjectCollection 
{
    public SHEAttachListCollection()
    {
        super(SHEAttachListInfo.class);
    }
    public boolean add(SHEAttachListInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHEAttachListCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHEAttachListInfo item)
    {
        return removeObject(item);
    }
    public SHEAttachListInfo get(int index)
    {
        return(SHEAttachListInfo)getObject(index);
    }
    public SHEAttachListInfo get(Object key)
    {
        return(SHEAttachListInfo)getObject(key);
    }
    public void set(int index, SHEAttachListInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHEAttachListInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHEAttachListInfo item)
    {
        return super.indexOf(item);
    }
}