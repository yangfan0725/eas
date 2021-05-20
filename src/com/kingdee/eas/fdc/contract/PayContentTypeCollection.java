package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayContentTypeCollection extends AbstractObjectCollection 
{
    public PayContentTypeCollection()
    {
        super(PayContentTypeInfo.class);
    }
    public boolean add(PayContentTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayContentTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayContentTypeInfo item)
    {
        return removeObject(item);
    }
    public PayContentTypeInfo get(int index)
    {
        return(PayContentTypeInfo)getObject(index);
    }
    public PayContentTypeInfo get(Object key)
    {
        return(PayContentTypeInfo)getObject(key);
    }
    public void set(int index, PayContentTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayContentTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayContentTypeInfo item)
    {
        return super.indexOf(item);
    }
}