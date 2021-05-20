package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;

public class FDCSplitBillEntryInfo extends AbstractFDCSplitBillEntryInfo implements Serializable 
{
    public FDCSplitBillEntryInfo()
    {
        super();
    }
    protected FDCSplitBillEntryInfo(String pkField)
    {
        super(pkField);
    }
}