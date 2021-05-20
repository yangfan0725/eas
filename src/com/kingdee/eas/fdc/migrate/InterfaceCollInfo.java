package com.kingdee.eas.fdc.migrate;

import java.io.Serializable;

public class InterfaceCollInfo extends AbstractInterfaceCollInfo implements Serializable 
{
    public InterfaceCollInfo()
    {
        super();
    }
    protected InterfaceCollInfo(String pkField)
    {
        super(pkField);
    }
}