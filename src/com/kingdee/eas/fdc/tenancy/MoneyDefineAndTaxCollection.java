package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MoneyDefineAndTaxCollection extends AbstractObjectCollection 
{
    public MoneyDefineAndTaxCollection()
    {
        super(MoneyDefineAndTaxInfo.class);
    }
    public boolean add(MoneyDefineAndTaxInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MoneyDefineAndTaxCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MoneyDefineAndTaxInfo item)
    {
        return removeObject(item);
    }
    public MoneyDefineAndTaxInfo get(int index)
    {
        return(MoneyDefineAndTaxInfo)getObject(index);
    }
    public MoneyDefineAndTaxInfo get(Object key)
    {
        return(MoneyDefineAndTaxInfo)getObject(key);
    }
    public void set(int index, MoneyDefineAndTaxInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MoneyDefineAndTaxInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MoneyDefineAndTaxInfo item)
    {
        return super.indexOf(item);
    }
}