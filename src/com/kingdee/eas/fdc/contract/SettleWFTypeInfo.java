package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

import com.kingdee.eas.fdc.contract.AbstractContractWFTypeInfo;

public class SettleWFTypeInfo extends AbstractSettleWFTypeInfo implements Serializable 
{
    public SettleWFTypeInfo()
    {
        super();
    }
    protected SettleWFTypeInfo(String pkField)
    {
        super(pkField);
    }
}