package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RecommendTypeCollection extends AbstractObjectCollection 
{
    public RecommendTypeCollection()
    {
        super(RecommendTypeInfo.class);
    }
    public boolean add(RecommendTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RecommendTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RecommendTypeInfo item)
    {
        return removeObject(item);
    }
    public RecommendTypeInfo get(int index)
    {
        return(RecommendTypeInfo)getObject(index);
    }
    public RecommendTypeInfo get(Object key)
    {
        return(RecommendTypeInfo)getObject(key);
    }
    public void set(int index, RecommendTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RecommendTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RecommendTypeInfo item)
    {
        return super.indexOf(item);
    }
}