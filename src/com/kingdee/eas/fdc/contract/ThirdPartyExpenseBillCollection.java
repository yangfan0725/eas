package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ThirdPartyExpenseBillCollection extends AbstractObjectCollection 
{
    public ThirdPartyExpenseBillCollection()
    {
        super(ThirdPartyExpenseBillInfo.class);
    }
    public boolean add(ThirdPartyExpenseBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ThirdPartyExpenseBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ThirdPartyExpenseBillInfo item)
    {
        return removeObject(item);
    }
    public ThirdPartyExpenseBillInfo get(int index)
    {
        return(ThirdPartyExpenseBillInfo)getObject(index);
    }
    public ThirdPartyExpenseBillInfo get(Object key)
    {
        return(ThirdPartyExpenseBillInfo)getObject(key);
    }
    public void set(int index, ThirdPartyExpenseBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ThirdPartyExpenseBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ThirdPartyExpenseBillInfo item)
    {
        return super.indexOf(item);
    }
}