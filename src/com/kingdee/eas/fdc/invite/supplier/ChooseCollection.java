package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChooseCollection extends AbstractObjectCollection 
{
    public ChooseCollection()
    {
        super(ChooseInfo.class);
    }
    public boolean add(ChooseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChooseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChooseInfo item)
    {
        return removeObject(item);
    }
    public ChooseInfo get(int index)
    {
        return(ChooseInfo)getObject(index);
    }
    public ChooseInfo get(Object key)
    {
        return(ChooseInfo)getObject(key);
    }
    public void set(int index, ChooseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChooseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChooseInfo item)
    {
        return super.indexOf(item);
    }
}