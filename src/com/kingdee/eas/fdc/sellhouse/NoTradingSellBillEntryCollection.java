package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NoTradingSellBillEntryCollection extends AbstractObjectCollection 
{
    public NoTradingSellBillEntryCollection()
    {
        super(NoTradingSellBillEntryInfo.class);
    }
    public boolean add(NoTradingSellBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NoTradingSellBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NoTradingSellBillEntryInfo item)
    {
        return removeObject(item);
    }
    public NoTradingSellBillEntryInfo get(int index)
    {
        return(NoTradingSellBillEntryInfo)getObject(index);
    }
    public NoTradingSellBillEntryInfo get(Object key)
    {
        return(NoTradingSellBillEntryInfo)getObject(key);
    }
    public void set(int index, NoTradingSellBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NoTradingSellBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NoTradingSellBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}