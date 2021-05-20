package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;

public class WebUserInfo extends AbstractWebUserInfo implements Serializable 
{
    public WebUserInfo()
    {
        super();
    }
    protected WebUserInfo(String pkField)
    {
        super(pkField);
    }
}