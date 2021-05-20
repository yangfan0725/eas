package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractSettlementSubmissionCollection extends AbstractObjectCollection 
{
    public ContractSettlementSubmissionCollection()
    {
        super(ContractSettlementSubmissionInfo.class);
    }
    public boolean add(ContractSettlementSubmissionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractSettlementSubmissionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractSettlementSubmissionInfo item)
    {
        return removeObject(item);
    }
    public ContractSettlementSubmissionInfo get(int index)
    {
        return(ContractSettlementSubmissionInfo)getObject(index);
    }
    public ContractSettlementSubmissionInfo get(Object key)
    {
        return(ContractSettlementSubmissionInfo)getObject(key);
    }
    public void set(int index, ContractSettlementSubmissionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractSettlementSubmissionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractSettlementSubmissionInfo item)
    {
        return super.indexOf(item);
    }
}