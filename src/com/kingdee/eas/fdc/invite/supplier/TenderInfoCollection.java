package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenderInfoCollection extends AbstractObjectCollection 
{
    public TenderInfoCollection()
    {
        super(TenderInfoInfo.class);
    }
    public boolean add(TenderInfoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenderInfoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenderInfoInfo item)
    {
        return removeObject(item);
    }
    public TenderInfoInfo get(int index)
    {
        return(TenderInfoInfo)getObject(index);
    }
    public TenderInfoInfo get(Object key)
    {
        return(TenderInfoInfo)getObject(key);
    }
    public void set(int index, TenderInfoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenderInfoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenderInfoInfo item)
    {
        return super.indexOf(item);
    }
}