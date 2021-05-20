package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OtherMoneyDefineTaxCollection extends AbstractObjectCollection 
{
    public OtherMoneyDefineTaxCollection()
    {
        super(OtherMoneyDefineTaxInfo.class);
    }
    public boolean add(OtherMoneyDefineTaxInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OtherMoneyDefineTaxCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OtherMoneyDefineTaxInfo item)
    {
        return removeObject(item);
    }
    public OtherMoneyDefineTaxInfo get(int index)
    {
        return(OtherMoneyDefineTaxInfo)getObject(index);
    }
    public OtherMoneyDefineTaxInfo get(Object key)
    {
        return(OtherMoneyDefineTaxInfo)getObject(key);
    }
    public void set(int index, OtherMoneyDefineTaxInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OtherMoneyDefineTaxInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OtherMoneyDefineTaxInfo item)
    {
        return super.indexOf(item);
    }
}