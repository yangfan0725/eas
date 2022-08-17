package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHEAttachBillCollection extends AbstractObjectCollection 
{
    public SHEAttachBillCollection()
    {
        super(SHEAttachBillInfo.class);
    }
    public boolean add(SHEAttachBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHEAttachBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHEAttachBillInfo item)
    {
        return removeObject(item);
    }
    public SHEAttachBillInfo get(int index)
    {
        return(SHEAttachBillInfo)getObject(index);
    }
    public SHEAttachBillInfo get(Object key)
    {
        return(SHEAttachBillInfo)getObject(key);
    }
    public void set(int index, SHEAttachBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHEAttachBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHEAttachBillInfo item)
    {
        return super.indexOf(item);
    }
}