package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierStockContractEntryCollection extends AbstractObjectCollection 
{
    public SupplierStockContractEntryCollection()
    {
        super(SupplierStockContractEntryInfo.class);
    }
    public boolean add(SupplierStockContractEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierStockContractEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierStockContractEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierStockContractEntryInfo get(int index)
    {
        return(SupplierStockContractEntryInfo)getObject(index);
    }
    public SupplierStockContractEntryInfo get(Object key)
    {
        return(SupplierStockContractEntryInfo)getObject(key);
    }
    public void set(int index, SupplierStockContractEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierStockContractEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierStockContractEntryInfo item)
    {
        return super.indexOf(item);
    }
}