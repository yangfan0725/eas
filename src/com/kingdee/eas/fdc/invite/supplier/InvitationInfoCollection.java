package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvitationInfoCollection extends AbstractObjectCollection 
{
    public InvitationInfoCollection()
    {
        super(InvitationInfoInfo.class);
    }
    public boolean add(InvitationInfoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvitationInfoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvitationInfoInfo item)
    {
        return removeObject(item);
    }
    public InvitationInfoInfo get(int index)
    {
        return(InvitationInfoInfo)getObject(index);
    }
    public InvitationInfoInfo get(Object key)
    {
        return(InvitationInfoInfo)getObject(key);
    }
    public void set(int index, InvitationInfoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvitationInfoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvitationInfoInfo item)
    {
        return super.indexOf(item);
    }
}