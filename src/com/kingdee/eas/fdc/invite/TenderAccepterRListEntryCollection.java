package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenderAccepterRListEntryCollection extends AbstractObjectCollection 
{
    public TenderAccepterRListEntryCollection()
    {
        super(TenderAccepterRListEntryInfo.class);
    }
    public boolean add(TenderAccepterRListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenderAccepterRListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenderAccepterRListEntryInfo item)
    {
        return removeObject(item);
    }
    public TenderAccepterRListEntryInfo get(int index)
    {
        return(TenderAccepterRListEntryInfo)getObject(index);
    }
    public TenderAccepterRListEntryInfo get(Object key)
    {
        return(TenderAccepterRListEntryInfo)getObject(key);
    }
    public void set(int index, TenderAccepterRListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenderAccepterRListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenderAccepterRListEntryInfo item)
    {
        return super.indexOf(item);
    }
}