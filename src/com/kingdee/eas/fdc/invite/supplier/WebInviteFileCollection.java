package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WebInviteFileCollection extends AbstractObjectCollection 
{
    public WebInviteFileCollection()
    {
        super(WebInviteFileInfo.class);
    }
    public boolean add(WebInviteFileInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WebInviteFileCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WebInviteFileInfo item)
    {
        return removeObject(item);
    }
    public WebInviteFileInfo get(int index)
    {
        return(WebInviteFileInfo)getObject(index);
    }
    public WebInviteFileInfo get(Object key)
    {
        return(WebInviteFileInfo)getObject(key);
    }
    public void set(int index, WebInviteFileInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WebInviteFileInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WebInviteFileInfo item)
    {
        return super.indexOf(item);
    }
}