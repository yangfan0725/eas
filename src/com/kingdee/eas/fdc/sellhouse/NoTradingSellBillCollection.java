package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NoTradingSellBillCollection extends AbstractObjectCollection 
{
    public NoTradingSellBillCollection()
    {
        super(NoTradingSellBillInfo.class);
    }
    public boolean add(NoTradingSellBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NoTradingSellBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NoTradingSellBillInfo item)
    {
        return removeObject(item);
    }
    public NoTradingSellBillInfo get(int index)
    {
        return(NoTradingSellBillInfo)getObject(index);
    }
    public NoTradingSellBillInfo get(Object key)
    {
        return(NoTradingSellBillInfo)getObject(key);
    }
    public void set(int index, NoTradingSellBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NoTradingSellBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NoTradingSellBillInfo item)
    {
        return super.indexOf(item);
    }
}