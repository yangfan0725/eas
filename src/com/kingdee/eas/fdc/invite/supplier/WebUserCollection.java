package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WebUserCollection extends AbstractObjectCollection 
{
    public WebUserCollection()
    {
        super(WebUserInfo.class);
    }
    public boolean add(WebUserInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WebUserCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WebUserInfo item)
    {
        return removeObject(item);
    }
    public WebUserInfo get(int index)
    {
        return(WebUserInfo)getObject(index);
    }
    public WebUserInfo get(Object key)
    {
        return(WebUserInfo)getObject(key);
    }
    public void set(int index, WebUserInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WebUserInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WebUserInfo item)
    {
        return super.indexOf(item);
    }
}