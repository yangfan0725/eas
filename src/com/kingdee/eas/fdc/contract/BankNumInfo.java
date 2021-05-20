package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class BankNumInfo extends AbstractBankNumInfo implements Serializable 
{
    public BankNumInfo()
    {
        super();
    }
    protected BankNumInfo(String pkField)
    {
        super(pkField);
    }
}