package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BankNumCollection extends AbstractObjectCollection 
{
    public BankNumCollection()
    {
        super(BankNumInfo.class);
    }
    public boolean add(BankNumInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BankNumCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BankNumInfo item)
    {
        return removeObject(item);
    }
    public BankNumInfo get(int index)
    {
        return(BankNumInfo)getObject(index);
    }
    public BankNumInfo get(Object key)
    {
        return(BankNumInfo)getObject(key);
    }
    public void set(int index, BankNumInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BankNumInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BankNumInfo item)
    {
        return super.indexOf(item);
    }
}