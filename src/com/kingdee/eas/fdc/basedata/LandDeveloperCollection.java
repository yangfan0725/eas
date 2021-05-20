package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class LandDeveloperCollection extends AbstractObjectCollection 
{
    public LandDeveloperCollection()
    {
        super(LandDeveloperInfo.class);
    }
    public boolean add(LandDeveloperInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(LandDeveloperCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(LandDeveloperInfo item)
    {
        return removeObject(item);
    }
    public LandDeveloperInfo get(int index)
    {
        return(LandDeveloperInfo)getObject(index);
    }
    public LandDeveloperInfo get(Object key)
    {
        return(LandDeveloperInfo)getObject(key);
    }
    public void set(int index, LandDeveloperInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(LandDeveloperInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(LandDeveloperInfo item)
    {
        return super.indexOf(item);
    }
}